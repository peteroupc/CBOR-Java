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

// TODO: In next major version, make .Keys and .Values read-only
// TODO: Add ReadObject that combines Read and ToObject; similarly
// for ReadJSON, FromJSONString, FromJSONBytes
// TODO: In Java version add overloads for Class<T> in overloads
// that take java.lang.reflect.getType()

  /**
   * <p>Represents an object in Concise Binary Object Representation (CBOR) and
   * contains methods for reading and writing CBOR data. CBOR is an
   * Internet Standard and defined in RFC 8949.</p><p> <p><b>Converting
   * CBOR objects</b></p> <p>There are many ways to get a CBOR object,
   * including from bytes, objects, streams and JSON, as described
   * below.</p> <p><b>To and from byte arrays:</b> The
   * CBORObject.DecodeFromBytes method converts a byte array in CBOR format
   * to a CBOR object. The EncodeToBytes method converts a CBOR object to
   * its corresponding byte array in CBOR format.</p> <p><b>To and from
   * data streams:</b> The CBORObject.Write methods write many kinds of
   * objects to a data stream, including numbers, CBOR objects, strings,
   * and arrays of numbers and strings. The CBORObject.Read method reads a
   * CBOR object from a data stream.</p> <p><b>To and from other
   * objects:</b> The <code>CBORObject.FromObject</code> method converts many
   * kinds of objects to a CBOR object, including numbers, strings, and
   * arrays and maps of numbers and strings. Methods like AsNumber and
   * AsString convert a CBOR object to different types of object. The
   * <code>CBORObject.ToObject</code> method converts a CBOR object to an object
   * of a given type; for example, a CBOR array to a native
   * <code>ArrayList</code> (or <code>ArrayList</code> in Java), or a CBOR integer to
   * an <code>int</code> or <code>long</code>.</p> <p><b>To and from JSON:</b> This
   * class also doubles as a reader and writer of JavaScript object
   * Notation (JSON). The CBORObject.FromJSONString method converts JSON in
   * text string form to a CBOR object, and the ToJSONString method
   * converts a CBOR object to a JSON string. (Note that the conversion
   * from CBOR to JSON is not always without loss and may make it
   * impossible to recover the original object when converting the JSON
   * back to CBOR. See the ToJSONString documentation.) Likewise,
   * ToJSONBytes and FromJSONBytes work with JSON in the form of byte
   * arrays rather than text strings.</p> <p>In addition, the
   * CBORObject.WriteJSON method writes many kinds of objects as JSON to a
   * data stream, including numbers, CBOR objects, strings, and arrays of
   * numbers and strings. The CBORObject.Read method reads a CBOR object
   * from a JSON data stream.</p> <p><b>Comparison Considerations:</b></p>
   * <p>Instances of CBORObject should not be compared for equality using
   *  the "==" operator; it's possible to create two CBOR objects with the
   *  same value but not the same reference. (The "==" operator might only
   * check if each side of the operator is the same instance.)</p> <p>This
   * class's natural ordering (under the compareTo method) is consistent
   * with the Equals method, meaning that two values that compare as equal
   * under the compareTo method are also equal under the Equals method;
   * this is a change in version 4.0. Two otherwise equal objects with
   * different tags are not treated as equal by both compareTo and Equals.
   * To strip the tags from a CBOR object before comparing, use the
   * <code>Untag</code> method.</p> <p><b>Thread Safety:</b></p> <p>Certain CBOR
   * objects are immutable (their values can't be changed), so they are
   * inherently safe for use by multiple threads.</p> <p>CBOR objects that
   * are arrays, maps, and byte strings (whether or not they are tagged)
   * are mutable, but this class doesn't attempt to synchronize reads and
   * writes to those objects by multiple threads, so those objects are not
   * thread safe without such synchronization.</p> <p>One kind of CBOR
   * object is called a map, or a list of key-value pairs. Keys can be any
   * kind of CBOR object, including numbers, strings, arrays, and maps.
   * However, untagged text strings (which means GetTags returns an empty
   *  array and the Type property, or "getType()" in Java, returns
   * TextString) are the most suitable to use as keys; other kinds of CBOR
   * object are much better used as map values instead, keeping in mind
   * that some of them are not thread safe without synchronizing reads and
   * writes to them.</p> <p>To find the type of a CBOR object, call its
   *  Type property (or "getType()" in Java). The return value can be
   * Integer, FloatingPoint, Boolean, SimpleValue, or TextString for
   * immutable CBOR objects, and Array, Map, or ByteString for mutable CBOR
   * objects.</p> <p><b>Nesting Depth:</b></p> <p>The DecodeFromBytes and
   * Read methods can only read objects with a limited maximum depth of
   * arrays and maps nested within other arrays and maps. The code sets
   * this maximum depth to 500 (allowing more than enough nesting for most
   * purposes), but it's possible that stack overflows in some runtimes
   * might lower the effective maximum nesting depth. When the nesting
   * depth goes above 500, the DecodeFromBytes and Read methods throw a
   * CBORException.</p> <p>The ReadJSON and FromJSONString methods
   * currently have nesting depths of 1000.</p></p>
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

    /**
     * Gets the number of keys in this map, or the number of items in this array,
     * or 0 if this item is neither an array nor a map.
     * @return The number of keys in this map, or the number of items in this
     * array, or 0 if this item is neither an array nor a map.
     */
    public final int size() {
        return (this.getType() == CBORType.Array) ? this.AsList().size() :
          ((this.getType() == CBORType.Map) ? this.AsMap().size() : 0);
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
     * Gets a value indicating whether this value is a CBOR false value, whether
     * tagged or not.
     * @return {@code true} if this value is a CBOR false value; otherwise, {@code
     * false}.
     */
    public final boolean isFalse() {
        return this.getItemType() == CBORObjectTypeSimpleValue && ((Integer)this.getThisItem()).intValue()
          == 20;
      }

    /**
     * Gets a value indicating whether this CBOR object represents a finite number.
     * @return {@code true} if this CBOR object represents a finite number;
     * otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().IsFinite()).
 */
@Deprecated
    public final boolean isFinite() {
        if (this.isNumber()) {
          CBORNumber cn = this.AsNumber();
          return !cn.IsInfinity() && !cn.IsNaN();
        } else {
          return false;
        }
      }

    /**
     * Gets a value indicating whether this object represents an integer number,
     * that is, a number without a fractional part. Infinity and
     * not-a-number are not considered integers.
     * @return {@code true} if this object represents an integer number, that is, a
     * number without a fractional part; otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().IsInteger()).
 */
@Deprecated
    public final boolean isIntegral() {
        CBORNumber cn = CBORNumber.FromCBORObject(this);
        return (cn != null) &&
          cn.GetNumberInterface().IsIntegral(cn.GetValue());
      }

    /**
     * Gets a value indicating whether this CBOR object is a CBOR null value,
     * whether tagged or not.
     * @return {@code true} if this value is a CBOR null value; otherwise, {@code
     * false}.
     */
    public final boolean isNull() {
        return this.getItemType() == CBORObjectTypeSimpleValue && ((Integer)this.getThisItem()).intValue()
          == 22;
      }

    /**
     * Gets a value indicating whether this data item has at least one tag.
     * @return {@code true} if this data item has at least one tag; otherwise,
     * {@code false}.
     */
    public final boolean isTagged() {
        return this.itemtypeValue == CBORObjectTypeTagged;
      }

    /**
     * Gets a value indicating whether this value is a CBOR true value, whether
     * tagged or not.
     * @return {@code true} if this value is a CBOR true value; otherwise, {@code
     * false}.
     */
    public final boolean isTrue() {
        return this.getItemType() == CBORObjectTypeSimpleValue && ((Integer)this.getThisItem()).intValue()
          == 21;
      }

    /**
     * Gets a value indicating whether this value is a CBOR undefined value,
     * whether tagged or not.
     * @return {@code true} if this value is a CBOR undefined value; otherwise,
     * {@code false}.
     */
    public final boolean isUndefined() {
        return this.getItemType() == CBORObjectTypeSimpleValue && ((Integer)this.getThisItem()).intValue()
          == 23;
      }

    /**
     * Gets a value indicating whether this object's value equals 0.
     * @return {@code true} if this object's value equals 0; otherwise, {@code
     * false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().IsZero()).
 */
@Deprecated
    public final boolean isZero() {
        CBORNumber cn = CBORNumber.FromCBORObject(this);
        return cn != null &&
          cn.GetNumberInterface().IsNumberZero(cn.GetValue());
      }

    /**
     * Gets a collection of the keys of this CBOR object. In general, the order in
     * which those keys occur is undefined unless this is a map created
     * using the NewOrderedMap method.
     * @return A collection of the keys of this CBOR object. To avoid potential
     * problems, the calling code should not modify the CBOR map or the
     * returned collection while iterating over the returned collection.
     * @throws IllegalStateException This object is not a map.
     */
    public final Collection<CBORObject> getKeys() {
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> dict = this.AsMap();
          return dict.keySet();
        }
        throw new IllegalStateException("Not a map");
      }

    /**
     * Gets a value indicating whether this object is a negative number.
     * @return {@code true} if this object is a negative number; otherwise, {@code
     * false}.
     * @deprecated Instead, use \u0028cbor.IsNumber()
 * &amp;&amp;\u0020cbor.AsNumber().IsNegative()).
 */
@Deprecated
    public final boolean isNegative() {
        CBORNumber cn = CBORNumber.FromCBORObject(this);
        return (cn != null) &&
          cn.GetNumberInterface().IsNegative(cn.GetValue());
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
     * Gets this value's sign: -1 if negative; 1 if positive; 0 if zero. Throws an
     * exception if this is a not-a-number value.
     * @return This value's sign: -1 if negative; 1 if positive; 0 if zero.
     * @throws IllegalStateException This object does not represent a number, or
     * this object is a not-a-number (NaN) value.
     * @deprecated Instead, convert this object to a number with.AsNumber(), \u0020 and use the
 * Sign property in.NET or the signum method in\u0020Java. Either will
 * treat not-a-number (NaN) values differently than here.
 */
@Deprecated
    public final int signum() {
        CBORNumber cn = CBORNumber.FromCBORObject(this);
        if (cn == null || cn.IsNaN()) {
          throw new IllegalStateException(
            "This Object is not a number.");
        }
        return cn.GetNumberInterface().Sign(cn.GetValue());
      }

    /**
     * Gets the simple value ID of this CBOR object, or -1 if the object is not a
     * simple value. In this method, objects with a CBOR type of Boolean or
     * SimpleValue are simple values, whether they are tagged or not.
     * @return The simple value ID of this object if it's a simple value, or -1 if
     * this object is not a simple value.
     */
    public final int getSimpleValue() {
        return (this.getItemType() == CBORObjectTypeSimpleValue) ?
          (((Integer)this.getThisItem()).intValue()) : -1;
      }

    /**
     * Gets a value indicating whether this CBOR object stores a number (including
     * infinity or a not-a-number or NaN value). Currently, this is true if
     * this item is untagged and has a CBORType of Integer or
     * FloatingPoint, or if this item has only one tag and that tag is 2,
     * 3, 4, 5, 30, 264, 265, 268, 269, or 270 with the right data type.
     * @return A value indicating whether this CBOR object stores a number.
     */
    public final boolean isNumber() {
        return CBORNumber.IsNumber(this);
      }

    /**
     * Gets the general data type of this CBOR object. This method disregards the
     * tags this object has, if any.
     * @return The general data type of this CBOR object.
     */
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

    /**
     * Gets a collection of the key/value pairs stored in this CBOR object, if it's
     * a map. Returns one entry for each key/value pair in the map. In
     * general, the order in which those entries occur is undefined unless
     * this is a map created using the NewOrderedMap method.
     * @return A collection of the key/value pairs stored in this CBOR map, as a
     * read-only view of those pairs. To avoid potential problems, the
     * calling code should not modify the CBOR map while iterating over the
     * returned collection.
     * @throws IllegalStateException This object is not a map.
     */
    public final Collection<Map.Entry<CBORObject, CBORObject>> getEntries() {
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> dict = this.AsMap();
          return PropertyMap.GetEntries(dict);
        }
        throw new IllegalStateException("Not a map");
      }

    /**
     * Gets a collection of the values of this CBOR object, if it's a map or an
     * array. If this object is a map, returns one value for each key in
     * the map; in general, the order in which those keys occur is
     * undefined unless this is a map created using the NewOrderedMap
     * method. If this is an array, returns all the values of the array in
     * the order they are listed. (This method can't be used to get the
     * bytes in a CBOR byte string; for that, use the GetByteString method
     * instead.).
     * @return A collection of the values of this CBOR map or array. To avoid
     * potential problems, the calling code should not modify the CBOR map
     * or array or the returned collection while iterating over the
     * returned collection.
     * @throws IllegalStateException This object is not a map or an array.
     */
    public final Collection<CBORObject> getValues() {
        if (this.getType() == CBORType.Map) {
          Map<CBORObject, CBORObject> dict = this.AsMap();
          return dict.values();
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

    /**
     * Gets the value of a CBOR object by integer index in this array or by integer
     * key in this map.
     * @param index Index starting at 0 of the element, or the integer key to this
     * map. (If this is a map, the given index can be any 32-bit signed
     * integer, even a negative one.).
     * @return The CBOR object referred to by index or key in this array or map. If
     * this is a CBOR map, returns {@code null} (not {@code
     * CBORObject.Null}) if an item with the given key doesn't exist (but
     * this behavior may change to throwing an exception in version 5.0 or
     * later).
     * @throws IllegalStateException This object is not an array or map.
     * @throws IllegalArgumentException This object is an array and the index is less than
     * 0 or at least the size of the array.
     * @throws NullPointerException The parameter "value" is null (as opposed to
     * CBORObject.Null).
     */
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
          return (!map.containsKey(key)) ? null : map.get(key);
        }
        throw new IllegalStateException("Not an array or map");
      }

    /**
     * Sets the value of a CBOR object by integer index in this array or by integer
     * key in this map.
     * @param index Index starting at 0 of the element, or the integer key to this
     * map. (If this is a map, the given index can be any 32-bit signed
     * integer, even a negative one.).
     * @throws IllegalStateException This object is not an array or map.
     * @throws IllegalArgumentException This object is an array and the index is less than
     * 0 or at least the size of the array.
     * @throws NullPointerException The parameter "value" is null (as opposed to
     * CBORObject.Null).
     */
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

    /**
     * Gets the value of a CBOR object by integer index in this array or by CBOR
     * object key in this map, or a default value if that value is not
     * found.
     * @param key An arbitrary object. If this is a CBOR map, this parameter is
     * converted to a CBOR object serving as the key to the map or index to
     * the array, and can be null. If this is a CBOR array, the key must be
     * an integer 0 or greater and less than the size of the array, and may
     * be any object convertible to a CBOR integer.
     * @param defaultValue A value to return if an item with the given key doesn't
     * exist, or if the CBOR object is an array and the key is not an
     * integer 0 or greater and less than the size of the array.
     * @return The CBOR object referred to by index or key in this array or map. If
     * this is a CBOR map, returns {@code null} (not {@code
     * CBORObject.Null}) if an item with the given key doesn't exist.
     */
    public CBORObject GetOrDefault(Object key, CBORObject defaultValue) {
      if (this.getType() == CBORType.Array) {
        int index = 0;
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

    /**
     * Gets the value of a CBOR object by integer index in this array or by CBOR
     * object key in this map.
     * @param key A CBOR object serving as the key to the map or index to the
     * array. If this is a CBOR array, the key must be an integer 0 or
     * greater and less than the size of the array.
     * @return The CBOR object referred to by index or key in this array or map. If
     * this is a CBOR map, returns {@code null} (not {@code
     * CBORObject.Null}) if an item with the given key doesn't exist.
     * @throws NullPointerException The key is null (as opposed to
     * CBORObject.Null); or the set method is called and the value is null.
     * @throws IllegalArgumentException This CBOR object is an array and the key is not an
     * integer 0 or greater and less than the size of the array.
     * @throws IllegalStateException This object is not a map or an array.
     */

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
          return (!map.containsKey(key)) ? null : map.get(key);
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

    /**
     * Sets the value of a CBOR object by integer index in this array or by CBOR
     * object key in this map.
     * @param key A CBOR object serving as the key to the map or index to the
     * array. If this is a CBOR array, the key must be an integer 0 or
     * greater and less than the size of the array.
     * @throws NullPointerException The key is null (as opposed to
     * CBORObject.Null); or the set method is called and the value is null.
     * @throws IllegalArgumentException This CBOR object is an array and the key is not an
     * integer 0 or greater and less than the size of the array.
     * @throws IllegalStateException This object is not a map or an array.
     */

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

    /**
     * Gets the value of a CBOR object in this map, using a string as the key.
     * @param key A key that points to the desired value.
     * @return The CBOR object referred to by key in this map. Returns {@code null}
     * if an item with the given key doesn't exist.
     * @throws NullPointerException The key is null.
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
     * Sets the value of a CBOR object in this map, using a string as the key.
     * @param key A key that points to the desired value.
     * @throws NullPointerException The key is null.
     * @throws IllegalStateException This object is not a map.
     */
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

    /**
     * Finds the sum of two CBOR numbers.
     * @param first The parameter {@code first} is a CBOR object.
     * @param second The parameter {@code second} is a CBOR object.
     * @return A CBOR object.
     * @throws IllegalArgumentException Either or both operands are not numbers (as
     * opposed to Not-a-Number, NaN).
     * @throws NullPointerException The parameter {@code first} or {@code second}
     * is null.
     * @deprecated Instead, convert both CBOR objects to numbers (with\u0020.AsNumber()), and
 * use the first number's.Add() method.
 */
@Deprecated
    public static CBORObject Addition(CBORObject first, CBORObject second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      CBORNumber numberA = CBORNumber.FromCBORObject(first);
      if (numberA == null) {
        throw new IllegalArgumentException("first" + "does not represent a" +
          "\u0020number");
      }
      CBORNumber b = CBORNumber.FromCBORObject(second);
      if (b == null) {
        throw new IllegalArgumentException("second" + "does not represent a" +
          "\u0020number");
      }
      return numberA.Add(b).ToCBORObject();
    }

    /**
     * <p>Generates a CBOR object from an array of CBOR-encoded bytes.</p>
     * @param data A byte array in which a single CBOR object is encoded.
     * @return A CBOR object decoded from the given byte array.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where not all of the byte
     * array represents a CBOR object. This exception is also thrown if the
     * parameter {@code data} is empty.
     * @throws NullPointerException The parameter {@code data} is null.
     */
    public static CBORObject DecodeFromBytes(byte[] data) {
      return DecodeFromBytes(data, CBOREncodeOptions.Default);
    }

    private static final CBOREncodeOptions AllowEmptyOptions =
      new CBOREncodeOptions("allowempty=1");

    /**
     * <p>Generates a sequence of CBOR objects from an array of CBOR-encoded
     * bytes.</p>
     * @param data A byte array in which any number of CBOR objects (including
     * zero) are encoded, one after the other. Can be empty, but cannot be
     * null.
     * @return An array of CBOR objects decoded from the given byte array. Returns
     * an empty array if {@code data} is empty.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where the last CBOR object in
     * the data was read only partly.
     * @throws NullPointerException The parameter {@code data} is null.
     */
    public static CBORObject[] DecodeSequenceFromBytes(byte[] data) {
      return DecodeSequenceFromBytes(data, AllowEmptyOptions);
    }

    /**
     * <p>Generates a sequence of CBOR objects from an array of CBOR-encoded
     * bytes.</p>
     * @param data A byte array in which any number of CBOR objects (including
     * zero) are encoded, one after the other. Can be empty, but cannot be
     * null.
     * @param options Specifies options to control how the CBOR object is decoded.
     * See {@link com.upokecenter.cbor.CBOREncodeOptions} for more
     * information. In this method, the AllowEmpty property is treated as
     * always set regardless of that value as specified in this parameter.
     * @return An array of CBOR objects decoded from the given byte array. Returns
     * an empty array if {@code data} is empty.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where the last CBOR object in
     * the data was read only partly.
     * @throws NullPointerException The parameter {@code data} is null, or the
     * parameter {@code options} is null.
     */
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

    /**
     * Generates a list of CBOR objects from an array of bytes in JavaScript object
     * Notation (JSON) text sequence format (RFC 7464). The byte array must
     * be in UTF-8 encoding and may not begin with a byte-order mark
     * (U+FEFF).<p>Generally, each JSON text in a JSON text sequence is
     * written as follows: Write a record separator byte (0x1e), then write
     * the JSON text in UTF-8 (without a byte order mark, U+FEFF), then
     * write the line feed byte (0x0a). RFC 7464, however, uses a more
     * liberal syntax for parsing JSON text sequences.</p>
     * @param bytes A byte array in which a JSON text sequence is encoded.
     * @return A list of CBOR objects read from the JSON sequence. Objects that
     * could not be parsed are replaced with {@code null} (as opposed to
     * {@code CBORObject.Null}) in the given list.
     * @throws NullPointerException The parameter {@code bytes} is null.
     * @throws com.upokecenter.cbor.CBORException The byte array is not empty and
     * does not begin with a record separator byte (0x1e), or an I/O error
     * occurred.
     */
    public static CBORObject[] FromJSONSequenceBytes(byte[] bytes) {
      return FromJSONSequenceBytes(bytes, JSONOptions.Default);
    }

    /**
     * Converts this object to a byte array in JavaScript object Notation (JSON)
     * format. The JSON text will be written out in UTF-8 encoding, without
     * a byte order mark, to the byte array. See the overload to
     * ToJSONString taking a JSONOptions argument for further information.
     * @return A byte array containing the converted in JSON format.
     */
    public byte[] ToJSONBytes() {
      return this.ToJSONBytes(JSONOptions.Default);
    }

    /**
     * Converts this object to a byte array in JavaScript object Notation (JSON)
     * format. The JSON text will be written out in UTF-8 encoding, without
     * a byte order mark, to the byte array. See the overload to
     * ToJSONString taking a JSONOptions argument for further information.
     * @param jsonoptions Specifies options to control writing the CBOR object to
     * JSON.
     * @return A byte array containing the converted object in JSON format.
     * @throws NullPointerException The parameter {@code jsonoptions} is null.
     */
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

    /**
     * Generates a list of CBOR objects from an array of bytes in JavaScript object
     * Notation (JSON) text sequence format (RFC 7464), using the specified
     * options to control the decoding process. The byte array must be in
     * UTF-8 encoding and may not begin with a byte-order mark
     * (U+FEFF).<p>Generally, each JSON text in a JSON text sequence is
     * written as follows: Write a record separator byte (0x1e), then write
     * the JSON text in UTF-8 (without a byte order mark, U+FEFF), then
     * write the line feed byte (0x0a). RFC 7464, however, uses a more
     * liberal syntax for parsing JSON text sequences.</p>
     * @param data A byte array in which a JSON text sequence is encoded.
     * @param options Specifies options to control the JSON decoding process.
     * @return A list of CBOR objects read from the JSON sequence. Objects that
     * could not be parsed are replaced with {@code null} (as opposed to
     * {@code CBORObject.Null}) in the given list.
     * @throws NullPointerException The parameter {@code data} is null.
     * @throws com.upokecenter.cbor.CBORException The byte array is not empty and
     * does not begin with a record separator byte (0x1e), or an I/O error
     * occurred.
     */
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

    /**
     * Generates a CBOR object from an array of CBOR-encoded bytes, using the given
     * <code>CBOREncodeOptions</code> object to control the decoding process.<p>
     * <p>The following example (originally written in C# for the.NET
     * version) implements a method that decodes a text string from a CBOR
     * byte array. It's successful only if the CBOR object contains an
     *  untagged text string.</p> <pre>private static string DecodeTextString(byte[] bytes) { if (bytes == null) { throw new NullPointerException("mapObj");} if (bytes.length == 0 || bytes[0]&lt;0x60 || bytes[0]&gt;0x7f) {throw new CBORException();} return CBORObject.DecodeFromBytes(bytes, CBOREncodeOptions.Default).AsString(); }</pre>. </p>
     * @param data A byte array in which a single CBOR object is encoded.
     * @param options Specifies options to control how the CBOR object is decoded.
     * See {@link com.upokecenter.cbor.CBOREncodeOptions} for more
     * information.
     * @return A CBOR object decoded from the given byte array. Returns null (as
     * opposed to CBORObject.Null) if {@code data} is empty and the
     * AllowEmpty property is set on the given options object.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where not all of the byte
     * array represents a CBOR object. This exception is also thrown if the
     * parameter {@code data} is empty unless the AllowEmpty property is
     * set on the given options object.
     * @throws NullPointerException The parameter {@code data} is null, or the
     * parameter {@code options} is null.
     */
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
        if (options.getAllowEmpty()) {
          return null;
        }
        throw new CBORException("data is empty.");
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
          (long)data.length,
          (long)(startingAvailable - ms.available()));
        return o;
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
    }

    /**
     * Divides a CBORObject object by the value of a CBORObject object.
     * @param first The parameter {@code first} is a CBOR object.
     * @param second The parameter {@code second} is a CBOR object.
     * @return The quotient of the two objects.
     * @throws NullPointerException The parameter {@code first} or {@code second}
     * is null.
     * @deprecated Instead, convert both CBOR objects to numbers (with\u0020.AsNumber()), and
 * use the first number's.Divide() method.
 */
