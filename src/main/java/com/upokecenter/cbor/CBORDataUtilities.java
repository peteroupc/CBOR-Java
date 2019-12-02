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
 * contain '.', 'E', or\u0020'e'\u0020before\u0020calling that version.
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
      int initialOffset = offset;
      if (str.charAt(0) == '-') {
        ++offset;
      }
      boolean haveDecimalPoint = false;
      boolean haveDigits = false;
      boolean haveDigitsAfterDecimal = false;
      boolean haveExponent = false;
      int i = offset;
      // Check syntax
      int k = i;
      if (endPos - 1 > k && str.charAt(k) == '0' && str.charAt(k + 1) == '0') {
        return null;
      }
      for (; k < endPos; ++k) {
        if (str.charAt(k) >= '0' && str.charAt(k) <= '9') {
          haveDigits = true;
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
      if (kind == JSONOptions.ConversionMode.Full) {
        EDecimal ed = EDecimal.FromString(str, initialOffset, count);
        if (!preserveNegativeZero && ed.isZero() && ed.isNegative()) {
          ed = ed.Negate();
        }
        return CBORObject.FromObject(ed);
      } else if (kind == JSONOptions.ConversionMode.Double) {
        double dbl = EFloat.FromString(
          str,
          initialOffset,
          count,
          EContext.Binary64).ToDouble();
        if (!preserveNegativeZero && dbl == 0.0) {
          dbl = 0.0;
        }
        return CBORObject.FromObject(dbl);
      } else if (kind == JSONOptions.ConversionMode.IntOrFloatFromDouble) {
        double dbl = EFloat.FromString(
          str,
          initialOffset,
          count,
          EContext.Binary64).ToDouble();
        if (!Double.isNaN(dbl) && dbl >= -9007199254740991.0 &&
          dbl <= 9007199254740991.0 && Math.floor(dbl) == dbl) {
          long idbl = (long)dbl;
          return CBORObject.FromObject(idbl);
        }
        return CBORObject.FromObject(dbl);
      } else if (kind == JSONOptions.ConversionMode.IntOrFloat) {
        EContext ctx = EContext.Binary64.WithBlankFlags();
        double dbl = EFloat.FromString(
          str,
          initialOffset,
          count,
          ctx).ToDouble();
        if ((ctx.getFlags() & EContext.FlagInexact) != 0) {
          // Inexact conversion to double, meaning that the String doesn't
          // represent an integer in [-(2^53)+1, 2^53), which is representable
          // ((exactly instanceof Double) ? (double)exactly : null), so treat as ConversionMode.Double
          if (!preserveNegativeZero && dbl == 0.0) {
            dbl = 0.0;
          }
          return CBORObject.FromObject(dbl);
        } else {
          // Exact conversion; treat as ConversionMode.IntToFloatFromDouble
          if (!Double.isNaN(dbl) && dbl >= -9007199254740991.0 &&
            dbl <= 9007199254740991.0 && Math.floor(dbl) == dbl) {
            long idbl = (long)dbl;
            return CBORObject.FromObject(idbl);
          }
          return CBORObject.FromObject(dbl);
        }
      } else {
        throw new IllegalArgumentException("Unsupported conversion kind.");
      }
    }
  }
