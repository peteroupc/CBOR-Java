package com.upokecenter.cbor;

    /**
     * Specifies options for encoding and decoding CBOR objects.
     */
  public final class CBOREncodeOptions {
    /**
     * No special options for encoding/decoding. Value: 0.
     * @deprecated Use 'new CBOREncodeOptions(true,true)' instead. Option classes in this
* library will follow the form seen in JSONOptions in a later version; the
* approach used in this class is too complicated.
 *'CBOREncodeOptions.Default' contains recommended default options that may
* be adopted by certain CBORObject methods in the next major version.
 */
@Deprecated
    public static final CBOREncodeOptions None =
      new CBOREncodeOptions(0);

    /**
     * Default options for CBOR objects. Disallow duplicate keys, and always encode
     * strings using definite-length encoding. These are recommended
     * settings for the options that may be adopted by certain CBORObject
     * methods in the next major version.
     */
    public static final CBOREncodeOptions Default =
      new CBOREncodeOptions(false, false);

    /**
     * Always encode strings with a definite-length encoding. Used only when
     * encoding CBOR objects. Value: 1.
     * @deprecated Use 'new CBOREncodeOptions(false,true)' instead. Option classes in this
* library will follow the form seen in JSONOptions in a later version; the
* approach used in this class is too complicated.
 */
@Deprecated
    public static final CBOREncodeOptions NoIndefLengthStrings =
      new CBOREncodeOptions(1);

    /**
     * Disallow duplicate keys when reading CBOR objects from a data stream. Used
     * only when decoding CBOR objects. Value: 2.
     * @deprecated Use 'new CBOREncodeOptions(true,false)' instead. Option classes in this
* library will follow the form seen in JSONOptions in a later version; the
* approach used in this class is too complicated.
 */
@Deprecated
    public static final CBOREncodeOptions NoDuplicateKeys =
      new CBOREncodeOptions(2);

    private final int value;

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
     * @param ctap2Canonical Either {@code true } or {@code false } .
     */
    public CBOREncodeOptions(
  boolean useIndefLengthStrings,
  boolean allowDuplicateKeys,
  boolean ctap2Canonical) {
      int val = 0;
      if (!useIndefLengthStrings) {
        val |= 1;
      }
      if (!allowDuplicateKeys) {
        val |= 2;
      }
      this.value = val;
      this.propVarctap2canonical = ctap2Canonical;
    }

    /**
     * Gets a value indicating whether to always encode strings with a
     * definite-length encoding.
     * @return A value indicating whether to always encode strings with a
     * definite-length encoding.
     */
    public final boolean getUseIndefLengthStrings() {
        return (this.value & 1) == 0;
      }

    /**
     * Gets a value indicating whether to disallow duplicate keys when reading CBOR
     * objects from a data stream. Used only when decoding CBOR objects.
     * @return A value indicating whether to disallow duplicate keys when reading
     * CBOR objects from a data stream.
     */
    public final boolean getAllowDuplicateKeys() {
        return (this.value & 2) == 0;
      }

    /**
     * Gets a value indicating whether CBOR objects are written out using the CTAP2
     * canonical CBOR encoding form. In this form, CBOR tags are not used,
     * map keys are written out in a canonical order, and non-integer
     * numbers and integers 2^63 or greater are written as 64-bit binary
     * floating-point numbers.
     * @return {@code true} if CBOR objects are written out using the CTAP2
     * canonical CBOR encoding form; otherwise, {@code false} .. In this
     * form, CBOR tags are not used, map keys are written out in a canonical
     * order, and non-integer numbers and integers 2^63 or greater are
     * written as 64-bit binary floating-point numbers.
     */
    public final boolean getCtap2Canonical() { return propVarctap2canonical; }
private final boolean propVarctap2canonical;

    /**
     * Gets this options object's value.
     * @return This options object's value.
     * @deprecated Option classes in this library will follow the form seen in JSONOptions in a
* later version; the approach used in this class is too complicated.
 */
@Deprecated
    public final int getValue() {
        return this.value;
      }

    private CBOREncodeOptions(int value) {
 this((value & 1) == 0, (value & 2) == 0);
    }

    /**
     * Returns an options object containing the combined flags of this and another
     * options object.
     * @param o The parameter {@code o} is a CBOREncodeOptions object.
     * @return A new CBOREncodeOptions object.
     * @deprecated May be removed in a later version. Option classes in this library will
* follow the form seen in JSONOptions in a later version; the approach used
* in this class is too complicated.
 */
@Deprecated
    public CBOREncodeOptions Or(CBOREncodeOptions o) {
      return new CBOREncodeOptions(this.value | o.value);
    }

    /**
     * Returns an options object containing the flags shared by this and another
     * options object.
     * @param o The parameter {@code o} is a CBOREncodeOptions object.
     * @return A CBOREncodeOptions object.
     * @deprecated May be removed in a later version. Option classes in this library will
* follow the form seen in JSONOptions in a later version; the approach used
* in this class is too complicated.
 */
@Deprecated
    public CBOREncodeOptions And(CBOREncodeOptions o) {
      return new CBOREncodeOptions(this.value & o.value);
    }
  }
