# com.upokecenter.cbor.CBORObject

    public final class CBORObject extends java.lang.Object implements java.lang.Comparable<CBORObject>

## Fields

* `static CBORObject False`<br>
* `static CBORObject NaN`<br>
* `static CBORObject NegativeInfinity`<br>
* `static CBORObject Null`<br>
* `static CBORObject PositiveInfinity`<br>
* `static CBORObject True`<br>
* `static CBORObject Undefined`<br>
* `static CBORObject Zero`<br>

## Methods

* `CBORObject Abs()`<br>
 Deprecated.
Instead, convert this object to a number (with .getAsNumber()()),
 and use that number's.getAbs()() method.
 Instead, convert this object to a number (with .getAsNumber()()),
 and use that number's.getAbs()() method.
* `CBORObject Add​(CBORObject obj)`<br>
* `CBORObject Add​(java.lang.Object obj)`<br>
* `CBORObject Add​(java.lang.Object key,
   java.lang.Object valueOb)`<br>
* `static CBORObject Addition​(CBORObject first,
        CBORObject second)`<br>
 Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Add() method.
 Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Add() method.
* `boolean AsBoolean()`<br>
* `byte AsByte()`<br>
 Deprecated.
Instead, use.getToObject()&lt;byte&gt;() in .NET or
  .getToObject()(Byte.class) in Java.
 Instead, use.getToObject()&lt;byte&gt;() in .NET or
  .getToObject()(Byte.class) in Java.
* `double AsDouble()`<br>
* `long AsDoubleBits()`<br>
* `double AsDoubleValue()`<br>
* `com.upokecenter.numbers.EDecimal AsEDecimal()`<br>
 Deprecated.
Instead, use.getToObject()&lt;PeterO.Numbers.EDecimal&gt;()
 in .NET or
  .getToObject()(com.upokecenter.numbers.EDecimal.class)
 in Java.
 Instead, use.getToObject()&lt;PeterO.Numbers.EDecimal&gt;()
 in .NET or
  .getToObject()(com.upokecenter.numbers.EDecimal.class)
 in Java.
* `com.upokecenter.numbers.EFloat AsEFloat()`<br>
 Deprecated.
Instead, use.getToObject()&lt;PeterO.Numbers.EFloat&gt;() in.getNET()
 or  .getToObject()(com.upokecenter.numbers.EFloat.class)
 in Java.
 Instead, use.getToObject()&lt;PeterO.Numbers.EFloat&gt;() in.getNET()
 or  .getToObject()(com.upokecenter.numbers.EFloat.class)
 in Java.
* `com.upokecenter.numbers.EInteger AsEInteger()`<br>
 Deprecated.
Instead, use.getToObject()&lt;PeterO.Numbers.EInteger&gt;()
 in .NET or
  .getToObject()(com.upokecenter.numbers.EInteger.class)
 in Java.
 Instead, use.getToObject()&lt;PeterO.Numbers.EInteger&gt;()
 in .NET or
  .getToObject()(com.upokecenter.numbers.EInteger.class)
 in Java.
* `com.upokecenter.numbers.EInteger AsEIntegerValue()`<br>
* `com.upokecenter.numbers.ERational AsERational()`<br>
 Deprecated.
Instead, use.getToObject()&lt;PeterO.Numbers.ERational&gt;() in
.NET or.getToObject()(com.upokecenter.numbers.ERational.class)
 in Java.
 Instead, use.getToObject()&lt;PeterO.Numbers.ERational&gt;() in
.NET or.getToObject()(com.upokecenter.numbers.ERational.class)
 in Java.
* `short AsInt16()`<br>
 Deprecated.
Instead, use the following: (cbor.AsNumber().ToInt16Checked()),
 or .getToObject()&lt;short&gt;() in .getNET().
 Instead, use the following: (cbor.AsNumber().ToInt16Checked()),
 or .getToObject()&lt;short&gt;() in .getNET().
* `int AsInt32()`<br>
* `int AsInt32Value()`<br>
* `long AsInt64()`<br>
 Deprecated.
Instead, use the following: (cbor.AsNumber().ToInt64Checked()), or
.ToObject&lt;long&gt;() in.getNET().
 Instead, use the following: (cbor.AsNumber().ToInt64Checked()), or
