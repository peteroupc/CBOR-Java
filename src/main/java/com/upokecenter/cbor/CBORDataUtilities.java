package com.upokecenter.cbor;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import java.util.*;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  /**
   * Contains methods useful for reading and writing data, with a focus on CBOR.
   */
  public final class CBORDataUtilities {
private CBORDataUtilities() {
}
    private static final String HexAlphabet = "0123456789ABCDEF";

    static String ToStringHelper(CBORObject obj, int depth) {
      StringBuilder sb = null;
      String simvalue = null;
      CBORType type = obj.getType();
      CBORObject curobject;
      if (obj.isTagged()) {
        if (sb == null) {
          if (type == CBORType.TextString) {
            // The default capacity of StringBuilder may be too small
            // for many strings, so set a suggested capacity
            // explicitly
            String str = obj.AsString();
            sb = new StringBuilder(Math.min(str.length(), 4096) + 16);
          } else {
            sb = new StringBuilder();
          }
        }
        // Append opening tags if needed
        curobject = obj;
        while (curobject.isTagged()) {
          EInteger ei = curobject.getMostOuterTag();
          sb.append(ei.toString());
          sb.append('(');
          curobject = curobject.UntagOne();
        }
      }
      switch (type) {
        case SimpleValue:
          sb = (sb == null) ? (new StringBuilder()) : sb;
          if (obj.isUndefined()) {
            sb.append("undefined");
          } else if (obj.isNull()) {
            sb.append("null");
          } else {
            sb.append("simple(");
            int thisItemInt = obj.getSimpleValue();
            char c;
            if (thisItemInt >= 100) {
              // NOTE: '0'-'9' have ASCII code 0x30-0x39
              c = (char)(0x30 + ((thisItemInt / 100) % 10));
              sb.append(c);
            }
            if (thisItemInt >= 10) {
              c = (char)(0x30 + ((thisItemInt / 10) % 10));
              sb.append(c);
              c = (char)(0x30 + (thisItemInt % 10));
            } else {
              c = (char)(0x30 + thisItemInt);
            }
            sb.append(c);
            sb.append(")");
          }
          break;
        case Boolean:
        case Integer:
          simvalue = obj.Untag().ToJSONString();
          if (sb == null) {
            return simvalue;
          }
          sb.append(simvalue);
          break;
        case FloatingPoint: {
          double f = obj.AsDoubleValue();
          simvalue = ((f)==Double.NEGATIVE_INFINITY) ? "-Infinity" :
(((f)==Double.POSITIVE_INFINITY) ? "Infinity" : (Double.isNaN(f) ?

                "NaN" : obj.Untag().ToJSONString()));
          if (sb == null) {
            return simvalue;
          }
          sb.append(simvalue);
          break;
        }
        case ByteString: {
          sb = (sb == null) ? (new StringBuilder()) : sb;
          sb.append("h'");
          byte[] data = obj.GetByteString();
          int length = data.length;
          for (int i = 0; i < length; ++i) {
            sb.append(HexAlphabet.charAt((data[i] >> 4) & 15));
            sb.append(HexAlphabet.charAt(data[i] & 15));
          }
          sb.append("'");
          break;
        }
        case TextString: {
          if (sb == null) {
            return "\"" + obj.AsString() + "\"";
          }
          sb.append('\"');
          sb.append(obj.AsString());
          sb.append('\"');
          break;
        }
        case Array: {
          sb = (sb == null) ? (new StringBuilder()) : sb;
          boolean first = true;
          sb.append("[");
          if (depth >= 50) {
            sb.append("...");
          } else {
            for (int i = 0; i < obj.size(); ++i) {
              if (!first) {
                sb.append(", ");
              }
              sb.append(ToStringHelper(obj.get(i), depth + 1));
              first = false;
            }
          }
          sb.append("]");
          break;
        }
        case Map: {
          sb = (sb == null) ? (new StringBuilder()) : sb;
          boolean first = true;
          sb.append("{");
          if (depth >= 50) {
            sb.append("...");
          } else {
            Collection<Map.Entry<CBORObject, CBORObject>> entries =
              obj.getEntries();
            for (Map.Entry<CBORObject, CBORObject> entry : entries) {
              CBORObject key = entry.getKey();
              CBORObject value = entry.getValue();
              if (!first) {
                sb.append(", ");
              }
              sb.append(ToStringHelper(key, depth + 1));
              sb.append(": ");
              sb.append(ToStringHelper(value, depth + 1));
              first = false;
            }
          }
          sb.append("}");
          break;
        }
        default: {
          sb = (sb == null) ? (new StringBuilder()) : sb;
          sb.append("???");
          break;
        }
      }
      // Append closing tags if needed
      curobject = obj;
      while (curobject.isTagged()) {
        sb.append(')');
        curobject = curobject.UntagOne();
      }
      return sb.toString();
    }

    private static final int MaxSafeInt = 214748363;
    private static final JSONOptions DefaultOptions =
        new JSONOptions("");
    private static final JSONOptions PreserveNegZeroNo =
        new JSONOptions("preservenegativezero=0");
    private static final JSONOptions PreserveNegZeroYes =
        new JSONOptions("preservenegativezero=1");

    /**
     * Parses a number whose format follows the JSON specification. The method uses
     * a JSONOptions with all default properties except for a
     * PreserveNegativeZero property of false.
     * @param str A text string to parse as a JSON string.
     * @return A CBOR object that represents the parsed number. Returns positive
     * zero if the number is a zero that starts with a minus sign (such as
     *  "-0" or "-0.0"). Returns null if the parsing fails, including if the
     * string is null or empty.
     */
    public static CBORObject ParseJSONNumber(String str) {
      // TODO: Preserve negative zeros in next major version
      return ParseJSONNumber(str, PreserveNegZeroNo);
    }

    /**
     * Parses a number whose format follows the JSON specification (RFC 8259). The
     * method uses a JSONOptions with all default properties except for a
     * PreserveNegativeZero property of false.<p>Roughly speaking, a valid
     * JSON number consists of an optional minus sign, one or more basic
     * digits (starting with 1 to 9 unless there is only one digit and that
     *  digit is 0), an optional decimal point (".", full stop) with one or
     * more basic digits, and an optional letter E or e with an optional
     * plus or minus sign and one or more basic digits (the exponent). A
     * string representing a valid JSON number is not allowed to contain
     * white space characters, including spaces.</p>
     * @param str A text string to parse as a JSON number.
     * @param integersOnly If true, no decimal points or exponents are allowed in
     * the string. The default is false.
     * @param positiveOnly If true, only positive numbers are allowed (the leading
     * minus is disallowed). The default is false.
     * @return A CBOR object that represents the parsed number. Returns positive
     * zero if the number is a zero that starts with a minus sign (such as
     *  "-0" or "-0.0"). Returns null if the parsing fails, including if the
     * string is null or empty.
     * @deprecated Call the one-argument version of this method instead. If this\u0020method
 * call used positiveOnly = true, check that the String\u0020does
 * not\u0020begin\u0020with '-' before calling that version. If this method
 * call used\u0020integersOnly\u0020 = true, check that the String does not
 * contain '.', 'E', or 'e'\u0020before\u0020calling that version.
 */
@Deprecated
    public static CBORObject ParseJSONNumber(
      String str,
      boolean integersOnly,
      boolean positiveOnly) {
      if (((str) == null || (str).length() == 0)) {
        return null;
      }
      if (integersOnly) {
        for (int i = 0; i < str.length(); ++i) {
if (str.charAt(i) >= '0' && str.charAt(i) <= '9' && (i > 0 || str.charAt(i) != '-')) {
  return null;
}
}
      }
      return (positiveOnly && str.charAt(0) == '-') ? null :
         ParseJSONNumber(
           str,
           0,
           str.length(),
           PreserveNegZeroNo);
    }

    /**
     * Parses a number whose format follows the JSON specification (RFC
     * 8259).<p>Roughly speaking, a valid JSON number consists of an
     * optional minus sign, one or more basic digits (starting with 1 to 9
     * unless there is only one digit and that digit is 0), an optional
     *  decimal point (".", full stop) with one or more basic digits, and an
     * optional letter E or e with an optional plus or minus sign and one
     * or more basic digits (the exponent). A string representing a valid
     * JSON number is not allowed to contain white space characters,
     * including spaces.</p>
     * @param str A text string to parse as a JSON number.
     * @param integersOnly If true, no decimal points or exponents are allowed in
     * the string. The default is false.
     * @param positiveOnly If true, the leading minus is disallowed in the string.
     * The default is false.
     * @param preserveNegativeZero If true, returns positive zero if the number is
     *  a zero that starts with a minus sign (such as "-0" or "-0.0").
     * Otherwise, returns negative zero in this case. The default is false.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the string is null or empty.
     */
    public static CBORObject ParseJSONNumber(
      String str,
      boolean integersOnly,
      boolean positiveOnly,
      boolean preserveNegativeZero) {
      // TODO: Deprecate integersOnly?
      // TODO: Deprecate this method eventually?
      if (((str) == null || (str).length() == 0)) {
        return null;
      }
      if (integersOnly) {
        for (int i = 0; i < str.length(); ++i) {
if (str.charAt(i) >= '0' && str.charAt(i) <= '9' && (i > 0 || str.charAt(i) != '-')) {
  return null;
}
}
      }
      JSONOptions jo = preserveNegativeZero ? PreserveNegZeroYes :
         PreserveNegZeroNo;
      return (positiveOnly && str.charAt(0) == '-') ? null :
         ParseJSONNumber(str,
           0,
           str.length(),
           jo);
    }

    /**
     * Parses a number whose format follows the JSON specification (RFC 8259) and
     * converts that number to a CBOR object.<p>Roughly speaking, a valid
     * JSON number consists of an optional minus sign, one or more basic
     * digits (starting with 1 to 9 unless there is only one digit and that
     *  digit is 0), an optional decimal point (".", full stop) with one or
     * more basic digits, and an optional letter E or e with an optional
     * plus or minus sign and one or more basic digits (the exponent). A
     * string representing a valid JSON number is not allowed to contain
     * white space characters, including spaces.</p>
     * @param str A text string to parse as a JSON number.
     * @param options An object containing options to control how JSON numbers are
     * decoded to CBOR objects. Can be null, in which case a JSONOptions
     * object with all default properties is used instead.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the string is null or empty.
     */
    public static CBORObject ParseJSONNumber(
      String str,
      JSONOptions options) {
      return ((str) == null || (str).length() == 0) ? null :
         ParseJSONNumber(str,
           0,
           str.length(),
           options);
    }

private static EInteger FastEIntegerFromString(String str, int index, int
endIndex) {
  if (endIndex - index > 32) {
     // DebugUtility.Log("FastEInteger " + index + " " + endIndex + " len=" +
    // (endIndex - index));
    int midIndex = index + (endIndex - index) / 2;
    EInteger eia = FastEIntegerFromString(str, index, midIndex);
    EInteger eib = FastEIntegerFromString(str, midIndex, endIndex);
    EInteger mult = EInteger.FromInt32(10).Pow(endIndex - midIndex);
    return eia.Multiply(mult).Add(eib);
  } else {
    return EInteger.FromSubstring(str, index, endIndex);
  }
}

    /**
     * Parses a number whose format follows the JSON specification (RFC 8259) and
     * converts that number to a CBOR object.<p>Roughly speaking, a valid
     * JSON number consists of an optional minus sign, one or more basic
     * digits (starting with 1 to 9 unless there is only one digit and that
     *  digit is 0), an optional decimal point (".", full stop) with one or
     * more basic digits, and an optional letter E or e with an optional
     * plus or minus sign and one or more basic digits (the exponent). A
     * string representing a valid JSON number is not allowed to contain
     * white space characters, including spaces.</p>
     * @param str A text string to parse as a JSON number.
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code str} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @param options An object containing options to control how JSON numbers are
     * decoded to CBOR objects. Can be null, in which case a JSONOptions
     * object with all default properties is used instead.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the string is null or empty or {@code
     * count} is 0 or less.
     * @throws IllegalArgumentException Either {@code offset} or {@code count} is less
     * than 0 or greater than {@code str} 's length, or {@code str} 's
     * length minus {@code offset} is less than {@code count}.
     * @throws NullPointerException The parameter {@code str} is null.
     */
    public static CBORObject ParseJSONNumber(
      String str,
      int offset,
      int count,
      JSONOptions options) {
      if (((str) == null || (str).length() == 0) || count <= 0) {
        return null;
      }
      if (offset < 0 || offset > str.length()) {
        return null;
      }
      if (count > str.length() || str.length() - offset < count) {
        return null;
      }
      JSONOptions opt = (options == null) ? (DefaultOptions) : options;
      boolean preserveNegativeZero = options.getPreserveNegativeZero();
      JSONOptions.ConversionMode kind = options.getNumberConversion();
      int endPos = offset + count;
      boolean negative = false;
      if (str.charAt(0) == '-') {
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
      if (i < endPos && str.charAt(i) == '0') {
        ++i;
        haveDigits = true;
        if (i == endPos) {
          if (preserveNegativeZero && negative) {
             // Negative zero in floating-point format
             return (kind == JSONOptions.ConversionMode.Double) ?
CBORObject.FromFloatingPointBits(0x8000, 2) :
CBORObject.FromObject(EDecimal.NegativeZero);
           }
          return CBORObject.FromObject(0);
        }
        if (str.charAt(i) == '.') {
          haveDecimalPoint = true;
          ++i;
        } else if (str.charAt(i) == 'E' || str.charAt(i) == 'e') {
          haveExponent = true;
        } else {
          return null;
        }
      }
      if (endPos - i >= 400) {
        // Check for certain fast cases
        int k = i;
        boolean hdp = haveDecimalPoint;
        boolean hd = haveDigits;
        boolean hdad = haveDigitsAfterDecimal;
        boolean hex = haveExponent;
        boolean haveManyDigits = false;
        boolean eplus = true;
        for (; k < endPos; ++k) {
          if (str.charAt(k) >= '0' && str.charAt(k) <= '9') {
            haveDigits = true;
            if (k - i > 400 && !haveDecimalPoint) {
               // Indicates that this value is bigger
               // than a decimal can store
               haveManyDigits = true;
            }
            if (haveDecimalPoint) {
              haveDigitsAfterDecimal = true;
            }
          } else if (str.charAt(k) == '.') {
            if (!haveDigits) {
              // no digits before the decimal point
              return null;
            } else if (haveDecimalPoint) {
              return null;
            }
            haveDecimalPoint = true;
          } else if (str.charAt(k) == 'E' || str.charAt(k) == 'e') {
            ++k;
            haveExponent = true;
            break;
          } else {
            return null;
          }
        }
        if (!haveDigits || (haveDecimalPoint && !haveDigitsAfterDecimal)) {
          return null;
        }
        if (haveExponent) {
          haveDigits = false;
          if (k == endPos) {
            return null;
          }
          if (str.charAt(k) == '+' || str.charAt(k) == '-') {
            if (str.charAt(k) == '-') {
              eplus = false;
            }
            ++k;
           }
           for (; k < endPos; ++k) {
             if (str.charAt(k) >= '0' && str.charAt(k) <= '9') {
               haveDigits = true;
             } else {
               return null;
             }
           }
           if (!haveDigits) {
             return null;
           }
        }
        if (k != endPos) {
          return null;
        }
        if (kind == JSONOptions.ConversionMode.Double ||
           kind == JSONOptions.ConversionMode.IntOrFloat ||
           kind == JSONOptions.ConversionMode.IntOrFloatFromDouble) {
          if (haveManyDigits && (!haveExponent || eplus)) {
             return negative ? CBORObject.FromObject(Double.NEGATIVE_INFINITY) :
                  CBORObject.FromObject(Double.POSITIVE_INFINITY);
          }
          // TODO: Fast cases involving negative exponents
        }
        haveDigitsAfterDecimal = hdad;
        haveDigits = hd;
        haveDecimalPoint = hdp;
        haveExponent = hex;
      }
      int digitStart = (haveDecimalPoint || haveExponent) ? -1 : i;
      int digitEnd = (haveDecimalPoint || haveExponent) ? -1 : i;
      int decimalDigitStart = haveDecimalPoint ? i : -1;
      int decimalDigitEnd = haveDecimalPoint ? i : -1;
      for (; i < endPos; ++i) {
        if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
          int thisdigit = (int)(str.charAt(i) - '0');
          if (haveDecimalPoint) {
            decimalDigitEnd = i + 1;
          } else {
 digitEnd = i + 1;
}
          if (mantInt <= MaxSafeInt) {
  mantInt *= 10;
  mantInt += thisdigit;
          }
          haveDigits = true;
          if (haveDecimalPoint) {
            haveDigitsAfterDecimal = true;
            if (newScaleInt == Integer.MIN_VALUE) {
              newScale = (newScale == null) ? (new FastInteger2(newScaleInt)) : newScale;
              newScale.AddInt(-1);
            } else {
              --newScaleInt;
            }
          }
        } else if (str.charAt(i) == '.') {
          if (!haveDigits) {
            // no digits before the decimal point
            return null;
          }
          if (haveDecimalPoint) {
            return null;
          }
          haveDecimalPoint = true;
          decimalDigitStart = i + 1;
          decimalDigitEnd = i + 1;
        } else if (str.charAt(i) == 'E' || str.charAt(i) == 'e') {
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
      if (mantInt > MaxSafeInt) {
        if (haveDecimalPoint) {
  if (digitStart < 0) {
    EInteger eidec = FastEIntegerFromString(
      str,
      decimalDigitStart,
      decimalDigitEnd);
    mant = new FastInteger2(eidec);
  } else {
    EInteger eidec;
    String tmpstr = str.substring(digitStart, (digitStart)+(digitEnd - digitStart)) +
              str.substring(decimalDigitStart, (decimalDigitStart)+(decimalDigitEnd -
decimalDigitStart));
    eidec = FastEIntegerFromString(tmpstr, 0, tmpstr.length());
    mant = new FastInteger2(eidec);
  }
} else if (digitStart >= 0) {
  EInteger eimant = FastEIntegerFromString(str, digitStart, digitEnd);
  mant = new FastInteger2(eimant);
}
      }
      if (haveExponent) {
        FastInteger2 exp = null;
        int expInt = 0;
        offset = 1;
        haveDigits = false;
        if (i == endPos) {
          return null;
        }
        if (str.charAt(i) == '+' || str.charAt(i) == '-') {
          if (str.charAt(i) == '-') {
            offset = -1;
          }
          ++i;
        }
        for (; i < endPos; ++i) {
          if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            haveDigits = true;
            int thisdigit = (int)(str.charAt(i) - '0');
            if (expInt > MaxSafeInt) {
              // TODO: Use same optimization as in mant above
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
          newScale = (newScale == null) ? (new FastInteger2(newScaleInt)) : newScale;
          if (offset < 0) {
            newScale.SubtractInt(expInt);
          } else if (expInt != 0) {
            newScale.AddInt(expInt);
          }
        } else {
          newScale = (newScale == null) ? (new FastInteger2(newScaleInt)) : newScale;
          if (offset < 0) {
            newScale.Subtract(exp);
          } else {
            newScale.Add(exp);
          }
        }
      }
      if (i != endPos) {
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
          // NOTE: mantInt can only be 0 or greater, so overflow is
          // impossible with Integer.MIN_VALUE

          if (negative) {
            mantInt = -mantInt;
            if (preserveNegativeZero && mantInt == 0) {
              if (kind == JSONOptions.ConversionMode.Double) {
                return CBORObject.FromFloatingPointBits(0x8000, 2);
              }
              return CBORObject.FromObject(
                  EDecimal.NegativeZero);
            }
          }
          if (kind == JSONOptions.ConversionMode.Double) {
            return CBORObject.FromObject((double)mantInt);
          } else {
            // mantInt is a 32-bit integer, write as CBOR integer in
            // all current kinds other than Double
            return CBORObject.FromObject(mantInt);
          }
        } else {
          EInteger bigmant2 = mant.AsEInteger();
          if (negative) {
            bigmant2=(bigmant2).Negate();
          }
          if (kind == JSONOptions.ConversionMode.Double) {
            // An arbitrary-precision integer; convert to double
            return CBORObject.FromObject(
              EFloat.FromEInteger(bigmant2).ToDouble());
          } else if (kind == JSONOptions.ConversionMode.IntOrFloat ||
               kind == JSONOptions.ConversionMode.IntOrFloatFromDouble) {
            if (bigmant2.CanFitInInt64()) {
              long longmant2 = bigmant2.ToInt64Checked();
              if (longmant2 >= (-(1 << 53)) + 1 && longmant2 <= (1 << 53) - 1) {
                // An arbitrary-precision integer that's "small enough";
                // return a CBOR Object of that integer
                return CBORObject.FromObject(bigmant2);
              }
            }
            return CBORObject.FromObject(
              EFloat.FromEInteger(bigmant2).ToDouble());
          }
          return CBORObject.FromObject(bigmant2);
        }
      } else {
        EInteger bigmant = (mant == null) ? (EInteger.FromInt32(mantInt)) :
          mant.AsEInteger();
        EInteger bigexp = (newScale == null) ? (EInteger.FromInt32(newScaleInt)) :
          newScale.AsEInteger();
        if (negative) {
          bigmant=(bigmant).Negate();
        }
        EDecimal edec;
        edec = EDecimal.Create(
          bigmant,
          bigexp);
        if (negative && preserveNegativeZero && bigmant.isZero()) {
          if (kind == JSONOptions.ConversionMode.Double) {
            return CBORObject.FromFloatingPointBits(0x8000, 2);
          }
          EDecimal negzero = EDecimal.NegativeZero;
          negzero = negzero.Quantize(bigexp, null);
          edec = negzero.Subtract(edec);
        }
        // Converting the EDecimal to a CBOR Object
        if (kind == JSONOptions.ConversionMode.Double) {
          double dbl = edec.ToDouble();
          if (preserveNegativeZero && dbl >= 0.0) {
            dbl = Math.abs(dbl);
          }
          return CBORObject.FromObject(dbl);
        } else if (kind == JSONOptions.ConversionMode.IntOrFloat ||
              kind == JSONOptions.ConversionMode.IntOrFloatFromDouble) {
          double dbl;
          CBORObject cbor = (kind == JSONOptions.ConversionMode.IntOrFloat) ?
            CBORObject.FromObject(edec) :
            CBORObject.FromObject(edec.ToDouble());
          CBORNumber cn = cbor.AsNumber();
          if (cn.IsInteger() && cn.CanFitInInt64()) {
             long v = cn.ToInt64Checked();
             if (v >= (-(1 << 53)) + 1 && v <= (1 << 53) - 1) {
               return CBORObject.FromObject(v);
             } else {
               dbl = cbor.AsDouble();
            }
          } else {
            dbl = edec.ToDouble();
          }
          if (preserveNegativeZero && dbl >= 0.0) {
            dbl = Math.abs(dbl);
          }
          return CBORObject.FromObject(dbl);
        }
        return CBORObject.FromObject(edec);
      }
    }
  }
