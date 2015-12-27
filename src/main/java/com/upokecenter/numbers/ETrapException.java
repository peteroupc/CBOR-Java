package com.upokecenter.numbers;
/*
Written in 2014 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

    /**
     * Exception thrown for arithmetic trap errors.
     */
  public class ETrapException extends ArithmeticException {
private static final long serialVersionUID = 1L;
    private final Object result;
    private final EContext ctx;

    /**
     * Gets the precision context used during the operation that triggered the
     * trap. May be null.
     * @return The precision context used during the operation that triggered the
     * trap. May be null.
     */
    public final EContext getContext() {
        return this.ctx;
      }

    private final int error;

    /**
     * Gets the defined result of the operation that caused the trap.
     * @return The defined result of the operation that caused the trap.
     */
    public final Object getResult() {
        return this.result;
      }

    /**
     * Gets the flag that specifies the kind of error (PrecisionContext.FlagXXX).
     * This will only be one flag, such as FlagInexact or FlagSubnormal.
     * @return The flag that specifies the kind of error
     * (PrecisionContext.FlagXXX). This will only be one flag, such as
     * FlagInexact or FlagSubnormal.
     */
    public final int getError() {
        return this.error;
      }

    private static String FlagToMessage(int flag) {
      return (flag == EContext.FlagClamped) ? "Clamped" : ((flag ==
        EContext.FlagDivideByZero) ? "DivideByZero" : ((flag ==
        EContext.FlagInexact) ? "Inexact" : ((flag ==
        EContext.FlagInvalid) ? "Invalid" : ((flag ==
        EContext.FlagOverflow) ? "Overflow" : ((flag ==
        EContext.FlagRounded) ? "Rounded" : ((flag ==
        EContext.FlagSubnormal) ? "Subnormal" : ((flag ==
        EContext.FlagUnderflow) ? "Underflow" : "Trap")))))));
    }

    /**
     * Initializes a new instance of the TrapException class.
     * @param flag A 32-bit signed integer.
     * @param ctx An EContext object.
     * @param result An arbitrary object.
     */
    public ETrapException (int flag, EContext ctx, Object result) {
 super(FlagToMessage(flag));
      this.error = flag;
      this.ctx = (ctx == null) ? null : ctx.Copy();
      this.result = result;
    }
  }
