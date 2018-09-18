package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.numbers.*;

  class CBORTag1 implements ICBORTag, ICBORConverter<java.util.Date>
  {
    public CBORTypeFilter GetTypeFilter() {
      return
      CBORTypeFilter.UnsignedInteger.WithNegativeInteger().WithFloatingPoint();
    }

    public CBORObject ValidateObject(CBORObject obj) {
      if (!obj.isFinite()) {
        throw new CBORException("Not a valid date");
      }
      return obj;
    }

    public CBORObject ToCBORObject(java.util.Date obj) {
       // TODO
       throw new UnsupportedOperationException();
    }
  }
