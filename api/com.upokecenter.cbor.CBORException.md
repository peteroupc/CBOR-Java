# com.upokecenter.cbor.CBORException

    public final class CBORException extends java.lang.RuntimeException

Exception thrown for errors involving CBOR data. <p>This library may throw
exceptions of this type in certain cases, notably when errors occur,
and may supply messages to those exceptions (the message can be
accessed through the <code>Message</code> property in.NET or the
<code>getMessage()</code> method in Java). These messages are intended to be
read by humans to help diagnose the error (or other cause of the
exception); they are not intended to be parsed by computer programs,
and the exact text of the messages may change at any time between
versions of this library.</p>

## Methods

- `CBORException() CBORException`<br>
  Initializes a new instance of the CBORException
  class.
- `CBORException​(java.lang.String message) CBORException`<br>
  Initializes a new instance of the CBORException
  class.
- `CBORException​(java.lang.String message, java.lang.Throwable innerException) CBORException`<br>
  Initializes a new instance of the CBORException
  class.

## Constructors

- `CBORException() CBORException`<br>
  Initializes a new instance of the CBORException
  class.
- `CBORException​(java.lang.String message) CBORException`<br>
  Initializes a new instance of the CBORException
  class.
- `CBORException​(java.lang.String message, java.lang.Throwable innerException) CBORException`<br>
  Initializes a new instance of the CBORException
  class.
