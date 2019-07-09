# com.upokecenter.cbor.CBORTypeMapper

    public final class CBORTypeMapper extends java.lang.Object

Holds converters to customize the serialization and deserialization behavior
 of <code>CBORObject.FromObject</code> and <code>CBORObject#ToObject</code> , as
 well as type filters for <code>ToObject</code>.

## Methods

* `CBORTypeMapper()`<br>
 Initializes a new instance of the CBORTypeMapper class.
* `<T> CBORTypeMapper AddConverter​(java.lang.reflect.Type type,
            ICBORConverter<T> converter)`<br>
 Registers an object that converts objects of a given type to CBOR objects
 (called a CBOR converter).
* `CBORTypeMapper AddTypeName​(java.lang.String name)`<br>
 Adds the fully qualified name of a Java or.NET type for use in type
 matching.
* `CBORTypeMapper AddTypePrefix​(java.lang.String prefix)`<br>
 Adds a prefix of a Java or.NET type for use in type matching.
* `boolean FilterTypeName​(java.lang.String typeName)`<br>
 Returns whether the given Java or.NET type name fits the filters given in
 this mapper.

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
    public <T> CBORTypeMapper AddConverter​(java.lang.reflect.Type type, ICBORConverter<T> converter)
Registers an object that converts objects of a given type to CBOR objects
 (called a CBOR converter).

**Type Parameters:**

* <code>T</code> - Must be the same as the "type" parameter.

**Parameters:**

* <code>type</code> - A Type object specifying the type that the converter converts to
 CBOR objects.

* <code>converter</code> - The parameter <code>converter</code> is an ICBORConverter
 object.

**Returns:**

* This object.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>type</code> or <code>
 converter</code> is null.

* <code>java.lang.IllegalArgumentException</code> - Converter doesn't contain a proper
  ToCBORObject method".

### FilterTypeName
    public boolean FilterTypeName​(java.lang.String typeName)
Returns whether the given Java or.NET type name fits the filters given in
 this mapper.

**Parameters:**

* <code>typeName</code> - The fully qualified name of a Java or.NET class (e.g.,
 <code>java.math.BigInteger</code> or <code>
 System.Globalization.CultureInfo</code>).

**Returns:**

* Either <code>true</code> if the given Java or.NET type name fits the
 filters given in this mapper, or <code>false</code> otherwise.

### AddTypePrefix
    public CBORTypeMapper AddTypePrefix​(java.lang.String prefix)
Adds a prefix of a Java or.NET type for use in type matching. A type
 matches a prefix if its fully qualified name is or begins with that
 prefix, using codepoint-by-codepoint (case-sensitive) matching.

**Parameters:**

* <code>prefix</code> - The prefix of a Java or.NET type (e.g., `java.math.` or
 `System.Globalization`).

**Returns:**

* This object.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>prefix</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The parameter <code>prefix</code> is empty.

### AddTypeName
    public CBORTypeMapper AddTypeName​(java.lang.String name)
Adds the fully qualified name of a Java or.NET type for use in type
 matching.

**Parameters:**

* <code>name</code> - The fully qualified name of a Java or.NET class (e.g., <code>
 java.math.BigInteger</code> or <code>System.Globalization.CultureInfo</code>).

**Returns:**

* This object.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>name</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The parameter <code>name</code> is empty.
