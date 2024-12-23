# com.upokecenter.cbor.CBORObject

    public final class CBORObject extends Object implements Comparable<CBORObject>

<p>Represents an object in Concise Binary Object Representation (CBOR) and
 contains methods for reading and writing CBOR data. CBOR is an Internet
 Standard and defined in RFC 8949.</p><p><b>Converting CBOR objects</b></p>
 <p>There are many ways to get a CBOR object, including from bytes, objects,
 streams and JSON, as described below.</p> <p><b>To and from byte arrays:</b>
 The CBORObject.DecodeFromBytes method converts a byte array in CBOR format
 to a CBOR object. The EncodeToBytes method converts a CBOR object to its
 corresponding byte array in CBOR format.</p> <p><b>To and from data
 streams:</b> The CBORObject.Write methods write many kinds of objects to a
 data stream, including numbers, CBOR objects, strings, and arrays of numbers
 and strings. The CBORObject.Read method reads a CBOR object from a data
 stream.</p> <p><b>To and from other objects:</b> The <code>
 CBORObject.From.get(Type)</code> method converts many kinds of objects to a CBOR
 object, including numbers, strings, and arrays and maps of numbers and
 strings. Methods like AsNumber and AsString convert a CBOR object to
 different types of object. The <code>CBORObject.ToObject</code> method converts a
 CBOR object to an object of a given type; for example, a CBOR array to a
 native <code>ArrayList</code> (or <code>ArrayList</code> in Java), or a CBOR integer
 to an <code>int</code> or <code>long</code>. Of these methods, the.NET versions of the
 methods <code>CBORObject.FromObject</code> and <code>CBORObject.ToObject</code> are
 not compatible with any context that disallows reflection, such as
 ahead-of-time compilation or self-contained app deployment.</p> <p><b>To and
 from JSON:</b> This class also doubles as a reader and writer of JavaScript
 object Notation (JSON). The CBORObject.FromJSONString method converts JSON
 in text string form to a CBOR object, and the ToJSONString method converts a
 CBOR object to a JSON string. (Note that the conversion from CBOR to JSON is
 not always without loss and may make it impossible to recover the original
 object when converting the JSON back to CBOR. See the ToJSONString
 documentation.) Likewise, ToJSONBytes and FromJSONBytes work with JSON in
 the form of byte arrays rather than text strings.</p> <p>In addition, the
 CBORObject.WriteJSON method writes many kinds of objects as JSON to a data
 stream, including numbers, CBOR objects, strings, and arrays of numbers and
 strings. The CBORObject.Read method reads a CBOR object from a JSON data
 stream.</p> <p><b>Comparison Considerations:</b></p> <p>Instances of
 CBORObject should not be compared for equality using the "==" operator; it's
 possible to create two CBOR objects with the same value but not the same
 reference. (The "==" operator might only check if each side of the operator
 is the same instance.)</p> <p>This class's natural ordering (under the
 compareTo method) is consistent with the Equals method, meaning that two
 values that compare as equal under the compareTo method are also equal under
 the Equals method; this is a change in version 4.0. Two otherwise equal
 objects with different tags are not treated as equal by both compareTo and
 Equals. To strip the tags from a CBOR object before comparing, use the
 <code>Untag</code> method.</p> <p><b>Thread Safety:</b></p> <p>Certain CBOR
 objects are immutable (their values can't be changed), so they are
 inherently safe for use by multiple threads.</p> <p>CBOR objects that are
 arrays, maps, and byte strings (whether or not they are tagged) are mutable,
 but this class doesn't attempt to synchronize reads and writes to those
 objects by multiple threads, so those objects are not thread safe without
 such synchronization.</p> <p>One kind of CBOR object is called a map, or a
 list of key-value pairs. Keys can be any kind of CBOR object, including
 numbers, strings, arrays, and maps. However, untagged text strings (which
 means GetTags returns an empty array and the Type property, or "getType()"
 in Java, returns TextString) are the most suitable to use as keys; other
 kinds of CBOR object are much better used as map values instead, keeping in
 mind that some of them are not thread safe without synchronizing reads and
 writes to them.</p> <p>To find the type of a CBOR object, call its Type
 property (or "getType()" in Java). The return value can be Integer,
 FloatingPoint, Boolean, SimpleValue, or TextString for immutable CBOR
 objects, and Array, Map, or ByteString for mutable CBOR objects.</p>
 <p><b>Nesting Depth:</b></p> <p>The DecodeFromBytes and Read methods can
 only read objects with a limited maximum depth of arrays and maps nested
 within other arrays and maps. The code sets this maximum depth to 500
 (allowing more than enough nesting for most purposes), but it's possible
 that stack overflows in some runtimes might lower the effective maximum
 nesting depth. When the nesting depth goes above 500, the DecodeFromBytes
 and Read methods throw a CBORException.</p> <p>The ReadJSON and
 FromJSONString methods currently have nesting depths of 1000.</p>

## Fields

* `static final CBORObject False`<br>
 Represents the value false.

* `static final CBORObject NaN`<br>
 A not-a-number value.

* `static final CBORObject NegativeInfinity`<br>
 The value negative infinity.

* `static final CBORObject Null`<br>
 Represents the value null.

* `static final CBORObject PositiveInfinity`<br>
 The value positive infinity.

* `static final CBORObject True`<br>
 Represents the value true.

* `static final CBORObject Undefined`<br>
 Represents the value undefined.

* `static final CBORObject Zero`<br>
 Gets a CBOR object for the number zero.

## Methods

* `CBORObject Add(CBORObject obj)`<br>
 Adds a new object to the end of this array.

* `CBORObject Add(Object obj)`<br>
 Converts an object to a CBOR object and adds it to the end of this
 array.

* `CBORObject Add(Object key,
 Object valueOb)`<br>
 Adds a new key and its value to this CBOR map, or adds the value if the
 key doesn't exist.

* `CBORObject ApplyJSONPatch(CBORObject patch)`<br>
 Returns a copy of this object after applying the operations in a JSON
 patch, in the form of a CBOR object.

* `boolean AsBoolean()`<br>
 Returns false if this object is a CBOR false, null, or undefined value
 (whether or not the object has tags); otherwise, true.

* `double AsDouble()`<br>
 Converts this object to a 64-bit floating point number.

* `long AsDoubleBits()`<br>
 Converts this object to the bits of a 64-bit floating-point number if this
 CBOR object's type is FloatingPoint.

* `double AsDoubleValue()`<br>
 Converts this object to a 64-bit floating-point number if this CBOR object's
 type is FloatingPoint.

* `com.upokecenter.numbers.EInteger AsEIntegerValue()`<br>
 Converts this object to an arbitrary-precision integer if this CBOR object's
 type is Integer.

* `UUID AsGuid()`<br>
 Converts this object to a java.util.UUID.

* `int AsInt32()`<br>
 Converts this object to a 32-bit signed integer.

* `int AsInt32Value()`<br>
 Converts this object to a 32-bit signed integer if this CBOR object's
 type is Integer.

* `long AsInt64Value()`<br>
 Converts this object to a 64-bit signed integer if this CBOR object's
 type is Integer.

* `CBORNumber AsNumber()`<br>
 Converts this object to a CBOR number.

* `float AsSingle()`<br>
 Converts this object to a 32-bit floating point number.

* `String AsString()`<br>
 Gets the value of this object as a text string.

* `CBORObject AtJSONPointer(String pointer)`<br>
 Gets the CBOR object referred to by a JSON Pointer according to RFC6901.

* `CBORObject AtJSONPointer(String pointer,
 CBORObject defaultValue)`<br>
 Gets the CBOR object referred to by a JSON Pointer according to RFC6901,
 or a default value if the operation fails.

* `long CalcEncodedSize()`<br>
 Calculates the number of bytes this CBOR object takes when serialized as a
 byte array using the EncodeToBytes() method.

* `boolean CanValueFitInInt32()`<br>
 Returns whether this CBOR object stores an integer (CBORType.Integer) within
 the range of a 32-bit signed integer.

* `boolean CanValueFitInInt64()`<br>
 Returns whether this CBOR object stores an integer (CBORType.Integer) within
 the range of a 64-bit signed integer.

* `void Clear()`<br>
 Removes all items from this CBOR array or all keys and values from this CBOR
 map.

* `int compareTo(CBORObject other)`<br>
 Compares two CBOR objects.

* `int CompareToIgnoreTags(CBORObject other)`<br>
 Compares this object and another CBOR object, ignoring the tags they have,
 if any.

* `boolean ContainsKey(CBORObject key)`<br>
 Determines whether a value of the given key exists in this object.

* `boolean ContainsKey(Object objKey)`<br>
 Determines whether a value of the given key exists in this object.

* `boolean ContainsKey(String key)`<br>
 Determines whether a value of the given key exists in this object.

* `static CBORObject DecodeFromBytes(byte[] data)`<br>
 Generates a CBOR object from an array of CBOR-encoded bytes.

* `static CBORObject DecodeFromBytes(byte[] data,
 CBOREncodeOptions options)`<br>
 Generates a CBOR object from an array of CBOR-encoded bytes, using the
 given CBOREncodeOptions object to control the decoding
 process.

* `static <T> T DecodeObjectFromBytes(byte[] data,
 CBOREncodeOptions enc,
 Type t)`<br>
 Generates an object of an arbitrary type from an array of CBOR-encoded
 bytes, using the given CBOREncodeOptions object to control the
 decoding process.

* `static <T> T DecodeObjectFromBytes(byte[] data,
 CBOREncodeOptions enc,
 Type t,
 CBORTypeMapper mapper,
 PODOptions pod)`<br>
 Generates an object of an arbitrary type from an array of CBOR-encoded
 bytes, using the given CBOREncodeOptions object to control the
 decoding process.

* `static <T> T DecodeObjectFromBytes(byte[] data,
 Type t)`<br>
 Generates an object of an arbitrary type from an array of CBOR-encoded
 bytes.

* `static <T> T DecodeObjectFromBytes(byte[] data,
 Type t,
 CBORTypeMapper mapper,
 PODOptions pod)`<br>
 Generates an object of an arbitrary type from an array of CBOR-encoded
 bytes.

* `static CBORObject[] DecodeSequenceFromBytes(byte[] data)`<br>
 Generates a sequence of CBOR objects from an array of CBOR-encoded bytes.

* `static CBORObject[] DecodeSequenceFromBytes(byte[] data,
 CBOREncodeOptions options)`<br>
 Generates a sequence of CBOR objects from an array of CBOR-encoded bytes.

* `byte[] EncodeToBytes()`<br>
 Writes the binary representation of this CBOR object and returns a byte
 array of that representation.

* `byte[] EncodeToBytes(CBOREncodeOptions options)`<br>
 Writes the binary representation of this CBOR object and returns a byte
 array of that representation, using the specified options for encoding the
 object to CBOR format.

* `boolean equals(CBORObject other)`<br>
 Compares the equality of two CBOR objects.

* `boolean equals(Object obj)`<br>
 Determines whether this object and another object are equal and have the
 same type.

* `static CBORObject FromBool(boolean value)`<br>
 Returns the CBOR true value or false value, depending on "value".

* `static CBORObject FromByte(byte value)`<br>
 Generates a CBOR object from a byte (0 to 255).

* `static CBORObject FromByteArray(byte[] bytes)`<br>
 Generates a CBOR object from an array of 8-bit bytes; the byte array is
 copied to a new byte array in this process.

* `static CBORObject FromCBORArray(CBORObject[] array)`<br>
 Generates a CBOR object from an array of CBOR objects.

* `static CBORObject FromCBORObjectAndTag(CBORObject cborObj,
 int smallTag)`<br>
 Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag in addition to its existing tags (the new tag is made the
 outermost tag).

* `static CBORObject FromCBORObjectAndTag(CBORObject cborObj,
 com.upokecenter.numbers.EInteger bigintTag)`<br>
 Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag in addition to its existing tags (the new tag is made the
 outermost tag).

* `static CBORObject FromDouble(double value)`<br>
 Generates a CBOR object from a 64-bit floating-point number.

* `static CBORObject FromEDecimal(com.upokecenter.numbers.EDecimal bigValue)`<br>
 Generates a CBOR object from a decimal number.

* `static CBORObject FromEFloat(com.upokecenter.numbers.EFloat bigValue)`<br>
 Generates a CBOR object from an arbitrary-precision binary floating-point
 number.

* `static CBORObject FromEInteger(com.upokecenter.numbers.EInteger bigintValue)`<br>
 Generates a CBOR object from an arbitrary-precision integer.

* `static CBORObject FromERational(com.upokecenter.numbers.ERational bigValue)`<br>
 Generates a CBOR object from an arbitrary-precision rational number.

* `static CBORObject FromFloatingPointBits(long floatingBits,
 int byteCount)`<br>
 Generates a CBOR object from a floating-point number represented by its
 bits.

* `static CBORObject FromGuid(UUID value)`<br>
 Generates a CBOR object from a java.util.UUID.

* `static CBORObject FromInt16(short value)`<br>
 Generates a CBOR object from a 16-bit signed integer.

* `static CBORObject FromInt32(int value)`<br>
 Generates a CBOR object from a 32-bit signed integer.

* `static CBORObject FromInt64(long value)`<br>
 Generates a CBOR object from a 64-bit signed integer.

* `static CBORObject FromJSONBytes(byte[] bytes)`<br>
 Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format.

* `static CBORObject FromJSONBytes(byte[] bytes,
 int offset,
 int count)`<br>
 Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format.

* `static CBORObject FromJSONBytes(byte[] bytes,
 int offset,
 int count,
 JSONOptions jsonoptions)`<br>
 Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.

* `static CBORObject FromJSONBytes(byte[] bytes,
 JSONOptions jsonoptions)`<br>
 Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.

* `static CBORObject[] FromJSONSequenceBytes(byte[] bytes)`<br>
 Generates a list of CBOR objects from an array of bytes in JavaScript
 object Notation (JSON) text sequence format (RFC 7464).

* `static CBORObject[] FromJSONSequenceBytes(byte[] data,
 JSONOptions options)`<br>
 Generates a list of CBOR objects from an array of bytes in JavaScript
 object Notation (JSON) text sequence format (RFC 7464), using the specified
 options to control the decoding process.

* `static CBORObject FromJSONString(String str)`<br>
 Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format.

* `static CBORObject FromJSONString(String str,
 int offset,
 int count)`<br>
 Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format.

* `static CBORObject FromJSONString(String str,
 int offset,
 int count,
 JSONOptions jsonoptions)`<br>
 Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.

* `static CBORObject FromJSONString(String str,
 JSONOptions jsonoptions)`<br>
 Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.

* `static CBORObject FromMap(Iterable<Map.Entry<CBORObject,CBORObject>> keysAndValues)`<br>
 Creates a new CBOR map that stores its keys in an undefined order.

* `static CBORObject FromObject(boolean value)`<br>
 Deprecated.
Use FromBool instead.

* `static CBORObject FromObject(byte value)`<br>
 Deprecated.
Use FromByte instead.

* `static CBORObject FromObject(byte[] bytes)`<br>
 Deprecated.
Use FromByteArray instead.

* `static CBORObject FromObject(double value)`<br>
 Deprecated.
Use FromDouble instead.

* `static CBORObject FromObject(float value)`<br>
 Deprecated.
Use FromFloat instead.

* `static CBORObject FromObject(int value)`<br>
 Deprecated.
Use FromInt instead.

* `static CBORObject FromObject(int[] array)`<br>
 Generates a CBOR object from an array of 32-bit integers.

* `static CBORObject FromObject(long value)`<br>
 Deprecated.
Use FromInt64 instead.

* `static CBORObject FromObject(long[] array)`<br>
 Generates a CBOR object from an array of 64-bit integers.

* `static CBORObject FromObject(short value)`<br>
 Deprecated.
Use FromInt16 instead.

* `static CBORObject FromObject(CBORObject value)`<br>
 Deprecated.
Don't use a function and use Nullable Reference Types to guard against
 nulls.

* `static CBORObject FromObject(CBORObject[] array)`<br>
 Deprecated.
Use FromCBORArray instead.

* `static CBORObject FromObject(com.upokecenter.numbers.EDecimal bigValue)`<br>
 Deprecated.
Use FromEDecimal instead.

* `static CBORObject FromObject(com.upokecenter.numbers.EFloat bigValue)`<br>
 Deprecated.
Use FromEFloat instead.

* `static CBORObject FromObject(com.upokecenter.numbers.EInteger bigintValue)`<br>
 Deprecated.
Use FromEInteger instead.

* `static CBORObject FromObject(com.upokecenter.numbers.ERational bigValue)`<br>
 Deprecated.
Use FromERational instead.

* `static CBORObject FromObject(Object obj)`<br>
 Generates a CBORObject from an arbitrary object.

* `static CBORObject FromObject(Object obj,
 CBORTypeMapper mapper)`<br>
 Generates a CBORObject from an arbitrary object.

* `static CBORObject FromObject(Object obj,
 CBORTypeMapper mapper,
 PODOptions options)`<br>
 Generates a CBORObject from an arbitrary object, using the given options
 to control how certain objects are converted to CBOR objects.

* `static CBORObject FromObject(Object obj,
 PODOptions options)`<br>
 Generates a CBORObject from an arbitrary object.

* `static CBORObject FromObject(String strValue)`<br>
 Deprecated.
Use FromString instead.

* `static CBORObject FromObjectAndTag(Object valueObValue,
 int smallTag)`<br>
 Deprecated.
Use FromCBORObjectAndTag instead.

* `static CBORObject FromObjectAndTag(Object valueObValue,
 com.upokecenter.numbers.EInteger bigintTag)`<br>
 Deprecated.
Use FromCBORObjectAndTag instead.

* `static CBORObject FromOrderedMap(Iterable<Map.Entry<CBORObject,CBORObject>> keysAndValues)`<br>
 Creates a new CBOR map that ensures that keys are stored in order.

* `static CBORObject FromSimpleValue(int simpleValue)`<br>
 Creates a CBOR object from a simple value number.

* `static CBORObject FromSingle(float value)`<br>
 Generates a CBOR object from a 32-bit floating-point number.

* `static CBORObject FromString(String strValue)`<br>
 Generates a CBOR object from a text string.

* `CBORObject get(int index)`<br>
 Gets the value of a CBOR object by integer index in this array or by integer
 key in this map.

* `CBORObject get(CBORObject key)`<br>
 Gets the value of a CBOR object by integer index in this array or by CBOR
 object key in this map.

* `CBORObject get(String key)`<br>
 Gets the value of a CBOR object in this map, using a string as the key.

* `com.upokecenter.numbers.EInteger[] GetAllTags()`<br>
 Gets a list of all tags, from outermost to innermost.

* `byte[] GetByteString()`<br>
 Gets the backing byte array used in this CBOR object, if this object is a
 byte string, without copying the data to a new byte array.

* `final Collection<Map.Entry<CBORObject,CBORObject>> getEntries()`<br>
 Gets a collection of the key/value pairs stored in this CBOR object, if it's
 a map.

* `final Collection<CBORObject> getKeys()`<br>
 Gets a collection of the keys of this CBOR object.

* `final com.upokecenter.numbers.EInteger getMostInnerTag()`<br>
 Gets the last defined tag for this CBOR data item, or -1 if the item is
 untagged.

* `final com.upokecenter.numbers.EInteger getMostOuterTag()`<br>
 Gets the outermost tag for this CBOR data item, or -1 if the item is
 untagged.

* `CBORObject GetOrDefault(int key,
 CBORObject defaultValue)`<br>
 Gets the value of a CBOR object by integer index in this array, or a default
 value if that value is not found.

* `CBORObject GetOrDefault(CBORObject cborkey,
 CBORObject defaultValue)`<br>
 Gets the value of a CBOR object by integer index in this array or by CBOR
 object key in this map, or a default value if that value is not found.

* `CBORObject GetOrDefault(String key,
 CBORObject defaultValue)`<br>
 Gets the value of a CBOR object by string key in a map, or a default value
 if that value is not found.

* `final int getSimpleValue()`<br>
 Gets the simple value ID of this CBOR object, or -1 if the object is not a
 simple value.

* `final int getTagCount()`<br>
 Gets the number of tags this object has.

* `final CBORType getType()`<br>
 Gets the general data type of this CBOR object.

* `final Collection<CBORObject> getValues()`<br>
 Gets a collection of the values of this CBOR object, if it's a map or an
 array.

* `int hashCode()`<br>
 Calculates the hash code of this object.

* `boolean HasMostInnerTag(int tagValue)`<br>
 Returns whether this object has an innermost tag and that tag is of the
 given number.

* `boolean HasMostInnerTag(com.upokecenter.numbers.EInteger bigTagValue)`<br>
 Returns whether this object has an innermost tag and that tag is of the
 given number, expressed as an arbitrary-precision number.

* `boolean HasMostOuterTag(int tagValue)`<br>
 Returns whether this object has an outermost tag and that tag is of the
 given number.

* `boolean HasMostOuterTag(com.upokecenter.numbers.EInteger bigTagValue)`<br>
 Returns whether this object has an outermost tag and that tag is of the
 given number.

* `boolean HasOneTag()`<br>
 Returns whether this object has only one tag.

* `boolean HasOneTag(int tagValue)`<br>
 Returns whether this object has only one tag and that tag is the given
 number.

* `boolean HasOneTag(com.upokecenter.numbers.EInteger bigTagValue)`<br>
 Returns whether this object has only one tag and that tag is the given
 number, expressed as an arbitrary-precision integer.

* `boolean HasTag(int tagValue)`<br>
 Returns whether this object has a tag of the given number.

* `boolean HasTag(com.upokecenter.numbers.EInteger bigTagValue)`<br>
 Returns whether this object has a tag of the given number.

* `CBORObject Insert(int index,
 CBORObject cborObj)`<br>
 Inserts a CBORObject at the specified position in this CBOR array.

* `CBORObject Insert(int index,
 Object valueOb)`<br>
 Deprecated.
Use the CBORObject overload instead.

* `final boolean isFalse()`<br>
 Gets a value indicating whether this value is a CBOR false value, whether
 tagged or not.

* `final boolean isNull()`<br>
 Gets a value indicating whether this CBOR object is a CBOR null value,
 whether tagged or not.

* `final boolean isNumber()`<br>
 Gets a value indicating whether this CBOR object stores a number (including
 infinity or a not-a-number or NaN value).

* `final boolean isTagged()`<br>
 Gets a value indicating whether this data item has at least one tag.

* `final boolean isTrue()`<br>
 Gets a value indicating whether this value is a CBOR true value, whether
 tagged or not.

* `final boolean isUndefined()`<br>
 Gets a value indicating whether this value is a CBOR undefined value,
 whether tagged or not.

* `static CBORObject NewArray()`<br>
 Creates a new empty CBOR array.

* `static CBORObject NewMap()`<br>
 Creates a new empty CBOR map that stores its keys in an undefined order.

* `static CBORObject NewOrderedMap()`<br>
 Creates a new empty CBOR map that ensures that keys are stored in the order
 in which they are first inserted.

* `static CBORObject Read(InputStream stream)`<br>
 Reads an object in CBOR format from a data stream.

* `static CBORObject Read(InputStream stream,
 CBOREncodeOptions options)`<br>
 Reads an object in CBOR format from a data stream, using the specified
 options to control the decoding process.

* `static CBORObject ReadJSON(InputStream stream)`<br>
 Generates a CBOR object from a data stream in JavaScript object Notation
 (JSON) format.

* `static CBORObject ReadJSON(InputStream stream,
 JSONOptions jsonoptions)`<br>
 Generates a CBOR object from a data stream in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.

* `static CBORObject[] ReadJSONSequence(InputStream stream)`<br>
 Generates a list of CBOR objects from a data stream in JavaScript object
 Notation (JSON) text sequence format (RFC 7464).

* `static CBORObject[] ReadJSONSequence(InputStream stream,
 JSONOptions jsonoptions)`<br>
 Generates a list of CBOR objects from a data stream in JavaScript object
 Notation (JSON) text sequence format (RFC 7464).

* `static CBORObject[] ReadSequence(InputStream stream)`<br>
 Reads a sequence of objects in CBOR format from a data stream.

* `static CBORObject[] ReadSequence(InputStream stream,
 CBOREncodeOptions options)`<br>
 Reads a sequence of objects in CBOR format from a data stream.

* `boolean Remove(CBORObject obj)`<br>
 If this object is an array, removes the first instance of the specified item
 from the array.

* `boolean Remove(Object obj)`<br>
 If this object is an array, removes the first instance of the specified item
 (once converted to a CBOR object) from the array.

* `boolean RemoveAt(int index)`<br>
 Removes the item at the given index of this CBOR array.

* `void set(int index,
 CBORObject value)`<br>
 Sets the value of a CBOR object by integer index in this array or by integer
 key in this map.

* `void set(CBORObject key,
 CBORObject value)`<br>
 Sets the value of a CBOR object by integer index in this array or by CBOR
 object key in this map.

* `void set(String key,
 CBORObject value)`<br>
 Sets the value of a CBOR object in this map, using a string as the key.

* `CBORObject Set(int key,
 CBORObject mapValue)`<br>
 Sets the value of a CBORObject of type Array at the given index to the given
 value.

* `CBORObject Set(CBORObject mapKey,
 CBORObject mapValue)`<br>
 Maps an object to a key in this CBOR map, or adds the value if the key
 doesn't exist.

* `CBORObject Set(Object key,
 Object valueOb)`<br>
 Deprecated.
Use the CBORObject overload instead.

* `final int size()`<br>
 Gets the number of keys in this map, or the number of items in this array,
 or 0 if this item is neither an array nor a map.

* `byte[] ToJSONBytes()`<br>
 Converts this object to a byte array in JavaScript object Notation (JSON)
 format.

* `byte[] ToJSONBytes(JSONOptions jsonoptions)`<br>
 Converts this object to a byte array in JavaScript object Notation (JSON)
 format.

* `String ToJSONString()`<br>
 Converts this object to a text string in JavaScript object Notation
 (JSON) format.

* `String ToJSONString(JSONOptions options)`<br>
 Converts this object to a text string in JavaScript object Notation
 (JSON) format, using the specified options to control the encoding process.

* `<T> T ToObject(Type t)`<br>
 Converts this CBOR object to an object of an arbitrary type.

* `<T> T ToObject(Type t,
 CBORTypeMapper mapper)`<br>
 Converts this CBOR object to an object of an arbitrary type.

* `<T> T ToObject(Type t,
 CBORTypeMapper mapper,
 PODOptions options)`<br>
 Converts this CBOR object to an object of an arbitrary type.

* `<T> T ToObject(Type t,
 PODOptions options)`<br>
 Converts this CBOR object to an object of an arbitrary type.

* `String toString()`<br>
 Returns this CBOR object in a text form intended to be read by humans.

* `CBORObject Untag()`<br>
 Gets an object with the same value as this one but without the tags it has,
 if any.

* `CBORObject UntagOne()`<br>
 Gets an object with the same value as this one but without this object's
 outermost tag, if any.

* `CBORObject WithTag(int smallTag)`<br>
 Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag in addition to its existing tags (the new tag is made the
 outermost tag).

* `CBORObject WithTag(com.upokecenter.numbers.EInteger bigintTag)`<br>
 Generates a CBOR object from this one, but gives the resulting object a tag
 in addition to its existing tags (the new tag is made the outermost tag).

* `static void Write(boolean value,
 OutputStream stream)`<br>
 Writes a Boolean value in CBOR format to a data stream.

* `static void Write(byte value,
 OutputStream stream)`<br>
 Writes a byte (0 to 255) in CBOR format to a data stream.

* `static void Write(double value,
 OutputStream stream)`<br>
 Writes a 64-bit floating-point number in CBOR format to a data stream.

* `static void Write(float value,
 OutputStream stream)`<br>
 Writes a 32-bit floating-point number in CBOR format to a data stream.

* `static void Write(int value,
 OutputStream stream)`<br>
 Writes a 32-bit signed integer in CBOR format to a data stream.

* `static void Write(long value,
 OutputStream stream)`<br>
 Writes a 64-bit signed integer in CBOR format to a data stream.

* `static void Write(short value,
 OutputStream stream)`<br>
 Writes a 16-bit signed integer in CBOR format to a data stream.

* `static void Write(CBORObject value,
 OutputStream stream)`<br>
 Writes a CBOR object to a CBOR data stream.

* `static void Write(com.upokecenter.numbers.EDecimal bignum,
 OutputStream stream)`<br>
 Writes a decimal floating-point number in CBOR format to a data stream, as
 though it were converted to a CBOR object via
 CBORObject.FromEDecimal(EDecimal) and then written out.

* `static void Write(com.upokecenter.numbers.EFloat bignum,
 OutputStream stream)`<br>
 Writes a binary floating-point number in CBOR format to a data stream, as
 though it were converted to a CBOR object via CBORObject.FromEFloat(EFloat)
 and then written out.

* `static void Write(com.upokecenter.numbers.EInteger bigint,
 OutputStream stream)`<br>
 Writes a arbitrary-precision integer in CBOR format to a data stream.

* `static void Write(com.upokecenter.numbers.ERational rational,
 OutputStream stream)`<br>
 Writes a rational number in CBOR format to a data stream, as though it were
 converted to a CBOR object via CBORObject.FromERational(ERational) and then
 written out.

* `static void Write(Object objValue,
 OutputStream stream)`<br>
 Writes a CBOR object to a CBOR data stream.

* `static void Write(Object objValue,
 OutputStream output,
 CBOREncodeOptions options)`<br>
 Writes an arbitrary object to a CBOR data stream, using the specified
 options for controlling how the object is encoded to CBOR data format.

* `static void Write(String str,
 OutputStream stream)`<br>
 Writes a text string in CBOR format to a data stream.

* `static void Write(String str,
 OutputStream stream,
 CBOREncodeOptions options)`<br>
 Writes a text string in CBOR format to a data stream, using the given
 options to control the encoding process.

* `static int WriteFloatingPointBits(OutputStream outputStream,
 long floatingBits,
 int byteCount)`<br>
 Writes the bits of a floating-point number in CBOR format to a data stream.

* `static int WriteFloatingPointBits(OutputStream outputStream,
 long floatingBits,
 int byteCount,
 boolean shortestForm)`<br>
 Writes the bits of a floating-point number in CBOR format to a data stream.

* `static int WriteFloatingPointValue(OutputStream outputStream,
 double doubleVal,
 int byteCount)`<br>
 Writes a 64-bit binary floating-point number in CBOR format to a data
 stream, either in its 64-bit form, or its rounded 32-bit or 16-bit
 equivalent.

* `static int WriteFloatingPointValue(OutputStream outputStream,
 float singleVal,
 int byteCount)`<br>
 Writes a 32-bit binary floating-point number in CBOR format to a data
 stream, either in its 64- or 32-bit form, or its rounded 16-bit equivalent.

* `static void WriteJSON(Object obj,
 OutputStream outputStream)`<br>
 Converts an arbitrary object to a text string in JavaScript object
 Notation (JSON) format, as in the ToJSONString method, and writes that
 string to a data stream in UTF-8.

* `void WriteJSONTo(OutputStream outputStream)`<br>
 Converts this object to a text string in JavaScript object Notation
 (JSON) format, as in the ToJSONString method, and writes that string to a
 data stream in UTF-8.

* `void WriteJSONTo(OutputStream outputStream,
 JSONOptions options)`<br>
 Converts this object to a text string in JavaScript object Notation
 (JSON) format, as in the ToJSONString method, and writes that string to a
 data stream in UTF-8, using the given JSON options to control the encoding
 process.

* `void WriteTo(OutputStream stream)`<br>
 Writes this CBOR object to a data stream.

* `void WriteTo(OutputStream stream,
 CBOREncodeOptions options)`<br>
 Writes this CBOR object to a data stream, using the specified options for
 encoding the data to CBOR format.

* `static int WriteValue(OutputStream outputStream,
 int majorType,
 int value)`<br>
 Writes a CBOR major type number and an integer 0 or greater associated
 with it to a data stream, where that integer is passed to this method as a
 32-bit signed integer.

* `static int WriteValue(OutputStream outputStream,
 int majorType,
 long value)`<br>
 Writes a CBOR major type number and an integer 0 or greater associated
 with it to a data stream, where that integer is passed to this method as a
 64-bit signed integer.

* `static int WriteValue(OutputStream outputStream,
 int majorType,
 com.upokecenter.numbers.EInteger bigintValue)`<br>
 Writes a CBOR major type number and an integer 0 or greater associated
 with it to a data stream, where that integer is passed to this method as an
 arbitrary-precision integer.

## Field Details

### False
    public static final CBORObject False
Represents the value false.
### NaN
    public static final CBORObject NaN
A not-a-number value.
### NegativeInfinity
    public static final CBORObject NegativeInfinity
The value negative infinity.
### Null
    public static final CBORObject Null
Represents the value null.
### PositiveInfinity
    public static final CBORObject PositiveInfinity
The value positive infinity.
### True
    public static final CBORObject True
Represents the value true.
### Undefined
    public static final CBORObject Undefined
Represents the value undefined.
### Zero
    public static final CBORObject Zero
Gets a CBOR object for the number zero.
## Method Details

### size
    public final int size()
Gets the number of keys in this map, or the number of items in this array,
 or 0 if this item is neither an array nor a map.

**Returns:**

* The number of keys in this map, or the number of items in this
 array, or 0 if this item is neither an array nor a map.

### getMostInnerTag
    public final com.upokecenter.numbers.EInteger getMostInnerTag()
Gets the last defined tag for this CBOR data item, or -1 if the item is
 untagged.

**Returns:**

* The last defined tag for this CBOR data item, or -1 if the item is
 untagged.

### isFalse
    public final boolean isFalse()
Gets a value indicating whether this value is a CBOR false value, whether
 tagged or not.

**Returns:**

* <code>true</code> if this value is a CBOR false value; otherwise, <code>
 false</code>.

### isNull
    public final boolean isNull()
Gets a value indicating whether this CBOR object is a CBOR null value,
 whether tagged or not.

**Returns:**

* <code>true</code> if this value is a CBOR null value; otherwise, <code>
 false</code>.

### isTagged
    public final boolean isTagged()
Gets a value indicating whether this data item has at least one tag.

**Returns:**

* <code>true</code> if this data item has at least one tag; otherwise,
 <code>false</code>.

### isTrue
    public final boolean isTrue()
Gets a value indicating whether this value is a CBOR true value, whether
 tagged or not.

**Returns:**

* <code>true</code> if this value is a CBOR true value; otherwise, <code>
 false</code>.

### isUndefined
    public final boolean isUndefined()
Gets a value indicating whether this value is a CBOR undefined value,
 whether tagged or not.

**Returns:**

* <code>true</code> if this value is a CBOR undefined value; otherwise,
 <code>false</code>.

### getKeys
    public final Collection<CBORObject> getKeys()
Gets a collection of the keys of this CBOR object. In general, the order in
 which those keys occur is undefined unless this is a map created using the
 NewOrderedMap method.

**Returns:**

* A read-only collection of the keys of this CBOR object. To avoid
 potential problems, the calling code should not modify the CBOR map while
 iterating over the returned collection.

**Throws:**

* <code>IllegalStateException</code> - This object is not a map.

### getMostOuterTag
    public final com.upokecenter.numbers.EInteger getMostOuterTag()
Gets the outermost tag for this CBOR data item, or -1 if the item is
 untagged.

**Returns:**

* The outermost tag for this CBOR data item, or -1 if the item is
 untagged.

### getSimpleValue
    public final int getSimpleValue()
Gets the simple value ID of this CBOR object, or -1 if the object is not a
 simple value. In this method, objects with a CBOR type of Boolean or
 SimpleValue are simple values, whether they are tagged or not.

**Returns:**

* The simple value ID of this object if it's a simple value, or -1 if
 this object is not a simple value.

### isNumber
    public final boolean isNumber()
Gets a value indicating whether this CBOR object stores a number (including
 infinity or a not-a-number or NaN value). Currently, this is true if this
 item is untagged and has a CBORType of Integer or FloatingPoint, or if this
 item has only one tag and that tag is 2, 3, 4, 5, 30, 264, 265, 268, 269, or
 270 with the right data type.

**Returns:**

* A value indicating whether this CBOR object stores a number.

### getType
    public final CBORType getType()
Gets the general data type of this CBOR object. This method disregards the
 tags this object has, if any.

**Returns:**

* The general data type of this CBOR object.

### getEntries
    public final Collection<Map.Entry<CBORObject,CBORObject>> getEntries()
Gets a collection of the key/value pairs stored in this CBOR object, if it's
 a map. Returns one entry for each key/value pair in the map. In general, the
 order in which those entries occur is undefined unless this is a map created
 using the NewOrderedMap method.

**Returns:**

* A collection of the key/value pairs stored in this CBOR map, as a
 read-only view of those pairs. To avoid potential problems, the calling code
 should not modify the CBOR map while iterating over the returned collection.

**Throws:**

* <code>IllegalStateException</code> - This object is not a map.

### getValues
    public final Collection<CBORObject> getValues()
Gets a collection of the values of this CBOR object, if it's a map or an
 array. If this object is a map, returns one value for each key in the map;
 in general, the order in which those keys occur is undefined unless this is
 a map created using the NewOrderedMap method. If this is an array, returns
 all the values of the array in the order they are listed. (This method can't
 be used to get the bytes in a CBOR byte string; for that, use the
 GetByteString method instead.).

**Returns:**

* A read-only collection of the values of this CBOR map or array. To
 avoid potential problems, the calling code should not modify the CBOR map or
 array while iterating over the returned collection.

**Throws:**

* <code>IllegalStateException</code> - This object is not a map or an array.

### get
    public CBORObject get(int index)
Gets the value of a CBOR object by integer index in this array or by integer
 key in this map.

**Parameters:**

* <code>index</code> - Index starting at 0 of the element, or the integer key to this
 map. (If this is a map, the given index can be any 32-bit signed integer,
 even a negative one.).

**Returns:**

* The CBOR object referred to by index or key in this array or map. If
 this is a CBOR map, returns <code>null</code> (not <code>CBORObject.Null</code>) if
 an item with the given key doesn't exist (but this behavior may change to
 throwing an exception in version 5.0 or later).

