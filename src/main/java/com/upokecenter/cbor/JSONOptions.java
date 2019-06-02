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
     * class with the given values for the options.<p>NOTE: The
     * base64Padding parameter may have no effect in the future. A future
     * version may, by default, include necessary padding when writing
     * traditional base64 to JSON and include no padding when writing
     * base64url to JSON, in accordance with the revision of the CBOR
     * specification.</p>
     * @param base64Padding Whether padding is included when writing data in
     * base64url or traditional base64 format to JSON.
     */
@SuppressWarnings("deprecation")
    public JSONOptions(boolean base64Padding) {
        this.propVarbase64padding = base64Padding;
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
* default, include necessary padding when writing traditional base64 to JSON
* and include no padding when writing base64url to JSON, in accordance with
* the revision of the CBOR specification.
 */
@Deprecated
    public final boolean getBase64Padding() { return propVarbase64padding; }
private final boolean propVarbase64padding;
   }
