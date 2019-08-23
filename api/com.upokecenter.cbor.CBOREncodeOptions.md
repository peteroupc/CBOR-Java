# com.upokecenter.cbor.CBOREncodeOptions

    public final class CBOREncodeOptions extends java.lang.Object

Specifies options for encoding and decoding CBOR objects.

## Fields

* `static CBOREncodeOptions Default`<br>
 Default options for CBOR objects.
* `static CBOREncodeOptions DefaultCtap2Canonical`<br>
 Default options for CBOR objects serialized using the CTAP2 canonicalization
 (used in Web Authentication, among other specifications).

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

* `boolean getAllowDuplicateKeys()`<br>
 Gets a value indicating whether to allow duplicate keys when reading CBOR
 objects from a data stream.
* `boolean getAllowEmpty() null`<br>
 Gets a value indicating whether decoding a CBOR object will return
 null instead of a CBOR object if the stream has no content or
 the end of the stream is reached before decoding begins.
* `boolean getCtap2Canonical()`<br>
 Gets a value indicating whether CBOR objects:  When encoding, are
 written out using the CTAP2 canonical CBOR encoding form, which is
 useful for implementing Web Authentication. When decoding,
 are checked for compliance with the CTAP2 canonical encoding
 form. In this form, CBOR tags are not used, map keys are
 written out in a canonical order, a maximum depth of four levels of
 arrays and/or maps is allowed, duplicate map keys are not allowed
 when decoding, and floating-point numbers are written out in their
 64-bit encoding form regardless of whether their value can be
 encoded without loss in a smaller form.
* `boolean getResolveReferences()`<br>
 Gets a value indicating whether to resolve references to sharable objects
 and sharable strings in the process of decoding a CBOR object.
* `boolean getUseIndefLengthStrings()`<br>
 Gets a value indicating whether to encode strings with an indefinite-length
 encoding under certain circumstances.
* `java.lang.String toString()`<br>
 Gets the values of this options object's properties in text form.

## Field Details

### Default
    public static final CBOREncodeOptions Default
Default options for CBOR objects. Disallow duplicate keys, and always encode
 strings using definite-length encoding.
### DefaultCtap2Canonical
    public static final CBOREncodeOptions DefaultCtap2Canonical
Default options for CBOR objects serialized using the CTAP2 canonicalization
 (used in Web Authentication, among other specifications). Disallow
 duplicate keys, and always encode strings using definite-length
 encoding.
## Method Details

### toString
    public java.lang.String toString()
Gets the values of this options object's properties in text form.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A text string containing the values of this options object's
 properties. The format of the string is the same as the one
 described in the string constructor for this class.

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
 save space.</p> <p>Note that unlike most other tags, these tags
 generally care about the relative order in which objects appear in a
 CBOR stream; thus they are not interoperable with CBOR
 implementations that follow the generic CBOR data model (since they
 may list map keys in an unspecified order). Interoperability
 problems with these tags can be reduced by not using them to mark
 keys or values of a map or to mark objects within those keys or
 values.</p>

**Returns:**

* A value indicating whether to resolve references to sharable objects
 and sharable strings. The default is false.

### getUseIndefLengthStrings
    public final boolean getUseIndefLengthStrings()
Gets a value indicating whether to encode strings with an indefinite-length
 encoding under certain circumstances.

**Returns:**

* A value indicating whether to encode strings with an
 indefinite-length encoding under certain circumstances. The default
 is false.

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

### getAllowDuplicateKeys
    public final boolean getAllowDuplicateKeys()
Gets a value indicating whether to allow duplicate keys when reading CBOR
 objects from a data stream. Used only when decoding CBOR objects.

**Returns:**

* A value indicating whether to allow duplicate keys when reading CBOR
 objects from a data stream. The default is false.

### getCtap2Canonical
    public final boolean getCtap2Canonical()
Gets a value indicating whether CBOR objects: <ul> <li>When encoding, are
 written out using the CTAP2 canonical CBOR encoding form, which is
 useful for implementing Web Authentication.</li> <li>When decoding,
 are checked for compliance with the CTAP2 canonical encoding
 form.</li></ul> In this form, CBOR tags are not used, map keys are
 written out in a canonical order, a maximum depth of four levels of
 arrays and/or maps is allowed, duplicate map keys are not allowed
 when decoding, and floating-point numbers are written out in their
 64-bit encoding form regardless of whether their value can be
 encoded without loss in a smaller form. This implementation allows
 CBOR objects whose canonical form exceeds 1024 bytes, the default
 maximum size for CBOR objects in that form according to the FIDO
 Client-to-Authenticator Protocol 2 specification.

**Returns:**

* <code>true</code> if CBOR objects are written out using the CTAP2
 canonical CBOR encoding form; otherwise, <code>false</code>.
