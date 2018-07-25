# com.upokecenter.cbor.CBOREncodeOptions

    public final class CBOREncodeOptions extends Object

Specifies options for encoding and decoding CBOR objects.

## Fields

* `static CBOREncodeOptions NoDuplicateKeys`<br>
 Deprecated.
Use 'new CBOREncodeOptions(true,false)' instead.
 Use 'new CBOREncodeOptions(true,false)' instead.
* `static CBOREncodeOptions NoIndefLengthStrings`<br>
 Deprecated.
Use 'new CBOREncodeOptions(false,true)' instead.
 Use 'new CBOREncodeOptions(false,true)' instead.
* `static CBOREncodeOptions None`<br>
 Deprecated.
Use 'new CBOREncodeOptions(true,true)' instead.
 Use 'new CBOREncodeOptions(true,true)' instead.

## Constructors

* `CBOREncodeOptions()`<br>
 Initializes a new instance of the CBOREncodeOptions class.
* `CBOREncodeOptions​(boolean useIndefLengthStrings,
                 boolean useDuplicateKeys)`<br>
 Initializes a new instance of the CBOREncodeOptions class.

## Methods

* `CBOREncodeOptions And​(CBOREncodeOptions o)`<br>
 Deprecated.
May be removed in a later version.
 May be removed in a later version.
* `boolean getUseDuplicateKeys()`<br>
 Gets a value not documented yet.
* `boolean getUseIndefLengthStrings()`<br>
 Gets a value not documented yet.
* `int getValue()`<br>
 Deprecated.
Option classes in this library will follow the form seen in JSONOptions in a
 later version; the approach used in this class is too complicated.
 Option classes in this library will follow the form seen in JSONOptions in a
 later version; the approach used in this class is too complicated.
* `CBOREncodeOptions Or​(CBOREncodeOptions o)`<br>
 Deprecated.
May be removed in a later version.
 May be removed in a later version.

## Field Details

### None
    @Deprecated public static final CBOREncodeOptions None
Deprecated.
<div class='deprecationComment'>Use 'new CBOREncodeOptions(true,true)' instead. Option classes in this
 library will follow the form seen in JSONOptions in a later version; the
 approach used in this class is too complicated.</div>

### NoIndefLengthStrings
    @Deprecated public static final CBOREncodeOptions NoIndefLengthStrings
Deprecated.
<div class='deprecationComment'>Use 'new CBOREncodeOptions(false,true)' instead. Option classes in this
 library will follow the form seen in JSONOptions in a later version; the
 approach used in this class is too complicated.</div>

### NoDuplicateKeys
    @Deprecated public static final CBOREncodeOptions NoDuplicateKeys
Deprecated.
<div class='deprecationComment'>Use 'new CBOREncodeOptions(true,false)' instead. Option classes in this
 library will follow the form seen in JSONOptions in a later version; the
 approach used in this class is too complicated.</div>

## Method Details

### getUseIndefLengthStrings
    public final boolean getUseIndefLengthStrings()
Gets a value not documented yet.

**Returns:**

* A value not documented yet.

### getUseDuplicateKeys
    public final boolean getUseDuplicateKeys()
Gets a value not documented yet.

**Returns:**

* A value not documented yet.

### getValue
    @Deprecated public final int getValue()
Deprecated.
<div class='deprecationComment'>Option classes in this library will follow the form seen in JSONOptions in a
 later version; the approach used in this class is too complicated.</div>

**Returns:**

* This options object's value.

### Or
    @Deprecated public CBOREncodeOptions Or​(CBOREncodeOptions o)
Deprecated.
<div class='deprecationComment'>May be removed in a later version. Option classes in this library will
 follow the form seen in JSONOptions in a later version; the approach used
 in this class is too complicated.</div>

**Parameters:**

* <code>o</code> - The parameter <code>o</code> is a CBOREncodeOptions object.

**Returns:**

* A new CBOREncodeOptions object.

### And
    @Deprecated public CBOREncodeOptions And​(CBOREncodeOptions o)
Deprecated.
<div class='deprecationComment'>May be removed in a later version. Option classes in this library will
 follow the form seen in JSONOptions in a later version; the approach used
 in this class is too complicated.</div>

**Parameters:**

* <code>o</code> - The parameter <code>o</code> is a CBOREncodeOptions object.

**Returns:**

* A CBOREncodeOptions object.
