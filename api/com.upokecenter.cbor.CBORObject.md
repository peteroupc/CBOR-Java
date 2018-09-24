# com.upokecenter.cbor.CBORObject

    public final class CBORObject extends Object implements Comparable<CBORObject>

<p>Represents an object in Concise Binary Object Representation (CBOR) and
 contains methods for reading and writing CBOR data. CBOR is defined
 in RFC 7049.</p> <p><b>Converting CBOR objects</b> </p> <p>There are
 many ways to get a CBOR object, including from bytes, objects,
 streams and JSON, as described below. </p> <p><b>To and from byte
 arrays:</b> The CBORObject.DecodeFromBytes method converts a byte
 array in CBOR format to a CBOR object. The EncodeToBytes method
 converts a CBOR object to its corresponding byte array in CBOR
 format. </p> <p><b>To and from data streams:</b> The CBORObject.Write
 methods write many kinds of objects to a data stream, including
 numbers, CBOR objects, strings, and arrays of numbers and strings.
 The CBORObject.Read method reads a CBOR object from a data stream.
 </p> <p><b>To and from other objects:</b> The
 <code>CBORObject.FromObject</code> method converts many kinds of objects to
 a CBOR object, including numbers, strings, and arrays and maps of
 numbers and strings. Methods like AsDouble, AsByte, and AsString
 convert a CBOR object to different types of object. The
 <code>CBORObject.ToObject</code> method converts a CBOR object to an object
 of a given type; for example, a CBOR array to a native <code>List</code>
 (or <code>ArrayList</code> in Java), or a CBOR integer to an <code>int</code> or
 <code>long</code>. </p> <p><b>To and from JSON:</b> This class also doubles
 as a reader and writer of JavaScript Object Notation (JSON). The
 CBORObject.FromJSONString method converts JSON to a CBOR object, and
 the ToJSONString method converts a CBOR object to a JSON string. </p>
 <p>In addition, the CBORObject.WriteJSON method writes many kinds of
 objects as JSON to a data stream, including numbers, CBOR objects,
 strings, and arrays of numbers and strings. The CBORObject.Read
 method reads a CBOR object from a JSON data stream. </p>
 <p><b>Comparison Considerations:</b> </p> <p>Instances of CBORObject
 should not be compared for equality using the "==" operator; it's
 possible to create two CBOR objects with the same value but not the
 same reference. (The "==" operator might only check if each side of
 the operator is the same instance.) </p> <p>This class's natural
 ordering (under the compareTo method) is not consistent with the
 Equals method. This means that two values that compare as equal under
 the compareTo method might not be equal under the Equals method. This
 is important to consider especially if an application wants to
 compare numbers, since the CBOR number type supports numbers of
 different formats, such as big integers, rational numbers, and
 arbitrary-precision decimal numbers. </p> <p>Another consideration is
 that two values that are otherwise equal may have different tags. To
 strip the tags from a CBOR object before comparing, use the
 <code>Untag</code> method. </p> <p>To compare two numbers, the
 CompareToIgnoreTags or compareTo method should be used. Which method
 to use depends on whether two equal values should still be considered
 equal if they have different tags. </p> <p>Although this class is
 inconsistent with the Equals method, it is safe to use CBORObject
 instances as hash keys as long as all of the keys are untagged text
 strings (which means GetTags returns an empty array and the Type
 property, or "getType()" in Java, returns TextString). This is
 because the natural ordering of these instances is consistent with
 the Equals method. </p> <p><b>Thread Safety:</b> </p> <p>CBOR objects
 that are numbers, "simple values", and text strings are immutable
 (their values can't be changed), so they are inherently safe for use
 by multiple threads. </p> <p>CBOR objects that are arrays, maps, and
 byte strings are mutable, but this class doesn't attempt to
 synchronize reads and writes to those objects by multiple threads, so
 those objects are not thread safe without such synchronization. </p>
 <p>One kind of CBOR object is called a map, or a list of key-value
 pairs. Keys can be any kind of CBOR object, including numbers,
 strings, arrays, and maps. However, text strings are the most
 suitable to use as keys; other kinds of CBOR object are much better
 used as map values instead, keeping in mind that some of them are not
 thread safe without synchronizing reads and writes to them. </p>
 <p>To find the type of a CBOR object, call its Type property (or
 "getType()" in Java). The return value can be Number, Boolean,
 SimpleValue, or TextString for immutable CBOR objects, and Array,
 Map, or ByteString for mutable CBOR objects. </p> <p><b>Nesting
 Depth:</b> </p> <p>The DecodeFromBytes and Read methods can only read
 objects with a limited maximum depth of arrays and maps nested within
 other arrays and maps. The code sets this maximum depth to 500
 (allowing more than enough nesting for most purposes), but it's
 possible that stack overflows in some runtimes might lower the
 effective maximum nesting depth. When the nesting depth goes above
 500, the DecodeFromBytes and Read methods throw a CBORException. </p>
 <p>The ReadJSON and FromJSONString methods currently have nesting
 depths of 1000. </p>

## Fields

* `static CBORObject False`<br>
 Represents the value false.
* `static CBORObject NaN`<br>
 A not-a-number value.
* `static CBORObject NegativeInfinity`<br>
 The value negative infinity.
* `static CBORObject Null`<br>
 Represents the value null.
* `static CBORObject PositiveInfinity`<br>
 The value positive infinity.
* `static CBORObject True`<br>
 Represents the value true.
* `static CBORObject Undefined`<br>
 Represents the value undefined.
* `static CBORObject Zero`<br>
 Gets a CBOR object for the number zero.

## Methods

* `CBORObject Abs()`<br>
 Gets this object's absolute value.
* `CBORObject Add​(CBORObject obj)`<br>
 Adds a new object to the end of this array.
* `CBORObject Add​(Object obj)`<br>
 Converts an object to a CBOR object and adds it to the end of this
 array.
* `CBORObject Add​(Object key,
   Object valueOb)`<br>
 Adds a new key and its value to this CBOR map, or adds the value if the
 key doesn't exist.
* `static CBORObject Addition​(CBORObject first,
        CBORObject second)`<br>
 Finds the sum of two CBOR numbers.
* `boolean AsBoolean()`<br>
 Returns false if this object is False, Null, or Undefined; otherwise, true.
* `byte AsByte()`<br>
 Converts this object to a byte (0 to 255).
* `double AsDouble()`<br>
 Converts this object to a 64-bit floating point number.
* `com.upokecenter.numbers.EDecimal AsEDecimal()`<br>
 Converts this object to a decimal number.
* `com.upokecenter.numbers.EFloat AsEFloat()`<br>
 Converts this object to an arbitrary-precision binary floating point number.
* `com.upokecenter.numbers.EInteger AsEInteger()`<br>
 Converts this object to an arbitrary-precision integer.
* `com.upokecenter.numbers.ERational AsERational()`<br>
 Converts this object to a rational number.
* `short AsInt16()`<br>
 Converts this object to a 16-bit signed integer.
* `int AsInt32()`<br>
 Converts this object to a 32-bit signed integer.
* `long AsInt64()`<br>
 Converts this object to a 64-bit signed integer.
* `float AsSingle()`<br>
 Converts this object to a 32-bit floating point number.
* `String AsString()`<br>
 Gets the value of this object as a text string.
* `boolean CanFitInDouble()`<br>
 Returns whether this object's value can be converted to a 64-bit floating
 point number without its value being rounded to another numerical
 value.
* `boolean CanFitInInt32()`<br>
 Returns whether this object's numerical value is an integer, is -(2^31) or
 greater, and is less than 2^31.
* `boolean CanFitInInt64()`<br>
 Returns whether this object's numerical value is an integer, is -(2^63) or
 greater, and is less than 2^63.
* `boolean CanFitInSingle()`<br>
 Returns whether this object's value can be converted to a 32-bit floating
 point number without its value being rounded to another numerical
 value.
* `boolean CanTruncatedIntFitInInt32()`<br>
 Returns whether this object's value, truncated to an integer, would be
 -(2^31) or greater, and less than 2^31.
* `boolean CanTruncatedIntFitInInt64()`<br>
 Returns whether this object's value, truncated to an integer, would be
 -(2^63) or greater, and less than 2^63.
* `void Clear()`<br>
 Removes all items from this CBOR array or all keys and values from this CBOR
 map.
* `int compareTo​(CBORObject other)`<br>
 Compares two CBOR objects.
* `int CompareToIgnoreTags​(CBORObject other)`<br>
 Compares this object and another CBOR object, ignoring the tags they have,
 if any.
* `boolean ContainsKey​(CBORObject key)`<br>
 Determines whether a value of the given key exists in this object.
* `boolean ContainsKey​(String key)`<br>
 Determines whether a value of the given key exists in this object.
* `static CBORObject DecodeFromBytes​(byte[] data) CBOREncodeOptions`<br>
 At the moment, use the overload of this method that takes a CBOREncodeOptions object.
* `static CBORObject DecodeFromBytes​(byte[] data,
               CBOREncodeOptions options) CBOREncodeOptions`<br>
 Generates a CBOR object from an array of CBOR-encoded bytes, using the given
 CBOREncodeOptions object to control the decoding
 process.
* `static CBORObject Divide​(CBORObject first,
      CBORObject second)`<br>
 Divides a CBORObject object by the value of a CBORObject object.
* `byte[] EncodeToBytes() CBOREncodeOptions`<br>
 At the moment, use the overload of this method that takes a CBOREncodeOptions object.
* `byte[] EncodeToBytes​(CBOREncodeOptions options)`<br>
 Writes the binary representation of this CBOR object and returns a byte
 array of that representation, using the specified options for
 encoding the object to CBOR format.
* `boolean equals​(CBORObject other)`<br>
 Compares the equality of two CBOR objects.
* `boolean equals​(Object obj)`<br>
 Determines whether this object and another object are equal and have the
 same type.
* `static CBORObject FromJSONString​(String str)`<br>
 Generates a CBOR object from a text string in JavaScript Object Notation
 (JSON) format.
* `static CBORObject FromJSONString​(String str,
              CBOREncodeOptions options)`<br>
 Generates a CBOR object from a text string in JavaScript Object Notation
 (JSON) format, using the specified options to control the decoding
 process.
* `static CBORObject FromObject​(boolean value)`<br>
 Returns the CBOR true value or false value, depending on "value".
* `static CBORObject FromObject​(byte value)`<br>
 Generates a CBOR object from a byte (0 to 255).
* `static CBORObject FromObject​(byte[] bytes)`<br>
 Generates a CBOR object from a byte array.
* `static CBORObject FromObject​(char value)`<br>
 Generates a CBOR string object from a Unicode character.
* `static CBORObject FromObject​(double value)`<br>
 Generates a CBOR object from a 64-bit floating-point number.
* `static CBORObject FromObject​(float value)`<br>
 Generates a CBOR object from a 32-bit floating-point number.
* `static CBORObject FromObject​(int value)`<br>
 Generates a CBOR object from a 32-bit signed integer.
* `static CBORObject FromObject​(int[] array)`<br>
 Generates a CBOR object from an array of 32-bit integers.
* `static CBORObject FromObject​(long value)`<br>
 Generates a CBOR object from a 64-bit signed integer.
* `static CBORObject FromObject​(long[] array)`<br>
 Generates a CBOR object from an array of 64-bit integers.
* `static CBORObject FromObject​(short value)`<br>
 Generates a CBOR object from a 16-bit signed integer.
* `static CBORObject FromObject​(CBORObject value)`<br>
 Generates a CBOR object from a CBOR object.
* `static CBORObject FromObject​(CBORObject[] array)`<br>
 Generates a CBOR object from an array of CBOR objects.
* `static CBORObject FromObject​(com.upokecenter.numbers.EDecimal otherValue)`<br>
 Generates a CBOR object from a decimal number.
* `static CBORObject FromObject​(com.upokecenter.numbers.EFloat bigValue)`<br>
 Generates a CBOR object from an arbitrary-precision binary floating-point
 number.
* `static CBORObject FromObject​(com.upokecenter.numbers.EInteger bigintValue)`<br>
 Generates a CBOR object from an arbitrary-precision integer.
* `static CBORObject FromObject​(com.upokecenter.numbers.ERational bigValue)`<br>
 Generates a CBOR object from a rational number.
* `static CBORObject FromObject​(Object obj)`<br>
 Generates a CBORObject from an arbitrary object.
* `static CBORObject FromObject​(Object obj,
          CBORTypeMapper mapper)`<br>
 Generates a CBORObject from an arbitrary object, using the given options
 to control how certain objects are converted to CBOR objects.
* `static CBORObject FromObject​(Object obj,
          CBORTypeMapper mapper,
          PODOptions options)`<br>
 Generates a CBORObject from an arbitrary object, using the given options
 to control how certain objects are converted to CBOR objects.
* `static CBORObject FromObject​(Object obj,
          PODOptions options)`<br>
 Generates a CBORObject from an arbitrary object.
* `static CBORObject FromObject​(String strValue)`<br>
 Generates a CBOR object from a text string.
* `static CBORObject FromObjectAndTag​(Object valueObValue,
                int smallTag)`<br>
 Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag.
* `static CBORObject FromObjectAndTag​(Object valueOb,
                com.upokecenter.numbers.EInteger bigintTag)`<br>
 Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag.
* `static CBORObject FromSimpleValue​(int simpleValue)`<br>
 Creates a CBOR object from a simple value number.
* `CBORObject get​(int index)`<br>
 Gets the value of a CBOR object by integer index in this array.
* `CBORObject get​(CBORObject key)`<br>
 Gets the value of a CBOR object in this map, using a CBOR object as the key.
* `CBORObject get​(String key)`<br>
 Gets the value of a CBOR object in this map, using a string as the key.
* `com.upokecenter.numbers.EInteger[] GetAllTags()`<br>
 Gets a list of all tags, from outermost to innermost.
* `byte[] GetByteString()`<br>
 Gets the byte array used in this object, if this object is a byte string,
 without copying the data to a new one.
* `Collection<CBORObject> getKeys()`<br>
 Gets a collection of the keys of this CBOR object in an undefined order.
* `com.upokecenter.numbers.EInteger getMostInnerTag()`<br>
 Gets the last defined tag for this CBOR data item, or -1 if the item is
 untagged.
* `com.upokecenter.numbers.EInteger getMostOuterTag()`<br>
 Gets the outermost tag for this CBOR data item, or -1 if the item is
 untagged.
* `int getSimpleValue()`<br>
 Gets the simple value ID of this object, or -1 if this object is not a
 simple value (including if the value is a floating-point number).
* `CBORType getType()`<br>
 Gets the general data type of this CBOR object.
* `Collection<CBORObject> getValues()`<br>
 Gets a collection of the values of this CBOR object, if it's a map or an
 array.
* `int hashCode()`<br>
 Calculates the hash code of this object.
* `boolean HasMostOuterTag​(int tagValue)`<br>
 Returns whether this object has a tag of the given number.
* `boolean HasMostOuterTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
 Returns whether this object has a tag of the given number.
* `boolean HasTag​(int tagValue)`<br>
 Returns whether this object has a tag of the given number.
* `boolean HasTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
 Returns whether this object has a tag of the given number.
* `CBORObject Insert​(int index,
      Object valueOb)`<br>
 Inserts an object at the specified position in this CBOR array.
* `boolean isFalse()`<br>
 Gets a value indicating whether this value is a CBOR false value.
* `boolean isFinite()`<br>
 Gets a value indicating whether this CBOR object represents a finite number.
* `boolean IsInfinity()`<br>
 Gets a value indicating whether this CBOR object represents infinity.
* `boolean isIntegral()`<br>
 Gets a value indicating whether this object represents an integral number,
 that is, a number without a fractional part.
* `boolean IsNaN()`<br>
 Gets a value indicating whether this CBOR object represents a not-a-number
 value (as opposed to whether this object's type is not a number
 type).
* `boolean isNegative()`<br>
 Gets a value indicating whether this object is a negative number.
* `boolean IsNegativeInfinity()`<br>
 Gets a value indicating whether this CBOR object represents negative
 infinity.
* `boolean isNull()`<br>
 Gets a value indicating whether this value is a CBOR null value.
* `boolean IsPositiveInfinity()`<br>
 Gets a value indicating whether this CBOR object represents positive
 infinity.
* `boolean isTagged()`<br>
 Gets a value indicating whether this data item has at least one tag.
* `boolean isTrue()`<br>
 Gets a value indicating whether this value is a CBOR true value.
* `boolean isUndefined()`<br>
 Gets a value indicating whether this value is a CBOR undefined value.
* `boolean isZero()`<br>
 Gets a value indicating whether this object's value equals 0.
* `static CBORObject Multiply​(CBORObject first,
        CBORObject second)`<br>
 Multiplies two CBOR numbers.
* `CBORObject Negate()`<br>
 Gets this object's value with the sign reversed.
* `static CBORObject NewArray()`<br>
 Creates a new empty CBOR array.
* `static CBORObject NewMap()`<br>
 Creates a new empty CBOR map.
* `static CBORObject Read​(InputStream stream) CBOREncodeOptions`<br>
 At the moment, use the overload of this method that takes a CBOREncodeOptions object.
* `static CBORObject Read​(InputStream stream,
    CBOREncodeOptions options)`<br>
 Reads an object in CBOR format from a data stream, using the specified
 options to control the decoding process.
* `static CBORObject ReadJSON​(InputStream stream)`<br>
 Generates a CBOR object from a data stream in JavaScript Object Notation
 (JSON) format.
* `static CBORObject ReadJSON​(InputStream stream,
        CBOREncodeOptions options)`<br>
 Generates a CBOR object from a data stream in JavaScript Object Notation
 (JSON) format, using the specified options to control the decoding
 process.
* `static CBORObject Remainder​(CBORObject first,
         CBORObject second)`<br>
 Finds the remainder that results when a CBORObject object is divided by the
 value of a CBORObject object.
* `boolean Remove​(CBORObject obj)`<br>
 If this object is an array, removes the first instance of the specified item
 from the array.
* `boolean Remove​(Object obj)`<br>
 If this object is an array, removes the first instance of the specified item
 (once converted to a CBOR object) from the array.
* `boolean RemoveAt​(int index)`<br>
 Removes the item at the given index of this CBOR array.
* `void set​(int index,
   CBORObject value)`<br>
 Gets the value of a CBOR object by integer index in this array.
* `void set​(CBORObject key,
   CBORObject value)`<br>
 Gets the value of a CBOR object in this map, using a CBOR object as the key.
* `void set​(String key,
   CBORObject value)`<br>
 Gets the value of a CBOR object in this map, using a string as the key.
* `CBORObject Set​(Object key,
   Object valueOb)`<br>
 Maps an object to a key in this CBOR map, or adds the value if the key
 doesn't exist.
* `int signum()`<br>
 Gets this value's sign: -1 if negative; 1 if positive; 0 if zero.
* `int size()`<br>
 Gets the number of keys in this map, or the number of items in this array,
 or 0 if this item is neither an array nor a map.
* `static CBORObject Subtract​(CBORObject first,
        CBORObject second)`<br>
 Finds the difference between two CBOR number objects.
* `String ToJSONString()`<br>
 Converts this object to a string in JavaScript Object Notation (JSON)
 format, using the specified options to control the encoding process.
* `String ToJSONString​(JSONOptions options)`<br>
 Converts this object to a string in JavaScript Object Notation (JSON)
 format, using the specified options to control the encoding process.
* `Object ToObject​(Type t)`<br>
 Converts this CBOR object to an object of an arbitrary type.
* `String toString()`<br>
 Returns this CBOR object in string form.
* `CBORObject Untag()`<br>
 Gets an object with the same value as this one but without the tags it has,
 if any.
* `CBORObject UntagOne()`<br>
 Gets an object with the same value as this one but without this object's
 outermost tag, if any.
* `static void Write​(boolean value,
     OutputStream stream)`<br>
 Writes a Boolean value in CBOR format to a data stream.
* `static void Write​(byte value,
     OutputStream stream)`<br>
 Writes a byte (0 to 255) in CBOR format to a data stream.
* `static void Write​(char value,
     OutputStream stream)`<br>
 Writes a Unicode character as a string in CBOR format to a data stream.
* `static void Write​(double value,
     OutputStream stream)`<br>
 Writes a 64-bit floating-point number in CBOR format to a data stream.
* `static void Write​(float value,
     OutputStream s)`<br>
 Writes a 32-bit floating-point number in CBOR format to a data stream.
* `static void Write​(int value,
     OutputStream stream)`<br>
 Writes a 32-bit signed integer in CBOR format to a data stream.
* `static void Write​(long value,
     OutputStream stream)`<br>
 Writes a 64-bit signed integer in CBOR format to a data stream.
* `static void Write​(short value,
     OutputStream stream)`<br>
 Writes a 16-bit signed integer in CBOR format to a data stream.
* `static void Write​(CBORObject value,
     OutputStream stream)`<br>
 Writes a CBOR object to a CBOR data stream.
* `static void Write​(com.upokecenter.numbers.EDecimal bignum,
     OutputStream stream) double double`<br>
 Writes a decimal floating-point number in CBOR format to a data stream, as
 follows:  If the value is null, writes the byte 0xF6.
 If the value is negative zero, infinity, or NaN, converts the
 number to a double and writes that double.
* `static void Write​(com.upokecenter.numbers.EFloat bignum,
     OutputStream stream) double double`<br>
 Writes a binary floating-point number in CBOR format to a data stream as
 follows:  If the value is null, writes the byte 0xF6.
 If the value is negative zero, infinity, or NaN, converts the
 number to a double and writes that double.
* `static void Write​(com.upokecenter.numbers.EInteger bigint,
     OutputStream stream)`<br>
 Writes a big integer in CBOR format to a data stream.
* `static void Write​(com.upokecenter.numbers.ERational rational,
     OutputStream stream)`<br>
 Writes a rational number in CBOR format to a data stream.
* `static void Write​(Object objValue,
     OutputStream stream) CBOREncodeOptions`<br>
 At the moment, use the overload of this method that takes a CBOREncodeOptions object.
* `static void Write​(Object objValue,
     OutputStream output,
     CBOREncodeOptions options)`<br>
 Writes an arbitrary object to a CBOR data stream, using the specified
 options for controlling how the object is encoded to CBOR data
 format.
* `static void Write​(String str,
     OutputStream stream) CBOREncodeOptions`<br>
 At the moment, use the overload of this method that takes a CBOREncodeOptions object.
* `static void Write​(String str,
     OutputStream stream,
     CBOREncodeOptions options)`<br>
 Writes a string in CBOR format to a data stream, using the given options to
 control the encoding process.
* `static void WriteJSON​(Object obj,
         OutputStream outputStream)`<br>
 Converts an arbitrary object to a string in JavaScript Object Notation
 (JSON) format, as in the ToJSONString method, and writes that string
 to a data stream in UTF-8.
* `void WriteJSONTo​(OutputStream outputStream)`<br>
 Converts this object to a string in JavaScript Object Notation (JSON)
 format, as in the ToJSONString method, and writes that string to a
 data stream in UTF-8.
* `void WriteJSONTo​(OutputStream outputStream,
           JSONOptions options)`<br>
 Converts this object to a string in JavaScript Object Notation (JSON)
 format, as in the ToJSONString method, and writes that string to a
 data stream in UTF-8, using the given JSON options to control the
 encoding process.
* `void WriteTo​(OutputStream stream) CBOREncodeOptions`<br>
 At the moment, use the overload of this method that takes a CBOREncodeOptions object.
* `void WriteTo​(OutputStream stream,
       CBOREncodeOptions options)`<br>
 Writes this CBOR object to a data stream, using the specified options for
 encoding the data to CBOR format.
* `static int WriteValue​(OutputStream outputStream,
          int majorType,
          int value)`<br>
 Writes a CBOR major type number and an integer 0 or greater associated with
 it to a data stream, where that integer is passed to this method as a
 32-bit signed integer.
* `static int WriteValue​(OutputStream outputStream,
          int majorType,
          long value)`<br>
 Writes a CBOR major type number and an integer 0 or greater associated with
 it to a data stream, where that integer is passed to this method as a
 64-bit signed integer.
* `static int WriteValue​(OutputStream outputStream,
          int majorType,
          com.upokecenter.numbers.EInteger bigintValue)`<br>
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
Gets a value indicating whether this value is a CBOR false value.

**Returns:**

* <code>true</code> If this value is a CBOR false value; otherwise, .
 <code>false</code> .

### isFinite
    public final boolean isFinite()
Gets a value indicating whether this CBOR object represents a finite number.

**Returns:**

* <code>true</code> If this CBOR object represents a finite number;
 otherwise, . <code>false</code>.

### isIntegral
    public final boolean isIntegral()
Gets a value indicating whether this object represents an integral number,
 that is, a number without a fractional part. Infinity and
 not-a-number are not considered integral.

**Returns:**

* <code>true</code> If this object represents an integral number, that is,
 a number without a fractional part; otherwise, . <code>false</code>.

### isNull
    public final boolean isNull()
Gets a value indicating whether this value is a CBOR null value.

**Returns:**

* <code>true</code> If this value is a CBOR null value; otherwise, . <code>
 false</code>.

### isTagged
    public final boolean isTagged()
Gets a value indicating whether this data item has at least one tag.

**Returns:**

* <code>true</code> If this data item has at least one tag; otherwise, .
 <code>false</code>.

### isTrue
    public final boolean isTrue()
Gets a value indicating whether this value is a CBOR true value.

**Returns:**

* <code>true</code> If this value is a CBOR true value; otherwise, . <code>
 false</code>.

### isUndefined
    public final boolean isUndefined()
Gets a value indicating whether this value is a CBOR undefined value.

**Returns:**

* <code>true</code> If this value is a CBOR undefined value; otherwise, .
 <code>false</code>.

### isZero
    public final boolean isZero()
Gets a value indicating whether this object's value equals 0.

**Returns:**

* <code>true</code> If this object's value equals 0; otherwise, . <code>
 false</code> .

### getKeys
    public final Collection<CBORObject> getKeys()
Gets a collection of the keys of this CBOR object in an undefined order.

**Returns:**

* A collection of the keys of this CBOR object.

**Throws:**

* <code>IllegalStateException</code> - This object is not a map.

### isNegative
    public final boolean isNegative()
Gets a value indicating whether this object is a negative number.

**Returns:**

* <code>true</code> If this object is a negative number; otherwise, .
 <code>false</code> .

### getMostOuterTag
    public final com.upokecenter.numbers.EInteger getMostOuterTag()
Gets the outermost tag for this CBOR data item, or -1 if the item is
 untagged.

**Returns:**

* The outermost tag for this CBOR data item, or -1 if the item is
 untagged.

### signum
    public final int signum()
Gets this value's sign: -1 if negative; 1 if positive; 0 if zero.

**Returns:**

* This value's sign: -1 if negative; 1 if positive; 0 if zero.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type,
 including the special not-a-number value (NaN).

### getSimpleValue
    public final int getSimpleValue()
Gets the simple value ID of this object, or -1 if this object is not a
 simple value (including if the value is a floating-point number).

**Returns:**

* The simple value ID of this object, or -1 if this object is not a
 simple value (including if the value is a floating-point number).

### getType
    public final CBORType getType()
Gets the general data type of this CBOR object.

**Returns:**

* The general data type of this CBOR object.

### getValues
    public final Collection<CBORObject> getValues()
Gets a collection of the values of this CBOR object, if it's a map or an
 array. If this object is a map, returns one value for each key in the
 map in an undefined order. If this is an array, returns all the
 values of the array in the order they are listed. (This method can't
 be used to get the bytes in a CBOR byte string; for that, use the
 GetByteArray method instead.).

**Returns:**

* A collection of the values of this CBOR map or array.

**Throws:**

* <code>IllegalStateException</code> - This object is not a map or an array.

### get
    public CBORObject get​(int index)
Gets the value of a CBOR object by integer index in this array.

**Parameters:**

* <code>index</code> - Zero-based index of the element.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>IllegalStateException</code> - This object is not an array.

* <code>NullPointerException</code> - The parameter "value" is null (as
 opposed to CBORObject.Null).

### set
    public void set​(int index, CBORObject value)
Gets the value of a CBOR object by integer index in this array.

**Parameters:**

* <code>index</code> - Zero-based index of the element.

**Throws:**

* <code>IllegalStateException</code> - This object is not an array.

* <code>NullPointerException</code> - The parameter "value" is null (as
 opposed to CBORObject.Null).

### get
    public CBORObject get​(CBORObject key)
Gets the value of a CBOR object in this map, using a CBOR object as the key.

**Parameters:**

* <code>key</code> - The parameter <code>key</code> is a CBOR object.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>NullPointerException</code> - The key is null (as opposed to
 CBORObject.Null); or the set method is called and the value is null.

* <code>IllegalStateException</code> - This object is not a map.

### set
    public void set​(CBORObject key, CBORObject value)
Gets the value of a CBOR object in this map, using a CBOR object as the key.

**Parameters:**

* <code>key</code> - The parameter <code>key</code> is a CBOR object.

**Throws:**

* <code>NullPointerException</code> - The key is null (as opposed to
 CBORObject.Null); or the set method is called and the value is null.

* <code>IllegalStateException</code> - This object is not a map.

### get
    public CBORObject get​(String key)
Gets the value of a CBOR object in this map, using a string as the key.

**Parameters:**

* <code>key</code> - A key that points to the desired value.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>NullPointerException</code> - The key is null.

* <code>IllegalStateException</code> - This object is not a map.

### set
    public void set​(String key, CBORObject value)
Gets the value of a CBOR object in this map, using a string as the key.

**Parameters:**

* <code>key</code> - A key that points to the desired value.

**Throws:**

* <code>NullPointerException</code> - The key is null.

* <code>IllegalStateException</code> - This object is not a map.

### Addition
    public static CBORObject Addition​(CBORObject first, CBORObject second)
Finds the sum of two CBOR numbers.

**Parameters:**

* <code>first</code> - The parameter <code>first</code> is a CBOR object.

* <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>IllegalArgumentException</code> - Either or both operands are not numbers (as
 opposed to Not-a-Number, NaN).

### DecodeFromBytes
    public static CBORObject DecodeFromBytes​(byte[] data)
<p><b>At the moment, use the overload of this method that takes a <code>CBOREncodeOptions</code> object. The object
 <code>CBOREncodeOptions.Default</code> contains recommended settings for
 CBOREncodeOptions, and those settings may be adopted by this overload
 (without a CBOREncodeOptions argument) in the next major
 version.</b></p> <p>Generates a CBOR object from an array of
 CBOR-encoded bytes.</p>

**Parameters:**

* <code>data</code> - A byte array in which a single CBOR object is encoded.

**Returns:**

* A CBOR object decoded from the given byte array.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where not all of the byte array
 represents a CBOR object. This exception is also thrown if the
 parameter <code>data</code> is empty.

* <code>NullPointerException</code> - The parameter <code>data</code> is null.

### DecodeFromBytes
    public static CBORObject DecodeFromBytes​(byte[] data, CBOREncodeOptions options)
Generates a CBOR object from an array of CBOR-encoded bytes, using the given
 <code>CBOREncodeOptions</code> object to control the decoding
 process.<p><p>The following example (originally written in C# for the
 .NET version) implements a method that decodes a text string from a
 CBOR byte array. It's successful only if the CBOR object contains an
 untagged text string. </p> <pre>private static String
 DecodeTextString&#x28;byte[] bytes)&#x7b; if&#x28;bytes ==
 null)&#x7b; throw new
 NullPointerException&#x28;nameof(mapObj));&#x7d;
 if&#x28;bytes.length == 0 || bytes[0]&lt;0x60 ||
 bytes[0]&gt;0x7f)&#x7b;throw new CBORException&#x28;);&#x7d; return
 CBORObject.DecodeFromBytes&#x28;bytes,
 CBOREncodeOptions.Default).getAsString()&#x28;); &#x7d; </pre> </p>

