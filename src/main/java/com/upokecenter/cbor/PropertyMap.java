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
// TODO: Remove in next major version
static final boolean DateTimeCompatHack = false;

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
        if(pi.getParameterTypes().length == (setters ? 1 : 0)) {
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
      if (t.equals(java.util.Date.class)) {
        return new CBORTag0().FromCBORObject(objThis);
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

    private static final int[] normalDays = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30,
      31, 30, 31 };
    private static final int[] leapDays = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30,
      31, 30, 31 };
    private static final int[] normalToMonth = { 0, 0x1f, 0x3b, 90, 120, 0x97, 0xb5,
      0xd4, 0xf3, 0x111, 0x130, 0x14e, 0x16d };
    private static final int[] leapToMonth = { 0, 0x1f, 60, 0x5b, 0x79, 0x98, 0xb6,
      0xd5, 0xf4, 0x112, 0x131, 0x14f, 0x16e };
    private static long GetNumberOfDaysProlepticGregorian(
         int year, int month, int day) {
      // NOTE: month=1 is January, year=1 is year 1
      long numDays = 0;
      int startYear = 1970;
      if (year < startYear) {
        for (int i = startYear - 1; i > year; --i) {
          if ((i & 3) != 0||
                    (i % 100 == 0 && i % 400 != 0)) {
            numDays -= 365;
          } else {
            numDays -= 366;
          }
        }
        if ((year & 3) != 0||
            (year % 100 == 0 && year % 400 != 0)) {
          numDays -= 365 - normalToMonth[month];
          numDays -= normalDays[month] - day + 1;
        } else {
          numDays -= 366 - leapToMonth[month];
          numDays -= leapDays[month] - day + 1;
        }
      } else {
        boolean isNormalYear = (year & 3) != 0||
          (year % 100 == 0 && year % 400 != 0);
        int i = startYear;
        for (; i + 401 < year; i += 400) {
          numDays += 146097;
        }
        for (; i < year; ++i) {
          if ((i & 3) != 0 || (i % 100 == 0 && i % 400 != 0)) {
            numDays += 365;
          } else {
            numDays += 366;
          }
        }
        --day;
        if (isNormalYear) {
          numDays += normalToMonth[month - 1];
        } else {
          numDays += leapToMonth[month - 1];
        }
        numDays += day;
      }
      return numDays;
    }

public static java.util.Date BuildUpDateTime(int[] dt){
 long dateMS=GetNumberOfDaysProlepticGregorian(
   dt[0],dt[1]+1,dt[2]);
 dateMS*=86400000L;
 dateMS+=dt[3]*3600000L; // Hours
 dateMS+=dt[4]*60000L;   // Minutes
 dateMS+=dt[5]*1000L;    // Seconds
 dateMS+=dt[6]/1000000;  // Milliseconds
 // Time zone offset in minutes
 dateMS-=dt[7]*60000L;
 return new java.util.Date(dateMS);
}

private static int FloorDiv(int a, int n) {
      return a >= 0 ? a / n : -1 - (-1 - a) / n;
    }

    private static long FloorDiv(long a, long n) {
      return a >= 0 ? a / n : -1 - (-1 - a) / n;
    }

    private static long FloorMod(long a, long n) {
      return a-FloorDiv(a, n)*n;
    }

    private static void GetNormalizedPartProlepticGregorian(
          int year,
                    int month,
                    long day,
                    int[] dest) {
      int divResult;
      divResult = FloorDiv((month - 1), 12);
      year += divResult;
      month = ((month - 1) - 12 * divResult) + 1;
      int[] dayArray = ((year & 3) != 0 || (year % 100 == 0 &&
        year % 400 != 0)) ? normalDays: leapDays;
      if (day > 101) {
        long count = (day - 100) / 146097;
        day -= count * 146097;
        year = (int)(year + count * 400);
      }
      while (true) {
        int days = dayArray[month];
        if (day > 0 && day <= days) {
          break;
        }
        if (day > days) {
          day -= days;
          if (month == 12) {
            month = 1;
            ++year;
            dayArray = ((year & 3) != 0 || (
                    year % 100 == 0 && year % 400 != 0)) ? normalDays :
              leapDays;
          } else {
            ++month;
          }
        }
        if (day <= 0) {
          divResult = FloorDiv((month - 2), 12);
          year += divResult;
          month = ((month - 2) - 12 * divResult) + 1;
          dayArray = ((year & 3) != 0 || (year % 100 == 0 && year
            % 400 != 0)) ? normalDays: leapDays;
          day += dayArray[month];
        }
      }
      dest[0]=year;
      dest[1]=month;
 dest[2]=(int)day;
    }

public static int[] BreakDownDateTime(java.util.Date jdate){
      long date=jdate.getTime();
      long days = FloorDiv(date, 86400000L) + 1;
      int[] ret = new int[8];
      GetNormalizedPartProlepticGregorian(1970, 1, days, ret);
      ret[3]=(int)(FloorMod(date, 86400000L) / 3600000L);
      ret[4]=(int)(FloorMod(date, 3600000L) / 60000L);
      ret[5]=(int)(FloorMod(date, 60000L) / 1000L);
      ret[6]=((int)FloorMod(date, 1000L))*1000000;
      ret[7]=0;
      return ret;
}

}