.ToObject&lt;long&gt;() in.getNET().
* `long AsInt64Value()`<br>
* `CBORNumber AsNumber()`<br>
* `float AsSingle()`<br>
* `java.lang.String AsString()`<br>
* `long CalcEncodedSize()`<br>
* `boolean CanFitInDouble()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().CanFitInDouble()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().CanFitInDouble()).
* `boolean CanFitInInt32()`<br>
 Deprecated.
Instead, use.CanValueFitInInt32(), if the application allows only CBOR
 integers, or (cbor.isNumber()
 &&cbor.AsNumber().CanFitInInt32()),   if the application
 allows any CBOR Object convertible to an integer.
 Instead, use.CanValueFitInInt32(), if the application allows only CBOR
 integers, or (cbor.isNumber()
 &&cbor.AsNumber().CanFitInInt32()),   if the application
 allows any CBOR Object convertible to an integer.
* `boolean CanFitInInt64()`<br>
 Deprecated.
Instead, use CanValueFitInInt64(), if the application allows only CBOR
 integers, or (cbor.isNumber()
 &&cbor.AsNumber().CanFitInInt64()),   if the application
 allows any CBOR Object convertible to an integer.
 Instead, use CanValueFitInInt64(), if the application allows only CBOR
 integers, or (cbor.isNumber()
 &&cbor.AsNumber().CanFitInInt64()),   if the application
 allows any CBOR Object convertible to an integer.
* `boolean CanFitInSingle()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().CanFitInSingle()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().CanFitInSingle()).
* `boolean CanTruncatedIntFitInInt32()`<br>
 Deprecated.
