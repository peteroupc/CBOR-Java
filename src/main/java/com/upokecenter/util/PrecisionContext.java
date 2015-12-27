package com.upokecenter.util;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import com.upokecenter.numbers.*;

    /**
     * Contains parameters for controlling the precision, rounding, and exponent
     * range of arbitrary-precision numbers.
     */
  public class PrecisionContext {
    // TODO: Add 'final' to PrecisionContext class in version 3

    /**
     * Signals that the exponent was adjusted to fit the exponent range.
     */
    public static final int FlagClamped = 32;

    /**
     * Signals a division of a nonzero number by zero.
     */
    public static final int FlagDivideByZero = 128;

    /**
     * Signals that the result was rounded to a different mathematical value, but
     * as close as possible to the original.
     */
    public static final int FlagInexact = 1;

    /**
     * Signals an invalid operation.
     */
    public static final int FlagInvalid = 64;

    /**
     * Signals that an operand was rounded to a different mathematical value before
     * an operation.
     */
    public static final int FlagLostDigits = 256;

    /**
     * Signals that the result is non-zero and the exponent is higher than the
     * highest exponent allowed.
     */
    public static final int FlagOverflow = 16;

    /**
     * Signals that the result was rounded to fit the precision; either the value
     * or the exponent may have changed from the original.
     */
    public static final int FlagRounded = 2;

    /**
     * Signals that the result&#x27;s exponent, before rounding, is lower than the
     * lowest exponent allowed.
     */
    public static final int FlagSubnormal = 4;

    /**
     * Signals that the result&#x27;s exponent, before rounding, is lower than the
     * lowest exponent allowed, and the result was rounded to a different
     * mathematical value, but as close as possible to the original.
     */
    public static final int FlagUnderflow = 8;

    final EContext ec;

    PrecisionContext(EContext ec) {
      this.ec = ec;
    }

    /**
     * Basic precision context, 9 digits precision, rounding mode half-up,
     * unlimited exponent range. The default rounding mode is HalfUp.
     */

    public static final PrecisionContext Basic =
      new PrecisionContext(EContext.Basic);

    /**
     * Precision context for Java's BigDecimal format. The default rounding mode is
     * HalfUp.
     */

    public static final PrecisionContext BigDecimalJava =
      new PrecisionContext(EContext.BigDecimalJava);

    /**
     * Precision context for the IEEE-754-2008 binary128 format, 113 bits
     * precision. The default rounding mode is HalfEven.
     */

    public static final PrecisionContext Binary128 =
      new PrecisionContext(EContext.Binary128);

    /**
     * Precision context for the IEEE-754-2008 binary16 format, 11 bits precision.
     * The default rounding mode is HalfEven.
     */

    public static final PrecisionContext Binary16 =
      new PrecisionContext(EContext.Binary16);

    /**
     * Precision context for the IEEE-754-2008 binary32 format, 24 bits precision.
     * The default rounding mode is HalfEven.
     */

    public static final PrecisionContext Binary32 =
      new PrecisionContext(EContext.Binary32);

    /**
     * Precision context for the IEEE-754-2008 binary64 format, 53 bits precision.
     * The default rounding mode is HalfEven.
     */

    public static final PrecisionContext Binary64 =
      new PrecisionContext(EContext.Binary64);

    /**
     * Precision context for the Common Language Infrastructure (.NET Framework)
     * decimal format, 96 bits precision, and a valid exponent range of -28
     * to 0. The default rounding mode is HalfEven.
     */

    public static final PrecisionContext CliDecimal =
      new PrecisionContext(EContext.CliDecimal);

    /**
     * Precision context for the IEEE-754-2008 decimal128 format. The default
     * rounding mode is HalfEven.
     */

    public static final PrecisionContext Decimal128 =
      new PrecisionContext(EContext.Decimal128);

    /**
     * Precision context for the IEEE-754-2008 decimal32 format. The default
     * rounding mode is HalfEven.
     */

    public static final PrecisionContext Decimal32 =
      new PrecisionContext(EContext.Decimal32);

    /**
     * Precision context for the IEEE-754-2008 decimal64 format. The default
     * rounding mode is HalfEven.
     */

    public static final PrecisionContext Decimal64 =
      new PrecisionContext(EContext.Decimal64);

    /**
     * Precision context for Java's BigDecimal format. The default rounding mode is
     * HalfUp.
     * @deprecated This context had the wrong settings in previous versions. Use BigDecimalJava
* instead.
 */
@Deprecated

    public static final PrecisionContext JavaBigDecimal = BigDecimalJava;

    /**
     * No specific limit on precision. Rounding mode HalfUp.
     */

    public static final PrecisionContext Unlimited =
      PrecisionContext.ForPrecision(0);

    /**
     * Initializes a new instance of the PrecisionContext class. HasFlags will be
     * set to false.
     * @param precision Not documented yet.
     * @param rounding Not documented yet.
     * @param exponentMinSmall Not documented yet. (3).
     * @param exponentMaxSmall Not documented yet. (4).
     * @param clampNormalExponents Not documented yet. (5).
     */
    public PrecisionContext (
int precision,
Rounding rounding,
int exponentMinSmall,
int exponentMaxSmall,
boolean clampNormalExponents) {
      this.ec = new EContext(precision,
        ExtendedDecimal.ToERounding(rounding),
        exponentMinSmall, exponentMaxSmall, clampNormalExponents);
 }

    /**
     * Gets a value indicating whether the EMax and EMin properties refer to the
     * number's Exponent property adjusted to the number's precision, or
     * just the number's Exponent property. The default value is true,
     * meaning that EMax and EMin refer to the adjusted exponent. Setting
     * this value to false (using WithAdjustExponent) is useful for modeling
     * floating point representations with an integer mantissa and an
     * integer exponent, such as Java's BigDecimal.
     * @return True if the EMax and EMin properties refer to the number's Exponent
     * property adjusted to the number's precision, or false if they refer
     * to just the number's Exponent property.
     */
    public final boolean getAdjustExponent() {
return this.ec.getAdjustExponent();
}

    /**
     * Gets a value indicating whether a converted number&#x27;s Exponent property
     * will not be higher than EMax + 1 - Precision. If a number&#x27;s
     * exponent is higher than that value, but not high enough to cause
     * overflow, the exponent is clamped to that value and enough zeros are
     * added to the number&#x27;s mantissa to account for the adjustment. If
     * HasExponentRange is false, this value is always false.
     * @return If true, a converted number's Exponent property will not be higher
     * than EMax + 1 - Precision.
     */
    public final boolean getClampNormalExponents() {
return this.ec.getClampNormalExponents();
}

    /**
     * Gets the highest exponent possible when a converted number is expressed in
     * scientific notation with one digit before the decimal point. For
     * example, with a precision of 3 and an EMax of 100, the maximum value
     * possible is 9.99E + 100. (This is not the same as the highest
     * possible Exponent property.) If HasExponentRange is false, this value
     * will be 0.
     * @return The highest exponent possible when a converted number is expressed
     * in scientific notation with one digit before the decimal point. For
     * example, with a precision of 3 and an EMax of 100, the maximum value
     * possible is 9.99E + 100. (This is not the same as the highest
     * possible Exponent property.) If HasExponentRange is false, this value
     * will be 0.
     */
    public final BigInteger getEMax() {
return new BigInteger(this.ec.getEMax());
}

    /**
     * Gets the lowest exponent possible when a converted number is expressed in
     * scientific notation with one digit before the decimal point. For
     * example, with a precision of 3 and an EMin of -100, the next value
     * that comes after 0 is 0.001E-100. (If AdjustExponent is false, this
     * property specifies the lowest possible Exponent property instead.) If
     * HasExponentRange is false, this value will be 0.
     * @return The lowest exponent possible when a converted number is expressed in
     * scientific notation with one digit before the decimal point.
     */
    public final BigInteger getEMin() {
return new BigInteger(this.ec.getEMin());
}

    /**
     * Gets the flags that are set from converting numbers according to this
     * precision context. If HasFlags is false, this value will be 0. This
     * value is a combination of bit fields. To retrieve a particular flag,
     * use the AND operation on the return value of this method. For
     * example: <code>(this.getFlags() &amp; PrecisionContext.FlagInexact) != 0</code>
     * returns TRUE if the Inexact flag is set.
     * @return The flags that are set from converting numbers according to this
     * precision context. If HasFlags is false, this value will be 0.
     */
    public final int getFlags() {
return this.ec.getFlags();
}
public final void setFlags(int value) {
        this.ec.setFlags(value);
}

    /**
     * Gets a value indicating whether this context defines a minimum and maximum
     * exponent. If false, converted exponents can have any exponent and
     * operations can't cause overflow or underflow.
     * @return True if this context defines a minimum and maximum exponent;
     * otherwise, false.
     */
    public final boolean getHasExponentRange() {
return this.ec.getHasExponentRange();
}

    /**
     * Gets a value indicating whether this context has a mutable Flags field.
     * @return True if this context has a mutable Flags field; otherwise, false.
     */
    public final boolean getHasFlags() {
return this.ec.getHasFlags();
}

    /**
     * Gets a value indicating whether this context defines a maximum precision.
     * @return True if this context defines a maximum precision; otherwise, false.
     */
    public final boolean getHasMaxPrecision() {
return this.ec.getHasMaxPrecision();
}

    /**
     * Gets a value indicating whether this context's Precision property is in
     * bits, rather than digits. The default is false.
     * @return True if this context's Precision property is in bits, rather than
     * digits; otherwise, false. The default is false.
     */
    public final boolean isPrecisionInBits() {
return this.ec.isPrecisionInBits();
}

    /**
     * Gets a value indicating whether to use a "simplified" arithmetic. In the
     * simplified arithmetic, infinity, not-a-number, and subnormal numbers
     * are not allowed, and negative zero is treated the same as positive
     * zero. For further details, see
     * <code>http://speleotrove.com/decimal/dax3274.html</code>
     * @return True if a "simplified" arithmetic will be used; otherwise, false.
     */
    public final boolean isSimplified() {
return this.ec.isSimplified();
}

    /**
     * Gets the maximum length of a converted number in digits, ignoring the
     * decimal point and exponent. For example, if precision is 3, a
     * converted number&#x27;s mantissa can range from 0 to 999 (up to three
     * digits long). If 0, converted numbers can have any precision.
     * @return The maximum length of a converted number in digits, ignoring the
     * decimal point and exponent.
     */
    public final BigInteger getPrecision() {
return new BigInteger(this.ec.getPrecision());
}

    /**
     * Gets the desired rounding mode when converting numbers that can&#x27;t be
     * represented in the given precision and exponent range.
     * @return The desired rounding mode when converting numbers that can't be
     * represented in the given precision and exponent range.
     */
    public final Rounding getRounding() {
        return ExtendedDecimal.ToRounding(this.ec.getRounding());
}

    /**
     * Gets the traps that are set for each flag in the context. Whenever a flag is
     * signaled, even if HasFlags is false, and the flag's trap is enabled,
     * the operation will throw a TrapException. <p>For example, if Traps
     * equals FlagInexact and FlagSubnormal, a TrapException will be thrown
     * if an operation's return value is not the same as the exact result
     * (FlagInexact) or if the return value's exponent is lower than the
     * lowest allowed (FlagSubnormal).</p>
     * @return The traps that are set for each flag in the context.
     */
    public final int getTraps() {
return this.ec.getTraps();
}

    /**
     * Creates a new precision context using the given maximum number of digits, an
     * unlimited exponent range, and the HalfUp rounding mode.
     * @param precision Maximum number of digits (precision).
     * @return An EContext object.
     */
    public static PrecisionContext ForPrecision(int precision) {
return new PrecisionContext(EContext.ForPrecision(precision));
}

    /**
     * Creates a new PrecisionContext object initialized with an unlimited exponent
     * range, and the given rounding mode and maximum precision.
     * @param precision Maximum number of digits (precision).
     * @param rounding An ERounding object.
     * @return An EContext object.
     */
    public static PrecisionContext ForPrecisionAndRounding(int precision,
      Rounding rounding) {
return new PrecisionContext(EContext.ForPrecisionAndRounding(precision,
  ExtendedDecimal.ToERounding(rounding)));
}

    /**
     * Creates a new PrecisionContext object initialized with an unlimited
     * precision, an unlimited exponent range, and the given rounding mode.
     * @param rounding The rounding mode for the new precision context.
     * @return An EContext object.
     */
    public static PrecisionContext ForRounding(Rounding rounding) {
return new
  PrecisionContext(EContext.ForRounding(ExtendedDecimal.ToERounding(rounding)));
}

    /**
     * Initializes a new PrecisionContext that is a copy of another
     * PrecisionContext.
     * @return An EContext object.
     */
    public PrecisionContext Copy() {
return new PrecisionContext(this.ec.Copy());
}

    /**
     * Determines whether a number can have the given Exponent property under this
     * precision context.
     * @param exponent An arbitrary-precision integer indicating the desired
     * exponent.
     * @return True if a number can have the given Exponent property under this
     * precision context; otherwise, false. If this context allows unlimited
     * precision, returns true for the exponent EMax and any exponent less
     * than EMax.
     * @throws java.lang.NullPointerException The parameter {@code exponent} is null.
     */
    public boolean ExponentWithinRange(BigInteger exponent) {
  if ((exponent) == null) {
  throw new NullPointerException("exponent");
}
return this.ec.ExponentWithinRange(exponent.ei);
}

    /**
     * Gets a string representation of this object. Note that the format is not
     * intended to be parsed and may change at any time.
     * @return A string representation of this object.
     */
    @Override public String toString() {
return this.ec.toString();
}

    /**
     * Copies this PrecisionContext and sets the copy's "AdjustExponent" property
     * to the given value.
     * @param adjustExponent Not documented yet.
     * @return An EContext object.
     */
    public PrecisionContext WithAdjustExponent(boolean adjustExponent) {
return new PrecisionContext(this.ec.WithAdjustExponent(adjustExponent));
}

    /**
     * Copies this precision context and sets the copy's exponent range.
     * @param exponentMin Desired minimum exponent (EMin).
     * @param exponentMax Desired maximum exponent (EMax).
     * @return An EContext object.
     * @throws java.lang.NullPointerException The parameter {@code exponentMin} is
     * null.
     * @throws java.lang.NullPointerException The parameter {@code exponentMax} is
     * null.
     */
    public PrecisionContext WithBigExponentRange(BigInteger exponentMin,
      BigInteger exponentMax) {
  if ((exponentMin) == null) {
  throw new NullPointerException("exponentMin");
}
  if ((exponentMax) == null) {
  throw new NullPointerException("exponentMax");
}
return new PrecisionContext(this.ec.WithBigExponentRange(exponentMin.ei,
  exponentMax.ei));
}

    /**
     * Copies this PrecisionContext and gives it a particular precision value.
     * @param bigintPrecision Not documented yet.
     * @return An EContext object.
     * @throws java.lang.NullPointerException The parameter {@code bigintPrecision}
     * is null.
     */
    public PrecisionContext WithBigPrecision(BigInteger bigintPrecision) {
  if ((bigintPrecision) == null) {
  throw new NullPointerException("bigintPrecision");
}
return new PrecisionContext(this.ec.WithBigPrecision(bigintPrecision.ei));
}

    /**
     * Copies this PrecisionContext with HasFlags set to true and a Flags value of
     * 0.
     * @return An EContext object.
     */
    public PrecisionContext WithBlankFlags() {
return new PrecisionContext(this.ec.WithBlankFlags());
}

    /**
     * Copies this precision context and sets the copy&#x27;s
     * &#x22;ClampNormalExponents&#x22; flag to the given value.
     * @param clamp Not documented yet.
     * @return An EContext object.
     */
    public PrecisionContext WithExponentClamp(boolean clamp) {
return new PrecisionContext(this.ec.WithExponentClamp(clamp));
}

    /**
     * Copies this precision context and sets the copy&#x27;s exponent range.
     * @param exponentMinSmall Desired minimum exponent (EMin).
     * @param exponentMaxSmall Desired maximum exponent (EMax).
     * @return An EContext object.
     */
    public PrecisionContext WithExponentRange(int exponentMinSmall,
      int exponentMaxSmall) {
return new PrecisionContext(this.ec.WithExponentRange(exponentMinSmall,
  exponentMaxSmall));
}

    /**
     * Copies this PrecisionContext with HasFlags set to false and a Flags value of
     * 0.
     * @return An EContext object.
     */
    public PrecisionContext WithNoFlags() {
return new PrecisionContext(this.ec.WithNoFlags());
}

    /**
     * Copies this PrecisionContext and gives it a particular precision value.
     * @param precision Desired precision. 0 means unlimited precision.
     * @return An EContext object.
     */
    public PrecisionContext WithPrecision(int precision) {
return new PrecisionContext(this.ec.WithPrecision(precision));
}

    /**
     * Copies this PrecisionContext and sets the copy's "IsPrecisionInBits"
     * property to the given value.
     * @param isPrecisionBits Not documented yet.
     * @return An EContext object.
     */
    public PrecisionContext WithPrecisionInBits(boolean isPrecisionBits) {
return new PrecisionContext(this.ec.WithPrecisionInBits(isPrecisionBits));
}

    /**
     * Copies this PrecisionContext with the specified rounding mode.
     * @param rounding Not documented yet.
     * @return An EContext object.
     */
    public PrecisionContext WithRounding(Rounding rounding) {
return new
  PrecisionContext(this.ec.WithRounding(ExtendedDecimal.ToERounding(rounding)));
}

    /**
     * Copies this PrecisionContext and sets the copy's "IsSimplified" property to
     * the given value.
     * @param simplified Not documented yet.
     * @return An EContext object.
     */
    public PrecisionContext WithSimplified(boolean simplified) {
return new PrecisionContext(this.ec.WithSimplified(simplified));
}

    /**
     * Copies this PrecisionContext with Traps set to the given value.
     * @param traps Flags representing the traps to enable. See the property
     * "Traps".
     * @return An EContext object.
     */
    public PrecisionContext WithTraps(int traps) {
return new PrecisionContext(this.ec.WithTraps(traps));
}

    /**
     * Copies this PrecisionContext with an unlimited exponent range.
     * @return An EContext object.
     */
    public PrecisionContext WithUnlimitedExponents() {
return new PrecisionContext(this.ec.WithUnlimitedExponents());
}
  }
