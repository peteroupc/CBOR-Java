package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/CBOR/
 */

import java.io.InputStream;
import java.lang.reflect.*;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

/**
 * Description of PropertyMap.
 */
class PropertyMap {
  private static class MethodData {
    private String name;
    private Member method;
    public MethodData(String name, Member method) {
        this.name=name;
        this.method=method;
    }
    public String getName() {
        return this.name;
    }
    public Type GetValueType() {
       if(method instanceof Method)
          return ((Method)method).getGenericParameterTypes()[0];
       else if(method instanceof Field)
          return ((Field)method).getGenericType();
       return null;
    }
    public void SetValue(Object obj, Object value) {
       try {
         if(method instanceof Method)
            ((Method)method).invoke(obj, value);
         else if(method instanceof Field)
            ((Field)method).set(obj, value);
       } catch(InvocationTargetException ex) {
         throw (RuntimeException)new CBORException("").initCause(ex);
       } catch (IllegalAccessException ex) {
         throw (RuntimeException)new CBORException("").initCause(ex);
       }
    }
    public Object GetValue(Object obj) {
       try {
         if(method instanceof Method)
          return ((Method)method).invoke(obj);
         else if(method instanceof Field)
          return ((Field)method).get(obj);
         else
          return null;
       } catch(InvocationTargetException ex) {
         throw (RuntimeException)new CBORException("").initCause(ex);
       } catch (IllegalAccessException ex) {
         throw (RuntimeException)new CBORException("").initCause(ex);
       }
    }
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
    public static String RemoveGetSetIs(String name){
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
    private static String RemoveIs(String name){
      if(IsIsMethod(name))return name.substring(2);
      return name;
    }
    public String GetAdjustedName(boolean useCamelCase){
      if(method instanceof Field) {
       if(useCamelCase){
         return CBORUtilities.FirstCharLower(RemoveIs(this.name));
       } else {
         return CBORUtilities.FirstCharUpper(this.name);
       }
      } else {
       if(useCamelCase){
         return CBORUtilities.FirstCharLower(RemoveGetSetIs(this.name));
       } else {
         return CBORUtilities.FirstCharUpper(RemoveGetSet(this.name));
       }
     }
    }
  }

  private static Map<Class<?>, List<MethodData>> propertyLists =
      new TreeMap<Class<?>, List<MethodData>>();

  private static Map<Class<?>, List<MethodData>> setterPropertyList =
      new TreeMap<Class<?>, List<MethodData>>();

  private static List<MethodData> GetPropertyList(final Class<?> t) {
    return GetPropertyList(t,false);
  }

private static Method FindMethod(List<Method> methods, String shortName, Type t){
 for(Method m : methods){
   String mn=MethodData.RemoveGetSetIs(m.getName());
   if(mn.equals(shortName) && m.getReturnType().equals(t)){
     return m;
   }
 }
 return null;
}

private static <T> List<T> Compact(List<T> list) {
  boolean shouldCompact=false;
  for(T item : list) {
    if(item==null){
      shouldCompact=true;
      break;
    }
  }
  if(shouldCompact) {
    List<T> newList=new ArrayList<T>();
    for(T item : list) {
      if(item!=null){
        newList.add(item);
      }
    }
    return newList;
  } else {
    return list;
  }
}

