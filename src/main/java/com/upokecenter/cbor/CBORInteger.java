package com.upokecenter.cbor;
/*
Written in 2014 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import com.upokecenter.util.*; import com.upokecenter.numbers.*;

  class CBORInteger implements ICBORNumber
  {
    public boolean IsPositiveInfinity(Object obj) {
      return false;
    }

    public boolean IsInfinity(Object obj) {
      return false;
    }

    public boolean IsNegativeInfinity(Object obj) {
      return false;
    }

    public boolean IsNaN(Object obj) {
      return false;
    }

    public double AsDouble(Object obj) {
      return ((Long)obj).doubleValue();
    }

    public EDecimal AsExtendedDecimal(Object obj) {
      return EDecimal.FromInt64((((Long)obj).longValue()));
    }

    public EFloat AsExtendedFloat(Object obj) {
      return EFloat.FromInt64((((Long)obj).longValue()));
    }

    public float AsSingle(Object obj) {
      return ((Long)obj).floatValue();
    }

    public EInteger AsBigInteger(Object obj) {
      return EInteger.FromInt64((((Long)obj).longValue()));
    }

    public long AsInt64(Object obj) {
      return (((Long)obj).longValue());
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

    public Object Negate(Object obj) {
      return (((((Long)obj).longValue())) == Long.MIN_VALUE) ? (EInteger.FromInt32(1).ShiftLeft(63)) :
      (-((((Long)obj).longValue())));
    }

    public boolean CanTruncatedIntFitInInt64(Object obj) {
      return true;
    }

    public boolean CanTruncatedIntFitInInt32(Object obj) {
      long val = (((Long)obj).longValue());
      return val >= Integer.MIN_VALUE && val <= Integer.MAX_VALUE;
    }

    public boolean IsZero(Object obj) {
      return ((((Long)obj).longValue())) == 0;
    }

    public int Sign(Object obj) {
      long val = (((Long)obj).longValue());
      return (val == 0) ? 0 : ((val < 0) ? -1 : 1);
    }

    public boolean IsIntegral(Object obj) {
      return true;
    }

    public int AsInt32(Object obj, int minValue, int maxValue) {
      long val = (((Long)obj).longValue());
      if (val >= minValue && val <= maxValue) {
        return (int)val;
      }
      throw new ArithmeticException("This Object's value is out of range");
    }

    public Object Abs(Object obj) {
      long val = (((Long)obj).longValue());
      return (val == Integer.MIN_VALUE) ? (EInteger.FromInt32(1).ShiftLeft(63)) : ((val < 0) ?
      -val : obj);
    }

public ERational AsExtendedRational(Object obj) {
      return ERational.FromInt64((((Long)obj).longValue()));
    }
  }
