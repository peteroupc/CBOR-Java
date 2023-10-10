# com.upokecenter.cbor.CBORObject

    public final class CBORObject extends Object implements Comparable<CBORObject>

## Fields

* `static final CBORObject False`<br>
  
* `static final CBORObject NaN`<br>
  
* `static final CBORObject NegativeInfinity`<br>
  
* `static final CBORObject Null`<br>
  
* `static final CBORObject PositiveInfinity`<br>
  
* `static final CBORObject True`<br>
  
* `static final CBORObject Undefined`<br>
  
* `static final CBORObject Zero`<br>
  

## Methods

* `CBORObject Add(CBORObject obj)`<br>
  
* `CBORObject Add(Object obj)`<br>
  
* `CBORObject Add(Object key,
 Object valueOb)`<br>
  
* `CBORObject ApplyJSONPatch(CBORObject patch)`<br>
  
* `boolean AsBoolean()`<br>
  
* `double AsDouble()`<br>
  
* `long AsDoubleBits()`<br>
  
* `double AsDoubleValue()`<br>
  
* `com.upokecenter.numbers.EInteger AsEIntegerValue()`<br>
  
* `int AsInt32()`<br>
  
* `int AsInt32Value()`<br>
  
* `long AsInt64Value()`<br>
  
* `CBORNumber AsNumber()`<br>
  
* `float AsSingle()`<br>
  
* `String AsString()`<br>
  
* `CBORObject AtJSONPointer(String pointer)`<br>
  
* `CBORObject AtJSONPointer(String pointer,
 CBORObject defaultValue)`<br>
  
* `long CalcEncodedSize()`<br>
  
* `boolean CanValueFitInInt32()`<br>
  
* `boolean CanValueFitInInt64()`<br>
  
* `void Clear()`<br>
  
* `int compareTo(CBORObject other)`<br>
  
* `int CompareToIgnoreTags(CBORObject other)`<br>
  
* `boolean ContainsKey(CBORObject key)`<br>
  
* `boolean ContainsKey(Object objKey)`<br>
  
* `boolean ContainsKey(String key)`<br>
  
* `static CBORObject DecodeFromBytes(byte[] data)`<br>
  
* `static CBORObject DecodeFromBytes(byte[] data,
 CBOREncodeOptions options)`<br>
  
* `static <T> T DecodeObjectFromBytes(byte[] data,
 CBOREncodeOptions enc,
 Type t)`<br>
  
* `static <T> T DecodeObjectFromBytes(byte[] data,
 CBOREncodeOptions enc,
 Type t,
 CBORTypeMapper mapper,
 PODOptions pod)`<br>
  
* `static <T> T DecodeObjectFromBytes(byte[] data,
 Type t)`<br>
  
* `static <T> T DecodeObjectFromBytes(byte[] data,
 Type t,
 CBORTypeMapper mapper,
 PODOptions pod)`<br>
  
* `static CBORObject[] DecodeSequenceFromBytes(byte[] data)`<br>
  
* `static CBORObject[] DecodeSequenceFromBytes(byte[] data,
 CBOREncodeOptions options)`<br>
  
* `byte[] EncodeToBytes()`<br>
  
* `byte[] EncodeToBytes(CBOREncodeOptions options)`<br>
  
* `boolean equals(CBORObject other)`<br>
  
* `boolean equals(Object obj)`<br>
  
* `static CBORObject FromFloatingPointBits(long floatingBits,
 int byteCount)`<br>
  
* `static CBORObject FromJSONBytes(byte[] bytes)`<br>
  
* `static CBORObject FromJSONBytes(byte[] bytes,
 int offset,
 int count)`<br>
  
* `static CBORObject FromJSONBytes(byte[] bytes,
 int offset,
 int count,
 JSONOptions jsonoptions)`<br>
  
* `static CBORObject FromJSONBytes(byte[] bytes,
 JSONOptions jsonoptions)`<br>
  
