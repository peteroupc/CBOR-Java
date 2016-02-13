# com.upokecenter.util.ExtendedFloat

    public final class ExtendedFloat extends Object implements Comparable<ExtendedFloat>

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.EFloat</code> in the <code>PeterO.Numbers</code>
 library (in .NET), or <code>com.upokecenter.numbers.EFloat</code> in the <code>com.github.peteroupc/numbers</code>
 artifact (in Java). This new class can be used in the
 <code>CBORObject.FromObject(object)</code> method (by including the new
 library in your code, among other things), but this version of the
 CBOR library doesn't include any methods that explicitly take an
 <code>EFloat</code> as a parameter or return value.</b></p> <p>Represents
 an arbitrary-precision binary floating-point number. Consists of an
 integer mantissa and an integer exponent, both arbitrary-precision.
 The value of the number equals mantissa * 2^exponent. This class also
 supports values for negative zero, not-a-number (NaN) values, and
 infinity.</p> <p>Passing a signaling NaN to any arithmetic operation
 shown here will signal the flag FlagInvalid and return a quiet NaN,
 even if another operand to that operation is a quiet NaN, unless
 noted otherwise.</p> <p>Passing a quiet NaN to any arithmetic
 operation shown here will return a quiet NaN, unless noted
 otherwise.</p> <p>Unless noted otherwise, passing a null
 arbitrary-precision binary float argument to any method here will
 throw an exception.</p> <p>When an arithmetic operation signals the
 flag FlagInvalid, FlagOverflow, or FlagDivideByZero, it will not
 throw an exception too, unless the operation's trap is enabled in the
 precision context (see PrecisionContext's Traps property).</p> <p>An
 arbitrary-precision binary float value can be serialized in one of
 the following ways:</p> <ul> <li>By calling the toString() method.
 However, not all strings can be converted back to an
 arbitrary-precision binary float without loss, especially if the
 string has a fractional part.</li> <li>By calling the
 UnsignedMantissa, Exponent, and IsNegative properties, and calling
 the IsInfinity, IsQuietNaN, and IsSignalingNaN methods. The return
 values combined will uniquely identify a particular
 arbitrary-precision binary float value.</li></ul> <p>If an operation
 requires creating an intermediate value that might be too big to fit
 in memory (or might require more than 2 gigabytes of memory to store
 -- due to the current use of a 32-bit integer internally as a
 length), the operation may signal an invalid-operation flag and
 return not-a-number (NaN). In certain rare cases, the compareTo
 method may throw OutOfMemoryError (called OutOfMemoryError in
 Java) in the same circumstances.</p> <p><b>Thread safety:</b>
 Instances of this class are immutable, so they are inherently safe
 for use by multiple threads. Multiple instances of this object with
 the same properties are interchangeable, so they should not be
 compared using the "==" operator (which only checks if each side of
 the operator is the same instance).</p>

## Fields

* `static ExtendedFloat NaN`<br>
 A not-a-number value.
* `static ExtendedFloat NegativeInfinity`<br>
 Negative infinity, less than any other number.
* `static ExtendedFloat NegativeZero`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat One`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat PositiveInfinity`<br>
 Positive infinity, greater than any other number.
* `static ExtendedFloat SignalingNaN`<br>
 A not-a-number value that signals an invalid operation flag when it's
 passed as an argument to any arithmetic operation in
 arbitrary-precision binary float.
* `static ExtendedFloat Ten`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat Zero`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.

## Methods

* `int compareTo(ExtendedFloat other)`<br>
 Not documented yet.
* `static ExtendedFloat Create(BigInteger mantissa,
      BigInteger exponent)`<br>
 Creates a number with the value exponent*2^mantissa.
* `static ExtendedFloat Create(int mantissaSmall,
      int exponentSmall)`<br>
 Creates a number with the value exponent*2^mantissa.
* `boolean equals(ExtendedFloat other)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `boolean equals(Object obj)`<br>
 Determines whether this object's mantissa and exponent are equal to
 those of another object and that other object is an
 arbitrary-precision decimal number.
* `boolean EqualsInternal(ExtendedFloat otherValue)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromString(String str)`<br>
 Not documented yet.
* `static ExtendedFloat FromString(String str,
          int offset,
          int length,
          PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger getExponent()`<br>
 Gets this object's exponent.
* `BigInteger getMantissa()`<br>
 Gets this object's un-scaled value.
* `BigInteger getUnsignedMantissa()`<br>
 Gets the absolute value of this object's un-scaled value.
* `int hashCode()`<br>
 Calculates this object's hash code.
* `boolean IsInfinity()`<br>
 Gets a value indicating whether this object is positive or negative
 infinity.
* `boolean IsNaN()`<br>
 Returns whether this object is a not-a-number value.
* `boolean isNegative()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsNegativeInfinity()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsPositiveInfinity()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsQuietNaN()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsSignalingNaN()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `int signum()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `String toString()`<br>
 Converts this value to a string.

## Field Details

### One
    @Deprecated public static final ExtendedFloat One
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>
### Zero
    @Deprecated public static final ExtendedFloat Zero
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>
### NegativeZero
    @Deprecated public static final ExtendedFloat NegativeZero
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>
### Ten
    @Deprecated public static final ExtendedFloat Ten
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>
### NaN
    public static final ExtendedFloat NaN
A not-a-number value.
### SignalingNaN
    public static final ExtendedFloat SignalingNaN
A not-a-number value that signals an invalid operation flag when it&#x27;s
 passed as an argument to any arithmetic operation in
 arbitrary-precision binary float.
### PositiveInfinity
    public static final ExtendedFloat PositiveInfinity
Positive infinity, greater than any other number.
### NegativeInfinity
    public static final ExtendedFloat NegativeInfinity
Negative infinity, less than any other number.
## Method Details

### getExponent
    public final BigInteger getExponent()
Gets this object&#x27;s exponent. This object&#x27;s value will be an
 integer if the exponent is positive or zero.

**Returns:**

* This object's exponent. This object's value will be an integer if
 the exponent is positive or zero.

### getUnsignedMantissa
    public final BigInteger getUnsignedMantissa()
Gets the absolute value of this object&#x27;s un-scaled value.

**Returns:**

* The absolute value of this object's un-scaled value.

### getMantissa
    public final BigInteger getMantissa()
Gets this object&#x27;s un-scaled value.

**Returns:**

* This object's un-scaled value. Will be negative if this object's
 value is negative (including a negative NaN).

### EqualsInternal
    @Deprecated public boolean EqualsInternal(ExtendedFloat otherValue)
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision binary float.

**Returns:**

* true if this object's mantissa and exponent are equal to those of
 another object; otherwise, false.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### equals
    @Deprecated public boolean equals(ExtendedFloat other)
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Parameters:**

* <code>other</code> - An arbitrary-precision binary float.

**Returns:**

* true if this object's mantissa and exponent are equal to those of
 another object; otherwise, false.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### equals
    public boolean equals(Object obj)
Determines whether this object&#x27;s mantissa and exponent are equal to
 those of another object and that other object is an
 arbitrary-precision decimal number.

**Overrides:**

* <code>equals</code>&nbsp;in class&nbsp;<code>Object</code>

**Parameters:**

* <code>obj</code> - An arbitrary object.

**Returns:**

* true if the objects are equal; otherwise, false .

### hashCode
    public int hashCode()
Calculates this object&#x27;s hash code.

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* This object's hash code.

### Create
    public static ExtendedFloat Create(int mantissaSmall, int exponentSmall)
Creates a number with the value exponent*2^mantissa.

**Parameters:**

* <code>mantissaSmall</code> - The un-scaled value.

* <code>exponentSmall</code> - The binary exponent.

**Returns:**

* An arbitrary-precision binary float.

### Create
    public static ExtendedFloat Create(BigInteger mantissa, BigInteger exponent)
Creates a number with the value exponent*2^mantissa.

**Parameters:**

* <code>mantissa</code> - The un-scaled value.

* <code>exponent</code> - The binary exponent.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>mantissa</code> or
 <code>exponent</code> is null.

### FromString
    @Deprecated public static ExtendedFloat FromString(String str, int offset, int length, PrecisionContext ctx)
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Parameters:**

* <code>str</code> - A text string.

* <code>offset</code> - A zero-based index showing where the desired portion of <code>str</code> begins.

* <code>length</code> - The length, in code units, of the desired portion of <code>str</code> (but not more than <code>str</code> 's length).

* <code>ctx</code> - A PrecisionContext object specifying the precision, rounding, and
 exponent range to apply to the parsed number. Can be null.

**Returns:**

* The parsed number, converted to arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>length</code> is
 less than 0 or greater than <code>str</code> 's length, or <code>str</code> ' s
 length minus <code>offset</code> is less than <code>length</code>.

### FromString
    public static ExtendedFloat FromString(String str)
Not documented yet.

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is not documented yet.

**Returns:**

* An ExtendedFloat object.

### toString
    public String toString()
Converts this value to a string.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object. The value is converted to
 decimal and the decimal form of this number's value is returned.

### IsNegativeInfinity
    @Deprecated public boolean IsNegativeInfinity()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object is negative infinity; otherwise, false .

### IsPositiveInfinity
    @Deprecated public boolean IsPositiveInfinity()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object is positive infinity; otherwise, false .

### IsNaN
    public boolean IsNaN()
Returns whether this object is a not-a-number value.

**Returns:**

* true if this object is a not-a-number value; otherwise, false .

### IsInfinity
    public boolean IsInfinity()
Gets a value indicating whether this object is positive or negative
 infinity.

**Returns:**

* true if this object is positive or negative infinity; otherwise,
 false.

### isNegative
    @Deprecated public final boolean isNegative()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object is negative, including negative zero; otherwise,
 false.

### IsQuietNaN
    @Deprecated public boolean IsQuietNaN()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object is a quiet not-a-number value; otherwise, false.

### IsSignalingNaN
    @Deprecated public boolean IsSignalingNaN()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object is a signaling not-a-number value; otherwise,
 false.

### compareTo
    public int compareTo(ExtendedFloat other)
Not documented yet.

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;ExtendedFloat&gt;</code>

**Parameters:**

* <code>other</code> - The parameter <code>other</code> is not documented yet.

**Returns:**

* A 32-bit signed integer.

### signum
    @Deprecated public final int signum()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* This value's sign: -1 if negative; 1 if positive; 0 if zero.
