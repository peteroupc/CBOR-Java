package com.upokecenter.cbor;

    /**
     * Specifies options for encoding and decoding CBOR objects.
     */
  public final class CBOREncodeOptions {
    /**
     * No special options for encoding/decoding. Value: 0.
     * @deprecated Use 'new CBOREncodeOptions(true,true)' instead. Option classes in this
 * library will follow a different form in a later version -- the approach
 * used in this class is too complicated. 'CBOREncodeOptions.Default'
 * contains recommended default options that may be adopted by certain
 * CBORObject methods in the next major version.
 */
@Deprecated
    public static final CBOREncodeOptions None =
 new CBOREncodeOptions("useindeflengthstrings=1;allowduplicatekeys=1");

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
     * @deprecated Use 'new CBOREncodeOptions(false,false)' instead. Option classes\u0020in
 * this library will follow a different form in a later\u0020version -- the
 * approach used in this class is too complicated.
 */
@Deprecated
    public static final CBOREncodeOptions NoIndefLengthStrings =
new CBOREncodeOptions("useindeflengthstrings=0;allowduplicatekeys=1");

    /**
     * Disallow duplicate keys when reading CBOR objects from a data stream. Used
     * only when decoding CBOR objects. Value: 2.
     * @deprecated Use 'new CBOREncodeOptions(true,false)' instead. Option classes\u0020in this
 * library will follow a different form in a later\u0020version -- the
 * approach used in this class is too complicated.
 */
@Deprecated
    public static final CBOREncodeOptions NoDuplicateKeys =
 new CBOREncodeOptions("useindeflengthstrings=1;allowduplicatekeys=0");

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
     * @param ctap2Canonical A value indicating whether to encode CBOR objects in
     * the CTAP2 canonical encoding form.
     */
    public CBOREncodeOptions(
      boolean useIndefLengthStrings,
      boolean allowDuplicateKeys,
      boolean ctap2Canonical) {
 this(BuildString(
         useIndefLengthStrings,
         allowDuplicateKeys,
         ctap2Canonical));
    }

    private static String BuildString(boolean useIndefLengthStrings,
      boolean allowDuplicateKeys,
      boolean ctap2Canonical) {
      return "useindeflengthstrings=" + (useIndefLengthStrings ? "1" : "0") +
         ";allowduplicatekeys=" + (allowDuplicateKeys ? "1" : "0") +
         ";ctap2canonical=" + (ctap2Canonical ? "1" : "0");
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
     * allowempty}. Keys other than these are ignored. If the same key
     * appears more than once, the value given for the last such key is
     * used. The four keys just given can have a value of {@code 1}, {@code
     * true}, {@code yes}, or {@code on} (in any combination of case),
     * which means true, and any other value meaning false. For example,
     * {@code allowduplicatekeys = Yes} and {@code allowduplicatekeys = 1} both
     * set the {@code AllowDuplicateKeys} property to true.
     * @throws NullPointerException The parameter {@code paramString} is null.
     */
    public CBOREncodeOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarresolvereferences = parser.GetBoolean("resolvereferences", true);
      this.propVaruseindeflengthstrings = parser.GetBoolean("useindeflengthstrings", true);
      this.propVarallowduplicatekeys = parser.GetBoolean("allowduplicatekeys", true);
      this.propVarallowempty = parser.GetBoolean("allowempty", false);
      this.propVarctap2canonical = parser.GetBoolean("ctap2canonical", false);
    }

    /**
     * Gets the values of this options object's properties in text form.
     * @return A text string containing the values of this options object's
     * properties. The format of the string is the same as the one
     * described in the String constructor for this class.
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
     * form, CBOR tags are not used, map keys are written out in a
     * canonical order, and non-integer numbers and integers 2^63 or
     * greater are written as 64-bit binary floating-point numbers.
     */
    public final boolean getCtap2Canonical() { return propVarctap2canonical; }
private final boolean propVarctap2canonical;

    /**
     * Gets this options object's value.<p> The return value is 1 if
     * UseIndefLengthStrings is false or 0 if true, plus 2 if
     * AllowDuplicateKeys is false or 0 if true. </p>
     * @return This options object's value.
     * @deprecated Use the toString() method to get the values of all this object's properties.
 */
