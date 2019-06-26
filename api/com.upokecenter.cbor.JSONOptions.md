# com.upokecenter.cbor.JSONOptions

    public final class JSONOptions extends java.lang.Object

Includes options to control how CBOR objects are converted to JSON.

## Fields

* `static JSONOptions Default`<br>
 The default options for converting CBOR objects to JSON.

## Constructors

* `JSONOptions() JSONOptions`<br>
 Initializes a new instance of the JSONOptions class with default
 options.
* `JSONOptions​(boolean base64Padding) JSONOptions`<br>
 Initializes a new instance of the JSONOptions class with the given
 value for the Base64Padding option.
* `JSONOptions​(boolean base64Padding,
           boolean replaceSurrogates) JSONOptions`<br>
 Initializes a new instance of the JSONOptions class with the given
 values for the options.

## Methods

* `boolean getBase64Padding()`<br>
 Deprecated.
This option now has no effect.
 This option now has no effect.
* `boolean getReplaceSurrogates() char`<br>
 Gets a value indicating whether surrogate code points not part of a
 surrogate pair (which consists of two consecutive char s
 forming one Unicode code point) are each replaced with a replacement
 character (U + FFFD).

## Field Details

### Default
    public static final JSONOptions Default
The default options for converting CBOR objects to JSON.
## Method Details

### getBase64Padding
    @Deprecated public final boolean getBase64Padding()
Deprecated.
This option now has no effect. This library now includes necessary padding
 when writing traditional base64 to JSON and includes no padding when
 writing base64url to JSON, in accordance with the revision of the CBOR
 specification.

**Returns:**

* The default is false, no padding.

### getReplaceSurrogates
    public final boolean getReplaceSurrogates()
Gets a value indicating whether surrogate code points not part of a
 surrogate pair (which consists of two consecutive <code>char</code> s
 forming one Unicode code point) are each replaced with a replacement
 character (U + FFFD). The default is false; an exception is thrown when
 such code points are encountered.

**Returns:**

* True, if surrogate code points not part of a surrogate pair are each
 replaced with a replacement character, or false if an exception is
 thrown when such code points are encountered.
