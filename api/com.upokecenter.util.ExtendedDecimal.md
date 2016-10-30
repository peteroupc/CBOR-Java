# com.upokecenter.util.ExtendedDecimal

    @Deprecated public final class ExtendedDecimal extends Object implements Comparable<ExtendedDecimal>

Deprecated.&nbsp;
<div class='block'>Use EDecimal from PeterO.Numbers/com.upokecenter.numbers and the output of
this class's toString method.</div>

## Fields

* `static ExtendedDecimal NaN`<br>
 Deprecated.  A not-a-number value.
* `static ExtendedDecimal NegativeInfinity`<br>
 Deprecated.  Negative infinity, less than any other number.
* `static ExtendedDecimal NegativeZero`<br>
 Deprecated.  Represents the number negative zero.
* `static ExtendedDecimal One`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal PositiveInfinity`<br>
 Deprecated.  Positive infinity, greater than any other number.
* `static ExtendedDecimal SignalingNaN`<br>
 Deprecated.  A not-a-number value that signals an invalid operation flag when it's
 passed as an argument to any arithmetic operation in
 arbitrary-precision decimal.
* `static ExtendedDecimal Ten`<br>
 Deprecated.  Represents the number 10.
* `static ExtendedDecimal Zero`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

## Methods

* `int compareTo(ExtendedDecimal other)`<br>
 Deprecated.  Not documented yet.
* `static ExtendedDecimal Create(BigInteger mantissa,
      BigInteger exponent)`<br>
 Deprecated.  Creates a number with the value exponent*10^mantissa.
* `boolean equals(ExtendedDecimal other)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean equals(Object obj)`<br>
 Deprecated.  Determines whether this object's mantissa and exponent are equal to
 those of another object and that other object is an
 arbitrary-precision decimal number.
* `static ExtendedDecimal FromString(String str)`<br>
 Deprecated.  Creates a decimal number from a text string that represents a number.
* `BigInteger getExponent()`<br>
 Deprecated.  Gets this object's exponent.
* `BigInteger getMantissa()`<br>
 Deprecated.  Gets this object's un-scaled value.
* `BigInteger getUnsignedMantissa()`<br>
 Deprecated.  Gets the absolute value of this object's un-scaled value.
* `int hashCode()`<br>
 Deprecated.  Calculates this object's hash code.
* `boolean IsInfinity()`<br>
 Deprecated.  Gets a value indicating whether this object is positive or negative
 infinity.
* `boolean IsNaN()`<br>
 Deprecated.  Gets a value indicating whether this object is not a number (NaN).
* `boolean isNegative()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsQuietNaN()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `int signum()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `double ToDouble()`<br>
 Deprecated.  Converts this value to a 64-bit floating-point number.
* `float ToSingle()`<br>
 Deprecated.  Converts this value to a 32-bit floating-point number.
* `String toString()`<br>
 Deprecated.  Converts this value to a string.

## Field Details

### One
    @Deprecated public static final ExtendedDecimal One
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
### Zero
    @Deprecated public static final ExtendedDecimal Zero
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
### NegativeZero
    public static final ExtendedDecimal NegativeZero
Deprecated.&nbsp;
### Ten
    public static final ExtendedDecimal Ten
Deprecated.&nbsp;
### NaN
    public static final ExtendedDecimal NaN
Deprecated.&nbsp;
### SignalingNaN
    public static final ExtendedDecimal SignalingNaN
Deprecated.&nbsp;
### PositiveInfinity
    public static final ExtendedDecimal PositiveInfinity
Deprecated.&nbsp;
### NegativeInfinity
    public static final ExtendedDecimal NegativeInfinity
Deprecated.&nbsp;
## Method Details

### getExponent
    public final BigInteger getExponent()
Deprecated.&nbsp;

**Returns:**

* This object's exponent. This object's value will be an integer if
 the exponent is positive or zero.

### getUnsignedMantissa
    public final BigInteger getUnsignedMantissa()
Deprecated.&nbsp;

**Returns:**

* The absolute value of this object's un-scaled value.

### getMantissa
    public final BigInteger getMantissa()
Deprecated.&nbsp;

**Returns:**

* This object's un-scaled value. Will be negative if this object's
 value is negative (including a negative NaN).

### equals
    @Deprecated public boolean equals(ExtendedDecimal other)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>other</code> - An arbitrary-precision decimal number.

**Returns:**

* <code>true</code> if this object's mantissa and exponent are equal to
 those of another object; otherwise, <code>false</code> .

### equals
    public boolean equals(Object obj)
Deprecated.&nbsp;

**Overrides:**

* <code>equals</code>&nbsp;in class&nbsp;<code>Object</code>

**Parameters:**

* <code>obj</code> - An arbitrary object.

**Returns:**

* <code>true</code> if the objects are equal; otherwise, <code>false</code>.

### hashCode
    public int hashCode()
Deprecated.&nbsp;

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* This object's hash code.

### Create
    public static ExtendedDecimal Create(BigInteger mantissa, BigInteger exponent)
Deprecated.&nbsp;

**Parameters:**

* <code>mantissa</code> - The un-scaled value.

* <code>exponent</code> - The decimal exponent.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>mantissa</code> or
 <code>exponent</code> is null.

### FromString
    public static ExtendedDecimal FromString(String str)
Deprecated.&nbsp;

**Parameters:**

* <code>str</code> - A string that represents a number.

**Returns:**

* An arbitrary-precision decimal number with the same value as the
 given string.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>NumberFormatException</code> - The parameter <code>str</code> is not a correctly
 formatted number string.

### ToSingle
    public float ToSingle()
Deprecated.&nbsp;

**Returns:**

* The closest 32-bit floating-point number to this value. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 32-bit floating point number.

### ToDouble
    public double ToDouble()
Deprecated.&nbsp;

**Returns:**

* The closest 64-bit floating-point number to this value. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 64-bit floating point number.

### toString
    public String toString()
Deprecated.&nbsp;

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object.

### IsNaN
    public boolean IsNaN()
Deprecated.&nbsp;

**Returns:**

* <code>true</code> if this object is not a number (NaN); otherwise, <code>false</code>.

### IsInfinity
    public boolean IsInfinity()
Deprecated.&nbsp;

**Returns:**

* <code>true</code> if this object is positive or negative infinity;
 otherwise, <code>false</code>.

### isNegative
    @Deprecated public final boolean isNegative()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> If this object is negative, including negative zero;
 otherwise, <code>false</code>.

### IsQuietNaN
    @Deprecated public boolean IsQuietNaN()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is a quiet not-a-number value;
 otherwise, <code>false</code>.

### compareTo
    public int compareTo(ExtendedDecimal other)
Deprecated.&nbsp;

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;ExtendedDecimal&gt;</code>

**Parameters:**

* <code>other</code> - An ExtendedDecimal object.

**Returns:**

* A 32-bit signed integer.

### signum
    @Deprecated public final int signum()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* This value's sign: -1 if negative; 1 if positive; 0 if zero.
