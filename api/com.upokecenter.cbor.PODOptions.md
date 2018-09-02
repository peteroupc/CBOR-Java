# com.upokecenter.cbor.PODOptions

    public class PODOptions extends Object

Options for converting "plain old data" objects to CBOR objects.

## Fields

* `static PODOptions Default`<br>
 The default settings for "plain old data" options.

## Constructors

* `PODOptions() PODOptions`<br>
 Initializes a new instance of the PODOptions
 class.
* `PODOptions​(boolean removeIsPrefix,
          boolean useCamelCase) PODOptions`<br>
 Initializes a new instance of the PODOptions
 class.

## Methods

* `boolean getRemoveIsPrefix()`<br>
 Gets a value indicating whether the "Is" prefix in property names is removed
 before they are used as keys.
* `boolean getUseCamelCase()`<br>
 Gets a value indicating whether property names are converted to camel case
 before they are used as keys.

## Field Details

### Default
    public static final PODOptions Default
The default settings for "plain old data" options.
## Method Details

### getRemoveIsPrefix
    public final boolean getRemoveIsPrefix()
Gets a value indicating whether the "Is" prefix in property names is removed
 before they are used as keys.

**Returns:**

* <code>true</code> If the prefix is removed; otherwise, . <code>false</code>.

### getUseCamelCase
    public final boolean getUseCamelCase()
Gets a value indicating whether property names are converted to camel case
 before they are used as keys.

**Returns:**

* <code>true</code> If the names are converted to camel case; otherwise, .
 <code>false</code>.
