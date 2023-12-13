package com.upokecenter.util;

  /**
   * <p>A class that adapts a random byte generator to generate random numbers in
   * a variety of statistical distributions. </p><p>The method descriptions in
   * this class assume the underlying random byte generator generates uniformly
   * distributed numbers that are independent of each other.</p> <p><b>Thread
   * safety:</b> The methods in this class are safe for concurrent use by
   * multiple threads, as long as the underlying random byte generator is as
   * well.</p>
   */
  public final class RandomGenerator implements IRandomGenExtended {
    private boolean valueHaveLastNormal;
    private IRandomGen valueIrg;
    private double valueLastNormal;
    private Object valueNormalLock = new Object();

    /**
     * Initializes a new instance of the RandomGenerator class.
     */
    public RandomGenerator() {
 this(new XorShift128Plus());
    }

    /**
     * Initializes a new instance of the RandomGenerator class.
     * @param valueIrg An IRandomGen object.
     */
    public RandomGenerator(IRandomGen valueIrg) {
      this.valueIrg = valueIrg;
    }

    /**
     * Returns either true or false, depending on the given probability.
     * @param p A probability from 0 through 1. 0 means always false, and 1 means
     * always true.
     * @return A Boolean object.
     */
    public boolean Bernoulli(double p) {
      if (p < 0) {
        throw new IllegalArgumentException("p(" + p + ") is less than 0");
      }
      if (p > 1) {
        throw new IllegalArgumentException("p(" + p + ") is more than 1");
      }
      return this.Uniform() < p;
    }

    /**
     * Returns either true or false at a 50% chance each.
     * @return A Boolean object.
     */
    public boolean Bernoulli() {
      return this.UniformInt(2) == 0;
    }

    /**
     * Conceptually, generates either 1 or 0 the given number of times, where
     * either number is equally likely, and counts the number of 1's generated.
     * @param trials The number of times to generate a random number, conceptually.
     * @return A 32-bit signed integer.
     */
    public int Binomial(int trials) {
      return this.Binomial(trials, 0.5);
    }

    public int GetBytes(byte[] bytes, int offset, int count) {
      return this.valueIrg.GetBytes(bytes, offset, count);
    }

    /**
     * Conceptually, generates either 1 or 0 the given number of times, where a 1
     * is generated at the given probability, and counts the number of 1's
     * generated.
     * @param trials The number of times to generate a random number, conceptually.
     * @param p The probability for each trial to succeed, from 0 (never) to 1
     * (always).
     * @return The number of successes in a given number of trials.
     */
    public int Binomial(int trials, double p) {
      if (p < 0) {
        throw new IllegalArgumentException("p(" + p + ") is less than 0");
      }
      if (p > 1) {
        throw new IllegalArgumentException("p(" + p + ") is more than 1");
      }
      if (trials <= -1) {
        throw new IllegalArgumentException("trials(" + trials +
          ") is not greater than " + (-1));
      }
      if (trials == 0 || p == 1.0) {
        return trials;
      }
      int count = 0;
      if (p == 0.5) {
        byte[] bytes = new byte[1];
        for (int i = 0; i < trials && i >= 0;) {
          this.valueIrg.GetBytes(bytes, 0, 1);
          int b = bytes[0];
          while (i < trials && i >= 0) {
            if ((b & 1) == 1) {
              ++count;
            }
            b >>= 1;
            ++i;
          }
        }
      } else {
        for (int i = 0; i < trials && i >= 0; ++i) {
          if (this.Uniform() < p) {
            ++count;
          }
        }
      }
      return count;
    }

    /**
     * Generates a random number that is the sum of the squares of "df"
     * normally-distributed random numbers with a mean of 0 and a standard
     * deviation of 1.
     * @param df Degrees of freedom (the number of independently chosen
     * normally-distributed numbers).
     * @return A 64-bit floating-point number.
     */
    public double ChiSquared(int df) {
      if (df <= 0) {
        throw new IllegalArgumentException("df(" + df + ") is not greater than 0");
      }
      return this.Gamma(df * 0.5, 2);
    }

    /**
     * Not documented yet.
     * @return A 64-bit floating-point number.
     */
    public double Exponential() {
      return -Math.log(1.0 - this.Uniform());
    }

    /**
     * Not documented yet.
     * @param a Another 64-bit floating-point number.
     * @param b A 64-bit floating-point number. (3).
     * @return A 64-bit floating-point number.
     */
    public double Gamma(double a, double b) {
      if (b <= 0) {
        throw new IllegalArgumentException("b(" + b + ") is not greater than 0");
      }
      return this.Gamma(a) * b;
    }

    /**
     * Not documented yet.
     * @param a Another 64-bit floating-point number.
     * @return A 64-bit floating-point number.
     */
    public double Gamma(double a) {
      if (a <= 0) {
        throw new IllegalArgumentException("a(" + a + ") is not greater than 0");
      }
      double v, x, u, x2, d, c;
      d = (a < 1 ? 1 + a : a) - (1.0 / 3.0);
      c = 1 / Math.sqrt(9 * d);
      do {
        do {
          x = this.Normal();
          v = Math.pow((c * x) + 1, 3);
        } while (v <= 0);
        u = 1.0 - this.Uniform();
        x2 = x * x;
      } while (u >= 1 - (0.0331 * x2 * x2) &&
        Math.log(u) >= (0.5 * x2) + (d * (1 - v + Math.log(v))));
      if (a < 1) {
        return d * v * Math.exp(this.Exponential() / -a);
      } else {
        return d * v;
      }
    }

    /**
     * Conceptually, generates either 1 or 0 until a 1 is generated, and counts the
     * number of 0's generated. Either number has an equal probability of being
     * generated.
     * @return The number of failures until a success happens.
     */
    public int Geometric() {
      return this.NegativeBinomial(1, 0.5);
    }

    /**
     * Conceptually, generates either 1 or 0 until a 1 is generated, and counts the
     * number of 0's generated. A 1 is generated at the given probability.
     * @param p A 64-bit floating-point number.
     * @return The number of failures until a success happens.
     */
    public int Geometric(double p) {
      return this.NegativeBinomial(1, p);
    }

    /**
     * Conceptually, given a set of tokens, some of which are labeled 1 and the
     * others labeled 0, draws "trials" tokens at random without replacement and
     * then counts the number of 1's drawn.
     * @param trials The number of tokens drawn at random without replacement.
     * @param ones The number of tokens labeled 1.
     * @param count The number of tokens labeled 1 or 0.
     * @return A 32-bit signed integer.
     */
    public int Hypergeometric(int trials, int ones, int count) {
      if (ones < 0) {
        throw new IllegalArgumentException("ones(" + ones + ") is less than 0");
      }
      if (ones > count) {
        throw new IllegalArgumentException("ones(" + ones + ") is more than " +
          count);
      }
      if (count < 0) {
        throw new IllegalArgumentException("count(" + count +
          ") is less than 0");
      }
      if (trials < 0) {
        throw new IllegalArgumentException("trials(" + trials +
          ") is less than 0");
      }
      if (trials > count) {
        throw new IllegalArgumentException("trials(" + trials +
          ") is more than " + count);
      }
      int ret = 0;
      for (int i = 0; i < trials && ones > 0; ++i) {
        if (this.UniformInt(count) < ones) {
          --ones;
          ++ret;
        }
        --count;
      }
      return ret;
    }

    /**
     * Generates a logarithmic normally-distributed number with the given mean and
     * standard deviation.
     * @param mean The desired mean.
     * @param sd Standard deviation.
     * @return A 64-bit floating-point number.
     */
    public double LogNormal(double mean, double sd) {
      return Math.exp(this.Normal(mean, sd));
    }

    /**
     * Conceptually, generates either 1 or 0 until the given number of 1's are
     * generated, and counts the number of 0's generated. A 1 is generated at the
     * given probability.
     * @param trials The number of 1's to generate before the process stops.
     * @param p The probability for each trial to succeed, from 0 (never) to 1
     * (always).
     * @return The number of 0's generated. Returns Integer.MAX_VALUE if {@code p}
     * is 0.
     */
    public int NegativeBinomial(int trials, double p) {
      if (p < 0) {
        throw new IllegalArgumentException("p(" + p + ") is less than 0");
      }
      if (p > 1) {
        throw new IllegalArgumentException("p(" + p + ") is more than 1");
      }
      if (trials <= -1) {
        throw new IllegalArgumentException("trials(" + trials +
          ") is not greater than " + (-1));
      }
      if (trials == 0 || p == 1.0) {
        return 0;
      }
      if (p == 0.0) {
        return Integer.MAX_VALUE;
      }
      int count = 0;
      if (p == 0.5) {
        byte[] bytes = new byte[1];
        while (true) {
          this.valueIrg.GetBytes(bytes, 0, 1);
          int b = bytes[0];
          for (int i = 0; i < 8; ++i) {
            if ((b & 1) == 1) {
              --trials;
              if (trials <= 0) {
                return count;
              }
            } else {
              count = (count + 1);
            }
            b >>= 1;
            ++i;
          }
        }
      } else {
        while (true) {
          if (this.Uniform() < p) {
            --trials;
            if (trials <= 0) {
              return count;
            }
          } else {
            count = (count + 1);
          }
        }
      }
    }

    /**
     * Conceptually, generates either 1 or 0 the given number of times until the
     * given number of 1's are generated, and counts the number of 0's generated.
     * Either number has an equal probability of being generated.
     * @param trials The number of 1's to generate before the process stops.
     * @return The number of 0's generated. Returns Integer.MAX_VALUE if "p" is 0.
     */
    public int NegativeBinomial(int trials) {
      return this.NegativeBinomial(trials, 0.5);
    }
    // The Normal, Exponential, Poisson, and
    // single-argument Gamma methods were adapted
    // from a third-party public-domain JavaScript file.

    /**
     * Generates a normally-distributed number with mean 0 and standard deviation
     * 1.
     * @return A 64-bit floating-point number.
     */
    public double Normal() {
      synchronized (this.valueNormalLock) {
        if (this.valueHaveLastNormal) {
          this.valueHaveLastNormal = false;
          return this.valueLastNormal;
        }
      }
      double x = 1.0 - this.Uniform();
      double y = this.Uniform();
      double s = Math.sqrt(-2 * Math.log(x));
      double t = 2 * Math.PI * y;
      double otherNormal = s * Math.sin(t);
      synchronized (this.valueNormalLock) {
        this.valueLastNormal = otherNormal;
        this.valueHaveLastNormal = true;
      }
      return s * Math.cos(t);
    }

    /**
     * Generates a normally-distributed number with the given mean and standard
     * deviation.
     * @param mean The desired mean.
     * @param sd Standard deviation.
     * @return A 64-bit floating-point number.
     */
    public double Normal(double mean, double sd) {
      return (this.Normal() * sd) + mean;
    }

    /**
     * Generates a random integer such that the average of random numbers
     * approaches the given mean number when this method is called repeatedly with
     * the same mean.
     * @param mean The expected mean of the random numbers.
     * @return A 32-bit signed integer.
     */
    public int Poisson(double mean) {
      if (mean < 0) {
        throw new IllegalArgumentException("mean(" + mean +
          ") is less than 0");
      }
      double l = Math.exp(-mean);
      int count = -1;
      double p = 1.0;
      while (true) {
        ++count;
        p *= this.Uniform();
        if (p <= l) {
          return count;
        }
      }
    }

    /**
     * Not documented yet.
     * @param min Smallest possible number that will be generated.
     * @param max Number that the randomly-generated number will be less than.
     * @return A 64-bit floating-point number.
     */
    public double Uniform(double min, double max) {
      if (min >= max) {
        throw new IllegalArgumentException("min(" + min + ") is not less than " +
          max);
      }
      return min + ((max - min) * this.Uniform());
    }

    /**
     * Returns a uniformly-distributed 64-bit floating-point number from 0 and up,
     * but less than the given number.
     * @param max Number that the randomly-generated number will be less than.
     * @return A 64-bit floating-point number.
     */
    public double Uniform(double max) {
      return this.Uniform(0.0, max);
    }

    /**
     * Returns a uniformly-distributed 64-bit floating-point number from 0 and up,
     * but less than 1.
     * @return A 64-bit floating-point number.
     */
    public double Uniform() {
      return this.UniformLong(9007199254740992L) / 9007199254740992.0;
    }

    /**
     * Returns a uniformly-distributed 32-bit floating-point number from 0 and up,
     * but less than 1.
     * @return A 32-bit floating-point number.
     */
    public double UniformSingle() {
      return this.UniformInt(16777216) / 16777216.0f;
    }

    /**
     * Generates a random 32-bit signed integer within a given range.
     * @param minInclusive Smallest possible value of the random number.
     * @param maxExclusive One plus the largest possible value of the random
     * number.
     * @return A 32-bit signed integer.
     */
    public int UniformInt(int minInclusive, int maxExclusive) {
      if (minInclusive > maxExclusive) {
        throw new IllegalArgumentException("minInclusive(" + minInclusive +
          ") is more than " + maxExclusive);
      }
      if (minInclusive == maxExclusive) {
        return minInclusive;
      }
      if (minInclusive >= 0) {
        return minInclusive + this.UniformInt(maxExclusive - minInclusive);
      } else {
        long diff = maxExclusive - minInclusive;
        if (diff <= Integer.MAX_VALUE) {
          return minInclusive + this.UniformInt((int)diff);
        } else {
          return (int)(minInclusive + this.UniformLong(diff));
        }
      }
    }

    /**
     * Generates a random 64-bit signed integer within a given range.
     * @param minInclusive Smallest possible value of the random number.
     * @param maxExclusive One plus the largest possible value of the random
     * number.
     * @return A 64-bit signed integer.
     */
    public long UniformLong(long minInclusive, long maxExclusive) {
      if (minInclusive > maxExclusive) {
        throw new IllegalArgumentException("minInclusive(" + minInclusive +
          ") is more than " + maxExclusive);
      }
      if (minInclusive == maxExclusive) {
        return minInclusive;
      }
      if (minInclusive >= 0) {
        return minInclusive + this.UniformLong(maxExclusive - minInclusive);
      } else {
        if ((maxExclusive < 0 && Long.MAX_VALUE + maxExclusive <
            minInclusive) ||
          (maxExclusive > 0 && Long.MIN_VALUE + maxExclusive > minInclusive) ||
          minInclusive - maxExclusive < 0) {
          // Difference is greater than MaxValue
          long lb = 0;
          byte[] b = new byte[8];
          while (true) {
            this.valueIrg.GetBytes(b, 0, 8);
            lb = b[0] & 0xffL;
            lb |= (b[1] & 0xffL) << 8;
            lb |= (b[2] & 0xffL) << 16;
            lb |= (b[3] & 0xffL) << 24;
            lb |= (b[4] & 0xffL) << 32;
            lb |= (b[5] & 0xffL) << 40;
            lb |= (b[6] & 0xffL) << 48;
            lb |= (b[7] & 0x7fL) << 56;
            if (lb >= minInclusive && lb < maxExclusive) {
              return lb;
            }
          }
        } else {
          return minInclusive + this.UniformLong(maxExclusive - minInclusive);
        }
      }
    }

    /**
     * Generates a random 32-bit signed integer 0 or greater and less than the
     * given number.
     * @param maxExclusive One plus the largest possible value of the random
     * number.
     * @return A 32-bit signed integer.
     */
    public int UniformInt(int maxExclusive) {
      if (maxExclusive < 0) {
        throw new IllegalArgumentException("maxExclusive(" + maxExclusive +
          ") is less than 0");
      }
      if (maxExclusive <= 1) {
        return 0;
      }
      IRandomGenExtended rge = ((this.valueIrg instanceof IRandomGenExtended) ? (IRandomGenExtended)this.valueIrg : null);
      if (rge != null) {
        return rge.GetInt32(maxExclusive);
      }
      byte[] b = new byte[4];
      switch (maxExclusive) {
        case 2: {
          this.valueIrg.GetBytes(b, 0, 1);
          return b[0] & 1;
        }
        case 256: {
          this.valueIrg.GetBytes(b, 0, 1);
          return (int)b[0] & 1;
        }
        default: {
          while (true) {
            int ib = 0;
            if (maxExclusive == 0x1000000) {
              this.valueIrg.GetBytes(b, 0, 3);
              ib = b[0] & 0xff;
              ib |= (b[1] & 0xff) << 8;
              ib |= (b[2] & 0xff) << 16;
              return ib;
            }
            if (maxExclusive == 0x10000) {
              this.valueIrg.GetBytes(b, 0, 2);
              ib = b[0] & 0xff;
              ib |= (b[1] & 0xff) << 8;
              return ib;
            }
            int maxexc;
            maxexc = (Integer.MAX_VALUE / maxExclusive) * maxExclusive;
            while (true) {
              this.valueIrg.GetBytes(b, 0, 4);
              ib = b[0] & 0xff;
              ib |= (b[1] & 0xff) << 8;
              ib |= (b[2] & 0xff) << 16;
              ib |= (b[3] & 0x7f) << 24;
              if (ib < maxexc) {
                return ib % maxExclusive;
              }
            }
          }
        }
      }
    }

    public long GetInt64(long maxExclusive) {
      return this.UniformLong(maxExclusive);
    }

    public int GetInt32(int maxExclusive) {
      return this.UniformInt(maxExclusive);
    }

    /**
     * Generates a random 32-bit signed integer 0 or greater and less than the
     * given number.
     * @param maxExclusive One plus the largest possible value of the random
     * number.
     * @return A 64-bit signed integer.
     */
    public long UniformLong(long maxExclusive) {
      if (maxExclusive < 0) {
        throw new IllegalArgumentException("maxExclusive(" + maxExclusive +
          ") is less than 0");
      }
      if (maxExclusive <= Integer.MAX_VALUE) {
        return this.UniformInt((int)maxExclusive);
      }
      IRandomGenExtended rge = ((this.valueIrg instanceof IRandomGenExtended) ? (IRandomGenExtended)this.valueIrg : null);
      if (rge != null) {
        return rge.GetInt64(maxExclusive);
      }
      long lb = 0;
      long maxexc;
      byte[] b = new byte[8];
      maxexc = (Long.MAX_VALUE / maxExclusive) * maxExclusive;
      while (true) {
        this.valueIrg.GetBytes(b, 0, 8);
        lb = b[0] & 0xffL;
        lb |= (b[1] & 0xffL) << 8;
        lb |= (b[2] & 0xffL) << 16;
        lb |= (b[3] & 0xffL) << 24;
        lb |= (b[4] & 0xffL) << 32;
        lb |= (b[5] & 0xffL) << 40;
        lb |= (b[6] & 0xffL) << 48;
        lb |= (b[7] & 0x7fL) << 56;
        if (lb < maxexc) {
          return lb % maxExclusive;
        }
      }
    }
  }
