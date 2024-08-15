package com.upokecenter.test; import com.upokecenter.util.*;
/*
Written in 2013 by Peter Occil.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under the Unlicense: https://unlicense.org/

 */

import java.util.*;

import java.io.*;

import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  /**
   * Contains methods for reading and writing objects represented in BEncode, a
   * serialization format used in the BitTorrent protocol. For more information,
   * see: http://wiki.theory.org/BitTorrentSpecification This class accepts
   * BEncoded strings in UTF-8, and outputs BEncoded strings in UTF-8. This class
   * also demonstrates how CBORObject supports predefined serialization formats.
   */
  public final class BEncoding {
private BEncoding() {
}
    private static void WriteUtf8(String s, OutputStream stream) throws java.io.IOException {
      if (com.upokecenter.util.DataUtilities.WriteUtf8(s, stream, false) != 0) {
        throw new CBORException("invalid surrogate");
      }
    }

    private static CBORObject ReadDictionary(InputStream stream) throws java.io.IOException {
      CBORObject obj = CBORObject.NewMap();
      while (true) {
        int c = stream.read();
        if (c == 'e') {
          break;
        }
        CBORObject s = ReadString(stream, (char)c);
        CBORObject o = ReadObject(stream, false);
        obj.set(s, o);
      }
      return obj;
    }

    private static CBORObject ReadInteger(InputStream stream) throws java.io.IOException {
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
      String s = builder.toString();
      if (s.length() >= 2 && s.charAt(0) == '0' && s.charAt(1) == '0') {
          throw new CBORException("Invalid integer encoding");
      }
      if (s.length() >= 3 && s.charAt(0) == '-' && s.charAt(1) == '0' && s.charAt(2) == '0') {
          throw new CBORException("Invalid integer encoding");
      }
      return CBORObject.FromObject(
          EInteger.FromString(s));
    }

    private static CBORObject ReadList(InputStream stream) throws java.io.IOException {
      CBORObject obj = CBORObject.NewArray();
      while (true) {
        CBORObject o = ReadObject(stream, true);
        if (o == null) {
          break; // 'e' was read
        }
        obj.Add(o);
      }
      return obj;
    }

    public static CBORObject Read(InputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      return ReadObject(stream, false);
    }

    private static CBORObject ReadObject(InputStream stream, boolean allowEnd) throws java.io.IOException {
      int c = stream.read();
      if (c == 'd') {
        return ReadDictionary(stream);
      }
      if (c == 'l') {
        return ReadList(stream);
      }
      if (allowEnd && c == 'e') {
        return null;
      }
      if (c == 'i') {
        return ReadInteger(stream);
      }
      if (c >= '0' && c <= '9') {
        return ReadString(stream, (char)c);
      }
      throw new CBORException("Object expected");
    }

    private static final String ValueDigits = "0123456789";

    public static String LongToString(long longValue) {
      if (longValue == Long.MIN_VALUE) {
        return "-9223372036854775808";
      }
      if (longValue == 0L) {
        return "0";
      }
      return (longValue == (long)Integer.MIN_VALUE) ? "-2147483648" :
EInteger.FromInt64(longValue).toString();
    }

    private static CBORObject ReadString(InputStream stream, char firstChar) throws java.io.IOException {
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
      String s = builder.toString();
      if (s.length() >= 2 && s.charAt(0) == '0' && s.charAt(1) == '0') {
          throw new CBORException("Invalid integer encoding");
      }
      EInteger numlength = EInteger.FromString(s);
      if (!numlength.CanFitInInt32()) {
        throw new CBORException("Length too long");
      }
      builder = new StringBuilder();
      switch (com.upokecenter.util.DataUtilities.ReadUtf8(
        stream,
        numlength.ToInt32Checked(),
        builder,
        false)) {
        case -2:
          throw new CBORException("Premature end of data");
        case -1:
          throw new CBORException("Invalid UTF-8");
      }
      return CBORObject.FromObject(builder.toString());
    }

    public static void Write(CBORObject obj, OutputStream stream) throws java.io.IOException {
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      if (obj.isNumber()) {
        if (stream == null) {
          throw new NullPointerException("stream");
        }
        stream.write(((byte)((byte)0x69)));
        WriteUtf8(obj.ToObject(EInteger.class).toString(), stream);
        stream.write(((byte)((byte)0x65)));
      } else if (obj.getType() == CBORType.TextString) {
        String s = obj.AsString();
        long length = com.upokecenter.util.DataUtilities.GetUtf8Length(s, false);
        if (length < 0) {
          throw new CBORException("invalid String");
        }
        WriteUtf8(LongToString(length), stream);
        if (stream == null) {
          throw new NullPointerException("stream");
        }
        stream.write(((byte)((byte)':')));
        WriteUtf8(s, stream);
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
          if (stream == null) {
            throw new NullPointerException("stream");
          }
          stream.write(((byte)((byte)0x64)));
          for (Map.Entry<String, CBORObject> entry : valueSMap.entrySet()) {
            String key = entry.getKey();
            CBORObject value = entry.getValue();
            long length = com.upokecenter.util.DataUtilities.GetUtf8Length(key, false);
            if (length < 0) {
              throw new CBORException("invalid String");
            }
            WriteUtf8(
              LongToString(length),
              stream);
            stream.write(((byte)((byte)':')));
            WriteUtf8(key, stream);
            Write(value, stream);
          }
          stream.write(((byte)((byte)0x65)));
        } else {
          if (stream == null) {
            throw new NullPointerException("stream");
          }
          stream.write(((byte)((byte)0x64)));
          for (CBORObject key : obj.getKeys()) {
            String str = key.AsString();
            long length = com.upokecenter.util.DataUtilities.GetUtf8Length(str, false);
            if (length < 0) {
              throw new CBORException("invalid String");
            }
            WriteUtf8(LongToString(length), stream);
            stream.write(((byte)((byte)':')));
            WriteUtf8(str, stream);
            Write(obj.get(key), stream);
          }
          stream.write(((byte)((byte)0x65)));
        }
      } else if (obj.getType() == CBORType.Array) {
        if (stream == null) {
          throw new NullPointerException("stream");
        }
        stream.write(((byte)((byte)0x6c)));
        for (int i = 0; i < obj.size(); ++i) {
          Write(obj.get(i), stream);
        }
        stream.write(((byte)((byte)0x65)));
      } else {
        String str = obj.ToJSONString();
        long length = com.upokecenter.util.DataUtilities.GetUtf8Length(str, false);
        if (length < 0) {
          throw new CBORException("invalid String");
        }
        WriteUtf8(LongToString(length), stream);
        if (stream == null) {
          throw new NullPointerException("stream");
        }
        stream.write(((byte)((byte)':')));
        WriteUtf8(str, stream);
      }
    }
  }