**Throws:**

* <code>IllegalStateException</code> - This object is not an array or map.

* <code>IllegalArgumentException</code> - This object is an array and the index is less than
 0 or at least the size of the array.

* <code>NullPointerException</code> - The parameter "value" is null (as opposed to
 CBORObject.Null).

### set
    public void set(int index, CBORObject value)
Sets the value of a CBOR object by integer index in this array or by integer
 key in this map.

**Parameters:**

* <code>index</code> - Index starting at 0 of the element, or the integer key to this
 map. (If this is a map, the given index can be any 32-bit signed integer,
 even a negative one.).

**Throws:**

* <code>IllegalStateException</code> - This object is not an array or map.

* <code>IllegalArgumentException</code> - This object is an array and the index is less than
 0 or at least the size of the array.

* <code>NullPointerException</code> - The parameter "value" is null (as opposed to
 CBORObject.Null).

### GetOrDefault
    public CBORObject GetOrDefault(CBORObject cborkey, CBORObject defaultValue)
Gets the value of a CBOR object by integer index in this array or by CBOR
 object key in this map, or a default value if that value is not found.

**Parameters:**

* <code>cborkey</code> - An arbitrary CBORObject. If this is a CBOR map, this
 parameter is converted to a CBOR object serving as the key to the map or
 index to the array, and can be null. If this is a CBOR array, the key must
 be an integer 0 or greater and less than the size of the array, and may be
 any object convertible to a CBOR integer.

* <code>defaultValue</code> - A value to return if an item with the given key doesn't
 exist, or if the CBOR object is an array and the key is not an integer 0 or
 greater and less than the size of the array.

**Returns:**

* The CBOR object referred to by index or key in this array or map. If
 this is a CBOR map, returns <code>null</code> (not <code>CBORObject.Null</code>) if
 an item with the given key doesn't exist.

### GetOrDefault
    public CBORObject GetOrDefault(int key, CBORObject defaultValue)
Gets the value of a CBOR object by integer index in this array, or a default
 value if that value is not found.

**Parameters:**

* <code>key</code> - An arbitrary object. If this is a CBOR map, this parameter is
 converted to a CBOR object serving as the key to the map or index to the
 array, and can be null. If this is a CBOR array, the key must be an integer
 0 or greater and less than the size of the array, and may be any object
 convertible to a CBOR integer.

* <code>defaultValue</code> - A value to return if an item with the given key doesn't
 exist, or if the CBOR object is an array and the key is not an integer 0 or
 greater and less than the size of the array.

**Returns:**

* The CBOR object referred to by index or key in this array or map. If
 this is a CBOR map, returns <code>null</code> (not <code>CBORObject.Null</code>) if
 an item with the given key doesn't exist.

### GetOrDefault
    public CBORObject GetOrDefault(String key, CBORObject defaultValue)
Gets the value of a CBOR object by string key in a map, or a default value
 if that value is not found.

**Parameters:**

* <code>key</code> - An arbitrary string. If this is a CBOR map, this parameter is
 converted to a CBOR object serving as the key to the map or index to the
 array, and can be null. If this is a CBOR array, defaultValue is returned.

* <code>defaultValue</code> - A value to return if an item with the given key doesn't
 exist, or if the CBOR object is an array.

**Returns:**

* The CBOR object referred to by index or key in this array or map. If
 this is a CBOR map, returns <code>null</code> (not <code>CBORObject.Null</code>) if
 an item with the given key doesn't exist.

### get
    public CBORObject get(CBORObject key)
Gets the value of a CBOR object by integer index in this array or by CBOR
 object key in this map.

**Parameters:**

* <code>key</code> - A CBOR object serving as the key to the map or index to the
 array. If this is a CBOR array, the key must be an integer 0 or greater and
 less than the size of the array.

**Returns:**

* The CBOR object referred to by index or key in this array or map. If
 this is a CBOR map, returns <code>null</code> (not <code>CBORObject.Null</code>) if
 an item with the given key doesn't exist.

**Throws:**

* <code>NullPointerException</code> - The key is null (as opposed to
 CBORObject.Null); or the set method is called and the value is null.

* <code>IllegalArgumentException</code> - This CBOR object is an array and the key is not an
 integer 0 or greater and less than the size of the array.

* <code>IllegalStateException</code> - This object is not a map or an array.

### set
    public void set(CBORObject key, CBORObject value)
Sets the value of a CBOR object by integer index in this array or by CBOR
 object key in this map.

**Parameters:**

* <code>key</code> - A CBOR object serving as the key to the map or index to the
 array. If this is a CBOR array, the key must be an integer 0 or greater and
 less than the size of the array.

**Throws:**

* <code>NullPointerException</code> - The key is null (as opposed to
 CBORObject.Null); or the set method is called and the value is null.

* <code>IllegalArgumentException</code> - This CBOR object is an array and the key is not an
 integer 0 or greater and less than the size of the array.

* <code>IllegalStateException</code> - This object is not a map or an array.

### get
    public CBORObject get(String key)
Gets the value of a CBOR object in this map, using a string as the key.

**Parameters:**

* <code>key</code> - A key that points to the desired value.

**Returns:**

* The CBOR object referred to by key in this map. Returns <code>null</code>
 if an item with the given key doesn't exist.

**Throws:**

* <code>NullPointerException</code> - The key is null.

* <code>IllegalStateException</code> - This object is not a map.

### set
    public void set(String key, CBORObject value)
Sets the value of a CBOR object in this map, using a string as the key.

**Parameters:**

* <code>key</code> - A key that points to the desired value.

**Throws:**

* <code>NullPointerException</code> - The key is null.

* <code>IllegalStateException</code> - This object is not a map.

### DecodeFromBytes
    public static CBORObject DecodeFromBytes(byte[] data)
Generates a CBOR object from an array of CBOR-encoded bytes.

**Parameters:**

* <code>data</code> - A byte array in which a single CBOR object is encoded.

**Returns:**

* A CBOR object decoded from the given byte array.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where not all of the byte array
 represents a CBOR object. This exception is also thrown if the parameter
 <code>data</code> is empty.

* <code>NullPointerException</code> - The parameter <code>data</code> is null.

### DecodeSequenceFromBytes
    public static CBORObject[] DecodeSequenceFromBytes(byte[] data)
Generates a sequence of CBOR objects from an array of CBOR-encoded bytes.

**Parameters:**

* <code>data</code> - A byte array in which any number of CBOR objects (including
 zero) are encoded, one after the other. Can be empty, but cannot be null.

**Returns:**

* An array of CBOR objects decoded from the given byte array. Returns
 an empty array if <code>data</code> is empty.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where the last CBOR object in the data
 was read only partly.

* <code>NullPointerException</code> - The parameter <code>data</code> is null.

### DecodeSequenceFromBytes
    public static CBORObject[] DecodeSequenceFromBytes(byte[] data, CBOREncodeOptions options)
Generates a sequence of CBOR objects from an array of CBOR-encoded bytes.

**Parameters:**

* <code>data</code> - A byte array in which any number of CBOR objects (including
 zero) are encoded, one after the other. Can be empty, but cannot be null.

* <code>options</code> - Specifies options to control how the CBOR object is decoded.
 See <code>CBOREncodeOptions</code> for more information. In
 this method, the AllowEmpty property is treated as always set regardless of
 that value as specified in this parameter.

**Returns:**

* An array of CBOR objects decoded from the given byte array. Returns
 an empty array if <code>data</code> is empty.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where the last CBOR object in the data
 was read only partly.

* <code>NullPointerException</code> - The parameter <code>data</code> is null, or the
 parameter <code>options</code> is null.

### FromJSONSequenceBytes
    public static CBORObject[] FromJSONSequenceBytes(byte[] bytes)
