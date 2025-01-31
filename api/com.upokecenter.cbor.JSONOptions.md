# com.upokecenter.cbor.JSONOptions

    public final class JSONOptions extends Object

Includes options to control how CBOR objects are converted to and from
 JavaScript object Notation (JSON).

## Nested Classes

* `static enum  JSONOptions.ConversionMode`<br>
 Specifies how JSON numbers are converted to CBOR objects when decoding JSON
 (such as via FromJSONString or ReadJSON).

## Fields

* `static final JSONOptions Default`<br>
 The default options for converting CBOR objects to JSON.

## Constructors

## Methods

* `final boolean getAllowDuplicateKeys()`<br>
 Gets a value indicating whether to allow duplicate keys when reading JSON.

* `final boolean getKeepKeyOrder()`<br>
 Gets a value indicating whether to preserve the order in which a map's keys
 appear when decoding JSON, by using maps created as though by
 CBORObject.NewOrderedMap.

* `final JSONOptions.ConversionMode getNumberConversion()`<br>
 Gets a value indicating how JSON numbers are decoded to CBOR objects.

* `final boolean getPreserveNegativeZero()`<br>
 Gets a value indicating whether the JSON decoder should preserve the
 distinction between positive zero and negative zero when the decoder decodes
 JSON to a floating-point number format that makes this distinction.

* `final boolean getReplaceSurrogates()`<br>
 Gets a value indicating whether surrogate code points not part of a
 surrogate pair (which consists of two consecutive char s forming one
 Unicode code point) are each replaced with a replacement character (U+FFFD).

* `final boolean getWriteBasic()`<br>
 Gets a value indicating whether JSON is written using only code points from
 the Basic Latin block (U+0000 to U+007F), also known as ASCII.

* `String toString()`<br>
 Gets the values of this options object's properties in text form.

## Field Details

### Default

    public static final JSONOptions Default

The default options for converting CBOR objects to JSON.

## Method Details

### toString

    public String toString()

Gets the values of this options object's properties in text form.

**Overrides:**

* <code>toString</code> in class <code>Object</code>

**Returns:**

* A text string containing the values of this options object's
 properties. The format of the string is the same as the one described in the
 string constructor for this class.

### getPreserveNegativeZero

    public final boolean getPreserveNegativeZero()

Gets a value indicating whether the JSON decoder should preserve the
 distinction between positive zero and negative zero when the decoder decodes
 JSON to a floating-point number format that makes this distinction. For a
 value of <code>false</code>, if the result of parsing a JSON string would be a
 floating-point negative zero, that result is a positive zero instead. (Note
 that this property has no effect for conversion kind <code>
 IntOrFloatFromDouble</code>, where floating-point zeros are not possible.).

**Returns:**

* A value indicating whether to preserve the distinction between
 positive zero and negative zero when decoding JSON. The default is true.

### getNumberConversion

    public final JSONOptions.ConversionMode getNumberConversion()

Gets a value indicating how JSON numbers are decoded to CBOR objects. None
 of the conversion modes affects how CBOR objects are later encoded (such as
 via <code>EncodeToBytes</code>).

**Returns:**

* A value indicating how JSON numbers are decoded to CBOR. The default
 is <code>ConversionMode.Full</code>.

### getWriteBasic

    public final boolean getWriteBasic()

Gets a value indicating whether JSON is written using only code points from
 the Basic Latin block (U+0000 to U+007F), also known as ASCII.

**Returns:**

* A value indicating whether JSON is written using only code points
 from the Basic Latin block (U+0000 to U+007F), also known as ASCII. Default
 is false.

### getKeepKeyOrder

    public final boolean getKeepKeyOrder()

Gets a value indicating whether to preserve the order in which a map's keys
 appear when decoding JSON, by using maps created as though by
 CBORObject.NewOrderedMap. If false, key order is not guaranteed to be
 preserved when decoding JSON.

**Returns:**

* A value indicating whether to preserve the order in which a CBOR
 map's keys appear when decoding JSON. The default is false.

### getAllowDuplicateKeys

    public final boolean getAllowDuplicateKeys()

Gets a value indicating whether to allow duplicate keys when reading JSON.
 Used only when decoding JSON. If this property is <code>true</code> and a JSON
 object has two or more values with the same key, the last value of that key
 set forth in the JSON object is taken.

**Returns:**

* A value indicating whether to allow duplicate keys when reading
 JSON. The default is false.

### getReplaceSurrogates

    public final boolean getReplaceSurrogates()

Gets a value indicating whether surrogate code points not part of a
 surrogate pair (which consists of two consecutive <code>char</code> s forming one
 Unicode code point) are each replaced with a replacement character (U+FFFD).
 If false, an exception is thrown when such code points are encountered.

**Returns:**

* True, if surrogate code points not part of a surrogate pair are each
 replaced with a replacement character, or false if an exception is thrown
 when such code points are encountered. The default is false.
