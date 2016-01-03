package com.upokecenter.test;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import java.io.*;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;

    /**
     * Contains CBOR tests.
     * @return Not documented yet.
     */

  public class CBORTest {
    public static void TestCBORMapAdd() {
      CBORObject cbor = CBORObject.NewMap();
      cbor.Add(1, 2);
      if (!(cbor.ContainsKey(CBORObject.FromObject(1))))Assert.fail();
      Assert.assertEquals((int)2, cbor.get(CBORObject.FromObject(1)));
      {
        String stringTemp = cbor.ToJSONString();
        Assert.assertEquals(
        "{\"1\":2}",
        stringTemp);
      }
      cbor.Add("hello", 2);
      if (!(cbor.ContainsKey("hello")))Assert.fail();
      if (!(cbor.ContainsKey(CBORObject.FromObject("hello"))))Assert.fail();
      Assert.assertEquals((int)2, cbor.get("hello"));
      cbor.Set(1, 3);
      if (!(cbor.ContainsKey(CBORObject.FromObject(1))))Assert.fail();
      Assert.assertEquals((int)3, cbor.get(CBORObject.FromObject(1)));
    }

    @Test
    public void TestArray() {
      CBORObject cbor = CBORObject.FromJSONString("[]");
      cbor.Add(CBORObject.FromObject(3));
      cbor.Add(CBORObject.FromObject(4));
      byte[] bytes = cbor.EncodeToBytes();
      TestCommon.AssertByteArraysEqual(
        new byte[] { (byte)(0x80 | 2), 3, 4  },
        bytes);
      cbor = CBORObject.FromObject(new String[] { "a", "b", "c", "d", "e" });
      Assert.assertEquals("[\"a\",\"b\",\"c\",\"d\",\"e\"]", cbor.ToJSONString());
      TestCommon.AssertRoundTrip(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0x9f, 0, 1, 2, 3, 4, 5,
                    6, 7, (byte)0xff  });
      {
        String stringTemp = cbor.ToJSONString();
        Assert.assertEquals(
        "[0,1,2,3,4,5,6,7]",
        stringTemp);
      }
    }

    @Test
    public void TestAsByte() {
      for (int i = 0; i < 255; ++i) {
        Assert.assertEquals((byte)i, CBORObject.FromObject(i).AsByte());
      }
      for (int i = -200; i < 0; ++i) {
        try {
          CBORObject.FromObject(i).AsByte();
        } catch (ArithmeticException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail(ex.toString()); throw new
            IllegalStateException("", ex);
        }
      }
      for (int i = 256; i < 512; ++i) {
        try {
          CBORObject.FromObject(i).AsByte();
        } catch (ArithmeticException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail(ex.toString()); throw new
            IllegalStateException("", ex);
        }
      }
    }

    @Test
    public void TestBigInteger() {
      BigInteger bi = BigInteger.valueOf(3);
      BigInteger negseven = BigInteger.valueOf(-7);
      for (int i = 0; i < 500; ++i) {
        TestCommon.AssertSer(
          CBORObject.FromObject(bi),
          bi.toString());
        if (!(CBORObject.FromObject(bi).isIntegral()))Assert.fail();
        TestCommon.AssertRoundTrip(CBORObject.FromObject(bi));

        TestCommon.AssertRoundTrip(CBORObject.FromObject(
          ExtendedDecimal.Create(
            bi,
            BigInteger.valueOf(1))));
        bi = bi.multiply(negseven);
      }
      BigInteger[] ranges = {
        BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.valueOf(512)),
        BigInteger.valueOf(Long.MIN_VALUE).add(BigInteger.valueOf(512)),
        BigInteger.valueOf(0).subtract(BigInteger.valueOf(512)), BigInteger.valueOf(0).add(BigInteger.valueOf(512)),
        BigInteger.valueOf(Long.MAX_VALUE).subtract(BigInteger.valueOf(512)),
        BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.valueOf(512)),
        ((BigInteger.valueOf(1).shiftLeft(64)).subtract(BigInteger.valueOf(1))).subtract(BigInteger.valueOf(512)),
        ((BigInteger.valueOf(1).shiftLeft(64)).subtract(BigInteger.valueOf(1))).add(BigInteger.valueOf(512)),
      };
      for (int i = 0; i < ranges.length; i += 2) {
        BigInteger bigintTemp = ranges[i];
        while (true) {
          TestCommon.AssertSer(
            CBORObject.FromObject(bigintTemp),
            bigintTemp.toString());
          if (bigintTemp.equals(ranges[i + 1])) {
            break;
          }
          bigintTemp = bigintTemp.add(BigInteger.valueOf(1));
        }
      }
    }

    @Test
    public void TestBigNumBytes() {
      CBORObject o = null;
      o = TestCommon.FromBytesTestAB(new byte[] { (byte)0xc2, 0x41, (byte)0x88  });
      Assert.assertEquals(BigInteger.valueOf(0x88L), o.AsBigInteger());
      o = TestCommon.FromBytesTestAB(new byte[] { (byte)0xc2, 0x42, (byte)0x88, 0x77  });
      Assert.assertEquals(BigInteger.valueOf(0x8877L), o.AsBigInteger());
      o = TestCommon.FromBytesTestAB(new byte[] { (byte)0xc2, 0x44, (byte)0x88, 0x77, 0x66,
        0x55  });
      Assert.assertEquals(BigInteger.valueOf(0x88776655L), o.AsBigInteger());
      o = TestCommon.FromBytesTestAB(new byte[] { (byte)0xc2, 0x47, (byte)0x88, 0x77, 0x66,
        0x55, 0x44, 0x33, 0x22  });
      Assert.assertEquals(BigInteger.valueOf(0x88776655443322L), o.AsBigInteger());
    }

    @Test
    public void TestBoolean() {
      TestCommon.AssertSer(CBORObject.True, "true");
      TestCommon.AssertSer(CBORObject.False, "false");
      Assert.assertEquals(CBORObject.True, CBORObject.FromObject(true));
      Assert.assertEquals(CBORObject.False, CBORObject.FromObject(false));
    }

    @Test
    public void TestByte() {
      for (int i = 0; i <= 255; ++i) {
        TestCommon.AssertSer(
          CBORObject.FromObject((byte)i),
          "" + i);
      }
    }

    @Test
    public void TestByteArray() {
      TestCommon.AssertSer(
        CBORObject.FromObject(new byte[] { 0x20, 0x78  }),
        "h'2078'");
    }

    @Test
    public void TestByteStringStream() {
      TestCommon.FromBytesTestAB(
        new byte[] { 0x5f, 0x41, 0x20, 0x41, 0x20, (byte)0xff  });
    }
    @Test(expected = CBORException.class)
    public void TestByteStringStreamNoIndefiniteWithinDefinite() {
      TestCommon.FromBytesTestAB(new byte[] { 0x5f, 0x41, 0x20, 0x5f, 0x41,
        0x20, (byte)0xff, (byte)0xff  });
    }
    @Test(expected = CBORException.class)
    public void TestByteStringStreamNoTagsBeforeDefinite() {
      TestCommon.FromBytesTestAB(new byte[] { 0x5f, 0x41, 0x20, (byte)0xc2, 0x41,
        0x20, (byte)0xff  });
    }

    @Test
    public void TestCanFitIn() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 5000; ++i) {
        CBORObject ed = RandomObjects.RandomNumber(r);
        ExtendedDecimal ed2;
        ed2 = ExtendedDecimal.FromDouble(ed.AsExtendedDecimal().ToDouble());
        if ((ed.AsExtendedDecimal().compareTo(ed2) == 0) !=
            ed.CanFitInDouble()) {
          Assert.fail(TestCommon.ToByteArrayString(ed) + "; /" + "/ " +
            ed.ToJSONString());
        }
        ed2 = ExtendedDecimal.FromSingle(ed.AsExtendedDecimal().ToSingle());
        if ((ed.AsExtendedDecimal().compareTo(ed2) == 0) !=
            ed.CanFitInSingle()) {
          Assert.fail(TestCommon.ToByteArrayString(ed) + "; /" + "/ " +
            ed.ToJSONString());
        }
        if (!ed.IsInfinity() && !ed.IsNaN()) {
          ed2 = ExtendedDecimal.FromBigInteger(ed.AsExtendedDecimal()
                    .ToBigInteger());
          if ((ed.AsExtendedDecimal().compareTo(ed2) == 0) != ed.isIntegral()) {
            Assert.fail(TestCommon.ToByteArrayString(ed) + "; /" + "/ " +
                    ed.ToJSONString());
          }
        }
        if (!ed.IsInfinity() && !ed.IsNaN()) {
          BigInteger bi = ed.AsBigInteger();
          if (ed.isIntegral()) {
            if (bi.canFitInInt() != ed.CanFitInInt32()) {
              Assert.fail(TestCommon.ToByteArrayString(ed) + "; /" + "/ " +
                    ed.ToJSONString());
            }
          }
          if (bi.canFitInInt() != ed.CanTruncatedIntFitInInt32()) {
            Assert.fail(TestCommon.ToByteArrayString(ed) + "; /" + "/ " +
                    ed.ToJSONString());
          }
          if (ed.isIntegral()) {
            if ((bi.bitLength() <= 63) != ed.CanFitInInt64()) {
              Assert.fail(TestCommon.ToByteArrayString(ed) + "; /" + "/ " +
                    ed.ToJSONString());
            }
          }
          if ((bi.bitLength() <= 63) != ed.CanTruncatedIntFitInInt64()) {
            Assert.fail(TestCommon.ToByteArrayString(ed) + "; /" + "/ " +
                    ed.ToJSONString());
          }
        }
      }
    }

    @Test
    public void TestCanFitInSpecificCases() {
      CBORObject cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xfb,
        0x41, (byte)0xe0, (byte)0x85, 0x48, 0x2d, 0x14, 0x47, 0x7a  });  // 2217361768.63373
      Assert.assertEquals(
BigIntegerTest.BigFromString("2217361768"),
cbor.AsBigInteger());
      if (cbor.AsBigInteger().canFitInInt())Assert.fail();
      if (cbor.CanTruncatedIntFitInInt32())Assert.fail();
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc5, (byte)0x82,
        0x18, 0x2f, 0x32  });  // -2674012278751232
      Assert.assertEquals(52, cbor.AsBigInteger().bitLength());
      if (!(cbor.CanFitInInt64()))Assert.fail();
      if (CBORObject.FromObject(2554895343L).CanFitInSingle())Assert.fail();
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc5, (byte)0x82,
        0x10, 0x38, 0x64  });  // -6619136
      Assert.assertEquals(BigInteger.valueOf(-6619136), cbor.AsBigInteger());
      Assert.assertEquals(-6619136, cbor.AsBigInteger().intValueChecked());
      Assert.assertEquals(-6619136, cbor.AsInt32());
      if (!(cbor.AsBigInteger().canFitInInt()))Assert.fail();
      if (!(cbor.CanTruncatedIntFitInInt32()))Assert.fail();
    }

    @Test
    public void TestCBORBigInteger() {
      CBORObject o = CBORObject.DecodeFromBytes(new byte[] { 0x3b, (byte)0xce,
        (byte)0xe2, 0x5a, 0x57, (byte)0xd8, 0x21, (byte)0xb9, (byte)0xa7  });
      Assert.assertEquals(
        BigIntegerTest.BigFromString("-14907577049884506536"),
        o.AsBigInteger());
    }

    @Test
    public void TestCBORExceptions() {
      try {
        CBORObject.NewArray().Remove(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().Remove(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().Add(CBORObject.Null);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().Add(CBORObject.True);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.Remove(CBORObject.True);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject(0).Remove(CBORObject.True);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject("").Remove(CBORObject.True);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().AsExtendedFloat();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsExtendedFloat();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsExtendedFloat();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsExtendedFloat();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsExtendedFloat();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject("").AsExtendedFloat();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCBORFromArray() {
      CBORObject o = CBORObject.FromObject(new int[] { 1, 2, 3 });
      Assert.assertEquals(3, o.size());
      Assert.assertEquals(1, o.get(0).AsInt32());
      Assert.assertEquals(2, o.get(1).AsInt32());
      Assert.assertEquals(3, o.get(2).AsInt32());
      TestCommon.AssertRoundTrip(o);
    }

    @Test
    public void TestCBORInfinity() {
      {
        String stringTemp =
          CBORObject.FromObject(ExtendedRational.NegativeInfinity).toString();
        Assert.assertEquals(
        "-Infinity",
        stringTemp);
      }
      {
        String stringTemp =
          CBORObject.FromObject(ExtendedRational.PositiveInfinity).toString();
        Assert.assertEquals(
        "Infinity",
        stringTemp);
      }

  TestCommon.AssertRoundTrip(CBORObject.FromObject(ExtendedRational.NegativeInfinity));

  TestCommon.AssertRoundTrip(CBORObject.FromObject(ExtendedRational.PositiveInfinity));
      if (!(CBORObject.FromObject(ExtendedRational.NegativeInfinity)
                    .IsInfinity()))Assert.fail();
      if (!(CBORObject.FromObject(ExtendedRational.PositiveInfinity)
                    .IsInfinity()))Assert.fail();
      if (!(CBORObject.FromObject(ExtendedRational.NegativeInfinity)
                    .IsNegativeInfinity()))Assert.fail();
      if (!(CBORObject.FromObject(ExtendedRational.PositiveInfinity)
                    .IsPositiveInfinity()))Assert.fail();
      if (!(CBORObject.PositiveInfinity.IsInfinity()))Assert.fail();
      if (!(CBORObject.PositiveInfinity.IsPositiveInfinity()))Assert.fail();
      if (!(CBORObject.NegativeInfinity.IsInfinity()))Assert.fail();
      if (!(CBORObject.NegativeInfinity.IsNegativeInfinity()))Assert.fail();
      if (!(CBORObject.NaN.IsNaN()))Assert.fail();

  TestCommon.AssertRoundTrip(CBORObject.FromObject(ExtendedDecimal.NegativeInfinity));

  TestCommon.AssertRoundTrip(CBORObject.FromObject(ExtendedFloat.NegativeInfinity));

  TestCommon.AssertRoundTrip(CBORObject.FromObject(Double.NEGATIVE_INFINITY));

  TestCommon.AssertRoundTrip(CBORObject.FromObject(Float.NEGATIVE_INFINITY));

  TestCommon.AssertRoundTrip(CBORObject.FromObject(ExtendedDecimal.PositiveInfinity));

  TestCommon.AssertRoundTrip(CBORObject.FromObject(ExtendedFloat.PositiveInfinity));

  TestCommon.AssertRoundTrip(CBORObject.FromObject(Double.POSITIVE_INFINITY));

  TestCommon.AssertRoundTrip(CBORObject.FromObject(Float.POSITIVE_INFINITY));
    }

    @Test
    public void TestCompareB() {
      if (!(CBORObject.DecodeFromBytes(new byte[] { (byte)0xfa, 0x7f,
        (byte)0x80, 0x00, 0x00  }).AsExtendedRational().IsInfinity()))Assert.fail();
      {
    CBORObject objectTemp = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc5,
  (byte)0x82, 0x38, (byte)0xc7, 0x3b, 0x00, 0x00, 0x08, (byte)0xbf,
  (byte)0xda, (byte)0xaf, 0x73, 0x46  });
   CBORObject objectTemp2 = CBORObject.DecodeFromBytes(new byte[] { 0x3b, 0x5a,
  (byte)0x9b, (byte)0x9a, (byte)0x9c, (byte)0xb4, (byte)0x95, (byte)0xbf,
  0x71  });
AddSubCompare(objectTemp, objectTemp2);
}
      {
    CBORObject objectTemp = CBORObject.DecodeFromBytes(new byte[] { (byte)0xfa,
  0x1f, (byte)0x80, (byte)0xdb, (byte)0x9b  });
   CBORObject objectTemp2 = CBORObject.DecodeFromBytes(new byte[] { (byte)0xfb,
  0x31, (byte)0x90, (byte)0xea, 0x16, (byte)0xbe, (byte)0x80, 0x0b, 0x37  });
AddSubCompare(objectTemp, objectTemp2);
}
      CBORObject cbor = CBORObject.FromObjectAndTag(
        Double.NEGATIVE_INFINITY,
        1956611);
      TestCommon.AssertRoundTrip(cbor);
      cbor =

        CBORObject.FromObjectAndTag(
          CBORObject.FromObject(Double.NEGATIVE_INFINITY),
          1956611);
      TestCommon.AssertRoundTrip(cbor);
      cbor =

        CBORObject.FromObjectAndTag(
          CBORObject.FromObject(ExtendedFloat.NegativeInfinity),
          1956611);
      TestCommon.AssertRoundTrip(cbor);
      cbor =

        CBORObject.FromObjectAndTag(
          CBORObject.FromObject(ExtendedDecimal.NegativeInfinity),
          1956611);
      TestCommon.AssertRoundTrip(cbor);
      cbor =

        CBORObject.FromObjectAndTag(
          CBORObject.FromObject(ExtendedRational.NegativeInfinity),
          1956611);
      TestCommon.AssertRoundTrip(cbor);
    }

    @Test
    public void TestDecFracCompareIntegerVsBigFraction() {
      CBORObject o1 = null;
      CBORObject o2 = null;
      o1 = CBORObject.DecodeFromBytes(new byte[] { (byte)0xfb, (byte)0x8b, 0x44,
        (byte)0xf2, (byte)0xa9, 0x0c, 0x27, 0x42, 0x28  });
      o2 = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc5, (byte)0x82, 0x38,
        (byte)0xa4, (byte)0xc3, 0x50, 0x02, (byte)0x98, (byte)0xc5, (byte)0xa8,
        0x02, (byte)0xc1, (byte)0xf6, (byte)0xc0, 0x1a, (byte)0xbe, 0x08,
          0x04, (byte)0x86, (byte)0x99, 0x3e, (byte)0xf1  });
      AddSubCompare(o1, o2);
    }

    @Test
    public void TestDecimalFrac() {
      TestCommon.FromBytesTestAB(
        new byte[] { (byte)0xc4, (byte)0x82, 0x3, 0x1a, 1, 2, 3, 4  });
    }
    @Test(expected = CBORException.class)
    public void TestDecimalFracExactlyTwoElements() {
      TestCommon.FromBytesTestAB(new byte[] { (byte)0xc4, (byte)0x82, (byte)0xc2, 0x41, 1  });
    }
    @Test(expected = CBORException.class)
    public void TestDecimalFracExponentMustNotBeBignum() {
      TestCommon.FromBytesTestAB(new byte[] { (byte)0xc4, (byte)0x82, (byte)0xc2, 0x41, 1, 0x1a,
        1, 2, 3, 4  });
    }

    @Test
    public void TestDecimalFracMantissaMayBeBignum() {
      CBORObject o = TestCommon.FromBytesTestAB(
        new byte[] { (byte)0xc4, (byte)0x82, 0x3, (byte)0xc2, 0x41, 1  });
      Assert.assertEquals(
        ExtendedDecimal.Create(BigInteger.valueOf(1), BigInteger.valueOf(3)),
        o.AsExtendedDecimal());
    }

    private static void AssertDecimalsEquivalent(String a, String b) {
      CBORObject ca = CBORDataUtilities.ParseJSONNumber(a);
      CBORObject cb = CBORDataUtilities.ParseJSONNumber(b);
      TestCommon.CompareTestEqual(ca, cb);
      TestCommon.AssertRoundTrip(ca);
      TestCommon.AssertRoundTrip(cb);
    }

    @Test
    public void TestDecimalsEquivalent() {
      AssertDecimalsEquivalent("1.310E-7", "131.0E-9");
      AssertDecimalsEquivalent("0.001231", "123.1E-5");
      AssertDecimalsEquivalent("3.0324E+6", "303.24E4");
      AssertDecimalsEquivalent("3.726E+8", "372.6E6");
      AssertDecimalsEquivalent("2663.6", "266.36E1");
      AssertDecimalsEquivalent("34.24", "342.4E-1");
      AssertDecimalsEquivalent("3492.5", "349.25E1");
      AssertDecimalsEquivalent("0.31919", "319.19E-3");
      AssertDecimalsEquivalent("2.936E-7", "293.6E-9");
      AssertDecimalsEquivalent("6.735E+10", "67.35E9");
      AssertDecimalsEquivalent("7.39E+10", "7.39E10");
      AssertDecimalsEquivalent("0.0020239", "202.39E-5");
      AssertDecimalsEquivalent("1.6717E+6", "167.17E4");
      AssertDecimalsEquivalent("1.7632E+9", "176.32E7");
      AssertDecimalsEquivalent("39.526", "395.26E-1");
      AssertDecimalsEquivalent("0.002939", "29.39E-4");
      AssertDecimalsEquivalent("0.3165", "316.5E-3");
      AssertDecimalsEquivalent("3.7910E-7", "379.10E-9");
      AssertDecimalsEquivalent("0.000016035", "160.35E-7");
      AssertDecimalsEquivalent("0.001417", "141.7E-5");
      AssertDecimalsEquivalent("7.337E+5", "73.37E4");
      AssertDecimalsEquivalent("3.4232E+12", "342.32E10");
      AssertDecimalsEquivalent("2.828E+8", "282.8E6");
      AssertDecimalsEquivalent("4.822E-7", "48.22E-8");
      AssertDecimalsEquivalent("2.6328E+9", "263.28E7");
      AssertDecimalsEquivalent("2.9911E+8", "299.11E6");
      AssertDecimalsEquivalent("3.636E+9", "36.36E8");
      AssertDecimalsEquivalent("0.20031", "200.31E-3");
      AssertDecimalsEquivalent("1.922E+7", "19.22E6");
      AssertDecimalsEquivalent("3.0924E+8", "309.24E6");
      AssertDecimalsEquivalent("2.7236E+7", "272.36E5");
      AssertDecimalsEquivalent("0.01645", "164.5E-4");
      AssertDecimalsEquivalent("0.000292", "29.2E-5");
      AssertDecimalsEquivalent("1.9939", "199.39E-2");
      AssertDecimalsEquivalent("2.7929E+9", "279.29E7");
      AssertDecimalsEquivalent("1.213E+7", "121.3E5");
      AssertDecimalsEquivalent("2.765E+6", "276.5E4");
      AssertDecimalsEquivalent("270.11", "270.11E0");
      AssertDecimalsEquivalent("0.017718", "177.18E-4");
      AssertDecimalsEquivalent("0.003607", "360.7E-5");
      AssertDecimalsEquivalent("0.00038618", "386.18E-6");
      AssertDecimalsEquivalent("0.0004230", "42.30E-5");
      AssertDecimalsEquivalent("1.8410E+5", "184.10E3");
      AssertDecimalsEquivalent("0.00030427", "304.27E-6");
      AssertDecimalsEquivalent("6.513E+6", "65.13E5");
      AssertDecimalsEquivalent("0.06717", "67.17E-3");
      AssertDecimalsEquivalent("0.00031123", "311.23E-6");
      AssertDecimalsEquivalent("0.0031639", "316.39E-5");
      AssertDecimalsEquivalent("1.146E+5", "114.6E3");
      AssertDecimalsEquivalent("0.00039937", "399.37E-6");
      AssertDecimalsEquivalent("3.3817", "338.17E-2");
      AssertDecimalsEquivalent("0.00011128", "111.28E-6");
      AssertDecimalsEquivalent("7.818E+7", "78.18E6");
      AssertDecimalsEquivalent("2.6417E-7", "264.17E-9");
      AssertDecimalsEquivalent("1.852E+9", "185.2E7");
      AssertDecimalsEquivalent("0.0016216", "162.16E-5");
      AssertDecimalsEquivalent("2.2813E+6", "228.13E4");
      AssertDecimalsEquivalent("3.078E+12", "307.8E10");
      AssertDecimalsEquivalent("0.00002235", "22.35E-6");
      AssertDecimalsEquivalent("0.0032827", "328.27E-5");
      AssertDecimalsEquivalent("1.334E+9", "133.4E7");
      AssertDecimalsEquivalent("34.022", "340.22E-1");
      AssertDecimalsEquivalent("7.19E+6", "7.19E6");
      AssertDecimalsEquivalent("35.311", "353.11E-1");
      AssertDecimalsEquivalent("3.4330E+6", "343.30E4");
      AssertDecimalsEquivalent("0.000022923", "229.23E-7");
      AssertDecimalsEquivalent("2.899E+4", "289.9E2");
      AssertDecimalsEquivalent("0.00031", "3.1E-4");
      AssertDecimalsEquivalent("2.0418E+5", "204.18E3");
      AssertDecimalsEquivalent("3.3412E+11", "334.12E9");
      AssertDecimalsEquivalent("1.717E+10", "171.7E8");
      AssertDecimalsEquivalent("2.7024E+10", "270.24E8");
      AssertDecimalsEquivalent("1.0219E+9", "102.19E7");
      AssertDecimalsEquivalent("15.13", "151.3E-1");
      AssertDecimalsEquivalent("91.23", "91.23E0");
      AssertDecimalsEquivalent("3.4114E+6", "341.14E4");
      AssertDecimalsEquivalent("33.832", "338.32E-1");
      AssertDecimalsEquivalent("0.19234", "192.34E-3");
      AssertDecimalsEquivalent("16835", "168.35E2");
      AssertDecimalsEquivalent("0.00038610", "386.10E-6");
      AssertDecimalsEquivalent("1.6624E+9", "166.24E7");
      AssertDecimalsEquivalent("2.351E+9", "235.1E7");
      AssertDecimalsEquivalent("0.03084", "308.4E-4");
      AssertDecimalsEquivalent("0.00429", "42.9E-4");
      AssertDecimalsEquivalent("9.718E-8", "97.18E-9");
      AssertDecimalsEquivalent("0.00003121", "312.1E-7");
      AssertDecimalsEquivalent("3.175E+4", "317.5E2");
      AssertDecimalsEquivalent("376.6", "376.6E0");
      AssertDecimalsEquivalent("0.0000026110", "261.10E-8");
      AssertDecimalsEquivalent("7.020E+11", "70.20E10");
      AssertDecimalsEquivalent("2.1533E+9", "215.33E7");
      AssertDecimalsEquivalent("3.8113E+7", "381.13E5");
      AssertDecimalsEquivalent("7.531", "75.31E-1");
      AssertDecimalsEquivalent("991.0", "99.10E1");
      AssertDecimalsEquivalent("2.897E+8", "289.7E6");
      AssertDecimalsEquivalent("0.0000033211", "332.11E-8");
      AssertDecimalsEquivalent("0.03169", "316.9E-4");
      AssertDecimalsEquivalent("2.7321E+12", "273.21E10");
      AssertDecimalsEquivalent("394.38", "394.38E0");
      AssertDecimalsEquivalent("5.912E+7", "59.12E6");
    }

    @Test
    public void TestDivide() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 =
          CBORObject.FromObject(RandomObjects.RandomBigInteger(r));
      CBORObject o2 = CBORObject.FromObject(RandomObjects.RandomBigInteger(r));
        if (o2.isZero()) {
          continue;
        }
        ExtendedRational er = new ExtendedRational(o1.AsBigInteger(), o2.AsBigInteger());
        if (er.compareTo(CBORObject.Divide(o1, o2).AsExtendedRational()) != 0) {
          Assert.fail(TestCommon.ObjectMessages(o1, o2, "Results don't match"));
        }
      }
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 = RandomObjects.RandomNumber(r);
        CBORObject o2 = RandomObjects.RandomNumber(r);
        ExtendedRational er =
          o1.AsExtendedRational().Divide(o2.AsExtendedRational());
        if (er.compareTo(CBORObject.Divide(o1, o2).AsExtendedRational()) != 0) {
          Assert.fail(TestCommon.ObjectMessages(o1, o2, "Results don't match"));
        }
      }
      try {
 ExtendedDecimal.FromString("1").Divide(ExtendedDecimal.FromString("3"), null);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestDouble() {
      if
        (!CBORObject.FromObject(Double.POSITIVE_INFINITY).IsPositiveInfinity()) {
        Assert.fail("Not positive infinity");
      }
      TestCommon.AssertSer(
        CBORObject.FromObject(Double.POSITIVE_INFINITY),
        "Infinity");
      TestCommon.AssertSer(
        CBORObject.FromObject(Double.NEGATIVE_INFINITY),
        "-Infinity");
      TestCommon.AssertSer(
        CBORObject.FromObject(Double.NaN),
        "NaN");
      CBORObject oldobj = null;
      for (int i = -65539; i <= 65539; ++i) {
        CBORObject o = CBORObject.FromObject((double)i);
        if (!(o.CanFitInDouble()))Assert.fail();
        if (!(o.CanFitInInt32()))Assert.fail();
        if (!(o.isIntegral()))Assert.fail();
        TestCommon.AssertSer(
          o,
          TestCommon.IntToString(i));
        if (oldobj != null) {
          TestCommon.CompareTestLess(oldobj, o);
        }
        oldobj = o;
      }
    }

    @Test
    public void TestExample() {
      // The following creates a CBOR map and adds
      // several kinds of objects to it
      CBORObject cbor = CBORObject.NewMap().Add("item", "any String")
        .Add("number", 42).Add("map", CBORObject.NewMap().Add("number", 42))
        .Add("array", CBORObject.NewArray().Add(999f).Add("xyz"))
        .Add("bytes", new byte[] { 0, 1, 2  });
      // The following converts the map to CBOR
      byte[] bytes = cbor.EncodeToBytes();
      // The following converts the map to JSON
      String json = cbor.ToJSONString();
    }

    @Test(timeout = 5000)
    public void TestExtendedExtremeExponent() {
      // Values with extremely high or extremely low exponents;
      // we just check whether this test method runs reasonably fast
      // for all these test cases
      CBORObject obj;
      obj = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc4, (byte)0x82,
     0x3a, 0x00, 0x1c, 0x2d, 0x0d, 0x1a, 0x13, 0x6c, (byte)0xa1, (byte)0x97  });
      TestCommon.AssertRoundTrip(obj);
      obj = CBORObject.DecodeFromBytes(new byte[] { (byte)0xda, 0x00, 0x14,
        0x57, (byte)0xce, (byte)0xc5, (byte)0x82, 0x1a, 0x46, 0x5a, 0x37,
        (byte)0x87, (byte)0xc3, 0x50, 0x5e, (byte)0xec, (byte)0xfd, 0x73,
          0x50, 0x64, (byte)0xa1, 0x1f, 0x10, (byte)0xc4, (byte)0xff,
          (byte)0xf2, (byte)0xc4, (byte)0xc9, 0x65, 0x12  });
      TestCommon.AssertRoundTrip(obj);
      int actual = CBORObject.FromObject(
        ExtendedDecimal.Create(BigInteger.valueOf(333333), BigInteger.valueOf(-2)))
        .compareTo(CBORObject.FromObject(ExtendedFloat.Create(
          BigInteger.valueOf(5234222),
          BigInteger.valueOf(-24936668661488L))));
      Assert.assertEquals(1, actual);
    }

    @Test
    public void TestExtendedFloatDouble() {
      TestExtendedFloatDoubleCore(3.5, "3.5");
      TestExtendedFloatDoubleCore(7, "7");
      TestExtendedFloatDoubleCore(1.75, "1.75");
      TestExtendedFloatDoubleCore(3.5, "3.5");
      TestExtendedFloatDoubleCore((double)Integer.MIN_VALUE, "-2147483648");
      TestExtendedFloatDoubleCore(
        (double)Long.MIN_VALUE,
        "-9223372036854775808");
      FastRandom rand = new FastRandom();
      for (int i = 0; i < 2047; ++i) {
        // Try a random double with a given
        // exponent
        TestExtendedFloatDoubleCore(RandomObjects.RandomDouble(rand, i), null);
        TestExtendedFloatDoubleCore(RandomObjects.RandomDouble(rand, i), null);
        TestExtendedFloatDoubleCore(RandomObjects.RandomDouble(rand, i), null);
        TestExtendedFloatDoubleCore(RandomObjects.RandomDouble(rand, i), null);
      }
    }

    @Test
    public void TestExtendedFloatSingle() {
      FastRandom rand = new FastRandom();
      for (int i = 0; i < 255; ++i) {
        // Try a random float with a given
        // exponent
        TestExtendedFloatSingleCore(RandomObjects.RandomSingle(rand, i), null);
        TestExtendedFloatSingleCore(RandomObjects.RandomSingle(rand, i), null);
        TestExtendedFloatSingleCore(RandomObjects.RandomSingle(rand, i), null);
        TestExtendedFloatSingleCore(RandomObjects.RandomSingle(rand, i), null);
      }
    }

    @Test
    public void TestExtendedInfinity() {
      if (!(ExtendedDecimal.PositiveInfinity.IsPositiveInfinity()))Assert.fail();
      if (ExtendedDecimal.PositiveInfinity.IsNegativeInfinity())Assert.fail();
      if (ExtendedDecimal.PositiveInfinity.isNegative())Assert.fail();
      if (ExtendedDecimal.NegativeInfinity.IsPositiveInfinity())Assert.fail();
      if (!(ExtendedDecimal.NegativeInfinity.IsNegativeInfinity()))Assert.fail();
      if (!(ExtendedDecimal.NegativeInfinity.isNegative()))Assert.fail();
      if (!(ExtendedFloat.PositiveInfinity.IsInfinity()))Assert.fail();
      if (!(ExtendedFloat.PositiveInfinity.IsPositiveInfinity()))Assert.fail();
      if (ExtendedFloat.PositiveInfinity.IsNegativeInfinity())Assert.fail();
      if (ExtendedFloat.PositiveInfinity.isNegative())Assert.fail();
      if (!(ExtendedFloat.NegativeInfinity.IsInfinity()))Assert.fail();
      if (ExtendedFloat.NegativeInfinity.IsPositiveInfinity())Assert.fail();
      if (!(ExtendedFloat.NegativeInfinity.IsNegativeInfinity()))Assert.fail();
      if (!(ExtendedFloat.NegativeInfinity.isNegative()))Assert.fail();
      if (!(ExtendedRational.PositiveInfinity.IsInfinity()))Assert.fail();
      if (!(ExtendedRational.PositiveInfinity.IsPositiveInfinity()))Assert.fail();
      if (ExtendedRational.PositiveInfinity.IsNegativeInfinity())Assert.fail();
      if (ExtendedRational.PositiveInfinity.isNegative())Assert.fail();
      if (!(ExtendedRational.NegativeInfinity.IsInfinity()))Assert.fail();
      if (ExtendedRational.NegativeInfinity.IsPositiveInfinity())Assert.fail();
      if (!(ExtendedRational.NegativeInfinity.IsNegativeInfinity()))Assert.fail();
      if (!(ExtendedRational.NegativeInfinity.isNegative()))Assert.fail();

      Assert.assertEquals(
        ExtendedDecimal.PositiveInfinity,
        ExtendedDecimal.FromDouble(Double.POSITIVE_INFINITY));
      Assert.assertEquals(
        ExtendedDecimal.NegativeInfinity,
        ExtendedDecimal.FromDouble(Double.NEGATIVE_INFINITY));
      Assert.assertEquals(
        ExtendedDecimal.PositiveInfinity,
        ExtendedDecimal.FromSingle(Float.POSITIVE_INFINITY));
      Assert.assertEquals(
        ExtendedDecimal.NegativeInfinity,
        ExtendedDecimal.FromSingle(Float.NEGATIVE_INFINITY));

      Assert.assertEquals(
        ExtendedFloat.PositiveInfinity,
        ExtendedFloat.FromDouble(Double.POSITIVE_INFINITY));
      Assert.assertEquals(
        ExtendedFloat.NegativeInfinity,
        ExtendedFloat.FromDouble(Double.NEGATIVE_INFINITY));
      Assert.assertEquals(
        ExtendedFloat.PositiveInfinity,
        ExtendedFloat.FromSingle(Float.POSITIVE_INFINITY));
      Assert.assertEquals(
        ExtendedFloat.NegativeInfinity,
        ExtendedFloat.FromSingle(Float.NEGATIVE_INFINITY));

      Assert.assertEquals(
        ExtendedRational.PositiveInfinity,
        ExtendedRational.FromDouble(Double.POSITIVE_INFINITY));
      Assert.assertEquals(
        ExtendedRational.NegativeInfinity,
        ExtendedRational.FromDouble(Double.NEGATIVE_INFINITY));
      Assert.assertEquals(
        ExtendedRational.PositiveInfinity,
        ExtendedRational.FromSingle(Float.POSITIVE_INFINITY));
      Assert.assertEquals(
        ExtendedRational.NegativeInfinity,
        ExtendedRational.FromSingle(Float.NEGATIVE_INFINITY));

      Assert.assertEquals(
        ExtendedRational.PositiveInfinity,
        ExtendedRational.FromExtendedDecimal(ExtendedDecimal.PositiveInfinity));
      Assert.assertEquals(
        ExtendedRational.NegativeInfinity,
        ExtendedRational.FromExtendedDecimal(ExtendedDecimal.NegativeInfinity));
      Assert.assertEquals(
        ExtendedRational.PositiveInfinity,
        ExtendedRational.FromExtendedFloat(ExtendedFloat.PositiveInfinity));
      Assert.assertEquals(
        ExtendedRational.NegativeInfinity,
        ExtendedRational.FromExtendedFloat(ExtendedFloat.NegativeInfinity));

  if (!(((ExtendedRational.PositiveInfinity.ToDouble()) == Double.POSITIVE_INFINITY)))Assert.fail();

  if (!(((ExtendedRational.NegativeInfinity.ToDouble()) == Double.NEGATIVE_INFINITY)))Assert.fail();

  if (!(((ExtendedRational.PositiveInfinity.ToSingle()) == Float.POSITIVE_INFINITY)))Assert.fail();

  if (!(((ExtendedRational.NegativeInfinity.ToSingle()) == Float.NEGATIVE_INFINITY)))Assert.fail();
      try {
        ExtendedDecimal.PositiveInfinity.ToBigInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ExtendedDecimal.NegativeInfinity.ToBigInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ExtendedFloat.PositiveInfinity.ToBigInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ExtendedFloat.NegativeInfinity.ToBigInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ExtendedRational.PositiveInfinity.ToBigInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ExtendedRational.NegativeInfinity.ToBigInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestExtendedMiscellaneous() {
      Assert.assertEquals(ExtendedDecimal.Zero, ExtendedDecimal.FromInt32(0));
      Assert.assertEquals(ExtendedDecimal.One, ExtendedDecimal.FromInt32(1));

      Assert.assertEquals(
        ExtendedFloat.Zero,
        ExtendedDecimal.Zero.ToExtendedFloat());
      Assert.assertEquals(
        ExtendedFloat.NegativeZero,
        ExtendedDecimal.NegativeZero.ToExtendedFloat());
      if (0.0 != ExtendedDecimal.Zero.ToSingle()) {
        Assert.fail("Failed " + ExtendedDecimal.Zero.ToSingle());
      }
      if (0.0 != ExtendedDecimal.Zero.ToDouble()) {
        Assert.fail("Failed " + ExtendedDecimal.Zero.ToDouble());
      }
      if (0.0f != ExtendedFloat.Zero.ToSingle()) {
        Assert.fail("Failed " + ExtendedFloat.Zero.ToDouble());
      }
      if (0.0f != ExtendedFloat.Zero.ToDouble()) {
        Assert.fail("Failed " + ExtendedFloat.Zero.ToDouble());
      }
      Assert.assertEquals(
        CBORObject.FromObject(0.1),
        CBORObject.FromObjectAndTag(0.1, 999999).Untag());
      Assert.assertEquals(-1, CBORObject.NewArray().getSimpleValue());
      Assert.assertEquals(
        CBORObject.FromObject(2),
        CBORObject.FromObject(-2).Abs());
      Assert.assertEquals(CBORObject.FromObject(2), CBORObject.FromObject(2).Abs());
    }

    @Test
    public void TestFloat() {
      TestCommon.AssertSer(
        CBORObject.FromObject(Float.POSITIVE_INFINITY),
        "Infinity");
      TestCommon.AssertSer(
        CBORObject.FromObject(Float.NEGATIVE_INFINITY),
        "-Infinity");
      TestCommon.AssertSer(
        CBORObject.FromObject(Float.NaN),
        "NaN");
      for (int i = -65539; i <= 65539; ++i) {
        TestCommon.AssertSer(
          CBORObject.FromObject((float)i),
          TestCommon.IntToString(i));
      }
    }
    @Test
    public void TestHalfPrecision() {
      CBORObject o = CBORObject.DecodeFromBytes(
        new byte[] { (byte)0xf9, 0x7c, 0x00  });
      Assert.assertEquals(Float.POSITIVE_INFINITY, o.AsSingle(), 0f);
      o = CBORObject.DecodeFromBytes(
        new byte[] { (byte)0xf9, 0x00, 0x00  });
      Assert.assertEquals((float)0, o.AsSingle(), 0f);
      o = CBORObject.DecodeFromBytes(
        new byte[] { (byte)0xf9, (byte)0xfc, 0x00  });
      Assert.assertEquals(Float.NEGATIVE_INFINITY, o.AsSingle(), 0f);
      o = CBORObject.DecodeFromBytes(
        new byte[] { (byte)0xf9, 0x7e, 0x00  });
      if (!(Float.isNaN(o.AsSingle())))Assert.fail();
    }

    @Test
    public void TestJSON() {
      CBORObject o;
      o = CBORObject.FromJSONString("[1,2,null,true,false,\"\"]");
      Assert.assertEquals(6, o.size());
      Assert.assertEquals(1, o.get(0).AsInt32());
      Assert.assertEquals(2, o.get(1).AsInt32());
      Assert.assertEquals(CBORObject.Null, o.get(2));
      Assert.assertEquals(CBORObject.True, o.get(3));
      Assert.assertEquals(CBORObject.False, o.get(4));
      Assert.assertEquals("", o.get(5).AsString());
      o = CBORObject.FromJSONString("[1.5,2.6,3.7,4.0,222.22]");
      double actual = o.get(0).AsDouble();
      Assert.assertEquals((double)1.5, actual, 0);
      {
java.io.ByteArrayInputStream ms2a = null;
try {
ms2a = new java.io.ByteArrayInputStream(new byte[] { });

        try {
          CBORObject.ReadJSON(ms2a);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms2a != null)ms2a.close(); } catch (java.io.IOException ex) {}
}
}
      {
java.io.ByteArrayInputStream ms2b = null;
try {
ms2b = new java.io.ByteArrayInputStream(new byte[] { 0x20  });

        try {
          CBORObject.ReadJSON(ms2b);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms2b != null)ms2b.close(); } catch (java.io.IOException ex) {}
}
}
      try {
        CBORObject.FromJSONString("");
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString("[.1]");
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString("[-.1]");
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString("\u0020");
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      {
        String stringTemp = CBORObject.FromJSONString("true").ToJSONString();
        Assert.assertEquals(
        "true",
        stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString(" true ").ToJSONString();
        Assert.assertEquals(
        "true",
        stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString("false").ToJSONString();
        Assert.assertEquals(
        "false",
        stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString("null").ToJSONString();
        Assert.assertEquals(
        "null",
        stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString("5").ToJSONString();
        Assert.assertEquals(
        "5",
        stringTemp);
      }
    }

    @Test
    public void TestJSONEscapedChars() {
      CBORObject o = CBORObject.FromJSONString(
        "[\"\\r\\n\\u0006\\u000E\\u001A\\\\\\\"\"]");
      Assert.assertEquals(1, o.size());
      {
        String stringTemp = o.get(0).AsString();
        Assert.assertEquals(
        "\r\n\u0006\u000E\u001A\\\"",
        stringTemp);
      }
      {
        String stringTemp = o.ToJSONString();
        Assert.assertEquals(
        "[\"\\r\\n\\u0006\\u000E\\u001A\\\\\\\"\"]",
        stringTemp);
      }
      TestCommon.AssertRoundTrip(o);
    }

    @Test
    public void TestLong() {
      long[] ranges = {
        -65539, 65539, 0xFFFFF000L, 0x100000400L,
        Long.MAX_VALUE - 1000, Long.MAX_VALUE, Long.MIN_VALUE, Long.MIN_VALUE +
          1000 };
      for (int i = 0; i < ranges.length; i += 2) {
        long j = ranges[i];
        while (true) {
          if (!(CBORObject.FromObject(j).isIntegral()))Assert.fail();
          if (!(CBORObject.FromObject(j).CanFitInInt64()))Assert.fail();
          if (!(CBORObject.FromObject(j).CanTruncatedIntFitInInt64()))Assert.fail();
          TestCommon.AssertSer(
            CBORObject.FromObject(j),
            String.format(java.util.Locale.US,"%s", j));
          Assert.assertEquals(
            CBORObject.FromObject(j),
            CBORObject.FromObject(BigInteger.valueOf(j)));
          CBORObject obj = CBORObject.FromJSONString(
            String.format(java.util.Locale.US,"[%s]", j));
          TestCommon.AssertSer(
            obj,
            String.format(java.util.Locale.US,"[%s]", j));
          if (j == ranges[i + 1]) {
            break;
          }
          ++j;
        }
      }
    }

    @Test
    public void TestMap() {
      CBORObject cbor = CBORObject.FromJSONString("{\"a\":2,\"b\":4}");
      Assert.assertEquals(2, cbor.size());
      TestCommon.AssertEqualsHashCode(
        CBORObject.FromObject(2),
        cbor.get(CBORObject.FromObject("a")));
      TestCommon.AssertEqualsHashCode(
        CBORObject.FromObject(4),
        cbor.get(CBORObject.FromObject("b")));
      Assert.assertEquals(2, cbor.get(CBORObject.FromObject("a")).AsInt32());
      Assert.assertEquals(4, cbor.get(CBORObject.FromObject("b")).AsInt32());
      Assert.assertEquals(0, CBORObject.True.size());
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xbf, 0x61, 0x61, 2,
                    0x61, 0x62, 4, (byte)0xff  });
      Assert.assertEquals(2, cbor.size());
      TestCommon.AssertEqualsHashCode(
        CBORObject.FromObject(2),
        cbor.get(CBORObject.FromObject("a")));
      TestCommon.AssertEqualsHashCode(
        CBORObject.FromObject(4),
        cbor.get(CBORObject.FromObject("b")));
      Assert.assertEquals(2, cbor.get(CBORObject.FromObject("a")).AsInt32());
      Assert.assertEquals(4, cbor.get(CBORObject.FromObject("b")).AsInt32());
    }

    @Test
    public void TestMapInMap() {
      CBORObject oo;
      oo = CBORObject.NewArray().Add(CBORObject.NewMap()
                    .Add(
                    new ExtendedRational(BigInteger.valueOf(1), BigInteger.valueOf(2)),
                    3).Add(4, false)).Add(true);
      TestCommon.AssertRoundTrip(oo);
      oo = CBORObject.NewArray();
      oo.Add(CBORObject.FromObject(0));
      CBORObject oo2 = CBORObject.NewMap();
      oo2.Add(CBORObject.FromObject(1), CBORObject.FromObject(1368));
      CBORObject oo3 = CBORObject.NewMap();
      oo3.Add(CBORObject.FromObject(2), CBORObject.FromObject(1625));
      CBORObject oo4 = CBORObject.NewMap();
      oo4.Add(oo2, CBORObject.True);
      oo4.Add(oo3, CBORObject.True);
      oo.Add(oo4);
      TestCommon.AssertRoundTrip(oo);
    }

    @Test
    public void TestParseDecimalStrings() {
      FastRandom rand = new FastRandom();
      for (int i = 0; i < 3000; ++i) {
        String r = RandomObjects.RandomDecimalString(rand);
        TestDecimalString(r);
      }
    }

    @Test(timeout = 50000)
    public void TestRandomData() {
      FastRandom rand = new FastRandom();
      CBORObject obj;
      for (int i = 0; i < 1000; ++i) {
        obj = RandomObjects.RandomCBORObject(rand);
        TestCommon.AssertRoundTrip(obj);
        TestWriteToJSON(obj);
      }
    }

    @Test(timeout = 50000)
    public void TestRandomNonsense() {
      FastRandom rand = new FastRandom();
      for (int i = 0; i < 200; ++i) {
        byte[] array = new byte[rand.NextValue(1000000) + 1];
        for (int j = 0; j < array.length; ++j) {
          if (j + 3 <= array.length) {
            int r = rand.NextValue(0x1000000);
            array[j] = (byte)(r & 0xff);
            array[j + 1] = (byte)((r >> 8) & 0xff);
            array[j + 2] = (byte)((r >> 16) & 0xff);
            j += 2;
          } else {
            array[j] = (byte)rand.NextValue(256);
          }
        }
        {
java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(array);
int startingAvailable = ms.available();

          while ((startingAvailable-ms.available()) != startingAvailable) {
            try {
              CBORObject o = CBORObject.Read(ms);
              try {
                if (o == null) {
                  Assert.fail("Object read is null");
                } else {
                  CBORObject.DecodeFromBytes(o.EncodeToBytes());
                }
              } catch (Exception ex) {
                Assert.fail(ex.toString());
                throw new IllegalStateException("", ex);
              }
              String jsonString = "";
              try {
                if (o.getType() == CBORType.Array || o.getType() == CBORType.Map) {
                  jsonString = o.ToJSONString();
                  CBORObject.FromJSONString(jsonString);
                  TestWriteToJSON(o);
                }
              } catch (Exception ex) {
                Assert.fail(jsonString + "\n" + ex);
                throw new IllegalStateException("", ex);
              }
            } catch (CBORException ex) {
              System.out.print("");  // Expected exception
            }
          }
}
finally {
try { if (ms != null)ms.close(); } catch (java.io.IOException ex) {}
}
}
      }
    }

    @Test(timeout = 20000)
    public void TestRandomSlightlyModified() {
      FastRandom rand = new FastRandom();
      // Test slightly modified objects
      for (int i = 0; i < 200; ++i) {
        CBORObject originalObject = RandomObjects.RandomCBORObject(rand);
        byte[] array = originalObject.EncodeToBytes();
        // System.out.println(originalObject);
        int count2 = rand.NextValue(10) + 1;
        for (int j = 0; j < count2; ++j) {
          int index = rand.NextValue(array.length);
          array[index] = ((byte)rand.NextValue(256));
        }
        {
java.io.ByteArrayInputStream inputStream = null;
try {
inputStream = new java.io.ByteArrayInputStream(array);
int startingAvailable = inputStream.available();

          while ((startingAvailable-inputStream.available()) != startingAvailable) {
            try {
              CBORObject o = CBORObject.Read(inputStream);
              byte[] encodedBytes = (o == null) ? null : o.EncodeToBytes();
              try {
                CBORObject.DecodeFromBytes(encodedBytes);
              } catch (Exception ex) {
                Assert.fail(ex.toString());
                throw new IllegalStateException("", ex);
              }
              String jsonString = "";
              try {
                if (o == null) {
                  Assert.fail("Object is null");
                }
       if (o != null && (o.getType() == CBORType.Array || o.getType() ==
                  CBORType.Map)) {
                  jsonString = o.ToJSONString();
                  // reread JSON String to test validity
                  CBORObject.FromJSONString(jsonString);
                  TestWriteToJSON(o);
                }
              } catch (Exception ex) {
                Assert.fail(jsonString + "\n" + ex);
                throw new IllegalStateException("", ex);
              }
            } catch (CBORException ex) {
              // Expected exception
              System.out.println(ex.getMessage());
            }
          }
}
finally {
try { if (inputStream != null)inputStream.close(); } catch (java.io.IOException ex) {}
}
}
      }
    }

    @Test
    public void TestReadWriteInt() {
      FastRandom r = new FastRandom();
      try {
        for (int i = 0; i < 100000; ++i) {
          int val = ((int)RandomObjects.RandomInt64(r));
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              MiniCBOR.WriteInt32(val, ms);
              {
java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(ms.toByteArray());

                Assert.assertEquals(val, MiniCBOR.ReadInt32(ms2));
}
finally {
try { if (ms2 != null)ms2.close(); } catch (java.io.IOException ex) {}
}
}
}
finally {
try { if (ms != null)ms.close(); } catch (java.io.IOException ex) {}
}
          }
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.Write(val, ms);
              {
java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(ms.toByteArray());

                Assert.assertEquals(val, MiniCBOR.ReadInt32(ms2));
}
finally {
try { if (ms2 != null)ms2.close(); } catch (java.io.IOException ex) {}
}
}
}
finally {
try { if (ms != null)ms.close(); } catch (java.io.IOException ex) {}
}
          }
        }
      } catch (IOException ioex) {
        Assert.fail(ioex.getMessage());
      }
    }

    @Test
    public void TestShort() {
      for (int i = Short.MIN_VALUE; i <= Short.MAX_VALUE; ++i) {
        TestCommon.AssertSer(
          CBORObject.FromObject((short)i),
          TestCommon.IntToString(i));
      }
    }

    @Test
    public void TestSimpleValues() {
      TestCommon.AssertSer(
        CBORObject.FromObject(true),
        "true");
      TestCommon.AssertSer(
        CBORObject.FromObject(false),
        "false");
      TestCommon.AssertSer(
        CBORObject.FromObject((Object)null),
        "null");
    }

    @Test
    public void TestSubtract() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 = RandomObjects.RandomNumber(r);
        CBORObject o2 = RandomObjects.RandomNumber(r);
        ExtendedDecimal cmpDecFrac =
          o1.AsExtendedDecimal().Subtract(o2.AsExtendedDecimal());
        ExtendedDecimal cmpCobj = CBORObject.Subtract(
          o1,
          o2).AsExtendedDecimal();
        if (cmpDecFrac.compareTo(cmpCobj) != 0) {
          Assert.assertEquals(TestCommon.ObjectMessages(o1, o2, "Results don't match"),0,cmpDecFrac.compareTo(cmpCobj));
        }
        TestCommon.AssertRoundTrip(o1);
        TestCommon.AssertRoundTrip(o2);
      }
    }

    @Test
    public void TestTaggedUntagged() {
      for (int i = 200; i < 1000; ++i) {
        if (i == 264 || i == 265 || i + 1 == 264 || i + 1 == 265) {
          // Skip since they're being used as
          // arbitrary-precision numbers
          continue;
        }
        CBORObject o, o2;
        o = CBORObject.FromObject(0);
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObject(BigInteger.valueOf(1).shiftLeft(100));
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObject(new byte[] { 1, 2, 3  });
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.NewArray();
        o.Add(CBORObject.FromObject(0));
        o.Add(CBORObject.FromObject(1));
        o.Add(CBORObject.FromObject(2));
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.NewMap();
        o.Add("a", CBORObject.FromObject(0));
        o.Add("b", CBORObject.FromObject(1));
        o.Add("c", CBORObject.FromObject(2));
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObject("a");
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.False;
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.True;
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.Null;
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.Undefined;
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
      }
    }

    @Test
    public void TestTags() {
      BigInteger maxuint = (BigInteger.valueOf(1).shiftLeft(64)).subtract(BigInteger.valueOf(1));
      BigInteger[] ranges = {
        BigInteger.valueOf(37),
        BigInteger.valueOf(65539), BigInteger.valueOf(Integer.MAX_VALUE).subtract(BigInteger.valueOf(500)),
        BigInteger.valueOf(Integer.MAX_VALUE).add(BigInteger.valueOf(500)),
        BigInteger.valueOf(Long.MAX_VALUE).subtract(BigInteger.valueOf(500)),
        BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.valueOf(500)),
        ((BigInteger.valueOf(1).shiftLeft(64)).subtract(BigInteger.valueOf(1))).subtract(BigInteger.valueOf(500)),
        maxuint };
      if (CBORObject.True.isTagged())Assert.fail();
      Assert.assertEquals(
        BigInteger.valueOf(0).subtract(BigInteger.valueOf(1)),
        CBORObject.True.getInnermostTag());
      BigInteger[] tagstmp = CBORObject.True.GetTags();
      Assert.assertEquals(0, tagstmp.length);
      for (int i = 0; i < ranges.length; i += 2) {
        BigInteger bigintTemp = ranges[i];
        while (true) {
          if (bigintTemp.compareTo(BigInteger.valueOf(-1)) >= 0 &&
              bigintTemp.compareTo(BigInteger.valueOf(37)) <= 0) {
            bigintTemp = bigintTemp.add(BigInteger.valueOf(1));
            continue;
          }
          if (bigintTemp.compareTo(BigInteger.valueOf(264)) == 0 ||
              bigintTemp.compareTo(BigInteger.valueOf(265)) == 0) {
            bigintTemp = bigintTemp.add(BigInteger.valueOf(1));
            continue;
          }
          CBORObject obj = CBORObject.FromObjectAndTag(0, bigintTemp);
          if (!(obj.isTagged()))Assert.fail("obj not tagged");
          BigInteger[] tags = obj.GetTags();
          Assert.assertEquals(1, tags.length);
          Assert.assertEquals(bigintTemp, tags[0]);
          if (!obj.getInnermostTag().equals(bigintTemp)) {
            String errmsg = String.format(java.util.Locale.US,"obj tag doesn't match: %s",
                obj);
            Assert.assertEquals(errmsg, bigintTemp, obj.getInnermostTag());
          }
          TestCommon.AssertSer(
            obj,
            String.format(java.util.Locale.US,"%s(0)", bigintTemp));
          if (!bigintTemp.equals(maxuint)) {
            BigInteger bigintNew = bigintTemp.add(BigInteger.valueOf(1));
            if (bigintNew.equals(BigInteger.valueOf(264)) ||
                bigintNew.equals(BigInteger.valueOf(265))) {
              bigintTemp = bigintTemp.add(BigInteger.valueOf(1));
              continue;
            }
            // Test multiple tags
            CBORObject obj2 = CBORObject.FromObjectAndTag(obj, bigintNew);
            BigInteger[] bi = obj2.GetTags();
            if (bi.length != 2) {
              {
                String stringTemp = String.format(java.util.Locale.US,"Expected 2 tags: %s",
                    obj2);
                Assert.assertEquals(stringTemp, 2, bi.length);
              }
            }
            bigintNew = bigintTemp.add(BigInteger.valueOf(1));
            if (!bi[0].equals(bigintNew)) {
              {
                String stringTemp = String.format(java.util.Locale.US,"Outer tag doesn't match: %s",
                    obj2);
                Assert.assertEquals(stringTemp, bigintTemp.add(BigInteger.valueOf(1)), bi[0]);
              }
            }
            if (!bi[1].equals(bigintTemp)) {
              {
                String stringTemp = String.format(java.util.Locale.US,"Inner tag doesn't match: %s",
                    obj2);
                Assert.assertEquals(stringTemp, bigintTemp, bi[1]);
              }
            }
            if (!obj2.getInnermostTag().equals(bigintTemp)) {
              {
                String stringTemp = String.format(java.util.Locale.US,"Innermost tag doesn't match: %s",
                    obj2);
                Assert.assertEquals(stringTemp, bigintTemp, obj2.getInnermostTag());
              }
            }
            String str = String.format(java.util.Locale.US,"%s(%s(0))",
              bigintTemp.add(BigInteger.valueOf(1)),
              bigintTemp);
            TestCommon.AssertSer(
              obj2,
              str);
          }
          if (bigintTemp.equals(ranges[i + 1])) {
            break;
          }
          bigintTemp = bigintTemp.add(BigInteger.valueOf(1));
        }
      }
    }

    @Test
    public void TestTags264And265() {
      CBORObject cbor;
      // Tag 264
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xd9, 0x01, 0x08, (byte)0x82,
        (byte)0xc2, 0x42, 2, 2, (byte)0xc2, 0x42, 2, 2  });
      TestCommon.AssertRoundTrip(cbor);
      // Tag 265
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xd9, 0x01, 0x09, (byte)0x82,
        (byte)0xc2, 0x42, 2, 2, (byte)0xc2, 0x42, 2, 2  });
      TestCommon.AssertRoundTrip(cbor);
    }
    @Test(expected = CBORException.class)
    public void TestTagThenBreak() {
      TestCommon.FromBytesTestAB(new byte[] { (byte)0xd1, (byte)0xff  });
    }

    @Test
    public void TestTextStringStream() {
      CBORObject cbor = TestCommon.FromBytesTestAB(
        new byte[] { 0x7f, 0x61, 0x2e, 0x61, 0x2e, (byte)0xff  });
      {
String stringTemp = cbor.AsString();
Assert.assertEquals(
"..",
stringTemp);
}
      TestTextStringStreamOne(TestCommon.Repeat('x', 200000));
      TestTextStringStreamOne(TestCommon.Repeat('\u00e0', 200000));
      TestTextStringStreamOne(TestCommon.Repeat('\u3000', 200000));
      TestTextStringStreamOne(TestCommon.Repeat("\ud800\udc00", 200000));
    }
    @Test(expected = CBORException.class)
    public void TestTextStringStreamNoIndefiniteWithinDefinite() {
      TestCommon.FromBytesTestAB(new byte[] { 0x7f, 0x61, 0x20, 0x7f, 0x61,
        0x20, (byte)0xff, (byte)0xff  });
    }
    @Test(expected = CBORException.class)
    public void TestTextStringStreamNoTagsBeforeDefinite() {
      TestCommon.FromBytesTestAB(new byte[] { 0x7f, 0x61, 0x20, (byte)0xc0, 0x61,
        0x20, (byte)0xff  });
    }

    private static void AddSubCompare(CBORObject o1, CBORObject o2) {
      ExtendedDecimal cmpDecFrac =
        o1.AsExtendedDecimal().Add(o2.AsExtendedDecimal());
      ExtendedDecimal cmpCobj = CBORObject.Addition(o1, o2).AsExtendedDecimal();
      TestCommon.CompareTestEqual(cmpDecFrac, cmpCobj);
      cmpDecFrac = o1.AsExtendedDecimal().Subtract(o2.AsExtendedDecimal());
      cmpCobj = CBORObject.Subtract(o1, o2).AsExtendedDecimal();
      TestCommon.CompareTestEqual(cmpDecFrac, cmpCobj);
      CBORObjectTest.CompareDecimals(o1, o2);
    }

    private static void TestDecimalString(String r) {
      CBORObject o = CBORObject.FromObject(ExtendedDecimal.FromString(r));
      CBORObject o2 = CBORDataUtilities.ParseJSONNumber(r);
      TestCommon.CompareTestEqual(o, o2);
    }

    private static void TestExtendedFloatDoubleCore(double d, String s) {
      double oldd = d;
      ExtendedFloat bf = ExtendedFloat.FromDouble(d);
      if (s != null) {
        Assert.assertEquals(s, bf.toString());
      }
      d = bf.ToDouble();
      Assert.assertEquals((double)oldd, d, 0);
      if (!(CBORObject.FromObject(bf).CanFitInDouble()))Assert.fail();
      TestCommon.AssertRoundTrip(CBORObject.FromObject(bf));
      TestCommon.AssertRoundTrip(CBORObject.FromObject(d));
    }

    private static void TestExtendedFloatSingleCore(float d, String s) {
      float oldd = d;
      ExtendedFloat bf = ExtendedFloat.FromSingle(d);
      if (s != null) {
        Assert.assertEquals(s, bf.toString());
      }
      d = bf.ToSingle();
      Assert.assertEquals((float)oldd, d, 0f);
      if (!(CBORObject.FromObject(bf).CanFitInSingle()))Assert.fail();
      TestCommon.AssertRoundTrip(CBORObject.FromObject(bf));
      TestCommon.AssertRoundTrip(CBORObject.FromObject(d));
    }

    private static void TestTextStringStreamOne(String longString) {
      CBORObject cbor, cbor2;
      cbor = CBORObject.FromObject(longString);
      cbor2 = TestCommon.FromBytesTestAB(cbor.EncodeToBytes());
      Assert.assertEquals(
        longString,
        CBORObject.DecodeFromBytes(cbor.EncodeToBytes()).AsString());
      {
Object objectTemp = longString;
Object objectTemp2 = CBORObject.DecodeFromBytes(cbor.EncodeToBytes(
          CBOREncodeOptions.NoIndefLengthStrings)).AsString();
Assert.assertEquals(objectTemp, objectTemp2);
}
      TestCommon.AssertEqualsHashCode(cbor, cbor2);
      Assert.assertEquals(longString, cbor2.AsString());
    }

    private static void TestWriteToJSON(CBORObject obj) {
      CBORObject objA = null;
      java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

        try {
          obj.WriteJSONTo(ms);
          objA = CBORObject.FromJSONString(DataUtilities.GetUtf8String(
            ms.toByteArray(),
            true));
        } catch (IOException ex) {
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms != null)ms.close(); } catch (java.io.IOException ex) {}
}
      CBORObject objB = CBORObject.FromJSONString(obj.ToJSONString());
      if (!objA.equals(objB)) {
        System.out.println(objA);
        System.out.println(objB);
        Assert.fail("WriteJSONTo gives different results from ToJSONString");
      }
    }
  }