<p>Generates a list of CBOR objects from an array of bytes in JavaScript
 object Notation (JSON) text sequence format (RFC 7464). The byte array must
 be in UTF-8 encoding and may not begin with a byte-order mark (U+FEFF).</p>
 <p>Generally, each JSON text in a JSON text sequence is written as follows:
 Write a record separator byte (0x1e), then write the JSON text in UTF-8
 (without a byte order mark, U+FEFF), then write the line feed byte (0x0a).
 RFC 7464, however, uses a more liberal syntax for parsing JSON text
 sequences.</p>

**Parameters:**

* <code>bytes</code> - A byte array in which a JSON text sequence is encoded.

**Returns:**

* A list of CBOR objects read from the JSON sequence. Objects that
 could not be parsed are replaced with <code>null</code> (as opposed to <code>
 CBORObject.Null</code>) in the given list.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bytes</code> is null.

* <code>CBORException</code> - The byte array is not empty and
 does not begin with a record separator byte (0x1e), or an I/O error
 occurred.

### ToJSONBytes
    public byte[] ToJSONBytes()
Converts this object to a byte array in JavaScript object Notation (JSON)
 format. The JSON text will be written out in UTF-8 encoding, without a byte
 order mark, to the byte array. See the overload to ToJSONString taking a
 JSONOptions argument for further information.

**Returns:**

* A byte array containing the converted in JSON format.

### ToJSONBytes
    public byte[] ToJSONBytes(JSONOptions jsonoptions)
Converts this object to a byte array in JavaScript object Notation (JSON)
 format. The JSON text will be written out in UTF-8 encoding, without a byte
 order mark, to the byte array. See the overload to ToJSONString taking a
 JSONOptions argument for further information.

**Parameters:**

* <code>jsonoptions</code> - Specifies options to control writing the CBOR object to
 JSON.

**Returns:**

* A byte array containing the converted object in JSON format.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>jsonoptions</code> is null.

### FromJSONSequenceBytes
    public static CBORObject[] FromJSONSequenceBytes(byte[] data, JSONOptions options)
<p>Generates a list of CBOR objects from an array of bytes in JavaScript
 object Notation (JSON) text sequence format (RFC 7464), using the specified
 options to control the decoding process. The byte array must be in UTF-8
 encoding and may not begin with a byte-order mark (U+FEFF).</p>
 <p>Generally, each JSON text in a JSON text sequence is written as follows:
 Write a record separator byte (0x1e), then write the JSON text in UTF-8
 (without a byte order mark, U+FEFF), then write the line feed byte (0x0a).
 RFC 7464, however, uses a more liberal syntax for parsing JSON text
 sequences.</p>

**Parameters:**

* <code>data</code> - A byte array in which a JSON text sequence is encoded.

* <code>options</code> - Specifies options to control the JSON decoding process.

**Returns:**

* A list of CBOR objects read from the JSON sequence. Objects that
 could not be parsed are replaced with <code>null</code> (as opposed to <code>
 CBORObject.Null</code>) in the given list.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>data</code> is null.

* <code>CBORException</code> - The byte array is not empty and
 does not begin with a record separator byte (0x1e), or an I/O error
 occurred.

### DecodeFromBytes
    public static CBORObject DecodeFromBytes(byte[] data, CBOREncodeOptions options)
<p>Generates a CBOR object from an array of CBOR-encoded bytes, using the
 given <code>CBOREncodeOptions</code> object to control the decoding
 process.</p><p>The following example (originally written in C# for the.NET
 version) implements a method that decodes a text string from a CBOR byte
 array. It's successful only if the CBOR object contains an untagged text
 string.</p> <pre>private static string DecodeTextString(byte[] bytes) { if
 (bytes == null) { throw new NullPointerException("mapObj");} if
 (bytes.length == 0 || bytes[0]&lt;0x60 || bytes[0]&gt;0x7f) {throw new
 CBORException();} return CBORObject.DecodeFromBytes(bytes,
 CBOREncodeOptions.Default).AsString(); }</pre>.

**Parameters:**

* <code>data</code> - A byte array in which a single CBOR object is encoded.

* <code>options</code> - Specifies options to control how the CBOR object is decoded.
 See <code>CBOREncodeOptions</code> for more information.

**Returns:**

* A CBOR object decoded from the given byte array. Returns null (as
 opposed to CBORObject.Null) if <code>data</code> is empty and the AllowEmpty
 property is set on the given options object.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where not all of the byte array
 represents a CBOR object. This exception is also thrown if the parameter
 <code>data</code> is empty unless the AllowEmpty property is set on the given
 options object.

* <code>NullPointerException</code> - The parameter <code>data</code> is null, or the
 parameter <code>options</code> is null.

### FromJSONString
    public static CBORObject FromJSONString(String str, int offset, int count)
<p>Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a CBORException
 is thrown. This is a change in version 4.0.</p> <p>Note that if a CBOR
 object is converted to JSON with <code>ToJSONString</code>, then the JSON is
 converted back to CBOR with this method, the new CBOR object will not
 necessarily be the same as the old CBOR object, especially if the old CBOR
 object uses data types not supported in JSON, such as integers in map
 keys.</p>

**Parameters:**

