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
     * Initializes a new instance of the {@link com.upokecenter.cbor.PODOptions}
     * class.
     * @param paramString A string setting forth the options to use. This is a
     * semicolon-separated list of options, each of which has a key and a
     *  value separated by an equal sign ("="). Whitespace and line
     * separators are not allowed to appear between the semicolons or
     * between the equal signs, nor may the string begin or end with
     * whitespace. The string can be empty, but cannot be null. The
     * following is an example of this parameter: {@code
     * usecamelcase = true}. The key can be any one of the following in any
     * combination of case: {@code usecamelcase}, {@code removeisprefix}.
     * Other keys are ignored. If the same key appears more than once, the
     * value given for the last such key is used. The key just given can
     * have a value of {@code 1}, {@code true}, {@code yes}, or {@code on}
     * (in any combination of case), which means true, and any other value
     * meaning false. For example, {@code usecamelcase = Yes} and {@code
     * usecamelcase = 1} both set the {@code UseCamelCase} property to true.
     * @throws NullPointerException The parameter {@code paramString} is null.
     */
    public PODOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarusecamelcase = parser.GetBoolean("usecamelcase", true);
      this.propVarremoveisprefix = parser.GetBoolean("removeisprefix", true);
    }

    /**
     * Gets the values of this options object's properties in text form.
     * @return A text string containing the values of this options object's
     * properties. The format of the string is the same as the one
     * described in the String constructor for this class.
     */
    @Override public String toString() {
      return new StringBuilder()
           .append("usecamelcase=")
           .append(this.getUseCamelCase() ? "true" : "false")
           .append(";removeisprefix=")
           .append(this.getRemoveIsPrefix() ? "true" : "false")
           .toString();
    }

    /**
     * The default settings for "plain old data" options.
     */
    public static final PODOptions Default = new PODOptions();

    /**
     * Gets a value indicating whether the "Is" prefix in property names is removed
     * before they are used as keys.
     * @return {@code true} If the prefix is removed; otherwise, . {@code false}.
     * @deprecated Property name conversion may change, making this property\u0020obsolete.
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
