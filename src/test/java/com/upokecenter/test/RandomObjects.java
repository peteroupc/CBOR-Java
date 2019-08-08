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
    public static byte[] RandomByteString(RandomGenerator rand) {
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int x = rand.UniformInt(0x2000);
      byte[] bytes = new byte[x];
      for (int i = 0; i < x; ++i) {
        bytes[i] = ((byte)rand.UniformInt(256));
      }
      return bytes;
    }

    public static byte[] RandomByteStringShort(RandomGenerator rand) {
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int x = rand.UniformInt(50);
      byte[] bytes = new byte[x];
      for (int i = 0; i < x; ++i) {
        bytes[i] = ((byte)rand.UniformInt(256));
      }
      return bytes;
    }

    public static ERational RandomERational(RandomGenerator rand) {
      EInteger bigintA = RandomEInteger(rand);
      EInteger bigintB = RandomEInteger(rand);
      if (bigintB.isZero()) {
        bigintB = EInteger.FromInt32(1);
      }
      return ERational.Create(bigintA, bigintB);
    }

    public static String RandomTextString(RandomGenerator rand) {
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int length = rand.UniformInt(0x2000);
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < length; ++i) {
        int x = rand.UniformInt(100);
        if (x < 95) {
          // ASCII
          sb.append((char)(0x20 + rand.UniformInt(0x60)));
        } else if (x < 98) {
          // Supplementary character
          x = rand.UniformInt(0x400) + 0xd800;
          sb.append((char)x);
          x = rand.UniformInt(0x400) + 0xdc00;
          sb.append((char)x);
        } else {
          // BMP character
          x = 0x20 + rand.UniformInt(0xffe0);
          if (x >= 0xd800 && x < 0xe000) {
            // surrogate code unit, generate ASCII instead
            x = 0x20 + rand.UniformInt(0x60);
          }
          sb.append((char)x);
        }
      }
      return sb.toString();
    }

    public static long RandomInt64(RandomGenerator rand) {
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      long r = rand.UniformInt(0x10000);
      r |= ((long)rand.UniformInt(0x10000)) << 16;
      if (rand.UniformInt(2) == 0) {
        r |= ((long)rand.UniformInt(0x10000)) << 32;
        if (rand.UniformInt(2) == 0) {
          r |= ((long)rand.UniformInt(0x10000)) << 48;
        }
      }
      return r;
    }

    public static double RandomDouble(RandomGenerator rand, int exponent) {
      if (exponent == Integer.MAX_VALUE) {
        if (rand == null) {
          throw new NullPointerException("rand");
        }
        exponent = rand.UniformInt(2047);
      }
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      long r = rand.UniformInt(0x10000);
      r |= ((long)rand.UniformInt(0x10000)) << 16;
      if (rand.UniformInt(2) == 0) {
        r |= ((long)rand.UniformInt(0x10000)) << 32;
        if (rand.UniformInt(2) == 0) {
          r |= ((long)rand.UniformInt(0x10000)) << 48;
        }
      }
      r &= ~0x7ff0000000000000L; // clear exponent
      r |= ((long)exponent) << 52; // set exponent
      return Double.longBitsToDouble(r);
    }

    public static float RandomSingle(RandomGenerator rand, int exponent) {
      if (exponent == Integer.MAX_VALUE) {
        if (rand == null) {
          throw new NullPointerException("rand");
        }
        exponent = rand.UniformInt(255);
      }
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int r = rand.UniformInt(0x10000);
      if (rand.UniformInt(2) == 0) {
        r |= ((int)rand.UniformInt(0x10000)) << 16;
      }
      r &= ~0x7f800000; // clear exponent
      r |= ((int)exponent) << 23; // set exponent
      return Float.intBitsToFloat(r);
    }

    public static EDecimal RandomEDecimal(RandomGenerator r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (r.UniformInt(100) == 0) {
        int x = r.UniformInt(3);
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

    public static EInteger RandomEInteger(RandomGenerator r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int selection = r.UniformInt(100);
      if (selection < 40) {
        StringAndBigInt sabi = StringAndBigInt.Generate(r, 16);
        return sabi.getBigIntValue();
      }
      if (selection < 50) {
        StringAndBigInt sabi = StringAndBigInt.Generate(
    r,
    2 + r.UniformInt(35));
        return sabi.getBigIntValue();
      } else {
        int count = r.UniformInt(60) + 1;
        byte[] bytes = new byte[count];
        for (int i = 0; i < count; ++i) {
          bytes[i] = (byte)((int)r.UniformInt(256));
        }
        return EInteger.FromBytes(bytes, true);
      }
    }

    public static EFloat RandomEFloat(RandomGenerator r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (r.UniformInt(100) == 0) {
        int x = r.UniformInt(3);
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
  EInteger.FromInt64(r.UniformInt(400) - 200));
    }

    public static String RandomBigIntString(RandomGenerator r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int count = r.UniformInt(50) + 1;
      StringBuilder sb = new StringBuilder();
      if (r.UniformInt(2) == 0) {
        sb.append('-');
      }
      for (int i = 0; i < count; ++i) {
        if (i == 0) {
          sb.append((char)('1' + r.UniformInt(9)));
        } else {
          sb.append((char)('0' + r.UniformInt(10)));
        }
      }
      return sb.toString();
    }

    public static EInteger RandomSmallIntegral(RandomGenerator r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int count = r.UniformInt(20) + 1;
      StringBuilder sb = new StringBuilder();
      if (r.UniformInt(2) == 0) {
        sb.append('-');
      }
      for (int i = 0; i < count; ++i) {
        if (i == 0) {
          sb.append((char)('1' + r.UniformInt(9)));
        } else {
          sb.append((char)('0' + r.UniformInt(10)));
        }
      }
      return EInteger.FromString(sb.toString());
    }

    public static String RandomDecimalString(RandomGenerator r) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      int count = r.UniformInt(40) + 1;
      StringBuilder sb = new StringBuilder();
      if (r.UniformInt(2) == 0) {
        sb.append('-');
      }
      for (int i = 0; i < count; ++i) {
        if (i == 0 && count > 1) {
          sb.append((char)('1' + r.UniformInt(9)));
        } else {
          sb.append((char)('0' + r.UniformInt(10)));
        }
      }
      if (r.UniformInt(2) == 0) {
        sb.append('.');
        count = r.UniformInt(30) + 1;
        for (int i = 0; i < count; ++i) {
          sb.append((char)('0' + r.UniformInt(10)));
        }
      }
      if (r.UniformInt(2) == 0) {
        sb.append('E');
        count = (r.UniformInt(100) < 10) ? r.UniformInt(5000) :
          r.UniformInt(20);
        if (count != 0) {
          sb.append(r.UniformInt(2) == 0 ? '+' : '-');
        }
        sb.append(TestCommon.IntToString(count));
      }
      return sb.toString();
    }
  }
