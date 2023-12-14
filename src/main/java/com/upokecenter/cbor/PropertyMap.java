package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/
 */

import java.io.InputStream;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.*;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private final String name;
    private final Member method;
    private final String adjustedName;
    private final String adjustedNameCamelCase;
    public MethodData(String name, Member method) {
        this.name=name;
        this.method=method;
        this.adjustedName=GetAdjustedNameInternal(false);
        this.adjustedNameCamelCase=GetAdjustedNameInternal(true);
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
    private String GetAdjustedName(boolean useCamelCase){
       return useCamelCase ? this.adjustedNameCamelCase : this.adjustedName;
    }
    private String GetAdjustedNameInternal(boolean useCamelCase){
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

  // NOTE: Must be HashMap, not TreeMap, since classes are not comparable.
  // HashMap is fine here since nothing here cares about key order for this map
  private static Map<Class<?>, List<MethodData>> propertyLists =
      new HashMap<Class<?>, List<MethodData>>();

  // NOTE: Must be HashMap, not TreeMap, since classes are not comparable.
  // HashMap is fine here since nothing here cares about key order for this map
  private static Map<Class<?>, List<MethodData>> setterPropertyList =
      new HashMap<Class<?>, List<MethodData>>();

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

public static <TKey, TValue> TValue GetOrDefault(Map<TKey, TValue> map,
    TKey key, TValue defaultValue){
  return map.getOrDefault(key, defaultValue);
}

  private static List<MethodData> GetPropertyList(final Class<?> t, boolean setters) {
    synchronized(setters ? setterPropertyList : propertyLists) {
      List<MethodData> ret;
      ret = (setters ? setterPropertyList : propertyLists).get(t);
      if (ret != null) {
        return ret;
      }
      ret = new ArrayList<MethodData>();
      if (IsProblematicForSerialization(t)) {
         ret.add(null);
         (setters ? setterPropertyList : propertyLists).put(t, ret);
         return ret;
      }
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

  public static <K, V> boolean
         DictRemove(Map<K, V> dict, K key) {
      // NOTE: No ambiguity since this method will
      // only ever be called on maps that don't allow raw nulls
      return dict.remove(key)!=null;
  }

  public static <K, V> Collection<K>
         ReadOnlyKeys(Map<K, V> dict) {
      return Collections.unmodifiableCollection(dict.keySet());
  }

  public static <K, V> Collection<V>
         ReadOnlyValues(Map<K, V> dict) {
      return Collections.unmodifiableCollection(dict.values());
  }

  private static final class LinkedListKeySet<TKey>
     extends AbstractSet<TKey> {
     private final LinkedList list;
     public LinkedListKeySet(LinkedList<TKey> list){
       this.list=list;
     }
     public int size() {
        return this.list.size();
     }
     public Iterator<TKey> iterator() {
        return this.list.iterator();
     }
  }

  public static final class OrderedMapSet<TKey, TValue>
       extends AbstractSet<Map.Entry<TKey, TValue>> {
      private final Map<TKey, TValue> dict;
      private final LinkedList<TKey> list;
      public OrderedMapSet(LinkedList<TKey> list, Map<TKey, TValue> dict){
       this.list=list;
       this.dict=dict;
      }
      public Iterator<Map.Entry<TKey, TValue>> iterator() {
        return new OrderedMapIterator<TKey, TValue>(list.iterator(), dict);
      }
      public int size() {
        return list.size();
      }
  }

  public static final class OrderedMapIterator<TKey, TValue>
        implements Iterator<Map.Entry<TKey, TValue>> {
      private final Map<TKey, TValue> dict;
      private final Iterator<TKey> iter;
      public OrderedMapIterator(Iterator<TKey> iter, Map<TKey, TValue> dict){
       this.iter=iter;
       this.dict=dict;
      }
      public boolean hasNext() {
        return this.iter.hasNext();
      }
      public Map.Entry<TKey, TValue> next() {
        TKey k=iter.next();
        return new AbstractMap.SimpleImmutableEntry<TKey, TValue>(k,dict.get(k));
      }
      public void remove() {
        this.iter.remove();
      }
  }

  public static final class OrderedMap<TKey, TValue>
        implements Map<TKey, TValue> {
      private final SortedMap<TKey, TValue> dict;
      private final LinkedList<TKey> list;
      public OrderedMap() {
        this.dict = new TreeMap<TKey, TValue>();
        this.list = new LinkedList<TKey>();
      }
      public Set<Map.Entry<TKey, TValue>> entrySet() {
        return new OrderedMapSet(list,dict);
      }
      public Set<TKey> keySet() {
        return new LinkedListKeySet(list);
      }
      public Set<TKey> sortedKeys() {
        return dict.keySet();
      }
      public void clear() {
         this.list.clear();
         this.dict.clear();
      }
      public boolean containsKey(Object k) {
         return this.dict.containsKey(k);
      }
      public boolean containsValue(Object v) {
         return this.dict.containsValue(v);
      }
      public boolean equals(Object v) {
         return this.dict.equals(v);
      }
      public int hashCode() {
         return this.dict.hashCode();
      }
      public boolean isEmpty() {
         return this.dict.isEmpty();
      }
      public TValue remove(Object k) {
         TValue ret=this.dict.remove(k);
         this.list.remove(k);
         return ret;
      }
      public int size() {
         return this.dict.size();
      }
      public String toString() {
         return this.dict.toString();
      }
      public Collection<TValue> values() {
         List<TValue> ret=new ArrayList<TValue>();
         for(Map.Entry<TKey,TValue> entry : this.entrySet()){
           ret.add(entry.getValue());
         }
         return ret;
      }
      public TValue get(Object k) {
         return this.dict.get(k);
      }
      public TValue put(TKey k, TValue v) {
         if (this.containsKey(k)){
            return this.dict.put(k,v);
         } else {
            TValue ret=this.dict.put(k,v);
            this.list.add(k);
            return ret;
         }
      }
      public void putAll(Map<? extends TKey,? extends TValue> m) {
         for(TKey k : m.keySet()){
            this.put(k,m.get(k));
         }
      }
  }

  public static Map<CBORObject,CBORObject> NewOrderedDict() {
      return new OrderedMap<CBORObject,CBORObject>();
  }

    public static <TKey, TValue> Collection<TKey> GetSortedKeys(
      Map<TKey, TValue> dict) {
      if (dict instanceof OrderedMap<?, ?>) {
        return ((OrderedMap<TKey, TValue>)dict).sortedKeys();
      }
      if (dict instanceof SortedMap<?, ?>) {
        return ((SortedMap<TKey, TValue>)dict).keySet();
      }
      throw new IllegalStateException("Internal error: Map doesn't" +
"\u0020support sorted keys");
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
   if(arr instanceof int[]) {
     int[] iarr=(int[])arr;
     if(mapper==null && options==null) {
       for(int i = 0;i < length;i++) {
        obj.Add(CBORObject.FromObject((int)iarr[i]));
       }
     } else {
     for(int i = 0;i < length;i++) {
      obj.Add(CBORObject.FromObject((int)iarr[i], options,
       mapper, depth+1));
     }
     }
     return obj;
   }
   if(arr instanceof Integer[]) {
     Integer[] iarr=(Integer[])arr;
     if(mapper==null && options==null) {
       for(int i = 0;i < length;i++) {
        obj.Add(CBORObject.FromObject((int)iarr[i]));
       }
     } else {
     for(int i = 0;i < length;i++) {
      obj.Add(CBORObject.FromObject((int)iarr[i], options,
       mapper, depth+1));
     }
     }
     return obj;
   }
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

  public static Iterable<Map.Entry<String, Object>> GetProperties(
       final Object o, boolean useCamelCase) {
      List<MethodData> list=GetPropertyList(o.getClass());
      if(list.size()==1 && list.get(0)==null) {
         // problematic for serialization
         return new ArrayList<Map.Entry<String, Object>>();
      }
      List<Map.Entry<String, Object>> ret =
        new ArrayList<Map.Entry<String, Object>>(list.size());
      for(MethodData key : list) {
        ret.add(new AbstractMap.SimpleEntry<String, Object>(
            key.GetAdjustedName(useCamelCase),
            key.GetValue(o)));
      }
      return ret;
  }

  public static Object FindOneArgumentMethod(final Class<?> objClass, final String name, final Type argtype) {
    if(!(argtype instanceof Class<?>)) {
      //System.out.println("Not a class: "+argtype);
      return null;
    }
    if(objClass==null)return null;
    //System.out.println("Finding one arg method "+name+" "+argtype);
    try {
      //System.out.println("Finding one arg method "+Arrays.toString(objClass.getMethods()));
      return objClass.getMethod(name, (Class<?>)argtype);
    } catch (SecurityException e) {
      //System.out.println("Security exception");
      return null;
    } catch (NoSuchMethodException e) {
      //System.out.println("No such method exception "+obj);
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

    public static CBORObject FromObjectOther(Object obj) {
      if (obj instanceof BigDecimal) {
        // TODO: Avoid going through EDecimal
        BigDecimal bd = ((BigDecimal)obj);
        EInteger ei = EInteger.FromBytes(bd.unscaledValue().toByteArray(),false);
        int iscale=bd.scale();
        if(iscale==Integer.MIN_VALUE) {
           long longscale=-((long)iscale);
           return CBORObject.NewArray(
                CBORObject.FromObject(longscale),
                CBORObject.FromObject(ei)).WithTag(4);
        } else {
           return CBORObject.NewArray(
                CBORObject.FromObject(-iscale),
                CBORObject.FromObject(ei)).WithTag(4);
        }
      }
      if (obj instanceof BigInteger) {
        // TODO: Avoid going through EInteger
        BigInteger bi = ((BigInteger)obj);
        EInteger ei = EInteger.FromBytes(bi.toByteArray(),false);
        return CBORObject.FromObject(ei);
      }
      return null;
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
//System.out.println(name);
if(name.startsWith("com.")) {
if(name.startsWith("com.sun.rowset") ||
   name.startsWith("com.sun.org.apache.") ||
   name.startsWith("com.sun.jndi.") ||
   name.startsWith("com.mchange.v2.c3p0.")) {
     return true;
   }
} else {
if(name.startsWith("org.springframework.") ||
   name.startsWith("java.io.") ||
   name.startsWith("java.lang.annotation.") ||
   name.startsWith("java.security.SignedObject") ||
   name.startsWith("org.apache.xalan.") ||
   name.startsWith("org.apache.xpath.") ||
   name.startsWith("org.codehaus.groovy.") ||
   name.startsWith("groovy.util.Expando") ||
   name.startsWith("java.util.logging.")){
   return true;
}
}
return false;
}

  public static Object TypeToObject(CBORObject objThis, Type t,
     CBORTypeMapper mapper, PODOptions options, int depth) {
      if (t.equals(Byte.class) || t.equals(Byte.TYPE)) {
        return objThis.AsNumber().ToByteChecked();
      }
      if (t.equals(Short.class) || t.equals(Short.TYPE)) {
        return objThis.AsNumber().ToInt16Checked();
      }
      if (t.equals(Integer.class) || t.equals(Integer.TYPE)) {
        return objThis.AsInt32();
      }
      if (t.equals(Long.class) || t.equals(Long.TYPE)) {
        return objThis.AsNumber().ToInt64Checked();
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
if(objThis.isNumber() && objThis.AsNumber().IsInteger() && objThis.AsNumber().CanTruncatedIntFitInInt32()){
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

      if (t.equals(java.math.BigDecimal.class)) {
        if(!objThis.isNumber())throw new CBORException("Not a CBOR number");
        EDecimal ei = objThis.AsNumber().ToEDecimal();
        if (!ei.isFinite()) {
          throw new CBORException("Can't convert to BigDecimal");
        }
        try {
          return new BigDecimal(
              new BigInteger(ei.getMantissa().ToBytes(false)),
              ei.getExponent().Negate().ToInt32Checked());
        } catch(Exception ex) {
          throw new CBORException("Can't convert to BigDecimal", ex);
        }
      }
      if (t.equals(java.math.BigInteger.class)) {
        if(!objThis.isNumber())throw new CBORException("Not a CBOR number");
        EInteger ei = objThis.AsNumber().ToEInteger();
        return new BigInteger(ei.ToBytes(false));
      }

      if (t.equals(EInteger.class)) {
        if(!objThis.isNumber())throw new CBORException("Not a CBOR number");
        return objThis.AsNumber().ToEInteger();
      }
      if (t.equals(EDecimal.class)) {
        if(!objThis.isNumber())throw new CBORException("Not a CBOR number");
        return objThis.AsNumber().ToEDecimal();
      }
      if (t.equals(EFloat.class)) {
        if(!objThis.isNumber())throw new CBORException("Not a CBOR number");
        return objThis.AsNumber().ToEFloat();
      }
      if (t.equals(ERational.class)) {
        if(!objThis.isNumber())throw new CBORException("Not a CBOR number");
        return objThis.AsNumber().ToERational();
      }
      if(t instanceof Class<?> && Enum.class.isAssignableFrom((Class<?>)t)){
if(objThis.getType()==CBORType.TextString){
 try {
  return Enum.valueOf((Class)t,objThis.AsString());
 } catch(Exception ex){
  throw new CBORException(ex.getMessage(),ex);
 }
} else if(objThis.isNumber() && objThis.AsNumber().IsInteger()){
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
ParameterizedType pt=(t instanceof ParameterizedType) ?
   ((ParameterizedType)t) : null;
Type rawType=(pt==null) ? t : pt.getRawType();
Type[] typeArguments=(pt==null) ? null : pt.getActualTypeArguments();
      if (objThis.getType() == CBORType.ByteString) {
        if (t.equals(byte[].class)) {
          byte[] bytes = objThis.GetByteString();
          byte[] byteret = new byte[bytes.length];
          System.arraycopy(bytes, 0, byteret, 0, byteret.length);
          return byteret;
        }
      }
// TODO: Support more output types for bytestrings
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
    rawType.equals(HashMap.class)){
  if(typeArguments==null || typeArguments.length<2){
   HashMap alist=new HashMap();
   for(Map.Entry<CBORObject, CBORObject> entry : objThis.getEntries()){
    CBORObject cbor=entry.getKey();
    CBORObject cborValue=entry.getValue();
    alist.put(cbor.ToObject(Object.class,mapper,options,depth+1),
      cborValue.ToObject(Object.class,mapper,options,depth+1));
   }
   return alist;
  } else {
   HashMap alist=new HashMap();
   for(Map.Entry<CBORObject, CBORObject> entry : objThis.getEntries()){
    CBORObject cbor=entry.getKey();
    CBORObject cborValue=entry.getValue();
    alist.put(cbor.ToObject(typeArguments[0],mapper,options,depth+1),
      cborValue.ToObject(typeArguments[1],mapper,options,depth+1));
   }
   return alist;
  }
 }
 if(rawType!=null &&
    rawType.equals(TreeMap.class) || rawType.equals(Map.class)){
  if(typeArguments==null || typeArguments.length<2){
   TreeMap alist=new TreeMap();
   for(Map.Entry<CBORObject, CBORObject> entry : objThis.getEntries()){
    CBORObject cbor=entry.getKey();
    CBORObject cborValue=entry.getValue();
    alist.put(cbor.ToObject(Object.class,mapper,options,depth+1),
      cborValue.ToObject(Object.class,mapper,options,depth+1));
   }
   return alist;
  } else {
   TreeMap alist=new TreeMap();
   for(Map.Entry<CBORObject, CBORObject> entry : objThis.getEntries()){
    CBORObject cbor=entry.getKey();
    CBORObject cborValue=entry.getValue();
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
        ArrayList<Map.Entry<String, CBORObject>> values =
          new ArrayList<Map.Entry<String, CBORObject>>();
        ArrayList<MethodData> methods=new ArrayList<MethodData>();
        List<MethodData> proplist=GetPropertyList(
             (Class<?>)rawType,true/* getting list of setters*/);
        if(proplist.size()==1 && proplist.get(0)==null){
           // problematic for serialization
          throw new CBORException();
        }
        for (MethodData method : proplist) {
          String key = method.GetAdjustedName(
             options==null || options.getUseCamelCase());
          CBORObject cborValue = objThis.GetOrDefault(key, null);
          if (cborValue != null) {
            Map.Entry<String, CBORObject> dict =
                new AbstractMap.SimpleEntry<String, CBORObject>(
              key,
              cborValue);
            values.add(dict);
            methods.add(method);
          }
        }

      try {
      Class<?> rawClass=(Class<?>)rawType;
      Object o = rawClass.getDeclaredConstructor().newInstance();
      Class<?> realClass=o.getClass();
      if(rawClass==realClass) {
      int index=0;
      for (Map.Entry<String, CBORObject> kv : values) {
          MethodData method=methods.get(index);
          Object dobj = kv.getValue().ToObject(
             method.GetValueType(),mapper,options, depth+1);
          method.SetValue(o, dobj);
          index++;
      }
      } else {
      // System.out.println("class="+rawClass+","+realClass);
      Map<String, CBORObject> dict;
      dict = new HashMap<String, CBORObject>();
      for (Map.Entry<String, CBORObject> kv : values) {
        dict.put(kv.getKey(),kv.getValue());
        if(dict.size()==50) {
           dict=new TreeMap<String, CBORObject>(dict);
        }
      }
         proplist=GetPropertyList(o.getClass(),true/* getting list of setters*/);
        if(proplist.size()==1 && proplist.get(0)==null){
           // problematic for serialization
          throw new CBORException();
        }
              for (MethodData key : proplist) {
        String kname = key.GetAdjustedName(
            options==null || options.getUseCamelCase());
        if (dict.containsKey(kname)) {
          CBORObject dget=dict.get(kname);
          Object dobj = dget.ToObject(
             key.GetValueType(),mapper,options, depth+1);
          key.SetValue(o, dobj);
        }
      }
      }
      return o;
    } catch(IllegalArgumentException ex) {
      throw (RuntimeException)new CBORException("").initCause(ex);
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

    /*
    public static <TKey, TValue> boolean
        TryGetValue(Map<TKey, TValue> map, TKey key,
        TValue[] outvalue) {
        if (map.containsKey(key)) {
          outvalue[0]=map[key];
          return true;
        }
        return false;
    }*/

    @SuppressWarnings("unchecked")
    public static Object CallFromObject(
       CBORTypeMapper.ConverterInfo convinfo,
       CBORObject obj) {
      if(convinfo.getConverter() instanceof ICBORToFromConverter<?>) {
        return ((ICBORToFromConverter<?>)convinfo.getConverter()).FromCBORObject(obj);
      }
      return null;
    }

    // Fractional seconds divided by milliseconds
    private static final int TicksDivFracSeconds = CBORUtilities.FractionalSeconds/1000;

    private static long FloorDiv(long longA, int longN) {
      return longA >= 0 ? longA / longN : (-1 - ((-1 - longA) / longN));
    }

    private static long FloorModLong(long longA, int longN) {
        return longA - (FloorDiv(longA, longN) * longN);
    }

   public static void BreakDownDateTime(java.util.Date bi,
        EInteger[] year, int[] lf) {
    if(TicksDivFracSeconds == 0)throw new IllegalStateException();
    long time=bi.getTime();
    int nanoseconds=(int)FloorModLong(time,1000);
    if(time<0 && nanoseconds!=0){
       // nanoseconds=1000-nanoseconds;
    }
    nanoseconds*=TicksDivFracSeconds;
    long seconds=FloorDiv(time,1000);
    CBORUtilities.BreakDownSecondsSinceEpoch(seconds,year,lf);
    lf[5]=nanoseconds;
   }

public static java.util.Date BuildUpDateTime(EInteger year, int[] dt){
 if(TicksDivFracSeconds == 0)throw new IllegalStateException();
 EInteger dateMS=CBORUtilities.GetNumberOfDaysProlepticGregorian(
   year,dt[0],dt[1]).Multiply(EInteger.FromInt32(86400000));
 EInteger frac=EInteger.FromInt32(0);
//System.out.println(dt[2]+","+dt[3]+","+dt[4]+","+dt[5]);
 frac=frac.Add(EInteger.FromInt32(dt[2]*3600000+dt[3]*60000+dt[4]*1000));
 // Milliseconds
 frac=frac.Add(EInteger.FromInt32(dt[5]/TicksDivFracSeconds));
 // Time zone offset in minutes
 frac=frac.Subtract(EInteger.FromInt32(dt[6]*60000));
 dateMS=dateMS.Add(frac);
 if(!dateMS.CanFitInInt64()) {
   throw new CBORException("Value too big or too small for Java Date");
 }
 return new java.util.Date(dateMS.ToInt64Checked());
}

}
