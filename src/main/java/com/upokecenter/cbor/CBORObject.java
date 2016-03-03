package com.upokecenter.cbor;
/*
Written in 2013 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import java.util.*;
import java.io.*;

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

    /**
     * Represents an object in Concise Binary Object Representation (CBOR) and
     * contains methods for reading and writing CBOR data. CBOR is defined
     * in RFC 7049. <p><b>Converting CBOR objects</b></p> <p>There are many
     * ways to get a CBOR object, including from bytes, objects, streams and
     * JSON, as described below.</p> <p><b>To and from byte arrays:</b> The
     * CBORObject.DecodeToBytes method converts a byte array in CBOR format
     * to a CBOR object. The EncodeToBytes method converts a CBOR object to
     * its corresponding byte array in CBOR format.</p> <p><b>To and from
     * data streams:</b> The CBORObject.Write methods write many kinds of
     * objects to a data stream, including numbers, CBOR objects, strings,
     * and arrays of numbers and strings. The CBORObject.Read method reads a
     * CBOR object from a data stream.</p> <p><b>To and from other
     * objects:</b> The CBORObject.FromObject method converts many kinds of
     * objects to a CBOR object, including numbers, strings, and arrays and
     * maps of numbers and strings. Methods like AsDouble, AsByte, and
     * AsString convert a CBOR object to different types of object.</p>
     * <p><b>To and from JSON:</b> This class also doubles as a reader and
     * writer of JavaScript Object Notation (JSON). The
     * CBORObject.FromJSONString method converts JSON to a CBOR object, and
     * the ToJSONString method converts a CBOR object to a JSON string.</p>
     * <p>In addition, the CBORObject.WriteJSON method writes many kinds of
     * objects as JSON to a data stream, including numbers, CBOR objects,
     * strings, and arrays of numbers and strings. The CBORObject.Read
     * method reads a CBOR object from a JSON data stream.</p>
     * <p><b>Comparison Considerations:</b></p> <p>Instances of CBORObject
     * should not be compared for equality using the "==" operator; it's
     * possible to create two CBOR objects with the same value but not the
     * same reference. (The "==" operator might only check if each side of
     * the operator is the same instance.)</p> <p>This class's natural
     * ordering (under the compareTo method) is not consistent with the
     * Equals method. This means that two values that compare as equal under
     * the compareTo method might not be equal under the Equals method. This
     * is important to consider especially if an application wants to
     * compare numbers, since the CBOR number type supports numbers of
     * different formats, such as big integers, rational numbers, and
     * arbitrary-precision decimal numbers.</p> <p>Another consideration is
     * that two values that are otherwise equal may have different tags. To
     * strip the tags from a CBOR object before comparing, use the
     * <code>Untag</code> method.</p> <p>To compare two numbers, the
     * CompareToIgnoreTags or compareTo method should be used. Which method
     * to use depends on whether two equal values should still be considered
     * equal if they have different tags.</p> <p>Although this class is
     * inconsistent with the Equals method, it is safe to use CBORObject
     * instances as hash keys as long as all of the keys are untagged text
     * strings (which means GetTags returns an empty array and the Type
     * property, or "getType()" in Java, returns TextString). This is
     * because the natural ordering of these instances is consistent with
     * the Equals method.</p> <p><b>Thread Safety:</b></p> <p>CBOR objects
     * that are numbers, "simple values", and text strings are immutable
     * (their values can't be changed), so they are inherently safe for use
     * by multiple threads.</p> <p>CBOR objects that are arrays, maps, and
     * byte strings are mutable, but this class doesn't attempt to
     * synchronize reads and writes to those objects by multiple threads, so
     * those objects are not thread safe without such synchronization.</p>
     * <p>One kind of CBOR object is called a map, or a list of key-value
     * pairs. Keys can be any kind of CBOR object, including numbers,
     * strings, arrays, and maps. However, text strings are the most
     * suitable to use as keys; other kinds of CBOR object are much better
     * used as map values instead, keeping in mind that some of them are not
     * thread safe without synchronizing reads and writes to them.</p> <p>To
     * find the type of a CBOR object, call its Type property (or
     * "getType()" in Java). The return value can be Number, Boolean,
     * SimpleValue, or TextString for immutable CBOR objects, and Array,
     * Map, or ByteString for mutable CBOR objects.</p> <p><b>Nesting
     * Depth:</b></p> <p>The DecodeFromBytes and Read methods can only read
     * objects with a limited maximum depth of arrays and maps nested within
     * other arrays and maps. The code sets this maximum depth to 500
     * (allowing more than enough nesting for most purposes), but it's
     * possible that stack overflows in some runtimes might lower the
     * effective maximum nesting depth. When the nesting depth goes above
     * 500, the DecodeFromBytes and Read methods throw a CBORException.</p>
     * <p>The ReadJSON and FromJSONString methods currently have nesting
     * depths of 1000.</p>
     */
  public final class CBORObject implements Comparable<CBORObject> {
    private static CBORObject ConstructSimpleValue(int v) {
      return new CBORObject(CBORObjectTypeSimpleValue, v);
    }

    private static CBORObject ConstructIntegerValue(int v) {
      return new CBORObject(CBORObjectTypeInteger, (long)v);
    }

    /**
     * Represents the value false.
     */

    public static final CBORObject False =
      CBORObject.ConstructSimpleValue(20);

    /**
     * A not-a-number value.
     */
    public static final CBORObject NaN = CBORObject.FromObject(Double.NaN);

    /**
     * The value negative infinity.
     */
    public static final CBORObject NegativeInfinity =
      CBORObject.FromObject(Double.NEGATIVE_INFINITY);

    /**
     * Represents the value null.
     */

    public static final CBORObject Null =
      CBORObject.ConstructSimpleValue(22);

    /**
     * The value positive infinity.
     */
    public static final CBORObject PositiveInfinity =
      CBORObject.FromObject(Double.POSITIVE_INFINITY);

    /**
     * Represents the value true.
     */

    public static final CBORObject True =
      CBORObject.ConstructSimpleValue(21);

    /**
     * Represents the value undefined.
     */

    public static final CBORObject Undefined =
      CBORObject.ConstructSimpleValue(23);

    /**
     * Gets a CBOR object for the number zero.
     */
    public static final CBORObject Zero =
      CBORObject.ConstructIntegerValue(0);

    static final int CBORObjectTypeArray = 4;
    static final int CBORObjectTypeBigInteger = 1;  // all other integers
    static final int CBORObjectTypeByteString = 2;
    static final int CBORObjectTypeDouble = 8;
    static final int CBORObjectTypeExtendedDecimal = 9;
    static final int CBORObjectTypeExtendedFloat = 11;
    static final int CBORObjectTypeExtendedRational = 12;
    static final int CBORObjectTypeInteger = 0;  // -(2^63).. (2^63-1)
    static final int CBORObjectTypeMap = 5;
    static final int CBORObjectTypeSimpleValue = 6;
    static final int CBORObjectTypeSingle = 7;
    static final int CBORObjectTypeTagged = 10;
    static final int CBORObjectTypeTextString = 3;
    static final EInteger Int64MaxValue =
      EInteger.FromInt64(Long.MAX_VALUE);

    static final EInteger Int64MinValue =
      EInteger.FromInt64(Long.MIN_VALUE);

    private static final int StreamedStringBufferLength = 4096;

    private static final Map<Object, ConverterInfo>
      ValueConverters = new HashMap<Object, ConverterInfo>();

    private static final ICBORNumber[] NumberInterfaces = {
      new CBORInteger(), new CBOREInteger(), null, null,
      null, null, null, new CBORSingle(),
      new CBORDouble(), new CBORExtendedDecimal(),
      null, new CBORExtendedFloat(), new CBORExtendedRational()
    };

    private static final Map<EInteger, ICBORTag>
      ValueTagHandlers = new HashMap<EInteger, ICBORTag>();

    private static final EInteger UInt64MaxValue =
      (EInteger.FromInt32(1).ShiftLeft(64)).Subtract(EInteger.FromInt64(1));

    private static final EInteger[] ValueEmptyTags = new EInteger[0];
    // Expected lengths for each head byte.
    // 0 means length varies. -1 means invalid.
    private static final int[] ValueExpectedLengths = { 1, 1, 1, 1, 1, 1,
      1, 1, 1,
      1, 1, 1, 1, 1, 1, 1,  // major type 0
      1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 5, 9, -1, -1, -1, -1,
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,  // major type 1
      1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 5, 9, -1, -1, -1, -1,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,  // major type 2
      17, 18, 19, 20, 21, 22, 23, 24, 0, 0, 0, 0, -1, -1, -1, 0,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,  // major type 3
      17, 18, 19, 20, 21, 22, 23, 24, 0, 0, 0, 0, -1, -1, -1, 0,
      1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  // major type 4
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0,
      1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  // major type 5
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  // major type 6
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1,
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,  // major type 7
      1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 5, 9, -1, -1, -1, -1 };

    private static final byte[] ValueFalseBytes = {  0x66, 0x61, 0x6c,
      0x73, 0x65  };

    private static final byte[] ValueNullBytes = {  0x6e, 0x75, 0x6c, 0x6c  };

    private static final int[] ValueNumberTypeOrder = { 0, 0, 2, 3, 4, 5,
      1, 0, 0,
      0, 0, 0, 0 };

    private static final byte[] ValueTrueBytes = {  0x74, 0x72, 0x75, 0x65  };

    private static CBORObject[] valueFixedObjects = InitializeFixedObjects();

    private int itemtypeValue;
    private Object itemValue;
    private int tagHigh;
    private int tagLow;

    CBORObject(CBORObject obj, int tagLow, int tagHigh) {
 this(CBORObjectTypeTagged, obj);
      this.tagLow = tagLow;
      this.tagHigh = tagHigh;
    }

    CBORObject(int type, Object item) {
      this.itemtypeValue = type;
      this.itemValue = item;
    }

    /**
     * Gets the number of keys in this map, or the number of items in this array,
     * or 0 if this item is neither an array nor a map.
     * @return The number of keys in this map, or the number of items in this
     * array, or 0 if this item is neither an array nor a map.
     */
    public final int size() {
        return (this.getItemType() == CBORObjectTypeArray) ? this.AsList().size() :
          ((this.getItemType() == CBORObjectTypeMap) ? this.AsMap().size() : 0);
      }

    /**
     * Gets the last defined tag for this CBOR data item, or -1 if the item is
     * untagged.
     * @return The last defined tag for this CBOR data item, or -1 if the item is
     * untagged.
     * @deprecated Use MostInnerTag instead.
 */
@Deprecated
    public final BigInteger getInnermostTag() {
        throw new UnsupportedOperationException();
      }

    /**
     * Gets the outermost tag for this CBOR data item, or -1 if the item is
     * untagged.
     * @return The outermost tag for this CBOR data item, or -1 if the item is
     * untagged.
     * @deprecated Use MostOuterTag instead.
 */
@Deprecated
    public final BigInteger getOutermostTag() {
        throw new UnsupportedOperationException();
      }

    /**
     * Gets the last defined tag for this CBOR data item, or -1 if the item is
     * untagged.
     * @return The last defined tag for this CBOR data item, or -1 if the item is
     * untagged.
     */
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
        if (previtem.tagHigh == 0 && previtem.tagLow >= 0 &&
            previtem.tagLow < 0x10000) {
          return EInteger.FromInt64(previtem.tagLow);
        }
        return LowHighToEInteger(
          previtem.tagLow,
          previtem.tagHigh);
      }

    /**
     * Gets a value indicating whether this value is a CBOR false value.
     * @return {@code true} If this value is a CBOR false value; otherwise, .
     * {@code false}.
     */
    public final boolean isFalse() {
        return this.getItemType() == CBORObjectTypeSimpleValue && ((Integer)this.getThisItem()).intValue()
          == 20;
      }

    /**
     * Gets a value indicating whether this CBOR object represents a finite number.
     * @return {@code true} If this CBOR object represents a finite number;
     * otherwise, {@code false}.
     */
    public final boolean isFinite() {
        return this.getType() == CBORType.Number && !this.IsInfinity() &&
          !this.IsNaN();
      }

    /**
     * Gets a value indicating whether this object represents an integral number,
     * that is, a number without a fractional part. Infinity and
     * not-a-number are not considered integral.
     * @return {@code true} If this object represents an integral number, that is,
     * a number without a fractional part; otherwise, {@code false}.
     */
    public final boolean isIntegral() {
        ICBORNumber cn = NumberInterfaces[this.getItemType()];
        return (cn != null) && cn.IsIntegral(this.getThisItem());
      }

    /**
     * Gets a value indicating whether this value is a CBOR null value.
     * @return {@code true} If this value is a CBOR null value; otherwise, {@code
     * false}.
     */
    public final boolean isNull() {
        return this.getItemType() == CBORObjectTypeSimpleValue && ((Integer)this.getThisItem()).intValue()
          == 22;
      }

    /**
     * Gets a value indicating whether this data item has at least one tag.
     * @return {@code true} If this data item has at least one tag; otherwise,
     * {@code false}.
     */
    public final boolean isTagged() {
        return this.itemtypeValue == CBORObjectTypeTagged;
      }

    /**
     * Gets a value indicating whether this value is a CBOR true value.
     * @return {@code true} If this value is a CBOR true value; otherwise, {@code
     * false}.
     */
    public final boolean isTrue() {
        return this.getItemType() == CBORObjectTypeSimpleValue && ((Integer)this.getThisItem()).intValue()
          == 21;
      }

    /**
     * Gets a value indicating whether this value is a CBOR undefined value.
     * @return {@code true} If this value is a CBOR undefined value; otherwise,
     * {@code false}.
     */
    public final boolean isUndefined() {
        return this.getItemType() == CBORObjectTypeSimpleValue && ((Integer)this.getThisItem()).intValue()
          == 23;
      }

    /**
     * Gets a value indicating whether this object&#x27;s value equals 0.
     * @return {@code true} If this object's value equals 0; otherwise, . {@code
     * false}.
     */
    public final boolean isZero() {
        ICBORNumber cn = NumberInterfaces[this.getItemType()];
        return cn != null && cn.IsZero(this.getThisItem());
      }

    /**
     * Gets a collection of the keys of this CBOR object in an undefined order.
     * @return A collection of the keys of this CBOR object.
     * @throws IllegalStateException This object is not a map.
     */
    public final Collection<CBORObject> getKeys() {
        if (this.getItemType() == CBORObjectTypeMap) {
          Map<CBORObject, CBORObject> dict = this.AsMap();
          return dict.keySet();
        }
        throw new IllegalStateException("Not a map");
      }

    /**
     * Gets a value indicating whether this object is a negative number.
     * @return {@code true} If this object is a negative number; otherwise, .
     * {@code false}.
     */
    public final boolean isNegative() {
        ICBORNumber cn = NumberInterfaces[this.getItemType()];
        return (cn != null) && cn.IsNegative(this.getThisItem());
      }

    /**
     * Gets the outermost tag for this CBOR data item, or -1 if the item is
     * untagged.
     * @return The outermost tag for this CBOR data item, or -1 if the item is
     * untagged.
     */
    public final EInteger getMostOuterTag() {
        if (!this.isTagged()) {
          return EInteger.FromInt32(-1);
        }
        if (this.tagHigh == 0 &&
            this.tagLow >= 0 && this.tagLow < 0x10000) {
          return EInteger.FromInt32(this.tagLow);
        }
        return LowHighToEInteger(
          this.tagLow,
          this.tagHigh);
      }

    /**
     * Gets this value's sign: -1 if negative; 1 if positive; 0 if zero.
     * @return This value's sign: -1 if negative; 1 if positive; 0 if zero.
     * @throws IllegalStateException This object's type is not a number type,
     * including the special not-a-number value (NaN).
     */
    public final int signum() {
        int ret = GetSignInternal(this.getItemType(), this.getThisItem());
        if (ret == 2) {
          throw new IllegalStateException("This Object is not a number.");
        }
        return ret;
      }

    /**
     * Gets the simple value ID of this object, or -1 if this object is not a
     * simple value (including if the value is a floating-point number).
     * @return The simple value ID of this object, or -1 if this object is not a
     * simple value (including if the value is a floating-point number).
     */
    public final int getSimpleValue() {
        return (this.getItemType() == CBORObjectTypeSimpleValue) ?
          (((Integer)this.getThisItem()).intValue()) : (-1);
      }

    /**
     * Gets the general data type of this CBOR object.
     * @return The general data type of this CBOR object.
     */
    public final CBORType getType() {
        switch (this.getItemType()) {
          case CBORObjectTypeInteger:
          case CBORObjectTypeBigInteger:
          case CBORObjectTypeSingle:
          case CBORObjectTypeDouble:
          case CBORObjectTypeExtendedDecimal:
          case CBORObjectTypeExtendedFloat:
          case CBORObjectTypeExtendedRational:
            return CBORType.Number;
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
            return CBORType.TextString;
          default:
            throw new IllegalStateException("Unexpected data type");
        }
      }

    /**
     * Gets a collection of the values of this CBOR object. If this object is a
     * map, returns one value for each key in the map in an undefined order.
     * If this is an array, returns all the values of the array in the order
     * they are listed.
     * @return A collection of the values of this CBOR object.
     * @throws IllegalStateException This object is not a map or an array.
     */
    public final Collection<CBORObject> getValues() {
        if (this.getItemType() == CBORObjectTypeMap) {
          Map<CBORObject, CBORObject> dict = this.AsMap();
          return dict.values();
        }
        if (this.getItemType() == CBORObjectTypeArray) {
          List<CBORObject> list = this.AsList();
          return java.util.Collections.unmodifiableList(list);
        }
        throw new IllegalStateException("Not a map or array");
      }

    final int getItemType() {
        CBORObject curobject = this;
        while (curobject.itemtypeValue == CBORObjectTypeTagged) {
          curobject = (CBORObject)curobject.itemValue;
        }
        return curobject.itemtypeValue;
      }

    final Object getThisItem() {
        CBORObject curobject = this;
        while (curobject.itemtypeValue == CBORObjectTypeTagged) {
          curobject = (CBORObject)curobject.itemValue;
        }
        return curobject.itemValue;
      }

    /**
     * Gets the value of a CBOR object by integer index in this array.
     * @param index Zero-based index of the element.
     * @return A CBORObject object.
     * @throws IllegalStateException This object is not an array.
     * @throws java.lang.NullPointerException The parameter "value" is null (as
     * opposed to CBORObject.Null).
     */
    public CBORObject get(int index) {
        if (this.getItemType() == CBORObjectTypeArray) {
          List<CBORObject> list = this.AsList();
          if (index < 0 || index >= list.size()) {
            throw new IllegalArgumentException("index");
          }
          return list.get(index);
        }
        throw new IllegalStateException("Not an array");
      }

    /**
     *
     */
    public void set(int index, CBORObject value) {
        if (this.getItemType() == CBORObjectTypeArray) {
          if (value == null) {
            throw new NullPointerException("value");
          }
          List<CBORObject> list = this.AsList();
          list.set(index, value);
        } else {
          throw new IllegalStateException("Not an array");
        }
      }

    /**
     * Gets the value of a CBOR object in this map, using a CBOR object as the key.
     * @param key Another CBOR object.
     * @return A CBORObject object.
     * @throws java.lang.NullPointerException The key is null (as opposed to
     * CBORObject.Null); or the set method is called and the value is null.
     * @throws IllegalStateException This object is not a map.
     */
    public CBORObject get(CBORObject key) {
        if (key == null) {
          throw new NullPointerException("key");
        }
        if (this.getItemType() == CBORObjectTypeMap) {
          Map<CBORObject, CBORObject> map = this.AsMap();
          return (!map.containsKey(key)) ? null : map.get(key);
        }
        throw new IllegalStateException("Not a map");
      }

    /**
     *
     */
    public void set(CBORObject key, CBORObject value) {
        if (key == null) {
          throw new NullPointerException("value");
        }
        if (value == null) {
          throw new NullPointerException("value");
        }
        if (this.getItemType() == CBORObjectTypeMap) {
          Map<CBORObject, CBORObject> map = this.AsMap();
          map.put(key, value);
        } else {
          throw new IllegalStateException("Not a map");
        }
      }

    /**
     * Gets the value of a CBOR object in this map, using a string as the key.
     * @param key A key that points to the desired value.
     * @return A CBORObject object.
     * @throws java.lang.NullPointerException The key is null.
     * @throws IllegalStateException This object is not a map.
     */
    public CBORObject get(String key) {
        if (key == null) {
          throw new NullPointerException("key");
        }
        CBORObject objkey = CBORObject.FromObject(key);
        return this.get(objkey);
      }

    /**
     *
     */
    public void set(String key, CBORObject value) {
        if (key == null) {
          throw new NullPointerException("value");
        }
        if (value == null) {
          throw new NullPointerException("value");
        }
        CBORObject objkey = CBORObject.FromObject(key);
        if (this.getItemType() == CBORObjectTypeMap) {
          Map<CBORObject, CBORObject> map = this.AsMap();
          map.put(objkey, value);
        } else {
          throw new IllegalStateException("Not a map");
        }
      }

    /**
     * Registers an object that converts objects of a given type to CBOR objects
     * (called a CBOR converter).
     * @param type A Type object specifying the type that the converter converts to
     * CBOR objects.
     * @param converter An ICBORConverter object.
     * @param <T> Must be the same as the "type" parameter.
     * @throws java.lang.NullPointerException The parameter {@code type} or {@code
     * converter} is null.
     */
    public static <T> void AddConverter(Class<?> type, ICBORConverter<T> converter) {
      if (type == null) {
        throw new NullPointerException("type");
      }
      if (converter == null) {
        throw new NullPointerException("converter");
      }
      ConverterInfo ci = new CBORObject.ConverterInfo();
      ci.setConverter(converter);
      ci.setToObject(PropertyMap.FindOneArgumentMethod(
        converter,
        "ToCBORObject",
        type));
      if (ci.getToObject() == null) {
        throw new IllegalArgumentException(
          "Converter doesn't contain a proper ToCBORObject method");
      }
      synchronized (ValueConverters) {
        ValueConverters.put(type, ci);
      }
    }

    /**
     * Finds the sum of two CBOR numbers.
     * @param first Another CBOR object.
     * @param second A CBORObject object. (3).
     * @return A CBORObject object.
     * @throws IllegalArgumentException Either or both operands are not numbers (as
     * opposed to Not-a-Number, NaN).
     */
    public static CBORObject Addition(CBORObject first, CBORObject second) {
      return CBORObjectMath.Addition(first, second);
    }

    /**
     * Registers an object that validates CBOR objects with new tags.
     * @param bigintTag An arbitrary-precision integer.
     * @param handler An ICBORTag object.
     * @throws java.lang.NullPointerException The parameter {@code bigintTag} or
     * {@code handler} is null.
     * @throws java.lang.NullPointerException The parameter {@code bigintTag} is less
     * than 0 or greater than (2^64-1).
     * @deprecated Use the EInteger version of this method.
 */
