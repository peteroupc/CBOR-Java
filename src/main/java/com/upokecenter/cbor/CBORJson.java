package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import java.util.*;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  final class CBORJson {
    // JSON parsing methods
    private static int SkipWhitespaceJSON(CharacterInputWithCount reader) {
      while (true) {
        int c = reader.ReadChar();
        if (c == -1 || (c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09)) {
          return c;
        }
      }
    }

    private CharacterInputWithCount reader;
    private StringBuilder sb;

    private String NextJSONString() {
      int c;
      this.sb = (this.sb == null) ? (new StringBuilder()) : this.sb;
      this.sb.delete(0, (0)+(this.sb.length()));
      while (true) {
        c = this.reader.ReadChar();
        if (c == -1 || c < 0x20) {
          this.reader.RaiseError("Unterminated String");
        }
        switch (c) {
          case '\\':
            c = this.reader.ReadChar();
            switch (c) {
              case '\\':
                this.sb.append('\\');
                break;
              case '/':
                // Now allowed to be escaped under RFC 8259
                this.sb.append('/');
                break;
              case '\"':
                this.sb.append('\"');
                break;
              case 'b':
                this.sb.append('\b');
                break;
              case 'f':
                this.sb.append('\f');
                break;
              case 'n':
                this.sb.append('\n');
                break;
              case 'r':
                this.sb.append('\r');
                break;
              case 't':
                this.sb.append('\t');
                break;
                case 'u': {  // Unicode escape
                  c = 0;
                  // Consists of 4 hex digits
                  for (int i = 0; i < 4; ++i) {
                    int ch = this.reader.ReadChar();
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
                this.reader.RaiseError("Invalid Unicode escaped character");
                    }
                  }
                  if ((c & 0xf800) != 0xd800) {
                    // Non-surrogate
                    this.sb.append((char)c);
                  } else if ((c & 0xfc00) == 0xd800) {
                    int ch = this.reader.ReadChar();
                    if (ch != '\\' || this.reader.ReadChar() != 'u') {
                    this.reader.RaiseError("Invalid escaped character");
                    }
                    int c2 = 0;
                    for (int i = 0; i < 4; ++i) {
                    ch = this.reader.ReadChar();
                    if (ch >= '0' && ch <= '9') {
                    c2 <<= 4;
                    c2 |= ch - '0';
                    } else if (ch >= 'A' && ch <= 'F') {
                    c2 <<= 4;
                    c2 |= ch + 10 - 'A';
                    } else if (ch >= 'a' && ch <= 'f') {
                    c2 <<= 4;
                    c2 |= ch + 10 - 'a';
                    } else {
                    this.reader.RaiseError("Invalid Unicode escaped character");
                    }
                    }
                    if ((c2 & 0xfc00) != 0xdc00) {
                    this.reader.RaiseError("Unpaired surrogate code point");
                    } else {
                    this.sb.append((char)c);
                    this.sb.append((char)c2);
                    }
                  } else {
                    this.reader.RaiseError("Unpaired surrogate code point");
                  }
                  break;
                }
              default:
                {
                  this.reader.RaiseError("Invalid escaped character");
                  break;
                }
            }
            break;
          case 0x22:  // double quote
            return this.sb.toString();
          default: {
              // NOTE: Assumes the character reader
              // throws an error on finding illegal surrogate
              // pairs in the String or invalid encoding
              // in the stream
              if ((c >> 16) == 0) {
                this.sb.append((char)c);
              } else {
              this.sb.append((char)((((c - 0x10000) >> 10) & 0x3ff) +
                  0xd800));
                this.sb.append((char)(((c - 0x10000) & 0x3ff) + 0xdc00));
              }
              break;
            }
        }
      }
    }

    private CBORObject NextJSONValue(
      int firstChar,
      int[] nextChar,
      int depth) {
      String str;
      int c = firstChar;
      CBORObject obj = null;
      if (c < 0) {
        this.reader.RaiseError("Unexpected end of data");
      }
      switch (c) {
        case '"':
          {
            // Parse a String
            // The tokenizer already checked the String for invalid
            // surrogate pairs, so just call the CBORObject
            // constructor directly
            obj = CBORObject.FromRaw(this.NextJSONString());
            nextChar[0] = SkipWhitespaceJSON(this.reader);
            return obj;
          }
        case '{':
          {
            // Parse an object
            obj = this.ParseJSONObject(depth + 1);
            nextChar[0] = SkipWhitespaceJSON(this.reader);
            return obj;
          }
        case '[':
          {
            // Parse an array
            obj = this.ParseJSONArray(depth + 1);
            nextChar[0] = SkipWhitespaceJSON(this.reader);
            return obj;
          }
        case 't':
          {
            // Parse true
          if (this.reader.ReadChar() != 'r' || this.reader.ReadChar() != 'u' ||
              this.reader.ReadChar() != 'e') {
              this.reader.RaiseError("Value can't be parsed.");
            }
            nextChar[0] = SkipWhitespaceJSON(this.reader);
            return CBORObject.True;
          }
        case 'f':
          {
            // Parse false
          if (this.reader.ReadChar() != 'a' || this.reader.ReadChar() != 'l' ||
              this.reader.ReadChar() != 's' || this.reader.ReadChar() != 'e') {
              this.reader.RaiseError("Value can't be parsed.");
            }
            nextChar[0] = SkipWhitespaceJSON(this.reader);
            return CBORObject.False;
          }
        case 'n':
          {
            // Parse null
          if (this.reader.ReadChar() != 'u' || this.reader.ReadChar() != 'l' ||
              this.reader.ReadChar() != 'l') {
              this.reader.RaiseError("Value can't be parsed.");
            }
            nextChar[0] = SkipWhitespaceJSON(this.reader);
            return CBORObject.Null;
          }
        case '-':
          {
            // Parse a negative number
            boolean lengthTwo = true;
            c = this.reader.ReadChar();
            if (c < '0' || c > '9') {
              this.reader.RaiseError("JSON number can't be parsed.");
            }
            int cval = -(c - '0');
            int cstart = c;
            StringBuilder sb = null;
            c = this.reader.ReadChar();
            while (c == '-' || c == '+' || c == '.' || (c >= '0' && c <= '9') ||
                   c == 'e' || c == 'E') {
              if (lengthTwo) {
                sb = new StringBuilder();
                sb.append((char)'-');
                sb.append((char)cstart);
                lengthTwo = false;
              }
              sb.append((char)c);
              c = this.reader.ReadChar();
            }
            if (lengthTwo) {
              obj = cval == 0 ?
              CBORDataUtilities.ParseJSONNumber("-0", true, false, true) :
                CBORObject.FromObject(cval);
            } else {
              str = sb.toString();
              obj = CBORDataUtilities.ParseJSONNumber(str);
              if (obj == null) {
                this.reader.RaiseError("JSON number can't be parsed. " + str);
              }
            }
            if (c == -1 || (c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09)) {
              nextChar[0] = c;
            } else {
              nextChar[0] = SkipWhitespaceJSON(this.reader);
            }
            return obj;
          }
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
          {
            // Parse a number
            boolean lengthOne = true;
            int cval = c - '0';
            int cstart = c;
            StringBuilder sb = null;
            c = this.reader.ReadChar();
            while (c == '-' || c == '+' || c == '.' || (c >= '0' && c <= '9') ||
                   c == 'e' || c == 'E') {
              if (lengthOne) {
                sb = new StringBuilder();
                sb.append((char)cstart);
                lengthOne = false;
              }
              sb.append((char)c);
              c = this.reader.ReadChar();
            }
            if (lengthOne) {
              obj = CBORObject.FromObject(cval);
            } else {
              str = sb.toString();
              obj = CBORDataUtilities.ParseJSONNumber(str);
              if (obj == null) {
                this.reader.RaiseError("JSON number can't be parsed. " + str);
              }
            }
            if (c == -1 || (c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09)) {
              nextChar[0] = c;
            } else {
              nextChar[0] = SkipWhitespaceJSON(this.reader);
            }
            return obj;
          }
        default:
          this.reader.RaiseError("Value can't be parsed.");
          break;
      }
      return null;
    }

    private boolean noDuplicates;

    public CBORJson(CharacterInputWithCount reader, boolean noDuplicates) {
      this.reader = reader;
      this.sb = null;
      this.noDuplicates = noDuplicates;
    }

    public CBORObject ParseJSON(boolean objectOrArrayOnly, int[] nextchar) {
      int c;
      CBORObject ret;
      c = SkipWhitespaceJSON(this.reader);
      if (c == '[') {
        ret = this.ParseJSONArray(0);
        nextchar[0] = SkipWhitespaceJSON(this.reader);
        return ret;
      }
      if (c == '{') {
        ret = this.ParseJSONObject(0);
        nextchar[0] = SkipWhitespaceJSON(this.reader);
        return ret;
      }
      if (objectOrArrayOnly) {
        this.reader.RaiseError("A JSON Object must begin with '{' or '['");
      }
      return this.NextJSONValue(c, nextchar, 0);
    }

    static CBORObject ParseJSONValue(
      CharacterInputWithCount reader,
      boolean noDuplicates,
      boolean objectOrArrayOnly,
      int[] nextchar) {
      CBORJson cj = new CBORJson(reader, noDuplicates);
      return cj.ParseJSON(objectOrArrayOnly, nextchar);
    }

    private CBORObject ParseJSONObject(int depth) {
      // Assumes that the last character read was '{'
      if (depth > 1000) {
        this.reader.RaiseError("Too deeply nested");
      }
      int c;
      CBORObject key = null;
      CBORObject obj;
      int[] nextchar = new int[1];
      boolean seenComma = false;
      HashMap<CBORObject, CBORObject> myHashMap = new HashMap<CBORObject, CBORObject>();
      while (true) {
        c = SkipWhitespaceJSON(this.reader);
        switch (c) {
          case -1:
            this.reader.RaiseError("A JSONObject must end with '}'");
            break;
          case '}':
            if (seenComma) {
              // Situation like '{"0"=>1,}'
              this.reader.RaiseError("Trailing comma");
              return null;
            }
            return CBORObject.FromRaw(myHashMap);
            default: {
              // Read the next String
              if (c < 0) {
                this.reader.RaiseError("Unexpected end of data");
                return null;
              }
              if (c != '"') {
                this.reader.RaiseError("Expected a String as a key");
                return null;
              }
              // Parse a String that represents the Object's key
              // The tokenizer already checked the String for invalid
              // surrogate pairs, so just call the CBORObject
              // constructor directly
              obj = CBORObject.FromRaw(this.NextJSONString());
              key = obj;
              if (this.noDuplicates && myHashMap.containsKey(obj)) {
                this.reader.RaiseError("Key already exists: " + key);
                return null;
              }
              break;
            }
        }
        if (SkipWhitespaceJSON(this.reader) != ':') {
          this.reader.RaiseError("Expected a ':' after a key");
        }
        // NOTE: Will overwrite existing value
        myHashMap.put(key, this.NextJSONValue(
          SkipWhitespaceJSON(this.reader),
          nextchar,
          depth));
        switch (nextchar[0]) {
          case ',':
            seenComma = true;
            break;
          case '}':
            return CBORObject.FromRaw(myHashMap);
            default: this.reader.RaiseError("Expected a ',' or '}'");
            break;
        }
      }
    }

    CBORObject ParseJSONArray(int depth) {
      // Assumes that the last character read was '['
      if (depth > 1000) {
        this.reader.RaiseError("Too deeply nested");
      }
      ArrayList<CBORObject> myArrayList = new ArrayList<CBORObject>();
      boolean seenComma = false;
      int[] nextchar = new int[1];
      while (true) {
        int c = SkipWhitespaceJSON(this.reader);
        if (c == ']') {
          if (seenComma) {
            // Situation like '[0,1,]'
            this.reader.RaiseError("Trailing comma");
          }
          return CBORObject.FromRaw(myArrayList);
        }
        if (c == ',') {
          // Situation like '[,0,1,2]' or '[0,,1]'
          this.reader.RaiseError("Empty array element");
        }
        myArrayList.add(
          this.NextJSONValue(
            c,
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
            this.reader.RaiseError("Expected a ',' or ']'");
            break;
        }
      }
    }

    private static final String Hex16 = "0123456789ABCDEF";

    static void WriteJSONStringUnquoted(
      String str,
      StringOutput sb) throws java.io.IOException {
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
        } else if (c < 0x20 || (c >= 0x7f && (c == 0x2028 || c == 0x2029 ||
                    (c >= 0x7f && c <= 0xa0) || c == 0xfeff || c == 0xfffe ||
                    c == 0xffff))) {
          // Control characters, and also the line and paragraph separators
          // which apparently can't appear in JavaScript (as opposed to
          // JSON) strings
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
          } else if (c >= 0x100) {
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
        } else {
          if ((c & 0xfc00) == 0xd800) {
           if (i >= str.length() - 1 || (str.charAt(i + 1) & 0xfc00) != 0xdc00) {
            // TODO: Add JSONOptions for handling of unpaired
            // surrogates in strings (at least U + FFFD/escape seqs.).
            // NOTE: RFC 8259 doesn't prohibit any particular
            // error-handling behavior when a writer of JSON
            // receives a String with an unpaired surrogate.
            throw new CBORException("Unpaired surrogate in String");
           }
          }
          if (!first) {
            if ((c & 0xfc00) == 0xd800) {
              sb.WriteString(str, i, 2);
              ++i;
            } else {
              sb.WriteCodePoint((int)c);
            }
          }
        }
      }
      if (first) {
        sb.WriteString(str);
      }
    }

    static void WriteJSONToInternal(
      CBORObject obj,
      StringOutput writer,
      JSONOptions options) throws java.io.IOException {
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
                CBORUtilities.SingleToString(f)));
            return;
          }
          case CBORObject.CBORObjectTypeDouble: {
            double f = ((Double)thisItem).doubleValue();
            if (((f) == Double.NEGATIVE_INFINITY) || ((f) == Double.POSITIVE_INFINITY) ||
                Double.isNaN(f)) {
              writer.WriteString("null");
              return;
            }
            String dblString = CBORUtilities.DoubleToString(f);
            writer.WriteString(
              CBORObject.TrimDotZero(dblString));
            return;
          }
          case CBORObject.CBORObjectTypeInteger: {
            long longItem = (((Long)thisItem).longValue());
            writer.WriteString(CBORUtilities.LongToString(longItem));
            return;
          }
          case CBORObject.CBORObjectTypeBigInteger: {
            writer.WriteString(((EInteger)thisItem).toString());
            return;
          }
          case CBORObject.CBORObjectTypeExtendedDecimal: {
            EDecimal dec = (EDecimal)thisItem;
            if (dec.IsInfinity() || dec.IsNaN()) {
              writer.WriteString("null");
            } else {
              writer.WriteString(dec.toString());
            }
            return;
          }
          case CBORObject.CBORObjectTypeExtendedFloat: {
            EFloat flo = (EFloat)thisItem;
            if (flo.IsInfinity() || flo.IsNaN()) {
              writer.WriteString("null");
              return;
            }
            if (flo.isFinite() &&
                flo.getExponent().Abs().compareTo(EInteger.FromInt64(2500)) > 0) {
              // Too inefficient to convert to a decimal number
              // from a bigfloat with a very high exponent,
              // so convert to double instead
              double f = flo.ToDouble();
              if (((f) == Double.NEGATIVE_INFINITY) ||
                  ((f) == Double.POSITIVE_INFINITY) || Double.isNaN(f)) {
                writer.WriteString("null");
                return;
              }
              String dblString =
                  CBORUtilities.DoubleToString(f);
              writer.WriteString(
                CBORObject.TrimDotZero(dblString));
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
                options.getBase64Padding());
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
                options.getBase64Padding());
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
              WriteJSONToInternal(i, writer, options);
              first = false;
            }
            writer.WriteCodePoint((int)']');
            break;
          }
          case CBORObject.CBORObjectTypeExtendedRational: {
            ERational dec = (ERational)thisItem;
            EDecimal f = dec.ToEDecimalExactIfPossible(
              EContext.Decimal128.WithUnlimitedExponents());
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
              if (key.getItemType() != CBORObject.CBORObjectTypeTextString ||
              key.isTagged()) {
                // treat a non-text-String item or a tagged item
                // as having non-String keys
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
                WriteJSONToInternal(value, writer, options);
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
                WriteJSONToInternal(value, writer, options);
                first = false;
              }
              writer.WriteCodePoint((int)'}');
            }
            break;
          }
        default: throw new IllegalStateException("Unexpected item type");
      }
    }
  }