@Deprecated
    public static CBORObject Divide(CBORObject first, CBORObject second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      CBORNumber a = CBORNumber.FromCBORObject(first);
      if (a == null) {
        throw new IllegalArgumentException("first" + "does not represent a" +
          "\u0020number");
      }
      CBORNumber b = CBORNumber.FromCBORObject(second);
      if (b == null) {
        throw new IllegalArgumentException("second" + "does not represent a" +
          "\u0020number");
      }
      return a.Divide(b).ToCBORObject();
    }

    /**
     * <p>Generates a CBOR object from a text string in JavaScript object Notation
     * (JSON) format.</p> <p>If a JSON object has duplicate keys, a
     * CBORException is thrown. This is a change in version 4.0.</p>
     * <p>Note that if a CBOR object is converted to JSON with
     * <code>ToJSONString</code>, then the JSON is converted back to CBOR with
     * this method, the new CBOR object will not necessarily be the same as
     * the old CBOR object, especially if the old CBOR object uses data
     * types not supported in JSON, such as integers in map keys.</p>
     * @param str A text string in JSON format. The entire string must contain a
     * single JSON object and not multiple objects. The string may not
     * begin with a byte-order mark (U+FEFF).
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code str} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @return A CBOR object.
     * @throws NullPointerException The parameter {@code str} is null.
     * @throws com.upokecenter.cbor.CBORException The string is not in JSON format.
     * @throws IllegalArgumentException Either {@code offset} or {@code count} is less
     * than 0 or greater than {@code str} 's length, or {@code str} 's
     * length minus {@code offset} is less than {@code count}.
     */
    public static CBORObject FromJSONString(String str, int offset, int count) {
      if (str == null) {
        throw new NullPointerException("str");
      }
      return FromJSONString(str, offset, count, JSONOptions.Default);
    }

    /**
     * Generates a CBOR object from a text string in JavaScript object Notation
     * (JSON) format, using the specified options to control the decoding
     * process. <p>Note that if a CBOR object is converted to JSON with
     * <code>ToJSONString</code>, then the JSON is converted back to CBOR with
     * this method, the new CBOR object will not necessarily be the same as
     * the old CBOR object, especially if the old CBOR object uses data
     * types not supported in JSON, such as integers in map keys.</p>
     * @param str A text string in JSON format. The entire string must contain a
     * single JSON object and not multiple objects. The string may not
     * begin with a byte-order mark (U+FEFF).
     * @param jsonoptions Specifies options to control the JSON decoding process.
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code str} or {@code
     * jsonoptions} is null.
     * @throws com.upokecenter.cbor.CBORException The string is not in JSON format.
     */
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

    /**
     * <p>Generates a CBOR object from a text string in JavaScript object Notation
     * (JSON) format.</p> <p>If a JSON object has duplicate keys, a
     * CBORException is thrown. This is a change in version 4.0.</p>
     * <p>Note that if a CBOR object is converted to JSON with
     * <code>ToJSONString</code>, then the JSON is converted back to CBOR with
     * this method, the new CBOR object will not necessarily be the same as
     * the old CBOR object, especially if the old CBOR object uses data
     * types not supported in JSON, such as integers in map keys.</p>
     * @param str A text string in JSON format. The entire string must contain a
     * single JSON object and not multiple objects. The string may not
     * begin with a byte-order mark (U+FEFF).
     * @return A CBOR object.
     * @throws NullPointerException The parameter {@code str} is null.
     * @throws com.upokecenter.cbor.CBORException The string is not in JSON format.
     */
    public static CBORObject FromJSONString(String str) {
      return FromJSONString(str, JSONOptions.Default);
    }

    /**
     * Generates a CBOR object from a text string in JavaScript object Notation
     * (JSON) format, using the specified options to control the decoding
     * process. <p>Note that if a CBOR object is converted to JSON with
     * <code>ToJSONString</code>, then the JSON is converted back to CBOR with
     * this method, the new CBOR object will not necessarily be the same as
     * the old CBOR object, especially if the old CBOR object uses data
     * types not supported in JSON, such as integers in map keys.</p>
     * @param str A text string in JSON format. The entire string must contain a
     * single JSON object and not multiple objects. The string may not
     * begin with a byte-order mark (U+FEFF).
     * @param options Specifies options to control the decoding process. This
     * method uses only the AllowDuplicateKeys property of this object.
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code str} or {@code options}
     * is null.
     * @throws com.upokecenter.cbor.CBORException The string is not in JSON format.
     * @deprecated Instead, use.getFromJSONString()\u0028str,
 * new\u0020JSONOptions\u0028\allowduplicatekeys = true\))
 * or\u0020.getFromJSONString()\u0028str, \u0020 new
 * JSONOptions\u0028\allowduplicatekeys = false\)), as\u0020appropriate.
 */
