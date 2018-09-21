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
          return (methodName.startsWith("get") && methodName.length() > 3 &&
              methodName.charAt(3) >= 'A' && methodName.charAt(3) <= 'Z' &&
              !methodName.equals("getClass"));
    }
    public static boolean IsSetMethod(String methodName){
          return (methodName.startsWith("set") && methodName.length() > 3 &&
              methodName.charAt(3) >= 'A' && methodName.charAt(3) <= 'Z');
    }
    public static boolean IsIsMethod(String methodName){
          return (methodName.startsWith("is") && methodName.length() > 2 &&
              methodName.charAt(2) >= 'A' && methodName.charAt(2) <= 'Z');
    }
    public String GetAdjustedName(boolean removeIsPrefix, boolean useCamelCase){
          String methodName = this.name;
          if(MethodData.IsGetMethod(methodName) ||
             MethodData.IsSetMethod(methodName)) {
            methodName = methodName.substring(3);
          } else if(removeIsPrefix && MethodData.IsIsMethod(methodName)) {
            methodName = methodName.substring(2);
          }
          if(useCamelCase && methodName.charAt(0) >= 'A' && methodName.charAt(0) <= 'Z') {
              StringBuilder sb = new StringBuilder();
              sb.append((char)(methodName.charAt(0) + 0x20));
              sb.append(methodName.substring(1));
              methodName = sb.toString();
          }
          return methodName;
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
      for(Method pi : t.getMethods()) {
        if(pi.getParameterTypes().length == (setters ? 1 : 0) &&
          (pi.getModifiers() & Modifier.STATIC)==0) {
          String methodName = pi.getName();
          boolean includeMethod=false;
          if(setters)includeMethod=MethodData.IsSetMethod(methodName);
          else includeMethod=MethodData.IsGetMethod(methodName) ||
                 MethodData.IsIsMethod(methodName);
          if(includeMethod){
            MethodData md = new MethodData();
            md.name = methodName;
            md.method = pi;
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
  public static CBORObject FromArray(final Object arr, PODOptions options) {
   int length = Array.getLength(arr);
   CBORObject obj = CBORObject.NewArray();
   for(int i = 0;i < length;i++) {
    obj.Add(CBORObject.FromObject(Array.get(arr,i), options));
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
      Object o = null;
      for (Constructor ci : t.getConstructors()) {
          int nump = ci.getParameterCount();
          o = ci.newInstance(new Object[nump]);
          break;
      }
      if(o==null){ return t.newInstance(); }
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

  public static Object TypeToObject(CBORObject objThis, Type t) {
      if (t.equals(java.util.Date.class)) {
        return new CBORTag0().FromCBORObject(objThis);
      }
      if (t.equals(java.util.UUID.class)) {
        return new CBORTag37().FromCBORObject(objThis);
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
        return objThis.isTrue();
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
string typeName=rawType.getName();
if(name!=null &&
   (name.startsWith("org.springframework.") ||
   name.startsWith("java.io.") ||
   name.startsWith("java.util.logging.") ||
   name.startsWith("com.mchange.v2.c3p0."))){
  throw new UnsupportedOperationException("Type "+name+" not supported");
}
if(objThis.getType()==CBORType.Array){
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
  throw new UnsupportedOperationException();
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
       throw new UnsupportedOperationException();
    }

   public static void BreakDownDateTime(java.util.Date bi,
        EInteger[] year, int[] lf) {
    long time=bi.getTime();
    EDecimal edec=EDecimal.FromInt64(time).Divide(
      EDecimal.FromInt32(1000));
    CBORUtilities.BreakDownSecondsSinceEpoch(edec,year,lf);
   }

public static java.util.Date BuildUpDateTime(EInteger year, int[] dt){
 EInteger dateMS=CBORUtilities.GetNumberOfDaysProlepticGregorian(
   year,dt[0],dt[1]);
 dateMS=dateMS.Multiply(EInteger.FromInt32(86400000));
 dateMS=dateMS.Add(EInteger.FromInt32(dt[2]*3600000+dt[3]*60000+dt[4]));
 // Milliseconds
 dateMS=dateMS.Add(EInteger.FromInt32(dt[5]/1000000));
 // Time zone offset in minutes
 dateMS=dateMS.Add(EInteger.FromInt32(dt[6]*60000));
 return new java.util.Date(dateMS.ToInt64Checked());
}

}