@Deprecated
    public final int getValue() {
        return (this.getUseIndefLengthStrings() ? 0 : 1) + (this.getAllowDuplicateKeys() ?
0 : 2);
      }

    /**
     * Gets a value indicating whether to resolve references to sharable objects
     * and sharable strings in the process of decoding a CBOR object.<p>
     * <p>Sharable objects are marked with tag 28, and references to those
     * objects are marked with tag 29 (where a reference of 0 means the
     * first sharable object in the CBOR stream, a reference of 1 means the
     * second, and so on). Sharable strings (byte strings and text strings)
     * appear within an enclosing object marked with tag 256, and
     * references to them are marked with tag 25; in general, a string is
     * sharable only if storing its reference rather than the string would
     * save space. </p> <p>Note that unlike most other tags, these tags
     * generally care about the relative order in which objects appear in a
     * CBOR stream; thus they are not interoperable with CBOR
     * implementations that follow the generic CBOR data model (since they
     * may list map keys in an unspecified order). Interoperability
     * problems with these tags can be reduced by not using them to mark
     * keys or values of a map or to mark objects within those keys or
     * values. </p> </p>
     * @return A value indicating whether to resolve references to sharable objects
     * and sharable strings. The default is false.
     */
    public final boolean getResolveReferences() { return propVarresolvereferences; }
private final boolean propVarresolvereferences;

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
     * Returns an options object containing the combined flags of this and another
     * options object.<p> For compatibility reasons, for the object
     * returned by this method, the UseIndefLengthStrings property will be
     * set to true if that property is true in <i> both </i> objects, or
     * false otherwise; the AllowDuplicateKeys property will be set to true
     * if that property is true in <i> both </i> objects, or false
     * otherwise; and other properties will have their default values.
     * (This class formerly used the inverse of both properties and Or
     * works off of that.) </p>
     * @param o The parameter
      {@code o}
       is a CBOREncodeOptions object.
     * @return A new CBOREncodeOptions object.
     * @throws NullPointerException The parameter {@code o} is null.
     * @deprecated May be removed in a later version. Option classes\u0020in this library will
 * follow a different form in a later\u0020version -- the approach used in
 * this class is too complicated.
 */
@Deprecated
    public CBOREncodeOptions Or(CBOREncodeOptions o) {
      if (o == null) {
        throw new NullPointerException("o");
      }
      String str = new StringBuilder()
           .append("allowduplicatekeys=")
           .append((this.getAllowDuplicateKeys() && o.getAllowDuplicateKeys()) ?
"true" : "false")
           .append(";useindeflengthstrings=")
           .append((this.getUseIndefLengthStrings() && o.getUseIndefLengthStrings()) ?
"true" : "false")
           .toString();
      return new CBOREncodeOptions(str);
    }

    /**
     * Returns an options object containing the flags shared by this and another
     * options object.<p> For compatibility reasons, for the object
     * returned by this method, the UseIndefLengthStrings property will be
     * set to true if that property is true in <i> either or both </i>
     * objects, or false otherwise; the AllowDuplicateKeys property will be
     * set to true if that property is true in <i> either or both </i>
     * objects, or false otherwise; and other properties will have their
     * default values. (This class formerly used the inverse of both
     * properties and And works off of that.) </p>
     * @param o The parameter
      {@code o}
       is a CBOREncodeOptions object.
     * @return A CBOREncodeOptions object.
     * @throws NullPointerException The parameter {@code o} is null.
     * @deprecated May be removed in a later version. Option classes\u0020in this library will
 * follow a different form in a later\u0020version -- the approach used in
 * this class is too complicated.
 */
@Deprecated
    public CBOREncodeOptions And(CBOREncodeOptions o) {
      if (o == null) {
        throw new NullPointerException("o");
      }
      String str = new StringBuilder()
           .append("allowduplicatekeys=")
           .append((this.getAllowDuplicateKeys() || o.getAllowDuplicateKeys()) ?
"true" : "false")
           .append(";useindeflengthstrings=")
           .append((this.getUseIndefLengthStrings() || o.getUseIndefLengthStrings()) ?
"true" : "false")
           .toString();
      return new CBOREncodeOptions(str);
    }
  }
