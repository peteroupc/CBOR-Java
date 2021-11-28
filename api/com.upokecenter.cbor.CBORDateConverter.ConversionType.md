# com.upokecenter.cbor.CBORDateConverter.ConversionType

## Nested Classes

## Enum Constants

* `TaggedNumber`<br>
 FromCBORObject accepts objects with tag 0 (date/time strings) and tag 1
 (number of seconds since the start of 1970), and ToCBORObject
 converts date/time objects to CBOR objects of tag 1.
* `TaggedString`<br>
 FromCBORObject accepts CBOR objects with tag 0 (date/time strings) and tag 1
 (number of seconds since the start of 1970), and ToCBORObject
 converts date/time objects to CBOR objects of tag 0.
* `UntaggedNumber`<br>
 FromCBORObject accepts untagged CBOR integer or CBOR floating-point objects
 that give the number of seconds since the start of 1970, and
 ToCBORObject converts date/time objects (java.util.Date in DotNet, and
 Date in Java) to such untagged CBOR objects.

## Methods

* `static CBORDateConverter.ConversionType valueOf​(java.lang.String name)`<br>
 Returns the enum constant of this type with the specified name.
* `static CBORDateConverter.ConversionType[] values()`<br>
 Returns an array containing the constants of this enum type, in
the order they are declared.

## Enum Constant Details

### <a id='TaggedString'>TaggedString</a>

FromCBORObject accepts CBOR objects with tag 0 (date/time strings) and tag 1
 (number of seconds since the start of 1970), and ToCBORObject
 converts date/time objects to CBOR objects of tag 0.
### <a id='TaggedNumber'>TaggedNumber</a>

FromCBORObject accepts objects with tag 0 (date/time strings) and tag 1
 (number of seconds since the start of 1970), and ToCBORObject
 converts date/time objects to CBOR objects of tag 1. The
 ToCBORObject conversion is lossless only if the number of seconds
 since the start of 1970 can be represented exactly as an integer
 in the interval [-(2^64), 2^64 - 1] or as a 64-bit floating-point
 number in the IEEE 754r binary64 format; the conversion is lossy
 otherwise. The ToCBORObject conversion will throw an exception if
 the conversion to binary64 results in positive infinity, negative
 infinity, or not-a-number.
### <a id='UntaggedNumber'>UntaggedNumber</a>

FromCBORObject accepts untagged CBOR integer or CBOR floating-point objects
 that give the number of seconds since the start of 1970, and
 ToCBORObject converts date/time objects (java.util.Date in DotNet, and
 Date in Java) to such untagged CBOR objects. The ToCBORObject
 conversion is lossless only if the number of seconds since the
 start of 1970 can be represented exactly as an integer in the
 interval [-(2^64), 2^64 - 1] or as a 64-bit floating-point number
 in the IEEE 754r binary64 format; the conversion is lossy
 otherwise. The ToCBORObject conversion will throw an exception if
 the conversion to binary64 results in positive infinity, negative
 infinity, or not-a-number.
## Method Details

### <a id='values()'>values</a>

### <a id='valueOf(java.lang.String)'>valueOf</a>
