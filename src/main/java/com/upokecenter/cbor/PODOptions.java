package com.upokecenter.cbor;

  /**
   * Options for controlling how certain DotNET or Java objects, such as
   * so-called "plain old data" objects (better known as POCOs in DotNET or POJOs
   * in Java), are converted to CBOR objects.
   */
  public class PODOptions {
    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.PODOptions}
     * class with all the default options.
     */
    public PODOptions() {
 this("");
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.PODOptions}
     * class.
     * @param removeIsPrefix The parameter is not used.
     * @param useCamelCase The value of the "UseCamelCase" property.
     * @deprecated Use the more readable String constructor instead.
 */
@Deprecated
    public PODOptions(boolean removeIsPrefix, boolean useCamelCase) {
      this.propVarusecamelcase = useCamelCase;
    }

    /**
     * Initializes a new instance of the {@link com.upokecenter.cbor.PODOptions}
     * class.
     * @param paramString A string setting forth the options to use. This is a
     * semicolon-separated list of options, each of which has a key and a value
     * separated by an equal sign ("="). Whitespace and line separators are not
     * allowed to appear between the semicolons or between the equal signs, nor may
     * the string begin or end with whitespace. The string can be empty, but cannot
     * be null. The following is an example of this parameter: {@code
     * usecamelcase = true}. The key can be any one of the following where the
     * letters can be any combination of basic uppercase and/or basic lowercase
     * letters: {@code usecamelcase}. Other keys are ignored in this version of the
     * CBOR library. (Keys are compared using a basic case-insensitive comparison,
     * in which two strings are equal if they match after converting the basic
     * uppercase letters A to Z (U+0041 to U+005A) in both strings to basic
     * lowercase letters.) If two or more key/value pairs have equal keys (in a
     * basic case-insensitive comparison), the value given for the last such key is
     * used. The key just given can have a value of {@code 1}, {@code true}, {@code
     * yes}, or {@code on} (where the letters can be any combination of basic
     * uppercase and basic lowercase letters), which means true, and any other
     * value meaning false. For example, {@code usecamelcase = Yes} and {@code
     * usecamelcase = 1} both set the {@code UseCamelCase} property to true. In the
     * future, this class may allow other keys to store other kinds of values, not
     * just true or false.
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
     * properties. The format of the string is the same as the one described in the
     * string constructor for this class.
     */
    @Override public String toString() {
      return new StringBuilder()
        .append("usecamelcase=").append(this.getUseCamelCase() ? "true" :
"false")
        .toString();
    }

    /**
     * The default settings for "plain old data" options.
     */
    public static final PODOptions Default = new PODOptions();

    /**
     * <p>Gets a value indicating whether property, field, and method names are
     * converted to camel case before they are used as keys. This option changes
     * the behavior of key name serialization as follows. If "useCamelCase" is
     * {@code false} :</p> <ul> <li>In the .NET version, all key names are
     * capitalized, meaning the first letter in the name is converted to a basic
     * uppercase letter if it's a basic lowercase letter ("a" to "z"). (For
     * example, "Name" and "IsName" both remain unchanged.)</li><li>In the Java
     * version, all field names are capitalized, and for each eligible method name,
     * the word "get" or "set" is removed from the name if the name starts with
     * that word, then the name is capitalized. (For example, "getName" and
     * "setName" both become "Name", and "isName" becomes "IsName".)</li></ul>
     * <p>If "useCamelCase" is {@code true} :</p> <ul> <li>In the .NET version, for
     * each eligible property or field name, the word "Is" is removed from the name
     * if the name starts with that word, then the name is converted to camel case,
     * meaning the first letter in the name is converted to a basic lowercase
     * letter if it's a basic uppercase letter ("A" to "Z"). (For example, "Name"
     * and "IsName" both become "name", and "IsIsName" becomes
     * "isName".)</li><li>In the Java version: For each eligible method name, the
     * word "get", "set", or "is" is removed from the name if the name starts with
     * that word, then the name is converted to camel case. (For example,
     * "getName", "setName", and "isName" all become "name".) For each eligible
     * field name, the word "is" is removed from the name if the name starts with
     * that word, then the name is converted to camel case. (For example, "name"
     * and "isName" both become "name".)</li></ul> <p>In the description above, a
     * name "starts with" a word if that word begins the name and is followed by a
     * character other than a basic digit or basic lowercase letter, that is, other
     * than "a" to "z" or "0" to "9".</p>
     * @return {@code true} If the names are converted to camel case; otherwise,
     * {@code false}. This property is {@code true} by default.
     */
    public final boolean getUseCamelCase() { return propVarusecamelcase; }
private final boolean propVarusecamelcase;
  }
