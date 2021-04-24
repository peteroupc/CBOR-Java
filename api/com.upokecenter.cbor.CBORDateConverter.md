# com.upokecenter.cbor.CBORDateConverter

    public final class CBORDateConverter extends java.lang.Object implements ICBORToFromConverter<java.util.Date>

<p>A class for converting date-time objects to and from tagged CBOR
  objects.</p> <p>In this class's documentation, the "number of seconds
  since the start of 1970" is based on the POSIX definition of "seconds
  since the Epoch", a definition that does not count leap seconds. This
 number of seconds assumes the use of a proleptic Gregorian calendar,
 in which the rules regarding the number of days in each month and
 which years are leap years are the same for all years as they were in
 1970 (including without regard to time zone differences or transitions
 from other calendars to the Gregorian).</p>

## Fields

- `static class CBORDateConverter.ConversionType`<br>
  Conversion type for date-time conversion.
- `static CBORDateConverter TaggedNumber`<br>
  A converter object where FromCBORObject accepts CBOR objects with tag 0
  (date/time strings) and tag 1 (number of seconds since the start of
  1970), and ToCBORObject converts date/time objects (java.util.Date in
  DotNet, and Date in Java) to CBOR objects of tag 1.
- `static CBORDateConverter TaggedString`<br>
  A converter object where FromCBORObject accepts CBOR objects with tag 0
  (date/time strings) and tag 1 (number of seconds since the start of
  1970), and ToCBORObject converts date/time objects (java.util.Date in
  DotNet, and Date in Java) to CBOR objects of tag 0.
- `static CBORDateConverter UntaggedNumber`<br>
  A converter object where FromCBORObject accepts untagged CBOR integer or
  CBOR floating-point objects that give the number of seconds since
  the start of 1970, and where ToCBORObject converts date/time objects
  (java.util.Date in DotNet, and Date in Java) to such untagged CBOR
  objects.

## Nested Classes

- `static class CBORDateConverter.ConversionType`<br>
  Conversion type for date-time conversion.

## Constructors

- `CBORDateConverter() CBORDateConverter`<br>
  Initializes a new instance of the CBORDateConverter class.
- `CBORDateConverter​(CBORDateConverter.ConversionType convType) CBORDateConverter`<br>
  Initializes a new instance of the CBORDateConverter class.

## Methods

- `CBORObject DateTimeFieldsToCBORObject​(int smallYear, int month, int day)`<br>
  Converts a date/time in the form of a year, month, and day to a CBOR object.
- `CBORObject DateTimeFieldsToCBORObject​(int smallYear, int month, int day, int hour, int minute, int second)`<br>
  Converts a date/time in the form of a year, month, day, hour, minute, and
  second to a CBOR object.
- `CBORObject DateTimeFieldsToCBORObject​(com.upokecenter.numbers.EInteger bigYear, int[] lesserFields)`<br>
  Converts a date/time in the form of a year, month, day, hour, minute,
  second, fractional seconds, and time offset to a CBOR object.
- `java.util.Date FromCBORObject​(CBORObject obj)`<br>
  Converts a CBOR object to a java.util.Date (in DotNet) or a Date (in Java).
- `CBORDateConverter.ConversionType getType()`<br>
  Gets the conversion type for this date converter.
- `CBORObject ToCBORObject​(java.util.Date obj)`<br>
  Converts a java.util.Date (in DotNet) or Date (in Java) to a CBOR object in a
  manner specified by this converter's conversion type.
- `boolean TryGetDateTimeFields​(CBORObject obj, com.upokecenter.numbers.EInteger[] year, int[] lesserFields)`<br>
  Tries to extract the fields of a date and time in the form of a CBOR object.

## Field Details

### TaggedString

    public static final CBORDateConverter TaggedString

A converter object where FromCBORObject accepts CBOR objects with tag 0
(date/time strings) and tag 1 (number of seconds since the start of
1970), and ToCBORObject converts date/time objects (java.util.Date in
DotNet, and Date in Java) to CBOR objects of tag 0.

### TaggedNumber

    public static final CBORDateConverter TaggedNumber

A converter object where FromCBORObject accepts CBOR objects with tag 0
(date/time strings) and tag 1 (number of seconds since the start of
1970), and ToCBORObject converts date/time objects (java.util.Date in
DotNet, and Date in Java) to CBOR objects of tag 1. The ToCBORObject
conversion is lossless only if the number of seconds since the start
of 1970 can be represented exactly as an integer in the interval
[-(2^64), 2^64 - 1] or as a 64-bit floating-point number in the IEEE
754r binary64 format; the conversion is lossy otherwise. The
ToCBORObject conversion will throw an exception if the conversion to
binary64 results in positive infinity, negative infinity, or
not-a-number.

### UntaggedNumber

    public static final CBORDateConverter UntaggedNumber

A converter object where FromCBORObject accepts untagged CBOR integer or
CBOR floating-point objects that give the number of seconds since
the start of 1970, and where ToCBORObject converts date/time objects
(java.util.Date in DotNet, and Date in Java) to such untagged CBOR
objects. The ToCBORObject conversion is lossless only if the number
of seconds since the start of 1970 can be represented exactly as an
integer in the interval [-(2^64), 2^64 - 1] or as a 64-bit
floating-point number in the IEEE 754r binary64 format; the
conversion is lossy otherwise. The ToCBORObject conversion will
throw an exception if the conversion to binary64 results in positive
infinity, negative infinity, or not-a-number.

