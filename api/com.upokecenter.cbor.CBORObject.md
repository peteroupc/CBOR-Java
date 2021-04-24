# com.upokecenter.cbor.CBORObject

    public final class CBORObject extends java.lang.Object implements java.lang.Comparable<CBORObject>

<p>Represents an object in Concise Binary Object Representation (CBOR) and
 contains methods for reading and writing CBOR data. CBOR is an
 Internet Standard and defined in RFC 8949.</p><p> </p><p><b>Converting
 CBOR objects</b></p> <p>There are many ways to get a CBOR object,
 including from bytes, objects, streams and JSON, as described
 below.</p> <p><b>To and from byte arrays:</b> The
 CBORObject.DecodeFromBytes method converts a byte array in CBOR format
 to a CBOR object. The EncodeToBytes method converts a CBOR object to
 its corresponding byte array in CBOR format.</p> <p><b>To and from
 data streams:</b> The CBORObject.Write methods write many kinds of
 objects to a data stream, including numbers, CBOR objects, strings,
 and arrays of numbers and strings. The CBORObject.Read method reads a
 CBOR object from a data stream.</p> <p><b>To and from other
 objects:</b> The <code>CBORObject.FromObject</code> method converts many
 kinds of objects to a CBOR object, including numbers, strings, and
 arrays and maps of numbers and strings. Methods like AsNumber and
 AsString convert a CBOR object to different types of object. The
 <code>CBORObject.ToObject</code> method converts a CBOR object to an object
 of a given type; for example, a CBOR array to a native
 <code>ArrayList</code> (or <code>ArrayList</code> in Java), or a CBOR integer to
 an <code>int</code> or <code>long</code>.</p> <p><b>To and from JSON:</b> This
 class also doubles as a reader and writer of JavaScript object
 Notation (JSON). The CBORObject.FromJSONString method converts JSON in
 text string form to a CBOR object, and the ToJSONString method
 converts a CBOR object to a JSON string. (Note that the conversion
 from CBOR to JSON is not always without loss and may make it
 impossible to recover the original object when converting the JSON
 back to CBOR. See the ToJSONString documentation.) Likewise,
 ToJSONBytes and FromJSONBytes work with JSON in the form of byte
 arrays rather than text strings.</p> <p>In addition, the
 CBORObject.WriteJSON method writes many kinds of objects as JSON to a
 data stream, including numbers, CBOR objects, strings, and arrays of
 numbers and strings. The CBORObject.Read method reads a CBOR object
 from a JSON data stream.</p> <p><b>Comparison Considerations:</b></p>
 <p>Instances of CBORObject should not be compared for equality using
  the "==" operator; it's possible to create two CBOR objects with the
  same value but not the same reference. (The "==" operator might only
 check if each side of the operator is the same instance.)</p> <p>This
 class's natural ordering (under the compareTo method) is consistent
 with the Equals method, meaning that two values that compare as equal
 under the compareTo method are also equal under the Equals method;
 this is a change in version 4.0. Two otherwise equal objects with
 different tags are not treated as equal by both compareTo and Equals.
 To strip the tags from a CBOR object before comparing, use the
 <code>Untag</code> method.</p> <p><b>Thread Safety:</b></p> <p>Certain CBOR
 objects are immutable (their values can't be changed), so they are
 inherently safe for use by multiple threads.</p> <p>CBOR objects that
 are arrays, maps, and byte strings (whether or not they are tagged)
 are mutable, but this class doesn't attempt to synchronize reads and
 writes to those objects by multiple threads, so those objects are not
 thread safe without such synchronization.</p> <p>One kind of CBOR
 object is called a map, or a list of key-value pairs. Keys can be any
 kind of CBOR object, including numbers, strings, arrays, and maps.
 However, untagged text strings (which means GetTags returns an empty
  array and the Type property, or "getType()" in Java, returns
 TextString) are the most suitable to use as keys; other kinds of CBOR
 object are much better used as map values instead, keeping in mind
 that some of them are not thread safe without synchronizing reads and
 writes to them.</p> <p>To find the type of a CBOR object, call its
  Type property (or "getType()" in Java). The return value can be
 Integer, FloatingPoint, Boolean, SimpleValue, or TextString for
 immutable CBOR objects, and Array, Map, or ByteString for mutable CBOR
 objects.</p> <p><b>Nesting Depth:</b></p> <p>The DecodeFromBytes and
 Read methods can only read objects with a limited maximum depth of
 arrays and maps nested within other arrays and maps. The code sets
 this maximum depth to 500 (allowing more than enough nesting for most
 purposes), but it's possible that stack overflows in some runtimes
 might lower the effective maximum nesting depth. When the nesting
 depth goes above 500, the DecodeFromBytes and Read methods throw a
 CBORException.</p> <p>The ReadJSON and FromJSONString methods
 currently have nesting depths of 1000.</p>

## Fields

- `static CBORObject False`<br>
  Represents the value false.
- `static CBORObject NaN`<br>
  A not-a-number value.
- `static CBORObject NegativeInfinity`<br>
  The value negative infinity.
- `static CBORObject Null`<br>
  Represents the value null.
- `static CBORObject PositiveInfinity`<br>
  The value positive infinity.
- `static CBORObject True`<br>
  Represents the value true.
- `static CBORObject Undefined`<br>
  Represents the value undefined.
- `static CBORObject Zero`<br>
  Gets a CBOR object for the number zero.

## Methods

- `CBORObject Abs()`<br>
  Deprecated.
  Instead, convert this object to a number (with .getAsNumber()()),
  and use that number's.getAbs()() method.
  Instead, convert this object to a number (with .getAsNumber()()),
  and use that number's.getAbs()() method.
- `CBORObject Add​(CBORObject obj)`<br>
  Adds a new object to the end of this array.
- `CBORObject Add​(java.lang.Object obj)`<br>
  Converts an object to a CBOR object and adds it to the end of this
  array.
- `CBORObject Add​(java.lang.Object key, java.lang.Object valueOb)`<br>
  Adds a new key and its value to this CBOR map, or adds the value if the
  key doesn't exist.
- `static CBORObject Addition​(CBORObject first, CBORObject second)`<br>
  Deprecated.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Add() method.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Add() method.
- `boolean AsBoolean()`<br>
  Returns false if this object is a CBOR false, null, or undefined value
  (whether or not the object has tags); otherwise, true.
- `byte AsByte()`<br>
  Deprecated.
  Instead, use.getToObject()&lt;byte&gt;() in .NET or
  .getToObject()(Byte.class) in Java.
  Instead, use.getToObject()&lt;byte&gt;() in .NET or
  .getToObject()(Byte.class) in Java.
- `double AsDouble()`<br>
  Converts this object to a 64-bit floating point number.
- `long AsDoubleBits()`<br>
  Converts this object to the bits of a 64-bit floating-point number if this
  CBOR object's type is FloatingPoint.
- `double AsDoubleValue()`<br>
  Converts this object to a 64-bit floating-point number if this CBOR object's
  type is FloatingPoint.
- `com.upokecenter.numbers.EDecimal AsEDecimal()`<br>
  Deprecated.
  Instead, use.getToObject()&lt;PeterO.Numbers.EDecimal&gt;()
  in .NET or
  .getToObject()(com.upokecenter.numbers.EDecimal.class)
  in Java.
  Instead, use.getToObject()&lt;PeterO.Numbers.EDecimal&gt;()
  in .NET or
  .getToObject()(com.upokecenter.numbers.EDecimal.class)
  in Java.
- `com.upokecenter.numbers.EFloat AsEFloat()`<br>
  Deprecated.
  Instead, use.getToObject()&lt;PeterO.Numbers.EFloat&gt;() in.NET
  or .getToObject()(com.upokecenter.numbers.EFloat.class)
  in Java.
  Instead, use.getToObject()&lt;PeterO.Numbers.EFloat&gt;() in.NET
  or .getToObject()(com.upokecenter.numbers.EFloat.class)
  in Java.
- `com.upokecenter.numbers.EInteger AsEInteger()`<br>
  Deprecated.
  Instead, use.getToObject()&lt;PeterO.Numbers.EInteger&gt;()
  in .NET or
  .getToObject()(com.upokecenter.numbers.EInteger.class)
  in Java.
  Instead, use.getToObject()&lt;PeterO.Numbers.EInteger&gt;()
  in .NET or
  .getToObject()(com.upokecenter.numbers.EInteger.class)
  in Java.
- `com.upokecenter.numbers.EInteger AsEIntegerValue()`<br>
  Converts this object to an arbitrary-precision integer if this CBOR object's
  type is Integer.
- `com.upokecenter.numbers.ERational AsERational()`<br>
  Deprecated.
  Instead, use.getToObject()&lt;PeterO.Numbers.ERational&gt;() in
  .NET or.getToObject()(com.upokecenter.numbers.ERational.class)
  in Java.
  Instead, use.getToObject()&lt;PeterO.Numbers.ERational&gt;() in
  .NET or.getToObject()(com.upokecenter.numbers.ERational.class)
  in Java.
- `short AsInt16()`<br>
  Deprecated.
  Instead, use the following: (cbor.AsNumber().ToInt16Checked()),
  or .getToObject()&lt;short&gt;() in .NET.
  Instead, use the following: (cbor.AsNumber().ToInt16Checked()),
  or .getToObject()&lt;short&gt;() in .NET.
- `int AsInt32()`<br>
  Converts this object to a 32-bit signed integer.
- `int AsInt32Value()`<br>
  Converts this object to a 32-bit signed integer if this CBOR object's type
  is Integer.
- `long AsInt64()`<br>
  Deprecated.
  Instead, use the following: (cbor.AsNumber().ToInt64Checked()), or
  .ToObject&lt;long&gt;() in.NET.
  Instead, use the following: (cbor.AsNumber().ToInt64Checked()), or
  .ToObject&lt;long&gt;() in.NET.
- `long AsInt64Value()`<br>
  Converts this object to a 64-bit signed integer if this CBOR object's type
  is Integer.
- `CBORNumber AsNumber()`<br>
  Converts this object to a CBOR number.
- `float AsSingle()`<br>
  Converts this object to a 32-bit floating point number.
- `java.lang.String AsString()`<br>
  Gets the value of this object as a text string.
- `long CalcEncodedSize() EncodeToBytes()`<br>
  Calculates the number of bytes this CBOR object takes when serialized as a
  byte array using the EncodeToBytes() method.
- `boolean CanFitInDouble()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().CanFitInDouble()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().CanFitInDouble()).
- `boolean CanFitInInt32()`<br>
  Deprecated.
  Instead, use.CanValueFitInInt32(), if the application allows only CBOR
  integers, or (cbor.isNumber()
  &&cbor.AsNumber().CanFitInInt32()), if the application
  allows any CBOR Object convertible to an integer.
  Instead, use.CanValueFitInInt32(), if the application allows only CBOR
  integers, or (cbor.isNumber()
  &&cbor.AsNumber().CanFitInInt32()), if the application
  allows any CBOR Object convertible to an integer.
- `boolean CanFitInInt64()`<br>
  Deprecated.
  Instead, use CanValueFitInInt64(), if the application allows only CBOR
  integers, or (cbor.isNumber()
  &&cbor.AsNumber().CanFitInInt64()), if the application
  allows any CBOR Object convertible to an integer.
  Instead, use CanValueFitInInt64(), if the application allows only CBOR
  integers, or (cbor.isNumber()
  &&cbor.AsNumber().CanFitInInt64()), if the application
  allows any CBOR Object convertible to an integer.
- `boolean CanFitInSingle()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().CanFitInSingle()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().CanFitInSingle()).
- `boolean CanTruncatedIntFitInInt32()`<br>
  Deprecated.
  Instead, use the following: (cbor.CanValueFitInInt32() if only
  integers of any tag are allowed, or (cbor.isNumber()
  && cbor.AsNumber().CanTruncatedIntFitInInt32()).
  Instead, use the following: (cbor.CanValueFitInInt32() if only
  integers of any tag are allowed, or (cbor.isNumber()
  && cbor.AsNumber().CanTruncatedIntFitInInt32()).
- `boolean CanTruncatedIntFitInInt64()`<br>
  Deprecated.
  Instead, use the following: (cbor.CanValueFitInInt64() if only
  integers of any tag are allowed, or (cbor.isNumber()
  && cbor.AsNumber().CanTruncatedIntFitInInt64()).
  Instead, use the following: (cbor.CanValueFitInInt64() if only
  integers of any tag are allowed, or (cbor.isNumber()
  && cbor.AsNumber().CanTruncatedIntFitInInt64()).
- `boolean CanValueFitInInt32()`<br>
  Returns whether this CBOR object stores an integer (CBORType.Integer) within
  the range of a 32-bit signed integer.
- `boolean CanValueFitInInt64()`<br>
  Returns whether this CBOR object stores an integer (CBORType.Integer) within
  the range of a 64-bit signed integer.
- `void Clear()`<br>
  Removes all items from this CBOR array or all keys and values from this CBOR
  map.
- `int compareTo​(CBORObject other)`<br>
  Compares two CBOR objects.
- `int CompareToIgnoreTags​(CBORObject other)`<br>
  Compares this object and another CBOR object, ignoring the tags they have,
  if any.
- `boolean ContainsKey​(CBORObject key)`<br>
  Determines whether a value of the given key exists in this object.
- `boolean ContainsKey​(java.lang.Object objKey)`<br>
  Determines whether a value of the given key exists in this object.
- `boolean ContainsKey​(java.lang.String key)`<br>
  Determines whether a value of the given key exists in this object.
- `static CBORObject DecodeFromBytes​(byte[] data)`<br>
  Generates a CBOR object from an array of CBOR-encoded bytes.
- `static CBORObject DecodeFromBytes​(byte[] data, CBOREncodeOptions options) CBOREncodeOptions`<br>
  Generates a CBOR object from an array of CBOR-encoded bytes, using the given
  CBOREncodeOptions object to control the decoding process.
- `static <T> T DecodeObjectFromBytes​(byte[] data, CBOREncodeOptions enc, java.lang.reflect.Type t) CBOREncodeOptions`<br>
  Generates an object of an arbitrary type from an array of CBOR-encoded
  bytes, using the given CBOREncodeOptions object to control
  the decoding process.
- `static <T> T DecodeObjectFromBytes​(byte[] data, CBOREncodeOptions enc, java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions pod) CBOREncodeOptions`<br>
  Generates an object of an arbitrary type from an array of CBOR-encoded
  bytes, using the given CBOREncodeOptions object to control
  the decoding process.
- `static <T> T DecodeObjectFromBytes​(byte[] data, java.lang.reflect.Type t)`<br>
  Generates an object of an arbitrary type from an array of CBOR-encoded
  bytes.
- `static <T> T DecodeObjectFromBytes​(byte[] data, java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions pod)`<br>
  Generates an object of an arbitrary type from an array of CBOR-encoded
  bytes.
- `static CBORObject[] DecodeSequenceFromBytes​(byte[] data)`<br>
  Generates a sequence of CBOR objects from an array of CBOR-encoded
  bytes.
- `static CBORObject[] DecodeSequenceFromBytes​(byte[] data, CBOREncodeOptions options)`<br>
  Generates a sequence of CBOR objects from an array of CBOR-encoded
  bytes.
- `static CBORObject Divide​(CBORObject first, CBORObject second)`<br>
  Deprecated.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Divide() method.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Divide() method.
- `byte[] EncodeToBytes()`<br>
  Writes the binary representation of this CBOR object and returns a byte
  array of that representation.
- `byte[] EncodeToBytes​(CBOREncodeOptions options)`<br>
  Writes the binary representation of this CBOR object and returns a byte
  array of that representation, using the specified options for
  encoding the object to CBOR format.
- `boolean equals​(CBORObject other)`<br>
  Compares the equality of two CBOR objects.
- `boolean equals​(java.lang.Object obj)`<br>
  Determines whether this object and another object are equal and have the
  same type.
- `static CBORObject FromFloatingPointBits​(long floatingBits, int byteCount)`<br>
  Generates a CBOR object from a floating-point number represented by its
  bits.
- `static CBORObject FromJSONBytes​(byte[] bytes)`<br>
  Generates a CBOR object from a byte array in JavaScript object Notation
  (JSON) format.
- `static CBORObject FromJSONBytes​(byte[] bytes, int offset, int count)`<br>
  Generates a CBOR object from a byte array in JavaScript object Notation
  (JSON) format.
- `static CBORObject FromJSONBytes​(byte[] bytes, int offset, int count, JSONOptions jsonoptions)`<br>
  Generates a CBOR object from a byte array in JavaScript object Notation
  (JSON) format, using the specified options to control the decoding
  process.
- `static CBORObject FromJSONBytes​(byte[] bytes, JSONOptions jsonoptions)`<br>
  Generates a CBOR object from a byte array in JavaScript object Notation
  (JSON) format, using the specified options to control the decoding
  process.
- `static CBORObject[] FromJSONSequenceBytes​(byte[] bytes)`<br>
  Generates a list of CBOR objects from an array of bytes in JavaScript object
  Notation (JSON) text sequence format (RFC 7464).
- `static CBORObject[] FromJSONSequenceBytes​(byte[] data, JSONOptions options)`<br>
  Generates a list of CBOR objects from an array of bytes in JavaScript object
  Notation (JSON) text sequence format (RFC 7464), using the specified
  options to control the decoding process.
- `static CBORObject FromJSONString​(java.lang.String str)`<br>
  Generates a CBOR object from a text string in JavaScript object Notation
  (JSON) format.
- `static CBORObject FromJSONString​(java.lang.String str, int offset, int count)`<br>
  Generates a CBOR object from a text string in JavaScript object Notation
  (JSON) format.
- `static CBORObject FromJSONString​(java.lang.String str, int offset, int count, JSONOptions jsonoptions)`<br>
  Generates a CBOR object from a text string in JavaScript object Notation
  (JSON) format, using the specified options to control the decoding
  process.
- `static CBORObject FromJSONString​(java.lang.String str, CBOREncodeOptions options)`<br>
  Deprecated.
  Instead, use.getFromJSONString()(str,
  new JSONOptions(\allowduplicatekeys = true\))
  or .getFromJSONString()(str, new
  JSONOptions(\allowduplicatekeys = false\)), as appropriate.
  Instead, use.getFromJSONString()(str,
  new JSONOptions(\allowduplicatekeys = true\))
  or .getFromJSONString()(str, new
  JSONOptions(\allowduplicatekeys = false\)), as appropriate.
- `static CBORObject FromJSONString​(java.lang.String str, JSONOptions jsonoptions)`<br>
  Generates a CBOR object from a text string in JavaScript object Notation
  (JSON) format, using the specified options to control the decoding
  process.
- `static CBORObject FromObject​(boolean value)`<br>
  Returns the CBOR true value or false value, depending on "value".
- `static CBORObject FromObject​(byte value)`<br>
  Generates a CBOR object from a byte (0 to 255).
- `static CBORObject FromObject​(byte[] bytes)`<br>
  Generates a CBOR object from an array of 8-bit bytes; the byte array is
  copied to a new byte array in this process.
- `static CBORObject FromObject​(double value)`<br>
  Generates a CBOR object from a 64-bit floating-point number.
- `static CBORObject FromObject​(float value)`<br>
  Generates a CBOR object from a 32-bit floating-point number.
- `static CBORObject FromObject​(int value)`<br>
  Generates a CBOR object from a 32-bit signed integer.
- `static CBORObject FromObject​(int[] array)`<br>
  Generates a CBOR object from an array of 32-bit integers.
- `static CBORObject FromObject​(long value)`<br>
  Generates a CBOR object from a 64-bit signed integer.
- `static CBORObject FromObject​(long[] array)`<br>
  Generates a CBOR object from an array of 64-bit integers.
- `static CBORObject FromObject​(short value)`<br>
  Generates a CBOR object from a 16-bit signed integer.
- `static CBORObject FromObject​(CBORObject value)`<br>
  Generates a CBOR object from a CBOR object.
- `static CBORObject FromObject​(CBORObject[] array)`<br>
  Generates a CBOR object from an array of CBOR objects.
- `static CBORObject FromObject​(com.upokecenter.numbers.EDecimal bigValue)`<br>
  Generates a CBOR object from a decimal number.
- `static CBORObject FromObject​(com.upokecenter.numbers.EFloat bigValue)`<br>
  Generates a CBOR object from an arbitrary-precision binary floating-point
  number.
- `static CBORObject FromObject​(com.upokecenter.numbers.EInteger bigintValue)`<br>
  Generates a CBOR object from an arbitrary-precision integer.
- `static CBORObject FromObject​(com.upokecenter.numbers.ERational bigValue)`<br>
  Generates a CBOR object from an arbitrary-precision rational number.
- `static CBORObject FromObject​(java.lang.Object obj)`<br>
  Generates a CBORObject from an arbitrary object.
- `static CBORObject FromObject​(java.lang.Object obj, CBORTypeMapper mapper)`<br>
  Generates a CBORObject from an arbitrary object.
- `static CBORObject FromObject​(java.lang.Object obj, CBORTypeMapper mapper, PODOptions options)`<br>
  Generates a CBORObject from an arbitrary object, using the given options
  to control how certain objects are converted to CBOR objects.
- `static CBORObject FromObject​(java.lang.Object obj, PODOptions options)`<br>
  Generates a CBORObject from an arbitrary object.
- `static CBORObject FromObject​(java.lang.String strValue)`<br>
  Generates a CBOR object from a text string.
- `static CBORObject FromObjectAndTag​(java.lang.Object valueObValue, int smallTag)`<br>
  Generates a CBOR object from an arbitrary object and gives the resulting
  object a tag in addition to its existing tags (the new tag is made
  the outermost tag).
- `static CBORObject FromObjectAndTag​(java.lang.Object valueOb, com.upokecenter.numbers.EInteger bigintTag)`<br>
  Generates a CBOR object from an arbitrary object and gives the resulting
  object a tag in addition to its existing tags (the new tag is made
  the outermost tag).
- `static CBORObject FromSimpleValue​(int simpleValue)`<br>
  Creates a CBOR object from a simple value number.
- `CBORObject get​(int index)`<br>
  Gets the value of a CBOR object by integer index in this array or by integer
  key in this map.
- `CBORObject get​(CBORObject key)`<br>
  Gets the value of a CBOR object by integer index in this array or by CBOR
  object key in this map.
- `CBORObject get​(java.lang.String key)`<br>
  Gets the value of a CBOR object in this map, using a string as the key.
- `com.upokecenter.numbers.EInteger[] GetAllTags()`<br>
  Gets a list of all tags, from outermost to innermost.
- `byte[] GetByteString()`<br>
  Gets the backing byte array used in this CBOR object, if this object is a
  byte string, without copying the data to a new byte array.
- `java.util.Collection<java.util.Map.Entry<CBORObject,​CBORObject>> getEntries()`<br>
  Gets a collection of the key/value pairs stored in this CBOR object, if it's
  a map.
- `java.util.Collection<CBORObject> getKeys()`<br>
  Gets a collection of the keys of this CBOR object.
- `com.upokecenter.numbers.EInteger getMostInnerTag()`<br>
  Gets the last defined tag for this CBOR data item, or -1 if the item is
  untagged.
- `com.upokecenter.numbers.EInteger getMostOuterTag()`<br>
  Gets the outermost tag for this CBOR data item, or -1 if the item is
  untagged.
- `CBORObject GetOrDefault​(java.lang.Object key, CBORObject defaultValue)`<br>
  Gets the value of a CBOR object by integer index in this array or by CBOR
  object key in this map, or a default value if that value is not
  found.
- `int getSimpleValue()`<br>
  Gets the simple value ID of this CBOR object, or -1 if the object is not a
  simple value.
- `int getTagCount()`<br>
  Gets the number of tags this object has.
- `CBORType getType()`<br>
  Gets the general data type of this CBOR object.
- `java.util.Collection<CBORObject> getValues()`<br>
  Gets a collection of the values of this CBOR object, if it's a map or an
  array.
- `int hashCode()`<br>
  Calculates the hash code of this object.
- `boolean HasMostInnerTag​(int tagValue)`<br>
  Returns whether this object has an innermost tag and that tag is of the
  given number.
- `boolean HasMostInnerTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
  Returns whether this object has an innermost tag and that tag is of the
  given number, expressed as an arbitrary-precision number.
- `boolean HasMostOuterTag​(int tagValue)`<br>
  Returns whether this object has an outermost tag and that tag is of the
  given number.
- `boolean HasMostOuterTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
  Returns whether this object has an outermost tag and that tag is of the
  given number.
- `boolean HasOneTag()`<br>
  Returns whether this object has only one tag.
- `boolean HasOneTag​(int tagValue)`<br>
  Returns whether this object has only one tag and that tag is the given
  number.
- `boolean HasOneTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
  Returns whether this object has only one tag and that tag is the given
  number, expressed as an arbitrary-precision integer.
- `boolean HasTag​(int tagValue)`<br>
  Returns whether this object has a tag of the given number.
- `boolean HasTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
  Returns whether this object has a tag of the given number.
- `CBORObject Insert​(int index, java.lang.Object valueOb)`<br>
  Inserts an object at the specified position in this CBOR array.
- `boolean isFalse()`<br>
  Gets a value indicating whether this value is a CBOR false value, whether
  tagged or not.
- `boolean isFinite()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsFinite()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsFinite()).
- `boolean IsInfinity()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsInfinity()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsInfinity()).
- `boolean isIntegral()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsInteger()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsInteger()).
- `boolean IsNaN()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsNaN()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsNaN()).
- `boolean isNegative()`<br>
  Deprecated.
  Instead, use (cbor.IsNumber()
  && cbor.AsNumber().IsNegative()).
  Instead, use (cbor.IsNumber()
  && cbor.AsNumber().IsNegative()).
- `boolean IsNegativeInfinity()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsNegativeInfinity()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsNegativeInfinity()).
- `boolean isNull()`<br>
  Gets a value indicating whether this CBOR object is a CBOR null value,
  whether tagged or not.
- `boolean isNumber()`<br>
  Gets a value indicating whether this CBOR object stores a number (including
  infinity or a not-a-number or NaN value).
- `boolean IsPositiveInfinity()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsPositiveInfinity()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsPositiveInfinity()).
- `boolean isTagged()`<br>
  Gets a value indicating whether this data item has at least one tag.
- `boolean isTrue()`<br>
  Gets a value indicating whether this value is a CBOR true value, whether
  tagged or not.
- `boolean isUndefined()`<br>
  Gets a value indicating whether this value is a CBOR undefined value,
  whether tagged or not.
- `boolean isZero()`<br>
  Deprecated.
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsZero()).
  Instead, use the following: (cbor.isNumber()
  && cbor.AsNumber().IsZero()).
