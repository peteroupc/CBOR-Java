package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

  class CBORUuidConverter implements ICBORToFromConverter<java.util.UUID>
  {
    private static CBORObject ValidateObject(CBORObject obj) {
      if (obj.getType() != CBORType.ByteString) {
        throw new CBORException("UUID must be a byte String");
      }
      byte[] bytes = obj.GetByteString();
      if (bytes.length != 16) {
 throw new CBORException("UUID must be 16" +
"\u0020bytes long");
}
 return obj;
    }

    /**
     * Internal API.
     * @param obj The parameter {@code obj} is an internal parameter.
     * @return A CBORObject object.
     */
    public CBORObject ToCBORObject(java.util.UUID obj) {
      byte[] bytes = PropertyMap.UUIDToBytes(obj);
      return CBORObject.FromByteArray(bytes).WithTag(37);
    }

    public java.util.UUID FromCBORObject(CBORObject obj) {
      if (!obj.HasMostOuterTag(37)) {
        throw new CBORException("Must have outermost tag 37");
      }
      ValidateObject(obj);
      byte[] bytes = obj.GetByteString();
      char[] guidChars = new char[36];
      String hex = "0123456789abcdef";
      int index = 0;
      for (int i = 0; i < 16; ++i) {
        if (i == 4 || i == 6 || i == 8 || i == 10) {
          guidChars[index++] = '-';
        }
        guidChars[index++] = hex.charAt((bytes[i] >> 4) & 15);
        guidChars[index++] = hex.charAt(bytes[i] & 15);
      }
      String guidString = new String(guidChars);
      // NOTE: Don't use the byte[] constructor of the DotNet java.util.UUID class,
      // since the bytes may have to be rearranged in order to generate a
      // java.util.UUID from a UUID; thus the exact java.util.UUID generated from a byte String
      // by that constructor may differ between little-endian and big-endian
      // computers, but I don't have access to a big-endian machine to test
      // this hypothesis.
      return java.util.UUID.fromString(guidString);
    }
  }
