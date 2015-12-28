package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;

  public class ExtendedRationalTest {
    @Test
    public void TestConstructor() {
      // not implemented yet
    }
    @Test
    public void TestAbs() {
      // not implemented yet
    }
    @Test
    public void TestAdd() {
      // not implemented yet
    }
    @Test
    public void TestCompareTo() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 500; ++i) {
        ExtendedRational bigintA = RandomObjects.RandomRational(r);
        ExtendedRational bigintB = RandomObjects.RandomRational(r);
        ExtendedRational bigintC = RandomObjects.RandomRational(r);
        TestCommon.CompareTestRelations(bigintA, bigintB, bigintC);
      }
      TestCommon.CompareTestLess(ExtendedRational.Zero, ExtendedRational.NaN);
      for (int i = 0; i < 100; ++i) {
        BigInteger num = RandomObjects.RandomBigInteger(r);
        if (num.signum() == 0) {
          // Skip if number is 0; 0/1 and 0/2 are
          // equal in that case
          continue;
        }
        num = (num).abs();
        ExtendedRational rat = new ExtendedRational(num, BigInteger.valueOf(1));
        ExtendedRational rat2 = new ExtendedRational(num, BigInteger.valueOf(2));
        TestCommon.CompareTestLess(rat2, rat);
        TestCommon.CompareTestGreater(rat, rat2);
      }
      TestCommon.CompareTestLess(
        new ExtendedRational(
          BigInteger.valueOf(1),
          BigInteger.valueOf(2)),
 (
          new ExtendedRational(BigInteger.valueOf(4), BigInteger.valueOf(1))));
      for (int i = 0; i < 100; ++i) {
        BigInteger num = RandomObjects.RandomBigInteger(r);
        BigInteger den = RandomObjects.RandomBigInteger(r);
        if (den.signum() == 0) {
          den = BigInteger.valueOf(1);
        }
        ExtendedRational rat = new ExtendedRational(num, den);
        for (int j = 0; j < 10; ++j) {
          BigInteger num2 = num;
          BigInteger den2 = den;
          BigInteger mult = RandomObjects.RandomBigInteger(r);
          if (mult.signum() == 0 || mult.equals(BigInteger.valueOf(1))) {
            mult = BigInteger.valueOf(2);
          }
          num2 = num2.multiply(mult);
          den2 = den2.multiply(mult);
          ExtendedRational rat2 = new ExtendedRational(num2, den2);
          TestCommon.CompareTestEqual(rat, rat2);
        }
      }
    }
    @Test
    public void TestCompareToBinary() {
      // not implemented yet
    }
    @Test
    public void TestCompareToDecimal() {
      // not implemented yet
    }
    @Test
    public void TestCreate() {
      // not implemented yet
    }
    @Test
    public void TestCreateNaN() {
      // not implemented yet
    }
    @Test
    public void TestDenominator() {
      // not implemented yet
    }
    @Test
    public void TestDivide() {
      FastRandom fr = new FastRandom();
      for (int i = 0; i < 500; ++i) {
        ExtendedRational er = RandomObjects.RandomRational(fr);
        ExtendedRational er2 = RandomObjects.RandomRational(fr);
        if (er2.signum() == 0 || !er2.isFinite()) {
          continue;
        }
        if (er.signum() == 0 || !er.isFinite()) {
          continue;
        }
        ExtendedRational ermult = er.Multiply(er2);
        ExtendedRational erdiv = ermult.Divide(er);
        TestCommon.CompareTestEqual(erdiv, er2);
        erdiv = ermult.Divide(er2);
        TestCommon.CompareTestEqual(erdiv, er);
      }
    }
    @Test
    public void TestEquals() {
      // not implemented yet
    }
    @Test
    public void TestFromBigInteger() {
      // not implemented yet
    }
    @Test
    public void TestFromDouble() {
      // not implemented yet
    }
    @Test
    public void TestFromExtendedDecimal() {
      // not implemented yet
    }
    @Test
    public void TestFromExtendedFloat() {
      // not implemented yet
    }
    @Test
    public void TestFromInt32() {
      // not implemented yet
    }
    @Test
    public void TestFromInt64() {
      // not implemented yet
    }
    @Test
    public void TestFromSingle() {
      // not implemented yet
    }
    @Test
    public void TestGetHashCode() {
      // not implemented yet
    }
    @Test
    public void TestIsFinite() {
      if (ExtendedRational.PositiveInfinity.isFinite())Assert.fail();
      if (ExtendedRational.NegativeInfinity.isFinite())Assert.fail();
      if (!(ExtendedRational.Zero.isFinite()))Assert.fail();
      if (ExtendedRational.NaN.isFinite())Assert.fail();
    }
    @Test
    public void TestIsInfinity() {
      if (!(ExtendedRational.PositiveInfinity.IsInfinity()))Assert.fail();
      if (!(ExtendedRational.NegativeInfinity.IsInfinity()))Assert.fail();
      if (ExtendedRational.Zero.IsInfinity())Assert.fail();
      if (ExtendedRational.NaN.IsInfinity())Assert.fail();
    }
    @Test
    public void TestIsNaN() {
      if (ExtendedRational.PositiveInfinity.IsNaN())Assert.fail();
      if (ExtendedRational.NegativeInfinity.IsNaN())Assert.fail();
      if (ExtendedRational.Zero.IsNaN())Assert.fail();
      if (ExtendedRational.One.IsNaN())Assert.fail();
      if (!(ExtendedRational.NaN.IsNaN()))Assert.fail();
    }
    @Test
    public void TestIsNegative() {
      // not implemented yet
    }
    @Test
    public void TestIsNegativeInfinity() {
      if (ExtendedRational.PositiveInfinity.IsNegativeInfinity())Assert.fail();
      if (!(ExtendedRational.NegativeInfinity.IsNegativeInfinity()))Assert.fail();
      if (ExtendedRational.Zero.IsNegativeInfinity())Assert.fail();
      if (ExtendedRational.One.IsNegativeInfinity())Assert.fail();
      if (ExtendedRational.NaN.IsNegativeInfinity())Assert.fail();
    }
    @Test
    public void TestIsPositiveInfinity() {
      if (!(ExtendedRational.PositiveInfinity.IsPositiveInfinity()))Assert.fail();
      if (ExtendedRational.NegativeInfinity.IsPositiveInfinity())Assert.fail();
      if (ExtendedRational.Zero.IsPositiveInfinity())Assert.fail();
      if (ExtendedRational.One.IsPositiveInfinity())Assert.fail();
      if (ExtendedRational.NaN.IsPositiveInfinity())Assert.fail();
    }
    @Test
    public void TestIsQuietNaN() {
      // not implemented yet
    }
    @Test
    public void TestIsSignalingNaN() {
      // not implemented yet
    }
    @Test
    public void TestIsZero() {
      if (!(ExtendedRational.NegativeZero.signum() == 0))Assert.fail();
      if (!(ExtendedRational.Zero.signum() == 0))Assert.fail();
      if (ExtendedRational.One.signum() == 0)Assert.fail();
      if (ExtendedRational.NegativeInfinity.signum() == 0)Assert.fail();
      if (ExtendedRational.PositiveInfinity.signum() == 0)Assert.fail();
    }
    @Test
    public void TestMultiply() {
      // not implemented yet
    }
    @Test
    public void TestNegate() {
      // not implemented yet
    }
    @Test
    public void TestNumerator() {
      // not implemented yet
    }
    @Test
    public void TestRemainder() {
      FastRandom fr = new FastRandom();
      for (int i = 0; i < 100; ++i) {
        ExtendedRational er;
        ExtendedRational er2;
        er = new ExtendedRational(
          RandomObjects.RandomBigInteger(fr),
          BigInteger.valueOf(1));
        er2 = new ExtendedRational(
          RandomObjects.RandomBigInteger(fr),
          BigInteger.valueOf(1));
        if (er2.signum() == 0 || !er2.isFinite()) {
          continue;
        }
        if (er.signum() == 0 || !er.isFinite()) {
          // Code below will divide by "er",
          // so skip if "er" is zero
          continue;
        }
        ExtendedRational ermult = er.Multiply(er2);
        ExtendedRational erdiv = ermult.Divide(er);
        erdiv = ermult.Remainder(er);
        if (erdiv.signum() != 0) {
          Assert.fail(ermult + "; " + er);
        }
        erdiv = ermult.Remainder(er2);
        if (erdiv.signum() != 0) {
          Assert.fail(er + "; " + er2);
        }
      }
    }
    @Test
    public void TestSign() {
      Assert.assertEquals(0, ExtendedRational.NegativeZero.signum());
      Assert.assertEquals(0, ExtendedRational.Zero.signum());
      Assert.assertEquals(1, ExtendedRational.One.signum());
      Assert.assertEquals(-1, ExtendedRational.NegativeInfinity.signum());
      Assert.assertEquals(1, ExtendedRational.PositiveInfinity.signum());
    }
    @Test
    public void TestSubtract() {
      // not implemented yet
    }
    @Test
    public void TestToBigInteger() {
      // not implemented yet
    }
    @Test
    public void TestToBigIntegerExact() {
      try {
        ExtendedRational.PositiveInfinity.ToBigIntegerExact();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ExtendedRational.NegativeInfinity.ToBigIntegerExact();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ExtendedRational.NaN.ToBigIntegerExact();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ExtendedRational.SignalingNaN.ToBigIntegerExact();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestToDouble() {
      // test for correct rounding
      double dbl;
      dbl = ExtendedRational.FromExtendedDecimal(
        ExtendedDecimal.FromString(
       "1.972579273363468721491642554610734805464744567871093749999999999999"))
        .ToDouble();
      {
String stringTemp = ExtendedFloat.FromDouble(dbl).ToPlainString();
Assert.assertEquals(
"1.9725792733634686104693400920950807631015777587890625",
stringTemp);
}
    }
    @Test
    public void TestToExtendedDecimal() {
      // not implemented yet
    }
    @Test
    public void TestToExtendedDecimalExactIfPossible() {
      // not implemented yet
    }
    @Test
    public void TestToExtendedFloat() {
      // not implemented yet
    }
    @Test
    public void TestToExtendedFloatExactIfPossible() {
      // not implemented yet
    }
    @Test
    public void TestToSingle() {
      // not implemented yet
    }
    @Test
    public void TestToString() {
      // not implemented yet
    }
    @Test
    public void TestUnsignedNumerator() {
      // not implemented yet
    }
  }
