# com.upokecenter.util.ExtendedRational

    @Deprecated public final class ExtendedRational extends java.lang.Object implements java.lang.Comparable<ExtendedRational>

Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers and the
 output of this class's toString method.

## Fields

* `static ExtendedRational NaN`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational NegativeInfinity`<br>
 Deprecated. Negative infinity, less than any other number.
* `static ExtendedRational NegativeZero`<br>
 Deprecated. A rational number for negative zero.
* `static ExtendedRational One`<br>
 Deprecated. The rational number one.
* `static ExtendedRational PositiveInfinity`<br>
 Deprecated. Positive infinity, greater than any other number.
* `static ExtendedRational SignalingNaN`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational Ten`<br>
 Deprecated. The rational number ten.
* `static ExtendedRational Zero`<br>
 Deprecated. A rational number for zero.

## Constructors

* `ExtendedRational​(BigInteger numerator,
                BigInteger denominator) com.upokecenter.ExtendedRational`<br>
 Deprecated. Initializes a new instance of the com.upokecenter.ExtendedRational
 class.

## Methods

* `int compareTo​(ExtendedRational other)`<br>
 Deprecated. Compares this value to another.
* `static ExtendedRational Create​(int numeratorSmall,
      int denominatorSmall)`<br>
 Deprecated. Creates a rational number with the given numerator and denominator.
* `static ExtendedRational Create​(BigInteger numerator,
      BigInteger denominator)`<br>
 Deprecated. Creates a rational number with the given numerator and denominator.
* `boolean equals​(ExtendedRational other)`<br>
 Deprecated. Checks whether this and another value are equal.
* `boolean equals​(java.lang.Object obj)`<br>
 Deprecated. Checks whether this and another value are equal.
* `BigInteger getDenominator()`<br>
 Deprecated. Gets this object's denominator.
* `BigInteger getNumerator()`<br>
 Deprecated. Gets this object's numerator.
* `BigInteger getUnsignedNumerator()`<br>
 Deprecated. Gets this object's numerator with the sign removed.
* `int hashCode()`<br>
 Deprecated. Calculates the hash code for this object.
* `boolean isFinite()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isNegative()`<br>
 Deprecated. Gets a value indicating whether this object's value is negative (including
 negative zero).
* `boolean isZero()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `int signum()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `java.lang.String toString()`<br>
 Deprecated. Converts this object to a text string.

## Field Details

### NaN
    @Deprecated public static final ExtendedRational NaN
Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.

### NegativeInfinity
    public static final ExtendedRational NegativeInfinity
Deprecated.
### NegativeZero
    public static final ExtendedRational NegativeZero
Deprecated.
### One
    public static final ExtendedRational One
Deprecated.
### PositiveInfinity
    public static final ExtendedRational PositiveInfinity
Deprecated.
### SignalingNaN
    @Deprecated public static final ExtendedRational SignalingNaN
Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.

### Ten
    public static final ExtendedRational Ten
Deprecated.
### Zero
    public static final ExtendedRational Zero
Deprecated.
## Method Details

### getDenominator
    public final BigInteger getDenominator()
Deprecated.

**Returns:**

* This object's denominator.

### isFinite
    @Deprecated public final boolean isFinite()
Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> If this object is finite (not infinity or NaN);
 otherwise, . <code>false</code>.

### isNegative
    public final boolean isNegative()
Deprecated.

**Returns:**

* <code>true</code> If this object's value is negative; otherwise,. <code>
 false</code>.

### isZero
    @Deprecated public final boolean isZero()
Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> If this object's value equals 0; otherwise,. <code>
 false</code>.

### getNumerator
    public final BigInteger getNumerator()
Deprecated.

**Returns:**

* This object's numerator. If this object is a not-a-number value,
 returns the diagnostic information (which will be negative if this
 object is negative).

### signum
    @Deprecated public final int signum()
Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* Zero if this value is zero or negative zero; -1 if this value is
 less than 0; and 1 if this value is greater than 0.

### getUnsignedNumerator
    public final BigInteger getUnsignedNumerator()
Deprecated.

**Returns:**

* This object's numerator. If this object is a not-a-number value,
 returns the diagnostic information.

### Create
    public static ExtendedRational Create​(int numeratorSmall, int denominatorSmall)
Deprecated.

**Parameters:**

* <code>numeratorSmall</code> - The parameter <code>numeratorSmall</code> is a 32-bit
 signed integer.

* <code>denominatorSmall</code> - The parameter <code>denominatorSmall</code> is a 32-bit
 signed integer.

**Returns:**

* An arbitrary-precision rational number.

### Create
    public static ExtendedRational Create​(BigInteger numerator, BigInteger denominator)
Deprecated.

**Parameters:**

* <code>numerator</code> - An arbitrary-precision integer.

* <code>denominator</code> - Another arbitrary-precision integer.

**Returns:**

* An arbitrary-precision rational number.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>numerator</code> or
 <code>denominator</code> is null.

### toString
    public java.lang.String toString()
Deprecated.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A string representation of this object. The result can be Infinity,
 NaN, or sNaN (with a minus sign before it for negative values), or a
 number of the following form: [-]numerator/denominator.

### compareTo
    public int compareTo​(ExtendedRational other)
Deprecated.

**Specified by:**

* <code>compareTo</code> in interface <code>java.lang.Comparable&lt;ExtendedRational&gt;</code>

**Parameters:**

* <code>other</code> - The parameter <code>other</code> is an ExtendedRational object.

**Returns:**

* Less than 0 if this value is less than, 0 if equal to, or greater
 than 0 if greater than the other value.

### equals
    public boolean equals​(ExtendedRational other)
Deprecated.

**Parameters:**

* <code>other</code> - The parameter <code>other</code> is an ExtendedRational object.

**Returns:**

* Either
      <code>true</code> or
      <code>false</code> .

### equals
    public boolean equals​(java.lang.Object obj)
Deprecated.

**Overrides:**

* <code>equals</code> in class <code>java.lang.Object</code>

**Parameters:**

* <code>obj</code> - The parameter
      <code>obj</code>
       is an arbitrary object.

**Returns:**

* Either
      <code>true</code> or
      <code>false</code> .

### hashCode
    public int hashCode()
Deprecated.

**Overrides:**

* <code>hashCode</code> in class <code>java.lang.Object</code>

**Returns:**

* A 32-bit signed integer.
