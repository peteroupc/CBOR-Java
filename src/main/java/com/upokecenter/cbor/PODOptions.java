package com.upokecenter.cbor;

    /**
     * Options for converting "plain old data" objects to CBOR objects.
     */
    public class PODOptions {
    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.PODOptions}
     * class.
     */
    public PODOptions() {
 this(true, true);
}

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.PODOptions}
     * class.
     * @param removeIsPrefix If set to {@code true} remove is prefix. NOTE: May be
     * ignored in future versions of this library.
     * @param useCamelCase If set to {@code true} use camel case.
     */
    public PODOptions(boolean removeIsPrefix, boolean useCamelCase) {
      this.propVarremoveisprefix = removeIsPrefix;

      this.propVarusecamelcase = useCamelCase;
    }

    /**
     * The default settings for "plain old data" options.
     */
    public static final PODOptions Default = new PODOptions();

    /**
     * Gets a value indicating whether the "Is" prefix in property names is removed
     * before they are used as keys.
     * @return {@code true} If the prefix is removed; otherwise, . {@code false}.
     * @deprecated Property name conversion may change, making this property obsolete.
 */
@Deprecated
        public final boolean getRemoveIsPrefix() { return propVarremoveisprefix; }
private final boolean propVarremoveisprefix;

    /**
     * Gets a value indicating whether property names are converted to camel case
     * before they are used as keys.
     * @return {@code true} If the names are converted to camel case; otherwise, .
     * {@code false}.
     */
    public final boolean getUseCamelCase() { return propVarusecamelcase; }
private final boolean propVarusecamelcase;
    }
