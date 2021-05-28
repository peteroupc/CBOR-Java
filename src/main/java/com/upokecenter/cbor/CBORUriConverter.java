package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
http://creativecommons.org/publicdomain/zero/1.0/

 */

  class CBORUriConverter implements ICBORToFromConverter<java.net.URI> {
    private static CBORObject ValidateObject(CBORObject obj) {
      if (obj.getType() != CBORType.TextString) {
        throw new CBORException("URI/IRI must be a text String");
      }
      boolean isiri = obj.HasMostOuterTag(266);
      boolean isiriref = obj.HasMostOuterTag(267);
      if (
        isiriref && !URIUtility.IsValidIRI(
          obj.AsString(),
          URIUtility.ParseMode.IRIStrict)) {
        throw new CBORException("String is not a valid IRI Reference");
      }
      if (
        isiri && (!URIUtility.IsValidIRI(
            obj.AsString(),
            URIUtility.ParseMode.IRIStrict) ||
          !URIUtility.HasScheme(obj.AsString()))) {
        throw new CBORException("String is not a valid IRI");
      }
      if (!URIUtility.IsValidIRI(
          obj.AsString(),
          URIUtility.ParseMode.URIStrict) ||
        !URIUtility.HasScheme(obj.AsString())) {
        throw new CBORException("String is not a valid URI");
      }
      return obj;
    }

    public java.net.URI FromCBORObject(CBORObject obj) {
      if (obj.HasMostOuterTag(32) ||
             obj.HasMostOuterTag(266) ||
             obj.HasMostOuterTag(267)) {
        ValidateObject(obj);
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
        nonascii |= uriString.charAt(i) >= 0x80;
      }
      int tag = nonascii ? 266 : 32;
      if (!URIUtility.HasScheme(uriString)) {
        tag = 267;
      }
      return CBORObject.FromObjectAndTag(uriString, (int)tag);
    }
  }
