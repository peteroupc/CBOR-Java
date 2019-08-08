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
     * <code>PeterO.Numbers.EFloat</code> in the <a
     * href='https://www.nuget.org/packages/PeterO.Numbers'>
     * <code>PeterO.Numbers</code> </a> library (in .NET), or
     * <code>com.upokecenter.numbers.EFloat</code> in the <a
     * href='https://github.com/peteroupc/numbers-java'>
     * <code>com.github.peteroupc/numbers</code> </a> artifact (in Java). This
     * new class can be used in the <code>CBORObject.FromObject(object)</code>
     * method (by including the new library in your code, among other
     * things).</b> </p> <p>Represents an arbitrary-precision binary
     * floating-point number. Consists of an integer mantissa and an
     * integer exponent, both arbitrary-precision. The value of the number
     * equals mantissa * 2^exponent. This class also supports values for
     * negative zero, not-a-number (NaN) values, and infinity. </p>
     * <p>Passing a signaling NaN to any arithmetic operation shown here
     * will signal the flag FlagInvalid and return a quiet NaN, even if
     * another operand to that operation is a quiet NaN, unless noted
     * otherwise. </p> <p>Passing a quiet NaN to any arithmetic operation
     * shown here will return a quiet NaN, unless noted otherwise. </p>
     * <p>Unless noted otherwise, passing a null arbitrary-precision binary
     * float argument to any method here will throw an exception. </p>
     * <p>When an arithmetic operation signals the flag FlagInvalid,
     * FlagOverflow, or FlagDivideByZero, it will not throw an exception
     * too, unless the operation's trap is enabled in the precision context
     * (see PrecisionContext's Traps property). </p> <p>An
     * arbitrary-precision binary float value can be serialized in one of
     * the following ways: </p> <ul> <li>By calling the toString() method.
     * However, not all strings can be converted back to an
     * arbitrary-precision binary float without loss, especially if the
     * string has a fractional part. </li> <li>By calling the
     * UnsignedMantissa, Exponent, and IsNegative properties, and calling
     * the IsInfinity, IsQuietNaN, and IsSignalingNaN methods. The return
     * values combined will uniquely identify a particular
     * arbitrary-precision binary float value. </li> </ul> <p>If an
     * operation requires creating an intermediate value that might be too
     * big to fit in memory (or might require more than 2 gigabytes of
     * memory to store -- due to the current use of a 32-bit integer
     * internally as a length), the operation may signal an
     * invalid-operation flag and return not-a-number (NaN). In certain
     * rare cases, the compareTo method may throw OutOfMemoryError
     * (called OutOfMemoryError in Java) in the same circumstances. </p>
     * <p><b>Thread safety:</b> Instances of this class are immutable, so
     * they are inherently safe for use by multiple threads. Multiple
     * instances of this object with the same properties are
     *  interchangeable, so they should not be compared using the "=="
     * operator (which might only check if each side of the operator is the
     * same instance). </p>
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers and the output
 * of\u0020this class's toString method.
 */
@Deprecated
  public final class ExtendedFloat implements Comparable<ExtendedFloat> {
    private final EFloat ef;

    ExtendedFloat(EFloat ef) {
      this.ef = ef;
    }

    /**
     * Gets this object's exponent. This object's value will be an integer if the
     * exponent is positive or zero.
     * @return This object's exponent. This object's value will be an integer if
     * the exponent is positive or zero.
     */
    public final BigInteger getExponent() {
        return new BigInteger(this.getEf().getExponent());
      }

    /**
     * Gets the absolute value of this object's un-scaled value.
     * @return The absolute value of this object's un-scaled value.
     */
    public final BigInteger getUnsignedMantissa() {
        return new BigInteger(this.getEf().getUnsignedMantissa());
      }

    /**
     * Gets this object's un-scaled value.
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
     * @return {@code true} if this object's mantissa and exponent are equal to
     * those of another object; otherwise, {@code false}.
     * @throws NullPointerException The parameter {@code otherValue} is
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
     * @return {@code true} if this object's mantissa and exponent are equal to
     * those of another object; otherwise, {@code false}.
     * @throws NullPointerException The parameter {@code other} is null.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean equals(ExtendedFloat other) {
      return other != null && this.getEf().equals(other.getEf());
    }

    /**
     * Determines whether this object's mantissa and exponent are equal to those of
     * another object and that other object is an arbitrary-precision
     * decimal number.
     * @param obj The parameter
      {@code obj}
       is an arbitrary object.
     * @return {@code true} if the objects are equal; otherwise, {@code false}.
     */
    @Override public boolean equals(Object obj) {
      ExtendedFloat bi = ((obj instanceof ExtendedFloat) ? (ExtendedFloat)obj : null);
      return (bi == null) ? false : this.getEf().equals(bi.getEf());
    }

    /**
     * Calculates this object's hash code. No application or process IDs are used
     * in the hash code calculation.
     * @return This object's hash code.
     */
    @Override public int hashCode() {
      return this.getEf().hashCode();
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
     * @throws NullPointerException The parameter {@code mantissa} or
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
     * Creates a binary float from a text string that represents a number. Note
     * that if the string contains a negative exponent, the resulting value
     * might not be exact, in which case the resulting binary float will be
     * an approximation of this decimal number's value. (NOTE: This
     * documentation previously said the binary float will contain enough
     * precision to accurately convert it to a 32-bit or 64-bit floating
     * point number. Due to double rounding, this will generally not be the
     * case for certain numbers converted from decimal to ExtendedFloat via
     * this method and in turn converted to <code>double</code> or <code>float</code>.)
     * <p>The format of the string generally consists of:</p> <ul> <li>An
     *  optional plus sign ("+" , U+002B) or minus sign ("-", U+002D) (if
     * '-' , the value is negative.)</li> <li>One or more digits, with a
     * single optional decimal point after the first digit and before the
     *  last digit.</li> <li>Optionally, "E+"/"e+" (positive exponent) or
     *  "E-"/"e-" (negative exponent) plus one or more digits specifying the
     *  exponent.</li></ul> <p>The string can also be "-INF", "-Infinity",
     *  "Infinity", "INF", quiet NaN ("NaN") followed by any number of
     *  digits, or signaling NaN ("sNaN") followed by any number of digits,
     * all in any combination of upper and lower case.</p> <p>All
     * characters mentioned above are the corresponding characters in the
     * Basic Latin range. In particular, the digits must be the basic
     * digits 0 to 9 (U + 0030 to U + 0039). The string is not allowed to
     * contain white space characters, including spaces.</p>
     * @param str The parameter {@code str} is a text string.
     * @param offset A zero-based index showing where the desired portion of {@code
     * str} begins.
     * @param length The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @param ctx A PrecisionContext object specifying the precision, rounding, and
     * exponent range to apply to the parsed number. Can be null.
     * @return The parsed number, converted to arbitrary-precision binary float.
     * @throws NullPointerException The parameter {@code str} is null.
     * @throws IllegalArgumentException Either {@code offset} or {@code length} is
     * less than 0 or greater than {@code str} 's length, or {@code str} '
     * s length minus {@code offset} is less than {@code length}.
     * @throws IllegalArgumentException Either {@code offset} or {@code length} is
     * less than 0 or greater than {@code str} 's length, or {@code str} 's
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
     * Creates a binary float from a text string that represents a number.
     * @param str A text string containing the number to convert.
     * @return The parsed number, converted to arbitrary-precision binary float.
     */
    public static ExtendedFloat FromString(String str) {
      return new ExtendedFloat(EFloat.FromString(str));
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
     * A not-a-number value that signals an invalid operation flag when it's passed
     * as an argument to any arithmetic operation in arbitrary-precision
     * binary float.
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
     * @return {@code true} if this object is negative infinity; otherwise, {@code
     * false}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsNegativeInfinity() {
      return this.getEf().IsNegativeInfinity();
    }

    /**
     * Returns whether this object is positive infinity.
     * @return {@code true} if this object is positive infinity; otherwise, {@code
     * false}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsPositiveInfinity() {
      return this.getEf().IsPositiveInfinity();
    }

    /**
     * Returns whether this object is a not-a-number value.
     * @return {@code true} if this object is a not-a-number value; otherwise,
     * {@code false}.
     */
    public boolean IsNaN() {
      return this.getEf().IsNaN();
    }

    /**
     * Gets a value indicating whether this object is positive or negative
     * infinity.
     * @return {@code true} if this object is positive or negative infinity;
     * otherwise, {@code false}.
     */
    public boolean IsInfinity() {
      return this.getEf().IsInfinity();
    }

    /**
     * Gets a value indicating whether this object is negative, including negative
     * zero.
     * @return {@code true} If this object is negative, including negative zero;
     * otherwise, . {@code false}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isNegative() {
        return this.getEf().isNegative();
      }

    /**
     * Gets a value indicating whether this object is a quiet not-a-number value.
     * @return {@code true} if this object is a quiet not-a-number value;
     * otherwise, {@code false}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsQuietNaN() {
      return this.getEf().IsQuietNaN();
    }

    /**
     * Gets a value indicating whether this object is a signaling not-a-number
     * value.
     * @return {@code true} if this object is a signaling not-a-number value;
     * otherwise, {@code false}.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsSignalingNaN() {
      return this.getEf().IsSignalingNaN();
    }

    /**
     * Compares this extended float to another.
     * @param other An extended float to compare this one with.
     * @return Less than 0 if this value is less than, 0 if equal to, or greater
     * than 0 if greater than the other value.
     */
    public int compareTo(ExtendedFloat other) {
      return this.getEf().compareTo(other == null ? null : other.getEf());
    }

    /**
     * Gets this value's sign: -1 if negative; 1 if positive; 0 if zero.
     * @return This value's sign: -1 if negative; 1 if positive; 0 if zero.
     * @deprecated Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final int signum() {
        return this.getEf().signum();
      }

    final EFloat getEf() {
        return this.ef;
      }
  }
