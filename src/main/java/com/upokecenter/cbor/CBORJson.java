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
    private static final String Hex16 = "0123456789ABCDEF";
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
      this.sb.delete(0, this.sb.length());
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
              case 'u': { // Unicode escape
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
                    this.reader.RaiseError (
                      "Invalid Unicode escaped character");
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
                      this.reader.RaiseError("Invalid Unicode escaped" +
"\u0020character");
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
              default: {
                this.reader.RaiseError("Invalid escaped character");
                break;
              }
            }
            break;
          case 0x22: // double quote
            return this.sb.toString();
          default: {
            // NOTE: Assumes the character reader
            // throws an error on finding illegal surrogate
            // pairs in the String or invalid encoding
            // in the stream
            if ((c >> 16) == 0) {
              this.sb.append((char)c);
            } else {
              this.sb.append((char)((((c - 0x10000) >> 10) & 0x3ff) |
                  0xd800));
              this.sb.append((char)(((c - 0x10000) & 0x3ff) | 0xdc00));
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
        case '"': {
          // Parse a String
          // The tokenizer already checked the String for invalid
          // surrogate pairs, so just call the CBORObject
          // constructor directly
          obj = CBORObject.FromRaw(this.NextJSONString());
          nextChar[0] = SkipWhitespaceJSON(this.reader);
          return obj;
        }
        case '{': {
          // Parse an object
          obj = this.ParseJSONObject(depth + 1);
          nextChar[0] = SkipWhitespaceJSON(this.reader);
          return obj;
        }
        case '[': {
          // Parse an array
          obj = this.ParseJSONArray(depth + 1);
          nextChar[0] = SkipWhitespaceJSON(this.reader);
          return obj;
        }
        case 't': {
          // Parse true
          if (this.reader.ReadChar() != 'r' || this.reader.ReadChar() != 'u' ||
            this.reader.ReadChar() != 'e') {
            this.reader.RaiseError("Value can't be parsed.");
          }
          nextChar[0] = SkipWhitespaceJSON(this.reader);
          return CBORObject.True;
        }
        case 'f': {
          // Parse false
          if (this.reader.ReadChar() != 'a' || this.reader.ReadChar() != 'l' ||
            this.reader.ReadChar() != 's' || this.reader.ReadChar() != 'e') {
            this.reader.RaiseError("Value can't be parsed.");
          }
          nextChar[0] = SkipWhitespaceJSON(this.reader);
          return CBORObject.False;
        }
        case 'n': {
          // Parse null
          if (this.reader.ReadChar() != 'u' || this.reader.ReadChar() != 'l' ||
            this.reader.ReadChar() != 'l') {
            this.reader.RaiseError("Value can't be parsed.");
          }
          nextChar[0] = SkipWhitespaceJSON(this.reader);
          return CBORObject.Null;
        }
        case '-': {
          // Parse a negative number
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
            if (sb == null) {
              sb = new StringBuilder();
              sb.append('-');
              sb.append((char)cstart);
            }
            sb.append((char)c);
            c = this.reader.ReadChar();
          }
          if (sb == null) {
            // Single-digit number
             str = new String(new char[] { '-', (char)cstart });
           } else {
            str = sb.toString();
          }
          obj = CBORDataUtilities.ParseJSONNumber(str, this.options);
          if (obj == null) {
              String errstr = (str.length() <= 100) ? str : (str.substring(0, 100) +
"...");
              this.reader.RaiseError("JSON number can't be parsed. " + errstr);
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
        case '9': {
          // Parse a number
          int cval = c - '0';
          int cstart = c;
          StringBuilder sb = null;
          c = this.reader.ReadChar();
          while (c == '-' || c == '+' || c == '.' || (c >= '0' && c <= '9') ||
            c == 'e' || c == 'E') {
            if (sb == null) {
              sb = new StringBuilder();
              sb.append((char)cstart);
            }
            sb.append((char)c);
            c = this.reader.ReadChar();
          }
          if (sb == null) {
             // Single-digit number
             str = new String(new char[] { (char)cstart });
           } else {
             str = sb.toString();
          }
          obj = CBORDataUtilities.ParseJSONNumber(str, this.options);
          if (obj == null) {
              String errstr = (str.length() <= 100) ? str : (str.substring(0, 100) +
"...");
              this.reader.RaiseError("JSON number can't be parsed. " + errstr);
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

    private final JSONOptions options;

    public CBORJson(CharacterInputWithCount reader, JSONOptions options) {
      this.reader = reader;
      this.sb = null;
      this.options = options;
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
      JSONOptions options,
      boolean objectOrArrayOnly,
      int[] nextchar) {
      CBORJson cj = new CBORJson(reader, options);
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
            this.reader.RaiseError("A JSON Object must end with '}'");
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
            if (!this.options.getAllowDuplicateKeys() &&
              myHashMap.containsKey(obj)) {
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
        myHashMap.put(key, this.NextJSONValue (
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

    static void WriteJSONStringUnquoted(
      String str,
      StringOutput sb,
      JSONOptions options) throws java.io.IOException {
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
              // NOTE: RFC 8259 doesn't prohibit any particular
              // error-handling behavior when a writer of JSON
              // receives a String with an unpaired surrogate.
              if (options.getReplaceSurrogates()) {
                if (first) {
                  first = false;
                  sb.WriteString(str, 0, i);
                }
                // Replace unpaired surrogate with U+FFFD
                c = (char)0xfffd;
              } else {
                throw new CBORException("Unpaired surrogate in String");
              }
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
      if (obj.isNumber()) {
        writer.WriteString(CBORNumber.FromCBORObject(obj).ToJSONString());
        return;
      }
      switch (obj.getType()) {
        case Integer:
        case FloatingPoint: {
          CBORObject untaggedObj = obj.Untag();
          writer.WriteString (
            CBORNumber.FromCBORObject(untaggedObj).ToJSONString());
          break;
        }
        case Boolean: {
          if (obj.isTrue()) {
            writer.WriteString("true");
            return;
          }
          if (obj.isFalse()) {
            writer.WriteString("false");
            return;
          }
          return;
        }
        case SimpleValue: {
          writer.WriteString("null");
          return;
        }
        case ByteString: {
          byte[] byteArray = obj.GetByteString();
          if (byteArray.length == 0) {
            writer.WriteString("\"\"");
            return;
          }
          writer.WriteCodePoint((int)'\"');
          if (obj.HasTag(22)) {
            // Base64 with padding
            Base64.WriteBase64(
              writer,
              byteArray,
              0,
              byteArray.length,
              true);
          } else if (obj.HasTag(23)) {
            // Write as base16
            for (int i = 0; i < byteArray.length; ++i) {
              writer.WriteCodePoint((int)Hex16.charAt((byteArray[i] >> 4) & 15));
              writer.WriteCodePoint((int)Hex16.charAt(byteArray[i] & 15));
            }
          } else {
            // Base64url no padding
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
        case TextString: {
          String thisString = obj.AsString();
          if (thisString.length() == 0) {
            writer.WriteString("\"\"");
            return;
          }
          writer.WriteCodePoint((int)'\"');
          WriteJSONStringUnquoted(thisString, writer, options);
          writer.WriteCodePoint((int)'\"');
          break;
        }
        case Array: {
          writer.WriteCodePoint((int)'[');
          for (int i = 0; i < obj.size(); ++i) {
            if (i > 0) {
              writer.WriteCodePoint((int)',');
            }
            WriteJSONToInternal(obj.get(i), writer, options);
          }
          writer.WriteCodePoint((int)']');
          break;
        }
        case Map: {
          boolean first = true;
          boolean hasNonStringKeys = false;
          Collection<Map.Entry<CBORObject, CBORObject>> entries =
            obj.getEntries();
          for (Map.Entry<CBORObject, CBORObject> entry : entries) {
            CBORObject key = entry.getKey();
            if (key.getType() != CBORType.TextString ||
              key.isTagged()) {
              // treat a non-text-String item or a tagged item
              // as having non-String keys
              hasNonStringKeys = true;
              break;
            }
          }
          if (!hasNonStringKeys) {
            writer.WriteCodePoint((int)'{');
            for (Map.Entry<CBORObject, CBORObject> entry : entries) {
              CBORObject key = entry.getKey();
              CBORObject value = entry.getValue();
              if (!first) {
                writer.WriteCodePoint((int)',');
              }
              writer.WriteCodePoint((int)'\"');
              WriteJSONStringUnquoted(key.AsString(), writer, options);
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
            for (Map.Entry<CBORObject, CBORObject> entry : entries) {
              CBORObject key = entry.getKey();
              CBORObject value = entry.getValue();
              String str = (key.getType() == CBORType.TextString) ?
                key.AsString() : key.ToJSONString();
              if (stringMap.containsKey(str)) {
                throw new
                CBORException("Duplicate JSON String equivalents of map" +
"\u0020keys");
              }
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
              WriteJSONStringUnquoted((String)key, writer, options);
              writer.WriteCodePoint((int)'\"');
              writer.WriteCodePoint((int)':');
              WriteJSONToInternal(value, writer, options);
              first = false;
            }
            writer.WriteCodePoint((int)'}');
          }
          break;
        }
        default: throw new IllegalStateException("Unexpected item" +
"\u0020type");
      }
    }
  }