- `static CBORObject Multiply​(CBORObject first, CBORObject second)`<br>
  Deprecated.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Multiply() method.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Multiply() method.
- `CBORObject Negate()`<br>
  Deprecated.
  Instead, convert this object to a number (with .AsNumber()), and
  use that number's.Negate() method.
  Instead, convert this object to a number (with .AsNumber()), and
  use that number's.Negate() method.
- `static CBORObject NewArray()`<br>
  Creates a new empty CBOR array.
- `static CBORObject NewMap()`<br>
  Creates a new empty CBOR map that stores its keys in an undefined order.
- `static CBORObject NewOrderedMap()`<br>
  Creates a new empty CBOR map that ensures that keys are stored in the order
  in which they are first inserted.
- `static CBORObject Read​(java.io.InputStream stream)`<br>
  Reads an object in CBOR format from a data stream.
- `static CBORObject Read​(java.io.InputStream stream, CBOREncodeOptions options)`<br>
  Reads an object in CBOR format from a data stream, using the specified
  options to control the decoding process.
- `static CBORObject ReadJSON​(java.io.InputStream stream)`<br>
  Generates a CBOR object from a data stream in JavaScript object Notation
  (JSON) format.
- `static CBORObject ReadJSON​(java.io.InputStream stream, CBOREncodeOptions options)`<br>
  Deprecated.
  Instead, use.getReadJSON()(stream,
  new JSONOptions(\allowduplicatekeys = true\))
  or .getReadJSON()(stream,
  new JSONOptions(\allowduplicatekeys = false\)),
  as appropriate.
  Instead, use.getReadJSON()(stream,
  new JSONOptions(\allowduplicatekeys = true\))
  or .getReadJSON()(stream,
  new JSONOptions(\allowduplicatekeys = false\)),
  as appropriate.
- `static CBORObject ReadJSON​(java.io.InputStream stream, JSONOptions jsonoptions)`<br>
  Generates a CBOR object from a data stream in JavaScript object Notation
  (JSON) format, using the specified options to control the decoding
  process.
- `static CBORObject[] ReadJSONSequence​(java.io.InputStream stream)`<br>
  Generates a list of CBOR objects from a data stream in JavaScript object
  Notation (JSON) text sequence format (RFC 7464).
- `static CBORObject[] ReadJSONSequence​(java.io.InputStream stream, JSONOptions jsonoptions)`<br>
  Generates a list of CBOR objects from a data stream in JavaScript object
  Notation (JSON) text sequence format (RFC 7464).
- `static CBORObject[] ReadSequence​(java.io.InputStream stream)`<br>
  Reads a sequence of objects in CBOR format from a data stream.
- `static CBORObject[] ReadSequence​(java.io.InputStream stream, CBOREncodeOptions options)`<br>
  Reads a sequence of objects in CBOR format from a data stream.
- `static CBORObject Remainder​(CBORObject first, CBORObject second)`<br>
  Deprecated.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Remainder() method.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Remainder() method.
- `boolean Remove​(CBORObject obj)`<br>
  If this object is an array, removes the first instance of the specified item
  from the array.
- `boolean Remove​(java.lang.Object obj)`<br>
  If this object is an array, removes the first instance of the specified item
  (once converted to a CBOR object) from the array.
- `boolean RemoveAt​(int index)`<br>
  Removes the item at the given index of this CBOR array.
- `void set​(int index, CBORObject value)`<br>
  Sets the value of a CBOR object by integer index in this array or by integer
  key in this map.
- `void set​(CBORObject key, CBORObject value)`<br>
  Sets the value of a CBOR object by integer index in this array or by CBOR
  object key in this map.
- `void set​(java.lang.String key, CBORObject value)`<br>
  Sets the value of a CBOR object in this map, using a string as the key.
- `CBORObject Set​(java.lang.Object key, java.lang.Object valueOb)`<br>
  Maps an object to a key in this CBOR map, or adds the value if the key
  doesn't exist.
- `int signum()`<br>
  Deprecated.
  Instead, convert this object to a number with.AsNumber(), and use the
  Sign property in.NET or the signum method in Java.
  Instead, convert this object to a number with.AsNumber(), and use the
  Sign property in.NET or the signum method in Java.
- `int size()`<br>
  Gets the number of keys in this map, or the number of items in this array,
  or 0 if this item is neither an array nor a map.
- `static CBORObject Subtract​(CBORObject first, CBORObject second)`<br>
  Deprecated.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Subtract() method.
  Instead, convert both CBOR objects to numbers (with .AsNumber()), and
  use the first number's.Subtract() method.
- `byte[] ToJSONBytes()`<br>
  Converts this object to a byte array in JavaScript object Notation (JSON)
  format.
- `byte[] ToJSONBytes​(JSONOptions jsonoptions)`<br>
  Converts this object to a byte array in JavaScript object Notation (JSON)
  format.
- `java.lang.String ToJSONString()`<br>
  Converts this object to a text string in JavaScript object Notation (JSON)
  format.
- `java.lang.String ToJSONString​(JSONOptions options)`<br>
  Converts this object to a text string in JavaScript object Notation (JSON)
  format, using the specified options to control the encoding process.
- `<T> T ToObject​(java.lang.reflect.Type t)`<br>
  Converts this CBOR object to an object of an arbitrary type.
- `<T> T ToObject​(java.lang.reflect.Type t, CBORTypeMapper mapper)`<br>
  Converts this CBOR object to an object of an arbitrary type.
- `<T> T ToObject​(java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions options)`<br>
  Converts this CBOR object to an object of an arbitrary type.
- `<T> T ToObject​(java.lang.reflect.Type t, PODOptions options)`<br>
  Converts this CBOR object to an object of an arbitrary type.
- `java.lang.String toString()`<br>
  Returns this CBOR object in a text form intended to be read by humans.
- `CBORObject Untag()`<br>
  Gets an object with the same value as this one but without the tags it has,
  if any.
- `CBORObject UntagOne()`<br>
  Gets an object with the same value as this one but without this object's
  outermost tag, if any.
- `CBORObject WithTag​(int smallTag)`<br>
  Generates a CBOR object from an arbitrary object and gives the resulting
  object a tag in addition to its existing tags (the new tag is made
  the outermost tag).
- `CBORObject WithTag​(com.upokecenter.numbers.EInteger bigintTag)`<br>
  Generates a CBOR object from this one, but gives the resulting object a tag
  in addition to its existing tags (the new tag is made the outermost
  tag).
- `static void Write​(boolean value, java.io.OutputStream stream)`<br>
  Writes a Boolean value in CBOR format to a data stream.
- `static void Write​(byte value, java.io.OutputStream stream)`<br>
  Writes a byte (0 to 255) in CBOR format to a data stream.
- `static void Write​(double value, java.io.OutputStream stream)`<br>
  Writes a 64-bit floating-point number in CBOR format to a data stream.
- `static void Write​(float value, java.io.OutputStream stream)`<br>
  Writes a 32-bit floating-point number in CBOR format to a data stream.
- `static void Write​(int value, java.io.OutputStream stream)`<br>
  Writes a 32-bit signed integer in CBOR format to a data stream.
- `static void Write​(long value, java.io.OutputStream stream)`<br>
  Writes a 64-bit signed integer in CBOR format to a data stream.
- `static void Write​(short value, java.io.OutputStream stream)`<br>
  Writes a 16-bit signed integer in CBOR format to a data stream.
- `static void Write​(CBORObject value, java.io.OutputStream stream)`<br>
  Writes a CBOR object to a CBOR data stream.
- `static void Write​(com.upokecenter.numbers.EDecimal bignum, java.io.OutputStream stream)`<br>
  Writes a decimal floating-point number in CBOR format to a data stream, as
  though it were converted to a CBOR object via
  CBORObject.FromObject(EDecimal) and then written out.
- `static void Write​(com.upokecenter.numbers.EFloat bignum, java.io.OutputStream stream)`<br>
  Writes a binary floating-point number in CBOR format to a data stream, as
  though it were converted to a CBOR object via
  CBORObject.FromObject(EFloat) and then written out.
- `static void Write​(com.upokecenter.numbers.EInteger bigint, java.io.OutputStream stream)`<br>
  Writes a arbitrary-precision integer in CBOR format to a data stream.
- `static void Write​(com.upokecenter.numbers.ERational rational, java.io.OutputStream stream)`<br>
  Writes a rational number in CBOR format to a data stream, as though it were
  converted to a CBOR object via CBORObject.FromObject(ERational) and
  then written out.
- `static void Write​(java.lang.Object objValue, java.io.OutputStream stream)`<br>
  Writes a CBOR object to a CBOR data stream.
- `static void Write​(java.lang.Object objValue, java.io.OutputStream output, CBOREncodeOptions options)`<br>
  Writes an arbitrary object to a CBOR data stream, using the specified
  options for controlling how the object is encoded to CBOR data
  format.
- `static void Write​(java.lang.String str, java.io.OutputStream stream)`<br>
  Writes a text string in CBOR format to a data stream.
- `static void Write​(java.lang.String str, java.io.OutputStream stream, CBOREncodeOptions options)`<br>
  Writes a text string in CBOR format to a data stream, using the given
  options to control the encoding process.
- `static int WriteFloatingPointBits​(java.io.OutputStream outputStream, long floatingBits, int byteCount)`<br>
  Writes the bits of a floating-point number in CBOR format to a data stream.
- `static int WriteFloatingPointBits​(java.io.OutputStream outputStream, long floatingBits, int byteCount, boolean shortestForm)`<br>
  Writes the bits of a floating-point number in CBOR format to a data stream.
- `static int WriteFloatingPointValue​(java.io.OutputStream outputStream, double doubleVal, int byteCount)`<br>
  Writes a 64-bit binary floating-point number in CBOR format to a data
  stream, either in its 64-bit form, or its rounded 32-bit or 16-bit
  equivalent.
- `static int WriteFloatingPointValue​(java.io.OutputStream outputStream, float singleVal, int byteCount)`<br>
  Writes a 32-bit binary floating-point number in CBOR format to a data
  stream, either in its 64- or 32-bit form, or its rounded 16-bit
  equivalent.
- `static void WriteJSON​(java.lang.Object obj, java.io.OutputStream outputStream)`<br>
  Converts an arbitrary object to a text string in JavaScript object Notation
  (JSON) format, as in the ToJSONString method, and writes that string
  to a data stream in UTF-8.
- `void WriteJSONTo​(java.io.OutputStream outputStream)`<br>
  Converts this object to a text string in JavaScript object Notation (JSON)
  format, as in the ToJSONString method, and writes that string to a
  data stream in UTF-8.
- `void WriteJSONTo​(java.io.OutputStream outputStream, JSONOptions options)`<br>
  Converts this object to a text string in JavaScript object Notation (JSON)
  format, as in the ToJSONString method, and writes that string to a
  data stream in UTF-8, using the given JSON options to control the
  encoding process.
- `void WriteTo​(java.io.OutputStream stream)`<br>
  Writes this CBOR object to a data stream.
- `void WriteTo​(java.io.OutputStream stream, CBOREncodeOptions options)`<br>
  Writes this CBOR object to a data stream, using the specified options for
  encoding the data to CBOR format.
- `static int WriteValue​(java.io.OutputStream outputStream, int majorType, int value)`<br>
  Writes a CBOR major type number and an integer 0 or greater associated with
  it to a data stream, where that integer is passed to this method as
  a 32-bit signed integer.
- `static int WriteValue​(java.io.OutputStream outputStream, int majorType, long value)`<br>
  Writes a CBOR major type number and an integer 0 or greater associated with
  it to a data stream, where that integer is passed to this method as
  a 64-bit signed integer.
- `static int WriteValue​(java.io.OutputStream outputStream, int majorType, com.upokecenter.numbers.EInteger bigintValue)`<br>
  Writes a CBOR major type number and an integer 0 or greater associated with
  it to a data stream, where that integer is passed to this method as
  an arbitrary-precision integer.

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

- The number of keys in this map, or the number of items in this
  array, or 0 if this item is neither an array nor a map.

### getMostInnerTag

    public final com.upokecenter.numbers.EInteger getMostInnerTag()

Gets the last defined tag for this CBOR data item, or -1 if the item is
untagged.

**Returns:**

- The last defined tag for this CBOR data item, or -1 if the item is
  untagged.

### isFalse

    public final boolean isFalse()

Gets a value indicating whether this value is a CBOR false value, whether
tagged or not.

**Returns:**

- <code>true</code> if this value is a CBOR false value; otherwise, <code>
  false</code>.

### isFinite

    @Deprecated public final boolean isFinite()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().IsFinite()).

**Returns:**

- <code>true</code> if this CBOR object represents a finite number;
  otherwise, <code>false</code>.

### isIntegral

    @Deprecated public final boolean isIntegral()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().IsInteger()).

**Returns:**

- <code>true</code> if this object represents an integer number, that is, a
  number without a fractional part; otherwise, <code>false</code>.

### isNull

    public final boolean isNull()

Gets a value indicating whether this CBOR object is a CBOR null value,
whether tagged or not.

**Returns:**

- <code>true</code> if this value is a CBOR null value; otherwise, <code>
  false</code>.

### isTagged

    public final boolean isTagged()

Gets a value indicating whether this data item has at least one tag.

**Returns:**

- <code>true</code> if this data item has at least one tag; otherwise,
  <code>false</code>.

### isTrue

    public final boolean isTrue()

Gets a value indicating whether this value is a CBOR true value, whether
tagged or not.

**Returns:**

- <code>true</code> if this value is a CBOR true value; otherwise, <code>
  false</code>.

### isUndefined

    public final boolean isUndefined()

Gets a value indicating whether this value is a CBOR undefined value,
whether tagged or not.

**Returns:**

- <code>true</code> if this value is a CBOR undefined value; otherwise,
  <code>false</code>.

### isZero

    @Deprecated public final boolean isZero()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().IsZero()).

**Returns:**

- <code>true</code> if this object's value equals 0; otherwise, <code>
  false</code>.

### getKeys

    public final java.util.Collection<CBORObject> getKeys()

Gets a collection of the keys of this CBOR object. In general, the order in
which those keys occur is undefined unless this is a map created
using the NewOrderedMap method.

**Returns:**

- A collection of the keys of this CBOR object. To avoid potential
  problems, the calling code should not modify the CBOR map or the
  returned collection while iterating over the returned collection.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not a map.

### isNegative

    @Deprecated public final boolean isNegative()

Deprecated.
Instead, use (cbor.IsNumber()
&amp;&amp; cbor.AsNumber().IsNegative()).

**Returns:**

- <code>true</code> if this object is a negative number; otherwise, <code>
  false</code>.

### getMostOuterTag

    public final com.upokecenter.numbers.EInteger getMostOuterTag()

Gets the outermost tag for this CBOR data item, or -1 if the item is
untagged.

**Returns:**

- The outermost tag for this CBOR data item, or -1 if the item is
  untagged.

### signum

    @Deprecated public final int signum()

Deprecated.
Instead, convert this object to a number with.AsNumber(), and use the
Sign property in.NET or the signum method in Java. Either will
treat not-a-number (NaN) values differently than here.

**Returns:**

- This value's sign: -1 if negative; 1 if positive; 0 if zero.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number, or
  this object is a not-a-number (NaN) value.

### getSimpleValue

    public final int getSimpleValue()

Gets the simple value ID of this CBOR object, or -1 if the object is not a
simple value. In this method, objects with a CBOR type of Boolean or
SimpleValue are simple values, whether they are tagged or not.

**Returns:**

- The simple value ID of this object if it's a simple value, or -1 if
  this object is not a simple value.

### isNumber

    public final boolean isNumber()

Gets a value indicating whether this CBOR object stores a number (including
infinity or a not-a-number or NaN value). Currently, this is true if
this item is untagged and has a CBORType of Integer or
FloatingPoint, or if this item has only one tag and that tag is 2,
3, 4, 5, 30, 264, 265, 268, 269, or 270 with the right data type.

**Returns:**

- A value indicating whether this CBOR object stores a number.

### getType

    public final CBORType getType()

Gets the general data type of this CBOR object. This method disregards the
tags this object has, if any.

**Returns:**

- The general data type of this CBOR object.

### getEntries

    public final java.util.Collection<java.util.Map.Entry<CBORObject,​CBORObject>> getEntries()

Gets a collection of the key/value pairs stored in this CBOR object, if it's
a map. Returns one entry for each key/value pair in the map. In
general, the order in which those entries occur is undefined unless
this is a map created using the NewOrderedMap method.

**Returns:**

- A collection of the key/value pairs stored in this CBOR map, as a
  read-only view of those pairs. To avoid potential problems, the
  calling code should not modify the CBOR map while iterating over the
  returned collection.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not a map.

### getValues

    public final java.util.Collection<CBORObject> getValues()

Gets a collection of the values of this CBOR object, if it's a map or an
array. If this object is a map, returns one value for each key in
the map; in general, the order in which those keys occur is
undefined unless this is a map created using the NewOrderedMap
method. If this is an array, returns all the values of the array in
the order they are listed. (This method can't be used to get the
bytes in a CBOR byte string; for that, use the GetByteString method
instead.).

**Returns:**

- A collection of the values of this CBOR map or array. To avoid
  potential problems, the calling code should not modify the CBOR map
  or array or the returned collection while iterating over the
  returned collection.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not a map or an array.

### get

    public CBORObject get​(int index)

Gets the value of a CBOR object by integer index in this array or by integer
key in this map.

**Parameters:**

- <code>index</code> - Index starting at 0 of the element, or the integer key to this
  map. (If this is a map, the given index can be any 32-bit signed
  integer, even a negative one.).

**Returns:**

- The CBOR object referred to by index or key in this array or map. If
  this is a CBOR map, returns <code>null</code> (not <code>
  CBORObject.Null</code>) if an item with the given key doesn't exist.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not an array or map.

- <code>java.lang.IllegalArgumentException</code> - This object is an array and the index is less than
  0 or at least the size of the array.

- <code>java.lang.NullPointerException</code> - The parameter "value" is null (as opposed to
  CBORObject.Null).

### set

    public void set​(int index, CBORObject value)

Sets the value of a CBOR object by integer index in this array or by integer
key in this map.

**Parameters:**

- <code>index</code> - Index starting at 0 of the element, or the integer key to this
  map. (If this is a map, the given index can be any 32-bit signed
  integer, even a negative one.).

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not an array or map.

- <code>java.lang.IllegalArgumentException</code> - This object is an array and the index is less than
  0 or at least the size of the array.

- <code>java.lang.NullPointerException</code> - The parameter "value" is null (as opposed to
  CBORObject.Null).

### GetOrDefault

    public CBORObject GetOrDefault​(java.lang.Object key, CBORObject defaultValue)

Gets the value of a CBOR object by integer index in this array or by CBOR
object key in this map, or a default value if that value is not
found.

**Parameters:**

- <code>key</code> - An arbitrary object. If this is a CBOR map, this parameter is
  converted to a CBOR object serving as the key to the map or index to
  the array, and can be null. If this is a CBOR array, the key must be
  an integer 0 or greater and less than the size of the array, and may
  be any object convertible to a CBOR integer.

- <code>defaultValue</code> - A value to return if an item with the given key doesn't
  exist, or if the CBOR object is an array and the key is not an
  integer 0 or greater and less than the size of the array.

**Returns:**

- The CBOR object referred to by index or key in this array or map. If
  this is a CBOR map, returns <code>null</code> (not <code>
  CBORObject.Null</code>) if an item with the given key doesn't exist.

### get

    public CBORObject get​(CBORObject key)

Gets the value of a CBOR object by integer index in this array or by CBOR
object key in this map.

**Parameters:**

- <code>key</code> - A CBOR object serving as the key to the map or index to the
  array. If this is a CBOR array, the key must be an integer 0 or
  greater and less than the size of the array.

**Returns:**

- The CBOR object referred to by index or key in this array or map. If
  this is a CBOR map, returns <code>null</code> (not <code>
  CBORObject.Null</code>) if an item with the given key doesn't exist.

**Throws:**

- <code>java.lang.NullPointerException</code> - The key is null (as opposed to
  CBORObject.Null); or the set method is called and the value is null.

