# com.upokecenter.cbor.JSONOptions

    public final class JSONOptions extends java.lang.Object

## Fields

* `static JSONOptions Default`<br>
 The default options for converting CBOR objects to JSON.

## Constructors

* `JSONOptions() JSONOptions`<br>
 Initializes a new instance of the JSONOptions
 class with default options.
* `JSONOptions​(boolean base64Padding) JSONOptions`<br>
 Initializes a new instance of the JSONOptions
 class with the given values for the options.
* `JSONOptions​(java.lang.String paramString) JSONOptions`<br>
 Initializes a new instance of the JSONOptions
 class.

## Methods

* `boolean getBase64Padding()`<br>
 Deprecated.
This option may have no effect in the future.
 This option may have no effect in the future.
* `java.lang.String toString()`<br>
 Gets the values of this options object's properties in text form.

## Field Details

### Default
    public static final JSONOptions Default
The default options for converting CBOR objects to JSON.
## Method Details

### toString
    public java.lang.String toString()
Gets the values of this options object's properties in text form.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A text string containing the values of this options object's
 properties. The format of the string is the same as the one
 described in the String constructor for this class.

### getBase64Padding
    @Deprecated public final boolean getBase64Padding()
Deprecated.
This option may have no effect in the future. A future version may, by
 default, include necessary padding when writing traditional base64 to
 JSON and include no padding when writing base64url to JSON, in accordance
 with the revision of the CBOR specification.

**Returns:**

* The default is false, no padding.
