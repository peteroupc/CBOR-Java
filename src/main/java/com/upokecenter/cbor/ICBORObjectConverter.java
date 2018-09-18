package com.upokecenter.cbor;

    /**
     * Interface implemented by classes that convert objects of arbitrary types to
     * and from CBOR objects.
     * @param <T> Type of objects that a class implementing this method can convert
     * to and from CBOR objects.
     */
  public interface ICBORObjectConverter<T> extends ICBORConverter<T> {
    /**
     * Converts a CBOR object to an object of a type supported by the implementing
     * class.
     * @param cbor A CBOR object to convert.
     * @return The converted object.
     * @throws com.upokecenter.cbor.CBORException An error occurred in the
     * conversion; for example, the conversion doesn't support the given
     * CBOR object.
     */
    T FromCBORObject(CBORObject cbor);
  }
