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

// NOTE: Certain differences from CBORJson2 are noted.

  final class CBORJson3 {
    // JSON parsing method
    private int SkipWhitespaceJSON() {
      while (this.index < this.endPos) {
        char c = this.jstring.charAt(this.index++);
        if (c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09) {
          return c;
        }
      }
      return -1;
    }

    void RaiseError(String str) {
      throw new CBORException(str + " (approx. offset: " +
        Math.max(0, this.index - 1) + ")");
    }

    // NOTE: Differs from CBORJson2
    private final String jstring;
    private final JSONOptions options;
    private StringBuilder sb;
    private int index;
    private int endPos;

    private String NextJSONString() {
      int c;
      this.sb = (this.sb == null) ? (new StringBuilder()) : this.sb;
      this.sb.delete(0, this.sb.length());
      while (true) {
        c = this.index < this.endPos ? ((int)this.jstring.charAt(this.index++)) &
          0xffff : -1;
        if (c == -1 || c < 0x20) {
          this.RaiseError("Unterminated String");
        }
        switch (c) {
          case '\\':
            c = this.index < this.endPos ? ((int)this.jstring.charAt(this.index++)) &
              0xffff : -1;
            switch (c) {
              case '\\':
              case '/':
              case '\"':
                // Slash is now allowed to be escaped under RFC 8259
                this.sb.append((char)c);
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
                  int ch = this.index < this.endPos ?
                    ((int)this.jstring.charAt(this.index++)) : -1;
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
                    this.RaiseError(
                      "Invalid Unicode escaped character");
                  }
                }
                if ((c & 0xf800) != 0xd800) {
                  // Non-surrogate
                  this.sb.append((char)c);
                } else if ((c & 0xfc00) == 0xd800) {
                  int ch = this.index < this.endPos ?
                    ((int)this.jstring.charAt(this.index++)) : -1;
                  if (ch != '\\' || (this.index < this.endPos ?
                      ((int)this.jstring.charAt(this.index++)) : -1) != 'u') {
                    this.RaiseError("Invalid escaped character");
                  }
                  int c2 = 0;
                  for (int i = 0; i < 4; ++i) {
                    ch = this.index < this.endPos ?
                      ((int)this.jstring.charAt(this.index++)) : -1;
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
                      this.RaiseError(
                        "Invalid Unicode escaped character");
                    }
                  }
                  if ((c2 & 0xfc00) != 0xdc00) {
                    this.RaiseError("Unpaired surrogate code point");
                  } else {
                    this.sb.append((char)c);
                    this.sb.append((char)c2);
                  }
                } else {
                  this.RaiseError("Unpaired surrogate code point");
                }
                break;
              }
              default: {
                this.RaiseError("Invalid escaped character");
                break;
              }
            }
            break;
          case 0x22: // double quote
            return this.sb.toString();
          default: {
            // NOTE: Differs from CBORJson2
            if ((c & 0xf800) != 0xd800) {
              // Non-surrogate
              this.sb.append((char)c);
            } else if ((c & 0xfc00) == 0xd800 && this.index < this.endPos &&
              (this.jstring.charAt(this.index) & 0xfc00) == 0xdc00) {
              // Surrogate pair
              this.sb.append((char)c);
              this.sb.append(this.jstring.charAt(this.index));
              ++this.index;
            } else {
              this.RaiseError("Unpaired surrogate code point");
            }
            break;
          }
        }
      }
    }

    private CBORObject NextJSONNegativeNumber(
      int[] nextChar) {
      // Assumes the last character read was '-'
      // DebugUtility.Log("js=" + (jstring));
      CBORObject obj;
      int numberStartIndex = this.index - 1;
      int c = this.index < this.endPos ? ((int)this.jstring.charAt(this.index++)) &
        0xffff : -1;
      if (c < '0' || c > '9') {
        this.RaiseError("JSON number can't be parsed.");
      }
      if (this.index < this.endPos && c != '0') {
        // Check for negative single-digit
        int c2 = ((int)this.jstring.charAt(this.index)) & 0xffff;
        if (c2 == ',' || c2 == ']' || c2 == '}') {
          ++this.index;
          obj = CBORDataUtilities.ParseSmallNumberAsNegative(
            c - '0',
            this.options);
          nextChar[0] = c2;
          return obj;
        } else if (c2 == 0x20 || c2 == 0x0a || c2 == 0x0d || c2 == 0x09) {
          ++this.index;
          obj = CBORDataUtilities.ParseSmallNumberAsNegative(
            c - '0',
            this.options);
          nextChar[0] = this.SkipWhitespaceJSON();
          return obj;
        }
      }
      // NOTE: Differs from CBORJson2, notably because the whole
      // rest of the String is checked whether the beginning of the rest
      // is a JSON number
      int[] endIndex = new int[1];
      endIndex[0] = numberStartIndex;
      obj = CBORDataUtilities.ParseJSONNumber(
              this.jstring,
              numberStartIndex,
              this.endPos - numberStartIndex,
              this.options,
              endIndex);
      int numberEndIndex = endIndex[0];
      this.index = numberEndIndex >= this.endPos ? this.endPos :
         (numberEndIndex + 1);
      if (obj == null) {
        int strlen = numberEndIndex - numberStartIndex;
        String errstr = this.jstring.substring(numberStartIndex, (numberStartIndex)+(Math.min(100, strlen)));
        if (strlen > 100) {
          errstr += "...";
        }
        this.RaiseError("JSON number can't be parsed. " + errstr);
      }

      c = numberEndIndex >= this.endPos ? -1 :
this.jstring.charAt(numberEndIndex);
      // check if character can validly appear after a JSON number
      if (c != ',' && c != ']' && c != '}' && c != -1 &&
          c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09) {
        this.RaiseError("Invalid character after JSON number");
      }
      // DebugUtility.Log("endIndex="+endIndex[0]+", "+
      // this.jstring.substring(endIndex[0], (endIndex[0])+(// Math.min(20, this.endPos-endIndex[0]))));
      if (c == -1 || (c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09)) {
        nextChar[0] = c;
      } else {
        nextChar[0] = this.SkipWhitespaceJSON();
      }
      return obj;
    }

    private CBORObject NextJSONNonnegativeNumber(int c, int[] nextChar) {
      // Assumes the last character read was a digit
      CBORObject obj = null;
      int cval = c - '0';
      int cstart = c;
      int startIndex = this.index - 1;
      boolean needObj = true;
      int numberStartIndex = this.index - 1;
      // DebugUtility.Log("js=" + (jstring));
      c = this.index < this.endPos ? ((int)this.jstring.charAt(this.index++)) &
        0xffff : -1;
      if (!(c == '-' || c == '+' || c == '.' || (c >= '0' && c <= '9') ||
          c == 'e' || c == 'E')) {
        // Optimize for common case where JSON number
        // is a single digit without sign or exponent
        obj = CBORDataUtilities.ParseSmallNumber(cval, this.options);
        needObj = false;
      } else if (c >= '0' && c <= '9') {
        int csecond = c;
        if (cstart == '0') {
          // Leading zero followed by any digit is not allowed
          this.RaiseError("JSON number can't be parsed.");
        }
        cval = (cval * 10) + (int)(c - '0');
        c = this.index < this.endPos ? ((int)this.jstring.charAt(this.index++)) : -1;
        if (c >= '0' && c <= '9') {
          int digits = 2;
          while (digits < 9 && (c >= '0' && c <= '9')) {
            cval = (cval * 10) + (int)(c - '0');
            c = this.index < this.endPos ?
              ((int)this.jstring.charAt(this.index++)) : -1;
            ++digits;
          }
          if (!(c == 'e' || c == 'E' || c == '.' || (c >= '0' && c <=
                '9'))) {
            // All-digit number that's short enough
            obj = CBORDataUtilities.ParseSmallNumber(cval, this.options);
            needObj = false;
          }
        } else if (!(c == '-' || c == '+' || c == '.' || c == 'e' || c
            == 'E')) {
          // Optimize for common case where JSON number
          // is two digits without sign, decimal point, or exponent
          obj = CBORDataUtilities.ParseSmallNumber(cval, this.options);
          needObj = false;
        }
      }
      if (needObj) {
        // NOTE: Differs from CBORJson2, notably because the whole
        // rest of the String is checked whether the beginning of the rest
        // is a JSON number
        int[] endIndex = new int[1];
        endIndex[0] = numberStartIndex;
        obj = CBORDataUtilities.ParseJSONNumber(
              this.jstring,
              numberStartIndex,
              this.endPos - numberStartIndex,
              this.options,
              endIndex);
        int numberEndIndex = endIndex[0];
        this.index = numberEndIndex >= this.endPos ? this.endPos :
           (numberEndIndex + 1);
        if (obj == null) {
          int strlen = numberEndIndex - numberStartIndex;
          String errstr = this.jstring.substring(numberStartIndex, (numberStartIndex)+(Math.min(100, strlen)));
          if (strlen > 100) {
            errstr += "...";
          }
          this.RaiseError("JSON number can't be parsed. " + errstr);
        }

        c = numberEndIndex >= this.endPos ? -1 :
this.jstring.charAt(numberEndIndex);
        // check if character can validly appear after a JSON number
        if (c != ',' && c != ']' && c != '}' && c != -1 &&
          c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09) {
          this.RaiseError("Invalid character after JSON number");
        }
        // DebugUtility.Log("endIndex="+endIndex[0]+", "+
        // this.jstring.substring(endIndex[0], (endIndex[0])+(// Math.min(20, this.endPos-endIndex[0]))));
      }
      if (c == -1 || (c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09)) {
        nextChar[0] = c;
      } else {
        nextChar[0] = this.SkipWhitespaceJSON();
      }
      return obj;
    }

    private CBORObject NextJSONValue(
      int firstChar,
      int[] nextChar,
      int depth) {
      int c = firstChar;
      CBORObject obj = null;
      if (c < 0) {
        this.RaiseError("Unexpected end of data");
      }
      switch (c) {
        case '"': {
          // Parse a String
          // The tokenizer already checked the String for invalid
          // surrogate pairs, so just call the CBORObject
          // constructor directly
          obj = CBORObject.FromRaw(this.NextJSONString());
          nextChar[0] = this.SkipWhitespaceJSON();
          return obj;
        }
        case '{': {
          // Parse an object
          obj = this.ParseJSONObject(depth + 1);
          nextChar[0] = this.SkipWhitespaceJSON();
          return obj;
        }
        case '[': {
          // Parse an array
          obj = this.ParseJSONArray(depth + 1);
          nextChar[0] = this.SkipWhitespaceJSON();
          return obj;
        }
        case 't': {
          // Parse true
          if (this.endPos - this.index <= 2 ||
            (((int)this.jstring.charAt(this.index)) & 0xFF) != 'r' ||
            (((int)this.jstring.charAt(this.index + 1)) & 0xFF) != 'u' ||
            (((int)this.jstring.charAt(this.index + 2)) & 0xFF) != 'e') {
            this.RaiseError("Value can't be parsed.");
          }
          this.index += 3;
          nextChar[0] = this.SkipWhitespaceJSON();
          return CBORObject.True;
        }
        case 'f': {
          // Parse false
          if (this.endPos - this.index <= 3 ||
            (((int)this.jstring.charAt(this.index)) & 0xFF) != 'a' ||
            (((int)this.jstring.charAt(this.index + 1)) & 0xFF) != 'l' ||
            (((int)this.jstring.charAt(this.index + 2)) & 0xFF) != 's' ||
            (((int)this.jstring.charAt(this.index + 3)) & 0xFF) != 'e') {
            this.RaiseError("Value can't be parsed.");
          }
          this.index += 4;
          nextChar[0] = this.SkipWhitespaceJSON();
          return CBORObject.False;
        }
        case 'n': {
          // Parse null
          if (this.endPos - this.index <= 2 ||
            (((int)this.jstring.charAt(this.index)) & 0xFF) != 'u' ||
            (((int)this.jstring.charAt(this.index + 1)) & 0xFF) != 'l' ||
            (((int)this.jstring.charAt(this.index + 2)) & 0xFF) != 'l') {
            this.RaiseError("Value can't be parsed.");
          }
          this.index += 3;
          nextChar[0] = this.SkipWhitespaceJSON();
          return CBORObject.Null;
        }
        case '-': {
          // Parse a negative number
          return this.NextJSONNegativeNumber(nextChar);
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
          // Parse a nonnegative number
          return this.NextJSONNonnegativeNumber(c, nextChar);
        }
        default:
          this.RaiseError("Value can't be parsed.");
          break;
      }
      return null;
    }

    public CBORJson3(String jstring, int index, int endPos, JSONOptions
      options) {
      this.sb = null;
      this.jstring = jstring;
      this.index = index;
      this.endPos = endPos;
      this.options = options;
    }

    public CBORObject ParseJSON(int[] nextchar) {
      int c;
      CBORObject ret;
      c = this.SkipWhitespaceJSON();
      if (c == '[') {
        ret = this.ParseJSONArray(0);
        nextchar[0] = this.SkipWhitespaceJSON();
        return ret;
      }
      if (c == '{') {
        ret = this.ParseJSONObject(0);
        nextchar[0] = this.SkipWhitespaceJSON();
        return ret;
      }
      return this.NextJSONValue(c, nextchar, 0);
    }

    static CBORObject ParseJSONValue(
      String jstring,
      int index,
      int endPos,
      JSONOptions options) {
      int[] nextchar = new int[1];
      CBORJson3 cj = new CBORJson3(jstring, index, endPos, options);
      CBORObject obj = cj.ParseJSON(nextchar);
      if (nextchar[0] != -1) {
        cj.RaiseError("End of String not reached");
      }
      return obj;
    }

    static CBORObject ParseJSONValue(
      String jstring,
      int index,
      int endPos,
      JSONOptions options,
      int[] nextchar) {
      CBORJson3 cj = new CBORJson3(jstring, index, endPos, options);
      return cj.ParseJSON(nextchar);
    }

    private CBORObject ParseJSONObject(int depth) {
      // Assumes that the last character read was '{'
      if (depth > 1000) {
        this.RaiseError("Too deeply nested");
      }
      int c;
      CBORObject key = null;
      CBORObject obj;
      int[] nextchar = new int[1];
      boolean seenComma = false;
      HashMap<CBORObject, CBORObject> myHashMap = new HashMap<CBORObject, CBORObject>();
      while (true) {
        c = this.SkipWhitespaceJSON();
        switch (c) {
          case -1:
            this.RaiseError("A JSON Object must end with '}'");
            break;
          case '}':
            if (seenComma) {
              // Situation like '{"0"=>1,}'
              this.RaiseError("Trailing comma");
              return null;
            }
            return CBORObject.FromRaw(myHashMap);
          default: {
            // Read the next String
            if (c < 0) {
              this.RaiseError("Unexpected end of data");
              return null;
            }
            if (c != '"') {
              this.RaiseError("Expected a String as a key");
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
              this.RaiseError("Key already exists: " + key);
              return null;
            }
            break;
          }
        }
        if (this.SkipWhitespaceJSON() != ':') {
          this.RaiseError("Expected a ':' after a key");
        }
        // NOTE: Will overwrite existing value
        myHashMap.put(key, this.NextJSONValue(
            this.SkipWhitespaceJSON(),
            nextchar,
            depth));
        switch (nextchar[0]) {
          case ',':
            seenComma = true;
            break;
          case '}':
            return CBORObject.FromRaw(myHashMap);
          default: this.RaiseError("Expected a ',' or '}'");
            break;
        }
      }
    }

    CBORObject ParseJSONArray(int depth) {
      // Assumes that the last character read was '['
      if (depth > 1000) {
        this.RaiseError("Too deeply nested");
      }
      ArrayList<CBORObject> myArrayList = new ArrayList<CBORObject>();
      boolean seenComma = false;
      int[] nextchar = new int[1];
      while (true) {
        int c = this.SkipWhitespaceJSON();
        if (c == ']') {
          if (seenComma) {
            // Situation like '[0,1,]'
            this.RaiseError("Trailing comma");
          }
          return CBORObject.FromRaw(myArrayList);
        }
        if (c == ',') {
          // Situation like '[,0,1,2]' or '[0,,1]'
          this.RaiseError("Empty array element");
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
          default: this.RaiseError("Expected a ',' or ']'");
            break;
        }
      }
    }
  }
