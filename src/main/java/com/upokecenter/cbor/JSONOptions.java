package com.upokecenter.cbor;

  /**
   * Includes options to control how CBOR objects are converted to and from
   * JavaScript object Notation (JSON).
   */
  public final class JSONOptions {
    /**
     * Specifies how JSON numbers are converted to CBOR objects when decoding JSON
     * (such as via {@code FromJSONString} or {@code ReadJSON}). None of these
     * conversion modes affects how CBOR objects are later encoded (such as via
     * {@code EncodeToBytes}).
     */
    public enum ConversionMode
    {
      /**
       * JSON numbers are decoded to CBOR using the full precision given in the JSON
       * text. The number will be converted to a CBOR object as follows: If the
       * number's exponent is 0 (after shifting the decimal point to the end of the
       * number without changing its value), use the rules given in the {@code
       * CBORObject.FromObject(EInteger)} method; otherwise, use the rules given in
       * the {@code CBORObject.FromObject(EDecimal)} method. An exception in version
       * 4.x involves negative zeros; if the negative zero's exponent is 0, it's
       * written as a CBOR floating-point number; otherwise the negative zero is
       * written as an EDecimal.
       */
      Full,

      /**
       * <p>JSON numbers are decoded to CBOR as their closest-rounded approximation
       * as 64-bit binary floating-point numbers (using the
       * round-to-nearest/ties-to-even rounding mode). (In some cases, numbers
       * extremely close to zero may underflow to positive or negative zero, and
       * numbers of extremely large absolute value may overflow to infinity.). It's
       * important to note that this mode affects only how JSON numbers are
       * <i>decoded</i> to a CBOR object; it doesn't affect how {@code EncodeToBytes}
       * and other methods encode CBOR objects. Notably, by default, {@code
       * EncodeToBytes} encodes CBOR floating-point values to the CBOR format in
       * their 16-bit ("half-float"), 32-bit ("single-precision"), or 64-bit
       * ("double-precision") encoding form depending on the value.</p>
       */
      Double,

      /**
       * <p>A JSON number is decoded to CBOR objects either as a CBOR integer (major
       * type 0 or 1) if the JSON number represents an integer at least -(2^53)+1 and
       * less than 2^53, or as their closest-rounded approximation as 64-bit binary
       * floating-point numbers (using the round-to-nearest/ties-to-even rounding
       * mode) otherwise. For example, the JSON number
       * 0.99999999999999999999999999999999999 is not an integer, so it's converted
       * to its closest 64-bit binary floating-point approximation, namely 1.0. (In
       * some cases, numbers extremely close to zero may underflow to positive or
       * negative zero, and numbers of extremely large absolute value may overflow to
       * infinity.). It's important to note that this mode affects only how JSON
       * numbers are <i>decoded</i> to a CBOR object; it doesn't affect how {@code
       * EncodeToBytes} and other methods encode CBOR objects. Notably, by default,
       * {@code EncodeToBytes} encodes CBOR floating-point values to the CBOR format
       * in their 16-bit ("half-float"), 32-bit ("single-precision"), or 64-bit
       * ("double-precision") encoding form depending on the value.</p>
       */
      IntOrFloat,

      /**
       * <p>A JSON number is decoded to CBOR objects either as a CBOR integer (major
       * type 0 or 1) if the number's closest-rounded approximation as a 64-bit
       * binary floating-point number (using the round-to-nearest/ties-to-even
       * rounding mode) represents an integer at least -(2^53)+1 and less than 2^53,
       * or as that approximation otherwise. For example, the JSON number
       * 0.99999999999999999999999999999999999 is the integer 1 when rounded to its
       * closest 64-bit binary floating-point approximation (1.0), so it's converted
       * to the CBOR integer 1 (major type 0). (In some cases, numbers extremely
       * close to zero may underflow to zero, and numbers of extremely large absolute
       * value may overflow to infinity.). It's important to note that this mode
       * affects only how JSON numbers are <i>decoded</i> to a CBOR object; it
       * doesn't affect how {@code EncodeToBytes} and other methods encode CBOR
       * objects. Notably, by default, {@code EncodeToBytes} encodes CBOR
       * floating-point values to the CBOR format in their 16-bit ("half-float"),
       * 32-bit ("single-precision"), or 64-bit ("double-precision") encoding form
       * depending on the value.</p>
       */
      IntOrFloatFromDouble,

      /**
       * JSON numbers are decoded to CBOR as their closest-rounded approximation to
       * an IEEE 854 decimal128 value, using the round-to-nearest/ties-to-even
       * rounding mode and the rules for the EDecimal form of that approximation as
       * given in the {@code CBORObject.FromObject(EDecimal)} method. (In some cases,
       * numbers extremely close to zero may underflow to zero, and numbers of
       * extremely large absolute value may overflow to infinity.).
       */
      Decimal128,
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.JSONOptions}
     * class with default options.
     */
    public JSONOptions() {
 this("");
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.JSONOptions}
     * class.
     * @param paramString A string setting forth the options to use. This is a
     * semicolon-separated list of options, each of which has a key and a value
     * separated by an equal sign ("="). Whitespace and line separators are not
     * allowed to appear between the semicolons or between the equal signs, nor may
     * the string begin or end with whitespace. The string can be empty, but cannot
     * be null. The following is an example of this parameter: {@code
     * writebasic = false;replacesurrogates = true}. The key can be any one of the
     * following where the letters can be any combination of basic upper-case
     * and/or basic lower-case letters: {@code replacesurrogates}, {@code
     * allowduplicatekeys}, {@code preservenegativezero}, {@code numberconversion},
     * {@code writebasic}, {@code keepkeyorder}. Other keys are ignored in this
     * version of the CBOR library. (Keys are compared using a basic
     * case-insensitive comparison, in which two strings are equal if they match
     * after converting the basic upper-case letters A to Z (U+0041 to U+005A) in
     * both strings to basic lower-case letters.) If two or more key/value pairs
     * have equal keys (in a basic case-insensitive comparison), the value given
     * for the last such key is used. The first four keys just given can have a
     * value of {@code 1}, {@code true}, {@code yes}, or {@code on} (where the
     * letters can be any combination of basic upper-case and/or basic lower-case
     * letters), which means true, and any other value meaning false. The last key,
     * {@code numberconversion}, can have a value of any name given in the {@code
     * JSONOptions.ConversionMode} enumeration (where the letters can be any
     * combination of basic upper-case and/or basic lower-case letters), and any
     * other value is unrecognized. (If the {@code numberconversion} key is not
     * given, its value is treated as {@code intorfloat} (formerly {@code full} in
     * versions earlier than 5.0). If that key is given, but has an unrecognized
     * value, an exception is thrown.) For example, {@code allowduplicatekeys = Yes}
     * and {@code allowduplicatekeys = 1} both set the {@code AllowDuplicateKeys}
     * property to true, and {@code numberconversion = double} sets the {@code
     * NumberConversion} property to {@code ConversionMode.Double}.
     * @throws NullPointerException The parameter {@code paramString} is null. In
     * the future, this class may allow other keys to store other kinds of values,
     * not just true or false.
     * @throws IllegalArgumentException An unrecognized value for {@code numberconversion}
     * was given.
     */
    public JSONOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarpreservenegativezero = parser.GetBoolean(
        "preservenegativezero",
        true);
      this.propVarallowduplicatekeys = parser.GetBoolean(
        "allowduplicatekeys",
        false);
      this.propVarkeepkeyorder = parser.GetBoolean(
        "keepkeyorder",
        false);
      this.propVarreplacesurrogates = parser.GetBoolean(
        "replacesurrogates",
        false);
      this.propVarnumberconversion = ToNumberConversion(parser.GetLCString(
        "numberconversion",
        null));
      this.propVarwritebasic = parser.GetBoolean(
        "writebasic",
        false);
    }

    /**
     * Gets the values of this options object's properties in text form.
     * @return A text string containing the values of this options object's
     * properties. The format of the string is the same as the one described in the
     * string constructor for this class.
     */
    @Override public String toString() {
      return new StringBuilder()
        .append(";replacesurrogates=")
        .append(this.getReplaceSurrogates() ? "true" : "false")
        .append(";preservenegativezero=")
        .append(this.getPreserveNegativeZero() ? "true" : "false")
        .append(";keepkeyorder=").append(this.getKeepKeyOrder() ? "true" : "false")
        .append(";numberconversion=").append(this.FromNumberConversion())
        .append(";allowduplicatekeys=")
        .append(this.getAllowDuplicateKeys() ? "true" : "false")
        .append(";writebasic=").append(this.getWriteBasic() ? "true" : "false")
        .toString();
    }

    /**
     * The default options for converting CBOR objects to JSON.
     */
    public static final JSONOptions Default = new JSONOptions();

    private String FromNumberConversion() {
      ConversionMode kind = this.getNumberConversion();
      return kind == ConversionMode.Full ? "full" :
        kind == ConversionMode.Double ? "double" :
        kind == ConversionMode.Decimal128 ? "decimal128" :
        kind == ConversionMode.IntOrFloat ? "intorfloat" :
        (kind == ConversionMode.IntOrFloatFromDouble) ?
"intorfloatfromdouble" : "full";
    }

    private static ConversionMode ToNumberConversion(String str) {
      if (str != null) {
        if (str.equals("full")) {
          return ConversionMode.Full;
        }
        if (str.equals("double")) {
          return ConversionMode.Double;
        }
        if (str.equals("decimal128")) {
          return ConversionMode.Decimal128;
        }
        if (str.equals("intorfloat")) {
          return ConversionMode.IntOrFloat;
        }
        if (str.equals("intorfloatfromdouble")) {
          return ConversionMode.IntOrFloatFromDouble;
        }
      } else {
        return ConversionMode.IntOrFloat;
      }
      throw new IllegalArgumentException("Unrecognized conversion mode");
    }

    /**
     * Gets a value indicating whether the JSON decoder should preserve the
     * distinction between positive zero and negative zero when the decoder decodes
     * JSON to a floating-point number format that makes this distinction. For a
     * value of {@code false}, if the result of parsing a JSON string would be a
     * floating-point negative zero, that result is a positive zero instead. (Note
     * that this property has no effect for conversion kind {@code
     * IntOrFloatFromDouble}, where floating-point zeros are not possible.).
     * @return A value indicating whether to preserve the distinction between
     * positive zero and negative zero when decoding JSON. The default is true.
     */
    public final boolean getPreserveNegativeZero() { return propVarpreservenegativezero; }
