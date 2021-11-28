# com.upokecenter.cbor.CBORType

## Nested Classes

## Enum Constants

* `Array`<br>
 An array of CBOR objects.
* `Boolean`<br>
 The simple values true and false.
* `ByteString`<br>
 An array of bytes.
* `FloatingPoint`<br>
 A 16-, 32-, or 64-bit binary floating-point number.
* `Integer`<br>
 An integer in the interval [-(2^64), 2^64 - 1], or an integer of major type
 0 and 1.
* `Map`<br>
 A map of CBOR objects.
* `Number`<br>
 Deprecated.
Since version 4.0, CBORObject.Type no longer returns this value for any
 CBOR Object - this is a breaking change from earlier
 versions.
 Since version 4.0, CBORObject.Type no longer returns this value for any
 CBOR Object - this is a breaking change from earlier
 versions.
* `SimpleValue`<br>
 A "simple value" other than floating point values, true, and false.
* `TextString`<br>
 A text string.

## Methods

* `static CBORType valueOf​(java.lang.String name)`<br>
 Returns the enum constant of this type with the specified name.
* `static CBORType[] values()`<br>
 Returns an array containing the constants of this enum type, in
the order they are declared.

## Enum Constant Details

### <a id='Number'>Number</a>

This property is no longer used.
### <a id='Boolean'>Boolean</a>

The simple values true and false.
### <a id='SimpleValue'>SimpleValue</a>

A "simple value" other than floating point values, true, and false.
### <a id='ByteString'>ByteString</a>

An array of bytes.
### <a id='TextString'>TextString</a>

A text string.
### <a id='Array'>Array</a>

An array of CBOR objects.
### <a id='Map'>Map</a>

A map of CBOR objects.
### <a id='Integer'>Integer</a>

An integer in the interval [-(2^64), 2^64 - 1], or an integer of major type
 0 and 1.
### <a id='FloatingPoint'>FloatingPoint</a>

A 16-, 32-, or 64-bit binary floating-point number.
## Method Details

### <a id='values()'>values</a>

### <a id='valueOf(java.lang.String)'>valueOf</a>
