package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

   /**
    * Classes that implement this interface can support conversions from CBOR
    * objects to a custom type and back.
    * @param <T> Type of objects to convert to and from CBOR objects.
    */
  public interface ICBORToFromConverter<T> extends ICBORConverter<T>
  {
   /**
    * Converts a CBOR object to a custom type.
    * @param obj A CBOR object to convert to the custom type.
    * @return An object of the custom type after conversion.
    */
    T FromCBORObject(CBORObject obj);
  }