Instead, use the following: (cbor.CanValueFitInInt32() if only
 integers of any tag are allowed, or (cbor.isNumber()
 && cbor.AsNumber().CanTruncatedIntFitInInt32()).
 Instead, use the following: (cbor.CanValueFitInInt32() if only
 integers of any tag are allowed, or (cbor.isNumber()
 && cbor.AsNumber().CanTruncatedIntFitInInt32()).
* `boolean CanTruncatedIntFitInInt64()`<br>
 Deprecated.
Instead, use the following: (cbor.CanValueFitInInt64() if only
 integers of any tag are allowed, or (cbor.isNumber()
 && cbor.AsNumber().CanTruncatedIntFitInInt64()).
 Instead, use the following: (cbor.CanValueFitInInt64() if only
 integers of any tag are allowed, or (cbor.isNumber()
 && cbor.AsNumber().CanTruncatedIntFitInInt64()).
* `boolean CanValueFitInInt32()`<br>
* `boolean CanValueFitInInt64()`<br>
* `void Clear()`<br>
* `int compareTo​(CBORObject other)`<br>
* `int CompareToIgnoreTags​(CBORObject other)`<br>
* `boolean ContainsKey​(CBORObject key)`<br>
* `boolean ContainsKey​(java.lang.Object objKey)`<br>
* `boolean ContainsKey​(java.lang.String key)`<br>
* `static CBORObject DecodeFromBytes​(byte[] data)`<br>
* `static CBORObject DecodeFromBytes​(byte[] data,
               CBOREncodeOptions options)`<br>
* `static CBORObject[] DecodeSequenceFromBytes​(byte[] data)`<br>
* `static CBORObject[] DecodeSequenceFromBytes​(byte[] data,
                       CBOREncodeOptions options)`<br>
* `static CBORObject Divide​(CBORObject first,
      CBORObject second)`<br>
 Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Divide() method.
 Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Divide() method.
* `byte[] EncodeToBytes()`<br>
* `byte[] EncodeToBytes​(CBOREncodeOptions options)`<br>
* `boolean equals​(CBORObject other)`<br>
* `boolean equals​(java.lang.Object obj)`<br>
* `static CBORObject FromFloatingPointBits​(long floatingBits,
                     int byteCount)`<br>
* `static CBORObject FromJSONBytes​(byte[] bytes)`<br>
* `static CBORObject FromJSONBytes​(byte[] bytes,
             int offset,
             int count)`<br>
* `static CBORObject FromJSONBytes​(byte[] bytes,
             int offset,
             int count,
             JSONOptions jsonoptions)`<br>
* `static CBORObject FromJSONBytes​(byte[] bytes,
             JSONOptions jsonoptions)`<br>
* `static CBORObject[] FromJSONSequenceBytes​(byte[] bytes)`<br>
* `static CBORObject[] FromJSONSequenceBytes​(byte[] data,
                     JSONOptions options)`<br>
* `static CBORObject FromJSONString​(java.lang.String str)`<br>
* `static CBORObject FromJSONString​(java.lang.String str,
              int offset,
              int count)`<br>
* `static CBORObject FromJSONString​(java.lang.String str,
              int offset,
              int count,
              JSONOptions jsonoptions)`<br>
* `static CBORObject FromJSONString​(java.lang.String str,
              CBOREncodeOptions options)`<br>
 Deprecated.
Instead, use.getFromJSONString()(str,
 new JSONOptions(\allowduplicatekeys = true\))
 or .getFromJSONString()(str,   new
 JSONOptions(\allowduplicatekeys = false\)), as appropriate.
 Instead, use.getFromJSONString()(str,
 new JSONOptions(\allowduplicatekeys = true\))
 or .getFromJSONString()(str,   new
 JSONOptions(\allowduplicatekeys = false\)), as appropriate.
* `static CBORObject FromJSONString​(java.lang.String str,
              JSONOptions jsonoptions)`<br>
* `static CBORObject FromObject​(boolean value)`<br>
* `static CBORObject FromObject​(byte value)`<br>
* `static CBORObject FromObject​(byte[] bytes)`<br>
* `static CBORObject FromObject​(double value)`<br>
* `static CBORObject FromObject​(float value)`<br>
* `static CBORObject FromObject​(int value)`<br>
* `static CBORObject FromObject​(int[] array)`<br>
* `static CBORObject FromObject​(long value)`<br>
* `static CBORObject FromObject​(long[] array)`<br>
* `static CBORObject FromObject​(short value)`<br>
* `static CBORObject FromObject​(CBORObject value)`<br>
* `static CBORObject FromObject​(CBORObject[] array)`<br>
* `static CBORObject FromObject​(com.upokecenter.numbers.EDecimal bigValue)`<br>
* `static CBORObject FromObject​(com.upokecenter.numbers.EFloat bigValue)`<br>
* `static CBORObject FromObject​(com.upokecenter.numbers.EInteger bigintValue)`<br>
* `static CBORObject FromObject​(com.upokecenter.numbers.ERational bigValue)`<br>
* `static CBORObject FromObject​(java.lang.Object obj)`<br>
* `static CBORObject FromObject​(java.lang.Object obj,
          CBORTypeMapper mapper)`<br>
* `static CBORObject FromObject​(java.lang.Object obj,
          CBORTypeMapper mapper,
          PODOptions options)`<br>
* `static CBORObject FromObject​(java.lang.Object obj,
          PODOptions options)`<br>
* `static CBORObject FromObject​(java.lang.String strValue)`<br>
* `static CBORObject FromObjectAndTag​(java.lang.Object valueObValue,
                int smallTag)`<br>
* `static CBORObject FromObjectAndTag​(java.lang.Object valueOb,
                com.upokecenter.numbers.EInteger bigintTag)`<br>
* `static CBORObject FromSimpleValue​(int simpleValue)`<br>
* `CBORObject get​(int index)`<br>
* `CBORObject get​(CBORObject key)`<br>
* `CBORObject get​(java.lang.String key)`<br>
* `com.upokecenter.numbers.EInteger[] GetAllTags()`<br>
* `byte[] GetByteString()`<br>
* `java.util.Collection<java.util.Map.Entry<CBORObject,​CBORObject>> getEntries()`<br>
* `java.util.Collection<CBORObject> getKeys()`<br>
* `com.upokecenter.numbers.EInteger getMostInnerTag()`<br>
* `com.upokecenter.numbers.EInteger getMostOuterTag()`<br>
* `CBORObject GetOrDefault​(java.lang.Object key,
            CBORObject defaultValue)`<br>
* `int getSimpleValue()`<br>
* `int getTagCount()`<br>
* `CBORType getType()`<br>
* `java.util.Collection<CBORObject> getValues()`<br>
* `int hashCode()`<br>
* `boolean HasMostInnerTag​(int tagValue)`<br>
* `boolean HasMostInnerTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
* `boolean HasMostOuterTag​(int tagValue)`<br>
* `boolean HasMostOuterTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
* `boolean HasOneTag()`<br>
* `boolean HasOneTag​(int tagValue)`<br>
* `boolean HasOneTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
* `boolean HasTag​(int tagValue)`<br>
* `boolean HasTag​(com.upokecenter.numbers.EInteger bigTagValue)`<br>
* `CBORObject Insert​(int index,
      java.lang.Object valueOb)`<br>
* `boolean isFalse()`<br>
* `boolean isFinite()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsFinite()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsFinite()).
* `boolean IsInfinity()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsInfinity()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsInfinity()).
* `boolean isIntegral()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsInteger()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsInteger()).
* `boolean IsNaN()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsNaN()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsNaN()).
* `boolean isNegative()`<br>
 Deprecated.
Instead, use (cbor.IsNumber()
 && cbor.AsNumber().IsNegative()).
 Instead, use (cbor.IsNumber()
 && cbor.AsNumber().IsNegative()).
* `boolean IsNegativeInfinity()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsNegativeInfinity()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsNegativeInfinity()).
* `boolean isNull()`<br>
* `boolean isNumber()`<br>
* `boolean IsPositiveInfinity()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsPositiveInfinity()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsPositiveInfinity()).
* `boolean isTagged()`<br>
* `boolean isTrue()`<br>
* `boolean isUndefined()`<br>
* `boolean isZero()`<br>
 Deprecated.
Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsZero()).
 Instead, use the following: (cbor.isNumber()
 && cbor.AsNumber().IsZero()).
* `static CBORObject Multiply​(CBORObject first,
        CBORObject second)`<br>
 Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Multiply() method.
 Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Multiply() method.
* `CBORObject Negate()`<br>
 Deprecated.
Instead, convert this object to a number (with .AsNumber()), and
 use that number's.Negate() method.
 Instead, convert this object to a number (with .AsNumber()), and
 use that number's.Negate() method.
* `static CBORObject NewArray()`<br>
* `static CBORObject NewMap()`<br>
* `static CBORObject Read​(java.io.InputStream stream)`<br>
* `static CBORObject Read​(java.io.InputStream stream,
    CBOREncodeOptions options)`<br>
* `static CBORObject ReadJSON​(java.io.InputStream stream)`<br>
* `static CBORObject ReadJSON​(java.io.InputStream stream,
        CBOREncodeOptions options)`<br>
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
* `static CBORObject ReadJSON​(java.io.InputStream stream,
        JSONOptions jsonoptions)`<br>
* `static CBORObject[] ReadJSONSequence​(java.io.InputStream stream)`<br>
* `static CBORObject[] ReadJSONSequence​(java.io.InputStream stream,
                JSONOptions jsonoptions)`<br>
* `static CBORObject[] ReadSequence​(java.io.InputStream stream)`<br>
* `static CBORObject[] ReadSequence​(java.io.InputStream stream,
            CBOREncodeOptions options)`<br>
* `static CBORObject Remainder​(CBORObject first,
         CBORObject second)`<br>
 Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Remainder() method.
 Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Remainder() method.
* `boolean Remove​(CBORObject obj)`<br>
* `boolean Remove​(java.lang.Object obj)`<br>
* `boolean RemoveAt​(int index)`<br>
* `void set​(int index,
   CBORObject value)`<br>
* `void set​(CBORObject key,
   CBORObject value)`<br>
* `void set​(java.lang.String key,
   CBORObject value)`<br>
* `CBORObject Set​(java.lang.Object key,
   java.lang.Object valueOb)`<br>
* `int signum()`<br>
 Deprecated.
Instead, convert this object to a number with.AsNumber(),   and use the
 Sign property in.NET or the signum method in Java.
 Instead, convert this object to a number with.AsNumber(),   and use the
 Sign property in.NET or the signum method in Java.
* `int size()`<br>
* `static CBORObject Subtract​(CBORObject first,
        CBORObject second)`<br>
 Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Subtract() method.
 Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Subtract() method.
* `byte[] ToJSONBytes()`<br>
* `byte[] ToJSONBytes​(JSONOptions jsonoptions)`<br>
* `java.lang.String ToJSONString()`<br>
* `java.lang.String ToJSONString​(JSONOptions options)`<br>
* `<T> T ToObject​(java.lang.reflect.Type t)`<br>
* `<T> T ToObject​(java.lang.reflect.Type t,
        CBORTypeMapper mapper)`<br>
* `<T> T ToObject​(java.lang.reflect.Type t,
        CBORTypeMapper mapper,
        PODOptions options)`<br>
* `<T> T ToObject​(java.lang.reflect.Type t,
        PODOptions options)`<br>
* `java.lang.String toString()`<br>
* `CBORObject Untag()`<br>
* `CBORObject UntagOne()`<br>
* `CBORObject WithTag​(int smallTag)`<br>
* `CBORObject WithTag​(com.upokecenter.numbers.EInteger bigintTag)`<br>
* `static void Write​(boolean value,
     java.io.OutputStream stream)`<br>
* `static void Write​(byte value,
     java.io.OutputStream stream)`<br>
* `static void Write​(double value,
     java.io.OutputStream stream)`<br>
* `static void Write​(float value,
     java.io.OutputStream stream)`<br>
* `static void Write​(int value,
     java.io.OutputStream stream)`<br>
* `static void Write​(long value,
     java.io.OutputStream stream)`<br>
* `static void Write​(short value,
     java.io.OutputStream stream)`<br>
* `static void Write​(CBORObject value,
     java.io.OutputStream stream)`<br>
* `static void Write​(com.upokecenter.numbers.EDecimal bignum,
     java.io.OutputStream stream)`<br>
* `static void Write​(com.upokecenter.numbers.EFloat bignum,
     java.io.OutputStream stream)`<br>
* `static void Write​(com.upokecenter.numbers.EInteger bigint,
     java.io.OutputStream stream)`<br>
* `static void Write​(com.upokecenter.numbers.ERational rational,
     java.io.OutputStream stream)`<br>
* `static void Write​(java.lang.Object objValue,
     java.io.OutputStream stream)`<br>
* `static void Write​(java.lang.Object objValue,
     java.io.OutputStream output,
     CBOREncodeOptions options)`<br>
* `static void Write​(java.lang.String str,
     java.io.OutputStream stream)`<br>
* `static void Write​(java.lang.String str,
     java.io.OutputStream stream,
     CBOREncodeOptions options)`<br>
* `static int WriteFloatingPointBits​(java.io.OutputStream outputStream,
                      long floatingBits,
                      int byteCount)`<br>
* `static int WriteFloatingPointValue​(java.io.OutputStream outputStream,
                       double doubleVal,
                       int byteCount)`<br>
* `static int WriteFloatingPointValue​(java.io.OutputStream outputStream,
                       float singleVal,
                       int byteCount)`<br>
* `static void WriteJSON​(java.lang.Object obj,
         java.io.OutputStream outputStream)`<br>
* `void WriteJSONTo​(java.io.OutputStream outputStream)`<br>
* `void WriteJSONTo​(java.io.OutputStream outputStream,
           JSONOptions options)`<br>
* `void WriteTo​(java.io.OutputStream stream)`<br>
* `void WriteTo​(java.io.OutputStream stream,
       CBOREncodeOptions options)`<br>
* `static int WriteValue​(java.io.OutputStream outputStream,
          int majorType,
          int value)`<br>
* `static int WriteValue​(java.io.OutputStream outputStream,
          int majorType,
          long value)`<br>
* `static int WriteValue​(java.io.OutputStream outputStream,
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
### isFinite
    @Deprecated public final boolean isFinite()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().IsFinite()).

### isIntegral
    @Deprecated public final boolean isIntegral()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().IsInteger()).

