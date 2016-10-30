package com.upokecenter.test;
/*
Written by Peter O. in 2013-2016.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

    /**
     * The system&#x27;s random number generator will be called many times during
     * testing. Unfortunately it can be very slow. So we use this wrapper
     * class.
     */
  public class FastRandom {
    private static final int ReseedCount = 495;

    private final java.util.Random rand;
    private final java.util.Random rand2;
    private int count;
    private static final Object ValueSeedsLock = new Object();

    private int w = 521288629;
    private int z = 362436069;
    private long s0, s1;
    private int lastRand = 0;
    private boolean haveLastRand = false;
    private static final int[] ValueSeeds = new int[32];

    private static void AddSeed(int seed) {
      synchronized (ValueSeedsLock) {
        if (seedIndex == -1) {
          seedIndex = 0;
        }
        ValueSeeds[seedIndex] ^= seed;
        seedCount = Math.max(seedCount, seedIndex + 1);
        ++seedIndex;
        seedIndex %= ValueSeeds.length;
      }
    }

    private static int GetSeed() {
      synchronized (ValueSeedsLock) {
        if (seedCount == 0) {
          return 0;
        }
        if (seedReadIndex >= seedCount) {
          seedReadIndex = 0;
        }
        return ValueSeeds[seedReadIndex++];
      }
    }

    private static int seedIndex;
    private static int seedCount;
    private static int seedReadIndex;

    public FastRandom() {
      int randseed = GetSeed();
      this.rand = (randseed == 0) ? (new java.util.Random()) : (new java.util.Random(randseed));
      int randseed2 = (GetSeed() ^ SysRandNext(this.rand, this.rand));
      this.rand2 = (randseed2 == 0) ? (new java.util.Random()) : (new java.util.Random(randseed2));
      this.count = ReseedCount;
    }

    private static int SysRandNext(java.util.Random randA, java.util.Random randB) {
      int ret = randA.nextInt(0x10000);
      ret |= randB.nextInt(0x10000) << 16;
      return ret;
    }

    private int NextValueInternal(int mask) {
      if (this.haveLastRand) {
        this.haveLastRand = false;
        return this.lastRand & mask;
      } else if ((this.count & 7) == 0) {
        int w = this.w, z = this.z;
        // Use George Marsaglia's multiply-with-carry
        // algorithm.
        this.z = z = ((36969 * (z & 65535)) + ((z >> 16) & 0xffff));
        this.w = w = ((18000 * (w & 65535)) + ((z >> 16) & 0xffff));
        return ((z << 16) | (w & 65535)) & mask;
      } else {
        // Adapted from public domain code from Sebastiano
        // Vigna's xorshift128plus at:
        // http://xorshift.di.unimi.it/xorshift128plus.c
        long z1 = this.s0;
        long z0 = this.s1;
        z1 = (z1 ^ (z1 << 23));
        long a = (z1 >> 18) & 0x3fffffffffffL;
        long b = (z0 >> 5) & 0x7ffffffffffffffL;
        this.s0 = z0;
        this.s1 = z1 ^ z0 ^ a ^ b;
        b = (this.s1 + z0);
        this.lastRand = ((int)(b >> 32));
        this.haveLastRand = true;
        return ((int)b) & mask;
      }
    }

    /**
     * Generates a random number.
     * @param v The return value will be 0 or greater, and less than this number.
     * @return A 32-bit signed integer.
     */
    public int NextValue(int v) {
      if (v <= 1) {
        if (v == 1) {
          return 0;
        }
        throw new IllegalArgumentException(
          "v (" + v + ") is not greater than 0");
      }
      if (this.count >= ReseedCount) {
        // Call the default random number generator
        // every once in a while, to reseed
        this.count = 0;
        if (this.rand != null) {
          // System.out.println("reseed");
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
          this.s0 ^= this.z;
          this.s1 ^= this.w;
          if ((this.s0 | this.s1) == 0) {
            this.s0 = this.z;
          }
          seed = SysRandNext(this.rand, this.rand2);
          AddSeed(seed);
          return this.rand.nextInt(v);
        }
      }
      ++this.count;
      if (v == 0x1000000) {
        return this.NextValueInternal(0xffffff);
      }
      if (v == 0x10000) {
        return this.NextValueInternal(0xffff);
      }
      if (v == 0x100) {
        return this.NextValueInternal(0xff);
      }
      int maxExclusive;
      maxExclusive = (v <= 100) ? 2147483600 : ((Integer.MAX_VALUE / v) * v);
      while (true) {
        int vi = this.NextValueInternal(0x7fffffff);
        if (vi < maxExclusive) {
          return vi % v;
        }
      }
    }
  }
