package com.upokecenter.test;
/*
Written in 2013-2018 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import org.junit.Assert;

  public final class TestCommon {
private TestCommon() {
}
    private static final String ValueDigits = "0123456789";

    public static int StringToInt(String str) {
      boolean neg = false;
      int i = 0;
      if (str == null) {
        throw new NullPointerException("str");
      }
      if (str.length() > 0 && str.charAt(0) == '-') {
        neg = true;
        ++i;
      }
      if (i == str.length()) {
        throw new NumberFormatException();
      }
      int ret = 0;
      while (i < str.length()) {
        int c = str.charAt(i);
        ++i;
        if (c >= '0' && c <= '9') {
          int x = c - '0';
          if (ret > 214748364) {
            throw new NumberFormatException();
          }
          ret *= 10;
          if (ret == 2147483640) {
            if (neg && x == 8) {
              if (i != str.length()) {
                throw new NumberFormatException();
              }
              return Integer.MIN_VALUE;
            }
            if (x > 7) {
              throw new NumberFormatException();
            }
          }
          ret += x;
        } else {
          throw new NumberFormatException();
        }
      }
      return neg ? -ret : ret;
    }

    public static long StringToLong(String str) {
      boolean neg = false;
      int i = 0;
      if (str == null) {
        throw new NullPointerException("str");
      }
      if (str.length() > 0 && str.charAt(0) == '-') {
        neg = true;
        ++i;
      }
      if (i == str.length()) {
        throw new NumberFormatException();
      }
      long ret = 0;
      while (i < str.length()) {
        int c = str.charAt(i);
        ++i;
        if (c >= '0' && c <= '9') {
          int x = c - '0';
          if ((long)ret > 922337203685477580L) {
            throw new NumberFormatException();
          }
          ret *= 10;
          if ((long)ret == 9223372036854775800L) {
            if (neg && x == 8) {
              if (i != str.length()) {
                throw new NumberFormatException();
              }
              return Long.MIN_VALUE;
            }
            if (x > 7) {
              throw new NumberFormatException();
            }
          }
          ret += x;
        } else {
          throw new NumberFormatException();
        }
      }
      return neg ? -ret : ret;
    }

    public static void AssertByteArraysEqual(byte[] arr1, byte[] arr2) {
      if (!ByteArraysEqual(arr1, arr2)) {
        Assert.fail("Expected " + ToByteArrayString(arr1) + ",\ngot..... " +
          ToByteArrayString(arr2));
      }
    }

    public static void AssertNotEqual(Object o, Object o2, String msg) {
      if (o == null) {
        throw new NullPointerException("o");
      }
      if (o.equals(o2)) {
        String str = msg + "\r\n" + ObjectMessages(
          o,
          o2,
          "Unexpectedly equal");
        Assert.fail(str);
      }
    }

    public static void AssertEquals(Object o, Object o2, String msg) {
      if (o == null) {
        throw new NullPointerException("o");
      }
      if (!o.equals(o2)) {
        Assert.assertEquals(msg, o, o2);
      }
    }

    public static void AssertNotEqual(Object o, Object o2) {
      if (o == null) {
        throw new NullPointerException("o");
      }
      if (o.equals(o2)) {
        String str = ObjectMessages(
          o,
          o2,
          "Unexpectedly equal");
        Assert.fail(str);
      }
    }

    public static void AssertEquals(Object o, Object o2) {
      if (o == null) {
        throw new NullPointerException("o");
      }
      if (!o.equals(o2)) {
        Assert.assertEquals(o, o2);
      }
    }

    public static void AssertEqualsHashCode(Object o, Object o2) {
      if (o == null) {
        throw new NullPointerException("o");
      }
      if (o.equals(o2)) {
        if (o2 == null) {
          throw new NullPointerException("o2");
        }
        if (!o2.equals(o)) {
          Assert.fail (
            "" + o + " equals " + o2 + " but not vice versa");
        }
        // Test for the guarantee that equal objects
        // must have equal hash codes
        if (o2.hashCode() != o.hashCode()) {
          // Don't use Assert.assertEquals directly because it has
          // quite a lot of overhead
          Assert.fail (
            "" + o + " and " + o2 + " don't have equal hash codes");
        }
      } else {
        if (o2 == null) {
          throw new NullPointerException("o2");
        }
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

    public static <T extends Comparable<T>> void CompareTestNotEqual(T o1, T o2) {
      if (CompareTestReciprocal(o1, o2) == 0) {
        Assert.fail(ObjectMessages(
            o1,
            o2,
            "Unexpectedly equal: " + CompareTestReciprocal(o1, o2)));
      }
    }

    public static <T extends Comparable<T>> void CompareTestNotEqual(T o1, T o2, String msg) {
      if (CompareTestReciprocal(o1, o2) == 0) {
        String str = msg + "\r\n" + ObjectMessages (
          o1,
          o2,
          "Unexpectedly equal: " + CompareTestReciprocal(o1, o2));
        Assert.fail(str);
      }
    }

    public static <T extends Comparable<T>> void CompareTestEqual(T o1, T o2) {
      if (CompareTestReciprocal(o1, o2) != 0) {
        Assert.fail(ObjectMessages(
            o1,
            o2,
            "Not equal: " + CompareTestReciprocal(o1, o2)));
      }
    }

    public static <T extends Comparable<T>> void CompareTestEqual(T o1, T o2, String msg) {
      if (CompareTestReciprocal(o1, o2) != 0) {
        String str = msg + "\r\n" + ObjectMessages (
          o1,
          o2,
          "Not equal: " + CompareTestReciprocal(o1, o2));
        Assert.fail(str);
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

    public static <T extends Comparable<T>> void CompareTestGreaterEqual(T o1, T o2) {
      CompareTestLessEqual(o2, o1);
    }

    public static <T extends Comparable<T>> void CompareTestLessEqual(T o1, T o2) {
      if (CompareTestReciprocal(o1, o2) > 0) {
        Assert.fail(ObjectMessages(
            o1,
            o2,
            "Not less or equal: " + CompareTestReciprocal(o1, o2)));
      }
    }

    public static <T extends Comparable<T>> void CompareTestLess(T o1, T o2, String msg) {
      if (CompareTestReciprocal(o1, o2) >= 0) {
        String str = msg + "\r\n" + ObjectMessages (
          o1,
          o2,
          "Not less: " + CompareTestReciprocal(o1, o2));
        Assert.fail(str);
      }
    }

    public static <T extends Comparable<T>> void CompareTestLessEqual(T o1, T o2, String msg) {
      if (CompareTestReciprocal(o1, o2) > 0) {
        String str = msg + "\r\n" + ObjectMessages (
          o1,
          o2,
          "Not less or equal: " + CompareTestReciprocal(o1, o2));
        Assert.fail(str);
      }
    }

    public static <T extends Comparable<T>> void CompareTestGreater(T o1, T o2, String msg) {
      if (CompareTestReciprocal(o1, o2) <= 0) {
        String str = msg + "\r\n" + ObjectMessages (
          o1,
          o2,
          "Not greater: " + CompareTestReciprocal(o1, o2));
        Assert.fail(str);
      }
    }

    public static <T extends Comparable<T>> void CompareTestGreaterEqual(T o1, T o2, String msg) {
      if (CompareTestReciprocal(o1, o2) < 0) {
        String str = msg + "\r\n" + ObjectMessages (
          o1,
          o2,
          "Not greater or equal: " + CompareTestReciprocal(o1, o2));
        Assert.fail(str);
      }
    }

    public static <T extends Comparable<T>> int CompareTestReciprocal(T o1, T o2) {
      if (o1 == null) {
        throw new NullPointerException("o1");
      }
      if (o2 == null) {
        throw new NullPointerException("o2");
      }
      int cmp, cmp2;
      cmp = ((o1.compareTo(o2) == 0) ? 0 : ((o1.compareTo(o2)< 0) ? -1 : 1));
      cmp2 = ((o2.compareTo(o1) == 0) ? 0 : ((o2.compareTo(o1)< 0) ? -1 : 1));
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

    public static String IntToString(int value) {
      if (value == Integer.MIN_VALUE) {
        return "-2147483648";
      }
      if (value == 0) {
        return "0";
      }
      boolean neg = value < 0;
      char[] chars = new char[12];
      int count = 11;
      if (neg) {
        value = -value;
      }
      while (value > 43698) {
        int intdivvalue = value / 10;
        char digit = ValueDigits.charAt((int)(value - (intdivvalue * 10)));
        chars[count--] = digit;
        value = intdivvalue;
      }
      while (value > 9) {
        int intdivvalue = (value * 26215) >> 18;
        char digit = ValueDigits.charAt((int)(value - (intdivvalue * 10)));
        chars[count--] = digit;
        value = intdivvalue;
      }
      if (value != 0) {
        chars[count--] = ValueDigits.charAt((int)value);
      }
      if (neg) {
        chars[count] = '-';
      } else {
        ++count;
      }
      return new String(chars, count, 12 - count);
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
        count = 11;
        if (neg) {
          intlongValue = -intlongValue;
        }
        while (intlongValue > 43698) {
          int intdivValue = intlongValue / 10;
          char digit = ValueDigits.charAt((int)(intlongValue - (intdivValue *
10)));
          chars[count--] = digit;
          intlongValue = intdivValue;
        }
        while (intlongValue > 9) {
          int intdivValue = (intlongValue * 26215) >> 18;
          char digit = ValueDigits.charAt((int)(intlongValue - (intdivValue *
10)));
          chars[count--] = digit;
          intlongValue = intdivValue;
        }
        if (intlongValue != 0) {
          chars[count--] = ValueDigits.charAt((int)intlongValue);
        }
        if (neg) {
          chars[count] = '-';
        } else {
          ++count;
        }
        return new String(chars, count, 12 - count);
      } else {
        chars = new char[24];
        count = 23;
        if (neg) {
          longValue = -longValue;
        }
        while (longValue > 43698) {
          long divValue = longValue / 10;
          char digit = ValueDigits.charAt((int)(longValue - (divValue * 10)));
          chars[count--] = digit;
          longValue = divValue;
        }
        while (longValue > 9) {
          long divValue = (longValue * 26215) >> 18;
          char digit = ValueDigits.charAt((int)(longValue - (divValue * 10)));
          chars[count--] = digit;
          longValue = divValue;
        }
        if (longValue != 0) {
          chars[count--] = ValueDigits.charAt((int)longValue);
        }
        if (neg) {
          chars[count] = '-';
        } else {
          ++count;
        }
        return new String(chars, count, 24 - count);
      }
    }

    public static String ObjectMessages(
      Object o1,
      Object o2,
      String s) {
      return s + ":\n" + o1 + " and\n" + o2;
    }

    public static String ObjectMessages(
      Object o1,
      Object o2,
      Object o3,
      String s) {
      return s + ":\n" + o1 + " and\n" + o2 + " and\n" + o3;
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

    public static String ToByteArrayString(byte[] bytes) {
      if (bytes == null) {
        return "null";
      }
      StringBuilder sb = new StringBuilder();
      String ValueHex = "0123456789ABCDEF";
      sb.append("new byte[] { ");
      for (int i = 0; i < bytes.length; ++i) {
        if (i > 0) {
          sb.append(","); }
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
  }
