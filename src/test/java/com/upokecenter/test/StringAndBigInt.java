package com.upokecenter.test;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  final class StringAndBigInt {
    private static final String ValueDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String ValueDigitsLower =
      "0123456789abcdefghijklmnopqrstuvwxyz";

    public final String getStringValue() { return propVarstringvalue; }
private final String propVarstringvalue;

    public final EInteger getBigIntValue() { return propVarbigintvalue; }
private final EInteger propVarbigintvalue;

    private StringAndBigInt(String sv, EInteger biv) {
      this.propVarstringvalue = sv;
      this.propVarbigintvalue = biv;
    }

    public static StringAndBigInt Generate(IRandomGenExtended rand, int radix) {
      return Generate(rand, radix, 50);
    }

    public static StringAndBigInt Generate(
      IRandomGenExtended rand,
      int radix,
      int maxNumDigits) {
      if (radix < 2) {
        throw new IllegalArgumentException("radix(" + radix +
          ") is less than 2");
      }
      if (radix > 36) {
        throw new IllegalArgumentException("radix(" + radix +
          ") is more than 36");
      }
      EInteger bv = EInteger.FromInt32(0);
      int numDigits = 1 + rand.GetInt32(maxNumDigits);
      boolean negative = false;
      StringBuilder builder = new StringBuilder();
      if (rand.GetInt32(2) == 0) {
        builder.append('-');
        negative = true;
      }
      int radixpowint = radix * radix * radix * radix;
      EInteger radixpow4 = EInteger.FromInt32(radixpowint);
      EInteger radixpow1 = EInteger.FromInt32(radix);
      int count = 0;
      for (int i = 0; i < numDigits - 4; i += 4) {
        int digitvalues = rand.GetInt32(radixpowint);
        int digit = digitvalues % radix;
        digitvalues /= radix;
        int digit2 = digitvalues % radix;
        digitvalues /= radix;
        int digit3 = digitvalues % radix;
        digitvalues /= radix;
        int digit4 = digitvalues % radix;
        count += 4;
        int bits = rand.GetInt32(16);
        builder = (bits & 0x01) == 0 ? builder.append(ValueDigits.charAt(digit)) :
          builder.append(ValueDigitsLower.charAt(digit));
        builder = (bits & 0x02) == 0 ? builder.append(ValueDigits.charAt(digit2)) :
          builder.append(ValueDigitsLower.charAt(digit2));
        builder = (bits & 0x04) == 0 ? builder.append(ValueDigits.charAt(digit3)) :
          builder.append(ValueDigitsLower.charAt(digit3));
        builder = (bits & 0x08) == 0 ? builder.append(ValueDigits.charAt(digit4)) :
          builder.append(ValueDigitsLower.charAt(digit4));
        int digits = (((((digit * radix) + digit2) *
          radix) + digit3) * radix) + digit4;
        bv = bv.Multiply(radixpow4);
        EInteger bigintTmp = EInteger.FromInt32(digits);
        bv = bv.Add(bigintTmp);
      }
      for (int i = count; i < numDigits; ++i) {
        int digit = rand.GetInt32(radix);
        builder = rand.GetInt32(2) == 0 ? builder.append(ValueDigits.charAt(digit)) :
          builder.append(ValueDigitsLower.charAt(digit));
        bv = bv.Multiply(radixpow1);
        EInteger bigintTmp = EInteger.FromInt32(digit);
        bv = bv.Add(bigintTmp);
      }
      if (negative) {
        bv = bv.Negate();
      }
      return new StringAndBigInt(builder.toString(), bv);
    }
  }
