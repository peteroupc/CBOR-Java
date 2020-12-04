package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

  /**
   * Represents a type that a CBOR object can have.
   */
  public enum CBORType {
    /**
     * This property is no longer used.
     * @deprecated Since version 4.0, CBORObject.Type no longer returns this\u0020value for any
 * CBOR Object - this is a breaking change from earlier
 * versions.\u0020Instead, use the IsNumber property of CBORObject to
 * determine whether a CBOR Object represents a number, or use the two new
 * CBORType values instead. CBORType.Integer covers CBOR objects
 * representing\u0020integers of\u0020major type 0 and 1.
 * CBORType.FloatingPoint covers CBOR objects representing 16-, 32-, and
 * 64-bit floating-point numbers. CBORType.Number may be removed in version
 * 5.0 or later.
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
