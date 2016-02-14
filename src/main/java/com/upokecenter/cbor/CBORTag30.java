package com.upokecenter.cbor;
/*
Written in 2014 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import com.upokecenter.util.*; import com.upokecenter.numbers.*;

  class CBORTag30 implements ICBORTag
  {
    public CBORTypeFilter GetTypeFilter() {
      return new CBORTypeFilter().WithArrayExactLength(
        2,
        CBORTypeFilter.UnsignedInteger.WithNegativeInteger().WithTags(2, 3),
        CBORTypeFilter.UnsignedInteger.WithTags(2));
    }

    public CBORObject ValidateObject(CBORObject obj) {
      if (obj.getType() != CBORType.Array) {
        throw new CBORException("Rational number must be an array");
      }
      if (obj.size() != 2) {
        throw new CBORException("Rational number requires exactly 2 items");
      }
      CBORObject first = obj.get(0);
      CBORObject second = obj.get(1);
      if (!first.isIntegral()) {
        throw new CBORException("Rational number requires integer numerator");
      }
      if (!second.isIntegral()) {
        throw new CBORException("Rational number requires integer denominator");
      }
      if (second.signum() <= 0) {
throw new CBORException("Rational number requires denominator greater than 0");
      }
      EInteger denom = second.AsEInteger();
      // NOTE: Discards tags. See comment in CBORTag2.
      return denom.equals(EInteger.FromInt32(1)) ?
      CBORObject.FromObject(first.AsEInteger()) :
      CBORObject.FromObject(
new ERational(
first.AsEInteger(),
denom));
    }
  }
