package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import java.util.*;
import java.io.*;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

// TODO: Add ReadObject that combines Read and ToObject; similarly
// for ReadJSON, FromJSONString, FromJSONBytes
// TODO: In Java version add overloads for Class<T> in overloads
// that take java.lang.reflect.Type

  public final class CBORObject implements Comparable<CBORObject>
  {
    private static CBORObject ConstructSimpleValue(int v) {
      return new CBORObject(CBORObjectTypeSimpleValue, v);
    }

    private static CBORObject ConstructIntegerValue(int v) {
      return new CBORObject(CBORObjectTypeInteger, (long)v);
    }

    public static final CBORObject False =
      CBORObject.ConstructSimpleValue(20);

    public static final CBORObject NaN = CBORObject.FromObject(Double.NaN);

    public static final CBORObject NegativeInfinity =
      CBORObject.FromObject(Double.NEGATIVE_INFINITY);

    public static final CBORObject Null =
      CBORObject.ConstructSimpleValue(22);

    public static final CBORObject PositiveInfinity =
      CBORObject.FromObject(Double.POSITIVE_INFINITY);

    public static final CBORObject True =
      CBORObject.ConstructSimpleValue(21);

    public static final CBORObject Undefined =
      CBORObject.ConstructSimpleValue(23);

    public static final CBORObject Zero =
      CBORObject.ConstructIntegerValue(0);

    private static final int CBORObjectTypeInteger = 0; // -(2^63).. (2^63-1)
    private static final int CBORObjectTypeEInteger = 1; // all other integers
    private static final int CBORObjectTypeByteString = 2;
    private static final int CBORObjectTypeTextString = 3;
    private static final int CBORObjectTypeArray = 4;
    private static final int CBORObjectTypeMap = 5;
    private static final int CBORObjectTypeTagged = 6;
    private static final int CBORObjectTypeSimpleValue = 7;
    private static final int CBORObjectTypeDouble = 8;
    private static final int CBORObjectTypeTextStringUtf8 = 9;
    private static final int CBORObjectTypeTextStringAscii = 10;

    private static final int StreamedStringBufferLength = 4096;

    private static final EInteger UInt64MaxValue =
      (EInteger.FromInt32(1).ShiftLeft(64)).Subtract(EInteger.FromInt64(1));

    private static final EInteger[] ValueEmptyTags = new EInteger[0];
    // Expected lengths for each head byte.
    // 0 means length varies. -1 means invalid.
    private static final int[] ValueExpectedLengths = {
      1, 1, 1, 1, 1, 1,
      1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, // major type 0
      1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 5, 9, -1, -1, -1, -1,
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // major type 1
      1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 5, 9, -1, -1, -1, -1,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, // major type 2
      17, 18, 19, 20, 21, 22, 23, 24, 0, 0, 0, 0, -1, -1, -1, 0,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, // major type 3
      17, 18, 19, 20, 21, 22, 23, 24, 0, 0, 0, 0, -1, -1, -1, 0,
      1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // major type 4
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0,
      1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // major type 5
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // major type 6
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1,
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // major type 7
      1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 5, 9, -1, -1, -1, -1,
    };

    private static final byte[] ValueFalseBytes = {
      0x66, 0x61, 0x6c,
      0x73, 0x65,
     };

    private static final byte[] ValueNullBytes = { 0x6e, 0x75, 0x6c, 0x6c };

    private static final byte[] ValueTrueBytes = { 0x74, 0x72, 0x75, 0x65 };

    private static final CBORObject[] FixedObjects =
      InitializeFixedObjects();

    private final int itemtypeValue;
    private final Object itemValue;
    private final int tagHigh;
    private final int tagLow;

    CBORObject(CBORObject obj, int tagLow, int tagHigh) {
      this.itemtypeValue = CBORObjectTypeTagged;
      this.itemValue = obj;
      this.tagLow = tagLow;
      this.tagHigh = tagHigh;
    }

    CBORObject(int type, Object item) {
      this.itemtypeValue = type;
      this.itemValue = item;
      this.tagLow = 0;
      this.tagHigh = 0;
    }

    public final int size() { return (this.getType() == CBORType.Array) ? this.AsList().size() :
          ((this.getType() == CBORType.Map) ? this.AsMap().size() : 0); }

    public final EInteger getMostInnerTag() {
        if (!this.isTagged()) {
          return EInteger.FromInt32(-1);
        }
        CBORObject previtem = this;
        CBORObject curitem = (CBORObject)this.itemValue;
        while (curitem.isTagged()) {
          previtem = curitem;
          curitem = (CBORObject)curitem.itemValue;
        }
        return previtem.tagHigh == 0 && previtem.tagLow >= 0 &&
          previtem.tagLow < 0x10000 ? EInteger.FromInt64(previtem.tagLow) :
          LowHighToEInteger(
            previtem.tagLow,
            previtem.tagHigh);
      }

    public final boolean isFalse() { return this.getItemType() == CBORObjectTypeSimpleValue &&
((Integer)this.getThisItem()).intValue()
          == 20; }

    public final boolean isNull() { return this.getItemType() == CBORObjectTypeSimpleValue &&
((Integer)this.getThisItem()).intValue()
          == 22; }

    public final boolean isTagged() { return this.itemtypeValue == CBORObjectTypeTagged; }

    public final boolean isTrue() { return this.getItemType() == CBORObjectTypeSimpleValue &&
((Integer)this.getThisItem()).intValue()
          == 21; }

    public final boolean isUndefined() { return this.getItemType() == CBORObjectTypeSimpleValue &&
((Integer)this.getThisItem()).intValue()
          == 23; }

    public final Collection<CBORObject> getKeys() {
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> dict = this.AsMap();
          return PropertyMap.ReadOnlyKeys(dict);
        }
        throw new IllegalStateException("Not a map");
      }

    public final EInteger getMostOuterTag() { return !this.isTagged() ?
          EInteger.FromInt32(-1) : this.tagHigh == 0 &&
          this.tagLow >= 0 && this.tagLow < 0x10000 ?
          EInteger.FromInt32(this.tagLow) : LowHighToEInteger(
            this.tagLow,
            this.tagHigh); }

    public final int getSimpleValue() { return (this.getItemType() == CBORObjectTypeSimpleValue) ?
          (((Integer)this.getThisItem()).intValue()) : -1; }

    public final boolean isNumber() { return CBORNumber.IsNumber(this); }

    public final CBORType getType() {
        switch (this.getItemType()) {
          case CBORObjectTypeInteger:
          case CBORObjectTypeEInteger:
            return CBORType.Integer;
          case CBORObjectTypeDouble:
            return CBORType.FloatingPoint;
          case CBORObjectTypeSimpleValue:
            return (((Integer)this.getThisItem()).intValue() == 21 || ((Integer)this.getThisItem()).intValue() == 20) ?
              CBORType.Boolean : CBORType.SimpleValue;
          case CBORObjectTypeArray:
            return CBORType.Array;
          case CBORObjectTypeMap:
            return CBORType.Map;
          case CBORObjectTypeByteString:
            return CBORType.ByteString;
          case CBORObjectTypeTextString:
          case CBORObjectTypeTextStringUtf8:
          case CBORObjectTypeTextStringAscii:
            return CBORType.TextString;
          default: throw new IllegalStateException("Unexpected data type");
        }
      }

    public final Collection<Map.Entry<CBORObject, CBORObject>> getEntries() {
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> dict = this.AsMap();
          return PropertyMap.GetEntries(dict);
        }
        throw new IllegalStateException("Not a map");
      }

    public final Collection<CBORObject> getValues() {
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> dict = this.AsMap();
          return PropertyMap.ReadOnlyValues(dict);
        }
        if (this.getType() == CBORType.Array) {
          List<CBORObject> list = this.AsList();
          return java.util.Collections.unmodifiableList(
              list);
        }
        throw new IllegalStateException("Not a map or array");
      }

    private final int getItemType() {
        CBORObject curobject = this;
        while (curobject.itemtypeValue == CBORObjectTypeTagged) {
          curobject = (CBORObject)curobject.itemValue;
        }
        return curobject.itemtypeValue;
      }

    private final Object getThisItem() {
        CBORObject curobject = this;
        while (curobject.itemtypeValue == CBORObjectTypeTagged) {
          curobject = (CBORObject)curobject.itemValue;
        }
        return curobject.itemValue;
      }

    public CBORObject get(int index) {
        if (this.getType() == CBORType.Array) {
          List<CBORObject> list = this.AsList();
          if (index < 0 || index >= list.size()) {
 throw new IllegalArgumentException("index");
}
 return list.get(index);
        }
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> map = this.AsMap();
          CBORObject key = CBORObject.FromObject(index);
          // TODO: In next major version, consider throwing an exception
          // instead if key does not exist.
          return PropertyMap.GetOrDefault(map, key, null);
        }
        throw new IllegalStateException("Not an array or map");
      }
public void set(int index, CBORObject value) {
        if (this.getType() == CBORType.Array) {
          if (value == null) {
            throw new NullPointerException("value");
          }
          List<CBORObject> list = this.AsList();
          if (index < 0 || index >= list.size()) {
            throw new IllegalArgumentException("index");
          }
          list.set(index, value);
        } else if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> map = this.AsMap();
          CBORObject key = CBORObject.FromObject(index);
          map.put(key, value);
        } else {
          throw new IllegalStateException("Not an array or map");
        }
      }

    public CBORObject GetOrDefault(Object key, CBORObject defaultValue) {
      if (this.getType() == CBORType.Array) {
        int index;
        if (key instanceof Integer) {
          index = ((Integer)key).intValue();
        } else {
          CBORObject cborkey = CBORObject.FromObject(key);
          if (!cborkey.isNumber() || !cborkey.AsNumber().CanFitInInt32()) {
            return defaultValue;
          }
          index = cborkey.AsNumber().ToInt32Checked();
        }
        List<CBORObject> list = this.AsList();
        return (index < 0 || index >= list.size()) ? defaultValue :
          list.get(index);
      }
      if (this.getType() == CBORType.Map) {
        Map<CBORObject, CBORObject> map = this.AsMap();
        CBORObject ckey = CBORObject.FromObject(key);
        return PropertyMap.GetOrDefault(map, ckey, defaultValue);
      }
      return defaultValue;
    }

    public CBORObject get(CBORObject key) {
        /* "The CBORObject class represents a logical data store." +
        " Also, an object indexer is not included here because it's unusual
        for " +
        "CBOR map keys to be anything other than text strings or integers; " +
        "including an object indexer would introduce the security issues
        present in the FromObject method because of the need to convert to
        CBORObject;" +
        " and this CBORObject indexer is included here because any CBOR
        Object " +
        "can serve as a map key, not just integers or text strings." */
        if (key == null) {
          throw new NullPointerException("key");
        }
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> map = this.AsMap();
          return PropertyMap.GetOrDefault(map, key, null);
        }
        if (this.getType() == CBORType.Array) {
          if (!key.isNumber() || !key.AsNumber().IsInteger()) {
            throw new IllegalArgumentException("Not an integer");
          }
          if (!key.AsNumber().CanFitInInt32()) {
            throw new IllegalArgumentException("key");
          }
          List<CBORObject> list = this.AsList();
          int index = key.AsNumber().ToInt32Checked();
          if (index < 0 || index >= list.size()) {
 throw new IllegalArgumentException("key");
}
 return list.get(index);
        }
        throw new IllegalStateException("Not an array or map");
      }
public void set(CBORObject key, CBORObject value) {
        if (key == null) {
          throw new NullPointerException("key");
        }
        if (value == null) {
          throw new NullPointerException("value");
        }
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> map = this.AsMap();
          map.put(key, value);
          return;
        }
        if (this.getType() == CBORType.Array) {
          if (!key.isNumber() || !key.AsNumber().IsInteger()) {
            throw new IllegalArgumentException("Not an integer");
          }
          if (!key.AsNumber().CanFitInInt32()) {
            throw new IllegalArgumentException("key");
          }
          List<CBORObject> list = this.AsList();
          int index = key.AsNumber().ToInt32Checked();
          if (index < 0 || index >= list.size()) {
            throw new IllegalArgumentException("key");
          }
          list.set(index, value);
          return;
        }
        throw new IllegalStateException("Not an array or map");
      }

    public CBORObject get(String key) {
        if (key == null) {
          throw new NullPointerException("key");
        }
        CBORObject objkey = CBORObject.FromObject(key);
        return this.get(objkey);
      }
