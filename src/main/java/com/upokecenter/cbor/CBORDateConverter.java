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

  /**
   * <p>A class for converting date-time objects to and from tagged CBOR
   *  objects.</p> <p>In this method's documentation, the "number of seconds
   *  since the start of 1970" is based on the POSIX definition of "seconds
   *  since the Epoch", a definition that does not count leap seconds. This
   * number of seconds assumes the use of a proleptic Gregorian calendar,
   * in which the rules regarding the number of days in each month and
   * which years are leap years are the same for all years as they were in
   * 1970 (including without regard to transitions from other calendars to
   * the Gregorian).</p>
   */
  public final class CBORDateConverter implements ICBORToFromConverter<java.util.Date> {
    private final ConversionType convType;

       /**
        * A converter object where FromCBORObject accepts CBOR objects with tag 0
        * (date/time strings) and tag 1 (number of seconds since the start
        * of 1970), and ToCBORObject converts date/time objects (java.util.Date
        * in DotNet, and Date in Java) to CBOR objects of tag 0.
        */
    public static final CBORDateConverter TaggedString =
       new CBORDateConverter(ConversionType.TaggedString);

       /**
        * A converter object where FromCBORObject accepts CBOR objects with tag 0
        * (date/time strings) and tag 1 (number of seconds since the start
        * of 1970), and ToCBORObject converts date/time objects (java.util.Date
        * in DotNet, and Date in Java) to CBOR objects of tag 1. The
        * ToCBORObject conversion is lossless only if the number of seconds
        * since the start of 1970 can be represented exactly as an integer
        * in the interval [-(2^64), 2^64 - 1] or as a 64-bit floating-point
        * number in the IEEE 754r binary64 format; the conversion is lossy
        * otherwise. The ToCBORObject conversion will throw an exception if
        * the conversion to binary64 results in positive infinity, negative
        * infinity, or not-a-number.
        */
    public static final CBORDateConverter TaggedNumber =
       new CBORDateConverter(ConversionType.TaggedNumber);

       /**
        * A converter object where FromCBORObject accepts untagged CBOR integer or
        * CBOR floating-point objects that give the number of seconds since
        * the start of 1970, and where ToCBORObject converts date/time
        * objects (java.util.Date in DotNet, and Date in Java) to such untagged
        * CBOR objects. The ToCBORObject conversion is lossless only if the
        * number of seconds since the start of 1970 can be represented
        * exactly as an integer in the interval [-(2^64), 2^64 - 1] or as a
        * 64-bit floating-point number in the IEEE 754r binary64 format;
        * the conversion is lossy otherwise. The ToCBORObject conversion
        * will throw an exception if the conversion to binary64 results in
        * positive infinity, negative infinity, or not-a-number.
        */
    public static final CBORDateConverter UntaggedNumber =
       new CBORDateConverter(ConversionType.UntaggedNumber);

  /**
   * Conversion type for date-time conversion.
   */
    public enum ConversionType {
       /**
        * FromCBORObject accepts CBOR objects with tag 0 (date/time strings) and tag 1
        * (number of seconds since the start of 1970), and ToCBORObject
        * converts date/time objects to CBOR objects of tag 0.
        */
       TaggedString,

       /**
        * FromCBORObject accepts objects with tag 0 (date/time strings) and tag 1
        * (number of seconds since the start of 1970), and ToCBORObject
        * converts date/time objects to CBOR objects of tag 1. The
        * ToCBORObject conversion is lossless only if the number of seconds
        * since the start of 1970 can be represented exactly as an integer
        * in the interval [-(2^64), 2^64 - 1] or as a 64-bit floating-point
        * number in the IEEE 754r binary64 format; the conversion is lossy
        * otherwise. The ToCBORObject conversion will throw an exception if
        * the conversion to binary64 results in positive infinity, negative
        * infinity, or not-a-number.
        */
       TaggedNumber,

       /**
        * FromCBORObject accepts untagged CBOR integer or CBOR floating-point objects
        * that give the number of seconds since the start of 1970, and
        * ToCBORObject converts date/time objects (java.util.Date in DotNet, and
        * Date in Java) to such untagged CBOR objects. The ToCBORObject
        * conversion is lossless only if the number of seconds since the
        * start of 1970 can be represented exactly as an integer in the
        * interval [-(2^64), 2^64 - 1] or as a 64-bit floating-point number
        * in the IEEE 754r binary64 format; the conversion is lossy
        * otherwise. The ToCBORObject conversion will throw an exception if
        * the conversion to binary64 results in positive infinity, negative
        * infinity, or not-a-number.
        */
       UntaggedNumber,
    }

  /**
   * Initializes a new instance of the {@link CBORDateConverter} class.
   */
    public CBORDateConverter() {
 this(ConversionType.TaggedString);
}

  /**
   * Initializes a new instance of the {@link CBORDateConverter} class.
   * @param convType The parameter {@code convType} is a
   * Cbor.CBORDateConverter.ConversionType object.
   */
    public CBORDateConverter(ConversionType convType) {
       this.convType = convType;
    }

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

  /**
   * Not documented yet.
   * @param obj The parameter {@code obj} is a Cbor.CBORObject object.
   * @return The return value is not documented yet.
   * @throws NullPointerException The parameter {@code obj} is null.
   */
    public java.util.Date FromCBORObject(CBORObject obj) {
      if (this.convType == ConversionType.UntaggedNumber) {
        if (obj == null) {
          throw new NullPointerException("obj");
        }
        if (obj.isTagged()) {
         throw new CBORException("May not be tagged");
      }
      CBORObject untagobj = obj;
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
      if (obj == null) {
        throw new NullPointerException("obj");
      }
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

    private static java.util.Date StringToDateTime(String str) {
      int[] lesserFields = new int[7];
      EInteger[] year = new EInteger[1];
      CBORUtilities.ParseAtomDateTimeString(str, year, lesserFields);
      return PropertyMap.BuildUpDateTime(year[0], lesserFields);
    }

  /**
   * Not documented yet.
   * @param obj The parameter {@code obj} is a java.util.Date object.
   * @return The return value is not documented yet.
   */
    public CBORObject ToCBORObject(java.util.Date obj) {
      switch (this.convType) {
        case TaggedString:
           return CBORObject.FromObjectAndTag(DateTimeToString(obj), 0);
        case TaggedNumber:
        case UntaggedNumber:
        try {
           int[] lesserFields = new int[7];
           EInteger[] year = new EInteger[1];
           PropertyMap.BreakDownDateTime(obj, year, lesserFields);
         int[] status = new int[1];
         EFloat ef = CBORUtilities.DateTimeToIntegerOrDouble(
           year[0],
           lesserFields,
           status);
         if (status[0] == 0) {
          return this.convType == ConversionType.TaggedNumber ?
             CBORObject.FromObjectAndTag(ef.ToEInteger(), 1) :
             CBORObject.FromObject(ef.ToEInteger());
        } else if (status[0] == 1) {
          return this.convType == ConversionType.TaggedNumber ?
             CBORObject.FromObjectAndTag(ef.ToDouble(), 1) :
             CBORObject.FromObject(ef.ToDouble());
        } else {
          throw new CBORException("Too big or small to fit an integer or" +
"\u0020floating-point number");
        }
         } catch (IllegalArgumentException ex) {
          throw new CBORException(ex.getMessage(), ex);
      }
      default: throw new CBORException("Internal error");
      }
    }
  }
