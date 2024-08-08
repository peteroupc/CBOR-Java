package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under the Unlicense: https://unlicense.org/

 */

import com.upokecenter.numbers.*;

  class CBORInteger implements ICBORNumber
  {
    public Object Abs(Object obj) {
      long val = (((Long)obj).longValue());
      return (val == Integer.MIN_VALUE) ? (EInteger.FromInt32(1).ShiftLeft(63)) : ((val < 0) ?
          -val : obj);
    }

    public EInteger AsEInteger(Object obj) {
      return EInteger.FromInt64((((Long)obj).longValue()));
    }

    public double AsDouble(Object obj) {
      return ((Long)obj).longValue();
    }

    public EDecimal AsEDecimal(Object obj) {
      return EDecimal.FromInt64((((Long)obj).longValue()));
    }

    public EFloat AsEFloat(Object obj) {
      return EFloat.FromInt64((((Long)obj).longValue()));
    }

    public ERational AsERational(Object obj) {
      return ERational.FromInt64((((Long)obj).longValue()));
    }

    public int AsInt32(Object obj, int minValue, int maxValue) {
      long val = (((Long)obj).longValue());
      if (!(val >= minValue && val <= maxValue)) {
 throw new ArithmeticException("This Object's value is out of range");
}
 return (int)val;
    }

    public long AsInt64(Object obj) {
      return ((Long)obj).longValue();
    }

    public float AsSingle(Object obj) {
      return ((Long)obj).longValue();
    }

    public boolean CanFitInDouble(Object obj) {
      long intItem = (((Long)obj).longValue());
      if (intItem == Long.MIN_VALUE) {
        return true;
      }
      intItem = (intItem < 0) ? -intItem : intItem;
      while (intItem >= (1L << 53) && (intItem & 1) == 0) {
        intItem >>= 1;
      }
      return intItem < (1L << 53);
    }

    public boolean CanFitInInt32(Object obj) {
      long val = (((Long)obj).longValue());
      return val >= Integer.MIN_VALUE && val <= Integer.MAX_VALUE;
    }

    public boolean CanFitInInt64(Object obj) {
      return true;
    }

    public boolean CanFitInSingle(Object obj) {
      long intItem = (((Long)obj).longValue());
      if (intItem == Long.MIN_VALUE) {
        return true;
      }
      intItem = (intItem < 0) ? -intItem : intItem;
      while (intItem >= (1L << 24) && (intItem & 1) == 0) {
        intItem >>= 1;
      }
      return intItem < (1L << 24);
    }

    public boolean CanTruncatedIntFitInInt32(Object obj) {
      long val = (((Long)obj).longValue());
      return val >= Integer.MIN_VALUE && val <= Integer.MAX_VALUE;
    }

    public boolean CanTruncatedIntFitInUInt64(Object obj) {
      long val = (((Long)obj).longValue());
      return val >= 0;
    }

    public boolean CanFitInUInt64(Object obj) {
      long val = (((Long)obj).longValue());
      return val >= 0;
    }

    public boolean CanTruncatedIntFitInInt64(Object obj) {
      return true;
    }

    public boolean IsInfinity(Object obj) {
      return false;
    }

    public boolean IsIntegral(Object obj) {
      return true;
    }

    public boolean IsNaN(Object obj) {
      return false;
    }

    public boolean IsNegative(Object obj) {
      return ((((Long)obj).longValue())) < 0;
    }

    public boolean IsNegativeInfinity(Object obj) {
      return false;
    }

    public boolean IsPositiveInfinity(Object obj) {
      return false;
    }

    public boolean IsNumberZero(Object obj) {
      return ((((Long)obj).longValue())) == 0;
    }

    public Object Negate(Object obj) {
      return (((((Long)obj).longValue())) == Long.MIN_VALUE) ? (EInteger.FromInt32(1).ShiftLeft(63)) :
(-(((Long)obj).longValue()));
    }

    public int Sign(Object obj) {
      long val = (((Long)obj).longValue());
      return (val == 0) ? 0 : ((val < 0) ? -1 : 1);
    }
  }
