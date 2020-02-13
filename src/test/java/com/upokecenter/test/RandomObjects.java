package com.upokecenter.test;
/*
Written by Peter O. in 2014-2016.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  /// <summary>Description of RandomObjects.</summary>
  public final class RandomObjects {
private RandomObjects() {
}
    private static final int MaxExclusiveStringLength = 0x2000;
    private static final int MaxExclusiveShortStringLength = 50;
    private static final int MaxNumberLength = 100000;
    private static final int MaxShortNumberLength = 40;
    private static final int MaxStringNumDigits = 50;

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
        } else {
          // BMP character
          x = 0x20 + rand.GetInt32(0xffe0);
          if (x >= 0xd800 && x < 0xe000) {
            // surrogate code unit, generate ASCII instead
            x = 0x20 + rand.GetInt32(0x60);
          }
          sb.append((char)x);
        }
      }
      return sb.toString();
    }

    public static long RandomInt64(IRandomGenExtended rand) {
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
      return r;
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
        r |= ((int)rand.GetInt32(0x10000)) << 16;
      }
      r &= ~0x7f800000; // clear exponent
      r |= ((int)exponent) << 23; // set exponent
      return Float.intBitsToFloat(r);
    }

    private static String GenerateEDecimalSmallString(IRandomGenExtended
wrapper, boolean extended) {
       StringBuilder sb = new StringBuilder();
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
       return EDecimal.FromString(GenerateEDecimalSmallString(wrapper, false));
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
            decimalString[0]="Infinity";
          }
          return EDecimal.PositiveInfinity;
        }
        if (x == 1) {
          if (decimalString != null) {
            decimalString[0]="-Infinity";
          }
          return EDecimal.NegativeInfinity;
        }
        if (x == 2) {
          if (decimalString != null) {
            decimalString[0]="NaN";
          }
          return EDecimal.NaN;
        }
        // Signaling NaN currently not generated because
        // it doesn't round-trip as well
      }
      if (r.GetInt32(100) < 10) {
        String str = RandomDecimalString(r);
        if (str.length() < 500) {
          if (decimalString != null) {
            decimalString[0]=str;
          }
          return EDecimal.FromString(str);
        }
      }
      EInteger emant = RandomEInteger(r);
      EInteger eexp = RandomEInteger(r);
      EDecimal ed = EDecimal.Create(emant, eexp);
      if (decimalString != null) {
        decimalString[0]=emant.toString() + "E" + eexp.toString();
      }
      return ed;
    }

    public static EInteger RandomEInteger(IRandomGenExtended r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int selection = r.GetInt32(100);
      if (selection < 10) {
        int count = r.GetInt32(MaxNumberLength) + 1;
        byte[] bytes = new byte[count];
        r.GetBytes(bytes, 0, bytes.length);
        return EInteger.FromBytes(bytes, true);
      }
      if (selection < 50) {
        StringAndBigInt sabi = StringAndBigInt.Generate(
            r,
            2 + r.GetInt32(35),
            MaxStringNumDigits);
        return sabi.getBigIntValue();
      } else {
        int count = r.GetInt32(MaxShortNumberLength) + 1;
        byte[] bytes = new byte[count];
        r.GetBytes(bytes, 0, bytes.length);
        return EInteger.FromBytes(bytes, true);
      }
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
      return EFloat.Create(
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

    private static void AppendRandomDecimalsLong(IRandomGenExtended r,
  StringBuilder sb, long count) {
      if ((sb) == null) {
        throw new NullPointerException("sb");
      }
      if ((r) == null) {
        throw new NullPointerException("r");
      }
      if (count > 0) {
        int buflen = (int)Math.min(0x10000, Math.max(count + 8, 64));
        byte[] buffer = new byte[buflen];
        while (count>0) {
          r.GetBytes(buffer, 0, buflen);
          for (int i = 0;i<buflen && count>0; ++i) {
            int x=((int)buffer[i]) & 31;
            if (x< 30) {
              sb.append((char)(0x30 + (x % 10)));
              --count;
            }
          }
        }
      }
    }

    private static void AppendRandomDecimals(IRandomGenExtended r,
  StringBuilder sb, int smallCount) {
      AppendRandomDecimalsLong(r, sb, smallCount);
    }

    public static String RandomDecimalString(
      IRandomGenExtended r,
      boolean extended,
      boolean limitedExponent) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (r.GetInt32(100) < 10) {
        return GenerateEDecimalSmallString(r, extended);
      }
      long count = ((long)r.GetInt32(MaxNumberLength) *
r.GetInt32(MaxNumberLength)) / MaxNumberLength;
      count = Math.max(1, count);
      long afterPointCount = 0;
      long exponentCount = 0;
      boolean smallExponent = false;
      if (r.GetInt32(2) == 0) {
        afterPointCount = ((long)r.GetInt32(MaxNumberLength) *
r.GetInt32(MaxNumberLength)) / MaxNumberLength;
        afterPointCount = ((long)count *
r.GetInt32(MaxNumberLength)) / MaxNumberLength;
        afterPointCount = Math.max(1, count);
      }
      if (r.GetInt32(2) == 0) {
        if (limitedExponent || r.GetInt32(10) > 0) {
          exponentCount = 5;
        } else {
          exponentCount = ((long)r.GetInt32(MaxNumberLength) *
r.GetInt32(MaxNumberLength)) / MaxNumberLength;
          exponentCount = ((long)count *
r.GetInt32(MaxNumberLength)) / MaxNumberLength;
          exponentCount = Math.max(1, count);
        }
      }
      int bufferSize=(int)Math.min(
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
          sb.append("E");
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
