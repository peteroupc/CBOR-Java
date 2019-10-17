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

  interface ICBORNumber {
    boolean IsPositiveInfinity(Object obj);

    boolean IsInfinity(Object obj);

    boolean IsNegativeInfinity(Object obj);

    boolean IsNaN(Object obj);

    boolean IsNegative(Object obj);

    double AsDouble(Object obj);

    Object Negate(Object obj);

    Object Abs(Object obj);

    EDecimal AsEDecimal(Object obj);

    EFloat AsEFloat(Object obj);

    ERational AsERational(Object obj);

    float AsSingle(Object obj);

    EInteger AsEInteger(Object obj);

    long AsInt64(Object obj);

    boolean CanFitInSingle(Object obj);

    boolean CanFitInDouble(Object obj);

    boolean CanFitInInt32(Object obj);

    boolean CanFitInInt64(Object obj);

    boolean CanTruncatedIntFitInInt64(Object obj);

    boolean CanTruncatedIntFitInInt32(Object obj);

    int AsInt32(Object obj, int minValue, int maxValue);

    boolean IsNumberZero(Object obj);

    int Sign(Object obj);

    boolean IsIntegral(Object obj);
  }