@Deprecated
    public static CBORObject FromJSONString(
      String str,
      CBOREncodeOptions options) {
      if (str == null) {
        throw new NullPointerException("str");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      JSONOptions jsonoptions = new JSONOptions(options.getAllowDuplicateKeys() ?
        "allowduplicatekeys=1" : "allowduplicatekeys=0");
      return FromJSONString(str, jsonoptions);
    }

    /**
     * Generates a CBOR object from a text string in JavaScript object Notation
     * (JSON) format, using the specified options to control the decoding
     * process. <p>Note that if a CBOR object is converted to JSON with
     * <code>ToJSONString</code>, then the JSON is converted back to CBOR with
     * this method, the new CBOR object will not necessarily be the same as
     * the old CBOR object, especially if the old CBOR object uses data
     * types not supported in JSON, such as integers in map keys.</p>
     * @param str The parameter {@code str} is a text string.
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code str} begins.
     * @param count The length, in code units, of the desired portion of {@code
     * str} (but not more than {@code str} 's length).
     * @param jsonoptions The parameter {@code jsonoptions} is a Cbor.JSONOptions
     * object.
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code str} or {@code
     * jsonoptions} is null.
     * @throws com.upokecenter.cbor.CBORException The string is not in JSON format.
     * @throws IllegalArgumentException Either {@code offset} or {@code count} is less
     * than 0 or greater than {@code str} 's length, or {@code str} 's
     * length minus {@code offset} is less than {@code count}.
     */
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

    /**
     * Converts this CBOR object to an object of an arbitrary type. See the
     * documentation for the overload of this method taking a
     * CBORTypeMapper parameter for more information. This method doesn't
     * use a CBORTypeMapper parameter to restrict which data types are
     * eligible for Plain-Old-Data serialization.<p> <p>Java offers no easy
     * way to express a generic type, at least none as easy as C#'s
     * <code>typeof</code> operator. The following example, written in Java, is a
     * way to specify that the return value will be an ArrayList of string
     * objects.</p> <pre>Type arrayListString = new ParameterizedType() { public Type[] getActualTypeArguments() { &#x2f;&#x2a; Contains one type parameter, string&#x2a;&#x2f; return new Type[] { string.class }; } public Type getRawType() { /* Raw type is ArrayList &#x2a;&#x2f; return ArrayList.class; } public Type getOwnerType() { return null; } }; ArrayList&lt;string&gt; array = (ArrayList&lt;string&gt;) cborArray.ToObject(arrayListString);</pre> <p>By comparison, the C#
     * version is much shorter.</p> <pre>List&lt;string&gt; array = (List&lt;string&gt;)cborArray.ToObject(typeof(List&lt;string&gt;));</pre>. </p>
     * @param t The type, class, or interface that this method's return value will
     * belong to. To express a generic type in Java, see the example.
     * <b>Note:</b> For security reasons, an application should not base
     * this parameter on user input or other externally supplied data.
     * Whenever possible, this parameter should be either a type specially
     * handled by this method (such as {@code int} or {@code string}) or a
     * plain-old-data type (POCO or POJO type) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.
     * @return The converted object.
     * @throws com.upokecenter.cbor.CBORException The given type {@code t} , or
     * this object's CBOR type, is not supported, or the given object's
     * nesting is too deep, or another error occurred when serializing the
     * object.
     * @throws NullPointerException The parameter {@code t} is null.
     */
    @SuppressWarnings("unchecked")
public <T> T ToObject(java.lang.reflect.Type t) {
      return (T)(this.ToObject(t, null, null, 0));
    }

    /**
     * Converts this CBOR object to an object of an arbitrary type. See the
     * documentation for the overload of this method taking a
     * CBORTypeMapper and PODOptions parameters parameters for more
     * information.
     * @param t The type, class, or interface that this method's return value will
     * belong to. To express a generic type in Java, see the example.
     * <b>Note:</b> For security reasons, an application should not base
     * this parameter on user input or other externally supplied data.
     * Whenever possible, this parameter should be either a type specially
     * handled by this method (such as {@code int} or {@code string}) or a
     * plain-old-data type (POCO or POJO type) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.
     * @param mapper This parameter controls which data types are eligible for
     * Plain-Old-Data deserialization and includes custom converters from
     * CBOR objects to certain data types.
     * @return The converted object.
     * @throws com.upokecenter.cbor.CBORException The given type {@code t}, or this
     * object's CBOR type, is not supported, or the given object's nesting
     * is too deep, or another error occurred when serializing the object.
     * @throws NullPointerException The parameter {@code t} is null.
     */
    @SuppressWarnings("unchecked")
public <T> T ToObject(java.lang.reflect.Type t, CBORTypeMapper mapper) {
      if (mapper == null) {
        throw new NullPointerException("mapper");
      }
      return (T)(this.ToObject(t, mapper, null, 0));
    }

    /**
     * Converts this CBOR object to an object of an arbitrary type. See the
     * documentation for the overload of this method taking a
     * CBORTypeMapper and PODOptions parameters for more information. This
     * method (without a CBORTypeMapper parameter) allows all data types
     * not otherwise handled to be eligible for Plain-Old-Data
     * serialization.
     * @param t The type, class, or interface that this method's return value will
     * belong to. To express a generic type in Java, see the example.
     * <b>Note:</b> For security reasons, an application should not base
     * this parameter on user input or other externally supplied data.
     * Whenever possible, this parameter should be either a type specially
     * handled by this method (such as {@code int} or {@code string}) or a
     * plain-old-data type (POCO or POJO type) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.
     * @param options Specifies options for controlling deserialization of CBOR
     * objects.
     * @return The converted object.
     * @throws UnsupportedOperationException The given type {@code t}, or this object's
     * CBOR type, is not supported.
     * @throws NullPointerException The parameter {@code t} is null.
     * @throws com.upokecenter.cbor.CBORException The given object's nesting is too
     * deep, or another error occurred when serializing the object.
     */
    @SuppressWarnings("unchecked")
public <T> T ToObject(java.lang.reflect.Type t, PODOptions options) {
      if (options == null) {
        throw new NullPointerException("options");
      }
      return (T)(this.ToObject(t, null, options, 0));
    }

    /**
     * <p>Converts this CBOR object to an object of an arbitrary type. The
     * following cases are checked in the logical order given (rather than
     * the strict order in which they are implemented by this library):</p>
     * <ul><li>If the type is <code>CBORObject</code> , return this object.</li>
     * <li>If the given object is <code>CBORObject.Null</code> (with or without
     * tags), returns <code>null</code> .</li> <li>If the object is of a type
     * corresponding to a type converter mentioned in the <paramref
     * name='mapper'/> parameter, that converter will be used to convert
     * the CBOR object to an object of the given type. Type converters can
     * be used to override the default conversion behavior of almost any
     * object.</li> <li>If the type is <code>object</code> , return this
     * object.</li> <li>If the type is <code>char</code> , converts
     * single-character CBOR text strings and CBOR integers from 0 through
     * 65535 to a <code>char</code> object and returns that <code>char</code>
     * object.</li> <li>If the type is <code>boolean</code> (<code>boolean</code> in
     * Java), returns the result of AsBoolean.</li> <li>If the type is
     * <code>short</code> , returns this number as a 16-bit signed integer after
     * converting its value to an integer by discarding its fractional
     * part, and throws an exception if this object's value is infinity or
     * a not-a-number value, or does not represent a number (currently
     * IllegalStateException, but may change in the next major version), or
     * if the value, once converted to an integer by discarding its
     * fractional part, is less than -32768 or greater than 32767
     * (currently ArithmeticException, but may change in the next major
     * version).</li> <li>If the type is <code>long</code> , returns this number
     * as a 64-bit signed integer after converting its value to an integer
     * by discarding its fractional part, and throws an exception if this
     * object's value is infinity or a not-a-number value, or does not
     * represent a number (currently IllegalStateException, but may change
     * in the next major version), or if the value, once converted to an
     * integer by discarding its fractional part, is less than -2^63 or
     * greater than 2^63-1 (currently ArithmeticException, but may change in
     * the next major version).</li> <li>If the type is <code>short</code> , the
     * same rules as for <code>long</code> are used, but the range is from -32768
     * through 32767 and the return type is <code>short</code> .</li> <li>If the
     * type is <code>byte</code> , the same rules as for <code>long</code> are used,
     * but the range is from 0 through 255 and the return type is
     * <code>byte</code> .</li> <li>If the type is <code>sbyte</code> , the same rules
     * as for <code>long</code> are used, but the range is from -128 through 127
     * and the return type is <code>sbyte</code> .</li> <li>If the type is
     * <code>ushort</code> , the same rules as for <code>long</code> are used, but the
     * range is from 0 through 65535 and the return type is <code>ushort</code>
     *.</li> <li>If the type is <code>uint</code> , the same rules as for
     * <code>long</code> are used, but the range is from 0 through 2^31-1 and the
     * return type is <code>uint</code> .</li> <li>If the type is <code>ulong</code> ,
     * the same rules as for <code>long</code> are used, but the range is from 0
     * through 2^63-1 and the return type is <code>ulong</code> .</li> <li>If the
     * type is <code>int</code> or a primitive floating-point type (<code>float</code>
     * , <code>double</code> , as well as <code>decimal</code> in.NET), returns the
     * result of the corresponding As* method.</li> <li>If the type is
     * <code>string</code> , returns the result of AsString.</li> <li>If the type
     * is <code>EFloat</code> , <code>EDecimal</code> , <code>EInteger</code> , or
     * <code>ERational</code> in the <a
  * href='https://www.nuget.org/packages/PeterO.Numbers'><code>PeterO.Numbers</code>
     * </a> library (in .NET) or the <a
  * href='https://github.com/peteroupc/numbers-java'><code>com.github.peteroupc/numbers</code>
     * </a> artifact (in Java), or if the type is <code>BigInteger</code> or
     * <code>BigDecimal</code> in the Java version, converts the given object to
     * a number of the corresponding type and throws an exception
     * (currently IllegalStateException) if the object does not represent a
     * number (for this purpose, infinity and not-a-number values, but not
     * <code>CBORObject.Null</code> , are considered numbers). Currently, this is
     * equivalent to the result of <code>AsEFloat()</code> , <code>AsEDecimal()</code>
     * , <code>AsEInteger</code> , or <code>AsERational()</code> , respectively, but
     * may change slightly in the next major version. Note that in the case
     * of <code>EFloat</code> , if this object represents a decimal number with a
     * fractional part, the conversion may lose information depending on
     * the number, and if the object is a rational number with a
     * nonterminating binary expansion, the number returned is a binary
     * floating-point number rounded to a high but limited precision. In
     * the case of <code>EDecimal</code> , if this object expresses a rational
     * number with a nonterminating decimal expansion, returns a decimal
     * number rounded to 34 digits of precision. In the case of
     * <code>EInteger</code> , if this CBOR object expresses a floating-point
     * number, it is converted to an integer by discarding its fractional
     * part, and if this CBOR object expresses a rational number, it is
     * converted to an integer by dividing the numerator by the denominator
     * and discarding the fractional part of the result, and this method
     * throws an exception (currently ArithmeticException, but may change in
     * the next major version) if this object expresses infinity or a
     * not-a-number value.</li> <li>In the.NET version, if the type is a
     * nullable (e.g., <code>Nullable&lt;int&gt;</code> or <code>int?</code> , returns
     * <code>null</code> if this CBOR object is null, or this object's value
     * converted to the nullable's underlying type, e.g., <code>int</code> .</li>
     * <li>If the type is an enumeration (<code>Enum</code>) type and this CBOR
     * object is a text string or an integer, returns the appropriate
     * enumerated constant. (For example, if <code>MyEnum</code> includes an
     * entry for <code>MyValue</code> , this method will return
     *  <code>MyEnum.MyValue</code> if the CBOR object represents <code>"MyValue"</code>
     * or the underlying value for <code>MyEnum.MyValue</code> .) <b>Note:</b> If
     * an integer is converted to a.NET Enum constant, and that integer is
     * shared by more than one constant of the same type, it is undefined
     * which constant from among them is returned. (For example, if
     * <code>MyEnum.Zero = 0</code> and <code>MyEnum.Null = 0</code> , converting 0 to
     * <code>MyEnum</code> may return either <code>MyEnum.Zero</code> or
     * <code>MyEnum.Null</code> .) As a result, .NET Enum types with constants
     * that share an underlying value should not be passed to this
     * method.</li> <li>If the type is <code>byte[]</code> (a one-dimensional
     * byte array) and this CBOR object is a byte string, returns a byte
     * array which this CBOR byte string's data will be copied to. (This
     * method can't be used to encode CBOR data to a byte array; for that,
     * use the EncodeToBytes method instead.)</li> <li>If the type is a
     * one-dimensional or multidimensional array type and this CBOR object
     * is an array, returns an array containing the items in this CBOR
     * object.</li> <li>If the type is List, ReadOnlyCollection or the
     * generic or non-generic List, ICollection, Iterable,
     * IReadOnlyCollection, or IReadOnlyList (or ArrayList, List,
     * Collection, or Iterable in Java), and if this CBOR object is an
     * array, returns an object conforming to the type, class, or interface
     * passed to this method, where the object will contain all items in
     * this CBOR array.</li> <li>If the type is Dictionary,
     * ReadOnlyDictionary or the generic or non-generic Map or
     * IReadOnlyDictionary (or HashMap or Map in Java), and if this CBOR
     * object is a map, returns an object conforming to the type, class, or
     * interface passed to this method, where the object will contain all
     * keys and values in this CBOR map.</li> <li>If the type is an
     *  enumeration constant ("enum"), and this CBOR object is an integer or
     * text string, returns the enumeration constant with the given number
     * or name, respectively. (Enumeration constants made up of multiple
     * enumeration constants, as allowed by .NET, can only be matched by
     * number this way.)</li> <li>If the type is <code>java.util.Date</code> (or
     * <code>Date</code> in Java) , returns a date/time object if the CBOR
     * object's outermost tag is 0 or 1. For tag 1, this method treats the
     * CBOR object as a number of seconds since the start of 1970, which is
     *  based on the POSIX definition of "seconds since the Epoch", a
     * definition that does not count leap seconds. In this method, this
     * number of seconds assumes the use of a proleptic Gregorian calendar,
     * in which the rules regarding the number of days in each month and
     * which years are leap years are the same for all years as they were
     * in 1970 (including without regard to time zone differences or
     * transitions from other calendars to the Gregorian). The string
     * format used in tag 0 supports only years up to 4 decimal digits
     * long. For tag 1, CBOR objects that express infinity or not-a-number
     * (NaN) are treated as invalid by this method. This default behavior
     * for <code>java.util.Date</code> and <code>Date</code> can be changed by passing a
     * suitable CBORTypeMapper to this method, such as a CBORTypeMapper
     * that registers a CBORDateConverter for <code>java.util.Date</code> or
     * <code>Date</code> objects. See the examples.</li> <li>If the type is
     * <code>java.net.URI</code> (or <code>URI</code> in Java), returns a URI object if
     * possible.</li> <li>If the type is <code>java.util.UUID</code> (or <code>UUID</code> in
     * Java), returns a UUID object if possible.</li> <li>Plain-Old-Data
     * deserialization: If the object is a type not specially handled
     * above, the type includes a zero-parameter constructor (default or
     *  not), this CBOR object is a CBOR map, and the "mapper" parameter (if
     * any) allows this type to be eligible for Plain-Old-Data
     * deserialization, then this method checks the given type for eligible
     * setters as follows:</li> <li>(*) In the .NET version, eligible
     * setters are the public, nonstatic setters of properties with a
     * public, nonstatic getter. Eligible setters also include public,
     * nonstatic, non- <code>static final</code> , non- <code>readonly</code> fields. If a
     *  class has two properties and/or fields of the form "X" and "IsX",
     *  where "X" is any name, or has multiple properties and/or fields with
     * the same name, those properties and fields are ignored.</li> <li>(*)
     * In the Java version, eligible setters are public, nonstatic methods
     *  starting with "set" followed by a character other than a basic digit
     *  or lower-case letter, that is, other than "a" to "z" or "0" to "9",
     * that take one parameter. The class containing an eligible setter
     * must have a public, nonstatic method with the same name, but
     *  starting with "get" or "is" rather than "set", that takes no
     * parameters and does not return void. (For example, if a class has
     *  "public setValue(string)" and "public getValue()", "setValue" is an
     *  eligible setter. However, "setValue()" and "setValue(string, int)"
     * are not eligible setters.) In addition, public, nonstatic, nonfinal
     * fields are also eligible setters. If a class has two or more
     * otherwise eligible setters (methods and/or fields) with the same
     * name, but different parameter type, they are not eligible
     * setters.</li> <li>Then, the method creates an object of the given
     * type and invokes each eligible setter with the corresponding value
     * in the CBOR map, if any. Key names in the map are matched to
     * eligible setters according to the rules described in the {@link
     * com.upokecenter.cbor.PODOptions} documentation. Note that for
     * security reasons, certain types are not supported even if they
     * contain eligible setters. For the Java version, the object creation
     * may fail in the case of a nested nonstatic class.</li> </ul><p>
     * <p>The following example (originally written in C# for the DotNet
     * version) uses a CBORTypeMapper to change how CBOR objects are
     * converted to java.util.Date objects. In this case, the ToObject method
     * assumes the CBOR object is an untagged number giving the number of
     * seconds since the start of 1970.</p> <pre>CBORTypeMapper conv = new CBORTypeMapper().AddConverter(java.util.Date.class, CBORDateConverter.UntaggedNumber); var obj = CBORObject.FromObject().getToObject()&lt;java.util.Date&gt;(conv);</pre>
     * <p>Java offers no easy way to express a generic type, at least none
     * as easy as C#'s <code>typeof</code> operator. The following example,
     * written in Java, is a way to specify that the return value will be
     * an ArrayList of string objects.</p> <pre>Type arrayListString = new ParameterizedType() { public Type[] getActualTypeArguments() { &#x2f;&#x2a; Contains one type parameter, string&#x2a;&#x2f; return new Type[] { string.class }; } public Type getRawType() { /* Raw type is ArrayList &#x2a;&#x2f; return ArrayList.class; } public Type getOwnerType() { return null; } }; ArrayList&lt;string&gt; array = (ArrayList&lt;string&gt;) cborArray.ToObject(arrayListString);</pre>
     * <p>By comparison, the C# version is much shorter.</p>
     * <pre>List&lt;string&gt; array = (List&lt;string&gt;)cborArray.ToObject(typeof(List&lt;string&gt;));</pre> . </p>
     * @param t The type, class, or interface that this method's return value will
     * belong to. To express a generic type in Java, see the example.
     * <b>Note:</b> For security reasons, an application should not base
     * this parameter on user input or other externally supplied data.
     * Whenever possible, this parameter should be either a type specially
     * handled by this method, such as {@code int} or {@code string} , or a
     * plain-old-data type (POCO or POJO type) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.
     * @param mapper This parameter controls which data types are eligible for
     * Plain-Old-Data deserialization and includes custom converters from
     * CBOR objects to certain data types. Can be null.
     * @param options Specifies options for controlling deserialization of CBOR
     * objects.
     * @return The converted object.
     * @throws com.upokecenter.cbor.CBORException The given type {@code t} , or
     * this object's CBOR type, is not supported, or the given object's
     * nesting is too deep, or another error occurred when serializing the
     * object.
     * @throws NullPointerException The parameter {@code t} or {@code options} is
     * null.
     */
    @SuppressWarnings("unchecked")
public <T> T ToObject(java.lang.reflect.Type t, CBORTypeMapper mapper, PODOptions
      options) {
      if (options == null) {
        throw new NullPointerException("options");
      }
      return (T)(this.ToObject(t, mapper, options, 0));
    }

    /**
     * Generates an object of an arbitrary type from an array of CBOR-encoded
     * bytes, using the given <code>CBOREncodeOptions</code> object to control
     * the decoding process. It is equivalent to DecodeFromBytes followed
     * by ToObject. See the documentation for those methods for more
     * information.
     * @param data A byte array in which a single CBOR object is encoded.
     * @param enc Specifies options to control how the CBOR object is decoded. See
     * {@link com.upokecenter.cbor.CBOREncodeOptions} for more information.
     * @param t The type, class, or interface that this method's return value will
     * belong to. To express a generic type in Java, see the example.
     * <b>Note:</b> For security reasons, an application should not base
     * this parameter on user input or other externally supplied data.
     * Whenever possible, this parameter should be either a type specially
     * handled by this method, such as {@code int} or {@code string}, or a
     * plain-old-data type (POCO or POJO type) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.
     * @param mapper This parameter controls which data types are eligible for
     * Plain-Old-Data deserialization and includes custom converters from
     * CBOR objects to certain data types. Can be null.
     * @param pod Specifies options for controlling deserialization of CBOR
     * objects.
     * @return An object of the given type decoded from the given byte array.
     * Returns null (as opposed to CBORObject.Null) if {@code data} is
     * empty and the AllowEmpty property is set on the given
     * CBOREncodeOptions object.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where not all of the byte
     * array represents a CBOR object. This exception is also thrown if the
     * parameter {@code data} is empty unless the AllowEmpty property is
     * set on the given options object. Also thrown if the given type
     * {@code t}, or this object's CBOR type, is not supported, or the
     * given object's nesting is too deep, or another error occurred when
     * serializing the object.
     * @throws NullPointerException The parameter {@code data} is null, or the
     * parameter {@code enc} is null, or the parameter {@code t} or {@code
     * pod} is null.
     */
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

    /**
     * Generates an object of an arbitrary type from an array of CBOR-encoded
     * bytes, using the given <code>CBOREncodeOptions</code> object to control
     * the decoding process. It is equivalent to DecodeFromBytes followed
     * by ToObject. See the documentation for those methods for more
     * information.
     * @param data A byte array in which a single CBOR object is encoded.
     * @param enc Specifies options to control how the CBOR object is decoded. See
     * {@link com.upokecenter.cbor.CBOREncodeOptions} for more information.
     * @param t The type, class, or interface that this method's return value will
     * belong to. To express a generic type in Java, see the example.
     * <b>Note:</b> For security reasons, an application should not base
     * this parameter on user input or other externally supplied data.
     * Whenever possible, this parameter should be either a type specially
     * handled by this method, such as {@code int} or {@code string}, or a
     * plain-old-data type (POCO or POJO type) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.
     * @return An object of the given type decoded from the given byte array.
     * Returns null (as opposed to CBORObject.Null) if {@code data} is
     * empty and the AllowEmpty property is set on the given
     * CBOREncodeOptions object.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where not all of the byte
     * array represents a CBOR object. This exception is also thrown if the
     * parameter {@code data} is empty unless the AllowEmpty property is
     * set on the given options object. Also thrown if the given type
     * {@code t}, or this object's CBOR type, is not supported, or the
     * given object's nesting is too deep, or another error occurred when
     * serializing the object.
     * @throws NullPointerException The parameter {@code data} is null, or the
     * parameter {@code enc} is null, or the parameter {@code t} is null.
     */
    @SuppressWarnings("unchecked")
public static <T> T DecodeObjectFromBytes(
      byte[] data,
      CBOREncodeOptions enc,
      java.lang.reflect.Type t) {
       return (T)(DecodeFromBytes(data, enc).ToObject(t));
    }

    /**
     * Generates an object of an arbitrary type from an array of CBOR-encoded
     * bytes. It is equivalent to DecodeFromBytes followed by ToObject. See
     * the documentation for those methods for more information.
     * @param data A byte array in which a single CBOR object is encoded.
     * @param t The type, class, or interface that this method's return value will
     * belong to. To express a generic type in Java, see the example.
     * <b>Note:</b> For security reasons, an application should not base
     * this parameter on user input or other externally supplied data.
     * Whenever possible, this parameter should be either a type specially
     * handled by this method, such as {@code int} or {@code string}, or a
     * plain-old-data type (POCO or POJO type) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.
     * @param mapper This parameter controls which data types are eligible for
     * Plain-Old-Data deserialization and includes custom converters from
     * CBOR objects to certain data types. Can be null.
     * @param pod Specifies options for controlling deserialization of CBOR
     * objects.
     * @return An object of the given type decoded from the given byte array.
     * Returns null (as opposed to CBORObject.Null) if {@code data} is
     * empty and the AllowEmpty property is set on the given
     * CBOREncodeOptions object.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where not all of the byte
     * array represents a CBOR object. This exception is also thrown if the
     * parameter {@code data} is empty unless the AllowEmpty property is
     * set on the given options object. Also thrown if the given type
     * {@code t}, or this object's CBOR type, is not supported, or the
     * given object's nesting is too deep, or another error occurred when
     * serializing the object.
     * @throws NullPointerException The parameter {@code data} is null, or the
     * parameter {@code t} or {@code pod} is null.
     */
    @SuppressWarnings("unchecked")
public static <T> T DecodeObjectFromBytes(
      byte[] data,
      java.lang.reflect.Type t,
      CBORTypeMapper mapper,
      PODOptions pod) {
       return (T)(DecodeObjectFromBytes(data, CBOREncodeOptions.Default, t, mapper, pod));
    }

    /**
     * Generates an object of an arbitrary type from an array of CBOR-encoded
     * bytes. It is equivalent to DecodeFromBytes followed by ToObject. See
     * the documentation for those methods for more information.
     * @param data A byte array in which a single CBOR object is encoded.
     * @param t The type, class, or interface that this method's return value will
     * belong to. To express a generic type in Java, see the example.
     * <b>Note:</b> For security reasons, an application should not base
     * this parameter on user input or other externally supplied data.
     * Whenever possible, this parameter should be either a type specially
     * handled by this method, such as {@code int} or {@code string}, or a
     * plain-old-data type (POCO or POJO type) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.
     * @return An object of the given type decoded from the given byte array.
     * Returns null (as opposed to CBORObject.Null) if {@code data} is
     * empty and the AllowEmpty property is set on the given
     * CBOREncodeOptions object.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data. This includes cases where not all of the byte
     * array represents a CBOR object. This exception is also thrown if the
     * parameter {@code data} is empty unless the AllowEmpty property is
     * set on the given options object. Also thrown if the given type
     * {@code t}, or this object's CBOR type, is not supported, or the
     * given object's nesting is too deep, or another error occurred when
     * serializing the object.
     * @throws NullPointerException The parameter {@code data} is null, or the
     * parameter {@code t} is null.
     */
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
          throw new IllegalStateException("Not a number type");
        }
        return (T)(cn.GetNumberInterface().AsEFloat(cn.GetValue()));
      }
      if (t.equals(EInteger.class)) {
        CBORNumber cn = CBORNumber.FromCBORObject(this);
        if (cn == null) {
          throw new IllegalStateException("Not a number type");
        }
        return (T)(cn.GetNumberInterface().AsEInteger(cn.GetValue()));
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
          throw new IllegalStateException("Not a number type");
        }
        return (T)(cn.GetNumberInterface().AsERational(cn.GetValue()));
      }
      return (T)(t.equals(String.class) ? this.AsString() :
        PropertyMap.TypeToObject(this, t, mapper, options, depth));
    }

    /**
     * Generates a CBOR object from a 64-bit signed integer.
     * @param value The parameter {@code value} is a 64-bit signed integer.
     * @return A CBOR object.
     */
    public static CBORObject FromObject(long value) {
      if (value >= 0L && value < 24L) {
        return FixedObjects[(int)value];
      } else {
        return (value >= -24L && value < 0L) ? FixedObjects[0x20 - (int)(value +
              1L)] : new CBORObject(CBORObjectTypeInteger, value);
      }
    }

    /**
     * Generates a CBOR object from a CBOR object.
     * @param value The parameter {@code value} is a CBOR object.
     * @return Same as {@code value}, or "CBORObject.Null" is {@code value} is
     * null.
     */
    public static CBORObject FromObject(CBORObject value) {
      return (value == null) ? (CBORObject.Null) : value;
    }

    private static int IntegerByteLength(int intValue) {
      if (intValue < 0) {
        intValue = -(intValue + 1);
      }
      if (intValue > 0xffff) {
        return 5;
      } else if (intValue > 0xff) {
        return 3;
      } else {
        return (intValue > 23) ? 2 : 1;
      }
    }

    private static int IntegerByteLength(long longValue) {
      if (longValue < 0) {
        longValue = -(longValue + 1);
      }
      if (longValue > 0xffffffffL) {
        return 9;
      } else if (longValue > 0xffffL) {
        return 5;
      } else if (longValue > 0xffL) {
        return 3;
      } else {
        return (longValue > 23L) ? 2 : 1;
      }
    }

    /**
     * Calculates the number of bytes this CBOR object takes when serialized as a
     * byte array using the <code>EncodeToBytes()</code> method. This calculation
     * assumes that integers, lengths of maps and arrays, lengths of text
     * and byte strings, and tag numbers are encoded in their shortest
     * form; that floating-point numbers are encoded in their shortest
     * value-preserving form; and that no indefinite-length encodings are
     * used.
     * @return The number of bytes this CBOR object takes when serialized as a byte
     * array using the {@code EncodeToBytes()} method.
     * @throws com.upokecenter.cbor.CBORException The CBOR object has an extremely
     * deep level of nesting, including if the CBOR object is or has an
     * array or map that includes itself.
     */
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
          int bits = CBORUtilities.DoubleToHalfPrecisionIfSameValue(valueBits);
          if (bits != -1) {
            return size + 3;
          }
          return CBORUtilities.DoubleRetainsSameValueInSingle(valueBits) ?
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
          long ulength = DataUtilities.GetUtf8Length(this.AsString(), false);
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

    /**
     * Generates a CBOR object from an arbitrary-precision integer. The CBOR object
     * is generated as follows: <ul> <li>If the number is null, returns
     * CBORObject.Null.</li> <li>Otherwise, if the number is greater than
     * or equal to -(2^64) and less than 2^64, the CBOR object will have
     * the object type Integer and the appropriate value.</li>
     * <li>Otherwise, the CBOR object will have tag 2 (zero or positive) or
     * 3 (negative) and the appropriate value.</li></ul>
     * @param bigintValue An arbitrary-precision integer. Can be null.
     * @return The given number encoded as a CBOR object. Returns CBORObject.Null
     * if {@code bigintValue} is null.
     */
    public static CBORObject FromObject(EInteger bigintValue) {
      if ((Object)bigintValue == (Object)null) {
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

    /**
     * Generates a CBOR object from an arbitrary-precision binary floating-point
     * number. The CBOR object is generated as follows (this is a change in
     * version 4.0): <ul> <li>If the number is null, returns
     * CBORObject.Null.</li> <li>Otherwise, if the number expresses
     * infinity, not-a-number, or negative zero, the CBOR object will have
     * tag 269 and the appropriate format.</li> <li>Otherwise, if the
     * number's exponent is at least 2^64 or less than -(2^64), the CBOR
     * object will have tag 265 and the appropriate format.</li>
     * <li>Otherwise, the CBOR object will have tag 5 and the appropriate
     * format.</li></ul>
     * @param bigValue An arbitrary-precision binary floating-point number. Can be
     * null.
     * @return The given number encoded as a CBOR object. Returns CBORObject.Null
     * if {@code bigValue} is null.
     */
    public static CBORObject FromObject(EFloat bigValue) {
      if ((Object)bigValue == (Object)null) {
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

    /**
     * Generates a CBOR object from an arbitrary-precision rational number. The
     * CBOR object is generated as follows (this is a change in version
     * 4.0): <ul> <li>If the number is null, returns CBORObject.Null.</li>
     * <li>Otherwise, if the number expresses infinity, not-a-number, or
     * negative zero, the CBOR object will have tag 270 and the appropriate
     * format.</li> <li>Otherwise, the CBOR object will have tag 30 and the
     * appropriate format.</li></ul>
     * @param bigValue An arbitrary-precision rational number. Can be null.
     * @return The given number encoded as a CBOR object. Returns CBORObject.Null
     * if {@code bigValue} is null.
     */
    public static CBORObject FromObject(ERational bigValue) {
      if ((Object)bigValue == (Object)null) {
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

    /**
     * Generates a CBOR object from a decimal number. The CBOR object is generated
     * as follows (this is a change in version 4.0): <ul> <li>If the number
     * is null, returns CBORObject.Null.</li> <li>Otherwise, if the number
     * expresses infinity, not-a-number, or negative zero, the CBOR object
     * will have tag 268 and the appropriate format.</li> <li>If the
     * number's exponent is at least 2^64 or less than -(2^64), the CBOR
     * object will have tag 264 and the appropriate format.</li>
     * <li>Otherwise, the CBOR object will have tag 4 and the appropriate
     * format.</li></ul>
     * @param bigValue An arbitrary-precision decimal number. Can be null.
     * @return The given number encoded as a CBOR object. Returns CBORObject.Null
     * if {@code bigValue} is null.
     */
    public static CBORObject FromObject(EDecimal bigValue) {
      if ((Object)bigValue == (Object)null) {
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

    /**
     * Generates a CBOR object from a text string.
     * @param strValue A text string value. Can be null.
     * @return A CBOR object representing the string, or CBORObject.Null if
     * stringValue is null.
     * @throws IllegalArgumentException The string contains an unpaired surrogate code
     * point.
     */
    public static CBORObject FromObject(String strValue) {
      if (strValue == null) {
        return CBORObject.Null;
      }
      if (strValue.length() == 0) {
        return GetFixedObject(0x60);
      }
      long utf8Length = DataUtilities.GetUtf8Length(strValue, false);
      if (utf8Length < 0) {
        throw new IllegalArgumentException("String contains an unpaired " +
          "surrogate code point.");
      }
      return new CBORObject(
        strValue.length() == utf8Length ? CBORObjectTypeTextStringAscii : CBORObjectTypeTextString,
        strValue);
    }

    /**
     * Generates a CBOR object from a 32-bit signed integer.
     * @param value The parameter {@code value} is a 32-bit signed integer.
     * @return A CBOR object.
     */
    public static CBORObject FromObject(int value) {
      if (value >= 0 && value < 24) {
        return FixedObjects[value];
      } else {
        return (value >= -24 && value < 0) ? FixedObjects[0x20 - (value + 1)] :
          FromObject((long)value);
      }
    }

    /**
     * Generates a CBOR object from a 16-bit signed integer.
     * @param value The parameter {@code value} is a 16-bit signed integer.
     * @return A CBOR object generated from the given integer.
     */
    public static CBORObject FromObject(short value) {
      if (value >= 0 && value < 24) {
        return FixedObjects[value];
      } else {
        return (value >= -24 && value < 0) ? FixedObjects[0x20 - (value + 1)] :
          FromObject((long)value);
      }
    }

    /**
     * Returns the CBOR true value or false value, depending on "value".
     * @param value Either {@code true} or {@code false}.
     * @return CBORObject.True if value is true; otherwise CBORObject.False.
     */
    public static CBORObject FromObject(boolean value) {
      return value ? CBORObject.True : CBORObject.False;
    }

    /**
     * Generates a CBOR object from a byte (0 to 255).
     * @param value The parameter {@code value} is a byte (from 0 to 255).
     * @return A CBOR object generated from the given integer.
     */
    public static CBORObject FromObject(byte value) {
      return FromObject(((int)value) & 0xff);
    }

    /**
     * Generates a CBOR object from a 32-bit floating-point number. The input value
     * can be a not-a-number (NaN) value (such as <code>Float.NaN</code> in
     * DotNet or Float.NaN in Java); however, NaN values have multiple
     * forms that are equivalent for many applications' purposes, and
     * <code>Float.NaN</code> / <code>Float.NaN</code> is only one of these equivalent
     * forms. In fact, <code>CBORObject.FromObject(Float.NaN)</code> or
     * <code>CBORObject.FromObject(Float.NaN)</code> could produce a CBOR-encoded
     * object that differs between DotNet and Java, because
     * <code>Float.NaN</code> / <code>Float.NaN</code> may have a different form in
     * DotNet and Java (for example, the NaN value's sign may be negative
     * in DotNet, but positive in Java).
     * @param value The parameter {@code value} is a 32-bit floating-point number.
     * @return A CBOR object generated from the given number.
     */
    public static CBORObject FromObject(float value) {
      long doubleBits = CBORUtilities.SingleToDoublePrecision(
          CBORUtilities.SingleToInt32Bits(value));
      return new CBORObject(CBORObjectTypeDouble, doubleBits);
    }

    /**
     * Generates a CBOR object from a 64-bit floating-point number. The input value
     * can be a not-a-number (NaN) value (such as <code>Double.NaN</code>);
     * however, NaN values have multiple forms that are equivalent for many
     * applications' purposes, and <code>Double.NaN</code> is only one of these
     * equivalent forms. In fact, <code>CBORObject.FromObject(Double.NaN)</code>
     * could produce a CBOR-encoded object that differs between DotNet and
     * Java, because <code>Double.NaN</code> may have a different form in DotNet
     * and Java (for example, the NaN value's sign may be negative in
     * DotNet, but positive in Java).
     * @param value The parameter {@code value} is a 64-bit floating-point number.
     * @return A CBOR object generated from the given number.
     */
    public static CBORObject FromObject(double value) {
      long doubleBits = CBORUtilities.DoubleToInt64Bits(value);
      return new CBORObject(CBORObjectTypeDouble, doubleBits);
    }

    /**
     * Generates a CBOR object from an array of 8-bit bytes; the byte array is
     * copied to a new byte array in this process. (This method can't be
     * used to decode CBOR data from a byte array; for that, use the
     * <b>DecodeFromBytes</b> method instead.).
     * @param bytes An array of 8-bit bytes; can be null.
     * @return A CBOR object where each element of the given byte array is copied
     * to a new array, or CBORObject.Null if the value is null.
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
      List<CBORObject> list = new ArrayList<CBORObject>(array.length ==
        Integer.MAX_VALUE ? array.length : (array.length + 1));
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
      List<CBORObject> list = new ArrayList<CBORObject>(array.length ==
        Integer.MAX_VALUE ? array.length : (array.length + 1));
      for (long i : array) {
        list.add(FromObject(i));
      }
      return new CBORObject(CBORObjectTypeArray, list);
    }

    /**
     * Generates a CBORObject from an arbitrary object. See the overload of this
     * method that takes CBORTypeMapper and PODOptions arguments.
     * @param obj The parameter {@code obj} is an arbitrary object, which can be
     * null. <p><b>NOTE:</b> For security reasons, whenever possible, an
     * application should not base this parameter on user input or other
     * externally supplied data unless the application limits this
     * parameter's inputs to types specially handled by this method (such
     * as {@code int} or {@code string}) and/or to plain-old-data types
     * (POCO or POJO types) within the control of the application. If the
     * plain-old-data type references other data types, those types should
     * likewise meet either criterion above.</p>.
     * @return A CBOR object corresponding to the given object. Returns
     * CBORObject.Null if the object is null.
     */
    public static CBORObject FromObject(Object obj) {
      return FromObject(obj, PODOptions.Default);
    }

    /**
     * Generates a CBORObject from an arbitrary object. See the overload of this
     * method that takes CBORTypeMapper and PODOptions arguments.
     * @param obj The parameter {@code obj} is an arbitrary object. <p><b>NOTE:</b>
     * For security reasons, whenever possible, an application should not
     * base this parameter on user input or other externally supplied data
     * unless the application limits this parameter's inputs to types
     * specially handled by this method (such as {@code int} or {@code
     * string}) and/or to plain-old-data types (POCO or POJO types) within
     * the control of the application. If the plain-old-data type
     * references other data types, those types should likewise meet either
     * criterion above.</p>.
     * @param options An object containing options to control how certain objects
     * are converted to CBOR objects.
     * @return A CBOR object corresponding to the given object. Returns
     * CBORObject.Null if the object is null.
     * @throws NullPointerException The parameter {@code options} is null.
     */
    public static CBORObject FromObject(
      Object obj,
      PODOptions options) {
      return FromObject(obj, options, null, 0);
    }

    /**
     * Generates a CBORObject from an arbitrary object. See the overload of this
     * method that takes CBORTypeMapper and PODOptions arguments.
     * @param obj The parameter {@code obj} is an arbitrary object. <p><b>NOTE:</b>
     * For security reasons, whenever possible, an application should not
     * base this parameter on user input or other externally supplied data
     * unless the application limits this parameter's inputs to types
     * specially handled by this method (such as {@code int} or {@code
     * string}) and/or to plain-old-data types (POCO or POJO types) within
     * the control of the application. If the plain-old-data type
     * references other data types, those types should likewise meet either
     * criterion above.</p>.
     * @param mapper An object containing optional converters to convert objects of
     * certain types to CBOR objects.
     * @return A CBOR object corresponding to the given object. Returns
     * CBORObject.Null if the object is null.
     * @throws NullPointerException The parameter {@code mapper} is null.
     */
    public static CBORObject FromObject(
      Object obj,
      CBORTypeMapper mapper) {
      if (mapper == null) {
        throw new NullPointerException("mapper");
      }
      return FromObject(obj, PODOptions.Default, mapper, 0);
    }

    /**
     * <p>Generates a CBORObject from an arbitrary object, using the given options
     * to control how certain objects are converted to CBOR objects. The
     * following cases are checked in the logical order given (rather than
     * the strict order in which they are implemented by this library):</p>
     * <ul><li><code>null</code> is converted to <code>CBORObject.Null</code> .</li>
     * <li>A <code>CBORObject</code> is returned as itself.</li> <li>If the
     * object is of a type corresponding to a type converter mentioned in
     * the <paramref name='mapper'/> parameter, that converter will be used
     * to convert the object to a CBOR object. Type converters can be used
     * to override the default conversion behavior of almost any
     * object.</li> <li>A <code>char</code> is converted to an integer (from 0
     * through 65535), and returns a CBOR object of that integer. (This is
     * a change in version 4.0 from previous versions, which converted
     * <code>char</code> , except surrogate code points from 0xd800 through
     * 0xdfff, into single-character text strings.)</li> <li>A
     * <code>boolean</code> (<code>boolean</code> in Java) is converted to
     * <code>CBORObject.True</code> or <code>CBORObject.False</code> .</li> <li>A
     * <code>byte</code> is converted to a CBOR integer from 0 through 255.</li>
     * <li>A primitive integer type (<code>int</code> , <code>short</code> ,
     * <code>long</code> , as well as <code>sbyte</code> , <code>ushort</code> , <code>uint</code>
     * , and <code>ulong</code> in.NET) is converted to the corresponding CBOR
     * integer.</li> <li>A primitive floating-point type (<code>float</code> ,
     * <code>double</code> , as well as <code>decimal</code> in.NET) is converted to
     * the corresponding CBOR number.</li> <li>A <code>string</code> is converted
     * to a CBOR text string. To create a CBOR byte string object from
     * <code>string</code> , see the example given in <see
     * cref='PeterO.Cbor.CBORObject.FromObject(System.Byte[])'/>.</li>
     * <li>In the.NET version, a nullable is converted to
     * <code>CBORObject.Null</code> if the nullable's value is <code>null</code> , or
     * converted according to the nullable's underlying type, if that type
     * is supported by this method.</li> <li>In the Java version, a number
     * of type <code>BigInteger</code> or <code>BigDecimal</code> is converted to the
     * corresponding CBOR number.</li> <li>A number of type <code>EDecimal</code>
     * , <code>EFloat</code> , <code>EInteger</code> , and <code>ERational</code> in the <a
  * href='https://www.nuget.org/packages/PeterO.Numbers'><code>PeterO.Numbers</code>
     * </a> library (in .NET) or the <a
  * href='https://github.com/peteroupc/numbers-java'><code>com.github.peteroupc/numbers</code>
     * </a> artifact (in Java) is converted to the corresponding CBOR
     * number.</li> <li>An array other than <code>byte[]</code> is converted to a
     * CBOR array. In the.NET version, a multidimensional array is
     * converted to an array of arrays.</li> <li>A <code>byte[]</code>
     * (1-dimensional byte array) is converted to a CBOR byte string; the
     * byte array is copied to a new byte array in this process. (This
     * method can't be used to decode CBOR data from a byte array; for
     * that, use the <b>DecodeFromBytes</b> method instead.)</li> <li>An
     * object implementing Map (Map in Java) is converted to a CBOR map
     * containing the keys and values enumerated.</li> <li>An object
     * implementing Iterable (Iterable in Java) is converted to a CBOR
     * array containing the items enumerated.</li> <li>An enumeration(
     * <code>Enum</code>) object is converted to its <i>underlying value</i> in
     * the.NET version, or the result of its <code>ordinal()</code> method in the
     * Java version.</li> <li>An object of type <code>java.util.Date</code> ,
     * <code>java.net.URI</code> , or <code>java.util.UUID</code> (<code>Date</code> , <code>URI</code> , or
     * <code>UUID</code> , respectively, in Java) will be converted to a tagged
     * CBOR object of the appropriate kind. By default, <code>java.util.Date</code> /
     * <code>Date</code> will be converted to a tag-0 string following the date
     * format used in the Atom syndication format, but this behavior can be
     * changed by passing a suitable CBORTypeMapper to this method, such as
     * a CBORTypeMapper that registers a CBORDateConverter for
     * <code>java.util.Date</code> or <code>Date</code> objects. See the examples.</li>
     * <li>If the object is a type not specially handled above, this method
     * checks the <paramref name='obj'/> parameter for eligible getters as
     * follows:</li> <li>(*) In the .NET version, eligible getters are the
     * public, nonstatic getters of read/write properties (and also those
     * of read-only properties in the case of a compiler-generated type or
     * an F# type). Eligible getters also include public, nonstatic, non-
     * <code>static final</code> , non- <code>readonly</code> fields. If a class has two
     *  properties and/or fields of the form "X" and "IsX", where "X" is any
     * name, or has multiple properties and/or fields with the same name,
     * those properties and fields are ignored.</li> <li>(*) In the Java
     * version, eligible getters are public, nonstatic methods starting
     *  with "get" or "is" (either word followed by a character other than a
     *  basic digit or lower-case letter, that is, other than "a" to "z" or
     *  "0" to "9"), that take no parameters and do not return void, except
     *  that methods named "getClass" are not eligible getters. In addition,
     * public, nonstatic, nonfinal fields are also eligible getters. If a
     * class has two otherwise eligible getters (methods and/or fields) of
     *  the form "isX" and "getX", where "X" is the same in both, or two
     * such getters with the same name but different return type, they are
     * not eligible getters.</li> <li>Then, the method returns a CBOR map
     * with each eligible getter's name or property name as each key, and
     * with the corresponding value returned by that getter as that key's
     * value. Before adding a key-value pair to the map, the key's name is
     * adjusted according to the rules described in the {@link
     * com.upokecenter.cbor.PODOptions} documentation. Note that for
     * security reasons, certain types are not supported even if they
     * contain eligible getters.</li> </ul> <p><b>REMARK:</b> .NET
     * enumeration (<code>Enum</code>) constants could also have been converted
     * to text strings with <code>toString()</code> , but that method will return
     * multiple names if the given Enum object is a combination of Enum
     * objects (e.g. if the object is <code>FileAccess.Read |
     * FileAccess.Write</code>). More generally, if Enums are converted to
     * text strings, constants from Enum types with the <code>Flags</code>
     * attribute, and constants from the same Enum type that share an
     * underlying value, should not be passed to this method.</p><p> <p>The
     * following example (originally written in C# for the DotNet version)
     * uses a CBORTypeMapper to change how java.util.Date objects are converted
     * to CBOR. In this case, such objects are converted to CBOR objects
     * with tag 1 that store numbers giving the number of seconds since the
     * start of 1970.</p> <pre>CBORTypeMapper conv = new CBORTypeMapper().AddConverter(java.util.Date.class, CBORDateConverter.TaggedNumber); CBORObject obj = CBORObject.FromObject(java.util.Date.Now, conv);</pre> <p>The following
     * example generates a CBOR object from a 64-bit signed integer that is
     * treated as a 64-bit unsigned integer (such as DotNet's UInt64, which
     * has no direct equivalent in the Java language), in the sense that
     * the value is treated as 2^64 plus the original value if it's
     * negative.</p> <pre>long x = -40L; &#x2f;&#x2a; Example 64-bit value treated as 2^64-40.&#x2a;&#x2f; CBORObject obj = CBORObject.FromObject(v &lt; 0 ? EInteger.FromInt32(1).ShiftLeft(64).Add(v) : EInteger.FromInt64(v));</pre> <p>In the Java version, which has
     * java.math.getBigInteger(), the following can be used instead:</p>
     * <pre>long x = -40L; &#x2f;&#x2a; Example 64-bit value treated as 2^64-40.&#x2a;&#x2f; CBORObject obj = CBORObject.FromObject(v &lt; 0 ? BigInteger.valueOf(1).shiftLeft(64).add(BigInteger.valueOf(v)) : BigInteger.valueOf(v));</pre> </p>
     * @param obj An arbitrary object to convert to a CBOR object. <p><b>NOTE:</b>
     * For security reasons, whenever possible, an application should not
     * base this parameter on user input or other externally supplied data
     * unless the application limits this parameter's inputs to types
     * specially handled by this method (such as {@code int} or {@code
     * string}) and/or to plain-old-data types (POCO or POJO types) within
     * the control of the application. If the plain-old-data type
     * references other data types, those types should likewise meet either
     * criterion above.</p>.
     * @param mapper An object containing optional converters to convert objects of
     * certain types to CBOR objects. Can be null.
     * @param options An object containing options to control how certain objects
     * are converted to CBOR objects.
     * @return A CBOR object corresponding to the given object. Returns
     * CBORObject.Null if the object is null.
     * @throws NullPointerException The parameter {@code options} is null.
     * @throws com.upokecenter.cbor.CBORException An error occurred while
     * converting the given object to a CBOR object.
     */
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
      byte[] bytearr = ((obj instanceof byte[]) ? (byte[])obj : null);
      if (bytearr != null) {
        return FromObject(bytearr);
      }
      if (obj instanceof Map<?, ?>) {
        // Map appears first because Map includes Iterable
        objret = CBORObject.NewMap();
        Map<?, ?> objdic =
          (Map<?, ?>)obj;
        for (Object keyPair : objdic.entrySet()) {
          Map.Entry<?, ?>
          kvp = (Map.Entry<?, ?>)keyPair;
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

    /**
     * Generates a CBOR object from this one, but gives the resulting object a tag
     * in addition to its existing tags (the new tag is made the outermost
     * tag).
     * @param bigintTag Tag number. The tag number 55799 can be used to mark a
     *  "self-described CBOR" object. This document does not attempt to list
     * all CBOR tags and their meanings. An up-to-date list can be found at
     * the CBOR Tags registry maintained by the Internet Assigned Numbers
     * Authority(<i>iana.org/assignments/cbor-tags</i>).
     * @return A CBOR object with the same value as this one but given the tag
     * {@code bigintTag} in addition to its existing tags (the new tag is
     * made the outermost tag).
     * @throws IllegalArgumentException The parameter {@code bigintTag} is less than 0 or
     * greater than 2^64-1.
     * @throws NullPointerException The parameter {@code bigintTag} is null.
     */
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
            "tag more than 18446744073709551615 (" + bigintTag + ")");
        }
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
        return new CBORObject(this, tagLow, tagHigh);
      }
    }

    /**
     * Generates a CBOR object from an arbitrary object and gives the resulting
     * object a tag in addition to its existing tags (the new tag is made
     * the outermost tag).
     * @param valueOb The parameter {@code valueOb} is an arbitrary object, which
     * can be null. <p><b>NOTE:</b> For security reasons, whenever
     * possible, an application should not base this parameter on user
     * input or other externally supplied data unless the application
     * limits this parameter's inputs to types specially handled by this
     * method (such as {@code int} or {@code string}) and/or to
     * plain-old-data types (POCO or POJO types) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.</p>.
     * @param bigintTag Tag number. The tag number 55799 can be used to mark a
     *  "self-described CBOR" object. This document does not attempt to list
     * all CBOR tags and their meanings. An up-to-date list can be found at
     * the CBOR Tags registry maintained by the Internet Assigned Numbers
     * Authority(<i>iana.org/assignments/cbor-tags</i>).
     * @return A CBOR object where the object {@code valueOb} is converted to a
     * CBOR object and given the tag {@code bigintTag}. If {@code valueOb}
     * is null, returns a version of CBORObject.Null with the given tag.
     * @throws IllegalArgumentException The parameter {@code bigintTag} is less than 0 or
     * greater than 2^64-1.
     * @throws NullPointerException The parameter {@code bigintTag} is null.
     */
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
          "tag more than 18446744073709551615 (" + bigintTag + ")");
      }
      return FromObject(valueOb).WithTag(bigintTag);
    }

    /**
     * Generates a CBOR object from an arbitrary object and gives the resulting
     * object a tag in addition to its existing tags (the new tag is made
     * the outermost tag).
     * @param smallTag A 32-bit integer that specifies a tag number. The tag number
     *  55799 can be used to mark a "self-described CBOR" object. This
     * document does not attempt to list all CBOR tags and their meanings.
     * An up-to-date list can be found at the CBOR Tags registry maintained
     * by the Internet Assigned Numbers Authority(
     * <i>iana.org/assignments/cbor-tags</i>).
     * @return A CBOR object with the same value as this one but given the tag
     * {@code smallTag} in addition to its existing tags (the new tag is
     * made the outermost tag).
     * @throws IllegalArgumentException The parameter {@code smallTag} is less than 0.
     */
    public CBORObject WithTag(int smallTag) {
      if (smallTag < 0) {
        throw new IllegalArgumentException("smallTag(" + smallTag +
          ") is less than 0");
      }
      return new CBORObject(this, smallTag, 0);
    }

    /**
     * Generates a CBOR object from an arbitrary object and gives the resulting
     * object a tag in addition to its existing tags (the new tag is made
     * the outermost tag).
     * @param valueObValue The parameter {@code valueObValue} is an arbitrary
     * object, which can be null. <p><b>NOTE:</b> For security reasons,
     * whenever possible, an application should not base this parameter on
     * user input or other externally supplied data unless the application
     * limits this parameter's inputs to types specially handled by this
     * method (such as {@code int} or {@code string}) and/or to
     * plain-old-data types (POCO or POJO types) within the control of the
     * application. If the plain-old-data type references other data types,
     * those types should likewise meet either criterion above.</p>.
     * @param smallTag A 32-bit integer that specifies a tag number. The tag number
     *  55799 can be used to mark a "self-described CBOR" object. This
     * document does not attempt to list all CBOR tags and their meanings.
     * An up-to-date list can be found at the CBOR Tags registry maintained
     * by the Internet Assigned Numbers Authority(
     * <i>iana.org/assignments/cbor-tags</i>).
     * @return A CBOR object where the object {@code valueObValue} is converted to
     *  a CBOR object and given the tag {@code smallTag}. If "valueOb" is
     * null, returns a version of CBORObject.Null with the given tag.
     * @throws IllegalArgumentException The parameter {@code smallTag} is less than 0.
     */
    public static CBORObject FromObjectAndTag(
      Object valueObValue,
      int smallTag) {
      if (smallTag < 0) {
        throw new IllegalArgumentException("smallTag(" + smallTag +
          ") is less than 0");
      }
      return FromObject(valueObValue).WithTag(smallTag);
    }

    /**
     * Creates a CBOR object from a simple value number.
     * @param simpleValue The parameter {@code simpleValue} is a 32-bit signed
     * integer.
     * @return A CBOR object.
     * @throws IllegalArgumentException The parameter {@code simpleValue} is less than 0,
     * greater than 255, or from 24 through 31.
     */
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
        throw new IllegalArgumentException("Simple value is from 24 to 31: " +
          simpleValue);
      }
      if (simpleValue < 32) {
        return FixedObjects[0xe0 + simpleValue];
      }
      return new CBORObject(
          CBORObjectTypeSimpleValue,
          simpleValue);
    }

    /**
     * Multiplies two CBOR numbers.
     * @param first The parameter {@code first} is a CBOR object.
     * @param second The parameter {@code second} is a CBOR object.
     * @return The product of the two numbers.
     * @throws IllegalArgumentException Either or both operands are not numbers (as
     * opposed to Not-a-Number, NaN).
     * @throws NullPointerException The parameter {@code first} or {@code second}
     * is null.
     * @deprecated Instead, convert both CBOR objects to numbers (with\u0020.AsNumber()), and
 * use the first number's.Multiply() method.
 */
@Deprecated
    public static CBORObject Multiply(CBORObject first, CBORObject second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      CBORNumber a = CBORNumber.FromCBORObject(first);
      if (a == null) {
        throw new IllegalArgumentException("first" + "does not represent a" +
          "\u0020number");
      }
      CBORNumber b = CBORNumber.FromCBORObject(second);
      if (b == null) {
        throw new IllegalArgumentException("second" + "does not represent a" +
          "\u0020number");
      }
      return a.Multiply(b).ToCBORObject();
    }

    /**
     * Creates a new empty CBOR array.
     * @return A new CBOR array.
     */
    public static CBORObject NewArray() {
      return new CBORObject(CBORObjectTypeArray, new ArrayList<CBORObject>());
    }

    static CBORObject NewArray(CBORObject o1, CBORObject o2) {
      ArrayList<CBORObject> list = new ArrayList<CBORObject>(2);
      list.add(o1);
      list.add(o2);
      return new CBORObject(CBORObjectTypeArray, list);
    }

    static CBORObject NewArray(
      CBORObject o1,
      CBORObject o2,
      CBORObject o3) {
      ArrayList<CBORObject> list = new ArrayList<CBORObject>(2);
      list.add(o1);
      list.add(o2);
      list.add(o3);
      return new CBORObject(CBORObjectTypeArray, list);
    }

    /**
     * Creates a new empty CBOR map that stores its keys in an undefined order.
     * @return A new CBOR map.
     */
    public static CBORObject NewMap() {
      return new CBORObject(
          CBORObjectTypeMap,
          new TreeMap<CBORObject, CBORObject>());
    }

    /**
     * Creates a new empty CBOR map that ensures that keys are stored in the order
     * in which they are first inserted.
     * @return A new CBOR map.
     */
    public static CBORObject NewOrderedMap() {
      return new CBORObject(
          CBORObjectTypeMap,
          PropertyMap.NewOrderedDict());
    }

    /**
     * <p>Reads a sequence of objects in CBOR format from a data stream. This
     * method will read CBOR objects from the stream until the end of the
     * stream is reached or an error occurs, whichever happens first.</p>
     * @param stream A readable data stream.
     * @return An array containing the CBOR objects that were read from the data
     * stream. Returns an empty array if there is no unread data in the
     * stream.
     * @throws NullPointerException The parameter {@code stream} is null, or the
     *  parameter "options" is null.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data, including if the last CBOR object was read only
     * partially.
     */
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

    /**
     * <p>Reads a sequence of objects in CBOR format from a data stream. This
     * method will read CBOR objects from the stream until the end of the
     * stream is reached or an error occurs, whichever happens first.</p>
     * @param stream A readable data stream.
     * @param options Specifies the options to use when decoding the CBOR data
     * stream. See CBOREncodeOptions for more information. In this method,
     * the AllowEmpty property is treated as set regardless of the value of
     * that property specified in this parameter.
     * @return An array containing the CBOR objects that were read from the data
     * stream. Returns an empty array if there is no unread data in the
     * stream.
     * @throws NullPointerException The parameter {@code stream} is null, or the
     * parameter {@code options} is null.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data, including if the last CBOR object was read only
     * partially.
     */
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

    /**
     * <p>Reads an object in CBOR format from a data stream. This method will read
     * from the stream until the end of the CBOR object is reached or an
     * error occurs, whichever happens first.</p>
     * @param stream A readable data stream.
     * @return A CBOR object that was read.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data.
     */
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

    /**
     * Reads an object in CBOR format from a data stream, using the specified
     * options to control the decoding process. This method will read from
     * the stream until the end of the CBOR object is reached or an error
     * occurs, whichever happens first.
     * @param stream A readable data stream.
     * @param options Specifies the options to use when decoding the CBOR data
     * stream. See CBOREncodeOptions for more information.
     * @return A CBOR object that was read.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws com.upokecenter.cbor.CBORException There was an error in reading or
     * parsing the data.
     */
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

    /**
     * Generates a CBOR object from a data stream in JavaScript object Notation
     * (JSON) format. The JSON stream may begin with a byte-order mark
     * (U+FEFF). Since version 2.0, the JSON stream can be in UTF-8,
     * UTF-16, or UTF-32 encoding; the encoding is detected by assuming
     * that the first character read must be a byte-order mark or a nonzero
     * basic character (U+0001 to U+007F). (In previous versions, only
     * UTF-8 was allowed.). (This behavior may change to supporting only
     * UTF-8, with or without a byte order mark, in version 5.0 or later,
     * perhaps with an option to restore the previous behavior of also
     * supporting UTF-16 and UTF-32.).
     * @param stream A readable data stream. The sequence of bytes read from the
     * data stream must contain a single JSON object and not multiple
     * objects.
     * @return A CBOR object.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @throws com.upokecenter.cbor.CBORException The data stream contains invalid
     * encoding or is not in JSON format.
     */
    public static CBORObject ReadJSON(InputStream stream) throws java.io.IOException {
      return ReadJSON(stream, JSONOptions.Default);
    }

    /**
     * Generates a list of CBOR objects from a data stream in JavaScript object
     * Notation (JSON) text sequence format (RFC 7464). The data stream
     * must be in UTF-8 encoding and may not begin with a byte-order mark
     * (U+FEFF).<p>Generally, each JSON text in a JSON text sequence is
     * written as follows: Write a record separator byte (0x1e), then write
     * the JSON text in UTF-8 (without a byte order mark, U+FEFF), then
     * write the line feed byte (0x0a). RFC 7464, however, uses a more
     * liberal syntax for parsing JSON text sequences.</p>
     * @param stream A readable data stream. The sequence of bytes read from the
     * data stream must either be empty or begin with a record separator
     * byte (0x1e).
     * @return A list of CBOR objects read from the JSON sequence. Objects that
     * could not be parsed are replaced with {@code null} (as opposed to
     * {@code CBORObject.Null}) in the given list.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @throws com.upokecenter.cbor.CBORException The data stream is not empty and
     * does not begin with a record separator byte (0x1e).
     */
    public static CBORObject[] ReadJSONSequence(InputStream stream) throws java.io.IOException {
      return ReadJSONSequence(stream, JSONOptions.Default);
    }

    /**
     * Generates a CBOR object from a data stream in JavaScript object Notation
     * (JSON) format, using the specified options to control the decoding
     * process. The JSON stream may begin with a byte-order mark (U+FEFF).
     * Since version 2.0, the JSON stream can be in UTF-8, UTF-16, or
     * UTF-32 encoding; the encoding is detected by assuming that the first
     * character read must be a byte-order mark or a nonzero basic
     * character (U+0001 to U+007F). (In previous versions, only UTF-8 was
     * allowed.).
     * @param stream A readable data stream. The sequence of bytes read from the
     * data stream must contain a single JSON object and not multiple
     * objects.
     * @param options Contains options to control the JSON decoding process. This
     * method uses only the AllowDuplicateKeys property of this object.
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @throws com.upokecenter.cbor.CBORException The data stream contains invalid
     * encoding or is not in JSON format.
     * @deprecated Instead, use.getReadJSON()\u0028stream,
 * new\u0020JSONOptions\u0028\allowduplicatekeys = true\))
 * or\u0020.getReadJSON()\u0028stream,
 * new\u0020JSONOptions\u0028\allowduplicatekeys = false\)),
 * as\u0020appropriate.
 */
@Deprecated
    public static CBORObject ReadJSON(
      InputStream stream,
      CBOREncodeOptions options) throws java.io.IOException {
      if (stream == null) {
        throw new NullPointerException("stream");
      }
      if (options == null) {
        throw new NullPointerException("options");
      }
      JSONOptions jsonoptions = new JSONOptions(options.getAllowDuplicateKeys() ?
        "allowduplicatekeys=1" : "allowduplicatekeys=0");
      return ReadJSON(stream, jsonoptions);
    }

    /**
     * Generates a list of CBOR objects from a data stream in JavaScript object
     * Notation (JSON) text sequence format (RFC 7464). The data stream
     * must be in UTF-8 encoding and may not begin with a byte-order mark
     * (U+FEFF).<p>Generally, each JSON text in a JSON text sequence is
     * written as follows: Write a record separator byte (0x1e), then write
     * the JSON text in UTF-8 (without a byte order mark, U+FEFF), then
     * write the line feed byte (0x0a). RFC 7464, however, uses a more
     * liberal syntax for parsing JSON text sequences.</p>
     * @param stream A readable data stream. The sequence of bytes read from the
     * data stream must either be empty or begin with a record separator
     * byte (0x1e).
     * @param jsonoptions Specifies options to control how JSON texts in the stream
     * are decoded to CBOR. See the JSONOptions class.
     * @return A list of CBOR objects read from the JSON sequence. Objects that
     * could not be parsed are replaced with {@code null} (as opposed to
     * {@code CBORObject.Null}) in the given list.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @throws com.upokecenter.cbor.CBORException The data stream is not empty and
     * does not begin with a record separator byte (0x1e).
     */
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
        IOException ioex = ((ex.getCause() instanceof IOException) ? (IOException)ex.getCause() : null);
        if (ioex != null) {
          throw ioex;
        }
        throw ex;
      }
    }

    /**
     * Generates a CBOR object from a data stream in JavaScript object Notation
     * (JSON) format, using the specified options to control the decoding
     * process. The JSON stream may begin with a byte-order mark (U+FEFF).
     * Since version 2.0, the JSON stream can be in UTF-8, UTF-16, or
     * UTF-32 encoding; the encoding is detected by assuming that the first
     * character read must be a byte-order mark or a nonzero basic
     * character (U+0001 to U+007F). (In previous versions, only UTF-8 was
     * allowed.). (This behavior may change to supporting only UTF-8, with
     * or without a byte order mark, in version 5.0 or later, perhaps with
     * an option to restore the previous behavior of also supporting UTF-16
     * and UTF-32.).
     * @param stream A readable data stream. The sequence of bytes read from the
     * data stream must contain a single JSON object and not multiple
     * objects.
     * @param jsonoptions Specifies options to control how the JSON stream is
     * decoded to CBOR. See the JSONOptions class.
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @throws com.upokecenter.cbor.CBORException The data stream contains invalid
     * encoding or is not in JSON format.
     */
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
        IOException ioex = ((ex.getCause() instanceof IOException) ? (IOException)ex.getCause() : null);
        if (ioex != null) {
          throw ioex;
        }
        throw ex;
      }
    }

    /**
     * <p>Generates a CBOR object from a byte array in JavaScript object Notation
     * (JSON) format.</p> <p>If a JSON object has duplicate keys, a
     * CBORException is thrown.</p> <p>Note that if a CBOR object is
     * converted to JSON with <code>ToJSONBytes</code>, then the JSON is
     * converted back to CBOR with this method, the new CBOR object will
     * not necessarily be the same as the old CBOR object, especially if
     * the old CBOR object uses data types not supported in JSON, such as
     * integers in map keys.</p>
     * @param bytes A byte array in JSON format. The entire byte array must contain
     * a single JSON object and not multiple objects. The byte array may
     * begin with a byte-order mark (U+FEFF). The byte array can be in
     * UTF-8, UTF-16, or UTF-32 encoding; the encoding is detected by
     * assuming that the first character read must be a byte-order mark or
     * a nonzero basic character (U+0001 to U+007F). (This behavior may
     * change to supporting only UTF-8, with or without a byte order mark,
     * in version 5.0 or later, perhaps with an option to restore the
     * previous behavior of also supporting UTF-16 and UTF-32.).
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code bytes} is null.
     * @throws com.upokecenter.cbor.CBORException The byte array contains invalid
     * encoding or is not in JSON format.
     */
    public static CBORObject FromJSONBytes(byte[] bytes) {
      // TODO: In next major version, consider supporting UTF-8 only
      return FromJSONBytes(bytes, JSONOptions.Default);
    }

    /**
     * Generates a CBOR object from a byte array in JavaScript object Notation
     * (JSON) format, using the specified options to control the decoding
     * process. <p>Note that if a CBOR object is converted to JSON with
     * <code>ToJSONBytes</code>, then the JSON is converted back to CBOR with
     * this method, the new CBOR object will not necessarily be the same as
     * the old CBOR object, especially if the old CBOR object uses data
     * types not supported in JSON, such as integers in map keys.</p>
     * @param bytes A byte array in JSON format. The entire byte array must contain
     * a single JSON object and not multiple objects. The byte array may
     * begin with a byte-order mark (U+FEFF). The byte array can be in
     * UTF-8, UTF-16, or UTF-32 encoding; the encoding is detected by
     * assuming that the first character read must be a byte-order mark or
     * a nonzero basic character (U+0001 to U+007F). (This behavior may
     * change to supporting only UTF-8, with or without a byte order mark,
     * in version 5.0 or later, perhaps with an option to restore the
     * previous behavior of also supporting UTF-16 and UTF-32.).
     * @param jsonoptions Specifies options to control how the JSON data is decoded
     * to CBOR. See the JSONOptions class.
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code bytes} or {@code
     * jsonoptions} is null.
     * @throws com.upokecenter.cbor.CBORException The byte array contains invalid
     * encoding or is not in JSON format.
     */
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

    /**
     * <p>Generates a CBOR object from a byte array in JavaScript object Notation
     * (JSON) format.</p> <p>If a JSON object has duplicate keys, a
     * CBORException is thrown.</p> <p>Note that if a CBOR object is
     * converted to JSON with <code>ToJSONBytes</code>, then the JSON is
     * converted back to CBOR with this method, the new CBOR object will
     * not necessarily be the same as the old CBOR object, especially if
     * the old CBOR object uses data types not supported in JSON, such as
     * integers in map keys.</p>
     * @param bytes A byte array, the specified portion of which is in JSON format.
     * The specified portion of the byte array must contain a single JSON
     * object and not multiple objects. The portion may begin with a
     * byte-order mark (U+FEFF). The portion can be in UTF-8, UTF-16, or
     * UTF-32 encoding; the encoding is detected by assuming that the first
     * character read must be a byte-order mark or a nonzero basic
     * character (U+0001 to U+007F). (This behavior may change to
     * supporting only UTF-8, with or without a byte order mark, in version
     * 5.0 or later, perhaps with an option to restore the previous
     * behavior of also supporting UTF-16 and UTF-32.).
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code bytes} begins.
     * @param count The length, in bytes, of the desired portion of {@code bytes}
     * (but not more than {@code bytes} 's length).
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code bytes} is null.
     * @throws com.upokecenter.cbor.CBORException The byte array contains invalid
     * encoding or is not in JSON format.
     * @throws IllegalArgumentException Either {@code offset} or {@code count} is less
     * than 0 or greater than {@code bytes} 's length, or {@code bytes} 's
     * length minus {@code offset} is less than {@code count}.
     */
    public static CBORObject FromJSONBytes(byte[] bytes, int offset, int
      count) {
      return FromJSONBytes(bytes, offset, count, JSONOptions.Default);
    }

    /**
     * Generates a CBOR object from a byte array in JavaScript object Notation
     * (JSON) format, using the specified options to control the decoding
     * process. <p>Note that if a CBOR object is converted to JSON with
     * <code>ToJSONBytes</code>, then the JSON is converted back to CBOR with
     * this method, the new CBOR object will not necessarily be the same as
     * the old CBOR object, especially if the old CBOR object uses data
     * types not supported in JSON, such as integers in map keys.</p>
     * @param bytes A byte array, the specified portion of which is in JSON format.
     * The specified portion of the byte array must contain a single JSON
     * object and not multiple objects. The portion may begin with a
     * byte-order mark (U+FEFF). The portion can be in UTF-8, UTF-16, or
     * UTF-32 encoding; the encoding is detected by assuming that the first
     * character read must be a byte-order mark or a nonzero basic
     * character (U+0001 to U+007F). (This behavior may change to
     * supporting only UTF-8, with or without a byte order mark, in version
     * 5.0 or later, perhaps with an option to restore the previous
     * behavior of also supporting UTF-16 and UTF-32.).
     * @param offset An index, starting at 0, showing where the desired portion of
     * {@code bytes} begins.
     * @param count The length, in bytes, of the desired portion of {@code bytes}
     * (but not more than {@code bytes} 's length).
     * @param jsonoptions Specifies options to control how the JSON data is decoded
     * to CBOR. See the JSONOptions class.
     * @return A CBOR object containing the JSON data decoded.
     * @throws NullPointerException The parameter {@code bytes} or {@code
     * jsonoptions} is null.
     * @throws com.upokecenter.cbor.CBORException The byte array contains invalid
     * encoding or is not in JSON format.
     * @throws IllegalArgumentException Either {@code offset} or {@code count} is less
     * than 0 or greater than {@code bytes} 's length, or {@code bytes} 's
     * length minus {@code offset} is less than {@code count}.
     */
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
      if (bytes == null) {
        throw new NullPointerException("bytes");
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

    /**
     * Finds the remainder that results when a CBORObject object is divided by the
     * value of a CBOR object.
     * @param first The parameter {@code first} is a CBOR object.
     * @param second The parameter {@code second} is a CBOR object.
     * @return The remainder of the two numbers.
     * @throws NullPointerException The parameter {@code first} or {@code second}
     * is null.
     * @deprecated Instead, convert both CBOR objects to numbers (with\u0020.AsNumber()), and
 * use the first number's.Remainder() method.
 */
@Deprecated
    public static CBORObject Remainder(CBORObject first, CBORObject second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      CBORNumber a = CBORNumber.FromCBORObject(first);
      if (a == null) {
        throw new IllegalArgumentException("first" + "does not represent a" +
          "\u0020number");
      }
      CBORNumber b = CBORNumber.FromCBORObject(second);
      if (b == null) {
        throw new IllegalArgumentException("second" + "does not represent a" +
          "\u0020number");
      }
      return a.Remainder(b).ToCBORObject();
    }

    /**
     * Finds the difference between two CBOR number objects.
     * @param first The parameter {@code first} is a CBOR object.
     * @param second The parameter {@code second} is a CBOR object.
     * @return The difference of the two objects.
     * @throws IllegalArgumentException Either or both operands are not numbers (as
     * opposed to Not-a-Number, NaN).
     * @throws NullPointerException The parameter {@code first} or {@code second}
     * is null.
     * @deprecated Instead, convert both CBOR objects to numbers (with\u0020.AsNumber()), and
 * use the first number's.Subtract() method.
 */
@Deprecated
    public static CBORObject Subtract(CBORObject first, CBORObject second) {
      if (first == null) {
        throw new NullPointerException("first");
      }
      if (second == null) {
        throw new NullPointerException("second");
      }
      CBORNumber a = CBORNumber.FromCBORObject(first);
      if (a == null) {
        throw new IllegalArgumentException("first" + "does not represent a" +
          "\u0020number");
      }
      CBORNumber b = CBORNumber.FromCBORObject(second);
      if (b == null) {
        throw new IllegalArgumentException("second" + "does not represent a" +
          "\u0020number");
      }
      return a.Subtract(b).ToCBORObject();
    }

    /**
     * <p>Writes a text string in CBOR format to a data stream. The string will be
     * encoded using definite-length encoding regardless of its length.</p>
     * @param str The string to write. Can be null.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(String str, OutputStream stream) throws java.io.IOException {
      Write(str, stream, CBOREncodeOptions.Default);
    }

    /**
     * Writes a text string in CBOR format to a data stream, using the given
     * options to control the encoding process.
     * @param str The string to write. Can be null.
     * @param stream A writable data stream.
     * @param options Options for encoding the data to CBOR.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
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
          long codePointLength = DataUtilities.GetUtf8Length(str, true);
          WritePositiveInt64(3, codePointLength, stream);
          DataUtilities.WriteUtf8(str, stream, true);
        } else {
          WriteStreamedString(str, stream);
        }
      }
    }

    /**
     * Writes a binary floating-point number in CBOR format to a data stream, as
     * though it were converted to a CBOR object via
     * CBORObject.FromObject(EFloat) and then written out.
     * @param bignum An arbitrary-precision binary floating-point number. Can be
     * null.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
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

    /**
     * Writes a rational number in CBOR format to a data stream, as though it were
     * converted to a CBOR object via CBORObject.FromObject(ERational) and
     * then written out.
     * @param rational An arbitrary-precision rational number. Can be null.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
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

    /**
     * Writes a decimal floating-point number in CBOR format to a data stream, as
     * though it were converted to a CBOR object via
     * CBORObject.FromObject(EDecimal) and then written out.
     * @param bignum The arbitrary-precision decimal number to write. Can be null.
     * @param stream InputStream to write to.
     * @throws NullPointerException The parameter {@code stream} is null.
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

    /**
     * Writes a arbitrary-precision integer in CBOR format to a data stream.
     * @param bigint Arbitrary-precision integer to write. Can be null.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
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
     * @throws NullPointerException The parameter {@code stream} is null.
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
        value = -value; // Will never overflow
        WritePositiveInt64(1, value, stream);
      }
    }

    /**
     * Writes a 32-bit signed integer in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
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

    /**
     * Writes a 16-bit signed integer in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public static void Write(short value, OutputStream stream) throws java.io.IOException {
      Write((long)value, stream);
    }

    /**
     * Writes a Boolean value in CBOR format to a data stream.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
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
     * less than 24, writes that byte. If the value is 25 to 255, writes
     * the byte 24, then this byte's value.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
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
     * Writes a 32-bit floating-point number in CBOR format to a data stream. The
     * number is written using the shortest floating-point encoding
     * possible; this is a change from previous versions.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
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

    /**
     * Writes a 64-bit floating-point number in CBOR format to a data stream. The
     * number is written using the shortest floating-point encoding
     * possible; this is a change from previous versions.
     * @param value The value to write.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
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

    /**
     * Writes a CBOR object to a CBOR data stream.
     * @param value The value to write. Can be null.
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
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
     * <p>Writes a CBOR object to a CBOR data stream. See the three-parameter Write
     * method that takes a CBOREncodeOptions.</p>
     * @param objValue The arbitrary object to be serialized. Can be null.
     * @param stream A writable data stream.
     */
    public static void Write(Object objValue, OutputStream stream) throws java.io.IOException {
      Write(objValue, stream, CBOREncodeOptions.Default);
    }

    /**
     * Writes an arbitrary object to a CBOR data stream, using the specified
     * options for controlling how the object is encoded to CBOR data
     * format. If the object is convertible to a CBOR map or a CBOR object
     * that contains CBOR maps, the order in which the keys to those maps
     * are written out to the data stream is undefined unless the map was
     * created using the NewOrderedMap method. The example code given in
     * <see cref='PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can
     * be used to write out certain keys of a CBOR map in a given order.
     * Currently, the following objects are supported: <ul> <li>Lists of
     * CBORObject.</li> <li>Maps of CBORObject. The order in which the keys
     * to the map are written out to the data stream is undefined unless
     * the map was created using the NewOrderedMap method.</li>
     * <li>Null.</li> <li>Byte arrays, which will always be written as
     * definite-length byte strings.</li> <li>string objects. The strings
     * will be encoded using definite-length encoding regardless of their
     * length.</li> <li>Any object accepted by the FromObject static
     * methods.</li></ul>
     * @param objValue The arbitrary object to be serialized. Can be null.
     * <p><b>NOTE:</b> For security reasons, whenever possible, an
     * application should not base this parameter on user input or other
     * externally supplied data unless the application limits this
     * parameter's inputs to types specially handled by this method (such
     * as {@code int} or {@code string}) and/or to plain-old-data types
     * (POCO or POJO types) within the control of the application. If the
     * plain-old-data type references other data types, those types should
     * likewise meet either criterion above.</p>.
     * @param output A writable data stream.
     * @param options CBOR options for encoding the CBOR object to bytes.
     * @throws IllegalArgumentException The object's type is not supported.
     * @throws NullPointerException The parameter {@code options} or {@code
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
      if (options.getCtap2Canonical()) {
        FromObject(objValue).WriteTo(output, options);
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
      FromObject(objValue).WriteTo(output, options);
    }

    /**
     * Converts an arbitrary object to a text string in JavaScript object Notation
     * (JSON) format, as in the ToJSONString method, and writes that string
     * to a data stream in UTF-8. If the object is convertible to a CBOR
     * map, or to a CBOR object that contains CBOR maps, the order in which
     * the keys to those maps are written out to the JSON string is
     * undefined unless the map was created using the NewOrderedMap method.
     * The example code given in
     * <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
     * can be used to write out certain keys of a CBOR map in a given order
     * to a JSON string.
     * @param obj The parameter {@code obj} is an arbitrary object. Can be null.
     * <p><b>NOTE:</b> For security reasons, whenever possible, an
     * application should not base this parameter on user input or other
     * externally supplied data unless the application limits this
     * parameter's inputs to types specially handled by this method (such
     * as {@code int} or {@code string}) and/or to plain-old-data types
     * (POCO or POJO types) within the control of the application. If the
     * plain-old-data type references other data types, those types should
     * likewise meet either criterion above.</p>.
     * @param outputStream A writable data stream.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
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

    /**
     * Gets this object's absolute value.
     * @return This object's absolute without its negative sign.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     * @deprecated Instead, convert this object to a number \u0028with\u0020.getAsNumber()\u0028)),
 * and use that number's.getAbs()\u0028) method.
 */
@Deprecated
    public CBORObject Abs() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      if (cn == null) {
        throw new IllegalStateException("This Object is not a number.");
      }
      Object oldItem = cn.GetValue();
      Object newItem = cn.GetNumberInterface().Abs(oldItem);
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
      return (rat != null) ? CBORObject.FromObject(rat) : ((oldItem ==
            newItem) ? this : CBORObject.FromObject(newItem));
    }

    /**
     * <p>Adds a new key and its value to this CBOR map, or adds the value if the
     * key doesn't exist.</p> <p>NOTE: This method can't be used to add a
     * tag to an existing CBOR object. To create a CBOR object with a given
     * tag, call the <code>CBORObject.FromObjectAndTag</code> method and pass the
     * CBOR object and the desired tag number to that method.</p>
     * @param key An object representing the key, which will be converted to a
     * CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @param valueOb An object representing the value, which will be converted to
     * a CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @return This instance.
     * @throws IllegalArgumentException The parameter {@code key} already exists in this
     * map.
     * @throws IllegalStateException This object is not a map.
     * @throws IllegalArgumentException The parameter {@code key} or {@code valueOb} has
     * an unsupported type.
     */
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

    /**
     * <p>Adds a new object to the end of this array. (Used to throw
     * NullPointerException on a null reference, but now converts the null
     * reference to CBORObject.Null, for convenience with the object
     * overload of this method).</p> <p>NOTE: This method can't be used to
     * add a tag to an existing CBOR object. To create a CBOR object with a
     * given tag, call the <code>CBORObject.FromObjectAndTag</code> method and
     * pass the CBOR object and the desired tag number to that
     * method.</p><p> <p>The following example creates a CBOR array and
     * adds several CBOR objects, one of which has a custom CBOR tag, to
     * that array. Note the chaining behavior made possible by this
     *  method.</p> <pre>CBORObject obj = CBORObject.NewArray() .Add(CBORObject.False) .Add(CBORObject.FromObject(5)) .Add(CBORObject.FromObject("text string")) .Add(CBORObject.FromObjectAndTag(9999, 1));</pre> . </p>
     * @param obj The parameter {@code obj} is a CBOR object.
     * @return This instance.
     * @throws IllegalStateException This object is not an array.
     */
    public CBORObject Add(CBORObject obj) {
      if (this.getType() == CBORType.Array) {
        List<CBORObject> list = this.AsList();
        list.add(obj);
        return this;
      }
      throw new IllegalStateException("Not an array");
    }

    /**
     * <p>Converts an object to a CBOR object and adds it to the end of this
     * array.</p> <p>NOTE: This method can't be used to add a tag to an
     * existing CBOR object. To create a CBOR object with a given tag, call
     * the <code>CBORObject.FromObjectAndTag</code> method and pass the CBOR
     * object and the desired tag number to that method.</p><p> <p>The
     * following example creates a CBOR array and adds several CBOR
     * objects, one of which has a custom CBOR tag, to that array. Note the
     *  chaining behavior made possible by this method.</p> <pre>CBORObject obj = CBORObject.NewArray() .Add(CBORObject.False) .Add(5) .Add("text string") .Add(CBORObject.FromObjectAndTag(9999, 1));</pre> . </p>
     * @param obj A CBOR object (or an object convertible to a CBOR object) to add
     * to this CBOR array.
     * @return This instance.
     * @throws IllegalStateException This instance is not an array.
     * @throws IllegalArgumentException The type of {@code obj} is not supported.
     */
    public CBORObject Add(Object obj) {
      if (this.getType() == CBORType.Array) {
        List<CBORObject> list = this.AsList();
        list.add(CBORObject.FromObject(obj));
        return this;
      }
      throw new IllegalStateException("Not an array");
    }

    /**
     * Converts this object to an arbitrary-precision integer. See the ToObject
     * overload taking a type for more information.
     * @return The closest arbitrary-precision integer to this object.
     * @throws IllegalStateException This object does not represent a number (for
     * the purposes of this method, infinity and not-a-number values, but
     * not {@code CBORObject.Null}, are considered numbers).
     * @throws ArithmeticException This object's value is infinity or not-a-number
     * (NaN).
     * @deprecated Instead, use.getToObject()&amp;lt;PeterO.Numbers.EInteger&amp;gt;\u0028)
 * in\u0020.NET or
 * \u0020.getToObject()\u0028com.upokecenter.numbers.EInteger.class)
 * in\u0020Java.
 */
