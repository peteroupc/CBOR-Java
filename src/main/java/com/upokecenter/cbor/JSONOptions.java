package com.upokecenter.cbor;

import java.util.*;

    /**
     * Includes options to control how CBOR objects are converted to JSON.
     */
  public final class JSONOptions {
    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.JSONOptions}
     * class with default options.
     */
    public JSONOptions() {
 this(false);
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.JSONOptions}
     * class with the given value for the Base64Padding option.
     * @param base64Padding Whether padding is included when writing data in
     * base64url or traditional base64 format to JSON.
     */
    public JSONOptions(boolean base64Padding) {
 this(base64Padding, false);
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
    public JSONOptions(boolean base64Padding, boolean replaceSurrogates) {
      this.propVarbase64padding = base64Padding;
      this.propVarreplacesurrogates = replaceSurrogates;
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
     * of the following in any combination of case: {@code base64padding},
     * {@code replacesurrogates}. Other keys are ignored. (Keys are
     * compared using a basic case-insensitive comparison, in which two
     * strings are equal if they match after converting the basic
     * upper-case letters A to Z (U+0041 to U+005A) in both strings to
     * basic lower-case letters.) If two or more key/value pairs have equal
     * keys (in a basic case-insensitive comparison), the value given for
     * the last such key is used. The two keys just given can have a value
     * of {@code 1}, {@code true}, {@code yes}, or {@code on} (in any
     * combination of case), which means true, and any other value meaning
     * false. For example, {@code base64padding = Yes} and {@code
     * base64padding = 1} both set the {@code Base64Padding} property to
     * true.
     * @throws NullPointerException The parameter {@code paramString} is null.
     */
    public JSONOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarbase64padding = parser.GetBoolean("base64padding", true);
      this.propVarreplacesurrogates = parser.GetBoolean("replacesurrogates", true);
    }

    /**
     * Gets the values of this options object's properties in text form.
     * @return A text string containing the values of this options object's
     * properties. The format of the string is the same as the one
     * described in the string constructor for this class.
     */
    @Override public String toString() {
      return new StringBuilder()
           .append("base64padding=")
           .append(this.getBase64Padding() ? "true" : "false")
           .append(";replacesurrogates=")
           .append(this.getReplaceSurrogates() ? "true" : "false")
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
     * @deprecated This option now has no effect. This library now includes necessary padding
 * when writing traditional base64 to JSON and includes no padding when
 * writing base64url to JSON, in accordance with the revision of the CBOR
 * specification.
 */
@Deprecated
    public final boolean getBase64Padding() { return propVarbase64padding; }
private final boolean propVarbase64padding;

    /**
     * Gets a value indicating whether surrogate code points not part of a
     * surrogate pair (which consists of two consecutive <code>char</code> s
     * forming one Unicode code point) are each replaced with a replacement
     * character (U+FFFD). The default is false; an exception is thrown
     * when such code points are encountered.
     * @return True, if surrogate code points not part of a surrogate pair are each
     * replaced with a replacement character, or false if an exception is
     * thrown when such code points are encountered.
     */
    public final boolean getReplaceSurrogates() { return propVarreplacesurrogates; }
private final boolean propVarreplacesurrogates;
   }
