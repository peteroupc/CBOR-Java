package com.upokecenter.util;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.numbers.*;

    /**
     * <p><b>This class is obsolete. It will be replaced by a new version of this
     * class in a different namespace/package and library, called
     * <code>PeterO.Numbers.ETrapException</code> in the <a
  * href='https://www.nuget.org/packages/PeterO.Numbers'><code>PeterO.Numbers</code></a>
     * library (in .NET), or <code>com.upokecenter.numbers.ETrapException</code>
     * in the <a
  * href='https://github.com/peteroupc/numbers-java'><code>com.github.peteroupc/numbers</code></a>
     * artifact (in Java).</b></p> Exception thrown for arithmetic trap
     * errors.
     * @deprecated Use ETrapException from PeterO.Numbers/com.upokecenter.numbers.
 */
@Deprecated
  public class TrapException extends ArithmeticException {
private static final long serialVersionUID = 1L;
    private ETrapException ete;

    /**
     * Gets the precision context used during the operation that triggered the
     * trap. May be null.
     * @return The precision context used during the operation that triggered the
     * trap. May be null.
     */
    public final PrecisionContext getContext() {
        return new PrecisionContext(this.ete.getContext());
}

    /**
     * Gets the defined result of the operation that caused the trap.
     * @return The defined result of the operation that caused the trap.
     */
    public final Object getResult() {
        return this.ete.getResult();
}

    /**
     * Gets the flag that specifies the kind of error (PrecisionContext.FlagXXX).
     * This will only be one flag, such as FlagInexact or FlagSubnormal.
     * @return The flag that specifies the kind of error
     * (PrecisionContext.FlagXXX). This will only be one flag, such as
     * FlagInexact or FlagSubnormal.
     */
    public final int getError() {
        return this.ete.getError();
}

    private TrapException() {
 super();
    }

    static TrapException Create(ETrapException ete) {
      TrapException ex = new TrapException();
      ex.ete = ete;
      return ex;
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.TrapException}
     * class.
     * @param flag A flag that specifies the kind of error
     * (PrecisionContext.FlagXXX). This will only be one flag, such as
     * FlagInexact or FlagSubnormal.
     * @param ctx A context object for arbitrary-precision arithmetic settings.
     * @param result The desired result of the operation that caused the trap, such
     * as an {@code ExtendedDecimal} or {@code ExtendedFloat}.
     */
    public TrapException(int flag, PrecisionContext ctx, Object result) {
 super("");
      Object wrappedResult = result;
      EDecimal ed = ((result instanceof EDecimal) ? (EDecimal)result : null);
      ERational er = ((result instanceof ERational) ? (ERational)result : null);
      EFloat ef = ((result instanceof EFloat) ? (EFloat)result : null);
      if (ed != null) {
 wrappedResult = new ExtendedDecimal(ed);
}
      if (er != null) {
 wrappedResult = new ExtendedRational(er);
}
      if (ef != null) {
 wrappedResult = new ExtendedFloat(ef);
}
      this.ete = new ETrapException(
  flag,
  ctx == null ? null : ctx.getEc(),
  wrappedResult);
    }
  }
