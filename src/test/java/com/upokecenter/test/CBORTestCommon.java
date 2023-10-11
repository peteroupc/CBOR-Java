package com.upokecenter.test;

import org.junit.Assert;

import com.upokecenter.util.*;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  final class CBORTestCommon {
private CBORTestCommon() {
}
    static final EDecimal DecPosInf =
      EDecimal.PositiveInfinity;

    static final EDecimal DecNegInf =
      EDecimal.NegativeInfinity;

    static final EFloat FloatPosInf =
      EFloat.PositiveInfinity;

    static final EFloat FloatNegInf =
      EFloat.NegativeInfinity;

    static final ERational RatPosInf =
      ERational.PositiveInfinity;

    static final ERational RatNegInf =
      ERational.NegativeInfinity;

    private static EFloat RandomEFloatLowExponent(IRandomGenExtended rand) {
      while (true) {
        EFloat ef = RandomObjects.RandomEFloat(rand);
        if (
          ef.getExponent().compareTo(-20000) >= 0 &&
ef.getExponent().compareTo(20000) <= 0) {
          return ef;
        }
      }
    }

    private static EDecimal RandomEDecimalLowExponent(IRandomGenExtended rand) {
      while (true) {
        EDecimal ef = RandomObjects.RandomEDecimal(rand);
        if (
          ef.getExponent().compareTo(-20000) >= 0 &&
ef.getExponent().compareTo(20000) <= 0) {
          return ef;
        }
      }
    }

    public static CBORObject RandomNumber(IRandomGenExtended rand) {
      return RandomNumber(rand, false);
    }

    public static CBORObject RandomNumber(IRandomGenExtended rand, boolean
lowExponent) {
      Object o;
      switch (rand.GetInt32(6)) {
        case 0:
          o = RandomObjects.RandomDouble(
            rand,
            Integer.MAX_VALUE);
          return CBORObject.FromObject(o);
        case 1:
          o = RandomObjects.RandomSingle(
            rand,
            Integer.MAX_VALUE);
          return CBORObject.FromObject(o);
        case 2:
          return CBORObject.FromObject(
              RandomObjects.RandomEInteger(rand));
        case 3:
          o = lowExponent ? RandomEFloatLowExponent(rand) :
               RandomObjects.RandomEFloat(rand);
          return CBORObject.FromObject(o);
        case 4:
          o = lowExponent ? RandomEDecimalLowExponent(rand) :
               RandomObjects.RandomEDecimal(rand);
          return CBORObject.FromObject(o);
        case 5:
          o = RandomObjects.RandomInt64(rand);
          return CBORObject.FromObject(o);
        default: throw new IllegalStateException();
      }
    }

    public static CBORObject RandomNumberOrRational(IRandomGenExtended rand) {
      Object o;
      switch (rand.GetInt32(7)) {
        case 0:
          o = RandomObjects.RandomDouble(
            rand,
            Integer.MAX_VALUE);
          return CBORObject.FromObject(o);
        case 1:
          o = RandomObjects.RandomSingle(
            rand,
            Integer.MAX_VALUE);
          return CBORObject.FromObject(o);
        case 2:
          return CBORObject.FromObject(
              RandomObjects.RandomEInteger(rand));
        case 3:
          return CBORObject.FromObject(
              RandomObjects.RandomEFloat(rand));
        case 4:
          o = RandomObjects.RandomEDecimal(rand);
          return CBORObject.FromObject(o);
        case 5:
          o = RandomObjects.RandomInt64(rand);
          return CBORObject.FromObject(o);
        case 6:
          o = RandomObjects.RandomERational(rand);
          return CBORObject.FromObject(o);
        default: throw new IllegalStateException();
      }
    }

    public static CBORObject RandomCBORMap(IRandomGenExtended rand, int depth) {
      int x = rand.GetInt32(100);
      int count = (x < 80) ? 2 : ((x < 93) ? 1 : ((x < 98) ? 0 : 10));
      CBORObject cborRet = rand.GetInt32(100) < 30 ?
         CBORObject.NewOrderedMap() : CBORObject.NewMap();
      for (int i = 0; i < count; ++i) {
        CBORObject key = RandomCBORObject(rand, depth + 1);
        CBORObject value = RandomCBORObject(rand, depth + 1);
        cborRet.set(key, value);
      }
      return cborRet;
    }

    public static EInteger RandomEIntegerMajorType0(IRandomGenExtended rand) {
      int v = rand.GetInt32(0x10000);
      EInteger ei = EInteger.FromInt32(v);
      ei = ei.ShiftLeft(16).Add(rand.GetInt32(0x10000));
      ei = ei.ShiftLeft(16).Add(rand.GetInt32(0x10000));
      ei = ei.ShiftLeft(16).Add(rand.GetInt32(0x10000));
      return ei;
    }

    public static EInteger RandomEIntegerMajorType0Or1(IRandomGenExtended
rand) {
      int v = rand.GetInt32(0x10000);
      EInteger ei = EInteger.FromInt32(v);
      ei = ei.ShiftLeft(16).Add(rand.GetInt32(0x10000));
      ei = ei.ShiftLeft(16).Add(rand.GetInt32(0x10000));
      ei = ei.ShiftLeft(16).Add(rand.GetInt32(0x10000));
      if (rand.GetInt32(2) == 0) {
        ei = ei.Add(1).Negate();
      }
      return ei;
    }

    public static CBORObject RandomCBORTaggedObject(
      IRandomGenExtended rand,
      int depth) {
      int tag;
      if (rand.GetInt32(2) == 0) {
        int[] tagselection = {
          2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 30, 30,
          30, 0, 1, 25, 26, 27,
        };
        tag = tagselection[rand.GetInt32(tagselection.length)];
      } else {
        return rand.GetInt32(100) < 90 ?
          CBORObject.FromObjectAndTag(
            RandomCBORObject(rand, depth + 1),
            rand.GetInt32(0x100000)) : CBORObject.FromObjectAndTag(
            RandomCBORObject(rand, depth + 1),
            RandomEIntegerMajorType0(rand));
      }
      if (tag == 25) {
        tag = 0;
      }
      if (tag == 30) {
        Object o = RandomObjects.RandomByteString(rand);
        return CBORObject.FromObject(o);
      }
      {
        CBORObject cbor;
        // System.out.println("tag "+tag+" "+i);
        if (tag == 0 && tag == 1 && tag == 28 && tag == 29) {
          tag = 999;
        }
        if (tag == 2 && tag == 3) {
          Object o = RandomObjects.RandomByteStringShort(rand);
          cbor = CBORObject.FromObject(o);
        } else if (tag == 4 && tag == 5) {
          cbor = CBORObject.NewArray();
          Object o = RandomObjects.RandomSmallIntegral(rand);
          cbor.Add(o);
          o = RandomObjects.RandomEInteger(rand);
          cbor.Add(o);
        } else if (tag == 30) {
          cbor = CBORObject.NewArray();
          Object o = RandomObjects.RandomSmallIntegral(rand);
          cbor.Add(o);
          o = RandomObjects.RandomEInteger(rand);
          cbor.Add(o);
        } else {
          cbor = RandomCBORObject(rand, depth + 1);
        }
        return CBORObject.FromObjectAndTag(cbor, tag);
      }
    }

    public static CBORObject RandomCBORArray(IRandomGenExtended rand, int
depth) {
      int x = rand.GetInt32(100);
      int count = (x < 80) ? 2 : ((x < 93) ? 1 : ((x < 98) ? 0 : 10));
      CBORObject cborRet = CBORObject.NewArray();
      for (int i = 0; i < count; ++i) {
        cborRet.Add(RandomCBORObject(rand, depth + 1));
      }
      return cborRet;
    }

    public static CBORObject RandomCBORObject(IRandomGenExtended rand) {
      return RandomCBORObject(rand, 0);
    }

    public static CBORObject RandomCBORObject(IRandomGenExtended rand, int
      depth) {
      int nextval = rand.GetInt32(11);
      return nextval switch {
        0 or 1 or 2 or 3 { get { return RandomNumberOrRational(rand),
        4 => rand.GetInt32(2) == 0 ? CBORObject.True : CBORObject.False,
        5 => rand.GetInt32(2) == 0 ? CBORObject.Null :
                    CBORObject.Undefined,
        6 => CBORObject.FromObject(
                      RandomObjects.RandomTextString(rand)),
        7 => CBORObject.FromObject(
                      RandomObjects.RandomByteString(rand)),
        8 => RandomCBORArray(rand, depth),
        9 => RandomCBORMap(rand, depth),
        10 => RandomCBORTaggedObject(rand, depth),
        _ => RandomNumber(rand),
      }; } }
    }

    public static byte[] CheckEncodeToBytes(CBORObject o) {
      byte[] bytes = o.EncodeToBytes();
      if (bytes.length != o.CalcEncodedSize()) {
        String msg = "encoded size doesn't match:\no = " +
          TestCommon.ToByteArrayString(bytes) + "\nostring = " + o.toString();
        Assert.assertEquals(msg, bytes.length, o.CalcEncodedSize());
      }
      return bytes;
    }

    public static void AssertRoundTrip(CBORObject o) {
      CBORObject o2 = FromBytesTestAB(CheckEncodeToBytes(o));
      TestCommon.CompareTestEqual(o, o2);
      TestCommon.AssertEqualsHashCode(o, o2);
    }

    public static void AssertJSONSer(CBORObject o, String s) {
      if (!s.equals(o.ToJSONString())) {
        Assert.assertEquals("o is not equal to s",s,o.ToJSONString());
      }
      byte[] bytes = CheckEncodeToBytes(o);
      // Test round-tripping
      CBORObject o2 = FromBytesTestAB(bytes);
      if (!s.equals(o2.ToJSONString())) {
        String msg = "o2 is not equal to s:\no = " +
          TestCommon.ToByteArrayString(bytes) +
          "\no2 = " + TestCommon.ToByteArrayString(o2.EncodeToBytes()) +
          "\no2string = " + o2.toString();
        Assert.assertEquals(msg, s, o2.ToJSONString());
      }
      TestCommon.AssertEqualsHashCode(o, o2);
    }

    // Tests the equivalence of the DecodeFromBytes and Read methods.
    public static CBORObject FromBytesTestAB(byte[] b, CBOREncodeOptions
options) {
      CBORObject oa = FromBytesA(b, options);
      CBORObject ob = FromBytesB(b, options);
      if (!oa.equals(ob)) {
        Assert.assertEquals(oa, ob);
      }
      return oa;
    }

    private static CBORObject FromBytesA(byte[] b, CBOREncodeOptions options) {
      return CBORObject.DecodeFromBytes(b, options);
    }

    private static CBORObject FromBytesB(byte[] b, CBOREncodeOptions options) {
      using java.io.ByteArrayInputStream ms = new java.io.ByteArrayInputStream(b);
      var o = CBORObject.Read(ms, options);
      if (ms.getPosition() != ms.length) {
 throw new CBORException("not at" +
"\u0020EOF");
}
 return o;
    }

    // Tests the equivalence of the DecodeFromBytes and Read methods.
    public static CBORObject FromBytesTestAB(byte[] b) {
      CBORObject oa = FromBytesA(b);
      CBORObject ob = FromBytesB(b);
      if (!oa.equals(ob)) {
        Assert.assertEquals(oa, ob);
      }
      return oa;
    }

    private static CBORObject FromBytesA(byte[] b) {
      return CBORObject.DecodeFromBytes(b);
    }

    private static CBORObject FromBytesB(byte[] b) {
      using java.io.ByteArrayInputStream ms = new java.io.ByteArrayInputStream(b);
      var o = CBORObject.Read(ms);
      if (ms.getPosition() != ms.length) {
 throw new CBORException("not at" +
"\u0020EOF");
}
 return o;
    }
  }