- <code>java.lang.IllegalArgumentException</code> - This CBOR object is an array and the key is not an
  integer 0 or greater and less than the size of the array.

- <code>java.lang.IllegalStateException</code> - This object is not a map or an array.

### set

    public void set​(CBORObject key, CBORObject value)

Sets the value of a CBOR object by integer index in this array or by CBOR
object key in this map.

**Parameters:**

- <code>key</code> - A CBOR object serving as the key to the map or index to the
  array. If this is a CBOR array, the key must be an integer 0 or
  greater and less than the size of the array.

**Throws:**

- <code>java.lang.NullPointerException</code> - The key is null (as opposed to
  CBORObject.Null); or the set method is called and the value is null.

- <code>java.lang.IllegalArgumentException</code> - This CBOR object is an array and the key is not an
  integer 0 or greater and less than the size of the array.

- <code>java.lang.IllegalStateException</code> - This object is not a map or an array.

### get

    public CBORObject get​(java.lang.String key)

Gets the value of a CBOR object in this map, using a string as the key.

**Parameters:**

- <code>key</code> - A key that points to the desired value.

**Returns:**

- The CBOR object referred to by key in this map. Returns <code>null</code>
  if an item with the given key doesn't exist.

**Throws:**

- <code>java.lang.NullPointerException</code> - The key is null.

- <code>java.lang.IllegalStateException</code> - This object is not a map.

### set

    public void set​(java.lang.String key, CBORObject value)

Sets the value of a CBOR object in this map, using a string as the key.

**Parameters:**

- <code>key</code> - A key that points to the desired value.

**Throws:**

- <code>java.lang.NullPointerException</code> - The key is null.

- <code>java.lang.IllegalStateException</code> - This object is not a map.

### Addition

    @Deprecated public static CBORObject Addition​(CBORObject first, CBORObject second)

Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
use the first number's.Add() method.

**Parameters:**

- <code>first</code> - The parameter <code>first</code> is a CBOR object.

- <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

- A CBOR object.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - Either or both operands are not numbers (as
  opposed to Not-a-Number, NaN).

- <code>java.lang.NullPointerException</code> - The parameter <code>first</code> or <code>second</code>
  is null.

### DecodeFromBytes

    public static CBORObject DecodeFromBytes​(byte[] data)

<p>Generates a CBOR object from an array of CBOR-encoded bytes.</p>

**Parameters:**

- <code>data</code> - A byte array in which a single CBOR object is encoded.

**Returns:**

- A CBOR object decoded from the given byte array.

**Throws:**

- <code>CBORException</code> - There was an error in reading or
  parsing the data. This includes cases where not all of the byte
  array represents a CBOR object. This exception is also thrown if the
  parameter <code>data</code> is empty.

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null.

### DecodeSequenceFromBytes

    public static CBORObject[] DecodeSequenceFromBytes​(byte[] data)

<p>Generates a sequence of CBOR objects from an array of CBOR-encoded
 bytes.</p>

**Parameters:**

- <code>data</code> - A byte array in which any number of CBOR objects (including
  zero) are encoded, one after the other. Can be empty, but cannot be
  null.

**Returns:**

- An array of CBOR objects decoded from the given byte array. Returns
  an empty array if <code>data</code> is empty.

**Throws:**

- <code>CBORException</code> - There was an error in reading or
  parsing the data. This includes cases where the last CBOR object in
  the data was read only partly.

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null.

### DecodeSequenceFromBytes

    public static CBORObject[] DecodeSequenceFromBytes​(byte[] data, CBOREncodeOptions options)

<p>Generates a sequence of CBOR objects from an array of CBOR-encoded
 bytes.</p>

**Parameters:**

- <code>data</code> - A byte array in which any number of CBOR objects (including
  zero) are encoded, one after the other. Can be empty, but cannot be
  null.

- <code>options</code> - Specifies options to control how the CBOR object is decoded.
  See <code>CBOREncodeOptions</code> for more
  information. In this method, the AllowEmpty property is treated as
  always set regardless of that value as specified in this parameter.

**Returns:**

- An array of CBOR objects decoded from the given byte array. Returns
  an empty array if <code>data</code> is empty.

**Throws:**

- <code>CBORException</code> - There was an error in reading or
  parsing the data. This includes cases where the last CBOR object in
  the data was read only partly.

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null, or the
  parameter <code>options</code> is null.

### FromJSONSequenceBytes

    public static CBORObject[] FromJSONSequenceBytes​(byte[] bytes)

Generates a list of CBOR objects from an array of bytes in JavaScript object
Notation (JSON) text sequence format (RFC 7464). The byte array must
be in UTF-8 encoding and may not begin with a byte-order mark
(U+FEFF).<p>Generally, each JSON text in a JSON text sequence is
written as follows: Write a record separator byte (0x1e), then write
the JSON text in UTF-8 (without a byte order mark, U+FEFF), then
write the line feed byte (0x0a). RFC 7464, however, uses a more
liberal syntax for parsing JSON text sequences.</p>

**Parameters:**

- <code>bytes</code> - A byte array in which a JSON text sequence is encoded.

**Returns:**

- A list of CBOR objects read from the JSON sequence. Objects that
  could not be parsed are replaced with <code>null</code> (as opposed to
  <code>CBORObject.Null</code>) in the given list.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bytes</code> is null.

- <code>CBORException</code> - The byte array is not empty and
  does not begin with a record separator byte (0x1e), or an I/O error
  occurred.

### ToJSONBytes

    public byte[] ToJSONBytes()

Converts this object to a byte array in JavaScript object Notation (JSON)
format. The JSON text will be written out in UTF-8 encoding, without
a byte order mark, to the byte array. See the overload to
ToJSONString taking a JSONOptions argument for further information.

**Returns:**

- A byte array containing the converted in JSON format.

### ToJSONBytes

    public byte[] ToJSONBytes​(JSONOptions jsonoptions)

Converts this object to a byte array in JavaScript object Notation (JSON)
format. The JSON text will be written out in UTF-8 encoding, without
a byte order mark, to the byte array. See the overload to
ToJSONString taking a JSONOptions argument for further information.

**Parameters:**

- <code>jsonoptions</code> - Specifies options to control writing the CBOR object to
  JSON.

**Returns:**

- A byte array containing the converted object in JSON format.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>jsonoptions</code> is null.

### FromJSONSequenceBytes

    public static CBORObject[] FromJSONSequenceBytes​(byte[] data, JSONOptions options)

Generates a list of CBOR objects from an array of bytes in JavaScript object
Notation (JSON) text sequence format (RFC 7464), using the specified
options to control the decoding process. The byte array must be in
UTF-8 encoding and may not begin with a byte-order mark
(U+FEFF).<p>Generally, each JSON text in a JSON text sequence is
written as follows: Write a record separator byte (0x1e), then write
the JSON text in UTF-8 (without a byte order mark, U+FEFF), then
write the line feed byte (0x0a). RFC 7464, however, uses a more
liberal syntax for parsing JSON text sequences.</p>

**Parameters:**

- <code>data</code> - A byte array in which a JSON text sequence is encoded.

- <code>options</code> - Specifies options to control the JSON decoding process.

**Returns:**

- A list of CBOR objects read from the JSON sequence. Objects that
  could not be parsed are replaced with <code>null</code> (as opposed to
  <code>CBORObject.Null</code>) in the given list.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null.

- <code>CBORException</code> - The byte array is not empty and
  does not begin with a record separator byte (0x1e), or an I/O error
  occurred.

### DecodeFromBytes

    public static CBORObject DecodeFromBytes​(byte[] data, CBOREncodeOptions options)

Generates a CBOR object from an array of CBOR-encoded bytes, using the given
<code>CBOREncodeOptions</code> object to control the decoding process.<p>

 </p><p>The following example (originally written in C# for the.NET
 version) implements a method that decodes a text string from a CBOR
 byte array. It's successful only if the CBOR object contains an
  untagged text string.</p> <pre>private static string DecodeTextString(byte[] bytes) { if (bytes == null) { throw new NullPointerException("mapObj");} if (bytes.length == 0 || bytes[0]&lt;0x60 || bytes[0]&gt;0x7f) {throw new CBORException();} return CBORObject.DecodeFromBytes(bytes, CBOREncodeOptions.Default).AsString(); }</pre>.

**Parameters:**

- <code>data</code> - A byte array in which a single CBOR object is encoded.

- <code>options</code> - Specifies options to control how the CBOR object is decoded.
  See <code>CBOREncodeOptions</code> for more
  information.

**Returns:**

- A CBOR object decoded from the given byte array. Returns null (as
  opposed to CBORObject.Null) if <code>data</code> is empty and the
  AllowEmpty property is set on the given options object.

**Throws:**

- <code>CBORException</code> - There was an error in reading or
  parsing the data. This includes cases where not all of the byte
  array represents a CBOR object. This exception is also thrown if the
  parameter <code>data</code> is empty unless the AllowEmpty property is
  set on the given options object.

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null, or the
  parameter <code>options</code> is null.

### Divide

    @Deprecated public static CBORObject Divide​(CBORObject first, CBORObject second)

Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
use the first number's.Divide() method.

**Parameters:**

- <code>first</code> - The parameter <code>first</code> is a CBOR object.

- <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

- The quotient of the two objects.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>first</code> or <code>second</code>
  is null.

### FromJSONString

    public static CBORObject FromJSONString​(java.lang.String str, int offset, int count)

<p>Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a
 CBORException is thrown. This is a change in version 4.0.</p>
 <p>Note that if a CBOR object is converted to JSON with
 <code>ToJSONString</code>, then the JSON is converted back to CBOR with
 this method, the new CBOR object will not necessarily be the same as
 the old CBOR object, especially if the old CBOR object uses data
 types not supported in JSON, such as integers in map keys.</p>

**Parameters:**

- <code>str</code> - A text string in JSON format. The entire string must contain a
  single JSON object and not multiple objects. The string may not
  begin with a byte-order mark (U+FEFF).

- <code>offset</code> - An index, starting at 0, showing where the desired portion of
  <code>str</code> begins.

