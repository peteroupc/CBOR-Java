package com.upokecenter.test;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import java.util.*;

import org.junit.Assert;

import com.upokecenter.util.*;
import com.upokecenter.cbor.*;

  public final class TestCommon {
private TestCommon() {
}
    private static final String ValueDigits = "0123456789";

    public static void AssertByteArraysEqual(byte[] arr1, byte[] arr2) {
      if (!ByteArraysEqual(arr1, arr2)) {
        Assert.fail("Expected " + ToByteArrayString(arr1) + ", got " +
          ToByteArrayString(arr2));
      }
    }

    public static void AssertEqualsHashCode(Object o, Object o2) {
      if (o.equals(o2)) {
        if (!o2.equals(o)) {
          Assert.fail(
"" + o + " equals " + o2 + " but not vice versa");
        }
        // Test for the guarantee that equal objects
        // must have equal hash codes
        if (o2.hashCode() != o.hashCode()) {
          // Don't use Assert.assertEquals directly because it has
          // quite a lot of overhead
          Assert.fail(
"" + o + " and " + o2 + " don't have equal hash codes");
        }
      } else {
        if (o2.equals(o)) {
          Assert.fail("" + o + " does not equal " + o2 +
            " but not vice versa");
        }
        // At least check that hashCode doesn't throw
        try {
          o.hashCode();
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        try {
          o2.hashCode();
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
    }

    public static void AssertRoundTrip(CBORObject o) {
      CBORObject o2 = FromBytesTestAB(o.EncodeToBytes());
      CompareTestEqual(o, o2);
      TestNumber(o);
      AssertEqualsHashCode(o, o2);
    }

    public static void AssertSer(CBORObject o, String s) {
      if (!s.equals(o.toString())) {
        Assert.assertEquals("o is not equal to s",s,o.toString());
      }
      // Test round-tripping
      CBORObject o2 = FromBytesTestAB(o.EncodeToBytes());
      if (!s.equals(o2.toString())) {
        Assert.assertEquals("o2 is not equal to s",s,o2.toString());
      }
      TestNumber(o);
      AssertEqualsHashCode(o, o2);
    }

    public static <T extends Comparable<T>> void CompareTestConsistency(T o1, T o2, T o3) {
      if (o1 == null) {
        throw new NullPointerException("o1");
      }
      if (o2 == null) {
        throw new NullPointerException("o2");
      }
      if (o3 == null) {
        throw new NullPointerException("o3");
      }
      int cmp = CompareTestReciprocal(o1, o2);
      int cmp2 = CompareTestReciprocal(o2, o3);
      int cmp3 = CompareTestReciprocal(o1, o3);
      Assert.assertEquals(cmp == 0, o1.equals(o2));
      Assert.assertEquals(cmp == 0, o2.equals(o1));
      Assert.assertEquals(cmp2 == 0, o2.equals(o3));
      Assert.assertEquals(cmp2 == 0, o3.equals(o2));
      Assert.assertEquals(cmp3 == 0, o1.equals(o3));
      Assert.assertEquals(cmp3 == 0, o3.equals(o1));
    }

    public static <T extends Comparable<T>> void CompareTestEqual(T o1, T o2) {
      if (CompareTestReciprocal(o1, o2) != 0) {
        Assert.fail(ObjectMessages(
          o1,
          o2,
          "Not equal: " + CompareTestReciprocal(o1, o2)));
      }
    }

    public static <T extends Comparable<T>> void CompareTestEqualAndConsistent(T o1, T o2) {
      CompareTestEqualAndConsistent(o1, o2, null);
    }

    public static <T extends Comparable<T>> void CompareTestEqualAndConsistent(
T o1,
T o2,
String msg) {
      if (CompareTestReciprocal(o1, o2) != 0) {
        msg = (msg == null ? "" : (msg + "\r\n")) +
          "Not equal: " + CompareTestReciprocal(o1, o2);
        Assert.fail(ObjectMessages(
          o1,
          o2,
          msg));
      }
      if (!o1.equals(o2)) {
        msg = (msg == null ? "" : (msg + "\r\n")) +
          "Not equal: " + CompareTestReciprocal(o1, o2);
        Assert.fail(ObjectMessages(
          o1,
          o2,
          msg));
      }
    }

    public static <T extends Comparable<T>> void CompareTestGreater(T o1, T o2) {
      CompareTestLess(o2, o1);
    }

    public static <T extends Comparable<T>> void CompareTestLess(T o1, T o2) {
      if (CompareTestReciprocal(o1, o2) >= 0) {
        Assert.fail(ObjectMessages(
          o1,
          o2,
          "Not less: " + CompareTestReciprocal(o1, o2)));
      }
    }

    public static <T extends Comparable<T>> int CompareTestReciprocal(T o1, T o2) {
      if (o1 == null) {
        throw new NullPointerException("o1");
      }
      if (o2 == null) {
        throw new NullPointerException("o2");
      }
      int cmp = ((o1.compareTo(o2) == 0) ? 0 : ((o1.compareTo(o2)< 0) ? -1 : 1));
      int cmp2 = ((o2.compareTo(o1) == 0) ? 0 : ((o2.compareTo(o1)< 0) ? -1 : 1));
      if (-cmp2 != cmp) {
        Assert.assertEquals(ObjectMessages(o1, o2, "Not reciprocal"),cmp,-cmp2);
      }
      return cmp;
    }

    public static <T extends Comparable<T>> void CompareTestRelations(T o1, T o2, T o3) {
      if (o1 == null) {
        throw new NullPointerException("o1");
      }
      if (o2 == null) {
        throw new NullPointerException("o2");
      }
      if (o3 == null) {
        throw new NullPointerException("o3");
      }
      if (o1.compareTo(o1) != 0) {
        Assert.fail(o1.toString());
      }
      if (o2.compareTo(o2) != 0) {
        Assert.fail(o2.toString());
      }
      if (o3.compareTo(o3) != 0) {
        Assert.fail(o3.toString());
      }
      int cmp12 = CompareTestReciprocal(o1, o2);
      int cmp23 = CompareTestReciprocal(o2, o3);
      int cmp13 = CompareTestReciprocal(o1, o3);
      // CompareTestReciprocal tests compareTo both
      // ways, so shortcutting by negating the values
      // is allowed here
      int cmp21 = -cmp12;
      int cmp32 = -cmp23;
      int cmp31 = -cmp13;
      // Transitivity checks
      for (int i = -1; i <= 1; ++i) {
        if (cmp12 == i) {
          if (cmp23 == i && cmp13 != i) {
            Assert.fail(ObjectMessages(o1, o2, o3, "Not transitive"));
          }
        }
        if (cmp23 == i) {
          if (cmp31 == i && cmp21 != i) {
            Assert.fail(ObjectMessages(o1, o2, o3, "Not transitive"));
          }
        }
        if (cmp31 == i) {
          if (cmp12 == i && cmp32 != i) {
            Assert.fail(ObjectMessages(o1, o2, o3, "Not transitive"));
          }
        }
      }
    }
    // Tests the equivalence of the FromBytes and Read methods.
    public static CBORObject FromBytesTestAB(byte[] b) {
      CBORObject oa = FromBytesA(b);
      CBORObject ob = FromBytesB(b);
      if (!oa.equals(ob)) {
        Assert.assertEquals(oa, ob);
      }
      return oa;
    }

    public static String IntToString(int value) {
      if (value == Integer.MIN_VALUE) {
        return "-2147483648";
      }
      if (value == 0) {
        return "0";
      }
      boolean neg = value < 0;
      char[] chars = new char[24];
      int count = 0;
      if (neg) {
        chars[0] = '-';
        ++count;
        value = -value;
      }
      while (value != 0) {
        int intdivvalue = value / 10;
        char digit = ValueDigits.charAt((int)(value - (intdivvalue * 10)));
        chars[count++] = digit;
        value = intdivvalue;
      }
      if (neg) {
        ReverseChars(chars, 1, count - 1);
      } else {
        ReverseChars(chars, 0, count);
      }
      return new String(chars, 0, count);
    }

    public static String LongToString(long longValue) {
      if (longValue == Long.MIN_VALUE) {
        return "-9223372036854775808";
      }
      if (longValue == 0L) {
        return "0";
      }
      if (longValue == (long)Integer.MIN_VALUE) {
        return "-2147483648";
      }
      boolean neg = longValue < 0;
      int count = 0;
      char[] chars;
      int intlongValue = ((int)longValue);
      if ((long)intlongValue == longValue) {
        chars = new char[12];
        if (neg) {
          chars[0] = '-';
          ++count;
          intlongValue = -intlongValue;
        }
        while (intlongValue != 0) {
          int intdivlongValue = intlongValue / 10;
        char digit = ValueDigits.charAt((int)(intlongValue - (intdivlongValue *
            10)));
          chars[count++] = digit;
          intlongValue = intdivlongValue;
        }
      } else {
        chars = new char[24];
        if (neg) {
          chars[0] = '-';
          ++count;
          longValue = -longValue;
        }
        while (longValue != 0) {
          long divlongValue = longValue / 10;
          char digit = ValueDigits.charAt((int)(longValue - (divlongValue * 10)));
          chars[count++] = digit;
          longValue = divlongValue;
        }
      }
      if (neg) {
        ReverseChars(chars, 1, count - 1);
      } else {
        ReverseChars(chars, 0, count);
      }
      return new String(chars, 0, count);
    }

    public static String ObjectMessages(
      Object o1,
      Object o2,
      String s) {
      CBORObject co1 = ((o1 instanceof CBORObject) ? (CBORObject)o1 : null);
      CBORObject co2 = ((o2 instanceof CBORObject) ? (CBORObject)o2 : null);
      return (co1 != null) ? TestCommon.ObjectMessages(co1, co2, s) : (s +
        ":\n" + o1 + " and\n" + o2);
    }

    public static String ObjectMessages(
      CBORObject o1,
      CBORObject o2,
      String s) {
      if (o1.getType() == CBORType.Number && o2.getType() == CBORType.Number) {
        return s + ":\n" + o1 + " and\n" + o2 + "\nOR\n" +
          o1.AsExtendedDecimal() + " and\n" + o2.AsExtendedDecimal() +
       "\nOR\n" + "AddSubCompare(" + TestCommon.ToByteArrayString(o1) + ",\n" +
          TestCommon.ToByteArrayString(o2) + ");";
      }
      return s + ":\n" + o1 + " and\n" + o2 + "\nOR\n" +
TestCommon.ToByteArrayString(o1) + " and\n" + TestCommon.ToByteArrayString(o2);
    }

    public static String ObjectMessages(
      Object o1,
      Object o2,
      Object o3,
      String s) {
      CBORObject co1 = ((o1 instanceof CBORObject) ? (CBORObject)o1 : null);
      CBORObject co2 = ((o2 instanceof CBORObject) ? (CBORObject)o2 : null);
      CBORObject co3 = ((o3 instanceof CBORObject) ? (CBORObject)o3 : null);
      return (co1 != null) ? TestCommon.ObjectMessages(co1, co2, co3, s) :
        (s + ":\n" + o1 + " and\n" + o2 + " and\n" + o3);
    }

    public static String ObjectMessages(
      CBORObject o1,
      CBORObject o2,
      CBORObject o3,
      String s) {
      return s + ":\n" + o1 + " and\n" + o2 + " and\n" + o3 + "\nOR\n" +
TestCommon.ToByteArrayString(o1) + " and\n" + TestCommon.ToByteArrayString(o2) +
 " and\n" + TestCommon.ToByteArrayString(o3);
    }

    public static String Repeat(char c, int num) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < num; ++i) {
        sb.append(c);
      }
      return sb.toString();
    }

    public static String Repeat(String c, int num) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < num; ++i) {
        sb.append(c);
      }
      return sb.toString();
    }

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
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex); throw new
            IllegalStateException("", ex);
        }
        try {
          o.AsInt16();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex); throw new
            IllegalStateException("", ex);
        }
        try {
          o.AsInt32();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex); throw new
            IllegalStateException("", ex);
        }
        try {
          o.AsInt64();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex); throw new
            IllegalStateException("", ex);
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
          o.AsBigInteger();
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail("Object: " + o + ", " + ex); throw new
            IllegalStateException("", ex);
        }
        return;
      }
      try {
        o.AsSingle();
      } catch (Exception ex) {
        Assert.fail("Object: " + o + ", " + ex); throw new
          IllegalStateException("", ex);
      }
      try {
        o.AsDouble();
      } catch (Exception ex) {
        Assert.fail("Object: " + o + ", " + ex); throw new
          IllegalStateException("", ex);
      }
    }

    public static String ToByteArrayString(byte[] bytes) {
      if (bytes == null) {
        return "null";
      }
      StringBuilder sb = new StringBuilder();
      String ValueHex = "0123456789ABCDEF";
      sb.append("new byte[] { ");
      for (int i = 0; i < bytes.length; ++i) {
        if (i > 0) {
          sb.append(",");  }
        if ((bytes[i] & 0x80) != 0) {
          sb.append("(byte)0x");
        } else {
          sb.append("0x");
        }
        sb.append(ValueHex.charAt((bytes[i] >> 4) & 0xf));
        sb.append(ValueHex.charAt(bytes[i] & 0xf));
      }
      sb.append("}");
      return sb.toString();
    }

    public static String ToByteArrayString(CBORObject obj) {
      return new StringBuilder()
        .append("CBORObject.DecodeFromBytes(")
           .append(ToByteArrayString(obj.EncodeToBytes()))
           .append(")").toString();
    }

    private static boolean ByteArraysEqual(byte[] arr1, byte[] arr2) {
      if (arr1 == null) {
        return arr2 == null;
      }
      if (arr2 == null) {
        return false;
      }
      if (arr1.length != arr2.length) {
        return false;
      }
      for (int i = 0; i < arr1.length; ++i) {
        if (arr1[i] != arr2[i]) {
          return false;
        }
      }
      return true;
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
try { if (ms != null)ms.close(); } catch (java.io.IOException ex) {}
}
}
    }

    private static void ReverseChars(char[] chars, int offset, int length) {
      int half = length >> 1;
      int right = offset + length - 1;
      for (int i = 0; i < half; i++, right--) {
        char value = chars[offset + i];
        chars[offset + i] = chars[right];
        chars[right] = value;
      }
    }
  }
