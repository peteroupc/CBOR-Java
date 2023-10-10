package com.upokecenter.test;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  /**
   * Generates random objects of various kinds for purposes of testing code that
   * uses them. The methods will not necessarily sample uniformly from all
   * objects of a particular kind.
   */
  public final class RandomObjects {
private RandomObjects() {
}
    private static final int MaxExclusiveStringLength = 0x2000;
    private static final int MaxExclusiveShortStringLength = 50;
    private static final int MaxNumberLength = 50000;
    private static final int MaxShortNumberLength = 40;

    public static byte[] RandomUtf8Bytes(
      IRandomGenExtended rg) {
      return RandomUtf8Bytes(rg, false);
    }

    public static byte[] RandomUtf8Bytes(
      IRandomGenExtended rg,
      boolean jsonSafe) {
      using java.io.ByteArrayOutputStream ms = new java.io.ByteArrayOutputStream();
      if (rg == null) {
        throw new NullPointerException("rg");
      }
      int length = 1 + rg.GetInt32(6);
      for (int i = 0; i < length; ++i) {
        int v = rg.GetInt32(4);
        if (v == 0) {
          int b = 0xe0 + rg.GetInt32(0xee - 0xe1);
          ms.write((byte)b);
          if (b == 0xe0) {
            ms.write((byte)(0xa0 + rg.GetInt32(0x20)));
          } else if (b == 0xed) {
            ms.write((byte)(0x80 + rg.GetInt32(0x20)));
          } else {
            ms.write((byte)(0x80 + rg.GetInt32(0x40)));
          }
          ms.write((byte)(0x80 + rg.GetInt32(0x40)));
        } else if (v == 1) {
          int b = 0xf0 + rg.GetInt32(0xf5 - 0xf0);
          ms.write((byte)b);
          if (b == 0xf0) {
            ms.write((byte)(0x90 + rg.GetInt32(0x30)));
          } else if (b == 0xf4) {
            ms.write((byte)(0x80 + rg.GetInt32(0x10)));
          } else {
            ms.write((byte)(0x80 + rg.GetInt32(0x40)));
          }
          ms.write((byte)(0x80 + rg.GetInt32(0x40)));
          ms.write((byte)(0x80 + rg.GetInt32(0x40)));
        } else if (v == 2) {
          if (rg.GetInt32(100) < 5) {
            // 0x80, to help detect ASCII off-by-one errors
            ms.write(0xc2);
            ms.write(0x80);
          } else {
            ms.write((byte)(0xc2 + rg.GetInt32(0xe0 - 0xc2)));
            ms.write((byte)(0x80 + rg.GetInt32(0x40)));
          }
        } else {
          int ch = rg.GetInt32(0x80);
          if (jsonSafe && (ch == '\\' || ch == '\"' || ch < 0x20)) {
            ch = '?';
          }
          ms.write((byte)ch);
        }
      }
      return ms.toByteArray();
    }

    public static byte[] RandomByteString(IRandomGenExtended rand) {
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int x = rand.GetInt32(MaxExclusiveStringLength);
      byte[] bytes = new byte[x];
      rand.GetBytes(bytes, 0, bytes.length);
      return bytes;
    }

    public static byte[] RandomByteString(IRandomGenExtended rand, int length) {
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      byte[] bytes = new byte[length];
      rand.GetBytes(bytes, 0, bytes.length);
      return bytes;
    }

    public static byte[] RandomByteStringShort(IRandomGenExtended rand) {
      if (rand == null) {
 throw new NullPointerException("rand");
}
 return RandomByteString(
          rand,
          rand.GetInt32(MaxExclusiveShortStringLength));
    }

    public static ERational RandomERational(IRandomGenExtended rand) {
      EInteger bigintA = RandomEInteger(rand);
      EInteger bigintB = RandomEInteger(rand);
      if (bigintB.isZero()) {
        bigintB = EInteger.FromInt32(1);
      }
      return ERational.Create(bigintA, bigintB);
    }

    public static String RandomTextString(IRandomGenExtended rand) {
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int length = rand.GetInt32(MaxExclusiveStringLength);
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < length; ++i) {
        int x = rand.GetInt32(100);
        if (x < 95) {
          // ASCII
          sb.append((char)(0x20 + rand.GetInt32(0x60)));
        } else if (x < 98) {
          // Supplementary character
          x = rand.GetInt32(0x400) + 0xd800;
          sb.append((char)x);
          x = rand.GetInt32(0x400) + 0xdc00;
          sb.append((char)x);
        } else if (rand.GetInt32(100) < 5) {
          // 0x80, to help detect ASCII off-by-one errors
          sb.append((char)0x80);
        } else {
          // BMP character
          x = 0x20 + rand.GetInt32(0xffe0);
          if (x instanceof >= 0xd800 and < 0xe000) {
            // surrogate code unit, generate ASCII instead
            x = 0x20 + rand.GetInt32(0x60);
          }
          sb.append((char)x);
        }
      }
      return sb.toString();
    }

    public static int RandomInt32(IRandomGenExtended rand) {
      byte[] bytes = RandomByteString(rand, 4);
      int ret = bytes[0] & 0xff;
      ret |= (bytes[1] & 0xff) << 8;
      ret |= (bytes[2] & 0xff) << 16;
      ret |= (bytes[3] & 0xff) << 24;
      return ret;
    }

    public static long RandomInt64(IRandomGenExtended rand) {
      byte[] bytes = RandomByteString(rand, 8);
      long ret = ((long)bytes[0]) & 0xff;
      ret |= (((long)bytes[1]) & 0xff) << 8;
      ret |= (((long)bytes[2]) & 0xff) << 16;
      ret |= (((long)bytes[3]) & 0xff) << 24;
      ret |= (((long)bytes[4]) & 0xff) << 32;
      ret |= (((long)bytes[5]) & 0xff) << 40;
      ret |= (((long)bytes[6]) & 0xff) << 48;
      ret |= (((long)bytes[7]) & 0xff) << 56;
      return ret;
    }

    public static double RandomDouble(IRandomGenExtended rand, int exponent) {
      if (exponent == Integer.MAX_VALUE) {
        if (rand == null) {
          throw new NullPointerException("rand");
        }
        exponent = rand.GetInt32(2047);
      }
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      long r = rand.GetInt32(0x10000);
      r |= ((long)rand.GetInt32(0x10000)) << 16;
      if (rand.GetInt32(2) == 0) {
        r |= ((long)rand.GetInt32(0x10000)) << 32;
        if (rand.GetInt32(2) == 0) {
          r |= ((long)rand.GetInt32(0x10000)) << 48;
        }
      }
      r &= ~0x7ff0000000000000L; // clear exponent
      r |= ((long)exponent) << 52; // set exponent
      return Double.longBitsToDouble(r);
    }

    public static double RandomFiniteDouble(IRandomGenExtended rand) {
      long r;
      do {
        r = RandomInt64(rand);
      } while (((r >> 52) & 0x7ff) == 0x7ff);
      return Double.longBitsToDouble(r);
    }

    public static double RandomDouble(IRandomGenExtended rand) {
      long r = RandomInt64(rand);
      return Double.longBitsToDouble(r);
    }

    public static float RandomSingle(IRandomGenExtended rand) {
      int r = RandomInt32(rand);
      return Float.intBitsToFloat(r);
    }

    public static float RandomSingle(IRandomGenExtended rand, int exponent) {
      if (exponent == Integer.MAX_VALUE) {
        if (rand == null) {
          throw new NullPointerException("rand");
        }
        exponent = rand.GetInt32(255);
      }
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int r = rand.GetInt32(0x10000);
      if (rand.GetInt32(2) == 0) {
        r |= rand.GetInt32(0x10000) << 16;
      }
      r &= ~0x7f800000; // clear exponent
      r |= exponent << 23; // set exponent
      return Float.intBitsToFloat(r);
    }

    public static String RandomDecimalStringShort(
      IRandomGenExtended wrapper,
      boolean extended) {
      StringBuilder sb = new StringBuilder();
      if (wrapper == null) {
        throw new NullPointerException("wrapper");
      }
      int len = 1 + wrapper.GetInt32(4);
      if (!extended) {
        sb.append((char)('1' + wrapper.GetInt32(9)));
        --len;
      }
      AppendRandomDecimals(wrapper, sb, len);
      sb.append('.');
      len = 1 + wrapper.GetInt32(36);
      AppendRandomDecimals(wrapper, sb, len);
      sb.append('E');
      len = wrapper.GetInt32(25) - 12;
      sb.append(TestCommon.IntToString(len));
      return sb.toString();
    }

    public static EDecimal GenerateEDecimalSmall(IRandomGenExtended wrapper) {
      if (wrapper == null) {
        throw new NullPointerException("wrapper");
      }
      if (wrapper.GetInt32(2) == 0) {
        EInteger eix = EInteger.FromBytes(
            RandomByteString(wrapper, 1 + wrapper.GetInt32(36)),
            true);
        int exp = wrapper.GetInt32(25) - 12;
        return EDecimal.Create(eix, exp);
      }
      return EDecimal.FromString(RandomDecimalStringShort(wrapper, false));
    }

    public static EDecimal RandomEDecimal(IRandomGenExtended r) {
      return RandomEDecimal(r, null);
    }

    public static EDecimal RandomEDecimal(IRandomGenExtended r, String[]
      decimalString) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (r.GetInt32(100) == 0) {
        int x = r.GetInt32(3);
        if (x == 0) {
          if (decimalString != null) {
            decimalString[0] = "Infinity";
          }
          return EDecimal.PositiveInfinity;
        }
        if (x == 1) {
          if (decimalString != null) {
            decimalString[0] = "-Infinity";
          }
          return EDecimal.NegativeInfinity;
        }
        if (x == 2) {
          if (decimalString != null) {
            decimalString[0] = "NaN";
          }
          return EDecimal.NaN;
        }
        // Signaling NaN currently not generated because
        // it doesn't round-trip as well
      }
      if (r.GetInt32(100) < 30) {
        String str = RandomDecimalString(r);
        if (str.length() < 500) {
          if (decimalString != null) {
            decimalString[0] = str;
          }
          return EDecimal.FromString(str);
        }
      }
      EInteger emant = RandomEInteger(r);
      EInteger eexp;
      if (r.GetInt32(100) < 95) {
        int exp = (r.GetInt32(100) < 80) ? (r.GetInt32(50) - 25) :
          (r.GetInt32(5000) - 2500);
        eexp = EInteger.FromInt32(exp);
      } else {
        eexp = RandomEInteger(r);
      }
      var ed = EDecimal.Create(emant, eexp);
      if (decimalString != null) {
        decimalString[0] = emant.toString() + "E" + eexp.toString();
      }
      return ed;
    }

    private static EInteger BitHeavyEInteger(IRandomGenExtended rg, int count) {
      StringBuilder sb = new StringBuilder();
      int[] oneChances = {
        999, 1, 980, 20, 750, 250, 980,
        20, 980, 20, 980, 20, 750, 250,
      };
      int oneChance = oneChances[rg.GetInt32(oneChances.length)];
      for (int i = 0; i < count; ++i) {
        sb.append((rg.GetInt32(1000) >= oneChance) ? '0' : '1');
      }
      return EInteger.FromRadixString(sb.toString(), 2);
    }

    private static EInteger DigitHeavyEInteger(IRandomGenExtended rg, int
count) {
      StringBuilder sb = new StringBuilder();
      int[] oneChances = {
        999, 1, 980, 20, 750, 250, 980,
        20, 980, 20, 980, 20, 750, 250,
      };
      int oneChance = oneChances[rg.GetInt32(oneChances.length)];
      for (int i = 0; i < count; ++i) {
        sb.append((rg.GetInt32(1000) >= oneChance) ? '0' : '9');
      }
      return EInteger.FromRadixString(sb.toString(), 10);
    }

    public static EInteger RandomEInteger(IRandomGenExtended r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int selection = r.GetInt32(100);
      if (selection < 10) {
        int count = r.GetInt32(MaxNumberLength);
        count = (int)((long)count * r.GetInt32(MaxNumberLength) /
            MaxNumberLength);
        count = (int)((long)count * r.GetInt32(MaxNumberLength) /
            MaxNumberLength);
        count = Math.max(count, 1);
        if (selection instanceof 0 or 1) {
          return BitHeavyEInteger(r, count);
        } else if ((selection == 2 || selection == 3) && count < 500) {
          return DigitHeavyEInteger(r, count);
        }
        byte[] bytes = RandomByteString(r, count);
        return EInteger.FromBytes(bytes, true);
      } else {
        byte[] bytes = RandomByteString(
            r,
            r.GetInt32(MaxShortNumberLength) + 1);
        return EInteger.FromBytes(bytes, true);
      }
    }

    public static EInteger RandomEIntegerSmall(IRandomGenExtended r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      byte[] bytes = RandomByteString(
          r,
          r.GetInt32(MaxShortNumberLength) + 1);
      return EInteger.FromBytes(bytes, true);
    }

    private static int IntInRange(IRandomGenExtended rg, int minInc, int
maxExc) {
      return minInc + rg.GetInt32(maxExc - minInc);
    }

    public static EFloat CloseToPowerOfTwo(IRandomGenExtended rg) {
      if (rg == null) {
        throw new NullPointerException("rg");
      }
      int pwr = (rg.GetInt32(100) < 80) ? IntInRange(rg, -20, 20) :
        IntInRange(rg, -300, 300);
      int pwr2 = pwr - (rg.GetInt32(100) < 80 ? IntInRange(rg, 51, 61) :
        IntInRange(rg, 2, 300));
      EFloat ef = rg.GetInt32(2) == 0 ? EFloat.Create(1,
  pwr).Add(EFloat.Create(1, pwr2)) : EFloat.Create(1,
  pwr).Subtract(EFloat.Create(1, pwr2));
      if (rg.GetInt32(10) == 0) {
        pwr2 = pwr - (rg.GetInt32(100) < 80 ? IntInRange(rg, 51, 61) :
          IntInRange(rg, 2, 300));
        ef = (rg.GetInt32(2) == 0) ? ef.Add(EFloat.Create(1, pwr2)) :
          ef.Subtract(EFloat.Create(1, pwr2));
      }
      return ef;
    }

    public static EFloat RandomEFloat(IRandomGenExtended r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (r.GetInt32(100) == 0) {
        int x = r.GetInt32(3);
        if (x == 0) {
          return EFloat.PositiveInfinity;
        }
        if (x == 1) {
          return EFloat.NegativeInfinity;
        }
        if (x == 2) {
          return EFloat.NaN;
        }
      }
      return r.GetInt32(100) == 3 ?
        CloseToPowerOfTwo(r) : EFloat.Create(
          RandomEInteger(r),
          EInteger.FromInt64(r.GetInt32(400) - 200));
    }

    public static String RandomBigIntString(IRandomGenExtended r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int count = r.GetInt32(MaxShortNumberLength) + 1;
      StringBuilder sb = new StringBuilder();
      if (r.GetInt32(2) == 0) {
        sb.append('-');
      }
      sb.append((char)('1' + r.GetInt32(9)));
      --count;
      AppendRandomDecimals(r, sb, count);
      return sb.toString();
    }

    public static EInteger RandomSmallIntegral(IRandomGenExtended r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int count = r.GetInt32(MaxShortNumberLength / 2) + 1;
      StringBuilder sb = new StringBuilder();
      if (r.GetInt32(2) == 0) {
        sb.append('-');
      }
      sb.append((char)('1' + r.GetInt32(9)));
      --count;
      AppendRandomDecimals(r, sb, count);
      return EInteger.FromString(sb.toString());
    }

    public static String RandomDecimalString(IRandomGenExtended r) {
      return RandomDecimalString(r, false, true);
    }

    private static final char[] CharTable = {
      '0', '0', '0', '1', '1', '1', '2', '2', '2', '3', '3', '3', '4', '4', '4',
      '5', '5', '5', '6', '6', '6', '7', '7', '7', '8', '8', '8', '9', '9', '9',
    };

    // Special 10-digit-long strings
    private static final String[] ValueSpecialDecimals = {
      "1000000000",
      "0000000001",
      "4999999999",
      "5000000000",
      "5000000001",
      "5500000000",
      "0000000000",
      "9999999999",
    };

    // Special 40-digit-long strings
    private static final String[] ValueSpecialDecimals2 = {
      "1000000000000000000000000000000000000000",
      "0000000000000000000000000000000000000001",
      "4999999999999999999999999999999999999999",
      "5000000000000000000000000000000000000000",
      "5000000000000000000000000000000000000001",
      "5500000000000000000000000000000000000000",
      "0000000000000000000000000000000000000000",
      "9999999999999999999999999999999999999999",
    };

    private static void AppendRandomDecimalsLong(
      IRandomGenExtended r,
      StringBuilder sb,
      long count) {
      if (sb == null) {
        throw new NullPointerException("sb");
      }
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (count > 0) {
        int buflen = (int)Math.min(0x10000, Math.max(count + 8, 64));
        byte[] buffer = new byte[buflen];
        while (count > 0) {
          r.GetBytes(buffer, 0, buflen);
          int i = 0;
          while (i < buflen && count > 0) {
            int x = buffer[i] & 31;
            if (x < 30) {
              sb.append(CharTable[x]);
              --count;
              ++i;
            } else if (count >= 40 && i + 1 < buflen) {
              int y = (buffer[i + 1] & 0xff) % ValueSpecialDecimals2.length;
              sb.append(ValueSpecialDecimals2[y]);
              count -= 40;
              i += 2;
            } else if (count >= 10 && i + 1 < buflen) {
              int y = (buffer[i + 1] & 0xff) % ValueSpecialDecimals.length;
              sb.append(ValueSpecialDecimals[y]);
              count -= 10;
              i += 2;
            } else {
              ++i;
            }
          }
        }
      }
    }

    private static void AppendRandomDecimals(
      IRandomGenExtended r,
      StringBuilder sb,
      int smallCount) {
      AppendRandomDecimalsLong(r, sb, smallCount);
    }

    public static String RandomDecimalStringShort(
      IRandomGenExtended r) {
      return RandomDecimalStringShort(r, false);
    }

    public static String RandomDecimalString(
      IRandomGenExtended r,
      boolean extended,
      boolean limitedExponent) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (r.GetInt32(100) < 95) {
        return RandomDecimalStringShort(r, extended);
      }
      long count = (long)r.GetInt32(MaxNumberLength) *
          r.GetInt32(MaxNumberLength) / MaxNumberLength;
      count *= r.GetInt32(MaxNumberLength) / MaxNumberLength;
      count = Math.max(1, count);
      long afterPointCount = 0;
      long exponentCount = 0;
      boolean smallExponent = false;
      if (r.GetInt32(2) == 0) {
        afterPointCount = (long)r.GetInt32(MaxNumberLength) *
            r.GetInt32(MaxNumberLength) / MaxNumberLength;
        afterPointCount = afterPointCount *
            r.GetInt32(MaxNumberLength) / MaxNumberLength;
        afterPointCount = Math.max(1, afterPointCount);
      }
      if (r.GetInt32(2) == 0) {
        if (limitedExponent || r.GetInt32(10) > 0) {
          exponentCount = 5;
        } else {
          exponentCount = (long)r.GetInt32(MaxNumberLength) *
              r.GetInt32(MaxNumberLength) / MaxNumberLength;
          exponentCount = exponentCount *
              r.GetInt32(MaxNumberLength) / MaxNumberLength;
          exponentCount = exponentCount *
              r.GetInt32(MaxNumberLength) / MaxNumberLength;
          exponentCount = Math.max(1, exponentCount);
        }
      }
      int bufferSize = (int)Math.min(
          Integer.MAX_VALUE,
          8 + count + afterPointCount + exponentCount);
      StringBuilder sb = new StringBuilder(bufferSize);
      if (r.GetInt32(2) == 0) {
        sb.append('-');
      }
      if (!extended) {
        sb.append((char)('1' + r.GetInt32(9)));
        --count;
      }
      AppendRandomDecimalsLong(r, sb, count);
      if (afterPointCount > 0) {
        sb.append('.');
        AppendRandomDecimalsLong(r, sb, afterPointCount);
      }
      if (exponentCount > 0) {
        int rr = r.GetInt32(3);
        if (rr == 0) {
          sb.append('E');
        } else if (rr == 1) {
          sb.append("E+");
        } else if (rr == 2) {
          sb.append("E-");
        }
        if (smallExponent) {
          sb.append(TestCommon.IntToString(r.GetInt32(10000)));
        } else {
          AppendRandomDecimalsLong(r, sb, exponentCount);
        }
      }
      return sb.toString();
    }
  }
