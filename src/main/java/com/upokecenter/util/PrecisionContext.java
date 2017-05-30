package com.upokecenter.util;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.numbers.*;

    /**
     * <p><b>This class is largely obsolete. It will be replaced by a new version
     * of this class in a different namespace/package and library, called
     * <code>PeterO.Numbers.EContext</code> in the <code>PeterO.EContext</code> library
     * (in .NET), or <code>com.upokecenter.numbers.EFloat</code> in the <a
  * href='https://github.com/peteroupc/numbers-java'><code>com.github.peteroupc/numbers</code></a>
     * artifact (in Java).</b></p>Contains parameters for controlling the
     * precision, rounding, and exponent range of arbitrary-precision
     * numbers.
     */
  public class PrecisionContext {
    /**
     * Signals that the exponent was adjusted to fit the exponent range.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagClamped = 32;

    /**
     * Signals a division of a nonzero number by zero.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagDivideByZero = 128;

    /**
     * Signals that the result was rounded to a different mathematical value, but
     * as close as possible to the original.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagInexact = 1;

    /**
     * Signals an invalid operation.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagInvalid = 64;

    /**
     * Signals that an operand was rounded to a different mathematical value before
     * an operation.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagLostDigits = 256;

    /**
     * Signals that the result is non-zero and the exponent is higher than the
     * highest exponent allowed.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagOverflow = 16;

    /**
     * Signals that the result was rounded to fit the precision; either the value
     * or the exponent may have changed from the original.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagRounded = 2;

    /**
     * Signals that the result&#x27;s exponent, before rounding, is lower than the
     * lowest exponent allowed.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagSubnormal = 4;

    /**
     * Signals that the result&#x27;s exponent, before rounding, is lower than the
     * lowest exponent allowed, and the result was rounded to a different
     * mathematical value, but as close as possible to the original.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final int FlagUnderflow = 8;

    private final EContext ec;

    PrecisionContext(EContext ec) {
      this.ec = ec;
    }

    /**
     * Basic precision context, 9 digits precision, rounding mode half-up,
     * unlimited exponent range. The default rounding mode is HalfUp.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Basic =
      new PrecisionContext(EContext.Basic);

    /**
     * Precision context for Java's BigDecimal format. The default rounding mode is
     * HalfUp.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext BigDecimalJava =
      new PrecisionContext(EContext.BigDecimalJava);

    /**
     * Precision context for the IEEE-754-2008 binary128 format, 113 bits
     * precision. The default rounding mode is HalfEven.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Binary128 =
      new PrecisionContext(EContext.Binary128);

    /**
     * Precision context for the IEEE-754-2008 binary16 format, 11 bits precision.
     * The default rounding mode is HalfEven.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Binary16 =
      new PrecisionContext(EContext.Binary16);

    /**
     * Precision context for the IEEE-754-2008 binary32 format, 24 bits precision.
     * The default rounding mode is HalfEven.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Binary32 =
      new PrecisionContext(EContext.Binary32);

    /**
     * Precision context for the IEEE-754-2008 binary64 format, 53 bits precision.
     * The default rounding mode is HalfEven.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Binary64 =
      new PrecisionContext(EContext.Binary64);

    /**
     * Precision context for the Common Language Infrastructure (.NET Framework)
     * decimal format, 96 bits precision, and a valid exponent range of -28
     * to 0. The default rounding mode is HalfEven.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext CliDecimal =
      new PrecisionContext(EContext.CliDecimal);

    /**
     * Precision context for the IEEE-754-2008 decimal128 format. The default
     * rounding mode is HalfEven.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Decimal128 =
      new PrecisionContext(EContext.Decimal128);

    /**
     * Precision context for the IEEE-754-2008 decimal32 format. The default
     * rounding mode is HalfEven.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Decimal32 =
      new PrecisionContext(EContext.Decimal32);

    /**
     * Precision context for the IEEE-754-2008 decimal64 format. The default
     * rounding mode is HalfEven.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Decimal64 =
      new PrecisionContext(EContext.Decimal64);

    /**
     * Precision context for Java's BigDecimal format. The default rounding mode is
     * HalfUp.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext JavaBigDecimal = BigDecimalJava;

    /**
     * No specific limit on precision. Rounding mode HalfUp.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static final PrecisionContext Unlimited =
      PrecisionContext.ForPrecision(0);

    /**
     * Initializes a new instance of the {@link com.upokecenter.PrecisionContext}
     * class. HasFlags will be set to false.
     * @param precision The maximum number of digits a number can have, or 0 for an
     * unlimited number of digits.
     * @param rounding The rounding mode to use when a number can't fit the given
     * precision.
     * @param exponentMinSmall The minimum exponent.
     * @param exponentMaxSmall The maximum exponent.
     * @param clampNormalExponents Whether to clamp a number's significand to the
     * given maximum precision (if it isn't zero) while remaining within the
     * exponent range.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext(
  int precision,
  Rounding rounding,
  int exponentMinSmall,
  int exponentMaxSmall,
  boolean clampNormalExponents) {
      this.ec = new EContext(
  precision,
        ExtendedDecimal.ToERounding(rounding),
        exponentMinSmall,
 exponentMaxSmall,
 clampNormalExponents);
 }

    /**
     * Gets a value indicating whether the EMax and EMin properties refer to the
     * number's Exponent property adjusted to the number's precision, or
     * just the number's Exponent property. The default value is true,
     * meaning that EMax and EMin refer to the adjusted exponent. Setting
     * this value to false (using WithAdjustExponent) is useful for modeling
     * floating point representations with an integer mantissa and an
     * integer exponent, such as Java's BigDecimal.
     * @return {@code true} if the EMax and EMin properties refer to the number's
     * Exponent property adjusted to the number's precision, or false if
     * they refer to just the number's Exponent property.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final boolean getAdjustExponent() {
return this.getEc().getAdjustExponent();
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
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final boolean getClampNormalExponents() {
return this.getEc().getClampNormalExponents();
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
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final BigInteger getEMax() {
return new BigInteger(this.getEc().getEMax());
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
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final BigInteger getEMin() {
return new BigInteger(this.getEc().getEMin());
}

    /**
     * Gets the flags that are set from converting numbers according to this
     * precision context. If HasFlags is false, this value will be 0. This
     * value is a combination of bit fields. To retrieve a particular flag,
     * use the AND operation on the return value of this method. For
     * example: <code>(this.getFlags() &amp; PrecisionContext.FlagInexact) != 0</code>
     * returns <code>true</code> if the Inexact flag is set.
     * @return The flags that are set from converting numbers according to this
     * precision context. If HasFlags is false, this value will be 0.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final int getFlags() {
return this.getEc().getFlags();
}
public final void setFlags(int value) {
        this.getEc().setFlags(value);
}

    /**
     * Gets a value indicating whether this context defines a minimum and maximum
     * exponent. If false, converted exponents can have any exponent and
     * operations can't cause overflow or underflow.
     * @return {@code true} if this context defines a minimum and maximum exponent;
     * otherwise, {@code false}.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final boolean getHasExponentRange() {
return this.getEc().getHasExponentRange();
}

    /**
     * Gets a value indicating whether this context has a mutable Flags field.
     * @return {@code true} if this context has a mutable Flags field; otherwise,
     * {@code false}.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final boolean getHasFlags() {
return this.getEc().getHasFlags();
}

    /**
     * Gets a value indicating whether this context defines a maximum precision.
     * @return {@code true} if this context defines a maximum precision; otherwise,
     * {@code false}.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final boolean getHasMaxPrecision() {
return this.getEc().getHasMaxPrecision();
}

    /**
     * Gets a value indicating whether this context's Precision property is in
     * bits, rather than digits. The default is false.
     * @return {@code true} if this context's Precision property is in bits, rather
     * than digits; otherwise, {@code false}. The default is false.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final boolean isPrecisionInBits() {
return this.getEc().isPrecisionInBits();
}

    /**
     * Gets a value indicating whether to use a "simplified" arithmetic. In the
     * simplified arithmetic, infinity, not-a-number, and subnormal numbers
     * are not allowed, and negative zero is treated the same as positive
     * zero. For further details, see <a
  * href='http://speleotrove.com/decimal/dax3274.html'><code>http://speleotrove.com/decimal/dax3274.html</code></a>
     * @return {@code true} if a "simplified" arithmetic will be used; otherwise,
     * {@code false}.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final boolean isSimplified() {
return this.getEc().isSimplified();
}

    /**
     * Gets the maximum length of a converted number in digits, ignoring the
     * decimal point and exponent. For example, if precision is 3, a
     * converted number&#x27;s mantissa can range from 0 to 999 (up to three
     * digits long). If 0, converted numbers can have any precision.
     * @return The maximum length of a converted number in digits, ignoring the
     * decimal point and exponent.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final BigInteger getPrecision() {
return new BigInteger(this.getEc().getPrecision());
}

    /**
     * Gets the desired rounding mode when converting numbers that can&#x27;t be
     * represented in the given precision and exponent range.
     * @return The desired rounding mode when converting numbers that can't be
     * represented in the given precision and exponent range.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final Rounding getRounding() {
        return ExtendedDecimal.ToRounding(this.getEc().getRounding());
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
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public final int getTraps() {
return this.getEc().getTraps();
}

    final EContext getEc() {
        return this.ec;
      }

    /**
     * Creates a new precision context using the given maximum number of digits, an
     * unlimited exponent range, and the HalfUp rounding mode.
     * @param precision Maximum number of digits (precision).
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static PrecisionContext ForPrecision(int precision) {
return new PrecisionContext(EContext.ForPrecision(precision));
}

    /**
     * Creates a new PrecisionContext object initialized with an unlimited exponent
     * range, and the given rounding mode and maximum precision.
     * @param precision Maximum number of digits (precision).
     * @param rounding An ERounding object.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static PrecisionContext ForPrecisionAndRounding(
  int precision,
  Rounding rounding) {
return new PrecisionContext(
  EContext.ForPrecisionAndRounding(
  precision,
  ExtendedDecimal.ToERounding(rounding)));
}

    /**
     * Creates a new PrecisionContext object initialized with an unlimited
     * precision, an unlimited exponent range, and the given rounding mode.
     * @param rounding The rounding mode for the new precision context.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public static PrecisionContext ForRounding(Rounding rounding) {
return new
  PrecisionContext(EContext.ForRounding(ExtendedDecimal.ToERounding(rounding)));
}

    /**
     * Initializes a new PrecisionContext that is a copy of another
     * PrecisionContext.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext Copy() {
return new PrecisionContext(this.getEc().Copy());
}

    /**
     * Determines whether a number can have the given Exponent property under this
     * precision context.
     * @param exponent An arbitrary-precision integer indicating the desired
     * exponent.
     * @return {@code true} if a number can have the given Exponent property under
     * this precision context; otherwise, {@code false}. If this context
     * allows unlimited precision, returns true for the exponent EMax and
     * any exponent less than EMax.
     * @throws java.lang.NullPointerException The parameter {@code exponent} is null.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public boolean ExponentWithinRange(BigInteger exponent) {
  if (exponent == null) {
  throw new NullPointerException("exponent");
}
return this.getEc().ExponentWithinRange(exponent.getEi());
}

    /**
     * Gets a string representation of this object. Note that the format is not
     * intended to be parsed and may change at any time.
     * @return A string representation of this object.
     */
    @Override public String toString() {
return this.getEc().toString();
}

    /**
     * Copies this PrecisionContext and sets the copy's "AdjustExponent" property
     * to the given value.
     * @param adjustExponent The new value of the "AdjustExponent" property for the
     * copy.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithAdjustExponent(boolean adjustExponent) {
return new PrecisionContext(this.getEc().WithAdjustExponent(adjustExponent));
}

    /**
     * Copies this precision context and sets the copy's exponent range.
     * @param exponentMin Desired minimum exponent (EMin).
     * @param exponentMax Desired maximum exponent (EMax).
     * @return A context object for arbitrary-precision arithmetic settings.
     * @throws java.lang.NullPointerException The parameter {@code exponentMin} is
     * null.
     * @throws java.lang.NullPointerException The parameter {@code exponentMax} is
     * null.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithBigExponentRange(
  BigInteger exponentMin,
  BigInteger exponentMax) {
  if (exponentMin == null) {
  throw new NullPointerException("exponentMin");
}
  if (exponentMax == null) {
  throw new NullPointerException("exponentMax");
}
return new PrecisionContext(
  this.getEc().WithBigExponentRange(
  exponentMin.getEi(),
  exponentMax.getEi()));
}

    /**
     * Copies this PrecisionContext and gives it a particular precision value.
     * @param bigintPrecision Desired maximum number of digits a number can have.
     * If 0, the number of digits is unlimited.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @throws java.lang.NullPointerException The parameter {@code bigintPrecision}
     * is null.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithBigPrecision(BigInteger bigintPrecision) {
  if (bigintPrecision == null) {
  throw new NullPointerException("bigintPrecision");
}
return new PrecisionContext(this.getEc().WithBigPrecision(bigintPrecision.getEi()));
}

    /**
     * Copies this PrecisionContext with HasFlags set to true and a Flags value of
     * 0.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithBlankFlags() {
return new PrecisionContext(this.getEc().WithBlankFlags());
}

    /**
     * Copies this precision context and sets the copy&#x27;s
     * &#x22;ClampNormalExponents&#x22; flag to the given value.
     * @param clamp Desired value for ClampNormalExponents.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithExponentClamp(boolean clamp) {
return new PrecisionContext(this.getEc().WithExponentClamp(clamp));
}

    /**
     * Copies this precision context and sets the copy&#x27;s exponent range.
     * @param exponentMinSmall Desired minimum exponent (EMin).
     * @param exponentMaxSmall Desired maximum exponent (EMax).
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithExponentRange(
  int exponentMinSmall,
  int exponentMaxSmall) {
return new PrecisionContext(
  this.getEc().WithExponentRange(
  exponentMinSmall,
  exponentMaxSmall));
}

    /**
     * Copies this PrecisionContext with HasFlags set to false and a Flags value of
     * 0.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithNoFlags() {
return new PrecisionContext(this.getEc().WithNoFlags());
}

    /**
     * Copies this PrecisionContext and gives it a particular precision value.
     * @param precision Desired maximum number of digits a number can have. If 0,
     * the number of digits is unlimited.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithPrecision(int precision) {
return new PrecisionContext(this.getEc().WithPrecision(precision));
}

    /**
     * Copies this PrecisionContext and sets the copy's "IsPrecisionInBits"
     * property to the given value.
     * @param isPrecisionBits The new value of the "IsPrecisionInBits" property for
     * the copy.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithPrecisionInBits(boolean isPrecisionBits) {
return new PrecisionContext(this.getEc().WithPrecisionInBits(isPrecisionBits));
}

    /**
     * Copies this PrecisionContext with the specified rounding mode.
     * @param rounding Desired value of the Rounding property.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithRounding(Rounding rounding) {
return new
  PrecisionContext(this.getEc().WithRounding(ExtendedDecimal.ToERounding(rounding)));
}

    /**
     * Copies this PrecisionContext and sets the copy's "IsSimplified" property to
     * the given value.
     * @param simplified The new value of the "IsSimplified" property for the copy.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithSimplified(boolean simplified) {
return new PrecisionContext(this.getEc().WithSimplified(simplified));
}

    /**
     * Copies this PrecisionContext with Traps set to the given value.
     * @param traps Flags representing the traps to enable. See the property
     * "Traps".
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithTraps(int traps) {
return new PrecisionContext(this.getEc().WithTraps(traps));
}

    /**
     * Copies this PrecisionContext with an unlimited exponent range.
     * @return A context object for arbitrary-precision arithmetic settings.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
   public PrecisionContext WithUnlimitedExponents() {
return new PrecisionContext(this.getEc().WithUnlimitedExponents());
}
  }
