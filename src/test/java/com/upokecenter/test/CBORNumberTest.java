package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

public class CBORNumberTest {
private static CBORNumber ToCN(Object o) {
  return ToObjectTest.TestToFromObjectRoundTrip(o).AsNumber();
}

@Test
public void TestAbs() {
      TestCommon.CompareTestEqual(
        ToCN(2),
        ToCN(-2).Abs());
      TestCommon.CompareTestEqual(
        ToCN(2),
        ToCN(2).Abs());
      TestCommon.CompareTestEqual(
        ToCN(2.5),
        ToCN(-2.5).Abs());
      {
        CBORNumber objectTemp = ToCN(EDecimal.FromString("6.63"));
        CBORNumber objectTemp2 = ToCN(EDecimal.FromString(
          "-6.63")).Abs();
        TestCommon.CompareTestEqual(objectTemp, objectTemp2);
      }
      {
        CBORNumber objectTemp = ToCN(EFloat.FromString("2.75"));
        CBORNumber objectTemp2 = ToCN(EFloat.FromString("-2.75")).Abs();
        TestCommon.CompareTestEqual(objectTemp, objectTemp2);
      }
      {
        CBORNumber objectTemp = ToCN(ERational.FromDouble(2.5));
        CBORNumber objectTemp2 = ToCN(ERational.FromDouble(-2.5)).Abs();
        TestCommon.CompareTestEqual(objectTemp, objectTemp2);
      }
    }
@Test
public void TestToCBORObject() {
// not implemented yet
}
@Test
public void TestFromCBORObject() {
// not implemented yet
}
@Test
public void TestToString() {
// not implemented yet
}
@Test
public void TestCanFitInInt32() {
// not implemented yet
}
@Test
public void TestCanFitInInt64() {
// not implemented yet
}
@Test
public void TestIsInfinity() {
// not implemented yet
}
@Test
public void TestIsNaN() {
      if (CBORObject.FromObject(0).AsNumber().IsNaN()) {
 Assert.fail();
 }
      if (CBORObject.FromObject(99).AsNumber().IsNaN()) {
 Assert.fail();
 }
      if (CBORObject.PositiveInfinity.AsNumber().IsNaN()) {
 Assert.fail();
 }
      if (CBORObject.NegativeInfinity.AsNumber().IsNaN()) {
 Assert.fail();
 }
      if (!(CBORObject.NaN.AsNumber().IsNaN())) {
 Assert.fail();
 }
}
@Test
public void TestNegate() {
// not implemented yet
}
@Test
public void TestAdd() {
// not implemented yet
}
@Test
public void TestSubtract() {
// not implemented yet
}

private static EDecimal AsED(CBORObject obj) {
      return (EDecimal)obj.ToObject(EDecimal.class);
    }
@Test
public void TestMultiply() {
      try {
        ToCN(2).Multiply(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      RandomGenerator r = new RandomGenerator();
      for (int i = 0; i < 3000; ++i) {
        CBORObject o1 = CBORTestCommon.RandomNumber(r);
        CBORObject o2 = CBORTestCommon.RandomNumber(r);
        EDecimal cmpDecFrac = AsED(o1).Multiply(AsED(o2));
        EDecimal cmpCobj = ToCN(o1).Multiply(ToCN(o2)).AsEDecimal();
        if (!cmpDecFrac.equals(cmpCobj)) {
          TestCommon.CompareTestEqual(
            cmpDecFrac,
            cmpCobj,
            o1.toString() + "\n" + o2.toString());
        }
        CBORTestCommon.AssertRoundTrip(o1);
        CBORTestCommon.AssertRoundTrip(o2);
      }
}
@Test
public void TestDivide() {
      try {
        ToCN(2).Divide(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
}
@Test
public void TestRemainder() {
      try {
        ToCN(2).Remainder(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
}
@Test
public void TestCompareTo() {
// not implemented yet
}
@Test
public void TestLessThan() {
// not implemented yet
}
@Test
public void TestLessThanOrEqual() {
// not implemented yet
}
@Test
public void TestGreaterThan() {
// not implemented yet
}
@Test
public void TestGreaterThanOrEqual() {
// not implemented yet
}
@Test
public void TestGetType() {
// not implemented yet
}

@Test
public void TestAsEInteger() {
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          (Object)null).AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Null.AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsNumber().AsEInteger();
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
        String numberString = numberinfo.get("number").AsString();
        CBORObject cbornumber =
  ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(numberString));
        if (!numberinfo.get("integer").equals(CBORObject.Null)) {
          Assert.assertEquals(
            numberinfo.get("integer").AsString(),
            cbornumber.AsNumber().AsEInteger().toString());
        } else {
          try {
            cbornumber.AsNumber().AsEInteger();
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
ToObjectTest.TestToFromObjectRoundTrip(0.75f).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip(0.99f).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip(0.0000000000000001f)
            .AsNumber().AsEInteger().toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip(0.5f).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip(1.5f).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "1",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip(2.5f).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "2",
          stringTemp);
      }
      {
        String stringTemp =

ToObjectTest.TestToFromObjectRoundTrip(
  (float)328323f).AsNumber().AsEInteger().toString();
        Assert.assertEquals(
          "328323",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)0.75).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)0.99).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)0.0000000000000001)
            .AsNumber().AsEInteger().toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)0.5).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)1.5).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "1",
          stringTemp);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip((double)2.5).AsNumber().AsEInteger()
            .toString();
        Assert.assertEquals(
          "2",
          stringTemp);
      }
      {
        double dbl = 328323;
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(dbl)
            .AsNumber().AsEInteger().toString();
        Assert.assertEquals(
          "328323",
          stringTemp);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
                  .AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
                  .AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          Float.NaN).AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
                  .AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
                  .AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          Double.NaN).AsNumber().AsEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

