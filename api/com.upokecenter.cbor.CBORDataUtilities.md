# com.upokecenter.cbor.CBORDataUtilities

    public final class CBORDataUtilities extends java.lang.Object

Contains methods useful for reading and writing data, with a focus on CBOR.

## Methods

* `static CBORObject ParseJSONNumber​(java.lang.String str)`<br>
 Parses a number whose format follows the JSON specification.
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               boolean integersOnly,
               boolean positiveOnly)`<br>
 Deprecated.
Call the one-argument version of this method instead.
 Call the one-argument version of this method instead.
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               boolean integersOnly,
               boolean positiveOnly,
               boolean preserveNegativeZero)`<br>
 Parses a number whose format follows the JSON specification (RFC
 8259).
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               int offset,
               int count,
               JSONOptions options)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259) and
 converts that number to a CBOR object.
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               JSONOptions options)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259) and
 converts that number to a CBOR object.

## Method Details

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str)
Parses a number whose format follows the JSON specification. The method uses
 a JSONOptions with all default properties except for a
 PreserveNegativeZero property of false.

**Parameters:**

* <code>str</code> - A text string to parse as a JSON string.

**Returns:**

* A CBOR object that represents the parsed number. Returns positive
 zero if the number is a zero that starts with a minus sign (such as
  "-0" or "-0.0"). Returns null if the parsing fails, including if the
 string is null or empty.

### ParseJSONNumber
    @Deprecated public static CBORObject ParseJSONNumber​(java.lang.String str, boolean integersOnly, boolean positiveOnly)
Deprecated.
Call the one-argument version of this method instead. If this method
 call used positiveOnly = true, check that the String does
 not begin with '-' before calling that version. If this method
 call used integersOnly  = true, check that the String does not
 contain '.', 'E', or 'e' before calling that version.

**Parameters:**

* <code>str</code> - A text string to parse as a JSON number.

* <code>integersOnly</code> - If true, no decimal points or exponents are allowed in
 the string. The default is false.

* <code>positiveOnly</code> - If true, only positive numbers are allowed (the leading
 minus is disallowed). The default is false.

**Returns:**

* A CBOR object that represents the parsed number. Returns positive
 zero if the number is a zero that starts with a minus sign (such as
  "-0" or "-0.0"). Returns null if the parsing fails, including if the
 string is null or empty.

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str, boolean integersOnly, boolean positiveOnly, boolean preserveNegativeZero)
Parses a number whose format follows the JSON specification (RFC
 8259).<p>Roughly speaking, a valid JSON number consists of an
 optional minus sign, one or more basic digits (starting with 1 to 9
 unless there is only one digit and that digit is 0), an optional
  decimal point (".", full stop) with one or more basic digits, and an
 optional letter E or e with an optional plus or minus sign and one
 or more basic digits (the exponent). A string representing a valid
 JSON number is not allowed to contain white space characters,
 including spaces.</p>

**Parameters:**

* <code>str</code> - A text string to parse as a JSON number.

* <code>integersOnly</code> - If true, no decimal points or exponents are allowed in
 the string. The default is false.

* <code>positiveOnly</code> - If true, the leading minus is disallowed in the string.
 The default is false.

* <code>preserveNegativeZero</code> - If true, returns positive zero if the number is
  a zero that starts with a minus sign (such as "-0" or "-0.0").
 Otherwise, returns negative zero in this case. The default is false.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the string is null or empty.

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str, JSONOptions options)
Parses a number whose format follows the JSON specification (RFC 8259) and
 converts that number to a CBOR object.<p>Roughly speaking, a valid
 JSON number consists of an optional minus sign, one or more basic
 digits (starting with 1 to 9 unless there is only one digit and that
  digit is 0), an optional decimal point (".", full stop) with one or
 more basic digits, and an optional letter E or e with an optional
 plus or minus sign and one or more basic digits (the exponent). A
 string representing a valid JSON number is not allowed to contain
 white space characters, including spaces.</p>

**Parameters:**

* <code>str</code> - A text string to parse as a JSON number.

* <code>options</code> - An object containing options to control how JSON numbers are
 decoded to CBOR objects. Can be null, in which case a JSONOptions
 object with all default properties is used instead.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the string is null or empty.

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str, int offset, int count, JSONOptions options)
Parses a number whose format follows the JSON specification (RFC 8259) and
 converts that number to a CBOR object.<p>Roughly speaking, a valid
 JSON number consists of an optional minus sign, one or more basic
 digits (starting with 1 to 9 unless there is only one digit and that
  digit is 0), an optional decimal point (".", full stop) with one or
 more basic digits, and an optional letter E or e with an optional
 plus or minus sign and one or more basic digits (the exponent). A
 string representing a valid JSON number is not allowed to contain
 white space characters, including spaces.</p>

**Parameters:**

* <code>str</code> - A text string to parse as a JSON number.

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>str</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 str</code> (but not more than <code>str</code> 's length).

* <code>options</code> - An object containing options to control how JSON numbers are
 decoded to CBOR objects. Can be null, in which case a JSONOptions
 object with all default properties is used instead.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the string is null or empty or <code>
 count</code> is 0 or less.

**Throws:**

* <code>java.lang.IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
 than 0 or greater than <code>str</code> 's length, or <code>str</code> 's
 length minus <code>offset</code> is less than <code>count</code>.

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.