**Parameters:**

* <code>data</code> - A byte array in which a single CBOR object is encoded.

* <code>options</code> - The parameter <code>options</code> is a CBOREncodeOptions object.

**Returns:**

* A CBOR object decoded from the given byte array.

**Throws:**

* <code>CBORException</code> - There was an error in reading or
 parsing the data. This includes cases where not all of the byte array
 represents a CBOR object. This exception is also thrown if the
 parameter <code>data</code> is empty.

* <code>NullPointerException</code> - The parameter <code>data</code> is null.

### Divide
    public static CBORObject Divide​(CBORObject first, CBORObject second)
Divides a CBORObject object by the value of a CBORObject object.

**Parameters:**

* <code>first</code> - The parameter <code>first</code> is a CBOR object.

* <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

* The quotient of the two objects.

### FromJSONString
    public static CBORObject FromJSONString​(String str)
<p>Generates a CBOR object from a text string in JavaScript Object Notation
 (JSON) format.</p> <p>If a JSON object has duplicate keys, a
 CBORException is thrown. This is a change in version 4.0.</p>

**Parameters:**

* <code>str</code> - A string in JSON format. The entire string must contain a single
 JSON object and not multiple objects. The string may not begin with a
 byte-order mark (U + FEFF).

**Returns:**

* A CBOR object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>CBORException</code> - The string is not in JSON format.