@Test
public void TestAsEDecimal() {
      {
        Object objectTemp = CBORTestCommon.DecPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
                  .AsNumber().AsEDecimal();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.DecNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
                  .AsNumber().AsEDecimal();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        String stringTemp =
ToObjectTest.TestToFromObjectRoundTrip(Float.NaN).AsNumber().AsEDecimal()
            .toString();
        Assert.assertEquals(
          "NaN",
          stringTemp);
      }
      {
        Object objectTemp = CBORTestCommon.DecPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
                  .AsNumber().AsEDecimal();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.DecNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
                  .AsNumber().AsEDecimal();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = "NaN";
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(
            Double.NaN).AsNumber().AsEDecimal()
                    .toString();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      try {
        CBORObject.NewArray().AsNumber().AsEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsNumber().AsEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsNumber().AsEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsNumber().AsEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsNumber().AsEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          "").AsNumber().AsEDecimal();
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
                  .AsNumber().AsEFloat();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.FloatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
                  .AsNumber().AsEFloat();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
        .AsNumber().IsNaN())) {
 Assert.fail();
 }
      {
        Object objectTemp = CBORTestCommon.FloatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
                  .AsNumber().AsEFloat();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.FloatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
                  .AsNumber().AsEFloat();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      if (!(ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
        .AsNumber().IsNaN())) {
 Assert.fail();
 }
    }
    @Test
    public void TestAsERational() {
      {
        Object objectTemp = CBORTestCommon.RatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
                  .AsNumber().AsERational();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.RatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
                  .AsNumber().AsERational();
        Assert.assertEquals(objectTemp, objectTemp2);
      }

      if (!(
        ToObjectTest.TestToFromObjectRoundTrip(
        ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
                        .AsNumber().AsERational()).AsNumber().IsNaN()))Assert.fail();
      {
        Object objectTemp = CBORTestCommon.RatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
                  .AsNumber().AsERational();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.RatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
                  .AsNumber().AsERational();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      if (!(
      ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
          .AsNumber().AsERational().IsNaN())) {
 Assert.fail();
 }
    }
}