public void set(String key, CBORObject value) {
        if (key == null) {
          throw new NullPointerException("key");
        }
        if (value == null) {
          throw new NullPointerException("value");
        }
        CBORObject objkey = CBORObject.FromObject(key);
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> map = this.AsMap();
          map.put(objkey, value);
        } else {
          throw new IllegalStateException("Not a map");
        }
      }

    public static CBORObject DecodeFromBytes(byte[] data) {
      return DecodeFromBytes(data, CBOREncodeOptions.Default);
    }

    private static final CBOREncodeOptions AllowEmptyOptions =
      new CBOREncodeOptions("allowempty=1");

    public static CBORObject[] DecodeSequenceFromBytes(byte[] data) {
      return DecodeSequenceFromBytes(data, AllowEmptyOptions);
    }

    public static CBORObject[] DecodeSequenceFromBytes(byte[] data,
      CBOREncodeOptions options) {
      if (data == null) {
        throw new NullPointerException("data");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      if (data.length == 0) {
        return new CBORObject[0];
      }
      CBOREncodeOptions opt = options;
      if (!opt.getAllowEmpty()) {
        opt = new CBOREncodeOptions(opt.toString() + ";allowempty=1");
      }
      ArrayList<CBORObject> cborList = new ArrayList<CBORObject>();
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(data);

        while (true) {
          CBORObject obj = Read(ms, opt);
          if (obj == null) {
            break;
          }
          cborList.add(obj);
        }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      return cborList.toArray(new CBORObject[] { });
    }

    public static CBORObject[] FromJSONSequenceBytes(byte[] bytes) {
      return FromJSONSequenceBytes(bytes, JSONOptions.Default);
    }

    public byte[] ToJSONBytes() {
      return this.ToJSONBytes(JSONOptions.Default);
    }

    public byte[] ToJSONBytes(JSONOptions jsonoptions) {
      if (jsonoptions == null) {
        throw new NullPointerException("jsonoptions");
      }
      try {
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          this.WriteJSONTo(ms);
          return ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ex) {
        throw new CBORException(ex.getMessage(), ex);
      }
    }

    public static CBORObject[] FromJSONSequenceBytes(byte[] data,
      JSONOptions options) {
      if (data == null) {
        throw new NullPointerException("data");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      try {
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(data);

          return ReadJSONSequence(ms, options);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ex) {
        throw new CBORException(ex.getMessage(), ex);
      }
    }

    public static CBORObject DecodeFromBytes(
      byte[] data,
      CBOREncodeOptions options) {
      if (options == null) {
        throw new NullPointerException("options");
      }
      if (data == null) {
        throw new NullPointerException("data");
      }
      if (data.length == 0) {
        if (!(options.getAllowEmpty())) {
 throw new CBORException("data is empty.");
}
 return (CBORObject)null;
      }
      int firstbyte = data[0] & 0xff;
      int expectedLength = ValueExpectedLengths[firstbyte];
      // if invalid
      if (expectedLength == -1) {
        throw new CBORException("Unexpected data encountered");
      }
      if (expectedLength != 0) {
        // if fixed length
        CheckCBORLength(expectedLength, data.length);
        if (!options.getCtap2Canonical() ||
          (firstbyte >= 0x00 && firstbyte < 0x18) ||
          (firstbyte >= 0x20 && firstbyte < 0x38)) {
          return GetFixedLengthObject(firstbyte, data);
        }
      }
      if (firstbyte == 0xc0 && !options.getCtap2Canonical()) {
        // value with tag 0
        String s = GetOptimizedStringIfShortAscii(data, 1);
        if (s != null) {
          return new CBORObject(FromObject(s), 0, 0);
        }
      }
      // For objects with variable length,
      // read the Object as though
      // the byte array were a stream
      {
        java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(data);
int startingAvailable = ms.available();

        CBORObject o = Read(ms, options);
        CheckCBORLength(
          data.length,
          (startingAvailable - ms.available()));
        return o;
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
    }

    public static CBORObject FromJSONString(String str, int offset, int count) {
      if (str == null) {
 throw new NullPointerException("str");
}
 return FromJSONString(str, offset, count, JSONOptions.Default);
    }

    public static CBORObject FromJSONString(
      String str,
      JSONOptions jsonoptions) {
      if (str == null) {
 throw new NullPointerException("str");
}
 if (jsonoptions == null) {
 throw new NullPointerException("jsonoptions");
}
 return FromJSONString(str, 0, str.length(), jsonoptions);
    }

    public static CBORObject FromJSONString(String str) {
      return FromJSONString(str, JSONOptions.Default);
    }

    public static CBORObject FromJSONString(
      String str,
      int offset,
      int count,
      JSONOptions jsonoptions) {
      if (str == null) {
 throw new NullPointerException("str");
}
 if (jsonoptions == null) {
 throw new NullPointerException("jsonoptions");
}
 if (count > 0 && str.charAt(offset) == 0xfeff) {
 throw new CBORException(
          "JSON Object began with a byte order mark (U+FEFF) (offset 0)");
}
 if (count == 0) {
 throw new CBORException("String is empty");
}
 return CBORJson3.ParseJSONValue(str, offset, offset + count, jsonoptions);
    }

    @SuppressWarnings("unchecked")
public <T> T ToObject(java.lang.reflect.Type t) {
      return (T)(this.ToObject(t, null, null, 0));
    }

    @SuppressWarnings("unchecked")
public <T> T ToObject(java.lang.reflect.Type t, CBORTypeMapper mapper) {
      if (mapper == null) {
 throw new NullPointerException("mapper");
}
 return (T)(this.ToObject(t, mapper, null, 0));
    }

    @SuppressWarnings("unchecked")
public <T> T ToObject(java.lang.reflect.Type t, PODOptions options) {
      if (options == null) {
 throw new NullPointerException("options");
}
 return (T)(this.ToObject(t, null, options, 0));
    }

    @SuppressWarnings("unchecked")
public <T> T ToObject(java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions
      options) {
      if (options == null) {
 throw new NullPointerException("options");
}
 return (T)(this.ToObject(t, mapper, options, 0));
    }

    @SuppressWarnings("unchecked")
public static <T> T DecodeObjectFromBytes(
      byte[] data,
      CBOREncodeOptions enc,
      java.lang.reflect.Type t,
      CBORTypeMapper mapper,
      PODOptions pod) {
      if (pod == null) {
 throw new NullPointerException("pod");
}
 if (enc == null) {
 throw new NullPointerException("enc");
}
 return (T)(DecodeFromBytes(data, enc).ToObject(t, mapper, pod));
    }

    @SuppressWarnings("unchecked")
public static <T> T DecodeObjectFromBytes(
      byte[] data,
      CBOREncodeOptions enc,
      java.lang.reflect.Type t) {
      return (T)(DecodeFromBytes(data, enc).ToObject(t));
    }

    @SuppressWarnings("unchecked")
public static <T> T DecodeObjectFromBytes(
      byte[] data,
      java.lang.reflect.Type t,
      CBORTypeMapper mapper,
      PODOptions pod) {
      return (T)(DecodeObjectFromBytes(data, CBOREncodeOptions.Default, t, mapper, pod));
    }

    @SuppressWarnings("unchecked")
public static <T> T DecodeObjectFromBytes(byte[] data, java.lang.reflect.Type t) {
      return (T)(DecodeObjectFromBytes(data, CBOREncodeOptions.Default, t));
    }

    @SuppressWarnings("unchecked")
 <T> T ToObject(java.lang.reflect.Type t,
      CBORTypeMapper mapper,
      PODOptions options,
      int depth) {
      ++depth;
      if (depth > 100) {
        throw new CBORException("Depth level too high");
      }
      if (t == null) {
        throw new NullPointerException("t");
      }
      if (t.equals(CBORObject.class)) {
        return (T)(this);
      }
      if (this.isNull()) {
        // TODO: In next major version, consider returning null
        // here only if this object is untagged, to allow behavior
        // to be customizable by CBORTypeMapper
        return null;
      }
      if (mapper != null) {
        Object obj = mapper.ConvertBackWithConverter(this, t);
        if (obj != null) {
          return (T)(obj);
        }
      }
      if (t.equals(Object.class)) {
        return (T)(this);
      }
      // TODO: In next major version, address inconsistent
      // implementations for EDecimal, EInteger, EFloat,
      // and ERational (perhaps
      // by using EDecimal implementation). Also, these operations
      // might throw IllegalStateException rather than CBORException.
      // Make them throw CBORException in next major version.
      if (t.equals(EDecimal.class)) {
        CBORNumber cn = this.AsNumber();
        return (T)(cn.GetNumberInterface().AsEDecimal(cn.GetValue()));
      }
      if (t.equals(EFloat.class)) {
        CBORNumber cn = CBORNumber.FromCBORObject(this);
        if (cn == null) {
 throw new IllegalStateException("Not a" +
"\u0020number type");
}
 return (T)((Object)cn.GetNumberInterface().AsEFloat(cn.GetValue()));
      }
      if (t.equals(EInteger.class)) {
        CBORNumber cn = CBORNumber.FromCBORObject(this);
        if (cn == null) {
 throw new IllegalStateException("Not a" +
"\u0020number type");
}
 return (T)((Object)cn.GetNumberInterface().AsEInteger(cn.GetValue()));
      }
      if (t.equals(ERational.class)) {
        // NOTE: Will likely be simplified in version 5.0 and later
        if (this.HasMostInnerTag(30) && this.size() != 2) {
          EInteger num, den;
          num = (EInteger)this.get(0).ToObject(EInteger.class);
          den = (EInteger)this.get(1).ToObject(EInteger.class);
          return (T)(ERational.Create(num, den));
        }
        CBORNumber cn = CBORNumber.FromCBORObject(this);
        if (cn == null) {
 throw new IllegalStateException("Not a" +
"\u0020number type");
}
 return (T)((Object)cn.GetNumberInterface().AsERational(cn.GetValue()));
      }
      return (T)(t.equals(String.class) ? this.AsString() :
        PropertyMap.TypeToObject(this, t, mapper, options, depth));
    }

    public static CBORObject FromObject(long value) {
      return value >= 0L && value < 24L ? FixedObjects[(int)value] :
        (value >= -24L && value < 0L) ? FixedObjects[0x20 - (int)(value +
              1L)] : new CBORObject(CBORObjectTypeInteger, value);
    }

    public static CBORObject FromObject(CBORObject value) {
      return (value == null) ? (CBORObject.Null) : value;
    }

    private static int IntegerByteLength(int intValue) {
      if (intValue < 0) {
        intValue = -(intValue + 1);
      }
      return intValue > 0xffff ? 5 : intValue > 0xff ? 3 : (intValue > 23) ?
2 : 1;
    }

    private static int IntegerByteLength(long longValue) {
      if (longValue < 0) {
        longValue = -(longValue + 1);
      }
      return longValue > 0xffffffffL ? 9 : longValue > 0xffffL ? 5 :
longValue > 0xffL ? 3 : (longValue > 23L) ? 2 : 1;
    }

    public long CalcEncodedSize() {
      return this.CalcEncodedSize(0);
    }

    private long CalcEncodedSize(int depth) {
      if (depth > 1000) {
        throw new CBORException("Too deeply nested");
      }
      // System.out.println("type="+this.getType()+" depth="+depth);
      long size = 0L;
      CBORObject cbor = this;
      while (cbor.isTagged()) {
        EInteger etag = cbor.getMostOuterTag();
        if (etag.CanFitInInt64()) {
          long tag = etag.ToInt64Checked();
          size = (size + IntegerByteLength(tag));
        } else {
          size = (size + 9);
        }
        cbor = cbor.UntagOne();
      }
      if (cbor.getItemType() == CBORObjectTypeTextStringUtf8) {
        byte[] bytes = (byte[])this.getThisItem();
        size = (size + IntegerByteLength(bytes.length));
        return size + bytes.length;
      }
      if (cbor.getItemType() == CBORObjectTypeTextStringAscii) {
        String str = (String)this.getThisItem();
        size = (size + IntegerByteLength(str.length()));
        return size + str.length();
      }
      switch (cbor.getType()) {
        case Integer: {
            if (cbor.CanValueFitInInt64()) {
              long tag = cbor.AsInt64Value();
              size = (size + IntegerByteLength(tag));
              return size;
            } else {
              return size + 9;
            }
          }
        case FloatingPoint: {
            long valueBits = cbor.AsDoubleBits();
            int bits =
CBORUtilities.DoubleToHalfPrecisionIfSameValue(valueBits);
            return bits != -1 ? size + 3 :
              CBORUtilities.DoubleRetainsSameValueInSingle(valueBits) ?
              (size + 5) : (size + 9);
          }
        case Array:
          size = (size + IntegerByteLength(cbor.size()));
          for (int i = 0; i < cbor.size(); ++i) {
            long newsize = cbor.get(i).CalcEncodedSize(depth + 1);
            size = (size + newsize);
          }
          return size;
        case Map: {
            Collection<Map.Entry<CBORObject, CBORObject>> entries =
              this.getEntries();
            size = (size + IntegerByteLength(entries.size()));
            try {
              for (Map.Entry<CBORObject, CBORObject> entry : entries) {
                CBORObject key = entry.getKey();
                CBORObject value = entry.getValue();
                size = (size + key.CalcEncodedSize(depth + 1));
                size = (size + value.CalcEncodedSize(depth + 1));
              }
            } catch (IllegalStateException ex) {
              // Additional error that may occur in iteration
              throw new CBORException(ex.getMessage(), ex);
            } catch (IllegalArgumentException ex) {
              // Additional error that may occur in iteration
              throw new CBORException(ex.getMessage(), ex);
            }
            return size;
          }
        case TextString: {
            long ulength = com.upokecenter.util.DataUtilities.GetUtf8Length(this.AsString(), false);
            size = (size + IntegerByteLength(ulength));
            return size + ulength;
          }
        case ByteString: {
            byte[] bytes = cbor.GetByteString();
            size = (size + IntegerByteLength(bytes.length));
            return size + bytes.length;
          }
        case Boolean:
          return size + 1;
        case SimpleValue:
          return size + (cbor.getSimpleValue() >= 24 ? 2 : 1);
        default: throw new IllegalStateException();
      }
    }

    public static CBORObject FromObject(EInteger bigintValue) {
      if (bigintValue == null) {
        return CBORObject.Null;
      }
      if (bigintValue.CanFitInInt64()) {
        return CBORObject.FromObject(bigintValue.ToInt64Checked());
      } else {
        EInteger bitLength = bigintValue.GetSignedBitLengthAsEInteger();
        if (bitLength.compareTo(64) <= 0) {
          // Fits in major type 0 or 1
          return new CBORObject(CBORObjectTypeEInteger, bigintValue);
        } else {
          int tag = (bigintValue.signum() < 0) ? 3 : 2;
          return CBORObject.FromObjectAndTag(
              EIntegerBytes(bigintValue),
              tag);
        }
      }
    }

    public static CBORObject FromObject(EFloat bigValue) {
      if (bigValue == null) {
        return CBORObject.Null;
      }
      CBORObject cbor;
      int tag;
      if (bigValue.IsInfinity() || bigValue.IsNaN() ||
        (bigValue.isNegative() && bigValue.isZero())) {
        int options = bigValue.isNegative() ? 1 : 0;
        if (bigValue.IsInfinity()) {
          options += 2;
        }
        if (bigValue.IsQuietNaN()) {
          options += 4;
        }
        if (bigValue.IsSignalingNaN()) {
          options += 6;
        }
        cbor = CBORObject.NewArray(
            CBORObject.FromObject(bigValue.getExponent()),
            CBORObject.FromObject(bigValue.getUnsignedMantissa()),
            CBORObject.FromObject(options));
        tag = 269;
      } else {
        EInteger exponent = bigValue.getExponent();
        if (exponent.CanFitInInt64()) {
          tag = 5;
          cbor = CBORObject.NewArray(
              CBORObject.FromObject(exponent.ToInt64Checked()),
              CBORObject.FromObject(bigValue.getMantissa()));
        } else {
          tag = (exponent.GetSignedBitLengthAsInt64() > 64) ?
            265 : 5;
          cbor = CBORObject.NewArray(
              CBORObject.FromObject(exponent),
              CBORObject.FromObject(bigValue.getMantissa()));
        }
      }
      return cbor.WithTag(tag);
    }

    public static CBORObject FromObject(ERational bigValue) {
      if (bigValue == null) {
        return CBORObject.Null;
      }
      CBORObject cbor;
      int tag;
      if (bigValue.IsInfinity() || bigValue.IsNaN() ||
        (bigValue.isNegative() && bigValue.isZero())) {
        int options = bigValue.isNegative() ? 1 : 0;
        if (bigValue.IsInfinity()) {
          options += 2;
        }
        if (bigValue.IsQuietNaN()) {
          options += 4;
        }
        if (bigValue.IsSignalingNaN()) {
          options += 6;
        }

        cbor = CBORObject.NewArray(
            FromObject(bigValue.getUnsignedNumerator()),
            FromObject(bigValue.getDenominator()),
            FromObject(options));
        tag = 270;
      } else {
        tag = 30;
        cbor = CBORObject.NewArray(
            CBORObject.FromObject(bigValue.getNumerator()),
            CBORObject.FromObject(bigValue.getDenominator()));
      }
      return cbor.WithTag(tag);
    }

    public static CBORObject FromObject(EDecimal bigValue) {
      if (bigValue == null) {
        return CBORObject.Null;
      }
      CBORObject cbor;
      int tag;
      if (bigValue.IsInfinity() || bigValue.IsNaN() ||
        (bigValue.isNegative() && bigValue.isZero())) {
        int options = bigValue.isNegative() ? 1 : 0;
        if (bigValue.IsInfinity()) {
          options += 2;
        }
        if (bigValue.IsQuietNaN()) {
          options += 4;
        }
        if (bigValue.IsSignalingNaN()) {
          options += 6;
        }
        cbor = CBORObject.NewArray(
            FromObject(bigValue.getExponent()),
            FromObject(bigValue.getUnsignedMantissa()),
            FromObject(options));
        tag = 268;
      } else {
        EInteger exponent = bigValue.getExponent();
        if (exponent.CanFitInInt64()) {
          tag = 4;
          cbor = CBORObject.NewArray(
              CBORObject.FromObject(exponent.ToInt64Checked()),
              CBORObject.FromObject(bigValue.getMantissa()));
        } else {
          tag = (exponent.GetSignedBitLengthAsInt64() > 64) ?
            264 : 4;
          cbor = CBORObject.NewArray(
              CBORObject.FromObject(exponent),
              CBORObject.FromObject(bigValue.getMantissa()));
        }
      }
      return cbor.WithTag(tag);
    }

    public static CBORObject FromObject(String strValue) {
      if (strValue == null) {
        return CBORObject.Null;
      }
      if (strValue.length() == 0) {
        return GetFixedObject(0x60);
      }
      long utf8Length = com.upokecenter.util.DataUtilities.GetUtf8Length(strValue, false);
      if (utf8Length < 0) {
 throw new IllegalArgumentException("String contains an unpaired " +
          "surrogate code point.");
}
 return new CBORObject(
            strValue.length() == utf8Length ? CBORObjectTypeTextStringAscii : CBORObjectTypeTextString,
            strValue);
    }

    public static CBORObject FromObject(int value) {
      return value >= 0 && value < 24 ? FixedObjects[value] :
        (value >= -24 && value < 0) ? FixedObjects[0x20 - (value + 1)] :
          FromObject((long)value);
    }

    public static CBORObject FromObject(short value) {
      return value >= 0 && value < 24 ? FixedObjects[value] :
        (value >= -24 && value < 0) ? FixedObjects[0x20 - (value + 1)] :
          FromObject((long)value);
    }

    public static CBORObject FromObject(boolean value) {
      return value ? CBORObject.True : CBORObject.False;
    }

    public static CBORObject FromObject(byte value) {
      return FromObject(value & 0xff);
    }

    public static CBORObject FromObject(float value) {
      long doubleBits = CBORUtilities.SingleToDoublePrecision(
          CBORUtilities.SingleToInt32Bits(value));
      return new CBORObject(CBORObjectTypeDouble, doubleBits);
    }

    public static CBORObject FromObject(double value) {
      long doubleBits = CBORUtilities.DoubleToInt64Bits(value);
      return new CBORObject(CBORObjectTypeDouble, doubleBits);
    }

    public static CBORObject FromObject(byte[] bytes) {
      if (bytes == null) {
        return CBORObject.Null;
      }
      byte[] newvalue = new byte[bytes.length];
      System.arraycopy(bytes, 0, newvalue, 0, bytes.length);
      return new CBORObject(CBORObjectTypeByteString, bytes);
    }

    public static CBORObject FromObject(CBORObject[] array) {
      if (array == null) {
        return CBORObject.Null;
      }
      List<CBORObject> list = new ArrayList<CBORObject>();
      for (CBORObject cbor : array) {
        list.add(cbor);
      }
      return new CBORObject(CBORObjectTypeArray, list);
    }

    static CBORObject FromArrayBackedObject(CBORObject[] array) {
      if (array == null) {
        return CBORObject.Null;
      }
      List<CBORObject> list = PropertyMap.ListFromArray(array);
      return new CBORObject(CBORObjectTypeArray, list);
    }

    public static CBORObject FromObject(int[] array) {
      if (array == null) {
        return CBORObject.Null;
      }
      List<CBORObject> list = new ArrayList<CBORObject>(array.length ==
        Integer.MAX_VALUE ? array.length : (array.length + 1));
      for (int i : array) {
        list.add(FromObject(i));
      }
      return new CBORObject(CBORObjectTypeArray, list);
    }

    public static CBORObject FromObject(long[] array) {
      if (array == null) {
        return CBORObject.Null;
      }
      List<CBORObject> list = new ArrayList<CBORObject>(array.length ==
        Integer.MAX_VALUE ? array.length : (array.length + 1));
      for (long i : array) {
        list.add(FromObject(i));
      }
      return new CBORObject(CBORObjectTypeArray, list);
    }

    public static CBORObject FromObject(Object obj) {
      return FromObject(obj, PODOptions.Default);
    }

    public static CBORObject FromObject(
      Object obj,
      PODOptions options) {
      return FromObject(obj, options, null, 0);
    }

    public static CBORObject FromObject(
      Object obj,
      CBORTypeMapper mapper) {
      if (mapper == null) {
 throw new NullPointerException("mapper");
}
 return FromObject(obj, PODOptions.Default, mapper, 0);
    }

    public static CBORObject FromObject(
      Object obj,
      CBORTypeMapper mapper,
      PODOptions options) {
      if (mapper == null) {
 throw new NullPointerException("mapper");
}
 return FromObject(obj, options, mapper, 0);
    }

    static CBORObject FromObject(
      Object obj,
      PODOptions options,
      CBORTypeMapper mapper,
      int depth) {
      if (options == null) {
        throw new NullPointerException("options");
      }
      if (depth >= 100) {
        throw new CBORException("Nesting depth too high");
      }
      if (obj == null) {
        return CBORObject.Null;
      }
      if (obj instanceof CBORObject) {
        return FromObject((CBORObject)obj);
      }
      CBORObject objret;
      if (mapper != null) {
        objret = mapper.ConvertWithConverter(obj);
        if (objret != null) {
          return objret;
        }
      }
      if (obj instanceof String) {
        return FromObject((String)obj);
      }
      if (obj instanceof Integer) {
        return FromObject(((Integer)obj).intValue());
      }
      if (obj instanceof Long) {
        return FromObject((((Long)obj).longValue()));
      }
      if (obj instanceof EInteger) {
        return FromObject(((EInteger)obj));
      }
      if (obj instanceof EDecimal) {
        return FromObject(((EDecimal)obj));
      }
      if (obj instanceof EFloat) {
        return FromObject(((EFloat)obj));
      }
      if (obj instanceof ERational) {
        return FromObject(((ERational)obj));
      }
      if (obj instanceof Short) {
        return FromObject(((Short)obj).shortValue());
      }
      if (obj instanceof Character) {
        return FromObject((int)((Character)obj).charValue());
      }
      if (obj instanceof Boolean) {
        return FromObject(((Boolean)obj).booleanValue());
      }
      if (obj instanceof Byte) {
        return FromObject(((Byte)obj).byteValue());
      }
      if (obj instanceof Float) {
        return FromObject(((Float)obj).floatValue());
      }

      if (obj instanceof Double) {
        return FromObject(((Double)obj).doubleValue());
      }
      if (obj instanceof byte[]) {
        return FromObject(((byte[])obj));
      }
      if (obj instanceof Map<?, ?>) {
        // Map appears first because Map includes Iterable
        objret = CBORObject.NewMap();
        Map<?, ?> objdic = (Map<?, ?>)obj;
        for (Object keyPair : objdic.entrySet()) {
          Map.Entry<?, ?> kvp = (Map.Entry<?, ?>)keyPair;
          CBORObject objKey = CBORObject.FromObject(
              kvp.getKey(),
              options,
              mapper,
              depth + 1);
          objret.set(objKey, CBORObject.FromObject(
              kvp.getValue(),
              options,
              mapper,
              depth + 1));
        }
        return objret;
      }
      if (obj.getClass().isArray()) {
        return PropertyMap.FromArray(obj, options, mapper, depth);
      }
      if (obj instanceof Iterable<?>) {
        objret = CBORObject.NewArray();
        for (Object element : (Iterable<?>)obj) {
          objret.Add(
            CBORObject.FromObject(
              element,
              options,
              mapper,
              depth + 1));
        }
        return objret;
      }
      if (obj instanceof Enum<?>) {
        return FromObject(PropertyMap.EnumToObjectAsInteger((Enum<?>)obj));
      }
      if (obj instanceof java.util.Date) {
        return new CBORDateConverter().ToCBORObject((java.util.Date)obj);
      }
      if (obj instanceof java.net.URI) {
        return new CBORUriConverter().ToCBORObject((java.net.URI)obj);
      }
      if (obj instanceof java.util.UUID) {
        return new CBORUuidConverter().ToCBORObject((java.util.UUID)obj);
      }
      objret = PropertyMap.FromObjectOther(obj);
      if (objret != null) {
        return objret;
      }
      objret = CBORObject.NewMap();
      for (Map.Entry<String, Object> key : PropertyMap.GetProperties(
          obj,
          options.getUseCamelCase())) {
        objret.set(key.getKey(), CBORObject.FromObject(
            key.getValue(),
            options,
            mapper,
            depth + 1));
      }
      return objret;
    }

    public CBORObject WithTag(EInteger bigintTag) {
      if (bigintTag == null) {
        throw new NullPointerException("bigintTag");
      }
      if (bigintTag.signum() < 0) {
        throw new IllegalArgumentException("tagEInt's sign(" + bigintTag.signum() +
          ") is less than 0");
      }
      if (bigintTag.CanFitInInt32()) {
        // Low-numbered, commonly used tags
        return this.WithTag(bigintTag.ToInt32Checked());
      } else {
        if (bigintTag.compareTo(UInt64MaxValue) > 0) {
          throw new IllegalArgumentException(
            "tag more than 18446744073709551615");
        }
        int tagLow = 0;
        int tagHigh = 0;
        byte[] bytes = bigintTag.ToBytes(true);
        for (int i = 0; i < Math.min(4, bytes.length); ++i) {
          int b = bytes[i] & 0xff;
          tagLow = (tagLow | (b << (i * 8)));
        }
        for (int i = 4; i < Math.min(8, bytes.length); ++i) {
          int b = bytes[i] & 0xff;
          tagHigh = (tagHigh | (b << (i * 8)));
        }
        return new CBORObject(this, tagLow, tagHigh);
      }
    }

    public static CBORObject FromObjectAndTag(
      Object valueOb,
      EInteger bigintTag) {
      if (bigintTag == null) {
 throw new NullPointerException("bigintTag");
}
 if (bigintTag.signum() < 0) {
 throw new IllegalArgumentException("tagEInt's sign(" + bigintTag.signum() +
          ") is less than 0");
}
 if (bigintTag.compareTo(UInt64MaxValue) > 0) {
 throw new IllegalArgumentException(
          "tag more than 18446744073709551615");
}
 return FromObject(valueOb).WithTag(bigintTag);
    }

    public CBORObject WithTag(int smallTag) {
      if (smallTag < 0) {
 throw new IllegalArgumentException("smallTag(" + smallTag +
          ") is less than 0");
}
 return new CBORObject(this, smallTag, 0);
    }

    public static CBORObject FromObjectAndTag(
      Object valueObValue,
      int smallTag) {
      if (smallTag < 0) {
 throw new IllegalArgumentException("smallTag(" + smallTag +
          ") is less than 0");
}
 return FromObject(valueObValue).WithTag(smallTag);
    }

    public static CBORObject FromSimpleValue(int simpleValue) {
      if (simpleValue < 0) {
        throw new IllegalArgumentException("simpleValue(" + simpleValue +
          ") is less than 0");
      }
      if (simpleValue > 255) {
        throw new IllegalArgumentException("simpleValue(" + simpleValue +
          ") is more than " + "255");
      }
      if (simpleValue >= 24 && simpleValue < 32) {
 throw new IllegalArgumentException("Simple value is from 24 to 31 - " +
          simpleValue);
}
 return simpleValue < 32 ?
        FixedObjects[0xe0 + simpleValue] : new CBORObject(
          CBORObjectTypeSimpleValue,
          simpleValue);
    }

    public static CBORObject NewArray() {
      return new CBORObject(CBORObjectTypeArray, new ArrayList<CBORObject>());
    }

    static CBORObject NewArray(CBORObject o1, CBORObject o2) {
      java.util.ArrayList<CBORObject> list = new java.util.ArrayList<CBORObject>(java.util.Arrays.asList(
        o1,
        o2));
      return new CBORObject(CBORObjectTypeArray, list);
    }

    static CBORObject NewArray(
      CBORObject o1,
      CBORObject o2,
      CBORObject o3) {
      java.util.ArrayList<CBORObject> list = new java.util.ArrayList<CBORObject>(java.util.Arrays.asList(
        o1,
        o2,
        o3));
      return new CBORObject(CBORObjectTypeArray, list);
    }

    public static CBORObject NewMap() {
      return new CBORObject(
          CBORObjectTypeMap,
          new TreeMap<CBORObject, CBORObject>());
    }

    public static CBORObject NewOrderedMap() {
      return new CBORObject(
          CBORObjectTypeMap,
          PropertyMap.NewOrderedDict());
    }

    public static CBORObject[] ReadSequence(InputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      ArrayList<CBORObject> cborList = new ArrayList<CBORObject>();
      while (true) {
        CBORObject obj = Read(stream, AllowEmptyOptions);
        if (obj == null) {
          break;
        }
        cborList.add(obj);
      }
      return cborList.toArray(new CBORObject[] { });
    }

    public static CBORObject[] ReadSequence(InputStream stream, CBOREncodeOptions
      options) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      CBOREncodeOptions opt = options;
      if (!opt.getAllowEmpty()) {
        opt = new CBOREncodeOptions(opt.toString() + ";allowempty=1");
      }
      ArrayList<CBORObject> cborList = new ArrayList<CBORObject>();
      while (true) {
        CBORObject obj = Read(stream, opt);
        if (obj == null) {
          break;
        }
        cborList.add(obj);
      }
      return cborList.toArray(new CBORObject[] { });
    }

    public static CBORObject Read(InputStream stream) {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      try {
        CBORReader reader = new CBORReader(stream);
        return reader.Read();
      } catch (IOException ex) {
        throw new CBORException("I/O error occurred.", ex);
      }
    }

    public static CBORObject Read(InputStream stream, CBOREncodeOptions options) {
      if (options == null) {
        throw new NullPointerException("options");
      }
      try {
        CBORReader reader = new CBORReader(stream, options);
        return reader.Read();
      } catch (IOException ex) {
        throw new CBORException("I/O error occurred.", ex);
      }
    }

    public static CBORObject ReadJSON(InputStream stream) throws java.io.IOException {
      return ReadJSON(stream, JSONOptions.Default);
    }

    public static CBORObject[] ReadJSONSequence(InputStream stream) throws java.io.IOException {
      return ReadJSONSequence(stream, JSONOptions.Default);
    }

    public static CBORObject[] ReadJSONSequence(InputStream stream, JSONOptions
      jsonoptions) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (jsonoptions == null) {
        throw new NullPointerException("jsonoptions");
      }
      CharacterInputWithCount reader;
      reader = new CharacterInputWithCount(
        new CharacterReader(stream, 0, true, true));
      try {
        int[] nextchar = new int[1];
        CBORObject[] objlist = CBORJson.ParseJSONSequence(
            reader,
            jsonoptions,
            nextchar);
        if (nextchar[0] != -1) {
          reader.RaiseError("End of data stream not reached");
        }
        return objlist;
      } catch (CBORException ex) {
        if (ex.getCause() instanceof IOException) {
          throw ((IOException)ex.getCause());
        }
        throw ex;
      }
    }

    public static CBORObject ReadJSON(
      InputStream stream,
      JSONOptions jsonoptions) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (jsonoptions == null) {
        throw new NullPointerException("jsonoptions");
      }
      CharacterInputWithCount reader;
      reader = new CharacterInputWithCount(
        new CharacterReader(stream, 2, true));
      try {
        int[] nextchar = new int[1];
        CBORObject obj = CBORJson.ParseJSONValue(
            reader,
            jsonoptions,
            nextchar);
        if (nextchar[0] != -1) {
          reader.RaiseError("End of data stream not reached");
        }
        return obj;
      } catch (CBORException ex) {
        if (ex.getCause() instanceof IOException) {
          throw ((IOException)ex.getCause());
        }
        throw ex;
      }
    }

    public static CBORObject FromJSONBytes(byte[] bytes) {
      // TODO: In next major version, consider supporting UTF-8 only
      return FromJSONBytes(bytes, JSONOptions.Default);
    }

    public static CBORObject FromJSONBytes(
      byte[] bytes,
      JSONOptions jsonoptions) {
      // TODO: In next major version, consider supporting UTF-8 only
      if (bytes == null) {
 throw new NullPointerException("bytes");
}
 if (jsonoptions == null) {
 throw new NullPointerException("jsonoptions");
}
 if (bytes.length == 0) {
 throw new CBORException("Byte array is empty");
}
 return FromJSONBytes(bytes, 0, bytes.length, jsonoptions);
    }

    public static CBORObject FromJSONBytes(byte[] bytes, int offset, int
      count) {
      return FromJSONBytes(bytes, offset, count, JSONOptions.Default);
    }

    public static CBORObject FromJSONBytes(
      byte[] bytes,
      int offset,
      int count,
      JSONOptions jsonoptions) {
      if (bytes == null) {
        throw new NullPointerException("bytes");
      }
      if (jsonoptions == null) {
        throw new NullPointerException("jsonoptions");
      }
      if (offset < 0) {
        throw new IllegalArgumentException("offset (" + offset + ") is not greater" +
          "\u0020or equal to 0");
      }
      if (offset > bytes.length) {
        throw new IllegalArgumentException("offset (" + offset + ") is not less or" +
          "\u0020equal to " + bytes.length);
      }
      if (count < 0) {
        throw new IllegalArgumentException("count (" + count + ") is not greater or" +
          "\u0020equal to 0");
      }
      if (count > bytes.length) {
        throw new IllegalArgumentException("count (" + count + ") is not less or" +
          "\u0020equal to " + bytes.length);
      }
      if (bytes.length - offset < count) {
        throw new IllegalArgumentException("bytes's length minus " + offset + " (" +
          (bytes.length - offset) + ") is not greater or equal to " + count);
      }
      if (count == 0) {
        throw new CBORException("Byte array is empty");
      }
      if (bytes[offset] >= 0x01 && bytes[offset] <= 0x7f && count >= 2 &&
        bytes[offset + 1] != 0) {
        // UTF-8 JSON bytes
        return CBORJson2.ParseJSONValue(
            bytes,
            offset,
            offset + count,
            jsonoptions);
      } else {
        // Other than UTF-8 without byte order mark
        try {
          {
            java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes, offset, count);

            return ReadJSON(ms, jsonoptions);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        } catch (IOException ex) {
          throw new CBORException(ex.getMessage(), ex);
        }
      }
    }

    public static void Write(String str, OutputStream stream) throws java.io.IOException {
      Write(str, stream, CBOREncodeOptions.Default);
    }

    public static void Write(
      String str,
      OutputStream stream,
      CBOREncodeOptions options) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      if (str == null) {
        stream.write(0xf6); // Write null instead of String
      } else {
        if (!options.getUseIndefLengthStrings() || options.getCtap2Canonical()) {
          // NOTE: Length of a String Object won't be higher than the maximum
          // allowed for definite-length strings
          long codePointLength = com.upokecenter.util.DataUtilities.GetUtf8Length(str, true);
          WritePositiveInt64(3, codePointLength, stream);
          com.upokecenter.util.DataUtilities.WriteUtf8(str, stream, true);
        } else {
          WriteStreamedString(str, stream);
        }
      }
    }

    public static void Write(EFloat bignum, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (bignum == null) {
        stream.write(0xf6);
        return;
      }
      if ((bignum.isZero() && bignum.isNegative()) || bignum.IsInfinity() ||
        bignum.IsNaN()) {
        Write(CBORObject.FromObject(bignum), stream);
        return;
      }
      EInteger exponent = bignum.getExponent();
      if (exponent.CanFitInInt64()) {
        stream.write(0xc5); // tag 5
        stream.write(0x82); // array, length 2
      } else if (exponent.GetSignedBitLengthAsInt64() > 64) {
        stream.write(0xd9); // tag 265
        stream.write(0x01);
        stream.write(0x09);
        stream.write(0x82); // array, length 2
      } else {
        stream.write(0xc5); // tag 5
        stream.write(0x82); // array, length 2
      }
      Write(
        bignum.getExponent(),
        stream);
      Write(bignum.getMantissa(), stream);
    }

    public static void Write(ERational rational, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (rational == null) {
        stream.write(0xf6);
        return;
      }
      if (!rational.isFinite() || (rational.isNegative() && rational.isZero())) {
        Write(CBORObject.FromObject(rational), stream);
        return;
      }
      stream.write(0xd8); // tag 30
      stream.write(0x1e);
      stream.write(0x82); // array, length 2
      Write(rational.getNumerator(), stream);
      Write(
        rational.getDenominator(),
        stream);
    }

    public static void Write(EDecimal bignum, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (bignum == null) {
        stream.write(0xf6);
        return;
      }
      if (!bignum.isFinite() || (bignum.isNegative() && bignum.isZero())) {
        Write(CBORObject.FromObject(bignum), stream);
        return;
      }
      EInteger exponent = bignum.getExponent();
      if (exponent.CanFitInInt64()) {
        stream.write(0xc4); // tag 4
        stream.write(0x82); // array, length 2
      } else if (exponent.GetSignedBitLengthAsInt64() > 64) {
        stream.write(0xd9); // tag 264
        stream.write(0x01);
        stream.write(0x08);
        stream.write(0x82); // array, length 2
      } else {
        stream.write(0xc4); // tag 4
        stream.write(0x82); // array, length 2
      }
      Write(exponent, stream);
      Write(bignum.getMantissa(), stream);
    }

    private static byte[] EIntegerBytes(EInteger ei) {
      if (ei.isZero()) {
        return new byte[] { 0 };
      }
      if (ei.signum() < 0) {
        ei = ei.Add(1).Negate();
      }
      byte[] bytes = ei.ToBytes(false);
      int index = 0;
      while (index < bytes.length && bytes[index] == 0) {
        ++index;
      }
      if (index > 0) {
        byte[] newBytes = new byte[bytes.length - index];
        System.arraycopy(bytes, index, newBytes, 0, newBytes.length);
        return newBytes;
      }
      return bytes;
    }

    public static void Write(EInteger bigint, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (bigint == null) {
        stream.write(0xf6);
        return;
      }
      int datatype = 0;
      if (bigint.signum() < 0) {
        datatype = 1;
        bigint = bigint.Add(EInteger.FromInt32(1));
        bigint = bigint.Negate();
      }
      if (bigint.CanFitInInt64()) {
        // If the arbitrary-precision integer is representable as a long and in
        // major type 0 or 1, write that major type
        // instead of as a bignum
        WritePositiveInt64(datatype, bigint.ToInt64Checked(), stream);
      } else {
        // Get a byte array of the arbitrary-precision integer's value,
        // since shifting and doing AND operations is
        // slow with large EIntegers
        byte[] bytes = bigint.ToBytes(true);
        int byteCount = bytes.length;
        while (byteCount > 0 && bytes[byteCount - 1] == 0) {
          // Ignore trailing zero bytes
          --byteCount;
        }
        if (byteCount != 0) {
          int half = byteCount >> 1;
          int right = byteCount - 1;
          for (int i = 0; i < half; ++i, --right) {
            // NOTE: Swapping syntax can't be used in netstandard1.0
            // because it relies on System.ValueTuple
            byte tmp = bytes[i];
            bytes[i] = bytes[right];
            bytes[right] = tmp;
          }
        }
        switch (byteCount) {
          case 0:
            stream.write((byte)(datatype << 5));
            return;
          case 1:
            WritePositiveInt(datatype, bytes[0] & 0xff, stream);
            break;
          case 2:
            stream.write((byte)((datatype << 5) | 25));
            stream.write(bytes, 0, byteCount);
            break;
          case 3:
            stream.write((byte)((datatype << 5) | 26));
            stream.write(0);
            stream.write(bytes, 0, byteCount);
            break;
          case 4:
            stream.write((byte)((datatype << 5) | 26));
            stream.write(bytes, 0, byteCount);
            break;
          case 5:
            stream.write((byte)((datatype << 5) | 27));
            stream.write(0);
            stream.write(0);
            stream.write(0);
            stream.write(bytes, 0, byteCount);
            break;
          case 6:
            stream.write((byte)((datatype << 5) | 27));
            stream.write(0);
            stream.write(0);
            stream.write(bytes, 0, byteCount);
            break;
          case 7:
            stream.write((byte)((datatype << 5) | 27));
            stream.write(0);
            stream.write(bytes, 0, byteCount);
            break;
          case 8:
            stream.write((byte)((datatype << 5) | 27));
            stream.write(bytes, 0, byteCount);
            break;
          default: stream.write((datatype == 0) ?
              (byte)0xc2 : (byte)0xc3);
            WritePositiveInt(2, byteCount, stream);
            stream.write(bytes, 0, byteCount);
            break;
        }
      }
    }

    public static void Write(long value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (value >= 0) {
        WritePositiveInt64(0, value, stream);
      } else {
        ++value;
        value = -value; // Will never overflow
        WritePositiveInt64(1, value, stream);
      }
    }

    public static void Write(int value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      int type = 0;
      if (value < 0) {
        ++value;
        value = -value;
        type = 0x20;
      }
      if (value < 24) {
        stream.write((byte)(value | type));
      } else if (value <= 0xff) {
        byte[] bytes = { (byte)(24 | type), (byte)(value & 0xff) };
        stream.write(bytes, 0, 2);
      } else if (value <= 0xffff) {
        byte[] bytes = {
          (byte)(25 | type), (byte)((value >> 8) & 0xff),
          (byte)(value & 0xff),
         };
        stream.write(bytes, 0, 3);
      } else {
        byte[] bytes = {
          (byte)(26 | type), (byte)((value >> 24) & 0xff),
          (byte)((value >> 16) & 0xff), (byte)((value >> 8) & 0xff),
          (byte)(value & 0xff),
         };
        stream.write(bytes, 0, 5);
      }
    }

    public static void Write(short value, OutputStream stream) throws java.io.IOException {
      Write((long)value, stream);
    }

    public static void Write(boolean value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      stream.write(value ? (byte)0xf5 : (byte)0xf4);
    }

    public static void Write(byte value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if ((value & 0xff) < 24) {
        stream.write(value);
      } else {
        stream.write(24);
        stream.write(value);
      }
    }

    public static void Write(float value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      WriteFloatingPointBits(
        stream,
        CBORUtilities.SingleToInt32Bits(value),
        4,
        true);
    }

    public static void Write(double value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      WriteFloatingPointBits(
        stream,
        CBORUtilities.DoubleToInt64Bits(value),
        8,
        true);
    }

    public static void Write(CBORObject value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (value == null) {
        stream.write(0xf6);
      } else {
        value.WriteTo(stream);
      }
    }

    public static void Write(Object objValue, OutputStream stream) throws java.io.IOException {
      Write(objValue, stream, CBOREncodeOptions.Default);
    }

    @SuppressWarnings("unchecked")
