# com.upokecenter.cbor.JSONOptions

## Nested Classes

* `static class  JSONOptions.ConversionMode FromJSONString ReadJSON`<br>
 Specifies how JSON numbers are converted to CBOR objects when decoding JSON
 (such as via FromJSONString or ReadJSON).

## Fields

* `static JSONOptions Default`<br>
 The default options for converting CBOR objects to JSON.

## Constructors

* `JSONOptions() JSONOptions`<br>
 Initializes a new instance of the JSONOptions
 class with default options.
* `JSONOptions​(boolean base64Padding)`<br>
 Deprecated.
Use the more readable String constructor instead.
 Use the more readable String constructor instead.
* `JSONOptions​(boolean base64Padding,
boolean replaceSurrogates)`<br>
 Deprecated.
* `JSONOptions​(java.lang.String paramString) JSONOptions`<br>
 Initializes a new instance of the JSONOptions
 class.

## Methods

* `boolean getAllowDuplicateKeys()`<br>
 Gets a value indicating whether to allow duplicate keys when reading JSON.
* `boolean getBase64Padding()`<br>
 Deprecated.
This property now has no effect.
 This property now has no effect.
* `JSONOptions.ConversionMode getNumberConversion()`<br>
 Gets a value indicating how JSON numbers are decoded to CBOR objects.
* `boolean getPreserveNegativeZero()`<br>
 Gets a value indicating whether the JSON decoder should preserve the
 distinction between positive zero and negative zero when the decoder
 decodes JSON to a floating-point number format that makes this
 distinction.
* `boolean getReplaceSurrogates() char`<br>
 Gets a value indicating whether surrogate code points not part of a
 surrogate pair (which consists of two consecutive char s
 forming one Unicode code point) are each replaced with a replacement
 character (U+FFFD).
* `boolean getWriteBasic()`<br>
 Gets a value indicating whether JSON is written using only code points from
 the Basic Latin block (U+0000 to U+007F), also known as ASCII.
* `java.lang.String toString()`<br>
 Gets the values of this options object's properties in text form.

## Field Details

### <a id='Default'>Default</a>

The default options for converting CBOR objects to JSON.
## Method Details

### <a id='toString()'>toString</a>

Gets the values of this options object's properties in text form.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A text string containing the values of this options object's
 properties. The format of the string is the same as the one
 described in the string constructor for this class.

### <a id='getBase64Padding()'>getBase64Padding</a>

Gets a value indicating whether the Base64Padding property is true. This
 property has no effect; in previous versions, this property meant
 that padding was written out when writing base64url or traditional
 base64 to JSON.

**Returns:**

* A value indicating whether the Base64Padding property is true.

### <a id='getPreserveNegativeZero()'>getPreserveNegativeZero</a>

Gets a value indicating whether the JSON decoder should preserve the
 distinction between positive zero and negative zero when the decoder
 decodes JSON to a floating-point number format that makes this
 distinction. For a value of <code>false</code>, if the result of parsing a
 JSON string would be a floating-point negative zero, that result is
 a positive zero instead. (Note that this property has no effect for
 conversion kind <code>IntOrFloatFromDouble</code>, where floating-point
 zeros are not possible.).

**Returns:**

* A value indicating whether to preserve the distinction between
 positive zero and negative zero when decoding JSON. The default is
 true.

### <a id='getNumberConversion()'>getNumberConversion</a>

Gets a value indicating how JSON numbers are decoded to CBOR objects. None
 of the conversion modes affects how CBOR objects are later encoded
 (such as via <code>EncodeToBytes</code>).

**Returns:**

* A value indicating how JSON numbers are decoded to CBOR. The default
 is <code>ConversionMode.Full</code>.

### <a id='getWriteBasic()'>getWriteBasic</a>

Gets a value indicating whether JSON is written using only code points from
 the Basic Latin block (U+0000 to U+007F), also known as ASCII.

**Returns:**

* A value indicating whether JSON is written using only code points
 from the Basic Latin block (U+0000 to U+007F), also known as ASCII.
 Default is false.

### <a id='getAllowDuplicateKeys()'>getAllowDuplicateKeys</a>

Gets a value indicating whether to allow duplicate keys when reading JSON.
 Used only when decoding JSON. If this property is <code>true</code> and a
 JSON object has two or more values with the same key, the last value
 of that key set forth in the JSON object is taken.

**Returns:**

* A value indicating whether to allow duplicate keys when reading
 JSON. The default is false.

### <a id='getReplaceSurrogates()'>getReplaceSurrogates</a>

Gets a value indicating whether surrogate code points not part of a
 surrogate pair (which consists of two consecutive <code>char</code> s
 forming one Unicode code point) are each replaced with a replacement
 character (U+FFFD). If false, an exception is thrown when such code
 points are encountered.

**Returns:**

* True, if surrogate code points not part of a surrogate pair are each
 replaced with a replacement character, or false if an exception is
 thrown when such code points are encountered. The default is false.