@Deprecated
    public static void AddTagHandler(BigInteger bigintTag, ICBORTag handler) {
      if (bigintTag == null) {
        throw new NullPointerException("bigintTag");
      }
      if (handler == null) {
        throw new NullPointerException("handler");
      }
      AddTagHandler(PropertyMap.FromLegacy(bigintTag), handler);
    }

    /**
     * Registers an object that validates CBOR objects with new tags.
     * @param bigintTag An arbitrary-precision integer.
     * @param handler An ICBORTag object.
     * @throws java.lang.NullPointerException The parameter {@code bigintTag} or
     * {@code handler} is null.
     * @throws java.lang.NullPointerException The parameter {@code bigintTag} is less
     * than 0 or greater than (2^64-1).
     */
    public static void AddTagHandler(EInteger bigintTag, ICBORTag handler) {
      if (bigintTag == null) {
        throw new NullPointerException("bigintTag");
      }
      if (handler == null) {
        throw new NullPointerException("handler");
      }
      if (bigintTag.signum() < 0) {
        throw new IllegalArgumentException("bigintTag.signum() (" +
                    bigintTag.signum() + ") is less than 0");
      }
      if (bigintTag.GetSignedBitLength() > 64) {
        throw new IllegalArgumentException("bigintTag.bitLength (" +
                    (long)bigintTag.GetSignedBitLength() + ") is more than " +
                    "64");
      }
      synchronized (ValueTagHandlers) {
        ValueTagHandlers.put(bigintTag, handler);
      }
    }

    /**
     * Generates a CBOR object from an array of CBOR-encoded bytes.
     * @param data A byte array.
     * @return A CBOR object corresponding to the data.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where not all of the byte array
     * represents a CBOR object. This exception is also thrown if the
     * parameter {@code data} is empty.
     * @throws java.lang.NullPointerException The parameter {@code data} is null.
     */
    public static CBORObject DecodeFromBytes(byte[] data) {
      return DecodeFromBytes(data, CBOREncodeOptions.None);
    }

    /**
     * Generates a CBOR object from an array of CBOR-encoded bytes.
     * @param data A byte array.
     * @param options A CBOREncodeOptions object.
     * @return A CBOR object corresponding to the data.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where not all of the byte array
     * represents a CBOR object. This exception is also thrown if the
     * parameter {@code data} is empty.
     * @throws java.lang.NullPointerException The parameter {@code data} is null.
     */
    public static CBORObject DecodeFromBytes(
byte[] data,
CBOREncodeOptions options) {
      if (data == null) {
        throw new NullPointerException("data");
      }
      if (data.length == 0) {
        throw new CBORException("data is empty.");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      int firstbyte = (int)(data[0] & (int)0xff);
      int expectedLength = ValueExpectedLengths[firstbyte];
      // if invalid
      if (expectedLength == -1) {
        throw new CBORException("Unexpected data encountered");
      }
      if (expectedLength != 0) {
        // if fixed length
        CheckCBORLength(expectedLength, data.length);
      }
      if (firstbyte == 0xc0) {
        // value with tag 0
        String s = GetOptimizedStringIfShortAscii(data, 1);
        if (s != null) {
          return new CBORObject(FromObject(s), 0, 0);
        }
      }
      if (expectedLength != 0) {
        return GetFixedLengthObject(firstbyte, data);
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
        CheckCBORLength((long)data.length, (long)(startingAvailable-ms.available()));
        return o;
}
finally {
try { if (ms != null)ms.close(); } catch (java.io.IOException ex) {}
}
}
    }

    /**
     * Divides a CBORObject object by the value of a CBORObject object.
     * @param first A CBORObject object.
     * @param second Another CBOR object.
     * @return The quotient of the two objects.
     */
    public static CBORObject Divide(CBORObject first, CBORObject second) {
      return CBORObjectMath.Divide(first, second);
    }

    /**
     * Generates a CBOR object from a text string in JavaScript Object Notation
     * (JSON) format. <p>If a JSON object has the same key, only the last
     * given value will be used for each duplicated key.</p>
     * @param str A string in JSON format. The entire string must contain a single
     * JSON object and not multiple objects. The string may not begin with a
     * byte-order mark (U + FEFF).
     * @return A CBORObject object.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws com.upokecenter.cbor.CBORException The string is not in JSON format.
     */
    public static CBORObject FromJSONString(String str) {
      return FromJSONString(str, CBOREncodeOptions.None);
    }

    /**
     * Generates a CBOR object from a text string in JavaScript Object Notation
     * (JSON) format. <p>By default, if a JSON object has the same key, only
     * the last given value will be used for each duplicated key.</p>
     * @param str A string in JSON format. The entire string must contain a single
     * JSON object and not multiple objects. The string may not begin with a
     * byte-order mark (U + FEFF).
     * @param options A CBOREncodeOptions object.
     * @return A CBORObject object.
     * @throws java.lang.NullPointerException The parameter {@code str} is null.
     * @throws com.upokecenter.cbor.CBORException The string is not in JSON format.
     */
    public static CBORObject FromJSONString(
String str,
CBOREncodeOptions options) {
      if (str == null) {
        throw new NullPointerException("str");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      if (str.length() > 0 && str.charAt(0) == 0xfeff) {
        throw new CBORException(
          "JSON Object began with a byte order mark (U+FEFF) (offset 0)");
      }
      CharacterInputWithCount reader = new CharacterInputWithCount(
        new CharacterReader(str, false, true));
      CBOREncodeOptions opt = options.And(CBOREncodeOptions.NoDuplicateKeys);
      int[] nextchar = new int[1];
      CBORObject obj = CBORJson.ParseJSONValue(
      reader,
      opt.getValue() != 0,
      false,
      nextchar);
      if (nextchar[0] != -1) {
        reader.RaiseError("End of String not reached");
      }
      return obj;
    }

    /**
     * Generates a CBOR object from a 64-bit signed integer.
     * @param value A 64-bit signed integer.
     * @return A CBORObject object.
     */
    public static CBORObject FromObject(long value) {
      return (value >= 0L && value < 24L) ? valueFixedObjects[(int)value] :
        (new CBORObject(CBORObjectTypeInteger, value));
    }

    /**
     * Generates a CBOR object from a CBOR object.
     * @param value A CBOR object.
     * @return Same as.
     */
    public static CBORObject FromObject(CBORObject value) {
      return (value == null) ? (CBORObject.Null) : value;
    }

    /**
     * Generates a CBOR object from an arbitrary-precision integer.
     * @param bigintValue An arbitrary-precision value.
     * @return A CBOR number.
     * @deprecated Use the EInteger version of this method.
 */
@Deprecated
    public static CBORObject FromObject(BigInteger bigintValue) {
      return ((Object)bigintValue == (Object)null) ? CBORObject.Null :
        FromObject(PropertyMap.FromLegacy(bigintValue));
    }

    /**
     * Generates a CBOR object from an arbitrary-precision integer.
     * @param bigintValue An arbitrary-precision value.
     * @return A CBOR number.
     */
    public static CBORObject FromObject(EInteger bigintValue) {
      if ((Object)bigintValue == (Object)null) {
        return CBORObject.Null;
      }
      return (bigintValue.compareTo(Int64MinValue) >= 0 &&
              bigintValue.compareTo(Int64MaxValue) <= 0) ?
        new CBORObject(
          CBORObjectTypeInteger,
          bigintValue.AsInt64Checked()) : (new CBORObject(
        CBORObjectTypeBigInteger,
        bigintValue));
    }

    /**
     * Generates a CBOR object from an arbitrary-precision binary floating-point
     * number.
     * @param bigValue An arbitrary-precision binary floating-point number.
     * @return A CBOR number.
     * @deprecated Use the EFloat version of this method instead.
 */
@Deprecated
    public static CBORObject FromObject(ExtendedFloat bigValue) {
      return ((Object)bigValue == (Object)null) ? CBORObject.Null :
        FromObject(PropertyMap.FromLegacy(bigValue));
    }

    /**
     * Generates a CBOR object from an arbitrary-precision binary floating-point
     * number.
     * @param bigValue An arbitrary-precision binary floating-point number.
     * @return A CBOR number.
     */
    static CBORObject FromObject(EFloat bigValue) {
      if ((Object)bigValue == (Object)null) {
        return CBORObject.Null;
      }
      if (bigValue.IsInfinity()) {
        return CBORObject.FromObject(bigValue.ToDouble());
      }
      if (bigValue.IsNaN()) {
        return new CBORObject(
          CBORObjectTypeExtendedFloat,
          bigValue);
      }
      EInteger bigintExponent = bigValue.getExponent();
      return (bigintExponent.isZero() && !(bigValue.isZero() &&
                    bigValue.isNegative())) ? FromObject(bigValue.getMantissa()) :
        new CBORObject(
          CBORObjectTypeExtendedFloat,
          bigValue);
    }

    /**
     * Generates a CBOR object from an arbitrary-precision binary floating-point
     * number.
     * @param bigValue An arbitrary-precision binary floating-point number.
     * @return A CBOR number.
     * @deprecated Use the ERational version of this method instead.
 */
@Deprecated
    public static CBORObject FromObject(ExtendedRational bigValue) {
      return ((Object)bigValue == (Object)null) ? CBORObject.Null :
        FromObject(PropertyMap.FromLegacy(bigValue));
    }

    /**
     * Generates a CBOR object from a rational number.
     * @param bigValue A rational number.
     * @return A CBOR number.
     */
    static CBORObject FromObject(ERational bigValue) {
      if ((Object)bigValue == (Object)null) {
        return CBORObject.Null;
      }
      if (bigValue.IsInfinity()) {
        return CBORObject.FromObject(bigValue.ToDouble());
      }
      if (bigValue.IsNaN()) {
        return new CBORObject(
          CBORObjectTypeExtendedRational,
          bigValue);
      }
      return (bigValue.isFinite() && bigValue.getDenominator().equals(EInteger.FromInt32(1))) ?
            FromObject(bigValue.getNumerator()) : (new CBORObject(
              CBORObjectTypeExtendedRational,
              bigValue));
    }

    /**
     * Generates a CBOR object from a decimal number.
     * @param otherValue An arbitrary-precision decimal number.
     * @return A CBOR number.
     */
    static CBORObject FromObject(EDecimal otherValue) {
      if ((Object)otherValue == (Object)null) {
        return CBORObject.Null;
      }
      if (otherValue.IsInfinity()) {
        return CBORObject.FromObject(otherValue.ToDouble());
      }
      if (otherValue.IsNaN()) {
        return new CBORObject(
          CBORObjectTypeExtendedDecimal,
          otherValue);
      }
      EInteger bigintExponent = otherValue.getExponent();
      return (bigintExponent.isZero() && !(otherValue.isZero() &&
                    otherValue.isNegative())) ? FromObject(otherValue.getMantissa()) :
        new CBORObject(
          CBORObjectTypeExtendedDecimal,
          otherValue);
    }

    /**
     * Generates a CBOR object from a decimal number.
     * @param otherValue An arbitrary-precision decimal number.
     * @return A CBOR number.
     * @deprecated Use the EDecimal version of this method instead.
 */
@Deprecated
    public static CBORObject FromObject(ExtendedDecimal otherValue) {
      return ((Object)otherValue == (Object)null) ? CBORObject.Null :
        FromObject(PropertyMap.FromLegacy(otherValue));
    }

    /**
     * Generates a CBOR object from a text string.
     * @param strValue A string value. Can be null.
     * @return A CBOR object representing the string, or CBORObject.Null if
     * stringValue is null.
     * @throws IllegalArgumentException The string contains an unpaired surrogate
     * code point.
     */
    public static CBORObject FromObject(String strValue) {
      if (strValue == null) {
        return CBORObject.Null;
      }
      if (DataUtilities.GetUtf8Length(strValue, false) < 0) {
        throw new
        IllegalArgumentException("String contains an unpaired surrogate code point.");
      }
      return new CBORObject(CBORObjectTypeTextString, strValue);
    }

    /**
     * Generates a CBOR object from a 32-bit signed integer.
     * @param value A 32-bit signed integer.
     * @return A CBORObject object.
     */
    public static CBORObject FromObject(int value) {
      return (value >= 0 && value < 24) ? valueFixedObjects[value] :
        FromObject((long)value);
    }

    /**
     * Generates a CBOR object from a 16-bit signed integer.
     * @param value A 16-bit signed integer.
     * @return A CBORObject object.
     */
    public static CBORObject FromObject(short value) {
      return (value >= 0 && value < 24) ? valueFixedObjects[value] :
        FromObject((long)value);
    }

    /**
     * Generates a CBOR string object from a Unicode character.
     * @param value A char object.
     * @return A CBORObject object.
     * @throws IllegalArgumentException The parameter {@code value} is a surrogate
     * code point.
     */
    public static CBORObject FromObject(char value) {
      char[] valueChar = { value };
      return FromObject(new String(valueChar));
    }

    /**
     * Returns the CBOR true value or false value, depending on &#x22;value&#x22;.
     * @param value Either True or False.
     * @return CBORObject.True if value is true; otherwise CBORObject.False.
     */
    public static CBORObject FromObject(boolean value) {
      return value ? CBORObject.True : CBORObject.False;
    }

    /**
     * Generates a CBOR object from a byte (0 to 255).
     * @param value A Byte object.
     * @return A CBORObject object.
     */
    public static CBORObject FromObject(byte value) {
      return FromObject(((int)value) & 0xff);
    }

    /**
     * Generates a CBOR object from a 32-bit floating-point number.
     * @param value A 32-bit floating-point number.
     * @return A CBORObject object.
     */
    public static CBORObject FromObject(float value) {
      return new CBORObject(CBORObjectTypeSingle, value);
    }

    /**
     * Generates a CBOR object from a 64-bit floating-point number.
     * @param value A 64-bit floating-point number.
     * @return A CBORObject object.
     */
    public static CBORObject FromObject(double value) {
      return new CBORObject(CBORObjectTypeDouble, value);
    }

    /**
     * Generates a CBOR object from a byte array. The byte array is copied to a new
     * byte array. (This method can't be used to decode CBOR data from a
     * byte array; for that, use the DecodeFromBytes method instead.).
     * @param bytes A byte array. Can be null.
     * @return A CBOR byte string object where each byte of the given byte array is
     * copied to a new array, or CBORObject.Null if the value is null.
     */
    public static CBORObject FromObject(byte[] bytes) {
      if (bytes == null) {
        return CBORObject.Null;
      }
      byte[] newvalue = new byte[bytes.length];
      System.arraycopy(bytes, 0, newvalue, 0, bytes.length);
      return new CBORObject(CBORObjectTypeByteString, bytes);
    }

    /**
     * Generates a CBOR object from an array of CBOR objects.
     * @param array An array of CBOR objects.
     * @return A CBOR object where each element of the given array is copied to a
     * new array, or CBORObject.Null if the value is null.
     */
    public static CBORObject FromObject(CBORObject[] array) {
      if (array == null) {
        return CBORObject.Null;
      }
      List<CBORObject> list = new ArrayList<CBORObject>();
      for (CBORObject i : array) {
        list.add(FromObject(i));
      }
      return new CBORObject(CBORObjectTypeArray, list);
    }

    /**
     * Generates a CBOR object from an array of 32-bit integers.
     * @param array An array of 32-bit integers.
     * @return A CBOR array object where each element of the given array is copied
     * to a new array, or CBORObject.Null if the value is null.
     */
    public static CBORObject FromObject(int[] array) {
      if (array == null) {
        return CBORObject.Null;
      }
      List<CBORObject> list = new ArrayList<CBORObject>();
      for (int i : array) {
        list.add(FromObject(i));
      }
      return new CBORObject(CBORObjectTypeArray, list);
    }

    /**
     * Generates a CBOR object from an array of 64-bit integers.
     * @param array An array of 64-bit integers.
     * @return A CBOR array object where each element of the given array is copied
     * to a new array, or CBORObject.Null if the value is null.
     */
    public static CBORObject FromObject(long[] array) {
      if (array == null) {
        return CBORObject.Null;
      }
      List<CBORObject> list = new ArrayList<CBORObject>();
      for (long i : array) {
        // System.out.println(i);
        list.add(FromObject(i));
      }
      return new CBORObject(CBORObjectTypeArray, list);
    }

    /**
     * Generates a CBOR object from a list of objects.
     * @param value An array of CBOR objects. Can be null.
     * @param <T> A type convertible to CBORObject.
     * @return A CBOR object where each element of the given array is converted to
     * a CBOR object and copied to a new array, or CBORObject.Null if the
     * value is null.
     */
    public static <T> CBORObject FromObject(List<T> value) {
      if (value == null) {
        return CBORObject.Null;
      }
      if (value.size() == 0) {
        return new CBORObject(CBORObjectTypeArray, new ArrayList<T>());
      }
      CBORObject retCbor = CBORObject.NewArray();
      for (T i : (List<T>)value) {
        retCbor.Add(CBORObject.FromObject(i));
      }
      return retCbor;
    }

    /**
     * Generates a CBOR object from an enumerable set of objects.
     * @param value An object that implements the IEnumerable interface. In the
     * .NET version, this can be the return value of an iterator or the
     * result of a LINQ query.
     * @param <T> A type convertible to CBORObject.
     * @return A CBOR object where each element of the given enumerable object is
     * converted to a CBOR object and copied to a new array, or
     * CBORObject.Null if the value is null.
     */
    public static <T> CBORObject FromObject(Iterable<T> value) {
      if (value == null) {
        return CBORObject.Null;
      }
      CBORObject retCbor = CBORObject.NewArray();
      for (T i : (Iterable<T>)value) {
        retCbor.Add(CBORObject.FromObject(i));
      }
      return retCbor;
    }

    /**
     * Generates a CBOR object from a map of objects.
     * @param dic A map of CBOR objects.
     * @param <TKey> A type convertible to CBORObject; the type of the keys.
     * @param <TValue> A type convertible to CBORObject; the type of the values.
     * @return A CBOR object where each key and value of the given map is converted
     * to a CBOR object and copied to a new map, or CBORObject.Null if
     * {@code dic} is null.
     */
    public static <TKey, TValue> CBORObject FromObject(Map<TKey,
                    TValue> dic) {
      if (dic == null) {
        return CBORObject.Null;
      }
      HashMap<CBORObject, CBORObject> map = new HashMap<CBORObject, CBORObject>();
      for (Map.Entry<TKey, TValue> entry : dic.entrySet()) {
        CBORObject key = FromObject(entry.getKey());
        CBORObject value = FromObject(entry.getValue());
        map.put(key, value);
      }
      return new CBORObject(CBORObjectTypeMap, map);
    }

    /**
     * Generates a CBORObject from an arbitrary object. The following types are
     * specially handled by this method: null; primitive types; string;
     * CBORObject; the <code>EDecimal</code>, <code>EFloat</code>, <code>EInteger</code>, and
     * <code>ERational</code> classes in the new <a
  * href='https://www.nuget.org/packages/PeterO.Numbers'><code>PeterO.Numbers</code></a>
     * library (in .NET) or the <a
  * href='https://github.com/peteroupc/numbers-java'><code>com.github.peteroupc/numbers</code></a>
     * artifact (in Java); the legacy <code>ExtendedDecimal</code>,
     * <code>ExtendedFloat</code>, <code>ExtendedInteger</code>, and
     * <code>ExtendedRational</code> classes in this library; arrays; enumerations
     * (<code>Enum</code> objects); and maps. <p>In the .NET version, if the
     * object is a type not specially handled by this method, returns a CBOR
     * map with the values of each of its read/write properties (or all
     * properties in the case of an anonymous type). Properties are
     * converted to their camel-case names (meaning if a name starts with A
     * to Z, that letter is lower-cased). If the property name begins with
     * the word "Is", that word is deleted from the name. Also, .NET
     * <code>Enum</code> objects will be converted to their integer values, and a
     * multidimensional array is converted to an array of arrays.</p> <p>In
     * the Java version, if the object is a type not specially handled by
     * this method, this method checks the CBOR object for methods starting
     * with the word "get" or "is" that take no parameters, and returns a
     * CBOR map with one entry for each such method found. For each method
     * found, the starting word "get" or "is" is deleted from its name, and
     * the name is converted to camel case (meaning if a name starts with A
     * to Z, that letter is lower-cased). Also, Java <code>Enum</code> objects
     * will be converted to the result of their name method.</p> <p>If the
     * input is a byte array, the byte array is copied to a new byte array.
     * (This method can't be used to decode CBOR data from a byte array; for
     * that, use the DecodeFromBytes method instead.).</p>
     * @param obj An arbitrary object.
     * @return A CBOR object corresponding to the given object. Returns
     * CBORObject.Null if the object is null.
     */
    public static CBORObject FromObject(Object obj) {
      if (obj == null) {
        return CBORObject.Null;
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
      if (obj instanceof CBORObject) {
        return FromObject((CBORObject)obj);
      }
      EInteger eif = ((obj instanceof EInteger) ? (EInteger)obj : null);
      if (eif != null) {
        return FromObject(eif);
      }
      EDecimal edf = ((obj instanceof EDecimal) ? (EDecimal)obj : null);
      if (edf != null) {
        return FromObject(edf);
      }
      EFloat eff = ((obj instanceof EFloat) ? (EFloat)obj : null);
      if (eff != null) {
        return FromObject(eff);
      }
      ERational erf = ((obj instanceof ERational) ? (ERational)obj : null);
      if (erf != null) {
        return FromObject(erf);
      }

      BigInteger bi = ((obj instanceof BigInteger) ? (BigInteger)obj : null);
      if (bi != null) {
        return FromObject(bi);
      }
      ExtendedDecimal df = ((obj instanceof ExtendedDecimal) ? (ExtendedDecimal)obj : null);
      if (df != null) {
        return FromObject(df);
      }
      ExtendedFloat bf = ((obj instanceof ExtendedFloat) ? (ExtendedFloat)obj : null);
      if (bf != null) {
        return FromObject(bf);
      }
      ExtendedRational rf = ((obj instanceof ExtendedRational) ? (ExtendedRational)obj : null);
      if (rf != null) {
        return FromObject(rf);
      }

      if (obj instanceof Short) {
      return FromObject(((Short)obj).shortValue());
      }
      if (obj instanceof Character) {
        return FromObject(((Character)obj).charValue());
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

      if (obj instanceof Enum<?>) {
        return FromObject(PropertyMap.EnumToObject((Enum<?>)obj));
      }
      if (obj instanceof Double) {
        return FromObject(((Double)obj).doubleValue());
      }
      byte[] bytearr = ((obj instanceof byte[]) ? (byte[])obj : null);
      if (bytearr != null) {
        return FromObject(bytearr);
      }
      CBORObject objret;
      if (obj instanceof Map<?, ?>) {
        // Map appears first because Map includes Iterable
        objret = CBORObject.NewMap();
        Map<?, ?> objdic =
          (Map<?, ?>)obj;
        for (Object key : objdic.keySet()) {
       objret.set(CBORObject.FromObject(key), CBORObject.FromObject(objdic.get(key)));
        }
        return objret;
      }
      if (obj.getClass().isArray()) {
        return PropertyMap.FromArray(obj);
      }
      if (obj instanceof Iterable<?>) {
        objret = CBORObject.NewArray();
        for (Object element : (Iterable<?>)obj) {
          objret.Add(CBORObject.FromObject(element));
        }
        return objret;
      }
      objret = ConvertWithConverter(obj);
      if (objret != null) {
        return objret;
      }
      objret = CBORObject.NewMap();
      for (Map.Entry<String, Object> key : PropertyMap.GetProperties(obj)) {
        objret.set(key.getKey(), CBORObject.FromObject(key.getValue()));
      }
      return objret;
    }

    /**
     * Generates a CBOR object from an arbitrary object and gives the resulting
     * object a tag.
     * @param valueOb An arbitrary object. If the tag number is 2 or 3, this must
     * be a byte string whose bytes represent an integer in little-endian
     * byte order, and the value of the number is 1 minus the integer's
     * value for tag 3. If the tag number is 4 or 5, this must be an array
     * with two elements: the first must be an integer representing the
     * exponent, and the second must be an integer representing a mantissa.
     * @param bigintTag Tag number. The tag number 55799 can be used to mark a
     * "self-described CBOR" object.
     * @return A CBOR object where the object {@code valueOb} is converted to a
     * CBOR object and given the tag {@code bigintTag}.
     * @throws IllegalArgumentException The parameter {@code bigintTag} is less
     * than 0 or greater than 2^64-1, or {@code valueOb} 's type is
     * unsupported.
     * @throws java.lang.NullPointerException The parameter {@code bigintTag} is
     * null.
     * @deprecated Use the EInteger version instead.
 */
@Deprecated
    public static CBORObject FromObjectAndTag(
      Object valueOb,
      BigInteger bigintTag) {
      if (bigintTag == null) {
        throw new NullPointerException("bigintTag");
      }
      return FromObjectAndTag(valueOb, PropertyMap.FromLegacy(bigintTag));
    }

    /**
     * Generates a CBOR object from an arbitrary object and gives the resulting
     * object a tag.
     * @param valueOb An arbitrary object. If the tag number is 2 or 3, this must
     * be a byte string whose bytes represent an integer in little-endian
     * byte order, and the value of the number is 1 minus the integer's
     * value for tag 3. If the tag number is 4 or 5, this must be an array
     * with two elements: the first must be an integer representing the
     * exponent, and the second must be an integer representing a mantissa.
     * @param bigintTag Tag number. The tag number 55799 can be used to mark a
     * "self-described CBOR" object.
     * @return A CBOR object where the object {@code valueOb} is converted to a
     * CBOR object and given the tag {@code bigintTag}.
     * @throws IllegalArgumentException The parameter {@code bigintTag} is less
     * than 0 or greater than 2^64-1, or {@code valueOb} 's type is
     * unsupported.
     * @throws java.lang.NullPointerException The parameter {@code bigintTag} is
     * null.
     */
    public static CBORObject FromObjectAndTag(
      Object valueOb,
      EInteger bigintTag) {
      if (bigintTag == null) {
        throw new NullPointerException("bigintTag");
      }
      if (bigintTag.signum() < 0) {
        throw new IllegalArgumentException("tagEInt's sign (" + bigintTag.signum() +
                    ") is less than 0");
      }
      if (bigintTag.compareTo(UInt64MaxValue) > 0) {
        throw new IllegalArgumentException(
          "tag more than 18446744073709551615 (" + bigintTag + ")");
      }
      CBORObject c = FromObject(valueOb);
      if (bigintTag.GetSignedBitLength() <= 16) {
        // Low-numbered, commonly used tags
        return FromObjectAndTag(c, bigintTag.ToInt32Checked());
      } else {
        int tagLow = 0;
        int tagHigh = 0;
        byte[] bytes = bigintTag.ToBytes(true);
        for (int i = 0; i < Math.min(4, bytes.length); ++i) {
          int b = ((int)bytes[i]) & 0xff;
          tagLow = (tagLow | (((int)b) << (i * 8)));
        }
        for (int i = 4; i < Math.min(8, bytes.length); ++i) {
          int b = ((int)bytes[i]) & 0xff;
          tagHigh = (tagHigh | (((int)b) << (i * 8)));
        }
        CBORObject c2 = new CBORObject(c, tagLow, tagHigh);
        ICBORTag tagconv = FindTagConverter(bigintTag);
        if (tagconv != null) {
          c2 = tagconv.ValidateObject(c2);
        }
        return c2;
      }
    }

    /**
     * Generates a CBOR object from an arbitrary object and gives the resulting
     * object a tag.
     * @param valueObValue An arbitrary object. If the tag number is 2 or 3, this
     * must be a byte string whose bytes represent an integer in
     * little-endian byte order, and the value of the number is 1 minus the
     * integer's value for tag 3. If the tag number is 4 or 5, this must be
     * an array with two elements: the first must be an integer representing
     * the exponent, and the second must be an integer representing a
     * mantissa.
     * @param smallTag A 32-bit integer that specifies a tag number. The tag number
     * 55799 can be used to mark a "self-described CBOR" object.
     * @return A CBOR object where the object {@code valueObValue} is converted to
     * a CBOR object and given the tag {@code smallTag}.
     * @throws IllegalArgumentException The parameter {@code smallTag} is less than
     * 0 or {@code valueObValue} 's type is unsupported.
     */
    public static CBORObject FromObjectAndTag(
      Object valueObValue,
      int smallTag) {
      if (smallTag < 0) {
        throw new IllegalArgumentException("smallTag (" + smallTag +
                    ") is less than 0");
      }
      ICBORTag tagconv = FindTagConverter(smallTag);
      CBORObject c = FromObject(valueObValue);
      c = new CBORObject(c, smallTag, 0);
      return (tagconv != null) ? tagconv.ValidateObject(c) : c;
    }

    /**
     * Creates a CBOR object from a simple value number.
     * @param simpleValue A 32-bit signed integer.
     * @return A CBORObject object.
     * @throws IllegalArgumentException The parameter {@code simpleValue} is less
     * than 0, greater than 255, or from 24 through 31.
     */
    public static CBORObject FromSimpleValue(int simpleValue) {
      if (simpleValue < 0) {
        throw new IllegalArgumentException("simpleValue (" + simpleValue +
                    ") is less than 0");
      }
      if (simpleValue > 255) {
        throw new IllegalArgumentException("simpleValue (" + simpleValue +
                    ") is more than " + "255");
      }
      if (simpleValue >= 24 && simpleValue < 32) {
        throw new IllegalArgumentException("Simple value is from 24 to 31: " +
                    simpleValue);
      }
      if (simpleValue < 32) {
        return valueFixedObjects[0xe0 + simpleValue];
      }
      return new CBORObject(
        CBORObjectTypeSimpleValue,
        simpleValue);
    }

    /**
     * Multiplies two CBOR numbers.
     * @param first A CBORObject object.
     * @param second Another CBOR object.
     * @return The product of the two numbers.
     * @throws IllegalArgumentException Either or both operands are not numbers (as
     * opposed to Not-a-Number, NaN).
     */
    public static CBORObject Multiply(CBORObject first, CBORObject second) {
      return CBORObjectMath.Multiply(first, second);
    }

    /**
     * Creates a new empty CBOR array.
     * @return A new CBOR array.
     */
    public static CBORObject NewArray() {
      return new CBORObject(CBORObjectTypeArray, new ArrayList<CBORObject>());
    }

    /**
     * Creates a new empty CBOR map.
     * @return A new CBOR map.
     */
    public static CBORObject NewMap() {
      return FromObject(new HashMap<CBORObject, CBORObject>());
    }

    /**
     * Reads an object in CBOR format from a data stream. This method will read
     * from the stream until the end of the CBOR object is reached or an
     * error occurs, whichever happens first.
     * @param stream A readable data stream.
     * @return A CBOR object that was read.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data.
     */
    public static CBORObject Read(InputStream stream) {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      try {
        return new CBORReader(stream).Read(null);
      } catch (IOException ex) {
        throw new CBORException("I/O error occurred.", ex);
      }
    }

    /**
     * Reads an object in CBOR format from a data stream. This method will read
     * from the stream until the end of the CBOR object is reached or an
     * error occurs, whichever happens first.
     * @param stream A readable data stream.
     * @param options A CBOREncodeOptions object.
     * @return A CBOR object that was read.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data.
     */
    public static CBORObject Read(InputStream stream, CBOREncodeOptions options) {
      if (options == null) {
        throw new NullPointerException("options");
      }
      try {
        CBORReader reader = new CBORReader(stream);
        CBOREncodeOptions opt = options.And(CBOREncodeOptions.NoDuplicateKeys);
        if (opt.getValue() != 0) {
          reader.setDuplicatePolicy(CBORReader.CBORDuplicatePolicy.Disallow);
        }
        return reader.Read(null);
      } catch (IOException ex) {
        throw new CBORException("I/O error occurred.", ex);
      }
    }

    /**
     * Generates a CBOR object from a data stream in JavaScript Object Notation
     * (JSON) format. The JSON stream may begin with a byte-order mark
     * (U + FEFF). Since version 2.0, the JSON stream can be in UTF-8, UTF-16,
     * or UTF-32 encoding; the encoding is detected by assuming that the
     * first character read must be a byte-order mark or a nonzero basic
     * character (U + 0001 to U + 007F). (In previous versions, only UTF-8 was
     * allowed.) <p>If a JSON object has the same key, only the last given
     * value will be used for each duplicated key.</p>
     * @param stream A readable data stream. The sequence of bytes read from the
     * data stream must contain a single JSON object and not multiple
     * objects.
     * @return A CBORObject object.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @throws com.upokecenter.cbor.CBORException The data stream contains invalid
     * encoding or is not in JSON format.
     */
    public static CBORObject ReadJSON(InputStream stream) throws java.io.IOException {
      return ReadJSON(stream, CBOREncodeOptions.None);
    }

    /**
     * Generates a CBOR object from a data stream in JavaScript Object Notation
     * (JSON) format. The JSON stream may begin with a byte-order mark
     * (U + FEFF). Since version 2.0, the JSON stream can be in UTF-8, UTF-16,
     * or UTF-32 encoding; the encoding is detected by assuming that the
     * first character read must be a byte-order mark or a nonzero basic
     * character (U + 0001 to U + 007F). (In previous versions, only UTF-8 was
     * allowed.) <p>By default, if a JSON object has the same key, only the
     * last given value will be used for each duplicated key.</p>
     * @param stream A readable data stream. The sequence of bytes read from the
     * data stream must contain a single JSON object and not multiple
     * objects.
     * @param options A CBOREncodeOptions object.
     * @return A CBORObject object.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @throws com.upokecenter.cbor.CBORException The data stream contains invalid
     * encoding or is not in JSON format.
     */
    public static CBORObject ReadJSON(
  InputStream stream,
  CBOREncodeOptions options) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      CharacterInputWithCount reader = new CharacterInputWithCount(
        new CharacterReader(stream, 2, true));
      try {
        CBOREncodeOptions opt = options.And(CBOREncodeOptions.NoDuplicateKeys);
        int[] nextchar = new int[1];
        CBORObject obj = CBORJson.ParseJSONValue(
     reader,
     opt.getValue() != 0,
     false,
     nextchar);
        if (nextchar[0] != -1) {
          reader.RaiseError("End of data stream not reached");
        }
        return obj;
      } catch (CBORException ex) {
        IOException ioex = ((ex.getCause() instanceof IOException) ? (IOException)ex.getCause() : null);
        if (ioex != null) {
          throw ioex;
        }
        throw ex;
      }
    }

    /**
     * Finds the remainder that results when a CBORObject object is divided by the
     * value of a CBORObject object.
     * @param first A CBORObject object.
     * @param second Another CBOR object.
     * @return The remainder of the two numbers.
     */
    public static CBORObject Remainder(CBORObject first, CBORObject second) {
      return CBORObjectMath.Remainder(first, second);
    }

    /**
     * Finds the difference between two CBOR number objects.
     * @param first A CBORObject object.
     * @param second Another CBOR object.
     * @return The difference of the two objects.
     * @throws IllegalArgumentException Either or both operands are not numbers (as
     * opposed to Not-a-Number, NaN).
     */
    public static CBORObject Subtract(CBORObject first, CBORObject second) {
      return CBORObjectMath.Subtract(first, second);
    }

    /**
     * Writes a string in CBOR format to a data stream. The string will be encoded
     * using indefinite-length encoding if its length exceeds a certain
     * threshold (this behavior may change in future versions of this
     * library).
     * @param str The string to write. Can be null.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(String str, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (str == null) {
        stream.write(0xf6);  // Write null instead of String
      } else {
        // TODO: Maybe change to unstreamed String in 3.0
        WriteStreamedString(str, stream);
      }
    }

    /**
     * Writes a string in CBOR format to a data stream.
     * @param str The string to write. Can be null.
     * @param stream A writable data stream.
     * @param options Options for encoding the data to CBOR.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(
      String str,
      OutputStream stream,
      CBOREncodeOptions options) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (str == null) {
        stream.write(0xf6);  // Write null instead of String
      } else {
        CBOREncodeOptions noIndef =
          options.And(CBOREncodeOptions.NoIndefLengthStrings);
        if (noIndef.getValue() != 0) {
          // NOTE: Length of a String Object won't be higher than the maximum
          // allowed for definite-length strings
          long codePointLength = DataUtilities.GetUtf8Length(str, true);
          WritePositiveInt64(3, codePointLength, stream);
          DataUtilities.WriteUtf8(str, stream, true);
        } else {
          WriteStreamedString(str, stream);
        }
      }
    }

    /**
     * Writes a binary floating-point number in CBOR format to a data stream as
     * follows: <ul> <li>If the value is null, writes the byte 0xF6.</li>
     * <li>If the value is negative zero, infinity, or NaN, converts the
     * number to a <code>double</code> and writes that <code>double</code>. If negative
     * zero should not be written this way, use the Plus method to convert
     * the value beforehand.</li> <li>If the value has an exponent of zero,
     * writes the value as an unsigned integer or signed integer if the
     * number can fit either type or as a big integer otherwise.</li> <li>In
     * all other cases, writes the value as a big float.</li></ul>
     * @param bignum An arbitrary-precision binary float.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @deprecated Pass an EFloat to the Write method instead.
 */
@Deprecated
    public static void Write(ExtendedFloat bignum, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (bignum == null) {
        stream.write(0xf6);
        return;
      }
      Write(PropertyMap.FromLegacy(bignum), stream);
    }

    /**
     * Writes a binary floating-point number in CBOR format to a data stream as
     * follows: <ul> <li>If the value is null, writes the byte 0xF6.</li>
     * <li>If the value is negative zero, infinity, or NaN, converts the
     * number to a <code>double</code> and writes that <code>double</code>. If negative
     * zero should not be written this way, use the Plus method to convert
     * the value beforehand.</li> <li>If the value has an exponent of zero,
     * writes the value as an unsigned integer or signed integer if the
     * number can fit either type or as a big integer otherwise.</li> <li>In
     * all other cases, writes the value as a big float.</li></ul>
     * @param bignum An arbitrary-precision binary float.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
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
        Write(bignum.ToDouble(), stream);
        return;
      }
      EInteger exponent = bignum.getExponent();
      if (exponent.isZero()) {
        Write(bignum.getMantissa(), stream);
      } else {
        if (!BigIntFits(exponent)) {
          stream.write(0xd9);  // tag 265
          stream.write(0x01);
          stream.write(0x09);
          stream.write(0x82);  // array, length 2
        } else {
          stream.write(0xc5);  // tag 5
          stream.write(0x82);  // array, length 2
        }
        Write(bignum.getExponent(), stream);
        Write(bignum.getMantissa(), stream);
      }
    }

    /**
     * Writes a rational number in CBOR format to a data stream.
     * @param rational An arbitrary-precision rational number.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @deprecated Pass an ERational to the Write method instead.
 */
@Deprecated
    public static void Write(ExtendedRational rational, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (rational == null) {
        stream.write(0xf6);
        return;
      }
      Write(PropertyMap.FromLegacy(rational), stream);
    }

    /**
     * Writes a rational number in CBOR format to a data stream.
     * @param rational An arbitrary-precision rational number.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(ERational rational, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (rational == null) {
        stream.write(0xf6);
        return;
      }
      if (!rational.isFinite()) {
        Write(rational.ToDouble(), stream);
        return;
      }
      if (rational.getDenominator().equals(EInteger.FromInt32(1))) {
        Write(rational.getNumerator(), stream);
        return;
      }
      stream.write(0xd8);  // tag 30
      stream.write(0x1e);
      stream.write(0x82);  // array, length 2
      Write(rational.getNumerator(), stream);
      Write(rational.getDenominator(), stream);
    }

    /**
     * Writes a decimal floating-point number in CBOR format to a data stream, as
     * follows: <ul> <li>If the value is null, writes the byte 0xF6.</li>
     * <li>If the value is negative zero, infinity, or NaN, converts the
     * number to a <code>double</code> and writes that <code>double</code>. If negative
     * zero should not be written this way, use the Plus method to convert
     * the value beforehand.</li> <li>If the value has an exponent of zero,
     * writes the value as an unsigned integer or signed integer if the
     * number can fit either type or as a big integer otherwise.</li> <li>In
     * all other cases, writes the value as a decimal number.</li></ul>
     * @param bignum The arbitrary-precision decimal number to write. Can be null.
     * @param stream InputStream to write to.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @deprecated Pass an EDecimal to the Write method instead.
 */
@Deprecated
    public static void Write(ExtendedDecimal bignum, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (bignum == null) {
        stream.write(0xf6);
        return;
      }
      Write(PropertyMap.FromLegacy(bignum), stream);
    }

    /**
     * Writes a decimal floating-point number in CBOR format to a data stream, as
     * follows: <ul> <li>If the value is null, writes the byte 0xF6.</li>
     * <li>If the value is negative zero, infinity, or NaN, converts the
     * number to a <code>double</code> and writes that <code>double</code>. If negative
     * zero should not be written this way, use the Plus method to convert
     * the value beforehand.</li> <li>If the value has an exponent of zero,
     * writes the value as an unsigned integer or signed integer if the
     * number can fit either type or as a big integer otherwise.</li> <li>In
     * all other cases, writes the value as a decimal number.</li></ul>
     * @param bignum The arbitrary-precision decimal number to write. Can be null.
     * @param stream InputStream to write to.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(EDecimal bignum, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (bignum == null) {
        stream.write(0xf6);
        return;
      }
      if ((bignum.isZero() && bignum.isNegative()) || bignum.IsInfinity() ||
          bignum.IsNaN()) {
        Write(bignum.ToDouble(), stream);
        return;
      }
      EInteger exponent = bignum.getExponent();
      if (exponent.isZero()) {
        Write(bignum.getMantissa(), stream);
      } else {
        if (!BigIntFits(exponent)) {
          stream.write(0xd9);  // tag 264
          stream.write(0x01);
          stream.write(0x08);
          stream.write(0x82);  // array, length 2
        } else {
          stream.write(0xc4);  // tag 4
          stream.write(0x82);  // array, length 2
        }
        Write(bignum.getExponent(), stream);
        Write(bignum.getMantissa(), stream);
      }
    }

    /**
     * Writes a big integer in CBOR format to a data stream.
     * @param bigint Big integer to write. Can be null.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @deprecated Pass an EInteger to this method instead.
 */
@Deprecated
    public static void Write(BigInteger bigint, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if ((Object)bigint == (Object)null) {
        stream.write(0xf6);
        return;
      }
      Write(PropertyMap.FromLegacy(bigint), stream);
    }

    /**
     * Writes a big integer in CBOR format to a data stream.
     * @param bigint Big integer to write. Can be null.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(EInteger bigint, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if ((Object)bigint == (Object)null) {
        stream.write(0xf6);
        return;
      }
      int datatype = 0;
      if (bigint.signum() < 0) {
        datatype = 1;
        bigint = bigint.Add(EInteger.FromInt32(1));
        bigint=(bigint).Negate();
      }
      if (bigint.compareTo(Int64MaxValue) <= 0) {
        // If the big integer is representable as a long and in
        // major type 0 or 1, write that major type
        // instead of as a bignum
        long ui = bigint.AsInt64Checked();
        WritePositiveInt64(datatype, ui, stream);
      } else {
        // Get a byte array of the big integer's value,
        // since shifting and doing AND operations is
        // slow with large BigIntegers
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
            byte value = bytes[i];
            bytes[i] = bytes[right];
            bytes[right] = value;
          }
        }
        switch (byteCount) {
          case 0:
            stream.write((byte)(datatype << 5));
            return;
          case 1:
            WritePositiveInt(datatype, ((int)bytes[0]) & 0xff, stream);
            break;
          case 2:
            stream.write((byte)((datatype << 5) | 25));
            stream.write(bytes, 0, byteCount);
            break;
          case 3:
            stream.write((byte)((datatype << 5) | 26));
            stream.write((byte)0);
            stream.write(bytes, 0, byteCount);
            break;
          case 4:
            stream.write((byte)((datatype << 5) | 26));
            stream.write(bytes, 0, byteCount);
            break;
          case 5:
            stream.write((byte)((datatype << 5) | 27));
            stream.write((byte)0);
            stream.write((byte)0);
            stream.write((byte)0);
            stream.write(bytes, 0, byteCount);
            break;
          case 6:
            stream.write((byte)((datatype << 5) | 27));
            stream.write((byte)0);
            stream.write((byte)0);
            stream.write(bytes, 0, byteCount);
            break;
          case 7:
            stream.write((byte)((datatype << 5) | 27));
            stream.write((byte)0);
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

    /**
     * Writes a 64-bit signed integer in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(long value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (value >= 0) {
        WritePositiveInt64(0, value, stream);
      } else {
        ++value;
        value = -value;  // Will never overflow
        WritePositiveInt64(1, value, stream);
      }
    }

    /**
     * Writes a 32-bit signed integer in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
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
        byte[] bytes = {  (byte)(24 | type), (byte)(value & 0xff)  };
        stream.write(bytes, 0, 2);
      } else if (value <= 0xffff) {
        byte[] bytes = {  (byte)(25 | type), (byte)((value >> 8) & 0xff),
          (byte)(value & 0xff)  };
        stream.write(bytes, 0, 3);
      } else {
        byte[] bytes = {  (byte)(26 | type), (byte)((value >> 24) & 0xff),
          (byte)((value >> 16) & 0xff), (byte)((value >> 8) & 0xff),
          (byte)(value & 0xff)  };
        stream.write(bytes, 0, 5);
      }
    }

    /**
     * Writes a 16-bit signed integer in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(short value, OutputStream stream) throws java.io.IOException {
      Write((long)value, stream);
    }

    /**
     * Writes a Unicode character as a string in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws IllegalArgumentException The parameter {@code value} is a surrogate
     * code point.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(char value, OutputStream stream) throws java.io.IOException {
      if (value >= 0xd800 && value < 0xe000) {
        throw new IllegalArgumentException("Value is a surrogate code point.");
      }
      char[] valueChar = { value };
      Write(new String(valueChar), stream);
    }

    /**
     * Writes a Boolean value in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(boolean value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      stream.write(value ? (byte)0xf5 : (byte)0xf4);
    }

    /**
     * Writes a byte (0 to 255) in CBOR format to a data stream. If the value is
     * less than 24, writes that byte. If the value is 25 to 255, writes the
     * byte 24, then this byte's value.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(byte value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if ((((int)value) & 0xff) < 24) {
        stream.write(value);
      } else {
        stream.write((byte)24);
        stream.write(value);
      }
    }

    /**
     * Writes a 32-bit floating-point number in CBOR format to a data stream.
     * @param value The value to write.
     * @param s A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code s} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(float value, OutputStream s) throws java.io.IOException {
      if (s == null) {
        throw new NullPointerException("s");
      }
      int bits = Float.floatToRawIntBits(value);
      byte[] data = {  (byte)0xfa, (byte)((bits >> 24) & 0xff),
        (byte)((bits >> 16) & 0xff), (byte)((bits >> 8) & 0xff),
        (byte)(bits & 0xff)  };
      s.write(data, 0, 5);
    }

    /**
     * Writes a 64-bit floating-point number in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(double value, OutputStream stream) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      long bits =
        Double.doubleToRawLongBits(value);
      byte[] data = {  (byte)0xfb,
        (byte)((bits >> 56) & 0xff), (byte)((bits >> 48) & 0xff),
        (byte)((bits >> 40) & 0xff), (byte)((bits >> 32) & 0xff),
        (byte)((bits >> 24) & 0xff), (byte)((bits >> 16) & 0xff),
        (byte)((bits >> 8) & 0xff), (byte)(bits & 0xff)  };
      stream.write(data, 0, 9);
    }

    /**
     * Writes a CBOR object to a CBOR data stream.
     * @param value The value to write. Can be null.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     */
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

    /**
     * Writes a CBOR object to a CBOR data stream. See the three-parameter Write
     * method that takes a CBOREncodeOptions.
     * @param objValue An arbitrary object.
     * @param stream A writable data stream.
     */
    public static void Write(Object objValue, OutputStream stream) throws java.io.IOException {
      Write(objValue, stream, CBOREncodeOptions.None);
    }

    /**
     * Writes an arbitrary object to a CBOR data stream. Currently, the following
     * objects are supported: <ul> <li>Lists of CBORObject.</li> <li>Maps of
     * CBORObject.</li> <li>Null.</li> <li>Byte arrays, which will always be
     * written as definite-length byte strings.</li> <li>String objects,
     * which will be written as indefinite-length text strings if their size
     * exceeds a certain threshold (this behavior may change in future
     * versions of this library).</li> <li>Any object accepted by the
     * FromObject static methods.</li></ul>
     * @param objValue The arbitrary object to be serialized. Can be null.
     * @param output A writable data stream.
     * @param options CBOR options for encoding the CBOR object to bytes.
     * @throws IllegalArgumentException The object's type is not supported.
     * @throws java.lang.NullPointerException The parameter {@code options} or {@code
     * output} is null.
     */
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
      byte[] data = ((objValue instanceof byte[]) ? (byte[])objValue : null);
      if (data != null) {
        WritePositiveInt(3, data.length, output);
        output.write(data, 0, data.length);
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
      FromObject(objValue).WriteTo(output);
    }

    /**
     * Converts an arbitrary object to a string in JavaScript Object Notation
     * (JSON) format, as in the ToJSONString method, and writes that string
     * to a data stream in UTF-8.
     * @param obj An arbitrary object.
     * @param outputStream A writable data stream.
     */
    public static void WriteJSON(Object obj, OutputStream outputStream) throws java.io.IOException {
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

    /**
     * Gets this object's absolute value.
     * @return This object's absolute without its negative sign.
     * @throws IllegalStateException This object's type is not a number type.
     */
    public CBORObject Abs() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("This Object is not a number.");
      }
      Object oldItem = this.getThisItem();
      Object newItem = cn.Abs(oldItem);
      if (oldItem == newItem) {
        return this;
      }
      if (newItem instanceof EDecimal) {
        return CBORObject.FromObject((EDecimal)newItem);
      }
      if (newItem instanceof EInteger) {
        return CBORObject.FromObject((EInteger)newItem);
      }
      if (newItem instanceof EFloat) {
        return CBORObject.FromObject((EFloat)newItem);
      }
      ERational rat = ((newItem instanceof ERational) ? (ERational)newItem : null);
     return (rat != null) ? CBORObject.FromObject(rat) : ((oldItem == newItem) ?
        this : CBORObject.FromObject(newItem));
    }

    /**
     * Adds a new key and its value to this CBOR map, or adds the value if the key
     * doesn't exist.
     * @param key An object representing the key, which will be converted to a
     * CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @param valueOb An object representing the value, which will be converted to
     * a CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @return This instance.
     * @throws IllegalArgumentException The parameter {@code key} already exists in
     * this map.
     * @throws IllegalStateException This object is not a map.
     * @throws IllegalArgumentException The parameter {@code key} or {@code
     * valueOb} has an unsupported type.
     */
    public CBORObject Add(Object key, Object valueOb) {
      if (this.getItemType() == CBORObjectTypeMap) {
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
        map.put(mapKey, mapValue);
      } else {
        throw new IllegalStateException("Not a map");
      }
      return this;
    }

    /**
     * Adds a new object to the end of this array. (Used to throw
     * NullPointerException on a null reference, but now converts the null
     * reference to CBORObject.Null, for convenience with the Object
     * overload of this method.).
     * @param obj A CBOR object.
     * @return This instance.
     * @throws IllegalStateException This object is not an array.
     */
    public CBORObject Add(CBORObject obj) {
      if (this.getItemType() == CBORObjectTypeArray) {
        List<CBORObject> list = this.AsList();
        list.add(obj);
        return this;
      }
      throw new IllegalStateException("Not an array");
    }

    /**
     * Converts an object to a CBOR object and adds it to the end of this array.
     * @param obj A CBOR object.
     * @return This instance.
     * @throws IllegalStateException This object is not an array.
     * @throws IllegalArgumentException The type of {@code obj} is not supported.
     */
    public CBORObject Add(Object obj) {
      if (this.getItemType() == CBORObjectTypeArray) {
        List<CBORObject> list = this.AsList();
        list.add(CBORObject.FromObject(obj));
        return this;
      }
      throw new IllegalStateException("Not an array");
    }

    /**
     * Converts this object to an arbitrary-precision integer. Fractional values
     * are truncated to an integer.
     * @return The closest big integer to this object.
     * @throws IllegalStateException This object's type is not a number type,
     * including if this object is CBORObject.Null.
     * @throws java.lang.ArithmeticException This object's value is infinity or
     * not-a-number (NaN).
     * @deprecated Use the AsEInteger method instead.
 */
@Deprecated
    public BigInteger AsBigInteger() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return BigInteger.fromBytes(
        cn.AsEInteger(this.getThisItem()).ToBytes(true),
        true);
    }

    /**
     * Converts this object to an arbitrary-precision integer. Fractional values
     * are truncated to an integer.
     * @return The closest big integer to this object.
     * @throws IllegalStateException This object's type is not a number type,
     * including if this object is CBORObject.Null.
     * @throws java.lang.ArithmeticException This object's value is infinity or
     * not-a-number (NaN).
     */
    public EInteger AsEInteger() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return cn.AsEInteger(this.getThisItem());
    }

    /**
     * Returns false if this object is False, Null, or Undefined; otherwise, true.
     * @return False if this object is False, Null, or Undefined; otherwise, true.
     */
    public boolean AsBoolean() {
      return !this.isFalse() && !this.isNull() && !this.isUndefined();
    }

    /**
     * Converts this object to a byte (0 to 255). Floating point values are
     * truncated to an integer.
     * @return The closest byte-sized integer to this object.
     * @throws IllegalStateException This object's type is not a number type.
     * @throws java.lang.ArithmeticException This object's value exceeds the range of a
     * byte (would be less than 0 or greater than 255 when truncated to an
     * integer).
     */
    public byte AsByte() {
      return (byte)this.AsInt32(0, 255);
    }

    /**
     * Converts this object to a 64-bit floating point number.
     * @return The closest 64-bit floating point number to this object. The return
     * value can be positive infinity or negative infinity if this value
     * exceeds the range of a 64-bit floating point number.
     * @throws IllegalStateException This object's type is not a number type.
     */
    public double AsDouble() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return cn.AsDouble(this.getThisItem());
    }

    /**
     * Converts this object to a decimal number.
     * @return A decimal number for this object's value. If this object is a
     * rational number with a nonterminating decimal expansion, returns a
     * decimal number rounded to 34 digits.
     * @throws IllegalStateException This object's type is not a number type,
     * including if this object is CBORObject.Null.
     * @deprecated Use AsEDecimal instead.
 */
