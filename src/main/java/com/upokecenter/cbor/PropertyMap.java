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
    public static boolean IsIsMethod(String methodName){
          return (methodName.startsWith("is") && methodName.length() > 2 &&
              methodName.charAt(2) >= 'A' && methodName.charAt(2) <= 'Z');
    }
    public String GetAdjustedName(boolean removeIsPrefix, boolean useCamelCase){
          String methodName = this.name;
          if(MethodData.IsGetMethod(methodName)) {
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

  private static List<MethodData> GetPropertyList(final Class<?> t) {
    synchronized(propertyLists) {
      List<MethodData> ret;
      ret = propertyLists.get(t);
      if (ret != null) {
        return ret;
      }
      ret = new ArrayList<MethodData>();
      for(Method pi : t.getMethods()) {
        if(pi.getParameterTypes().length == 0) {
          String methodName = pi.getName();
          if(MethodData.IsGetMethod(methodName) || MethodData.IsIsMethod(methodName)){
            MethodData md = new MethodData();
            md.name = methodName;
            md.method = pi;
            ret.add(md);
          }
        }
      }
      propertyLists.put(t, ret);
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

  /**
   * <p>GetProperties.</p>
   *
   * @param o a {@link java.lang.Object} object.
   * @return a {@link java.lang.Iterable} object.
   */
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

  private static Object methodSync=new Object();
  private static Method[] legacyMethods=new Method[8];
  private static boolean haveMethods=false;
  private static Method getLegacyMethod(int method){
    synchronized(methodSync){
      if(!haveMethods){
        try {
          legacyMethods[0]=BigInteger.class.getDeclaredMethod("ToLegacy",EInteger.class);
          legacyMethods[1]=BigInteger.class.getDeclaredMethod("FromLegacy",BigInteger.class);
          legacyMethods[2]=ExtendedDecimal.class.getDeclaredMethod("ToLegacy",EDecimal.class);
          legacyMethods[3]=ExtendedDecimal.class.getDeclaredMethod("FromLegacy",ExtendedDecimal.class);
          legacyMethods[4]=ExtendedFloat.class.getDeclaredMethod("ToLegacy",EFloat.class);
          legacyMethods[5]=ExtendedFloat.class.getDeclaredMethod("FromLegacy",ExtendedFloat.class);
          legacyMethods[6]=ExtendedRational.class.getDeclaredMethod("ToLegacy",ERational.class);
          legacyMethods[7]=ExtendedRational.class.getDeclaredMethod("FromLegacy",ExtendedRational.class);
          for(int i=0;i<legacyMethods.length;i++){
            legacyMethods[i].setAccessible(true);
          }
        } catch (SecurityException e) {
          throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
          throw new RuntimeException(e);
        }
        haveMethods=true;
      }
      return legacyMethods[method];
    }
  }

    public static boolean ExceedsKnownLength(InputStream inStream, long size) {
      return false;
    }

    public static void SkipStreamToEnd(InputStream inStream) {
    }

    public static BigInteger ToLegacy(EInteger ei){
      return (BigInteger)InvokeOneArgumentMethod(
        getLegacyMethod(0), null, ei);
    }
    public static ExtendedDecimal ToLegacy(EDecimal ed){
      return (ExtendedDecimal)InvokeOneArgumentMethod(
        getLegacyMethod(2), null, ed);
    }
    public static ExtendedFloat ToLegacy(EFloat ef){
      return (ExtendedFloat)InvokeOneArgumentMethod(
        getLegacyMethod(4), null, ef);
    }
    public static ExtendedRational ToLegacy(ERational er){
      return (ExtendedRational)InvokeOneArgumentMethod(
        getLegacyMethod(6), null, er);
    }

    public static EInteger FromLegacy(BigInteger ei){
      return (EInteger)InvokeOneArgumentMethod(
        getLegacyMethod(1), null, ei);
    }
    public static EDecimal FromLegacy(ExtendedDecimal ed){
      return (EDecimal)InvokeOneArgumentMethod(
        getLegacyMethod(3), null, ed);
    }
    public static EFloat FromLegacy(ExtendedFloat ef){
      return (EFloat)InvokeOneArgumentMethod(
        getLegacyMethod(5), null, ef);
    }
    public static ERational FromLegacy(ExtendedRational er){
      return (ERational)InvokeOneArgumentMethod(
        getLegacyMethod(7), null, er);
    }

  /**
   * <p>InvokeOneArgumentMethod.</p>
   *
   * @param method a {@link java.lang.Object} object.
   * @param obj a {@link java.lang.Object} object.
   * @param argument a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
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

  public static Object TypeToObject(CBORObject objThis, Type t) {
      //if (t.Equals(typeof(DateTime))) {
      //  return new CBORTag0().FromCBORObject(objThis);
      //}
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
   for(CBORObject cbor : objThis.getValues()){
    CBORObject cborValue=objThis.get(cbor);
    alist.put(cbor.ToObject(typeArguments[0]),
      cborValue.ToObject(typeArguments[1]));
   }
   return alist;
  }
 }
}

/*
      if (objThis.Type == CBORType.Map) {
        var isDict = false;
        Type keyType = null;
        Type valueType = null;
        object dictObject = null;
        var values = new List<KeyValuePair<string, CBORObject>>();
        foreach (string key in PropertyMap.GetPropertyNames(
                   t,
                   true,
                   true)) {
          if (objThis.ContainsKey(key)) {
            CBORObject cborValue = objThis[key];
            var dict = new KeyValuePair<string, CBORObject>(
              key,
              cborValue);
            values.Add(dict);
          }
        }
        return PropertyMap.ObjectWithProperties(
    t,
    values,
    true,
    true);
      } else
*/    {
        throw new UnsupportedOperationException();
      }
    }

}
