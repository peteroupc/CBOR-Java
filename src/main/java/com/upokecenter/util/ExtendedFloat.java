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
     * <p><b>This class is largely obsolete. It will be replaced by a new version
     * of this class in a different namespace/package and library, called
     * <code>PeterO.Numbers.EFloat</code> in the <code>PeterO.Numbers</code> library (in
     * .NET), or <code>com.upokecenter.numbers.getEFloat()</code> in the
     * <code>com.github.peteroupc/numbers</code> artifact (in Java). This new
     * class can be used in the <code>CBORObject.FromObject(Object)</code> method
     * (by including the new library in your code, among other things), but
     * this version of the CBOR library doesn't include any methods that
     * explicitly take an <code>EFloat</code> as a parameter or return
     * value.</b></p> <p> Represents an arbitrary-precision binary
     * floating-point number. Consists of an integer mantissa and an integer
     * exponent, both arbitrary-precision. The value of the number equals
     * mantissa * 2^exponent. This class also supports values for negative
     * zero, not-a-number (NaN) values, and infinity.</p> <p>Passing a
     * signaling NaN to any arithmetic operation shown here will signal the
     * flag FlagInvalid and return a quiet NaN, even if another operand to
     * that operation is a quiet NaN, unless noted otherwise.</p> <p>Passing
     * a quiet NaN to any arithmetic operation shown here will return a
     * quiet NaN, unless noted otherwise.</p> <p>Unless noted otherwise,
     * passing a null arbitrary-precision binary float argument to any
     * method here will throw an exception.</p> <p>When an arithmetic
     * operation signals the flag FlagInvalid, FlagOverflow, or
     * FlagDivideByZero, it will not throw an exception too, unless the
     * operation's trap is enabled in the precision context (see
     * PrecisionContext's Traps property).</p> <p>An arbitrary-precision
     * binary float value can be serialized in one of the following
     * ways:</p> <ul> <li>By calling the toString() method. However, not all
     * strings can be converted back to an arbitrary-precision binary float
     * without loss, especially if the string has a fractional part.</li>
     * <li>By calling the UnsignedMantissa, Exponent, and IsNegative
     * properties, and calling the IsInfinity, IsQuietNaN, and
     * IsSignalingNaN methods. The return values combined will uniquely
     * identify a particular arbitrary-precision binary float
     * value.</li></ul> <p>If an operation requires creating an intermediate
     * value that might be too big to fit in memory (or might require more
     * than 2 gigabytes of memory to store -- due to the current use of a
     * 32-bit integer internally as a length), the operation may signal an
     * invalid-operation flag and return not-a-number (NaN). In certain rare
     * cases, the compareTo method may throw OutOfMemoryError (called
     * OutOfMemoryError in Java) in the same circumstances.</p> <p><b>Thread
     * safety:</b>Instances of this class are immutable, so they are
     * inherently safe for use by multiple threads. Multiple instances of
     * this object with the same properties are interchangeable, so they
     * should not be compared using the "==" operator (which only checks if
     * each side of the operator is the same instance).</p>
     */
  public final class ExtendedFloat implements Comparable<ExtendedFloat> {
    private final EFloat ef;

    ExtendedFloat(EFloat ef) {
      this.ef = ef;
    }

    /**
     * Gets this object&#x27;s exponent. This object&#x27;s value will be an
     * integer if the exponent is positive or zero.
     * @return This object's exponent. This object's value will be an integer if
     * the exponent is positive or zero.
     */
    public final BigInteger getExponent() {
        return new BigInteger(this.getEf().getExponent());
      }

    /**
     * Gets the absolute value of this object&#x27;s un-scaled value.
     * @return The absolute value of this object's un-scaled value.
     */
    public final BigInteger getUnsignedMantissa() {
        return new BigInteger(this.getEf().getUnsignedMantissa());
      }

    /**
     * Gets this object&#x27;s un-scaled value.
     * @return This object's un-scaled value. Will be negative if this object's
     * value is negative (including a negative NaN).
     */
    public final BigInteger getMantissa() {
        return new BigInteger(this.getEf().getMantissa());
      }

    static ExtendedFloat ToLegacy(EFloat ei) {
      return new ExtendedFloat(ei);
    }
    static EFloat FromLegacy(ExtendedFloat bei) {
      return bei.getEf();
    }

    /**
     * Determines whether this object's mantissa and exponent are equal to those of
     * another object.
     * @param otherValue An arbitrary-precision binary float.
     * @return True if this object's mantissa and exponent are equal to those of
     * another object; otherwise, false.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean EqualsInternal(ExtendedFloat otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return this.getEf().EqualsInternal(otherValue.getEf());
    }

    /**
     * Determines whether this object's mantissa and exponent are equal to those of
     * another object.
     * @param other An arbitrary-precision binary float.
     * @return True if this object's mantissa and exponent are equal to those of
     * another object; otherwise, false.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean equals(ExtendedFloat other) {
      if (other == null) {
        throw new NullPointerException("other");
      }
      return this.getEf().equals(other.getEf());
    }

    /**
     * Determines whether this object&#x27;s mantissa and exponent are equal to
     * those of another object and that other object is an
     * arbitrary-precision decimal number.
     * @param obj An arbitrary object.
     * @return True if the objects are equal; otherwise, false.
     */
    @Override public boolean equals(Object obj) {
      ExtendedFloat bi = ((obj instanceof ExtendedFloat) ? (ExtendedFloat)obj : null);
      return (bi == null) ? false : this.getEf().equals(bi.getEf());
    }

    /**
     * Calculates this object&#x27;s hash code.
     * @return This object's hash code.
     */
    @Override public int hashCode() {
      return this.getEf().hashCode();
    }

    /**
     * Creates a not-a-number arbitrary-precision binary float.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @return A quiet not-a-number.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null.
     * @throws IllegalArgumentException The parameter {@code diag} is less than 0.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat CreateNaN(BigInteger diag) {
      if (diag == null) {
        throw new NullPointerException("diag");
      }
      return new ExtendedFloat(EFloat.CreateNaN(diag.getEi()));
    }

    /**
     * Creates a not-a-number arbitrary-precision binary float.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @param signaling Whether the return value will be signaling (true) or quiet
     * (false).
     * @param negative Whether the return value is negative.
     * @param ctx A PrecisionContext object.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null.
     * @throws IllegalArgumentException The parameter {@code diag} is less than 0.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat CreateNaN(
BigInteger diag,
boolean signaling,
boolean negative,
PrecisionContext ctx) {
      if (diag == null) {
        throw new NullPointerException("diag");
      }

      return new ExtendedFloat(
EFloat.CreateNaN(
diag.getEi(),
signaling,
negative,
ctx == null ? null : ctx.getEc()));
    }

    /**
     * Creates a number with the value exponent*2^mantissa.
     * @param mantissaSmall The un-scaled value.
     * @param exponentSmall The binary exponent.
     * @return An arbitrary-precision binary float.
     */
    public static ExtendedFloat Create(int mantissaSmall, int exponentSmall) {
      return new ExtendedFloat(EFloat.Create(mantissaSmall, exponentSmall));
    }

    /**
     * Creates a number with the value exponent*2^mantissa.
     * @param mantissa The un-scaled value.
     * @param exponent The binary exponent.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code mantissa} or
     * {@code exponent} is null.
     */
    public static ExtendedFloat Create(
BigInteger mantissa,
BigInteger exponent) {
      if (mantissa == null) {
        throw new NullPointerException("mantissa");
      }
      if (exponent == null) {
        throw new NullPointerException("exponent");
      }
      return new ExtendedFloat(EFloat.Create(mantissa.getEi(), exponent.getEi()));
    }

    /**
     * Creates a binary float from a string that represents a number. Note that if
     * the string contains a negative exponent, the resulting value might
     * not be exact, in which case the resulting binary float will be an
     * approximation of this decimal number's value. (NOTE: This
     * documentation previously said the binary float will contain enough
     * precision to accurately convert it to a 32-bit or 64-bit floating
     * point number. Due to double rounding, this will generally not be the
     * case for certain numbers converted from decimal to ExtendedFloat via
     * this method and in turn converted to <code>double</code> or <code>float</code>.)
     * <p>The format of the string generally consists of:</p> <ul> <li>An
     * optional plus sign ("+" , U+002B) or minus sign ("-", U+002D) (if '-'
     * , the value is negative.)</li> <li>One or more digits, with a single
     * optional decimal point after the first digit and before the last
     * digit.</li> <li>Optionally, "E+"/"e+" (positive exponent) or
     * "E-"/"e-" (negative exponent) plus one or more digits specifying the
     * exponent.</li></ul> <p>The string can also be "-INF", "-Infinity",
     * "Infinity", "INF", quiet NaN ("NaN") followed by any number of
     * digits, or signaling NaN ("sNaN") followed by any number of digits,
     * all in any combination of upper and lower case.</p> <p>All characters
     * mentioned above are the corresponding characters in the Basic Latin
     * range. In particular, the digits must be the basic digits 0 to 9
     * (U + 0030 to U + 0039). The string is not allowed to contain white space
     * characters, including spaces.</p>
     * @param str A text string.
     * @param offset A zero-based index showing where the desired portion of {@code
     * str} begins.
     * @param length The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @param ctx A PrecisionContext object specifying the precision, rounding, and
     * exponent range to apply to the parsed number. Can be null.
     * @return The parsed number, converted to arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws IllegalArgumentException Either {@code offset} or {@code length} is
     * less than 0 or greater than {@code str} 's length, or {@code str} ' s
     * length minus {@code offset} is less than {@code length}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat FromString(
String str,
int offset,
int length,
PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
  EFloat.FromString(
  str,
  offset,
  length,
  ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Creates a binary float from a string that represents a number. See the
     * four-parameter FromString method.
     * @param str The parameter {@code str} is not documented yet.
     * @return The parsed number, converted to arbitrary-precision binary float.
     */
    public static ExtendedFloat FromString(String str) {
      return new ExtendedFloat(EFloat.FromString(str));
    }

    /**
     * Not documented yet.
     * @param str A text string.
     * @param ctx A PrecisionContext object specifying the precision, rounding, and
     * exponent range to apply to the parsed number. Can be null.
     * @return The parsed number, converted to arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat FromString(String str, PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
  EFloat.FromString(
  str,
  ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Not documented yet.
     * @param str A text string.
     * @param offset A zero-based index showing where the desired portion of {@code
     * str} begins.
     * @param length The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws IllegalArgumentException Either {@code offset} or {@code length} is
     * less than 0 or greater than {@code str} 's length, or {@code str} ' s
     * length minus {@code offset} is less than {@code length}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat FromString(String str, int offset, int length) {
      return new ExtendedFloat(EFloat.FromString(str, offset, length));
    }

    /**
     * Converts this value to an arbitrary-precision integer. Any fractional part
     * of this value will be discarded when converting to a big integer.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public BigInteger ToBigInteger() {
      return new BigInteger(this.getEf().ToEInteger());
    }

    /**
     * Converts this value to an arbitrary-precision integer, checking whether the
     * value contains a fractional part.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     * @throws ArithmeticException This object's value is not an exact integer.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public BigInteger ToBigIntegerExact() {
      return new BigInteger(this.getEf().ToEIntegerExact());
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
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public float ToSingle() {
      return this.getEf().ToSingle();
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
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public double ToDouble() {
      return this.getEf().ToDouble();
    }

    /**
     * Creates a binary float from a 32-bit floating-point number. This method
     * computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the floating point
     * number to a string first.
     * @param flt A 32-bit floating-point number.
     * @return A binary float with the same value as {@code flt}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat FromSingle(float flt) {
      return new ExtendedFloat(EFloat.FromSingle(flt));
    }

    /**
     * Converts a big integer to the same value as a binary float.
     * @param bigint An arbitrary-precision integer.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code bigint} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat FromBigInteger(BigInteger bigint) {
      if (bigint == null) {
        throw new NullPointerException("bigint");
      }
      return new ExtendedFloat(EFloat.FromEInteger(bigint.getEi()));
    }

    /**
     * Converts a 64-bit integer to the same value as a binary float.
     * @param valueSmall A 64-bit signed integer.
     * @return An arbitrary-precision binary float with the exponent set to 0.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat FromInt64(long valueSmall) {
      return new ExtendedFloat(EFloat.FromInt64(valueSmall));
    }

    /**
     * Creates a binary float from a 32-bit signed integer.
     * @param valueSmaller A 32-bit signed integer.
     * @return An arbitrary-precision binary float with the exponent set to 0.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat FromInt32(int valueSmaller) {
      return new ExtendedFloat(EFloat.FromInt32(valueSmaller));
    }

    /**
     * Creates a binary float from a 64-bit floating-point number. This method
     * computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the floating point
     * number to a string first.
     * @param dbl A 64-bit floating-point number.
     * @return A binary float with the same value as {@code dbl}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat FromDouble(double dbl) {
      return new ExtendedFloat(EFloat.FromDouble(dbl));
    }

    /**
     * Converts this value to an extended decimal.
     * @return An extended decimal with the same value as this extended float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal ToExtendedDecimal() {
      return new ExtendedDecimal(this.getEf().ToExtendedDecimal());
    }

    /**
     * Converts this value to a string.
     * @return A string representation of this object. The value is converted to
     * decimal and the decimal form of this number's value is returned.
     */
    @Override public String toString() {
      return this.getEf().toString();
    }

    /**
     * Converts this value to an extended decimal, then returns the value of that
     * decimal's ToEngineeringString method.
     * @return A text string.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public String ToEngineeringString() {
      return this.getEf().ToEngineeringString();
    }

    /**
     * Converts this value to a string, but without exponential notation.
     * @return A text string.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public String ToPlainString() {
      return this.getEf().ToPlainString();
    }

    /**
     * Represents the number 1.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final ExtendedFloat One =
     new ExtendedFloat(EFloat.One);

    /**
     * Represents the number 0.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final ExtendedFloat Zero =
     new ExtendedFloat(EFloat.Zero);

    /**
     * Represents the number negative zero.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final ExtendedFloat NegativeZero =
     new ExtendedFloat(EFloat.NegativeZero);

    /**
     * Represents the number 10.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final ExtendedFloat Ten =
     new ExtendedFloat(EFloat.Ten);

    //----------------------------------------------------------------

    /**
     * A not-a-number value.
     */
    public static final ExtendedFloat NaN =
     new ExtendedFloat(EFloat.NaN);

    /**
     * A not-a-number value that signals an invalid operation flag when it&#x27;s
     * passed as an argument to any arithmetic operation in
     * arbitrary-precision binary float.
     */
    public static final ExtendedFloat SignalingNaN =
     new ExtendedFloat(EFloat.SignalingNaN);

    /**
     * Positive infinity, greater than any other number.
     */
    public static final ExtendedFloat PositiveInfinity =
     new ExtendedFloat(EFloat.PositiveInfinity);

    /**
     * Negative infinity, less than any other number.
     */
    public static final ExtendedFloat NegativeInfinity =
     new ExtendedFloat(EFloat.NegativeInfinity);

    /**
     * Returns whether this object is negative infinity.
     * @return True if this object is negative infinity; otherwise, false.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsNegativeInfinity() {
      return this.getEf().IsNegativeInfinity();
    }

    /**
     * Returns whether this object is positive infinity.
     * @return True if this object is positive infinity; otherwise, false.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsPositiveInfinity() {
      return this.getEf().IsPositiveInfinity();
    }

    /**
     * Returns whether this object is a not-a-number value.
     * @return True if this object is a not-a-number value; otherwise, false.
     */
    public boolean IsNaN() {
      return this.getEf().IsNaN();
    }

    /**
     * Gets a value indicating whether this object is positive or negative
     * infinity.
     * @return True if this object is positive or negative infinity; otherwise,
     * false.
     */
    public boolean IsInfinity() {
      return this.getEf().IsInfinity();
    }

    /**
     * Gets a value indicating whether this object is finite (not infinity or NaN).
     * @return True if this object is finite (not infinity or NaN); otherwise,
     * false.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isFinite() {
        return this.getEf().isFinite();
      }

    /**
     * Gets a value indicating whether this object is negative, including negative
     * zero.
     * @return True if this object is negative, including negative zero; otherwise,
     * false.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isNegative() {
        return this.getEf().isNegative();
      }

    /**
     * Gets a value indicating whether this object is a quiet not-a-number value.
     * @return True if this object is a quiet not-a-number value; otherwise, false.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsQuietNaN() {
      return this.getEf().IsQuietNaN();
    }

    /**
     * Gets a value indicating whether this object is a signaling not-a-number
     * value.
     * @return True if this object is a signaling not-a-number value; otherwise,
     * false.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsSignalingNaN() {
      return this.getEf().IsSignalingNaN();
    }

    /**
     * Gets this value&#x27;s sign: -1 if negative; 1 if positive; 0 if zero.
     * @return This value's sign: -1 if negative; 1 if positive; 0 if zero.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final int signum() {
        return this.getEf().signum();
      }

    /**
     * Gets a value indicating whether this object&#x27;s value equals 0.
     * @return True if this object's value equals 0; otherwise, false.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isZero() {
        return this.getEf().isZero();
      }

    final EFloat getEf() {
        return this.ef;
      }

    /**
     * Gets the absolute value of this object.
     * @return An arbitrary-precision binary float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Abs() {
      return new ExtendedFloat(this.getEf().Abs(null));
    }

    /**
     * Gets an object with the same value as this one, but with the sign reversed.
     * @return An arbitrary-precision binary float. If this value is positive zero,
     * returns positive zero.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Negate() {
      return new ExtendedFloat(this.getEf().Negate(null));
    }

    /**
     * Divides this object by another binary float and returns the result. When
     * possible, the result will be exact.
     * @param divisor The divisor.
     * @return The quotient of the two numbers. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0.
     * @throws ArithmeticException The result can't be exact because it would have
     * a nonterminating binary expansion.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Divide(ExtendedFloat divisor) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedFloat(this.getEf().Divide(divisor.getEf()));
    }

    /**
     * Divides this object by another binary float and returns a result with the
     * same exponent as this object (the dividend).
     * @param divisor The divisor.
     * @param rounding The rounding mode to use if the result must be scaled down
     * to have the same exponent as this value.
     * @return The quotient of the two numbers. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0.
     * @throws ArithmeticException The rounding mode is Rounding.Unnecessary and
     * the result is not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat DivideToSameExponent(
ExtendedFloat divisor,
Rounding rounding) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedFloat(
this.getEf().DivideToSameExponent(
divisor.getEf(),
ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Divides two arbitrary-precision binary floats, and returns the integer part
     * of the result, rounded down, with the preferred exponent set to this
     * value's exponent minus the divisor's exponent.
     * @param divisor The divisor.
     * @return The integer part of the quotient of the two objects. Signals
     * FlagDivideByZero and returns infinity if the divisor is 0 and the
     * dividend is nonzero. Signals FlagInvalid and returns not-a-number
     * (NaN) if the divisor and the dividend are 0.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat DivideToIntegerNaturalScale(ExtendedFloat divisor) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedFloat(this.getEf().DivideToIntegerNaturalScale(divisor.getEf()));
    }

    /**
     * Removes trailing zeros from this object&#x27;s mantissa. For example, 1.00
     * becomes 1. <p>If this object's value is 0, changes the exponent to
     * 0.</p>
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return This value with trailing zeros removed. Note that if the result has
     * a very high exponent and the context says to clamp high exponents,
     * there may still be some trailing zeros in the mantissa.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Reduce(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().Reduce(ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Not documented yet.
     * @param divisor Another arbitrary-precision binary float.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RemainderNaturalScale(ExtendedFloat divisor) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedFloat(this.getEf().RemainderNaturalScale(divisor.getEf()));
    }

    /**
     * Calculates the remainder of a number by the formula this - ((this / divisor)
     * * divisor). This is meant to be similar to the remainder operation in
     * Java's BigDecimal.
     * @param divisor Another arbitrary-precision binary float.
     * @param ctx A precision context object to control the precision, rounding,
     * and exponent range of the integer part of the result. This context
     * will be used only in the division portion of the remainder
     * calculation. Flags will be set on the given context only if the
     * context's HasFlags is true and the integer part of the division
     * result doesn't fit the precision and exponent range without rounding.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RemainderNaturalScale(
ExtendedFloat divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }

        return new ExtendedFloat(
this.getEf().RemainderNaturalScale(
divisor.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision binary floats, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision binary float.
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
     * exponent is outside that range.
     * @throws ArithmeticException The rounding mode is Rounding.Unnecessary and
     * the result is not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat DivideToExponent(
ExtendedFloat divisor,
long desiredExponentSmall,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }

        return new ExtendedFloat(
this.getEf().DivideToExponent(
divisor.getEf(),
desiredExponentSmall,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides this arbitrary-precision binary float by another arbitrary-precision
     * binary float object. The preferred exponent for the result is this
     * object's exponent minus the divisor's exponent.
     * @param divisor The divisor.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0.
     * @throws ArithmeticException Either {@code ctx} is null or {@code ctx} 's
     * precision is 0, and the result would have a nonterminating binary
     * expansion; or, the rounding mode is Rounding.Unnecessary and the
     * result is not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Divide(
ExtendedFloat divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedFloat(
this.getEf().Divide(
divisor.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision binary floats, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision binary float.
     * @param desiredExponentSmall The desired exponent. A negative number places
     * the cutoff point to the right of the usual decimal point. A positive
     * number places the cutoff point to the left of the usual decimal
     * point.
     * @param rounding The rounding mode to use if the result must be scaled down
     * to have the same exponent as this value.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0.
     * @throws ArithmeticException The rounding mode is Rounding.Unnecessary and
     * the result is not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat DivideToExponent(
ExtendedFloat divisor,
long desiredExponentSmall,
Rounding rounding) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedFloat(
this.getEf().DivideToExponent(
divisor.getEf(),
desiredExponentSmall,
ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Divides two arbitrary-precision binary floats, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision binary float.
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
     * exponent is outside that range.
     * @throws ArithmeticException The rounding mode is Rounding.Unnecessary and
     * the result is not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} or {@code
     * exponent} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat DivideToExponent(
ExtendedFloat divisor,
BigInteger exponent,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        if (exponent == null) {
          throw new NullPointerException("exponent");
        }

        return new ExtendedFloat(
   this.getEf().DivideToExponent(
   divisor.getEf(),
   exponent.getEi(),
   ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision binary floats, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision binary float.
     * @param desiredExponent The desired exponent. A negative number places the
     * cutoff point to the right of the usual decimal point. A positive
     * number places the cutoff point to the left of the usual decimal
     * point.
     * @param rounding The rounding mode to use if the result must be scaled down
     * to have the same exponent as this value.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0.
     * @throws ArithmeticException The rounding mode is Rounding.Unnecessary and
     * the result is not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} or {@code
     * desiredExponent} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat DivideToExponent(
ExtendedFloat divisor,
BigInteger desiredExponent,
Rounding rounding) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      if (desiredExponent == null) {
        throw new NullPointerException("desiredExponent");
      }
      return new ExtendedFloat(
this.getEf().DivideToExponent(
divisor.getEf(),
desiredExponent.getEi(),
ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Finds the absolute value of this object (if it&#x27;s negative, it becomes
     * positive).
     * @param context A precision context to control precision, rounding, and
     * exponent range of the result. If HasFlags of the context is true,
     * will also store the flags resulting from the operation (the flags are
     * in addition to the pre-existing flags). Can be null.
     * @return The absolute value of this object.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Abs(PrecisionContext context) {
      try {
        return new ExtendedFloat(this.getEf().Abs(context == null ? null :
            context.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary float with the same value as this object but with the sign
     * reversed.
     * @param context A precision context to control precision, rounding, and
     * exponent range of the result. If HasFlags of the context is true,
     * will also store the flags resulting from the operation (the flags are
     * in addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision binary float. If this value is positive zero,
     * returns positive zero.
     * @throws java.lang.NullPointerException The parameter {@code context} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Negate(PrecisionContext context) {
      try {
        if (context == null) {
          throw new NullPointerException("context");
        }
        return new ExtendedFloat(this.getEf().Negate(context == null ? null :
             context.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Adds this object and another binary float and returns the result.
     * @param otherValue An arbitrary-precision binary float.
     * @return The sum of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Add(ExtendedFloat otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedFloat(this.getEf().Add(otherValue.getEf()));
    }

    /**
     * Subtracts an arbitrary-precision binary float from this instance and returns
     * the result..
     * @param otherValue An arbitrary-precision binary float.
     * @return The difference of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Subtract(ExtendedFloat otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedFloat(this.getEf().Subtract(otherValue.getEf()));
    }

    /**
     * Subtracts an arbitrary-precision binary float from this instance.
     * @param otherValue An arbitrary-precision binary float.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The difference of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Subtract(
ExtendedFloat otherValue,
PrecisionContext ctx) {
      try {
        if (otherValue == null) {
          throw new NullPointerException("otherValue");
        }

        return new ExtendedFloat(
this.getEf().Subtract(
otherValue.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Multiplies two binary floats. The resulting exponent will be the sum of the
     * exponents of the two binary floats.
     * @param otherValue Another binary float.
     * @return The product of the two binary floats.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Multiply(ExtendedFloat otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedFloat(this.getEf().Multiply(otherValue.getEf()));
    }

    /**
     * Multiplies by one binary float, and then adds another binary float.
     * @param multiplicand The value to multiply.
     * @param augend The value to add.
     * @return The result this * multiplicand + augend.
     * @throws java.lang.NullPointerException The parameter {@code multiplicand} or
     * {@code augend} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MultiplyAndAdd(
ExtendedFloat multiplicand,
ExtendedFloat augend) {
      if (multiplicand == null) {
        throw new NullPointerException("multiplicand");
      }
      if (augend == null) {
        throw new NullPointerException("augend");
      }
      return new ExtendedFloat(
this.getEf().MultiplyAndAdd(
multiplicand.getEf(),
augend.getEf()));
    }

    /**
     * Divides this object by another object, and returns the integer part of the
     * result, with the preferred exponent set to this value's exponent
     * minus the divisor's exponent.
     * @param divisor The divisor.
     * @param ctx A precision context object to control the precision, rounding,
     * and exponent range of the integer part of the result. Flags will be
     * set on the given context only if the context's HasFlags is true and
     * the integer part of the result doesn't fit the precision and exponent
     * range without rounding.
     * @return The integer part of the quotient of the two objects. Returns null if
     * the return value would overflow the exponent range. A caller can
     * handle a null return value by treating it as positive infinity if
     * both operands have the same sign or as negative infinity if both
     * operands have different signs. Signals FlagDivideByZero and returns
     * infinity if the divisor is 0 and the dividend is nonzero. Signals
     * FlagInvalid and returns not-a-number (NaN) if the divisor and the
     * dividend are 0.
     * @throws ArithmeticException The rounding mode is Rounding.Unnecessary and
     * the integer part of the result is not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat DivideToIntegerNaturalScale(
ExtendedFloat divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }

        return new ExtendedFloat(
this.getEf().DivideToIntegerNaturalScale(
divisor.getEf(),
ctx == null ? null : ctx.getEc()));
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
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat DivideToIntegerZeroScale(
ExtendedFloat divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }

        return new ExtendedFloat(
this.getEf().DivideToIntegerZeroScale(
divisor.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the remainder that results when dividing two arbitrary-precision
     * binary floats.
     * @param divisor An arbitrary-precision binary float.
     * @param ctx A PrecisionContext object.
     * @return The remainder of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Remainder(
ExtendedFloat divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }

        return new ExtendedFloat(
this.getEf().Remainder(
divisor.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the distance to the closest multiple of the given divisor, based on
     * the result of dividing this object's value by another object's value.
     * <ul> <li>If this and the other object divide evenly, the result is
     * 0.</li> <li>If the remainder's absolute value is less than half of
     * the divisor's absolute value, the result has the same sign as this
     * object and will be the distance to the closest multiple.</li> <li>If
     * the remainder's absolute value is more than half of the divisor' s
     * absolute value, the result has the opposite sign of this object and
     * will be the distance to the closest multiple.</li> <li>If the
     * remainder's absolute value is exactly half of the divisor's absolute
     * value, the result has the opposite sign of this object if the
     * quotient, rounded down, is odd, and has the same sign as this object
     * if the quotient, rounded down, is even, and the result's absolute
     * value is half of the divisor's absolute value.</li></ul> This
     * function is also known as the "IEEE Remainder" function.
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
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RemainderNear(
ExtendedFloat divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }

        return new ExtendedFloat(
this.getEf().RemainderNear(
divisor.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the largest value that's smaller than the given value.
     * @param ctx A precision context object to control the precision and exponent
     * range of the result. The rounding mode from this context is ignored.
     * If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags).
     * @return Returns the largest value that's less than the given value. Returns
     * negative infinity if the result is negative infinity.
     * @throws IllegalArgumentException The parameter {@code ctx} is null, the
     * precision is 0, or {@code ctx} has an unlimited exponent range.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat NextMinus(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().NextMinus(ctx == null ? null :
            ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the smallest value that's greater than the given value.
     * @param ctx A precision context object to control the precision and exponent
     * range of the result. The rounding mode from this context is ignored.
     * If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags).
     * @return Returns the smallest value that's greater than the given value.
     * @throws IllegalArgumentException The parameter {@code ctx} is null, the
     * precision is 0, or {@code ctx} has an unlimited exponent range.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat NextPlus(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().NextPlus(ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the next value that is closer to the other object's value than this
     * object's value.
     * @param otherValue An arbitrary-precision binary float.
     * @param ctx A precision context object to control the precision and exponent
     * range of the result. The rounding mode from this context is ignored.
     * If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags).
     * @return Returns the next value that is closer to the other object' s value
     * than this object's value.
     * @throws IllegalArgumentException The parameter {@code ctx} is null, the
     * precision is 0, or {@code ctx} has an unlimited exponent range.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat NextToward(
ExtendedFloat otherValue,
PrecisionContext ctx) {
      try {
        if (otherValue == null) {
          throw new NullPointerException("otherValue");
        }

        return new ExtendedFloat(
this.getEf().NextToward(
otherValue.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Gets the greater value between two binary floats.
     * @param first An arbitrary-precision binary float.
     * @param second Another arbitrary-precision binary float.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The larger value of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat Max(
ExtendedFloat first,
ExtendedFloat second,
PrecisionContext ctx) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }

      return new ExtendedFloat(
EFloat.Max(
first.getEf(),
second.getEf(),
ctx == null ? null : ctx.getEc()));
    }

    /**
     * Gets the lesser value between two binary floats.
     * @param first An arbitrary-precision binary float.
     * @param second Another arbitrary-precision binary float.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The smaller value of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat Min(
ExtendedFloat first,
ExtendedFloat second,
PrecisionContext ctx) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }

      return new ExtendedFloat(
EFloat.Min(
first.getEf(),
second.getEf(),
ctx == null ? null : ctx.getEc()));
    }

    /**
     * Gets the greater value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Max.
     * @param first Another arbitrary-precision binary float.
     * @param second An arbitrary-precision binary float. (3).
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat MaxMagnitude(
ExtendedFloat first,
ExtendedFloat second,
PrecisionContext ctx) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }

      return new ExtendedFloat(
EFloat.MaxMagnitude(
first.getEf(),
second.getEf(),
ctx == null ? null : ctx.getEc()));
    }

    /**
     * Gets the lesser value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Min.
     * @param first Another arbitrary-precision binary float.
     * @param second An arbitrary-precision binary float. (3).
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat MinMagnitude(
ExtendedFloat first,
ExtendedFloat second,
PrecisionContext ctx) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }

      return new ExtendedFloat(
EFloat.MinMagnitude(
first.getEf(),
second.getEf(),
ctx == null ? null : ctx.getEc()));
    }

    /**
     * Gets the greater value between two binary floats.
     * @param first An arbitrary-precision binary float.
     * @param second Another arbitrary-precision binary float.
     * @return The larger value of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat Max(
ExtendedFloat first,
ExtendedFloat second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedFloat(EFloat.Max(first.getEf(), second.getEf()));
    }

    /**
     * Gets the lesser value between two binary floats.
     * @param first An arbitrary-precision binary float.
     * @param second Another arbitrary-precision binary float.
     * @return The smaller value of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat Min(
ExtendedFloat first,
ExtendedFloat second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedFloat(EFloat.Min(first.getEf(), second.getEf()));
    }

    /**
     * Gets the greater value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Max.
     * @param first Another arbitrary-precision binary float.
     * @param second An arbitrary-precision binary float. (3).
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat MaxMagnitude(
ExtendedFloat first,
ExtendedFloat second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedFloat(EFloat.MaxMagnitude(first.getEf(), second.getEf()));
    }

    /**
     * Gets the lesser value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Min.
     * @param first Another arbitrary-precision binary float.
     * @param second An arbitrary-precision binary float. (3).
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat MinMagnitude(
ExtendedFloat first,
ExtendedFloat second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedFloat(EFloat.MinMagnitude(first.getEf(), second.getEf()));
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
     * @param other An arbitrary-precision binary float.
     * @return Less than 0 if this object's value is less than the other value, or
     * greater than 0 if this object's value is greater than the other value
     * or if {@code other} is null, or 0 if both values are equal.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public int compareTo(ExtendedFloat other) {
      if (other == null) {
        throw new NullPointerException("other");
      }
      return this.getEf().compareTo(other.getEf());
    }

    /**
     * Compares the mathematical values of this object and another object. <p>In
     * this method, negative zero and positive zero are considered
     * equal.</p> <p>If this object or the other object is a quiet NaN or
     * signaling NaN, this method returns a quiet NaN, and will signal a
     * FlagInvalid flag if either is a signaling NaN.</p>
     * @param other An arbitrary-precision binary float.
     * @param ctx A precision context. The precision, rounding, and exponent range
     * are ignored. If HasFlags of the context is true, will store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null.
     * @return Quiet NaN if this object or the other object is NaN, or 0 if both
     * objects have the same value, or -1 if this object is less than the
     * other value, or 1 if this object is greater.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat CompareToWithContext(
ExtendedFloat other,
PrecisionContext ctx) {
      try {
        if (other == null) {
          throw new NullPointerException("other");
        }

        return new ExtendedFloat(
this.getEf().CompareToWithContext(
other.getEf(),
ctx == null ? null : ctx.getEc()));
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
     * @param other An arbitrary-precision binary float.
     * @param ctx A precision context. The precision, rounding, and exponent range
     * are ignored. If HasFlags of the context is true, will store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null.
     * @return Quiet NaN if this object or the other object is NaN, or 0 if both
     * objects have the same value, or -1 if this object is less than the
     * other value, or 1 if this object is greater.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat CompareToSignal(
ExtendedFloat other,
PrecisionContext ctx) {
      try {
        if (other == null) {
          throw new NullPointerException("other");
        }

        return new ExtendedFloat(
this.getEf().CompareToSignal(
other.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the sum of this object and another object. The result's exponent is
     * set to the lower of the exponents of the two operands.
     * @param otherValue The number to add to.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The sum of thisValue and the other object.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Add(
ExtendedFloat otherValue,
PrecisionContext ctx) {
      try {
        if (otherValue == null) {
          throw new NullPointerException("otherValue");
        }

        return new ExtendedFloat(
this.getEf().Add(
otherValue.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary float with the same value but a new exponent.
     * @param desiredExponent An arbitrary-precision integer.
     * @param ctx A PrecisionContext object.
     * @return A binary float with the same value as this object but with the
     * exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
     * if an overflow error occurred, or the rounded result can't fit the
     * given precision, or if the context defines an exponent range and the
     * given exponent is outside that range.
     * @throws java.lang.NullPointerException The parameter {@code desiredExponent}
     * is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Quantize(
BigInteger desiredExponent,
PrecisionContext ctx) {
      try {
        if (desiredExponent == null) {
          throw new NullPointerException("desiredExponent");
        }

        return new ExtendedFloat(
this.getEf().Quantize(
desiredExponent.getEi(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary float with the same value but a new exponent.
     * @param desiredExponentSmall A 32-bit signed integer.
     * @param ctx A PrecisionContext object.
     * @return A binary float with the same value as this object but with the
     * exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
     * if an overflow error occurred, or the rounded result can't fit the
     * given precision, or if the context defines an exponent range and the
     * given exponent is outside that range.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Quantize(
int desiredExponentSmall,
PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
this.getEf().Quantize(
desiredExponentSmall,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary float with the same value as this object but with the same
     * exponent as another binary float.
     * @param otherValue A binary float containing the desired exponent of the
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
     * @return A binary float with the same value as this object but with the
     * exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
     * if an overflow error occurred, or the result can't fit the given
     * precision without rounding. Signals FlagInvalid and returns
     * not-a-number (NaN) if the new exponent is outside of the valid range
     * of the precision context, if it defines an exponent range.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Quantize(
ExtendedFloat otherValue,
PrecisionContext ctx) {
      try {
        if (otherValue == null) {
          throw new NullPointerException("otherValue");
        }
        return new ExtendedFloat(
this.getEf().Quantize(
otherValue.getEf(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary number with the same value as this object but rounded to an
     * integer, and signals an invalid operation if the result would be
     * inexact.
     * @param ctx A precision context to control precision and rounding of the
     * result. If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null, in which case the default rounding
     * mode is HalfEven.
     * @return A binary number rounded to the closest integer representable in the
     * given precision. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding. Signals
     * FlagInvalid and returns not-a-number (NaN) if the precision context
     * defines an exponent range, the new exponent must be changed to 0 when
     * rounding, and 0 is outside of the valid range of the precision
     * context.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RoundToIntegralExact(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().RoundToIntegralExact(ctx == null ?
             null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary number with the same value as this object but rounded to an
     * integer, without adding the FlagInexact or FlagRounded flags.
     * @param ctx A precision context to control precision and rounding of the
     * result. If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags), except that this function will never add the
     * FlagRounded and FlagInexact flags (the only difference between this
     * and RoundToExponentExact). Can be null, in which case the default
     * rounding mode is HalfEven.
     * @return A binary number rounded to the closest integer representable in the
     * given precision, meaning if the result can't fit the precision,
     * additional digits are discarded to make it fit. Signals FlagInvalid
     * and returns not-a-number (NaN) if the precision context defines an
     * exponent range, the new exponent must be changed to 0 when rounding,
     * and 0 is outside of the valid range of the precision context.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RoundToIntegralNoRoundedFlag(PrecisionContext ctx) {
      try {
        return new
        ExtendedFloat(this.getEf().RoundToIntegralNoRoundedFlag(ctx == null ?
            null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary number with the same value as this object but rounded to
     * the given exponent, and signals an invalid operation if the result
     * would be inexact.
     * @param exponent The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places from the number. For example, -3 means round to the sixteenth
     * (10b^-3, 0.0001b), and 3 means round to the sixteen-place (10b^3,
     * 1000b). A value of 0 rounds the number to an integer.
     * @param ctx A PrecisionContext object.
     * @return A binary number rounded to the closest value representable in the
     * given precision. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding. Signals
     * FlagInvalid and returns not-a-number (NaN) if the precision context
     * defines an exponent range, the new exponent must be changed to the
     * given exponent when rounding, and the given exponent is outside of
     * the valid range of the precision context.
     * @throws java.lang.NullPointerException The parameter {@code exponent} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RoundToExponentExact(
BigInteger exponent,
PrecisionContext ctx) {
      try {
        if (exponent == null) {
          throw new NullPointerException("exponent");
        }

        return new ExtendedFloat(
this.getEf().RoundToExponentExact(
exponent.getEi(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary number with the same value as this object but rounded to a
     * new exponent if necessary.
     * @param exponent The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places from the number. For example, -3 means round to the sixteenth
     * (10b^-3, 0.0001b), and 3 means round to the sixteen-place (10b^3,
     * 1000b). A value of 0 rounds the number to an integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null, in which case the
     * default rounding mode is HalfEven.
     * @return A binary number rounded to the closest value representable in the
     * given precision, meaning if the result can't fit the precision,
     * additional digits are discarded to make it fit. Signals FlagInvalid
     * and returns not-a-number (NaN) if the precision context defines an
     * exponent range, the new exponent must be changed to the given
     * exponent when rounding, and the given exponent is outside of the
     * valid range of the precision context.
     * @throws java.lang.NullPointerException The parameter {@code exponent} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RoundToExponent(
BigInteger exponent,
PrecisionContext ctx) {
      try {
        if (exponent == null) {
          throw new NullPointerException("exponent");
        }

        return new ExtendedFloat(
this.getEf().RoundToExponent(
exponent.getEi(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary number with the same value as this object but rounded to
     * the given exponent, and signals an invalid operation if the result
     * would be inexact.
     * @param exponentSmall The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places number. For example, -3 means round to the sixteenth (10b^-3,
     * 0.0001b), and 3 means round to the sixteen-place (10b^3, 1000b). A
     * value of 0 rounds the number to an integer.
     * @param ctx A PrecisionContext object.
     * @return A binary number rounded to the closest value representable in the
     * given precision. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding. Signals
     * FlagInvalid and returns not-a-number (NaN) if the precision context
     * defines an exponent range, the new exponent must be changed to the
     * given exponent when rounding, and the given exponent is outside of
     * the valid range of the precision context.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RoundToExponentExact(
int exponentSmall,
PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
this.getEf().RoundToExponentExact(
exponentSmall,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a binary number with the same value as this object but rounded to a
     * new exponent if necessary.
     * @param exponentSmall The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places number. For example, -3 means round to the sixteenth (10b^-3,
     * 0.0001b), and 3 means round to the sixteen-place (10b^3, 1000b). A
     * value of 0 rounds the number to an integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null, in which case the
     * default rounding mode is HalfEven.
     * @return A binary number rounded to the closest value representable in the
     * given precision, meaning if the result can't fit the precision,
     * additional digits are discarded to make it fit. Signals FlagInvalid
     * and returns not-a-number (NaN) if the precision context defines an
     * exponent range, the new exponent must be changed to the given
     * exponent when rounding, and the given exponent is outside of the
     * valid range of the precision context.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RoundToExponent(
int exponentSmall,
PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
this.getEf().RoundToExponent(
exponentSmall,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Multiplies two binary floats. The resulting scale will be the sum of the
     * scales of the two binary floats. The result's sign is positive if
     * both operands have the same sign, and negative if they have different
     * signs.
     * @param op Another binary float.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The product of the two binary floats.
     * @throws java.lang.NullPointerException The parameter {@code op} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Multiply(
ExtendedFloat op,
PrecisionContext ctx) {
      try {
        if (op == null) {
          throw new NullPointerException("op");
        }

        return new ExtendedFloat(
this.getEf().Multiply(
op.getEf(),
ctx == null ? null : ctx.getEc()));
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
     * @throws java.lang.NullPointerException The parameter {@code op} or {@code
     * augend} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MultiplyAndAdd(
ExtendedFloat op,
ExtendedFloat augend,
PrecisionContext ctx) {
      try {
        if (op == null) {
          throw new NullPointerException("op");
        }
        if (augend == null) {
          throw new NullPointerException("augend");
        }

        return new ExtendedFloat(
this.getEf().MultiplyAndAdd(
op.getEf(),
augend.getEf(),
ctx == null ? null : ctx.getEc()));
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
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MultiplyAndSubtract(
ExtendedFloat op,
ExtendedFloat subtrahend,
PrecisionContext ctx) {
      try {
        if (op == null) {
          throw new NullPointerException("op");
        }
        if (subtrahend == null) {
          throw new NullPointerException("subtrahend");
        }

        return new ExtendedFloat(
   this.getEf().MultiplyAndSubtract(
   op.getEf(),
   subtrahend.getEf(),
   ctx == null ? null : ctx.getEc()));
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
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RoundToPrecision(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().RoundToPrecision(ctx == null ? null :
               ctx.getEc()));
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
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Plus(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().Plus(ctx == null ? null : ctx.getEc()));
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
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat RoundToBinaryPrecision(PrecisionContext ctx) {
      if (ctx == null) {
        return this;
      }
      PrecisionContext ctx2 = ctx.Copy().WithPrecisionInBits(true);
      ExtendedFloat ret = this.RoundToPrecision(ctx2);
      if (ctx2.getHasFlags()) {
        ctx.setFlags(ctx2.getFlags());
      }
      return ret;
    }

    /**
     * Finds the square root of this object's value.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as the square root function's results are generally not exact
     * for many inputs.</i>
     * @return The square root. Signals the flag FlagInvalid and returns NaN if
     * this object is less than 0 (the square root would be a complex
     * number, but the return value is still NaN).
     * @throws IllegalArgumentException The parameter {@code ctx} is null or the
     * precision is unlimited (the context's Precision property is 0).
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat SquareRoot(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().SquareRoot(ctx == null ? null :
             ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds e (the base of natural logarithms) raised to the power of this
     * object's value.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as the exponential function's results are generally not
     * exact.</i>
     * @return Exponential of this object. If this object's value is 1, returns an
     * approximation to " e" within the given precision.
     * @throws IllegalArgumentException The parameter {@code ctx} is null or the
     * precision is unlimited (the context's Precision property is 0).
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Exp(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().Exp(ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the natural logarithm of this object, that is, the power (exponent)
     * that e (the base of natural logarithms) must be raised to in order to
     * equal this object's value.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as the ln function's results are generally not exact.</i>
     * @return Ln(this object). Signals the flag FlagInvalid and returns NaN if
     * this object is less than 0 (the result would be a complex number with
     * a real part equal to Ln of this object's absolute value and an
     * imaginary part equal to pi, but the return value is still NaN.).
     * @throws IllegalArgumentException The parameter {@code ctx} is null or the
     * precision is unlimited (the context's Precision property is 0).
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Log(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().Log(ctx == null ? null : ctx.getEc()));
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
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as the ln function's results are generally not exact.</i>
     * @return Ln(this object)/Ln(10). Signals the flag FlagInvalid and returns
     * not-a-number (NaN) if this object is less than 0. Signals FlagInvalid
     * and returns not-a-number (NaN) if the parameter {@code ctx} is null
     * or the precision is unlimited (the context's Precision property is
     * 0).
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Log10(PrecisionContext ctx) {
      try {
        return new ExtendedFloat(this.getEf().Log10(ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Raises this object's value to the given exponent.
     * @param exponent An arbitrary-precision binary float.
     * @param ctx A PrecisionContext object.
     * @return This^exponent. Signals the flag FlagInvalid and returns NaN if this
     * object and exponent are both 0; or if this value is less than 0 and
     * the exponent either has a fractional part or is infinity.
     * @throws IllegalArgumentException The parameter {@code ctx} is null or the
     * precision is unlimited (the context's Precision property is 0), and
     * the exponent has a fractional part.
     * @throws java.lang.NullPointerException The parameter {@code exponent} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Pow(ExtendedFloat exponent, PrecisionContext ctx) {
      try {
        if (exponent == null) {
          throw new NullPointerException("exponent");
        }
        return new ExtendedFloat(
this.getEf().Pow(
exponent.getEf(),
ctx == null ? null : ctx.getEc()));
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
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Pow(int exponentSmall, PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
this.getEf().Pow(
exponentSmall,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Raises this object&#x27;s value to the given exponent.
     * @param exponentSmall A 32-bit signed integer.
     * @return This^exponent. Returns not-a-number (NaN) if this object and
     * exponent are both 0.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Pow(int exponentSmall) {
      return new ExtendedFloat(this.getEf().Pow(exponentSmall));
    }

    /**
     * Finds the constant &#x3c0;.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as &#x3c0; can never be represented exactly.</i>
     * @return π rounded to the given precision.
     * @throws IllegalArgumentException The parameter {@code ctx} is null or the
     * precision is unlimited (the context's Precision property is 0).
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedFloat PI(PrecisionContext ctx) {
      return new ExtendedFloat(EFloat.PI(ctx == null ? null : ctx.getEc()));
    }

    /**
     * Returns a number similar to this number but with the radix point moved to
     * the left.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision binary float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MovePointLeft(int places) {
      return new ExtendedFloat(this.getEf().MovePointLeft(places));
    }

    /**
     * Returns a number similar to this number but with the radix point moved to
     * the left.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision binary float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MovePointLeft(int places, PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
this.getEf().MovePointLeft(
places,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the radix point moved to
     * the left.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MovePointLeft(BigInteger bigPlaces) {
      if (bigPlaces == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedFloat(this.getEf().MovePointLeft(bigPlaces.getEi()));
    }

    /**
     * Returns a number similar to this number but with the radix point moved to
     * the left.
     * @param bigPlaces An arbitrary-precision integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MovePointLeft(
BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if (bigPlaces == null) {
          throw new NullPointerException("bigPlaces");
        }

        return new ExtendedFloat(
   this.getEf().MovePointLeft(
   bigPlaces.getEi(),
   ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the radix point moved to
     * the right.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision binary float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MovePointRight(int places) {
      return new ExtendedFloat(this.getEf().MovePointRight(places));
    }

    /**
     * Returns a number similar to this number but with the radix point moved to
     * the right.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision binary float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MovePointRight(int places, PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
this.getEf().MovePointRight(
places,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the radix point moved to
     * the right.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MovePointRight(BigInteger bigPlaces) {
      if (bigPlaces == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedFloat(this.getEf().MovePointRight(bigPlaces.getEi()));
    }

    /**
     * Returns a number similar to this number but with the radix point moved to
     * the right.
     * @param bigPlaces An arbitrary-precision integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return A number whose scale is increased by {@code bigPlaces}, but not to
     * more than 0.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat MovePointRight(
BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if (bigPlaces == null) {
          throw new NullPointerException("bigPlaces");
        }

        return new ExtendedFloat(
this.getEf().MovePointRight(
bigPlaces.getEi(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision binary float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat ScaleByPowerOfTwo(int places) {
      return new ExtendedFloat(this.getEf().ScaleByPowerOfTwo(places));
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision binary float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat ScaleByPowerOfTwo(int places, PrecisionContext ctx) {
      try {
        return new ExtendedFloat(
this.getEf().ScaleByPowerOfTwo(
places,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision binary float.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat ScaleByPowerOfTwo(BigInteger bigPlaces) {
      if (bigPlaces == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedFloat(this.getEf().ScaleByPowerOfTwo(bigPlaces.getEi()));
    }

    /**
     * Returns a number similar to this number but with its scale adjusted.
     * @param bigPlaces An arbitrary-precision integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return A number whose scale is increased by {@code bigPlaces}.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat ScaleByPowerOfTwo(
BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if (bigPlaces == null) {
          throw new NullPointerException("bigPlaces");
        }

        return new ExtendedFloat(
this.getEf().ScaleByPowerOfTwo(
bigPlaces.getEi(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the number of digits in this number's mantissa. Returns 1 if this
     * value is 0, and 0 if this value is infinity or NaN.
     * @return An arbitrary-precision integer.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public BigInteger Precision() {
      return new BigInteger(this.getEf().Precision());
    }

    /**
     * Returns the unit in the last place. The mantissa will be 1 and the exponent
     * will be this number's exponent. Returns 1 with an exponent of 0 if
     * this number is infinity or NaN.
     * @return An arbitrary-precision binary float.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat Ulp() {
      return new ExtendedFloat(this.getEf().Ulp());
    }

    /**
     * Calculates the quotient and remainder using the DivideToIntegerNaturalScale
     * and the formula in RemainderNaturalScale.
     * @param divisor The number to divide by.
     * @return A 2 element array consisting of the quotient and remainder in that
     * order.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat[] DivideAndRemainderNaturalScale(ExtendedFloat
        divisor) {
      EFloat[] edec = this.getEf().DivideAndRemainderNaturalScale(divisor == null ?
        null : divisor.getEf());
      return new ExtendedFloat[] {
        new ExtendedFloat(edec[0]), new ExtendedFloat(edec[1])
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
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat[] DivideAndRemainderNaturalScale(
     ExtendedFloat divisor,
     PrecisionContext ctx) {
      try {
        EFloat[] edec = this.getEf().DivideAndRemainderNaturalScale(
divisor == null ? null : divisor.getEf(),
ctx == null ? null : ctx.getEc());
        return new ExtendedFloat[] {
        new ExtendedFloat(edec[0]), new ExtendedFloat(edec[1])
      };
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }
  }
