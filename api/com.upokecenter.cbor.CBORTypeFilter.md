# com.upokecenter.cbor.CBORTypeFilter

    public final class CBORTypeFilter extends Object

Specifies what kinds of CBOR objects a tag can be. This class is used when a
 CBOR object is being read from a data stream. This class can't be
 inherited; this is a change in version 2.0 from previous versions,
 where the class was inadvertently left inheritable.

## Fields

* `static CBORTypeFilter Any`<br>
 A filter that allows any CBOR object.
* `static CBORTypeFilter ByteString`<br>
 A filter that allows byte strings.
* `static CBORTypeFilter NegativeInteger`<br>
 A filter that allows negative integers.
* `static CBORTypeFilter None`<br>
 A filter that allows no CBOR types.
* `static CBORTypeFilter TextString`<br>
 A filter that allows text strings.
* `static CBORTypeFilter UnsignedInteger`<br>
 A filter that allows unsigned integers.

## Constructors

* `CBORTypeFilter()`<br>

## Methods

* `boolean ArrayIndexAllowed​(int index)`<br>
 Determines whether this type filter allows CBOR arrays and the given array
 index is allowed under this type filter.
* `boolean ArrayLengthMatches​(int length)`<br>
 Returns whether an array's length is allowed under this filter.
* `boolean ArrayLengthMatches​(long length)`<br>
 Returns whether an array's length is allowed under a filter.
* `boolean ArrayLengthMatches​(com.upokecenter.numbers.EInteger bigLength)`<br>
 Returns whether an array's length is allowed under a filter.
* `CBORTypeFilter GetSubFilter​(int index)`<br>
 Gets the type filter for this array filter by its index.
* `CBORTypeFilter GetSubFilter​(long index)`<br>
 Gets the type filter for this array filter by its index.
* `boolean MajorTypeMatches​(int type)`<br>
 Returns whether the given CBOR major type matches a major type allowed by
 this filter.
* `boolean NonFPSimpleValueAllowed()`<br>
 Returns whether this filter allows simple values that are not floating-point
 numbers.
* `boolean TagAllowed​(int tag)`<br>
 Gets a value indicating whether CBOR objects can have the given tag number.
* `boolean TagAllowed​(long longTag)`<br>
 Gets a value indicating whether CBOR objects can have the given tag number.
* `boolean TagAllowed​(com.upokecenter.numbers.EInteger bigTag)`<br>
 Gets a value indicating whether CBOR objects can have the given tag number.
* `CBORTypeFilter WithArrayAnyLength()`<br>
 Copies this filter and includes arrays of any length in the new filter.
* `CBORTypeFilter WithArrayExactLength​(int arrayLength,
                    CBORTypeFilter... elements)`<br>
 Copies this filter and includes CBOR arrays with an exact length to the new
 filter.
* `CBORTypeFilter WithArrayMinLength​(int arrayLength,
                  CBORTypeFilter... elements)`<br>
 Copies this filter and includes CBOR arrays with at least a given length to
 the new filter.
* `CBORTypeFilter WithByteString()`<br>
 Copies this filter and includes byte strings in the new filter.
* `CBORTypeFilter WithFloatingPoint()`<br>
 Copies this filter and includes floating-point numbers in the new filter.
* `CBORTypeFilter WithMap()`<br>
 Copies this filter and includes maps in the new filter.
* `CBORTypeFilter WithNegativeInteger()`<br>
 Copies this filter and includes negative integers in the new filter.
* `CBORTypeFilter WithTags​(int... tags)`<br>
 Copies this filter and includes a set of valid CBOR tags in the new filter.
* `CBORTypeFilter WithTextString()`<br>
 Copies this filter and includes text strings in the new filter.
* `CBORTypeFilter WithUnsignedInteger()`<br>
 Copies this filter and includes unsigned integers in the new filter.

## Field Details

### Any
    public static final CBORTypeFilter Any
A filter that allows any CBOR object.
### ByteString
    public static final CBORTypeFilter ByteString
A filter that allows byte strings.
### NegativeInteger
    public static final CBORTypeFilter NegativeInteger
A filter that allows negative integers.
### None
    public static final CBORTypeFilter None
A filter that allows no CBOR types.
### TextString
    public static final CBORTypeFilter TextString
A filter that allows text strings.
### UnsignedInteger
    public static final CBORTypeFilter UnsignedInteger
A filter that allows unsigned integers.
## Method Details

### ArrayIndexAllowed
    public boolean ArrayIndexAllowed​(int index)
Determines whether this type filter allows CBOR arrays and the given array
 index is allowed under this type filter.

**Parameters:**

* <code>index</code> - An array index, starting from 0.

**Returns:**

* <code>true</code> if this type filter allows CBOR arrays and the given
 array index is allowed under this type filter; otherwise, <code>
 false</code>.

### ArrayLengthMatches
    public boolean ArrayLengthMatches​(int length)
Returns whether an array's length is allowed under this filter.

**Parameters:**

* <code>length</code> - The length of a CBOR array.

**Returns:**

* <code>true</code> if this filter allows CBOR arrays and an array's length
 is allowed under this filter; otherwise, <code>false</code>.

### ArrayLengthMatches
    public boolean ArrayLengthMatches​(long length)
Returns whether an array's length is allowed under a filter.

**Parameters:**

* <code>length</code> - The length of a CBOR array.

**Returns:**

* <code>true</code> if this filter allows CBOR arrays and an array's length
 is allowed under a filter; otherwise, <code>false</code> .

### ArrayLengthMatches
    public boolean ArrayLengthMatches​(com.upokecenter.numbers.EInteger bigLength)
