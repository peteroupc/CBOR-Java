package com.upokecenter.cbor;
/*
Written in 2013 by Peter Occil.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under the Unlicense: https://unlicense.org/

*/

  final class JSONPatch {
private JSONPatch() {
}
    private static CBORObject AddOperation(
      CBORObject o,
      String valueOpStr,
      String path,
      CBORObject value) {
      if (path == null) {
        throw new CBORException("Patch " + valueOpStr);
      }
      if (path.length() == 0) {
        o = value;
      } else {
        // System.out.println("pointer--->"+path);
        JSONPointer pointer = JSONPointer.FromPointer(o, path);
        CBORObject parent = pointer.GetParent();
        // System.out.println("addop pointer "+path+" ["+parent+"]");
        if (pointer.GetParent().getType() == CBORType.Array) {
          int index = pointer.GetIndex();
          // System.out.println("index "+index);
          if (index < 0) {
            throw new CBORException("Patch " + valueOpStr + " path");
          }
          // System.out.println("before "+parent+"");
          parent.Insert(index, value);
          // System.out.println("after "+parent+"");
        } else if (pointer.GetParent().getType() == CBORType.Map) {
          String key = pointer.GetKey();
          parent.Set(CBORObject.FromString(key), value);
        } else {
          throw new CBORException("Patch " + valueOpStr + " path");
        }
      }
      return o;
    }

    private static CBORObject CloneCbor(CBORObject o) {
      switch (o.getType()) {
        case ByteString:
        case Map:
        case Array:
          return CBORObject.DecodeFromBytes(o.EncodeToBytes());
        default: return o;
      }
    }

    private static String GetString(CBORObject o, String str) {
      CBORObject co = o.GetOrDefault(str, null);
      if (co == null) {
 throw new CBORException(str + " not found");
}
 if (co.getType() != CBORType.TextString) {
 throw new CBORException("Not a" +
"\u0020text String type");
}
 return co.AsString();
    }

    public static CBORObject Patch(CBORObject o, CBORObject ptch) {
      // clone the Object in case of failure
      if (o == null) {
        throw new CBORException("Object is null");
      }
      if (ptch == null) {
        throw new CBORException("patch is null");
      }
      if (ptch.getType() != CBORType.Array) {
        throw new CBORException("patch is not an array");
      }
      o = CloneCbor(o);
      for (int i = 0; i < ptch.size(); ++i) {
        CBORObject patchOp = ptch.get(i);

        // NOTE: This algorithm requires "op" to exist
        // only once; the CBORObject, however, does not
        // allow duplicates
        String valueOpStr = GetString(patchOp, "op");
        if ("add".equals(valueOpStr)) {
          // operation
          CBORObject value = patchOp.GetOrDefault("value", null);
          if (value == null) {
            throw new CBORException("Patch " + valueOpStr + " value");
          }
          value = patchOp.get("value");
          o = AddOperation(
              o,
              valueOpStr,
              GetString(patchOp, "path"),
              value);
        } else if ("replace".equals(valueOpStr)) {
          // operation
          CBORObject value = patchOp.GetOrDefault("value", null);
          if (value == null) {
            throw new CBORException("Patch " + valueOpStr + " value");
          }

          o = ReplaceOperation(
              o,
              valueOpStr,
              GetString(patchOp, "path"),
              CloneCbor(value));
        } else if ("remove".equals(valueOpStr)) {
          // Remove operation
          String path = GetString(patchOp, "path");
          if (path == null) {
            throw new CBORException("Patch " + valueOpStr + " path");
          }
          if (path.length() == 0) {
            o = null;
          } else {
            RemoveOperation(o, valueOpStr, GetString(patchOp, "path"));
          }
        } else if ("move".equals(valueOpStr)) {
          String path = GetString(patchOp, "path");
          if (path == null) {
            throw new CBORException("Patch " + valueOpStr + " path");
          }
          String fromPath = GetString(patchOp, "from");
          if (fromPath == null) {
            throw new CBORException("Patch " + valueOpStr + " from");
          }
          if (path.equals(fromPath)) {
            JSONPointer pointer = JSONPointer.FromPointer(o, path);
            if (pointer.Exists()) {
              // Moving to the same path, so return
              return o;
            }
          }
          // if (path.startsWith(fromPath)) {
          // throw new CBORException("Patch " + valueOpStr + ": startsWith failed " +
          // "[" + path + "] [" + fromPath + "]");
          // }
          CBORObject movedObj = RemoveOperation(o, valueOpStr, fromPath);
          o = AddOperation(o, valueOpStr, path, CloneCbor(movedObj));
        } else if ("copy".equals(valueOpStr)) {
          String path = GetString(patchOp, "path");
          String fromPath = GetString(patchOp, "from");
          if (path == null) {
            throw new CBORException("Patch " + valueOpStr + " path");
          }
          if (fromPath == null) {
            throw new CBORException("Patch " + valueOpStr + " from");
          }
          JSONPointer pointer = JSONPointer.FromPointer(o, fromPath);
          if (!pointer.Exists()) {
            throw new CBORException("Patch " + valueOpStr + " " + fromPath);
          }
          CBORObject copiedObj = pointer.GetValue();
          o = AddOperation(
              o,
              valueOpStr,
              path,
              CloneCbor(copiedObj));
        } else if ("test".equals(valueOpStr)) {
          String path = GetString(patchOp, "path");
          if (path == null) {
            throw new CBORException("Patch " + valueOpStr + " path");
          }
          CBORObject value = null;
          if (!patchOp.ContainsKey("value")) {
            throw new CBORException("Patch " + valueOpStr + " value");
          }
          value = patchOp.get("value");
          JSONPointer pointer = JSONPointer.FromPointer(o, path);
          if (!pointer.Exists()) {
            throw new CBORException("Patch " + valueOpStr + " " + path);
          }
          Object testedObj = pointer.GetValue();
          if ((testedObj == null) ? (value != null) :
            !testedObj.equals(value)) {
            throw new CBORException("Patch " + valueOpStr);
          }
        } else {
          throw new CBORException("Unrecognized op");
        }
      }
      return (o == null) ? (CBORObject.Null) : o;
    }

    private static CBORObject RemoveOperation(
      CBORObject o,
      String valueOpStr,
      String path) {
      if (path == null) {
        throw new CBORException("Patch " + valueOpStr);
      }
      if (path.length() == 0) {
        return o;
      } else {
        JSONPointer pointer = JSONPointer.FromPointer(o, path);
        if (!pointer.Exists()) {
          throw new CBORException("Patch " + valueOpStr + " " + path);
        }
        o = pointer.GetValue();
        if (pointer.GetParent().getType() == CBORType.Array) {
          pointer.GetParent().RemoveAt(pointer.GetIndex());
        } else if (pointer.GetParent().getType() == CBORType.Map) {
          pointer.GetParent().Remove(
            CBORObject.FromString(pointer.GetKey()));
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
        throw new CBORException("Patch " + valueOpStr);
      }

      if (path.length() == 0) {
        o = value;
      } else {
        JSONPointer pointer = JSONPointer.FromPointer(o, path);
        if (!pointer.Exists()) {
          throw new CBORException("Patch " + valueOpStr + " " + path);
        }
        if (pointer.GetParent().getType() == CBORType.Array) {
          int index = pointer.GetIndex();
          if (index < 0) {
            throw new CBORException("Patch " + valueOpStr + " path");
          }
          pointer.GetParent().Set(index, value);
        } else if (pointer.GetParent().getType() == CBORType.Map) {
          String key = pointer.GetKey();
          pointer.GetParent().Set(CBORObject.FromString(key), value);
        } else {
          throw new CBORException("Patch " + valueOpStr + " path");
        }
      }
      return o;
    }
  }
