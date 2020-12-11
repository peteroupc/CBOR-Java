package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  class CBORExtendedFloat implements ICBORNumber {
    public boolean IsPositiveInfinity(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.IsPositiveInfinity();
    }

    public boolean IsInfinity(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.IsInfinity();
    }

    public boolean IsNegativeInfinity(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.IsNegativeInfinity();
    }

    public boolean IsNaN(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.IsNaN();
    }

    public double AsDouble(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.ToDouble();
    }

    public EDecimal AsEDecimal(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.ToEDecimal();
    }

    public EFloat AsEFloat(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef;
    }

    public float AsSingle(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.ToSingle();
    }

    public EInteger AsEInteger(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.ToEInteger();
    }

    public long AsInt64(Object obj) {
      EFloat ef = (EFloat)obj;
      if (this.CanTruncatedIntFitInInt64(obj)) {
        EInteger bi = ef.ToEInteger();
        return bi.ToInt64Checked();
      }
      throw new ArithmeticException("This Object's value is out of range");
    }

    public boolean CanFitInSingle(Object obj) {
      EFloat ef = (EFloat)obj;
      return (!ef.isFinite()) || (ef.compareTo(EFloat.FromSingle(
            ef.ToSingle())) == 0);
    }

    public boolean CanFitInDouble(Object obj) {
      EFloat ef = (EFloat)obj;
      return (!ef.isFinite()) || (ef.compareTo(EFloat.FromDouble(
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
      EFloat ef = (EFloat)obj;
      if (!ef.isFinite()) {
        return false;
      }
      if (ef.isZero()) {
        return true;
      }
      if (ef.getExponent().compareTo(EInteger.FromInt64(65)) >= 0) {
        return false;
      }
      EInteger bi = ef.ToEInteger();
      return bi.CanFitInInt64();
    }

    public boolean CanTruncatedIntFitInUInt64(Object obj) {
      EFloat ef = (EFloat)obj;
      if (!ef.isFinite()) {
        return false;
      }
      if (ef.isZero()) {
        return true;
      }
      if (ef.getExponent().compareTo(EInteger.FromInt64(65)) >= 0) {
        return false;
      }
      EInteger bi = ef.ToEInteger();
      return bi.signum() >= 0 && bi.GetUnsignedBitLengthAsInt64() <= 64;
    }

    public boolean CanTruncatedIntFitInInt32(Object obj) {
      EFloat ef = (EFloat)obj;
      if (!ef.isFinite()) {
        return false;
      }
      if (ef.isZero()) {
        return true;
      }
      if (ef.getExponent().compareTo(EInteger.FromInt64(33)) >= 0) {
        return false;
      }
      EInteger bi = ef.ToEInteger();
      return bi.CanFitInInt32();
    }

    public boolean IsNumberZero(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.isZero();
    }

    public int Sign(Object obj) {
      EFloat ef = (EFloat)obj;
      return ef.IsNaN() ? 2 : ef.signum();
    }

    public boolean IsIntegral(Object obj) {
      EFloat ef = (EFloat)obj;
      if (!ef.isFinite()) {
        return false;
      }
      if (ef.getExponent().signum() >= 0) {
        return true;
      }
      EFloat ef2 = EFloat.FromEInteger(ef.ToEInteger());
      return ef2.compareTo(ef) == 0;
    }

    public int AsInt32(Object obj, int minValue, int maxValue) {
      EFloat ef = (EFloat)obj;
      if (this.CanTruncatedIntFitInInt32(obj)) {
        EInteger bi = ef.ToEInteger();
        int ret = bi.ToInt32Checked();
        if (ret >= minValue && ret <= maxValue) {
          return ret;
        }
      }
      throw new ArithmeticException("This Object's value is out of range");
    }

    public Object Negate(Object obj) {
      EFloat ed = (EFloat)obj;
      return ed.Negate();
    }

    public Object Abs(Object obj) {
      EFloat ed = (EFloat)obj;
      return ed.Abs();
    }

    public ERational AsERational(Object obj) {
      return ERational.FromEFloat((EFloat)obj);
    }

    public boolean IsNegative(Object obj) {
      return ((EFloat)obj).isNegative();
    }
  }
