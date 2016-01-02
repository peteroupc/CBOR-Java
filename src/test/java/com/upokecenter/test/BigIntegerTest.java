package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;

  public class BigIntegerTest {
    static BigInteger BigValueOf(long value) {
      return BigInteger.valueOf(value);
    }

    static BigInteger BigFromString(String str) {
      return BigInteger.fromString(str);
    }

    static BigInteger BigFromBytes(byte[] bytes) {
      return BigInteger.fromBytes(bytes, true);
    }

    public static BigInteger RandomBigInteger(FastRandom r) {
      int selection = r.NextValue(100);
      int count = r.NextValue(60) + 1;
      if (selection < 40) {
        count = r.NextValue(7) + 1;
      }
      if (selection < 50) {
        count = r.NextValue(15) + 1;
      }
      byte[] bytes = new byte[count];
        for (int i = 0; i < count; ++i) {
          bytes[i] = (byte)((int)r.NextValue(256));
        }
      return BigFromBytes(bytes);
    }

    public static void DoTestDivide(
String dividend,
String divisor,
String result) {
      BigInteger bigintA = BigFromString(dividend);
      BigInteger bigintB = BigFromString(divisor);
      BigInteger bigintTemp;
      if (bigintB.signum() == 0) {
        try {
          bigintTemp = bigintA.divide(bigintB);
          Assert.fail("Expected divide by 0 error");
        } catch (ArithmeticException ex) {
          System.out.println(ex.getMessage());
        }
        try {
          bigintA.divideAndRemainder(bigintB);
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      } else {
        AssertBigIntegersEqual(result, bigintA.divide(bigintB));
        AssertBigIntegersEqual(result, bigintA.divideAndRemainder(bigintB)[0]);
      }
    }

    public static void DoTestMultiply(String m1, String m2, String result) {
      BigInteger bigintA = BigFromString(m1);
      BigInteger bigintB = BigFromString(m2);
      bigintA = bigintA.multiply(bigintB);
      AssertBigIntegersEqual(result, bigintA);
    }

    public static void DoTestPow(String m1, int m2, String result) {
      BigInteger bigintA = BigFromString(m1);
      AssertBigIntegersEqual(result, bigintA.pow(m2));

      AssertBigIntegersEqual(result, bigintA.PowBigIntVar(BigInteger.valueOf(m2)));
    }

    public static void AssertBigIntegersEqual(String a, BigInteger b) {
      Assert.assertEquals(a, b.toString());
      BigInteger a2 = BigFromString(a);
      TestCommon.CompareTestEqualAndConsistent(a2, b);
      TestCommon.AssertEqualsHashCode(a2, b);
    }

    public static void DoTestDivideAndRemainder(
String dividend,
String divisor,
String result,
String rem) {
      BigInteger bigintA = BigFromString(dividend);
      BigInteger bigintB = BigFromString(divisor);
      BigInteger rembi;
      if (bigintB.signum() == 0) {
        try {
          BigInteger quo;
{
BigInteger[] divrem=(bigintA).divideAndRemainder(bigintB);
quo = divrem[0];
rembi = divrem[1]; }
          if (((Object)quo) == null) {
            Assert.fail();
          }
          Assert.fail("Expected divide by 0 error");
        } catch (ArithmeticException ex) {
          System.out.println(ex.getMessage());
        }
      } else {
        BigInteger quo;
{
BigInteger[] divrem=(bigintA).divideAndRemainder(bigintB);
quo = divrem[0];
rembi = divrem[1]; }
        AssertBigIntegersEqual(result, quo);
        AssertBigIntegersEqual(rem, rembi);
      }
    }

    @Test
    public void TestMultiplyDivide() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 10000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        BigInteger bigintB = RandomBigInteger(r);
        // Test that A*B/A = B and A*B/B = A
        BigInteger bigintC = bigintA.multiply(bigintB);
        BigInteger bigintRem;
        BigInteger bigintE;
        BigInteger bigintD;
        if (bigintB.signum() != 0) {
          {
BigInteger[] divrem=(bigintC).divideAndRemainder(bigintB);
bigintD = divrem[0];
bigintRem = divrem[1]; }
          if (!bigintD.equals(bigintA)) {
            Assert.assertEquals("TestMultiplyDivide " + bigintA + "; " + bigintB + ";\n" + bigintC,bigintA,bigintD);
          }
          if (bigintRem.signum() != 0) {
            Assert.assertEquals("TestMultiplyDivide " + bigintA + "; " + bigintB,BigInteger.valueOf(0),bigintRem);
          }
          bigintE = bigintC.divide(bigintB);
          if (!bigintD.equals(bigintE)) {
            // Testing that divideWithRemainder and division method return
            // the same value
            Assert.assertEquals("TestMultiplyDivide " + bigintA + "; " + bigintB + ";\n" + bigintC,bigintD,bigintE);
          }
          bigintE = bigintC.remainder(bigintB);
          if (!bigintRem.equals(bigintE)) {
            Assert.assertEquals("TestMultiplyDivide " + bigintA + "; " + bigintB + ";\n" + bigintC,bigintRem,bigintE);
          }
          if (bigintE.signum() > 0 && !bigintC.mod(bigintB).equals(bigintE)) {
            Assert.fail("TestMultiplyDivide " + bigintA + "; " + bigintB +
              ";\n" + bigintC);
          }
        }
        if (bigintA.signum() != 0) {
          {
BigInteger[] divrem=(bigintC).divideAndRemainder(bigintA);
bigintD = divrem[0];
bigintRem = divrem[1]; }
          if (!bigintD.equals(bigintB)) {
            Assert.assertEquals("TestMultiplyDivide " + bigintA + "; " + bigintB,bigintB,bigintD);
          }
          if (bigintRem.signum() != 0) {
            Assert.assertEquals("TestMultiplyDivide " + bigintA + "; " + bigintB,BigInteger.valueOf(0),bigintRem);
          }
        }
        if (bigintB.signum() != 0) {
          {
BigInteger[] divrem=(bigintA).divideAndRemainder(bigintB);
bigintC = divrem[0];
bigintRem = divrem[1]; }
          bigintD = bigintB.multiply(bigintC);
          bigintD = bigintD.add(bigintRem);
          if (!bigintD.equals(bigintA)) {
            Assert.assertEquals("TestMultiplyDivide " + bigintA + "; " + bigintB,bigintA,bigintD);
          }
        }
      }
    }

    @Test
    public void TestAddSubtract() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 10000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        BigInteger bigintB = RandomBigInteger(r);
        BigInteger bigintC = bigintA.add(bigintB);
        BigInteger bigintD = bigintC.subtract(bigintB);
        if (!bigintD.equals(bigintA)) {
          Assert.assertEquals("TestAddSubtract " + bigintA + "; " + bigintB,bigintA,bigintD);
        }
        bigintD = bigintC.subtract(bigintA);
        if (!bigintD.equals(bigintB)) {
          Assert.assertEquals("TestAddSubtract " + bigintA + "; " + bigintB,bigintB,bigintD);
        }
        bigintC = bigintA.subtract(bigintB);
        bigintD = bigintC.add(bigintB);
        if (!bigintD.equals(bigintA)) {
          Assert.assertEquals("TestAddSubtract " + bigintA + "; " + bigintB,bigintA,bigintD);
        }
      }
    }

    public static void DoTestRemainder(
String dividend,
String divisor,
String result) {
      BigInteger bigintA = BigFromString(dividend);
      BigInteger bigintB = BigFromString(divisor);
      if (bigintB.signum() == 0) {
        try {
          bigintA.remainder(bigintB); Assert.fail("Expected divide by 0 error");
        } catch (ArithmeticException ex) {
          System.out.println(ex.getMessage());
        }
        try {
          bigintA.divideAndRemainder(bigintB);
          Assert.fail("Should have failed");
        } catch (ArithmeticException ex) {
          System.out.print("");
        } catch (Exception ex) {
          Assert.fail(ex.toString());
          throw new IllegalStateException("", ex);
        }
      } else {
        AssertBigIntegersEqual(result, bigintA.remainder(bigintB));
        AssertBigIntegersEqual(result, bigintA.divideAndRemainder(bigintB)[1]);
      }
    }

    public static void AssertAdd(BigInteger bi, BigInteger bi2, String s) {
      BigIntegerTest.AssertBigIntegersEqual(s, bi.add(bi2));
      BigIntegerTest.AssertBigIntegersEqual(s, bi2.add(bi));
      BigInteger negbi = BigInteger.valueOf(0).subtract(bi);
      BigInteger negbi2 = BigInteger.valueOf(0).subtract(bi2);
      BigIntegerTest.AssertBigIntegersEqual(s, bi.subtract(negbi2));
      BigIntegerTest.AssertBigIntegersEqual(s, bi2.subtract(negbi));
    }
    @Test
    public void TestAdd() {
      BigInteger posSmall = BigInteger.valueOf(5);
      BigInteger negSmall = BigInteger.valueOf(-5);
      BigInteger posLarge = BigInteger.valueOf(5555555);
      BigInteger negLarge = BigInteger.valueOf(-5555555);
      AssertAdd(posSmall, posSmall, "10");
      AssertAdd(posSmall, negSmall, "0");
      AssertAdd(posSmall, posLarge, "5555560");
      AssertAdd(posSmall, negLarge, "-5555550");
      AssertAdd(negSmall, negSmall, "-10");
      AssertAdd(negSmall, posLarge, "5555550");
      AssertAdd(negSmall, negLarge, "-5555560");
      AssertAdd(posLarge, posLarge, "11111110");
      AssertAdd(posLarge, negLarge, "0");
      AssertAdd(negLarge, negLarge, "-11111110");
    }

    @Test
    public void TestCanFitInInt() {
      // not implemented yet
    }
    @Test
    public void TestCompareTo() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 500; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        BigInteger bigintB = RandomBigInteger(r);
        BigInteger bigintC = RandomBigInteger(r);
        TestCommon.CompareTestRelations(bigintA, bigintB, bigintC);
        TestCommon.CompareTestConsistency(bigintA, bigintB, bigintC);
      }
    }
    @Test
    public void TestDivide() {
      int intA, intB;
      FastRandom fr = new FastRandom();
      for (int i = 0; i < 10000; ++i) {
        intA = fr.NextValue(0x1000000);
        intB = fr.NextValue(0x1000000);
        if (intB == 0) {
          continue;
        }
        int c = intA / intB;
        BigInteger bigintA = BigInteger.valueOf(intA);
        BigInteger bigintB = BigInteger.valueOf(intB);
        BigInteger bigintC = bigintA.divide(bigintB);
        Assert.assertEquals(bigintC.intValueChecked(), c);
      }
      DoTestDivide("2472320648", "2831812081", "0");
      DoTestDivide("-2472320648", "2831812081", "0");
      DoTestDivide(
    "9999999999999999999999",
    "281474976710655",
    "35527136");
    }
    @Test
    public void TestDivRem() {
      // not implemented yet
    }
    @Test
    public void TestEquals() {
      if (BigInteger.valueOf(1).equals(null))Assert.fail();
      if (BigInteger.valueOf(0).equals(null))Assert.fail();
      if (BigInteger.valueOf(1).equals(BigInteger.valueOf(0)))Assert.fail();
      if (BigInteger.valueOf(0).equals(BigInteger.valueOf(1)))Assert.fail();
      FastRandom r = new FastRandom();
      for (int i = 0; i < 500; ++i) {
        BigInteger bigintA = RandomObjects.RandomBigInteger(r);
        BigInteger bigintB = RandomObjects.RandomBigInteger(r);
        TestCommon.AssertEqualsHashCode(bigintA, bigintB);
      }
    }

    public static int ModPow(int x, int pow, int intMod) {
      if (x < 0) {
        throw new IllegalArgumentException(
          "x (" + x + ") is less than 0");
      }
      if (pow <= 0) {
        throw new IllegalArgumentException(
          "pow (" + pow + ") is not greater than 0");
      }
      if (intMod <= 0) {
        throw new IllegalArgumentException(
          "mod (" + intMod + ") is not greater than 0");
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
        return n == 3 || n == 5 || n == 7 || n == 11 ||
          n == 13 || n == 17 || n == 19 || n == 23;
      }
      // Use a deterministic Rabin-Miller test
      int d = n - 1;
      int shift = 0;
      while ((d & 1) == 0) {
        d >>= 1;
        ++shift;
      }
      int mp = 0, mp2 = 0;
      boolean found = false;
      // For all 32-bit integers it's enough
      // to check the strong pseudoprime
      // bases 2, 7, and 61
      if (n > 2) {
        mp = ModPow(2, d, n);
        if (mp != 1 && mp + 1 != n) {
          found = false;
          for (int i = 1; i < shift; ++i) {
            mp2 = ModPow(2, d << i, n);
            if (mp2 + 1 == n) {
              found = true;
              break;
            }
          }
          if (found) {
            return false;
          }
        }
      }
      if (n > 7) {
        mp = ModPow(7, d, n);
        if (mp != 1 && mp + 1 != n) {
          found = false;
          for (int i = 1; i < shift; ++i) {
            mp2 = ModPow(7, d << i, n);
            if (mp2 + 1 == n) {
              found = true;
              break;
            }
          }
          if (found) {
            return false;
          }
        }
      }
      if (n > 61) {
        mp = ModPow(61, d, n);
        if (mp != 1 && mp + 1 != n) {
          found = false;
          for (int i = 1; i < shift; ++i) {
            mp2 = ModPow(61, d << i, n);
            if (mp2 + 1 == n) {
              found = true;
              break;
            }
          }
          if (found) {
            return false;
          }
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
      try {
 BigInteger.valueOf(0).gcd(null);
Assert.fail("Should have failed");
} catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
      {
String stringTemp = BigInteger.valueOf(0).gcd(BigFromString(
"244")).toString();
Assert.assertEquals(
"244",
stringTemp);
}
      {
String stringTemp = BigInteger.valueOf(0).gcd(BigFromString(
"-244")).toString();
Assert.assertEquals(
"244",
stringTemp);
}
      {
String stringTemp = BigFromString(
"244").gcd(BigInteger.valueOf(0)).toString();
Assert.assertEquals(
"244",
stringTemp);
}
      {
String stringTemp = BigFromString(
"-244").gcd(BigInteger.valueOf(0)).toString();
Assert.assertEquals(
"244",
stringTemp);
}
      {
String stringTemp = BigInteger.valueOf(1).gcd(BigFromString("244")).toString();
Assert.assertEquals(
"1",
stringTemp);
}
      {
String stringTemp = BigInteger.valueOf(1).gcd(BigFromString(
"-244")).toString();
Assert.assertEquals(
"1",
stringTemp);
}
      {
String stringTemp = BigFromString("244").gcd(BigInteger.valueOf(1)).toString();
Assert.assertEquals(
"1",
stringTemp);
}
      {
String stringTemp = BigFromString(
"-244").gcd(BigInteger.valueOf(1)).toString();
Assert.assertEquals(
"1",
stringTemp);
}
      {
String stringTemp = BigFromString("15").gcd(BigFromString(
"15")).toString();
Assert.assertEquals(
"15",
stringTemp);
}
      {
String stringTemp = BigFromString("-15").gcd(
        BigFromString("15")).toString();
Assert.assertEquals(
"15",
stringTemp);
}
      {
String stringTemp = BigFromString("15").gcd(
        BigFromString("-15")).toString();
Assert.assertEquals(
"15",
stringTemp);
}
      {
String stringTemp = BigFromString(
"-15").gcd(BigFromString("-15")).toString();
Assert.assertEquals(
"15",
stringTemp);
}
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
        BigInteger ba = RandomBigInteger(rand);
        if (ba.signum() == 0) {
          continue;
        }
        ba = ba.multiply(bigprime);
        Assert.assertEquals(
          bigprime,
          bigprime.gcd(ba));
      }
      TestGcdPair(BigInteger.valueOf(-1867), BigInteger.valueOf(-4456), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(4604), BigInteger.valueOf(-4516), BigInteger.valueOf(4));
      TestGcdPair(BigInteger.valueOf(-1756), BigInteger.valueOf(4525), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(1568), BigInteger.valueOf(-4955), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(2519), BigInteger.valueOf(2845), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-1470), BigInteger.valueOf(132), BigInteger.valueOf(6));
      TestGcdPair(BigInteger.valueOf(-2982), BigInteger.valueOf(2573), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-244), BigInteger.valueOf(-3929), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-3794), BigInteger.valueOf(-2325), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-2667), BigInteger.valueOf(2123), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-3712), BigInteger.valueOf(-1850), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(2329), BigInteger.valueOf(3874), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(1384), BigInteger.valueOf(-4278), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(213), BigInteger.valueOf(-1217), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(1163), BigInteger.valueOf(2819), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(1921), BigInteger.valueOf(-579), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(3886), BigInteger.valueOf(-13), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-3270), BigInteger.valueOf(-3760), BigInteger.valueOf(10));
      TestGcdPair(BigInteger.valueOf(-3528), BigInteger.valueOf(1822), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(1547), BigInteger.valueOf(-333), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(2402), BigInteger.valueOf(2850), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(4519), BigInteger.valueOf(1296), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(1821), BigInteger.valueOf(2949), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-2634), BigInteger.valueOf(-4353), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-1728), BigInteger.valueOf(199), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-4646), BigInteger.valueOf(-1418), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-35), BigInteger.valueOf(-3578), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-2244), BigInteger.valueOf(-3250), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-3329), BigInteger.valueOf(1039), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-3064), BigInteger.valueOf(-4730), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-1214), BigInteger.valueOf(4130), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-3038), BigInteger.valueOf(-3184), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-209), BigInteger.valueOf(-1617), BigInteger.valueOf(11));
      TestGcdPair(BigInteger.valueOf(-1101), BigInteger.valueOf(-2886), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-3021), BigInteger.valueOf(-4499), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(3108), BigInteger.valueOf(1815), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(1195), BigInteger.valueOf(4618), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-3643), BigInteger.valueOf(2156), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-2067), BigInteger.valueOf(-3780), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(4251), BigInteger.valueOf(1607), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(438), BigInteger.valueOf(741), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-3692), BigInteger.valueOf(-2135), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-1076), BigInteger.valueOf(2149), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-3224), BigInteger.valueOf(-1532), BigInteger.valueOf(4));
      TestGcdPair(BigInteger.valueOf(-3713), BigInteger.valueOf(1721), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(3038), BigInteger.valueOf(-2657), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(4977), BigInteger.valueOf(-110), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-3305), BigInteger.valueOf(-922), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(1902), BigInteger.valueOf(2481), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-4804), BigInteger.valueOf(-1378), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-1446), BigInteger.valueOf(-4226), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-1409), BigInteger.valueOf(3303), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-1626), BigInteger.valueOf(-3193), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(912), BigInteger.valueOf(-421), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(751), BigInteger.valueOf(-1755), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(3135), BigInteger.valueOf(-3581), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-4941), BigInteger.valueOf(-2885), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(4744), BigInteger.valueOf(3240), BigInteger.valueOf(8));
      TestGcdPair(BigInteger.valueOf(3488), BigInteger.valueOf(4792), BigInteger.valueOf(8));
      TestGcdPair(BigInteger.valueOf(3632), BigInteger.valueOf(3670), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(-4821), BigInteger.valueOf(-1749), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(4666), BigInteger.valueOf(2013), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(810), BigInteger.valueOf(-3466), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(2199), BigInteger.valueOf(161), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-1137), BigInteger.valueOf(-1620), BigInteger.valueOf(3));
      TestGcdPair(BigInteger.valueOf(-472), BigInteger.valueOf(66), BigInteger.valueOf(2));
      TestGcdPair(BigInteger.valueOf(3825), BigInteger.valueOf(2804), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-2895), BigInteger.valueOf(1942), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(1576), BigInteger.valueOf(-4209), BigInteger.valueOf(1));
      TestGcdPair(BigInteger.valueOf(-277), BigInteger.valueOf(-4415), BigInteger.valueOf(1));
      for (int i = 0; i < 1000; ++i) {
        prime = rand.NextValue(0x7fffffff);
        if (rand.NextValue(2) == 0) {
          prime = -prime;
        }
        int intB = rand.NextValue(0x7fffffff);
        if (rand.NextValue(2) == 0) {
          intB = -intB;
        }
        BigInteger biga = BigInteger.valueOf(prime);
        BigInteger bigb = BigInteger.valueOf(intB);
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
    public void TestGetLowBit() {
      // not implemented yet
    }
    @Test
    public void TestGetUnsignedBitLength() {
      // not implemented yet
    }

    @Test
    public void TestIntValueChecked() {
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigValueOf(Integer.MIN_VALUE).intValueChecked());
      Assert.assertEquals(
        Integer.MAX_VALUE,
        BigValueOf(Integer.MAX_VALUE).intValueChecked());
      try {
        BigValueOf(Integer.MIN_VALUE - 1L).intValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigValueOf(Integer.MAX_VALUE + 1L).intValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigFromString("999999999999999999999999999999999").intValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigValueOf(Integer.MIN_VALUE).intValueChecked());
      Assert.assertEquals(
        Integer.MAX_VALUE,
        BigValueOf(Integer.MAX_VALUE).intValueChecked());
      try {
        BigValueOf(Integer.MIN_VALUE - 1L).intValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigValueOf(Integer.MAX_VALUE + 1L).intValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestIsEven() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        BigInteger mod = bigintA.remainder(BigValueOf(2));
        Assert.assertEquals(mod.signum() == 0, bigintA.isEven());
      }
    }

    @Test
    public void TestLongValueChecked() {
      Assert.assertEquals(
        Long.MIN_VALUE,
        BigValueOf(Long.MIN_VALUE).longValueChecked());
      Assert.assertEquals(
        Long.MAX_VALUE,
        BigValueOf(Long.MAX_VALUE).longValueChecked());
      try {
        BigInteger bigintTemp = BigValueOf(Long.MIN_VALUE);
        bigintTemp = bigintTemp.subtract(BigInteger.valueOf(1));
        bigintTemp.longValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger bigintTemp = BigValueOf(Long.MAX_VALUE);
        bigintTemp = bigintTemp.add(BigInteger.valueOf(1));
        bigintTemp.longValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(
        ((long)0xFFFFFFF200000000L),
  BigValueOf(((long)0xFFFFFFF200000000L)).longValueChecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF280000000L),
  BigValueOf(((long)0xFFFFFFF280000000L)).longValueChecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF280000001L),
  BigValueOf(((long)0xFFFFFFF280000001L)).longValueChecked());
      Assert.assertEquals(
        ((long)0xFFFFFFF27FFFFFFFL),
  BigValueOf(((long)0xFFFFFFF27FFFFFFFL)).longValueChecked());
      Assert.assertEquals(
        0x0000000380000001L,
        BigValueOf(0x0000000380000001L).longValueChecked());
      Assert.assertEquals(
        0x0000000382222222L,
        BigValueOf(0x0000000382222222L).longValueChecked());
      Assert.assertEquals(-8L, BigValueOf(-8L).longValueChecked());
      Assert.assertEquals(-32768L, BigValueOf(-32768L).longValueChecked());
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigValueOf(Integer.MIN_VALUE).longValueChecked());
      Assert.assertEquals(
        Integer.MAX_VALUE,
        BigValueOf(Integer.MAX_VALUE).longValueChecked());
      Assert.assertEquals(
        0x80000000L,
        BigValueOf(0x80000000L).longValueChecked());
      Assert.assertEquals(
        0x90000000L,
        BigValueOf(0x90000000L).longValueChecked());
      try {
        BigFromString("999999999999999999999999999999999").longValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(
        Long.MIN_VALUE,
        BigValueOf(Long.MIN_VALUE).longValueChecked());
      Assert.assertEquals(
        Long.MAX_VALUE,
        BigValueOf(Long.MAX_VALUE).longValueChecked());
      try {
        BigInteger bigintTemp = BigValueOf(Long.MIN_VALUE);
        bigintTemp = bigintTemp.subtract(BigInteger.valueOf(1));
        bigintTemp.longValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger bigintTemp = BigValueOf(Long.MAX_VALUE);
        bigintTemp = bigintTemp.add(BigInteger.valueOf(1));
        bigintTemp.longValueChecked();
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      long longV = ((long)0xFFFFFFF200000000L);
      Assert.assertEquals(
longV,
BigValueOf(longV).longValueChecked());
      longV = ((long)0xFFFFFFF280000000L);
      Assert.assertEquals(
longV,
BigValueOf(longV).longValueChecked());
      longV = ((long)0xFFFFFFF280000001L);
      Assert.assertEquals(
longV,
BigValueOf(longV).longValueChecked());
      longV = ((long)0xFFFFFFF27FFFFFFFL);
      Assert.assertEquals(
longV,
BigValueOf(longV).longValueChecked());
      Assert.assertEquals(
        0x0000000380000001L,
        BigValueOf(0x0000000380000001L).longValueChecked());
      Assert.assertEquals(
        0x0000000382222222L,
        BigValueOf(0x0000000382222222L).longValueChecked());
      Assert.assertEquals(-8L, BigValueOf(-8L).longValueChecked());
      Assert.assertEquals(-32768L, BigValueOf(-32768L).longValueChecked());
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigValueOf(Integer.MIN_VALUE).longValueChecked());
      Assert.assertEquals(
        Integer.MAX_VALUE,
        BigValueOf(Integer.MAX_VALUE).longValueChecked());
      Assert.assertEquals(
        0x80000000L,
        BigValueOf(0x80000000L).longValueChecked());
      Assert.assertEquals(
        0x90000000L,
        BigValueOf(0x90000000L).longValueChecked());
    }

    @Test
    public void TestMod() {
      try {
        BigInteger.valueOf(1).mod(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        (BigInteger.valueOf(13)).mod(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        (BigInteger.valueOf(13)).mod(BigInteger.valueOf(-4));
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        (BigInteger.valueOf(-13)).mod(BigInteger.valueOf(-4));
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestIntValueUnchecked() {
      Assert.assertEquals(0L, BigInteger.valueOf(0).intValueUnchecked());
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigValueOf(Integer.MIN_VALUE).intValueUnchecked());
      Assert.assertEquals(
        Integer.MAX_VALUE,
        BigValueOf(Integer.MAX_VALUE).intValueUnchecked());
      Assert.assertEquals(
        Integer.MAX_VALUE,
        BigValueOf(Integer.MIN_VALUE - 1L).intValueUnchecked());
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigValueOf(Integer.MAX_VALUE + 1L).intValueUnchecked());
    }
    @Test
    public void TestLongValueUnchecked() {
      Assert.assertEquals(0L, BigInteger.valueOf(0).longValueUnchecked());
      Assert.assertEquals(
        Long.MIN_VALUE,
        BigValueOf(Long.MIN_VALUE).longValueUnchecked());
      Assert.assertEquals(
        Long.MAX_VALUE,
        BigValueOf(Long.MAX_VALUE).longValueUnchecked());
      {
Object objectTemp = Long.MAX_VALUE;
Object objectTemp2 = BigValueOf(Long.MIN_VALUE)
        .subtract(BigInteger.valueOf(1)).longValueUnchecked();
Assert.assertEquals(objectTemp, objectTemp2);
}
      Assert.assertEquals(
        Long.MIN_VALUE,
        BigValueOf(Long.MAX_VALUE).add(BigInteger.valueOf(1)).longValueUnchecked());
      long aa = ((long)0xFFFFFFF200000000L);
      Assert.assertEquals(
              aa,
              BigValueOf(aa).longValueUnchecked());
      aa = ((long)0xFFFFFFF280000000L);
      Assert.assertEquals(
              aa,
              BigValueOf(aa).longValueUnchecked());
      aa = ((long)0xFFFFFFF200000001L);
      Assert.assertEquals(
              aa,
              BigValueOf(aa).longValueUnchecked());
      aa = ((long)0xFFFFFFF27FFFFFFFL);
      Assert.assertEquals(
              aa,
              BigValueOf(aa).longValueUnchecked());
      Assert.assertEquals(
        0x0000000380000001L,
        BigValueOf(0x0000000380000001L).longValueUnchecked());
      Assert.assertEquals(
        0x0000000382222222L,
        BigValueOf(0x0000000382222222L).longValueUnchecked());
      Assert.assertEquals(-8L, BigValueOf(-8L).longValueUnchecked());
      Assert.assertEquals(
        -32768L,
        BigValueOf(-32768L).longValueUnchecked());
      Assert.assertEquals(
        Integer.MIN_VALUE,
        BigValueOf(Integer.MIN_VALUE).longValueUnchecked());
      Assert.assertEquals(
        Integer.MAX_VALUE,
        BigValueOf(Integer.MAX_VALUE).longValueUnchecked());
      Assert.assertEquals(
        0x80000000L,
        BigValueOf(0x80000000L).longValueUnchecked());
      Assert.assertEquals(
        0x90000000L,
        BigValueOf(0x90000000L).longValueUnchecked());
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
    public void TestDivideAndRemainder() {
      try {
        BigInteger.valueOf(1).divideAndRemainder(BigInteger.valueOf(0));
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).divideAndRemainder(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestFromBytes() {
      Assert.assertEquals(
        BigInteger.valueOf(0), BigInteger.fromBytes(new byte[] { }, false));

      try {
        BigInteger.fromBytes(null, false);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestBitLength() {
      Assert.assertEquals(31, BigValueOf(-2147483647L).bitLength());
      Assert.assertEquals(31, BigValueOf(-2147483648L).bitLength());
      Assert.assertEquals(32, BigValueOf(-2147483649L).bitLength());
      Assert.assertEquals(32, BigValueOf(-2147483650L).bitLength());
      Assert.assertEquals(31, BigValueOf(2147483647L).bitLength());
      Assert.assertEquals(32, BigValueOf(2147483648L).bitLength());
      Assert.assertEquals(32, BigValueOf(2147483649L).bitLength());
      Assert.assertEquals(32, BigValueOf(2147483650L).bitLength());
      Assert.assertEquals(0, BigValueOf(0).bitLength());
      Assert.assertEquals(1, BigValueOf(1).bitLength());
      Assert.assertEquals(2, BigValueOf(2).bitLength());
      Assert.assertEquals(2, BigValueOf(2).bitLength());
      Assert.assertEquals(31, BigValueOf(Integer.MAX_VALUE).bitLength());
      Assert.assertEquals(31, BigValueOf(Integer.MIN_VALUE).bitLength());
      Assert.assertEquals(16, BigValueOf(65535).bitLength());
      Assert.assertEquals(16, BigValueOf(-65535).bitLength());
      Assert.assertEquals(17, BigValueOf(65536).bitLength());
      Assert.assertEquals(16, BigValueOf(-65536).bitLength());
      Assert.assertEquals(
        65,
        BigFromString("19084941898444092059").bitLength());
      Assert.assertEquals(
        65,
        BigFromString("-19084941898444092059").bitLength());
      Assert.assertEquals(0, BigValueOf(-1).bitLength());
      Assert.assertEquals(1, BigValueOf(-2).bitLength());
    }
    @Test
    public void TestFromString() {
      try {
        BigFromString("xyz");
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigFromString("");
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigFromString(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
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
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }

      try {
        BigInteger.fromSubstring("123", -1, 2);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromSubstring("123", 4, 2);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromSubstring("123", 1, -1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromSubstring("123", 1, 4);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromSubstring("123", 1, 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromSubstring("123", 2, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      {
 String stringTemp = BigInteger.fromSubstring(
   "0123456789",
   9,
   10).toString();
        Assert.assertEquals(
          "9",
          stringTemp);
      }
      {
 String stringTemp = BigInteger.fromSubstring(
   "0123456789",
   8,
   10).toString();
        Assert.assertEquals(
          "89",
          stringTemp);
      }
      {
 String stringTemp = BigInteger.fromSubstring(
   "0123456789",
   7,
   10).toString();
        Assert.assertEquals(
          "789",
          stringTemp);
      }
      {
 String stringTemp = BigInteger.fromSubstring(
   "0123456789",
   6,
   10).toString();
        Assert.assertEquals(
          "6789",
          stringTemp);
      }
    }
    @Test
    public void TestFromRadixString() {
      try {
        BigInteger.fromRadixString(null, 10);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", -37);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", Integer.MIN_VALUE);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixString("0", Integer.MAX_VALUE);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
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
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", 1, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", 0, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", -37, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", Integer.MIN_VALUE, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0", Integer.MAX_VALUE, 0, 1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("123", 10, -1, 2);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("123", 10, 4, 5);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("123", 10, 0, -8);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("123", 10, 0, 6);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("123", 10, 2, 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("123", 10, 0, 0);
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("123", 10, 1, 1);
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("-", 10, 0, 1);
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("g", 16, 0, 1);
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0123gggg", 16, 0, 8);
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0123gggg", 10, 0, 8);
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.fromRadixSubstring("0123aaaa", 10, 0, 8);
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
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
          String sabiString = sabi.getStringValue();
          BigInteger actualBigInt = BigInteger.fromRadixSubstring(
            padding + sabiString,
            i,
            j + 1,
            j + 1 + sabiString.length());
          Assert.assertEquals(
            sabi.getBigIntValue(),
            actualBigInt);
        }
      }
    }
    @Test
    public void TestGetDigitCount() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        String str = (bigintA).abs().toString();
        Assert.assertEquals(str.length(), bigintA.getDigitCount());
      }
    }
    @Test
    public void TestBigIntegerModPow() {
      try {
 BigInteger.valueOf(1).ModPow(null, null);
Assert.fail("Should have failed");
} catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
      try {
 BigInteger.valueOf(1).ModPow(null, BigInteger.valueOf(0));
Assert.fail("Should have failed");
} catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
      try {
 BigInteger.valueOf(1).ModPow(BigInteger.valueOf(0), null);
Assert.fail("Should have failed");
} catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
      try {
 BigInteger.valueOf(1).ModPow(BigFromString("-1"), BigFromString("1"));
Assert.fail("Should have failed");
} catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
      try {
 BigInteger.valueOf(1).ModPow(BigFromString("0"), BigFromString("0"));
Assert.fail("Should have failed");
} catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
      try {
 BigInteger.valueOf(1).ModPow(BigFromString("0"), BigFromString("-1"));
Assert.fail("Should have failed");
} catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
      try {
 BigInteger.valueOf(1).ModPow(BigFromString("1"), BigFromString("0"));
Assert.fail("Should have failed");
} catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
      try {
 BigInteger.valueOf(1).ModPow(BigFromString("1"), BigFromString("-1"));
Assert.fail("Should have failed");
} catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
    }

    @Test
    public void TestSqrt() {
      FastRandom r = new FastRandom();
      for (int i = 0; i < 10000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        if (bigintA.signum() < 0) {
          bigintA = bigintA.negate();
        }
        if (bigintA.signum() == 0) {
          bigintA = BigInteger.valueOf(1);
        }
        BigInteger sr = bigintA.sqrt();
        BigInteger srsqr = sr.multiply(sr);
        sr = sr.add(BigInteger.valueOf(1));
        BigInteger sronesqr = sr.multiply(sr);
        if (srsqr.compareTo(bigintA) > 0) {
          Assert.fail(srsqr + " not " + bigintA +
            " or less (TestSqrt, sqrt=" + sr + ")");
        }
        if (sronesqr.compareTo(bigintA) <= 0) {
          Assert.fail(srsqr + " not greater than " + bigintA +
            " (TestSqrt, sqrt=" + sr + ")");
        }
      }
    }
    @Test
    public void TestTestBit() {
      if (BigInteger.valueOf(0).testBit(0))Assert.fail();
      if (BigInteger.valueOf(0).testBit(1))Assert.fail();
      if (!(BigInteger.valueOf(1).testBit(0)))Assert.fail();
      if (BigInteger.valueOf(1).testBit(1))Assert.fail();
      for (int i = 0; i < 32; ++i) {
        if (!(BigValueOf(-1).testBit(i)))Assert.fail();
      }
    }

    @Test
    public void TestToRadixString() {
      FastRandom fr = new FastRandom();
      try {
        BigInteger.valueOf(1).toRadixString(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).toRadixString(0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).toRadixString(1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).toRadixString(37);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).toRadixString(Integer.MIN_VALUE);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).toRadixString(Integer.MAX_VALUE);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        System.out.print("");
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
      FastRandom r = new FastRandom();
      for (int radix = 2; radix < 36; ++radix) {
        for (int i = 0; i < 80; ++i) {
          BigInteger bigintA = RandomBigInteger(r);
          String s = bigintA.toRadixString(radix);
          BigInteger big2 = BigInteger.fromRadixString(s, radix);
          Assert.assertEquals(big2.toRadixString(radix), s);
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
      FastRandom r = new FastRandom();
      for (int i = 0; i < 200; ++i) {
        int power = 1 + r.NextValue(8);
        BigInteger bigintA = RandomBigInteger(r);
        BigInteger bigintB = bigintA;
        for (int j = 1; j < power; ++j) {
          bigintB = bigintB.multiply(bigintA);
        }
        DoTestPow(bigintA.toString(), power, bigintB.toString());
      }
    }
    @Test
    public void TestMultiply() {
      try {
        BigInteger.valueOf(1).multiply(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        System.out.print("");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      FastRandom r = new FastRandom();
      for (int i = 0; i < 10000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        BigInteger bigintB = bigintA.add(BigInteger.valueOf(1));
        BigInteger bigintC = bigintA.multiply(bigintB);
        // Test near-squaring
        if (bigintA.signum() == 0 || bigintB.signum() == 0) {
          Assert.assertEquals(BigInteger.valueOf(0), bigintC);
        }
        if (bigintA.equals(BigInteger.valueOf(1))) {
          Assert.assertEquals(bigintB, bigintC);
        }
        if (bigintB.equals(BigInteger.valueOf(1))) {
          Assert.assertEquals(bigintA, bigintC);
        }
        bigintB = bigintA;
        // Test squaring
        bigintC = bigintA.multiply(bigintB);
        if (bigintA.signum() == 0 || bigintB.signum() == 0) {
          Assert.assertEquals(BigInteger.valueOf(0), bigintC);
        }
        if (bigintA.equals(BigInteger.valueOf(1))) {
          Assert.assertEquals(bigintB, bigintC);
        }
        if (bigintB.equals(BigInteger.valueOf(1))) {
          Assert.assertEquals(bigintA, bigintC);
        }
      }
      DoTestMultiply(
"39258416159456516340113264558732499166970244380745050",
"39258416159456516340113264558732499166970244380745051",
"1541223239349076530208308657654362309553698742116222355477449713742236585667505604058123112521437480247550");
      DoTestMultiply(
  "5786426269322750882632312999752639738983363095641642905722171221986067189342123124290107105663618428969517616421742429671402859775667602123564",
  "331378991485809774307751183645559883724387697397707434271522313077548174328632968616330900320595966360728317363190772921",
  "1917500101435169880779183578665955372346028226046021044867189027856189131730889958057717187493786883422516390996639766012958050987359732634213213442579444095928862861132583117668061032227577386757036981448703231972963300147061503108512300577364845823910107210444");
    }
    @Test
    public void TestPowBigIntVar() {
      // not implemented yet
    }
    @Test
    public void TestRemainder() {
      DoTestRemainder("2472320648", "2831812081", "2472320648");
      DoTestRemainder("-2472320648", "2831812081", "-2472320648");
    }

    public static void DoTestShiftLeft(String m1, int m2, String result) {
      BigInteger bigintA = BigFromString(m1);
      AssertBigIntegersEqual(result, bigintA.shiftLeft(m2));
      m2 = -m2;
      AssertBigIntegersEqual(result, bigintA.shiftRight(m2));
    }

    public static void DoTestShiftRight(String m1, int m2, String result) {
      BigInteger bigintA = BigFromString(m1);
      AssertBigIntegersEqual(result, bigintA.shiftRight(m2));
      m2 = -m2;
      AssertBigIntegersEqual(result, bigintA.shiftLeft(m2));
    }

    public static void DoTestShiftRight2(String m1, int m2, BigInteger result) {
      BigInteger bigintA = BigFromString(m1);
      TestCommon.CompareTestEqualAndConsistent(result, bigintA.shiftRight(m2));
      m2 = -m2;
      TestCommon.CompareTestEqualAndConsistent(result, bigintA.shiftLeft(m2));
    }
    @Test
    public void TestShiftLeft() {
      BigInteger bigint = BigInteger.valueOf(1);
      bigint = bigint.shiftLeft(100);
      TestCommon.CompareTestEqualAndConsistent(bigint.shiftLeft(12), bigint.shiftRight(-12));
      TestCommon.CompareTestEqualAndConsistent(bigint.shiftLeft(-12), bigint.shiftRight(12));
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        BigInteger bigintB = bigintA;
        for (int j = 0; j < 100; ++j) {
          BigInteger ba = bigintA;
          ba = ba.shiftLeft(j);
          TestCommon.CompareTestEqualAndConsistent(bigintB, ba);
          int negj = -j;
          ba = bigintA;
          ba = ba.shiftRight(negj);
          TestCommon.CompareTestEqualAndConsistent(bigintB, ba);
          bigintB = bigintB.multiply(BigInteger.valueOf(2));
        }
      }
    }
    @Test
    public void TestShiftRight() {
      BigInteger bigint = BigInteger.valueOf(1);
      bigint = bigint.shiftLeft(80);
      TestCommon.CompareTestEqualAndConsistent(bigint.shiftLeft(12), bigint.shiftRight(-12));
      TestCommon.CompareTestEqualAndConsistent(bigint.shiftLeft(-12), bigint.shiftRight(12));
      FastRandom r = new FastRandom();
      BigInteger minusone = BigInteger.valueOf(0);
      minusone = minusone.subtract(BigInteger.valueOf(1));
      for (int i = 0; i < 1000; ++i) {
        int smallint = r.NextValue(0x7fffffff);
        BigInteger bigintA = BigInteger.valueOf(smallint);
        String str = bigintA.toString();
        for (int j = 32; j < 80; ++j) {
          DoTestShiftRight2(str, j, BigInteger.valueOf(0));
          DoTestShiftRight2("-" + str, j, minusone);
        }
      }
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        bigintA = (bigintA).abs();
        BigInteger bigintB = bigintA;
        for (int j = 0; j < 100; ++j) {
          BigInteger ba = bigintA;
          ba = ba.shiftRight(j);
          TestCommon.CompareTestEqualAndConsistent(bigintB, ba);
          int negj = -j;
          ba = bigintA;
          ba = ba.shiftLeft(negj);
          TestCommon.CompareTestEqualAndConsistent(bigintB, ba);
          bigintB = bigintB.divide(BigInteger.valueOf(2));
        }
      }
    }
    @Test
    public void TestSign() {
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
    public void TestToString() {
      BigInteger bi = BigInteger.valueOf(3);
      AssertBigIntegersEqual("3", bi);
      BigInteger negseven = BigInteger.valueOf(-7);
      AssertBigIntegersEqual("-7", negseven);
      BigInteger other = BigInteger.valueOf(-898989);
      AssertBigIntegersEqual("-898989", other);
      other = BigInteger.valueOf(898989);
      AssertBigIntegersEqual("898989", other);
      FastRandom r = new FastRandom();
      for (int i = 0; i < 1000; ++i) {
        BigInteger bigintA = RandomBigInteger(r);
        String s = bigintA.toString();
        BigInteger big2 = BigFromString(s);
        Assert.assertEquals(big2.toString(), s);
      }
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
      {
        String stringTemp = BigInteger.valueOf(0).toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
    }

    @Test
    public void TestMiscellaneous() {
      Assert.assertEquals(1, BigInteger.valueOf(0).getDigitCount());
      BigInteger minValue = BigInteger.valueOf(Integer.MIN_VALUE);
      BigInteger minValueTimes2 = minValue.add(minValue);
      Assert.assertEquals(Integer.MIN_VALUE, minValue.intValueChecked());
      try {
        System.out.println(minValueTimes2.intValueChecked());
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      BigInteger verybig = BigInteger.valueOf(1).shiftLeft(80);
      try {
        System.out.println(verybig.intValueChecked());
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        System.out.println(verybig.longValueChecked());
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).PowBigIntVar(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).pow(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        (BigInteger.valueOf(0).subtract(BigInteger.valueOf(1))).PowBigIntVar(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      if (BigInteger.valueOf(1).equals(BigInteger.valueOf(0)))Assert.fail();
      if (verybig.equals(BigInteger.valueOf(0)))Assert.fail();
      if (BigInteger.valueOf(1).equals(BigInteger.valueOf(0).subtract(BigInteger.valueOf(1))))Assert.fail();
      Assert.assertEquals(1, BigInteger.valueOf(1).compareTo(null));
      BigInteger[] tmpsqrt = BigInteger.valueOf(0).sqrtWithRemainder();
      Assert.assertEquals(BigInteger.valueOf(0), tmpsqrt[0]);
    }

    @Test
    public void TestExceptions() {
      try {
        BigFromString(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }

      try {
        BigInteger.valueOf(0).testBit(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigFromString("x11");
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigFromString(".");
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigFromString("..");
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigFromString("e200");
        Assert.fail("Should have failed");
      } catch (NumberFormatException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }

      try {
        BigInteger.valueOf(1).mod(BigInteger.valueOf(-1));
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).add(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).subtract(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).divide(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).divide(BigInteger.valueOf(0));
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).remainder(BigInteger.valueOf(0));
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).mod(BigInteger.valueOf(0));
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger.valueOf(1).remainder(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      Assert.assertEquals(BigInteger.valueOf(1), (BigInteger.valueOf(13)).mod(BigInteger.valueOf(4)));
      Assert.assertEquals(BigInteger.valueOf(3), (BigInteger.valueOf(-13)).mod(BigInteger.valueOf(4)));
    }
  }
