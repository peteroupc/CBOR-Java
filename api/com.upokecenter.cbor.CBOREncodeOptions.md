# com.upokecenter.cbor.CBOREncodeOptions

## Fields

* `static CBOREncodeOptions Default`<br>
 Default options for CBOR objects.
* `static CBOREncodeOptions DefaultCtap2Canonical`<br>
 Default options for CBOR objects serialized using the CTAP2 canonicalization
 (used in Web Authentication, among other specifications).

## Constructors

* `CBOREncodeOptions() CBOREncodeOptions`<br>
 Initializes a new instance of the CBOREncodeOptions class with all the default
 options.
* `CBOREncodeOptions​(boolean useIndefLengthStrings,
boolean allowDuplicateKeys)`<br>
 Deprecated.
Use the more readable String constructor instead.
 Use the more readable String constructor instead.
* `CBOREncodeOptions​(boolean useIndefLengthStrings,
boolean allowDuplicateKeys,
boolean ctap2Canonical)`<br>
 Deprecated.
Use the more readable String constructor instead.
 Use the more readable String constructor instead.
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
 useful for implementing Web Authentication (WebAuthn). When
 decoding, are checked for compliance with the CTAP2 canonical
 encoding form. In this form, CBOR tags are not used, map
 keys are written out in a canonical order, a maximum depth of four
 levels of arrays and/or maps is allowed, duplicate map keys are not
 allowed when decoding, and floating-point numbers are written out in
 their 64-bit encoding form regardless of whether their value can be
 encoded without loss in a smaller form.
* `boolean getFloat64()`<br>
 Gets a value indicating whether to encode floating-point numbers in a CBOR
 object in their 64-bit encoding form regardless of whether their
 value can be encoded without loss in a smaller form.
* `boolean getKeepKeyOrder()`<br>
 Gets a value indicating whether to preserve the order in which a CBOR map's
 keys appear when decoding a CBOR object, by using maps created as
 though by CBORObject.NewOrderedMap.
* `boolean getResolveReferences()`<br>
 Gets a value indicating whether to resolve references to sharable objects
 and sharable strings in the process of decoding a CBOR object.
* `boolean getUseIndefLengthStrings()`<br>
 Gets a value indicating whether to encode strings with an indefinite-length
 encoding under certain circumstances.
* `java.lang.String toString()`<br>
 Gets the values of this options object's properties in text form.

## Field Details

### <a id='Default'>Default</a>

Default options for CBOR objects. Disallow duplicate keys, and always encode
 strings using definite-length encoding.
### <a id='DefaultCtap2Canonical'>DefaultCtap2Canonical</a>

Default options for CBOR objects serialized using the CTAP2 canonicalization
 (used in Web Authentication, among other specifications). Disallow
 duplicate keys, and always encode strings using definite-length
 encoding.
## Method Details

### <a id='toString()'>toString</a>

Gets the values of this options object's properties in text form.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A text string containing the values of this options object's
 properties. The format of the string is the same as the one
 described in the string constructor for this class.

### <a id='getResolveReferences()'>getResolveReferences</a>

Gets a value indicating whether to resolve references to sharable objects
 and sharable strings in the process of decoding a CBOR object.
 Enabling this property, however, can cause a security risk if a
 decoded CBOR object is then re-encoded.<p> </p><p><b>About sharable
 objects and references</b></p> <p>Sharable objects are marked with
 tag 28, and references to those objects are marked with tag 29
 (where a reference of 0 means the first sharable object in the CBOR
 stream, a reference of 1 means the second, and so on). Sharable
 strings (byte strings and text strings) appear within an enclosing
 object marked with tag 256, and references to them are marked with
 tag 25; in general, a string is sharable only if storing its
 reference rather than the string would save space.</p> <p>Note that
 unlike most other tags, these tags generally care about the relative
 order in which objects appear in a CBOR stream; thus they are not
 interoperable with CBOR implementations that follow the generic CBOR
 data model (since they may list map keys in an unspecified order).
 Interoperability problems with these tags can be reduced by not
 using them to mark keys or values of a map or to mark objects within
 those keys or values.</p> <p><b>Security Note</b></p> <p>When this
 property is enabled and a decoded CBOR object contains references to
 sharable CBOR objects within it, those references will be replaced
 with the sharable objects they refer to (but without making a copy
 of those objects). However, if shared references are deeply nested
 and used multiple times, these references can result in a CBOR
 object that is orders of magnitude bigger than if shared references
 weren't resolved, and this can cause a denial of service when the
 decoded CBOR object is then serialized (e.g., with
 <code>EncodeToBytes()</code>, <code>toString()</code>, <code>ToJSONString()</code>, or
 <code>WriteTo</code>), because object references are expanded in the
 process.</p> <p>For example, the following object in CBOR diagnostic
  notation, <code>[28(["xxx", "yyy"]), 28([29(0), 29(0), 29(0)]),
 28([29(1), 29(1)]), 28([29(2), 29(2)]), 28([29(3), 29(3)]),
 28([29(4), 29(4)]), 28([29(5), 29(5)])]</code>, expands to a CBOR
 object with a serialized size of about 1831 bytes when this property
 is enabled, as opposed to about 69 bytes when this property is
 disabled.</p> <p>One way to mitigate security issues with this
 property is to limit the maximum supported size a CBORObject can
 have once serialized to CBOR or JSON. This can be done by passing a
  so-called "limited memory stream" to the <code>WriteTo</code> or
 <code>WriteJSONTo</code> methods when serializing the object to JSON or
  CBOR. A "limited memory stream" is a <code>InputStream</code> (or
 <code>OutputStream</code> in Java) that throws an exception if it would
 write more bytes than a given maximum size or would seek past that
 size. (See the documentation for <code>CBORObject.WriteTo</code> or
 <code>CBORObject.WriteJSONTo</code> for example code.) Another mitigation
 is to check the CBOR object's type before serializing it, since only
 arrays and maps can have the security problem described here, or to
 check the maximum nesting depth of a CBOR array or map before
 serializing it.</p>

