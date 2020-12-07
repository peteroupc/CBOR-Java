package com.upokecenter.test;

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  public class JavaTests {
    @Test
    public void TestAsBigInteger() {
      if (CBORObject.Null.ToObject(BigInteger.class) != null) {
        Assert.fail();
      }
      try {
        CBORObject.True.ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(BigInteger.class);
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
        String numberString = (String)numberinfo.get("number")
          .ToObject(String.class);
        CBORObject cbornumber = null;
        try {
          cbornumber = ToObjectTest.TestToFromObjectRoundTrip(
            new BigDecimal(numberString));
        } catch(NumberFormatException nfe) {
          EDecimal ed=EDecimal.FromString(numberString);
          if(ed.isFinite()) {
              Assert.fail();
          }
          continue;
        }
        if (!numberinfo.get("integer").equals(CBORObject.Null)) {
          Assert.assertEquals(
            numberinfo.get("integer").ToObject(String.class),
            cbornumber.ToObject(BigInteger.class).toString());
        } else {
          try {
            cbornumber.ToObject(BigInteger.class);
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
          ToObjectTest.TestToFromObjectRoundTrip(0.75f)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(0.99f)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip(0.0000000000000001f)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(0.5f)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(1.5f)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "1",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(2.5f)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "2",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip((float)328323f)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "328323",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip((double)0.75)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip((double)0.99)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
          ToObjectTest.TestToFromObjectRoundTrip((double)0.0000000000000001)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip((double)0.5)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip((double)1.5)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "1",
          stringTemp);
      }
      {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip((double)2.5)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "2",
          stringTemp);
      }
      {
        double dvalue = 328323;
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(dvalue)
          .ToObject(BigInteger.class).toString();
        Assert.assertEquals(
          "328323",
          stringTemp);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
        .ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
        .ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
        .ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
        .ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
        .ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
        .ToObject(BigInteger.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    public static BigDecimal RandomBigDecimal(IRandomGenExtended r) {
      return RandomBigDecimal(r, null);
    }

    public static BigDecimal RandomBigDecimal(IRandomGenExtended r, String[]
      decimalString) {
      if (r == null) {
        throw new NullPointerException("r");
      }
      if (r.GetInt32(100) < 30) {
        String str = RandomObjects.RandomDecimalString(r);
        if (str.length() < 500) {
          if (decimalString != null) {
            decimalString[0] = str;
          }
          return new BigDecimal(str);
        }
      }
      EInteger emant = RandomObjects.RandomEInteger(r);
      int exp = (r.GetInt32(100) < 80) ? (r.GetInt32(50) - 25) :
          (r.GetInt32(5000) - 2500);
      BigDecimal ed = new BigDecimal(new BigInteger(emant.ToBytes(false)), -exp);
      if (decimalString != null) {
        decimalString[0] = emant.toString() + "E" + EInteger.FromInt32(-exp).toString();
      }
      return ed;
    }

    @Test
    public void TestAsBigDecimal() {
      BigDecimal bd=new BigDecimal("334.337");
      CBORObject cborObject=CBORObject.FromObject(bd);
      EDecimal ed=cborObject.ToObject(EDecimal.class);
      Assert.assertEquals("334.337",ed.toString());
      bd=cborObject.ToObject(BigDecimal.class);
      Assert.assertEquals("334.337",bd.toString());
      RandomGenerator rg=new RandomGenerator();
      for(int i=0;i<500;i++){
        bd=RandomBigDecimal(rg,null);
        String str=bd.toString();
        cborObject=CBORObject.FromObject(bd);
        ed=cborObject.ToObject(EDecimal.class);
        Assert.assertEquals(str,ed.toString());
        bd=cborObject.ToObject(BigDecimal.class);
      }
      try {
        Object objectTemp = CBORTestCommon.DecPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.POSITIVE_INFINITY)
          .ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Object objectTemp = CBORTestCommon.DecNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Float.NEGATIVE_INFINITY)
          .ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        String stringTemp = ToObjectTest.TestToFromObjectRoundTrip(Float.NaN)
          .ToObject(BigDecimal.class).toString();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Object objectTemp = CBORTestCommon.DecPosInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY)
          .ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Object objectTemp = CBORTestCommon.DecNegInf;
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NEGATIVE_INFINITY)
          .ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Object objectTemp = "NaN";
        Object objectTemp2 =
          ToObjectTest.TestToFromObjectRoundTrip(Double.NaN)
          .ToObject(BigDecimal.class).toString();
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.True.ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.Undefined.ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip("")
        .ToObject(BigDecimal.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
  }
