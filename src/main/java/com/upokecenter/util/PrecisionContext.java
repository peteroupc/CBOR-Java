package com.upokecenter.util;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.numbers.*;

    /**
     * A precision context.
     * @deprecated Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public class PrecisionContext {
    private final EContext ec;

    final EContext getEc() {
        return this.ec;
      }

    PrecisionContext(EContext ec) {
      this.ec = ec;
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.PrecisionContext}
     * class. HasFlags will be set to false.
     * @param precision The maximum number of digits a number can have, or 0 for an
     * unlimited number of digits.
     * @param rounding The rounding mode to use when a number can't fit the given
     * precision.
     * @param exponentMinSmall The minimum exponent.
     * @param exponentMaxSmall The maximum exponent.
     * @param clampNormalExponents Whether to clamp a number's significand to the
     * given maximum precision (if it isn't zero) while remaining within
     * the exponent range.
     */
    public PrecisionContext(
      int precision,
      Rounding rounding,
      int exponentMinSmall,
      int exponentMaxSmall,
      boolean clampNormalExponents) {
      throw new UnsupportedOperationException("This class is now obsolete.");
    }

    /**
     * Gets a string representation of this object. Note that the format is not
     * intended to be parsed and may change at any time.
     * @return A string representation of this object.
     */
    @Override public String toString() {
      return "";
    }
  }
