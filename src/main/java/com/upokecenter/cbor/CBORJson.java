package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import java.util.*;

import com.upokecenter.util.*;

  final class CBORJson {
private CBORJson() {
}
    // JSON parsing methods
    static int SkipWhitespaceJSON(CharacterInputWithCount reader) {
      while (true) {
        int c = reader.ReadChar();
        if (c == -1 || (c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09)) {
          return c;
        }
      }
    }

    static String NextJSONString(
CharacterInputWithCount reader,
int quote) {
      int c;
      StringBuilder sb = null;
      boolean surrogate = false;
      boolean surrogateEscaped = false;
      boolean escaped = false;
      while (true) {
        c = reader.ReadChar();
        if (c == -1 || c < 0x20) {
          reader.RaiseError("Unterminated String");
        }
        switch (c) {
          case '\\':
            c = reader.ReadChar();
            escaped = true;
            switch (c) {
              case '\\':
                c = '\\';
                break;
              case '/':
                // Now allowed to be escaped under RFC 7159
                c = '/';
                break;
              case '\"':
                c = '\"';
                break;
              case 'b':
                c = '\b';
                break;
              case 'f':
                c = '\f';
                break;
              case 'n':
                c = '\n';
                break;
              case 'r':
                c = '\r';
                break;
              case 't':
                c = '\t';
                break;
                case 'u': { // Unicode escape
                  c = 0;
                  // Consists of 4 hex digits
                  for (int i = 0; i < 4; ++i) {
                    int ch = reader.ReadChar();
                    if (ch >= '0' && ch <= '9') {
                    c <<= 4;
                    c |= ch - '0';
                    } else if (ch >= 'A' && ch <= 'F') {
                    c <<= 4;
                    c |= ch + 10 - 'A';
                    } else if (ch >= 'a' && ch <= 'f') {
                    c <<= 4;
                    c |= ch + 10 - 'a';
                    } else {
                reader.RaiseError("Invalid Unicode escaped character");
                    }
                  }
                  break;
                }
                default: reader.RaiseError("Invalid escaped character");
                  break;
            }
            break;
            default: escaped = false;
            break;
        }
        if (surrogate) {
          if ((c & 0x1ffc00) != 0xdc00) {
            // Note: this includes the ending quote
            // and supplementary characters
            reader.RaiseError("Unpaired surrogate code point");
          }
          if (escaped != surrogateEscaped) {
            reader.RaiseError(
              "Pairing escaped surrogate with unescaped surrogate");
          }
          surrogate = false;
        } else if ((c & 0x1ffc00) == 0xd800) {
          surrogate = true;
          surrogateEscaped = escaped;
        } else if ((c & 0x1ffc00) == 0xdc00) {
          reader.RaiseError("Unpaired surrogate code point");
        }
        if (c == quote && !escaped) {
          // End quote reached
          return (sb == null) ? "" : sb.toString();
        }
        sb = (sb == null) ? ((new StringBuilder())) : sb;
        if (c <= 0xffff) {
          sb.append((char)c);
        } else {
          sb.append((char)((((c - 0x10000) >> 10) & 0x3ff) + 0xd800));
          sb.append((char)(((c - 0x10000) & 0x3ff) + 0xdc00));
        }
      }
    }

    static CBORObject NextJSONValue(
      CharacterInputWithCount reader,
      int firstChar,
      boolean noDuplicates,
      int[] nextChar,
      int depth) {
      String str;
      int c = firstChar;
      CBORObject obj = null;
      if (c < 0) {
        reader.RaiseError("Unexpected end of data");
      }
      if (c == '"') {
        // Parse a String
        // The tokenizer already checked the String for invalid
        // surrogate pairs, so just call the CBORObject
        // constructor directly
        obj = CBORObject.FromRaw(NextJSONString(reader, c));
        nextChar[0] = SkipWhitespaceJSON(reader);
        return obj;
      }
      if (c == '{') {
        // Parse an object
        obj = ParseJSONObject(reader, noDuplicates, depth + 1);
        nextChar[0] = SkipWhitespaceJSON(reader);
        return obj;
      }
      if (c == '[') {
        // Parse an array
        obj = ParseJSONArray(reader, noDuplicates, depth + 1);
        nextChar[0] = SkipWhitespaceJSON(reader);
        return obj;
      }
      if (c == 't') {
        // Parse true
        if (reader.ReadChar() != 'r' || reader.ReadChar() != 'u' ||
            reader.ReadChar() != 'e') {
          reader.RaiseError("Value can't be parsed.");
        }
        nextChar[0] = SkipWhitespaceJSON(reader);
        return CBORObject.True;
      }
      if (c == 'f') {
        // Parse false
        if (reader.ReadChar() != 'a' || reader.ReadChar() != 'l' ||
            reader.ReadChar() != 's' || reader.ReadChar() != 'e') {
          reader.RaiseError("Value can't be parsed.");
        }
        nextChar[0] = SkipWhitespaceJSON(reader);
        return CBORObject.False;
      }
      if (c == 'n') {
        // Parse null
        if (reader.ReadChar() != 'u' || reader.ReadChar() != 'l' ||
            reader.ReadChar() != 'l') {
          reader.RaiseError("Value can't be parsed.");
        }
        nextChar[0] = SkipWhitespaceJSON(reader);
        return CBORObject.Null;
      }
      if (c == '-' || (c >= '0' && c <= '9')) {
        // Parse a number
        StringBuilder sb = new StringBuilder();
        while (c == '-' || c == '+' || c == '.' || (c >= '0' && c <= '9') ||
               c == 'e' || c == 'E') {
          sb.append((char)c);
          c = reader.ReadChar();
        }
        str = sb.toString();
        obj = CBORDataUtilities.ParseJSONNumber(str);
        if (obj == null) {
          reader.RaiseError("JSON number can't be parsed. " + str);
        }
        if (c == -1 || (c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09)) {
          nextChar[0] = c;
        } else {
          nextChar[0] = SkipWhitespaceJSON(reader);
        }
        return obj;
      }
      reader.RaiseError("Value can't be parsed.");
      return null;
    }

    static CBORObject ParseJSONValue(
      CharacterInputWithCount reader,
      boolean noDuplicates,
      boolean objectOrArrayOnly,
      int depth) {
      if (depth > 1000) {
        reader.RaiseError("Too deeply nested");
      }
      int c;
      c = SkipWhitespaceJSON(reader);
      if (c == '[') {
        return ParseJSONArray(reader, noDuplicates, depth);
      }
      if (c == '{') {
        return ParseJSONObject(reader, noDuplicates, depth);
      }
      if (objectOrArrayOnly) {
        reader.RaiseError("A JSON Object must begin with '{' or '['");
      }
      int[] nextChar = new int[1];
      return NextJSONValue(reader, c, noDuplicates, nextChar, depth);
    }

    static CBORObject ParseJSONObject(
      CharacterInputWithCount reader,
      boolean noDuplicates,
      int depth) {
      // Assumes that the last character read was '{'
      if (depth > 1000) {
        reader.RaiseError("Too deeply nested");
      }
      int c;
      CBORObject key = null;
      CBORObject obj;
      int[] nextchar = new int[1];
      boolean seenComma = false;
      HashMap<CBORObject, CBORObject> myHashMap = new HashMap<CBORObject, CBORObject>();
      while (true) {
        c = SkipWhitespaceJSON(reader);
        switch (c) {
          case -1:
            reader.RaiseError("A JSONObject must end with '}'");
            break;
          case '}':
            if (seenComma) {
              // Situation like '{"0"=>1,}'
              reader.RaiseError("Trailing comma");
              return null;
            }
            return CBORObject.FromRaw(myHashMap);
            default: {
              // Read the next String
              if (c < 0) {
                reader.RaiseError("Unexpected end of data");
                return null;
              }
              if (c != '"') {
                reader.RaiseError("Expected a String as a key");
                return null;
              }
              // Parse a String that represents the Object's key
              // The tokenizer already checked the String for invalid
              // surrogate pairs, so just call the CBORObject
              // constructor directly
              obj = CBORObject.FromRaw(NextJSONString(reader, c));
              key = obj;
              if (noDuplicates && myHashMap.containsKey(obj)) {
                reader.RaiseError("Key already exists: " + key);
                return null;
              }
              break;
            }
        }
        if (SkipWhitespaceJSON(reader) != ':') {
          reader.RaiseError("Expected a ':' after a key");
        }
        // NOTE: Will overwrite existing value
        myHashMap.put(key, NextJSONValue(
          reader,
          SkipWhitespaceJSON(reader),
          noDuplicates,
          nextchar,
          depth));
        switch (nextchar[0]) {
          case ',':
            seenComma = true;
            break;
          case '}':
            return CBORObject.FromRaw(myHashMap);
            default: reader.RaiseError("Expected a ',' or '}'");
            break;
        }
      }
    }

    static CBORObject ParseJSONArray(
      CharacterInputWithCount reader,
      boolean noDuplicates,
      int depth) {
      // Assumes that the last character read was '['
      if (depth > 1000) {
        reader.RaiseError("Too deeply nested");
      }
      ArrayList<CBORObject> myArrayList = new ArrayList<CBORObject>();
      boolean seenComma = false;
      int[] nextchar = new int[1];
      while (true) {
        int c = SkipWhitespaceJSON(reader);
        if (c == ']') {
          if (seenComma) {
            // Situation like '[0,1,]'
            reader.RaiseError("Trailing comma");
          }
          return CBORObject.FromRaw(myArrayList);
        }
        if (c == ',') {
          // Situation like '[,0,1,2]' or '[0,,1]'
          reader.RaiseError("Empty array element");
        }
        myArrayList.add(
          NextJSONValue(
            reader,
            c,
            noDuplicates,
            nextchar,
            depth));
        c = nextchar[0];
        switch (c) {
          case ',':
            seenComma = true;
            break;
          case ']':
            return CBORObject.FromRaw(myArrayList);
          default:
            reader.RaiseError("Expected a ',' or ']'");
            break;
        }
      }
    }

    private static final String Hex16 = "0123456789ABCDEF";

    static void WriteJSONStringUnquoted(
      String str,
      StringOutput sb) throws java.io.IOException {
      // Surrogates were already verified when this
      // String was added to the CBOR Object; that check
      // is not repeated here
      boolean first = true;
      for (int i = 0; i < str.length(); ++i) {
        char c = str.charAt(i);
        if (c == '\\' || c == '"') {
          if (first) {
            first = false;
            sb.WriteString(str, 0, i);
          }
          sb.WriteCodePoint((int)'\\');
          sb.WriteCodePoint((int)c);
        } else if (c < 0x20 || (c >= 0x85 && (c == 0x2028 || c == 0x2029 ||
                    c == 0x85 || c == 0xfeff || c == 0xfffe ||
                    c == 0xffff))) {
          // Control characters, and also the line and paragraph separators
          // which apparently can't appear in JavaScript (as opposed to
          // JSON) strings
          // TODO: Also include 0x7f..0x9f in version 3
          if (first) {
            first = false;
            sb.WriteString(str, 0, i);
          }
          if (c == 0x0d) {
            sb.WriteString("\\r");
          } else if (c == 0x0a) {
            sb.WriteString("\\n");
          } else if (c == 0x08) {
            sb.WriteString("\\b");
          } else if (c == 0x0c) {
            sb.WriteString("\\f");
          } else if (c == 0x09) {
            sb.WriteString("\\t");
          } else if (c == 0x85) {
            sb.WriteString("\\u0085");
          } else if (c >= 0x2028) {
            sb.WriteString("\\u");
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 12) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 8) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 4) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)(c & 15)));
          } else {
            sb.WriteString("\\u00");
            sb.WriteCodePoint((int)Hex16.charAt((int)(c >> 4)));
            sb.WriteCodePoint((int)Hex16.charAt((int)(c & 15)));
          }
        } else if (!first) {
          if ((c & 0xfc00) == 0xd800) {
            sb.WriteString(str, i, 2);
            ++i;
          } else {
            sb.WriteCodePoint((int)c);
          }
        }
      }
      if (first) {
        sb.WriteString(str);
      }
    }

    static void WriteJSONToInternal(
      CBORObject obj,
      StringOutput writer) throws java.io.IOException {
      int type = obj.getItemType();
      Object thisItem = obj.getThisItem();
      switch (type) {
          case CBORObject.CBORObjectTypeSimpleValue: {
            if (obj.isTrue()) {
              writer.WriteString("true");
              return;
            }
            if (obj.isFalse()) {
              writer.WriteString("false");
              return;
            }
            writer.WriteString("null");
            return;
          }
          case CBORObject.CBORObjectTypeSingle: {
            float f = ((Float)thisItem).floatValue();
            if (((f) == Float.NEGATIVE_INFINITY) ||
                ((f) == Float.POSITIVE_INFINITY) || Float.isNaN(f)) {
              writer.WriteString("null");
              return;
            }
            writer.WriteString(
              CBORObject.TrimDotZero(
                Float.toString((float)f)));
            return;
          }
          case CBORObject.CBORObjectTypeDouble: {
            double f = ((Double)thisItem).doubleValue();
            if (((f) == Double.NEGATIVE_INFINITY) || ((f) == Double.POSITIVE_INFINITY) ||
                Double.isNaN(f)) {
              writer.WriteString("null");
              return;
            }
            writer.WriteString(CBORObject.TrimDotZero(
              Double.toString((double)f)));
            return;
          }
          case CBORObject.CBORObjectTypeInteger: {
            long longItem = (((Long)thisItem).longValue());
            writer.WriteString(CBORUtilities.LongToString(longItem));
            return;
          }
          case CBORObject.CBORObjectTypeBigInteger: {
            writer.WriteString(
              CBORUtilities.BigIntToString((BigInteger)thisItem));
            return;
          }
          case CBORObject.CBORObjectTypeExtendedDecimal: {
            ExtendedDecimal dec = (ExtendedDecimal)thisItem;
            if (dec.IsInfinity() || dec.IsNaN()) {
              writer.WriteString("null");
            } else {
              writer.WriteString(dec.toString());
            }
            return;
          }
          case CBORObject.CBORObjectTypeExtendedFloat: {
            ExtendedFloat flo = (ExtendedFloat)thisItem;
            if (flo.IsInfinity() || flo.IsNaN()) {
              writer.WriteString("null");
              return;
            }
            if (flo.isFinite() &&
                (flo.getExponent()).abs().compareTo(BigInteger.valueOf(2500)) > 0) {
              // Too inefficient to convert to a decimal number
              // from a bigfloat with a very high exponent,
              // so convert to double instead
              double f = flo.ToDouble();
              if (((f) == Double.NEGATIVE_INFINITY) ||
                  ((f) == Double.POSITIVE_INFINITY) || Double.isNaN(f)) {
                writer.WriteString("null");
                return;
              }
              writer.WriteString(
                CBORObject.TrimDotZero(
                  Double.toString((double)f)));
              return;
            }
            writer.WriteString(flo.toString());
            return;
          }
        case CBORObject.CBORObjectTypeByteString:
          {
            byte[] byteArray = (byte[])thisItem;
            if (byteArray.length == 0) {
              writer.WriteString("\"\"");
              return;
            }
            writer.WriteCodePoint((int)'\"');
            if (obj.HasTag(22)) {
              Base64.WriteBase64(
                writer,
                byteArray,
                0,
                byteArray.length,
                false);
            } else if (obj.HasTag(23)) {
              // Write as base16
              for (int i = 0; i < byteArray.length; ++i) {
                writer.WriteCodePoint((int)Hex16.charAt((byteArray[i] >> 4) & 15));
                writer.WriteCodePoint((int)Hex16.charAt(byteArray[i] & 15));
              }
            } else {
              Base64.WriteBase64URL(
                writer,
                byteArray,
                0,
                byteArray.length,
                false);
            }
            writer.WriteCodePoint((int)'\"');
            break;
          }
          case CBORObject.CBORObjectTypeTextString: {
            String thisString = (String)thisItem;
            if (thisString.length() == 0) {
              writer.WriteString("\"\"");
              return;
            }
            writer.WriteCodePoint((int)'\"');
            WriteJSONStringUnquoted(thisString, writer);
            writer.WriteCodePoint((int)'\"');
            break;
          }
          case CBORObject.CBORObjectTypeArray: {
            boolean first = true;
            writer.WriteCodePoint((int)'[');
            for (CBORObject i : obj.AsList()) {
              if (!first) {
                writer.WriteCodePoint((int)',');
              }
              WriteJSONToInternal(i, writer);
              first = false;
            }
            writer.WriteCodePoint((int)']');
            break;
          }
          case CBORObject.CBORObjectTypeExtendedRational: {
            ExtendedRational dec = (ExtendedRational)thisItem;
            ExtendedDecimal f = dec.ToExtendedDecimalExactIfPossible(
              PrecisionContext.Decimal128.WithUnlimitedExponents());
            if (!f.isFinite()) {
              writer.WriteString("null");
            } else {
              writer.WriteString(f.toString());
            }
            break;
          }
          case CBORObject.CBORObjectTypeMap: {
            boolean first = true;
            boolean hasNonStringKeys = false;
            Map<CBORObject, CBORObject> objMap = obj.AsMap();
            for (Map.Entry<CBORObject, CBORObject> entry : objMap.entrySet()) {
              CBORObject key = entry.getKey();
              if (key.getItemType() != CBORObject.CBORObjectTypeTextString) {
                hasNonStringKeys = true;
                break;
              }
            }
            if (!hasNonStringKeys) {
              writer.WriteCodePoint((int)'{');
              for (Map.Entry<CBORObject, CBORObject> entry : objMap.entrySet()) {
                CBORObject key = entry.getKey();
                CBORObject value = entry.getValue();
                if (!first) {
                  writer.WriteCodePoint((int)',');
                }
                writer.WriteCodePoint((int)'\"');
                WriteJSONStringUnquoted((String)key.getThisItem(), writer);
                writer.WriteCodePoint((int)'\"');
                writer.WriteCodePoint((int)':');
                WriteJSONToInternal(value, writer);
                first = false;
              }
              writer.WriteCodePoint((int)'}');
            } else {
              // This map has non-String keys
              Map<String, CBORObject> stringMap = new
                HashMap<String, CBORObject>();
              // Copy to a map with String keys, since
              // some keys could be duplicates
              // when serialized to strings
              for (Map.Entry<CBORObject, CBORObject> entry : objMap.entrySet()) {
                CBORObject key = entry.getKey();
                CBORObject value = entry.getValue();
           String str = (key.getItemType() == CBORObject.CBORObjectTypeTextString) ?
                  ((String)key.getThisItem()) : key.ToJSONString();
                stringMap.put(str, value);
              }
              first = true;
              writer.WriteCodePoint((int)'{');
              for (Map.Entry<String, CBORObject> entry : stringMap.entrySet()) {
                String key = entry.getKey();
                CBORObject value = entry.getValue();
                if (!first) {
                  writer.WriteCodePoint((int)',');
                }
                writer.WriteCodePoint((int)'\"');
                WriteJSONStringUnquoted((String)key, writer);
                writer.WriteCodePoint((int)'\"');
                writer.WriteCodePoint((int)':');
                WriteJSONToInternal(value, writer);
                first = false;
              }
              writer.WriteCodePoint((int)'}');
            }
            break;
          }
        default:
          throw new IllegalStateException("Unexpected item type");
      }
    }
  }
