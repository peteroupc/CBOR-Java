package com.upokecenter.cbor;

    /**
     * Specifies options for encoding and decoding CBOR objects.
     */
  public final class CBOREncodeOptions {
    /**
     * Default options for CBOR objects. Disallow duplicate keys, and always encode
     * strings using definite-length encoding. These are recommended
     * settings for the options that may be adopted by certain CBORObject
     * methods in the next major version.
     */
    public static final CBOREncodeOptions Default =
      new CBOREncodeOptions(false, false);

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
     * @param useIndefLengthStrings A value indicating whether to always encode
     * strings with a definite-length encoding.
     * @param allowDuplicateKeys A value indicating whether to disallow duplicate
     * keys when reading CBOR objects from a data stream.
     * @param ctap2Canonical Either {@code true} or {@code false} .
     */
    public CBOREncodeOptions(
  boolean useIndefLengthStrings,
  boolean allowDuplicateKeys,
  boolean ctap2Canonical) {
      this.propVaruseindeflengthstrings = useIndefLengthStrings;
      this.propVarallowduplicatekeys = allowDuplicateKeys;
      this.propVarctap2canonical = ctap2Canonical;
    }

    /**
     * Gets a value indicating whether to always encode strings with a
     * definite-length encoding.
     * @return A value indicating whether to always encode strings with a
     * definite-length encoding.
     */
    public final boolean getUseIndefLengthStrings() { return propVaruseindeflengthstrings; }
private final boolean propVaruseindeflengthstrings;

    /**
     * Gets a value indicating whether to disallow duplicate keys when reading CBOR
     * objects from a data stream. Used only when decoding CBOR objects.
     * @return A value indicating whether to disallow duplicate keys when reading
     * CBOR objects from a data stream.
     */
    public final boolean getAllowDuplicateKeys() { return propVarallowduplicatekeys; }
private final boolean propVarallowduplicatekeys;

    /**
     * Gets a value indicating whether CBOR objects are written out using the CTAP2
     * canonical CBOR encoding form. In this form, CBOR tags are not used,
     * map keys are written out in a canonical order, and non-integer
     * numbers and integers 2^63 or greater are written as 64-bit binary
     * floating-point numbers.
     * @return {@code true} if CBOR objects are written out using the CTAP2
     * canonical CBOR encoding form; otherwise, {@code false}.. In this
     * form, CBOR tags are not used, map keys are written out in a canonical
     * order, and non-integer numbers and integers 2^63 or greater are
     * written as 64-bit binary floating-point numbers.
     */
    public final boolean getCtap2Canonical() { return propVarctap2canonical; }
private final boolean propVarctap2canonical;
  }
