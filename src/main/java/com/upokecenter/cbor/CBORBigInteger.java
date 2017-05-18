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

  class CBOREInteger implements ICBORNumber
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
      return EFloat.FromEInteger((EInteger)obj).ToDouble();
    }

    public EDecimal AsExtendedDecimal(Object obj) {
      return EDecimal.FromEInteger((EInteger)obj);
    }

    public EFloat AsExtendedFloat(Object obj) {
      return EFloat.FromEInteger((EInteger)obj);
    }

    public float AsSingle(Object obj) {
      return EFloat.FromEInteger((EInteger)obj).ToSingle();
    }

    public EInteger AsEInteger(Object obj) {
      return (EInteger)obj;
    }

    public long AsInt64(Object obj) {
      EInteger bi = (EInteger)obj;
      if (bi.compareTo(CBORObject.Int64MaxValue) > 0 ||
          bi.compareTo(CBORObject.Int64MinValue) < 0) {
        throw new ArithmeticException("This Object's value is out of range");
      }
      return bi.ToInt64Checked();
    }

    public boolean CanFitInSingle(Object obj) {
      EInteger bigintItem = (EInteger)obj;
      EFloat ef = EFloat.FromEInteger(bigintItem);
      EFloat ef2 = EFloat.FromSingle(ef.ToSingle());
      return ef.compareTo(ef2) == 0;
    }

    public boolean CanFitInDouble(Object obj) {
      EInteger bigintItem = (EInteger)obj;
      EFloat ef = EFloat.FromEInteger(bigintItem);
      EFloat ef2 = EFloat.FromDouble(ef.ToDouble());
      return ef.compareTo(ef2) == 0;
    }

    public boolean CanFitInInt32(Object obj) {
      EInteger bi = (EInteger)obj;
      return bi.CanFitInInt32();
    }

    public boolean CanFitInInt64(Object obj) {
      EInteger bi = (EInteger)obj;
      return bi.GetSignedBitLength() <= 63;
    }

    public boolean CanTruncatedIntFitInInt64(Object obj) {
      return this.CanFitInInt64(obj);
    }

    public boolean CanTruncatedIntFitInInt32(Object obj) {
      return this.CanFitInInt32(obj);
    }

    public boolean IsZero(Object obj) {
      return ((EInteger)obj).isZero();
    }

    public int Sign(Object obj) {
      return ((EInteger)obj).signum();
    }

    public boolean IsIntegral(Object obj) {
      return true;
    }

    public int AsInt32(Object obj, int minValue, int maxValue) {
      EInteger bi = (EInteger)obj;
      if (bi.CanFitInInt32()) {
        int ret = bi.ToInt32Checked();
        if (ret >= minValue && ret <= maxValue) {
          return ret;
        }
      }
      throw new ArithmeticException("This Object's value is out of range");
    }

    public Object Negate(Object obj) {
      EInteger bigobj = (EInteger)obj;
      bigobj=(bigobj).Negate();
      return bigobj;
    }

    public Object Abs(Object obj) {
      return ((EInteger)obj).Abs();
    }

    public ERational AsExtendedRational(Object obj) {
      return ERational.FromEInteger((EInteger)obj);
    }

    public boolean IsNegative(Object obj) {
      return ((EInteger)obj).signum() < 0;
    }
  }
