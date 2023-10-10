package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import com.upokecenter.numbers.*;

  public final class CBORDateConverter implements ICBORToFromConverter<java.util.Date>
  {
    public static final CBORDateConverter TaggedString =
      new CBORDateConverter(ConversionType.TaggedString);

    public static final CBORDateConverter TaggedNumber =
      new CBORDateConverter(ConversionType.TaggedNumber);

    public static final CBORDateConverter UntaggedNumber =
      new CBORDateConverter(ConversionType.UntaggedNumber);

    public enum ConversionType
    {
      TaggedString,

      TaggedNumber,

      UntaggedNumber,
    }

    public CBORDateConverter() {
 this(ConversionType.TaggedString);
    }

    public final ConversionType getType() { return propVartype; }
private final ConversionType propVartype;

    public CBORDateConverter(ConversionType convType) {
      this.propVartype = convType;
    }

    private static String DateTimeToString(java.util.Date bi) {
      try {
        int[] lesserFields = new int[7];
        EInteger[] outYear = new EInteger[1];
        PropertyMap.BreakDownDateTime(bi, outYear, lesserFields);
        return CBORUtilities.ToAtomDateTimeString(outYear[0], lesserFields);
      } catch (IllegalArgumentException ex) {
        throw new CBORException(ex.getMessage(), ex);
      }
    }

    public java.util.Date FromCBORObject(CBORObject obj) {
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      int[] lesserFields = new int[7];
      EInteger[] outYear = new EInteger[1];
      String str = this.TryGetDateTimeFieldsInternal(
          obj,
          outYear,
          lesserFields);
      if (!(str == null)) {
        throw new CBORException(str);
      }
 return PropertyMap.BuildUpDateTime(outYear[0],
  lesserFields);
    }

    public boolean TryGetDateTimeFields(CBORObject obj, EInteger[] year, int[]
      lesserFields) {
      if (year == null) {
        return false;
      }
      EInteger[] outYear = year;
      if (outYear.length < 1) {
        return false;
      }
      if (lesserFields == null) {
        return false;
      }
      if (lesserFields.length < 7) {
        return false;
      }
      String str = this.TryGetDateTimeFieldsInternal(
          obj,
          outYear,
          lesserFields);
      if (str == null) {
        // No error String was returned
        return true;
      } else {
        // An error String was returned
        outYear[0] = null;
        for (int i = 0; i < 7; ++i) {
          lesserFields[i] = 0;
        }
        return false;
      }
    }

    private String TryGetDateTimeFieldsInternal(
      CBORObject obj,
      EInteger[] year,
      int[] lesserFields) {
      if (obj == null) {
        return "Object is null";
      }
      if (year == null) {
        return "Year is null";
      }
      EInteger[] outYear = year;
      if (outYear.length < 1) {
        return "\"year\" + \"'s length\" (" +
          outYear.length + ") is not greater or equal to 1";
      }
      if (lesserFields == null) {
        return "Lesser fields is null";
      }
      if (lesserFields.length < 7) {
        return "\"lesserFields\" + \"'s length\" (" +
          lesserFields.length + ") is not greater or equal to 7";
      }
      ConversionType thisType = this.getType();
      if (thisType == ConversionType.UntaggedNumber) {
        if (obj.isTagged()) {
          return "May not be tagged";
        }
        CBORObject untagobj = obj;
        if (!untagobj.isNumber()) {
          return "Not a finite number";
        }
        CBORNumber num = untagobj.AsNumber();
        if (!num.IsFinite()) {
          return "Not a finite number";
        }
        if (num.compareTo(Long.MIN_VALUE) < 0 ||
          num.compareTo(Long.MAX_VALUE) > 0) {
          return "Too big or small to fit a java.util.Date";
        }
        if (num.CanFitInInt64()) {
          CBORUtilities.BreakDownSecondsSinceEpoch(
            num.ToInt64Checked(),
            outYear,
            lesserFields);
        } else {
          EDecimal dec;
          dec = (EDecimal)untagobj.ToObject(EDecimal.class);
          CBORUtilities.BreakDownSecondsSinceEpoch(
            dec,
            outYear,
            lesserFields);
        }
        return null; // no error
      }
      if (obj.HasMostOuterTag(0)) {
        String str = obj.AsString();
        try {
          CBORUtilities.ParseAtomDateTimeString(str, outYear, lesserFields);
          return null; // no error
        } catch (ArithmeticException ex) {
          return ex.getMessage();
        } catch (IllegalStateException ex) {
          return ex.getMessage();
        } catch (IllegalArgumentException ex) {
          return ex.getMessage();
        }
      } else if (obj.HasMostOuterTag(1)) {
        CBORObject untagobj = obj.UntagOne();
        if (!untagobj.isNumber()) {
          return "Not a finite number";
        }
        CBORNumber num = untagobj.AsNumber();
        if (!num.IsFinite()) {
          return "Not a finite number";
        }
        if (num.CanFitInInt64()) {
          CBORUtilities.BreakDownSecondsSinceEpoch(
            num.ToInt64Checked(),
            outYear,
            lesserFields);
        } else {
          EDecimal dec;
          dec = (EDecimal)untagobj.ToObject(EDecimal.class);
          CBORUtilities.BreakDownSecondsSinceEpoch(
            dec,
            outYear,
            lesserFields);
        }
        return null; // No error
      }
      return "Not tag 0 or 1";
    }

    public CBORObject DateTimeFieldsToCBORObject(int smallYear, int month, int
      day) {
      return this.DateTimeFieldsToCBORObject(EInteger.FromInt32(smallYear),
          new int[] { month, day, 0, 0, 0, 0, 0 });
    }

    public CBORObject DateTimeFieldsToCBORObject(
      int smallYear,
      int month,
      int day,
      int hour,
      int minute,
      int second) {
      return this.DateTimeFieldsToCBORObject(EInteger.FromInt32(smallYear),
          new int[] { month, day, hour, minute, second, 0, 0 });
    }

    public CBORObject DateTimeFieldsToCBORObject(int year, int[]
      lesserFields) {
      return this.DateTimeFieldsToCBORObject(EInteger.FromInt32(year),
  lesserFields);
    }

    public CBORObject DateTimeFieldsToCBORObject(EInteger bigYear, int[]
      lesserFields) {
      if (bigYear == null) {
        throw new NullPointerException("bigYear");
      }
      if (lesserFields == null) {
        throw new NullPointerException("lesserFields");
      }
      // TODO: Make into CBORException in next major version
      if (lesserFields.length < 7) {
        throw new IllegalArgumentException("\"lesserFields\" + \"'s length\" (" +
          lesserFields.length + ") is not greater or equal to 7");
      }
      try {
        CBORUtilities.CheckYearAndLesserFields(bigYear, lesserFields);
        ConversionType thisType = this.getType();
        switch (thisType) {
          case TaggedString: {
              String str = CBORUtilities.ToAtomDateTimeString(bigYear,
                  lesserFields);
              return CBORObject.FromObjectAndTag(str, 0);
            }
          case TaggedNumber:
          case UntaggedNumber:
            try {
              int[] status = new int[1];
              EFloat ef = CBORUtilities.DateTimeToIntegerOrDouble(
                  bigYear,
                  lesserFields,
                  status);
              switch (status[0]) {
                case 0:
                  return thisType == ConversionType.TaggedNumber ?
                    CBORObject.FromObjectAndTag(ef.ToEInteger(), 1) :
                    CBORObject.FromObject(ef.ToEInteger());
                case 1:
                  return thisType == ConversionType.TaggedNumber ?
                    CBORObject.FromFloatingPointBits(ef.ToDoubleBits(), 8)
                    .WithTag(1) :
                    CBORObject.FromFloatingPointBits(ef.ToDoubleBits(), 8);
                default: throw new CBORException("Too big or small to fit an" +
                    "\u0020integer" + "\u0020or floating-point number");
              }
            } catch (IllegalArgumentException ex) {
              throw new CBORException(ex.getMessage(), ex);
            }
          default: throw new CBORException("Internal error");
        }
      } catch (IllegalArgumentException ex) {
        throw new CBORException(ex.getMessage(), ex);
      }
    }

    public CBORObject ToCBORObject(java.util.Date obj) {
      try {
        int[] lesserFields = new int[7];
        EInteger[] outYear = new EInteger[1];
        PropertyMap.BreakDownDateTime(obj, outYear, lesserFields);
        return this.DateTimeFieldsToCBORObject(outYear[0], lesserFields);
      } catch (IllegalArgumentException ex) {
        throw new CBORException(ex.getMessage(), ex);
      }
    }
  }
