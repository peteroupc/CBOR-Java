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
try { if (s != null)s.close(); } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ex) {
        throw new CBORException("", ex);
      }
    }

    private static byte[] EncodingToBytes(CBORObject b) {
      try {
        java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          BEncoding.Write(b, ms);
          return ms.toByteArray();
}
finally {
try { if (ms != null)ms.close(); } catch (java.io.IOException ex) {}
}
      } catch (IOException ex) {
        throw new CBORException("", ex);
      }
    }

    public static void doTestLong(long value) {
      String b = "i" + TestCommon.LongToString(value) + "e";
      CBORObject beo = EncodingFromBytes(DataUtilities.GetUtf8Bytes(b, false));
      Assert.assertEquals(value, beo.AsInt64());
      String newb = DataUtilities.GetUtf8String(EncodingToBytes(beo), false);
      Assert.assertEquals(b, newb);
    }

    public static void doTestString(String value) {
      String b = DataUtilities.GetUtf8Length(value, false) + ":" + value;
      CBORObject beo = EncodingFromBytes(DataUtilities.GetUtf8Bytes(b, false));
      Assert.assertEquals(value, beo.AsString());
      String newb = DataUtilities.GetUtf8String(EncodingToBytes(beo), false);
      Assert.assertEquals(b, newb);
    }

    @Test
    public void testLong() {
      doTestLong(0);
      doTestLong(-1);
      doTestLong(Integer.MIN_VALUE);
      doTestLong(Integer.MAX_VALUE);
      doTestLong(Long.MIN_VALUE);
      doTestLong(Long.MAX_VALUE);
    }

    @Test
    public void testList() {
      CBORObject beo = CBORObject.NewArray();
      beo.Add(CBORObject.FromObject(1));
      beo.Add(CBORObject.FromObject("two"));
      beo.Add(CBORObject.FromObject(3));
      beo.Add(CBORObject.FromObject("four"));
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
    public void testDictionary() {
      CBORObject beo = CBORObject.NewMap();
      beo.set("zero",CBORObject.FromObject(1));
      beo.set("one",CBORObject.FromObject("two"));
      beo.set("two",CBORObject.FromObject(3));
      beo.set("three",CBORObject.FromObject("four"));
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
    public void testString() {
      doTestString("");
      doTestString(" ");
      doTestString("test");

  doTestString(TestCommon.Repeat("three", 15));
      doTestString("te\u007fst");
      doTestString("te\u0080st");
      doTestString("te\u3000st");
      doTestString("te\u07ffst");
      doTestString("te\u0800st");
      doTestString("te\uffffst");
      doTestString("te\ud7ffst");
      doTestString("te\ue000st");
      doTestString("te\ud800\udc00st");
      doTestString("te\udbff\udc00st");
      doTestString("te\ud800\udfffst");
      doTestString("te\udbff\udfffst");
    }
  }
