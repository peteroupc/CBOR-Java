package com.upokecenter.cbor;
/*
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

  /**
   * <p>Exception thrown for errors involving CBOR data. </p> <p>This library may
   * throw exceptions of this type in certain cases, notably when errors occur,
   * and may supply messages to those exceptions (the message can be accessed
   * through the {@code Message} property in.NET or the {@code getMessage()}
   * method in Java). These messages are intended to be read by humans to help
   * diagnose the error (or other cause of the exception); they are not intended
   * to be parsed by computer programs, and the exact text of the messages may
   * change at any time between versions of this library.</p>
   */

public final class CBORException extends RuntimeException {
private static final long serialVersionUID = 1L;
    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.CBORException}
     * class.
     */
    public CBORException() {
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.CBORException}
     * class.
     * @param message The parameter {@code message} is a text string.
     */
    public CBORException(String message) {
 super(message);
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.CBORException}
     * class. Uses the given message and inner exception.
     * @param message The parameter {@code message} is a text string.
     * @param innerException The parameter {@code innerException} is an Exception
     * object.
     */
    public CBORException(String message, Throwable innerException) {
 super(message);
initCause(innerException);;
    }
  }
