package com.upokecenter.cbor;
/*
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

    /**
     * Exception thrown for errors involving CBOR data.
     */
  public class CBORException extends RuntimeException {
private static final long serialVersionUID = 1L;
    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.getCBORException()}
     * class.
     */
    public CBORException() {
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.getCBORException()}
     * class.
     * @param message A text string.
     */
    public CBORException(String message) {
 super(message);
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.getCBORException()}
     * class. Uses the given message and inner exception.
     * @param message A text string.
     * @param innerException An Exception object.
     */
    public CBORException(String message, Throwable innerException) {
 super(message, innerException);
    }
  }
