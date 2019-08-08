package com.upokecenter.test;

import java.util.*;

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

    public static CBORObject RandomNumber(RandomGenerator rand) {
      Object o = null;
      switch (rand.UniformInt(6)) {
        case 0:
          o = RandomObjects.RandomDouble(
            rand,
            Integer.MAX_VALUE);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        case 1:
          o = RandomObjects.RandomSingle(
            rand,
            Integer.MAX_VALUE);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        case 2:
          return ToObjectTest.TestToFromObjectRoundTrip(
              RandomObjects.RandomEInteger(rand));
        case 3:
          return ToObjectTest.TestToFromObjectRoundTrip(
              RandomObjects.RandomEFloat(rand));
        case 4:
          o = RandomObjects.RandomEDecimal(rand);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        case 5:
          o = RandomObjects.RandomInt64(rand);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        default: throw new IllegalStateException();
      }
    }

    public static CBORObject RandomNumberOrRational(RandomGenerator rand) {
      Object o = null;
      switch (rand.UniformInt(7)) {
        case 0:
          o = RandomObjects.RandomDouble(
            rand,
            Integer.MAX_VALUE);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        case 1:
          o = RandomObjects.RandomSingle(
            rand,
            Integer.MAX_VALUE);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        case 2:
          return ToObjectTest.TestToFromObjectRoundTrip(
              RandomObjects.RandomEInteger(rand));
        case 3:
          return ToObjectTest.TestToFromObjectRoundTrip(
              RandomObjects.RandomEFloat(rand));
        case 4:
          o = RandomObjects.RandomEDecimal(rand);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        case 5:
          o = RandomObjects.RandomInt64(rand);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        case 6:
          o = RandomObjects.RandomERational(rand);
          return ToObjectTest.TestToFromObjectRoundTrip(o);
        default: throw new IllegalStateException();
      }
    }

    public static CBORObject RandomCBORMap(RandomGenerator rand, int depth) {
      int x = rand.UniformInt(100);
      int count = (x < 80) ? 2 : ((x < 93) ? 1 : ((x < 98) ? 0 : 10));
      CBORObject cborRet = CBORObject.NewMap();
      for (int i = 0; i < count; ++i) {
        CBORObject key = RandomCBORObject(rand, depth + 1);
        CBORObject value = RandomCBORObject(rand, depth + 1);
        cborRet.set(key, value);
      }
      return cborRet;
    }

    public static CBORObject RandomCBORTaggedObject(
      RandomGenerator rand,
      int depth) {
      int tag = 0;
      if (rand.UniformInt(2) == 0) {
        int[] tagselection = {
          2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 30, 30,
          30, 0, 1, 25, 26, 27,
        };
        tag = tagselection[rand.UniformInt(tagselection.length)];
      } else {
        tag = rand.UniformInt(0x1000000);
      }
      if (tag == 25) {
        tag = 0;
      }
      if (tag == 30) {
        Object o = RandomObjects.RandomByteString(rand);
        return ToObjectTest.TestToFromObjectRoundTrip(o);
      }
      {
        CBORObject cbor;
       // System.out.println("tag "+tag+" "+i);
        if (tag == 0 || tag == 1 || tag == 28 || tag == 29) {
          tag = 999;
        }
        if (tag == 2 || tag == 3) {
          Object o = RandomObjects.RandomByteStringShort(rand);
          cbor = ToObjectTest.TestToFromObjectRoundTrip(o);
        } else if (tag == 4 || tag == 5) {
          cbor = CBORObject.NewArray();
          Object o = RandomObjects.RandomSmallIntegral(rand);
          cbor.Add(ToObjectTest.TestToFromObjectRoundTrip(o));
          o = RandomObjects.RandomEInteger(rand);
          cbor.Add(ToObjectTest.TestToFromObjectRoundTrip(o));
        } else if (tag == 30) {
          cbor = CBORObject.NewArray();
          Object o = RandomObjects.RandomSmallIntegral(rand);
          cbor.Add(ToObjectTest.TestToFromObjectRoundTrip(o));
          o = RandomObjects.RandomEInteger(rand);
          cbor.Add(ToObjectTest.TestToFromObjectRoundTrip(o));
        } else {
          cbor = RandomCBORObject(rand, depth + 1);
        }
        try {
          cbor = CBORObject.FromObjectAndTag(cbor, tag);
         // System.out.println("done");
          return cbor;
        } catch (Exception ex) {
          return CBORObject.FromObjectAndTag(cbor, 999);
        }
      }
    }

    public static CBORObject RandomCBORArray(RandomGenerator rand, int depth) {
      int x = rand.UniformInt(100);
      int count = (x < 80) ? 2 : ((x < 93) ? 1 : ((x < 98) ? 0 : 10));
      CBORObject cborRet = CBORObject.NewArray();
      for (int i = 0; i < count; ++i) {
        cborRet.Add(RandomCBORObject(rand, depth + 1));
      }
      return cborRet;
    }

    public static CBORObject RandomCBORObject(RandomGenerator rand) {
      return RandomCBORObject(rand, 0);
    }

    public static CBORObject RandomCBORObject(RandomGenerator rand, int depth) {
      int nextval = rand.UniformInt(11);
      switch (nextval) {
        case 0:
        case 1:
        case 2:
        case 3:
          return RandomNumberOrRational(rand);
        case 4:
          return rand.UniformInt(2) == 0 ? CBORObject.True : CBORObject.False;
        case 5:
          return rand.UniformInt(2) == 0 ? CBORObject.Null :
            CBORObject.Undefined;
        case 6:
          return ToObjectTest.TestToFromObjectRoundTrip(
            RandomObjects.RandomTextString(rand));
        case 7:
          return ToObjectTest.TestToFromObjectRoundTrip(
            RandomObjects.RandomByteString(rand));
        case 8:
          return RandomCBORArray(rand, depth);
        case 9:
          return RandomCBORMap(rand, depth);
        case 10:
          return RandomCBORTaggedObject(rand, depth);
        default: return RandomNumber(rand);
      }
    }

