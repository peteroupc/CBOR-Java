# com.upokecenter.cbor.CBORDateConverter

    public final class CBORDateConverter extends java.lang.Object implements ICBORToFromConverter<java.util.Date>

<p>A class for converting date-time objects to and from tagged CBOR
  objects.</p> <p>In this method's documentation, the "number of seconds
  since the start of 1970" is based on the POSIX definition of "seconds
  since the Epoch", a definition that does not count leap seconds. This
 number of seconds assumes the use of a proleptic Gregorian calendar,
 in which the rules regarding the number of days in each month and
 which years are leap years are the same for all years as they were in
 1970 (including without regard to time zone differences or transitions
 from other calendars to the Gregorian).</p>

## Fields

* `static class  CBORDateConverter.ConversionType`<br>
 Conversion type for date-time conversion.
* `static CBORDateConverter TaggedNumber`<br>
 A converter object where FromCBORObject accepts CBOR objects with tag 0
 (date/time strings) and tag 1 (number of seconds since the start
 of 1970), and ToCBORObject converts date/time objects (java.util.Date
 in DotNet, and Date in Java) to CBOR objects of tag 1.
* `static CBORDateConverter TaggedString`<br>
 A converter object where FromCBORObject accepts CBOR objects with tag 0
 (date/time strings) and tag 1 (number of seconds since the start
 of 1970), and ToCBORObject converts date/time objects (java.util.Date
 in DotNet, and Date in Java) to CBOR objects of tag 0.
* `static CBORDateConverter UntaggedNumber`<br>
 A converter object where FromCBORObject accepts untagged CBOR integer or
 CBOR floating-point objects that give the number of seconds since
 the start of 1970, and where ToCBORObject converts date/time
 objects (java.util.Date in DotNet, and Date in Java) to such untagged
 CBOR objects.

## Nested Classes

* `static class  CBORDateConverter.ConversionType`<br>
 Conversion type for date-time conversion.

## Constructors

* `CBORDateConverter() CBORDateConverter`<br>
 Initializes a new instance of the CBORDateConverter class.
* `CBORDateConverter​(CBORDateConverter.ConversionType convType) CBORDateConverter`<br>
 Initializes a new instance of the CBORDateConverter class.

## Methods

* `java.util.Date FromCBORObject​(CBORObject obj)`<br>
 Not documented yet.
* `CBORObject ToCBORObject​(java.util.Date obj)`<br>
 Not documented yet.

## Field Details

### TaggedString
    public static final CBORDateConverter TaggedString
A converter object where FromCBORObject accepts CBOR objects with tag 0
 (date/time strings) and tag 1 (number of seconds since the start
 of 1970), and ToCBORObject converts date/time objects (java.util.Date
 in DotNet, and Date in Java) to CBOR objects of tag 0.
### TaggedNumber
    public static final CBORDateConverter TaggedNumber
A converter object where FromCBORObject accepts CBOR objects with tag 0
 (date/time strings) and tag 1 (number of seconds since the start
 of 1970), and ToCBORObject converts date/time objects (java.util.Date
 in DotNet, and Date in Java) to CBOR objects of tag 1. The
 ToCBORObject conversion is lossless only if the number of seconds
 since the start of 1970 can be represented exactly as an integer
 in the interval [-(2^64), 2^64 - 1] or as a 64-bit floating-point
 number in the IEEE 754r binary64 format; the conversion is lossy
 otherwise. The ToCBORObject conversion will throw an exception if
 the conversion to binary64 results in positive infinity, negative
 infinity, or not-a-number.
### UntaggedNumber
    public static final CBORDateConverter UntaggedNumber
A converter object where FromCBORObject accepts untagged CBOR integer or
 CBOR floating-point objects that give the number of seconds since
 the start of 1970, and where ToCBORObject converts date/time
 objects (java.util.Date in DotNet, and Date in Java) to such untagged
 CBOR objects. The ToCBORObject conversion is lossless only if the
 number of seconds since the start of 1970 can be represented
 exactly as an integer in the interval [-(2^64), 2^64 - 1] or as a
 64-bit floating-point number in the IEEE 754r binary64 format;
 the conversion is lossy otherwise. The ToCBORObject conversion
 will throw an exception if the conversion to binary64 results in
 positive infinity, negative infinity, or not-a-number.
## Method Details

### FromCBORObject
    public java.util.Date FromCBORObject​(CBORObject obj)
Not documented yet.

**Specified by:**

* <code>FromCBORObject</code> in interface <code>ICBORToFromConverter&lt;java.util.Date&gt;</code>

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is a Cbor.CBORObject object.

**Returns:**

* The return value is not documented yet.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>obj</code> is null.

### ToCBORObject
    public CBORObject ToCBORObject​(java.util.Date obj)
Not documented yet.

**Specified by:**

* <code>ToCBORObject</code> in interface <code>ICBORConverter&lt;java.util.Date&gt;</code>

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is a java.util.Date object.

**Returns:**

* The return value is not documented yet.
