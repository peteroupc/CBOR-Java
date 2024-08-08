package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under the Unlicense: https://unlicense.org/

 */

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

    public EDecimal AsEDecimal(Object obj) {
      return EDecimal.FromEInteger((EInteger)obj);
    }

    public EFloat AsEFloat(Object obj) {
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
      if (!bi.CanFitInInt64()) {
 throw new ArithmeticException("This" +
"\u0020object's value is out of range");
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
      return bi.CanFitInInt64();
    }

    public boolean CanFitInUInt64(Object obj) {
      EInteger bi = (EInteger)obj;
      return bi.signum() >= 0 && bi.GetUnsignedBitLengthAsInt64() <= 64;
    }

    public boolean CanTruncatedIntFitInInt64(Object obj) {
      return this.CanFitInInt64(obj);
    }

    public boolean CanTruncatedIntFitInUInt64(Object obj) {
      return this.CanFitInUInt64(obj);
    }

    public boolean CanTruncatedIntFitInInt32(Object obj) {
      return this.CanFitInInt32(obj);
    }

    public boolean IsNumberZero(Object obj) {
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
      bigobj = bigobj.Negate();
      return bigobj;
    }

    public Object Abs(Object obj) {
      return ((EInteger)obj).Abs();
    }

    public ERational AsERational(Object obj) {
      return ERational.FromEInteger((EInteger)obj);
    }

    public boolean IsNegative(Object obj) {
      return ((EInteger)obj).signum() < 0;
    }
  }
