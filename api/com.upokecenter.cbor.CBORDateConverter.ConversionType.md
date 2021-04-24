# com.upokecenter.cbor.CBORDateConverter.ConversionType

    public static enum CBORDateConverter.ConversionType extends java.lang.Enum<CBORDateConverter.ConversionType>

Conversion type for date-time conversion.

## Enum Constants

- `TaggedNumber`<br>
  FromCBORObject accepts objects with tag 0 (date/time strings) and tag 1
  (number of seconds since the start of 1970), and ToCBORObject
  converts date/time objects to CBOR objects of tag 1.
- `TaggedString`<br>
  FromCBORObject accepts CBOR objects with tag 0 (date/time strings) and tag 1
  (number of seconds since the start of 1970), and ToCBORObject
  converts date/time objects to CBOR objects of tag 0.
- `UntaggedNumber`<br>
  FromCBORObject accepts untagged CBOR integer or CBOR floating-point objects
  that give the number of seconds since the start of 1970, and
  ToCBORObject converts date/time objects (java.util.Date in DotNet, and
  Date in Java) to such untagged CBOR objects.

## Methods

- `static CBORDateConverter.ConversionType valueOf​(java.lang.String name)`<br>
  Returns the enum constant of this type with the specified name.
- `static CBORDateConverter.ConversionType[] values()`<br>
  Returns an array containing the constants of this enum type, in
  the order they are declared.

## Method Details

### TaggedString

    public static final CBORDateConverter.ConversionType TaggedString

### TaggedNumber

    public static final CBORDateConverter.ConversionType TaggedNumber

### UntaggedNumber

    public static final CBORDateConverter.ConversionType UntaggedNumber

### values

    public static CBORDateConverter.ConversionType[] values()

### valueOf

    public static CBORDateConverter.ConversionType valueOf​(java.lang.String name)

## Enum Constant Details

### TaggedString

    public static final CBORDateConverter.ConversionType TaggedString

FromCBORObject accepts CBOR objects with tag 0 (date/time strings) and tag 1
(number of seconds since the start of 1970), and ToCBORObject
converts date/time objects to CBOR objects of tag 0.

### TaggedNumber

    public static final CBORDateConverter.ConversionType TaggedNumber

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

### UntaggedNumber

    public static final CBORDateConverter.ConversionType UntaggedNumber

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