private final boolean propVarpreservenegativezero;

    /**
     * Gets a value indicating how JSON numbers are decoded to CBOR objects. None
     * of the conversion modes affects how CBOR objects are later encoded (such as
     * via {@code EncodeToBytes}).
     * @return A value indicating how JSON numbers are decoded to CBOR. The default
     * is {@code ConversionMode.Full}.
     */
    public final ConversionMode getNumberConversion() { return propVarnumberconversion; }
private final ConversionMode propVarnumberconversion;

    /**
     * Gets a value indicating whether JSON is written using only code points from
     * the Basic Latin block (U+0000 to U+007F), also known as ASCII.
     * @return A value indicating whether JSON is written using only code points
     * from the Basic Latin block (U+0000 to U+007F), also known as ASCII. Default
     * is false.
     */
    public final boolean getWriteBasic() { return propVarwritebasic; }
private final boolean propVarwritebasic;

    /**
     * Gets a value indicating whether to preserve the order in which a map's keys
     * appear when decoding JSON, by using maps created as though by
     * CBORObject.NewOrderedMap. If false, key order is not guaranteed to be
     * preserved when decoding JSON.
     * @return A value indicating whether to preserve the order in which a CBOR
     * map's keys appear when decoding JSON. The default is false.
     */
    public final boolean getKeepKeyOrder() { return propVarkeepkeyorder; }
private final boolean propVarkeepkeyorder;

    /**
     * Gets a value indicating whether to allow duplicate keys when reading JSON.
     * Used only when decoding JSON. If this property is {@code true} and a JSON
     * object has two or more values with the same key, the last value of that key
     * set forth in the JSON object is taken.
     * @return A value indicating whether to allow duplicate keys when reading
     * JSON. The default is false.
     */
    public final boolean getAllowDuplicateKeys() { return propVarallowduplicatekeys; }
private final boolean propVarallowduplicatekeys;

    /**
     * Gets a value indicating whether surrogate code points not part of a
     * surrogate pair (which consists of two consecutive {@code char} s forming one
     * Unicode code point) are each replaced with a replacement character (U+FFFD).
     * If false, an exception is thrown when such code points are encountered.
     * @return True, if surrogate code points not part of a surrogate pair are each
     * replaced with a replacement character, or false if an exception is thrown
     * when such code points are encountered. The default is false.
     */
    public final boolean getReplaceSurrogates() { return propVarreplacesurrogates; }
private final boolean propVarreplacesurrogates;
  }