  private static List<MethodData> GetPropertyList(final Class<?> t, boolean setters) {
    synchronized(setters ? setterPropertyList : propertyLists) {
      List<MethodData> ret;
      ret = (setters ? setterPropertyList : propertyLists).get(t);
      if (ret != null) {
        return ret;
      }
      ret = new ArrayList<MethodData>();
      List<Method> getMethods=new ArrayList<Method>();
      List<Method> setMethods=new ArrayList<Method>();
      List<Method> isMethods=new ArrayList<Method>();
      Map<String,Integer> getMethodNames=new
         TreeMap<String,Integer>();
      Map<String,Integer> setMethodNames=new
         TreeMap<String,Integer>();
      Map<String,Integer> methodNamesToIndices=new
         TreeMap<String,Integer>();
      boolean hasAmbiguousGetName=false;
      boolean hasAmbiguousSetName=false;
      for(Method pi : t.getMethods()) {
        if((pi.getModifiers() & (Modifier.PUBLIC | Modifier.STATIC))==Modifier.PUBLIC) {
          String methodName = pi.getName();
          String mn=MethodData.RemoveGetSetIs(methodName);
          if(MethodData.IsGetMethod(methodName)){
            if(pi.getParameterTypes().length == 0 &&
              !pi.getReturnType().equals(Void.TYPE)){
           if(getMethodNames.containsKey(mn)){
hasAmbiguousGetName=true;
getMethodNames.put(mn,getMethodNames.get(mn)+1);
} else {
getMethodNames.put(mn,1);
}
              getMethods.add(pi);
            }
          } else if(MethodData.IsIsMethod(methodName)){
            if(pi.getParameterTypes().length == 0 &&
              !pi.getReturnType().equals(Void.TYPE)){
           if(getMethodNames.containsKey(mn)){
hasAmbiguousGetName=true;
getMethodNames.put(mn,getMethodNames.get(mn)+1);
} else {
getMethodNames.put(mn,1);
}
              isMethods.add(pi);
            }
          } else if(MethodData.IsSetMethod(methodName)){
            if(pi.getParameterTypes().length == 1 &&
              pi.getReturnType().equals(Void.TYPE)){
           if(setMethodNames.containsKey(mn)){
hasAmbiguousSetName=true;
setMethodNames.put(mn,setMethodNames.get(mn)+1);
} else {
setMethodNames.put(mn,1);
}
              setMethods.add(pi);
            }
          }
        }
      }
if(!setters){
  for(Method m : getMethods){
    String mn=MethodData.RemoveGetSetIs(m.getName());
    // Don't add ambiguous methods
    if(getMethodNames.get(mn)>1){ continue;}
            MethodData md = new MethodData(m.getName(), m);
     ret.add(md);
  }
  for(Method m : isMethods){
    String mn=MethodData.RemoveGetSetIs(m.getName());
    // Don't add ambiguous methods
    if(getMethodNames.get(mn)>1){ continue;}
            MethodData md = new MethodData(m.getName(), m);
     ret.add(md);
  }
} else {
  for(Method m : setMethods){
    String mn=MethodData.RemoveGetSetIs(m.getName());
    // Don't add ambiguous methods
    if(setMethodNames.get(mn)>1){ continue;}
    // Check for existence of get method
    if(!getMethodNames.containsKey(mn)){ continue;}
    Method gm=FindMethod(getMethods,
      mn,m.getParameterTypes()[0]);
    if(gm==null)gm=FindMethod(isMethods,
      mn,m.getParameterTypes()[0]);
    if(gm!=null){
     int sz=ret.size();
            MethodData md = new MethodData(m.getName(), m);
     ret.add(md);
     methodNamesToIndices.put(mn, sz);
    }
  }
}
      // Now add fields
      for(Field pi : t.getFields()) {
        if((pi.getModifiers() & (Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL))==Modifier.PUBLIC) {
          String methodName = pi.getName();
          String mn=MethodData.RemoveIs(methodName);
          if(getMethodNames.containsKey(mn) || setMethodNames.containsKey(mn)) {
             // Ambiguous with eligible method name
             int index=methodNamesToIndices.containsKey(mn) ?
                 (int)methodNamesToIndices.get(mn) : -1;
             if(index>=0) {
                ret.set(index, null);
             }
          } else {
             // No ambiguity, so add field
            MethodData md = new MethodData(pi.getName(), pi);
            ret.add(md);
          }
        }
      }
      ret=Compact(ret);
      (setters ? setterPropertyList : propertyLists).put(t, ret);
      return ret;
    }
  }

