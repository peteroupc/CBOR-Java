package com.upokecenter.cbor;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  final class FastInteger2 {
    private static final class MutableNumber {
      private int[] data;
      private int wordCount;

      MutableNumber(int val) {
        if (val < 0) {
          throw new IllegalArgumentException("val (" + val + ") is less than " + "0 ");
        }
        this.data = new int[4];
        this.wordCount = (val == 0) ? 0 : 1;
        this.data[0] = val;
      }

      EInteger ToEInteger() {
        if (this.wordCount == 1 && (this.data[0] >> 31) == 0) {
          return EInteger.FromInt64((int)this.data[0]);
        }
        byte[] bytes = new byte[(this.wordCount * 4) + 1];
        for (int i = 0; i < this.wordCount; ++i) {
          bytes[i * 4] = (byte)(this.data[i] & 0xff);
          bytes[(i * 4) + 1] = (byte)((this.data[i] >> 8) & 0xff);
          bytes[(i * 4) + 2] = (byte)((this.data[i] >> 16) & 0xff);
          bytes[(i * 4) + 3] = (byte)((this.data[i] >> 24) & 0xff);
        }
        bytes[bytes.length - 1] = (byte)0;
        return EInteger.FromBytes(bytes, true);
      }

      int[] GetLastWordsInternal(int numWords32Bit) {
        int[] ret = new int[numWords32Bit];
        System.arraycopy(this.data, 0, ret, 0, Math.min(numWords32Bit, this.wordCount));
        return ret;
      }

      boolean CanFitInInt32() {
        return this.wordCount == 0 || (this.wordCount == 1 && (this.data[0] >>
        31) == 0);
      }

      int ToInt32() {
        return this.wordCount == 0 ? 0 : this.data[0];
      }

      MutableNumber Multiply(int multiplicand) {
        if (multiplicand < 0) {
          throw new IllegalArgumentException("multiplicand (" + multiplicand +
            ") is less than " + "0 ");
        }
        if (multiplicand != 0) {
          int carry = 0;
          if (this.wordCount == 0) {
            if (this.data.length == 0) {
              this.data = new int[4];
            }
            this.data[0] = 0;
            this.wordCount = 1;
          }
          int result0, result1, result2, result3;
          if (multiplicand < 65536) {
            for (int i = 0; i < this.wordCount; ++i) {
              int x0 = this.data[i];
              int x1 = x0;
              int y0 = multiplicand;
              x0 &= 65535;
              x1 = (x1 >> 16) & 65535;
              int temp = (x0 * y0);  // a * c
              result1 = (temp >> 16) & 65535;
              result0 = temp & 65535;
              result2 = 0;
              temp = (x1 * y0);  // b * c
              result2 += (temp >> 16) & 65535;
              result1 += temp & 65535;
              result2 += (result1 >> 16) & 65535;
              result1 &= 65535;
              result3 = (result2 >> 16) & 65535;
              result2 &= 65535;
              // Add carry
              x0 = ((int)(result0 | (result1 << 16)));
              x1 = ((int)(result2 | (result3 << 16)));
              int x2 = (x0 + carry);
              if (((x2 >> 31) == (x0 >> 31)) ? ((x2 & Integer.MAX_VALUE) < (x0 &
              Integer.MAX_VALUE)) : ((x2 >> 31) == 0)) {
                // Carry in addition
                x1 = (x1 + 1);
              }
              this.data[i] = x2;
              carry = x1;
            }
          } else {
            for (int i = 0; i < this.wordCount; ++i) {
              int x0 = this.data[i];
              int x1 = x0;
              int y0 = multiplicand;
              int y1 = y0;
              x0 &= 65535;
              y0 &= 65535;
              x1 = (x1 >> 16) & 65535;
              y1 = (y1 >> 16) & 65535;
              int temp = (x0 * y0);  // a * c
              result1 = (temp >> 16) & 65535;
              result0 = temp & 65535;
              temp = (x0 * y1);  // a * d
              result2 = (temp >> 16) & 65535;
              result1 += temp & 65535;
              result2 += (result1 >> 16) & 65535;
              result1 &= 65535;
              temp = (x1 * y0);  // b * c
              result2 += (temp >> 16) & 65535;
              result1 += temp & 65535;
              result2 += (result1 >> 16) & 65535;
              result1 &= 65535;
              result3 = (result2 >> 16) & 65535;
              result2 &= 65535;
              temp = (x1 * y1);  // b * d
              result3 += (temp >> 16) & 65535;
              result2 += temp & 65535;
              result3 += (result2 >> 16) & 65535;
              result2 &= 65535;
              // Add carry
              x0 = ((int)(result0 | (result1 << 16)));
              x1 = ((int)(result2 | (result3 << 16)));
              int x2 = (x0 + carry);
              if (((x2 >> 31) == (x0 >> 31)) ? ((x2 & Integer.MAX_VALUE) < (x0 &
              Integer.MAX_VALUE)) : ((x2 >> 31) == 0)) {
                // Carry in addition
                x1 = (x1 + 1);
              }
              this.data[i] = x2;
              carry = x1;
            }
          }
          if (carry != 0) {
            if (this.wordCount >= this.data.length) {
              int[] newdata = new int[this.wordCount + 20];
              System.arraycopy(this.data, 0, newdata, 0, this.data.length);
              this.data = newdata;
            }
            this.data[this.wordCount] = carry;
            ++this.wordCount;
          }
          // Calculate the correct data length
          while (this.wordCount != 0 && this.data[this.wordCount - 1] == 0) {
            --this.wordCount;
          }
        } else {
          if (this.data.length > 0) {
            this.data[0] = 0;
          }
          this.wordCount = 0;
        }
        return this;
      }

      final int signum() {
          return this.wordCount == 0 ? 0 : 1;
        }

      MutableNumber SubtractInt(int other) {
        if (other < 0) {
     throw new IllegalArgumentException("other (" + other + ") is less than " +
            "0 ");
        }
      if (other != 0) {
          {
            // Ensure a length of at least 1
            if (this.wordCount == 0) {
              if (this.data.length == 0) {
                this.data = new int[4];
              }
              this.data[0] = 0;
              this.wordCount = 1;
            }
            int borrow;
            int u;
            int a = this.data[0];
            u = a - other;
            borrow = ((((a >> 31) == (u >> 31)) ?
                    ((a & Integer.MAX_VALUE) < (u & Integer.MAX_VALUE)) :
                    ((a >> 31) == 0)) || (a == u && other != 0)) ? 1 : 0;
            this.data[0] = (int)u;
            if (borrow != 0) {
              for (int i = 1; i < this.wordCount; ++i) {
                u = this.data[i] - borrow;
                borrow = (((this.data[i] >> 31) == (u >> 31)) ?
                ((this.data[i] & Integer.MAX_VALUE) < (u & Integer.MAX_VALUE)) :
                    ((this.data[i] >> 31) == 0)) ? 1 : 0;
                this.data[i] = (int)u;
              }
            }
            // Calculate the correct data length
            while (this.wordCount != 0 && this.data[this.wordCount - 1] == 0) {
              --this.wordCount;
            }
          }
        }
        return this;
      }

      MutableNumber Subtract(MutableNumber other) {
        {
          {
       // System.out.println("" + this.data.length + " " +
             // (other.data.length));
            int neededSize = (this.wordCount > other.wordCount) ?
            this.wordCount : other.wordCount;
            if (this.data.length < neededSize) {
              int[] newdata = new int[neededSize + 20];
              System.arraycopy(this.data, 0, newdata, 0, this.data.length);
              this.data = newdata;
            }
            neededSize = (this.wordCount < other.wordCount) ? this.wordCount :
            other.wordCount;
            int u = 0;
            int borrow = 0;
            for (int i = 0; i < neededSize; ++i) {
              int a = this.data[i];
              u = (a - other.data[i]) - borrow;
              borrow = ((((a >> 31) == (u >> 31)) ? ((a & Integer.MAX_VALUE) <
              (u & Integer.MAX_VALUE)) :
                    ((a >> 31) == 0)) || (a == u && other.data[i] !=
                    0)) ? 1 : 0;
              this.data[i] = (int)u;
            }
            if (borrow != 0) {
              for (int i = neededSize; i < this.wordCount; ++i) {
                int a = this.data[i];
                u = (a - other.data[i]) - borrow;
                borrow = ((((a >> 31) == (u >> 31)) ? ((a & Integer.MAX_VALUE) <
                (u & Integer.MAX_VALUE)) :
                    ((a >> 31) == 0)) || (a == u && other.data[i] !=
                    0)) ? 1 : 0;
                this.data[i] = (int)u;
              }
            }
            // Calculate the correct data length
            while (this.wordCount != 0 && this.data[this.wordCount - 1] == 0) {
              --this.wordCount;
            }
            return this;
          }
        }
      }

       MutableNumber Add(int augend) {
        if (augend < 0) {
   throw new IllegalArgumentException("augend (" + augend + ") is less than " +
            "0 ");
        }
        {
        if (augend != 0) {
          int carry = 0;
          // Ensure a length of at least 1
          if (this.wordCount == 0) {
            if (this.data.length == 0) {
              this.data = new int[4];
            }
            this.data[0] = 0;
            this.wordCount = 1;
          }
          for (int i = 0; i < this.wordCount; ++i) {
            int u;
            int a = this.data[i];
            u = (a + augend) + carry;
            carry = ((((u >> 31) == (a >> 31)) ? ((u & Integer.MAX_VALUE) < (a &
            Integer.MAX_VALUE)) :
                    ((u >> 31) == 0)) || (u == a && augend != 0)) ? 1 : 0;
            this.data[i] = u;
            if (carry == 0) {
              return this;
            }
            augend = 0;
          }
          if (carry != 0) {
            if (this.wordCount >= this.data.length) {
              int[] newdata = new int[this.wordCount + 20];
              System.arraycopy(this.data, 0, newdata, 0, this.data.length);
              this.data = newdata;
            }
            this.data[this.wordCount] = carry;
            ++this.wordCount;
          }
        }
        // Calculate the correct data length
        while (this.wordCount != 0 && this.data[this.wordCount - 1] == 0) {
          --this.wordCount;
        }
        return this;
      }
    }
    }

    private int smallValue;  // if integerMode is 0
    private MutableNumber mnum;  // if integerMode is 1
    private EInteger largeValue;  // if integerMode is 2
    private int integerMode;

    FastInteger2(int value) {
      this.smallValue = value;
    }

    int AsInt32() {
      switch (this.integerMode) {
        case 0:
          return this.smallValue;
        case 1:
          return this.mnum.ToInt32();
        case 2:
          return this.largeValue.ToInt32Checked();
        default: throw new IllegalStateException();
      }
    }

    static EInteger WordsToEInteger(int[] words) {
      int wordCount = words.length;
      if (wordCount == 1 && (words[0] >> 31) == 0) {
        return EInteger.FromInt64((int)words[0]);
      }
      byte[] bytes = new byte[(wordCount * 4) + 1];
      for (int i = 0; i < wordCount; ++i) {
        bytes[(i * 4) + 0] = (byte)(words[i] & 0xff);
        bytes[(i * 4) + 1] = (byte)((words[i] >> 8) & 0xff);
        bytes[(i * 4) + 2] = (byte)((words[i] >> 16) & 0xff);
        bytes[(i * 4) + 3] = (byte)((words[i] >> 24) & 0xff);
      }
      bytes[bytes.length - 1] = (byte)0;
      return EInteger.FromBytes(bytes, true);
    }

    FastInteger2 SetInt(int val) {
      this.smallValue = val;
      this.integerMode = 0;
      return this;
    }

    /**
     * Not documented yet.
     * @param val The parameter {@code val} is not documented yet.
     * @return A FastInteger2 object.
     */
    FastInteger2 Multiply(int val) {
      if (val == 0) {
        this.smallValue = 0;
        this.integerMode = 0;
      } else {
        switch (this.integerMode) {
          case 0:
            boolean apos = this.smallValue > 0L;
            boolean bpos = val > 0L;
            if (
              (apos && ((!bpos && (Integer.MIN_VALUE / this.smallValue) > val) ||
                    (bpos && this.smallValue > (Integer.MAX_VALUE / val)))) ||
              (!apos && ((!bpos && this.smallValue != 0L &&
                    (Integer.MAX_VALUE / this.smallValue) > val) ||
                    (bpos && this.smallValue < (Integer.MIN_VALUE / val))))) {
              // would overflow, convert to large
              if (apos && bpos) {
                // if both operands are nonnegative
                // convert to mutable big integer
                this.integerMode = 1;
                this.mnum = new MutableNumber(this.smallValue);
                this.mnum.Multiply(val);
              } else {
                // if either operand is negative
                // convert to big integer
                this.integerMode = 2;
                this.largeValue = EInteger.FromInt32(this.smallValue);
                this.largeValue = this.largeValue.Multiply(EInteger.FromInt32(val));
              }
            } else {
              smallValue *= val;
            }
            break;
          case 1:
            if (val < 0) {
              this.integerMode = 2;
              this.largeValue = this.mnum.ToEInteger();
              this.largeValue = this.largeValue.Multiply(EInteger.FromInt32(val));
            } else {
              mnum.Multiply(val);
            }
            break;
          case 2:
            this.largeValue = this.largeValue.Multiply(EInteger.FromInt32(val));
            break;
          default: throw new IllegalStateException();
        }
      }
      return this;
    }

    /**
     * Not documented yet.
     * @param val The parameter {@code val} is not documented yet.
     * @return A FastInteger2 object.
     */
    FastInteger2 Subtract(FastInteger2 val) {
      EInteger valValue;
      switch (this.integerMode) {
        case 0:
          if (val.integerMode == 0) {
            int vsv = val.smallValue;
            if ((vsv < 0 && Integer.MAX_VALUE + vsv < this.smallValue) ||
                (vsv > 0 && Integer.MIN_VALUE + vsv > this.smallValue)) {
              // would overflow, convert to large
              this.integerMode = 2;
              this.largeValue = EInteger.FromInt32(this.smallValue);
              this.largeValue = this.largeValue.Subtract(EInteger.FromInt32(vsv));
            } else {
              this.smallValue -= vsv;
            }
          } else {
            integerMode = 2;
            largeValue = EInteger.FromInt32(smallValue);
            valValue = val.AsBigInteger();
            largeValue = largeValue.Subtract(valValue);
          }
          break;
        case 1:
          if (val.integerMode == 1) {
            // NOTE: Mutable numbers are
            // currently always zero or positive
            this.mnum.Subtract(val.mnum);
          } else if (val.integerMode == 0 && val.smallValue >= 0) {
            mnum.SubtractInt(val.smallValue);
          } else {
            integerMode = 2;
            largeValue = mnum.ToEInteger();
            valValue = val.AsBigInteger();
            largeValue = largeValue.Subtract(valValue);
          }
          break;
        case 2:
          valValue = val.AsBigInteger();
          this.largeValue = this.largeValue.Subtract(valValue);
          break;
        default: throw new IllegalStateException();
      }
      return this;
    }

    /**
     * Not documented yet.
     * @param val The parameter {@code val} is not documented yet.
     * @return A FastInteger2 object.
     */
    FastInteger2 SubtractInt(int val) {
      if (val == Integer.MIN_VALUE) {
        return this.AddInt(Integer.MAX_VALUE).AddInt(1);
      }
      if (this.integerMode == 0) {
        if ((val < 0 && Integer.MAX_VALUE + val < this.smallValue) ||
                (val > 0 && Integer.MIN_VALUE + val > this.smallValue)) {
          // would overflow, convert to large
          this.integerMode = 2;
          this.largeValue = EInteger.FromInt32(this.smallValue);
          this.largeValue = this.largeValue.Subtract(EInteger.FromInt32(val));
        } else {
          this.smallValue -= val;
        }
        return this;
      }
      return this.AddInt(-val);
    }

    FastInteger2 Add(FastInteger2 val) {
      EInteger valValue;
      switch (this.integerMode) {
        case 0:
          if (val.integerMode == 0) {
            if ((this.smallValue < 0 && (int)val.smallValue < Integer.MIN_VALUE
            - this.smallValue) ||
                (this.smallValue > 0 && (int)val.smallValue > Integer.MAX_VALUE
                - this.smallValue)) {
              // would overflow
              if (val.smallValue >= 0) {
                this.integerMode = 1;
                this.mnum = new MutableNumber(this.smallValue);
                this.mnum.Add(val.smallValue);
              } else {
                this.integerMode = 2;
                this.largeValue = EInteger.FromInt32(this.smallValue);
                this.largeValue = this.largeValue.Add(EInteger.FromInt64(val.smallValue));
              }
            } else {
              this.smallValue += val.smallValue;
            }
          } else {
            integerMode = 2;
            largeValue = EInteger.FromInt32(smallValue);
            valValue = val.AsBigInteger();
            largeValue = largeValue.Add(valValue);
          }
          break;
        case 1:
          if (val.integerMode == 0 && val.smallValue >= 0) {
            this.mnum.Add(val.smallValue);
          } else {
            integerMode = 2;
            largeValue = mnum.ToEInteger();
            valValue = val.AsBigInteger();
            largeValue = largeValue.Add(valValue);
          }
          break;
        case 2:
          valValue = val.AsBigInteger();
          this.largeValue = this.largeValue.Add(valValue);
          break;
        default: throw new IllegalStateException();
      }
      return this;
    }

    FastInteger2 AddInt(int val) {
      EInteger valValue;
      switch (this.integerMode) {
        case 0:
          if ((this.smallValue < 0 && (int)val < Integer.MIN_VALUE -
        this.smallValue) || (this.smallValue > 0 && (int)val >
            Integer.MAX_VALUE - this.smallValue)) {
            // would overflow
            if (val >= 0) {
              this.integerMode = 1;
              this.mnum = new MutableNumber(this.smallValue);
              this.mnum.Add(val);
            } else {
              this.integerMode = 2;
              this.largeValue = EInteger.FromInt32(this.smallValue);
              this.largeValue = this.largeValue.Add(EInteger.FromInt32(val));
            }
          } else {
            smallValue += val;
          }
          break;
        case 1:
          if (val >= 0) {
            this.mnum.Add(val);
          } else {
            integerMode = 2;
            largeValue = mnum.ToEInteger();
            valValue = EInteger.FromInt32(val);
            largeValue = largeValue.Add(valValue);
          }
          break;
        case 2:
          valValue = EInteger.FromInt32(val);
          this.largeValue = this.largeValue.Add(valValue);
          break;
        default: throw new IllegalStateException();
      }
      return this;
    }

    boolean CanFitInInt32() {
      switch (this.integerMode) {
        case 0:
          return true;
        case 1:
          return this.mnum.CanFitInInt32();
          case 2: {
            return this.largeValue.CanFitInInt32();
          }
        default:
          throw new IllegalStateException();
      }
    }

    /**
     * Gets a value not documented yet.
     * @return A value not documented yet.
     */
    final int signum() {
        switch (this.integerMode) {
          case 0:
          return (this.smallValue == 0) ? 0 : ((this.smallValue < 0) ? -1 :
              1);
          case 1:
            return this.mnum.signum();
          case 2:
            return this.largeValue.signum();
          default: return 0;
        }
      }

    EInteger AsBigInteger() {
      switch (this.integerMode) {
        case 0:
          return EInteger.FromInt32(this.smallValue);
        case 1:
          return this.mnum.ToEInteger();
        case 2:
          return this.largeValue;
        default: throw new IllegalStateException();
      }
    }
  }
