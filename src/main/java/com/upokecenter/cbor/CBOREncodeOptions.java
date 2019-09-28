package com.upokecenter.cbor;

    /**
     * Specifies options for encoding and decoding CBOR objects.
     */
  public final class CBOREncodeOptions {
    /**
     * Default options for CBOR objects. Disallow duplicate keys, and always encode
     * strings using definite-length encoding.
     */
    public static final CBOREncodeOptions Default =
      new CBOREncodeOptions(false, false);

    /**
     * Default options for CBOR objects serialized using the CTAP2 canonicalization
     * (used in Web Authentication, among other specifications). Disallow
     * duplicate keys, and always encode strings using definite-length
     * encoding.
     */
    public static final CBOREncodeOptions DefaultCtap2Canonical =
      new CBOREncodeOptions(false, false, true);

    /**
     * Initializes a new instance of the {@link
     * com.upokecenter.cbor.CBOREncodeOptions} class.
     */
    public CBOREncodeOptions() {
 this(false, false);
}

    /**
     * Initializes a new instance of the {@link
     * com.upokecenter.cbor.CBOREncodeOptions} class.
     * @param useIndefLengthStrings A value indicating whether to always encode
     * strings with a definite-length encoding.
     * @param allowDuplicateKeys A value indicating whether to disallow duplicate
     * keys when reading CBOR objects from a data stream.
     */
    public CBOREncodeOptions(
      boolean useIndefLengthStrings,
      boolean allowDuplicateKeys) {
 this(useIndefLengthStrings, allowDuplicateKeys, false);
    }

    /**
     * Initializes a new instance of the {@link
     * com.upokecenter.cbor.CBOREncodeOptions} class.
     * @param useIndefLengthStrings A value indicating whether to encode strings
     * with a definite-length encoding in certain cases.
     * @param allowDuplicateKeys A value indicating whether to allow duplicate keys
     * when reading CBOR objects from a data stream.
     * @param ctap2Canonical A value indicating whether CBOR objects are written
     * out using the CTAP2 canonical CBOR encoding form, which is useful
     * for implementing Web Authentication.
     */
    public CBOREncodeOptions(
      boolean useIndefLengthStrings,
      boolean allowDuplicateKeys,
      boolean ctap2Canonical) {
      this.propVarresolvereferences = false;
      this.propVarallowempty = false;
      this.propVaruseindeflengthstrings = useIndefLengthStrings;
      this.propVarallowduplicatekeys = allowDuplicateKeys;
      this.propVarctap2canonical = ctap2Canonical;
    }

    /**
     * Initializes a new instance of the {@link
     * com.upokecenter.cbor.CBOREncodeOptions} class.
     * @param paramString A string setting forth the options to use. This is a
     * semicolon-separated list of options, each of which has a key and a
     *  value separated by an equal sign ("="). Whitespace and line
     * separators are not allowed to appear between the semicolons or
     * between the equal signs, nor may the string begin or end with
     * whitespace. The string can be empty, but cannot be null. The
     * following is an example of this parameter: {@code
     * allowduplicatekeys = true;ctap2Canonical = true}. The key can be any one
     * of the following in any combination of case: {@code
     * allowduplicatekeys}, {@code ctap2canonical}, {@code
     * resolvereferences}, {@code useindeflengthstrings}, {@code
     * allowempty}. Keys other than these are ignored. (Keys are compared
     * using a basic case-insensitive comparison, in which two strings are
     * equal if they match after converting the basic upper-case letters A
     * to Z (U+0041 to U+005A) in both strings to basic lower-case
     * letters.) If two or more key/value pairs have equal keys (in a basic
     * case-insensitive comparison), the value given for the last such key
     * is used. The four keys just given can have a value of {@code 1},
     * {@code true}, {@code yes}, or {@code on} (in any combination of
     * case), which means true, and any other value meaning false. For
     * example, {@code allowduplicatekeys = Yes} and {@code
     * allowduplicatekeys = 1} both set the {@code AllowDuplicateKeys}
     * property to true. In the future, this class may allow other keys to
     * store other kinds of values, not just true or false.
     * @throws NullPointerException The parameter {@code paramString} is null.
     */
    public CBOREncodeOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarresolvereferences = parser.GetBoolean("resolvereferences", false);
      this.propVaruseindeflengthstrings = parser.GetBoolean(
        "useindeflengthstrings",
        false);
      this.propVarallowduplicatekeys = parser.GetBoolean("allowduplicatekeys", false);
      this.propVarallowempty = parser.GetBoolean("allowempty", false);
      this.propVarctap2canonical = parser.GetBoolean("ctap2canonical", false);
    }

    /**
     * Gets the values of this options object's properties in text form.
     * @return A text string containing the values of this options object's
     * properties. The format of the string is the same as the one
     * described in the string constructor for this class.
     */
    @Override public String toString() {
      return new StringBuilder()
           .append("allowduplicatekeys=")
           .append(this.getAllowDuplicateKeys() ? "true" : "false")
           .append(";useindeflengthstrings=")
           .append(this.getUseIndefLengthStrings() ? "true" : "false")
           .append(";ctap2canonical=")
           .append(this.getCtap2Canonical() ? "true" : "false")
           .append(";resolvereferences=")
           .append(this.getResolveReferences() ? "true" : "false")
           .append(";allowempty=").append(this.getAllowEmpty() ? "true" : "false")
           .toString();
    }

    /**
     * Gets a value indicating whether to resolve references to sharable objects
     * and sharable strings in the process of decoding a CBOR object.
     * <p><b>Security Note:</b> When this property is enabled and a decoded
     * CBOR object contains references to sharable CBOR objects within it,
     * those references will be replaced with the sharable objects they
     * refer to (but without making a copy of those objects). However, a
     * security problem can happen if shared references refer to objects
     * that themselves contain shared references to other objects that in
     * turn contain shared references, and so on. With a sufficient level
     * of nesting, nested references can resolve to CBOR objects that are
     * orders of magnitude bigger than if shared references weren't
     * resolved, and this can cause a denial of service when the decoded
     * CBOR object is encoded to a byte array or converted to a string (as
     * with <code>EncodeToBytes()</code>, <code>toString()</code>, or
     * <code>ToJSONString()</code>), because object references are expanded in
     * the process. For example, the following object in CBOR diagnostic
     *  notation, <code>[28(["xxx", "yyy"]), 28([29(0), 29(0), 29(0)]),
     * 28([29(1), 29(1)]), 28([29(2), 29(2)]), 28([29(3), 29(3)]),
     * 28([29(4), 29(4)]), 28([29(5), 29(5)])]</code>, expands to a CBOR
     * object with a serialized size of about 1831 bytes when this property
     * is enabled, as opposed to about 69 bytes when this property is
     * disabled.</p><p> <p>Sharable objects are marked with tag 28, and
     * references to those objects are marked with tag 29 (where a
     * reference of 0 means the first sharable object in the CBOR stream, a
     * reference of 1 means the second, and so on). Sharable strings (byte
     * strings and text strings) appear within an enclosing object marked
     * with tag 256, and references to them are marked with tag 25; in
     * general, a string is sharable only if storing its reference rather
     * than the string would save space.</p> <p>Note that unlike most other
     * tags, these tags generally care about the relative order in which
     * objects appear in a CBOR stream; thus they are not interoperable
     * with CBOR implementations that follow the generic CBOR data model
     * (since they may list map keys in an unspecified order).
     * Interoperability problems with these tags can be reduced by not
     * using them to mark keys or values of a map or to mark objects within
     * those keys or values.</p></p>
     * @return A value indicating whether to resolve references to sharable objects
     * and sharable strings. The default is false.
     */
    public final boolean getResolveReferences() { return propVarresolvereferences; }
