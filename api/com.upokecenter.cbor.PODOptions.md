# com.upokecenter.cbor.PODOptions

    public class PODOptions extends java.lang.Object

Options for converting "plain old data" objects (better known as POCOs in
 .NET or POJOs in Java) to CBOR objects.

## Fields

* `static PODOptions Default`<br>
 The default settings for "plain old data" options.

## Constructors

* `PODOptions() PODOptions`<br>
 Initializes a new instance of the PODOptions
 class.
* `PODOptions​(boolean removeIsPrefix,
          boolean useCamelCase) PODOptions`<br>
 Initializes a new instance of the PODOptions
 class.

## Methods

* `boolean getUseCamelCase()`<br>
 Gets a value indicating whether property names are converted to camel
 case before they are used as keys.

## Field Details

### Default
    public static final PODOptions Default
The default settings for "plain old data" options.
## Method Details

### getUseCamelCase
    public final boolean getUseCamelCase()
<p>Gets a value indicating whether property names are converted to camel
 case before they are used as keys. This option changes the behavior
 of key name serialization as follows. If "useCamelCase" is <code>false
 </code> : </p> <ul> <li>In the .NET version, all key names are
 capitalized, meaning the first letter in the name is converted to
 upper case if it's a basic lower-case letter ("a" to "z"). (For
 example, "Name" and "IsName" both remain unchanged.) </li> <li>In the
 Java version, for each eligible method name, the word "get" or "set"
 is removed from the name if the name starts with that word, then the
 name is capitalized. (For example, "getName" and "setName" both
 become "Name", and "isName" becomes "IsName".) </li> </ul> <p>If
 "useCamelCase" is <code>true </code> : </p> <ul> <li>In the .NET version,
 for each eligible property name, the word "Is" is removed from the
 name if the name starts with that word, then the name is converted to
 camel case, meaning the first letter in the name is converted to
 lower case if it's a basic upper-case letter ("A" to "Z"). (For
 example, "Name" and "IsName" both become "name".) </li> <li>In the
 Java version, for each eligible method name, the word "get", "set",
 or "is" is removed from the name if the name starts with that word,
 then the name is converted to camel case. (For example, "getName",
 "setName", and "isName" all become "name".) </li> </ul> <p>In the
 description above, a name "starts with" a word if that word begins
 the name and is followed by a character other than a basic digit or
 lower-case letter, that is, other than "a" to "z" or "0" to "9". </p>

**Returns:**

* <code>true</code> If the names are converted to camel case; otherwise,
 <code>false</code> . This property is <code>true</code> by default.
