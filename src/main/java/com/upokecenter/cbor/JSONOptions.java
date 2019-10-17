package com.upokecenter.cbor;

import java.util.*;

// TODO: PreserveNegativeZero property

  /**
   * Includes options to control how CBOR objects are converted to JSON.
   */
  public final class JSONOptions {
    /**
     * Specifies how JSON numbers are converted to CBOR when decoding JSON.
     */
    public enum ConversionKind {
       /**
        * JSON numbers are decoded to CBOR using the full precision given in the JSON
        * text. This may involve numbers being converted to
        * arbitrary-precision integers or decimal numbers, where
        * appropriate. The distinction between positive and negative zero
        * is preserved.
        */
       Full,

       /**
        * JSON numbers are decoded to CBOR as their closest-rounded approximation as
        * 64-bit binary floating-point numbers. The distinction between
        * positive and negative zero is preserved.
        */
       Double,

       /**
        * A JSON number is decoded to CBOR either as a CBOR integer (major type 0 or
        * 1) if the JSON number represents an integer at least -(2^53) and
        * less than or equal to 2^53, or as their closest-rounded
        * approximation as 64-bit binary floating-point numbers otherwise,
        * where a JSON number is treated as an integer or non-integer based
        * on the full precision given in the JSON text. For example, the
        * JSON number 0.99999999999999999999999999999999999 is not an
        * integer, so it's converted to its closest floating-point
        * approximation, namely 1.0.
        */
       IntOrFloat,

       /**
        * A JSON number is decoded to CBOR either as a CBOR integer (major type 0 or
        * 1) if the number's closest-rounded approximation as a 64-bit
        * binary floating-point number represents an integer at least
        * -(2^53) and less than or equal to 2^53, or as that approximation
        * otherwise, where a JSON number is treated as an integer or
        * non-integer based on its approximated floating-point number. For
        * example, the JSON number 0.99999999999999999999999999999999999 is
        * the integer 1 when rounded to its closest floating-point
        * approximation (1.0), so it's converted to the CBOR integer 1
        * (major type 0).
        */
       IntOrFloatFromDouble,
    };

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.JSONOptions}
     * class with default options.
     */
    public JSONOptions() {
 this("");
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.JSONOptions}
     * class with the given value for the Base64Padding option.
     * @param base64Padding Whether padding is included when writing data in
     * base64url or traditional base64 format to JSON.
     * @deprecated Use the String constructor instead.
 */
@Deprecated
    public JSONOptions(boolean base64Padding) {
 this("base64Padding=" + (base64Padding ? "1" : "0"));
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.JSONOptions}
     * class with the given values for the options.
     * @param base64Padding Whether padding is included when writing data in
     * base64url or traditional base64 format to JSON.
     * @param replaceSurrogates Whether surrogate code points not part of a
     * surrogate pair (which consists of two consecutive {@code char} s
     * forming one Unicode code point) are each replaced with a replacement
     * character (U+FFFD). The default is false; an exception is thrown
     * when such code points are encountered.
     */
@SuppressWarnings("deprecation")

/**
 * @deprecated Use the String constructor instead.
 */
@Deprecated
    public JSONOptions(boolean base64Padding, boolean replaceSurrogates) {
 this("base64Padding=" + (base64Padding ? "1" : "0") +
           ";replacesurrogates=" + (replaceSurrogates ? "1" : "0"));
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.JSONOptions}
     * class.
     * @param paramString A string setting forth the options to use. This is a
     * semicolon-separated list of options, each of which has a key and a
     *  value separated by an equal sign ("="). Whitespace and line
     * separators are not allowed to appear between the semicolons or
     * between the equal signs, nor may the string begin or end with
     * whitespace. The string can be empty, but cannot be null. The
     * following is an example of this parameter: {@code
     * base64padding = false;replacesurrogates = true}. The key can be any one
     * of the following where the letters can be any combination of basic
     * upper-case and/or basic lower-case letters: {@code base64padding},
     * {@code replacesurrogates}, {@code allowduplicatekeys}, {@code
     * preservenegativezero}, {@code numberconversion}. Other keys are
     * ignored. (Keys are compared using a basic case-insensitive
     * comparison, in which two strings are equal if they match after
     * converting the basic upper-case letters A to Z (U+0041 to U+005A) in
     * both strings to basic lower-case letters.) If two or more key/value
     * pairs have equal keys (in a basic case-insensitive comparison), the
     * value given for the last such key is used. The first four keys just
     * given can have a value of {@code 1}, {@code true}, {@code yes}, or
     * {@code on} (where the letters can be any combination of basic
     * upper-case and/or basic lower-case letters), which means true, and
     * any other value meaning false. The last key, {@code
     * numberconversion}, can have a value of any name given in the {@code
     * JSONOptions.ConversionKind} enumeration (where the letters can be
     * any combination of basic upper-case and/or basic lower-case
     * letters), or any other value, which is treated the same as {@code
     * full}. For example, {@code base64padding = Yes} and {@code
     * base64padding = 1} both set the {@code Base64Padding} property to
     * true, and {@code numberconversion = double} sets the {@code
     * NumberConversion} property to {@code ConversionKind.Double} .
     * @throws NullPointerException The parameter {@code paramString} is null. In
     * the future, this class may allow other keys to store other kinds of
     * values, not just true or false.
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
      this.propVarbase64padding = parser.GetBoolean("base64padding", true);
      // TODO: Note in release notes that JSONOptions String constructor
      // inadvertently set ReplaceSurrogates to true by default
      this.propVarreplacesurrogates = parser.GetBoolean(
        "replacesurrogates",
        false);
      this.propVarnumberconversion = ToNumberConversion(parser.GetLCString(
        "numberconversion",
        null));
    }

    /**
     * Gets the values of this options object's properties in text form.
     * @return A text string containing the values of this options object's
     * properties. The format of the string is the same as the one
     * described in the string constructor for this class.
     */
    @Override public String toString() {
      return new StringBuilder()
        .append("base64padding=").append(this.getBase64Padding() ? "true" : "false")
        .append(";replacesurrogates=")
        .append(this.getReplaceSurrogates() ? "true" : "false")
        .append(this.getPreserveNegativeZero() ? "true" : "false")
        .append(";numberconversion=").append(this.FromNumberConversion())
        .append(";allowduplicatekeys=")
        .append(this.getAllowDuplicateKeys() ? "true" : "false")
        .toString();
    }

    /**
     * The default options for converting CBOR objects to JSON.
     */
    public static final JSONOptions Default = new JSONOptions();

    /**
     * Gets a value indicating whether the Base64Padding property is true. This
     * property has no effect; in previous versions, this property meant
     * that padding was written out when writing base64url or traditional
     * base64 to JSON.
     * @return A value indicating whether the Base64Padding property is true.
     * @deprecated This property now has no effect. This library now includes \u0020necessary
 * padding when writing traditional base64 to JSON and\u0020includes no
 * padding when writing base64url to JSON, in \u0020accordance with the
 * revision of the CBOR specification.
 */
