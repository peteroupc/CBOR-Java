package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  class CBORDoubleBits implements ICBORNumber {
    public boolean IsPositiveInfinity(Object obj) {
       return ((((Long)obj).longValue())) == (0x7ffL << 52);
    }

    public boolean IsInfinity(Object obj) {
       return (((((Long)obj).longValue())) & ~(1L << 63)) == (0x7ffL << 52);
    }

    public boolean IsNegativeInfinity(Object obj) {
       return ((((Long)obj).longValue())) == (0xfffL << 52);
    }

    public boolean IsNaN(Object obj) {
       return CBORUtilities.DoubleBitsNaN((((Long)obj).longValue()));
    }

    public double AsDouble(Object obj) {
       return CBORUtilities.Int64BitsToDouble((((Long)obj).longValue()));
    }

    public EDecimal AsEDecimal(Object obj) {
      return EDecimal.FromDoubleBits((((Long)obj).longValue()));
    }

    public EFloat AsEFloat(Object obj) {
      return EFloat.FromDoubleBits((((Long)obj).longValue()));
    }

    public float AsSingle(Object obj) {
       return CBORUtilities.Int32BitsToSingle(
         CBORUtilities.DoubleToRoundedSinglePrecision((((Long)obj).longValue())));
    }

    public EInteger AsEInteger(Object obj) {
      return CBORUtilities.EIntegerFromDoubleBits((((Long)obj).longValue()));
    }

    public long AsInt64(Object obj) {
      if (this.IsNaN(obj) || this.IsInfinity(obj)) {
        throw new ArithmeticException("This Object's value is out of range");
      }
      long b = DoubleBitsRoundDown((((Long)obj).longValue()));
      boolean neg = (b >> 63) != 0;
      b &= ~(1L << 63);
      if (b == 0) {
        return 0;
      }
      if (neg && b == (0x43eL << 52)) {
        return Long.MIN_VALUE;
      }
      if ((b >> 52) >= 0x43e) {
        throw new ArithmeticException("This Object's value is out of range");
      }
      int exp = (int)(b >> 52);
      long mant = b & ((1L << 52) - 1);
      mant |= 1L << 52;
      int shift = 52 - (exp - 0x3ff);
      if (shift < 0) {
        mant <<= -shift;
      } else {
        mant >>= shift;
      }
      if (neg) {
        mant = -mant;
      }
      return mant;
    }

    public boolean CanFitInSingle(Object obj) {
      return this.IsNaN(obj) ||
CBORUtilities.DoubleRetainsSameValueInSingle((((Long)obj).longValue()));
    }

    public boolean CanFitInDouble(Object obj) {
      return true;
    }

    public boolean CanFitInInt32(Object obj) {
      return this.IsIntegral(obj) && this.CanTruncatedIntFitInInt32(obj);
    }

    public boolean CanFitInInt64(Object obj) {
      return this.IsIntegral(obj) && this.CanTruncatedIntFitInInt64(obj);
    }

    public boolean CanFitInUInt64(Object obj) {
      return this.IsIntegral(obj) && this.CanTruncatedIntFitInUInt64(obj);
    }

    private static long DoubleBitsRoundDown(long bits) {
      long origbits = bits;
      bits &= ~(1L << 63);
      if (bits == 0) {
        return origbits;
      }
      // Infinity and NaN
      if (bits >= ((long)(0x7ffL << 52))) {
        return origbits;
      }
      // Beyond non-integer range
      if ((bits >> 52) >= 0x433) {
        return origbits;
      }
      // Less than 1
      if ((bits >> 52) <= 0x3fe) {
        return (origbits >> 63) != 0 ? (1L << 63) : 0;
      }
      int exp = (int)(bits >> 52);
      long mant = bits & ((1L << 52) - 1);
      int shift = 52 - (exp - 0x3ff);
      return ((mant >> shift) << shift) | (origbits & (0xfffL << 52));
    }

    public boolean CanTruncatedIntFitInInt64(Object obj) {
      if (this.IsNaN(obj) || this.IsInfinity(obj)) {
        return false;
      }
      long b = DoubleBitsRoundDown((((Long)obj).longValue()));
      boolean neg = (b >> 63) != 0;
      b &= ~(1L << 63);
      return (neg && b == (0x43eL << 52)) || ((b >> 52) < 0x43e);
    }

    public boolean CanTruncatedIntFitInUInt64(Object obj) {
      if (this.IsNaN(obj) || this.IsInfinity(obj)) {
        return false;
      }
      long b = DoubleBitsRoundDown((((Long)obj).longValue()));
      boolean neg = (b >> 63) != 0;
      b &= ~(1L << 63);
      return (neg && b == 0) || (!neg && (b >> 52) < 0x43f);
    }

    public boolean CanTruncatedIntFitInInt32(Object obj) {
      if (this.IsNaN(obj) || this.IsInfinity(obj)) {
        return false;
      }
      long b = DoubleBitsRoundDown((((Long)obj).longValue()));
      boolean neg = (b >> 63) != 0;
      b &= ~(1L << 63);
      return (neg && b == (0x41eL << 52)) || ((b >> 52) < 0x41e);
    }

    public int AsInt32(Object obj, int minValue, int maxValue) {
      if (this.IsNaN(obj) || this.IsInfinity(obj)) {
        throw new ArithmeticException("This Object's value is out of range");
      }
      long b = DoubleBitsRoundDown((((Long)obj).longValue()));
      boolean neg = (b >> 63) != 0;
      b &= ~(1L << 63);
      if (b == 0) {
        return 0;
      }
      // Beyond non-integer range (thus beyond int32 range)
      if ((b >> 52) >= 0x433) {
        throw new ArithmeticException("This Object's value is out of range");
      }
      int exp = (int)(b >> 52);
      long mant = b & ((1L << 52) - 1);
      mant |= 1L << 52;
      int shift = 52 - (exp - 0x3ff);
      mant >>= shift;
      if (neg) {
        mant = -mant;
      }
      if (mant < minValue || mant > maxValue) {
        throw new ArithmeticException("This Object's value is out of range");
      }
      return (int)mant;
    }

    public boolean IsNumberZero(Object obj) {
       return (((((Long)obj).longValue())) & ~(1L << 63)) == 0;
    }

    public int Sign(Object obj) {
return this.IsNaN(obj) ? (-2) : ((((((Long)obj).longValue())) >> 63) != 0 ? -1 : 1);
    }

    public boolean IsIntegral(Object obj) {
      return CBORUtilities.IsIntegerValue((((Long)obj).longValue()));
    }

    public Object Negate(Object obj) {
       return ((((Long)obj).longValue())) ^ (1L << 63);
    }

    public Object Abs(Object obj) {
       return ((((Long)obj).longValue())) & ~(1L << 63);
    }

    public ERational AsERational(Object obj) {
      return ERational.FromDoubleBits((((Long)obj).longValue()));
    }

    public boolean IsNegative(Object obj) {
       return (((((Long)obj).longValue())) >> 63) != 0;
    }
  }