@Deprecated
    public ExtendedDecimal AsExtendedDecimal() {
      return ExtendedDecimal.FromString(this.AsEDecimal().toString());
    }

    /**
     * Converts this object to a decimal number.
     * @return A decimal number for this object's value. If this object is a
     * rational number with a nonterminating decimal expansion, returns a
     * decimal number rounded to 34 digits.
     * @throws IllegalStateException This object's type is not a number type,
     * including if this object is CBORObject.Null.
     */
    public EDecimal AsEDecimal() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return cn.AsExtendedDecimal(this.getThisItem());
    }

    /**
     * Converts this object to an arbitrary-precision binary floating point number.
     * @return An arbitrary-precision binary floating point number for this
     * object's value. Note that if this object is a decimal number with a
     * fractional part, the conversion may lose information depending on the
     * number. If this object is a rational number with a nonterminating
     * binary expansion, returns a binary floating-point number rounded to
     * 113 bits.
     * @throws IllegalStateException This object's type is not a number type,
     * including if this object is CBORObject.Null.
     * @deprecated Use AsEFloat instead.
 */
@Deprecated
    public ExtendedFloat AsExtendedFloat() {
      return ExtendedFloat.FromString(this.AsEFloat().toString());
    }

    /**
     * Converts this object to an arbitrary-precision binary floating point number.
     * @return An arbitrary-precision binary floating point number for this
     * object's value. Note that if this object is a decimal number with a
     * fractional part, the conversion may lose information depending on the
     * number. If this object is a rational number with a nonterminating
     * binary expansion, returns a binary floating-point number rounded to
     * 113 bits.
     * @throws IllegalStateException This object's type is not a number type,
     * including if this object is CBORObject.Null.
     */
    public EFloat AsEFloat() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return cn.AsExtendedFloat(this.getThisItem());
    }

    /**
     * Converts this object to a rational number.
     * @return A rational number for this object's value.
     * @throws IllegalStateException This object's type is not a number type,
     * including if this object is CBORObject.Null.
     * @deprecated Use AsERational instead.
 */