@Deprecated
    public final boolean getBase64Padding() { return propVarbase64padding; }
private final boolean propVarbase64padding;

    private String FromNumberConversion() {
      ConversionKind kind = this.getNumberConversion();
      if (kind == ConversionKind.Full) {
        return "full";
      }
      if (kind == ConversionKind.Double) {
        return "double";
      }
      if (kind == ConversionKind.IntOrFloat) {
        return "intorfloat";
      }
      return (kind == ConversionKind.IntOrFloatFromDouble) ?
"intorfloatfromdouble" : "full";
    }

    private static ConversionKind ToNumberConversion(String str) {
      if (str != null) {
        if (str.equals("full")) {
          return ConversionKind.Full;
        }
        if (str.equals("double")) {
          return ConversionKind.Double;
        }
        if (str.equals("intorfloat")) {
          return ConversionKind.IntOrFloat;
        }
        if (str.equals("intorfloatfromdouble")) {
          return ConversionKind.IntOrFloatFromDouble;
        }
      }
      return ConversionKind.Full;
    }

    /**
     * Gets a value indicating whether the JSON decoder should preserve the
     * distinction between positive zero and negative zero in
     * floating-point number formats when the decoder decodes JSON to CBOR.
     *  For example the JSON number "-0.0" (which expresses negative zero)
     * is decoded to negative zero if this property is <code>true</code>, and to
     * positive zero if this property is <code>false</code>. This property has no
     * effect for number conversion kinds in which zeros are always decoded
     * as CBOR integers (such as the <code>IntOrFloat</code> and
     * <code>IntOrFloatFromDouble</code> conversion kinds).
     * @return A value indicating whether to preserve the distinction between
     * positive zero and negative zero. The default is true.
     */
    public final boolean getPreserveNegativeZero() { return propVarpreservenegativezero; }
private final boolean propVarpreservenegativezero;

    /**
     * Gets a value indicating how JSON numbers are decoded to CBOR integers.
     * @return A value indicating how JSON numbers are decoded to CBOR integers.
     * The default is {@code ConversionKind.Full}.
     */
    public final ConversionKind getNumberConversion() { return propVarnumberconversion; }
private final ConversionKind propVarnumberconversion;

    /**
     * Gets a value indicating whether to allow duplicate keys when reading JSON.
     * Used only when decoding JSON.
     * @return A value indicating whether to allow duplicate keys when reading
     * JSON. The default is false.
     */
    public final boolean getAllowDuplicateKeys() { return propVarallowduplicatekeys; }
private final boolean propVarallowduplicatekeys;

    /**
     * Gets a value indicating whether surrogate code points not part of a
     * surrogate pair (which consists of two consecutive <code>char</code> s
     * forming one Unicode code point) are each replaced with a replacement
     * character (U+FFFD). If false, an exception is thrown when such code
     * points are encountered.
     * @return True, if surrogate code points not part of a surrogate pair are each
     * replaced with a replacement character, or false if an exception is
     * thrown when such code points are encountered. The default is false.
     */
    public final boolean getReplaceSurrogates() { return propVarreplacesurrogates; }
private final boolean propVarreplacesurrogates;
  }
