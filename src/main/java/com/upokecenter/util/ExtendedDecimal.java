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
     * <code>PeterO.Numbers.EDecimal</code> in the <a
  * href='https://www.nuget.org/packages/PeterO.Numbers'><code>PeterO.Numbers</code></a>
     * library (in .NET), or <code>com.upokecenter.numbers.EDecimal</code> in the
     * <a
  * href='https://github.com/peteroupc/numbers-java'><code>com.github.peteroupc/numbers</code></a>
     * artifact (in Java). This new class can be used in the
     * <code>CBORObject.FromObject(Object)</code> method (by including the new
     * library in your code, among other things), but this version of the
     * CBOR library doesn't include any methods that explicitly take an
     * <code>EDecimal</code> as a parameter or return value.</b></p> Represents an
     * arbitrary-precision decimal floating-point number. <p><b>About
     * decimal arithmetic</b></p> <p> Decimal (base-10) arithmetic, such as
     * that provided by this class, is appropriate for calculations
     * involving such real-world data as prices and other sums of money, tax
     * rates, and measurements. These calculations often involve multiplying
     * or dividing one decimal with another decimal, or performing other
     * operations on decimal numbers. Many of these calculations also rely
     * on rounding behavior in which the result after rounding is a decimal
     * number (for example, multiplying a price by a premium rate, then
     * rounding, should result in a decimal amount of money). </p> <p>On the
     * other hand, most implementations of <code>float</code> and <code>double</code>,
     * including in C# and Java, store numbers in a binary (base-2)
     * floating-point format and use binary floating-point arithmetic. Many
     * decimal numbers can't be represented exactly in binary floating-point
     * format (regardless of its length). Applying binary arithmetic to
     * numbers intended to be decimals can sometimes lead to unintuitive
     * results, as is shown in the description for the FromDouble() method
     * of this class.</p> <p><b>About ExtendedDecimal instances</b></p> <p>
     * Each instance of this class consists of an integer mantissa and an
     * integer exponent, both arbitrary-precision. The value of the number
     * equals mantissa * 10^exponent.</p> <p>The mantissa is the value of
     * the digits that make up a number, ignoring the decimal point and
     * exponent. For example, in the number 2356.78, the mantissa is 235678.
     * The exponent is where the "floating" decimal point of the number is
     * located. A positive exponent means "move it to the right", and a
     * negative exponent means "move it to the left." In the example 2,
     * 356.78, the exponent is -2, since it has 2 decimal places and the
     * decimal point is "moved to the left by 2." Therefore, in the
     * arbitrary-precision decimal representation, this number would be
     * stored as 235678 * 10^-2.</p> <p>The mantissa and exponent format
     * preserves trailing zeros in the number's value. This may give rise to
     * multiple ways to store the same value. For example, 1.00 and 1 would
     * be stored differently, even though they have the same value. In the
     * first case, 100 * 10^-2 (100 with decimal point moved left by 2), and
     * in the second case, 1 * 10^0 (1 with decimal point moved 0).</p>
     * <p>This class also supports values for negative zero, not-a-number
     * (NaN) values, and infinity. <b>Negative zero</b> is generally used
     * when a negative number is rounded to 0; it has the same mathematical
     * value as positive zero. <b>Infinity</b> is generally used when a
     * non-zero number is divided by zero, or when a very high number can't
     * be represented in a given exponent range. <b>Not-a-number</b> is
     * generally used to signal errors.</p> <p>This class implements the
     * General Decimal Arithmetic Specification version 1.70 (except part of
     * chapter 6): <code>http://speleotrove.com/decimal/decarith.html</code></p>
     * <p><b>Errors and Exceptions</b></p> <p>Passing a signaling NaN to any
     * arithmetic operation shown here will signal the flag FlagInvalid and
     * return a quiet NaN, even if another operand to that operation is a
     * quiet NaN, unless noted otherwise.</p> <p>Passing a quiet NaN to any
     * arithmetic operation shown here will return a quiet NaN, unless noted
     * otherwise. Invalid operations will also return a quiet NaN, as stated
     * in the individual methods.</p> <p>Unless noted otherwise, passing a
     * null arbitrary-precision decimal argument to any method here will
     * throw an exception.</p> <p>When an arithmetic operation signals the
     * flag FlagInvalid, FlagOverflow, or FlagDivideByZero, it will not
     * throw an exception too, unless the flag's trap is enabled in the
     * precision context (see EContext's Traps property).</p> <p>If an
     * operation requires creating an intermediate value that might be too
     * big to fit in memory (or might require more than 2 gigabytes of
     * memory to store -- due to the current use of a 32-bit integer
     * internally as a length), the operation may signal an
     * invalid-operation flag and return not-a-number (NaN). In certain rare
     * cases, the compareTo method may throw OutOfMemoryError (called
     * OutOfMemoryError in Java) in the same circumstances.</p>
     * <p><b>Serialization</b></p> <p>An arbitrary-precision decimal value
     * can be serialized (converted to a stable format) in one of the
     * following ways:</p> <ul> <li>By calling the toString() method, which
     * will always return distinct strings for distinct arbitrary-precision
     * decimal values.</li> <li>By calling the UnsignedMantissa, Exponent,
     * and IsNegative properties, and calling the IsInfinity, IsQuietNaN,
     * and IsSignalingNaN methods. The return values combined will uniquely
     * identify a particular arbitrary-precision decimal value.</li></ul>
     * <p><b>Thread safety</b></p> <p>Instances of this class are immutable,
     * so they are inherently safe for use by multiple threads. Multiple
     * instances of this object with the same properties are
     * interchangeable, so they should not be compared using the "=="
     * operator (which only checks if each side of the operator is the same
     * instance).</p> <p><b>Comparison considerations</b></p> <p>This
     * class's natural ordering (under the compareTo method) is not
     * consistent with the Equals method. This means that two values that
     * compare as equal under the compareTo method might not be equal under
     * the Equals method. The compareTo method compares the mathematical
     * values of the two instances passed to it (and considers two different
     * NaN values as equal), while two instances with the same mathematical
     * value, but different exponents, will be considered unequal under the
     * Equals method.</p>
     */
  public final class ExtendedDecimal implements Comparable<ExtendedDecimal> {
    /**
     * Gets this object&#x27;s exponent. This object&#x27;s value will be an
     * integer if the exponent is positive or zero.
     * @return This object's exponent. This object's value will be an integer if
     * the exponent is positive or zero.
     */
    public final BigInteger getExponent() {
        return new BigInteger(this.getEd().getExponent());
      }

    /**
     * Gets the absolute value of this object&#x27;s un-scaled value.
     * @return The absolute value of this object's un-scaled value.
     */
    public final BigInteger getUnsignedMantissa() {
        return new BigInteger(this.getEd().getUnsignedMantissa());
      }

    /**
     * Gets this object&#x27;s un-scaled value.
     * @return This object's un-scaled value. Will be negative if this object's
     * value is negative (including a negative NaN).
     */
    public final BigInteger getMantissa() {
        return new BigInteger(this.getEd().getMantissa());
      }

    static ExtendedDecimal ToLegacy(EDecimal ei) {
      return new ExtendedDecimal(ei);
    }

    static EDecimal FromLegacy(ExtendedDecimal bei) {
      return bei.getEd();
    }

    /**
     * Determines whether this object&#x27;s mantissa and exponent are equal to
     * those of another object.
     * @param other An arbitrary-precision decimal number.
     * @return true if this object's mantissa and exponent are equal to those of
     * another object; otherwise, false.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean equals(ExtendedDecimal other) {
      return (other == null) ? false : this.getEd().equals(other.getEd());
    }

    /**
     * Determines whether this object&#x27;s mantissa and exponent are equal to
     * those of another object and that other object is an
     * arbitrary-precision decimal number.
     * @param obj An arbitrary object.
     * @return true if the objects are equal; otherwise, false.
     */
    @Override public boolean equals(Object obj) {
      ExtendedDecimal bi = ((obj instanceof ExtendedDecimal) ? (ExtendedDecimal)obj : null);
      return (bi == null) ? false : this.getEd().equals(bi.getEd());
    }

    /**
     * Calculates this object&#x27;s hash code.
     * @return This object's hash code.
     */
    @Override public int hashCode() {
      return this.getEd().hashCode();
    }

    /**
     * Creates a number with the value exponent*10^mantissa.
     * @param mantissaSmall The un-scaled value.
     * @param exponentSmall The decimal exponent.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal Create(int mantissaSmall, int exponentSmall) {
      return new ExtendedDecimal(EDecimal.Create(mantissaSmall, exponentSmall));
    }

    private final EDecimal ed;

    ExtendedDecimal(EDecimal ed) {
      if (ed == null) {
        throw new NullPointerException("ed");
      }
      this.ed = ed;
    }

    /**
     * Creates a number with the value exponent*10^mantissa.
     * @param mantissa The un-scaled value.
     * @param exponent The decimal exponent.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code mantissa} or
     * {@code exponent} is null.
     */
    public static ExtendedDecimal Create(
      BigInteger mantissa,
      BigInteger exponent) {
      if (mantissa == null) {
        throw new NullPointerException("mantissa");
      }
      if (exponent == null) {
        throw new NullPointerException("exponent");
      }
      return new ExtendedDecimal(EDecimal.Create(mantissa.getEi(), exponent.getEi()));
    }

    /**
     * Creates a not-a-number arbitrary-precision decimal number.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @return A quiet not-a-number.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null or
     * is less than 0.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal CreateNaN(BigInteger diag) {
      if (diag == null) {
        throw new NullPointerException("diag");
      }
      return new ExtendedDecimal(EDecimal.CreateNaN(diag.getEi()));
    }

    /**
     * Creates a not-a-number arbitrary-precision decimal number.
     * @param diag A number to use as diagnostic information associated with this
     * object. If none is needed, should be zero.
     * @param signaling Whether the return value will be signaling (true) or quiet
     * (false).
     * @param negative Whether the return value is negative.
     * @param ctx A context object for arbitrary-precision arithmetic settings.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code diag} is null or
     * is less than 0.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal CreateNaN(
BigInteger diag,
boolean signaling,
boolean negative,
PrecisionContext ctx) {
      if (diag == null) {
        throw new NullPointerException("diag");
      }
      return new ExtendedDecimal(
  EDecimal.CreateNaN(
  diag.getEi(),
  signaling,
  negative,
  ctx == null ? null : ctx.getEc()));
    }

    /**
     * Creates a decimal number from a text string that represents a number. See
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
     * Creates a decimal number from a text string that represents a number. See
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromString(String str, PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
  EDecimal.FromString(
  str,
  ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Creates a decimal number from a text string that represents a number. See
     * <code>FromString(String, int, int, EContext)</code> for more information.
     * @param str A string that represents a number.
     * @param offset A zero-based index showing where the desired portion of {@code
     * str} begins.
     * @param length The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @return An arbitrary-precision decimal number with the same value as the
     * given string.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws java.lang.NumberFormatException The parameter {@code str} is not a correctly
     * formatted number string.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromString(
String str,
int offset,
int length) {
      return new ExtendedDecimal(EDecimal.FromString(str, offset, length));
    }

    /**
     * <p>Creates a decimal number from a text string that represents a number.</p>
     * <p>The format of the string generally consists of:</p> <ul> <li>An
     * optional plus sign ("+" , U+002B) or minus sign ("-", U+002D) (if '-'
     * , the value is negative.)</li> <li>One or more digits, with a single
     * optional decimal point after the first digit and before the last
     * digit.</li> <li>Optionally, "E+"/"e+" (positive exponent) or
     * "E-"/"e-" (negative exponent) plus one or more digits specifying the
     * exponent.</li></ul> <p>The string can also be "-INF", "-Infinity",
     * "Infinity", "INF" , quiet NaN ("NaN" /"-NaN") followed by any number
     * of digits, or signaling NaN ("sNaN" /"-sNaN") followed by any number
     * of digits, all in any combination of upper and lower case.</p> <p>All
     * characters mentioned above are the corresponding characters in the
     * Basic Latin range. In particular, the digits must be the basic digits
     * 0 to 9 (U + 0030 to U + 0039). The string portion is not allowed to
     * contain white space characters, including spaces.</p>
     * @param str A text string, a portion of which represents a number.
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromString(
String str,
int offset,
int length,
PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
  EDecimal.FromString(
  str,
  offset,
  length,
  ctx == null ? null : ctx.getEc()));
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public int CompareToBinary(ExtendedFloat other) {
      return (other == null) ? 1 : this.getEd().CompareToBinary(other.getEf());
    }

    /**
     * Converts this value to an arbitrary-precision integer. Any fractional part
     * in this value will be discarded when converting to a big integer.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public BigInteger ToBigInteger() {
      return new BigInteger(this.getEd().ToEInteger());
    }

/**
 * @deprecated Implements legacy behavior using an obsolete class for convenience.
 */
@Deprecated
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
      return (r == Rounding.Unnecessary) ? ERounding.None : ((r ==
        Rounding.Odd) ? ERounding.Odd : ERounding.Down);
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
      return (r == ERounding.None) ? Rounding.Unnecessary : ((r ==
        ERounding.Odd) ? Rounding.Odd : Rounding.Down);
    }

    /**
     * Converts this value to an arbitrary-precision integer, checking whether the
     * fractional part of the integer would be lost.
     * @return An arbitrary-precision integer.
     * @throws java.lang.ArithmeticException This object's value is infinity or NaN.
     * @throws ArithmeticException This object's value is not an exact integer.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public BigInteger ToBigIntegerExact() {
      return new BigInteger(this.getEd().ToEIntegerExact());
    }

    /**
     * Creates a binary floating-point number from this object&#x27;s value. Note
     * that if the binary floating-point number contains a negative
     * exponent, the resulting value might not be exact, in which case the
     * resulting binary float will be an approximation of this decimal
     * number's value. (NOTE: This documentation previously said the binary
     * float will contain enough precision to accurately convert it to a
     * 32-bit or 64-bit floating point number. Due to double rounding, this
     * will generally not be the case for certain numbers converted from
     * decimal to ExtendedFloat via this method and in turn converted to
     * <code>double</code> or <code>float</code>.)
     * @return An arbitrary-precision binary float.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedFloat ToExtendedFloat() {
      return new ExtendedFloat(this.getEd().ToExtendedFloat());
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
      return this.getEd().ToSingle();
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
      return this.getEd().ToDouble();
    }

    /**
     * Creates a decimal number from a 32-bit binary floating-point number. This
     * method computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the floating point
     * number to a string first. Remember, though, that the exact value of a
     * 32-bit binary floating-point number is not always the value that
     * results when passing a literal decimal number (for example, calling
     * <code>ExtendedDecimal.FromSingle(0.1f)</code>), since not all decimal
     * numbers can be converted to exact binary numbers (in the example
     * given, the resulting arbitrary-precision decimal will be the the
     * value of the closest "float" to 0.1, not 0.1 exactly). To create an
     * arbitrary-precision decimal number from a decimal number, use
     * FromString instead in most cases (for example:
     * <code>ExtendedDecimal.FromString("0.1")</code>).
     * @param flt A 32-bit floating-point number.
     * @return A decimal number with the same value as {@code flt}.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromSingle(float flt) {
      return new ExtendedDecimal(EDecimal.FromSingle(flt));
    }

    /**
     * Converts a big integer to an arbitrary precision decimal.
     * @param bigint An arbitrary-precision integer.
     * @return An arbitrary-precision decimal number with the exponent set to 0.
     * @throws java.lang.NullPointerException The parameter {@code bigint} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromBigInteger(BigInteger bigint) {
      if (bigint == null) {
        throw new NullPointerException("bigint");
      }
      return new ExtendedDecimal(EDecimal.FromEInteger(bigint.getEi()));
    }

    /**
     * Creates a decimal number from a 64-bit signed integer.
     * @param valueSmall A 64-bit signed integer.
     * @return An arbitrary-precision decimal number with the exponent set to 0.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromInt64(long valueSmall) {
      return new ExtendedDecimal(EDecimal.FromInt64(valueSmall));
    }

    /**
     * Creates a decimal number from a 32-bit signed integer.
     * @param valueSmaller A 32-bit signed integer.
     * @return An arbitrary-precision decimal number with the exponent set to 0.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromInt32(int valueSmaller) {
      return new ExtendedDecimal(EDecimal.FromInt32(valueSmaller));
    }

    /**
     * Creates a decimal number from a 64-bit binary floating-point number. This
     * method computes the exact value of the floating point number, not an
     * approximation, as is often the case by converting the floating point
     * number to a string first. Remember, though, that the exact value of a
     * 64-bit binary floating-point number is not always the value that
     * results when passing a literal decimal number (for example, calling
     * <code>ExtendedDecimal.FromDouble(0.1f)</code>), since not all decimal
     * numbers can be converted to exact binary numbers (in the example
     * given, the resulting arbitrary-precision decimal will be the value of
     * the closest "double" to 0.1, not 0.1 exactly). To create an
     * arbitrary-precision decimal number from a decimal number, use
     * FromString instead in most cases (for example:
     * <code>ExtendedDecimal.FromString("0.1")</code>).
     * @param dbl A 64-bit floating-point number.
     * @return A decimal number with the same value as {@code dbl}.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromDouble(double dbl) {
      return new ExtendedDecimal(EDecimal.FromDouble(dbl));
    }

    /**
     * Creates a decimal number from an arbitrary-precision binary floating-point
     * number.
     * @param bigfloat An arbitrary-precision binary floating-point number.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code bigfloat} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal FromExtendedFloat(ExtendedFloat bigfloat) {
      if (bigfloat == null) {
        throw new NullPointerException("bigfloat");
      }
      return new ExtendedDecimal(EDecimal.FromExtendedFloat(bigfloat.getEf()));
    }

    /**
     * Converts this value to a string. Returns a value compatible with this
     * class's FromString method.
     * @return A string representation of this object.
     */
    @Override public String toString() {
      return this.getEd().toString();
    }

    /**
     * Same as toString(), except that when an exponent is used it will be a
     * multiple of 3.
     * @return A text string.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public String ToEngineeringString() {
      return this.getEd().ToEngineeringString();
    }

    /**
     * Converts this value to a string, but without using exponential notation.
     * @return A text string.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public String ToPlainString() {
      return this.getEd().ToPlainString();
    }

    /**
     * Represents the number 1.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final ExtendedDecimal One =
      ExtendedDecimal.Create(BigInteger.valueOf(1), BigInteger.valueOf(0));

    /**
     * Represents the number 0.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
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
      new ExtendedDecimal(EDecimal.Ten);

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
     * @return true if this object is negative infinity; otherwise, false.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsNegativeInfinity() {
      return this.getEd().IsNegativeInfinity();
    }

    /**
     * Returns whether this object is positive infinity.
     * @return true if this object is positive infinity; otherwise, false.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsPositiveInfinity() {
      return this.getEd().IsPositiveInfinity();
    }

    /**
     * Gets a value indicating whether this object is not a number (NaN).
     * @return true if this object is not a number (NaN); otherwise, false.
     */
    public boolean IsNaN() {
      return this.getEd().IsNaN();
    }

    /**
     * Gets a value indicating whether this object is positive or negative
     * infinity.
     * @return true if this object is positive or negative infinity; otherwise,
     * false.
     */
    public boolean IsInfinity() {
      return this.getEd().IsInfinity();
    }

    /**
     * Gets a value indicating whether this object is finite (not infinity or NaN).
     * @return true if this object is finite (not infinity or NaN); otherwise,
     * false.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isFinite() {
        return this.getEd().isFinite();
      }

    /**
     * Gets a value indicating whether this object is negative, including negative
     * zero.
     * @return true if this object is negative, including negative zero; otherwise,
     * false.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isNegative() {
        return this.getEd().isNegative();
      }

    /**
     * Gets a value indicating whether this object is a quiet not-a-number value.
     * @return true if this object is a quiet not-a-number value; otherwise, false.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsQuietNaN() {
      return this.getEd().IsQuietNaN();
    }

    /**
     * Gets a value indicating whether this object is a signaling not-a-number
     * value.
     * @return true if this object is a signaling not-a-number value; otherwise,
     * false.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public boolean IsSignalingNaN() {
      return this.getEd().IsSignalingNaN();
    }

    /**
     * Gets this value&#x27;s sign: -1 if negative; 1 if positive; 0 if zero.
     * @return This value's sign: -1 if negative; 1 if positive; 0 if zero.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final int signum() {
        return this.getEd().signum();
      }

    /**
     * Gets a value indicating whether this object&#x27;s value equals 0.
     * @return true if this object's value equals 0; otherwise, false.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isZero() {
        return this.getEd().isZero();
      }

    final EDecimal getEd() {
        return this.ed;
      }

    /**
     * Gets the absolute value of this object.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Abs() {
      return new ExtendedDecimal(this.getEd().Abs(null));
    }

    /**
     * Gets an object with the same value as this one, but with the sign reversed.
     * @return An arbitrary-precision decimal number. If this value is positive
     * zero, returns positive zero.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Negate() {
      return new ExtendedDecimal(this.getEd().Negate(null));
    }

    /**
     * Divides this object by another decimal number and returns the result. When
     * possible, the result will be exact.
     * @param divisor The number to divide by.
     * @return The quotient of the two numbers. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Returns not-a-number (NaN) if the divisor and the dividend are 0.
     * Returns NaN if the result can't be exact because it would have a
     * nonterminating decimal expansion.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Divide(ExtendedDecimal divisor) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedDecimal(this.getEd().Divide(divisor.getEd()));
    }

    /**
     * Divides this object by another decimal number and returns a result with the
     * same exponent as this object (the dividend).
     * @param divisor The number to divide by.
     * @param rounding The rounding mode to use if the result must be scaled down
     * to have the same exponent as this value.
     * @return The quotient of the two numbers. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
     * the dividend are 0. Signals FlagInvalid and returns not-a-number
     * (NaN) if the rounding mode is Rounding.Unnecessary and the result is
     * not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal DivideToSameExponent(
ExtendedDecimal divisor,
Rounding rounding) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedDecimal(
this.getEd().DivideToSameExponent(
divisor.getEd(),
ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Divides two arbitrary-precision decimal numbers, and returns the integer
     * part of the result, rounded down, with the preferred exponent set to
     * this value's exponent minus the divisor's exponent.
     * @param divisor The number to divide by.
     * @return The integer part of the quotient of the two objects. Signals
     * FlagDivideByZero and returns infinity if the divisor is 0 and the
     * dividend is nonzero. Signals FlagInvalid and returns not-a-number
     * (NaN) if the divisor and the dividend are 0.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal DivideToIntegerNaturalScale(ExtendedDecimal
                    divisor) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
   return new ExtendedDecimal(this.getEd().DivideToIntegerNaturalScale(divisor.getEd()));
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Reduce(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().Reduce(ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Calculates the remainder of a number by the formula "this" - (("this" /
     * "divisor") * "divisor").
     * @param divisor The number to divide by.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RemainderNaturalScale(ExtendedDecimal divisor) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedDecimal(this.getEd().RemainderNaturalScale(divisor.getEd()));
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
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RemainderNaturalScale(
ExtendedDecimal divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(
this.getEd().RemainderNaturalScale(
divisor.getEd(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision decimal numbers, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision decimal number.
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
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal DivideToExponent(
ExtendedDecimal divisor,
long desiredExponentSmall,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(
this.getEd().DivideToExponent(
divisor.getEd(),
desiredExponentSmall,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides this arbitrary-precision decimal number by another
     * arbitrary-precision decimal number. The preferred exponent for the
     * result is this object's exponent minus the divisor's exponent.
     * @param divisor The number to divide by.
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
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Divide(
ExtendedDecimal divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(
   this.getEd().Divide(
   divisor.getEd(),
   ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision decimal numbers, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision decimal number.
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
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal DivideToExponent(
ExtendedDecimal divisor,
long desiredExponentSmall,
Rounding rounding) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      return new ExtendedDecimal(
this.getEd().DivideToExponent(
divisor.getEd(),
desiredExponentSmall,
ExtendedDecimal.ToERounding(rounding)));
    }

    /**
     * Divides two arbitrary-precision decimal numbers, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision decimal number.
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
     * @throws java.lang.NullPointerException The parameter {@code divisor} or {@code
     * exponent} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal DivideToExponent(
ExtendedDecimal divisor,
BigInteger exponent,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        if (exponent == null) {
          throw new NullPointerException("exponent");
        }
        return new ExtendedDecimal(
     this.getEd().DivideToExponent(
     divisor.getEd(),
     exponent.getEi(),
     ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides two arbitrary-precision decimal numbers, and gives a particular
     * exponent to the result.
     * @param divisor An arbitrary-precision decimal number.
     * @param desiredExponent The desired exponent. A negative number places the
     * cutoff point to the right of the usual decimal point. A positive
     * number places the cutoff point to the left of the usual decimal
     * point.
     * @param rounding The rounding mode to use if the result must be scaled down
     * to have the same exponent as this value.
     * @return The quotient of the two objects. Signals FlagDivideByZero and
     * returns infinity if the divisor is 0 and the dividend is nonzero.
     * Returns not-a-number (NaN) if the divisor and the dividend are 0.
     * Returns NaN if the rounding mode is Rounding.Unnecessary and the
     * result is not exact.
     * @throws java.lang.NullPointerException The parameter {@code divisor} or {@code
     * desiredExponent} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal DivideToExponent(
ExtendedDecimal divisor,
BigInteger desiredExponent,
Rounding rounding) {
      if (divisor == null) {
        throw new NullPointerException("divisor");
      }
      if (desiredExponent == null) {
        throw new NullPointerException("desiredExponent");
      }
      return new ExtendedDecimal(
this.getEd().DivideToExponent(
divisor.getEd(),
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Abs(PrecisionContext context) {
      try {
        return new ExtendedDecimal(this.getEd().Abs(context == null ? null :
                context.getEc()));
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
     * @return An arbitrary-precision decimal number. If this value is positive
     * zero, returns positive zero.
     * @throws java.lang.NullPointerException The parameter {@code context} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Negate(PrecisionContext context) {
      try {
        if (context == null) {
          throw new NullPointerException("context");
        }
        return new ExtendedDecimal(this.getEd().Negate(context == null ? null :
          context.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Adds this object and another decimal number and returns the result.
     * @param otherValue An arbitrary-precision decimal number.
     * @return The sum of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Add(ExtendedDecimal otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedDecimal(this.getEd().Add(otherValue.getEd()));
    }

    /**
     * Subtracts an arbitrary-precision decimal number from this instance and
     * returns the result.
     * @param otherValue An arbitrary-precision decimal number.
     * @return The difference of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Subtract(ExtendedDecimal otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedDecimal(this.getEd().Subtract(otherValue.getEd()));
    }

    /**
     * Subtracts an arbitrary-precision decimal number from this instance.
     * @param otherValue An arbitrary-precision decimal number.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The difference of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Subtract(
ExtendedDecimal otherValue,
PrecisionContext ctx) {
      try {
        if (otherValue == null) {
          throw new NullPointerException("otherValue");
        }
        return new ExtendedDecimal(
        this.getEd().Subtract(
        otherValue.getEd(),
        ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Multiplies two decimal numbers. The resulting exponent will be the sum of
     * the exponents of the two decimal numbers.
     * @param otherValue Another decimal number.
     * @return The product of the two decimal numbers.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Multiply(ExtendedDecimal otherValue) {
      if (otherValue == null) {
        throw new NullPointerException("otherValue");
      }
      return new ExtendedDecimal(this.getEd().Multiply(otherValue.getEd()));
    }

    /**
     * Multiplies by one decimal number, and then adds another decimal number.
     * @param multiplicand The value to multiply.
     * @param augend The value to add.
     * @return The result this * {@code multiplicand} + {@code augend}.
     * @throws java.lang.NullPointerException The parameter {@code multiplicand} or
     * {@code augend} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MultiplyAndAdd(
ExtendedDecimal multiplicand,
ExtendedDecimal augend) {
      if (multiplicand == null) {
        throw new NullPointerException("multiplicand");
      }
      if (augend == null) {
        throw new NullPointerException("augend");
      }
      return new ExtendedDecimal(
      this.getEd().MultiplyAndAdd(
      multiplicand.getEd(),
      augend.getEd()));
    }

    /**
     * Divides this object by another object, and returns the integer part of the
     * result, with the preferred exponent set to this value's exponent
     * minus the divisor's exponent.
     * @param divisor The number to divide by.
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
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal DivideToIntegerNaturalScale(
ExtendedDecimal divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(
   this.getEd().DivideToIntegerNaturalScale(
   divisor.getEd(),
   ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Divides this object by another object, and returns the integer part of the
     * result, with the exponent set to 0.
     * @param divisor The number to divide by.
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal DivideToIntegerZeroScale(
ExtendedDecimal divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(
this.getEd().DivideToIntegerZeroScale(
divisor.getEd(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the remainder that results when dividing two arbitrary-precision
     * decimal numbers.
     * @param divisor An arbitrary-precision decimal number.
     * @param ctx The parameter {@code ctx} is not documented yet.
     * @return The remainder of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Remainder(
ExtendedDecimal divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(
      this.getEd().Remainder(
      divisor.getEd(),
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
     * @param divisor The number to divide by.
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RemainderNear(
ExtendedDecimal divisor,
PrecisionContext ctx) {
      try {
        if (divisor == null) {
          throw new NullPointerException("divisor");
        }
        return new ExtendedDecimal(
   this.getEd().RemainderNear(
   divisor.getEd(),
   ctx == null ? null : ctx.getEc()));
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal NextMinus(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().NextMinus(ctx == null ? null :
              ctx.getEc()));
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal NextPlus(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().NextPlus(ctx == null ? null :
             ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Finds the next value that is closer to the other object's value than this
     * object's value. Returns a copy of this value with the same sign as
     * the other value if both values are equal.
     * @param otherValue An arbitrary-precision decimal number.
     * @param ctx A precision context object to control the precision and exponent
     * range of the result. The rounding mode from this context is ignored.
     * If HasFlags of the context is true, will also store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags).
     * @return Returns the next value that is closer to the other object' s value
     * than this object's value. Signals FlagInvalid and returns NaN if the
     * parameter {@code ctx} is null, the precision is 0, or {@code ctx} has
     * an unlimited exponent range.
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal NextToward(
ExtendedDecimal otherValue,
PrecisionContext ctx) {
      try {
        if (otherValue == null) {
          throw new NullPointerException("otherValue");
        }
        return new ExtendedDecimal(
   this.getEd().NextToward(
   otherValue.getEd(),
   ctx == null ? null : ctx.getEc()));
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
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal Max(
ExtendedDecimal first,
ExtendedDecimal second,
PrecisionContext ctx) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(
EDecimal.Max(
first.getEd(),
second.getEd(),
ctx == null ? null : ctx.getEc()));
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
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal Min(
ExtendedDecimal first,
ExtendedDecimal second,
PrecisionContext ctx) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(
EDecimal.Min(
first.getEd(),
second.getEd(),
ctx == null ? null : ctx.getEc()));
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
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal MaxMagnitude(
ExtendedDecimal first,
ExtendedDecimal second,
PrecisionContext ctx) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(
EDecimal.MaxMagnitude(
first.getEd(),
second.getEd(),
ctx == null ? null : ctx.getEc()));
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
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal MinMagnitude(
ExtendedDecimal first,
ExtendedDecimal second,
PrecisionContext ctx) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(
EDecimal.MinMagnitude(
first.getEd(),
second.getEd(),
ctx == null ? null : ctx.getEc()));
    }

    /**
     * Gets the greater value between two decimal numbers.
     * @param first An arbitrary-precision decimal number.
     * @param second Another arbitrary-precision decimal number.
     * @return The larger value of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal Max(
ExtendedDecimal first,
ExtendedDecimal second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.Max(first.getEd(), second.getEd()));
    }

    /**
     * Gets the lesser value between two decimal numbers.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @return The smaller value of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal Min(
ExtendedDecimal first,
ExtendedDecimal second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.Min(first.getEd(), second.getEd()));
    }

    /**
     * Gets the greater value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Max.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal MaxMagnitude(
ExtendedDecimal first,
ExtendedDecimal second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.MaxMagnitude(first.getEd(), second.getEd()));
    }

    /**
     * Gets the lesser value between two values, ignoring their signs. If the
     * absolute values are equal, has the same effect as Min.
     * @param first The first value to compare.
     * @param second The second value to compare.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code first} or {@code
     * second} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal MinMagnitude(
ExtendedDecimal first,
ExtendedDecimal second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      return new ExtendedDecimal(EDecimal.MinMagnitude(first.getEd(), second.getEd()));
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
     * @param other An arbitrary-precision decimal number.
     * @return Less than 0 if this object's value is less than the other value, or
     * greater than 0 if this object's value is greater than the other value
     * or if {@code other} is null, or 0 if both values are equal.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public int compareTo(ExtendedDecimal other) {
      if (other == null) {
        throw new NullPointerException("other");
      }
      return this.getEd().compareTo(other.getEd());
    }

    /**
     * Compares the mathematical values of this object and another object. <p>In
     * this method, negative zero and positive zero are considered
     * equal.</p> <p>If this object or the other object is a quiet NaN or
     * signaling NaN, this method returns a quiet NaN, and will signal a
     * FlagInvalid flag if either is a signaling NaN.</p>
     * @param other An arbitrary-precision decimal number.
     * @param ctx A precision context. The precision, rounding, and exponent range
     * are ignored. If HasFlags of the context is true, will store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null.
     * @return Quiet NaN if this object or the other object is NaN, or 0 if both
     * objects have the same value, or -1 if this object is less than the
     * other value, or 1 if this object is greater.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal CompareToWithContext(
ExtendedDecimal other,
PrecisionContext ctx) {
      try {
        if (other == null) {
          throw new NullPointerException("other");
        }
        return new ExtendedDecimal(
        this.getEd().CompareToWithContext(
        other.getEd(),
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
     * @param other An arbitrary-precision decimal number.
     * @param ctx A precision context. The precision, rounding, and exponent range
     * are ignored. If HasFlags of the context is true, will store the flags
     * resulting from the operation (the flags are in addition to the
     * pre-existing flags). Can be null.
     * @return Quiet NaN if this object or the other object is NaN, or 0 if both
     * objects have the same value, or -1 if this object is less than the
     * other value, or 1 if this object is greater.
     * @throws java.lang.NullPointerException The parameter {@code other} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal CompareToSignal(
ExtendedDecimal other,
PrecisionContext ctx) {
      try {
        if (other == null) {
          throw new NullPointerException("other");
        }
        return new ExtendedDecimal(
   this.getEd().CompareToSignal(
   other.getEd(),
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Add(
ExtendedDecimal otherValue,
PrecisionContext ctx) {
      try {
        if (otherValue == null) {
          throw new NullPointerException("otherValue");
        }
        return new ExtendedDecimal(
   this.getEd().Add(
   otherValue.getEd(),
   ctx == null ? null : ctx.getEc()));
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
     * @throws java.lang.NullPointerException The parameter {@code desiredExponent}
     * is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Quantize(
BigInteger desiredExponent,
PrecisionContext ctx) {
      try {
        if (desiredExponent == null) {
          throw new NullPointerException("desiredExponent");
        }
        return new ExtendedDecimal(
      this.getEd().Quantize(
      desiredExponent.getEi(),
      ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this one but a new exponent.
     * @param desiredExponentSmall The desired exponent for the result. The
     * exponent is the number of fractional digits in the result, expressed
     * as a negative number. Can also be positive, which eliminates
     * lower-order places from the number. For example, -3 means round to
     * the thousandth (10^-3, 0.0001), and 3 means round to the thousand
     * (10^3, 1000). A value of 0 rounds the number to an integer.
     * @param rounding The mode to use when the result needs to be rounded in order
     * to have the given exponent.
     * @return A decimal number with the same value as this object but with the
     * exponent changed. Returns not-a-number (NaN) if the rounding mode is
     * Rounding.Unnecessary and the result is not exact.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Quantize(
int desiredExponentSmall,
Rounding rounding) {
      return new ExtendedDecimal(
this.getEd().Quantize(
desiredExponentSmall,
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Quantize(
int desiredExponentSmall,
PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
        this.getEd().Quantize(
        desiredExponentSmall,
        ctx == null ? null : ctx.getEc()));
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
     * @throws java.lang.NullPointerException The parameter {@code otherValue} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Quantize(
ExtendedDecimal otherValue,
PrecisionContext ctx) {
      try {
        if (otherValue == null) {
          throw new NullPointerException("otherValue");
        }
        return new ExtendedDecimal(
        this.getEd().Quantize(
        otherValue.getEd(),
        ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to
     * an integer, and signals an inexact flag if the result would be
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RoundToIntegralExact(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().RoundToIntegralExact(ctx == null ?
               null : ctx.getEc()));
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RoundToIntegralNoRoundedFlag(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().RoundToIntegralNoRoundedFlag(ctx ==
                null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to
     * an integer, and signals an inexact flag if the result would be
     * inexact.
     * @param exponent The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places from the number. For example, -3 means round to the thousandth
     * (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
     * value of 0 rounds the number to an integer.
     * @param ctx A context object for arbitrary-precision arithmetic settings.
     * @return A decimal number rounded to the closest value representable in the
     * given precision. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding. Signals
     * FlagInvalid and returns not-a-number (NaN) if the precision context
     * defines an exponent range, the new exponent must be changed to the
     * given exponent when rounding, and the given exponent is outside of
     * the valid range of the precision context.
     * @throws java.lang.NullPointerException The parameter {@code exponent} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RoundToExponentExact(
BigInteger exponent,
PrecisionContext ctx) {
      try {
        if (exponent == null) {
          throw new NullPointerException("exponent");
        }
        return new ExtendedDecimal(
this.getEd().RoundToExponentExact(
exponent.getEi(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to a
     * new exponent if necessary.
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
     * @throws java.lang.NullPointerException The parameter {@code exponent} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RoundToExponent(
BigInteger exponent,
PrecisionContext ctx) {
      try {
        if (exponent == null) {
          throw new NullPointerException("exponent");
        }
        return new ExtendedDecimal(
      this.getEd().RoundToExponent(
      exponent.getEi(),
      ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to
     * an integer, and signals an inexact flag if the result would be
     * inexact.
     * @param exponentSmall The minimum exponent the result can have. This is the
     * maximum number of fractional digits in the result, expressed as a
     * negative number. Can also be positive, which eliminates lower-order
     * places from the number. For example, -3 means round to the thousandth
     * (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
     * value of 0 rounds the number to an integer.
     * @param ctx A context object for arbitrary-precision arithmetic settings.
     * @return A decimal number rounded to the closest value representable in the
     * given precision. Signals FlagInvalid and returns not-a-number (NaN)
     * if the result can't fit the given precision without rounding. Signals
     * FlagInvalid and returns not-a-number (NaN) if the precision context
     * defines an exponent range, the new exponent must be changed to the
     * given exponent when rounding, and the given exponent is outside of
     * the valid range of the precision context.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RoundToExponentExact(
int exponentSmall,
PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
this.getEd().RoundToExponentExact(
exponentSmall,
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a decimal number with the same value as this object but rounded to a
     * new exponent if necessary.
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RoundToExponent(
int exponentSmall,
PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
        this.getEd().RoundToExponent(
        exponentSmall,
        ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Multiplies two decimal numbers. The resulting scale will be the sum of the
     * scales of the two decimal numbers. The result's sign is positive if
     * both operands have the same sign, and negative if they have different
     * signs.
     * @param op Another decimal number.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return The product of the two decimal numbers.
     * @throws java.lang.NullPointerException The parameter {@code op} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Multiply(ExtendedDecimal op, PrecisionContext ctx) {
      try {
        if (op == null) {
          throw new NullPointerException("op");
        }
        return new ExtendedDecimal(
this.getEd().Multiply(
op.getEd(),
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MultiplyAndAdd(
ExtendedDecimal op,
ExtendedDecimal augend,
PrecisionContext ctx) {
      try {
        if (op == null) {
          throw new NullPointerException("op");
        }
        if (augend == null) {
          throw new NullPointerException("augend");
        }
        return new ExtendedDecimal(
this.getEd().MultiplyAndAdd(
op.getEd(),
augend.getEd(),
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MultiplyAndSubtract(
ExtendedDecimal op,
ExtendedDecimal subtrahend,
PrecisionContext ctx) {
      try {
        if (op == null) {
          throw new NullPointerException("op");
        }
        if (subtrahend == null) {
          throw new NullPointerException("subtrahend");
        }
        return new ExtendedDecimal(
     this.getEd().MultiplyAndSubtract(
     op.getEd(),
     subtrahend.getEd(),
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RoundToPrecision(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().RoundToPrecision(ctx == null ? null :
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Plus(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().Plus(ctx == null ? null : ctx.getEc()));
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal RoundToBinaryPrecision(PrecisionContext ctx) {
      if (ctx == null) {
        return this;
      }
      PrecisionContext ctx2 = ctx.Copy().WithPrecisionInBits(true);
      ExtendedDecimal ret = this.RoundToPrecision(ctx2);
      if (ctx2.getHasFlags()) {
        ctx.setFlags(ctx2.getFlags());
      }
      return ret;
    }

    /**
     * Finds the square root of this object&#x27;s value.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as the square root function's results are generally not exact
     * for many inputs.</i>
     * @return The square root. Signals the flag FlagInvalid and returns NaN if
     * this object is less than 0 (the square root would be a complex
     * number, but the return value is still NaN). Signals FlagInvalid and
     * returns not-a-number (NaN) if the parameter {@code ctx} is null or
     * the precision is unlimited (the context's Precision property is 0).
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal SquareRoot(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().SquareRoot(ctx == null ? null :
               ctx.getEc()));
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
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as the exponential function's results are generally not
     * exact.</i>
     * @return Exponential of this object. If this object's value is 1, returns an
     * approximation to " e" within the given precision. Signals FlagInvalid
     * and returns not-a-number (NaN) if the parameter {@code ctx} is null
     * or the precision is unlimited (the context's Precision property is
     * 0).
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Exp(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().Exp(ctx == null ? null : ctx.getEc()));
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
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as the ln function's results are generally not exact.</i>
     * @return Ln(this object). Signals the flag FlagInvalid and returns NaN if
     * this object is less than 0 (the result would be a complex number with
     * a real part equal to Ln of this object's absolute value and an
     * imaginary part equal to pi, but the return value is still NaN.).
     * Signals FlagInvalid and returns not-a-number (NaN) if the parameter
     * {@code ctx} is null or the precision is unlimited (the context's
     * Precision property is 0). Signals no flags and returns negative
     * infinity if this object's value is 0.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Log(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().Log(ctx == null ? null : ctx.getEc()));
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Log10(PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(this.getEd().Log10(ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Raises this object's value to the given exponent.
     * @param exponent An arbitrary-precision decimal number.
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
     * @throws java.lang.NullPointerException The parameter {@code exponent} is null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Pow(ExtendedDecimal exponent, PrecisionContext ctx) {
      try {
        if (exponent == null) {
          throw new NullPointerException("exponent");
        }
        return new ExtendedDecimal(
this.getEd().Pow(
exponent.getEd(),
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Pow(int exponentSmall, PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
   this.getEd().Pow(
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Pow(int exponentSmall) {
      return new ExtendedDecimal(this.getEd().Pow(exponentSmall));
    }

    /**
     * Finds the constant &#x3c0;.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). <i>This parameter cannot be
     * null, as &#x3c0; can never be represented exactly.</i>
     * @return π rounded to the given precision. Signals FlagInvalid and returns
     * not-a-number (NaN) if the parameter {@code ctx} is null or the
     * precision is unlimited (the context's Precision property is 0).
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static ExtendedDecimal PI(PrecisionContext ctx) {
      return new ExtendedDecimal(EDecimal.PI(ctx == null ? null : ctx.getEc()));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the left.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MovePointLeft(int places) {
      return new ExtendedDecimal(this.getEd().MovePointLeft(places));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the left.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MovePointLeft(int places, PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
      this.getEd().MovePointLeft(
      places,
      ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the left.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MovePointLeft(BigInteger bigPlaces) {
      if (bigPlaces == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedDecimal(this.getEd().MovePointLeft(bigPlaces.getEi()));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the left.
     * @param bigPlaces An arbitrary-precision integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MovePointLeft(
BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if (bigPlaces == null) {
          throw new NullPointerException("bigPlaces");
        }
        return new ExtendedDecimal(
this.getEd().MovePointLeft(
bigPlaces.getEi(),
ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the right.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MovePointRight(int places) {
      return new ExtendedDecimal(this.getEd().MovePointRight(places));
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the right.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MovePointRight(int places, PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
       this.getEd().MovePointRight(
       places,
       ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the decimal point moved to
     * the right.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MovePointRight(BigInteger bigPlaces) {
      if (bigPlaces == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedDecimal(this.getEd().MovePointRight(bigPlaces.getEi()));
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
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal MovePointRight(
BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if (bigPlaces == null) {
          throw new NullPointerException("bigPlaces");
        }

        return new ExtendedDecimal(
      this.getEd().MovePointRight(
      bigPlaces.getEi(),
      ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param places A 32-bit signed integer.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal ScaleByPowerOfTen(int places) {
      return new ExtendedDecimal(this.getEd().ScaleByPowerOfTen(places));
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param places A 32-bit signed integer.
     * @param ctx A precision context to control precision, rounding, and exponent
     * range of the result. If HasFlags of the context is true, will also
     * store the flags resulting from the operation (the flags are in
     * addition to the pre-existing flags). Can be null.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal ScaleByPowerOfTen(int places, PrecisionContext ctx) {
      try {
        return new ExtendedDecimal(
   this.getEd().ScaleByPowerOfTen(
   places,
   ctx == null ? null : ctx.getEc()));
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }

    /**
     * Returns a number similar to this number but with the scale adjusted.
     * @param bigPlaces An arbitrary-precision integer.
     * @return An arbitrary-precision decimal number.
     * @throws java.lang.NullPointerException The parameter {@code bigPlaces} is
     * null.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal ScaleByPowerOfTen(BigInteger bigPlaces) {
      if (bigPlaces == null) {
        throw new NullPointerException("bigPlaces");
      }
      return new ExtendedDecimal(this.getEd().ScaleByPowerOfTen(bigPlaces.getEi()));
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal ScaleByPowerOfTen(
BigInteger bigPlaces,
PrecisionContext ctx) {
      try {
        if (bigPlaces == null) {
          throw new NullPointerException("bigPlaces");
        }

        return new ExtendedDecimal(
this.getEd().ScaleByPowerOfTen(
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public BigInteger Precision() {
      return new BigInteger(this.getEd().Precision());
    }

    /**
     * Returns the unit in the last place. The mantissa will be 1 and the exponent
     * will be this number's exponent. Returns 1 with an exponent of 0 if
     * this number is infinity or NaN.
     * @return An arbitrary-precision decimal number.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal Ulp() {
      return new ExtendedDecimal(this.getEd().Ulp());
    }

    /**
     * Calculates the quotient and remainder using the DivideToIntegerNaturalScale
     * and the formula in RemainderNaturalScale.
     * @param divisor The number to divide by.
     * @return A 2 element array consisting of the quotient and remainder in that
     * order.
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal[] DivideAndRemainderNaturalScale(ExtendedDecimal
      divisor) {
      EDecimal[] edec = this.getEd().DivideAndRemainderNaturalScale(divisor ==
        null ? null : divisor.getEd());
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
     * @deprecated Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public ExtendedDecimal[] DivideAndRemainderNaturalScale(
      ExtendedDecimal divisor,
      PrecisionContext ctx) {
      try {
        EDecimal[] edec = this.getEd().DivideAndRemainderNaturalScale(
divisor == null ? null : divisor.getEd(),
ctx == null ? null : ctx.getEc());
        return new ExtendedDecimal[] {
        new ExtendedDecimal(edec[0]), new ExtendedDecimal(edec[1])
      };
      } catch (ETrapException ex) {
        throw TrapException.Create(ex);
      }
    }
  }
