package com.upokecenter.util;
/*
Written by Peter O. in 2013.

Parts of the code were adapted by Peter O. from
the public-domain code from the library
CryptoPP by Wei Dai.

Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.numbers.*;

    /**
     * <p><b>This class is largely obsolete. It will be replaced by a new version
     * of this class in a different namespace/package and library, called
     * <code>PeterO.Numbers.EInteger</code> in the <a
  * href='https://www.nuget.org/packages/PeterO.Numbers'><code>PeterO.Numbers</code></a>
     * library (in .NET), or <code>com.upokecenter.numbers.EInteger</code> in the
     * <a
  * href='https://github.com/peteroupc/numbers-java'><code>com.github.peteroupc/numbers</code></a>
     * artifact (in Java). This new class can be used in the
     * <code>CBORObject.FromObject(Object)</code> method (by including the new
     * library in your code, among other things), but this version of the
     * CBOR library doesn't include any methods that explicitly take an
     * <code>EInteger</code> as a parameter or return value.</b></p> An
     * arbitrary-precision integer. <p><b>Thread safety:</b>Instances of
     * this class are immutable, so they are inherently safe for use by
     * multiple threads. Multiple instances of this object with the same
     * value are interchangeable, but they should be compared using the
     * "Equals" method rather than the "==" operator.</p>
     */
  public final class BigInteger implements Comparable<BigInteger> {
    /**
     * BigInteger for the number one.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final BigInteger ONE = new BigInteger(EInteger.FromInt32(1));

    private static final BigInteger ValueOneValue = new
      BigInteger(EInteger.FromInt32(1));

    private final EInteger ei;

    BigInteger(EInteger ei) {
      if (ei == null) {
  throw new NullPointerException("ei");
}
      this.ei = ei;
    }

    static BigInteger ToLegacy(EInteger ei) {
      return new BigInteger(ei);
    }

    static EInteger FromLegacy(BigInteger bei) {
      return bei.getEi();
    }

    /**
     * BigInteger for the number ten.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public static final BigInteger TEN = BigInteger.valueOf(10);

    /**
     * BigInteger for the number zero.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public static final BigInteger ZERO = new
      BigInteger(EInteger.FromInt32(0));

  private static final BigInteger ValueZeroValue = new
      BigInteger(EInteger.FromInt32(0));

    /**
     * Gets a value indicating whether this value is even.
     * @return {@code true} if this value is even; otherwise, {@code false}.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public final boolean isEven() {
 return this.getEi().isEven();
}

    /**
     * Gets a value indicating whether this value is 0.
     * @return {@code true} if this value is 0; otherwise, {@code false}.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public final boolean isZero() {
 return this.getEi().isZero();
}

    /**
     * Gets the sign of this object's value.
     * @return 0 if this value is zero; -1 if this value is negative, or 1 if this
     * value is positive.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public final int signum() {
 return this.getEi().signum();
}

    final EInteger getEi() {
        return this.ei;
      }

    /**
     * Initializes an arbitrary-precision integer from an array of bytes.
     * @param bytes A byte array.
     * @param littleEndian A Boolean object.
     * @return An arbitrary-precision integer.
     * @throws java.lang.NullPointerException The parameter {@code bytes} is null.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public static BigInteger fromByteArray(byte[] bytes, boolean littleEndian) {
      return new BigInteger(EInteger.FromBytes(bytes, littleEndian));
 }

    /**
     * Initializes an arbitrary-precision integer from an array of bytes.
     * @param bytes A byte array consisting of the two's-complement integer
     * representation of the arbitrary-precision integer to create. The last
     * byte contains the lowest 8-bits, the next-to-last contains the next
     * lowest 8 bits, and so on. To encode negative numbers, take the
     * absolute value of the number, subtract by 1, encode the number into
     * bytes, XOR each byte, and if the most-significant bit of the first
     * byte isn't set, add an additional byte at the start with the value
     * 255. For little-endian, the byte order is reversed from the byte
     * order just discussed.
     * @param littleEndian If true, the byte order is little-endian, or
     * least-significant-byte first. If false, the byte order is big-endian,
     * or most-significant-byte first.
     * @return An arbitrary-precision integer. Returns 0 if the byte array's length
     * is 0.
     * @throws java.lang.NullPointerException The parameter {@code bytes} is null.
     */
  public static BigInteger fromBytes(byte[] bytes, boolean littleEndian) {
      return new BigInteger(EInteger.FromBytes(bytes, littleEndian));
    }

    /**
     * Converts a string to an arbitrary-precision integer.
     * @param str A text string. The string must contain only characters allowed by
     * the given radix, except that it may start with a minus sign ("-",
     * U + 002D) to indicate a negative number. The string is not allowed to
     * contain white space characters, including spaces.
     * @param radix A base from 2 to 36. Depending on the radix, the string can use
     * the basic digits 0 to 9 (U + 0030 to U + 0039) and then the basic letters
     * A to Z (U + 0041 to U + 005A). For example, 0-9 in radix 10, and 0-9,
     * then A-F in radix 16.
     * @return An arbitrary-precision integer with the same value as given in the
     * string.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws IllegalArgumentException The parameter {@code radix} is less than 2
     * or greater than 36.
     * @throws java.lang.NumberFormatException The string is empty or in an invalid format.
     */
  public static BigInteger fromRadixString(String str, int radix) {
      return new BigInteger(EInteger.FromRadixString(str, radix));
    }

    /**
     * Converts a portion of a string to an arbitrary-precision integer in a given
     * radix.
     * @param str A text string. The desired portion of the string must contain
     * only characters allowed by the given radix, except that it may start
     * with a minus sign ("-", U+002D) to indicate a negative number. The
     * desired portion is not allowed to contain white space characters,
     * including spaces.
     * @param radix A base from 2 to 36. Depending on the radix, the string can use
     * the basic digits 0 to 9 (U + 0030 to U + 0039) and then the basic letters
     * A to Z (U + 0041 to U + 005A). For example, 0-9 in radix 10, and 0-9,
     * then A-F in radix 16.
     * @param index The index of the string that starts the string portion.
     * @param endIndex The index of the string that ends the string portion. The
     * length will be index + endIndex - 1.
     * @return An arbitrary-precision integer with the same value as given in the
     * string portion.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws IllegalArgumentException The parameter {@code index} is less than 0,
     * {@code endIndex} is less than 0, or either is greater than the
     * string's length, or {@code endIndex} is less than {@code index}.
     * @throws java.lang.NumberFormatException The string portion is empty or in an invalid
     * format.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public static BigInteger fromRadixSubstring(
      String str,
      int radix,
      int index,
      int endIndex) {
 return new BigInteger(
  EInteger.FromRadixSubstring(
  str,
  radix,
  index,
  endIndex));
    }

    /**
     * Converts a string to an arbitrary-precision integer.
     * @param str A text string. The string must contain only basic digits 0 to 9
     * (U+0030 to U+0039), except that it may start with a minus sign ("-",
     * U + 002D) to indicate a negative number. The string is not allowed to
     * contain white space characters, including spaces.
     * @return An arbitrary-precision integer with the same value as given in the
     * string.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws java.lang.NumberFormatException The parameter {@code str} is in an invalid
     * format.
     */
  public static BigInteger fromString(String str) {
return new BigInteger(EInteger.FromString(str));
}

    /**
     * Converts a portion of a string to an arbitrary-precision integer.
     * @param str A text string. The desired portion of the string must contain
     * only basic digits 0 to 9 (U + 0030 to U + 0039), except that it may start
     * with a minus sign ("-", U+002D) to indicate a negative number. The
     * desired portion is not allowed to contain white space characters,
     * including spaces.
     * @param index The index of the string that starts the string portion.
     * @param endIndex The index of the string that ends the string portion. The
     * length will be index + endIndex - 1.
     * @return An arbitrary-precision integer with the same value as given in the
     * string portion.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws IllegalArgumentException The parameter {@code index} is less than 0,
     * {@code endIndex} is less than 0, or either is greater than the
     * string's length, or {@code endIndex} is less than {@code index}.
     * @throws java.lang.NumberFormatException The string portion is empty or in an invalid
     * format.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public static BigInteger fromSubstring(
  String str,
  int index,
  int endIndex) {
return new BigInteger(EInteger.FromSubstring(str, index, endIndex));
}

    /**
     * Converts a 64-bit signed integer to a big integer.
     * @param longerValue A 64-bit signed integer.
     * @return An arbitrary-precision integer with the same value as the 64-bit
     * number.
     */
  public static BigInteger valueOf(long longerValue) {
      return new BigInteger(EInteger.FromInt64(longerValue));
 }

    /**
     * Returns the absolute value of this object's value.
     * @return This object's value with the sign removed.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger abs() {
      return new BigInteger(this.getEi().Abs());
 }

    /**
     * Adds this object and another object.
     * @param bigintAugend Another arbitrary-precision integer.
     * @return The sum of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code bigintAugend} is
     * null.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger add(BigInteger bigintAugend) {
      if (bigintAugend == null) {
  throw new NullPointerException("bigintAugend");
}
return new BigInteger(this.getEi().Add(bigintAugend.getEi()));
 }

    /**
     * Finds the minimum number of bits needed to represent this object&#x27;s
     * value, except for its sign. If the value is negative, finds the
     * number of bits in a value equal to this object's absolute value minus
     * 1.
     * @return The number of bits in this object's value. Returns 0 if this
     * object's value is 0 or negative 1.
     */
  public int bitLength() {
return this.getEi().GetSignedBitLength();
 }

    /**
     * Returns whether this object's value can fit in a 32-bit signed integer.
     * @return {@code true} if this object's value is MinValue or greater, and
     * MaxValue or less; otherwise, {@code false}.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public boolean canFitInInt() {
return this.getEi().CanFitInInt32();
      }

    /**
     * Compares an arbitrary-precision integer with this instance.
     * @param other The parameter {@code other} is not documented yet.
     * @return Zero if the values are equal; a negative number if this instance is
     * less, or a positive number if this instance is greater.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public int compareTo(BigInteger other) {
      return (other == null) ? 1 : this.getEi().compareTo(other.getEi());
 }

    /**
     * Divides this instance by the value of an arbitrary-precision integer. The
     * result is rounded down (the fractional part is discarded). Except if
     * the result is 0, it will be negative if this object is positive and
     * the other is negative, or vice versa, and will be positive if both
     * are positive or both are negative.
     * @param bigintDivisor Another arbitrary-precision integer.
     * @return The quotient of the two objects.
     * @throws ArithmeticException The divisor is zero.
     * @throws java.lang.NullPointerException The parameter {@code bigintDivisor} is
     * null.
     * @throws ArithmeticException Attempted to divide by zero.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger divide(BigInteger bigintDivisor) {
      if (bigintDivisor == null) {
        throw new NullPointerException("bigintDivisor");
      }
      return new BigInteger(this.getEi().Divide(bigintDivisor.getEi()));
 }

    /**
     * Divides this object by another big integer and returns the quotient and
     * remainder.
     * @param divisor An arbitrary-precision integer.
     * @return An array with two big integers: the first is the quotient, and the
     * second is the remainder.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @throws ArithmeticException The parameter {@code divisor} is 0.
     * @throws ArithmeticException Attempted to divide by zero.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger[] divideAndRemainder(BigInteger divisor) {
      if (divisor == null) {
  throw new NullPointerException("divisor");
}
      EInteger[] eia = this.getEi().DivRem(divisor.getEi());
    return new BigInteger[] { new BigInteger(eia[0]), new BigInteger(eia[1])
        };
 }

    /**
     * Determines whether this object and another object are equal.
     * @param obj An arbitrary object.
     * @return {@code true} if this object and another object are equal; otherwise,
     * {@code false}.
     */
  @Override public boolean equals(Object obj) {
      BigInteger bi = ((obj instanceof BigInteger) ? (BigInteger)obj : null);
      return (bi == null) ? false : this.getEi().equals(bi.getEi());
}

    /**
     * Returns the greatest common divisor of two integers. The greatest common
     * divisor (GCD) is also known as the greatest common factor (GCF).
     * @param bigintSecond Another arbitrary-precision integer.
     * @return An arbitrary-precision integer.
     * @throws java.lang.NullPointerException The parameter {@code bigintSecond} is
     * null.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger gcd(BigInteger bigintSecond) {
  if (bigintSecond == null) {
  throw new NullPointerException("bigintSecond");
}
return new BigInteger(this.getEi().Gcd(bigintSecond.getEi()));
}

    /**
     * Finds the number of decimal digits this number has.
     * @return The number of decimal digits. Returns 1 if this object' s value is
     * 0.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public int getDigitCount() {
      return this.getEi().GetDigitCount();
 }

    /**
     * Returns the hash code for this instance.
     * @return A 32-bit signed integer.
     */
  @Override public int hashCode() {
      return this.getEi().hashCode();
 }

    /**
     * Gets the lowest set bit in this number's absolute value.
     * @return The lowest bit set in the number, starting at 0. Returns 0 if this
     * value is 0 or odd. (NOTE: In future versions, may return -1 instead
     * if this value is 0.).
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public int getLowBit() {
      return this.isZero() ? 0 : this.getEi().GetLowBit();
 }

    /**
     * See <code>getLowBit()</code>
     * @return See getLowBit().
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public int getLowestSetBit() {
      return this.getLowBit();
 }

    /**
     * Finds the minimum number of bits needed to represent this object&#x27;s
     * absolute value.
     * @return The number of bits in this object's value. Returns 0 if this
     * object's value is 0, and returns 1 if the value is negative 1.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public int getUnsignedBitLength() {
      return this.getUnsignedBitLength();
 }

    /**
     * Converts this object's value to a 32-bit signed integer.
     * @return A 32-bit signed integer.
     * @throws java.lang.ArithmeticException This object's value is too big to fit a
     * 32-bit signed integer.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public int intValue() {
return this.getEi().AsInt32Checked();
}

    /**
     * Converts this object's value to a 32-bit signed integer.
     * @return A 32-bit signed integer.
     * @throws java.lang.ArithmeticException This object's value is too big to fit a
     * 32-bit signed integer.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public int intValueChecked() {
return this.getEi().AsInt32Checked();
}

    /**
     * Converts this object's value to a 32-bit signed integer. If the value can't
     * fit in a 32-bit integer, returns the lower 32 bits of this object's
     * two's complement representation (in which case the return value might
     * have a different sign than this object's value).
     * @return A 32-bit signed integer.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public int intValueUnchecked() {
return this.getEi().AsInt32Unchecked();
}

    /**
     * Converts this object's value to a 64-bit signed integer.
     * @return A 64-bit signed integer.
     * @throws java.lang.ArithmeticException This object's value is too big to fit a
     * 64-bit signed integer.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public long longValue() {
return this.getEi().AsInt64Checked();
}

    /**
     * Converts this object's value to a 64-bit signed integer, throwing an
     * exception if it can't fit.
     * @return A 64-bit signed integer.
     * @throws java.lang.ArithmeticException This object's value is too big to fit a
     * 64-bit signed integer.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public long longValueChecked() {
return this.getEi().AsInt64Checked();
}

    /**
     * Converts this object's value to a 64-bit signed integer. If the value can't
     * fit in a 64-bit integer, returns the lower 64 bits of this object's
     * two's complement representation (in which case the return value might
     * have a different sign than this object's value).
     * @return A 64-bit signed integer.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public long longValueUnchecked() {
      return this.getEi().AsInt64Unchecked();
 }

    /**
     * Finds the modulus remainder that results when this instance is divided by
     * the value of an arbitrary-precision integer. The modulus remainder is
     * the same as the normal remainder if the normal remainder is positive,
     * and equals divisor plus normal remainder if the normal remainder is
     * negative.
     * @param divisor A divisor greater than 0 (the modulus).
     * @return An arbitrary-precision integer.
     * @throws ArithmeticException The parameter {@code divisor} is negative.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger mod(BigInteger divisor) {
  if (divisor == null) {
  throw new NullPointerException("divisor");
}
return new BigInteger(this.getEi().Mod(divisor.getEi()));
}

    /**
     * Calculates the remainder when an arbitrary-precision integer raised to a
     * certain power is divided by another arbitrary-precision integer.
     * @param pow Another arbitrary-precision integer.
     * @param mod An arbitrary-precision integer. (3).
     * @return An arbitrary-precision integer.
     * @throws java.lang.NullPointerException The parameter {@code pow} or {@code
     * mod} is null.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger ModPow(BigInteger pow, BigInteger mod) {
  if (pow == null) {
  throw new NullPointerException("pow");
}
  if (mod == null) {
  throw new NullPointerException("mod");
}
return new BigInteger(this.getEi().ModPow(pow.getEi(), mod.getEi()));
}

    /**
     * Multiplies this instance by the value of an arbitrary-precision integer
     * object.
     * @param bigintMult Another arbitrary-precision integer.
     * @return The product of the two numbers.
     * @throws java.lang.NullPointerException The parameter {@code bigintMult} is
     * null.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger multiply(BigInteger bigintMult) {
      if (bigintMult == null) {
        throw new NullPointerException("bigintMult");
      }
      return new BigInteger(this.getEi().Multiply(bigintMult.getEi()));
    }

    /**
     * Gets the value of this object with the sign reversed.
     * @return This object's value with the sign reversed.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger negate() {
      return new BigInteger(this.getEi().Negate());
 }

    /**
     * Raises a big integer to a power.
     * @param powerSmall The exponent to raise to.
     * @return The result. Returns 1 if {@code powerSmall} is 0.
     * @throws IllegalArgumentException The parameter {@code powerSmall} is less
     * than 0.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger pow(int powerSmall) {
return new BigInteger(this.getEi().Pow(powerSmall));
}

    /**
     * Raises a big integer to a power, which is given as another big integer.
     * @param power The exponent to raise to.
     * @return The result. Returns 1 if {@code power} is 0.
     * @throws java.lang.NullPointerException The parameter {@code power} is null.
     * @throws IllegalArgumentException The parameter {@code power} is less than 0.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger PowBigIntVar(BigInteger power) {
  if (power == null) {
  throw new NullPointerException("power");
}
return new BigInteger(this.getEi().PowBigIntVar(power.getEi()));
}

    /**
     * Finds the remainder that results when this instance is divided by the value
     * of an arbitrary-precision integer. The remainder is the value that
     * remains when the absolute value of this object is divided by the
     * absolute value of the other object; the remainder has the same sign
     * (positive or negative) as this object.
     * @param divisor Another arbitrary-precision integer.
     * @return The remainder of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code divisor} is null.
     * @throws ArithmeticException Attempted to divide by zero.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger remainder(BigInteger divisor) {
  if (divisor == null) {
  throw new NullPointerException("divisor");
}
return new BigInteger(this.getEi().Remainder(divisor.getEi()));
}

    /**
     * Returns a big integer with the bits shifted to the left by a number of bits.
     * A value of 1 doubles this value, a value of 2 multiplies it by 4, a
     * value of 3 by 8, a value of 4 by 16, and so on.
     * @param numberBits The number of bits to shift. Can be negative, in which
     * case this is the same as shiftRight with the absolute value of
     * numberBits.
     * @return An arbitrary-precision integer.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger shiftLeft(int numberBits) {
      return new BigInteger(this.getEi().ShiftLeft(numberBits));
 }

    /**
     * Returns a big integer with the bits shifted to the right. For this
     * operation, the arbitrary-precision integer is treated as a two's
     * complement representation. Thus, for negative values, the
     * arbitrary-precision integer is sign-extended.
     * @param numberBits Number of bits to shift right.
     * @return An arbitrary-precision integer.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger shiftRight(int numberBits) {
      return new BigInteger(this.getEi().ShiftRight(numberBits));
    }

    /**
     * Finds the square root of this instance&#x27;s value, rounded down.
     * @return The square root of this object's value. Returns 0 if this value is 0
     * or less.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger sqrt() {
      return new BigInteger(this.getEi().Sqrt());
    }

    /**
     * Calculates the square root and the remainder.
     * @return An array of two big integers: the first integer is the square root,
     * and the second is the difference between this value and the square of
     * the first integer. Returns two zeros if this value is 0 or less, or
     * one and zero if this value equals 1.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger[] sqrtWithRemainder() {
      EInteger[] eia = this.getEi().SqrtRem();
      return new BigInteger[] { new BigInteger(eia[0]), new BigInteger(eia[1])
        };
    }

    /**
     * Subtracts an arbitrary-precision integer from this arbitrary-precision
     * integer.
     * @param subtrahend Another arbitrary-precision integer.
     * @return The difference of the two objects.
     * @throws java.lang.NullPointerException The parameter {@code subtrahend} is
     * null.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public BigInteger subtract(BigInteger subtrahend) {
      if (subtrahend == null) {
  throw new NullPointerException("subtrahend");
}
      return new BigInteger(this.getEi().Subtract(subtrahend.getEi()));
 }

    /**
     * Returns whether a bit is set in the two's-complement representation of this
     * object's value.
     * @param index Zero based index of the bit to test. 0 means the least
     * significant bit.
     * @return {@code true} if a bit is set in the two's-complement representation
     * of this object's value; otherwise, {@code false}.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public boolean testBit(int index) {
return this.getEi().GetSignedBit(index);
}

    /**
     * Returns a byte array of this object&#x27;s value.
     * @param littleEndian A Boolean object.
     * @return A byte array.
     * @deprecated Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public byte[] toByteArray(boolean littleEndian) {
      return this.toBytes(littleEndian);
 }

    /**
     * Returns a byte array of this object&#x27;s value. The byte array will take
     * the form of the number's two' s-complement representation, using the
     * fewest bytes necessary to represent its value unambiguously. If this
     * value is negative, the bits that appear "before" the most significant
     * bit of the number will be all ones.
     * @param littleEndian If true, the least significant bits will appear first.
     * @return A byte array. If this value is 0, returns a byte array with the
     * single element 0.
     */
  public byte[] toBytes(boolean littleEndian) {
      return this.getEi().ToBytes(littleEndian);
 }

    /**
     * Generates a string representing the value of this object, in the given
     * radix.
     * @param radix A radix from 2 through 36. For example, to generate a
     * hexadecimal (base-16) string, specify 16. To generate a decimal
     * (base-10) string, specify 10.
     * @return A string representing the value of this object. If this value is 0,
     * returns "0". If negative, the string will begin with a hyphen/minus
     * ("-"). Depending on the radix, the string will use the basic digits 0
     * to 9 (U + 0030 to U + 0039) and then the basic letters A to Z (U + 0041 to
     * U + 005A). For example, 0-9 in radix 10, and 0-9, then A-F in radix 16.
     * @throws IllegalArgumentException The parameter "index" is less than 0,
     * "endIndex" is less than 0, or either is greater than the string's
     * length, or "endIndex" is less than "index" ; or radix is less than 2
     * or greater than 36.
     */
  public String toRadixString(int radix) {
      return this.getEi().ToRadixString(radix);
 }

    /**
     * Converts this object to a text string in base 10.
     * @return A string representation of this object. If negative, the string will
     * begin with a minus sign ("-", U+002D). The string will use the basic
     * digits 0 to 9 (U + 0030 to U + 0039).
     */
  @Override public String toString() {
      return this.getEi().toString();
    }
  }
