package com.upokecenter.cbor;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

  /**
   * Represents a type that a CBOR object can have.
   */
  public enum CBORType {
    /**
     * A number of any kind, including integers, big integers, floating point
     * numbers, and decimal numbers. The floating-point value Not-a-Number
     * is also included in the Number type.
     * @deprecated Use the IsNumber property of CBORObject to determine whether a CBOR Object
 * represents a number, or use the two new CBORType values instead.
 * CBORType.Integer covers CBOR objects representing integers of major type
 * 0 and 1. CBORType.FloatingPoint covers CBOR objects representing 16-,
 * 32-, and 64-bit floating-point numbers.
 */
@Deprecated
    Number,

    /**
     * The simple values true and false.
     */
    Boolean,

    /**
     * A "simple value" other than floating point values, true, and false.
     */
    SimpleValue,

    /**
     * An array of bytes.
     */
    ByteString,

    /**
     * A text string.
     */
    TextString,

    /**
     * An array of CBOR objects.
     */
    Array,

    /**
     * A map of CBOR objects.
     */
    Map,

    /**
     * An integer in the interval [-(2^64), 2^64 - 1], or an integer of major type
     * 0 and 1.
     */
    Integer,

    /**
     * A 16-, 32-, or 64-bit binary floating-point number.
     */
    FloatingPoint,
  }
