package com.upokecenter.cbor;

    /**
     * Not documented yet.
     * @param <T> Type parameter not documented yet.
     */
  public interface ICBORObjectConverter<T> extends ICBORConverter<T> {
    /**
     * Not documented yet.
     * @param cbor The parameter {@code cbor} is not documented yet.
     * @return The return value is not documented yet.
     */
    T FromCBORObject(CBORObject cbor);
  }
