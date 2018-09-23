# com.upokecenter.cbor.CBORTypeMapper

    public final class CBORTypeMapper extends Object

Not documented yet.

## Methods

* `CBORTypeMapper()`<br>
 Initializes a new instance of the CBORTypeMapper class.
* `<T> void AddConverter​(Type type,
            ICBORConverter<T> converter)`<br>
 Not documented yet.
* `CBORTypeMapper AddTypeName​(String name)`<br>
 Adds the fully qualified name of a Java or .NET type for use in type
 matching.
* `CBORTypeMapper AddTypePrefix​(String prefix)`<br>
 Adds a prefix of a Java or .NET type for use in type matching.
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
    public <T> void AddConverter​(Type type, ICBORConverter<T> converter)
Not documented yet.

**Type Parameters:**

* <code>T</code> - Type parameter not documented yet.

**Parameters:**

* <code>type</code> - The parameter <code>type</code> is not documented yet.

* <code>converter</code> - The parameter <code>converter</code> is not documented yet.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>type</code> or <code>
 converter</code> is null.

* <code>IllegalArgumentException</code> - Converter doesn't contain a proper
 ToCBORObject method.

### FilterTypeName
    public boolean FilterTypeName​(String typeName)
Not documented yet.

**Parameters:**

* <code>typeName</code> - Not documented yet.

**Returns:**

* A Boolean object.

### AddTypePrefix
    public CBORTypeMapper AddTypePrefix​(String prefix)
Adds a prefix of a Java or .NET type for use in type matching. A type
 matches a prefix if its fully qualified name is or begins with that
 prefix, using codepoint-by-codepoint (case-sensitive) matching.

**Parameters:**

* <code>prefix</code> - The parameter <code>prefix</code> is not documented yet.

**Returns:**

* A CBORTypeMapper object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>prefix</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>prefix</code> is empty.

### AddTypeName
    public CBORTypeMapper AddTypeName​(String name)
Adds the fully qualified name of a Java or .NET type for use in type
 matching.

**Parameters:**

* <code>name</code> - The parameter <code>name</code> is not documented yet.

**Returns:**

* A CBORTypeMapper object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>name</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>name</code> is empty.