@Deprecated
    public EInteger AsEInteger() {
      return (EInteger)this.ToObject(EInteger.class);
    }

    /**
     * Returns false if this object is a CBOR false, null, or undefined value
     * (whether or not the object has tags); otherwise, true.
     * @return False if this object is a CBOR false, null, or undefined value;
     * otherwise, true.
     */
    public boolean AsBoolean() {
      return !this.isFalse() && !this.isNull() && !this.isUndefined();
    }

    /**
     * Converts this object to a byte (0 to 255). Floating point values are
     * converted to integers by discarding their fractional parts.
     * @return The closest byte-sized integer to this object.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     * @throws ArithmeticException This object's value exceeds the range of a byte
     * (would be less than 0 or greater than 255 when converted to an
     * integer by discarding its fractional part).
     * @deprecated Instead, use.getToObject()&amp;lt;byte&amp;gt;\u0028) in\u0020.NET or
 * \u0020.getToObject()\u0028Byte.class) in\u0020Java.
 */
@Deprecated
    public byte AsByte() {
      return (byte)this.AsInt32(0, 255);
    }

    byte AsByteLegacy() {
      return (byte)this.AsInt32(0, 255);
    }

    /**
     * Converts this object to a 64-bit floating point number.
     * @return The closest 64-bit floating point number to this object. The return
     * value can be positive infinity or negative infinity if this value
     * exceeds the range of a 64-bit floating point number.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     */
    public double AsDouble() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      if (cn == null) {
        throw new IllegalStateException("Not a number type");
      }
      return cn.GetNumberInterface().AsDouble(cn.GetValue());
    }

    /**
     * Converts this object to a decimal number.
     * @return A decimal number for this object's value.
     * @throws IllegalStateException This object does not represent a number (for
     * the purposes of this method, infinity and not-a-number values, but
     * not {@code CBORObject.Null}, are considered numbers).
     * @deprecated Instead, use.getToObject()&amp;lt;PeterO.Numbers.EDecimal&amp;gt;\u0028)
 * in\u0020.NET or
 * \u0020.getToObject()\u0028com.upokecenter.numbers.EDecimal.class)
 * in\u0020Java.
 */
