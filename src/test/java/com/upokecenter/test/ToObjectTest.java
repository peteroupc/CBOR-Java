package com.upokecenter.test;

import java.util.*;
import java.io.*;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  public class ToObjectTest {
    @Test
    public void TestAsEInteger() {
      if (CBORObject.Null.ToObject(EInteger.class) != null) {
        Assert.fail();
      }
      try {
        CBORObject.True.ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = CBORObjectTest.GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
      String numberString = (String)numberinfo.get("number").ToObject(String.class);
        CBORObject cbornumber =
  ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(numberString));
        if (!numberinfo.get("integer").equals(CBORObject.Null)) {
          Assert.assertEquals(
            numberinfo.get("integer").ToObject(String.class),
            cbornumber.ToObject(EInteger.class).toString());
        } else {
          try {
            cbornumber.ToObject(EInteger.class);
            Assert.fail("Should have failed");
          } catch (ArithmeticException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
        }
      }

      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((float)0.75)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((float)0.99)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((float)0.0000000000000001)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((float)0.5)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((float)1.5)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "1",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((float)2.5)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "2",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((float)328323f)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "328323",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)0.75)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)0.99)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)0.0000000000000001)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)0.5)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)1.5)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "1",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)2.5)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "2",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)328323)
            .ToObject(EInteger.class).toString();
        Assert.assertEquals(
          "328323",
          stringTemp);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
                  .ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString()); throw new
          IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
                  .ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString()); throw new
          IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
                  .ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString()); throw new
          IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
                  .ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString()); throw new
          IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
                  .ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString()); throw new
          IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
                  .ToObject(EInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString()); throw new
          IllegalStateException("", ex);
      }
    }
    @Test
    public void TestAsBoolean() {
      Assert.assertEquals(true, CBORObject.True.ToObject(boolean.class));
      {
        Object objectTemp = true;
        Object objectTemp2 = (Object)ToObjectTest.TestToFromObjectRoundTrip(0)
                      .ToObject(boolean.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = true;
 Object objectTemp2 = (Object)ToObjectTest.TestToFromObjectRoundTrip("")
                      .ToObject(boolean.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      Assert.assertEquals(false, CBORObject.False.ToObject(boolean.class));
      Assert.assertEquals(false, CBORObject.Undefined.ToObject(boolean.class));
      Assert.assertEquals(true, CBORObject.NewArray().ToObject(boolean.class));
      Assert.assertEquals(true, CBORObject.NewMap().ToObject(boolean.class));
    }

    @Test
    public void TestNullBoolean() {
      if (CBORObject.Null.ToObject(boolean.class) != null) {
        Assert.fail();
      }
    }

    @Test
    public void TestAsByte() {
      try {
        CBORObject.NewArray().ToObject(byte.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(byte.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(byte.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(byte.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(byte.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("")
                  .ToObject(byte.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = CBORObjectTest.GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
  (String)numberinfo.get("number").ToObject(String.class)));
        if ((boolean)numberinfo.get("byte").AsBoolean()) {
          Assert.assertEquals(
  TestCommon.StringToInt((String)numberinfo.get("integer").ToObject(String.class)),
            ((int)(Byte)cbornumber.ToObject(byte.class)) & 0xff);
        } else {
          try {
            cbornumber.ToObject(byte.class);
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
  ToObjectTest.TestToFromObjectRoundTrip(i).ToObject(byte.class));
      }
      for (int i = -200; i < 0; ++i) {
        try {
          ToObjectTest.TestToFromObjectRoundTrip(i).ToObject(byte.class);
        } catch (ArithmeticException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString()); throw new
            IllegalStateException("", ex);
        }
      }
      for (int i = 256; i < 512; ++i) {
        try {
          ToObjectTest.TestToFromObjectRoundTrip(i).ToObject(byte.class);
        } catch (ArithmeticException ex) {
          // NOTE: Intentionally empty
        } catch (Exception ex) {
          Assert.fail(ex.toString()); throw new
            IllegalStateException("", ex);
        }
      }
    }

    @Test
    public void TestAsDouble() {
      try {
        CBORObject.NewArray().ToObject(double.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(double.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(double.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(double.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(double.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("")
                  .ToObject(double.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = CBORObjectTest.GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
  (String)numberinfo.get("number").ToObject(String.class)));
        CBORObjectTest.AreEqualExact(
  (double)EDecimal.FromString((String)numberinfo.get("number").ToObject(String.class)).ToDouble(),
          (Double)cbornumber.ToObject(double.class));
      }
    }

    @Test
    public void TestAsEDecimal() {
      {
        Object objectTemp = CBORTestCommon.DecPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
                  .ToObject(EDecimal.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.DecNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
                  .ToObject(EDecimal.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
            .ToObject(EDecimal.class).toString();
        Assert.assertEquals(
          "NaN",
          stringTemp);
      }
      {
        Object objectTemp = CBORTestCommon.DecPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
                  .ToObject(EDecimal.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.DecNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
                  .ToObject(EDecimal.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = "NaN";
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
          .ToObject(EDecimal.class).toString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      try {
        CBORObject.NewArray().ToObject(EDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(EDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(EDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(EDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(EDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("")
                  .ToObject(EDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestAsEFloat() {
      {
        Object objectTemp = CBORTestCommon.FloatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
                  .ToObject(EFloat.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.FloatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
                  .ToObject(EFloat.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      EFloat ef = (EFloat)ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
        .ToObject(EFloat.class);
      if (!(ef.IsNaN())) {
 Assert.fail();
 }
      {
        Object objectTemp = CBORTestCommon.FloatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
                  .ToObject(EFloat.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.FloatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
                  .ToObject(EFloat.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      ef = (EFloat)ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
        .ToObject(EFloat.class);
      if (!(ef.IsNaN())) {
 Assert.fail();
 }
    }
    @Test
    public void TestAsERational() {
      {
        Object objectTemp = CBORTestCommon.RatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
                  .ToObject(ERational.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.RatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
                  .ToObject(ERational.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }

  Assert.assertTrue(ToObjectTest.TestToFromObjectRoundTrip(ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
                .ToObject(ERational.class)).IsNaN());
      {
        Object objectTemp = CBORTestCommon.RatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
                  .ToObject(ERational.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.RatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
                  .ToObject(ERational.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      Assert.assertTrue(
  ToObjectTest.TestToFromObjectRoundTrip(ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
          .ToObject(ERational.class)).IsNaN());
    }
    @Test
    public void TestAsInt16() {
      try {
        CBORObject.NewArray().ToObject(short.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(short.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(short.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(short.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(short.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("")
                  .ToObject(short.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = CBORObjectTest.GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(
   EDecimal.FromString((String)numberinfo.get("number").ToObject(String.class)));
        if ((boolean)numberinfo.get("int16").AsBoolean()) {
          Assert.assertEquals(
  (short)TestCommon.StringToInt((String)numberinfo.get("integer").ToObject(String.class)),
  cbornumber.ToObject(short.class));
        } else {
          try {
            cbornumber.ToObject(short.class);
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
        CBORObject.NewArray().ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
ToObjectTest.TestToFromObjectRoundTrip("") .ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = CBORObjectTest.GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        EDecimal edec =
    EDecimal.FromString((String)numberinfo.get("number").ToObject(String.class));
        CBORObject cbornumber = ToObjectTest.TestToFromObjectRoundTrip(edec);
        boolean isdouble; isdouble = (boolean)numberinfo.get("double").AsBoolean();
        CBORObject cbornumberdouble =
          ToObjectTest.TestToFromObjectRoundTrip(edec.ToDouble());
        boolean issingle; issingle = (boolean)numberinfo.get("single").AsBoolean();
        CBORObject cbornumbersingle =
          ToObjectTest.TestToFromObjectRoundTrip(edec.ToSingle());
        if ((boolean)numberinfo.get("int32").AsBoolean()) {
          Assert.assertEquals(
  TestCommon.StringToInt((String)numberinfo.get("integer").ToObject(String.class)),
    cbornumber.ToObject(int.class));
          if (isdouble) {
            Assert.assertEquals(
  TestCommon.StringToInt((String)numberinfo.get("integer").ToObject(String.class)),
    cbornumberdouble.ToObject(int.class));
          }
          if (issingle) {
            Assert.assertEquals(
  TestCommon.StringToInt((String)numberinfo.get("integer").ToObject(String.class)),
    cbornumbersingle.ToObject(int.class));
          }
        } else {
          try {
            System.out.println(cbornumber.ToObject(int.class));
            System.out.println(cbornumber.ToObject(int.class));
            Assert.fail("Should have failed " + cbornumber);
          } catch (ArithmeticException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString() + cbornumber);
            throw new IllegalStateException("", ex);
          }
          if (isdouble) {
            try {
              cbornumberdouble.ToObject(int.class);
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
              cbornumbersingle.ToObject(int.class);
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
        CBORObject.NewArray().ToObject(long.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(long.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(long.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(long.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(long.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("")
                  .ToObject(long.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = CBORObjectTest.GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        EDecimal edec =
    EDecimal.FromString((String)numberinfo.get("number").ToObject(String.class));
        CBORObject cbornumber = ToObjectTest.TestToFromObjectRoundTrip(edec);
        boolean isdouble; isdouble = (boolean)numberinfo.get("double").AsBoolean();
        CBORObject cbornumberdouble =
          ToObjectTest.TestToFromObjectRoundTrip(edec.ToDouble());
        boolean issingle; issingle = (boolean)numberinfo.get("single").AsBoolean();
        CBORObject cbornumbersingle =
          ToObjectTest.TestToFromObjectRoundTrip(edec.ToSingle());
        if ((boolean)numberinfo.get("int64").AsBoolean()) {
          Assert.assertEquals(
   TestCommon.StringToLong((String)numberinfo.get("integer").ToObject(String.class)),
   cbornumber.ToObject(long.class));
          if (isdouble) {
            Assert.assertEquals(
   TestCommon.StringToLong((String)numberinfo.get("integer").ToObject(String.class)),
   cbornumberdouble.ToObject(long.class));
          }
          if (issingle) {
            Assert.assertEquals(
   TestCommon.StringToLong((String)numberinfo.get("integer").ToObject(String.class)),
   cbornumbersingle.ToObject(long.class));
          }
        } else {
          try {
            cbornumber.ToObject(long.class);
            Assert.fail("Should have failed " + cbornumber);
          } catch (ArithmeticException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString() + cbornumber);
            throw new IllegalStateException("", ex);
          }
          if (isdouble) {
            try {
              cbornumberdouble.ToObject(long.class);
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
              cbornumbersingle.ToObject(long.class);
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
    public void TestAsSingle() {
      try {
        CBORObject.NewArray().ToObject(float.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(float.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(float.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(float.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(float.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("")
                  .ToObject(float.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      CBORObject numbers = CBORObjectTest.GetNumberData();
      for (int i = 0; i < numbers.size(); ++i) {
        CBORObject numberinfo = numbers.get(i);
        CBORObject cbornumber =
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
  (String)numberinfo.get("number").ToObject(String.class)));
        float f1 = (float)EDecimal.FromString((String)numberinfo.get("number").ToObject(String.class)).ToSingle();
        Object f2 = cbornumber.ToObject(float.class);
        if (!((Object)f1).equals(f2)) {
          Assert.fail();
        }
      }
    }
    @Test
    public void TestAsString() {
      {
        String stringTemp = (String)ToObjectTest.TestToFromObjectRoundTrip("test")
.ToObject(String.class);
        Assert.assertEquals(
          "test",
          stringTemp);
      }
      String sb = (String)ToObjectTest.TestToFromObjectRoundTrip(CBORObject.Null)
        .ToObject(String.class);
      if (sb != null) {
        Assert.fail();
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(true).ToObject(String.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(false).ToObject(String.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(5).ToObject(String.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().ToObject(String.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(String.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test(timeout = 5000)
    public void TestToObject() {
      PODClass ao = new PODClass();
      CBORObject co = CBORObject.FromObject(ao);
      if (co.ContainsKey("PrivatePropA")) {
 Assert.fail();
 }
      if (co.ContainsKey("privatePropA")) {
 Assert.fail();
 }
      if (co.ContainsKey("staticPropA")) {
 Assert.fail();
 }
      if (co.ContainsKey("StaticPropA")) {
 Assert.fail();
 }
      co.set("privatePropA",ToObjectTest.TestToFromObjectRoundTrip(999));
      co.set("propA",ToObjectTest.TestToFromObjectRoundTrip(999));
      co.set("floatProp",ToObjectTest.TestToFromObjectRoundTrip(3.5));
      co.set("doubleProp",ToObjectTest.TestToFromObjectRoundTrip(4.5));
      co.set("stringProp",ToObjectTest.TestToFromObjectRoundTrip("stringProp"));
      co.set("stringArray",CBORObject.NewArray().Add("a").Add("b"));
      ao = (PODClass)co.ToObject(PODClass.class);
      // Check whether ToObject ignores private setters
      if (!(ao.HasGoodPrivateProp())) {
 Assert.fail();
 }
      Assert.assertEquals(999, ao.getPropA());
      if (ao.getFloatProp() != (float)3.5) {
        Assert.fail();
      }
      if (ao.getDoubleProp() != 4.5) {
        Assert.fail();
      }
      Assert.assertEquals("stringProp", ao.getStringProp());
      String[] stringArray = ao.getStringArray();
      Assert.assertEquals(2, stringArray.length);
      Assert.assertEquals("a", stringArray[0]);
      Assert.assertEquals("b", stringArray[1]);
      if (ao.isPropC()) {
 Assert.fail();
 }
      co.set("propC",CBORObject.True);
      ao = (PODClass)co.ToObject(PODClass.class);
      if (!(ao.isPropC())) {
 Assert.fail();
 }
      co = CBORObject.True;
      Assert.assertEquals(true, co.ToObject(boolean.class));
      co = CBORObject.False;
      Assert.assertEquals(false, co.ToObject(boolean.class));
      co = ToObjectTest.TestToFromObjectRoundTrip("hello world");
      String stringTemp = (String)co.ToObject(String.class);
      Assert.assertEquals(
        "hello world",
        stringTemp);
      co = CBORObject.NewArray();
      co.Add("hello");
      co.Add("world");
      ArrayList<String> stringList = (ArrayList<String>)
        co.ToObject((new java.lang.reflect.ParameterizedType() {public java.lang.reflect.Type[] getActualTypeArguments() {return new java.lang.reflect.Type[] { String.class };}public java.lang.reflect.Type getRawType() { return ArrayList.class; } public java.lang.reflect.Type getOwnerType() { return null; }}));
      Assert.assertEquals(2, stringList.size());
      Assert.assertEquals("hello", stringList.get(0));
      Assert.assertEquals("world", stringList.get(1));
      List<String> istringList = (List<String>)
        co.ToObject((new java.lang.reflect.ParameterizedType() {public java.lang.reflect.Type[] getActualTypeArguments() {return new java.lang.reflect.Type[] { String.class };}public java.lang.reflect.Type getRawType() { return List.class; } public java.lang.reflect.Type getOwnerType() { return null; }}));
      Assert.assertEquals(2, istringList.size());
      Assert.assertEquals("hello", istringList.get(0));
      Assert.assertEquals("world", istringList.get(1));
      co = CBORObject.NewMap();
      co.Add("a", 1);
      co.Add("b", 2);
      HashMap<String, Integer> intDict =
        (HashMap<String, Integer>)co.ToObject(
          (new java.lang.reflect.ParameterizedType() {public java.lang.reflect.Type[] getActualTypeArguments() {return new java.lang.reflect.Type[] { String.class, Integer.class };}public java.lang.reflect.Type getRawType() { return HashMap.class; } public java.lang.reflect.Type getOwnerType() { return null; }}));
      Assert.assertEquals(2, intDict.size());
      if (!(intDict.containsKey("a"))) {
 Assert.fail();
 }
      if (!(intDict.containsKey("b"))) {
 Assert.fail();
 }
      if (intDict.get("a") != 1) {
        Assert.fail();
      }
      if (intDict.get("b") != 2) {
        Assert.fail();
      }
      Map<String, Integer> iintDict = (Map<String, Integer>)co.ToObject(
          (new java.lang.reflect.ParameterizedType() {public java.lang.reflect.Type[] getActualTypeArguments() {return new java.lang.reflect.Type[] { String.class, Integer.class };}public java.lang.reflect.Type getRawType() { return Map.class; } public java.lang.reflect.Type getOwnerType() { return null; }}));
      Assert.assertEquals(2, iintDict.size());
      if (!(iintDict.containsKey("a"))) {
 Assert.fail();
 }
      if (!(iintDict.containsKey("b"))) {
 Assert.fail();
 }
      if (iintDict.get("a") != 1) {
        Assert.fail();
      }
      if (iintDict.get("b") != 2) {
        Assert.fail();
      }
      co = CBORObject.FromObjectAndTag(
        "2000-01-01T00:00:00Z",
        0);
      try {
        co.ToObject(java.util.Date.class);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestShortRoundTrip() {
      for (int i = -32768; i < 32768; ++i) {
        short c = (short)i;
        TestToFromObjectRoundTrip(c);
      }
    }

    @Test
    public void TestCharRoundTrip() {
      for (int i = 0; i < 0x10000; ++i) {
        char c = (char)i;
        TestToFromObjectRoundTrip(c);
      }
    }

    private static String RandomDate(RandomGenerator rand) {
      int year = rand.UniformInt(1, 10000);
      int month = rand.UniformInt(1, 13);
      int day = rand.UniformInt(1, 29);
      int hour = rand.UniformInt(1, 24);
      int min = rand.UniformInt(1, 60);
      int sec = rand.UniformInt(1, 60);
      char[] dt = new char[20];
      dt[0] = (char)(0x30 + ((year / 1000) % 10));
      dt[1] = (char)(0x30 + ((year / 100) % 10));
      dt[2] = (char)(0x30 + ((year / 10) % 10));
      dt[3] = (char)(0x30 + (year % 10));
      dt[4] = '-';
      dt[5] = (char)(0x30 + ((month / 10) % 10));
      dt[6] = (char)(0x30 + (month % 10));
      dt[7] = '-';
      dt[8] = (char)(0x30 + ((day / 10) % 10));
      dt[9] = (char)(0x30 + (day % 10));
      dt[10] = 'T';
      dt[11] = (char)(0x30 + ((hour / 10) % 10));
      dt[12] = (char)(0x30 + (hour % 10));
      dt[13] = ':';
      dt[14] = (char)(0x30 + ((min / 10) % 10));
      dt[15] = (char)(0x30 + (min % 10));
      dt[16] = ':';
      dt[17] = (char)(0x30 + ((sec / 10) % 10));
      dt[18] = (char)(0x30 + (sec % 10));
      dt[19] = 'Z';
      return new String(dt);
    }
    @Test
    public void TestDateRoundTrip() {
      RandomGenerator rand = new RandomGenerator();
      for (int i = 0; i < 5000; ++i) {
        String s = RandomDate(rand);
        CBORObject cbor = CBORObject.FromObjectAndTag(s, 0);
        java.util.Date dtime = (java.util.Date)cbor.ToObject(java.util.Date.class);
        CBORObject cbor2 = CBORObject.FromObject(dtime);
        Assert.assertEquals(s, cbor2.AsString());
        TestToFromObjectRoundTrip(dtime);
      }
    }
    @Test
    public void TestBadDate() {
      CBORObject cbor = CBORObject.FromObjectAndTag(
        "2000-1-01T00:00:00Z",
        0);
      try {
        cbor.ToObject(java.util.Date.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.FromObjectAndTag(
        "2000-01-1T00:00:00Z",
        0);
      try {
        cbor.ToObject(java.util.Date.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.FromObjectAndTag(
        "2000-01-01T0:00:00Z",
        0);
      try {
        cbor.ToObject(java.util.Date.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.FromObjectAndTag(
        "2000-01-01T00:0:00Z",
        0);
      try {
        cbor.ToObject(java.util.Date.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.FromObjectAndTag(
        "2000-01-01T00:00:0Z",
        0);
      try {
        cbor.ToObject(java.util.Date.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      cbor = CBORObject.FromObjectAndTag(
        "T01:01:01Z",
        0);
      try {
        cbor.ToObject(java.util.Date.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestUriRoundTrip() {
      try {
        java.net.URI uri = new java.net.URI("http://example.com/path/path2?query#fragment");
        TestToFromObjectRoundTrip(uri);
      } catch (Exception ex) {
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestUUIDRoundTrip() {
      RandomGenerator rng = new RandomGenerator();
      for (int i = 0; i < 500; ++i) {
        TestToFromObjectRoundTrip(RandomUUID(rng));
      }
    }

    public static Object RandomUUID(RandomGenerator rand) {
      String hex = "0123456789ABCDEF";
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < 8; ++i) {
        sb.append(hex.charAt(rand.UniformInt(16)));
      }
      sb.append('-');
      for (int i = 0; i < 4; ++i) {
        sb.append(hex.charAt(rand.UniformInt(16)));
      }
      sb.append('-');
      for (int i = 0; i < 4; ++i) {
        sb.append(hex.charAt(rand.UniformInt(16)));
      }
      sb.append('-');
      for (int i = 0; i < 4; ++i) {
        sb.append(hex.charAt(rand.UniformInt(16)));
      }
      sb.append('-');
      for (int i = 0; i < 12; ++i) {
        sb.append(hex.charAt(rand.UniformInt(16)));
      }
      return java.util.UUID.fromString(sb.toString());
    }

    public static CBORObject TestToFromObjectRoundTrip(Object obj) {
      CBORObject cbor = CBORObject.FromObject(obj);
      if (obj != null) {
        Object obj2 = null;
        try {
          obj2 = cbor.ToObject(obj.getClass());
        } catch (Exception ex) {
          Assert.fail(ex.toString() + "\n" + cbor);
          throw new IllegalStateException("", ex);
        }
        if (!obj.equals(obj2)) {
          if (obj instanceof byte[]) {
            TestCommon.AssertByteArraysEqual(
              (byte[])obj,
              (byte[])obj2);
          } else if (obj instanceof String[]) {
            Assert.assertEquals((String[])obj, (String[])obj2);
          } else {
            Assert.assertEquals(cbor.toString(), obj, obj2);
          }
        }
      }
      return cbor;
    }
  }