* <code>str</code> - A text string in JSON format. The entire string must contain a
 single JSON object and not multiple objects. The string may not begin with a
 byte-order mark (U+FEFF).

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>str</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 str</code> (but not more than <code>str</code> 's length).

**Returns:**

* A CBOR object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>CBORException</code> - The string is not in JSON format.

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
 than 0 or greater than <code>str</code> 's length, or <code>str</code> 's length minus
 <code>offset</code> is less than <code>count</code>.

### FromJSONString
    public static CBORObject FromJSONString(String str, JSONOptions jsonoptions)
<p>Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.
 </p> <p>Note that if a CBOR object is converted to JSON with <code>
 ToJSONString</code>, then the JSON is converted back to CBOR with this method, the
 new CBOR object will not necessarily be the same as the old CBOR object,
 especially if the old CBOR object uses data types not supported in JSON,
 such as integers in map keys.</p>

**Parameters:**

* <code>str</code> - A text string in JSON format. The entire string must contain a
 single JSON object and not multiple objects. The string may not begin with a
 byte-order mark (U+FEFF).

* <code>jsonoptions</code> - Specifies options to control the JSON decoding process.

**Returns:**

* A CBOR object containing the JSON data decoded.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> or <code>
 jsonoptions</code> is null.

* <code>CBORException</code> - The string is not in JSON format.

### FromJSONString
    public static CBORObject FromJSONString(String str)
<p>Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a CBORException
 is thrown. This is a change in version 4.0.</p> <p>Note that if a CBOR
 object is converted to JSON with <code>ToJSONString</code>, then the JSON is
 converted back to CBOR with this method, the new CBOR object will not
 necessarily be the same as the old CBOR object, especially if the old CBOR
 object uses data types not supported in JSON, such as integers in map
 keys.</p>

**Parameters:**

* <code>str</code> - A text string in JSON format. The entire string must contain a
 single JSON object and not multiple objects. The string may not begin with a
 byte-order mark (U+FEFF).

**Returns:**

* A CBOR object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>CBORException</code> - The string is not in JSON format.

### FromJSONString
    public static CBORObject FromJSONString(String str, int offset, int count, JSONOptions jsonoptions)
<p>Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.
 </p> <p>Note that if a CBOR object is converted to JSON with <code>
 ToJSONString</code>, then the JSON is converted back to CBOR with this method, the
 new CBOR object will not necessarily be the same as the old CBOR object,
 especially if the old CBOR object uses data types not supported in JSON,
 such as integers in map keys.</p>

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>str</code> begins.

* <code>count</code> - The length, in code units, of the desired portion of <code>
 str</code> (but not more than <code>str</code> 's length).

* <code>jsonoptions</code> - The parameter <code>jsonoptions</code> is a Cbor.JSONOptions
 object.

**Returns:**

* A CBOR object containing the JSON data decoded.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> or <code>
 jsonoptions</code> is null.

* <code>CBORException</code> - The string is not in JSON format.

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
 than 0 or greater than <code>str</code> 's length, or <code>str</code> 's length minus
 <code>offset</code> is less than <code>count</code>.

### ToObject
    public <T> T ToObject(Type t)
<p>Converts this CBOR object to an object of an arbitrary type. See the
 documentation for the overload of this method taking a CBORTypeMapper
 parameter for more information. This method doesn't use a CBORTypeMapper
 parameter to restrict which data types are eligible for Plain-Old-Data
 serialization.</p><p>Java offers no easy way to express a generic type, at
 least none as easy as C#'s <code>typeof</code> operator. The following example,
 written in Java, is a way to specify that the return value will be an
 ArrayList of string objects.</p> <pre>Type arrayListString = new
 ParameterizedType() { public Type[] getActualTypeArguments() { /* Contains
 one type parameter, string*/ return new Type[] { string.class }; } public
 Type getRawType() { /* Raw type is ArrayList */ return ArrayList.class; }
 public Type getOwnerType() { return null; } }; ArrayList&lt;string&gt; array
 = (ArrayList&lt;string&gt;) cborArray.ToObject(arrayListString);</pre> <p>By
 comparison, the C# version is much shorter.</p> <pre>List&lt;string&gt;
 array = (List&lt;string&gt;)cborArray.ToObject(
 typeof(List&lt;string&gt;));</pre>.

**Parameters:**

* <code>t</code> - <p>The type, class, or interface that this method's return value
 will belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security reasons, an application should not base this
 parameter on user input or other externally supplied data. Whenever
 possible, this parameter should be either a type specially handled by this
 method (such as <code>int</code> or <code>string</code>) or a plain-old-data type
 (POCO or POJO type) within the control of the application. If the
 plain-old-data type references other data types, those types should likewise
 meet either criterion given earlier.</p>

**Returns:**

* The converted object.

**Throws:**

* <code>CBORException</code> - The given type <code>t</code> , or
 this object's CBOR type, is not supported, or the given object's nesting is
 too deep, or another error occurred when serializing the object.

* <code>NullPointerException</code> - The parameter <code>t</code> is null.

### ToObject
    public <T> T ToObject(Type t, CBORTypeMapper mapper)
Converts this CBOR object to an object of an arbitrary type. See the
 documentation for the overload of this method taking a CBORTypeMapper and
 PODOptions parameters parameters for more information.

**Parameters:**

* <code>t</code> - <p>The type, class, or interface that this method's return value
 will belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security reasons, an application should not base this
 parameter on user input or other externally supplied data. Whenever
 possible, this parameter should be either a type specially handled by this
 method (such as <code>int</code> or <code>string</code>) or a plain-old-data type
 (POCO or POJO type) within the control of the application. If the
 plain-old-data type references other data types, those types should likewise
 meet either criterion given earlier.</p>

* <code>mapper</code> - This parameter controls which data types are eligible for
 Plain-Old-Data deserialization and includes custom converters from CBOR
 objects to certain data types.

**Returns:**

* The converted object.

**Throws:**

* <code>CBORException</code> - The given type <code>t</code>, or this
 object's CBOR type, is not supported, or the given object's nesting is too
 deep, or another error occurred when serializing the object.

* <code>NullPointerException</code> - The parameter <code>t</code> is null.

### ToObject
    public <T> T ToObject(Type t, PODOptions options)
Converts this CBOR object to an object of an arbitrary type. See the
 documentation for the overload of this method taking a CBORTypeMapper and
 PODOptions parameters for more information. This method (without a
 CBORTypeMapper parameter) allows all data types not otherwise handled to be
 eligible for Plain-Old-Data serialization.

**Parameters:**

* <code>t</code> - <p>The type, class, or interface that this method's return value
 will belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security reasons, an application should not base this
 parameter on user input or other externally supplied data. Whenever
 possible, this parameter should be either a type specially handled by this
 method (such as <code>int</code> or <code>string</code>) or a plain-old-data type
 (POCO or POJO type) within the control of the application. If the
 plain-old-data type references other data types, those types should likewise
 meet either criterion given earlier.</p>

* <code>options</code> - Specifies options for controlling deserialization of CBOR
 objects.

**Returns:**

* The converted object.

**Throws:**

* <code>UnsupportedOperationException</code> - The given type <code>t</code>, or this object's
 CBOR type, is not supported.

* <code>NullPointerException</code> - The parameter <code>t</code> is null.

* <code>CBORException</code> - The given object's nesting is too
 deep, or another error occurred when serializing the object.

### ToObject
    public <T> T ToObject(Type t, CBORTypeMapper mapper, PODOptions options)
<p>Converts this CBOR object to an object of an arbitrary type. The
 following cases are checked in the logical order given (rather than the
 strict order in which they are implemented by this library):</p> <ul><li>If
 the type is <code>CBORObject</code> , return this object.</li><li>If the given
 object is <code>CBORObject.Null</code> (with or without tags), returns <code>
 null</code> .</li><li>If the object is of a type corresponding to a type converter
 mentioned in the <code>mapper</code> parameter, that converter will be used to
 convert the CBOR object to an object of the given type. Type converters can
 be used to override the default conversion behavior of almost any
 object.</li><li>If the type is <code>object</code> , return this
 object.</li><li>If the type is <code>char</code> , converts single-character CBOR
 text strings and CBOR integers from 0 through 65535 to a <code>char</code> object
 and returns that <code>char</code> object.</li><li>If the type is <code>boolean</code>
 (<code>boolean</code> in Java), returns the result of AsBoolean.</li><li>If the
 type is <code>short</code> , returns this number as a 16-bit signed integer after
 converting its value to an integer by discarding its fractional part, and
 throws an exception if this object's value is infinity or a not-a-number
 value, or does not represent a number (currently IllegalStateException, but
 may change in the next major version), or if the value, once converted to an
 integer by discarding its fractional part, is less than -32768 or greater
 than 32767 (currently ArithmeticException, but may change in the next major
 version).</li><li>If the type is <code>long</code> , returns this number as a
 64-bit signed integer after converting its value to an integer by discarding
 its fractional part, and throws an exception if this object's value is
 infinity or a not-a-number value, or does not represent a number (currently
 IllegalStateException, but may change in the next major version), or if the
 value, once converted to an integer by discarding its fractional part, is
 less than -2^63 or greater than 2^63-1 (currently ArithmeticException, but may
 change in the next major version).</li><li>If the type is <code>short</code> ,
 the same rules as for <code>long</code> are used, but the range is from -32768
 through 32767 and the return type is <code>short</code> .</li><li>If the type is
 <code>byte</code> , the same rules as for <code>long</code> are used, but the range is
 from 0 through 255 and the return type is <code>byte</code> .</li><li>If the type
 is <code>sbyte</code> , the same rules as for <code>long</code> are used, but the
 range is from -128 through 127 and the return type is <code>sbyte</code>
 .</li><li>If the type is <code>ushort</code> , the same rules as for <code>long</code>
 are used, but the range is from 0 through 65535 and the return type is
 <code>ushort</code> .</li><li>If the type is <code>uint</code> , the same rules as for
 <code>long</code> are used, but the range is from 0 through 2^31-1 and the return
 type is <code>uint</code> .</li><li>If the type is <code>ulong</code> , the same rules
 as for <code>long</code> are used, but the range is from 0 through 2^63-1 and the
 return type is <code>ulong</code> .</li><li>If the type is <code>int</code> or a
 primitive floating-point type (<code>float</code> , <code>double</code> , as well as
 <code>decimal</code> in.NET), returns the result of the corresponding As*
 method.</li><li>If the type is <code>string</code> , returns the result of
 AsString.</li><li>If the type is <code>EFloat</code> , <code>EDecimal</code> , <code>
 EInteger</code> , or <code>ERational</code> in the <code>PeterO.Numbers</code>
  library (in .NET) or the <code>
 com.github.peteroupc/numbers</code>  artifact (in Java), or if the type is
 <code>BigInteger</code> or <code>BigDecimal</code> in the Java version, converts the
 given object to a number of the corresponding type and throws an exception
 (currently IllegalStateException) if the object does not represent a number
 (for this purpose, infinity and not-a-number values, but not <code>
 CBORObject.Null</code> , are considered numbers). Currently, this is equivalent to
 the result of <code>AsEFloat()</code> , <code>AsEDecimal()</code> , <code>AsEInteger</code>
 , or <code>AsERational()</code> , respectively, but may change slightly in the
 next major version. Note that in the case of <code>EFloat</code> , if this object
 represents a decimal number with a fractional part, the conversion may lose
 information depending on the number, and if the object is a rational number
 with a nonterminating binary expansion, the number returned is a binary
 floating-point number rounded to a high but limited precision. In the case
 of <code>EDecimal</code> , if this object expresses a rational number with a
 nonterminating decimal expansion, returns a decimal number rounded to 34
 digits of precision. In the case of <code>EInteger</code> , if this CBOR object
 expresses a floating-point number, it is converted to an integer by
 discarding its fractional part, and if this CBOR object expresses a rational
 number, it is converted to an integer by dividing the numerator by the
 denominator and discarding the fractional part of the result, and this
 method throws an exception (currently ArithmeticException, but may change in
 the next major version) if this object expresses infinity or a not-a-number
 value.</li><li>In the.NET version, if the type is a nullable (e.g., <code>
 Nullable&amp;lt;int&amp;gt;</code> or <code>int?</code> , returns <code>null</code> if this CBOR
 object is null, or this object's value converted to the nullable's
 underlying type, e.g., <code>int</code> .</li><li>If the type is an enumeration(
 <code>Enum</code>) type and this CBOR object is a text string or an integer,
 returns the appropriate enumerated constant. (For example, if <code>MyEnum</code>
 includes an entry for <code>MyValue</code> , this method will return <code>
 MyEnum.MyValue</code> if the CBOR object represents <code>"MyValue"</code> or the
 underlying value for <code>MyEnum.MyValue</code> .) <b>Note:</b> If an integer is
 converted to a.NET Enum constant, and that integer is shared by more than
 one constant of the same type, it is undefined which constant from among
 them is returned. (For example, if <code>MyEnum.Zero = 0</code> and <code>
 MyEnum.Null = 0</code> , converting 0 to <code>MyEnum</code> may return either <code>
 MyEnum.Zero</code> or <code>MyEnum.Null</code> .) As a result, .NET Enum types with
 constants that share an underlying value should not be passed to this
 method.</li><li>If the type is <code>byte[]</code> (a one-dimensional byte array)
 and this CBOR object is a byte string, returns a byte array which this CBOR
 byte string's data will be copied to. (This method can't be used to encode
 CBOR data to a byte array; for that, use the EncodeToBytes method
 instead.)</li><li>If the type is a one-dimensional or multidimensional array
 type and this CBOR object is an array, returns an array containing the items
 in this CBOR object.</li><li>If the type is List, ReadOnlyCollection or the
 generic or non-generic List, ICollection, Iterable, IReadOnlyCollection, or
 IReadOnlyList (or ArrayList, List, Collection, or Iterable in Java), and if
 this CBOR object is an array, returns an object conforming to the type,
 class, or interface passed to this method, where the object will contain all
 items in this CBOR array.</li><li>If the type is Dictionary,
 ReadOnlyDictionary or the generic or non-generic Map or IReadOnlyDictionary
 (or HashMap or Map in Java), and if this CBOR object is a map, returns an
 object conforming to the type, class, or interface passed to this method,
 where the object will contain all keys and values in this CBOR
 map.</li><li>If the type is an enumeration constant ("enum"), and this CBOR
 object is an integer or text string, returns the enumeration constant with
 the given number or name, respectively. (Enumeration constants made up of
 multiple enumeration constants, as allowed by .NET, can only be matched by
 number this way.)</li><li>If the type is <code>java.util.Date</code> (or <code>Date</code>
 in Java) , returns a date/time object if the CBOR object's outermost tag ==
 0 || tag == 1. For tag 1, this method treats the CBOR object as a number of
 seconds since the start of 1970, which is based on the POSIX definition of
 "seconds since the Epoch", a definition that does not count leap seconds. In
 this method, this number of seconds assumes the use of a proleptic Gregorian
 calendar, in which the rules regarding the number of days in each month and
 which years are leap years are the same for all years as they were in 1970
 (including without regard to time zone differences or transitions from other
 calendars to the Gregorian). The string format used in tag 0 supports only
 years up to 4 decimal digits long. For tag 1, CBOR objects that express
 infinity or not-a-number (NaN) are treated as invalid by this method. This
 default behavior for <code>java.util.Date</code> and <code>Date</code> can be changed by
 passing a suitable CBORTypeMapper to this method, such as a CBORTypeMapper
 that registers a CBORDateConverter for <code>java.util.Date</code> or <code>Date</code>
 objects. See the examples.</li><li>If the type is <code>java.net.URI</code> (or <code>
 URI</code> in Java), returns a URI object if possible.</li><li>If the type is
 <code>java.util.UUID</code> (or <code>UUID</code> in Java), returns a UUID object if
 possible.</li><li>Plain-Old-Data deserialization: If the object is a type
 not specially handled above, the type includes a zero-parameter constructor
 (default or not), this CBOR object is a CBOR map, and the "mapper" parameter
 (if any) allows this type to be eligible for Plain-Old-Data deserialization,
 then this method checks the given type for eligible setters as
 follows:</li><li>(*) In the .NET version, eligible setters are the public,
 nonstatic setters of properties with a public, nonstatic getter. Eligible
 setters also include public, nonstatic, non- <code>static final</code> , non- <code>
 readonly</code> fields. If a class has two properties and/or fields of the form
 "X" and "IsX", where "X" is any name, or has multiple properties and/or
 fields with the same name, those properties and fields are
 ignored.</li><li>(*) In the Java version, eligible setters are public,
 nonstatic methods starting with "set" followed by a character other than a
 basic digit or lowercase letter, that is, other than "a" to "z" or "0" to
 "9", that take one parameter. The class containing an eligible setter must
 have a public, nonstatic method with the same name, but starting with "get"
 or "is" rather than "set", that takes no parameters and does not return
 void. (For example, if a class has "public setValue(string)" and "public
 getValue()", "setValue" is an eligible setter. However, "setValue()" and
 "setValue(string, int)" are not eligible setters.) In addition, public,
 nonstatic, nonfinal fields are also eligible setters. If a class has two or
 more otherwise eligible setters (methods and/or fields) with the same name,
 but different parameter type, they are not eligible setters.</li><li>Then,
 the method creates an object of the given type and invokes each eligible
 setter with the corresponding value in the CBOR map, if any. Key names in
 the map are matched to eligible setters according to the rules described in
 the <code>PODOptions</code> documentation. Note that for
 security reasons, certain types are not supported even if they contain
 eligible setters. For the Java version, the object creation may fail in the
 case of a nested nonstatic class.</li></ul><p>The following example
 (originally written in C# for the DotNet version) uses a CBORTypeMapper to
 change how CBOR objects are converted to java.util.Date objects. In this case, the
 ToObject method assumes the CBOR object is an untagged number giving the
 number of seconds since the start of 1970.</p> <pre>CBORTypeMapper conv =
 new CBORTypeMapper().AddConverter(java.util.Date.class,
 CBORDateConverter.UntaggedNumber); CBORObject obj =
 CBORObject.FromObject().getToObject()&lt;java.util.Date&gt;(conv);</pre> <p>Java offers
 no easy way to express a generic type, at least none as easy as C#'s <code>
 typeof</code> operator. The following example, written in Java, is a way to
 specify that the return value will be an ArrayList of string objects.</p>
 <pre>Type arrayListString = new ParameterizedType() { public Type[]
 getActualTypeArguments() { /* Contains one type parameter, string*/ return
 new Type[] { string.class }; } public Type getRawType() { /* Raw type is
 ArrayList */ return ArrayList.class; } public Type getOwnerType() { return
 null; } }; ArrayList&lt;string&gt; array = (ArrayList&lt;string&gt;)
 cborArray.ToObject(arrayListString);</pre> <p>By comparison, the C# version
 is much shorter.</p> <pre>List&lt;string&gt; array =
 (List&lt;string&gt;)cborArray.ToObject(typeof(List&lt;string&gt;));</pre> .

**Parameters:**

* <code>t</code> - <p>The type, class, or interface that this method's return value
 will belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security reasons, an application should not base this
 parameter on user input or other externally supplied data. Whenever
 possible, this parameter should be either a type specially handled by this
 method, such as <code>int</code> or <code>string</code> , or a plain-old-data type
 (POCO or POJO type) within the control of the application. If the
 plain-old-data type references other data types, those types should likewise
 meet either criterion given earlier.</p>

* <code>mapper</code> - This parameter controls which data types are eligible for
 Plain-Old-Data deserialization and includes custom converters from CBOR
 objects to certain data types. Can be null.

* <code>options</code> - Specifies options for controlling deserialization of CBOR
 objects.

**Returns:**

* The converted object.

**Throws:**

* <code>CBORException</code> - The given type <code>t</code> , or
 this object's CBOR type, is not supported, or the given object's nesting is
 too deep, or another error occurred when serializing the object.

* <code>NullPointerException</code> - The parameter <code>t</code> or <code>options</code> is
 null.

### DecodeObjectFromBytes
    public static <T> T DecodeObjectFromBytes(byte[] data, CBOREncodeOptions enc, Type t, CBORTypeMapper mapper, PODOptions pod)
Generates an object of an arbitrary type from an array of CBOR-encoded
 bytes, using the given <code>CBOREncodeOptions</code> object to control the
 decoding process. It is equivalent to DecodeFromBytes followed by ToObject.
 See the documentation for those methods for more information.

**Parameters:**

* <code>data</code> - A byte array in which a single CBOR object is encoded.

* <code>enc</code> - Specifies options to control how the CBOR object is decoded. See
 <code>CBOREncodeOptions</code> for more information.

* <code>t</code> - <p>The type, class, or interface that this method's return value
 will belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security reasons, an application should not base this
 parameter on user input or other externally supplied data. Whenever
 possible, this parameter should be either a type specially handled by this
 method, such as <code>int</code> or <code>string</code>, or a plain-old-data type
 (POCO or POJO type) within the control of the application. If the
 plain-old-data type references other data types, those types should likewise
 meet either criterion given earlier.</p>

* <code>mapper</code> - This parameter controls which data types are eligible for
 Plain-Old-Data deserialization and includes custom converters from CBOR
 objects to certain data types. Can be null.

* <code>pod</code> - Specifies options for controlling deserialization of CBOR
 objects.

**Returns:**

* An object of the given type decoded from the given byte array.
 Returns null (as opposed to CBORObject.Null) if <code>data</code> is empty and
 the AllowEmpty property is set on the given CBOREncodeOptions object.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where not all of the byte array
 represents a CBOR object. This exception is also thrown if the parameter
 <code>data</code> is empty unless the AllowEmpty property is set on the given
 options object. Also thrown if the given type <code>t</code>, or this object's
 CBOR type, is not supported, or the given object's nesting is too deep, or
 another error occurred when serializing the object.

* <code>NullPointerException</code> - The parameter <code>data</code> is null, or the
 parameter <code>enc</code> is null, or the parameter <code>t</code> or <code>pod</code> is
 null.

### DecodeObjectFromBytes
    public static <T> T DecodeObjectFromBytes(byte[] data, CBOREncodeOptions enc, Type t)
Generates an object of an arbitrary type from an array of CBOR-encoded
 bytes, using the given <code>CBOREncodeOptions</code> object to control the
 decoding process. It is equivalent to DecodeFromBytes followed by ToObject.
 See the documentation for those methods for more information.

**Parameters:**

* <code>data</code> - A byte array in which a single CBOR object is encoded.

* <code>enc</code> - Specifies options to control how the CBOR object is decoded. See
 <code>CBOREncodeOptions</code> for more information.

* <code>t</code> - <p>The type, class, or interface that this method's return value
 will belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security reasons, an application should not base this
 parameter on user input or other externally supplied data. Whenever
 possible, this parameter should be either a type specially handled by this
 method, such as <code>int</code> or <code>string</code>, or a plain-old-data type
 (POCO or POJO type) within the control of the application. If the
 plain-old-data type references other data types, those types should likewise
 meet either criterion given earlier.</p>

**Returns:**

* An object of the given type decoded from the given byte array.
 Returns null (as opposed to CBORObject.Null) if <code>data</code> is empty and
 the AllowEmpty property is set on the given CBOREncodeOptions object.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where not all of the byte array
 represents a CBOR object. This exception is also thrown if the parameter
 <code>data</code> is empty unless the AllowEmpty property is set on the given
 options object. Also thrown if the given type <code>t</code>, or this object's
 CBOR type, is not supported, or the given object's nesting is too deep, or
 another error occurred when serializing the object.

* <code>NullPointerException</code> - The parameter <code>data</code> is null, or the
 parameter <code>enc</code> is null, or the parameter <code>t</code> is null.

### DecodeObjectFromBytes
    public static <T> T DecodeObjectFromBytes(byte[] data, Type t, CBORTypeMapper mapper, PODOptions pod)
Generates an object of an arbitrary type from an array of CBOR-encoded
 bytes. It is equivalent to DecodeFromBytes followed by ToObject. See the
 documentation for those methods for more information.

**Parameters:**

* <code>data</code> - A byte array in which a single CBOR object is encoded.

* <code>t</code> - <p>The type, class, or interface that this method's return value
 will belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security reasons, an application should not base this
 parameter on user input or other externally supplied data. Whenever
 possible, this parameter should be either a type specially handled by this
 method, such as <code>int</code> or <code>string</code>, or a plain-old-data type
 (POCO or POJO type) within the control of the application. If the
 plain-old-data type references other data types, those types should likewise
 meet either criterion given earlier.</p>

* <code>mapper</code> - This parameter controls which data types are eligible for
 Plain-Old-Data deserialization and includes custom converters from CBOR
 objects to certain data types. Can be null.

* <code>pod</code> - Specifies options for controlling deserialization of CBOR
 objects.

**Returns:**

* An object of the given type decoded from the given byte array.
 Returns null (as opposed to CBORObject.Null) if <code>data</code> is empty and
 the AllowEmpty property is set on the given CBOREncodeOptions object.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where not all of the byte array
 represents a CBOR object. This exception is also thrown if the parameter
 <code>data</code> is empty unless the AllowEmpty property is set on the given
 options object. Also thrown if the given type <code>t</code>, or this object's
 CBOR type, is not supported, or the given object's nesting is too deep, or
 another error occurred when serializing the object.

* <code>NullPointerException</code> - The parameter <code>data</code> is null, or the
 parameter <code>t</code> or <code>pod</code> is null.

### DecodeObjectFromBytes
    public static <T> T DecodeObjectFromBytes(byte[] data, Type t)
Generates an object of an arbitrary type from an array of CBOR-encoded
 bytes. It is equivalent to DecodeFromBytes followed by ToObject. See the
 documentation for those methods for more information.

**Parameters:**

* <code>data</code> - A byte array in which a single CBOR object is encoded.

* <code>t</code> - <p>The type, class, or interface that this method's return value
 will belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security reasons, an application should not base this
 parameter on user input or other externally supplied data. Whenever
 possible, this parameter should be either a type specially handled by this
 method, such as <code>int</code> or <code>string</code>, or a plain-old-data type
 (POCO or POJO type) within the control of the application. If the
 plain-old-data type references other data types, those types should likewise
 meet either criterion given earlier.</p>

**Returns:**

* An object of the given type decoded from the given byte array.
 Returns null (as opposed to CBORObject.Null) if <code>data</code> is empty and
 the AllowEmpty property is set on the given CBOREncodeOptions object.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where not all of the byte array
 represents a CBOR object. This exception is also thrown if the parameter
 <code>data</code> is empty unless the AllowEmpty property is set on the given
 options object. Also thrown if the given type <code>t</code>, or this object's
 CBOR type, is not supported, or the given object's nesting is too deep, or
 another error occurred when serializing the object.

* <code>NullPointerException</code> - The parameter <code>data</code> is null, or the
 parameter <code>t</code> is null.

### FromInt64
    public static CBORObject FromInt64(long value)
Generates a CBOR object from a 64-bit signed integer.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 64-bit signed integer.

**Returns:**

* A CBOR object.

### FromObject
    @Deprecated public static CBORObject FromObject(long value)
Generates a CBOR object from a 64-bit signed integer.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 64-bit signed integer.

**Returns:**

* A CBOR object.

### FromObject
    @Deprecated public static CBORObject FromObject(CBORObject value)
Generates a CBOR object from a CBOR object.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a CBOR object.

**Returns:**

* Same as <code>value</code>, or "CBORObject.Null" is <code>value</code> is
 null.

### CalcEncodedSize
    public long CalcEncodedSize()
Calculates the number of bytes this CBOR object takes when serialized as a
 byte array using the <code>EncodeToBytes()</code> method. This calculation
 assumes that integers, lengths of maps and arrays, lengths of text and byte
 strings, and tag numbers are encoded in their shortest form; that
 floating-point numbers are encoded in their shortest value-preserving form;
 and that no indefinite-length encodings are used.

**Returns:**

* The number of bytes this CBOR object takes when serialized as a byte
 array using the <code>EncodeToBytes()</code> method.

**Throws:**

* <code>CBORException</code> - The CBOR object has an extremely
 deep level of nesting, including if the CBOR object is or has an array or
 map that includes itself.

### FromEInteger
    public static CBORObject FromEInteger(com.upokecenter.numbers.EInteger bigintValue)
<p>Generates a CBOR object from an arbitrary-precision integer. The CBOR
 object is generated as follows: </p> <ul> <li>If the number is null, returns
 CBORObject.Null.</li><li>Otherwise, if the number is greater than or equal
 to -(2^64) and less than 2^64, the CBOR object will have the object type
 Integer and the appropriate value.</li><li>Otherwise, the CBOR object will
 have tag 2 (zero or positive) or 3 (negative) and the appropriate
 value.</li></ul>

**Parameters:**

* <code>bigintValue</code> - An arbitrary-precision integer. Can be null.

**Returns:**

* The given number encoded as a CBOR object. Returns CBORObject.Null
 if <code>bigintValue</code> is null.

### FromObject
    @Deprecated public static CBORObject FromObject(com.upokecenter.numbers.EInteger bigintValue)
Generates a CBOR object from an arbitrary-precision integer.

**Parameters:**

* <code>bigintValue</code> - An arbitrary-precision integer. Can be null.

**Returns:**

* The given number encoded as a CBOR object. Returns CBORObject.Null
 if <code>bigintValue</code> is null.

### FromEFloat
    public static CBORObject FromEFloat(com.upokecenter.numbers.EFloat bigValue)
<p>Generates a CBOR object from an arbitrary-precision binary floating-point
 number. The CBOR object is generated as follows (this is a change in version
 4.0): </p> <ul> <li>If the number is null, returns
 CBORObject.Null.</li><li>Otherwise, if the number expresses infinity,
 not-a-number, or negative zero, the CBOR object will have tag 269 and the
 appropriate format.</li><li>Otherwise, if the number's exponent is at least
 2^64 or less than -(2^64), the CBOR object will have tag 265 and the
 appropriate format.</li><li>Otherwise, the CBOR object will have tag 5 and
 the appropriate format.</li></ul>

**Parameters:**

* <code>bigValue</code> - An arbitrary-precision binary floating-point number. Can be
 null.

**Returns:**

* The given number encoded as a CBOR object. Returns CBORObject.Null
 if <code>bigValue</code> is null.

### FromObject
    @Deprecated public static CBORObject FromObject(com.upokecenter.numbers.EFloat bigValue)
Generates a CBOR object from an arbitrary-precision binary floating-point
 number.

**Parameters:**

* <code>bigValue</code> - An arbitrary-precision binary floating-point number.

**Returns:**

* The given number encoded as a CBOR object.

### FromERational
    public static CBORObject FromERational(com.upokecenter.numbers.ERational bigValue)
<p>Generates a CBOR object from an arbitrary-precision rational number. The
 CBOR object is generated as follows (this is a change in version 4.0): </p>
 <ul> <li>If the number is null, returns CBORObject.Null.</li><li>Otherwise,
 if the number expresses infinity, not-a-number, or negative zero, the CBOR
 object will have tag 270 and the appropriate format.</li><li>Otherwise, the
 CBOR object will have tag 30 and the appropriate format.</li></ul>

**Parameters:**

* <code>bigValue</code> - An arbitrary-precision rational number. Can be null.

**Returns:**

* The given number encoded as a CBOR object. Returns CBORObject.Null
 if <code>bigValue</code> is null.

### FromObject
    @Deprecated public static CBORObject FromObject(com.upokecenter.numbers.ERational bigValue)
Generates a CBOR object from an arbitrary-precision rational number.

**Parameters:**

* <code>bigValue</code> - An arbitrary-precision rational number.

**Returns:**

* The given number encoded as a CBOR object.

### FromEDecimal
    public static CBORObject FromEDecimal(com.upokecenter.numbers.EDecimal bigValue)
<p>Generates a CBOR object from a decimal number. The CBOR object is
 generated as follows (this is a change in version 4.0): </p> <ul> <li>If the
 number is null, returns CBORObject.Null.</li><li>Otherwise, if the number
 expresses infinity, not-a-number, or negative zero, the CBOR object will
 have tag 268 and the appropriate format.</li><li>If the number's exponent is
 at least 2^64 or less than -(2^64), the CBOR object will have tag 264 and
 the appropriate format.</li><li>Otherwise, the CBOR object will have tag 4
 and the appropriate format.</li></ul>

**Parameters:**

* <code>bigValue</code> - An arbitrary-precision decimal number. Can be null.

**Returns:**

* The given number encoded as a CBOR object. Returns CBORObject.Null
 if <code>bigValue</code> is null.

### FromObject
    @Deprecated public static CBORObject FromObject(com.upokecenter.numbers.EDecimal bigValue)
Generates a CBOR object from a decimal number.

**Parameters:**

* <code>bigValue</code> - An arbitrary-precision decimal number.

**Returns:**

* The given number encoded as a CBOR object.

### FromString
    public static CBORObject FromString(String strValue)
Generates a CBOR object from a text string.

**Parameters:**

* <code>strValue</code> - A text string value. Can be null.

**Returns:**

* A CBOR object representing the string, or CBORObject.Null if
 stringValue is null.

**Throws:**

* <code>IllegalArgumentException</code> - The string contains an unpaired surrogate code
 point.

### FromObject
    @Deprecated public static CBORObject FromObject(String strValue)
Generates a CBOR object from a text string.

**Parameters:**

* <code>strValue</code> - A text string value. Can be null.

**Returns:**

* A CBOR object representing the string, or CBORObject.Null if
 stringValue is null.

### FromInt32
    public static CBORObject FromInt32(int value)
Generates a CBOR object from a 32-bit signed integer.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 32-bit signed integer.

**Returns:**

* A CBOR object.

### FromGuid
    public static CBORObject FromGuid(UUID value)
Generates a CBOR object from a java.util.UUID.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a java.util.UUID.

**Returns:**

* A CBOR object.

### FromObject
    @Deprecated public static CBORObject FromObject(int value)
Generates a CBOR object from a 32-bit signed integer.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 32-bit signed integer.

**Returns:**

* A CBOR object.

### FromInt16
    public static CBORObject FromInt16(short value)
Generates a CBOR object from a 16-bit signed integer.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 16-bit signed integer.

**Returns:**

* A CBOR object generated from the given integer.

### FromObject
    @Deprecated public static CBORObject FromObject(short value)
Generates a CBOR object from a 16-bit signed integer.

**Parameters:**

* <code>value</code> - A 16-bit signed integer.

**Returns:**

* A CBOR object generated from the given integer.

### FromBool
    public static CBORObject FromBool(boolean value)
Returns the CBOR true value or false value, depending on "value".

**Parameters:**

* <code>value</code> - Either <code>true</code> or <code>false</code>.

**Returns:**

* CBORObject.True if value is true; otherwise CBORObject.False.

### FromObject
    @Deprecated public static CBORObject FromObject(boolean value)
Returns the CBOR true value or false value, depending on "value".

**Parameters:**

* <code>value</code> - Either <code>true</code> or <code>false</code>.

**Returns:**

* CBORObject.True if value is true; otherwise CBORObject.False.

### FromByte
    public static CBORObject FromByte(byte value)
Generates a CBOR object from a byte (0 to 255).

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a byte (from 0 to 255).

**Returns:**

* A CBOR object generated from the given integer.

### FromObject
    @Deprecated public static CBORObject FromObject(byte value)
Generates a CBOR object from a byte.

**Parameters:**

* <code>value</code> - A byte.

**Returns:**

* A CBOR object.

### FromSingle
    public static CBORObject FromSingle(float value)
Generates a CBOR object from a 32-bit floating-point number. The input value
 can be a not-a-number (NaN) value (such as <code>Float.NaN</code> in DotNet or
 Float.NaN in Java); however, NaN values have multiple forms that are
 equivalent for many applications' purposes, and <code>Float.NaN</code> / <code>
 Float.NaN</code> is only one of these equivalent forms. In fact, <code>
 CBORObject.FromSingle(Float.NaN)</code> or <code>
 CBORObject.FromSingle(Float.NaN)</code> could produce a CBOR-encoded object that
 differs between DotNet and Java, because <code>Float.NaN</code> / <code>
 Float.NaN</code> may have a different form in DotNet and Java (for example, the
 NaN value's sign may be negative in DotNet, but positive in Java).

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 32-bit floating-point number.

**Returns:**

* A CBOR object generated from the given number.

### FromObject
    @Deprecated public static CBORObject FromObject(float value)
Generates a CBOR object from a 32-bit floating-point number.

**Parameters:**

* <code>value</code> - A 32-bit floating-point number.

**Returns:**

* A CBOR object.

### FromDouble
    public static CBORObject FromDouble(double value)
Generates a CBOR object from a 64-bit floating-point number. The input value
 can be a not-a-number (NaN) value (such as <code>Double.NaN</code>); however,
 NaN values have multiple forms that are equivalent for many applications'
 purposes, and <code>Double.NaN</code> is only one of these equivalent forms. In
 fact, <code>CBORObject.FromDouble(Double.NaN)</code> could produce a CBOR-encoded
 object that differs between DotNet and Java, because <code>Double.NaN</code> may
 have a different form in DotNet and Java (for example, the NaN value's sign
 may be negative in DotNet, but positive in Java).

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 64-bit floating-point number.

**Returns:**

* A CBOR object generated from the given number.

### FromObject
    @Deprecated public static CBORObject FromObject(double value)
Generates a CBOR object from a 64-bit floating-point number.

**Parameters:**

* <code>value</code> - A 64-bit floating-point number.

**Returns:**

* A CBOR object generated from the given number.

### FromByteArray
    public static CBORObject FromByteArray(byte[] bytes)
<p>Generates a CBOR object from an array of 8-bit bytes; the byte array is
 copied to a new byte array in this process. (This method can't be used to
 decode CBOR data from a byte array; for that, use the <b>DecodeFromBytes</b>
 method instead.).</p>

**Parameters:**

* <code>bytes</code> - An array of 8-bit bytes; can be null.

**Returns:**

* A CBOR object where each element of the given byte array is copied
 to a new array, or CBORObject.Null if the value is null.

### FromObject
    @Deprecated public static CBORObject FromObject(byte[] bytes)
Generates a CBOR object from an array of 8-bit bytes.

**Parameters:**

* <code>bytes</code> - An array of 8-bit bytes; can be null.

**Returns:**

* A CBOR object where each element of the given byte array is copied
 to a new array, or CBORObject.Null if the value is null.

### FromCBORArray
    public static CBORObject FromCBORArray(CBORObject[] array)
Generates a CBOR object from an array of CBOR objects.

**Parameters:**

* <code>array</code> - An array of CBOR objects.

**Returns:**

* A CBOR object where each element of the given array is copied to a
 new array, or CBORObject.Null if the value is null.

### FromObject
    @Deprecated public static CBORObject FromObject(CBORObject[] array)
Generates a CBOR object from an array of CBOR objects.

**Parameters:**

* <code>array</code> - An array of CBOR objects.

**Returns:**

* A CBOR object.

### FromObject
    public static CBORObject FromObject(int[] array)
Generates a CBOR object from an array of 32-bit integers.

**Parameters:**

* <code>array</code> - An array of 32-bit integers.

**Returns:**

* A CBOR array object where each element of the given array is copied
 to a new array, or CBORObject.Null if the value is null.

### FromObject
    public static CBORObject FromObject(long[] array)
Generates a CBOR object from an array of 64-bit integers.

**Parameters:**

* <code>array</code> - An array of 64-bit integers.

**Returns:**

* A CBOR array object where each element of the given array is copied
 to a new array, or CBORObject.Null if the value is null.

### FromObject
    public static CBORObject FromObject(Object obj)
Generates a CBORObject from an arbitrary object. See the overload of this
 method that takes CBORTypeMapper and PODOptions arguments.

**Parameters:**

* <code>obj</code> - <p>The parameter <code>obj</code> is an arbitrary object, which can be
 null. </p><p><b>NOTE:</b> For security reasons, whenever possible, an
 application should not base this parameter on user input or other externally
 supplied data, and whenever possible, the application should limit this
 parameter's inputs to types specially handled by this method (such as <code>
 int</code> or <code>string</code>) and/or to plain-old-data types (POCO or POJO types)
 within the control of the application. If the plain-old-data type references
 other data types, those types should likewise meet either criterion
 above.</p>.

**Returns:**

* A CBOR object corresponding to the given object. Returns
 CBORObject.Null if the object is null.

### FromObject
    public static CBORObject FromObject(Object obj, PODOptions options)
Generates a CBORObject from an arbitrary object. See the overload of this
 method that takes CBORTypeMapper and PODOptions arguments.

**Parameters:**

* <code>obj</code> - <p>The parameter <code>obj</code> is an arbitrary object.
 </p><p><b>NOTE:</b> For security reasons, whenever possible, an application
 should not base this parameter on user input or other externally supplied
 data, and whenever possible, the application should limit this parameter's
 inputs to types specially handled by this method (such as <code>int</code> or
 <code>string</code>) and/or to plain-old-data types (POCO or POJO types) within
 the control of the application. If the plain-old-data type references other
 data types, those types should likewise meet either criterion above.</p>.

* <code>options</code> - An object containing options to control how certain objects
 are converted to CBOR objects.

**Returns:**

* A CBOR object corresponding to the given object. Returns
 CBORObject.Null if the object is null.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>options</code> is null.

### FromObject
    public static CBORObject FromObject(Object obj, CBORTypeMapper mapper)
Generates a CBORObject from an arbitrary object. See the overload of this
 method that takes CBORTypeMapper and PODOptions arguments.

**Parameters:**

* <code>obj</code> - <p>The parameter <code>obj</code> is an arbitrary object.
 </p><p><b>NOTE:</b> For security reasons, whenever possible, an application
 should not base this parameter on user input or other externally supplied
 data, and whenever possible, the application should limit this parameter's
 inputs to types specially handled by this method (such as <code>int</code> or
 <code>string</code>) and/or to plain-old-data types (POCO or POJO types) within
 the control of the application. If the plain-old-data type references other
 data types, those types should likewise meet either criterion above.</p>.

* <code>mapper</code> - An object containing optional converters to convert objects of
 certain types to CBOR objects.

**Returns:**

* A CBOR object corresponding to the given object. Returns
 CBORObject.Null if the object is null.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>mapper</code> is null.

### FromObject
    public static CBORObject FromObject(Object obj, CBORTypeMapper mapper, PODOptions options)
<p>Generates a CBORObject from an arbitrary object, using the given options
 to control how certain objects are converted to CBOR objects. The following
 cases are checked in the logical order given (rather than the strict order
 in which they are implemented by this library):</p> <ul><li> <code>null</code> is
 converted to <code>CBORObject.Null</code> .</li><li>A <code>CBORObject</code> is
 returned as itself.</li><li>If the object is of a type corresponding to a
 type converter mentioned in the <code>mapper</code> parameter, that converter
 will be used to convert the object to a CBOR object. Type converters can be
 used to override the default conversion behavior of almost any
 object.</li><li>A <code>char</code> is converted to an integer (from 0 through
 65535), and returns a CBOR object of that integer. (This is a change in
 version 4.0 from previous versions, which converted <code>char</code> , except
 surrogate code points from 0xd800 through 0xdfff, into single-character text
 strings.)</li><li>A <code>boolean</code> (<code>boolean</code> in Java) is converted
 to <code>CBORObject.True</code> or <code>CBORObject.False</code> .</li><li>A <code>
 byte</code> is converted to a CBOR integer from 0 through 255.</li><li>A primitive
 integer type (<code>int</code> , <code>short</code> , <code>long</code> , as well as
 <code>sbyte</code> , <code>ushort</code> , <code>uint</code> , and <code>ulong</code> in.NET) is
 converted to the corresponding CBOR integer.</li><li>A primitive
 floating-point type (<code>float</code> , <code>double</code> , as well as <code>
 decimal</code> in.NET) is converted to the corresponding CBOR number.</li><li>A
 <code>string</code> is converted to a CBOR text string. To create a CBOR byte
 string object from <code>string</code> , see the example given in <code>
 com.upokecenter.cbor.CBORObject.FromObject(System.Byte[])</code>.</li><li>In
 the.NET version, a nullable is converted to <code>CBORObject.Null</code> if the
 nullable's value is <code>null</code> , or converted according to the nullable's
 underlying type, if that type is supported by this method.</li><li>In the
 Java version, a number of type <code>BigInteger</code> or <code>BigDecimal</code> is
 converted to the corresponding CBOR number.</li><li>A number of type <code>
 EDecimal</code> , <code>EFloat</code> , <code>EInteger</code> , and <code>ERational</code> in the
 <code>
 PeterO.Numbers</code>  library (in .NET) or the <code>
 com.github.peteroupc/numbers</code>  artifact (in Java) is converted to the
 corresponding CBOR number.</li><li>An array other than <code>byte[]</code> is
 converted to a CBOR array. In the.NET version, a multidimensional array is
 converted to an array of arrays.</li><li>A <code>byte[]</code> (1-dimensional
 byte array) is converted to a CBOR byte string; the byte array is copied to
 a new byte array in this process. (This method can't be used to decode CBOR
 data from a byte array; for that, use the <b>DecodeFromBytes</b> method
 instead.)</li><li>An object implementing Map (Map in Java) is converted to a
 CBOR map containing the keys and values enumerated.</li><li>An object
 implementing Iterable (Iterable in Java) is converted to a CBOR array
 containing the items enumerated.</li><li>An enumeration (<code>Enum</code>)
 object is converted to its <i>underlying value</i> in the.NET version, or
 the result of its <code>ordinal()</code> method in the Java version.</li><li>An
 object of type <code>java.util.Date</code> , <code>java.net.URI</code> , or <code>java.util.UUID</code> (<code>
 Date</code> , <code>URI</code> , or <code>UUID</code> , respectively, in Java) will be
 converted to a tagged CBOR object of the appropriate kind. By default,
 <code>java.util.Date</code> / <code>Date</code> will be converted to a tag-0 string
 following the date format used in the Atom syndication format, but this
 behavior can be changed by passing a suitable CBORTypeMapper to this method,
 such as a CBORTypeMapper that registers a CBORDateConverter for <code>
 java.util.Date</code> or <code>Date</code> objects. See the examples.</li><li>If the object
 is a type not specially handled above, this method checks the <code>obj</code>
 parameter for eligible getters as follows:</li><li>(*) In the .NET version,
 eligible getters are the public, nonstatic getters of read/write properties
 (and also those of read-only properties in the case of a compiler-generated
 type or an F# type). Eligible getters also include public, nonstatic, non-
 <code>static final</code> , non- <code>readonly</code> fields. If a class has two properties
 and/or fields of the form "X" and "IsX", where "X" is any name, or has
 multiple properties and/or fields with the same name, those properties and
 fields are ignored.</li><li>(*) In the Java version, eligible getters are
 public, nonstatic methods starting with "get" or "is" (either word followed
 by a character other than a basic digit or lowercase letter, that is, other
 than "a" to "z" or "0" to "9"), that take no parameters and do not return
 void, except that methods named "getClass" are not eligible getters. In
 addition, public, nonstatic, nonfinal fields are also eligible getters. If a
 class has two otherwise eligible getters (methods and/or fields) of the form
 "isX" and "getX", where "X" is the same in both, or two such getters with
 the same name but different return type, they are not eligible
 getters.</li><li>Then, the method returns a CBOR map with each eligible
 getter's name or property name as each key, and with the corresponding value
 returned by that getter as that key's value. Before adding a key-value pair
 to the map, the key's name is adjusted according to the rules described in
 the <code>PODOptions</code> documentation. Note that for
 security reasons, certain types are not supported even if they contain
 eligible getters.</li></ul> <p><b>REMARK:</b> .NET enumeration (<code>
 Enum</code>) constants could also have been converted to text strings with <code>
 toString()</code> , but that method will return multiple names if the given Enum
 object is a combination of Enum objects (e.g. if the object is <code>
 FileAccess.Read | FileAccess.Write</code>). More generally, if Enums are
 converted to text strings, constants from Enum types with the <code>Flags</code>
 attribute, and constants from the same Enum type that share an underlying
 value, should not be passed to this method.</p><p>The following example
 (originally written in C# for the DotNet version) uses a CBORTypeMapper to
 change how java.util.Date objects are converted to CBOR. In this case, such
 objects are converted to CBOR objects with tag 1 that store numbers giving
 the number of seconds since the start of 1970.</p> <pre>CBORTypeMapper conv
 = new CBORTypeMapper().AddConverter(java.util.Date.class,
 CBORDateConverter.TaggedNumber); CBORObject obj =
 CBORObject.FromObject(java.util.Date.Now, conv);</pre> <p>The following example
 generates a CBOR object from a 64-bit signed integer that is treated as a
 64-bit unsigned integer (such as DotNet's UInt64, which has no direct
 equivalent in the Java language), in the sense that the value is treated as
 2^64 plus the original value if it's negative.</p> <pre>long x = -40L; /*
 Example 64-bit value treated as 2^64-40.*/ CBORObject obj =
 CBORObject.FromObject(v &lt; 0 ? EInteger.FromInt32(1).ShiftLeft(64).Add(v)
 : EInteger.FromInt64(v));</pre> <p>In the Java version, which has
 java.math.getBigInteger(), the following can be used instead:</p> <pre>long x =
 -40L; /* Example 64-bit value treated as 2^64-40.*/ CBORObject obj =
 CBORObject.FromObject(v &lt; 0 ?
 BigInteger.valueOf(1).shiftLeft(64).add(BigInteger.valueOf(v)) :
 BigInteger.valueOf(v));</pre>

**Parameters:**

* <code>obj</code> - <p>An arbitrary object to convert to a CBOR object.
 </p><p><b>NOTE:</b> For security reasons, whenever possible, an application
 should not base this parameter on user input or other externally supplied
 data, and whenever possible, the application should limit this parameter's
 inputs to types specially handled by this method (such as <code>int</code> or
 <code>string</code>) and/or to plain-old-data types (POCO or POJO types) within
 the control of the application. If the plain-old-data type references other
 data types, those types should likewise meet either criterion above.</p>.

* <code>mapper</code> - An object containing optional converters to convert objects of
 certain types to CBOR objects. Can be null.

* <code>options</code> - An object containing options to control how certain objects
 are converted to CBOR objects.

**Returns:**

* A CBOR object corresponding to the given object. Returns
 CBORObject.Null if the object is null.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>options</code> is null.

* <code>CBORException</code> - An error occurred while
 converting the given object to a CBOR object.

### WithTag
    public CBORObject WithTag(com.upokecenter.numbers.EInteger bigintTag)
Generates a CBOR object from this one, but gives the resulting object a tag
 in addition to its existing tags (the new tag is made the outermost tag).

**Parameters:**

* <code>bigintTag</code> - <p>Tag number. The tag number 55799 can be used to mark a
 "self-described CBOR" object. This document does not attempt to list all
 CBOR tags and their meanings. An up-to-date list can be found at the CBOR
 Tags registry maintained by the Internet Assigned Numbers Authority(
 <i>iana.org/assignments/cbor-tags</i>).</p>

**Returns:**

* A CBOR object with the same value as this one but given the tag
 <code>bigintTag</code> in addition to its existing tags (the new tag is made the
 outermost tag).

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>bigintTag</code> is less than 0 or
 greater than 2^64-1.

* <code>NullPointerException</code> - The parameter <code>bigintTag</code> is null.

### FromCBORObjectAndTag
    public static CBORObject FromCBORObjectAndTag(CBORObject cborObj, com.upokecenter.numbers.EInteger bigintTag)
Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag in addition to its existing tags (the new tag is made the
 outermost tag).

**Parameters:**

* <code>cborObj</code> - <p>The parameter <code>cborObj</code> is a CBORObject.
 </p><p><b>NOTE:</b> For security reasons, whenever possible, an application
 should not base this parameter on user input or other externally supplied
 data, and whenever possible, the application should limit this parameter's
 inputs to types specially handled by this method (such as <code>int</code> or
 <code>string</code>) and/or to plain-old-data types (POCO or POJO types) within
 the control of the application. If the plain-old-data type references other
 data types, those types should likewise meet either criterion above.</p>.

* <code>bigintTag</code> - <p>Tag number. The tag number 55799 can be used to mark a
 "self-described CBOR" object. This document does not attempt to list all
 CBOR tags and their meanings. An up-to-date list can be found at the CBOR
 Tags registry maintained by the Internet Assigned Numbers Authority(
 <i>iana.org/assignments/cbor-tags</i>).</p>

**Returns:**

* A CBOR object where the object <code>cborObj</code> is given the tag
 <code>bigintTag</code>.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>bigintTag</code> is less than 0 or
 greater than 2^64-1.

* <code>NullPointerException</code> - The parameter <code>bigintTag</code> is null.

### FromObjectAndTag
    @Deprecated public static CBORObject FromObjectAndTag(Object valueObValue, com.upokecenter.numbers.EInteger bigintTag)
Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag in addition to its existing tags (the new tag is made the
 outermost tag).

**Parameters:**

* <code>valueObValue</code> - An arbitrary object, which can be null.

* <code>bigintTag</code> - Tag number.

**Returns:**

* A CBOR object where the object <code>valueObValue</code> is given the tag
 <code>bigintTag</code>.

### WithTag
    public CBORObject WithTag(int smallTag)
Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag in addition to its existing tags (the new tag is made the
 outermost tag).

**Parameters:**

* <code>smallTag</code> - <p>A 32-bit integer that specifies a tag number. The tag
 number 55799 can be used to mark a "self-described CBOR" object. This
 document does not attempt to list all CBOR tags and their meanings. An
 up-to-date list can be found at the CBOR Tags registry maintained by the
 Internet Assigned Numbers Authority (<i>iana.org/assignments/cbor-tags</i>
).</p>

**Returns:**

* A CBOR object with the same value as this one but given the tag
 <code>smallTag</code> in addition to its existing tags (the new tag is made the
 outermost tag).

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>smallTag</code> is less than 0.

### FromCBORObjectAndTag
    public static CBORObject FromCBORObjectAndTag(CBORObject cborObj, int smallTag)
Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag in addition to its existing tags (the new tag is made the
 outermost tag).

**Parameters:**

* <code>cborObj</code> - <p>The parameter <code>cborObj</code> is a CBORObject.
 </p><p><b>NOTE:</b> For security reasons, whenever possible, an application
 should not base this parameter on user input or other externally supplied
 data, and whenever possible, the application should limit this parameter's
 inputs to types specially handled by this method (such as <code>int</code> or
 <code>string</code>) and/or to plain-old-data types (POCO or POJO types) within
 the control of the application. If the plain-old-data type references other
 data types, those types should likewise meet either criterion above.</p>.

* <code>smallTag</code> - <p>A 32-bit integer that specifies a tag number. The tag
 number 55799 can be used to mark a "self-described CBOR" object. This
 document does not attempt to list all CBOR tags and their meanings. An
 up-to-date list can be found at the CBOR Tags registry maintained by the
 Internet Assigned Numbers Authority (<i>iana.org/assignments/cbor-tags</i>
).</p>

**Returns:**

* A CBOR object where the object <code>cborObj</code> is given the tag
 <code>smallTag</code>. If "valueOb" is null, returns a version of CBORObject.Null
 with the given tag.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>smallTag</code> is less than 0.

### FromObjectAndTag
    @Deprecated public static CBORObject FromObjectAndTag(Object valueObValue, int smallTag)
Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag in addition to its existing tags (the new tag is made the
 outermost tag).

**Parameters:**

* <code>valueObValue</code> - The parameter, an arbitrary object, which can be null.

* <code>smallTag</code> - A 32-bit integer that specifies a tag number.

**Returns:**

* A CBOR object where the object <code>valueObValue</code> is given the tag
 <code>smallTag</code>.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>smallTag</code> is less than 0.

### FromSimpleValue
    public static CBORObject FromSimpleValue(int simpleValue)
Creates a CBOR object from a simple value number.

**Parameters:**

* <code>simpleValue</code> - The parameter <code>simpleValue</code> is a 32-bit signed
 integer.

**Returns:**

* A CBOR object.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>simpleValue</code> is less than 0,
 greater than 255, or from 24 through 31.

### NewArray
    public static CBORObject NewArray()
Creates a new empty CBOR array.

**Returns:**

* A new CBOR array.

### NewMap
    public static CBORObject NewMap()
Creates a new empty CBOR map that stores its keys in an undefined order.

**Returns:**

* A new CBOR map.

### FromMap
    public static CBORObject FromMap(Iterable<Map.Entry<CBORObject,CBORObject>> keysAndValues)
Creates a new CBOR map that stores its keys in an undefined order.

**Parameters:**

* <code>keysAndValues</code> - A sequence of key-value pairs.

**Returns:**

* A new CBOR map.

### NewOrderedMap
    public static CBORObject NewOrderedMap()
Creates a new empty CBOR map that ensures that keys are stored in the order
 in which they are first inserted.

**Returns:**

* A new CBOR map.

### FromOrderedMap
    public static CBORObject FromOrderedMap(Iterable<Map.Entry<CBORObject,CBORObject>> keysAndValues)
Creates a new CBOR map that ensures that keys are stored in order.

**Parameters:**

* <code>keysAndValues</code> - A sequence of key-value pairs.

**Returns:**

* A new CBOR map.

### ReadSequence
    public static CBORObject[] ReadSequence(InputStream stream) throws IOException
Reads a sequence of objects in CBOR format from a data stream. This method
 will read CBOR objects from the stream until the end of the stream is
 reached or an error occurs, whichever happens first.

**Parameters:**

* <code>stream</code> - A readable data stream.

**Returns:**

* An array containing the CBOR objects that were read from the data
 stream. Returns an empty array if there is no unread data in the stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null, or the
 parameter "options" is null.

* <code>CBORException</code> - There was an error in reading or
 parsing the data, including if the last CBOR object was read only partially.

* <code>IOException</code>

### ReadSequence
    public static CBORObject[] ReadSequence(InputStream stream, CBOREncodeOptions options) throws IOException
Reads a sequence of objects in CBOR format from a data stream. This method
 will read CBOR objects from the stream until the end of the stream is
 reached or an error occurs, whichever happens first.

**Parameters:**

* <code>stream</code> - A readable data stream.

* <code>options</code> - Specifies the options to use when decoding the CBOR data
 stream. See CBOREncodeOptions for more information. In this method, the
 AllowEmpty property is treated as set regardless of the value of that
 property specified in this parameter.

**Returns:**

* An array containing the CBOR objects that were read from the data
 stream. Returns an empty array if there is no unread data in the stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null, or the
 parameter <code>options</code> is null.

* <code>CBORException</code> - There was an error in reading or
 parsing the data, including if the last CBOR object was read only partially.

* <code>IOException</code>

### Read
    public static CBORObject Read(InputStream stream)
Reads an object in CBOR format from a data stream. This method will read
 from the stream until the end of the CBOR object is reached or an error
 occurs, whichever happens first.

**Parameters:**

* <code>stream</code> - A readable data stream.

**Returns:**

* A CBOR object that was read.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>CBORException</code> - There was an error in reading or
 parsing the data.

### Read
    public static CBORObject Read(InputStream stream, CBOREncodeOptions options)
Reads an object in CBOR format from a data stream, using the specified
 options to control the decoding process. This method will read from the
 stream until the end of the CBOR object is reached or an error occurs,
 whichever happens first.

**Parameters:**

* <code>stream</code> - A readable data stream.

* <code>options</code> - Specifies the options to use when decoding the CBOR data
 stream. See CBOREncodeOptions for more information.

**Returns:**

* A CBOR object that was read.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>CBORException</code> - There was an error in reading or
 parsing the data.

### ReadJSON
    public static CBORObject ReadJSON(InputStream stream) throws IOException
Generates a CBOR object from a data stream in JavaScript object Notation
 (JSON) format. The JSON stream may begin with a byte-order mark (U+FEFF).
 Since version 2.0, the JSON stream can be in UTF-8, UTF-16, or UTF-32
 encoding; the encoding is detected by assuming that the first character read
 must be a byte-order mark or a nonzero basic character (U+0001 to U+007F).
 (In previous versions, only UTF-8 was allowed.). (This behavior may change
 to supporting only UTF-8, with or without a byte order mark, in version 5.0
 or later, perhaps with an option to restore the previous behavior of also
 supporting UTF-16 and UTF-32.).

**Parameters:**

* <code>stream</code> - A readable data stream. The sequence of bytes read from the
 data stream must contain a single JSON object and not multiple objects.

**Returns:**

* A CBOR object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

* <code>CBORException</code> - The data stream contains invalid
 encoding or is not in JSON format.

### ReadJSONSequence
    public static CBORObject[] ReadJSONSequence(InputStream stream) throws IOException
<p>Generates a list of CBOR objects from a data stream in JavaScript object
 Notation (JSON) text sequence format (RFC 7464). The data stream must be in
 UTF-8 encoding and may not begin with a byte-order mark (U+FEFF).</p>
 <p>Generally, each JSON text in a JSON text sequence is written as follows:
 Write a record separator byte (0x1e), then write the JSON text in UTF-8
 (without a byte order mark, U+FEFF), then write the line feed byte (0x0a).
 RFC 7464, however, uses a more liberal syntax for parsing JSON text
 sequences.</p>

**Parameters:**

* <code>stream</code> - A readable data stream. The sequence of bytes read from the
 data stream must either be empty or begin with a record separator byte
 (0x1e).

**Returns:**

* A list of CBOR objects read from the JSON sequence. Objects that
 could not be parsed are replaced with <code>null</code> (as opposed to <code>
 CBORObject.Null</code>) in the given list.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

* <code>CBORException</code> - The data stream is not empty and
 does not begin with a record separator byte (0x1e).

### ReadJSONSequence
    public static CBORObject[] ReadJSONSequence(InputStream stream, JSONOptions jsonoptions) throws IOException
<p>Generates a list of CBOR objects from a data stream in JavaScript object
 Notation (JSON) text sequence format (RFC 7464). The data stream must be in
 UTF-8 encoding and may not begin with a byte-order mark (U+FEFF).</p>
 <p>Generally, each JSON text in a JSON text sequence is written as follows:
 Write a record separator byte (0x1e), then write the JSON text in UTF-8
 (without a byte order mark, U+FEFF), then write the line feed byte (0x0a).
 RFC 7464, however, uses a more liberal syntax for parsing JSON text
 sequences.</p>

**Parameters:**

* <code>stream</code> - A readable data stream. The sequence of bytes read from the
 data stream must either be empty or begin with a record separator byte
 (0x1e).

* <code>jsonoptions</code> - Specifies options to control how JSON texts in the stream
 are decoded to CBOR. See the JSONOptions class.

**Returns:**

* A list of CBOR objects read from the JSON sequence. Objects that
 could not be parsed are replaced with <code>null</code> (as opposed to <code>
 CBORObject.Null</code>) in the given list.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

* <code>CBORException</code> - The data stream is not empty and
 does not begin with a record separator byte (0x1e).

### ReadJSON
    public static CBORObject ReadJSON(InputStream stream, JSONOptions jsonoptions) throws IOException
Generates a CBOR object from a data stream in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.
 The JSON stream may begin with a byte-order mark (U+FEFF). Since version
 2.0, the JSON stream can be in UTF-8, UTF-16, or UTF-32 encoding; the
 encoding is detected by assuming that the first character read must be a
 byte-order mark or a nonzero basic character (U+0001 to U+007F). (In
 previous versions, only UTF-8 was allowed.). (This behavior may change to
 supporting only UTF-8, with or without a byte order mark, in version 5.0 or
 later, perhaps with an option to restore the previous behavior of also
 supporting UTF-16 and UTF-32.).

**Parameters:**

* <code>stream</code> - A readable data stream. The sequence of bytes read from the
 data stream must contain a single JSON object and not multiple objects.

* <code>jsonoptions</code> - Specifies options to control how the JSON stream is
 decoded to CBOR. See the JSONOptions class.

**Returns:**

* A CBOR object containing the JSON data decoded.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

* <code>CBORException</code> - The data stream contains invalid
 encoding or is not in JSON format.

### FromJSONBytes
    public static CBORObject FromJSONBytes(byte[] bytes)
<p>Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a CBORException
 is thrown.</p> <p>Note that if a CBOR object is converted to JSON with
 <code>ToJSONBytes</code>, then the JSON is converted back to CBOR with this
 method, the new CBOR object will not necessarily be the same as the old CBOR
 object, especially if the old CBOR object uses data types not supported in
 JSON, such as integers in map keys.</p>

**Parameters:**

* <code>bytes</code> - A byte array in JSON format. The entire byte array must contain
 a single JSON object and not multiple objects. The byte array may begin with
 a byte-order mark (U+FEFF). The byte array can be in UTF-8, UTF-16, or
 UTF-32 encoding; the encoding is detected by assuming that the first
 character read must be a byte-order mark or a nonzero basic character
 (U+0001 to U+007F). (This behavior may change to supporting only UTF-8, with
 or without a byte order mark, in version 5.0 or later, perhaps with an
 option to restore the previous behavior of also supporting UTF-16 and
 UTF-32.).

**Returns:**

* A CBOR object containing the JSON data decoded.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bytes</code> is null.

* <code>CBORException</code> - The byte array contains invalid
 encoding or is not in JSON format.

### FromJSONBytes
    public static CBORObject FromJSONBytes(byte[] bytes, JSONOptions jsonoptions)
<p>Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.
 </p> <p>Note that if a CBOR object is converted to JSON with <code>
 ToJSONBytes</code>, then the JSON is converted back to CBOR with this method, the
 new CBOR object will not necessarily be the same as the old CBOR object,
 especially if the old CBOR object uses data types not supported in JSON,
 such as integers in map keys.</p>

**Parameters:**

* <code>bytes</code> - A byte array in JSON format. The entire byte array must contain
 a single JSON object and not multiple objects. The byte array may begin with
 a byte-order mark (U+FEFF). The byte array can be in UTF-8, UTF-16, or
 UTF-32 encoding; the encoding is detected by assuming that the first
 character read must be a byte-order mark or a nonzero basic character
 (U+0001 to U+007F). (This behavior may change to supporting only UTF-8, with
 or without a byte order mark, in version 5.0 or later, perhaps with an
 option to restore the previous behavior of also supporting UTF-16 and
 UTF-32.).

* <code>jsonoptions</code> - Specifies options to control how the JSON data is decoded
 to CBOR. See the JSONOptions class.

**Returns:**

* A CBOR object containing the JSON data decoded.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bytes</code> or <code>
 jsonoptions</code> is null.

* <code>CBORException</code> - The byte array contains invalid
 encoding or is not in JSON format.

### FromJSONBytes
    public static CBORObject FromJSONBytes(byte[] bytes, int offset, int count)
<p>Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a CBORException
 is thrown.</p> <p>Note that if a CBOR object is converted to JSON with
 <code>ToJSONBytes</code>, then the JSON is converted back to CBOR with this
 method, the new CBOR object will not necessarily be the same as the old CBOR
 object, especially if the old CBOR object uses data types not supported in
 JSON, such as integers in map keys.</p>

**Parameters:**

* <code>bytes</code> - A byte array, the specified portion of which is in JSON format.
 The specified portion of the byte array must contain a single JSON object
 and not multiple objects. The portion may begin with a byte-order mark
 (U+FEFF). The portion can be in UTF-8, UTF-16, or UTF-32 encoding; the
 encoding is detected by assuming that the first character read must be a
 byte-order mark or a nonzero basic character (U+0001 to U+007F). (This
 behavior may change to supporting only UTF-8, with or without a byte order
 mark, in version 5.0 or later, perhaps with an option to restore the
 previous behavior of also supporting UTF-16 and UTF-32.).

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>bytes</code> begins.

* <code>count</code> - The length, in bytes, of the desired portion of <code>bytes</code>
 (but not more than <code>bytes</code> 's length).

**Returns:**

* A CBOR object containing the JSON data decoded.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bytes</code> is null.

* <code>CBORException</code> - The byte array contains invalid
 encoding or is not in JSON format.

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
 than 0 or greater than <code>bytes</code> 's length, or <code>bytes</code> 's length
 minus <code>offset</code> is less than <code>count</code>.

### FromJSONBytes
    public static CBORObject FromJSONBytes(byte[] bytes, int offset, int count, JSONOptions jsonoptions)
<p>Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format, using the specified options to control the decoding process.
 </p> <p>Note that if a CBOR object is converted to JSON with <code>
 ToJSONBytes</code>, then the JSON is converted back to CBOR with this method, the
 new CBOR object will not necessarily be the same as the old CBOR object,
 especially if the old CBOR object uses data types not supported in JSON,
 such as integers in map keys.</p>

**Parameters:**

* <code>bytes</code> - A byte array, the specified portion of which is in JSON format.
 The specified portion of the byte array must contain a single JSON object
 and not multiple objects. The portion may begin with a byte-order mark
 (U+FEFF). The portion can be in UTF-8, UTF-16, or UTF-32 encoding; the
 encoding is detected by assuming that the first character read must be a
 byte-order mark or a nonzero basic character (U+0001 to U+007F). (This
 behavior may change to supporting only UTF-8, with or without a byte order
 mark, in version 5.0 or later, perhaps with an option to restore the
 previous behavior of also supporting UTF-16 and UTF-32.).

* <code>offset</code> - An index, starting at 0, showing where the desired portion of
 <code>bytes</code> begins.

* <code>count</code> - The length, in bytes, of the desired portion of <code>bytes</code>
 (but not more than <code>bytes</code> 's length).

* <code>jsonoptions</code> - Specifies options to control how the JSON data is decoded
 to CBOR. See the JSONOptions class.

**Returns:**

* A CBOR object containing the JSON data decoded.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bytes</code> or <code>
 jsonoptions</code> is null.

* <code>CBORException</code> - The byte array contains invalid
 encoding or is not in JSON format.

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
 than 0 or greater than <code>bytes</code> 's length, or <code>bytes</code> 's length
 minus <code>offset</code> is less than <code>count</code>.

### Write
    public static void Write(String str, OutputStream stream) throws IOException
Writes a text string in CBOR format to a data stream. The string will be
 encoded using definite-length encoding regardless of its length.

**Parameters:**

* <code>str</code> - The string to write. Can be null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(String str, OutputStream stream, CBOREncodeOptions options) throws IOException
Writes a text string in CBOR format to a data stream, using the given
 options to control the encoding process.

**Parameters:**

* <code>str</code> - The string to write. Can be null.

* <code>stream</code> - A writable data stream.

* <code>options</code> - Options for encoding the data to CBOR.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(com.upokecenter.numbers.EFloat bignum, OutputStream stream) throws IOException
Writes a binary floating-point number in CBOR format to a data stream, as
 though it were converted to a CBOR object via CBORObject.FromEFloat(EFloat)
 and then written out.

**Parameters:**

* <code>bignum</code> - An arbitrary-precision binary floating-point number. Can be
 null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(com.upokecenter.numbers.ERational rational, OutputStream stream) throws IOException
Writes a rational number in CBOR format to a data stream, as though it were
 converted to a CBOR object via CBORObject.FromERational(ERational) and then
 written out.

**Parameters:**

* <code>rational</code> - An arbitrary-precision rational number. Can be null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(com.upokecenter.numbers.EDecimal bignum, OutputStream stream) throws IOException
Writes a decimal floating-point number in CBOR format to a data stream, as
 though it were converted to a CBOR object via
 CBORObject.FromEDecimal(EDecimal) and then written out.

**Parameters:**

* <code>bignum</code> - The arbitrary-precision decimal number to write. Can be null.

* <code>stream</code> - InputStream to write to.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(com.upokecenter.numbers.EInteger bigint, OutputStream stream) throws IOException
Writes a arbitrary-precision integer in CBOR format to a data stream.

**Parameters:**

* <code>bigint</code> - Arbitrary-precision integer to write. Can be null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(long value, OutputStream stream) throws IOException
Writes a 64-bit signed integer in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(int value, OutputStream stream) throws IOException
Writes a 32-bit signed integer in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(short value, OutputStream stream) throws IOException
Writes a 16-bit signed integer in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(boolean value, OutputStream stream) throws IOException
Writes a Boolean value in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(byte value, OutputStream stream) throws IOException
Writes a byte (0 to 255) in CBOR format to a data stream. If the value is
 less than 24, writes that byte. If the value is 25 to 255, writes the byte
 24, then this byte's value.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(float value, OutputStream stream) throws IOException
Writes a 32-bit floating-point number in CBOR format to a data stream. The
 number is written using the shortest floating-point encoding possible; this
 is a change from previous versions.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(double value, OutputStream stream) throws IOException
Writes a 64-bit floating-point number in CBOR format to a data stream. The
 number is written using the shortest floating-point encoding possible; this
 is a change from previous versions.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write(CBORObject value, OutputStream stream) throws IOException
Writes a CBOR object to a CBOR data stream.

**Parameters:**

* <code>value</code> - The value to write. Can be null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code>

### Write
    public static void Write(Object objValue, OutputStream stream) throws IOException
Writes a CBOR object to a CBOR data stream. See the three-parameter Write
 method that takes a CBOREncodeOptions.

**Parameters:**

* <code>objValue</code> - The arbitrary object to be serialized. Can be null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(Object objValue, OutputStream output, CBOREncodeOptions options) throws IOException
<p>Writes an arbitrary object to a CBOR data stream, using the specified
 options for controlling how the object is encoded to CBOR data format. If
 the object is convertible to a CBOR map or a CBOR object that contains CBOR
 maps, the order in which the keys to those maps are written out to the data
 stream is undefined unless the map was created using the NewOrderedMap
 method. The example code given in <code>
 com.upokecenter.cbor.CBORObject.WriteTo(System.IO.InputStream)</code> can be used to
 write out certain keys of a CBOR map in a given order. Currently, the
 following objects are supported: </p> <ul> <li>Lists of
 CBORObject.</li><li>Maps of CBORObject. The order in which the keys to the
 map are written out to the data stream is undefined unless the map was
 created using the NewOrderedMap method.</li><li>Null.</li><li>Byte arrays,
 which will always be written as definite-length byte strings.</li><li>string
 objects. The strings will be encoded using definite-length encoding
 regardless of their length.</li><li>Any object accepted by the FromObject
 method.</li></ul>

**Parameters:**

* <code>objValue</code> - <p>The arbitrary object to be serialized. Can be null.
 </p><p><b>NOTE:</b> For security reasons, whenever possible, an application
 should not base this parameter on user input or other externally supplied
 data, and whenever possible, the application should limit this parameter's
 inputs to types specially handled by this method (such as <code>int</code> or
 <code>string</code>) and/or to plain-old-data types (POCO or POJO types) within
 the control of the application. If the plain-old-data type references other
 data types, those types should likewise meet either criterion above.</p>.

* <code>output</code> - A writable data stream.

* <code>options</code> - CBOR options for encoding the CBOR object to bytes.

**Throws:**

* <code>IllegalArgumentException</code> - The object's type is not supported.

* <code>NullPointerException</code> - The parameter <code>options</code> or <code>
 output</code> is null.

* <code>IOException</code>

### WriteJSON
    public static void WriteJSON(Object obj, OutputStream outputStream) throws IOException
<p>Converts an arbitrary object to a text string in JavaScript object
 Notation (JSON) format, as in the ToJSONString method, and writes that
 string to a data stream in UTF-8. If the object is convertible to a CBOR
 map, or to a CBOR object that contains CBOR maps, the order in which the
 keys to those maps are written out to the JSON string is undefined unless
 the map was created using the NewOrderedMap method. The example code given
 in <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b> can
 be used to write out certain keys of a CBOR map in a given order to a JSON
 string.</p>

**Parameters:**

* <code>obj</code> - <p>The parameter <code>obj</code> is an arbitrary object. Can be null.
 </p><p><b>NOTE:</b> For security reasons, whenever possible, an application
 should not base this parameter on user input or other externally supplied
 data, and whenever possible, the application should limit this parameter's
 inputs to types specially handled by this method (such as <code>int</code> or
 <code>string</code>) and/or to plain-old-data types (POCO or POJO types) within
 the control of the application. If the plain-old-data type references other
 data types, those types should likewise meet either criterion above.</p>.

* <code>outputStream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

* <code>IOException</code>

### Add
    public CBORObject Add(Object key, Object valueOb)
<p>Adds a new key and its value to this CBOR map, or adds the value if the
 key doesn't exist.</p> <p>NOTE: This method can't be used to add a tag to an
 existing CBOR object. To create a CBOR object with a given tag, call the
 <code>CBORObject.FromCBORObjectAndTag</code> method and pass the CBOR object and
 the desired tag number to that method.</p>

**Parameters:**

* <code>key</code> - An object representing the key, which will be converted to a
 CBORObject. Can be null, in which case this value is converted to
 CBORObject.Null.

* <code>valueOb</code> - An object representing the value, which will be converted to
 a CBORObject. Can be null, in which case this value is converted to
 CBORObject.Null.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>key</code> already exists in this
 map.

* <code>IllegalStateException</code> - This object is not a map.

* <code>IllegalArgumentException</code> - The parameter <code>key</code> or <code>valueOb</code> has
 an unsupported type.

### Add
    public CBORObject Add(CBORObject obj)
<p>Adds a new object to the end of this array. (Used to throw
 NullPointerException on a null reference, but now converts the null
 reference to CBORObject.Null, for convenience with the object overload of
 this method).</p> <p>NOTE: This method can't be used to add a tag to an
 existing CBOR object. To create a CBOR object with a given tag, call the
 <code>CBORObject.FromCBORObjectAndTag</code> method and pass the CBOR object and
 the desired tag number to that method.</p><p>The following example creates a
 CBOR array and adds several CBOR objects, one of which has a custom CBOR
 tag, to that array. Note the chaining behavior made possible by this
 method.</p> <pre>CBORObject obj = CBORObject.NewArray()
 .Add(CBORObject.False) .Add(CBORObject.FromObject(5))
 .Add(CBORObject.FromObject("text string"))
 .Add(CBORObject.FromCBORObjectAndTag(9999, 1));</pre> .

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is a CBOR object.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This object is not an array.

### Add
    public CBORObject Add(Object obj)
<p>Converts an object to a CBOR object and adds it to the end of this
 array.</p> <p>NOTE: This method can't be used to add a tag to an existing
 CBOR object. To create a CBOR object with a given tag, call the <code>
 CBORObject.FromCBORObjectAndTag</code> method and pass the CBOR object and the
 desired tag number to that method.</p><p>The following example creates a
 CBOR array and adds several CBOR objects, one of which has a custom CBOR
 tag, to that array. Note the chaining behavior made possible by this
 method.</p> <pre>CBORObject obj = CBORObject.NewArray()
 .Add(CBORObject.False) .Add(5) .Add("text string")
 .Add(CBORObject.FromCBORObjectAndTag(9999, 1));</pre> .

**Parameters:**

* <code>obj</code> - A CBOR object (or an object convertible to a CBOR object) to add
 to this CBOR array.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This instance is not an array.

* <code>IllegalArgumentException</code> - The type of <code>obj</code> is not supported.

### AsBoolean
    public boolean AsBoolean()
Returns false if this object is a CBOR false, null, or undefined value
 (whether or not the object has tags); otherwise, true.

**Returns:**

* False if this object is a CBOR false, null, or undefined value;
 otherwise, true.

### AsDouble
    public double AsDouble()
Converts this object to a 64-bit floating point number.

**Returns:**

* The closest 64-bit floating point number to this object. The return
 value can be positive infinity or negative infinity if this value exceeds
 the range of a 64-bit floating point number.

**Throws:**

* <code>IllegalStateException</code> - This object does not represent a number (for
 this purpose, infinities and not-a-number or NaN values, but not
 CBORObject.Null, are considered numbers).

### AsInt32Value
    public int AsInt32Value()
<p>Converts this object to a 32-bit signed integer if this CBOR object's
 type is Integer. This method disregards the tags this object has, if
 any.</p><p>The following example code (originally written in C# for the.NET
 Framework) shows a way to check whether a given CBOR object stores a 32-bit
 signed integer before getting its value.</p> <pre>CBORObject obj =
 CBORObject.FromInt32(99999); if (obj.CanValueFitInInt32()) { /* Not an
 Int32; handle the error */ System.out.println("Not a 32-bit integer."); }
 else { System.out.println("The value is " + obj.AsInt32Value()); }</pre> .

**Returns:**

* The 32-bit signed integer stored by this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not <code>
 CBORType.Integer</code>.

* <code>ArithmeticException</code> - This object's value exceeds the range of a 32-bit
 signed integer.

### AsInt64Value
    public long AsInt64Value()
<p>Converts this object to a 64-bit signed integer if this CBOR object's
 type is Integer. This method disregards the tags this object has, if
 any.</p><p>The following example code (originally written in C# for the.NET
 Framework) shows a way to check whether a given CBOR object stores a 64-bit
 signed integer before getting its value.</p> <pre>CBORObject obj =
 CBORObject.FromInt64(99999); if (obj.CanValueFitInInt64()) { /* Not an
 Int64; handle the error*/ System.out.println("Not a 64-bit integer."); }
 else { System.out.println("The value is " + obj.AsInt64Value()); }</pre> .

**Returns:**

* The 64-bit signed integer stored by this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not <code>
 CBORType.Integer</code>.

* <code>ArithmeticException</code> - This object's value exceeds the range of a 64-bit
 signed integer.

### CanValueFitInInt64
    public boolean CanValueFitInInt64()
Returns whether this CBOR object stores an integer (CBORType.Integer) within
 the range of a 64-bit signed integer. This method disregards the tags this
 object has, if any.

**Returns:**

* <code>true</code> if this CBOR object stores an integer
 (CBORType.Integer) whose value is at least -(2^63) and less than 2^63;
 otherwise, <code>false</code>.

### CanValueFitInInt32
    public boolean CanValueFitInInt32()
Returns whether this CBOR object stores an integer (CBORType.Integer) within
 the range of a 32-bit signed integer. This method disregards the tags this
 object has, if any.

**Returns:**

* <code>true</code> if this CBOR object stores an integer
 (CBORType.Integer) whose value is at least -(2^31) and less than 2^31;
 otherwise, <code>false</code>.

### AsEIntegerValue
    public com.upokecenter.numbers.EInteger AsEIntegerValue()
Converts this object to an arbitrary-precision integer if this CBOR object's
 type is Integer. This method disregards the tags this object has, if any.
 (Note that CBOR stores untagged integers at least -(2^64) and less than
 2^64.).

**Returns:**

* The integer stored by this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not <code>
 CBORType.Integer</code>.

### AsDoubleBits
    public long AsDoubleBits()
Converts this object to the bits of a 64-bit floating-point number if this
 CBOR object's type is FloatingPoint. This method disregards the tags this
 object has, if any.

**Returns:**

* The bits of a 64-bit floating-point number stored by this object.
 The most significant bit is the sign (set means negative, clear means
 nonnegative); the next most significant 11 bits are the exponent area; and
 the remaining bits are the significand area. If all the bits of the exponent
 area are set and the significand area is 0, this indicates infinity. If all
 the bits of the exponent area are set and the significand area is other than
 0, this indicates not-a-number (NaN).

**Throws:**

* <code>IllegalStateException</code> - This object's type is not <code>
 CBORType.FloatingPoint</code>.

### AsDoubleValue
    public double AsDoubleValue()
Converts this object to a 64-bit floating-point number if this CBOR object's
 type is FloatingPoint. This method disregards the tags this object has, if
 any.

**Returns:**

* The 64-bit floating-point number stored by this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not <code>
 CBORType.FloatingPoint</code>.

### AsNumber
    public CBORNumber AsNumber()
<p>Converts this object to a CBOR number. (NOTE: To determine whether this
 method call can succeed, call the <b>IsNumber</b> property (isNumber()
 method in Java) before calling this method.).</p>

**Returns:**

* The number represented by this object.

**Throws:**

* <code>IllegalStateException</code> - This object does not represent a number (for
 this purpose, infinities and not-a-number or NaN values, but not
 CBORObject.Null, are considered numbers).

### AsInt32
    public int AsInt32()
<p>Converts this object to a 32-bit signed integer. Non-integer number
 values are converted to integers by discarding their fractional parts.
 (NOTE: To determine whether this method call can succeed, call
 <b>AsNumber().getCanTruncatedIntFitInInt32()</b> before calling this method. See
 the example.).</p><p>The following example code (originally written in C#
 for the.NET Framework) shows a way to check whether a given CBOR object
 stores a 32-bit signed integer before getting its value.</p> <pre>CBORObject
 obj = CBORObject.FromInt32(99999); if
 (obj.AsNumber().CanTruncatedIntFitInInt32()) { /* Not an Int32; handle the
 error */ System.out.println("Not a 32-bit integer."); } else {
 System.out.println("The value is " + obj.AsInt32()); }</pre> .

**Returns:**

* The closest 32-bit signed integer to this object.

**Throws:**

* <code>IllegalStateException</code> - This object does not represent a number (for
 this purpose, infinities and not-a-number or NaN values, but not
 CBORObject.Null, are considered numbers).

* <code>ArithmeticException</code> - This object's value exceeds the range of a 32-bit
 signed integer.

### AsSingle
    public float AsSingle()
Converts this object to a 32-bit floating point number.

**Returns:**

* The closest 32-bit floating point number to this object. The return
 value can be positive infinity or negative infinity if this object's value
 exceeds the range of a 32-bit floating point number.

**Throws:**

* <code>IllegalStateException</code> - This object does not represent a number (for
 this purpose, infinities and not-a-number or NaN values, but not
 CBORObject.Null, are considered numbers).

### AsGuid
    public UUID AsGuid()
Converts this object to a java.util.UUID.

**Returns:**

* A java.util.UUID.

**Throws:**

* <code>IllegalStateException</code> - This object does not represent a java.util.UUID.

* <code>CBORException</code> - This object does not have the expected tag.

### AsString
    public String AsString()
<p>Gets the value of this object as a text string.</p> <p>This method is not
 the "reverse" of the <code>FromString</code> method in the sense that FromString
 can take either a text string or <code>null</code>, but this method can accept
 only text strings. The <code>ToObject</code> method is closer to a "reverse"
 version to <code>FromString</code> than the <code>AsString</code> method: <code>
 ToObject&amp;lt;string&amp;gt;(cbor)</code> in DotNet, or <code>ToObject(string.class)</code>
 in Java, will convert a CBOR object to a DotNet or Java string if it
 represents a text string, or to <code>null</code> if <code>IsNull</code> returns
 <code>true</code> for the CBOR object, and will fail in other cases.</p>

**Returns:**

* Gets this object's string.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a text string (for
 the purposes of this method, infinity and not-a-number values, but not
 <code>CBORObject.Null</code>, are considered numbers). To check the CBOR object
 for null before conversion, use the following idiom (originally written in
 C# for the.NET version): <code>(cbor == null || cbor.isNull()) ? null :
 cbor.AsString()</code>.

### compareTo
    public int compareTo(CBORObject other)
<p>Compares two CBOR objects. This implementation was changed in version
 4.0. </p><p>In this implementation:</p> <ul> <li>The null pointer (null
 reference) is considered less than any other object.</li><li>If the two
 objects are both integers (CBORType.Integer) both floating-point values,
 both byte strings, both simple values (including True and False), or both
 text strings, their CBOR encodings (as though EncodeToBytes were called on
 each integer) are compared as though by a byte-by-byte comparison. (This
 means, for example, that positive integers sort before negative
 integers).</li><li>If both objects have a tag, they are compared first by
 the tag's value then by the associated item (which itself can have a
 tag).</li><li>If both objects are arrays, they are compared item by item. In
 this case, if the arrays have different numbers of items, the array with
 more items is treated as greater than the other array.</li><li>If both
 objects are maps, their key-value pairs, sorted by key in accordance with
 this method, are compared, where each pair is compared first by key and then
 by value. In this case, if the maps have different numbers of key-value
 pairs, the map with more pairs is treated as greater than the other
 map.</li><li>If the two objects have different types, the object whose type
 comes first in the order of untagged integers, untagged byte strings,
 untagged text strings, untagged arrays, untagged maps, tagged objects,
 untagged simple values (including True and False) and untagged floating
 point values sorts before the other object.</li></ul> <p>This method is
 consistent with the Equals method.</p>

**Specified by:**

* <code>compareTo</code> in interface <code>Comparable&lt;CBORObject&gt;</code>

**Parameters:**

* <code>other</code> - A value to compare with.

**Returns:**

* A negative number, if this value is less than the other object; or
 0, if both values are equal; or a positive number, if this value is less
 than the other object or if the other object is null. This implementation
 returns a positive number if.

### CompareToIgnoreTags
    public int CompareToIgnoreTags(CBORObject other)
Compares this object and another CBOR object, ignoring the tags they have,
 if any. See the compareTo method for more information on the comparison
 function.

**Parameters:**

* <code>other</code> - A value to compare with.

**Returns:**

* Less than 0, if this value is less than the other object; or 0, if
 both values are equal; or greater than 0, if this value is less than the
 other object or if the other object is null.

### ContainsKey
    public boolean ContainsKey(Object objKey)
Determines whether a value of the given key exists in this object.

**Parameters:**

* <code>objKey</code> - The parameter <code>objKey</code> is an arbitrary object.

**Returns:**

* <code>true</code> if the given key is found, or <code>false</code> if the
 given key is not found or this object is not a map.

### ContainsKey
    public boolean ContainsKey(CBORObject key)
Determines whether a value of the given key exists in this object.

**Parameters:**

* <code>key</code> - An object that serves as the key. If this is <code>null</code>, checks
 for <code>CBORObject.Null</code>.

**Returns:**

* <code>true</code> if the given key is found, or <code>false</code> if the
 given key is not found or this object is not a map.

### ContainsKey
    public boolean ContainsKey(String key)
Determines whether a value of the given key exists in this object.

**Parameters:**

* <code>key</code> - A text string that serves as the key. If this is <code>null</code>,
 checks for <code>CBORObject.Null</code>.

**Returns:**

* <code>true</code> if the given key (as a CBOR object) is found, or <code>
 false</code> if the given key is not found or this object is not a map.

### EncodeToBytes
    public byte[] EncodeToBytes()
Writes the binary representation of this CBOR object and returns a byte
 array of that representation. If the CBOR object contains CBOR maps, or is a
 CBOR map itself, the order in which the keys to the map are written out to
 the byte array is undefined unless the map was created using the
 NewOrderedMap method. The example code given in <code>
 com.upokecenter.cbor.CBORObject.WriteTo(System.IO.InputStream)</code> can be used to
 write out certain keys of a CBOR map in a given order. For the CTAP2 (FIDO
 Client-to-Authenticator Protocol 2) canonical ordering, which is useful for
 implementing Web Authentication, call <code>EncodeToBytes(new
 CBOREncodeOptions("ctap2canonical=true"))</code> rather than this method.

**Returns:**

* A byte array in CBOR format.

### EncodeToBytes
    public byte[] EncodeToBytes(CBOREncodeOptions options)
Writes the binary representation of this CBOR object and returns a byte
 array of that representation, using the specified options for encoding the
 object to CBOR format. For the CTAP2 (FIDO Client-to-Authenticator Protocol
 2) canonical ordering, which is useful for implementing Web Authentication,
 call this method as follows: <code>EncodeToBytes(new
 CBOREncodeOptions("ctap2canonical=true"))</code>.

**Parameters:**

* <code>options</code> - Options for encoding the data to CBOR.

**Returns:**

* A byte array in CBOR format.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>options</code> is null.

### AtJSONPointer
    public CBORObject AtJSONPointer(String pointer)
Gets the CBOR object referred to by a JSON Pointer according to RFC6901. For
 more information, see the overload taking a default value parameter.

**Parameters:**

* <code>pointer</code> - A JSON pointer according to RFC 6901.

**Returns:**

* An object within this CBOR object. Returns this object if pointer is
 the empty string (even if this object has a CBOR type other than array or
 map).

**Throws:**

* <code>CBORException</code> - Thrown if the pointer is null, or
 if the pointer is invalid, or if there is no object at the given pointer, or
 the special key "-" appears in the pointer in the context of an array (not a
 map), or if the pointer is non-empty and this object has a CBOR type other
 than array or map.

### AtJSONPointer
    public CBORObject AtJSONPointer(String pointer, CBORObject defaultValue)
<p>Gets the CBOR object referred to by a JSON Pointer according to RFC6901,
 or a default value if the operation fails. The syntax for a JSON Pointer is:
 </p><pre>'/' KEY '/' KEY.get(...)</pre> where KEY represents a key into the
 JSON object or its sub-objects in the hierarchy. For example,
 <pre>/foo/2/bar</pre> means the same as <pre>obj.get('foo')[2]['bar']</pre>
 in JavaScript. If "~" and/or "/" occurs in a key, it must be escaped with
 "~0" or "~1", respectively, in a JSON pointer. JSON pointers also support
 the special key "-" (as in "/foo/-") to indicate the end of an array, but
 this method treats this key as an error since it refers to a nonexistent
 item. Indices to arrays (such as 2 in the example) must contain only basic
 digits 0 to 9 and no leading zeros. (Note that RFC 6901 was published before
 JSON was extended to support top-level values other than arrays and
 key-value dictionaries.).

**Parameters:**

* <code>pointer</code> - A JSON pointer according to RFC 6901.

* <code>defaultValue</code> - The parameter <code>defaultValue</code> is a Cbor.CBORObject
 object.

**Returns:**

* An object within the specified JSON object. Returns this object if
 pointer is the empty string (even if this object has a CBOR type other than
 array or map). Returns <code>defaultValue</code> if the pointer is null, or if
 the pointer is invalid, or if there is no object at the given pointer, or
 the special key "-" appears in the pointer in the context of an array (not a
 map), or if the pointer is non-empty and this object has a CBOR type other
 than array or map.

### ApplyJSONPatch
    public CBORObject ApplyJSONPatch(CBORObject patch)
<p>Returns a copy of this object after applying the operations in a JSON
 patch, in the form of a CBOR object. JSON patches are specified in RFC 6902
 and their format is summarized in the remarks below.</p> <p><b>Remarks:</b>
 A JSON patch is an array with one or more maps. Each map has the following
 keys: </p> <ul> <li>"op" - Required. This key's value is the patch operation
 and must be "add", "remove", "move", "copy", "test", or "replace", in basic
 lowercase letters and no other case combination.</li><li>"value" - Required
 if the operation is "add", "replace", or "test" and specifies the item to
 add (insert), or that will replace the existing item, or to check an
 existing item for equality, respectively. (For "test", the operation fails
 if the existing item doesn't match the specified value.)</li><li>"path" -
 Required for all operations. A JSON Pointer (RFC 6901) specifying the
 destination path in the CBOR object for the operation. For more information,
 see RFC 6901 or the documentation for AtJSONPointer(pointer,
 defaultValue).</li><li>"from" - Required if the operation is "move" or
 "copy". A JSON Pointer (RFC 6901) specifying the path in the CBOR object
 where the source value is located.</li></ul>

**Parameters:**

* <code>patch</code> - A JSON patch in the form of a CBOR object; it has the form
 summarized in the remarks.

**Returns:**

* The result of the patch operation.

**Throws:**

* <code>CBORException</code> - The parameter <code>patch</code> is
 null or the patch operation failed.

### equals
    public boolean equals(Object obj)
Determines whether this object and another object are equal and have the
 same type. Not-a-number values can be considered equal by this method.

**Overrides:**

* <code>equals</code> in class <code>Object</code>

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is an arbitrary object.

**Returns:**

* <code>true</code> if the objects are equal; otherwise, <code>false</code>. In
 this method, two objects are not equal if they don't have the same type or
 if one is null and the other isn't.

### equals
    public boolean equals(CBORObject other)
Compares the equality of two CBOR objects. Not-a-number values can be
 considered equal by this method.

**Parameters:**

* <code>other</code> - The object to compare.

**Returns:**

* <code>true</code> if the objects are equal; otherwise, <code>false</code>. In
 this method, two objects are not equal if they don't have the same type or
 if one is null and the other isn't.

### GetByteString
    public byte[] GetByteString()
Gets the backing byte array used in this CBOR object, if this object is a
 byte string, without copying the data to a new byte array. Any changes in
 the returned array's contents will be reflected in this CBOR object. Note,
 though, that the array's length can't be changed.

**Returns:**

* The byte array held by this CBOR object.

**Throws:**

* <code>IllegalStateException</code> - This object is not a byte string.

### hashCode
    public int hashCode()
Calculates the hash code of this object. The hash code for a given instance
 of this class is not guaranteed to be the same across versions of this
 class, and no application or process IDs are used in the hash code
 calculation.

**Overrides:**

* <code>hashCode</code> in class <code>Object</code>

**Returns:**

* A 32-bit hash code.

### GetAllTags
    public com.upokecenter.numbers.EInteger[] GetAllTags()
Gets a list of all tags, from outermost to innermost.

**Returns:**

* An array of tags, or the empty string if this object is untagged.

### HasOneTag
    public boolean HasOneTag()
Returns whether this object has only one tag.

**Returns:**

* <code>true</code> if this object has only one tag; otherwise, <code>
 false</code>.

### HasOneTag
    public boolean HasOneTag(int tagValue)
Returns whether this object has only one tag and that tag is the given
 number.

**Parameters:**

* <code>tagValue</code> - The tag number.

**Returns:**

* <code>true</code> if this object has only one tag and that tag is the
 given number; otherwise, <code>false</code>.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>tagValue</code> is less than 0.

### HasOneTag
    public boolean HasOneTag(com.upokecenter.numbers.EInteger bigTagValue)
Returns whether this object has only one tag and that tag is the given
 number, expressed as an arbitrary-precision integer.

**Parameters:**

* <code>bigTagValue</code> - An arbitrary-precision integer.

**Returns:**

* <code>true</code> if this object has only one tag and that tag is the
 given number; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigTagValue</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>bigTagValue</code> is less than 0.

### getTagCount
    public final int getTagCount()
Gets the number of tags this object has.

**Returns:**

* The number of tags this object has.

### HasMostInnerTag
    public boolean HasMostInnerTag(int tagValue)
Returns whether this object has an innermost tag and that tag is of the
 given number.

**Parameters:**

* <code>tagValue</code> - The tag number.

**Returns:**

* <code>true</code> if this object has an innermost tag and that tag is of
 the given number; otherwise, <code>false</code>.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>tagValue</code> is less than 0.

### HasMostInnerTag
    public boolean HasMostInnerTag(com.upokecenter.numbers.EInteger bigTagValue)
Returns whether this object has an innermost tag and that tag is of the
 given number, expressed as an arbitrary-precision number.

**Parameters:**

* <code>bigTagValue</code> - The tag number.

**Returns:**

* <code>true</code> if this object has an innermost tag and that tag is of
 the given number; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigTagValue</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>bigTagValue</code> is less than 0.

### HasMostOuterTag
    public boolean HasMostOuterTag(int tagValue)
Returns whether this object has an outermost tag and that tag is of the
 given number.

**Parameters:**

* <code>tagValue</code> - The tag number.

**Returns:**

* <code>true</code> if this object has an outermost tag and that tag is of
 the given number; otherwise, <code>false</code>.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>tagValue</code> is less than 0.

### HasMostOuterTag
    public boolean HasMostOuterTag(com.upokecenter.numbers.EInteger bigTagValue)
Returns whether this object has an outermost tag and that tag is of the
 given number.

**Parameters:**

* <code>bigTagValue</code> - The tag number.

**Returns:**

* <code>true</code> if this object has an outermost tag and that tag is of
 the given number; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigTagValue</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>bigTagValue</code> is less than 0.

### HasTag
    public boolean HasTag(int tagValue)
Returns whether this object has a tag of the given number.

**Parameters:**

* <code>tagValue</code> - The tag value to search for.

**Returns:**

* <code>true</code> if this object has a tag of the given number;
 otherwise, <code>false</code>.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>tagValue</code> is less than 0.

* <code>NullPointerException</code> - The parameter <code>tagValue</code> is null.

### HasTag
    public boolean HasTag(com.upokecenter.numbers.EInteger bigTagValue)
Returns whether this object has a tag of the given number.

**Parameters:**

* <code>bigTagValue</code> - The tag value to search for.

**Returns:**

* <code>true</code> if this object has a tag of the given number;
 otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigTagValue</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>bigTagValue</code> is less than 0.

### Insert
    @Deprecated public CBORObject Insert(int index, Object valueOb)
Inserts an object at the specified position in this CBOR array.

**Parameters:**

* <code>index</code> - Index starting at 0 to insert at.

* <code>valueOb</code> - An object representing the value, which will be converted to
 a CBORObject. Can be null, in which case this value is converted to
 CBORObject.Null.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This object is not an array.

* <code>IllegalArgumentException</code> - The parameter <code>valueOb</code> has an unsupported
 type; or <code>index</code> is not a valid index into this array.

### Insert
    public CBORObject Insert(int index, CBORObject cborObj)
Inserts a CBORObject at the specified position in this CBOR array.

**Parameters:**

* <code>index</code> - Index starting at 0 to insert at.

* <code>cborObj</code> - A CBORObject representing the value.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This object is not an array.

* <code>IllegalArgumentException</code> - The parameter <code>index</code> is not a valid index
 into this array.

### Clear
    public void Clear()
Removes all items from this CBOR array or all keys and values from this CBOR
 map.

**Throws:**

* <code>IllegalStateException</code> - This object is not a CBOR array or CBOR map.

### Remove
    public boolean Remove(Object obj)
If this object is an array, removes the first instance of the specified item
 (once converted to a CBOR object) from the array. If this object is a map,
 removes the item with the given key (once converted to a CBOR object) from
 the map.

**Parameters:**

* <code>obj</code> - The item or key (once converted to a CBOR object) to remove.

**Returns:**

* <code>true</code> if the item was removed; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>obj</code> is null (as opposed
 to CBORObject.Null).

* <code>IllegalStateException</code> - The object is not an array or map.

### RemoveAt
    public boolean RemoveAt(int index)
Removes the item at the given index of this CBOR array.

**Parameters:**

* <code>index</code> - The index, starting at 0, of the item to remove.

**Returns:**

* Returns "true" if the object was removed. Returns "false" if the
 given index is less than 0, or is at least as high as the number of items in
 the array.

**Throws:**

* <code>IllegalStateException</code> - This object is not a CBOR array.

### Remove
    public boolean Remove(CBORObject obj)
If this object is an array, removes the first instance of the specified item
 from the array. If this object is a map, removes the item with the given key
 from the map.

**Parameters:**

* <code>obj</code> - The item or key to remove.

**Returns:**

* <code>true</code> if the item was removed; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>obj</code> is null (as opposed
 to CBORObject.Null).

* <code>IllegalStateException</code> - The object is not an array or map.

### Set
    @Deprecated public CBORObject Set(Object key, Object valueOb)
Maps an object to a key in this CBOR map, or adds the value if the key
 doesn't exist. If this is a CBOR array, instead sets the value at the given
 index to the given value.

**Parameters:**

* <code>key</code> - If this instance is a CBOR map, this parameter is an object
 representing the key, which will be converted to a CBORObject; in this case,
 this parameter can be null, in which case this value is converted to
 CBORObject.Null. If this instance is a CBOR array, this parameter must be a
 32-bit signed integer(<code>int</code>) identifying the index (starting from 0)
 of the item to set in the array.

* <code>valueOb</code> - An object representing the value, which will be converted to
 a CBORObject. Can be null, in which case this value is converted to
 CBORObject.Null.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This object is not a map or an array.

* <code>IllegalArgumentException</code> - The parameter <code>key</code> or <code>valueOb</code> has
 an unsupported type, or this instance is a CBOR array and <code>key</code> is
 less than 0, is the size of this array or greater, or is not a 32-bit signed
 integer (<code>int</code>).

### Set
    public CBORObject Set(CBORObject mapKey, CBORObject mapValue)
Maps an object to a key in this CBOR map, or adds the value if the key
 doesn't exist.

**Parameters:**

* <code>mapKey</code> - If this instance is a CBOR map, this parameter is an object
 representing the key, which will be converted to a CBORObject; in this case,
 this parameter can be null, in which case this value is converted to
 CBORObject.Null.

* <code>mapValue</code> - A CBORObject representing the value, which should be of type
 CBORType.Map.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This object is not a map.

* <code>IllegalArgumentException</code> - The parameter <code>mapValue</code> or this instance is
 a CBOR array.

### Set
    public CBORObject Set(int key, CBORObject mapValue)
Sets the value of a CBORObject of type Array at the given index to the given
 value.

**Parameters:**

* <code>key</code> - This parameter must be a 32-bit signed integer(<code>int</code>)
 identifying the index (starting from 0) of the item to set in the array.

* <code>mapValue</code> - An CBORObject representing the value.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - MapValue is not a an array.

### ToJSONString
    public String ToJSONString()
<p>Converts this object to a text string in JavaScript object Notation
 (JSON) format. See the overload to ToJSONString taking a JSONOptions
 argument for further information. </p><p>If the CBOR object contains CBOR
 maps, or is a CBOR map itself, the order in which the keys to the map are
 written out to the JSON string is undefined unless the map was created using
 the NewOrderedMap method. Map keys other than untagged text strings are
 converted to JSON strings before writing them out (for example, <code>
 22("Test")</code> is converted to <code>"Test"</code> and <code>true</code> is converted to
 <code>"true"</code>). After such conversion, if two or more keys for the same
 map are identical, this method throws a CBORException. The example code
 given in <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
 can be used to write out certain keys of a CBOR map in a given order to a
 JSON string, or to write out a CBOR object as part of a JSON text
 sequence.</p> <p><b>Warning:</b> In general, if this CBOR object contains
 integer map keys or uses other features not supported in JSON, and the
 application converts this CBOR object to JSON and back to CBOR, the
 application <i>should not</i> expect the new CBOR object to be exactly the
 same as the original. This is because the conversion in many cases may have
 to convert unsupported features in JSON to supported features which
 correspond to a different feature in CBOR (such as converting integer map
 keys, which are supported in CBOR but not JSON, to text strings, which are
 supported in both).</p>

**Returns:**

* A text string containing the converted object in JSON format.

### ToJSONString
    public String ToJSONString(JSONOptions options)
<p>Converts this object to a text string in JavaScript object Notation
 (JSON) format, using the specified options to control the encoding process.
 This function works not only with arrays and maps, but also integers,
 strings, byte arrays, and other JSON data types. Notes: </p><ul><li>If this
 object contains maps with non-string keys, the keys are converted to JSON
 strings before writing the map as a JSON string.</li><li>If this object
 represents a number (the IsNumber property, or isNumber() method in Java,
 returns true), then it is written out as a number.</li><li>If the CBOR
 object contains CBOR maps, or is a CBOR map itself, the order in which the
 keys to the map are written out to the JSON string is undefined unless the
 map was created using the NewOrderedMap method. Map keys other than untagged
 text strings are converted to JSON strings before writing them out (for
 example, <code>22("Test")</code> is converted to <code>"Test"</code> and <code>true</code>
 is converted to <code>"true"</code>). After such conversion, if two or more keys
 for the same map are identical, this method throws a
 CBORException.</li><li>If a number in the form of an arbitrary-precision
 binary floating-point number has a very high binary exponent, it will be
 converted to a double before being converted to a JSON string. (The
 resulting double could overflow to infinity, in which case the
 arbitrary-precision binary floating-point number is converted to
 null.)</li><li>The string will not begin with a byte-order mark (U+FEFF);
 RFC 8259 (the JSON specification) forbids placing a byte-order mark at the
 beginning of a JSON string.</li><li>Byte strings are converted to Base64 URL
 without whitespace or padding by default (see section 3.4.5.3 of RFC 8949).
 A byte string will instead be converted to traditional base64 without
 whitespace and with padding if it has tag 22, or base16 for tag 23. (To
 create a CBOR object with a given tag, call the <code>
 CBORObject.FromCBORObjectAndTag</code> method and pass the CBOR object and the
 desired tag number to that method.)</li><li>Rational numbers will be
 converted to their exact form, if possible, otherwise to a high-precision
 approximation. (The resulting approximation could overflow to infinity, in
 which case the rational number is converted to null.)</li><li>Simple values
 other than true and false will be converted to null. (This doesn't include
 floating-point numbers.)</li><li>Infinity and not-a-number will be converted
 to null.</li></ul> <p><b>Warning:</b> In general, if this CBOR object
 contains integer map keys or uses other features not supported in JSON, and
 the application converts this CBOR object to JSON and back to CBOR, the
 application <i>should not</i> expect the new CBOR object to be exactly the
 same as the original. This is because the conversion in many cases may have
 to convert unsupported features in JSON to supported features which
 correspond to a different feature in CBOR (such as converting integer map
 keys, which are supported in CBOR but not JSON, to text strings, which are
 supported in both).</p> <p>The example code given below (originally written
 in C# for the.NET version) can be used to write out certain keys of a CBOR
 map in a given order to a JSON string.</p> <pre>/* Generates a JSON string
 of 'mapObj' whose keys are in the order given in 'keys' . Only keys found in
 'keys' will be written if they exist in 'mapObj'. */ private static string
 KeysToJSONMap(CBORObject mapObj, List&lt;CBORObject&gt; keys) { if (mapObj
 == null) { throw new NullPointerException)"mapObj");} if (keys ==
 null) { throw new NullPointerException)"keys");} if (obj.getType() !=
 CBORType.Map) { throw new IllegalArgumentException("'obj' is not a map."); }
 StringBuilder builder = new StringBuilder(); boolean first = true;
 builder.append("{"); for (CBORObject key in keys) { if
 (mapObj.ContainsKey(key)) { if (!first) {builder.append(", ");} var
 keyString=(key.getCBORType() == CBORType.string) ? key.AsString() :
 key.ToJSONString(); builder.append(CBORObject.FromObject(keyString)
 .ToJSONString()) .append(":").append(mapObj.get(key).ToJSONString());
 first=false; } } return builder.append("}").toString(); }</pre> .

**Parameters:**

* <code>options</code> - Specifies options to control writing the CBOR object to JSON.

**Returns:**

* A text string containing the converted object in JSON format.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>options</code> is null.

### toString
    public String toString()
<p>Returns this CBOR object in a text form intended to be read by humans.
 The value returned by this method is not intended to be parsed by computer
 programs, and the exact text of the value may change at any time between
 versions of this library. </p> <p>The returned string is not necessarily in
 JavaScript object Notation (JSON); to convert CBOR objects to JSON strings,
 use the <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
 method instead.</p>

**Overrides:**

* <code>toString</code> in class <code>Object</code>

**Returns:**

* A text representation of this object.

### Untag
    public CBORObject Untag()
Gets an object with the same value as this one but without the tags it has,
 if any. If this object is an array, map, or byte string, the data will not
 be copied to the returned object, so changes to the returned object will be
 reflected in this one.

**Returns:**

* A CBOR object.

### UntagOne
    public CBORObject UntagOne()
Gets an object with the same value as this one but without this object's
 outermost tag, if any. If this object is an array, map, or byte string, the
 data will not be copied to the returned object, so changes to the returned
 object will be reflected in this one.

**Returns:**

* A CBOR object.

### WriteJSONTo
    public void WriteJSONTo(OutputStream outputStream) throws IOException
<p>Converts this object to a text string in JavaScript object Notation
 (JSON) format, as in the ToJSONString method, and writes that string to a
 data stream in UTF-8. If the CBOR object contains CBOR maps, or is a CBOR
 map, the order in which the keys to the map are written out to the JSON
 string is undefined unless the map was created using the NewOrderedMap
 method. The example code given in
 <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b> can be
 used to write out certain keys of a CBOR map in a given order to a JSON
 string.</p><p>The following example (originally written in C# for the.NET
 version) writes out a CBOR object as part of a JSON text sequence (RFC
 7464).</p> <pre> stream.write(0x1e); /* RS */
 cborObject.WriteJSONTo(stream); /* JSON */ stream.write(0x0a); /* LF */
 </pre> <p>The following example (originally written in C# for the.NET
 version) shows how to use the <code>LimitedMemoryStream</code> class (implemented
 in <i>LimitedMemoryStream.cs</i> in the peteroupc/CBOR open-source
 repository) to limit the size of supported JSON serializations of CBOR
 objects.</p> <pre> /* maximum supported JSON size in bytes*/ int maxSize =
 20000; {
LimitedMemoryStream ms = null;
try {
ms = new LimitedMemoryStream(maxSize);

 cborObject.WriteJSONTo(ms); Array bytes = ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre> <p>The
 following example (written in Java for the Java version) shows how to use a
 subclassed <code>OutputStream</code> together with a <code>
 ByteArrayOutputStream</code> to limit the size of supported JSON serializations of
 CBOR objects.</p> <pre> /* maximum supported JSON size in bytes*/ final int
 maxSize = 20000; ByteArrayOutputStream ba = new ByteArrayOutputStream(); /*
 throws UnsupportedOperationException if too big*/ cborObject.WriteJSONTo(new
 FilterOutputStream(ba) { private int size = 0; public void write(byte[] b,
 int off, int len) { if (len&gt;(maxSize-size)) { throw
 new UnsupportedOperationException(); } size+=len; out.write(b, off, len); }
 public void write(byte b) { if (size &gt;= maxSize) {
 throw new UnsupportedOperationException(); } size++; out.write(b); } });
 byte[] bytes = ba.toByteArray(); </pre> <p>The following example (originally
 written in C# for the.NET version) shows how to use a.NET MemoryStream to
 limit the size of supported JSON serializations of CBOR objects. The
 disadvantage is that the extra memory needed to do so can be wasteful,
 especially if the average serialized object is much smaller than the maximum
 size given (for example, if the maximum size is 20000 bytes, but the average
 serialized object has a size of 50 bytes).</p> <pre> byte[] backing = new
 byte[20000]; /* maximum supported JSON size in bytes*/ byte[] bytes1,
 bytes2; {
java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream(backing);
 /* throws
 UnsupportedOperationException if too big*/ cborObject.WriteJSONTo(ms); bytes1 = new
 byte[ms.size()]; /* Copy serialized data if successful*/
 System.ArrayCopy(backing, 0, bytes1, 0, (int)ms.size()); /* Reset memory
 stream*/ ms.size() = 0; cborObject2.WriteJSONTo(ms); bytes2 = new
 byte[ms.size()]; /* Copy serialized data if successful*/
 System.ArrayCopy(backing, 0, bytes2, 0, (int)ms.size());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre>

**Parameters:**

* <code>outputStream</code> - A writable data stream.

**Throws:**

* <code>IOException</code> - An I/O error occurred.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

### WriteJSONTo
    public void WriteJSONTo(OutputStream outputStream, JSONOptions options) throws IOException
<p>Converts this object to a text string in JavaScript object Notation
 (JSON) format, as in the ToJSONString method, and writes that string to a
 data stream in UTF-8, using the given JSON options to control the encoding
 process. If the CBOR object contains CBOR maps, or is a CBOR map, the order
 in which the keys to the map are written out to the JSON string is undefined
 unless the map was created using the NewOrderedMap method. The example code
 given in <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
 can be used to write out certain keys of a CBOR map in a given order to a
 JSON string.</p>

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>options</code> - An object containing the options to control writing the CBOR
 object to JSON.

**Throws:**

* <code>IOException</code> - An I/O error occurred.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

### FromFloatingPointBits
    public static CBORObject FromFloatingPointBits(long floatingBits, int byteCount)
Generates a CBOR object from a floating-point number represented by its
 bits.

**Parameters:**

* <code>floatingBits</code> - The bits of a floating-point number number to write.

* <code>byteCount</code> - The number of bytes of the stored floating-point number;
 this also specifies the format of the "floatingBits" parameter. This value
 can be 2 if "floatingBits"'s lowest (least significant) 16 bits identify the
 floating-point number in IEEE 754r binary16 format; or 4 if "floatingBits"'s
 lowest (least significant) 32 bits identify the floating-point number in
 IEEE 754r binary32 format; or 8 if "floatingBits" identifies the floating
 point number in IEEE 754r binary64 format. Any other values for this
 parameter are invalid.

**Returns:**

* A CBOR object storing the given floating-point number.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
 4, or 8.

### WriteFloatingPointBits
    public static int WriteFloatingPointBits(OutputStream outputStream, long floatingBits, int byteCount) throws IOException
Writes the bits of a floating-point number in CBOR format to a data stream.

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>floatingBits</code> - The bits of a floating-point number number to write.

* <code>byteCount</code> - The number of bytes of the stored floating-point number;
 this also specifies the format of the "floatingBits" parameter. This value
 can be 2 if "floatingBits"'s lowest (least significant) 16 bits identify the
 floating-point number in IEEE 754r binary16 format; or 4 if "floatingBits"'s
 lowest (least significant) 32 bits identify the floating-point number in
 IEEE 754r binary32 format; or 8 if "floatingBits" identifies the floating
 point number in IEEE 754r binary64 format. Any other values for this
 parameter are invalid. This method will write one plus this many bytes to
 the data stream.

**Returns:**

* The number of 8-bit bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
 4, or 8.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

* <code>IOException</code>

### WriteFloatingPointBits
    public static int WriteFloatingPointBits(OutputStream outputStream, long floatingBits, int byteCount, boolean shortestForm) throws IOException
Writes the bits of a floating-point number in CBOR format to a data stream.

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>floatingBits</code> - The bits of a floating-point number number to write.

* <code>byteCount</code> - The number of bytes of the stored floating-point number;
 this also specifies the format of the "floatingBits" parameter. This value
 can be 2 if "floatingBits"'s lowest (least significant) 16 bits identify the
 floating-point number in IEEE 754r binary16 format; or 4 if "floatingBits"'s
 lowest (least significant) 32 bits identify the floating-point number in
 IEEE 754r binary32 format; or 8 if "floatingBits" identifies the floating
 point number in IEEE 754r binary64 format. Any other values for this
 parameter are invalid.

* <code>shortestForm</code> - If true, writes the shortest form of the floating-point
 number that preserves its value. If false, this method will write the number
 in the form given by 'floatingBits' by writing one plus the number of bytes
 given by 'byteCount' to the data stream.

**Returns:**

* The number of 8-bit bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
 4, or 8.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

* <code>IOException</code>

### WriteFloatingPointValue
    public static int WriteFloatingPointValue(OutputStream outputStream, double doubleVal, int byteCount) throws IOException
Writes a 64-bit binary floating-point number in CBOR format to a data
 stream, either in its 64-bit form, or its rounded 32-bit or 16-bit
 equivalent.

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>doubleVal</code> - The double-precision floating-point number to write.

* <code>byteCount</code> - The number of 8-bit bytes of the stored number. This value
 can be 2 to store the number in IEEE 754r binary16, rounded to nearest, ties
 to even; or 4 to store the number in IEEE 754r binary32, rounded to nearest,
 ties to even; or 8 to store the number in IEEE 754r binary64. Any other
 values for this parameter are invalid.

**Returns:**

* The number of 8-bit bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
 4, or 8.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

* <code>IOException</code>

### WriteFloatingPointValue
    public static int WriteFloatingPointValue(OutputStream outputStream, float singleVal, int byteCount) throws IOException
Writes a 32-bit binary floating-point number in CBOR format to a data
 stream, either in its 64- or 32-bit form, or its rounded 16-bit equivalent.

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>singleVal</code> - The single-precision floating-point number to write.

* <code>byteCount</code> - The number of 8-bit bytes of the stored number. This value
 can be 2 to store the number in IEEE 754r binary16, rounded to nearest, ties
 to even; or 4 to store the number in IEEE 754r binary32; or 8 to store the
 number in IEEE 754r binary64. Any other values for this parameter are
 invalid.

**Returns:**

* The number of 8-bit bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
 4, or 8.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

* <code>IOException</code>

### WriteValue
    public static int WriteValue(OutputStream outputStream, int majorType, long value) throws IOException
<p>Writes a CBOR major type number and an integer 0 or greater associated
 with it to a data stream, where that integer is passed to this method as a
 64-bit signed integer. This is a low-level method that is useful for
 implementing custom CBOR encoding methodologies. This method encodes the
 given major type and value in the shortest form allowed for the major
 type.</p> <p>There are other useful things to note when encoding CBOR that
 are not covered by this WriteValue method. To mark the start of an
 indefinite-length array, write the 8-bit byte 0x9f to the output stream. To
 mark the start of an indefinite-length map, write the 8-bit byte 0xbf to the
 output stream. To mark the end of an indefinite-length array or map, write
 the 8-bit byte 0xff to the output stream. For examples, see the
 WriteValue(InputStream, int, int) overload.</p>

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>majorType</code> - The CBOR major type to write. This is a number from 0
 through 7 as follows. 0: integer 0 or greater; 1: negative integer; 2: byte
 string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7: simple value. See
 RFC 8949 for details on these major types.

* <code>value</code> - An integer 0 or greater associated with the major type, as
 follows. 0: integer 0 or greater; 1: the negative integer's absolute value
 is 1 plus this number; 2: length in bytes of the byte string; 3: length in
 bytes of the UTF-8 text string; 4: number of items in the array; 5: number
 of key-value pairs in the map; 6: tag number; 7: simple value number, which
 must be in the interval [0, 23] or.charAt(32, 255).

**Returns:**

* The number of bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - Value is from 24 to 31 and major type is 7.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

* <code>IOException</code>

### WriteValue
    public static int WriteValue(OutputStream outputStream, int majorType, int value) throws IOException
<p>Writes a CBOR major type number and an integer 0 or greater associated
 with it to a data stream, where that integer is passed to this method as a
 32-bit signed integer. This is a low-level method that is useful for
 implementing custom CBOR encoding methodologies. This method encodes the
 given major type and value in the shortest form allowed for the major
 type.</p><p>There are other useful things to note when encoding CBOR that
 are not covered by this WriteValue method. To mark the start of an
 indefinite-length array, write the 8-bit byte 0x9f to the output stream. To
 mark the start of an indefinite-length map, write the 8-bit byte 0xbf to the
 output stream. To mark the end of an indefinite-length array or map, write
 the 8-bit byte 0xff to the output stream.</p><p>In the following example, an
 array of three objects is written as CBOR to a data stream.</p> <pre>/*
 array, length 3*/ CBORObject.WriteValue(stream, 4, 3); /* item 1 */
 CBORObject.Write("hello world", stream); CBORObject.Write(25, stream); /*
 item 2*/ CBORObject.Write(false, stream); /* item 3*/</pre> <p>In the
 following example, a map consisting of two key-value pairs is written as
 CBOR to a data stream.</p> <pre>CBORObject.WriteValue(stream, 5, 2); /* map,
 2 pairs*/ CBORObject.Write("number", stream); /* key 1 */
 CBORObject.Write(25, stream); /* value 1 */ CBORObject.Write("string",
 stream); /* key 2*/ CBORObject.Write("hello", stream); /* value 2*/</pre>
 <p>In the following example (originally written in C# for the.NET Framework
 version), a text string is written as CBOR to a data stream.</p> <pre>string
 str = "hello world"; byte[] bytes = com.upokecenter.util.DataUtilities.GetUtf8Bytes(str, true);
 CBORObject.WriteValue(stream, 4, bytes.length); stream.write(bytes, 0, * bytes.length);</pre> .

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>majorType</code> - The CBOR major type to write. This is a number from 0
 through 7 as follows. 0: integer 0 or greater; 1: negative integer; 2: byte
 string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7: simple value. See
 RFC 8949 for details on these major types.

* <code>value</code> - An integer 0 or greater associated with the major type, as
 follows. 0: integer 0 or greater; 1: the negative integer's absolute value
 is 1 plus this number; 2: length in bytes of the byte string; 3: length in
 bytes of the UTF-8 text string; 4: number of items in the array; 5: number
 of key-value pairs in the map; 6: tag number; 7: simple value number, which
 must be in the interval [0, 23] or.charAt(32, 255).

**Returns:**

* The number of bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - Value is from 24 to 31 and major type is 7.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is null.

* <code>IOException</code>

### WriteValue
    public static int WriteValue(OutputStream outputStream, int majorType, com.upokecenter.numbers.EInteger bigintValue) throws IOException
<p>Writes a CBOR major type number and an integer 0 or greater associated
 with it to a data stream, where that integer is passed to this method as an
 arbitrary-precision integer. This is a low-level method that is useful for
 implementing custom CBOR encoding methodologies. This method encodes the
 given major type and value in the shortest form allowed for the major
 type.</p> <p>There are other useful things to note when encoding CBOR that
 are not covered by this WriteValue method. To mark the start of an
 indefinite-length array, write the 8-bit byte 0x9f to the output stream. To
 mark the start of an indefinite-length map, write the 8-bit byte 0xbf to the
 output stream. To mark the end of an indefinite-length array or map, write
 the 8-bit byte 0xff to the output stream.</p>

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>majorType</code> - The CBOR major type to write. This is a number from 0
 through 7 as follows. 0: integer 0 or greater; 1: negative integer; 2: byte
 string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7: simple value. See
 RFC 8949 for details on these major types.

* <code>bigintValue</code> - An integer 0 or greater associated with the major type,
 as follows. 0: integer 0 or greater; 1: the negative integer's absolute
 value is 1 plus this number; 2: length in bytes of the byte string; 3:
 length in bytes of the UTF-8 text string; 4: number of items in the array;
 5: number of key-value pairs in the map; 6: tag number; 7: simple value
 number, which must be in the interval [0, 23] or.charAt(32, 255). For major
 types 0 to 6, this number may not be greater than 2^64 - 1.

**Returns:**

* The number of bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>majorType</code> is 7 and value is
 greater than 255.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> or <code>
 bigintValue</code> is null.

* <code>IOException</code>

### WriteTo
    public void WriteTo(OutputStream stream) throws IOException
<p>Writes this CBOR object to a data stream. If the CBOR object contains
 CBOR maps, or is a CBOR map, the order in which the keys to the map are
 written out to the data stream is undefined unless the map was created using
 the NewOrderedMap method. See the examples (originally written in C# for
 the.NET version) for ways to write out certain keys of a CBOR map in a given
 order. In the case of CBOR objects of type FloatingPoint, the number is
 written using the shortest floating-point encoding possible; this is a
 change from previous versions.</p><p>The following example shows a method
 that writes each key of 'mapObj' to 'outputStream', in the order given in
 'keys', where 'mapObj' is written out in the form of a CBOR
 <b>definite-length map</b> . Only keys found in 'keys' will be written if
 they exist in 'mapObj'.</p> <pre>private static void
 WriteKeysToMap(CBORObject mapObj, List&lt;CBORObject&gt; keys, OutputStream
 outputStream) { if (mapObj == null) { throw new
 NullPointerException("mapObj");} if (keys == null) {throw new
 NullPointerException("keys");} if (outputStream == null) {throw new
 NullPointerException("outputStream");} if (obj.getType()!=CBORType.Map) {
 throw new IllegalArgumentException("'obj' is not a map."); } int keyCount = 0; for
 (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { keyCount++; } }
 CBORObject.WriteValue(outputStream, 5, keyCount); for (CBORObject key in
 keys) { if (mapObj.ContainsKey(key)) { key.WriteTo(outputStream);
 mapObj.get(key).WriteTo(outputStream); } } }</pre> <p>The following example
 shows a method that writes each key of 'mapObj' to 'outputStream', in the
 order given in 'keys', where 'mapObj' is written out in the form of a CBOR
 <b>indefinite-length map</b> . Only keys found in 'keys' will be written if
 they exist in 'mapObj'.</p> <pre>private static void
 WriteKeysToIndefMap(CBORObject mapObj, List&lt;CBORObject&gt; keys,
 OutputStream outputStream) { if (mapObj == null) { throw new
 NullPointerException("mapObj");} if (keys == null) {throw new
 NullPointerException("keys");} if (outputStream == null) {throw new
 NullPointerException("outputStream");} if (obj.getType()!=CBORType.Map) {
 throw new IllegalArgumentException("'obj' is not a map."); }
 outputStream.write((byte)0xbf); for (CBORObject key in keys) { if
 (mapObj.ContainsKey(key)) { key.WriteTo(outputStream);
 mapObj.get(key).WriteTo(outputStream); } }
 outputStream.write((byte)0xff); }</pre> <p>The following example shows a
 method that writes out a list of objects to 'outputStream' as an
 <b>indefinite-length CBOR array</b> .</p> <pre>private static void
 WriteToIndefArray(List&lt;object&gt; list, InputStream outputStream) { if (list
 == null) { throw new NullPointerException("list");} if (outputStream
 == null) {throw new NullPointerException("outputStream");}
 outputStream.write((byte)0x9f); for (object item in list) { new
 CBORObject(item).WriteTo(outputStream); }
 outputStream.write((byte)0xff); }</pre> <p>The following example
 (originally written in C# for the.NET version) shows how to use the <code>
 LimitedMemoryStream</code> class (implemented in <i>LimitedMemoryStream.cs</i> in
 the peteroupc/CBOR open-source repository) to limit the size of supported
 CBOR serializations.</p> <pre> /* maximum supported CBOR size in bytes*/ int
 maxSize = 20000; {
LimitedMemoryStream ms = null;
try {
ms = new
 LimitedMemoryStream(maxSize);
 cborObject.WriteTo(ms); Array bytes =
 ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre> <p>The following example (written in Java for the
 Java version) shows how to use a subclassed <code>OutputStream</code> together
 with a <code>ByteArrayOutputStream</code> to limit the size of supported CBOR
 serializations.</p> <pre> /* maximum supported CBOR size in bytes*/ final
 int maxSize = 20000; ByteArrayOutputStream ba = new ByteArrayOutputStream();
 /* throws UnsupportedOperationException if too big*/ cborObject.WriteTo(new
 FilterOutputStream(ba) { private int size = 0; public void write(byte[] b,
 int off, int len) { if (len&gt;(maxSize-size)) { throw
 new UnsupportedOperationException(); } size+=len; out.write(b, off, len); }
 public void write(byte b) { if (size &gt;= maxSize) {
 throw new UnsupportedOperationException(); } size++; out.write(b); } });
 byte[] bytes = ba.toByteArray(); </pre> <p>The following example (originally
 written in C# for the.NET version) shows how to use a.NET MemoryStream to
 limit the size of supported CBOR serializations. The disadvantage is that
 the extra memory needed to do so can be wasteful, especially if the average
 serialized object is much smaller than the maximum size given (for example,
 if the maximum size is 20000 bytes, but the average serialized object has a
 size of 50 bytes).</p> <pre> byte[] backing = new byte[20000]; /* maximum
 supported CBOR size in bytes*/ byte[] bytes1, bytes2; using (MemoryStream ms
 = new java.io.ByteArrayInputStream(backing)) { /* throws UnsupportedOperationException if too big*/
 cborObject.WriteTo(ms); bytes1 = new byte[ms.getPosition()]; /* Copy serialized
 data if successful*/ System.ArrayCopy(backing, 0, bytes1, 0,
 (int)ms.getPosition()); /* Reset memory stream*/ ms.setPosition(0);
 cborObject2.WriteTo(ms); bytes2 = new byte[ms.getPosition()]; /* Copy serialized
 data if successful*/ System.ArrayCopy(backing, 0, bytes2, 0,
 (int)ms.getPosition()); } </pre>

**Parameters:**

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### WriteTo
    public void WriteTo(OutputStream stream, CBOREncodeOptions options) throws IOException
Writes this CBOR object to a data stream, using the specified options for
 encoding the data to CBOR format. If the CBOR object contains CBOR maps, or
 is a CBOR map, the order in which the keys to the map are written out to the
 data stream is undefined unless the map was created using the NewOrderedMap
 method. The example code given in <code>
 com.upokecenter.cbor.CBORObject.WriteTo(System.IO.InputStream)</code> can be used to
 write out certain keys of a CBOR map in a given order. In the case of CBOR
 objects of type FloatingPoint, the number is written using the shortest
 floating-point encoding possible; this is a change from previous versions.

**Parameters:**

* <code>stream</code> - A writable data stream.

* <code>options</code> - Options for encoding the data to CBOR.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

* <code>IllegalArgumentException</code> - Unexpected data type".
