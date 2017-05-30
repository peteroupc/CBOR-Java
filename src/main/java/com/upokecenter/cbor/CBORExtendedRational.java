package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  class CBORExtendedRational implements ICBORNumber
  {
    public boolean IsPositiveInfinity(Object obj) {
      return ((ERational)obj).IsPositiveInfinity();
    }

    public boolean IsInfinity(Object obj) {
      return ((ERational)obj).IsInfinity();
    }

    public boolean IsNegativeInfinity(Object obj) {
      return ((ERational)obj).IsNegativeInfinity();
    }

    public boolean IsNaN(Object obj) {
      return ((ERational)obj).IsNaN();
    }

    public double AsDouble(Object obj) {
      ERational er = (ERational)obj;
      return er.ToDouble();
    }

    public EDecimal AsExtendedDecimal(Object obj) {
      ERational er = (ERational)obj;
      return

  er.ToEDecimalExactIfPossible(EContext.Decimal128.WithUnlimitedExponents());
    }

    public EFloat AsExtendedFloat(Object obj) {
      ERational er = (ERational)obj;
      return

  er.ToEFloatExactIfPossible(EContext.Binary128.WithUnlimitedExponents());
    }

    public float AsSingle(Object obj) {
      ERational er = (ERational)obj;
      return er.ToSingle();
    }

    public EInteger AsEInteger(Object obj) {
      ERational er = (ERational)obj;
      return er.ToEInteger();
    }

    public long AsInt64(Object obj) {
      ERational ef = (ERational)obj;
      if (ef.isFinite()) {
        EInteger bi = ef.ToEInteger();
        if (bi.GetSignedBitLength() <= 63) {
          return bi.ToInt64Checked();
        }
      }
      throw new ArithmeticException("This Object's value is out of range");
    }

    public boolean CanFitInSingle(Object obj) {
      ERational ef = (ERational)obj;
      return (!ef.isFinite()) ||
      (ef.compareTo(ERational.FromSingle(ef.ToSingle())) == 0);
    }

    public boolean CanFitInDouble(Object obj) {
      ERational ef = (ERational)obj;
      return (!ef.isFinite()) ||
      (ef.compareTo(ERational.FromDouble(ef.ToDouble())) == 0);
    }

    public boolean CanFitInInt32(Object obj) {
      return this.IsIntegral(obj) && this.CanTruncatedIntFitInInt32(obj);
    }

    public boolean CanFitInInt64(Object obj) {
      return this.IsIntegral(obj) && this.CanTruncatedIntFitInInt64(obj);
    }

    public boolean CanTruncatedIntFitInInt64(Object obj) {
      ERational ef = (ERational)obj;
      if (!ef.isFinite()) {
        return false;
      }
      EInteger bi = ef.ToEInteger();
      return bi.GetSignedBitLength() <= 63;
    }

    public boolean CanTruncatedIntFitInInt32(Object obj) {
      ERational ef = (ERational)obj;
      if (!ef.isFinite()) {
        return false;
      }
      EInteger bi = ef.ToEInteger();
      return bi.CanFitInInt32();
    }

    public boolean IsZero(Object obj) {
      ERational ef = (ERational)obj;
      return ef.isZero();
    }

    public int Sign(Object obj) {
      ERational ef = (ERational)obj;
      return ef.signum();
    }

    public boolean IsIntegral(Object obj) {
      ERational ef = (ERational)obj;
      if (!ef.isFinite()) {
        return false;
      }
      if (ef.getDenominator().equals(EInteger.FromInt32(1))) {
        return true;
      }
      // A rational number is integral if the remainder
      // of the numerator divided by the denominator is 0
      EInteger denom = ef.getDenominator();
      EInteger rem = ef.getNumerator().Remainder(denom);
      return rem.isZero();
    }

    public int AsInt32(Object obj, int minValue, int maxValue) {
      ERational ef = (ERational)obj;
      if (ef.isFinite()) {
        EInteger bi = ef.ToEInteger();
        if (bi.CanFitInInt32()) {
          int ret = bi.ToInt32Checked();
          if (ret >= minValue && ret <= maxValue) {
            return ret;
          }
        }
      }
      throw new ArithmeticException("This Object's value is out of range");
    }

    public Object Negate(Object obj) {
      ERational ed = (ERational)obj;
      return ed.Negate();
    }

    public Object Abs(Object obj) {
      ERational ed = (ERational)obj;
      return ed.Abs();
    }

    public ERational AsExtendedRational(Object obj) {
      return (ERational)obj;
    }

    public boolean IsNegative(Object obj) {
      return ((ERational)obj).isNegative();
    }
  }