- <code>count</code> - The length, in code units, of the desired portion of <code>
  str</code> (but not more than <code>str</code> 's length).

**Returns:**

- A CBOR object.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

- <code>CBORException</code> - The string is not in JSON format.

- <code>java.lang.IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
  than 0 or greater than <code>str</code> 's length, or <code>str</code> 's
  length minus <code>offset</code> is less than <code>count</code>.

### FromJSONString

    public static CBORObject FromJSONString​(java.lang.String str, JSONOptions jsonoptions)

Generates a CBOR object from a text string in JavaScript object Notation
(JSON) format, using the specified options to control the decoding
process. <p>Note that if a CBOR object is converted to JSON with
<code>ToJSONString</code>, then the JSON is converted back to CBOR with
this method, the new CBOR object will not necessarily be the same as
the old CBOR object, especially if the old CBOR object uses data
types not supported in JSON, such as integers in map keys.</p>

**Parameters:**

- <code>str</code> - A text string in JSON format. The entire string must contain a
  single JSON object and not multiple objects. The string may not
  begin with a byte-order mark (U+FEFF).

- <code>jsonoptions</code> - Specifies options to control the JSON decoding process.

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>str</code> or <code>
  jsonoptions</code> is null.

- <code>CBORException</code> - The string is not in JSON format.

### FromJSONString

    public static CBORObject FromJSONString​(java.lang.String str)

<p>Generates a CBOR object from a text string in JavaScript object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a
 CBORException is thrown. This is a change in version 4.0.</p>
 <p>Note that if a CBOR object is converted to JSON with
 <code>ToJSONString</code>, then the JSON is converted back to CBOR with
 this method, the new CBOR object will not necessarily be the same as
 the old CBOR object, especially if the old CBOR object uses data
 types not supported in JSON, such as integers in map keys.</p>

**Parameters:**

- <code>str</code> - A text string in JSON format. The entire string must contain a
  single JSON object and not multiple objects. The string may not
  begin with a byte-order mark (U+FEFF).

**Returns:**

- A CBOR object.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

- <code>CBORException</code> - The string is not in JSON format.

### FromJSONString

    @Deprecated public static CBORObject FromJSONString​(java.lang.String str, CBOREncodeOptions options)

Deprecated.
Instead, use.getFromJSONString()(str,
new JSONOptions(\allowduplicatekeys = true\))
or .getFromJSONString()(str, new
JSONOptions(\allowduplicatekeys = false\)), as appropriate.

**Parameters:**

- <code>str</code> - A text string in JSON format. The entire string must contain a
  single JSON object and not multiple objects. The string may not
  begin with a byte-order mark (U+FEFF).

- <code>options</code> - Specifies options to control the decoding process. This
  method uses only the AllowDuplicateKeys property of this object.

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>str</code> or <code>options</code>
  is null.

- <code>CBORException</code> - The string is not in JSON format.

### FromJSONString

    public static CBORObject FromJSONString​(java.lang.String str, int offset, int count, JSONOptions jsonoptions)

Generates a CBOR object from a text string in JavaScript object Notation
(JSON) format, using the specified options to control the decoding
process. <p>Note that if a CBOR object is converted to JSON with
<code>ToJSONString</code>, then the JSON is converted back to CBOR with
this method, the new CBOR object will not necessarily be the same as
the old CBOR object, especially if the old CBOR object uses data
types not supported in JSON, such as integers in map keys.</p>

**Parameters:**

- <code>str</code> - The parameter <code>str</code> is a text string.

- <code>offset</code> - An index, starting at 0, showing where the desired portion of
  <code>str</code> begins.

- <code>count</code> - The length, in code units, of the desired portion of <code>
  str</code> (but not more than <code>str</code> 's length).

- <code>jsonoptions</code> - The parameter <code>jsonoptions</code> is a Cbor.JSONOptions
  object.

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>str</code> or <code>
  jsonoptions</code> is null.

- <code>CBORException</code> - The string is not in JSON format.

- <code>java.lang.IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
  than 0 or greater than <code>str</code> 's length, or <code>str</code> 's
  length minus <code>offset</code> is less than <code>count</code>.

### ToObject

    public <T> T ToObject​(java.lang.reflect.Type t)

Converts this CBOR object to an object of an arbitrary type. See the
documentation for the overload of this method taking a
CBORTypeMapper parameter for more information. This method doesn't
use a CBORTypeMapper parameter to restrict which data types are
eligible for Plain-Old-Data serialization.<p> </p><p>Java offers no easy
way to express a generic type, at least none as easy as C#'s
<code>typeof</code> operator. The following example, written in Java, is a
way to specify that the return value will be an ArrayList of string
objects.</p> <pre>Type arrayListString = new ParameterizedType() { public Type[] getActualTypeArguments() { /_ Contains one type parameter, string_/ return new Type[] { string.class }; } public Type getRawType() { /_ Raw type is ArrayList _/ return ArrayList.class; } public Type getOwnerType() { return null; } }; ArrayList&lt;string&gt; array = (ArrayList&lt;string&gt;) cborArray.ToObject(arrayListString);</pre> <p>By comparison, the C#
version is much shorter.</p> <pre>List&lt;string&gt; array = (List&lt;string&gt;)cborArray.ToObject(typeof(List&lt;string&gt;));</pre>.

**Parameters:**

- <code>t</code> - The type, class, or interface that this method's return value will
  belong to. To express a generic type in Java, see the example.
  <b>Note:</b> For security reasons, an application should not base
  this parameter on user input or other externally supplied data.
  Whenever possible, this parameter should be either a type specially
  handled by this method (such as <code>int</code> or <code>string</code>) or a
  plain-old-data type (POCO or POJO type) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.

**Returns:**

- The converted object.

**Throws:**

- <code>CBORException</code> - The given type <code>t</code> , or
  this object's CBOR type, is not supported, or the given object's
  nesting is too deep, or another error occurred when serializing the
  object.

- <code>java.lang.NullPointerException</code> - The parameter <code>t</code> is null.

### ToObject

    public <T> T ToObject​(java.lang.reflect.Type t, CBORTypeMapper mapper)

Converts this CBOR object to an object of an arbitrary type. See the
documentation for the overload of this method taking a
CBORTypeMapper and PODOptions parameters parameters for more
information.

**Parameters:**

- <code>t</code> - The type, class, or interface that this method's return value will
  belong to. To express a generic type in Java, see the example.
  <b>Note:</b> For security reasons, an application should not base
  this parameter on user input or other externally supplied data.
  Whenever possible, this parameter should be either a type specially
  handled by this method (such as <code>int</code> or <code>string</code>) or a
  plain-old-data type (POCO or POJO type) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.

- <code>mapper</code> - This parameter controls which data types are eligible for
  Plain-Old-Data deserialization and includes custom converters from
  CBOR objects to certain data types.

**Returns:**

- The converted object.

**Throws:**

- <code>CBORException</code> - The given type <code>t</code>, or this
  object's CBOR type, is not supported, or the given object's nesting
  is too deep, or another error occurred when serializing the object.

- <code>java.lang.NullPointerException</code> - The parameter <code>t</code> is null.

### ToObject

    public <T> T ToObject​(java.lang.reflect.Type t, PODOptions options)

Converts this CBOR object to an object of an arbitrary type. See the
documentation for the overload of this method taking a
CBORTypeMapper and PODOptions parameters for more information. This
method (without a CBORTypeMapper parameter) allows all data types
not otherwise handled to be eligible for Plain-Old-Data
serialization.

**Parameters:**

- <code>t</code> - The type, class, or interface that this method's return value will
  belong to. To express a generic type in Java, see the example.
  <b>Note:</b> For security reasons, an application should not base
  this parameter on user input or other externally supplied data.
  Whenever possible, this parameter should be either a type specially
  handled by this method (such as <code>int</code> or <code>string</code>) or a
  plain-old-data type (POCO or POJO type) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.

- <code>options</code> - Specifies options for controlling deserialization of CBOR
  objects.

**Returns:**

- The converted object.

**Throws:**

- <code>java.lang.UnsupportedOperationException</code> - The given type <code>t</code>, or this object's
  CBOR type, is not supported.

- <code>java.lang.NullPointerException</code> - The parameter <code>t</code> is null.

- <code>CBORException</code> - The given object's nesting is too
  deep, or another error occurred when serializing the object.

### ToObject

    public <T> T ToObject​(java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions options)

<p>Converts this CBOR object to an object of an arbitrary type. The
 following cases are checked in the logical order given (rather than
 the strict order in which they are implemented by this library):</p>
 <ul><li>If the type is <code>CBORObject</code> , return this object.</li>
 <li>If the given object is <code>CBORObject.Null</code> (with or without
 tags), returns <code>null</code> .</li> <li>If the object is of a type
 corresponding to a type converter mentioned in the <paramref name='mapper'/> parameter, that converter will be used to convert
 the CBOR object to an object of the given type. Type converters can
 be used to override the default conversion behavior of almost any
 object.</li> <li>If the type is <code>object</code> , return this
 object.</li> <li>If the type is <code>char</code> , converts
 single-character CBOR text strings and CBOR integers from 0 through
 65535 to a <code>char</code> object and returns that <code>char</code>
 object.</li> <li>If the type is <code>boolean</code> (<code>boolean</code> in
 Java), returns the result of AsBoolean.</li> <li>If the type is
 <code>short</code> , returns this number as a 16-bit signed integer after
 converting its value to an integer by discarding its fractional
 part, and throws an exception if this object's value is infinity or
 a not-a-number value, or does not represent a number (currently
 IllegalStateException, but may change in the next major version), or
 if the value, once converted to an integer by discarding its
 fractional part, is less than -32768 or greater than 32767
 (currently ArithmeticException, but may change in the next major
 version).</li> <li>If the type is <code>long</code> , returns this number
 as a 64-bit signed integer after converting its value to an integer
 by discarding its fractional part, and throws an exception if this
 object's value is infinity or a not-a-number value, or does not
 represent a number (currently IllegalStateException, but may change
 in the next major version), or if the value, once converted to an
 integer by discarding its fractional part, is less than -2^63 or
 greater than 2^63-1 (currently ArithmeticException, but may change in
 the next major version).</li> <li>If the type is <code>short</code> , the
 same rules as for <code>long</code> are used, but the range is from -32768
 through 32767 and the return type is <code>short</code> .</li> <li>If the
 type is <code>byte</code> , the same rules as for <code>long</code> are used,
 but the range is from 0 through 255 and the return type is
 <code>byte</code> .</li> <li>If the type is <code>sbyte</code> , the same rules
 as for <code>long</code> are used, but the range is from -128 through 127
 and the return type is <code>sbyte</code> .</li> <li>If the type is
 <code>ushort</code> , the same rules as for <code>long</code> are used, but the
 range is from 0 through 65535 and the return type is <code>ushort</code>
.</li> <li>If the type is <code>uint</code> , the same rules as for
 <code>long</code> are used, but the range is from 0 through 2^31-1 and the
 return type is <code>uint</code> .</li> <li>If the type is <code>ulong</code> ,
 the same rules as for <code>long</code> are used, but the range is from 0
 through 2^63-1 and the return type is <code>ulong</code> .</li> <li>If the
 type is <code>int</code> or a primitive floating-point type (<code>float</code>
 , <code>double</code> , as well as <code>decimal</code> in.NET), returns the
 result of the corresponding As* method.</li> <li>If the type is
 <code>string</code> , returns the result of AsString.</li> <li>If the type
 is <code>EFloat</code> , <code>EDecimal</code> , <code>EInteger</code> , or
 <code>ERational</code> in the <code>PeterO.Numbers</code>
  library (in .NET) or the <code>com.github.peteroupc/numbers</code>
  artifact (in Java), or if the type is <code>BigInteger</code> or
 <code>BigDecimal</code> in the Java version, converts the given object to
 a number of the corresponding type and throws an exception
 (currently IllegalStateException) if the object does not represent a
 number (for this purpose, infinity and not-a-number values, but not
 <code>CBORObject.Null</code> , are considered numbers). Currently, this is
 equivalent to the result of <code>AsEFloat()</code> , <code>AsEDecimal()</code>
 , <code>AsEInteger</code> , or <code>AsERational()</code> , respectively, but
 may change slightly in the next major version. Note that in the case
 of <code>EFloat</code> , if this object represents a decimal number with a
 fractional part, the conversion may lose information depending on
 the number, and if the object is a rational number with a
 nonterminating binary expansion, the number returned is a binary
 floating-point number rounded to a high but limited precision. In
 the case of <code>EDecimal</code> , if this object expresses a rational
 number with a nonterminating decimal expansion, returns a decimal
 number rounded to 34 digits of precision. In the case of
 <code>EInteger</code> , if this CBOR object expresses a floating-point
 number, it is converted to an integer by discarding its fractional
 part, and if this CBOR object expresses a rational number, it is
 converted to an integer by dividing the numerator by the denominator
 and discarding the fractional part of the result, and this method
 throws an exception (currently ArithmeticException, but may change in
 the next major version) if this object expresses infinity or a
 not-a-number value.</li> <li>In the.NET version, if the type is a
 nullable (e.g., <code>Nullable&lt;int&gt;</code> or <code>int?</code> , returns
 <code>null</code> if this CBOR object is null, or this object's value
 converted to the nullable's underlying type, e.g., <code>int</code> .</li>
 <li>If the type is an enumeration (<code>Enum</code>) type and this CBOR
 object is a text string or an integer, returns the appropriate
 enumerated constant. (For example, if <code>MyEnum</code> includes an
 entry for <code>MyValue</code> , this method will return
  <code>MyEnum.MyValue</code> if the CBOR object represents <code>"MyValue"</code>
 or the underlying value for <code>MyEnum.MyValue</code> .) <b>Note:</b> If
 an integer is converted to a.NET Enum constant, and that integer is
 shared by more than one constant of the same type, it is undefined
 which constant from among them is returned. (For example, if
 <code>MyEnum.Zero = 0</code> and <code>MyEnum.Null = 0</code> , converting 0 to
 <code>MyEnum</code> may return either <code>MyEnum.Zero</code> or
 <code>MyEnum.Null</code> .) As a result, .NET Enum types with constants
 that share an underlying value should not be passed to this
 method.</li> <li>If the type is <code>byte[]</code> (a one-dimensional
 byte array) and this CBOR object is a byte string, returns a byte
 array which this CBOR byte string's data will be copied to. (This
 method can't be used to encode CBOR data to a byte array; for that,
 use the EncodeToBytes method instead.)</li> <li>If the type is a
 one-dimensional or multidimensional array type and this CBOR object
 is an array, returns an array containing the items in this CBOR
 object.</li> <li>If the type is List or the generic or non-generic
 List, ICollection, or Iterable, (or ArrayList, List, Collection, or
 Iterable in Java), and if this CBOR object is an array, returns an
 object conforming to the type, class, or interface passed to this
 method, where the object will contain all items in this CBOR
 array.</li> <li>If the type is Dictionary or the generic or
 non-generic Map (or HashMap or Map in Java), and if this CBOR object
 is a map, returns an object conforming to the type, class, or
 interface passed to this method, where the object will contain all
 keys and values in this CBOR map.</li> <li>If the type is an
  enumeration constant ("enum"), and this CBOR object is an integer or
 text string, returns the enumeration constant with the given number
 or name, respectively. (Enumeration constants made up of multiple
 enumeration constants, as allowed by .NET, can only be matched by
 number this way.)</li> <li>If the type is <code>java.util.Date</code> (or
 <code>Date</code> in Java) , returns a date/time object if the CBOR
 object's outermost tag is 0 or 1. For tag 1, this method treats the
 CBOR object as a number of seconds since the start of 1970, which is
  based on the POSIX definition of "seconds since the Epoch", a
 definition that does not count leap seconds. In this method, this
 number of seconds assumes the use of a proleptic Gregorian calendar,
 in which the rules regarding the number of days in each month and
 which years are leap years are the same for all years as they were
 in 1970 (including without regard to time zone differences or
 transitions from other calendars to the Gregorian). The string
 format used in tag 0 supports only years up to 4 decimal digits
 long. For tag 1, CBOR objects that express infinity or not-a-number
 (NaN) are treated as invalid by this method. This default behavior
 for <code>java.util.Date</code> and <code>Date</code> can be changed by passing a
 suitable CBORTypeMapper to this method, such as a CBORTypeMapper
 that registers a CBORDateConverter for <code>java.util.Date</code> or
 <code>Date</code> objects. See the examples.</li> <li>If the type is
 <code>java.net.URI</code> (or <code>URI</code> in Java), returns a URI object if
 possible.</li> <li>If the type is <code>java.util.UUID</code> (or <code>UUID</code> in
 Java), returns a UUID object if possible.</li> <li>Plain-Old-Data
 deserialization: If the object is a type not specially handled
 above, the type includes a zero-parameter constructor (default or
  not), this CBOR object is a CBOR map, and the "mapper" parameter (if
 any) allows this type to be eligible for Plain-Old-Data
 deserialization, then this method checks the given type for eligible
 setters as follows:</li> <li>(*) In the .NET version, eligible
 setters are the public, nonstatic setters of properties with a
 public, nonstatic getter. Eligible setters also include public,
 nonstatic, non- <code>static final</code> , non- <code>readonly</code> fields. If a
  class has two properties and/or fields of the form "X" and "IsX",
  where "X" is any name, or has multiple properties and/or fields with
 the same name, those properties and fields are ignored.</li> <li>(*)
 In the Java version, eligible setters are public, nonstatic methods
  starting with "set" followed by a character other than a basic digit
  or lower-case letter, that is, other than "a" to "z" or "0" to "9",
 that take one parameter. The class containing an eligible setter
 must have a public, nonstatic method with the same name, but
  starting with "get" or "is" rather than "set", that takes no
 parameters and does not return void. (For example, if a class has
  "public setValue(string)" and "public getValue()", "setValue" is an
  eligible setter. However, "setValue()" and "setValue(string, int)"
 are not eligible setters.) In addition, public, nonstatic, nonfinal
 fields are also eligible setters. If a class has two or more
 otherwise eligible setters (methods and/or fields) with the same
 name, but different parameter type, they are not eligible
 setters.</li> <li>Then, the method creates an object of the given
 type and invokes each eligible setter with the corresponding value
 in the CBOR map, if any. Key names in the map are matched to
 eligible setters according to the rules described in the <code>PODOptions</code> documentation. Note that for
 security reasons, certain types are not supported even if they
 contain eligible setters. For the Java version, the object creation
 may fail in the case of a nested nonstatic class.</li> </ul><p>
 </p><p>The following example (originally written in C# for the DotNet
 version) uses a CBORTypeMapper to change how CBOR objects are
 converted to java.util.Date objects. In this case, the ToObject method
 assumes the CBOR object is an untagged number giving the number of
 seconds since the start of 1970.</p> <pre>CBORTypeMapper conv = new CBORTypeMapper().AddConverter(java.util.Date.class, CBORDateConverter.UntaggedNumber); var obj = CBORObject.FromObject().getToObject()&lt;java.util.Date&gt;(conv);</pre>
 <p>Java offers no easy way to express a generic type, at least none
 as easy as C#'s <code>typeof</code> operator. The following example,
 written in Java, is a way to specify that the return value will be
 an ArrayList of string objects.</p> <pre>Type arrayListString = new ParameterizedType() { public Type[] getActualTypeArguments() { /* Contains one type parameter, string*/ return new Type[] { string.class }; } public Type getRawType() { /* Raw type is ArrayList */ return ArrayList.class; } public Type getOwnerType() { return null; } }; ArrayList&lt;string&gt; array = (ArrayList&lt;string&gt;) cborArray.ToObject(arrayListString);</pre>
 <p>By comparison, the C# version is much shorter.</p>
 <pre>List&lt;string&gt; array = (List&lt;string&gt;)cborArray.ToObject(typeof(List&lt;string&gt;));</pre> .

**Parameters:**

- <code>t</code> - The type, class, or interface that this method's return value will
  belong to. To express a generic type in Java, see the example.
  <b>Note:</b> For security reasons, an application should not base
  this parameter on user input or other externally supplied data.
  Whenever possible, this parameter should be either a type specially
  handled by this method, such as <code>int</code> or <code>string</code> , or a
  plain-old-data type (POCO or POJO type) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.

- <code>mapper</code> - This parameter controls which data types are eligible for
  Plain-Old-Data deserialization and includes custom converters from
  CBOR objects to certain data types. Can be null.

- <code>options</code> - Specifies options for controlling deserialization of CBOR
  objects.

**Returns:**

- The converted object.

**Throws:**

- <code>CBORException</code> - The given type <code>t</code> , or
  this object's CBOR type, is not supported, or the given object's
  nesting is too deep, or another error occurred when serializing the
  object.

- <code>java.lang.NullPointerException</code> - The parameter <code>t</code> or <code>options</code> is
  null.

### DecodeObjectFromBytes

    public static <T> T DecodeObjectFromBytes​(byte[] data, CBOREncodeOptions enc, java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions pod)

Generates an object of an arbitrary type from an array of CBOR-encoded
bytes, using the given <code>CBOREncodeOptions</code> object to control
the decoding process. It is equivalent to DecodeFromBytes followed
by ToObject. See the documentation for those methods for more
information.

**Parameters:**

- <code>data</code> - A byte array in which a single CBOR object is encoded.

- <code>enc</code> - Specifies options to control how the CBOR object is decoded. See
  <code>CBOREncodeOptions</code> for more information.

- <code>t</code> - The type, class, or interface that this method's return value will
  belong to. To express a generic type in Java, see the example.
  <b>Note:</b> For security reasons, an application should not base
  this parameter on user input or other externally supplied data.
  Whenever possible, this parameter should be either a type specially
  handled by this method, such as <code>int</code> or <code>string</code>, or a
  plain-old-data type (POCO or POJO type) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.

- <code>mapper</code> - This parameter controls which data types are eligible for
  Plain-Old-Data deserialization and includes custom converters from
  CBOR objects to certain data types. Can be null.

- <code>pod</code> - Specifies options for controlling deserialization of CBOR
  objects.

**Returns:**

- An object of the given type decoded from the given byte array.
  Returns null (as opposed to CBORObject.Null) if <code>data</code> is
  empty and the AllowEmpty property is set on the given
  CBOREncodeOptions object.

**Throws:**

- <code>CBORException</code> - There was an error in reading or
  parsing the data. This includes cases where not all of the byte
  array represents a CBOR object. This exception is also thrown if the
  parameter <code>data</code> is empty unless the AllowEmpty property is
  set on the given options object. Also thrown if the given type
  <code>t</code>, or this object's CBOR type, is not supported, or the
  given object's nesting is too deep, or another error occurred when
  serializing the object.

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null, or the
  parameter <code>enc</code> is null, or the parameter <code>t</code> or <code>
  pod</code> is null.

### DecodeObjectFromBytes

    public static <T> T DecodeObjectFromBytes​(byte[] data, CBOREncodeOptions enc, java.lang.reflect.Type t)

Generates an object of an arbitrary type from an array of CBOR-encoded
bytes, using the given <code>CBOREncodeOptions</code> object to control
the decoding process. It is equivalent to DecodeFromBytes followed
by ToObject. See the documentation for those methods for more
information.

**Parameters:**

- <code>data</code> - A byte array in which a single CBOR object is encoded.

- <code>enc</code> - Specifies options to control how the CBOR object is decoded. See
  <code>CBOREncodeOptions</code> for more information.

- <code>t</code> - The type, class, or interface that this method's return value will
  belong to. To express a generic type in Java, see the example.
  <b>Note:</b> For security reasons, an application should not base
  this parameter on user input or other externally supplied data.
  Whenever possible, this parameter should be either a type specially
  handled by this method, such as <code>int</code> or <code>string</code>, or a
  plain-old-data type (POCO or POJO type) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.

**Returns:**

- An object of the given type decoded from the given byte array.
  Returns null (as opposed to CBORObject.Null) if <code>data</code> is
  empty and the AllowEmpty property is set on the given
  CBOREncodeOptions object.

**Throws:**

- <code>CBORException</code> - There was an error in reading or
  parsing the data. This includes cases where not all of the byte
  array represents a CBOR object. This exception is also thrown if the
  parameter <code>data</code> is empty unless the AllowEmpty property is
  set on the given options object. Also thrown if the given type
  <code>t</code>, or this object's CBOR type, is not supported, or the
  given object's nesting is too deep, or another error occurred when
  serializing the object.

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null, or the
  parameter <code>enc</code> is null, or the parameter <code>t</code> is null.

### DecodeObjectFromBytes

    public static <T> T DecodeObjectFromBytes​(byte[] data, java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions pod)

Generates an object of an arbitrary type from an array of CBOR-encoded
bytes. It is equivalent to DecodeFromBytes followed by ToObject. See
the documentation for those methods for more information.

**Parameters:**

- <code>data</code> - A byte array in which a single CBOR object is encoded.

- <code>t</code> - The type, class, or interface that this method's return value will
  belong to. To express a generic type in Java, see the example.
  <b>Note:</b> For security reasons, an application should not base
  this parameter on user input or other externally supplied data.
  Whenever possible, this parameter should be either a type specially
  handled by this method, such as <code>int</code> or <code>string</code>, or a
  plain-old-data type (POCO or POJO type) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.

- <code>mapper</code> - This parameter controls which data types are eligible for
  Plain-Old-Data deserialization and includes custom converters from
  CBOR objects to certain data types. Can be null.

- <code>pod</code> - Specifies options for controlling deserialization of CBOR
  objects.

**Returns:**

- An object of the given type decoded from the given byte array.
  Returns null (as opposed to CBORObject.Null) if <code>data</code> is
  empty and the AllowEmpty property is set on the given
  CBOREncodeOptions object.

**Throws:**

- <code>CBORException</code> - There was an error in reading or
  parsing the data. This includes cases where not all of the byte
  array represents a CBOR object. This exception is also thrown if the
  parameter <code>data</code> is empty unless the AllowEmpty property is
  set on the given options object. Also thrown if the given type
  <code>t</code>, or this object's CBOR type, is not supported, or the
  given object's nesting is too deep, or another error occurred when
  serializing the object.

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null, or the
  parameter <code>t</code> or <code>pod</code> is null.

### DecodeObjectFromBytes

    public static <T> T DecodeObjectFromBytes​(byte[] data, java.lang.reflect.Type t)

Generates an object of an arbitrary type from an array of CBOR-encoded
bytes. It is equivalent to DecodeFromBytes followed by ToObject. See
the documentation for those methods for more information.

**Parameters:**

- <code>data</code> - A byte array in which a single CBOR object is encoded.

- <code>t</code> - The type, class, or interface that this method's return value will
  belong to. To express a generic type in Java, see the example.
  <b>Note:</b> For security reasons, an application should not base
  this parameter on user input or other externally supplied data.
  Whenever possible, this parameter should be either a type specially
  handled by this method, such as <code>int</code> or <code>string</code>, or a
  plain-old-data type (POCO or POJO type) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.

**Returns:**

- An object of the given type decoded from the given byte array.
  Returns null (as opposed to CBORObject.Null) if <code>data</code> is
  empty and the AllowEmpty property is set on the given
  CBOREncodeOptions object.

**Throws:**

- <code>CBORException</code> - There was an error in reading or
  parsing the data. This includes cases where not all of the byte
  array represents a CBOR object. This exception is also thrown if the
  parameter <code>data</code> is empty unless the AllowEmpty property is
  set on the given options object. Also thrown if the given type
  <code>t</code>, or this object's CBOR type, is not supported, or the
  given object's nesting is too deep, or another error occurred when
  serializing the object.

- <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null, or the
  parameter <code>t</code> is null.

### FromObject

    public static CBORObject FromObject​(long value)

Generates a CBOR object from a 64-bit signed integer.

**Parameters:**

- <code>value</code> - The parameter <code>value</code> is a 64-bit signed integer.

**Returns:**

- A CBOR object.

### FromObject

    public static CBORObject FromObject​(CBORObject value)

Generates a CBOR object from a CBOR object.

**Parameters:**

- <code>value</code> - The parameter <code>value</code> is a CBOR object.

**Returns:**

- Same as <code>value</code>, or "CBORObject.Null" is <code>value</code> is
  null.

### CalcEncodedSize

    public long CalcEncodedSize()

Calculates the number of bytes this CBOR object takes when serialized as a
byte array using the <code>EncodeToBytes()</code> method. This calculation
assumes that integers, lengths of maps and arrays, lengths of text
and byte strings, and tag numbers are encoded in their shortest
form; that floating-point numbers are encoded in their shortest
value-preserving form; and that no indefinite-length encodings are
used.

**Returns:**

- The number of bytes this CBOR object takes when serialized as a byte
  array using the <code>EncodeToBytes()</code> method.

**Throws:**

- <code>CBORException</code> - The CBOR object has an extremely
  deep level of nesting, including if the CBOR object is or has an
  array or map that includes itself.

### FromObject

    public static CBORObject FromObject​(com.upokecenter.numbers.EInteger bigintValue)

Generates a CBOR object from an arbitrary-precision integer. The CBOR object
is generated as follows: <ul> <li>If the number is null, returns
CBORObject.Null.</li> <li>Otherwise, if the number is greater than
or equal to -(2^64) and less than 2^64, the CBOR object will have
the object type Integer and the appropriate value.</li>

 <li>Otherwise, the CBOR object will have tag 2 (zero or positive) or
 3 (negative) and the appropriate value.</li></ul>

**Parameters:**

- <code>bigintValue</code> - An arbitrary-precision integer. Can be null.

**Returns:**

- The given number encoded as a CBOR object. Returns CBORObject.Null
  if <code>bigintValue</code> is null.

### FromObject

    public static CBORObject FromObject​(com.upokecenter.numbers.EFloat bigValue)

Generates a CBOR object from an arbitrary-precision binary floating-point
number. The CBOR object is generated as follows (this is a change in
version 4.0): <ul> <li>If the number is null, returns
CBORObject.Null.</li> <li>Otherwise, if the number expresses
infinity, not-a-number, or negative zero, the CBOR object will have
tag 269 and the appropriate format.</li> <li>Otherwise, if the
number's exponent is at least 2^64 or less than -(2^64), the CBOR
object will have tag 265 and the appropriate format.</li>

 <li>Otherwise, the CBOR object will have tag 5 and the appropriate
 format.</li></ul>

**Parameters:**

- <code>bigValue</code> - An arbitrary-precision binary floating-point number. Can be
  null.

**Returns:**

- The given number encoded as a CBOR object. Returns CBORObject.Null
  if <code>bigValue</code> is null.

### FromObject

    public static CBORObject FromObject​(com.upokecenter.numbers.ERational bigValue)

Generates a CBOR object from an arbitrary-precision rational number. The
CBOR object is generated as follows (this is a change in version
4.0): <ul> <li>If the number is null, returns CBORObject.Null.</li>

 <li>Otherwise, if the number expresses infinity, not-a-number, or
 negative zero, the CBOR object will have tag 270 and the appropriate
 format.</li> <li>Otherwise, the CBOR object will have tag 30 and the
 appropriate format.</li></ul>

**Parameters:**

- <code>bigValue</code> - An arbitrary-precision rational number. Can be null.

**Returns:**

- The given number encoded as a CBOR object. Returns CBORObject.Null
  if <code>bigValue</code> is null.

### FromObject

    public static CBORObject FromObject​(com.upokecenter.numbers.EDecimal bigValue)

Generates a CBOR object from a decimal number. The CBOR object is generated
as follows (this is a change in version 4.0): <ul> <li>If the number
is null, returns CBORObject.Null.</li> <li>Otherwise, if the number
expresses infinity, not-a-number, or negative zero, the CBOR object
will have tag 268 and the appropriate format.</li> <li>If the
number's exponent is at least 2^64 or less than -(2^64), the CBOR
object will have tag 264 and the appropriate format.</li>

 <li>Otherwise, the CBOR object will have tag 4 and the appropriate
 format.</li></ul>

**Parameters:**

- <code>bigValue</code> - An arbitrary-precision decimal number. Can be null.

**Returns:**

- The given number encoded as a CBOR object. Returns CBORObject.Null
  if <code>bigValue</code> is null.

### FromObject

    public static CBORObject FromObject​(java.lang.String strValue)

Generates a CBOR object from a text string.

**Parameters:**

- <code>strValue</code> - A text string value. Can be null.

**Returns:**

- A CBOR object representing the string, or CBORObject.Null if
  stringValue is null.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The string contains an unpaired surrogate code
  point.

### FromObject

    public static CBORObject FromObject​(int value)

Generates a CBOR object from a 32-bit signed integer.

**Parameters:**

- <code>value</code> - The parameter <code>value</code> is a 32-bit signed integer.

**Returns:**

- A CBOR object.

### FromObject

    public static CBORObject FromObject​(short value)

Generates a CBOR object from a 16-bit signed integer.

**Parameters:**

- <code>value</code> - The parameter <code>value</code> is a 16-bit signed integer.

**Returns:**

- A CBOR object generated from the given integer.

### FromObject

    public static CBORObject FromObject​(boolean value)

Returns the CBOR true value or false value, depending on "value".

**Parameters:**

- <code>value</code> - Either <code>true</code> or <code>false</code>.

**Returns:**

- CBORObject.True if value is true; otherwise CBORObject.False.

### FromObject

    public static CBORObject FromObject​(byte value)

Generates a CBOR object from a byte (0 to 255).

**Parameters:**

- <code>value</code> - The parameter <code>value</code> is a byte (from 0 to 255).

**Returns:**

- A CBOR object generated from the given integer.

### FromObject

    public static CBORObject FromObject​(float value)

Generates a CBOR object from a 32-bit floating-point number.

**Parameters:**

- <code>value</code> - The parameter <code>value</code> is a 32-bit floating-point number.

**Returns:**

- A CBOR object generated from the given number.

### FromObject

    public static CBORObject FromObject​(double value)

Generates a CBOR object from a 64-bit floating-point number.

**Parameters:**

- <code>value</code> - The parameter <code>value</code> is a 64-bit floating-point number.

**Returns:**

- A CBOR object generated from the given number.

### FromObject

    public static CBORObject FromObject​(byte[] bytes)

Generates a CBOR object from an array of 8-bit bytes; the byte array is
copied to a new byte array in this process. (This method can't be
used to decode CBOR data from a byte array; for that, use the
<b>DecodeFromBytes</b> method instead.).

**Parameters:**

- <code>bytes</code> - An array of 8-bit bytes; can be null.

**Returns:**

- A CBOR object where each element of the given byte array is copied
  to a new array, or CBORObject.Null if the value is null.

### FromObject

    public static CBORObject FromObject​(CBORObject[] array)

Generates a CBOR object from an array of CBOR objects.

**Parameters:**

- <code>array</code> - An array of CBOR objects.

**Returns:**

- A CBOR object where each element of the given array is copied to a
  new array, or CBORObject.Null if the value is null.

### FromObject

    public static CBORObject FromObject​(int[] array)

Generates a CBOR object from an array of 32-bit integers.

**Parameters:**

- <code>array</code> - An array of 32-bit integers.

**Returns:**

- A CBOR array object where each element of the given array is copied
  to a new array, or CBORObject.Null if the value is null.

### FromObject

    public static CBORObject FromObject​(long[] array)

Generates a CBOR object from an array of 64-bit integers.

**Parameters:**

- <code>array</code> - An array of 64-bit integers.

**Returns:**

- A CBOR array object where each element of the given array is copied
  to a new array, or CBORObject.Null if the value is null.

### FromObject

    public static CBORObject FromObject​(java.lang.Object obj)

Generates a CBORObject from an arbitrary object. See the overload of this
method that takes CBORTypeMapper and PODOptions arguments.

**Parameters:**

- <code>obj</code> - The parameter <code>obj</code> is an arbitrary object, which can be
  null. <p><b>NOTE:</b> For security reasons, whenever possible, an
  application should not base this parameter on user input or other
  externally supplied data unless the application limits this
  parameter's inputs to types specially handled by this method (such
  as <code>int</code> or <code>string</code>) and/or to plain-old-data types
  (POCO or POJO types) within the control of the application. If the
  plain-old-data type references other data types, those types should
  likewise meet either criterion above.</p>.

**Returns:**

- A CBOR object corresponding to the given object. Returns
  CBORObject.Null if the object is null.

### FromObject

    public static CBORObject FromObject​(java.lang.Object obj, PODOptions options)

Generates a CBORObject from an arbitrary object. See the overload of this
method that takes CBORTypeMapper and PODOptions arguments.

**Parameters:**

- <code>obj</code> - The parameter <code>obj</code> is an arbitrary object. <p><b>NOTE:</b>
  For security reasons, whenever possible, an application should not
  base this parameter on user input or other externally supplied data
  unless the application limits this parameter's inputs to types
  specially handled by this method (such as <code>int</code> or <code>
  string</code>) and/or to plain-old-data types (POCO or POJO types) within
  the control of the application. If the plain-old-data type
  references other data types, those types should likewise meet either
  criterion above.</p>.

- <code>options</code> - An object containing options to control how certain objects
  are converted to CBOR objects.

**Returns:**

- A CBOR object corresponding to the given object. Returns
  CBORObject.Null if the object is null.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>options</code> is null.

### FromObject

    public static CBORObject FromObject​(java.lang.Object obj, CBORTypeMapper mapper)

Generates a CBORObject from an arbitrary object. See the overload of this
method that takes CBORTypeMapper and PODOptions arguments.

**Parameters:**

- <code>obj</code> - The parameter <code>obj</code> is an arbitrary object. <p><b>NOTE:</b>
  For security reasons, whenever possible, an application should not
  base this parameter on user input or other externally supplied data
  unless the application limits this parameter's inputs to types
  specially handled by this method (such as <code>int</code> or <code>
  string</code>) and/or to plain-old-data types (POCO or POJO types) within
  the control of the application. If the plain-old-data type
  references other data types, those types should likewise meet either
  criterion above.</p>.

- <code>mapper</code> - An object containing optional converters to convert objects of
  certain types to CBOR objects.

**Returns:**

- A CBOR object corresponding to the given object. Returns
  CBORObject.Null if the object is null.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>mapper</code> is null.

### FromObject

    public static CBORObject FromObject​(java.lang.Object obj, CBORTypeMapper mapper, PODOptions options)

<p>Generates a CBORObject from an arbitrary object, using the given options
 to control how certain objects are converted to CBOR objects. The
 following cases are checked in the logical order given (rather than
 the strict order in which they are implemented by this library):</p>
 <ul><li><code>null</code> is converted to <code>CBORObject.Null</code> .</li>
 <li>A <code>CBORObject</code> is returned as itself.</li> <li>If the
 object is of a type corresponding to a type converter mentioned in
 the <paramref name='mapper'/> parameter, that converter will be used
 to convert the object to a CBOR object. Type converters can be used
 to override the default conversion behavior of almost any
 object.</li> <li>A <code>char</code> is converted to an integer (from 0
 through 65535), and returns a CBOR object of that integer. (This is
 a change in version 4.0 from previous versions, which converted
 <code>char</code> , except surrogate code points from 0xd800 through
 0xdfff, into single-character text strings.)</li> <li>A
 <code>boolean</code> (<code>boolean</code> in Java) is converted to
 <code>CBORObject.True</code> or <code>CBORObject.False</code> .</li> <li>A
 <code>byte</code> is converted to a CBOR integer from 0 through 255.</li>
 <li>A primitive integer type (<code>int</code> , <code>short</code> ,
 <code>long</code> , as well as <code>sbyte</code> , <code>ushort</code> , <code>uint</code>
 , and <code>ulong</code> in.NET) is converted to the corresponding CBOR
 integer.</li> <li>A primitive floating-point type (<code>float</code> ,
 <code>double</code> , as well as <code>decimal</code> in.NET) is converted to
 the corresponding CBOR number.</li> <li>A <code>string</code> is converted
 to a CBOR text string. To create a CBOR byte string object from
 <code>string</code> , see the example given in <see cref='PeterO.Cbor.CBORObject.FromObject(System.Byte[])'/>.</li>
 <li>In the.NET version, a nullable is converted to
 <code>CBORObject.Null</code> if the nullable's value is <code>null</code> , or
 converted according to the nullable's underlying type, if that type
 is supported by this method.</li> <li>In the Java version, a number
 of type <code>BigInteger</code> or <code>BigDecimal</code> is converted to the
 corresponding CBOR number.</li> <li>A number of type <code>EDecimal</code>
 , <code>EFloat</code> , <code>EInteger</code> , and <code>ERational</code> in the <code>PeterO.Numbers</code>
  library (in .NET) or the <code>com.github.peteroupc/numbers</code>
  artifact (in Java) is converted to the corresponding CBOR
 number.</li> <li>An array other than <code>byte[]</code> is converted to a
 CBOR array. In the.NET version, a multidimensional array is
 converted to an array of arrays.</li> <li>A <code>byte[]</code>
 (1-dimensional byte array) is converted to a CBOR byte string; the
 byte array is copied to a new byte array in this process. (This
 method can't be used to decode CBOR data from a byte array; for
 that, use the <b>DecodeFromBytes</b> method instead.)</li> <li>An
 object implementing Map (Map in Java) is converted to a CBOR map
 containing the keys and values enumerated.</li> <li>An object
 implementing Iterable (Iterable in Java) is converted to a CBOR
 array containing the items enumerated.</li> <li>An enumeration(
 <code>Enum</code>) object is converted to its <i>underlying value</i> in
 the.NET version, or the result of its <code>ordinal()</code> method in the
 Java version.</li> <li>An object of type <code>java.util.Date</code> ,
 <code>java.net.URI</code> , or <code>java.util.UUID</code> (<code>Date</code> , <code>URI</code> , or
 <code>UUID</code> , respectively, in Java) will be converted to a tagged
 CBOR object of the appropriate kind. By default, <code>java.util.Date</code> /
 <code>Date</code> will be converted to a tag-0 string following the date
 format used in the Atom syndication format, but this behavior can be
 changed by passing a suitable CBORTypeMapper to this method, such as
 a CBORTypeMapper that registers a CBORDateConverter for
 <code>java.util.Date</code> or <code>Date</code> objects. See the examples.</li>
 <li>If the object is a type not specially handled above, this method
 checks the <paramref name='obj'/> parameter for eligible getters as
 follows:</li> <li>(*) In the .NET version, eligible getters are the
 public, nonstatic getters of read/write properties (and also those
 of read-only properties in the case of a compiler-generated type or
 an F# type). Eligible getters also include public, nonstatic, non-
 <code>static final</code> , non- <code>readonly</code> fields. If a class has two
  properties and/or fields of the form "X" and "IsX", where "X" is any
 name, or has multiple properties and/or fields with the same name,
 those properties and fields are ignored.</li> <li>(*) In the Java
 version, eligible getters are public, nonstatic methods starting
  with "get" or "is" (either word followed by a character other than a
  basic digit or lower-case letter, that is, other than "a" to "z" or
  "0" to "9"), that take no parameters and do not return void, except
  that methods named "getClass" are not eligible getters. In addition,
 public, nonstatic, nonfinal fields are also eligible getters. If a
 class has two otherwise eligible getters (methods and/or fields) of
  the form "isX" and "getX", where "X" is the same in both, or two
 such getters with the same name but different return type, they are
 not eligible getters.</li> <li>Then, the method returns a CBOR map
 with each eligible getter's name or property name as each key, and
 with the corresponding value returned by that getter as that key's
 value. Before adding a key-value pair to the map, the key's name is
 adjusted according to the rules described in the <code>PODOptions</code> documentation. Note that for
 security reasons, certain types are not supported even if they
 contain eligible getters.</li> </ul> <p><b>REMARK:</b> .NET
 enumeration (<code>Enum</code>) constants could also have been converted
 to text strings with <code>toString()</code> , but that method will return
 multiple names if the given Enum object is a combination of Enum
 objects (e.g. if the object is <code>FileAccess.Read |
 FileAccess.Write</code>). More generally, if Enums are converted to
 text strings, constants from Enum types with the <code>Flags</code>
 attribute, and constants from the same Enum type that share an
 underlying value, should not be passed to this method.</p><p> </p><p>The
 following example (originally written in C# for the DotNet version)
 uses a CBORTypeMapper to change how java.util.Date objects are converted
 to CBOR. In this case, such objects are converted to CBOR objects
 with tag 1 that store numbers giving the number of seconds since the
 start of 1970.</p> <pre>CBORTypeMapper conv = new CBORTypeMapper().AddConverter(java.util.Date.class, CBORDateConverter.TaggedNumber); CBORObject obj = CBORObject.FromObject(java.util.Date.Now, conv);</pre> <p>The following
 example generates a CBOR object from a 64-bit signed integer that is
 treated as a 64-bit unsigned integer (such as DotNet's UInt64, which
 has no direct equivalent in the Java language), in the sense that
 the value is treated as 2^64 plus the original value if it's
 negative.</p> <pre>long x = -40L; /* Example 64-bit value treated as 2^64-40.*/ CBORObject obj = CBORObject.FromObject(v &lt; 0 ? EInteger.FromInt32(1).ShiftLeft(64).Add(v) : EInteger.FromInt64(v));</pre> <p>In the Java version, which has
 java.math.getBigInteger(), the following can be used instead:</p>
 <pre>long x = -40L; /* Example 64-bit value treated as 2^64-40.*/ CBORObject obj = CBORObject.FromObject(v &lt; 0 ? BigInteger.valueOf(1).shiftLeft(64).add(BigInteger.valueOf(v)) : BigInteger.valueOf(v));</pre>

**Parameters:**

- <code>obj</code> - An arbitrary object to convert to a CBOR object. <p><b>NOTE:</b>
  For security reasons, whenever possible, an application should not
  base this parameter on user input or other externally supplied data
  unless the application limits this parameter's inputs to types
  specially handled by this method (such as <code>int</code> or <code>
  string</code>) and/or to plain-old-data types (POCO or POJO types) within
  the control of the application. If the plain-old-data type
  references other data types, those types should likewise meet either
  criterion above.</p>.

- <code>mapper</code> - An object containing optional converters to convert objects of
  certain types to CBOR objects. Can be null.

- <code>options</code> - An object containing options to control how certain objects
  are converted to CBOR objects.

**Returns:**

- A CBOR object corresponding to the given object. Returns
  CBORObject.Null if the object is null.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>options</code> is null.

- <code>CBORException</code> - An error occurred while
  converting the given object to a CBOR object.

### WithTag

    public CBORObject WithTag​(com.upokecenter.numbers.EInteger bigintTag)

Generates a CBOR object from this one, but gives the resulting object a tag
in addition to its existing tags (the new tag is made the outermost
tag).

**Parameters:**

- <code>bigintTag</code> - Tag number. The tag number 55799 can be used to mark a
  "self-described CBOR" object. This document does not attempt to list
  all CBOR tags and their meanings. An up-to-date list can be found at
  the CBOR Tags registry maintained by the Internet Assigned Numbers
  Authority(<i>iana.org/assignments/cbor-tags</i>).

**Returns:**

- A CBOR object with the same value as this one but given the tag
  <code>bigintTag</code> in addition to its existing tags (the new tag is
  made the outermost tag).

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>bigintTag</code> is less than 0 or
  greater than 2^64-1.

- <code>java.lang.NullPointerException</code> - The parameter <code>bigintTag</code> is null.

### FromObjectAndTag

    public static CBORObject FromObjectAndTag​(java.lang.Object valueOb, com.upokecenter.numbers.EInteger bigintTag)

Generates a CBOR object from an arbitrary object and gives the resulting
object a tag in addition to its existing tags (the new tag is made
the outermost tag).

**Parameters:**

- <code>valueOb</code> - The parameter <code>valueOb</code> is an arbitrary object, which
  can be null. <p><b>NOTE:</b> For security reasons, whenever
  possible, an application should not base this parameter on user
  input or other externally supplied data unless the application
  limits this parameter's inputs to types specially handled by this
  method (such as <code>int</code> or <code>string</code>) and/or to
  plain-old-data types (POCO or POJO types) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.</p>.

- <code>bigintTag</code> - Tag number. The tag number 55799 can be used to mark a
  "self-described CBOR" object. This document does not attempt to list
  all CBOR tags and their meanings. An up-to-date list can be found at
  the CBOR Tags registry maintained by the Internet Assigned Numbers
  Authority(<i>iana.org/assignments/cbor-tags</i>).

**Returns:**

- A CBOR object where the object <code>valueOb</code> is converted to a
  CBOR object and given the tag <code>bigintTag</code>. If <code>valueOb</code>
  is null, returns a version of CBORObject.Null with the given tag.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>bigintTag</code> is less than 0 or
  greater than 2^64-1.

- <code>java.lang.NullPointerException</code> - The parameter <code>bigintTag</code> is null.

### WithTag

    public CBORObject WithTag​(int smallTag)

Generates a CBOR object from an arbitrary object and gives the resulting
object a tag in addition to its existing tags (the new tag is made
the outermost tag).

**Parameters:**

- <code>smallTag</code> - A 32-bit integer that specifies a tag number. The tag number
  55799 can be used to mark a "self-described CBOR" object. This
  document does not attempt to list all CBOR tags and their meanings.
  An up-to-date list can be found at the CBOR Tags registry maintained
  by the Internet Assigned Numbers Authority(
  <i>iana.org/assignments/cbor-tags</i>).

**Returns:**

- A CBOR object with the same value as this one but given the tag
  <code>smallTag</code> in addition to its existing tags (the new tag is
  made the outermost tag).

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>smallTag</code> is less than 0.

### FromObjectAndTag

    public static CBORObject FromObjectAndTag​(java.lang.Object valueObValue, int smallTag)

Generates a CBOR object from an arbitrary object and gives the resulting
object a tag in addition to its existing tags (the new tag is made
the outermost tag).

**Parameters:**

- <code>valueObValue</code> - The parameter <code>valueObValue</code> is an arbitrary
  object, which can be null. <p><b>NOTE:</b> For security reasons,
  whenever possible, an application should not base this parameter on
  user input or other externally supplied data unless the application
  limits this parameter's inputs to types specially handled by this
  method (such as <code>int</code> or <code>string</code>) and/or to
  plain-old-data types (POCO or POJO types) within the control of the
  application. If the plain-old-data type references other data types,
  those types should likewise meet either criterion above.</p>.

- <code>smallTag</code> - A 32-bit integer that specifies a tag number. The tag number
  55799 can be used to mark a "self-described CBOR" object. This
  document does not attempt to list all CBOR tags and their meanings.
  An up-to-date list can be found at the CBOR Tags registry maintained
  by the Internet Assigned Numbers Authority(
  <i>iana.org/assignments/cbor-tags</i>).

**Returns:**

- A CBOR object where the object <code>valueObValue</code> is converted to
  a CBOR object and given the tag <code>smallTag</code>. If "valueOb" is
  null, returns a version of CBORObject.Null with the given tag.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>smallTag</code> is less than 0.

### FromSimpleValue

    public static CBORObject FromSimpleValue​(int simpleValue)

Creates a CBOR object from a simple value number.

**Parameters:**

- <code>simpleValue</code> - The parameter <code>simpleValue</code> is a 32-bit signed
  integer.

**Returns:**

- A CBOR object.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>simpleValue</code> is less than 0,
  greater than 255, or from 24 through 31.

### Multiply

    @Deprecated public static CBORObject Multiply​(CBORObject first, CBORObject second)

Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
use the first number's.Multiply() method.

**Parameters:**

- <code>first</code> - The parameter <code>first</code> is a CBOR object.

- <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

- The product of the two numbers.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - Either or both operands are not numbers (as
  opposed to Not-a-Number, NaN).

- <code>java.lang.NullPointerException</code> - The parameter <code>first</code> or <code>second</code>
  is null.

### NewArray

    public static CBORObject NewArray()

Creates a new empty CBOR array.

**Returns:**

- A new CBOR array.

### NewMap

    public static CBORObject NewMap()

Creates a new empty CBOR map that stores its keys in an undefined order.

**Returns:**

- A new CBOR map.

### NewOrderedMap

    public static CBORObject NewOrderedMap()

Creates a new empty CBOR map that ensures that keys are stored in the order
in which they are first inserted.

**Returns:**

- A new CBOR map.

### ReadSequence

    public static CBORObject[] ReadSequence​(java.io.InputStream stream) throws java.io.IOException

<p>Reads a sequence of objects in CBOR format from a data stream. This
 method will read CBOR objects from the stream until the end of the
 stream is reached or an error occurs, whichever happens first.</p>

**Parameters:**

- <code>stream</code> - A readable data stream.

**Returns:**

- An array containing the CBOR objects that were read from the data
  stream. Returns an empty array if there is no unread data in the
  stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null, or the
  parameter "options" is null.

- <code>CBORException</code> - There was an error in reading or
  parsing the data, including if the last CBOR object was read only
  partially.

- <code>java.io.IOException</code>

### ReadSequence

    public static CBORObject[] ReadSequence​(java.io.InputStream stream, CBOREncodeOptions options) throws java.io.IOException

<p>Reads a sequence of objects in CBOR format from a data stream. This
 method will read CBOR objects from the stream until the end of the
 stream is reached or an error occurs, whichever happens first.</p>

**Parameters:**

- <code>stream</code> - A readable data stream.

- <code>options</code> - Specifies the options to use when decoding the CBOR data
  stream. See CBOREncodeOptions for more information. In this method,
  the AllowEmpty property is treated as set regardless of the value of
  that property specified in this parameter.

**Returns:**

- An array containing the CBOR objects that were read from the data
  stream. Returns an empty array if there is no unread data in the
  stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null, or the
  parameter <code>options</code> is null.

- <code>CBORException</code> - There was an error in reading or
  parsing the data, including if the last CBOR object was read only
  partially.

- <code>java.io.IOException</code>

### Read

    public static CBORObject Read​(java.io.InputStream stream)

<p>Reads an object in CBOR format from a data stream. This method will read
 from the stream until the end of the CBOR object is reached or an
 error occurs, whichever happens first.</p>

**Parameters:**

- <code>stream</code> - A readable data stream.

**Returns:**

- A CBOR object that was read.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>CBORException</code> - There was an error in reading or
  parsing the data.

### Read

    public static CBORObject Read​(java.io.InputStream stream, CBOREncodeOptions options)

Reads an object in CBOR format from a data stream, using the specified
options to control the decoding process. This method will read from
the stream until the end of the CBOR object is reached or an error
occurs, whichever happens first.

**Parameters:**

- <code>stream</code> - A readable data stream.

- <code>options</code> - Specifies the options to use when decoding the CBOR data
  stream. See CBOREncodeOptions for more information.

**Returns:**

- A CBOR object that was read.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>CBORException</code> - There was an error in reading or
  parsing the data.

### ReadJSON

    public static CBORObject ReadJSON​(java.io.InputStream stream) throws java.io.IOException

Generates a CBOR object from a data stream in JavaScript object Notation
(JSON) format. The JSON stream may begin with a byte-order mark
(U+FEFF). Since version 2.0, the JSON stream can be in UTF-8,
UTF-16, or UTF-32 encoding; the encoding is detected by assuming
that the first character read must be a byte-order mark or a nonzero
basic character (U+0001 to U+007F). (In previous versions, only
UTF-8 was allowed.).

**Parameters:**

- <code>stream</code> - A readable data stream. The sequence of bytes read from the
  data stream must contain a single JSON object and not multiple
  objects.

**Returns:**

- A CBOR object.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

- <code>CBORException</code> - The data stream contains invalid
  encoding or is not in JSON format.

### ReadJSONSequence

    public static CBORObject[] ReadJSONSequence​(java.io.InputStream stream) throws java.io.IOException

Generates a list of CBOR objects from a data stream in JavaScript object
Notation (JSON) text sequence format (RFC 7464). The data stream
must be in UTF-8 encoding and may not begin with a byte-order mark
(U+FEFF).<p>Generally, each JSON text in a JSON text sequence is
written as follows: Write a record separator byte (0x1e), then write
the JSON text in UTF-8 (without a byte order mark, U+FEFF), then
write the line feed byte (0x0a). RFC 7464, however, uses a more
liberal syntax for parsing JSON text sequences.</p>

**Parameters:**

- <code>stream</code> - A readable data stream. The sequence of bytes read from the
  data stream must either be empty or begin with a record separator
  byte (0x1e).

**Returns:**

- A list of CBOR objects read from the JSON sequence. Objects that
  could not be parsed are replaced with <code>null</code> (as opposed to
  <code>CBORObject.Null</code>) in the given list.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

- <code>CBORException</code> - The data stream is not empty and
  does not begin with a record separator byte (0x1e).

### ReadJSON

    @Deprecated public static CBORObject ReadJSON​(java.io.InputStream stream, CBOREncodeOptions options) throws java.io.IOException

Deprecated.
Instead, use.getReadJSON()(stream,
new JSONOptions(\allowduplicatekeys = true\))
or .getReadJSON()(stream,
new JSONOptions(\allowduplicatekeys = false\)),
as appropriate.

**Parameters:**

- <code>stream</code> - A readable data stream. The sequence of bytes read from the
  data stream must contain a single JSON object and not multiple
  objects.

- <code>options</code> - Contains options to control the JSON decoding process. This
  method uses only the AllowDuplicateKeys property of this object.

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

- <code>CBORException</code> - The data stream contains invalid
  encoding or is not in JSON format.

### ReadJSONSequence

    public static CBORObject[] ReadJSONSequence​(java.io.InputStream stream, JSONOptions jsonoptions) throws java.io.IOException

Generates a list of CBOR objects from a data stream in JavaScript object
Notation (JSON) text sequence format (RFC 7464). The data stream
must be in UTF-8 encoding and may not begin with a byte-order mark
(U+FEFF).<p>Generally, each JSON text in a JSON text sequence is
written as follows: Write a record separator byte (0x1e), then write
the JSON text in UTF-8 (without a byte order mark, U+FEFF), then
write the line feed byte (0x0a). RFC 7464, however, uses a more
liberal syntax for parsing JSON text sequences.</p>

**Parameters:**

- <code>stream</code> - A readable data stream. The sequence of bytes read from the
  data stream must either be empty or begin with a record separator
  byte (0x1e).

- <code>jsonoptions</code> - Specifies options to control how JSON texts in the stream
  are decoded to CBOR. See the JSONOptions class.

**Returns:**

- A list of CBOR objects read from the JSON sequence. Objects that
  could not be parsed are replaced with <code>null</code> (as opposed to
  <code>CBORObject.Null</code>) in the given list.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

- <code>CBORException</code> - The data stream is not empty and
  does not begin with a record separator byte (0x1e).

### ReadJSON

    public static CBORObject ReadJSON​(java.io.InputStream stream, JSONOptions jsonoptions) throws java.io.IOException

Generates a CBOR object from a data stream in JavaScript object Notation
(JSON) format, using the specified options to control the decoding
process. The JSON stream may begin with a byte-order mark (U+FEFF).
Since version 2.0, the JSON stream can be in UTF-8, UTF-16, or
UTF-32 encoding; the encoding is detected by assuming that the first
character read must be a byte-order mark or a nonzero basic
character (U+0001 to U+007F). (In previous versions, only UTF-8 was
allowed.).

**Parameters:**

- <code>stream</code> - A readable data stream. The sequence of bytes read from the
  data stream must contain a single JSON object and not multiple
  objects.

- <code>jsonoptions</code> - Specifies options to control how the JSON stream is
  decoded to CBOR. See the JSONOptions class.

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

- <code>CBORException</code> - The data stream contains invalid
  encoding or is not in JSON format.

### FromJSONBytes

    public static CBORObject FromJSONBytes​(byte[] bytes)

<p>Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a
 CBORException is thrown.</p> <p>Note that if a CBOR object is
 converted to JSON with <code>ToJSONBytes</code>, then the JSON is
 converted back to CBOR with this method, the new CBOR object will
 not necessarily be the same as the old CBOR object, especially if
 the old CBOR object uses data types not supported in JSON, such as
 integers in map keys.</p>

**Parameters:**

- <code>bytes</code> - A byte array in JSON format. The entire byte array must contain
  a single JSON object and not multiple objects. The byte array may
  begin with a byte-order mark (U+FEFF). The byte array can be in
  UTF-8, UTF-16, or UTF-32 encoding; the encoding is detected by
  assuming that the first character read must be a byte-order mark or
  a nonzero basic character (U+0001 to U+007F).

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bytes</code> is null.

- <code>CBORException</code> - The byte array contains invalid
  encoding or is not in JSON format.

### FromJSONBytes

    public static CBORObject FromJSONBytes​(byte[] bytes, JSONOptions jsonoptions)

Generates a CBOR object from a byte array in JavaScript object Notation
(JSON) format, using the specified options to control the decoding
process. <p>Note that if a CBOR object is converted to JSON with
<code>ToJSONBytes</code>, then the JSON is converted back to CBOR with
this method, the new CBOR object will not necessarily be the same as
the old CBOR object, especially if the old CBOR object uses data
types not supported in JSON, such as integers in map keys.</p>

**Parameters:**

- <code>bytes</code> - A byte array in JSON format. The entire byte array must contain
  a single JSON object and not multiple objects. The byte array may
  begin with a byte-order mark (U+FEFF). The byte array can be in
  UTF-8, UTF-16, or UTF-32 encoding; the encoding is detected by
  assuming that the first character read must be a byte-order mark or
  a nonzero basic character (U+0001 to U+007F).

- <code>jsonoptions</code> - Specifies options to control how the JSON data is decoded
  to CBOR. See the JSONOptions class.

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bytes</code> or <code>
  jsonoptions</code> is null.

- <code>CBORException</code> - The byte array contains invalid
  encoding or is not in JSON format.

### FromJSONBytes

    public static CBORObject FromJSONBytes​(byte[] bytes, int offset, int count)

<p>Generates a CBOR object from a byte array in JavaScript object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a
 CBORException is thrown.</p> <p>Note that if a CBOR object is
 converted to JSON with <code>ToJSONBytes</code>, then the JSON is
 converted back to CBOR with this method, the new CBOR object will
 not necessarily be the same as the old CBOR object, especially if
 the old CBOR object uses data types not supported in JSON, such as
 integers in map keys.</p>

**Parameters:**

- <code>bytes</code> - A byte array, the specified portion of which is in JSON format.
  The specified portion of the byte array must contain a single JSON
  object and not multiple objects. The portion may begin with a
  byte-order mark (U+FEFF). The portion can be in UTF-8, UTF-16, or
  UTF-32 encoding; the encoding is detected by assuming that the first
  character read must be a byte-order mark or a nonzero basic
  character (U+0001 to U+007F).

- <code>offset</code> - An index, starting at 0, showing where the desired portion of
  <code>bytes</code> begins.

- <code>count</code> - The length, in bytes, of the desired portion of <code>bytes</code>
  (but not more than <code>bytes</code> 's length).

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bytes</code> is null.

- <code>CBORException</code> - The byte array contains invalid
  encoding or is not in JSON format.

- <code>java.lang.IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
  than 0 or greater than <code>bytes</code> 's length, or <code>bytes</code> 's
  length minus <code>offset</code> is less than <code>count</code>.

### FromJSONBytes

    public static CBORObject FromJSONBytes​(byte[] bytes, int offset, int count, JSONOptions jsonoptions)

Generates a CBOR object from a byte array in JavaScript object Notation
(JSON) format, using the specified options to control the decoding
process. <p>Note that if a CBOR object is converted to JSON with
<code>ToJSONBytes</code>, then the JSON is converted back to CBOR with
this method, the new CBOR object will not necessarily be the same as
the old CBOR object, especially if the old CBOR object uses data
types not supported in JSON, such as integers in map keys.</p>

**Parameters:**

- <code>bytes</code> - A byte array, the specified portion of which is in JSON format.
  The specified portion of the byte array must contain a single JSON
  object and not multiple objects. The portion may begin with a
  byte-order mark (U+FEFF). The portion can be in UTF-8, UTF-16, or
  UTF-32 encoding; the encoding is detected by assuming that the first
  character read must be a byte-order mark or a nonzero basic
  character (U+0001 to U+007F).

- <code>offset</code> - An index, starting at 0, showing where the desired portion of
  <code>bytes</code> begins.

- <code>count</code> - The length, in bytes, of the desired portion of <code>bytes</code>
  (but not more than <code>bytes</code> 's length).

- <code>jsonoptions</code> - Specifies options to control how the JSON data is decoded
  to CBOR. See the JSONOptions class.

**Returns:**

- A CBOR object containing the JSON data decoded.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bytes</code> or <code>
  jsonoptions</code> is null.

- <code>CBORException</code> - The byte array contains invalid
  encoding or is not in JSON format.

- <code>java.lang.IllegalArgumentException</code> - Either <code>offset</code> or <code>count</code> is less
  than 0 or greater than <code>bytes</code> 's length, or <code>bytes</code> 's
  length minus <code>offset</code> is less than <code>count</code>.

### Remainder

    @Deprecated public static CBORObject Remainder​(CBORObject first, CBORObject second)

Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
use the first number's.Remainder() method.

**Parameters:**

- <code>first</code> - The parameter <code>first</code> is a CBOR object.

- <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

- The remainder of the two numbers.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>first</code> or <code>second</code>
  is null.

### Subtract

    @Deprecated public static CBORObject Subtract​(CBORObject first, CBORObject second)

Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
use the first number's.Subtract() method.

**Parameters:**

- <code>first</code> - The parameter <code>first</code> is a CBOR object.

- <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

- The difference of the two objects.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - Either or both operands are not numbers (as
  opposed to Not-a-Number, NaN).

- <code>java.lang.NullPointerException</code> - The parameter <code>first</code> or <code>second</code>
  is null.

### Write

    public static void Write​(java.lang.String str, java.io.OutputStream stream) throws java.io.IOException

<p>Writes a text string in CBOR format to a data stream. The string will be
 encoded using definite-length encoding regardless of its length.</p>

**Parameters:**

- <code>str</code> - The string to write. Can be null.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(java.lang.String str, java.io.OutputStream stream, CBOREncodeOptions options) throws java.io.IOException

Writes a text string in CBOR format to a data stream, using the given
options to control the encoding process.

**Parameters:**

- <code>str</code> - The string to write. Can be null.

- <code>stream</code> - A writable data stream.

- <code>options</code> - Options for encoding the data to CBOR.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(com.upokecenter.numbers.EFloat bignum, java.io.OutputStream stream) throws java.io.IOException

Writes a binary floating-point number in CBOR format to a data stream, as
though it were converted to a CBOR object via
CBORObject.FromObject(EFloat) and then written out.

**Parameters:**

- <code>bignum</code> - An arbitrary-precision binary floating-point number. Can be
  null.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(com.upokecenter.numbers.ERational rational, java.io.OutputStream stream) throws java.io.IOException

Writes a rational number in CBOR format to a data stream, as though it were
converted to a CBOR object via CBORObject.FromObject(ERational) and
then written out.

**Parameters:**

- <code>rational</code> - An arbitrary-precision rational number. Can be null.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(com.upokecenter.numbers.EDecimal bignum, java.io.OutputStream stream) throws java.io.IOException

Writes a decimal floating-point number in CBOR format to a data stream, as
though it were converted to a CBOR object via
CBORObject.FromObject(EDecimal) and then written out.

**Parameters:**

- <code>bignum</code> - The arbitrary-precision decimal number to write. Can be null.

- <code>stream</code> - InputStream to write to.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(com.upokecenter.numbers.EInteger bigint, java.io.OutputStream stream) throws java.io.IOException

Writes a arbitrary-precision integer in CBOR format to a data stream.

**Parameters:**

- <code>bigint</code> - Arbitrary-precision integer to write. Can be null.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(long value, java.io.OutputStream stream) throws java.io.IOException

Writes a 64-bit signed integer in CBOR format to a data stream.

**Parameters:**

- <code>value</code> - The value to write.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(int value, java.io.OutputStream stream) throws java.io.IOException

Writes a 32-bit signed integer in CBOR format to a data stream.

**Parameters:**

- <code>value</code> - The value to write.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(short value, java.io.OutputStream stream) throws java.io.IOException

Writes a 16-bit signed integer in CBOR format to a data stream.

**Parameters:**

- <code>value</code> - The value to write.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(boolean value, java.io.OutputStream stream) throws java.io.IOException

Writes a Boolean value in CBOR format to a data stream.

**Parameters:**

- <code>value</code> - The value to write.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(byte value, java.io.OutputStream stream) throws java.io.IOException

Writes a byte (0 to 255) in CBOR format to a data stream. If the value is
less than 24, writes that byte. If the value is 25 to 255, writes
the byte 24, then this byte's value.

**Parameters:**

- <code>value</code> - The value to write.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(float value, java.io.OutputStream stream) throws java.io.IOException

Writes a 32-bit floating-point number in CBOR format to a data stream. The
number is written using the shortest floating-point encoding
possible; this is a change from previous versions.

**Parameters:**

- <code>value</code> - The value to write.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(double value, java.io.OutputStream stream) throws java.io.IOException

Writes a 64-bit floating-point number in CBOR format to a data stream. The
number is written using the shortest floating-point encoding
possible; this is a change from previous versions.

**Parameters:**

- <code>value</code> - The value to write.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### Write

    public static void Write​(CBORObject value, java.io.OutputStream stream) throws java.io.IOException

Writes a CBOR object to a CBOR data stream.

**Parameters:**

- <code>value</code> - The value to write. Can be null.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code>

### Write

    public static void Write​(java.lang.Object objValue, java.io.OutputStream stream) throws java.io.IOException

<p>Writes a CBOR object to a CBOR data stream. See the three-parameter Write
 method that takes a CBOREncodeOptions.</p>

**Parameters:**

- <code>objValue</code> - The arbitrary object to be serialized. Can be null.

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.io.IOException</code>

### Write

    public static void Write​(java.lang.Object objValue, java.io.OutputStream output, CBOREncodeOptions options) throws java.io.IOException

Writes an arbitrary object to a CBOR data stream, using the specified
options for controlling how the object is encoded to CBOR data
format. If the object is convertible to a CBOR map or a CBOR object
that contains CBOR maps, the order in which the keys to those maps
are written out to the data stream is undefined unless the map was
created using the NewOrderedMap method. The example code given in
<see cref='PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can
be used to write out certain keys of a CBOR map in a given order.
Currently, the following objects are supported: <ul> <li>Lists of
CBORObject.</li> <li>Maps of CBORObject. The order in which the keys
to the map are written out to the data stream is undefined unless
the map was created using the NewOrderedMap method.</li>

 <li>Null.</li> <li>Byte arrays, which will always be written as
 definite-length byte strings.</li> <li>string objects. The strings
 will be encoded using definite-length encoding regardless of their
 length.</li> <li>Any object accepted by the FromObject static
 methods.</li></ul>

**Parameters:**

- <code>objValue</code> - The arbitrary object to be serialized. Can be null.
<p><b>NOTE:</b> For security reasons, whenever possible, an
application should not base this parameter on user input or other
externally supplied data unless the application limits this
parameter's inputs to types specially handled by this method (such
as <code>int</code> or <code>string</code>) and/or to plain-old-data types
(POCO or POJO types) within the control of the application. If the
plain-old-data type references other data types, those types should
likewise meet either criterion above.</p>.

- <code>output</code> - A writable data stream.

- <code>options</code> - CBOR options for encoding the CBOR object to bytes.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The object's type is not supported.

- <code>java.lang.NullPointerException</code> - The parameter <code>options</code> or <code>
  output</code> is null.

- <code>java.io.IOException</code>

### WriteJSON

    public static void WriteJSON​(java.lang.Object obj, java.io.OutputStream outputStream) throws java.io.IOException

Converts an arbitrary object to a text string in JavaScript object Notation
(JSON) format, as in the ToJSONString method, and writes that string
to a data stream in UTF-8. If the object is convertible to a CBOR
map, or to a CBOR object that contains CBOR maps, the order in which
the keys to those maps are written out to the JSON string is
undefined unless the map was created using the NewOrderedMap method.
The example code given in
<b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
can be used to write out certain keys of a CBOR map in a given order
to a JSON string.

**Parameters:**

- <code>obj</code> - The parameter <code>obj</code> is an arbitrary object. Can be null.
<p><b>NOTE:</b> For security reasons, whenever possible, an
application should not base this parameter on user input or other
externally supplied data unless the application limits this
parameter's inputs to types specially handled by this method (such
as <code>int</code> or <code>string</code>) and/or to plain-old-data types
(POCO or POJO types) within the control of the application. If the
plain-old-data type references other data types, those types should
likewise meet either criterion above.</p>.

- <code>outputStream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

- <code>java.io.IOException</code>

### Abs

    @Deprecated public CBORObject Abs()

Deprecated.
Instead, convert this object to a number (with .getAsNumber()()),
and use that number's.getAbs()() method.

**Returns:**

- This object's absolute without its negative sign.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

### Add

    public CBORObject Add​(java.lang.Object key, java.lang.Object valueOb)

<p>Adds a new key and its value to this CBOR map, or adds the value if the
 key doesn't exist.</p> <p>NOTE: This method can't be used to add a
 tag to an existing CBOR object. To create a CBOR object with a given
 tag, call the <code>CBORObject.FromObjectAndTag</code> method and pass the
 CBOR object and the desired tag number to that method.</p>

**Parameters:**

- <code>key</code> - An object representing the key, which will be converted to a
  CBORObject. Can be null, in which case this value is converted to
  CBORObject.Null.

- <code>valueOb</code> - An object representing the value, which will be converted to
  a CBORObject. Can be null, in which case this value is converted to
  CBORObject.Null.

**Returns:**

- This instance.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>key</code> already exists in this
  map.

- <code>java.lang.IllegalStateException</code> - This object is not a map.

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>key</code> or <code>valueOb</code> has
  an unsupported type.

### Add

    public CBORObject Add​(CBORObject obj)

<p>Adds a new object to the end of this array. (Used to throw
 NullPointerException on a null reference, but now converts the null
 reference to CBORObject.Null, for convenience with the object
 overload of this method).</p> <p>NOTE: This method can't be used to
 add a tag to an existing CBOR object. To create a CBOR object with a
 given tag, call the <code>CBORObject.FromObjectAndTag</code> method and
 pass the CBOR object and the desired tag number to that
 method.</p><p> </p><p>The following example creates a CBOR array and
 adds several CBOR objects, one of which has a custom CBOR tag, to
 that array. Note the chaining behavior made possible by this
  method.</p> <pre>CBORObject obj = CBORObject.NewArray() .Add(CBORObject.False) .Add(CBORObject.FromObject(5)) .Add(CBORObject.FromObject("text string")) .Add(CBORObject.FromObjectAndTag(9999, 1));</pre> .

**Parameters:**

- <code>obj</code> - The parameter <code>obj</code> is a CBOR object.

**Returns:**

- This instance.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not an array.

### Add

    public CBORObject Add​(java.lang.Object obj)

<p>Converts an object to a CBOR object and adds it to the end of this
 array.</p> <p>NOTE: This method can't be used to add a tag to an
 existing CBOR object. To create a CBOR object with a given tag, call
 the <code>CBORObject.FromObjectAndTag</code> method and pass the CBOR
 object and the desired tag number to that method.</p><p> </p><p>The
 following example creates a CBOR array and adds several CBOR
 objects, one of which has a custom CBOR tag, to that array. Note the
  chaining behavior made possible by this method.</p> <pre>CBORObject obj = CBORObject.NewArray() .Add(CBORObject.False) .Add(5) .Add("text string") .Add(CBORObject.FromObjectAndTag(9999, 1));</pre> .

**Parameters:**

- <code>obj</code> - A CBOR object (or an object convertible to a CBOR object) to add
  to this CBOR array.

**Returns:**

- This instance.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This instance is not an array.

- <code>java.lang.IllegalArgumentException</code> - The type of <code>obj</code> is not supported.

### AsEInteger

    @Deprecated public com.upokecenter.numbers.EInteger AsEInteger()

Deprecated.
Instead, use.getToObject()&amp;lt;PeterO.Numbers.EInteger&amp;gt;()
in .NET or
.getToObject()(com.upokecenter.numbers.EInteger.class)
in Java.

**Returns:**

- The closest arbitrary-precision integer to this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  the purposes of this method, infinity and not-a-number values, but
  not <code>CBORObject.Null</code>, are considered numbers).

- <code>java.lang.ArithmeticException</code> - This object's value is infinity or not-a-number
  (NaN).

### AsBoolean

    public boolean AsBoolean()

Returns false if this object is a CBOR false, null, or undefined value
(whether or not the object has tags); otherwise, true.

**Returns:**

- False if this object is a CBOR false, null, or undefined value;
  otherwise, true.

### AsByte

    @Deprecated public byte AsByte()

Deprecated.
Instead, use.getToObject()&amp;lt;byte&amp;gt;() in .NET or
.getToObject()(Byte.class) in Java.

**Returns:**

- The closest byte-sized integer to this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

- <code>java.lang.ArithmeticException</code> - This object's value exceeds the range of a byte
  (would be less than 0 or greater than 255 when converted to an
  integer by discarding its fractional part).

### AsDouble

    public double AsDouble()

Converts this object to a 64-bit floating point number.

**Returns:**

- The closest 64-bit floating point number to this object. The return
  value can be positive infinity or negative infinity if this value
  exceeds the range of a 64-bit floating point number.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

### AsEDecimal

    @Deprecated public com.upokecenter.numbers.EDecimal AsEDecimal()

Deprecated.
Instead, use.getToObject()&amp;lt;PeterO.Numbers.EDecimal&amp;gt;()
in .NET or
.getToObject()(com.upokecenter.numbers.EDecimal.class)
in Java.

**Returns:**

- A decimal number for this object's value.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  the purposes of this method, infinity and not-a-number values, but
  not <code>CBORObject.Null</code>, are considered numbers).

### AsEFloat

    @Deprecated public com.upokecenter.numbers.EFloat AsEFloat()

Deprecated.
Instead, use.getToObject()&amp;lt;PeterO.Numbers.EFloat&amp;gt;() in.NET
or .getToObject()(com.upokecenter.numbers.EFloat.class)
in Java.

**Returns:**

- An arbitrary-precision binary floating-point number for this
  object's value.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  the purposes of this method, infinity and not-a-number values, but
  not <code>CBORObject.Null</code>, are considered numbers).

