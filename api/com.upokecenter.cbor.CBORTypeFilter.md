# com.upokecenter.cbor.CBORTypeFilter

    @Deprecated public final class CBORTypeFilter extends java.lang.Object

Deprecated.
May be removed without replacement.

## Fields

* `static CBORTypeFilter Any`<br>
 Deprecated. A filter that allows any CBOR object.
* `static CBORTypeFilter ByteString`<br>
 Deprecated. A filter that allows byte strings.
* `static CBORTypeFilter NegativeInteger`<br>
 Deprecated. A filter that allows negative integers.
* `static CBORTypeFilter None`<br>
 Deprecated. A filter that allows no CBOR types.
* `static CBORTypeFilter TextString`<br>
 Deprecated. A filter that allows text strings.
* `static CBORTypeFilter UnsignedInteger`<br>
 Deprecated. A filter that allows unsigned integers.

## Constructors

* `CBORTypeFilter()`<br>
 Deprecated.

## Methods

* `boolean ArrayIndexAllowed​(int index)`<br>
 Deprecated. Determines whether this type filter allows CBOR arrays and the given array
 index is allowed under this type filter.
* `boolean ArrayLengthMatches​(int length)`<br>
 Deprecated. Returns whether an array's length is allowed under this filter.
* `boolean ArrayLengthMatches​(long length)`<br>
 Deprecated. Returns whether an array's length is allowed under a filter.
* `boolean ArrayLengthMatches​(com.upokecenter.numbers.EInteger bigLength)`<br>
 Deprecated. Returns whether an array's length is allowed under a filter.
* `CBORTypeFilter GetSubFilter​(int index)`<br>
 Deprecated. Gets the type filter for this array filter by its index.
* `CBORTypeFilter GetSubFilter​(long index)`<br>
 Deprecated. Gets the type filter for this array filter by its index.
* `boolean MajorTypeMatches​(int type)`<br>
 Deprecated. Returns whether the given CBOR major type matches a major type allowed by
 this filter.
* `boolean NonFPSimpleValueAllowed()`<br>
 Deprecated. Returns whether this filter allows simple values that are not floating-point
 numbers.
* `boolean TagAllowed​(int tag)`<br>
 Deprecated. Gets a value indicating whether CBOR objects can have the given tag number.
* `boolean TagAllowed​(long longTag)`<br>
 Deprecated. Gets a value indicating whether CBOR objects can have the given tag number.
* `boolean TagAllowed​(com.upokecenter.numbers.EInteger bigTag)`<br>
 Deprecated. Gets a value indicating whether CBOR objects can have the given tag number.
* `CBORTypeFilter WithArrayAnyLength()`<br>
 Deprecated. Copies this filter and includes arrays of any length in the new filter.
* `CBORTypeFilter WithArrayExactLength​(int arrayLength,
                    CBORTypeFilter... elements)`<br>
 Deprecated. Copies this filter and includes CBOR arrays with an exact length to the new
 filter.
* `CBORTypeFilter WithArrayMinLength​(int arrayLength,
                  CBORTypeFilter... elements)`<br>
 Deprecated. Copies this filter and includes CBOR arrays with at least a given length to
 the new filter.
* `CBORTypeFilter WithByteString()`<br>
 Deprecated. Copies this filter and includes byte strings in the new filter.
* `CBORTypeFilter WithFloatingPoint()`<br>
 Deprecated. Copies this filter and includes floating-point numbers in the new filter.
* `CBORTypeFilter WithMap()`<br>
 Deprecated. Copies this filter and includes maps in the new filter.
* `CBORTypeFilter WithNegativeInteger()`<br>
 Deprecated. Copies this filter and includes negative integers in the new filter.
* `CBORTypeFilter WithTags​(int... tags)`<br>
 Deprecated. Copies this filter and includes a set of valid CBOR tags in the new filter.
* `CBORTypeFilter WithTextString()`<br>
 Deprecated. Copies this filter and includes text strings in the new filter.
* `CBORTypeFilter WithUnsignedInteger()`<br>
 Deprecated. Copies this filter and includes unsigned integers in the new filter.

## Field Details

### Any
    public static final CBORTypeFilter Any
Deprecated.
### ByteString
    public static final CBORTypeFilter ByteString
Deprecated.
### NegativeInteger
    public static final CBORTypeFilter NegativeInteger
Deprecated.
### None
    public static final CBORTypeFilter None
Deprecated.
### TextString
    public static final CBORTypeFilter TextString
Deprecated.
### UnsignedInteger
    public static final CBORTypeFilter UnsignedInteger
Deprecated.
## Method Details

### ArrayIndexAllowed
    public boolean ArrayIndexAllowed​(int index)
Deprecated.

**Parameters:**

* <code>index</code> - An array index, starting from 0.

**Returns:**

* <code>true</code> if this type filter allows CBOR arrays and the given
 array index is allowed under this type filter; otherwise, <code>
 false</code>.

### ArrayLengthMatches
    public boolean ArrayLengthMatches​(int length)
Deprecated.

**Parameters:**

* <code>length</code> - The length of a CBOR array.

**Returns:**

* <code>true</code> if this filter allows CBOR arrays and an array's length
 is allowed under this filter; otherwise, <code>false</code>.

### ArrayLengthMatches
    public boolean ArrayLengthMatches​(long length)
Deprecated.

**Parameters:**

* <code>length</code> - The length of a CBOR array.

**Returns:**

