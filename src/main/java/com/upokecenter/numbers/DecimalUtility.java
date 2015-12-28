package com.upokecenter.numbers;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

  public final class DecimalUtility {
private DecimalUtility() {
}
    private static final EInteger[] valueBigIntPowersOfTen = {
      EInteger.FromInt64(1), EInteger.FromInt64(10), EInteger.FromInt64(100), EInteger.FromInt64(1000),
      EInteger.FromInt64(10000), EInteger.FromInt64(100000), EInteger.FromInt64(1000000),
      EInteger.FromInt64(10000000), EInteger.FromInt64(100000000), EInteger.FromInt64(1000000000),
      EInteger.FromInt64(10000000000L), EInteger.FromInt64(100000000000L),
      EInteger.FromInt64(1000000000000L), EInteger.FromInt64(10000000000000L),
      EInteger.FromInt64(100000000000000L), EInteger.FromInt64(1000000000000000L),
      EInteger.FromInt64(10000000000000000L),
      EInteger.FromInt64(100000000000000000L), EInteger.FromInt64(1000000000000000000L)
    };

    private static final EInteger[] valueBigIntPowersOfFive = {
      EInteger.FromInt64(1), EInteger.FromInt64(5), EInteger.FromInt64(25), EInteger.FromInt64(125),
      EInteger.FromInt64(625), EInteger.FromInt64(3125), EInteger.FromInt64(15625),
      EInteger.FromInt64(78125), EInteger.FromInt64(390625),
      EInteger.FromInt64(1953125), EInteger.FromInt64(9765625), EInteger.FromInt64(48828125),
      EInteger.FromInt64(244140625), EInteger.FromInt64(1220703125),
      EInteger.FromInt64(6103515625L), EInteger.FromInt64(30517578125L),
      EInteger.FromInt64(152587890625L), EInteger.FromInt64(762939453125L),
      EInteger.FromInt64(3814697265625L), EInteger.FromInt64(19073486328125L),
      EInteger.FromInt64(95367431640625L),
      EInteger.FromInt64(476837158203125L), EInteger.FromInt64(2384185791015625L),
      EInteger.FromInt64(11920928955078125L),
      EInteger.FromInt64(59604644775390625L), EInteger.FromInt64(298023223876953125L),
      EInteger.FromInt64(1490116119384765625L), EInteger.FromInt64(7450580596923828125L)
    };

    public static int ShiftLeftOne(int[] arr) {
      {
        int carry = 0;
        for (int i = 0; i < arr.length; ++i) {
          int item = arr[i];
          arr[i] = (int)(arr[i] << 1) | (int)carry;
          carry = ((item >> 31) != 0) ? 1 : 0;
        }
        return carry;
      }
    }

    private static int CountTrailingZeros(int numberValue) {
      if (numberValue == 0) {
        return 32;
      }
      int i = 0;
      {
        if ((numberValue << 16) == 0) {
          numberValue >>= 16;
          i += 16;
        }
        if ((numberValue << 24) == 0) {
          numberValue >>= 8;
          i += 8;
        }
        if ((numberValue << 28) == 0) {
          numberValue >>= 4;
          i += 4;
        }
        if ((numberValue << 30) == 0) {
          numberValue >>= 2;
          i += 2;
        }
        if ((numberValue << 31) == 0) {
          ++i;
        }
      }
      return i;
    }

    public static int BitPrecisionInt(int numberValue) {
      if (numberValue == 0) {
        return 0;
      }
      int i = 32;
      {
        if ((numberValue >> 16) == 0) {
          numberValue <<= 16;
          i -= 8;
        }
        if ((numberValue >> 24) == 0) {
          numberValue <<= 8;
          i -= 8;
        }
        if ((numberValue >> 28) == 0) {
          numberValue <<= 4;
          i -= 4;
        }
        if ((numberValue >> 30) == 0) {
          numberValue <<= 2;
          i -= 2;
        }
        if ((numberValue >> 31) == 0) {
          --i;
        }
      }
      return i;
    }

    public static int ShiftAwayTrailingZerosTwoElements(int[] arr) {
      int a0 = arr[0];
      int a1 = arr[1];
      int tz = CountTrailingZeros(a0);
      if (tz == 0) {
        return 0;
      }
      {
        if (tz < 32) {
          int carry = a1 << (32 - tz);
          arr[0] = (int)((a0 >> tz) & (0x7fffffff >> (tz - 1))) | (int)carry;
          arr[1] = (a1 >> tz) & (0x7fffffff >> (tz - 1));
          return tz;
        }
        tz = CountTrailingZeros(a1);
        if (tz == 32) {
          arr[0] = 0;
        } else if (tz > 0) {
          arr[0] = (a1 >> tz) & (0x7fffffff >> (tz - 1));
        } else {
          arr[0] = a1;
        }
        arr[1] = 0;
        return 32 + tz;
      }
    }

    private static final EInteger valueBigShiftIteration =
      EInteger.FromInt64(1000000);

    public static EInteger ShiftLeft(EInteger val, EInteger bigShift) {
      if (val.signum() == 0) {
        return val;
      }
      while (bigShift.compareTo(valueBigShiftIteration) > 0) {
        val = val.ShiftLeft(1000000);
        bigShift = bigShift.Subtract(valueBigShiftIteration);
      }
      int lastshift = bigShift.AsInt32Checked();
      val = val.ShiftLeft(lastshift);
      return val;
    }

    public static EInteger ShiftLeftInt(EInteger val, int shift) {
      if (val.signum() == 0) {
        return val;
      }
      while (shift > 1000000) {
        val = val.ShiftLeft(1000000);
        shift -= 1000000;
      }
      int lastshift = (int)shift;
      val = val.ShiftLeft(lastshift);
      return val;
    }

    public static boolean HasBitSet(int[] arr, int bit) {
      return (bit >> 5) < arr.length && (arr[bit >> 5] & (1 << (bit & 31))) !=
      0;
    }

    private static final class PowerCache {
      private static final int MaxSize = 64;
      private final EInteger[] outputs;
      private final EInteger[] inputs;
      private final int[] inputsInts;

      public PowerCache() {
        this.outputs = new EInteger[MaxSize];
        this.inputs = new EInteger[MaxSize];
        this.inputsInts = new int[MaxSize];
      }

      private int size;

      public EInteger[] FindCachedPowerOrSmaller(EInteger bi) {
        EInteger[] ret = null;
        EInteger minValue = null;
        synchronized (this.outputs) {
          for (int i = 0; i < this.size; ++i) {
            if (this.inputs[i].compareTo(bi) <= 0 && (minValue == null ||
            this.inputs[i].compareTo(minValue) >= 0)) {
   // System.out.println("Have cached power (" + inputs[i] + "," + bi + ") ");
              ret = new EInteger[2];
              ret[0] = this.inputs[i];
              ret[1] = this.outputs[i];
              minValue = this.inputs[i];
            }
          }
        }
        return ret;
      }

      public EInteger GetCachedPower(EInteger bi) {
        synchronized (this.outputs) {
          for (int i = 0; i < this.size; ++i) {
            if (bi.equals(this.inputs[i])) {
              if (i != 0) {
                EInteger tmp;
                // Move to head of cache if it isn't already
                tmp = this.inputs[i]; this.inputs[i] = this.inputs[0];
                this.inputs[0] = tmp;
                int tmpi = this.inputsInts[i]; this.inputsInts[i] =
                this.inputsInts[0]; this.inputsInts[0] = tmpi;
                tmp = this.outputs[i]; this.outputs[i] = this.outputs[0];
                this.outputs[0] = tmp;
                // Move formerly newest to next newest
                if (i != 1) {
                  tmp = this.inputs[i]; this.inputs[i] = this.inputs[1];
                  this.inputs[1] = tmp;
                  tmpi = this.inputsInts[i]; this.inputsInts[i] =
                  this.inputsInts[1]; this.inputsInts[1] = tmpi;
                  tmp = this.outputs[i]; this.outputs[i] = this.outputs[1];
                  this.outputs[1] = tmp;
                }
              }
              return this.outputs[0];
            }
          }
        }
        return null;
      }

      public EInteger GetCachedPowerInt(int bi) {
        synchronized (this.outputs) {
          for (int i = 0; i < this.size; ++i) {
            if (this.inputsInts[i] >= 0 && this.inputsInts[i] == bi) {
              if (i != 0) {
                EInteger tmp;
                // Move to head of cache if it isn't already
                tmp = this.inputs[i]; this.inputs[i] = this.inputs[0];
                this.inputs[0] = tmp;
                int tmpi = this.inputsInts[i]; this.inputsInts[i] =
                this.inputsInts[0]; this.inputsInts[0] = tmpi;
                tmp = this.outputs[i]; this.outputs[i] = this.outputs[0];
                this.outputs[0] = tmp;
                // Move formerly newest to next newest
                if (i != 1) {
                  tmp = this.inputs[i]; this.inputs[i] = this.inputs[1];
                  this.inputs[1] = tmp;
                  tmpi = this.inputsInts[i]; this.inputsInts[i] =
                  this.inputsInts[1]; this.inputsInts[1] = tmpi;
                  tmp = this.outputs[i]; this.outputs[i] = this.outputs[1];
                  this.outputs[1] = tmp;
                }
              }
              return this.outputs[0];
            }
          }
        }
        return null;
      }

      public void AddPower(EInteger input, EInteger output) {
        synchronized (this.outputs) {
          if (this.size < MaxSize) {
            // Shift newer entries down
            for (int i = this.size; i > 0; --i) {
              this.inputs[i] = this.inputs[i - 1];
              this.inputsInts[i] = this.inputsInts[i - 1];
              this.outputs[i] = this.outputs[i - 1];
            }
            this.inputs[0] = input;
       this.inputsInts[0] = input.canFitInInt() ? input.AsInt32Checked() : -1;
            this.outputs[0] = output;
            ++this.size;
          } else {
            // Shift newer entries down
            for (int i = MaxSize - 1; i > 0; --i) {
              this.inputs[i] = this.inputs[i - 1];
              this.inputsInts[i] = this.inputsInts[i - 1];
              this.outputs[i] = this.outputs[i - 1];
            }
            this.inputs[0] = input;
       this.inputsInts[0] = input.canFitInInt() ? input.AsInt32Checked() : -1;
            this.outputs[0] = output;
          }
        }
      }
    }

    private static final PowerCache powerOfFiveCache = new
    DecimalUtility.PowerCache();

    private static final PowerCache powerOfTenCache = new
      DecimalUtility.PowerCache();

    public static EInteger FindPowerOfFiveFromBig(EInteger diff) {
      int sign = diff.signum();
      if (sign < 0) {
        return EInteger.FromInt64(0);
      }
      if (sign == 0) {
        return EInteger.FromInt64(1);
      }
      FastInteger intcurexp = FastInteger.FromBig(diff);
      if (intcurexp.CompareToInt(54) <= 0) {
        return FindPowerOfFive(intcurexp.AsInt32());
      }
      EInteger mantissa = EInteger.FromInt64(1);
      EInteger bigpow;
      EInteger origdiff = diff;
      bigpow = powerOfFiveCache.GetCachedPower(origdiff);
      if (bigpow != null) {
        return bigpow;
      }
      EInteger[] otherPower =
      powerOfFiveCache.FindCachedPowerOrSmaller(origdiff);
      if (otherPower != null) {
        intcurexp.SubtractBig(otherPower[0]);
        bigpow = otherPower[1];
        mantissa = bigpow;
      } else {
        bigpow = EInteger.FromInt64(0);
      }
      while (intcurexp.signum() > 0) {
        if (intcurexp.CompareToInt(27) <= 0) {
          bigpow = FindPowerOfFive(intcurexp.AsInt32());
          mantissa = mantissa.Multiply(bigpow);
          break;
        }
        if (intcurexp.CompareToInt(9999999) <= 0) {
          bigpow = FindPowerOfFive(1).pow(intcurexp.AsInt32());
          mantissa = mantissa.Multiply(bigpow);
          break;
        }
        if (bigpow.signum() == 0) {
          bigpow = FindPowerOfFive(1).pow(9999999);
        }
        mantissa = mantissa.Multiply(bigpow);
        intcurexp.AddInt(-9999999);
      }
      powerOfFiveCache.AddPower(origdiff, mantissa);
      return mantissa;
    }

    private static final EInteger valueBigInt36 = EInteger.FromInt64(36);

    public static EInteger FindPowerOfTenFromBig(EInteger
    bigintExponent) {
      int sign = bigintExponent.signum();
      if (sign < 0) {
        return EInteger.FromInt64(0);
      }
      if (sign == 0) {
        return EInteger.FromInt64(1);
      }
      if (bigintExponent.compareTo(valueBigInt36) <= 0) {
        return FindPowerOfTen(bigintExponent.AsInt32Checked());
      }
      FastInteger intcurexp = FastInteger.FromBig(bigintExponent);
      EInteger mantissa = EInteger.FromInt64(1);
      EInteger bigpow = EInteger.FromInt64(0);
      while (intcurexp.signum() > 0) {
        if (intcurexp.CompareToInt(18) <= 0) {
          bigpow = FindPowerOfTen(intcurexp.AsInt32());
          mantissa = mantissa.Multiply(bigpow);
          break;
        }
        if (intcurexp.CompareToInt(9999999) <= 0) {
          int val = intcurexp.AsInt32();
          bigpow = FindPowerOfFive(val);
          bigpow = bigpow.ShiftLeft(val);
          mantissa = mantissa.Multiply(bigpow);
          break;
        }
        if (bigpow.signum() == 0) {
          bigpow = FindPowerOfFive(9999999);
          bigpow = bigpow.ShiftLeft(9999999);
        }
        mantissa = mantissa.Multiply(bigpow);
        intcurexp.AddInt(-9999999);
      }
      return mantissa;
    }

    private static final EInteger valueFivePower40 =
    (EInteger.FromInt64(95367431640625L)).Multiply(EInteger.FromInt64(95367431640625L));

    public static EInteger FindPowerOfFive(int precision) {
      if (precision < 0) {
        return EInteger.FromInt64(0);
      }
      if (precision == 0) {
        return EInteger.FromInt64(1);
      }
      EInteger bigpow;
      EInteger ret;
      if (precision <= 27) {
        return valueBigIntPowersOfFive[(int)precision];
      }
      if (precision == 40) {
        return valueFivePower40;
      }
      int startPrecision = precision;
      bigpow = powerOfFiveCache.GetCachedPowerInt(precision);
      if (bigpow != null) {
        return bigpow;
      }
      EInteger origPrecision = EInteger.FromInt64(precision);
      if (precision <= 54) {
        if ((precision & 1) == 0) {
          ret = valueBigIntPowersOfFive[(int)(precision >> 1)];
          ret = ret.Multiply(ret);
          powerOfFiveCache.AddPower(origPrecision, ret);
          return ret;
        }
        ret = valueBigIntPowersOfFive[27];
        bigpow = valueBigIntPowersOfFive[((int)precision) - 27];
        ret = ret.Multiply(bigpow);
        powerOfFiveCache.AddPower(origPrecision, ret);
        return ret;
      }
      if (precision > 40 && precision <= 94) {
        ret = valueFivePower40;
        bigpow = FindPowerOfFive(precision - 40);
        ret = ret.Multiply(bigpow);
        powerOfFiveCache.AddPower(origPrecision, ret);
        return ret;
      }
      EInteger[] otherPower;
      boolean first = true;
      bigpow = EInteger.FromInt64(0);
      while (true) {
        otherPower =
        powerOfFiveCache.FindCachedPowerOrSmaller(EInteger.FromInt64(precision));
        if (otherPower != null) {
          EInteger otherPower0 = otherPower[0];
          EInteger otherPower1 = otherPower[1];
          precision -= otherPower0.AsInt32Checked();
          if (first) {
            bigpow = otherPower[1];
          } else {
            bigpow = bigpow.Multiply(otherPower1);
          }
          first = false;
        } else {
          break;
        }
      }
      ret = !first ? bigpow : EInteger.FromInt64(1);
      while (precision > 0) {
        if (precision <= 27) {
          bigpow = valueBigIntPowersOfFive[(int)precision];
          if (first) {
            ret = bigpow;
          } else {
            ret = ret.Multiply(bigpow);
          }
          first = false;
          break;
        }
        if (precision <= 9999999) {
          // System.out.println("calcing pow for "+precision);
          bigpow = valueBigIntPowersOfFive[1].pow(precision);
          if (precision != startPrecision) {
            EInteger bigprec = EInteger.FromInt64(precision);
            powerOfFiveCache.AddPower(bigprec, bigpow);
          }
          if (first) {
            ret = bigpow;
          } else {
            ret = ret.Multiply(bigpow);
          }
          first = false;
          break;
        }
        if (bigpow.signum() == 0) {
          bigpow = FindPowerOfFive(9999999);
        }
        if (first) {
          ret = bigpow;
        } else {
          ret = ret.Multiply(bigpow);
        }
        first = false;
        precision -= 9999999;
      }
      powerOfFiveCache.AddPower(origPrecision, ret);
      return ret;
    }

    public static EInteger FindPowerOfTen(int precision) {
      if (precision < 0) {
        return EInteger.FromInt64(0);
      }
      if (precision == 0) {
        return EInteger.FromInt64(1);
      }
      EInteger bigpow;
      EInteger ret;
      if (precision <= 18) {
        return valueBigIntPowersOfTen[(int)precision];
      }
      int startPrecision = precision;
      bigpow = powerOfTenCache.GetCachedPowerInt(precision);
      if (bigpow != null) {
        return bigpow;
      }
      EInteger origPrecision = EInteger.FromInt64(precision);
      if (precision <= 27) {
        int prec = (int)precision;
        ret = valueBigIntPowersOfFive[prec];
        ret = ret.ShiftLeft(prec);
        powerOfTenCache.AddPower(origPrecision, ret);
        return ret;
      }
      if (precision <= 36) {
        if ((precision & 1) == 0) {
          ret = valueBigIntPowersOfTen[(int)(precision >> 1)];
          ret = ret.Multiply(ret);
          powerOfTenCache.AddPower(origPrecision, ret);
          return ret;
        }
        ret = valueBigIntPowersOfTen[18];
        bigpow = valueBigIntPowersOfTen[((int)precision) - 18];
        ret = ret.Multiply(bigpow);
        powerOfTenCache.AddPower(origPrecision, ret);
        return ret;
      }
      EInteger[] otherPower;
      boolean first = true;
      bigpow = EInteger.FromInt64(0);
      while (true) {
        otherPower =
        powerOfTenCache.FindCachedPowerOrSmaller(EInteger.FromInt64(precision));
        if (otherPower != null) {
          EInteger otherPower0 = otherPower[0];
          EInteger otherPower1 = otherPower[1];
          precision -= otherPower0.AsInt32Checked();
          if (first) {
            bigpow = otherPower[1];
          } else {
            bigpow = bigpow.Multiply(otherPower1);
          }
          first = false;
        } else {
          break;
        }
      }
      ret = !first ? bigpow : EInteger.FromInt64(1);
      while (precision > 0) {
        if (precision <= 18) {
          bigpow = valueBigIntPowersOfTen[(int)precision];
          if (first) {
            ret = bigpow;
          } else {
            ret = ret.Multiply(bigpow);
          }
          first = false;
          break;
        }
        if (precision <= 9999999) {
          // System.out.println("calcing pow for "+precision);
          bigpow = FindPowerOfFive(precision);
          bigpow = bigpow.ShiftLeft(precision);
          if (precision != startPrecision) {
            EInteger bigprec = EInteger.FromInt64(precision);
            powerOfTenCache.AddPower(bigprec, bigpow);
          }
          if (first) {
            ret = bigpow;
          } else {
            ret = ret.Multiply(bigpow);
          }
          first = false;
          break;
        }
        if (bigpow.signum() == 0) {
          bigpow = FindPowerOfTen(9999999);
        }
        if (first) {
          ret = bigpow;
        } else {
          ret = ret.Multiply(bigpow);
        }
        first = false;
        precision -= 9999999;
      }
      powerOfTenCache.AddPower(origPrecision, ret);
      return ret;
    }

    public static EInteger ReduceTrailingZeros(
      EInteger bigmant,
      FastInteger exponentMutable,
      int radix,
      FastInteger digits,
      FastInteger precision,
      FastInteger idealExp) {
      if (bigmant.signum() == 0) {
        exponentMutable.SetInt(0);
        return bigmant;
      }
      EInteger bigradix = EInteger.FromInt64(radix);
      int bitToTest = 0;
      FastInteger bitsToShift = new FastInteger(0);
      while (bigmant.signum() != 0) {
        if (precision != null && digits.compareTo(precision) == 0) {
          break;
        }
        if (idealExp != null && exponentMutable.compareTo(idealExp) == 0) {
          break;
        }
        if (radix == 2) {
          if (bitToTest < Integer.MAX_VALUE) {
            if (bigmant.testBit(bitToTest)) {
              break;
            }
            ++bitToTest;
            bitsToShift.Increment();
          } else {
            if (!bigmant.isEven()) {
              break;
            }
            bigmant = bigmant.ShiftRight(1);
          }
        } else {
          EInteger bigrem;
          EInteger bigquo;
{
EInteger[] divrem=(bigmant).DivRem(bigradix);
bigquo = divrem[0];
bigrem = divrem[1]; }
          if (bigrem.signum() != 0) {
            break;
          }
          bigmant = bigquo;
        }
        exponentMutable.Increment();
        if (digits != null) {
          digits.Decrement();
        }
      }
      if (radix == 2 && !bitsToShift.isValueZero()) {
        while (bitsToShift.CompareToInt(1000000) > 0) {
          bigmant = bigmant.ShiftRight(1000000);
          bitsToShift.SubtractInt(1000000);
        }
        int tmpshift = bitsToShift.AsInt32();
        bigmant = bigmant.ShiftRight(tmpshift);
      }
      return bigmant;
    }
  }
