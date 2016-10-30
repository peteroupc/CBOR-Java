package com.upokecenter.cbor;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*; import com.upokecenter.numbers.*;

    /**
     * Contains methods useful for reading and writing data, with a focus on CBOR.
     */
  public final class CBORDataUtilities {
private CBORDataUtilities() {
}
    private static final int MaxSafeInt = 214748363;

    /**
     * Parses a number whose format follows the JSON specification. See
     * #ParseJSONNumber(String, integersOnly, parseOnly) for more
     * information.
     * @param str A string to parse. The string is not allowed to contain white
     * space characters, including spaces.
     * @return A CBOR object that represents the parsed number. Returns positive
     * zero if the number is a zero that starts with a minus sign (such as
     * "-0" or "-0.0"). Returns null if the parsing fails, including if the
     * string is null or empty.
     */
    public static CBORObject ParseJSONNumber(String str) {
      return ParseJSONNumber(str, false, false);
    }

    /**
     * Parses a number whose format follows the JSON specification (RFC 7159).
     * Roughly speaking, a valid number consists of an optional minus sign,
     * one or more basic digits (starting with 1 to 9 unless the only digit
     * is 0), an optional decimal point (".", full stop) with one or more
     * basic digits, and an optional letter E or e with an optional plus or
     * minus sign and one or more basic digits (the exponent).
     * @param str A string to parse. The string is not allowed to contain white
     * space characters, including spaces.
     * @param integersOnly If true, no decimal points or exponents are allowed in
     * the string.
     * @param positiveOnly If true, only positive numbers are allowed (the leading
     * minus is disallowed).
     * @return A CBOR object that represents the parsed number. Returns positive
     * zero if the number is a zero that starts with a minus sign (such as
     * "-0" or "-0.0"). Returns null if the parsing fails, including if the
     * string is null or empty.
     */
    public static CBORObject ParseJSONNumber(
      String str,
      boolean integersOnly,
      boolean positiveOnly) {
      return ParseJSONNumber(str, integersOnly, positiveOnly, false);
    }

    /**
     * Parses a number whose format follows the JSON specification (RFC 7159).
     * Roughly speaking, a valid number consists of an optional minus sign,
     * one or more basic digits (starting with 1 to 9 unless the only digit
     * is 0), an optional decimal point (".", full stop) with one or more
     * basic digits, and an optional letter E or e with an optional plus or
     * minus sign and one or more basic digits (the exponent).
     * @param str A string to parse. The string is not allowed to contain white
     * space characters, including spaces.
     * @param integersOnly If true, no decimal points or exponents are allowed in
     * the string.
     * @param positiveOnly If true, only positive numbers are allowed (the leading
     * minus is disallowed).
     * @param preserveNegativeZero If true, returns positive zero if the number is
     * a zero that starts with a minus sign (such as "-0" or "-0.0").
     * Otherwise, returns negative zero in this case.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the string is null or empty.
     */
    public static CBORObject ParseJSONNumber(
      String str,
      boolean integersOnly,
      boolean positiveOnly,
      boolean preserveNegativeZero) {
      if (((str) == null || (str).length() == 0)) {
        return null;
      }
      int offset = 0;
      boolean negative = false;
      if (str.charAt(0) == '-' && !positiveOnly) {
        negative = true;
        ++offset;
      }
      int mantInt = 0;
      FastInteger2 mant = null;
      int mantBuffer = 0;
      int mantBufferMult = 1;
      int expBuffer = 0;
      int expBufferMult = 1;
      boolean haveDecimalPoint = false;
      boolean haveDigits = false;
      boolean haveDigitsAfterDecimal = false;
      boolean haveExponent = false;
      int newScaleInt = 0;
      FastInteger2 newScale = null;
      int i = offset;
      // Ordinary number
      if (i < str.length() && str.charAt(i) == '0') {
        ++i;
        haveDigits = true;
        if (i == str.length()) {
          if (preserveNegativeZero && negative) {
            return CBORObject.FromObject(
             EDecimal.NegativeZero);
          }
          return CBORObject.FromObject(0);
        }
        if (!integersOnly) {
          if (str.charAt(i) == '.') {
            haveDecimalPoint = true;
            ++i;
          } else if (str.charAt(i) == 'E' || str.charAt(i) == 'e') {
            haveExponent = true;
          } else {
            return null;
          }
        } else {
          return null;
        }
      }
      for (; i < str.length(); ++i) {
        if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
          int thisdigit = (int)(str.charAt(i) - '0');
          if (mantInt > MaxSafeInt) {
            if (mant == null) {
              mant = new FastInteger2(mantInt);
              mantBuffer = thisdigit;
              mantBufferMult = 10;
            } else {
              if (mantBufferMult >= 1000000000) {
                mant.Multiply(mantBufferMult).AddInt(mantBuffer);
                mantBuffer = thisdigit;
                mantBufferMult = 10;
              } else {
                mantBufferMult *= 10;
                mantBuffer = (mantBuffer << 3) + (mantBuffer << 1);
                mantBuffer += thisdigit;
              }
            }
          } else {
            mantInt *= 10;
            mantInt += thisdigit;
          }
          haveDigits = true;
          if (haveDecimalPoint) {
            haveDigitsAfterDecimal = true;
            if (newScaleInt == Integer.MIN_VALUE) {
              newScale = (newScale == null) ? ((new FastInteger2(newScaleInt))) : newScale;
              newScale.AddInt(-1);
            } else {
              --newScaleInt;
            }
          }
        } else if (!integersOnly && str.charAt(i) == '.') {
          if (!haveDigits) {
            // no digits before the decimal point
            return null;
          }
          if (haveDecimalPoint) {
            return null;
          }
          haveDecimalPoint = true;
        } else if (!integersOnly && (str.charAt(i) == 'E' || str.charAt(i) == 'e')) {
          haveExponent = true;
          ++i;
          break;
        } else {
          return null;
        }
      }
      if (!haveDigits || (haveDecimalPoint && !haveDigitsAfterDecimal)) {
        return null;
      }
      if (mant != null && (mantBufferMult != 1 || mantBuffer != 0)) {
        mant.Multiply(mantBufferMult).AddInt(mantBuffer);
      }
      if (haveExponent) {
        FastInteger2 exp = null;
        int expInt = 0;
        offset = 1;
        haveDigits = false;
        if (i == str.length()) {
          return null;
        }
        if (str.charAt(i) == '+' || str.charAt(i) == '-') {
          if (str.charAt(i) == '-') {
            offset = -1;
          }
          ++i;
        }
        for (; i < str.length(); ++i) {
          if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            haveDigits = true;
            int thisdigit = (int)(str.charAt(i) - '0');
            if (expInt > MaxSafeInt) {
              if (exp == null) {
                exp = new FastInteger2(expInt);
                expBuffer = thisdigit;
                expBufferMult = 10;
              } else {
                if (expBufferMult >= 1000000000) {
                  exp.Multiply(expBufferMult).AddInt(expBuffer);
                  expBuffer = thisdigit;
                  expBufferMult = 10;
                } else {
                  // multiply expBufferMult and expBuffer each by 10
                  expBufferMult = (expBufferMult << 3) + (expBufferMult << 1);
                  expBuffer = (expBuffer << 3) + (expBuffer << 1);
                  expBuffer += thisdigit;
                }
              }
            } else {
              expInt *= 10;
              expInt += thisdigit;
            }
          } else {
            return null;
          }
        }
        if (!haveDigits) {
          return null;
        }
        if (exp != null && (expBufferMult != 1 || expBuffer != 0)) {
          exp.Multiply(expBufferMult).AddInt(expBuffer);
        }
        if (offset >= 0 && newScaleInt == 0 && newScale == null && exp ==
            null) {
          newScaleInt = expInt;
        } else if (exp == null) {
          newScale = (newScale == null) ? ((new FastInteger2(newScaleInt))) : newScale;
          if (offset < 0) {
            newScale.SubtractInt(expInt);
          } else if (expInt != 0) {
            newScale.AddInt(expInt);
          }
        } else {
          newScale = (newScale == null) ? ((new FastInteger2(newScaleInt))) : newScale;
          if (offset < 0) {
            newScale.Subtract(exp);
          } else {
            newScale.Add(exp);
          }
        }
      }
      if (i != str.length()) {
        // End of the String wasn't reached, so isn't a number
        return null;
      }
      if ((newScale == null && newScaleInt == 0) || (newScale != null &&
                    newScale.signum() == 0)) {
        // No fractional part
        if (mant != null && mant.CanFitInInt32()) {
          mantInt = mant.AsInt32();
          mant = null;
        }
        if (mant == null) {
          // NOTE: mantInt can only be 0 or greater, so overflow is impossible

          if (negative) {
            mantInt = -mantInt;
            if (preserveNegativeZero && mantInt == 0) {
              return CBORObject.FromObject(
                EDecimal.NegativeZero);
            }
          }
          return CBORObject.FromObject(mantInt);
        } else {
          EInteger bigmant2 = mant.AsBigInteger();
          if (negative) {
            bigmant2=(bigmant2).Negate();
          }
          return CBORObject.FromObject(bigmant2);
        }
      } else {
        EInteger bigmant = (mant == null) ? (EInteger.FromInt32(mantInt)) :
          mant.AsBigInteger();
        EInteger bigexp = (newScale == null) ? (EInteger.FromInt32(newScaleInt)) :
          newScale.AsBigInteger();
        if (negative) {
          bigmant=(bigmant).Negate();
        }
        EDecimal edec;
        edec = EDecimal.Create(
          bigmant,
          bigexp);
        if (negative && preserveNegativeZero && bigmant.isZero()) {
          EDecimal negzero = EDecimal.NegativeZero;
          negzero = negzero.Quantize(bigexp, null);
          edec = negzero.Subtract(edec);
        }
        return CBORObject.FromObject(edec);
      }
    }
  }
