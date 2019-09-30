package com.upokecenter.test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class LimitedMemoryStream extends OutputStream {
  private final ByteArrayOutputStream ms;
  private final int maxSize;
  private int pos;
  public LimitedMemoryStream(int maxSize) {
    if(maxSize<0)throw new IllegalArgumentException("maxSize");
    this.ms=new ByteArrayOutputStream();
    this.maxSize=maxSize;
    this.pos=0;
  }
  public void close() throws IOException {
    ms.close();
  }
  public void flush() throws IOException {
    ms.flush();
  }
  public void write(int b) throws IOException {
    if(pos>=maxSize)throw new UnsupportedOperationException();
    ms.write(b);
    pos++;
  }
  public void write(byte[] bytes) throws IOException {
    write(bytes, 0, bytes.length);
  }
  public void write(byte[] bytes, int offset, int length) throws IOException {
    if((long)pos + length > maxSize)throw new UnsupportedOperationException();
    ms.write(bytes, offset, length);
    pos+=length;
  }
}
