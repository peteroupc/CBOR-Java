package com.upokecenter.cbor;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

// TODO: Consider renaming As* methods here to To*

  /**
   * An instance of a number that CBOR or certain CBOR tags can represent. For
   * this purpose, infinities and not-a-number or NaN values are considered
   * numbers. Currently, this class can store one of the following kinds of
   * numbers: 64-bit signed integers or binary floating-point numbers; or
   * arbitrary-precision integers, decimal numbers, binary numbers, or
   * rational numbers.
   */

  public final class CBORNumber implements Comparable<CBORNumber> {
    enum Kind {
      /**
       * A 64-bit signed integer.
       */
      Integer,

      /**
       * A 64-bit binary floating-point number.
       */
      Double,

      /**
       * An arbitrary-precision integer.
       */
      EInteger,

      /**
       * An arbitrary-precision decimal number.
       */
      EDecimal,

      /**
       * An arbitrary-precision binary number.
       */
      EFloat,

      /**
       * An arbitrary-precision rational number.
       */
      ERational,
    }

    private static final ICBORNumber[] NumberInterfaces = {
      new CBORInteger(),
      new CBORDouble(),
      new CBOREInteger(),
      new CBORExtendedDecimal(),
      new CBORExtendedFloat(),
      new CBORExtendedRational(),
    };

    private final Kind kind;
    private final Object value;
    CBORNumber(Kind kind, Object value) {
      this.kind = kind;
      this.value = value;
    }

    ICBORNumber GetNumberInterface() {
      return GetNumberInterface(this.kind);
    }

    static ICBORNumber GetNumberInterface(CBORObject obj) {
      CBORNumber num = CBORNumber.FromCBORObject(obj);
      return (num == null) ? null : num.GetNumberInterface();
    }

    Object GetValue() {
      return this.value;
    }

    static ICBORNumber GetNumberInterface(Kind kind) {
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

    /**
     * Converts this object's value to a CBOR object.
     * @return A CBOR object that stores this object's value.
     */
    public CBORObject ToCBORObject() {
      return CBORObject.FromObject(this.value);
    }

  /**
   * Not documented yet.
   */
    public final int signum() {
        // TODO: Will return 2 if this is NaN; is this
        // still desirable?
        return this.GetNumberInterface().Sign(this.value);
      }

    static boolean IsNumber(CBORObject o) {
      if (IsUntaggedInteger(o)) {
        return true;
      } else if (!o.isTagged() && o.getType() == CBORType.FloatingPoint) {
        return true;
      } else if (o.HasOneTag(2) || o.HasOneTag(3)) {
        return o.getType() == CBORType.ByteString;
      } else if (o.HasOneTag(4) ||
   o.HasOneTag(5) ||
   o.HasOneTag(264) ||
   o.HasOneTag(265) ||
   o.HasOneTag(268) ||
   o.HasOneTag(269)) {
        return CheckBigFracToNumber(o,
            o.getMostOuterTag().ToInt32Checked());
      } else if (o.HasOneTag(30) ||
        o.HasOneTag(270)) {
        return CheckRationalToNumber(o,
            o.getMostOuterTag().ToInt32Checked());
      } else {
        return false;
      }
    }

    /**
     * Creates a CBOR number object from a CBOR object representing a number (that
     * is, one for which the IsNumber property in.NET or the isNumber()
     * method in Java returns true).
     * @param o The parameter is a CBOR object representing a number.
     * @return A CBOR number object, or null if the given CBOR object is null or
     * does not represent a number.
     */
    public static CBORNumber FromCBORObject(CBORObject o) {
      if (o == null) {
        return null;
      }
      if (IsUntaggedInteger(o)) {
        if (o.CanValueFitInInt64()) {
          return new CBORNumber(Kind.Integer, o.AsInt64Value());
        } else {
          return new CBORNumber(Kind.EInteger, o.AsEIntegerValue());
        }
      } else if (!o.isTagged() && o.getType() == CBORType.FloatingPoint) {
        return CBORNumber.FromObject(o.AsDoubleValue());
      }
      if (o.HasOneTag(2) || o.HasOneTag(3)) {
        return BignumToNumber(o);
      } else if (o.HasOneTag(4) ||
   o.HasOneTag(5) ||
   o.HasOneTag(264) ||
   o.HasOneTag(265) ||
   o.HasOneTag(268) ||
   o.HasOneTag(269)) {
        return BigFracToNumber(o,
            o.getMostOuterTag().ToInt32Checked());
      } else if (o.HasOneTag(30) ||
        o.HasOneTag(270)) {
        return RationalToNumber(o,
            o.getMostOuterTag().ToInt32Checked());
      } else {
        return null;
      }
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
      ERational erat = ERational.Create(numerator, denominator);
      if (tagName == 270) {
        if (numerator.signum() < 0) {
          return null; // "Numerator may not be negative");
        }
        if (!o.get(2).CanValueFitInInt32()) {
          return null; // "Invalid options";
        }
        int options = o.get(2).AsInt32Value();
        switch (options) {
          case 0:
            break;
          case 1:
            erat = erat.Negate();
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
      }
      return CBORNumber.FromObject(erat);
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
          default:
            return null; // "Invalid options");
        }
      }
      if (isdec) {
        return CBORNumber.FromObject(edec);
      } else {
        return CBORNumber.FromObject(efloat);
      }
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public boolean CanTruncatedIntFitInInt32() {
      return
this.GetNumberInterface().CanTruncatedIntFitInInt32(this.GetValue());
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public boolean CanTruncatedIntFitInInt64() {
      switch (this.kind) {
        case Integer:
          return true;
        default:
          return

              this.GetNumberInterface()
.CanTruncatedIntFitInInt64(this.GetValue());
      }
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public boolean CanFitInSingle() {
      return this.GetNumberInterface().CanFitInSingle(this.GetValue());
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public boolean CanFitInDouble() {
      return this.GetNumberInterface().CanFitInDouble(this.GetValue());
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public boolean IsFinite() {
      switch (this.kind) {
        case Integer:
        case EInteger:
          return true;
        default:
          return !this.IsInfinity() && !this.IsNaN();
      }
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public boolean IsInteger() {
      switch (this.kind) {
        case Integer:
        case EInteger:
          return true;
        default:
          return this.GetNumberInterface().IsIntegral(this.GetValue());
      }
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public boolean IsNegative() {
      return this.GetNumberInterface().IsNegative(this.GetValue());
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public boolean IsZero() {
      switch (this.kind) {
        case Integer: {
          long thisValue = (((Long)this.value).longValue());
          return thisValue == 0;
        }
        default: return this.GetNumberInterface().IsNumberZero(this.GetValue());
      }
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     * @throws ArithmeticException This value is infinity or not-a-number.
     */
    public EInteger ToEInteger() {
      return this.GetNumberInterface().AsEInteger(this.GetValue());
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     * @throws ArithmeticException This value is infinity or not-a-number or is not
     * an exact integer.
     */
    public EInteger ToEIntegerIfExact() {
      if (!this.IsInteger()) {
        throw new ArithmeticException("Not an integer");
      }
      return this.ToEInteger();
    }

    // Begin integer conversions

    /**
     * Converts this number's value to a byte (from 0 to 255) if it can fit in a
     * byte (from 0 to 255) after converting it to an integer by discarding
     * its fractional part.
     * @return This number's value, truncated to a byte (from 0 to 255).
     * @throws ArithmeticException This value is infinity or not-a-number, or the
     * number, once converted to an integer by discarding its fractional
     * part, is less than 0 or greater than 255.
     */
    public byte ToByteChecked() {
      if (!this.IsFinite()) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      return this.ToEInteger().ToByteChecked();
    }

    /**
     * Converts this number's value to an integer by discarding its fractional
     * part, and returns the least-significant bits of its two's-complement
     * form as a byte (from 0 to 255).
     * @return This number, converted to a byte (from 0 to 255). Returns 0 if this
     * value is infinity or not-a-number.
     */
    public byte ToByteUnchecked() {
      return this.IsFinite() ? this.ToEInteger().ToByteUnchecked() : (byte)0;
    }

    /**
     * Converts this number's value to a byte (from 0 to 255) if it can fit in a
     * byte (from 0 to 255) without rounding to a different numerical
     * value.
     * @return This number's value as a byte (from 0 to 255).
     * @throws ArithmeticException This value is infinity or not-a-number, is not
     * an exact integer, or is less than 0 or greater than 255.
     */
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

    /**
     * Converts a byte (from 0 to 255) to an arbitrary-precision decimal number.
     * @param inputByte The number to convert as a byte (from 0 to 255).
     * @return This number's value as an arbitrary-precision decimal number.
     */
    public static CBORNumber FromByte(byte inputByte) {
      int val = ((int)inputByte) & 0xff;
      return FromObject((long)val);
    }

    /**
     * Converts this number's value to a 16-bit signed integer if it can fit in a
     * 16-bit signed integer after converting it to an integer by
     * discarding its fractional part.
     * @return This number's value, truncated to a 16-bit signed integer.
     * @throws ArithmeticException This value is infinity or not-a-number, or the
     * number, once converted to an integer by discarding its fractional
     * part, is less than -32768 or greater than 32767.
     */
    public short ToInt16Checked() {
      if (!this.IsFinite()) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      return this.ToEInteger().ToInt16Checked();
    }

    /**
     * Converts this number's value to an integer by discarding its fractional
     * part, and returns the least-significant bits of its two's-complement
     * form as a 16-bit signed integer.
     * @return This number, converted to a 16-bit signed integer. Returns 0 if this
     * value is infinity or not-a-number.
     */
    public short ToInt16Unchecked() {
      return this.IsFinite() ? this.ToEInteger().ToInt16Unchecked() : (short)0;
    }

    /**
     * Converts this number's value to a 16-bit signed integer if it can fit in a
     * 16-bit signed integer without rounding to a different numerical
     * value.
     * @return This number's value as a 16-bit signed integer.
     * @throws ArithmeticException This value is infinity or not-a-number, is not
     * an exact integer, or is less than -32768 or greater than 32767.
     */
    public short ToInt16IfExact() {
      if (!this.IsFinite()) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      return this.IsZero() ? ((short)0) :
this.ToEIntegerIfExact().ToInt16Checked();
    }

    /**
     * Converts a 16-bit signed integer to an arbitrary-precision decimal number.
     * @param inputInt16 The number to convert as a 16-bit signed integer.
     * @return This number's value as an arbitrary-precision decimal number.
     */
    public static CBORNumber FromInt16(short inputInt16) {
      int val = (int)inputInt16;
      return FromObject((long)val);
    }

    /**
     * Converts this number's value to a 32-bit signed integer if it can fit in a
     * 32-bit signed integer after converting it to an integer by
     * discarding its fractional part.
     * @return This number's value, truncated to a 32-bit signed integer.
     * @throws ArithmeticException This value is infinity or not-a-number, or the
     * number, once converted to an integer by discarding its fractional
     * part, is less than -2147483648 or greater than 2147483647.
     */
    public int ToInt32Checked() {
      if (!this.IsFinite()) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      return this.ToEInteger().ToInt32Checked();
    }

    /**
     * Converts this number's value to an integer by discarding its fractional
     * part, and returns the least-significant bits of its two's-complement
     * form as a 32-bit signed integer.
     * @return This number, converted to a 32-bit signed integer. Returns 0 if this
     * value is infinity or not-a-number.
     */
    public int ToInt32Unchecked() {
      return this.IsFinite() ? this.ToEInteger().ToInt32Unchecked() : (int)0;
    }

    /**
     * Converts this number's value to a 32-bit signed integer if it can fit in a
     * 32-bit signed integer without rounding to a different numerical
     * value.
     * @return This number's value as a 32-bit signed integer.
     * @throws ArithmeticException This value is infinity or not-a-number, is not
     * an exact integer, or is less than -2147483648 or greater than
     * 2147483647.
     */
    public int ToInt32IfExact() {
      if (!this.IsFinite()) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      return this.IsZero() ? ((int)0) :
this.ToEIntegerIfExact().ToInt32Checked();
    }

    /**
     * Converts this number's value to a 64-bit signed integer if it can fit in a
     * 64-bit signed integer after converting it to an integer by
     * discarding its fractional part.
     * @return This number's value, truncated to a 64-bit signed integer.
     * @throws ArithmeticException This value is infinity or not-a-number, or the
     * number, once converted to an integer by discarding its fractional
     * part, is less than -9223372036854775808 or greater than
     * 9223372036854775807.
     */
    public long ToInt64Checked() {
      if (!this.IsFinite()) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      return this.ToEInteger().ToInt64Checked();
    }

    /**
     * Converts this number's value to an integer by discarding its fractional
     * part, and returns the least-significant bits of its two's-complement
     * form as a 64-bit signed integer.
     * @return This number, converted to a 64-bit signed integer. Returns 0 if this
     * value is infinity or not-a-number.
     */
    public long ToInt64Unchecked() {
      return this.IsFinite() ? this.ToEInteger().ToInt64Unchecked() : 0L;
    }

    /**
     * Converts this number's value to a 64-bit signed integer if it can fit in a
     * 64-bit signed integer without rounding to a different numerical
     * value.
     * @return This number's value as a 64-bit signed integer.
     * @throws ArithmeticException This value is infinity or not-a-number, is not
     * an exact integer, or is less than -9223372036854775808 or greater
     * than 9223372036854775807.
     */
    public long ToInt64IfExact() {
      if (!this.IsFinite()) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      return this.IsZero() ? 0L :
this.ToEIntegerIfExact().ToInt64Checked();
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
        return new CBORNumber(Kind.Integer, x);
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
      bytes = new byte[neededLength];
      for (int i = 0; i < data.length; ++i) {
        bytes[i] = data[data.length - 1 - i];
        if (negative) {
          bytes[i] = (byte)((~((int)bytes[i])) & 0xff);
        }
      }
      if (extended) {
        bytes[bytes.length - 1] = negative ? (byte)0xff : (byte)0;
      }
      bi = EInteger.FromBytes(bytes, true);
      if (bi.CanFitInInt64()) {
        return new CBORNumber(Kind.Integer, bi.ToInt64Checked());
      } else {
        return new CBORNumber(Kind.EInteger, bi);
      }
    }

    /**
     * Returns the value of this object in text form.
     * @return A text string representing the value of this object.
     */
    @Override public String toString() {
      switch (this.kind) {
        case Integer: {
            long longItem = (((Long)this.value).longValue());
            return CBORUtilities.LongToString(longItem);
          }
        default:
          return (this.value == null) ? "" :
            this.value.toString();
      }
    }

    String ToJSONString() {
      switch (this.kind) {
        case Double: {
            double f = ((Double)this.value).doubleValue();
            if (((f) == Double.NEGATIVE_INFINITY) ||
                         ((f) == Double.POSITIVE_INFINITY) ||
                         Double.isNaN(f)) {
              return "null";
            }
            String dblString = CBORUtilities.DoubleToString(f);
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
            if (dec.IsInfinity() || dec.IsNaN()) {
              return "null";
            } else {
              return dec.toString();
            }
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
              double f = flo.ToDouble();
              if (((f) == Double.NEGATIVE_INFINITY) ||
                             ((f) == Double.POSITIVE_INFINITY) ||
                             Double.isNaN(f)) {
                return "null";
              }
              String dblString = CBORUtilities.DoubleToString(f);
              return CBORUtilities.TrimDotZero(dblString);
            }
            return flo.toString();
          }
        case ERational: {
            ERational dec = (ERational)this.value;
            EDecimal f = dec.ToEDecimalExactIfPossible(
                EContext.Decimal128.WithUnlimitedExponents());
            if (!f.isFinite()) {
              return "null";
            } else {
              return f.toString();
            }
          }
        default: throw new IllegalStateException();
      }
    }

    static CBORNumber FromObject(int intValue) {
      return new CBORNumber(Kind.Integer, (long)intValue);
    }
    static CBORNumber FromObject(long longValue) {
      return new CBORNumber(Kind.Integer, longValue);
    }
    static CBORNumber FromObject(double doubleValue) {
      return new CBORNumber(Kind.Double, doubleValue);
    }
    static CBORNumber FromObject(EInteger eivalue) {
      return new CBORNumber(Kind.EInteger, eivalue);
    }
    static CBORNumber FromObject(EFloat value) {
      return new CBORNumber(Kind.EFloat, value);
    }
    static CBORNumber FromObject(EDecimal value) {
      return new CBORNumber(Kind.EDecimal, value);
    }
    static CBORNumber FromObject(ERational value) {
      return new CBORNumber(Kind.ERational, value);
    }

    /**
     * Returns whether this object's numerical value is an integer, is -(2^31) or
     * greater, and is less than 2^31.
     * @return {@code true} if this object's numerical value is an integer, is
     * -(2^31) or greater, and is less than 2^31; otherwise, {@code false}.
     */
    public boolean CanFitInInt32() {
      ICBORNumber icn = this.GetNumberInterface();
      Object gv = this.GetValue();
      if (!icn.CanFitInInt64(gv)) {
        return false;
      }
      long v = icn.AsInt64(gv);
      return v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE;
    }

    /**
     * Returns whether this object's numerical value is an integer, is -(2^63) or
     * greater, and is less than 2^63.
     * @return {@code true} if this object's numerical value is an integer, is
     * -(2^63) or greater, and is less than 2^63; otherwise, {@code false}.
     */
    public boolean CanFitInInt64() {
      return this.GetNumberInterface().CanFitInInt64(this.GetValue());
    }

    /**
     * Gets a value indicating whether this object represents infinity.
     * @return {@code true} if this object represents infinity; otherwise, {@code
     * false}.
     */
    public boolean IsInfinity() {
      return this.GetNumberInterface().IsInfinity(this.GetValue());
    }

    /**
     * Gets a value indicating whether this object represents positive infinity.
     * @return {@code true} if this object represents positive infinity; otherwise,
     * {@code false}.
     */
    public boolean IsPositiveInfinity() {
      return this.GetNumberInterface().IsPositiveInfinity(this.GetValue());
    }

    /**
     * Gets a value indicating whether this object represents negative infinity.
     * @return {@code true} if this object represents negative infinity; otherwise,
     * {@code false}.
     */
    public boolean IsNegativeInfinity() {
      return this.GetNumberInterface().IsNegativeInfinity(this.GetValue());
    }

    /**
     * Gets a value indicating whether this object represents a not-a-number value.
     * @return {@code true} if this object represents a not-a-number value;
     * otherwise, {@code false}.
     */
    public boolean IsNaN() {
      return this.GetNumberInterface().IsNaN(this.GetValue());
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public EDecimal ToEDecimal() {
      return this.GetNumberInterface().AsEDecimal(this.GetValue());
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public EFloat ToEFloat() {
      return this.GetNumberInterface().AsEFloat(this.GetValue());
    }

    /**
     * Not documented yet.
     * @return The return value is not documented yet.
     */
    public ERational ToERational() {
      return this.GetNumberInterface().AsERational(this.GetValue());
    }

    /**
     * Returns the absolute value of this CBOR number.
     * @return This object's absolute value without its negative sign.
     */
    public CBORNumber Abs() {
      switch (this.kind) {
        case Integer: {
            long longValue = (((Long)this.value).longValue());
            if (longValue == Long.MIN_VALUE) {
              return FromObject(EInteger.FromInt64(longValue).Negate());
            } else {
              return longValue >= 0 ? this : new CBORNumber(
                  this.kind,
                  Math.abs(longValue));
            }
          }
        case EInteger: {
            EInteger eivalue = (EInteger)this.value;
            return eivalue.signum() >= 0 ? this : FromObject(eivalue.Abs());
          }
        default:
          return new CBORNumber(this.kind,
              this.GetNumberInterface().Abs(this.GetValue()));
      }
    }

    /**
     * Returns a CBOR number with the same value as this one but with the sign
     * reversed.
     * @return A CBOR number with the same value as this one but with the sign
     * reversed.
     */
    public CBORNumber Negate() {
      switch (this.kind) {
        case Integer: {
            long longValue = (((Long)this.value).longValue());
            if (longValue == 0) {
              return FromObject(EDecimal.NegativeZero);
            } else if (longValue == Long.MIN_VALUE) {
              return FromObject(EInteger.FromInt64(longValue).Negate());
            } else {
              return new CBORNumber(this.kind, -longValue);
            }
          }
        case EInteger: {
          EInteger eiValue = (EInteger)this.value;
          if (eiValue.isZero()) {
            return FromObject(EDecimal.NegativeZero);
          } else {
            return FromObject(eiValue.Negate());
          }
        }
        default: return new CBORNumber(this.kind,
              this.GetNumberInterface().Negate(this.GetValue()));
      }
    }

    /**
     * Returns the sum of this number and another number.
     * @param b The number to add with this one.
     * @return The sum of this number and another number.
     * @throws NullPointerException The parameter {@code b} is null.
     */
    public CBORNumber Add(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      CBORNumber a = this;
      Object objA = a.value;
      Object objB = b.value;
      Kind typeA = a.kind;
      Kind typeB = b.kind;
      if (typeA == Kind.Integer && typeB == Kind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if ((valueA < 0 && valueB < Long.MIN_VALUE - valueA) ||
          (valueA > 0 && valueB > Long.MAX_VALUE - valueA)) {
          // would overflow, convert to EInteger
          return CBORNumber.FromObject(
              EInteger.FromInt64(valueA).Add(EInteger.FromInt64(valueB)));
        }
        return new CBORNumber(Kind.Integer, valueA + valueB);
      }
      if (typeA == Kind.ERational ||
        typeB == Kind.ERational) {
        ERational e1 =
          GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return new CBORNumber(Kind.ERational, e1.Add(e2));
      }
      if (typeA == Kind.EDecimal ||
        typeB == Kind.EDecimal) {
        EDecimal e1 =
          GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        return new CBORNumber(Kind.EDecimal, e1.Add(e2));
      }
      if (typeA == Kind.EFloat || typeB == Kind.EFloat ||
        typeA == Kind.Double || typeB == Kind.Double) {
        EFloat e1 = GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        return new CBORNumber(Kind.EFloat, e1.Add(e2));
      } else {
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
        return new CBORNumber(Kind.EInteger, b1.Add(b2));
      }
    }

    /**
     * Returns a number that expresses this number minus another.
     * @param b The second operand to the subtraction.
     * @return A CBOR number that expresses this number minus the given number.
     * @throws NullPointerException The parameter {@code b} is null.
     */
    public CBORNumber Subtract(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      CBORNumber a = this;
      Object objA = a.value;
      Object objB = b.value;
      Kind typeA = a.kind;
      Kind typeB = b.kind;
      if (typeA == Kind.Integer && typeB == Kind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if ((valueB < 0 && Long.MAX_VALUE + valueB < valueA) ||
          (valueB > 0 && Long.MIN_VALUE + valueB > valueA)) {
          // would overflow, convert to EInteger
          return CBORNumber.FromObject(
              EInteger.FromInt64(valueA).Subtract(EInteger.FromInt64(
                  valueB)));
        }
        return new CBORNumber(Kind.Integer, valueA - valueB);
      }
      if (typeA == Kind.ERational || typeB == Kind.ERational) {
        ERational e1 = GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return new CBORNumber(Kind.ERational, e1.Subtract(e2));
      }
      if (typeA == Kind.EDecimal || typeB == Kind.EDecimal) {
        EDecimal e1 = GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        return new CBORNumber(Kind.EDecimal, e1.Subtract(e2));
      }
      if (typeA == Kind.EFloat || typeB == Kind.EFloat ||
        typeA == Kind.Double || typeB == Kind.Double) {
        EFloat e1 = GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        return new CBORNumber(Kind.EFloat, e1.Subtract(e2));
      } else {
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
        return new CBORNumber(Kind.EInteger, b1.Subtract(b2));
      }
    }

    /**
     * Returns a CBOR number expressing the product of this number and the given
     * number.
     * @param b The second operand to the multiplication operation.
     * @return A number expressing the product of this number and the given number.
     * @throws NullPointerException The parameter {@code b} is null.
     */
    public CBORNumber Multiply(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      CBORNumber a = this;
      Object objA = a.value;
      Object objB = b.value;
      Kind typeA = a.kind;
      Kind typeB = b.kind;
      if (typeA == Kind.Integer && typeB == Kind.Integer) {
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
      if (typeA == Kind.ERational ||
        typeB == Kind.ERational) {
        ERational e1 =
          GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return CBORNumber.FromObject(e1.Multiply(e2));
      }
      if (typeA == Kind.EDecimal ||
        typeB == Kind.EDecimal) {
        EDecimal e1 =
          GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        return CBORNumber.FromObject(e1.Multiply(e2));
      }
      if (typeA == Kind.EFloat || typeB ==
        Kind.EFloat || typeA == Kind.Double || typeB ==
        Kind.Double) {
        EFloat e1 =
          GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        return new CBORNumber(Kind.EFloat, e1.Multiply(e2));
      } else {
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
        return new CBORNumber(Kind.EInteger, b1.Multiply(b2));
      }
    }

    /**
     * Returns the quotient of this number and another number.
     * @param b The right-hand side (divisor) to the division operation.
     * @return The quotient of this number and another one.
     * @throws NullPointerException The parameter {@code b} is null.
     */
    public CBORNumber Divide(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      CBORNumber a = this;
      Object objA = a.value;
      Object objB = b.value;
      Kind typeA = a.kind;
      Kind typeB = b.kind;
      if (typeA == Kind.Integer && typeB == Kind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if (valueB == 0) {
          return (valueA == 0) ? CBORNumber.FromObject(EDecimal.NaN) :
((valueA < 0) ? CBORNumber.FromObject(EDecimal.NegativeInfinity) :

              CBORNumber.FromObject(EDecimal.PositiveInfinity));
        }
        if (valueA == Long.MIN_VALUE && valueB == -1) {
          return new CBORNumber(Kind.Integer, valueA).Negate();
        }
        long quo = valueA / valueB;
        long rem = valueA - (quo * valueB);
        return (rem == 0) ? new CBORNumber(Kind.Integer, quo) :
          new CBORNumber(Kind.ERational,
            ERational.Create(
              EInteger.FromInt64(valueA),
              EInteger.FromInt64(valueB)));
      }
      if (typeA == Kind.ERational || typeB == Kind.ERational) {
        ERational e1 = GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return new CBORNumber(Kind.ERational, e1.Divide(e2));
      }
      if (typeA == Kind.EDecimal ||
        typeB == Kind.EDecimal) {
        EDecimal e1 =
          GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        if (e1.isZero() && e2.isZero()) {
          return new CBORNumber(Kind.EDecimal, EDecimal.NaN);
        }
        EDecimal eret = e1.Divide(e2, null);
        // If either operand is infinity or NaN, the result
        // is already exact. Likewise if the result is a finite number.
        if (!e1.isFinite() || !e2.isFinite() || eret.isFinite()) {
          return new CBORNumber(Kind.EDecimal, eret);
        }
        ERational er1 = GetNumberInterface(typeA).AsERational(objA);
        ERational er2 = GetNumberInterface(typeB).AsERational(objB);
        return new CBORNumber(Kind.ERational, er1.Divide(er2));
      }
      if (typeA == Kind.EFloat || typeB ==
        Kind.EFloat || typeA == Kind.Double || typeB ==
        Kind.Double) {
        EFloat e1 =
          GetNumberInterface(typeA).AsEFloat(objA);
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
        return new CBORNumber(Kind.ERational, er1.Divide(er2));
      } else {
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
          new CBORNumber(Kind.ERational, ERational.Create(b1, b2));
      }
    }

    /**
     * Returns the remainder when this number is divided by another number.
     * @param b The right-hand side (dividend) of the remainder operation.
     * @return The remainder when this number is divided by the other number.
     * @throws NullPointerException The parameter {@code b} is null.
     */
    public CBORNumber Remainder(CBORNumber b) {
      if (b == null) {
        throw new NullPointerException("b");
      }
      Object objA = this.value;
      Object objB = b.value;
      Kind typeA = this.kind;
      Kind typeB = b.kind;
      if (typeA == Kind.Integer && typeB == Kind.Integer) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        return (valueA == Long.MIN_VALUE && valueB == -1) ?
          CBORNumber.FromObject(0) : CBORNumber.FromObject(valueA % valueB);
      }
      if (typeA == Kind.ERational ||
        typeB == Kind.ERational) {
        ERational e1 =
          GetNumberInterface(typeA).AsERational(objA);
        ERational e2 = GetNumberInterface(typeB).AsERational(objB);
        return CBORNumber.FromObject(e1.Remainder(e2));
      }
      if (typeA == Kind.EDecimal ||
        typeB == Kind.EDecimal) {
        EDecimal e1 =
          GetNumberInterface(typeA).AsEDecimal(objA);
        EDecimal e2 = GetNumberInterface(typeB).AsEDecimal(objB);
        return CBORNumber.FromObject(e1.Remainder(e2, null));
      }
      if (typeA == Kind.EFloat ||
        typeB == Kind.EFloat || typeA == Kind.Double || typeB ==
        Kind.Double) {
        EFloat e1 =
          GetNumberInterface(typeA).AsEFloat(objA);
        EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
        return CBORNumber.FromObject(e1.Remainder(e2, null));
      } else {
        EInteger b1 = GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = GetNumberInterface(typeB).AsEInteger(objB);
        return CBORNumber.FromObject(b1.Remainder(b2));
      }
    }

    /**
     * Compares two CBOR numbers. In this implementation, the two numbers'
     * mathematical values are compared. Here, NaN (not-a-number) is
     * considered greater than any number.
     * @param other A value to compare with. Can be null.
     * @return A negative number, if this value is less than the other object; or
     * 0, if both values are equal; or a positive number, if this value is
     * less than the other object or if the other object is null. This
     * implementation returns a positive number if.
     */
    public int compareTo(CBORNumber other) {
      if (other == null) {
        return 1;
      }
      if (this == other) {
        return 0;
      }
      int cmp = 0;
      Kind typeA = this.kind;
      Kind typeB = other.kind;
      Object objA = this.value;
      Object objB = other.value;
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
              double a = ((Double)objA).doubleValue();
              double b = ((Double)objB).doubleValue();
              // Treat NaN as greater than all other numbers
              cmp = Double.isNaN(a) ? (Double.isNaN(b) ? 0 : 1) : (Double.isNaN(
                  b) ? (-1) : ((a == b) ? 0 : ((a < b) ? -1 :

                      1)));
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
          // DebugUtility.Log("a=" + this + " b=" + other);
          if (typeA == Kind.ERational) {
            ERational e1 =
              GetNumberInterface(typeA).AsERational(objA);
            if (typeB == Kind.EDecimal) {
              EDecimal e2 =
                GetNumberInterface(typeB).AsEDecimal(objB);
              cmp = e1.CompareToDecimal(e2);
            } else {
              EFloat e2 = GetNumberInterface(typeB).AsEFloat(objB);
              cmp = e1.CompareToBinary(e2);
            }
          } else if (typeB == Kind.ERational) {
            ERational e2 =
              GetNumberInterface(typeB).AsERational(objB);
            if (typeA == Kind.EDecimal) {
              EDecimal e1 =
                GetNumberInterface(typeA).AsEDecimal(objA);
              cmp = e2.CompareToDecimal(e1);
              cmp = -cmp;
            } else {
              EFloat e1 =
                GetNumberInterface(typeA).AsEFloat(objA);
              cmp = e2.CompareToBinary(e1);
              cmp = -cmp;
            }
          } else if (typeA == Kind.EDecimal ||
            typeB == Kind.EDecimal) {
            EDecimal e1 = null;
            EDecimal e2 = null;
            if (typeA == Kind.EFloat) {
              EFloat ef1 = (EFloat)objA;
              e2 = (EDecimal)objB;
              cmp = e2.CompareToBinary(ef1);
              cmp = -cmp;
            } else if (typeB == Kind.EFloat) {
              EFloat ef1 = (EFloat)objB;
              e2 = (EDecimal)objA;
              cmp = e2.CompareToBinary(ef1);
            } else {
              e1 = GetNumberInterface(typeA).AsEDecimal(objA);
              e2 = GetNumberInterface(typeB).AsEDecimal(objB);
              cmp = e1.compareTo(e2);
            }
          } else if (typeA == Kind.EFloat || typeB ==
            Kind.EFloat || typeA == Kind.Double || typeB ==
            Kind.Double) {
            EFloat e1 =
              GetNumberInterface(typeA).AsEFloat(objA);
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