* `static CBORObject[] FromJSONSequenceBytes(byte[] bytes)`<br>
  
* `static CBORObject[] FromJSONSequenceBytes(byte[] data,
 JSONOptions options)`<br>
  
* `static CBORObject FromJSONString(String str)`<br>
  
* `static CBORObject FromJSONString(String str,
 int offset,
 int count)`<br>
  
* `static CBORObject FromJSONString(String str,
 int offset,
 int count,
 JSONOptions jsonoptions)`<br>
  
* `static CBORObject FromJSONString(String str,
 JSONOptions jsonoptions)`<br>
  
* `static CBORObject FromObject(boolean value)`<br>
  
* `static CBORObject FromObject(byte value)`<br>
  
* `static CBORObject FromObject(byte[] bytes)`<br>
  
* `static CBORObject FromObject(double value)`<br>
  
* `static CBORObject FromObject(float value)`<br>
  
* `static CBORObject FromObject(int value)`<br>
  
* `static CBORObject FromObject(int[] array)`<br>
  
* `static CBORObject FromObject(long value)`<br>
  
* `static CBORObject FromObject(long[] array)`<br>
  
* `static CBORObject FromObject(short value)`<br>
  
* `static CBORObject FromObject(CBORObject value)`<br>
  
* `static CBORObject FromObject(CBORObject[] array)`<br>
  
* `static CBORObject FromObject(com.upokecenter.numbers.EDecimal bigValue)`<br>
  
* `static CBORObject FromObject(com.upokecenter.numbers.EFloat bigValue)`<br>
  
* `static CBORObject FromObject(com.upokecenter.numbers.EInteger bigintValue)`<br>
  
* `static CBORObject FromObject(com.upokecenter.numbers.ERational bigValue)`<br>
  
* `static CBORObject FromObject(Object obj)`<br>
  
* `static CBORObject FromObject(Object obj,
 CBORTypeMapper mapper)`<br>
  
* `static CBORObject FromObject(Object obj,
 CBORTypeMapper mapper,
 PODOptions options)`<br>
  
* `static CBORObject FromObject(Object obj,
 PODOptions options)`<br>
  
* `static CBORObject FromObject(String strValue)`<br>
  
* `static CBORObject FromObjectAndTag(Object valueObValue,
 int smallTag)`<br>
  
* `static CBORObject FromObjectAndTag(Object valueOb,
 com.upokecenter.numbers.EInteger bigintTag)`<br>
  
* `static CBORObject FromSimpleValue(int simpleValue)`<br>
  
* `CBORObject get(int index)`<br>
  
* `CBORObject get(CBORObject key)`<br>
  
* `CBORObject get(String key)`<br>
  
* `com.upokecenter.numbers.EInteger[] GetAllTags()`<br>
  
* `byte[] GetByteString()`<br>
  
* `final Collection<Map.Entry<CBORObject,CBORObject>> getEntries()`<br>
  
* `final Collection<CBORObject> getKeys()`<br>
  
* `final com.upokecenter.numbers.EInteger getMostInnerTag()`<br>
  
* `final com.upokecenter.numbers.EInteger getMostOuterTag()`<br>
  
* `CBORObject GetOrDefault(Object key,
 CBORObject defaultValue)`<br>
  
* `final int getSimpleValue()`<br>
  
* `final int getTagCount()`<br>
  
* `final CBORType getType()`<br>
  
* `final Collection<CBORObject> getValues()`<br>
  
* `int hashCode()`<br>
  
* `boolean HasMostInnerTag(int tagValue)`<br>
  
* `boolean HasMostInnerTag(com.upokecenter.numbers.EInteger bigTagValue)`<br>
  
* `boolean HasMostOuterTag(int tagValue)`<br>
  
* `boolean HasMostOuterTag(com.upokecenter.numbers.EInteger bigTagValue)`<br>
  
* `boolean HasOneTag()`<br>
  
