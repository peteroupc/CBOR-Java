package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

  public enum CBORType
  {
    /**
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

    Boolean,

    SimpleValue,

    ByteString,

    TextString,

    Array,

    Map,

    Integer,

    FloatingPoint,
  }
