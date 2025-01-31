# com.upokecenter.cbor.CBORDateConverter.ConversionType

    public static enum CBORDateConverter.ConversionType extends Enum<CBORDateConverter.ConversionType>

Conversion type for date-time conversion.

## Nested Classes

## Enum Constants

* `TaggedNumber `<br>
 FromCBORObject accepts objects with tag 0 (date/time strings) and tag 1
 (number of seconds since the start of 1970), and ToCBORObject converts
 date/time objects to CBOR objects of tag 1.

* `TaggedString `<br>
 FromCBORObject accepts CBOR objects with tag 0 (date/time strings) and tag 1
 (number of seconds since the start of 1970), and ToCBORObject converts
 date/time objects to CBOR objects of tag 0.

* `UntaggedNumber `<br>
 FromCBORObject accepts untagged CBOR integer or CBOR floating-point objects
 that give the number of seconds since the start of 1970, and ToCBORObject
 converts date/time objects (java.util.Date in DotNet, and Date in Java) to such
 untagged CBOR objects.

## Methods

* `static CBORDateConverter.ConversionType valueOf(String name)`<br>
 Returns the enum constant of this class with the specified name.

* `static CBORDateConverter.ConversionType[] values()`<br>
 Returns an array containing the constants of this enum class, in
the order they are declared.

## Method Details

### values

    public static CBORDateConverter.ConversionType[] values()

### valueOf

    public static CBORDateConverter.ConversionType valueOf(String name)