### isNull
    public final boolean isNull()
### isTagged
    public final boolean isTagged()
### isTrue
    public final boolean isTrue()
### isUndefined
    public final boolean isUndefined()
### isZero
    @Deprecated public final boolean isZero()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().IsZero()).

### getKeys
    public final java.util.Collection<CBORObject> getKeys()
### isNegative
    @Deprecated public final boolean isNegative()
Deprecated.
Instead, use (cbor.IsNumber()
 &amp;&amp; cbor.AsNumber().IsNegative()).

### getMostOuterTag
    public final com.upokecenter.numbers.EInteger getMostOuterTag()
### signum
    @Deprecated public final int signum()
Deprecated.
Instead, convert this object to a number with.AsNumber(),   and use the
 Sign property in.NET or the signum method in Java. Either will
 treat not-a-number (NaN) values differently than here.

### getSimpleValue
    public final int getSimpleValue()
### isNumber
    public final boolean isNumber()
### getType
    public final CBORType getType()
### getEntries
    public final java.util.Collection<java.util.Map.Entry<CBORObject,​CBORObject>> getEntries()
### getValues
    public final java.util.Collection<CBORObject> getValues()
### get
    public CBORObject get​(int index)
### set
    public void set​(int index, CBORObject value)
### GetOrDefault
    public CBORObject GetOrDefault​(java.lang.Object key, CBORObject defaultValue)
