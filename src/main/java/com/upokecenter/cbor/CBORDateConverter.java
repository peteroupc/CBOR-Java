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

  // <summary>A class for converting date-time objects to and from tagged CBOR
  // objects.</summary>

  /**
   * Not documented yet.
   */
  public final class CBORDateConverter implements ICBORToFromConverter<java.util.Date> {
    private final ConversionType convType;

  /**
   * Not documented yet.
   */
    public static final CBORDateConverter TaggedString =
       new CBORDateConverter(ConversionType.TaggedString);

  /**
   * Not documented yet.
   */
    public static final CBORDateConverter TaggedNumber =
       new CBORDateConverter(ConversionType.TaggedNumber);

  /**
   * Not documented yet.
   */
    public static final CBORDateConverter UntaggedNumber =
       new CBORDateConverter(ConversionType.UntaggedNumber);

  /**
   * Not documented yet.
   */
    public enum ConversionType {
       // <summary>FromCBORObject accepts objects with tag 0 (date/time strings)
       // and tag 1 (date/time numbers), and ToCBORObject converts date/time objects
       // to CBOR
       // objects of tag 0 (date/time strings).</summary>

  /**
   * Not documented yet.
   */
       TaggedString,
       // <summary>FromCBORObject accepts objects with tag 0 (date/time strings)
       // and tag 1 (date/time numbers), and ToCBORObject converts date/time objects
       // to CBOR
       // objects of tag 1 (date/time numbers).</summary>

  /**
   * Not documented yet.
   */
       TaggedNumber,

  /**
   * Not documented yet.
   */
       UntaggedNumber,
    }

  /**
   * Initializes a new instance of the CBORDateConverter class.
   */
    public CBORDateConverter() {
 this(ConversionType.TaggedString);}

  /**
   * Initializes a new instance of the CBORDateConverter class.
   * @param convType
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
   * @param obj Not documented yet.
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

  /**
   * Not documented yet.
   * @param str Not documented yet.
   * @return The return value is not documented yet.
   */
    public static java.util.Date StringToDateTime(String str) {
      int[] lesserFields = new int[7];
      EInteger[] year = new EInteger[1];
      CBORUtilities.ParseAtomDateTimeString(str, year, lesserFields);
      return PropertyMap.BuildUpDateTime(year[0], lesserFields);
    }

  /**
   * Not documented yet.
   * @param obj Not documented yet.
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
