# com.upokecenter.cbor.CBORDataUtilities

    public final class CBORDataUtilities extends Object

Contains methods useful for reading and writing data, with a focus on CBOR.

## Methods

* `static CBORObject ParseJSONNumber(byte[] bytes)`<br>
 Parses a number from a byte sequence whose format follows the JSON
 specification.

* `static CBORObject ParseJSONNumber(byte[] bytes,
 int offset,
 int count)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259)
 from a portion of a byte sequence, and converts that number to a CBOR
 object.

* `static CBORObject ParseJSONNumber(byte[] bytes,
 int offset,
 int count,
 JSONOptions options)`<br>
 Parses a number from a byte sequence whose format follows the JSON
 specification (RFC 8259) and converts that number to a CBOR object.

* `static CBORObject ParseJSONNumber(byte[] bytes,
 JSONOptions options)`<br>
 Parses a number from a byte sequence whose format follows the JSON
 specification (RFC 8259) and converts that number to a CBOR object.

* `static CBORObject ParseJSONNumber(char[] chars)`<br>
 Parses a number from a sequence of char s whose format follows the
 JSON specification.

* `static CBORObject ParseJSONNumber(char[] chars,
 int offset,
 int count)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259)
 from a portion of a sequence of char s, and converts that number to
 a CBOR object.

* `static CBORObject ParseJSONNumber(char[] chars,
 int offset,
 int count,
 JSONOptions options)`<br>
 Parses a number from a sequence of char s whose format follows
 the JSON specification (RFC 8259) and converts that number to a CBOR
 object.

* `static CBORObject ParseJSONNumber(char[] chars,
 JSONOptions options)`<br>
 Parses a number from a sequence of char s whose format follows
 the JSON specification (RFC 8259) and converts that number to a CBOR
 object.

* `static CBORObject ParseJSONNumber(String str)`<br>
 Parses a number whose format follows the JSON specification.

* `static CBORObject ParseJSONNumber(String str,
 int offset,
 int count)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259)
 from a portion of a text string, and converts that number to a CBOR
 object.

* `static CBORObject ParseJSONNumber(String str,
 int offset,
 int count,
 JSONOptions options)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259)
 and converts that number to a CBOR object.

* `static CBORObject ParseJSONNumber(String str,
 JSONOptions options)`<br>
 Parses a number whose format follows the JSON specification (RFC 8259)
 and converts that number to a CBOR object.

## Method Details

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(String str)

Parses a number whose format follows the JSON specification. The method uses
 a JSONOptions with all default properties.

**Parameters:**

* <code>str</code> - A text string to parse as a JSON number.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the string is null or empty.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(String str, JSONOptions options)

<p>Parses a number whose format follows the JSON specification (RFC 8259)
 and converts that number to a CBOR object.</p> <p>Roughly speaking, a valid
 JSON number consists of an optional minus sign, one or more basic digits
 (starting with 1 to 9 unless there is only one digit and that digit is 0),
 an optional decimal point (".", full stop) with one or more basic digits,
 and an optional letter E or e with an optional plus or minus sign and one or
 more basic digits (the exponent). A text string representing a valid JSON
 number is not allowed to contain white space characters, including
 spaces.</p>

**Parameters:**

* <code>str</code> - A text string to parse as a JSON number.

* <code>options</code> - An object containing options to control how JSON numbers are
 decoded to CBOR objects. Can be null, in which case a JSONOptions object
 with all default properties is used instead.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the string is null or empty.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(String str, int offset, int count)

<p>Parses a number whose format follows the JSON specification (RFC 8259)
 from a portion of a text string, and converts that number to a CBOR
 object.</p> <p>Roughly speaking, a valid JSON number consists of an optional
 minus sign, one or more basic digits (starting with 1 to 9 unless there is
 only one digit and that digit is 0), an optional decimal point (".", full
 stop) with one or more basic digits, and an optional letter E or e with an
 optional plus or minus sign and one or more basic digits (the exponent). A
 text string representing a valid JSON number is not allowed to contain white
 space characters, including spaces.</p>

**Parameters:**