@Deprecated
    public ExtendedRational AsExtendedRational() {
      return PropertyMap.ToLegacy(this.AsERational());
    }

    /**
     * Converts this object to a rational number.
     * @return A rational number for this object's value.
     * @throws IllegalStateException This object's type is not a number type,
     * including if this object is CBORObject.Null.
     */
    public ERational AsERational() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return cn.AsExtendedRational(this.getThisItem());
    }

    /**
     * Converts this object to a 16-bit signed integer. Floating point values are
     * truncated to an integer.
     * @return The closest 16-bit signed integer to this object.
     * @throws IllegalStateException This object's type is not a number type.
     * @throws java.lang.ArithmeticException This object's value exceeds the range of a
     * 16-bit signed integer.
     */
    public short AsInt16() {
      return (short)this.AsInt32(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    /**
     * Converts this object to a 32-bit signed integer. Floating point values are
     * truncated to an integer.
     * @return The closest big integer to this object.
     * @throws IllegalStateException This object's type is not a number type.
     * @throws java.lang.ArithmeticException This object's value exceeds the range of a
     * 32-bit signed integer.
     */
    public int AsInt32() {
      return this.AsInt32(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Converts this object to a 64-bit signed integer. Floating point values are
     * truncated to an integer.
     * @return The closest 64-bit signed integer to this object.
     * @throws IllegalStateException This object's type is not a number type.
     * @throws java.lang.ArithmeticException This object's value exceeds the range of a
     * 64-bit signed integer.
     */
    public long AsInt64() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return cn.AsInt64(this.getThisItem());
    }

    /**
     * Converts this object to a 32-bit floating point number.
     * @return The closest 32-bit floating point number to this object. The return
     * value can be positive infinity or negative infinity if this object's
     * value exceeds the range of a 32-bit floating point number.
     * @throws IllegalStateException This object's type is not a number type.
     */
    public float AsSingle() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return cn.AsSingle(this.getThisItem());
    }

    /**
     * Gets the value of this object as a text string.
     * @return Gets this object's string.
     * @throws IllegalStateException This object's type is not a string, including
     * if this object is CBORObject.Null.
     */
    public String AsString() {
      int type = this.getItemType();
      switch (type) {
        case CBORObjectTypeTextString: {
            return (String)this.getThisItem();
          }
        default: throw new IllegalStateException("Not a String type");
      }
    }

    /**
     * Returns whether this object's value can be converted to a 64-bit floating
     * point number without loss of its numerical value.
     * @return Whether this object's value can be converted to a 64-bit floating
     * point number without loss of its numerical value. Returns true if
     * this is a not-a-number value, even if the value's diagnostic
     * information can' t fit in a 64-bit floating point number.
     */
    public boolean CanFitInDouble() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return (cn != null) && cn.CanFitInDouble(this.getThisItem());
    }

    /**
     * Returns whether this object's value is an integral value, is -(2^31) or
     * greater, and is less than 2^31.
     * @return {@code true} if this object's value is an integral value, is -(2^31)
     * or greater, and is less than 2^31; otherwise, {@code false}.
     */
    public boolean CanFitInInt32() {
      if (!this.CanFitInInt64()) {
        return false;
      }
      long v = this.AsInt64();
      return v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE;
    }

    /**
     * Returns whether this object's value is an integral value, is -(2^63) or
     * greater, and is less than 2^63.
     * @return {@code true} if this object's value is an integral value, is -(2^63)
     * or greater, and is less than 2^63; otherwise, {@code false}.
     */
    public boolean CanFitInInt64() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return (cn != null) && cn.CanFitInInt64(this.getThisItem());
    }

    /**
     * Returns whether this object's value can be converted to a 32-bit floating
     * point number without loss of its numerical value.
     * @return Whether this object's value can be converted to a 32-bit floating
     * point number without loss of its numerical value. Returns true if
     * this is a not-a-number value, even if the value's diagnostic
     * information can' t fit in a 32-bit floating point number.
     */
    public boolean CanFitInSingle() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return (cn != null) && cn.CanFitInSingle(this.getThisItem());
    }

    /**
     * Returns whether this object's value, truncated to an integer, would be
     * -(2^31) or greater, and less than 2^31.
     * @return {@code true} if this object's value, truncated to an integer, would
     * be -(2^31) or greater, and less than 2^31; otherwise, {@code false}.
     */
    public boolean CanTruncatedIntFitInInt32() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return (cn != null) && cn.CanTruncatedIntFitInInt32(this.getThisItem());
    }

    /**
     * Returns whether this object's value, truncated to an integer, would be
     * -(2^63) or greater, and less than 2^63.
     * @return {@code true} if this object's value, truncated to an integer, would
     * be -(2^63) or greater, and less than 2^63; otherwise, {@code false}.
     */
    public boolean CanTruncatedIntFitInInt64() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return cn != null && cn.CanTruncatedIntFitInInt64(this.getThisItem());
    }

    /**
     * Compares two CBOR objects. <p>In this implementation:</p> <ul> <li>The null
     * pointer (null reference) is considered less than any other
     * object.</li> <li>If either object is true, false, CBORObject.Null, or
     * the undefined value, it is treated as less than the other value. If
     * both objects have one of these four values, then undefined is less
     * than CBORObject.Null, which is less than false, which is less than
     * true.</li> <li>If both objects are numbers, their mathematical values
     * are compared. Here, NaN (not-a-number) is considered greater than any
     * number.</li> <li>If both objects are simple values other than true,
     * false, CBORObject.Null, and the undefined value, the objects are
     * compared according to their ordinal numbers.</li> <li>If both objects
     * are arrays, each element is compared. If one array is shorter than
     * the other and the other array begins with that array (for the
     * purposes of comparison), the shorter array is considered less than
     * the longer array.</li> <li>If both objects are strings, compares each
     * string code-point by code-point, as though by the
     * DataUtilities.CodePointCompare method.</li> <li>If both objects are
     * maps, compares each map as though each were an array with the sorted
     * keys of that map as the array's elements. If both maps have the same
     * keys, their values are compared in the order of the sorted keys.</li>
     * <li>If each object is a different type, then they are sorted by their
     * type number, in the order given for the CBORType enumeration.</li>
     * <li>If each object has different tags and both objects are otherwise
     * equal under this method, each element is compared as though each were
     * an array with that object's tags listed in order from outermost to
     * innermost.</li></ul> <p>This method is not consistent with the Equals
     * method.</p>
     * @param other A value to compare with.
     * @return Less than 0, if this value is less than the other object; or 0, if
     * both values are equal; or greater than 0, if this value is less than
     * the other object or if the other object is null.
     */
    @SuppressWarnings("unchecked")
