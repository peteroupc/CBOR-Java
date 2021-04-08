# com.upokecenter.cbor.CBORDateConverter

    public final class CBORDateConverter extends java.lang.Object implements ICBORToFromConverter<java.util.Date>

Not documented yet.

## Fields

* `static class  CBORDateConverter.ConversionType`<br>
 Not documented yet.
* `static CBORDateConverter TaggedNumber`<br>
 Not documented yet.
* `static CBORDateConverter TaggedString`<br>
 Not documented yet.
* `static CBORDateConverter UntaggedNumber`<br>
 Not documented yet.

## Nested Classes

* `static class  CBORDateConverter.ConversionType`<br>
 Not documented yet.

## Constructors

* `CBORDateConverter()`<br>
 Initializes a new instance of the CBORDateConverter class.
* `CBORDateConverter​(CBORDateConverter.ConversionType convType)`<br>
 Initializes a new instance of the CBORDateConverter class.

## Methods

* `java.util.Date FromCBORObject​(CBORObject obj)`<br>
 Not documented yet.
* `static java.util.Date StringToDateTime​(java.lang.String str)`<br>
 Not documented yet.
* `CBORObject ToCBORObject​(java.util.Date obj)`<br>
 Not documented yet.

## Field Details

### TaggedString
    public static final CBORDateConverter TaggedString
Not documented yet.
### TaggedNumber
    public static final CBORDateConverter TaggedNumber
Not documented yet.
### UntaggedNumber
    public static final CBORDateConverter UntaggedNumber
Not documented yet.
## Method Details

### FromCBORObject
    public java.util.Date FromCBORObject​(CBORObject obj)
Not documented yet.

**Specified by:**

* <code>FromCBORObject</code> in interface <code>ICBORToFromConverter&lt;java.util.Date&gt;</code>

**Parameters:**

* <code>obj</code> - Not documented yet.

**Returns:**

* The return value is not documented yet.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>obj</code> is null.

### StringToDateTime
    public static java.util.Date StringToDateTime​(java.lang.String str)
Not documented yet.

**Parameters:**

* <code>str</code> - Not documented yet.

**Returns:**

* The return value is not documented yet.

### ToCBORObject
    public CBORObject ToCBORObject​(java.util.Date obj)
Not documented yet.

**Specified by:**

* <code>ToCBORObject</code> in interface <code>ICBORConverter&lt;java.util.Date&gt;</code>

**Parameters:**

* <code>obj</code> - Not documented yet.

**Returns:**

* The return value is not documented yet.
