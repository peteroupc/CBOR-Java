package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/CBOR/
 */

import java.io.InputStream;
import java.lang.reflect.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

/**
 * Description of PropertyMap.
 */
class PropertyMap {
  private static class MethodData {
    public String name;
    public Method method;
    public static boolean IsGetMethod(String methodName){
          return (CBORUtilities.NameStartsWithWord(methodName,"get") && !methodName.equals("getClass"));
    }
    public static boolean IsSetMethod(String methodName){
          return (CBORUtilities.NameStartsWithWord(methodName,"set"));
    }
    public static boolean IsIsMethod(String methodName){
          return (CBORUtilities.NameStartsWithWord(methodName,"is"));
    }
    public static String GetGetMethod(String methodName){
      return (IsSetMethod(methodName)) ?
          "get"+methodName.substring(3) :
          methodName;
    }
    public static String GetSetMethod(String methodName){
      return (IsSetMethod(methodName)) ?
          "set"+methodName.substring(3) :
          methodName;
    }
    public static String GetIsMethod(String methodName){
      return (IsIsMethod(methodName)) ?
          "is"+methodName.substring(2) :
          methodName;
    }
    private static String RemoveGetSetIs(String name){
      if(IsSetMethod(name))return name.substring(3);
      if(IsGetMethod(name))return name.substring(3);
      if(IsIsMethod(name))return name.substring(2);
      return name;
    }
    private static String RemoveGetSet(String name){
      if(IsSetMethod(name))return name.substring(3);
      if(IsGetMethod(name))return name.substring(3);
      return name;
    }
    public String GetAdjustedName(boolean removeIsPrefix, boolean useCamelCase){
      if(useCamelCase){
        return CBORUtilities.FirstCharLower(RemoveGetSetIs(this.name));
      } else {
        return CBORUtilities.FirstCharUpper(RemoveGetSet(this.name));
      }
    }
  }

  private static Map<Class<?>, List<MethodData>> propertyLists =
      new HashMap<Class<?>, List<MethodData>>();

  private static Map<Class<?>, List<MethodData>> setterPropertyList =
      new HashMap<Class<?>, List<MethodData>>();

  private static List<MethodData> GetPropertyList(final Class<?> t) {
    return GetPropertyList(t,false);
  }

  private static List<MethodData> GetPropertyList(final Class<?> t, boolean setters) {
    synchronized(setters ? setterPropertyList : propertyLists) {
      List<MethodData> ret;
      ret = (setters ? setterPropertyList : propertyLists).get(t);
      if (ret != null) {
        return ret;
      }
      ret = new ArrayList<MethodData>();
      Map<String,Method> propMap=new HashMap<String,Method>();
      for(Method pi : t.getMethods()) {
        if((pi.getModifiers() & Modifier.STATIC)==0) {
          String methodName = pi.getName();
          if(MethodData.IsGetMethod(methodName) ||
                 MethodData.IsIsMethod(methodName) ||
                 MethodData.IsSetMethod(methodName)){
            propMap.put(methodName,pi);
          }
        }
      }
      for(String key : propMap.keySet()){
        if(!setters && (MethodData.IsGetMethod(key) ||
           MethodData.IsIsMethod(key))){
            MethodData md = new MethodData();
            md.name = key;
            md.method = propMap.get(key);
            if(md.method.getParameterTypes().length == 0 &&
              !md.method.getReturnType().equals(Void.class)){
              ret.add(md);
            }
        } else if(setters && MethodData.IsSetMethod(key) && (
            propMap.containsKey(MethodData.GetGetMethod(key)) ||
            propMap.containsKey(MethodData.GetIsMethod(key)) )){
            MethodData md = new MethodData();
            md.name = key;
            md.method = propMap.get(key);
            if(md.method.getParameterTypes().length == 1){
              ret.add(md);
            }
        }
      }
      (setters ? setterPropertyList : propertyLists).put(t, ret);
      return ret;
    }
  }

