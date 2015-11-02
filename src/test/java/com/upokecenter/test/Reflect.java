package com.upokecenter.test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility methods for accessing internal APIs via reflection.
 */
public final class Reflect {
  private Reflect() {
  }

  public static Object construct(Class<?> clazz, Object... args){
    Constructor<?> con=null;
    if(clazz==null)return null;
    for(Constructor<?> c : clazz.getDeclaredConstructors()){
      int paramLength=c.getParameterTypes().length;
      if((paramLength==args.length || (c.isVarArgs() && paramLength>=args.length+1))){
        if(con==null) {
          con=c;
        } else
          return null; // ambiguous
      }
    }
    if(con!=null){
      return newInstance(con,args);
    }
    for(Constructor<?> c : clazz.getConstructors()){
      int paramLength=c.getParameterTypes().length;
      if((paramLength==args.length || (c.isVarArgs() && paramLength>=args.length+1))){
        if(con==null) {
          con=c;
        } else
          return null; // ambiguous
      }
    }
    if(con==null)return null;
    return newInstance(con,args);
  }
  public static Object construct(String className, Object... args){
    return construct(getClassForName(className),args);
  }
  public static Object constructWithTypes(Class<?> clazz, Class<?>[] parameterTypes, Object... args){
    if(parameterTypes.length!=args.length)
      throw new IllegalArgumentException();
    if(clazz==null)return null;
    try {
      Constructor<?> con=clazz.getConstructor(parameterTypes);
      if(con==null)return null;
      return newInstance(con,args);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }
  public static Object constructWithTypes(String className, Class<?>[] parameterTypes, Object... args){
    return constructWithTypes(getClassForName(className),parameterTypes,args);
  }
  public static Class<?> getClassForName(String name){
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }
  public static Object getField(Object obj, Field method, Object defaultValue){
    Object ret[]=new Object[]{defaultValue};
    if(!getFieldWithTest(obj,method,ret))
      return defaultValue;
    return ret[0];
  }
  public static Object getFieldByName(
      Object obj, String name, Object defaultValue){
    return getField(obj,getStaticFieldInfo(obj.getClass(),name),defaultValue);
  }
  public static boolean getFieldByNameWithTest(
      Object obj, String name, Object[] retvalue){
    return getFieldWithTest(obj,getStaticFieldInfo(obj.getClass(),name),retvalue);
  }
  public static Field getFieldInfo(Object obj, String name){
    return getStaticFieldInfo(obj.getClass(),name);
  }
  public static Field[] getFieldInfos(Object obj){
    return getStaticFieldInfos(obj.getClass());
  }
  public static boolean getFieldWithTest(
      Object obj, Field field, Object[] retvalue){
    if(retvalue!=null && retvalue.length>0){
      retvalue[0]=null;
    }
    if(field==null)
      return false;
    try {
      try {
        field.setAccessible(true);
      } catch(SecurityException e){
        // Not fatal if the field is public
      }
      Object ret=field.get(obj);
      if(retvalue!=null && retvalue.length>0){
        retvalue[0]=ret;
      }
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } catch (IllegalAccessException e) {
      return false;
    }
  }
  public static Method getMethod(Object obj, String name){
    return getStaticMethod(obj.getClass(),name);
  }
  public static Method getMethodWithTypes(Object obj, String name, Class<?>... types){
    return getStaticMethodWithTypes(obj.getClass(),name,types);
  }
  public static Object getStaticFieldByName(
      Class<?> clazz, String name, Object defaultValue){
    return getField(null,getStaticFieldInfo(clazz,name),defaultValue);
  }
  public static Object getStaticFieldByName(
      String className, String name, Object defaultValue){
    return getField(null,getStaticFieldInfo(getClassForName(className),name),defaultValue);
  }
  public static boolean getStaticFieldByNameWithTest(
      Class<?> clazz, String name, Object[] retvalue){
    return getFieldWithTest(null,getStaticFieldInfo(clazz,name),retvalue);
  }
  public static boolean getStaticFieldByNameWithTest(
      String className, String name, Object[] retvalue){
    return getFieldWithTest(null,getStaticFieldInfo(getClassForName(className),name),retvalue);
  }
  public static Field getStaticFieldInfo(Class<?> clazz, String name){
    if(clazz==null)return null;
    try {
      return clazz.getField(name);
    } catch (NoSuchFieldException e) {
      return null;
    }
  }
  public static Field[] getStaticFieldInfos(Class<?> clazz){
    if(clazz==null)return new Field[0];
    return clazz.getFields();
  }
  public static Method getStaticMethod(Class<?> clazz, String name){
    if(clazz==null)return null;
    Method con=null;
    for(Method c : clazz.getDeclaredMethods()){
      if(c.getName().equals(name)){
        if(con==null) {
          con=c;
        } else
          return null; // ambiguous
      }
    }
    if(con!=null){
      return con;
    }
    for(Method c : clazz.getMethods()){
      if(c.getName().equals(name)){
        if(con==null) {
          con=c;
        } else
          return null; // ambiguous
      }
    }
    return con;
  }
  private static Method getStaticMethod(Class<?> clazz, String name, int argCount){
    if(clazz==null)return null;
    Method con=null;
    for(Method c : clazz.getDeclaredMethods()){
      int paramLength=c.getParameterTypes().length;
      if((paramLength==argCount || (c.isVarArgs() && paramLength>=argCount+1)) &&
          c.getName().equals(name)){
        if(con==null) {
          con=c;
        } else
          return null; // ambiguous
      }
    }
    if(con!=null){
      return con;
    }
    for(Method c : clazz.getMethods()){
      int paramLength=c.getParameterTypes().length;
      if((paramLength==argCount || (c.isVarArgs() && paramLength>=argCount+1)) &&
          c.getName().equals(name)){
        if(con==null) {
          con=c;
        } else
          return null; // ambiguous
      }
    }
    return con;
  }
  public static Method getStaticMethodWithTypes(Class<?> clazz, String name, Class<?>... types){
    try {
      if(clazz==null)return null;
      return clazz.getMethod(name,types);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }
  public static Object invoke(Object obj, Method method, Object defaultValue, Object... args){
    Object ret[]=new Object[]{defaultValue};
    if(!invokeWithTest(obj,method,ret,args))
      return defaultValue;
    return ret[0];
  }
  public static Object invokeByName(
      Object obj, String name, Object defaultValue, Object... args){
    return invoke(obj,getStaticMethod(obj.getClass(),name,args.length),defaultValue,args);
  }
  public static boolean invokeByNameWithTest(
      Object obj, String name,
      Object[] retvalue, Object... args){
    return invokeWithTest(obj,getStaticMethod(obj.getClass(),name,args.length),retvalue,args);
  }
  public static Object invokeByNameWithTypes(
      Object obj, String name, Class<?>[] types, Object defaultValue, Object... args){
    return invoke(obj,getStaticMethodWithTypes(obj.getClass(),name,types),defaultValue,args);
  }

  public static Object invokeStaticByName(
      Class<?> clazz, String name, Object defaultValue, Object... args){
    return invoke(null,getStaticMethod(clazz,name,args.length),defaultValue,args);
  }

  public static boolean invokeStaticByNameWithTest(
      Class<?> clazz, String name,
      Object[] retvalue, Object... args){
    return invokeWithTest(clazz,getStaticMethod(clazz,name,args.length),retvalue,args);
  }

  public static Object invokeStaticByNameWithTypes(
      Class<?> clazz, String name, Class<?>[] types, Object defaultValue, Object... args){
    return invoke(null,getStaticMethodWithTypes(clazz,name,types),defaultValue,args);
  }
  public static boolean invokeWithTest(
      Object obj, Method method,
      Object[] retvalue, Object... args){
    if(retvalue!=null && retvalue.length>0){
      retvalue[0]=null;
    }
    if(method==null){
      return false;
    }
    try {
      try {
        method.setAccessible(true);
      } catch(SecurityException e){
        // Not fatal if the method is public
      }
      Object ret=method.invoke(obj,args);
      if(retvalue!=null && retvalue.length>0){
        retvalue[0]=ret;
      }
      return true;
    } catch (IllegalAccessException e) {
      return false;
    } catch (InvocationTargetException e) {
      Throwable cause=e.getCause();
      if(cause instanceof Error)
        throw (Error)cause;
      else if(cause instanceof RuntimeException)
        throw (RuntimeException)cause;
      else
        throw new RuntimeException(cause);
    }
  }
  private static Object newInstance(Constructor<?> con, Object... args){
    if(con==null)return null;
    try {
      try {
        con.setAccessible(true);
      } catch(SecurityException e){
        // Not fatal if the constructor is public
      }
      return con.newInstance(args);
    } catch (InstantiationException e) {
      return null;
    } catch (IllegalAccessException e) {
      return null;
    } catch (InvocationTargetException e) {
      Throwable cause=e.getCause();
      if(cause instanceof Error)
        throw (Error)cause;
      else if(cause instanceof RuntimeException)
        throw (RuntimeException)cause;
      else
        throw new RuntimeException(cause);
    }
  }

  private static Map<String, Class<?>> typeCache = new HashMap<String, Class<?>>();

  private static Class<?> FindType(String type) {
    synchronized(typeCache){
      if (typeCache.containsKey(type)) {
        return typeCache.get(type);
      }
      Class<?> ret = getClassForName(type);
      if(ret != null){
        typeCache.put(type, ret);
      }
      return ret;
    }
  }

  public static Object Construct(String type, Object... parameters) {
    return construct(FindType(type), parameters);
  }
  public static Object GetMethod(Object obj, String name) {
    return getMethod(obj,  name);
  }
  private static Object GetMethodExtended(Class<?> type, String name, boolean staticMethod, int parameterCount) {
    return getStaticMethod(type, name, parameterCount);
  }
  public static Object InvokeMethod(Object obj, Object method, Object... parameters) {
    return invoke(obj, (Method)method, null, parameters);
  }
  public static Object Invoke(Object obj, String name, Object... parameters) {
    return InvokeMethod(obj, GetMethodExtended(obj.getClass(), name, false, parameters.length), parameters);
  }
  public static Object InvokeStatic(String type, String name, Object... parameters) {
    return InvokeMethod(null, GetMethodExtended(FindType(type), name, true, parameters.length), parameters);
  }
  public static Object GetProperty(Object obj, String name) {
    return Invoke(obj, "get"+name);
  }
  public static Object GetPropertyStatic(String type, String name) {
    return InvokeStatic(type, "get"+name);
  }
  public static Object GetField(Object obj, String name) {
    return Reflect.getFieldByName(obj, name, null);
  }
  public static Object GetFieldStatic(String type, String name) {
    return Reflect.getStaticFieldByName(type, name, null);
  }
}