### AsERational

    @Deprecated public com.upokecenter.numbers.ERational AsERational()

Deprecated.
Instead, use.getToObject()&amp;lt;PeterO.Numbers.ERational&amp;gt;() in
.NET or.getToObject()(com.upokecenter.numbers.ERational.class)
in Java.

**Returns:**

- A rational number for this object's value.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  the purposes of this method, infinity and not-a-number values, but
  not <code>CBORObject.Null</code>, are considered numbers).

### AsInt16

    @Deprecated public short AsInt16()

Deprecated.
Instead, use the following: (cbor.AsNumber().ToInt16Checked()),
or .getToObject()&amp;lt;short&amp;gt;() in .NET.

**Returns:**

- The closest 16-bit signed integer to this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

- <code>java.lang.ArithmeticException</code> - This object's value exceeds the range of a 16-bit
  signed integer.

### AsInt32Value

    public int AsInt32Value()

Converts this object to a 32-bit signed integer if this CBOR object's type
is Integer. This method disregards the tags this object has, if
any.<p> </p><p>The following example code (originally written in C# for
the.NET Framework) shows a way to check whether a given CBOR object
stores a 32-bit signed integer before getting its value.</p>

  <pre>CBORObject obj = CBORObject.FromInt32(99999); if (obj.CanValueFitInInt32()) { /* Not an Int32; handle the error */ System.out.println("Not a 32-bit integer."); } else { System.out.println("The value is " + obj.AsInt32Value()); }</pre> .

