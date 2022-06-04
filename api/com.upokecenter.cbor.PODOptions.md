# com.upokecenter.cbor.PODOptions

## Fields

* `static PODOptions Default`<br>
 The default settings for "plain old data" options.

## Constructors

* `PODOptions() PODOptions`<br>
 Initializes a new instance of the PODOptions
 class with all the default options.
* `PODOptions​(boolean removeIsPrefix,
boolean useCamelCase)`<br>
 Deprecated.
Use the more readable String constructor instead.
 Use the more readable String constructor instead.
* `PODOptions​(java.lang.String paramString) PODOptions`<br>
 Initializes a new instance of the PODOptions
 class.

## Methods

* `boolean getUseCamelCase()`<br>
 Gets a value indicating whether property, field, and method names are
 converted to camel case before they are used as keys.
* `java.lang.String toString()`<br>
 Gets the values of this options object's properties in text form.

## Field Details

### <a id='Default'>Default</a>

The default settings for "plain old data" options.
## Method Details

### <a id='toString()'>toString</a>

Gets the values of this options object's properties in text form.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A text string containing the values of this options object's
 properties. The format of the string is the same as the one
 described in the string constructor for this class.

### <a id='getUseCamelCase()'>getUseCamelCase</a>

<p>Gets a value indicating whether property, field, and method names are
 converted to camel case before they are used as keys. This option
 changes the behavior of key name serialization as follows. If
  "useCamelCase" is <code>false</code> :</p> <ul> <li>In the .NET version,
 all key names are capitalized, meaning the first letter in the name
 is converted to a basic upper-case letter if it's a basic lower-case
  letter ("a" to "z"). (For example, "Name" and "IsName" both remain
 unchanged.)</li> <li>In the Java version, all field names are
  capitalized, and for each eligible method name, the word "get" or
  "set" is removed from the name if the name starts with that word,
  then the name is capitalized. (For example, "getName" and "setName"
  both become "Name", and "isName" becomes "IsName".)</li></ul> <p>If
  "useCamelCase" is <code>true</code> :</p> <ul> <li>In the .NET version,
  for each eligible property or field name, the word "Is" is removed
 from the name if the name starts with that word, then the name is
 converted to camel case, meaning the first letter in the name is
 converted to a basic lower-case letter if it's a basic upper-case
  letter ("A" to "Z"). (For example, "Name" and "IsName" both become
  "name", and "IsIsName" becomes "isName".)</li> <li>In the Java
  version: For each eligible method name, the word "get", "set", or
  "is" is removed from the name if the name starts with that word,
  then the name is converted to camel case. (For example, "getName",
  "setName", and "isName" all become "name".) For each eligible field
  name, the word "is" is removed from the name if the name starts with
 that word, then the name is converted to camel case. (For example,
  "name" and "isName" both become "name".)</li></ul> <p>In the
  description above, a name "starts with" a word if that word begins
 the name and is followed by a character other than a basic digit or
  basic lower-case letter, that is, other than "a" to "z" or "0" to
  "9".</p>

**Returns:**

* <code>true</code> If the names are converted to camel case; otherwise,
 <code>false</code>. This property is <code>true</code> by default.
