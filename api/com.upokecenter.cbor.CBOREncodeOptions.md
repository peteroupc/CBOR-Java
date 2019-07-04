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

## Methods

* `boolean getAllowDuplicateKeys()`<br>
 Gets a value indicating whether to allow duplicate keys when reading CBOR
 objects from a data stream.
* `boolean getCtap2Canonical()`<br>
 Gets a value indicating whether CBOR objects are written out using the CTAP2
 canonical CBOR encoding form, which is useful for implementing Web
 Authentication.
* `boolean getUseIndefLengthStrings()`<br>
 Gets a value indicating whether to encode strings with an indefinite-length
 encoding under certain circumstances.

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

### getUseIndefLengthStrings
    public final boolean getUseIndefLengthStrings()
Gets a value indicating whether to encode strings with an indefinite-length
 encoding under certain circumstances.

**Returns:**

* A value indicating whether to encode strings with an
 indefinite-length encoding under certain circumstances. The default
 is false.

### getAllowDuplicateKeys
    public final boolean getAllowDuplicateKeys()
Gets a value indicating whether to allow duplicate keys when reading CBOR
 objects from a data stream. Used only when decoding CBOR objects.

**Returns:**

* A value indicating whether to allow duplicate keys when reading CBOR
 objects from a data stream. The default is false.

### getCtap2Canonical
    public final boolean getCtap2Canonical()
Gets a value indicating whether CBOR objects are written out using the CTAP2
 canonical CBOR encoding form, which is useful for implementing Web
 Authentication. In this form, CBOR tags are not used, map keys are
 written out in a canonical order, and non-integer numbers and
 integers 2^63 or greater are written as 64-bit binary floating-point
 numbers.

**Returns:**

* <code>true</code> if CBOR objects are written out using the CTAP2
 canonical CBOR encoding form; otherwise, <code>false</code> .