  public static <K, V> Collection<Map.Entry<K, V>>
         GetEntries(Map<K, V> dict) {
      return Collections.unmodifiableMap(dict).entrySet();
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

  static class CopyOnWriteList extends AbstractList<CBORObject> {
     List<CBORObject> list;
     CBORObject[] array;
     public CopyOnWriteList(CBORObject[] array) {
        this.array=array;
     }
     public CBORObject get(int i) {
        return list!=null ? list.get(i) : array[i];
     }
     public int size() {
        return list!=null ? list.size() : array.length;
     }
     public CBORObject set(int i, CBORObject v) {
        if(list==null) {
           list=new ArrayList<CBORObject>(Arrays.asList(array));
           array=null;
        }
        return list.set(i, v);
     }
     public void add(int i, CBORObject v) {
        if(list==null) {
           list=new ArrayList<CBORObject>(Arrays.asList(array));
           array=null;
        }
        list.add(i, v);
     }
     public CBORObject remove(int i) {
        if(list==null) {
           list=new ArrayList<CBORObject>(Arrays.asList(array));
           array=null;
        }
        return list.remove(i);
     }
  }

  public static List<CBORObject> ListFromArray(CBORObject[] array) {
       return new CopyOnWriteList(array);
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

  public static Object EnumToObjectAsInteger(Enum<?> value) {
    return value.ordinal();
  }
  public static Iterable<Map.Entry<String, Object>> GetProperties(
       final Object o){
     return GetProperties(o, true);
  }

  public static Object ObjectWithProperties(
         Class<?> t,
         Iterable<Map.Entry<String, CBORObject>> keysValues,
         CBORTypeMapper mapper, PODOptions options,
         int depth) {
      try {
      Object o = t.getDeclaredConstructor().newInstance();
      Map<String, CBORObject> dict = new TreeMap<String, CBORObject>();
      for (Map.Entry<String, CBORObject> kv : keysValues) {
        String name = kv.getKey();
        dict.put(name,kv.getValue());
      }
      for (MethodData key : GetPropertyList(o.getClass(),true)) {
        String name = key.GetAdjustedName(
            options==null ? true : options.getUseCamelCase());
        if (dict.containsKey(name)) {
          CBORObject dget=dict.get(name);
          Object dobj = dget.ToObject(
             key.GetValueType(),mapper,options, depth+1);
          key.SetValue(o, dobj);
        }
      }
      return o;
    } catch(InvocationTargetException ex) {
      throw (RuntimeException)new CBORException("").initCause(ex);
    } catch(NoSuchMethodException ex) {
      throw (RuntimeException)new CBORException("").initCause(ex);
    } catch(InstantiationException ex) {
      throw (RuntimeException)new CBORException("").initCause(ex);
    } catch (IllegalAccessException ex) {
      throw (RuntimeException)new CBORException("").initCause(ex);
    }
    }

  public static Iterable<Map.Entry<String, Object>> GetProperties(
       final Object o, boolean useCamelCase) {
    List<Map.Entry<String, Object>> ret =
        new ArrayList<Map.Entry<String, Object>>();
    if(IsProblematicForSerialization(o.getClass())){
       return ret;
    }
      for(MethodData key : GetPropertyList(o.getClass())) {
        ret.add(new AbstractMap.SimpleEntry<String, Object>(
            key.GetAdjustedName(useCamelCase),
            key.GetValue(o)));
      }
      return ret;
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

  public static Object[] EnumValues(
       final Class<?> cls) {
    try {
      Method method=cls.getMethod("values");
      if(method.getParameterCount()==0 &&
        (method.getModifiers() & Modifier.PUBLIC)!=0){
        return (Object[])method.invoke(null);
      }
      return new Object[0];
    } catch (SecurityException e) {
      return new Object[0];
    } catch (IllegalAccessException e) {
      return new Object[0];
    } catch (InvocationTargetException e) {
      return new Object[0];
    } catch (NoSuchMethodException e) {
      return new Object[0];
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
      throw new CBORException(e.getMessage(), e);
    } catch (InvocationTargetException e) {
      throw new CBORException(e.getMessage(), e);
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
//System.err.println(name);
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

  public static Object TypeToObject(CBORObject objThis, Type t,
     CBORTypeMapper mapper, PODOptions options, int depth) {
      if (t.equals(Byte.class) || t.equals(Byte.TYPE)) {
        return objThis.AsByte();
      }
      if (t.equals(Short.class) || t.equals(Short.TYPE)) {
        return objThis.AsInt16();
      }
      if (t.equals(Integer.class) || t.equals(Integer.TYPE)) {
        return objThis.AsInt32();
      }
      if (t.equals(Long.class) || t.equals(Long.TYPE)) {
        return objThis.AsInt64();
      }
      if (t.equals(Double.class) || t.equals(Double.TYPE)) {
        return objThis.AsDouble();
      }
      if (t.equals(Float.class) || t.equals(Float.TYPE)) {
        return objThis.AsSingle();
      }
      if (t.equals(Boolean.class) || t.equals(Boolean.TYPE)) {
        return objThis.AsBoolean();
      }
      if(t.equals(Character.class) || t.equals(Character.TYPE)){
if(objThis.getType()==CBORType.TextString){
  String s=objThis.AsString();
  if(s.length()!=1)throw new CBORException("Can't convert to char");
  return s.charAt(0);
}
if(objThis.isIntegral() && objThis.CanTruncatedIntFitInInt32()){
  int c=objThis.AsInt32();
  if(c<0 || c>=0x10000)
    throw new CBORException("Can't convert to char");
  return (char)c;
}
throw new CBORException("Can't convert to char");
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
      if(t instanceof Class<?> && Enum.class.isAssignableFrom((Class<?>)t)){
if(objThis.getType()==CBORType.TextString){
 try {
  return Enum.valueOf((Class)t,objThis.AsString());
 } catch(Exception ex){
  throw new CBORException(ex.getMessage(),ex);
 }
} else if(objThis.isNumber() && objThis.isIntegral()){
 Object[] enumValues=EnumValues((Class<?>)t);
 int k=objThis.AsInt32();
 if(k<0 || k>=enumValues.length){
   throw new CBORException("Invalid enum: "+objThis.toString());
 }
 return enumValues[k];
} else {
 throw new CBORException("Invalid enum: "+objThis.toString());
}
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
 Class<?> rawClass=(rawType instanceof Class<?>) ?
    ((Class<?>)rawType) : null;
 if(rawClass!=null && rawClass.isArray()){
   Class<?> ct=rawClass.getComponentType();
   Object objRet=Array.newInstance(ct,
      objThis.size());
   int i=0;
   for(CBORObject cbor : objThis.getValues()){
    Array.set(objRet,i,cbor.ToObject(ct,mapper,options,depth+1));
    i++;
   }
   return objRet;
 }
 if(rawType!=null &&
    rawType.equals(List.class) || rawType.equals(Iterable.class) ||
    rawType.equals(java.util.Collection.class) || rawType.equals(ArrayList.class)){
  if(typeArguments==null || typeArguments.length==0){
   ArrayList alist=new ArrayList();
   for(CBORObject cbor : objThis.getValues()){
    alist.add(cbor.ToObject(Object.class,mapper,options,depth+1));
   }
   return alist;
  } else {
   ArrayList alist=new ArrayList();
   for(CBORObject cbor : objThis.getValues()){
    alist.add(cbor.ToObject(typeArguments[0],mapper,options,depth+1));
   }
   return alist;
  }
 } else if(rawClass!=null && !rawClass.isInterface()) {
   if(List.class.isAssignableFrom(rawClass) && typeArguments!=null &&
       typeArguments.length==1) {
     List list = null;
     try {
       list=(List)rawClass.getDeclaredConstructor().newInstance();
     } catch(NoSuchMethodException ex){
       throw new CBORException("Failed to create object", ex);
     } catch(InvocationTargetException ex){
       throw new CBORException("Failed to create object", ex);
     } catch(IllegalAccessException ex){
       throw new CBORException("Failed to create object", ex);
     } catch(InstantiationException ex){
       throw new CBORException("Failed to create object", ex);
     }
     for(CBORObject cbor : objThis.getValues()){
      list.add(cbor.ToObject(typeArguments[0],mapper,options,depth+1));
     }
     return list;
   }
 }
}
if(objThis.getType()==CBORType.Map){
 if(rawType!=null &&
    rawType.equals(TreeMap.class) || rawType.equals(Map.class)){
  if(typeArguments==null || typeArguments.length<2){
   TreeMap alist=new TreeMap();
   for(CBORObject cbor : objThis.getKeys()){
    CBORObject cborValue=objThis.get(cbor);
    alist.put(cbor.ToObject(Object.class,mapper,options,depth+1),
      cborValue.ToObject(Object.class,mapper,options,depth+1));
   }
   return alist;
  } else {
   TreeMap alist=new TreeMap();
   for(CBORObject cbor : objThis.getKeys()){
    CBORObject cborValue=objThis.get(cbor);
    alist.put(cbor.ToObject(typeArguments[0],mapper,options,depth+1),
      cborValue.ToObject(typeArguments[1],mapper,options,depth+1));
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
        for (MethodData method : GetPropertyList(
             (Class<?>)rawType,true/* getting list of setters*/)) {
          String key = method.GetAdjustedName(
             options==null ? true : options.getUseCamelCase());
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
    mapper,
    options,depth);
}
       throw new CBORException();
    }

    @SuppressWarnings("unchecked")
    public static CBORObject CallToObject(
       CBORTypeMapper.ConverterInfo convinfo,
       Object obj) {
      if(convinfo.getConverter() instanceof ICBORConverter) {
        return ((ICBORConverter)convinfo.getConverter()).ToCBORObject(obj);
      }
      return null;
    }

    @SuppressWarnings("unchecked")
    public static Object CallFromObject(
       CBORTypeMapper.ConverterInfo convinfo,
       CBORObject obj) {
      if(convinfo.getConverter() instanceof ICBORToFromConverter<?>) {
        return ((ICBORToFromConverter<?>)convinfo.getConverter()).FromCBORObject(obj);
      }
      return null;
    }

   public static void BreakDownDateTime(java.util.Date bi,
        EInteger[] year, int[] lf) {
    long time=bi.getTime();
    int nanoseconds=((int)(time%1000L));
    if(nanoseconds<0)nanoseconds=1000+nanoseconds;
    nanoseconds*=1000000;
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
