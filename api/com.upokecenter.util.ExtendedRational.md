# com.upokecenter.util.ExtendedRational

    @Deprecated public final class ExtendedRational extends Object implements Comparable<ExtendedRational>

<strong>Deprecated.</strong>&nbsp;
<div class='block'><i>Use ERational from PeterO.Numbers/com.upokecenter.numbers and the output of
this class's toString method.</i></div>

## Fields

* `static ExtendedRational NaN`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational NegativeInfinity`<br>
 Deprecated.  Negative infinity, less than any other number.
* `static ExtendedRational NegativeZero`<br>
 Deprecated.  A rational number for negative zero.
* `static ExtendedRational One`<br>
 Deprecated.  The rational number one.
* `static ExtendedRational PositiveInfinity`<br>
 Deprecated.  Positive infinity, greater than any other number.
* `static ExtendedRational SignalingNaN`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational Ten`<br>
 Deprecated.  The rational number ten.
* `static ExtendedRational Zero`<br>
 Deprecated.  A rational number for zero.

## Constructors

* `ExtendedRational(BigInteger numerator,
                BigInteger denominator)`<br>
 Deprecated.  Initializes a new instance of the
 class.

## Methods

* `int compareTo(ExtendedRational other)`<br>
 Deprecated.  Not documented yet.
* `static ExtendedRational Create(BigInteger numerator,
      BigInteger denominator)`<br>
 Deprecated.  Creates a rational number with the given numerator and denominator.
* `static ExtendedRational Create(int numeratorSmall,
      int denominatorSmall)`<br>
 Deprecated.  Creates a rational number with the given numerator and denominator.
* `boolean equals(ExtendedRational other)`<br>
 Deprecated.  Not documented yet.
* `boolean equals(Object obj)`<br>
 Deprecated.
* `BigInteger getDenominator()`<br>
 Deprecated.  Gets this object's denominator.
* `BigInteger getNumerator()`<br>
 Deprecated.  Gets this object's numerator.
* `BigInteger getUnsignedNumerator()`<br>
 Deprecated.  Gets this object's numerator with the sign removed.
* `int hashCode()`<br>
 Deprecated.
* `boolean isFinite()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isNegative()`<br>
 Deprecated.  Gets a value indicating whether this object's value is negative (including
 negative zero).
* `boolean isZero()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `int signum()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `String toString()`<br>
 Deprecated.  Converts this object to a text string.

## Field Details

### NaN
    @Deprecated public static final ExtendedRational NaN
Deprecated.&nbsp;<i>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</i>
### NegativeInfinity
    public static final ExtendedRational NegativeInfinity
Deprecated.&nbsp;
### NegativeZero
    public static final ExtendedRational NegativeZero
Deprecated.&nbsp;
### One
    public static final ExtendedRational One
Deprecated.&nbsp;
### PositiveInfinity
    public static final ExtendedRational PositiveInfinity
Deprecated.&nbsp;
### SignalingNaN
    @Deprecated public static final ExtendedRational SignalingNaN
Deprecated.&nbsp;<i>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</i>
### Ten
    public static final ExtendedRational Ten
Deprecated.&nbsp;
### Zero
    public static final ExtendedRational Zero
Deprecated.&nbsp;
## Method Details

### getDenominator
    public final BigInteger getDenominator()
Deprecated.&nbsp;

**Returns:**

* This object's denominator.

### isFinite
    @Deprecated public final boolean isFinite()
Deprecated.&nbsp;<i>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* true if this object is finite (not infinity or NaN); otherwise,
 false.

### isNegative
    public final boolean isNegative()
Deprecated.&nbsp;

**Returns:**

* true if this object's value is negative; otherwise, false.

### isZero
    @Deprecated public final boolean isZero()
Deprecated.&nbsp;<i>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* true if this object's value equals 0; otherwise, false.

### getNumerator
    public final BigInteger getNumerator()
Deprecated.&nbsp;

**Returns:**

* This object's numerator. If this object is a not-a-number value,
 returns the diagnostic information (which will be negative if this
 object is negative).

### signum
    @Deprecated public final int signum()
Deprecated.&nbsp;<i>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* Zero if this value is zero or negative zero; -1 if this value is
 less than 0; and 1 if this value is greater than 0.

### getUnsignedNumerator
    public final BigInteger getUnsignedNumerator()
Deprecated.&nbsp;

**Returns:**

* This object's numerator. If this object is a not-a-number value,
 returns the diagnostic information.

### Create
    public static ExtendedRational Create(int numeratorSmall, int denominatorSmall)
Deprecated.&nbsp;

**Parameters:**

* <code>numeratorSmall</code> - A 32-bit signed integer.

* <code>denominatorSmall</code> - A 32-bit signed integer. (2).

**Returns:**

* An arbitrary-precision rational number.

### Create
    public static ExtendedRational Create(BigInteger numerator, BigInteger denominator)
Deprecated.&nbsp;

**Parameters:**

* <code>numerator</code> - An arbitrary-precision integer.

* <code>denominator</code> - Another arbitrary-precision integer.

**Returns:**

* An arbitrary-precision rational number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>numerator</code> or
 <code>denominator</code> is null.

### toString
    public String toString()
Deprecated.&nbsp;

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object. The result can be Infinity,
 NaN, or sNaN (with a minus sign before it for negative values), or a
 number of the following form: [-]numerator/denominator.

### compareTo
    public int compareTo(ExtendedRational other)
Deprecated.&nbsp;

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;ExtendedRational&gt;</code>

**Parameters:**

* <code>other</code> - An ExtendedRational object.

**Returns:**

* A 32-bit signed integer.

### equals
    public boolean equals(ExtendedRational other)
Deprecated.&nbsp;

**Parameters:**

* <code>other</code> - An ExtendedRational object.

**Returns:**

* A Boolean object.

### equals
    public boolean equals(Object obj)
Deprecated.&nbsp;

**Overrides:**

* <code>equals</code>&nbsp;in class&nbsp;<code>Object</code>

**Parameters:**

* <code>obj</code> -

### hashCode
    public int hashCode()
Deprecated.&nbsp;

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>
