package com.upokecenter.numbers;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

  public final class BigNumberFlags {
private BigNumberFlags() {
}
    static final int FlagNegative = 1;
    static final int FlagQuietNaN = 4;
    static final int FlagSignalingNaN = 8;
    static final int FlagInfinity = 2;
    static final int FlagSpecial = FlagQuietNaN | FlagSignalingNaN |
    FlagInfinity;

    static final int FlagNaN = FlagQuietNaN | FlagSignalingNaN;
    static final int UnderflowFlags = EContext.FlagInexact |
    EContext.FlagSubnormal | EContext.FlagUnderflow | EContext.FlagRounded;

    static final int LostDigitsFlags = EContext.FlagLostDigits |
    EContext.FlagInexact | EContext.FlagRounded;

    static final int FiniteOnly = 0;
    static final int FiniteAndNonFinite = 1;
  }
