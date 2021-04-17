package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  public class DateTest {
    public static void DateConverterRoundTripOne(
      CBORDateConverter dtc,
      int smallYear,
      int[] lesserFields) {
       DateConverterRoundTripOne(
         dtc,
         EInteger.FromInt32(smallYear),
         lesserFields);
    }

    public static void DateConverterRoundTripOne(
      CBORDateConverter dtc,
      EInteger year,
      int[] lesserFields) {
       if (lesserFields == null) {
         throw new NullPointerException("lesserFields");
       }
       if (year == null) {
         throw new NullPointerException("year");
       }
       String yearString = year.toString();
       String fieldsString = yearString + "," + lesserFields[0] + "," +
lesserFields[1] + "," + lesserFields[2] + "," + lesserFields[3] + "," +
lesserFields[4] + "," + lesserFields[5] + "," + lesserFields[6];
       try {
       if (dtc == null) {
         throw new NullPointerException("dtc");
       }
       CBORObject obj = dtc.DateTimeFieldsToCBORObject(year, lesserFields);
       fieldsString += "\n" + obj.toString();
       EInteger[] newYear = new EInteger[1];
       int[] newLesserFields = new int[7];
       if (dtc.TryGetDateTimeFields(obj, newYear, newLesserFields)) {
         for (int i = 0; i < lesserFields.length; ++i) {
           Assert.assertEquals(fieldsString, lesserFields[i], newLesserFields[i]);
         }
         Assert.assertEquals("year\n" + fieldsString,year,newYear[0]);
       } else {
         Assert.fail(fieldsString);
       }
       } catch (Exception ex) {
         throw new IllegalStateException(
           ex.getMessage() + "\n" + fieldsString,
           ex);
       }
    }

    private static EInteger RandomYear(IRandomGenExtended irg) {
       return EInteger.FromInt32(irg.GetInt32(9998) + 1);
    }

    private static EInteger RandomExpandedYear(IRandomGenExtended irg) {
       return EInteger.FromInt32(irg.GetInt32(1000000) - 500000);
    }

    private static boolean IsLeapYear(EInteger bigYear) {
      bigYear = bigYear.Remainder(400);
      if (bigYear.signum() < 0) {
        bigYear = bigYear.Add(400);
      }
      return ((bigYear.Remainder(4).signum() == 0) &&
(bigYear.Remainder(100).signum() !=
0)) ||
          (bigYear.Remainder(400).signum() == 0);
    }

    private static final int[] ValueNormalDays = {
      0, 31, 28, 31, 30, 31, 30,
      31, 31, 30,
      31, 30, 31,
    };

    private static final int[] ValueLeapDays = {
      0, 31, 29, 31, 30, 31, 30,
      31, 31, 30,
      31, 30, 31,
    };

    private static int[] RandomLesserFields(IRandomGenExtended irg, EInteger
year) {
       int month = irg.GetInt32(12) + 1;
       int days = IsLeapYear(year) ? ValueLeapDays[month] :
ValueNormalDays[month];
       return new int[] {
         month,
         irg.GetInt32(days) + 1,
         irg.GetInt32(24),
         irg.GetInt32(60),
         irg.GetInt32(60),
         irg.GetInt32(1000000000),
         0,
       };
    }

    @Test
    public void DateConverterRoundTrip() {
       CBORDateConverter[] dtcs = new CBORDateConverter[] {
         CBORDateConverter.TaggedString,
       };
       RandomGenerator rg = new RandomGenerator();
       for (int i = 0; i < 10000; ++i) {
          EInteger year = RandomYear(rg);
          int[] lesserFields = RandomLesserFields(rg, year);
          for (int j = 0; j < dtcs.length; ++j) {
            DateConverterRoundTripOne(dtcs[j], year, lesserFields);
          }
       }
       dtcs = new CBORDateConverter[] {
         CBORDateConverter.TaggedNumber,
         CBORDateConverter.UntaggedNumber,
       };
       int[] lesserFields;
       for (int i = 0; i < 30000; ++i) {
          EInteger year = RandomExpandedYear(rg);
          lesserFields = RandomLesserFields(rg, year);
          // Don't check fractional seconds because conversion is lossy
          lesserFields[5] = 0;
          for (int j = 0; j < dtcs.length; ++j) {
            DateConverterRoundTripOne(dtcs[j], year, lesserFields);
          }
       }
       lesserFields = new int[] {
         2,
         11,
         7,
         59,
         3,
         0,
         0,
       };
       DateConverterRoundTripOne(
         CBORDateConverter.TaggedString,
         9328,
         lesserFields);
       DateConverterRoundTripOne(
         CBORDateConverter.UntaggedNumber,
         9328,
         lesserFields);
    }
  }
