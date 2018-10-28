package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

    /**
     * Not documented yet.
     * @param <T> Type parameter not documented yet.
     */
  public interface ICBORToFromConverter<T> extends ICBORConverter<T>
  {
    /**
     * Not documented yet.
     * @param obj Not documented yet.
     * @return Not documented yet.
     */
    T FromCBORObject(CBORObject obj);
  }