Returns whether an array's length is allowed under a filter.

**Parameters:**

* <code>bigLength</code> - An arbitrary-precision integer.

**Returns:**

* <code>true</code> if this filter allows CBOR arrays and an array's length
 is allowed under a filter; otherwise, <code>false</code> .

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigLength</code> is
 null.

### GetSubFilter
    public CBORTypeFilter GetSubFilter​(int index)
Gets the type filter for this array filter by its index.

**Parameters:**

* <code>index</code> - A zero-based index of the filter to retrieve.

**Returns:**

* Returns None if the index is out of range, or Any if this filter
 doesn't filter an array. Returns the appropriate filter for the array
 index otherwise.

### GetSubFilter
    public CBORTypeFilter GetSubFilter​(long index)
Gets the type filter for this array filter by its index.

**Parameters:**

* <code>index</code> - A zero-based index of the filter to retrieve.

**Returns:**

* Returns None if the index is out of range, or Any if this filter
 doesn't filter an array. Returns the appropriate filter for the array
 index otherwise.

### MajorTypeMatches
    public boolean MajorTypeMatches​(int type)
Returns whether the given CBOR major type matches a major type allowed by
 this filter.

**Parameters:**

* <code>type</code> - A CBOR major type from 0 to 7.

**Returns:**

* <code>true</code> if the given CBOR major type matches a major type
 allowed by this filter; otherwise, <code>false</code>.

### NonFPSimpleValueAllowed
    public boolean NonFPSimpleValueAllowed()
Returns whether this filter allows simple values that are not floating-point
 numbers.

**Returns:**

* <code>true</code> if this filter allows simple values that are not
 floating-point numbers; otherwise, <code>false</code>.

### TagAllowed
    public boolean TagAllowed​(int tag)
Gets a value indicating whether CBOR objects can have the given tag number.

**Parameters:**

* <code>tag</code> - A tag number. Returns false if this is less than 0.

**Returns:**

* <code>true</code> if CBOR objects can have the given tag number;
 otherwise, <code>false</code>.

### TagAllowed
    public boolean TagAllowed​(long longTag)
Gets a value indicating whether CBOR objects can have the given tag number.

**Parameters:**

* <code>longTag</code> - A tag number. Returns false if this is less than 0.

**Returns:**

* <code>true</code> if CBOR objects can have the given tag number;
 otherwise, <code>false</code>.

### TagAllowed
    public boolean TagAllowed​(com.upokecenter.numbers.EInteger bigTag)
Gets a value indicating whether CBOR objects can have the given tag number.

**Parameters:**

* <code>bigTag</code> - A tag number. Returns false if this is less than 0.

**Returns:**

* <code>true</code> if CBOR objects can have the given tag number;
 otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigTag</code> is null.

### WithArrayAnyLength
    public CBORTypeFilter WithArrayAnyLength()
Copies this filter and includes arrays of any length in the new filter.

**Returns:**

* A CBORTypeFilter object.

### WithArrayExactLength
    public CBORTypeFilter WithArrayExactLength​(int arrayLength, CBORTypeFilter... elements)
Copies this filter and includes CBOR arrays with an exact length to the new
 filter.

**Parameters:**

* <code>arrayLength</code> - The desired maximum length of an array.

* <code>elements</code> - An array containing the allowed types for each element in
 the array. There must be at least as many elements here as given in
 the arrayLength parameter.

**Returns:**

* A CBORTypeFilter object.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter arrayLength is less than 0.

* <code>NullPointerException</code> - The parameter elements is null.

* <code>IllegalArgumentException</code> - The parameter elements has fewer elements
 than specified in arrayLength.

### WithArrayMinLength
    public CBORTypeFilter WithArrayMinLength​(int arrayLength, CBORTypeFilter... elements)
Copies this filter and includes CBOR arrays with at least a given length to
 the new filter.

**Parameters:**

* <code>arrayLength</code> - The desired minimum length of an array.

* <code>elements</code> - An array containing the allowed types for each element in
 the array. There must be at least as many elements here as given in
 the arrayLength parameter.

**Returns:**

* A CBORTypeFilter object.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter arrayLength is less than 0.

* <code>NullPointerException</code> - The parameter elements is null.

* <code>IllegalArgumentException</code> - The parameter elements has fewer elements
 than specified in arrayLength.

### WithByteString
    public CBORTypeFilter WithByteString()
Copies this filter and includes byte strings in the new filter.

**Returns:**

* A CBORTypeFilter object.

### WithFloatingPoint
    public CBORTypeFilter WithFloatingPoint()
Copies this filter and includes floating-point numbers in the new filter.

**Returns:**

* A CBORTypeFilter object.

### WithMap
    public CBORTypeFilter WithMap()
Copies this filter and includes maps in the new filter.

**Returns:**

* A CBORTypeFilter object.

### WithNegativeInteger
    public CBORTypeFilter WithNegativeInteger()
Copies this filter and includes negative integers in the new filter.

**Returns:**

* A CBORTypeFilter object.

### WithTags
    public CBORTypeFilter WithTags​(int... tags)
Copies this filter and includes a set of valid CBOR tags in the new filter.

**Parameters:**

* <code>tags</code> - An array of the CBOR tags to add to the new filter.

**Returns:**

* A CBORTypeFilter object.

### WithTextString
    public CBORTypeFilter WithTextString()
Copies this filter and includes text strings in the new filter.

**Returns:**

* A CBORTypeFilter object.

### WithUnsignedInteger
    public CBORTypeFilter WithUnsignedInteger()
Copies this filter and includes unsigned integers in the new filter.

**Returns:**

* A CBORTypeFilter object.
