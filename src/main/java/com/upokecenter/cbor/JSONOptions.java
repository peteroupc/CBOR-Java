package com.upokecenter.cbor;

import java.util.*;

  /**
   * Includes options to control how CBOR objects are converted to JSON.
   */
  public final class JSONOptions {
    /**
     * Initializes a new instance of the {@link JSONOptions} class with default
     * options.
     */
    public JSONOptions() {
 this(false);
    }

    /**
     * Initializes a new instance of the {@link JSONOptions} class with the given
     * value for the Base64Padding option.
     * @param base64Padding Whether padding is included when writing data in
     * base64url or traditional base64 format to JSON.
     */
    public JSONOptions(boolean base64Padding) {
 this(base64Padding, false);
    }

    /**
     * Initializes a new instance of the {@link JSONOptions} class with the given
     * values for the options.
     * @param base64Padding Whether padding is included when writing data in
     * base64url or traditional base64 format to JSON.
     * @param replaceSurrogates Whether surrogate code points not part of a
     * surrogate pair (which consists of two consecutive {@code char} s
     * forming one Unicode code point) are each replaced with a replacement
     * character (U + FFFD). The default is false; an exception is thrown when
     * such code points are encountered.
     */
@SuppressWarnings("deprecation")
    public JSONOptions(boolean base64Padding, boolean replaceSurrogates) {
      this.propVarbase64padding = base64Padding;
      this.propVarreplacesurrogates = replaceSurrogates;
    }

    /**
     * The default options for converting CBOR objects to JSON.
     */
    public static final JSONOptions Default = new JSONOptions();

   /**
    * Gets a value indicating whether the Base64Padding property is true. This
    * property has no effect; in previous versions, this property meant that
    * padding was written out when writing base64url or traditional base64
    * to JSON.
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
    * character (U + FFFD). The default is false; an exception is thrown when
    * such code points are encountered.
    * @return True, if surrogate code points not part of a surrogate pair are each
    * replaced with a replacement character, or false if an exception is
    * thrown when such code points are encountered.
    */
    public final boolean getReplaceSurrogates() { return propVarreplacesurrogates; }
private final boolean propVarreplacesurrogates;
   }
