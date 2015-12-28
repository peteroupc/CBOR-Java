package com.upokecenter.test;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

    /**
     * The system&#x27;s random number generator will be called many times during
     * testing. Unfortunately it can be very slow. So we use this wrapper
     * class.
     */
  public class FastRandom {
    private static final int ReseedCount = 500;

    private final java.util.Random rand;
    private final java.util.Random rand2;
    private int count;

    private int w = 521288629;
    private int z = 362436069;
    private static final int[] seeds = new int[32];

    private static void AddSeed(int seed) {
      synchronized (seeds) {
        if (seedIndex == -1) {
          seedIndex = 0;
        }
        seeds[seedIndex ]^=seed;
        seedCount = Math.max(seedCount, seedIndex + 1);
        ++seedIndex;
        seedIndex %= seeds.length;
      }
    }

    private static int GetSeed() {
      synchronized (seeds) {
        if (seedCount == 0) {
          return 0;
        }
        if (seedReadIndex >= seedCount) {
          seedReadIndex = 0;
        }
        return seeds[seedReadIndex++];
      }
    }

    private static int seedIndex;
    private static int seedCount;
    private static int seedReadIndex;

    public FastRandom() {
      int randseed = GetSeed();
      this.rand = (randseed == 0) ? (new java.util.Random()) : (new java.util.Random(randseed));
      int randseed2 = (GetSeed() ^SysRandNext(this.rand, this.rand));
      this.rand2 = (randseed2 == 0) ? (new java.util.Random()) : (new java.util.Random(randseed2));
      this.count = ReseedCount;
    }

    private static int SysRandNext(java.util.Random randA, java.util.Random randB) {
      int ret = randA.nextInt(0x10000);
      ret |= randB.nextInt(0x10000) << 16;
      return ret;
    }

    private int NextValueInternal() {
      int w = this.w, z = this.z;
      // Use George Marsaglia's multiply-with-carry
      // algorithm.
      this.z = z = ((36969 * (z & 65535)) + ((z >> 16) & 0xffff));
      this.w = w = ((18000 * (w & 65535)) + ((z >> 16) & 0xffff));
      return ((z << 16) | (w & 65535)) & 0x7fffffff;
    }

    /**
     * Generates a random number.
     * @param v The return value will be 0 or greater, and less than this number.
     * @return A 32-bit signed integer.
     */
    public int NextValue(int v) {
      if (v <= 0) {
        throw new IllegalArgumentException(
          "v (" + v + ") is not greater than 0");
      }
      if (v <= 1) {
        return 0;
      }
      if (this.count >= ReseedCount) {
        // Call the default random number generator
        // every once in a while, to reseed
        this.count = 0;
        if (this.rand != null) {
          int seed = SysRandNext(this.rand, this.rand2);
          this.z ^= seed;
          seed = SysRandNext(this.rand2, this.rand);
          this.w ^= seed;
          if (this.z == 0) {
            this.z = 362436069;
          }
          if (this.w == 0) {
            this.w = 521288629;
          }
          seed = SysRandNext(this.rand, this.rand2);
          AddSeed(seed);
          return this.rand.nextInt(v);
        }
      }
      ++this.count;
      if (v == 0x1000000) {
        return this.NextValueInternal() & 0xffffff;
      }
      if (v == 0x10000) {
        return this.NextValueInternal() & 0xffff;
      }
      if (v == 0x100) {
        return this.NextValueInternal() & 0xff;
      }
      int maxExclusive = (Integer.MAX_VALUE / v) * v;
      while (true) {
        int vi = this.NextValueInternal();
        if (vi < maxExclusive) {
          return vi % v;
        }
      }
    }
  }
