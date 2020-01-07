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

  /**
   * Not documented yet.
   */
  interface ICBORNumber {
  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean IsPositiveInfinity(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean IsInfinity(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean IsNegativeInfinity(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean IsNaN(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean IsNegative(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    double AsDouble(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    Object Negate(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    Object Abs(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    EDecimal AsEDecimal(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    EFloat AsEFloat(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    ERational AsERational(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    float AsSingle(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    EInteger AsEInteger(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    long AsInt64(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean CanFitInSingle(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean CanFitInDouble(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean CanFitInInt32(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean CanFitInInt64(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean CanTruncatedIntFitInInt64(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean CanTruncatedIntFitInInt32(Object obj);

  /**
   * Not documented yet.
   * @return Not documented yet.
   */
    int AsInt32(Object obj, int minValue, int maxValue);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean IsNumberZero(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    int Sign(Object obj);

  /**
   * Not documented yet.
   * @param obj Not documented yet.
   * @return Not documented yet.
   */
    boolean IsIntegral(Object obj);
  }