  /**
   * <p>FromArray.</p>
   *
   * @param arr a {@link java.lang.Object} object.
   * @return a {@link com.upokecenter.cbor.CBORObject} object.
   */
  public static CBORObject FromArray(final Object arr, PODOptions options,
      CBORTypeMapper mapper, int depth) {
   int length = Array.getLength(arr);
   CBORObject obj = CBORObject.NewArray();
   for(int i = 0;i < length;i++) {
    obj.Add(CBORObject.FromObject(Array.get(arr,i), options,
     mapper, depth+1));
   }
   return obj;
  }

  /**
   * <p>EnumToObject.</p>
   *
   * @param value a {@link java.lang.Enum} object.
   * @return a {@link java.lang.Object} object.
   */
  public static Object EnumToObject(final Enum<?> value) {
    return value.name();
  }

  public static Iterable<Map.Entry<String, Object>> GetProperties(
       final Object o){
     return GetProperties(o, true, true);
  }

  public static Object ObjectWithProperties(
         Class<?> t,
         Iterable<Map.Entry<String, CBORObject>> keysValues,
         boolean removeIsPrefix,
         boolean useCamelCase) {
      try {
      Object o = t.newInstance();
      Map<String, CBORObject> dict = new HashMap<String, CBORObject>();
      for (Map.Entry<String, CBORObject> kv : keysValues) {
        String name = kv.getKey();
        dict.put(name,kv.getValue());
      }
      for (MethodData key : GetPropertyList(o.getClass(),true)) {
        String name = key.GetAdjustedName(removeIsPrefix, useCamelCase);
        if (dict.containsKey(name)) {
          CBORObject dget=dict.get(name);
          Object dobj = dget.ToObject(
             key.method.getGenericParameterTypes()[0]);
          key.method.invoke(o, dobj);
        }
      }
      return o;
    } catch(InvocationTargetException ex) {
      throw (RuntimeException)new RuntimeException("").initCause(ex);
    } catch(InstantiationException ex) {
      throw (RuntimeException)new RuntimeException("").initCause(ex);
    } catch (IllegalAccessException ex) {
      throw (RuntimeException)new RuntimeException("").initCause(ex);
    }
    }

  public static Iterable<Map.Entry<String, Object>> GetProperties(
       final Object o, boolean removeIsPrefix, boolean useCamelCase) {
    List<Map.Entry<String, Object>> ret =
        new ArrayList<Map.Entry<String, Object>>();
    if(IsProblematicForSerialization(o.getClass())){
       return ret;
    }
    try {
      for(MethodData key : GetPropertyList(o.getClass())) {
        ret.add(new AbstractMap.SimpleEntry<String, Object>(
            key.GetAdjustedName(removeIsPrefix, useCamelCase),
            key.method.invoke(o)));
      }
      return ret;
    } catch(InvocationTargetException ex) {
      throw (RuntimeException)new RuntimeException("").initCause(ex);
    } catch (IllegalAccessException ex) {
      throw (RuntimeException)new RuntimeException("").initCause(ex);
    }
  }

