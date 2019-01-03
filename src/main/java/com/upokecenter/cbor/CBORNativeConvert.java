package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  final class CBORNativeConvert {
private CBORNativeConvert() {
}
    private static CBORObject FromObjectAndInnerTags(
  Object objectValue,
  CBORObject objectWithTags) {
      CBORObject newObject = CBORObject.FromObject(objectValue);
      if (!objectWithTags.isTagged()) {
        return newObject;
      }
      objectWithTags = objectWithTags.UntagOne();
      if (!objectWithTags.isTagged()) {
        return newObject;
      }
      EInteger[] tags = objectWithTags.GetAllTags();
      for (int i = tags.length - 1; i >= 0; --i) {
        newObject = CBORObject.FromObjectAndTag(newObject, tags[i]);
      }
      return newObject;
    }

    public static CBORObject ConvertToNativeObject(CBORObject o) {
     if (o.HasMostOuterTag(2)) {
 return ConvertToBigNum(o, false);
}
     if (o.HasMostOuterTag(3)) {
 return ConvertToBigNum(o, true);
}
     if (o.HasMostOuterTag(4)) {
 return ConvertToDecimalFrac(o, true, false);
}
     if (o.HasMostOuterTag(5)) {
 return ConvertToDecimalFrac(o, false, false);
}
     if (o.HasMostOuterTag(30)) {
 return ConvertToRationalNumber(o);
}
     if (o.HasMostOuterTag(264)) {
 return ConvertToDecimalFrac(o, true, true);
}
  return o.HasMostOuterTag(265) ? ConvertToDecimalFrac(o, false, true) :
       o;
    }

    private static CBORObject ConvertToDecimalFrac(
      CBORObject o,
      boolean isDecimal,
      boolean extended) {
      if (o.getType() != CBORType.Array) {
        throw new CBORException("Big fraction must be an array");
      }
      if (o.size() != 2) {
        throw new CBORException("Big fraction requires exactly 2 items");
      }
      if (!o.get(0).isIntegral()) {
        throw new CBORException("Exponent is not an integer");
      }
      if (!o.get(1).isIntegral()) {
        throw new CBORException("Mantissa is not an integer");
      }
      EInteger exponent = o.get(0).AsEInteger();
      EInteger mantissa = o.get(1).AsEInteger();
      if (exponent.GetSignedBitLength() > 64 && !extended) {
        throw new CBORException("Exponent is too big");
      }
      if (exponent.isZero()) {
        // Exponent is 0, so return mantissa instead
        return CBORObject.FromObject(mantissa);
      }
      // NOTE: Discards tags. See comment in CBORTag2.
      return isDecimal ?
      CBORObject.FromObject(EDecimal.Create(mantissa, exponent)) :
      CBORObject.FromObject(EFloat.Create(mantissa, exponent));
    }

    private static CBORObject ConvertToBigNum(CBORObject o, boolean negative) {
      if (o.getType() != CBORType.ByteString) {
        throw new CBORException("Byte array expected");
      }
      byte[] data = o.GetByteString();
      if (data.length <= 7) {
        long x = 0;
        for (int i = 0; i < data.length; ++i) {
          x <<= 8;
          x |= ((long)data[i]) & 0xff;
        }
        if (negative) {
          x = -x;
          --x;
        }
        return FromObjectAndInnerTags(x, o);
      }
      int neededLength = data.length;
      byte[] bytes;
      EInteger bi;
      boolean extended = false;
      if (((data[0] >> 7) & 1) != 0) {
        // Increase the needed length
        // if the highest bit is set, to
        // distinguish negative and positive
        // values
        ++neededLength;
        extended = true;
      }
      bytes = new byte[neededLength];
      for (int i = 0; i < data.length; ++i) {
        bytes[i] = data[data.length - 1 - i];
        if (negative) {
          bytes[i] = (byte)((~((int)bytes[i])) & 0xff);
        }
      }
      if (extended) {
          bytes[bytes.length - 1] = negative ? (byte)0xff : (byte)0;
      }
      bi = EInteger.FromBytes(bytes, true);
      // NOTE: Here, any tags are discarded; when called from
      // the Read method, "o" will have no tags anyway (beyond tag 2),
      // and when called from FromObjectAndTag, we prefer
      // flexibility over throwing an error if the input
      // Object contains other tags. The tag 2 is also discarded
      // because we are returning a "natively" supported CBOR Object.
      return CBORObject.FromObject(bi);
    }

    private static CBORObject ConvertToRationalNumber(CBORObject obj) {
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
      // NOTE: Discards tags.
      return denom.equals(EInteger.FromInt32(1)) ?
      CBORObject.FromObject(first.AsEInteger()) :
      CBORObject.FromObject(
  ERational.Create(
  first.AsEInteger(),
  denom));
    }
  }
