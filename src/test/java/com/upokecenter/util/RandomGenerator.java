package com.upokecenter.util;

  public final class RandomGenerator implements IRandomGenExtended
  {
    private final IRandomGen valueIrg;
    private final Object valueNormalLock = new();
    private boolean valueHaveLastNormal;
    private double valueLastNormal;

    public RandomGenerator() {
 this(new XorShift128Plus());
    }

    public RandomGenerator(IRandomGen valueIrg) {
      this.valueIrg = valueIrg;
    }

    public boolean Bernoulli(double p) {
      if (p < 0) {
 throw new IllegalArgumentException("p(" + p + ") is less than 0");
}
 if (p > 1) {
 throw new IllegalArgumentException("p(" + p + ") is more than 1");
}
 return this.Uniform() < p;
    }

    public boolean Bernoulli() {
      return this.UniformInt(2) == 0;
    }

    public int Binomial(int trials) {
      return this.Binomial(trials, 0.5);
    }

    public int GetBytes(byte[] bytes, int offset, int count) {
      return this.valueIrg.GetBytes(bytes, offset, count);
    }

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
          this.valueIrg = this.valueIrg.GetBytes(bytes, 0, 1);
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

    public double ChiSquared(int df) {
      if (df <= 0) {
 throw new IllegalArgumentException("df(" + df + ") is not" +
"\u0020greater than 0");
}
 return this.Gamma(df * 0.5, 2);
    }

    public double Exponential() {
      return -Math.log(1.0 - this.Uniform());
    }

    public double Gamma(double a, double b) {
      if (b <= 0) {
 throw new IllegalArgumentException("b(" + b + ") is not" +
"\u0020greater than 0");
}
 return this.Gamma(a) * b;
    }

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
      return a < 1 ? d * v * Math.exp(this.Exponential() / -a) : d * v;
    }

    public int Geometric() {
      return this.NegativeBinomial(1, 0.5);
    }

    public int Geometric(double p) {
      return this.NegativeBinomial(1, p);
    }

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

    public double LogNormal(double mean, double sd) {
      return Math.exp(this.Normal(mean, sd));
    }

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
          this.valueIrg = this.valueIrg.GetBytes(bytes, 0, 1);
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

    public int NegativeBinomial(int trials) {
      return this.NegativeBinomial(trials, 0.5);
    }
    // The Normal, Exponential, Poisson, and
    // single-argument Gamma methods were adapted
    // from a third-party public-domain JavaScript file.

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

    public double Normal(double mean, double sd) {
      return (this.Normal() * sd) + mean;
    }

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

    public double Uniform(double min, double max) {
      if (min >= max) {
 throw new IllegalArgumentException("min(" + min + ") is not less than " +
          max);
}
 return min + ((max - min) * this.Uniform());
    }

    public double Uniform(double max) {
      return this.Uniform(0.0, max);
    }

    public double Uniform() {
      return this.UniformLong(9007199254740992L) / 9007199254740992.0;
    }

    public double UniformSingle() {
      return this.UniformInt(16777216) / 16777216.0f;
    }

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
        return diff <= Integer.MAX_VALUE ? minInclusive +
this.UniformInt((int)diff) : (int)(minInclusive + this.UniformLong(diff));
      }
    }

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
          byte[] b = new byte[8];
          while (true) {
            this.valueIrg = this.valueIrg.GetBytes(b, 0, 8);
            // Difference is greater than MaxValue
            long lb = b[0] & 0xffL;
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

    public int UniformInt(int maxExclusive) {
      if (maxExclusive < 0) {
        throw new IllegalArgumentException("maxExclusive(" + maxExclusive +
          ") is less than 0");
      }
      if (maxExclusive <= 1) {
        return 0;
      }
      if (this.valueIrg is IRandomGenExtended) {
        return ((IRandomGenExtended)this.valueIrg).GetInt32(maxExclusive);
      }
      byte[] b = new byte[4];
      switch (maxExclusive) {
        case 2: {
            this.valueIrg = this.valueIrg.GetBytes(b, 0, 1);
            return b[0] & 1;
          }
        case 256: {
            this.valueIrg = this.valueIrg.GetBytes(b, 0, 1);
            return b[0] & 1;
          }
        default: {
            while (true) {
              int ib;
              if (maxExclusive == 0x1000000) {
                this.valueIrg = this.valueIrg.GetBytes(b, 0, 3);
                ib = b[0] & 0xff;
                ib |= (b[1] & 0xff) << 8;
                ib |= (b[2] & 0xff) << 16;
                return ib;
              }
              if (maxExclusive == 0x10000) {
                this.valueIrg = this.valueIrg.GetBytes(b, 0, 2);
                ib = b[0] & 0xff;
                ib |= (b[1] & 0xff) << 8;
                return ib;
              }
              int maxexc;
              maxexc = Integer.MAX_VALUE / maxExclusive * maxExclusive;
              while (true) {
                this.valueIrg = this.valueIrg.GetBytes(b, 0, 4);
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

    public long UniformLong(long maxExclusive) {
      if (maxExclusive < 0) {
        throw new IllegalArgumentException("maxExclusive(" + maxExclusive +
          ") is less than 0");
      }
      if (maxExclusive <= Integer.MAX_VALUE) {
        return this.UniformInt((int)maxExclusive);
      }
      if (this.valueIrg is IRandomGenExtended) {
        return ((IRandomGenExtended)this.valueIrg).GetInt64(maxExclusive);
      }

      long maxexc;
      byte[] b = new byte[8];
      maxexc = Long.MAX_VALUE / maxExclusive * maxExclusive;
      while (true) {
        this.valueIrg = this.valueIrg.GetBytes(b, 0, 8);
        long lb = b[0] & 0xffL;
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
