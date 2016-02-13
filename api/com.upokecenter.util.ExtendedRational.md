# com.upokecenter.util.ExtendedRational

    public final class ExtendedRational extends Object implements Comparable<ExtendedRational>

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.ERational</code> in the <code>PeterO.Numbers</code>
 library (in .NET), or <code>com.upokecenter.numbers.ERational</code> in the
 <code>com.github.peteroupc/numbers</code>
 artifact (in Java). This new class can be used in the
 <code>CBORObject.FromObject(object)</code> method (by including the new
 library in your code, among other things), but this version of the
 CBOR library doesn't include any methods that explicitly take an
 <code>ERational</code> as a parameter or return value.</b></p>
 Arbitrary-precision rational number. This class can't be inherited;
 this is a change in version 2.0 from previous versions, where the
 class was inadvertently left inheritable. <p><b>Thread safety:</b>
 Instances of this class are immutable, so they are inherently safe
 for use by multiple threads. Multiple instances of this object with
 the same properties are interchangeable, so they should not be
 compared using the "==" operator (which only checks if each side of
 the operator is the same instance).</p>

## Fields

* `static ExtendedRational NaN`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational NegativeInfinity`<br>
 Negative infinity, less than any other number.
* `static ExtendedRational NegativeZero`<br>
 A rational number for negative zero.
* `static ExtendedRational One`<br>
 The rational number one.
* `static ExtendedRational PositiveInfinity`<br>
 Positive infinity, greater than any other number.
* `static ExtendedRational SignalingNaN`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational Ten`<br>
 The rational number ten.
* `static ExtendedRational Zero`<br>
 A rational number for zero.

## Constructors

* `ExtendedRational(BigInteger numerator,
                BigInteger denominator)`<br>
 Initializes a new instance of the
 class.

## Methods

* `int compareTo(ExtendedRational other)`<br>
 Not documented yet.
* `static ExtendedRational Create(BigInteger numerator,
      BigInteger denominator)`<br>
 Creates a rational number with the given numerator and denominator.
* `static ExtendedRational Create(int numeratorSmall,
      int denominatorSmall)`<br>
 Creates a rational number with the given numerator and denominator.
* `boolean equals(ExtendedRational other)`<br>
 Not documented yet.
* `boolean equals(Object obj)`<br>
* `BigInteger getDenominator()`<br>
 Gets this object's denominator.
* `BigInteger getNumerator()`<br>
 Gets this object's numerator.
* `BigInteger getUnsignedNumerator()`<br>
 Gets this object's numerator with the sign removed.
* `int hashCode()`<br>
* `boolean isFinite()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isNegative()`<br>
 Gets a value indicating whether this object's value is negative (including
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
 Converts this object to a text string.

## Field Details

### NaN
    @Deprecated public static final ExtendedRational NaN
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</span>
### NegativeInfinity
    public static final ExtendedRational NegativeInfinity
Negative infinity, less than any other number.
### NegativeZero
    public static final ExtendedRational NegativeZero
A rational number for negative zero.
### One
    public static final ExtendedRational One
The rational number one.
### PositiveInfinity
    public static final ExtendedRational PositiveInfinity
Positive infinity, greater than any other number.
### SignalingNaN
    @Deprecated public static final ExtendedRational SignalingNaN
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</span>
### Ten
    public static final ExtendedRational Ten
The rational number ten.
### Zero
    public static final ExtendedRational Zero
A rational number for zero.
## Method Details

### getDenominator
    public final BigInteger getDenominator()
Gets this object's denominator.

**Returns:**

* This object's denominator.

### isFinite
    @Deprecated public final boolean isFinite()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object is finite (not infinity or NaN); otherwise,
 false.

### isNegative
    public final boolean isNegative()
Gets a value indicating whether this object's value is negative (including
 negative zero).

**Returns:**

* true if this object's value is negative; otherwise, false.

### isZero
    @Deprecated public final boolean isZero()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object's value equals 0; otherwise, false.

### getNumerator
    public final BigInteger getNumerator()
Gets this object's numerator.

**Returns:**

* This object's numerator. If this object is a not-a-number value,
 returns the diagnostic information (which will be negative if this
 object is negative).

### signum
    @Deprecated public final int signum()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use ERational from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* Zero if this value is zero or negative zero; -1 if this value is
 less than 0; and 1 if this value is greater than 0.

### getUnsignedNumerator
    public final BigInteger getUnsignedNumerator()
Gets this object's numerator with the sign removed.

**Returns:**

* This object's numerator. If this object is a not-a-number value,
 returns the diagnostic information.

### Create
    public static ExtendedRational Create(int numeratorSmall, int denominatorSmall)
Creates a rational number with the given numerator and denominator.

**Parameters:**

* <code>numeratorSmall</code> - A 32-bit signed integer.

* <code>denominatorSmall</code> - A 32-bit signed integer. (2).

**Returns:**

* An arbitrary-precision rational number.

### Create
    public static ExtendedRational Create(BigInteger numerator, BigInteger denominator)
Creates a rational number with the given numerator and denominator.

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
Converts this object to a text string.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object. The result can be Infinity,
 NaN, or sNaN (with a minus sign before it for negative values), or a
 number of the following form: [-]numerator/denominator.

### compareTo
    public int compareTo(ExtendedRational other)
Not documented yet.

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;ExtendedRational&gt;</code>

**Parameters:**

* <code>other</code> - An ExtendedRational object.

**Returns:**

* A 32-bit signed integer.

### equals
    public boolean equals(ExtendedRational other)
Not documented yet.

**Parameters:**

* <code>other</code> - An ExtendedRational object.

**Returns:**

* A Boolean object.

### equals
    public boolean equals(Object obj)

**Overrides:**

* <code>equals</code>&nbsp;in class&nbsp;<code>Object</code>

**Parameters:**

* <code>obj</code> -

### hashCode
    public int hashCode()

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>
