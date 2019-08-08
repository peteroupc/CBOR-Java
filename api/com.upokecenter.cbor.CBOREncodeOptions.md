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
Use 'new CBOREncodeOptions(false,false)' instead.
 Use 'new CBOREncodeOptions(false,false)' instead.
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
* `CBOREncodeOptions​(java.lang.String paramString) CBOREncodeOptions`<br>
 Initializes a new instance of the CBOREncodeOptions class.

## Methods

* `CBOREncodeOptions And​(CBOREncodeOptions o)`<br>
 Deprecated.
May be removed in a later version.
 May be removed in a later version.
* `boolean getAllowDuplicateKeys()`<br>
 Gets a value indicating whether to disallow duplicate keys when reading CBOR
 objects from a data stream.
* `boolean getAllowEmpty() null`<br>
 Gets a value indicating whether decoding a CBOR object will return
 null instead of a CBOR object if the stream has no content or
 the end of the stream is reached before decoding begins.
* `boolean getCtap2Canonical()`<br>
 Gets a value indicating whether CBOR objects are written out using the CTAP2
 canonical CBOR encoding form.
* `boolean getResolveReferences()`<br>
 Gets a value indicating whether to resolve references to sharable objects
 and sharable strings in the process of decoding a CBOR object.
* `boolean getUseIndefLengthStrings()`<br>
 Gets a value indicating whether to always encode strings with a
 definite-length encoding.
* `int getValue()`<br>
 Deprecated.
Use the toString() method to get the values of all this object's properties.
 Use the toString() method to get the values of all this object's properties.
* `CBOREncodeOptions Or​(CBOREncodeOptions o)`<br>
 Deprecated.
May be removed in a later version.
 May be removed in a later version.
* `java.lang.String toString()`<br>
 Gets the values of this options object's properties in text form.

## Field Details

### None
    @Deprecated public static final CBOREncodeOptions None
Deprecated.
Use 'new CBOREncodeOptions(true,true)' instead. Option classes in this
 library will follow a different form in a later version -- the approach
 used in this class is too complicated. 'CBOREncodeOptions.Default'
 contains recommended default options that may be adopted by certain
 CBORObject methods in the next major version.

### Default
    public static final CBOREncodeOptions Default
Default options for CBOR objects. Disallow duplicate keys, and always encode
 strings using definite-length encoding. These are recommended
 settings for the options that may be adopted by certain CBORObject
 methods in the next major version.
### NoIndefLengthStrings
    @Deprecated public static final CBOREncodeOptions NoIndefLengthStrings
Deprecated.
Use 'new CBOREncodeOptions(false,false)' instead. Option classes in
 this library will follow a different form in a later version -- the
 approach used in this class is too complicated.

### NoDuplicateKeys
    @Deprecated public static final CBOREncodeOptions NoDuplicateKeys
Deprecated.
Use 'new CBOREncodeOptions(true,false)' instead. Option classes in this
 library will follow a different form in a later version -- the
 approach used in this class is too complicated.

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
 canonical CBOR encoding form; otherwise, <code>false</code>.. In this
 form, CBOR tags are not used, map keys are written out in a
 canonical order, and non-integer numbers and integers 2^63 or
 greater are written as 64-bit binary floating-point numbers.

### getValue
    @Deprecated public final int getValue()
Deprecated.
Use the toString() method to get the values of all this object's properties.

**Returns:**

* This options object's value.

### getResolveReferences
    public final boolean getResolveReferences()
Gets a value indicating whether to resolve references to sharable objects
 and sharable strings in the process of decoding a CBOR object.<p>
 </p><p>Sharable objects are marked with tag 28, and references to those
 objects are marked with tag 29 (where a reference of 0 means the
 first sharable object in the CBOR stream, a reference of 1 means the
 second, and so on). Sharable strings (byte strings and text strings)
 appear within an enclosing object marked with tag 256, and
 references to them are marked with tag 25; in general, a string is
 sharable only if storing its reference rather than the string would
 save space. </p> <p>Note that unlike most other tags, these tags
 generally care about the relative order in which objects appear in a
 CBOR stream; thus they are not interoperable with CBOR
 implementations that follow the generic CBOR data model (since they
 may list map keys in an unspecified order). Interoperability
 problems with these tags can be reduced by not using them to mark
 keys or values of a map or to mark objects within those keys or
 values. </p>

**Returns:**

* A value indicating whether to resolve references to sharable objects
 and sharable strings. The default is false.

### getAllowEmpty
    public final boolean getAllowEmpty()
Gets a value indicating whether decoding a CBOR object will return
 <code>null</code> instead of a CBOR object if the stream has no content or
 the end of the stream is reached before decoding begins. Used only
 when decoding CBOR objects.

**Returns:**

* A value indicating whether decoding a CBOR object will return <code>
 null</code> instead of a CBOR object if the stream has no content or the
 end of the stream is reached before decoding begins. The default is
 false.

### Or
    @Deprecated public CBOREncodeOptions Or​(CBOREncodeOptions o)
Deprecated.
May be removed in a later version. Option classes in this library will
 follow a different form in a later version -- the approach used in
 this class is too complicated.

**Parameters:**

* <code>o</code> - The parameter
      <code>o</code>
       is a CBOREncodeOptions object.

**Returns:**

* A new CBOREncodeOptions object.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>o</code> is null.

### And
    @Deprecated public CBOREncodeOptions And​(CBOREncodeOptions o)
Deprecated.
May be removed in a later version. Option classes in this library will
 follow a different form in a later version -- the approach used in
 this class is too complicated.

**Parameters:**

* <code>o</code> - The parameter
      <code>o</code>
       is a CBOREncodeOptions object.

**Returns:**

* A CBOREncodeOptions object.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>o</code> is null.
