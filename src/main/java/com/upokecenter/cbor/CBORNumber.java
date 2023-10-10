package com.upokecenter.cbor;

import com.upokecenter.numbers.*;

  public final class CBORNumber implements Comparable<CBORNumber> {
    public enum NumberKind {
      Integer,

      Double,

      EInteger,

      EDecimal,

      EFloat,

      ERational,
    }

    private static final ICBORNumber[] NumberInterfaces = {
      new CBORInteger(),
      new CBORDoubleBits(),
      new CBOREInteger(),
      new CBORExtendedDecimal(),
      new CBORExtendedFloat(),
      new CBORExtendedRational(),
    };
    private final Object value;
    CBORNumber(NumberKind kind, Object value) {
      this.propVarkind = kind;
      this.value = value;
    }

    ICBORNumber GetNumberInterface() {
      return GetNumberInterface(this.getKind());
    }

    static ICBORNumber GetNumberInterface(CBORObject obj) {
      CBORNumber num = CBORNumber.FromCBORObject(obj);
      return (num == null) ? null : num.GetNumberInterface();
    }

    Object GetValue() {
      return this.value;
    }

    static ICBORNumber GetNumberInterface(NumberKind kind) {
      switch (kind) {
        case Integer:
          return NumberInterfaces[0];
        case Double:
          return NumberInterfaces[1];
        case EInteger:
          return NumberInterfaces[2];
        case EDecimal:
          return NumberInterfaces[3];
        case EFloat:
          return NumberInterfaces[4];
        case ERational:
          return NumberInterfaces[5];
        default:
          throw new IllegalStateException();
      }
    }

    public CBORObject ToCBORObject() {
      return CBORObject.FromObject(this.value);
    }

    public final int signum() { return this.IsNaN() ? (this.IsNegative() ? -1 : 1) :
          this.GetNumberInterface().Sign(this.value); }

    static boolean IsNumber(CBORObject o) {
      if (IsUntaggedInteger(o)) {
        return true;
      } else {
        boolean isByteString = o.getType() == CBORType.ByteString;
        boolean isFloatingPoint = o.getType() == CBORType.FloatingPoint;
        return (!o.isTagged() && isFloatingPoint) || (
                o.HasOneTag(2) || o.HasOneTag(3) ?
                isByteString : (o.HasOneTag(4) ||
                o.HasOneTag(5) || o.HasOneTag(264) ||
                o.HasOneTag(265) || o.HasOneTag(268) ||
                o.HasOneTag(269)) ? CheckBigFracToNumber(o,
                o.getMostOuterTag().ToInt32Checked()) : ((o.HasOneTag(30) ||
                o.HasOneTag(270)) && CheckRationalToNumber(o,
                                            o.getMostOuterTag().ToInt32Checked())));
      }
    }

    public static CBORNumber FromCBORObject(CBORObject o) {
      if (o == null) {
        return null;
      }
      if (IsUntaggedInteger(o)) {
        return o.CanValueFitInInt64() ?
          new CBORNumber(NumberKind.Integer, o.AsInt64Value()) :
          new CBORNumber(NumberKind.EInteger, o.AsEIntegerValue());
      } else if (!o.isTagged() && o.getType() == CBORType.FloatingPoint) {
        return CBORNumber.FromDoubleBits(o.AsDoubleBits());
      }
      return o.HasOneTag(2) || o.HasOneTag(3) ?
        BignumToNumber(o) : o.HasOneTag(4) ||
           o.HasOneTag(5) || o.HasOneTag(264) ||
           o.HasOneTag(265) || o.HasOneTag(268) ||
           o.HasOneTag(269) ? BigFracToNumber(o,
                o.getMostOuterTag().ToInt32Checked()) : o.HasOneTag(30) ||
                o.HasOneTag(270) ? RationalToNumber(o,
                o.getMostOuterTag().ToInt32Checked()) : null;
    }

    private static boolean IsUntaggedInteger(CBORObject o) {
      return !o.isTagged() && o.getType() == CBORType.Integer;
    }

    private static boolean IsUntaggedIntegerOrBignum(CBORObject o) {
      return IsUntaggedInteger(o) || ((o.HasOneTag(2) || o.HasOneTag(3)) &&
          o.getType() == CBORType.ByteString);
    }

    private static EInteger IntegerOrBignum(CBORObject o) {
      if (IsUntaggedInteger(o)) {
        return o.AsEIntegerValue();
      } else {
        CBORNumber n = BignumToNumber(o);
        return n.GetNumberInterface().AsEInteger(n.GetValue());
      }
    }

    private static CBORNumber RationalToNumber(
      CBORObject o,
      int tagName) {
      if (o.getType() != CBORType.Array) {
        return null; // "Big fraction must be an array";
      }
      if (tagName == 270) {
        if (o.size() != 3) {
          return null; // "Extended big fraction requires exactly 3 items";
        }
        if (!IsUntaggedInteger(o.get(2))) {
          return null; // "Third item must be an integer";
        }
      } else {
        if (o.size() != 2) {
          return null; // "Big fraction requires exactly 2 items";
        }
      }
      if (!IsUntaggedIntegerOrBignum(o.get(0))) {
        return null; // "Numerator is not an integer or bignum";
      }
      if (!IsUntaggedIntegerOrBignum(o.get(1))) {
        return null; // "Denominator is not an integer or bignum");
      }
      EInteger numerator = IntegerOrBignum(o.get(0));
      EInteger denominator = IntegerOrBignum(o.get(1));
      if (denominator.signum() <= 0) {
        return null; // "Denominator may not be negative or zero");
      }
      if (tagName == 270) {
        if (numerator.signum() < 0) {
          return null; // "Numerator may not be negative");
        }
        if (!o.get(2).CanValueFitInInt32()) {
          return null; // "Invalid options";
        }
        int options = o.get(2).AsInt32Value();
        ERational erat = null;
        switch (options) {
          case 0:
            erat = ERational.Create(numerator, denominator);
            break;
          case 1:
            erat = ERational.Create(numerator, denominator).Negate();
            break;
          case 2:
            if (!numerator.isZero() || denominator.compareTo(1) != 0) {
              return null; // "invalid values");
            }
            erat = ERational.PositiveInfinity;
            break;
          case 3:
            if (!numerator.isZero() || denominator.compareTo(1) != 0) {
              return null; // "invalid values");
            }
            erat = ERational.NegativeInfinity;
            break;
          case 4:
          case 5:
          case 6:
          case 7:
            if (denominator.compareTo(1) != 0) {
              return null; // "invalid values");
            }
            erat = ERational.CreateNaN(
                numerator,
                options >= 6,
                options == 5 || options == 7);
            break;
          default: return null; // "Invalid options");
        }
        return CBORNumber.FromObject(erat);
      } else {
        return CBORNumber.FromObject(ERational.Create(numerator, denominator));
      }
    }

    private static boolean CheckRationalToNumber(
      CBORObject o,
      int tagName) {
      if (o.getType() != CBORType.Array) {
        return false;
      }
      if (tagName == 270) {
        if (o.size() != 3) {
          return false;
        }
        if (!IsUntaggedInteger(o.get(2))) {
          return false;
        }
      } else {
        if (o.size() != 2) {
          return false;
        }
      }
      if (!IsUntaggedIntegerOrBignum(o.get(0))) {
        return false;
      }
      if (!IsUntaggedIntegerOrBignum(o.get(1))) {
        return false;
      }
      EInteger denominator = IntegerOrBignum(o.get(1));
      if (denominator.signum() <= 0) {
        return false;
      }
      if (tagName == 270) {
        EInteger numerator = IntegerOrBignum(o.get(0));
        if (numerator.signum() < 0 || !o.get(2).CanValueFitInInt32()) {
          return false;
        }
        int options = o.get(2).AsInt32Value();
        switch (options) {
          case 0:
          case 1:
            return true;
          case 2:
          case 3:
            return numerator.isZero() && denominator.compareTo(1) == 0;
          case 4:
          case 5:
          case 6:
          case 7:
            return denominator.compareTo(1) == 0;
          default:
            return false;
        }
      }
      return true;
    }

    private static boolean CheckBigFracToNumber(
      CBORObject o,
      int tagName) {
      if (o.getType() != CBORType.Array) {
        return false;
      }
      if (tagName == 268 || tagName == 269) {
        if (o.size() != 3) {
          return false;
        }
        if (!IsUntaggedInteger(o.get(2))) {
          return false;
        }
      } else {
        if (o.size() != 2) {
          return false;
        }
      }
      if (tagName == 4 || tagName == 5) {
        if (!IsUntaggedInteger(o.get(0))) {
          return false;
        }
      } else {
        if (!IsUntaggedIntegerOrBignum(o.get(0))) {
          return false;
        }
      }
      if (!IsUntaggedIntegerOrBignum(o.get(1))) {
        return false;
      }
      if (tagName == 268 || tagName == 269) {
        EInteger exponent = IntegerOrBignum(o.get(0));
        EInteger mantissa = IntegerOrBignum(o.get(1));
        if (mantissa.signum() < 0 || !o.get(2).CanValueFitInInt32()) {
          return false;
        }
        int options = o.get(2).AsInt32Value();
        switch (options) {
          case 0:
          case 1:
            return true;
          case 2:
          case 3:
            return exponent.isZero() && mantissa.isZero();
          case 4:
          case 5:
          case 6:
          case 7:
            return exponent.isZero();
          default:
            return false;
        }
      }
      return true;
    }

    private static CBORNumber BigFracToNumber(
      CBORObject o,
      int tagName) {
      if (o.getType() != CBORType.Array) {
        return null; // "Big fraction must be an array");
      }
      if (tagName == 268 || tagName == 269) {
        if (o.size() != 3) {
          return null; // "Extended big fraction requires exactly 3 items");
        }
        if (!IsUntaggedInteger(o.get(2))) {
          return null; // "Third item must be an integer");
        }
      } else {
        if (o.size() != 2) {
          return null; // "Big fraction requires exactly 2 items");
        }
      }
      if (tagName == 4 || tagName == 5) {
        if (!IsUntaggedInteger(o.get(0))) {
          return null; // "Exponent is not an integer");
        }
      } else {
        if (!IsUntaggedIntegerOrBignum(o.get(0))) {
          return null; // "Exponent is not an integer or bignum");
        }
      }
      if (!IsUntaggedIntegerOrBignum(o.get(1))) {
        return null; // "Mantissa is not an integer or bignum");
      }
      EInteger exponent = IntegerOrBignum(o.get(0));
      EInteger mantissa = IntegerOrBignum(o.get(1));
      boolean isdec = tagName == 4 || tagName == 264 || tagName == 268;
      EDecimal edec = isdec ? EDecimal.Create(mantissa, exponent) : null;
      EFloat efloat = !isdec ? EFloat.Create(mantissa, exponent) : null;
      if (tagName == 268 || tagName == 269) {
        if (mantissa.signum() < 0) {
          return null; // "Mantissa may not be negative");
        }
        if (!o.get(2).CanValueFitInInt32()) {
          return null; // "Invalid options");
        }
        int options = o.get(2).AsInt32Value();
        switch (options) {
          case 0:
            break;
          case 1:
            if (isdec) {
              edec = edec.Negate();
            } else {
              efloat = efloat.Negate();
            }
            break;
          case 2:
            if (!exponent.isZero() || !mantissa.isZero()) {
              return null; // "invalid values");
            }
            if (isdec) {
              edec = EDecimal.PositiveInfinity;
            } else {
              efloat = EFloat.PositiveInfinity;
            }
            break;
          case 3:
            if (!exponent.isZero() || !mantissa.isZero()) {
              return null; // "invalid values");
            }
            if (isdec) {
              edec = EDecimal.NegativeInfinity;
            } else {
              efloat = EFloat.NegativeInfinity;
            }
            break;
          case 4:
          case 5:
          case 6:
          case 7:
            if (!exponent.isZero()) {
              return null; // "invalid values");
            }
            if (isdec) {
              edec = EDecimal.CreateNaN(
                  mantissa,
                  options >= 6,
                  options == 5 || options == 7,
                  null);
            } else {
              efloat = EFloat.CreateNaN(
                  mantissa,
                  options >= 6,
                  options == 5 || options == 7,
                  null);
            }
            break;
          default: return null; // "Invalid options");
        }
      }
      return isdec ? CBORNumber.FromObject(edec) :
CBORNumber.FromObject(efloat);
    }

    public final NumberKind getKind() { return propVarkind; }
private final NumberKind propVarkind;

    public boolean CanTruncatedIntFitInInt32() {
      return
        this.GetNumberInterface().CanTruncatedIntFitInInt32(this.GetValue());
    }

    public boolean CanTruncatedIntFitInInt64() {
      switch (this.getKind()) {
        case Integer:
          return true;
        default:
          return

            this.GetNumberInterface()
            .CanTruncatedIntFitInInt64(this.GetValue());
      }
    }

    public boolean CanTruncatedIntFitInUInt64() {
      return this.GetNumberInterface()
        .CanTruncatedIntFitInUInt64(this.GetValue());
    }

    public boolean CanFitInSingle() {
      return this.GetNumberInterface().CanFitInSingle(this.GetValue());
    }

    public boolean CanFitInDouble() {
      return this.GetNumberInterface().CanFitInDouble(this.GetValue());
    }

    public boolean IsFinite() {
      switch (this.getKind()) {
        case Integer:
        case EInteger:
          return true;
        default: return !this.IsInfinity() && !this.IsNaN();
      }
    }

    public boolean IsInteger() {
      switch (this.getKind()) {
        case Integer:
        case EInteger:
          return true;
        default: return this.GetNumberInterface().IsIntegral(this.GetValue());
      }
    }

    public boolean IsNegative() {
      return this.GetNumberInterface().IsNegative(this.GetValue());
    }

    public boolean IsZero() {
      switch (this.getKind()) {
        case Integer: {
            long thisValue = (((Long)this.value).longValue());
            return thisValue == 0;
          }
        default: return this.GetNumberInterface().IsNumberZero(this.GetValue());
      }
    }

    public EInteger ToEInteger() {
      return this.GetNumberInterface().AsEInteger(this.GetValue());
    }

    public EInteger ToEIntegerIfExact() {
      if (!this.IsInteger()) {
 throw new ArithmeticException("Not an" +
"\u0020integer");
}
 return this.ToEInteger();
    }

    // Begin integer conversions

    public byte ToByteChecked() {
      if (!this.IsFinite()) {
 throw new ArithmeticException("Value is" +
"\u0020infinity or NaN");
}
 return this.ToEInteger().ToByteChecked();
    }

    public byte ToByteUnchecked() {
      return this.IsFinite() ? this.ToEInteger().ToByteUnchecked() : (byte)0;
    }

    public byte ToByteIfExact() {
if (!this.IsFinite()) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
if (this.IsZero()) {
        return (byte)0;
      }
      if (this.IsNegative()) {
 throw new ArithmeticException("Value out of range");
}
 return this.ToEIntegerIfExact().ToByteChecked();
    }

    public static CBORNumber FromByte(byte inputByte) {
      int val = inputByte & 0xff;
      return FromObject((long)val);
    }

    public short ToInt16Checked() {
      if (!this.IsFinite()) {
 throw new ArithmeticException("Value is" +
"\u0020infinity or NaN");
}
 return this.ToEInteger().ToInt16Checked();
    }

    public short ToInt16Unchecked() {
      return this.IsFinite() ? this.ToEInteger().ToInt16Unchecked() : (short)0;
    }

    public short ToInt16IfExact() {
      if (!this.IsFinite()) {
 throw new ArithmeticException("Value is infinity or NaN");
}
 return this.IsZero() ? ((short)0) : this.ToEIntegerIfExact().ToInt16Checked();
    }

    public static CBORNumber FromInt16(short inputInt16) {
      int val = inputInt16;
      return FromObject((long)val);
    }

    public int ToInt32Checked() {
      if (!this.IsFinite()) {
 throw new ArithmeticException("Value is" +
"\u0020infinity or NaN");
}
 return this.ToEInteger().ToInt32Checked();
    }

    public int ToInt32Unchecked() {
      return this.IsFinite() ? this.ToEInteger().ToInt32Unchecked() : 0;
    }

    public int ToInt32IfExact() {
      if (!this.IsFinite()) {
 throw new ArithmeticException("Value is infinity or NaN");
}
 return this.IsZero() ? 0 : this.ToEIntegerIfExact().ToInt32Checked();
    }

    public long ToInt64Checked() {
      if (!this.IsFinite()) {
 throw new ArithmeticException("Value is" +
"\u0020infinity or NaN");
}
 return this.ToEInteger().ToInt64Checked();
    }

    public long ToInt64Unchecked() {
      return this.IsFinite() ? this.ToEInteger().ToInt64Unchecked() : 0L;
    }

    public long ToInt64IfExact() {
      if (!this.IsFinite()) {
 throw new ArithmeticException("Value is infinity or NaN");
}
 return this.IsZero() ? 0L : this.ToEIntegerIfExact().ToInt64Checked();
    }
    // End integer conversions
    private static CBORNumber BignumToNumber(CBORObject o) {
      if (o.getType() != CBORType.ByteString) {
        return null; // "Byte array expected");
      }
      boolean negative = o.HasMostInnerTag(3);
      byte[] data = o.GetByteString();
      if (data.length <= 7) {
        long x = 0;
        for (int i = 0; i < data.length; ++i) {
          x <<= 8;
          x |= ((long)data[i]) & 0xff;
        }
        if (negative) {
          x = -x;
          --x;
        }
        return new CBORNumber(NumberKind.Integer, x);
      }
      int neededLength = data.length;
      byte[] bytes;
      EInteger bi;
      boolean extended = false;
      if (((data[0] >> 7) & 1) != 0) {
        // Increase the needed length
        // if the highest bit is set, to
        // distinguish negative and positive
        // values
        ++neededLength;
        extended = true;
      }
      if (extended || negative) {
        bytes = new byte[neededLength];
        System.arraycopy(data, 0, bytes, neededLength - data.length, data.length);
        if (negative) {
          int i;
          for (i = neededLength - data.length; i < neededLength; ++i) {
            bytes[i] ^= 0xff;
          }
        }
        if (extended) {
          bytes[0] = negative ? (byte)0xff : (byte)0;
        }
        bi = EInteger.FromBytes(bytes, false);
      } else {
        // No data conversion needed
        bi = EInteger.FromBytes(data, false);
      }
      return bi.CanFitInInt64() ? new CBORNumber(NumberKind.Integer,
  bi.ToInt64Checked()) : new CBORNumber(NumberKind.EInteger, bi);
    }

    @Override public String toString() {
      switch (this.getKind()) {
        case Integer: {
            long longItem = (((Long)this.value).longValue());
            return CBORUtilities.LongToString(longItem);
          }
        case Double: {
            long longItem = (((Long)this.value).longValue());
            return CBORUtilities.DoubleBitsToString(longItem);
          }
        default:
          return (this.value == null) ? "" :
            this.value.toString();
      }
    }

    String ToJSONString() {
      switch (this.getKind()) {
        case Double: {
            long f = (((Long)this.value).longValue());
            if (!CBORUtilities.DoubleBitsFinite(f)) {
              return "null";
            }
            String dblString = CBORUtilities.DoubleBitsToString(f);
            return CBORUtilities.TrimDotZero(dblString);
          }
        case Integer: {
            long longItem = (((Long)this.value).longValue());
            return CBORUtilities.LongToString(longItem);
          }
        case EInteger: {
            Object eiobj = this.value;
            return ((EInteger)eiobj).toString();
          }
        case EDecimal: {
            EDecimal dec = (EDecimal)this.value;
            return dec.IsInfinity() || dec.IsNaN() ? "null" : dec.toString();
          }
        case EFloat: {
            EFloat flo = (EFloat)this.value;
            if (flo.IsInfinity() || flo.IsNaN()) {
              return "null";
            }
            if (flo.isFinite() &&
              flo.getExponent().Abs().compareTo(EInteger.FromInt64(2500)) > 0) {
              // Too inefficient to convert to a decimal number
              // from a bigfloat with a very high exponent,
              // so convert to double instead
              long f = flo.ToDoubleBits();
              if (!CBORUtilities.DoubleBitsFinite(f)) {
                return "null";
              }
              String dblString = CBORUtilities.DoubleBitsToString(f);
              return CBORUtilities.TrimDotZero(dblString);
            }
            return flo.toString();
          }
        case ERational: {
            ERational dec = (ERational)this.value;
            EDecimal f = dec.ToEDecimalExactIfPossible(
                EContext.Decimal128.WithUnlimitedExponents());
            // System.out.println(
            // " end="+java.util.Date.UtcNow);
            return !f.isFinite() ? "null" : f.toString();
          }
        default: throw new IllegalStateException();
      }
    }

    static CBORNumber FromObject(int intValue) {
      return new CBORNumber(NumberKind.Integer, (long)intValue);
    }
    static CBORNumber FromObject(long longValue) {
      return new CBORNumber(NumberKind.Integer, longValue);
    }
    static CBORNumber FromDoubleBits(long doubleBits) {
      return new CBORNumber(NumberKind.Double, doubleBits);
    }
    static CBORNumber FromObject(EInteger eivalue) {
      return new CBORNumber(NumberKind.EInteger, eivalue);
    }
    static CBORNumber FromObject(EFloat value) {
      return new CBORNumber(NumberKind.EFloat, value);
    }
    static CBORNumber FromObject(EDecimal value) {
      return new CBORNumber(NumberKind.EDecimal, value);
    }
    static CBORNumber FromObject(ERational value) {
      return new CBORNumber(NumberKind.ERational, value);
    }

    public boolean CanFitInInt32() {
      ICBORNumber icn = this.GetNumberInterface();
      Object gv = this.GetValue();
      if (!icn.CanFitInInt64(gv)) {
        return false;
      }
      long v = icn.AsInt64(gv);
      return v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE;
    }

    public boolean CanFitInInt64() {
      return this.GetNumberInterface().CanFitInInt64(this.GetValue());
    }

    public boolean CanFitInUInt64() {
      return this.GetNumberInterface().CanFitInUInt64(this.GetValue());
    }

    public boolean IsInfinity() {
      return this.GetNumberInterface().IsInfinity(this.GetValue());
    }

    public boolean IsPositiveInfinity() {
      return this.GetNumberInterface().IsPositiveInfinity(this.GetValue());
    }

    public boolean IsNegativeInfinity() {
      return this.GetNumberInterface().IsNegativeInfinity(this.GetValue());
    }

    public boolean IsNaN() {
      return this.GetNumberInterface().IsNaN(this.GetValue());
    }

    public EDecimal ToEDecimal() {
      return this.GetNumberInterface().AsEDecimal(this.GetValue());
    }

    public EFloat ToEFloat() {
      return this.GetNumberInterface().AsEFloat(this.GetValue());
    }

    public ERational ToERational() {
      return this.GetNumberInterface().AsERational(this.GetValue());
    }

    public CBORNumber Abs() {
      switch (this.getKind()) {
        case Integer: {
            long longValue = (((Long)this.value).longValue());
            return longValue == Long.MIN_VALUE ?
              FromObject(EInteger.FromInt64(longValue).Negate()) :
              longValue >= 0 ? this : new CBORNumber(
                  this.getKind(),
                  Math.abs(longValue));
          }
        case EInteger: {
            EInteger eivalue = (EInteger)this.value;
            return eivalue.signum() >= 0 ? this : FromObject(eivalue.Abs());
          }
        default:
          return new CBORNumber(this.getKind(),
              this.GetNumberInterface().Abs(this.GetValue()));
      }
    }

    public CBORNumber Negate() {
      switch (this.getKind()) {
        case Integer: {
            long longValue = (((Long)this.value).longValue());
            return longValue == 0 ? FromObject(EDecimal.NegativeZero) :
              longValue == Long.MIN_VALUE ?
FromObject(EInteger.FromInt64(longValue).Negate()) : new
CBORNumber(this.getKind(), -longValue);
          }
        case EInteger: {
            EInteger eiValue = (EInteger)this.value;
            return eiValue.isZero() ? FromObject(EDecimal.NegativeZero) :
FromObject(eiValue.Negate());
          }
        default:
          return new CBORNumber(this.getKind(),
              this.GetNumberInterface().Negate(this.GetValue()));
      }
    }

    private static ERational CheckOverflow(
      ERational e1,
      ERational e2,
      ERational eresult) {
      if (e1.isFinite() && e2.isFinite() && eresult.IsNaN()) {
 throw new OutOfMemoryError("Result might be too big to fit in" +
          "\u0020memory");
}
 return eresult;
    }

    private static EDecimal CheckOverflow(EDecimal e1, EDecimal e2, EDecimal
      eresult) {
      // System.out.println("ED e1.setExp("+e1.getExponent()));
      // System.out.println("ED e2.setExp("+e2.getExponent()));
      if (e1.isFinite() && e2.isFinite() && eresult.IsNaN()) {
 throw new OutOfMemoryError("Result might be too big to fit in" +
          "\u0020memory");
}
 return eresult;
    }

    private static EFloat CheckOverflow(EFloat e1, EFloat e2, EFloat eresult) {
      if (e1.isFinite() && e2.isFinite() && eresult.IsNaN()) {
 throw new OutOfMemoryError("Result might be too big to fit in" +
          "\u0020memory");
}
 return eresult;
    }

    private static NumberKind GetConvertKind(CBORNumber a, CBORNumber b) {
      NumberKind typeA = a.getKind();
      NumberKind typeB = b.getKind();
      NumberKind convertKind = !a.IsFinite() ?
        (typeB == NumberKind.Integer || typeB ==
            NumberKind.EInteger) ? ((typeA == NumberKind.Double) ?
NumberKind.EFloat :
            typeA) : ((typeB == NumberKind.Double) ? NumberKind.EFloat :
typeB) :
        !b.IsFinite() ? (typeA == NumberKind.Integer || typeA ==
                    NumberKind.EInteger) ? ((typeB == NumberKind.Double) ?
        NumberKind.EFloat : typeB) : ((typeA == NumberKind.Double) ?
NumberKind.EFloat : typeA) :
          typeA == NumberKind.ERational || typeB ==
                NumberKind.ERational ? NumberKind.ERational :
                  typeA == NumberKind.EDecimal || typeB == NumberKind.EDecimal ?
                NumberKind.EDecimal : (typeA == NumberKind.EFloat || typeB ==
                NumberKind.EFloat || typeA == NumberKind.Double || typeB ==
NumberKind.Double) ?
                                    NumberKind.EFloat : NumberKind.EInteger;
      return convertKind;
    }

    public CBORNumber Add(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      CBORNumber a = this;
      Object objA = a.value;
      Object objB = b.value;
      NumberKind typeA = a.getKind();
      NumberKind typeB = b.getKind();
      if (typeA == NumberKind.Integer && typeB == NumberKind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if ((valueA < 0 && valueB < Long.MIN_VALUE - valueA) ||
          (valueA > 0 && valueB > Long.MAX_VALUE - valueA)) {
          // would overflow, convert to EInteger
          return CBORNumber.FromObject(
              EInteger.FromInt64(valueA).Add(EInteger.FromInt64(valueB)));
        }
        return new CBORNumber(NumberKind.Integer, valueA + valueB);
      }
      NumberKind convertKind = GetConvertKind(a, b);
      if (convertKind == NumberKind.ERational) {
        // System.out.println("Rational/Rational");
        ERational e1 = GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        // System.out.println("conv Rational/Rational");
        return new CBORNumber(NumberKind.ERational,
            CheckOverflow(
              e1,
              e2,
              e1.Add(e2)));
      }
      if (convertKind == NumberKind.EDecimal) {
        // System.out.println("Decimal/Decimal");
        EDecimal e1 = GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        // System.out.println("ED e1.setExp("+e1.getExponent()));
        // System.out.println("ED e2.setExp("+e2.getExponent()));
        return new CBORNumber(NumberKind.EDecimal,
            CheckOverflow(
              e1,
              e2,
              e1.Add(e2)));
      }
      if (convertKind == NumberKind.EFloat) {
        // System.out.println("Float/Float");
        EFloat e1 = GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        // System.out.println("EF e1.setExp("+e1.getExponent()));
        // System.out.println("EF e2.setExp("+e2.getExponent()));
        return new CBORNumber(NumberKind.EFloat,
            CheckOverflow(
              e1,
              e2,
              e1.Add(e2)));
      } else {
        // System.out.println("type=" + typeA + "/" + typeB + " finite=" +
        // (// this.IsFinite()) + "/" + (b.IsFinite()));
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
        return new CBORNumber(NumberKind.EInteger, b1.Add(b2));
      }
    }

    public CBORNumber Subtract(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      CBORNumber a = this;
      Object objA = a.value;
      Object objB = b.value;
      NumberKind typeA = a.getKind();
      NumberKind typeB = b.getKind();
      if (typeA == NumberKind.Integer && typeB == NumberKind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if ((valueB < 0 && Long.MAX_VALUE + valueB < valueA) ||
          (valueB > 0 && Long.MIN_VALUE + valueB > valueA)) {
          // would overflow, convert to EInteger
          return CBORNumber.FromObject(
              EInteger.FromInt64(valueA).Subtract(EInteger.FromInt64(
                  valueB)));
        }
        return new CBORNumber(NumberKind.Integer, valueA - valueB);
      }
      NumberKind convertKind = GetConvertKind(a, b);
      if (convertKind == NumberKind.ERational) {
        ERational e1 = GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return new CBORNumber(NumberKind.ERational,
            CheckOverflow(
              e1,
              e2,
              e1.Subtract(e2)));
      }
      if (convertKind == NumberKind.EDecimal) {
        EDecimal e1 = GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        return new CBORNumber(NumberKind.EDecimal,
            CheckOverflow(
              e1,
              e2,
              e1.Subtract(e2)));
      }
      if (convertKind == NumberKind.EFloat) {
        EFloat e1 = GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        return new CBORNumber(NumberKind.EFloat,
            CheckOverflow(
              e1,
              e2,
              e1.Subtract(e2)));
      } else {
        // System.out.println("type=" + typeA + "/" + typeB + " finite=" +
        // (// this.IsFinite()) + "/" + (b.IsFinite()));
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA)
           .Subtract(GetNumberInterface(typeB).AsEInteger(objB));
        return new CBORNumber(NumberKind.EInteger, b1);
      }
    }

    public CBORNumber Multiply(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      CBORNumber a = this;
      Object objA = a.value;
      Object objB = b.value;
      NumberKind typeA = a.getKind();
      NumberKind typeB = b.getKind();
      if (typeA == NumberKind.Integer && typeB == NumberKind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        boolean apos = valueA > 0L;
        boolean bpos = valueB > 0L;
        if (
          (apos && ((!bpos && (Long.MIN_VALUE / valueA) > valueB) ||
              (bpos && valueA > (Long.MAX_VALUE / valueB)))) ||
          (!apos && ((!bpos && valueA != 0L &&
                (Long.MAX_VALUE / valueA) > valueB) ||
              (bpos && valueA < (Long.MIN_VALUE / valueB))))) {
          // would overflow, convert to EInteger
          EInteger bvalueA = EInteger.FromInt64(valueA);
          EInteger bvalueB = EInteger.FromInt64(valueB);
          return CBORNumber.FromObject(bvalueA.Multiply(bvalueB));
        }
        return CBORNumber.FromObject(valueA * valueB);
      }
      NumberKind convertKind = GetConvertKind(a, b);
      if (convertKind == NumberKind.ERational) {
        ERational e1 = GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return CBORNumber.FromObject(CheckOverflow(e1, e2, e1.Multiply(e2)));
      }
      if (convertKind == NumberKind.EDecimal) {
        EDecimal e1 = GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        return CBORNumber.FromObject(CheckOverflow(e1, e2, e1.Multiply(e2)));
      }
      if (convertKind == NumberKind.EFloat) {
        EFloat e1 = GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        return new CBORNumber(NumberKind.EFloat,
            CheckOverflow(
              e1,
              e2,
              e1.Multiply(e2)));
      } else {
        // System.out.println("type=" + typeA + "/" + typeB + " finite=" +
        // (// this.IsFinite()) + "/" + (b.IsFinite()));
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
        return new CBORNumber(NumberKind.EInteger, b1.Multiply(b2));
      }
    }

    public CBORNumber Divide(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      CBORNumber a = this;
      Object objA = a.value;
      Object objB = b.value;
      NumberKind typeA = a.getKind();
      NumberKind typeB = b.getKind();
      if (typeA == NumberKind.Integer && typeB == NumberKind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if (valueB == 0) {
          return (valueA == 0) ? CBORNumber.FromObject(EDecimal.NaN) :
            ((valueA < 0) ? CBORNumber.FromObject(EDecimal.NegativeInfinity) :

              CBORNumber.FromObject(EDecimal.PositiveInfinity));
        }
        if (valueA == Long.MIN_VALUE && valueB == -1) {
          return new CBORNumber(NumberKind.Integer, valueA).Negate();
        }
        long quo = valueA / valueB;
        long rem = valueA - (quo * valueB);
        return (rem == 0) ? new CBORNumber(NumberKind.Integer, quo) :
          new CBORNumber(NumberKind.ERational,
            ERational.Create(
              EInteger.FromInt64(valueA),
              EInteger.FromInt64(valueB)));
      }
      NumberKind convertKind = GetConvertKind(a, b);
      if (convertKind == NumberKind.ERational) {
        ERational e1 = GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return new CBORNumber(NumberKind.ERational,
            CheckOverflow(
              e1,
              e2,
              e1.Divide(e2)));
      }
      if (convertKind == NumberKind.EDecimal) {
        EDecimal e1 = GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        if (e1.isZero() && e2.isZero()) {
          return new CBORNumber(NumberKind.EDecimal, EDecimal.NaN);
        }
        EDecimal eret = e1.Divide(e2, null);
        // If either operand is infinity or NaN, the result
        // is already exact. Likewise if the result is a finite number.
        if (!e1.isFinite() || !e2.isFinite() || eret.isFinite()) {
          return new CBORNumber(NumberKind.EDecimal, eret);
        }
        ERational er1 = GetNumberInterface(typeA).AsERational(objA);
        ERational er2 = GetNumberInterface(typeB).AsERational(objB);
        return new CBORNumber(NumberKind.ERational,
            CheckOverflow(
              er1,
              er2,
              er1.Divide(er2)));
      }
      if (convertKind == NumberKind.EFloat) {
        EFloat e1 = GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        if (e1.isZero() && e2.isZero()) {
          return CBORNumber.FromObject(EDecimal.NaN);
        }
        EFloat eret = e1.Divide(e2, null);
        // If either operand is infinity or NaN, the result
        // is already exact. Likewise if the result is a finite number.
        if (!e1.isFinite() || !e2.isFinite() || eret.isFinite()) {
          return CBORNumber.FromObject(eret);
        }
        ERational er1 = GetNumberInterface(typeA).AsERational(objA);
        ERational er2 = GetNumberInterface(typeB).AsERational(objB);
        return new CBORNumber(NumberKind.ERational,
            CheckOverflow(
              er1,
              er2,
              er1.Divide(er2)));
      } else {
        // System.out.println("type=" + typeA + "/" + typeB + " finite=" +
        // (// this.IsFinite()) + "/" + (b.IsFinite()));
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
        if (b2.isZero()) {
          return b1.isZero() ? CBORNumber.FromObject(EDecimal.NaN) : ((b1.signum() <
                0) ? CBORNumber.FromObject(EDecimal.NegativeInfinity) :
              CBORNumber.FromObject(EDecimal.PositiveInfinity));
        }
        EInteger bigrem;
        EInteger bigquo;
        {
          EInteger[] divrem = b1.DivRem(b2);
          bigquo = divrem[0];
          bigrem = divrem[1];
        }
        return bigrem.isZero() ? CBORNumber.FromObject(bigquo) :
          new CBORNumber(NumberKind.ERational, ERational.Create(b1, b2));
      }
    }

    public CBORNumber Remainder(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      Object objA = this.value;
      Object objB = b.value;
      NumberKind typeA = this.getKind();
      NumberKind typeB = b.getKind();
      if (typeA == NumberKind.Integer && typeB == NumberKind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        return (valueA == Long.MIN_VALUE && valueB == -1) ?
          CBORNumber.FromObject(0) : CBORNumber.FromObject(valueA % valueB);
      }
      NumberKind convertKind = GetConvertKind(this, b);
      if (convertKind == NumberKind.ERational) {
        ERational e1 = GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return CBORNumber.FromObject(CheckOverflow(e1, e2, e1.Remainder(e2)));
      }
      if (convertKind == NumberKind.EDecimal) {
        EDecimal e1 = GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        return CBORNumber.FromObject(CheckOverflow(e1, e2, e1.Remainder(e2,
                null)));
      }
      if (convertKind == NumberKind.EFloat) {
        EFloat e1 = GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        return CBORNumber.FromObject(CheckOverflow(e1, e2, e1.Remainder(e2,
                null)));
      } else {
        // System.out.println("type=" + typeA + "/" + typeB + " finite=" +
        // (// this.IsFinite()) + "/" + (b.IsFinite()));
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
        return CBORNumber.FromObject(b1.Remainder(b2));
      }
    }

    public int compareTo(int other) {
      return this.compareTo(CBORObject.FromObject(other).AsNumber());
    }

    public int compareTo(long other) {
      return this.compareTo(CBORObject.FromObject(other).AsNumber());
    }

    public int compareTo(CBORNumber other) {
      if (other == null) {
        return 1;
      }
      if (this == other) {
        return 0;
      }

      NumberKind typeA = this.getKind();
      NumberKind typeB = other.getKind();
      Object objA = this.value;
      Object objB = other.value;
      int cmp;
      if (typeA == typeB) {
        switch (typeA) {
          case Integer: {
              long a = (((Long)objA).longValue());
              long b = (((Long)objB).longValue());
              cmp = (a == b) ? 0 : ((a < b) ? -1 : 1);
              break;
            }
          case EInteger: {
              EInteger bigintA = (EInteger)objA;
              EInteger bigintB = (EInteger)objB;
              cmp = bigintA.compareTo(bigintB);
              break;
            }
          case Double: {
              long a = (((Long)objA).longValue());
              long b = (((Long)objB).longValue());
              // Treat NaN as greater than all other numbers
              cmp = CBORUtilities.DoubleBitsNaN(a) ?
                (CBORUtilities.DoubleBitsNaN(b) ? 0 : 1) :
                (CBORUtilities.DoubleBitsNaN(b) ?
                  -1 : (((a < 0) != (b < 0)) ? ((a < b) ? -1 : 1) :
                    ((a == b) ? 0 : (((a < b) ^ (a < 0)) ? -1 : 1))));
              break;
            }
          case EDecimal: {
              cmp = ((EDecimal)objA).compareTo((EDecimal)objB);
              break;
            }
          case EFloat: {
              cmp = ((EFloat)objA).compareTo(
                  (EFloat)objB);
              break;
            }
          case ERational: {
              cmp = ((ERational)objA).compareTo(
                  (ERational)objB);
              break;
            }
          default: throw new IllegalStateException(
              "Unexpected data type");
        }
      } else {
        int s1 = GetNumberInterface(typeA).Sign(objA);
        int s2 = GetNumberInterface(typeB).Sign(objB);
        if (s1 != s2 && s1 != 2 && s2 != 2) {
          // if both types are numbers
          // and their signs are different
          return (s1 < s2) ? -1 : 1;
        }
        if (s1 == 2 && s2 == 2) {
          // both are NaN
          cmp = 0;
        } else if (s1 == 2) {
          // first Object is NaN
          return 1;
        } else if (s2 == 2) {
          // second Object is NaN
          return -1;
        } else {
          // System.out.println("a=" + this + " b=" + other);
          if (typeA == NumberKind.ERational) {
            ERational e1 = GetNumberInterface(typeA).AsERational(objA);
            if (typeB == NumberKind.EDecimal) {
              EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
              cmp = e1.CompareToDecimal(e2);
            } else {
              EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
              cmp = e1.CompareToBinary(e2);
            }
          } else if (typeB == NumberKind.ERational) {
            ERational e2 = GetNumberInterface(typeB).AsERational(objB);
            if (typeA == NumberKind.EDecimal) {
              EDecimal e1 = GetNumberInterface(typeA).AsEDecimal(objA);
              cmp = e2.CompareToDecimal(e1);
              cmp = -cmp;
            } else {
              EFloat e1 =
                GetNumberInterface(typeA).AsEFloat(objA);
              cmp = e2.CompareToBinary(e1);
              cmp = -cmp;
            }
          } else if (typeA == NumberKind.EDecimal || typeB ==
NumberKind.EDecimal) {
            EDecimal e2;
            if (typeA == NumberKind.EFloat) {
              EFloat ef1 = (EFloat)objA;
              e2 = (EDecimal)objB;
              cmp = e2.CompareToBinary(ef1);
              cmp = -cmp;
            } else if (typeB == NumberKind.EFloat) {
              EFloat ef1 = (EFloat)objB;
              e2 = (EDecimal)objA;
              cmp = e2.CompareToBinary(ef1);
            } else {
              EDecimal e1 = GetNumberInterface(typeA).AsEDecimal(objA);
              e2 = GetNumberInterface(typeB).AsEDecimal(objB);
              cmp = e1.compareTo(e2);
            }
          } else if (typeA == NumberKind.EFloat || typeB ==
            NumberKind.EFloat || typeA == NumberKind.Double || typeB ==
            NumberKind.Double) {
            EFloat e1 = GetNumberInterface(typeA).AsEFloat(objA);
            EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
            cmp = e1.compareTo(e2);
          } else {
            EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
            EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
            cmp = b1.compareTo(b2);
          }
        }
      }
      return cmp;
    }
  }
