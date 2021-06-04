package com.upokecenter.test;

import java.util.*;
import java.io.*;
import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

@SuppressWarnings("deprecation")
  public class CBORObjectTest {
    private static final String[] ValueJsonFails = {
      "\"\\uxxxx\"",
      "\"\\ud800\udc00\"",
      "\"\ud800\\udc00\"", "\"\\U0023\"", "\"\\u002x\"", "\"\\u00xx\"",
      "\"\\u0xxx\"", "\"\\u0\"", "\"\\u00\"", "\"\\u000\"", "trbb",
      "trub", "falsb", "nulb", "[true", "[true,", "[true]!", "tr\u0020",
      "tr", "fa", "nu", "True", "False", "Null", "TRUE", "FALSE","NULL",
      "truE", "falsE", "nulL", "tRUE", "fALSE", "nULL","tRuE","fAlSe","nUlL",
      "[tr]", "[fa]",
      "[nu]", "[True]", "[False]", "[Null]", "[TRUE]","[FALSE]","[NULL]",

  "[truE]", "[falsE]",
  "[nulL]","[tRUE]","[fALSE]","[nULL]","[tRuE]","[fAlSe]","[nUlL]",
      "fa ", "nu ", "fa lse", "nu ll", "tr ue",
      "[\"\ud800\\udc00\"]", "[\"\\ud800\udc00\"]",
      "[\"\\udc00\ud800\udc00\"]", "[\"\\ud800\ud800\udc00\"]",
      "[\"\\ud800\"]", "[1,2,", "[1,2,3", "{,\"0\":0,\"1\":1}",
      "{\"0\"::0}", "{\"0\":0,,\"1\":1}",
      "{\"0\":0,\"1\":1,}", "[,0,1,2]", "[0,,1,2]", "[0:1]", "[0:1:2]",
      "[0,1,,2]", "[0,1,2,]", "[0001]", "{a:true}",
      "{\"a\":#comment\ntrue}",
      "{\"a\"://comment\ntrue}", "{\"a\":/*comment*/true}", "{'a':true}",
      "{\"a\":'b'}", "{\"a\t\":true}", "{\"a\r\":true}", "{\"a\n\":true}",
      "['a']", "{\"a\":\"a\t\"}", "[\"a\\'\"]", "[NaN]", "[+Infinity]",
      "[-Infinity]", "[Infinity]", "{\"a\":\"a\r\"}", "{\"a\":\"a\n\"}",
      "[\"a\t\"]", "\"test\"\"", "\"test\"x", "\"test\"\u0300",
      "\"test\"\u0005", "[5]\"", "[5]x", "[5]\u0300", "[5]\u0005",
      "{\"test\":5}\"", "{\"test\":5}x", "{\"test\":5}\u0300",
      "{\"test\":5}\u0005", "true\"", "truex", "true}", "true\u0300",
      "true\u0005", "8024\"", "8024x", "8024}", "8024\u0300",
      "8024\u0005", "{\"test\":5}}", "{\"test\":5}{", "[5]]", "[5][",
      "00", "000", "001", "0001", "00.0", "001.0", "0001.0", "01E-4", "01.1E-4",
      "01E4", "01.1E4", "01e-4", "01.1e-4",
      "01e4", "01.1e4",
      "+0", "+1", "+0.0", "+1e4", "+1e-4", "+1.0","+1.0e4","+1.0e+4","+1.0e-4",
      "0000", "0x1", "0xf", "0x20", "0x01",
      "-3x", "-3e89x", "\u0005true", "x\\u0005z",
      "0,2", "0,05", "-0,2", "-0,05", "\u007F0.0", "\u00010.0", "0.0\u007F",
      "0.0\u0001", "-1.D\r\n", "-1.D\u0020", "-1.5L", "-0.0L", "0L","1L","1.5L",
      "0.0L",
      "0X1", "0Xf", "0X20", "0X01", ".2", ".05", "-.2",
      "-.05", "23.", "23.e0", "23.e1", "0.", "-0.", "[0000]", "[0x1]",
      "[0xf]", "[0x20]", "[0x01]", "[.2]", "[.05]", "[-.2]", "[-.05]",
      "[23.]", "[23.e0]", "[23.e1]", "[0.]", "\"abc", "\"ab\u0004c\"",
      "\u0004\"abc\"",
      "{\"x\":true \"y\":true}",
      "{\"x\":true\n\"y\":true}",
      "0,1,2,3", "\"x\",true",
      "\"x\",true",
      "\"x\":true",
      "\"x\":true,\"y\":true",
      "\"x\":true\n\"y\":true",
      "\"x\":true \"y\":true",
      "{\"x\":true,\"y\"}",
      "{\"x\",\"y\":true}",
      "{\"x\":true, \"y\"}",
      "{\"x\", \"y\":true}",
      "{[\"x\"]:true}",
      "{null:true}", "{true:true}", "{false:true}",
      "{[0]:true}", "{1:true}", "{{\"a\":true}:true}",
      "[1,\u0004" + "2]",
    };

    private static final String[] ValueJsonSucceeds = {
      "[0]",
      "[0.1]",
      "[0.1001]",
      "[0.0]", "true\n\r\t\u0020",
      "[-3 " + ",-5]", "\n\r\t\u0020true", "\"x\\u0005z\"",
      "[0.00]", "[0.000]", "[0.01]", "[0.001]", "[0.5]", "[0E5]", "[0e5]",
      "[0E+6]", "[\"\ud800\udc00\"]", "[\"\\ud800\\udc00\"]",
      "[\"\\ud800\\udc00\ud800\udc00\"]", "23.0e01", "23.0e00", "[23.0e01]",
      "[23.0e00]", "0", "1", "0.2", "0.05", "-0.2", "-0.05",
    };

    private static final JSONOptions ValueNoDuplicateKeys = new
    JSONOptions("allowduplicatekeys=false");

    static void CheckPropertyNames(
      Object ao,
      PODOptions cc,
      String p1,
      String p2,
      String p3) {
      CBORObjectTest.CheckPropertyNames(
        CBORObject.FromObject(ao, cc),
        p1,
        p2,
        p3);
    }

    static void CheckArrayPropertyNames(
      CBORObject co,
      int expectedCount,
      String p1,
      String p2,
      String p3) {
      Assert.assertEquals(CBORType.Array, co.getType());
      Assert.assertEquals(expectedCount, co.size());
      for (int i = 0; i < co.size(); ++i) {
        CBORObjectTest.CheckPropertyNames(co.get(i), p1, p2, p3);
      }
      CBORTestCommon.AssertRoundTrip(co);
    }

    static void CheckPODPropertyNames(
      CBORObject co,
      PODOptions cc,
      String p1,
      String p2,
      String p3) {
      Assert.assertEquals(CBORType.Map, co.getType());
      String keyName = cc.getUseCamelCase() ? "propValue" : "PropValue";
      if (!co.ContainsKey(keyName)) {
        Assert.fail("Expected " + keyName + " to exist: " + co.toString());
      }
      CBORObjectTest.CheckPropertyNames(co.get(keyName), p1, p2, p3);
    }

    static void CheckPODInDictPropertyNames(
      CBORObject co,
      String p1,
      String p2,
      String p3) {
      Assert.assertEquals(CBORType.Map, co.getType());
      if (!co.ContainsKey("PropValue")) {
        Assert.fail("Expected PropValue to exist: " + co.toString());
      }
      CBORObjectTest.CheckPropertyNames(co.get("PropValue"), p1, p2, p3);
    }

    static void CheckPropertyNames(
      CBORObject o,
      String p1,
      String p2,
      String p3) {
      if (o.ContainsKey("PrivatePropA")) {
 Assert.fail();
 }
      if (o.ContainsKey("privatePropA")) {
 Assert.fail();
 }
      if (o.ContainsKey("StaticPropA")) {
 Assert.fail();
 }
      if (o.ContainsKey("staticPropA")) {
 Assert.fail();
 }
      Assert.assertEquals(CBORType.Map, o.getType());
      if (!o.ContainsKey(p1)) {
        Assert.fail("Expected " + p1 + " to exist: " + o.toString());
      }
      if (!o.ContainsKey(p2)) {
        Assert.fail("Expected " + p2 + " to exist: " + o.toString());
      }
      if (!o.ContainsKey(p3)) {
        Assert.fail("Expected " + p3 + " to exist: " + o.toString());
      }
      CBORTestCommon.AssertRoundTrip(o);
    }

    static void CheckPropertyNames(Object ao) {
      PODOptions valueCcTF = new PODOptions(true, false);
      PODOptions valueCcFF = new PODOptions(false, false);
      PODOptions valueCcFT = new PODOptions(false, true);
      PODOptions valueCcTT = new PODOptions(true, true);
      CBORObjectTest.CheckPropertyNames(
        ao,
        valueCcTF,
        "PropA",
        "PropB",
        "IsPropC");
      //--
      CBORObjectTest.CheckPropertyNames(
        ao,
        valueCcFF,
        "PropA",
        "PropB",
        "IsPropC");
      CBORObjectTest.CheckPropertyNames(
        ao,
        valueCcFT,
        "propA",
        "propB",
        "propC");
      CBORObjectTest.CheckPropertyNames(
        ao,
        valueCcTT,
        "propA",
        "propB",
        "propC");
    }

    public static CBORObject GetNumberData() {
      return new AppResources("Resources").GetJSON("numbers");
    }

    public static void TestFailingJSON(String str) {
      TestFailingJSON(str, new JSONOptions("allowduplicatekeys=true"));
    }

    public static void TestFailingJSON(String str, JSONOptions opt) {
      byte[] bytes = null;
      try {
        bytes = DataUtilities.GetUtf8Bytes(str, false);
      } catch (IllegalArgumentException ex2) {
        System.out.println(ex2.getMessage());
        // Check only FromJSONString
        try {
          CBORObject.FromJSONString(str, opt);
          Assert.fail("Should have failed: str = " + str);
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        return;
      }
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        try {
          CBORObject.ReadJSON(ms, opt);
          Assert.fail("Should have failed: str = " + str);
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(str + "\r\n" + ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      try {
        CBORObject.FromJSONString(str, opt);
        Assert.fail("Should have failed: str = " + str);
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    public static CBORObject TestSucceedingJSON(String str) {
      return TestSucceedingJSON(str, null);
    }

    public static CBORObject TestSucceedingJSON(
      String str,
      JSONOptions options) {
      byte[] bytes = DataUtilities.GetUtf8Bytes(str, false);
      try {
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

          CBORObject obj = options == null ? CBORObject.ReadJSON(ms) :
            CBORObject.ReadJSON(ms, options);
          CBORObject obj2 = options == null ? CBORObject.FromJSONString(str) :
            CBORObject.FromJSONString(str, options);
          if (!obj.equals(obj2)) {
            TestCommon.CompareTestEqualAndConsistent(
              obj,
              obj2);
          }
          if (str == null) {
            throw new NullPointerException("str");
          }
          CBORObject obj3 = options == null ? CBORObject.FromJSONString(
              str,
              0,
              str.length()) :
            CBORObject.FromJSONString(str, 0, str.length(), options);
          if (!obj.equals(obj3)) {
            Assert.assertEquals(obj, obj3);
          }
          obj3 = options == null ? CBORObject.FromJSONString(
              "xyzxyz" + str,
              6,
              str.length()) :
            CBORObject.FromJSONString("xyzxyz" + str, 6, str.length(), options);
          if (!obj.equals(obj3)) {
            Assert.assertEquals(obj, obj3);
          }
          obj3 = options == null ? CBORObject.FromJSONString(
              "xyzxyz" + str + "xyzxyz",
              6,
              str.length()) : CBORObject.FromJSONString(
              "xyzxyz" + str + "xyzxyz",
              6,
              str.length(),
              options);
          if (!obj.equals(obj3)) {
            Assert.assertEquals(obj, obj3);
          }
          return obj;
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        Assert.fail(ex.toString() + "\n" + str);
        throw new IllegalStateException("", ex);
      }
    }

    public static String CharString(int cp, boolean quoted, char[] charbuf) {
      int index = 0;
      if (quoted) {
        if (charbuf == null) {
          throw new NullPointerException("charbuf");
        }
        charbuf[index++] = (char)0x22;
      }
      if (cp < 0x10000) {
        if (cp >= 0xd800 && cp < 0xe000) {
          return null;
        }
        if (charbuf == null) {
          throw new NullPointerException("charbuf");
        }
        charbuf[index++] = (char)cp;
        if (quoted) {
          charbuf[index++] = (char)0x22;
        }
        return new String(charbuf, 0, index);
      } else {
        cp -= 0x10000;
        if (charbuf == null) {
          throw new NullPointerException("charbuf");
        }
        charbuf[index++] = (char)((cp >> 10) + 0xd800);
        charbuf[index++] = (char)((cp & 0x3ff) | 0xdc00);
        if (quoted) {
          charbuf[index++] = (char)0x22;
        }
        return new String(charbuf, 0, index);
      }
    }

    @Test
    public void TestAdd() {
      CBORObject cbor = CBORObject.NewMap();
      CBORObject cborNull = CBORObject.Null;
      cbor.Add(null, true);
      Assert.assertEquals(CBORObject.True, cbor.get(cborNull));
      cbor.Add("key", null);
      Assert.assertEquals(CBORObject.Null, cbor.get("key"));
    }

    @Test
    public void TestAddConverter() {
      // not implemented yet
    }

    private static EDecimal AsED(CBORObject obj) {
      return (EDecimal)obj.ToObject(EDecimal.class);
    }

    @Test(timeout = 5000)
    public void TestAsNumberAdd() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 1000; ++i) {
        // NOTE: Avoid generating high-exponent numbers for this test
        CBORObject o1 = CBORTestCommon.RandomNumber(r, true);
        CBORObject o2 = CBORTestCommon.RandomNumber(r, true);
        EDecimal cmpCobj = null;
        try {
          cmpCobj = o1.AsNumber().Add(o2.AsNumber()).ToEDecimal();
        } catch (OutOfMemoryError ex) {
          continue;
        }
        EDecimal cmpDecFrac = AsED(o1).Add(AsED(o2));
        TestCommon.CompareTestEqual(cmpDecFrac, cmpCobj);
        CBORTestCommon.AssertRoundTrip(o1);
        CBORTestCommon.AssertRoundTrip(o2);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(2).AsNumber().Add(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestAsBoolean() {
      if (!(CBORObject.True.AsBoolean())) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(0).AsBoolean())) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip("")
        .AsBoolean())) {
 Assert.fail();
 }
      if (CBORObject.False.AsBoolean()) {
 Assert.fail();
 }
      if (CBORObject.Null.AsBoolean()) {
 Assert.fail();
 }
      if (CBORObject.Undefined.AsBoolean()) {
 Assert.fail();
 }
      if (!(CBORObject.NewArray().AsBoolean())) {
 Assert.fail();
 }
      if (!(CBORObject.NewMap().AsBoolean())) {
 Assert.fail();
 }
    }

    @Test
    public void TestAsByte() {
      try {
        CBORObject.NewArray().AsByte();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsByte();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsByte();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsByte();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsByte();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("").AsByte();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberinfo.get("number").AsString()));
        if (numberinfo.get("byte").AsBoolean()) {
          Assert.assertEquals(
            TestCommon.StringToInt(numberinfo.get("integer").AsString()),
            ((int)cbornumber.AsByte()) & 0xff);
        } else {
          try {
            cbornumber.AsByte();
            Assert.fail("Should have failed " + cbornumber);
          } catch (ArithmeticException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString() + cbornumber);
            throw new IllegalStateException("", ex);
          }
        }
      }
      for (int i = 0; i < 255; ++i) {
        Assert.assertEquals(
          (byte)i,
          ToObjectTest.TestToFromObjectRoundTrip(i).AsByte());
      }
      for (int i = -200; i < 0; ++i) {
        try {
          ToObjectTest.TestToFromObjectRoundTrip(i).AsByte();
        } catch (ArithmeticException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
      for (int i = 256; i < 512; ++i) {
        try {
          ToObjectTest.TestToFromObjectRoundTrip(i).AsByte();
        } catch (ArithmeticException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
    }

    @Test
    public void TestAsDouble() {
      try {
        CBORObject.NewArray().AsDouble();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsDouble();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsDouble();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsDouble();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsDouble();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("").AsDouble();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberinfo.get("number").AsString()));
        {
          double dtemp = (double)EDecimal.FromString(
              numberinfo.get("number").AsString()).ToDouble();
          double dtemp2 = cbornumber.AsDouble();
          AreEqualExact(dtemp, dtemp2);
        }
      }
    }

    @Test
    public void TestAsInt16() {
      try {
        CBORObject.NewArray().AsInt16();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsInt16();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsInt16();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsInt16();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsInt16();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("").AsInt16();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(
            EDecimal.FromString(numberinfo.get("number").AsString()));
        if (numberinfo.get("int16").AsBoolean()) {
          Assert.assertEquals(
            TestCommon.StringToInt(numberinfo.get("integer").AsString()),
            cbornumber.AsInt16());
        } else {
          try {
            cbornumber.AsInt16();
            Assert.fail("Should have failed " + cbornumber);
          } catch (ArithmeticException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString() + cbornumber);
            throw new IllegalStateException("", ex);
          }
        }
      }
    }

    @Test
    public void TestAsInt32() {
      try {
        CBORObject.NewArray().AsInt32();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsInt32();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsInt32();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsInt32();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsInt32();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("").AsInt32();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        EDecimal edec =
          EDecimal.FromString(numberinfo.get("number").AsString());
        CBORObject cbornumber = ToObjectTest.TestToFromObjectRoundTrip(edec);
        boolean isdouble = numberinfo.get("double").AsBoolean();
        CBORObject cbornumberdouble =
          ToObjectTest.TestToFromObjectRoundTrip(edec.ToDouble());
        boolean issingle = numberinfo.get("single").AsBoolean();
        CBORObject cbornumbersingle =
          ToObjectTest.TestToFromObjectRoundTrip(edec.ToSingle());
        if (numberinfo.get("int32").AsBoolean()) {
          Assert.assertEquals(
            TestCommon.StringToInt(numberinfo.get("integer").AsString()),
            cbornumber.AsInt32());
          if (isdouble) {
            Assert.assertEquals(
              TestCommon.StringToInt(numberinfo.get("integer").AsString()),
              cbornumberdouble.AsInt32());
          }
          if (issingle) {
            Assert.assertEquals(
              TestCommon.StringToInt(numberinfo.get("integer").AsString()),
              cbornumbersingle.AsInt32());
          }
        } else {
          try {
            cbornumber.AsInt32();
            Assert.fail("Should have failed " + cbornumber);
          } catch (ArithmeticException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString() + cbornumber);
            throw new IllegalStateException("", ex);
          }
          if (isdouble) {
            try {
              cbornumberdouble.AsInt32();
              Assert.fail("Should have failed");
            } catch (ArithmeticException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
          }
          if (issingle) {
            try {
              cbornumbersingle.AsInt32();
              Assert.fail("Should have failed");
            } catch (ArithmeticException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
          }
        }
      }
    }

    @Test
    public void TestAsInt64() {
      try {
        CBORObject.NewArray().AsInt64();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsInt64();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsInt64();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsInt64();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsInt64();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("").AsInt64();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        EDecimal edec =
          EDecimal.FromString(numberinfo.get("number").AsString());
        CBORObject cbornumber = ToObjectTest.TestToFromObjectRoundTrip(edec);
        boolean isdouble = numberinfo.get("double").AsBoolean();
        CBORObject cbornumberdouble =
          ToObjectTest.TestToFromObjectRoundTrip(edec.ToDouble());
        boolean issingle = numberinfo.get("single").AsBoolean();
        CBORObject cbornumbersingle =
          ToObjectTest.TestToFromObjectRoundTrip(edec.ToSingle());
        if (numberinfo.get("int64").AsBoolean()) {
          Assert.assertEquals(
            TestCommon.StringToLong(numberinfo.get("integer").AsString()),
            cbornumber.AsInt64());
          if (isdouble) {
            Assert.assertEquals(
              TestCommon.StringToLong(numberinfo.get("integer").AsString()),
              cbornumberdouble.AsInt64());
          }
          if (issingle) {
            Assert.assertEquals(
              TestCommon.StringToLong(numberinfo.get("integer").AsString()),
              cbornumbersingle.AsInt64());
          }
        } else {
          try {
            cbornumber.AsInt64();
            Assert.fail("Should have failed " + cbornumber);
          } catch (ArithmeticException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString() + cbornumber);
            throw new IllegalStateException("", ex);
          }
          if (isdouble) {
            try {
              cbornumberdouble.AsInt64();
              Assert.fail("Should have failed");
            } catch (ArithmeticException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
          }
          if (issingle) {
            try {
              cbornumbersingle.AsInt64();
              Assert.fail("Should have failed");
            } catch (ArithmeticException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
          }
        }
      }
    }

    @Test
    public void TestAsSByte() {
      // not implemented yet
    }

    @Test
    public void TestAsSingle() {
      try {
        CBORObject.NewArray().AsSingle();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsSingle();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsSingle();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsSingle();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsSingle();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("").AsSingle();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberinfo.get("number").AsString()));
        {
          float ftemp = (float)EDecimal.FromString(
              numberinfo.get("number").AsString()).ToSingle();
          float ftemp2 = cbornumber.AsSingle();
          AreEqualExact(ftemp, ftemp2);
        }
      }
    }

    @Test
    public void TestAsString() {
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip("test")
          .AsString();
        Assert.assertEquals(
          "test",
          stringTemp);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(CBORObject.Null).AsString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(true).AsString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(false).AsString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(5).AsString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().AsString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestAsUInt16() {
      // not implemented yet
    }

    @Test
    public void TestAsUInt32() {
      // not implemented yet
    }

    @Test
    public void TestAsUInt64() {
      // not implemented yet
    }

    @Test
    public void TestCanFitInDouble() {
      if (!(ToObjectTest.TestToFromObjectRoundTrip(
          0).CanFitInDouble())) {
 Assert.fail();
 }
    }
    @Test
    public void TestCanFitInDoubleA() {
      if (CBORObject.True.CanFitInDouble()) {
 Assert.fail();
 }
    }
    @Test
    public void TestCanFitInDoubleB() {
      if (ToObjectTest.TestToFromObjectRoundTrip("")
        .CanFitInDouble()) {
 Assert.fail();
 }
    }
    @Test
    public void TestCanFitInDoubleC() {
      if (CBORObject.NewArray().CanFitInDouble()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().CanFitInDouble()) {
 Assert.fail();
 }
    }
    @Test
    public void TestCanFitInDoubleD() {
      if (CBORObject.False.CanFitInDouble()) {
 Assert.fail();
 }
      if (CBORObject.Null.CanFitInDouble()) {
 Assert.fail();
 }
    }
    @Test
    public void TestCanFitInDoubleE() {
      if (CBORObject.Undefined.CanFitInDouble()) {
 Assert.fail();
 }
    }
    @Test
    public void TestCanFitInDoubleF() {
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberinfo.get("number").AsString()));
        if (cbornumber == null) {
          Assert.fail();
        }
        if (numberinfo.get("double").AsBoolean()) {
          if (!cbornumber.CanFitInDouble()) {
            Assert.fail(cbornumber.toString());
          }
        } else {
if (cbornumber.CanFitInDouble()) {
  Assert.fail(cbornumber.toString());
}
        }
      }
      RandomGenerator rand = new RandomGenerator();
      for (int i = 0; i < 2047; ++i) {
        // Try a random double with a given
        // exponent
        Object o = RandomObjects.RandomDouble(rand, i);
        CBORObject cbornumber = ToObjectTest.TestToFromObjectRoundTrip(o);
        if (cbornumber == null) {
          Assert.fail();
        }
        if (!(cbornumber.CanFitInDouble())) {
 Assert.fail();
 }
      }
    }

    @Test
    public void TestCanFitInInt32() {
      if (!(CInt32(ToObjectTest.TestToFromObjectRoundTrip(0))))Assert.fail();
      if (CInt32(CBORObject.True)) {
 Assert.fail();
 }
      if (CInt32(ToObjectTest.TestToFromObjectRoundTrip(
            ""))) {
 Assert.fail();
 }
      if (CInt32(CBORObject.NewArray())) {
 Assert.fail();
 }
      if (CInt32(CBORObject.NewMap())) {
 Assert.fail();
 }
      if (CInt32(CBORObject.False)) {
 Assert.fail();
 }
      if (CInt32(CBORObject.Null)) {
 Assert.fail();
 }
      if (CInt32(CBORObject.Undefined)) {
 Assert.fail();
 }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        if (numberinfo.get("number") == null) {
          Assert.fail();
        }
        if (numberinfo.get("int32") == null) {
          Assert.fail();
        }
        if (numberinfo.get("isintegral") == null) {
          Assert.fail();
        }
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberinfo.get("number").AsString()));
        if (cbornumber == null) {
          Assert.fail();
        }
        if (numberinfo.get("int32").AsBoolean() &&
          numberinfo.get("isintegral").AsBoolean()) {
          if (!(CInt32(cbornumber))) {
 Assert.fail();
 }
          Assert.assertTrue(
            CInt32(ToObjectTest.TestToFromObjectRoundTrip(
                cbornumber.AsInt32())));
        } else {
          if (CInt32(cbornumber)) {
 Assert.fail();
 }
        }
      }
    }

    private static boolean CInt64(CBORObject cbor) {
      return cbor != null && cbor.isNumber() && cbor.AsNumber().CanFitInInt64();
    }

    private static boolean CInt32(CBORObject cbor) {
      return cbor != null && cbor.isNumber() && cbor.AsNumber().CanFitInInt32();
    }

    @Test
    public void TestCanFitInInt64() {
      if (!(CInt64(ToObjectTest.TestToFromObjectRoundTrip(0))))Assert.fail();
      if (CInt64(CBORObject.True)) {
 Assert.fail();
 }
      if (CInt64(ToObjectTest.TestToFromObjectRoundTrip(
            ""))) {
 Assert.fail();
 }
      if (CInt64(CBORObject.NewArray())) {
 Assert.fail();
 }
      if (CInt64(CBORObject.NewMap())) {
 Assert.fail();
 }
      if (CInt64(CBORObject.False)) {
 Assert.fail();
 }
      if (CInt64(CBORObject.Null)) {
 Assert.fail();
 }
      if (CInt64(CBORObject.Undefined)) {
 Assert.fail();
 }

      EInteger ei;
      ei = EInteger.FromString("9223372036854775807");
      if (!(CInt64(CBORObject.FromObject(ei))))Assert.fail(ei.toString());
      ei = EInteger.FromString("9223372036854775808");
      if (CInt64(CBORObject.FromObject(ei))) {
 Assert.fail(ei.toString());
 }
      ei = EInteger.FromString("-9223372036854775807");
      if (!(CInt64(CBORObject.FromObject(ei))))Assert.fail(ei.toString());
      ei = EInteger.FromString("-9223372036854775808");
      if (!(CInt64(CBORObject.FromObject(ei))))Assert.fail(ei.toString());
      ei = EInteger.FromString("-9223372036854775809");
      if (CInt64(CBORObject.FromObject(ei))) {
 Assert.fail(ei.toString());
 }
      ei = EInteger.FromString("-9223373136366403584");
      if (CInt64(CBORObject.FromObject(ei))) {
 Assert.fail(ei.toString());
 }
      ei = EInteger.FromString("9223373136366403584");
      if (CInt64(CBORObject.FromObject(ei))) {
 Assert.fail(ei.toString());
 }
      String[] strings = new String[] {
        "8000FFFFFFFF0000",
        "8000AAAAAAAA0000",
        "8000800080000000",
        "8000000100010000",
        "8000FFFF00000000",
        "80000000FFFF0000",
        "8000800000000000",
        "8000000080000000",
        "8000AAAA00000000",
        "80000000AAAA0000",
        "8000000100000000",
        "8000000000010000",
      };
      for (String str : strings) {
        ei = EInteger.FromRadixString(str, 16);
        if (CInt64(CBORObject.FromObject(ei))) {
 Assert.fail();
 }
        ei = ei.Negate();
        if (CInt64(CBORObject.FromObject(ei))) {
 Assert.fail();
 }
      }

      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        if (numberinfo.get("number") == null) {
          Assert.fail();
        }
        if (numberinfo.get("int64") == null) {
          Assert.fail();
        }
        if (numberinfo.get("isintegral") == null) {
          Assert.fail();
        }
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberinfo.get("number").AsString()));
        if (numberinfo.get("int64").AsBoolean() &&
          numberinfo.get("isintegral").AsBoolean()) {
          if (!(CInt64(cbornumber))) {
 Assert.fail();
 }

          Assert.assertTrue(
            CInt64(ToObjectTest.TestToFromObjectRoundTrip(
                cbornumber.AsInt64())));
        } else {
          if (CInt64(cbornumber)) {
 Assert.fail();
 }
        }
      }
    }

    @Test
    public void TestCanFitInSingle() {
      if (!(ToObjectTest.TestToFromObjectRoundTrip(
          0).CanFitInSingle())) {
 Assert.fail();
 }
      if (CBORObject.True.CanFitInSingle()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip("")
        .CanFitInSingle()) {
 Assert.fail();
 }
      if (CBORObject.NewArray().CanFitInSingle()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().CanFitInSingle()) {
 Assert.fail();
 }
      if (CBORObject.False.CanFitInSingle()) {
 Assert.fail();
 }
      if (CBORObject.Null.CanFitInSingle()) {
 Assert.fail();
 }
      if (CBORObject.Undefined.CanFitInSingle()) {
 Assert.fail();
 }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        if (numberinfo.get("number") == null) {
          Assert.fail();
        }
        if (numberinfo.get("single") == null) {
          Assert.fail();
        }
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberinfo.get("number").AsString()));
        if (numberinfo.get("single").AsBoolean()) {
          if (!(cbornumber.CanFitInSingle())) {
 Assert.fail();
 }
        } else {
          if (cbornumber.CanFitInSingle()) {
 Assert.fail();
 }
        }
      }

      RandomGenerator rand = new RandomGenerator();
      for (int i = 0; i < 255; ++i) {
        // Try a random float with a given
        // exponent
        if (!(
          ToObjectTest.TestToFromObjectRoundTrip(
            RandomObjects.RandomSingle(
              rand,
              i)).CanFitInSingle()))Assert.fail();
      }
    }

    @Test
    public void TestCanTruncatedIntFitInInt32() {
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            11)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            12)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            13)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            14)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            15)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            16)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            17)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            18)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            19)).CanTruncatedIntFitInInt32()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(0)
        .CanTruncatedIntFitInInt32())) {
 Assert.fail();
 }
      if (CBORObject.True.CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip("")
        .CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (CBORObject.NewArray().CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (CBORObject.False.CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (CBORObject.Null.CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (CBORObject.Undefined.CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        if (numberinfo.get("number") == null) {
          Assert.fail();
        }
        if (numberinfo.get("int32") == null) {
          Assert.fail();
        }
        String numberString = numberinfo.get("number").AsString();
        CBORObject cbornumber = ToObjectTest.TestToFromObjectRoundTrip(
            EDecimal.FromString(numberString));
        if (numberinfo.get("int32").AsBoolean()) {
          if (!(cbornumber.CanTruncatedIntFitInInt32())) {
 Assert.fail(numberString);
 }
        } else {
          if (cbornumber.CanTruncatedIntFitInInt32()) {
 Assert.fail(numberString);
 }
        }
      }

      if (CBORObject.True.CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (CBORObject.False.CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (CBORObject.NewArray().CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().CanTruncatedIntFitInInt32()) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(0)
        .CanTruncatedIntFitInInt32())) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(2.5)
        .CanTruncatedIntFitInInt32())) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(Integer.MIN_VALUE)
        .CanTruncatedIntFitInInt32())) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(Integer.MAX_VALUE)
        .CanTruncatedIntFitInInt32())) {
 Assert.fail();
 }
      Object[] negint32 = new Object[] {
        Double.POSITIVE_INFINITY,
        Double.NEGATIVE_INFINITY,
        Double.NaN,
        CBORTestCommon.DecPosInf,
        CBORTestCommon.DecNegInf,
        EDecimal.NaN,
      };
      for (Object obj : negint32) {
        boolean bval = ToObjectTest.TestToFromObjectRoundTrip(obj)
          .CanTruncatedIntFitInInt32();
        if (bval) {
 Assert.fail(obj.toString());
 }
      }
    }

    @Test
    public void TestCanTruncatedIntFitInInt64() {
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            11)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            12)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            13)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            14)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            15)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            16)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            17)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            18)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(EFloat.Create(
            -2,
            19)).CanTruncatedIntFitInInt64()))Assert.fail();
      if (!(ToObjectTest.TestToFromObjectRoundTrip(0)
        .CanTruncatedIntFitInInt64())) {
 Assert.fail();
 }
      if (CBORObject.True.CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip("")
        .CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }
      if (CBORObject.NewArray().CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }
      if (CBORObject.False.CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }
      if (CBORObject.Null.CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }
      if (CBORObject.Undefined.CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }

      EInteger ei;
      ei = EInteger.FromString("9223372036854775807");
      {
        boolean btemp = CBORObject.FromObject(ei)
          .CanTruncatedIntFitInInt64();
        if (!(btemp)) {
 Assert.fail(ei.toString());
 }
      }
      ei = EInteger.FromString("9223372036854775808");
      if (CBORObject.FromObject(ei).CanTruncatedIntFitInInt64()) {
 Assert.fail(
        ei.toString());
 }
      ei = EInteger.FromString("-9223372036854775807");
      if (!(CBORObject.FromObject(ei).CanTruncatedIntFitInInt64())) {
 Assert.fail(
        ei.toString());
 }
      ei = EInteger.FromString("-9223372036854775808");
      if (!(CBORObject.FromObject(ei).CanTruncatedIntFitInInt64())) {
 Assert.fail(
        ei.toString());
 }
      ei = EInteger.FromString("-9223372036854775809");
      if (CBORObject.FromObject(ei).CanTruncatedIntFitInInt64()) {
 Assert.fail(
        ei.toString());
 }
      ei = EInteger.FromString("-9223373136366403584");
      if (CBORObject.FromObject(ei).CanTruncatedIntFitInInt64()) {
 Assert.fail(
        ei.toString());
 }
      ei = EInteger.FromString("9223373136366403584");
      if (CBORObject.FromObject(ei).CanTruncatedIntFitInInt64()) {
 Assert.fail(
        ei.toString());
 }
      String[] strings = new String[] {
        "8000FFFFFFFF0000",
        "8000AAAAAAAA0000",
        "8000800080000000",
        "8000000100010000",
        "8000FFFF00000000",
        "80000000FFFF0000",
        "8000800000000000",
        "8000000080000000",
        "8000AAAA00000000",
        "80000000AAAA0000",
        "8000000100000000",
        "8000000000010000",
      };
      for (String str : strings) {
        ei = EInteger.FromRadixString(str, 16);
        if (CBORObject.FromObject(ei).CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }
        ei = ei.Negate();
        if (CBORObject.FromObject(ei).CanTruncatedIntFitInInt64()) {
 Assert.fail();
 }
      }

      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        String numberString = numberinfo.get("number").AsString();
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberString));
        if (numberinfo.get("int64").AsBoolean()) {
          if (!(
            cbornumber.CanTruncatedIntFitInInt64())) {
 Assert.fail(
            numberString);
 }
        } else {
          if (
            cbornumber.CanTruncatedIntFitInInt64()) {
 Assert.fail(
            numberString);
 }
        }
      }
    }

    @Test(timeout = 1000)
    public void TestSlowCompareTo2() {
      CBORObject cbor1 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5,
        (byte)0x82, 0x3b, 0x00, 0x00, 0x00, (byte)0xd3, (byte)0xe1, 0x26,
        (byte)0xf9, 0x3b, (byte)0xc2, 0x4c, 0x01, 0x01, 0x01, 0x00, 0x00, 0x01,
        0x01, 0x00, 0x00, 0x01, 0x00, 0x00,
       });
      CBORObject cbor2 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4,
        (byte)0x82, 0x3b, 0x00, 0x00, 0x00, 0x56, (byte)0xe9, 0x21, (byte)0xda,
        (byte)0xe9, (byte)0xc2, 0x58, 0x2a, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x01, 0x00, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00,
        0x01, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x01,
        0x01, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00, 0x01, 0x01, 0x00, 0x01,
        0x00,
       });
      System.out.println(cbor1);
      System.out.println(cbor2);
      TestCommon.CompareTestGreater(cbor1.AsNumber(), cbor2.AsNumber());
    }

    @Test(timeout = 1000)
    public void TestSlowCompareTo6() {
      CBORObject cbor1 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5,
        (byte)0x82, 0x1b, 0x00, 0x00, 0x00, 0x7a, 0x50, (byte)0xe0, 0x1f,
        (byte)0xc6, (byte)0xc2, 0x4c, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00, 0x01,
        0x00, 0x00, 0x00, 0x01, 0x01,
       });
      CBORObject cbor2 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4,
        (byte)0x82, 0x19, 0x01, 0x60, (byte)0xc2, 0x58, (byte)0x87, (byte)0xbb,
        (byte)0xf8, 0x74, (byte)0xbe, (byte)0xcc, 0x46, 0x6b, 0x02, 0x3c,
        (byte)0x84, (byte)0xe9, (byte)0xd1, (byte)0xe9, 0x3c, (byte)0xd3,
        (byte)0xd5, 0x20, (byte)0xc1, 0x7e, 0x17, 0x09, 0x0f, (byte)0xdd, 0x73,
        0x5d, (byte)0xe4, 0x51, (byte)0xd6, 0x10, 0x52, 0x2e, 0x6c, 0x77,
        (byte)0x9f, 0x5e, 0x4f, 0x58, 0x72, 0x38, 0x43, (byte)0xb0, 0x28, 0x5a,
        0x6c, (byte)0xe5, (byte)0xd2, 0x36, (byte)0x9e, 0x69, 0x50, (byte)0xf9,
        0x62, 0x7f, (byte)0xcb, (byte)0xf5, 0x12, (byte)0x8c, 0x37, 0x2d,
        (byte)0x8e, 0x4f, (byte)0x83, 0x5c, (byte)0xd6, 0x6d, 0x5e, (byte)0xf0,
        0x65, 0x12, 0x4a, 0x0a, (byte)0x81, (byte)0x89, (byte)0xed, 0x20, 0x50,
        (byte)0xca, 0x0e, (byte)0x81, (byte)0xbc, (byte)0x9e, (byte)0x83, 0x66,
        (byte)0xb1, (byte)0xcd, 0x23, (byte)0xee, 0x24, 0x2e, (byte)0xec, 0x77,
        0x13, (byte)0x89, (byte)0xbd, (byte)0xfb, 0x47, (byte)0xd1, 0x02, 0x1c,
        0x4e, (byte)0xf5, 0x30, 0x59, 0x75, (byte)0xce, (byte)0xa8, (byte)0xaf,
        0x23, 0x51, 0x7e, 0x26, (byte)0xaa, (byte)0xed, (byte)0xe9, 0x34, 0x02,
        0x31, 0x70, (byte)0xe3, 0x3f, 0x71, (byte)0x9a, (byte)0x9a, (byte)0xe9,
        (byte)0xf3, 0x6d, (byte)0xd7, 0x28, 0x18, (byte)0xa2, (byte)0xb5,
        (byte)0x8b, (byte)0xca, 0x11, (byte)0x99,
       });
      System.out.println(cbor1);
      System.out.println(cbor2);
      TestCommon.CompareTestReciprocal(cbor1.AsNumber(), cbor2.AsNumber());
    }

    @Test(timeout = 1000)
    public void TestSlowCompareTo5() {
      CBORObject cbor1 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5,
        (byte)0x82, 0x1b, 0x00, 0x00, 0x10, 0x57, (byte)0xa5, (byte)0x96,
        (byte)0xbe, 0x7b, (byte)0xc2, 0x53, 0x01, 0x01, 0x00, 0x00, 0x00, 0x01,
        0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x01, 0x01, 0x00, 0x00, 0x01, 0x01,
        0x00,
       });
      CBORObject cbor2 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4,
        (byte)0x82, 0x19, 0x01, (byte)0x84, (byte)0xc2, 0x53, 0x20, 0x44, 0x52,
        0x64, (byte)0x9d, (byte)0xea, (byte)0xe8, 0x57, 0x13, (byte)0xa3, 0x7c,
        (byte)0xeb, 0x5e, 0x0e, 0x54, (byte)0xc8, (byte)0xf0, (byte)0xb2,
        0x58,
       });
      System.out.println(cbor1);
      System.out.println(cbor2);
      TestCommon.CompareTestReciprocal(cbor1.AsNumber(), cbor2.AsNumber());
    }

    @Test(timeout = 1000)
    public void TestSlowCompareTo() {
      CBORObject cbor1 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5,
        (byte)0x82, 0x3b, 0x00, 0x00, 0x00, 0x15, (byte)0xfc, (byte)0xa0,
        (byte)0xd9, (byte)0xf9, (byte)0xc3, 0x58, 0x36, 0x02, (byte)0x83, 0x3b,
        0x3c, (byte)0x99, (byte)0xdb, (byte)0xe4, (byte)0xfc, 0x2a, 0x69, 0x69,
        (byte)0xe7, 0x63, (byte)0xb7, 0x5d, 0x48, (byte)0xcf, 0x51, 0x33,
        (byte)0xd7, (byte)0xc3, 0x59, 0x4d, 0x63, 0x3c, (byte)0xbb, (byte)0x9d,
        0x43, 0x2d, (byte)0xd1, 0x51, 0x39, 0x1f, 0x03, 0x22, 0x5c, 0x13,
        (byte)0xed, 0x02, (byte)0xca, (byte)0xda, 0x09, 0x22, 0x07, (byte)0x9f,
        0x34, (byte)0x84, (byte)0xb4, 0x22, (byte)0xa8, 0x26, (byte)0x9f, 0x35,
        (byte)0x8d,
       });
      CBORObject cbor2 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4,
        (byte)0x82, 0x24, 0x26,
       });
      System.out.println(cbor1);
      System.out.println(cbor2);
      TestCommon.CompareTestGreater(cbor1.AsNumber(), cbor2.AsNumber());
    }

    @Test(timeout = 1000)
    public void TestSlowCompareTo3() {
      CBORObject cbor1 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5,
        (byte)0x82, 0x3b, 0x04, 0x55, 0x0a, 0x12, (byte)0x94, (byte)0xf8, 0x1f,
        (byte)0x9b, (byte)0xc2, 0x58, 0x1f, 0x01, 0x00, 0x00, 0x01, 0x01, 0x00,
        0x00, 0x01, 0x00, 0x00, 0x01, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00, 0x01,
        0x01, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x01,
        0x00,
       });
      CBORObject cbor2 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4,
        (byte)0x82, 0x39, 0x02, 0x03, (byte)0xc2, 0x58, 0x2d, 0x01, 0x00, 0x00,
        0x00, 0x00, 0x01, 0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x01,
        0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01,
        0x00, 0x01, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x01, 0x01, 0x00,
        0x01, 0x01, 0x00, 0x01, 0x00, 0x01,
       });
      System.out.println(cbor1);
      System.out.println(cbor2);
      TestCommon.CompareTestLess(cbor1.AsNumber(), cbor2.AsNumber());
    }

    @Test(timeout = 1000)
    public void TestSlowCompareTo4() {
      CBORObject cbor1 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4,
        (byte)0x82, 0x2f, 0x3b, 0x00, 0x1e, (byte)0xdc, 0x5d, 0x51, 0x5d, 0x26,
        (byte)0xb7,
       });
      CBORObject cbor2 = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5,
        (byte)0x82, 0x3b, 0x00, 0x18, 0x72, 0x44, 0x49, (byte)0xd0, 0x0c,
        (byte)0xb6, (byte)0xc3, 0x58, (byte)0x88, 0x0a, (byte)0xd0, 0x12,
        (byte)0x93, (byte)0xcb, 0x0a, 0x30, 0x2d, 0x11, 0x36, 0x59, 0x5a,
        (byte)0xfe, (byte)0x81, 0x79, (byte)0x80, (byte)0x86, (byte)0xb8, 0x2f,
        0x26, 0x4b, (byte)0xf4, 0x70, (byte)0xb4, 0x37, 0x3b, 0x7a, 0x1d,
        (byte)0x89, 0x4b, (byte)0xd4, 0x75, 0x07, (byte)0xad, 0x0c, (byte)0x90,
        0x6b, 0x1f, 0x53, (byte)0xf7, (byte)0xc3, (byte)0xde, 0x61, (byte)0xf2,
        0x62, 0x78, (byte)0x8a, 0x29, 0x31, 0x44, (byte)0xdd, 0x20, (byte)0xa4,
        0x79, 0x76, 0x59, (byte)0xb7, (byte)0xf7, 0x7c, 0x37, (byte)0xb8, 0x47,
        (byte)0xcf, (byte)0x96, (byte)0xf8, (byte)0x85, (byte)0xae, (byte)0xee,
        (byte)0xb4, 0x06, 0x13, (byte)0xef, (byte)0xd1, (byte)0xe6, 0x36,
        (byte)0xa5, (byte)0xfe, (byte)0xec, (byte)0x8f, (byte)0x8e, 0x00,
        (byte)0xaa, (byte)0xc2, (byte)0xd4, 0x77, (byte)0xcf, (byte)0xea,
        (byte)0xff, 0x4d, 0x12, 0x0b, (byte)0xf5, 0x08, (byte)0xc4, 0x0f, 0x08,
        (byte)0xa7, 0x07, (byte)0xb6, 0x45, 0x47, (byte)0x89, (byte)0xba, 0x5a,
        (byte)0xde, 0x6c, 0x69, 0x6a, 0x49, (byte)0xba, (byte)0xb2, (byte)0xd9,
        0x0f, (byte)0x9c, (byte)0xa4, (byte)0xec, 0x48, (byte)0xd2, 0x71, 0x50,
        (byte)0xde, (byte)0x96, (byte)0x99, (byte)0x9e, (byte)0x89, 0x33,
        (byte)0x8f, 0x6f, (byte)0xa8, 0x30, (byte)0xa1, 0x0a, 0x0f, (byte)0xab,
        (byte)0xfe, (byte)0xbe,
       });
      System.out.println(cbor1);
      System.out.println(cbor2);
      TestCommon.CompareTestLess(cbor1.AsNumber(), cbor2.AsNumber());
    }

    private static String TrimStr(String str, int len) {
      return str.substring(0, Math.min(len, str.length()));
    }

    @Test
    public void CompareLongDouble() {
      CBORObject cbor1 = CBORObject.FromObject(3.5E-15);
      CBORObject cbor2 = CBORObject.FromObject(281479271677953L);
      TestCommon.CompareTestLess(cbor1.AsDouble(), cbor2.AsDouble());
    }

    @Test(timeout = 300000)
    public void TestCompareTo() {
      RandomGenerator r = new RandomGenerator();
      int CompareCount = 3000;
      ArrayList<CBORObject> list = new ArrayList<CBORObject>();
      for (int i = 0; i < CompareCount; ++i) {
        CBORObject o1 = CBORTestCommon.RandomCBORObject(r);
        CBORObject o2 = CBORTestCommon.RandomCBORObject(r);
        CBORObject o3 = CBORTestCommon.RandomCBORObject(r);
        TestCommon.CompareTestRelations(o1, o2, o3);
      }
      System.out.println("Check compare");
      for (int i = 0; i < list.size(); ++i) {
        int j;
        j = i + 1;
        for (; j < list.size(); ++j) {
          CBORObject o1 = list.get(i);
          CBORObject o2 = list.get(j);
          TestCommon.CompareTestReciprocal(o1, o2);
        }
      }
      System.out.println("Sorting");
      java.util.Collections.sort(list);
      System.out.println("Check compare 2");
      for (int i = 0; i < list.size() - 1; ++i) {
        CBORObject o1 = list.get(i);
        CBORObject o2 = list.get(i + 1);
        TestCommon.CompareTestLessEqual(o1, o2);
      }
      for (int i = 0; i < 5000; ++i) {
        CBORObject o1 = CBORTestCommon.RandomNumber(r);
        CBORObject o2 = CBORTestCommon.RandomNumber(r);
        CompareDecimals(o1, o2);
      }
      TestCommon.CompareTestEqual(
        ToObjectTest.TestToFromObjectRoundTrip(0.1),
        ToObjectTest.TestToFromObjectRoundTrip(0.1));
      TestCommon.CompareTestEqual(
        ToObjectTest.TestToFromObjectRoundTrip(0.1f),
        ToObjectTest.TestToFromObjectRoundTrip(0.1f));
      for (int i = 0; i < 50; ++i) {
        CBORObject o1 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY);
        CBORObject o2 = CBORTestCommon.RandomNumberOrRational(r);
        if (o2.AsNumber().IsInfinity() || o2.AsNumber().IsNaN()) {
          continue;
        }
        TestCommon.CompareTestLess(o1.AsNumber(), o2.AsNumber());
        o1 = ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY);
        TestCommon.CompareTestLess(o1.AsNumber(), o2.AsNumber());
        o1 = ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY);
        TestCommon.CompareTestGreater(o1.AsNumber(), o2.AsNumber());
        o1 = ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY);
        TestCommon.CompareTestGreater(o1.AsNumber(), o2.AsNumber());
      }
      byte[] bytes1 = { 0, 1 };
      byte[] bytes2 = { 0, 2 };
      byte[] bytes3 = { 1, 1 };
      byte[] bytes4 = { 1, 2 };
      byte[] bytes5 = { 0, 2, 0 };
      byte[] bytes6 = { 1, 1, 4 };
      byte[] bytes7 = { 1, 2, 6 };
      CBORObject[] sortedObjects = {
        ToObjectTest.TestToFromObjectRoundTrip(bytes1),
        ToObjectTest.TestToFromObjectRoundTrip(bytes2),
        ToObjectTest.TestToFromObjectRoundTrip(bytes3),
        ToObjectTest.TestToFromObjectRoundTrip(bytes4),
        ToObjectTest.TestToFromObjectRoundTrip(bytes5),
        ToObjectTest.TestToFromObjectRoundTrip(bytes6),
        ToObjectTest.TestToFromObjectRoundTrip(bytes7),
        ToObjectTest.TestToFromObjectRoundTrip("aa"),
        ToObjectTest.TestToFromObjectRoundTrip("ab"),
        ToObjectTest.TestToFromObjectRoundTrip("ba"),
        ToObjectTest.TestToFromObjectRoundTrip("abc"),
        ToObjectTest.TestToFromObjectRoundTrip(CBORObject.NewArray()),
        ToObjectTest.TestToFromObjectRoundTrip(CBORObject.NewMap()),
        CBORObject.FromSimpleValue(0),
        CBORObject.FromSimpleValue(1),
        CBORObject.FromSimpleValue(19), CBORObject.FromSimpleValue(32),
        CBORObject.FromSimpleValue(255),
        ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY),
      };
      for (int i = 0; i < sortedObjects.length; ++i) {
        for (int j = i; j < sortedObjects.length; ++j) {
          if (i == j) {
            TestCommon.CompareTestEqual(sortedObjects[i], sortedObjects[j]);
          } else {
            TestCommon.CompareTestLess(sortedObjects[i], sortedObjects[j]);
          }
        }
        Assert.assertEquals(1, sortedObjects[i].compareTo(null));
      }
      CBORNumber sp =
        CBORObject.FromObject(Float.POSITIVE_INFINITY).AsNumber();
      CBORNumber sn = CBORObject.FromObject(
          Float.NEGATIVE_INFINITY).AsNumber();
      CBORNumber snan = CBORObject.FromObject(Float.NaN).AsNumber();
      CBORNumber dp = CBORObject.FromObject(
          Double.POSITIVE_INFINITY).AsNumber();
      CBORNumber dn = CBORObject.FromObject(
          Double.NEGATIVE_INFINITY).AsNumber();
      CBORNumber dnan = CBORObject.FromObject(Double.NaN).AsNumber();
      TestCommon.CompareTestEqual(sp, sp);
      TestCommon.CompareTestEqual(sp, dp);
      TestCommon.CompareTestEqual(dp, dp);
      TestCommon.CompareTestEqual(sn, sn);
      TestCommon.CompareTestEqual(sn, dn);
      TestCommon.CompareTestEqual(dn, dn);
      TestCommon.CompareTestEqual(snan, snan);
      TestCommon.CompareTestEqual(snan, dnan);
      TestCommon.CompareTestEqual(dnan, dnan);
      TestCommon.CompareTestLess(sn, sp);
      TestCommon.CompareTestLess(sn, dp);
      TestCommon.CompareTestLess(sn, snan);
      TestCommon.CompareTestLess(sn, dnan);
      TestCommon.CompareTestLess(sp, snan);
      TestCommon.CompareTestLess(sp, dnan);
      TestCommon.CompareTestLess(dn, dp);
      TestCommon.CompareTestLess(dp, dnan);
      TestCommon.CompareTestLess(dn, dnan);
      Assert.assertEquals(1, CBORObject.True.compareTo(null));
      Assert.assertEquals(1, CBORObject.False.compareTo(null));
      Assert.assertEquals(1, CBORObject.Null.compareTo(null));
      Assert.assertEquals(1, CBORObject.NewArray().compareTo(null));
      Assert.assertEquals(1, CBORObject.NewMap().compareTo(null));
      {
        long numberTemp =
          ToObjectTest.TestToFromObjectRoundTrip(100).compareTo(null);
        Assert.assertEquals(1, numberTemp);
      }
      {
        long numberTemp =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NaN).compareTo(null);
        Assert.assertEquals(1, numberTemp);
      }
      TestCommon.CompareTestLess(
        ToObjectTest.TestToFromObjectRoundTrip(0).AsNumber(),
        ToObjectTest.TestToFromObjectRoundTrip(1).AsNumber());
      TestCommon.CompareTestLess(
        ToObjectTest.TestToFromObjectRoundTrip(0.0f).AsNumber(),
        ToObjectTest.TestToFromObjectRoundTrip(1.0f).AsNumber());
      TestCommon.CompareTestLess(
        ToObjectTest.TestToFromObjectRoundTrip(0.0).AsNumber(),
        ToObjectTest.TestToFromObjectRoundTrip(1.0).AsNumber());
      TestCommon.CompareTestEqual(
        CBORObject.FromObject(10).AsNumber(),
        CBORObject.FromObject(ERational.Create(10, 1)).AsNumber());
    }

    @Test
    public void TestContainsKey() {
      // not implemented yet
    }

    @Test
    public void TestCount() {
      Assert.assertEquals(0, CBORObject.True.size());
      Assert.assertEquals(0, CBORObject.False.size());
      Assert.assertEquals(0, CBORObject.NewArray().size());
      Assert.assertEquals(0, CBORObject.NewMap().size());
    }

    @Test
    public void TestDecodeFromBytes() {
      try {
        CBORObject.DecodeFromBytes(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { 0 }, null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { 0x1c });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { 0x1e });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { (byte)0xfe });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeFromBytes(new byte[] { (byte)0xff });
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestDecodeFromBytesNoDuplicateKeys() {
      byte[] bytes;
      bytes = new byte[] { (byte)0xa2, 0x01, 0x00, 0x02, 0x03 };
      try {
        CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=0"));
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x01, 0x00, 0x01, 0x03 };
      try {
        CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=0"));
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x01, 0x00, 0x01, 0x03 };
      try {
        String opts = "allowduplicatekeys=1;useindeflengthstrings=1";
        CBORObject.DecodeFromBytes(bytes,
          new CBOREncodeOptions(opts));
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x60, 0x00, 0x60, 0x03 };
      try {
        CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=0"));
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] {
        (byte)0xa3, 0x60, 0x00, 0x62, 0x41, 0x41, 0x00, 0x60,
        0x03,
       };
      try {
        CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=0"));
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xa2, 0x61, 0x41, 0x00, 0x61, 0x41, 0x03 };
      try {
        CBORObject.DecodeFromBytes(bytes, new
          CBOREncodeOptions("allowduplicatekeys=0"));
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestDecodeSequenceFromBytes() {
      CBORObject[] objs;
      byte[] bytes;
      bytes = new byte[] { 0 };
      objs = CBORObject.DecodeSequenceFromBytes(bytes);
      Assert.assertEquals(1, objs.length);
      Assert.assertEquals(CBORObject.FromObject(0), objs[0]);
      bytes = new byte[] { 0, 1, 2 };
      objs = CBORObject.DecodeSequenceFromBytes(bytes);
      Assert.assertEquals(3, objs.length);
      Assert.assertEquals(CBORObject.FromObject(0), objs[0]);
      Assert.assertEquals(CBORObject.FromObject(1), objs[1]);
      Assert.assertEquals(CBORObject.FromObject(2), objs[2]);
      bytes = new byte[] { 0, 1, 0x61 };
      try {
        CBORObject.DecodeSequenceFromBytes(bytes);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x61 };
      try {
        CBORObject.DecodeSequenceFromBytes(bytes);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0, 1, 0x61, 0x41 };
      objs = CBORObject.DecodeSequenceFromBytes(bytes);
      Assert.assertEquals(3, objs.length);
      Assert.assertEquals(CBORObject.FromObject(0), objs[0]);
      Assert.assertEquals(CBORObject.FromObject(1), objs[1]);
      Assert.assertEquals(CBORObject.FromObject("A"), objs[2]);
      bytes = new byte[] { };
      objs = CBORObject.DecodeSequenceFromBytes(bytes);
      Assert.assertEquals(0, objs.length);
      try {
        CBORObject.DecodeSequenceFromBytes(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.DecodeSequenceFromBytes(bytes, null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestReadSequence() {
      CBORObject[] objs;
      byte[] bytes;
      bytes = new byte[] { 0 };
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        objs = null;
        try {
          objs = CBORObject.ReadSequence(ms);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      Assert.assertEquals(1, objs.length);
      Assert.assertEquals(CBORObject.FromObject(0), objs[0]);
      bytes = new byte[] { 0, 1, 2 };
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        objs = null;
        try {
          objs = CBORObject.ReadSequence(ms);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      Assert.assertEquals(3, objs.length);
      Assert.assertEquals(CBORObject.FromObject(0), objs[0]);
      Assert.assertEquals(CBORObject.FromObject(1), objs[1]);
      Assert.assertEquals(CBORObject.FromObject(2), objs[2]);
      bytes = new byte[] { 0, 1, 0x61 };
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        try {
          CBORObject.ReadSequence(ms);
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
      bytes = new byte[] { 0x61 };
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        try {
          CBORObject.ReadSequence(ms);
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
      bytes = new byte[] { 0, 1, 0x61, 0x41 };
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        objs = null;
        try {
          objs = CBORObject.ReadSequence(ms);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      Assert.assertEquals(3, objs.length);
      Assert.assertEquals(CBORObject.FromObject(0), objs[0]);
      Assert.assertEquals(CBORObject.FromObject(1), objs[1]);
      Assert.assertEquals(CBORObject.FromObject("A"), objs[2]);
      bytes = new byte[] { };
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        objs = null;
        try {
          objs = CBORObject.ReadSequence(ms);
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      Assert.assertEquals(0, objs.length);
      try {
        CBORObject.ReadSequence(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        try {
          CBORObject.ReadSequence(ms, null);
          Assert.fail("Should have failed");
        } catch (NullPointerException ex) {
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
    }

    @Test
    public void TestEncodeFloat64() {
      try {
        RandomGenerator rg = new RandomGenerator();
        CBOREncodeOptions options = new CBOREncodeOptions("float64=true");
        for (int i = 0; i < 10000; ++i) {
          double dbl = 0.0;
          dbl = (i == 0) ? Double.POSITIVE_INFINITY : ((i == 1) ?
              Double.NEGATIVE_INFINITY : RandomObjects.RandomDouble(rg));
          CBORObject cbor = CBORObject.FromObject(dbl);
          byte[] bytes = cbor.EncodeToBytes(options);
          Assert.assertEquals(9, bytes.length);
          TestCommon.AssertEqualsHashCode(
            cbor,
            CBORObject.DecodeFromBytes(bytes));
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            cbor.WriteTo(ms, options);
            bytes = ms.toByteArray();
            Assert.assertEquals(9, bytes.length);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(dbl, ms, options);
            bytes = ms.toByteArray();
            Assert.assertEquals(9, bytes.length);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(cbor, ms, options);
            bytes = ms.toByteArray();
            Assert.assertEquals(9, bytes.length);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          CBORObject cbor2 = CBORObject.NewArray().Add(cbor);
          bytes = cbor2.EncodeToBytes(options);
          TestCommon.AssertEqualsHashCode(
            cbor2,
            CBORObject.DecodeFromBytes(bytes));
          Assert.assertEquals(10, bytes.length);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            cbor2.WriteTo(ms, options);
            bytes = ms.toByteArray();
            Assert.assertEquals(10, bytes.length);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(cbor2, ms, options);
            bytes = ms.toByteArray();
            Assert.assertEquals(10, bytes.length);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          cbor2 = cbor.WithTag(1);
          bytes = cbor2.EncodeToBytes(options);
          Assert.assertEquals(10, bytes.length);
          TestCommon.AssertEqualsHashCode(
            cbor2,
            CBORObject.DecodeFromBytes(bytes));
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            cbor2.WriteTo(ms, options);
            bytes = ms.toByteArray();
            Assert.assertEquals(10, bytes.length);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(cbor2, ms, options);
            bytes = ms.toByteArray();
            Assert.assertEquals(10, bytes.length);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        }
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestEncodeToBytes() {
      // Test minimum data length
      int[] ranges = {
        -24, 23, 1,
        -256, -25, 2,
        24, 255, 2,
        256, 266, 3,
        -266, -257, 3,
        65525, 65535, 3,
        -65536, -65525, 3,
        65536, 65546, 5,
        -65547, -65537, 5,
      };
      String[] bigRanges = {
        "4294967285", "4294967295",
        "4294967296", "4294967306",
        "18446744073709551604", "18446744073709551615",
        "-4294967296", "-4294967286",
        "-4294967306", "-4294967297",
        "-18446744073709551616", "-18446744073709551604",
      };
      int[] bigSizes = { 5, 9, 9, 5, 9, 9 };
      for (int i = 0; i < ranges.length; i += 3) {
        for (int j = ranges[i]; j <= ranges[i + 1]; ++j) {
          CBORObject bcbor = ToObjectTest.TestToFromObjectRoundTrip(j);
          byte[] bytes = CBORTestCommon.CheckEncodeToBytes(bcbor);
          if (bytes.length != ranges[i + 2]) {
            String i2s = TestCommon.IntToString(j);
            Assert.assertEquals(i2s, ranges[i + 2], bytes.length);
          }
          bytes =
            ToObjectTest.TestToFromObjectRoundTrip(j).EncodeToBytes(new
              CBOREncodeOptions(false, false, true));
          if (bytes.length != ranges[i + 2]) {
            String i2s = TestCommon.IntToString(j);
            Assert.assertEquals(i2s, ranges[i + 2], bytes.length);
          }
        }
      }
      String veryLongString = TestCommon.Repeat("x", 10000);
      byte[] stringBytes =
        ToObjectTest.TestToFromObjectRoundTrip(veryLongString)
        .EncodeToBytes(new CBOREncodeOptions(false, false, true));
      Assert.assertEquals(10003, stringBytes.length);
      stringBytes = ToObjectTest.TestToFromObjectRoundTrip(veryLongString)
        .EncodeToBytes(new CBOREncodeOptions(false, true));
      Assert.assertEquals(10003, stringBytes.length);
      for (int i = 0; i < bigRanges.length; i += 2) {
        EInteger bj = EInteger.FromString(bigRanges[i]);
        EInteger valueBjEnd = EInteger.FromString(bigRanges[i + 1]);
        while (bj.compareTo(valueBjEnd)< 0) {
          CBORObject cbor = ToObjectTest.TestToFromObjectRoundTrip(bj);
          byte[] bytes = CBORTestCommon.CheckEncodeToBytes(cbor);
          if (bytes.length != bigSizes[i / 2]) {
            Assert.fail(bj.toString() + "\n" +
              TestCommon.ToByteArrayString(bytes));
          }
          bytes = ToObjectTest.TestToFromObjectRoundTrip(bj)
            .EncodeToBytes(new CBOREncodeOptions(false, false, true));
          if (bytes.length != bigSizes[i / 2]) {
            Assert.fail(bj.toString() + "\n" +
              TestCommon.ToByteArrayString(bytes));
          }
          bj = bj.Add(EInteger.FromInt32(1));
        }
      }
      try {
        CBORObject.True.EncodeToBytes(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestItem() {
      CBORObject cbor;
      CBORObject dummy = CBORObject.True;
      cbor = CBORObject.NewArray().Add(1).Add(2);
      Assert.assertEquals(1, cbor.get(0).AsInt32());
      Assert.assertEquals(2, cbor.get(1).AsInt32());
      Assert.assertEquals(1, cbor.get(CBORObject.FromObject(0)).AsInt32());
      Assert.assertEquals(2, cbor.get(CBORObject.FromObject(1)).AsInt32());
      try {
        dummy = cbor.get(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        dummy = cbor.get(2);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        dummy = cbor.get(CBORObject.FromObject(-1));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        dummy = cbor.get(CBORObject.FromObject(2));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor.set(0, CBORObject.FromObject(3));
      cbor.set(1, CBORObject.FromObject(4));
      Assert.assertEquals(3, cbor.get(0).AsInt32());
      Assert.assertEquals(4, cbor.get(1).AsInt32());
      Assert.assertEquals(3, cbor.get(CBORObject.FromObject(0)).AsInt32());
      Assert.assertEquals(4, cbor.get(CBORObject.FromObject(1)).AsInt32());
      try {
        cbor.set(-1, dummy);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor.set(2, dummy);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor.set(CBORObject.FromObject(-1), dummy);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor.set(CBORObject.FromObject(2), dummy);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      byte[] bytes = new byte[] { 1, 2, 3, 4 };
      CBORObject[] othercbor = new CBORObject[] {
        CBORObject.FromObject(9), CBORObject.True,
        CBORObject.FromObject(bytes),
        CBORObject.False, CBORObject.Null, CBORObject.FromObject("test"),
        CBORObject.FromObject(99999), CBORObject.FromObject(-1),
      };
      for (CBORObject c2 : othercbor) {
        try {
          dummy = c2.get(0);
          Assert.fail("Should have failed");
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        try {
          dummy = c2.get(CBORObject.FromObject(0));
          Assert.fail("Should have failed");
        } catch (IllegalStateException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
      cbor = CBORObject.NewMap().Add(0, 1).Add(-1, 2);
      Assert.assertEquals(1, cbor.get(0).AsInt32());
      Assert.assertEquals(2, cbor.get(-1).AsInt32());
      Assert.assertEquals(1, cbor.get(CBORObject.FromObject(0)).AsInt32());
      Assert.assertEquals(2, cbor.get(CBORObject.FromObject(-1)).AsInt32());
      if (cbor.get(-2) != null) {
        Assert.fail();
      }
      if (cbor.get(2) != null) {
        Assert.fail();
      }
      if (cbor.get("test") != null) {
        Assert.fail();
      }
      if (cbor.get(CBORObject.FromObject(-2)) != null) {
        Assert.fail();
      }
      if (cbor.get(CBORObject.FromObject(2)) != null) {
        Assert.fail();
      }
      if (cbor.get(CBORObject.FromObject("test")) != null) {
        Assert.fail();
      }
      cbor.set(0, CBORObject.FromObject(3));
      cbor.set(-1, CBORObject.FromObject(4));
      Assert.assertEquals(3, cbor.get(0).AsInt32());
      Assert.assertEquals(4, cbor.get(-1).AsInt32());
      Assert.assertEquals(3, cbor.get(CBORObject.FromObject(0)).AsInt32());
      Assert.assertEquals(4, cbor.get(CBORObject.FromObject(-1)).AsInt32());
      try {
        cbor.set(-2, dummy);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(dummy, cbor.get(-2));
      try {
        cbor.set(2, dummy);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor.set(CBORObject.FromObject(-2), dummy);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor.set(CBORObject.FromObject(2), dummy);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(dummy, cbor.get(2));
      try {
        cbor.set(CBORObject.FromObject(-5), dummy);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(dummy, cbor.get(-5));
      try {
        cbor.set(CBORObject.FromObject(5), dummy);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(dummy, cbor.get(-5));
    }

    @Test
    public void TestEquals() {
      byte[] cborbytes = new byte[] {
        (byte)0xd8, 0x1e, (byte)0x82, 0x00, 0x19,
        0x0f, 0x50,
       };
      CBORObject cbor = CBORObject.DecodeFromBytes(cborbytes);
      CBORObject cbor2 = CBORObject.DecodeFromBytes(cborbytes);
      TestCommon.CompareTestEqualAndConsistent(cbor, cbor2);
      ERational erat = ERational.Create(0, 3920);
      cbor2 = ToObjectTest.TestToFromObjectRoundTrip(erat);
      TestCommon.CompareTestEqualAndConsistent(cbor, cbor2);
      cbor2 = ToObjectTest.TestToFromObjectRoundTrip(cbor2);
      TestCommon.CompareTestEqualAndConsistent(cbor, cbor2);
      TestWriteObj(erat, erat);
      erat = ERational.Create(
          EInteger.FromInt32(0),
          EInteger.FromString("84170882933504200501581262010093"));
      cbor = ToObjectTest.TestToFromObjectRoundTrip(erat);
      ERational erat2 = ERational.Create(
          EInteger.FromInt32(0),
          EInteger.FromString("84170882933504200501581262010093"));
      cbor2 = ToObjectTest.TestToFromObjectRoundTrip(erat2);
      TestCommon.CompareTestEqualAndConsistent(cbor, cbor2);
      cbor2 = ToObjectTest.TestToFromObjectRoundTrip(cbor2);
      TestCommon.CompareTestEqualAndConsistent(cbor, cbor2);
      TestWriteObj(cbor, cbor2);
      TestWriteObj(erat, erat2);
    }

    private static void CompareTestNumber(CBORObject o1, CBORObject o2) {
      TestCommon.CompareTestEqual(o1.AsNumber(), o2.AsNumber());
    }

    @Test
    public void TestEquivalentNegativeInfinity() {
      CompareTestNumber(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecNegInf),
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf));
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecNegInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf);
        CompareTestNumber(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecNegInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY);
        CompareTestNumber(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY);
        CompareTestNumber(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY);
        CompareTestNumber(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatNegInf);
        CompareTestNumber(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestEquivalentPositiveInfinity() {
      CompareTestNumber(
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecPosInf),
        ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatPosInf));
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecPosInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.RatPosInf);
        CompareTestNumber(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecPosInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY);
        CompareTestNumber(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatPosInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY);
        CompareTestNumber(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.RatPosInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY);
        CompareTestNumber(objectTemp, objectTemp2);
      }
      {
        CBORObject objectTemp =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.FloatPosInf);
        CBORObject objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.RatPosInf);
        CompareTestNumber(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestFalse() {
      CBORTestCommon.AssertJSONSer(CBORObject.False, "false");
      Assert.assertEquals(
        CBORObject.False,
        ToObjectTest.TestToFromObjectRoundTrip(false));
    }

    @Test(timeout = 100000)
    public void TestFromJSONString() {
      char[] charbuf = new char[4];
      CBORObject cbor;
      // Test single-character strings
      for (int i = 0; i < 0x110000; ++i) {
        if (i >= 0xd800 && i < 0xe000) {
          continue;
        }
        String str = CharString(i, true, charbuf);
        if (i < 0x20 || i == 0x22 || i == 0x5c) {
          TestFailingJSON(str);
        } else {
          cbor = TestSucceedingJSON(str);
          String exp = CharString(i, false, charbuf);
          if (!exp.equals(cbor.AsString())) {
            Assert.assertEquals(exp, cbor.AsString());
          }
        }
      }
      for (String str : ValueJsonFails) {
        TestFailingJSON(str);
      }
      for (String str : ValueJsonSucceeds) {
        TestSucceedingJSON(str);
      }
      try {
        CBORObject.FromJSONString("\ufeff\u0020 {}");
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString("[]", (CBOREncodeOptions)null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromJSONString("[]", (JSONOptions)null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      TestFailingJSON("{\"a\":1,\"a\":2}", ValueNoDuplicateKeys);
      String aba = "{\"a\":1,\"b\":3,\"a\":2}";
      TestFailingJSON(aba, ValueNoDuplicateKeys);
      cbor = TestSucceedingJSON(aba, new JSONOptions("allowduplicatekeys=1"));
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip(2), cbor.get("a"));
      aba = "{\"a\":1,\"a\":4}";
      cbor = TestSucceedingJSON(aba, new JSONOptions("allowduplicatekeys=1"));
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip(4), cbor.get("a"));
      aba = "{\"a\" :1}";
      cbor = TestSucceedingJSON(aba);
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip(1), cbor.get("a"));
      aba = "{\"a\" : 1}";
      cbor = TestSucceedingJSON(aba);
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip(1), cbor.get("a"));
      cbor = TestSucceedingJSON("\"\\t\"");
      {
        String stringTemp = cbor.AsString();
        Assert.assertEquals(
          "\t",
          stringTemp);
      }
      Assert.assertEquals(CBORObject.True, TestSucceedingJSON("true"));
      Assert.assertEquals(CBORObject.False, TestSucceedingJSON("false"));
      Assert.assertEquals(CBORObject.Null, TestSucceedingJSON("null"));
      Assert.assertEquals(5, TestSucceedingJSON(" 5 ").AsInt32());
      {
        String stringTemp = TestSucceedingJSON("\"\\/\\b\"").AsString();
        Assert.assertEquals(
          "/\b",
          stringTemp);
      }
      {
        String stringTemp = TestSucceedingJSON("\"\\/\\f\"").AsString();
        Assert.assertEquals(
          "/\f",
          stringTemp);
      }
      String jsonTemp = TestCommon.Repeat(
          "[",
          2000) + TestCommon.Repeat(
          "]",
          2000);
      TestFailingJSON(jsonTemp);
    }

    @Test
    public void TestTagArray() {
      CBORObject obj = CBORObject.FromObjectAndTag("test", 999);
      EInteger[] etags = obj.GetAllTags();
      Assert.assertEquals(1, etags.length);
      Assert.assertEquals(999, etags[0].ToInt32Checked());
      obj = ToObjectTest.TestToFromObjectRoundTrip("test");
      etags = obj.GetAllTags();
      Assert.assertEquals(0, etags.length);
    }

    @Test
    public void TestEI() {
      CBORObject cbor =
        ToObjectTest.TestToFromObjectRoundTrip(EInteger.FromString("100"));
      if (!(cbor.isNumber())) {
 Assert.fail();
 }
      {
        String stringTemp = cbor.ToJSONString();
        Assert.assertEquals(
          "100",
          stringTemp);
      }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
            "200"));
      if (!(cbor.isNumber())) {
 Assert.fail();
 }
      {
        String stringTemp = cbor.ToJSONString();
        Assert.assertEquals(
          "200",
          stringTemp);
      }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(EFloat.FromString("300"));
      if (!(cbor.isNumber())) {
 Assert.fail();
 }
      {
        String stringTemp = cbor.ToJSONString();
        Assert.assertEquals(
          "300",
          stringTemp);
      }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(ERational.Create(1, 2));
      if (!(cbor.isNumber())) {
 Assert.fail();
 }
    }

    @Test
    public void TestFromObject() {
      CBORObject[] cborarray = new CBORObject[2];
      cborarray[0] = CBORObject.False;
      cborarray[1] = CBORObject.True;
      CBORObject cbor = CBORObject.FromObject(cborarray);
      Assert.assertEquals(2, cbor.size());
      Assert.assertEquals(CBORObject.False, cbor.get(0));
      Assert.assertEquals(CBORObject.True, cbor.get(1));
      CBORTestCommon.AssertRoundTrip(cbor);
      Assert.assertEquals(
        CBORObject.Null,
        CBORObject.FromObject((int[])null));
      long[] longarray = { 2, 3 };
      cbor = CBORObject.FromObject(longarray);
      Assert.assertEquals(2, cbor.size());
      if (!(CBORObject.FromObject(2).compareTo(cbor.get(0))
        == 0))Assert.fail();
      if (!(CBORObject.FromObject(3).compareTo(cbor.get(1))
        == 0))Assert.fail();
      CBORTestCommon.AssertRoundTrip(cbor);
      Assert.assertEquals(
        CBORObject.Null,
        CBORObject.FromObject((ERational)null));
      Assert.assertEquals(
        CBORObject.Null,
        CBORObject.FromObject((EDecimal)null));
      try {
        CBORObject.FromObject(ERational.Create(10, 2));
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }

      try {
        CBORObject.FromObject(CBORObject.FromObject(Double.NaN)
          .signum());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.True;
      try {
        CBORObject.FromObject(cbor.get(0));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor.set(0, CBORObject.False);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor = CBORObject.False;
        CBORObject.FromObject(cbor.getKeys());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip('\udddd');
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject(CBORObject.NewArray().getKeys());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject(CBORObject.NewArray().signum());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject(CBORObject.NewMap().signum());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static void CheckKeyValue(CBORObject o, String key, Object value) {
      if (!o.ContainsKey(key)) {
        Assert.fail("Expected " + key + " to exist: " + o.toString());
      }
      TestCommon.AssertEqualsHashCode(o.get(key), value);
    }

    public enum EnumClass {
      /**
       * Internal API.
       */
      Value1,

      /**
       * Internal API.
       */
      Value2,

      /**
       * Internal API.
       */
      Value3,
    }

    @Test
    public void TestFromObject_Enum() {
      CBORObject cbor;
      cbor = ToObjectTest.TestToFromObjectRoundTrip(EnumClass.Value1);
      Assert.assertEquals(0, cbor.AsInt32());
      cbor = ToObjectTest.TestToFromObjectRoundTrip(EnumClass.Value2);
      Assert.assertEquals(1, cbor.AsInt32());
      cbor = ToObjectTest.TestToFromObjectRoundTrip(EnumClass.Value3);
      Assert.assertEquals(2, cbor.AsInt32());
    }

    @Test
    public void TestToObject_Enum() {
      CBORObject cbor;
      EnumClass ec;
      cbor = CBORObject.FromObject("Value1");
      ec = (EnumClass)cbor.ToObject(EnumClass.class);
      Assert.assertEquals(EnumClass.Value1, ec);
      cbor = CBORObject.FromObject("Value2");
      ec = (EnumClass)cbor.ToObject(EnumClass.class);
      Assert.assertEquals(EnumClass.Value2, ec);
      cbor = CBORObject.FromObject("Value3");
      ec = (EnumClass)cbor.ToObject(EnumClass.class);
      Assert.assertEquals(EnumClass.Value3, ec);
      cbor = CBORObject.FromObject("ValueXYZ");
      try {
        cbor.ToObject(EnumClass.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.FromObject(true);
      try {
        cbor.ToObject(EnumClass.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestToObject_UnknownEnum() {
      CBORObject cbor;
      cbor = CBORObject.FromObject(999);
      try {
        cbor.ToObject(EnumClass.class);
        Assert.fail("Should have failed -- " +
          cbor.ToObject(EnumClass.class));
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static final class TestConverter implements ICBORToFromConverter<String> {
      public CBORObject ToCBORObject(String strValue) {
        return CBORObject.FromObject(
            DataUtilities.ToLowerCaseAscii(strValue));
      }

      public String FromCBORObject(CBORObject cbor) {
        if (cbor == null) {
          throw new NullPointerException("cbor");
        }
        if (cbor.getType() == CBORType.TextString) {
          return DataUtilities.ToLowerCaseAscii(cbor.AsString());
        }
        throw new CBORException();
      }
    }

    @Test
    public void TestFromObject_TypeMapper() {
      CBORTypeMapper mapper = new CBORTypeMapper()
      .AddConverter(String.class, new TestConverter());
      CBORObject cbor = CBORObject.FromObject("UPPER", mapper);
      Assert.assertEquals(CBORType.TextString, cbor.getType());
      {
        String stringTemp = cbor.AsString();
        Assert.assertEquals(
          "upper",
          stringTemp);
      }
      cbor = CBORObject.FromObject("LoWeR", mapper);
      Assert.assertEquals(CBORType.TextString, cbor.getType());
      {
        String stringTemp = cbor.AsString();
        Assert.assertEquals(
          "lower",
          stringTemp);
      }
    }

    @Test
    public void TestFromObject_Dictionary() {
      Map<String, String> dict = new HashMap<String, String>();
      dict.put("TestKey","TestValue");
      dict.put("TestKey2","TestValue2");
      CBORObject c = CBORObject.FromObject(dict);
      CheckKeyValue(c, "TestKey", "TestValue");
      CheckKeyValue(c, "TestKey2", "TestValue2");
      dict = (Map<String, String>)c.ToObject(
          (new java.lang.reflect.ParameterizedType() {public java.lang.reflect.Type[] getActualTypeArguments() {return new java.lang.reflect.Type[] { String.class, String.class };}public java.lang.reflect.Type getRawType() { return Map.class; } public java.lang.reflect.Type getOwnerType() { return null; }}));
      Assert.assertEquals(2, dict.keySet().size());
      if (!(dict.containsKey("TestKey"))) {
 Assert.fail();
 }
      if (!(dict.containsKey("TestKey2"))) {
 Assert.fail();
 }
      Assert.assertEquals("TestValue", dict.get("TestKey"));
      Assert.assertEquals("TestValue2", dict.get("TestKey2"));
    }

    // nesting a public type is needed
    // here for testing purposes
    public final class NestedPODClass {
      public NestedPODClass() {
        this.propVarpropvalue = new PODClass();
      }

      public final PODClass getPropValue() { return propVarpropvalue; }
private final PODClass propVarpropvalue;
    }

    @Test
    public void TestBase64Extras() {
      // Base64 tests
      CBORObject o;
      o = CBORObject.FromObjectAndTag(
          new byte[] { (byte)0x9a, (byte)0xd6, (byte)0xf0, (byte)0xe8 },
          23);
      {
        String stringTemp = o.ToJSONString();
        Assert.assertEquals(
          "\"9AD6F0E8\"",
          stringTemp);
      }
      o = ToObjectTest.TestToFromObjectRoundTrip(new byte[] {
        (byte)0x9a, (byte)0xd6,
        (byte)0xff, (byte)0xe8,
       });
      // Encode with Base64URL by default
      {
        String stringTemp = o.ToJSONString();
        Assert.assertEquals(
          "\"mtb_6A\"",
          stringTemp);
      }
      o = CBORObject.FromObjectAndTag(
          new byte[] { (byte)0x9a, (byte)0xd6, (byte)0xff, (byte)0xe8 },
          22);
      // Encode with Base64
      {
        String stringTemp = o.ToJSONString();
        Assert.assertEquals(
          "\"mtb/6A==\"",
          stringTemp);
      }
      JSONOptions options = new JSONOptions("base64padding=1");
      o = ToObjectTest.TestToFromObjectRoundTrip(new byte[] {
        (byte)0x9a, (byte)0xd6,
        (byte)0xff, (byte)0xe8,
       });
      // Encode with Base64URL by default
      {
        String stringTemp = o.ToJSONString(options);
        Assert.assertEquals(
          "\"mtb_6A\"",
          stringTemp);
      }
      o = CBORObject.FromObjectAndTag(
          new byte[] { (byte)0x9a, (byte)0xd6, (byte)0xff, (byte)0xe8 },
          22);
      // Encode with Base64
      {
        String stringTemp = o.ToJSONString(options);
        Assert.assertEquals(
          "\"mtb/6A==\"",
          stringTemp);
      }
    }

    @Test
    public void TestFromObject_PODOptions() {
      PODClass ao = new PODClass();
      PODOptions valueCcTF = new PODOptions(true, false);
      PODOptions valueCcFF = new PODOptions(false, false);
      PODOptions valueCcFT = new PODOptions(false, true);
      PODOptions valueCcTT = new PODOptions(true, true);
      CBORObject co;
      CBORObjectTest.CheckPropertyNames(ao);
      PODClass[] arrao = new PODClass[] { ao, ao };
      co = CBORObject.FromObject(arrao, valueCcTF);
      CBORObjectTest.CheckArrayPropertyNames(
        CBORObject.FromObject(arrao, valueCcTF),
        2,
        "PropA",
        "PropB",
        "IsPropC");
      CBORObjectTest.CheckArrayPropertyNames(
        CBORObject.FromObject(arrao, valueCcFT),
        2,
        "propA",
        "propB",
        "propC");
      CBORObjectTest.CheckArrayPropertyNames(
        CBORObject.FromObject(arrao, valueCcTT),
        2,
        "propA",
        "propB",
        "propC");
      NestedPODClass ao2 = new NestedPODClass();
      CBORObjectTest.CheckPODPropertyNames(
        CBORObject.FromObject(ao2, valueCcTF),
        valueCcTF,
        "PropA",
        "PropB",
        "IsPropC");
      CBORObjectTest.CheckPODPropertyNames(
        CBORObject.FromObject(ao2, valueCcFT),
        valueCcFT,
        "propA",
        "propB",
        "propC");
      CBORObjectTest.CheckPODPropertyNames(
        CBORObject.FromObject(ao2, valueCcTT),
        valueCcTT,
        "propA",
        "propB",
        "propC");
      HashMap<String, Object> aodict = new HashMap<String, Object>();
      aodict.put("PropValue",new PODClass());

      CBORObjectTest.CheckPODInDictPropertyNames(
        CBORObject.FromObject(aodict, valueCcTF),
        "PropA",
        "PropB",
        "IsPropC");
      CBORObjectTest.CheckPODInDictPropertyNames(
        CBORObject.FromObject(aodict, valueCcFT),
        "propA",
        "propB",
        "propC");
      CBORObjectTest.CheckPODInDictPropertyNames(
        CBORObject.FromObject(aodict, valueCcTT),
        "propA",
        "propB",
        "propC");
      CBORObjectTest.CheckArrayPropertyNames(
        CBORObject.FromObject(arrao, valueCcFF),
        2,
        "PropA",
        "PropB",
        "IsPropC");
      CBORObjectTest.CheckPODPropertyNames(
        CBORObject.FromObject(ao2, valueCcFF),
        valueCcFF,
        "PropA",
        "PropB",
        "IsPropC");
      CBORObjectTest.CheckPODInDictPropertyNames(
        CBORObject.FromObject(aodict, valueCcFF),
        "PropA",
        "PropB",
        "IsPropC");
    }

    @Test
    public void TestFromObjectAndTag() {
      EInteger bigvalue = EInteger.FromString("99999999999999999999999999999");
      try {
        CBORObject.FromObjectAndTag(2, bigvalue);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObjectAndTag(2, -1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObjectAndTag(CBORObject.Null, -1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObjectAndTag(CBORObject.Null, 999999);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      EInteger eintNull = null;
      try {
        CBORObject.FromObjectAndTag(2, eintNull);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObjectAndTag(2, EInteger.FromString("-1"));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestFromSimpleValue() {
      try {
        CBORObject.FromSimpleValue(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromSimpleValue(256);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      for (int i = 0; i < 256; ++i) {
        if (i >= 24 && i < 32) {
          try {
            CBORObject.FromSimpleValue(i);
            Assert.fail("Should have failed");
          } catch (IllegalArgumentException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
        } else {
          CBORObject cbor = CBORObject.FromSimpleValue(i);
          Assert.assertEquals(i, cbor.getSimpleValue());
        }
      }
    }

    @Test
    public void TestWithTag() {
      EInteger bigvalue = EInteger.FromString("99999999999999999999999999999");
      try {
        CBORObject.FromObject(2).WithTag(bigvalue);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject(2).WithTag(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject(CBORObject.Null).WithTag(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject(CBORObject.Null).WithTag(999999);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      EInteger eintNull = null;
      try {
        CBORObject.FromObject(2).WithTag(eintNull);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromObject(2).WithTag(EInteger.FromString("-1"));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestGetByteString() {
      try {
        CBORObject.True.GetByteString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(0).GetByteString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("test").GetByteString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.GetByteString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().GetByteString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().GetByteString();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestGetHashCode() {
      // not implemented yet
    }

    @Test
    public void TestGetTags() {
      // not implemented yet
    }

    @Test
    public void TestHasTag() {
      try {
        CBORObject.True.HasTag(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        EInteger ValueBigintNull = null;
        CBORObject.True.HasTag(ValueBigintNull);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.HasTag(EInteger.FromString("-1"));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      if (CBORObject.True.HasTag(0)) {
 Assert.fail();
 }
      if (CBORObject.True.HasTag(EInteger.FromInt32(0))) {
 Assert.fail();
 }
    }

    @Test
    public void TestMostInnerTag() {
      // not implemented yet
    }

    @Test
    public void TestInsert() {
      // not implemented yet
    }

    @Test
    public void TestIsFalse() {
      // not implemented yet
    }

    @Test
    public void TestIsFinite() {
      CBORObject cbor;
      if (!(ToObjectTest.TestToFromObjectRoundTrip(0).isFinite())) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip("")
        .isFinite()) {
 Assert.fail();
 }
      if (CBORObject.NewArray().isFinite()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().isFinite()) {
 Assert.fail();
 }
      cbor = CBORObject.True;
      if (cbor.isFinite()) {
 Assert.fail();
 }
      cbor = CBORObject.False;
      if (cbor.isFinite()) {
 Assert.fail();
 }
      cbor = CBORObject.Null;
      if (cbor.isFinite()) {
 Assert.fail();
 }
      cbor = CBORObject.Undefined;
      if (cbor.isFinite()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().isFinite()) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(0).isFinite())) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(2.5).isFinite())) {
 Assert.fail();
 }
      if (
        ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
        .isFinite()) {
 Assert.fail();
 }

      if (
        ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
        .isFinite()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
        .isFinite()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(
          CBORTestCommon.DecPosInf).isFinite()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(
          CBORTestCommon.DecNegInf).isFinite()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(EDecimal.NaN)
        .isFinite()) {
 Assert.fail();
 }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        EDecimal ed = EDecimal.FromString(numberinfo.get("number").AsString());
        CBORObject cbornumber = ToObjectTest.TestToFromObjectRoundTrip(ed);
        if (numberinfo.get("isintegral").AsBoolean()) {
          if (!(cbornumber.isFinite())) {
 Assert.fail(numberinfo.get("number").AsString());
 }
        }
        // NOTE: A nonintegral number is not necessarily non-finite
      }
    }

    @Test
    public void TestIsInfinity() {
      if (!(CBORObject.PositiveInfinity.AsNumber().IsInfinity())) {
 Assert.fail();
 }
      if (!(CBORObject.NegativeInfinity.AsNumber().IsInfinity())) {
 Assert.fail();
 }
      if (!(CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xfa, 0x7f,
        (byte)0x80, 0x00, 0x00,
       }).AsNumber().IsInfinity()))Assert.fail();
    }

    @Test
    public void TestIsIntegral() {
      CBORObject cbor;
      if (!(ToObjectTest.TestToFromObjectRoundTrip(0).isIntegral())) {
 Assert.fail();
 }
      cbor = ToObjectTest.TestToFromObjectRoundTrip("");
      if (cbor.isIntegral()) {
 Assert.fail();
 }
      if (CBORObject.NewArray().isIntegral()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().isIntegral()) {
 Assert.fail();
 }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(
          EInteger.FromRadixString(
            "8000000000000000",
            16));
      if (!(cbor.isIntegral())) {
 Assert.fail();
 }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(
          EInteger.FromRadixString(
            "80000000000000000000",
            16));
      if (!(cbor.isIntegral())) {
 Assert.fail();
 }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(
          EInteger.FromRadixString(
            "8000000000000000000000000",
            16));
      if (!(cbor.isIntegral())) {
 Assert.fail();
 }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(
          EDecimal.FromString("4444e+800")).isIntegral()))Assert.fail();

      if (ToObjectTest.TestToFromObjectRoundTrip(
          EDecimal.FromString("4444e-800")).isIntegral()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(2.5).isIntegral()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(
          999.99).isIntegral()) {
 Assert.fail();
 }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY);

      if (cbor.isIntegral()) {
 Assert.fail();
 }

      cbor = ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY);

      if (cbor.isIntegral()) {
 Assert.fail();
 }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(Double.NaN);

      if (cbor.isIntegral()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(
          CBORTestCommon.DecPosInf).isIntegral()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip(
          CBORTestCommon.DecNegInf).isIntegral()) {
 Assert.fail();
 }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(EDecimal.NaN);

      if (cbor.isIntegral()) {
 Assert.fail();
 }
      cbor = CBORObject.True;
      if (cbor.isIntegral()) {
 Assert.fail();
 }
      cbor = CBORObject.False;
      if (cbor.isIntegral()) {
 Assert.fail();
 }
      cbor = CBORObject.Null;
      if (cbor.isIntegral()) {
 Assert.fail();
 }
      cbor = CBORObject.Undefined;
      if (cbor.isIntegral()) {
 Assert.fail();
 }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberinfo.get("number").AsString()));
        if (numberinfo.get("isintegral").AsBoolean()) {
          if (!(cbornumber.isIntegral())) {
 Assert.fail();
 }
          if (cbornumber.AsNumber().IsPositiveInfinity()) {
 Assert.fail();
 }
          if (cbornumber.AsNumber().IsNegativeInfinity()) {
 Assert.fail();
 }
          if (cbornumber.AsNumber().IsNaN()) {
 Assert.fail();
 }
          if (cbornumber.isNull()) {
 Assert.fail();
 }
        } else {
          if (cbornumber.isIntegral()) {
 Assert.fail();
 }
        }
      }
    }

    @Test
    public void TestAsNumber() {
      try {
        CBORObject.True.AsNumber();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("").AsNumber();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().AsNumber();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsNumber();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsNumber();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Null.AsNumber();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsNumber();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestAsNumberIsNegativeInfinity() {
      if (CBORObject.FromObject(
          0).AsNumber().IsNegativeInfinity()) {
 Assert.fail();
 }

      if (
        CBORObject.PositiveInfinity.AsNumber().IsNegativeInfinity()) {
 Assert.fail();
 }

      if (!(
        CBORObject.NegativeInfinity.AsNumber().IsNegativeInfinity())) {
 Assert.fail();
 }
      if (CBORObject.NaN.AsNumber().IsNegativeInfinity()) {
 Assert.fail();
 }
    }

    @Test
    public void TestIsNull() {
      if (CBORObject.True.isNull()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip("")
        .isNull()) {
 Assert.fail();
 }
      if (CBORObject.NewArray().isNull()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().isNull()) {
 Assert.fail();
 }
      if (CBORObject.False.isNull()) {
 Assert.fail();
 }
      if (!(CBORObject.Null.isNull())) {
 Assert.fail();
 }
      if (CBORObject.Undefined.isNull()) {
 Assert.fail();
 }
      if (CBORObject.PositiveInfinity.isNull()) {
 Assert.fail();
 }
      if (CBORObject.NegativeInfinity.isNull()) {
 Assert.fail();
 }
      if (CBORObject.NaN.isNull()) {
 Assert.fail();
 }
    }

    @Test
    public void TestAsNumberIsPositiveInfinity() {
      if (CBORObject.FromObject(
          0).AsNumber().IsPositiveInfinity()) {
 Assert.fail();
 }

      if (!(
        CBORObject.PositiveInfinity.AsNumber().IsPositiveInfinity())) {
 Assert.fail();
 }

      if (
        CBORObject.NegativeInfinity.AsNumber().IsPositiveInfinity()) {
 Assert.fail();
 }
      if (CBORObject.NaN.AsNumber().IsPositiveInfinity()) {
 Assert.fail();
 }
    }

    @Test
    public void TestIsTagged() {
      // not implemented yet
    }

    @Test
    public void TestIsTrue() {
      // not implemented yet
    }

    @Test
    public void TestIsUndefined() {
      if (CBORObject.True.isUndefined()) {
 Assert.fail();
 }
      if (ToObjectTest.TestToFromObjectRoundTrip("")
        .isUndefined()) {
 Assert.fail();
 }
      if (CBORObject.NewArray().isUndefined()) {
 Assert.fail();
 }
      if (CBORObject.NewMap().isUndefined()) {
 Assert.fail();
 }
      if (CBORObject.False.isUndefined()) {
 Assert.fail();
 }
      if (CBORObject.Null.isUndefined()) {
 Assert.fail();
 }
      if (!(CBORObject.Undefined.isUndefined())) {
 Assert.fail();
 }
      if (CBORObject.PositiveInfinity.isUndefined()) {
 Assert.fail();
 }
      if (CBORObject.NegativeInfinity.isUndefined()) {
 Assert.fail();
 }
      if (CBORObject.NaN.isUndefined()) {
 Assert.fail();
 }
    }

    @Test
    public void TestIsZero() {
      // not implemented yet
    }

    @Test
    public void TestItem2() {
      CBORObject cbor = CBORObject.True;
      try {
        CBORObject cbor2 = cbor.get(0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.False;
      try {
        CBORObject cbor2 = cbor.get(0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(0);
      try {
        CBORObject cbor2 = cbor.get(0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = ToObjectTest.TestToFromObjectRoundTrip(2);
      try {
        CBORObject cbor2 = cbor.get(0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewArray();
      try {
        CBORObject cbor2 = cbor.get(0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestGetOrDefault() {
      CBORObject cbor = CBORObject.NewArray().Add(2).Add(3).Add(7);
      {
        Object objectTemp = CBORObject.Null;
        Object objectTemp2 = cbor.GetOrDefault(-1,
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORObject.FromObject(2);
        Object objectTemp2 = cbor.GetOrDefault(
            0,
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      Assert.assertEquals(
        CBORObject.FromObject(2),
        cbor.GetOrDefault(CBORObject.FromObject(0), CBORObject.Null));
      {
        Object objectTemp = CBORObject.FromObject(3);
        Object objectTemp2 = cbor.GetOrDefault(
            1,
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORObject.FromObject(7);
        Object objectTemp2 = cbor.GetOrDefault(
            2,
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      Assert.assertEquals(CBORObject.Null, cbor.GetOrDefault(3, CBORObject.Null));
      {
        Object objectTemp = CBORObject.Null;
        Object objectTemp2 = cbor.GetOrDefault("key",
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      cbor = CBORObject.NewMap().Add(1, 2).Add("key", "value");
      {
        Object objectTemp = CBORObject.Null;
        Object objectTemp2 = cbor.GetOrDefault(-1,
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      Assert.assertEquals(CBORObject.Null, cbor.GetOrDefault(0, CBORObject.Null));
      {
        Object objectTemp = CBORObject.FromObject(2);
        Object objectTemp2 = cbor.GetOrDefault(
            1,
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      Assert.assertEquals(CBORObject.Null, cbor.GetOrDefault(2, CBORObject.Null));
      Assert.assertEquals(CBORObject.Null, cbor.GetOrDefault(3, CBORObject.Null));
      {
        Object objectTemp = CBORObject.FromObject("value");
        Object objectTemp2 = cbor.GetOrDefault(
            "key",
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      Assert.assertEquals(
        CBORObject.FromObject("value"),
        cbor.GetOrDefault(CBORObject.FromObject("key"), CBORObject.Null));
      {
        Object objectTemp = CBORObject.Null;
        Object objectTemp2 = cbor.GetOrDefault("key2",
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      cbor = CBORObject.False;
      {
        Object objectTemp = CBORObject.Null;
        Object objectTemp2 = cbor.GetOrDefault(-1,
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      Assert.assertEquals(CBORObject.Null, cbor.GetOrDefault(0, CBORObject.Null));
      {
        Object objectTemp = CBORObject.Null;
        Object objectTemp2 = cbor.GetOrDefault("key",
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORObject.Null;
        Object objectTemp2 = cbor.GetOrDefault("key2",
            CBORObject.Null);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    private static void Sink(Object obj) {
      System.out.println("Sink for " + obj);
      Assert.fail();
    }

    @Test
    public void TestKeys() {
      CBORObject co;
      try {
        co = CBORObject.True;
        Sink(co.getKeys());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Sink(ToObjectTest.TestToFromObjectRoundTrip(0).getKeys());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Sink(ToObjectTest.TestToFromObjectRoundTrip("String").getKeys());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Sink(CBORObject.NewArray().getKeys());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Sink(ToObjectTest.TestToFromObjectRoundTrip(
            new byte[] { 0 }).getKeys());
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      if (CBORObject.NewMap().getKeys() == null) {
        Assert.fail();
      }
    }

    @Test(timeout = 200000)
    public void TestAsNumberMultiply() {
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 = CBORTestCommon.RandomNumber(r);
        CBORObject o2 = CBORTestCommon.RandomNumber(r);
        EDecimal cmpDecFrac = AsED(o1).Multiply(AsED(o2));
        EDecimal cmpCobj = o1.AsNumber().Multiply(o2.AsNumber()).ToEDecimal();
        if (!cmpDecFrac.equals(cmpCobj)) {
          TestCommon.CompareTestEqual(
            cmpDecFrac,
            cmpCobj,
            o1.toString() + "\n" + o2.toString());
        }
      }
    }

    @Test
    public void TestAsNumberNegate() {
      TestCommon.CompareTestEqual(
        ToObjectTest.TestToFromObjectRoundTrip(2).AsNumber(),
        ToObjectTest.TestToFromObjectRoundTrip(-2).AsNumber().Negate());
      TestCommon.CompareTestEqual(
        ToObjectTest.TestToFromObjectRoundTrip(-2).AsNumber(),
        ToObjectTest.TestToFromObjectRoundTrip(2).AsNumber().Negate());
    }

    @Test
    public void TestNegativeTenDigitLong() {
      CBORObject obj = CBORObject.FromJSONString("-1000000000");
      {
        String stringTemp = obj.ToJSONString();
        Assert.assertEquals(
          "-1000000000",
          stringTemp);
      }
      {
        String stringTemp = obj.toString();
        Assert.assertEquals(
          "-1000000000",
          stringTemp);
      }
    }

    @Test
    public void TestNegativeZero() {
      CBORObject negzero = ToObjectTest.TestToFromObjectRoundTrip(
          EDecimal.FromString("-0"));
      CBORTestCommon.AssertRoundTrip(negzero);
    }

    @Test
    public void TestNewArray() {
      // not implemented yet
    }

    @Test
    public void TestNewMap() {
      // not implemented yet
    }

    @Test
    public void TestOperatorAddition() {
      // not implemented yet
    }

    @Test
    public void TestOperatorDivision() {
      // not implemented yet
    }

    @Test
    public void TestOperatorModulus() {
      // not implemented yet
    }

    @Test
    public void TestOperatorMultiply() {
      // not implemented yet
    }

    @Test
    public void TestOperatorSubtraction() {
      // not implemented yet
    }

    @Test
    public void TestMostOuterTag() {
      CBORObject cbor = CBORObject.FromObjectAndTag(CBORObject.True, 999);
      cbor = CBORObject.FromObjectAndTag(CBORObject.True, 1000);
      Assert.assertEquals(EInteger.FromString("1000"), cbor.getMostOuterTag());
      cbor = CBORObject.True;
      Assert.assertEquals(EInteger.FromString("-1"), cbor.getMostOuterTag());
    }

    @Test
    public void TestRead() {
      try {
        CBORObject.Read(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        {
          java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(new byte[] { 0 });

          try {
            CBORObject.Read(ms2, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (ms2 != null) { ms2.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    public static void ExpectJsonSequenceError(byte[] bytes) {
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

        try {
          CBORObject.ReadJSONSequence(ms);
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
    }

    public static void ExpectJsonSequenceZero(byte[] bytes) {
      try {
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

          String ss = TestCommon.ToByteArrayString(bytes);
          CBORObject[] array = CBORObject.ReadJSONSequence(ms);
          Assert.assertEquals(ss, 0, array.length);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ioe) {
        throw new IllegalStateException(ioe.getMessage(), ioe);
      }
    }

    public static void ExpectJsonSequenceOne(byte[] bytes, CBORObject o1) {
      try {
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

          String ss = TestCommon.ToByteArrayString(bytes);
          CBORObject[] array = CBORObject.ReadJSONSequence(ms);
          Assert.assertEquals(ss, 1, array.length);
          Assert.assertEquals(ss, o1, array[0]);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ioe) {
        throw new IllegalStateException(ioe.getMessage(), ioe);
      }
    }

    public static void ExpectJsonSequenceTwo(
      byte[] bytes,
      CBORObject o1,
      CBORObject o2) {
      try {
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

          String ss = TestCommon.ToByteArrayString(bytes);
          CBORObject[] array = CBORObject.ReadJSONSequence(ms);
          Assert.assertEquals(ss, 2, array.length);
          Assert.assertEquals(ss, o1, array[0]);
          Assert.assertEquals(ss, o2, array[1]);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ioe) {
        throw new IllegalStateException(ioe.getMessage(), ioe);
      }
    }

    @Test
    public void TestJsonSequence() {
      byte[] bytes;
      bytes = new byte[] { };
      ExpectJsonSequenceZero(bytes);
      bytes = new byte[] { 0x1e, 0x22, 0x41, 0x22, 0x0a };
      ExpectJsonSequenceOne(bytes, CBORObject.FromObject("A"));
      bytes = new byte[] { 0x1e, 0x22, 0x41, 0x22, 0x20 };
      ExpectJsonSequenceOne(bytes, CBORObject.FromObject("A"));
      bytes = new byte[] { 0x1e, 0x22, 0x41, 0x22, 0x09 };
      ExpectJsonSequenceOne(bytes, CBORObject.FromObject("A"));
      bytes = new byte[] { 0x1e, 0x22, 0x41, 0x22, 0x0d };
      ExpectJsonSequenceOne(bytes, CBORObject.FromObject("A"));
      bytes = new byte[] {
        0x1e, (byte)0x66, (byte)0x61, (byte)0x6c, (byte)0x73,
        (byte)0x65, 0x0a,
       };
      ExpectJsonSequenceOne(bytes, CBORObject.False);
      bytes = new byte[] {
        0x1e, (byte)0x66, (byte)0x61, (byte)0x6c, (byte)0x73,
        (byte)0x65,
       };
      ExpectJsonSequenceOne(bytes, null);
      bytes = new byte[] {
        0x1e, (byte)0x66, (byte)0x61, (byte)0x6c, (byte)0x73,
        (byte)0x65, (byte)0x74, (byte)0x72, (byte)0x75, (byte)0x65, 0x0a,
       };
      ExpectJsonSequenceOne(bytes, null);
      bytes = new byte[] {
        0x1e, (byte)0x74, (byte)0x72, (byte)0x75, (byte)0x65,
        0x0a,
       };
      ExpectJsonSequenceOne(bytes, CBORObject.True);
      bytes = new byte[] {
        0x1e, (byte)0x74, (byte)0x72, (byte)0x75,
        (byte)0x65,
       };
      ExpectJsonSequenceOne(bytes, null);
      bytes = new byte[] {
        0x1e, (byte)0x6e, (byte)0x75, (byte)0x6c, (byte)0x6c,
        0x0a,
       };
      ExpectJsonSequenceOne(bytes, CBORObject.Null);
      bytes = new byte[] {
        0x1e, (byte)0x6e, (byte)0x75, (byte)0x6c,
        (byte)0x6c,
       };
      ExpectJsonSequenceOne(bytes, null);
      bytes = new byte[] { 0x1e, 0x22, 0x41, 0x22, 0x1e, (byte)'[', (byte)']' };
      ExpectJsonSequenceTwo(
        bytes,
        CBORObject.FromObject("A"),
        CBORObject.NewArray());
      bytes = new byte[] {
        0x1e, 0x22, 0x41, 0x22, 0x0a, 0x1e, (byte)'[',
        (byte)']',
       };
      ExpectJsonSequenceTwo(
        bytes,
        CBORObject.FromObject("A"),
        CBORObject.NewArray());
      bytes = new byte[] {
        0x1e, 0x22, 0x41, 0x22, 0x41, 0x1e, (byte)'[',
        (byte)']',
       };
      ExpectJsonSequenceTwo(bytes, null, CBORObject.NewArray());
      bytes = new byte[] { 0x1e, 0x1e, 0x22, 0x41, 0x22, 0x0a };
      ExpectJsonSequenceOne(bytes, CBORObject.FromObject("A"));
      bytes = new byte[] { 0x1e, 0x1e, 0x30, 0x0a };
      ExpectJsonSequenceOne(bytes, CBORObject.FromObject(0));
      bytes = new byte[] { 0x1e, 0x1e, (byte)0xef, (byte)0xbb, (byte)0xbf, 0x30, 0x0a };
      ExpectJsonSequenceOne(bytes, null);
      bytes = new byte[] {
        0x1e, 0x1e, (byte)0xef, (byte)0xbb, (byte)0xbf, 0x30, 0x0a, 0x1e, 0x30,
        0x0a,
       };
      ExpectJsonSequenceTwo(bytes, null, CBORObject.FromObject(0));
      bytes = new byte[] { 0x22, 0x41, 0x22, 0x0a };
      ExpectJsonSequenceError(bytes);
      bytes = new byte[] { (byte)0xef, (byte)0xbb, (byte)0xbf, 0x1e, 0x30, 0x0a };
      ExpectJsonSequenceError(bytes);
      bytes = new byte[] { (byte)0xfe, (byte)0xff, 0x00, 0x1e, 0, 0x30, 0, 0x0a };
      ExpectJsonSequenceError(bytes);
      bytes = new byte[] { (byte)0xff, (byte)0xfe, 0x1e, 0, 0x30, 0, 0x0a, 0 };
      ExpectJsonSequenceError(bytes);
      bytes = new byte[] { 0x1e, 0x22, 0x41, 0x22, 0x0a, 0x31, 0x31 };
      ExpectJsonSequenceOne(bytes, null);
      bytes = new byte[] { 0x1e, 0x22, 0x41, 0x22, 0x0a, 0x1e };
      ExpectJsonSequenceTwo(bytes, CBORObject.FromObject("A"), null);
      bytes = new byte[] { 0x1e, 0x22, 0x41, 0x22, 0x0a, 0x31, 0x31, 0x1e };
      ExpectJsonSequenceTwo(bytes, null, null);
      bytes = new byte[] { 0x1e };
      ExpectJsonSequenceOne(bytes, null);
      bytes = new byte[] { 0x1e, 0x1e };
      ExpectJsonSequenceOne(bytes, null);
    }

    @Test
    public void TestNonUtf8FromJSONBytes() {
      byte[] bytes;
      CBORObject cbor;
      bytes = new byte[] { 0x31, 0, 0x31, 0 };
      cbor = CBORObject.FromJSONBytes(bytes);
      Assert.assertEquals(CBORObject.FromObject(11), cbor);
      bytes = new byte[] { 0x31, 0, 0, 0, 0x31, 0, 0, 0 };
      cbor = CBORObject.FromJSONBytes(bytes);
      Assert.assertEquals(CBORObject.FromObject(11), cbor);
    }

    @Test
    public void TestReadJSON() {
      try {
        {
          java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(new byte[] { 0x30 });

          try {
            CBORObject.ReadJSON(ms2, (CBOREncodeOptions)null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          try {
            CBORObject.ReadJSON(ms2, (JSONOptions)null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (ms2 != null) { ms2.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xef, (byte)0xbb, (byte)0xbf,
          0x7b, 0x7d,
         });

          try {
            CBORObject.ReadJSON(ms);
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        // whitespace followed by BOM
        {
          java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(new byte[] {
          0x20, (byte)0xef, (byte)0xbb,
          (byte)0xbf, 0x7b, 0x7d,
         });

          try {
            CBORObject.ReadJSON(ms2);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (ms2 != null) { ms2.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream ms2a = null;
try {
ms2a = new java.io.ByteArrayInputStream(new byte[] { 0x7b, 0x05, 0x7d });

          try {
            CBORObject.ReadJSON(ms2a);
            Assert.fail("Should have failed");
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
ms2b = new java.io.ByteArrayInputStream(new byte[] { 0x05, 0x7b, 0x7d });

          try {
            CBORObject.ReadJSON(ms2b);
            Assert.fail("Should have failed");
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
        // two BOMs
        {
          java.io.ByteArrayInputStream ms3 = null;
try {
ms3 = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xef, (byte)0xbb, (byte)0xbf,
          (byte)0xef, (byte)0xbb, (byte)0xbf, 0x7b, 0x7d,
         });

          try {
            CBORObject.ReadJSON(ms3);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (ms3 != null) { ms3.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0, (byte)0xfe, (byte)0xff, 0,
          0,
          0,
          0x74, 0, 0, 0, 0x72, 0, 0, 0, 0x75, 0, 0, 0,
          0x65,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0, 0, 0x74, 0, 0,
          0, 0x72, 0,
          0, 0, 0x75, 0, 0, 0, 0x65,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0, 0,
          0x74, 0, 0, 0,
          0x72, 0, 0, 0, 0x75, 0, 0, 0, 0x65, 0, 0, 0,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0x74, 0, 0, 0, 0x72,
          0,
          0,
          0,
          0x75, 0, 0, 0, 0x65, 0, 0, 0,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, 0, 0x74,
          0, 0x72, 0,
          0x75, 0, 0x65,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0x74, 0, 0x72, 0,
          0x75, 0, 0x65,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x74, 0,
          0x72,
          0,
          0x75,
          0, 0x65, 0,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0x74, 0, 0x72, 0,
          0x75, 0, 0x65, 0,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xef, (byte)0xbb, (byte)0xbf,
          0x74, 0x72, 0x75, 0x65,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0x74, 0x72, 0x75,
          0x65,
         });

          Assert.assertEquals(CBORObject.True, CBORObject.ReadJSON(msjson));
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0, (byte)0xfe, (byte)0xff, 0,
          0, 0, 0x22,
          0, 1, 0, 0, 0, 0, 0, 0x22,
         });

          {
            String stringTemp = CBORObject.ReadJSON(msjson).AsString();
            Assert.assertEquals(
              "\ud800\udc00",
              stringTemp);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0, 0, 0x22, 0, 1,
          0, 0, 0, 0,
          0, 0x22,
         });

          {
            String stringTemp = CBORObject.ReadJSON(msjson).AsString();
            Assert.assertEquals(
              "\ud800\udc00",
              stringTemp);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0, 0,
          0x22, 0, 0, 0,
          0, 0, 1, 0, 0x22, 0, 0, 0,
         });

          {
            String stringTemp = CBORObject.ReadJSON(msjson).AsString();
            Assert.assertEquals(
              "\ud800\udc00",
              stringTemp);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0x22, 0, 0, 0, 0, 0,
          1, 0, 0x22,
          0,
          0, 0,
         });

          {
            String stringTemp = CBORObject.ReadJSON(msjson).AsString();
            Assert.assertEquals(
              "\ud800\udc00",
              stringTemp);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, 0,
          0x22, (byte)0xd8,
          0,
          (byte)0xdc, 0, 0, 0x22,
         });

          {
            String stringTemp = CBORObject.ReadJSON(msjson).AsString();
            Assert.assertEquals(
              "\ud800\udc00",
              stringTemp);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0x22, (byte)0xd8, 0,
          (byte)0xdc, 0, 0, 0x22,
         });

          {
            String stringTemp = CBORObject.ReadJSON(msjson).AsString();
            Assert.assertEquals(
              "\ud800\udc00",
              stringTemp);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x22, 0,
          0, (byte)0xd8, 0,
          (byte)0xdc, 0x22, 0,
         });

          {
            String stringTemp = CBORObject.ReadJSON(msjson).AsString();
            Assert.assertEquals(
              "\ud800\udc00",
              stringTemp);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0x22, 0, 0, (byte)0xd8, 0,
          (byte)0xdc, 0x22, 0,
         });

          {
            String stringTemp = CBORObject.ReadJSON(msjson).AsString();
            Assert.assertEquals(
              "\ud800\udc00",
              stringTemp);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0, (byte)0xfe, (byte)0xff, 0,
          0, 0, 0x22,
          0, 0, (byte)0xd8, 0, 0, 0, 0, 0x22,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0, 0, 0x22, 0, 0,
          (byte)0xd8, 0, 0,
          0,
          0, 0x22,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0, 0,
          0x22, 0, 0, 0,
          0, (byte)0xd8, 0, 0, 0x22, 0, 0, 0,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0x22, 0, 0, 0, 0,
          (byte)0xd8,
          0,
          0,
          0x22, 0, 0, 0,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, 0, 0x22,
          0, (byte)0xdc, 0,
          (byte)0xdc, 0, 0, 0x22,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0x22, 0, (byte)0xdc, 0,
          (byte)0xdc, 0, 0,
          0x22,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x22, 0,
          0, (byte)0xdc, 0,
          (byte)0xdc, 0x22, 0,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0x22, 0, 0, (byte)0xdc, 0,
          (byte)0xdc, 0x22, 0,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] { (byte)0xfc });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] { 0, 0 });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        // Illegal UTF-16
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, 0x20,
          0x20, 0x20,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x20,
          0x20, 0x20,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, (byte)0xd8,
          0x00,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, (byte)0xdc,
          0x00,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, (byte)0xd8,
          0x00, 0x20, 0x00,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, (byte)0xdc,
          0x00, 0x20, 0x00,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, (byte)0xd8,
          0x00, (byte)0xd8, 0x00,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, (byte)0xdc,
          0x00, (byte)0xd8, 0x00,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, (byte)0xdc,
          0x00, (byte)0xd8, 0x00, (byte)0xdc, 0x00,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xfe, (byte)0xff, (byte)0xdc,
          0x00, (byte)0xdc, 0x00,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}

        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x00,
          (byte)0xd8,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x00,
          (byte)0xdc,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x00,
          (byte)0xd8, 0x00, 0x20,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x00,
          (byte)0xdc, 0x00, 0x20,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x00,
          (byte)0xd8, 0x00, (byte)0xd8,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x00,
          (byte)0xdc, 0x00, (byte)0xd8,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x00,
          (byte)0xdc, 0x00, (byte)0xd8, 0x00, (byte)0xdc,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          (byte)0xff, (byte)0xfe, 0x00,
          (byte)0xdc, 0x00, (byte)0xdc,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}

        // Illegal UTF-32
        {
          java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(new byte[] {
          0, 0, 0, 0x20, 0,
         });

          try {
            CBORObject.ReadJSON(msjson);
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
        byte[] msbytes = new byte[] { 0, 0, 0, 0x20, 0, 0, };
        ReadJsonFail(msbytes);
        msbytes = new byte[] { 0, 0, 0, 0x20, 0, 0, 0 };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, 0, 0x20, 0, 0,
          (byte)0xd8, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, 0, 0x20, 0, 0,
          (byte)0xdc, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, 0, 0x20, 0,
          0x11, 0x00, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, 0, 0x20, 0,
          (byte)0xff, 0x00, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, 0, 0x20, 0x1,
          0, 0x00, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] { 0, 0, (byte)0xfe, (byte)0xff, 0, };
        ReadJsonFail(msbytes);
        msbytes = new byte[] { 0, 0, (byte)0xfe, (byte)0xff, 0, 0, };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, (byte)0xfe, (byte)0xff, 0,
          0, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, (byte)0xfe, (byte)0xff, 0,
          0, (byte)0xd8, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, (byte)0xfe, (byte)0xff, 0,
          0, (byte)0xdc, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, (byte)0xfe, (byte)0xff, 0,
          0x11, 0x00, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] {
          0, 0, (byte)0xfe, (byte)0xff, 0,
          (byte)0xff, 0x00, 0,
         };
        ReadJsonFail(msbytes);
        msbytes = new byte[] { 0, 0, (byte)0xfe, (byte)0xff, 0x1, 0, 0x00, 0, };
        ReadJsonFail(msbytes);
      } catch (IOException ex) {
        Assert.fail(ex.getMessage());
      }
    }

    private static void ReadJsonFail(byte[] msbytes) {
      {
        java.io.ByteArrayInputStream msjson = null;
try {
msjson = new java.io.ByteArrayInputStream(msbytes);

        try {
          CBORObject.ReadJSON(msjson);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
}
finally {
try { if (msjson != null) { msjson.close(); } } catch (java.io.IOException ex) {}
}
}
    }

    // TODO: In next major version, consider using CBORException
    // for circular refs in EncodeToBytes
    @Test
    public void TestEncodeToBytesCircularRefs() {
      CBORObject cbor = CBORObject.NewArray().Add(1).Add(2);
      cbor.Add(cbor);
      try {
        cbor.EncodeToBytes();
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add(1, 2).Add(3, 4);
      cbor.Add(cbor, "test");
      try {
        cbor.EncodeToBytes();
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add(1, 2).Add(3, 4);
      cbor.Add("test", cbor);
      try {
        cbor.EncodeToBytes();
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewArray().Add(1).Add(2);
      cbor.Add(CBORObject.NewArray().Add(cbor));
      try {
        cbor.EncodeToBytes();
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add(1, 2).Add(3, 4);
      cbor.Add(CBORObject.NewArray().Add(cbor), "test");
      try {
        cbor.EncodeToBytes();
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add(1, 2).Add(3, 4);
      cbor.Add("test", CBORObject.NewArray().Add(cbor));
      try {
        cbor.EncodeToBytes();
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCalcEncodedSizeCircularRefs1() {
      CBORObject cbor = CBORObject.NewArray().Add(1).Add(2);
      cbor.Add(cbor);
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestCalcEncodedSizeCircularRefs4() {
      CBORObject cbor = CBORObject.NewArray().Add(1).Add(2);
      cbor.Add(CBORObject.NewArray().Add(cbor));
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestCalcEncodedSizeCircularRefs2() {
      CBORObject cbor = CBORObject.NewMap().Add(1, 2).Add(3, 4);
      cbor.Add(cbor, "test");
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestCalcEncodedSizeCircularRefs3() {
      CBORObject cbor = CBORObject.NewMap().Add(1, 2).Add(3, 4);
      cbor.Add("test", cbor);
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestCalcEncodedSizeCircularRefs5() {
      CBORObject cbor = CBORObject.NewMap().Add(1, 2).Add(3, 4);
      cbor.Add(CBORObject.NewArray().Add(cbor), "test");
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestCalcEncodedSizeCircularRefs6() {
      CBORObject cbor = CBORObject.NewMap().Add(1, 2).Add(3, 4);
      cbor.Add("test", CBORObject.NewArray().Add(cbor));
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCalcEncodedSizeCircularRefs3a() {
      CBORObject cbor = CBORObject.NewOrderedMap().Add(1, 2).Add(3, 4);
      cbor.Add("test", cbor);
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestCalcEncodedSizeCircularRefs3b() {
      CBORObject cbor;
      cbor = CBORObject.NewOrderedMap().Add(1, 2).Add(3, 4);
      cbor.Add("test", cbor);
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCalcEncodedSizeCircularRefs3ba() {
      CBORObject cbor;

      cbor = CBORObject.NewOrderedMap().Add("abc", 2).Add("def", 4);
      cbor.Add("test", cbor);
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCalcEncodedSizeCircularRefs3bb() {
      CBORObject cbor;

      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      cbor.Add("test", cbor);
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCalcEncodedSizeCircularRefs3bc() {
      CBORObject cbor;
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      cbor.Add(CBORObject.NewOrderedMap().Add("jkl", cbor), "test");
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      cbor.Add("test", CBORObject.NewOrderedMap().Add("jkl", cbor));
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      cbor.Add(CBORObject.NewOrderedMap().Add(cbor, "jkl"), "test");
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      cbor.Add("test", CBORObject.NewOrderedMap().Add(cbor, "jkl"));
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp = CBORObject.NewOrderedMap().Add(cbor,
            "jkl").Add("mno",
            1);
        Object objectTemp2 = "test";
        cbor.Add(objectTemp, objectTemp2);
      }
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp = "test";
        Object objectTemp2 = CBORObject.NewOrderedMap().Add(cbor,
            "jkl").Add("mno", 1);
        cbor.Add(objectTemp, objectTemp2);
      }
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp = "test";
        Object objectTemp2 = CBORObject.NewOrderedMap().Add("mno",
            1).Add(cbor, "jkl");
        cbor.Add(objectTemp, objectTemp2);
      }
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp = CBORObject.NewOrderedMap().Add("mno", 1).Add(cbor,
            "jkl");
        Object objectTemp2 = "test";
        cbor.Add(objectTemp, objectTemp2);
      }
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // No circular refs
      cbor = CBORObject.NewOrderedMap().Add(1, 2).Add(3, 4);
      cbor.Add("test", CBORObject.NewOrderedMap());
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("abc", 2).Add("def", 4);
      cbor.Add("test", CBORObject.NewOrderedMap());
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      cbor.Add("test", CBORObject.NewOrderedMap());
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp = CBORObject.NewOrderedMap().Add("jkl",
            CBORObject.NewOrderedMap());
        Object objectTemp2 = "test";
        cbor.Add(objectTemp, objectTemp2);
      }
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp = "test";
        Object objectTemp2 = CBORObject.NewOrderedMap().Add("jkl",
            CBORObject.NewOrderedMap());
        cbor.Add(objectTemp, objectTemp2);
      }
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp =
          CBORObject.NewOrderedMap().Add(CBORObject.NewOrderedMap(),
            "jkl");
        Object objectTemp2 = "test";
        cbor.Add(objectTemp, objectTemp2);
      }
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp = "test";
        Object objectTemp2 = CBORObject.NewOrderedMap()
          .Add(CBORObject.NewOrderedMap(),
            "jkl");
        cbor.Add(objectTemp, objectTemp2);
      }
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        CBORObject.NewOrderedMap().Add(CBORObject.NewOrderedMap(),
          "jkl").Add("mno",
            1);
        Object objectTemp2 = "test";
        cbor.Add("test", objectTemp2);
      }
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp2 =
          CBORObject.NewOrderedMap().Add(CBORObject.NewOrderedMap(),
            "jkl").Add("mno",
            1);
        cbor.Add("test", objectTemp2);
      }
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp2 =
          CBORObject.NewOrderedMap().Add("mno", 1).Add(
            CBORObject.NewOrderedMap(),
            "jkl");
        cbor.Add("test", objectTemp2);
      }
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
      cbor = CBORObject.NewOrderedMap().Add("ghi", 2).Add("abc", 4);
      {
        Object objectTemp =
          CBORObject.NewOrderedMap().Add("mno", 1).Add(
            CBORObject.NewOrderedMap(),
            "jkl");
        cbor.Add(objectTemp, "test");
      }
      if (!(cbor.CalcEncodedSize() > 2)) {
 Assert.fail();
 }
    }

    @Test
    public void TestCalcEncodedSizeCircularRefs2a() {
      CBORObject cbor = CBORObject.NewOrderedMap().Add(1, 2).Add(3, 4);
      cbor.Add(cbor, "test");
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCalcEncodedSizeCircularRefs5a() {
      CBORObject cbor = CBORObject.NewOrderedMap().Add(1, 2).Add(3, 4);
      CBORObject cbor2 = CBORObject.NewArray().Add(cbor);
      cbor.Add(cbor2, "test");
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestCalcEncodedSizeCircularRefs6a() {
      CBORObject cbor = CBORObject.NewOrderedMap().Add(1, 2).Add(3, 4);
      cbor.Add("test", CBORObject.NewArray().Add(cbor));
      try {
        cbor.CalcEncodedSize();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestClear() {
      CBORObject cbor;
      cbor = CBORObject.NewArray().Add("a").Add("b").Add("c");
      Assert.assertEquals(3, cbor.size());
      cbor.Clear();
      Assert.assertEquals(0, cbor.size());
      cbor = CBORObject.NewMap()
        .Add("a", 0).Add("b", 1).Add("c", 2);
      Assert.assertEquals(3, cbor.size());
      cbor.Clear();
      Assert.assertEquals(0, cbor.size());
      try {
        ToObjectTest.TestToFromObjectRoundTrip(1).Clear();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.Clear();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Null.Clear();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestRemove() {
      CBORObject cbor;
      cbor = CBORObject.NewArray().Add("a").Add("b").Add("c");
      Assert.assertEquals(3, cbor.size());
      if (!(cbor.Remove(ToObjectTest.TestToFromObjectRoundTrip(
            "b"))))Assert.fail();
      if (cbor.Remove(ToObjectTest.TestToFromObjectRoundTrip(
            "x"))) {
 Assert.fail();
 }
      try {
        cbor.Remove((CBORObject)null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(2, cbor.size());
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip("a"), cbor.get(0));
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip("c"), cbor.get(1));
      cbor = CBORObject.NewArray().Add("a").Add("b").Add("c");
      Assert.assertEquals(3, cbor.size());

      if (!(cbor.Remove("b"))) {
 Assert.fail();
 }
      if (cbor.Remove("x")) {
 Assert.fail();
 }
      Assert.assertEquals(2, cbor.size());
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip("a"), cbor.get(0));
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip("c"), cbor.get(1));
      cbor = CBORObject.NewMap().Add("a", 0).Add("b", 1).Add("c", 2);
      Assert.assertEquals(3, cbor.size());

      if (!(cbor.Remove(ToObjectTest.TestToFromObjectRoundTrip(
            "b"))))Assert.fail();
      if (cbor.Remove(ToObjectTest.TestToFromObjectRoundTrip(
            "x"))) {
 Assert.fail();
 }
      try {
        cbor.Remove((CBORObject)null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(2, cbor.size());
      if (!(cbor.ContainsKey("a"))) {
 Assert.fail();
 }
      if (!(cbor.ContainsKey("c"))) {
 Assert.fail();
 }
      cbor = CBORObject.NewMap().Add("a", 0).Add("b", 1).Add("c", 2);
      Assert.assertEquals(3, cbor.size());

      if (!(cbor.Remove("b"))) {
 Assert.fail();
 }
      if (cbor.Remove("x")) {
 Assert.fail();
 }
      Assert.assertEquals(2, cbor.size());
      if (!(cbor.ContainsKey("a"))) {
 Assert.fail();
 }
      if (!(cbor.ContainsKey("c"))) {
 Assert.fail();
 }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(1).Remove("x");
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.Remove("x");
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Null.Remove("x");
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(1)
        .Remove(ToObjectTest.TestToFromObjectRoundTrip("b"));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.Remove(ToObjectTest.TestToFromObjectRoundTrip("b"));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Null.Remove(ToObjectTest.TestToFromObjectRoundTrip("b"));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestRemoveAt() {
      CBORObject cbor;
      cbor = CBORObject.NewArray().Add("a").Add("b").Add("c");
      if (!(cbor.RemoveAt(1))) {
 Assert.fail();
 }
      if (cbor.RemoveAt(2)) {
 Assert.fail();
 }
      if (cbor.RemoveAt(-1)) {
 Assert.fail();
 }
      Assert.assertEquals(2, cbor.size());
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip("a"), cbor.get(0));
      Assert.assertEquals(ToObjectTest.TestToFromObjectRoundTrip("c"), cbor.get(1));
      try {
        CBORObject.NewMap().RemoveAt(0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(1).RemoveAt(0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.RemoveAt(0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Null.RemoveAt(0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestSet() {
      CBORObject cbor = CBORObject.NewMap().Add("x", 0).Add("y", 1);
      Assert.assertEquals(0, cbor.get("x").AsInt32());
      Assert.assertEquals(1, cbor.get("y").AsInt32());
      cbor.Set("x", 5).Set("z", 6);
      Assert.assertEquals(5, cbor.get("x").AsInt32());
      Assert.assertEquals(6, cbor.get("z").AsInt32());
      cbor = CBORObject.NewArray().Add(1).Add(2).Add(3).Add(4);
      Assert.assertEquals(1, cbor.get(0).AsInt32());
      Assert.assertEquals(2, cbor.get(1).AsInt32());
      Assert.assertEquals(3, cbor.get(2).AsInt32());
      try {
        cbor.Set(-1, 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor.Set(4, 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        cbor.Set(999, 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject cbor2 = CBORObject.True;
      try {
        cbor2.Set(0, 0);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor.Set(0, 99);
      Assert.assertEquals(99, cbor.get(0).AsInt32());
      cbor.Set(3, 199);
      Assert.assertEquals(199, cbor.get(3).AsInt32());
    }

    @Test
    public void TestSign() {
      try {
        int sign = CBORObject.True.signum();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        int sign = CBORObject.False.signum();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        int sign = CBORObject.NewArray().signum();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        int sign = CBORObject.NewMap().signum();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        String numberString = numberinfo.get("number").AsString();
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberString));
        if (cbornumber.AsNumber().IsNaN()) {
          try {
            Assert.fail("" + cbornumber.signum());
            Assert.fail("Should have failed");
          } catch (IllegalStateException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
        } else if (numberString.length() > 0 && numberString.charAt(0) == '-') {
          Assert.assertEquals(-1, cbornumber.signum());
        } else if (numberinfo.get("number").AsString().equals("0")) {
          Assert.assertEquals(0, cbornumber.signum());
        } else {
          Assert.assertEquals(1, cbornumber.signum());
        }
      }
    }

    @Test(timeout = 1000)
    public void TestAsNumberSubtract() {
      try {
        ToObjectTest.TestToFromObjectRoundTrip(2).AsNumber().Subtract(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCompareToUnicodeString() {
      CBORObject cbora;
      CBORObject cborb;
      cbora = CBORObject.FromObject("aa\ud200\ue000");
      cborb = CBORObject.FromObject("aa\ud200\ue001");
      TestCommon.CompareTestLess(cbora, cborb);
      cbora = CBORObject.FromObject("aa\ud200\ue000");
      cborb = CBORObject.FromObject("aa\ud201\ue000");
      TestCommon.CompareTestLess(cbora, cborb);
      cbora = CBORObject.FromObject("aa\ud800\udc00\ue000");
      cborb = CBORObject.FromObject("aa\ue001\ue000");
      TestCommon.CompareTestGreater(cbora, cborb);
      cbora = CBORObject.FromObject("aa\ud800\udc00\ue000");
      cborb = CBORObject.FromObject("aa\ud800\udc01\ue000");
      TestCommon.CompareTestLess(cbora, cborb);
      cbora = CBORObject.FromObject("aa\ud800\udc00\ue000");
      cborb = CBORObject.FromObject("aa\ud801\udc00\ue000");
      TestCommon.CompareTestLess(cbora, cborb);
    }

    @Test
    public void TestToJSONString() {
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(
            "\u2027\u2028\u2029\u202a\u0008\u000c").ToJSONString();
        Assert.assertEquals(
          "\"\u2027\\u2028\\u2029\u202a\\b\\f\"",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(
            "\u0085\ufeff\ufffe\uffff").ToJSONString();
        Assert.assertEquals(
          "\"\\u0085\\uFEFF\\uFFFE\\uFFFF\"",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.True.ToJSONString();
        Assert.assertEquals(
          "true",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.False.ToJSONString();
        Assert.assertEquals(
          "false",
          stringTemp);
      }
      {
        String stringTemp = CBORObject.Null.ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
          .ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
          .ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NaN).ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
          .ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
          .ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NaN).ToJSONString();
        Assert.assertEquals(
          "null",
          stringTemp);
      }

      CBORObject cbor = CBORObject.NewArray();
      byte[] b64bytes = new byte[] {
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xff, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xff, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
        0x01, (byte)0xfe, (byte)0xdd, (byte)0xfd, (byte)0xdc,
       };
      cbor.Add(b64bytes);
      TestSucceedingJSON(cbor.ToJSONString());
      cbor = CBORObject.NewMap();
      cbor.Add("key", "\ud800\udc00");
      try {
        cbor.ToJSONString();
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      try {
        cbor.Add("key", "\ud800");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      try {
        cbor.Add("key", "\udc00");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      try {
        cbor.Add("key", "\ud800\udc00\ud800");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      try {
        cbor.Add("key", "\udc00\udc00\ud800");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      cbor.Add("\ud800\udc00", "value");
      try {
        cbor.ToJSONString();
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      try {
        cbor.Add("\ud800", "value");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      try {
        cbor.Add("\udc00", "value");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      try {
        cbor.Add("\ud800\udc00\ud800", "value");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap();
      try {
        cbor.Add("\udc00\udc00\ud800", "value");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewArray();
      cbor.Add("\ud800\udc00");
      try {
        cbor.ToJSONString();
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewArray();
      try {
        cbor.Add("\ud800");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewArray();
      try {
        cbor.Add("\udc00");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewArray();
      try {
        cbor.Add("\ud800\udc00\ud800");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewArray();
      try {
        cbor.Add("\udc00\udc00\ud800");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestToJSONString_DuplicateKeys() {
      CBORObject cbor;
      cbor = CBORObject.NewMap().Add("true", 1).Add(true, 1);
      try {
        cbor.ToJSONString();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add("true", 1).Add(false, 1);
      try {
        cbor.ToJSONString();
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add("9999-01-01T00:00:00Z", 1)
        .Add(CBORObject.FromObjectAndTag("9999-01-01T00:00:00Z", 0), 1);
      try {
        cbor.ToJSONString();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add("34", 1).Add(34, 1);
      try {
        cbor.ToJSONString();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add("-34", 1).Add(-34, 1);
      try {
        cbor.ToJSONString();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.NewMap().Add("-34", 1).Add(-35, 1);
      try {
        cbor.ToJSONString();
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestToFloatingPointBits() {
      try {
        CBORObject.FromFloatingPointBits(0, 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromFloatingPointBits(0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromFloatingPointBits(0, 3);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromFloatingPointBits(0, 5);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromFloatingPointBits(0, 6);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromFloatingPointBits(0, 7);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.FromFloatingPointBits(0, 9);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestToFloatingPointBitsSingle() {
      // Regression test
      CBORObject o;
      o = CBORObject.FromFloatingPointBits(2140148306L, 4);
      if (!(Double.isNaN(o.AsDoubleValue())))Assert.fail();
      o = CBORObject.FromFloatingPointBits(1651724151L, 4);
      if (!(o.AsDoubleValue() == 1.1220712138406615E21)) {
 Assert.fail();
 }
      o = CBORObject.FromFloatingPointBits(-1566356128L, 4);
      if (!(o.AsDoubleValue() == -4.426316249665156E-18)) {
 Assert.fail();
 }
    }

    @Test
    public void TestToJSONString_ByteArray_Padding() {
      CBORObject o;
      JSONOptions options = new JSONOptions("");
      o = CBORObject.FromObjectAndTag(
          new byte[] { (byte)0x9a, (byte)0xd6, (byte)0xf0, (byte)0xe8 }, 22);
      {
        String stringTemp = o.ToJSONString(options);
        Assert.assertEquals(
          "\"mtbw6A==\"",
          stringTemp);
      }
      // untagged, so base64url without padding
      o = ToObjectTest.TestToFromObjectRoundTrip(new byte[] {
        (byte)0x9a, (byte)0xd6,
        (byte)0xf0, (byte)0xe8,
       });
      {
        String stringTemp = o.ToJSONString(options);
        Assert.assertEquals(
          "\"mtbw6A\"",
          stringTemp);
      }
      // tagged 23, so base16
      o = CBORObject.FromObjectAndTag(
          new byte[] { (byte)0x9a, (byte)0xd6, (byte)0xf0, (byte)0xe8 },
          23);
      {
        String stringTemp = o.ToJSONString(options);
        Assert.assertEquals(
          "\"9AD6F0E8\"",
          stringTemp);
      }
      o = ToObjectTest.TestToFromObjectRoundTrip(new byte[] {
        (byte)0x9a, (byte)0xd6,
        (byte)0xff, (byte)0xe8,
       });
    }

    @Test
    public void TestToString() {
      {
        String stringTemp = CBORObject.Undefined.toString();
        Assert.assertEquals(
          "undefined",
          stringTemp);
      }
      CBORObject cbor = CBORObject.True;
      String cborString;
      cborString = cbor.toString();
      if (cborString == null) {
        Assert.fail();
      }
      TestCommon.AssertNotEqual("21", cborString);
      TestCommon.AssertNotEqual("simple(21)", cborString);
      cbor = CBORObject.False;
      cborString = cbor.toString();
      if (cborString == null) {
        Assert.fail();
      }
      TestCommon.AssertNotEqual("20", cborString);
      TestCommon.AssertNotEqual("simple(20)", cborString);
      cbor = CBORObject.Null;
      cborString = cbor.toString();
      if (cborString == null) {
        Assert.fail();
      }
      TestCommon.AssertNotEqual("22", cborString);
      TestCommon.AssertNotEqual("simple(22)", cborString);
      cbor = CBORObject.Undefined;
      cborString = cbor.toString();
      if (cborString == null) {
        Assert.fail();
      }
      TestCommon.AssertNotEqual("23", cborString);
      TestCommon.AssertNotEqual("simple(23)", cborString);
      {
        String stringTemp = CBORObject.FromSimpleValue(50).toString();
        Assert.assertEquals(
          "simple(50)",
          stringTemp);
      }
    }

    @Test
    public void TestSimpleValuesNotIntegers() {
      CBORObject cbor = CBORObject.True;
      TestCommon.AssertNotEqual(CBORObject.FromObject(21), cbor);
      cbor = CBORObject.False;
      TestCommon.AssertNotEqual(CBORObject.FromObject(20), cbor);
      cbor = CBORObject.Null;
      TestCommon.AssertNotEqual(CBORObject.FromObject(22), cbor);
      cbor = CBORObject.Undefined;
      TestCommon.AssertNotEqual(CBORObject.FromObject(23), cbor);
    }

    @Test
    public void TestTrue() {
      CBORTestCommon.AssertJSONSer(CBORObject.True, "true");
      Assert.assertEquals(
        CBORObject.True,
        ToObjectTest.TestToFromObjectRoundTrip(true));
    }

    @Test
    public void TestCalcEncodedBytesSpecific() {
      CBORObject cbor;

      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xda, 0x00, 0x1d,
        (byte)0xdb, 0x03, (byte)0xd9, 0x01, 0x0d, (byte)0x83, 0x00, 0x00,
        0x03,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);

      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xda, 0x00, 0x14,
        0x57,
        (byte)0xce,
        (byte)0xc5,
        (byte)0x82, 0x1a, 0x46, 0x5a, 0x37,
        (byte)0x87,
        (byte)0xc3, 0x50, 0x5e,
        (byte)0xec,
        (byte)0xfd, 0x73, 0x50, 0x64,
        (byte)0xa1, 0x1f, 0x10,
        (byte)0xc4, (byte)0xff, (byte)0xf2, (byte)0xc4, (byte)0xc9, 0x65,
        0x12,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);

      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xfa, 0x56, 0x00,
        0x69, 0x2a,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xf9, (byte)0xfc,
        0x00,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xa2,
        (byte)0x82,
        (byte)0xf6,
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
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
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
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xfa, (byte)0xc7,
        (byte)0x80, 0x01, (byte)0x80,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xa5, 0x64, 0x69,
        0x74, 0x65, 0x6d, 0x6a, 0x61, 0x6e, 0x79, 0x20, 0x73, 0x74, 0x72, 0x69,
        0x6e, 0x67, 0x66, 0x6e, 0x75, 0x6d, 0x62, 0x65, 0x72, 0x18, 0x2a, 0x63,
        0x6d, 0x61, 0x70,
        (byte)0xa1, 0x66, 0x6e, 0x75, 0x6d, 0x62, 0x65, 0x72, 0x18, 0x2a, 0x65,
        0x61, 0x72, 0x72, 0x61, 0x79,
        (byte)0x82,
        (byte)0xf9, 0x63,
        (byte)0xce, 0x63, 0x78, 0x79, 0x7a, 0x65, 0x62, 0x79, 0x74, 0x65, 0x73,
        0x43, 0x00, 0x01, 0x02,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xda, 0x00, 0x14,
        0x57,
        (byte)0xce,
        (byte)0xc5,
        (byte)0x82, 0x1a, 0x46, 0x5a, 0x37,
        (byte)0x87,
        (byte)0xc3, 0x50, 0x5e,
        (byte)0xec,
        (byte)0xfd, 0x73, 0x50, 0x64,
        (byte)0xa1, 0x1f, 0x10,
        (byte)0xc4, (byte)0xff, (byte)0xf2, (byte)0xc4, (byte)0xc9, 0x65,
        0x12,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xfa, (byte)0xc7,
        (byte)0x80, 0x01, (byte)0x80,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0x82,
        (byte)0xda, 0x00, 0x0a,
        (byte)0xe8,
        (byte)0xb6,
        (byte)0xfb, 0x43,
        (byte)0xc0, 0x00, 0x00,
        (byte)0xd5, 0x42, 0x7f,
        (byte)0xdc, (byte)0xfa, 0x71, (byte)0x80, (byte)0xd7, (byte)0xc8,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xfa, 0x29, 0x0a,
        0x4c, (byte)0x9e,
       });
      CBORTestCommon.CheckEncodeToBytes(cbor);
    }

    @Test
    public void TestType() {
      CBORObject cbor = CBORObject.True;
      Assert.assertEquals(
        CBORType.Boolean,
        cbor.getType());
      // Simple value for true
      cbor = CBORObject.FromSimpleValue(21);
      Assert.assertEquals(
        CBORType.Boolean,
        cbor.getType());
      cbor = CBORObject.FromObjectAndTag(CBORObject.True, 999);
      Assert.assertEquals(
        CBORType.Boolean,
        cbor.getType());
      cbor = CBORObject.False;
      Assert.assertEquals(
        CBORType.Boolean,
        cbor.getType());
      cbor = CBORObject.Null;
      Assert.assertEquals(
        CBORType.SimpleValue,
        cbor.getType());
      cbor = CBORObject.Undefined;
      Assert.assertEquals(
        CBORType.SimpleValue,
        cbor.getType());
      cbor = CBORObject.FromSimpleValue(99);
      Assert.assertEquals(
        CBORType.SimpleValue,
        cbor.getType());
    }

    @Test
    public void TestUntag() {
      CBORObject o = CBORObject.FromObjectAndTag("test", 999);
      Assert.assertEquals(EInteger.FromString("999"), o.getMostInnerTag());
      o = o.Untag();
      Assert.assertEquals(EInteger.FromString("-1"), o.getMostInnerTag());
    }

    @Test
    public void TestUntagOne() {
      // not implemented yet
    }

    @Test
    public void TestValues() {
      // not implemented yet
    }

    @Test
    public void TestWrite() {
      for (int i = 0; i < 2000; ++i) {
        this.TestWrite2();
      }
      for (int i = 0; i < 40; ++i) {
        TestWrite3();
      }
    }

    public static void TestWriteExtraOne(long longValue) {
      try {
        {
          CBORObject cborTemp1 =
            ToObjectTest.TestToFromObjectRoundTrip(longValue);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)longValue);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(longValue, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(longValue, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip(longValue));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)longValue, longValue);
        }

        EInteger bigintVal = EInteger.FromInt64(longValue);
        {
          CBORObject cborTemp1 =
            ToObjectTest.TestToFromObjectRoundTrip(bigintVal);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)bigintVal);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(bigintVal, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(bigintVal, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip(bigintVal));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)bigintVal, bigintVal);
        }

        if (longValue >= (long)Integer.MIN_VALUE && longValue <=
          (long)Integer.MAX_VALUE) {
          int intval = (int)longValue;
          {
            CBORObject cborTemp1 =
              ToObjectTest.TestToFromObjectRoundTrip(intval);
            CBORObject cborTemp2 =
              ToObjectTest.TestToFromObjectRoundTrip((Object)intval);
            TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
            try {
              CBORObject.Write(intval, null);
              Assert.fail("Should have failed");
            } catch (NullPointerException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            AssertWriteThrow(cborTemp1);
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.Write(intval, ms);
              CBORObject.Write(cborTemp1, ms);
              cborTemp1.WriteTo(ms);
              AssertReadThree(
                ms.toByteArray(),
                ToObjectTest.TestToFromObjectRoundTrip(intval));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            TestWriteObj((Object)intval, intval);
          }
        }
        if (longValue >= -32768L && longValue <= 32767) {
          short shortval = (short)longValue;
          CBORObject cborTemp1 = ToObjectTest
            .TestToFromObjectRoundTrip(shortval);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)shortval);
          TestCommon.CompareTestEqualAndConsistent(
            cborTemp1,
            cborTemp2);
          try {
            CBORObject.Write(shortval, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(shortval, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip(shortval));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)shortval, shortval);
        }
        if (longValue >= 0L && longValue <= 255) {
          byte byteval = (byte)longValue;
          {
            CBORObject cborTemp1 =
              ToObjectTest.TestToFromObjectRoundTrip(byteval);
            CBORObject cborTemp2 =
              ToObjectTest.TestToFromObjectRoundTrip((Object)byteval);
            TestCommon.CompareTestEqualAndConsistent(cborTemp1,
              cborTemp2);
            try {
              CBORObject.Write(byteval, null);
              Assert.fail("Should have failed");
            } catch (NullPointerException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            AssertWriteThrow(cborTemp1);
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.Write(byteval, ms);
              CBORObject.Write(cborTemp1, ms);
              cborTemp1.WriteTo(ms);
              AssertReadThree(
                ms.toByteArray(),
                ToObjectTest.TestToFromObjectRoundTrip(byteval));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            TestWriteObj((Object)byteval, byteval);
          }
        }
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    @Test
    public void TestWriteExtra() {
      try {
        String str = null;
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(str);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)str);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(str, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(str, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip((Object)null));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)str, null);
        }

        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(
              "test");
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)"test");
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write("test", null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write("test", ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            {
              CBORObject objectTemp2 = ToObjectTest.TestToFromObjectRoundTrip(
                  "test");
              AssertReadThree(ms.toByteArray(), objectTemp2);
            }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)"test", "test");
        }

        str = TestCommon.Repeat("test", 4000);
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(str);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)str);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(str, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(str, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip(str));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)str, str);
        }

        long[] values = {
          0, 1, 23, 24, -1, -23, -24, -25,
          0x7f, -128, 255, 256, 0x7fff, -32768, 0x7fff,
          -32768, -65536, -32769, -65537,
          0x7fffff, 0x7fff7f, 0x7fff7fff, 0x7fff7fff7fL, 0x7fff7fff7fffL,
          0x7fff7fff7fff7fL, 0x7fff7fff7fff7fffL,
          Long.MAX_VALUE, Long.MIN_VALUE, Integer.MIN_VALUE,
          Integer.MAX_VALUE,
        };
        for (int i = 0; i < values.length; ++i) {
          TestWriteExtraOne(values[i]);
        }
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(0.0f);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)0.0f);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(0.0f, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(0.0f, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip(0.0f));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)0.0f, 0.0f);
        }

        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(2.6);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)2.6);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(2.6, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(2.6, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip(2.6));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)2.6, 2.6);
        }

        CBORObject cbor = null;
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(cbor);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)cbor);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(cbor, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(cbor, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip((Object)null));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)cbor, null);
        }

        Object aobj = null;
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(aobj);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)aobj);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(aobj, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(aobj, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip((Object)null));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)aobj, null);
        }
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    public static void TestWrite3() {
      EFloat ef = null;
      EDecimal ed = null;
      RandomGenerator fr = new RandomGenerator();
      try {
        for (int i = 0; i < 256; ++i) {
          byte b = (byte)(i & 0xff);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write((byte)b, ms);
            CBORObject cobj = CBORObject.DecodeFromBytes(ms.toByteArray());
            Assert.assertEquals(i, cobj.AsInt32());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        }

        for (int i = 0; i < 50; ++i) {
          ef = RandomObjects.RandomEFloat(fr);
          if (!ef.IsNaN()) {
            CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(ef);
            CBORObject cborTemp2 =
              ToObjectTest.TestToFromObjectRoundTrip((Object)ef);
            TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
            try {
              CBORObject.Write(ef, null);
              Assert.fail("Should have failed");
            } catch (NullPointerException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            AssertWriteThrow(cborTemp1);
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.Write(ef, ms);
              CBORObject.Write(cborTemp1, ms);
              cborTemp1.WriteTo(ms);
              AssertReadThree(
                ms.toByteArray(),
                ToObjectTest.TestToFromObjectRoundTrip(ef));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            TestWriteObj((Object)ef, ef);
          }

          ef = EFloat.Create(
              RandomObjects.RandomEInteger(fr),
              RandomObjects.RandomEInteger(fr));
          {
            CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(ef);
            CBORObject cborTemp2 =
              ToObjectTest.TestToFromObjectRoundTrip((Object)ef);
            TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
            try {
              CBORObject.Write(ef, null);
              Assert.fail("Should have failed");
            } catch (NullPointerException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            AssertWriteThrow(cborTemp1);
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.Write(ef, ms);
              CBORObject.Write(cborTemp1, ms);
              cborTemp1.WriteTo(ms);
              if (cborTemp1.isNegative() && cborTemp1.isZero()) {
                AssertReadThree(ms.toByteArray());
              } else {
                AssertReadThree(
                  ms.toByteArray(),
                  ToObjectTest.TestToFromObjectRoundTrip(ef));
              }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            TestWriteObj((Object)ef, ef);
          }
        }
        for (int i = 0; i < 50; ++i) {
          ed = RandomObjects.RandomEDecimal(fr);
          if (!ed.IsNaN()) {
            CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(ed);
            CBORObject cborTemp2 =
              ToObjectTest.TestToFromObjectRoundTrip((Object)ed);
            TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
            try {
              CBORObject.Write(ed, null);
              Assert.fail("Should have failed");
            } catch (NullPointerException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            AssertWriteThrow(cborTemp1);
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.Write(ed, ms);
              CBORObject.Write(cborTemp1, ms);
              cborTemp1.WriteTo(ms);
              if (cborTemp1.isNegative() && cborTemp1.isZero()) {
                AssertReadThree(ms.toByteArray());
              } else {
                AssertReadThree(
                  ms.toByteArray(),
                  ToObjectTest.TestToFromObjectRoundTrip(ed));
              }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            if (!(cborTemp1.isNegative() && cborTemp1.isZero())) {
  TestWriteObj((Object)ed, ed);
}
          }

          ed = EDecimal.Create(
              RandomObjects.RandomEInteger(fr),
              RandomObjects.RandomEInteger(fr));
          {
            CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(ed);
            CBORObject cborTemp2 =
              ToObjectTest.TestToFromObjectRoundTrip((Object)ed);
            TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
            try {
              CBORObject.Write(ed, null);
              Assert.fail("Should have failed");
            } catch (NullPointerException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            AssertWriteThrow(cborTemp1);
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.Write(ed, ms);
              CBORObject.Write(cborTemp1, ms);
              cborTemp1.WriteTo(ms);
              AssertReadThree(
                ms.toByteArray(),
                ToObjectTest.TestToFromObjectRoundTrip(ed));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            TestWriteObj((Object)ed, ed);
          }
        }
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    @Test
    public void TestWrite2() {
      try {
        RandomGenerator fr = new RandomGenerator();

        EFloat ef = null;
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(ef);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)ef);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(ef, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(ef, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip((Object)null));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)ef, null);
        }

        ef = EFloat.FromString("20");
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(ef);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)ef);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(ef, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(ef, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip(ef));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)ef, ef);
        }

        ERational er = null;
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(er);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)er);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(er, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(er, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip((Object)null));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)er, null);
        }
        do {
          er = RandomObjects.RandomERational(fr);
        } while (er.isNegative() && er.isZero());
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(er);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)er);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(er, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(er, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            if (cborTemp1.isNegative() && cborTemp1.isZero()) {
              AssertReadThree(ms.toByteArray());
            } else {
              AssertReadThree(
                ms.toByteArray(),
                ToObjectTest.TestToFromObjectRoundTrip(er));
            }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)er, er);
        }

        EDecimal ed = null;
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(ed);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)ed);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(ed, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(ed, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip((Object)null));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)ed, null);
        }

        EInteger bigint = null;
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(
              bigint);
          CBORObject cborTemp2 =
            ToObjectTest.TestToFromObjectRoundTrip((Object)bigint);
          TestCommon.CompareTestEqualAndConsistent(cborTemp1, cborTemp2);
          try {
            CBORObject.Write(bigint, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(bigint, ms);
            CBORObject.Write(cborTemp1, ms);
            cborTemp1.WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip((Object)null));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          TestWriteObj((Object)bigint, null);
        }
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    @Test
    public void TestWriteJSON() {
      // not implemented yet
      try {
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          CBORObject.WriteJSON(CBORObject.True, ms);
          byte[] bytes = ms.toByteArray();
          String str = DataUtilities.GetUtf8String(bytes, false);
          Assert.assertEquals("true", str);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          CBORObject.True.WriteJSONTo(ms);
          byte[] bytes = ms.toByteArray();
          String str = DataUtilities.GetUtf8String(bytes, false);
          Assert.assertEquals("true", str);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          CBORObject.WriteJSON(CBORObject.False, ms);
          byte[] bytes = ms.toByteArray();
          String str = DataUtilities.GetUtf8String(bytes, false);
          Assert.assertEquals("false", str);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          CBORObject.False.WriteJSONTo(ms);
          byte[] bytes = ms.toByteArray();
          String str = DataUtilities.GetUtf8String(bytes, false);
          Assert.assertEquals("false", str);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    @Test
    public void TestWriteJSONTo() {
      // not implemented yet
    }

    @Test
    public void TestWriteTo() {
      // not implemented yet
    }

    @Test
    public void TestZero() {
      {
        String stringTemp = CBORObject.Zero.toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      Assert.assertEquals(
        ToObjectTest.TestToFromObjectRoundTrip(0),
        CBORObject.Zero);
    }

    static void CompareDecimals(CBORObject o1, CBORObject o2) {
      int cmpDecFrac = TestCommon.CompareTestReciprocal(
          AsED(o1),
          AsED(o2));
      int cmpCobj = TestCommon.CompareTestReciprocal(o1.AsNumber(),
          o2.AsNumber());
      if (cmpDecFrac != cmpCobj) {
        Assert.fail(TestCommon.ObjectMessages(
            o1,
            o2,
            "Compare: Results\u0020don't match"));
      }
      CBORTestCommon.AssertRoundTrip(o1);
      CBORTestCommon.AssertRoundTrip(o2);
    }

    static void AreEqualExact(double a, double b) {
      if (Double.isNaN(a)) {
        if (!(Double.isNaN(b))) {
 Assert.fail();
 }
      } else if (a != b) {
        Assert.fail("expected " + a + ", got " + b);
      }
    }

    static void AreEqualExact(float a, float b) {
      if (Float.isNaN(a)) {
        if (!(Float.isNaN(b))) {
 Assert.fail();
 }
      } else if (a != b) {
        Assert.fail("expected " + a + ", got " + b);
      }
    }

    private static String Chop(String str) {
      return (str.length() < 100) ? str : (str.substring(0,100) + "...");
    }

    private static void AssertReadThree(byte[] bytes) {
      try {
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

          CBORObject cbor1, cbor2, cbor3;
          cbor1 = CBORObject.Read(ms);
          cbor2 = CBORObject.Read(ms);
          cbor3 = CBORObject.Read(ms);
          TestCommon.CompareTestRelations(cbor1, cbor2, cbor3);
          TestCommon.CompareTestEqualAndConsistent(cbor1, cbor2);
          TestCommon.CompareTestEqualAndConsistent(cbor2, cbor3);
          TestCommon.CompareTestEqualAndConsistent(cbor3, cbor1);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        Assert.fail(ex.toString() + "\r\n" +
          TestCommon.ToByteArrayString(bytes));
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    private static void AssertReadThree(byte[] bytes, CBORObject cbor) {
      try {
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

          CBORObject cbor1, cbor2, cbor3;
          cbor1 = CBORObject.Read(ms);
          cbor2 = CBORObject.Read(ms);
          cbor3 = CBORObject.Read(ms);
          TestCommon.CompareTestEqualAndConsistent(cbor1, cbor);
          TestCommon.CompareTestRelations(cbor1, cbor2, cbor3);
          TestCommon.CompareTestEqualAndConsistent(cbor1, cbor2);
          TestCommon.CompareTestEqualAndConsistent(cbor2, cbor3);
          TestCommon.CompareTestEqualAndConsistent(cbor3, cbor1);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        Assert.fail(ex.toString() + "\r\n" +
          Chop(TestCommon.ToByteArrayString(bytes)) + "\r\n" +
          "cbor = " + Chop(cbor.toString()) + "\r\n");
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    private static void AssertWriteThrow(CBORObject cbor) {
      try {
        cbor.WriteTo(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Write(cbor, null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestWriteBigExponentNumber() {
      String[] exponents = new String[] {
        "15368525994429920286",
        "18446744073709551615",
        "-18446744073709551616",
        "18446744073709551616",
        "-18446744073709551617",
        "18446744073709551615",
        "-18446744073709551614",
      };
      for (String strexp : exponents) {
        EInteger bigexp = EInteger.FromString(strexp);
        EDecimal ed = EDecimal.Create(EInteger.FromInt32(99), bigexp);
        TestWriteObj(ed);
        EFloat ef = EFloat.Create(EInteger.FromInt32(99), bigexp);
        TestWriteObj(ef);
        bigexp = bigexp.Negate();
        ed = EDecimal.Create(EInteger.FromInt32(99), bigexp);
        TestWriteObj(ed);
        ef = EFloat.Create(EInteger.FromInt32(99), bigexp);
        TestWriteObj(ef);
      }
    }

    private static void TestWriteObj(Object obj) {
      try {
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(obj);
          try {
            CBORObject.Write(obj, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(obj, ms);
            CBORObject.Write(ToObjectTest.TestToFromObjectRoundTrip(obj), ms);
            ToObjectTest.TestToFromObjectRoundTrip(obj).WriteTo(ms);
            AssertReadThree(ms.toByteArray());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        }
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    @Test
    public void TestEMap() {
      CBORObject cbor = CBORObject.NewMap()
        .Add("name", "Example");
      byte[] bytes = CBORTestCommon.CheckEncodeToBytes(cbor);
    }

    private static void TestWriteObj(Object obj, Object objTest) {
      try {
        {
          CBORObject cborTemp1 = ToObjectTest.TestToFromObjectRoundTrip(obj);
          try {
            CBORObject.Write(obj, null);
            Assert.fail("Should have failed");
          } catch (NullPointerException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          AssertWriteThrow(cborTemp1);
          {
            java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

            CBORObject.Write(obj, ms);
            CBORObject.Write(ToObjectTest.TestToFromObjectRoundTrip(obj), ms);
            ToObjectTest.TestToFromObjectRoundTrip(obj).WriteTo(ms);
            AssertReadThree(
              ms.toByteArray(),
              ToObjectTest.TestToFromObjectRoundTrip(objTest));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        }
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    @Test
    public void TestWriteValue() {
      try {
        try {
          CBORObject.WriteValue(null, 0, 0);
          Assert.fail("Should have failed");
        } catch (NullPointerException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        try {
          CBORObject.WriteValue(null, 1, 0);
          Assert.fail("Should have failed");
        } catch (NullPointerException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        try {
          CBORObject.WriteValue(null, 2, 0);
          Assert.fail("Should have failed");
        } catch (NullPointerException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        try {
          CBORObject.WriteValue(null, 3, 0);
          Assert.fail("Should have failed");
        } catch (NullPointerException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        try {
          CBORObject.WriteValue(null, 4, 0);
          Assert.fail("Should have failed");
        } catch (NullPointerException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          try {
            CBORObject.WriteValue(ms, -1, 0);
            Assert.fail("Should have failed");
          } catch (IllegalArgumentException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          try {
            CBORObject.WriteValue(ms, 8, 0);
            Assert.fail("Should have failed");
          } catch (IllegalArgumentException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          try {
            CBORObject.WriteValue(ms, 7, 256);
            Assert.fail("Should have failed");
          } catch (IllegalArgumentException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          try {
            CBORObject.WriteValue(ms, 7, Integer.MAX_VALUE);
            Assert.fail("Should have failed");
          } catch (IllegalArgumentException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          try {
            CBORObject.WriteValue(ms, 7, Long.MAX_VALUE);
            Assert.fail("Should have failed");
          } catch (IllegalArgumentException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
          for (int i = 0; i <= 7; ++i) {
            try {
              CBORObject.WriteValue(ms, i, -1);
              Assert.fail("Should have failed");
            } catch (IllegalArgumentException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            try {
              CBORObject.WriteValue(ms, i, Integer.MIN_VALUE);
              Assert.fail("Should have failed");
            } catch (IllegalArgumentException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            try {
              CBORObject.WriteValue(ms, i, -1L);
              Assert.fail("Should have failed");
            } catch (IllegalArgumentException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            try {
              CBORObject.WriteValue(ms, i, Long.MIN_VALUE);
              Assert.fail("Should have failed");
            } catch (IllegalArgumentException ex) {
              // NOTE: Intentionally empty
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
          }
          for (int i = 0; i <= 6; ++i) {
            try {
              CBORObject.WriteValue(ms, i, Integer.MAX_VALUE);
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
            try {
              CBORObject.WriteValue(ms, i, Long.MAX_VALUE);
            } catch (Exception ex) {
              Assert.fail(ex.toString());
              throw new IllegalStateException("", ex);
            }
          }
          // Test minimum data length
          int[] ranges = {
            0, 23, 1,
            24, 255, 2,
            256, 266, 3,
            65525, 65535, 3,
            65536, 65546, 5,
          };
          String[] bigRanges = {
            "4294967285", "4294967295",
            "4294967296", "4294967306",
            "18446744073709551604", "18446744073709551615",
          };
          int[] bigSizes = { 5, 9, 9, 5, 9, 9 };
          for (int i = 0; i < ranges.length; i += 3) {
            for (int j = ranges[i]; j <= ranges[i + 1]; ++j) {
              for (int k = 0; k <= 6; ++k) {
                int count;
                count = CBORObject.WriteValue(ms, k, j);
                Assert.assertEquals(ranges[i + 2], count);
                count = CBORObject.WriteValue(ms, k, (long)j);
                Assert.assertEquals(ranges[i + 2], count);
                count = CBORObject.WriteValue(ms, k, EInteger.FromInt32(j));
                Assert.assertEquals(ranges[i + 2], count);
              }
            }
          }
          for (int i = 0; i < bigRanges.length; i += 2) {
            EInteger bj = EInteger.FromString(bigRanges[i]);
            EInteger valueBjEnd = EInteger.FromString(bigRanges[i + 1]);
            while (bj.compareTo(valueBjEnd)< 0) {
              for (int k = 0; k <= 6; ++k) {
                int count;
                count = CBORObject.WriteValue(ms, k, bj);
                Assert.assertEquals(bigSizes[i / 2], count);
              }
              bj = bj.Add(EInteger.FromInt32(1));
            }
          }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException(ex.toString(), ex);
      }
    }

    @Test
    public void TestWriteFloatingPointValue() {
      RandomGenerator r = new RandomGenerator();
      byte[] bytes = new byte[] { 0, 0, 0 };
      try {
        for (int i = 0; i < 0x10000; ++i) {
          bytes[0] = (byte)0xf9;
          bytes[1] = (byte)((i >> 8) & 0xff);
          bytes[2] = (byte)(i & 0xff);
          CBORObject cbor = CBORObject.DecodeFromBytes(bytes);
          if (!cbor.AsNumber().IsNaN()) {
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.WriteFloatingPointValue(
                ms,
                cbor.AsDouble(),
                2);
              TestCommon.AssertByteArraysEqual(bytes, ms.toByteArray());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.WriteFloatingPointValue(
                ms,
                cbor.AsSingle(),
                2);
              TestCommon.AssertByteArraysEqual(bytes, ms.toByteArray());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          }
        }
        // 32-bit values
        bytes = new byte[5];
        for (int i = 0; i < 100000; ++i) {
          bytes[0] = (byte)0xfa;
          for (int j = 1; j <= 4; ++j) {
            bytes[j] = (byte)r.UniformInt(256);
          }

          CBORObject cbor = CBORObject.DecodeFromBytes(bytes);
          if (!cbor.AsNumber().IsNaN()) {
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.WriteFloatingPointValue(
                ms,
                cbor.AsDouble(),
                4);
              TestCommon.AssertByteArraysEqual(bytes, ms.toByteArray());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.WriteFloatingPointValue(
                ms,
                cbor.AsSingle(),
                4);
              TestCommon.AssertByteArraysEqual(bytes, ms.toByteArray());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
          }
        }
        // 64-bit values
        bytes = new byte[9];
        for (int i = 0; i < 100000; ++i) {
          bytes[0] = (byte)0xfb;
          for (int j = 1; j <= 8; ++j) {
            bytes[j] = (byte)r.UniformInt(256);
          }
          CBORObject cbor = CBORObject.DecodeFromBytes(bytes);
          if (!cbor.AsNumber().IsNaN()) {
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.WriteFloatingPointValue(
                ms,
                cbor.AsDouble(),
                8);
              TestCommon.AssertByteArraysEqual(bytes, ms.toByteArray());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            CBORObject c2 = null;
            byte[] c2bytes = null;
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.WriteFloatingPointValue(
                ms,
                cbor.AsSingle(),
                8);
              c2bytes = ms.toByteArray();
              c2 = CBORObject.DecodeFromBytes(
                  c2bytes);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            {
              java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

              CBORObject.WriteFloatingPointValue(
                ms,
                c2.AsSingle(),
                8);
              TestCommon.AssertByteArraysEqual(c2bytes, ms.toByteArray());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
            if (i == 0) {
              {
                java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

                try {
                  CBORObject.WriteFloatingPointValue(ms, cbor.AsSingle(), 5);
                  Assert.fail("Should have failed");
                } catch (IllegalArgumentException ex) {
                  // NOTE: Intentionally empty
                } catch (Exception ex) {
                  Assert.fail(ex.toString());
                  throw new IllegalStateException("", ex);
                }
                try {
                  CBORObject.WriteFloatingPointValue(null, cbor.AsSingle(), 4);
                  Assert.fail("Should have failed");
                } catch (NullPointerException ex) {
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
            }
          }
        }
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    private static String DateTimeToString(
      int year,
      int month,
      int day,
      int hour,
      int minute,
      int second,
      int millisecond) {
      char[] charbuf = new char[millisecond > 0 ? 24 : 20];
      charbuf[0] = (char)('0' + ((year / 1000) % 10));
      charbuf[1] = (char)('0' + ((year / 100) % 10));
      charbuf[2] = (char)('0' + ((year / 10) % 10));
      charbuf[3] = (char)('0' + (year % 10));
      charbuf[4] = '-';
      charbuf[5] = (char)('0' + ((month / 10) % 10));
      charbuf[6] = (char)('0' + (month % 10));
      charbuf[7] = '-';
      charbuf[8] = (char)('0' + ((day / 10) % 10));
      charbuf[9] = (char)('0' + (day % 10));
      charbuf[10] = 'T';
      charbuf[11] = (char)('0' + ((hour / 10) % 10));
      charbuf[12] = (char)('0' + (hour % 10));
      charbuf[13] = ':';
      charbuf[14] = (char)('0' + ((minute / 10) % 10));
      charbuf[15] = (char)('0' + (minute % 10));
      charbuf[16] = ':';
      charbuf[17] = (char)('0' + ((second / 10) % 10));
      charbuf[18] = (char)('0' + (second % 10));
      if (millisecond > 0) {
        charbuf[19] = '.';
        charbuf[20] = (char)('0' + ((millisecond / 100) % 10));
        charbuf[21] = (char)('0' + ((millisecond / 10) % 10));
        charbuf[22] = (char)('0' + (millisecond % 10));
        charbuf[23] = 'Z';
      } else {
        charbuf[19] = 'Z';
      }
      return new String(charbuf);
    }

    private static void TestDateTimeStringNumberOne(String str, long num) {
        CBORObject dtstring = CBORObject.FromObject(str).WithTag(0);
        CBORObject dtnum = CBORObject.FromObject(num).WithTag(1);
        TestDateTimeStringNumberOne(dtstring, dtnum);
    }
    private static void TestDateTimeStringNumberOne(String str, double num) {
        CBORObject dtstring = CBORObject.FromObject(str).WithTag(0);
        CBORObject dtnum = CBORObject.FromObject(num).WithTag(1);
        TestDateTimeStringNumberOne(dtstring, dtnum);
    }
    private static void TestDateTimeStringNumberOne(CBORObject dtstring,
  CBORObject dtnum) {
        CBORDateConverter convNumber = CBORDateConverter.TaggedNumber;
        CBORDateConverter convString = CBORDateConverter.TaggedString;
        CBORObject cbor;
        EInteger[] eiYear = new EInteger[1];
        int[] lesserFields = new int[7];
        String strnum = dtstring + ", " + dtnum;
        cbor = convNumber.ToCBORObject(convNumber.FromCBORObject(dtstring));
        Assert.assertEquals(strnum, dtnum, cbor);
        if (!convNumber.TryGetDateTimeFields(dtstring, eiYear, lesserFields)) {
          Assert.fail(strnum);
        }
        cbor = convNumber.DateTimeFieldsToCBORObject(eiYear[0], lesserFields);
        Assert.assertEquals(strnum, dtnum, cbor);
        cbor = convString.DateTimeFieldsToCBORObject(eiYear[0], lesserFields);
        Assert.assertEquals(strnum, dtstring, cbor);
        cbor = convString.ToCBORObject(convString.FromCBORObject(dtnum));
        Assert.assertEquals(strnum, dtstring, cbor);
        if (!convString.TryGetDateTimeFields(dtnum, eiYear, lesserFields)) {
          Assert.fail(strnum);
        }
        cbor = convNumber.DateTimeFieldsToCBORObject(eiYear[0], lesserFields);
        Assert.assertEquals(strnum, dtnum, cbor);
        cbor = convString.DateTimeFieldsToCBORObject(eiYear[0], lesserFields);
        Assert.assertEquals(strnum, dtstring, cbor);
    }

    @Test
    public void TestDateTimeStringNumber() {
        TestDateTimeStringNumberOne("1970-01-01T00:00:00.25Z", 0.25);
        TestDateTimeStringNumberOne("1970-01-01T00:00:00.75Z", 0.75);
        TestDateTimeStringNumberOne("1969-12-31T23:59:59.75Z", -0.25);
        TestDateTimeStringNumberOne("1969-12-31T23:59:59.25Z", -0.75);
        TestDateTimeStringNumberOne("1970-01-03T00:00:00Z", 172800);
        TestDateTimeStringNumberOne("1970-01-03T00:00:00Z", 172800);
        TestDateTimeStringNumberOne("1970-01-03T00:00:00Z", 172800);
        TestDateTimeStringNumberOne("2001-01-03T00:00:00Z", 978480000);
        TestDateTimeStringNumberOne("2001-01-03T00:00:00.25Z", 978480000.25);
        TestDateTimeStringNumberOne("1960-01-03T00:00:00Z", -315446400);
        TestDateTimeStringNumberOne("1400-01-03T00:00:00Z", -17987270400L);
        TestDateTimeStringNumberOne("2100-01-03T00:00:00Z", 4102617600L);
        TestDateTimeStringNumberOne("1970-01-03T00:00:01Z", 172801);
        TestDateTimeStringNumberOne("2001-01-03T00:00:01Z", 978480001);
        TestDateTimeStringNumberOne("1960-01-03T00:00:01Z", -315446399);
        TestDateTimeStringNumberOne("1960-01-03T00:00:00.25Z", -315446399.75);
        TestDateTimeStringNumberOne("1960-01-03T00:00:00.75Z", -315446399.25);
        TestDateTimeStringNumberOne("1400-01-03T00:00:01Z", -17987270399L);
        TestDateTimeStringNumberOne("2100-01-03T00:00:01Z", 4102617601L);
    }

    public void TestApplyJSONPatchOpAdd(
      CBORObject expected,
      CBORObject src,
      String path,
      Object obj) {
      CBORObject patch = CBORObject.NewMap().Add("op", "add")
          .Add("path", path).Add("value", CBORObject.FromObject(obj));
      patch = CBORObject.NewArray().Add(patch);
      this.TestApplyJSONPatchOp(expected, src, patch);
    }

    public void TestApplyJSONPatchOpReplace(
      CBORObject expected,
      CBORObject src,
      String path,
      Object obj) {
      CBORObject patch = CBORObject.NewMap().Add("op", "replace")
          .Add("path", path).Add("value", CBORObject.FromObject(obj));
      patch = CBORObject.NewArray().Add(patch);
      this.TestApplyJSONPatchOp(expected, src, patch);
    }

    public void TestApplyJSONPatchOpRemove(
      CBORObject expected,
      CBORObject src,
      String path) {
      CBORObject patch = CBORObject.NewMap().Add("op", "remove")
          .Add("path", path);
      patch = CBORObject.NewArray().Add(patch);
      this.TestApplyJSONPatchOp(expected, src, patch);
    }

    public void TestApplyJSONPatchOp(
      CBORObject expected,
      CBORObject src,
      CBORObject patch) {
      CBORObject actual = CBORObject.DecodeFromBytes(src.EncodeToBytes());
      if (expected == null) {
       try {
 actual.ApplyJSONPatch(patch);
 Assert.fail("Should have failed");
} catch (CBORException ex) {
// NOTE: Intentionally empty
} catch (Exception ex) {
 throw new IllegalStateException(ex.toString() + "\n" + patch);
}
      } else {
       try {
          byte[] oldactualbytes = actual.EncodeToBytes();
          CBORObject oldactual = actual;
          actual = actual.ApplyJSONPatch(patch);
          byte[] newactualbytes = oldactual.EncodeToBytes();
          // Check whether the patch didn't change the existing Object
          TestCommon.AssertByteArraysEqual(oldactualbytes, newactualbytes);
} catch (Exception ex) {
throw new IllegalStateException(ex.toString() + "\n" + patch);
}
       Assert.assertEquals(expected, actual);
      }
    }
    private static final String JSONPatchTests = "[]";

    @Test
    public void TestApplyJSONPatchJSONTests() {
      CBORObject tests = CBORObject.FromJSONString(JSONPatchTests,
         new JSONOptions("allowduplicatekeys=1"));
      for (CBORObject testcbor : tests.getValues()) {
        if (testcbor.GetOrDefault("disabled", CBORObject.False).AsBoolean()) {
          continue;
        }
        String
err = testcbor.GetOrDefault("error",
  CBORObject.FromObject("")).AsString();
        String
  comment = testcbor.GetOrDefault("comment",
  CBORObject.FromObject("")).AsString();
        try {
          if (testcbor.ContainsKey("error")) {
            this.TestApplyJSONPatchOp(null, testcbor.get("doc"), testcbor.get("patch"));
          } else {
            this.TestApplyJSONPatchOp(
              testcbor.get("expected"),
              testcbor.get("doc"),
              testcbor.get("patch"));
          }
        } catch (Exception ex) {
          String exmsg = ex.getClass() + "\n" + comment + "\n" + err;
          throw new IllegalStateException(exmsg, ex);
        }
      }
    }

    @Test
    public void TestApplyJSONPatchTest() {
      CBORObject patch;
      patch = CBORObject.NewMap().Add("op", "test")
          .Add("path", "").Add("value",
  CBORObject.NewArray().Add(1).Add(2));
      patch = CBORObject.NewArray().Add(patch);
      CBORObject exp;
      exp = CBORObject.NewArray().Add(1).Add(2);
      this.TestApplyJSONPatchOp(exp, exp, patch);
      patch = CBORObject.NewMap().Add("op", "test")
          .Add("path", "").Add("value",
  CBORObject.NewArray().Add(1).Add(3));
      patch = CBORObject.NewArray().Add(patch);
      this.TestApplyJSONPatchOp(null, exp, patch);
      patch = CBORObject.NewMap().Add("op", "test")
          .Add("path", "").Add("value",
  CBORObject.NewArray().Add(2).Add(2));
      patch = CBORObject.NewArray().Add(patch);
      this.TestApplyJSONPatchOp(null, exp, patch);
      patch = CBORObject.NewMap().Add("op", "test")
          .Add("path", "").Add("value", CBORObject.NewMap().Add(2,
  2));
      patch = CBORObject.NewArray().Add(patch);
      this.TestApplyJSONPatchOp(null, exp, patch);
      patch = CBORObject.NewMap().Add("op", "test")
          .Add("path", "").Add("value", CBORObject.True);
      patch = CBORObject.NewArray().Add(patch);
      this.TestApplyJSONPatchOp(null, exp, patch);
      patch = CBORObject.NewMap().Add("op", "test")
          .Add("path", "").Add("value", CBORObject.Null);
      patch = CBORObject.NewArray().Add(patch);
      this.TestApplyJSONPatchOp(null, exp, patch);
    }

    @Test
    public void TestApplyJSONPatch() {
      // TODO: Finish tests for ApplyJSONPatch
      CBORObject patch;
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("path", "/0"));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);

      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "ADD").Add("path", "/0").Add("value",
  3));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);

      patch = CBORObject.NewArray().Add(
  CBORObject.NewMap().Add("op", "RePlAcE").Add("path", "/0").Add("value", 3));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);

      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "unknown").Add("path", "/0"));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "add").Add("path", "")
            .Add("value", CBORObject.True));
      this.TestApplyJSONPatchOp(
         CBORObject.True,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "add").Add("path", "")
             .Add("value", CBORObject.NewMap()));
      this.TestApplyJSONPatchOp(
         CBORObject.NewMap(),
         CBORObject.NewArray().Add(1),
         patch);

      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "add").Add("path", "/0"));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "add").Add("path", null).Add("value",
  2));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "add").Add("value", 2));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "remove"));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "remove").Add("value", 2));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);

      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "replace"));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "replace").Add("valuuuuu", 2));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);
      patch = CBORObject.NewArray().Add(
         CBORObject.NewMap().Add("op", "replace").Add("path", "/0"));
      this.TestApplyJSONPatchOp(
         null,
         CBORObject.NewArray().Add(1),
         patch);

      this.TestApplyJSONPatchOpReplace(
         CBORObject.NewArray().Add(1).Add(3),
         CBORObject.NewArray().Add(1).Add(2),
         "/1",
         3);
      this.TestApplyJSONPatchOpReplace(
         CBORObject.NewArray().Add(3).Add(2),
         CBORObject.NewArray().Add(1).Add(2),
         "/0",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/00",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/00000",
         3);
      this.TestApplyJSONPatchOpReplace(
         CBORObject.NewMap().Add("f1", "f2").Add("f3", 3),
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/f3",
         3);
      this.TestApplyJSONPatchOpReplace(
         CBORObject.NewMap().Add("f1", 3).Add("f3", "f4"),
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/f1",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/foo",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/f1/xyz",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/f1/",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/0",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/-",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/-",
         3);
      this.TestApplyJSONPatchOpReplace(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/foo",
         3);
      this.TestApplyJSONPatchOpRemove(
         CBORObject.NewArray().Add(1),
         CBORObject.NewArray().Add(1).Add(2),
         "/1");
      this.TestApplyJSONPatchOpRemove(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/01");
      this.TestApplyJSONPatchOpRemove(
         CBORObject.NewArray().Add(2),
         CBORObject.NewArray().Add(1).Add(2),
         "/0");
      this.TestApplyJSONPatchOpRemove(
         CBORObject.NewMap().Add("f1", "f2"),
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/f3");
      this.TestApplyJSONPatchOpRemove(
         CBORObject.NewMap().Add("f3", "f4"),
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/f1");
      this.TestApplyJSONPatchOpRemove(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/foo");
      this.TestApplyJSONPatchOpRemove(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/f1/xyz");
      this.TestApplyJSONPatchOpRemove(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/f1/");
      this.TestApplyJSONPatchOpRemove(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/0");
      this.TestApplyJSONPatchOpRemove(
         null,
         CBORObject.NewMap().Add("f1", "f2").Add("f3", "f4"),
         "/-");
      this.TestApplyJSONPatchOpRemove(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/-");
      this.TestApplyJSONPatchOpRemove(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/foo");
      this.TestApplyJSONPatchOpAdd(
         CBORObject.NewArray().Add(1),
         CBORObject.NewArray(),
         "/-",
         1);
      this.TestApplyJSONPatchOpAdd(
         CBORObject.NewArray().Add(1),
         CBORObject.NewArray(),
         "/0",
         1);
      this.TestApplyJSONPatchOpAdd(
         null,
         CBORObject.NewArray(),
         "/1",
         1);
      this.TestApplyJSONPatchOpAdd(
         CBORObject.NewArray().Add(1).Add(2),
         CBORObject.NewArray().Add(1),
         "/-",
         2);
      this.TestApplyJSONPatchOpAdd(
         CBORObject.NewArray().Add(0).Add(1).Add(2),
         CBORObject.NewArray().Add(1).Add(2),
         "/0",
         0);
      this.TestApplyJSONPatchOpAdd(
         CBORObject.NewArray().Add(1).Add(0).Add(2),
         CBORObject.NewArray().Add(1).Add(2),
         "/1",
         0);
      this.TestApplyJSONPatchOpAdd(
         CBORObject.NewArray().Add(1).Add(2).Add(0),
         CBORObject.NewArray().Add(1).Add(2),
         "/2",
         0);
      this.TestApplyJSONPatchOpAdd(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/3",
         0);
      this.TestApplyJSONPatchOpAdd(
         null,
         CBORObject.NewArray().Add(1).Add(2),
         "/foo",
         0);
      this.TestApplyJSONPatchOpAdd(
         CBORObject.NewMap().Add("foo", "bar"),
         CBORObject.NewMap(),
         "/foo",
         "bar");
      this.TestApplyJSONPatchOpAdd(
         CBORObject.NewMap().Add("foo", "baz"),
         CBORObject.NewMap().Add("foo", "bar"),
         "/foo",
         "baz");
    }

    @Test
    public void TestAtJSONPointer() {
      // TODO: Finish tests for AtJSONPointer
    }

    @Test
    public void TestDateTime() {
      ArrayList<String> dateList = new ArrayList<String>();
      dateList.add("0783-08-19T03:10:29.406Z");
      dateList.add("1954-03-07T16:20:38.256Z");
      RandomGenerator rng = new RandomGenerator();
      for (int i = 0; i < 2000; ++i) {
        String dtstr = DateTimeToString(
            rng.UniformInt(9999) + 1,
            rng.UniformInt(12) + 1,
            rng.UniformInt(28) + 1,
            rng.UniformInt(24),
            rng.UniformInt(60),
            rng.UniformInt(60),
            rng.UniformInt(1000));
        dateList.add(dtstr);
      }
      for (String dtstr : dateList) {
        CBORObject cbor = CBORObject.FromObjectAndTag(dtstr, 0);
        java.util.Date dt = (java.util.Date)cbor.ToObject(java.util.Date.class);
        ToObjectTest.TestToFromObjectRoundTrip(dt);
      }
    }

    public static void TestDateTimeTag1One(String str, long timeValue) {
      TestDateTimeTag1One(str, EInteger.FromInt64(timeValue));
    }

    public static void TestDateTimeTag1One(String str, EInteger ei) {
      CBORObject cbornum;
      cbornum = CBORObject.FromObjectAndTag(str, 0);
      java.util.Date dtx = (java.util.Date)cbornum.ToObject(java.util.Date.class);
      ToObjectTest.TestToFromObjectRoundTrip(dtx);
      cbornum = CBORObject.FromObjectAndTag(ei, 1);
      java.util.Date dtx2 = (java.util.Date)cbornum.ToObject(java.util.Date.class);
      ToObjectTest.TestToFromObjectRoundTrip(dtx2);
      TestCommon.AssertEqualsHashCode(dtx, dtx2);
      if (ei == null) {
        throw new NullPointerException("ei");
      }
      if (ei.CanFitInInt64()) {
        cbornum = CBORObject.FromObjectAndTag(ei.ToInt64Checked(), 1);
        dtx2 = (java.util.Date)cbornum.ToObject(java.util.Date.class);
        TestCommon.AssertEqualsHashCode(dtx, dtx2);
        ToObjectTest.TestToFromObjectRoundTrip(dtx2);
      }
      EFloat ef1 = EFloat.FromEInteger(ei).Plus(EContext.Binary64);
      EFloat ef2 = EFloat.FromEInteger(ei);
      if (ef1.compareTo(ef2) == 0) {
        cbornum = CBORObject.FromObjectAndTag(ef1, 1);
        dtx2 = (java.util.Date)cbornum.ToObject(java.util.Date.class);
        TestCommon.AssertEqualsHashCode(dtx, dtx2);
        ToObjectTest.TestToFromObjectRoundTrip(dtx2);
        cbornum = CBORObject.FromObjectAndTag(ef1.ToDouble(), 1);
        dtx2 = (java.util.Date)cbornum.ToObject(java.util.Date.class);
        TestCommon.AssertEqualsHashCode(dtx, dtx2);
        ToObjectTest.TestToFromObjectRoundTrip(dtx2);
      }
    }

    public static void TestDateTimeTag1One(String str, double dbl) {
      CBORObject cbornum;
      cbornum = CBORObject.FromObjectAndTag(str, 0);
      java.util.Date dtx = (java.util.Date)cbornum.ToObject(java.util.Date.class);
      ToObjectTest.TestToFromObjectRoundTrip(dtx);
      cbornum = CBORObject.FromObjectAndTag(dbl, 1);
      java.util.Date dtx2 = (java.util.Date)cbornum.ToObject(java.util.Date.class);
      ToObjectTest.TestToFromObjectRoundTrip(dtx2);
      TestCommon.AssertEqualsHashCode(dtx, dtx2);
    }

    @Test(timeout = 10000)
    public void TestDateTimeTag1Specific1() {
      // Test speed
      EInteger ei = EInteger.FromString("-14261178672295354872");
      CBORObject cbornum = CBORObject.FromObjectAndTag(ei, 1);
      try {
        java.util.Date dtx = (java.util.Date)cbornum.ToObject(java.util.Date.class);
        ToObjectTest.TestToFromObjectRoundTrip(dtx);
      } catch (CBORException ex) {
        System.out.println("Not supported: " + ei);
      }
    }

    @Test
    public void TestDateTimeSpecific2() {
      TestDateTimeTag1One("1758-09-28T23:25:24Z", -6666626076L);
      TestDateTimeTag1One("1758-09-28T23:25:24.000Z", -6666626076L);
      TestDateTimeTag1One("1758-09-28T23:25:24.500Z", -6666626075.5);
      TestDateTimeTag1One("2325-11-08T01:47:40Z", 11229587260L);
      TestDateTimeTag1One("2325-11-08T01:47:40.000Z", 11229587260L);
      TestDateTimeTag1One("2325-11-08T01:47:40.500Z", 11229587260.5);
      TestDateTimeTag1One("1787-03-04T10:21:24Z", -5769495516L);
      TestDateTimeTag1One("1787-03-04T10:21:24.000Z", -5769495516L);
      TestDateTimeTag1One("1787-03-04T10:21:24.500Z", -5769495515.5);
      TestDateTimeTag1One("1828-11-17T11:59:01Z", -4453358459L);
      TestDateTimeTag1One("1828-11-17T11:59:01.000Z", -4453358459L);
      TestDateTimeTag1One("1828-11-17T11:59:01.500Z", -4453358458.5);
      TestDateTimeTag1One("2379-01-22T01:20:02Z", 12908596802L);
      TestDateTimeTag1One("2379-01-22T01:20:02.000Z", 12908596802L);
      TestDateTimeTag1One("1699-05-31T22:37:24Z", -8538830556L);
      TestDateTimeTag1One("1699-05-31T22:37:24.000Z", -8538830556L);
      TestDateTimeTag1One("1699-05-31T22:37:24.500Z", -8538830555.5);
      TestDateTimeTag1One("2248-02-13T03:16:17Z", 8776523777L);
      TestDateTimeTag1One("2248-02-13T03:16:17.000Z", 8776523777L);
      TestDateTimeTag1One("2248-02-13T03:16:17.500Z", 8776523777.5);
      TestDateTimeTag1One("2136-04-15T16:45:29Z", 5247564329L);
      TestDateTimeTag1One("2136-04-15T16:45:29.000Z", 5247564329L);
      TestDateTimeTag1One("1889-09-05T00:23:45Z", -2534715375L);
      TestDateTimeTag1One("1889-09-05T00:23:45.000Z", -2534715375L);
      TestDateTimeTag1One("1889-09-05T00:23:45.500Z", -2534715374.5);
      TestDateTimeTag1One("2095-08-13T20:04:08Z", 3964104248L);
      TestDateTimeTag1One("2095-08-13T20:04:08.000Z", 3964104248L);
      TestDateTimeTag1One("2095-08-13T20:04:08.500Z", 3964104248.5);
      TestDateTimeTag1One("2475-03-27T17:41:48Z", 15943714908L);
      TestDateTimeTag1One("2475-03-27T17:41:48.000Z", 15943714908L);
      TestDateTimeTag1One("2475-03-27T17:41:48.500Z", 15943714908.5);
      TestDateTimeTag1One("1525-11-18T07:47:54Z", -14015088726L);
      TestDateTimeTag1One("1525-11-18T07:47:54.000Z", -14015088726L);
      TestDateTimeTag1One("2353-01-12T09:36:32Z", 12087308192L);
      TestDateTimeTag1One("2353-01-12T09:36:32.000Z", 12087308192L);
      TestDateTimeTag1One("2353-01-12T09:36:32.500Z", 12087308192.5);
      TestDateTimeTag1One("2218-11-29T08:23:31Z", 7854827011L);
      TestDateTimeTag1One("2218-11-29T08:23:31.000Z", 7854827011L);
      TestDateTimeTag1One("2377-08-21T09:44:12Z", 12863785452L);
      TestDateTimeTag1One("2377-08-21T09:44:12.000Z", 12863785452L);
      TestDateTimeTag1One("2377-08-21T09:44:12.500Z", 12863785452.5);
      TestDateTimeTag1One("1530-09-02T02:13:52Z", -13863995168L);
      TestDateTimeTag1One("1530-09-02T02:13:52.000Z", -13863995168L);
      TestDateTimeTag1One("1530-09-02T02:13:52.500Z", -13863995167.5);
      TestDateTimeTag1One("2319-03-11T18:18:48Z", 11019349128L);
      TestDateTimeTag1One("2319-03-11T18:18:48.000Z", 11019349128L);
      TestDateTimeTag1One("2319-03-11T18:18:48.500Z", 11019349128.5);
      TestDateTimeTag1One("1602-12-05T09:36:58Z", -11583699782L);
      TestDateTimeTag1One("1602-12-05T09:36:58.000Z", -11583699782L);
      TestDateTimeTag1One("1874-01-25T21:14:10Z", -3027293150L);
      TestDateTimeTag1One("1874-01-25T21:14:10.000Z", -3027293150L);
      TestDateTimeTag1One("1874-01-25T21:14:10.500Z", -3027293149.5);
      TestDateTimeTag1One("1996-02-26T04:09:49Z", 825307789L);
      TestDateTimeTag1One("1996-02-26T04:09:49.000Z", 825307789L);
      TestDateTimeTag1One("1996-02-26T04:09:49.500Z", 825307789.5);
      TestDateTimeTag1One("2113-11-27T22:16:09Z", 4541264169L);
      TestDateTimeTag1One("2113-11-27T22:16:09.000Z", 4541264169L);
      TestDateTimeTag1One("2113-11-27T22:16:09.500Z", 4541264169.5);
      TestDateTimeTag1One("1612-01-07T16:25:51Z", -11296827249L);
      TestDateTimeTag1One("1612-01-07T16:25:51.000Z", -11296827249L);
      TestDateTimeTag1One("1612-01-07T16:25:51.500Z", -11296827248.5);
      TestDateTimeTag1One("2077-12-08T22:15:00Z", 3406227300L);
      TestDateTimeTag1One("2077-12-08T22:15:00.000Z", 3406227300L);
      TestDateTimeTag1One("2077-12-08T22:15:00.500Z", 3406227300.5);
      TestDateTimeTag1One("1820-07-06T12:06:08Z", -4717396432L);
      TestDateTimeTag1One("1820-07-06T12:06:08.000Z", -4717396432L);
      TestDateTimeTag1One("1820-07-06T12:06:08.500Z", -4717396431.5);
      TestDateTimeTag1One("1724-01-17T16:42:20Z", -7761597460L);
      TestDateTimeTag1One("1724-01-17T16:42:20.000Z", -7761597460L);
      TestDateTimeTag1One("1724-01-17T16:42:20.500Z", -7761597459.5);
      TestDateTimeTag1One("2316-03-11T00:46:54Z", 10924678014L);
      TestDateTimeTag1One("2316-03-11T00:46:54.000Z", 10924678014L);
      TestDateTimeTag1One("2495-07-18T22:11:29Z", 16584646289L);
      TestDateTimeTag1One("2495-07-18T22:11:29.000Z", 16584646289L);
      TestDateTimeTag1One("2495-07-18T22:11:29.500Z", 16584646289.5);
      TestDateTimeTag1One("1874-04-25T08:52:46Z", -3019561634L);
      TestDateTimeTag1One("1874-04-25T08:52:46.000Z", -3019561634L);
      TestDateTimeTag1One("1874-04-25T08:52:46.500Z", -3019561633.5);
      TestDateTimeTag1One("2226-05-18T19:38:50Z", 8090480330L);
      TestDateTimeTag1One("2226-05-18T19:38:50.000Z", 8090480330L);
      TestDateTimeTag1One("2226-05-18T19:38:50.500Z", 8090480330.5);
      TestDateTimeTag1One("2108-06-26T09:01:48Z", 4370144508L);
      TestDateTimeTag1One("2108-06-26T09:01:48.000Z", 4370144508L);
      TestDateTimeTag1One("2108-06-26T09:01:48.500Z", 4370144508.5);
      TestDateTimeTag1One("1955-10-03T06:06:55Z", -449603585L);
      TestDateTimeTag1One("1955-10-03T06:06:55.000Z", -449603585L);
      TestDateTimeTag1One("1955-10-03T06:06:55.500Z", -449603584.5);
      TestDateTimeTag1One("1906-03-26T17:32:58Z", -2012365622L);
      TestDateTimeTag1One("1906-03-26T17:32:58.000Z", -2012365622L);
      TestDateTimeTag1One("1906-03-26T17:32:58.500Z", -2012365621.5);
      TestDateTimeTag1One("1592-03-10T03:46:03Z", -11922581637L);
      TestDateTimeTag1One("1592-03-10T03:46:03.000Z", -11922581637L);
      TestDateTimeTag1One("1592-03-10T03:46:03.500Z", -11922581636.5);
      TestDateTimeTag1One("2433-12-19T01:24:19Z", 14641349059L);
      TestDateTimeTag1One("2433-12-19T01:24:19.000Z", 14641349059L);
      TestDateTimeTag1One("2433-12-19T01:24:19.500Z", 14641349059.5);
      TestDateTimeTag1One("1802-02-07T09:43:23Z", -5298358597L);
      TestDateTimeTag1One("1802-02-07T09:43:23.000Z", -5298358597L);
      TestDateTimeTag1One("2318-04-11T20:11:23Z", 10990498283L);
      TestDateTimeTag1One("2318-04-11T20:11:23.000Z", 10990498283L);
      TestDateTimeTag1One("2318-04-11T20:11:23.500Z", 10990498283.5);
      TestDateTimeTag1One("2083-01-06T11:06:22Z", 3566459182L);
      TestDateTimeTag1One("2083-01-06T11:06:22.000Z", 3566459182L);
      TestDateTimeTag1One("2083-01-06T11:06:22.500Z", 3566459182.5);
      TestDateTimeTag1One("1561-08-16T19:31:48Z", -12887094492L);
      TestDateTimeTag1One("1561-08-16T19:31:48.000Z", -12887094492L);
      TestDateTimeTag1One("1561-08-16T19:31:48.500Z", -12887094491.5);
      TestDateTimeTag1One("2475-11-05T21:20:03Z", 15962995203L);
      TestDateTimeTag1One("2475-11-05T21:20:03.000Z", 15962995203L);
      TestDateTimeTag1One("2475-11-05T21:20:03.500Z", 15962995203.5);
      TestDateTimeTag1One("2209-05-13T09:31:56Z", 7553554316L);
      TestDateTimeTag1One("2209-05-13T09:31:56.000Z", 7553554316L);
      TestDateTimeTag1One("2209-05-13T09:31:56.500Z", 7553554316.5);
      TestDateTimeTag1One("1943-06-25T19:09:49Z", -836887811L);
      TestDateTimeTag1One("1943-06-25T19:09:49.000Z", -836887811L);
      TestDateTimeTag1One("1943-06-25T19:09:49.500Z", -836887810.5);
      TestDateTimeTag1One("1751-09-18T07:31:00Z", -6888472140L);
      TestDateTimeTag1One("1751-09-18T07:31:00.000Z", -6888472140L);
      TestDateTimeTag1One("1751-09-18T07:31:00.500Z", -6888472139.5);
      TestDateTimeTag1One("1538-05-07T23:40:25Z", -13621652375L);
      TestDateTimeTag1One("1538-05-07T23:40:25.000Z", -13621652375L);
      TestDateTimeTag1One("1538-05-07T23:40:25.500Z", -13621652374.5);
      TestDateTimeTag1One("1628-02-10T00:07:33Z", -10789026747L);
      TestDateTimeTag1One("1628-02-10T00:07:33.000Z", -10789026747L);
      TestDateTimeTag1One("1628-02-10T00:07:33.500Z", -10789026746.5);
      TestDateTimeTag1One("1584-08-23T09:30:49Z", -12160679351L);
      TestDateTimeTag1One("1584-08-23T09:30:49.000Z", -12160679351L);
      TestDateTimeTag1One("1584-08-23T09:30:49.500Z", -12160679350.5);
      TestDateTimeTag1One("2230-08-28T23:13:43Z", 8225536423L);
      TestDateTimeTag1One("2230-08-28T23:13:43.000Z", 8225536423L);
      TestDateTimeTag1One("1846-02-19T20:02:33Z", -3908750247L);
      TestDateTimeTag1One("1846-02-19T20:02:33.000Z", -3908750247L);
      TestDateTimeTag1One("1846-02-19T20:02:33.500Z", -3908750246.5);
      TestDateTimeTag1One("2114-07-28T00:06:13Z", 4562179573L);
      TestDateTimeTag1One("2114-07-28T00:06:13.000Z", 4562179573L);
      TestDateTimeTag1One("2114-07-28T00:06:13.500Z", 4562179573.5);
      TestDateTimeTag1One("1855-04-03T15:29:33Z", -3621054627L);
      TestDateTimeTag1One("1855-04-03T15:29:33.000Z", -3621054627L);
      TestDateTimeTag1One("1855-04-03T15:29:33.500Z", -3621054626.5);
      TestDateTimeTag1One("1532-02-04T13:08:22Z", -13819027898L);
      TestDateTimeTag1One("1532-02-04T13:08:22.000Z", -13819027898L);
      TestDateTimeTag1One("2285-12-28T16:35:29Z", 9971742929L);
      TestDateTimeTag1One("2285-12-28T16:35:29.000Z", 9971742929L);
      TestDateTimeTag1One("2285-12-28T16:35:29.500Z", 9971742929.5);
      TestDateTimeTag1One("1784-08-08T15:25:01Z", -5850520499L);
      TestDateTimeTag1One("1784-08-08T15:25:01.000Z", -5850520499L);
      TestDateTimeTag1One("2190-06-25T10:55:10Z", 6957744910L);
      TestDateTimeTag1One("2190-06-25T10:55:10.000Z", 6957744910L);
      TestDateTimeTag1One("2190-06-25T10:55:10.500Z", 6957744910.5);
      TestDateTimeTag1One("2263-10-08T20:28:28Z", 9270448108L);
      TestDateTimeTag1One("2263-10-08T20:28:28.000Z", 9270448108L);
      TestDateTimeTag1One("2263-10-08T20:28:28.500Z", 9270448108.5);
      TestDateTimeTag1One("2036-05-12T10:02:45Z", 2094199365L);
      TestDateTimeTag1One("2036-05-12T10:02:45.000Z", 2094199365L);
      TestDateTimeTag1One("2036-05-12T10:02:45.500Z", 2094199365.5);
      TestDateTimeTag1One("2166-09-08T09:25:14Z", 6206837114L);
      TestDateTimeTag1One("2166-09-08T09:25:14.000Z", 6206837114L);
      TestDateTimeTag1One("2166-09-08T09:25:14.500Z", 6206837114.5);
      TestDateTimeTag1One("1698-12-30T18:31:11Z", -8551978129L);
      TestDateTimeTag1One("1698-12-30T18:31:11.000Z", -8551978129L);
      TestDateTimeTag1One("1780-10-16T15:02:56Z", -5970790624L);
      TestDateTimeTag1One("1780-10-16T15:02:56.000Z", -5970790624L);
      TestDateTimeTag1One("1780-10-16T15:02:56.500Z", -5970790623.5);
      TestDateTimeTag1One("1710-10-12T20:07:58Z", -8180193122L);
      TestDateTimeTag1One("1710-10-12T20:07:58.000Z", -8180193122L);
      TestDateTimeTag1One("1710-10-12T20:07:58.500Z", -8180193121.5);
      TestDateTimeTag1One("2034-09-28T04:45:04Z", 2043031504L);
      TestDateTimeTag1One("2034-09-28T04:45:04.000Z", 2043031504L);
      TestDateTimeTag1One("2034-09-28T04:45:04.500Z", 2043031504.5);
      TestDateTimeTag1One("1801-12-10T15:45:47Z", -5303434453L);
      TestDateTimeTag1One("1801-12-10T15:45:47.000Z", -5303434453L);
      TestDateTimeTag1One("1537-08-24T13:13:09Z", -13643808411L);
      TestDateTimeTag1One("1537-08-24T13:13:09.000Z", -13643808411L);
      TestDateTimeTag1One("1537-08-24T13:13:09.500Z", -13643808410.5);
      TestDateTimeTag1One("2249-09-24T21:07:14Z", 8827477634L);
      TestDateTimeTag1One("2249-09-24T21:07:14.000Z", 8827477634L);
      TestDateTimeTag1One("2249-09-24T21:07:14.500Z", 8827477634.5);
      TestDateTimeTag1One("2137-11-27T05:22:38Z", 5298585758L);
      TestDateTimeTag1One("2137-11-27T05:22:38.000Z", 5298585758L);
      TestDateTimeTag1One("2137-11-27T05:22:38.500Z", 5298585758.5);
      TestDateTimeTag1One("2123-07-31T13:09:34Z", 4846482574L);
      TestDateTimeTag1One("2123-07-31T13:09:34.000Z", 4846482574L);
      TestDateTimeTag1One("2123-07-31T13:09:34.500Z", 4846482574.5);
      TestDateTimeTag1One("2242-01-31T12:14:20Z", 8586130460L);
      TestDateTimeTag1One("2242-01-31T12:14:20.000Z", 8586130460L);
      TestDateTimeTag1One("2242-01-31T12:14:20.500Z", 8586130460.5);
      TestDateTimeTag1One("2232-11-04T21:12:33Z", 8294562753L);
      TestDateTimeTag1One("2232-11-04T21:12:33.000Z", 8294562753L);
      TestDateTimeTag1One("1590-12-06T04:30:48Z", -11962322952L);
      TestDateTimeTag1One("1590-12-06T04:30:48.000Z", -11962322952L);
      TestDateTimeTag1One("1590-12-06T04:30:48.500Z", -11962322951.5);
      TestDateTimeTag1One("1910-05-16T17:54:04Z", -1881727556L);
      TestDateTimeTag1One("1910-05-16T17:54:04.000Z", -1881727556L);
      TestDateTimeTag1One("1910-05-16T17:54:04.500Z", -1881727555.5);
      TestDateTimeTag1One("2482-06-15T23:28:00Z", 16171572480L);
      TestDateTimeTag1One("2482-06-15T23:28:00.000Z", 16171572480L);
      TestDateTimeTag1One("2482-06-15T23:28:00.500Z", 16171572480.5);
      TestDateTimeTag1One("1808-01-17T13:11:23Z", -5110858117L);
      TestDateTimeTag1One("1808-01-17T13:11:23.000Z", -5110858117L);
      TestDateTimeTag1One("1872-05-04T12:15:05Z", -3081843895L);
      TestDateTimeTag1One("1872-05-04T12:15:05.000Z", -3081843895L);
      TestDateTimeTag1One("1872-05-04T12:15:05.500Z", -3081843894.5);
      TestDateTimeTag1One("1719-05-18T16:44:33Z", -7908909327L);
      TestDateTimeTag1One("1719-05-18T16:44:33.000Z", -7908909327L);
      TestDateTimeTag1One("2137-05-26T02:17:32Z", 5282590652L);
      TestDateTimeTag1One("2137-05-26T02:17:32.000Z", 5282590652L);
      TestDateTimeTag1One("2137-05-26T02:17:32.500Z", 5282590652.5);
      TestDateTimeTag1One("1714-06-15T13:41:14Z", -8064267526L);
      TestDateTimeTag1One("1714-06-15T13:41:14.000Z", -8064267526L);
      TestDateTimeTag1One("1714-06-15T13:41:14.500Z", -8064267525.5);
      TestDateTimeTag1One("1878-12-03T20:14:03Z", -2874109557L);
      TestDateTimeTag1One("1878-12-03T20:14:03.000Z", -2874109557L);
      TestDateTimeTag1One("1878-12-03T20:14:03.500Z", -2874109556.5);
      TestDateTimeTag1One("2190-11-26T23:45:55Z", 6971096755L);
      TestDateTimeTag1One("2190-11-26T23:45:55.000Z", 6971096755L);
      TestDateTimeTag1One("2020-01-22T15:58:52Z", 1579708732L);
      TestDateTimeTag1One("2020-01-22T15:58:52.000Z", 1579708732L);
      TestDateTimeTag1One("2020-01-22T15:58:52.500Z", 1579708732.5);
      TestDateTimeTag1One("2245-10-06T15:40:51Z", 8702264451L);
      TestDateTimeTag1One("2245-10-06T15:40:51.000Z", 8702264451L);
      TestDateTimeTag1One("2245-10-06T15:40:51.500Z", 8702264451.5);
      TestDateTimeTag1One("1647-08-10T21:26:16Z", -10173695624L);
      TestDateTimeTag1One("1647-08-10T21:26:16.000Z", -10173695624L);
      TestDateTimeTag1One("1647-08-10T21:26:16.500Z", -10173695623.5);
      TestDateTimeTag1One("1628-11-10T01:03:36Z", -10765349784L);
      TestDateTimeTag1One("1628-11-10T01:03:36.000Z", -10765349784L);
      TestDateTimeTag1One("1628-11-10T01:03:36.500Z", -10765349783.5);
      TestDateTimeTag1One("2359-11-30T16:24:04Z", 12304455844L);
      TestDateTimeTag1One("2359-11-30T16:24:04.000Z", 12304455844L);
      TestDateTimeTag1One("2359-11-30T16:24:04.500Z", 12304455844.5);
      TestDateTimeTag1One("1833-10-12T18:44:22Z", -4298678138L);
      TestDateTimeTag1One("1833-10-12T18:44:22.000Z", -4298678138L);
      TestDateTimeTag1One("1833-10-12T18:44:22.500Z", -4298678137.5);
      TestDateTimeTag1One("1550-07-27T20:11:15Z", -13235975325L);
      TestDateTimeTag1One("1550-07-27T20:11:15.000Z", -13235975325L);
      TestDateTimeTag1One("1550-07-27T20:11:15.500Z", -13235975324.5);
      TestDateTimeTag1One("2376-11-23T23:17:49Z", 12840419869L);
      TestDateTimeTag1One("2376-11-23T23:17:49.000Z", 12840419869L);
      TestDateTimeTag1One("2376-11-23T23:17:49.500Z", 12840419869.5);
      TestDateTimeTag1One("2291-11-16T10:53:45Z", 10157396025L);
      TestDateTimeTag1One("2291-11-16T10:53:45.000Z", 10157396025L);
      TestDateTimeTag1One("2291-11-16T10:53:45.500Z", 10157396025.5);
      TestDateTimeTag1One("2349-11-15T11:45:50Z", 11987610350L);
      TestDateTimeTag1One("2349-11-15T11:45:50.000Z", 11987610350L);
      TestDateTimeTag1One("2059-05-22T21:03:13Z", 2820862993L);
      TestDateTimeTag1One("2059-05-22T21:03:13.000Z", 2820862993L);
      TestDateTimeTag1One("2059-05-22T21:03:13.500Z", 2820862993.5);
      TestDateTimeTag1One("1601-04-03T01:34:37Z", -11636519123L);
      TestDateTimeTag1One("1601-04-03T01:34:37.000Z", -11636519123L);
      TestDateTimeTag1One("1601-04-03T01:34:37.500Z", -11636519122.5);
      TestDateTimeTag1One("1853-11-01T19:05:56Z", -3665796844L);
      TestDateTimeTag1One("1853-11-01T19:05:56.000Z", -3665796844L);
      TestDateTimeTag1One("1853-11-01T19:05:56.500Z", -3665796843.5);
      TestDateTimeTag1One("2465-03-10T00:10:34Z", 15626650234L);
      TestDateTimeTag1One("2465-03-10T00:10:34.000Z", 15626650234L);
      TestDateTimeTag1One("1961-06-28T14:59:41Z", -268563619L);
      TestDateTimeTag1One("1961-06-28T14:59:41.000Z", -268563619L);
      TestDateTimeTag1One("1961-06-28T14:59:41.500Z", -268563618.5);
      TestDateTimeTag1One("2078-02-03T01:57:23Z", 3411079043L);
      TestDateTimeTag1One("2078-02-03T01:57:23.000Z", 3411079043L);
      TestDateTimeTag1One("2078-02-03T01:57:23.500Z", 3411079043.5);
      TestDateTimeTag1One("2325-11-05T11:53:57Z", 11229364437L);
      TestDateTimeTag1One("2325-11-05T11:53:57.000Z", 11229364437L);
      TestDateTimeTag1One("2325-11-05T11:53:57.500Z", 11229364437.5);
      TestDateTimeTag1One("2189-02-10T04:55:14Z", 6914523314L);
      TestDateTimeTag1One("2189-02-10T04:55:14.000Z", 6914523314L);
      TestDateTimeTag1One("2189-02-10T04:55:14.500Z", 6914523314.5);
      TestDateTimeTag1One("2416-04-20T21:48:33Z", 14083969713L);
      TestDateTimeTag1One("2416-04-20T21:48:33.000Z", 14083969713L);
      TestDateTimeTag1One("2416-04-20T21:48:33.500Z", 14083969713.5);
      TestDateTimeTag1One("2009-06-24T20:06:34Z", 1245873994L);
      TestDateTimeTag1One("2009-06-24T20:06:34.000Z", 1245873994L);
      TestDateTimeTag1One("2009-06-24T20:06:34.500Z", 1245873994.5);
      TestDateTimeTag1One("2488-05-20T22:56:10Z", 16358712970L);
      TestDateTimeTag1One("2488-05-20T22:56:10.000Z", 16358712970L);
      TestDateTimeTag1One("1519-07-05T21:55:20Z", -14216177080L);
      TestDateTimeTag1One("1519-07-05T21:55:20.000Z", -14216177080L);
      TestDateTimeTag1One("1519-07-05T21:55:20.500Z", -14216177079.5);
      TestDateTimeTag1One("2349-05-25T11:44:14Z", 11972576654L);
      TestDateTimeTag1One("2349-05-25T11:44:14.000Z", 11972576654L);
    }

    @Test(timeout = 100000)
    public void TestDateTimeTag1() {
      CBORObject cbornum;
      RandomGenerator rg = new RandomGenerator();
      java.util.Date dt, dt2;
      for (int i = 0; i < 1000; ++i) {
        EInteger ei = CBORTestCommon.RandomEIntegerMajorType0Or1(rg);
        cbornum = CBORObject.FromObjectAndTag(ei, 1);
        try {
          java.util.Date dtx = (java.util.Date)cbornum.ToObject(java.util.Date.class);
          ToObjectTest.TestToFromObjectRoundTrip(dtx);
        } catch (CBORException ex) {
          // System.out.println("Not supported: "+ei);
        }
      }
      for (int i = 0; i < 1000; ++i) {
        double dbl = RandomObjects.RandomFiniteDouble(rg);
        cbornum = CBORObject.FromObjectAndTag(dbl, 1);
        try {
          java.util.Date dtx = (java.util.Date)cbornum.ToObject(java.util.Date.class);
          ToObjectTest.TestToFromObjectRoundTrip(dtx);
        } catch (CBORException ex) {
          // System.out.println("Not supported: "+dbl);
        }
      }
      String dateStr = "1970-01-01T00:00:00.000Z";
      CBORObject cbor = CBORObject.FromObjectAndTag(dateStr, 0);
      dt = (java.util.Date)cbor.ToObject(java.util.Date.class);
      CBORObject cbor2 = CBORObject.FromObjectAndTag(0, 1);
      dt2 = (java.util.Date)cbor.ToObject(java.util.Date.class);
      Assert.assertEquals(dt2, dt);
    }

    private static CBORObject FromJSON(String json, JSONOptions jsonop) {
      // System.Diagnostics.Stopwatch sw = new System.Diagnostics.Stopwatch();
      // sw.Start();
      CBORObject cbor = CBORObject.FromJSONString(json, jsonop);
      // sw.Stop();
      // System.out.println("" + sw.getElapsedMilliseconds() + " ms");
      return cbor;
    }

    private static CBORObject FromJSON(String json, String numconv) {
      return FromJSON(json, new JSONOptions("numberconversion=" + numconv));
    }

    public static void AssertJSONDouble(
      String json,
      String numconv,
      double dbl) {
      JSONOptions opt = new JSONOptions("numberconversion=" + numconv);
      CBORObject[] cbors = {
        FromJSON(json, numconv),
        CBORDataUtilities.ParseJSONNumber(json, opt),
      };
      for (CBORObject cbor : cbors) {
        if (cbor.getType() != CBORType.FloatingPoint) {
          Assert.assertEquals(json + " " + numconv + " " + dbl,CBORType.FloatingPoint,cbor.getType());
        }
        double cbordbl = cbor.AsDoubleValue();
        if (dbl != cbordbl) {
          Assert.fail("dbl = " + dbl + ", cbordbl = " + cbordbl + ", " +
             json + " " + numconv + " " + dbl);
        }
      }
    }

    public static void AssertJSONInteger(
      String json,
      String numconv,
      long longval) {
      JSONOptions opt = new JSONOptions("numberconversion=" + numconv);
      CBORObject[] cbors = {
        FromJSON(json, numconv),
        CBORDataUtilities.ParseJSONNumber(json, opt),
      };
      for (CBORObject cbor : cbors) {
        if (cbor.getType() != CBORType.Integer) {
          String msg = json + " " + numconv + " " + longval;
          msg = msg.substring(0, Math.min(100, msg.length()));
          if (msg.length() > 100) {
            msg += "...";
          }
          Assert.assertEquals(msg, CBORType.Integer, cbor.getType());
        }
        Assert.assertEquals(longval, cbor.AsInt64Value());
      }
    }

    public static void AssertJSONInteger(
      String json,
      String numconv,
      int intval) {
      JSONOptions opt = new JSONOptions("numberconversion=" + numconv);
      CBORObject[] cbors = {
        FromJSON(json, numconv),
        CBORDataUtilities.ParseJSONNumber(json, opt),
      };
      for (CBORObject cbor : cbors) {
        if (cbor.getType() != CBORType.Integer) {
          String msg = json + " " + numconv + " " + intval;
          msg = msg.substring(0, Math.min(100, msg.length()));
          if (msg.length() > 100) {
            msg += "...";
          }
          Assert.assertEquals(msg, CBORType.Integer, cbor.getType());
        }
        Assert.assertEquals(intval, cbor.AsInt32Value());
      }
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringLongSpecific1() {
      JSONOptions jsonop = JSONOptions.Default;
      String json = "{\"x\":-9.2574033594381E-7962\u002c\"1\":" +
        "-2.8131427974929237E+240}";
      try {
        FromJSON(json, jsonop);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestFromJsonStringFastCases() {
      JSONOptions op = new JSONOptions("numberconversion=double");
      Assert.assertEquals(
        JSONOptions.ConversionMode.Double,
        op.getNumberConversion());
      op = new JSONOptions("numberconversion=intorfloat");
      Assert.assertEquals(
        JSONOptions.ConversionMode.IntOrFloat,
        op.getNumberConversion());
      // System.Diagnostics.Stopwatch sw = new System.Diagnostics.Stopwatch();
      // sw.Start();
      String manyzeros = TestCommon.Repeat("0", 1000000);
      String manythrees = TestCommon.Repeat("3", 1000000);
      AssertJSONDouble(
        "0e-" + manyzeros,
        "double",
        0.0);

      AssertJSONDouble(
        "0." + manyzeros,
        "double",
        0.0);

      AssertJSONDouble(
        "0." + manyzeros + "e-9999999999999",
        "double",
        0.0);

      AssertJSONDouble(
        manythrees + "e-9999999999999",
        "double",
        0.0);

      AssertJSONDouble(
        manythrees + "e-9999999999999",
        "intorfloat",
        0.0);

      AssertJSONInteger(
        manythrees + "e-9999999999999",
        "intorfloatfromdouble",
        0);

      AssertJSONDouble(
        "0." + manyzeros + "e-99999999",
        "double",
        0.0);

      AssertJSONDouble(
        manythrees + "e-99999999",
        "double",
        0.0);

      AssertJSONDouble(
        manythrees + "e-99999999",
        "intorfloat",
        0.0);
      AssertJSONInteger(
        manythrees + "e-99999999",
        "intorfloatfromdouble",
        0);
      AssertJSONInteger(
        "0e-" + manyzeros,
        "intorfloat",
        0);
      AssertJSONInteger(
        "0e-" + manyzeros,
        "intorfloatfromdouble",
        0);
      AssertJSONInteger(
        "-0e-" + manyzeros,
        "intorfloat",
        0);
      AssertJSONInteger(
        "-0e-" + manyzeros,
        "intorfloatfromdouble",
        0);
      AssertJSONInteger(
        "0." + manyzeros,
        "intorfloat",
        0);
      AssertJSONInteger(
        "0." + manyzeros,
        "intorfloatfromdouble",
        0);
      AssertJSONInteger(
        "-0." + manyzeros,
        "intorfloat",
        0);
      AssertJSONInteger(
        "-0." + manyzeros,
        "intorfloatfromdouble",
        0);
    }

    @Test
    public void TestFromJsonStringFiniteDoubleSpec() {
      RandomGenerator rg = new RandomGenerator();
      for (int i = 0; i < 10000; ++i) {
        double dbl = RandomObjects.RandomFiniteDouble(rg);
        EFloat efd = EFloat.FromDouble(dbl);
        AssertJSONDouble(
          efd.ToShortestString(EContext.Binary64),
          "double",
          dbl);
        AssertJSONDouble(
          efd.toString(),
          "double",
          dbl);
      }
    }

    @Test
    public void TestEDecimalEFloatWithHighExponent() {
      String decstr = "0E100441809235791722330759976";
      Assert.assertEquals(0L, EDecimal.FromString(decstr).ToDoubleBits());
      Assert.assertEquals(0L, EFloat.FromString(decstr).ToDoubleBits());
    {
        Object objectTemp = 0L;
        Object objectTemp2 = EDecimal.FromString(decstr,
  EContext.Decimal32).ToDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = 0L;
        Object objectTemp2 = EFloat.FromString(decstr,
  EContext.Binary64).ToDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      decstr = "0E-100441809235791722330759976";
      Assert.assertEquals(0L, EDecimal.FromString(decstr).ToDoubleBits());
      Assert.assertEquals(0L, EFloat.FromString(decstr).ToDoubleBits());
    {
        Object objectTemp = 0L;
        Object objectTemp2 = EDecimal.FromString(decstr,
  EContext.Decimal32).ToDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = 0L;
        Object objectTemp2 = EFloat.FromString(decstr,
  EContext.Binary64).ToDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      decstr = "-0E100441809235791722330759976";
      long negzero = 1L << 63;
      Assert.assertEquals(negzero, EDecimal.FromString(decstr).ToDoubleBits());
      Assert.assertEquals(negzero, EFloat.FromString(decstr).ToDoubleBits());
    {
        Object objectTemp = negzero;
        Object objectTemp2 = EDecimal.FromString(decstr,
  EContext.Decimal32).ToDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = negzero;
        Object objectTemp2 = EFloat.FromString(decstr,
  EContext.Binary64).ToDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      decstr = "-0E-100441809235791722330759976";
      Assert.assertEquals(negzero, EDecimal.FromString(decstr).ToDoubleBits());
      Assert.assertEquals(negzero, EFloat.FromString(decstr).ToDoubleBits());
    {
        Object objectTemp = negzero;
        Object objectTemp2 = EDecimal.FromString(decstr,
  EContext.Decimal32).ToDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = negzero;
        Object objectTemp2 = EFloat.FromString(decstr,
  EContext.Binary64).ToDoubleBits();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestFromJsonStringZeroWithHighExponent() {
      String decstr = "0E100441809235791722330759976";
      EDecimal ed = EDecimal.FromString(decstr);
      double dbl = ed.ToDouble();
      Assert.assertEquals((double)0, dbl, 0);
      AssertJSONDouble(decstr, "double", dbl);
      AssertJSONInteger(decstr, "intorfloat", 0);
      decstr = "0E1321909565013040040586";
      ed = EDecimal.FromString(decstr);
      dbl = ed.ToDouble();
      Assert.assertEquals((double)0, dbl, 0);
      AssertJSONDouble(decstr, "double", dbl);
      AssertJSONInteger(decstr, "intorfloat", 0);
      double dblnegzero = EFloat.FromString("-0").ToDouble();
      AssertJSONDouble("0E-1321909565013040040586", "double", 0.0);
      AssertJSONInteger("0E-1321909565013040040586", "intorfloat", 0);
      AssertJSONDouble("-0E1321909565013040040586", "double", dblnegzero);
      AssertJSONInteger("-0E1321909565013040040586", "intorfloat", 0);
      AssertJSONDouble("-0E-1321909565013040040586", "double", dblnegzero);
      AssertJSONInteger("-0E-1321909565013040040586", "intorfloat", 0);

      AssertJSONDouble("0E-100441809235791722330759976", "double", 0.0);
      AssertJSONInteger("0E-100441809235791722330759976", "intorfloat", 0);
      AssertJSONDouble("-0E100441809235791722330759976", "double", dblnegzero);
      AssertJSONInteger("-0E100441809235791722330759976", "intorfloat", 0);
      AssertJSONDouble("-0E-100441809235791722330759976", "double", dblnegzero);
      AssertJSONInteger("-0E-100441809235791722330759976", "intorfloat", 0);
    }

    @Test
    public void TestFromJsonStringEDecimalSpec() {
      RandomGenerator rg = new RandomGenerator();
      for (int i = 0; i < 2000; ++i) {
        String[] decstring = new String[1];
        EDecimal ed = RandomObjects.RandomEDecimal(rg, decstring);
        if (decstring[0] == null) {
          Assert.fail();
        }
        double dbl = ed.ToDouble();
        if (((dbl) == Double.POSITIVE_INFINITY) ||
                 ((dbl) == Double.NEGATIVE_INFINITY) ||
                 Double.isNaN(dbl)) {
          continue;
        }
        AssertJSONDouble(
          decstring[0],
          "double",
          dbl);
      }
    }

    @Test
    public void TestFromJsonCTLInString() {
       for (int i = 0; i <= 0x20; ++i) {
          byte[] bytes = { 0x22, (byte)i, 0x22 };
          char[] chars = {(char)0x22, (char)i, (char)0x22 };
          String str = new String(chars, 0, chars.length);
          if (i == 0x20) {
             try {
 CBORObject.FromJSONString(str);
} catch (Exception ex) {
Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
             try {
 CBORObject.FromJSONBytes(bytes);
} catch (Exception ex) {
Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
          } else {
             try {
 CBORObject.FromJSONString(str);
 Assert.fail("Should have failed");
} catch (CBORException ex) {
// NOTE: Intentionally empty
} catch (Exception ex) {
 Assert.fail(ex.toString());
 throw new IllegalStateException("", ex);
}
             try {
 CBORObject.FromJSONBytes(bytes);
 Assert.fail("Should have failed");
} catch (CBORException ex) {
// NOTE: Intentionally empty
} catch (Exception ex) {
 Assert.fail(ex.toString());
 throw new IllegalStateException("", ex);
}
          }
        }
     }

    // @Test
    public void TestFromJsonLeadingTrailingCTLBytes() {
       // TODO: Reenable eventually, once UTF-8 only support
       // for CBORObject.FromJSONBytes is implemented
       for (int i = 0; i <= 0x20; ++i) {
          // Leading CTL
          byte[] bytes = { (byte)i, 0x31 };
          if (i == 0x09 || i == 0x0d || i == 0x0a || i == 0x20) {
             try {
 CBORObject.FromJSONBytes(bytes);
} catch (Exception ex) {
Assert.fail(ex.toString() + "bytes " + i);
throw new IllegalStateException("", ex);
}
          } else {
             try {
 CBORObject.FromJSONBytes(bytes);
 Assert.fail("Should have failed bytes " + i);
} catch (CBORException ex) {
// NOTE: Intentionally empty
} catch (Exception ex) {
 Assert.fail(ex.toString());
 throw new IllegalStateException("", ex);
}
          }
          // Trailing CTL
          bytes = new byte[] { 0x31, (byte)i };
          if (i == 0x09 || i == 0x0d || i == 0x0a || i == 0x20) {
             try {
 CBORObject.FromJSONBytes(bytes);
} catch (Exception ex) {
Assert.fail(ex.toString() + "bytes " + i);
throw new IllegalStateException("", ex);
}
          } else {
             try {
 CBORObject.FromJSONBytes(bytes);
 Assert.fail("Should have failed");
} catch (CBORException ex) {
// NOTE: Intentionally empty
} catch (Exception ex) {
 Assert.fail(ex.toString() + "bytes " + i);
 throw new IllegalStateException("", ex);
}
          }
       }
    }

    @Test
    public void TestFromJsonLeadingTrailingCTL() {
       for (int i = 0; i <= 0x20; ++i) {
          // Leading CTL
          char[] chars = {(char)i, (char)0x31 };
          String str = new String(chars, 0, chars.length);
          if (i == 0x09 || i == 0x0d || i == 0x0a || i == 0x20) {
             try {
 CBORObject.FromJSONString(str);
} catch (Exception ex) {
Assert.fail(ex.toString() + "String " + i);
throw new IllegalStateException("", ex);
}
          } else {
             try {
 CBORObject.FromJSONString(str);
 Assert.fail("Should have failed String " + i);
} catch (CBORException ex) {
// NOTE: Intentionally empty
} catch (Exception ex) {
 Assert.fail(ex.toString());
 throw new IllegalStateException("", ex);
}
          }
          // Trailing CTL
          chars = new char[] { (char)0x31, (char)i};
          str = new String(chars, 0, chars.length);
          if (i == 0x09 || i == 0x0d || i == 0x0a || i == 0x20) {
             try {
 CBORObject.FromJSONString(str);
} catch (Exception ex) {
Assert.fail(ex.toString() + "String " + i);
throw new IllegalStateException("", ex);
}
          } else {
             try {
 CBORObject.FromJSONString(str);
 Assert.fail("Should have failed");
} catch (CBORException ex) {
// NOTE: Intentionally empty
} catch (Exception ex) {
 Assert.fail(ex.toString() + "String " + i);
 throw new IllegalStateException("", ex);
}
          }
       }
    }

    @Test
    public void TestFromJsonStringSmallDoubleSpec() {
      RandomGenerator rg = new RandomGenerator();
      for (int i = 0; i < 10000; ++i) {
        int rv = rg.GetInt32(Integer.MAX_VALUE) * ((rg.GetInt32(2) * 2) - 1);
        String rvstring = TestCommon.IntToString(rv);
        AssertJSONDouble(
          rvstring,
          "double",
          (double)rv);
        AssertJSONInteger(
          rvstring,
          "intorfloat",
          rv);
      }
      AssertJSONDouble("511", "double", 511);
      AssertJSONDouble("-511", "double", -511);
      AssertJSONDouble(
        TestCommon.IntToString(Integer.MAX_VALUE),
        "double",
        (double)Integer.MAX_VALUE);
      AssertJSONDouble(
        TestCommon.IntToString(Integer.MAX_VALUE),
        "double",
        (double)Integer.MAX_VALUE);
      AssertJSONDouble(
        TestCommon.IntToString(Integer.MIN_VALUE),
        "double",
        (double)Integer.MIN_VALUE);
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringSmallDouble() {
      CBORObject cbor;
      AssertJSONDouble("0", "double", 0.0);
      cbor = FromJSON("[0, 1, 2, 3]", "double");
      Assert.assertEquals(4, cbor.size());
      Assert.assertEquals((double)0.0, cbor.get(0).AsDouble(), 0);
      Assert.assertEquals((double)1.0, cbor.get(1).AsDouble(), 0);
      Assert.assertEquals((double)2.0, cbor.get(2).AsDouble(), 0);
      Assert.assertEquals((double)3.0, cbor.get(3).AsDouble(), 0);
      cbor = FromJSON("[0]", "double");
      Assert.assertEquals(1, cbor.size());
      Assert.assertEquals((double)0.0, cbor.get(0).AsDouble(), 0);
      cbor = FromJSON("[-0]", "double");
      Assert.assertEquals(1, cbor.size());
      cbor = FromJSON("[1]", "double");
      Assert.assertEquals(1, cbor.size());
      Assert.assertEquals((double)1.0, cbor.get(0).AsDouble(), 0);
      cbor = FromJSON("[-1]", "double");
      Assert.assertEquals(1, cbor.size());
      Assert.assertEquals((double)-1.0, cbor.get(0).AsDouble(), 0);
      cbor = FromJSON("[-1022,-1023,-1024,-1025,1022,1023,1024,1025]",
          "double");
      Assert.assertEquals(8, cbor.size());
      Assert.assertEquals((double)-1022.0, cbor.get(0).AsDouble(), 0);
      Assert.assertEquals((double)-1023.0, cbor.get(1).AsDouble(), 0);
      Assert.assertEquals((double)-1024.0, cbor.get(2).AsDouble(), 0);
      Assert.assertEquals((double)-1025.0, cbor.get(3).AsDouble(), 0);
      Assert.assertEquals((double)1022.0, cbor.get(4).AsDouble(), 0);
      Assert.assertEquals((double)1023.0, cbor.get(5).AsDouble(), 0);
      Assert.assertEquals((double)1024.0, cbor.get(6).AsDouble(), 0);
      Assert.assertEquals((double)1025.0, cbor.get(7).AsDouble(), 0);
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringLongKindFull() {
      JSONOptions jsonop = new JSONOptions("numberconversion=full");
      String json = TestCommon.Repeat("7", 100000);
      CBORObject cbor = FromJSON(json, jsonop);
      if (!(cbor.isTagged())) {
 Assert.fail();
 }
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringLongKindFull2() {
      JSONOptions jsonop = new JSONOptions("numberconversion=full");
      String json = TestCommon.Repeat("7", 100000) + ".0";
      CBORObject cbor = FromJSON(json, jsonop);
      if (!(cbor.isTagged())) {
 Assert.fail();
 }
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringLongKindFullBad() {
      System.out.println("FullBad 1");
      JSONOptions jsonop = new JSONOptions("numberconversion=full");
      String manysevens = TestCommon.Repeat("7", 1000000);
      String[] badjson = {
        manysevens + "x",
        "7x" + manysevens,
        manysevens + "e0x",
        "-" + manysevens + "x",
        "-7x" + manysevens,
        "-" + manysevens + "e0x",
      };
      for (String str : badjson) {
        try {
          FromJSON(str, jsonop);
          Assert.fail("Should have failed");
        } catch (CBORException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      }
      System.out.println("FullBad 2");
      String json = TestCommon.Repeat("0", 1000000);
      try {
        FromJSON(json, jsonop);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringLongKindsBad() {
      JSONOptions jsonop;
      String json = TestCommon.Repeat("7", 1000000) + "x";
      jsonop = new JSONOptions("numberconversion=double");
      try {
        FromJSON(json, jsonop);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      jsonop = new JSONOptions("numberconversion=intorfloatfromdouble");
      try {
        FromJSON(json, jsonop);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      jsonop = new JSONOptions("numberconversion=intorfloat");
      try {
        FromJSON(json, jsonop);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      json = TestCommon.Repeat("0", 1000000);
      jsonop = new JSONOptions("numberconversion=double");
      try {
        FromJSON(json, jsonop);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      jsonop = new JSONOptions("numberconversion=intorfloatfromdouble");
      try {
        FromJSON(json, jsonop);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      jsonop = new JSONOptions("numberconversion=intorfloat");
      try {
        FromJSON(json, jsonop);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringLongKindIntOrFloatFromDouble() {
      JSONOptions jsonop = new JSONOptions("numberconversion=intorfloatfromdouble");
      String manysevens = TestCommon.Repeat("7", 1000000);
      String json = manysevens;
      CBORObject cbor = FromJSON(json, jsonop);
      Assert.assertEquals(CBORType.FloatingPoint, cbor.getType());
      if (!(cbor.AsDoubleValue() == Double.POSITIVE_INFINITY)) {
 Assert.fail();
 }
      json = manysevens + "e+0";
      cbor = FromJSON(json, jsonop);
      Assert.assertEquals(CBORType.FloatingPoint, cbor.getType());
      if (!(cbor.AsDoubleValue() == Double.POSITIVE_INFINITY)) {
 Assert.fail();
 }
      json = manysevens + "e0";
      cbor = FromJSON(json, jsonop);
      Assert.assertEquals(CBORType.FloatingPoint, cbor.getType());
      if (!(cbor.AsDoubleValue() == Double.POSITIVE_INFINITY)) {
 Assert.fail();
 }
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringLongKindIntOrFloat() {
      JSONOptions jsonop = new JSONOptions("numberconversion=intorfloat");
      String json = TestCommon.Repeat("7", 1000000);
      CBORObject cbor = FromJSON(json, jsonop);
      Assert.assertEquals(CBORType.FloatingPoint, cbor.getType());
      if (!(cbor.AsDoubleValue() == Double.POSITIVE_INFINITY)) {
 Assert.fail();
 }
    }

    @Test(timeout = 10000)
    public void TestFromJsonStringLongKindIntOrFloat2() {
      JSONOptions jsonop = new JSONOptions("numberconversion=intorfloat");
      String json = "-" + TestCommon.Repeat("7", 1000000);
      CBORObject cbor = FromJSON(json, jsonop);
      Assert.assertEquals(CBORType.FloatingPoint, cbor.getType());
      if (!(cbor.AsDoubleValue() == Double.NEGATIVE_INFINITY)) {
 Assert.fail();
 }
    }

    @Test
    public void TestToObject_TypeMapper() {
      CBORTypeMapper mapper = new CBORTypeMapper()
      .AddConverter(String.class, new TestConverter());
      CBORObject cbor = CBORObject.FromObject("UpPeR");
      {
        String stringTemp = (String)cbor.ToObject(String.class, mapper);
        Assert.assertEquals(
          "upper",
          stringTemp);
      }
      cbor = CBORObject.FromObject("TRUE");
      {
        String stringTemp = (String)cbor.ToObject(String.class, mapper);
        Assert.assertEquals(
          "true",
          stringTemp);
      }
      cbor = CBORObject.FromObject("false");
      {
        String stringTemp = (String)cbor.ToObject(String.class, mapper);
        Assert.assertEquals(
          "false",
          stringTemp);
      }
      cbor = CBORObject.FromObject("FALSE");
      {
        String stringTemp = (String)cbor.ToObject(String.class, mapper);
        Assert.assertEquals(
          "false",
          stringTemp);
      }
    }
  }