* `boolean HasOneTag(int tagValue)`<br>
  
* `boolean HasOneTag(com.upokecenter.numbers.EInteger bigTagValue)`<br>
  
* `boolean HasTag(int tagValue)`<br>
  
* `boolean HasTag(com.upokecenter.numbers.EInteger bigTagValue)`<br>
  
* `CBORObject Insert(int index,
 Object valueOb)`<br>
  
* `final boolean isFalse()`<br>
  
* `final boolean isNull()`<br>
  
* `final boolean isNumber()`<br>
  
* `final boolean isTagged()`<br>
  
* `final boolean isTrue()`<br>
  
* `final boolean isUndefined()`<br>
  
* `static CBORObject NewArray()`<br>
  
* `static CBORObject NewMap()`<br>
  
* `static CBORObject NewOrderedMap()`<br>
  
* `static CBORObject Read(InputStream stream)`<br>
  
* `static CBORObject Read(InputStream stream,
 CBOREncodeOptions options)`<br>
  
* `static CBORObject ReadJSON(InputStream stream)`<br>
  
* `static CBORObject ReadJSON(InputStream stream,
 JSONOptions jsonoptions)`<br>
  
* `static CBORObject[] ReadJSONSequence(InputStream stream)`<br>
  
* `static CBORObject[] ReadJSONSequence(InputStream stream,
 JSONOptions jsonoptions)`<br>
  
* `static CBORObject[] ReadSequence(InputStream stream)`<br>
  
* `static CBORObject[] ReadSequence(InputStream stream,
 CBOREncodeOptions options)`<br>
  
* `boolean Remove(CBORObject obj)`<br>
  
* `boolean Remove(Object obj)`<br>
  
* `boolean RemoveAt(int index)`<br>
  
* `void set(int index,
 CBORObject value)`<br>
  
* `void set(CBORObject key,
 CBORObject value)`<br>
  
* `void set(String key,
 CBORObject value)`<br>
  
* `CBORObject Set(Object key,
 Object valueOb)`<br>
  
* `final int size()`<br>
  
* `byte[] ToJSONBytes()`<br>
  
* `byte[] ToJSONBytes(JSONOptions jsonoptions)`<br>
  
* `String ToJSONString()`<br>
  
* `String ToJSONString(JSONOptions options)`<br>
  
* `<T> T ToObject(Type t)`<br>
  
* `<T> T ToObject(Type t,
 CBORTypeMapper mapper)`<br>
  
* `<T> T ToObject(Type t,
 CBORTypeMapper mapper,
 PODOptions options)`<br>
  
* `<T> T ToObject(Type t,
 PODOptions options)`<br>
  
* `String toString()`<br>
  
* `CBORObject Untag()`<br>
  
* `CBORObject UntagOne()`<br>
  
* `CBORObject WithTag(int smallTag)`<br>
  
* `CBORObject WithTag(com.upokecenter.numbers.EInteger bigintTag)`<br>
  
* `static void Write(boolean value,
 OutputStream stream)`<br>
  
* `static void Write(byte value,
 OutputStream stream)`<br>
  
* `static void Write(double value,
 OutputStream stream)`<br>
  
* `static void Write(float value,
 OutputStream stream)`<br>
  
* `static void Write(int value,
 OutputStream stream)`<br>
  
* `static void Write(long value,
 OutputStream stream)`<br>
  
* `static void Write(short value,
 OutputStream stream)`<br>
  
* `static void Write(CBORObject value,
 OutputStream stream)`<br>
  
* `static void Write(com.upokecenter.numbers.EDecimal bignum,
 OutputStream stream)`<br>
  
* `static void Write(com.upokecenter.numbers.EFloat bignum,
 OutputStream stream)`<br>
  
* `static void Write(com.upokecenter.numbers.EInteger bigint,
 OutputStream stream)`<br>
  
* `static void Write(com.upokecenter.numbers.ERational rational,
 OutputStream stream)`<br>
  
