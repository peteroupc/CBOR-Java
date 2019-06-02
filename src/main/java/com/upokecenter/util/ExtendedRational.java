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
     * <code>PeterO.Numbers.ERational </code> in the <a
     * href='https://www.nuget.org/packages/PeterO.Numbers'>
     * <code>PeterO.Numbers </code> </a> library (in .NET), or
     * <code>com.upokecenter.numbers.ERational </code> in the <a
     * href='https://github.com/peteroupc/numbers-java'>
     * <code>com.github.peteroupc/numbers </code> </a> artifact (in Java). This
     * new class can be used in the <code>CBORObject.FromObject(object) </code>
     * method (by including the new library in your code, among other
     * things). </b> </p> Arbitrary-precision rational number. This class
     * can't be inherited; this is a change in version 2.0 from previous
     * versions, where the class was inadvertently left inheritable.
     * <p><b>Thread safety: </b> Instances of this class are immutable, so
     * they are inherently safe for use by multiple threads. Multiple
     * instances of this object with the same properties are
     * interchangeable, so they should not be compared using the "=="
     * operator (which might only check if each side of the operator is the
     * same instance). </p>
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers and the output of
 *this class's toString method.
 */
@Deprecated
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
     * @return {@code true} If this object is finite (not infinity or NaN);
     * otherwise, . {@code false} .
     * @deprecated Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
    public final boolean isFinite() {
        return this.getEr().isFinite();
      }

    /**
     * Gets a value indicating whether this object's value is negative (including
     * negative zero).
     * @return {@code true} If this object's value is negative; otherwise, . {@code
     * false} .
     */
    public final boolean isNegative() {
        return this.getEr().isNegative();
      }

    /**
     * Gets a value indicating whether this object's value equals 0.
     * @return {@code true} If this object's value equals 0; otherwise, . {@code
     * false} .
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
     * @param numeratorSmall The parameter {@code numeratorSmall} is a 32-bit
     * signed integer.
     * @param denominatorSmall The parameter {@code denominatorSmall} is a 32-bit
     * signed integer.
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

    /**
     * Compares this value to another.
     * @param other The parameter {@code other} is an ExtendedRational object.
     * @return Less than 0 if this value is less than, 0 if equal to, or greater
     * than 0 if greater than the other value.
     */
    public int compareTo(ExtendedRational other) {
      return this.getEr().compareTo(other == null ? null : other.getEr());
    }

    /**
     * Checks whether this and another value are equal.
     * @param other The parameter {@code other} is an ExtendedRational object.
     * @return Either {@code true} or {@code false} .
     */
    public boolean equals(ExtendedRational other) {
      return this.getEr().equals(other == null ? null : other.getEr());
    }

    /**
     * Checks whether this and another value are equal.
     * @param obj The parameter {@code obj} is an arbitrary object.
     * @return Either {@code true} or {@code false} .
     */
    @Override public boolean equals(Object obj) {
      ExtendedRational other = ((obj instanceof ExtendedRational) ? (ExtendedRational)obj : null);
      return this.getEr().equals(other == null ? null : other.getEr());
    }

    /**
     * Calculates the hash code for this object. No application or process IDs are
     * used in the hash code calculation.
     * @return A 32-bit signed integer.
     */
    @Override public int hashCode() {
      return this.getEr().hashCode();
    }
  }