**Returns:**

- The 32-bit signed integer stored by this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object's type is not <code>
  CBORType.Integer</code>.

- <code>java.lang.ArithmeticException</code> - This object's value exceeds the range of a 32-bit
  signed integer.

### AsInt64Value

    public long AsInt64Value()

Converts this object to a 64-bit signed integer if this CBOR object's type
is Integer. This method disregards the tags this object has, if
any.<p> </p><p>The following example code (originally written in C# for
the.NET Framework) shows a way to check whether a given CBOR object
stores a 64-bit signed integer before getting its value.</p>

  <pre>CBORObject obj = CBORObject.FromInt64(99999); if (obj.CanValueFitInInt64()) { /* Not an Int64; handle the error*/ System.out.println("Not a 64-bit integer."); } else { System.out.println("The value is " + obj.AsInt64Value()); }</pre> .

**Returns:**

- The 64-bit signed integer stored by this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object's type is not <code>
  CBORType.Integer</code>.

- <code>java.lang.ArithmeticException</code> - This object's value exceeds the range of a 64-bit
  signed integer.

### CanValueFitInInt64

    public boolean CanValueFitInInt64()

Returns whether this CBOR object stores an integer (CBORType.Integer) within
the range of a 64-bit signed integer. This method disregards the
tags this object has, if any.

**Returns:**

- <code>true</code> if this CBOR object stores an integer
  (CBORType.Integer) whose value is at least -(2^63) and less than
  2^63; otherwise, <code>false</code>.

### CanValueFitInInt32

    public boolean CanValueFitInInt32()

Returns whether this CBOR object stores an integer (CBORType.Integer) within
the range of a 32-bit signed integer. This method disregards the
tags this object has, if any.

**Returns:**

- <code>true</code> if this CBOR object stores an integer
  (CBORType.Integer) whose value is at least -(2^31) and less than
  2^31; otherwise, <code>false</code>.

### AsEIntegerValue

    public com.upokecenter.numbers.EInteger AsEIntegerValue()

Converts this object to an arbitrary-precision integer if this CBOR object's
type is Integer. This method disregards the tags this object has, if
any. (Note that CBOR stores untagged integers at least -(2^64) and
less than 2^64.).

**Returns:**

- The integer stored by this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object's type is not <code>
  CBORType.Integer</code>.

### AsDoubleBits

    public long AsDoubleBits()

Converts this object to the bits of a 64-bit floating-point number if this
CBOR object's type is FloatingPoint. This method disregards the tags
this object has, if any.

**Returns:**

- The bits of a 64-bit floating-point number stored by this object.
  The most significant bit is the sign (set means negative, clear
  means nonnegative); the next most significant 11 bits are the
  exponent area; and the remaining bits are the significand area. If
  all the bits of the exponent area are set and the significand area
  is 0, this indicates infinity. If all the bits of the exponent area
  are set and the significand area is other than 0, this indicates
  not-a-number (NaN).

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object's type is not <code>
  CBORType.FloatingPoint</code>.

### AsDoubleValue

    public double AsDoubleValue()

Converts this object to a 64-bit floating-point number if this CBOR object's
type is FloatingPoint. This method disregards the tags this object
has, if any.

**Returns:**

- The 64-bit floating-point number stored by this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object's type is not <code>
  CBORType.FloatingPoint</code>.

### AsNumber

    public CBORNumber AsNumber()

Converts this object to a CBOR number. (NOTE: To determine whether this
method call can succeed, call the <b>IsNumber</b> property
(isNumber() method in Java) before calling this method.).

**Returns:**

- The number represented by this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

### AsInt32

    public int AsInt32()

