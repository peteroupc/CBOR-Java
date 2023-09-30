# com.upokecenter.cbor.CBORException

    public final class CBORException extends RuntimeException

<p>Exception thrown for errors involving CBOR data. </p> <p>This library may
 throw exceptions of this type in certain cases, notably when errors occur,
 and may supply messages to those exceptions (the message can be accessed
 through the <code>Message</code> property in.NET or the <code>getMessage()</code>
 method in Java). These messages are intended to be read by humans to help
 diagnose the error (or other cause of the exception); they are not intended
 to be parsed by computer programs, and the exact text of the messages may
 change at any time between versions of this library.</p>

## Constructors

## Methods