@Deprecated
    public EDecimal AsEDecimal() {
      return (EDecimal)this.ToObject(EDecimal.class);
    }

    /**
     * Converts this object to an arbitrary-precision binary floating point number.
     * See the ToObject overload taking a type for more information.
     * @return An arbitrary-precision binary floating-point number for this
     * object's value.
     * @throws IllegalStateException This object does not represent a number (for
     * the purposes of this method, infinity and not-a-number values, but
     * not {@code CBORObject.Null}, are considered numbers).
     * @deprecated Instead, use.getToObject()&amp;lt;PeterO.Numbers.EFloat&amp;gt;\u0028) in.NET
 * or \u0020.getToObject()\u0028com.upokecenter.numbers.EFloat.class)
 * in\u0020Java.
 */
@Deprecated
    public EFloat AsEFloat() {
      return (EFloat)this.ToObject(EFloat.class);
    }

    /**
     * Converts this object to a rational number. See the ToObject overload taking
     * a type for more information.
     * @return A rational number for this object's value.
     * @throws IllegalStateException This object does not represent a number (for
     * the purposes of this method, infinity and not-a-number values, but
     * not {@code CBORObject.Null}, are considered numbers).
     * @deprecated Instead, use.getToObject()&amp;lt;PeterO.Numbers.ERational&amp;gt;\u0028) in
 *.NET\u0020or.getToObject()\u0028com.upokecenter.numbers.ERational.class)
 * in\u0020Java.
 */
