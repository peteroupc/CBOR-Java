package com.upokecenter.cbor;

    /**
     * Options for converting "plain old data" objects (better known as POCOs in
     *.NET or POJOs in Java) to CBOR objects.
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
     * @param removeIsPrefix The parameter is not used.
     * @param useCamelCase The value of the "UseCamelCase" property.
     */

    public PODOptions(boolean removeIsPrefix, boolean useCamelCase) {
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
     * combination of case: {@code usecamelcase}. Other keys are ignored.
     * (Keys are compared using a basic case-insensitive comparison, in
     * which two strings are equal if they match after converting the basic
     * upper-case letters A to Z (U+0041 to U+005A) in both strings to
     * basic lower-case letters.) If two or more key/value pairs have equal
     * keys (in a basic case-insensitive comparison), the value given for
     * the last such key is used. The key just given can have a value of
     * {@code 1}, {@code true}, {@code yes} , or {@code on} (in any
     * combination of case), which means true, and any other value meaning
     * false. For example, {@code usecamelcase = Yes} and {@code
     * usecamelcase = 1} both set the {@code UseCamelCase} property to true.
     * @throws NullPointerException The parameter {@code paramString} is null.
     */
    public PODOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarusecamelcase = parser.GetBoolean("usecamelcase", true);
    }

    /**
     * Gets the values of this options object's properties in text form.
     * @return A text string containing the values of this options object's
     * properties. The format of the string is the same as the one
     * described in the string constructor for this class.
     */
    @Override public String toString() {
      return new StringBuilder()
           .append("usecamelcase=")
           .append(this.getUseCamelCase() ? "true" : "false")
           .toString();
    }

    /**
     * The default settings for "plain old data" options.
     */
    public static final PODOptions Default = new PODOptions();

    /**
     * <p>Gets a value indicating whether property names are converted to camel
     * case before they are used as keys. This option changes the behavior
     *  of key name serialization as follows. If "useCamelCase" is
     * <code>false</code> :</p> <ul> <li>In the .NET version, all key names are
     * capitalized, meaning the first letter in the name is converted to a
     *  basic upper-case letter if it's a basic lower-case letter ("a" to
     *  "z"). (For example, "Name" and "IsName" both remain unchanged.)</li>
     * <li>In the Java version, for each eligible method name, the word
     *  "get" or "set" is removed from the name if the name starts with that
     *  word, then the name is capitalized. (For example, "getName" and
     *  "setName" both become "Name", and "isName" becomes
     *  "IsName".)</li></ul> <p>If "useCamelCase" is <code>true</code> :</p> <ul>
     * <li>In the .NET version, for each eligible property name, the word
     *  "Is" is removed from the name if the name starts with that word,
     * then the name is converted to camel case, meaning the first letter
     * in the name is converted to a basic lower-case letter if it's a
     *  basic upper-case letter ("A" to "Z"). (For example, "Name" and
     *  "IsName" both become "name".)</li> <li>In the Java version, for each
     *  eligible method name, the word "get", "set", or "is" is removed from
     * the name if the name starts with that word, then the name is
     *  converted to camel case. (For example, "getName", "setName", and
     *  "isName" all become "name".)</li></ul> <p>In the description above,
     *  a name "starts with" a word if that word begins the name and is
     * followed by a character other than a basic digit or basic lower-case
     *  letter, that is, other than "a" to "z" or "0" to "9".</p>
     * @return {@code true} If the names are converted to camel case; otherwise,
     * {@code false}. This property is {@code true} by default.
     */
    public final boolean getUseCamelCase() { return propVarusecamelcase; }
private final boolean propVarusecamelcase;
    }
