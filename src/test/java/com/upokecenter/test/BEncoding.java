package com.upokecenter.test; import com.upokecenter.util.*;
/*
Written in 2013 by Peter Occil.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import java.util.*;

import java.io.*;

import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

    /**
     * Contains methods for reading and writing objects represented in BEncode, a
     * serialization format used in the BitTorrent protocol. For more
     * information, see: http://wiki.theory.org/BitTorrentSpecification This
     * class accepts BEncoded strings in UTF-8, and outputs BEncoded strings
     * in UTF-8. This class also demonstrates how CBORObject supports
     * predefined serialization formats.
     */
  public final class BEncoding {
private BEncoding() {
}
    private static void writeUtf8(String s, OutputStream stream) throws java.io.IOException {
      if (DataUtilities.WriteUtf8(s, stream, false) != 0) {
        throw new CBORException("invalid surrogate");
      }
    }

    private static CBORObject readDictionary(InputStream stream) throws java.io.IOException {
      CBORObject obj = CBORObject.NewMap();
      while (true) {
        int c = stream.read();
        if (c == 'e') {
          break;
        }
        CBORObject s = readString(stream, (char)c);
        CBORObject o = readObject(stream, false);
        obj.set(s, o);
      }
      return obj;
    }

    private static CBORObject readInteger(InputStream stream) throws java.io.IOException {
      StringBuilder builder = new StringBuilder();
      boolean start = true;
      while (true) {
        int c = stream.read();
        if (c < 0) {
          throw new CBORException("Premature end of data");
        }
        if (c >= (int)'0' && c <= (int)'9') {
          builder.append((char)c);
          start = false;
        } else if (c == (int)'e') {
          break;
        } else if (start && c == '-') {
          start = false;
          builder.append((char)c);
        } else {
          throw new CBORException("Invalid integer encoding");
        }
      }
      return CBORDataUtilities.ParseJSONNumber(
        builder.toString(),
        true,
        false);
    }

    private static CBORObject readList(InputStream stream) throws java.io.IOException {
      CBORObject obj = CBORObject.NewArray();
      while (true) {
        CBORObject o = readObject(stream, true);
        if (o == null) {
          break;  // 'e' was read
        }
        obj.Add(o);
      }
      return obj;
    }

    public static CBORObject Read(InputStream stream) throws java.io.IOException {
      return readObject(stream, false);
    }

    private static CBORObject readObject(InputStream stream, boolean allowEnd) throws java.io.IOException {
      int c = stream.read();
      if (c == 'd') {
        return readDictionary(stream);
      }
      if (c == 'l') {
        return readList(stream);
      }
      if (allowEnd && c == 'e') {
        return null;
      }
      if (c == 'i') {
        return readInteger(stream);
      }
      if (c >= '0' && c <= '9') {
        return readString(stream, (char)c);
      }
      throw new CBORException("Object expected");
    }

    private static final String ValueDigits = "0123456789";

    private static void ReverseChars(char[] chars, int offset, int length) {
      int half = length >> 1;
      int right = offset + length - 1;
      for (int i = 0; i < half; i++, right--) {
        char value = chars[offset + i];
        chars[offset + i] = chars[right];
        chars[right] = value;
      }
    }

    public static String LongToString(long longValue) {
      if (longValue == Long.MIN_VALUE) {
        return "-9223372036854775808";
      }
      if (longValue == 0L) {
        return "0";
      }
      if (longValue == (long)Integer.MIN_VALUE) {
        return "-2147483648";
      }
      boolean neg = longValue < 0;
      int count = 0;
      char[] chars;
      int intlongValue = ((int)longValue);
      if ((long)intlongValue == longValue) {
        chars = new char[12];
        if (neg) {
          chars[0] = '-';
          ++count;
          intlongValue = -intlongValue;
        }
        while (intlongValue != 0) {
          int intdivlongValue = intlongValue / 10;
          char digit = ValueDigits.charAt((int)(intlongValue - (intdivlongValue *
              10)));
          chars[count++] = digit;
          intlongValue = intdivlongValue;
        }
      } else {
        chars = new char[24];
        if (neg) {
          chars[0] = '-';
          ++count;
          longValue = -longValue;
        }
        while (longValue != 0) {
          long divlongValue = longValue / 10;
          char digit = ValueDigits.charAt((int)(longValue - (divlongValue * 10)));
          chars[count++] = digit;
          longValue = divlongValue;
        }
      }
      if (neg) {
        ReverseChars(chars, 1, count - 1);
      } else {
        ReverseChars(chars, 0, count);
      }
      return new String(chars, 0, count);
    }

    private static CBORObject readString(InputStream stream, char firstChar) throws java.io.IOException {
      StringBuilder builder = new StringBuilder();
      if (firstChar < (int)'0' && firstChar > (int)'9') {
        throw new CBORException("Invalid integer encoding");
      }
      builder.append(firstChar);
      while (true) {
        int c = stream.read();
        if (c < 0) {
          throw new CBORException("Premature end of data");
        }
        if (c >= (int)'0' && c <= (int)'9') {
          builder.append((char)c);
        } else if (c == (int)':') {
          break;
        } else {
          throw new CBORException("Invalid integer encoding");
        }
      }
      CBORObject number = CBORDataUtilities.ParseJSONNumber(
        builder.toString(),
        true,
        true);
      int length = 0;
      try {
        length = number.AsInt32();
      } catch (ArithmeticException ex) {
        throw new CBORException("Length too long", ex);
      }
      builder = new StringBuilder();
      switch (DataUtilities.ReadUtf8(stream, length, builder, false)) {
        case -2:
          throw new CBORException("Premature end of data");
        case -1:
          throw new CBORException("Invalid UTF-8");
      }
      return CBORObject.FromObject(builder.toString());
    }

    public static void Write(CBORObject obj, OutputStream stream) throws java.io.IOException {
      if (obj.getType() == CBORType.Number) {
        stream.write(((byte)((byte)'i')));
        writeUtf8(obj.AsBigInteger().toString(), stream);
        stream.write(((byte)((byte)'e')));
      } else if (obj.getType() == CBORType.TextString) {
        String s = obj.AsString();
        long length = DataUtilities.GetUtf8Length(s, false);
        if (length < 0) {
          throw new CBORException("invalid String");
        }
        writeUtf8(LongToString(length), stream);
        stream.write(((byte)((byte)':')));
        writeUtf8(s, stream);
      } else if (obj.getType() == CBORType.Map) {
        boolean hasNonStringKeys = false;
        for (CBORObject key : obj.getKeys()) {
          if (key.getType() != CBORType.TextString) {
            hasNonStringKeys = true;
            break;
          }
        }
        if (hasNonStringKeys) {
          HashMap<String, CBORObject> valueSMap = new HashMap<String, CBORObject>();
          // Copy to a map with String keys, since
          // some keys could be duplicates
          // when serialized to strings
          for (CBORObject key : obj.getKeys()) {
            CBORObject value = obj.get(key);
            String str = (key.getType() == CBORType.TextString) ?
              key.AsString() : key.ToJSONString();
            valueSMap.put(str, value);
          }
          stream.write(((byte)((byte)'d')));
          for (Map.Entry<String, CBORObject> entry : valueSMap.entrySet()) {
            String key = entry.getKey();
            CBORObject value = entry.getValue();
            long length = DataUtilities.GetUtf8Length(key, false);
            if (length < 0) {
              throw new CBORException("invalid String");
            }
            writeUtf8(
LongToString(length),
stream);
            stream.write(((byte)((byte)':')));
            writeUtf8(key, stream);
            Write(value, stream);
          }
          stream.write(((byte)((byte)'e')));
        } else {
          stream.write(((byte)((byte)'d')));
          for (CBORObject key : obj.getKeys()) {
            String str = key.AsString();
            long length = DataUtilities.GetUtf8Length(str, false);
            if (length < 0) {
              throw new CBORException("invalid String");
            }
            writeUtf8(LongToString(length), stream);
            stream.write(((byte)((byte)':')));
            writeUtf8(str, stream);
            Write(obj.get(key), stream);
          }
          stream.write(((byte)((byte)'e')));
        }
      } else if (obj.getType() == CBORType.Array) {
        stream.write(((byte)((byte)'l')));
        for (int i = 0; i < obj.size(); ++i) {
          Write(obj.get(i), stream);
        }
        stream.write(((byte)((byte)'e')));
      } else {
        String str = obj.ToJSONString();
        long length = DataUtilities.GetUtf8Length(str, false);
        if (length < 0) {
          throw new CBORException("invalid String");
        }
        writeUtf8(LongToString(length), stream);
        stream.write(((byte)((byte)':')));
        writeUtf8(str, stream);
      }
    }
  }
