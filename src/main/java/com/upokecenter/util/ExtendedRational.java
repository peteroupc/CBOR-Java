package com.upokecenter.util;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.numbers.*;

    /**
     * <p><b>This class is largely obsolete. It will be replaced by a new version
     * of this class in a different namespace/package and library, called
     * <code>PeterO.Numbers.ERational</code> in the <a
  * href='https://www.nuget.org/packages/PeterO.Numbers'><code>PeterO.Numbers</code></a>
     * library (in .NET), or <code>com.upokecenter.numbers.ERational</code> in the
     * <a
  * href='https://github.com/peteroupc/numbers-java'><code>com.github.peteroupc/numbers</code></a>
     * artifact (in Java). This new class can be used in the
     * <code>CBORObject.FromObject(Object)</code> method (by including the new
     * library in your code, among other things), but this version of the
     * CBOR library doesn't include any methods that explicitly take an
     * <code>ERational</code> as a parameter or return
     * value.</b></p>Arbitrary-precision rational number. This class cannot
     * be inherited; this is a change in version 2.0 from previous versions,
     * where the class was inadvertently left inheritable. <p><b>Thread
     * safety:</b>Instances of this class are immutable, so they are
     * inherently safe for use by multiple threads. Multiple instances of
     * this object with the same properties are interchangeable, so they
     * should not be compared using the "==" operator (which only checks if
     * each side of the operator is the same instance).</p>
     */
  public final class ExtendedRational implements Comparable<ExtendedRational> {
    /**
     * A not-a-number value.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final ExtendedRational NaN =
      new ExtendedRational(ERational.NaN);

    /**
     * Negative infinity, less than any other number.
     */
    public static final ExtendedRational NegativeInfinity = new
      ExtendedRational(ERational.NegativeInfinity);

    /**
     * A rational number for negative zero.
     */
    public static final ExtendedRational NegativeZero =
      new ExtendedRational(ERational.NegativeZero);

    /**
     * The rational number one.
     */
    public static final ExtendedRational One =
      FromBigIntegerInternal(BigInteger.valueOf(1));

    /**
     * Positive infinity, greater than any other number.
     */
    public static final ExtendedRational PositiveInfinity = new
      ExtendedRational(ERational.PositiveInfinity);

    /**
     * A signaling not-a-number value.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final ExtendedRational SignalingNaN = new
      ExtendedRational(ERational.SignalingNaN);

    /**
     * The rational number ten.
     */
    public static final ExtendedRational Ten =
      FromBigIntegerInternal(BigInteger.valueOf(10));

    /**
     * A rational number for zero.
     */
    public static final ExtendedRational Zero =
      FromBigIntegerInternal(BigInteger.valueOf(0));

    private final ERational er;

    /**
     * Initializes a new instance of the {@link com.upokecenter.ExtendedRational}
     * class.
     * @param numerator An arbitrary-precision integer.
     * @param denominator Another arbitrary-precision integer.
     * @throws java.lang.NullPointerException The parameter {@code numerator} or
     * {@code denominator} is null.
     */
    public ExtendedRational(BigInteger numerator, BigInteger denominator) {
      this.er = new ERational(numerator.getEi(), denominator.getEi());
    }

    ExtendedRational(ERational er) {
      if (er == null) {
        throw new NullPointerException("er");
      }
      this.er = er;
    }

    /**
     * Gets this object's denominator.
     * @return This object's denominator.
     */
    public final BigInteger getDenominator() {
        return new BigInteger(this.getEr().getDenominator());
      }

    /**
     * Gets a value indicating whether this object is finite (not infinity or NaN).
     * @return {@code true} if this object is finite (not infinity or NaN);
     * otherwise, {@code false}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isFinite() {
        return this.getEr().isFinite();
      }

    /**
     * Gets a value indicating whether this object's value is negative (including
     * negative zero).
     * @return {@code true} if this object's value is negative; otherwise, {@code
     * false}.
     */
    public final boolean isNegative() {
        return this.getEr().isNegative();
      }

    /**
     * Gets a value indicating whether this object's value equals 0.
     * @return {@code true} if this object's value equals 0; otherwise, {@code
     * false}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isZero() {
        return this.getEr().isZero();
      }

    /**
     * Gets this object's numerator.
     * @return This object's numerator. If this object is a not-a-number value,
     * returns the diagnostic information (which will be negative if this
     * object is negative).
     */
    public final BigInteger getNumerator() {
        return new BigInteger(this.getEr().getNumerator());
      }

    /**
     * Gets the sign of this rational number.
     * @return Zero if this value is zero or negative zero; -1 if this value is
     * less than 0; and 1 if this value is greater than 0.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final int signum() {
        return this.getEr().signum();
      }

    /**
     * Gets this object's numerator with the sign removed.
     * @return This object's numerator. If this object is a not-a-number value,
     * returns the diagnostic information.
     */
    public final BigInteger getUnsignedNumerator() {
        return new BigInteger(this.getEr().getUnsignedNumerator());
      }

    final ERational getEr() {
        return this.er;
      }

    /**
     * Creates a rational number with the given numerator and denominator.
     * @param numeratorSmall A 32-bit signed integer.
     * @param denominatorSmall A 32-bit signed integer. (2).
     * @return An arbitrary-precision rational number.
     */
    public static ExtendedRational Create(
  int numeratorSmall,
  int denominatorSmall) {
      return new ExtendedRational(
  ERational.Create(
  numeratorSmall,
  denominatorSmall));
    }

    /**
     * Creates a rational number with the given numerator and denominator.
     * @param numerator An arbitrary-precision integer.
     * @param denominator Another arbitrary-precision integer.
     * @return An arbitrary-precision rational number.
     * @throws java.lang.NullPointerException The parameter {@code numerator} or
     * {@code denominator} is null.
     */
    public static ExtendedRational Create(
  BigInteger numerator,
  BigInteger denominator) {
      if (numerator == null) {
        throw new NullPointerException("numerator");
      }
      if (denominator == null) {
        throw new NullPointerException("denominator");
      }
      return new ExtendedRational(
  ERational.Create(
  numerator.getEi(),
  denominator.getEi()));
    }

    /**
     * Creates a not-a-number arbitrary-precision rational number.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @return An arbitrary-precision rational number.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null.
     * @throws IllegalArgumentException The parameter {@code diag} is less than 0.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational CreateNaN(BigInteger diag) {
      if (diag == null) {
        throw new NullPointerException("diag");
      }
      return new ExtendedRational(ERational.CreateNaN(diag.getEi()));
    }

    /**
     * Creates a not-a-number arbitrary-precision rational number.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @param signaling Whether the return value will be signaling (true) or quiet
     * (false).
     * @param negative Whether the return value is negative.
     * @return An arbitrary-precision rational number.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null.
     * @throws IllegalArgumentException The parameter {@code diag} is less than 0.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational CreateNaN(
  BigInteger diag,
  boolean signaling,
  boolean negative) {
      if (diag == null) {
        throw new NullPointerException("diag");
      }
      return new ExtendedRational(
  ERational.CreateNaN(
  diag.getEi(),
  signaling,
  negative));
    }

    /**
     * Converts a big integer to a rational number.
     * @param bigint An arbitrary-precision integer.
     * @return The exact value of the integer as a rational number.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational FromBigInteger(BigInteger bigint) {
      return FromBigIntegerInternal(bigint);
    }

    /**
     * Converts a 64-bit floating-point number to a rational number. This method
     * computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the number to a
     * string.
     * @param flt A 64-bit floating-point number.
     * @return A rational number with the same value as {@code flt}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational FromDouble(double flt) {
      return new ExtendedRational(ERational.FromDouble(flt));
    }

    /**
     * Converts an arbitrary-precision decimal number to a rational number.
     * @param ef An arbitrary-precision decimal number.
     * @return An arbitrary-precision rational number.
     * @throws java.lang.NullPointerException The parameter {@code ef} is null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational FromExtendedDecimal(ExtendedDecimal ef) {
      if (ef == null) {
        throw new NullPointerException("ef");
      }
      return new ExtendedRational(ERational.FromExtendedDecimal(ef.getEd()));
    }

    /**
     * Converts an arbitrary-precision binary float to a rational number.
     * @param ef An arbitrary-precision binary float.
     * @return An arbitrary-precision rational number.
     * @throws java.lang.NullPointerException The parameter {@code ef} is null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational FromExtendedFloat(ExtendedFloat ef) {
      if (ef == null) {
        throw new NullPointerException("ef");
      }
      return new ExtendedRational(ERational.FromExtendedFloat(ef.getEf()));
    }

    /**
     * Converts a 32-bit signed integer to a rational number.
     * @param smallint A 32-bit signed integer.
     * @return An arbitrary-precision rational number.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational FromInt32(int smallint) {
      return new ExtendedRational(ERational.FromInt32(smallint));
    }

    /**
     * Converts a 64-bit signed integer to a rational number.
     * @param longInt A 64-bit signed integer.
     * @return An arbitrary-precision rational number.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational FromInt64(long longInt) {
      return new ExtendedRational(ERational.FromInt64(longInt));
    }

    /**
     * Converts a 32-bit floating-point number to a rational number. This method
     * computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the number to a
     * string.
     * @param flt A 32-bit floating-point number.
     * @return A rational number with the same value as {@code flt}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedRational FromSingle(float flt) {
      return new ExtendedRational(ERational.FromSingle(flt));
    }

    /**
     * Finds the absolute value of this rational number.
     * @return An arbitrary-precision rational number.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedRational Abs() {
      return new ExtendedRational(this.getEr().Abs());
    }

    /**
     * Adds two rational numbers.
     * @param otherValue Another arbitrary-precision rational number.
     * @return The sum of the two numbers. Returns not-a-number (NaN) if either
     * operand is NaN.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedRational Add(ExtendedRational otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedRational(this.getEr().Add(otherValue.getEr())).Simplify();
    }

    /**
     * Compares an arbitrary-precision rational number with this instance.
     * @param other An arbitrary-precision rational number.
     * @return Zero if the values are equal; a negative number if this instance is
     * less, or a positive number if this instance is greater.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public int compareTo(ExtendedRational other) {
      if (other == null) {
        throw new NullPointerException("other");
      }
      return this.getEr().compareTo(other.getEr());
    }

    /**
     * Compares an arbitrary-precision binary float with this instance.
     * @param other An arbitrary-precision binary float.
     * @return Zero if the values are equal; a negative number if this instance is
     * less, or a positive number if this instance is greater.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public int CompareToBinary(ExtendedFloat other) {
      if (other == null) {
        throw new NullPointerException("other");
      }
      return this.getEr().CompareToBinary(other.getEf());
    }

    /**
     * Compares an arbitrary-precision decimal number with this instance.
     * @param other An arbitrary-precision decimal number.
     * @return Zero if the values are equal; a negative number if this instance is
     * less, or a positive number if this instance is greater.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public int CompareToDecimal(ExtendedDecimal other) {
      if (other == null) {
        throw new NullPointerException("other");
      }
      return this.getEr().CompareToDecimal(other.getEd());
    }

    /**
     * Divides this instance by the value of an arbitrary-precision rational number
     * object.
     * @param otherValue An arbitrary-precision rational number.
     * @return The quotient of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedRational Divide(ExtendedRational otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedRational(this.getEr().Divide(otherValue.getEr())).Simplify();
    }

    /**
     * Determines whether this object and another object are equal.
     * @param obj An arbitrary object.
     * @return {@code true} if the objects are equal; otherwise, {@code false}.
     */
    @Override public boolean equals(Object obj) {
      ExtendedRational bi = ((obj instanceof ExtendedRational) ? (ExtendedRational)obj : null);
      return (bi == null) ? false : this.getEr().equals(bi.getEr());
    }

    /**
     * Returns whether this object's properties are equal to the properties of
     * another rational number object.
     * @param other Another arbitrary-precision rational number.
     * @return {@code true} if this object's properties are equal to the properties
     * of another rational number object; otherwise, {@code false}.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean equals(ExtendedRational other) {
      if (other == null) {
        throw new NullPointerException("other");
      }
      return this.getEr().equals(other.getEr());
    }

    /**
     * Returns the hash code for this instance.
     * @return A 32-bit hash code.
     */
    @Override public int hashCode() {
      return this.getEr().hashCode();
    }

    /**
     * Gets a value indicating whether this object's value is infinity.
     * @return {@code true} if this object's value is infinity; otherwise, {@code
     * false}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsInfinity() {
      return this.getEr().IsInfinity();
    }

    /**
     * Returns whether this object is a not-a-number value.
     * @return {@code true} if this object is a not-a-number value; otherwise,
     * {@code false}.
     */
    public boolean IsNaN() {
      return this.getEr().IsNaN();
    }

    /**
     * Returns whether this object is negative infinity.
     * @return {@code true} if this object is negative infinity; otherwise, {@code
     * false}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsNegativeInfinity() {
      return this.getEr().IsNegativeInfinity();
    }

    /**
     * Returns whether this object is positive infinity.
     * @return {@code true} if this object is positive infinity; otherwise, {@code
     * false}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsPositiveInfinity() {
      return this.getEr().IsPositiveInfinity();
    }

    /**
     * Returns whether this object is a quiet not-a-number value.
     * @return {@code true} if this object is a quiet not-a-number value;
     * otherwise, {@code false}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsQuietNaN() {
      return this.getEr().IsQuietNaN();
    }

    /**
     * Returns whether this object is a signaling not-a-number value (which causes
     * an error if the value is passed to any arithmetic operation in this
     * class).
     * @return {@code true} if this object is a signaling not-a-number value (which
     * causes an error if the value is passed to any arithmetic operation in
     * this class); otherwise, {@code false}.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsSignalingNaN() {
      return this.getEr().IsSignalingNaN();
    }

    /**
     * Multiplies this instance by the value of an arbitrary-precision rational
     * number.
     * @param otherValue An arbitrary-precision rational number.
     * @return The product of the two numbers.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedRational Multiply(ExtendedRational otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedRational(this.getEr().Multiply(otherValue.getEr())).Simplify();
    }

    /**
     * Finds a rational number with the same value as this object but with the sign
     * reversed.
     * @return The negated form of this rational number.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedRational Negate() {
      return new ExtendedRational(this.getEr().Negate());
    }

    /**
     * Finds the remainder that results when this instance is divided by the value
     * of an arbitrary-precision rational number.
     * @param otherValue An arbitrary-precision rational number.
     * @return The remainder of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedRational Remainder(ExtendedRational otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedRational(this.getEr().Remainder(otherValue.getEr())).Simplify();
    }

    /**
     * Subtracts an arbitrary-precision rational number from this instance.
     * @param otherValue An arbitrary-precision rational number.
     * @return The difference of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedRational Subtract(ExtendedRational otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedRational(this.getEr().Subtract(otherValue.getEr())).Simplify();
    }

    /**
     * Converts this value to an arbitrary-precision integer. Any fractional part
     * in this value will be discarded when converting to a big integer.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public BigInteger ToBigInteger() {
      return new BigInteger(this.getEr().ToEInteger());
    }

    /**
     * Converts this value to an arbitrary-precision integer, checking whether the
     * value is an exact integer.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     * @throws ArithmeticException This object's value is not an exact integer.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public BigInteger ToBigIntegerExact() {
      return new BigInteger(this.getEr().ToEIntegerExact());
    }

    /**
     * Converts this value to a 64-bit floating-point number. The half-even
     * rounding mode is used.
     * @return The closest 64-bit floating-point number to this value. The return
     * value can be positive infinity or negative infinity if this value
     * exceeds the range of a 64-bit floating point number.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public double ToDouble() {
      return this.getEr().ToDouble();
    }

    /**
     * Converts this rational number to a decimal number.
     * @return The exact value of the rational number, or not-a-number (NaN) if the
     * result can't be exact because it has a nonterminating decimal
     * expansion.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal ToExtendedDecimal() {
      return new ExtendedDecimal(this.getEr().ToExtendedDecimal());
    }

    /**
     * Converts this rational number to a decimal number and rounds the result to
     * the given precision.
     * @param ctx A PrecisionContext object.
     * @return An arbitrary-precision decimal.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal ToExtendedDecimal(PrecisionContext ctx) {
      return new ExtendedDecimal(this.getEr().ToExtendedDecimal(ctx == null ? null :
        ctx.getEc()));
    }

    /**
     * Converts this rational number to a decimal number, but if the result would
     * have a nonterminating decimal expansion, rounds that result to the
     * given precision.
     * @param ctx A precision context object to control the precision. The rounding
     * and exponent range settings of this context are ignored. This context
     * will be used only if the exact result would have a nonterminating
     * decimal expansion. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null, in which case this
     * method is the same as ToExtendedDecimal.
     * @return An arbitrary-precision decimal.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal ToExtendedDecimalExactIfPossible(PrecisionContext
      ctx) {
      return new
  ExtendedDecimal(this.getEr().ToExtendedDecimalExactIfPossible(ctx == null ?
          null : ctx.getEc()));
    }

    /**
     * Converts this rational number to a binary number.
     * @return The exact value of the rational number, or not-a-number (NaN) if the
     * result can't be exact because it has a nonterminating binary
     * expansion.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat ToExtendedFloat() {
      return new ExtendedFloat(this.getEr().ToExtendedFloat());
    }

    /**
     * Converts this rational number to a binary number and rounds the result to
     * the given precision.
     * @param ctx A PrecisionContext object.
     * @return An arbitrary-precision binary float.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat ToExtendedFloat(PrecisionContext ctx) {
      return new ExtendedFloat(this.getEr().ToExtendedFloat(ctx == null ? null :
              ctx.getEc()));
    }

    /**
     * Converts this rational number to a binary number, but if the result would
     * have a nonterminating binary expansion, rounds that result to the
     * given precision.
     * @param ctx A precision context object to control the precision. The rounding
     * and exponent range settings of this context are ignored. This context
     * will be used only if the exact result would have a nonterminating
     * binary expansion. If HasFlags of the context is true, will also store
     * the flags resulting from the operation (the flags are in addition to
     * the pre-existing flags). Can be null, in which case this method is
     * the same as ToExtendedFloat.
     * @return An arbitrary-precision binary float.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat ToExtendedFloatExactIfPossible(PrecisionContext ctx) {
      return new ExtendedFloat(this.getEr().ToExtendedFloatExactIfPossible(ctx ==
            null ? null : ctx.getEc()));
    }

    /**
     * Converts this value to a 32-bit floating-point number. The half-even
     * rounding mode is used.
     * @return The closest 32-bit floating-point number to this value. The return
     * value can be positive infinity or negative infinity if this value
     * exceeds the range of a 32-bit floating point number.
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public float ToSingle() {
      return this.getEr().ToSingle();
    }

    /**
     * Converts this object to a text string.
     * @return A string representation of this object. The result can be Infinity,
     * NaN, or sNaN (with a minus sign before it for negative values), or a
     * number of the following form: [-]numerator/denominator.
     */
    @Override public String toString() {
      return this.getEr().toString();
    }

    static ERational FromLegacy(ExtendedRational bei) {
      return bei.getEr();
    }

    static ExtendedRational ToLegacy(ERational ei) {
      return new ExtendedRational(ei);
    }

    private static ExtendedRational FromBigIntegerInternal(BigInteger bigint) {
      return new ExtendedRational(ERational.FromEInteger(bigint.getEi()));
    }

    private ExtendedRational Simplify() {
      // TODO: Don't simplify automatically in version 3.0
      if (this.isFinite()) {
        BigInteger num = (this.getNumerator()).abs();
        BigInteger den = (this.getDenominator()).abs();
        boolean neg = this.isNegative();
        int lowBit = num.getLowBit();
        lowBit = Math.min(lowBit, den.getLowBit());
        if (lowBit > 0) {
          num = num.shiftRight(lowBit);
          den = den.shiftRight(lowBit);
          if (neg) {
            num = num.negate();
          }
          return Create(num, den);
        }
      }
      return this;
    }
  }
