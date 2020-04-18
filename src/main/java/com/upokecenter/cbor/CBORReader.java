package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import java.util.*;
import java.io.*;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  class CBORReader {
    private final InputStream stream;
    private final CBOREncodeOptions options;
    private int depth;
    private StringRefs stringRefs;
    private boolean hasSharableObjects;

    public CBORReader(InputStream inStream) {
 this(inStream,
        CBOREncodeOptions.Default);
    }

    public CBORReader(InputStream inStream, CBOREncodeOptions options) {
      this.stream = inStream;
      this.options = options;
    }

    private static EInteger ToUnsignedEInteger(long val) {
      EInteger lval = EInteger.FromInt64(val & ~(1L << 63));
      if ((val >> 63) != 0) {
        EInteger bigintAdd = EInteger.FromInt32(1).ShiftLeft(63);
        lval = lval.Add(bigintAdd);
      }
      return lval;
    }

    private void HandleItemTag(long uadditional) {
      int uad = uadditional >= 257 ? 257 : (uadditional < 0 ? 0 :
          (int)uadditional);
      switch (uad) {
        case 256:
          // Tag 256: String namespace
          this.stringRefs = (this.stringRefs == null) ? (new StringRefs()) : this.stringRefs;
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

    private CBORObject ObjectFromByteArray(byte[] data, int lengthHint) {
      CBORObject cbor = CBORObject.FromRaw(data);
      if (this.stringRefs != null) {
        this.stringRefs.AddStringIfNeeded(cbor, lengthHint);
      }
      return cbor;
    }

    private CBORObject ObjectFromUtf8Array(byte[] data, int lengthHint) {
      CBORObject cbor = data.length == 0 ?
         CBORObject.FromObject("") :
         CBORObject.FromRawUtf8(data);
      if (this.stringRefs != null) {
        this.stringRefs.AddStringIfNeeded(cbor, lengthHint);
      }
      return cbor;
    }

    private static CBORObject ResolveSharedRefs(
      CBORObject obj,
      SharedRefs sharedRefs) {
      if (obj == null) {
        return null;
      }
      CBORType type = obj.getType();
      boolean hasTag = obj.HasMostOuterTag(29);
      if (hasTag) {
        CBORObject untagged = obj.UntagOne();
        if (untagged.isTagged() ||
          untagged.getType() != CBORType.Integer ||
untagged.AsNumber().IsNegative()) {
          throw new CBORException(
            "Shared ref index must be an untagged integer 0 or greater");
        }
        return sharedRefs.GetObject(untagged.AsEIntegerValue());
      }
      hasTag = obj.HasMostOuterTag(28);
      if (hasTag) {
        obj = obj.UntagOne();
        sharedRefs.AddObject(obj);
      }
      if (type == CBORType.Map) {
        for (CBORObject key : obj.getKeys()) {
          CBORObject value = obj.get(key);
          CBORObject newvalue = ResolveSharedRefs(value, sharedRefs);
          if (value != newvalue) {
            obj.set(key, newvalue);
          }
        }
      } else if (type == CBORType.Array) {
        for (int i = 0; i < obj.size(); ++i) {
          obj.set(i, ResolveSharedRefs(obj.get(i), sharedRefs));
        }
      }
      return obj;
    }

    public CBORObject Read() throws java.io.IOException {
      CBORObject obj = this.options.getAllowEmpty() ?
        this.ReadInternalOrEOF() : this.ReadInternal();
      if (this.options.getResolveReferences() && this.hasSharableObjects) {
        SharedRefs sharedRefs = new SharedRefs();
        return ResolveSharedRefs(obj, sharedRefs);
      }
      return obj;
    }

    private CBORObject ReadInternalOrEOF() throws java.io.IOException {
      if (this.depth > 500) {
        throw new CBORException("Too deeply nested");
      }
      int firstbyte = this.stream.read();
      if (firstbyte < 0) {
        // End of stream
        return null;
      }
      return this.ReadForFirstByte(firstbyte);
    }

    private CBORObject ReadInternal() throws java.io.IOException {
      if (this.depth > 500) {
        throw new CBORException("Too deeply nested");
      }
      int firstbyte = this.stream.read();
      if (firstbyte < 0) {
        throw new CBORException("Premature end of data");
      }
      return this.ReadForFirstByte(firstbyte);
    }

    private CBORObject ReadStringArrayMap(int type, long uadditional) throws java.io.IOException {
      boolean canonical = this.options.getCtap2Canonical();
      if (type == 2 || type == 3) { // Byte String or text String
        if ((uadditional >> 31) != 0) {
          throw new CBORException("Length of " +
            ToUnsignedEInteger(uadditional).toString() + " is bigger" +
            "\u0020than supported");
        }
        int hint = (uadditional > Integer.MAX_VALUE ||
            (uadditional >> 63) != 0) ? Integer.MAX_VALUE : (int)uadditional;
        byte[] data = ReadByteData(this.stream, uadditional, null);
        if (type == 3) {
          if (!CBORUtilities.CheckUtf8(data)) {
            throw new CBORException("Invalid UTF-8");
          }
          return this.ObjectFromUtf8Array(data, hint);
        } else {
          return this.ObjectFromByteArray(data, hint);
        }
      }
      if (type == 4) { // Array
        if (this.options.getCtap2Canonical() && this.depth >= 4) {
          throw new CBORException("Depth too high in canonical CBOR");
        }
        CBORObject cbor = CBORObject.NewArray();
        if ((uadditional >> 31) != 0) {
          throw new CBORException("Length of " +
            ToUnsignedEInteger(uadditional).toString() + " is bigger than" +
"\u0020supported");
        }
        if (PropertyMap.ExceedsKnownLength(this.stream, uadditional)) {
          throw new CBORException("Remaining data too small for array" +
"\u0020length");
        }
        ++this.depth;
        for (long i = 0; i < uadditional; ++i) {
          cbor.Add(
            this.ReadInternal());
        }
        --this.depth;
        return cbor;
      }
      if (type == 5) { // Map, type 5
        if (this.options.getCtap2Canonical() && this.depth >= 4) {
          throw new CBORException("Depth too high in canonical CBOR");
        }
        CBORObject cbor = CBORObject.NewMap();
        if ((uadditional >> 31) != 0) {
          throw new CBORException("Length of " +
            ToUnsignedEInteger(uadditional).toString() + " is bigger than" +
            "\u0020supported");
        }
        if (PropertyMap.ExceedsKnownLength(this.stream, uadditional)) {
          throw new CBORException("Remaining data too small for map" +
"\u0020length");
        }
        CBORObject lastKey = null;
        Comparator<CBORObject> comparer = CBORCanonical.Comparer;
        for (long i = 0; i < uadditional; ++i) {
          ++this.depth;
          CBORObject key = this.ReadInternal();
          CBORObject value = this.ReadInternal();
          --this.depth;
          if (this.options.getCtap2Canonical() && lastKey != null) {
            int cmp = comparer.compare(lastKey, key);
            if (cmp > 0) {
              throw new CBORException("Map key not in canonical order");
            } else if (cmp == 0) {
              throw new CBORException("Duplicate map key");
            }
          }
          if (!this.options.getAllowDuplicateKeys()) {
            if (cbor.ContainsKey(key)) {
              throw new CBORException("Duplicate key already exists");
            }
          }
          lastKey = key;
          cbor.set(key, value);
        }
        return cbor;
      }
      return null;
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
      long uadditional;
      CBORObject fixedObject;
      if (this.options.getCtap2Canonical()) {
        if (additional >= 0x1c) {
          // NOTE: Includes stop byte and indefinite length data items
          throw new CBORException("Invalid canonical CBOR encountered");
        }
        // Check if this represents a fixed Object (NOTE: All fixed objects
        // comply with CTAP2 canonical CBOR).
        fixedObject = CBORObject.GetFixedObject(firstbyte);
        if (fixedObject != null) {
          return fixedObject;
        }
        if (type == 6) {
          throw new CBORException("Tags not allowed in canonical CBOR");
        }
        uadditional = ReadDataLength(
          this.stream,
          firstbyte,
          type,
          type == 7);
        if (type == 0) {
          return (uadditional >> 63) != 0 ?
            CBORObject.FromObject(ToUnsignedEInteger(uadditional)) :
            CBORObject.FromObject(uadditional);
          } else if (type == 1) {
          return (uadditional >> 63) != 0 ? CBORObject.FromObject(
              ToUnsignedEInteger(uadditional).Add(1).Negate()) :
            CBORObject.FromObject((-uadditional) - 1L);
          } else if (type == 7) {
          if (additional < 24) {
            return CBORObject.FromSimpleValue(additional);
          } else if (additional == 24 && uadditional < 32) {
            throw new CBORException("Invalid simple value encoding");
          } else if (additional == 24) {
            return CBORObject.FromSimpleValue((int)uadditional);
          } else if (additional == 25) {
            return CBORObject.FromFloatingPointBits(uadditional, 2);
          } else if (additional == 26) {
            return CBORObject.FromFloatingPointBits(uadditional, 4);
          } else if (additional == 27) {
            return CBORObject.FromFloatingPointBits(uadditional, 8);
          }
        } else if (type >= 2 && type <= 5) {
          return this.ReadStringArrayMap(type, uadditional);
        }
        throw new CBORException("Unexpected data encountered");
      }
      int expectedLength = CBORObject.GetExpectedLength(firstbyte);
      // Data checks
      if (expectedLength == -1) {
        // if the head byte is invalid
        throw new CBORException("Unexpected data encountered");
      }
      // Check if this represents a fixed Object
      fixedObject = CBORObject.GetFixedObject(firstbyte);
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
      if (additional == 31) {
        // Indefinite-length for major types 2 to 5 (other major
        // types were already handled in the call to
        // GetFixedLengthObject).
        switch (type) {
          case 2: {
            // Streaming byte String
            {
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
                  throw new CBORException("Length" + ToUnsignedEInteger(len)
+
                    " is bigger than supported ");
                }
                if (nextByte != 0x40) {
                  // NOTE: 0x40 means the empty byte String
                  ReadByteData(this.stream, len, ms);
                }
              }
              if (ms.size() > Integer.MAX_VALUE) {
                throw new
                CBORException("Length of bytes to be streamed is bigger" +
"\u0020than supported ");
              }
              data = ms.toByteArray();
              return CBORObject.FromRaw(data);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          }
          case 3: {
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
                throw new CBORException("Length" + ToUnsignedEInteger(len) +
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
            return CBORObject.FromRaw(builder.toString());
          }
          case 4: {
            CBORObject cbor = CBORObject.NewArray();
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
          case 5: {
            CBORObject cbor = CBORObject.NewMap();
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
              CBORObject value = this.ReadInternal();
              --this.depth;
              if (!this.options.getAllowDuplicateKeys()) {
                if (cbor.ContainsKey(key)) {
                  throw new CBORException("Duplicate key already exists");
                }
              }
              cbor.set(key, value);
            }
            return cbor;
          }
          default: throw new CBORException("Unexpected data encountered");
        }
      }
      EInteger bigintAdditional = EInteger.FromInt32(0);
      uadditional = ReadDataLength(this.stream, firstbyte, type);
      // The following doesn't check for major types 0 and 1,
      // since all of them are fixed-length types and are
      // handled in the call to GetFixedLengthObject.
      if (type >= 2 && type <= 5) {
        return this.ReadStringArrayMap(type, uadditional);
      }
      if (type == 6) { // Tagged item
        boolean haveFirstByte = false;
        int newFirstByte = -1;
        if (this.options.getResolveReferences() && (uadditional >> 32) == 0) {
          // NOTE: HandleItemTag treats only certain tags up to 256 specially
          this.HandleItemTag(uadditional);
        }
        ++this.depth;
        CBORObject o = haveFirstByte ? this.ReadForFirstByte(
            newFirstByte) : this.ReadInternal();
        --this.depth;
        if ((uadditional >> 63) != 0) {
          return CBORObject.FromObjectAndTag(o,
              ToUnsignedEInteger(uadditional));
        }
        if (uadditional < 65536) {
          if (this.options.getResolveReferences()) {
            int uaddl = uadditional >= 257 ? 257 : (uadditional < 0 ? 0 :
                (int)uadditional);
            switch (uaddl) {
              case 256:
                // String tag
                this.stringRefs.Pop();
                break;
              case 25:
                // stringref tag
                if (o.isTagged() || o.getType() != CBORType.Integer) {
                  throw new CBORException("stringref must be an unsigned" +
                    "\u0020integer");
                }
                return this.stringRefs.GetString(o.AsEIntegerValue());
            }
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

    private static final byte[] EmptyByteArray = new byte[0];

    private static byte[] ReadByteData(
      InputStream stream,
      long uadditional,
      OutputStream outputStream) throws java.io.IOException {
      if (uadditional == 0) {
        return EmptyByteArray;
      }
      if ((uadditional >> 63) != 0 || uadditional > Integer.MAX_VALUE) {
        throw new CBORException("Length" + ToUnsignedEInteger(uadditional) +
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
        {
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
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      }
    }

    private static long ReadDataLength(
      InputStream stream,
      int headByte,
      int expectedType) throws java.io.IOException {
      return ReadDataLength(stream, headByte, expectedType, true);
    }

    private static long ReadDataLength(
      InputStream stream,
      int headByte,
      int expectedType,
      boolean allowNonShortest) throws java.io.IOException {
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
      switch (headByte) {
        case 24: {
          int tmp = stream.read();
          if (tmp < 0) {
            throw new CBORException("Premature end of data");
          }
          if (!allowNonShortest && tmp < 24) {
            throw new CBORException("Non-shortest CBOR form");
          }
          return tmp;
        }
        case 25: {
          if (stream.read(data, 0, 2) != 2) {
            throw new CBORException("Premature end of data");
          }
          int lowAdditional = ((int)(data[0] & (int)0xff)) << 8;
          lowAdditional |= (int)(data[1] & (int)0xff);
          if (!allowNonShortest && lowAdditional < 256) {
            throw new CBORException("Non-shortest CBOR form");
          }
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
          if (!allowNonShortest && (uadditional >> 16) == 0) {
            throw new CBORException("Non-shortest CBOR form");
          }
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
          if (!allowNonShortest && (uadditional >> 32) == 0) {
            throw new CBORException("Non-shortest CBOR form");
          }
          return uadditional;
        }
        case 28:
        case 29:
        case 30:
          throw new CBORException("Unexpected data encountered");
        case 31:
          throw new CBORException("Indefinite-length data not allowed" +
"\u0020here");
        default: return headByte;
      }
    }

    /*
    // - - - - - ASYNCHRONOUS METHODS

    private static async Task<int> ReadByteAsync(InputStream stream) {
      byte[] bytes = new byte[1];
      if (await stream.ReadAsync(bytes, 0, 1) == 0) {
        return -1;
      } else {
        return bytes[0];
      }
    }
    */
  }
