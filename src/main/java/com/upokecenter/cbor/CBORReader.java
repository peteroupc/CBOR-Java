package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import java.io.*;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  class CBORReader {
    private final InputStream stream;
    private int depth;
    private CBORDuplicatePolicy policy;
    private StringRefs stringRefs;
    private boolean hasSharableObjects;

    public CBORReader(InputStream inStream) {
      this.stream = inStream;
      this.policy = CBORDuplicatePolicy.Overwrite;
    }

    enum CBORDuplicatePolicy {
      Overwrite, Disallow,
    }

    public final CBORDuplicatePolicy getDuplicatePolicy() {
        return this.policy;
      }
public final void setDuplicatePolicy(CBORDuplicatePolicy value) {
        this.policy = value;
      }

    public CBORObject ResolveSharedRefsIfNeeded(CBORObject obj) {
      if (this.hasSharableObjects) {
        SharedRefs sharedRefs = new SharedRefs();
        return ResolveSharedRefs(obj, sharedRefs);
      }
      return obj;
    }

    private static CBORObject ResolveSharedRefs(
  CBORObject obj,
  SharedRefs sharedRefs) {
  int type = obj.getItemType();
  boolean hasTag = obj.getMostOuterTag().equals(EInteger.FromInt64(29));
  if (hasTag) {
        if (!obj.isIntegral() || obj.isNegative()) {
   throw new CBORException("Shared ref index must be an integer 0 or greater");
        }
        return sharedRefs.GetObject(obj.AsEInteger());
  }
  hasTag = obj.getMostOuterTag().equals(EInteger.FromInt64(28));
  if (hasTag) {
      obj = obj.UntagOne();
      sharedRefs.AddObject(obj);
  }
  if (type == CBORObject.CBORObjectTypeMap) {
    for (CBORObject key : obj.getKeys()) {
      CBORObject value = obj.get(key);
      CBORObject newvalue = ResolveSharedRefs(value, sharedRefs);
      if (value != newvalue) {
        obj.set(key, newvalue);
      }
    }
  } else if (type == CBORObject.CBORObjectTypeArray) {
    for (int i = 0; i < obj.size(); ++i) {
      obj.set(i, ResolveSharedRefs(obj.get(i), sharedRefs));
    }
  }
  return obj;
    }

    public CBORObject Read() throws java.io.IOException {
      if (this.depth > 500) {
        throw new CBORException("Too deeply nested");
      }
      int firstbyte = this.stream.read();
      if (firstbyte < 0) {
        throw new CBORException("Premature end of data");
      }
      return this.ReadForFirstByte(firstbyte);
    }

    public CBORObject ReadForFirstByte(int firstbyte) throws java.io.IOException {
      if (this.depth > 500) {
        throw new CBORException("Too deeply nested");
      }
      if (firstbyte < 0) {
        throw new CBORException("Premature end of data");
      }
      if (firstbyte == 0xff) {
        throw new CBORException("Unexpected break code encountered");
      }
      int type = (firstbyte >> 5) & 0x07;
      int additional = firstbyte & 0x1f;
      int expectedLength = CBORObject.GetExpectedLength(firstbyte);
      // Data checks
      if (expectedLength == -1) {
        // if the head byte is invalid
        throw new CBORException("Unexpected data encountered");
      }
      // Check if this represents a fixed Object
      CBORObject fixedObject = CBORObject.GetFixedObject(firstbyte);
      if (fixedObject != null) {
        return fixedObject;
      }
      // Read fixed-length data
      byte[] data = null;
      if (expectedLength != 0) {
        data = new byte[expectedLength];
        // include the first byte because GetFixedLengthObject
        // will assume it exists for some head bytes
        data[0] = ((byte)firstbyte);
        if (expectedLength > 1 &&
            this.stream.read(data, 1, expectedLength - 1) != expectedLength
            - 1) {
          throw new CBORException("Premature end of data");
        }
        CBORObject cbor = CBORObject.GetFixedLengthObject(firstbyte, data);
        if (this.stringRefs != null && (type == 2 || type == 3)) {
          this.stringRefs.AddStringIfNeeded(cbor, expectedLength - 1);
        }
        return cbor;
      }
      // Special check: Decimal fraction or bigfloat
      if (firstbyte == 0xc4 || firstbyte == 0xc5) {
        int nextbyte = this.stream.read();
        if (nextbyte != 0x82 && nextbyte != 0x9f) {
 throw new CBORException("2-item array expected");
}
        boolean indefArray = nextbyte == 0x9f;
        nextbyte = this.stream.read();
        if (nextbyte >= 0x40) {
          throw new CBORException("Major type 0 or 1 or bignum expected");
        }
        CBORObject exponent = this.ReadForFirstByte(nextbyte);
        nextbyte = this.stream.read();
        if (nextbyte >= 0x40 && nextbyte != 0xc2 && nextbyte != 0xc3) {
          throw new CBORException("Major type 0 or 1 expected");
        }
        CBORObject significand = this.ReadForFirstByte(nextbyte);
        if (indefArray && this.stream.read() != 0xff) {
          throw new CBORException("End of array expected");
        }
        CBORObject arr = CBORObject.NewArray()
          .Add(exponent).Add(significand);
        return CBORObject.FromObjectAndTag(
          arr,
          firstbyte == 0xc4 ? 4 : 5);
      }
      long uadditional = (long)additional;
      EInteger bigintAdditional = EInteger.FromInt32(0);
      boolean hasBigAdditional = false;
      data = new byte[8];
      int lowAdditional = 0;
      switch (firstbyte & 0x1f) {
        case 24: {
            int tmp = this.stream.read();
            if (tmp < 0) {
              throw new CBORException("Premature end of data");
            }
            lowAdditional = tmp;
            uadditional = lowAdditional;
            break;
          }
        case 25: {
            if (this.stream.read(data, 0, 2) != 2) {
              throw new CBORException("Premature end of data");
            }
            lowAdditional = ((int)(data[0] & (int)0xff)) << 8;
            lowAdditional |= (int)(data[1] & (int)0xff);
            uadditional = lowAdditional;
            break;
          }
        case 26: {
            if (this.stream.read(data, 0, 4) != 4) {
              throw new CBORException("Premature end of data");
            }
            uadditional = ((long)(data[0] & 0xffL)) << 24;
            uadditional |= ((long)(data[1] & 0xffL)) << 16;
            uadditional |= ((long)(data[2] & 0xffL)) << 8;
            uadditional |= (long)(data[3] & 0xffL);
            break;
          }
        case 27: {
            if (this.stream.read(data, 0, 8) != 8) {
              throw new CBORException("Premature end of data");
            }
            if ((((int)data[0]) & 0x80) != 0) {
              // Won't fit in a signed 64-bit number
              byte[] uabytes = new byte[9];
              uabytes[0] = data[7];
              uabytes[1] = data[6];
              uabytes[2] = data[5];
              uabytes[3] = data[4];
              uabytes[4] = data[3];
              uabytes[5] = data[2];
              uabytes[6] = data[1];
              uabytes[7] = data[0];
              uabytes[8] = 0;
              hasBigAdditional = true;
              bigintAdditional = EInteger.FromBytes(uabytes, true);
            } else {
              uadditional = ((long)(data[0] & 0xffL)) << 56;
              uadditional |= ((long)(data[1] & 0xffL)) << 48;
              uadditional |= ((long)(data[2] & 0xffL)) << 40;
              uadditional |= ((long)(data[3] & 0xffL)) << 32;
              uadditional |= ((long)(data[4] & 0xffL)) << 24;
              uadditional |= ((long)(data[5] & 0xffL)) << 16;
              uadditional |= ((long)(data[6] & 0xffL)) << 8;
              uadditional |= (long)(data[7] & 0xffL);
            }
            break;
          }
      }
      // The following doesn't check for major types 0 and 1,
      // since all of them are fixed-length types and are
      // handled in the call to GetFixedLengthObject.
      if (type == 2) {  // Byte String
        if (additional == 31) {
          // Streaming byte String
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            // Requires same type as this one
            while (true) {
              int nextByte = this.stream.read();
              if (nextByte == 0xff) {
                // break if the "break" code was read
                break;
              }
              long len = ReadDataLength(this.stream, nextByte, 2);
              if ((len >> 63) != 0 || len > Integer.MAX_VALUE) {
                throw new CBORException("Length" + ToUnsignedBigInteger(len) +
                  " is bigger than supported ");
              }
              if (nextByte != 0x40) {
  // NOTE: 0x40 means the empty byte String
                ReadByteData(this.stream, len, ms);
              }
            }
            if (ms.size() > Integer.MAX_VALUE) {
              throw new
  CBORException("Length of bytes to be streamed is bigger than supported ");
            }
            data = ms.toByteArray();
            return new CBORObject(
  CBORObject.CBORObjectTypeByteString,
  data);
}
finally {
try { if (ms != null) {
 ms.close();
 } } catch (java.io.IOException ex) {}
}
        } else {
          if (hasBigAdditional) {
            throw new CBORException("Length of " +
                    bigintAdditional.toString() + " is bigger than supported");
          }
          if (uadditional > Integer.MAX_VALUE) {
            throw new CBORException("Length of " +
              CBORUtilities.LongToString(uadditional) +
              " is bigger than supported");
          }
          data = ReadByteData(this.stream, uadditional, null);
          CBORObject cbor = new CBORObject(CBORObject.CBORObjectTypeByteString, data);
          if (this.stringRefs != null) {
            int hint = (uadditional > Integer.MAX_VALUE || hasBigAdditional) ?
            Integer.MAX_VALUE : (int)uadditional;
            this.stringRefs.AddStringIfNeeded(cbor, hint);
          }
          return cbor;
        }
      }
      if (type == 3) {  // Text String
        if (additional == 31) {
          // Streaming text String
          StringBuilder builder = new StringBuilder();
          while (true) {
            int nextByte = this.stream.read();
            if (nextByte == 0xff) {
              // break if the "break" code was read
              break;
            }
            long len = ReadDataLength(this.stream, nextByte, 3);
            if ((len >> 63) != 0 || len > Integer.MAX_VALUE) {
              throw new CBORException("Length" + ToUnsignedBigInteger(len) +
                " is bigger than supported");
            }
            if (nextByte != 0x60) {
  // NOTE: 0x60 means the empty String
              if (PropertyMap.ExceedsKnownLength(this.stream, len)) {
                throw new CBORException("Premature end of data");
              }
              switch (
  DataUtilities.ReadUtf8(
  this.stream,
  (int)len,
  builder,
  false)) {
                case -1:
                  throw new CBORException("Invalid UTF-8");
                case -2:
                  throw new CBORException("Premature end of data");
              }
            }
          }
          return new CBORObject(
            CBORObject.CBORObjectTypeTextString,
            builder.toString());
        } else {
          if (hasBigAdditional) {
            throw new CBORException("Length of " +
                    bigintAdditional.toString() + " is bigger than supported");
          }
          if (uadditional > Integer.MAX_VALUE) {
            throw new CBORException("Length of " +
              CBORUtilities.LongToString(uadditional) +
              " is bigger than supported");
          }
          if (PropertyMap.ExceedsKnownLength(this.stream, uadditional)) {
            throw new CBORException("Premature end of data");
          }
          StringBuilder builder = new StringBuilder();
          switch (
  DataUtilities.ReadUtf8(
  this.stream,
  (int)uadditional,
  builder,
  false)) {
            case -1:
              throw new CBORException("Invalid UTF-8");
            case -2:
              throw new CBORException("Premature end of data");
          }
          CBORObject cbor = new CBORObject(
  CBORObject.CBORObjectTypeTextString,
  builder.toString());
          if (this.stringRefs != null) {
            int hint = (uadditional > Integer.MAX_VALUE || hasBigAdditional) ?
            Integer.MAX_VALUE : (int)uadditional;
            this.stringRefs.AddStringIfNeeded(cbor, hint);
          }
          return cbor;
        }
      }
      if (type == 4) {  // Array
        CBORObject cbor = CBORObject.NewArray();
        if (additional == 31) {
          int vtindex = 0;
          // Indefinite-length array
          while (true) {
            int headByte = this.stream.read();
            if (headByte < 0) {
              throw new CBORException("Premature end of data");
            }
            if (headByte == 0xff) {
              // Break code was read
              break;
            }
            ++this.depth;
            CBORObject o = this.ReadForFirstByte(
  headByte);
            --this.depth;
            cbor.Add(o);
            ++vtindex;
          }
          return cbor;
        }
        if (hasBigAdditional) {
          throw new CBORException("Length of " +
  bigintAdditional.toString() + " is bigger than supported");
        }
        if (uadditional > Integer.MAX_VALUE) {
          throw new CBORException("Length of " +
            CBORUtilities.LongToString(uadditional) +
            " is bigger than supported");
        }
        if (PropertyMap.ExceedsKnownLength(this.stream, uadditional)) {
          throw new CBORException("Remaining data too small for array length");
        }
        ++this.depth;
        for (long i = 0; i < uadditional; ++i) {
          cbor.Add(
            this.Read());
        }
        --this.depth;
        return cbor;
      }
      if (type == 5) {  // Map, type 5
        CBORObject cbor = CBORObject.NewMap();
        if (additional == 31) {
          // Indefinite-length map
          while (true) {
            int headByte = this.stream.read();
            if (headByte < 0) {
              throw new CBORException("Premature end of data");
            }
            if (headByte == 0xff) {
              // Break code was read
              break;
            }
            ++this.depth;
            CBORObject key = this.ReadForFirstByte(headByte);
            CBORObject value = this.Read();
            --this.depth;
            if (this.policy == CBORDuplicatePolicy.Disallow) {
              if (cbor.ContainsKey(key)) {
                throw new CBORException("Duplicate key already exists: " + key);
              }
            }
            cbor.set(key, value);
          }
          return cbor;
        }
        if (hasBigAdditional) {
          throw new CBORException("Length of " +
  bigintAdditional.toString() + " is bigger than supported");
        }
        if (uadditional > Integer.MAX_VALUE) {
          throw new CBORException("Length of " +
            CBORUtilities.LongToString(uadditional) +
            " is bigger than supported");
        }
        if (PropertyMap.ExceedsKnownLength(this.stream, uadditional)) {
            throw new CBORException("Remaining data too small for map length");
        }
        for (long i = 0; i < uadditional; ++i) {
          ++this.depth;
          CBORObject key = this.Read();
          CBORObject value = this.Read();
          --this.depth;
          if (this.policy == CBORDuplicatePolicy.Disallow) {
            if (cbor.ContainsKey(key)) {
              throw new CBORException("Duplicate key already exists: " + key);
            }
          }
          cbor.set(key, value);
        }
        return cbor;
      }
      if (type == 6) {  // Tagged item
        boolean haveFirstByte = false;
        int newFirstByte = -1;
        if (!hasBigAdditional) {
          int uad = uadditional >= 257 ? 257 : (uadditional < 0 ? 0 :
            (int)uadditional);
            switch (uad) {
            case 256:
              // Tag 256: String namespace
              this.stringRefs = (this.stringRefs == null) ? ((new StringRefs())) : this.stringRefs;
              this.stringRefs.Push();
              break;
            case 25:
              // String reference
              if (this.stringRefs == null) {
                throw new CBORException("No stringref namespace");
              }
              break;
              case 28:
              case 29:
              this.hasSharableObjects = true;
              break;
          }
        }
        ++this.depth;
        CBORObject o = haveFirstByte ? this.ReadForFirstByte(
  newFirstByte) : this.Read();
        --this.depth;
        if (hasBigAdditional) {
          return CBORObject.FromObjectAndTag(o, bigintAdditional);
        }
        if (uadditional < 65536) {
          int uaddl = uadditional >= 257 ? 257 : (uadditional < 0 ? 0 :
            (int)uadditional);
          switch (uaddl) {
            case 256:
              // String tag
              this.stringRefs.Pop();
              break;
            case 25:
              // stringref tag
              return this.stringRefs.GetString(o.AsEInteger());
          }

          return CBORObject.FromObjectAndTag(
            o,
            (int)uadditional);
        }
        return CBORObject.FromObjectAndTag(
          o,
          EInteger.FromInt64(uadditional));
      }
      throw new CBORException("Unexpected data encountered");
    }

    private static byte[] ReadByteData(
  InputStream stream,
  long uadditional,
  OutputStream outputStream) throws java.io.IOException {
      if ((uadditional >> 63) != 0 || uadditional > Integer.MAX_VALUE) {
        throw new CBORException("Length" + ToUnsignedBigInteger(uadditional) +
          " is bigger than supported ");
      }
      if (PropertyMap.ExceedsKnownLength(stream, uadditional)) {
        throw new CBORException("Premature end of stream");
      }
      if (uadditional <= 0x10000) {
        // Simple case: small size
        byte[] data = new byte[(int)uadditional];
        if (stream.read(data, 0, data.length) != data.length) {
          throw new CBORException("Premature end of stream");
        }
        if (outputStream != null) {
          outputStream.write(data, 0, data.length);
          return null;
        }
        return data;
      } else {
        byte[] tmpdata = new byte[0x10000];
        int total = (int)uadditional;
        if (outputStream != null) {
          while (total > 0) {
            int bufsize = Math.min(tmpdata.length, total);
            if (stream.read(tmpdata, 0, bufsize) != bufsize) {
              throw new CBORException("Premature end of stream");
            }
            outputStream.write(tmpdata, 0, bufsize);
            total -= bufsize;
          }
          return null;
        }
        java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream(0x10000);

          while (total > 0) {
            int bufsize = Math.min(tmpdata.length, total);
            if (stream.read(tmpdata, 0, bufsize) != bufsize) {
              throw new CBORException("Premature end of stream");
            }
            ms.write(tmpdata, 0, bufsize);
            total -= bufsize;
          }
          return ms.toByteArray();
}
finally {
try { if (ms != null) {
 ms.close();
 } } catch (java.io.IOException ex) {}
}
      }
    }

    private static long ReadDataLength(
  InputStream stream,
  int headByte,
  int expectedType) throws java.io.IOException {
      if (headByte < 0) {
        throw new CBORException("Unexpected data encountered");
      }
      if (((headByte >> 5) & 0x07) != expectedType) {
        throw new CBORException("Unexpected data encountered");
      }
      headByte &= 0x1f;
      if (headByte < 24) {
        return headByte;
      }
      byte[] data = new byte[8];
      switch (headByte & 0x1f) {
        case 24: {
            int tmp = stream.read();
            if (tmp < 0) {
              throw new CBORException("Premature end of data");
            }
            return tmp;
          }
        case 25: {
            if (stream.read(data, 0, 2) != 2) {
              throw new CBORException("Premature end of data");
            }
            int lowAdditional = ((int)(data[0] & (int)0xff)) << 8;
            lowAdditional |= (int)(data[1] & (int)0xff);
            return lowAdditional;
          }
        case 26: {
            if (stream.read(data, 0, 4) != 4) {
              throw new CBORException("Premature end of data");
            }
            long uadditional = ((long)(data[0] & 0xffL)) << 24;
            uadditional |= ((long)(data[1] & 0xffL)) << 16;
            uadditional |= ((long)(data[2] & 0xffL)) << 8;
            uadditional |= (long)(data[3] & 0xffL);
            return uadditional;
          }
        case 27: {
            if (stream.read(data, 0, 8) != 8) {
              throw new CBORException("Premature end of data");
            }
            // Treat return value as an unsigned integer
            long uadditional = ((long)(data[0] & 0xffL)) << 56;
            uadditional |= ((long)(data[1] & 0xffL)) << 48;
            uadditional |= ((long)(data[2] & 0xffL)) << 40;
            uadditional |= ((long)(data[3] & 0xffL)) << 32;
            uadditional |= ((long)(data[4] & 0xffL)) << 24;
            uadditional |= ((long)(data[5] & 0xffL)) << 16;
            uadditional |= ((long)(data[6] & 0xffL)) << 8;
            uadditional |= (long)(data[7] & 0xffL);
            return uadditional;
          }
        case 28:
        case 29:
        case 30:
          throw new CBORException("Unexpected data encountered");
        case 31:
          throw new CBORException("Indefinite-length data not allowed here");
        default: return headByte;
      }
    }

    private static EInteger ToUnsignedBigInteger(long val) {
      EInteger lval = EInteger.FromInt64(val & ~(1L << 63));
      if ((val >> 63) != 0) {
        EInteger bigintAdd = EInteger.FromInt32(1).ShiftLeft(63);
        lval = lval.Add(bigintAdd);
      }
      return lval;
    }
  }
