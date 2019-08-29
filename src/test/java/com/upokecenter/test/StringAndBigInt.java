package com.upokecenter.test;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  final class StringAndBigInt {
    private static final String ValueDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String ValueDigitsLower =
          "0123456789abcdefghijklmnopqrstuvwxyz";

    private String stringValue;

    public final String getStringValue() {
        return this.stringValue;
      }

    private EInteger bigintValue;

    public final EInteger getBigIntValue() {
        return this.bigintValue;
      }

    public static StringAndBigInt Generate(RandomGenerator rand, int radix) {
       return Generate(rand, radix, 50);
    }

    public static StringAndBigInt Generate(RandomGenerator rand, int radix,
  int maxNumDigits) {
      if (radix < 2) {
        throw new IllegalArgumentException("radix (" + radix +
          ") is less than 2");
      }
      if (radix > 36) {
        throw new IllegalArgumentException("radix (" + radix +
          ") is more than 36");
      }
      EInteger bv = EInteger.FromInt32(0);
      StringAndBigInt sabi = new StringAndBigInt();
      int numDigits = 1 + rand.UniformInt(maxNumDigits);
      boolean negative = false;
      StringBuilder builder = new StringBuilder();
      if (rand.UniformInt(2) == 0) {
        builder.append('-');
        negative = true;
      }
      int radixpowint = radix * radix * radix * radix;
      EInteger radixpow4 = EInteger.FromInt32(radixpowint);
      EInteger radixpow1 = EInteger.FromInt32(radix);
      int count = 0;
      for (int i = 0; i < numDigits - 4; i += 4) {
        int digitvalues = rand.UniformInt(radixpowint);
        int digit = digitvalues % radix;
        digitvalues /= radix;
        int digit2 = digitvalues % radix;
        digitvalues /= radix;
        int digit3 = digitvalues % radix;
        digitvalues /= radix;
        int digit4 = digitvalues % radix;
        digitvalues /= radix;
        count += 4;
        int bits = rand.UniformInt(16);
        if ((bits & 0x01) == 0) {
          builder.append(ValueDigits.charAt(digit));
        } else {
          builder.append(ValueDigitsLower.charAt(digit));
        }
        if ((bits & 0x02) == 0) {
          builder.append(ValueDigits.charAt(digit2));
        } else {
          builder.append(ValueDigitsLower.charAt(digit2));
        }
        if ((bits & 0x04) == 0) {
          builder.append(ValueDigits.charAt(digit3));
        } else {
          builder.append(ValueDigitsLower.charAt(digit3));
        }
        if ((bits & 0x08) == 0) {
          builder.append(ValueDigits.charAt(digit4));
        } else {
          builder.append(ValueDigitsLower.charAt(digit4));
        }
        int digits = (((((digit * radix) + digit2) *
          radix) + digit3) * radix) + digit4;
        bv = bv.Multiply(radixpow4);
        EInteger bigintTmp = EInteger.FromInt32(digits);
        bv = bv.Add(bigintTmp);
      }
      for (int i = count; i < numDigits; ++i) {
        int digit = rand.UniformInt(radix);
        if (rand.UniformInt(2) == 0) {
          builder.append(ValueDigits.charAt(digit));
        } else {
          builder.append(ValueDigitsLower.charAt(digit));
        }
        bv = bv.Multiply(radixpow1);
        EInteger bigintTmp = EInteger.FromInt32(digit);
        bv = bv.Add(bigintTmp);
      }
      if (negative) {
        bv = bv.Negate();
      }
      sabi.bigintValue = bv;
      sabi.stringValue = builder.toString();
      return sabi;
    }
  }
