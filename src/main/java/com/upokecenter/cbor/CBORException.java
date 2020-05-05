package com.upokecenter.cbor;
/*
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
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
