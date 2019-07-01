# com.upokecenter.cbor.CBOREncodeOptions

    public final class CBOREncodeOptions extends java.lang.Object

Specifies options for encoding and decoding CBOR objects.

## Fields

* `static CBOREncodeOptions Default`<br>
 Default options for CBOR objects.
* `static CBOREncodeOptions NoDuplicateKeys`<br>
 Deprecated.
Use 'new CBOREncodeOptions(true,false)' instead.
 Use 'new CBOREncodeOptions(true,false)' instead.
* `static CBOREncodeOptions NoIndefLengthStrings`<br>
 Deprecated.
Use 'new CBOREncodeOptions(false,true)' instead.
 Use 'new CBOREncodeOptions(false,true)' instead.
* `static CBOREncodeOptions None`<br>
 Deprecated.
Use 'new CBOREncodeOptions(true,true)' instead.
 Use 'new CBOREncodeOptions(true,true)' instead.

## Constructors

* `CBOREncodeOptions() CBOREncodeOptions`<br>
 Initializes a new instance of the CBOREncodeOptions class.
* `CBOREncodeOptions​(boolean useIndefLengthStrings,
                 boolean allowDuplicateKeys) CBOREncodeOptions`<br>
 Initializes a new instance of the CBOREncodeOptions class.
* `CBOREncodeOptions​(boolean useIndefLengthStrings,
                 boolean allowDuplicateKeys,
                 boolean ctap2Canonical) CBOREncodeOptions`<br>
 Initializes a new instance of the CBOREncodeOptions class.

## Methods

* `CBOREncodeOptions And​(CBOREncodeOptions o)`<br>
 Deprecated.
May be removed in a later version.
 May be removed in a later version.
* `boolean getAllowDuplicateKeys()`<br>
 Gets a value indicating whether to disallow duplicate keys when reading CBOR
 objects from a data stream.
* `boolean getCtap2Canonical()`<br>
 Gets a value indicating whether CBOR objects are written out using the CTAP2
 canonical CBOR encoding form.
* `boolean getUseIndefLengthStrings()`<br>
 Gets a value indicating whether to always encode strings with a
 definite-length encoding.
* `int getValue()`<br>
 Deprecated.
Option classes in this library will follow the form seen in JSONOptions in a
 later version; the approach used in this class is too complicated.
 Option classes in this library will follow the form seen in JSONOptions in a
 later version; the approach used in this class is too complicated.
* `CBOREncodeOptions Or​(CBOREncodeOptions o)`<br>
 Deprecated.
May be removed in a later version.
 May be removed in a later version.

## Field Details

### None
    @Deprecated public static final CBOREncodeOptions None
Deprecated.
Use 'new CBOREncodeOptions(true,true)' instead. Option classes in this
 library will follow the form seen in JSONOptions in a later version; the
 approach used in this class is too complicated.
'CBOREncodeOptions.Default' contains recommended default options that may
 be adopted by certain CBORObject methods in the next major version.

### Default
    public static final CBOREncodeOptions Default
Default options for CBOR objects. Disallow duplicate keys, and always encode
 strings using definite-length encoding. These are recommended
 settings for the options that may be adopted by certain CBORObject
 methods in the next major version.
### NoIndefLengthStrings
    @Deprecated public static final CBOREncodeOptions NoIndefLengthStrings
Deprecated.
Use 'new CBOREncodeOptions(false,true)' instead. Option classes in this
 library will follow the form seen in JSONOptions in a later version; the
 approach used in this class is too complicated.

### NoDuplicateKeys
    @Deprecated public static final CBOREncodeOptions NoDuplicateKeys
Deprecated.
Use 'new CBOREncodeOptions(true,false)' instead. Option classes in this
 library will follow the form seen in JSONOptions in a later version; the
 approach used in this class is too complicated.

## Method Details

### getUseIndefLengthStrings
    public final boolean getUseIndefLengthStrings()
Gets a value indicating whether to always encode strings with a
 definite-length encoding.

**Returns:**

* A value indicating whether to always encode strings with a
 definite-length encoding.

### getAllowDuplicateKeys
    public final boolean getAllowDuplicateKeys()
Gets a value indicating whether to disallow duplicate keys when reading CBOR
 objects from a data stream. Used only when decoding CBOR objects.

**Returns:**

* A value indicating whether to disallow duplicate keys when reading
 CBOR objects from a data stream.

### getCtap2Canonical
    public final boolean getCtap2Canonical()
Gets a value indicating whether CBOR objects are written out using the CTAP2
 canonical CBOR encoding form. In this form, CBOR tags are not used,
 map keys are written out in a canonical order, and non-integer
 numbers and integers 2^63 or greater are written as 64-bit binary
 floating-point numbers.

**Returns:**

* <code>true</code> if CBOR objects are written out using the CTAP2
 canonical CBOR encoding form; otherwise, <code>false</code> .. In this
 form, CBOR tags are not used, map keys are written out in a canonical
 order, and non-integer numbers and integers 2^63 or greater are
 written as 64-bit binary floating-point numbers.

### getValue
    @Deprecated public final int getValue()
Deprecated.
Option classes in this library will follow the form seen in JSONOptions in a
 later version; the approach used in this class is too complicated.

**Returns:**

* This options object's value.

### Or
    @Deprecated public CBOREncodeOptions Or​(CBOREncodeOptions o)
Deprecated.
May be removed in a later version. Option classes in this library will
 follow the form seen in JSONOptions in a later version; the approach used
 in this class is too complicated.

**Parameters:**

* <code>o</code> - The parameter <code>o</code> is a CBOREncodeOptions object.

**Returns:**

* A new CBOREncodeOptions object.

### And
    @Deprecated public CBOREncodeOptions And​(CBOREncodeOptions o)
Deprecated.
May be removed in a later version. Option classes in this library will
 follow the form seen in JSONOptions in a later version; the approach used
 in this class is too complicated.

**Parameters:**

* <code>o</code> - The parameter <code>o</code> is a CBOREncodeOptions object.

**Returns:**

* A CBOREncodeOptions object.
