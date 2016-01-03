package com.upokecenter.numbers;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

  public final class DigitShiftAccumulator implements IShiftAccumulator {
    private int bitLeftmost;

    /**
     * Gets a value indicating whether the last discarded digit was set.
     * @return True if the last discarded digit was set; otherwise, false.
     */
    public final int getLastDiscardedDigit() {
        return this.bitLeftmost;
      }

    private int bitsAfterLeftmost;

    /**
     * Gets a value indicating whether any of the discarded digits to the right of
     * the last one was set.
     * @return True if any of the discarded digits to the right of the last one was
     * set; otherwise, false.
     */
    public final int getOlderDiscardedDigits() {
        return this.bitsAfterLeftmost;
      }

    private EInteger shiftedBigInt;
    private FastInteger knownBitLength;

    public FastInteger GetDigitLength() {
      this.knownBitLength = (this.knownBitLength == null) ? (this.CalcKnownDigitLength()) : this.knownBitLength;
      return FastInteger.Copy(this.knownBitLength);
    }

    private int shiftedSmall;
    private boolean isSmall;
    private FastInteger discardedBitCount;

    public final FastInteger getDiscardedDigitCount() {
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        return this.discardedBitCount;
      }

    private static final EInteger ValueTen = EInteger.FromInt64(10);

    /**
     * Gets the current integer after shifting.
     * @return The current integer after shifting.
     */
    public final EInteger getShiftedInt() {
        return this.isSmall ? (EInteger.FromInt64(this.shiftedSmall)) :
        this.shiftedBigInt;
      }

    public DigitShiftAccumulator(
EInteger bigint,
int lastDiscarded,
int olderDiscarded) {
      if (bigint.canFitInInt()) {
        this.shiftedSmall = bigint.AsInt32Checked();
        if (this.shiftedSmall < 0) {
          throw new IllegalArgumentException("shiftedSmall (" + this.shiftedSmall +
            ") is less than 0");
        }
        this.isSmall = true;
      } else {
        this.shiftedBigInt = bigint;
        this.isSmall = false;
      }
      this.bitsAfterLeftmost = (olderDiscarded != 0) ? 1 : 0;
      this.bitLeftmost = lastDiscarded;
    }

    private static int FastParseLong(String str, int offset, int length) {
      // Assumes the String is length 9 or less and contains
      // only the digits '0' through '9'
      if (length > 9) {
   throw new IllegalArgumentException("length (" + length + ") is more than " +
          "9 ");
      }
      int ret = 0;
      for (int i = 0; i < length; ++i) {
        int digit = (int)(str.charAt(offset + i) - '0');
        ret *= 10;
        ret += digit;
      }
      return ret;
    }

    public final FastInteger getShiftedIntFast() {
        return this.isSmall ? (new FastInteger(this.shiftedSmall)) :
        FastInteger.FromBig(this.shiftedBigInt);
      }

    public void ShiftRight(FastInteger fastint) {
      if (fastint == null) {
        throw new NullPointerException("fastint");
      }
      if (fastint.CanFitInInt32()) {
        int fi = fastint.AsInt32();
        if (fi < 0) {
          return;
        }
        this.ShiftRightInt(fi);
      } else {
        if (fastint.signum() <= 0) {
          return;
        }
        EInteger bi = fastint.AsBigInteger();
        while (bi.signum() > 0) {
          int count = 1000000;
          if (bi.compareTo(EInteger.FromInt64(1000000)) < 0) {
            count = bi.AsInt32Checked();
          }
          this.ShiftRightInt(count);
          bi = bi.Subtract(EInteger.FromInt64(count));
          if (this.isSmall ? this.shiftedSmall == 0 :
          this.shiftedBigInt.isZero()) {
            break;
          }
        }
      }
    }

    public void TruncateRight(FastInteger fastint) {
      if (fastint == null) {
        throw new NullPointerException("fastint");
      }
      if (!this.isSmall) {
        this.ShiftRight(fastint);
      }
      if (fastint.CanFitInInt32()) {
        int fi = fastint.AsInt32();
        if (fi < 0) {
          return;
        }
        this.TruncateRightSmall(fi);
      } else {
        this.ShiftRight(fastint);
      }
    }

    private void ShiftRightBig(int digits) {
      if (digits <= 0) {
        return;
      }
      if (this.shiftedBigInt.isZero()) {
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.AddInt(digits);
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.bitLeftmost = 0;
        this.knownBitLength = new FastInteger(1);
        return;
      }
      // System.out.println("digits=" + digits);
      if (digits == 1) {
        EInteger bigrem;
        EInteger bigquo;
{
EInteger[] divrem = this.shiftedBigInt.DivRem(EInteger.FromInt64(10));
bigquo = divrem[0];
bigrem = divrem[1]; }
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.bitLeftmost = bigrem.AsInt32Checked();
        this.shiftedBigInt = bigquo;
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.AddInt(digits);
        if (this.knownBitLength != null) {
          if (bigquo.isZero()) {
            this.knownBitLength.SetInt(0);
          } else {
            this.knownBitLength.Decrement();
          }
        }
        return;
      }
      int startCount = Math.min(4, digits - 1);
      if (startCount > 0) {
        EInteger bigrem;
        EInteger radixPower = DecimalUtility.FindPowerOfTen(startCount);
        EInteger bigquo;
{
EInteger[] divrem = this.shiftedBigInt.DivRem(radixPower);
bigquo = divrem[0];
bigrem = divrem[1]; }
        if (!bigrem.isZero()) {
          this.bitsAfterLeftmost |= 1;
        }
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.shiftedBigInt = bigquo;
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.AddInt(startCount);
        digits -= startCount;
        if (this.shiftedBigInt.isZero()) {
          // Shifted all the way to 0
          this.isSmall = true;
          this.shiftedSmall = 0;
          this.knownBitLength = new FastInteger(1);
          this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
          this.bitLeftmost = 0;
          return;
        }
      }
      if (digits == 1) {
        EInteger bigrem;
        EInteger bigquo;
{
EInteger[] divrem = this.shiftedBigInt.DivRem(ValueTen);
bigquo = divrem[0];
bigrem = divrem[1]; }
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.bitLeftmost = bigrem.AsInt32Checked();
        this.shiftedBigInt = bigquo;
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.Increment();
        this.knownBitLength = (this.knownBitLength != null) ?
        this.knownBitLength.Decrement() : this.GetDigitLength();
        this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
        return;
      }
      this.knownBitLength = (this.knownBitLength == null) ? (this.GetDigitLength()) : this.knownBitLength;
      if (new FastInteger(digits).Decrement().compareTo(this.knownBitLength)
      >= 0) {
        // Shifting more bits than available
        this.bitsAfterLeftmost |= this.shiftedBigInt.isZero() ? 0 : 1;
        this.isSmall = true;
        this.shiftedSmall = 0;
        this.knownBitLength = new FastInteger(1);
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.AddInt(digits);
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.bitLeftmost = 0;
        return;
      }
      if (this.shiftedBigInt.canFitInInt()) {
        this.isSmall = true;
        this.shiftedSmall = this.shiftedBigInt.AsInt32Checked();
        this.ShiftRightSmall(digits);
        return;
      }
      String str = this.shiftedBigInt.toString();
      // NOTE: Will be 1 if the value is 0
      int digitLength = str.length();
      int bitDiff = 0;
      if (digits > digitLength) {
        bitDiff = digits - digitLength;
      }
      this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
      this.discardedBitCount.AddInt(digits);
      this.bitsAfterLeftmost |= this.bitLeftmost;
      int digitShift = Math.min(digitLength, digits);
      if (digits >= digitLength) {
        this.isSmall = true;
        this.shiftedSmall = 0;
        this.knownBitLength = new FastInteger(1);
      } else {
        int newLength = (int)(digitLength - digitShift);
        this.knownBitLength = new FastInteger(newLength);
        if (newLength <= 9) {
          // Fits in a small number
          this.isSmall = true;
          this.shiftedSmall = FastParseLong(str, 0, newLength);
        } else {
          this.shiftedBigInt = EInteger.FromSubstring(str, 0, newLength);
        }
      }
      for (int i = str.length() - 1; i >= 0; --i) {
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.bitLeftmost = (int)(str.charAt(i) - '0');
        --digitShift;
        if (digitShift <= 0) {
          break;
        }
      }
      this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
      if (bitDiff > 0) {
        // Shifted more digits than the digit length
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.bitLeftmost = 0;
      }
    }

    private void ShiftToBitsBig(int digits) {
      // Shifts a number until it reaches the given number of digits,
      // gathering information on whether the last digit discarded is set
      // and whether the discarded digits to the right of that digit are set.
      // Assumes that the big integer being shifted is positive.
      if (this.knownBitLength != null) {
        if (this.knownBitLength.CompareToInt(digits) <= 0) {
          return;
        }
      }
      String str;
      this.knownBitLength = (this.knownBitLength == null) ? (this.GetDigitLength()) : this.knownBitLength;
      if (this.knownBitLength.CompareToInt(digits) <= 0) {
        return;
      }
      FastInteger digitDiff =
      FastInteger.Copy(this.knownBitLength).SubtractInt(digits);
      if (digitDiff.CompareToInt(1) == 0) {
        EInteger bigrem;
        EInteger bigquo;
{
EInteger[] divrem = this.shiftedBigInt.DivRem(ValueTen);
bigquo = divrem[0];
bigrem = divrem[1]; }
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.bitLeftmost = bigrem.AsInt32Checked();
        this.shiftedBigInt = bigquo;
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.Add(digitDiff);
        this.knownBitLength.Subtract(digitDiff);
        this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
        return;
      }
      if (digitDiff.CompareToInt(9) <= 0) {
        EInteger bigrem;
        int diffInt = digitDiff.AsInt32();
        EInteger radixPower = DecimalUtility.FindPowerOfTen(diffInt);
        EInteger bigquo;
{
EInteger[] divrem = this.shiftedBigInt.DivRem(radixPower);
bigquo = divrem[0];
bigrem = divrem[1]; }
        int rem = bigrem.AsInt32Checked();
        this.bitsAfterLeftmost |= this.bitLeftmost;
        for (int i = 0; i < diffInt; ++i) {
          if (i == diffInt - 1) {
            this.bitLeftmost = rem % 10;
          } else {
            this.bitsAfterLeftmost |= rem % 10;
            rem /= 10;
          }
        }
        this.shiftedBigInt = bigquo;
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.Add(digitDiff);
        this.knownBitLength.Subtract(digitDiff);
        this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
        return;
      }
      if (digitDiff.CompareToInt(Integer.MAX_VALUE) <= 0) {
        EInteger bigrem;
        EInteger radixPower =
        DecimalUtility.FindPowerOfTen(digitDiff.AsInt32() - 1);
        EInteger bigquo;
{
EInteger[] divrem = this.shiftedBigInt.DivRem(radixPower);
bigquo = divrem[0];
bigrem = divrem[1]; }
        this.bitsAfterLeftmost |= this.bitLeftmost;
        if (!bigrem.isZero()) {
          this.bitsAfterLeftmost |= 1;
        }
        {
          EInteger bigquo2;
{
EInteger[] divrem = bigquo.DivRem(ValueTen);
bigquo2 = divrem[0];
bigrem = divrem[1]; }
          this.bitLeftmost = bigrem.AsInt32Checked();
          this.shiftedBigInt = bigquo2;
        }
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.Add(digitDiff);
        this.knownBitLength.Subtract(digitDiff);
        this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
        return;
      }
      str = this.shiftedBigInt.toString();
      // NOTE: Will be 1 if the value is 0
      int digitLength = str.length();
      this.knownBitLength = new FastInteger(digitLength);
      // Shift by the difference in digit length
      if (digitLength > digits) {
        int digitShift = digitLength - digits;
        this.knownBitLength.SubtractInt(digitShift);
        int newLength = (int)(digitLength - digitShift);
        // System.out.println("dlen= " + digitLength + " dshift=" +
        // digitShift + " newlen= " + newLength);
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        if (digitShift <= Integer.MAX_VALUE) {
          this.discardedBitCount.AddInt((int)digitShift);
        } else {
          this.discardedBitCount.AddBig(EInteger.FromInt64(digitShift));
        }
        for (int i = str.length() - 1; i >= 0; --i) {
          this.bitsAfterLeftmost |= this.bitLeftmost;
          this.bitLeftmost = (int)(str.charAt(i) - '0');
          --digitShift;
          if (digitShift <= 0) {
            break;
          }
        }
        if (newLength <= 9) {
          this.isSmall = true;
          this.shiftedSmall = FastParseLong(str, 0, newLength);
        } else {
          this.shiftedBigInt = EInteger.FromSubstring(str, 0, newLength);
        }
        this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
      }
    }

    public void ShiftRightInt(int digits) {
      // <summary>Shifts a number to the right, gathering information on
      // whether the last digit discarded is set and whether the discarded
      // digits to the right of that digit are set. Assumes that the big
      // integer being shifted is positive.</summary>
      if (this.isSmall) {
        this.ShiftRightSmall(digits);
      } else {
        this.ShiftRightBig(digits);
      }
    }

    private static final int[] ValueTenPowers = {
      1, 10, 100, 1000, 10000, 100000,
      1000000, 10000000, 100000000
    };

    private void ShiftRightSmall(int digits) {
      if (digits <= 0) {
        return;
      }
      if (this.shiftedSmall == 0) {
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.AddInt(digits);
        this.bitsAfterLeftmost |= this.bitLeftmost;
        this.bitLeftmost = 0;
        this.knownBitLength = new FastInteger(1);
        return;
      }
      if (digits >= 2 && digits <= 8) {
        if (this.shiftedSmall >= ValueTenPowers[digits]) {
         int bigPower = ValueTenPowers[digits];
         int smallPower = ValueTenPowers[digits - 1];
       this.discardedBitCount = (this.discardedBitCount == null) ? ((new
           FastInteger(0))) : this.discardedBitCount;
         this.discardedBitCount.AddInt(digits);
         int div = this.shiftedSmall / bigPower;
         int rem = this.shiftedSmall - (div * bigPower);
         int rem2 = rem / smallPower;
         this.bitLeftmost = rem2;
         this.bitsAfterLeftmost |= rem - (rem2 * smallPower);
         this.shiftedSmall = div;
         this.knownBitLength = (div < 10) ? (new FastInteger(1)) :
           this.CalcKnownDigitLength();
         return;
        } else if (this.shiftedSmall >= ValueTenPowers[digits - 1]) {
         int smallPower = ValueTenPowers[digits - 1];
         if (this.discardedBitCount != null) {
           this.discardedBitCount.AddInt(digits);
         } else {
           this.discardedBitCount = new FastInteger(digits);
         }
         int rem = this.shiftedSmall;
         int rem2 = rem / smallPower;
         this.bitLeftmost = rem2;
         this.bitsAfterLeftmost |= rem - (rem2 * smallPower);
         this.shiftedSmall = 0;
         this.knownBitLength = new FastInteger(1);
         return;
        } else {
         if (this.discardedBitCount != null) {
           this.discardedBitCount.AddInt(digits);
         } else {
           this.discardedBitCount = new FastInteger(digits);
         }
         int rem = this.shiftedSmall;
         this.bitLeftmost = 0;
         this.bitsAfterLeftmost |= rem;
         this.shiftedSmall = 0;
         this.knownBitLength = new FastInteger(1);
         return;
        }
      }
      int kb = 0;
      int tmp = this.shiftedSmall;
      // DebugUtility.Log("" + this.shiftedSmall + ", " + digits);
      while (tmp > 0) {
        ++kb;
        tmp /= 10;
      }
      // Make sure digit length is 1 if value is 0
      if (kb == 0) {
        ++kb;
      }
      this.knownBitLength = new FastInteger(kb);
         if (this.discardedBitCount != null) {
           this.discardedBitCount.AddInt(digits);
         } else {
           this.discardedBitCount = new FastInteger(digits);
         }
      while (digits > 0) {
        if (this.shiftedSmall == 0) {
          this.bitsAfterLeftmost |= this.bitLeftmost;
          this.bitLeftmost = 0;
          this.knownBitLength = new FastInteger(0);
          break;
        } else {
          int digit = (int)(this.shiftedSmall % 10);
          this.bitsAfterLeftmost |= this.bitLeftmost;
          this.bitLeftmost = digit;
          --digits;
          this.shiftedSmall /= 10;
          this.knownBitLength.Decrement();
        }
      }
      this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
    }

    private void TruncateRightSmall(int digits) {
      if (digits <= 0) {
        return;
      }
      if (this.shiftedSmall == 0 || digits >= 11) {
        this.discardedBitCount = (this.discardedBitCount == null) ? ((new FastInteger(0))) : this.discardedBitCount;
        this.discardedBitCount.AddInt(digits);
        this.bitsAfterLeftmost = 0;
        this.bitLeftmost = 0;
        this.shiftedSmall = 0;
        this.knownBitLength = new FastInteger(1);
        return;
      }
      if (digits >= 1 && digits <= 8) {
        if (this.shiftedSmall >= ValueTenPowers[digits]) {
         int bigPower = ValueTenPowers[digits];
         int smallPower = ValueTenPowers[digits - 1];
         if (this.discardedBitCount != null) {
           this.discardedBitCount.AddInt(digits);
         } else {
           this.discardedBitCount = new FastInteger(digits);
         }
         this.shiftedSmall /= bigPower;
         this.bitsAfterLeftmost = 0;
         this.bitLeftmost = 0;
         this.knownBitLength = (this.shiftedSmall < 10) ? (new
           FastInteger(1)) : this.CalcKnownDigitLength();
         return;
        } else {
         int smallPower = ValueTenPowers[digits - 1];
         if (this.discardedBitCount != null) {
           this.discardedBitCount.AddInt(digits);
         } else {
           this.discardedBitCount = new FastInteger(digits);
         }
         this.bitLeftmost = 0;
         this.bitsAfterLeftmost = 0;
         this.shiftedSmall = 0;
         this.knownBitLength = new FastInteger(1);
         return;
        }
      }
      this.ShiftRightSmall(digits);
    }

    public void ShiftToDigits(FastInteger bits) {
      if (bits.CanFitInInt32()) {
        int intval = bits.AsInt32();
        if (intval < 0) {
   throw new IllegalArgumentException("intval (" + intval + ") is less than " +
            "0");
        }
        this.ShiftToDigitsInt(intval);
      } else {
        if (bits.signum() < 0) {
          throw new IllegalArgumentException("bits's sign (" + bits.signum() +
            ") is less than 0");
        }
        this.knownBitLength = this.CalcKnownDigitLength();
        EInteger bigintDiff = this.knownBitLength.AsBigInteger();
        EInteger bitsBig = bits.AsBigInteger();
        bigintDiff = bigintDiff.Subtract(bitsBig);
        if (bigintDiff.signum() > 0) {
          // current length is greater than the
          // desired bit length
          this.ShiftRight(FastInteger.FromBig(bigintDiff));
        }
      }
    }

    public void ShiftToDigitsInt(int digits) {
      // <summary>Shifts a number until it reaches the given number of
      // digits, gathering information on whether the last digit discarded
      // is set and whether the discarded digits to the right of that digit
      // are set. Assumes that the big integer being shifted is
      // positive.</summary>
      if (this.isSmall) {
        this.ShiftToBitsSmall(digits);
      } else {
        this.ShiftToBitsBig(digits);
      }
    }

    private FastInteger CalcKnownDigitLength() {
      if (this.isSmall) {
        int kb = 0;
        int v2 = this.shiftedSmall;
        if (v2 < 100000) {
          kb = ((v2 >= 10000) ? 5 : ((v2 >= 1000) ? 4 : ((v2 >= 100) ?
          3 : ((v2 >= 10) ? 2 : 1))));
        } else {
          kb = (v2 >= 1000000000) ? 10 : ((v2 >= 100000000) ? 9 : ((v2 >=
          10000000) ? 8 : ((v2 >= 1000000) ? 7 : 6)));
        }
        return new FastInteger(kb);
      }
      return new FastInteger(this.shiftedBigInt.getDigitCount());
    }

    private void ShiftToBitsSmall(int digits) {
      int kb = 0;
      int v2 = this.shiftedSmall;
      kb = (v2 >= 1000000000) ? 10 : ((v2 >= 100000000) ? 9 : ((v2 >=
      10000000) ? 8 : ((v2 >= 1000000) ? 7 : ((v2 >= 100000) ? 6 : ((v2 >=
      10000) ? 5 : ((v2 >= 1000) ? 4 : ((v2 >= 100) ? 3 : ((v2 >= 10) ? 2 :
      1))))))));
      this.knownBitLength = new FastInteger(kb);
      if (kb > digits) {
        int digitShift = (int)(kb - digits);
        int newLength = (int)(kb - digitShift);
        this.knownBitLength = new FastInteger(Math.max(1, newLength));
        this.discardedBitCount = this.discardedBitCount != null ?
          this.discardedBitCount.AddInt(digitShift) :
          (new FastInteger(digitShift));
        for (int i = 0; i < digitShift; ++i) {
          int digit = (int)(this.shiftedSmall % 10);
          this.shiftedSmall /= 10;
          this.bitsAfterLeftmost |= this.bitLeftmost;
          this.bitLeftmost = digit;
        }
        this.bitsAfterLeftmost = (this.bitsAfterLeftmost != 0) ? 1 : 0;
      }
    }
  }