* `static void Write(Object objValue,
 OutputStream stream)`<br>
  
* `static void Write(Object objValue,
 OutputStream output,
 CBOREncodeOptions options)`<br>
  
* `static void Write(String str,
 OutputStream stream)`<br>
  
* `static void Write(String str,
 OutputStream stream,
 CBOREncodeOptions options)`<br>
  
* `static int WriteFloatingPointBits(OutputStream outputStream,
 long floatingBits,
 int byteCount)`<br>
  
* `static int WriteFloatingPointBits(OutputStream outputStream,
 long floatingBits,
 int byteCount,
 boolean shortestForm)`<br>
  
* `static int WriteFloatingPointValue(OutputStream outputStream,
 double doubleVal,
 int byteCount)`<br>
  
* `static int WriteFloatingPointValue(OutputStream outputStream,
 float singleVal,
 int byteCount)`<br>
  
* `static void WriteJSON(Object obj,
 OutputStream outputStream)`<br>
  
* `void WriteJSONTo(OutputStream outputStream)`<br>
  
* `void WriteJSONTo(OutputStream outputStream,
 JSONOptions options)`<br>
  
* `void WriteTo(OutputStream stream)`<br>
  
* `void WriteTo(OutputStream stream,
 CBOREncodeOptions options)`<br>
  
* `static int WriteValue(OutputStream outputStream,
 int majorType,
 int value)`<br>
  
* `static int WriteValue(OutputStream outputStream,
 int majorType,
 long value)`<br>
  
* `static int WriteValue(OutputStream outputStream,
 int majorType,
 com.upokecenter.numbers.EInteger bigintValue)`<br>
  

## Field Details

### False
    public static final CBORObject False
### NaN
    public static final CBORObject NaN
### NegativeInfinity
    public static final CBORObject NegativeInfinity
### Null
    public static final CBORObject Null
### PositiveInfinity
    public static final CBORObject PositiveInfinity
### True
    public static final CBORObject True
### Undefined
    public static final CBORObject Undefined
### Zero
    public static final CBORObject Zero
## Method Details

### size
    public final int size()
### getMostInnerTag
    public final com.upokecenter.numbers.EInteger getMostInnerTag()
### isFalse
    public final boolean isFalse()
### isNull
    public final boolean isNull()
### isTagged
    public final boolean isTagged()
### isTrue
    public final boolean isTrue()
### isUndefined
    public final boolean isUndefined()
### getKeys
    public final Collection<CBORObject> getKeys()
### getMostOuterTag
    public final com.upokecenter.numbers.EInteger getMostOuterTag()
### getSimpleValue
    public final int getSimpleValue()
### isNumber
    public final boolean isNumber()
### getType
    public final CBORType getType()
### getEntries
    public final Collection<Map.Entry<CBORObject,CBORObject>> getEntries()
### getValues
    public final Collection<CBORObject> getValues()
### get
    public CBORObject get(int index)
### set
    public void set(int index, CBORObject value)
### GetOrDefault
    public CBORObject GetOrDefault(Object key, CBORObject defaultValue)
### get
    public CBORObject get(CBORObject key)
### set
    public void set(CBORObject key, CBORObject value)
### get
    public CBORObject get(String key)
### set
    public void set(String key, CBORObject value)
### DecodeFromBytes
    public static CBORObject DecodeFromBytes(byte[] data)
### DecodeSequenceFromBytes
    public static CBORObject[] DecodeSequenceFromBytes(byte[] data)
### DecodeSequenceFromBytes
    public static CBORObject[] DecodeSequenceFromBytes(byte[] data, CBOREncodeOptions options)
### FromJSONSequenceBytes
    public static CBORObject[] FromJSONSequenceBytes(byte[] bytes)
### ToJSONBytes
    public byte[] ToJSONBytes()
### ToJSONBytes
    public byte[] ToJSONBytes(JSONOptions jsonoptions)
