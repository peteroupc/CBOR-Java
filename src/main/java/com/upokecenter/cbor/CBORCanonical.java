package com.upokecenter.cbor;

import java.util.*;
import java.io.*;

  final class CBORCanonical {
private CBORCanonical() {
}
    private static final class CtapComparer implements Comparator<CBORObject> {
      public int compare(CBORObject a, CBORObject b) {
        byte[] abs;
        byte[] bbs;
boolean bothBytes = false;
        if (a.getType() == CBORType.ByteString && b.getType() == CBORType.ByteString) {
          abs = a.GetByteString();
          bbs = b.GetByteString();
bothBytes = true;
        } else {
          abs = CtapCanonicalEncode(a);
          bbs = CtapCanonicalEncode(b);
        }
if (!bothBytes && (abs[0] & 0xe0) != (bbs[0] & 0xe0)) {
 // different major types
 return (abs[0] & 0xe0) < (bbs[0] & 0xe0) ? -1 : 1;
}
        if (abs.length != bbs.length) {
 // different lengths
 return abs.length < bbs.length ? -1 : 1;
}
        for (int i = 0; i < abs.length; ++i) {
          if (abs[i] != bbs[i]) {
            int ai = ((int)abs[i]) & 0xff;
            int bi = ((int)bbs[i]) & 0xff;
            return (ai < bi) ? -1 : 1;
          }
        }
        return 0;
      }
    }

    public static byte[] CtapCanonicalEncode(CBORObject a) {
      CBORObject cbor = a.Untag();
      CBORType valueAType = cbor.getType();
      try {
        if (valueAType == CBORType.Array) {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.WriteValue(ms, 4, cbor.size());
            for (int i = 0; i < cbor.size(); ++i) {
              byte[] bytes = CtapCanonicalEncode(cbor.get(i));
              ms.write(bytes, 0, bytes.length);
            }
            return ms.toByteArray();
}
finally {
try { if (ms != null) {
 ms.close();
 } } catch (java.io.IOException ex) {}
}
        } else if (valueAType == CBORType.Map) {
          ArrayList<CBORObject> sortedKeys = new ArrayList<CBORObject>();
          for (CBORObject key : cbor.getKeys()) {
            sortedKeys.add(key);
          }
          java.util.Collections.sort(sortedKeys, new CtapComparer());
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.WriteValue(ms, 5, cbor.size());
            for (CBORObject key : sortedKeys) {
              byte[] bytes = CtapCanonicalEncode(key);
              ms.write(bytes, 0, bytes.length);
              bytes = CtapCanonicalEncode(cbor.get(key));
              ms.write(bytes, 0, bytes.length);
            }
            return ms.toByteArray();
}
finally {
try { if (ms != null) {
 ms.close();
 } } catch (java.io.IOException ex) {}
}
        }
      } catch (IOException ex) {
        throw new IllegalStateException(ex.toString(), ex);
      }
      if (valueAType == CBORType.SimpleValue ||
       valueAType == CBORType.Boolean || valueAType == CBORType.ByteString ||
       valueAType == CBORType.TextString) {
        return cbor.EncodeToBytes(CBOREncodeOptions.Default);
      } else if (valueAType == CBORType.Number) {
        if (cbor.CanFitInInt64()) {
          return cbor.EncodeToBytes(CBOREncodeOptions.Default);
        } else {
          cbor = CBORObject.FromObject(cbor.AsDouble());
          return cbor.EncodeToBytes(CBOREncodeOptions.Default);
        }
      } else {
        throw new IllegalArgumentException("Invalid CBOR type.");
      }
    }
  }
