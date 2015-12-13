package com.upokecenter.cbor;

    /**
     * Specifies options for encoding CBOR objects to bytes.
     */
  public final class CBOREncodeOptions {
    /**
     * No special options for encoding. Value: 0.
     */
    public static final CBOREncodeOptions None =
      new CBOREncodeOptions(0);

    /**
     * Always encode strings with a definite-length encoding. Value: 1.
     */
    public static final CBOREncodeOptions NoIndefLengthStrings =
      new CBOREncodeOptions(1);

    private final int value;

    /**
     * Gets this options object's value.
     * @return This options object's value.
     */
    public final int getValue() {
 return this.value;
}

    /**
     * Not documented yet.
     * @param other An arbitrary object.
     * @return A Boolean object.
     */
    @Override public boolean equals(Object other) {
      CBOREncodeOptions enc = ((other instanceof CBOREncodeOptions) ? (CBOREncodeOptions)other : null);
      return (enc == null) ? (false) : (enc.getValue() == this.getValue());
    }

    /**
     * Not documented yet.
     * @return A 32-bit signed integer.
     */
    @Override public int hashCode() {
      return this.getValue();
    }

    private CBOREncodeOptions(int value) {
      this.value = value;
    }

    /**
     * Combines the flags of this options object with another options object.
     * @param o Another CBOREncodeOptions object.
     * @return A CBOREncodeOptions object.
     */
    public CBOREncodeOptions Or(CBOREncodeOptions o) {
      return new CBOREncodeOptions(this.value | o.value);
    }

    /**
     * Returns an options object whose flags are shared by this and another options
     * object.
     * @param o Another CBOREncodeOptions object.
     * @return A CBOREncodeOptions object.
     */
    public CBOREncodeOptions And(CBOREncodeOptions o) {
      return new CBOREncodeOptions(this.value & o.value);
    }
  }
