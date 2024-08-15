package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under the Unlicense: https://unlicense.org/

 */

  final class Base64 {
private Base64() {
}
    private static final String Base64URL =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

    private static final String Base64Classic =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static void WriteBase64(
      StringOutput writer,
      byte[] data,
      int offset,
      int count,
      boolean padding) throws java.io.IOException {
      WriteBase64(writer, data, offset, count, true, padding);
    }

    public static void WriteBase64URL(
      StringOutput writer,
      byte[] data,
      int offset,
      int count,
      boolean padding) throws java.io.IOException {
      WriteBase64(writer, data, offset, count, false, padding);
    }

    private static void WriteBase64(
      StringOutput writer,
      byte[] data,
      int offset,
      int count,
      boolean classic,
      boolean padding) throws java.io.IOException {
      if (writer == null) {
        throw new NullPointerException("writer");
      }
      if (offset < 0) {
        throw new IllegalArgumentException("offset(" + offset + ") is less than " +
          "0 ");
      }
      if (offset > data.length) {
        throw new IllegalArgumentException("offset(" + offset + ") is more than " +
          data.length);
      }
      if (count < 0) {
        throw new IllegalArgumentException("count(" + count + ") is less than " +
          "0 ");
      }
      if (count > data.length) {
        throw new IllegalArgumentException("count(" + count + ") is more than " +
          data.length);
      }
      if (data.length - offset < count) {
        throw new IllegalArgumentException("data's length minus " + offset + "(" +
          (data.length - offset) + ") is less than " + count);
      }
      String alphabet = classic ? Base64Classic : Base64URL;
      int length = offset + count;
      int i = offset;
      byte[] buffer = new byte[32];
      int bufferOffset = 0;
      for (i = offset; i < (length - 2); i += 3) {
        if (bufferOffset >= buffer.length) {
           writer.WriteAscii(buffer, 0, bufferOffset);
           bufferOffset = 0;
        }
        buffer[bufferOffset++] = (byte)alphabet.charAt((data[i] >> 2) & 63);
        buffer[bufferOffset++] = (byte)alphabet.charAt(((data[i] & 3) << 4) +
            ((data[i + 1] >> 4) & 15));
        buffer[bufferOffset++] = (byte)alphabet.charAt(((data[i + 1] & 15) << 2) +
((data[i +
                  2] >> 6) & 3));
        buffer[bufferOffset++] = (byte)alphabet.charAt(data[i + 2] & 63);
      }
      int lenmod3 = count % 3;
      if (lenmod3 != 0) {
        if (bufferOffset >= buffer.length) {
           writer.WriteAscii(buffer, 0, bufferOffset);
           bufferOffset = 0;
        }
        i = length - lenmod3;
        buffer[bufferOffset++] = (byte)alphabet.charAt((data[i] >> 2) & 63);
        if (lenmod3 == 2) {
          buffer[bufferOffset++] = (byte)alphabet.charAt(((data[i] & 3) << 4) +
((data[i + 1] >>
                  4) & 15));
          buffer[bufferOffset++] = (byte)alphabet.charAt((data[i + 1] & 15) << 2);
          if (padding) {
            buffer[bufferOffset++] = (byte)'=';
          }
        } else {
          buffer[bufferOffset++] = (byte)alphabet.charAt((data[i] & 3) << 4);
          if (padding) {
            buffer[bufferOffset++] = (byte)'=';
            buffer[bufferOffset++] = (byte)'=';
          }
        }
      }
      if (bufferOffset >= 0) {
        writer.WriteAscii(buffer, 0, bufferOffset);
      }
    }
  }
