package com.upokecenter.test;

import java.io.*;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;

  public class BEncodingTest {
    private static CBORObject EncodingFromBytes(byte[] b) {
      try {
        {
          java.io.ByteArrayInputStream s = null;
try {
s = new java.io.ByteArrayInputStream(b);

          return BEncoding.Read(s);
}
finally {
try { if (s != null) { s.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ex) {
        throw new CBORException("", ex);
      }
    }

    private static byte[] EncodingToBytes(CBORObject b) {
      try {
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          BEncoding.Write(b, ms);
          return ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ex) {
        throw new CBORException("", ex);
      }
    }

    public static void DoTestLong(long value) {
      String b = "i" + TestCommon.LongToString(value) + "e";
      CBORObject beo = EncodingFromBytes(DataUtilities.GetUtf8Bytes(b,
            false));
      Assert.assertEquals(value, beo.AsInt64());
      String newb = DataUtilities.GetUtf8String(EncodingToBytes(beo), false);
      Assert.assertEquals(b, newb);
    }

    public static void DoTestString(String value) {
      String b = DataUtilities.GetUtf8Length(value, false) + ":" + value;
      CBORObject beo = EncodingFromBytes(DataUtilities.GetUtf8Bytes(b,
            false));
      Assert.assertEquals(value, beo.AsString());
      String newb = DataUtilities.GetUtf8String(EncodingToBytes(beo), false);
      Assert.assertEquals(b, newb);
    }

    @Test
    public void TestLong() {
      DoTestLong(0);
      DoTestLong(-1);
      DoTestLong(Integer.MIN_VALUE);
      DoTestLong(Integer.MAX_VALUE);
      DoTestLong(Long.MIN_VALUE);
      DoTestLong(Long.MAX_VALUE);
    }

    @Test
    public void TestList() {
      CBORObject beo = CBORObject.NewArray();
      beo.Add(ToObjectTest.TestToFromObjectRoundTrip(1));
      beo.Add(ToObjectTest.TestToFromObjectRoundTrip("two"));
      beo.Add(ToObjectTest.TestToFromObjectRoundTrip(3));
      beo.Add(ToObjectTest.TestToFromObjectRoundTrip("four"));
      Assert.assertEquals(4, beo.size());
      Assert.assertEquals(1, beo.get(0).AsInt64());
      {
        String stringTemp = beo.get(1).AsString();
        Assert.assertEquals(
          "two",
          stringTemp);
      }
      Assert.assertEquals(3, beo.get(2).AsInt64());
      {
        String stringTemp = beo.get(3).AsString();
        Assert.assertEquals(
          "four",
          stringTemp);
      }
      byte[] b = EncodingToBytes(beo);
      beo = EncodingFromBytes(b);
      Assert.assertEquals(4, beo.size());
      Assert.assertEquals(1, beo.get(0).AsInt64());
      {
        String stringTemp = beo.get(1).AsString();
        Assert.assertEquals(
          "two",
          stringTemp);
      }
      Assert.assertEquals(3, beo.get(2).AsInt64());
      {
        String stringTemp = beo.get(3).AsString();
        Assert.assertEquals(
          "four",
          stringTemp);
      }
    }

    @Test
    public void TestDictionary() {
      CBORObject beo = CBORObject.NewMap();
      beo.set("zero",ToObjectTest.TestToFromObjectRoundTrip(1));
      beo.set("one",ToObjectTest.TestToFromObjectRoundTrip("two"));
      beo.set("two",ToObjectTest.TestToFromObjectRoundTrip(3));
      beo.set("three",ToObjectTest.TestToFromObjectRoundTrip("four"));
      Assert.assertEquals(4, beo.size());
      Assert.assertEquals(1, beo.get("zero").AsInt64());
      {
        String stringTemp = beo.get("one").AsString();
        Assert.assertEquals(
          "two",
          stringTemp);
      }
      Assert.assertEquals(3, beo.get("two").AsInt64());
      {
        String stringTemp = beo.get("three").AsString();
        Assert.assertEquals(
          "four",
          stringTemp);
      }
      byte[] b = EncodingToBytes(beo);
      beo = EncodingFromBytes(b);
      Assert.assertEquals(4, beo.size());
      Assert.assertEquals(1, beo.get("zero").AsInt64());
      {
        String stringTemp = beo.get("one").AsString();
        Assert.assertEquals(
          "two",
          stringTemp);
      }
      Assert.assertEquals(3, beo.get("two").AsInt64());
      {
        String stringTemp = beo.get("three").AsString();
        Assert.assertEquals(
          "four",
          stringTemp);
      }
    }

    @Test
    public void TestString() {
      DoTestString("");
      DoTestString(" ");
      DoTestString("test");

      DoTestString (
        TestCommon.Repeat("three", 15));
      DoTestString("te\u007fst");
      DoTestString("te\u0080st");
      DoTestString("te\u3000st");
      DoTestString("te\u07ffst");
      DoTestString("te\u0800st");
      DoTestString("te\uffffst");
      DoTestString("te\ud7ffst");
      DoTestString("te\ue000st");
      DoTestString("te\ud800\udc00st");
      DoTestString("te\udbff\udc00st");
      DoTestString("te\ud800\udfffst");
      DoTestString("te\udbff\udfffst");
    }
  }
