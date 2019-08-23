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

  public final class JSONPatch {
private JSONPatch() {
}
    private static CBORObject AddOperation(
      CBORObject o,
      String valueOpStr,
      String path,
      CBORObject value) {
      if (path == null) {
        throw new IllegalArgumentException("Patch " + valueOpStr);
      }
      if (path.length() == 0) {
        o = value;
      } else {
        JSONPointer pointer = JSONPointer.FromPointer(o, path);
        if (pointer.GetParent().getType() == CBORType.Array) {
          int index = pointer.GetIndex();
          if (index < 0) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " path");
          }
          ((CBORObject)pointer.GetParent()).Insert(index, value);
        } else if (pointer.GetParent().getType() == CBORType.Map) {
          String key = pointer.GetKey();
          ((CBORObject)pointer.GetParent()).Set(key, value);
        } else {
          throw new IllegalArgumentException("Patch " + valueOpStr + " path");
        }
      }
      return o;
    }

    private static CBORObject CloneCbor(CBORObject o) {
      return CBORObject.FromJSONString(o.ToJSONString());
    }

    private static String GetString(CBORObject o, String key) {
      return o.ContainsKey(key) ? o.get(key).AsString() : null;
    }

    public static CBORObject Patch(CBORObject o, CBORObject ptch) {
      // clone the Object in case of failure
      if (o == null) {
        throw new NullPointerException("o");
      }
      o = CloneCbor(o);
      if (ptch == null) {
        throw new NullPointerException("ptch");
      }
      for (int i = 0; i < ptch.size(); ++i) {
        CBORObject patchOp = ptch.get(i);
        // NOTE: This algorithm requires "op" to exist
        // only once; the CBORObject, however, does not
        // allow duplicates
        String valueOpStr = GetString(patchOp, "op");
        if (valueOpStr == null) {
          throw new IllegalArgumentException("Patch");
        }
        if ("add".equals(valueOpStr)) {
          // operation
          CBORObject value = null;
          if (!patchOp.ContainsKey("value")) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " value");
          }
          value = patchOp.get("value");
          o = AddOperation(o, valueOpStr, GetString(patchOp, "path"), value);
        } else if ("replace".equals(valueOpStr)) {
          // operation
          CBORObject value = null;
          if (!patchOp.ContainsKey("value")) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " value");
          }
          value = patchOp.get("value");
          o = ReplaceOperation(
  o,
  valueOpStr,
  GetString(patchOp, "path"),
  value);
        } else if ("remove".equals(valueOpStr)) {
          // Remove operation
          String path = patchOp.get("path").AsString();
          if (path == null) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " path");
          }
          if (path.length() == 0) {
            o = null;
          } else {
            RemoveOperation(o, valueOpStr, GetString(patchOp, "path"));
          }
        } else if ("move".equals(valueOpStr)) {
          String path = patchOp.get("path").AsString();
          if (path == null) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " path");
          }
          String fromPath = patchOp.get("from").AsString();
          if (fromPath == null) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " from");
          }
          if (path.startsWith(fromPath)) {
            throw new IllegalArgumentException("Patch " + valueOpStr);
          }
          CBORObject movedObj = RemoveOperation(o, valueOpStr, fromPath);
          o = AddOperation(o, valueOpStr, path, CloneCbor(movedObj));
        } else if ("copy".equals(valueOpStr)) {
          String path = patchOp.get("path").AsString();
          String fromPath = patchOp.get("from").AsString();
          if (path == null) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " path");
          }
          if (fromPath == null) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " from");
          }
          JSONPointer pointer = JSONPointer.FromPointer(o, path);
          if (!pointer.Exists()) {
            throw new NoSuchElementException("Patch " +
              valueOpStr + " " + fromPath);
          }
          CBORObject copiedObj = pointer.GetValue();
          o = AddOperation(
  o,
  valueOpStr,
  path,
  CloneCbor(copiedObj));
        } else if ("test".equals(valueOpStr)) {
          String path = patchOp.get("path").AsString();
          if (path == null) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " path");
          }
          CBORObject value = null;
          if (!patchOp.ContainsKey("value")) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " value");
          }
          value = patchOp.get("value");
          JSONPointer pointer = JSONPointer.FromPointer(o, path);
          if (!pointer.Exists()) {
            throw new IllegalArgumentException("Patch " +
              valueOpStr + " " + path);
          }
          Object testedObj = pointer.GetValue();
          if ((testedObj == null) ? (value != null) :
            !testedObj.equals(value)) {
            throw new IllegalStateException("Patch " + valueOpStr);
          }
        }
      }
      return (o == null) ? CBORObject.Null : o;
    }

    private static CBORObject RemoveOperation(
      CBORObject o,
      String valueOpStr,
      String path) {
      if (path == null) {
        throw new IllegalArgumentException("Patch " + valueOpStr);
      }
      if (path.length() == 0) {
        return o;
      } else {
        JSONPointer pointer = JSONPointer.FromPointer(o, path);
        if (!pointer.Exists()) {
          throw new NoSuchElementException("Patch " +
            valueOpStr + " " + path);
        }
        o = pointer.GetValue();
        if (pointer.GetParent().getType() == CBORType.Array) {
          ((CBORObject)pointer.GetParent()).RemoveAt(pointer.GetIndex());
        } else if (pointer.GetParent().getType() == CBORType.Map) {
          ((CBORObject)pointer.GetParent()).Remove(
              CBORObject.FromObject(pointer.GetKey()));
        }
        return o;
      }
    }

    private static CBORObject ReplaceOperation(
      CBORObject o,
      String valueOpStr,
      String path,
      CBORObject value) {
      if (path == null) {
        throw new IllegalArgumentException("Patch " + valueOpStr);
      }
      if (path.length() == 0) {
        o = value;
      } else {
        JSONPointer pointer = JSONPointer.FromPointer(o, path);
        if (!pointer.Exists()) {
          throw new NoSuchElementException("Patch " +
            valueOpStr + " " + path);
        }
        if (pointer.GetParent().getType() == CBORType.Array) {
          int index = pointer.GetIndex();
          if (index < 0) {
            throw new IllegalArgumentException("Patch " + valueOpStr + " path");
          }
          ((CBORObject)pointer.GetParent()).Set(index, value);
        } else if (pointer.GetParent().getType() == CBORType.Map) {
          String key = pointer.GetKey();
          ((CBORObject)pointer.GetParent()).Set(key, value);
        } else {
          throw new IllegalArgumentException("Patch " + valueOpStr + " path");
        }
      }
      return o;
    }
  }