* <code>str</code> - A text string containing the portion to parse as a JSON number.

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>str</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 str</code> (but not more than <code>str</code> 's length).

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the string is null or empty.

**Throws:**

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
 than 0 or greater than <code>str</code> 's length, or <code>str</code> 's length minus
 <code>offset</code> is less than <code>count</code>.

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(String str, int offset, int count, JSONOptions options)

<p>Parses a number whose format follows the JSON specification (RFC 8259)
 and converts that number to a CBOR object.</p> <p>Roughly speaking, a valid
 JSON number consists of an optional minus sign, one or more basic digits
 (starting with 1 to 9 unless there is only one digit and that digit is 0),
 an optional decimal point (".", full stop) with one or more basic digits,
 and an optional letter E or e with an optional plus or minus sign and one or
 more basic digits (the exponent). A text string representing a valid JSON
 number is not allowed to contain white space characters, including
 spaces.</p>

**Parameters:**

* <code>str</code> - A text string to parse as a JSON number.

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>str</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 str</code> (but not more than <code>str</code> 's length).

* <code>options</code> - An object containing options to control how JSON numbers are
 decoded to CBOR objects. Can be null, in which case a JSONOptions object
 with all default properties is used instead.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the string is null or empty or <code>count</code> is
 0 or less.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>IllegalArgumentException</code> - Unsupported conversion kind.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(byte[] bytes, int offset, int count, JSONOptions options)

<p>Parses a number from a byte sequence whose format follows the JSON
 specification (RFC 8259) and converts that number to a CBOR object.</p>
 <p>Roughly speaking, a valid JSON number consists of an optional minus sign,
 one or more basic digits (starting with 1 to 9 unless there is only one
 digit and that digit is 0), an optional decimal point (".", full stop) with
 one or more basic digits, and an optional letter E or e with an optional
 plus or minus sign and one or more basic digits (the exponent). A byte
 sequence representing a valid JSON number is not allowed to contain white
 space characters, including spaces.</p>

**Parameters:**

* <code>bytes</code> - A sequence of bytes to parse as a JSON number.

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>bytes</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 bytes</code> (but not more than <code>bytes</code> 's length).

* <code>options</code> - An object containing options to control how JSON numbers are
 decoded to CBOR objects. Can be null, in which case a JSONOptions object
 with all default properties is used instead.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the byte sequence is null or empty or <code>
 count</code> is 0 or less.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bytes</code> is null.

* <code>IllegalArgumentException</code> - Unsupported conversion kind.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(byte[] bytes, JSONOptions options)

<p>Parses a number from a byte sequence whose format follows the JSON
 specification (RFC 8259) and converts that number to a CBOR object.</p>
 <p>Roughly speaking, a valid JSON number consists of an optional minus sign,
 one or more basic digits (starting with 1 to 9 unless there is only one
 digit and that digit is 0), an optional decimal point (".", full stop) with
 one or more basic digits, and an optional letter E or e with an optional
 plus or minus sign and one or more basic digits (the exponent). A byte
 sequence representing a valid JSON number is not allowed to contain white
 space characters, including spaces.</p>

**Parameters:**

* <code>bytes</code> - A sequence of bytes to parse as a JSON number.

* <code>options</code> - An object containing options to control how JSON numbers are
 decoded to CBOR objects. Can be null, in which case a JSONOptions object
 with all default properties is used instead.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the byte sequence is null or empty.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(byte[] bytes, int offset, int count)

<p>Parses a number whose format follows the JSON specification (RFC 8259)
 from a portion of a byte sequence, and converts that number to a CBOR
 object.</p> <p>Roughly speaking, a valid JSON number consists of an optional
 minus sign, one or more basic digits (starting with 1 to 9 unless there is
 only one digit and that digit is 0), an optional decimal point (".", full
 stop) with one or more basic digits, and an optional letter E or e with an
 optional plus or minus sign and one or more basic digits (the exponent). A
 byte sequence representing a valid JSON number is not allowed to contain
 white space characters, including spaces.</p>

**Parameters:**

* <code>bytes</code> - A sequence of bytes to parse as a JSON number.

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>bytes</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 bytes</code> (but not more than <code>bytes</code> 's length).

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the byte sequence is null or empty.

**Throws:**

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
 than 0 or greater than <code>bytes</code> 's length, or <code>bytes</code> 's length
 minus <code>offset</code> is less than <code>count</code>.

* <code>NullPointerException</code> - The parameter <code>bytes</code> is null.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(byte[] bytes)

Parses a number from a byte sequence whose format follows the JSON
 specification. The method uses a JSONOptions with all default properties.

**Parameters:**

* <code>bytes</code> - A byte sequence to parse as a JSON number.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the byte sequence is null or empty.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(char[] chars, int offset, int count, JSONOptions options)

<p>Parses a number from a sequence of <code>char</code> s whose format follows
 the JSON specification (RFC 8259) and converts that number to a CBOR
 object.</p> <p>Roughly speaking, a valid JSON number consists of an optional
 minus sign, one or more basic digits (starting with 1 to 9 unless there is
 only one digit and that digit is 0), an optional decimal point (".", full
 stop) with one or more basic digits, and an optional letter E or e with an
 optional plus or minus sign and one or more basic digits (the exponent). A
 sequence of <code>char</code> s representing a valid JSON number is not allowed
 to contain white space characters, including spaces.</p>

**Parameters:**

* <code>chars</code> - A sequence of <code>char</code> s to parse as a JSON number.

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>chars</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 chars</code> (but not more than <code>chars</code> 's length).

* <code>options</code> - An object containing options to control how JSON numbers are
 decoded to CBOR objects. Can be null, in which case a JSONOptions object
 with all default properties is used instead.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the sequence of <code>char</code> s is null or empty
 or <code>count</code> is 0 or less.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>chars</code> is null.

* <code>IllegalArgumentException</code> - Unsupported conversion kind.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(char[] chars, JSONOptions options)

<p>Parses a number from a sequence of <code>char</code> s whose format follows
 the JSON specification (RFC 8259) and converts that number to a CBOR
 object.</p> <p>Roughly speaking, a valid JSON number consists of an optional
 minus sign, one or more basic digits (starting with 1 to 9 unless there is
 only one digit and that digit is 0), an optional decimal point (".", full
 stop) with one or more basic digits, and an optional letter E or e with an
 optional plus or minus sign and one or more basic digits (the exponent). A
 sequence of <code>char</code> s representing a valid JSON number is not allowed
 to contain white space characters, including spaces.</p>

**Parameters:**

* <code>chars</code> - A sequence of <code>char</code> s to parse as a JSON number.

* <code>options</code> - An object containing options to control how JSON numbers are
 decoded to CBOR objects. Can be null, in which case a JSONOptions object
 with all default properties is used instead.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the sequence of <code>char</code> s is null or empty.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(char[] chars, int offset, int count)

<p>Parses a number whose format follows the JSON specification (RFC 8259)
 from a portion of a sequence of <code>char</code> s, and converts that number to
 a CBOR object.</p> <p>Roughly speaking, a valid JSON number consists of an
 optional minus sign, one or more basic digits (starting with 1 to 9 unless
 there is only one digit and that digit is 0), an optional decimal point
 (".", full stop) with one or more basic digits, and an optional letter E or
 e with an optional plus or minus sign and one or more basic digits (the
 exponent). A sequence of <code>char</code> s representing a valid JSON number is
 not allowed to contain white space characters, including spaces.</p>

**Parameters:**

* <code>chars</code> - A sequence of <code>char</code> s to parse as a JSON number.

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>chars</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 chars</code> (but not more than <code>chars</code> 's length).

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the sequence of <code>char</code> s is null or empty.

**Throws:**

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
 than 0 or greater than <code>chars</code> 's length, or <code>chars</code> 's length
 minus <code>offset</code> is less than <code>count</code>.

* <code>NullPointerException</code> - The parameter <code>chars</code> is null.

### ParseJSONNumber

    public static CBORObject ParseJSONNumber(char[] chars)

Parses a number from a sequence of <code>char</code> s whose format follows the
 JSON specification. The method uses a JSONOptions with all default
 properties.

**Parameters:**

* <code>chars</code> - A sequence of <code>char</code> s to parse as a JSON number.

**Returns:**

* A CBOR object that represents the parsed number. Returns null if the
 parsing fails, including if the sequence of <code>char</code> s is null or empty.
