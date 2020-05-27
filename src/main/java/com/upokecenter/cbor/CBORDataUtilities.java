package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import java.util.*;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

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
          // TODO: Avoid converting to double
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
          sb = sb == null ? new StringBuilder() : sb;
          sb.append('\"');
          String ostring = obj.AsString();
          sb.append(ostring);
          /*
          for (int i = 0; i < ostring.length(); ++i) {
            if (ostring.charAt(i) >= 0x20 && ostring.charAt(i) <= 0x7f) {
              sb.append(ostring.charAt(i));
            } else {
                 sb.append("\\u");
                 sb.append(HexAlphabet.charAt((ostring.charAt(i) >> 12) & 15));
                 sb.append(HexAlphabet.charAt((ostring.charAt(i) >> 8) & 15));
                 sb.append(HexAlphabet.charAt((ostring.charAt(i) >> 4) & 15));
                 sb.append(HexAlphabet.charAt(ostring.charAt(i) & 15));
             }
          }
          */
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

    private static final JSONOptions DefaultOptions =
      new JSONOptions("");
    private static final JSONOptions PreserveNegZeroNo =
      new JSONOptions("preservenegativezero=0");
    private static final JSONOptions PreserveNegZeroYes =
      new JSONOptions("preservenegativezero=1");

    public static CBORObject ParseJSONNumber(String str) {
      // TODO: Preserve negative zeros in next major version
      return ParseJSONNumber(str, PreserveNegZeroNo);
    }

