package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;

  public class BigIntegerTest {
    @Test
    public void TestAbs() {
      // not implemented yet
    }
    @Test
    public void TestAdd() {
      // not implemented yet
    }
    @Test
    public void TestAnd() {
      // not implemented yet
    }
    @Test
    public void TestBitLength() {
      Assert.assertEquals(31, BigInteger.valueOf(-2147483647L).bitLength());
      Assert.assertEquals(31, BigInteger.valueOf(-2147483648L).bitLength());
      Assert.assertEquals(32, BigInteger.valueOf(-2147483649L).bitLength());
      Assert.assertEquals(32, BigInteger.valueOf(-2147483650L).bitLength());
      Assert.assertEquals(31, BigInteger.valueOf(2147483647L).bitLength());
      Assert.assertEquals(32, BigInteger.valueOf(2147483648L).bitLength());
      Assert.assertEquals(32, BigInteger.valueOf(2147483649L).bitLength());
      Assert.assertEquals(32, BigInteger.valueOf(2147483650L).bitLength());
      Assert.assertEquals(0, BigInteger.valueOf(0).bitLength());
      Assert.assertEquals(1, BigInteger.valueOf(1).bitLength());
      Assert.assertEquals(2, BigInteger.valueOf(2).bitLength());
      Assert.assertEquals(2, BigInteger.valueOf(2).bitLength());
      Assert.assertEquals(31, BigInteger.valueOf(Integer.MAX_VALUE).bitLength());
      Assert.assertEquals(31, BigInteger.valueOf(Integer.MIN_VALUE).bitLength());
      Assert.assertEquals(16, BigInteger.valueOf(65535).bitLength());
      Assert.assertEquals(16, BigInteger.valueOf(-65535).bitLength());
      Assert.assertEquals(17, BigInteger.valueOf(65536).bitLength());
      Assert.assertEquals(16, BigInteger.valueOf(-65536).bitLength());
      Assert.assertEquals(
65,
BigInteger.fromString("19084941898444092059").bitLength());
      Assert.assertEquals(
65,
BigInteger.fromString("-19084941898444092059").bitLength());
      Assert.assertEquals(0, BigInteger.valueOf(-1).bitLength());
      Assert.assertEquals(1, BigInteger.valueOf(-2).bitLength());
    }
    @Test
    public void TestCanFitInInt() {
      // not implemented yet
    }
    @Test
    public void TestCompareTo() {
      // not implemented yet
    }
    @Test
    public void TestDivide() {
      // not implemented yet
    }
    @Test
    public void TestDivideAndRemainder() {
      try {
        BigInteger.ONE.divideAndRemainder(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestDivRem() {
      // not implemented yet
    }
    @Test
    public void TestEquals() {
      // not implemented yet
    }
    @Test
    public void TestFromByteArray() {
      Assert.assertEquals(
        BigInteger.ZERO, BigInteger.fromByteArray(new byte[] { }, false));
    }
    @Test
    public void TestFromString() {
      try {
        BigInteger.fromString(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestFromSubstring() {
      try {
        BigInteger.fromSubstring(null, 0, 1);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestFromRadixString() {
      try {
        BigInteger.fromRadixString(null, 10);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", -37);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", Integer.MIN_VALUE);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", Integer.MAX_VALUE);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      FastRandom fr = new FastRandom();
      for (int i = 2; i <= 36; ++i) {
        for (int j = 0; j < 100; ++j) {
          StringAndBigInt sabi = StringAndBigInt.Generate(fr, i);
          Assert.assertEquals(
sabi.getBigIntValue(),
BigInteger.fromRadixString(sabi.getStringValue(), i));
        }
      }
    }
    @Test
    public void TestFromRadixSubstring() {
      try {
        BigInteger.fromRadixSubstring(null, 10, 0, 1);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", 1, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", 0, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", -37, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", Integer.MIN_VALUE, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", Integer.MAX_VALUE, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      FastRandom fr = new FastRandom();
      for (int i = 2; i <= 36; ++i) {
        StringBuilder padding = new StringBuilder();
        for (int j = 0; j < 100; ++j) {
          StringAndBigInt sabi = StringAndBigInt.Generate(fr, i);
          padding.append('!');
          BigInteger actualBigInt = BigInteger.fromRadixSubstring(
            padding + sabi.getStringValue(),
            i,
            j + 1,
            j + 1 + sabi.getStringValue().length);
          Assert.assertEquals(
sabi.getBigIntValue(),
actualBigInt);
        }
      }
    }

    public static int ModPow(int x, int pow, int intMod) {
      if (x < 0) {
        throw new IllegalArgumentException(
          "x (" + x + ") is less than " + "0");
      }
      if (pow <= 0) {
        throw new IllegalArgumentException(
          "pow (" + pow + ") is not greater than " + "0");
      }
      if (intMod <= 0) {
        throw new IllegalArgumentException(
          "mod (" + intMod + ") is not greater than " + "0");
      }
      int r = 1;
      int v = x;
      while (pow != 0) {
        if ((pow & 1) != 0) {
          r = (int)(((long)r * (long)v) % intMod);
        }
        pow >>= 1;
        if (pow != 0) {
          v = (int)(((long)v * (long)v) % intMod);
        }
      }
      return r;
    }

    public static boolean IsPrime(int n) {
      if (n < 2) {
        return false;
      }
      if (n == 2) {
        return true;
      }
      if (n % 2 == 0) {
        return false;
      }
      if (n <= 23) {
        return (n == 3 || n == 5 || n == 7 || n == 11 ||
                n == 13 || n == 17 || n == 19 || n == 23);
      }
      // Use a deterministic Rabin-Miller test
      int d = n - 1;
      while ((d & 1) == 0) {
        d >>= 1;
      }
      int mp = 0;
      // For all 32-bit integers it's enough
      // to check the strong pseudoprime
      // bases 2, 7, and 61
      if (n > 2) {
        mp = ModPow(2, d, n);
        if (mp != 1 && mp + 1 != n) {
          return false;
        }
      }
      if (n > 7) {
        mp = ModPow(7, d, n);
        if (mp != 1 && mp + 1 != n) {
          return false;
        }
      }
      if (n > 61) {
        mp = ModPow(61, d, n);
        if (mp != 1 && mp + 1 != n) {
          return false;
        }
      }
      return true;
    }

    private static void TestGcdPair(
BigInteger biga,
BigInteger bigb,
BigInteger biggcd) {
      BigInteger ba = biga.gcd(bigb);
      BigInteger bb = bigb.gcd(biga);
      Assert.assertEquals(ba, biggcd);
      Assert.assertEquals(bb, biggcd);
    }

    @Test
    public void TestGcd() {
      int prime = 0;
      FastRandom rand = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        while (true) {
          prime = rand.NextValue(0x7fffffff);
          prime |= 1;
          if (IsPrime(prime)) {
            break;
          }
        }
        BigInteger bigprime = BigInteger.valueOf(prime);
        BigInteger ba = RandomObjects.RandomBigInteger(rand);
        if (ba.signum() == 0) {
          continue;
        }
        ba = ba.multiply(bigprime);
        Assert.assertEquals(
bigprime,
bigprime.gcd(ba));
      }
      TestGcdPair(BigInteger.valueOf(-1867), BigInteger.valueOf(-4456), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(4604), BigInteger.valueOf(-4516), BigInteger.valueOf(4));
      TestGcdPair(BigInteger.valueOf(-1756), BigInteger.valueOf(4525), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(1568), BigInteger.valueOf(-4955), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(2519), BigInteger.valueOf(2845), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-1470), BigInteger.valueOf(132), BigInteger.valueOf(6));
      TestGcdPair(BigInteger.valueOf(-2982), BigInteger.valueOf(2573), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-244), BigInteger.valueOf(-3929), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-3794), BigInteger.valueOf(-2325), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-2667), BigInteger.valueOf(2123), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-3712), BigInteger.valueOf(-1850), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(2329), BigInteger.valueOf(3874), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(1384), BigInteger.valueOf(-4278), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(213), BigInteger.valueOf(-1217), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(1163), BigInteger.valueOf(2819), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(1921), BigInteger.valueOf(-579), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(3886), BigInteger.valueOf(-13), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-3270), BigInteger.valueOf(-3760), BigInteger.TEN);
      TestGcdPair(BigInteger.valueOf(-3528), BigInteger.valueOf(1822), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(1547), BigInteger.valueOf(-333), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(2402), BigInteger.valueOf(2850), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(4519), BigInteger.valueOf(1296), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(1821), BigInteger.valueOf(2949), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-2634), BigInteger.valueOf(-4353), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-1728), BigInteger.valueOf(199), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-4646), BigInteger.valueOf(-1418), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-35), BigInteger.valueOf(-3578), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-2244), BigInteger.valueOf(-3250), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-3329), BigInteger.valueOf(1039), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-3064), BigInteger.valueOf(-4730), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-1214), BigInteger.valueOf(4130), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-3038), BigInteger.valueOf(-3184), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-209), BigInteger.valueOf(-1617), BigInteger.valueOf(11));
      TestGcdPair(BigInteger.valueOf(-1101), BigInteger.valueOf(-2886), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-3021), BigInteger.valueOf(-4499), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(3108), BigInteger.valueOf(1815), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(1195), BigInteger.valueOf(4618), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-3643), BigInteger.valueOf(2156), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-2067), BigInteger.valueOf(-3780), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(4251), BigInteger.valueOf(1607), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(438), BigInteger.valueOf(741), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-3692), BigInteger.valueOf(-2135), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-1076), BigInteger.valueOf(2149), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-3224), BigInteger.valueOf(-1532), BigInteger.valueOf(4));
      TestGcdPair(BigInteger.valueOf(-3713), BigInteger.valueOf(1721), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(3038), BigInteger.valueOf(-2657), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(4977), BigInteger.valueOf(-110), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-3305), BigInteger.valueOf(-922), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(1902), BigInteger.valueOf(2481), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-4804), BigInteger.valueOf(-1378), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-1446), BigInteger.valueOf(-4226), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-1409), BigInteger.valueOf(3303), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-1626), BigInteger.valueOf(-3193), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(912), BigInteger.valueOf(-421), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(751), BigInteger.valueOf(-1755), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(3135), BigInteger.valueOf(-3581), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-4941), BigInteger.valueOf(-2885), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(4744), BigInteger.valueOf(3240), BigInteger.valueOf(8));
      TestGcdPair(BigInteger.valueOf(3488), BigInteger.valueOf(4792), BigInteger.valueOf(8));
      TestGcdPair(BigInteger.valueOf(3632), BigInteger.valueOf(3670), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-4821), BigInteger.valueOf(-1749), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(4666), BigInteger.valueOf(2013), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(810), BigInteger.valueOf(-3466), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(2199), BigInteger.valueOf(161), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-1137), BigInteger.valueOf(-1620), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-472), BigInteger.valueOf(66), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(3825), BigInteger.valueOf(2804), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-2895), BigInteger.valueOf(1942), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(1576), BigInteger.valueOf(-4209), BigInteger.ONE);
      TestGcdPair(BigInteger.valueOf(-277), BigInteger.valueOf(-4415), BigInteger.ONE);
      for (int i = 0; i < 1000; ++i) {
        prime = rand.NextValue(0x7fffffff);
        if (rand.NextValue(2) == 0) {
          prime = -prime;
        }
        int b = rand.NextValue(0x7fffffff);
        if (rand.NextValue(2) == 0) {
          b = -b;
        }
        BigInteger biga = BigInteger.valueOf(prime);
        BigInteger bigb = BigInteger.valueOf(b);
        BigInteger ba = biga.gcd(bigb);
        BigInteger bb = bigb.gcd(biga);
        Assert.assertEquals(ba, bb);
      }
    }

    @Test
    public void TestGetBits() {
      // not implemented yet
    }
    @Test
    public void TestGetDigitCount() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomObjects.RandomBigInteger(r);
        String str = (bigintA).abs().toString();
        Assert.assertEquals(str.length(), bigintA.getDigitCount());
      }
    }
    @Test
    public void TestGetHashCode() {
      // not implemented yet
    }
    @Test
    public void TestGetLowestSetBit() {
      // not implemented yet
    }
    @Test
    public void TestGetUnsignedBitLength() {
      // not implemented yet
    }
    @Test
    public void TestGreatestCommonDivisor() {
      // not implemented yet
    }
    @Test
    public void TestIntValue() {
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigInteger.valueOf(Integer.MIN_VALUE).intValue());
      Assert.assertEquals(
Integer.MAX_VALUE,
BigInteger.valueOf(Integer.MAX_VALUE).intValue());
      try {
        BigInteger.valueOf(Integer.MIN_VALUE - 1L).intValue();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(Integer.MAX_VALUE + 1L).intValue();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestIntValueChecked() {
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigInteger.valueOf(Integer.MIN_VALUE).intValueChecked());
      Assert.assertEquals(
Integer.MAX_VALUE,
BigInteger.valueOf(Integer.MAX_VALUE).intValueChecked());
      try {
        BigInteger.valueOf(Integer.MIN_VALUE - 1L).intValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(Integer.MAX_VALUE + 1L).intValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestIntValueUnchecked() {
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigInteger.valueOf(Integer.MIN_VALUE).intValueUnchecked());
      Assert.assertEquals(
Integer.MAX_VALUE,
BigInteger.valueOf(Integer.MAX_VALUE).intValueUnchecked());
      Assert.assertEquals(
Integer.MAX_VALUE,
BigInteger.valueOf(Integer.MIN_VALUE - 1L).intValueUnchecked());
      Assert.assertEquals(
Integer.MIN_VALUE,
BigInteger.valueOf(Integer.MAX_VALUE + 1L).intValueUnchecked());
    }
    @Test
    public void TestIsEven() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomObjects.RandomBigInteger(r);
        BigInteger mod = bigintA.remainder(BigInteger.valueOf(2));
        Assert.assertEquals(mod.signum() == 0, bigintA.testBit(0) == false);
      }
    }
    @Test
    public void TestIsPowerOfTwo() {
      // not implemented yet
    }
    @Test
    public void TestIsZero() {
      // not implemented yet
    }
    @Test
    public void TestLongValue() {
      Assert.assertEquals(
        Long.MIN_VALUE,
        BigInteger.valueOf(Long.MIN_VALUE).longValue());
      Assert.assertEquals(
Long.MAX_VALUE,
BigInteger.valueOf(Long.MAX_VALUE).longValue());
      try {
        BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE).longValue();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE).longValue();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(
        ((long)0xFFFFFFF200000000L),
        BigInteger.valueOf(((long)0xFFFFFFF200000000L)).longValue());
      Assert.assertEquals(
((long)0xFFFFFFF280000000L),
BigInteger.valueOf(((long)0xFFFFFFF280000000L)).longValue());
      Assert.assertEquals(
((long)0xFFFFFFF280000001L),
BigInteger.valueOf(((long)0xFFFFFFF280000001L)).longValue());
      Assert.assertEquals(
((long)0xFFFFFFF27FFFFFFFL),
BigInteger.valueOf(((long)0xFFFFFFF27FFFFFFFL)).longValue());
      Assert.assertEquals(
0x0000000380000001L,
BigInteger.valueOf(0x0000000380000001L).longValue());
      Assert.assertEquals(
0x0000000382222222L,
BigInteger.valueOf(0x0000000382222222L).longValue());
      Assert.assertEquals(-8L, BigInteger.valueOf(-8L).longValue());
      Assert.assertEquals(-32768L, BigInteger.valueOf(-32768L).longValue());
      Assert.assertEquals(
Integer.MIN_VALUE,
BigInteger.valueOf(Integer.MIN_VALUE).longValue());
      Assert.assertEquals(
Integer.MAX_VALUE,
BigInteger.valueOf(Integer.MAX_VALUE).longValue());
      Assert.assertEquals(0x80000000L, BigInteger.valueOf(0x80000000L).longValue());
      Assert.assertEquals(0x90000000L, BigInteger.valueOf(0x90000000L).longValue());
    }
    @Test
    public void TestLongValueChecked() {
      Assert.assertEquals(
        Long.MIN_VALUE,
        BigInteger.valueOf(Long.MIN_VALUE).longValueChecked());
      Assert.assertEquals(
Long.MAX_VALUE,
BigInteger.valueOf(Long.MAX_VALUE).longValueChecked());
      try {
        BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE)
          .longValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
   BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE)
          .longValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(
        ((long)0xFFFFFFF200000000L),
        BigInteger.valueOf(((long)0xFFFFFFF200000000L))
        .longValueChecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF280000000L),
        BigInteger.valueOf(((long)0xFFFFFFF280000000L))
        .longValueChecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF280000001L),
        BigInteger.valueOf(((long)0xFFFFFFF280000001L))
        .longValueChecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF27FFFFFFFL),
        BigInteger.valueOf(((long)0xFFFFFFF27FFFFFFFL))
        .longValueChecked());
      Assert.assertEquals(
        0x0000000380000001L,
        BigInteger.valueOf(0x0000000380000001L).longValueChecked());
      Assert.assertEquals(
0x0000000382222222L,
BigInteger.valueOf(0x0000000382222222L).longValueChecked());
      Assert.assertEquals(-8L, BigInteger.valueOf(-8L).longValueChecked());
      Assert.assertEquals(-32768L, BigInteger.valueOf(-32768L).longValueChecked());
      Assert.assertEquals(
Integer.MIN_VALUE,
BigInteger.valueOf(Integer.MIN_VALUE).longValueChecked());
      Assert.assertEquals(
Integer.MAX_VALUE,
BigInteger.valueOf(Integer.MAX_VALUE).longValueChecked());
      Assert.assertEquals(
0x80000000L,
BigInteger.valueOf(0x80000000L).longValueChecked());
      Assert.assertEquals(
0x90000000L,
BigInteger.valueOf(0x90000000L).longValueChecked());
    }
    @Test
    public void TestLongValueUnchecked() {
      Assert.assertEquals(
        Long.MIN_VALUE,
        BigInteger.valueOf(Long.MIN_VALUE).longValueUnchecked());
      Assert.assertEquals(
Long.MAX_VALUE,
BigInteger.valueOf(Long.MAX_VALUE).longValueUnchecked());
      Assert.assertEquals(
Long.MAX_VALUE,
                      BigInteger.valueOf(Long.MIN_VALUE)
                      .subtract(BigInteger.ONE).longValueUnchecked());
      Assert.assertEquals(
Long.MIN_VALUE,
BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE).longValueUnchecked());
      Assert.assertEquals(
((long)0xFFFFFFF200000000L),
                      BigInteger.valueOf(((long)0xFFFFFFF200000000L))
                      .longValueUnchecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF280000000L),
        BigInteger.valueOf(((long)0xFFFFFFF280000000L))
        .longValueUnchecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF280000001L),
        BigInteger.valueOf(((long)0xFFFFFFF280000001L))
        .longValueUnchecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF27FFFFFFFL),
        BigInteger.valueOf(((long)0xFFFFFFF27FFFFFFFL))
        .longValueUnchecked());
      Assert.assertEquals(
        0x0000000380000001L,
        BigInteger.valueOf(0x0000000380000001L).longValueUnchecked());
      Assert.assertEquals(
0x0000000382222222L,
BigInteger.valueOf(0x0000000382222222L).longValueUnchecked());
      Assert.assertEquals(-8L, BigInteger.valueOf(-8L).longValueUnchecked());
      Assert.assertEquals(
-32768L,
BigInteger.valueOf(-32768L).longValueUnchecked());
      Assert.assertEquals(
Integer.MIN_VALUE,
BigInteger.valueOf(Integer.MIN_VALUE).longValueUnchecked());
      Assert.assertEquals(
Integer.MAX_VALUE,
BigInteger.valueOf(Integer.MAX_VALUE).longValueUnchecked());
      Assert.assertEquals(
0x80000000L,
BigInteger.valueOf(0x80000000L).longValueUnchecked());
      Assert.assertEquals(
0x90000000L,
BigInteger.valueOf(0x90000000L).longValueUnchecked());
    }
    @Test
    public void TestMod() {
      try {
        BigInteger.ONE.mod(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestModPow() {
      // not implemented yet
    }
    @Test
    public void TestMultiply() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomObjects.RandomBigInteger(r);
        BigInteger bigintB = bigintA .add(BigInteger.ONE);
        BigInteger bigintC = bigintA.multiply(bigintB);
        // Test near-squaring
        if (bigintA.signum() == 0 || bigintB.signum() == 0) {
          Assert.assertEquals(BigInteger.ZERO, bigintC);
        }
        if (bigintA.equals(BigInteger.ONE)) {
          Assert.assertEquals(bigintB, bigintC);
        }
        if (bigintB.equals(BigInteger.ONE)) {
          Assert.assertEquals(bigintA, bigintC);
        }
        bigintB = bigintA;
        // Test squaring
        bigintC = bigintA.multiply(bigintB);
        if (bigintA.signum() == 0 || bigintB.signum() == 0) {
          Assert.assertEquals(BigInteger.ZERO, bigintC);
        }
        if (bigintA.equals(BigInteger.ONE)) {
          Assert.assertEquals(bigintB, bigintC);
        }
        if (bigintB.equals(BigInteger.ONE)) {
          Assert.assertEquals(bigintA, bigintC);
        }
      }
    }
    @Test
    public void TestNegate() {
      // not implemented yet
    }
    @Test
    public void TestNot() {
      // not implemented yet
    }
    @Test
    public void TestOne() {
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
    public void TestOperatorExplicit() {
      // not implemented yet
    }
    @Test
    public void TestOperatorGreaterThan() {
      // not implemented yet
    }
    @Test
    public void TestOperatorGreaterThanOrEqual() {
      // not implemented yet
    }
    @Test
    public void TestOperatorImplicit() {
      // not implemented yet
    }
    @Test
    public void TestOperatorLeftShift() {
      // not implemented yet
    }
    @Test
    public void TestOperatorLessThan() {
      // not implemented yet
    }
    @Test
    public void TestOperatorLessThanOrEqual() {
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
    public void TestOperatorRightShift() {
      // not implemented yet
    }
    @Test
    public void TestOperatorSubtraction() {
      // not implemented yet
    }
    @Test
    public void TestOperatorUnaryNegation() {
      // not implemented yet
    }
    @Test
    public void TestOr() {
      // not implemented yet
    }
    @Test
    public void TestPow() {
      // not implemented yet
    }
    @Test
    public void TestPowBigIntVar() {
      // not implemented yet
    }
    @Test
    public void TestRemainder() {
      // not implemented yet
    }
    @Test
    public void TestShiftLeft() {
      BigInteger bigint = BigInteger.ONE;
      bigint = bigint.shiftLeft(100);
      Assert.assertEquals(bigint.shiftLeft(12), bigint.shiftRight(-12));
      Assert.assertEquals(bigint.shiftLeft(-12), bigint.shiftRight(12));
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomObjects.RandomBigInteger(r);
        BigInteger bigintB = bigintA;
        for (int j = 0; j < 100; ++j) {
          BigInteger ba = bigintA;
          ba = ba.shiftLeft(j);
          Assert.assertEquals(bigintB, ba);
          int negj = -j;
          ba = bigintA;
          ba = ba.shiftRight(negj);
          Assert.assertEquals(bigintB, ba);
          bigintB = bigintB.multiply(BigInteger.valueOf(2));
        }
      }
    }
    @Test
    public void TestShiftRight() {
      BigInteger bigint = BigInteger.ONE;
      bigint = bigint.shiftLeft(80);
      Assert.assertEquals(bigint.shiftLeft(12), bigint.shiftRight(-12));
      Assert.assertEquals(bigint.shiftLeft(-12), bigint.shiftRight(12));
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        int smallint = r.NextValue(0x7fffffff);
        BigInteger bigintA = BigInteger.valueOf(smallint);
        String str = bigintA.toString();
        for (int j = 32; j < 80; ++j) {
          TestCommon.DoTestShiftRight(str, j, "0");
          TestCommon.DoTestShiftRight("-" + str, j, "-1");
        }
      }
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomObjects.RandomBigInteger(r);
        bigintA = (bigintA).abs();
        BigInteger bigintB = bigintA;
        for (int j = 0; j < 100; ++j) {
          BigInteger ba = bigintA;
          ba = ba.shiftRight(j);
          Assert.assertEquals(bigintB, ba);
          int negj = -j;
          ba = bigintA;
          ba = ba.shiftLeft(negj);
          Assert.assertEquals(bigintB, ba);
          bigintB = bigintB.divide(BigInteger.valueOf(2));
        }
      }
    }
    @Test
    public void TestSign() {
      // not implemented yet
    }
    @Test
    public void TestSqrt() {
      // not implemented yet
    }
    @Test
    public void TestSqrtWithRemainder() {
      // not implemented yet
    }
    @Test
    public void TestSubtract() {
      // not implemented yet
    }
    @Test
    public void TestTestBit() {
      if (BigInteger.ZERO.testBit(0))Assert.fail();
      if (BigInteger.ZERO.testBit(1))Assert.fail();
      if (!(BigInteger.ONE.testBit(0)))Assert.fail();
      if (BigInteger.ONE.testBit(1))Assert.fail();
      for (int i = 0; i < 32; ++i) {
        if (!(BigInteger.ONE.negate().testBit(i)))Assert.fail();
      }
    }
    @Test
    public void TestToByteArray() {
      // not implemented yet
    }

    private static String ToUpperCaseAscii(String str) {
      if (str == null) {
        return null;
      }
      int len = str.length();
      char c = (char)0;
      boolean hasLowerCase = false;
      for (int i = 0; i < len; ++i) {
        c = str.charAt(i);
        if (c >= 'a' && c <= 'z') {
          hasLowerCase = true;
          break;
        }
      }
      if (!hasLowerCase) {
        return str;
      }
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < len; ++i) {
        c = str.charAt(i);
        if (c >= 'a' && c <= 'z') {
          builder.append((char)(c - 0x20));
        } else {
          builder.append(c);
        }
      }
      return builder.toString();
    }

    @Test
    public void TestToRadixString() {
      FastRandom fr = new FastRandom();
      try {
        BigInteger.ONE.toRadixString(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.ONE.toRadixString(0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.ONE.toRadixString(1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.ONE.toRadixString(37);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.ONE.toRadixString(Integer.MIN_VALUE);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.ONE.toRadixString(Integer.MAX_VALUE);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      for (int i = 2; i <= 36; ++i) {
        for (int j = 0; j < 100; ++j) {
          StringAndBigInt sabi = StringAndBigInt.Generate(fr, i);
          // Upper case result expected
          String expected = ToUpperCaseAscii(sabi.getStringValue());
          int k = 0;
          // Expects result with no unnecessary leading zeros
          boolean negative = sabi.getBigIntValue().signum() < 0;
          if (expected.charAt(0) == '-') {
            ++k;
          }
          while (k < expected.length() - 1) {
            if (expected.charAt(k) == '0') {
              ++k;
            } else {
              break;
            }
          }
          expected = expected.substring(k);
          if (negative) {
            expected = "-" + expected;
          }
          Assert.assertEquals(
expected,
sabi.getBigIntValue().toRadixString(i));
        }
      }
    }

    @Test
    public void TestToString() {
      // not implemented yet
    }
    @Test
    public void TestValueOf() {
      // not implemented yet
    }
    @Test
    public void TestXor() {
      // not implemented yet
    }
    @Test
    public void TestZero() {
      // not implemented yet
    }
  }
