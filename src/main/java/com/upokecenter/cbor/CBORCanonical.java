package com.upokecenter.cbor;

import java.util.*;
import java.io.*;

  final class CBORCanonical {
private CBORCanonical() {
}
    static final Comparator<CBORObject> Comparer =
      new CtapComparer();

    private static final class CtapComparer implements Comparator<CBORObject> {
      public int compare(CBORObject a, CBORObject b) {
        if (a == null) {
          return b == null ? 0 : -1;
        }
        if (b == null) {
          return 1;
        }
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

    private static boolean IsArrayOrMap(CBORObject a) {
       return a.getType() == CBORType.Array || a.getType() == CBORType.Map;
    }

    public static byte[] CtapCanonicalEncode(CBORObject a) {
      return CtapCanonicalEncode(a, 0);
    }

    private static byte[] CtapCanonicalEncode(CBORObject a, int depth) {
      CBORObject cbor = a.Untag();
      CBORType valueAType = cbor.getType();
      try {
        if (valueAType == CBORType.Array) {
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.WriteValue(ms, 4, cbor.size());
            for (int i = 0; i < cbor.size(); ++i) {
              if (depth >= 3 && IsArrayOrMap(cbor.get(i))) {
                throw new CBORException("Nesting level too deep");
              }
              byte[] bytes = CtapCanonicalEncode(cbor.get(i), depth + 1);
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
        } else if (valueAType == CBORType.Map) {
          ArrayList<CBORObject> sortedKeys = new ArrayList<CBORObject>();
          for (CBORObject key : cbor.getKeys()) {
            if (depth >= 3 && (IsArrayOrMap(key) ||
               IsArrayOrMap(cbor.get(key)))) {
              throw new CBORException("Nesting level too deep");
            }
            sortedKeys.add(key);
          }
          java.util.Collections.sort(sortedKeys, Comparer);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.WriteValue(ms, 5, cbor.size());
            for (CBORObject key : sortedKeys) {
              byte[] bytes = CtapCanonicalEncode(key, depth + 1);
              ms.write(bytes, 0, bytes.length);
              bytes = CtapCanonicalEncode(cbor.get(key), depth + 1);
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
        }
      } catch (IOException ex) {
        throw new IllegalStateException(ex.toString(), ex);
      }
      if (valueAType == CBORType.SimpleValue ||
       valueAType == CBORType.Boolean || valueAType == CBORType.ByteString ||
       valueAType == CBORType.TextString) {
        return cbor.EncodeToBytes(CBOREncodeOptions.Default);
      } else if (valueAType == CBORType.FloatingPoint) {
        long bits = cbor.AsDoubleBits();
        return new byte[] {
          (byte)0xfb,
          (byte)((bits >> 56) & 0xffL),
          (byte)((bits >> 48) & 0xffL),
          (byte)((bits >> 40) & 0xffL),
          (byte)((bits >> 32) & 0xffL),
          (byte)((bits >> 24) & 0xffL),
          (byte)((bits >> 16) & 0xffL),
          (byte)((bits >> 8) & 0xffL),
          (byte)(bits & 0xffL),
         };
      } else if (valueAType == CBORType.Integer) {
        return cbor.EncodeToBytes(CBOREncodeOptions.Default);
      } else {
        throw new IllegalArgumentException("Invalid CBOR type.");
      }
    }
  }
