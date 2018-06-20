package com.upokecenter.cbor;

    /**
     * Options for converting "plain old data" objects to CBOR objects.
     */
    public class PODOptions {
    /**
     * Initializes a new instance of the {@link CBOR.PeterO.Cbor.PODOptions} class.
     */
    public PODOptions() {
 this(true, true); }

    /**
     * Initializes a new instance of the {@link CBOR.PeterO.Cbor.PODOptions} class.
     * @param removeIsPrefix If set to {@code true} remove is prefix.
     * @param useCamelCase If set to {@code true} use camel case.
     */
    public PODOptions(boolean removeIsPrefix, boolean useCamelCase) {
      this.setRemoveIsPrefix(removeIsPrefix);
      this.setUseCamelCase(useCamelCase);
    }

    /**
     * The default settings for "plain old data" options.
     */
    public static final PODOptions Default = new PODOptions();

    /**
     * Gets a value indicating whether the "Is" prefix in property names is removed
     * before they are used as keys.
     * @return {@code true} if the prefix is removed; otherwise, {@code false}.
     */
        private boolean propVarremoveisprefix;
public final boolean getRemoveIsPrefix() { return propVarremoveisprefix; }
private final void setRemoveIsPrefix(boolean value) { propVarremoveisprefix = value; }

    /**
     * Gets a value indicating whether property names are converted to camel case
     * before they are used as keys.
     * @return {@code true} if the names are converted to camel case; otherwise,
     * {@code false}.
     */
    private boolean propVarusecamelcase;
public final boolean getUseCamelCase() { return propVarusecamelcase; }
private final void setUseCamelCase(boolean value) { propVarusecamelcase = value; }
    }