public int compareTo(CBORObject other) {
      if (other == null) {
        return 1;
      }
      if (this == other) {
        return 0;
      }
      int typeA = this.getItemType();
      int typeB = other.getItemType();
      Object objA = this.getThisItem();
      Object objB = other.getThisItem();
      int simpleValueA = -1;
      int simpleValueB = -1;
      if (typeA == CBORObjectTypeSimpleValue) {
        if (((Integer)objA).intValue() == 20) {  // false
          simpleValueA = 2;
        } else if (((Integer)objA).intValue() == 21) {  // true
          simpleValueA = 3;
        } else if (((Integer)objA).intValue() == 22) {  // null
          simpleValueA = 1;
        } else if (((Integer)objA).intValue() == 23) {  // undefined
          simpleValueA = 0;
        }
      }
      if (typeB == CBORObjectTypeSimpleValue) {
        if (((Integer)objB).intValue() == 20) {  // false
          simpleValueB = 2;
        } else if (((Integer)objB).intValue() == 21) {  // true
          simpleValueB = 3;
        } else if (((Integer)objB).intValue() == 22) {  // null
          simpleValueB = 1;
        } else if (((Integer)objB).intValue() == 23) {  // undefined
          simpleValueB = 0;
        }
      }
      int cmp = 0;
      if (simpleValueA >= 0 || simpleValueB >= 0) {
        if (simpleValueB < 0) {
          return -1;  // B is not true, false, null, or undefined, so A is less
        }
        if (simpleValueA < 0) {
          return 1;
        }
        cmp = (simpleValueA == simpleValueB) ? 0 : ((simpleValueA <
                    simpleValueB) ? -1 : 1);
      } else if (typeA == typeB) {
        switch (typeA) {
          case CBORObjectTypeInteger: {
              long a = (((Long)objA).longValue());
              long b = (((Long)objB).longValue());
              cmp = (a == b) ? 0 : ((a < b) ? -1 : 1);
              break;
            }
          case CBORObjectTypeSingle: {
              float a = ((Float)objA).floatValue();
              float b = ((Float)objB).floatValue();
              // Treat NaN as greater than all other numbers
              cmp = Float.isNaN(a) ? (Float.isNaN(b) ? 0 : 1) :
                (Float.isNaN(b) ? (-1) : ((a == b) ? 0 : ((a < b) ? -1 :
                    1)));
              break;
            }
          case CBORObjectTypeBigInteger: {
              EInteger bigintA = (EInteger)objA;
              EInteger bigintB = (EInteger)objB;
              cmp = bigintA.compareTo(bigintB);
              break;
            }
          case CBORObjectTypeDouble: {
              double a = ((Double)objA).doubleValue();
              double b = ((Double)objB).doubleValue();
              // Treat NaN as greater than all other numbers
              cmp = Double.isNaN(a) ? (Double.isNaN(b) ? 0 : 1) :
                (Double.isNaN(b) ? (-1) : ((a == b) ? 0 : ((a < b) ? -1 :
                    1)));
              break;
            }
          case CBORObjectTypeExtendedDecimal: {
              cmp = ((EDecimal)objA).compareTo((EDecimal)objB);
              break;
            }
          case CBORObjectTypeExtendedFloat: {
              cmp = ((EFloat)objA).compareTo(
                (EFloat)objB);
              break;
            }
          case CBORObjectTypeExtendedRational: {
              cmp = ((ERational)objA).compareTo(
                (ERational)objB);
              break;
            }
          case CBORObjectTypeByteString: {
              cmp = CBORUtilities.ByteArrayCompare((byte[])objA, (byte[])objB);
              break;
            }
          case CBORObjectTypeTextString: {
              cmp = DataUtilities.CodePointCompare(
                (String)objA,
                (String)objB);
              break;
            }
          case CBORObjectTypeArray: {
              cmp = ListCompare(
                (ArrayList<CBORObject>)objA,
                (ArrayList<CBORObject>)objB);
              break;
            }
          case CBORObjectTypeMap: {
              cmp = MapCompare(
                (Map<CBORObject, CBORObject>)objA,
                (Map<CBORObject, CBORObject>)objB);
              break;
            }
          case CBORObjectTypeSimpleValue: {
              int valueA = ((Integer)objA).intValue();
              int valueB = ((Integer)objB).intValue();
              cmp = (valueA == valueB) ? 0 : ((valueA < valueB) ? -1 : 1);
              break;
            }
          default: throw new IllegalArgumentException("Unexpected data type");
        }
      } else {
        int typeOrderA = ValueNumberTypeOrder[typeA];
        int typeOrderB = ValueNumberTypeOrder[typeB];
        // Check whether general types are different
        // (treating number types the same)
        if (typeOrderA != typeOrderB) {
          return (typeOrderA < typeOrderB) ? -1 : 1;
        }
        // At this point, both types should be number types.

        int s1 = GetSignInternal(typeA, objA);
        int s2 = GetSignInternal(typeB, objB);
        if (s1 != s2 && s1 != 2 && s2 != 2) {
          // if both types are numbers
          // and their signs are different
          return (s1 < s2) ? -1 : 1;
        }
        if (s1 == 2 && s2 == 2) {
          // both are NaN
          cmp = 0;
        } else if (s1 == 2) {
          // first Object is NaN
          return 1;
        } else if (s2 == 2) {
          // second Object is NaN
          return -1;
        } else {
          // DebugUtility.Log("a=" + this + " b=" + other);
          if (typeA == CBORObjectTypeExtendedRational) {
            ERational e1 = NumberInterfaces[typeA].AsExtendedRational(objA);
            if (typeB == CBORObjectTypeExtendedDecimal) {
              EDecimal e2 = NumberInterfaces[typeB].AsExtendedDecimal(objB);
              cmp = e1.CompareToDecimal(e2);
            } else {
              EFloat e2 = NumberInterfaces[typeB].AsExtendedFloat(objB);
              cmp = e1.CompareToBinary(e2);
            }
          } else if (typeB == CBORObjectTypeExtendedRational) {
            ERational e2 = NumberInterfaces[typeB].AsExtendedRational(objB);
            if (typeA == CBORObjectTypeExtendedDecimal) {
              EDecimal e1 = NumberInterfaces[typeA].AsExtendedDecimal(objA);
              cmp = e2.CompareToDecimal(e1);
              cmp = -cmp;
            } else {
              EFloat e1 = NumberInterfaces[typeA].AsExtendedFloat(objA);
              cmp = e2.CompareToBinary(e1);
              cmp = -cmp;
            }
          } else if (typeA == CBORObjectTypeExtendedDecimal ||
                    typeB == CBORObjectTypeExtendedDecimal) {
            EDecimal e1 = null;
            EDecimal e2 = null;
            if (typeA == CBORObjectTypeExtendedFloat) {
              EFloat ef1 = (EFloat)objA;
              e2 = (EDecimal)objB;
              cmp = e2.CompareToBinary(ef1);
              cmp = -cmp;
            } else if (typeB == CBORObjectTypeExtendedFloat) {
              EFloat ef1 = (EFloat)objB;
              e2 = (EDecimal)objA;
              cmp = e2.CompareToBinary(ef1);
            } else {
              e1 = NumberInterfaces[typeA].AsExtendedDecimal(objA);
              e2 = NumberInterfaces[typeB].AsExtendedDecimal(objB);
              cmp = e1.compareTo(e2);
            }
          } else if (typeA == CBORObjectTypeExtendedFloat || typeB ==
                    CBORObjectTypeExtendedFloat ||
                    typeA == CBORObjectTypeDouble || typeB ==
                    CBORObjectTypeDouble ||
                    typeA == CBORObjectTypeSingle || typeB ==
                    CBORObjectTypeSingle) {
            EFloat e1 = NumberInterfaces[typeA].AsExtendedFloat(objA);
            EFloat e2 = NumberInterfaces[typeB].AsExtendedFloat(objB);
            cmp = e1.compareTo(e2);
          } else {
            EInteger b1 = NumberInterfaces[typeA].AsEInteger(objA);
            EInteger b2 = NumberInterfaces[typeB].AsEInteger(objB);
            cmp = b1.compareTo(b2);
          }
        }
      }
      return (cmp == 0) ? ((!this.isTagged() && !other.isTagged()) ? 0 :
           TagsCompare(this.GetAllTags(), other.GetAllTags())) :
                    cmp;
    }

    /**
     * Compares this object and another CBOR object, ignoring the tags they have,
     * if any. See the compareTo method for more information on the
     * comparison function.
     * @param other A value to compare with.
     * @return Less than 0, if this value is less than the other object; or 0, if
     * both values are equal; or greater than 0, if this value is less than
     * the other object or if the other object is null.
     */
    public int CompareToIgnoreTags(CBORObject other) {
      return (other == null) ? 1 : ((this == other) ? 0 :
                    this.Untag().compareTo(other.Untag()));
    }

    /**
     * Determines whether a value of the given key exists in this object.
     * @param key An object that serves as the key.
     * @return {@code true} if the given key is found, or false if the given key is
     * not found or this object is not a map.
     * @throws java.lang.NullPointerException Key is null (as opposed to
     * CBORObject.Null).
     */
    public boolean ContainsKey(CBORObject key) {
      if (key == null) {
        throw new NullPointerException("key");
      }
      if (this.getItemType() == CBORObjectTypeMap) {
        Map<CBORObject, CBORObject> map = this.AsMap();
        return map.containsKey(key);
      }
      return false;
    }

    /**
     * Determines whether a value of the given key exists in this object.
     * @param key A string that serves as the key.
     * @return {@code true} if the given key (as a CBOR object) is found, or false
     * if the given key is not found or this object is not a map.
     * @throws java.lang.NullPointerException Key is null.
     */
    public boolean ContainsKey(String key) {
      if (key == null) {
        throw new NullPointerException("key");
      }
      if (this.getItemType() == CBORObjectTypeMap) {
        Map<CBORObject, CBORObject> map = this.AsMap();
        return map.containsKey(CBORObject.FromObject(key));
      }
      return false;
    }

    /**
     * Gets the binary representation of this data item.
     * @return A byte array in CBOR format.
     */
    public byte[] EncodeToBytes() {
      return this.EncodeToBytes(CBOREncodeOptions.None);
    }

    /**
     * Gets the binary representation of this data item.
     * @param options Options for encoding the data to CBOR.
     * @return A byte array in CBOR format.
     * @throws java.lang.NullPointerException The parameter {@code options} is null.
     */
    public byte[] EncodeToBytes(CBOREncodeOptions options) {
      if (options == null) {
        throw new NullPointerException("options");
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
          tagbyte = (byte)(0xc0 + (int)this.tagLow);
        }
      }
      if (!hasComplexTag) {
        switch (this.getItemType()) {
          case CBORObjectTypeTextString: {
              byte[] ret = GetOptimizedBytesIfShortAscii(
                this.AsString(), tagged ? (((int)tagbyte) & 0xff) : -1);
              if (ret != null) {
                return ret;
              }
              break;
            }
          case CBORObjectTypeSimpleValue: {
              if (tagged) {
                if (this.isFalse()) {
                  return new byte[] { tagbyte, (byte)0xf4  };
                }
                if (this.isTrue()) {
                  return new byte[] { tagbyte, (byte)0xf5  };
                }
                if (this.isNull()) {
                  return new byte[] { tagbyte, (byte)0xf6  };
                }
                if (this.isUndefined()) {
                  return new byte[] { tagbyte, (byte)0xf7  };
                }
              } else {
                if (this.isFalse()) {
                  return new byte[] { (byte)0xf4  };
                }
                if (this.isTrue()) {
                  return new byte[] { (byte)0xf5  };
                }
                if (this.isNull()) {
                  return new byte[] { (byte)0xf6  };
                }
                if (this.isUndefined()) {
                  return new byte[] { (byte)0xf7  };
                }
              }
              break;
            }
          case CBORObjectTypeInteger: {
              long value = (((Long)this.getThisItem()).longValue());
              byte[] intBytes = null;
              if (value >= 0) {
                intBytes = GetPositiveInt64Bytes(0, value);
              } else {
                ++value;
                value = -value;  // Will never overflow
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
          case CBORObjectTypeSingle: {
              float value = ((Float)this.getThisItem()).floatValue();
              int bits = Float.floatToRawIntBits(value);
              return tagged ? new byte[] { tagbyte, (byte)0xfa,
                (byte)((bits >> 24) & 0xff), (byte)((bits >> 16) & 0xff),
                (byte)((bits >> 8) & 0xff), (byte)(bits & 0xff)  } :
                new byte[] { (byte)0xfa, (byte)((bits >> 24) & 0xff),
                (byte)((bits >> 16) & 0xff), (byte)((bits >> 8) & 0xff),
                (byte)(bits & 0xff)  };
            }
          case CBORObjectTypeDouble: {
              double value = ((Double)this.getThisItem()).doubleValue();
              long bits = Double.doubleToRawLongBits(value);
              return tagged ? new byte[] { tagbyte, (byte)0xfb,
                (byte)((bits >> 56) & 0xff), (byte)((bits >> 48) & 0xff),
                (byte)((bits >> 40) & 0xff), (byte)((bits >> 32) & 0xff),
                (byte)((bits >> 24) & 0xff), (byte)((bits >> 16) & 0xff),
                (byte)((bits >> 8) & 0xff), (byte)(bits & 0xff)  } :
                new byte[] { (byte)0xfb, (byte)((bits >> 56) & 0xff),
                (byte)((bits >> 48) & 0xff), (byte)((bits >> 40) & 0xff),
                (byte)((bits >> 32) & 0xff), (byte)((bits >> 24) & 0xff),
                (byte)((bits >> 16) & 0xff), (byte)((bits >> 8) & 0xff),
                (byte)(bits & 0xff)  };
            }
        }
      }
      try {
        java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream(16);

          this.WriteTo(ms, options);
          return ms.toByteArray();
}
finally {
try { if (ms != null)ms.close(); } catch (java.io.IOException ex) {}
}
      } catch (IOException ex) {
        throw new CBORException("I/O Error occurred", ex);
      }
    }

    /**
     * Determines whether this object and another object are equal and have the
     * same type. Not-a-number values can be considered equal by this
     * method.
     * @param obj An arbitrary object.
     * @return {@code true} if the objects are equal; otherwise, {@code false}.
     */
    @Override public boolean equals(Object obj) {
      return this.equals(((obj instanceof CBORObject) ? (CBORObject)obj : null));
    }

    /**
     * Compares the equality of two CBOR objects. Not-a-number values can be
     * considered equal by this method.
     * @param other The object to compare.
     * @return {@code true} if the objects are equal; otherwise, {@code false}.
     */
    @SuppressWarnings("unchecked")
