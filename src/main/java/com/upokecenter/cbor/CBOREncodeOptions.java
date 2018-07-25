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
 */
@Deprecated
    public static final CBOREncodeOptions None =
      new CBOREncodeOptions(0);

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
     * Initializes a new instance of the CBOREncodeOptions class.
     */
    public CBOREncodeOptions() {
 this(false, false);}

    /**
     * Initializes a new instance of the CBOREncodeOptions class.
     * @param useIndefLengthStrings A Boolean object.
     * @param useDuplicateKeys Another Boolean object.
     */
    public CBOREncodeOptions(boolean useIndefLengthStrings, boolean
      useDuplicateKeys) {
      int val = 0;
      if (!useIndefLengthStrings) {
 val|=1;
}
      if (!useDuplicateKeys) {
 val|=2;
}
      this.value = val;
    }

    /**
     * Gets a value not documented yet.
     * @return A value not documented yet.
     */
    public final boolean getUseIndefLengthStrings() {
        return (this.value & 1) == 0;
      }

    /**
     * Gets a value not documented yet.
     * @return A value not documented yet.
     */
    public final boolean getUseDuplicateKeys() {
        return (this.value & 2) == 0;
      }

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
      this.value = value;
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