Converts this object to a 32-bit signed integer. Non-integer number values
are converted to integers by discarding their fractional parts.
(NOTE: To determine whether this method call can succeed, call
<b>AsNumber().getCanTruncatedIntFitInInt32()</b> before calling this
method. See the example.).<p> </p><p>The following example code
(originally written in C# for the.NET Framework) shows a way to
check whether a given CBOR object stores a 32-bit signed integer
before getting its value.</p> <pre>CBORObject obj = CBORObject.FromInt32(99999); if (obj.AsNumber().CanTruncatedIntFitInInt32()) { /_ Not an Int32; handle the error _/ System.out.println("Not a 32-bit integer."); } else { System.out.println("The value is " + obj.AsInt32()); }</pre> .

**Returns:**

- The closest 32-bit signed integer to this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

- <code>java.lang.ArithmeticException</code> - This object's value exceeds the range of a 32-bit
  signed integer.

### AsInt64

    @Deprecated public long AsInt64()

Deprecated.
Instead, use the following: (cbor.AsNumber().ToInt64Checked()), or
.ToObject&amp;lt;long&amp;gt;() in.NET.

**Returns:**

- The closest 64-bit signed integer to this object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

- <code>java.lang.ArithmeticException</code> - This object's value exceeds the range of a 64-bit
  signed integer.

### AsSingle

    public float AsSingle()

Converts this object to a 32-bit floating point number.

**Returns:**

- The closest 32-bit floating point number to this object. The return
  value can be positive infinity or negative infinity if this object's
  value exceeds the range of a 32-bit floating point number.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

### AsString

    public java.lang.String AsString()

Gets the value of this object as a text string.

**Returns:**

- Gets this object's string.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object's type is not a text string (for
  the purposes of this method, infinity and not-a-number values, but
  not <code>CBORObject.Null</code>, are considered numbers). To check the
  CBOR object for null before conversion, use the following idiom
  (originally written in C# for the.NET version): <code>(cbor == null
  || cbor.isNull()) ? null : cbor.AsString()</code>.

### CanFitInDouble

    @Deprecated public boolean CanFitInDouble()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().CanFitInDouble()).

**Returns:**

- <code>true</code> if this object's value can be converted to a 64-bit
  floating point number without its value being rounded to another
  numerical value, or if this is a not-a-number value, even if the
  value's diagnostic information can't fit in a 64-bit floating point
  number; otherwise, <code>false</code>.

### CanFitInInt32

    @Deprecated public boolean CanFitInInt32()

Deprecated.
Instead, use.CanValueFitInInt32(), if the application allows only CBOR
integers, or (cbor.isNumber()
&amp;&amp;cbor.AsNumber().CanFitInInt32()), if the application
allows any CBOR Object convertible to an integer.

**Returns:**

- <code>true</code> if this object's numerical value is an integer, is
  -(2^31) or greater, and is less than 2^31; otherwise, <code>false</code>.

### CanFitInInt64

    @Deprecated public boolean CanFitInInt64()

Deprecated.
Instead, use CanValueFitInInt64(), if the application allows only CBOR
integers, or (cbor.isNumber()
&amp;&amp;cbor.AsNumber().CanFitInInt64()), if the application
allows any CBOR Object convertible to an integer.

**Returns:**

- <code>true</code> if this object's numerical value is an integer, is
  -(2^63) or greater, and is less than 2^63; otherwise, <code>false</code>.

### CanFitInSingle

    @Deprecated public boolean CanFitInSingle()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().CanFitInSingle()).

**Returns:**

- <code>true</code> if this object's value can be converted to a 32-bit
  floating point number without its value being rounded to another
  numerical value, or if this is a not-a-number value, even if the
  value's diagnostic information can' t fit in a 32-bit floating point
  number; otherwise, <code>false</code>.

### CanTruncatedIntFitInInt32

    @Deprecated public boolean CanTruncatedIntFitInInt32()

Deprecated.
Instead, use the following: (cbor.CanValueFitInInt32() if only
integers of any tag are allowed, or (cbor.isNumber()
&amp;&amp; cbor.AsNumber().CanTruncatedIntFitInInt32()).

**Returns:**

- <code>true</code> if this object's value, converted to an integer by
  discarding its fractional part, would be -(2^31) or greater, and
  less than 2^31; otherwise, <code>false</code>.

### CanTruncatedIntFitInInt64

    @Deprecated public boolean CanTruncatedIntFitInInt64()

Deprecated.
Instead, use the following: (cbor.CanValueFitInInt64() if only
integers of any tag are allowed, or (cbor.isNumber()
&amp;&amp; cbor.AsNumber().CanTruncatedIntFitInInt64()).

**Returns:**

- <code>true</code> if this object's value, converted to an integer by
  discarding its fractional part, would be -(2^63) or greater, and
  less than 2^63; otherwise, <code>false</code>.

### compareTo

    public int compareTo​(CBORObject other)

Compares two CBOR objects. This implementation was changed in version 4.0.

 <p>In this implementation:</p> <ul> <li>The null pointer (null
 reference) is considered less than any other object.</li> <li>If the
 two objects are both integers (CBORType.Integer) both floating-point
 values, both byte strings, both simple values (including True and
 False), or both text strings, their CBOR encodings (as though
 EncodeToBytes were called on each integer) are compared as though by
 a byte-by-byte comparison. (This means, for example, that positive
 integers sort before negative integers).</li> <li>If both objects
 have a tag, they are compared first by the tag's value then by the
 associated item (which itself can have a tag).</li> <li>If both
 objects are arrays, they are compared item by item. In this case, if
 the arrays have different numbers of items, the array with more
 items is treated as greater than the other array.</li> <li>If both
 objects are maps, their key-value pairs, sorted by key in accordance
 with this method, are compared, where each pair is compared first by
 key and then by value. In this case, if the maps have different
 numbers of key-value pairs, the map with more pairs is treated as
 greater than the other map.</li> <li>If the two objects have
 different types, the object whose type comes first in the order of
 untagged integers, untagged byte strings, untagged text strings,
 untagged arrays, untagged maps, tagged objects, untagged simple
 values (including True and False) and untagged floating point values
 sorts before the other object.</li></ul> <p>This method is
 consistent with the Equals method.</p>

**Specified by:**

- <code>compareTo</code> in interface <code>java.lang.Comparable&lt;CBORObject&gt;</code>

**Parameters:**

- <code>other</code> - A value to compare with.

**Returns:**

- A negative number, if this value is less than the other object; or
  0, if both values are equal; or a positive number, if this value is
  less than the other object or if the other object is null. This
  implementation returns a positive number if.

### CompareToIgnoreTags

    public int CompareToIgnoreTags​(CBORObject other)

Compares this object and another CBOR object, ignoring the tags they have,
if any. See the compareTo method for more information on the
comparison function.

**Parameters:**

- <code>other</code> - A value to compare with.

**Returns:**

- Less than 0, if this value is less than the other object; or 0, if
  both values are equal; or greater than 0, if this value is less than
  the other object or if the other object is null.

### ContainsKey

    public boolean ContainsKey​(java.lang.Object objKey)

Determines whether a value of the given key exists in this object.

**Parameters:**

- <code>objKey</code> - The parameter <code>objKey</code> is an arbitrary object.

**Returns:**

- <code>true</code> if the given key is found, or <code>false</code> if the
  given key is not found or this object is not a map.

### ContainsKey

    public boolean ContainsKey​(CBORObject key)

Determines whether a value of the given key exists in this object.

**Parameters:**

- <code>key</code> - An object that serves as the key. If this is <code>null</code>, checks
  for <code>CBORObject.Null</code>.

**Returns:**

- <code>true</code> if the given key is found, or <code>false</code> if the
  given key is not found or this object is not a map.

### ContainsKey

    public boolean ContainsKey​(java.lang.String key)

Determines whether a value of the given key exists in this object.

**Parameters:**

- <code>key</code> - A text string that serves as the key. If this is <code>null</code>,
  checks for <code>CBORObject.Null</code>.

**Returns:**

- <code>true</code> if the given key (as a CBOR object) is found, or <code>
  false</code> if the given key is not found or this object is not a map.

### EncodeToBytes

    public byte[] EncodeToBytes()

<p>Writes the binary representation of this CBOR object and returns a byte
 array of that representation. If the CBOR object contains CBOR maps,
 or is a CBOR map itself, the order in which the keys to the map are
 written out to the byte array is undefined unless the map was
 created using the NewOrderedMap method. The example code given in
 <see cref='PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can
 be used to write out certain keys of a CBOR map in a given order.
 For the CTAP2 (FIDO Client-to-Authenticator Protocol 2) canonical
 ordering, which is useful for implementing Web Authentication, call
  <code>EncodeToBytes(new CBOREncodeOptions("ctap2canonical=true"))</code>
 rather than this method.</p>

**Returns:**

- A byte array in CBOR format.

### EncodeToBytes

    public byte[] EncodeToBytes​(CBOREncodeOptions options)

Writes the binary representation of this CBOR object and returns a byte
array of that representation, using the specified options for
encoding the object to CBOR format. For the CTAP2 (FIDO
Client-to-Authenticator Protocol 2) canonical ordering, which is
useful for implementing Web Authentication, call this method as
follows: <code>EncodeToBytes(new
CBOREncodeOptions("ctap2canonical=true"))</code>.

**Parameters:**

- <code>options</code> - Options for encoding the data to CBOR.

**Returns:**

- A byte array in CBOR format.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>options</code> is null.

### equals

    public boolean equals​(java.lang.Object obj)

Determines whether this object and another object are equal and have the
same type. Not-a-number values can be considered equal by this
method.

**Overrides:**

- <code>equals</code> in class <code>java.lang.Object</code>

**Parameters:**

- <code>obj</code> - The parameter <code>obj</code> is an arbitrary object.

**Returns:**

- <code>true</code> if the objects are equal; otherwise, <code>false</code>. In
  this method, two objects are not equal if they don't have the same
  type or if one is null and the other isn't.

### equals

    public boolean equals​(CBORObject other)

Compares the equality of two CBOR objects. Not-a-number values can be
considered equal by this method.

**Parameters:**

- <code>other</code> - The object to compare.

**Returns:**

- <code>true</code> if the objects are equal; otherwise, <code>false</code>. In
  this method, two objects are not equal if they don't have the same
  type or if one is null and the other isn't.

### GetByteString

    public byte[] GetByteString()

Gets the backing byte array used in this CBOR object, if this object is a
byte string, without copying the data to a new byte array. Any
changes in the returned array's contents will be reflected in this
CBOR object. Note, though, that the array's length can't be changed.

**Returns:**

- The byte array held by this CBOR object.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not a byte string.

### hashCode

    public int hashCode()

Calculates the hash code of this object. The hash code for a given instance
of this class is not guaranteed to be the same across versions of
this class, and no application or process IDs are used in the hash
code calculation.

**Overrides:**

- <code>hashCode</code> in class <code>java.lang.Object</code>

**Returns:**

- A 32-bit hash code.

### GetAllTags

    public com.upokecenter.numbers.EInteger[] GetAllTags()

Gets a list of all tags, from outermost to innermost.

**Returns:**

- An array of tags, or the empty string if this object is untagged.

### HasOneTag

    public boolean HasOneTag()

Returns whether this object has only one tag.

**Returns:**

- <code>true</code> if this object has only one tag; otherwise, <code>
  false</code>.

### HasOneTag

    public boolean HasOneTag​(int tagValue)

Returns whether this object has only one tag and that tag is the given
number.

**Parameters:**

- <code>tagValue</code> - The tag number.

**Returns:**

- <code>true</code> if this object has only one tag and that tag is the
  given number; otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>tagValue</code> is less than 0.

### HasOneTag

    public boolean HasOneTag​(com.upokecenter.numbers.EInteger bigTagValue)

Returns whether this object has only one tag and that tag is the given
number, expressed as an arbitrary-precision integer.

**Parameters:**

- <code>bigTagValue</code> - An arbitrary-precision integer.

**Returns:**

- <code>true</code> if this object has only one tag and that tag is the
  given number; otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bigTagValue</code> is null.

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>bigTagValue</code> is less than 0.

### getTagCount

    public final int getTagCount()

Gets the number of tags this object has.

**Returns:**

- The number of tags this object has.

### HasMostInnerTag

    public boolean HasMostInnerTag​(int tagValue)

Returns whether this object has an innermost tag and that tag is of the
given number.

**Parameters:**

- <code>tagValue</code> - The tag number.

**Returns:**

- <code>true</code> if this object has an innermost tag and that tag is of
  the given number; otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>tagValue</code> is less than 0.

### HasMostInnerTag

    public boolean HasMostInnerTag​(com.upokecenter.numbers.EInteger bigTagValue)

Returns whether this object has an innermost tag and that tag is of the
given number, expressed as an arbitrary-precision number.

**Parameters:**

- <code>bigTagValue</code> - The tag number.

**Returns:**

- <code>true</code> if this object has an innermost tag and that tag is of
  the given number; otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bigTagValue</code> is null.

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>bigTagValue</code> is less than 0.

### HasMostOuterTag

    public boolean HasMostOuterTag​(int tagValue)

Returns whether this object has an outermost tag and that tag is of the
given number.

**Parameters:**

- <code>tagValue</code> - The tag number.

**Returns:**

- <code>true</code> if this object has an outermost tag and that tag is of
  the given number; otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>tagValue</code> is less than 0.

### HasMostOuterTag

    public boolean HasMostOuterTag​(com.upokecenter.numbers.EInteger bigTagValue)

Returns whether this object has an outermost tag and that tag is of the
given number.

**Parameters:**

- <code>bigTagValue</code> - The tag number.

**Returns:**

- <code>true</code> if this object has an outermost tag and that tag is of
  the given number; otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bigTagValue</code> is null.

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>bigTagValue</code> is less than 0.

### HasTag

    public boolean HasTag​(int tagValue)

Returns whether this object has a tag of the given number.

**Parameters:**

- <code>tagValue</code> - The tag value to search for.

**Returns:**

- <code>true</code> if this object has a tag of the given number;
  otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>tagValue</code> is less than 0.

- <code>java.lang.NullPointerException</code> - The parameter <code>tagValue</code> is null.

### HasTag

    public boolean HasTag​(com.upokecenter.numbers.EInteger bigTagValue)

Returns whether this object has a tag of the given number.

**Parameters:**

- <code>bigTagValue</code> - The tag value to search for.

**Returns:**

- <code>true</code> if this object has a tag of the given number;
  otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>bigTagValue</code> is null.

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>bigTagValue</code> is less than 0.

### Insert

    public CBORObject Insert​(int index, java.lang.Object valueOb)

Inserts an object at the specified position in this CBOR array.

**Parameters:**

- <code>index</code> - Index starting at 0 to insert at.

- <code>valueOb</code> - An object representing the value, which will be converted to
  a CBORObject. Can be null, in which case this value is converted to
  CBORObject.Null.

**Returns:**

- This instance.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not an array.

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>valueOb</code> has an unsupported
  type; or <code>index</code> is not a valid index into this array.

### IsInfinity

    @Deprecated public boolean IsInfinity()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().IsInfinity()).

**Returns:**

- <code>true</code> if this CBOR object represents infinity; otherwise,
  <code>false</code>.

### IsNaN

    @Deprecated public boolean IsNaN()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().IsNaN()).

**Returns:**

- <code>true</code> if this CBOR object represents a not-a-number value (as
  opposed to whether this object does not represent a number as
  defined by the IsNumber property or <code>isNumber()</code> method in
  Java); otherwise, <code>false</code>.

### IsNegativeInfinity

    @Deprecated public boolean IsNegativeInfinity()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().IsNegativeInfinity()).

**Returns:**

- <code>true</code> if this CBOR object represents negative infinity;
  otherwise, <code>false</code>.

### IsPositiveInfinity

    @Deprecated public boolean IsPositiveInfinity()

Deprecated.
Instead, use the following: (cbor.isNumber()
&amp;&amp; cbor.AsNumber().IsPositiveInfinity()).

**Returns:**

- <code>true</code> if this CBOR object represents positive infinity;
  otherwise, <code>false</code>.

### Negate

    @Deprecated public CBORObject Negate()

Deprecated.
Instead, convert this object to a number (with .AsNumber()), and
use that number's.Negate() method.

**Returns:**

- The reversed-sign form of this number.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object does not represent a number (for
  this purpose, infinities and not-a-number or NaN values, but not
  CBORObject.Null, are considered numbers).

### Clear

    public void Clear()

Removes all items from this CBOR array or all keys and values from this CBOR
map.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not a CBOR array or CBOR map.

### Remove

    public boolean Remove​(java.lang.Object obj)

If this object is an array, removes the first instance of the specified item
(once converted to a CBOR object) from the array. If this object is
a map, removes the item with the given key (once converted to a CBOR
object) from the map.

**Parameters:**

- <code>obj</code> - The item or key (once converted to a CBOR object) to remove.

**Returns:**

- <code>true</code> if the item was removed; otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>obj</code> is null (as opposed
  to CBORObject.Null).

- <code>java.lang.IllegalStateException</code> - The object is not an array or map.

### RemoveAt

    public boolean RemoveAt​(int index)

Removes the item at the given index of this CBOR array.

**Parameters:**

- <code>index</code> - The index, starting at 0, of the item to remove.

**Returns:**

- Returns "true" if the object was removed. Returns "false" if the
  given index is less than 0, or is at least as high as the number of
  items in the array.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not a CBOR array.

### Remove

    public boolean Remove​(CBORObject obj)

If this object is an array, removes the first instance of the specified item
from the array. If this object is a map, removes the item with the
given key from the map.

**Parameters:**

- <code>obj</code> - The item or key to remove.

**Returns:**

- <code>true</code> if the item was removed; otherwise, <code>false</code>.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>obj</code> is null (as opposed
  to CBORObject.Null).

- <code>java.lang.IllegalStateException</code> - The object is not an array or map.

### Set

    public CBORObject Set​(java.lang.Object key, java.lang.Object valueOb)

Maps an object to a key in this CBOR map, or adds the value if the key
doesn't exist. If this is a CBOR array, instead sets the value at
the given index to the given value.

**Parameters:**

- <code>key</code> - If this instance is a CBOR map, this parameter is an object
  representing the key, which will be converted to a CBORObject; in
  this case, this parameter can be null, in which case this value is
  converted to CBORObject.Null. If this instance is a CBOR array, this
  parameter must be a 32-bit signed integer(<code>int</code>) identifying
  the index (starting from 0) of the item to set in the array.

- <code>valueOb</code> - An object representing the value, which will be converted to
  a CBORObject. Can be null, in which case this value is converted to
  CBORObject.Null.

**Returns:**

- This instance.

**Throws:**

- <code>java.lang.IllegalStateException</code> - This object is not a map or an array.

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>key</code> or <code>valueOb</code> has
  an unsupported type, or this instance is a CBOR array and <code>
  key</code> is less than 0, is the size of this array or greater, or is not
  a 32-bit signed integer (<code>int</code>).

### ToJSONString

    public java.lang.String ToJSONString()

Converts this object to a text string in JavaScript object Notation (JSON)
format. See the overload to ToJSONString taking a JSONOptions
argument for further information. <p>If the CBOR object contains
CBOR maps, or is a CBOR map itself, the order in which the keys to
the map are written out to the JSON string is undefined unless the
map was created using the NewOrderedMap method. Map keys other than
untagged text strings are converted to JSON strings before writing
them out (for example, <code>22("Test")</code> is converted to
<code>"Test"</code> and <code>true</code> is converted to <code>"true"</code>). After
such conversion, if two or more keys for the same map are identical,
this method throws a CBORException. The example code given in
<b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
can be used to write out certain keys of a CBOR map in a given order
to a JSON string, or to write out a CBOR object as part of a JSON
text sequence.</p> <p><b>Warning:</b> In general, if this CBOR
object contains integer map keys or uses other features not
supported in JSON, and the application converts this CBOR object to
JSON and back to CBOR, the application <i>should not</i> expect the
new CBOR object to be exactly the same as the original. This is
because the conversion in many cases may have to convert unsupported
features in JSON to supported features which correspond to a
different feature in CBOR (such as converting integer map keys,
which are supported in CBOR but not JSON, to text strings, which are
supported in both).</p>

**Returns:**

- A text string containing the converted object in JSON format.

### ToJSONString

    public java.lang.String ToJSONString​(JSONOptions options)

Converts this object to a text string in JavaScript object Notation (JSON)
format, using the specified options to control the encoding process.
This function works not only with arrays and maps, but also
integers, strings, byte arrays, and other JSON data types. Notes:

 <ul><li>If this object contains maps with non-string keys, the keys
 are converted to JSON strings before writing the map as a JSON
 string.</li> <li>If this object represents a number (the IsNumber
 property, or isNumber() method in Java, returns true), then it is
 written out as a number.</li> <li>If the CBOR object contains CBOR
 maps, or is a CBOR map itself, the order in which the keys to the
 map are written out to the JSON string is undefined unless the map
 was created using the NewOrderedMap method. Map keys other than
 untagged text strings are converted to JSON strings before writing
  them out (for example, <code>22("Test")</code> is converted to
  <code>"Test"</code> and <code>true</code> is converted to <code>"true"</code>). After
 such conversion, if two or more keys for the same map are identical,
 this method throws a CBORException.</li> <li>If a number in the form
 of an arbitrary-precision binary floating-point number has a very
 high binary exponent, it will be converted to a double before being
 converted to a JSON string. (The resulting double could overflow to
 infinity, in which case the arbitrary-precision binary
 floating-point number is converted to null.)</li> <li>The string
 will not begin with a byte-order mark (U+FEFF); RFC 8259 (the JSON
 specification) forbids placing a byte-order mark at the beginning of
 a JSON string.</li> <li>Byte strings are converted to Base64 URL
 without whitespace or padding by default (see section 3.4.5.3 of RFC
 8949). A byte string will instead be converted to traditional base64
 without whitespace and with padding if it has tag 22, or base16 for
 tag 23. (To create a CBOR object with a given tag, call the
 <code>CBORObject.FromObjectAndTag</code> method and pass the CBOR object
 and the desired tag number to that method.)</li> <li>Rational
 numbers will be converted to their exact form, if possible,
 otherwise to a high-precision approximation. (The resulting
 approximation could overflow to infinity, in which case the rational
 number is converted to null.)</li> <li>Simple values other than true
 and false will be converted to null. (This doesn't include
 floating-point numbers.)</li> <li>Infinity and not-a-number will be
 converted to null.</li> </ul> <p><b>Warning:</b> In general, if this
 CBOR object contains integer map keys or uses other features not
 supported in JSON, and the application converts this CBOR object to
 JSON and back to CBOR, the application <i>should not</i> expect the
 new CBOR object to be exactly the same as the original. This is
 because the conversion in many cases may have to convert unsupported
 features in JSON to supported features which correspond to a
 different feature in CBOR (such as converting integer map keys,
 which are supported in CBOR but not JSON, to text strings, which are
 supported in both).</p> <p>The example code given below (originally
 written in C# for the.NET version) can be used to write out certain
  keys of a CBOR map in a given order to a JSON string.</p> <pre>/* Generates a JSON string of 'mapObj' whose keys are in the order given in 'keys' . Only keys found in 'keys' will be written if they exist in 'mapObj'. */ private static string KeysToJSONMap(CBORObject mapObj, List&lt;CBORObject&gt; keys) { if (mapObj == null) { throw new NullPointerException)"mapObj");} if (keys == null) { throw new NullPointerException)"keys");} if (obj.getType() != CBORType.Map) { throw new IllegalArgumentException("'obj' is not a map."); } StringBuilder builder = new StringBuilder(); boolean first = true; builder.append("{"); for (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { if (!first) {builder.append(", ");} var keyString=(key.getCBORType() == CBORType.string) ? key.AsString() : key.ToJSONString(); builder.append(CBORObject.FromObject(keyString) .ToJSONString()) .append(":").append(mapObj.get(key).ToJSONString()); first=false; } } return builder.append("}").toString(); }</pre> .

