# com.upokecenter.cbor.CBORTypeMapper

    public final class CBORTypeMapper extends Object

Not documented yet.

## Methods

* `CBORTypeMapper()`<br>
 Initializes a new instance of the CBORTypeMapper class.
* `<T> CBORTypeMapper AddConverter​(Type type,
            ICBORConverter<T> converter)`<br>
 Not documented yet.
* `CBORTypeMapper AddTypeName​(String name)`<br>
 Not documented yet.
* `CBORTypeMapper AddTypePrefix​(String prefix)`<br>
 Not documented yet.
* `boolean FilterTypeName​(String typeName)`<br>
 Not documented yet.

## Constructors

* `CBORTypeMapper()`<br>
 Initializes a new instance of the CBORTypeMapper class.

## Method Details

### CBORTypeMapper
    public CBORTypeMapper()
Initializes a new instance of the CBORTypeMapper class.
### CBORTypeMapper
    public CBORTypeMapper()
Initializes a new instance of the CBORTypeMapper class.
### AddConverter
    public <T> CBORTypeMapper AddConverter​(Type type, ICBORConverter<T> converter)
Not documented yet.

**Parameters:**

* <code>type</code> - Not documented yet.

* <code>converter</code> - Not documented yet.

**Returns:**

* A CBORTypeMapper object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>type</code> or <code>
 converter</code> is null.

* <code>IllegalArgumentException</code> - Converter doesn't contain a proper ToCBORObject
 method.

### FilterTypeName
    public boolean FilterTypeName​(String typeName)
Not documented yet.

**Parameters:**

* <code>typeName</code> - Not documented yet.

**Returns:**

* A Boolean object.

### AddTypePrefix
    public CBORTypeMapper AddTypePrefix​(String prefix)
Not documented yet.

**Parameters:**

* <code>prefix</code> - Not documented yet.

**Returns:**

* A CBORTypeMapper object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>prefix</code> is null.

### AddTypeName
    public CBORTypeMapper AddTypeName​(String name)
Not documented yet.

**Parameters:**

* <code>name</code> - Not documented yet.

**Returns:**

* A CBORTypeMapper object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>name</code> is null.