  /**
   * <p>FindOneArgumentMethod.</p>
   *
   * @param obj a {@link java.lang.Object} object.
   * @param name a {@link java.lang.String} object.
   * @param argtype a {@link java.lang.Class} object.
   * @return a {@link java.lang.Object} object.
   */
  public static Object FindOneArgumentMethod(final Object obj, final String name, final Type argtype) {
    if(!(argtype instanceof Class<?>))return null;
    try {
      return obj.getClass().getMethod(name, (Class<?>)argtype);
    } catch (SecurityException e) {
      return null;
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

    public static boolean ExceedsKnownLength(InputStream inStream, long size) {
      return false;
    }

    public static void SkipStreamToEnd(InputStream inStream) {
    }

  public static Object InvokeOneArgumentMethod(final Object method,
      final Object obj, final Object argument) {
    if(method == null) {
      throw new NullPointerException("method");
    }
    Method m = (Method)method;
    try {
      return m.invoke(obj, argument);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] UUIDToBytes(java.util.UUID obj){

      byte[] bytes2 = new byte[16];
      long lsb = obj.getLeastSignificantBits();
      long msb = obj.getMostSignificantBits();
      bytes2[0] = (byte)((msb >> 56) & 0xFFL);
      bytes2[1] = (byte)((msb >> 48) & 0xFFL);
      bytes2[2] = (byte)((msb >> 40) & 0xFFL);
      bytes2[3] = (byte)((msb >> 32) & 0xFFL);
      bytes2[4] = (byte)((msb >> 24) & 0xFFL);
      bytes2[5] = (byte)((msb >> 16) & 0xFFL);
      bytes2[6] = (byte)((msb >> 8) & 0xFFL);
      bytes2[7] = (byte)((msb) & 0xFFL);
      bytes2[8] = (byte)((lsb >> 56) & 0xFFL);
      bytes2[9] = (byte)((lsb >> 48) & 0xFFL);
      bytes2[10] = (byte)((lsb >> 40) & 0xFFL);
      bytes2[11] = (byte)((lsb >> 32) & 0xFFL);
      bytes2[12] = (byte)((lsb >> 24) & 0xFFL);
      bytes2[13] = (byte)((lsb >> 16) & 0xFFL);
      bytes2[14] = (byte)((lsb >> 8) & 0xFFL);
      bytes2[15] = (byte)((lsb) & 0xFFL);
      return bytes2;
  }

private static boolean IsProblematicForSerialization(Class<?> cls){
String name=cls.getName();
if((name.startsWith("java.")||
    name.startsWith("javax.")||
    name.startsWith("com.sun."))){
  boolean serializable = false;
  for(Class<?> iface : cls.getInterfaces()){
    if(iface.equals(java.io.Serializable.class)){
      serializable = true;
      break;
    }
  }
  if(!serializable){
    return true;
  }
}
if(Type.class.isAssignableFrom(cls) ||
       Method.class.isAssignableFrom(cls) ||
       Field.class.isAssignableFrom(cls) ||
       Constructor.class.isAssignableFrom(cls)){
      return true;
}
System.err.println(name);
if((name.startsWith("org.springframework.") ||
   name.startsWith("java.io.") ||
   name.startsWith("java.lang.annotation.") ||
   name.startsWith("java.security.SignedObject") ||
   name.startsWith("com.sun.rowset") ||
   name.startsWith("com.sun.org.apache.") ||
   name.startsWith("org.apache.xalan.") ||
   name.startsWith("org.apache.xpath.") ||
   name.startsWith("org.codehaus.groovy.") ||
   name.startsWith("com.sun.jndi.") ||
   name.startsWith("groovy.util.Expando") ||
   name.startsWith("java.util.logging.") ||
   name.startsWith("com.mchange.v2.c3p0."))){
   return true;
}
return false;
}

  public static Object TypeToObject(CBORObject objThis, Type t) {
      if (t.equals(Byte.class) || t.equals(byte.class)) {
        return objThis.AsByte();
      }
      if (t.equals(Short.class) || t.equals(short.class)) {
        return objThis.AsInt16();
      }
      if (t.equals(Integer.class) || t.equals(int.class)) {
        return objThis.AsInt32();
      }
      if (t.equals(Long.class) || t.equals(long.class)) {
        return objThis.AsInt64();
      }
      if (t.equals(Double.class) || t.equals(double.class)) {
        return objThis.AsDouble();
      }
      if (t.equals(Float.class) || t.equals(float.class)) {
        return objThis.AsSingle();
      }
      if (t.equals(Boolean.class) || t.equals(boolean.class)) {
        return objThis.AsBoolean();
      }
      if (t.equals(java.util.Date.class)) {
        return new CBORDateConverter().FromCBORObject(objThis);
      }
      if (t.equals(java.util.UUID.class)) {
        return new CBORUuidConverter().FromCBORObject(objThis);
      }
      if (t.equals(java.net.URI.class)) {
        return new CBORUriConverter().FromCBORObject(objThis);
      }
      if (t.equals(EInteger.class)) {
        return objThis.AsEInteger();
      }
      if (t.equals(EDecimal.class)) {
        return objThis.AsEDecimal();
      }
      if (t.equals(EFloat.class)) {
        return objThis.AsEFloat();
      }
      if (t.equals(ERational.class)) {
        return objThis.AsERational();
      }
      if (objThis.getType() == CBORType.ByteString) {
        if (t.equals(byte[].class)) {
          byte[] bytes = objThis.GetByteString();
          byte[] byteret = new byte[bytes.length];
          System.arraycopy(bytes, 0, byteret, 0, byteret.length);
          return byteret;
        }
      }
ParameterizedType pt=(t instanceof ParameterizedType) ?
   ((ParameterizedType)t) : null;
Type rawType=(pt==null) ? t : pt.getRawType();
Type[] typeArguments=(pt==null) ? null : pt.getActualTypeArguments();
if(objThis.getType()==CBORType.Array){
 //TODO: Support arrays
 if(rawType!=null &&
    rawType.equals(List.class) || rawType.equals(Iterable.class) ||
    rawType.equals(java.util.Collection.class) || rawType.equals(ArrayList.class)){
  if(typeArguments==null || typeArguments.length==0){
   ArrayList alist=new ArrayList();
   for(CBORObject cbor : objThis.getValues()){
    alist.add(cbor.ToObject(Object.class));
   }
   return alist;
  } else {
   ArrayList alist=new ArrayList();
   for(CBORObject cbor : objThis.getValues()){
    alist.add(cbor.ToObject(typeArguments[0]));
   }
   return alist;
  }
 }
}
if(objThis.getType()==CBORType.Map){
 if(rawType!=null &&
    rawType.equals(HashMap.class) || rawType.equals(Map.class)){
  if(typeArguments==null || typeArguments.length<2){
   HashMap alist=new HashMap();
   for(CBORObject cbor : objThis.getKeys()){
    CBORObject cborValue=objThis.get(cbor);
    alist.put(cbor.ToObject(Object.class),
      cborValue.ToObject(Object.class));
   }
   return alist;
  } else {
   HashMap alist=new HashMap();
   for(CBORObject cbor : objThis.getKeys()){
    CBORObject cborValue=objThis.get(cbor);
    alist.put(cbor.ToObject(typeArguments[0]),
      cborValue.ToObject(typeArguments[1]));
   }
   return alist;
  }
 }
if(rawType==null || !(rawType instanceof Class<?>)){
  throw new CBORException();
}
String name=((Class<?>)rawType).getName();
if(name==null ){
  throw new CBORException();
}
if(IsProblematicForSerialization((Class<?>)rawType)){
  throw new CBORException(name);
}

        ArrayList<Map.Entry<String, CBORObject>> values =
          new ArrayList<Map.Entry<String, CBORObject>>();
        for (MethodData method : GetPropertyList((Class<?>)rawType,true)) {
          String key = method.GetAdjustedName(true,true);
          if (objThis.ContainsKey(key)) {
            CBORObject cborValue = objThis.get(key);
            Map.Entry<String, CBORObject> dict =
                new AbstractMap.SimpleEntry<String, CBORObject>(
              key,
              cborValue);
            values.add(dict);
          }
        }
        return PropertyMap.ObjectWithProperties(
    (Class<?>)rawType,
    values,
    true,
    true);
}
       throw new CBORException();
    }

   public static void BreakDownDateTime(java.util.Date bi,
        EInteger[] year, int[] lf) {
    long time=bi.getTime();
    int nanoseconds=((int)(time%1000L));
    if(nanoseconds<0)nanoseconds=1000+nanoseconds;
    nanoseconds*=1000000;
//System.out.println(nanoseconds+","+time);
    EDecimal edec=EDecimal.FromInt64(time).Divide(
      EDecimal.FromInt32(1000));
    CBORUtilities.BreakDownSecondsSinceEpoch(edec,year,lf);
    lf[5]=nanoseconds;
   }

public static java.util.Date BuildUpDateTime(EInteger year, int[] dt){
 EInteger dateMS=CBORUtilities.GetNumberOfDaysProlepticGregorian(
   year,dt[0],dt[1]).Multiply(EInteger.FromInt32(86400000));
 EInteger frac=EInteger.FromInt32(0);
//System.out.println(dt[2]+","+dt[3]+","+dt[4]+","+dt[5]);
 frac=frac.Add(EInteger.FromInt32(dt[2]*3600000+dt[3]*60000+dt[4]*1000));
 // Milliseconds
 frac=frac.Add(EInteger.FromInt32(dt[5]/1000000));
 // Time zone offset in minutes
 frac=frac.Subtract(EInteger.FromInt32(dt[6]*60000));
 dateMS=dateMS.Add(frac);
 return new java.util.Date(dateMS.ToInt64Checked());
}

}
