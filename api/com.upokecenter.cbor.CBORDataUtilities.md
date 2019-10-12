# com.upokecenter.cbor.CBORDataUtilities

    public final class CBORDataUtilities extends java.lang.Object

Contains methods useful for reading and writing data, with a focus on CBOR.

## Methods

* `static double ParseJSONDouble​(java.lang.String str)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259), in
 the form of a 64-bit binary floating-point number.
* `static double ParseJSONDouble​(java.lang.String str,
               boolean preserveNegativeZero)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259), in
 the form of a 64-bit binary floating-point number.
* `static CBORObject ParseJSONNumber​(java.lang.String str)`<br>
 Parses a number whose format follows the JSON specification.
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               boolean integersOnly,
               boolean positiveOnly)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259).
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               boolean integersOnly,
               boolean positiveOnly,
               boolean preserveNegativeZero)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259).

## Method Details

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str)
Parses a number whose format follows the JSON specification. See
 #ParseJSONNumber(string, integersOnly, parseOnly) for more
 information.

**Parameters:**

* <code>str</code> - A string to parse. The string is not allowed to contain white
 space characters, including spaces.

**Returns:**

* A CBOR object that represents the parsed number. Returns positive
 zero if the number is a zero that starts with a minus sign (such as
  "-0" or "-0.0"). Returns null if the parsing fails, including if the
 string is null or empty.

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str, boolean integersOnly, boolean positiveOnly)
Parses a number whose format follows the JSON specification (RFC 8259).
 Roughly speaking, a valid number consists of an optional minus sign,
 one or more basic digits (starting with 1 to 9 unless the only digit
  is 0), an optional decimal point (".", full stop) with one or more
 basic digits, and an optional letter E or e with an optional plus or
 minus sign and one or more basic digits (the exponent).

**Parameters:**

* <code>str</code> - A string to parse. The string is not allowed to contain white
 space characters, including spaces.

* <code>integersOnly</code> - If true, no decimal points or exponents are allowed in
 the string. The default is false.

* <code>positiveOnly</code> - If true, only positive numbers are allowed (the leading
 minus is disallowed). The default is false.

**Returns:**

* A CBOR object that represents the parsed number. Returns positive
 zero if the number is a zero that starts with a minus sign (such as
  "-0" or "-0.0"). Returns null if the parsing fails, including if the
 string is null or empty.

### ParseJSONDouble
    public static double ParseJSONDouble​(java.lang.String str)
Parses a number whose format follows the JSON specification (RFC 8259), in
 the form of a 64-bit binary floating-point number. See
 #ParseJSONDouble(string, preserveNegativeZero) for more information.

**Parameters:**

* <code>str</code> - A string to parse. The string is not allowed to contain white
 space characters, including spaces.

**Returns:**

* A 64-bit binary floating-point number parsed from the given string.
 Returns NaN if the parsing fails, including if the string is null or
 empty. (To check for NaN, use <code>Double.isNaN()</code> in.NET or
 <code>Double.isNaN()</code> in Java.)

### ParseJSONDouble
    public static double ParseJSONDouble​(java.lang.String str, boolean preserveNegativeZero)
Parses a number whose format follows the JSON specification (RFC 8259), in
 the form of a 64-bit binary floating-point number. Roughly speaking,
 a valid number consists of an optional minus sign, one or more basic
 digits (starting with 1 to 9 unless the only digit is 0), an
  optional decimal point (".", full stop) with one or more basic
 digits, and an optional letter E or e with an optional plus or minus
 sign and one or more basic digits (the exponent).

**Parameters:**

* <code>str</code> - A string to parse. The string is not allowed to contain white
 space characters, including spaces.

* <code>preserveNegativeZero</code> - If true, returns positive zero if the number is
  a zero that starts with a minus sign (such as "-0" or "-0.0").
 Otherwise, returns negative zero in this case. The default is false.

**Returns:**

* A 64-bit binary floating-point number parsed from the given string.
 Returns NaN if the parsing fails, including if the string is null or
 empty. (To check for NaN, use <code>Double.isNaN()</code> in.NET or
 <code>Double.isNaN()</code> in Java.)

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str, boolean integersOnly, boolean positiveOnly, boolean preserveNegativeZero)
Parses a number whose format follows the JSON specification (RFC 8259).
 Roughly speaking, a valid number consists of an optional minus sign,
 one or more basic digits (starting with 1 to 9 unless the only digit
  is 0), an optional decimal point (".", full stop) with one or more
 basic digits, and an optional letter E or e with an optional plus or
 minus sign and one or more basic digits (the exponent).

**Parameters:**

* <code>str</code> - A string to parse. The string is not allowed to contain white
 space characters, including spaces.

* <code>integersOnly</code> - If true, no decimal points or exponents are allowed in
 the string. The default is false.

* <code>positiveOnly</code> - If true, only positive numbers are allowed (the leading
 minus is disallowed). The default is false.

* <code>preserveNegativeZero</code> - If true, returns positive zero if the number is
  a zero that starts with a minus sign (such as "-0" or "-0.0").
 Otherwise, returns negative zero in this case. The default is false.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the string is null or empty.