### FromJSONSequenceBytes
    public static CBORObject[] FromJSONSequenceBytes(byte[] data, JSONOptions options)
### DecodeFromBytes
    public static CBORObject DecodeFromBytes(byte[] data, CBOREncodeOptions options)
### FromJSONString
    public static CBORObject FromJSONString(String str, int offset, int count)
### FromJSONString
    public static CBORObject FromJSONString(String str, JSONOptions jsonoptions)
### FromJSONString
    public static CBORObject FromJSONString(String str)
### FromJSONString
    public static CBORObject FromJSONString(String str, int offset, int count, JSONOptions jsonoptions)
### ToObject
    public <T> T ToObject(Type t)
### ToObject
    public <T> T ToObject(Type t, CBORTypeMapper mapper)
### ToObject
    public <T> T ToObject(Type t, PODOptions options)
### ToObject
    public <T> T ToObject(Type t, CBORTypeMapper mapper, PODOptions options)
### DecodeObjectFromBytes
    public static <T> T DecodeObjectFromBytes(byte[] data, CBOREncodeOptions enc, Type t, CBORTypeMapper mapper, PODOptions pod)
### DecodeObjectFromBytes
    public static <T> T DecodeObjectFromBytes(byte[] data, CBOREncodeOptions enc, Type t)
### DecodeObjectFromBytes
    public static <T> T DecodeObjectFromBytes(byte[] data, Type t, CBORTypeMapper mapper, PODOptions pod)
### DecodeObjectFromBytes
    public static <T> T DecodeObjectFromBytes(byte[] data, Type t)
### FromObject
    public static CBORObject FromObject(long value)
### FromObject
    public static CBORObject FromObject(CBORObject value)
### CalcEncodedSize
    public long CalcEncodedSize()
### FromObject
    public static CBORObject FromObject(com.upokecenter.numbers.EInteger bigintValue)
### FromObject
    public static CBORObject FromObject(com.upokecenter.numbers.EFloat bigValue)
### FromObject
    public static CBORObject FromObject(com.upokecenter.numbers.ERational bigValue)
### FromObject
    public static CBORObject FromObject(com.upokecenter.numbers.EDecimal bigValue)
### FromObject
    public static CBORObject FromObject(String strValue)
### FromObject
    public static CBORObject FromObject(int value)
### FromObject
    public static CBORObject FromObject(short value)
### FromObject
    public static CBORObject FromObject(boolean value)
### FromObject
    public static CBORObject FromObject(byte value)
### FromObject
    public static CBORObject FromObject(float value)
### FromObject
    public static CBORObject FromObject(double value)
### FromObject
    public static CBORObject FromObject(byte[] bytes)
### FromObject
    public static CBORObject FromObject(CBORObject[] array)
### FromObject
    public static CBORObject FromObject(int[] array)
### FromObject
    public static CBORObject FromObject(long[] array)
### FromObject
    public static CBORObject FromObject(Object obj)
### FromObject
    public static CBORObject FromObject(Object obj, PODOptions options)
### FromObject
    public static CBORObject FromObject(Object obj, CBORTypeMapper mapper)
### FromObject
    public static CBORObject FromObject(Object obj, CBORTypeMapper mapper, PODOptions options)
### WithTag
    public CBORObject WithTag(com.upokecenter.numbers.EInteger bigintTag)
### FromObjectAndTag
    public static CBORObject FromObjectAndTag(Object valueOb, com.upokecenter.numbers.EInteger bigintTag)
### WithTag
    public CBORObject WithTag(int smallTag)
### FromObjectAndTag
    public static CBORObject FromObjectAndTag(Object valueObValue, int smallTag)
### FromSimpleValue
    public static CBORObject FromSimpleValue(int simpleValue)
### NewArray
    public static CBORObject NewArray()
### NewMap
    public static CBORObject NewMap()
### NewOrderedMap
    public static CBORObject NewOrderedMap()