### get
    public CBORObject get​(CBORObject key)
### set
    public void set​(CBORObject key, CBORObject value)
### get
    public CBORObject get​(java.lang.String key)
### set
    public void set​(java.lang.String key, CBORObject value)
### Addition
    @Deprecated public static CBORObject Addition​(CBORObject first, CBORObject second)
Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Add() method.

### DecodeFromBytes
    public static CBORObject DecodeFromBytes​(byte[] data)
### DecodeSequenceFromBytes
    public static CBORObject[] DecodeSequenceFromBytes​(byte[] data)
### DecodeSequenceFromBytes
    public static CBORObject[] DecodeSequenceFromBytes​(byte[] data, CBOREncodeOptions options)
### FromJSONSequenceBytes
    public static CBORObject[] FromJSONSequenceBytes​(byte[] bytes)
### ToJSONBytes
    public byte[] ToJSONBytes()
### ToJSONBytes
    public byte[] ToJSONBytes​(JSONOptions jsonoptions)
### FromJSONSequenceBytes
    public static CBORObject[] FromJSONSequenceBytes​(byte[] data, JSONOptions options)
### DecodeFromBytes
    public static CBORObject DecodeFromBytes​(byte[] data, CBOREncodeOptions options)
### Divide
    @Deprecated public static CBORObject Divide​(CBORObject first, CBORObject second)
Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Divide() method.

### FromJSONString
    public static CBORObject FromJSONString​(java.lang.String str, int offset, int count)
### FromJSONString
    public static CBORObject FromJSONString​(java.lang.String str, JSONOptions jsonoptions)
### FromJSONString
    public static CBORObject FromJSONString​(java.lang.String str)
### FromJSONString
    @Deprecated public static CBORObject FromJSONString​(java.lang.String str, CBOREncodeOptions options)
Deprecated.
Instead, use.getFromJSONString()(str,
 new JSONOptions(\allowduplicatekeys = true\))
 or .getFromJSONString()(str,   new
 JSONOptions(\allowduplicatekeys = false\)), as appropriate.

### FromJSONString
    public static CBORObject FromJSONString​(java.lang.String str, int offset, int count, JSONOptions jsonoptions)