public static void Write(
      Object objValue,
      OutputStream output,
      CBOREncodeOptions options) throws java.io.IOException {
      if (options == null) {
        throw new NullPointerException("options");
      }
      if (output == null) {
        throw new NullPointerException("output");
      }
      if (objValue == null) {
        output.write(0xf6);
        return;
      }
      if (options.getCtap2Canonical()) {
        FromObject(objValue).WriteTo(output, options);
        return;
      }
      if (objValue instanceof byte[]) {
        WritePositiveInt(3, ((byte[])objValue).length, output);
        output.write(((byte[])objValue), 0, ((byte[])objValue).length);
        return;
      }
      if (objValue instanceof List<?>) {
        WriteObjectArray(
          (List<CBORObject>)objValue,
          output,
          options);
        return;
      }
      if (objValue instanceof Map<?, ?>) {
        WriteObjectMap(
          (Map<CBORObject, CBORObject>)objValue,
          output,
          options);
        return;
      }
      FromObject(objValue).WriteTo(output, options);
    }

    public static void WriteJSON(Object obj, OutputStream outputStream) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      if (obj == null) {
        outputStream.write(ValueNullBytes, 0, ValueNullBytes.length);
        return;
      }
      if (obj instanceof Boolean) {
        if (((Boolean)obj).booleanValue()) {
          outputStream.write(ValueTrueBytes, 0, ValueTrueBytes.length);
          return;
        }
        outputStream.write(ValueFalseBytes, 0, ValueFalseBytes.length);
        return;
      }
      CBORObject.FromObject(obj).WriteJSONTo(outputStream);
    }

    public CBORObject Add(Object key, Object valueOb) {
      if (this.getType() == CBORType.Map) {
        CBORObject mapKey;
        CBORObject mapValue;
        if (key == null) {
          mapKey = CBORObject.Null;
        } else {
          mapKey = ((key instanceof CBORObject) ? (CBORObject)key : null);
          mapKey = (mapKey == null) ? (CBORObject.FromObject(key)) : mapKey;
        }
        if (valueOb == null) {
          mapValue = CBORObject.Null;
        } else {
          mapValue = ((valueOb instanceof CBORObject) ? (CBORObject)valueOb : null);
          mapValue = (mapValue == null) ? (CBORObject.FromObject(valueOb)) : mapValue;
        }
        Map<CBORObject, CBORObject> map = this.AsMap();
        if (map.containsKey(mapKey)) {
          throw new IllegalArgumentException("Key already exists");
        }
        map.put(
          mapKey,
          mapValue);
      } else {
        throw new IllegalStateException("Not a map");
      }
      return this;
    }

    public CBORObject Add(CBORObject obj) {
      if (this.getType() == CBORType.Array) {
        List<CBORObject> list = this.AsList();
        list.add(obj);
        return this;
      }
      throw new IllegalStateException("Not an array");
    }

    public CBORObject Add(Object obj) {
      if (this.getType() == CBORType.Array) {
        List<CBORObject> list = this.AsList();
        list.add(CBORObject.FromObject(obj));
        return this;
      }
      throw new IllegalStateException("Not an array");
    }

    public boolean AsBoolean() {
      return !this.isFalse() && !this.isNull() && !this.isUndefined();
    }

    public double AsDouble() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      if (cn == null) {
 throw new IllegalStateException("Not a number" +
"\u0020type");
}
 return cn.GetNumberInterface().AsDouble(cn.GetValue());
    }

    public int AsInt32Value() {
      switch (this.getItemType()) {
        case CBORObjectTypeInteger: {
            long longValue = (((Long)this.getThisItem()).longValue());
            if (longValue < Integer.MIN_VALUE || longValue > Integer.MAX_VALUE) {
              throw new ArithmeticException();
            }
 return (int)longValue;
          }
        case CBORObjectTypeEInteger: {
            EInteger ei = (EInteger)this.getThisItem();
            return ei.ToInt32Checked();
          }
        default: throw new IllegalStateException("Not an integer type");
      }
    }

    public long AsInt64Value() {
      switch (this.getItemType()) {
        case CBORObjectTypeInteger:
          return ((Long)this.getThisItem()).longValue();
        case CBORObjectTypeEInteger: {
            EInteger ei = (EInteger)this.getThisItem();
            return ei.ToInt64Checked();
          }
        default: throw new IllegalStateException("Not an integer type");
      }
    }

    public boolean CanValueFitInInt64() {
      switch (this.getItemType()) {
        case CBORObjectTypeInteger:
          return true;
        case CBORObjectTypeEInteger: {
            EInteger ei = (EInteger)this.getThisItem();
            return ei.CanFitInInt64();
          }
        default: return false;
      }
    }

    public boolean CanValueFitInInt32() {
      switch (this.getItemType()) {
        case CBORObjectTypeInteger: {
            long elong = (((Long)this.getThisItem()).longValue());
            return elong >= Integer.MIN_VALUE && elong <= Integer.MAX_VALUE;
          }
        case CBORObjectTypeEInteger: {
            EInteger ei = (EInteger)this.getThisItem();
            return ei.CanFitInInt32();
          }
        default: return false;
      }
    }

    public EInteger AsEIntegerValue() {
      switch (this.getItemType()) {
        case CBORObjectTypeInteger:
          return EInteger.FromInt64((((Long)this.getThisItem()).longValue()));
        case CBORObjectTypeEInteger:
          return (EInteger)this.getThisItem();
        default: throw new IllegalStateException("Not an integer type");
      }
    }

    public long AsDoubleBits() {
      switch (this.getType()) {
        case FloatingPoint:
          return ((Long)this.getThisItem()).longValue();
        default: throw new IllegalStateException("Not a floating-point" +
            "\u0020type");
      }
    }

    public double AsDoubleValue() {
      switch (this.getType()) {
        case FloatingPoint:
          return CBORUtilities.Int64BitsToDouble((((Long)this.getThisItem()).longValue()));
        default: throw new IllegalStateException("Not a floating-point" +
            "\u0020type");
      }
    }

    public CBORNumber AsNumber() {
      CBORNumber num = CBORNumber.FromCBORObject(this);
      if (num == null) {
 throw new IllegalStateException("Not a number type");
}
 return num;
    }

    public int AsInt32() {
      return this.AsInt32(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public float AsSingle() {
      CBORNumber cn = this.AsNumber();
      return cn.GetNumberInterface().AsSingle(cn.GetValue());
    }

    public String AsString() {
      int type = this.getItemType();
      switch (type) {
        case CBORObjectTypeTextString:
        case CBORObjectTypeTextStringAscii: {
            return (String)this.getThisItem();
          }
        case CBORObjectTypeTextStringUtf8: {
            return com.upokecenter.util.DataUtilities.GetUtf8String((byte[])this.getThisItem(), false);
          }
        default:
          throw new IllegalStateException("Not a text String type");
      }
    }

    private static String Chop(String str) {
      return str.substring(0, Math.min(100, str.length()));
    }

    @SuppressWarnings("unchecked")
public int compareTo(CBORObject other) {
      if (other == null) {
        return 1;
      }
      if (this == other) {
        return 0;
      }
      int typeA = this.itemtypeValue;
      int typeB = other.itemtypeValue;
      Object objA = this.itemValue;
      Object objB = other.itemValue;
      // System.out.println("typeA=" + typeA);
      // System.out.println("typeB=" + typeB);
      // System.out.println("objA=" + Chop(this.getItemType() ==
      // CBORObjectTypeMap ? "(map)" :
      // this.toString()));
      // System.out.println("objB=" + Chop(other.getItemType() ==
      // CBORObjectTypeMap ? "(map)" :
      // other.toString()));
      int cmp;
      if (typeA == typeB) {
        switch (typeA) {
          case CBORObjectTypeInteger: {
              long a = (((Long)objA).longValue());
              long b = (((Long)objB).longValue());
              if (a >= 0 && b >= 0) {
                cmp = (a == b) ? 0 : ((a < b) ? -1 : 1);
              } else if (a <= 0 && b <= 0) {
                cmp = (a == b) ? 0 : ((a < b) ? 1 : -1);
              } else if (a < 0 && b >= 0) {
                // NOTE: Negative integers sort after
                // nonnegative integers in the bytewise
                // ordering of CBOR encodings
                cmp = 1;
              } else {
                cmp = -1;
              }
              break;
            }
          case CBORObjectTypeEInteger: {
              cmp = CBORUtilities.ByteArrayCompare(
                  this.EncodeToBytes(),
                  other.EncodeToBytes());
              break;
            }
          case CBORObjectTypeByteString:
          case CBORObjectTypeTextStringUtf8: {
              cmp = CBORUtilities.ByteArrayCompareLengthFirst((byte[])objA,
                  (byte[])objB);
              break;
            }
          case CBORObjectTypeTextStringAscii: {
              String strA = (String)objA;
              String strB = (String)objB;
              int alen = strA.length();
              int blen = strB.length();
              cmp = (alen < blen) ? (-1) : ((alen > blen) ? 1 :
  strA.compareTo(strB));
              break;
            }
          case CBORObjectTypeTextString: {
              String strA = (String)objA;
              String strB = (String)objB;
              cmp = CBORUtilities.CompareStringsAsUtf8LengthFirst(
                  strA,
                  strB);
              break;
            }
          case CBORObjectTypeArray: {
              cmp = ListCompare(
                  (ArrayList<CBORObject>)objA,
                  (ArrayList<CBORObject>)objB);
              break;
            }
          case CBORObjectTypeMap:
            cmp = MapCompare(
                (Map<CBORObject, CBORObject>)objA,
                (Map<CBORObject, CBORObject>)objB);
            break;
          case CBORObjectTypeTagged:
            cmp = this.getMostOuterTag().compareTo(other.getMostOuterTag());
            if (cmp == 0) {
              cmp = ((CBORObject)objA).compareTo((CBORObject)objB);
            }
            break;
          case CBORObjectTypeSimpleValue: {
              int valueA = ((Integer)objA).intValue();
              int valueB = ((Integer)objB).intValue();
              cmp = (valueA == valueB) ? 0 : ((valueA < valueB) ? -1 : 1);
              break;
            }
          case CBORObjectTypeDouble: {
              cmp = CBORUtilities.ByteArrayCompare(
                  GetDoubleBytes(this.AsDoubleBits(), 0),
                  GetDoubleBytes(other.AsDoubleBits(), 0));
              break;
            }
          default: throw new IllegalStateException("Unexpected data " +
              "type");
        }
      } else if ((typeB == CBORObjectTypeInteger && typeA ==
          CBORObjectTypeEInteger) || (typeA == CBORObjectTypeInteger && typeB ==
          CBORObjectTypeEInteger)) {
        cmp = CBORUtilities.ByteArrayCompare(
            this.EncodeToBytes(),
            other.EncodeToBytes());
      } else if ((typeB == CBORObjectTypeTextString || typeB ==
CBORObjectTypeTextStringAscii) && typeA ==
        CBORObjectTypeTextStringUtf8) {
        cmp = -CBORUtilities.CompareUtf16Utf8LengthFirst(
            (String)objB,
            (byte[])objA);
      } else if ((typeA == CBORObjectTypeTextString || typeA ==
CBORObjectTypeTextStringAscii) && typeB ==
        CBORObjectTypeTextStringUtf8) {
        cmp = CBORUtilities.CompareUtf16Utf8LengthFirst(
            (String)objA,
            (byte[])objB);
      } else if ((typeA == CBORObjectTypeTextString && typeB ==
CBORObjectTypeTextStringAscii) ||
         (typeB == CBORObjectTypeTextString && typeA ==
CBORObjectTypeTextStringAscii)) {
        cmp = -CBORUtilities.CompareStringsAsUtf8LengthFirst(
            (String)objB,
            (String)objA);
      } else if ((typeA == CBORObjectTypeTextString || typeA ==
CBORObjectTypeTextStringAscii) && typeB ==
        CBORObjectTypeTextStringUtf8) {
        cmp = CBORUtilities.CompareUtf16Utf8LengthFirst(
            (String)objA,
            (byte[])objB);
      } else {
        int ta = (typeA == CBORObjectTypeTextStringUtf8 || typeA ==
CBORObjectTypeTextStringAscii) ?
          CBORObjectTypeTextString : typeA;
        int tb = (typeB == CBORObjectTypeTextStringUtf8 || typeB ==
CBORObjectTypeTextStringAscii) ?
          CBORObjectTypeTextString : typeB;
        /* NOTE: itemtypeValue numbers are ordered such that they
        // correspond to the lexicographical order of their CBOR encodings
        // (with the exception of Integer and EInteger together,
        // and TextString/TextStringUtf8) */
        cmp = (ta < tb) ? -1 : 1;
      }
      // System.out.println(" -> " + (cmp));
      return cmp;
    }

    public int CompareToIgnoreTags(CBORObject other) {
      return (other == null) ? 1 : ((this == other) ? 0 :
          this.Untag().compareTo(other.Untag()));
    }

    public boolean ContainsKey(Object objKey) {
      return (this.getType() == CBORType.Map) &&
this.ContainsKey(CBORObject.FromObject(objKey));
    }

    public boolean ContainsKey(CBORObject key) {
      key = (key == null) ? (CBORObject.Null) : key;
      if (this.getType() == CBORType.Map) {
        Map<CBORObject, CBORObject> map = this.AsMap();
        return map.containsKey(key);
      }
      return false;
    }

    public boolean ContainsKey(String key) {
      if (this.getType() == CBORType.Map) {
        CBORObject ckey = key == null ? CBORObject.Null :
          CBORObject.FromObject(key);
        Map<CBORObject, CBORObject> map = this.AsMap();
        return map.containsKey(ckey);
      }
      return false;
    }

    private static byte[] GetDoubleBytes64(long valueBits, int tagbyte) {
      // Encode as double precision
      return tagbyte != 0 ? new byte[] { (byte)tagbyte, (byte)0xfb,
        (byte)((valueBits >> 56) & 0xff), (byte)((valueBits >> 48) & 0xff),
        (byte)((valueBits >> 40) & 0xff), (byte)((valueBits >> 32) & 0xff),
        (byte)((valueBits >> 24) & 0xff), (byte)((valueBits >> 16) & 0xff),
        (byte)((valueBits >> 8) & 0xff), (byte)(valueBits & 0xff),
       } : new byte[] { (byte)0xfb, (byte)((valueBits >> 56) & 0xff),
   (byte)((valueBits >> 48) & 0xff), (byte)((valueBits >> 40) & 0xff),
   (byte)((valueBits >> 32) & 0xff), (byte)((valueBits >> 24) & 0xff),
   (byte)((valueBits >> 16) & 0xff), (byte)((valueBits >> 8) & 0xff),
   (byte)(valueBits & 0xff),
  };
    }

    private static byte[] GetDoubleBytes(long valueBits, int tagbyte) {
      int bits = CBORUtilities.DoubleToHalfPrecisionIfSameValue(valueBits);
      if (bits != -1) {
        return tagbyte != 0 ? new byte[] { (byte)tagbyte, (byte)0xf9,
          (byte)((bits >> 8) & 0xff), (byte)(bits & 0xff),
         } : new byte[] { (byte)0xf9, (byte)((bits >> 8) & 0xff),
   (byte)(bits & 0xff),
  };
      }
      if (CBORUtilities.DoubleRetainsSameValueInSingle(valueBits)) {
        bits = CBORUtilities.DoubleToRoundedSinglePrecision(valueBits);
        return tagbyte != 0 ? new byte[] { (byte)tagbyte, (byte)0xfa,
          (byte)((bits >> 24) & 0xff), (byte)((bits >> 16) & 0xff),
          (byte)((bits >> 8) & 0xff), (byte)(bits & 0xff),
         } : new byte[] { (byte)0xfa, (byte)((bits >> 24) & 0xff),
   (byte)((bits >> 16) & 0xff), (byte)((bits >> 8) & 0xff),
   (byte)(bits & 0xff),
  };
      }
      return GetDoubleBytes64(valueBits, tagbyte);
    }

    public byte[] EncodeToBytes() {
      return this.EncodeToBytes(CBOREncodeOptions.Default);
    }

    public byte[] EncodeToBytes(CBOREncodeOptions options) {
      if (options == null) {
        throw new NullPointerException("options");
      }
      if (options.getCtap2Canonical()) {
        return CBORCanonical.CtapCanonicalEncode(this);
      }
      // For some types, a memory stream is a lot of
      // overhead since the amount of memory the types
      // use is fixed and small
      boolean hasComplexTag = false;
      byte tagbyte = 0;
      boolean tagged = this.isTagged();
      if (this.isTagged()) {
        CBORObject taggedItem = (CBORObject)this.itemValue;
        if (taggedItem.isTagged() || this.tagHigh != 0 ||
          (this.tagLow >> 16) != 0 || this.tagLow >= 24) {
          hasComplexTag = true;
        } else {
          tagbyte = (byte)(0xc0 + this.tagLow);
        }
      }
      if (!hasComplexTag) {
        switch (this.getItemType()) {
          case CBORObjectTypeTextString:
          case CBORObjectTypeTextStringAscii: {
              byte[] ret = GetOptimizedBytesIfShortAscii(
                this.AsString(),
                tagged ? (tagbyte & 0xff) : -1);
              if (ret != null) {
                return ret;
              }
              break;
            }
          case CBORObjectTypeTextStringUtf8: {
              if (!tagged && !options.getUseIndefLengthStrings()) {
                byte[] bytes = (byte[])this.getThisItem();
                return SerializeUtf8(bytes);
              }
              break;
            }
          case CBORObjectTypeSimpleValue: {
              if (tagged) {
                if (this.isFalse()) {
                  return new byte[] { (byte)tagbyte, (byte)0xf4 };
                }
                if (this.isTrue()) {
                  return new byte[] { (byte)tagbyte, (byte)0xf5 };
                }
                if (this.isNull()) {
                  return new byte[] { (byte)tagbyte, (byte)0xf6 };
                }
                if (this.isUndefined()) {
                  return new byte[] { (byte)tagbyte, (byte)0xf7 };
                }
              } else {
                if (this.isFalse()) {
                  return new byte[] { (byte)0xf4 };
                }
                if (this.isTrue()) {
                  return new byte[] { (byte)0xf5 };
                }
                if (this.isNull()) {
                  return new byte[] { (byte)0xf6 };
                }
                if (this.isUndefined()) {
                  return new byte[] { (byte)0xf7 };
                }
              }
              break;
            }
          case CBORObjectTypeInteger: {
              long value = (((Long)this.getThisItem()).longValue());
              byte[] intBytes;
              if (value >= 0) {
                intBytes = GetPositiveInt64Bytes(0, value);
              } else {
                ++value;
                value = -value; // Will never overflow
                intBytes = GetPositiveInt64Bytes(1, value);
              }
              if (!tagged) {
                return intBytes;
              }
              byte[] ret2 = new byte[intBytes.length + 1];
              System.arraycopy(intBytes, 0, ret2, 1, intBytes.length);
              ret2[0] = tagbyte;
              return ret2;
            }
          case CBORObjectTypeDouble: {
              return options.getFloat64() ?
                GetDoubleBytes64(this.AsDoubleBits(), tagbyte & 0xff) :
                GetDoubleBytes(this.AsDoubleBits(), tagbyte & 0xff);
            }
          default:
            break;
        }
      }
      try {
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream(16);

          this.WriteTo(ms, options);
          return ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ex) {
        throw new CBORException("I/O Error occurred", ex);
      }
    }

    public CBORObject AtJSONPointer(String pointer) {
      CBORObject ret = this.AtJSONPointer(pointer, null);
      if (ret == null) {
 throw new CBORException("Invalid JSON pointer");
}
 return ret;
    }

    public CBORObject AtJSONPointer(String pointer, CBORObject defaultValue) {
      return JSONPointer.GetObject(this, pointer, null);
    }

    public CBORObject ApplyJSONPatch(CBORObject patch) {
      return JSONPatch.Patch(this, patch);
    }

    @Override public boolean equals(Object obj) {
      return this.equals(((obj instanceof CBORObject) ? (CBORObject)obj : null));
    }

    @SuppressWarnings("unchecked")
public boolean equals(CBORObject other) {
      CBORObject otherValue = other;
      if (otherValue == null) {
        return false;
      }
      if (this == otherValue) {
        return true;
      }
      if ((this.itemtypeValue == CBORObjectTypeTextString ||
this.itemtypeValue == CBORObjectTypeTextStringAscii) &&
        otherValue.itemtypeValue == CBORObjectTypeTextStringUtf8) {
        return CBORUtilities.StringEqualsUtf8(
            (String)this.itemValue,
            (byte[])otherValue.itemValue);
      }
      if ((otherValue.itemtypeValue == CBORObjectTypeTextString ||
otherValue.itemtypeValue == CBORObjectTypeTextStringAscii) &&
        this.itemtypeValue == CBORObjectTypeTextStringUtf8) {
        return CBORUtilities.StringEqualsUtf8(
            (String)otherValue.itemValue,
            (byte[])this.itemValue);
      }
      if ((otherValue.itemtypeValue == CBORObjectTypeTextString &&
this.itemtypeValue == CBORObjectTypeTextStringAscii) || (this.itemtypeValue
== CBORObjectTypeTextString && otherValue.itemtypeValue ==
CBORObjectTypeTextStringAscii)) {
        return ((this.itemValue) == null) ? ((otherValue.itemValue) == null) : (this.itemValue).equals(otherValue.itemValue);
      }
      if (this.itemtypeValue != otherValue.itemtypeValue) {
        return false;
      }
      switch (this.itemtypeValue) {
        case CBORObjectTypeByteString:
        case CBORObjectTypeTextStringUtf8:
          return CBORUtilities.ByteArrayEquals(
              (byte[])this.itemValue,
              ((otherValue.itemValue instanceof byte[]) ? (byte[])otherValue.itemValue : null));
        case CBORObjectTypeMap: {
            return CBORMapEquals(
              this.AsMap(),
              ((otherValue.itemValue instanceof Map<?, ?>) ? (Map<CBORObject, CBORObject>)otherValue.itemValue : null));
          }
        case CBORObjectTypeArray:
          return CBORArrayEquals(
              this.AsList(),
              ((otherValue.itemValue instanceof List<?>) ? (List<CBORObject>)otherValue.itemValue : null));
        case CBORObjectTypeTagged:
          return this.tagLow == otherValue.tagLow &&
            this.tagHigh == otherValue.tagHigh &&
            (((this.itemValue) == null) ? ((otherValue.itemValue) == null) : (this.itemValue).equals(otherValue.itemValue));
        case CBORObjectTypeDouble:
          return this.AsDoubleBits() == otherValue.AsDoubleBits();
        default: return ((this.itemValue) == null) ? ((otherValue.itemValue) == null) : (this.itemValue).equals(otherValue.itemValue);
      }
    }

    public byte[] GetByteString() {
      if (!(this.getItemType() == CBORObjectTypeByteString)) {
 throw new IllegalStateException("Not a byte" +
"\u0020string");
}
 return (byte[])this.getThisItem();
    }

    @Override public int hashCode() {
      int valueHashCode = 651869431;
      {
        if (this.itemValue != null) {
          int itemHashCode;
          long longValue;
          switch (this.itemtypeValue) {
            case CBORObjectTypeByteString:
              itemHashCode =
                CBORUtilities.ByteArrayHashCode(this.GetByteString());
              break;
            case CBORObjectTypeTextStringUtf8:
              itemHashCode = CBORUtilities.Utf8HashCode(
                  (byte[])this.itemValue);
              break;
            case CBORObjectTypeMap:
              itemHashCode = CBORMapHashCode(this.AsMap());
              break;
            case CBORObjectTypeArray:
              itemHashCode = CBORArrayHashCode(this.AsList());
              break;
            case CBORObjectTypeTextString:
            case CBORObjectTypeTextStringAscii:
              itemHashCode = CBORUtilities.StringHashCode(
                  (String)this.itemValue);
              break;
            case CBORObjectTypeSimpleValue:
              itemHashCode = ((Integer)this.itemValue).intValue();
              break;
            case CBORObjectTypeDouble:
              longValue = this.AsDoubleBits();
              longValue |= longValue >> 32;
              itemHashCode = ((int)longValue);
              break;
            case CBORObjectTypeInteger:
              longValue = (((Long)this.itemValue).longValue());
              longValue |= longValue >> 32;
              itemHashCode = ((int)longValue);
              break;
            case CBORObjectTypeTagged:
              itemHashCode = (this.tagLow + this.tagHigh);
              itemHashCode += 651869483 * this.itemValue.hashCode();
              break;
            default:
              // EInteger, CBORObject
              itemHashCode = this.itemValue.hashCode();
              break;
          }
          valueHashCode += 651869479 * itemHashCode;
        }
      }
      return valueHashCode;
    }

    public EInteger[] GetAllTags() {
      if (!this.isTagged()) {
        return ValueEmptyTags;
      }
      CBORObject curitem = this;
      if (curitem.isTagged()) {
        ArrayList<EInteger> extIntegerList = new ArrayList<EInteger>();
        while (curitem.isTagged()) {
          extIntegerList.add(
            LowHighToEInteger(
              curitem.tagLow,
              curitem.tagHigh));
          curitem = (CBORObject)curitem.itemValue;
        }
        return extIntegerList.toArray(new EInteger[] { });
      }
      return new EInteger[] { LowHighToEInteger(this.tagLow, this.tagHigh) };
    }

    public boolean HasOneTag() {
      return this.isTagged() && !((CBORObject)this.itemValue).isTagged();
    }

    public boolean HasOneTag(int tagValue) {
      return this.HasOneTag() && this.HasMostOuterTag(tagValue);
    }

    public boolean HasOneTag(EInteger bigTagValue) {
      return this.HasOneTag() && this.HasMostOuterTag(bigTagValue);
    }

    public final int getTagCount() {
        int count = 0;
        CBORObject curitem = this;
        while (curitem.isTagged()) {
          count = (count + 1);
          curitem = (CBORObject)curitem.itemValue;
        }
        return count;
      }

    public boolean HasMostInnerTag(int tagValue) {
      if (tagValue < 0) {
 throw new IllegalArgumentException("tagValue(" + tagValue +
          ") is less than 0");
}
 return this.isTagged() && this.HasMostInnerTag(
          EInteger.FromInt32(tagValue));
    }

    public boolean HasMostInnerTag(EInteger bigTagValue) {
      if (bigTagValue == null) {
 throw new NullPointerException("bigTagValue");
}
 if (bigTagValue.signum() < 0) {
 throw new IllegalArgumentException("bigTagValue(" + bigTagValue +
          ") is less than 0");
}
 return this.isTagged() && this.getMostInnerTag().equals(bigTagValue);
    }

    public boolean HasMostOuterTag(int tagValue) {
      if (tagValue < 0) {
 throw new IllegalArgumentException("tagValue(" + tagValue +
          ") is less than 0");
}
 return this.isTagged() && this.tagHigh == 0 && this.tagLow == tagValue;
    }

    public boolean HasMostOuterTag(EInteger bigTagValue) {
      if (bigTagValue == null) {
 throw new NullPointerException("bigTagValue");
}
 if (bigTagValue.signum() < 0) {
 throw new IllegalArgumentException("bigTagValue(" + bigTagValue +
          ") is less than 0");
}
 return this.isTagged() && this.getMostOuterTag().equals(bigTagValue);
    }

    public boolean HasTag(int tagValue) {
      if (tagValue < 0) {
        throw new IllegalArgumentException("tagValue(" + tagValue +
          ") is less than 0");
      }
      CBORObject obj = this;
      while (true) {
        if (!obj.isTagged()) {
          return false;
        }
        if (obj.tagHigh == 0 && tagValue == obj.tagLow) {
          return true;
        }
        obj = (CBORObject)obj.itemValue;
      }
    }

    public boolean HasTag(EInteger bigTagValue) {
      if (bigTagValue == null) {
        throw new NullPointerException("bigTagValue");
      }
      if (bigTagValue.signum() < 0) {
        throw new IllegalArgumentException("doesn't satisfy bigTagValue.signum()>= 0");
      }
      EInteger[] bigTags = this.GetAllTags();
      for (EInteger bigTag : bigTags) {
        if (bigTagValue.equals(bigTag)) {
          return true;
        }
      }
      return false;
    }

    public CBORObject Insert(int index, Object valueOb) {
      if (this.getType() == CBORType.Array) {
        CBORObject mapValue;
        List<CBORObject> list = this.AsList();
        if (index < 0 || index > list.size()) {
          throw new IllegalArgumentException("index");
        }
        if (valueOb == null) {
          mapValue = CBORObject.Null;
        } else {
          mapValue = ((valueOb instanceof CBORObject) ? (CBORObject)valueOb : null);
          mapValue = (mapValue == null) ? (CBORObject.FromObject(valueOb)) : mapValue;
        }
        list.add(
          index,
          mapValue);
      } else {
        throw new IllegalStateException("Not an array");
      }
      return this;
    }

    public void Clear() {
      if (this.getType() == CBORType.Array) {
        List<CBORObject> list = this.AsList();
        list.clear();
      } else if (this.getType() == CBORType.Map) {
        Map<CBORObject, CBORObject> dict = this.AsMap();
        dict.clear();
      } else {
        throw new IllegalStateException("Not a map or array");
      }
    }

    public boolean Remove(Object obj) {
      return this.Remove(CBORObject.FromObject(obj));
    }

    public boolean RemoveAt(int index) {
      if (this.getItemType() != CBORObjectTypeArray) {
        throw new IllegalStateException("Not an array");
      }
      if (index < 0 || index >= this.size()) {
        return false;
      }
      List<CBORObject> list = this.AsList();
      list.remove(index);
      return true;
    }

    public boolean Remove(CBORObject obj) {
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      if (this.getType() == CBORType.Map) {
        Map<CBORObject, CBORObject> dict = this.AsMap();
        return PropertyMap.DictRemove(dict, obj);
      }
      if (this.getType() == CBORType.Array) {
        List<CBORObject> list = this.AsList();
        return list.remove(obj);
      }
      throw new IllegalStateException("Not a map or array");
    }

    public CBORObject Set(Object key, Object valueOb) {
      if (this.getType() == CBORType.Map) {
        CBORObject mapKey;
        CBORObject mapValue;
        if (key == null) {
          mapKey = CBORObject.Null;
        } else {
          mapKey = ((key instanceof CBORObject) ? (CBORObject)key : null);
          mapKey = (mapKey == null) ? (CBORObject.FromObject(key)) : mapKey;
        }
        if (valueOb == null) {
          mapValue = CBORObject.Null;
        } else {
          mapValue = ((valueOb instanceof CBORObject) ? (CBORObject)valueOb : null);
          mapValue = (mapValue == null) ? (CBORObject.FromObject(valueOb)) : mapValue;
        }
        Map<CBORObject, CBORObject> map = this.AsMap();
        map.put(mapKey, mapValue);
      } else if (this.getType() == CBORType.Array) {
        if (key instanceof Integer) {
          List<CBORObject> list = this.AsList();
          int index = ((Integer)key).intValue();
          if (index < 0 || index >= this.size()) {
            throw new IllegalArgumentException("key");
          }
          CBORObject mapValue;
          if (valueOb == null) {
            mapValue = CBORObject.Null;
          } else {
            mapValue = ((valueOb instanceof CBORObject) ? (CBORObject)valueOb : null);
            mapValue = (mapValue == null) ? (CBORObject.FromObject(valueOb)) : mapValue;
          }
          list.set(index, mapValue);
        } else {
          throw new IllegalArgumentException("Is an array, but key is not int");
        }
      } else {
        throw new IllegalStateException("Not a map or array");
      }
      return this;
    }

    public String ToJSONString() {
      return this.ToJSONString(JSONOptions.Default);
    }

    public String ToJSONString(JSONOptions options) {
      if (options == null) {
        throw new NullPointerException("options");
      }
      CBORType type = this.getType();
      switch (type) {
        case Boolean:
        case SimpleValue: {
            return this.isTrue() ? "true" : (this.isFalse() ? "false" : "null");
          }
        case Integer: {
            return this.AsEIntegerValue().toString();
          }
        case FloatingPoint: {
            long dblbits = this.AsDoubleBits();
            return CBORUtilities.DoubleBitsFinite(dblbits) ?
                 CBORUtilities.DoubleBitsToString(dblbits) : "null";
          }
        default:
          {
            StringBuilder sb = new StringBuilder();
            try {
              CBORJsonWriter.WriteJSONToInternal(
                this,
                new StringOutput(sb),
                options);
            } catch (IOException ex) {
              // This is truly exceptional
              throw new IllegalStateException("Internal error", ex);
            }
            return sb.toString();
          }
      }
    }

    @Override public String toString() {
      return CBORDataUtilities.ToStringHelper(this, 0);
    }

    public CBORObject Untag() {
      CBORObject curobject = this;
      while (curobject.itemtypeValue == CBORObjectTypeTagged) {
        curobject = (CBORObject)curobject.itemValue;
      }
      return curobject;
    }

    public CBORObject UntagOne() {
      return (this.itemtypeValue == CBORObjectTypeTagged) ?
        ((CBORObject)this.itemValue) : this;
    }

    public void WriteJSONTo(OutputStream outputStream) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      CBORJsonWriter.WriteJSONToInternal(
        this,
        new StringOutput(outputStream),
        JSONOptions.Default);
    }

    public void WriteJSONTo(OutputStream outputStream, JSONOptions options) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      CBORJsonWriter.WriteJSONToInternal(
        this,
        new StringOutput(outputStream),
        options);
    }

    public static CBORObject FromFloatingPointBits(
      long floatingBits,
      int byteCount) {
      long value;
      switch (byteCount) {
        case 2:
          value = CBORUtilities.HalfToDoublePrecision(
              ((int)(floatingBits & 0xffffL)));
          return new CBORObject(CBORObjectTypeDouble, value);
        case 4:

          value = CBORUtilities.SingleToDoublePrecision(
              ((int)(floatingBits & 0xffffffffL)));
          return new CBORObject(CBORObjectTypeDouble, value);
        case 8:
          return new CBORObject(CBORObjectTypeDouble, floatingBits);
        default: throw new IllegalArgumentException("byteCount");
      }
    }

    public static int WriteFloatingPointBits(
      OutputStream outputStream,
      long floatingBits,
      int byteCount) throws java.io.IOException {
      return WriteFloatingPointBits(
          outputStream,
          floatingBits,
          byteCount,
          false);
    }

    public static int WriteFloatingPointBits(
      OutputStream outputStream,
      long floatingBits,
      int byteCount,
      boolean shortestForm) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      if (shortestForm) {
        if (byteCount == 8) {
          int bits =
            CBORUtilities.DoubleToHalfPrecisionIfSameValue(floatingBits);
          if (bits != -1) {
            return WriteFloatingPointBits(outputStream, bits, 2, false);
          }
          if (CBORUtilities.DoubleRetainsSameValueInSingle(floatingBits)) {
            bits = CBORUtilities.DoubleToRoundedSinglePrecision(floatingBits);
            return WriteFloatingPointBits(outputStream, bits, 4, false);
          }
        } else if (byteCount == 4) {
          int bits =
            CBORUtilities.SingleToHalfPrecisionIfSameValue(floatingBits);
          if (bits != -1) {
            return WriteFloatingPointBits(outputStream, bits, 2, false);
          }
        }
      }
      byte[] bytes;
      switch (byteCount) {
        case 2:
          bytes = new byte[] {
            (byte)0xf9,
            (byte)((floatingBits >> 8) & 0xffL),
            (byte)(floatingBits & 0xffL),
           };
          outputStream.write(bytes, 0, 3);
          return 3;
        case 4:
          bytes = new byte[] {
            (byte)0xfa,
            (byte)((floatingBits >> 24) & 0xffL),
            (byte)((floatingBits >> 16) & 0xffL),
            (byte)((floatingBits >> 8) & 0xffL),
            (byte)(floatingBits & 0xffL),
           };
          outputStream.write(bytes, 0, 5);
          return 5;
        case 8:
          bytes = new byte[] {
            (byte)0xfb,
            (byte)((floatingBits >> 56) & 0xffL),
            (byte)((floatingBits >> 48) & 0xffL),
            (byte)((floatingBits >> 40) & 0xffL),
            (byte)((floatingBits >> 32) & 0xffL),
            (byte)((floatingBits >> 24) & 0xffL),
            (byte)((floatingBits >> 16) & 0xffL),
            (byte)((floatingBits >> 8) & 0xffL),
            (byte)(floatingBits & 0xffL),
           };
          outputStream.write(bytes, 0, 9);
          return 9;
        default: throw new IllegalArgumentException("byteCount");
      }
    }

    public static int WriteFloatingPointValue(
      OutputStream outputStream,
      double doubleVal,
      int byteCount) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      long bits;
      switch (byteCount) {
        case 2:
          bits = CBORUtilities.DoubleToInt64Bits(doubleVal);
          bits = CBORUtilities.DoubleToRoundedHalfPrecision(bits);
          bits &= 0xffffL;
          return WriteFloatingPointBits(outputStream, bits, 2);
        case 4:
          bits = CBORUtilities.DoubleToInt64Bits(doubleVal);
          bits = CBORUtilities.DoubleToRoundedSinglePrecision(bits);
          bits &= 0xffffffffL;
          return WriteFloatingPointBits(outputStream, bits, 4);
        case 8:
          bits = CBORUtilities.DoubleToInt64Bits(doubleVal);
          return WriteFloatingPointBits(outputStream, bits, 8);
        default: throw new IllegalArgumentException("byteCount");
      }
    }

    public static int WriteFloatingPointValue(
      OutputStream outputStream,
      float singleVal,
      int byteCount) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }

      int bits;
      long longbits;
      switch (byteCount) {
        case 2:
          bits = Float.floatToRawIntBits(singleVal);
          bits = CBORUtilities.SingleToRoundedHalfPrecision(bits);
          bits &= 0xffff;
          return WriteFloatingPointBits(outputStream, bits, 2);
        case 4:
          bits = Float.floatToRawIntBits(singleVal);
          longbits = bits & 0xffffffffL;
          return WriteFloatingPointBits(outputStream, longbits, 4);
        case 8:
          bits = Float.floatToRawIntBits(singleVal);
          longbits = CBORUtilities.SingleToDoublePrecision(bits);
          return WriteFloatingPointBits(outputStream, longbits, 8);
        default: throw new IllegalArgumentException("byteCount");
      }
    }

    public static int WriteValue(
      OutputStream outputStream,
      int majorType,
      long value) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      if (majorType < 0) {
        throw new IllegalArgumentException("majorType(" + majorType +
          ") is less than 0");
      }
      if (majorType > 7) {
        throw new IllegalArgumentException("majorType(" + majorType +
          ") is more than 7");
      }
      if (value < 0) {
        throw new IllegalArgumentException("value(" + value +
          ") is less than 0");
      }
      if (majorType == 7) {
        if (value > 255) {
          throw new IllegalArgumentException("value(" + value +
            ") is more than 255");
        }
        if (value <= 23) {
          outputStream.write((byte)(0xe0 + (int)value));
          return 1;
        } else if (value < 32) {
          throw new IllegalArgumentException("value is from 24 to 31 and major" +
            " type is 7");
        } else {
          outputStream.write(0xf8);
          outputStream.write((byte)value);
          return 2;
        }
      } else {
        return WritePositiveInt64(majorType, value, outputStream);
      }
    }

    public static int WriteValue(
      OutputStream outputStream,
      int majorType,
      int value) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      if (majorType < 0) {
        throw new IllegalArgumentException("majorType(" + majorType +
          ") is less than 0");
      }
      if (majorType > 7) {
        throw new IllegalArgumentException("majorType(" + majorType +
          ") is more than 7");
      }
      if (value < 0) {
        throw new IllegalArgumentException("value(" + value +
          ") is less than 0");
      }
      if (majorType == 7) {
        if (value > 255) {
          throw new IllegalArgumentException("value(" + value +
            ") is more than 255");
        }
        if (value <= 23) {
          outputStream.write((byte)(0xe0 + value));
          return 1;
        } else if (value < 32) {
          throw new IllegalArgumentException("value is from 24 to 31 and major" +
            "\u0020type" + "\u0020is 7");
        } else {
          outputStream.write(0xf8);
          outputStream.write((byte)value);
          return 2;
        }
      } else {
        return WritePositiveInt(majorType, value, outputStream);
      }
    }

    public static int WriteValue(
      OutputStream outputStream,
      int majorType,
      EInteger bigintValue) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      if (bigintValue == null) {
        throw new NullPointerException("bigintValue");
      }
      if (bigintValue.signum() < 0) {
        throw new IllegalArgumentException("tagEInt's sign(" + bigintValue.signum() +
          ") is less than 0");
      }
      if (bigintValue.compareTo(UInt64MaxValue) > 0) {
        throw new IllegalArgumentException(
          "tag more than 18446744073709551615 (" + bigintValue + ")");
      }
      if (bigintValue.CanFitInInt64()) {
        return WriteValue(
            outputStream,
            majorType,
            bigintValue.ToInt64Checked());
      }
      long longVal = bigintValue.ToInt64Unchecked();
      int highbyte = (int)((longVal >> 56) & 0xff);
      if (majorType < 0) {
        throw new IllegalArgumentException("majorType(" + majorType +
          ") is less than 0");
      }
      if (majorType > 7) {
        throw new IllegalArgumentException("majorType(" + majorType +
          ") is more than 7");
      }
      if (majorType == 7) {
        throw new IllegalArgumentException(
          "majorType is 7 and value is greater" + "\u0020than 255");
      }
      byte[] bytes = new byte[] { (byte)(27 | (majorType << 5)), (byte)highbyte,
        (byte)((longVal >> 48) & 0xff), (byte)((longVal >> 40) & 0xff),
        (byte)((longVal >> 32) & 0xff), (byte)((longVal >> 24) & 0xff),
        (byte)((longVal >> 16) & 0xff), (byte)((longVal >> 8) & 0xff),
        (byte)(longVal & 0xff),
       };
      outputStream.write(bytes, 0, bytes.length);
      return bytes.length;
    }

    public void WriteTo(OutputStream stream) throws java.io.IOException {
      this.WriteTo(stream, CBOREncodeOptions.Default);
    }

    public void WriteTo(OutputStream stream, CBOREncodeOptions options) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      if (options.getCtap2Canonical()) {
        byte[] bytes = CBORCanonical.CtapCanonicalEncode(this);
        stream.write(bytes, 0, bytes.length);
        return;
      }
      this.WriteTags(stream);
      int type = this.getItemType();
      switch (type) {
        case CBORObjectTypeInteger: {
            Write((((Long)this.getThisItem()).longValue()), stream);
            break;
          }
        case CBORObjectTypeEInteger: {
            Write((EInteger)this.getThisItem(), stream);
            break;
          }
        case CBORObjectTypeByteString:
        case CBORObjectTypeTextStringUtf8: {
            byte[] arr = (byte[])this.getThisItem();
            WritePositiveInt(
              (this.getType() == CBORType.ByteString) ? 2 : 3,
              arr.length,
              stream);
            stream.write(arr, 0, arr.length);
            break;
          }
        case CBORObjectTypeTextString:
        case CBORObjectTypeTextStringAscii: {
            Write((String)this.getThisItem(), stream, options);
            break;
          }
        case CBORObjectTypeArray: {
            WriteObjectArray(this.AsList(), stream, options);
            break;
          }
        case CBORObjectTypeMap: {
            WriteObjectMap(this.AsMap(), stream, options);
            break;
          }
        case CBORObjectTypeSimpleValue: {
            int value = this.getSimpleValue();
            if (value < 24) {
              stream.write((byte)(0xe0 + value));
            } else {
              stream.write(0xf8);
              stream.write((byte)value);
            }

            break;
          }
        case CBORObjectTypeDouble: {
            WriteFloatingPointBits(
               stream,
               this.AsDoubleBits(),
               8,
               !options.getFloat64());
            break;
          }
        default: {
            throw new IllegalArgumentException("Unexpected data type");
          }
      }
    }

    static CBORObject FromRaw(byte[] bytes) {
      return new CBORObject(CBORObjectTypeByteString, bytes);
    }

    static CBORObject FromRawUtf8(byte[] bytes) {
      return new CBORObject(CBORObjectTypeTextStringUtf8, bytes);
    }

    static CBORObject FromRaw(String str) {
      return new CBORObject(CBORObjectTypeTextString, str);
    }

    static CBORObject FromRaw(List<CBORObject> list) {
      return new CBORObject(CBORObjectTypeArray, list);
    }

    static CBORObject FromRaw(Map<CBORObject, CBORObject>
      map) {
      return new CBORObject(CBORObjectTypeMap, map);
    }

    static int GetExpectedLength(int value) {
      return ValueExpectedLengths[value];
    }

    // Generate a CBOR Object for head bytes with fixed length.
    // Note that this function assumes that the length of the data
    // was already checked.
    static CBORObject GetFixedLengthObject(
      int firstbyte,
      byte[] data) {
      CBORObject fixedObj = FixedObjects[firstbyte];
      if (fixedObj != null) {
        return fixedObj;
      }
      int majortype = firstbyte >> 5;
      if ((firstbyte & 0x1c) == 0x18) {
        // contains 1 to 8 extra bytes of additional information
        long uadditional;
        switch (firstbyte & 0x1f) {
          case 24:
            uadditional = data[1] & 0xff;
            break;
          case 25:
            uadditional = (data[1] & 0xffL) << 8;
            uadditional |= data[2] & 0xffL;
            break;
          case 26:
            uadditional = (data[1] & 0xffL) << 24;
            uadditional |= (data[2] & 0xffL) << 16;
            uadditional |= (data[3] & 0xffL) << 8;
            uadditional |= data[4] & 0xffL;
            break;
          case 27:
            uadditional = (data[1] & 0xffL) << 56;
            uadditional |= (data[2] & 0xffL) << 48;
            uadditional |= (data[3] & 0xffL) << 40;
            uadditional |= (data[4] & 0xffL) << 32;
            uadditional |= (data[5] & 0xffL) << 24;
            uadditional |= (data[6] & 0xffL) << 16;
            uadditional |= (data[7] & 0xffL) << 8;
            uadditional |= data[8] & 0xffL;
            break;
          default: throw new CBORException("Unexpected data encountered");
        }
        switch (majortype) {
          case 0:
            if ((uadditional >> 63) == 0) {
              // use only if additional's top bit isn't set
              // (additional is a signed long)
              return new CBORObject(CBORObjectTypeInteger, uadditional);
            } else {
              int low = ((int)(uadditional & 0xffffffffL));
              int high = ((int)((uadditional >> 32) & 0xffffffffL));
              return FromObject(LowHighToEInteger(low, high));
            }
          case 1:
            if ((uadditional >> 63) == 0) {
              // use only if additional's top bit isn't set
              // (additional is a signed long)
              return new CBORObject(
                  CBORObjectTypeInteger,
                  -1 - uadditional);
            } else {
              int low = ((int)(uadditional & 0xffffffffL));
              int high = ((int)((uadditional >> 32) & 0xffffffffL));
              EInteger bigintAdditional = LowHighToEInteger(low, high);
              EInteger minusOne = EInteger.FromInt32(-1);
              bigintAdditional = minusOne.Subtract(bigintAdditional);
              return FromObject(bigintAdditional);
            }
          case 7:
            if (firstbyte >= 0xf9 && firstbyte <= 0xfb) {
              long dblbits = uadditional;
              if (firstbyte == 0xf9) {
                dblbits = CBORUtilities.HalfToDoublePrecision(
                    ((int)uadditional));
              } else if (firstbyte == 0xfa) {
                dblbits = CBORUtilities.SingleToDoublePrecision(
                    ((int)uadditional));
              }
              return new CBORObject(
                  CBORObjectTypeDouble,
                  dblbits);
            }
            if (firstbyte == 0xf8) {
              if ((int)uadditional < 32) {
 throw new CBORException("Invalid overlong simple value");
}
 return new CBORObject(
                  CBORObjectTypeSimpleValue,
                  (int)uadditional);
            }
            throw new CBORException("Unexpected data encountered");
          default: throw new CBORException("Unexpected data encountered");
        }
      }
      if (majortype == 2) { // short byte String
        byte[] ret = new byte[firstbyte - 0x40];
        System.arraycopy(data, 1, ret, 0, firstbyte - 0x40);
        return new CBORObject(CBORObjectTypeByteString, ret);
      }
      if (majortype == 3) { // short text String
        byte[] ret = new byte[firstbyte - 0x60];
        System.arraycopy(data, 1, ret, 0, firstbyte - 0x60);
        if (!CBORUtilities.CheckUtf8(ret)) {
 throw new CBORException("Invalid encoding");
}
 return new CBORObject(CBORObjectTypeTextStringUtf8, ret);
      }
      if (firstbyte == 0x80) {
        // empty array
        return CBORObject.NewArray();
      }
      if (firstbyte == 0xa0) {
        // empty map
        return CBORObject.NewOrderedMap();
      }
      throw new CBORException("Unexpected data encountered");
    }

    static CBORObject GetFixedObject(int value) {
      return FixedObjects[value];
    }

    @SuppressWarnings("unchecked")
