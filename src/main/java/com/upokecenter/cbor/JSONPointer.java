package com.upokecenter.cbor;
/*
Written in 2013-2018 by Peter Occil.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

*/

import java.util.*;

import com.upokecenter.numbers.*;

  final class JSONPointer {
    private final String refValue;
    private final boolean isRoot;
    private final CBORObject jsonobj;

    public static JSONPointer FromPointer(CBORObject obj, String pointer) {
      int index = 0;
      if (pointer == null) {
        throw new NullPointerException("pointer");
      }
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      if (pointer.length() == 0) {
        return new JSONPointer(obj, pointer, true);
      }
      while (true) {
        if (obj == null) {
          throw new CBORException("Invalid pointer: obj is null");
        }
        if (obj.getType() == CBORType.Array) {
          if (index >= pointer.length() || pointer.charAt(index) != '/') {
            throw new CBORException("Invalid pointer");
          }
          ++index;
          int[] value = new int[] { 0 };
          // System.out.println("index parse 0: " + (pointer.substring(index)));
          int newIndex = ReadPositiveInteger(pointer, index, value);
          // System.out.println("index parse 1: " + (pointer.substring(newIndex)));
          if (value[0] < 0) {
            if (index < pointer.length() && pointer.charAt(index) == '-' &&
              (index + 1 == pointer.length() || pointer.charAt(index + 1) == '/')) {
              // Index at the end of the array
              return new JSONPointer(obj, "-");
            }
            throw new CBORException("Invalid pointer");
          }
          if (newIndex == pointer.length()) {
            return new JSONPointer(obj, pointer.substring(index));
          } else if (value[0] > obj.size()) {
            throw new CBORException("Invalid array index in pointer");
          } else if (value[0] == obj.size()) {
            if (!(newIndex + 1 == pointer.length())) {
 throw new CBORException("Invalid array index in pointer");
}
 return new JSONPointer(obj, pointer.substring(index));
          } else {
            obj = obj.get(value[0]);

            index = newIndex;
          }
          index = newIndex;
        } else if (obj.getType() == CBORType.Map) {
          // System.out.println("Parsing map key(0) " + (pointer.substring(index)));
          if (index >= pointer.length() || pointer.charAt(index) != '/') {
            throw new CBORException("Invalid pointer");
          }
          ++index;
          // System.out.println("Parsing map key " + (pointer.substring(index)));
          String key = null;
          int oldIndex = index;
          boolean tilde = false;
          while (index < pointer.length()) {
            int c = pointer.charAt(index);
            if (c == '/') {
              break;
            }
            if (c == '~') {
              tilde = true;
              break;
            }
            ++index;
          }
          if (!tilde) {
            key = pointer.substring(
              oldIndex, (
              oldIndex)+(index - oldIndex));
          } else {
            index = oldIndex;
            StringBuilder sb = new StringBuilder();
            while (index < pointer.length()) {
              int c = pointer.charAt(index);
              if (c == '/') {
                break;
              }
              if (c == '~') {
                if (index + 1 < pointer.length()) {
                  if (pointer.charAt(index + 1) == '1') {
                    index += 2;
                    sb.append('/');
                    continue;
                  } else if (pointer.charAt(index + 1) == '0') {
                    index += 2;
                    sb.append('~');
                    continue;
                  }
                }
                throw new CBORException("Invalid pointer");
              } else {
                sb.append((char)c);
              }
              ++index;
            }
            key = sb.toString();
          }
          if (index == pointer.length()) {
            return new JSONPointer(obj, key);
          } else {
            obj = obj.GetOrDefault(key, null);
            if (obj == null) {
              throw new CBORException("Invalid pointer; key not found");
            }
          }
        } else {
          throw new CBORException("Invalid pointer");
        }
      }
    }
    public static CBORObject GetObject(
      CBORObject obj,
      String pointer,
      CBORObject defaultValue) {
      if (obj == null) {
        throw new CBORException("obj");
      }
      if (pointer == null) {
        return defaultValue;
      }
      if (pointer.length() == 0) {
        return obj;
      }
      if (obj.getType() != CBORType.Array && obj.getType() != CBORType.Map) {
        return defaultValue;
      }
      try {
        CBORObject cobj = JSONPointer.FromPointer(obj, pointer).GetValue();
        return (cobj == null) ? (defaultValue) : cobj;
      } catch (CBORException ex) {
        return defaultValue;
      }
    }

    private static int ReadPositiveInteger(
      String str,
      int index,
      int[] result) {
      boolean haveNumber = false;
      boolean haveZeros = false;
      int oldIndex = index;
      result[0] = -1;
      if (index == str.length()) {
        return index;
      }
      if (str.length() - 1 == index && str.charAt(index) == '0') {
        result[0] = 0;
        return index + 1;
      }
      if (str.length() - 1 > index && str.charAt(index) == '0' && str.charAt(index + 1) !=
'0') {
        result[0] = 0;
        return index + 1;
      }
      if (str.charAt(index) == '0') {
        // NOTE: Leading zeros not allowed in JSON Pointer numbers
        return index;
      }
      long lvalue = 0;
      while (index < str.length()) {
        int number = str.charAt(index++);
        if (number >= '0' && number <= '9') {
          lvalue = (lvalue * 10) + (number - '0');
          haveNumber = true;
          if (haveZeros) {
            return oldIndex + 1;
          }
        } else {
          --index;
          break;
        }
        if (lvalue > Integer.MAX_VALUE) {
          return index - 1;
        }
      }
      if (!haveNumber) {
        return index;
      }
      result[0] = (int)lvalue;
      return index;
    }

    private JSONPointer(CBORObject jsonobj, String refValue) {
 this(jsonobj, refValue, false);
    }

    private JSONPointer(CBORObject jsonobj, String refValue, boolean isRoot) {
      this.isRoot = isRoot;
      this.jsonobj = jsonobj;
      this.refValue = refValue;
    }

    public boolean Exists() {
      if (this.refValue.length() == 0) {
        // Root always exists
        return true;
      }
      if (this.jsonobj.getType() == CBORType.Array) {
        if (this.refValue.equals("-")) {
          return false;
        }
        EInteger eivalue = EInteger.FromString(this.refValue);
        int icount = this.jsonobj.size();
        return eivalue.signum() >= 0 &&
          eivalue.compareTo(EInteger.FromInt32(icount)) < 0;
      } else {
        return this.jsonobj.getType() == CBORType.Map ?
this.jsonobj.ContainsKey(this.refValue) : this.refValue.length() == 0;
      }
    }

    /**
     * Gets an index into the specified object, if the object is an array and is
     * not greater than the array's length.
     * @return The index contained in this instance, or -1 if the object isn't a
     * JSON array or is greater than the array's length.
     */
    public int GetIndex() {
      if (this.jsonobj.getType() == CBORType.Array) {
        if (this.refValue.equals("-")) {
          return this.jsonobj.size();
        }
        EInteger value = EInteger.FromString(this.refValue);
        int icount = this.jsonobj.size();
        return (value.signum() < 0) ? (-1) :
((value.compareTo(EInteger.FromInt32(icount)) > 0) ? (-1) :

            value.ToInt32Unchecked());
      } else {
        return -1;
      }
    }

    public String GetKey() {
      return this.refValue;
    }

    public CBORObject GetParent() {
      return this.jsonobj;
    }

    public CBORObject GetValue() {
      if (this.isRoot) {
        // Root always exists
        return this.jsonobj;
      }
      CBORObject tmpcbor;
      if (this.jsonobj.getType() == CBORType.Array) {
        int index = this.GetIndex();
        if (index >= 0 && index < this.jsonobj.size()) {
          tmpcbor = this.jsonobj;
          return tmpcbor.get(index);
        } else {
          return null;
        }
      } else if (this.jsonobj.getType() == CBORType.Map) {
        // System.out.println("jsonobj=" + this.jsonobj + " refValue=[" + this.refValue
        // + "]");
        tmpcbor = this.jsonobj;
        return tmpcbor.GetOrDefault(this.refValue, null);
      } else {
        return (this.refValue.length() == 0) ? this.jsonobj : null;
      }
    }

    /**
     * <p>Gets all children of the specified JSON object that contain the specified
     * key; the method will remove matching keys. As an example, consider this
     * object: </p><pre>[{"key":"value1","foo":"foovalue"},
     * {"key":"value2","bar":"barvalue"}, {"baz":"bazvalue"}]</pre> If
     * getPointersToKey is called on this object with a keyToFind called "key", we
     * get the following Map as the return value: <pre>{ "/0" =&gt; "value1", //
     * "/0" points to {"foo":"foovalue"} "/1" =&gt; "value2" /* "/1" points to
     * {"bar":"barvalue"} &#x2a;&#x2f; }</pre> and the JSON object will change to the
     * following: <pre>[{"foo":"foovalue"}, {"bar":"barvalue"},
     * {"baz","bazvalue"}]</pre>.
     * @param root The object to search.
     * @param keyToFind The key to search for.
     * @return A map: The JSON Pointers are relative to the root object.
     * @throws NullPointerException The parameter {@code root} is null.
     */
    public static Map<String, CBORObject> GetPointersWithKeyAndRemove(
      CBORObject root,
      String keyToFind) {
      Map<String, CBORObject> list = new HashMap<String, CBORObject>();
      if (root == null) {
        throw new NullPointerException("root");
      }
      GetPointersWithKey(root, keyToFind, "", list, true);
      return list;
    }

    /**
     * <p>Gets all children of the specified JSON object that contain the specified
     * key; the method will not remove matching keys. As an example, consider this
     * object: </p><pre>[{"key":"value1","foo":"foovalue"},
     * {"key":"value2","bar":"barvalue"}, {"baz":"bazvalue"}]</pre> If
     * getPointersToKey is called on this object with a keyToFind called "key", we
     * get the following Map as the return value: <pre>{ "/0" =&gt; "value1", //
     * "/0" points to {"key":"value1","foo":"foovalue"} "/1" =&gt; "value2" // "/1"
     * points to {"key":"value2","bar":"barvalue"} }</pre> and the JSON object will
     * remain unchanged. <ul> <li>The keys in the map are JSON Pointers to the
     * objects within <i>root</i> that contained a key named <i>keyToFind</i>. To
     * get the actual JSON object, call JSONPointer.GetObject, passing <i>root</i>
     * and the pointer as arguments.</li><li>The values in the map are the values
     * of each of those keys named <i>keyToFind</i>.</li></ul> The JSON Pointers
     * are relative to the root object.
     * @param root object to search.
     * @param keyToFind The key to search for.
     * @return A map:.
     * @throws NullPointerException The parameter {@code root} is null.
     */
    public static Map<String, CBORObject> GetPointersWithKey(
      CBORObject root,
      String keyToFind) {
      Map<String, CBORObject> list = new HashMap<String, CBORObject>();
      if (root == null) {
        throw new NullPointerException("root");
      }
      GetPointersWithKey(root, keyToFind, "", list, false);
      return list;
    }

    private static String Replace(String str, char c, String srep) {
      int j = -1;
      for (int i = 0; i < str.length(); ++i) {
        if (str.charAt(i) == c) {
          j = i;
          break;
        }
      }
      if (j == -1) {
        return str;
      }
      StringBuilder sb = new StringBuilder();
      sb.append(str.substring(0, j));
      sb.append(srep);
      for (int i = j + 1; i < str.length(); ++i) {
        sb = str.charAt(i) == c ? sb.append(srep) : sb.append(str.charAt(i));
      }
      return sb.toString();
    }

    private static void GetPointersWithKey(
      CBORObject root,
      String keyToFind,
      String currentPointer,
      Map<String, CBORObject> pointerList,
      boolean remove) {
      if (root.getType() == CBORType.Map) {
        CBORObject rootObj = root;
        if (rootObj.ContainsKey(keyToFind)) {
          // Key found in this object,
          // add this object's JSON pointer
          CBORObject pointerKey = rootObj.get(keyToFind);
          pointerList.put(currentPointer, pointerKey);
          // and remove the key from the Object
          // if necessary
          if (remove) {
            rootObj.Remove(CBORObject.FromString(keyToFind));
          }
        }
        // Search the key's values
        for (CBORObject key : rootObj.getKeys()) {
          String ptrkey = key.AsString();
          ptrkey = Replace(ptrkey, '~', "~0");
          ptrkey = Replace(ptrkey, '/', "~1");
          GetPointersWithKey(
            rootObj.get(key),
            keyToFind,
            currentPointer + "/" + ptrkey,
            pointerList,
            remove);
        }
      } else if (root.getType() == CBORType.Array) {
        for (int i = 0; i < root.size(); ++i) {
          String ptrkey = EInteger.FromInt32(i).toString();
          GetPointersWithKey(
            root.get(i),
            keyToFind,
            currentPointer + "/" + ptrkey,
            pointerList,
            remove);
        }
      }
    }
  }
