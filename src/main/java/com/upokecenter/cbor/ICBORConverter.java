package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

  /**
   * Interface implemented by classes that convert objects of arbitrary types to
   * CBOR objects.
   * @param <T> Type to convert to a CBOR object.
   */
  public interface ICBORConverter<T>
  {
    /**
     * Converts an object to a CBOR object.
     * @param obj An object to convert to a CBOR object.
     * @return A CBOR object.
     */
    CBORObject ToCBORObject(T obj);
  }