public boolean equals(CBORObject other) {
      CBORObject otherValue = ((other instanceof CBORObject) ? (CBORObject)other : null);
      if (otherValue == null) {
        return false;
      }
      if (this == otherValue) {
        return true;
      }
      switch (this.itemtypeValue) {
        case CBORObjectTypeByteString:
          if (!CBORUtilities.ByteArrayEquals(
            (byte[])this.getThisItem(),
            ((otherValue.itemValue instanceof byte[]) ? (byte[])otherValue.itemValue : null))) {
            return false;
          }
          break;
        case CBORObjectTypeMap: {
            Map<CBORObject, CBORObject> cbordict =
              ((otherValue.itemValue instanceof Map<?, ?>) ? (Map<CBORObject, CBORObject>)otherValue.itemValue : null);
            if (!CBORMapEquals(this.AsMap(), cbordict)) {
              return false;
            }
            break;
          }
        case CBORObjectTypeArray:
          if (!CBORArrayEquals(
            this.AsList(),
            ((otherValue.itemValue instanceof List<?>) ? (List<CBORObject>)otherValue.itemValue : null))) {
            return false;
          }
          break;
        default:
          if (!(((this.itemValue) == null) ? ((otherValue.itemValue) == null) : (this.itemValue).equals(otherValue.itemValue))) {
            return false;
          }
          break;
      }
      return this.itemtypeValue == otherValue.itemtypeValue &&
        this.tagLow == otherValue.tagLow && this.tagHigh == otherValue.tagHigh;
    }

    /**
     * Gets the byte array used in this object, if this object is a byte string,
     * without copying the data to a new one. This method's return value can
     * be used to modify the array's contents. Note, though, that the array'
     * s length can't be changed.
     * @return A byte array.
     * @throws IllegalStateException This object is not a byte string.
     */
    public byte[] GetByteString() {
      if (this.getItemType() == CBORObjectTypeByteString) {
        return (byte[])this.getThisItem();
      }
      throw new IllegalStateException("Not a byte String");
    }

    /**
     * Calculates the hash code of this object. No application or process IDs are
     * used in the hash code calculation.
     * @return A 32-bit hash code.
     */
    @Override public int hashCode() {
      int valueHashCode = 651869431;
      {
        if (this.itemValue != null) {
          int itemHashCode = 0;
          long longValue = 0L;
          switch (this.itemtypeValue) {
            case CBORObjectTypeByteString:
              itemHashCode =
                CBORUtilities.ByteArrayHashCode((byte[])this.getThisItem());
              break;
            case CBORObjectTypeMap:
              itemHashCode = CBORMapHashCode(this.AsMap());
              break;
            case CBORObjectTypeArray:
              itemHashCode = CBORArrayHashCode(this.AsList());
              break;
            case CBORObjectTypeTextString:
              itemHashCode = StringHashCode((String)this.itemValue);
              break;
            case CBORObjectTypeSimpleValue:
              itemHashCode = ((Integer)this.itemValue).intValue();
              break;
            case CBORObjectTypeSingle:
              itemHashCode =
  Float.floatToRawIntBits(((Float)this.itemValue).floatValue());
              break;
            case CBORObjectTypeDouble:
              longValue =
  Double.doubleToRawLongBits(((Double)this.itemValue).doubleValue());
              longValue |= longValue >> 32;
              itemHashCode = ((int)longValue);
              break;
            case CBORObjectTypeInteger:
              longValue = (((Long)this.itemValue).longValue());
              longValue |= longValue >> 32;
              itemHashCode = ((int)longValue);
              break;
            default:
              // EInteger, EFloat, EDecimal, ERational, CBORObject
              itemHashCode = this.itemValue.hashCode();
              break;
          }
          valueHashCode += 651869479 * itemHashCode;
        }
        valueHashCode += 651869483 * (this.itemtypeValue +
                    this.tagLow + this.tagHigh);
      }
      return valueHashCode;
    }

    /**
     * Gets a list of all tags, from outermost to innermost.
     * @return An array of tags, or the empty string if this object is untagged.
     * @deprecated Use the GetAllTags method instead.
 */
