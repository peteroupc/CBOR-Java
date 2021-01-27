# API Documentation

* [com.upokecenter.cbor.ICBORConverter&amp;lt;T&amp;gt;](com.upokecenter.cbor.ICBORConverter.md) -
Interface implemented by classes that convert objects of arbitrary types to
 CBOR objects.

* [com.upokecenter.cbor.ICBORToFromConverter&amp;lt;T&amp;gt;](com.upokecenter.cbor.ICBORToFromConverter.md) -
Classes that implement this interface can support conversions from CBOR
 objects to a custom type and back.

* [com.upokecenter.cbor.CBORDataUtilities](com.upokecenter.cbor.CBORDataUtilities.md) -
Contains methods useful for reading and writing data, with a focus on CBOR.

* [com.upokecenter.cbor.CBOREncodeOptions](com.upokecenter.cbor.CBOREncodeOptions.md) -
Specifies options for encoding and decoding CBOR objects.

* [com.upokecenter.cbor.CBORNumber](com.upokecenter.cbor.CBORNumber.md) -
An instance of a number that CBOR or certain CBOR tags can represent.

* [com.upokecenter.cbor.CBORObject](com.upokecenter.cbor.CBORObject.md) -
Represents an object in Concise Binary Object Representation (CBOR) and
 contains methods for reading and writing CBOR data.

* [com.upokecenter.cbor.CBORTypeMapper](com.upokecenter.cbor.CBORTypeMapper.md) -
Holds converters to customize the serialization and deserialization behavior
 of CBORObject.FromObject and CBORObject#ToObject, as
 well as type filters for ToObject.

* [com.upokecenter.cbor.JSONOptions](com.upokecenter.cbor.JSONOptions.md) -
Includes options to control how CBOR objects are converted to JSON.

* [com.upokecenter.cbor.PODOptions](com.upokecenter.cbor.PODOptions.md) -
Options for controlling how certain DotNET or Java objects, such as
  so-called "plain old data" objects (better known as POCOs in DotNET or
 POJOs in Java), are converted to CBOR objects.

* [com.upokecenter.cbor.CBORNumber.NumberKind](com.upokecenter.cbor.CBORNumber.NumberKind.md) -
Specifies the underlying form of this CBOR number object.

* [com.upokecenter.cbor.CBORType](com.upokecenter.cbor.CBORType.md) -
Represents a type that a CBOR object can have.

* [com.upokecenter.cbor.JSONOptions.ConversionMode](com.upokecenter.cbor.JSONOptions.ConversionMode.md) -
Specifies how JSON numbers are converted to CBOR objects when decoding JSON
 (such as via FromJSONString or ReadJSON).

* [com.upokecenter.cbor.CBORException](com.upokecenter.cbor.CBORException.md) -
Exception thrown for errors involving CBOR data.

* [com.upokecenter.util.DataUtilities](com.upokecenter.util.DataUtilities.md) -
Contains methods useful for reading and writing text strings.