@Deprecated
    public ERational AsERational() {
      return (ERational)this.ToObject(ERational.class);
    }

    /**
     * Converts this object to a 16-bit signed integer. Floating point values are
     * converted to integers by discarding their fractional parts.
     * @return The closest 16-bit signed integer to this object.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     * @throws ArithmeticException This object's value exceeds the range of a 16-bit
     * signed integer.
     * @deprecated Instead, use the following:\u0020\u0028cbor.AsNumber().ToInt16Checked()),
 * or\u0020.getToObject()&amp;lt;short&amp;gt;() in\u0020.NET.
 */
@Deprecated
    public short AsInt16() {
      return (short)this.AsInt32(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    /**
     * Converts this object to a 32-bit signed integer if this CBOR object's type
     * is Integer. This method disregards the tags this object has, if
     * any.<p> <p>The following example code (originally written in C# for
     * the.NET Framework) shows a way to check whether a given CBOR object
     * stores a 32-bit signed integer before getting its value.</p>
     *  <pre>CBORObject obj = CBORObject.FromInt32(99999); if (obj.CanValueFitInInt32()) { /* Not an Int32; handle the error &#x2a;&#x2f; System.out.println("Not a 32-bit integer."); } else { System.out.println("The value is " + obj.AsInt32Value()); }</pre> .
     * </p>
     * @return The 32-bit signed integer stored by this object.
     * @throws IllegalStateException This object's type is not {@code
     * CBORType.Integer}.
     * @throws ArithmeticException This object's value exceeds the range of a 32-bit
     * signed integer.
     */
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

    /**
     * Converts this object to a 64-bit signed integer if this CBOR object's type
     * is Integer. This method disregards the tags this object has, if
     * any.<p> <p>The following example code (originally written in C# for
     * the.NET Framework) shows a way to check whether a given CBOR object
     * stores a 64-bit signed integer before getting its value.</p>
     *  <pre>CBORObject obj = CBORObject.FromInt64(99999); if (obj.CanValueFitInInt64()) { &#x2f;&#x2a; Not an Int64; handle the error&#x2a;&#x2f; System.out.println("Not a 64-bit integer."); } else { System.out.println("The value is " + obj.AsInt64Value()); }</pre> . </p>
     * @return The 64-bit signed integer stored by this object.
     * @throws IllegalStateException This object's type is not {@code
     * CBORType.Integer}.
     * @throws ArithmeticException This object's value exceeds the range of a 64-bit
     * signed integer.
     */
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

    /**
     * Returns whether this CBOR object stores an integer (CBORType.Integer) within
     * the range of a 64-bit signed integer. This method disregards the
     * tags this object has, if any.
     * @return {@code true} if this CBOR object stores an integer
     * (CBORType.Integer) whose value is at least -(2^63) and less than
     * 2^63; otherwise, {@code false}.
     */
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

    /**
     * Returns whether this CBOR object stores an integer (CBORType.Integer) within
     * the range of a 32-bit signed integer. This method disregards the
     * tags this object has, if any.
     * @return {@code true} if this CBOR object stores an integer
     * (CBORType.Integer) whose value is at least -(2^31) and less than
     * 2^31; otherwise, {@code false}.
     */
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
        default:
          return false;
      }
    }

    /**
     * Converts this object to an arbitrary-precision integer if this CBOR object's
     * type is Integer. This method disregards the tags this object has, if
     * any. (Note that CBOR stores untagged integers at least -(2^64) and
     * less than 2^64.).
     * @return The integer stored by this object.
     * @throws IllegalStateException This object's type is not {@code
     * CBORType.Integer}.
     */
    public EInteger AsEIntegerValue() {
      switch (this.getItemType()) {
        case CBORObjectTypeInteger:
          return EInteger.FromInt64((((Long)this.getThisItem()).longValue()));
        case CBORObjectTypeEInteger:
          return (EInteger)this.getThisItem();
        default: throw new IllegalStateException("Not an integer type");
      }
    }

    /**
     * Converts this object to the bits of a 64-bit floating-point number if this
     * CBOR object's type is FloatingPoint. This method disregards the tags
     * this object has, if any.
     * @return The bits of a 64-bit floating-point number stored by this object.
     * The most significant bit is the sign (set means negative, clear
     * means nonnegative); the next most significant 11 bits are the
     * exponent area; and the remaining bits are the significand area. If
     * all the bits of the exponent area are set and the significand area
     * is 0, this indicates infinity. If all the bits of the exponent area
     * are set and the significand area is other than 0, this indicates
     * not-a-number (NaN).
     * @throws IllegalStateException This object's type is not {@code
     * CBORType.FloatingPoint}.
     */
    public long AsDoubleBits() {
      switch (this.getType()) {
        case FloatingPoint:
          return ((Long)this.getThisItem()).longValue();
        default: throw new IllegalStateException("Not a floating-point" +
            "\u0020type");
      }
    }

    /**
     * Converts this object to a 64-bit floating-point number if this CBOR object's
     * type is FloatingPoint. This method disregards the tags this object
     * has, if any.
     * @return The 64-bit floating-point number stored by this object.
     * @throws IllegalStateException This object's type is not {@code
     * CBORType.FloatingPoint}.
     */
    public double AsDoubleValue() {
      switch (this.getType()) {
        case FloatingPoint:
          return CBORUtilities.Int64BitsToDouble((((Long)this.getThisItem()).longValue()));
        default: throw new IllegalStateException("Not a floating-point" +
            "\u0020type");
      }
    }

    /**
     * Converts this object to a CBOR number. (NOTE: To determine whether this
     * method call can succeed, call the <b>IsNumber</b> property
     * (isNumber() method in Java) before calling this method.).
     * @return The number represented by this object.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     */
    public CBORNumber AsNumber() {
      CBORNumber num = CBORNumber.FromCBORObject(this);
      if (num == null) {
        throw new IllegalStateException("Not a number type");
      }
      return num;
    }

    /**
     * Converts this object to a 32-bit signed integer. Non-integer number values
     * are converted to integers by discarding their fractional parts.
     * (NOTE: To determine whether this method call can succeed, call
     * <b>AsNumber().getCanTruncatedIntFitInInt32()</b> before calling this
     * method. See the example.).<p> <p>The following example code
     * (originally written in C# for the.NET Framework) shows a way to
     * check whether a given CBOR object stores a 32-bit signed integer
     *  before getting its value.</p> <pre>CBORObject obj = CBORObject.FromInt32(99999); if (obj.AsNumber().CanTruncatedIntFitInInt32()) { &#x2f;&#x2a; Not an Int32; handle the error &#x2a;&#x2f; System.out.println("Not a 32-bit integer."); } else { System.out.println("The value is " + obj.AsInt32()); }</pre> . </p>
     * @return The closest 32-bit signed integer to this object.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     * @throws ArithmeticException This object's value exceeds the range of a 32-bit
     * signed integer.
     */
    public int AsInt32() {
      return this.AsInt32(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Converts this object to a 64-bit signed integer. Non-integer numbers are
     * converted to integers by discarding their fractional parts. (NOTE:
     * To determine whether this method call can succeed, call
     * <b>AsNumber().getCanTruncatedIntFitInInt64()</b> before calling this
     * method. See the example.).<p> <p>The following example code
     * (originally written in C# for the.NET Framework) shows a way to
     * check whether a given CBOR object stores a 64-bit signed integer
     *  before getting its value.</p> <pre>CBORObject obj = CBORObject.FromInt64(99999); if (obj.isIntegral() &amp;&amp; obj.AsNumber().CanFitInInt64()) { &#x2f;&#x2a; Not an Int64; handle the error &#x2a;&#x2f; System.out.println("Not a 64-bit integer."); } else { System.out.println("The value is " + obj.AsInt64()); }</pre> . </p>
     * @return The closest 64-bit signed integer to this object.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     * @throws ArithmeticException This object's value exceeds the range of a 64-bit
     * signed integer.
     * @deprecated Instead, use the following:\u0020\u0028cbor.AsNumber().ToInt64Checked()), or
 *.ToObject&amp;lt;long&amp;gt;()\u0020in.NET.
 */
@Deprecated
    public long AsInt64() {
      CBORNumber cn = this.AsNumber();
      return cn.GetNumberInterface().AsInt64(cn.GetValue());
    }

    /**
     * Converts this object to a 32-bit floating point number.
     * @return The closest 32-bit floating point number to this object. The return
     * value can be positive infinity or negative infinity if this object's
     * value exceeds the range of a 32-bit floating point number.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     */
    public float AsSingle() {
      CBORNumber cn = this.AsNumber();
      return cn.GetNumberInterface().AsSingle(cn.GetValue());
    }

    /**
     * Gets the value of this object as a text string.<p>This method is not the
     *  "reverse" of the <code>FromObject</code> method in the sense that
     * FromObject can take either a text string or <code>null</code>, but this
     * method can accept only text strings. The <code>ToObject</code> method is
     *  closer to a "reverse" version to <code>FromObject</code> than the
     * <code>AsString</code> method: <code>ToObject&lt;string&gt;(cbor)</code> in
     * DotNet, or <code>ToObject(string.class)</code> in Java, will convert a
     * CBOR object to a DotNet or Java string if it represents a text
     * string, or to <code>null</code> if <code>IsNull</code> returns <code>true</code> for
     * the CBOR object, and will fail in other cases.</p>
     * @return Gets this object's string.
     * @throws IllegalStateException This object's type is not a text string (for
     * the purposes of this method, infinity and not-a-number values, but
     * not {@code CBORObject.Null}, are considered numbers). To check the
     * CBOR object for null before conversion, use the following idiom
     * (originally written in C# for the.NET version): {@code (cbor == null
     * || cbor.isNull()) ? null : cbor.AsString()}.
     */
    public String AsString() {
      int type = this.getItemType();
      switch (type) {
        case CBORObjectTypeTextString:
        case CBORObjectTypeTextStringAscii: {
          return (String)this.getThisItem();
        }
        case CBORObjectTypeTextStringUtf8: {
          return DataUtilities.GetUtf8String((byte[])this.getThisItem(), false);
        }
        default:
          throw new IllegalStateException("Not a text String type");
      }
    }

    /**
     * Returns whether this object's value can be converted to a 64-bit floating
     * point number without its value being rounded to another numerical
     * value.
     * @return {@code true} if this object's value can be converted to a 64-bit
     * floating point number without its value being rounded to another
     * numerical value, or if this is a not-a-number value, even if the
     * value's diagnostic information can't fit in a 64-bit floating point
     * number; otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().CanFitInDouble()).
 */
@Deprecated
    public boolean CanFitInDouble() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      return (cn != null) &&
        cn.GetNumberInterface().CanFitInDouble(cn.GetValue());
    }

    /**
     * Returns whether this object's numerical value is an integer, is -(2^31) or
     * greater, and is less than 2^31.
     * @return {@code true} if this object's numerical value is an integer, is
     * -(2^31) or greater, and is less than 2^31; otherwise, {@code false}.
     * @deprecated Instead, use.CanValueFitInInt32(), if the application allows\u0020only CBOR
 * integers, or \u0028cbor.isNumber()
 * &amp;&amp;cbor.AsNumber().CanFitInInt32()), \u0020 if the application
 * allows any CBOR Object convertible to an integer.
 */
@Deprecated
    public boolean CanFitInInt32() {
      if (!this.CanFitInInt64()) {
        return false;
      }
      long v = this.AsInt64();
      return v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE;
    }

    /**
     * Returns whether this object's numerical value is an integer, is -(2^63) or
     * greater, and is less than 2^63.
     * @return {@code true} if this object's numerical value is an integer, is
     * -(2^63) or greater, and is less than 2^63; otherwise, {@code false}.
     * @deprecated Instead, use CanValueFitInInt64(), if the application allows\u0020only CBOR
 * integers, or \u0028cbor.isNumber()
 * &amp;&amp;cbor.AsNumber().CanFitInInt64()), \u0020 if the application
 * allows any CBOR Object convertible to an integer.
 */
@Deprecated
    public boolean CanFitInInt64() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      return (cn != null) &&
        cn.GetNumberInterface().CanFitInInt64(cn.GetValue());
    }

    /**
     * Returns whether this object's value can be converted to a 32-bit floating
     * point number without its value being rounded to another numerical
     * value.
     * @return {@code true} if this object's value can be converted to a 32-bit
     * floating point number without its value being rounded to another
     * numerical value, or if this is a not-a-number value, even if the
     * value's diagnostic information can' t fit in a 32-bit floating point
     * number; otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().CanFitInSingle()).
 */
@Deprecated
    public boolean CanFitInSingle() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      return (cn != null) &&
        cn.GetNumberInterface().CanFitInSingle(cn.GetValue());
    }

    /**
     * Returns whether this object's value, converted to an integer by discarding
     * its fractional part, would be -(2^31) or greater, and less than
     * 2^31.
     * @return {@code true} if this object's value, converted to an integer by
     * discarding its fractional part, would be -(2^31) or greater, and
     * less than 2^31; otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.CanValueFitInInt32()\u0020if only
 * integers of any tag are allowed, or\u0020\u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().CanTruncatedIntFitInInt32()).
 */
@Deprecated
    public boolean CanTruncatedIntFitInInt32() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      return (cn != null) &&
        cn.GetNumberInterface().CanTruncatedIntFitInInt32(cn.GetValue());
    }

    /**
     * Returns whether this object's value, converted to an integer by discarding
     * its fractional part, would be -(2^63) or greater, and less than
     * 2^63.
     * @return {@code true} if this object's value, converted to an integer by
     * discarding its fractional part, would be -(2^63) or greater, and
     * less than 2^63; otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.CanValueFitInInt64()\u0020if only
 * integers of any tag are allowed, or\u0020\u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().CanTruncatedIntFitInInt64()).
 */
