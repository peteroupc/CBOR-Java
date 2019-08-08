package com.upokecenter.cbor;

import java.util.*;

@SuppressWarnings("deprecation")

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
     * class with the given values for the options.<p>NOTE: The
     * base64Padding parameter may have no effect in the future. A future
     * version may, by default, include necessary padding when writing
     * traditional base64 to JSON and include no padding when writing
     * base64url to JSON, in accordance with the revision of the CBOR
     * specification.</p>
     * @param base64Padding Whether padding is included when writing data in
     * base64url or traditional base64 format to JSON.
     */
    public JSONOptions(boolean base64Padding) {
        this.propVarbase64padding = base64Padding;
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
     * base64padding = false}. The key can be any one of the following in any
     * combination of case: {@code base64padding}. Other keys are ignored.
     * If the same key appears more than once, the value given for the last
     * such key is used. The two keys just given can have a value of {@code
     * 1}, {@code true}, {@code yes}, or {@code on} (in any combination of
     * case), which means true, and any other value meaning false. For
     * example, {@code base64padding = Yes} and {@code base64padding = 1} both
     * set the {@code Base64Padding} property to true.
     * @throws NullPointerException The parameter {@code paramString} is null.
     */
    public JSONOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarbase64padding = parser.GetBoolean("base64padding", true);
    }

    /**
     * Gets the values of this options object's properties in text form.
     * @return A text string containing the values of this options object's
     * properties. The format of the string is the same as the one
     * described in the String constructor for this class.
     */
    @Override public String toString() {
      return new StringBuilder()
           .append("base64padding=")
           .append(this.getBase64Padding() ? "true" : "false")
           .toString();
    }

    /**
     * The default options for converting CBOR objects to JSON.
     */
    public static final JSONOptions Default = new JSONOptions();

    /**
     * Gets a value indicating whether padding is written out when writing
     * base64url or traditional base64 to JSON.<p> The padding character is
     * '='. </p>
     * @return The default is false, no padding.
     * @deprecated This option may have no effect in the future. A future version may, by
 * default, include necessary padding when writing traditional base64 to
 * JSON and include no padding when writing base64url to JSON, in accordance
 * with the revision of the CBOR specification.
 */
@Deprecated
    public final boolean getBase64Padding() { return propVarbase64padding; }
private final boolean propVarbase64padding;
  }