* <code>true</code> if this filter allows CBOR arrays and an array's length
 is allowed under a filter; otherwise, <code>false</code>.

### ArrayLengthMatches
    public boolean ArrayLengthMatches​(com.upokecenter.numbers.EInteger bigLength)
Deprecated.

**Parameters:**

* <code>bigLength</code> - An arbitrary-precision integer.

**Returns:**

* <code>true</code> if this filter allows CBOR arrays and an array's length
 is allowed under a filter; otherwise, <code>false</code>.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>bigLength</code> is
 null.

### GetSubFilter
    public CBORTypeFilter GetSubFilter​(int index)
Deprecated.

**Parameters:**

* <code>index</code> - A zero-based index of the filter to retrieve.

**Returns:**

* Returns None if the index is out of range, or Any if this filter
 doesn't filter an array. Returns the appropriate filter for the
 array index otherwise.

### GetSubFilter
    public CBORTypeFilter GetSubFilter​(long index)
Deprecated.

**Parameters:**

* <code>index</code> - A zero-based index of the filter to retrieve.

**Returns:**

* Returns None if the index is out of range, or Any if this filter
 doesn't filter an array. Returns the appropriate filter for the
 array index otherwise.

### MajorTypeMatches
    public boolean MajorTypeMatches​(int type)
Deprecated.

**Parameters:**

* <code>type</code> - A CBOR major type from 0 to 7.

**Returns:**

* <code>true</code> if the given CBOR major type matches a major type
 allowed by this filter; otherwise, <code>false</code>.

### NonFPSimpleValueAllowed
    public boolean NonFPSimpleValueAllowed()
Deprecated.

**Returns:**

* <code>true</code> if this filter allows simple values that are not
 floating-point numbers; otherwise, <code>false</code>.

### TagAllowed
    public boolean TagAllowed​(int tag)
Deprecated.

**Parameters:**

* <code>tag</code> - A tag number. Returns false if this is less than 0.

**Returns:**

* <code>true</code> if CBOR objects can have the given tag number;
 otherwise, <code>false</code>.

### TagAllowed
    public boolean TagAllowed​(long longTag)
Deprecated.

**Parameters:**

* <code>longTag</code> - A tag number. Returns false if this is less than 0.

**Returns:**

* <code>true</code> if CBOR objects can have the given tag number;
 otherwise, <code>false</code>.

### TagAllowed
    public boolean TagAllowed​(com.upokecenter.numbers.EInteger bigTag)
Deprecated.

**Parameters:**

* <code>bigTag</code> - A tag number. Returns false if this is less than 0.

**Returns:**

* <code>true</code> if CBOR objects can have the given tag number;
 otherwise, <code>false</code>.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>bigTag</code> is null.

### WithArrayAnyLength
    public CBORTypeFilter WithArrayAnyLength()
Deprecated.

**Returns:**

* A CBORTypeFilter object.

### WithArrayExactLength
    public CBORTypeFilter WithArrayExactLength​(int arrayLength, CBORTypeFilter... elements)
Deprecated.

**Parameters:**

* <code>arrayLength</code> - The desired maximum length of an array.

* <code>elements</code> - An array containing the allowed types for each element in
 the array. There must be at least as many elements here as given in
 the arrayLength parameter.

**Returns:**

* A CBORTypeFilter object.

**Throws:**

* <code>java.lang.IllegalArgumentException</code> - The parameter arrayLength is less than 0.

* <code>java.lang.NullPointerException</code> - The parameter elements is null.

* <code>java.lang.IllegalArgumentException</code> - The parameter elements has fewer elements
 than specified in arrayLength.

### WithArrayMinLength
    public CBORTypeFilter WithArrayMinLength​(int arrayLength, CBORTypeFilter... elements)
Deprecated.

**Parameters:**

* <code>arrayLength</code> - The desired minimum length of an array.

* <code>elements</code> - An array containing the allowed types for each element in
 the array. There must be at least as many elements here as given in
 the arrayLength parameter.

**Returns:**

* A CBORTypeFilter object.

**Throws:**

* <code>java.lang.IllegalArgumentException</code> - The parameter arrayLength is less than 0.

* <code>java.lang.NullPointerException</code> - The parameter elements is null.

* <code>java.lang.IllegalArgumentException</code> - The parameter elements has fewer elements
 than specified in arrayLength.

### WithByteString
    public CBORTypeFilter WithByteString()
Deprecated.

**Returns:**

* A CBORTypeFilter object.

### WithFloatingPoint
    public CBORTypeFilter WithFloatingPoint()
Deprecated.

**Returns:**

* A CBORTypeFilter object.

### WithMap
    public CBORTypeFilter WithMap()
Deprecated.

**Returns:**

* A CBORTypeFilter object.

### WithNegativeInteger
    public CBORTypeFilter WithNegativeInteger()
Deprecated.

**Returns:**

* A CBORTypeFilter object.

### WithTags
    public CBORTypeFilter WithTags​(int... tags)
Deprecated.

**Parameters:**

* <code>tags</code> - An array of the CBOR tags to add to the new filter.

**Returns:**

* A CBORTypeFilter object.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>tags</code> or <code>
 tags</code> is null.

### WithTextString
    public CBORTypeFilter WithTextString()
Deprecated.

**Returns:**

* A CBORTypeFilter object.

### WithUnsignedInteger
    public CBORTypeFilter WithUnsignedInteger()
Deprecated.

**Returns:**

* A CBORTypeFilter object.
