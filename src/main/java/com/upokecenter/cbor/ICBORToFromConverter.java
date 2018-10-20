package com.upokecenter.cbor;

    /**
     * Not documented yet.
     * @param <T> Type parameter not documented yet.
     */
  public interface ICBORToFromConverter<T> extends ICBORConverter<T> {
    /**
     * Not documented yet.
     * @param cbor Not documented yet.
     * @return Not documented yet.
     */
    T FromCBORObject(CBORObject cbor);
  }
