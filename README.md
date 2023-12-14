# CBOR

[![Maven Central](https://img.shields.io/maven-central/v/com.upokecenter/cbor.svg?style=plastic)](https://search.maven.org/#search|ga|1|g%3A%22com.upokecenter%22%20AND%20a%3A%22cbor%22)

---

A Java implementation of Concise Binary Object Representation, a general-purpose binary data format defined in RFC 7049. According to that RFC, CBOR's data model "is an extended version of the JSON data model", supporting many more types of data than JSON. "CBOR was inspired by MessagePack", but "is not intended as a version of or replacement for MessagePack."

This implementation was written by Peter O. and is released to the Public Domain under the [CC0 Declaration](https://creativecommons.org/publicdomain/zero/1.0/).

This implementation also doubles as a reader and writer of JSON, and can convert data from JSON to CBOR and back.

Finally, this implementation supports arbitrary-precision binary and decimal floating-point numbers and rational numbers with arbitrary-precision components.

## How to Install

Starting with version 0.23.0, the Java implementation is available
as an [artifact](https://search.maven.org/#search|ga|1|g%3A%22com.upokecenter%22%20AND%20a%3A%22cbor%22) in the Central Repository. To add this library to a Maven
project, add the following to the `dependencies` section in your `pom.xml` file:

```xml
<dependency>
  <groupId>com.upokecenter</groupId>
  <artifactId>cbor</artifactId>
  <version>5.0.0-alpha1</version>
</dependency>
```

In other Java-based environments, the library can be referred to by its
group ID (`com.upokecenter`), artifact ID (`cbor`), and version, as given above.

## Documentation

This library defines one class, called CBORObject, that allows you to read and
write CBOR objects to and from data streams and byte arrays, and to convert JSON
text to CBOR objects and back.

**See the [Java API documentation](https://peteroupc.github.io/CBOR/api/).**

## Examples

Reading data from a file.

```java
 // Java
 // Open the file stream
 try (FileInputStream stream = new FileInputStream("object.cbor")) {
    // Read the CBOR object from the stream
    var cbor = CBORObject.Read(stream);
    // At this point, the object is read, but the file stream might
    // not have ended yet.  Here, the code may choose to read another
    // CBOR object, check for the end of the stream, or just ignore the
    // rest of the file.  The following is an example of checking for the
    // end of the stream.
    if (stream.getChannel().position() != stream.getChannel().size()) {
      // The end of the stream wasn't reached yet.
    } else {
      // The end of the stream was reached.
    }
 }
```

Writing multiple objects to a file, including arbitrary objects:

```java
// Java
// This example uses the "try-with-resources" statement from Java 7.
// This example writes different kinds of objects in CBOR
// format to the same file.
try (FileOutputStream stream = new FileOutputStream("object.cbor")) {
   CBORObject.Write(true, stream);
   CBORObject.Write(422.5, stream);
   CBORObject.Write("some string", stream);
   CBORObject.Write(CBORObject.Undefined, stream);
   CBORObject.NewArray().Add(42).WriteTo(stream);
}
```

NOTE: All code samples in this section are released to the Public Domain,
as explained in <https://creativecommons.org/publicdomain/zero/1.0/>.

## Source Code

Source code is available in the [project page](https://github.com/peteroupc/CBOR-Java).

## About

Written in 2013-2016 by Peter O.

Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
[https://creativecommons.org/publicdomain/zero/1.0/](https://creativecommons.org/publicdomain/zero/1.0/)

## Clarifications

The following are some clarifications to RFC 7049.

- Section 2.4.2 doesn't specify what happens if a bignum's byte
  string has a length of 0. This implementation treats a positive
  bignum with length 0 as having a value of 0 and a negative
  bignum with length 0 as having a value of -1.
- Section 2.4.1 specifies the number of seconds since the start of 1970. It is
  based on the POSIX definition of "seconds since the Epoch", which
  the RFC cites as a normative reference. This definition does not
  count leap seconds. When this implementation supports date
  conversion, it won't count leap seconds, either. This implementation
  treats values of infinity and NaN as invalid.
- For tag 32, this implementation accepts strings that are valid
  Internationalized Resource Identifiers (IRIs) in addition to URIs.
  IRI are like URIs except that they also allow non-ASCII characters.

## Release Notes

For release notes, see the [CBOR .NET repository](https://github.com/peteroupc/CBOR).

The [commit history](https://github.com/peteroupc/CBOR-Java/commits/master)
contains details on code changes in previous versions.

## Acknowledgments

For acknowledgments, see the [CBOR .NET repository](https://github.com/peteroupc/CBOR).

I thank all users who sent issues to this repository.