private final boolean propVarresolvereferences;

    /**
     * Gets a value indicating whether to encode strings with an indefinite-length
     * encoding under certain circumstances.
     * @return A value indicating whether to encode strings with an
     * indefinite-length encoding under certain circumstances. The default
     * is false.
     */
    public final boolean getUseIndefLengthStrings() { return propVaruseindeflengthstrings; }
private final boolean propVaruseindeflengthstrings;

    /**
     * Gets a value indicating whether decoding a CBOR object will return
     * <code>null</code> instead of a CBOR object if the stream has no content or
     * the end of the stream is reached before decoding begins. Used only
     * when decoding CBOR objects.
     * @return A value indicating whether decoding a CBOR object will return {@code
     * null} instead of a CBOR object if the stream has no content or the
     * end of the stream is reached before decoding begins. The default is
     * false.
     */
    public final boolean getAllowEmpty() { return propVarallowempty; }
private final boolean propVarallowempty;

    /**
     * Gets a value indicating whether to allow duplicate keys when reading CBOR
     * objects from a data stream. Used only when decoding CBOR objects.
     * @return A value indicating whether to allow duplicate keys when reading CBOR
     * objects from a data stream. The default is false.
     */
    public final boolean getAllowDuplicateKeys() { return propVarallowduplicatekeys; }
private final boolean propVarallowduplicatekeys;

    /**
     * Gets a value indicating whether CBOR objects: <ul> <li>When encoding, are
     * written out using the CTAP2 canonical CBOR encoding form, which is
     * useful for implementing Web Authentication.</li> <li>When decoding,
     * are checked for compliance with the CTAP2 canonical encoding
     * form.</li></ul> In this form, CBOR tags are not used, map keys are
     * written out in a canonical order, a maximum depth of four levels of
     * arrays and/or maps is allowed, duplicate map keys are not allowed
     * when decoding, and floating-point numbers are written out in their
     * 64-bit encoding form regardless of whether their value can be
     * encoded without loss in a smaller form. This implementation allows
     * CBOR objects whose canonical form exceeds 1024 bytes, the default
     * maximum size for CBOR objects in that form according to the FIDO
     * Client-to-Authenticator Protocol 2 specification.
     * @return {@code true} if CBOR objects are written out using the CTAP2
     * canonical CBOR encoding form; otherwise, {@code false}.
     */
    public final boolean getCtap2Canonical() { return propVarctap2canonical; }
private final boolean propVarctap2canonical;
  }
