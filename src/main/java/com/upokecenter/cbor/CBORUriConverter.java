package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

  class CBORUriConverter implements ICBORObjectConverter<java.net.URI>
  {
    private CBORObject ValidateObject(CBORObject obj) {
      if (obj.getType() != CBORType.TextString) {
        throw new CBORException("URI must be a text String");
      }
      if (!URIUtility.isValidIRI(obj.AsString())) {
        throw new CBORException("String is not a valid URI/IRI");
      }
      return obj;
    }

    public java.net.URI FromCBORObject(CBORObject obj) {
      if (obj.HasMostOuterTag(32)) {
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
      return CBORObject.FromObjectAndTag(uriString, (int)32);
    }
  }