@Deprecated
    public BigInteger[] GetTags() {
      EInteger[] etags = this.GetAllTags();
      if (etags.length == 0) {
        return new BigInteger[0];
      }
      BigInteger[] bigret = new BigInteger[etags.length];
      for (int i = 0; i < bigret.length; ++i) {
        bigret[i] = PropertyMap.ToLegacy(etags[i]);
      }
      return bigret;
    }

    /**
     * Gets a list of all tags, from outermost to innermost.
     * @return An array of tags, or the empty string if this object is untagged.
     */
    public EInteger[] GetAllTags() {
      if (!this.isTagged()) {
        return ValueEmptyTags;
      }
      CBORObject curitem = this;
      if (curitem.isTagged()) {
        ArrayList<EInteger> list = new ArrayList<EInteger>();
        while (curitem.isTagged()) {
          list.add(
            LowHighToEInteger(
              curitem.tagLow,
              curitem.tagHigh));
          curitem = (CBORObject)curitem.itemValue;
        }
        return list.toArray(new EInteger[] { });
      }
      return new EInteger[] { LowHighToEInteger(this.tagLow, this.tagHigh) };
    }

    /**
     * Returns whether this object has a tag of the given number.
     * @param tagValue The tag value to search for.
     * @return {@code true} if this object has a tag of the given number;
     * otherwise, {@code false}.
     * @throws IllegalArgumentException TagValue is less than 0.
     * @throws java.lang.NullPointerException The parameter "obj" is null.
     */
    public boolean HasTag(int tagValue) {
      if (tagValue < 0) {
        throw new IllegalArgumentException("tagValue (" + tagValue +
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

    /**
     * Returns whether this object has a tag of the given number.
     * @param bigTagValue The tag value to search for.
     * @return {@code true} if this object has a tag of the given number;
     * otherwise, {@code false}.
     * @throws java.lang.NullPointerException BigTagValue is null.
     * @throws IllegalArgumentException BigTagValue is less than 0.
     * @deprecated Use the EInteger version of this method.
 */
@Deprecated
    public boolean HasTag(BigInteger bigTagValue) {
      if (bigTagValue == null) {
        throw new NullPointerException("bigTagValue");
      }
      return this.HasTag(EInteger.FromBytes(
        bigTagValue.toBytes(true),
        true));
    }

    /**
     * Returns whether this object has a tag of the given number.
     * @param bigTagValue The tag value to search for.
     * @return {@code true} if this object has a tag of the given number;
     * otherwise, {@code false}.
     * @throws java.lang.NullPointerException BigTagValue is null.
     * @throws IllegalArgumentException BigTagValue is less than 0.
     */
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

    /**
     * Inserts an object at the specified position in this CBOR array.
     * @param index Zero-based index to insert at.
     * @param valueOb An object representing the value, which will be converted to
     * a CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @return This instance.
     * @throws IllegalStateException This object is not an array.
     * @throws IllegalArgumentException The parameter {@code valueOb} has an
     * unsupported type; or {@code index} is not a valid index into this
     * array.
     */
    public CBORObject Insert(int index, Object valueOb) {
      if (this.getItemType() == CBORObjectTypeArray) {
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
        list.add(index, mapValue);
      } else {
        throw new IllegalStateException("Not an array");
      }
      return this;
    }

    /**
     * Gets a value indicating whether this CBOR object represents infinity.
     * @return {@code true} if this CBOR object represents infinity; otherwise,
     * {@code false}.
     */
    public boolean IsInfinity() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return cn != null && cn.IsInfinity(this.getThisItem());
    }

    /**
     * Gets a value indicating whether this CBOR object represents a not-a-number
     * value (as opposed to whether this object&#x27;s type is not a number
     * type).
     * @return {@code true} if this CBOR object represents a not-a-number value (as
     * opposed to whether this object's type is not a number type);
     * otherwise, {@code false}.
     */
    public boolean IsNaN() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return cn != null && cn.IsNaN(this.getThisItem());
    }

    /**
     * Gets a value indicating whether this CBOR object represents negative
     * infinity.
     * @return {@code true} if this CBOR object represents negative infinity;
     * otherwise, {@code false}.
     */
    public boolean IsNegativeInfinity() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return cn != null && cn.IsNegativeInfinity(this.getThisItem());
    }

    /**
     * Gets a value indicating whether this CBOR object represents positive
     * infinity.
     * @return {@code true} if this CBOR object represents positive infinity;
     * otherwise, {@code false}.
     */
    public boolean IsPositiveInfinity() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      return cn != null && cn.IsPositiveInfinity(this.getThisItem());
    }

    /**
     * Gets this object's value with the sign reversed.
     * @return The reversed-sign form of this number.
     * @throws IllegalStateException This object's type is not a number type.
     */
    public CBORObject Negate() {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("This Object is not a number.");
      }
      Object newItem = cn.Negate(this.getThisItem());
      if (newItem instanceof EDecimal) {
        return CBORObject.FromObject((EDecimal)newItem);
      }
      if (newItem instanceof EInteger) {
        return CBORObject.FromObject((EInteger)newItem);
      }
      if (newItem instanceof EFloat) {
        return CBORObject.FromObject((EFloat)newItem);
      }
      ERational rat = ((newItem instanceof ERational) ? (ERational)newItem : null);
      return (rat != null) ? CBORObject.FromObject(rat) :
        CBORObject.FromObject(newItem);
    }

    /**
     * If this object is an array, removes the first instance of the specified item
     * from the array. If this object is a map, removes the item with the
     * given key from the map.
     * @param obj The item or key to remove.
     * @return {@code true} if the item was removed; otherwise, {@code false}.
     * @throws java.lang.NullPointerException The parameter {@code obj} is null (as
     * opposed to CBORObject.Null).
     * @throws IllegalStateException The object is not an array or map.
     */
    public boolean Remove(CBORObject obj) {
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      if (this.getItemType() == CBORObjectTypeMap) {
        Map<CBORObject, CBORObject> dict = this.AsMap();
        boolean hasKey = dict.containsKey(obj);
        if (hasKey) {
          dict.remove(obj);
          return true;
        }
        return false;
      }
      if (this.getItemType() == CBORObjectTypeArray) {
        List<CBORObject> list = this.AsList();
        return list.remove(obj);
      }
      throw new IllegalStateException("Not a map or array");
    }

    /**
     * Maps an object to a key in this CBOR map, or adds the value if the key
     * doesn't exist.
     * @param key An object representing the key, which will be converted to a
     * CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @param valueOb An object representing the value, which will be converted to
     * a CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @return This instance.
     * @throws IllegalStateException This object is not a map.
     * @throws IllegalArgumentException The parameter {@code key} or {@code
     * valueOb} has an unsupported type.
     */
    public CBORObject Set(Object key, Object valueOb) {
      if (this.getItemType() == CBORObjectTypeMap) {
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
          map.put(mapKey, mapValue);
        } else {
          map.put(mapKey, mapValue);
        }
      } else {
        throw new IllegalStateException("Not a map");
      }
      return this;
    }

    /**
     * Converts this object to a string in JavaScript Object Notation (JSON)
     * format. This function works not only with arrays and maps, but also
     * integers, strings, byte arrays, and other JSON data types. Notes:
     * <ul> <li>If this object contains maps with non-string keys, the keys
     * are converted to JSON strings before writing the map as a JSON
     * string.</li> <li>If a number in the form of an arbitrary-precision
     * binary float has a very high binary exponent, it will be converted to
     * a double before being converted to a JSON string. (The resulting
     * double could overflow to infinity, in which case the
     * arbitrary-precision binary float is converted to null.)</li> <li>The
     * string will not begin with a byte-order mark (U + FEFF); RFC 7159 (the
     * JSON specification) forbids placing a byte-order mark at the
     * beginning of a JSON string.</li> <li>Byte strings are converted to
     * Base64 URL by default.</li> <li>Rational numbers will be converted to
     * their exact form, if possible, otherwise to a high-precision
     * approximation. (The resulting approximation could overflow to
     * infinity, in which case the rational number is converted to
     * null.)</li> <li>Simple values other than true and false will be
     * converted to null. (This doesn't include floating-point
     * numbers.)</li> <li>Infinity and not-a-number will be converted to
     * null.</li></ul>
     * @return A text string containing the converted object.
     */
    public String ToJSONString() {
      int type = this.getItemType();
      switch (type) {
        case CBORObjectTypeSimpleValue: {
            return this.isTrue() ? "true" : (this.isFalse() ? "false" : "null");
          }
        case CBORObjectTypeInteger: {
            return CBORUtilities.LongToString((((Long)this.getThisItem()).longValue()));
          }
        default:
          {
            StringBuilder sb = new StringBuilder();
            try {
              CBORJson.WriteJSONToInternal(this, new StringOutput(sb));
            } catch (IOException ex) {
              // This is truly exceptional
              throw new IllegalStateException("Internal error", ex);
            }
            return sb.toString();
          }
      }
    }

    /**
     * Returns this CBOR object in string form. The format is intended to be
     * human-readable, not machine-readable, and the format may change at
     * any time.
     * @return A text representation of this object.
     */
    @Override public String toString() {
      StringBuilder sb = null;
      String simvalue = null;
      int type = this.getItemType();
      if (this.isTagged()) {
        if (sb == null) {
          if (type == CBORObjectTypeTextString) {
            // The default capacity of StringBuilder may be too small
            // for many strings, so set a suggested capacity
            // explicitly
            String str = this.AsString();
            sb = new StringBuilder(Math.min(str.length(), 4096) + 16);
          } else {
            sb = new StringBuilder();
          }
        }
        this.AppendOpeningTags(sb);
      }
      switch (type) {
        case CBORObjectTypeSimpleValue: {
            if (this.isTrue()) {
              simvalue = "true";
              if (sb == null) {
                return simvalue;
              }
              sb.append(simvalue);
            } else if (this.isFalse()) {
              simvalue = "false";
              if (sb == null) {
                return simvalue;
              }
              sb.append(simvalue);
            } else if (this.isNull()) {
              simvalue = "null";
              if (sb == null) {
                return simvalue;
              }
              sb.append(simvalue);
            } else if (this.isUndefined()) {
              simvalue = "undefined";
              if (sb == null) {
                return simvalue;
              }
              sb.append(simvalue);
            } else {
              sb = (sb == null) ? ((new StringBuilder())) : sb;
              sb.append("simple(");
              int thisItemInt = ((Integer)this.getThisItem()).intValue();
              sb.append(
                CBORUtilities.LongToString(thisItemInt));
              sb.append(")");
            }

            break;
          }
        case CBORObjectTypeSingle: {
            float f = ((Float)this.getThisItem()).floatValue();
            simvalue = ((f)==Float.NEGATIVE_INFINITY) ? "-Infinity" :
              (((f)==Float.POSITIVE_INFINITY) ? "Infinity" : (Float.isNaN(f) ?
                    "NaN" : TrimDotZero(CBORUtilities.SingleToString(f))));
            if (sb == null) {
              return simvalue;
            }
            sb.append(simvalue);
            break;
          }
        case CBORObjectTypeDouble: {
            double f = ((Double)this.getThisItem()).doubleValue();
            simvalue = ((f)==Double.NEGATIVE_INFINITY) ? "-Infinity" :
              (((f)==Double.POSITIVE_INFINITY) ? "Infinity" : (Double.isNaN(f) ?
                    "NaN" : TrimDotZero(CBORUtilities.DoubleToString(f))));
            if (sb == null) {
              return simvalue;
            }
            sb.append(simvalue);
            break;
          }
        case CBORObjectTypeExtendedFloat: {
            simvalue = ExtendedToString((EFloat)this.getThisItem());
            if (sb == null) {
              return simvalue;
            }
            sb.append(simvalue);
            break;
          }
        case CBORObjectTypeInteger: {
            long v = (((Long)this.getThisItem()).longValue());
            simvalue = CBORUtilities.LongToString(v);
            if (sb == null) {
              return simvalue;
            }
            sb.append(simvalue);
            break;
          }
        case CBORObjectTypeBigInteger: {
            simvalue = CBORUtilities.BigIntToString((EInteger)this.getThisItem());
            if (sb == null) {
              return simvalue;
            }
            sb.append(simvalue);
            break;
          }
        case CBORObjectTypeByteString: {
            sb = (sb == null) ? ((new StringBuilder())) : sb;
            sb.append("h'");
            CBORUtilities.ToBase16(sb, (byte[])this.getThisItem());
            sb.append("'");
            break;
          }
        case CBORObjectTypeTextString: {
            if (sb == null) {
              return "\"" + this.AsString() + "\"";
            }
            sb.append('\"');
            sb.append(this.AsString());
            sb.append('\"');
            break;
          }
        case CBORObjectTypeArray: {
            sb = (sb == null) ? ((new StringBuilder())) : sb;
            boolean first = true;
            sb.append("[");
            for (CBORObject i : this.AsList()) {
              if (!first) {
                sb.append(", ");
              }
              sb.append(i.toString());
              first = false;
            }
            sb.append("]");
            break;
          }
        case CBORObjectTypeMap: {
            sb = (sb == null) ? ((new StringBuilder())) : sb;
            boolean first = true;
            sb.append("{");
            Map<CBORObject, CBORObject> map = this.AsMap();
            for (Map.Entry<CBORObject, CBORObject> entry : map.entrySet()) {
              CBORObject key = entry.getKey();
              CBORObject value = entry.getValue();
              if (!first) {
                sb.append(", ");
              }
              sb.append(key.toString());
              sb.append(": ");
              sb.append(value.toString());
              first = false;
            }
            sb.append("}");
            break;
          }
        default:
          {
            if (sb == null) {
              return this.getThisItem().toString();
            }
            sb.append(this.getThisItem().toString());
            break;
          }
      }

      if (this.isTagged()) {
        this.AppendClosingTags(sb);
      }
      return sb.toString();
    }

    /**
     * Gets an object with the same value as this one but without the tags it has,
     * if any. If this object is an array, map, or byte string, the data
     * will not be copied to the returned object, so changes to the returned
     * object will be reflected in this one.
     * @return A CBORObject object.
     */
    public CBORObject Untag() {
      CBORObject curobject = this;
      while (curobject.itemtypeValue == CBORObjectTypeTagged) {
        curobject = (CBORObject)curobject.itemValue;
      }
      return curobject;
    }

    /**
     * Gets an object with the same value as this one but without this object's
     * outermost tag, if any. If this object is an array, map, or byte
     * string, the data will not be copied to the returned object, so
     * changes to the returned object will be reflected in this one.
     * @return A CBORObject object.
     */
    public CBORObject UntagOne() {
      return (this.itemtypeValue == CBORObjectTypeTagged) ?
        ((CBORObject)this.itemValue) : this;
    }

    /**
     * Converts this object to a string in JavaScript Object Notation (JSON)
     * format, as in the ToJSONString method, and writes that string to a
     * data stream in UTF-8.
     * @param outputStream A writable data stream.
     * @throws java.io.IOException An I/O error occurred.
     * @throws java.lang.NullPointerException The parameter {@code outputStream} is
     * null.
     */
    public void WriteJSONTo(OutputStream outputStream) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      CBORJson.WriteJSONToInternal(this, new StringOutput(outputStream));
    }

    /**
     * Writes this CBOR object to a data stream.
     * @param stream A writable data stream.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public void WriteTo(OutputStream stream) throws java.io.IOException {
      this.WriteTo(stream, CBOREncodeOptions.None);
    }

    /**
     * Writes this CBOR object to a data stream.
     * @param stream A writable data stream.
     * @param options Options for encoding the data to CBOR.
     * @throws java.lang.NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public void WriteTo(OutputStream stream, CBOREncodeOptions options) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      this.WriteTags(stream);
      int type = this.getItemType();
      switch (type) {
        case CBORObjectTypeInteger: {
            Write((((Long)this.getThisItem()).longValue()), stream);
            break;
          }
        case CBORObjectTypeBigInteger: {
            Write((EInteger)this.getThisItem(), stream);
            break;
          }
        case CBORObjectTypeByteString: {
            byte[] arr = (byte[])this.getThisItem();
            WritePositiveInt(
              (this.getItemType() == CBORObjectTypeByteString) ? 2 : 3,
              arr.length,
              stream);
            stream.write(arr, 0, arr.length);
            break;
          }
        case CBORObjectTypeTextString: {
            Write((String)this.getThisItem(), stream, options);
            break;
          }
        case CBORObjectTypeArray: {
            WriteObjectArray(this.AsList(), stream, options);
            break;
          }
        case CBORObjectTypeExtendedDecimal: {
            EDecimal dec = (EDecimal)this.getThisItem();
            Write(dec, stream);
            break;
          }
        case CBORObjectTypeExtendedFloat: {
            EFloat flo = (EFloat)this.getThisItem();
            Write(flo, stream);
            break;
          }
        case CBORObjectTypeExtendedRational: {
            ERational flo = (ERational)this.getThisItem();
            Write(flo, stream);
            break;
          }
        case CBORObjectTypeMap: {
            WriteObjectMap(this.AsMap(), stream, options);
            break;
          }
        case CBORObjectTypeSimpleValue: {
            int value = ((Integer)this.getThisItem()).intValue();
            if (value < 24) {
              stream.write((byte)(0xe0 + value));
            } else {
              stream.write(0xf8);
              stream.write((byte)value);
            }

            break;
          }
        case CBORObjectTypeSingle: {
            Write(((Float)this.getThisItem()).floatValue(), stream);
            break;
          }
        case CBORObjectTypeDouble: {
            Write(((Double)this.getThisItem()).doubleValue(), stream);
            break;
          }
        default: {
            throw new IllegalArgumentException("Unexpected data type");
          }
      }
    }

    static ICBORTag FindTagConverter(EInteger bigintTag) {
      if (TagHandlersEmpty()) {
        AddTagHandler(EInteger.FromInt64(2), new CBORTag2());
        AddTagHandler(EInteger.FromInt64(3), new CBORTag3());
        AddTagHandler(EInteger.FromInt64(4), new CBORTag4());
        AddTagHandler(EInteger.FromInt64(5), new CBORTag5());
        AddTagHandler(EInteger.FromInt64(264), new CBORTag4(true));
        AddTagHandler(EInteger.FromInt64(265), new CBORTag5(true));
        AddTagHandler(EInteger.FromInt64(25), new CBORTagUnsigned());
        AddTagHandler(EInteger.FromInt64(28), new CBORTag28());
        AddTagHandler(EInteger.FromInt64(29), new CBORTagUnsigned());
        AddTagHandler(EInteger.FromInt64(256), new CBORTagAny());
        AddTagHandler(EInteger.FromInt32(0), new CBORTag0());
        AddTagHandler(EInteger.FromInt64(32), new CBORTag32());
        AddTagHandler(EInteger.FromInt64(33), new CBORTagGenericString());
        AddTagHandler(EInteger.FromInt64(34), new CBORTagGenericString());
        AddTagHandler(EInteger.FromInt64(35), new CBORTagGenericString());
        AddTagHandler(EInteger.FromInt64(36), new CBORTagGenericString());
        AddTagHandler(EInteger.FromInt64(37), new CBORTag37());
        AddTagHandler(EInteger.FromInt64(30), new CBORTag30());
      }
      synchronized (ValueTagHandlers) {
        if (ValueTagHandlers.containsKey(bigintTag)) {
          return ValueTagHandlers.get(bigintTag);
        }

        return null;
      }
    }

    static ICBORTag FindTagConverterLong(long tagLong) {
      return FindTagConverter(EInteger.FromInt64(tagLong));
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
      CBORObject fixedObj = valueFixedObjects[firstbyte];
      if (fixedObj != null) {
        return fixedObj;
      }
      int majortype = firstbyte >> 5;
      if (firstbyte >= 0x61 && firstbyte < 0x78) {
        // text String length 1 to 23
        String s = GetOptimizedStringIfShortAscii(data, 0);
        if (s != null) {
          return new CBORObject(CBORObjectTypeTextString, s);
        }
      }
      if ((firstbyte & 0x1c) == 0x18) {
        // contains 1 to 8 extra bytes of additional information
        long uadditional = 0;
        switch (firstbyte & 0x1f) {
          case 24:
            uadditional = (int)(data[1] & (int)0xff);
            break;
          case 25:
            uadditional = ((long)(data[1] & (long)0xff)) << 8;
            uadditional |= (long)(data[2] & (long)0xff);
            break;
          case 26:
            uadditional = ((long)(data[1] & (long)0xff)) << 24;
            uadditional |= ((long)(data[2] & (long)0xff)) << 16;
            uadditional |= ((long)(data[3] & (long)0xff)) << 8;
            uadditional |= (long)(data[4] & (long)0xff);
            break;
          case 27:
            uadditional = ((long)(data[1] & (long)0xff)) << 56;
            uadditional |= ((long)(data[2] & (long)0xff)) << 48;
            uadditional |= ((long)(data[3] & (long)0xff)) << 40;
            uadditional |= ((long)(data[4] & (long)0xff)) << 32;
            uadditional |= ((long)(data[5] & (long)0xff)) << 24;
            uadditional |= ((long)(data[6] & (long)0xff)) << 16;
            uadditional |= ((long)(data[7] & (long)0xff)) << 8;
            uadditional |= (long)(data[8] & (long)0xff);
            break;
          default:
            throw new CBORException("Unexpected data encountered");
        }
        switch (majortype) {
          case 0:
            if ((uadditional >> 63) == 0) {
              // use only if additional's top bit isn't set
              // (additional is a signed long)
              return new CBORObject(CBORObjectTypeInteger, uadditional);
            } else {
              int low = ((int)((uadditional) & 0xffffffffL));
              int high = ((int)((uadditional >> 32) & 0xffffffffL));
              return FromObject(LowHighToEInteger(low, high));
            }
          case 1:
            if ((uadditional >> 63) == 0) {
              // use only if additional's top bit isn't set
              // (additional is a signed long)
              return new CBORObject(CBORObjectTypeInteger, -1 - uadditional);
            } else {
              int low = ((int)((uadditional) & 0xffffffffL));
              int high = ((int)((uadditional >> 32) & 0xffffffffL));
              EInteger bigintAdditional = LowHighToEInteger(low, high);
              EInteger minusOne = EInteger.FromInt64(-1);
              bigintAdditional = minusOne.Subtract(bigintAdditional);
              return FromObject(bigintAdditional);
            }
          case 7:
            if (firstbyte == 0xf9) {
              return new CBORObject(
                CBORObjectTypeSingle,
             CBORUtilities.HalfPrecisionToSingle(((int)uadditional)));
            }
            if (firstbyte == 0xfa) {
              float flt = Float.intBitsToFloat(((int)uadditional));
              return new CBORObject(
                CBORObjectTypeSingle,
                flt);
            }
            if (firstbyte == 0xfb) {
              double flt = Double.longBitsToDouble(uadditional);
              return new CBORObject(
                CBORObjectTypeDouble,
                flt);
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
      if (majortype == 2) {  // short byte String
        byte[] ret = new byte[firstbyte - 0x40];
        System.arraycopy(data, 1, ret, 0, firstbyte - 0x40);
        return new CBORObject(CBORObjectTypeByteString, ret);
      }
      if (majortype == 3) {  // short text String
        StringBuilder ret = new StringBuilder(firstbyte - 0x60);
        DataUtilities.ReadUtf8FromBytes(data, 1, firstbyte - 0x60, ret, false);
        return new CBORObject(CBORObjectTypeTextString, ret.toString());
      }
      if (firstbyte == 0x80) {
        // empty array
        return FromObject(new ArrayList<CBORObject>());
      }
      if (firstbyte == 0xa0) {
        // empty map
        return FromObject(new HashMap<CBORObject, CBORObject>());
      }
      throw new CBORException("Unexpected data encountered");
    }

    static CBORObject GetFixedObject(int value) {
      return valueFixedObjects[value];
    }

    static ICBORNumber GetNumberInterface(int type) {
      return NumberInterfaces[type];
    }

    static String TrimDotZero(String str) {
      return (str.length() > 2 && str.charAt(str.length() - 1) == '0' && str.charAt(str.length()
                    - 2) == '.') ? str.substring(0,str.length() - 2) :
        str;
    }

    @SuppressWarnings("unchecked")
List<CBORObject> AsList() {
      return (List<CBORObject>)this.getThisItem();
    }

    @SuppressWarnings("unchecked")
Map<CBORObject, CBORObject> AsMap() {
      return (Map<CBORObject, CBORObject>)this.getThisItem();
    }

    void Redefine(CBORObject cbor) {
      this.itemtypeValue = cbor.itemtypeValue;
      this.tagLow = cbor.tagLow;
      this.tagHigh = cbor.tagHigh;
      this.itemValue = cbor.itemValue;
    }

    private static boolean BigIntFits(EInteger bigint) {
      return bigint.GetSignedBitLength() <= 64;
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

    private static int StringHashCode(String str) {
      if (str == null) {
        return 0;
      }
      int ret = 19;
      int count = str.length();
      {
        ret = (ret * 31) + count;
        for (int i = 0; i < count; ++i) {
          ret = (ret * 31) + (int)str.charAt(i);
        }
      }
      return ret;
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
        CBORObject valueB = null;
        boolean hasKey;
valueB = mapB.get(kvp.getKey());
hasKey=(valueB == null) ? mapB.containsKey(kvp.getKey()) : true;
        if (hasKey) {
          CBORObject valueA = kvp.getValue();
          if (!(valueA == null ? valueB == null : valueA.equals(valueB))) {
            return false;
          }
        } else {
          return false;
        }
      }
      return true;
    }

    private static int CBORMapHashCode(Map<CBORObject, CBORObject> a) {
      // To simplify matters, we use just the count of
      // the map as the basis for the hash code. More complicated
      // hash code calculation would generally involve defining
      // how CBORObjects ought to be compared (since a stable
      // sort order is necessary for two equal maps to have the
      // same hash code), which is much too difficult to do.
      return (a.size() * 19);
    }

    private static void CheckCBORLength(
      long expectedLength,
      long actualLength) {
      if (actualLength < expectedLength) {
        throw new CBORException("Premature end of data");
      }
      if (actualLength > expectedLength) {
        throw new CBORException("Too many bytes");
      }
    }

    private static void CheckCBORLength(int expectedLength, int actualLength) {
      if (actualLength < expectedLength) {
        throw new CBORException("Premature end of data");
      }
      if (actualLength > expectedLength) {
        throw new CBORException("Too many bytes");
      }
    }

    private static CBORObject ConvertWithConverter(Object obj) {
      Object type = obj.getClass();
      ConverterInfo convinfo = null;
      synchronized (ValueConverters) {
        if (ValueConverters.size() == 0) {
          CBORTag0.AddConverter();
          CBORTag37.AddConverter();
          CBORTag32.AddConverter();
        }
        if (ValueConverters.containsKey(type)) {
          convinfo = ValueConverters.get(type);
        } else {
          return null;
        }
      }
      if (convinfo == null) {
        return null;
      }
      return (CBORObject)PropertyMap.InvokeOneArgumentMethod(
        convinfo.getToObject(),
        convinfo.getConverter(),
        obj);
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

    private static ICBORTag FindTagConverter(int tag) {
      return FindTagConverter(EInteger.FromInt32(tag));
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
          bytes[offset] = (byte)0x78;
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
        int nextbyte = (int)(data[offset] & (int)0xff);
        if (nextbyte >= 0x60 && nextbyte < 0x78) {
          int offsetp1 = 1 + offset;
          // Check for type 3 String of short length
          int rightLength = offsetp1 + (nextbyte - 0x60);
          CheckCBORLength(rightLength, length);
          // Check for all ASCII text
          for (int i = offsetp1; i < length; ++i) {
            if ((data[i] & ((byte)0x80)) != 0) {
              return null;
            }
          }
          // All ASCII text, so convert to a String
          // from a char array without having to
          // convert from UTF-8 first
          char[] c = new char[length - offsetp1];
          for (int i = offsetp1; i < length; ++i) {
            c[i - offsetp1] = (char)(data[i] & (int)0xff);
          }
          return new String(c);
        }
      }
      return null;
    }

    private static byte[] GetPositiveInt64Bytes(int type, long value) {
      if (value < 0) {
        throw new IllegalArgumentException("value (" + value + ") is less than " +
                    "0");
      }
      if (value < 24) {
        return new byte[] { (byte)((byte)value | (byte)(type << 5))  };
      }
      if (value <= 0xffL) {
        return new byte[] { (byte)(24 | (type << 5)), (byte)(value & 0xff)
         };
      }
      if (value <= 0xffffL) {
        return new byte[] { (byte)(25 | (type << 5)),
          (byte)((value >> 8) & 0xff), (byte)(value & 0xff)
         };
      }
      if (value <= 0xffffffffL) {
        return new byte[] { (byte)(26 | (type << 5)),
          (byte)((value >> 24) & 0xff), (byte)((value >> 16) & 0xff),
          (byte)((value >> 8) & 0xff), (byte)(value & 0xff)
         };
      }
      return new byte[] { (byte)(27 | (type << 5)), (byte)((value >> 56) & 0xff),
        (byte)((value >> 48) & 0xff), (byte)((value >> 40) & 0xff),
        (byte)((value >> 32) & 0xff), (byte)((value >> 24) & 0xff),
        (byte)((value >> 16) & 0xff), (byte)((value >> 8) & 0xff),
        (byte)(value & 0xff)  };
    }

    private static byte[] GetPositiveIntBytes(int type, int value) {
      if (value < 0) {
        throw new IllegalArgumentException("value (" + value + ") is less than " +
                    "0");
      }
      if (value < 24) {
        return new byte[] { (byte)((byte)value | (byte)(type << 5))  };
      }
      if (value <= 0xff) {
        return new byte[] { (byte)(24 | (type << 5)), (byte)(value & 0xff)
         };
      }
      if (value <= 0xffff) {
        return new byte[] { (byte)(25 | (type << 5)),
          (byte)((value >> 8) & 0xff), (byte)(value & 0xff)
         };
      }
      return new byte[] { (byte)(26 | (type << 5)), (byte)((value >> 24) & 0xff),
        (byte)((value >> 16) & 0xff), (byte)((value >> 8) & 0xff),
        (byte)(value & 0xff)  };
    }

    private static int GetSignInternal(int type, Object obj) {
      ICBORNumber cn = NumberInterfaces[type];
      return cn == null ? 2 : cn.Sign(obj);
    }
    // Initialize fixed values for certain
    // head bytes
    private static CBORObject[] InitializeFixedObjects() {
      valueFixedObjects = new CBORObject[256];
      for (int i = 0; i < 0x18; ++i) {
        valueFixedObjects[i] = new CBORObject(CBORObjectTypeInteger, (long)i);
      }
      for (int i = 0x20; i < 0x38; ++i) {
        valueFixedObjects[i] = new CBORObject(
          CBORObjectTypeInteger,
          (long)(-1 - (i - 0x20)));
      }
      valueFixedObjects[0x60] = new CBORObject(
        CBORObjectTypeTextString,
        "");
      for (int i = 0xe0; i < 0xf8; ++i) {
        valueFixedObjects[i] = new CBORObject(
          CBORObjectTypeSimpleValue,
          (int)(i - 0xe0));
      }
      return valueFixedObjects;
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
      int c = Math.min(listACount, listBCount);
      for (int i = 0; i < c; ++i) {
        int cmp = listA.get(i).compareTo(listB.get(i));
        if (cmp != 0) {
          return cmp;
        }
      }
      return (listACount != listBCount) ? ((listACount < listBCount) ? -1 : 1) :
        0;
    }

    private static EInteger LowHighToEInteger(int tagLow, int tagHigh) {
      byte[] uabytes = null;
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
      ArrayList<CBORObject> sortedASet = new ArrayList<CBORObject>(mapA.keySet());
      ArrayList<CBORObject> sortedBSet = new ArrayList<CBORObject>(mapB.keySet());
      java.util.Collections.sort(sortedASet);
      java.util.Collections.sort(sortedBSet);
      listACount = sortedASet.size();
      listBCount = sortedBSet.size();
      int minCount = Math.min(listACount, listBCount);
      // Compare the keys
      for (int i = 0; i < minCount; ++i) {
        CBORObject itemA = sortedASet.get(i);
        CBORObject itemB = sortedBSet.get(i);
        if (itemA == null) {
          return -1;
        }
        int cmp = itemA.compareTo(itemB);
        if (cmp != 0) {
          return cmp;
        }
      }
      if (listACount == listBCount) {
        // Both maps have the same keys, so compare their values
        for (int i = 0; i < minCount; ++i) {
          CBORObject keyA = sortedASet.get(i);
          CBORObject keyB = sortedBSet.get(i);
          int cmp = mapA.get(keyA).compareTo(mapB.get(keyB));
          if (cmp != 0) {
            return cmp;
          }
        }
        return 0;
      }
      return (listACount > listBCount) ? 1 : -1;
    }

    private static List<Object> PushObject(
      List<Object> stack,
      Object parent,
      Object child) {
      if (stack == null) {
        stack = new ArrayList<Object>();
        stack.add(parent);
      }
      for (Object o : stack) {
        if (o == child) {
          throw new IllegalArgumentException("Circular reference in data structure");
        }
      }
      stack.add(child);
      return stack;
    }

    private static boolean TagHandlersEmpty() {
      synchronized (ValueTagHandlers) {
        return ValueTagHandlers.size() == 0;
      }
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
          child.WriteTo(outputStream);
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

    private static void WritePositiveInt(int type, int value, OutputStream s) throws java.io.IOException {
      byte[] bytes = GetPositiveIntBytes(type, value);
      s.write(bytes, 0, bytes.length);
    }

    private static void WritePositiveInt64(int type, long value, OutputStream s) throws java.io.IOException {
      byte[] bytes = GetPositiveInt64Bytes(type, value);
      s.write(bytes, 0, bytes.length);
    }

    private static void WriteStreamedString(String str, OutputStream stream) throws java.io.IOException {
      byte[] bytes;
      bytes = GetOptimizedBytesIfShortAscii(str, -1);
      if (bytes != null) {
        stream.write(bytes, 0, bytes.length);
        return;
      }
      bytes = new byte[StreamedStringBufferLength];
      int byteIndex = 0;
      boolean streaming = false;
      for (int index = 0; index < str.length(); ++index) {
        int c = str.charAt(index);
        if (c <= 0x7f) {
          if (byteIndex >= StreamedStringBufferLength) {
            // Write bytes retrieved so far
            if (!streaming) {
              stream.write((byte)0x7f);
            }
            WritePositiveInt(3, byteIndex, stream);
            stream.write(bytes, 0, byteIndex);
            byteIndex = 0;
            streaming = true;
          }
          bytes[byteIndex++] = (byte)c;
        } else if (c <= 0x7ff) {
          if (byteIndex + 2 > StreamedStringBufferLength) {
            // Write bytes retrieved so far - the next three bytes
            // would exceed the length, and the CBOR spec forbids
            // splitting characters when generating text strings
            if (!streaming) {
              stream.write((byte)0x7f);
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
              str.charAt(index + 1) >= 0xdc00 && str.charAt(index + 1) <= 0xdfff) {
            // Get the Unicode code point for the surrogate pair
            c = 0x10000 + ((c - 0xd800) << 10) + (str.charAt(index + 1) - 0xdc00);
            ++index;
          } else if ((c & 0xf800) == 0xd800) {
            // unpaired surrogate, write U + FFFD instead
            c = 0xfffd;
          }
          if (c <= 0xffff) {
            if (byteIndex + 3 > StreamedStringBufferLength) {
              // Write bytes retrieved so far - the next three bytes
              // would exceed the length, and the CBOR spec forbids
              // splitting characters when generating text strings
              if (!streaming) {
                stream.write((byte)0x7f);
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
                stream.write((byte)0x7f);
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
        stream.write((byte)0xff);
      }
    }

    //-----------------------------------------------------------
    private void AppendClosingTags(StringBuilder sb) {
      CBORObject curobject = this;
      while (curobject.isTagged()) {
        sb.append(')');
        curobject = (CBORObject)curobject.itemValue;
      }
    }

    private void AppendOpeningTags(StringBuilder sb) {
      CBORObject curobject = this;
      while (curobject.isTagged()) {
        int low = curobject.tagLow;
        int high = curobject.tagHigh;
        if (high == 0 && (low >> 16) == 0) {
          sb.append(CBORUtilities.LongToString(low));
        } else {
          EInteger bi = LowHighToEInteger(low, high);
          sb.append(CBORUtilities.BigIntToString(bi));
        }
        sb.append('(');
        curobject = (CBORObject)curobject.itemValue;
      }
    }

    private int AsInt32(int minValue, int maxValue) {
      ICBORNumber cn = NumberInterfaces[this.getItemType()];
      if (cn == null) {
        throw new IllegalStateException("not a number type");
      }
      return cn.AsInt32(this.getThisItem(), minValue, maxValue);
    }

    private void WriteTags(OutputStream s) throws java.io.IOException {
      CBORObject curobject = this;
      while (curobject.isTagged()) {
        int low = curobject.tagLow;
        int high = curobject.tagHigh;
        if (high == 0 && (low >> 16) == 0) {
          WritePositiveInt(6, low, s);
        } else if (high == 0) {
          long value = ((long)low) & 0xffffffffL;
          WritePositiveInt64(6, value, s);
        } else if ((high >> 16) == 0) {
          long value = ((long)low) & 0xffffffffL;
          long highValue = ((long)high) & 0xffffffffL;
          value |= highValue << 32;
          WritePositiveInt64(6, value, s);
        } else {
          byte[] arrayToWrite = {  (byte)0xdb,
            (byte)((high >> 24) & 0xff), (byte)((high >> 16) & 0xff),
            (byte)((high >> 8) & 0xff), (byte)(high & 0xff),
            (byte)((low >> 24) & 0xff), (byte)((low >> 16) & 0xff),
            (byte)((low >> 8) & 0xff), (byte)(low & 0xff)  };
          s.write(arrayToWrite, 0, 9);
        }
        curobject = (CBORObject)curobject.itemValue;
      }
    }

    private static final class ConverterInfo {
      private Object toObject;

    /**
     * Gets the converter's ToCBORObject method.
     * @return The converter's ToCBORObject method.
     */
      public final Object getToObject() {
          return this.toObject;
        }
public final void setToObject(Object value) {
          this.toObject = value;
        }

      private Object converter;

    /**
     * Gets the ICBORConverter object.
     * @return The ICBORConverter object.
     */
      public final Object getConverter() {
          return this.converter;
        }
public final void setConverter(Object value) {
          this.converter = value;
        }
    }
  }
