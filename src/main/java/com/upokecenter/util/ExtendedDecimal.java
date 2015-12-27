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
     * Represents an arbitrary-precision decimal floating-point number. Consists of
     * an integer mantissa and an integer exponent, both
     * arbitrary-precision. The value of the number equals mantissa *
     * 10^exponent. <p>The mantissa is the value of the digits that make up
     * a number, ignoring the decimal point and exponent. For example, in
     * the number 2356.78, the mantissa is 235678. The exponent is where the
     * "floating" decimal point of the number is located. A positive
     * exponent means "move it to the right", and a negative exponent means
     * "move it to the left." In the example 2, 356.78, the exponent is -2,
     * since it has 2 decimal places and the decimal point is "moved to the
     * left by 2." Therefore, in the arbitrary-precision decimal
     * representation, this number would be stored as 235678 * 10^-2.</p>
     * <p>The mantissa and exponent format preserves trailing zeros in the
     * number's value. This may give rise to multiple ways to store the same
     * value. For example, 1.00 and 1 would be stored differently, even
     * though they have the same value. In the first case, 100 * 10^-2 (100
     * with decimal point moved left by 2), and in the second case, 1 * 10^0
     * (1 with decimal point moved 0).</p> <p>This class also supports
     * values for negative zero, not-a-number (NaN) values, and infinity.
     * <b>Negative zero</b> is generally used when a negative number is
     * rounded to 0; it has the same mathematical value as positive zero.
     * <b>Infinity</b> is generally used when a non-zero number is divided
     * by zero, or when a very high number can't be represented in a given
     * exponent range. <b>Not-a-number</b> is generally used to signal
     * errors.</p> <p>This class implements the General Decimal Arithmetic
     * Specification version 1.70:
     * <code>http://speleotrove.com/decimal/decarith.html</code></p> <p>Passing a
     * signaling NaN to any arithmetic operation shown here will signal the
     * flag FlagInvalid and return a quiet NaN, even if another operand to
     * that operation is a quiet NaN, unless noted otherwise.</p> <p>Passing
     * a quiet NaN to any arithmetic operation shown here will return a
     * quiet NaN, unless noted otherwise. Invalid operations will also
     * return a quiet NaN, as stated in the individual methods.</p>
     * <p>Unless noted otherwise, passing a null arbitrary-precision decimal
     * argument to any method here will throw an exception.</p> <p>When an
     * arithmetic operation signals the flag FlagInvalid, FlagOverflow, or
     * FlagDivideByZero, it will not throw an exception too, unless the
     * flag's trap is enabled in the precision context (see EContext's Traps
     * property).</p> <p>An arbitrary-precision decimal value can be
     * serialized in one of the following ways:</p> <ul> <li>By calling the
     * toString() method, which will always return distinct strings for
     * distinct arbitrary-precision decimal values.</li> <li>By calling the
     * UnsignedMantissa, Exponent, and IsNegative properties, and calling
     * the IsInfinity, IsQuietNaN, and IsSignalingNaN methods. The return
     * values combined will uniquely identify a particular
     * arbitrary-precision decimal value.</li></ul>
     */
  public final class ExtendedDecimal implements Comparable<ExtendedDecimal> {
    /**
     * Gets this object&#x27;s exponent. This object&#x27;s value will be an
     * integer if the exponent is positive or zero.
     * @return This object's exponent. This object's value will be an integer if
     * the exponent is positive or zero.
     */
    public final BigInteger getExponent() {
        return new BigInteger(this.ed.getExponent());
      }

    /**
     * Gets the absolute value of this object&#x27;s un-scaled value.
     * @return The absolute value of this object's un-scaled value.
     */
    public final BigInteger getUnsignedMantissa() {
        return new BigInteger(this.ed.getUnsignedMantissa());
      }

    /**
     * Gets this object&#x27;s un-scaled value.
     * @return This object's un-scaled value. Will be negative if this object's
     * value is negative (including a negative NaN).
     */
    public final BigInteger getMantissa() {
        return new BigInteger(this.ed.getMantissa());
      }

    /**
     * Determines whether this object&#x27;s mantissa and exponent are equal to
     * those of another object.
     * @param other An arbitrary-precision decimal object.
     * @return True if this object's mantissa and exponent are equal to those of
     * another object; otherwise, false.
     */
    public boolean equals(ExtendedDecimal other) {
      return (other == null) ? (false) : (this.ed.equals(other.ed));
    }

    /**
     * Determines whether this object&#x27;s mantissa and exponent are equal to
     * those of another object and that other object is a decimal fraction.
     * @param obj An arbitrary object.
     * @return True if the objects are equal; otherwise, false.
     */
    @Override public boolean equals(Object obj) {
      ExtendedDecimal bi = ((obj instanceof ExtendedDecimal) ? (ExtendedDecimal)obj : null);
      return (bi == null) ? (false) : (this.ed.equals(bi.ed));
    }

    /**
     * Calculates this object&#x27;s hash code.
     * @return This object's hash code.
     */
    @Override public int hashCode() {
      return this.ed.hashCode();
    }

    /**
     * Creates a number with the value exponent*10^mantissa.
     * @param mantissaSmall The un-scaled value.
     * @param exponentSmall The decimal exponent.
     * @return An arbitrary-precision decimal object.
     */
    public static ExtendedDecimal Create(int mantissaSmall, int exponentSmall) {
      return new ExtendedDecimal(EDecimal.Create(mantissaSmall, exponentSmall));
    }

    final EDecimal ed;
    ExtendedDecimal(EDecimal ed) {
      if ((ed) == null) {
        throw new NullPointerException("ed");
      }
      this.ed = ed;
    }

    /**
     * Creates a number with the value exponent*10^mantissa.
     * @param mantissa The un-scaled value.
     * @param exponent The decimal exponent.
     * @return An arbitrary-precision decimal object.
     * @throws java.lang.NullPointerException The parameter {@code mantissa} or
     * {@code exponent} is null.
     */
    public static ExtendedDecimal Create(
      BigInteger mantissa,
      BigInteger exponent) {
      if ((mantissa) == null) {
        throw new NullPointerException("mantissa");
      }
      if ((exponent) == null) {
        throw new NullPointerException("exponent");
      }
      return new ExtendedDecimal(EDecimal.Create(mantissa.ei, exponent.ei));
    }

    /**
     * Creates a not-a-number arbitrary-precision decimal object.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @return A quiet not-a-number.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null or
     * is less than 0.
     */
    public static ExtendedDecimal CreateNaN(BigInteger diag) {
      if ((diag) == null) {
        throw new NullPointerException("diag");
      }
      return new ExtendedDecimal(EDecimal.CreateNaN(diag.ei));
    }

    /**
     * Creates a not-a-number arbitrary-precision decimal object.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @param signaling Whether the return value will be signaling (true) or quiet
     * (false).
     * @param negative Whether the return value is negative.
     * @param ctx An EContext object.
     * @return An arbitrary-precision decimal object.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null or
     * is less than 0.
     */
    public static ExtendedDecimal CreateNaN(BigInteger diag,
      boolean signaling,
      boolean negative,
      PrecisionContext ctx) {
      if ((diag) == null) {
        throw new NullPointerException("diag");
      }
    return new ExtendedDecimal(EDecimal.CreateNaN(diag.ei, signaling,
        negative,
        ctx == null ? null : ctx.ec));
    }

    /**
     * Creates a decimal number from a string that represents a number. See
     * <code>FromString(String, int, int, EContext)</code> for more information.
     * @param str A string that represents a number.
     * @return An arbitrary-precision decimal number with the same value as the
     * given string.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws java.lang.NumberFormatException The parameter {@code str} is not a correctly
     * formatted number string.
     */
    public static ExtendedDecimal FromString(String str) {
      return new ExtendedDecimal(EDecimal.FromString(str));
    }

    /**
     * Creates a decimal number from a string that represents a number. See
     * <code>FromString(String, int, int, EContext)</code> for more information.
     * @param str A string that represents a number.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal number with the same value as the
     * given string.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws java.lang.NumberFormatException The parameter {@code str} is not a correctly
     * formatted number string.
     */
    public static ExtendedDecimal FromString(String str, PrecisionContext ctx) {
try {
      return new ExtendedDecimal(EDecimal.FromString(str, ctx == null ? null :
        ctx.ec));
    } catch (ETrapException ex) {
 throw TrapException.Create(ex);
}
 }

    /**
     * Creates a decimal number from a string that represents a number. See
     * <code>FromString(String, int, int, EContext)</code> for more information.
     * @param str A string that represents a number.
     * @param offset A zero-based index showing where the desired portion of "str"
     * begins.
     * @param length The length, in code units, of the desired portion of "str"
     * (but not more than "str" 's length).
     * @return An arbitrary-precision decimal number with the same value as the
     * given string.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws java.lang.NumberFormatException The parameter {@code str} is not a correctly
     * formatted number string.
     */
    public static ExtendedDecimal FromString(String str,
      int offset,
      int length) {
      return new ExtendedDecimal(EDecimal.FromString(str, offset, length));
    }

    /**
     * <p>Creates a decimal number from a string that represents a number.</p>
     * <p>The format of the string generally consists of:</p> <ul> <li>An
     * optional plus sign ("+" , U+002B) or minus sign ("-", U+002D) (if '-'
     * , the value is negative.)</li> <li>One or more digits, with a single
     * optional decimal point after the first digit and before the last
     * digit.</li> <li>Optionally, "E+" (positive exponent) or "E-"
     * (negative exponent) plus one or more digits specifying the
     * exponent.</li></ul> <p>The string can also be "-INF", "-Infinity",
     * "Infinity", "INF" , quiet NaN ("NaN" /"-NaN") followed by any number
     * of digits, or signaling NaN ("sNaN" /"-sNaN") followed by any number
     * of digits, all in any combination of upper and lower case.</p> <p>All
     * characters mentioned above are the corresponding characters in the
     * Basic Latin range. In particular, the digits must be the basic digits
     * 0 to 9 (U + 0030 to U + 0039). The string is not allowed to contain white
     * space characters, including spaces.</p>
     * @param str A string object, a portion of which represents a number.
     * @param offset A zero-based index that identifies the start of the number.
     * @param length The length of the number within the string.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal number with the same value as the
     * given string.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws java.lang.NumberFormatException The parameter {@code str} is not a correctly
     * formatted number string.
     */
    public static ExtendedDecimal FromString(String str,
      int offset,
      int length,
      PrecisionContext ctx) {
try {
      return new ExtendedDecimal(EDecimal.FromString(str, offset, length,
        ctx == null ? null : ctx.ec));
    } catch (ETrapException ex) {
 throw TrapException.Create(ex);
}
 }

    /**
     * Compares an arbitrary-precision binary float with this instance.
     * @param other The other object to compare. Can be null.
     * @return Zero if the values are equal; a negative number if this instance is
     * less, or a positive number if this instance is greater. Returns 0 if
     * both values are NaN (even signaling NaN) and 1 if this value is NaN
     * (even signaling NaN) and the other isn't, or if the other value is
     * null.
     */
    public int CompareToBinary(ExtendedFloat other) {
      return ((other) == null) ? (1) : (this.ed.CompareToBinary(other.ef));
    }

    /**
     * Converts this value to an arbitrary-precision integer. Any fractional part
     * in this value will be discarded when converting to a big integer.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     */
    public BigInteger ToBigInteger() {
      return new BigInteger(this.ed.ToBigInteger());
    }

    static ERounding ToERounding(Rounding r) {
      if (r == Rounding.Ceiling) {
        return ERounding.Ceiling;
      }
      if (r == Rounding.Floor) {
        return ERounding.Floor;
      }
      if (r == Rounding.HalfDown) {
        return ERounding.HalfDown;
      }
      if (r == Rounding.HalfEven) {
        return ERounding.HalfEven;
      }
      if (r == Rounding.HalfUp) {
        return ERounding.HalfUp;
      }
      if (r == Rounding.Up) {
        return ERounding.Up;
      }
      if (r == Rounding.ZeroFiveUp) {
        return ERounding.ZeroFiveUp;
      }
      if (r == Rounding.OddOrZeroFiveUp) {
        return ERounding.OddOrZeroFiveUp;
      }
      return (r == Rounding.Unnecessary) ? (ERounding.None) : ((r ==
        Rounding.Odd) ? (ERounding.Odd) : (ERounding.Down));
    }

    static Rounding ToRounding(ERounding r) {
      if (r == ERounding.Ceiling) {
        return Rounding.Ceiling;
      }
      if (r == ERounding.Floor) {
        return Rounding.Floor;
      }
      if (r == ERounding.HalfDown) {
        return Rounding.HalfDown;
      }
      if (r == ERounding.HalfEven) {
        return Rounding.HalfEven;
      }
      if (r == ERounding.HalfUp) {
        return Rounding.HalfUp;
      }
      if (r == ERounding.Up) {
        return Rounding.Up;
      }
      if (r == ERounding.ZeroFiveUp) {
        return Rounding.ZeroFiveUp;
      }
      if (r == ERounding.OddOrZeroFiveUp) {
        return Rounding.OddOrZeroFiveUp;
      }
      return (r == ERounding.None) ? (Rounding.Unnecessary) : ((r ==
        ERounding.Odd) ? (Rounding.Odd) : (Rounding.Down));
    }

    /**
     * Converts this value to an arbitrary-precision integer, checking whether the
     * fractional part of the integer would be lost.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     * @throws ArithmeticException This object's value is not an exact integer.
     */
    public BigInteger ToBigIntegerExact() {
      return new BigInteger(this.ed.ToBigIntegerExact());
    }

    private static final BigInteger valueOneShift62 = BigInteger.valueOf(1).shiftLeft(62);

    /**
     * Creates a binary floating-point number from this object&#x27;s value. Note
     * that if the binary floating-point number contains a negative
     * exponent, the resulting value might not be exact. However, the
     * resulting binary float will contain enough precision to accurately
     * convert it to a 32-bit or 64-bit floating point number (float or
     * double).
     * @return An arbitrary-precision binary float.
     */
    public ExtendedFloat ToExtendedFloat() {
      return new ExtendedFloat(this.ed.ToExtendedFloat());
    }

    /**
     * Converts this value to a 32-bit floating-point number. The half-even
     * rounding mode is used. <p>If this value is a NaN, sets the high bit
     * of the 32-bit floating point number's mantissa for a quiet NaN, and
     * clears it for a signaling NaN. Then the next highest bit of the
     * mantissa is cleared for a quiet NaN, and set for a signaling NaN.
     * Then the other bits of the mantissa are set to the lowest bits of
     * this object's unsigned mantissa.</p>
     * @return The closest 32-bit floating-point number to this value. The return
     * value can be positive infinity or negative infinity if this value
     * exceeds the range of a 32-bit floating point number.
     */
    public float ToSingle() {
      return this.ed.ToSingle();
    }

    /**
     * Converts this value to a 64-bit floating-point number. The half-even
     * rounding mode is used. <p>If this value is a NaN, sets the high bit
     * of the 64-bit floating point number's mantissa for a quiet NaN, and
     * clears it for a signaling NaN. Then the next highest bit of the
     * mantissa is cleared for a quiet NaN, and set for a signaling NaN.
     * Then the other bits of the mantissa are set to the lowest bits of
     * this object's unsigned mantissa.</p>
     * @return The closest 64-bit floating-point number to this value. The return
     * value can be positive infinity or negative infinity if this value
     * exceeds the range of a 64-bit floating point number.
     */
    public double ToDouble() {
      return this.ed.ToDouble();
    }

    /**
     * Creates a decimal number from a 32-bit floating-point number. This method
     * computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the floating point
     * number to a string first. Remember, though, that the exact value of a
     * 32-bit floating-point number is not always the value you get when you
     * pass a literal decimal number (for example, calling
     * <code>ExtendedDecimal.FromSingle(0.1f)</code>), since not all decimal
     * numbers can be converted to exact binary numbers (in the example
     * given, the resulting arbitrary-precision decimal will be the the
     * value of the closest "float" to 0.1, not 0.1 exactly). To create an
     * arbitrary-precision decimal number from a decimal number, use
     * FromString instead in most cases (for example:
     * <code>ExtendedDecimal.FromString("0.1")</code>).
     * @param flt A 32-bit floating-point number.
     * @return A decimal number with the same value as {@code flt}.
     */
    public static ExtendedDecimal FromSingle(float flt) {
      return new ExtendedDecimal(EDecimal.FromSingle(flt));
    }

    /**
     * Converts a big integer to an arbitrary precision decimal.
     * @param bigint An arbitrary-precision integer.
     * @return An arbitrary-precision decimal object with the exponent set to 0.
     */
    public static ExtendedDecimal FromBigInteger(BigInteger bigint) {
      if ((bigint) == null) {
        throw new NullPointerException("bigint");
      }
      return new ExtendedDecimal(EDecimal.FromBigInteger(bigint.ei));
    }

    /**
     * Creates a decimal number from a 64-bit signed integer.
     * @param valueSmall A 64-bit signed integer.
     * @return An arbitrary-precision decimal object with the exponent set to 0.
     */
    public static ExtendedDecimal FromInt64(long valueSmall) {
      return new ExtendedDecimal(EDecimal.FromInt64(valueSmall));
    }

    /**
     * Creates a decimal number from a 32-bit signed integer.
     * @param valueSmaller A 32-bit signed integer.
     * @return An arbitrary-precision decimal object.
     */
    public static ExtendedDecimal FromInt32(int valueSmaller) {
      return new ExtendedDecimal(EDecimal.FromInt32(valueSmaller));
    }

    /**
     * Creates a decimal number from a 64-bit floating-point number. This method
     * computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the floating point
     * number to a string first. Remember, though, that the exact value of a
     * 64-bit floating-point number is not always the value you get when you
     * pass a literal decimal number (for example, calling
     * <code>ExtendedDecimal.FromDouble(0.1f)</code>), since not all decimal
     * numbers can be converted to exact binary numbers (in the example
     * given, the resulting arbitrary-precision decimal will be the value of
     * the closest "double" to 0.1, not 0.1 exactly). To create an
     * arbitrary-precision decimal number from a decimal number, use
     * FromString instead in most cases (for example:
     * <code>ExtendedDecimal.FromString("0.1")</code>).
     * @param dbl A 64-bit floating-point number.
     * @return A decimal number with the same value as {@code dbl}.
     */
    public static ExtendedDecimal FromDouble(double dbl) {
      return new ExtendedDecimal(EDecimal.FromDouble(dbl));
    }

    /**
     * Creates a decimal number from an arbitrary-precision binary floating-point
     * number.
     * @param bigfloat A big floating-point number.
     * @return An arbitrary-precision decimal object.
     * @throws java.lang.NullPointerException The parameter {@code bigfloat} is null.
     */
    public static ExtendedDecimal FromExtendedFloat(ExtendedFloat bigfloat) {
      if ((bigfloat) == null) {
        throw new NullPointerException("bigfloat");
      }
      return new ExtendedDecimal(EDecimal.FromExtendedFloat(bigfloat.ef));
    }

    /**
     * Converts this value to a string. Returns a value compatible with this
     * class's FromString method.
     * @return A string representation of this object.
     */
    @Override public String toString() {
      return this.ed.toString();
    }

    /**
     * Same as toString(), except that when an exponent is used it will be a
     * multiple of 3.
     * @return A string object.
     */
    public String ToEngineeringString() {
      return this.ed.ToEngineeringString();
    }

    /**
     * Converts this value to a string, but without using exponential notation.
     * @return A string object.
     */
    public String ToPlainString() {
      return this.ed.ToPlainString();
    }

    /**
     * Represents the number 1.
     */

    public static final ExtendedDecimal One =
      ExtendedDecimal.Create(BigInteger.valueOf(1), BigInteger.valueOf(0));

    /**
     * Represents the number 0.
     */

    public static final ExtendedDecimal Zero =
      ExtendedDecimal.Create(BigInteger.valueOf(0), BigInteger.valueOf(0));

    /**
     * Represents the number negative zero.
     */

    public static final ExtendedDecimal NegativeZero =
      new ExtendedDecimal(EDecimal.NegativeZero);

    /**
     * Represents the number 10.
     */

    public static final ExtendedDecimal Ten =
      ExtendedDecimal.Create(BigInteger.valueOf(10), BigInteger.valueOf(0));

    //----------------------------------------------------------------

    /**
     * A not-a-number value.
     */
    public static final ExtendedDecimal NaN =
      new ExtendedDecimal(EDecimal.NaN);

    /**
     * A not-a-number value that signals an invalid operation flag when it&#x27;s
     * passed as an argument to any arithmetic operation in
     * arbitrary-precision decimal.
     */
    public static final ExtendedDecimal SignalingNaN =
      new ExtendedDecimal(EDecimal.SignalingNaN);

    /**
     * Positive infinity, greater than any other number.
     */
    public static final ExtendedDecimal PositiveInfinity =
      new ExtendedDecimal(EDecimal.PositiveInfinity);

    /**
     * Negative infinity, less than any other number.
     */
    public static final ExtendedDecimal NegativeInfinity =
      new ExtendedDecimal(EDecimal.NegativeInfinity);

    /**
     * Returns whether this object is negative infinity.
     * @return True if this object is negative infinity; otherwise, false.
     */
    public boolean IsNegativeInfinity() {
      return this.ed.IsNegativeInfinity();
    }

    /**
     * Returns whether this object is positive infinity.
     * @return True if this object is positive infinity; otherwise, false.
     */
    public boolean IsPositiveInfinity() {
      return this.ed.IsPositiveInfinity();
    }

    /**
     * Gets a value indicating whether this object is not a number (NaN).
     * @return True if this object is not a number (NaN); otherwise, false.
     */
    public boolean IsNaN() {
      return this.ed.IsNaN();
    }

    /**
     * Gets a value indicating whether this object is positive or negative
     * infinity.
     * @return True if this object is positive or negative infinity; otherwise,
     * false.
     */
    public boolean IsInfinity() {
      return this.ed.IsInfinity();
    }

    /**
     * Gets a value indicating whether this object is finite (not infinity or NaN).
     * @return True if this object is finite (not infinity or NaN); otherwise,
     * false.
     */
    public final boolean isFinite() {
        return this.ed.isFinite();
      }

    /**
     * Gets a value indicating whether this object is negative, including negative
     * zero.
     * @return True if this object is negative, including negative zero; otherwise,
     * false.
     */
    public final boolean isNegative() {
        return this.ed.isNegative();
      }

    /**
     * Gets a value indicating whether this object is a quiet not-a-number value.
     * @return True if this object is a quiet not-a-number value; otherwise, false.
     */
    public boolean IsQuietNaN() {
      return this.ed.IsQuietNaN();
    }

    /**
     * Gets a value indicating whether this object is a signaling not-a-number
     * value.
     * @return True if this object is a signaling not-a-number value; otherwise,
     * false.
     */
    public boolean IsSignalingNaN() {
      return this.ed.IsSignalingNaN();
    }

    /**
     * Gets this value&#x27;s sign: -1 if negative; 1 if positive; 0 if zero.
     * @return This value's sign: -1 if negative; 1 if positive; 0 if zero.
     */
    public final int signum() {
        return this.ed.signum();
      }

    /**
     * Gets a value indicating whether this object&#x27;s value equals 0.
     * @return True if this object's value equals 0; otherwise, false.
     */
    public final boolean isZero() {
        return this.ed.signum() == 0;
      }

    /**
     * Gets the absolute value of this object.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal Abs() {
      return new ExtendedDecimal(this.ed.Abs());
    }

    /**
     * Gets an object with the same value as this one, but with the sign reversed.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal Negate() {
      return new ExtendedDecimal(this.ed.Negate());
    }

    /**
     * Divides this object by another decimal number and returns the result. When
     * possible, the result will be exact.
     * @param divisor The divisor.
     * @return The quotient of the two numbers. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * returns not-a-number (NaN) if the divisor and the dividend are 0.
     * Returns NaN if the result can't be exact because it would have a
     * nonterminating decimal expansion.
     */
    public ExtendedDecimal Divide(ExtendedDecimal divisor) {
      if ((divisor) == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedDecimal(this.ed.Divide(divisor.ed));
    }

    /**
     * Divides this object by another decimal number and returns a result with the
     * same exponent as this object (the dividend).
     * @param divisor The divisor.
     * @param rounding The rounding mode to use if the result must be scaled down
     * to have the same exponent as this value.
     * @return The quotient of the two numbers. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0. Signals FlagInvalid and returns not-a-number
     * (NaN) if the rounding mode is Rounding.Unnecessary and the result is
     * not exact.
     */
    public ExtendedDecimal DivideToSameExponent(ExtendedDecimal divisor,
      Rounding rounding) {
      if ((divisor) == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedDecimal(this.ed.DivideToSameExponent(divisor.ed,
        ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Divides two arbitrary-precision decimal objects, and returns the integer
     * part of the result, rounded down, with the preferred exponent set to
     * this value&#x27;s exponent minus the divisor&#x27;s exponent.
     * @param divisor The divisor.
     * @return The integer part of the quotient of the two objects. Signals
     * FlagDivideByZero and returns infinity if the divisor is 0 and the
     * dividend is nonzero. Signals FlagInvalid and returns not-a-number
     * (NaN) if the divisor and the dividend are 0.
     */
    public ExtendedDecimal DivideToIntegerNaturalScale(ExtendedDecimal
                    divisor) {
      if ((divisor) == null) {
        throw new NullPointerException("divisor");
      }
   return new ExtendedDecimal(this.ed.DivideToIntegerNaturalScale(divisor.ed));
    }

    /**
     * Removes trailing zeros from this object&#x27;s mantissa. For example, 1.000
     * becomes 1. <p>If this object's value is 0, changes the exponent to
     * 0.</p>
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return This value with trailing zeros removed. Note that if the result has
     * a very high exponent and the context says to clamp high exponents,
     * there may still be some trailing zeros in the mantissa.
     */
    public ExtendedDecimal Reduce(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.ed.Reduce(ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Calculates the remainder of a number by the formula "this" - (("this" /
     * "divisor") * "divisor").
     * @param divisor The number to divide by.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal RemainderNaturalScale(ExtendedDecimal divisor) {
      if ((divisor) == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedDecimal(this.ed.RemainderNaturalScale(divisor.ed));
    }

    /**
     * Calculates the remainder of a number by the formula "this" - (("this" /
     * "divisor") * "divisor").
     * @param divisor The number to divide by.
     * @param ctx A precision context object to control the precision, rounding,
     * and exponent range of the result. This context will be used only in
     * the division portion of the remainder calculation; as a result, it's
     * possible for the return value to have a higher precision than given
     * in this context. Flags will be set on the given context only if the
     * context's HasFlags is true and the integer part of the division
     * result doesn't fit the precision and exponent range without rounding.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal RemainderNaturalScale(ExtendedDecimal divisor,
      PrecisionContext ctx) {
      try {
        if ((divisor) == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(this.ed.RemainderNaturalScale(divisor.ed,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision decimal objects, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision decimal object.
     * @param desiredExponentSmall The desired exponent. A negative number places
     * the cutoff point to the right of the usual decimal point. A positive
     * number places the cutoff point to the left of the usual decimal
     * point.
     * @param ctx A precision context object to control the rounding mode to use if
     * the result must be scaled down to have the same exponent as this
     * value. If the precision given in the context is other than 0, calls
     * the Quantize method with both arguments equal to the result of the
     * operation (and can signal FlagInvalid and return NaN if the result
     * doesn't fit the given precision). If HasFlags of the context is true,
     * will also store the flags resulting from the operation (the flags are
     * in addition to the pre-existing flags). Can be null, in which case
     * the default rounding mode is HalfEven.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0. Signals FlagInvalid and returns not-a-number
     * (NaN) if the context defines an exponent range and the desired
     * exponent is outside that range. Signals FlagInvalid and returns
     * not-a-number (NaN) if the rounding mode is Rounding.Unnecessary and
     * the result is not exact.
     */
    public ExtendedDecimal DivideToExponent(ExtendedDecimal divisor,
      long desiredExponentSmall,
      PrecisionContext ctx) {
      try {
        if ((divisor) == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(this.ed.DivideToExponent(divisor.ed,
          desiredExponentSmall, ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides this arbitrary-precision decimal object by another
     * arbitrary-precision decimal object. The preferred exponent for the
     * result is this object&#x27;s exponent minus the divisor&#x27;s
     * exponent.
     * @param divisor The divisor.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0; or, either {@code ctx} is null or {@code ctx} 's
     * precision is 0, and the result would have a nonterminating decimal
     * expansion; or, the rounding mode is Rounding.Unnecessary and the
     * result is not exact.
     */
    public ExtendedDecimal Divide(ExtendedDecimal divisor,
      PrecisionContext ctx) {
      try {
        if ((divisor) == null) {
          throw new NullPointerException("divisor");
        }
     return new ExtendedDecimal(this.ed.Divide(divisor.ed, ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision decimal objects, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision decimal object.
     * @param desiredExponentSmall The desired exponent. A negative number places
     * the cutoff point to the right of the usual decimal point. A positive
     * number places the cutoff point to the left of the usual decimal
     * point.
     * @param rounding The rounding mode to use if the result must be scaled down
     * to have the same exponent as this value.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0. Signals FlagInvalid and returns not-a-number
     * (NaN) if the rounding mode is Rounding.Unnecessary and the result is
     * not exact.
     */
    public ExtendedDecimal DivideToExponent(ExtendedDecimal divisor,
      long desiredExponentSmall,
      Rounding rounding) {
      if ((divisor) == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedDecimal(this.ed.DivideToExponent(divisor.ed,
        desiredExponentSmall, ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Divides two arbitrary-precision decimal objects, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision decimal object.
     * @param exponent The desired exponent. A negative number places the cutoff
     * point to the right of the usual decimal point. A positive number
     * places the cutoff point to the left of the usual decimal point.
     * @param ctx A precision context object to control the rounding mode to use if
     * the result must be scaled down to have the same exponent as this
     * value. If the precision given in the context is other than 0, calls
     * the Quantize method with both arguments equal to the result of the
     * operation (and can signal FlagInvalid and return NaN if the result
     * doesn't fit the given precision). If HasFlags of the context is true,
     * will also store the flags resulting from the operation (the flags are
     * in addition to the pre-existing flags). Can be null, in which case
     * the default rounding mode is HalfEven.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0. Signals FlagInvalid and returns not-a-number
     * (NaN) if the context defines an exponent range and the desired
     * exponent is outside that range. Signals FlagInvalid and returns
     * not-a-number (NaN) if the rounding mode is Rounding.Unnecessary and
     * the result is not exact.
     */
    public ExtendedDecimal DivideToExponent(ExtendedDecimal divisor,
      BigInteger exponent,
      PrecisionContext ctx) {
      try {
        if ((divisor) == null) {
          throw new NullPointerException("divisor");
        }
        if ((exponent) == null) {
          throw new NullPointerException("exponent");
        }
   return new ExtendedDecimal(this.ed.DivideToExponent(divisor.ed,
          exponent.ei,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision decimal objects, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision decimal object.
     * @param desiredExponent The desired exponent. A negative number places the
     * cutoff point to the right of the usual decimal point. A positive
     * number places the cutoff point to the left of the usual decimal
     * point.
     * @param rounding The rounding mode to use if the result must be scaled down
     * to have the same exponent as this value.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * returns not-a-number (NaN) if the divisor and the dividend are 0.
     * Returns NaN if the rounding mode is Rounding.Unnecessary and the
     * result is not exact.
     */
    public ExtendedDecimal DivideToExponent(ExtendedDecimal divisor,
      BigInteger desiredExponent,
      Rounding rounding) {
      if ((divisor) == null) {
        throw new NullPointerException("divisor");
      }
      if ((desiredExponent) == null) {
        throw new NullPointerException("desiredExponent");
      }
      return new ExtendedDecimal(this.ed.DivideToExponent(divisor.ed,
        desiredExponent.ei, ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Finds the absolute value of this object (if it&#x27;s negative, it becomes
     * positive).
     * @param context A precision context to control precision, rounding, and
     * exponent range of the result. If HasFlags of the context is true,
     * will also store the flags resulting from the operation (the flags are
     * in addition to the pre-existing flags). Can be null.
     * @return The absolute value of this object.
     */
    public ExtendedDecimal Abs(PrecisionContext context) {
      try {
  return new ExtendedDecimal(this.ed.Abs(context == null ? null :
          context.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but with the
     * sign reversed.
     * @param context A precision context to control precision, rounding, and
     * exponent range of the result. If HasFlags of the context is true,
     * will also store the flags resulting from the operation (the flags are
     * in addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal Negate(PrecisionContext context) {
      try {
        if ((context) == null) {
          throw new NullPointerException("context");
        }
        return new ExtendedDecimal(this.ed.Negate(context == null ? null :
          context.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Adds this object and another decimal number and returns the result.
     * @param otherValue An arbitrary-precision decimal object.
     * @return The sum of the two objects.
     */
    public ExtendedDecimal Add(ExtendedDecimal otherValue) {
      if ((otherValue) == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedDecimal(this.ed.Add(otherValue.ed));
    }

    /**
     * Subtracts an arbitrary-precision decimal object from this instance and
     * returns the result.
     * @param otherValue An arbitrary-precision decimal object.
     * @return The difference of the two objects.
     */
    public ExtendedDecimal Subtract(ExtendedDecimal otherValue) {
      if ((otherValue) == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedDecimal(this.ed.Subtract(otherValue.ed));
    }

    /**
     * Subtracts an arbitrary-precision decimal object from this instance.
     * @param otherValue An arbitrary-precision decimal object.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The difference of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     */
    public ExtendedDecimal Subtract(ExtendedDecimal otherValue,
      PrecisionContext ctx) {
      try {
        if ((otherValue) == null) {
          throw new NullPointerException("otherValue");
        }
return new ExtendedDecimal(this.ed.Subtract(otherValue.ed, ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Multiplies two decimal numbers. The resulting exponent will be the sum of
     * the exponents of the two decimal numbers.
     * @param otherValue Another decimal number.
     * @return The product of the two decimal numbers.
     */
    public ExtendedDecimal Multiply(ExtendedDecimal otherValue) {
      if ((otherValue) == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedDecimal(this.ed.Multiply(otherValue.ed));
    }

    /**
     * Multiplies by one decimal number, and then adds another decimal number.
     * @param multiplicand The value to multiply.
     * @param augend The value to add.
     * @return The result this * {@code multiplicand} + {@code augend}.
     */
    public ExtendedDecimal MultiplyAndAdd(ExtendedDecimal multiplicand,
      ExtendedDecimal augend) {
      if ((multiplicand) == null) {
        throw new NullPointerException("multiplicand");
      }
      if ((augend) == null) {
        throw new NullPointerException("augend");
      }
return new ExtendedDecimal(this.ed.MultiplyAndAdd(multiplicand.ed,
        augend.ed));
    }

    /**
     * Divides this object by another object, and returns the integer part of the
     * result, with the preferred exponent set to this value&#x27;s exponent
     * minus the divisor&#x27;s exponent.
     * @param divisor The divisor.
     * @param ctx A precision context object to control the precision, rounding,
     * and exponent range of the integer part of the result. Flags will be
     * set on the given context only if the context's HasFlags is true and
     * the integer part of the result doesn't fit the precision and exponent
     * range without rounding.
     * @return The integer part of the quotient of the two objects. Signals
     * FlagInvalid and returns not-a-number (NaN) if the return value would
     * overflow the exponent range. Signals FlagDivideByZero and returns
     * infinity if the divisor is 0 and the dividend is nonzero. Signals
     * FlagInvalid and returns not-a-number (NaN) if the divisor and the
     * dividend are 0. Signals FlagInvalid and returns not-a-number (NaN) if
     * the rounding mode is Rounding.Unnecessary and the result is not
     * exact.
     */
    public ExtendedDecimal DivideToIntegerNaturalScale(ExtendedDecimal divisor,
      PrecisionContext ctx) {
      try {
        if ((divisor) == null) {
          throw new NullPointerException("divisor");
        }
     return new ExtendedDecimal(this.ed.DivideToIntegerNaturalScale(divisor.ed,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides this object by another object, and returns the integer part of the
     * result, with the exponent set to 0.
     * @param divisor The divisor.
     * @param ctx A precision context object to control the precision. The rounding
     * and exponent range settings of this context are ignored. If HasFlags
     * of the context is true, will also store the flags resulting from the
     * operation (the flags are in addition to the pre-existing flags). Can
     * be null.
     * @return The integer part of the quotient of the two objects. The exponent
     * will be set to 0. Signals FlagDivideByZero and returns infinity if
     * the divisor is 0 and the dividend is nonzero. Signals FlagInvalid and
     * returns not-a-number (NaN) if the divisor and the dividend are 0, or
     * if the result doesn't fit the given precision.
     */
    public ExtendedDecimal DivideToIntegerZeroScale(ExtendedDecimal divisor,
      PrecisionContext ctx) {
      try {
        if ((divisor) == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(this.ed.DivideToIntegerZeroScale(divisor.ed,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the remainder that results when dividing two arbitrary-precision
     * decimal objects.
     * @param divisor An arbitrary-precision decimal object.
     * @param ctx Not documented yet.
     * @return The remainder of the two objects.
     */
    public ExtendedDecimal Remainder(ExtendedDecimal divisor,
      PrecisionContext ctx) {
      try {
        if ((divisor) == null) {
          throw new NullPointerException("divisor");
        }
  return new ExtendedDecimal(this.ed.Remainder(divisor.ed, ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the distance to the closest multiple of the given divisor, based on
     * the result of dividing this object&#x27;s value by another
     * object&#x27;s value. <ul> <li>If this and the other object divide
     * evenly, the result is 0.</li> <li>If the remainder's absolute value
     * is less than half of the divisor's absolute value, the result has the
     * same sign as this object and will be the distance to the closest
     * multiple.</li> <li>If the remainder's absolute value is more than
     * half of the divisor' s absolute value, the result has the opposite
     * sign of this object and will be the distance to the closest
     * multiple.</li> <li>If the remainder's absolute value is exactly half
     * of the divisor's absolute value, the result has the opposite sign of
     * this object if the quotient, rounded down, is odd, and has the same
     * sign as this object if the quotient, rounded down, is even, and the
     * result's absolute value is half of the divisor's absolute
     * value.</li></ul> This function is also known as the "IEEE Remainder"
     * function.
     * @param divisor The divisor.
     * @param ctx A precision context object to control the precision. The rounding
     * and exponent range settings of this context are ignored (the rounding
     * mode is always treated as HalfEven). If HasFlags of the context is
     * true, will also store the flags resulting from the operation (the
     * flags are in addition to the pre-existing flags). Can be null.
     * @return The distance of the closest multiple. Signals FlagInvalid and
     * returns not-a-number (NaN) if the divisor is 0, or either the result
     * of integer division (the quotient) or the remainder wouldn't fit the
     * given precision.
     */
    public ExtendedDecimal RemainderNear(ExtendedDecimal divisor,
      PrecisionContext ctx) {
      try {
        if ((divisor) == null) {
          throw new NullPointerException("divisor");
        }
     return new ExtendedDecimal(this.ed.RemainderNear(divisor.ed, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the largest value that&#x27;s smaller than the given value.
     * @param ctx A precision context object to control the precision and exponent
     * range of the result. The rounding mode from this context is ignored.
     * If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags).
     * @return Returns the largest value that's less than the given value. Returns
     * negative infinity if the result is negative infinity. Signals
     * FlagInvalid and returns not-a-number (NaN) if the parameter {@code
     * ctx} is null, the precision is 0, or {@code ctx} has an unlimited
     * exponent range.
     */
    public ExtendedDecimal NextMinus(PrecisionContext ctx) {
      try {
    return new ExtendedDecimal(this.ed.NextMinus(ctx == null ? null :
          ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the smallest value that&#x27;s greater than the given value.
     * @param ctx A precision context object to control the precision and exponent
     * range of the result. The rounding mode from this context is ignored.
     * If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags).
     * @return Returns the smallest value that's greater than the given
     * value.Signals FlagInvalid and returns not-a-number (NaN) if the
     * parameter {@code ctx} is null, the precision is 0, or {@code ctx} has
     * an unlimited exponent range.
     */
    public ExtendedDecimal NextPlus(PrecisionContext ctx) {
      try {
     return new ExtendedDecimal(this.ed.NextPlus(ctx == null ? null :
          ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the next value that is closer to the other object&#x27;s value than
     * this object&#x27;s value. Returns a copy of this value with the same
     * sign as the other value if both values are equal.
     * @param otherValue An arbitrary-precision decimal object.
     * @param ctx A precision context object to control the precision and exponent
     * range of the result. The rounding mode from this context is ignored.
     * If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags).
     * @return Returns the next value that is closer to the other object' s value
     * than this object's value. Signals FlagInvalid and returns NaN if the
     * parameter {@code ctx} is null, the precision is 0, or {@code ctx} has
     * an unlimited exponent range.
     */
    public ExtendedDecimal NextToward(ExtendedDecimal otherValue,
      PrecisionContext ctx) {
      try {
        if ((otherValue) == null) {
          throw new NullPointerException("otherValue");
        }
     return new ExtendedDecimal(this.ed.NextToward(otherValue.ed, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Gets the greater value between two decimal numbers.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The larger value of the two objects.
     */
    public static ExtendedDecimal Max(ExtendedDecimal first,
      ExtendedDecimal second,
      PrecisionContext ctx) {
      if ((first) == null) {
        throw new NullPointerException("first");
      }
      if ((second) == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.Max(first.ed, second.ed, ctx == null ?
        null : ctx.ec));
    }

    /**
     * Gets the lesser value between two decimal numbers.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The smaller value of the two objects.
     */
    public static ExtendedDecimal Min(ExtendedDecimal first,
      ExtendedDecimal second,
      PrecisionContext ctx) {
      if ((first) == null) {
        throw new NullPointerException("first");
      }
      if ((second) == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.Min(first.ed, second.ed, ctx == null ?
        null : ctx.ec));
    }

    /**
     * Gets the greater value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Max.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal object.
     */
    public static ExtendedDecimal MaxMagnitude(ExtendedDecimal first,
      ExtendedDecimal second,
      PrecisionContext ctx) {
      if ((first) == null) {
        throw new NullPointerException("first");
      }
      if ((second) == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.MaxMagnitude(first.ed, second.ed,
        ctx == null ? null : ctx.ec));
    }

    /**
     * Gets the lesser value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Min.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal object.
     */
    public static ExtendedDecimal MinMagnitude(ExtendedDecimal first,
      ExtendedDecimal second,
      PrecisionContext ctx) {
      if ((first) == null) {
        throw new NullPointerException("first");
      }
      if ((second) == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.MinMagnitude(first.ed, second.ed,
        ctx == null ? null : ctx.ec));
    }

    /**
     * Gets the greater value between two decimal numbers.
     * @param first An arbitrary-precision decimal object.
     * @param second Another arbitrary-precision decimal object.
     * @return The larger value of the two objects.
     */
    public static ExtendedDecimal Max(ExtendedDecimal first,
      ExtendedDecimal second) {
      if ((first) == null) {
        throw new NullPointerException("first");
      }
      if ((second) == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.Max(first.ed, second.ed));
    }

    /**
     * Gets the lesser value between two decimal numbers.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @return The smaller value of the two objects.
     */
    public static ExtendedDecimal Min(ExtendedDecimal first,
      ExtendedDecimal second) {
      if ((first) == null) {
        throw new NullPointerException("first");
      }
      if ((second) == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.Min(first.ed, second.ed));
    }

    /**
     * Gets the greater value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Max.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @return An arbitrary-precision decimal object.
     */
    public static ExtendedDecimal MaxMagnitude(ExtendedDecimal first,
      ExtendedDecimal second) {
      if ((first) == null) {
        throw new NullPointerException("first");
      }
      if ((second) == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.MaxMagnitude(first.ed, second.ed));
    }

    /**
     * Gets the lesser value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Min.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @return An arbitrary-precision decimal object.
     */
    public static ExtendedDecimal MinMagnitude(ExtendedDecimal first,
      ExtendedDecimal second) {
      if ((first) == null) {
        throw new NullPointerException("first");
      }
      if ((second) == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.MinMagnitude(first.ed, second.ed));
    }

    /**
     * Compares the mathematical values of this object and another object,
     * accepting NaN values. <p>This method is not consistent with the
     * Equals method because two different numbers with the same
     * mathematical value, but different exponents, will compare as
     * equal.</p> <p>In this method, negative zero and positive zero are
     * considered equal.</p> <p>If this object or the other object is a
     * quiet NaN or signaling NaN, this method will not trigger an error.
     * Instead, NaN will compare greater than any other number, including
     * infinity. Two different NaN values will be considered equal.</p>
     * @param other An arbitrary-precision decimal object.
     * @return Less than 0 if this object's value is less than the other value, or
     * greater than 0 if this object's value is greater than the other value
     * or if {@code other} is null, or 0 if both values are equal.
     */
    public int compareTo(ExtendedDecimal other) {
      if ((other) == null) {
        throw new NullPointerException("other");
      }
      return this.ed.compareTo(other.ed);
    }

    /**
     * Compares the mathematical values of this object and another object. <p>In
     * this method, negative zero and positive zero are considered
     * equal.</p> <p>If this object or the other object is a quiet NaN or
     * signaling NaN, this method returns a quiet NaN, and will signal a
     * FlagInvalid flag if either is a signaling NaN.</p>
     * @param other An arbitrary-precision decimal object.
     * @param ctx A precision context. The precision, rounding, and exponent range
     * are ignored. If HasFlags of the context is true, will store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null.
     * @return Quiet NaN if this object or the other object is NaN, or 0 if both
     * objects have the same value, or -1 if this object is less than the
     * other value, or 1 if this object is greater.
     */
    public ExtendedDecimal CompareToWithContext(ExtendedDecimal other,
      PrecisionContext ctx) {
      try {
        if ((other) == null) {
          throw new NullPointerException("other");
        }
return new ExtendedDecimal(this.ed.CompareToWithContext(other.ed, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Compares the mathematical values of this object and another object, treating
     * quiet NaN as signaling. <p>In this method, negative zero and positive
     * zero are considered equal.</p> <p>If this object or the other object
     * is a quiet NaN or signaling NaN, this method will return a quiet NaN
     * and will signal a FlagInvalid flag.</p>
     * @param other An arbitrary-precision decimal object.
     * @param ctx A precision context. The precision, rounding, and exponent range
     * are ignored. If HasFlags of the context is true, will store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null.
     * @return Quiet NaN if this object or the other object is NaN, or 0 if both
     * objects have the same value, or -1 if this object is less than the
     * other value, or 1 if this object is greater.
     */
    public ExtendedDecimal CompareToSignal(ExtendedDecimal other,
      PrecisionContext ctx) {
      try {
        if ((other) == null) {
          throw new NullPointerException("other");
        }
     return new ExtendedDecimal(this.ed.CompareToSignal(other.ed, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the sum of this object and another object. The result&#x27;s exponent
     * is set to the lower of the exponents of the two operands.
     * @param otherValue The number to add to.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The sum of thisValue and the other object.
     */
    public ExtendedDecimal Add(ExtendedDecimal otherValue,
      PrecisionContext ctx) {
      try {
        if ((otherValue) == null) {
          throw new NullPointerException("otherValue");
        }
     return new ExtendedDecimal(this.ed.Add(otherValue.ed, ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value but a new exponent. <p>Note
     * that this is not always the same as rounding to a given number of
     * decimal places, since it can fail if the difference between this
     * value's exponent and the desired exponent is too big, depending on
     * the maximum precision. If rounding to a number of decimal places is
     * desired, it's better to use the RoundToExponent and RoundToIntegral
     * methods instead.</p>
     * @param desiredExponent An arbitrary-precision integer.
     * @param ctx A precision context to control precision and rounding of the
     * result. If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null, in which case the default rounding
     * mode is HalfEven.
     * @return A decimal number with the same value as this object but with the
     * exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
     * if the rounded result can't fit the given precision, or if the
     * context defines an exponent range and the given exponent is outside
     * that range.
     */
    public ExtendedDecimal Quantize(BigInteger desiredExponent,
      PrecisionContext ctx) {
      try {
        if ((desiredExponent) == null) {
          throw new NullPointerException("desiredExponent");
        }
  return new ExtendedDecimal(this.ed.Quantize(desiredExponent.ei, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this one but a new exponent.
     * @param desiredExponentSmall A 32-bit signed integer.
     * @param rounding Not documented yet.
     * @return A decimal number with the same value as this object but with the
     * exponent changed. returns not-a-number (NaN) if the rounding mode is
     * Rounding.Unnecessary and the result is not exact.
     */
    public ExtendedDecimal Quantize(int desiredExponentSmall,
      Rounding rounding) {
      return new ExtendedDecimal(this.ed.Quantize(desiredExponentSmall,
        ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Returns a decimal number with the same value but a new exponent. <p>Note
     * that this is not always the same as rounding to a given number of
     * decimal places, since it can fail if the difference between this
     * value's exponent and the desired exponent is too big, depending on
     * the maximum precision. If rounding to a number of decimal places is
     * desired, it's better to use the RoundToExponent and RoundToIntegral
     * methods instead.</p>
     * @param desiredExponentSmall The desired exponent for the result. The
     * exponent is the number of fractional digits in the result, expressed
     * as a negative number. Can also be positive, which eliminates
     * lower-order places from the number. For example, -3 means round to
     * the thousandth (10^-3, 0.0001), and 3 means round to the thousand
     * (10^3, 1000). A value of 0 rounds the number to an integer.
     * @param ctx A precision context to control precision and rounding of the
     * result. If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null, in which case the default rounding
     * mode is HalfEven.
     * @return A decimal number with the same value as this object but with the
     * exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
     * if the rounded result can't fit the given precision, or if the
     * context defines an exponent range and the given exponent is outside
     * that range.
     */
    public ExtendedDecimal Quantize(int desiredExponentSmall,
      PrecisionContext ctx) {
      try {
return new ExtendedDecimal(this.ed.Quantize(desiredExponentSmall, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but with the
     * same exponent as another decimal number. <p>Note that this is not
     * always the same as rounding to a given number of decimal places,
     * since it can fail if the difference between this value's exponent and
     * the desired exponent is too big, depending on the maximum precision.
     * If rounding to a number of decimal places is desired, it's better to
     * use the RoundToExponent and RoundToIntegral methods instead.</p>
     * @param otherValue A decimal number containing the desired exponent of the
     * result. The mantissa is ignored. The exponent is the number of
     * fractional digits in the result, expressed as a negative number. Can
     * also be positive, which eliminates lower-order places from the
     * number. For example, -3 means round to the thousandth (10^-3,
     * 0.0001), and 3 means round to the thousand (10^3, 1000). A value of 0
     * rounds the number to an integer.
     * @param ctx A precision context to control precision and rounding of the
     * result. If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null, in which case the default rounding
     * mode is HalfEven.
     * @return A decimal number with the same value as this object but with the
     * exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding, or if
     * the precision context defines an exponent range and the given
     * exponent is outside that range.
     */
    public ExtendedDecimal Quantize(ExtendedDecimal otherValue,
      PrecisionContext ctx) {
      try {
        if ((otherValue) == null) {
          throw new NullPointerException("otherValue");
        }
return new ExtendedDecimal(this.ed.Quantize(otherValue.ed, ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to
     * an integer, and signals an invalid operation if the result would be
     * inexact.
     * @param ctx A precision context to control precision and rounding of the
     * result. If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null, in which case the default rounding
     * mode is HalfEven.
     * @return A decimal number rounded to the closest integer representable in the
     * given precision. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding. Signals
     * FlagInvalid and returns not-a-number (NaN) if the precision context
     * defines an exponent range, the new exponent must be changed to 0 when
     * rounding, and 0 is outside of the valid range of the precision
     * context.
     */
    public ExtendedDecimal RoundToIntegralExact(PrecisionContext ctx) {
      try {
   return new ExtendedDecimal(this.ed.RoundToIntegralExact(ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to
     * an integer, without adding the FlagInexact or FlagRounded flags.
     * @param ctx A precision context to control precision and rounding of the
     * result. If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags), except that this function will never add the
     * FlagRounded and FlagInexact flags (the only difference between this
     * and RoundToExponentExact). Can be null, in which case the default
     * rounding mode is HalfEven.
     * @return A decimal number rounded to the closest integer representable in the
     * given precision, meaning if the result can't fit the precision,
     * additional digits are discarded to make it fit. Signals FlagInvalid
     * and returns not-a-number (NaN) if the precision context defines an
     * exponent range, the new exponent must be changed to 0 when rounding,
     * and 0 is outside of the valid range of the precision context.
     */
    public ExtendedDecimal RoundToIntegralNoRoundedFlag(PrecisionContext ctx) {
      try {
  return new ExtendedDecimal(this.ed.RoundToIntegralNoRoundedFlag(ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to
     * an integer, and signals an invalid operation if the result would be
     * inexact.
     * @param exponent The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places from the number. For example, -3 means round to the thousandth
     * (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
     * value of 0 rounds the number to an integer.
     * @param ctx An EContext object.
     * @return A decimal number rounded to the closest value representable in the
     * given precision. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding. Signals
     * FlagInvalid and returns not-a-number (NaN) if the precision context
     * defines an exponent range, the new exponent must be changed to the
     * given exponent when rounding, and the given exponent is outside of
     * the valid range of the precision context.
     */
    public ExtendedDecimal RoundToExponentExact(BigInteger exponent,
      PrecisionContext ctx) {
      try {
        if ((exponent) == null) {
          throw new NullPointerException("exponent");
        }
        return new ExtendedDecimal(this.ed.RoundToExponentExact(exponent.ei,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object, and rounds it
     * to a new exponent if necessary.
     * @param exponent The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places from the number. For example, -3 means round to the thousandth
     * (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
     * value of 0 rounds the number to an integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null, in which case the
     * default rounding mode is HalfEven.
     * @return A decimal number rounded to the closest value representable in the
     * given precision, meaning if the result can't fit the precision,
     * additional digits are discarded to make it fit. Signals FlagInvalid
     * and returns not-a-number (NaN) if the precision context defines an
     * exponent range, the new exponent must be changed to the given
     * exponent when rounding, and the given exponent is outside of the
     * valid range of the precision context.
     */
    public ExtendedDecimal RoundToExponent(BigInteger exponent,
      PrecisionContext ctx) {
      try {
        if ((exponent) == null) {
          throw new NullPointerException("exponent");
        }
  return new ExtendedDecimal(this.ed.RoundToExponent(exponent.ei, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to
     * an integer, and signals an invalid operation if the result would be
     * inexact.
     * @param exponentSmall The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places from the number. For example, -3 means round to the thousandth
     * (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
     * value of 0 rounds the number to an integer.
     * @param ctx An EContext object.
     * @return A decimal number rounded to the closest value representable in the
     * given precision. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding. Signals
     * FlagInvalid and returns not-a-number (NaN) if the precision context
     * defines an exponent range, the new exponent must be changed to the
     * given exponent when rounding, and the given exponent is outside of
     * the valid range of the precision context.
     */
    public ExtendedDecimal RoundToExponentExact(int exponentSmall,
      PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.ed.RoundToExponentExact(exponentSmall,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object, and rounds it
     * to a new exponent if necessary.
     * @param exponentSmall The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places from the number. For example, -3 means round to the thousandth
     * (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
     * value of 0 rounds the number to an integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null, in which case the
     * default rounding mode is HalfEven.
     * @return A decimal number rounded to the closest value representable in the
     * given precision, meaning if the result can't fit the precision,
     * additional digits are discarded to make it fit. Signals FlagInvalid
     * and returns not-a-number (NaN) if the precision context defines an
     * exponent range, the new exponent must be changed to the given
     * exponent when rounding, and the given exponent is outside of the
     * valid range of the precision context.
     */
    public ExtendedDecimal RoundToExponent(int exponentSmall,
      PrecisionContext ctx) {
      try {
return new ExtendedDecimal(this.ed.RoundToExponent(exponentSmall, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Multiplies two decimal numbers. The resulting scale will be the sum of the
     * scales of the two decimal numbers. The result&#x27;s sign is positive
     * if both operands have the same sign, and negative if they have
     * different signs.
     * @param op Another decimal number.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The product of the two decimal numbers.
     */
    public ExtendedDecimal Multiply(ExtendedDecimal op, PrecisionContext ctx) {
      try {
        if ((op) == null) {
          throw new NullPointerException("op");
        }
        return new ExtendedDecimal(this.ed.Multiply(op.ed, ctx == null ? null :
          ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Multiplies by one value, and then adds another value.
     * @param op The value to multiply.
     * @param augend The value to add.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The result thisValue * multiplicand + augend.
     */
    public ExtendedDecimal MultiplyAndAdd(ExtendedDecimal op,
      ExtendedDecimal augend,
      PrecisionContext ctx) {
      try {
        if ((op) == null) {
          throw new NullPointerException("op");
        }
        if ((augend) == null) {
          throw new NullPointerException("augend");
        }
        return new ExtendedDecimal(this.ed.MultiplyAndAdd(op.ed, augend.ed,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Multiplies by one value, and then subtracts another value.
     * @param op The value to multiply.
     * @param subtrahend The value to subtract.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The result thisValue * multiplicand - subtrahend.
     * @throws java.lang.NullPointerException The parameter {@code op} or {@code
     * subtrahend} is null.
     */
    public ExtendedDecimal MultiplyAndSubtract(ExtendedDecimal op,
      ExtendedDecimal subtrahend,
      PrecisionContext ctx) {
      try {
        if ((op) == null) {
          throw new NullPointerException("op");
        }
        if ((subtrahend) == null) {
          throw new NullPointerException("subtrahend");
        }
   return new ExtendedDecimal(this.ed.MultiplyAndSubtract(op.ed,
          subtrahend.ed, ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Rounds this object&#x27;s value to a given precision, using the given
     * rounding mode and range of exponent.
     * @param ctx A context for controlling the precision, rounding mode, and
     * exponent range. Can be null.
     * @return The closest value to this object's value, rounded to the specified
     * precision. Returns the same value as this object if {@code ctx} is
     * null or the precision and exponent range are unlimited.
     */
    public ExtendedDecimal RoundToPrecision(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.ed.RoundToPrecision(ctx == null ? null :
          ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Rounds this object&#x27;s value to a given precision, using the given
     * rounding mode and range of exponent, and also converts negative zero
     * to positive zero.
     * @param ctx A context for controlling the precision, rounding mode, and
     * exponent range. Can be null.
     * @return The closest value to this object's value, rounded to the specified
     * precision. Returns the same value as this object if {@code ctx} is
     * null or the precision and exponent range are unlimited.
     */
    public ExtendedDecimal Plus(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.ed.Plus(ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Rounds this object&#x27;s value to a given maximum bit length, using the
     * given rounding mode and range of exponent.
     * @param ctx A context for controlling the precision, rounding mode, and
     * exponent range. The precision is interpreted as the maximum bit
     * length of the mantissa. Can be null.
     * @return The closest value to this object's value, rounded to the specified
     * precision. Returns the same value as this object if {@code ctx} is
     * null or the precision and exponent range are unlimited.
     * @deprecated Instead of this method use RoundToPrecision and pass a precision context
* with the IsPrecisionInBits property set.
 */
@Deprecated
    public ExtendedDecimal RoundToBinaryPrecision(PrecisionContext ctx) {
      try {
 return new ExtendedDecimal(this.ed.RoundToBinaryPrecision(ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the square root of this object&#x27;s value.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). --This parameter cannot be null,
     * as the square root function's results are generally not exact for
     * many inputs.--.
     * @return The square root. Signals the flag FlagInvalid and returns NaN if
     * this object is less than 0 (the square root would be a complex
     * number, but the return value is still NaN). Signals FlagInvalid and
     * returns not-a-number (NaN) if the parameter {@code ctx} is null or
     * the precision is unlimited (the context's Precision property is 0).
     */
    public ExtendedDecimal SquareRoot(PrecisionContext ctx) {
      try {
   return new ExtendedDecimal(this.ed.SquareRoot(ctx == null ? null :
          ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds e (the base of natural logarithms) raised to the power of this
     * object&#x27;s value.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). --This parameter cannot be null,
     * as the exponential function's results are generally not exact.--.
     * @return Exponential of this object. If this object's value is 1, returns an
     * approximation to " e" within the given precision. Signals FlagInvalid
     * and returns not-a-number (NaN) if the parameter {@code ctx} is null
     * or the precision is unlimited (the context's Precision property is
     * 0).
     */
    public ExtendedDecimal Exp(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.ed.Exp(ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the natural logarithm of this object, that is, the power (exponent)
     * that e (the base of natural logarithms) must be raised to in order to
     * equal this object&#x27;s value.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). --This parameter cannot be null,
     * as the ln function's results are generally not exact.--.
     * @return Ln(this object). Signals the flag FlagInvalid and returns NaN if
     * this object is less than 0 (the result would be a complex number with
     * a real part equal to Ln of this object's absolute value and an
     * imaginary part equal to pi, but the return value is still NaN.).
     * Signals FlagInvalid and returns not-a-number (NaN) if the parameter
     * {@code ctx} is null or the precision is unlimited (the context's
     * Precision property is 0). Signals no flags and returns negative
     * infinity if this object's value is 0.
     */
    public ExtendedDecimal Log(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.ed.Log(ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the base-10 logarithm of this object, that is, the power (exponent)
     * that the number 10 must be raised to in order to equal this
     * object&#x27;s value.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). --This parameter cannot be null,
     * as the ln function's results are generally not exact.--.
     * @return Ln(this object)/Ln(10). Signals the flag FlagInvalid and returns
     * not-a-number (NaN) if this object is less than 0. Signals FlagInvalid
     * and returns not-a-number (NaN) if the parameter {@code ctx} is null
     * or the precision is unlimited (the context's Precision property is
     * 0).
     */
    public ExtendedDecimal Log10(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.ed.Log10(ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Raises this object&#x27;s value to the given exponent.
     * @param exponent An arbitrary-precision decimal object.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags).
     * @return This^exponent. Signals the flag FlagInvalid and returns NaN if this
     * object and exponent are both 0; or if this value is less than 0 and
     * the exponent either has a fractional part or is infinity. Signals
     * FlagInvalid and returns not-a-number (NaN) if the parameter {@code
     * ctx} is null or the precision is unlimited (the context's Precision
     * property is 0), and the exponent has a fractional part.
     */
    public ExtendedDecimal Pow(ExtendedDecimal exponent, PrecisionContext ctx) {
      try {
        if ((exponent) == null) {
          throw new NullPointerException("exponent");
        }
        return new ExtendedDecimal(this.ed.Pow(exponent.ed, ctx == null ? null :
          ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Raises this object&#x27;s value to the given exponent.
     * @param exponentSmall A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags).
     * @return This^exponent. Signals the flag FlagInvalid and returns NaN if this
     * object and exponent are both 0.
     */
    public ExtendedDecimal Pow(int exponentSmall, PrecisionContext ctx) {
      try {
     return new ExtendedDecimal(this.ed.Pow(exponentSmall, ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Raises this object&#x27;s value to the given exponent.
     * @param exponentSmall A 32-bit signed integer.
     * @return This^exponent. returns not-a-number (NaN) if this object and
     * exponent are both 0.
     */
    public ExtendedDecimal Pow(int exponentSmall) {
      return new ExtendedDecimal(this.ed.Pow(exponentSmall));
    }

    /**
     * Finds the constant pi.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). --This parameter cannot be null,
     * as pi can never be represented exactly.--.
     * @return Pi rounded to the given precision. Signals FlagInvalid and returns
     * not-a-number (NaN) if the parameter {@code ctx} is null or the
     * precision is unlimited (the context's Precision property is 0).
     */
    public static ExtendedDecimal PI(PrecisionContext ctx) {
      return new ExtendedDecimal(EDecimal.PI(ctx == null ? null : ctx.ec));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the left.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal MovePointLeft(int places) {
      return new ExtendedDecimal(this.ed.MovePointLeft(places));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the left.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal MovePointLeft(int places, PrecisionContext ctx) {
      try {
  return new ExtendedDecimal(this.ed.MovePointLeft(places, ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the left.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal MovePointLeft(BigInteger bigPlaces) {
      if ((bigPlaces) == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedDecimal(this.ed.MovePointLeft(bigPlaces.ei));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the left.
     * @param bigPlaces An arbitrary-precision integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal MovePointLeft(BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if ((bigPlaces) == null) {
          throw new NullPointerException("bigPlaces");
        }
        return new ExtendedDecimal(this.ed.MovePointLeft(bigPlaces.ei,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the right.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal MovePointRight(int places) {
      return new ExtendedDecimal(this.ed.MovePointRight(places));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the right.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal MovePointRight(int places, PrecisionContext ctx) {
      try {
 return new ExtendedDecimal(this.ed.MovePointRight(places, ctx == null ?
          null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the right.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal MovePointRight(BigInteger bigPlaces) {
      if ((bigPlaces) == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedDecimal(this.ed.MovePointRight(bigPlaces.ei));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the right.
     * @param bigPlaces An arbitrary-precision integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return A number whose scale is increased by {@code bigPlaces}, but not to
     * more than 0.
     */
    public ExtendedDecimal MovePointRight(BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if ((bigPlaces) == null) {
          throw new NullPointerException("bigPlaces");
        }

  return new ExtendedDecimal(this.ed.MovePointRight(bigPlaces.ei, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal ScaleByPowerOfTen(int places) {
      return new ExtendedDecimal(this.ed.ScaleByPowerOfTen(places));
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal ScaleByPowerOfTen(int places, PrecisionContext ctx) {
      try {
     return new ExtendedDecimal(this.ed.ScaleByPowerOfTen(places, ctx ==
          null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal ScaleByPowerOfTen(BigInteger bigPlaces) {
      if ((bigPlaces) == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedDecimal(this.ed.ScaleByPowerOfTen(bigPlaces.ei));
    }

    /**
     * Returns a number similar to this number but with its scale adjusted.
     * @param bigPlaces An arbitrary-precision integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return A number whose scale is increased by {@code bigPlaces}.
     */
    public ExtendedDecimal ScaleByPowerOfTen(BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if ((bigPlaces) == null) {
          throw new NullPointerException("bigPlaces");
        }

        return new ExtendedDecimal(this.ed.ScaleByPowerOfTen(bigPlaces.ei,
          ctx == null ? null : ctx.ec));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the number of digits in this number's mantissa. Returns 1 if this
     * value is 0, and 0 if this value is infinity or NaN.
     * @return An arbitrary-precision integer.
     */
    public BigInteger Precision() {
      return new BigInteger(this.ed.Precision());
    }

    /**
     * Returns the unit in the last place. The mantissa will be 1 and the exponent
     * will be this number's exponent. Returns 1 with an exponent of 0 if
     * this number is infinity or NaN.
     * @return An arbitrary-precision decimal object.
     */
    public ExtendedDecimal Ulp() {
      return new ExtendedDecimal(this.ed.Ulp());
    }

    /**
     * Calculates the quotient and remainder using the DivideToIntegerNaturalScale
     * and the formula in RemainderNaturalScale.
     * @param divisor The number to divide by.
     * @return A 2 element array consisting of the quotient and remainder in that
     * order.
     */
    public ExtendedDecimal[] DivideAndRemainderNaturalScale(ExtendedDecimal
      divisor) {
      EDecimal[] edec = this.ed.DivideAndRemainderNaturalScale(divisor ==
        null ? null : divisor.ed);
      return new ExtendedDecimal[] {
        new ExtendedDecimal(edec[0]), new ExtendedDecimal(edec[1])
      };
    }

    /**
     * Calculates the quotient and remainder using the DivideToIntegerNaturalScale
     * and the formula in RemainderNaturalScale.
     * @param divisor The number to divide by.
     * @param ctx A precision context object to control the precision, rounding,
     * and exponent range of the result. This context will be used only in
     * the division portion of the remainder calculation; as a result, it's
     * possible for the remainder to have a higher precision than given in
     * this context. Flags will be set on the given context only if the
     * context's HasFlags is true and the integer part of the division
     * result doesn't fit the precision and exponent range without rounding.
     * @return A 2 element array consisting of the quotient and remainder in that
     * order.
     */
    public ExtendedDecimal[] DivideAndRemainderNaturalScale(
      ExtendedDecimal divisor,
      PrecisionContext ctx) {
      try {
        EDecimal[] edec = this.ed.DivideAndRemainderNaturalScale(divisor ==
          null ? null : divisor.ed,
          ctx == null ? null : ctx.ec);
        return new ExtendedDecimal[] {
        new ExtendedDecimal(edec[0]), new ExtendedDecimal(edec[1])
      };
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }
  }
