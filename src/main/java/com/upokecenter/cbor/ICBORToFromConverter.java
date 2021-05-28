package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

  /**
   * Classes that implement this interface can support conversions from CBOR
   * objects to a custom type and back.
   * @param <T> Type of objects to convert to and from CBOR objects.
   */
  public interface ICBORToFromConverter<T> extends ICBORConverter<T> {
    /**
     * Converts a CBOR object to a custom type.
     * @param obj A CBOR object to convert to the custom type.
     * @return An object of the custom type after conversion.
     */
    T FromCBORObject(CBORObject obj);
  }
