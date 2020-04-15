package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  /**
   * Contains utility methods that may have use outside of the CBORObject class.
   */
  final class CBORUtilities {
private CBORUtilities() {
}
    private static final String HexAlphabet = "0123456789ABCDEF";

    public static int FastPathStringCompare(String strA, String strB) {
      if (strA == null) {
        return (strB == null) ? 0 : -1;
      }
      if (strB == null) {
        return 1;
      }
      if (strA.length() == 0) {
        return strB.length() == 0 ? 0 : -1;
      }
      if (strB.length() == 0) {
        return strA.length() == 0 ? 0 : 1;
      }
      long strAUpperBound = strA.length() * 3;
      if (strAUpperBound < strB.length()) {
        return -1;
      }
      long strBUpperBound = strB.length() * 3;
      if (strBUpperBound < strA.length()) {
        return 1;
      }
      if (strA.length() < 128 && strB.length() < 128) {
        if (strA.length() == strB.length()) {
          boolean equalStrings = true;
          for (int i = 0; i < strA.length(); ++i) {
            if (strA.charAt(i) != strB.charAt(i)) {
              equalStrings = false;
              break;
            }
          }
          if (equalStrings) {
            return 0;
          }
        }
        boolean nonAscii = false;
        for (int i = 0; i < strA.length(); ++i) {
          if ((strA.charAt(i) & 0xf800) == 0xd800) {
            return -2; // has surrogates
          } else if (strA.charAt(i) > 0x80) {
            nonAscii = true;
            break;
          }
        }
        for (int i = 0; i < strB.length(); ++i) {
          if ((strB.charAt(i) & 0xf800) == 0xd800) {
            return -2; // has surrogates
          } else if (strB.charAt(i) > 0x80) {
            nonAscii = true;
            break;
          }
        }
        if (nonAscii) {
          long cplA = DataUtilities.GetUtf8Length(strA, true);
          long cplB = DataUtilities.GetUtf8Length(strB, true);
          if (cplA != cplB) {
            return cplA < cplB ? -1 : 1;
          }
        } else {
          if (strA.length() != strB.length()) {
            return strA.length() < strB.length() ? -1 : 1;
          }
        }
        for (int i = 0; i < strA.length(); ++i) {
          if (strA.charAt(i) != strB.charAt(i)) {
            return strA.charAt(i) < strB.charAt(i) ? -1 : 1;
          }
        }
        return 0;
      }
      return -2; // not short enough
    }

    public static int Utf8CodePointAt(byte[] utf8, int offset) {
       int endPos = utf8.length;
       if (offset < 0 || offset >= endPos) {
         return -1;
       }
       int c = ((int)utf8[offset]) & 0xff;
       if (c <= 0x7f) {
         return c;
       } else if (c >= 0xc2 && c <= 0xdf) {
         ++offset;
         int c1 = offset < endPos ?
                ((int)utf8[offset]) & 0xff : -1;
                return (
                  c1 < 0x80 || c1 > 0xbf) ? -2 : (((c - 0xc0) << 6) |
(c1 - 0x80));
            } else if (c >= 0xe0 && c <= 0xef) {
              ++offset;
              int c1 = offset < endPos ? ((int)utf8[offset++]) & 0xff : -1;
              int c2 = offset < endPos ? ((int)utf8[offset]) & 0xff : -1;
              int lower = (c == 0xe0) ? 0xa0 : 0x80;
              int upper = (c == 0xed) ? 0x9f : 0xbf;
              return (c1 < lower || c1 > upper || c2 < 0x80 || c2 > 0xbf) ?
-2 : (((c - 0xe0) << 12) | ((c1 - 0x80) << 6) | (c2 - 0x80));
            } else if (c >= 0xf0 && c <= 0xf4) {
              ++offset;
              int c1 = offset < endPos ? ((int)utf8[offset++]) & 0xff : -1;
              int c2 = offset < endPos ? ((int)utf8[offset++]) & 0xff : -1;
              int c3 = offset < endPos ? ((int)utf8[offset]) & 0xff : -1;
              int lower = (c == 0xf0) ? 0x90 : 0x80;
              int upper = (c == 0xf4) ? 0x8f : 0xbf;
              if (c1 < lower || c1 > upper || c2 < 0x80 || c2 > 0xbf ||
                c3 < 0x80 || c3 > 0xbf) {
                return -2;
              }
              return ((c - 0xf0) << 18) | ((c1 - 0x80) << 12) | ((c2 - 0x80) <<
                  6) | (c3 - 0x80);
            } else {
                return -2;
            }
    }

    public static boolean CheckUtf16(String str) {
      int upos = 0;
      while (true) {
        if (upos == str.length()) {
          return true;
        }
        int sc = DataUtilities.CodePointAt(str, upos, 1);
        if (sc < 0) {
          return false;
        }
        if (sc >= 0x10000) {
          upos += 2;
        } else {
           ++upos;
         }
      }
    }

    public static boolean CheckUtf8(byte[] utf8) {
      int upos = 0;
      while (true) {
         int sc = Utf8CodePointAt(utf8, upos);
         if (sc == -1) {
           return true;
         }
         if (sc == -2) {
           return false;
         }
         if (sc >= 0x10000) {
           upos += 4;
         } else if (sc >= 0x800) {
           upos += 3;
         } else if (sc >= 0x80) {
           upos += 2;
         } else {
           ++upos;
         }
      }
    }

    public static boolean StringEqualsUtf8(String str, byte[] utf8) {
      if (str == null) {
        return utf8 == null;
      }
      if (utf8 == null) {
        return false;
      }
      long strAUpperBound = str.length() * 3;
      if (strAUpperBound < utf8.length) {
        return false;
      }
      long strBUpperBound = utf8.length * 3;
      if (strBUpperBound < str.length()) {
        return false;
      }
      int spos = 0;
      int upos = 0;
      while (true) {
         int sc = DataUtilities.CodePointAt(str, spos, 1);
         int uc = Utf8CodePointAt(utf8, upos);
         if (uc == -2) {
           throw new IllegalStateException("Invalid encoding");
         }
         if (sc == -1) {
           return uc == -1;
         }
         if (sc != uc) {
           return false;
         }
         if (sc >= 0x10000) {
           spos += 2;
           upos += 4;
         } else if (sc >= 0x800) {
           ++spos;
           upos += 3;
         } else if (sc >= 0x80) {
           ++spos;
           upos += 2;
         } else {
           ++spos;
           ++upos;
         }
      }
    }

    public static boolean ByteArrayEquals(byte[] a, byte[] b) {
      if (a == null) {
        return b == null;
      }
      if (b == null) {
        return false;
      }
      if (a.length != b.length) {
        return false;
      }
      for (int i = 0; i < a.length; ++i) {
        if (a[i] != b[i]) {
          return false;
        }
      }
      return true;
    }

    public static int ByteArrayHashCode(byte[] a) {
      if (a == null) {
        return 0;
      }
      int ret = 19;
      {
        ret = (ret * 31) + a.length;
        for (int i = 0; i < a.length; ++i) {
          ret = (ret * 31) + a[i];
        }
      }
      return ret;
    }

    public static int ByteArrayCompare(byte[] a, byte[] b) {
      if (a == null) {
        return (b == null) ? 0 : -1;
      }
      if (b == null) {
        return 1;
      }
      int c = Math.min(a.length, b.length);
      for (int i = 0; i < c; ++i) {
        if (a[i] != b[i]) {
          return (a[i] < b[i]) ? -1 : 1;
        }
      }
      return (a.length != b.length) ? ((a.length < b.length) ? -1 : 1) : 0;
    }

    public static int ByteArrayCompareLengthFirst(byte[] a, byte[] b) {
      if (a == null) {
        return (b == null) ? 0 : -1;
      }
      if (b == null) {
        return 1;
      }
      if (a.length != b.length) {
        return a.length < b.length ? -1 : 1;
      }
      for (int i = 0; i < a.length; ++i) {
        if (a[i] != b[i]) {
          return (a[i] < b[i]) ? -1 : 1;
        }
      }
      return 0;
    }

    public static String TrimDotZero(String str) {
      return (str.length() > 2 && str.charAt(str.length() - 1) == '0' && str.charAt(str.length()
            - 2) == '.') ? str.substring(0,str.length() - 2) :
        str;
    }

    public static long DoubleToInt64Bits(double dbl) {
      return Double.doubleToRawLongBits(dbl);
    }

    public static int SingleToInt32Bits(float flt) {
      return Float.floatToRawIntBits(flt);
    }

    public static double Int64BitsToDouble(long bits) {
      return Double.longBitsToDouble(bits);
    }

    public static float Int32BitsToSingle(int bits) {
      return Float.intBitsToFloat(bits);
    }

    public static String DoubleToString(double dbl) {
      return EFloat.FromDouble(dbl).ToShortestString(EContext.Binary64);
    }

    public static String SingleToString(float sing) {
      return EFloat.FromSingle(sing).ToShortestString(EContext.Binary32);
    }

    public static String LongToString(long longValue) {
      if (longValue == Long.MIN_VALUE) {
        return "-9223372036854775808";
      }
      if (longValue == 0L) {
        return "0";
      }
      if (longValue == (long)Integer.MIN_VALUE) {
        return "-2147483648";
      }
      boolean neg = longValue < 0;
      int count = 0;
      char[] chars;
      int intlongValue = ((int)longValue);
      if ((long)intlongValue == longValue) {
        chars = new char[12];
        count = 11;
        if (neg) {
          intlongValue = -intlongValue;
        }
        while (intlongValue > 43698) {
          int intdivValue = intlongValue / 10;
          char digit = HexAlphabet.charAt((int)(intlongValue - (intdivValue *
                  10)));
          chars[count--] = digit;
          intlongValue = intdivValue;
        }
        while (intlongValue > 9) {
          int intdivValue = (intlongValue * 26215) >> 18;
          char digit = HexAlphabet.charAt((int)(intlongValue - (intdivValue *
                  10)));
          chars[count--] = digit;
          intlongValue = intdivValue;
        }
        if (intlongValue != 0) {
          chars[count--] = HexAlphabet.charAt((int)intlongValue);
        }
        if (neg) {
          chars[count] = '-';
        } else {
          ++count;
        }
        return new String(chars, count, 12 - count);
      } else {
        chars = new char[24];
        count = 23;
        if (neg) {
          longValue = -longValue;
        }
        while (longValue > 43698) {
          long divValue = longValue / 10;
          char digit = HexAlphabet.charAt((int)(longValue - (divValue * 10)));
          chars[count--] = digit;
          longValue = divValue;
        }
        while (longValue > 9) {
          long divValue = (longValue * 26215) >> 18;
          char digit = HexAlphabet.charAt((int)(longValue - (divValue * 10)));
          chars[count--] = digit;
          longValue = divValue;
        }
        if (longValue != 0) {
          chars[count--] = HexAlphabet.charAt((int)longValue);
        }
        if (neg) {
          chars[count] = '-';
        } else {
          ++count;
        }
        return new String(chars, count, 24 - count);
      }
    }

    private static EInteger FloorDiv(EInteger a, EInteger n) {
      return a.signum() >= 0 ? a.Divide(n) : EInteger.FromInt32(-1).Subtract(
          EInteger.FromInt32(-1).Subtract(a).Divide(n));
    }

    private static EInteger FloorMod(EInteger a, EInteger n) {
      return a.Subtract(FloorDiv(a, n).Multiply(n));
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

    private static final int[] ValueNormalToMonth = {
      0, 0x1f, 0x3b, 90, 120,
      0x97, 0xb5,
      0xd4, 0xf3, 0x111, 0x130, 0x14e, 0x16d,
    };

    private static final int[] ValueLeapToMonth = {
      0, 0x1f, 60, 0x5b, 0x79,
      0x98, 0xb6,
      0xd5, 0xf4, 0x112, 0x131, 0x14f, 0x16e,
    };

    public static void GetNormalizedPartProlepticGregorian(
      EInteger year,
      int month,
      EInteger day,
      EInteger[] dest) {
      // NOTE: This method assumes month is 1 to 12
      if (month <= 0 || month > 12) {
        throw new IllegalArgumentException("month");
      }
      int[] dayArray = (year.Remainder(4).signum() != 0 || (
            year.Remainder(100).signum() == 0 && year.Remainder(400).signum() !=
0)) ?
        ValueNormalDays : ValueLeapDays;
      if (day.compareTo(101) > 0) {
        EInteger count = day.Subtract(100).Divide(146097);
        day = day.Subtract(count.Multiply(146097));
        year = year.Add(count.Multiply(400));
      }
      while (true) {
        EInteger days = EInteger.FromInt32(dayArray[month]);
        if (day.signum() > 0 && day.compareTo(days) <= 0) {
          break;
        }
        if (day.compareTo(days) > 0) {
          day = day.Subtract(days);
          if (month == 12) {
            month = 1;
            year = year.Add(1);
            dayArray = (year.Remainder(4).signum() != 0 || (
                  year.Remainder(100).signum() == 0 &&
                  year.Remainder(400).signum() != 0)) ? ValueNormalDays :
              ValueLeapDays;
            } else {
            ++month;
          }
        }
        if (day.signum() <= 0) {
          --month;
          if (month <= 0) {
            year = year.Add(-1);
            month = 12;
          }
          dayArray = (year.Remainder(4).signum() != 0 || (
                year.Remainder(100).signum() == 0 &&
                year.Remainder(400).signum() != 0)) ? ValueNormalDays :
            ValueLeapDays;
          day = day.Add(dayArray[month]);
        }
      }
      dest[0] = year;
      dest[1] = EInteger.FromInt32(month);
      dest[2] = day;
    }

    /*
       // Example: Apple Time is a 32-bit unsigned integer
       // of the number of seconds since the start of 1904.
       // EInteger appleTime = GetNumberOfDaysProlepticGregorian(
         // year, // month
         //,
         day)
       // .Subtract(GetNumberOfDaysProlepticGregorian(
       // EInteger.FromInt32(1904),
       1 // ,
       s1));*/
    public static EInteger GetNumberOfDaysProlepticGregorian(
      EInteger year,
      int month,
      int mday) {
      // NOTE: month = 1 is January, year = 1 is year 1
      if (month <= 0 || month > 12) {
        throw new IllegalArgumentException("month");
      }
      if (mday <= 0 || mday > 31) {
        throw new IllegalArgumentException("mday");
      }
      EInteger numDays = EInteger.FromInt32(0);
      int startYear = 1970;
      if (year.compareTo(startYear) < 0) {
        EInteger ei = EInteger.FromInt32(startYear - 1);
        EInteger diff = ei.Subtract(year);

        if (diff.compareTo(401) > 0) {
          EInteger blocks = diff.Subtract(401).Divide(400);
          numDays = numDays.Subtract(blocks.Multiply(146097));
          diff = diff.Subtract(blocks.Multiply(400));
          ei = ei.Subtract(blocks.Multiply(400));
        }

        numDays = numDays.Subtract(diff.Multiply(365));
        int decrement = 1;
        for (;
          ei.compareTo(year) > 0;
          ei = ei.Subtract(decrement)) {
          if (decrement == 1 && ei.Remainder(4).signum() == 0) {
            decrement = 4;
          }
          if (!(ei.Remainder(4).signum() != 0 || (
                ei.Remainder(100).signum() == 0 && ei.Remainder(400).signum() !=
                0))) {
            numDays = numDays.Subtract(1);
          }
        }
        if (year.Remainder(4).signum() != 0 || (
            year.Remainder(100).signum() == 0 && year.Remainder(400).signum() != 0)) {
          numDays = numDays.Subtract(365 - ValueNormalToMonth[month])
            .Subtract(ValueNormalDays[month] - mday + 1);
          } else {
          numDays = numDays
            .Subtract(366 - ValueLeapToMonth[month])
            .Subtract(ValueLeapDays[month] - mday + 1);
        }
      } else {
        boolean isNormalYear = year.Remainder(4).signum() != 0 ||
(year.Remainder(100).signum() == 0 && year.Remainder(400).signum() != 0);

        EInteger ei = EInteger.FromInt32(startYear);
        if (ei.Add(401).compareTo(year) < 0) {
          EInteger y2 = year.Subtract(2);
          numDays = numDays.Add(
              y2.Subtract(startYear).Divide(400).Multiply(146097));
          ei = y2.Subtract(
              y2.Subtract(startYear).Remainder(400));
        }

        EInteger diff = year.Subtract(ei);
        numDays = numDays.Add(diff.Multiply(365));
        EInteger eileap = ei;
        if (ei.Remainder(4).signum() != 0) {
          eileap = eileap.Add(4 - eileap.Remainder(4).ToInt32Checked());
        }
        numDays = numDays.Add(year.Subtract(eileap).Add(3).Divide(4));
        if (ei.Remainder(100).signum() != 0) {
          ei = ei.Add(100 - ei.Remainder(100).ToInt32Checked());
        }
        while (ei.compareTo(year) < 0) {
          if (ei.Remainder(400).signum() != 0) {
            numDays = numDays.Subtract(1);
          }
          ei = ei.Add(100);
        }
        int yearToMonth = isNormalYear ? ValueNormalToMonth[month - 1] :
          ValueLeapToMonth[month - 1];
        numDays = numDays.Add(yearToMonth)
          .Add(mday - 1);
      }
      return numDays;
    }

    public static void BreakDownSecondsSinceEpoch(
      EDecimal edec,
      EInteger[] year,
      int[] lesserFields) {
      EInteger integerPart = edec.Quantize(0, ERounding.Floor)
        .ToEInteger();
      EDecimal fractionalPart = edec.Abs()
        .Subtract(EDecimal.FromEInteger(integerPart).Abs());
      int nanoseconds = fractionalPart.Multiply(1000000000)
        .ToInt32Checked();
      EInteger[] normPart = new EInteger[3];
      EInteger days = FloorDiv(
          integerPart,
          EInteger.FromInt32(86400)).Add(1);
      int secondsInDay = FloorMod(
          integerPart,
          EInteger.FromInt32(86400)).ToInt32Checked();
      GetNormalizedPartProlepticGregorian(
        EInteger.FromInt32(1970),
        1,
        days,
        normPart);
      lesserFields[0] = normPart[1].ToInt32Checked();
      lesserFields[1] = normPart[2].ToInt32Checked();
      lesserFields[2] = secondsInDay / 3600;
      lesserFields[3] = (secondsInDay % 3600) / 60;
      lesserFields[4] = secondsInDay % 60;
      lesserFields[5] = nanoseconds / 100;
      lesserFields[6] = 0;
      year[0] = normPart[0];
    }

    public static boolean NameStartsWithWord(String name, String word) {
      int wl = word.length();
      return name.length() > wl && name.substring(0,wl).equals(word) && !(name.charAt(wl) >= 'a' && name.charAt(wl) <=
'z') &&
        !(name.charAt(wl) >= '0' && name.charAt(wl) <= '9');
    }

    public static String FirstCharLower(String name) {
      if (name.length() > 0 && name.charAt(0) >= 'A' && name.charAt(0) <= 'Z') {
        StringBuilder sb = new StringBuilder();
        sb.append((char)(name.charAt(0) + 0x20));
        sb.append(name.substring(1));
        return sb.toString();
      }
      return name;
    }

    public static String FirstCharUpper(String name) {
      if (name.length() > 0 && name.charAt(0) >= 'a' && name.charAt(0) <= 'z') {
        StringBuilder sb = new StringBuilder();
        sb.append((char)(name.charAt(0) - 0x20));
        sb.append(name.substring(1));
        return sb.toString();
      }
      return name;
    }

    private static boolean IsValidDateTime(int[] dateTime) {
      if (dateTime == null || dateTime.length < 8) {
        return false;
      }
      if (dateTime[1] < 1 || dateTime[1] > 12 || dateTime[2] < 1) {
        return false;
      }
      boolean leap = IsLeapYear(dateTime[0]);
      if (dateTime[1] == 4 || dateTime[1] == 6 || dateTime[1] == 9 ||
        dateTime[1] == 11) {
        if (dateTime[2] > 30) {
          return false;
        }
      } else if (dateTime[1] == 2) {
        if (dateTime[2] > (leap ? 29 : 28)) {
          return false;
        }
      } else {
        if (dateTime[2] > 31) {
          return false;
        }
      }
      return !(dateTime[3] < 0 || dateTime[4] < 0 || dateTime[5] < 0 ||
          dateTime[3] >= 24 || dateTime[4] >= 60 || dateTime[5] >= 61 ||
          dateTime[6] < 0 ||
          dateTime[6] >= 1000000000 || dateTime[7] <= -1440 ||
          dateTime[7] >= 1440);
    }

    private static boolean IsLeapYear(int yr) {
      yr %= 400;
      if (yr < 0) {
        yr += 400;
      }
      return (((yr % 4) == 0) && ((yr % 100) != 0)) || ((yr % 400) == 0);
    }

    public static void ParseAtomDateTimeString(
      String str,
      EInteger[] bigYearArray,
      int[] lf) {
      int[] d = ParseAtomDateTimeString(str);
      bigYearArray[0] = EInteger.FromInt32(d[0]);
      System.arraycopy(d, 1, lf, 0, 7);
    }

    private static int[] ParseAtomDateTimeString(String str) {
      boolean bad = false;
      if (str.length() < 19) {
        throw new IllegalArgumentException("Invalid date/time");
      }
      for (int i = 0; i < 19 && !bad; ++i) {
        if (i == 4 || i == 7) {
          bad |= str.charAt(i) != '-';
        } else if (i == 13 || i == 16) {
          bad |= str.charAt(i) != ':';
        } else if (i == 10) {
          bad |= str.charAt(i) != 'T';
          /*lowercase t not used to separate date/time,
          following RFC 4287 sec. 3.3*/ } else {
          bad |= str.charAt(i) < '0' || str.charAt(i) >
            '9';
        }
      }
      if (bad) {
        throw new IllegalArgumentException("Invalid date/time");
      }
      int year = ((str.charAt(0) - '0') * 1000) + ((str.charAt(1) - '0') * 100) +
        ((str.charAt(2) - '0') * 10) + (str.charAt(3) - '0');
      int month = ((str.charAt(5) - '0') * 10) + (str.charAt(6) - '0');
      int day = ((str.charAt(8) - '0') * 10) + (str.charAt(9) - '0');
      int hour = ((str.charAt(11) - '0') * 10) + (str.charAt(12) - '0');
      int minute = ((str.charAt(14) - '0') * 10) + (str.charAt(15) - '0');
      int second = ((str.charAt(17) - '0') * 10) + (str.charAt(18) - '0');
      int index = 19;
      int nanoSeconds = 0;
      if (index <= str.length() && str.charAt(index) == '.') {
        int icount = 0;
        ++index;
        while (index < str.length()) {
          if (str.charAt(index) < '0' || str.charAt(index) > '9') {
            break;
          }
          if (icount < 9) {
            nanoSeconds = (nanoSeconds * 10) + (str.charAt(index) - '0');
            ++icount;
          }
          ++index;
        }
        while (icount < 9) {
          nanoSeconds *= 10;
          ++icount;
        }
      }
      int utcToLocal = 0;
      if (index + 1 == str.length() && str.charAt(index) == 'Z') {
        /*lowercase z not used to indicate UTC,
          following RFC 4287 sec. 3.3*/
        utcToLocal = 0;
      } else if (index + 6 == str.length()) {
        bad = false;
        for (int i = 0; i < 6 && !bad; ++i) {
          if (i == 0) {
            bad |= str.charAt(index + i) != '-' && str.charAt(index + i) != '+';
          } else if (i == 3) {
            bad |= str.charAt(index + i) != ':';
          } else {
            bad |= str.charAt(index + i) < '0' || str.charAt(index + i) > '9';
          }
        }
        if (bad) {
          throw new IllegalArgumentException("Invalid date/time");
        }
        boolean neg = str.charAt(index) == '-';
        int tzhour = ((str.charAt(index + 1) - '0') * 10) + (str.charAt(index + 2) - '0');
        int tzminute = ((str.charAt(index + 4) - '0') * 10) + (str.charAt(index + 5) - '0');
        if (tzminute >= 60) {
          throw new IllegalArgumentException("Invalid date/time");
        }
        utcToLocal = ((neg ? -1 : 1) * (tzhour * 60)) + tzminute;
      } else {
        throw new IllegalArgumentException("Invalid date/time");
      }
      int[] dt = {
        year, month, day, hour, minute, second,
        nanoSeconds, utcToLocal,
      };
      if (!IsValidDateTime(dt)) {
        throw new IllegalArgumentException("Invalid date/time");
      }
      return dt;
    }

    public static String ToAtomDateTimeString(
      EInteger bigYear,
      int[] lesserFields) {
      if (lesserFields[6] != 0) {
        throw new UnsupportedOperationException(
          "Local time offsets not supported");
      }
      int smallYear = bigYear.ToInt32Checked();
      if (smallYear < 0) {
        throw new IllegalArgumentException("year(" + smallYear +
          ") is not greater or equal to 0");
      }
      if (smallYear > 9999) {
        throw new IllegalArgumentException("year(" + smallYear +
          ") is not less or equal to 9999");
      }
      int month = lesserFields[0];
      int day = lesserFields[1];
      int hour = lesserFields[2];
      int minute = lesserFields[3];
      int second = lesserFields[4];
      int fracSeconds = lesserFields[5];
      char[] charbuf = new char[32];
      charbuf[0] = (char)('0' + ((smallYear / 1000) % 10));
      charbuf[1] = (char)('0' + ((smallYear / 100) % 10));
      charbuf[2] = (char)('0' + ((smallYear / 10) % 10));
      charbuf[3] = (char)('0' + (smallYear % 10));
      charbuf[4] = '-';
      charbuf[5] = (char)('0' + ((month / 10) % 10));
      charbuf[6] = (char)('0' + (month % 10));
      charbuf[7] = '-';
      charbuf[8] = (char)('0' + ((day / 10) % 10));
      charbuf[9] = (char)('0' + (day % 10));
      charbuf[10] = 'T';
      charbuf[11] = (char)('0' + ((hour / 10) % 10));
      charbuf[12] = (char)('0' + (hour % 10));
      charbuf[13] = ':';
      charbuf[14] = (char)('0' + ((minute / 10) % 10));
      charbuf[15] = (char)('0' + (minute % 10));
      charbuf[16] = ':';
      charbuf[17] = (char)('0' + ((second / 10) % 10));
      charbuf[18] = (char)('0' + (second % 10));
      int charbufLength = 19;
      if (fracSeconds > 0) {
        charbuf[19] = '.';
        ++charbufLength;
        int digitdiv = 100000000;
        int index = 20;
        while (digitdiv > 0 && fracSeconds != 0) {
          int digit = (fracSeconds / digitdiv) % 10;
          fracSeconds -= digit * digitdiv;
          charbuf[index++] = (char)('0' + digit);
          ++charbufLength;
          digitdiv /= 10;
        }
        charbuf[index] = 'Z';
        ++charbufLength;
      } else {
        charbuf[19] = 'Z';
        ++charbufLength;
      }
      return new String(charbuf, 0, charbufLength);
    }

    public static EInteger BigIntegerFromDouble(double dbl) {
      long lvalue = Double.doubleToRawLongBits(dbl);
      int value0 = ((int)(lvalue & 0xffffffffL));
      int value1 = ((int)((lvalue >> 32) & 0xffffffffL));
      int floatExponent = (int)((value1 >> 20) & 0x7ff);
      boolean neg = (value1 >> 31) != 0;
      if (floatExponent == 2047) {
        throw new ArithmeticException("Value is infinity or NaN");
      }
      value1 &= 0xfffff; // Mask out the exponent and sign
      if (floatExponent == 0) {
        ++floatExponent;
      } else {
        value1 |= 0x100000;
      }
      if ((value1 | value0) != 0) {
        while ((value0 & 1) == 0) {
          value0 >>= 1;
          value0 &= 0x7fffffff;
          value0 = (value0 | (value1 << 31));
          value1 >>= 1;
          ++floatExponent;
        }
      }
      floatExponent -= 1075;
      byte[] bytes = new byte[9];
      EInteger bigmantissa;
      bytes[0] = (byte)(value0 & 0xff);
      bytes[1] = (byte)((value0 >> 8) & 0xff);
      bytes[2] = (byte)((value0 >> 16) & 0xff);
      bytes[3] = (byte)((value0 >> 24) & 0xff);
      bytes[4] = (byte)(value1 & 0xff);
      bytes[5] = (byte)((value1 >> 8) & 0xff);
      bytes[6] = (byte)((value1 >> 16) & 0xff);
      bytes[7] = (byte)((value1 >> 24) & 0xff);
      bytes[8] = (byte)0;
      bigmantissa = EInteger.FromBytes(bytes, true);
      if (floatExponent == 0) {
        if (neg) {
          bigmantissa = bigmantissa.Negate();
        }
        return bigmantissa;
      }
      if (floatExponent > 0) {
        // Value is an integer
        bigmantissa = bigmantissa.ShiftLeft(floatExponent);
        if (neg) {
          bigmantissa=(bigmantissa).Negate();
        }
        return bigmantissa;
      } else {
        // Value has a fractional part
        int exp = -floatExponent;
        bigmantissa = bigmantissa.ShiftRight(exp);
        if (neg) {
          bigmantissa=(bigmantissa).Negate();
        }
        return bigmantissa;
      }
    }

    private static int RoundedShift(long mant, int shift) {
      long mask = (1L << shift) - 1;
      long half = 1L << (shift - 1);
      long shifted = mant >> shift;
      long masked = mant & mask;
      return (masked > half || (masked == half && (shifted & 1L) != 0)) ?
        ((int)shifted) + 1 : ((int)shifted);
    }

    private static int RoundedShift(int mant, int shift) {
      int mask = (1 << shift) - 1;
      int half = 1 << (shift - 1);
      int shifted = mant >> shift;
      int masked = mant & mask;
      return (masked > half || (masked == half && (shifted & 1) != 0)) ?
        shifted + 1 : shifted;
    }

    public static int DoubleToHalfPrecisionIfSameValue(long bits) {
      int exp = ((int)((bits >> 52) & 0x7ffL));
      long mant = bits & 0xfffffffffffffL;
      int sign = ((int)(bits >> 48)) & (1 << 15);
      int sexp = exp - 1008;
      // System.out.println("bits={0:X8}, exp=" + exp + " sexp=" + (sexp));
      if (exp == 2047) { // Infinity and NaN
        int newmant = ((int)(mant >> 42));
        return ((mant & ((1L << 42) - 1)) == 0) ? (sign | 0x7c00 | newmant) :
          -1;
        } else if (sexp >= 31) { // overflow
        return -1;
      } else if (sexp < -10) { // underflow
        return -1;
      } else if (sexp > 0) { // normal
        return ((mant & ((1L << 42) - 1)) == 0) ? (sign | (sexp << 10) |
            RoundedShift(mant, 42)) : -1;
      } else { // subnormal and zero
        return ((mant & ((1L << (42 - (sexp - 1))) - 1)) == 0) ? (sign |
            RoundedShift(mant | (1L << 52), 42 - (sexp - 1))) : -1;
      }
    }

    public static boolean DoubleRetainsSameValueInSingle(long bits) {
      int exp = ((int)((bits >> 52) & 0x7ffL));
      long mant = bits & 0xfffffffffffffL;
      int sexp = exp - 896;
      // System.out.println("sng mant={0:X8}, exp=" + exp + " sexp=" + (sexp));
      if (exp == 2047) { // Infinity and NaN
        return (mant & ((1L << 29) - 1)) == 0;
      } else if (sexp < -23 || sexp >= 255) { // underflow or overflow
        return false;
      } else if (sexp > 0) { // normal
        return (mant & ((1L << 29) - 1)) == 0;
      } else { // subnormal and zero
        return (mant & ((1L << (29 - (sexp - 1))) - 1)) == 0;
      }
    }

    // NOTE: Rounds to nearest, ties to even
    public static int SingleToRoundedHalfPrecision(int bits) {
      int exp = ((int)((bits >> 23) & 0xff));
      int mant = bits & 0x7fffff;
      int sign = (bits >> 16) & (1 << 15);
      int sexp = exp - 112;
      if (exp == 255) { // Infinity and NaN
        int newmant = ((int)(mant >> 13));
        return (mant != 0 && newmant == 0) ?
          // signaling NaN truncated to have mantissa 0
          (sign | 0x7c01) : (sign | 0x7c00 | newmant);
        } else if (sexp >= 31) { // overflow
        return sign | 0x7c00;
      } else if (sexp < -10) { // underflow
        return sign;
      } else if (sexp > 0) { // normal
        return sign | (sexp << 10) | RoundedShift(mant, 13);
      } else { // subnormal and zero
        return sign | RoundedShift(mant | (1 << 23), 13 - (sexp - 1));
      }
    }

    // NOTE: Rounds to nearest, ties to even
    public static int DoubleToRoundedHalfPrecision(long bits) {
      int exp = ((int)((bits >> 52) & 0x7ffL));
      long mant = bits & 0xfffffffffffffL;
      int sign = ((int)(bits >> 48)) & (1 << 15);
      int sexp = exp - 1008;
      if (exp == 2047) { // Infinity and NaN
        int newmant = ((int)(mant >> 42));
        return (mant != 0 && newmant == 0) ?
          // signaling NaN truncated to have mantissa 0
          (sign | 0x7c01) : (sign | 0x7c00 | newmant);
        } else if (sexp >= 31) { // overflow
        return sign | 0x7c00;
      } else if (sexp < -10) { // underflow
        return sign;
      } else if (sexp > 0) { // normal
        return sign | (sexp << 10) | RoundedShift(mant, 42);
      } else { // subnormal and zero
        return sign | RoundedShift(mant | (1L << 52), 42 - (sexp - 1));
      }
    }

    // NOTE: Rounds to nearest, ties to even
    public static int DoubleToRoundedSinglePrecision(long bits) {
      int exp = ((int)((bits >> 52) & 0x7ffL));
      long mant = bits & 0xfffffffffffffL;
      int sign = ((int)(bits >> 32)) & (1 << 31);
      int sexp = exp - 896;
      if (exp == 2047) { // Infinity and NaN
        int newmant = ((int)(mant >> 29));
        return (mant != 0 && newmant == 0) ?
          // signaling NaN truncated to have mantissa 0
          (sign | 0x7f800001) : (sign | 0x7f800000 | newmant);
        } else if (sexp >= 255) { // overflow
        return sign | 0x7f800000;
      } else if (sexp < -23) { // underflow
        return sign;
      } else if (sexp > 0) { // normal
        return sign | (sexp << 23) | RoundedShift(mant, 29);
      } else { // subnormal and zero
        return sign | RoundedShift(mant | (1L << 52), 29 - (sexp - 1));
      }
    }

    public static int SingleToHalfPrecisionIfSameValue(float f) {
      int bits = Float.floatToRawIntBits(f);
      int exp = (bits >> 23) & 0xff;
      int mant = bits & 0x7fffff;
      int sign = (bits >> 16) & 0x8000;
      if (exp == 255) { // Infinity and NaN
        return (bits & 0x1fff) == 0 ? sign + 0x7c00 + (mant >> 13) : -1;
      } else if (exp == 0) { // Subnormal
        return (bits & 0x1fff) == 0 ? sign + (mant >> 13) : -1;
      }
      if (exp <= 102 || exp >= 143) { // Overflow or underflow
        return -1;
      } else if (exp <= 112) { // Subnormal
        int shift = 126 - exp;
        return (bits & ((1 << shift) - 1)) == 0 ? sign +
          (1024 >> (145 - exp)) + (mant >> shift) : -1;
        } else {
        return (bits & 0x1fff) == 0 ? sign + ((exp - 112) << 10) +
          (mant >> 13) : -1;
      }
    }

    public static long SingleToDoublePrecision(int bits) {
      long negvalue = (long)((bits >> 31) & 1) << 63;
      int exp = (bits >> 23) & 0xff;
      int mant = bits & 0x7fffff;
      long value = 0;
      if (exp == 255) {
        value = 0x7ff0000000000000L | ((long)mant << 29) | negvalue;
      } else if (exp == 0) {
        if (mant == 0) {
          value = negvalue;
        } else {
          ++exp;
          while (mant < 0x800000) {
            mant <<= 1;
            --exp;
          }
          value = ((long)(exp + 896) << 52) | ((long)(mant & 0x7fffff) <<
              29) | negvalue;
        }
      } else {
        value = ((long)(exp + 896) << 52) | ((long)mant << 29) | negvalue;
      }
      return value;
    }

    public static long HalfToDoublePrecision(int bits) {
      long negvalue = (long)(bits & 0x8000) << 48;
      int exp = (bits >> 10) & 31;
      int mant = bits & 0x3ff;
      long value = 0;
      if (exp == 31) {
        value = 0x7ff0000000000000L | ((long)mant << 42) | negvalue;
      } else if (exp == 0) {
        if (mant == 0) {
          value = negvalue;
        } else {
          ++exp;
          while (mant < 0x400) {
            mant <<= 1;
            --exp;
          }
          value = ((long)(exp + 1008) << 52) | ((long)(mant & 0x3ff) << 42) |
            negvalue;
        }
      } else {
        value = ((long)(exp + 1008) << 52) | ((long)mant << 42) | negvalue;
      }
      return value;
    }
  }
