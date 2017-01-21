package com.upokecenter.cbor;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

    /**
     * Contains utility methods that may have use outside of the CBORObject class.
     */
  final class CBORUtilities {
private CBORUtilities() {
}
    private static final String HexAlphabet = "0123456789ABCDEF";

    public static void ToBase16(StringBuilder str, byte[] data) {
      if (data == null) {
        throw new NullPointerException("data");
      }
      int length = data.length;
      for (int i = 0; i < length; ++i) {
        str.append(HexAlphabet.charAt((data[i] >> 4) & 15));
        str.append(HexAlphabet.charAt(data[i] & 15));
      }
    }

    public static boolean ByteArrayEquals(byte[] a, byte[] b) {
      if (a == null) {
        return b == null;
      }
      if (b == null) {
        return false;
      }
      if (a.length != b.length) {
        return false;
      }
      for (int i = 0; i < a.length; ++i) {
        if (a[i] != b[i]) {
          return false;
        }
      }
      return true;
    }

    public static int ByteArrayHashCode(byte[] a) {
      if (a == null) {
        return 0;
      }
      int ret = 19;
      {
        ret = (ret * 31) + a.length;
        for (int i = 0; i < a.length; ++i) {
          ret = (ret * 31) + a[i];
        }
      }
      return ret;
    }

    public static int ByteArrayCompare(byte[] a, byte[] b) {
      if (a == null) {
        return (b == null) ? 0 : -1;
      }
      if (b == null) {
        return 1;
      }
      int c = Math.min(a.length, b.length);
      for (int i = 0; i < c; ++i) {
        if (a[i] != b[i]) {
          return (a[i] < b[i]) ? -1 : 1;
        }
      }
      return (a.length != b.length) ? ((a.length < b.length) ? -1 : 1) : 0;
    }

    public static String DoubleToString(double dbl) {
      return EFloat.FromDouble(dbl).ToShortestString(EContext.Binary64);
    }

    public static String SingleToString(float sing) {
      return EFloat.FromSingle(sing).ToShortestString(EContext.Binary32);
    }

    public static EInteger BigIntegerFromSingle(float flt) {
      int value = Float.floatToRawIntBits(flt);
      int fpexponent = (int)((value >> 23) & 0xff);
      if (fpexponent == 255) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      int mantissa = value & 0x7fffff;
      if (fpexponent == 0) {
        ++fpexponent;
      } else {
        mantissa |= 1 << 23;
      }
      if (mantissa == 0) {
        return EInteger.FromInt32(0);
      }
      fpexponent -= 150;
      while ((mantissa & 1) == 0) {
        ++fpexponent;
        mantissa >>= 1;
      }
      boolean neg = (value >> 31) != 0;
      if (fpexponent == 0) {
        if (neg) {
          mantissa = -mantissa;
        }
        return EInteger.FromInt32(mantissa);
      }
      if (fpexponent > 0) {
        // Value is an integer
        EInteger bigmantissa = EInteger.FromInt32(mantissa);
        bigmantissa = bigmantissa.ShiftLeft(fpexponent);
        if (neg) {
          bigmantissa=(bigmantissa).Negate();
        }
        return bigmantissa;
      } else {
        // Value has a fractional part
        int exp = -fpexponent;
        for (int i = 0; i < exp && mantissa != 0; ++i) {
          mantissa >>= 1;
        }
        return EInteger.FromInt32(mantissa);
      }
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
        char digit = HexAlphabet.charAt((int)(intlongValue - (intdivValue * 10)));
        chars[count--] = digit;
        intlongValue = intdivValue;
      }
      while (intlongValue > 9) {
        int intdivValue = (intlongValue * 26215) >> 18;
        char digit = HexAlphabet.charAt((int)(intlongValue - (intdivValue * 10)));
        chars[count--] = digit;
        intlongValue = intdivValue;
      }
      if (intlongValue != 0) {
        chars[count--] = HexAlphabet.charAt((int)intlongValue);
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
        char digit = HexAlphabet.charAt((int)(longValue - (divValue * 10)));
        chars[count--] = digit;
        longValue = divValue;
      }
      while (longValue > 9) {
        long divValue = (longValue * 26215) >> 18;
        char digit = HexAlphabet.charAt((int)(longValue - (divValue * 10)));
        chars[count--] = digit;
        longValue = divValue;
      }
      if (longValue != 0) {
        chars[count--] = HexAlphabet.charAt((int)longValue);
      }
      if (neg) {
        chars[count] = '-';
      } else {
        ++count;
      }
      return new String(chars, count, 24 - count);
      }
    }

    public static String BigIntToString(EInteger bigint) {
      return bigint.toString();
    }

    public static EInteger BigIntegerFromDouble(double dbl) {
      long lvalue = Double.doubleToRawLongBits(dbl);
      int value0 = ((int)(lvalue & 0xffffffffL));
      int value1 = ((int)((lvalue >> 32) & 0xffffffffL));
      int floatExponent = (int)((value1 >> 20) & 0x7ff);
      boolean neg = (value1 >> 31) != 0;
      if (floatExponent == 2047) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      value1 &= 0xfffff; // Mask out the exponent and sign
      if (floatExponent == 0) {
        ++floatExponent;
      } else {
        value1 |= 0x100000;
      }
      if ((value1 | value0) != 0) {
        while ((value0 & 1) == 0) {
          value0 >>= 1;
          value0 &= 0x7fffffff;
          value0 = (value0 | (value1 << 31));
          value1 >>= 1;
          ++floatExponent;
        }
      }
      floatExponent -= 1075;
      byte[] bytes = new byte[9];
      EInteger bigmantissa;
      bytes[0] = (byte)(value0 & 0xff);
      bytes[1] = (byte)((value0 >> 8) & 0xff);
      bytes[2] = (byte)((value0 >> 16) & 0xff);
      bytes[3] = (byte)((value0 >> 24) & 0xff);
      bytes[4] = (byte)(value1 & 0xff);
      bytes[5] = (byte)((value1 >> 8) & 0xff);
      bytes[6] = (byte)((value1 >> 16) & 0xff);
      bytes[7] = (byte)((value1 >> 24) & 0xff);
      bytes[8] = (byte)0;
      bigmantissa = EInteger.FromBytes(bytes, true);
      if (floatExponent == 0) {
        if (neg) {
          bigmantissa = bigmantissa.Negate();
        }
        return bigmantissa;
      }
      if (floatExponent > 0) {
        // Value is an integer
        bigmantissa = bigmantissa.ShiftLeft(floatExponent);
        if (neg) {
          bigmantissa=(bigmantissa).Negate();
        }
        return bigmantissa;
      } else {
        // Value has a fractional part
        int exp = -floatExponent;
        bigmantissa = bigmantissa.ShiftRight(exp);
        if (neg) {
          bigmantissa=(bigmantissa).Negate();
        }
        return bigmantissa;
      }
    }

    public static float HalfPrecisionToSingle(int value) {
      int negvalue = (value >= 0x8000) ? (1 << 31) : 0;
      value &= 0x7fff;
      if (value >= 0x7c00) {
        value = (int)(0x3fc00 | (value & 0x3ff)) << 13 | negvalue;
        return Float.intBitsToFloat(value);
      }
      if (value > 0x400) {
        value = (int)((value + 0x1c000) << 13) | negvalue;
        return Float.intBitsToFloat(value);
      }
      if ((value & 0x400) == value) {
        value = (int)((value == 0) ? 0 : 0x38800000) | negvalue;
        return Float.intBitsToFloat(value);
      } else {
        // denormalized
        int m = value & 0x3ff;
        value = 0x1c400;
        while ((m >> 10) == 0) {
          value -= 0x400;
          m <<= 1;
        }
        value = ((value | (m & 0x3ff)) << 13) | negvalue;
        return Float.intBitsToFloat(value);
      }
    }
  }
