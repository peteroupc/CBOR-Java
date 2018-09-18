package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

  class CBORTag37 implements ICBORTag, ICBORObjectConverter<java.util.UUID>
  {
    public CBORTypeFilter GetTypeFilter() {
      return CBORTypeFilter.ByteString;
    }

    public CBORObject ValidateObject(CBORObject obj) {
      if (obj.getType() != CBORType.ByteString) {
        throw new CBORException("UUID must be a byte String");
      }
      byte[] bytes = obj.GetByteString();
      if (bytes.length != 16) {
        throw new CBORException("UUID must be 16 bytes long");
      }
      return obj;
    }

    static void AddConverter() {
        CBORObject.AddConverter(java.util.UUID.class, new CBORTag37());
    }

    /**
     * Converts a UUID to a CBOR object.
     * @param obj A UUID.
     * @return A CBORObject object.
     */
    public CBORObject ToCBORObject(java.util.UUID obj) {
      byte[] bytes = PropertyMap.UUIDToBytes(obj);
      return CBORObject.FromObjectAndTag(bytes, (int)37);
    }

    public java.util.UUID FromCBORObject(CBORObject obj) {
      if (!obj.HasMostOuterTag(37)) {
        throw new CBORException("Must have outermost tag 37");
      }
      this.ValidateObject(obj);
      byte[] bytes = obj.GetByteString();
      char[] guidChars = new char[36];
      String hex = "0123456789abcdef";
      int index = 0;
      for (int i = 0; i < 16; ++i) {
       if (i == 4 || i == 6 || i == 8 || i == 10) {
         guidChars[index++] = '-';
       }
       guidChars[index++] = hex.charAt((int)(bytes[i] >> 4) & 15);
       guidChars[index++] = hex.charAt((int)bytes[i] & 15);
      }
      String guidString = new String(guidChars);
      return java.util.UUID.fromString(guidString);
    }
  }