private List<CBORObject> AsList() {
      return (List<CBORObject>)this.getThisItem();
    }

    @SuppressWarnings("unchecked")
private Map<CBORObject, CBORObject> AsMap() {
      return (Map<CBORObject, CBORObject>)this.getThisItem();
    }

    private static boolean CBORArrayEquals(
      List<CBORObject> listA,
      List<CBORObject> listB) {
      if (listA == null) {
        return listB == null;
      }
      if (listB == null) {
        return false;
      }
      int listACount = listA.size();
      int listBCount = listB.size();
      if (listACount != listBCount) {
        return false;
      }
      for (int i = 0; i < listACount; ++i) {
        CBORObject itemA = listA.get(i);
        CBORObject itemB = listB.get(i);
        if (!(itemA == null ? itemB == null : itemA.equals(itemB))) {
          return false;
        }
      }
      return true;
    }

    private static int CBORArrayHashCode(List<CBORObject> list) {
      if (list == null) {
        return 0;
      }
      int ret = 19;
      int count = list.size();
      {
        ret = (ret * 31) + count;
        for (int i = 0; i < count; ++i) {
          ret = (ret * 31) + list.get(i).hashCode();
        }
      }
      return ret;
    }

    private static boolean StringEquals(String str, String str2) {
      if (str == str2) {
        return true;
      }
      if (str.length() != str2.length()) {
        return false;
      }
      int count = str.length();
      for (int i = 0; i < count; ++i) {
        if (str.charAt(i) != str2.charAt(i)) {
          return false;
        }
      }
      return true;
    }

    private static boolean CBORMapEquals(
      Map<CBORObject, CBORObject> mapA,
      Map<CBORObject, CBORObject> mapB) {
      if (mapA == null) {
        return mapB == null;
      }
      if (mapB == null) {
        return false;
      }
      if (mapA.size() != mapB.size()) {
        return false;
      }
      for (Map.Entry<CBORObject, CBORObject> kvp : mapA.entrySet()) {
        CBORObject valueB = PropertyMap.GetOrDefault(mapB, kvp.getKey(), null);
        if (valueB == null) {
          return false;
        }
        if (kvp.getValue() == null) {
          // Null (as opposed to CBORObject.Null) values not supported in CBOR maps.
          throw new IllegalStateException();
        }
        if (!kvp.getValue().equals(valueB)) {
          return false;
        }
      }
      return true;
    }

    private static int CBORMapHashCode(Map<CBORObject, CBORObject>
      a) {
      // To simplify matters, we use just the count of
      // the map as the basis for the hash code. More complicated
      // hash code calculation would involve the sum of the hash codes of
      // the map's key-value pairs (an approach that works regardless of the order
      // in which map keys are iterated, because wraparound addition
      // is commutative and associative), but this could take much more time
      // to calculate, especially if the keys and values are very big.
      return a.size() * 19;
    }

    private static void CheckCBORLength(
      long expectedLength,
      long actualLength) {
      if (actualLength < expectedLength) {
        throw new CBORException("Premature end of data");
      }
      if (actualLength > expectedLength) {
        throw new CBORException(
            "Too many bytes. There is data beyond the decoded CBOR Object.");
      }
    }

    private static void CheckCBORLength(int expectedLength, int
      actualLength) {
      if (actualLength < expectedLength) {
        throw new CBORException("Premature end of data");
      }
      if (actualLength > expectedLength) {
        throw new CBORException(
            "Too many bytes. There is data beyond the decoded CBOR Object.");
      }
    }

    private static String ExtendedToString(EFloat ef) {
      if (ef.isFinite() && (ef.getExponent().compareTo(EInteger.FromInt64(2500)) > 0 ||
          ef.getExponent().compareTo(EInteger.FromInt64(-2500)) < 0)) {
        // It can take very long to convert a number with a very high
        // or very low exponent to a decimal String, so do this instead
        return ef.getMantissa() + "p" + ef.getExponent();
      }
      return ef.toString();
    }

    private static byte[] GetOptimizedBytesIfShortAscii(
      String str,
      int tagbyteInt) {
      byte[] bytes;
      if (str.length() <= 255) {
        // The strings will usually be short ASCII strings, so
        // use this optimization
        int offset = 0;
        int length = str.length();
        int extra = (length < 24) ? 1 : 2;
        if (tagbyteInt >= 0) {
          ++extra;
        }
        bytes = new byte[length + extra];
        if (tagbyteInt >= 0) {
          bytes[offset] = (byte)tagbyteInt;
          ++offset;
        }
        if (length < 24) {
          bytes[offset] = (byte)(0x60 + str.length());
          ++offset;
        } else {
          bytes[offset] = 0x78;
          bytes[offset + 1] = (byte)str.length();
          offset += 2;
        }
        boolean issimple = true;
        for (int i = 0; i < str.length(); ++i) {
          char c = str.charAt(i);
          if (c >= 0x80) {
            issimple = false;
            break;
          }
          bytes[i + offset] = ((byte)c);
        }
        if (issimple) {
          return bytes;
        }
      }
      return null;
    }

    private static String GetOptimizedStringIfShortAscii(
      byte[] data,
      int offset) {
      int length = data.length;
      if (length > offset) {
        int nextbyte = data[offset] & 0xff;
        if (nextbyte >= 0x60 && nextbyte < 0x78) {
          int offsetp1 = 1 + offset;
          // Check for type 3 String of short length
          int rightLength = offsetp1 + (nextbyte - 0x60);
          CheckCBORLength(
            rightLength,
            length);
          // Check for all ASCII text
          for (int i = offsetp1; i < length; ++i) {
            if ((data[i] & 0x80) != 0) {
              return null;
            }
          }
          // All ASCII text, so convert to a text String
          // from a char array without having to
          // convert from UTF-8 first
          char[] c = new char[length - offsetp1];
          for (int i = offsetp1; i < length; ++i) {
            c[i - offsetp1] = (char)(data[i] & 0xff);
          }
          return new String(c);
        }
      }
      return null;
    }

    private static byte[] SerializeUtf8(byte[] utf8) {
      byte[] bytes;
      if (utf8.length < 24) {
        bytes = new byte[utf8.length + 1];
        bytes[0] = (byte)(utf8.length | 0x60);
        System.arraycopy(utf8, 0, bytes, 1, utf8.length);
        return bytes;
      }
      if (utf8.length <= 0xffL) {
        bytes = new byte[utf8.length + 2];
        bytes[0] = 0x78;
        bytes[1] = (byte)utf8.length;
        System.arraycopy(utf8, 0, bytes, 2, utf8.length);
        return bytes;
      }
      if (utf8.length <= 0xffffL) {
        bytes = new byte[utf8.length + 3];
        bytes[0] = 0x79;
        bytes[1] = (byte)((utf8.length >> 8) & 0xff);
        bytes[2] = (byte)(utf8.length & 0xff);
        System.arraycopy(utf8, 0, bytes, 3, utf8.length);
        return bytes;
      }
      byte[] posbytes = GetPositiveInt64Bytes(3, utf8.length);
      bytes = new byte[utf8.length + posbytes.length];
      System.arraycopy(posbytes, 0, bytes, 0, posbytes.length);
      System.arraycopy(utf8, 0, bytes, posbytes.length, utf8.length);
      return bytes;
    }

    private static byte[] GetPositiveInt64Bytes(int type, long value) {
      if (value < 0) {
        throw new IllegalArgumentException("value(" + value + ") is less than " +
          "0");
      }
      return value < 24 ? new byte[] { (byte)((byte)value | (byte)(type << 5)) } :
        value <= 0xffL ? new byte[] { (byte)(24 | (type << 5)), (byte)(value & 0xff),
         } : value <= 0xffffL ? new byte[] { (byte)(25 | (type << 5)),
   (byte)((value >> 8) & 0xff), (byte)(value & 0xff),
  } : value <= 0xffffffffL ? new byte[] { (byte)(26 | (type << 5)),
   (byte)((value >> 24) & 0xff), (byte)((value >> 16) & 0xff),
   (byte)((value >> 8) & 0xff), (byte)(value & 0xff),
  } : new byte[] { (byte)(27 | (type << 5)), (byte)((value >> 56) & 0xff),
   (byte)((value >> 48) & 0xff), (byte)((value >> 40) & 0xff),
   (byte)((value >> 32) & 0xff), (byte)((value >> 24) & 0xff),
   (byte)((value >> 16) & 0xff), (byte)((value >> 8) & 0xff),
   (byte)(value & 0xff),
  };
    }

    private static byte[] GetPositiveIntBytes(int type, int value) {
      if (value < 0) {
 throw new IllegalArgumentException("value(" + value + ") is less than " +
          "0");
}
 return value < 24 ?
        new byte[] { (byte)((byte)value | (byte)(type << 5)) } :
        value <= 0xff ? new byte[] { (byte)(24 | (type << 5)), (byte)(value & 0xff),
         } : value <= 0xffff ? new byte[] { (byte)(25 | (type << 5)),
   (byte)((value >> 8) & 0xff), (byte)(value & 0xff),
  } : new byte[] { (byte)(26 | (type << 5)), (byte)((value >> 24) & 0xff),
   (byte)((value >> 16) & 0xff), (byte)((value >> 8) & 0xff),
   (byte)(value & 0xff),
  };
    }

    // Initialize fixed values for certain
    // head bytes
    private static CBORObject[] InitializeFixedObjects() {
      CBORObject[] fixedObjects = new CBORObject[256];
      for (int i = 0; i < 0x18; ++i) {
        fixedObjects[i] = new CBORObject(CBORObjectTypeInteger, (long)i);
      }
      for (int i = 0x20; i < 0x38; ++i) {
        fixedObjects[i] = new CBORObject(
          CBORObjectTypeInteger,
          (long)(-1 - (i - 0x20)));
      }
      fixedObjects[0x60] = new CBORObject(
        CBORObjectTypeTextString,
        "");
      for (int i = 0xe0; i < 0xf8; ++i) {
        fixedObjects[i] = new CBORObject(
          CBORObjectTypeSimpleValue,
          i - 0xe0);
      }
      return fixedObjects;
    }

    private static int ListCompare(
      List<CBORObject> listA,
      List<CBORObject> listB) {
      if (listA == null) {
        return (listB == null) ? 0 : -1;
      }
      if (listB == null) {
        return 1;
      }
      int listACount = listA.size();
      int listBCount = listB.size();
      // NOTE: Compare list counts to conform
      // to bytewise lexicographical ordering
      if (listACount != listBCount) {
        return listACount < listBCount ? -1 : 1;
      }
      for (int i = 0; i < listACount; ++i) {
        int cmp = listA.get(i).compareTo(listB.get(i));
        if (cmp != 0) {
          return cmp;
        }
      }
      return 0;
    }

    private static EInteger LowHighToEInteger(int tagLow, int tagHigh) {
      byte[] uabytes;
      if (tagHigh != 0) {
        uabytes = new byte[9];
        uabytes[7] = (byte)((tagHigh >> 24) & 0xff);
        uabytes[6] = (byte)((tagHigh >> 16) & 0xff);
        uabytes[5] = (byte)((tagHigh >> 8) & 0xff);
        uabytes[4] = (byte)(tagHigh & 0xff);
        uabytes[3] = (byte)((tagLow >> 24) & 0xff);
        uabytes[2] = (byte)((tagLow >> 16) & 0xff);
        uabytes[1] = (byte)((tagLow >> 8) & 0xff);
        uabytes[0] = (byte)(tagLow & 0xff);
        uabytes[8] = 0;
        return EInteger.FromBytes(uabytes, true);
      }
      if (tagLow != 0) {
        uabytes = new byte[5];
        uabytes[3] = (byte)((tagLow >> 24) & 0xff);
        uabytes[2] = (byte)((tagLow >> 16) & 0xff);
        uabytes[1] = (byte)((tagLow >> 8) & 0xff);
        uabytes[0] = (byte)(tagLow & 0xff);
        uabytes[4] = 0;
        return EInteger.FromBytes(uabytes, true);
      }
      return EInteger.FromInt32(0);
    }

    private static int MapCompare(
      Map<CBORObject, CBORObject> mapA,
      Map<CBORObject, CBORObject> mapB) {
      if (mapA == null) {
        return (mapB == null) ? 0 : -1;
      }
      if (mapB == null) {
        return 1;
      }
      if (mapA == mapB) {
        return 0;
      }
      int listACount = mapA.size();
      int listBCount = mapB.size();
      if (listACount == 0 && listBCount == 0) {
        return 0;
      }
      if (listACount == 0) {
        return -1;
      }
      if (listBCount == 0) {
        return 1;
      }
      // NOTE: Compare map key counts to conform
      // to bytewise lexicographical ordering
      if (listACount != listBCount) {
        return listACount < listBCount ? -1 : 1;
      }
      ArrayList<CBORObject> sortedASet = new ArrayList<CBORObject>(PropertyMap.GetSortedKeys(mapA));
      ArrayList<CBORObject> sortedBSet = new ArrayList<CBORObject>(PropertyMap.GetSortedKeys(mapB));
      // System.out.println("---done sorting");
      listACount = sortedASet.size();
      sortedBSet.size();
      // Compare the keys
      /* for (int i = 0; i < listACount; ++i) {
        String str = sortedASet.get(i).toString();
        str = str.substring(0, Math.min(100, str.length()));
        System.out.println("A " + i + "=" + str);
      }
      for (int i = 0; i < listBCount; ++i) {
        String str = sortedBSet.get(i).toString();
        str = str.substring(0, Math.min(100, str.length()));
        System.out.println("B " + i + "=" + str);
      }*/
      for (int i = 0; i < listACount; ++i) {
        CBORObject itemA = sortedASet.get(i);
        CBORObject itemB = sortedBSet.get(i);
        if (itemA == null) {
          return -1;
        }
        int cmp = itemA.compareTo(itemB);
        // String ot = itemA + "/" +
        // (cmp != 0 ? itemB.toString() : "~") +
        // " -> cmp=" + (cmp);
        // System.out.println(ot);
        if (cmp != 0) {
          return cmp;
        }
        // Both maps have the same key, so compare
        // the value under that key
        cmp = mapA.get(itemA).compareTo(mapB.get(itemB));
        // System.out.println(itemA + "/~" +
        // " -> "+mapA.get(itemA)+", "+(cmp != 0 ? mapB.get(itemB).toString() :
        // "~") + " -> cmp=" + cmp);
        if (cmp != 0) {
          return cmp;
        }
      }
      return 0;
    }

    private static List<Object> PushObject(
      List<Object> stack,
      Object parent,
      Object child) {
      if (stack == null) {
        stack = new java.util.ArrayList<Object>(java.util.Arrays.asList(
          parent));
      }
      for (Object o : stack) {
        if (o == child) {
          throw new IllegalArgumentException("Circular reference in data" +
            "\u0020structure");
        }
      }
      stack.add(child);
      return stack;
    }

    private static int TagsCompare(EInteger[] tagsA, EInteger[] tagsB) {
      if (tagsA == null) {
        return (tagsB == null) ? 0 : -1;
      }
      if (tagsB == null) {
        return 1;
      }
      int listACount = tagsA.length;
      int listBCount = tagsB.length;
      int c = Math.min(listACount, listBCount);
      for (int i = 0; i < c; ++i) {
        int cmp = tagsA[i].compareTo(tagsB[i]);
        if (cmp != 0) {
          return cmp;
        }
      }
      return (listACount != listBCount) ? ((listACount < listBCount) ? -1 : 1) :
        0;
    }

    private static List<Object> WriteChildObject(
      Object parentThisItem,
      CBORObject child,
      OutputStream outputStream,
      List<Object> stack,
      CBOREncodeOptions options) throws java.io.IOException {
      if (child == null) {
        outputStream.write(0xf6);
      } else {
        int type = child.getItemType();
        if (type == CBORObjectTypeArray) {
          stack = PushObject(stack, parentThisItem, child.getThisItem());
          child.WriteTags(outputStream);
          WriteObjectArray(child.AsList(), outputStream, stack, options);
          stack.remove(stack.size() - 1);
        } else if (type == CBORObjectTypeMap) {
          stack = PushObject(stack, parentThisItem, child.getThisItem());
          child.WriteTags(outputStream);
          WriteObjectMap(child.AsMap(), outputStream, stack, options);
          stack.remove(stack.size() - 1);
        } else {
          child.WriteTo(outputStream, options);
        }
      }
      return stack;
    }

    private static void WriteObjectArray(
      List<CBORObject> list,
      OutputStream outputStream,
      CBOREncodeOptions options) throws java.io.IOException {
      WriteObjectArray(list, outputStream, null, options);
    }

    private static void WriteObjectArray(
      List<CBORObject> list,
      OutputStream outputStream,
      List<Object> stack,
      CBOREncodeOptions options) throws java.io.IOException {
      Object thisObj = list;
      WritePositiveInt(4, list.size(), outputStream);
      for (CBORObject i : list) {
        stack = WriteChildObject(thisObj, i, outputStream, stack, options);
      }
    }

    private static void WriteObjectMap(
      Map<CBORObject, CBORObject> map,
      OutputStream outputStream,
      CBOREncodeOptions options) throws java.io.IOException {
      WriteObjectMap(map, outputStream, null, options);
    }

    private static void WriteObjectMap(
      Map<CBORObject, CBORObject> map,
      OutputStream outputStream,
      List<Object> stack,
      CBOREncodeOptions options) throws java.io.IOException {
      Object thisObj = map;
      WritePositiveInt(5, map.size(), outputStream);
      for (Map.Entry<CBORObject, CBORObject> entry : map.entrySet()) {
        CBORObject key = entry.getKey();
        CBORObject value = entry.getValue();
        stack = WriteChildObject(
            thisObj,
            key,
            outputStream,
            stack,
            options);
        stack = WriteChildObject(
            thisObj,
            value,
            outputStream,
            stack,
            options);
      }
    }

    private static int WritePositiveInt(int type, int value, OutputStream s) throws java.io.IOException {
      byte[] bytes = GetPositiveIntBytes(type, value);
      s.write(bytes, 0, bytes.length);
      return bytes.length;
    }

    private static int WritePositiveInt64(int type, long value, OutputStream s) throws java.io.IOException {
      byte[] bytes = GetPositiveInt64Bytes(type, value);
      s.write(bytes, 0, bytes.length);
      return bytes.length;
    }

    private static void WriteStreamedString(String str, OutputStream stream) throws java.io.IOException {
      byte[] bytes;
      bytes = GetOptimizedBytesIfShortAscii(str, -1);
      if (bytes != null) {
        stream.write(bytes, 0, bytes.length);
        return;
      }
      // Take String's length into account when allocating
      // stream buffer, in case it's much smaller than the usual stream
      // String buffer length and to improve performance on small strings
      int bufferLength = Math.min(StreamedStringBufferLength, str.length());
      if (bufferLength < StreamedStringBufferLength) {
        bufferLength = Math.min(
            StreamedStringBufferLength,
            bufferLength * 3);
      }
      bytes = new byte[bufferLength];
      int byteIndex = 0;
      boolean streaming = false;
      for (int index = 0; index < str.length(); ++index) {
        int c = str.charAt(index);
        if (c <= 0x7f) {
          if (byteIndex >= StreamedStringBufferLength) {
            // Write bytes retrieved so far
            if (!streaming) {
              stream.write(0x7f);
            }
            WritePositiveInt(3, byteIndex, stream);
            stream.write(bytes, 0, byteIndex);
            byteIndex = 0;
            streaming = true;
          }
          bytes[byteIndex++] = (byte)c;
        } else if (c <= 0x7ff) {
          if (byteIndex + 2 > StreamedStringBufferLength) {
            // Write bytes retrieved so far - the next two bytes
            // would exceed the length, and the CBOR spec forbids
            // splitting characters when generating text strings
            if (!streaming) {
              stream.write(0x7f);
            }
            WritePositiveInt(3, byteIndex, stream);
            stream.write(bytes, 0, byteIndex);
            byteIndex = 0;
            streaming = true;
          }
          bytes[byteIndex++] = (byte)(0xc0 | ((c >> 6) & 0x1f));
          bytes[byteIndex++] = (byte)(0x80 | (c & 0x3f));
        } else {
          if ((c & 0xfc00) == 0xd800 && index + 1 < str.length() &&
            (str.charAt(index + 1) & 0xfc00) == 0xdc00) {
            // Get the Unicode code point for the surrogate pair
            c = 0x10000 + ((c & 0x3ff) << 10) + (str.charAt(index + 1) & 0x3ff);
            ++index;
          } else if ((c & 0xf800) == 0xd800) {
            // unpaired surrogate, write U+FFFD instead
            c = 0xfffd;
          }
          if (c <= 0xffff) {
            if (byteIndex + 3 > StreamedStringBufferLength) {
              // Write bytes retrieved so far - the next three bytes
              // would exceed the length, and the CBOR spec forbids
              // splitting characters when generating text strings
              if (!streaming) {
                stream.write(0x7f);
              }
              WritePositiveInt(3, byteIndex, stream);
              stream.write(bytes, 0, byteIndex);
              byteIndex = 0;
              streaming = true;
            }
            bytes[byteIndex++] = (byte)(0xe0 | ((c >> 12) & 0x0f));
            bytes[byteIndex++] = (byte)(0x80 | ((c >> 6) & 0x3f));
            bytes[byteIndex++] = (byte)(0x80 | (c & 0x3f));
          } else {
            if (byteIndex + 4 > StreamedStringBufferLength) {
              // Write bytes retrieved so far - the next four bytes
              // would exceed the length, and the CBOR spec forbids
              // splitting characters when generating text strings
              if (!streaming) {
                stream.write(0x7f);
              }
              WritePositiveInt(3, byteIndex, stream);
              stream.write(bytes, 0, byteIndex);
              byteIndex = 0;
              streaming = true;
            }
            bytes[byteIndex++] = (byte)(0xf0 | ((c >> 18) & 0x07));
            bytes[byteIndex++] = (byte)(0x80 | ((c >> 12) & 0x3f));
            bytes[byteIndex++] = (byte)(0x80 | ((c >> 6) & 0x3f));
            bytes[byteIndex++] = (byte)(0x80 | (c & 0x3f));
          }
        }
      }
      WritePositiveInt(3, byteIndex, stream);
      stream.write(bytes, 0, byteIndex);
      if (streaming) {
        stream.write(0xff);
      }
    }

    private int AsInt32(int minValue, int maxValue) {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      if (cn == null) {
 throw new IllegalStateException("not a number type");
}
 return cn.GetNumberInterface().AsInt32(
          cn.GetValue(),
          minValue,
          maxValue);
    }

    private void WriteTags(OutputStream s) throws java.io.IOException {
      CBORObject curobject = this;
      while (curobject.isTagged()) {
        int low = curobject.tagLow;
        int high = curobject.tagHigh;
        if (high == 0 && (low >> 16) == 0) {
          WritePositiveInt(6, low, s);
        } else if (high == 0) {
          long value = low & 0xffffffffL;
          WritePositiveInt64(6, value, s);
        } else if ((high >> 16) == 0) {
          long value = low & 0xffffffffL;
          long highValue = high & 0xffffffffL;
          value |= highValue << 32;
          WritePositiveInt64(6, value, s);
        } else {
          byte[] arrayToWrite = {
            (byte)0xdb,
            (byte)((high >> 24) & 0xff), (byte)((high >> 16) & 0xff),
            (byte)((high >> 8) & 0xff), (byte)(high & 0xff),
            (byte)((low >> 24) & 0xff), (byte)((low >> 16) & 0xff),
            (byte)((low >> 8) & 0xff), (byte)(low & 0xff),
           };
          s.write(arrayToWrite, 0, 9);
        }
        curobject = (CBORObject)curobject.itemValue;
      }
    }
  }