### ToObject
    public <T> T ToObject​(java.lang.reflect.Type t)
### ToObject
    public <T> T ToObject​(java.lang.reflect.Type t, CBORTypeMapper mapper)
### ToObject
    public <T> T ToObject​(java.lang.reflect.Type t, PODOptions options)
### ToObject
    public <T> T ToObject​(java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions options)
### FromObject
    public static CBORObject FromObject​(long value)
### FromObject
    public static CBORObject FromObject​(CBORObject value)
### CalcEncodedSize
    public long CalcEncodedSize()
### FromObject
    public static CBORObject FromObject​(com.upokecenter.numbers.EInteger bigintValue)
### FromObject
    public static CBORObject FromObject​(com.upokecenter.numbers.EFloat bigValue)
### FromObject
    public static CBORObject FromObject​(com.upokecenter.numbers.ERational bigValue)
### FromObject
    public static CBORObject FromObject​(com.upokecenter.numbers.EDecimal bigValue)
### FromObject
    public static CBORObject FromObject​(java.lang.String strValue)
### FromObject
    public static CBORObject FromObject​(int value)
### FromObject
    public static CBORObject FromObject​(short value)
### FromObject
    public static CBORObject FromObject​(boolean value)
### FromObject
    public static CBORObject FromObject​(byte value)
### FromObject
    public static CBORObject FromObject​(float value)
### FromObject
    public static CBORObject FromObject​(double value)
### FromObject
    public static CBORObject FromObject​(byte[] bytes)
### FromObject
    public static CBORObject FromObject​(CBORObject[] array)
### FromObject
    public static CBORObject FromObject​(int[] array)
### FromObject
    public static CBORObject FromObject​(long[] array)
### FromObject
    public static CBORObject FromObject​(java.lang.Object obj)
### FromObject
    public static CBORObject FromObject​(java.lang.Object obj, PODOptions options)
### FromObject
    public static CBORObject FromObject​(java.lang.Object obj, CBORTypeMapper mapper)
### FromObject
    public static CBORObject FromObject​(java.lang.Object obj, CBORTypeMapper mapper, PODOptions options)
### WithTag
    public CBORObject WithTag​(com.upokecenter.numbers.EInteger bigintTag)
### FromObjectAndTag
    public static CBORObject FromObjectAndTag​(java.lang.Object valueOb, com.upokecenter.numbers.EInteger bigintTag)
### WithTag
    public CBORObject WithTag​(int smallTag)
### FromObjectAndTag
    public static CBORObject FromObjectAndTag​(java.lang.Object valueObValue, int smallTag)
### FromSimpleValue
    public static CBORObject FromSimpleValue​(int simpleValue)
### Multiply
    @Deprecated public static CBORObject Multiply​(CBORObject first, CBORObject second)
Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Multiply() method.

### NewArray
    public static CBORObject NewArray()
### NewMap
    public static CBORObject NewMap()
