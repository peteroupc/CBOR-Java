# com.upokecenter.util.ExtendedRational

    public final class ExtendedRational extends Object implements Comparable<ExtendedRational>

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.ERational</code> in the <code>PeterO.Numbers</code>
 library (in .NET), or <code>com.upokecenter.numbers.ERational</code> in the
 <code>com.github.peteroupc/numbers</code>
 artifact (in Java). This new class can be used in the
 <code>CBORObject.FromObject(Object)</code> method (by including the new
 library in your code, among other things), but this version of the
 CBOR library doesn't include any methods that explicitly take an
 <code>ERational</code> as a parameter or return
 value.</b></p>Arbitrary-precision rational number. This class cannot
 be inherited; this is a change in version 2.0 from previous versions,
 where the class was inadvertently left inheritable. <p><b>Thread
 safety:</b>Instances of this class are immutable, so they are
 inherently safe for use by multiple threads. Multiple instances of
 this object with the same properties are interchangeable, so they
 should not be compared using the "==" operator (which only checks if
 each side of the operator is the same instance).</p>

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
                BigInteger denominator) com.upokecenter.ExtendedRational`<br>
 Initializes a new instance of the com.upokecenter.ExtendedRational
 class.

## Methods

* `ExtendedRational Abs()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedRational Add(ExtendedRational otherValue)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `int compareTo(ExtendedRational other)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `int CompareToBinary(ExtendedFloat other)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `int CompareToDecimal(ExtendedDecimal other)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational Create(BigInteger numerator,
      BigInteger denominator)`<br>
 Creates a rational number with the given numerator and denominator.
* `static ExtendedRational Create(int numeratorSmall,
      int denominatorSmall)`<br>
 Creates a rational number with the given numerator and denominator.
* `static ExtendedRational CreateNaN(BigInteger diag)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational CreateNaN(BigInteger diag,
         boolean signaling,
         boolean negative)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedRational Divide(ExtendedRational otherValue)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean equals(ExtendedRational other)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean equals(Object obj)`<br>
 Determines whether this object and another object are equal.
* `static ExtendedRational FromBigInteger(BigInteger bigint)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational FromDouble(double flt)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational FromExtendedDecimal(ExtendedDecimal ef)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational FromExtendedFloat(ExtendedFloat ef)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational FromInt32(int smallint)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational FromInt64(long longInt)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedRational FromSingle(float flt)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger getDenominator()`<br>
 Gets this object's denominator.
* `BigInteger getNumerator()`<br>
 Gets this object's numerator.
* `BigInteger getUnsignedNumerator()`<br>
 Gets this object's numerator with the sign removed.
* `int hashCode()`<br>
 Returns the hash code for this instance.
* `boolean isFinite()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsInfinity()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsNaN()`<br>
 Returns whether this object is a not-a-number value.
* `boolean isNegative()`<br>
 Gets a value indicating whether this object's value is negative (including
 negative zero).
* `boolean IsNegativeInfinity()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsPositiveInfinity()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsQuietNaN()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsSignalingNaN()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isZero()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedRational Multiply(ExtendedRational otherValue)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedRational Negate()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedRational Remainder(ExtendedRational otherValue)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `int signum()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedRational Subtract(ExtendedRational otherValue)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger ToBigInteger()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger ToBigIntegerExact()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `double ToDouble()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal ToExtendedDecimal()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal ToExtendedDecimal(PrecisionContext ctx)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal ToExtendedDecimalExactIfPossible(PrecisionContext ctx)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat ToExtendedFloat()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat ToExtendedFloat(PrecisionContext ctx)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat ToExtendedFloatExactIfPossible(PrecisionContext ctx)`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `float ToSingle()`<br>
 Deprecated.
Use ERational from PeterO.Numbers/com.upokecenter.numbers.
 Use ERational from PeterO.Numbers/com.upokecenter.numbers.
* `String toString()`<br>
 Converts this object to a text string.

## Field Details

### NaN
    @Deprecated public static final ExtendedRational NaN
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.
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
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.
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
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is finite (not infinity or NaN);
 otherwise, <code>false</code>.

### isNegative
    public final boolean isNegative()
Gets a value indicating whether this object's value is negative (including
 negative zero).

**Returns:**

* <code>true</code> if this object's value is negative; otherwise, <code>false</code>.