### ReadSequence
    public static CBORObject[] ReadSequence(InputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### ReadSequence
    public static CBORObject[] ReadSequence(InputStream stream, CBOREncodeOptions options) throws IOException

**Throws:**

* <code>IOException</code>

### Read
    public static CBORObject Read(InputStream stream)
### Read
    public static CBORObject Read(InputStream stream, CBOREncodeOptions options)
### ReadJSON
    public static CBORObject ReadJSON(InputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### ReadJSONSequence
    public static CBORObject[] ReadJSONSequence(InputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### ReadJSONSequence
    public static CBORObject[] ReadJSONSequence(InputStream stream, JSONOptions jsonoptions) throws IOException

**Throws:**

* <code>IOException</code>

### ReadJSON
    public static CBORObject ReadJSON(InputStream stream, JSONOptions jsonoptions) throws IOException

**Throws:**

* <code>IOException</code>

### FromJSONBytes
    public static CBORObject FromJSONBytes(byte[] bytes)
### FromJSONBytes
    public static CBORObject FromJSONBytes(byte[] bytes, JSONOptions jsonoptions)
### FromJSONBytes
    public static CBORObject FromJSONBytes(byte[] bytes, int offset, int count)
### FromJSONBytes
    public static CBORObject FromJSONBytes(byte[] bytes, int offset, int count, JSONOptions jsonoptions)
### Write
    public static void Write(String str, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(String str, OutputStream stream, CBOREncodeOptions options) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(com.upokecenter.numbers.EFloat bignum, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(com.upokecenter.numbers.ERational rational, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(com.upokecenter.numbers.EDecimal bignum, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(com.upokecenter.numbers.EInteger bigint, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(long value, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(int value, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(short value, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(boolean value, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(byte value, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(float value, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(double value, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(CBORObject value, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(Object objValue, OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### Write
    public static void Write(Object objValue, OutputStream output, CBOREncodeOptions options) throws IOException

**Throws:**

* <code>IOException</code>

### WriteJSON
    public static void WriteJSON(Object obj, OutputStream outputStream) throws IOException

**Throws:**

* <code>IOException</code>

### Add
    public CBORObject Add(Object key, Object valueOb)
### Add
    public CBORObject Add(CBORObject obj)
### Add
    public CBORObject Add(Object obj)
### AsBoolean
    public boolean AsBoolean()
### AsDouble
    public double AsDouble()
### AsInt32Value
    public int AsInt32Value()
### AsInt64Value
    public long AsInt64Value()
### CanValueFitInInt64
    public boolean CanValueFitInInt64()
### CanValueFitInInt32
    public boolean CanValueFitInInt32()
### AsEIntegerValue
    public com.upokecenter.numbers.EInteger AsEIntegerValue()
### AsDoubleBits
    public long AsDoubleBits()
### AsDoubleValue
    public double AsDoubleValue()
### AsNumber
    public CBORNumber AsNumber()
### AsInt32
    public int AsInt32()
### AsSingle
    public float AsSingle()
### AsString
    public String AsString()
### compareTo
    public int compareTo(CBORObject other)

**Specified by:**

* <code>compareTo</code> in interface <code>Comparable&lt;CBORObject&gt;</code>

### CompareToIgnoreTags
    public int CompareToIgnoreTags(CBORObject other)
### ContainsKey
    public boolean ContainsKey(Object objKey)
### ContainsKey
    public boolean ContainsKey(CBORObject key)
### ContainsKey
    public boolean ContainsKey(String key)
### EncodeToBytes
    public byte[] EncodeToBytes()
### EncodeToBytes
    public byte[] EncodeToBytes(CBOREncodeOptions options)
### AtJSONPointer
    public CBORObject AtJSONPointer(String pointer)
### AtJSONPointer
    public CBORObject AtJSONPointer(String pointer, CBORObject defaultValue)
### ApplyJSONPatch
    public CBORObject ApplyJSONPatch(CBORObject patch)
### equals
    public boolean equals(Object obj)

**Overrides:**

* <code>equals</code> in class <code>Object</code>

### equals
    public boolean equals(CBORObject other)
### GetByteString
    public byte[] GetByteString()
### hashCode
    public int hashCode()

**Overrides:**

* <code>hashCode</code> in class <code>Object</code>

### GetAllTags
    public com.upokecenter.numbers.EInteger[] GetAllTags()
### HasOneTag
    public boolean HasOneTag()
### HasOneTag
    public boolean HasOneTag(int tagValue)
### HasOneTag
    public boolean HasOneTag(com.upokecenter.numbers.EInteger bigTagValue)
### getTagCount
    public final int getTagCount()
### HasMostInnerTag
    public boolean HasMostInnerTag(int tagValue)
### HasMostInnerTag
    public boolean HasMostInnerTag(com.upokecenter.numbers.EInteger bigTagValue)
### HasMostOuterTag
    public boolean HasMostOuterTag(int tagValue)
### HasMostOuterTag
    public boolean HasMostOuterTag(com.upokecenter.numbers.EInteger bigTagValue)
### HasTag
    public boolean HasTag(int tagValue)
### HasTag
    public boolean HasTag(com.upokecenter.numbers.EInteger bigTagValue)
### Insert
    public CBORObject Insert(int index, Object valueOb)
### Clear
    public void Clear()
### Remove
    public boolean Remove(Object obj)
### RemoveAt
    public boolean RemoveAt(int index)
### Remove
    public boolean Remove(CBORObject obj)
### Set
    public CBORObject Set(Object key, Object valueOb)
### ToJSONString
    public String ToJSONString()
### ToJSONString
    public String ToJSONString(JSONOptions options)
### toString
    public String toString()

**Overrides:**

* <code>toString</code> in class <code>Object</code>

### Untag
    public CBORObject Untag()
### UntagOne
    public CBORObject UntagOne()
### WriteJSONTo
    public void WriteJSONTo(OutputStream outputStream) throws IOException

**Throws:**

* <code>IOException</code>

### WriteJSONTo
    public void WriteJSONTo(OutputStream outputStream, JSONOptions options) throws IOException

**Throws:**

* <code>IOException</code>

### FromFloatingPointBits
    public static CBORObject FromFloatingPointBits(long floatingBits, int byteCount)
### WriteFloatingPointBits
    public static int WriteFloatingPointBits(OutputStream outputStream, long floatingBits, int byteCount) throws IOException

**Throws:**

* <code>IOException</code>

### WriteFloatingPointBits
    public static int WriteFloatingPointBits(OutputStream outputStream, long floatingBits, int byteCount, boolean shortestForm) throws IOException

**Throws:**

* <code>IOException</code>

### WriteFloatingPointValue
    public static int WriteFloatingPointValue(OutputStream outputStream, double doubleVal, int byteCount) throws IOException

**Throws:**

* <code>IOException</code>

### WriteFloatingPointValue
    public static int WriteFloatingPointValue(OutputStream outputStream, float singleVal, int byteCount) throws IOException

**Throws:**

* <code>IOException</code>

### WriteValue
    public static int WriteValue(OutputStream outputStream, int majorType, long value) throws IOException

**Throws:**

* <code>IOException</code>

### WriteValue
    public static int WriteValue(OutputStream outputStream, int majorType, int value) throws IOException

**Throws:**

* <code>IOException</code>

### WriteValue
    public static int WriteValue(OutputStream outputStream, int majorType, com.upokecenter.numbers.EInteger bigintValue) throws IOException

**Throws:**

* <code>IOException</code>

### WriteTo
    public void WriteTo(OutputStream stream) throws IOException

**Throws:**

* <code>IOException</code>

### WriteTo
    public void WriteTo(OutputStream stream, CBOREncodeOptions options) throws IOException

**Throws:**

* <code>IOException</code>
