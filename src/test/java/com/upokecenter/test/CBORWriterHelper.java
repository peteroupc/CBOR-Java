package com.upokecenter.test;
import java.io.*;
import com.upokecenter.cbor.*;

  public final class CBORWriterHelper {
    private final OutputStream outputStream;

    public CBORWriterHelper(OutputStream outputStream) {
      this.outputStream = outputStream;
    }

    public CBORWriterHelper WriteStartArray(int size) throws java.io.IOException {
      CBORObject.WriteValue(this.outputStream, 4, size);
      return this;
    }

    public CBORWriterHelper WriteStartMap(int size) throws java.io.IOException {
      CBORObject.WriteValue(this.outputStream, 5, size);
      return this;
    }

    public CBORWriterHelper WriteStartArray(long size) throws java.io.IOException {
      CBORObject.WriteValue(this.outputStream, 4, size);
      return this;
    }

    public CBORWriterHelper WriteStartMap(long size) throws java.io.IOException {
      CBORObject.WriteValue(this.outputStream, 5, size);
      return this;
    }

    public CBORWriterHelper WriteStartArray() throws java.io.IOException {
      this.outputStream.write(0x9f);
      return this;
    }

    public CBORWriterHelper WriteStartMap() throws java.io.IOException {
      this.outputStream.write(0xbf);
      return this;
    }

    public CBORWriterHelper WriteEndArray() throws java.io.IOException {
      this.outputStream.write(0xff);
      return this;
    }

    public CBORWriterHelper WriteEndMap() throws java.io.IOException {
      this.outputStream.write(0xff);
      return this;
    }

    public CBORWriterHelper Write(Object key, Object value) throws java.io.IOException {
      CBORObject.Write(key, this.outputStream);
      CBORObject.Write(value, this.outputStream);
      return this;
    }

    public CBORWriterHelper Write(Object value) throws java.io.IOException {
      CBORObject.Write(value, this.outputStream);
      return this;
    }
  }
