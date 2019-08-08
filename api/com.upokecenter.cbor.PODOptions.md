# com.upokecenter.cbor.PODOptions

    public class PODOptions extends java.lang.Object

Options for converting "plain old data" objects to CBOR objects.

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
* `PODOptions​(java.lang.String paramString) PODOptions`<br>
 Initializes a new instance of the PODOptions
 class.

## Methods

* `boolean getRemoveIsPrefix()`<br>
 Deprecated.
Property name conversion may change, making this property obsolete.
 Property name conversion may change, making this property obsolete.
* `boolean getUseCamelCase()`<br>
 Gets a value indicating whether property names are converted to camel case
 before they are used as keys.
* `java.lang.String toString()`<br>
 Gets the values of this options object's properties in text form.

## Field Details

### Default
    public static final PODOptions Default
The default settings for "plain old data" options.
## Method Details

### toString
    public java.lang.String toString()
Gets the values of this options object's properties in text form.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A text string containing the values of this options object's
 properties. The format of the string is the same as the one
 described in the String constructor for this class.

### getRemoveIsPrefix
    @Deprecated public final boolean getRemoveIsPrefix()
Deprecated.
Property name conversion may change, making this property obsolete.

**Returns:**

* <code>true</code> If the prefix is removed; otherwise, . <code>false</code>.

### getUseCamelCase
    public final boolean getUseCamelCase()
Gets a value indicating whether property names are converted to camel case
 before they are used as keys.

**Returns:**

* <code>true</code> If the names are converted to camel case; otherwise, .
 <code>false</code>.
