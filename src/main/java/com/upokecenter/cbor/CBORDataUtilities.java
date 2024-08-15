package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under the Unlicense: https://unlicense.org/

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

    private static final long DoubleNegInfinity = ((long)(0xfffL << 52));
    private static final long DoublePosInfinity = ((long)(0x7ffL << 52));

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
            sb.append(')');
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
          long bits = obj.AsDoubleBits();
          simvalue = bits == DoubleNegInfinity ? "-Infinity" : (
              bits == DoublePosInfinity ? "Infinity" : (
                CBORUtilities.DoubleBitsNaN(bits) ? "NaN" :
obj.Untag().ToJSONString()));
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
          sb.append((char)0x27);
          break;
        }
        case TextString: {
          sb = sb == null ? new StringBuilder() : sb;
          sb.append('\"');
          String ostring = obj.AsString();
          int length = ostring.length();
          for (int i = 0; i < length; ++i) {
            int cp = com.upokecenter.util.DataUtilities.CodePointAt(ostring, i, 0);
            if (cp >= 0x10000) {
              sb.append("\\U");
              sb.append(HexAlphabet.charAt((cp >> 20) & 15));
              sb.append(HexAlphabet.charAt((cp >> 16) & 15));
              sb.append(HexAlphabet.charAt((cp >> 12) & 15));
              sb.append(HexAlphabet.charAt((cp >> 8) & 15));
              sb.append(HexAlphabet.charAt((cp >> 4) & 15));
              sb.append(HexAlphabet.charAt(cp & 15));
              ++i;
            } else if (cp >= 0x7F || cp < 0x20 || cp == (int)'\\' || cp ==
(int)'\"') {
              sb.append("\\u");
              sb.append(HexAlphabet.charAt((cp >> 12) & 15));
              sb.append(HexAlphabet.charAt((cp >> 8) & 15));
              sb.append(HexAlphabet.charAt((cp >> 4) & 15));
              sb.append(HexAlphabet.charAt(cp & 15));
            } else {
              sb.append((char)cp);
            }
          }
          sb.append('\"');
          break;
        }
        case Array: {
          sb = (sb == null) ? (new StringBuilder()) : sb;
          boolean first = true;
          sb.append('[');
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
          sb.append(']');
          break;
        }
        case Map: {
          sb = (sb == null) ? (new StringBuilder()) : sb;
          boolean first = true;
          sb.append('{');
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
          sb.append('}');
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

    static final JSONOptions DefaultOptions =
      new JSONOptions("");
    private static final JSONOptions PreserveNegZeroNo =
      new JSONOptions("preservenegativezero=0");
    private static final JSONOptions PreserveNegZeroYes =
      new JSONOptions("preservenegativezero=1");

    /**
     * Parses a number whose format follows the JSON specification. The method uses
     * a JSONOptions with all default properties except for a PreserveNegativeZero
     * property of false.
     * @param str A text string to parse as a JSON number.
     * @return A CBOR object that represents the parsed number. Returns positive
     * zero if the number is a zero that starts with a minus sign (such as "-0" or
     * "-0.0"). Returns null if the parsing fails, including if the string is null
     * or empty.
     */
    public static CBORObject ParseJSONNumber(String str) {
      // TODO: Preserve negative zeros in next major version
      return ParseJSONNumber(str, PreserveNegZeroNo);
    }

    /**
     * <p>Parses a number whose format follows the JSON specification (RFC 8259).
     * The method uses a JSONOptions with all default properties except for a
     * PreserveNegativeZero property of false.</p> <p>Roughly speaking, a valid
     * JSON number consists of an optional minus sign, one or more basic digits
     * (starting with 1 to 9 unless there is only one digit and that digit is 0),
     * an optional decimal point (".", full stop) with one or more basic digits,
     * and an optional letter E or e with an optional plus or minus sign and one or
     * more basic digits (the exponent). A text string representing a valid JSON
     * number is not allowed to contain white space characters, including
     * spaces.</p>
     * @param str A text string to parse as a JSON number.
     * @param integersOnly If true, no decimal points or exponents are allowed in
     * the string. The default is false.
     * @param positiveOnly If true, only positive numbers are allowed (the leading
     * minus is disallowed). The default is false.
     * @return A CBOR object that represents the parsed number. Returns positive
     * zero if the number is a zero that starts with a minus sign (such as "-0" or
     * "-0.0"). Returns null if the parsing fails, including if the string is null
     * or empty.
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
     * <p>Parses a number whose format follows the JSON specification (RFC
     * 8259).</p> <p>Roughly speaking, a valid JSON number consists of an optional
     * minus sign, one or more basic digits (starting with 1 to 9 unless there is
     * only one digit and that digit is 0), an optional decimal point (".", full
     * stop) with one or more basic digits, and an optional letter E or e with an
     * optional plus or minus sign and one or more basic digits (the exponent). A
     * text string representing a valid JSON number is not allowed to contain white
     * space characters, including spaces.</p>
     * @param str A text string to parse as a JSON number.
     * @param integersOnly If true, no decimal points or exponents are allowed in
     * the string. The default is false.
     * @param positiveOnly If true, the leading minus is disallowed in the string.
     * The default is false.
     * @param preserveNegativeZero If true, returns positive zero if the number is
     * a zero that starts with a minus sign (such as "-0" or "-0.0"). Otherwise,
     * returns negative zero in this case. The default is false.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the string is null or empty.
     * @deprecated Instead, call ParseJSONNumber(str, jsonoptions) with\u0020a JSONOptions that
 * sets preserveNegativeZero to the\u0020desired value, either true or
 * false. If this\u0020method call used positiveOnly = true, check that the
 * String\u0020does not\u0020begin\u0020with '-' before calling that
 * version. If this method call used\u0020integersOnly\u0020 = true, check
 * that the String does not contain '.', 'E',
 * or\u0020'e'\u0020before\u0020calling that version.
 */
@Deprecated
    public static CBORObject ParseJSONNumber(
      String str,
      boolean integersOnly,
      boolean positiveOnly,
      boolean preserveNegativeZero) {
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
     * <p>Parses a number whose format follows the JSON specification (RFC 8259)
     * and converts that number to a CBOR object.</p> <p>Roughly speaking, a valid
     * JSON number consists of an optional minus sign, one or more basic digits
     * (starting with 1 to 9 unless there is only one digit and that digit is 0),
     * an optional decimal point (".", full stop) with one or more basic digits,
     * and an optional letter E or e with an optional plus or minus sign and one or
     * more basic digits (the exponent). A text string representing a valid JSON
     * number is not allowed to contain white space characters, including
     * spaces.</p>
     * @param str A text string to parse as a JSON number.
     * @param options An object containing options to control how JSON numbers are
     * decoded to CBOR objects. Can be null, in which case a JSONOptions object
     * with all default properties is used instead.
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
     * <p>Parses a number whose format follows the JSON specification (RFC 8259)
     * from a portion of a text string, and converts that number to a CBOR
     * object.</p> <p>Roughly speaking, a valid JSON number consists of an optional
     * minus sign, one or more basic digits (starting with 1 to 9 unless there is
     * only one digit and that digit is 0), an optional decimal point (".", full
     * stop) with one or more basic digits, and an optional letter E or e with an
     * optional plus or minus sign and one or more basic digits (the exponent). A
     * text string representing a valid JSON number is not allowed to contain white
     * space characters, including spaces.</p>
     * @param str A text string containing the portion to parse as a JSON number.
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code str} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the string is null or empty.
     * @throws IllegalArgumentException Either {@code offset} or {@code count} is less
     * than 0 or greater than {@code str} 's length, or {@code str} 's length minus
     * {@code offset} is less than {@code count}.
     * @throws NullPointerException The parameter {@code str} is null.
     */
    public static CBORObject ParseJSONNumber(
      String str,
      int offset,
      int count) {
      return ((str) == null || (str).length() == 0) ? null :
        ParseJSONNumber(str,
          offset,
          count,
          JSONOptions.Default);
    }

    static CBORObject ParseSmallNumberAsNegative(
      int digit,
      JSONOptions options) {
      if (options != null && options.getNumberConversion() ==
        JSONOptions.ConversionMode.Double) {
        return CBORObject.FromFloatingPointBits(
           CBORUtilities.IntegerToDoubleBits(-digit),
           8);
      } else if (options != null && options.getNumberConversion() ==
        JSONOptions.ConversionMode.Decimal128) {
        return CBORObject.FromObject(EDecimal.FromInt32(-digit));
      } else {
        // NOTE: Assumes digit is greater than zero, so PreserveNegativeZeros is
        // irrelevant
        return CBORObject.FromObject(-digit);
      }
    }

    static CBORObject ParseSmallNumber(int digit, JSONOptions
      options) {
      if (options != null && options.getNumberConversion() ==
        JSONOptions.ConversionMode.Double) {
        return CBORObject.FromFloatingPointBits(
           CBORUtilities.IntegerToDoubleBits(digit),
           8);
      } else if (options != null && options.getNumberConversion() ==
        JSONOptions.ConversionMode.Decimal128) {
        return CBORObject.FromObject(EDecimal.FromInt32(digit));
      } else {
        // NOTE: Assumes digit is nonnegative, so PreserveNegativeZeros is irrelevant
        return CBORObject.FromObject(digit);
      }
    }

    /**
     * <p>Parses a number whose format follows the JSON specification (RFC 8259)
     * and converts that number to a CBOR object.</p> <p>Roughly speaking, a valid
     * JSON number consists of an optional minus sign, one or more basic digits
     * (starting with 1 to 9 unless there is only one digit and that digit is 0),
     * an optional decimal point (".", full stop) with one or more basic digits,
     * and an optional letter E or e with an optional plus or minus sign and one or
     * more basic digits (the exponent). A text string representing a valid JSON
     * number is not allowed to contain white space characters, including
     * spaces.</p>
     * @param str A text string to parse as a JSON number.
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code str} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @param options An object containing options to control how JSON numbers are
     * decoded to CBOR objects. Can be null, in which case a JSONOptions object
     * with all default properties is used instead.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the string is null or empty or {@code count} is
     * 0 or less.
     * @throws NullPointerException The parameter {@code str} is null.
     * @throws IllegalArgumentException Unsupported conversion kind.
     */
    public static CBORObject ParseJSONNumber(
      String str,
      int offset,
      int count,
      JSONOptions options) {
      return CBORDataUtilitiesTextString.ParseJSONNumber(
        str,
        offset,
        count,
        options,
        null);
    }

    /**
     * <p>Parses a number from a byte sequence whose format follows the JSON
     * specification (RFC 8259) and converts that number to a CBOR object.</p>
     * <p>Roughly speaking, a valid JSON number consists of an optional minus sign,
     * one or more basic digits (starting with 1 to 9 unless there is only one
     * digit and that digit is 0), an optional decimal point (".", full stop) with
     * one or more basic digits, and an optional letter E or e with an optional
     * plus or minus sign and one or more basic digits (the exponent). A byte
     * sequence representing a valid JSON number is not allowed to contain white
     * space characters, including spaces.</p>
     * @param bytes A sequence of bytes to parse as a JSON number.
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code bytes} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * bytes} (but not more than {@code bytes} 's length).
     * @param options An object containing options to control how JSON numbers are
     * decoded to CBOR objects. Can be null, in which case a JSONOptions object
     * with all default properties is used instead.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the byte sequence is null or empty or {@code
     * count} is 0 or less.
     * @throws NullPointerException The parameter {@code bytes} is null.
     * @throws IllegalArgumentException Unsupported conversion kind.
     */
    public static CBORObject ParseJSONNumber(
      byte[] bytes,
      int offset,
      int count,
      JSONOptions options) {
      return CBORDataUtilitiesByteArrayString.ParseJSONNumber(
        bytes,
        offset,
        count,
        options,
        null);
    }

    /**
     * <p>Parses a number from a byte sequence whose format follows the JSON
     * specification (RFC 8259) and converts that number to a CBOR object.</p>
     * <p>Roughly speaking, a valid JSON number consists of an optional minus sign,
     * one or more basic digits (starting with 1 to 9 unless there is only one
     * digit and that digit is 0), an optional decimal point (".", full stop) with
     * one or more basic digits, and an optional letter E or e with an optional
     * plus or minus sign and one or more basic digits (the exponent). A byte
     * sequence representing a valid JSON number is not allowed to contain white
     * space characters, including spaces.</p>
     * @param bytes A sequence of bytes to parse as a JSON number.
     * @param options An object containing options to control how JSON numbers are
     * decoded to CBOR objects. Can be null, in which case a JSONOptions object
     * with all default properties is used instead.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the byte sequence is null or empty.
     */
    public static CBORObject ParseJSONNumber(
      byte[] bytes,
      JSONOptions options) {
      return (bytes == null || bytes.length == 0) ? null :
        ParseJSONNumber(bytes,
          0,
          bytes.length,
          options);
    }

    /**
     * <p>Parses a number whose format follows the JSON specification (RFC 8259)
     * from a portion of a byte sequence, and converts that number to a CBOR
     * object.</p> <p>Roughly speaking, a valid JSON number consists of an optional
     * minus sign, one or more basic digits (starting with 1 to 9 unless there is
     * only one digit and that digit is 0), an optional decimal point (".", full
     * stop) with one or more basic digits, and an optional letter E or e with an
     * optional plus or minus sign and one or more basic digits (the exponent). A
     * byte sequence representing a valid JSON number is not allowed to contain
     * white space characters, including spaces.</p>
     * @param bytes A sequence of bytes to parse as a JSON number.
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code bytes} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * bytes} (but not more than {@code bytes} 's length).
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the byte sequence is null or empty.
     * @throws IllegalArgumentException Either {@code offset} or {@code count} is less
     * than 0 or greater than {@code bytes} 's length, or {@code bytes} 's length
     * minus {@code offset} is less than {@code count}.
     * @throws NullPointerException The parameter {@code bytes} is null.
     */
    public static CBORObject ParseJSONNumber(
      byte[] bytes,
      int offset,
      int count) {
      return (bytes == null || bytes.length == 0) ? null :
        ParseJSONNumber(bytes,
          offset,
          count,
          JSONOptions.Default);
    }

    /**
     * Parses a number from a byte sequence whose format follows the JSON
     * specification. The method uses a JSONOptions with all default properties
     * except for a PreserveNegativeZero property of false.
     * @param bytes A byte sequence to parse as a JSON number.
     * @return A CBOR object that represents the parsed number. Returns positive
     * zero if the number is a zero that starts with a minus sign (such as "-0" or
     * "-0.0"). Returns null if the parsing fails, including if the byte sequence
     * is null or empty.
     */
    public static CBORObject ParseJSONNumber(byte[] bytes) {
      // TODO: Preserve negative zeros in next major version
      return ParseJSONNumber(bytes, PreserveNegZeroNo);
    }

    /**
     * <p>Parses a number from a sequence of {@code char} s whose format follows
     * the JSON specification (RFC 8259) and converts that number to a CBOR
     * object.</p> <p>Roughly speaking, a valid JSON number consists of an optional
     * minus sign, one or more basic digits (starting with 1 to 9 unless there is
     * only one digit and that digit is 0), an optional decimal point (".", full
     * stop) with one or more basic digits, and an optional letter E or e with an
     * optional plus or minus sign and one or more basic digits (the exponent). A
     * sequence of {@code char} s representing a valid JSON number is not allowed
     * to contain white space characters, including spaces.</p>
     * @param chars A sequence of {@code char} s to parse as a JSON number.
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code chars} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * chars} (but not more than {@code chars} 's length).
     * @param options An object containing options to control how JSON numbers are
     * decoded to CBOR objects. Can be null, in which case a JSONOptions object
     * with all default properties is used instead.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the sequence of {@code char} s is null or empty
     * or {@code count} is 0 or less.
     * @throws NullPointerException The parameter {@code chars} is null.
     * @throws IllegalArgumentException Unsupported conversion kind.
     */
    public static CBORObject ParseJSONNumber(
      char[] chars,
      int offset,
      int count,
      JSONOptions options) {
      return CBORDataUtilitiesCharArrayString.ParseJSONNumber(
        chars,
        offset,
        count,
        options,
        null);
    }

    /**
     * <p>Parses a number from a sequence of {@code char} s whose format follows
     * the JSON specification (RFC 8259) and converts that number to a CBOR
     * object.</p> <p>Roughly speaking, a valid JSON number consists of an optional
     * minus sign, one or more basic digits (starting with 1 to 9 unless there is
     * only one digit and that digit is 0), an optional decimal point (".", full
     * stop) with one or more basic digits, and an optional letter E or e with an
     * optional plus or minus sign and one or more basic digits (the exponent). A
     * sequence of {@code char} s representing a valid JSON number is not allowed
     * to contain white space characters, including spaces.</p>
     * @param chars A sequence of {@code char} s to parse as a JSON number.
     * @param options An object containing options to control how JSON numbers are
     * decoded to CBOR objects. Can be null, in which case a JSONOptions object
     * with all default properties is used instead.
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the sequence of {@code char} s is null or empty.
     */
    public static CBORObject ParseJSONNumber(
      char[] chars,
      JSONOptions options) {
      return (chars == null || chars.length == 0) ? null :
        ParseJSONNumber(chars,
          0,
          chars.length,
          options);
    }

    /**
     * <p>Parses a number whose format follows the JSON specification (RFC 8259)
     * from a portion of a sequence of {@code char} s, and converts that number to
     * a CBOR object.</p> <p>Roughly speaking, a valid JSON number consists of an
     * optional minus sign, one or more basic digits (starting with 1 to 9 unless
     * there is only one digit and that digit is 0), an optional decimal point
     * (".", full stop) with one or more basic digits, and an optional letter E or
     * e with an optional plus or minus sign and one or more basic digits (the
     * exponent). A sequence of {@code char} s representing a valid JSON number is
     * not allowed to contain white space characters, including spaces.</p>
     * @param chars A sequence of {@code char} s to parse as a JSON number.
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code chars} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * chars} (but not more than {@code chars} 's length).
     * @return A CBOR object that represents the parsed number. Returns null if the
     * parsing fails, including if the sequence of {@code char} s is null or empty.
     * @throws IllegalArgumentException Either {@code offset} or {@code count} is less
     * than 0 or greater than {@code chars} 's length, or {@code chars} 's length
     * minus {@code offset} is less than {@code count}.
     * @throws NullPointerException The parameter {@code chars} is null.
     */
    public static CBORObject ParseJSONNumber(
      char[] chars,
      int offset,
      int count) {
      return (chars == null || chars.length == 0) ? null :
        ParseJSONNumber(chars,
          offset,
          count,
          JSONOptions.Default);
    }

    /**
     * Parses a number from a sequence of {@code char} s whose format follows the
     * JSON specification. The method uses a JSONOptions with all default
     * properties except for a PreserveNegativeZero property of false.
     * @param chars A sequence of {@code char} s to parse as a JSON number.
     * @return A CBOR object that represents the parsed number. Returns positive
     * zero if the number is a zero that starts with a minus sign (such as "-0" or
     * "-0.0"). Returns null if the parsing fails, including if the sequence of
     * {@code char} s is null or empty.
     */
    public static CBORObject ParseJSONNumber(char[] chars) {
      // TODO: Preserve negative zeros in next major version
      return ParseJSONNumber(chars, PreserveNegZeroNo);
    }
  }
