package com.upokecenter.util;
/*
Written in 2014 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import com.upokecenter.numbers.*;

    /**
     * Arbitrary-precision rational number. This class cannot be inherited; this is
     * a change in version 2.0 from previous versions, where the class was
     * inadvertently left inheritable.
     */
  public final class ExtendedRational implements Comparable<ExtendedRational> {
    /**
     * Gets this object's numerator.
     * @return This object's numerator. If this object is a not-a-number value,
     * returns the diagnostic information (which will be negative if this
     * object is negative).
     */
    public final BigInteger getNumerator() {
return new BigInteger(this.er.getNumerator());
}

    /**
     * Gets this object's numerator with the sign removed.
     * @return This object's numerator. If this object is a not-a-number value,
     * returns the diagnostic information.
     */
    public final BigInteger getUnsignedNumerator() {
return new BigInteger(this.er.getUnsignedNumerator());
}

    /**
     * Gets this object's denominator.
     * @return This object's denominator.
     */
    public final BigInteger getDenominator() {
return new BigInteger(this.er.getDenominator());
}

    /**
     * Determines whether this object and another object are equal.
     * @param obj An arbitrary object.
     * @return True if the objects are equal; otherwise, false.
     */
    @Override public boolean equals(Object obj) {
      ExtendedRational bi = ((obj instanceof ExtendedRational) ? (ExtendedRational)obj : null);
      return (bi == null) ? (false) : (this.er.equals(bi.er));
    }

    /**
     * Returns the hash code for this instance.
     * @return A 32-bit hash code.
     */
    @Override public int hashCode() {
return this.er.hashCode();
}

    /**
     * Creates a number with the given numerator and denominator.
     * @param numeratorSmall A 32-bit signed integer.
     * @param denominatorSmall A 32-bit signed integer. (2).
     * @return An arbitrary-precision rational number.
     */
    public static ExtendedRational Create(int numeratorSmall,
int denominatorSmall) {
return new ExtendedRational(ERational.Create(numeratorSmall, denominatorSmall));
}

    /**
     * Creates a number with the given numerator and denominator.
     * @param numerator An arbitrary-precision integer.
     * @param denominator Another arbitrary-precision integer.
     * @return An arbitrary-precision rational number.
     */
    public static ExtendedRational Create(BigInteger numerator,
BigInteger denominator) {
  if ((numerator) == null) {
  throw new NullPointerException("numerator");
}
  if ((denominator) == null) {
  throw new NullPointerException("denominator");
}
return new ExtendedRational(ERational.Create(numerator.ei, denominator.ei));
}

    /**
     * Initializes a new instance of the arbitrary-precision rational number class.
     * @param numerator An arbitrary-precision integer.
     * @param denominator Another arbitrary-precision integer.
     * @throws java.lang.NullPointerException The parameter {@code numerator} or
     * {@code denominator} is null.
     */
    public ExtendedRational (BigInteger numerator, BigInteger denominator) {
      this.er = new ERational(numerator.ei, denominator.ei);
 }

    /**
     * Converts this object to a text string.
     * @return A string representation of this object. The result can be Infinity,
     * NaN, or sNaN (with a minus sign before it for negative values), or a
     * number of the following form: [-]numerator/denominator.
     */
    @Override public String toString() {
return this.er.toString();
}

    final ERational er;
    ExtendedRational(ERational er) {
      if ((er) == null) {
  throw new NullPointerException("er");
}
      this.er = er;
    }

    /**
     * Converts a big integer to a rational number.
     * @param bigint An arbitrary-precision integer.
     * @return The exact value of the integer as a rational number.
     */
    public static ExtendedRational FromBigInteger(BigInteger bigint) {
      return new ExtendedRational(ERational.FromBigInteger(bigint.ei));
 }

    /**
     * Converts this rational number to a decimal number.
     * @return The exact value of the rational number, or not-a-number (NaN) if the
     * result can't be exact because it has a nonterminating decimal
     * expansion.
     */
    public ExtendedDecimal ToExtendedDecimal() {
return new ExtendedDecimal(this.er.ToExtendedDecimal());
}

    /**
     * Converts a 32-bit floating-point number to a rational number. This method
     * computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the number to a
     * string.
     * @param flt A 32-bit floating-point number.
     * @return A rational number with the same value as {@code flt}.
     */
    public static ExtendedRational FromSingle(float flt) {
return new ExtendedRational(ERational.FromSingle(flt));
}

    /**
     * Converts a 64-bit floating-point number to a rational number. This method
     * computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the number to a
     * string.
     * @param flt A 64-bit floating-point number.
     * @return A rational number with the same value as {@code flt}.
     */
    public static ExtendedRational FromDouble(double flt) {
return new ExtendedRational(ERational.FromDouble(flt));
}

    /**
     * Creates a not-a-number arbitrary-precision rational number.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @return An arbitrary-precision rational number.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null.
     * @throws IllegalArgumentException The parameter {@code diag} is less than 0.
     */
    public static ExtendedRational CreateNaN(BigInteger diag) {
  if ((diag) == null) {
  throw new NullPointerException("diag");
}
return new ExtendedRational(ERational.CreateNaN(diag.ei));
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
     */
    public static ExtendedRational CreateNaN(BigInteger diag,
boolean signaling,
boolean negative) {
  if ((diag) == null) {
  throw new NullPointerException("diag");
}
return new ExtendedRational(ERational.CreateNaN(diag.ei, signaling, negative));
}

    /**
     *
     * @param ef An arbitrary-precision binary float.
     * @return An arbitrary-precision rational number.
     * @throws java.lang.NullPointerException The parameter {@code ef} is null.
     */
    public static ExtendedRational FromExtendedFloat(ExtendedFloat ef) {
  if ((ef) == null) {
  throw new NullPointerException("ef");
}
return new ExtendedRational(ERational.FromExtendedFloat(ef.ef));
}

    /**
     *
     * @param ef An arbitrary-precision decimal object.
     * @return An arbitrary-precision rational number.
     * @throws java.lang.NullPointerException The parameter {@code ef} is null.
     */
    public static ExtendedRational FromExtendedDecimal(ExtendedDecimal ef) {
  if ((ef) == null) {
  throw new NullPointerException("ef");
}
return new ExtendedRational(ERational.FromExtendedDecimal(ef.ed));
}

    /**
     * Converts this rational number to a decimal number and rounds the result to
     * the given precision.
     * @param ctx A PrecisionContext object.
     * @return An arbitrary-precision decimal.
     */
    public ExtendedDecimal ToExtendedDecimal(PrecisionContext ctx) {
return new ExtendedDecimal(this.er.ToExtendedDecimal(ctx == null ? null :
  ctx.ec));
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
     */
    public ExtendedDecimal ToExtendedDecimalExactIfPossible(PrecisionContext
      ctx) {
return new
  ExtendedDecimal(this.er.ToExtendedDecimalExactIfPossible(ctx == null ? null:
  ctx.ec));
}

    /**
     * Converts this rational number to a binary number.
     * @return The exact value of the rational number, or not-a-number (NaN) if the
     * result can't be exact because it has a nonterminating binary
     * expansion.
     */
    public ExtendedFloat ToExtendedFloat() {
return new ExtendedFloat(this.er.ToExtendedFloat());
}

    /**
     * Converts this rational number to a binary number and rounds the result to
     * the given precision.
     * @param ctx A PrecisionContext object.
     * @return An arbitrary-precision binary float.
     */
    public ExtendedFloat ToExtendedFloat(PrecisionContext ctx) {
return new ExtendedFloat(this.er.ToExtendedFloat(ctx == null ? null : ctx.ec));
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
     */
    public ExtendedFloat ToExtendedFloatExactIfPossible(PrecisionContext ctx) {
return new ExtendedFloat(this.er.ToExtendedFloatExactIfPossible(ctx == null ?
  null : ctx.ec));
}

    /**
     * Gets a value indicating whether this object is finite (not infinity or NaN).
     * @return True if this object is finite (not infinity or NaN); otherwise,
     * false.
     */
    public final boolean isFinite() {
return this.er.isFinite();
}

    /**
     * Converts this value to an arbitrary-precision integer. Any fractional part
     * in this value will be discarded when converting to a big integer.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     */
    public BigInteger ToBigInteger() {
return new BigInteger(this.er.ToBigInteger());
}

    /**
     * Converts this value to an arbitrary-precision integer, checking whether the
     * value is an exact integer.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     * @throws ArithmeticException This object's value is not an exact integer.
     */
    public BigInteger ToBigIntegerExact() {
return new BigInteger(this.er.ToBigIntegerExact());
}

    /**
     * Not documented yet.
     * @param smallint A 32-bit signed integer.
     * @return An arbitrary-precision rational number.
     */
    public static ExtendedRational FromInt32(int smallint) {
return new ExtendedRational(ERational.FromInt32(smallint));
}

    /**
     * Not documented yet.
     * @param longInt A 64-bit signed integer.
     * @return An arbitrary-precision rational number.
     */
    public static ExtendedRational FromInt64(long longInt) {
return new ExtendedRational(ERational.FromInt64(longInt));
}

    /**
     * Converts this value to a 64-bit floating-point number. The half-even
     * rounding mode is used.
     * @return The closest 64-bit floating-point number to this value. The return
     * value can be positive infinity or negative infinity if this value
     * exceeds the range of a 64-bit floating point number.
     */
    public double ToDouble() {
return this.er.ToDouble();
}

    /**
     * Converts this value to a 32-bit floating-point number. The half-even
     * rounding mode is used.
     * @return The closest 32-bit floating-point number to this value. The return
     * value can be positive infinity or negative infinity if this value
     * exceeds the range of a 32-bit floating point number.
     */
    public float ToSingle() {
return this.er.ToSingle();
}

    /**
     * Finds the absolute value of this rational number.
     * @return An arbitrary-precision rational number.
     */
    public ExtendedRational Abs() {
return new ExtendedRational(this.er.Abs());
}

    /**
     * Not documented yet.
     * @return An arbitrary-precision rational number.
     */
    public ExtendedRational Negate() {
return new ExtendedRational(this.er.Negate());
}

    /**
     * Gets a value indicating whether this object's value equals 0.
     * @return True if this object's value equals 0; otherwise, false.
     */
    public final boolean isZero() {
return this.er.signum() == 0;
}

    /**
     * Gets the sign of this rational number.
     * @return Zero if this value is zero or negative zero; -1 if this value is
     * less than 0; and 1 if this value is greater than 0.
     */
    public final int signum() {
return this.er.signum();
}

    /**
     * Compares an arbitrary-precision rational number with this instance.
     * @param other An arbitrary-precision rational number.
     * @return Zero if the values are equal; a negative number if this instance is
     * less, or a positive number if this instance is greater.
     */
    public int compareTo(ExtendedRational other) {
  if ((other) == null) {
  throw new NullPointerException("other");
}
return this.er.compareTo(other.er);
}

    /**
     * Compares an arbitrary-precision binary float with this instance.
     * @param other An arbitrary-precision binary float.
     * @return Zero if the values are equal; a negative number if this instance is
     * less, or a positive number if this instance is greater.
     */
    public int CompareToBinary(ExtendedFloat other) {
  if ((other) == null) {
  throw new NullPointerException("other");
}
return this.er.CompareToBinary(other.ef);
}

    /**
     * Compares an arbitrary-precision decimal object with this instance.
     * @param other An arbitrary-precision decimal object.
     * @return Zero if the values are equal; a negative number if this instance is
     * less, or a positive number if this instance is greater.
     */
    public int CompareToDecimal(ExtendedDecimal other) {
  if ((other) == null) {
  throw new NullPointerException("other");
}
return this.er.CompareToDecimal(other.ed);
}

    /**
     * Not documented yet.
     * @param other An arbitrary-precision rational number.
     * @return A Boolean object.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     */
    public boolean equals(ExtendedRational other) {
  if ((other) == null) {
  throw new NullPointerException("other");
}
return this.er.equals(other.er);
}

    /**
     * Returns whether this object is negative infinity.
     * @return True if this object is negative infinity; otherwise, false.
     */
    public boolean IsNegativeInfinity() {
return this.er.IsNegativeInfinity();
}

    /**
     * Returns whether this object is positive infinity.
     * @return True if this object is positive infinity; otherwise, false.
     */
    public boolean IsPositiveInfinity() {
return this.er.IsPositiveInfinity();
}

    /**
     * Returns whether this object is a not-a-number value.
     * @return True if this object is a not-a-number value; otherwise, false.
     */
    public boolean IsNaN() {
return this.er.IsNaN();
}

    /**
     * Gets a value indicating whether this object's value is negative (including
     * negative zero).
     * @return True if this object's value is negative; otherwise, false.
     */
    public final boolean isNegative() {
return this.er.isNegative();
}

    /**
     * Gets a value indicating whether this object's value is infinity.
     * @return True if this object's value is infinity; otherwise, false.
     */
    public boolean IsInfinity() {
return this.er.IsInfinity();
}

    /**
     * Returns whether this object is a quiet not-a-number value.
     * @return True if this object is a quiet not-a-number value; otherwise, false.
     */
    public boolean IsQuietNaN() {
return this.er.IsQuietNaN();
}

    /**
     * Returns whether this object is a signaling not-a-number value (which causes
     * an error if the value is passed to any arithmetic operation in this
     * class).
     * @return True if this object is a signaling not-a-number value (which causes
     * an error if the value is passed to any arithmetic operation in this
     * class); otherwise, false.
     */
    public boolean IsSignalingNaN() {
return this.er.IsSignalingNaN();
}

    /**
     * A not-a-number value.
     */
    public static final ExtendedRational NaN =
      new ExtendedRational(ERational.NaN);

    /**
     * A signaling not-a-number value.
     */
    public static final ExtendedRational SignalingNaN = new
      ExtendedRational(ERational.SignalingNaN);

    /**
     * Positive infinity, greater than any other number.
     */
    public static final ExtendedRational PositiveInfinity = new
      ExtendedRational(ERational.PositiveInfinity);

    /**
     * Negative infinity, less than any other number.
     */
    public static final ExtendedRational NegativeInfinity = new
      ExtendedRational(ERational.NegativeInfinity);

    /**
     * Adds two rational numbers.
     * @param otherValue Another arbitrary-precision rational number.
     * @return The sum of the two numbers. returns not-a-number (NaN) if either
     * operand is NaN.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     */
    public ExtendedRational Add(ExtendedRational otherValue) {
  if ((otherValue) == null) {
  throw new NullPointerException("otherValue");
}
return new ExtendedRational(this.er.Add(otherValue.er));
}

    /**
     * Subtracts an arbitrary-precision rational number from this instance.
     * @param otherValue An arbitrary-precision rational number.
     * @return The difference of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     */
    public ExtendedRational Subtract(ExtendedRational otherValue) {
  if ((otherValue) == null) {
  throw new NullPointerException("otherValue");
}
return new ExtendedRational(this.er.Subtract(otherValue.er));
}

    /**
     * Multiplies this instance by the value of an arbitrary-precision rational
     * number.
     * @param otherValue An arbitrary-precision rational number.
     * @return The product of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     */
    public ExtendedRational Multiply(ExtendedRational otherValue) {
  if ((otherValue) == null) {
  throw new NullPointerException("otherValue");
}
return new ExtendedRational(this.er.Multiply(otherValue.er));
}

    /**
     * Divides this instance by the value of an arbitrary-precision rational number
     * object.
     * @param otherValue An arbitrary-precision rational number.
     * @return The quotient of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     */
    public ExtendedRational Divide(ExtendedRational otherValue) {
  if ((otherValue) == null) {
  throw new NullPointerException("otherValue");
}
return new ExtendedRational(this.er.Divide(otherValue.er));
}

    /**
     * Finds the remainder that results when this instance is divided by the value
     * of an arbitrary-precision rational number.
     * @param otherValue An arbitrary-precision rational number.
     * @return The remainder of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     */
    public ExtendedRational Remainder(ExtendedRational otherValue) {
  if ((otherValue) == null) {
  throw new NullPointerException("otherValue");
}
return new ExtendedRational(this.er.Remainder(otherValue.er));
}

    /**
     * A rational number for zero.
     */
public static final ExtendedRational Zero =
      FromBigInteger(BigInteger.valueOf(0));

    /**
     * A rational number for negative zero.
     */
    public static final ExtendedRational NegativeZero =
      new ExtendedRational(ERational.NegativeZero);

    /**
     * The rational number one.
     */
  public static final ExtendedRational One =
      FromBigInteger(BigInteger.valueOf(1));

    /**
     * The rational number ten.
     */
  public static final ExtendedRational Ten =
      FromBigInteger(BigInteger.valueOf(10));
  }
