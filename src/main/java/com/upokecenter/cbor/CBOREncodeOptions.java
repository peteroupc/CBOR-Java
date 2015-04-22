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

    private int value;

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
     * Combines the flags of this options object with another options object.
     * @param o A CBOREncodeOptions object. (2).
     * @return A CBOREncodeOptions object.
     */
    public CBOREncodeOptions Or(CBOREncodeOptions o) {
      return new CBOREncodeOptions(this.value | o.value);
    }

    /**
     * Returns an options object whose flags are shared by this and another options
     * object.
     * @param o A CBOREncodeOptions object. (2).
     * @return A CBOREncodeOptions object.
     */
    public CBOREncodeOptions And(CBOREncodeOptions o) {
      return new CBOREncodeOptions(this.value & o.value);
    }
  }