**Parameters:**

- <code>options</code> - Specifies options to control writing the CBOR object to JSON.

**Returns:**

- A text string containing the converted object in JSON format.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>options</code> is null.

### toString

    public java.lang.String toString()

Returns this CBOR object in a text form intended to be read by humans. The
value returned by this method is not intended to be parsed by
computer programs, and the exact text of the value may change at any
time between versions of this library. <p>The returned string is not
necessarily in JavaScript object Notation (JSON); to convert CBOR
objects to JSON strings, use the
<b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
method instead.</p>

**Overrides:**

- <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

- A text representation of this object.

### Untag

    public CBORObject Untag()

Gets an object with the same value as this one but without the tags it has,
if any. If this object is an array, map, or byte string, the data
will not be copied to the returned object, so changes to the
returned object will be reflected in this one.

**Returns:**

- A CBOR object.

### UntagOne

    public CBORObject UntagOne()

Gets an object with the same value as this one but without this object's
outermost tag, if any. If this object is an array, map, or byte
string, the data will not be copied to the returned object, so
changes to the returned object will be reflected in this one.

**Returns:**

- A CBOR object.

### WriteJSONTo

    public void WriteJSONTo​(java.io.OutputStream outputStream) throws java.io.IOException

Converts this object to a text string in JavaScript object Notation (JSON)
format, as in the ToJSONString method, and writes that string to a
data stream in UTF-8. If the CBOR object contains CBOR maps, or is a
CBOR map, the order in which the keys to the map are written out to
the JSON string is undefined unless the map was created using the
NewOrderedMap method. The example code given in
<b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
can be used to write out certain keys of a CBOR map in a given order
to a JSON string.<p> </p><p>The following example (originally written in
C# for the.NET version) writes out a CBOR object as part of a JSON
text sequence (RFC 7464).</p> <pre> stream.write(0x1e); /_ RS _/ cborObject.WriteJSONTo(stream); /_ JSON _/ stream.write(0x0a); /_ LF _/ </pre> <p>The following example (originally written
in C# for the.NET version) shows how to use the
<code>LimitedMemoryStream</code> class (implemented in
<i>LimitedMemoryStream.cs</i> in the peteroupc/CBOR open-source
repository) to limit the size of supported JSON serializations of
CBOR objects.</p> <pre> /_ maximum supported JSON size in bytes_/ int maxSize = 20000; {
LimitedMemoryStream ms = null;
try {
ms = new LimitedMemoryStream(maxSize);
cborObject.WriteJSONTo(ms); var bytes = ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre> <p>The following example (written
in Java for the Java version) shows how to use a subclassed
<code>OutputStream</code> together with a <code>ByteArrayOutputStream</code> to
limit the size of supported JSON serializations of CBOR objects.</p>

 <pre> /* maximum supported JSON size in bytes*/ final int maxSize = 20000; ByteArrayOutputStream ba = new ByteArrayOutputStream(); /* throws UnsupportedOperationException if too big*/ cborObject.WriteJSONTo(new FilterOutputStream(ba) { private int size = 0; public void write(byte[] b, int off, int len) { if (len&gt;(maxSize-size)) { throw new UnsupportedOperationException(); } size+=len; out.write(b, off, len); } public void write(byte b) { if (size &gt;= maxSize) { throw new UnsupportedOperationException(); } size++; out.write(b); } }); byte[] bytes = ba.toByteArray(); </pre>
 <p>The following example (originally written in C# for the.NET
 version) shows how to use a.NET MemoryStream to limit the size of
 supported JSON serializations of CBOR objects. The disadvantage is
 that the extra memory needed to do so can be wasteful, especially if
 the average serialized object is much smaller than the maximum size
 given (for example, if the maximum size is 20000 bytes, but the
 average serialized object has a size of 50 bytes).</p> <pre> byte[] backing = new byte[20000]; /* maximum supported JSON size in bytes*/ byte[] bytes1, bytes2; {
java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream(backing);
 /* throws UnsupportedOperationException if too big*/ cborObject.WriteJSONTo(ms); bytes1 = new byte[ms.size()]; /* Copy serialized data if successful*/ System.ArrayCopy(backing, 0, bytes1, 0, (int)ms.size()); /* Reset memory stream*/ ms.size() = 0; cborObject2.WriteJSONTo(ms); bytes2 = new byte[ms.size()]; /* Copy serialized data if successful*/ System.ArrayCopy(backing, 0, bytes2, 0, (int)ms.size());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre>

**Parameters:**

- <code>outputStream</code> - A writable data stream.

**Throws:**

- <code>java.io.IOException</code> - An I/O error occurred.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

### WriteJSONTo

    public void WriteJSONTo​(java.io.OutputStream outputStream, JSONOptions options) throws java.io.IOException

Converts this object to a text string in JavaScript object Notation (JSON)
format, as in the ToJSONString method, and writes that string to a
data stream in UTF-8, using the given JSON options to control the
encoding process. If the CBOR object contains CBOR maps, or is a
CBOR map, the order in which the keys to the map are written out to
the JSON string is undefined unless the map was created using the
NewOrderedMap method. The example code given in
<b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
can be used to write out certain keys of a CBOR map in a given order
to a JSON string.

**Parameters:**

- <code>outputStream</code> - A writable data stream.

- <code>options</code> - An object containing the options to control writing the CBOR
  object to JSON.

**Throws:**

- <code>java.io.IOException</code> - An I/O error occurred.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

### FromFloatingPointBits

    public static CBORObject FromFloatingPointBits​(long floatingBits, int byteCount)

Generates a CBOR object from a floating-point number represented by its
bits.

**Parameters:**

- <code>floatingBits</code> - The bits of a floating-point number number to write.

- <code>byteCount</code> - The number of bytes of the stored floating-point number;
  this also specifies the format of the "floatingBits" parameter. This
  value can be 2 if "floatingBits"'s lowest (least significant) 16
  bits identify the floating-point number in IEEE 754r binary16
  format; or 4 if "floatingBits"'s lowest (least significant) 32 bits
  identify the floating-point number in IEEE 754r binary32 format; or
  8 if "floatingBits" identifies the floating point number in IEEE
  754r binary64 format. Any other values for this parameter are
  invalid.

**Returns:**

- A CBOR object storing the given floating-point number.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
  4, or 8.

### WriteFloatingPointBits

    public static int WriteFloatingPointBits​(java.io.OutputStream outputStream, long floatingBits, int byteCount) throws java.io.IOException

Writes the bits of a floating-point number in CBOR format to a data stream.

**Parameters:**

- <code>outputStream</code> - A writable data stream.

- <code>floatingBits</code> - The bits of a floating-point number number to write.

- <code>byteCount</code> - The number of bytes of the stored floating-point number;
  this also specifies the format of the "floatingBits" parameter. This
  value can be 2 if "floatingBits"'s lowest (least significant) 16
  bits identify the floating-point number in IEEE 754r binary16
  format; or 4 if "floatingBits"'s lowest (least significant) 32 bits
  identify the floating-point number in IEEE 754r binary32 format; or
  8 if "floatingBits" identifies the floating point number in IEEE
  754r binary64 format. Any other values for this parameter are
  invalid. This method will write one plus this many bytes to the data
  stream.

**Returns:**

- The number of 8-bit bytes ordered to be written to the data stream.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
  4, or 8.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

- <code>java.io.IOException</code>

### WriteFloatingPointBits

    public static int WriteFloatingPointBits​(java.io.OutputStream outputStream, long floatingBits, int byteCount, boolean shortestForm) throws java.io.IOException

Writes the bits of a floating-point number in CBOR format to a data stream.

**Parameters:**

- <code>outputStream</code> - A writable data stream.

- <code>floatingBits</code> - The bits of a floating-point number number to write.

- <code>byteCount</code> - The number of bytes of the stored floating-point number;
  this also specifies the format of the "floatingBits" parameter. This
  value can be 2 if "floatingBits"'s lowest (least significant) 16
  bits identify the floating-point number in IEEE 754r binary16
  format; or 4 if "floatingBits"'s lowest (least significant) 32 bits
  identify the floating-point number in IEEE 754r binary32 format; or
  8 if "floatingBits" identifies the floating point number in IEEE
  754r binary64 format. Any other values for this parameter are
  invalid.

- <code>shortestForm</code> - If true, writes the shortest form of the floating-point
  number that preserves its value. If false, this method will write
  the number in the form given by 'floatingBits' by writing one plus
  the number of bytes given by 'byteCount' to the data stream.

**Returns:**

- The number of 8-bit bytes ordered to be written to the data stream.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
  4, or 8.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

- <code>java.io.IOException</code>

### WriteFloatingPointValue

    public static int WriteFloatingPointValue​(java.io.OutputStream outputStream, double doubleVal, int byteCount) throws java.io.IOException

Writes a 64-bit binary floating-point number in CBOR format to a data
stream, either in its 64-bit form, or its rounded 32-bit or 16-bit
equivalent.

**Parameters:**

- <code>outputStream</code> - A writable data stream.

- <code>doubleVal</code> - The double-precision floating-point number to write.

- <code>byteCount</code> - The number of 8-bit bytes of the stored number. This value
  can be 2 to store the number in IEEE 754r binary16, rounded to
  nearest, ties to even; or 4 to store the number in IEEE 754r
  binary32, rounded to nearest, ties to even; or 8 to store the number
  in IEEE 754r binary64. Any other values for this parameter are
  invalid.

**Returns:**

- The number of 8-bit bytes ordered to be written to the data stream.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
  4, or 8.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

- <code>java.io.IOException</code>

### WriteFloatingPointValue

    public static int WriteFloatingPointValue​(java.io.OutputStream outputStream, float singleVal, int byteCount) throws java.io.IOException

Writes a 32-bit binary floating-point number in CBOR format to a data
stream, either in its 64- or 32-bit form, or its rounded 16-bit
equivalent.

**Parameters:**

- <code>outputStream</code> - A writable data stream.

- <code>singleVal</code> - The single-precision floating-point number to write.

- <code>byteCount</code> - The number of 8-bit bytes of the stored number. This value
  can be 2 to store the number in IEEE 754r binary16, rounded to
  nearest, ties to even; or 4 to store the number in IEEE 754r
  binary32; or 8 to store the number in IEEE 754r binary64. Any other
  values for this parameter are invalid.

**Returns:**

- The number of 8-bit bytes ordered to be written to the data stream.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>byteCount</code> is other than 2,
  4, or 8.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

- <code>java.io.IOException</code>

### WriteValue

    public static int WriteValue​(java.io.OutputStream outputStream, int majorType, long value) throws java.io.IOException

Writes a CBOR major type number and an integer 0 or greater associated with
it to a data stream, where that integer is passed to this method as
a 64-bit signed integer. This is a low-level method that is useful
for implementing custom CBOR encoding methodologies. This method
encodes the given major type and value in the shortest form allowed
for the major type.<p>There are other useful things to note when
encoding CBOR that are not covered by this WriteValue method. To
mark the start of an indefinite-length array, write the 8-bit byte
0x9f to the output stream. To mark the start of an indefinite-length
map, write the 8-bit byte 0xbf to the output stream. To mark the end
of an indefinite-length array or map, write the 8-bit byte 0xff to
the output stream. For examples, see the WriteValue(InputStream, int,
int) overload.</p>

**Parameters:**

- <code>outputStream</code> - A writable data stream.

- <code>majorType</code> - The CBOR major type to write. This is a number from 0
  through 7 as follows. 0: integer 0 or greater; 1: negative integer;
  2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
  simple value. See RFC 8949 for details on these major types.

- <code>value</code> - An integer 0 or greater associated with the major type, as
  follows. 0: integer 0 or greater; 1: the negative integer's absolute
  value is 1 plus this number; 2: length in bytes of the byte string;
  3: length in bytes of the UTF-8 text string; 4: number of items in
  the array; 5: number of key-value pairs in the map; 6: tag number;
  7: simple value number, which must be in the interval [0, 23] or
  [32, 255].

**Returns:**

- The number of bytes ordered to be written to the data stream.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - Value is from 24 to 31 and major type is 7.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

- <code>java.io.IOException</code>

### WriteValue

    public static int WriteValue​(java.io.OutputStream outputStream, int majorType, int value) throws java.io.IOException

Writes a CBOR major type number and an integer 0 or greater associated with
it to a data stream, where that integer is passed to this method as
a 32-bit signed integer. This is a low-level method that is useful
for implementing custom CBOR encoding methodologies. This method
encodes the given major type and value in the shortest form allowed
for the major type.<p>There are other useful things to note when
encoding CBOR that are not covered by this WriteValue method. To
mark the start of an indefinite-length array, write the 8-bit byte
0x9f to the output stream. To mark the start of an indefinite-length
map, write the 8-bit byte 0xbf to the output stream. To mark the end
of an indefinite-length array or map, write the 8-bit byte 0xff to
the output stream.</p><p> </p><p>In the following example, an array of
three objects is written as CBOR to a data stream.</p>

  <pre>/* array, length 3*/ CBORObject.WriteValue(stream, 4, 3); /* item 1 */ CBORObject.Write("hello world", stream); CBORObject.Write(25, stream); /* item 2*/ CBORObject.Write(false, stream); /* item 3*/</pre> <p>In the following

example, a map consisting of two key-value pairs is written as CBOR
to a data stream.</p> <pre>CBORObject.WriteValue(stream, 5, 2); /_ map, 2 pairs_/ CBORObject.Write("number", stream); /_ key 1 _/ CBORObject.Write(25, stream); /_ value 1 _/ CBORObject.Write("string", stream); /_ key 2_/ CBORObject.Write("hello", stream); /_ value 2_/</pre> <p>In the following example
(originally written in C# for the.NET Framework version), a text
string is written as CBOR to a data stream.</p> <pre>string str = "hello world"; byte[] bytes = DataUtilities.GetUtf8Bytes(str, true); CBORObject.WriteValue(stream, 4, bytes.length); stream.write(bytes, 0, bytes.length);</pre> .

**Parameters:**

- <code>outputStream</code> - A writable data stream.

- <code>majorType</code> - The CBOR major type to write. This is a number from 0
  through 7 as follows. 0: integer 0 or greater; 1: negative integer;
  2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
  simple value. See RFC 8949 for details on these major types.

- <code>value</code> - An integer 0 or greater associated with the major type, as
  follows. 0: integer 0 or greater; 1: the negative integer's absolute
  value is 1 plus this number; 2: length in bytes of the byte string;
  3: length in bytes of the UTF-8 text string; 4: number of items in
  the array; 5: number of key-value pairs in the map; 6: tag number;
  7: simple value number, which must be in the interval [0, 23] or
  [32, 255].

**Returns:**

- The number of bytes ordered to be written to the data stream.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - Value is from 24 to 31 and major type is 7.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> is null.

- <code>java.io.IOException</code>

### WriteValue

    public static int WriteValue​(java.io.OutputStream outputStream, int majorType, com.upokecenter.numbers.EInteger bigintValue) throws java.io.IOException

Writes a CBOR major type number and an integer 0 or greater associated with
it to a data stream, where that integer is passed to this method as
an arbitrary-precision integer. This is a low-level method that is
useful for implementing custom CBOR encoding methodologies. This
method encodes the given major type and value in the shortest form
allowed for the major type.<p>There are other useful things to note
when encoding CBOR that are not covered by this WriteValue method.
To mark the start of an indefinite-length array, write the 8-bit
byte 0x9f to the output stream. To mark the start of an
indefinite-length map, write the 8-bit byte 0xbf to the output
stream. To mark the end of an indefinite-length array or map, write
the 8-bit byte 0xff to the output stream.</p>

**Parameters:**

- <code>outputStream</code> - A writable data stream.

- <code>majorType</code> - The CBOR major type to write. This is a number from 0
  through 7 as follows. 0: integer 0 or greater; 1: negative integer;
  2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
  simple value. See RFC 8949 for details on these major types.

- <code>bigintValue</code> - An integer 0 or greater associated with the major type,
  as follows. 0: integer 0 or greater; 1: the negative integer's
  absolute value is 1 plus this number; 2: length in bytes of the byte
  string; 3: length in bytes of the UTF-8 text string; 4: number of
  items in the array; 5: number of key-value pairs in the map; 6: tag
  number; 7: simple value number, which must be in the interval [0,
  23] or [32, 255]. For major types 0 to 6, this number may not be
  greater than 2^64 - 1.

**Returns:**

- The number of bytes ordered to be written to the data stream.

**Throws:**

- <code>java.lang.IllegalArgumentException</code> - The parameter <code>majorType</code> is 7 and value is
  greater than 255.

- <code>java.lang.NullPointerException</code> - The parameter <code>outputStream</code> or <code>
  bigintValue</code> is null.

- <code>java.io.IOException</code>

### WriteTo

    public void WriteTo​(java.io.OutputStream stream) throws java.io.IOException

<p>Writes this CBOR object to a data stream. If the CBOR object contains
 CBOR maps, or is a CBOR map, the order in which the keys to the map
 are written out to the data stream is undefined unless the map was
 created using the NewOrderedMap method. See the examples (originally
 written in C# for the.NET version) for ways to write out certain
 keys of a CBOR map in a given order. In the case of CBOR objects of
 type FloatingPoint, the number is written using the shortest
 floating-point encoding possible; this is a change from previous
 versions.</p><p> </p><p>The following example shows a method that writes
 each key of 'mapObj' to 'outputStream', in the order given in
 'keys', where 'mapObj' is written out in the form of a CBOR
 <b>definite-length map</b> . Only keys found in 'keys' will be
  written if they exist in 'mapObj'.</p> <pre>private static void WriteKeysToMap(CBORObject mapObj, List&lt;CBORObject&gt; keys, OutputStream outputStream) throws java.io.IOException { if (mapObj == null) { throw new NullPointerException("mapObj");} if (keys == null) {throw new NullPointerException("keys");} if (outputStream == null) {throw new NullPointerException("outputStream");} if (obj.getType()!=CBORType.Map) { throw new IllegalArgumentException("'obj' is not a map."); } int keyCount = 0; for (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { keyCount++; } } CBORObject.WriteValue(outputStream, 5, keyCount); for (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { key.WriteTo(outputStream); mapObj.get(key).WriteTo(outputStream); } } }</pre> <p>The following example shows a method that writes each
 key of 'mapObj' to 'outputStream', in the order given in 'keys',
 where 'mapObj' is written out in the form of a CBOR
 <b>indefinite-length map</b> . Only keys found in 'keys' will be
  written if they exist in 'mapObj'.</p> <pre>private static void WriteKeysToIndefMap(CBORObject mapObj, List&lt;CBORObject&gt; keys, OutputStream outputStream) throws java.io.IOException { if (mapObj == null) { throw new NullPointerException("mapObj");} if (keys == null) {throw new NullPointerException("keys");} if (outputStream == null) {throw new NullPointerException("outputStream");} if (obj.getType()!=CBORType.Map) { throw new IllegalArgumentException("'obj' is not a map."); } outputStream.write((byte)0xBF); for (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { key.WriteTo(outputStream); mapObj.get(key).WriteTo(outputStream); } } outputStream.write((byte)0xff); }</pre> <p>The following example
 shows a method that writes out a list of objects to 'outputStream'
  as an <b>indefinite-length CBOR array</b> .</p> <pre>private static void WriteToIndefArray(List&lt;object&gt; list, OutputStream outputStream) throws java.io.IOException { if (list == null) { throw new NullPointerException("list");} if (outputStream == null) {throw new NullPointerException("outputStream");} outputStream.write((byte)0x9f); for (object item in list) { new CBORObject(item).WriteTo(outputStream); } outputStream.write((byte)0xff); }</pre> <p>The following example
 (originally written in C# for the.NET version) shows how to use the
 <code>LimitedMemoryStream</code> class (implemented in
 <i>LimitedMemoryStream.cs</i> in the peteroupc/CBOR open-source
 repository) to limit the size of supported CBOR serializations.</p>
 <pre> /* maximum supported CBOR size in bytes*/ int maxSize = 20000; {
LimitedMemoryStream ms = null;
try {
ms = new LimitedMemoryStream(maxSize);
 cborObject.WriteTo(ms); var bytes = ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre> <p>The following example (written in Java for
 the Java version) shows how to use a subclassed <code>OutputStream</code>
 together with a <code>ByteArrayOutputStream</code> to limit the size of
 supported CBOR serializations.</p> <pre> /* maximum supported CBOR size in bytes*/ final int maxSize = 20000; ByteArrayOutputStream ba = new ByteArrayOutputStream(); /* throws UnsupportedOperationException if too big*/ cborObject.WriteTo(new FilterOutputStream(ba) { private int size = 0; public void write(byte[] b, int off, int len) { if (len&gt;(maxSize-size)) { throw new UnsupportedOperationException(); } size+=len; out.write(b, off, len); } public void write(byte b) { if (size &gt;= maxSize) { throw new UnsupportedOperationException(); } size++; out.write(b); } }); byte[] bytes = ba.toByteArray(); </pre>
 <p>The following example (originally written in C# for the.NET
 version) shows how to use a.NET MemoryStream to limit the size of
 supported CBOR serializations. The disadvantage is that the extra
 memory needed to do so can be wasteful, especially if the average
 serialized object is much smaller than the maximum size given (for
 example, if the maximum size is 20000 bytes, but the average
 serialized object has a size of 50 bytes).</p> <pre> byte[] backing = new byte[20000]; /* maximum supported CBOR size in bytes*/ byte[] bytes1, bytes2; {
java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream(backing);
 /* throws UnsupportedOperationException if too big*/ cborObject.WriteTo(ms); bytes1 = new byte[ms.size()]; /* Copy serialized data if successful*/ System.ArrayCopy(backing, 0, bytes1, 0, (int)ms.size()); /* Reset memory stream*/ ms.size() = 0; cborObject2.WriteTo(ms); bytes2 = new byte[ms.size()]; /* Copy serialized data if successful*/ System.ArrayCopy(backing, 0, bytes2, 0, (int)ms.size());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre>

**Parameters:**

- <code>stream</code> - A writable data stream.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

### WriteTo

    public void WriteTo​(java.io.OutputStream stream, CBOREncodeOptions options) throws java.io.IOException

Writes this CBOR object to a data stream, using the specified options for
encoding the data to CBOR format. If the CBOR object contains CBOR
maps, or is a CBOR map, the order in which the keys to the map are
written out to the data stream is undefined unless the map was
created using the NewOrderedMap method. The example code given in
<see cref='PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can
be used to write out certain keys of a CBOR map in a given order. In
the case of CBOR objects of type FloatingPoint, the number is
written using the shortest floating-point encoding possible; this is
a change from previous versions.

**Parameters:**

- <code>stream</code> - A writable data stream.

- <code>options</code> - Options for encoding the data to CBOR.

**Throws:**

- <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

- <code>java.io.IOException</code> - An I/O error occurred.

- <code>java.lang.IllegalArgumentException</code> - Unexpected data type".
