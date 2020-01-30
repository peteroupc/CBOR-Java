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

  /**
   * Description of RandomObjects.
   */
  public final class RandomObjects {
private RandomObjects() {
}
    private static final int MaxExclusiveStringLength = 0x2000;
    private static final int MaxExclusiveExponentLength = 0x2000;
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
      for (int i = 0; i < x; ++i) {
        bytes[i] = ((byte)rand.GetInt32(256));
      }
      return bytes;
    }

    public static byte[] RandomByteStringShort(IRandomGenExtended rand) {
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int x = rand.GetInt32(MaxExclusiveShortStringLength);
      byte[] bytes = new byte[x];
      for (int i = 0; i < x; ++i) {
        bytes[i] = ((byte)rand.GetInt32(256));
      }
      return bytes;
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

    public static EDecimal RandomEDecimal(IRandomGenExtended r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (r.GetInt32(100) == 0) {
        int x = r.GetInt32(3);
        if (x == 0) {
          return EDecimal.PositiveInfinity;
        }
        if (x == 1) {
          return EDecimal.NegativeInfinity;
        }
        if (x == 2) {
          return EDecimal.NaN;
        }
        // Signaling NaN currently not generated because
        // it doesn't round-trip as well
      }
      String str = RandomDecimalString(r);
      return EDecimal.FromString(str);
    }

    public static EInteger RandomEInteger(IRandomGenExtended r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int selection = r.GetInt32(100);
      if (selection < 40) {
        StringAndBigInt sabi = StringAndBigInt.Generate(
          r,
          16,
          MaxStringNumDigits);
        return sabi.getBigIntValue();
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
        for (int i = 0; i < count; ++i) {
          bytes[i] = (byte)((int)r.GetInt32(256));
        }
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
      for (int i = 0; i < count; ++i) {
        if (i == 0) {
          sb.append((char)('1' + r.GetInt32(9)));
        } else {
          sb.append((char)('0' + r.GetInt32(10)));
        }
      }
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
      for (int i = 0; i < count; ++i) {
        if (i == 0) {
          sb.append((char)('1' + r.GetInt32(9)));
        } else {
          sb.append((char)('0' + r.GetInt32(10)));
        }
      }
      return EInteger.FromString(sb.toString());
    }

    public static String RandomDecimalString(IRandomGenExtended r) {
      return RandomDecimalString(r, false, true);
    }

    public static String RandomDecimalString(IRandomGenExtended r, boolean
extended, boolean limitedExponent) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      long count = ((long)r.GetInt32(MaxNumberLength) *
r.GetInt32(MaxNumberLength)) / MaxNumberLength;
      count = Math.max(1, count);
      StringBuilder sb = new StringBuilder();
      if (r.GetInt32(2) == 0) {
        sb.append('-');
      }
      for (int i = 0; i < count; ++i) {
        if (i == 0 && count > 1 && !extended) {
          sb.append((char)('1' + r.GetInt32(9)));
        } else {
          sb.append((char)('0' + r.GetInt32(10)));
        }
      }
      if (r.GetInt32(2) == 0) {
        sb.append('.');
        count = ((long)r.GetInt32(MaxNumberLength) *
r.GetInt32(MaxNumberLength)) / MaxNumberLength;
        count = Math.max(1, count);
        for (int i = 0; i < count; ++i) {
          sb.append((char)('0' + r.GetInt32(10)));
        }
      }
      if (r.GetInt32(2) == 0) {
        int rr = r.GetInt32(3);
if (rr == 0) {
          sb.append("E");
        } else if (rr == 1) {
   sb.append("E+");
 } else if (rr == 2) {
   sb.append("E-");
 }
        if (limitedExponent || r.GetInt32(10) > 0) {
   sb.append(TestCommon.IntToString(r.GetInt32(10000)));
        } else {
          count = ((long)r.GetInt32(MaxNumberLength) *
r.GetInt32(MaxNumberLength)) / MaxNumberLength;
          count = Math.max(1, count);
          for (int i = 0; i < count; ++i) {
            sb.append((char)(0x30 + r.GetInt32(10)));
          }
        }
      }
      return sb.toString();
    }
  }
