package com.upokecenter.cbor;

    /**
     * Specifies options for encoding and decoding CBOR objects.
     */
  public final class CBOREncodeOptions {
    /**
     * No special options for encoding/decoding. Value: 0.
     */
    public static final CBOREncodeOptions None =
      new CBOREncodeOptions(0);

    /**
     * Always encode strings with a definite-length encoding. Used only when
     * encoding CBOR objects. Value: 1.
     */
    public static final CBOREncodeOptions NoIndefLengthStrings =
      new CBOREncodeOptions(1);

    /**
     * Disallow duplicate keys when reading CBOR objects from a data stream. Used
     * only when decoding CBOR objects. Value: 2.
     */
    public static final CBOREncodeOptions NoDuplicateKeys =
      new CBOREncodeOptions(2);

    private final int value;

    /**
     * Gets this options object's value.
     * @return This options object's value.
     */
    public final int getValue() {
        return this.value;
      }

    private CBOREncodeOptions(int value) {
      this.value = value;
    }

    /**
     * Returns an options object containing the combined flags of this and another
     * options object.
     * @param o The parameter {@code o} is a CBOREncodeOptions object.
     * @return A new CBOREncodeOptions object.
     */
    public CBOREncodeOptions Or(CBOREncodeOptions o) {
      return new CBOREncodeOptions(this.value | o.value);
    }

    /**
     * Returns an options object containing the flags shared by this and another
     * options object.
     * @param o The parameter {@code o} is a CBOREncodeOptions object.
     * @return A CBOREncodeOptions object.
     */
    public CBOREncodeOptions And(CBOREncodeOptions o) {
      return new CBOREncodeOptions(this.value & o.value);
    }
  }
