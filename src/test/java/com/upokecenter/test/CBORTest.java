package com.upokecenter.test;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import java.io.*;
import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  public class CBORTest {
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
      CBORObject cbor1 = CBORObject.DecodeFromBytes (bytes1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes (bytes2);
      CBORObject cbor3 = CBORObject.DecodeFromBytes (bytes3);
      CBORObject cbor4 = CBORObject.DecodeFromBytes (bytes4);
      TestCommon.CompareTestLess (cbor1, cbor2);
      TestCommon.CompareTestLess (cbor1, cbor4);
      TestCommon.CompareTestLess (cbor3, cbor2);
      TestCommon.CompareTestLess (cbor3, cbor4);
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
      CBORObject cbor1 = CBORObject.DecodeFromBytes (bytes1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes (bytes2);
      CBORObject cbor3 = CBORObject.DecodeFromBytes (bytes3);
      CBORObject cbor4 = CBORObject.DecodeFromBytes (bytes4);
      TestCommon.CompareTestLess (cbor1, cbor2);
      TestCommon.CompareTestLess (cbor1, cbor4);
      TestCommon.CompareTestLess (cbor3, cbor2);
      TestCommon.CompareTestLess (cbor3, cbor4);
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
      CBORObject cbor1 = CBORObject.DecodeFromBytes (bytes1);
      CBORObject cbor2 = CBORObject.DecodeFromBytes (bytes2);
      CBORObject cbor3 = CBORObject.DecodeFromBytes (bytes3);
      CBORObject cbor4 = CBORObject.DecodeFromBytes (bytes4);
      TestCommon.CompareTestLess (cbor1, cbor2);
      TestCommon.CompareTestLess (cbor1, cbor4);
      TestCommon.CompareTestLess (cbor3, cbor2);
      TestCommon.CompareTestLess (cbor3, cbor4);
    }

    @Test
    public void TestCBORMapAdd() {
      CBORObject cbor = CBORObject.NewMap();
      cbor.Add (1, 2);
      if (!(cbor.ContainsKey(
          ToObjectTest.TestToFromObjectRoundTrip (1))))Assert.fail();
      {
        int varintTemp2 = cbor.get(
            ToObjectTest.TestToFromObjectRoundTrip (1))
          .AsInt32();
        Assert.assertEquals(2, varintTemp2);
      }
      {
        String stringTemp = cbor.ToJSONString();
        Assert.assertEquals(
          "{\"1\":2}",
          stringTemp);
      }
      cbor.Add ("hello", 2);
      if (!(cbor.ContainsKey ("hello"))) {
 Assert.fail();
 }

      if (!(cbor.ContainsKey (ToObjectTest.TestToFromObjectRoundTrip(
            "hello"))))Assert.fail();
      Assert.assertEquals((int)2, cbor.get("hello").AsInt32Value());
      cbor.Set (1, 3);
      CBORObject cborone = ToObjectTest.TestToFromObjectRoundTrip (1);
      if (!(cbor.ContainsKey (cborone))) {
 Assert.fail();
 }
      Assert.assertEquals((int)3, cbor.get(cborone).AsInt32Value());
    }

    @Test
    public void TestArray() {
      CBORObject cbor = CBORObject.FromJSONString ("[]");
      cbor.Add (ToObjectTest.TestToFromObjectRoundTrip (3));
      cbor.Add (ToObjectTest.TestToFromObjectRoundTrip (4));
      byte[] bytes = CBORTestCommon.CheckEncodeToBytes (cbor);
      TestCommon.AssertByteArraysEqual(
        new byte[] { (byte) ((byte)0x80 | 2), 3, 4 },
        bytes);
      cbor = CBORObject.FromObject (new String[] { "a", "b", "c",
        "d", "e",
      });
      Assert.assertEquals("[\"a\",\"b\",\"c\",\"d\",\"e\"]", cbor.ToJSONString());
      String[] strArray = (String[])cbor.ToObject (typeof (String[]));
      cbor = CBORObject.FromObject (strArray);
      Assert.assertEquals("[\"a\",\"b\",\"c\",\"d\",\"e\"]", cbor.ToJSONString());
      CBORTestCommon.AssertRoundTrip (cbor);
      cbor = CBORObject.DecodeFromBytes (new byte[] {
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
        EInteger bi = RandomObjects.RandomEInteger (r);
        CBORTestCommon.AssertJSONSer(
          ToObjectTest.TestToFromObjectRoundTrip (bi),
          bi.toString());

        if (!(
          ToObjectTest.TestToFromObjectRoundTrip(
            bi).AsNumber().IsInteger())) {
 Assert.fail();
 }

        CBORTestCommon.AssertRoundTrip(
          ToObjectTest.TestToFromObjectRoundTrip (bi));
        CBORTestCommon.AssertRoundTrip (ToObjectTest.TestToFromObjectRoundTrip(
            EDecimal.FromString (bi.toString() + "e1")));
      }
      EInteger[] ranges = {
        EInteger.FromString ("-9223372036854776320"),
        EInteger.FromString ("-9223372036854775296"),
        EInteger.FromString ("-512"),
        EInteger.FromString ("512"),
        EInteger.FromString ("9223372036854775295"),
        EInteger.FromString ("9223372036854776319"),
        EInteger.FromString ("18446744073709551103"),
        EInteger.FromString ("18446744073709552127"),
      };
      for (int i = 0; i < ranges.length; i += 2) {
        EInteger bigintTemp = ranges[i];
        while (true) {
          CBORTestCommon.AssertJSONSer(
            ToObjectTest.TestToFromObjectRoundTrip (bigintTemp),
            bigintTemp.toString());
          if (bigintTemp.equals (ranges[i + 1])) {
            break;
          }
          bigintTemp = bigintTemp.Add(EInteger.FromInt32(1));
        }
      }
    }

    @Test
    public void TestBigNumBytes() {
      CBORObject o = null;
      o = CBORTestCommon.FromBytesTestAB (new byte[] {
        (byte)0xc2, 0x41,
        (byte)0x88,
       });
      Assert.assertEquals(EInteger.FromRadixString ("88", 16),
        o.ToObject (typeof (EInteger)));
      o = CBORTestCommon.FromBytesTestAB (new byte[] {
        (byte)0xc2, 0x42,
        (byte)0x88,
        0x77,
       });
      Assert.assertEquals(EInteger.FromRadixString ("8877", 16),
        o.ToObject (typeof (EInteger)));
      o = CBORTestCommon.FromBytesTestAB (new byte[] {
        (byte)0xc2, 0x44,
        (byte)0x88, 0x77,
        0x66,
        0x55,
       });
      Assert.assertEquals(
        EInteger.FromRadixString ("88776655", 16),
        o.ToObject (typeof (EInteger)));
      o = CBORTestCommon.FromBytesTestAB (new byte[] {
        (byte)0xc2, 0x47,
        (byte)0x88, 0x77,
        0x66,
        0x55, 0x44, 0x33, 0x22,
       });
      Assert.assertEquals(
        EInteger.FromRadixString ("88776655443322", 16),
        o.ToObject (typeof (EInteger)));
    }

    @Test
    public void TestByte() {
      for (int i = 0; i <= 255; ++i) {
        CBORTestCommon.AssertJSONSer(
          ToObjectTest.TestToFromObjectRoundTrip ((byte)i),
          TestCommon.IntToString (i));
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
      TestWriteToJSON (CBORObject.DecodeFromBytes (bytes));
    }

    @Test
    public void TestEmptyIndefiniteLength() {
      CBORObject cbor;
      cbor = CBORObject.DecodeFromBytes (new byte[] { 0x5f, (byte)0xff });
      Assert.assertEquals(0, cbor.GetByteString().length);
      cbor = CBORObject.DecodeFromBytes (new byte[] { 0x7f, (byte)0xff });
      String str = cbor.AsString();
      Assert.assertEquals(0, str.length());
      cbor = CBORObject.DecodeFromBytes (new byte[] { (byte)0x9f, (byte)0xff });
      Assert.assertEquals(CBORType.Array, cbor.getType());
      Assert.assertEquals(0, cbor.size());
      cbor = CBORObject.DecodeFromBytes (new byte[] { (byte)0xbf, (byte)0xff });
      Assert.assertEquals(CBORType.Map, cbor.getType());
      Assert.assertEquals(0, cbor.size());
    }
    @Test
    public void TestByteStringStreamNoIndefiniteWithinDefinite() {
      try {
        CBORTestCommon.FromBytesTestAB (new byte[] {
          0x5f, 0x41, 0x20, 0x5f,
          0x41, 0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          0x5f, 0x5f, 0x42, 0x20,
          0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          0x5f, 0x42, 0x20, 0x20,
          0x5f, 0x42, 0x20, 0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          0x5f, 0x7f, 0x62, 0x20,
          0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          0x5f, 0x5f, 0x41, 0x20,
          (byte)0xff, 0x41, 0x20, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          0x7f, 0x7f, 0x62, 0x20,
          0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          0x7f, 0x62, 0x20, 0x20,
          0x7f, 0x62, 0x20, 0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          0x7f, 0x5f, 0x42, 0x20,
          0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          0x7f, 0x7f, 0x61, 0x20,
          (byte)0xff, 0x61, 0x20, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] { 0x5f, 0x00, (byte)0xff });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] { 0x7f, 0x00, (byte)0xff });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] { 0x5f, 0x20, (byte)0xff });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] { 0x7f, 0x20, (byte)0xff });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          (byte)0xbf, 0x00,
          (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (new byte[] {
          (byte)0xbf, 0x20,
          (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestByteStringStreamNoTagsBeforeDefinite() {
      try {
        CBORTestCommon.FromBytesTestAB (new byte[] {
          0x5f, 0x41, 0x20,
          (byte)0xc2, 0x41, 0x20, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    public static String ObjectMessage(CBORObject obj) {
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      return new StringBuilder()
        .append ("CBORObject.DecodeFromBytes(")
        .append (TestCommon.ToByteArrayString (obj.EncodeToBytes()))
        .append ("); /").append ("/ ").append (obj.ToJSONString()).toString();
    }

    @Test
    public void TestCanFitIn() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 5000; ++i) {
        CBORObject ed = CBORTestCommon.RandomNumber (r);
        EDecimal ed2;

        ed2 = EDecimal.FromDouble (AsED (ed).ToDouble());
        if ((AsED (ed).compareTo (ed2) == 0) !=
          ed.AsNumber().CanFitInDouble()) {
          Assert.fail (ObjectMessage (ed));
        }
        ed2 = EDecimal.FromSingle (AsED (ed).ToSingle());
        if ((AsED (ed).compareTo (ed2) == 0) !=
          ed.AsNumber().CanFitInSingle()) {
          Assert.fail (ObjectMessage (ed));
        }
        if (!ed.AsNumber().IsInfinity() && !ed.AsNumber().IsNaN()) {
          ed2 = EDecimal.FromEInteger (AsED (ed).ToEInteger());
          if ((AsED (ed).compareTo (ed2) == 0) != ed.AsNumber().IsInteger()) {
            Assert.fail (ObjectMessage (ed));
          }
        }
        if (!ed.AsNumber().IsInfinity() && !ed.AsNumber().IsNaN()) {
          EInteger bi = AsEI (ed);
          if (ed.AsNumber().IsInteger()) {
            if ((bi.GetSignedBitLengthAsEInteger().ToInt32Checked() <= 31) !=
              ed.AsNumber().CanFitInInt32()) {
              Assert.fail (ObjectMessage (ed));
            }
          }
          if ((bi.GetSignedBitLengthAsEInteger().ToInt32Checked() <= 31) !=
            ed.AsNumber().CanTruncatedIntFitInInt32()) {
            Assert.fail (ObjectMessage (ed));
          }
          if (ed.AsNumber().IsInteger()) {
            if ((bi.GetSignedBitLengthAsEInteger().ToInt32Checked() <= 63) !=
              ed.AsNumber().CanFitInInt64()) {
              Assert.fail (ObjectMessage (ed));
            }
          }
          if ((bi.GetSignedBitLengthAsEInteger().ToInt32Checked() <= 63) !=
            ed.AsNumber().CanTruncatedIntFitInInt64()) {
            Assert.fail (ObjectMessage (ed));
          }
        }
      }
    }

    @Test
    public void TestCanFitInSpecificCases() {
      CBORObject cbor = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xfb,
        0x41, (byte)0xe0, (byte)0x85, 0x48, 0x2d, 0x14, 0x47, 0x7a,
       }); // 2217361768.63373
      Assert.assertEquals(
        EInteger.FromString ("2217361768"),
        cbor.ToObject (typeof (EInteger)));

      if (
        AsEI (cbor).GetSignedBitLengthAsEInteger().ToInt32Checked()
        <= 31) {
 Assert.fail();
 }
      if (cbor.AsNumber().CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      cbor = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xc5, (byte)0x82,
        0x18, 0x2f, 0x32,
       }); // -2674012278751232
      {
        int intTemp = AsEI (cbor)
        .GetSignedBitLengthAsEInteger().ToInt32Checked();
        Assert.assertEquals(52, intTemp);
      }
      if (!(cbor.AsNumber().CanFitInInt64())) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip (2554895343L)
        .AsNumber().CanFitInSingle()) {
 Assert.fail();
 }
      cbor = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xc5, (byte)0x82,
        0x10, 0x38, 0x64,
       }); // -6619136
      Assert.assertEquals(EInteger.FromString ("-6619136"),
        cbor.ToObject (typeof (EInteger)));
      Assert.assertEquals(-6619136, cbor.AsInt32());
      if (!(cbor.AsNumber().CanTruncatedIntFitInInt32())) {
 Assert.fail();
 }
    }

    @Test
    public void TestCBOREInteger() {
      CBORObject o = CBORObject.DecodeFromBytes (new byte[] {
        0x3b, (byte)0xce,
        (byte)0xe2, 0x5a, 0x57, (byte)0xd8, 0x21, (byte)0xb9, (byte)0xa7,
       });
      Assert.assertEquals(
        EInteger.FromString ("-14907577049884506536"),
        o.ToObject (typeof (EInteger)));
    }

    @Test
    public void TestCBORExceptions() {
      try {
        CBORObject.NewArray().Remove (null);
        Assert.fail ("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().Remove (null);
        Assert.fail ("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().Add (CBORObject.Null);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().Add (CBORObject.True);
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.Remove (CBORObject.True);
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip (0).Remove (CBORObject.True);
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip ("")
        .Remove (CBORObject.True);
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().ToObject (typeof (EFloat));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject (typeof (EFloat));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject (typeof (EFloat));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject (typeof (EFloat));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject (typeof (EFloat));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          "").ToObject (typeof (EFloat));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCBORFromArray() {
      CBORObject o = CBORObject.FromObject (new int[] { 1, 2, 3 });
      Assert.assertEquals(3, o.size());
      Assert.assertEquals(1, o.get(0).AsInt32());
      Assert.assertEquals(2, o.get(1).AsInt32());
      Assert.assertEquals(3, o.get(2).AsInt32());
      CBORTestCommon.AssertRoundTrip (o);
    }

    @Test
    public void TestCBORInfinityRoundTrip() {
      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.FloatNegInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.RatPosInf));

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
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.FloatNegInf)
        .AsNumber().IsNegativeInfinity())) {
 Assert.fail();
 }

      if (!(
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.RatPosInf)
        .AsNumber().IsPositiveInfinity())) {
 Assert.fail();
 }
      if (!(
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.RatPosInf)
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
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.DecNegInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.FloatNegInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (Double.NEGATIVE_INFINITY));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (Float.NEGATIVE_INFINITY));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.DecPosInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.FloatPosInf));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (Double.POSITIVE_INFINITY));

      CBORTestCommon.AssertRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip (Float.POSITIVE_INFINITY));
    }

    @Test
    public void TestCompareB() {
      {
        String stringTemp = CBORObject.DecodeFromBytes (new byte[] {
          (byte)0xfa, 0x7f, (byte)0x80, 0x00, 0x00,
         }).ToObject (typeof (ERational)).toString();
        Assert.assertEquals(
          "Infinity",
          stringTemp);
      }
      {
        CBORObject objectTemp = CBORObject.DecodeFromBytes (new byte[] {
          (byte)0xc5, (byte)0x82, 0x38, (byte)0xc7, 0x3b, 0x00, 0x00, 0x08,
          (byte)0xbf, (byte)0xda, (byte)0xaf, 0x73, 0x46,
         });
        CBORObject objectTemp2 = CBORObject.DecodeFromBytes (new byte[] {
          0x3b, 0x5a, (byte)0x9b, (byte)0x9a, (byte)0x9c, (byte)0xb4, (byte)0x95,
          (byte)0xbf, 0x71,
         });
        AddSubCompare (objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp = CBORObject.DecodeFromBytes (new byte[] {
          (byte)0xfa, 0x1f, (byte)0x80, (byte)0xdb, (byte)0x9b,
         });
        CBORObject objectTemp2 = CBORObject.DecodeFromBytes (new byte[] {
          (byte)0xfb, 0x31, (byte)0x90, (byte)0xea, 0x16, (byte)0xbe, (byte)0x80,
          0x0b, 0x37,
         });
        AddSubCompare (objectTemp, objectTemp2);
      }
      CBORObject cbor = CBORObject.FromObjectAndTag(
          Double.NEGATIVE_INFINITY,
          1956611);
      CBORTestCommon.AssertRoundTrip (cbor);
      cbor =

        CBORObject.FromObjectAndTag(
          ToObjectTest.TestToFromObjectRoundTrip (Double.NEGATIVE_INFINITY),
          1956611);
      CBORTestCommon.AssertRoundTrip (cbor);
      cbor =

        CBORObject.FromObjectAndTag(
          ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.FloatNegInf),
          1956611);
      CBORTestCommon.AssertRoundTrip (cbor);
      cbor =

        CBORObject.FromObjectAndTag(
          ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.DecNegInf),
          1956611);
      CBORTestCommon.AssertRoundTrip (cbor);
      cbor =

        CBORObject.FromObjectAndTag(
          ToObjectTest.TestToFromObjectRoundTrip (CBORTestCommon.FloatNegInf),
          1956611);
      CBORTestCommon.AssertRoundTrip (cbor);
    }

    @Test
    public void TestEquivJSON() {
      byte[] jsonBytes = new byte[] {
        0x22, 0x48, 0x54, 0x30, 0x43, 0x5c, 0x75,
        0x64, 0x61, 0x62, 0x43, 0x5c, 0x75, 0x64, 0x64, 0x32, 0x39, 0x48,
        (byte)0xdc,
        (byte)0x9a, 0x4e,
        (byte)0xc2, (byte)0xa3, 0x49, 0x4d, 0x43, 0x40, 0x25, 0x31, 0x3b, 0x22,
       };
      this.TestEquivJSONOne (jsonBytes);
      jsonBytes = new byte[] {
        0x22, 0x35, 0x54, 0x30, 0x4d, 0x2d, 0x2b, 0x5c,
        0x75, 0x64, 0x38, 0x36, 0x38, 0x5c, 0x75, 0x44, 0x63, 0x46, 0x32, 0x4f,
        0x34, 0x4e, 0x34,
        (byte)0xe0, (byte)0xa3, (byte)0xac, 0x2b, 0x31, 0x23, 0x22,
       };
      this.TestEquivJSONOne (jsonBytes);
    }

    public void TestEquivJSONOne(byte[] bytes) {
      CBORObject cbo = CBORObject.FromJSONBytes (bytes);
      if (!(cbo != null)) {
 Assert.fail();
 }
      CBORObject cbo2 = CBORObject.FromJSONString (cbo.ToJSONString());
      if (!(cbo2 != null)) {
 Assert.fail();
 }
      if (!cbo.equals (cbo2)) {
        Assert.assertEquals("differs for JSONString",cbo,cbo2);
      }
      cbo2 = CBORObject.FromJSONBytes (cbo.ToJSONBytes());
      if (!(cbo2 != null)) {
 Assert.fail();
 }
      if (!cbo.equals (cbo2)) {
        Assert.assertEquals("differs for JSONBytes",cbo,cbo2);
      }
    }

    @Test
    public void TestDecFracCompareIntegerVsBigFraction() {
      CBORObject o1 = null;
      CBORObject o2 = null;
      o1 = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xfb, (byte)0x8b,
        0x44,
        (byte)0xf2, (byte)0xa9, 0x0c, 0x27, 0x42, 0x28,
       });
      o2 = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xc5, (byte)0x82,
        0x38,
        (byte)0xa4, (byte)0xc3, 0x50, 0x02, (byte)0x98, (byte)0xc5, (byte)0xa8,
        0x02, (byte)0xc1, (byte)0xf6, (byte)0xc0, 0x1a, (byte)0xbe, 0x08,
        0x04, (byte)0x86, (byte)0x99, 0x3e, (byte)0xf1,
       });
      AddSubCompare (o1, o2);
    }

    @Test
    public void TestDecimalFrac() {
      CBORObject obj = CBORTestCommon.FromBytesTestAB(
          new byte[] { (byte)0xc4, (byte)0x82, 0x3, 0x1a, 1, 2, 3, 4 });
      try {
        System.out.println ("" + obj.ToObject (typeof (EDecimal)));
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestDecimalFracExactlyTwoElements() {
      CBORObject obj = CBORTestCommon.FromBytesTestAB (new byte[] {
        (byte)0xc4, (byte)0x81,
        (byte)0xc2, 0x41,
        1,
       });
      try {
        System.out.println ("" + obj.ToObject (typeof (EDecimal)));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestDecimalFracExponentMustNotBeBignum() {
      CBORObject obj = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xc4,
        (byte)0x82,
        (byte)0xc2, 0x41, 1,
        0x1a,
        1, 2, 3, 4,
       });
      try {
        System.out.println ("" + obj.ToObject (typeof (EDecimal)));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestBigFloatExponentMustNotBeBignum() {
      CBORObject cbor = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xc5,
        (byte)0x82,
        (byte)0xc2, 0x41, 1,
        0x1a,
        1, 2, 3, 4,
       });
      try {
        System.out.println ("" + cbor.ToObject (typeof (EFloat)));
        Assert.fail ("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestDecimalFracMantissaMayBeBignum() {
      CBORObject o = CBORTestCommon.FromBytesTestAB(
          new byte[] { (byte)0xc4, (byte)0x82, 0x3, (byte)0xc2, 0x41, 1 });
      Assert.assertEquals(
        EDecimal.FromString ("1e3"),
        o.ToObject (typeof (EDecimal)));
    }

    @Test
    public void TestBigFloatFracMantissaMayBeBignum() {
      CBORObject o = CBORTestCommon.FromBytesTestAB(
          new byte[] { (byte)0xc5, (byte)0x82, 0x3, (byte)0xc2, 0x41, 1 });
      {
        long numberTemp = EFloat.FromString ("8").compareTo(
            (EFloat)o.ToObject (typeof (EFloat)));

        Assert.assertEquals(0, numberTemp);
      }
    }

    @Test
    public void TestDivide() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 =
          ToObjectTest.TestToFromObjectRoundTrip(
            RandomObjects.RandomEInteger (r));

        CBORObject o2 = ToObjectTest.TestToFromObjectRoundTrip(
            RandomObjects.RandomEInteger (r));

        if (o2.AsNumber().IsZero()) {
          continue;
        }
        ERational er = ERational.Create (AsEI (o1), AsEI (o2));
        {
          ERational objectTemp = er;
          ERational objectTemp2;
          CBORNumber cn = CBORObject.FromObject (o1).AsNumber()
            .Divide (CBORObject.FromObject (o2).AsNumber());
          objectTemp2 = cn.ToERational();
          TestCommon.CompareTestEqual (objectTemp, objectTemp2);
        }
      }
    }

    @Test
    public void TestCBORCompareTo() {
      int cmp = CBORObject.FromObject (0).compareTo (null);
      if (cmp <= 0) {
        Assert.fail();
      }
      cmp = CBORObject.FromObject (0).AsNumber().compareTo (null);
      if (cmp <= 0) {
        Assert.fail();
      }
    }

    @Test
    public void TestDouble() {
      if (!ToObjectTest.TestToFromObjectRoundTrip(
          Double.POSITIVE_INFINITY).AsNumber().IsPositiveInfinity()) {
        Assert.fail ("Not positive infinity");
      }

      Assert.assertTrue(
        (
          (EDecimal)ToObjectTest.TestToFromObjectRoundTrip(
            Double.POSITIVE_INFINITY)
          .ToObject (typeof (EDecimal))).IsPositiveInfinity());

      Assert.assertTrue(
        (
          (EDecimal)ToObjectTest.TestToFromObjectRoundTrip(
            Double.NEGATIVE_INFINITY)
          .ToObject (typeof (EDecimal))).IsNegativeInfinity());
      Assert.assertTrue(
        ((EDecimal)ToObjectTest.TestToFromObjectRoundTrip (Double.NaN)

          .ToObject (typeof (EDecimal))).IsNaN());
      for (int i = -65539; i <= 65539; ++i) {
        CBORObject o = ToObjectTest.TestToFromObjectRoundTrip ((double)i);
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
          TestCommon.IntToString (i));
      }
    }
    @Test
    public void TestDoubleCompare() {
      CBORObject oldobj = null;
      for (int i = -65539; i <= 65539; ++i) {
        CBORObject o = ToObjectTest.TestToFromObjectRoundTrip ((double)i);
        if (oldobj != null) {
          TestCommon.CompareTestLess (oldobj.AsNumber(), o.AsNumber());
        }
        oldobj = o;
      }
    }

    @Test
    public void TestExample() {
      // The following creates a CBOR map and adds
      // several kinds of objects to it
      CBORObject cbor = CBORObject.NewMap().Add ("item", "any String")
        .Add ("number", 42).Add ("map", CBORObject.NewMap().Add ("number", 42))
        .Add ("array", CBORObject.NewArray().Add (999f).Add ("xyz"))
        .Add ("bytes", new byte[] { 0, 1, 2 });
      // The following converts the map to CBOR
      CBORTestCommon.CheckEncodeToBytes (cbor);
      // The following converts the map to JSON
      cbor.ToJSONString();
    }

    @Test
    [Timeout (5000)]
    public void TestExtendedExtremeExponent() {
      // Values with extremely high or extremely low exponents;
      // we just check whether this test method runs reasonably fast
      // for all these test cases
      CBORObject obj;
      obj = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xc4, (byte)0x82,
        0x3a, 0x00, 0x1c, 0x2d, 0x0d, 0x1a, 0x13, 0x6c, (byte)0xa1,
        (byte)0x97,
       });
      CBORTestCommon.AssertRoundTrip (obj);
      obj = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xda, 0x00, 0x14,
        0x57, (byte)0xce, (byte)0xc5, (byte)0x82, 0x1a, 0x46, 0x5a, 0x37,
        (byte)0x87, (byte)0xc3, 0x50, 0x5e, (byte)0xec, (byte)0xfd, 0x73,
        0x50, 0x64, (byte)0xa1, 0x1f, 0x10, (byte)0xc4, (byte)0xff,
        (byte)0xf2, (byte)0xc4, (byte)0xc9, 0x65, 0x12,
       });
      CBORTestCommon.AssertRoundTrip (obj);
    }

    @Test
    [Timeout (5000)]
    public void TestExtendedExtremeExponentCompare() {
      CBORObject cbor1 = ToObjectTest.TestToFromObjectRoundTrip(
          EDecimal.FromString ("333333e-2"));
      CBORObject cbor2 = ToObjectTest.TestToFromObjectRoundTrip(
          EFloat.Create(
            EInteger.FromString ("5234222"),
            EInteger.FromString ("-24936668661488")));
      TestCommon.CompareTestGreater (cbor1.AsNumber(), cbor2.AsNumber());
    }

    @Test
    public void TestFloat() {
      Assert.assertTrue(
        (
          (EDecimal)ToObjectTest.TestToFromObjectRoundTrip(
            Float.POSITIVE_INFINITY)
          .ToObject (typeof (EDecimal))).IsPositiveInfinity());
      Assert.assertTrue(
        (
          (EDecimal)ToObjectTest.TestToFromObjectRoundTrip(
            Float.NEGATIVE_INFINITY)
          .ToObject (typeof (EDecimal))).IsNegativeInfinity());
      Assert.assertTrue(
        ((EDecimal)ToObjectTest.TestToFromObjectRoundTrip (Float.NaN)

          .ToObject (typeof (EDecimal))).IsNaN());
      for (int i = -65539; i <= 65539; ++i) {
        CBORObject o = ToObjectTest.TestToFromObjectRoundTrip ((float)i);
        // System.out.print("jsonser i=" + (// i) + " o=" + (o.toString()) + " json=" +
        // (o.ToJSONString()) + " type=" + (o.getType()));
        CBORTestCommon.AssertJSONSer(
          o,
          TestCommon.IntToString (i));
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
      if (!(Float.isNaN (o.AsSingle())))Assert.fail();
    }

    @Test
    public void TestTag268() {
      CBORObject cbor;
      CBORObject cbortag;
      for (int tag = 268; tag <= 269; ++tag) {
        cbor = CBORObject.NewArray().Add (-3).Add (99999).Add (0);
        cbortag = CBORObject.FromObjectAndTag (cbor, tag);
        if (cbortag.AsNumber().IsNegative()) {
 Assert.fail();
 }
        cbor = CBORObject.NewArray().Add (-3).Add (99999);
        cbortag = CBORObject.FromObjectAndTag (cbor, tag);
        try {
          System.out.println ("" + cbortag.ToObject (typeof (EDecimal)));
          Assert.fail ("Should have failed " + cbortag.toString());
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        cbor = CBORObject.NewArray().Add (-3).Add (99999).Add (1);
        cbortag = CBORObject.FromObjectAndTag (cbor, tag);
        if (!(cbortag.AsNumber().IsNegative())) {
 Assert.fail();
 }
        cbor = CBORObject.NewArray().Add (-3).Add (99999).Add (-1);
        cbortag = CBORObject.FromObjectAndTag (cbor, tag);
        try {
          System.out.println ("" + cbortag.ToObject (typeof (EDecimal)));
          Assert.fail ("Should have failed " + cbortag.toString());
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        cbor = CBORObject.NewArray().Add (-3).Add (99999).Add (2);
        cbortag = CBORObject.FromObjectAndTag (cbor, tag);
        try {
          System.out.println ("" + cbortag.ToObject (typeof (EDecimal)));
          Assert.fail ("Should have failed " + cbortag.toString());
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        cbor = CBORObject.NewArray().Add (0).Add (0).Add (2);
        cbortag = CBORObject.FromObjectAndTag (cbor, tag);
        if (cbortag.AsNumber().IsNegative()) {
 Assert.fail();
 }
        cbor = CBORObject.NewArray().Add (0).Add (0).Add (3);
        cbortag = CBORObject.FromObjectAndTag (cbor, tag);
        if (!(cbortag.AsNumber().IsNegative())) {
 Assert.fail();
 }
        cbor = CBORObject.NewArray().Add (-3).Add (99999).Add (8);
        cbortag = CBORObject.FromObjectAndTag (cbor, tag);
        try {
          System.out.println ("" + cbortag.ToObject (typeof (EDecimal)));
          Assert.fail ("Should have failed " + cbortag.toString());
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
    }

    @Test
    public void TestJSON() {
      CBORObject o;
      o = CBORObject.FromJSONString ("[1,2,null,true,false,\"\"]");
      Assert.assertEquals(6, o.size());
      Assert.assertEquals(1, o.get(0).AsInt32());
      Assert.assertEquals(2, o.get(1).AsInt32());
      Assert.assertEquals(CBORObject.Null, o.get(2));
      Assert.assertEquals(CBORObject.True, o.get(3));
      Assert.assertEquals(CBORObject.False, o.get(4));
      Assert.assertEquals("", o.get(5).AsString());
      o = CBORObject.FromJSONString ("[1.5,2.6,3.7,4.0,222.22]");
      double actual = o.get(0).AsDouble();
      Assert.assertEquals((double)1.5, actual, 0);
      {
        java.io.ByteArrayInputStream ms2a = null;
try {
ms2a = new java.io.ByteArrayInputStream(new byte[] { });

        try {
          CBORObject.ReadJSON (ms2a);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
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
          CBORObject.ReadJSON (ms2b);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms2b != null) { ms2b.close(); } } catch (java.io.IOException ex) {}
}
}
      try {
        CBORObject.FromJSONString ("");
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString ("[.1]");
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString ("[-.1]");
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString ("\u0020");
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      {
        String stringTemp = CBORObject.FromJSONString ("true").ToJSONString();
        Assert.assertEquals(
          "true",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString (" true ").ToJSONString();
        Assert.assertEquals(
          "true",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString ("false").ToJSONString();
        Assert.assertEquals(
          "false",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString ("null").ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.FromJSONString ("5").ToJSONString();
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
      CBORTestCommon.AssertRoundTrip (o);
    }

    @Test
    [Timeout (100000)]
    public void TestLong() {
      long[] ranges = {
        -65539, 65539, 0xfffff000L, 0x100000400L,
          Long.MAX_VALUE - 1000,
          Long.MAX_VALUE,
          Long.MIN_VALUE,
          Long.MIN_VALUE + 1000,
        };
      for (int i = 0; i < ranges.length; i += 2) {
        long j = ranges[i];
        while (true) {
          CBORNumber cn = ToObjectTest.TestToFromObjectRoundTrip (j).AsNumber();
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
            ToObjectTest.TestToFromObjectRoundTrip (j),
            TestCommon.LongToString (j));
          Assert.assertEquals(
            ToObjectTest.TestToFromObjectRoundTrip (j),
            ToObjectTest.TestToFromObjectRoundTrip (EInteger.FromInt64 (j)));
          CBORObject obj = CBORObject.FromJSONString(
              "[" + TestCommon.LongToString (j) + "]");
          CBORTestCommon.AssertJSONSer(
            obj,
            "[" + TestCommon.LongToString (j) + "]");
          if (j == ranges[i + 1]) {
            break;
          }
          ++j;
        }
      }
    }

    @Test
    public void TestMap() {
      CBORObject cbor = CBORObject.FromJSONString ("{\"a\":2,\"b\":4}");
      Assert.assertEquals(2, cbor.size());
      TestCommon.AssertEqualsHashCode(
        ToObjectTest.TestToFromObjectRoundTrip (2),
        cbor.get(ToObjectTest.TestToFromObjectRoundTrip ("a")));
      TestCommon.AssertEqualsHashCode(
        ToObjectTest.TestToFromObjectRoundTrip (4),
        cbor.get(ToObjectTest.TestToFromObjectRoundTrip ("b")));
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
      cbor = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xbf, 0x61, 0x61, 2,
        0x61, 0x62, 4, (byte)0xff,
       });
      Assert.assertEquals(2, cbor.size());
      TestCommon.AssertEqualsHashCode(
        ToObjectTest.TestToFromObjectRoundTrip (2),
        cbor.get(ToObjectTest.TestToFromObjectRoundTrip ("a")));
      TestCommon.AssertEqualsHashCode(
        ToObjectTest.TestToFromObjectRoundTrip (4),
        cbor.get(ToObjectTest.TestToFromObjectRoundTrip ("b")));
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
      oo = CBORObject.NewArray().Add (CBORObject.NewMap()
          .Add(
            ERational.Create (EInteger.FromInt32(1), EInteger.FromString ("2")),
            3).Add (4, false)).Add (true);
      CBORTestCommon.AssertRoundTrip (oo);
      oo = CBORObject.NewArray();
      oo.Add (ToObjectTest.TestToFromObjectRoundTrip (0));
      CBORObject oo2 = CBORObject.NewMap();
      oo2.Add(
        ToObjectTest.TestToFromObjectRoundTrip (1),
        ToObjectTest.TestToFromObjectRoundTrip (1368));
      CBORObject oo3 = CBORObject.NewMap();
      oo3.Add(
        ToObjectTest.TestToFromObjectRoundTrip (2),
        ToObjectTest.TestToFromObjectRoundTrip (1625));
      CBORObject oo4 = CBORObject.NewMap();
      oo4.Add (oo2, CBORObject.True);
      oo4.Add (oo3, CBORObject.True);
      oo.Add (oo4);
      CBORTestCommon.AssertRoundTrip (oo);
    }

    @Test
    public void TestParseDecimalStrings() {
      RandomGenerator rand = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        String r = RandomObjects.RandomDecimalString (rand);
        CBORObject o = ToObjectTest.TestToFromObjectRoundTrip(
            EDecimal.FromString (r));
        CBORObject o2 = CBORDataUtilities.ParseJSONNumber (r);
        TestCommon.CompareTestEqual (o.AsNumber(), o2.AsNumber());
      }
    }

    @Test
    [Timeout (50000)]
    public void TestRandomData() {
      RandomGenerator rand = new RandomGenerator();
      CBORObject obj;
      for (int i = 0; i < 1000; ++i) {
        obj = CBORTestCommon.RandomCBORObject (rand);
        CBORTestCommon.AssertRoundTrip (obj);
        String jsonString = "";
        try {
          jsonString = obj.ToJSONString();
        } catch (CBORException ex) {
          jsonString = "";
        }
        if (jsonString.length() > 0) {
          CBORObject.FromJSONString (jsonString);
          TestWriteToJSON (obj);
        }
      }
    }
    public static CBORObject ReferenceTestObject() {
      return ReferenceTestObject (50);
    }
    public static CBORObject ReferenceTestObject(int nests) {
      CBORObject root = CBORObject.NewArray();
      CBORObject arr = CBORObject.NewArray().Add ("xxx").Add ("yyy");
      arr.Add ("zzz")
      .Add ("wwww").Add ("iiiiiii").Add ("aaa").Add ("bbb").Add ("ccc");
      arr = CBORObject.FromObjectAndTag (arr, 28);
      root.Add (arr);
      CBORObject refobj;
      for (int i = 0; i <= nests; ++i) {
        refobj = CBORObject.FromObjectAndTag (i, 29);
        arr = CBORObject.FromObject (new CBORObject[] {
          refobj, refobj, refobj, refobj, refobj, refobj, refobj, refobj,
          refobj,
        });
        arr = CBORObject.FromObjectAndTag (arr, 28);
        root.Add (arr);
      }
      return root;
    }

    @Test
    [Timeout (5000)]
    public void TestCtap2CanonicalReferenceTest() {
      for (int i = 4; i <= 60; ++i) {
        // has high recursive reference depths, higher than
        // Ctap2Canonical supports, which is 4
        TestCtap2CanonicalReferenceTestOne (ReferenceTestObject (i));
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
      CBOREncodeOptions  encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes (bytes, encodeOptions);
      encodeOptions = new CBOREncodeOptions("ctap2canonical=true");
      if (root == null) {
        Assert.fail();
      }
      try {
        {
          java.io.ByteArrayOutputStream lms = null;
try {
lms = new java.io.ByteArrayOutputStream();

          root.WriteTo (lms, encodeOptions);
          Assert.fail ("Should have failed");
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    [Timeout (50000)]
    public void TestNoRecursiveExpansion() {
      for (int i = 5; i <= 60; ++i) {
        // has high recursive reference depths
        // System.Diagnostics.Stopwatch sw = new System.Diagnostics.Stopwatch();sw.Start();
        // System.out.println("depth = "+i);
        TestNoRecursiveExpansionOne (ReferenceTestObject (i));
        // System.out.println("elapsed=" + sw.getElapsedMilliseconds() + " ms");
      }
    }

    @Test
    [Timeout (50000)]
    public void TestNoRecursiveExpansionJSON() {
      for (int i = 5; i <= 60; ++i) {
        // has high recursive reference depths
        // System.Diagnostics.Stopwatch sw = new System.Diagnostics.Stopwatch();sw.Start();
        // System.out.println("depth = "+i);
        TestNoRecursiveExpansionJSONOne (ReferenceTestObject (i));
        // System.out.println("elapsed=" + sw.getElapsedMilliseconds() + " ms");
      }
    }

    public static void TestNoRecursiveExpansionOne(CBORObject root) {
      if (root == null) {
        throw new NullPointerException("root");
      }
      CBORObject origroot = root;
      byte[] bytes = CBORTestCommon.CheckEncodeToBytes (root);
      CBOREncodeOptions  encodeOptions = new CBOREncodeOptions("resolvereferences=false");
      root = CBORObject.DecodeFromBytes (bytes, encodeOptions);
      encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes (bytes, encodeOptions);
      if (root == null) {
        Assert.fail();
      }
      // Test a mitigation for wild recursive-reference expansions
      encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes (bytes, encodeOptions);
      if (root == null) {
        Assert.fail();
      }
      try {
        {
          LimitedMemoryStream lms = null;
try {
lms = new LimitedMemoryStream(100000);

          root.WriteTo (lms);
          Assert.fail ("Should have failed");
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (UnsupportedOperationException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        {
          LimitedMemoryStream lms = null;
try {
lms = new LimitedMemoryStream(100000);

          origroot.WriteTo (lms);
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    public static void TestNoRecursiveExpansionJSONOne(CBORObject root) {
      if (root == null) {
        throw new NullPointerException("root");
      }
      CBORObject origroot = root;
      byte[] bytes = CBORTestCommon.CheckEncodeToBytes (root);
      CBOREncodeOptions  encodeOptions = new CBOREncodeOptions("resolvereferences=false");
      root = CBORObject.DecodeFromBytes (bytes, encodeOptions);
      encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes (bytes, encodeOptions);
      if (root == null) {
        Assert.fail();
      }
      // Test a mitigation for wild recursive-reference expansions
      encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      root = CBORObject.DecodeFromBytes (bytes, encodeOptions);
      if (root == null) {
        Assert.fail();
      }
      try {
        {
          LimitedMemoryStream lms = null;
try {
lms = new LimitedMemoryStream(100000);

          root.WriteJSONTo (lms);
          Assert.fail ("Should have failed");
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (UnsupportedOperationException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        {
          LimitedMemoryStream lms = null;
try {
lms = new LimitedMemoryStream(100000);

          origroot.WriteJSONTo (lms);
}
finally {
try { if (lms != null) { lms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestSharedRefValidInteger() {
      byte[] bytes;
      CBOREncodeOptions  encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      // Shared ref is integer
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, 0x00,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, encodeOptions);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Shared ref is negative
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, 0x20,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, encodeOptions);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Shared ref is non-integer
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, (byte)0xc4, (byte)0x82,
        0x27, 0x19, (byte)0xff, (byte)0xff,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, encodeOptions);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Shared ref is non-number
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, 0x61, 0x41,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, encodeOptions);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Shared ref is out of range
      bytes = new byte[] {
        (byte)0x82, (byte)0xd8, 0x1c, 0x00, (byte)0xd8,
        0x1d, 0x01,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, encodeOptions);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static String ToByteArrayStringFrom(byte[] array, int pos) {
      byte[] newArray = new byte[array.length - pos];
      System.arraycopy (array, pos, newArray, 0, newArray.length);
      return TestCommon.ToByteArrayString (newArray);
    }

    @Test
    [Timeout (500000)]
    public void TestRandomNonsense() {
      RandomGenerator rand = new RandomGenerator();
      for (int i = 0; i < 1000; ++i) {
        byte[] array = new byte[rand.UniformInt(1000000) + 1];
        for (int j = 0; j < array.length; ++j) {
          if (j + 3 <= array.length) {
            int r = rand.UniformInt (0x1000000);
            array[j] = (byte) (r & (byte)0xff);
            array[j + 1] = (byte) ((r >> 8) & (byte)0xff);
            array[j + 2] = (byte) ((r >> 16) & (byte)0xff);
            j += 2;
          } else {
            array[j] = (byte)rand.UniformInt (256);
          }
        }
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(array);
int startingAvailable = ms.available();

          int iobj = 0;
          while (iobj < 25 && (startingAvailable - ms.available()) != startingAvailable) {
            ++iobj;
            int objpos = (int)(startingAvailable - ms.available());
            try {
              CBORObject o = CBORObject.Read (ms);
              try {
                if (o == null) {
                  Assert.fail ("Object read is null");
                } else {
                  CBORObject.DecodeFromBytes (o.EncodeToBytes());
                }
              } catch (Exception ex) {
                String failString = ex.toString() +
                  (ex.getCause() == null ? "" : "\n" +
                    ex.getCause().toString()) +
                  "\n" + ToByteArrayStringFrom (array, objpos);
                failString = failString.substring(
                    0, (
                    0)+(Math.min (2000, failString.length())));
                Assert.fail (failString);
                throw new IllegalStateException("", ex);
              }
              String jsonString = "";
              try {
                try {
                  jsonString = o.ToJSONString();
                } catch (CBORException ex) {
                  jsonString = "";
                }
                if (jsonString.length() > 0) {
                  CBORObject.FromJSONString (jsonString);
                  TestWriteToJSON (o);
                }
              } catch (Exception ex) {
                String failString = jsonString + "\n" + ex.toString() +
                  (ex.getCause() == null ? "" : "\n" +
                    ex.getCause().toString()) +
                  "\n" + ToByteArrayStringFrom (o.EncodeToBytes(), 0);
                failString = failString.substring(
                    0, (
                    0)+(Math.min (2000, failString.length())));
                Assert.fail (failString);
                throw new IllegalStateException("", ex);
              }
            } catch (CBORException ex) {
              // Expected exception
            } catch (Exception ex) {
              // if (!ex.getMessage().equals("Not a number type")) {
              String failString = ex.toString() +
                (ex.getCause() == null ? "" : "\n" +
                  ex.getCause().toString()) +
                "\n" + ToByteArrayStringFrom (array, objpos);
              failString = failString.substring(
                  0, (
                  0)+(Math.min (2000, failString.length())));
              Assert.fail (failString);
              throw new IllegalStateException("", ex);
              // }
            }
          }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      }
    }

    private static void TestRandomSlightlyModifiedOne(byte[] array,
      RandomGenerator rand) {
      if (array.length > 50000) {
        System.out.println ("" + array.length);
      }
      int count2 = rand.UniformInt (10) + 1;
      for (int j = 0; j < count2; ++j) {
        int index = rand.UniformInt (array.length);
        array[index] = ((byte)rand.UniformInt (256));
      }
      {
        java.io.ByteArrayInputStream inputStream = null;
try {
inputStream = new java.io.ByteArrayInputStream(array);
int startingAvailable = inputStream.available();

        while ((startingAvailable - inputStream.available()) != startingAvailable) {
          try {
            CBORObject o;
            o = CBORObject.Read (inputStream);
            byte[] encodedBytes = (o == null) ? null : o.EncodeToBytes();
            try {
              CBORObject.DecodeFromBytes (encodedBytes);
            } catch (Exception ex) {
              String failString = ex.toString() +
                (ex.getCause() == null ? "" : "\n" +
                  ex.getCause().toString());
              failString += "\n" + TestCommon.ToByteArrayString (array);
              failString = failString.substring(
                  0, (
                  0)+(Math.min (2000, failString.length())));
              Assert.fail (failString);
              throw new IllegalStateException("", ex);
            }
            String jsonString = "";
            try {
              if (o == null) {
                Assert.fail ("Object is null");
              }
              if (o != null) {
                try {
                  jsonString = o.ToJSONString();
                } catch (CBORException ex) {
                  System.out.println (ex.getMessage());
                  jsonString = "";
                }
                if (jsonString.length() > 0) {
                  CBORObject.FromJSONString (jsonString);
                  TestWriteToJSON (o);
                }
              }
            } catch (Exception ex) {
              String failString = jsonString + "\n" + ex +
                (ex.getCause() == null ? "" : "\n" +
                  ex.getCause().toString());
              failString += "\n" + TestCommon.ToByteArrayString (array);
              failString = failString.substring(
                  0, (
                  0)+(Math.min (2000, failString.length())));
              Assert.fail (failString);
              throw new IllegalStateException("", ex);
            }
          } catch (CBORException ex) {
            // Expected exception
            System.out.print (ex.getMessage().substring(0, 0));
          } catch (IllegalStateException ex) {
            String failString = ex.toString() +
              (ex.getCause() == null ? "" : "\n" +
                ex.getCause().toString());
            failString += "\n" + TestCommon.ToByteArrayString (array);
            failString = failString.substring(
                0, (
                0)+(Math.min (2000, failString.length())));
            Assert.fail (failString);
            throw new IllegalStateException("", ex);
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
        CBORObject originalObject = CBORTestCommon.RandomCBORObject (rand);
        byte[] array = originalObject.EncodeToBytes();
        TestRandomSlightlyModifiedOne (array, rand);
      }
    }

    private static void TestReadWriteIntOne(int val) {
      try {
        {
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            MiniCBOR.WriteInt32 (val, ms);
            byte[] msarray = ms.toByteArray();
            {
              java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(msarray);

              Assert.assertEquals(TestCommon.ToByteArrayString (msarray), val, MiniCBOR.ReadInt32 (ms2));
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

            CBORObject.Write (val, ms);
            byte[] msarray = ms.toByteArray();
            {
              java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(msarray);

              Assert.assertEquals(TestCommon.ToByteArrayString (msarray), val, MiniCBOR.ReadInt32 (ms2));
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
        Assert.fail (ioex.getMessage());
      }
    }

    @Test
    public void TestReadWriteInt() {
      RandomGenerator r = new RandomGenerator();
      for (int i = -70000; i < 70000; ++i) {
        TestReadWriteIntOne (i);
      }
      for (int i = 0; i < 100000; ++i) {
        int val = ((int)RandomObjects.RandomInt64 (r));
        TestReadWriteIntOne (val);
      }
    }

    @Test
    public void TestShort() {
      for (int i = Short.MIN_VALUE; i <= Short.MAX_VALUE; ++i) {
        CBORTestCommon.AssertJSONSer(
          ToObjectTest.TestToFromObjectRoundTrip ((short)i),
          TestCommon.IntToString (i));
      }
    }

    @Test
    public void TestSimpleValues() {
      CBORTestCommon.AssertJSONSer(
        ToObjectTest.TestToFromObjectRoundTrip (true),
        "true");
      CBORTestCommon.AssertJSONSer(
        ToObjectTest.TestToFromObjectRoundTrip (false),
        "false");
      CBORTestCommon.AssertJSONSer(
        ToObjectTest.TestToFromObjectRoundTrip ((Object)null),
        "null");
    }

    @Test
    public void TestCtap2NestingLevel() {
      CBORObject o;
      CBOREncodeOptions  ctap = new CBOREncodeOptions("ctap2canonical=true");
      // 1 nesting level
      o = CBORObject.FromJSONString ("[]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 1 nesting level
      o = CBORObject.FromJSONString ("[0]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 3 nesting levels
      o = CBORObject.FromJSONString ("[[[]]]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 4 nesting levels
      o = CBORObject.FromJSONString ("[[[[]]]]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 5 nesting levels
      o = CBORObject.FromJSONString ("[[[[[]]]]]");
      try {
        o.EncodeToBytes (ctap);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // 4 nesting levels
      o = CBORObject.FromJSONString ("[[[[0]]]]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 1 nesting level
      o = CBORObject.FromJSONString ("[]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 3 nesting levels
      o = CBORObject.FromJSONString ("[[[]]]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 4 nesting levels
      o = CBORObject.FromJSONString ("[[{\"x\": []}]]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 5 nesting levels
      o = CBORObject.FromJSONString ("[[[{\"x\": []}]]]");
      try {
        o.EncodeToBytes (ctap);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // 4 nesting levels
      o = CBORObject.FromJSONString ("[[[{\"x\": 0}]]]");
      if (o.EncodeToBytes (ctap) == null) {
        Assert.fail();
      }
      // 5 nesting levels
      o = CBORObject.FromJSONString ("[[[[{\"x\": 0}]]]]");
      try {
        o.EncodeToBytes (ctap);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestAsNumberMultiply() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 = CBORTestCommon.RandomNumber (r);
        CBORObject o2 = CBORTestCommon.RandomNumber (r);
        EDecimal cmpDecFrac = AsED (o1).Multiply (AsED (o2));
        EDecimal cmpCobj = o1.AsNumber().Multiply (o2.AsNumber()).ToEDecimal();
        if (cmpDecFrac.compareTo (cmpCobj) != 0) {
          String msg = "o1=" + o1.toString() + ", o2=" + o2.toString() +
            ", " + AsED (o1) + ", " + AsED (o2) + ", cmpCobj=" +
            cmpCobj.toString() + ", cmpDecFrac=" + cmpDecFrac.toString();
          TestCommon.CompareTestEqual (cmpDecFrac, cmpCobj, msg);
        }
        CBORTestCommon.AssertRoundTrip (o1);
        CBORTestCommon.AssertRoundTrip (o2);
      }
    }

    @Test
    public void TestAsNumberAdd() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 = CBORTestCommon.RandomNumber (r);
        CBORObject o2 = CBORTestCommon.RandomNumber (r);
        EDecimal cmpDecFrac = AsED (o1).Add (AsED (o2));
        EDecimal cmpCobj = o1.AsNumber().Add (o2.AsNumber()).ToEDecimal();
        if (cmpDecFrac.compareTo (cmpCobj) != 0) {
          String msg = "o1=" + o1.toString() + ", o2=" + o2.toString() +
            ", " + AsED (o1) + ", " + AsED (o2) + ", cmpCobj=" +
            cmpCobj.toString() + ", cmpDecFrac=" + cmpDecFrac.toString();
          TestCommon.CompareTestEqual (cmpDecFrac, cmpCobj, msg);
        }
        CBORTestCommon.AssertRoundTrip (o1);
        CBORTestCommon.AssertRoundTrip (o2);
      }
    }

    @Test
    public void TestAsNumberSubtract() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 = CBORTestCommon.RandomNumber (r);
        CBORObject o2 = CBORTestCommon.RandomNumber (r);
        EDecimal cmpDecFrac = AsED (o1).Subtract (AsED (o2));
        EDecimal cmpCobj = o1.AsNumber().Subtract (o2.AsNumber()).ToEDecimal();
        if (cmpDecFrac.compareTo (cmpCobj) != 0) {
          String msg = "o1=" + o1.toString() + ", o2=" + o2.toString() +
            ", " + AsED (o1) + ", " + AsED (o2) + ", cmpCobj=" +
            cmpCobj.toString() + ", cmpDecFrac=" + cmpDecFrac.toString();
          TestCommon.CompareTestEqual (cmpDecFrac, cmpCobj, msg);
        }
        CBORTestCommon.AssertRoundTrip (o1);
        CBORTestCommon.AssertRoundTrip (o2);
      }
    }

    @Test
    [Timeout (10000)]
    public void TestTaggedUntagged() {
      for (int i = 200; i < 1000; ++i) {
        CBORObject o, o2;
        o = ToObjectTest.TestToFromObjectRoundTrip (0);
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o =
          ToObjectTest.TestToFromObjectRoundTrip (EInteger.FromString(
              "999999999999999999999999999999999"));
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = ToObjectTest.TestToFromObjectRoundTrip (new byte[] { 1, 2, 3 });
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.NewArray();
        o.Add (ToObjectTest.TestToFromObjectRoundTrip (0));
        o.Add (ToObjectTest.TestToFromObjectRoundTrip (1));
        o.Add (ToObjectTest.TestToFromObjectRoundTrip (2));
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.NewMap();
        o.Add ("a", ToObjectTest.TestToFromObjectRoundTrip (0));
        o.Add ("b", ToObjectTest.TestToFromObjectRoundTrip (1));
        o.Add ("c", ToObjectTest.TestToFromObjectRoundTrip (2));
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = ToObjectTest.TestToFromObjectRoundTrip ("a");
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.False;
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.True;
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.Null;
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.Undefined;
        o2 = CBORObject.FromObjectAndTag (o, i);
        TestCommon.AssertEqualsHashCode (o, o2);
        o = CBORObject.FromObjectAndTag (o, i + 1);
        TestCommon.AssertEqualsHashCode (o, o2);
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
      if (oexp == null ? oact != null : !oexp.equals (oact)) {
        Assert.assertEquals(oexp, oact);
      }
    }
    private static void AssertEquals(Object oexp, Object oact, String str) {
      // Much less overhead than Assert alone if the
      // two arguments are equal
      if (oexp == null ? oact != null : !oexp.equals (oact)) {
        Assert.assertEquals(str, oexp, oact);
      }
    }

    @Test
    [Timeout (15000)]
    public void TestTags() {
      EInteger maxuint = EInteger.FromString ("18446744073709551615");
      EInteger[] ranges = {
        EInteger.FromString ("37"),
        EInteger.FromString ("65539"),
        EInteger.FromString ("2147483147"),
        EInteger.FromString ("2147484147"),
        EInteger.FromString ("9223372036854775307"),
        EInteger.FromString ("9223372036854776307"),
        EInteger.FromString ("18446744073709551115"),
        EInteger.FromString ("18446744073709551615"),
      };
      if (CBORObject.True.isTagged()) {
 Assert.fail();
 }
      CBORObject trueObj = CBORObject.True;
      AssertEquals(
        EInteger.FromString ("-1"),
        trueObj.getMostInnerTag());
      EInteger[] tagstmp = CBORObject.True.GetAllTags();
      for (int i = 0; i < ranges.length; i += 2) {
        EInteger bigintTemp = ranges[i];
        while (true) {
          EInteger ei = bigintTemp;
          EInteger bigintNext = ei.Add (EInteger.FromInt32(1));
          if (bigintTemp.GetSignedBitLengthAsEInteger().ToInt32Checked() <=
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
          CBORObject obj = CBORObject.FromObjectAndTag (0, bigintTemp);
          if (!obj.isTagged()) {
            Assert.fail ("obj not tagged");
          }
          EInteger[] tags = obj.GetAllTags();
          AssertEquals (1, tags.length);
          AssertEquals (bigintTemp, tags[0]);
          if (!obj.getMostInnerTag().equals (bigintTemp)) {
            String errmsg = "obj tag doesn't match: " + obj;
            AssertEquals(
              bigintTemp,
              obj.getMostInnerTag(),
              errmsg);
          }
          tags = obj.GetAllTags();
          AssertEquals (1, tags.length);
          AssertEquals (bigintTemp, obj.getMostOuterTag());
          AssertEquals (bigintTemp, obj.getMostInnerTag());
          AssertEquals (0, obj.AsInt32Value());
          if (!bigintTemp.equals (maxuint)) {
            EInteger bigintNew = bigintNext;
            // Test multiple tags
            CBORObject obj2 = CBORObject.FromObjectAndTag (obj, bigintNew);
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
            if (!obj2.getMostInnerTag().equals ((Object)bigintTemp)) {
              {
                String stringTemp = "Innermost tag doesn't match: " + obj2;
                AssertEquals(
                  bigintTemp,
                  obj2.getMostInnerTag(),
                  stringTemp);
              }
            }
            EInteger[] tags2 = obj2.GetAllTags();
            AssertEquals (2, tags2.length);
            AssertEquals (bigintNext, obj2.getMostOuterTag());
            AssertEquals (bigintTemp, obj2.getMostInnerTag());
            AssertEquals (0, obj2.AsInt32Value());
          }
          if (bigintTemp.compareTo (ranges[i + 1]) >= 0) {
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
          CBORObject.DecodeFromBytes (bytes);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
    }

    @Test
    public void TestDecodeCtap2Canonical() {
      // Tests that the code rejects noncanonical data
      CBOREncodeOptions  options = new CBOREncodeOptions("ctap2canonical=1");
      if (!(options.getCtap2Canonical())) {
 Assert.fail();
 }
      byte[] bytes;
      for (int i = 0; i < 2; ++i) {
        int eb = i == 0 ? 0 : 0x20;
        bytes = new byte[] { (byte)eb };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x17 + eb) };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x18 + eb), 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x19 + eb), 0, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x1a + eb), 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x1b + eb), 0, 0, 0, 0, 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x18 + eb), 0x17 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x18 + eb), 0x18 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x19 + eb), 0, (byte)0xff };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x19 + eb), 1, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x1a + eb), 0, 0, (byte)0xff, (byte)0xff };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x1a + eb), 0, 1, 0, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] {
          (byte) (0x1b + eb), 0, 0, 0, 0, (byte)0xff,
          (byte)0xff, (byte)0xff, (byte)0xff,
         };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x1b + eb), 0, 0, 0, 1, 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
      for (int i = 2; i <= 5; ++i) {
        int eb = 0x20 * i;
        bytes = new byte[] { (byte)eb };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x18 + eb), 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x19 + eb), 0, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x1a + eb), 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
        bytes = new byte[] { (byte) (0x1b + eb), 0, 0, 0, 0, 0, 0, 0, 0 };
        try {
          CBORObject.DecodeFromBytes (bytes, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
      bytes = new byte[] { (byte)0xc0, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xd7, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xd8, (byte)0xff, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xd9, (byte)0xff, (byte)0xff, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0xda, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, 0,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0xdb, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, 0,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Nesting depth
      bytes = new byte[] { (byte)0x81, (byte)0x81, (byte)0x81, (byte)0x80 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0x81, (byte)0x81, (byte)0x81, (byte)0x81, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0x81, (byte)0x81, (byte)0x81, (byte)0xa1,
        0, 0,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0x81, (byte)0x81, (byte)0x81, (byte)0x81,
        (byte)0x80,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0x81, (byte)0x81, (byte)0x81, (byte)0xa1,
        0, 0,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0x81, (byte)0x81, (byte)0x81, (byte)0x81,
        (byte)0xa0,
       };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Floating Point Numbers
      bytes = new byte[] { (byte)0xf9, 8, 8 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xfa, 8, 8, 8, 8 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xfb, 8, 8, 8, 8, 8, 8, 8, 8 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Map Key Ordering
      bytes = new byte[] { (byte)0xa2, 0, 0, 1, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 1, 0, 0, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0, 0, 0x20, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x20, 0, 0, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0, 0, 0x38, (byte)0xff, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x38, (byte)0xff, 0, 0, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x41, (byte)0xff, 0, 0x42, 0, 0, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x42, 0, 0, 0, 0x41, (byte)0xff, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x61, 0x7f, 0, 0x62, 0, 0, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x62, 0, 0, 0, 0x61, 0x7f, 0 };
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestIndefLengthMore() {
      byte[] bytes;
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x41, 0x31, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x00, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x18, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x20, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x38, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x60, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, 0x61, 0x31, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0x80, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0x81, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xa0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xa1, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xc0, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xd8, (byte)0xff, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xe0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xf8, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, 0x41, 0x30, (byte)0xf9, (byte)0xff, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        0x5f, 0x41, 0x30, (byte)0xfa, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff,
       };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        0x5f, 0x41, 0x30, (byte)0xfb, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
       };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x61, 0x31, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x00, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x18, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x20, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x38, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x40, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, 0x41, 0x31, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0x80, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0x81, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xa0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xa1, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xc0, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xd8, (byte)0xff, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xe0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xf8, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, 0x61, 0x30, (byte)0xf9, (byte)0xff, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        0x7f, 0x61, 0x30, (byte)0xfa, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff,
       };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        0x7f, 0x61, 0x30, (byte)0xfb, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
       };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, 0x41, 0x31, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x00, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x18, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x20, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x38, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x60, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, 0x61, 0x31, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, (byte)0x80, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, (byte)0x81, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, (byte)0xa0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, (byte)0xa1, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, (byte)0xc0, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, (byte)0xd8, (byte)0xff, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, (byte)0xe0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, (byte)0xf8, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x5f, (byte)0xf9, (byte)0xff, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x5f, (byte)0xfa, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        0x5f, (byte)0xfb, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff,
       };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x61, 0x31, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x00, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x18, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x20, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x38, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x40, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, 0x41, 0x31, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, (byte)0x80, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, (byte)0x81, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, (byte)0xa0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, (byte)0xa1, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, (byte)0xc0, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, (byte)0xd8, (byte)0xff, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, (byte)0xe0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, (byte)0xf8, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }

      bytes = new byte[] { 0x7f, (byte)0xf9, (byte)0xff, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x7f, (byte)0xfa, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        0x7f, (byte)0xfb, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
        (byte)0xff, (byte)0xff,
       };
      try {
        CBORObject.DecodeFromBytes (bytes);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (TestCommon.ToByteArrayString (bytes) + "\n" +
          ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestTags264And265() {
      CBORObject cbor;
      // Tag 264
      cbor = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xd9, 0x01, 0x08,
        (byte)0x82,
        (byte)0xc2, 0x42, 2, 2, (byte)0xc2, 0x42, 2, 2,
       });
      CBORTestCommon.AssertRoundTrip (cbor);
      // Tag 265
      cbor = CBORObject.DecodeFromBytes (new byte[] {
        (byte)0xd9, 0x01, 0x09,
        (byte)0x82,
        (byte)0xc2, 0x42, 2, 2, (byte)0xc2, 0x42, 2, 2,
       });
      CBORTestCommon.AssertRoundTrip (cbor);
    }
    @Test
    public void TestTagThenBreak() {
      try {
        CBORTestCommon.FromBytesTestAB (new byte[] { (byte)0xd1, (byte)0xff });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
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
      TestTextStringStreamOne (TestCommon.Repeat ('x', 200000));
      TestTextStringStreamOne (TestCommon.Repeat ('\u00e0', 200000));
      TestTextStringStreamOne (TestCommon.Repeat ('\u3000', 200000));
      TestTextStringStreamOne (TestCommon.Repeat ("\ud800\udc00", 200000));
    }
    @Test
    public void TestTextStringStreamNoIndefiniteWithinDefinite() {
      try {
        CBORTestCommon.FromBytesTestAB (new byte[] {
          0x7f, 0x61, 0x20, 0x7f,
          0x61, 0x20, (byte)0xff, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestIntegerFloatingEquivalence() {
      CBORObject cbor;
      // 0 versus 0.0
      cbor = CBORObject.NewMap();
      cbor.Set ((int)0, CBORObject.FromObject ("testzero"));
      cbor.Set ((double)0.0, CBORObject.FromObject ("testpointzero"));
      Assert.assertEquals(2, cbor.size());
      {
        String stringTemp = cbor.get(CBORObject.FromObject (0)).AsString();
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
      cbor.Set ((double)0.0, CBORObject.FromObject ("testpointzero"));
      cbor.Set ((int)0, CBORObject.FromObject ("testzero"));
      Assert.assertEquals(2, cbor.size());
      {
        String stringTemp = cbor.get(CBORObject.FromObject (0)).AsString();
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
      cbor.Set ((int)3, CBORObject.FromObject ("testzero"));
      cbor.Set ((double)3.0, CBORObject.FromObject ("testpointzero"));
      Assert.assertEquals(2, cbor.size());
      {
        String stringTemp = cbor.get(CBORObject.FromObject (3)).AsString();
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
      cbor.Set ((double)3.0, CBORObject.FromObject ("testpointzero"));
      cbor.Set ((int)3, CBORObject.FromObject ("testzero"));
      Assert.assertEquals(2, cbor.size());
      {
        String stringTemp = cbor.get(CBORObject.FromObject (3)).AsString();
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
      ToObjectTest.TestToFromObjectRoundTrip (EDecimal.SignalingNaN);
      ToObjectTest.TestToFromObjectRoundTrip (ERational.SignalingNaN);
      ToObjectTest.TestToFromObjectRoundTrip (EFloat.SignalingNaN);
    }

    @Test
    public void TestBigNumberThresholds() {
      EInteger maxCborInteger = EInteger.FromString ("18446744073709551615");
      EInteger maxInt64 = EInteger.FromString ("9223372036854775807");
      EInteger minCborInteger = EInteger.FromString ("-18446744073709551616");
      EInteger minInt64 = EInteger.FromString ("-9223372036854775808");
      EInteger pastMaxCborInteger = EInteger.FromString(
          "18446744073709551616");
      EInteger pastMaxInt64 = EInteger.FromString ("9223372036854775808");
      EInteger pastMinCborInteger =
        EInteger.FromString ("-18446744073709551617");
      EInteger pastMinInt64 = EInteger.FromString ("-9223372036854775809");
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
        cbor = CBORObject.FromObject (eints[i]);
        if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
        if (isPastCbor[i]) {
          if (isNegative) {
            if (!(cbor.HasOneTag (3))) {
 Assert.fail();
 }
          } else {
            if (!(cbor.HasOneTag (2))) {
 Assert.fail();
 }
          }
        } else {
          Assert.assertEquals(CBORType.Integer, cbor.getType());
          Assert.assertEquals(0, cbor.getTagCount());
        }
        EFloat ef = EFloat.Create (EInteger.FromInt32(1), eints[i]);
        cbor = CBORObject.FromObject (ef);
        if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
        if (isPastCbor[i]) {
          if (!(cbor.HasOneTag (265))) {
 Assert.fail();
 }
          if (isNegative) {
            if (!(cbor.get(0).HasOneTag (3))) {
 Assert.fail();
 }
          } else {
            if (!(cbor.get(0).HasOneTag (2))) {
 Assert.fail();
 }
          }
        } else {
          if (!(cbor.HasOneTag (5))) {
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

            CBORObject.Write (ef, ms);
            cbor = CBORObject.DecodeFromBytes (ms.toByteArray());
            if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
            if (isPastCbor[i]) {
              if (!(cbor.HasOneTag (265))) {
 Assert.fail();
 }
              if (isNegative) {
                if (!(cbor.get(0).HasOneTag (3))) {
 Assert.fail();
 }
              } else {
                if (!(cbor.get(0).HasOneTag (2))) {
 Assert.fail();
 }
              }
            } else {
              if (!(cbor.HasOneTag (5))) {
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
          EDecimal ed = EDecimal.Create (EInteger.FromInt32(1), eints[i]);
          cbor = CBORObject.FromObject (ed);
          if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
          if (isPastCbor[i]) {
            if (!(cbor.HasOneTag (264))) {
 Assert.fail();
 }
            if (isNegative) {
              if (!(cbor.get(0).HasOneTag (3))) {
 Assert.fail();
 }
            } else {
              if (!(cbor.get(0).HasOneTag (2))) {
 Assert.fail();
 }
            }
          } else {
            if (!(cbor.HasOneTag (4))) {
 Assert.fail();
 }
            Assert.assertEquals(CBORType.Integer, cbor.get(0).getType());
            Assert.assertEquals(0, cbor.get(0).getTagCount());
          }
          {
            java.io.ByteArrayOutputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayOutputStream();

            CBORObject.Write (ed, ms2);
            cbor = CBORObject.DecodeFromBytes (ms2.toByteArray());
            if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
            if (isPastCbor[i]) {
              if (!(cbor.HasOneTag (264))) {
 Assert.fail();
 }
              if (isNegative) {
                if (!(cbor.get(0).HasOneTag (3))) {
 Assert.fail();
 }
              } else {
                if (!(cbor.get(0).HasOneTag (2))) {
 Assert.fail();
 }
              }
            } else {
              if (!(cbor.HasOneTag (4))) {
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
    public void TestAllowEmpty() {
      CBOREncodeOptions options;
      byte[] bytes = new byte[0];
      options = new CBOREncodeOptions("");
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      options = new CBOREncodeOptions("allowempty=true");
      Assert.assertEquals(null, CBORObject.DecodeFromBytes (bytes, options));
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        options = new CBOREncodeOptions("");
        try {
          CBORObject.Read (ms, options);
          Assert.fail ("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail (ex.toString());
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
        Assert.assertEquals(null, CBORObject.Read (ms, options));
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
      CBORObject cbor = CBORObject.DecodeFromBytes (bytes);
      CBOREncodeOptions  options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println ("" + cbor);
      try {
        cbor.EncodeToBytes (options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (CBORTestCommon.CheckEncodeToBytes (cbor),
          options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
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
      CBORObject cbor = CBORObject.DecodeFromBytes (bytes);
      CBOREncodeOptions  options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println ("" + cbor);
      try {
        cbor.EncodeToBytes (options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (CBORTestCommon.CheckEncodeToBytes (cbor),
          options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
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
      CBORObject cbor = CBORObject.DecodeFromBytes (bytes);
      CBOREncodeOptions  options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println ("" + cbor);
      try {
        cbor.EncodeToBytes (options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (CBORTestCommon.CheckEncodeToBytes (cbor),
          options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
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
      CBORObject cbor = CBORObject.DecodeFromBytes (bytes);
      CBOREncodeOptions  options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println ("" + cbor);
      try {
        cbor.EncodeToBytes (options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (CBORTestCommon.CheckEncodeToBytes (cbor),
          options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
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
      CBORObject cbor = CBORObject.DecodeFromBytes (bytes);
      CBOREncodeOptions  options = new CBOREncodeOptions("ctap2canonical=true");
      System.out.println ("" + cbor);
      try {
        cbor.EncodeToBytes (options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (bytes, options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes (CBORTestCommon.CheckEncodeToBytes (cbor),
          options);
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCtap2CanonicalDecodeEncode() {
      RandomGenerator r = new RandomGenerator();

      CBOREncodeOptions  options = new CBOREncodeOptions("ctap2canonical=true");
      for (int i = 0; i < 3000; ++i) {
        CBORObject cbor = CBORTestCommon.RandomCBORObject (r);
        byte[] e2bytes = CBORTestCommon.CheckEncodeToBytes (cbor);
        byte[] bytes = e2bytes;
        cbor = CBORObject.DecodeFromBytes (bytes);
        CBORObject cbor2 = null;
        try {
          bytes = cbor.EncodeToBytes (options);
          try {
            cbor2 = CBORObject.DecodeFromBytes (bytes, options);
          } catch (Exception ex2) {
            Assert.fail (ex2.toString());
            throw new IllegalStateException("", ex2);
          }
          byte[] bytes2 = cbor2.EncodeToBytes (options);
          TestCommon.AssertByteArraysEqual (bytes, bytes2);
        } catch (CBORException ex4) {
          // Canonical encoding failed, so DecodeFromBytes must fail
          bytes = CBORTestCommon.CheckEncodeToBytes (cbor);
          try {
            CBORObject.DecodeFromBytes (bytes, options);
            Assert.fail ("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex3) {
            Assert.fail (ex3.toString() + "\n" + ex4.toString());
            throw new IllegalStateException("", ex3);
          }
        }
      }
    }

    @Test
    public void TestTextStringStreamNoTagsBeforeDefinite() {
      try {
        CBORTestCommon.FromBytesTestAB (new byte[] {
          0x7f, 0x61, 0x20,
          (byte)0xc0, 0x61, 0x20, (byte)0xff,
         });
        Assert.fail ("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail (ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static EInteger AsEI(CBORObject obj) {
      Object o = obj.ToObject (typeof (EInteger));
      return (EInteger)o;
    }

    private static EDecimal AsED(CBORObject obj) {
      return EDecimal.FromString(
          obj.ToObject (typeof (EDecimal)).toString());
    }

    private static void AddSubCompare(CBORObject o1, CBORObject o2) {
      EDecimal cmpDecFrac = AsED (o1).Add (AsED (o2));
      EDecimal cmpCobj = o1.AsNumber().Add (o2.AsNumber()).ToEDecimal();
      TestCommon.CompareTestEqual (cmpDecFrac, cmpCobj);
      cmpDecFrac = AsED (o1).Subtract (AsED (o2));
      cmpCobj = o1.AsNumber().Subtract (o2.AsNumber()).ToEDecimal();
      TestCommon.CompareTestEqual (cmpDecFrac, cmpCobj);
      CBORObjectTest.CompareDecimals (o1, o2);
    }

    private static void TestTextStringStreamOne(String longString) {
      CBORObject cbor, cbor2;
      cbor = ToObjectTest.TestToFromObjectRoundTrip (longString);
      cbor2 = CBORTestCommon.FromBytesTestAB (CBORTestCommon.CheckEncodeToBytes(
            cbor));
      {
        Object objectTemp = longString;
        Object objectTemp2 = CBORObject.DecodeFromBytes(
            CBORTestCommon.CheckEncodeToBytes (cbor)).AsString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = longString;
        Object objectTemp2 = CBORObject.DecodeFromBytes (cbor.EncodeToBytes(
              new CBOREncodeOptions(false, true))).AsString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      TestCommon.AssertEqualsHashCode (cbor, cbor2);
      Assert.assertEquals(longString, cbor2.AsString());
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
          obj.WriteJSONTo (ms);
          jsonString = DataUtilities.GetUtf8String(
              ms.toByteArray(),
              false);
          objA = CBORObject.FromJSONString (jsonString);
        } catch (CBORException ex) {
          throw new IllegalStateException(jsonString, ex);
        } catch (IOException ex) {
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      CBORObject objB = CBORObject.FromJSONString (obj.ToJSONString());
      if (!objA.equals (objB)) {
        Assert.fail ("WriteJSONTo gives different results from " +
          "ToJSONString\nobj=" +
          TestCommon.ToByteArrayString (obj.EncodeToBytes()));
      }
    }
  }
