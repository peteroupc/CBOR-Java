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

  class CBORExtendedRational implements ICBORNumber {
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

    public EDecimal AsEDecimal(Object obj) {
      ERational er = (ERational)obj;
      return

        er.ToEDecimalExactIfPossible(
          EContext.Decimal128.WithUnlimitedExponents());
    }

    public EFloat AsEFloat(Object obj) {
      ERational er = (ERational)obj;
      return

        er.ToEFloatExactIfPossible(
          EContext.Binary128.WithUnlimitedExponents());
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
        if (bi.CanFitInInt64()) {
          return bi.ToInt64Checked();
        }
      }
      throw new ArithmeticException("This Object's value is out of range");
    }

    public boolean CanFitInSingle(Object obj) {
      ERational ef = (ERational)obj;
      return (!ef.isFinite()) || (ef.compareTo(ERational.FromSingle(
            ef.ToSingle())) == 0);
    }

    public boolean CanFitInDouble(Object obj) {
      ERational ef = (ERational)obj;
      return (!ef.isFinite()) || (ef.compareTo(ERational.FromDouble(
            ef.ToDouble())) == 0);
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

    public boolean CanTruncatedIntFitInInt64(Object obj) {
      ERational ef = (ERational)obj;
      if (!ef.isFinite()) {
        return false;
      }
      EInteger bi = ef.ToEInteger();
      return bi.CanFitInInt64();
    }

    public boolean CanTruncatedIntFitInInt32(Object obj) {
      ERational ef = (ERational)obj;
      if (!ef.isFinite()) {
        return false;
      }
      EInteger bi = ef.ToEInteger();
      return bi.CanFitInInt32();
    }

    public boolean CanTruncatedIntFitInUInt64(Object obj) {
      ERational ef = (ERational)obj;
      if (!ef.isFinite()) {
        return false;
      }
      EInteger bi = ef.ToEInteger();
      return bi.signum() >= 0 && bi.GetUnsignedBitLengthAsInt64() <= 64;
    }

    public boolean IsNumberZero(Object obj) {
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

    public ERational AsERational(Object obj) {
      return (ERational)obj;
    }

    public boolean IsNegative(Object obj) {
      return ((ERational)obj).isNegative();
    }
  }