### FromJSONString
    public static CBORObject FromJSONString​(String str, CBOREncodeOptions options)
Generates a CBOR object from a text string in JavaScript Object Notation
 (JSON) format, using the specified options to control the decoding
 process.

**Parameters:**

* <code>str</code> - A string in JSON format. The entire string must contain a single
 JSON object and not multiple objects. The string may not begin with a
 byte-order mark (U + FEFF).

* <code>options</code> - Specifies options to control the decoding process.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> or <code>
 options</code> is null.

* <code>CBORException</code> - The string is not in JSON format.

### ToObject
    public Object ToObject​(Type t)
<p>Converts this CBOR object to an object of an arbitrary type. The
 following types are specially handled by this method: </p> <ul>
 <li>If the type is <code>CBORObject</code> , return this object. </li>
 <li>If the given object is <code>CBORObject.Null</code> (with or without
 tags), returns <code>null</code> . </li> <li>If the type is <code>object</code> ,
 return this object. </li> <li>If the type is <code>char</code> ... (To be
 implemented). </li> <li>If the type is <code>bool</code> (<code>boolean</code>
 in Java), returns the result of AsBoolean. </li> <li>If the type is a
 primitive integer type (<code>byte</code> , <code>int</code> , <code>short</code> ,
 <code>long</code> , as well as <code>sbyte</code> , <code>ushort</code> , <code>uint</code> ,
 and <code>ulong</code> in .NET) or a primitive floating-point type (
 <code>float</code> , <code>double</code> , as well as <code>decimal</code> in .NET),
 returns the result of the corresponding As* method. </li> <li>If the
 type is <code>String</code> , returns the result of AsString. </li> <li>If
 the type is <code>EDecimal</code> , <code>EFloat</code> , <code>EInteger</code> , or
 <code>ERational</code> in the
 <code>PeterO.Numbers</code>  library (in .NET) or the
 <code>com.github.peteroupc/numbers</code>  artifact (in Java), returns
 the result of the corresponding As* method. </li> <li>If the type is
 <code>byte[]</code> and this CBOR object is a byte string, returns a byte
 array which this CBOR byte string's data will be copied to. </li>
 <li>If the type is a one-dimensional array type and this CBOR object
 is an array, returns an array containing the items in this CBOR
 object. (Multidimensional arrays to be documented.) </li> <li>If the
 type is the generic List, IList, ICollection, or IEnumerable (or
 ArrayList, List, Collection, or Iterable in Java), and if this CBOR
 object is an array, returns an object conforming to the type, class,
 or interface passed to this method, where the object will contain all
 items in this CBOR array. </li> <li>If the type is the generic
 Dictionary or IDictionary (or HashMap or Map in Java), and if this
 CBOR object is a map, returns an object conforming to the type,
 class, or interface passed to this method, where the object will
 contain all keys and values in this CBOR map. </li> <li>Enums (To be
 implemented). </li> <li>Type converters (To be implemented). </li>
 <li>If the type is <code>java.util.Date</code> (or <code>Date</code> in Java) , returns
 a date/time object if the CBOR object's outermost tag is 0 or 1.
 </li> <li>If the type is <code>java.net.URI</code> (or <code>URI</code> in Java), returns
 a URI object if possible. </li> <li>If the type is <code>java.util.UUID</code> (or
 <code>UUID</code> in Java), returns a UUID object if possible. </li> </ul>
 <p>In the .NET version, if the object is a CBOR map, and if the type
 is not specially handled above and has a zero-argument public
 constructor (default or otherwise), an object of the given type is
 created, then this method checks the CBOR object for public,
 nonstatic writable properties. For each property found, if its name
 (with the "Is" prefix deleted and then converted to camel case)
 matches the name of a key in this CBOR map, and if that property has
 a getter, that property's setter is invoked and the corresponding
 value is passed to that method. </p> <p>In the Java version, if the
 object is a CBOR map, and if the type is not specially handled above
 and has a zero-argument public constructor(default or otherwise), an
 object of the given type is created, then this method checks the CBOR
 object for public, nonstatic methods starting with the word "set"
 (followed by an upper-case A to Z) that take a single parameter. For
 each method found, if its name (with the starting word "set" deleted
 and then converted to camel case) matches the name of a key in this
 CBOR map, and if it has a corresponding getter method that takes no
 parameters, that method is invoked and the corresponding value is
 passed to that method. </p> <p>REMARK: The behavior of this method is
 likely to change in the final version 3.4 of this library as well as
 in the next major version (4.0). There are certain inconsistencies
 between the ToObject method and the FromObject method as well as
 between the .NET and Java versions of FromObject. For one thing,
 java.util.Date/Date objects in FromObject are converted differently between
 the two versions -- either as CBOR maps with their "get" properties
 (Java) or as tag-0 strings (.NET) -- this difference has to remain
 for backward compatibility with version 3.0. For another thing, the
 treatment of properties/getters starting with "Is" is subtly
 inconsistent between the .NET and Java versions of FromObject,
 especially when using certain PODOptions. A certain consistency
 between .NET and Java and between FromObject and ToObject are sought
 for version 4.0. It is also hoped that-- </p> <ul> <li>the ToObject
 method will support deserializing to objects consisting of fields and
 not getters ("getX()" methods), both in .NET and in Java, and </li>
 <li>both FromObject and ToObject will be better designed, in version
 4.0, so that backward-compatible improvements are easier to make.
 </li> </ul><p><p>Java offers no easy way to express a generic type,
 at least none as easy as C#'s <code>typeof</code> operator. The following
 example, written in Java, is a way to specify that the return value
 will be an ArrayList of String objects. </p> <pre>Type
 arrayListString = new ParameterizedType() { public Type[]
 getActualTypeArguments() { /* Contains one type parameter, String &#x2a;&#x2f;
 return new Type[] { String.class }; } public Type getRawType() { /* Raw
 type is ArrayList &#x2a;&#x2f; return ArrayList.class; } public Type
 getOwnerType() { return null; } }; ArrayList&lt;String&gt; array =
 (ArrayList&lt;String&gt;) cborArray.ToObject(arrayListString);
 </pre> <p>By comparison, the C# version is much shorter. </p>
 <pre>var&#x20;array = (List&lt;String&gt;)cborArray.ToObject(
 typeof&#x28;List&lt;String&gt;)); </pre> </p>

**Parameters:**

* <code>t</code> - The type, class, or interface that this method's return value will
 belong to. To express a generic type in Java, see the example.
 <b>Note:</b> For security purposes, an application should not base
 this parameter on user input or other externally supplied data.
 Whenever possible, this parameter should be either a type specially
 handled by this method (such as <code>int</code> or <code>String</code>) or a
 plain-old-data type (POCO or POJO type) within the control of the
 application. If the plain-old-data type references other data types,
 those types should likewise meet either criterion above.

**Returns:**

* The converted object.

**Throws:**

* <code>UnsupportedOperationException</code> - The given type <code>t</code> , or this
 object's CBOR type, is not supported.

* <code>NullPointerException</code> - The parameter <code>t</code> is null.

### FromObject
    public static CBORObject FromObject​(long value)
Generates a CBOR object from a 64-bit signed integer.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 64-bit signed integer.

**Returns:**

* A CBORObject object.

### FromObject
    public static CBORObject FromObject​(CBORObject value)
Generates a CBOR object from a CBOR object.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a CBOR object.

**Returns:**

* Same as.

### FromObject
    public static CBORObject FromObject​(com.upokecenter.numbers.EInteger bigintValue)
Generates a CBOR object from an arbitrary-precision integer.

**Parameters:**

* <code>bigintValue</code> - An arbitrary-precision value.

**Returns:**

* A CBOR number.

### FromObject
    public static CBORObject FromObject​(com.upokecenter.numbers.EFloat bigValue)
Generates a CBOR object from an arbitrary-precision binary floating-point
 number.

**Parameters:**

* <code>bigValue</code> - An arbitrary-precision binary floating-point number.

**Returns:**

* A CBOR number.

### FromObject
    public static CBORObject FromObject​(com.upokecenter.numbers.ERational bigValue)
Generates a CBOR object from a rational number.

**Parameters:**

* <code>bigValue</code> - A rational number.

**Returns:**

* A CBOR number.

### FromObject
    public static CBORObject FromObject​(com.upokecenter.numbers.EDecimal otherValue)
Generates a CBOR object from a decimal number.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision decimal number.

**Returns:**

* A CBOR number.

### FromObject
    public static CBORObject FromObject​(String strValue)
Generates a CBOR object from a text string.

**Parameters:**

* <code>strValue</code> - A string value. Can be null.

**Returns:**

* A CBOR object representing the string, or CBORObject.Null if
 stringValue is null.

**Throws:**

* <code>IllegalArgumentException</code> - The string contains an unpaired surrogate
 code point.

### FromObject
    public static CBORObject FromObject​(int value)
Generates a CBOR object from a 32-bit signed integer.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 32-bit signed integer.

**Returns:**

* A CBORObject object.

### FromObject
    public static CBORObject FromObject​(short value)
Generates a CBOR object from a 16-bit signed integer.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 16-bit signed integer.

**Returns:**

* A CBORObject object.

### FromObject
    public static CBORObject FromObject​(char value)
Generates a CBOR string object from a Unicode character.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a char object.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>value</code> is a surrogate
 code point.

### FromObject
    public static CBORObject FromObject​(boolean value)
Returns the CBOR true value or false value, depending on "value".

**Parameters:**

* <code>value</code> - Either True or False.

**Returns:**

* CBORObject.True if value is true; otherwise CBORObject.False.

### FromObject
    public static CBORObject FromObject​(byte value)
Generates a CBOR object from a byte (0 to 255).

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a byte (from 0 to 255).

**Returns:**

* A CBORObject object.

### FromObject
    public static CBORObject FromObject​(float value)
Generates a CBOR object from a 32-bit floating-point number.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 32-bit floating-point number.

**Returns:**

* A CBORObject object.

### FromObject
    public static CBORObject FromObject​(double value)
Generates a CBOR object from a 64-bit floating-point number.

**Parameters:**

* <code>value</code> - The parameter <code>value</code> is a 64-bit floating-point number.

**Returns:**

* A CBORObject object.

### FromObject
    public static CBORObject FromObject​(byte[] bytes)
<p>Generates a CBOR object from a byte array. The byte array is copied to a
 new byte array. (This method can't be used to decode CBOR data from a
 byte array; for that, use the DecodeFromBytes method
 instead.).</p><p><p>The following example encodes a text string to a
 UTF-8 byte array, then uses the array to create a CBOR byte string
 object. It is not recommended to use <code>Encoding.UTF8.GetBytes</code> in
 .NET, or the <code>getBytes()</code> method in Java to do this. For
 instance, <code>Encoding.UTF8</code> begins the encoded string with a
 byte-order mark, and <code>getBytes()</code> encodes text strings in an
 unspecified character encoding. Both behaviors can be undesirable.
 Instead, use the <code>DataUtilities.GetUtf8Bytes</code> method to convert
 text strings to UTF-8.</p> <pre>/* true does character replacement
 of invalid UTF-8; false throws an exception on invalid UTF-8 &#x2a;&#x2f;
 byte[] bytes = DataUtilities.GetUtf8Bytes(textString, true);
 CBORObject cbor = CBORObject.FromBytes(bytes); </pre> </p>

**Parameters:**

* <code>bytes</code> - A byte array. Can be null.

**Returns:**

* A CBOR byte string object where each byte of the given byte array is
 copied to a new array, or CBORObject.Null if the value is null.

### FromObject
    public static CBORObject FromObject​(CBORObject[] array)
Generates a CBOR object from an array of CBOR objects.

**Parameters:**

* <code>array</code> - An array of CBOR objects.

**Returns:**

* A CBOR object where each element of the given array is copied to a
 new array, or CBORObject.Null if the value is null.

### FromObject
    public static CBORObject FromObject​(int[] array)
Generates a CBOR object from an array of 32-bit integers.

**Parameters:**

* <code>array</code> - An array of 32-bit integers.

**Returns:**

* A CBOR array object where each element of the given array is copied
 to a new array, or CBORObject.Null if the value is null.

### FromObject
    public static CBORObject FromObject​(long[] array)
Generates a CBOR object from an array of 64-bit integers.

**Parameters:**

* <code>array</code> - An array of 64-bit integers.

**Returns:**

* A CBOR array object where each element of the given array is copied
 to a new array, or CBORObject.Null if the value is null.

### FromObject
    public static CBORObject FromObject​(Object obj)
Generates a CBORObject from an arbitrary object. See the overload of this
 method that takes CBORTypeMapper and PODOptions arguments.

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is an arbitrary object.

**Returns:**

* A CBOR object corresponding to the given object. Returns
 CBORObject.Null if the object is null.

### FromObject
    public static CBORObject FromObject​(Object obj, PODOptions options)
Generates a CBORObject from an arbitrary object. See the overload of this
 method that takes CBORTypeMapper and PODOptions arguments.

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is an arbitrary object.

* <code>options</code> - An object containing options to control how certain objects
 are converted to CBOR objects.

**Returns:**

* A CBOR object corresponding to the given object. Returns
 CBORObject.Null if the object is null.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>options</code> is null.

### FromObject
    public static CBORObject FromObject​(Object obj, CBORTypeMapper mapper)
<p>Generates a CBORObject from an arbitrary object, using the given options
 to control how certain objects are converted to CBOR objects. The
 following types are specially handled by this method:</p> <ul>
 <li><code>null</code> is converted to <code>CBORObject.Null</code>.</li> <li>A
 <code>char</code> is TBD.</li> <li>A <code>boolean</code> (<code>boolean</code> in
 Java) is converted to <code>CBORObject.True</code> or
 <code>CBORObject.False</code>.</li> <li>A <code>byte</code> is converted to a
 CBOR integer from 0 through 255.</li> <li>A primitive integer type (
 <code>int</code>, <code>short</code>, <code>long</code>, as well as <code>sbyte</code>,
 <code>ushort</code>, <code>uint</code> , and <code>ulong</code> in .NET) is converted
 to the corresponding CBOR integer.</li> <li>A primitive
 floating-point type (<code>float</code>, <code>double</code>, as well as
 <code>decimal</code> in .NET) is converted to the corresponding CBOR
 number.</li> <li>A <code>string</code> is converted to a CBOR text string.
 To create a CBOR byte string object from <code>string</code>, see the
 example given in <see cref='M:PeterO.Cbor.CBORObject.FromObject(System.Byte[])'/> .</li>
 <li>A number of type <code>EDecimal</code>, <code>EFloat</code>, <code>EInteger</code>,
 and <code>ERational</code> in the <code>PeterO.Numbers</code>
 library (in .NET) or the <code>com.github.peteroupc/numbers</code>
 artifact (in Java) is converted to the corresponding CBOR
 number.</li> <li>An array (other than a byte array) is converted to a
 CBOR array. In the .NET version, a multidimensional array is
 converted to an array of arrays. A byte array is converted to a CBOR
 byte string.</li> <li>An object implementing Map (Map in Java) is
 converted to a CBOR map containing the keys and values
 enumerated.</li> <li>An object implementing Iterable (Iterable in
 Java) is converted to a CBOR array containing the items
 enumerated.</li> <li>An enumeration (<code>Enum</code>) object is
 converted to its integer value in the .NET version, or the result of
 its "getName" method in the Java version. (TBD)</li> <li>If the
 object is of a type corresponding to a type converter mentioned in
 the <paramref name='mapper'/> parameter, that converter will be used
 to convert the object to a CBOR object.</li> <li>An object of type
 <code>java.util.Date</code>, <code>java.net.URI</code>, or <code>java.util.UUID</code> (<code>Date</code>,
 <code>URI</code>, or <code>UUID</code>, respectively, in Java) will be converted
 to a tagged CBOR object of the appropriate kind. (TBD: Decide whether
 a custom CBORTypeMapper can override these behaviors.)</li></ul>
 <p>In the .NET version, if the object is a type not specially handled
 above, returns a CBOR map with the values of each of its public,
 nonstatic properties (limited to read/write properties except in the
 case of a compiler-generated type). Properties are converted to their
 camel-case names (meaning if a name starts with A to Z, that letter
 is lower-cased). If the property name begins with the word "Is"
 followed by an upper-case A to Z, the "Is" prefix is deleted from the
 name. (Passing the appropriate "options" parameter can be done to
 control whether the "Is" prefix is removed and whether a camel-case
 conversion happens.) Also, .NET <code>Enum</code> objects will be converted
 to their integer values, and .</p> <p>In the Java version, if the
 object is a type not specially handled above, this method checks the
 CBOR object for public, nonstatic methods starting with the word
 "get" or "is" (either word followed by an upper-case A to Z) that
 take no parameters, and returns a CBOR map with one entry for each
 such method found. For each method found, the starting word "get" or
 "is" is deleted from its name, and the name is converted to camel
 case (meaning if a name starts with A to Z, that letter is
 lower-cased). (Passing the appropriate "options" parameter can be
 done to control whether the "is" prefix is removed and whether a
 camel-case conversion happens.) Also, Java <code>Enum</code> objects will
 be converted to the result of their <code>name</code> method.</p> <p>If the
 input is a byte array, the byte array is copied to a new byte array.
 (This method can't be used to decode CBOR data from a byte array; for
 that, use the DecodeFromBytes method instead.).</p> <p>REMARK: The
 behavior of this method is likely to change in the next major version
 (4.0). There are certain inconsistencies between the ToObject method
 and the FromObject method as well as between the .NET and Java
 versions of FromObject. For one thing, java.util.Date/Date objects in
 FromObject are converted differently between the two versions --
 either as CBOR maps with their "get" properties (Java) or as tag-0
 strings (.NET) -- this difference has to remain for backward
 compatibility with version 3.0. For another thing, the treatment of
 properties/getters starting with "Is" is subtly inconsistent between
 the .NET and Java versions of FromObject, especially when using
 certain PODOptions. A certain consistency between .NET and Java and
 between FromObject and ToObject are sought for version 4.0. It is
 also hoped that--</p> <ul> <li>the ToObject method will support
 deserializing to objects consisting of fields and not getters
 ("getX()" methods), both in .NET and in Java, and</li> <li>both
 FromObject and ToObject will be better designed, in version 4.0, so
 that backward-compatible improvements are easier to make.</li></ul>

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is an arbitrary object.

* <code>mapper</code> - The parameter <code>mapper</code> is not documented yet.

**Returns:**

* A CBOR object corresponding to the given object. Returns
 CBORObject.Null if the object is null.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>mapper</code> is null.

### FromObject
    public static CBORObject FromObject​(Object obj, CBORTypeMapper mapper, PODOptions options)
<p>Generates a CBORObject from an arbitrary object, using the given options
 to control how certain objects are converted to CBOR objects. The
 following types are specially handled by this method:</p><ul>
 <li><code>null</code> is converted to <code>CBORObject.Null</code>.</li> <li>A
 <code>char</code> is TBD.</li> <li>A <code>bool</code> (<code>boolean</code> in Java)
 is converted to <code>CBORObject.True</code> or
 <code>CBORObject.False</code>.</li> <li>A <code>byte</code> is converted to a
 CBOR integer from 0 through 255.</li> <li>A primitive integer type
 (<code>int</code>, <code>short</code>, <code>long</code>, as well as <code>sbyte</code>,
 <code>ushort</code>, <code>uint</code>, and <code>ulong</code> in .NET) is converted to
 the corresponding CBOR integer.</li> <li>A primitive floating-point
 type (<code>float</code>, <code>double</code>, as well as <code>decimal</code> in .NET)
 is converted to the corresponding CBOR number.</li> <li>A
 <code>String</code> is converted to a CBOR text string. To create a CBOR
 byte string object from <code>String</code>, see the example given in <see cref='M:PeterO.Cbor.CBORObject.FromObject(System.Byte[])'/> .</li>
 <li>A number of type <code>EDecimal</code>, <code>EFloat</code>, <code>EInteger</code>,
 and <code>ERational</code> in the <code>PeterO.Numbers</code>
 library (in .NET) or the <code>com.github.peteroupc/numbers</code>
 artifact (in Java) is converted to the corresponding CBOR
 number.</li> <li>An array (other than a byte array) is converted to a
 CBOR array. In the .NET version, a multidimensional array is
 converted to an array of arrays. A byte array is converted to a CBOR
 byte string.</li> <li>An object implementing IDictionary (Map in
 Java) is converted to a CBOR map containing the keys and values
 enumerated.</li> <li>An object implementing IEnumerable (Iterable in
 Java) is converted to a CBOR array containing the items
 enumerated.</li> <li>An enumeration (<code>Enum</code>) object is converted
 to its integer value in the .NET version, or the result of its
 "getName" method in the Java version. (TBD)</li> <li>If the object is
 of a type corresponding to a type converter mentioned in the
 <paramref name='mapper'/> parameter, that converter will be used to
 convert the object to a CBOR object.</li> <li>An object of type
 <code>java.util.Date</code>, <code>java.net.URI</code>, or <code>java.util.UUID</code> (<code>Date</code>, <code>URI</code>,
 or <code>UUID</code>, respectively, in Java) will be converted to a tagged
 CBOR object of the appropriate kind. (TBD: Decide whether a custom
 CBORTypeMapper can override these behaviors.)</li> </ul> <p>In the
 .NET version, if the object is a type not specially handled above,
 returns a CBOR map with the values of each of its public, nonstatic
 properties (limited to read/write properties except in the case of a
 compiler-generated type). Properties are converted to their
 camel-case names (meaning if a name starts with A to Z, that letter
 is lower-cased). If the property name begins with the word "Is"
 followed by an upper-case A to Z, the "Is" prefix is deleted from the
 name. (Passing the appropriate "options" parameter can be done to
 control whether the "Is" prefix is removed and whether a camel-case
 conversion happens.) Also, .NET <code>Enum</code> objects will be converted
 to their integer values, and .</p> <p>In the Java version, if the
 object is a type not specially handled above, this method checks the
 CBOR object for public, nonstatic methods starting with the word
 "get" or "is" (either word followed by an upper-case A to Z) that
 take no parameters, and returns a CBOR map with one entry for each
 such method found. For each method found, the starting word "get" or
 "is" is deleted from its name, and the name is converted to camel
 case (meaning if a name starts with A to Z, that letter is
 lower-cased). (Passing the appropriate "options" parameter can be
 done to control whether the "is" prefix is removed and whether a
 camel-case conversion happens.) Also, Java <code>Enum</code> objects will
 be converted to the result of their <code>name</code> method.</p> <p>If the
 input is a byte array, the byte array is copied to a new byte array.
 (This method can't be used to decode CBOR data from a byte array; for
 that, use the DecodeFromBytes method instead.).</p> <p>REMARK: The
 behavior of this method is likely to change in the next major version
 (4.0). There are certain inconsistencies between the ToObject method
 and the FromObject method as well as between the .NET and Java
 versions of FromObject. For one thing, java.util.Date/Date objects in
 FromObject are converted differently between the two versions --
 either as CBOR maps with their "get" properties (Java) or as tag-0
 strings (.NET) -- this difference has to remain for backward
 compatibility with version 3.0. For another thing, the treatment of
 properties/getters starting with "Is" is subtly inconsistent between
 the .NET and Java versions of FromObject, especially when using
 certain PODOptions. A certain consistency between .NET and Java and
 between FromObject and ToObject are sought for version 4.0. It is
 also hoped that--</p> <ul> <li>the ToObject method will support
 deserializing to objects consisting of fields and not getters
 ("getX()" methods), both in .NET and in Java, and</li> <li>both
 FromObject and ToObject will be better designed, in version 4.0, so
 that backward-compatible improvements are easier to make.</li></ul>

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is an arbitrary object.

* <code>mapper</code> - The parameter <code>mapper</code> is not documented yet.

* <code>options</code> - An object containing options to control how certain objects
 are converted to CBOR objects.

**Returns:**

* A CBOR object corresponding to the given object. Returns
 CBORObject.Null if the object is null.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>options</code> is null.

### FromObjectAndTag
    public static CBORObject FromObjectAndTag​(Object valueOb, com.upokecenter.numbers.EInteger bigintTag)
Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag.

**Parameters:**

* <code>valueOb</code> - An arbitrary object. If the tag number is 2 or 3, this must
 be a byte string whose bytes represent an integer in little-endian
 byte order, and the value of the number is 1 minus the integer's
 value for tag 3. If the tag number is 4 or 5, this must be an array
 with two elements: the first must be an integer representing the
 exponent, and the second must be an integer representing a mantissa.

* <code>bigintTag</code> - Tag number. The tag number 55799 can be used to mark a
 "self-described CBOR" object. This document does not attempt to list
 all CBOR tags and their meanings. An up-to-date list can be found at
 the CBOR Tags registry maintained by the Internet Assigned Numbers
 Authority (<i>iana.org/assignments/cbor-tags</i>).

**Returns:**

* A CBOR object where the object <code>valueOb</code> is converted to a
 CBOR object and given the tag <code>bigintTag</code>.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>bigintTag</code> is less
 than 0 or greater than 2^64-1, or <code>valueOb</code> 's type is
 unsupported.

* <code>NullPointerException</code> - The parameter <code>bigintTag</code> is
 null.

### FromObjectAndTag
    public static CBORObject FromObjectAndTag​(Object valueObValue, int smallTag)
Generates a CBOR object from an arbitrary object and gives the resulting
 object a tag.

**Parameters:**

* <code>valueObValue</code> - An arbitrary object. If the tag number is 2 or 3, this
 must be a byte string whose bytes represent an integer in
 little-endian byte order, and the value of the number is 1 minus the
 integer's value for tag 3. If the tag number is 4 or 5, this must be
 an array with two elements: the first must be an integer representing
 the exponent, and the second must be an integer representing a
 mantissa.

* <code>smallTag</code> - A 32-bit integer that specifies a tag number. The tag number
 55799 can be used to mark a "self-described CBOR" object. This
 document does not attempt to list all CBOR tags and their meanings.
 An up-to-date list can be found at the CBOR Tags registry maintained
 by the Internet Assigned Numbers Authority
 (<i>iana.org/assignments/cbor-tags</i>).

**Returns:**

* A CBOR object where the object <code>valueObValue</code> is converted to
 a CBOR object and given the tag <code>smallTag</code>.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>smallTag</code> is less than
 0 or <code>valueObValue</code> 's type is unsupported.

### FromSimpleValue
    public static CBORObject FromSimpleValue​(int simpleValue)
Creates a CBOR object from a simple value number.

**Parameters:**

* <code>simpleValue</code> - The parameter <code>simpleValue</code> is a 32-bit signed
 integer.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>simpleValue</code> is less
 than 0, greater than 255, or from 24 through 31.

### Multiply
    public static CBORObject Multiply​(CBORObject first, CBORObject second)
Multiplies two CBOR numbers.

**Parameters:**

* <code>first</code> - The parameter <code>first</code> is a CBOR object.

* <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

* The product of the two numbers.

**Throws:**

* <code>IllegalArgumentException</code> - Either or both operands are not numbers (as
 opposed to Not-a-Number, NaN).

### NewArray
    public static CBORObject NewArray()
Creates a new empty CBOR array.

**Returns:**

* A new CBOR array.

### NewMap
    public static CBORObject NewMap()
Creates a new empty CBOR map.

**Returns:**

* A new CBOR map.

### Read
    public static CBORObject Read​(InputStream stream)
<p><b>At the moment, use the overload of this method that takes a <code>CBOREncodeOptions</code> object. The object
 <code>CBOREncodeOptions.Default</code> contains recommended settings for
 CBOREncodeOptions, and those settings may be adopted by this overload
 (without a CBOREncodeOptions argument) in the next major
 version.</b></p> <p>Reads an object in CBOR format from a data
 stream. This method will read from the stream until the end of the
 CBOR object is reached or an error occurs, whichever happens
 first.</p>

**Parameters:**

* <code>stream</code> - A readable data stream.

**Returns:**

* A CBOR object that was read.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>CBORException</code> - There was an error in reading or
 parsing the data.

### Read
    public static CBORObject Read​(InputStream stream, CBOREncodeOptions options)
Reads an object in CBOR format from a data stream, using the specified
 options to control the decoding process. This method will read from
 the stream until the end of the CBOR object is reached or an error
 occurs, whichever happens first.

**Parameters:**

* <code>stream</code> - A readable data stream.

* <code>options</code> - The parameter <code>options</code> is a CBOREncodeOptions object.

**Returns:**

* A CBOR object that was read.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>CBORException</code> - There was an error in reading or
 parsing the data.

### ReadJSON
    public static CBORObject ReadJSON​(InputStream stream) throws IOException
Generates a CBOR object from a data stream in JavaScript Object Notation
 (JSON) format. The JSON stream may begin with a byte-order mark
 (U + FEFF). Since version 2.0, the JSON stream can be in UTF-8, UTF-16,
 or UTF-32 encoding; the encoding is detected by assuming that the
 first character read must be a byte-order mark or a nonzero basic
 character (U + 0001 to U + 007F). (In previous versions, only UTF-8 was
 allowed.) <p>If a JSON object has the same key, only the last given
 value will be used for each duplicated key.</p>

**Parameters:**

* <code>stream</code> - A readable data stream. The sequence of bytes read from the
 data stream must contain a single JSON object and not multiple
 objects.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

* <code>CBORException</code> - The data stream contains invalid
 encoding or is not in JSON format.

### ReadJSON
    public static CBORObject ReadJSON​(InputStream stream, CBOREncodeOptions options) throws IOException
Generates a CBOR object from a data stream in JavaScript Object Notation
 (JSON) format, using the specified options to control the decoding
 process. The JSON stream may begin with a byte-order mark (U + FEFF).
 Since version 2.0, the JSON stream can be in UTF-8, UTF-16, or UTF-32
 encoding; the encoding is detected by assuming that the first
 character read must be a byte-order mark or a nonzero basic character
 (U + 0001 to U + 007F). (In previous versions, only UTF-8 was allowed.)
 <p>By default, if a JSON object has the same key, only the last given
 value will be used for each duplicated key.</p>

**Parameters:**

* <code>stream</code> - A readable data stream. The sequence of bytes read from the
 data stream must contain a single JSON object and not multiple
 objects.

* <code>options</code> - The parameter <code>options</code> is a CBOREncodeOptions object.

**Returns:**

* A CBORObject object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

* <code>CBORException</code> - The data stream contains invalid
 encoding or is not in JSON format.

### Remainder
    public static CBORObject Remainder​(CBORObject first, CBORObject second)
Finds the remainder that results when a CBORObject object is divided by the
 value of a CBORObject object.

**Parameters:**

* <code>first</code> - The parameter <code>first</code> is a CBOR object.

* <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

* The remainder of the two numbers.

### Subtract
    public static CBORObject Subtract​(CBORObject first, CBORObject second)
Finds the difference between two CBOR number objects.

**Parameters:**

* <code>first</code> - The parameter <code>first</code> is a CBOR object.

* <code>second</code> - The parameter <code>second</code> is a CBOR object.

**Returns:**

* The difference of the two objects.

**Throws:**

* <code>IllegalArgumentException</code> - Either or both operands are not numbers (as
 opposed to Not-a-Number, NaN).

### Write
    public static void Write​(String str, OutputStream stream) throws IOException
<p><b>At the moment, use the overload of this method that takes a <code>CBOREncodeOptions</code> object. The object
 <code>CBOREncodeOptions.Default</code> contains recommended settings for
 CBOREncodeOptions, and those settings may be adopted by this overload
 (without a CBOREncodeOptions argument) in the next major
 version.</b></p> <p>Writes a string in CBOR format to a data stream.
 The string will be encoded using indefinite-length encoding if its
 length exceeds a certain threshold (this behavior may change in
 future versions of this library).</p>

**Parameters:**

* <code>str</code> - The string to write. Can be null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(String str, OutputStream stream, CBOREncodeOptions options) throws IOException
Writes a string in CBOR format to a data stream, using the given options to
 control the encoding process.

**Parameters:**

* <code>str</code> - The string to write. Can be null.

* <code>stream</code> - A writable data stream.

* <code>options</code> - Options for encoding the data to CBOR.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(com.upokecenter.numbers.EFloat bignum, OutputStream stream) throws IOException
Writes a binary floating-point number in CBOR format to a data stream as
 follows: <ul> <li>If the value is null, writes the byte 0xF6.</li>
 <li>If the value is negative zero, infinity, or NaN, converts the
 number to a <code>double</code> and writes that <code>double</code>. If negative
 zero should not be written this way, use the Plus method to convert
 the value beforehand.</li> <li>If the value has an exponent of zero,
 writes the value as an unsigned integer or signed integer if the
 number can fit either type or as a big integer otherwise.</li> <li>In
 all other cases, writes the value as a big float.</li></ul>

**Parameters:**

* <code>bignum</code> - An arbitrary-precision binary float.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(com.upokecenter.numbers.ERational rational, OutputStream stream) throws IOException
Writes a rational number in CBOR format to a data stream.

**Parameters:**

* <code>rational</code> - An arbitrary-precision rational number.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(com.upokecenter.numbers.EDecimal bignum, OutputStream stream) throws IOException
Writes a decimal floating-point number in CBOR format to a data stream, as
 follows: <ul> <li>If the value is null, writes the byte 0xF6.</li>
 <li>If the value is negative zero, infinity, or NaN, converts the
 number to a <code>double</code> and writes that <code>double</code>. If negative
 zero should not be written this way, use the Plus method to convert
 the value beforehand.</li> <li>If the value has an exponent of zero,
 writes the value as an unsigned integer or signed integer if the
 number can fit either type or as a big integer otherwise.</li> <li>In
 all other cases, writes the value as a decimal number.</li></ul>

**Parameters:**

* <code>bignum</code> - The arbitrary-precision decimal number to write. Can be null.

* <code>stream</code> - InputStream to write to.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(com.upokecenter.numbers.EInteger bigint, OutputStream stream) throws IOException
Writes a big integer in CBOR format to a data stream.

**Parameters:**

* <code>bigint</code> - Big integer to write. Can be null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(long value, OutputStream stream) throws IOException
Writes a 64-bit signed integer in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(int value, OutputStream stream) throws IOException
Writes a 32-bit signed integer in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(short value, OutputStream stream) throws IOException
Writes a 16-bit signed integer in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(char value, OutputStream stream) throws IOException
Writes a Unicode character as a string in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>value</code> is a surrogate
 code point.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(boolean value, OutputStream stream) throws IOException
Writes a Boolean value in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(byte value, OutputStream stream) throws IOException
Writes a byte (0 to 255) in CBOR format to a data stream. If the value is
 less than 24, writes that byte. If the value is 25 to 255, writes the
 byte 24, then this byte's value.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(float value, OutputStream s) throws IOException
Writes a 32-bit floating-point number in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>s</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>s</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(double value, OutputStream stream) throws IOException
Writes a 64-bit floating-point number in CBOR format to a data stream.

**Parameters:**

* <code>value</code> - The value to write.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### Write
    public static void Write​(CBORObject value, OutputStream stream) throws IOException
Writes a CBOR object to a CBOR data stream.

**Parameters:**

* <code>value</code> - The value to write. Can be null.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code>

### Write
    public static void Write​(Object objValue, OutputStream stream) throws IOException
<p><b>At the moment, use the overload of this method that takes a <code>CBOREncodeOptions</code> object. The object
 <code>CBOREncodeOptions.Default</code> contains recommended settings for
 CBOREncodeOptions, and those settings may be adopted by this overload
 (without a CBOREncodeOptions argument) in the next major version.</b>
 </p> <p>Writes a CBOR object to a CBOR data stream. See the
 three-parameter Write method that takes a CBOREncodeOptions. </p>

**Parameters:**

* <code>objValue</code> - The parameter <code>objValue</code> is an arbitrary object.

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>IOException</code>

### Write
    public static void Write​(Object objValue, OutputStream output, CBOREncodeOptions options) throws IOException
Writes an arbitrary object to a CBOR data stream, using the specified
 options for controlling how the object is encoded to CBOR data
 format. If the object is convertible to a CBOR map or a CBOR object
 that contains CBOR maps, the keys to those maps are written out to
 the data stream in an undefined order. The example code given in <see cref='M:PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can be
 used to write out certain keys of a CBOR map in a given order.
 Currently, the following objects are supported: <ul> <li>Lists of
 CBORObject.</li> <li>Maps of CBORObject. The keys to the map are
 written out to the data stream in an undefined order.</li>
 <li>Null.</li> <li>Byte arrays, which will always be written as
 definite-length byte strings.</li> <li>String objects, which will be
 written as indefinite-length text strings if their size exceeds a
 certain threshold (this behavior may change in future versions of
 this library).</li> <li>Any object accepted by the FromObject static
 methods.</li></ul>

**Parameters:**

* <code>objValue</code> - The arbitrary object to be serialized. Can be null.

* <code>output</code> - A writable data stream.

* <code>options</code> - CBOR options for encoding the CBOR object to bytes.

**Throws:**

* <code>IllegalArgumentException</code> - The object's type is not supported.

* <code>NullPointerException</code> - The parameter <code>options</code> or <code>
 output</code> is null.

* <code>IOException</code>

### WriteJSON
    public static void WriteJSON​(Object obj, OutputStream outputStream) throws IOException
Converts an arbitrary object to a string in JavaScript Object Notation
 (JSON) format, as in the ToJSONString method, and writes that string
 to a data stream in UTF-8. If the object is convertible to a CBOR
 map, or to a CBOR object that contains CBOR maps, the keys to those
 maps are written out to the JSON string in an undefined order. The
 example code given in <see cref='M:PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)'/>
 can be used to write out certain keys of a CBOR map in a given order
 to a JSON string.

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is an arbitrary object.

* <code>outputStream</code> - A writable data stream.

**Throws:**

* <code>IOException</code>

### Abs
    public CBORObject Abs()
Gets this object's absolute value.

**Returns:**

* This object's absolute without its negative sign.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type.

### Add
    public CBORObject Add​(Object key, Object valueOb)
<p>Adds a new key and its value to this CBOR map, or adds the value if the
 key doesn't exist.</p> <p>NOTE: This method can't be used to add a
 tag to an existing CBOR object. To create a CBOR object with a given
 tag, call the <code>CBORObject.FromObjectAndTag</code> method and pass the
 CBOR object and the desired tag number to that method.</p>

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

* <code>IllegalArgumentException</code> - The parameter <code>key</code> already exists in
 this map.

* <code>IllegalStateException</code> - This object is not a map.

* <code>IllegalArgumentException</code> - The parameter <code>key</code> or <code>
 valueOb</code> has an unsupported type.

### Add
    public CBORObject Add​(CBORObject obj)
<p>Adds a new object to the end of this array. (Used to throw
 NullPointerException on a null reference, but now converts the null
 reference to CBORObject.Null, for convenience with the Object
 overload of this method).</p> <p>NOTE: This method can't be used to
 add a tag to an existing CBOR object. To create a CBOR object with a
 given tag, call the <code>CBORObject.FromObjectAndTag</code> method and
 pass the CBOR object and the desired tag number to that
 method.</p><p><p>The following example creates a CBOR array and adds
 several CBOR objects, one of which has a custom CBOR tag, to that
 array. Note the chaining behavior made possible by this method.</p>
 <pre>CBORObject obj = CBORObject.NewArray() .Add(CBORObject.False)
 .Add(CBORObject.FromObject(5)) .Add(CBORObject.FromObject("text
 string")) .Add(CBORObject.FromObjectAndTag(9999, 1)); </pre> </p>

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is a CBOR object.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This object is not an array.

### Add
    public CBORObject Add​(Object obj)
<p>Converts an object to a CBOR object and adds it to the end of this
 array.</p> <p>NOTE: This method can't be used to add a tag to an
 existing CBOR object. To create a CBOR object with a given tag, call
 the <code>CBORObject.FromObjectAndTag</code> method and pass the CBOR
 object and the desired tag number to that method.</p><p><p>The
 following example creates a CBOR array and adds several CBOR objects,
 one of which has a custom CBOR tag, to that array. Note the chaining
 behavior made possible by this method.</p> <pre>CBORObject obj =
 CBORObject.NewArray() .Add(CBORObject.False) .Add(5) .Add("text
 string") .Add(CBORObject.FromObjectAndTag(9999, 1)); </pre> </p>

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is a CBOR object.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This object is not an array.

* <code>IllegalArgumentException</code> - The type of <code>obj</code> is not supported.

### AsEInteger
    public com.upokecenter.numbers.EInteger AsEInteger()
Converts this object to an arbitrary-precision integer. Fractional values
 are truncated to an integer.

**Returns:**

* The closest big integer to this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type,
 including if this object is CBORObject.Null.

* <code>ArithmeticException</code> - This object's value is infinity or
 not-a-number (NaN).

### AsBoolean
    public boolean AsBoolean()
Returns false if this object is False, Null, or Undefined; otherwise, true.

**Returns:**

* False if this object is False, Null, or Undefined; otherwise, true.

### AsByte
    public byte AsByte()
Converts this object to a byte (0 to 255). Floating point values are
 truncated to an integer.

**Returns:**

* The closest byte-sized integer to this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type.

* <code>ArithmeticException</code> - This object's value exceeds the range of a
 byte (would be less than 0 or greater than 255 when truncated to an
 integer).

### AsDouble
    public double AsDouble()
Converts this object to a 64-bit floating point number.

**Returns:**

* The closest 64-bit floating point number to this object. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 64-bit floating point number.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type.

### AsEDecimal
    public com.upokecenter.numbers.EDecimal AsEDecimal()
Converts this object to a decimal number.

**Returns:**

* A decimal number for this object's value. If this object is a
 rational number with a nonterminating decimal expansion, returns a
 decimal number rounded to 34 digits.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type,
 including if this object is CBORObject.Null.

### AsEFloat
    public com.upokecenter.numbers.EFloat AsEFloat()
Converts this object to an arbitrary-precision binary floating point number.

**Returns:**

* An arbitrary-precision binary floating point number for this
 object's value. Note that if this object is a decimal number with a
 fractional part, the conversion may lose information depending on the
 number. If this object is a rational number with a nonterminating
 binary expansion, returns a binary floating-point number rounded to
 113 bits.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type,
 including if this object is CBORObject.Null.

### AsERational
    public com.upokecenter.numbers.ERational AsERational()
Converts this object to a rational number.

**Returns:**

* A rational number for this object's value.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type,
 including if this object is CBORObject.Null.

### AsInt16
    public short AsInt16()
Converts this object to a 16-bit signed integer. Floating point values are
 truncated to an integer.

**Returns:**

* The closest 16-bit signed integer to this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type.

* <code>ArithmeticException</code> - This object's value exceeds the range of a
 16-bit signed integer.

### AsInt32
    public int AsInt32()
Converts this object to a 32-bit signed integer. Non-integer number values
 are truncated to an integer. (NOTE: To determine whether this method
 call can succeed, call the <b>CanTruncatedIntFitInInt32</b> method
 before calling this method. Checking whether this object's type is
 <code>CBORType.Number</code> is not sufficient. See the example.).<p><p>The
 following example code (originally written in C# for the .NET
 Framework) shows a way to check whether a given CBOR object stores a
 32-bit signed integer before getting its value. </p> <pre>CBORObject
 obj = CBORObject.FromInt32(99999); if&#x28;obj.isIntegral() &amp;&amp;
 obj.getCanTruncatedIntFitInInt32()&#x28;)) &#x7b;  // Not an Int32; handle
 the error Console.WriteLine("Not a 32-bit integer."); &#x7d; else {
 Console.WriteLine("The value is " + obj.AsInt32()); } </pre> </p>

**Returns:**

* The closest 32-bit signed integer to this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type.

* <code>ArithmeticException</code> - This object's value exceeds the range of a
 32-bit signed integer.

### AsInt64
    public long AsInt64()
Converts this object to a 64-bit signed integer. Non-integer numbers are
 truncated to an integer. (NOTE: To determine whether this method call
 can succeed, call the <b>CanTruncatedIntFitInInt64</b> method before
 calling this method. Checking whether this object's type is
 <code>CBORType.Number</code> is not sufficient. See the example.).<p><p>The
 following example code (originally written in C# for the .NET
 Framework) shows a way to check whether a given CBOR object stores a
 64-bit signed integer before getting its value. </p> <pre>CBORObject
 obj = CBORObject.FromInt64(99999); if&#x28;obj.isIntegral() &amp;&amp;
 obj.getCanTruncatedIntFitInInt64()&#x28;)) &#x7b;  // Not an Int64; handle
 the error Console.WriteLine("Not a 64-bit integer."); &#x7d; else {
 Console.WriteLine("The value is " + obj.AsInt64()); } </pre> </p>

**Returns:**

* The closest 64-bit signed integer to this object.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type.

* <code>ArithmeticException</code> - This object's value exceeds the range of a
 64-bit signed integer.

### AsSingle
    public float AsSingle()
Converts this object to a 32-bit floating point number.

**Returns:**

* The closest 32-bit floating point number to this object. The return
 value can be positive infinity or negative infinity if this object's
 value exceeds the range of a 32-bit floating point number.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type.

### AsString
    public String AsString()
Gets the value of this object as a text string.<p><p>The following example
 code (originally written in C# for the .NET Framework) shows an idiom
 for returning a string value if a CBOR object is a text string, or
 <code>null</code> if the CBOR object is a CBOR null.</p> <pre>CBORObject
 obj = CBORObject.FromString("test"); string str = obj.isNull() ? null :
 obj.AsString(); </pre> </p>

**Returns:**

* Gets this object's string.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a string, including
 if this object is CBORObject.Null.

### CanFitInDouble
    public boolean CanFitInDouble()
Returns whether this object's value can be converted to a 64-bit floating
 point number without its value being rounded to another numerical
 value.

**Returns:**

* Whether this object's value can be converted to a 64-bit floating
 point number without its value being rounded to another numerical
 value. Returns true if this is a not-a-number value, even if the
 value's diagnostic information can' t fit in a 64-bit floating point
 number.

### CanFitInInt32
    public boolean CanFitInInt32()
Returns whether this object's numerical value is an integer, is -(2^31) or
 greater, and is less than 2^31.

**Returns:**

* <code>true</code> if this object's numerical value is an integer, is
 -(2^31) or greater, and is less than 2^31; otherwise, <code>false</code> .

### CanFitInInt64
    public boolean CanFitInInt64()
Returns whether this object's numerical value is an integer, is -(2^63) or
 greater, and is less than 2^63.

**Returns:**

* <code>true</code> if this object's numerical value is an integer, is
 -(2^63) or greater, and is less than 2^63; otherwise, <code>false</code> .

### CanFitInSingle
    public boolean CanFitInSingle()
Returns whether this object's value can be converted to a 32-bit floating
 point number without its value being rounded to another numerical
 value.

**Returns:**

* Whether this object's value can be converted to a 32-bit floating
 point number without its value being rounded to another numerical
 value. Returns true if this is a not-a-number value, even if the
 value's diagnostic information can' t fit in a 32-bit floating point
 number.

### CanTruncatedIntFitInInt32
    public boolean CanTruncatedIntFitInInt32()
Returns whether this object's value, truncated to an integer, would be
 -(2^31) or greater, and less than 2^31.

**Returns:**

* <code>true</code> if this object's value, truncated to an integer, would
 be -(2^31) or greater, and less than 2^31; otherwise, <code>false</code> .

### CanTruncatedIntFitInInt64
    public boolean CanTruncatedIntFitInInt64()
Returns whether this object's value, truncated to an integer, would be
 -(2^63) or greater, and less than 2^63.

**Returns:**

* <code>true</code> if this object's value, truncated to an integer, would
 be -(2^63) or greater, and less than 2^63; otherwise, <code>false</code> .

### compareTo
    public int compareTo​(CBORObject other)
Compares two CBOR objects. <p>In this implementation:</p> <ul> <li>The null
 pointer (null reference) is considered less than any other
 object.</li> <li>If either object is true, false, CBORObject.Null, or
 the undefined value, it is treated as less than the other value. If
 both objects have one of these four values, then undefined is less
 than CBORObject.Null, which is less than false, which is less than
 true.</li> <li>If both objects are numbers, their mathematical values
 are compared. Here, NaN (not-a-number) is considered greater than any
 number.</li> <li>If both objects are simple values other than true,
 false, CBORObject.Null, and the undefined value, the objects are
 compared according to their ordinal numbers.</li> <li>If both objects
 are arrays, each element is compared. If one array is shorter than
 the other and the other array begins with that array (for the
 purposes of comparison), the shorter array is considered less than
 the longer array.</li> <li>If both objects are strings, compares each
 string code-point by code-point, as though by the
 DataUtilities.CodePointCompare method.</li> <li>If both objects are
 maps, compares each map as though each were an array with the sorted
 keys of that map as the array's elements. If both maps have the same
 keys, their values are compared in the order of the sorted keys.</li>
 <li>If each object is a different type, then they are sorted by their
 type number, in the order given for the CBORType enumeration.</li>
 <li>If each object has different tags and both objects are otherwise
 equal under this method, each element is compared as though each were
 an array with that object's tags listed in order from outermost to
 innermost.</li></ul> <p>This method is not consistent with the Equals
 method.</p>

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;CBORObject&gt;</code>

**Parameters:**

* <code>other</code> - A value to compare with.

**Returns:**

* Less than 0, if this value is less than the other object; or 0, if
 both values are equal; or greater than 0, if this value is less than
 the other object or if the other object is null.

**Throws:**

* <code>IllegalArgumentException</code> - An internal error occurred.

### CompareToIgnoreTags
    public int CompareToIgnoreTags​(CBORObject other)
Compares this object and another CBOR object, ignoring the tags they have,
 if any. See the compareTo method for more information on the
 comparison function.

**Parameters:**

* <code>other</code> - A value to compare with.

**Returns:**

* Less than 0, if this value is less than the other object; or 0, if
 both values are equal; or greater than 0, if this value is less than
 the other object or if the other object is null.

### ContainsKey
    public boolean ContainsKey​(CBORObject key)
Determines whether a value of the given key exists in this object.

**Parameters:**

* <code>key</code> - An object that serves as the key.

**Returns:**

* <code>true</code> if the given key is found, or false if the given key is
 not found or this object is not a map.

**Throws:**

* <code>NullPointerException</code> - Key is null (as opposed to
 CBORObject.Null).

### ContainsKey
    public boolean ContainsKey​(String key)
Determines whether a value of the given key exists in this object.

**Parameters:**

* <code>key</code> - A string that serves as the key.

**Returns:**

* <code>true</code> if the given key (as a CBOR object) is found, or false
 if the given key is not found or this object is not a map.

**Throws:**

* <code>NullPointerException</code> - Key is null.

### EncodeToBytes
    public byte[] EncodeToBytes()
<p><b>At the moment, use the overload of this method that takes a <code>CBOREncodeOptions</code> object. The object
 <code>CBOREncodeOptions.Default</code> contains recommended settings for
 CBOREncodeOptions, and those settings may be adopted by this overload
 (without a CBOREncodeOptions argument) in the next major version.</b>
 </p> <p>Writes the binary representation of this CBOR object and
 returns a byte array of that representation. If the CBOR object
 contains CBOR maps, or is a CBOR map itself, the keys to the map are
 written out to the byte array in an undefined order. The example code
 given in <see cref='M:PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can be
 used to write out certain keys of a CBOR map in a given order. </p>

**Returns:**

* A byte array in CBOR format.

### EncodeToBytes
    public byte[] EncodeToBytes​(CBOREncodeOptions options)
Writes the binary representation of this CBOR object and returns a byte
 array of that representation, using the specified options for
 encoding the object to CBOR format. If the CBOR object contains CBOR
 maps, or is a CBOR map itself, the keys to the map are written out to
 the byte array in an undefined order. The example code given in <see cref='M:PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can be
 used to write out certain keys of a CBOR map in a given order.

**Parameters:**

* <code>options</code> - Options for encoding the data to CBOR.

**Returns:**

* A byte array in CBOR format.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>options</code> is null.

### equals
    public boolean equals​(Object obj)
Determines whether this object and another object are equal and have the
 same type. Not-a-number values can be considered equal by this
 method.

**Overrides:**

* <code>equals</code>&nbsp;in class&nbsp;<code>Object</code>

**Parameters:**

* <code>obj</code> - The parameter <code>obj</code> is an arbitrary object.

**Returns:**

* <code>true</code> if the objects are equal; otherwise, <code>false</code> .

### equals
    public boolean equals​(CBORObject other)
Compares the equality of two CBOR objects. Not-a-number values can be
 considered equal by this method.

**Parameters:**

* <code>other</code> - The object to compare.

**Returns:**

* <code>true</code> if the objects are equal; otherwise, <code>false</code> .

### GetByteString
    public byte[] GetByteString()
Gets the byte array used in this object, if this object is a byte string,
 without copying the data to a new one. This method's return value can
 be used to modify the array's contents. Note, though, that the array'
 s length can't be changed.

**Returns:**

* The byte array held by this CBOR object.

**Throws:**

* <code>IllegalStateException</code> - This object is not a byte string.

### hashCode
    public int hashCode()
Calculates the hash code of this object. No application or process IDs are
 used in the hash code calculation.

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A 32-bit hash code.

### GetAllTags
    public com.upokecenter.numbers.EInteger[] GetAllTags()
Gets a list of all tags, from outermost to innermost.

**Returns:**

* An array of tags, or the empty string if this object is untagged.

### HasMostOuterTag
    public boolean HasMostOuterTag​(int tagValue)
Returns whether this object has a tag of the given number.

**Parameters:**

* <code>tagValue</code> - The tag value to search for.

**Returns:**

* <code>true</code> if this object has a tag of the given number;
 otherwise, <code>false</code>.

**Throws:**

* <code>IllegalArgumentException</code> - TagValue is less than 0.

* <code>NullPointerException</code> - The parameter "obj" is null.

### HasMostOuterTag
    public boolean HasMostOuterTag​(com.upokecenter.numbers.EInteger bigTagValue)
Returns whether this object has a tag of the given number.

**Parameters:**

* <code>bigTagValue</code> - The tag value to search for.

**Returns:**

* <code>true</code> if this object has a tag of the given number;
 otherwise, <code>false</code> .

**Throws:**

* <code>NullPointerException</code> - BigTagValue is null.

* <code>IllegalArgumentException</code> - BigTagValue is less than 0.

### HasTag
    public boolean HasTag​(int tagValue)
Returns whether this object has a tag of the given number.

**Parameters:**

* <code>tagValue</code> - The tag value to search for.

**Returns:**

* <code>true</code> if this object has a tag of the given number;
 otherwise, <code>false</code>.

**Throws:**

* <code>IllegalArgumentException</code> - TagValue is less than 0.

* <code>NullPointerException</code> - The parameter "obj" is null.

### HasTag
    public boolean HasTag​(com.upokecenter.numbers.EInteger bigTagValue)
Returns whether this object has a tag of the given number.

**Parameters:**

* <code>bigTagValue</code> - The tag value to search for.

**Returns:**

* <code>true</code> if this object has a tag of the given number;
 otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - BigTagValue is null.

* <code>IllegalArgumentException</code> - BigTagValue is less than 0.

### Insert
    public CBORObject Insert​(int index, Object valueOb)
Inserts an object at the specified position in this CBOR array.

**Parameters:**

* <code>index</code> - Zero-based index to insert at.

* <code>valueOb</code> - An object representing the value, which will be converted to
 a CBORObject. Can be null, in which case this value is converted to
 CBORObject.Null.

**Returns:**

* This instance.

**Throws:**

* <code>IllegalStateException</code> - This object is not an array.

* <code>IllegalArgumentException</code> - The parameter <code>valueOb</code> has an
 unsupported type; or <code>index</code> is not a valid index into this
 array.

### IsInfinity
    public boolean IsInfinity()
Gets a value indicating whether this CBOR object represents infinity.

**Returns:**

* <code>true</code> if this CBOR object represents infinity; otherwise,
 <code>false</code> .

### IsNaN
    public boolean IsNaN()
Gets a value indicating whether this CBOR object represents a not-a-number
 value (as opposed to whether this object's type is not a number
 type).

**Returns:**

* <code>true</code> if this CBOR object represents a not-a-number value (as
 opposed to whether this object's type is not a number type);
 otherwise, <code>false</code> .

### IsNegativeInfinity
    public boolean IsNegativeInfinity()
Gets a value indicating whether this CBOR object represents negative
 infinity.

**Returns:**

* <code>true</code> if this CBOR object represents negative infinity;
 otherwise, <code>false</code> .

### IsPositiveInfinity
    public boolean IsPositiveInfinity()
Gets a value indicating whether this CBOR object represents positive
 infinity.

**Returns:**

* <code>true</code> if this CBOR object represents positive infinity;
 otherwise, <code>false</code> .

### Negate
    public CBORObject Negate()
Gets this object's value with the sign reversed.

**Returns:**

* The reversed-sign form of this number.

**Throws:**

* <code>IllegalStateException</code> - This object's type is not a number type.

### Clear
    public void Clear()
Removes all items from this CBOR array or all keys and values from this CBOR
 map.

**Throws:**

* <code>InvalidOperationException</code> - This object is not a CBOR array or CBOR
 map.

### Remove
    public boolean Remove​(Object obj)
If this object is an array, removes the first instance of the specified item
 (once converted to a CBOR object) from the array. If this object is a
 map, removes the item with the given key (once converted to a CBOR
 object) from the map.

**Parameters:**

* <code>obj</code> - The item or key (once converted to a CBOR object) to remove.

**Returns:**

* <code>true</code> if the item was removed; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>obj</code> is null (as
 opposed to CBORObject.Null).

* <code>IllegalStateException</code> - The object is not an array or map.

### RemoveAt
    public boolean RemoveAt​(int index)
Removes the item at the given index of this CBOR array.

**Parameters:**

* <code>index</code> - The index, starting at 0, of the item to remove.

**Returns:**

* Returns "true" if the object was removed. Returns "false" if the
 given index is less than 0, or is at least as high as the number of
 items in the array.

**Throws:**

* <code>InvalidOperationException</code> - This object is not a CBOR array.

### Remove
    public boolean Remove​(CBORObject obj)
If this object is an array, removes the first instance of the specified item
 from the array. If this object is a map, removes the item with the
 given key from the map.

**Parameters:**

* <code>obj</code> - The item or key to remove.

**Returns:**

* <code>true</code> if the item was removed; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>obj</code> is null (as
 opposed to CBORObject.Null).

* <code>IllegalStateException</code> - The object is not an array or map.

### Set
    public CBORObject Set​(Object key, Object valueOb)
Maps an object to a key in this CBOR map, or adds the value if the key
 doesn't exist.

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

* <code>IllegalStateException</code> - This object is not a map.

* <code>IllegalArgumentException</code> - The parameter <code>key</code> or <code>
 valueOb</code> has an unsupported type.

### ToJSONString
    public String ToJSONString()
Converts this object to a string in JavaScript Object Notation (JSON)
 format, using the specified options to control the encoding process.
 See the overload to JSONString taking a JSONOptions argument. <p>If
 the CBOR object contains CBOR maps, or is a CBOR map itself, the keys
 to the map are written out to the JSON string in an undefined order.
 The example code given in <see cref='M:PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)'/>
 can be used to write out certain keys of a CBOR map in a given order
 to a JSON string.</p>

**Returns:**

* A text string.

### ToJSONString
    public String ToJSONString​(JSONOptions options)
Converts this object to a string in JavaScript Object Notation (JSON)
 format, using the specified options to control the encoding process.
 This function works not only with arrays and maps, but also integers,
 strings, byte arrays, and other JSON data types. Notes: <ul> <li>If
 this object contains maps with non-string keys, the keys are
 converted to JSON strings before writing the map as a JSON string.
 </li> <li>If the CBOR object contains CBOR maps, or is a CBOR map
 itself, the keys to the map are written out to the JSON string in an
 undefined order. </li> <li>If a number in the form of an
 arbitrary-precision binary float has a very high binary exponent, it
 will be converted to a double before being converted to a JSON
 string. (The resulting double could overflow to infinity, in which
 case the arbitrary-precision binary float is converted to null.)
 </li> <li>The string will not begin with a byte-order mark (U + FEFF);
 RFC 8259 (the JSON specification) forbids placing a byte-order mark
 at the beginning of a JSON string. </li> <li>Byte strings are
 converted to Base64 URL without whitespace or padding by default (see
 section 4.1 of RFC 7049). A byte string will instead be converted to
 traditional base64 without whitespace or padding by default if it has
 tag 22, or base16 for tag 23. Padding will be included in the Base64
 URL or traditional base64 form if <b>Base64Padding</b> in the JSON
 options is set to <b>true</b> . (To create a CBOR object with a given
 tag, call the <code>CBORObject.FromObjectAndTag</code> method and pass the
 CBOR object and the desired tag number to that method.) </li>
 <li>Rational numbers will be converted to their exact form, if
 possible, otherwise to a high-precision approximation. (The resulting
 approximation could overflow to infinity, in which case the rational
 number is converted to null.) </li> <li>Simple values other than true
 and false will be converted to null. (This doesn't include
 floating-point numbers.) </li> <li>Infinity and not-a-number will be
 converted to null. </li> </ul> <p>The example code given below
 (originally written in C# for the .NET version) can be used to write
 out certain keys of a CBOR map in a given order to a JSON string.
 </p> <pre>/* Generates a JSON string of 'mapObj' whose keys are in
 the order given in 'keys' . Only keys found in 'keys' will be written
 if they exist in 'mapObj'. &#x2a;&#x2f; private static string
 KeysToJSONMap&#x28;CBORObject mapObj, IList&lt;CBORObject&gt;
 keys&#x29;&#x7b; if (mapObj == null) { throw new
 NullPointerException&#x29;nameof(mapObj));} if (keys == null) {
 throw new NullPointerException&#x29;nameof(keys));} if (obj.getType() !=
 CBORType.Map) { throw new IllegalArgumentException("'obj' is not a map."); }
 StringBuilder builder = new StringBuilder(); var first = true;
 builder.Append("{"); for (CBORObject key in keys) { if
 (mapObj.ContainsKey(key)) { if (!first) {builder.Append(", ");} var
 keyString=(key.getCBORType() == CBORType.String) ? key.AsString() :
 key.ToJSONString(); builder.Append(CBORObject.FromObject(keyString)
 .ToJSONString()) .Append(":").Append(mapObj.get(key).ToJSONString());
 first=false; } } return builder.Append("}").toString(); } </pre>

**Parameters:**

* <code>options</code> - An object containing the options to control writing the CBOR
 object to JSON.

**Returns:**

* A text string containing the converted object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>options</code> is null.

### toString
    public String toString()
Returns this CBOR object in string form. The format is intended to be
 human-readable, not machine-readable, the format is not intended to
 be parsed, and the format may change at any time. The returned string
 is not necessarily in JavaScript Object Notation (JSON); to convert
 CBOR objects to JSON strings, use the <see cref='M:PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)'/>
 method instead.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A text representation of this object.

### Untag
    public CBORObject Untag()
Gets an object with the same value as this one but without the tags it has,
 if any. If this object is an array, map, or byte string, the data
 will not be copied to the returned object, so changes to the returned
 object will be reflected in this one.

**Returns:**

* A CBORObject object.

### UntagOne
    public CBORObject UntagOne()
Gets an object with the same value as this one but without this object's
 outermost tag, if any. If this object is an array, map, or byte
 string, the data will not be copied to the returned object, so
 changes to the returned object will be reflected in this one.

**Returns:**

* A CBORObject object.

### WriteJSONTo
    public void WriteJSONTo​(OutputStream outputStream) throws IOException
Converts this object to a string in JavaScript Object Notation (JSON)
 format, as in the ToJSONString method, and writes that string to a
 data stream in UTF-8. If the CBOR object contains CBOR maps, or is a
 CBOR map, the keys to the map are written out to the JSON string in
 an undefined order. The example code given in <see cref='M:PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)'/>
 can be used to write out certain keys of a CBOR map in a given order
 to a JSON string.

**Parameters:**

* <code>outputStream</code> - A writable data stream.

**Throws:**

* <code>IOException</code> - An I/O error occurred.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is
 null.

### WriteJSONTo
    public void WriteJSONTo​(OutputStream outputStream, JSONOptions options) throws IOException
Converts this object to a string in JavaScript Object Notation (JSON)
 format, as in the ToJSONString method, and writes that string to a
 data stream in UTF-8, using the given JSON options to control the
 encoding process. If the CBOR object contains CBOR maps, or is a CBOR
 map, the keys to the map are written out to the JSON string in an
 undefined order. The example code given in <see cref='M:PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)'/>
 can be used to write out certain keys of a CBOR map in a given order
 to a JSON string.

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>options</code> - An object containing the options to control writing the CBOR
 object to JSON.

**Throws:**

* <code>IOException</code> - An I/O error occurred.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is
 null.

### WriteValue
    public static int WriteValue​(OutputStream outputStream, int majorType, long value) throws IOException
Writes a CBOR major type number and an integer 0 or greater associated with
 it to a data stream, where that integer is passed to this method as a
 64-bit signed integer. This is a low-level method that is useful for
 implementing custom CBOR encoding methodologies. This method encodes
 the given major type and value in the shortest form allowed for the
 major type.

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>majorType</code> - The CBOR major type to write. This is a number from 0
 through 7 as follows. 0: integer 0 or greater; 1: negative integer;
 2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
 simple value. See RFC 7049 for details on these major types.

* <code>value</code> - An integer 0 or greater associated with the major type, as
 follows. 0: integer 0 or greater; 1: the negative integer's absolute
 value is 1 plus this number; 2: length in bytes of the byte string;
 3: length in bytes of the UTF-8 text string; 4: number of items in
 the array; 5: number of key-value pairs in the map; 6: tag number; 7:
 simple value number, which must be in the interval [0, 23] or [32,
 255].

**Returns:**

* The number of bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - Value is from 24 to 31 and major type is 7.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is
 null.

* <code>IOException</code>

### WriteValue
    public static int WriteValue​(OutputStream outputStream, int majorType, int value) throws IOException
Writes a CBOR major type number and an integer 0 or greater associated with
 it to a data stream, where that integer is passed to this method as a
 32-bit signed integer. This is a low-level method that is useful for
 implementing custom CBOR encoding methodologies. This method encodes
 the given major type and value in the shortest form allowed for the
 major type.<p><p>In the following example, an array of three objects
 is written as CBOR to a data stream.</p>
 <pre>CBORObject.WriteValue(stream, 4, 3); /* array, length 3 &#x2a;&#x2f;
 CBORObject.Write("hello world", stream); /* item 1 &#x2a;&#x2f;
 CBORObject.Write(25, stream); /* item 2 &#x2a;&#x2f; CBORObject.Write(false,
 stream);  // item 3 </pre> <p>In the following example, a map
 consisting of two key-value pairs is written as CBOR to a data
 stream.</p> <pre>CBORObject.WriteValue(stream, 5, 2);  // map, 2
 pairs CBORObject.Write("number", stream);  // key 1
 CBORObject.Write(25, stream);  // value 1 CBORObject.Write("string",
 stream);  // key 2 CBORObject.Write("hello", stream);  // value 2
 </pre> <p>In the following example (originally written in C# for the
 .NET Framework version), a text string is written as CBOR to a data
 stream.</p> <pre>string str = "hello world"; byte[] bytes =
 DataUtilities.GetUtf8Bytes(str, true); CBORObject.WriteValue(stream,
 4, bytes.length); stream.write(bytes, 0, bytes.length); </pre> </p>

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>majorType</code> - The CBOR major type to write. This is a number from 0
 through 7 as follows. 0: integer 0 or greater; 1: negative integer;
 2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
 simple value. See RFC 7049 for details on these major types.

* <code>value</code> - An integer 0 or greater associated with the major type, as
 follows. 0: integer 0 or greater; 1: the negative integer's absolute
 value is 1 plus this number; 2: length in bytes of the byte string;
 3: length in bytes of the UTF-8 text string; 4: number of items in
 the array; 5: number of key-value pairs in the map; 6: tag number; 7:
 simple value number, which must be in the interval [0, 23] or [32,
 255].

**Returns:**

* The number of bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - Value is from 24 to 31 and major type is 7.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> is
 null.

* <code>IOException</code>

### WriteValue
    public static int WriteValue​(OutputStream outputStream, int majorType, com.upokecenter.numbers.EInteger bigintValue) throws IOException
Writes a CBOR major type number and an integer 0 or greater associated with
 it to a data stream, where that integer is passed to this method as
 an arbitrary-precision integer. This is a low-level method that is
 useful for implementing custom CBOR encoding methodologies. This
 method encodes the given major type and value in the shortest form
 allowed for the major type.

**Parameters:**

* <code>outputStream</code> - A writable data stream.

* <code>majorType</code> - The CBOR major type to write. This is a number from 0
 through 7 as follows. 0: integer 0 or greater; 1: negative integer;
 2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
 simple value. See RFC 7049 for details on these major types.

* <code>bigintValue</code> - An integer 0 or greater associated with the major type,
 as follows. 0: integer 0 or greater; 1: the negative integer's
 absolute value is 1 plus this number; 2: length in bytes of the byte
 string; 3: length in bytes of the UTF-8 text string; 4: number of
 items in the array; 5: number of key-value pairs in the map; 6: tag
 number; 7: simple value number, which must be in the interval [0, 23]
 or [32, 255]. For major types 0 to 6, this number may not be greater
 than 2^64 - 1.

**Returns:**

* The number of bytes ordered to be written to the data stream.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>majorType</code> is 7 and
 value is greater than 255.

* <code>NullPointerException</code> - The parameter <code>outputStream</code> or
 <code>bigintValue</code> is null.

* <code>IOException</code>

### WriteTo
    public void WriteTo​(OutputStream stream) throws IOException
<p><b>At the moment, use the overload of this method that takes a <code>CBOREncodeOptions</code> object. The object
 <code>CBOREncodeOptions.Default</code> contains recommended settings for
 CBOREncodeOptions, and those settings may be adopted by this overload
 (without a CBOREncodeOptions argument) in the next major version.</b>
 </p> <p>Writes this CBOR object to a data stream. If the CBOR object
 contains CBOR maps, or is a CBOR map, the keys to the map are written
 out to the data stream in an undefined order. See the examples
 (written in C# for the .NET version) for ways to write out certain
 keys of a CBOR map in a given order. </p><p><p>The following example
 shows a method that writes each key of 'mapObj' to 'outputStream', in
 the order given in 'keys', where 'mapObj' is written out in the form
 of a CBOR <b>definite-length map</b> . Only keys found in 'keys' will
 be written if they exist in 'mapObj'. </p> <pre>private static void
 WriteKeysToMap&#x28;CBORObject mapObj, IList&lt;CBORObject&gt; keys,
 InputStream outputStream)&#x7b; if&#x28;mapObj == null)&#x7b; throw new
 NullPointerException&#x28;nameof(mapObj));&#x7d; if&#x28;keys ==
 null)&#x7b;throw new NullPointerException&#x28;nameof(keys));&#x7d;
 if&#x28;outputStream == null)&#x7b;throw new
 NullPointerException&#x28;nameof(outputStream));&#x7d;
 if&#x28;obj.getType() != CBORType.Map)&#x7b; throw new
 IllegalArgumentException("'obj' is not a map."); &#x7d; int keyCount = 0;
 for (CBORObject key in keys) &#x7b;
 if&#x28;mapObj.ContainsKey(key))&#x7b; keyCount++; &#x7d; &#x7d;
 CBORObject.WriteValue(outputStream, 5, keyCount); for (CBORObject key
 in keys) &#x7b; if&#x28;mapObj.ContainsKey(key))&#x7b;
 key.WriteTo(outputStream); mapObj.get(key).WriteTo(outputStream); &#x7d;
 &#x7d; &#x7d; </pre> <p>The following example shows a method that
 writes each key of 'mapObj' to 'outputStream', in the order given in
 'keys', where 'mapObj' is written out in the form of a CBOR
 <b>indefinite-length map</b> . Only keys found in 'keys' will be
 written if they exist in 'mapObj'. </p> <pre>private static void
 WriteKeysToIndefMap&#x28;CBORObject mapObj, IList&lt;CBORObject&gt;
 keys, InputStream outputStream)&#x7b; if&#x28;mapObj == null)&#x7b; throw
 new NullPointerException&#x28;nameof(mapObj));&#x7d; if&#x28;keys ==
 null)&#x7b;throw new NullPointerException&#x28;nameof(keys));&#x7d;
 if&#x28;outputStream == null)&#x7b;throw new
 NullPointerException&#x28;nameof(outputStream));&#x7d;
 if&#x28;obj.getType() != CBORType.Map)&#x7b; throw new
 IllegalArgumentException("'obj' is not a map."); &#x7d;
 outputStream.write((byte)0xbf); for (CBORObject key in keys)
 &#x7b; if&#x28;mapObj.ContainsKey(key))&#x7b;
 key.WriteTo(outputStream); mapObj.get(key).WriteTo(outputStream); &#x7d;
 &#x7d; outputStream.write((byte)0xff); &#x7d; </pre> <p>The
 following example shows a method that writes out a list of objects to
 'outputStream' as an <b>indefinite-length CBOR array</b> . </p>
 <pre>private static void WriteToIndefArray&#x28; IList&lt;object&gt;
 list, InputStream outputStream)&#x7b; if&#x28;list == null)&#x7b; throw
 new NullPointerException&#x28;nameof(list));&#x7d;
 if&#x28;outputStream == null)&#x7b;throw new
 NullPointerException&#x28;nameof(outputStream));&#x7d;
 outputStream.write((byte)0x9f); for (object item in list) &#x7b;
 new CBORObject(item).WriteTo(outputStream); &#x7d;
 outputStream.write((byte)0xff); &#x7d; </pre> </p>

**Parameters:**

* <code>stream</code> - A writable data stream.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

### WriteTo
    public void WriteTo​(OutputStream stream, CBOREncodeOptions options) throws IOException
Writes this CBOR object to a data stream, using the specified options for
 encoding the data to CBOR format. If the CBOR object contains CBOR
 maps, or is a CBOR map, the keys to the map are written out to the
 data stream in an undefined order. The example code given in <see cref='M:PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can be
 used to write out certain keys of a CBOR map in a given order.

**Parameters:**

* <code>stream</code> - A writable data stream.

* <code>options</code> - Options for encoding the data to CBOR.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>stream</code> is null.

* <code>IOException</code> - An I/O error occurred.

* <code>IllegalArgumentException</code> - "Unexpected data type".
