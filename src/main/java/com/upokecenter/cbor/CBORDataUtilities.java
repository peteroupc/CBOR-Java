package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import java.util.*;

import com.upokecenter.numbers.*;

  public final class CBORDataUtilities {
private CBORDataUtilities() {
}
    private static final String HexAlphabet = "0123456789ABCDEF";

    private static final long DoubleNegInfinity = (0xfffL << 52);
    private static final long DoublePosInfinity = (0x7ffL << 52);

    static String ToStringHelper(CBORObject obj, int depth) {
      StringBuilder sb = null;
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
      String simvalue;
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
            sb = (sb == null) ? (new StringBuilder()) : sb;
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
              } else if (cp >= 0x7F || cp < 0x20 || cp == '\\' || cp ==
  '\"') {
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

    public static CBORObject ParseJSONNumber(String str) {
      return ParseJSONNumber(str, JSONOptions.Default);
    }

    public static CBORObject ParseJSONNumber(
      String str,
      JSONOptions options) {
      return ((str) == null || (str).length() == 0) ? null :
        ParseJSONNumber(str,
          0,
          str.length(),
          options);
    }

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

    public static CBORObject ParseJSONNumber(
      byte[] bytes,
      JSONOptions options) {
      return (bytes == null || bytes.length == 0) ? null :
        ParseJSONNumber(bytes,
          0,
          bytes.length,
          options);
    }

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

    public static CBORObject ParseJSONNumber(byte[] bytes) {
      return ParseJSONNumber(bytes, JSONOptions.Default);
    }

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

    public static CBORObject ParseJSONNumber(
      char[] chars,
      JSONOptions options) {
      return (chars == null || chars.length == 0) ? null :
        ParseJSONNumber(chars,
          0,
          chars.length,
          options);
    }

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

    public static CBORObject ParseJSONNumber(char[] chars) {
      return ParseJSONNumber(chars, JSONOptions.Default);
    }
  }
