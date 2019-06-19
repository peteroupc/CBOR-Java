package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

  class CBORUriConverter implements ICBORToFromConverter<java.net.URI>
  {
    private CBORObject ValidateObject(CBORObject obj) {
      if (obj.getType() != CBORType.TextString) {
        throw new CBORException("URI/IRI must be a text String");
      }
      boolean isiri = obj.HasMostOuterTag(266);
      boolean isiriref = obj.HasMostOuterTag(267);
      if (
  isiriref && !URIUtility.isValidIRI(
  obj.AsString(),
  URIUtility.ParseMode.IRIStrict)) {
  throw new CBORException("String is not a valid IRI Reference");
}
if (
  isiri && (!URIUtility.isValidIRI(
  obj.AsString(),
  URIUtility.ParseMode.IRIStrict) ||
   !URIUtility.hasScheme(obj.AsString()))) {
  throw new CBORException("String is not a valid IRI");
}
      if (!URIUtility.isValidIRI(
  obj.AsString(),
  URIUtility.ParseMode.URIStrict) ||
   !URIUtility.hasScheme(obj.AsString())) {
  throw new CBORException("String is not a valid URI");
}
      return obj;
    }

    public java.net.URI FromCBORObject(CBORObject obj) {
      if (obj.HasMostOuterTag(32) ||
obj.HasMostOuterTag(266) ||
obj.HasMostOuterTag(267)) {
        this.ValidateObject(obj);
        try {
         return new java.net.URI(obj.AsString());
        } catch (Exception ex) {
         throw new CBORException(ex.getMessage(), ex);
        }
      }
      throw new CBORException();
    }

    public CBORObject ToCBORObject(java.net.URI uri) {
      if (uri == null) {
        throw new NullPointerException("uri");
      }
      String uriString = uri.toString();
      boolean nonascii = false;
      for (int i = 0; i < uriString.length(); ++i) {
        if (uriString.charAt(i) >= 0x80) {
 nonascii = true;
}
      }
      int tag = nonascii ? 266 : 32;
      if (!URIUtility.hasScheme(uriString)) {
 tag = 267;
}
      return CBORObject.FromObjectAndTag(uriString, (int)tag);
    }
  }
