package com.upokecenter.cbor;
/*
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

  public final class CBORException extends RuntimeException {
private static final long serialVersionUID = 1L;

    public CBORException() {
    }

    public CBORException(String message) {
 super(message);
    }

    public CBORException(String message, Throwable innerException) {
 super(message);
initCause(innerException);;
    }
  }
