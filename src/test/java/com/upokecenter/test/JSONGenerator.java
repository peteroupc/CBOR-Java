package com.upokecenter.test;

import java.util.*;

import com.upokecenter.util.*;

  public final class JSONGenerator {
    private static final class ByteWriter {
      private byte[] bytes = new byte[64];
      private int pos = 0;
      public ByteWriter Write(int b) {
        if (this.pos < this.bytes.length) {
          this.bytes[this.pos++] = (byte)b;
        } else {
          byte[] newbytes = new byte[this.bytes.length * 2];
          System.arraycopy(this.bytes, 0, newbytes, 0, this.bytes.length);
          this.bytes = newbytes;
          this.bytes[this.pos++] = (byte)b;
        }
        return this;
      }
      public final int getByteLength() {
          return this.pos;
        }
      public byte[] ToBytes() {
        byte[] newbytes = new byte[this.pos];
        System.arraycopy(this.bytes, 0, newbytes, 0, this.pos);
        return newbytes;
      }
    }

    private static int[] MajorTypes = {
      0, 1, 3, 4, 5,
    };
    private static int[] MajorTypesTop = {
      0, 1, 3, 4, 4, 4, 4, 4, 5, 5, 5,
      5, 5, 5, 5, 5, 5, 5, 5, 5,
    };

    private static int[] Escapes = {
      (int)'\\', (int)'/', (int)'\"',
      (int)'b', (int)'f', (int)'n', (int)'r', (int)'t', (int)'u',
    };

    private static char[] EscapeChars = {
      '\\', '/', '\"',
      (char)8, (char)12, '\n', '\r', '\t', (char)0,
    };

    private static void GenerateCodeUnit(
      RandomGenerator ra,
      ByteWriter bs,
      int cu) {
      int c;
      int shift = 12;
      for (int i = 0; i < 4; ++i) {
        c = (cu >> shift) & 0xf;
        if (c < 10) {
          bs.Write(0x30 + c);
        } else {
          bs.Write(0x41 + (c - 10) + ra.UniformInt(2) * 0x20);
        }
        shift -= 4;
      }
    }

    private static void GenerateUtf16(
      RandomGenerator ra,
      ByteWriter bs,
      StringBuilder sb) {
      int r = ra.UniformInt(0x110000 - 0x800);
      if (r >= 0xd800) {
        r += 0x800;
      }
      if (r >= 0x10000) {
        int rc = (((r - 0x10000) >> 10) & 0x3ff) | 0xd800;
        GenerateCodeUnit(ra, bs, rc);
        if (sb != null) {
          sb.append((char)rc);
        }
        bs.Write((int)'\\');
        bs.Write((int)'u');
        rc = ((r - 0x10000) & 0x3ff) | 0xdc00;
        GenerateCodeUnit(ra, bs, rc);
        if (sb != null) {
          sb.append((char)rc);
        }
      } else {
        GenerateCodeUnit(ra, bs, r);
        if (sb != null) {
          sb.append((char)r);
        }
      }
    }
    private static void GenerateWhitespace(RandomGenerator ra, ByteWriter bs) {
      if (ra.UniformInt(10) == 0) {
        int len = ra.UniformInt(20);
        int[] ws = {0x09, 0x0d, 0x0a, 0x20 };
        if (ra.UniformInt(100) == 0) {
          len = ra.UniformInt(100);
        }
        for (int i = 0; i < len; ++i) {
          bs.Write(ws[ra.UniformInt(ws.length)]);
        }
      }
    }

    private static void GenerateJsonNumber(RandomGenerator ra, ByteWriter bs) {
      if (ra.UniformInt(2) == 0) {
        bs.Write((int)'-');
      }
      int len = (ra.UniformInt(1000) * ra.UniformInt(3)) + 1;
      bs.Write(0x31 + ra.UniformInt(9));
      for (int i = 0; i < len; ++i) {
        bs.Write(0x30 + ra.UniformInt(10));
      }
      if (ra.UniformInt(2) == 0) {
        bs.Write(0x2e);
        len = (ra.UniformInt(1000) * ra.UniformInt(3)) + 1;
        for (int i = 0; i < len; ++i) {
          bs.Write(0x30 + ra.UniformInt(10));
        }
      }
      if (ra.UniformInt(2) == 0) {
        int rr = ra.UniformInt(3);
        if (rr == 0) {
          bs.Write((int)'E');
        } else if (rr == 1) {
          bs.Write((int)'E').Write((int)'+');
        } else if (rr == 2) {
          bs.Write((int)'E').Write((int)'-');
        }
        len = 1 + ra.UniformInt(1000);
        for (int i = 0; i < len; ++i) {
          bs.Write(0x30 + ra.UniformInt(10));
        }
      }
    }

    private static int GenerateUtf8(
      RandomGenerator ra,
      ByteWriter bs,
      StringBuilder sb,
      int len) {
      int r = ra.UniformInt(3);
      int r2, r3, r4;
      if (r == 0 && len >= 2) {
        r = 0xc2 + ra.UniformInt((0xdf - 0xc2) + 1);
        bs.Write(r);
        r2 = 0x80 + ra.UniformInt(0x40);
        bs.Write(r2);
        if (sb != null) {
          sb.append((char)(((r - 0x80) << 6) | r2));
        }
        return 2;
      } else if (r == 1 && len >= 3) {
        r = 0xe0 + ra.UniformInt(16);
        bs.Write(r);
        int lower = (r == 0xe0) ? 0xa0 : 0x80;
        int upper = (r == 0xed) ? 0x9f : 0xbf;
        r2 = lower + ra.UniformInt((upper - lower) + 1);
        bs.Write(r2);
        r3 = 0x80 + ra.UniformInt(0x40);
        bs.Write(r3);
        if (sb != null) {
          sb.append((char)(((r - 0x80) << 12) | ((r2 - 0x80) << 6) | r3));
        }
        return 3;
      } else if (r == 2 && len >= 4) {
        r = 0xf0 + ra.UniformInt(5);
        bs.Write(r);
        int lower = (r == 0xf0) ? 0x90 : 0x80;
        int upper = (r == 0xf4) ? 0x8f : 0xbf;
        r2 = lower + ra.UniformInt((upper - lower) + 1);
        bs.Write(r2);
        r3 = 0x80 + ra.UniformInt(0x40);
        bs.Write(r3);
        r4 = 0x80 + ra.UniformInt(0x40);
        bs.Write(r4);
        r = ((r - 0x80) << 18) | ((r2 - 0x80) << 12) | ((r3 - 0x80) << 6) | r4;
        if (sb != null) {
          sb.append((char)(((r - 0x10000) >> 10) | 0xd800));
          sb.append((char)(((r - 0x10000) & 0x3ff) | 0xdc00));
        }
        return 4;
      }
      return 0;
    }

    private static void GenerateJsonKey(
      RandomGenerator ra,
      ByteWriter bskey,
      int depth,
      Map<String, String> keys) {
      while (true) {
        StringBuilder sb = new StringBuilder();
        ByteWriter bs = new ByteWriter();
        int len = ra.UniformInt(1000);
        if (ra.UniformInt(50) == 0 && depth < 2) {
          // Exponential curve that strongly favors small numbers
          long v = (long)ra.UniformInt(1000000) * ra.UniformInt(1000000);
          len = (int)(v / 1000000);
        }
        bs.Write(0x22);
        for (int i = 0; i < len;) {
          int r = ra.UniformInt(10);
          if (r > 2) {
            int x = 0x20 + ra.UniformInt(60);
            if (x == (int)'\"') {
              bs.Write((int)'\\').Write(x);
              sb.append('\"');
            } else if (x == (int)'\\') {
              bs.Write((int)'\\').Write(x);
              sb.append('\\');
            } else {
              bs.Write(x);
              sb.append((char)x);
            }
            ++i;
          } else if (r == 1) {
            bs.Write((int)'\\');
            int escindex = ra.UniformInt(Escapes.length);
            int esc = Escapes[escindex];
            bs.Write((int)esc);
            if (esc == (int)'u') {
              GenerateUtf16(ra, bs, sb);
            } else {
              sb.append(EscapeChars[escindex]);
            }
          } else {
            GenerateUtf8(ra, bs, sb, len - i);
          }
        }
        bs.Write(0x22);
        String key = sb.toString();
        if (!keys.containsKey(key)) {
          keys.put(key,"");
          byte[] bytes = bs.ToBytes();
          for (int i = 0; i < bytes.length; ++i) {
            bskey.Write(((int)bytes[i]) & 0xff);
          }
          return;
        }
      }
    }

    private static void GenerateJsonString(
      RandomGenerator ra,
      ByteWriter bs,
      int depth) {
      int len = ra.UniformInt(1000);
      if (ra.UniformInt(50) == 0 && depth < 2) {
        // Exponential curve that strongly favors small numbers
        long v = (long)ra.UniformInt(1000000) * ra.UniformInt(1000000);
        len = (int)(v / 1000000);
      }
      bs.Write(0x22);
      for (int i = 0; i < len;) {
        int r = ra.UniformInt(10);
        if (r > 2) {
          int x = 0x20 + ra.UniformInt(60);
          if (x == (int)'\"') {
            bs.Write((int)'\\').Write(x);
          } else if (x == (int)'\\') {
            bs.Write((int)'\\').Write(x);
          } else {
            bs.Write(x);
          }
          ++i;
        } else if (r == 1) {
          bs.Write((int)'\\');
          int esc = Escapes[ra.UniformInt(Escapes.length)];
          bs.Write((int)esc);
          if (esc == (int)'u') {
            GenerateUtf16(ra, bs, null);
          }
        } else {
          GenerateUtf8(ra, bs, null, len - i);
        }
      }
      bs.Write(0x22);
    }

    private void Generate(RandomGenerator r, int depth, ByteWriter bs) {
      int majorType;
      majorType = MajorTypes[r.UniformInt(MajorTypes.length)];
      if (depth == 0) {
        majorType = MajorTypesTop[r.UniformInt(MajorTypes.length)];
      }
      GenerateWhitespace(r, bs);
      if (bs.getByteLength() > 2000000) {
        majorType = r.UniformInt(2); // either 0 or 1
      }
      if (majorType == 0) {
        GenerateJsonNumber(r, bs);
      } else if (majorType == 1) {
        switch (r.UniformInt(3)) {
          case 0:
            bs.Write((int)'t').Write((int)'r').Write((int)'u').Write(
              (int)'e');
            break;
          case 1:
            bs.Write((int)'n').Write((int)'u').Write((int)'l').Write(
              (int)'l');
            break;
          case 2:

            bs.Write((int)'f').Write((int)'a').Write((int)'l').Write(
  (int)'s').Write(
                    (int)'e');
            break;
        }
      } else if (majorType == 3) {
        GenerateJsonString(r, bs, depth);
      } else if (majorType == 4 || majorType == 5) {
        int len = r.UniformInt(8);
        if (r.UniformInt(50) == 0 && depth < 2) {
          long v = (long)r.UniformInt(1000) * r.UniformInt(1000);
          len = (int)(v / 1000);
        }
        if (depth > 6) {
          len = r.UniformInt(100) == 0 ? 1 : 0;
        }
        if (majorType == 4) {
          bs.Write((int)'[');
          for (int i = 0; i < len; ++i) {
            if (i > 0) {
              bs.Write((int)',');
            }
            this.Generate(r, depth + 1, bs);
          }
          bs.Write((int)']');
        }
        if (majorType == 5) {
          bs.Write((int)'{');
          HashMap<String, String> keys = new HashMap<String, String>();
          for (int i = 0; i < len; ++i) {
            if (i > 0) {
              bs.Write((int)',');
            }
            GenerateWhitespace(r, bs);
            GenerateJsonKey(r, bs, depth, keys);
            GenerateWhitespace(r, bs);
            bs.Write((int)':');
            GenerateWhitespace(r, bs);
            this.Generate(r, depth + 1, bs);
          }
          bs.Write((int)'}');
        }
      }
      GenerateWhitespace(r, bs);
    }

    public byte[] Generate(IRandomGen random) {
      ByteWriter bs = new ByteWriter();
      this.Generate(new RandomGenerator(random), 0, bs);
      byte[] ret = bs.ToBytes();
      return ret;
    }
  }
