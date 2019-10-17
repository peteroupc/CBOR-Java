package com.upokecenter.test;
/*
Written in 2013-2018 by Peter Occil.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/

If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
*/

import java.util.*;

import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  public final class JSONPointer {
    public static JSONPointer FromPointer(CBORObject obj, String pointer) {
      int index = 0;
      if (pointer == null) {
        throw new NullPointerException("pointer");
      }
      if (pointer.length() == 0) {
        return new JSONPointer(obj, pointer);
      }
      while (true) {
        if (obj == null) {
          throw new NullPointerException("obj");
        }
        if (obj.getType() == CBORType.Array) {
          if (index >= pointer.length() || pointer.charAt(index) != '/') {
            throw new IllegalArgumentException(pointer);
          }
          ++index;
          int[] value = new int[] { 0 };
          int newIndex = ReadPositiveInteger(pointer, index, value);
          if (value[0] < 0) {
            if (index < pointer.length() && pointer.charAt(index) == '-' &&
              (index + 1 == pointer.length() || pointer.charAt(index + 1) == '/')) {
              // Index at the end of the array
              return new JSONPointer(obj, "-");
            }
            throw new IllegalArgumentException(pointer);
          }
          if (newIndex == pointer.length()) {
            return new JSONPointer(obj, pointer.substring(index));
          } else {
            obj = obj.get(value[0]);
            index = newIndex;
          }
          index = newIndex;
        } else if (obj.getType() == CBORType.Map) {
          if (obj.equals(CBORObject.Null)) {
            throw new NoSuchElementException(pointer);
          }
          if (index >= pointer.length() || pointer.charAt(index) != '/') {
            throw new IllegalArgumentException(pointer);
          }
          ++index;
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
                throw new IllegalArgumentException(pointer);
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
            obj = ((CBORObject)obj).get(key);
          }
        } else {
          throw new NoSuchElementException(pointer);
        }
      }
    }

    /**
     * Gets the JSON object referred to by a JSON Pointer according to RFC6901. The
     * syntax for pointers is: <pre>'/' KEY '/' KEY.get(...)</pre> where
     * KEY represents a key into the JSON object or its sub-objects in the
     * hierarchy. For example, <pre>/foo/2/bar</pre> means the same as
     *  <pre>obj.get('foo')[2]['bar']</pre> in JavaScript. If "~" and/or "/"
     *  occurs in a key, it must be escaped with "~0" or "~1", respectively,
     * in a JSON pointer.
     * @param obj A CBOR object.
     * @param pointer A JSON pointer according to RFC 6901.
     * @return An object within the specified JSON object, or {@code obj} if
     * pointer is the empty string, if the pointer is null, if the pointer
     * is invalid , if there is no JSON object at the given pointer, or if
     * {@code obj} is not of type CBORObject, unless pointer is the empty
     * string.
     * @throws NullPointerException The parameter {@code pointer} is null.
     */
    public static Object GetObject(CBORObject obj, String pointer) {
      if (pointer == null) {
        throw new NullPointerException("pointer");
      }
      return (pointer.length() == 0) ? obj :
        JSONPointer.FromPointer(obj, pointer).GetValue();
    }

    private static int ReadPositiveInteger(
      String str,
      int index,
      int[] result) {
      boolean haveNumber = false;
      boolean haveZeros = false;
      int oldIndex = index;
      result[0] = -1;
      while (index < str.length()) { // skip zeros
        int c = str.charAt(index++);
        if (c != '0') {
          --index;
          break;
        }
        if (haveZeros) {
          --index;
          return index;
        }
        haveNumber = true;
        haveZeros = true;
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

    private String refValue;

    private CBORObject jsonobj;

    private JSONPointer(CBORObject jsonobj, String refValue) {
      this.jsonobj = jsonobj;
      this.refValue = refValue;
    }

    public boolean Exists() {
      if (this.jsonobj.getType() == CBORType.Array) {
        if (this.refValue.equals("-")) {
          return false;
        }
        EInteger eivalue = EInteger.FromString(this.refValue);
        int icount = ((CBORObject)this.jsonobj).size();
        return eivalue.signum() >= 0 &&
          eivalue.compareTo(EInteger.FromInt32(icount)) < 0;
        } else if (this.jsonobj.getType() == CBORType.Map) {
          return ((CBORObject)this.jsonobj).ContainsKey(this.refValue);
        } else {
        return this.refValue.length() == 0;
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
          return ((CBORObject)this.jsonobj).size();
        }
        EInteger value = EInteger.FromString(this.refValue);
        int icount = ((CBORObject)this.jsonobj).size();
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
      if (this.refValue.length() == 0) {
        return this.jsonobj;
      }
      CBORObject tmpcbor = null;
      if (this.jsonobj.getType() == CBORType.Array) {
        int index = this.GetIndex();
        if (index >= 0 && index < ((CBORObject)this.jsonobj).size()) {
          tmpcbor = this.jsonobj;
          return tmpcbor.get(index);
        } else {
          return null;
        }
      } else if (this.jsonobj.getType() == CBORType.Map) {
        tmpcbor = this.jsonobj;
        return tmpcbor.get(this.refValue);
      } else {
        return (this.refValue.length() == 0) ? this.jsonobj : null;
      }
    }

    /**
     * Gets all children of the specified JSON object that contain the specified
     * key. The method will not remove matching keys. As an example,
     *  consider this object: <pre>[{"key":"value1","foo":"foovalue"}, {"key":"value2","bar":"barvalue"}, {"baz":"bazvalue"}]</pre> If
     * getPointersToKey is called on this object with a keyToFind called
     *  "key", we get the following Map as the return value: <pre>{ "/0" => "value1", // "/0" points to {"foo":"foovalue"} "/1" => "value2" // "/1" points to {"bar":"barvalue"} }</pre> and the JSON object will
     *  change to the following: <pre>[{"foo":"foovalue"}, {"bar":"barvalue"}, {"baz","bazvalue"}]</pre> @param root object to
     * search @param keyToFind the key to search for. @return a map: <ul>
     * <li>The keys in the map are JSON Pointers to the objects within
     * <i>root</i> that contained a key named <i>keyToFind</i>. To get the
     * actual JSON object, call JSONPointer.GetObject, passing <i>root</i>
     * and the pointer as arguments.</li> <li>The values in the map are the
     * values of each of those keys named <i>keyToFind</i>.</li></ul> The
     * JSON Pointers are relative to the root object.
     * @param root The parameter {@code root} is not documented yet.
     * @param keyToFind The parameter {@code keyToFind} is not documented yet.
     * @return An Map(string, object) object.
     * @throws NullPointerException The parameter {@code root} is null.
     */
    public static Map<String, Object> GetPointersWithKeyAndRemove(
      CBORObject root,
      String keyToFind) {
      Map<String, Object> list = new HashMap<String, Object>();
      if (root == null) {
        throw new NullPointerException("root");
      }
      GetPointersWithKey(root, keyToFind, "", list, true);
      return list;
    }

    /**
     * Gets all children of the specified JSON object that contain the specified
     * key. The method will remove matching keys. As an example, consider
     *  this object: <pre>[{"key":"value1","foo":"foovalue"}, {"key":"value2","bar":"barvalue"}, {"baz":"bazvalue"}]</pre> If
     * getPointersToKey is called on this object with a keyToFind called
     *  "key", we get the following Map as the return value: <pre>{ "/0" => "value1", // "/0" points to {"key":"value1","foo":"foovalue"} "/1" => "value2" // "/1" points to {"key":"value2","bar":"barvalue"} }</pre> and the JSON object will remain unchanged. @param root
     * object to search @param keyToFind the key to search for. @return a
     * map: <ul> <li>The keys in the map are JSON Pointers to the objects
     * within <i>root</i> that contained a key named <i>keyToFind</i>. To
     * get the actual JSON object, call JSONPointer.GetObject, passing
     * <i>root</i> and the pointer as arguments.</li> <li>The values in the
     * map are the values of each of those keys named
     * <i>keyToFind</i>.</li></ul> The JSON Pointers are relative to the
     * root object.
     * @param root The parameter {@code root} is not documented yet.
     * @param keyToFind The parameter {@code keyToFind} is not documented yet.
     * @return An Map(string, object) object.
     * @throws NullPointerException The parameter {@code root} is null.
     */
    public static Map<String, Object> GetPointersWithKey(
      CBORObject root,
      String keyToFind) {
      Map<String, Object> list = new HashMap<String, Object>();
      if (root == null) {
        throw new NullPointerException("root");
      }
      GetPointersWithKey(root, keyToFind, "", list, false);
      return list;
    }

    private static void GetPointersWithKey(
      CBORObject root,
      String keyToFind,
      String currentPointer,
      Map<String, Object> pointerList,
      boolean remove) {
      if (root.getType() == CBORType.Map) {
        CBORObject rootObj = (CBORObject)root;
        if (rootObj.ContainsKey(keyToFind)) {
          // Key found in this object,
          // add this object's JSON pointer
          Object pointerKey = rootObj.get(keyToFind);
          pointerList.put(currentPointer, pointerKey);
          // and remove the key from the Object
          // if necessary
          if (remove) {
            rootObj.Remove(CBORObject.FromObject(keyToFind));
          }
        }
        // Search the key's values
        for (CBORObject key : rootObj.getKeys()) {
          String ptrkey = key.AsString();
          ptrkey = ptrkey.replace("~", "~0");
          ptrkey = ptrkey.replace("/", "~1");
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
