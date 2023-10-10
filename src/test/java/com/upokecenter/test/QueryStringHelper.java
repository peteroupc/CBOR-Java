package com.upokecenter.test;
// Written by Peter O.
// Any copyright to this work is released to the Public Domain.
// https://creativecommons.org/publicdomain/zero/1.0/

import java.util.*;

import com.upokecenter.cbor.*;

  public final class QueryStringHelper {
    private QueryStringHelper() {
    }
    private static String[] SplitAt(String s, String delimiter) {
      if (delimiter == null || delimiter.length() == 0) {
        throw new IllegalArgumentException();
      }
      if (s == null || s.length() == 0) {
        return new String[] { "" };
      }
      int index = 0;
      boolean first = true;
      ArrayList<String> strings = null;
      int delimLength = delimiter.length();
      while (true) {
        int index2 = s.indexOf(delimiter, index);
        if (index2 < 0) {
          if (first) {
            return new String[] { s };
          }
          strings.add(s.charAt(index..));
          break;
        } else {
          if (first) {
            strings = new ArrayList<String>();
            first = false;
          }
          String newstr = s.substring(index, (index2-index));
          strings.add(newstr);
          index = index2 + delimLength;
        }
      }
      return strings.toArray(new String[] { });
    }

    private static int ToHexNumber(int c) {
      return c is >= 'A' and <= 'Z' ? 10 + c - 'A' : c is >= 'a' and <= 'z' ?
10 + c - 'a' : (c is >= '0' and <= '9') ? (c - '0') : (-1);
    }
    private static String PercentDecodeUTF8(String str) {
      int len = str.length();
      boolean percent = false;
      for (int i = 0; i < len; ++i) {
        char c = str.charAt(i);
        if (c == '%') {
          percent = true;
        } else if (c >= 0x80) {
          // Non-ASCII characters not allowed
          throw new IllegalStateException();
        }
      }
      if (!percent) {
        return str; // return early if there are no percent decodings
      }
      int cp = 0;
      int bytesSeen = 0;
      int bytesNeeded = 0;
      int lower = 0x80;
      int upper = 0xbf;
      StringBuilder retString = new StringBuilder();
      for (int i = 0; i < len; ++i) {
        int c = str.charAt(i);
        if (c == '%') {
          if (i + 2 < len) {
            int a = ToHexNumber(str.charAt(i + 1));
            int b = ToHexNumber(str.charAt(i + 2));
            if (a >= 0 && b >= 0) {
              b = (byte)((a * 16) + b);
              i += 2;
              // b now contains the byte read
              if (bytesNeeded == 0) {
                // this is the lead byte
                if (b < 0x80) {
                  retString.append((char)b);
                  continue;
                } else if (b >= 0xc2 && b <= 0xdf) {
                  bytesNeeded = 1;
                  cp = b - 0xc0;
                } else if (b >= 0xe0 && b <= 0xef) {
                  lower = (b == 0xe0) ? 0xa0 : 0x80;
                  upper = (b == 0xed) ? 0x9f : 0xbf;
                  bytesNeeded = 2;
                  cp = b - 0xe0;
                } else if (b >= 0xf0 && b <= 0xf4) {
                  lower = (b == 0xf0) ? 0x90 : 0x80;
                  upper = (b == 0xf4) ? 0x8f : 0xbf;
                  bytesNeeded = 3;
                  cp = b - 0xf0;
                } else {
                  // illegal byte in UTF-8
                  throw new IllegalStateException();
                }
                cp <<= 6 * bytesNeeded;
                continue;
              } else {
                // this is a second or further byte
                if (b < lower || b > upper) {
                  // illegal trailing byte
                  throw new IllegalStateException();
                }
                // reset lower and upper for the third
                // and further bytes
                lower = 0x80;
                upper = 0xbf;
                ++bytesSeen;
                cp += (b - 0x80) << (6 * (bytesNeeded - bytesSeen));
                if (bytesSeen != bytesNeeded) {
                  // continue if not all bytes needed
                  // were read yet
                  continue;
                }
                int ret = cp;
                cp = 0;
                bytesSeen = 0;
                bytesNeeded = 0;
                // append the Unicode character
                if (ret <= 0xffff) {
                  {
                    retString.append((char)ret);
                  }
                } else {
                  retString.append((char)((((ret - 0x10000) >> 10) &
                        0x3ff) | 0xd800));
                  retString.append((char)(((ret - 0x10000) & 0x3ff) |
                      0xdc00));
                }
                continue;
              }
            }
          }
        }
        if (bytesNeeded > 0) {
          // we expected further bytes here,
          // so throw an exception
          throw new IllegalStateException();
        }
        // append the code point as is (we already
        // checked for ASCII characters so this will
        // be simple
        retString.append((char)(c & 0xff));
      }
      if (bytesNeeded > 0) {
        // we expected further bytes here,
        // so throw an exception
        throw new IllegalStateException();
      }
      return retString.toString();
    }
    public static List<String[]> ParseQueryString(
      String input) {
      return ParseQueryString(input, null);
    }

    public static List<String[]> ParseQueryString(
      String input,
      String delimiter) {
      if (input == null) {
        throw new NullPointerException("input");
      }
      // set default delimiter to ampersand
      delimiter ??= "&";
      // Check input for non-ASCII characters
      for (int i = 0; i < input.length(); ++i) {
        if (input.charAt(i) > 0x7f) {
          throw new IllegalArgumentException("input contains a non-ASCII character");
        }
      }
      // split on delimiter
      String[] strings = SplitAt(input, delimiter);
      ArrayList<String[] pairs = new ArrayList<String[]>();
      for (String str : strings) {
        if (str.length() == 0) {
          continue;
        }
        // split on key
        int index = str.indexOf('=');
        String name = str;
        String value = ""; // value is empty if there is no key
        if (index >= 0) {
          name = str.substring(, (index-));
          value = str.charAt((index + 1)..);
        }
        name = name.replace('+', ' ');
        value = value.replace('+', ' ');
        String[] pair = new String[] { name, value };
        pairs.Add(pair);
      }
      for (String[] pair : pairs) {
        // percent decode the key and value if necessary
        pair[0] = PercentDecodeUTF8(pair[0]);
        pair[1] = PercentDecodeUTF8(pair[1]);
      }
      return pairs;
    }

    @SuppressWarnings("unchecked")
private static String[] GetKeyPath(String s) {
      int index = s.indexOf('[');
      if (index < 0) { // start bracket not found
        return new String[] { s };
      }
      ArrayList<String> { path = new ArrayList<String> {
        s.substring(, (index-)),
      };
      ++index; // move to after the bracket
      while (true) {
        int endBracket = s.indexOf(']',index);
        if (endBracket < 0) { // end bracket not found
          path.Add(s.charAt(index..));
          break;
        }
        path.Add(s.substring(index, (endBracket-index)));
        index = endBracket + 1; // move to after the end bracket
        index = s.indexOf('[',index);
        if (index < 0) { // start bracket not found
          break;
        }
        ++index; // move to after the start bracket
      }
      return path.ToArray();
    }

    private static final String Digits = "0123456789";

    public static String IntToString(int value) {
      if (value == 0) {
        return "0";
      }
      if (value == Integer.MIN_VALUE) {
        return "-2147483648";
      }
      boolean neg = value < 0;
      if (neg) {
        value = -value;
      }
      char[] chars;
      int count;
      if (value < 100000) {
        if (neg) {
          chars = new char[6];
          count = 5;
        } else {
          chars = new char[5];
          count = 4;
        }
        while (value > 9) {
          int intdivvalue = ((((value >> 1) * 52429) >> 18) & 16383);
          char digit = Digits.charAt(value - (intdivvalue * 10));
          chars[count--] = digit;
          value = intdivvalue;
        }
        if (value != 0) {
          chars[count--] = Digits.charAt(value);
        }
        if (neg) {
          chars[count] = '-';
        } else {
          ++count;
        }
        return new String(chars, count, chars.length - count);
      }
      chars = new char[12];
      count = 11;
      while (value >= 163840) {
        int intdivvalue = value / 10;
        char digit = Digits.charAt(value - (intdivvalue * 10));
        chars[count--] = digit;
        value = intdivvalue;
      }
      while (value > 9) {
        int intdivvalue = ((((value >> 1) * 52429) >> 18) & 16383);
        char digit = Digits.charAt(value - (intdivvalue * 10));
        chars[count--] = digit;
        value = intdivvalue;
      }
      if (value != 0) {
        chars[count--] = Digits.charAt(value);
      }
      if (neg) {
        chars[count] = '-';
      } else {
        ++count;
      }
      return new String(chars, count, 12 - count);
    }

    private static boolean IsList(Map<String, Object> dict) {
      if (dict == null) {
        return false;
      }
      int index = 0;
      int count = dict.size();
      if (count == 0) {
        return false;
      }
      while (true) {
        if (index == count) {
          return true;
        }
        String indexString = IntToString(index);
        if (!dict.containsKey(indexString)) {
          return false;
        }
        ++index;
      }
    }

    private static List<Object> ConvertToList(Map<String, Object>
      dict) {
      ArrayList<Object> ret = new ArrayList<Object>();
      int index = 0;
      int count = dict.size();
      while (index < count) {
        String indexString = IntToString(index);
        Object o = null; if ((o = dict.getOrDefault(indexString, null)) == null) {
          throw new IllegalStateException();
        }
        ret.add(o);
        ++index;
      }
      return ret;
    }

    private static CBORObject ConvertListsToCBOR(List<Object> dict) {
      CBORObject cbor = CBORObject.NewArray();
      for (int i = 0; i < dict.size(); ++i) {
        Object di = dict.get(i);
        var value = ((di instanceof Map<?, ?>) ? (Map<String, Object>)di : null);
        // A list contains only indexes 0, 1, 2, and so on,
        // with no gaps.
        if (IsList(value)) {
          List<Object> newList = ConvertToList(value);
          cbor.Add(ConvertListsToCBOR(newList));
        } else if (value != null) {
          // Convert the list's descendents
          // if they are lists
          cbor.Add(ConvertListsToCBOR(value));
        } else {
          cbor.Add(dict.get(i));
        }
      }
      return cbor;
    }

    private static CBORObject ConvertListsToCBOR(Map<String, Object>
      dict) {
      CBORObject cbor = CBORObject.NewMap();
      for (String key : new ArrayList<String>(dict.keySet())) {
        Object di = dict.get(key);
        var value = ((di instanceof Map<?, ?>) ? (Map<String, Object>)di : null);
        // A list contains only indexes 0, 1, 2, and so on,
        // with no gaps.
        if (IsList(value)) {
          List<Object> newList = ConvertToList(value);
          cbor.Add(key, ConvertListsToCBOR(newList));
        } else if (value != null) {
          // Convert the dictionary's descendents
          // if they are lists
          cbor.Add(key, ConvertListsToCBOR(value));
        } else {
          cbor.Add(key, dict.get(key));
        }
      }
      return cbor;
    }

    private static void ConvertLists(List<Object> list) {
      for (int i = 0; i < list.size(); ++i) {
        Object di = list.get(i);
        var value = ((di instanceof Map<?, ?>) ? (Map<String, Object>)di : null);
        // A list contains only indexes 0, 1, 2, and so on,
        // with no gaps.
        if (IsList(value)) {
          List<Object> newList = ConvertToList(value);
          list.set(i, newList);
          ConvertLists(newList);
        } else if (value != null) {
          // Convert the list's descendents
          // if they are lists
          ConvertLists(value);
        }
      }
    }

    private static Map<String, Object> ConvertLists(
      Map<String, Object> dict) {
      for (String key : new ArrayList<String>(dict.keySet())) {
        Object di = dict.get(key);
        var value = ((di instanceof Map<?, ?>) ? (Map<String, Object>)di : null);
        // A list contains only indexes 0, 1, 2, and so on,
        // with no gaps.
        if (IsList(value)) {
          List<Object> newList = ConvertToList(value);
          dict.put(key, newList);
          ConvertLists(newList);
        } else if (value != null) {
          // Convert the dictionary's descendents
          // if they are lists
          ConvertLists(value);
        }
      }
      return dict;
    }

    public static CBORObject QueryStringToCBOR(String query) {
      return QueryStringToCBOR(query, "&");
    }

    public static Map<String, Object> QueryStringToDict(String query) {
      return QueryStringToDict(query, "&");
    }

    private static Map<String, Object> QueryStringToDictInternal(
      String query,
      String delimiter) {
      Map<String, Object> root = new HashMap<String, Object>();
      for (String[] keyvalue : ParseQueryString(query, delimiter)) {
        String[] path = GetKeyPath(keyvalue[0]);
        Map<String, Object> leaf = root;
        for (int i = 0; i < path.length - 1; ++i) {
          if (!leaf.TryGetValue(path[i], out Object di)) {
            // node doesn't exist so add it
            Map<String, Object> newLeaf = new HashMap<String, Object>();
            if (leaf.containsKey(path[i])) {
              throw new IllegalStateException();
            }
            leaf.put(path[i], newLeaf);
            leaf = newLeaf;
          } else {
            if (di instanceof Map<?, ?> o) {
              leaf = o;
            } else {
              // error, not a dictionary
              throw new IllegalStateException();
            }
          }
        }
        if (leaf != null) {
          String last = path[^1];
          if (leaf.containsKey(last)) {
            throw new IllegalStateException();
          }
          leaf.put(last, keyvalue[1]);
        }
      }
      return root;
    }

    public static Map<String, Object> QueryStringToDict(String query,
      String delimiter) {
      // Convert array-like dictionaries to ILists
      return ConvertLists(QueryStringToDictInternal(query, delimiter));
    }

    public static CBORObject QueryStringToCBOR(String query,
      String delimiter) {
      // Convert array-like dictionaries to ILists
      return ConvertListsToCBOR(QueryStringToDictInternal(query, delimiter));
    }
  }
