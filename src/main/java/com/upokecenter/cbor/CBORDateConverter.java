package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.numbers.*;

  class CBORDateConverter implements ICBORToFromConverter<java.util.Date> {
    private static String DateTimeToString(java.util.Date bi) {
      try {
        int[] lesserFields = new int[7];
        EInteger[] year = new EInteger[1];
        PropertyMap.BreakDownDateTime(bi, year, lesserFields);
        return CBORUtilities.ToAtomDateTimeString(year[0], lesserFields);
      } catch (IllegalArgumentException ex) {
          throw new CBORException(ex.getMessage(), ex);
      }
    }

    public java.util.Date FromCBORObject(CBORObject obj) {
      if (obj.HasMostOuterTag(0)) {
        try {
          return StringToDateTime(obj.AsString());
        } catch (ArithmeticException ex) {
          throw new CBORException(ex.getMessage(), ex);
        } catch (IllegalStateException ex) {
          throw new CBORException(ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
          throw new CBORException(ex.getMessage(), ex);
        }
      } else if (obj.HasMostOuterTag(1)) {
        CBORObject untagobj = obj.UntagOne();
        if (!untagobj.isNumber()) {
          throw new CBORException("Not a finite number");
        }
        CBORNumber num = untagobj.AsNumber();
        if (!num.IsFinite()) {
          throw new CBORException("Not a finite number");
        }
        if (num.compareTo(Long.MIN_VALUE) < 0 ||
            num.compareTo(Long.MAX_VALUE) > 0) {
          throw new CBORException("Too big or small to fit a java.util.Date");
        }
        EDecimal dec;
        dec = (EDecimal)untagobj.ToObject(EDecimal.class);
        int[] lesserFields = new int[7];
        EInteger[] year = new EInteger[1];
        CBORUtilities.BreakDownSecondsSinceEpoch(
          dec,
          year,
          lesserFields);
        return PropertyMap.BuildUpDateTime(year[0], lesserFields);
      }
      throw new CBORException("Not tag 0 or 1");
    }

    public static java.util.Date StringToDateTime(String str) {
      int[] lesserFields = new int[7];
      EInteger[] year = new EInteger[1];
      CBORUtilities.ParseAtomDateTimeString(str, year, lesserFields);
      return PropertyMap.BuildUpDateTime(year[0], lesserFields);
    }

    public CBORObject ToCBORObject(java.util.Date obj) {
      return CBORObject.FromObjectAndTag(DateTimeToString(obj), 0);
    }
  }
