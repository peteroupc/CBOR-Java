# com.upokecenter.cbor.CBOREncodeOptions

    public final class CBOREncodeOptions extends Object

Specifies options for encoding and decoding CBOR objects.

## Fields

* `static CBOREncodeOptions NoDuplicateKeys`<br>
 Disallow duplicate keys when reading CBOR objects from a data stream.
* `static CBOREncodeOptions NoIndefLengthStrings`<br>
 Always encode strings with a definite-length encoding.
* `static CBOREncodeOptions None`<br>
 No special options for encoding/decoding.

## Methods

* `CBOREncodeOptions And​(CBOREncodeOptions o)`<br>
 Returns an options object containing the flags shared by this and another
 options object.
* `int getValue()`<br>
 Gets this options object's value.
* `CBOREncodeOptions Or​(CBOREncodeOptions o)`<br>
 Returns an options object containing the combined flags of this and another
 options object.

## Field Details

### None
    public static final CBOREncodeOptions None
No special options for encoding/decoding. Value: 0.
### NoIndefLengthStrings
    public static final CBOREncodeOptions NoIndefLengthStrings
Always encode strings with a definite-length encoding. Used only when
 encoding CBOR objects. Value: 1.
### NoDuplicateKeys
    public static final CBOREncodeOptions NoDuplicateKeys
Disallow duplicate keys when reading CBOR objects from a data stream. Used
 only when decoding CBOR objects. Value: 2.
## Method Details

### getValue
    public final int getValue()
Gets this options object's value.

**Returns:**

* This options object's value.

### Or
    public CBOREncodeOptions Or​(CBOREncodeOptions o)
Returns an options object containing the combined flags of this and another
 options object.

**Parameters:**

* <code>o</code> - The parameter <code>o</code> is a CBOREncodeOptions object.

**Returns:**

* A new CBOREncodeOptions object.

### And
    public CBOREncodeOptions And​(CBOREncodeOptions o)
Returns an options object containing the flags shared by this and another
 options object.

**Parameters:**

* <code>o</code> - The parameter <code>o</code> is a CBOREncodeOptions object.

**Returns:**

* A CBOREncodeOptions object.
