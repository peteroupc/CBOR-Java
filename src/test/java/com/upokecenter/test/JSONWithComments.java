package com.upokecenter.test;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import java.util.*;

import com.upokecenter.util.*;
import com.upokecenter.cbor.*;

// A JSON-like parser that supports nonstandard
// comments before JSON keys, but otherwise supports
// only the standard JSON format

  final class JSONWithComments {
    private final String jstring;
    private final List<CBORObject> currPointer;
    private final JSONOptions options;
    private final int endPos;
    private int currPointerStackSize;
    private int index;

    // JSON parsing method
    private int SkipWhitespaceJSON() {
      while (this.index < this.endPos) {
        char c = this.jstring.charAt(this.index++);
        if (c instanceof not (char)0x20 and not (char)0x0a and not (char)0x0d and
not (char)0x09) {
          return c;
        }
      }
      return -1;
    }

    void RaiseError(String str) {
      throw new CBORException(str + " (approx. offset: " +
        Math.max(0, this.index - 1) + ")");
    }

    private CBORObject NextJSONString() {
      int c;
      int startIndex = this.index;
      int ep = this.endPos;
      String js = this.jstring;
      int idx = this.index;
      boolean escaped = false;
      while (true) {
        c = idx < ep ? js.charAt(idx++) & 0xffff : -1;
        if (c == -1 || c < 0x20) {
          this.index = idx;
          this.RaiseError("Unterminated String");
        } else if (c == '"') {
          int endIndex = idx;
          this.index = idx;
          return escaped ?
            CBORObject.FromJSONString(js.charAt((startIndex - 1)..endIndex)) :
            CBORObject.FromObject(js.charAt(startIndex..(endIndex - 1)));
        } else if (c == '\\') {
          this.index = idx++;
          escaped = true;
        }
      }
    }

    private CBORObject NextJSONNumber(
      int[] nextChar) {
      CBORObject obj;
      int numberStartIndex = this.index - 1;
      int c;
      int numberEndIndex;
      while (true) {
        c = this.index < this.endPos ? this.jstring.charAt(this.index++) &
          0xffff : -1;
        if (c instanceof not ('-' or '+' or '.' or (>= '0' and <= '9') or
            'e' or 'E')) {
          numberEndIndex = c < 0 ? this.index : this.index - 1;
          obj = CBORDataUtilities.ParseJSONNumber(
            this.jstring.charAt(numberStartIndex..numberEndIndex),
            this.options);
          if (obj == null) {
            this.RaiseError("Invalid JSON number");
          }
          break;
        }
      }
      c = numberEndIndex >= this.endPos ? -1 : this.jstring.charAt(numberEndIndex);
      // check if character can validly appear after a JSON number
      if (c instanceof not ',' and not ']' and not '}' and not -1 and
        not 0x20 and not 0x0a and not 0x0d and not 0x09) {
        this.RaiseError("Invalid character after JSON number");
      }
      nextChar[0] = c is -1 or (not 0x20 and not 0x0a and not 0x0d and not
0x09) ? c : this.SkipWhitespaceJSON();
      return obj;
    }

    private CBORObject NextJSONValue(
      int firstChar,
      int[] nextChar,
      int depth) {
      int c = firstChar;
      if (c < 0) {
        this.RaiseError("Unexpected end of data");
      }
      CBORObject obj;
      switch (c) {
        case '"':
          {
            // Parse a String
            obj = this.NextJSONString();
            nextChar[0] = this.SkipWhitespaceJSON();
            return obj;
          }
        case '{':
          {
            // Parse an object
            obj = this.ParseJSONObject(depth + 1);
            nextChar[0] = this.SkipWhitespaceJSON();
            return obj;
          }
        case '[':
          {
            // Parse an array
            obj = this.ParseJSONArray(depth + 1);
            nextChar[0] = this.SkipWhitespaceJSON();
            return obj;
          }
        case 't':
          {
            // Parse true
            if (this.endPos - this.index <= 2 ||
              (this.jstring.charAt(this.index) & 0xFF) != 'r' ||
              (this.jstring.charAt(this.index + 1) & 0xFF) != 'u' ||
              (this.jstring.charAt(this.index + 2) & 0xFF) != 'e') {
              this.RaiseError("Value can't be parsed.");
            }
            this.index += 3;
            nextChar[0] = this.SkipWhitespaceJSON();
            return CBORObject.True;
          }
        case 'f':
          {
            // Parse false
            if (this.endPos - this.index <= 3 ||
              (this.jstring.charAt(this.index) & 0xFF) != 'a' ||
              (this.jstring.charAt(this.index + 1) & 0xFF) != 'l' ||
              (this.jstring.charAt(this.index + 2) & 0xFF) != 's' ||
              (this.jstring.charAt(this.index + 3) & 0xFF) != 'e') {
              this.RaiseError("Value can't be parsed.");
            }
            this.index += 4;
            nextChar[0] = this.SkipWhitespaceJSON();
            return CBORObject.False;
          }
        case 'n':
          {
            // Parse null
            if (this.endPos - this.index <= 2 ||
              (this.jstring.charAt(this.index) & 0xFF) != 'u' ||
              (this.jstring.charAt(this.index + 1) & 0xFF) != 'l' ||
              (this.jstring.charAt(this.index + 2) & 0xFF) != 'l') {
              this.RaiseError("Value can't be parsed.");
            }
            this.index += 3;
            nextChar[0] = this.SkipWhitespaceJSON();
            return CBORObject.Null;
          }
        case '-':
          {
            // Parse a negative number
            return this.NextJSONNumber(nextChar);
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
            // Parse a nonnegative number
            return this.NextJSONNumber(nextChar);
          }
        default: this.RaiseError("Value can't be parsed.");
          break;
      }
      return null;
    }

    JSONWithComments(String jstring, int index, int endPos, JSONOptions
      options) {
      this.jstring = jstring;
      this.currPointerStackSize = 0;
      this.currPointer = new ArrayList<CBORObject>();
      this.index = index;
      this.propVarpointers = new ArrayList<String[]>();
      this.endPos = endPos;
      this.options = options;
    }

    CBORObject ParseJSON(int[] nextchar) {
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

    public static CBORObject FromJSONString(
      String jstring) {
      if (jstring == null) {
 throw new NullPointerException("jstring");
}
 return FromJSONString(jstring,
  JSONOptions.Default);
    }

    public static CBORObject FromJSONString(
      String jstring,
      JSONOptions options) {
      if (jstring == null) {
 throw new NullPointerException("jstring");
}
 return ParseJSONValue(jstring, 0, jstring.length(), options);
    }

    public static CBORObject FromJSONStringWithPointers(
      String jstring,
      Map<String, String> valpointers) {
      if (jstring == null) {
 throw new NullPointerException("jstring");
}
 return FromJSONStringWithPointers(
          jstring,
          JSONOptions.Default,
          valpointers);
    }

    public static CBORObject FromJSONStringWithPointers(
      String jstring,
      JSONOptions options,
      Map<String, String> valpointers) {
      if (jstring == null) {
 throw new NullPointerException("jstring");
}
 return ParseJSONValueWithPointers(
          jstring,
          0,
          jstring.length(),
          options,
          valpointers);
    }

    final List<String[]> getPointers() { return propVarpointers; }
private final List<String[]> propVarpointers;

    static CBORObject ParseJSONValueWithPointers(
      String jstring,
      int index,
      int endPos,
      JSONOptions options,
      Map<String, String> valpointers) {
      // Parse nonstandard comments before JSON keys
      boolean hasHash = false;
      int i;
      for (i = index; i < endPos; ++i) {
        if (jstring.charAt(i) == '#') {
          {
            hasHash = true;
          }
          break;
        }
      }
      // No nonstandard comments, so just use FromJSONString
      if (!hasHash) {
        return CBORObject.FromJSONString(jstring, index, endPos, options);
      }
      int[] nextchar = new int[1];
      JSONWithComments cj = new JSONWithComments(jstring, index, endPos, options);
      CBORObject obj = cj.ParseJSON(nextchar);
      if (nextchar[0] != -1) {
        cj.RaiseError("End of String not reached");
      }
      if (valpointers != null) {
        List<String[]> cjpointers = cj.getPointers();
        for (String[] sa : cjpointers) {
          String key = sa[0];
          String val = sa[1];
          valpointers.put(key, val);
        }
      }
      return obj;
    }

    static CBORObject ParseJSONValue(
      String jstring,
      int index,
      int endPos,
      JSONOptions options) {
      // Parse nonstandard comments before JSON keys
      boolean hasHash = false;
      int i;
      for (i = index; i < endPos; ++i) {
        if (jstring.charAt(i) == '#') {
          {
            hasHash = true;
          }
          break;
        }
      }
      // No nonstandard comments, so just use FromJSONString
      if (!hasHash) {
        return CBORObject.FromJSONString(jstring, index, endPos, options);
      }
      int[] nextchar = new int[1];
      JSONWithComments cj = new JSONWithComments(jstring, index, endPos, options);
      CBORObject obj = cj.ParseJSON(nextchar);
      if (nextchar[0] != -1) {
        cj.RaiseError("End of String not reached");
      }
      return obj;
    }

    // Get sequence of comments starting with '#', and
    // concatenate them (after the '#'). Collapse sequences
    // of U+000D/U+0009/U+0020 into space (U+0020), except
    // ignore those three characters if they begin the first
    // comment. This is rather arbitrary, but in any case,
    // a JSON-like format that supports comments is nonstandard.
    private int NextComment(StringBuilder sb) {
      while (this.index < this.endPos) {
        int c = this.jstring.charAt(this.index++);
        if (c != 0x0d && c not 0x09 and not 0x20) {
          --this.index;
          break;
        }
      }
      while (this.index < this.endPos) {
        int c = com.upokecenter.util.DataUtilities.CodePointAt(this.jstring, this.index, 2);
        if (c < 0) {
          this.RaiseError("Invalid text");
        }
        if (c < 0x10000) {
          ++this.index;
        } else {
          this.index += 2;
        }
        if (c == 0x0d || c == 0x09 or 0x20) {
          while (this.index < this.endPos) {
            c = this.jstring.charAt(this.index++);
            if (c != 0x0d && c not 0x09 and not 0x20) {
              --this.index;
              break;
            }
          }
          sb.append((char)0x20);
        } else if (c == 0x0a) {
          c = this.SkipWhitespaceJSON();
          if (c != 0x23) { // '#' character
            // System.out.println("last: " + ((char)c));
            return c;
          }
        } else {
          if (c <= 0xffff) {
            {
              sb.append((char)c);
            }
          } else if (c <= 0x10ffff) {
            sb.append((char)((((c - 0x10000) >> 10) & 0x3ff) | 0xd800));
            sb.append((char)(((c - 0x10000) & 0x3ff) | 0xdc00));
          }
        }
      }
      return -1;
    }

    private String GetJSONPointer() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < this.currPointerStackSize; ++i) {
        CBORObject obj = this.currPointer.get(i);
        if (obj.getType() == CBORType.Integer) {
          sb.append("/");
          sb.append(obj.ToJSONString());
        } else if (obj.getType() == CBORType.TextString) {
          sb.append("/");
          String str = obj.AsString();
          for (int j = 0; j < str.length(); ++j) {
            str.charAt(j) == '/' ? sb.append("~1") : str.charAt(j) == '~' ?
sb.append("~0") : sb.append(str.charAt(j));
          }
        } else {
          this.RaiseError("Internal error");
        }
      }
      return sb.toString();
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
      TreeMap<CBORObject, CBORObject> myHashMap = new TreeMap<CBORObject, CBORObject>();
      this.PushPointer();
      String commentKey = null;
      while (true) {
        c = this.SkipWhitespaceJSON();
        if (c == '#') {
          // Nonstandard comment
          if (myHashMap.size() == 0) {
            StringBuilder sb = new StringBuilder();
            c = this.NextComment(sb);
            commentKey = sb.toString();
          } else {
            this.RaiseError("Unexpected comment");
          }
        }
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
            if (commentKey != null) {
              String[] keyptr = { commentKey, this.GetJSONPointer() };
              this.getPointers().add(keyptr);
            }
            this.PopPointer();
            return CBORObject.FromObject(myHashMap);
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
              obj = this.NextJSONString();
              key = obj;
              break;
            }
        }
        if (this.SkipWhitespaceJSON() != ':') {
          this.RaiseError("Expected a ':' after a key");
        }
        this.SetPointer(key);
        int oldCount = myHashMap.size();
        // NOTE: Will overwrite existing value
        myHashMap.put(key, this.NextJSONValue(
            this.SkipWhitespaceJSON(),
            nextchar,
            depth));
        int newCount = myHashMap.size();
        if (!this.options.getAllowDuplicateKeys() &&
          oldCount == newCount) {
          this.RaiseError("Duplicate key already exists");
          return null;
        }
        switch (nextchar[0]) {
          case ',':
            seenComma = true;
            break;
          case '}':
            this.PopPointer();
            if (commentKey != null) {
              String[] keyptr = { commentKey, this.GetJSONPointer() };
              this.getPointers().add(keyptr);
            }
            return CBORObject.FromObject(myHashMap);
          default:
            this.RaiseError("Expected a ',' or '}'");
            break;
        }
      }
    }

    private void SetPointer(CBORObject obj) {
      this.currPointer.set(this.currPointerStackSize - 1, obj);
    }

    private void PushPointer() {
      if (this.currPointerStackSize > this.currPointer.size()) {
        this.RaiseError("Internal error");
      }
      if (this.currPointerStackSize == this.currPointer.size()) {
        this.currPointer.add(CBORObject.Null);
      } else {
        this.currPointer.set(this.currPointerStackSize, CBORObject.Null);
      }
      ++this.currPointerStackSize;
    }
    private void PopPointer() {
      if (this.currPointerStackSize < 0) {
        this.RaiseError("Internal error");
      }
      --this.currPointerStackSize;
    }

    CBORObject ParseJSONArray(int depth) {
      // Assumes that the last character read was '['
      if (depth > 1000) {
        this.RaiseError("Too deeply nested");
      }
      long arrayIndex = 0;
      CBORObject myArrayList = CBORObject.NewArray();
      boolean seenComma = false;
      int[] nextchar = new int[1];
      this.PushPointer();
      while (true) {
        int c = this.SkipWhitespaceJSON();
        if (c == ']') {
          if (seenComma) {
            // Situation like '[0,1,]'
            this.RaiseError("Trailing comma");
          }
          this.PopPointer();
          return myArrayList;
        }
        if (c == ',') {
          // Situation like '[,0,1,2]' or '[0,,1]'
          this.RaiseError("Empty array element");
        }
        this.SetPointer(CBORObject.FromObject(arrayIndex));
        arrayIndex = (arrayIndex + 1);
        myArrayList.Add(
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
            return myArrayList;
          default: this.RaiseError("Expected a ',' or ']'");
            break;
        }
      }
    }
  }
