package com.upokecenter.test;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import java.util.*;
import java.io.*;
import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  public class CBORTest {
    public static int ByteArrayCompareLengthFirst(byte[] a, byte[] b) {
      if (a == null) {
        return (b == null) ? 0 : -1;
      }
      if (b == null) {
        return 1;
      }
      if (a.length != b.length) {
        return a.length < b.length ? -1 : 1;
      }
      for (int i = 0; i < a.length; ++i) {
        if (a[i] != b[i]) {
          return (a[i] < b[i]) ? -1 : 1;
        }
      }
      return 0;
    }

    @Test
    public void TestCorrectUtf8Specific() {
      TestJsonUtf8One(new byte[] {
        (byte)0xe8,
        (byte)0xad,
        (byte)0xbd,
        (byte)0xf1,
        (byte)0x81,
        (byte)0x95,
        (byte)0xb9,
        (byte)0xc3, (byte)0x84, (byte)0xcc, (byte)0xb6, (byte)0xcd,
        (byte)0xa3,
       });
      TestJsonUtf8One(new byte[] {
        (byte)0xe8,
        (byte)0x89,
        (byte)0xa0,
        (byte)0xf2,
        (byte)0x97,
        (byte)0x84, (byte)0xbb, 0x3f, (byte)0xd1, (byte)0x83, (byte)0xd1,
        (byte)0xb9,
       });
      TestJsonUtf8One(new byte[] {
        (byte)0xf3,
        (byte)0xbb,
        (byte)0x98,
        (byte)0x8a,
        (byte)0xc3,
        (byte)0x9f,
        (byte)0xe7,
        (byte)0xa5,
        (byte)0x96, (byte)0xd9, (byte)0x92, (byte)0xe1, (byte)0xa3,
        (byte)0xad,
       });
      TestJsonUtf8One(new byte[] {
        (byte)0xf0,
        (byte)0xa7,
        (byte)0xbf,
        (byte)0x84, 0x70, 0x55,
        (byte)0xd0, (byte)0x91, (byte)0xe8, (byte)0xbe, (byte)0x9f,
       });
      TestJsonUtf8One(new byte[] {
        (byte)0xd9,
        (byte)0xae,
        (byte)0xe4,
        (byte)0xa1,
        (byte)0xa0,
        (byte)0xf3,
        (byte)0x90,
        (byte)0x94,
        (byte)0x99,
        (byte)0xf3,
        (byte)0xab,
        (byte)0x8a, (byte)0xad, (byte)0xf4, (byte)0x88, (byte)0x9a,
        (byte)0x9a,
       });
      TestJsonUtf8One(new byte[] {
        0x3d,
        (byte)0xf2,
        (byte)0x83,
        (byte)0xa9,
        (byte)0xbe,
        (byte)0xea,
        (byte)0xb9,
        (byte)0xbd, (byte)0xd7, (byte)0x8b, (byte)0xe7, (byte)0xbc,
        (byte)0x83,
       });
      TestJsonUtf8One(new byte[] {
        (byte)0xc4,
        (byte)0xab, (byte)0xf4, (byte)0x8e, (byte)0x8a, (byte)0x91, 0x61, 0x4d,
        0x3b,
       });
      TestJsonUtf8One(new byte[] {
        (byte)0xf1,
        (byte)0xae,
        (byte)0x86, (byte)0xad, 0x5f, (byte)0xd0, (byte)0xb7, 0x6e, (byte)0xda,
        (byte)0x85,
       });
    }

    public static void TestJsonUtf8One(byte[] bytes) {
      if (bytes == null) {
        throw new NullPointerException("bytes");
      }
      String str = com.upokecenter.util.DataUtilities.GetUtf8String(bytes, false);
      byte[] bytes2 = new byte[bytes.length + 2];
      bytes2[0] = 0x22;
      System.arraycopy(bytes, 0, bytes2, 1, bytes.length);
      bytes2[bytes2.length - 1] = 0x22;
      String str2 = CBORObject.FromJSONBytes(bytes2)
        .AsString();
      if (!str.equals(str2)) {
        Assert.assertEquals(TestCommon.ToByteArrayString(bytes), str, str2);
      }
    }

    @Test
    public void TestCorrectUtf8() {
      RandomGenerator rg = new RandomGenerator();
      for (int i = 0; i < 500; ++i) {
        TestJsonUtf8One(RandomObjects.RandomUtf8Bytes(rg, true));
      }
    }

    @Test
    public void TestLexOrderSpecific1() {
      byte[] bytes1 = new byte[] {
        (byte)129, (byte)165, 27, 0, 0, 65, 2, 0, 0, (byte)144, (byte)172, 71,
        125, 0, 14, (byte)204, 3, 19, (byte)214, 67, 93, 67, 70, 101, 123, 121, 96, 44,
        68, 69,
        (byte)158, 1, (byte)193, (byte)250, 21, 59, 122, (byte)166, 24, 16, (byte)141, (byte)232, 48, (byte)145, 97,
        72, 58,
        (byte)134, 85, (byte)244, 83, 100, 92, 115, 76, 82, 99, 80, 122, 94,
       };
      byte[] bytes2 = new byte[] {
        (byte)129, (byte)165, 27, 0, 0, 127, (byte)163, 0, 0, (byte)137, 100,
        69, (byte)167, 15, 101, 37, 18, 69, (byte)230, (byte)236, 57, (byte)241, (byte)146, 101, 120, 80,
        66, 115,
        64, 98, 91, 105, 100, 102, 102, 78, 106, 101, 82, 117, 82, 46, 80, 69,
        (byte)150,
        80, (byte)162, (byte)211, (byte)214, 105, 122, 59, 65, 32, 80, 70, 47, 90, 113, 66, (byte)187,
        69,
       };
      byte[] bytes3 = new byte[] {
        (byte)129, (byte)165, 67, 93, 67, 70, 101, 123, 121, 96,
        44, 68, 100, 92, 115, 76, 82, 99, 80, 122, 94, 27, 0, 0, 65, 2, 0,
        0, (byte)144,
        (byte)172, 71, 125, 0, 14, (byte)204, 3, 19, (byte)214, 97, 72, 58, (byte)134, 85, (byte)244, 83,
        69, (byte)158,
        1, (byte)193, (byte)250, 21, 59, 122, (byte)166, 24, 16, (byte)141, (byte)232, 48, (byte)145,
       };
      byte[] bytes4 = new byte[] {
        (byte)129, (byte)165, 27, 0, 0, 127, (byte)163, 0, 0, (byte)137, 100,
        69, (byte)167, 15, 101, 37, 18, 98, 91, 105, 100, 102, 102, 78, 106, 69,
        (byte)230, (byte)236,
        57, (byte)241, (byte)146, 101, 120, 80, 66, 115, 64, 105, 122, 59, 65, 32, 80,
        70, 47,
        90, 113, 66, (byte)187, 69, 101, 82, 117, 82, 46, 80, 69, (byte)150, 80, (byte)162, (byte)211,
        (byte)214,
       };
      CBORObject cbor1 = CBORObject.DecodeFromBytes(bytes1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes(bytes2);
      CBORObject cbor3 = CBORObject.DecodeFromBytes(bytes3);
      CBORObject cbor4 = CBORObject.DecodeFromBytes(bytes4);
      TestCommon.CompareTestLess(cbor1, cbor2);
      TestCommon.CompareTestLess(cbor1, cbor4);
      TestCommon.CompareTestLess(cbor3, cbor2);
      TestCommon.CompareTestLess(cbor3, cbor4);
    }

    @Test
    public void TestLexOrderSpecific2() {
      byte[] bytes1 = new byte[] {
        (byte)129, (byte)165, 59, 72, 110, 0, 0, 122, (byte)250, (byte)251,
        (byte)131, 71, 22, (byte)187, (byte)235, (byte)209, (byte)143, 30, (byte)146, 69, 36, (byte)230, (byte)134, 20, 97,
        100, 78,
        112, 92, 101, 70, 54, (byte)136, (byte)203, (byte)227, (byte)188, 120, 64, 72, 58, 42, (byte)171,
        (byte)177, 73,
        (byte)245, (byte)198, (byte)139, 99, 36, 116, 76, 101, 99, 109, 60, 113, 107, 70, (byte)219,
        37, 80,
        108, 40, (byte)133,
       };
      byte[] bytes2 = new byte[] {
        (byte)129, (byte)165, 67, 62, (byte)217, 7, 69, 113, (byte)188, (byte)156,
        26, 34, 69, 32, 101, (byte)130, (byte)188, (byte)201, 27, 122, (byte)228, 0, 0, 0, 0, (byte)186,
        9, 69,
        70, 71, (byte)152, 50, 17, 67, (byte)231, (byte)129, (byte)240, 100, 79, 116, 84, 81, 69, (byte)188,
        114,
        (byte)227, 101, (byte)209, (byte)244, 103, 91, 37, 62, 59, 78, 124, 95,
       };
      byte[] bytes3 = new byte[] {
        (byte)129, (byte)165, 72, 58, 42, (byte)171, (byte)177, 73, (byte)245, (byte)198,
        (byte)139, 99, 36, 116, 76, 69, 36, (byte)230, (byte)134, 20, 97, 100, 78, 112, 92,
        101, 70,
        54, (byte)136, (byte)203, (byte)227, (byte)188, 120, 64, 59, 72, 110, 0, 0, 122, (byte)250, (byte)251,
        (byte)131, 71,
        22, (byte)187, (byte)235, (byte)209, (byte)143, 30, (byte)146, 101, 99, 109, 60, 113, 107, 70,
        (byte)219, 37,
        80, 108, 40, (byte)133,
       };
      byte[] bytes4 = new byte[] {
        (byte)129, (byte)165, 69, 70, 71, (byte)152, 50, 17, 67, (byte)231,
        (byte)129, (byte)240, 100, 79, 116, 84, 81, 69, (byte)188, 114, (byte)227, 101, (byte)209, 67, 62,
        (byte)217, 7,
        69, 113, (byte)188, (byte)156, 26, 34, (byte)244, 103, 91, 37, 62, 59, 78, 124, 95,
        69, 32,
        101, (byte)130, (byte)188, (byte)201, 27, 122, (byte)228, 0, 0, 0, 0, (byte)186, 9,
       };
      CBORObject cbor1 = CBORObject.DecodeFromBytes(bytes1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes(bytes2);
      CBORObject cbor3 = CBORObject.DecodeFromBytes(bytes3);
      CBORObject cbor4 = CBORObject.DecodeFromBytes(bytes4);
      TestCommon.CompareTestLess(cbor1, cbor2);
      TestCommon.CompareTestLess(cbor1, cbor4);
      TestCommon.CompareTestLess(cbor3, cbor2);
      TestCommon.CompareTestLess(cbor3, cbor4);
    }

    @Test
    public void TestLexOrderSpecific3() {
      byte[] bytes1 = new byte[] {
        (byte)129, (byte)165, 67, 62, (byte)217, 7, 69, 113, (byte)188, (byte)156,
        26, 34, 69, 32, 101, (byte)130, (byte)188, (byte)201, 27, 122, (byte)228, 0, 0, 0, 0, (byte)186,
        9, 69,
        70, 71, (byte)152, 50, 17, 67, (byte)231, (byte)129, (byte)240, 100, 79, 116, 84, 81, 69, (byte)188,
        114,
        (byte)227, 101, (byte)209, (byte)244, 103, 91, 37, 62, 59, 78, 124, 95,
       };
      byte[] bytes2 = new byte[] {
        (byte)129, (byte)165, 67, 64, (byte)196, (byte)213, (byte)217, 43, 37, 27,
        37, (byte)184, 58, (byte)144, (byte)176, (byte)207, (byte)252, (byte)194, 68, 43, 68, 5, (byte)219, 27, 0, 0, 126,
        (byte)173, 36, (byte)137, (byte)166, 19, 69, 27, 99, (byte)166, 37, (byte)216, 101, 87, 91, 80,
        79, 100,
        69, (byte)217, 77, (byte)189, (byte)138, 22, 101, 40, 93, 54, 59, 73, 97, 60, 99, 69,
        35, 66,
       };
      byte[] bytes3 = new byte[] {
        (byte)129, (byte)165, 69, 70, 71, (byte)152, 50, 17, 67, (byte)231,
        (byte)129, (byte)240, 100, 79, 116, 84, 81, 69, (byte)188, 114, (byte)227, 101, (byte)209, 67, 62,
        (byte)217, 7,
        69, 113, (byte)188, (byte)156, 26, 34, (byte)244, 103, 91, 37, 62, 59, 78, 124, 95,
        69, 32,
        101, (byte)130, (byte)188, (byte)201, 27, 122, (byte)228, 0, 0, 0, 0, (byte)186, 9,
       };
      byte[] bytes4 = new byte[] {
        (byte)129, (byte)165, 67, 64, (byte)196, (byte)213, (byte)217, 43, 37, 27,
        37, (byte)184, 58, (byte)144, (byte)176, (byte)207, (byte)252, (byte)194, 69, 27, 99, (byte)166, 37, (byte)216, 101,
        87, 91,
        80, 79, 100, 97, 60, 99, 69, 35, 66, 69, (byte)217, 77, (byte)189, (byte)138, 22, 101,
        40, 93,
        54, 59, 73, 68, 43, 68, 5, (byte)219, 27, 0, 0, 126, (byte)173, 36, (byte)137, (byte)166, 19,
       };
      CBORObject cbor1 = CBORObject.DecodeFromBytes(bytes1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes(bytes2);
      CBORObject cbor3 = CBORObject.DecodeFromBytes(bytes3);
      CBORObject cbor4 = CBORObject.DecodeFromBytes(bytes4);
      TestCommon.CompareTestLess(cbor1, cbor2);
      TestCommon.CompareTestLess(cbor1, cbor4);
      TestCommon.CompareTestLess(cbor3, cbor2);
      TestCommon.CompareTestLess(cbor3, cbor4);
    }

    @Test
    public void TestCBORMapAdd() {
      CBORObject cbor = CBORObject.NewMap();
      cbor.Add(1, 2);
      if (!(cbor.ContainsKey(
          ToObjectTest.TestToFromObjectRoundTrip(1))))Assert.fail();
      {
        int varintTemp2 = cbor.get(
            ToObjectTest.TestToFromObjectRoundTrip(1))
          .AsInt32();
        Assert.assertEquals(2, varintTemp2);
      }
      {
        String stringTemp = cbor.ToJSONString();
        Assert.assertEquals(
          "{\"1\":2}",
          stringTemp);
      }
      cbor.Add("hello", 2);
      if (!(cbor.ContainsKey("hello"))) {
 Assert.fail();
 }

      if (!(cbor.ContainsKey(ToObjectTest.TestToFromObjectRoundTrip(
            "hello"))))Assert.fail();
      Assert.assertEquals((int)2, cbor.get("hello").AsInt32Value());
      cbor.Set(1, 3);
      CBORObject cborone = ToObjectTest.TestToFromObjectRoundTrip(1);
      if (!(cbor.ContainsKey(cborone))) {
 Assert.fail();
 }
      Assert.assertEquals((int)3, cbor.get(cborone).AsInt32Value());
    }

    @Test
    public void TestArray() {
      CBORObject cbor = CBORObject.FromJSONString("[]");
      cbor.Add(ToObjectTest.TestToFromObjectRoundTrip(3));
      cbor.Add(ToObjectTest.TestToFromObjectRoundTrip(4));
      byte[] bytes = CBORTestCommon.CheckEncodeToBytes(cbor);
      TestCommon.AssertByteArraysEqual(
        new byte[] { (byte)((byte)0x80 | 2), 3, 4 },
        bytes);
      cbor = CBORObject.FromObject(new String[] { "a", "b", "c",
        "d", "e",
      });
      Assert.assertEquals("[\"a\",\"b\",\"c\",\"d\",\"e\"]", cbor.ToJSONString());
      String[] strArray = (String[])cbor.ToObject(String[].class);
      cbor = CBORObject.FromObject(strArray);
      Assert.assertEquals("[\"a\",\"b\",\"c\",\"d\",\"e\"]", cbor.ToJSONString());
      CBORTestCommon.AssertRoundTrip(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0x9f, 0, 1, 2, 3,
        4, 5,
        6, 7, (byte)0xff,
       });
      {
        String stringTemp = cbor.ToJSONString();
        String str1817 = "[0,1,2,3,4,5,6,7]";

        Assert.assertEquals(
          str1817,
          stringTemp);
      }
    }

    @Test
    public void TestEInteger() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 500; ++i) {
        EInteger bi = RandomObjects.RandomEInteger(r);
        CBORTestCommon.AssertJSONSer(
          ToObjectTest.TestToFromObjectRoundTrip(bi),
          bi.toString());

        if (!(
          ToObjectTest.TestToFromObjectRoundTrip(
            bi).AsNumber().IsInteger())) {
 Assert.fail();
 }

        CBORTestCommon.AssertRoundTrip(
          ToObjectTest.TestToFromObjectRoundTrip(bi));
        CBORTestCommon.AssertRoundTrip(ToObjectTest.TestToFromObjectRoundTrip(
            EDecimal.FromString(bi.toString() + "e1")));
      }
      EInteger[] ranges = {
        EInteger.FromString("-9223372036854776320"),
        EInteger.FromString("-9223372036854775296"),
        EInteger.FromString("-512"),
        EInteger.FromString("512"),
        EInteger.FromString("9223372036854775295"),
        EInteger.FromString("9223372036854776319"),
        EInteger.FromString("18446744073709551103"),
        EInteger.FromString("18446744073709552127"),
      };
      for (int i = 0; i < ranges.length; i += 2) {
        EInteger bigintTemp = ranges[i];
        while (true) {
          CBORTestCommon.AssertJSONSer(
            ToObjectTest.TestToFromObjectRoundTrip(bigintTemp),
            bigintTemp.toString());
          if (bigintTemp.equals(ranges[i + 1])) {
            break;
          }
          bigintTemp = bigintTemp.Add(EInteger.FromInt32(1));
        }
      }
    }

    @Test
    public void TestBigNumBytes() {
      CBORObject o = null;
      o = CBORTestCommon.FromBytesTestAB(new byte[] {
        (byte)0xc2, 0x41,
        (byte)0x88,
       });
      Assert.assertEquals(
        EInteger.FromRadixString("88", 16),
        o.ToObject(EInteger.class));
      o = CBORTestCommon.FromBytesTestAB(new byte[] {
        (byte)0xc2, 0x42,
        (byte)0x88,
        0x77,
       });
      Assert.assertEquals(
        EInteger.FromRadixString("8877", 16),
        o.ToObject(EInteger.class));
      o = CBORTestCommon.FromBytesTestAB(new byte[] {
        (byte)0xc2, 0x44,
        (byte)0x88, 0x77,
        0x66,
        0x55,
       });
      Assert.assertEquals(
        EInteger.FromRadixString("88776655", 16),
        o.ToObject(EInteger.class));
      o = CBORTestCommon.FromBytesTestAB(new byte[] {
        (byte)0xc2, 0x47,
        (byte)0x88, 0x77,
        0x66,
        0x55, 0x44, 0x33, 0x22,
       });
      Assert.assertEquals(
        EInteger.FromRadixString("88776655443322", 16),
        o.ToObject(EInteger.class));
    }

    @Test
    public void TestByte() {
      for (int i = 0; i <= 255; ++i) {
        CBORTestCommon.AssertJSONSer(
          ToObjectTest.TestToFromObjectRoundTrip((byte)i),
          TestCommon.IntToString(i));
      }
    }

    @Test
    public void TestByteArray() {
      CBORObject co = ToObjectTest.TestToFromObjectRoundTrip(
          new byte[] { 0x20, 0x78 });
      EInteger[] tags = co.GetAllTags();
      Assert.assertEquals(0, tags.length);
      byte[] bytes = co.GetByteString();
      Assert.assertEquals(2, bytes.length);
      Assert.assertEquals(0x20, bytes[0]);
      Assert.assertEquals(0x78, bytes[1]);
    }

    @Test
    public void TestByteStringStream() {
      CBORTestCommon.FromBytesTestAB(
        new byte[] { 0x5f, 0x41, 0x20, 0x41, 0x20, (byte)0xff });
    }

    @Test
    public void TestWriteToJSONSpecific() {
      byte[] bytes = new byte[] {
        0x6a, 0x25, 0x7f, 0x41, 0x58, 0x11, 0x54,
        (byte)0xc3, (byte)0x94, 0x19, 0x49,
       };
      TestWriteToJSON(CBORObject.DecodeFromBytes(bytes));
      bytes = new byte[] {
        (byte)0xfb, 0x61, (byte)0x90, 0x00, 0x00, 0x7c,
        0x01, 0x5a, 0x0a,
       };
      TestWriteToJSON(CBORObject.DecodeFromBytes(bytes));
      bytes = new byte[] {
        (byte)0xfb, 0x36, (byte)0x90, 0x01, 0x00, 0x3f,
        (byte)0xd9, 0x2b, (byte)0xdb,
       };
      TestWriteToJSON(CBORObject.DecodeFromBytes(bytes));
    }

    @Test
    public void TestEmptyIndefiniteLength() {
      CBORObject cbor;
      cbor = CBORObject.DecodeFromBytes(new byte[] { 0x5f, (byte)0xff });
      Assert.assertEquals(0, cbor.GetByteString().length);
      cbor = CBORObject.DecodeFromBytes(new byte[] { 0x7f, (byte)0xff });
      String str = cbor.AsString();
      Assert.assertEquals(0, str.length());
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0x9f, (byte)0xff });
      Assert.assertEquals(CBORType.Array, cbor.getType());
      Assert.assertEquals(0, cbor.size());
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xbf, (byte)0xff });
      Assert.assertEquals(CBORType.Map, cbor.getType());
      Assert.assertEquals(0, cbor.size());
    }

    @Test
    public void TestByteStringStreamNoIndefiniteWithinDefinite() {
      try {
        CBORTestCommon.FromBytesTestAB(new byte[] {
          0x5f, 0x41, 0x20, 0x5f,
          0x41, 0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          0x5f, 0x5f, 0x42, 0x20,
          0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          0x5f, 0x42, 0x20, 0x20,
          0x5f, 0x42, 0x20, 0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          0x5f, 0x7f, 0x62, 0x20,
          0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          0x5f, 0x5f, 0x41, 0x20,
          (byte)0xff, 0x41, 0x20, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          0x7f, 0x7f, 0x62, 0x20,
          0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          0x7f, 0x62, 0x20, 0x20,
          0x7f, 0x62, 0x20, 0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          0x7f, 0x5f, 0x42, 0x20,
          0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          0x7f, 0x7f, 0x61, 0x20,
          (byte)0xff, 0x61, 0x20, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { 0x5f, 0x00, (byte)0xff });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { 0x7f, 0x00, (byte)0xff });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { 0x5f, 0x20, (byte)0xff });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { 0x7f, 0x20, (byte)0xff });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xbf, 0x00,
          (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xbf, 0x20,
          (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestByteStringStreamNoTagsBeforeDefinite() {
      try {
        CBORTestCommon.FromBytesTestAB(new byte[] {
          0x5f, 0x41, 0x20,
          (byte)0xc2, 0x41, 0x20, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    public static String ObjectMessage(CBORObject obj) {
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      return new StringBuilder()
        .append("CBORObject.DecodeFromBytes(")
        .append(TestCommon.ToByteArrayString(obj.EncodeToBytes()))
        .append("); /").append("/ ").append(obj.ToJSONString()).toString();
    }

    public static void TestCanFitInOne(CBORObject ed) {
      EDecimal ed2;
      if (ed == null) {
        throw new NullPointerException("ed");
      }
      CBORNumber edNumber = ed.AsNumber();
      EDecimal edNumberED = AsED(ed);
      ed2 = EDecimal.FromDouble(edNumberED.ToDouble());
      if ((edNumberED.compareTo(ed2) == 0) != edNumber.CanFitInDouble()) {
        Assert.fail(ObjectMessage(ed) + "\n// CanFitInDouble");
      }
      ed2 = EDecimal.FromSingle(AsED(ed).ToSingle());
      if ((edNumberED.compareTo(ed2) == 0) != edNumber.CanFitInSingle()) {
        Assert.fail(ObjectMessage(ed) + "\n// CanFitInSingle");
      }
      if (!edNumber.IsInfinity() && !edNumber.IsNaN()) {
        if (edNumberED.IsInteger() != edNumber.IsInteger()) {
          Assert.fail(ObjectMessage(ed) + "\n// IsInteger");
        }
      }
      if (!edNumber.IsInfinity() && !edNumber.IsNaN()) {
        EDecimal edec = edNumberED;
        EInteger bi = null;
        try {
          bi = edec.ToSizedEInteger(128);
        } catch (ArithmeticException ex) {
          bi = null;
        }
        if (edNumber.IsInteger()) {
          if ((bi != null && bi.GetSignedBitLengthAsInt64() <= 31) !=
            edNumber.CanFitInInt32()) {
            Assert.fail(ObjectMessage(ed) + "\n// Int32");
          }
        }
        if ((bi != null && bi.GetSignedBitLengthAsInt64() <= 31) !=
          edNumber.CanTruncatedIntFitInInt32()) {
          Assert.fail(ObjectMessage(ed) + "\n// TruncInt32");
        }
        if (edNumber.IsInteger()) {
          if ((bi != null && bi.GetSignedBitLengthAsInt64() <= 63) !=
            edNumber.CanFitInInt64()) {
            Assert.fail(ObjectMessage(ed) + "\n// Int64");
          }
        }
        if ((bi != null && bi.GetSignedBitLengthAsInt64() <= 63) !=
          edNumber.CanTruncatedIntFitInInt64()) {
          Assert.fail(ObjectMessage(ed) + "\n// TruncInt64");
        }
      }
    }

    @Test(timeout = 10000)
    public void TestCanFitIn() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 5000; ++i) {
        CBORObject ed = CBORTestCommon.RandomNumber(r);
        TestCanFitInOne(ed);
      }
      CBORObject cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xfb,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
       });
      TestCanFitInOne(cbor);
    }

    @Test
    public void TestCanFitInSpecificCases() {
      CBORObject cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xfb,
        0x41, (byte)0xe0, (byte)0x85, 0x48, 0x2d, 0x14, 0x47, 0x7a,
       }); // 2217361768.63373
      Assert.assertEquals(
        EInteger.FromString("2217361768"),
        cbor.ToObject(EInteger.class));

      if (
        AsEI(cbor).GetSignedBitLengthAsInt64()
        <= 31) {
 Assert.fail();
 }
      if (cbor.AsNumber().CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5, (byte)0x82,
        0x18, 0x2f, 0x32,
       }); // -2674012278751232
      Assert.assertEquals(
        52L,
        AsEI(cbor).GetSignedBitLengthAsInt64());
      if (!(cbor.AsNumber().CanFitInInt64())) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(2554895343L)
        .AsNumber().CanFitInSingle()) {
 Assert.fail();
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5, (byte)0x82,
        0x10, 0x38, 0x64,
       }); // -6619136
      Assert.assertEquals(EInteger.FromString("-6619136"),
        cbor.ToObject(EInteger.class));
      Assert.assertEquals(-6619136, cbor.AsInt32());
      if (!(cbor.AsNumber().CanTruncatedIntFitInInt32())) {
 Assert.fail();
 }
    }

    @Test
    public void TestCBOREInteger() {
      CBORObject o = CBORObject.DecodeFromBytes(new byte[] {
        0x3b, (byte)0xce,
        (byte)0xe2, 0x5a, 0x57, (byte)0xd8, 0x21, (byte)0xb9, (byte)0xa7,
       });
      Assert.assertEquals(
        EInteger.FromString("-14907577049884506536"),
        o.ToObject(EInteger.class));
    }

    @Test
    public void TestCBORExceptions() {
      try {
        CBORObject.NewArray().Remove(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().Remove(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().Add(CBORObject.Null);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().Add(CBORObject.True);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.Remove(CBORObject.True);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(0).Remove(CBORObject.True);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("")
        .Remove(CBORObject.True);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().ToObject(EFloat.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(EFloat.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(EFloat.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(EFloat.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(EFloat.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          "").ToObject(EFloat.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCBORFromArray() {
      CBORObject o = CBORObject.FromObject(new int[] { 1, 2, 3 });
      Assert.assertEquals(3, o.size());
      Assert.assertEquals(1, o.get(0).AsInt32());
      Assert.assertEquals(2, o.get(1).AsInt32());
      Assert.assertEquals(3, o.get(2).AsInt32());
      CBORTestCommon.AssertRoundTrip(o);
    }

    @Test
    public void TestCBORInfinityRoundTrip() {
      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.RatPosInf));

      boolean bval = ToObjectTest.TestToFromObjectRoundTrip(
          CBORTestCommon.FloatNegInf).AsNumber().IsInfinity();
      if (!(bval)) {
 Assert.fail();
 }

      if (!(ToObjectTest.TestToFromObjectRoundTrip(
          CBORTestCommon.RatPosInf).AsNumber().IsInfinity())) {
 Assert.fail();
 }

      if (!(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf)
        .AsNumber().IsNegativeInfinity())) {
 Assert.fail();
 }

      if (!(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.RatPosInf)
        .AsNumber().IsPositiveInfinity())) {
 Assert.fail();
 }
      if (!(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.RatPosInf)
        .AsNumber().IsInfinity())) {
 Assert.fail();
 }

      if (!(
        CBORObject.PositiveInfinity.AsNumber().IsPositiveInfinity())) {
 Assert.fail();
 }

      if (!(
        CBORObject.NegativeInfinity.AsNumber().IsNegativeInfinity())) {
 Assert.fail();
 }
      if (!(CBORObject.NaN.AsNumber().IsNaN())) {
 Assert.fail();
 }

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecNegInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecPosInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatPosInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY));
    }

    @Test
    public void TestEquivJSONSpecificA() {
      TestEquivJSONOne(new byte[] {
        0x2d, 0x37, 0x30, 0x31, 0x39, 0x34,
        0x38, 0x33, 0x35, 0x39, 0x31, 0x33, 0x37, 0x34, 0x38, 0x45, 0x30,
       });
    }

    public static boolean TestEquivJSONOne(byte[] bytes) {
      if (bytes == null) {
        throw new NullPointerException("bytes");
      }
      if (!(bytes.length > 0)) {
        return false;
      }
      CBORObject cbo = CBORObject.FromJSONBytes(bytes);
      if (!(cbo != null)) {
 Assert.fail();
 }
      CBORObject cbo2 = CBORObject.FromJSONString(cbo.ToJSONString());
      if (!(cbo2 != null)) {
 Assert.fail();
 }
      if (!cbo.equals(cbo2)) {
        System.out.print("jsonstring");
        System.out.print(TestCommon.ToByteArrayString(bytes));
        System.out.print(com.upokecenter.util.DataUtilities.GetUtf8String(bytes, true));
        System.out.print("old " + TestCommon.ToByteArrayString(cbo.ToJSONBytes()));
        System.out.print(cbo.ToJSONString());
        System.out.print("new " +
          TestCommon.ToByteArrayString(cbo2.ToJSONBytes()));
        System.out.print(cbo2.ToJSONString());
        Assert.assertEquals(cbo, cbo2);
      }
      cbo2 = CBORObject.FromJSONBytes(cbo.ToJSONBytes());
      if (!(cbo2 != null)) {
 Assert.fail();
 }
      if (!cbo.equals(cbo2)) {
        System.out.print("jsonbytes");
        System.out.print(TestCommon.ToByteArrayString(bytes));
        System.out.print(com.upokecenter.util.DataUtilities.GetUtf8String(bytes, true));
        System.out.print("old " + TestCommon.ToByteArrayString(cbo.ToJSONBytes()));
        System.out.print(cbo.ToJSONString());
        System.out.print("new " +
          TestCommon.ToByteArrayString(cbo2.ToJSONBytes()));
        System.out.print(cbo2.ToJSONString());
        Assert.assertEquals(cbo, cbo2);
      }
      return true;
    }

    public static boolean TestEquivJSONNumberOne(byte[] bytes) {
      // Assume the JSON begins and ends with a digit
      if (bytes == null) {
        throw new NullPointerException("bytes");
      }
      if (!(bytes.length > 0)) {
        return false;
      }
      if (!((bytes[0] >= 0x30 && bytes[0] <= 0x39) || bytes[0] == (byte)'-')) {
        return false;
      }
      if (!(bytes[bytes.length - 1] >= 0x30 && bytes[bytes.length - 1] <=
          0x39)) {
        return false;
      }
      CBORObject cbor, cbor2, cbored, cbor3;
      JSONOptions jsoptions = new JSONOptions("numberconversion=full");
      String str = com.upokecenter.util.DataUtilities.GetUtf8String(bytes, true);
      EDecimal ed = EDecimal.FromString(str);
      // Test consistency between JSON conversion methods
      cbor = CBORObject.FromJSONBytes(bytes, jsoptions);
      cbor2 = CBORDataUtilities.ParseJSONNumber(str, jsoptions);
      cbor3 = CBORObject.FromJSONString(str, jsoptions);
      cbored = (ed.getExponent().compareTo(0) == 0 && !(ed.isNegative() && ed.signum()
            == 0)) ?
        CBORObject.FromObject(ed.getMantissa()) : CBORObject.FromObject(ed);
      Assert.assertEquals("[" + str + "] cbor2",cbor,cbor2);
      Assert.assertEquals("[" + str + "] cbor3",cbor,cbor3);
      Assert.assertEquals("[" + str + "] cbored",cbor,cbored);
      return true;
    }

    public static boolean TestEquivJSONNumberDecimal128One(byte[] bytes) {
      // Assume the JSON begins and ends with a digit
      if (bytes == null) {
        throw new NullPointerException("bytes");
      }
      if (!(bytes.length > 0)) {
        return false;
      }
      if (!((bytes[0] >= 0x30 && bytes[0] <= 0x39) || bytes[0] == (byte)'-')) {
        return false;
      }
      if (!(bytes[bytes.length - 1] >= 0x30 && bytes[bytes.length - 1] <=
          0x39)) {
        return false;
      }
      CBORObject cbor, cbor2, cbored, cbor3;
      JSONOptions jsoptions = new JSONOptions("numberconversion=decimal128");
      String str = com.upokecenter.util.DataUtilities.GetUtf8String(bytes, true);
      // Test consistency between JSON conversion methods
      EDecimal ed = EDecimal.FromString(str, EContext.Decimal128);
      cbor = CBORObject.FromJSONBytes(bytes, jsoptions);
      cbor2 = CBORDataUtilities.ParseJSONNumber(str, jsoptions);
      cbor3 = CBORObject.FromJSONString(str, jsoptions);
      cbored = CBORObject.FromObject(ed);
      Assert.assertEquals("[" + str + "] cbor2",cbor,cbor2);
      Assert.assertEquals("[" + str + "] cbor3",cbor,cbor3);
      Assert.assertEquals("[" + str + "] cbored",cbor,cbored);
      return true;
    }

    public static void TestCompareToOne(byte[] bytes) {
      CBORObject cbor = CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=1"));
      byte[] bytes2 = cbor.EncodeToBytes();
      CBORObject cbor2 = CBORObject.DecodeFromBytes(bytes2);
      if (!cbor.equals(cbor2)) {
        String sbytes = TestCommon.ToByteArrayString(bytes) +
          "\ncbor=" + cbor +
          "\ncborbytes=" + TestCommon.ToByteArrayString(bytes2) +
          "\ncbor2=" + cbor2 +
          "\ncborbytes2=" + TestCommon.ToByteArrayString(cbor2.EncodeToBytes());
        Assert.assertEquals(sbytes, cbor, cbor2);
      } else {
        Assert.assertEquals(cbor, cbor2);
      }
      if (cbor.compareTo(cbor2) != 0) {
        String sbytes = TestCommon.ToByteArrayString(bytes) +
          "\ncbor=" + cbor +
          "\ncborbytes=" + TestCommon.ToByteArrayString(bytes2) +
          "\ncbor2=" + cbor2 +
          "\ncborbytes2=" + TestCommon.ToByteArrayString(cbor2.EncodeToBytes());
        Assert.assertEquals(sbytes, 0, cbor.compareTo(cbor2));
      } else {
        Assert.assertEquals(0, cbor.compareTo(cbor2));
      }
    }

    @Test
    public void TestCompareToSpecificA() {
      byte[] bytes = new byte[] { (byte)0xfa, (byte)0xb3, 0x00, 0x00, 0x00 };
      TestCompareToOne(bytes);
    }

    @Test
    public void TestCompareToSpecificE() {
      byte[] bytes = new byte[] {
        (byte)0xbf,
        (byte)0xf9,
        (byte)0xce,
        (byte)0xdc,
        (byte)0x99, 0x00, 0x01,
        (byte)0xf8,
        (byte)0xa0, 0x61, 0x37, 0x12, 0x7f, 0x78, 0x0d, 0x1c, 0x78, 0x4a,
        0x48, 0x3e,
        (byte)0xe1,
        (byte)0xa5,
        (byte)0xb2,
        (byte)0xf4,
        (byte)0x82,
        (byte)0x8f,
        (byte)0x8a, 0x32, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04,
        0x2d, 0x57, 0x55, 0x08, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x02, 0x41, 0x28,
        (byte)0xff, (byte)0xe3, (byte)0xff,
       };
      TestCompareToOne(bytes);
    }

    @Test
    public void TestCompareToSpecificC() {
      byte[] bytes = new byte[] {
        (byte)0xb9, 0x00, 0x02,
        (byte)0xfa,
        (byte)0x93,
        (byte)0x96,
        (byte)0xf3,
        (byte)0xcb, 0x1b,
        (byte)0xe7, 0x65, 0x72,
        (byte)0x83,
        (byte)0xa0, 0x39,
        (byte)0xa0,
        (byte)0xfe, 0x7f, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01,
        0x2e, 0x7a, 0x00, 0x00, 0x00, 0x03, 0x1e, 0x33, 0x52, 0x60, 0x7a, 0x00,
        0x00, 0x00, 0x03, 0x62, 0x1e, 0x23,
        (byte)0xff, 0x18, (byte)0x89,
       };
      TestCompareToOne(bytes);
    }

    @Test
    public void TestCompareToSpecificD() {
      byte[] bytes = new byte[] {
        (byte)0xbf, 0x00, 0x00,
        (byte)0xe0, 0x00, 0x7f, 0x78, 0x10, 0x64, 0x6b, 0x05, 0x77, 0x38, 0x3c,
        0x51, 0x66, 0x7c, 0x02, 0x31, 0x51, 0x56, 0x33, 0x56, 0x6a, 0x7b, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0x70, 0x16, 0x20, 0x2f, 0x29,
        0x1a, 0x1f, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x78,
        0x01, 0x5c,
        (byte)0xff, (byte)0xfa, (byte)0xa1, (byte)0xeb, (byte)0xc3, 0x1d,
        (byte)0xff,
       };
      TestCompareToOne(bytes);
    }

    @Test
    public void TestCompareToSpecificB() {
      byte[] bytes = new byte[] {
        (byte)0xa4,
        (byte)0xe3,
        (byte)0xf8, 0x70,
        (byte)0xdb, 0x02, 0x2d, 0x0d, 0x30, 0x39, 0x14,
        (byte)0xf5,
        (byte)0x8c, 0x39, 0x56, 0x1c, 0x3a,
        (byte)0x92, 0x27, 0x00, 0x04, 0x39, 0x1e, 0x05,
        (byte)0xf9, 0x73,
        (byte)0xac, 0x7f, 0x78, 0x05, 0x2d,
        (byte)0xe5,
        (byte)0xad,
        (byte)0xb8, 0x0b, 0x63, 0x27, 0x50, 0x7e, 0x78, 0x02, 0x04, 0x56,
        (byte)0xff, 0x1b,
        (byte)0x9d, (byte)0x8c, 0x66, (byte)0xaf, 0x18, 0x1d, 0x01,
        (byte)0x8e,
       };
      TestCompareToOne(bytes);
    }

    @Test
    public void TestCompareToSpecific() {
      byte[] bytes;
      bytes = new byte[] {
        (byte)0xa2,
        (byte)0xf8,
        (byte)0xf7, 0x19,
        (byte)0xde,
        (byte)0x91, 0x7f, 0x79, 0x00, 0x11, 0x7b, 0x1b, 0x29, 0x59, 0x57, 0x6a,
        0x70, 0x68,
        (byte)0xe3,
        (byte)0x98,
        (byte)0xba, 0x6a, 0x49, 0x50, 0x54, 0x0b, 0x21, 0x62, 0x32, 0x17, 0x7b,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x67, 0x43, 0x37, 0x42,
        0x5f, 0x22, 0x7c, 0x0e, 0x68, 0x13, 0x74, 0x43, 0x1e, 0x4c, 0x5b, 0x2b,
        0x6c, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7a, 0x00,
        0x00, 0x00, 0x00, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x78, 0x01, 0x38, 0x78, 0x00, 0x78, 0x00, 0x7b, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x01, 0x39,
        (byte)0xff, (byte)0x9f, (byte)0xff,
       };
      TestCompareToOne(bytes);
    }

    @Test
    public void TestCompareB1() {
      byte[] bytes;
      CBORObject o;
      bytes = new byte[] {
        (byte)0xbb, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x02,
        (byte)0xf8, 0x2d, 0x11, 0x7f, 0x79, 0x00, 0x2e, 0x7c, 0x2c, 0x18, 0x40,
        0x3e,
        (byte)0xc7,
        (byte)0xa9, 0x0c, 0x57, 0x50, 0x63, 0x30, 0x0f, 0x07, 0x76, 0x14, 0x31,
        0x52, 0x5c, 0x0a, 0x43, 0x4a, 0x6f, 0x08, 0x11, 0x25, 0x0b, 0x1a, 0x10,
        0x74,
        (byte)0xf1,
        (byte)0x84,
        (byte)0xbd,
        (byte)0x93, 0x4f, 0x74, 0x23, 0x5b, 0x7c, 0x5c, 0x76, 0x70, 0x0a,
        (byte)0xde,
        (byte)0xa3, 0x5e, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0b,
        0x76,
        (byte)0xf0,
        (byte)0xad,
        (byte)0xbf,
        (byte)0xba, 0x14, 0x45, 0x0d, 0x2e, 0x6e, 0x62, 0x62, 0x10, 0x63,
        (byte)0xff, 0x35,
       };
      o = CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=1"));
      CBORTestCommon.AssertRoundTrip(o);
      bytes = new byte[] {
        (byte)0xd9, 0x0e, 0x02,
        (byte)0xbf, 0x7f, 0x78, 0x07, 0x12, 0x45, 0x2f, 0x48,
        (byte)0xc8,
        (byte)0xb7, 0x5a, 0x79, 0x00, 0x01, 0x5e, 0x7b, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x01, 0x72, 0x78, 0x00, 0x7a, 0x00, 0x00, 0x00, 0x01,
        0x49, 0x61, 0x6d, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01,
        0x13,
        (byte)0xff,
        (byte)0xed,
        (byte)0xfb,
        (byte)0x82, 0x18,
        (byte)0xc9, 0x6c, 0x3b, (byte)0xc0, 0x53, 0x1f, (byte)0xeb,
        (byte)0xff,
       };
      o = CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=1"));
      CBORTestCommon.AssertRoundTrip(o);
      bytes = new byte[] {
        (byte)0xbf,
        (byte)0xfa,
        (byte)0xc5, 0x7f, 0x16,
        (byte)0xe2,
        (byte)0xf9, 0x05, 0x2d, 0x7f, 0x79, 0x00, 0x02, 0x4f, 0x0a, 0x67, 0x1a,
        0x17, 0x17, 0x1d, 0x0a, 0x74, 0x0a, 0x79, 0x00, 0x0e, 0x48, 0x23, 0x4e,
        0x32, 0x53, 0x74, 0x78,
        (byte)0xf0,
        (byte)0xa9,
        (byte)0x8b,
        (byte)0xb9, 0x03, 0x68, 0x3b, 0x7b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x02, 0x67, 0x0e, 0x7a, 0x00, 0x00, 0x00, 0x02, 0x74, 0x37, 0x79,
        0x00, 0x09, 0x6f, 0x11, 0x60, 0x3c, 0x24, 0x13, 0x16, 0x25, 0x35, 0x78,
        0x01, 0x6a,
        (byte)0xff, (byte)0xf9, (byte)0xc0, 0x69, 0x19, 0x0b, (byte)0x8a, 0x05,
        (byte)0xff,
       };
      o = CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=1"));
      CBORTestCommon.AssertRoundTrip(o);
    }

    @Test
    public void TestCompareB() {
      {
        String stringTemp = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xfa, 0x7f, (byte)0x80, 0x00, 0x00,
         }).ToObject(ERational.class).toString();
        Assert.assertEquals(
          "Infinity",
          stringTemp);
      }
      {
        CBORObject objectTemp = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc5, (byte)0x82, 0x38, (byte)0xc7, 0x3b, 0x00, 0x00, 0x08,
          (byte)0xbf, (byte)0xda, (byte)0xaf, 0x73, 0x46,
         });
        CBORObject objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          0x3b, 0x5a, (byte)0x9b, (byte)0x9a, (byte)0x9c, (byte)0xb4, (byte)0x95,
          (byte)0xbf, 0x71,
         });
        AddSubCompare(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xfa, 0x1f, (byte)0x80, (byte)0xdb, (byte)0x9b,
         });
        CBORObject objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xfb, 0x31, (byte)0x90, (byte)0xea, 0x16, (byte)0xbe, (byte)0x80,
          0x0b, 0x37,
         });
        AddSubCompare(objectTemp, objectTemp2);
      }
      CBORObject cbor = CBORObject.FromObjectAndTag(
          Double.NEGATIVE_INFINITY,
          1956611);
      CBORTestCommon.AssertRoundTrip(cbor);
      cbor = CBORObject.FromObjectAndTag(
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY),
          1956611);
      CBORTestCommon.AssertRoundTrip(cbor);
      cbor = CBORObject.FromObjectAndTag(
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf),
          1956611);
      CBORTestCommon.AssertRoundTrip(cbor);
      cbor = CBORObject.FromObjectAndTag(
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecNegInf),
          1956611);
      CBORTestCommon.AssertRoundTrip(cbor);
      cbor = CBORObject.FromObjectAndTag(
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf),
          1956611);
      CBORTestCommon.AssertRoundTrip(cbor);
    }

    @Test
    public void TestEquivJSON() {
      byte[] jsonBytes = new byte[] {
        0x22, 0x48, 0x54, 0x30, 0x43, 0x5c, 0x75,
        0x64, 0x61, 0x62, 0x43, 0x5c, 0x75, 0x64, 0x64, 0x32, 0x39, 0x48,
        (byte)0xdc,
        (byte)0x9a, 0x4e,
        (byte)0xc2, (byte)0xa3, 0x49, 0x4d, 0x43, 0x40, 0x25, 0x31, 0x3b,
        0x22,
       };
      TestEquivJSONOne(jsonBytes);
      jsonBytes = new byte[] {
        0x22, 0x35, 0x54, 0x30, 0x4d, 0x2d, 0x2b, 0x5c,
        0x75, 0x64, 0x38, 0x36, 0x38, 0x5c, 0x75, 0x44, 0x63, 0x46, 0x32, 0x4f,
        0x34, 0x4e, 0x34,
        (byte)0xe0, (byte)0xa3, (byte)0xac, 0x2b, 0x31, 0x23, 0x22,
       };
      TestEquivJSONOne(jsonBytes);
    }

    @Test
    public void TestDecFracCompareIntegerVsBigFraction() {
      CBORObject o1 = null;
      CBORObject o2 = null;
      o1 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xfb, (byte)0x8b,
        0x44,
        (byte)0xf2, (byte)0xa9, 0x0c, 0x27, 0x42, 0x28,
       });
      o2 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5, (byte)0x82,
        0x38,
        (byte)0xa4, (byte)0xc3, 0x50, 0x02, (byte)0x98, (byte)0xc5, (byte)0xa8,
        0x02, (byte)0xc1, (byte)0xf6, (byte)0xc0, 0x1a, (byte)0xbe, 0x08,
        0x04, (byte)0x86, (byte)0x99, 0x3e, (byte)0xf1,
       });
      AddSubCompare(o1, o2);
    }

    @Test
    public void TestDecimalFrac() {
      CBORObject obj = CBORTestCommon.FromBytesTestAB(
          new byte[] { (byte)0xc4, (byte)0x82, 0x3, 0x1a, 1, 2, 3, 4 });
      try {
        System.out.println("" + obj.ToObject(EDecimal.class));
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestDecimalFracExactlyTwoElements() {
      CBORObject obj = CBORTestCommon.FromBytesTestAB(new byte[] {
        (byte)0xc4, (byte)0x81,
        (byte)0xc2, 0x41,
        1,
       });
      try {
        System.out.println("" + obj.ToObject(EDecimal.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestDecimalFracExponentMustNotBeBignum() {
      CBORObject obj = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4,
        (byte)0x82,
        (byte)0xc2, 0x41, 1,
        0x1a,
        1, 2, 3, 4,
       });
      try {
        System.out.println("" + obj.ToObject(EDecimal.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestBigFloatExponentMustNotBeBignum() {
      CBORObject cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5,
        (byte)0x82,
        (byte)0xc2, 0x41, 1,
        0x1a,
        1, 2, 3, 4,
       });
      try {
        System.out.println("" + cbor.ToObject(EFloat.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestDecimalFracMantissaMayBeBignum() {
      CBORObject o = CBORTestCommon.FromBytesTestAB(
          new byte[] { (byte)0xc4, (byte)0x82, 0x3, (byte)0xc2, 0x41, 1 });
      Assert.assertEquals(
        EDecimal.FromString("1e3"),
        o.ToObject(EDecimal.class));
    }

    @Test
    public void TestBigFloatFracMantissaMayBeBignum() {
      CBORObject o = CBORTestCommon.FromBytesTestAB(
          new byte[] { (byte)0xc5, (byte)0x82, 0x3, (byte)0xc2, 0x41, 1 });
      {
        long numberTemp = EFloat.FromString("8").compareTo(
            (EFloat)o.ToObject(EFloat.class));

        Assert.assertEquals(0, numberTemp);
      }
    }

    @Test
    public void TestDivide() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 =
          ToObjectTest.TestToFromObjectRoundTrip(
            RandomObjects.RandomEInteger(r));

        CBORObject o2 = ToObjectTest.TestToFromObjectRoundTrip(
            RandomObjects.RandomEInteger(r));

        if (o2.AsNumber().IsZero()) {
          continue;
        }
        ERational er = ERational.Create(AsEI(o1), AsEI(o2));
        {
          ERational objectTemp = er;
          ERational objectTemp2;
          CBORNumber cn = CBORObject.FromObject(o1).AsNumber()
            .Divide(CBORObject.FromObject(o2).AsNumber());
          objectTemp2 = cn.ToERational();
          TestCommon.CompareTestEqual(objectTemp, objectTemp2);
        }
      }
    }

    @Test
    public void TestCBORCompareTo() {
      int cmp = CBORObject.FromObject(0).compareTo(null);
      if (cmp <= 0) {
        Assert.fail();
      }
      cmp = CBORObject.FromObject(0).AsNumber().compareTo(null);
      if (cmp <= 0) {
        Assert.fail();
      }
    }

    @Test
    public void TestDouble() {
      if (!ToObjectTest.TestToFromObjectRoundTrip(
          Double.POSITIVE_INFINITY).AsNumber().IsPositiveInfinity()) {
        Assert.fail("Not positive infinity");
      }

      Assert.assertTrue(
        (
          (EDecimal)ToObjectTest.TestToFromObjectRoundTrip(
            Double.POSITIVE_INFINITY)
          .ToObject(EDecimal.class)).IsPositiveInfinity());

      Assert.assertTrue(
        (
          (EDecimal)ToObjectTest.TestToFromObjectRoundTrip(
            Double.NEGATIVE_INFINITY)
          .ToObject(EDecimal.class)).IsNegativeInfinity());
      Assert.assertTrue(
        ((EDecimal)ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)

          .ToObject(EDecimal.class)).IsNaN());
      for (int i = -65539; i <= 65539; ++i) {
        CBORObject o = ToObjectTest.TestToFromObjectRoundTrip((double)i);
        if (!(o.AsNumber().CanFitInDouble())) {
 Assert.fail();
 }
        if (!(o.AsNumber().CanFitInInt32())) {
 Assert.fail();
 }
        if (!(o.AsNumber().IsInteger())) {
 Assert.fail();
 }
        CBORTestCommon.AssertJSONSer(
          o,
          TestCommon.IntToString(i));
      }
    }

    @Test
    public void TestDoubleCompare() {
      CBORObject oldobj = null;
      for (int i = -65539; i <= 65539; ++i) {
        CBORObject o = ToObjectTest.TestToFromObjectRoundTrip((double)i);
        if (oldobj != null) {
          TestCommon.CompareTestLess(oldobj.AsNumber(), o.AsNumber());
        }
        oldobj = o;
      }
    }

    @Test
    public void TestExample() {
      // The following creates a CBOR map and adds
      // several kinds of objects to it
      CBORObject cbor = CBORObject.NewMap().Add("item", "any String")
        .Add("number", 42).Add("map", CBORObject.NewMap().Add("number", 42))
        .Add("array", CBORObject.NewArray().Add(999f).Add("xyz"))
        .Add("bytes", new byte[] { 0, 1, 2 });
      // The following converts the map to CBOR
      CBORTestCommon.CheckEncodeToBytes(cbor);
      // The following converts the map to JSON
      cbor.ToJSONString();
    }

    @Test(timeout = 5000)
    public void TestExtendedExtremeExponent() {
      // Values with extremely high or extremely low exponents;
      // we just check whether this test method runs reasonably fast
      // for all these test cases
      CBORObject obj;
      obj = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4, (byte)0x82,
        0x3a, 0x00, 0x1c, 0x2d, 0x0d, 0x1a, 0x13, 0x6c, (byte)0xa1,
        (byte)0x97,
       });
      CBORTestCommon.AssertRoundTrip(obj);
      obj = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xda, 0x00, 0x14,
        0x57, (byte)0xce, (byte)0xc5, (byte)0x82, 0x1a, 0x46, 0x5a, 0x37,
        (byte)0x87, (byte)0xc3, 0x50, 0x5e, (byte)0xec, (byte)0xfd, 0x73,
        0x50, 0x64, (byte)0xa1, 0x1f, 0x10, (byte)0xc4, (byte)0xff,
        (byte)0xf2, (byte)0xc4, (byte)0xc9, 0x65, 0x12,
       });
      CBORTestCommon.AssertRoundTrip(obj);
    }

    @Test(timeout = 5000)
    public void TestExtendedExtremeExponentCompare() {
      CBORObject cbor1 = ToObjectTest.TestToFromObjectRoundTrip(
          EDecimal.FromString("333333e-2"));
      CBORObject cbor2 = ToObjectTest.TestToFromObjectRoundTrip(
          EFloat.Create(
            EInteger.FromString("5234222"),
            EInteger.FromString("-24936668661488")));
      TestCommon.CompareTestGreater(cbor1.AsNumber(), cbor2.AsNumber());
    }

    @Test
    public void TestFloat() {
      Assert.assertTrue(
        (
          (EDecimal)ToObjectTest.TestToFromObjectRoundTrip(
            Float.POSITIVE_INFINITY)
          .ToObject(EDecimal.class)).IsPositiveInfinity());
      Assert.assertTrue(
        (
          (EDecimal)ToObjectTest.TestToFromObjectRoundTrip(
            Float.NEGATIVE_INFINITY)
          .ToObject(EDecimal.class)).IsNegativeInfinity());
      Assert.assertTrue(
        ((EDecimal)ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)

          .ToObject(EDecimal.class)).IsNaN());
      for (int i = -65539; i <= 65539; ++i) {
        CBORObject o = ToObjectTest.TestToFromObjectRoundTrip((float)i);
        // System.out.print("jsonser i=" + (// i) + " o=" + (o.toString()) + " json=" +
        // (o.ToJSONString()) + " type=" + (o.getType()));
        CBORTestCommon.AssertJSONSer(
          o,
          TestCommon.IntToString(i));
      }
    }

    @Test
    public void TestHalfPrecision() {
      CBORObject o = CBORObject.DecodeFromBytes(
          new byte[] { (byte)0xf9, 0x7c, 0x00 });
      if (o.AsSingle() != Float.POSITIVE_INFINITY) {
        Assert.fail();
      }
      o = CBORObject.DecodeFromBytes(
          new byte[] { (byte)0xf9, 0x00, 0x00 });
      if (o.AsSingle() != 0f) {
        Assert.fail();
      }
      o = CBORObject.DecodeFromBytes(
          new byte[] { (byte)0xf9, (byte)0xfc, 0x00 });
      if (o.AsSingle() != Float.NEGATIVE_INFINITY) {
        Assert.fail();
      }
      o = CBORObject.DecodeFromBytes(
          new byte[] { (byte)0xf9, 0x7e, 0x00 });
      if (!(Float.isNaN(o.AsSingle())))Assert.fail();
    }

    @Test
    public void TestTag268() {
      CBORObject cbor;
      CBORObject cbortag;
      for (int tag = 268; tag <= 269; ++tag) {
        cbor = CBORObject.NewArray().Add(-3).Add(99999).Add(0);
        cbortag = CBORObject.FromObjectAndTag(cbor, tag);
        if (cbortag.AsNumber().IsNegative()) {
 Assert.fail();
 }
        cbor = CBORObject.NewArray().Add(-3).Add(99999);
        cbortag = CBORObject.FromObjectAndTag(cbor, tag);
        try {
          System.out.println("" + cbortag.ToObject(EDecimal.class));
          Assert.fail("Should have failed " + cbortag.toString());
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        cbor = CBORObject.NewArray().Add(-3).Add(99999).Add(1);
        cbortag = CBORObject.FromObjectAndTag(cbor, tag);
        if (!(cbortag.AsNumber().IsNegative())) {
 Assert.fail();
 }
        cbor = CBORObject.NewArray().Add(-3).Add(99999).Add(-1);
        cbortag = CBORObject.FromObjectAndTag(cbor, tag);
        try {
          System.out.println("" + cbortag.ToObject(EDecimal.class));
          Assert.fail("Should have failed " + cbortag.toString());
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        cbor = CBORObject.NewArray().Add(-3).Add(99999).Add(2);
        cbortag = CBORObject.FromObjectAndTag(cbor, tag);
        try {
          System.out.println("" + cbortag.ToObject(EDecimal.class));
          Assert.fail("Should have failed " + cbortag.toString());
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        cbor = CBORObject.NewArray().Add(0).Add(0).Add(2);
        cbortag = CBORObject.FromObjectAndTag(cbor, tag);
        if (cbortag.AsNumber().IsNegative()) {
 Assert.fail();
 }
        cbor = CBORObject.NewArray().Add(0).Add(0).Add(3);
        cbortag = CBORObject.FromObjectAndTag(cbor, tag);
        if (!(cbortag.AsNumber().IsNegative())) {
 Assert.fail();
 }
        cbor = CBORObject.NewArray().Add(-3).Add(99999).Add(8);
        cbortag = CBORObject.FromObjectAndTag(cbor, tag);
        try {
          System.out.println("" + cbortag.ToObject(EDecimal.class));
          Assert.fail("Should have failed " + cbortag.toString());
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
    }

    @Test
    public void TestRoundTripNaN() {
      long doublennan = ((long)0xfff8000000000000L);
      long doublepnan = ((long)0x7ff8000000000000L);
      int singlennan = ((int)0xffc00000);
      int singlepnan = ((int)0x7fc00000);
      int halfnnan = 0xfe00;
      int halfpnan = 0x7e00;
      {
        Object objectTemp = doublennan;
        Object objectTemp2 = CBORObject.FromFloatingPointBits(doublennan,
          8).AsDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = doublepnan;
        Object objectTemp2 = CBORObject.FromFloatingPointBits(doublepnan,
          8).AsDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = doublennan;
        Object objectTemp2 = CBORObject.FromFloatingPointBits(singlennan,
          4).AsDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = doublepnan;
        Object objectTemp2 = CBORObject.FromFloatingPointBits(singlepnan,
          4).AsDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = doublennan;
        Object objectTemp2 = CBORObject.FromFloatingPointBits(halfnnan,
          2).AsDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = doublepnan;
        Object objectTemp2 = CBORObject.FromFloatingPointBits(halfpnan,
          2).AsDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestPlist() {
      CBORObject o;
      o = CBORObject.FromJSONString("[1,2,null,true,false,\"\"]");
      o.Add(new byte[] { 32, 33, 44, 55 });
      o.Add(CBORObject.FromObjectAndTag(9999, 1));
      System.out.println(o.ToJSONString());
      System.out.println(CBORPlistWriter.ToPlistString(o));
    }

    @Test
    public void TestJSON() {
      CBORObject o;
      o = CBORObject.FromJSONString("[1,2,null,true,false,\"\"]");
      Assert.assertEquals(6, o.size());
      Assert.assertEquals(1, o.get(0).AsInt32());
      Assert.assertEquals(2, o.get(1).AsInt32());
      Assert.assertEquals(CBORObject.Null, o.get(2));
      Assert.assertEquals(CBORObject.True, o.get(3));
      Assert.assertEquals(CBORObject.False, o.get(4));
      Assert.assertEquals("", o.get(5).AsString());
      o = CBORObject.FromJSONString("[1.5,2.6,3.7,4.0,222.22]");
      double actual = o.get(0).AsDouble();
      Assert.assertEquals((double)1.5, actual, 0);
      {
        java.io.ByteArrayInputStream ms2a = null;
try {
ms2a = new java.io.ByteArrayInputStream(new byte[] { });

        try {
          CBORObject.ReadJSON(ms2a);
          Assert.fail("Should have failed A");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms2a != null) { ms2a.close(); } } catch (java.io.IOException ex) {}
}
}
      {
        java.io.ByteArrayInputStream ms2b = null;
try {
ms2b = new java.io.ByteArrayInputStream(new byte[] { 0x20 });

        try {
          CBORObject.ReadJSON(ms2b);
          Assert.fail("Should have failed B");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms2b != null) { ms2b.close(); } } catch (java.io.IOException ex) {}
}
}
      try {
        CBORObject.FromJSONString("");
        Assert.fail("Should have failed C");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString("[.1]");
        Assert.fail("Should have failed D");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString("[-.1]");
        Assert.fail("Should have failed E");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString("\u0020");
        Assert.fail("Should have failed F");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      {
        String stringTemp = CBORObject.FromJSONString(" true ").ToJSONString();
        Assert.assertEquals(
          "true",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString(" true ").ToJSONString();
        Assert.assertEquals(
          "true",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString("false").ToJSONString();
        Assert.assertEquals(
          "false",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString("null").ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString("5").ToJSONString();
        Assert.assertEquals(
          "5",
          stringTemp);
      }
    }

    @Test
    public void TestJSONEscapedChars() {
      CBORObject o = CBORObject.FromJSONString(
          "[\"\\r\\n\\u0006\\u000E\\u001A\\\\\\\"\"]");
      Assert.assertEquals(1, o.size());
      {
        String stringTemp = o.get(0).AsString();
        Assert.assertEquals(
          "\r\n\u0006\u000E\u001A\\\"",
          stringTemp);
      }
      {
        String stringTemp = o.ToJSONString();
        Assert.assertEquals(
          "[\"\\r\\n\\u0006\\u000E\\u001A\\\\\\\"\"]",
          stringTemp);
      }
      CBORTestCommon.AssertRoundTrip(o);
    }

    @Test(timeout = 100000)
    public void TestLong() {
      long[] ranges = {
        0, 65539, 0xfffff000L, 0x100000400L,
        Long.MAX_VALUE - 1000,
        Long.MAX_VALUE,
        Long.MIN_VALUE,
        Long.MIN_VALUE + 1000,
      };
      ranges[0] = -65539;
      for (int i = 0; i < ranges.length; i += 2) {
        long j = ranges[i];
        while (true) {
          CBORNumber cn = ToObjectTest.TestToFromObjectRoundTrip(j).AsNumber();
          if (!(cn.IsInteger())) {
 Assert.fail();
 }
          if (!(cn.CanFitInInt64())) {
 Assert.fail();
 }
          if (!(cn.CanTruncatedIntFitInInt64())) {
 Assert.fail();
 }
          CBORTestCommon.AssertJSONSer(
            ToObjectTest.TestToFromObjectRoundTrip(j),
            TestCommon.LongToString(j));
          Assert.assertEquals(
            ToObjectTest.TestToFromObjectRoundTrip(j),
            ToObjectTest.TestToFromObjectRoundTrip(EInteger.FromInt64(j)));
          CBORObject obj = CBORObject.FromJSONString(
              "[" + TestCommon.LongToString(j) + "]");
          CBORTestCommon.AssertJSONSer(
            obj,
            "[" + TestCommon.LongToString(j) + "]");
          if (j == ranges[i + 1]) {
            break;
          }
          ++j;
        }
      }
    }

    @Test
    public void TestMap() {
      CBORObject cbor = CBORObject.FromJSONString("{\"a\":2,\"b\":4}");
      Assert.assertEquals(2, cbor.size());
      TestCommon.AssertEqualsHashCode(
        ToObjectTest.TestToFromObjectRoundTrip(2),
        cbor.get(ToObjectTest.TestToFromObjectRoundTrip("a")));
      TestCommon.AssertEqualsHashCode(
        ToObjectTest.TestToFromObjectRoundTrip(4),
        cbor.get(ToObjectTest.TestToFromObjectRoundTrip("b")));
      {
        long numberTemp = cbor.get(ToObjectTest.TestToFromObjectRoundTrip(
              "a")).AsInt32();
        Assert.assertEquals(2, numberTemp);
      }
      {
        long numberTemp = cbor.get(ToObjectTest.TestToFromObjectRoundTrip(
              "b")).AsInt32();
        Assert.assertEquals(4, numberTemp);
      }
      Assert.assertEquals(0, CBORObject.True.size());
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xbf, 0x61, 0x61, 2,
        0x61, 0x62, 4, (byte)0xff,
       });
      Assert.assertEquals(2, cbor.size());
      TestCommon.AssertEqualsHashCode(
        ToObjectTest.TestToFromObjectRoundTrip(2),
        cbor.get(ToObjectTest.TestToFromObjectRoundTrip("a")));
      TestCommon.AssertEqualsHashCode(
        ToObjectTest.TestToFromObjectRoundTrip(4),
        cbor.get(ToObjectTest.TestToFromObjectRoundTrip("b")));
      {
        long numberTemp = cbor.get(ToObjectTest.TestToFromObjectRoundTrip(
              "a")).AsInt32();
        Assert.assertEquals(2, numberTemp);
      }
      {
        long numberTemp = cbor.get(ToObjectTest.TestToFromObjectRoundTrip(
              "b")).AsInt32();
        Assert.assertEquals(4, numberTemp);
      }
    }

    @Test
    public void TestMapInMap() {
      CBORObject oo;
      oo = CBORObject.NewArray().Add(CBORObject.NewMap()
          .Add(
            ERational.Create(EInteger.FromInt32(1), EInteger.FromString("2")),
            3).Add(4, false)).Add(true);
      CBORTestCommon.AssertRoundTrip(oo);
      oo = CBORObject.NewArray();
      oo.Add(ToObjectTest.TestToFromObjectRoundTrip(0));
      CBORObject oo2 = CBORObject.NewMap();
      oo2.Add(
        ToObjectTest.TestToFromObjectRoundTrip(1),
        ToObjectTest.TestToFromObjectRoundTrip(1368));
      CBORObject oo3 = CBORObject.NewMap();
      oo3.Add(
        ToObjectTest.TestToFromObjectRoundTrip(2),
        ToObjectTest.TestToFromObjectRoundTrip(1625));
      CBORObject oo4 = CBORObject.NewMap();
      oo4.Add(oo2, CBORObject.True);
      oo4.Add(oo3, CBORObject.True);
      oo.Add(oo4);
      CBORTestCommon.AssertRoundTrip(oo);
    }

    public static void TestParseDecimalStringsOne(String r) {
      CBORObject o = ToObjectTest.TestToFromObjectRoundTrip(
          EDecimal.FromString(r));
      CBORObject o2 = CBORDataUtilities.ParseJSONNumber(r);
      TestCommon.CompareTestEqual(o.AsNumber(), o2.AsNumber());
    }

    @Test
    public void TestJSONWithComments() {
      Map<String, String> dict;
      String str = "[\n {\n # Bm\n\"a\":1,\n\"b\":2\n},{\n #" +
        "\u0020Sm\n\"a\":3,\n\"b\":4\n}\n]";
      CBORObject obj = JSONWithComments.FromJSONString(str);
      System.out.println(obj);
      str = "[\n {\n # B\n # Dm\n\"a\":1,\n\"b\":2\n},{\n #" +
        "\u0020Sm\n\"a\":3,\n\"b\":4\n}\n]";
      obj = JSONWithComments.FromJSONString(str);
      System.out.println(obj);
      str = "[\n {\n # B A C\n # Dm\n\"a\":1,\n\"b\":2\n},{\n #" +
        "\u0020Sm\n\"a\":3,\n\"b\":4\n}\n]";
      obj = JSONWithComments.FromJSONString(str);
      System.out.println(obj);
      str = "[\n {\n # B\t \tA C\n # Dm\n\"a\":1,\n\"b\":2\n},{\n #" +
        "\u0020Sm\n\"a\":3,\n\"b\":4\n}\n]";
      obj = JSONWithComments.FromJSONString(str);
      System.out.println(obj);
      dict = new HashMap<String, String>();
      str = "{\"f\":[\n {\n # B\t \tA C\n # Dm\n\"a\":1,\n\"b\":2\n},{\n #" +
        "\u0020Sm\n\"a\":3,\n\"b\":4\n}\n]}";
      obj = JSONWithComments.FromJSONString(str);
      System.out.println(obj);
      obj = JSONWithComments.FromJSONStringWithPointers(str, dict);
      for (String key : dict.keySet()) {
        System.out.println(key);
        System.out.println(dict.get(key));
        System.out.println(obj.AtJSONPointer(dict.get(key)));
      }
      System.out.println(obj);
    }

    @Test
    public void TestParseDecimalStrings() {
      RandomGenerator rand = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        TestParseDecimalStringsOne(RandomObjects.RandomDecimalString(rand));
      }
    }

    @Test(timeout = 200000)
    public void TestRandomData() {
      RandomGenerator rand = new RandomGenerator();
      CBORObject obj;
      for (int i = 0; i < 1000; ++i) {
        obj = CBORTestCommon.RandomCBORObject(rand);
        CBORTestCommon.AssertRoundTrip(obj);
        String jsonString = "";
        try {
          jsonString = obj.ToJSONString();
        } catch (IllegalStateException ex) {
          System.out.println(TestCommon.ToByteArrayString(obj.EncodeToBytes()));
          throw new IllegalStateException(ex.getMessage(), ex);
        } catch (CBORException ex) {
          jsonString = "";
        }
        if (jsonString.length() > 0) {
          CBORObject.FromJSONString(jsonString);
          TestWriteToJSON(obj);
        }
      }
    }

    public static CBORObject ReferenceTestObject() {
      return ReferenceTestObject(50);
    }

    public static CBORObject ReferenceTestObject(int nests) {
      CBORObject root = CBORObject.NewArray();
      CBORObject arr = CBORObject.NewArray().Add("xxx").Add("yyy");
      arr.Add("zzz")
      .Add("wwww").Add("iiiiiii").Add("aaa").Add("bbb").Add("ccc");
      arr = CBORObject.FromObjectAndTag(arr, 28);
      root.Add(arr);
      CBORObject refobj;
      for (int i = 0; i <= nests; ++i) {
        refobj = CBORObject.FromObjectAndTag(i, 29);
        arr = CBORObject.FromObject(new CBORObject[] {
          refobj, refobj, refobj, refobj, refobj, refobj, refobj, refobj,
          refobj,
        });
        arr = CBORObject.FromObjectAndTag(arr, 28);
        root.Add(arr);
      }
      return root;
    }

    @Test(timeout = 5000)
    public void TestCtap2CanonicalReferenceTest() {
      for (int i = 4; i <= 60; ++i) {
        // has high recursive reference depths, higher than
        // Ctap2Canonical supports, which is 4
        TestCtap2CanonicalReferenceTestOne(ReferenceTestObject(i));
      }
    }

    public static void TestCtap2CanonicalReferenceTestOne(CBORObject root) {
      if (root == null) {
        throw new NullPointerException("root");
      }
      byte[] bytes = root.EncodeToBytes();
      // NOTE: root has a nesting depth of more than four, so
      // encoding it should fail with Ctap2Canonical
      CBORObject origroot = root;
      CBOREncodeOptions encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      encodeOptions = new CBOREncodeOptions("ctap2canonical=true");
      if (root == null) {
        Assert.fail();
      }
      try {
        {
          java.io.ByteArrayOutputStream lms = null;
try {
lms = new java.io.ByteArrayOutputStream();

          root.WriteTo(lms, encodeOptions);
          Assert.fail("Should have failed");
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test(timeout = 50000)
    public void TestNoRecursiveExpansion() {
      for (int i = 5; i <= 60; ++i) {
        // has high recursive reference depths
        // System.Diagnostics.Stopwatch sw = new System.Diagnostics.Stopwatch();sw.Start();
        // System.out.println("depth = "+i);
        TestNoRecursiveExpansionOne(ReferenceTestObject(i));
        // System.out.println("elapsed=" + sw.getElapsedMilliseconds() + " ms");
      }
    }

    @Test(timeout = 50000)
    public void TestNoRecursiveExpansionJSON() {
      for (int i = 5; i <= 60; ++i) {
        // has high recursive reference depths
        // System.Diagnostics.Stopwatch sw = new System.Diagnostics.Stopwatch();sw.Start();
        // System.out.println("depth = "+i);
        TestNoRecursiveExpansionJSONOne(ReferenceTestObject(i));
        // System.out.println("elapsed=" + sw.getElapsedMilliseconds() + " ms");
      }
    }

    public static void TestNoRecursiveExpansionOne(CBORObject root) {
      if (root == null) {
        throw new NullPointerException("root");
      }
      CBORObject origroot = root;
      byte[] bytes = CBORTestCommon.CheckEncodeToBytes(root);
      CBOREncodeOptions encodeOptions = new CBOREncodeOptions("resolvereferences=false");
      root = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      if (root == null) {
        Assert.fail();
      }
      // Test a mitigation for wild recursive-reference expansions
      encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      if (root == null) {
        Assert.fail();
      }
      try {
        {
          LimitedMemoryStream lms = null;
try {
lms = new LimitedMemoryStream(100000);

          root.WriteTo(lms);
          Assert.fail("Should have failed");
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (UnsupportedOperationException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        {
          LimitedMemoryStream lms = null;
try {
lms = new LimitedMemoryStream(100000);

          origroot.WriteTo(lms);
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    public static void TestNoRecursiveExpansionJSONOne(CBORObject root) {
      if (root == null) {
        throw new NullPointerException("root");
      }
      CBORObject origroot = root;
      byte[] bytes = CBORTestCommon.CheckEncodeToBytes(root);
      CBOREncodeOptions encodeOptions = new CBOREncodeOptions("resolvereferences=false");
      root = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      if (root == null) {
        Assert.fail();
      }
      // Test a mitigation for wild recursive-reference expansions
      encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      if (root == null) {
        Assert.fail();
      }
      try {
        {
          LimitedMemoryStream lms = null;
try {
lms = new LimitedMemoryStream(100000);

          root.WriteJSONTo(lms);
          Assert.fail("Should have failed");
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (UnsupportedOperationException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        {
          LimitedMemoryStream lms = null;
try {
lms = new LimitedMemoryStream(100000);

          origroot.WriteJSONTo(lms);
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestSharedRefValidInteger() {
      byte[] bytes;
      CBOREncodeOptions encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      // Shared ref is integer
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, 0x00,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, encodeOptions);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Shared ref is negative
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, 0x20,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, encodeOptions);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Shared ref is non-integer
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, (byte)0xc4, (byte)0x82,
        0x27, 0x19, (byte)0xff, (byte)0xff,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, encodeOptions);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Shared ref is non-number
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, 0x61, 0x41,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, encodeOptions);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Shared ref is out of range
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, 0x01,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, encodeOptions);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static String ToByteArrayStringFrom(byte[] array, int pos) {
      byte[] newArray = new byte[array.length - pos];
      System.arraycopy(array, pos, newArray, 0, newArray.length);
      return TestCommon.ToByteArrayString(newArray);
    }

    @Test(timeout = 500000)
    public void TestRandomNonsense() {
      RandomGenerator rand = new RandomGenerator();
      for (int i = 0; i < 1000; ++i) {
        byte[] array = new byte[rand.UniformInt(100000) + 1];
        rand.GetBytes(array, 0, array.length);
        TestRandomOne(array);
      }
    }

    public static byte[] SlightlyModify(byte[] array,
      IRandomGenExtended rand) {
      if (array == null) {
        throw new NullPointerException("array");
      }
      if (array.length > 50000) {
        System.out.println("" + array.length);
      }
      if (rand == null) {
        throw new NullPointerException("rand");
      }
      int count2 = rand.GetInt32(10) + 1;
      for (int j = 0; j < count2; ++j) {
        int index = rand.GetInt32(array.length);
        array[index] = ((byte)rand.GetInt32(256));
      }
      return array;
    }

    public static void TestRandomOne(byte[] array) {
      {
        java.io.ByteArrayInputStream inputStream = null;
try {
inputStream = new java.io.ByteArrayInputStream(array);
int startingAvailable = inputStream.available();

        while ((startingAvailable - inputStream.available()) != startingAvailable) {
          long oldPos = 0L;
          try {
            CBORObject o;
            oldPos = (startingAvailable - inputStream.available());
            o = CBORObject.Read(inputStream);
            long cborlen = (startingAvailable - inputStream.available()) - oldPos;
            // if (cborlen > 3000) {
            // System.out.println("pos=" + (startingAvailable - inputStream.available()) + " of " +
            // startingAvailable + ", cborlen=" + cborlen);
            // }
            byte[] encodedBytes = (o == null) ? null : o.EncodeToBytes();
            try {
              CBORObject.DecodeFromBytes(encodedBytes);
            } catch (Exception ex) {
              throw new IllegalStateException(ex.getMessage(), ex);
            }
            String jsonString = "";
            try {
              if (o == null) {
                Assert.fail("Object is null");
              }
              if (o != null) {
                try {
                  jsonString = o.ToJSONString();
                } catch (CBORException ex) {
                  System.out.println(ex.getMessage());
                  jsonString = "";
                }
                if (jsonString.length() > 0) {
                  CBORObject.FromJSONString(jsonString);
                  TestWriteToJSON(o);
                }
              }
            } catch (Exception ex) {
              throw new IllegalStateException(ex.getMessage(), ex);
            }
          } catch (CBORException ex) {
            // Expected exception
            System.out.print(ex.getMessage().substring(0, 0));
          } catch (IllegalStateException ex) {
            String failString = ex.toString() +
              (ex.getCause() == null ? "" : "\n" +
                ex.getCause().toString());
            failString += "\nlength: " + array.length + " bytes";
            failString += "\nstart pos: " + oldPos + ", truelen=" +
              ((startingAvailable - inputStream.available()) - oldPos);
            failString += "\n" + TestCommon.ToByteArrayString(array);
            failString = failString.substring(
                0, (
                0)+(Math.min(2000, failString.length())));
            throw new IllegalStateException(failString, ex);
          }
        }
}
finally {
try { if (inputStream != null) { inputStream.close(); } } catch (java.io.IOException ex) {}
}
}
    }

    @Test
    public void TestRandomSlightlyModified() {
      RandomGenerator rand = new RandomGenerator();
      // Test slightly modified objects
      for (int i = 0; i < 2000; ++i) {
        CBORObject originalObject = CBORTestCommon.RandomCBORObject(rand);
        byte[] array = originalObject.EncodeToBytes();
        System.out.println("i=" + i + " obj=" + array.length);
        TestRandomOne(SlightlyModify(array, rand));
      }
    }

    private static void TestReadWriteIntOne(int val) {
      try {
        {
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            MiniCBOR.WriteInt32(val, ms);
            byte[] msarray = ms.toByteArray();
            {
              java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(msarray);

              Assert.assertEquals(TestCommon.ToByteArrayString(msarray), val, MiniCBOR.ReadInt32(ms2));
}
finally {
try { if (ms2 != null) { ms2.close(); } } catch (java.io.IOException ex) {}
}
}
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        }
        {
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(val, ms);
            byte[] msarray = ms.toByteArray();
            {
              java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(msarray);

              Assert.assertEquals(TestCommon.ToByteArrayString(msarray), val, MiniCBOR.ReadInt32(ms2));
}
finally {
try { if (ms2 != null) { ms2.close(); } } catch (java.io.IOException ex) {}
}
}
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        }
      } catch (IOException ioex) {
        Assert.fail(ioex.getMessage() + " val=" + val);
      }
    }

    public static EInteger UnsignedLongToEInteger(long v) {
      if (v >= 0) {
        return EInteger.FromInt64(v);
      } else {
        return EInteger.FromInt32(1).ShiftLeft(64).Add(v);
      }
    }

    public static void TestUnsignedLongOne(long v, String expectedStr) {
      EInteger ei = UnsignedLongToEInteger(v);

      Assert.assertEquals(
        expectedStr,
        com.upokecenter.util.DataUtilities.ToLowerCaseAscii(ei.ToRadixString(16)));
      CBORObject cbor = CBORObject.FromObject(ei);
      if (!(cbor.AsNumber().signum() >= 0)) {
 Assert.fail();
 }
      TestCommon.AssertEqualsHashCode(
        ei,
        cbor.ToObject(EInteger.class));
    }

    @Test
    public void TestUnsignedLong() {
      TestUnsignedLongOne(0x0L, "0");
      TestUnsignedLongOne(0xFL, "f");
      TestUnsignedLongOne(0xFFFFFFFFL, "ffffffff");
      TestUnsignedLongOne(-1, "ffffffffffffffff");
      TestUnsignedLongOne(-3, "fffffffffffffffd");
      TestUnsignedLongOne(Long.MAX_VALUE, "7fffffffffffffff");
      TestUnsignedLongOne(Long.MAX_VALUE - 1, "7ffffffffffffffe");
      TestUnsignedLongOne(Long.MIN_VALUE, "8000000000000000");
      TestUnsignedLongOne(Long.MIN_VALUE + 1, "8000000000000001");
    }

    @Test
    public void TestReadWriteInt() {
      RandomGenerator r = new RandomGenerator();
      for (int i = -70000; i < 70000; ++i) {
        TestReadWriteIntOne(i);
      }
      for (int i = 0; i < 100000; ++i) {
        int val = ((int)RandomObjects.RandomInt64(r));
        TestReadWriteIntOne(val);
      }
    }

    @Test
    public void TestShort() {
      for (int i = Short.MIN_VALUE; i <= Short.MAX_VALUE; ++i) {
        CBORTestCommon.AssertJSONSer(
          ToObjectTest.TestToFromObjectRoundTrip((short)i),
          TestCommon.IntToString(i));
      }
    }

    @Test
    public void TestSimpleValues() {
      CBORTestCommon.AssertJSONSer(
        ToObjectTest.TestToFromObjectRoundTrip(true),
        "true");
      CBORTestCommon.AssertJSONSer(
        ToObjectTest.TestToFromObjectRoundTrip(false),
        "false");
      CBORTestCommon.AssertJSONSer(
        ToObjectTest.TestToFromObjectRoundTrip((Object)null),
        "null");
    }

    @Test
    public void TestCtap2NestingLevel() {
      CBORObject o;
      CBOREncodeOptions ctap = new CBOREncodeOptions("ctap2canonical=true");
      // 1 nesting level
      o = CBORObject.FromJSONString("[]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 1 nesting level
      o = CBORObject.FromJSONString("[0]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 3 nesting levels
      o = CBORObject.FromJSONString("[[[]]]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 4 nesting levels
      o = CBORObject.FromJSONString("[[[[]]]]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 5 nesting levels
      o = CBORObject.FromJSONString("[[[[[]]]]]");
      try {
        o.EncodeToBytes(ctap);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // 4 nesting levels
      o = CBORObject.FromJSONString("[[[[0]]]]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 1 nesting level
      o = CBORObject.FromJSONString("[]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 3 nesting levels
      o = CBORObject.FromJSONString("[[[]]]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 4 nesting levels
      o = CBORObject.FromJSONString("[[{\"x\": []}]]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 5 nesting levels
      o = CBORObject.FromJSONString("[[[{\"x\": []}]]]");
      try {
        o.EncodeToBytes(ctap);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // 4 nesting levels
      o = CBORObject.FromJSONString("[[[{\"x\": 0}]]]");
      if (o.EncodeToBytes(ctap) == null) {
        Assert.fail();
      }
      // 5 nesting levels
      o = CBORObject.FromJSONString("[[[[{\"x\": 0}]]]]");
      try {
        o.EncodeToBytes(ctap);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static String Chop(String str) {
      return (str.length() < 100) ? str : (str.substring(0,100) + "...");
    }

    private static void VerifyEqual(
      CBORNumber expected,
      CBORNumber actual,
      CBORObject o1,
      CBORObject o2) {
      if (expected.compareTo(actual) != 0) {
        String msg = "o1=" + Chop(o1.toString()) + ", o2=" +
          Chop(o2.toString());
        TestCommon.CompareTestEqual(expected, actual, msg);
      }
    }

    @Test
    public void TestRational1() {
      byte[] eb1 = new byte[] {
        (byte)0xd8, 0x1e,
        (byte)0x82,
        (byte)0xc2, 0x58, 0x22, 0x24,
        (byte)0xba, 0x21,
        (byte)0xf3,
        (byte)0xa9,
        (byte)0xfb, 0x1c,
        (byte)0xde,
        (byte)0xc5, 0x49,
        (byte)0xd2, 0x2c,
        (byte)0x94, 0x27,
        (byte)0xb1, 0x0d, 0x36,
        (byte)0xea, 0x1c,
        (byte)0xcb, 0x5d,
        (byte)0xe9, 0x13,
        (byte)0xef,
        (byte)0xf2, 0x2c,
        (byte)0xbf,
        (byte)0xc8,
        (byte)0xad, 0x42,
        (byte)0x8a,
        (byte)0xae, 0x65,
        (byte)0x85,
        (byte)0xc2, 0x58, 0x19, 0x74,
        (byte)0xf5, 0x20, 0x74, 0x43,
        (byte)0xd4,
        (byte)0xdf,
        (byte)0x93, 0x12,
        (byte)0xc3,
        (byte)0x89,
        (byte)0xdd, 0x53, 0x62,
        (byte)0xdf, 0x5c, 0x66, 0x2f, 0x4d,
        (byte)0xbd, 0x7e, 0x57, (byte)0xdd, (byte)0x91, 0x6c,
       };
      System.out.println("" +
        CBORObject.DecodeFromBytes(eb1).ToObject(ERational.class));
      TestRandomOne(eb1);
    }

    @Test
    public void TestRational2() {
      byte[] eb1 = new byte[] {
        (byte)0xd8, 0x1e,
        (byte)0x82,
        (byte)0xc2, 0x58, 0x18, 0x2d,
        (byte)0x8e, 0x6b, 0x70, 0x4e,
        (byte)0xf2,
        (byte)0xc9, 0x15,
        (byte)0xe3, 0x34, 0x5f, 0x7c,
        (byte)0xbb, 0x07, 0x22,
        (byte)0xd3, 0x40, 0x37, 0x52,
        (byte)0xbd, 0x75, 0x3a, 0x4b,
        (byte)0xe0,
        (byte)0xc2, 0x51, 0x28, 0x42,
        (byte)0x81,
        (byte)0x93, 0x22, 0x6e,
        (byte)0x94, 0x4d,
        (byte)0xff, (byte)0xdb, 0x45, (byte)0x97, 0x0c, 0x56, 0x04, (byte)0xe3,
        0x21,
       };
      System.out.println("" +
        CBORObject.DecodeFromBytes(eb1).ToObject(ERational.class));
      TestRandomOne(eb1);
    }

    @Test
    public void TestRational3() {
      byte[] eb1 = new byte[] {
        (byte)0xd8, 0x1e,
        (byte)0x82, 0x1b, 0x00, 0x00, 0x26,
        (byte)0xbd, 0x75, 0x51,
        (byte)0x9a, 0x7b,
        (byte)0xc2, 0x57, 0x0c,
        (byte)0xb4, 0x04,
        (byte)0xe3, 0x21,
        (byte)0xf0,
        (byte)0xb6, 0x2d,
        (byte)0xd3, 0x6b,
        (byte)0xd8, 0x4e,
        (byte)0xf2,
        (byte)0xc9, 0x15, (byte)0xe3, 0x34, (byte)0xa2, 0x16, 0x07, 0x07, 0x0d,
        (byte)0xd3,
       };
      System.out.println("" +
        CBORObject.DecodeFromBytes(eb1).ToObject(ERational.class));
      TestRandomOne(eb1);
    }

    @Test(timeout = 60000)
    public void TestAsNumberAddSubtractSpecific() {
      byte[] eb1 = new byte[] {
        (byte)0xd9, 0x01, 0x08,
        (byte)0x82,
        (byte)0xc3, 0x57, 0x0f,
        (byte)0xf2,
        (byte)0xa2,
        (byte)0x97, 0x0b,
        (byte)0xee,
        (byte)0xa8,
        (byte)0x9c,
        (byte)0xa1, 0x3f, 0x7b, 0x22, 0x5f,
        (byte)0x82, 0x4f,
        (byte)0xfa, 0x3d,
        (byte)0xaa,
        (byte)0xfc, 0x27, 0x64,
        (byte)0xf0, 0x2f,
        (byte)0xc2, 0x58, 0x19, 0x16, 0x01,
        (byte)0xe6, 0x6a, 0x7f,
        (byte)0xe4,
        (byte)0x90,
        (byte)0x9e, 0x28, 0x33, 0x1d,
        (byte)0x87,
        (byte)0xcd, 0x1e, 0x37,
        (byte)0xdb, 0x5d,
        (byte)0xd1, (byte)0xc2, (byte)0xc9, 0x40, (byte)0xa6, 0x1b,
        (byte)0xb5, (byte)0x87,
       };
      byte[] eb2 = new byte[] {
        (byte)0xc5,
        (byte)0x82, 0x18,
        (byte)0xbe,
        (byte)0xc2, 0x58, 0x26, 0x06, 0x5d, 0x42,
        (byte)0xc3,
        (byte)0x88,
        (byte)0xbe,
        (byte)0x86,
        (byte)0xbe, 0x15,
        (byte)0x9f,
        (byte)0x99,
        (byte)0x81,
        (byte)0x96,
        (byte)0xa6,
        (byte)0xac, 0x4b, 0x37,
        (byte)0xb4, 0x43,
        (byte)0xf8, 0x17, 0x6d, 0x7e, 0x10, 0x38,
        (byte)0xda, 0x65,
        (byte)0x90,
        (byte)0xa9,
        (byte)0x80, (byte)0xef, (byte)0xa3, 0x65, (byte)0xca, 0x7d, 0x4f,
        (byte)0xa8, 0x27,
       };
      CBORObject cbor1 = CBORObject.DecodeFromBytes(eb1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes(eb2);
      EDecimal ed1 = AsED(cbor1);
      EDecimal ed2 = AsED(cbor2);
      // Should return NaN due to memory issues
      ed1 = ed1.Add(ed2);
      if (!(ed1.IsNaN())) {
 Assert.fail();
 }
    }

    @Test(timeout = 60000)
    public void TestAsNumberAddSubtractSpecific2() {
      byte[] eb1 = new byte[] {
        (byte)0xc4,
        (byte)0x82, 0x1b, 0x00, 0x00, 0x00, 0x6e, 0x1c, 0x51, 0x6c, 0x6e,
        (byte)0xc3, 0x4f, 0x7c, 0x0f, 0x6e, 0x1d,
        (byte)0x89, 0x26,
        (byte)0x8d, 0x57, (byte)0xec, 0x00, 0x54, (byte)0xb9, 0x51,
        (byte)0xae, 0x43,
       };
      byte[] eb2 = new byte[] { (byte)0xfa, 0x75, 0x00, 0x57, (byte)0xbe };
      CBORObject cbor1 = CBORObject.DecodeFromBytes(eb1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes(eb2);
      EDecimal ed1 = AsED(cbor1);
      EDecimal ed2 = AsED(cbor2);
      // Should return NaN due to memory issues
      ed1 = ed1.Add(ed2);
      if (!(ed1.IsNaN())) {
 Assert.fail();
 }
    }

    @Test(timeout = 100000)
    public void TestAsNumberAddSubtract() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        // NOTE: Avoid generating high-exponent numbers for this test
        CBORObject o1 = CBORTestCommon.RandomNumber(r, true);
        CBORObject o2 = CBORTestCommon.RandomNumber(r, true);
        byte[] eb1 = o1.EncodeToBytes();
        byte[] eb2 = o2.EncodeToBytes();
        CBORTestCommon.AssertRoundTrip(o1);
        CBORTestCommon.AssertRoundTrip(o2);
        CBORNumber on1 = o1.AsNumber();
        CBORNumber on2 = o2.AsNumber();
        // System.out.println(i+"");
        // System.out.println(i+" "+Chop(o1.toString()));
        // System.out.println(i+" "+Chop(o2.toString()));
        // System.out.println(i+" "+TestCommon.ToByteArrayString(eb1));
        // System.out.println(i+" "+TestCommon.ToByteArrayString(eb2));
        CBORNumber onSum = null;
        try {
          onSum = on1.Add(on2);
        } catch (OutOfMemoryError ex) {
          continue;
        }
        if (!onSum.IsFinite()) {
          // System.out.println("on1=" + o1);
          // System.out.println("on2=" + o2);
          continue;
        }
        if (!onSum.IsFinite()) {
          Assert.fail(o1.toString());
        }
        CBORNumber on2a = onSum.Subtract(on1);
        if (!on2a.IsFinite()) {
          Assert.fail(o1.toString());
        }
        VerifyEqual(on2a, on2, o1, o2);
        CBORNumber on1a = onSum.Subtract(on2);
        if (!on1a.IsFinite()) {
          Assert.fail(o1.toString());
        }
        VerifyEqual(on1a, on1, o1, o2);
      }
    }

    public static boolean TestAsNumberMultiplyDivideOne(
      CBORObject o1,
      CBORObject o2) {
      if (o1 == null) {
        throw new NullPointerException("o1");
      }
      if (o2 == null) {
        throw new NullPointerException("o2");
      }
      if (!o1.isNumber() || !o2.isNumber()) {
        return false;
      }
      byte[] eb1 = o1.EncodeToBytes();
      byte[] eb2 = o2.EncodeToBytes();
      CBORTestCommon.AssertRoundTrip(o1);
      CBORTestCommon.AssertRoundTrip(o2);
      CBORNumber on1 = o1.AsNumber();
      CBORNumber on2 = o2.AsNumber();
      CBORNumber onSum = null;
      try {
        onSum = on1.Multiply(on2);
      } catch (OutOfMemoryError ex) {
        return false;
      }
      if (!onSum.IsFinite()) {
        // System.out.println("on1=" + o1);
        // System.out.println("on2=" + o2);
        return false;
      }
      // System.out.println(i+"");
      // System.out.println(i+" "+Chop(o1.toString()));
      // System.out.println(i+" "+Chop(o2.toString()));
      // System.out.println(i + " " + Chop(onSum.toString()));
      if (!onSum.IsFinite()) {
        Assert.fail("onSum is not finite\n" +
          "o1=" + TestCommon.ToByteArrayString(eb1) + "\n" +
          "o2=" + TestCommon.ToByteArrayString(eb2) + "\n");
      }
      CBORNumber on2a = onSum.Divide(on1);
      // NOTE: Ignore if divisor is zero
      if (!on1.IsZero() && !on2a.IsFinite()) {
        Assert.fail("on2a is not finite\n" +
          "o1=" + TestCommon.ToByteArrayString(eb1) + "\n" +
          "o2=" + TestCommon.ToByteArrayString(eb2) + "\n");
      }
      if (!on1.IsZero() && !on2.IsZero()) {
        VerifyEqual(on2a, on2, o1, o2);
      }
      CBORNumber on1a = onSum.Divide(on2);
      // NOTE: Ignore if divisor is zero
      if (!on2.IsZero() && !on1a.IsFinite()) {
        Assert.fail("on1a is not finite\n" +
          "o1=" + on1 + "\n" + "o2=" + on2 + "\n" +
          "{\nbyte[] by" +
          "tes1 = " + TestCommon.ToByteArrayString(eb1) + ";\n" +
          "byte[] by" + "tes2 =" + TestCommon.ToByteArrayString(eb2) + ";\n" +
          "TestAsNumberMultiplyDivideOne(\nCBORObject.D" +
          "ecodeFromBytes(bytes1),\n" +
          "CBORObject.DecodeFromBytes(bytes2));\n}\n");
      }
      if (!on1.IsZero() && !on2.IsZero()) {
        VerifyEqual(on1a, on1, o1, o2);
      }
      return true;
    }

    @Test(timeout = 100000)
    public void TestAsNumberMultiplyDivide() {
      byte[] bo1 = new byte[] {
        0x1b, 0x75, (byte)0xdd, (byte)0xb0,
        (byte)0xcc, 0x50, (byte)0x9b, (byte)0xd0, 0x2b,
       };
      byte[] bo2 = new byte[] { (byte)0xc5, (byte)0x82, 0x23, 0x00 };
      CBORObject cbor1 = CBORObject.DecodeFromBytes(bo1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes(bo2);
      TestAsNumberMultiplyDivideOne(cbor1, cbor2);
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 = CBORTestCommon.RandomNumber(r);
        CBORObject o2 = CBORTestCommon.RandomNumber(r);
        TestAsNumberMultiplyDivideOne(o1, o2);
      }
    }

    @Test
    public void TestOrderedMap() {
      CBORObject cbor;
      List<CBORObject> list;
      cbor = CBORObject.NewOrderedMap().Add("a", 1).Add("b", 2).Add("c", 3);
      list = new ArrayList<CBORObject>();
      for (CBORObject obj : cbor.getKeys()) {
        list.add(obj);
      }
      Assert.assertEquals(3, list.size());
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject("a"), list.get(0));
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject("b"), list.get(1));
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject("c"), list.get(2));
      cbor = CBORObject.NewOrderedMap().Add("c", 1).Add("a", 2).Add("vv", 3);
      list = new ArrayList<CBORObject>();
      for (CBORObject obj : cbor.getKeys()) {
        list.add(obj);
      }
      Assert.assertEquals(3, list.size());
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject("c"), list.get(0));
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject("a"), list.get(1));
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject("vv"), list.get(2));
      list = new ArrayList<CBORObject>();
      for (CBORObject obj : cbor.getValues()) {
        list.add(obj);
      }
      Assert.assertEquals(3, list.size());
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject(1), list.get(0));
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject(2), list.get(1));
      TestCommon.AssertEqualsHashCode(CBORObject.FromObject(3), list.get(2));
    }

    @Test(timeout = 10000)
    public void TestTaggedUntagged() {
      for (int i = 200; i < 1000; ++i) {
        CBORObject o, o2;
        o = ToObjectTest.TestToFromObjectRoundTrip(0);
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o =
          ToObjectTest.TestToFromObjectRoundTrip(EInteger.FromString(
              "999999999999999999999999999999999"));
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = ToObjectTest.TestToFromObjectRoundTrip(new byte[] { 1, 2, 3 });
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.NewArray();
        o.Add(ToObjectTest.TestToFromObjectRoundTrip(0));
        o.Add(ToObjectTest.TestToFromObjectRoundTrip(1));
        o.Add(ToObjectTest.TestToFromObjectRoundTrip(2));
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.NewMap();
        o.Add("a", ToObjectTest.TestToFromObjectRoundTrip(0));
        o.Add("b", ToObjectTest.TestToFromObjectRoundTrip(1));
        o.Add("c", ToObjectTest.TestToFromObjectRoundTrip(2));
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = ToObjectTest.TestToFromObjectRoundTrip("a");
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.False;
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.True;
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.Null;
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.Undefined;
        o2 = CBORObject.FromObjectAndTag(o, i);
        TestCommon.AssertEqualsHashCode(o, o2);
        o = CBORObject.FromObjectAndTag(o, i + 1);
        TestCommon.AssertEqualsHashCode(o, o2);
      }
    }

    private static void AssertEquals(int exp, int act) {
      // Much less overhead than Assert alone if the
      // two arguments are equal
      if (exp != act) {
        Assert.assertEquals(exp, act);
      }
    }

    private static void AssertEquals(Object oexp, Object oact) {
      // Much less overhead than Assert alone if the
      // two arguments are equal
      if (oexp == null ? oact != null : !oexp.equals(oact)) {
        Assert.assertEquals(oexp, oact);
      }
    }

    private static void AssertEquals(Object oexp, Object oact, String str) {
      // Much less overhead than Assert alone if the
      // two arguments are equal
      if (oexp == null ? oact != null : !oexp.equals(oact)) {
        Assert.assertEquals(str, oexp, oact);
      }
    }

    @Test(timeout = 15000)
    public void TestTags() {
      EInteger maxuint = EInteger.FromString("18446744073709551615");
      EInteger[] ranges = {
        EInteger.FromString("37"),
        EInteger.FromString("65539"),
        EInteger.FromString("2147483147"),
        EInteger.FromString("2147484147"),
        EInteger.FromString("9223372036854775307"),
        EInteger.FromString("9223372036854776307"),
        EInteger.FromString("18446744073709551115"),
        EInteger.FromString("18446744073709551615"),
      };
      if (CBORObject.True.isTagged()) {
 Assert.fail();
 }
      CBORObject trueObj = CBORObject.True;
      AssertEquals(
        EInteger.FromString("-1"),
        trueObj.getMostInnerTag());
      EInteger[] tagstmp = CBORObject.True.GetAllTags();
      for (int i = 0; i < ranges.length; i += 2) {
        EInteger bigintTemp = ranges[i];
        while (true) {
          EInteger ei = bigintTemp;
          EInteger bigintNext = ei.Add(EInteger.FromInt32(1));
          if (bigintTemp.GetSignedBitLengthAsInt64() <=
            31) {
            int bc = ei.ToInt32Checked();
            if (bc >= -1 && bc <= 37) {
              bigintTemp = bigintNext;
              continue;
            }
            if (bc >= 264 && bc <= 270) {
              bigintTemp = bigintNext;
              continue;
            }
          }
          CBORObject obj = CBORObject.FromObjectAndTag(0, bigintTemp);
          if (!obj.isTagged()) {
            Assert.fail("obj not tagged");
          }
          EInteger[] tags = obj.GetAllTags();
          AssertEquals(1, tags.length);
          AssertEquals(bigintTemp, tags[0]);
          if (!obj.getMostInnerTag().equals(bigintTemp)) {
            String errmsg = "obj tag doesn't match: " + obj;
            AssertEquals(
              bigintTemp,
              obj.getMostInnerTag(),
              errmsg);
          }
          tags = obj.GetAllTags();
          AssertEquals(1, tags.length);
          AssertEquals(bigintTemp, obj.getMostOuterTag());
          AssertEquals(bigintTemp, obj.getMostInnerTag());
          AssertEquals(0, obj.AsInt32Value());
          if (!bigintTemp.equals(maxuint)) {
            EInteger bigintNew = bigintNext;
            // Test multiple tags
            CBORObject obj2 = CBORObject.FromObjectAndTag(obj, bigintNew);
            EInteger[] bi = obj2.GetAllTags();
            if (bi.length != 2) {
              {
                String stringTemp = "Expected 2 tags: " + obj2;
                AssertEquals(
                  2,
                  bi.length,
                  stringTemp);
              }
            }
            bigintNew = bigintNext;
            TestCommon.CompareTestEqualAndConsistent(
              bi[0],
              bigintNew,
              "Outer tag doesn't match");
            TestCommon.CompareTestEqualAndConsistent(
              bi[1],
              bigintTemp,
              "Inner tag doesn't match");
            if (!obj2.getMostInnerTag().equals((Object)bigintTemp)) {
              {
                String stringTemp = "Innermost tag doesn't match: " + obj2;
                AssertEquals(
                  bigintTemp,
                  obj2.getMostInnerTag(),
                  stringTemp);
              }
            }
            EInteger[] tags2 = obj2.GetAllTags();
            AssertEquals(2, tags2.length);
            AssertEquals(bigintNext, obj2.getMostOuterTag());
            AssertEquals(bigintTemp, obj2.getMostInnerTag());
            AssertEquals(0, obj2.AsInt32Value());
          }
          if (bigintTemp.compareTo(ranges[i + 1]) >= 0) {
            break;
          }
          bigintTemp = bigintNext;
        }
      }
    }

    @Test
    public void TestOverlongSimpleValues() {
      for (int i = 0; i <= 0x1f; ++i) {
        byte[] bytes = new byte[] { (byte)0xf8, (byte)i };
        try {
          CBORObject.DecodeFromBytes(bytes);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
    }

    @Test
    public void TestDecodeCtap2Canonical() {
      // Tests that the code rejects noncanonical data
      CBOREncodeOptions options = new CBOREncodeOptions("ctap2canonical=1");
      if (!(options.getCtap2Canonical())) {
 Assert.fail();
 }
      byte[] bytes;
      for (int i = 0; i < 2; ++i) {
        int eb = i == 0 ? 0 : 0x20;
        bytes = new byte[] { (byte)eb };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x17 + eb) };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x18 + eb), 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x19 + eb), 0, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x1a + eb), 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x1b + eb), 0, 0, 0, 0, 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x18 + eb), 0x17 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x18 + eb), 0x18 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x19 + eb), 0, (byte)0xff };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x19 + eb), 1, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x1a + eb), 0, 0, (byte)0xff, (byte)0xff };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x1a + eb), 0, 1, 0, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] {
          (byte)(0x1b + eb), 0, 0, 0, 0, (byte)0xff,
          (byte)0xff, (byte)0xff, (byte)0xff,
         };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x1b + eb), 0, 0, 0, 1, 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
      for (int i = 2; i <= 5; ++i) {
        int eb = 0x20 * i;
        bytes = new byte[] { (byte)eb };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x18 + eb), 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x19 + eb), 0, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x1a + eb), 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte)(0x1b + eb), 0, 0, 0, 0, 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
      bytes = new byte[] { (byte)0xc0, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xd7, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xd8, (byte)0xff, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xd9, (byte)0xff, (byte)0xff, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0xda, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, 0,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0xdb, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, 0,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Nesting depth
      bytes = new byte[] { (byte)0x81, (byte)0x81, (byte)0x81, (byte)0x80 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0x81, (byte)0x81, (byte)0x81, (byte)0x81, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0x81, (byte)0x81, (byte)0x81, (byte)0xa1,
        0, 0,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0x81, (byte)0x81, (byte)0x81, (byte)0x81,
        (byte)0x80,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0x81, (byte)0x81, (byte)0x81, (byte)0xa1,
        0, 0,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0x81, (byte)0x81, (byte)0x81, (byte)0x81,
        (byte)0xa0,
       };
      TestFailingDecode(bytes, options);
      // Floating Point Numbers
      bytes = new byte[] { (byte)0xf9, 8, 8 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xfa, 8, 8, 8, 8 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xfb, 8, 8, 8, 8, 8, 8, 8, 8 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Map Key Ordering
      bytes = new byte[] { (byte)0xa2, 0, 0, 1, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 1, 0, 0, 0 };
      TestFailingDecode(bytes, options);
      bytes = new byte[] { (byte)0xa2, 0, 0, 0x20, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x20, 0, 0, 0 };
      TestFailingDecode(bytes, options);
      bytes = new byte[] { (byte)0xa2, 0, 0, 0x38, (byte)0xff, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x38, (byte)0xff, 0, 0, 0 };
      TestFailingDecode(bytes, options);
      bytes = new byte[] { (byte)0xa2, 0x41, (byte)0xff, 0, 0x42, 0, 0, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x42, 0, 0, 0, 0x41, (byte)0xff, 0 };
      TestFailingDecode(bytes, options);
      bytes = new byte[] { (byte)0xa2, 0x61, 0x7f, 0, 0x62, 0, 0, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes, options);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x62, 0, 0, 0, 0x61, 0x7f, 0 };
      TestFailingDecode(bytes, options);
    }

    @Test
    public void TestIndefLengthMore() {
      byte[] bytes;
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x41, 0x31, (byte)0xff };
      TestSucceedingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x00, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x18, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x38, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x60, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x61, 0x31, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0x81, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xa0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xa1, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xc0, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xd8, (byte)0xff, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xe0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xf8, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xf9, (byte)0xff, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] {
        0x5f, 0x41, 0x30, (byte)0xfa, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff,
       };
      TestFailingDecode(bytes);
      bytes = new byte[] {
        0x5f, 0x41, 0x30, (byte)0xfb, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
       };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x61, 0x31, (byte)0xff };
      TestSucceedingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x00, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x18, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x38, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x40, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x41, 0x31, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0x81, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xa0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xa1, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xc0, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xd8, (byte)0xff, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xe0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xf8, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xf9, (byte)0xff, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] {
        0x7f, 0x61, 0x30, (byte)0xfa, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff,
       };
      TestFailingDecode(bytes);
      bytes = new byte[] {
        0x7f, 0x61, 0x30, (byte)0xfb, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
       };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x41, 0x31, (byte)0xff };
      TestSucceedingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x00, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x18, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x38, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x60, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, 0x61, 0x31, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0x81, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0xa0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0xa1, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0xc0, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0xd8, (byte)0xff, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0xe0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0xf8, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0xf9, (byte)0xff, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x5f, (byte)0xfa, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] {
        0x5f, (byte)0xfb, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff,
       };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, 0x31, (byte)0xff };
      TestSucceedingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x00, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x18, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x38, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x40, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x41, 0x31, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0x81, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0xa0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0xa1, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0xc0, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0xd8, (byte)0xff, 0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0xe0, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0xf8, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0xf9, (byte)0xff, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, (byte)0xfa, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] {
        0x7f, (byte)0xfb, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff,
       };
      TestFailingDecode(bytes);
      // Indefinite-length String with one Unicode code point
      bytes = new byte[] { 0x7f, 0x62, (byte)0xc2, (byte)0x80, (byte)0xff };
      TestSucceedingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x63, (byte)0xe2, (byte)0x80, (byte)0x80, (byte)0xff };
      TestSucceedingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x64, (byte)0xf2, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0xff };
      TestSucceedingDecode(bytes);
      // Disallow splitting code points in indefinite-length
      // text strings
      bytes = new byte[] { 0x7f, 0x61, (byte)0xc2, 0x61, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, (byte)0xe2, 0x62, (byte)0x80, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x62, (byte)0xe2, (byte)0x80, 0x61, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, (byte)0xf2, 0x63, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x62, (byte)0xf2, (byte)0x80, 0x62, (byte)0x80, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x63, (byte)0xf2, (byte)0x80, (byte)0x80, 0x61, (byte)0x80, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, (byte)0xc2, 0x61, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x62, (byte)0xe2, (byte)0x80, 0x61, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x63, (byte)0xf2, (byte)0x80, (byte)0x80, 0x61, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x61, (byte)0xc2, 0x62, (byte)0x80, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] { 0x7f, 0x62, (byte)0xe2, (byte)0x80, 0x62, (byte)0x80, 0x20, (byte)0xff };
      TestFailingDecode(bytes);
      bytes = new byte[] {
        0x7f, 0x63, (byte)0xf2, (byte)0x80, (byte)0x80, 0x62, (byte)0x80, 0x20,
        (byte)0xff,
       };
      TestFailingDecode(bytes);
    }

    private static void TestSucceedingDecode(byte[] bytes) {
      try {
        CBORTestCommon.FromBytesTestAB(bytes);
      } catch (Exception ex) {
        Assert.fail(TestCommon.ToByteArrayString(bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static void TestFailingDecode(byte[] bytes) {
      try {
        CBORTestCommon.FromBytesTestAB(bytes);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(TestCommon.ToByteArrayString(bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(bytes);
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(TestCommon.ToByteArrayString(bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static void TestFailingDecode(byte[] bytes, CBOREncodeOptions
      options) {
      try {
        CBORTestCommon.FromBytesTestAB(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(TestCommon.ToByteArrayString(bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static int[][] valueBadLesserFields = {
      new int[] { 0, 1, 0, 0, 0, 0, 0 },
      new int[] { -1, 1, 0, 0, 0, 0, 0 },
      new int[] { 1, 32, 0, 0, 0, 0, 0 },
      new int[] { 2, 30, 0, 0, 0, 0, 0 },
      new int[] { 3, 32, 0, 0, 0, 0, 0 },
      new int[] { 4, 31, 0, 0, 0, 0, 0 },
      new int[] { 5, 32, 0, 0, 0, 0, 0 },
      new int[] { 6, 31, 0, 0, 0, 0, 0 },
      new int[] { 7, 32, 0, 0, 0, 0, 0 },
      new int[] { 8, 32, 0, 0, 0, 0, 0 },
      new int[] { 9, 31, 0, 0, 0, 0, 0 },
      new int[] { 10, 32, 0, 0, 0, 0, 0 },
      new int[] { 11, 31, 0, 0, 0, 0, 0 },
      new int[] { 12, 32, 0, 0, 0, 0, 0 },
      new int[] { 13, 1, 0, 0, 0, 0, 0 },
      new int[] { Integer.MIN_VALUE, 1, 0, 0, 0, 0, 0 },
      new int[] { Integer.MAX_VALUE, 1, 0, 0, 0, 0, 0 },
      new int[] { 1, 0, 0, 0, 0, 0, 0 },
      new int[] { 1, -1, 0, 0, 0, 0, 0 },
      new int[] { 1, Integer.MIN_VALUE, 0, 0, 0, 0, 0 },
      new int[] { 1, 32, 0, 0, 0, 0, 0 },
      new int[] { 1, Integer.MAX_VALUE, 0, 0, 0, 0, 0 },
      new int[] { 1, 1, -1, 0, 0, 0, 0 },
      new int[] { 1, 1, Integer.MIN_VALUE, 0, 0, 0, 0 },
      new int[] { 1, 1, 24, 0, 0, 0, 0 },
      new int[] { 1, 1, 59, 0, 0, 0, 0 },
      new int[] { 1, 1, 60, 0, 0, 0, 0 },
      new int[] { 1, 1, Integer.MAX_VALUE, 0, 0, 0, 0 },
      new int[] { 1, 1, 0, -1, 0, 0, 0 },
      new int[] { 1, 1, 0, Integer.MIN_VALUE, 0, 0, 0 },
      new int[] { 1, 1, 0, 60, 0, 0, 0 },
      new int[] { 1, 1, 0, Integer.MAX_VALUE, 0, 0, 0 },
      new int[] { 1, 1, 0, 0, -1, 0, 0 },
      new int[] { 1, 1, 0, 0, Integer.MIN_VALUE, 0, 0 },
      new int[] { 1, 1, 0, 0, 60, 0, 0 },
      new int[] { 1, 1, 0, 0, Integer.MAX_VALUE, 0, 0 },
      new int[] { 1, 1, 0, 0, 0, -1, 0 },
      new int[] { 1, 1, 0, 0, 0, Integer.MIN_VALUE, 0 },
      new int[] { 1, 1, 0, 0, 0, 1000 * 1000 * 1000, 0 },
      new int[] { 1, 1, 0, 0, 0, Integer.MAX_VALUE, 0 },
      new int[] { 1, 1, 0, 0, 0, 0, -1440 },
      new int[] { 1, 1, 0, 0, 0, 0, Integer.MIN_VALUE },
      new int[] { 1, 1, 0, 0, 0, 0, 1440 },
      new int[] { 1, 1, 0, 0, 0, 0, Integer.MAX_VALUE },
    };

    private static void TestBadDateFieldsOne(CBORDateConverter conv) {
      EInteger eint = EInteger.FromInt32(2000);
      int[] lesserFields;
      for (int i = 0; i < valueBadLesserFields.length; ++i) {
        lesserFields = valueBadLesserFields[i];
        Assert.assertEquals("" + i,7,lesserFields.length);
        if (lesserFields[3] == 0 && lesserFields[4] == 0 &&
          lesserFields[5] == 0 &&
          lesserFields[6] == 0 && lesserFields[2] == 0) {
          try {
            conv.DateTimeFieldsToCBORObject(
              2000,
              lesserFields[0],
              lesserFields[1]);
            Assert.fail(
              "Should have failed: " + lesserFields[0] + " " + lesserFields[1]);
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
        }
        if (lesserFields[5] == 0 && lesserFields[6] == 0) {
          try {
            conv.DateTimeFieldsToCBORObject(
              2000,
              lesserFields[0],
              lesserFields[1],
              lesserFields[2],
              lesserFields[3],
              lesserFields[4]);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
        }
        try {
          conv.DateTimeFieldsToCBORObject(eint, lesserFields);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
      lesserFields = null;
      try {
        conv.DateTimeFieldsToCBORObject(eint, lesserFields);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // TODO: Make into CBORException in next major version
      lesserFields = new int[] { 1 };
      try {
        conv.DateTimeFieldsToCBORObject(eint, lesserFields);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      lesserFields = new int[] { 1, 1 };
      try {
        conv.DateTimeFieldsToCBORObject(eint, lesserFields);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      lesserFields = new int[] { 1, 1, 0 };
      try {
        conv.DateTimeFieldsToCBORObject(eint, lesserFields);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      lesserFields = new int[] { 1, 1, 0, 0 };
      try {
        conv.DateTimeFieldsToCBORObject(eint, lesserFields);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      lesserFields = new int[] { 1, 1, 0, 0, 0 };
      try {
        conv.DateTimeFieldsToCBORObject(eint, lesserFields);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      lesserFields = new int[] { 1, 1, 0, 0, 0, 0 };
      try {
        conv.DateTimeFieldsToCBORObject(eint, lesserFields);
        Assert.fail("Should have failed: 6");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2100, 2, 29);
        Assert.fail("Should have failed: 2100/2/29");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2001, 2, 29);
        Assert.fail("Should have failed: 2001/2/29");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2007, 2, 29);
        Assert.fail("Should have failed: 2007/2/29");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2000, 2, 28);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2100, 2, 28);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2001, 2, 28);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2007, 2, 28);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2004, 2, 29);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2008, 2, 29);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        conv.DateTimeFieldsToCBORObject(2000, 2, 29);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestBadDateFields() {
      TestBadDateFieldsOne(CBORDateConverter.TaggedNumber);
      TestBadDateFieldsOne(CBORDateConverter.UntaggedNumber);
      TestBadDateFieldsOne(CBORDateConverter.TaggedString);
    }

    @Test
    public void TestTags264And265() {
      CBORObject cbor;
      // Tag 264
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd9, 0x01, 0x08,
        (byte)0x82,
        (byte)0xc2, 0x42, 2, 2, (byte)0xc2, 0x42, 2, 2,
       });
      CBORTestCommon.AssertRoundTrip(cbor);
      // Tag 265
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd9, 0x01, 0x09,
        (byte)0x82,
        (byte)0xc2, 0x42, 2, 2, (byte)0xc2, 0x42, 2, 2,
       });
      CBORTestCommon.AssertRoundTrip(cbor);
    }

    @Test
    public void TestTagThenBreak() {
      TestFailingDecode(new byte[] { (byte)0xd1, (byte)0xff });
    }

    @Test
    public void TestTextStringStream() {
      CBORObject cbor = CBORTestCommon.FromBytesTestAB(
          new byte[] { 0x7f, 0x61, 0x2e, 0x61, 0x2e, (byte)0xff });
      {
        String stringTemp = cbor.AsString();
        Assert.assertEquals(
          "..",
          stringTemp);
      }
      TestTextStringStreamOne(TestCommon.Repeat('x', 200000));
      TestTextStringStreamOne(TestCommon.Repeat('\u00e0', 200000));
      TestTextStringStreamOne(TestCommon.Repeat('\u3000', 200000));
      TestTextStringStreamOne(TestCommon.Repeat("\ud800\udc00", 200000));
      TestTextStringStreamOne(
        "A" + TestCommon.Repeat('\u00e0', 200000));
      TestTextStringStreamOne(
        "A" + TestCommon.Repeat('\u3000', 200000));
      TestTextStringStreamOne(
        "AA" + TestCommon.Repeat('\u3000', 200000));
      TestTextStringStreamOne(
        "A" + TestCommon.Repeat("\ud800\udc00", 200000));
      TestTextStringStreamOne(
        "AA" + TestCommon.Repeat("\ud800\udc00", 200000));
      TestTextStringStreamOne(
        "AAA" + TestCommon.Repeat("\ud800\udc00", 200000));
    }

    @Test
    public void TestTextStringStreamNoIndefiniteWithinDefinite() {
      TestFailingDecode(new byte[] {
        0x7f, 0x61, 0x20, 0x7f, 0x61, 0x20,
        (byte)0xff, (byte)0xff,
       });
    }

    @Test
    public void TestIntegerFloatingEquivalence() {
      CBORObject cbor;
      // 0 versus 0.0
      cbor = CBORObject.NewMap();
      cbor.Set((int)0, CBORObject.FromObject("testzero"));
      cbor.Set((double)0.0, CBORObject.FromObject("testpointzero"));
      Assert.assertEquals(2, cbor.size());
      {
        String stringTemp = cbor.get(CBORObject.FromObject(0)).AsString();
        Assert.assertEquals(
          "testzero",
          stringTemp);
      }
      {
        String stringTemp = cbor.get(CBORObject.FromObject(
              (double)0.0)).AsString();
        Assert.assertEquals(
          "testpointzero",
          stringTemp);
      }
      cbor = CBORObject.NewMap();
      cbor.Set((double)0.0, CBORObject.FromObject("testpointzero"));
      cbor.Set((int)0, CBORObject.FromObject("testzero"));
      Assert.assertEquals(2, cbor.size());
      {
        String stringTemp = cbor.get(CBORObject.FromObject(0)).AsString();
        Assert.assertEquals(
          "testzero",
          stringTemp);
      }
      {
        String stringTemp = cbor.get(CBORObject.FromObject(
              (double)0.0)).AsString();
        Assert.assertEquals(
          "testpointzero",
          stringTemp);
      }
      // 3 versus 3.0
      cbor = CBORObject.NewMap();
      cbor.Set((int)3, CBORObject.FromObject("testzero"));
      cbor.Set((double)3.0, CBORObject.FromObject("testpointzero"));
      Assert.assertEquals(2, cbor.size());
      {
        String stringTemp = cbor.get(CBORObject.FromObject(3)).AsString();
        Assert.assertEquals(
          "testzero",
          stringTemp);
      }
      {
        String stringTemp = cbor.get(CBORObject.FromObject(
              (double)3.0)).AsString();
        Assert.assertEquals(
          "testpointzero",
          stringTemp);
      }
      cbor = CBORObject.NewMap();
      cbor.Set((double)3.0, CBORObject.FromObject("testpointzero"));
      cbor.Set((int)3, CBORObject.FromObject("testzero"));
      Assert.assertEquals(2, cbor.size());
      {
        String stringTemp = cbor.get(CBORObject.FromObject(3)).AsString();
        Assert.assertEquals(
          "testzero",
          stringTemp);
      }
      {
        String stringTemp = cbor.get(CBORObject.FromObject(
              (double)3.0)).AsString();
        Assert.assertEquals(
          "testpointzero",
          stringTemp);
      }
    }

    @Test
    public void TestRoundTripESignalingNaN() {
      ToObjectTest.TestToFromObjectRoundTrip(EDecimal.SignalingNaN);
      ToObjectTest.TestToFromObjectRoundTrip(ERational.SignalingNaN);
      ToObjectTest.TestToFromObjectRoundTrip(EFloat.SignalingNaN);
    }

    @Test
    public void TestBigNumberThresholds() {
      EInteger maxCborInteger = EInteger.FromString("18446744073709551615");
      EInteger maxInt64 = EInteger.FromString("9223372036854775807");
      EInteger minCborInteger = EInteger.FromString("-18446744073709551616");
      EInteger minInt64 = EInteger.FromString("-9223372036854775808");
      EInteger pastMaxCborInteger = EInteger.FromString(
          "18446744073709551616");
      EInteger pastMaxInt64 = EInteger.FromString("9223372036854775808");
      EInteger pastMinCborInteger =
        EInteger.FromString("-18446744073709551617");
      EInteger pastMinInt64 = EInteger.FromString("-9223372036854775809");
      EInteger[] eints = new EInteger[] {
        maxCborInteger, maxInt64, minCborInteger,
        minInt64, pastMaxCborInteger, pastMaxInt64, pastMinCborInteger,
        pastMinInt64,
      };
      boolean[] isPastCbor = new boolean[] {
        false, false, false, false, true, false, true,
        false,
      };
      boolean[] isPastInt64 = new boolean[] {
        false, false, false, false, true, true, true,
        true,
      };
      for (int i = 0; i < eints.length; ++i) {
        CBORObject cbor;
        boolean isNegative = eints[i].signum() < 0;
        cbor = CBORObject.FromObject(eints[i]);
        if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
        if (isPastCbor[i]) {
          if (isNegative) {
            if (!(cbor.HasOneTag(3))) {
 Assert.fail();
 }
          } else {
            if (!(cbor.HasOneTag(2))) {
 Assert.fail();
 }
          }
        } else {
          Assert.assertEquals(CBORType.Integer, cbor.getType());
          Assert.assertEquals(0, cbor.getTagCount());
        }
        EFloat ef = EFloat.Create(EInteger.FromInt32(1), eints[i]);
        cbor = CBORObject.FromObject(ef);
        if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
        if (isPastCbor[i]) {
          if (!(cbor.HasOneTag(265))) {
 Assert.fail();
 }
          if (isNegative) {
            if (!(cbor.get(0).HasOneTag(3))) {
 Assert.fail();
 }
          } else {
            if (!(cbor.get(0).HasOneTag(2))) {
 Assert.fail();
 }
          }
        } else {
          if (!(cbor.HasOneTag(5))) {
 Assert.fail();
 }
          Assert.assertEquals(CBORType.Integer, cbor.get(0).getType());
          Assert.assertEquals(0, cbor.get(0).getTagCount());
        }
        try {
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(ef, ms);
            cbor = CBORObject.DecodeFromBytes(ms.toByteArray());
            if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
            if (isPastCbor[i]) {
              if (!(cbor.HasOneTag(265))) {
 Assert.fail();
 }
              if (isNegative) {
                if (!(cbor.get(0).HasOneTag(3))) {
 Assert.fail();
 }
              } else {
                if (!(cbor.get(0).HasOneTag(2))) {
 Assert.fail();
 }
              }
            } else {
              if (!(cbor.HasOneTag(5))) {
 Assert.fail();
 }
              Assert.assertEquals(CBORType.Integer, cbor.get(0).getType());
              Assert.assertEquals(0, cbor.get(0).getTagCount());
            }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          EDecimal ed = EDecimal.Create(EInteger.FromInt32(1), eints[i]);
          cbor = CBORObject.FromObject(ed);
          if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
          if (isPastCbor[i]) {
            if (!(cbor.HasOneTag(264))) {
 Assert.fail();
 }
            if (isNegative) {
              if (!(cbor.get(0).HasOneTag(3))) {
 Assert.fail();
 }
            } else {
              if (!(cbor.get(0).HasOneTag(2))) {
 Assert.fail();
 }
            }
          } else {
            if (!(cbor.HasOneTag(4))) {
 Assert.fail();
 }
            Assert.assertEquals(CBORType.Integer, cbor.get(0).getType());
            Assert.assertEquals(0, cbor.get(0).getTagCount());
          }
          {
            java.io.ByteArrayOutputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayOutputStream();

            CBORObject.Write(ed, ms2);
            cbor = CBORObject.DecodeFromBytes(ms2.toByteArray());
            if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
            if (isPastCbor[i]) {
              if (!(cbor.HasOneTag(264))) {
 Assert.fail();
 }
              if (isNegative) {
                if (!(cbor.get(0).HasOneTag(3))) {
 Assert.fail();
 }
              } else {
                if (!(cbor.get(0).HasOneTag(2))) {
 Assert.fail();
 }
              }
            } else {
              if (!(cbor.HasOneTag(4))) {
 Assert.fail();
 }
              Assert.assertEquals(CBORType.Integer, cbor.get(0).getType());
              Assert.assertEquals(0, cbor.get(0).getTagCount());
            }
}
finally {
try { if (ms2 != null) { ms2.close(); } } catch (java.io.IOException ex) {}
}
}
        } catch (IOException ioe) {
          throw new IllegalStateException(ioe.getMessage(), ioe);
        }
      }
    }

    @Test
    public void TestRationalJSONSpecificA() {
      ERational er =

  ERational.FromString("1088692579850251977918382727683876451288883451475551838663907953515213777772897669/734154292316019508508581520803142368704146796235662433292652");
      CBORObject.FromObject(er).ToJSONString();
    }
    @Test
    public void TestRationalJSONSpecificB() {
      ERational er2 =

  ERational.FromString("1117037884940373468269515037592447741921166676191625235424/13699696515096285881634845839085271311137");
      CBORObject.FromObject(er2).ToJSONString();
    }
    @Test
    public void TestRationalJSONSpecificC() {
      ERational er2 =

  ERational.FromString("42595158956667/1216724793801972483341765319799605241541780250657492435");
      CBORObject.FromObject(er2).ToJSONString();
    }

    @Test
    public void TestAllowEmpty() {
      CBOREncodeOptions options;
      byte[] bytes = new byte[0];
      options = new CBOREncodeOptions("");
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      options = new CBOREncodeOptions("allowempty=true");
      Assert.assertEquals(null, CBORObject.DecodeFromBytes(bytes, options));
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        options = new CBOREncodeOptions("");
        try {
          CBORObject.Read(ms, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        options = new CBOREncodeOptions("allowempty=true");
        Assert.assertEquals(null, CBORObject.Read(ms, options));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
    }

    @Test
    public void TestCtap2CanonicalDecodeEncodeSpecific1() {
      byte[] bytes = new byte[] {
        (byte)0xa2, (byte)0x82, (byte)0xf6,
        (byte)0x82,
        (byte)0xfb, 0x3c,
        (byte)0xf0, 0x03, 0x42,
        (byte)0xcb, 0x54, 0x6c,
        (byte)0x85,
        (byte)0x82,
        (byte)0xc5,
        (byte)0x82, 0x18,
        (byte)0xba, 0x0a,
        (byte)0xfa,
        (byte)0x84,
        (byte)0xa0, 0x57,
        (byte)0x97, 0x42, 0x00, 0x01, 0x65, 0x62, 0x7d, 0x45, 0x20, 0x6c, 0x41,
        0x00,
       };
      CBORObject cbor = CBORObject.DecodeFromBytes(bytes);
      CBOREncodeOptions options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println("" + cbor);
      try {
        cbor.EncodeToBytes(options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(CBORTestCommon.CheckEncodeToBytes(cbor),
          options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCtap2CanonicalDecodeEncodeSpecific2() {
      byte[] bytes = new byte[] {
        (byte)0x82,
        (byte)0x82,
        (byte)0xf5,
        (byte)0x82,
        (byte)0x81,
        (byte)0xd8, 0x1e, (byte)0x82, 0x29, 0x01, (byte)0x80, 0x43, 0x01, 0x01,
        0x00,
       };
      CBORObject cbor = CBORObject.DecodeFromBytes(bytes);
      CBOREncodeOptions options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println("" + cbor);
      try {
        cbor.EncodeToBytes(options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(CBORTestCommon.CheckEncodeToBytes(cbor),
          options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCtap2CanonicalDecodeEncodeSpecific3() {
      byte[] bytes = new byte[] {
        (byte)0x82,
        (byte)0xfa,
        (byte)0xe0,
        (byte)0xa0,
        (byte)0x9d,
        (byte)0xba,
        (byte)0x82,
        (byte)0x82,
        (byte)0xf7, (byte)0xa2, (byte)0xa0, (byte)0xf7, 0x60, 0x41, 0x00,
        (byte)0xf4,
       };
      CBORObject cbor = CBORObject.DecodeFromBytes(bytes);
      CBOREncodeOptions options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println("" + cbor);
      try {
        cbor.EncodeToBytes(options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(CBORTestCommon.CheckEncodeToBytes(cbor),
          options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCtap2CanonicalDecodeEncodeSpecific4() {
      byte[] bytes = new byte[] {
        (byte)0x81,
        (byte)0x82,
        (byte)0xda, 0x00, 0x0d, 0x77, 0x09,
        (byte)0xf4, (byte)0x82, (byte)0x82, (byte)0xf4, (byte)0xa0,
        (byte)0xf6,
       };
      CBORObject cbor = CBORObject.DecodeFromBytes(bytes);
      CBOREncodeOptions options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println("" + cbor);
      try {
        cbor.EncodeToBytes(options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(CBORTestCommon.CheckEncodeToBytes(cbor),
          options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCtap2CanonicalDecodeEncodeSpecific5() {
      byte[] bytes = new byte[] {
        (byte)0xa2,
        (byte)0xda, 0x00, 0x03, 0x69,
        (byte)0x95, (byte)0xf6, (byte)0xf7, (byte)0xf6, (byte)0xf4,
       };
      CBORObject cbor = CBORObject.DecodeFromBytes(bytes);
      CBOREncodeOptions options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println("" + cbor);
      try {
        cbor.EncodeToBytes(options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(bytes, options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(CBORTestCommon.CheckEncodeToBytes(cbor),
          options);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    public static void TestCtap2CanonicalDecodeEncodeOne(
      CBORObject cbor) {
      CBOREncodeOptions options = new CBOREncodeOptions("ctap2canonical=true");
      if (cbor == null) {
        throw new NullPointerException("cbor");
      }
      byte[] e2bytes = CBORTestCommon.CheckEncodeToBytes(cbor);
      byte[] bytes = e2bytes;
      cbor = CBORObject.DecodeFromBytes(bytes);
      CBORObject cbor2 = null;
      try {
        bytes = cbor.EncodeToBytes(options);
        try {
          cbor2 = CBORObject.DecodeFromBytes(bytes, options);
        } catch (Exception ex2) {
          Assert.fail(ex2.toString());
          throw new IllegalStateException("", ex2);
        }
        byte[] bytes2 = cbor2.EncodeToBytes(options);
        TestCommon.AssertByteArraysEqual(bytes, bytes2);
      } catch (CBORException ex4) {
        // Canonical encoding failed, so DecodeFromBytes must fail
        bytes = CBORTestCommon.CheckEncodeToBytes(cbor);
        try {
          CBORObject.DecodeFromBytes(bytes, options);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex3) {
          Assert.fail(ex3.toString() + "\n" + ex4.toString());
          throw new IllegalStateException("", ex3);
        }
      }
    }

    @Test
    public void TestCtap2CanonicalDecodeEncode() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        TestCtap2CanonicalDecodeEncodeOne(
          CBORTestCommon.RandomCBORObject(r));
      }
    }

    @Test
    public void TestTextStringStreamNoTagsBeforeDefinite() {
      try {
        CBORTestCommon.FromBytesTestAB(new byte[] {
          0x7f, 0x61, 0x20,
          (byte)0xc0, 0x61, 0x20, (byte)0xff,
         });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static EInteger AsEI(CBORObject obj) {
      Object o = obj.ToObject(EInteger.class);
      return (EInteger)o;
    }

    private static EDecimal AsED(CBORObject obj) {
      Object o = obj.ToObject(EDecimal.class);
      return (EDecimal)o;
    }

    private static ERational AsER(CBORObject obj) {
      Object o = obj.ToObject(ERational.class);
      return (ERational)o;
    }

    private static void AddSubCompare(CBORObject o1, CBORObject o2) {
      EDecimal cmpDecFrac = AsED(o1).Add(AsED(o2));
      EDecimal cmpCobj = o1.AsNumber().Add(o2.AsNumber()).ToEDecimal();
      TestCommon.CompareTestEqual(cmpDecFrac, cmpCobj);
      cmpDecFrac = AsED(o1).Subtract(AsED(o2));
      cmpCobj = o1.AsNumber().Subtract(o2.AsNumber()).ToEDecimal();
      TestCommon.CompareTestEqual(cmpDecFrac, cmpCobj);
      CBORObjectTest.CompareDecimals(o1, o2);
    }

    @Test
    public void TestRationalJsonString() {
      String s1 =

  "2314185985457202732189984229086860275536452482912712559300364012538811890519021609896348772904852567130731662638662357113651250315642348662481229868556065813139982071069333964882192144997551182445870403177326619887472161149361459394237531679153467064950578633985038857850930553390675215926785522674620921221013857844957579079905210161700278381169854796455676266121858525817919848944985101521416062436650605384179954486013171983603514573732843973878942460661051122207994787725632035785836247773451399551083190779512400561839577794870702499681043124072992405732619348558204728800270899359780143357389476977840367320292768181094717788094551212489822736249585469244387735363318078783976724668392554429679443922755068135350076319909649622682466354980725423633530350364989945871920048447230307815643527525431336201627641891131614532527580497256382071436840494627668584005384127077683035880018530366999707415485257695504047147523521952194384880231172509079788316925500613704258819197092976088140216280520582313645747413451716685429138670645309423396623806701594839731451445336814620082926910150739091172178600865482539725012429775997863264496120844788653020449046903816363344201802799558922359223708825558520103859838244276323990910167216851809090120320961066908102124848129364767874532700083684330840078660557364044159387179646160035386030868471110043830522222249658101959143096323641704675830142899751696476007503506009598273729872080504917363964684006707667515610753851782851579370526135223570019729110932882718719";
      String s2 =

  "6662791484278690594826817847881545965329948329731867968121995135273120814985447625408875010164308165523077008393040907448927095816668472183767306507621988644226927007049807896601977790621449471807224544610921018712323247068196141241260970690722422573836727986751170029846748991630865560108915742912790575418880931905841405752318207096850143527159053198029648842245667818442862752212319584591326350903220882410151458427571245209321776934621224736963318933098990162346637307854541301688032696173626360523085187457965260167140087021479260407414362314681927575639118779628079152745063483804212029391314516551540082552323766393935679162832149309343521979435765872081112730566874916857979923774605127048865566043423311513224206112727810624953812129189407444425723013814542858953773303224750083748214186967592731457750110532337407558719554095585903998079748001889804344632924251379769721367766565683489037136792018541299840911134792202457550460405605363852082703644386814261111315827747899661812006358141505684436007974039689212221755906535319187254965909243842599581550882694985174561192357511545227109515529785121078195397742875082523296406527673130136841581998940369597346610553537051630040762759128694436878055285011408511186930096142698312900789328008870013582608819840691525856150433351282368061590406881127142805435230013505013582096402814554965693562771980924387951907732638686068565579913844909487962223859043024131114445573057517284388114134555750443506173757889119715387627461644374462498045130424821914143893279013612002227413094709860042079542320696728791055885208451681839380238306841352325674806804434188273228678316889664118537421135644047836961335665043472528998461372064871916691003281042407296035913958087310042321020211485879442799018303005446353339317990963540";

      ERational er = ERational.Create(
          EInteger.FromString(s1),
          EInteger.FromString(s2));
      CBORObject cbor = CBORObject.FromObject(er);
      cbor.ToJSONString();
    }

    public static boolean CheckUtf16(String str) {
      if (str == null) {
        return false;
      }
      for (int i = 0; i < str.length(); ++i) {
        char c = str.charAt(i);
        if ((c & 0xfc00) == 0xd800 && i + 1 < str.length() &&
          (str.charAt(i) & 0xfc00) == 0xdc00) {
          ++i;
        } else if ((c & 0xf800) == 0xd800) {
          return false;
        }
      }
      return true;
    }

    @Test
    public void TestWriteBasic() {
      JSONOptions jsonop1 = new JSONOptions("writebasic=true");
      String json = CBORObject.FromObject("\uD800\uDC00").ToJSONString(jsonop1);
      Assert.assertEquals("\"\\uD800\\uDC00\"", json);
      json = CBORObject.FromObject("\u0800\u0C00").ToJSONString(jsonop1);
      Assert.assertEquals("\"\\u0800\\u0C00\"", json);
      json = CBORObject.FromObject("\u0085\uFFFF").ToJSONString(jsonop1);
      Assert.assertEquals("\"\\u0085\\uFFFF\"", json);
      RandomGenerator rg = new RandomGenerator();
      for (int i = 0; i < 1000; ++i) {
        String rts = RandomObjects.RandomTextString(rg);
        CBORObject cbor = CBORObject.FromObject(rts);
        json = cbor.ToJSONString(jsonop1);
        // Check that the JSON contains only ASCII code points
        for (int j = 0; j < json.length(); ++j) {
          char c = json.charAt(j);
          if ((c < 0x20 && c != 0x09 && c != 0x0a && c != 0x0d) || c >= 0x7f) {
            Assert.fail(rts);
          }
        }
        // Round-trip check
        Assert.assertEquals(cbor, CBORObject.FromJSONString(json));
      }
    }

    @Test
    public void TestJSONOptions() {
      JSONOptions jsonop1 = new JSONOptions("numberconversion=intorfloat");
      {
        Object objectTemp = jsonop1.toString();
        Object objectTemp2 = new JSONOptions(jsonop1.toString()).toString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      JSONOptions jsonop2 = new JSONOptions("numberconversion=decimal128");
      {
        Object objectTemp = jsonop2.toString();
        Object objectTemp2 = new JSONOptions(jsonop2.toString()).toString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      JSONOptions jsonop3 = new JSONOptions("numberconversion=intorfloatfromdouble");
      {
        Object objectTemp = jsonop3.toString();
        Object objectTemp2 = new JSONOptions(jsonop3.toString()).toString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      JSONOptions jsonop4 = new JSONOptions("numberconversion=double");
      {
        Object objectTemp = jsonop4.toString();
        Object objectTemp2 = new JSONOptions(jsonop4.toString()).toString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestPODOptions() {
      PODOptions podop = PODOptions.Default;
      {
        Object objectTemp = podop.toString();
        Object objectTemp2 = new PODOptions(podop.toString()).toString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestCBOREncodeOptions() {
      CBOREncodeOptions encodeop = CBOREncodeOptions.Default;
      {
        Object objectTemp = encodeop.toString();
        Object objectTemp2 = new
        CBOREncodeOptions(encodeop.toString()).toString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestRandomJSON() {
      JSONGenerator jsongen = new JSONGenerator();
      RandomGenerator rg = new RandomGenerator();
      JSONOptions jsonop1 = new JSONOptions("numberconversion=intorfloat");
      JSONOptions jsonop2 = new JSONOptions("numberconversion=decimal128");
      JSONOptions jsonop3 = new JSONOptions("numberconversion=intorfloatfromdouble");
      JSONOptions jsonop4 = new JSONOptions("numberconversion=double");
      for (int i = 0; i < 200; ++i) {
        byte[] jsonbytes = jsongen.Generate(rg);
        // System.out.println("" + i + " len=" + jsonbytes.length);
        JSONOptions currop = null;
        try {
          currop = jsonop1;
          CBORObject.FromJSONBytes(jsonbytes, jsonop1);
          currop = jsonop2;
          CBORObject.FromJSONBytes(jsonbytes, jsonop2);
          currop = jsonop3;
          CBORObject.FromJSONBytes(jsonbytes, jsonop3);
          currop = jsonop4;
          CBORObject.FromJSONBytes(jsonbytes, jsonop4);
        } catch (CBORException ex) {
          String msg = ex.getMessage() + "\n" +
            com.upokecenter.util.DataUtilities.GetUtf8String(jsonbytes, true) + "\n" + currop;
          throw new IllegalStateException(msg, ex);
        }
      }
    }

    public static boolean TestTextStringStreamOne(String longString) {
      if (!CheckUtf16(longString)) {
        return false;
      }
      CBORObject cbor, cbor2;
      cbor = ToObjectTest.TestToFromObjectRoundTrip(longString);
      cbor2 = CBORTestCommon.FromBytesTestAB(
          CBORTestCommon.CheckEncodeToBytes(
            cbor));
      {
        Object objectTemp = longString;
        Object objectTemp2 = CBORObject.DecodeFromBytes(
            CBORTestCommon.CheckEncodeToBytes(cbor)).AsString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        String cc = "useindeflengthstrings";
        cc += "=";
        cc += "false,allowduplicatekeys";
        cc += "=";
        cc += "true";
        String strTemp2 = CBORObject.DecodeFromBytes(cbor.EncodeToBytes(
              new CBOREncodeOptions(cc))).AsString();
        Assert.assertEquals(longString, strTemp2);
      }
      {
        String cc = "useindeflengthstrings";
        cc += "=";
        cc += "true,allowduplicatekeys";
        cc += "=";
        cc += "true";
        String strTemp2 = CBORObject.DecodeFromBytes(cbor.EncodeToBytes(
              new CBOREncodeOptions(cc))).AsString();
        Assert.assertEquals(longString, strTemp2);
      }
      TestCommon.AssertEqualsHashCode(cbor, cbor2);
      Assert.assertEquals(longString, cbor2.AsString());
      return true;
    }

    public static void TestWriteToJSON(CBORObject obj) {
      CBORObject objA = null;
      String jsonString = "";
      {
        java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

        try {
          if (obj == null) {
            throw new NullPointerException("obj");
          }
          obj.WriteJSONTo(ms);
          jsonString = com.upokecenter.util.DataUtilities.GetUtf8String(
              ms.toByteArray(),
              false);
          objA = CBORObject.FromJSONString(jsonString);
        } catch (CBORException ex) {
          throw new IllegalStateException(
            jsonString + "\n" + ex.toString(),
            ex);
        } catch (IOException ex) {
          throw new IllegalStateException(
            "IOException\n" + ex.toString(),
            ex);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      CBORObject objB = CBORObject.FromJSONString(obj.ToJSONString());
      if (!objA.equals(objB)) {
        Assert.fail("WriteJSONTo gives different results from " +
          "ToJSONString\nobj=" +
          TestCommon.ToByteArrayString(obj.EncodeToBytes()) +
          "\nobjA=" + TestCommon.ToByteArrayString(objA.EncodeToBytes()) +
          "\nobjB=" + TestCommon.ToByteArrayString(objB.EncodeToBytes()) +
          "\nobj=" + obj.toString() + "\nobjA=" + objA.toString() +
          "\nobjB=" + objB.toString() + "\njsonstring=" + jsonString +
          "\ntojsonstring=" + obj.ToJSONString());
      }
    }
  }
