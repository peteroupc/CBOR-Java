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
      byte[] b2 = obj.GetByteString();
      byte[] bytes = {
        b2[3], b2[2], b2[1], b2[0], b2[5], b2[4], b2[7],
        b2[6], b2[8], b2[9], b2[10], b2[11], b2[12], b2[13], b2[14], b2[15],
       };
      return java.util.UUID.fromString(bytes);
    }
  }
