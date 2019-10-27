package com.upokecenter.cbor;

import java.util.*;
import java.io.*;

  final class CBORCanonical {
private CBORCanonical() {
}
    static final Comparator<CBORObject> Comparer =
      new CtapComparer();

    private static final Comparator<Map.Entry<byte[], byte[]>>
    ByteComparer = new CtapByteComparer();

    private static final class CtapByteComparer implements Comparator<Map.Entry<byte[],
      byte[]>> {
      public int compare(
        Map.Entry<byte[], byte[]> kva,
        Map.Entry<byte[], byte[]> kvb) {
        byte[] bytesA = kva.getKey();
        byte[] bytesB = kvb.getKey();
        if (bytesA == null) {
          return bytesB == null ? 0 : -1;
        }
        if (bytesB == null) {
          return 1;
        }
        if (bytesA.length == 0) {
          return bytesB.length == 0 ? 0 : -1;
        }
        if (bytesB.length == 0) {
          return 1;
        }
        if (bytesA == bytesB) {
          // NOTE: Assumes reference equality of CBORObjects
          return 0;
        }
        // check major types
        if (((int)bytesA[0] & 0xe0) != ((int)bytesB[0] & 0xe0)) {
          return ((int)bytesA[0] & 0xe0) < ((int)bytesB[0] & 0xe0) ? -1 : 1;
        }
        // check lengths
        if (bytesA.length != bytesB.length) {
          return bytesA.length < bytesB.length ? -1 : 1;
        }
        // check bytes
        for (int i = 0; i < bytesA.length; ++i) {
          if (bytesA[i] != bytesB[i]) {
            int ai = ((int)bytesA[i]) & 0xff;
            int bi = ((int)bytesB[i]) & 0xff;
            return (ai < bi) ? -1 : 1;
          }
        }
        return 0;
      }
    }

    private static final class CtapComparer implements Comparator<CBORObject> {
      private static int MajorType(CBORObject a) {
        if (a.isTagged()) {
          return 6;
        }
        switch (a.getType()) {
          case Integer:
            return a.isNegative() ? 1 : 0;
          case SimpleValue:
          case Boolean:
          case FloatingPoint:
            return 7;
          case ByteString:
            return 2;
          case TextString:
            return 3;
          case Array:
            return 4;
          case Map:
            return 5;
          default: throw new IllegalStateException();
        }
      }

      public int compare(CBORObject a, CBORObject b) {
        if (a == null) {
          return b == null ? 0 : -1;
        }
        if (b == null) {
          return 1;
        }
        if (a == b) {
          // NOTE: Assumes reference equality of CBORObjects
          return 0;
        }
        a = a.Untag();
        b = b.Untag();
        byte[] abs;
        byte[] bbs;
        int amt = MajorType(a);
        int bmt = MajorType(b);
        if (amt != bmt) {
          return amt < bmt ? -1 : 1;
        }
        // DebugUtility.Log("a="+a);
        // DebugUtility.Log("b="+b);
        if (amt == 2) {
          // Both objects are byte strings
          abs = a.GetByteString();
          bbs = b.GetByteString();
        } else {
          // Might store arrays or maps, where
          // canonical encoding can fail due to too-deep
          // nesting
          abs = CtapCanonicalEncode(a);
          bbs = CtapCanonicalEncode(b);
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

    private static boolean ByteArraysEqual(byte[] bytesA, byte[] bytesB) {
      if (bytesA == bytesB) {
        return true;
      }
      if (bytesA == null || bytesB == null) {
        return false;
      }
      if (bytesA.length == bytesB.length) {
        for (int j = 0; j < bytesA.length; ++j) {
          if (bytesA[j] != bytesB[j]) {
            return false;
          }
        }
        return true;
      }
      return false;
    }

    private static void CheckDepth(CBORObject cbor, int depth) {
        if (cbor.getType() == CBORType.Array) {
          for (int i = 0; i < cbor.size(); ++i) {
              if (depth >= 3 && IsArrayOrMap(cbor.get(i))) {
                throw new CBORException("Nesting level too deep");
              }
              CheckDepth(cbor.get(i), depth + 1);
            }
        } else if (cbor.getType() == CBORType.Map) {
          for (CBORObject key : cbor.getKeys()) {
            if (depth >= 3 && (IsArrayOrMap(key) ||
                IsArrayOrMap(cbor.get(key)))) {
              throw new CBORException("Nesting level too deep");
            }
            CheckDepth(key, depth + 1);
            CheckDepth(cbor.get(key), depth + 1);
          }
        }
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
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        } else if (valueAType == CBORType.Map) {
          Map.Entry<byte[], byte[]> kv1;
          ArrayList<Map.Entry<byte[], byte[]>> sortedKeys;
          sortedKeys = new ArrayList<Map.Entry<byte[], byte[]>>();
          for (CBORObject key : cbor.getKeys()) {
            if (depth >= 3 && (IsArrayOrMap(key) ||
                IsArrayOrMap(cbor.get(key)))) {
              throw new CBORException("Nesting level too deep");
            }
            CheckDepth(key, depth + 1);
            CheckDepth(cbor.get(key), depth + 1);
            // Check if key and value can be canonically encoded
            // (will throw an exception if they cannot)
            kv1 = new AbstractMap.SimpleImmutableEntry<byte[], byte[]>(
              CtapCanonicalEncode(key, depth + 1),
              CtapCanonicalEncode(cbor.get(key), depth + 1));
            sortedKeys.add(kv1);
          }
          java.util.Collections.sort(sortedKeys, ByteComparer);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.WriteValue(ms, 5, cbor.size());
            byte[] lastKey = null;
            for (int i = 0; i < sortedKeys.size(); ++i) {
              kv1 = sortedKeys.get(i);
              byte[] bytes = kv1.getKey();
              if (lastKey != null && ByteArraysEqual(bytes, lastKey)) {
                throw new CBORException("duplicate canonical CBOR key");
              }
              lastKey = bytes;
              ms.write(bytes, 0, bytes.length);
              bytes = kv1.getValue();
              ms.write(bytes, 0, bytes.length);
            }
            return ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
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
