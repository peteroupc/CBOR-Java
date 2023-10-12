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
      if (!(CBORObject.FromObject(0).AsNumber().CanFitInInt64())) {
 Assert.fail();
 }
      if (!(CBORObject.FromObject(99).AsNumber().CanFitInInt64())) {
 Assert.fail();
 }
      if (CBORObject.PositiveInfinity.AsNumber().CanFitInInt64()) {
 Assert.fail();
 }
      if (CBORObject.NegativeInfinity.AsNumber().CanFitInInt64()) {
 Assert.fail();
 }
      if (CBORObject.NaN.AsNumber().CanFitInInt64()) {
 Assert.fail();
 }
    }

    @Test
    public void TestCanFitInUInt64() {
      if (!(CBORObject.FromObject(0).AsNumber().CanFitInUInt64())) {
 Assert.fail("0");
 }
      if (!(
        CBORObject.FromObject(99).AsNumber().CanFitInUInt64())) {
 Assert.fail(
        "99");
 }

      if (!(CBORObject.FromObject(99.0).AsNumber().CanFitInUInt64())) {
 Assert.fail(
        "99.0");
 }

      if (!(
        CBORObject.FromObject(1.0).AsNumber().CanFitInUInt64())) {
 Assert.fail(
        "99.0");
 }

      if (!(CBORObject.FromObject(-0.0).AsNumber().CanFitInUInt64())) {
 Assert.fail(
        "-0.0");
 }
      boolean
      b = CBORObject.FromObject(
          EInteger.FromInt32(1).ShiftLeft(65)).AsNumber().CanFitInUInt64();
      if (b) {
 Assert.fail();
 }

      if (
        CBORObject.FromObject(-99).AsNumber().CanFitInUInt64()) {
 Assert.fail(
        "-99");
 }

      if (CBORObject.FromObject(-99.0).AsNumber().CanFitInUInt64()) {
 Assert.fail(
        "-99.0");
 }

      if (
        CBORObject.FromObject(0.1).AsNumber().CanFitInUInt64()) {
 Assert.fail(
        "0.1");
 }
      if (CBORObject.FromObject(-0.1).AsNumber().CanFitInUInt64()) {
 Assert.fail();
 }
      if (CBORObject.FromObject(99.1).AsNumber().CanFitInUInt64()) {
 Assert.fail();
 }
      if (CBORObject.FromObject(-99.1).AsNumber().CanFitInUInt64()) {
 Assert.fail();
 }
      if (CBORObject.PositiveInfinity.AsNumber().CanFitInUInt64()) {
 Assert.fail();
 }
      if (CBORObject.NegativeInfinity.AsNumber().CanFitInUInt64()) {
 Assert.fail();
 }
      if (CBORObject.NaN.AsNumber().CanFitInUInt64()) {
 Assert.fail("NaN");
 }
    }

    @Test
    public void TestCanTruncatedIntFitInUInt64() {
      if (!(
        CBORObject.FromObject(0).AsNumber().CanTruncatedIntFitInUInt64())) {
 Assert.fail(
        "0");
 }

      if (!(
        CBORObject.FromObject(99).AsNumber().CanTruncatedIntFitInUInt64())) {
 Assert.fail(
        "99");
 }

      if (!(
        CBORObject.FromObject(99.0).AsNumber().CanTruncatedIntFitInUInt64())) {
 Assert.fail(
        "99.0");
 }
      if (!(CBORObject.FromObject(
          -0.0).AsNumber().CanTruncatedIntFitInUInt64())) {
 Assert.fail();
 }

      if (
        CBORObject.FromObject(-99).AsNumber().CanTruncatedIntFitInUInt64()) {
 Assert.fail();
 }
      boolean
      b = CBORObject.FromObject(EInteger.FromInt32(1).ShiftLeft(65)).AsNumber()
        .CanTruncatedIntFitInUInt64();
      if (b) {
 Assert.fail();
 }

      if (
        CBORObject.FromObject(
          -99.0).AsNumber().CanTruncatedIntFitInUInt64()) {
 Assert.fail();
 }

      if (!(
        CBORObject.FromObject(0.1).AsNumber().CanTruncatedIntFitInUInt64())) {
 Assert.fail();
 }
      if (!(CBORObject.FromObject(
          -0.1).AsNumber().CanTruncatedIntFitInUInt64())) {
 Assert.fail();
 }
      if (!(CBORObject.FromObject(
          99.1).AsNumber().CanTruncatedIntFitInUInt64())) {
 Assert.fail();
 }

      if (
        CBORObject.PositiveInfinity.AsNumber()
        .CanTruncatedIntFitInUInt64()) {
 Assert.fail();
 }

      if (
        CBORObject.NegativeInfinity.AsNumber()
        .CanTruncatedIntFitInUInt64()) {
 Assert.fail();
 }
      if (CBORObject.NaN.AsNumber().CanTruncatedIntFitInUInt64()) {
 Assert.fail();
 }
    }

    @Test
    public void TestIsInfinity() {
      if (CBORObject.FromObject(0).AsNumber().IsInfinity()) {
 Assert.fail();
 }
      if (CBORObject.FromObject(99).AsNumber().IsInfinity()) {
 Assert.fail();
 }
      if (!(CBORObject.PositiveInfinity.AsNumber().IsInfinity())) {
 Assert.fail();
 }
      if (!(CBORObject.NegativeInfinity.AsNumber().IsInfinity())) {
 Assert.fail();
 }
      if (CBORObject.NaN.AsNumber().IsInfinity()) {
 Assert.fail();
 }
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
      if (!(ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
        .AsNumber().IsNaN())) {
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
        EDecimal cmpDecFrac, cmpCobj;
        cmpDecFrac = AsED(o1).Multiply(AsED(o2));
        cmpCobj = ToCN(o1).Multiply(ToCN(o2)).ToEDecimal();
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
          null).AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Null.AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsNumber().ToEInteger();
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
          ToObjectTest.TestToFromObjectRoundTrip(EDecimal.FromString(
              numberString));
        if (!numberinfo.get("integer").equals(CBORObject.Null)) {
          Assert.assertEquals(
            numberinfo.get("integer").AsString(),
            cbornumber.AsNumber().ToEInteger().toString());
        } else {
          try {
            cbornumber.AsNumber().ToEInteger();
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
          ToObjectTest.TestToFromObjectRoundTrip(0.75f).AsNumber().ToEInteger()
          .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(0.99f).AsNumber().ToEInteger()
          .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(0.0000000000000001f)
          .AsNumber().ToEInteger().toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(0.5f).AsNumber().ToEInteger()
          .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(1.5f).AsNumber().ToEInteger()
          .toString();
        Assert.assertEquals(
          "1",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(2.5f).AsNumber().ToEInteger()
          .toString();
        Assert.assertEquals(
          "2",
          stringTemp);
      }
      {
        String stringTemp =

          ToObjectTest.TestToFromObjectRoundTrip(
            328323f).AsNumber().ToEInteger().toString();
        Assert.assertEquals(
          "328323",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(
            0.75).AsNumber().ToEInteger()
          .toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(
            0.99).AsNumber().ToEInteger().toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(0.0000000000000001)
          .AsNumber().ToEInteger().toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(
            0.5).AsNumber().ToEInteger().toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(
            1.5).AsNumber().ToEInteger().toString();
        Assert.assertEquals(
          "1",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(
            2.5).AsNumber().ToEInteger().toString();
        Assert.assertEquals(
          "2",
          stringTemp);
      }
      {
        double dbl = 328323;
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(dbl)
          .AsNumber().ToEInteger().toString();
        Assert.assertEquals(
          "328323",
          stringTemp);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
        .AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
        .AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          Float.NaN).AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
        .AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
        .AsNumber().ToEInteger();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          Double.NaN).AsNumber().ToEInteger();
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
          .AsNumber().ToEDecimal();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.DecNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
          .AsNumber().ToEDecimal();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.DecPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
          .AsNumber().ToEDecimal();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.DecNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
          .AsNumber().ToEDecimal();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        boolean bo = ToObjectTest.TestToFromObjectRoundTrip(
            Double.NaN).AsNumber().ToEDecimal().IsNaN();
        if (!(bo)) {
 Assert.fail();
 }
      }
      {
        boolean bo =
          ToObjectTest.TestToFromObjectRoundTrip(
            Float.NaN).AsNumber().ToEDecimal().IsNaN();
        if (!(bo)) {
 Assert.fail();
 }
      }
      try {
        CBORObject.NewArray().AsNumber().ToEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().AsNumber().ToEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.AsNumber().ToEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.AsNumber().ToEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.AsNumber().ToEDecimal();
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(
          "").AsNumber().ToEDecimal();
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
          .AsNumber().ToEFloat();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.FloatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
          .AsNumber().ToEFloat();
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
          .AsNumber().ToEFloat();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.FloatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
          .AsNumber().ToEFloat();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestAsERational() {
      {
        Object objectTemp = CBORTestCommon.RatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
          .AsNumber().ToERational();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.RatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
          .AsNumber().ToERational();
        Assert.assertEquals(objectTemp, objectTemp2);
      }

      if (!(
        ToObjectTest.TestToFromObjectRoundTrip(
          ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
          .AsNumber().ToERational()).AsNumber().IsNaN()))Assert.fail();
      {
        Object objectTemp = CBORTestCommon.RatPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
          .AsNumber().ToERational();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = CBORTestCommon.RatNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
          .AsNumber().ToERational();
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      if (!(
        ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
        .AsNumber().ToERational().IsNaN())) {
 Assert.fail();
 }
    }
  }
