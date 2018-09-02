package com.upokecenter.test;
/*
Written in 2013 by Peter Occil.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/

If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
*/

import java.util.*;
import com.upokecenter.cbor.*;

  public class JSONPatch {
    private static CBORObject addOperation(
        CBORObject o,
        String valueOpStr,
        String path,
        CBORObject value) {
      if (path == null) {
        throw new IllegalArgumentException("patch " + valueOpStr);
      }
      if (path.length() == 0) {
        o = value;
      } else {
        JSONPointer pointer = JSONPointer.fromPointer(o, path);
        if (pointer.getParent().getType() == CBORType.Array) {
          int index = pointer.getIndex();
          if (index < 0) {
            throw new IllegalArgumentException("patch " + valueOpStr + " path");
          }
          ((CBORObject)pointer.getParent()).Insert(index, value);
        } else if (pointer.getParent().getType() == CBORType.Map) {
          String key = pointer.getKey();
          ((CBORObject)pointer.getParent()).Set(key, value);
        } else {
          throw new IllegalArgumentException("patch " + valueOpStr + " path");
        }
      }
      return o;
    }

    private static CBORObject cloneCbor(CBORObject o) {
      return CBORObject.FromJSONString(o.ToJSONString());
    }

    private static String getString(CBORObject o, String key) {
      return o.ContainsKey(key) ? o.get(key).AsString() : null;
    }

    public static CBORObject patch(CBORObject o, CBORObject patch) {
      // clone the Object in case of failure
      o = cloneCbor(o);
      for (int i = 0; i < patch.size(); ++i) {
        CBORObject patchOp = patch.get(i);
        // NOTE: This algorithm requires "op" to exist
        // only once; the CBORObject, however, does not
        // allow duplicates
        String valueOpStr = getString(patchOp, "op");
        if (valueOpStr == null) {
          throw new IllegalArgumentException("patch");
        }
        if ("add".equals(valueOpStr)) {
          // operation
          CBORObject value = null;
          if (!patchOp.ContainsKey("value")) {
throw new IllegalArgumentException("patch " + valueOpStr + " value");
          }
            value = patchOp.get("value");
          o = addOperation(o, valueOpStr, getString(patchOp, "path"), value);
        } else if ("replace".equals(valueOpStr)) {
          // operation
          CBORObject value = null;
          if (!patchOp.ContainsKey("value")) {
throw new IllegalArgumentException("patch " + valueOpStr + " value");
          }
            value = patchOp.get("value");
        o = replaceOperation(
  o,
  valueOpStr,
  getString(patchOp, "path"),
  value);
        } else if ("remove".equals(valueOpStr)) {
          // Remove operation
          String path = patchOp.get("path").AsString();
          if (path == null) {
            throw new IllegalArgumentException("patch " + valueOpStr + " path");
          }
          if (path.length() == 0) {
            o = null;
          } else {
            removeOperation(o, valueOpStr, getString(patchOp, "path"));
          }
        } else if ("move".equals(valueOpStr)) {
          String path = patchOp.get("path").AsString();
          if (path == null) {
            throw new IllegalArgumentException("patch " + valueOpStr + " path");
          }
          String fromPath = patchOp.get("from").AsString();
          if (fromPath == null) {
            throw new IllegalArgumentException("patch " + valueOpStr + " from");
          }
          if (path.startsWith(fromPath)) {
            throw new IllegalArgumentException("patch " + valueOpStr);
          }
          CBORObject movedObj = removeOperation(o, valueOpStr, fromPath);
          o = addOperation(o, valueOpStr, path, cloneCbor(movedObj));
        } else if ("copy".equals(valueOpStr)) {
          String path = patchOp.get("path").AsString();
          String fromPath = patchOp.get("from").AsString();
          if (path == null) {
            throw new IllegalArgumentException("patch " + valueOpStr + " path");
          }
          if (fromPath == null) {
            throw new IllegalArgumentException("patch " + valueOpStr + " from");
          }
          JSONPointer pointer = JSONPointer.fromPointer(o, path);
          if (!pointer.exists()) {
            throw new NoSuchElementException("patch " +
              valueOpStr + " " + fromPath);
          }
          CBORObject copiedObj = pointer.getValue();
          o = addOperation(
  o,
  valueOpStr,
  path,
  cloneCbor(copiedObj));
        } else if ("test".equals(valueOpStr)) {
          String path = patchOp.get("path").AsString();
          if (path == null) {
            throw new IllegalArgumentException("patch " + valueOpStr + " path");
          }
          CBORObject value = null;
          if (!patchOp.ContainsKey("value")) {
throw new IllegalArgumentException("patch " + valueOpStr + " value");
          }
            value = patchOp.get("value");
          JSONPointer pointer = JSONPointer.fromPointer(o, path);
          if (!pointer.exists()) {
            throw new IllegalArgumentException("patch " +
              valueOpStr + " " + path);
          }
          Object testedObj = pointer.getValue();
        if ((testedObj == null) ? (value != null) :
            !testedObj.equals(value)) {
            throw new IllegalStateException("patch " + valueOpStr);
          }
        }
      }
      return (o == null) ? CBORObject.Null : o;
    }

    private static CBORObject removeOperation(
        CBORObject o,
        String valueOpStr,
        String path) {
      if (path == null) {
        throw new IllegalArgumentException("patch " + valueOpStr);
      }
      if (path.length() == 0) {
        return o;
      } else {
        JSONPointer pointer = JSONPointer.fromPointer(o, path);
        if (!pointer.exists()) {
          throw new NoSuchElementException("patch " +
            valueOpStr + " " + path);
        }
        o = pointer.getValue();
        if (pointer.getParent().getType() == CBORType.Array) {
          ((CBORObject)pointer.getParent()).RemoveAt(pointer.getIndex());
        } else if (pointer.getParent().getType() == CBORType.Map) {
          ((CBORObject)pointer.getParent()).Remove(
              CBORObject.FromObject(pointer.getKey()));
        }
        return o;
      }
    }

    private static CBORObject replaceOperation(
        CBORObject o,
        String valueOpStr,
        String path,
        CBORObject value) {
      if (path == null) {
        throw new IllegalArgumentException("patch " + valueOpStr);
      }
      if (path.length() == 0) {
        o = value;
      } else {
        JSONPointer pointer = JSONPointer.fromPointer(o, path);
        if (!pointer.exists()) {
          throw new NoSuchElementException("patch " +
            valueOpStr + " " + path);
        }
        if (pointer.getParent().getType() == CBORType.Array) {
          int index = pointer.getIndex();
          if (index < 0) {
            throw new IllegalArgumentException("patch " + valueOpStr + " path");
          }
        ((CBORObject)pointer.getParent()).Set(index, value);
        } else if (pointer.getParent().getType() == CBORType.Map) {
          String key = pointer.getKey();
          ((CBORObject)pointer.getParent()).Set(key, value);
        } else {
          throw new IllegalArgumentException("patch " + valueOpStr + " path");
        }
      }
      return o;
    }
  }