**Returns:**

* A value indicating whether to resolve references to sharable objects
 and sharable strings. The default is false.

### <a id='getUseIndefLengthStrings()'>getUseIndefLengthStrings</a>

Gets a value indicating whether to encode strings with an indefinite-length
 encoding under certain circumstances.

**Returns:**

* A value indicating whether to encode strings with an
 indefinite-length encoding under certain circumstances. The default
 is false.

### <a id='getKeepKeyOrder()'>getKeepKeyOrder</a>

Gets a value indicating whether to preserve the order in which a CBOR map's
 keys appear when decoding a CBOR object, by using maps created as
 though by CBORObject.NewOrderedMap. If false, key order is not
 guaranteed to be preserved when decoding CBOR.

**Returns:**

* A value indicating whether to preserve the order in which a CBOR
 map's keys appear when decoding a CBOR object. The default is false.

### <a id='getAllowEmpty()'>getAllowEmpty</a>

Gets a value indicating whether decoding a CBOR object will return
 <code>null</code> instead of a CBOR object if the stream has no content or
 the end of the stream is reached before decoding begins. Used only
 when decoding CBOR objects.

**Returns:**

* A value indicating whether decoding a CBOR object will return <code>
 null</code> instead of a CBOR object if the stream has no content or the
 end of the stream is reached before decoding begins. The default is
 false.

### <a id='getAllowDuplicateKeys()'>getAllowDuplicateKeys</a>

Gets a value indicating whether to allow duplicate keys when reading CBOR
 objects from a data stream. Used only when decoding CBOR objects. If
 this property is <code>true</code> and a CBOR map has two or more values
 with the same key, the last value of that key set forth in the CBOR
 map is taken.

**Returns:**

* A value indicating whether to allow duplicate keys when reading CBOR
 objects from a data stream. The default is false.

### <a id='getFloat64()'>getFloat64</a>

Gets a value indicating whether to encode floating-point numbers in a CBOR
 object in their 64-bit encoding form regardless of whether their
 value can be encoded without loss in a smaller form. Used only when
 encoding CBOR objects.

**Returns:**

* Gets a value indicating whether to encode floating-point numbers in
 a CBOR object in their 64-bit encoding form regardless of whether
 their value can be encoded without loss in a smaller form. Used only
 when encoding CBOR objects. The default is false.

### <a id='getCtap2Canonical()'>getCtap2Canonical</a>

Gets a value indicating whether CBOR objects: <ul> <li>When encoding, are
 written out using the CTAP2 canonical CBOR encoding form, which is
 useful for implementing Web Authentication (WebAuthn).</li> <li>When
 decoding, are checked for compliance with the CTAP2 canonical
 encoding form.</li></ul> In this form, CBOR tags are not used, map
 keys are written out in a canonical order, a maximum depth of four
 levels of arrays and/or maps is allowed, duplicate map keys are not
 allowed when decoding, and floating-point numbers are written out in
 their 64-bit encoding form regardless of whether their value can be
 encoded without loss in a smaller form. This implementation allows
 CBOR objects whose canonical form exceeds 1024 bytes, the default
 maximum size for CBOR objects in that form according to the FIDO
 Client-to-Authenticator Protocol 2 specification.

**Returns:**

* <code>true</code> if CBOR objects are written out using the CTAP2
 canonical CBOR encoding form; otherwise, <code>false</code>. The default
 is <code>false</code>.