### isZero
    @Deprecated public final boolean isZero()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object's value equals 0; otherwise, <code>false</code>.

### getNumerator
    public final BigInteger getNumerator()
Gets this object's numerator.

**Returns:**

* This object's numerator. If this object is a not-a-number value,
 returns the diagnostic information (which will be negative if this
 object is negative).

### signum
    @Deprecated public final int signum()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

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

### CreateNaN
    @Deprecated public static ExtendedRational CreateNaN(BigInteger diag)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>diag</code> - A number to use as diagnostic information associated with this
 object. If none is needed, should be zero.

**Returns:**

* An arbitrary-precision rational number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>diag</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>diag</code> is less than 0.

### CreateNaN
    @Deprecated public static ExtendedRational CreateNaN(BigInteger diag, boolean signaling, boolean negative)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>diag</code> - A number to use as diagnostic information associated with this
 object. If none is needed, should be zero.

* <code>signaling</code> - Whether the return value will be signaling (true) or quiet
 (false).

* <code>negative</code> - Whether the return value is negative.

**Returns:**

* An arbitrary-precision rational number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>diag</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>diag</code> is less than 0.

### FromBigInteger
    @Deprecated public static ExtendedRational FromBigInteger(BigInteger bigint)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigint</code> - An arbitrary-precision integer.

**Returns:**

* The exact value of the integer as a rational number.

### FromDouble
    @Deprecated public static ExtendedRational FromDouble(double flt)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>flt</code> - A 64-bit floating-point number.

**Returns:**

* A rational number with the same value as <code>flt</code>.

### FromExtendedDecimal
    @Deprecated public static ExtendedRational FromExtendedDecimal(ExtendedDecimal ef)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ef</code> - An arbitrary-precision decimal number.

**Returns:**

* An arbitrary-precision rational number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>ef</code> is null.

### FromExtendedFloat
    @Deprecated public static ExtendedRational FromExtendedFloat(ExtendedFloat ef)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ef</code> - An arbitrary-precision binary float.

**Returns:**

* An arbitrary-precision rational number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>ef</code> is null.

### FromInt32
    @Deprecated public static ExtendedRational FromInt32(int smallint)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>smallint</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision rational number.

### FromInt64
    @Deprecated public static ExtendedRational FromInt64(long longInt)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>longInt</code> - A 64-bit signed integer.

**Returns:**

* An arbitrary-precision rational number.

### FromSingle
    @Deprecated public static ExtendedRational FromSingle(float flt)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>flt</code> - A 32-bit floating-point number.

**Returns:**

* A rational number with the same value as <code>flt</code>.

### Abs
    @Deprecated public ExtendedRational Abs()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision rational number.

### Add
    @Deprecated public ExtendedRational Add(ExtendedRational otherValue)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - Another arbitrary-precision rational number.

**Returns:**

* The sum of the two numbers. Returns not-a-number (NaN) if either
 operand is NaN.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### compareTo
    @Deprecated public int compareTo(ExtendedRational other)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;ExtendedRational&gt;</code>

**Parameters:**

* <code>other</code> - An arbitrary-precision rational number.

**Returns:**

* Zero if the values are equal; a negative number if this instance is
 less, or a positive number if this instance is greater.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### CompareToBinary
    @Deprecated public int CompareToBinary(ExtendedFloat other)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>other</code> - An arbitrary-precision binary float.

**Returns:**

* Zero if the values are equal; a negative number if this instance is
 less, or a positive number if this instance is greater.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### CompareToDecimal
    @Deprecated public int CompareToDecimal(ExtendedDecimal other)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>other</code> - An arbitrary-precision decimal number.

**Returns:**

* Zero if the values are equal; a negative number if this instance is
 less, or a positive number if this instance is greater.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### Divide
    @Deprecated public ExtendedRational Divide(ExtendedRational otherValue)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision rational number.

**Returns:**

* The quotient of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### equals
    public boolean equals(Object obj)
Determines whether this object and another object are equal.

**Overrides:**

* <code>equals</code>&nbsp;in class&nbsp;<code>Object</code>

**Parameters:**

* <code>obj</code> - An arbitrary object.

**Returns:**

* <code>true</code> if the objects are equal; otherwise, <code>false</code>.

### equals
    @Deprecated public boolean equals(ExtendedRational other)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>other</code> - Another arbitrary-precision rational number.