@Deprecated
    public boolean CanTruncatedIntFitInInt64() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      return cn != null &&
        cn.GetNumberInterface().CanTruncatedIntFitInInt64(cn.GetValue());
    }

    private static String Chop(String str) {
      return str.substring(0, Math.min(100, str.length()));
    }

    /**
     * Compares two CBOR objects. This implementation was changed in version 4.0.
     * <p>In this implementation:</p> <ul> <li>The null pointer (null
     * reference) is considered less than any other object.</li> <li>If the
     * two objects are both integers (CBORType.Integer) both floating-point
     * values, both byte strings, both simple values (including True and
     * False), or both text strings, their CBOR encodings (as though
     * EncodeToBytes were called on each integer) are compared as though by
     * a byte-by-byte comparison. (This means, for example, that positive
     * integers sort before negative integers).</li> <li>If both objects
     * have a tag, they are compared first by the tag's value then by the
     * associated item (which itself can have a tag).</li> <li>If both
     * objects are arrays, they are compared item by item. In this case, if
     * the arrays have different numbers of items, the array with more
     * items is treated as greater than the other array.</li> <li>If both
     * objects are maps, their key-value pairs, sorted by key in accordance
     * with this method, are compared, where each pair is compared first by
     * key and then by value. In this case, if the maps have different
     * numbers of key-value pairs, the map with more pairs is treated as
     * greater than the other map.</li> <li>If the two objects have
     * different types, the object whose type comes first in the order of
     * untagged integers, untagged byte strings, untagged text strings,
     * untagged arrays, untagged maps, tagged objects, untagged simple
     * values (including True and False) and untagged floating point values
     * sorts before the other object.</li></ul> <p>This method is
     * consistent with the Equals method.</p>
     * @param other A value to compare with.
     * @return A negative number, if this value is less than the other object; or
     * 0, if both values are equal; or a positive number, if this value is
     * less than the other object or if the other object is null. This
     * implementation returns a positive number if.
     */
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
     * @param objKey The parameter {@code objKey} is an arbitrary object.
     * @return {@code true} if the given key is found, or {@code false} if the
     * given key is not found or this object is not a map.
     */
    public boolean ContainsKey(Object objKey) {
      return (this.getType() == CBORType.Map) ?
        this.ContainsKey(CBORObject.FromObject(objKey)) : false;
    }

    /**
     * Determines whether a value of the given key exists in this object.
     * @param key An object that serves as the key. If this is {@code null}, checks
     * for {@code CBORObject.Null}.
     * @return {@code true} if the given key is found, or {@code false} if the
     * given key is not found or this object is not a map.
     */
    public boolean ContainsKey(CBORObject key) {
      key = (key == null) ? (CBORObject.Null) : key;
      if (this.getType() == CBORType.Map) {
        Map<CBORObject, CBORObject> map = this.AsMap();
        return map.containsKey(key);
      }
      return false;
    }

    /**
     * Determines whether a value of the given key exists in this object.
     * @param key A text string that serves as the key. If this is {@code null},
     * checks for {@code CBORObject.Null}.
     * @return {@code true} if the given key (as a CBOR object) is found, or {@code
     * false} if the given key is not found or this object is not a map.
     */
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

    /**
     * <p>Writes the binary representation of this CBOR object and returns a byte
     * array of that representation. If the CBOR object contains CBOR maps,
     * or is a CBOR map itself, the order in which the keys to the map are
     * written out to the byte array is undefined unless the map was
     * created using the NewOrderedMap method. The example code given in
     * <see cref='PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can
     * be used to write out certain keys of a CBOR map in a given order.
     * For the CTAP2 (FIDO Client-to-Authenticator Protocol 2) canonical
     * ordering, which is useful for implementing Web Authentication, call
     *  <code>EncodeToBytes(new CBOREncodeOptions("ctap2canonical=true"))</code>
     * rather than this method.</p>
     * @return A byte array in CBOR format.
     */
    public byte[] EncodeToBytes() {
      return this.EncodeToBytes(CBOREncodeOptions.Default);
    }

    /**
     * Writes the binary representation of this CBOR object and returns a byte
     * array of that representation, using the specified options for
     * encoding the object to CBOR format. For the CTAP2 (FIDO
     * Client-to-Authenticator Protocol 2) canonical ordering, which is
     * useful for implementing Web Authentication, call this method as
     * follows: <code>EncodeToBytes(new
     *  CBOREncodeOptions("ctap2canonical=true"))</code>.
     * @param options Options for encoding the data to CBOR.
     * @return A byte array in CBOR format.
     * @throws NullPointerException The parameter {@code options} is null.
     */
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
          tagbyte = (byte)(0xc0 + (int)this.tagLow);
        }
      }
      if (!hasComplexTag) {
        switch (this.getItemType()) {
          case CBORObjectTypeTextString:
          case CBORObjectTypeTextStringAscii: {
            byte[] ret = GetOptimizedBytesIfShortAscii(
                this.AsString(), tagged ? (((int)tagbyte) & 0xff) : -1);
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
              byte[] simpleBytes = new byte[] { tagbyte, (byte)0xf4 };
              if (this.isFalse()) {
                simpleBytes[1] = (byte)0xf4;
                return simpleBytes;
              }
              if (this.isTrue()) {
                simpleBytes[1] = (byte)0xf5;
                return simpleBytes;
              }
              if (this.isNull()) {
                simpleBytes[1] = (byte)0xf6;
                return simpleBytes;
              }
              if (this.isUndefined()) {
                simpleBytes[1] = (byte)0xf7;
                return simpleBytes;
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
            byte[] intBytes = null;
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
            if (options.getFloat64()) {
            return GetDoubleBytes64(
                this.AsDoubleBits(),
                ((int)tagbyte) & 0xff);
            } else {
            return GetDoubleBytes(
                this.AsDoubleBits(),
                ((int)tagbyte) & 0xff);
            }
          }
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

    /**
     * Gets the CBOR object referred to by a JSON Pointer according to RFC6901. For
     * more information, see the overload taking a default value parameter.
     * @param pointer A JSON pointer according to RFC 6901.
     * @return An object within this CBOR object. Returns this object if pointer is
     * the empty string (even if this object has a CBOR type other than
     * array or map).
     * @throws com.upokecenter.cbor.CBORException Thrown if the pointer is null, or
     * if the pointer is invalid, or if there is no object at the given
     *  pointer, or the special key "-" appears in the pointer, or if the
     * pointer is non-empty and this object has a CBOR type other than
     * array or map.
     */
    public CBORObject AtJSONPointer(String pointer) {
      CBORObject ret = this.AtJSONPointer(pointer, null);
      if (ret == null) {
         throw new CBORException("Invalid JSON pointer");
      }
      return ret;
    }

    /**
     * Gets the CBOR object referred to by a JSON Pointer according to RFC6901, or
     * a default value if the operation fails. The syntax for a JSON
     * Pointer is: <pre>'/' KEY '/' KEY.get(...)</pre> where KEY represents
     * a key into the JSON object or its sub-objects in the hierarchy. For
     * example, <pre>/foo/2/bar</pre> means the same as
     *  <pre>obj.get('foo')[2]['bar']</pre> in JavaScript. If "~" and/or "/"
     *  occurs in a key, it must be escaped with "~0" or "~1", respectively,
     *  in a JSON pointer. JSON pointers also support the special key "-"
     *  (as in "/foo/-") to indicate the end of an array, but this method
     * treats this key as an error since it refers to a nonexistent item.
     * Indices to arrays (such as 2 in the example) must contain only basic
     * digits 0 to 9 and no leading zeros. (Note that RFC 6901 was
     * published before JSON was extended to support top-level values other
     * than arrays and key-value dictionaries.).
     * @param pointer A JSON pointer according to RFC 6901.
     * @param defaultValue The parameter {@code defaultValue} is a Cbor.CBORObject
     * object.
     * @return An object within the specified JSON object. Returns this object if
     * pointer is the empty string (even if this object has a CBOR type
     * other than array or map). Returns {@code defaultValue} if the
     * pointer is null, or if the pointer is invalid, or if there is no
     *  object at the given pointer, or the special key "-" appears in the
     * pointer, or if the pointer is non-empty and this object has a CBOR
     * type other than array or map.
     */
    public CBORObject AtJSONPointer(String pointer, CBORObject defaultValue) {
      return JSONPointer.GetObject(this, pointer, null);
    }

  /**
   * Returns a copy of this object after applying the operations in a JSON patch,
   * in the form of a CBOR object. JSON patches are specified in RFC 6902
   * and their format is summarized in the remarks below.<p><b>Remarks:</b>
   * A JSON patch is an array with one or more maps. Each map has the
   *  following keys: <ul> <li>"op" - Required. This key's value is the
   *  patch operation and must be "add", "remove", "move", "copy", "test",
   *  or "replace", in basic lower case letters and no other case
   *  combination.</li> <li>"value" - Required if the operation is "add",
   *  "replace", or "test" and specifies the item to add (insert), or that
   * will replace the existing item, or to check an existing item for
   *  equality, respectively. (For "test", the operation fails if the
   *  existing item doesn't match the specified value.)</li> <li>"path" -
   * Required for all operations. A JSON Pointer (RFC 6901) specifying the
   * destination path in the CBOR object for the operation. For more
   * information, see RFC 6901 or the documentation for
   *  AtJSONPointer(pointer, defaultValue).</li> <li>"from" - Required if
   *  the operation is "move" or "copy". A JSON Pointer (RFC 6901)
   * specifying the path in the CBOR object where the source value is
   * located.</li></ul></p>
   * @param patch A JSON patch in the form of a CBOR object; it has the form
   * summarized in the remarks.
   * @return The result of the patch operation.
   * @throws com.upokecenter.cbor.CBORException The parameter {@code patch} is
   * null or the patch operation failed.
   */
    public CBORObject ApplyJSONPatch(CBORObject patch) {
      return JSONPatch.Patch(this, patch);
    }

    /**
     * Determines whether this object and another object are equal and have the
     * same type. Not-a-number values can be considered equal by this
     * method.
     * @param obj The parameter {@code obj} is an arbitrary object.
     * @return {@code true} if the objects are equal; otherwise, {@code false}. In
     * this method, two objects are not equal if they don't have the same
     * type or if one is null and the other isn't.
     */
    @Override public boolean equals(Object obj) {
      return this.equals(((obj instanceof CBORObject) ? (CBORObject)obj : null));
    }

    /**
     * Compares the equality of two CBOR objects. Not-a-number values can be
     * considered equal by this method.
     * @param other The object to compare.
     * @return {@code true} if the objects are equal; otherwise, {@code false}. In
     * this method, two objects are not equal if they don't have the same
     * type or if one is null and the other isn't.
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
          Map<CBORObject, CBORObject> cbordict =
            ((otherValue.itemValue instanceof Map<?, ?>) ? (Map<CBORObject, CBORObject>)otherValue.itemValue : null);
          return CBORMapEquals(this.AsMap(), cbordict);
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

    /**
     * Gets the backing byte array used in this CBOR object, if this object is a
     * byte string, without copying the data to a new byte array. Any
     * changes in the returned array's contents will be reflected in this
     * CBOR object. Note, though, that the array's length can't be changed.
     * @return The byte array held by this CBOR object.
     * @throws IllegalStateException This object is not a byte string.
     */
    public byte[] GetByteString() {
      if (this.getItemType() == CBORObjectTypeByteString) {
        return (byte[])this.getThisItem();
      }
      throw new IllegalStateException("Not a byte String");
    }

    /**
     * Calculates the hash code of this object. The hash code for a given instance
     * of this class is not guaranteed to be the same across versions of
     * this class, and no application or process IDs are used in the hash
     * code calculation.
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
     * Returns whether this object has only one tag.
     * @return {@code true} if this object has only one tag; otherwise, {@code
     * false}.
     */
    public boolean HasOneTag() {
      return this.isTagged() && !((CBORObject)this.itemValue).isTagged();
    }

    /**
     * Returns whether this object has only one tag and that tag is the given
     * number.
     * @param tagValue The tag number.
     * @return {@code true} if this object has only one tag and that tag is the
     * given number; otherwise, {@code false}.
     * @throws IllegalArgumentException The parameter {@code tagValue} is less than 0.
     */
    public boolean HasOneTag(int tagValue) {
      return this.HasOneTag() && this.HasMostOuterTag(tagValue);
    }

    /**
     * Returns whether this object has only one tag and that tag is the given
     * number, expressed as an arbitrary-precision integer.
     * @param bigTagValue An arbitrary-precision integer.
     * @return {@code true} if this object has only one tag and that tag is the
     * given number; otherwise, {@code false}.
     * @throws NullPointerException The parameter {@code bigTagValue} is null.
     * @throws IllegalArgumentException The parameter {@code bigTagValue} is less than 0.
     */
    public boolean HasOneTag(EInteger bigTagValue) {
      return this.HasOneTag() && this.HasMostOuterTag(bigTagValue);
    }

    /**
     * Gets the number of tags this object has.
     * @return The number of tags this object has.
     */
    public final int getTagCount() {
        int count = 0;
        CBORObject curitem = this;
        while (curitem.isTagged()) {
          count = (count + 1);
          curitem = (CBORObject)curitem.itemValue;
        }
        return count;
      }

    /**
     * Returns whether this object has an innermost tag and that tag is of the
     * given number.
     * @param tagValue The tag number.
     * @return {@code true} if this object has an innermost tag and that tag is of
     * the given number; otherwise, {@code false}.
     * @throws IllegalArgumentException The parameter {@code tagValue} is less than 0.
     */
    public boolean HasMostInnerTag(int tagValue) {
      if (tagValue < 0) {
        throw new IllegalArgumentException("tagValue(" + tagValue +
          ") is less than 0");
      }
      return this.isTagged() && this.HasMostInnerTag(
          EInteger.FromInt32(tagValue));
    }

    /**
     * Returns whether this object has an innermost tag and that tag is of the
     * given number, expressed as an arbitrary-precision number.
     * @param bigTagValue The tag number.
     * @return {@code true} if this object has an innermost tag and that tag is of
     * the given number; otherwise, {@code false}.
     * @throws NullPointerException The parameter {@code bigTagValue} is null.
     * @throws IllegalArgumentException The parameter {@code bigTagValue} is less than 0.
     */
    public boolean HasMostInnerTag(EInteger bigTagValue) {
      if (bigTagValue == null) {
        throw new NullPointerException("bigTagValue");
      }
      if (bigTagValue.signum() < 0) {
        throw new IllegalArgumentException("bigTagValue(" + bigTagValue +
          ") is less than 0");
      }
      return (!this.isTagged()) ? false : this.getMostInnerTag().equals(bigTagValue);
    }

    /**
     * Returns whether this object has an outermost tag and that tag is of the
     * given number.
     * @param tagValue The tag number.
     * @return {@code true} if this object has an outermost tag and that tag is of
     * the given number; otherwise, {@code false}.
     * @throws IllegalArgumentException The parameter {@code tagValue} is less than 0.
     */
    public boolean HasMostOuterTag(int tagValue) {
      if (tagValue < 0) {
        throw new IllegalArgumentException("tagValue(" + tagValue +
          ") is less than 0");
      }
      return this.isTagged() && this.tagHigh == 0 && this.tagLow == tagValue;
    }

    /**
     * Returns whether this object has an outermost tag and that tag is of the
     * given number.
     * @param bigTagValue The tag number.
     * @return {@code true} if this object has an outermost tag and that tag is of
     * the given number; otherwise, {@code false}.
     * @throws NullPointerException The parameter {@code bigTagValue} is null.
     * @throws IllegalArgumentException The parameter {@code bigTagValue} is less than 0.
     */
    public boolean HasMostOuterTag(EInteger bigTagValue) {
      if (bigTagValue == null) {
        throw new NullPointerException("bigTagValue");
      }
      if (bigTagValue.signum() < 0) {
        throw new IllegalArgumentException("bigTagValue(" + bigTagValue +
          ") is less than 0");
      }
      return (!this.isTagged()) ? false : this.getMostOuterTag().equals(bigTagValue);
    }

    /**
     * Returns whether this object has a tag of the given number.
     * @param tagValue The tag value to search for.
     * @return {@code true} if this object has a tag of the given number;
     * otherwise, {@code false}.
     * @throws IllegalArgumentException The parameter {@code tagValue} is less than 0.
     * @throws NullPointerException The parameter {@code tagValue} is null.
     */
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

    /**
     * Returns whether this object has a tag of the given number.
     * @param bigTagValue The tag value to search for.
     * @return {@code true} if this object has a tag of the given number;
     * otherwise, {@code false}.
     * @throws NullPointerException The parameter {@code bigTagValue} is null.
     * @throws IllegalArgumentException The parameter {@code bigTagValue} is less than 0.
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
     * @param index Index starting at 0 to insert at.
     * @param valueOb An object representing the value, which will be converted to
     * a CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @return This instance.
     * @throws IllegalStateException This object is not an array.
     * @throws IllegalArgumentException The parameter {@code valueOb} has an unsupported
     * type; or {@code index} is not a valid index into this array.
     */
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

    /**
     * Gets a value indicating whether this CBOR object represents infinity.
     * @return {@code true} if this CBOR object represents infinity; otherwise,
     * {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().IsInfinity()).
 */
@Deprecated
    public boolean IsInfinity() {
      return this.isNumber() && this.AsNumber().IsInfinity();
    }

    /**
     * Gets a value indicating whether this CBOR object represents a not-a-number
     * value (as opposed to whether this object does not express a number).
     * @return {@code true} if this CBOR object represents a not-a-number value (as
     * opposed to whether this object does not represent a number as
     * defined by the IsNumber property or {@code isNumber()} method in
     * Java); otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().IsNaN()).
 */
@Deprecated
    public boolean IsNaN() {
      return this.isNumber() && this.AsNumber().IsNaN();
    }

    /**
     * Gets a value indicating whether this CBOR object represents negative
     * infinity.
     * @return {@code true} if this CBOR object represents negative infinity;
     * otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().IsNegativeInfinity()).
 */
@Deprecated
    public boolean IsNegativeInfinity() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      return cn != null &&
        cn.GetNumberInterface().IsNegativeInfinity(cn.GetValue());
    }

    /**
     * Gets a value indicating whether this CBOR object represents positive
     * infinity.
     * @return {@code true} if this CBOR object represents positive infinity;
     * otherwise, {@code false}.
     * @deprecated Instead, use the following: \u0028cbor.isNumber()
 * &amp;&amp;\u0020cbor.AsNumber().IsPositiveInfinity()).
 */
@Deprecated
    public boolean IsPositiveInfinity() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      return cn != null &&
        cn.GetNumberInterface().IsPositiveInfinity(cn.GetValue());
    }

    /**
     * Gets this object's value with the sign reversed.
     * @return The reversed-sign form of this number.
     * @throws IllegalStateException This object does not represent a number (for
     * this purpose, infinities and not-a-number or NaN values, but not
     * CBORObject.Null, are considered numbers).
     * @deprecated Instead, convert this object to a number \u0028with\u0020.AsNumber()), and
 * use that number's.Negate() method.
 */
@Deprecated
    public CBORObject Negate() {
      CBORNumber cn = CBORNumber.FromCBORObject(this);
      if (cn == null) {
        throw new IllegalStateException("This Object is not a number.");
      }
      Object newItem = cn.GetNumberInterface().Negate(cn.GetValue());
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
     * Removes all items from this CBOR array or all keys and values from this CBOR
     * map.
     * @throws IllegalStateException This object is not a CBOR array or CBOR map.
     */
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

    /**
     * If this object is an array, removes the first instance of the specified item
     * (once converted to a CBOR object) from the array. If this object is
     * a map, removes the item with the given key (once converted to a CBOR
     * object) from the map.
     * @param obj The item or key (once converted to a CBOR object) to remove.
     * @return {@code true} if the item was removed; otherwise, {@code false}.
     * @throws NullPointerException The parameter {@code obj} is null (as opposed
     * to CBORObject.Null).
     * @throws IllegalStateException The object is not an array or map.
     */
    public boolean Remove(Object obj) {
      return this.Remove(CBORObject.FromObject(obj));
    }

    /**
     * Removes the item at the given index of this CBOR array.
     * @param index The index, starting at 0, of the item to remove.
     * @return Returns "true" if the object was removed. Returns "false" if the
     * given index is less than 0, or is at least as high as the number of
     * items in the array.
     * @throws IllegalStateException This object is not a CBOR array.
     */
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

    /**
     * If this object is an array, removes the first instance of the specified item
     * from the array. If this object is a map, removes the item with the
     * given key from the map.
     * @param obj The item or key to remove.
     * @return {@code true} if the item was removed; otherwise, {@code false}.
     * @throws NullPointerException The parameter {@code obj} is null (as opposed
     * to CBORObject.Null).
     * @throws IllegalStateException The object is not an array or map.
     */
    public boolean Remove(CBORObject obj) {
      if (obj == null) {
        throw new NullPointerException("obj");
      }
      if (this.getType() == CBORType.Map) {
        Map<CBORObject, CBORObject> dict = this.AsMap();
        boolean hasKey = dict.containsKey(obj);
        if (hasKey) {
          dict.remove(obj);
          return true;
        }
        return false;
      }
      if (this.getType() == CBORType.Array) {
        List<CBORObject> list = this.AsList();
        return list.remove(obj);
      }
      throw new IllegalStateException("Not a map or array");
    }

    /**
     * Maps an object to a key in this CBOR map, or adds the value if the key
     * doesn't exist. If this is a CBOR array, instead sets the value at
     * the given index to the given value.
     * @param key If this instance is a CBOR map, this parameter is an object
     * representing the key, which will be converted to a CBORObject; in
     * this case, this parameter can be null, in which case this value is
     * converted to CBORObject.Null. If this instance is a CBOR array, this
     * parameter must be a 32-bit signed integer({@code int}) identifying
     * the index (starting from 0) of the item to set in the array.
     * @param valueOb An object representing the value, which will be converted to
     * a CBORObject. Can be null, in which case this value is converted to
     * CBORObject.Null.
     * @return This instance.
     * @throws IllegalStateException This object is not a map or an array.
     * @throws IllegalArgumentException The parameter {@code key} or {@code valueOb} has
     * an unsupported type, or this instance is a CBOR array and {@code
     * key} is less than 0, is the size of this array or greater, or is not
     * a 32-bit signed integer ({@code int}).
     */
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
        if (map.containsKey(mapKey)) {
          map.put(mapKey, mapValue);
        } else {
          map.put(mapKey, mapValue);
        }
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

    /**
     * Converts this object to a text string in JavaScript object Notation (JSON)
     * format. See the overload to ToJSONString taking a JSONOptions
     * argument for further information. <p>If the CBOR object contains
     * CBOR maps, or is a CBOR map itself, the order in which the keys to
     * the map are written out to the JSON string is undefined unless the
     * map was created using the NewOrderedMap method. Map keys other than
     * untagged text strings are converted to JSON strings before writing
     *  them out (for example, <code>22("Test")</code> is converted to
     *  <code>"Test"</code> and <code>true</code> is converted to <code>"true"</code>). After
     * such conversion, if two or more keys for the same map are identical,
     * this method throws a CBORException. The example code given in
     * <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
     * can be used to write out certain keys of a CBOR map in a given order
     * to a JSON string, or to write out a CBOR object as part of a JSON
     * text sequence.</p> <p><b>Warning:</b> In general, if this CBOR
     * object contains integer map keys or uses other features not
     * supported in JSON, and the application converts this CBOR object to
     * JSON and back to CBOR, the application <i>should not</i> expect the
     * new CBOR object to be exactly the same as the original. This is
     * because the conversion in many cases may have to convert unsupported
     * features in JSON to supported features which correspond to a
     * different feature in CBOR (such as converting integer map keys,
     * which are supported in CBOR but not JSON, to text strings, which are
     * supported in both).</p>
     * @return A text string containing the converted object in JSON format.
     */
    public String ToJSONString() {
      return this.ToJSONString(JSONOptions.Default);
    }

    /**
     * Converts this object to a text string in JavaScript object Notation (JSON)
     * format, using the specified options to control the encoding process.
     * This function works not only with arrays and maps, but also
     * integers, strings, byte arrays, and other JSON data types. Notes:
     * <ul><li>If this object contains maps with non-string keys, the keys
     * are converted to JSON strings before writing the map as a JSON
     * string.</li> <li>If this object represents a number (the IsNumber
     * property, or isNumber() method in Java, returns true), then it is
     * written out as a number.</li> <li>If the CBOR object contains CBOR
     * maps, or is a CBOR map itself, the order in which the keys to the
     * map are written out to the JSON string is undefined unless the map
     * was created using the NewOrderedMap method. Map keys other than
     * untagged text strings are converted to JSON strings before writing
     *  them out (for example, <code>22("Test")</code> is converted to
     *  <code>"Test"</code> and <code>true</code> is converted to <code>"true"</code>). After
     * such conversion, if two or more keys for the same map are identical,
     * this method throws a CBORException.</li> <li>If a number in the form
     * of an arbitrary-precision binary floating-point number has a very
     * high binary exponent, it will be converted to a double before being
     * converted to a JSON string. (The resulting double could overflow to
     * infinity, in which case the arbitrary-precision binary
     * floating-point number is converted to null.)</li> <li>The string
     * will not begin with a byte-order mark (U+FEFF); RFC 8259 (the JSON
     * specification) forbids placing a byte-order mark at the beginning of
     * a JSON string.</li> <li>Byte strings are converted to Base64 URL
     * without whitespace or padding by default (see section 3.4.5.3 of RFC
     * 8949). A byte string will instead be converted to traditional base64
     * without whitespace and with padding if it has tag 22, or base16 for
     * tag 23. (To create a CBOR object with a given tag, call the
     * <code>CBORObject.FromObjectAndTag</code> method and pass the CBOR object
     * and the desired tag number to that method.)</li> <li>Rational
     * numbers will be converted to their exact form, if possible,
     * otherwise to a high-precision approximation. (The resulting
     * approximation could overflow to infinity, in which case the rational
     * number is converted to null.)</li> <li>Simple values other than true
     * and false will be converted to null. (This doesn't include
     * floating-point numbers.)</li> <li>Infinity and not-a-number will be
     * converted to null.</li> </ul> <p><b>Warning:</b> In general, if this
     * CBOR object contains integer map keys or uses other features not
     * supported in JSON, and the application converts this CBOR object to
     * JSON and back to CBOR, the application <i>should not</i> expect the
     * new CBOR object to be exactly the same as the original. This is
     * because the conversion in many cases may have to convert unsupported
     * features in JSON to supported features which correspond to a
     * different feature in CBOR (such as converting integer map keys,
     * which are supported in CBOR but not JSON, to text strings, which are
     * supported in both).</p> <p>The example code given below (originally
     * written in C# for the.NET version) can be used to write out certain
     *  keys of a CBOR map in a given order to a JSON string.</p> <pre>/* Generates a JSON string of 'mapObj' whose keys are in the order given in 'keys' . Only keys found in 'keys' will be written if they exist in 'mapObj'. &#x2a;&#x2f; private static string KeysToJSONMap(CBORObject mapObj, List&lt;CBORObject&gt; keys) { if (mapObj == null) { throw new NullPointerException)"mapObj");} if (keys == null) { throw new NullPointerException)"keys");} if (obj.getType() != CBORType.Map) { throw new IllegalArgumentException("'obj' is not a map."); } StringBuilder builder = new StringBuilder(); boolean first = true; builder.append("{"); for (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { if (!first) {builder.append(", ");} var keyString=(key.getCBORType() == CBORType.string) ? key.AsString() : key.ToJSONString(); builder.append(CBORObject.FromObject(keyString) .ToJSONString()) .append(":").append(mapObj.get(key).ToJSONString()); first=false; } } return builder.append("}").toString(); }</pre> .
     * @param options Specifies options to control writing the CBOR object to JSON.
     * @return A text string containing the converted object in JSON format.
     * @throws NullPointerException The parameter {@code options} is null.
     */
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
        default: {
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

    /**
     * Returns this CBOR object in a text form intended to be read by humans. The
     * value returned by this method is not intended to be parsed by
     * computer programs, and the exact text of the value may change at any
     * time between versions of this library. <p>The returned string is not
     * necessarily in JavaScript object Notation (JSON); to convert CBOR
     * objects to JSON strings, use the
     * <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
     * method instead.</p>
     * @return A text representation of this object.
     */
    @Override public String toString() {
      return CBORDataUtilities.ToStringHelper(this, 0);
    }

    /**
     * Gets an object with the same value as this one but without the tags it has,
     * if any. If this object is an array, map, or byte string, the data
     * will not be copied to the returned object, so changes to the
     * returned object will be reflected in this one.
     * @return A CBOR object.
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
     * @return A CBOR object.
     */
    public CBORObject UntagOne() {
      return (this.itemtypeValue == CBORObjectTypeTagged) ?
        ((CBORObject)this.itemValue) : this;
    }

    /**
     * Converts this object to a text string in JavaScript object Notation (JSON)
     * format, as in the ToJSONString method, and writes that string to a
     * data stream in UTF-8. If the CBOR object contains CBOR maps, or is a
     * CBOR map, the order in which the keys to the map are written out to
     * the JSON string is undefined unless the map was created using the
     * NewOrderedMap method. The example code given in
     * <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
     * can be used to write out certain keys of a CBOR map in a given order
     * to a JSON string.<p> <p>The following example (originally written in
     * C# for the.NET version) writes out a CBOR object as part of a JSON
     * text sequence (RFC 7464).</p> <pre> stream.write(0x1e); &#x2f;&#x2a; RS &#x2a;&#x2f; cborObject.WriteJSONTo(stream); &#x2f;&#x2a; JSON &#x2a;&#x2f; stream.write(0x0a); &#x2f;&#x2a; LF &#x2a;&#x2f; </pre> <p>The following example (originally written
     * in C# for the.NET version) shows how to use the
     * <code>LimitedMemoryStream</code> class (implemented in
     * <i>LimitedMemoryStream.cs</i> in the peteroupc/CBOR open-source
     * repository) to limit the size of supported JSON serializations of
     * CBOR objects.</p> <pre> &#x2f;&#x2a; maximum supported JSON size in bytes&#x2a;&#x2f; int maxSize = 20000; {
LimitedMemoryStream ms = null;
try {
ms = new LimitedMemoryStream(maxSize);
 cborObject.WriteJSONTo(ms); var bytes = ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre> <p>The following example (written
     * in Java for the Java version) shows how to use a subclassed
     * <code>OutputStream</code> together with a <code>ByteArrayOutputStream</code> to
     * limit the size of supported JSON serializations of CBOR objects.</p>
     * <pre> &#x2f;&#x2a; maximum supported JSON size in bytes&#x2a;&#x2f; final int maxSize = 20000; ByteArrayOutputStream ba = new ByteArrayOutputStream(); &#x2f;&#x2a; throws UnsupportedOperationException if too big&#x2a;&#x2f; cborObject.WriteJSONTo(new FilterOutputStream(ba) { private int size = 0; public void write(byte[] b, int off, int len) { if (len&gt;(maxSize-size)) { throw new UnsupportedOperationException(); } size+=len; out.write(b, off, len); } public void write(byte b) { if (size &gt;= maxSize) { throw new UnsupportedOperationException(); } size++; out.write(b); } }); byte[] bytes = ba.toByteArray(); </pre>
     * <p>The following example (originally written in C# for the.NET
     * version) shows how to use a.NET MemoryStream to limit the size of
     * supported JSON serializations of CBOR objects. The disadvantage is
     * that the extra memory needed to do so can be wasteful, especially if
     * the average serialized object is much smaller than the maximum size
     * given (for example, if the maximum size is 20000 bytes, but the
     * average serialized object has a size of 50 bytes).</p> <pre> byte[] backing = new byte[20000]; &#x2f;&#x2a; maximum supported JSON size in bytes&#x2a;&#x2f; byte[] bytes1, bytes2; {
java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream(backing);
 &#x2f;&#x2a; throws UnsupportedOperationException if too big&#x2a;&#x2f; cborObject.WriteJSONTo(ms); bytes1 = new byte[ms.size()]; &#x2f;&#x2a; Copy serialized data if successful&#x2a;&#x2f; System.ArrayCopy(backing, 0, bytes1, 0, (int)ms.size()); &#x2f;&#x2a; Reset memory stream&#x2a;&#x2f; ms.size() = 0; cborObject2.WriteJSONTo(ms); bytes2 = new byte[ms.size()]; &#x2f;&#x2a; Copy serialized data if successful&#x2a;&#x2f; System.ArrayCopy(backing, 0, bytes2, 0, (int)ms.size());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre>
     * </p>
     * @param outputStream A writable data stream.
     * @throws java.io.IOException An I/O error occurred.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
    public void WriteJSONTo(OutputStream outputStream) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      CBORJsonWriter.WriteJSONToInternal(
        this,
        new StringOutput(outputStream),
        JSONOptions.Default);
    }

    /**
     * Converts this object to a text string in JavaScript object Notation (JSON)
     * format, as in the ToJSONString method, and writes that string to a
     * data stream in UTF-8, using the given JSON options to control the
     * encoding process. If the CBOR object contains CBOR maps, or is a
     * CBOR map, the order in which the keys to the map are written out to
     * the JSON string is undefined unless the map was created using the
     * NewOrderedMap method. The example code given in
     * <b>PeterO.Cbor.CBORObject.ToJSONString(PeterO.Cbor.JSONOptions)</b>
     * can be used to write out certain keys of a CBOR map in a given order
     * to a JSON string.
     * @param outputStream A writable data stream.
     * @param options An object containing the options to control writing the CBOR
     * object to JSON.
     * @throws java.io.IOException An I/O error occurred.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
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

    /**
     * Generates a CBOR object from a floating-point number represented by its
     * bits.
     * @param floatingBits The bits of a floating-point number number to write.
     * @param byteCount The number of bytes of the stored floating-point number;
     *  this also specifies the format of the "floatingBits" parameter. This
     *  value can be 2 if "floatingBits"'s lowest (least significant) 16
     * bits identify the floating-point number in IEEE 754r binary16
     *  format; or 4 if "floatingBits"'s lowest (least significant) 32 bits
     * identify the floating-point number in IEEE 754r binary32 format; or
     *  8 if "floatingBits" identifies the floating point number in IEEE
     * 754r binary64 format. Any other values for this parameter are
     * invalid.
     * @return A CBOR object storing the given floating-point number.
     * @throws IllegalArgumentException The parameter {@code byteCount} is other than 2,
     * 4, or 8.
     */
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

    /**
     * Writes the bits of a floating-point number in CBOR format to a data stream.
     * @param outputStream A writable data stream.
     * @param floatingBits The bits of a floating-point number number to write.
     * @param byteCount The number of bytes of the stored floating-point number;
     *  this also specifies the format of the "floatingBits" parameter. This
     *  value can be 2 if "floatingBits"'s lowest (least significant) 16
     * bits identify the floating-point number in IEEE 754r binary16
     *  format; or 4 if "floatingBits"'s lowest (least significant) 32 bits
     * identify the floating-point number in IEEE 754r binary32 format; or
     *  8 if "floatingBits" identifies the floating point number in IEEE
     * 754r binary64 format. Any other values for this parameter are
     * invalid. This method will write one plus this many bytes to the data
     * stream.
     * @return The number of 8-bit bytes ordered to be written to the data stream.
     * @throws IllegalArgumentException The parameter {@code byteCount} is other than 2,
     * 4, or 8.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
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

    /**
     * Writes the bits of a floating-point number in CBOR format to a data stream.
     * @param outputStream A writable data stream.
     * @param floatingBits The bits of a floating-point number number to write.
     * @param byteCount The number of bytes of the stored floating-point number;
     *  this also specifies the format of the "floatingBits" parameter. This
     *  value can be 2 if "floatingBits"'s lowest (least significant) 16
     * bits identify the floating-point number in IEEE 754r binary16
     *  format; or 4 if "floatingBits"'s lowest (least significant) 32 bits
     * identify the floating-point number in IEEE 754r binary32 format; or
     *  8 if "floatingBits" identifies the floating point number in IEEE
     * 754r binary64 format. Any other values for this parameter are
     * invalid.
     * @param shortestForm If true, writes the shortest form of the floating-point
     * number that preserves its value. If false, this method will write
     * the number in the form given by 'floatingBits' by writing one plus
     * the number of bytes given by 'byteCount' to the data stream.
     * @return The number of 8-bit bytes ordered to be written to the data stream.
     * @throws IllegalArgumentException The parameter {@code byteCount} is other than 2,
     * 4, or 8.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
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
            return WriteFloatingPointBits(outputStream, (long)bits, 2, false);
          }
          if (CBORUtilities.DoubleRetainsSameValueInSingle(floatingBits)) {
            bits = CBORUtilities.DoubleToRoundedSinglePrecision(floatingBits);
            return WriteFloatingPointBits(outputStream, (long)bits, 4, false);
          }
        } else if (byteCount == 4) {
          int bits =
            CBORUtilities.SingleToHalfPrecisionIfSameValue(floatingBits);
          if (bits != -1) {
            return WriteFloatingPointBits(outputStream, (long)bits, 2, false);
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
        default:
          throw new IllegalArgumentException("byteCount");
      }
    }

    /**
     * Writes a 64-bit binary floating-point number in CBOR format to a data
     * stream, either in its 64-bit form, or its rounded 32-bit or 16-bit
     * equivalent.
     * @param outputStream A writable data stream.
     * @param doubleVal The double-precision floating-point number to write.
     * @param byteCount The number of 8-bit bytes of the stored number. This value
     * can be 2 to store the number in IEEE 754r binary16, rounded to
     * nearest, ties to even; or 4 to store the number in IEEE 754r
     * binary32, rounded to nearest, ties to even; or 8 to store the number
     * in IEEE 754r binary64. Any other values for this parameter are
     * invalid.
     * @return The number of 8-bit bytes ordered to be written to the data stream.
     * @throws IllegalArgumentException The parameter {@code byteCount} is other than 2,
     * 4, or 8.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
    public static int WriteFloatingPointValue(
      OutputStream outputStream,
      double doubleVal,
      int byteCount) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      long bits = 0;
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

    /**
     * Writes a 32-bit binary floating-point number in CBOR format to a data
     * stream, either in its 64- or 32-bit form, or its rounded 16-bit
     * equivalent.
     * @param outputStream A writable data stream.
     * @param singleVal The single-precision floating-point number to write.
     * @param byteCount The number of 8-bit bytes of the stored number. This value
     * can be 2 to store the number in IEEE 754r binary16, rounded to
     * nearest, ties to even; or 4 to store the number in IEEE 754r
     * binary32; or 8 to store the number in IEEE 754r binary64. Any other
     * values for this parameter are invalid.
     * @return The number of 8-bit bytes ordered to be written to the data stream.
     * @throws IllegalArgumentException The parameter {@code byteCount} is other than 2,
     * 4, or 8.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
    public static int WriteFloatingPointValue(
      OutputStream outputStream,
      float singleVal,
      int byteCount) throws java.io.IOException {
      if (outputStream == null) {
        throw new NullPointerException("outputStream");
      }
      int bits = 0;
      long longbits = 0L;
      switch (byteCount) {
        case 2:
          bits = Float.floatToRawIntBits(singleVal);
          bits = CBORUtilities.SingleToRoundedHalfPrecision(bits);
          bits &= 0xffff;
          return WriteFloatingPointBits(outputStream, bits, 2);
        case 4:
          bits = Float.floatToRawIntBits(singleVal);
          longbits = ((long)bits) & 0xffffffffL;
          return WriteFloatingPointBits(outputStream, longbits, 4);
        case 8:
          bits = Float.floatToRawIntBits(singleVal);
          longbits = CBORUtilities.SingleToDoublePrecision(bits);
          return WriteFloatingPointBits(outputStream, longbits, 8);
        default: throw new IllegalArgumentException("byteCount");
      }
    }

    /**
     * Writes a CBOR major type number and an integer 0 or greater associated with
     * it to a data stream, where that integer is passed to this method as
     * a 64-bit signed integer. This is a low-level method that is useful
     * for implementing custom CBOR encoding methodologies. This method
     * encodes the given major type and value in the shortest form allowed
     * for the major type.<p>There are other useful things to note when
     * encoding CBOR that are not covered by this WriteValue method. To
     * mark the start of an indefinite-length array, write the 8-bit byte
     * 0x9f to the output stream. To mark the start of an indefinite-length
     * map, write the 8-bit byte 0xbf to the output stream. To mark the end
     * of an indefinite-length array or map, write the 8-bit byte 0xff to
     * the output stream. For examples, see the WriteValue(InputStream, int,
     * int) overload.</p>
     * @param outputStream A writable data stream.
     * @param majorType The CBOR major type to write. This is a number from 0
     * through 7 as follows. 0: integer 0 or greater; 1: negative integer;
     * 2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
     * simple value. See RFC 8949 for details on these major types.
     * @param value An integer 0 or greater associated with the major type, as
     * follows. 0: integer 0 or greater; 1: the negative integer's absolute
     * value is 1 plus this number; 2: length in bytes of the byte string;
     * 3: length in bytes of the UTF-8 text string; 4: number of items in
     * the array; 5: number of key-value pairs in the map; 6: tag number;
     * 7: simple value number, which must be in the interval [0, 23] or
     * [32, 255].
     * @return The number of bytes ordered to be written to the data stream.
     * @throws IllegalArgumentException Value is from 24 to 31 and major type is 7.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
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
          outputStream.write((byte)0xf8);
          outputStream.write((byte)value);
          return 2;
        }
      } else {
        return WritePositiveInt64(majorType, value, outputStream);
      }
    }

    /**
     * Writes a CBOR major type number and an integer 0 or greater associated with
     * it to a data stream, where that integer is passed to this method as
     * a 32-bit signed integer. This is a low-level method that is useful
     * for implementing custom CBOR encoding methodologies. This method
     * encodes the given major type and value in the shortest form allowed
     * for the major type.<p>There are other useful things to note when
     * encoding CBOR that are not covered by this WriteValue method. To
     * mark the start of an indefinite-length array, write the 8-bit byte
     * 0x9f to the output stream. To mark the start of an indefinite-length
     * map, write the 8-bit byte 0xbf to the output stream. To mark the end
     * of an indefinite-length array or map, write the 8-bit byte 0xff to
     * the output stream.</p><p> <p>In the following example, an array of
     * three objects is written as CBOR to a data stream.</p>
     *  <pre>&#x2f;&#x2a; array, length 3&#x2a;&#x2f; CBORObject.WriteValue(stream, 4, 3); &#x2f;&#x2a; item 1 &#x2a;&#x2f; CBORObject.Write("hello world", stream); CBORObject.Write(25, stream); &#x2f;&#x2a; item 2&#x2a;&#x2f; CBORObject.Write(false, stream); &#x2f;&#x2a; item 3&#x2a;&#x2f;</pre> <p>In the following
     * example, a map consisting of two key-value pairs is written as CBOR
     *  to a data stream.</p> <pre>CBORObject.WriteValue(stream, 5, 2); &#x2f;&#x2a; map, 2 pairs&#x2a;&#x2f; CBORObject.Write("number", stream); &#x2f;&#x2a; key 1 &#x2a;&#x2f; CBORObject.Write(25, stream); &#x2f;&#x2a; value 1 &#x2a;&#x2f; CBORObject.Write("string", stream); &#x2f;&#x2a; key 2&#x2a;&#x2f; CBORObject.Write("hello", stream); &#x2f;&#x2a; value 2&#x2a;&#x2f;</pre> <p>In the following example
     * (originally written in C# for the.NET Framework version), a text
     *  string is written as CBOR to a data stream.</p> <pre>string str = "hello world"; byte[] bytes = DataUtilities.GetUtf8Bytes(str, true); CBORObject.WriteValue(stream, 4, bytes.length); stream.write(bytes, 0, bytes.length);</pre> . </p>
     * @param outputStream A writable data stream.
     * @param majorType The CBOR major type to write. This is a number from 0
     * through 7 as follows. 0: integer 0 or greater; 1: negative integer;
     * 2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
     * simple value. See RFC 8949 for details on these major types.
     * @param value An integer 0 or greater associated with the major type, as
     * follows. 0: integer 0 or greater; 1: the negative integer's absolute
     * value is 1 plus this number; 2: length in bytes of the byte string;
     * 3: length in bytes of the UTF-8 text string; 4: number of items in
     * the array; 5: number of key-value pairs in the map; 6: tag number;
     * 7: simple value number, which must be in the interval [0, 23] or
     * [32, 255].
     * @return The number of bytes ordered to be written to the data stream.
     * @throws IllegalArgumentException Value is from 24 to 31 and major type is 7.
     * @throws NullPointerException The parameter {@code outputStream} is null.
     */
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
          outputStream.write((byte)0xf8);
          outputStream.write((byte)value);
          return 2;
        }
      } else {
        return WritePositiveInt(majorType, value, outputStream);
      }
    }

    /**
     * Writes a CBOR major type number and an integer 0 or greater associated with
     * it to a data stream, where that integer is passed to this method as
     * an arbitrary-precision integer. This is a low-level method that is
     * useful for implementing custom CBOR encoding methodologies. This
     * method encodes the given major type and value in the shortest form
     * allowed for the major type.<p>There are other useful things to note
     * when encoding CBOR that are not covered by this WriteValue method.
     * To mark the start of an indefinite-length array, write the 8-bit
     * byte 0x9f to the output stream. To mark the start of an
     * indefinite-length map, write the 8-bit byte 0xbf to the output
     * stream. To mark the end of an indefinite-length array or map, write
     * the 8-bit byte 0xff to the output stream.</p>
     * @param outputStream A writable data stream.
     * @param majorType The CBOR major type to write. This is a number from 0
     * through 7 as follows. 0: integer 0 or greater; 1: negative integer;
     * 2: byte string; 3: UTF-8 text string; 4: array; 5: map; 6: tag; 7:
     * simple value. See RFC 8949 for details on these major types.
     * @param bigintValue An integer 0 or greater associated with the major type,
     * as follows. 0: integer 0 or greater; 1: the negative integer's
     * absolute value is 1 plus this number; 2: length in bytes of the byte
     * string; 3: length in bytes of the UTF-8 text string; 4: number of
     * items in the array; 5: number of key-value pairs in the map; 6: tag
     * number; 7: simple value number, which must be in the interval [0,
     * 23] or [32, 255]. For major types 0 to 6, this number may not be
     * greater than 2^64 - 1.
     * @return The number of bytes ordered to be written to the data stream.
     * @throws IllegalArgumentException The parameter {@code majorType} is 7 and value is
     * greater than 255.
     * @throws NullPointerException The parameter {@code outputStream} or {@code
     * bigintValue} is null.
     */
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

    /**
     * <p>Writes this CBOR object to a data stream. If the CBOR object contains
     * CBOR maps, or is a CBOR map, the order in which the keys to the map
     * are written out to the data stream is undefined unless the map was
     * created using the NewOrderedMap method. See the examples (originally
     * written in C# for the.NET version) for ways to write out certain
     * keys of a CBOR map in a given order. In the case of CBOR objects of
     * type FloatingPoint, the number is written using the shortest
     * floating-point encoding possible; this is a change from previous
     * versions.</p><p> <p>The following example shows a method that writes
     * each key of 'mapObj' to 'outputStream', in the order given in
     * 'keys', where 'mapObj' is written out in the form of a CBOR
     * <b>definite-length map</b> . Only keys found in 'keys' will be
     *  written if they exist in 'mapObj'.</p> <pre>private static void WriteKeysToMap(CBORObject mapObj, List&lt;CBORObject&gt; keys, OutputStream outputStream) throws java.io.IOException { if (mapObj == null) { throw new NullPointerException("mapObj");} if (keys == null) {throw new NullPointerException("keys");} if (outputStream == null) {throw new NullPointerException("outputStream");} if (obj.getType()!=CBORType.Map) { throw new IllegalArgumentException("'obj' is not a map."); } int keyCount = 0; for (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { keyCount++; } } CBORObject.WriteValue(outputStream, 5, keyCount); for (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { key.WriteTo(outputStream); mapObj.get(key).WriteTo(outputStream); } } }</pre> <p>The following example shows a method that writes each
     * key of 'mapObj' to 'outputStream', in the order given in 'keys',
     * where 'mapObj' is written out in the form of a CBOR
     * <b>indefinite-length map</b> . Only keys found in 'keys' will be
     *  written if they exist in 'mapObj'.</p> <pre>private static void WriteKeysToIndefMap(CBORObject mapObj, List&lt;CBORObject&gt; keys, OutputStream outputStream) throws java.io.IOException { if (mapObj == null) { throw new NullPointerException("mapObj");} if (keys == null) {throw new NullPointerException("keys");} if (outputStream == null) {throw new NullPointerException("outputStream");} if (obj.getType()!=CBORType.Map) { throw new IllegalArgumentException("'obj' is not a map."); } outputStream.write((byte)0xBF); for (CBORObject key in keys) { if (mapObj.ContainsKey(key)) { key.WriteTo(outputStream); mapObj.get(key).WriteTo(outputStream); } } outputStream.write((byte)0xff); }</pre> <p>The following example
     * shows a method that writes out a list of objects to 'outputStream'
     *  as an <b>indefinite-length CBOR array</b> .</p> <pre>private static void WriteToIndefArray(List&lt;object&gt; list, OutputStream outputStream) throws java.io.IOException { if (list == null) { throw new NullPointerException("list");} if (outputStream == null) {throw new NullPointerException("outputStream");} outputStream.write((byte)0x9f); for (object item in list) { new CBORObject(item).WriteTo(outputStream); } outputStream.write((byte)0xff); }</pre> <p>The following example
     * (originally written in C# for the.NET version) shows how to use the
     * <code>LimitedMemoryStream</code> class (implemented in
     * <i>LimitedMemoryStream.cs</i> in the peteroupc/CBOR open-source
     * repository) to limit the size of supported CBOR serializations.</p>
     * <pre> &#x2f;&#x2a; maximum supported CBOR size in bytes&#x2a;&#x2f; int maxSize = 20000; {
LimitedMemoryStream ms = null;
try {
ms = new LimitedMemoryStream(maxSize);
 cborObject.WriteTo(ms); var bytes = ms.toByteArray();
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre> <p>The following example (written in Java for
     * the Java version) shows how to use a subclassed <code>OutputStream</code>
     * together with a <code>ByteArrayOutputStream</code> to limit the size of
     * supported CBOR serializations.</p> <pre> &#x2f;&#x2a; maximum supported CBOR size in bytes&#x2a;&#x2f; final int maxSize = 20000; ByteArrayOutputStream ba = new ByteArrayOutputStream(); &#x2f;&#x2a; throws UnsupportedOperationException if too big&#x2a;&#x2f; cborObject.WriteTo(new FilterOutputStream(ba) { private int size = 0; public void write(byte[] b, int off, int len) { if (len&gt;(maxSize-size)) { throw new UnsupportedOperationException(); } size+=len; out.write(b, off, len); } public void write(byte b) { if (size &gt;= maxSize) { throw new UnsupportedOperationException(); } size++; out.write(b); } }); byte[] bytes = ba.toByteArray(); </pre>
     * <p>The following example (originally written in C# for the.NET
     * version) shows how to use a.NET MemoryStream to limit the size of
     * supported CBOR serializations. The disadvantage is that the extra
     * memory needed to do so can be wasteful, especially if the average
     * serialized object is much smaller than the maximum size given (for
     * example, if the maximum size is 20000 bytes, but the average
     * serialized object has a size of 50 bytes).</p> <pre> byte[] backing = new byte[20000]; &#x2f;&#x2a; maximum supported CBOR size in bytes&#x2a;&#x2f; byte[] bytes1, bytes2; {
java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream(backing);
 &#x2f;&#x2a; throws UnsupportedOperationException if too big&#x2a;&#x2f; cborObject.WriteTo(ms); bytes1 = new byte[ms.size()]; &#x2f;&#x2a; Copy serialized data if successful&#x2a;&#x2f; System.ArrayCopy(backing, 0, bytes1, 0, (int)ms.size()); &#x2f;&#x2a; Reset memory stream&#x2a;&#x2f; ms.size() = 0; cborObject2.WriteTo(ms); bytes2 = new byte[ms.size()]; &#x2f;&#x2a; Copy serialized data if successful&#x2a;&#x2f; System.ArrayCopy(backing, 0, bytes2, 0, (int)ms.size());
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
} </pre> </p>
     * @param stream A writable data stream.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     */
    public void WriteTo(OutputStream stream) throws java.io.IOException {
      this.WriteTo(stream, CBOREncodeOptions.Default);
    }

    /**
     * Writes this CBOR object to a data stream, using the specified options for
     * encoding the data to CBOR format. If the CBOR object contains CBOR
     * maps, or is a CBOR map, the order in which the keys to the map are
     * written out to the data stream is undefined unless the map was
     * created using the NewOrderedMap method. The example code given in
     * <see cref='PeterO.Cbor.CBORObject.WriteTo(System.IO.InputStream)'/> can
     * be used to write out certain keys of a CBOR map in a given order. In
     * the case of CBOR objects of type FloatingPoint, the number is
     * written using the shortest floating-point encoding possible; this is
     * a change from previous versions.
     * @param stream A writable data stream.
     * @param options Options for encoding the data to CBOR.
     * @throws NullPointerException The parameter {@code stream} is null.
     * @throws java.io.IOException An I/O error occurred.
     * @throws IllegalArgumentException Unexpected data type".
     */
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
        long uadditional = 0;
        switch (firstbyte & 0x1f) {
          case 24:
            uadditional = (int)(data[1] & (int)0xff);
            break;
          case 25:
            uadditional = (data[1] & 0xffL) << 8;
            uadditional |= (long)(data[2] & 0xffL);
            break;
          case 26:
            uadditional = (data[1] & 0xffL) << 24;
            uadditional |= (data[2] & 0xffL) << 16;
            uadditional |= (data[3] & 0xffL) << 8;
            uadditional |= (long)(data[4] & 0xffL);
            break;
          case 27:
            uadditional = (data[1] & 0xffL) << 56;
            uadditional |= (data[2] & 0xffL) << 48;
            uadditional |= (data[3] & 0xffL) << 40;
            uadditional |= (data[4] & 0xffL) << 32;
            uadditional |= (data[5] & 0xffL) << 24;
            uadditional |= (data[6] & 0xffL) << 16;
            uadditional |= (data[7] & 0xffL) << 8;
            uadditional |= (long)(data[8] & 0xffL);
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
              EInteger minusOne = EInteger.FromInt64(-1);
              bigintAdditional = minusOne.Subtract(bigintAdditional);
              return FromObject(bigintAdditional);
            }
          case 7:
            if (firstbyte >= 0xf9 && firstbyte <= 0xfb) {
              long dblbits = (long)uadditional;
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
          CheckCBORLength(
            rightLength,
            length);
          // Check for all ASCII text
          for (int i = offsetp1; i < length; ++i) {
            if ((data[i] & ((byte)0x80)) != 0) {
              return null;
            }
          }
          // All ASCII text, so convert to a text String
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
        bytes[0] = (byte)0x78;
        bytes[1] = (byte)utf8.length;
        System.arraycopy(utf8, 0, bytes, 2, utf8.length);
        return bytes;
      }
      if (utf8.length <= 0xffffL) {
        bytes = new byte[utf8.length + 3];
        bytes[0] = (byte)0x79;
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
      if (value < 24) {
        return new byte[] { (byte)((byte)value | (byte)(type << 5)) };
      }
      if (value <= 0xffL) {
        return new byte[] { (byte)(24 | (type << 5)), (byte)(value & 0xff),
         };
      }
      if (value <= 0xffffL) {
        return new byte[] { (byte)(25 | (type << 5)),
          (byte)((value >> 8) & 0xff), (byte)(value & 0xff),
         };
      }
      if (value <= 0xffffffffL) {
        return new byte[] { (byte)(26 | (type << 5)),
          (byte)((value >> 24) & 0xff), (byte)((value >> 16) & 0xff),
          (byte)((value >> 8) & 0xff), (byte)(value & 0xff),
         };
      }
      return new byte[] { (byte)(27 | (type << 5)), (byte)((value >> 56) & 0xff),
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
      if (value < 24) {
        return new byte[] { (byte)((byte)value | (byte)(type << 5)) };
      }
      if (value <= 0xff) {
        return new byte[] { (byte)(24 | (type << 5)), (byte)(value & 0xff),
         };
      }
      if (value <= 0xffff) {
        return new byte[] { (byte)(25 | (type << 5)),
          (byte)((value >> 8) & 0xff), (byte)(value & 0xff),
         };
      }
      return new byte[] { (byte)(26 | (type << 5)), (byte)((value >> 24) & 0xff),
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
          (int)(i - 0xe0));
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
      // NOTE: Compare map key counts to conform
      // to bytewise lexicographical ordering
      if (listACount != listBCount) {
        return listACount < listBCount ? -1 : 1;
      }
      ArrayList<CBORObject> sortedASet = new ArrayList<CBORObject>(mapA.keySet());
      ArrayList<CBORObject> sortedBSet = new ArrayList<CBORObject>(mapB.keySet());
      // System.out.println("---sorting mapA's keys");
      java.util.Collections.sort(sortedASet);
      // System.out.println("---sorting mapB's keys");
      java.util.Collections.sort(sortedBSet);
      // System.out.println("---done sorting");
      listACount = sortedASet.size();
      listBCount = sortedBSet.size();
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
        stack = new ArrayList<Object>(4);
        stack.add(parent);
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
            // Write bytes retrieved so far - the next two bytes
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
          long value = ((long)low) & 0xffffffffL;
          WritePositiveInt64(6, value, s);
        } else if ((high >> 16) == 0) {
          long value = ((long)low) & 0xffffffffL;
          long highValue = ((long)high) & 0xffffffffL;
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