## Method Details

### getType

    public final CBORDateConverter.ConversionType getType()

Gets the conversion type for this date converter.

**Returns:**

- The conversion type for this date converter.

### FromCBORObject

    public java.util.Date FromCBORObject​(CBORObject obj)

Converts a CBOR object to a java.util.Date (in DotNet) or a Date (in Java).

**Specified by:**

- <code>FromCBORObject</code> in interface <code>ICBORToFromConverter&lt;java.util.Date&gt;</code>

**Parameters:**

- <code>obj</code> - A CBOR object that specifies a date/time according to the
  conversion type used to create this date converter.

**Returns:**

- A java.util.Date or Date that encodes the date/time specified in the CBOR
  object.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>obj</code> is null.

- <code>CBORException</code> - The format of the CBOR object is
  not supported, or another error occurred in conversion.

### TryGetDateTimeFields

    public boolean TryGetDateTimeFields​(CBORObject obj, com.upokecenter.numbers.EInteger[] year, int[] lesserFields)

Tries to extract the fields of a date and time in the form of a CBOR object.

**Parameters:**

- <code>obj</code> - A CBOR object that specifies a date/time according to the
  conversion type used to create this date converter.

- <code>year</code> - An array whose first element will store the year. The array's
  length must be 1 or greater. If this function fails, the first
  element is set to null.

- <code>lesserFields</code> - An array that will store the fields (other than the
year) of the date and time. The array's length must be 7 or greater.
If this function fails, the first seven elements are set to 0. If
this method is successful, the first seven elements of the array
(starting at 0) will be as follows: <ul> <li>0 - Month of the year,
from 1 (January) through 12 (December).</li> <li>1 - Day of the
month, from 1 through 31.</li> <li>2 - Hour of the day, from 0
through 23.</li> <li>3 - Minute of the hour, from 0 through 59.</li>
<li>4 - Second of the minute, from 0 through 59.</li> <li>5 -
Fractional seconds, expressed in nanoseconds. This value cannot be
less than 0.</li> <li>6 - Number of minutes to subtract from this
date and time to get global time. This number can be positive or
negative, but cannot be less than -1439 or greater than 1439. For
tags 0 and 1, this value is always 0.</li></ul>.

**Returns:**

- Either <code>true</code> if the method is successful, or <code>false</code>
  otherwise.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>year</code> or <code>
  lesserFields</code> is null, or contains fewer elements than required.

### DateTimeFieldsToCBORObject

    public CBORObject DateTimeFieldsToCBORObject​(int smallYear, int month, int day)

Converts a date/time in the form of a year, month, and day to a CBOR object.
The hour, minute, and second are treated as 00:00:00 by this method,
and the time offset is treated as 0 by this method.

**Parameters:**

- <code>smallYear</code> - The year.

- <code>month</code> - Month of the year, from 1 (January) through 12 (December).

- <code>day</code> - Day of the month, from 1 through 31.

**Returns:**

- A CBOR object encoding the given date fields according to the
  conversion type used to create this date converter.

**Throws:**

- <code>CBORException</code> - An error occurred in conversion.

### DateTimeFieldsToCBORObject

    public CBORObject DateTimeFieldsToCBORObject​(int smallYear, int month, int day, int hour, int minute, int second)

Converts a date/time in the form of a year, month, day, hour, minute, and
second to a CBOR object. The time offset is treated as 0 by this
method.

**Parameters:**

- <code>smallYear</code> - The year.

- <code>month</code> - Month of the year, from 1 (January) through 12 (December).

- <code>day</code> - Day of the month, from 1 through 31.

- <code>hour</code> - Hour of the day, from 0 through 23.

- <code>minute</code> - Minute of the hour, from 0 through 59.

- <code>second</code> - Second of the minute, from 0 through 59.

**Returns:**

- A CBOR object encoding the given date fields according to the
  conversion type used to create this date converter.

**Throws:**

- <code>CBORException</code> - An error occurred in conversion.

### DateTimeFieldsToCBORObject

    public CBORObject DateTimeFieldsToCBORObject​(com.upokecenter.numbers.EInteger bigYear, int[] lesserFields)

Converts a date/time in the form of a year, month, day, hour, minute,
second, fractional seconds, and time offset to a CBOR object.

**Parameters:**

- <code>bigYear</code> - The parameter <code>bigYear</code> is a Numbers.EInteger object.

- <code>lesserFields</code> - An array that will store the fields (other than the
  year) of the date and time. See the TryGetDateTimeFields method for
  information on the "lesserFields" parameter.

**Returns:**

- A CBOR object encoding the given date fields according to the
  conversion type used to create this date converter.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bigYear</code> or <code>
  lesserFields</code> is null.

- <code>CBORException</code> - An error occurred in conversion.

### ToCBORObject

    public CBORObject ToCBORObject​(java.util.Date obj)

Converts a java.util.Date (in DotNet) or Date (in Java) to a CBOR object in a
manner specified by this converter's conversion type.

**Specified by:**

- <code>ToCBORObject</code> in interface <code>ICBORConverter&lt;java.util.Date&gt;</code>

**Parameters:**

- <code>obj</code> - The parameter <code>obj</code> is a java.util.Date object.

**Returns:**

- A CBOR object encoding the date/time in the java.util.Date or Date
  according to the conversion type used to create this date converter.

**Throws:**

- <code>CBORException</code> - An error occurred in conversion.
