package com.upokecenter.cbor;

    /**
     * Options for converting "plain old data" objects (better known as POCOs in
     * .NET or POJOs in Java) to CBOR objects.
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
     * @param removeIsPrefix If set to {@code true} remove is prefix.
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
     * @deprecated Property name conversion has changed, making this property obsolete.
 */
@Deprecated
        public final boolean getRemoveIsPrefix() { return propVarremoveisprefix; }
private final boolean propVarremoveisprefix;

    /**
     * <p>Gets a value indicating whether property names are converted to camel
     * case before they are used as keys. This option changes the behavior
     * of key name serialization as follows. If "useCamelCase" is
     * <code>false</code>:</p><ul> <li>In the .NET version, all key names are
     * capitalized, meaning the first letter in the name is converted to
     * upper case if it's a basic lower-case letter ("a" to "z"). (For
     * example, "Name" and "IsName" both remain unchanged.)</li> <li>In the
     * Java version, for each eligible method name, the word "get" or "set"
     * is removed from the name if the name starts with that word, then the
     * name is capitalized. (For example, "getName" and "setName" both
     * become "Name", and "isName" becomes "IsName".)</li> </ul><p>If
     * "useCamelCase" is <code>true</code>:</p><ul> <li>In the .NET version, for
     * each eligible method name, the word "Is" is removed from the name if
     * the name starts with that word, then the name is converted to camel
     * case, meaning the first letter in the name is converted to camel case
     * if it's a basic lower-case letter ("a" to "z"). (For example, "Name"
     * and "IsName" both become "name".)</li> <li>In the Java version, for
     * each eligible method name, the word "get", "set", or "is" is removed
     * from the name if the name starts with that word, then the name is
     * converted to camel case. (For example, "getName", "setName", and
     * "isName" all become "name".)</li> </ul> <p>In the description above,
     * a name "starts with" a word if that word begins the name and is
     * followed by a character other than a basic digit or lower-case
     * letter, that is, other than "a" to "z" or "0" to "9".</p>
     * @return {@code true} If the names are converted to camel case; otherwise, .
     * {@code false}.
     */
    public final boolean getUseCamelCase() { return propVarusecamelcase; }
private final boolean propVarusecamelcase;
    }