**Returns:**

* <code>true</code> if this object's properties are equal to the properties
 of another rational number object; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### hashCode
    public int hashCode()
Returns the hash code for this instance.

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A 32-bit hash code.

### IsInfinity
    @Deprecated public boolean IsInfinity()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object's value is infinity; otherwise, <code>false</code>.

### IsNaN
    public boolean IsNaN()
Returns whether this object is a not-a-number value.

**Returns:**

* <code>true</code> if this object is a not-a-number value; otherwise,
 <code>false</code>.

### IsNegativeInfinity
    @Deprecated public boolean IsNegativeInfinity()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is negative infinity; otherwise, <code>false</code>.

### IsPositiveInfinity
    @Deprecated public boolean IsPositiveInfinity()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is positive infinity; otherwise, <code>false</code>.

### IsQuietNaN
    @Deprecated public boolean IsQuietNaN()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is a quiet not-a-number value;
 otherwise, <code>false</code>.

### IsSignalingNaN
    @Deprecated public boolean IsSignalingNaN()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is a signaling not-a-number value (which
 causes an error if the value is passed to any arithmetic operation in
 this class); otherwise, <code>false</code>.

### Multiply
    @Deprecated public ExtendedRational Multiply(ExtendedRational otherValue)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision rational number.

**Returns:**

* The product of the two numbers.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Negate
    @Deprecated public ExtendedRational Negate()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The negated form of this rational number.

### Remainder
    @Deprecated public ExtendedRational Remainder(ExtendedRational otherValue)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision rational number.

**Returns:**

* The remainder of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Subtract
    @Deprecated public ExtendedRational Subtract(ExtendedRational otherValue)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision rational number.

**Returns:**

* The difference of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### ToBigInteger
    @Deprecated public BigInteger ToBigInteger()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is infinity or NaN.

### ToBigIntegerExact
    @Deprecated public BigInteger ToBigIntegerExact()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is infinity or NaN.

* <code>ArithmeticException</code> - This object's value is not an exact integer.

### ToDouble
    @Deprecated public double ToDouble()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The closest 64-bit floating-point number to this value. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 64-bit floating point number.

### ToExtendedDecimal
    @Deprecated public ExtendedDecimal ToExtendedDecimal()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The exact value of the rational number, or not-a-number (NaN) if the
 result can't be exact because it has a nonterminating decimal
 expansion.

### ToExtendedDecimal
    @Deprecated public ExtendedDecimal ToExtendedDecimal(PrecisionContext ctx)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* An arbitrary-precision decimal.

### ToExtendedDecimalExactIfPossible
    @Deprecated public ExtendedDecimal ToExtendedDecimalExactIfPossible(PrecisionContext ctx)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context object to control the precision. The rounding
 and exponent range settings of this context are ignored. This context
 will be used only if the exact result would have a nonterminating
 decimal expansion. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null, in which case this
 method is the same as ToExtendedDecimal.

**Returns:**

* An arbitrary-precision decimal.

### ToExtendedFloat
    @Deprecated public ExtendedFloat ToExtendedFloat()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The exact value of the rational number, or not-a-number (NaN) if the
 result can't be exact because it has a nonterminating binary
 expansion.

### ToExtendedFloat
    @Deprecated public ExtendedFloat ToExtendedFloat(PrecisionContext ctx)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* An arbitrary-precision binary float.

### ToExtendedFloatExactIfPossible
    @Deprecated public ExtendedFloat ToExtendedFloatExactIfPossible(PrecisionContext ctx)
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context object to control the precision. The rounding
 and exponent range settings of this context are ignored. This context
 will be used only if the exact result would have a nonterminating
 binary expansion. If HasFlags of the context is true, will also store
 the flags resulting from the operation (the flags are in addition to
 the pre-existing flags). Can be null, in which case this method is
 the same as ToExtendedFloat.

**Returns:**

* An arbitrary-precision binary float.

### ToSingle
    @Deprecated public float ToSingle()
Deprecated.&nbsp;Use ERational from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The closest 32-bit floating-point number to this value. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 32-bit floating point number.

### toString
    public String toString()
Converts this object to a text string.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object. The result can be Infinity,
 NaN, or sNaN (with a minus sign before it for negative values), or a
 number of the following form: [-]numerator/denominator.
