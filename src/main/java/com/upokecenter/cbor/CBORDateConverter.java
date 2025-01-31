package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under the Unlicense: https://unlicense.org/

 */

import com.upokecenter.numbers.*;

  /**
   * <p>A class for converting date-time objects to and from tagged CBOR
   * objects.</p> <p>In this class's documentation, the "number of seconds since
   * the start of 1970" is based on the POSIX definition of "seconds since the
   * Epoch", a definition that does not count leap seconds. This number of
   * seconds assumes the use of a proleptic Gregorian calendar, in which the
   * rules regarding the number of days in each month and which years are leap
   * years are the same for all years as they were in 1970 (including without
   * regard to time zone differences or transitions from other calendars to the
   * Gregorian).</p>
   */
  public final class CBORDateConverter implements ICBORToFromConverter<java.util.Date> {
    /**
     * A converter object where FromCBORObject accepts CBOR objects with tag 0
     * (date/time strings) and tag 1 (number of seconds since the start of 1970),
     * and ToCBORObject converts date/time objects (java.util.Date in DotNet, and Date in
     * Java) to CBOR objects of tag 0.
     */
    public static final CBORDateConverter TaggedString =
      new CBORDateConverter(ConversionType.TaggedString);

    /**
     * A converter object where FromCBORObject accepts CBOR objects with tag 0
     * (date/time strings) and tag 1 (number of seconds since the start of 1970),
     * and ToCBORObject converts date/time objects (java.util.Date in DotNet, and Date in
     * Java) to CBOR objects of tag 1. The ToCBORObject conversion is lossless only
     * if the number of seconds since the start of 1970 can be represented exactly
     * as an integer in the interval [-(2^64), 2^64 - 1] or as a 64-bit
     * floating-point number in the IEEE 754r binary64 format; the conversion is
     * lossy otherwise. The ToCBORObject conversion will throw an exception if the
     * conversion to binary64 results in positive infinity, negative infinity, or
     * not-a-number.
     */
    public static final CBORDateConverter TaggedNumber =
      new CBORDateConverter(ConversionType.TaggedNumber);

    /**
     * A converter object where FromCBORObject accepts untagged CBOR integer or
     * CBOR floating-point objects that give the number of seconds since the start
     * of 1970, and where ToCBORObject converts date/time objects (java.util.Date in
     * DotNet, and Date in Java) to such untagged CBOR objects. The ToCBORObject
     * conversion is lossless only if the number of seconds since the start of 1970
     * can be represented exactly as an integer in the interval [-(2^64), 2^64 - 1]
     * or as a 64-bit floating-point number in the IEEE 754r binary64 format; the
     * conversion is lossy otherwise. The ToCBORObject conversion will throw an
     * exception if the conversion to binary64 results in positive infinity,
     * negative infinity, or not-a-number.
     */
    public static final CBORDateConverter UntaggedNumber =
      new CBORDateConverter(ConversionType.UntaggedNumber);

    /**
     * Conversion type for date-time conversion.
     */
    public enum ConversionType
    {
      /**
       * FromCBORObject accepts CBOR objects with tag 0 (date/time strings) and tag 1
       * (number of seconds since the start of 1970), and ToCBORObject converts
       * date/time objects to CBOR objects of tag 0.
       */
      TaggedString,

      /**
       * FromCBORObject accepts objects with tag 0 (date/time strings) and tag 1
       * (number of seconds since the start of 1970), and ToCBORObject converts
       * date/time objects to CBOR objects of tag 1. The ToCBORObject conversion is
       * lossless only if the number of seconds since the start of 1970 can be
       * represented exactly as an integer in the interval [-(2^64), 2^64 - 1] or as
       * a 64-bit floating-point number in the IEEE 754r binary64 format; the
       * conversion is lossy otherwise. The ToCBORObject conversion will throw an
       * exception if the conversion to binary64 results in positive infinity,
       * negative infinity, or not-a-number.
       */
      TaggedNumber,

      /**
       * FromCBORObject accepts untagged CBOR integer or CBOR floating-point objects
       * that give the number of seconds since the start of 1970, and ToCBORObject
       * converts date/time objects (java.util.Date in DotNet, and Date in Java) to such
       * untagged CBOR objects. The ToCBORObject conversion is lossless only if the
       * number of seconds since the start of 1970 can be represented exactly as an
       * integer in the interval [-(2^64), 2^64 - 1] or as a 64-bit floating-point
       * number in the IEEE 754r binary64 format; the conversion is lossy otherwise.
       * The ToCBORObject conversion will throw an exception if the conversion to
       * binary64 results in positive infinity, negative infinity, or not-a-number.
       */
      UntaggedNumber,
    }

    /**
     * Initializes a new instance of the {@link
     * com.upokecenter.cbor.CBORDateConverter} class.
     */
    public CBORDateConverter() {
 this(ConversionType.TaggedString);
    }

    /**
     * Gets the conversion type for this date converter.
     * @return The conversion type for this date converter.
     */
    public final ConversionType getType() { return propVartype; }
private final ConversionType propVartype;

    /**
     * Initializes a new instance of the {@link
     * com.upokecenter.cbor.CBORDateConverter} class.
     * @param convType Conversion type giving the rules for converting dates and
     * times to and from CBOR objects.
     */
    public CBORDateConverter(ConversionType convType) {
      this.propVartype = convType;
    }

    /**
     * Converts a CBOR object to a java.util.Date (in DotNet) or a Date (in Java).
     * @param obj A CBOR object that specifies a date/time according to the
     * conversion type used to create this date converter.
     * @return A java.util.Date or Date that encodes the date/time specified in the CBOR
     * object.
     * @throws NullPointerException The parameter {@code obj} is null.
     * @throws com.upokecenter.cbor.CBORException The format of the CBOR object is
     * not supported, or another error occurred in conversion.
     */
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

    /**
     * Tries to extract the fields of a date and time in the form of a CBOR object.
     * @param obj A CBOR object that specifies a date/time according to the
     * conversion type used to create this date converter.
     * @param year An array whose first element will store the year. The array's
     * length must be 1 or greater. If this function fails, the first element is
     * set to null.
     * @param lesserFields <p>An array that will store the fields (other than the
     * year) of the date and time. The array's length must be 7 or greater. If this
     * function fails, the first seven elements are set to 0. If this method is
     * successful, the first seven elements of the array (starting at 0) will be as
     * follows: </p><ul> <li>0 - Month of the year, from 1 (January) through 12
     * (December).</li><li>1 - Day of the month, from 1 through 31.</li><li>2 -
     * Hour of the day, from 0 through 23.</li><li>3 - Minute of the hour, from 0
     * through 59.</li><li>4 - Second of the minute, from 0 through 59.</li><li>5 -
     * Fractional seconds, expressed in nanoseconds. This value cannot be less than
     * 0 and must be less than 1000*1000*1000.</li><li>6 - Number of minutes to
     * subtract from this date and time to get global time. This number can be
     * positive or negative, but cannot be less than -1439 or greater than 1439.
     * For tags 0 and 1, this value is always 0.</li></ul>.
     * @return Either {@code true} if the method is successful, or {@code false}
     * otherwise.
     */
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
          dec = untagobj.ToEDecimal();
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
          dec = untagobj.ToEDecimal();
          CBORUtilities.BreakDownSecondsSinceEpoch(
            dec,
            outYear,
            lesserFields);
        }
        return null; // No error
      }
      return "Not tag 0 or 1";
    }

    /**
     * Converts a date/time in the form of a year, month, and day to a CBOR object.
     * The hour, minute, and second are treated as 00:00:00 by this method, and the
     * time offset is treated as 0 by this method.
     * @param smallYear The year.
     * @param month Month of the year, from 1 (January) through 12 (December).
     * @param day Day of the month, from 1 through 31.
     * @return A CBOR object encoding the specified date fields according to the
     * conversion type used to create this date converter.
     * @throws com.upokecenter.cbor.CBORException An error occurred in conversion.
     */
    public CBORObject DateTimeFieldsToCBORObject(int smallYear, int month, int
      day) {
      return this.DateTimeFieldsToCBORObject(EInteger.FromInt32(smallYear),
        new int[] { month, day, 0, 0, 0, 0, 0 });
    }

    /**
     * Converts a date/time in the form of a year, month, day, hour, minute, and
     * second to a CBOR object. The time offset is treated as 0 by this method.
     * @param smallYear The year.
     * @param month Month of the year, from 1 (January) through 12 (December).
     * @param day Day of the month, from 1 through 31.
     * @param hour Hour of the day, from 0 through 23.
     * @param minute Minute of the hour, from 0 through 59.
     * @param second Second of the minute, from 0 through 59.
     * @return A CBOR object encoding the specified date fields according to the
     * conversion type used to create this date converter.
     * @throws com.upokecenter.cbor.CBORException An error occurred in conversion.
     */
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

    /**
     * Converts a date/time in the form of a year, month, day, hour, minute,
     * second, fractional seconds, and time offset to a CBOR object.
     * @param year The year.
     * @param lesserFields An array that will store the fields (other than the
     * year) of the date and time. See the TryGetDateTimeFields method for
     * information on the "lesserFields" parameter.
     * @return A CBOR object encoding the specified date fields according to the
     * conversion type used to create this date converter.
     * @throws NullPointerException The parameter {@code lesserFields} is null.
     * @throws com.upokecenter.cbor.CBORException An error occurred in conversion.
     */
    public CBORObject DateTimeFieldsToCBORObject(int year, int[]
      lesserFields) {
      return this.DateTimeFieldsToCBORObject(EInteger.FromInt32(year),
        lesserFields);
    }

    /**
     * Converts a date/time in the form of a year, month, day, hour, minute,
     * second, fractional seconds, and time offset to a CBOR object.
     * @param bigYear The year.
     * @param lesserFields An array that will store the fields (other than the
     * year) of the date and time. See the TryGetDateTimeFields method for
     * information on the "lesserFields" parameter.
     * @return A CBOR object encoding the specified date fields according to the
     * conversion type used to create this date converter.
     * @throws NullPointerException The parameter {@code bigYear} or {@code
     * lesserFields} is null.
     * @throws com.upokecenter.cbor.CBORException An error occurred in conversion.
     */
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
            return CBORObject.FromString(str).WithTag(0);
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
                case 0: {
                  CBORObject cbor = CBORObject.FromEInteger(ef.ToEInteger());
                  return thisType == ConversionType.TaggedNumber ?
                    CBORObject.FromCBORObjectAndTag(cbor, 1) :
                    cbor;
                }
                case 1:
                  return thisType == ConversionType.TaggedNumber ?
                    CBORObject.FromFloatingPointBits(ef.ToDoubleBits(), 8)
                    .WithTag(1) :
                    CBORObject.FromFloatingPointBits(ef.ToDoubleBits(), 8);
                default:
                  throw new CBORException("Too big or small to fit an" +
                    "\u0020integer" + "\u0020or floating-point number");
              }
            } catch (IllegalArgumentException ex) {
              throw new CBORException(ex.getMessage(), ex);
            }
          default:
            throw new CBORException("Internal error");
        }
      } catch (IllegalArgumentException ex) {
        throw new CBORException(ex.getMessage(), ex);
      }
    }

    /**
     * Converts a java.util.Date (in DotNet) or Date (in Java) to a CBOR object in a
     * manner specified by this converter's conversion type.
     * @param obj The parameter {@code obj} is a java.util.Date object.
     * @return A CBOR object encoding the date/time in the java.util.Date or Date
     * according to the conversion type used to create this date converter.
     * @throws com.upokecenter.cbor.CBORException An error occurred in conversion.
     */
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