/**
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
        return CBORObject.FromObject((double)(-digit));
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
        return CBORObject.FromObject((double)digit);
      } else if (options != null && options.getNumberConversion() ==
        JSONOptions.ConversionMode.Decimal128) {
        return CBORObject.FromObject(EDecimal.FromInt32(digit));
      } else {
        // NOTE: Assumes digit is nonnegative, so PreserveNegativeZeros is irrelevant
        return CBORObject.FromObject(digit);
      }
    }

    // TODO: Use EFloat.ToDoubleBits when next version of
    // Numbers is available
    private static long ToDoubleBits(EFloat ef) {
      if (ef.IsPositiveInfinity()) {
        return (long)0x7ff0000000000000L;
      }
      if (ef.IsNegativeInfinity()) {
        return (long)0xfff0000000000000L;
      }
      if (ef.IsNaN()) {
        int[] nan = { 0, 0x7ff00000 };
        if (ef.isNegative()) {
          nan[1] |= ((int)(1 << 31));
        }
        if (ef.IsQuietNaN()) {
          // Quiet NaN is a NaN in which the highest bit of
          // the mantissa area is set
          nan[1] |= 0x80000;
        } else if (ef.getUnsignedMantissa().isZero()) {
          // Set the 0x40000 bit to keep the mantissa from
          // being zero if this is a signaling NaN
          nan[1] |= 0x40000;
        }
        if (!ef.getUnsignedMantissa().isZero()) {
          // Copy diagnostic information
          int[] words = new int[2];
          EInteger um = ef.getUnsignedMantissa();
          words[0]=um.ToInt32Unchecked();
          words[1]=um.ShiftRight(32).ToInt32Unchecked();
          nan[0] = words[0];
          nan[1] |= words[1] & 0x7ffff;
          if ((words[0] | (words[1] & 0x7ffff)) == 0 && !ef.IsQuietNaN()) {
            // Set the 0x40000 bit to keep the mantissa from
            // being zero if this is a signaling NaN
            nan[1] |= 0x40000;
          }
        }
        long lret = (((long)nan[0]) & 0xffffffffL);
        lret |= (((long)nan[1]) << 32);
        return lret;
      }
      EFloat thisValue = ef;
      // Check whether rounding can be avoided for common cases
      // where the value already fits a double
      if (!thisValue.isFinite() ||
        thisValue.getUnsignedMantissa().GetUnsignedBitLengthAsInt64() > 53 ||
        thisValue.getExponent().compareTo(-900) < 0 ||
        thisValue.getExponent().compareTo(900) > 0) {
        thisValue = ef.RoundToPrecision(EContext.Binary64);
      }
      if (!thisValue.isFinite()) {
        return ToDoubleBits(thisValue);
      }
      long longmant = thisValue.getUnsignedMantissa().ToInt64Checked();
      if (thisValue.isNegative() && longmant == 0) {
        return 1L << 63;
      } else if (longmant == 0) {
        return 0L;
      }
      // System.out.println("todouble -->" + this);
      long longBitLength = EInteger.FromInt64(longmant)
          .GetUnsignedBitLengthAsInt64();
      int expo = thisValue.getExponent().ToInt32Checked();
      boolean subnormal = false;
      if (longBitLength < 53) {
        int diff = 53 - (int)longBitLength;
        expo -= diff;
        if (expo < -1074) {
          // System.out.println("Diff changed from " + diff + " to " + (diff -
          // (-1074 - expo)));
          diff -= -1074 - expo;
          expo = -1074;
          subnormal = true;
        }
        longmant <<= diff;
      }
              // Clear the high bits where the exponent and sign are
              longmant &= 0xfffffffffffffL;
              if (!subnormal) {
                longmant |= (long)(expo + 1075) << 52;
              }
              if (thisValue.isNegative()) {
                longmant |= ((long)(1L << 63));
              }
              return longmant;
    }

    private static boolean IsBeyondSafeRange(long bits) {
       // Absolute value of double is greater than 9007199254740991.0,
       // or value is NaN
       return (bits&~(1L << 63)) > (0x7ffL << 52) ||
                    (bits&~(1L << 63)) > 0x433fffffffffffffL;
    }

    private static boolean IsIntegerValue(long bits) {
       bits&=~(1L << 63);
       // Infinity and NaN
       if (bits >= (0x7ffL << 52)) {
         return false;
       }
       // Beyond non-integer range
       if ((bits >> 52) >= 0x433) {
         return true;
       }
       // Less than 1
       if ((bits >> 52) <= 0x3fe) {
         return false;
       }
       int exp=(int)(bits >> 52);
       long mant = bits&((1L << 52)-1);
       int shift = 52-(exp-0x3ff);
       return ((mant >> shift) << shift) == mant;
    }

    private static long GetIntegerValue(long bits) {
       long sign=((bits & (1L << 63)) != 0) ? -1 : 1;
       bits&=~(1L << 63);
       // Infinity and NaN
       if (bits >= (0x7ffL << 52)) {
         throw new UnsupportedOperationException();
       }
       // Beyond safe range
       if ((bits >> 52) >= 0x434) {
         throw new UnsupportedOperationException();
       }
       // Less than 1
       if ((bits >> 52) <= 0x3fe) {
         throw new UnsupportedOperationException();
       }
       int exp=(int)(bits >> 52);
       long mant = bits&((1L << 52)-1);
       mant|=(1L << 52);
       int shift = 52-(exp-0x3ff);
       return (mant >> shift) * sign;
    }

    public static CBORObject ParseJSONNumber(
      String str,
      int offset,
      int count,
      JSONOptions options) {
      return ParseJSONNumber(str, offset, count, options, null);
    }
    static CBORObject ParseJSONNumber(
      String str,
      int offset,
      int count,
      JSONOptions options,
      int[] endOfNumber) {
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
      boolean negative = false;
      if (str.charAt(initialOffset) == '-') {
        ++offset;
        negative = true;
      }
      int numOffset = offset;
      boolean haveDecimalPoint = false;
      boolean haveDigits = false;
      boolean haveDigitsAfterDecimal = false;
      boolean haveExponent = false;
      int i = offset;
      int decimalPointPos = -1;
      // Check syntax
      int k = i;
      if (endPos - 1 > k && str.charAt(k) == '0' && str.charAt(k + 1) >= '0' &&
        str.charAt(k + 1) <= '9') {
        if (endOfNumber != null) {
          endOfNumber[0] = k + 2;
        }
        return null;
      }
      for (; k < endPos; ++k) {
        char c = str.charAt(k);
        if (c >= '0' && c <= '9') {
          haveDigits = true;
          haveDigitsAfterDecimal |= haveDecimalPoint;
        } else if (c == '.') {
          if (!haveDigits || haveDecimalPoint) {
            // no digits before the decimal point,
            // or decimal point already seen
            if (endOfNumber != null) {
              endOfNumber[0] = k;
            }
            return null;
          }
          haveDecimalPoint = true;
          decimalPointPos = k;
        } else if (c == 'E' || c == 'e') {
          ++k;
          haveExponent = true;
          break;
        } else {
          if (endOfNumber != null) {
            endOfNumber[0] = k;
            // Check if character can validly appear after a JSON number
            if (c != ',' && c != ']' && c != '}' &&
              c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09) {
              return null;
            } else {
              endPos = k;
              break;
            }
          }
          return null;
        }
      }
      if (!haveDigits || (haveDecimalPoint && !haveDigitsAfterDecimal)) {
        if (endOfNumber != null) {
          endOfNumber[0] = k;
        }
        return null;
      }
      int exponentPos = -1;
      boolean negativeExp = false;
      if (haveExponent) {
        haveDigits = false;
        if (k == endPos) {
          if (endOfNumber != null) {
            endOfNumber[0] = k;
          }
          return null;
        }
        char c = str.charAt(k);
        if (c == '+') {
          ++k;
        } else if (c == '-') {
          negativeExp = true;
          ++k;
        }
        for (; k < endPos; ++k) {
          c = str.charAt(k);
          if (c >= '0' && c <= '9') {
            if (exponentPos < 0 && c != '0') {
              exponentPos = k;
            }
            haveDigits = true;
          } else if (endOfNumber != null) {
            endOfNumber[0] = k;
            // Check if character can validly appear after a JSON number
            if (c != ',' && c != ']' && c != '}' &&
              c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09) {
              return null;
            } else {
              endPos = k;
              break;
            }
          } else {
            return null;
          }
        }
        if (!haveDigits) {
          if (endOfNumber != null) {
            endOfNumber[0] = k;
          }
          return null;
        }
      }
      if (endOfNumber != null) {
        endOfNumber[0] = endPos;
      }
      if (exponentPos >= 0 && endPos - exponentPos > 20) {
        // Exponent too high for precision to overcome (which
        // has a length no bigger than Integer.MAX_VALUE, which is 10 digits
        // long)
        if (negativeExp) {
          // underflow
          if (kind == JSONOptions.ConversionMode.Double ||
            kind == JSONOptions.ConversionMode.IntOrFloat) {
            if (!negative) {
              return CBORObject.FromFloatingPointBits(0, 2);
            } else {
              return CBORObject.FromFloatingPointBits(0x8000, 2);
            }
          } else if (kind ==
            JSONOptions.ConversionMode.IntOrFloatFromDouble) {
            return CBORObject.FromObject(0);
          }
        } else {
          // overflow
          if (kind == JSONOptions.ConversionMode.Double ||
            kind == JSONOptions.ConversionMode.IntOrFloatFromDouble ||
            kind == JSONOptions.ConversionMode.IntOrFloat) {
            return CBORObject.FromFloatingPointBits(
                negative ? (0xfffL << 52) : (0x7ffL << 52), 8);
          } else if (kind == JSONOptions.ConversionMode.Decimal128) {
            return CBORObject.FromObject(negative ?
                EDecimal.NegativeInfinity : EDecimal.PositiveInfinity);
          }
        }
      }
      if (!haveExponent && !haveDecimalPoint &&
        (endPos - numOffset) <= 16) {
        // Very common case of all-digit JSON number strings
        // less than 2^53 (with or without number sign)
        long v = 0L;
        int vi = numOffset;
        for (; vi < endPos; ++vi) {
          v = (v * 10) + (int)(str.charAt(vi) - '0');
        }
        if ((v != 0 || !negative) && v < (1L << 53) - 1) {
          if (negative) {
            v = -v;
          }
          if (kind == JSONOptions.ConversionMode.Double) {
            return CBORObject.FromObject(ToDoubleBits(EFloat.FromInt64(v)));
          } else if (kind == JSONOptions.ConversionMode.Decimal128) {
            return CBORObject.FromObject(EDecimal.FromInt64(v));
          } else {
            return CBORObject.FromObject(v);
          }
        }
      }
      if (kind == JSONOptions.ConversionMode.Full) {
        if (!haveDecimalPoint && !haveExponent) {
          EInteger ei = EInteger.FromSubstring(str, initialOffset, endPos);
          if (preserveNegativeZero && ei.isZero() && negative) {
            // TODO: In next major version, change to EDecimal.NegativeZero
            return CBORObject.FromFloatingPointBits(0x8000, 2);
          }
          return CBORObject.FromObject(ei);
        }
        if (!haveExponent && haveDecimalPoint && (endPos - numOffset) <= 19) {
          // No more than 18 digits plus one decimal point (which
          // should fit a long)
          long lv = 0L;
          int expo = -(endPos - (decimalPointPos + 1));
          int vi = numOffset;
          for (; vi < decimalPointPos; ++vi) {
            lv = ((lv * 10) + (int)(str.charAt(vi) - '0'));
          }
          for (vi = decimalPointPos + 1; vi < endPos; ++vi) {
            lv = ((lv * 10) + (int)(str.charAt(vi) - '0'));
          }
          if (negative) {
            lv = -lv;
          }
          if (!negative || lv != 0) {
            if (expo == 0) {
              return CBORObject.FromObject(lv);
            } else {
              CBORObject cbor = CBORObject.FromObject(
              new CBORObject[] {
                CBORObject.FromObject(expo),
                CBORObject.FromObject(lv),
              });
              return cbor.WithTag(4);
            }
          }
        }
        EDecimal ed = EDecimal.FromString(
            str,
            initialOffset,
            endPos - initialOffset);
        if (ed.isZero() && negative) {
          if (ed.getExponent().isZero()) {
           // TODO: In next major version, use EDecimal
           // for preserveNegativeZero
           return preserveNegativeZero ?
              CBORObject.FromFloatingPointBits(0x8000, 2) :
              CBORObject.FromObject(0);
            } else if (!preserveNegativeZero) {
              return CBORObject.FromObject(ed.Negate());
            } else {
            return CBORObject.FromObject(ed);
          }
        } else {
          return ed.getExponent().isZero() ? CBORObject.FromObject(ed.getMantissa()) :
CBORObject.FromObject(ed);
        }
      } else if (kind == JSONOptions.ConversionMode.Double) {
        EFloat ef = EFloat.FromString(
            str,
            initialOffset,
            endPos - initialOffset,
            EContext.Binary64);
        long lb = ToDoubleBits(ef);
        if (!preserveNegativeZero && (lb == 1L << 63 || lb == 0L)) {
          lb = 0L;
        }
        return CBORObject.FromFloatingPointBits(lb, 8);
      } else if (kind == JSONOptions.ConversionMode.Decimal128) {
        EDecimal ed = EDecimal.FromString(
            str,
            initialOffset,
            endPos - initialOffset,
            EContext.Decimal128);
        if (!preserveNegativeZero && ed.isNegative() && ed.isZero()) {
          ed = ed.Negate();
        }
        return CBORObject.FromObject(ed);
      } else if (kind == JSONOptions.ConversionMode.IntOrFloatFromDouble) {
        EFloat ef = EFloat.FromString(
            str,
            initialOffset,
            endPos - initialOffset,
            EContext.Binary64);
        long lb = ToDoubleBits(ef);
        if (!IsBeyondSafeRange(lb) && IsIntegerValue(lb)) {
          return CBORObject.FromObject(GetIntegerValue(lb));
        }
        return CBORObject.FromFloatingPointBits(lb, 8);
      } else if (kind == JSONOptions.ConversionMode.IntOrFloat) {
        EContext ctx = EContext.Binary64.WithBlankFlags();
        EFloat ef = EFloat.FromString(
            str,
            initialOffset,
            endPos - initialOffset,
            ctx);
        long lb = ToDoubleBits(ef);
        if ((ctx.getFlags() & EContext.FlagInexact) != 0) {
          // Inexact conversion to double, meaning that the String doesn't
          // represent an integer in [-(2^53)+1, 2^53), which is representable
          // ((exactly instanceof Double) ? (double)exactly : null), so treat as ConversionMode.Double
          if (!preserveNegativeZero && (lb == 1L << 63 || lb == 0L)) {
            lb = 0L;
          }
          return CBORObject.FromFloatingPointBits(lb, 8);
        } else {
          // Exact conversion; treat as ConversionMode.IntToFloatFromDouble
          if (!IsBeyondSafeRange(lb) && IsIntegerValue(lb)) {
            return CBORObject.FromObject(GetIntegerValue(lb));
          }
          return CBORObject.FromFloatingPointBits(lb, 8);
        }
      } else {
        throw new IllegalArgumentException("Unsupported conversion kind.");
      }
    }
  }