@SuppressWarnings("deprecation")
    public static void TestNumber(CBORObject o) {
      if (o.getType() != CBORType.Number) {
        return;
      }
      if (o.IsPositiveInfinity() || o.IsNegativeInfinity() ||
          o.IsNaN()) {
        try {
          o.AsByte();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex);
          throw new IllegalStateException("", ex);
        }
        try {
          o.AsInt16();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex);
          throw new IllegalStateException("", ex);
        }
        try {
          o.AsInt32();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex);
          throw new IllegalStateException("", ex);
        }
        try {
          o.AsInt64();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex);
          throw new IllegalStateException("", ex);
        }
        try {
          o.AsSingle();
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        try {
          o.AsDouble();
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        try {
          o.AsEInteger();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
         // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex);
          throw new IllegalStateException("", ex);
        }
        return;
      }
      try {
        o.AsSingle();
      } catch (Exception ex) {
        Assert.fail("Object: " + o + ", " + ex);
        throw new
          IllegalStateException("", ex);
      }
      try {
        o.AsDouble();
      } catch (Exception ex) {
        Assert.fail("Object: " + o + ", " + ex);
        throw new
          IllegalStateException("", ex);
      }
    }

    public static void AssertRoundTrip(CBORObject o) {
      CBORObject o2 = FromBytesTestAB(o.EncodeToBytes());
      TestCommon.CompareTestEqual(o, o2);
      TestNumber(o);
      TestCommon.AssertEqualsHashCode(o, o2);
    }

    public static void AssertJSONSer(CBORObject o, String s) {
      if (!s.startsWith(o.ToJSONString())) {
        Assert.assertEquals("o is not equal to s",s,o.ToJSONString());
      }
     // Test round-tripping
      CBORObject o2 = FromBytesTestAB(o.EncodeToBytes());
      if (!s.startsWith(o2.ToJSONString())) {
        String msg = "o2 is not equal to s:\no = " +
          TestCommon.ToByteArrayString(o.EncodeToBytes()) +
          "\no2 = " + TestCommon.ToByteArrayString(o2.EncodeToBytes()) +
          "\no2string = " + o2.toString();
        Assert.assertEquals(msg, s, o2.ToJSONString());
      }
      TestNumber(o);
      TestCommon.AssertEqualsHashCode(o, o2);
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
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(b);
int startingAvailable = ms.available();

        CBORObject o = CBORObject.Read(ms);
        if ((startingAvailable-ms.available()) != startingAvailable) {
          throw new CBORException("not at EOF");
        }
        return o;
}
finally {
try { if (ms != null) {
 ms.close();
 } } catch (java.io.IOException ex) {}
}
}
    }
  }
