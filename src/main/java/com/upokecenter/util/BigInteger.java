package com.upokecenter.util;
/*
Written in 2013 by Peter O.

Parts of the code were adapted by Peter O. from
the public-domain code from the library
CryptoPP by Wei Dai.

Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
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
     * <code>CBORObject.FromObject(object)</code> method (by including the new
     * library in your code, among other things), but this version of the
     * CBOR library doesn't include any methods that explicitly take an
     * <code>EInteger</code> as a parameter or return value.</b></p> An
     * arbitrary-precision integer. <p><b>Thread safety:</b> Instances of
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

  private static final BigInteger ValueZeroValue = new
      BigInteger(EInteger.FromInt32(0));

    final EInteger getEi() {
        return this.ei;
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
     * Converts a 64-bit signed integer to a big integer.
     * @param longerValue A 64-bit signed integer.
     * @return An arbitrary-precision integer with the same value as the 64-bit
     * number.
     */
  public static BigInteger valueOf(long longerValue) {
      return new BigInteger(EInteger.FromInt64(longerValue));
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
     * Determines whether this object and another object are equal.
     * @param obj An arbitrary object.
     * @return true if this object and another object are equal; otherwise, false.
     */
  @Override public boolean equals(Object obj) {
      BigInteger bi = ((obj instanceof BigInteger) ? (BigInteger)obj : null);
      return (bi == null) ? false : this.getEi().equals(bi.getEi());
}

    /**
     * Returns the hash code for this instance.
     * @return A 32-bit signed integer.
     */
  @Override public int hashCode() {
      return this.getEi().hashCode();
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

    /**
     * Not documented yet.
     * @param other A BigInteger object.
     * @return A 32-bit signed integer.
     */
    public int compareTo(BigInteger other) {
      return this.getEi().compareTo(other == null ? null : other.getEi());
    }
  }