### ReadSequence
    public static CBORObject[] ReadSequence​(java.io.InputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### ReadSequence
    public static CBORObject[] ReadSequence​(java.io.InputStream stream, CBOREncodeOptions options) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Read
    public static CBORObject Read​(java.io.InputStream stream)
### Read
    public static CBORObject Read​(java.io.InputStream stream, CBOREncodeOptions options)
### ReadJSON
    public static CBORObject ReadJSON​(java.io.InputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### ReadJSONSequence
    public static CBORObject[] ReadJSONSequence​(java.io.InputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### ReadJSON
    @Deprecated public static CBORObject ReadJSON​(java.io.InputStream stream, CBOREncodeOptions options) throws java.io.IOException
Deprecated.
Instead, use.getReadJSON()(stream,
 new JSONOptions(\allowduplicatekeys = true\))
 or .getReadJSON()(stream,
 new JSONOptions(\allowduplicatekeys = false\)),
 as appropriate.

**Throws:**

* <code>java.io.IOException</code>

### ReadJSONSequence
    public static CBORObject[] ReadJSONSequence​(java.io.InputStream stream, JSONOptions jsonoptions) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### ReadJSON
    public static CBORObject ReadJSON​(java.io.InputStream stream, JSONOptions jsonoptions) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### FromJSONBytes
    public static CBORObject FromJSONBytes​(byte[] bytes)
### FromJSONBytes
    public static CBORObject FromJSONBytes​(byte[] bytes, JSONOptions jsonoptions)
### FromJSONBytes
    public static CBORObject FromJSONBytes​(byte[] bytes, int offset, int count)
### FromJSONBytes
    public static CBORObject FromJSONBytes​(byte[] bytes, int offset, int count, JSONOptions jsonoptions)
### Remainder
    @Deprecated public static CBORObject Remainder​(CBORObject first, CBORObject second)
Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Remainder() method.

### Subtract
    @Deprecated public static CBORObject Subtract​(CBORObject first, CBORObject second)
Deprecated.
Instead, convert both CBOR objects to numbers (with .AsNumber()), and
 use the first number's.Subtract() method.

### Write
    public static void Write​(java.lang.String str, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(java.lang.String str, java.io.OutputStream stream, CBOREncodeOptions options) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(com.upokecenter.numbers.EFloat bignum, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(com.upokecenter.numbers.ERational rational, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(com.upokecenter.numbers.EDecimal bignum, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(com.upokecenter.numbers.EInteger bigint, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(long value, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(int value, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(short value, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(boolean value, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(byte value, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(float value, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(double value, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(CBORObject value, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(java.lang.Object objValue, java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Write
    public static void Write​(java.lang.Object objValue, java.io.OutputStream output, CBOREncodeOptions options) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteJSON
    public static void WriteJSON​(java.lang.Object obj, java.io.OutputStream outputStream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### Abs
    @Deprecated public CBORObject Abs()
Deprecated.
Instead, convert this object to a number (with .getAsNumber()()),
 and use that number's.getAbs()() method.

### Add
    public CBORObject Add​(java.lang.Object key, java.lang.Object valueOb)
### Add
    public CBORObject Add​(CBORObject obj)
### Add
    public CBORObject Add​(java.lang.Object obj)
### AsEInteger
    @Deprecated public com.upokecenter.numbers.EInteger AsEInteger()
Deprecated.
Instead, use.getToObject()&amp;lt;PeterO.Numbers.EInteger&amp;gt;()
 in .NET or
  .getToObject()(com.upokecenter.numbers.EInteger.class)
 in Java.

### AsBoolean
    public boolean AsBoolean()
### AsByte
    @Deprecated public byte AsByte()
Deprecated.
Instead, use.getToObject()&amp;lt;byte&amp;gt;() in .NET or
  .getToObject()(Byte.class) in Java.

### AsDouble
    public double AsDouble()
### AsEDecimal
    @Deprecated public com.upokecenter.numbers.EDecimal AsEDecimal()
Deprecated.
Instead, use.getToObject()&amp;lt;PeterO.Numbers.EDecimal&amp;gt;()
 in .NET or
  .getToObject()(com.upokecenter.numbers.EDecimal.class)
 in Java.

### AsEFloat
    @Deprecated public com.upokecenter.numbers.EFloat AsEFloat()
Deprecated.
Instead, use.getToObject()&amp;lt;PeterO.Numbers.EFloat&amp;gt;() in.getNET()
 or  .getToObject()(com.upokecenter.numbers.EFloat.class)
 in Java.

### AsERational
    @Deprecated public com.upokecenter.numbers.ERational AsERational()
Deprecated.
Instead, use.getToObject()&amp;lt;PeterO.Numbers.ERational&amp;gt;() in
.NET or.getToObject()(com.upokecenter.numbers.ERational.class)
 in Java.

### AsInt16
    @Deprecated public short AsInt16()
Deprecated.
Instead, use the following: (cbor.AsNumber().ToInt16Checked()),
 or .getToObject()&amp;lt;short&amp;gt;() in .getNET().

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
### AsInt64
    @Deprecated public long AsInt64()
Deprecated.
Instead, use the following: (cbor.AsNumber().ToInt64Checked()), or
.ToObject&amp;lt;long&amp;gt;() in.getNET().

### AsSingle
    public float AsSingle()
### AsString
    public java.lang.String AsString()
### CanFitInDouble
    @Deprecated public boolean CanFitInDouble()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().CanFitInDouble()).

### CanFitInInt32
    @Deprecated public boolean CanFitInInt32()
Deprecated.
Instead, use.CanValueFitInInt32(), if the application allows only CBOR
 integers, or (cbor.isNumber()
 &amp;&amp;cbor.AsNumber().CanFitInInt32()),   if the application
 allows any CBOR Object convertible to an integer.

### CanFitInInt64
    @Deprecated public boolean CanFitInInt64()
Deprecated.
Instead, use CanValueFitInInt64(), if the application allows only CBOR
 integers, or (cbor.isNumber()
 &amp;&amp;cbor.AsNumber().CanFitInInt64()),   if the application
 allows any CBOR Object convertible to an integer.

### CanFitInSingle
    @Deprecated public boolean CanFitInSingle()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().CanFitInSingle()).

### CanTruncatedIntFitInInt32
    @Deprecated public boolean CanTruncatedIntFitInInt32()
Deprecated.
Instead, use the following: (cbor.CanValueFitInInt32() if only
 integers of any tag are allowed, or (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().CanTruncatedIntFitInInt32()).

### CanTruncatedIntFitInInt64
    @Deprecated public boolean CanTruncatedIntFitInInt64()
Deprecated.
Instead, use the following: (cbor.CanValueFitInInt64() if only
 integers of any tag are allowed, or (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().CanTruncatedIntFitInInt64()).

### compareTo
    public int compareTo​(CBORObject other)

**Specified by:**

* <code>compareTo</code> in interface <code>java.lang.Comparable&lt;CBORObject&gt;</code>

### CompareToIgnoreTags
    public int CompareToIgnoreTags​(CBORObject other)
### ContainsKey
    public boolean ContainsKey​(java.lang.Object objKey)
### ContainsKey
    public boolean ContainsKey​(CBORObject key)
### ContainsKey
    public boolean ContainsKey​(java.lang.String key)
### EncodeToBytes
    public byte[] EncodeToBytes()
### EncodeToBytes
    public byte[] EncodeToBytes​(CBOREncodeOptions options)
### equals
    public boolean equals​(java.lang.Object obj)

**Overrides:**

* <code>equals</code> in class <code>java.lang.Object</code>

### equals
    public boolean equals​(CBORObject other)
### GetByteString
    public byte[] GetByteString()
### hashCode
    public int hashCode()

**Overrides:**

* <code>hashCode</code> in class <code>java.lang.Object</code>

### GetAllTags
    public com.upokecenter.numbers.EInteger[] GetAllTags()
### HasOneTag
    public boolean HasOneTag()
### HasOneTag
    public boolean HasOneTag​(int tagValue)
### HasOneTag
    public boolean HasOneTag​(com.upokecenter.numbers.EInteger bigTagValue)
### getTagCount
    public final int getTagCount()
### HasMostInnerTag
    public boolean HasMostInnerTag​(int tagValue)
### HasMostInnerTag
    public boolean HasMostInnerTag​(com.upokecenter.numbers.EInteger bigTagValue)
### HasMostOuterTag
    public boolean HasMostOuterTag​(int tagValue)
### HasMostOuterTag
    public boolean HasMostOuterTag​(com.upokecenter.numbers.EInteger bigTagValue)
### HasTag
    public boolean HasTag​(int tagValue)
### HasTag
    public boolean HasTag​(com.upokecenter.numbers.EInteger bigTagValue)
### Insert
    public CBORObject Insert​(int index, java.lang.Object valueOb)
### IsInfinity
    @Deprecated public boolean IsInfinity()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().IsInfinity()).

### IsNaN
    @Deprecated public boolean IsNaN()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().IsNaN()).

### IsNegativeInfinity
    @Deprecated public boolean IsNegativeInfinity()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().IsNegativeInfinity()).

### IsPositiveInfinity
    @Deprecated public boolean IsPositiveInfinity()
Deprecated.
Instead, use the following: (cbor.isNumber()
 &amp;&amp; cbor.AsNumber().IsPositiveInfinity()).

### Negate
    @Deprecated public CBORObject Negate()
Deprecated.
Instead, convert this object to a number (with .AsNumber()), and
 use that number's.Negate() method.

### Clear
    public void Clear()
### Remove
    public boolean Remove​(java.lang.Object obj)
### RemoveAt
    public boolean RemoveAt​(int index)
### Remove
    public boolean Remove​(CBORObject obj)
### Set
    public CBORObject Set​(java.lang.Object key, java.lang.Object valueOb)
### ToJSONString
    public java.lang.String ToJSONString()
### ToJSONString
    public java.lang.String ToJSONString​(JSONOptions options)
### toString
    public java.lang.String toString()

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

### Untag
    public CBORObject Untag()
### UntagOne
    public CBORObject UntagOne()
### WriteJSONTo
    public void WriteJSONTo​(java.io.OutputStream outputStream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteJSONTo
    public void WriteJSONTo​(java.io.OutputStream outputStream, JSONOptions options) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### FromFloatingPointBits
    public static CBORObject FromFloatingPointBits​(long floatingBits, int byteCount)
### WriteFloatingPointBits
    public static int WriteFloatingPointBits​(java.io.OutputStream outputStream, long floatingBits, int byteCount) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteFloatingPointValue
    public static int WriteFloatingPointValue​(java.io.OutputStream outputStream, double doubleVal, int byteCount) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteFloatingPointValue
    public static int WriteFloatingPointValue​(java.io.OutputStream outputStream, float singleVal, int byteCount) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteValue
    public static int WriteValue​(java.io.OutputStream outputStream, int majorType, long value) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteValue
    public static int WriteValue​(java.io.OutputStream outputStream, int majorType, int value) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteValue
    public static int WriteValue​(java.io.OutputStream outputStream, int majorType, com.upokecenter.numbers.EInteger bigintValue) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteTo
    public void WriteTo​(java.io.OutputStream stream) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>

### WriteTo
    public void WriteTo​(java.io.OutputStream stream, CBOREncodeOptions options) throws java.io.IOException

**Throws:**

* <code>java.io.IOException</code>
