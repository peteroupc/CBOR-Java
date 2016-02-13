# com.upokecenter.util.ExtendedDecimal

    public final class ExtendedDecimal extends Object implements Comparable<ExtendedDecimal>

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.EDecimal</code> in the <code>PeterO.Numbers</code>
 library (in .NET), or <code>com.upokecenter.numbers.EDecimal</code> in the
 <code>com.github.peteroupc/numbers</code>
 artifact (in Java). This new class can be used in the
 <code>CBORObject.FromObject(object)</code> method (by including the new
 library in your code, among other things), but this version of the
 CBOR library doesn't include any methods that explicitly take an
 <code>EDecimal</code> as a parameter or return value.</b></p> Represents an
 arbitrary-precision decimal floating-point number. <p><b>About
 decimal arithmetic</b></p> <p>Decimal (base-10) arithmetic, such as
 that provided by this class, is appropriate for calculations
 involving such real-world data as prices and other sums of money, tax
 rates, and measurements. These calculations often involve multiplying
 or dividing one decimal with another decimal, or performing other
 operations on decimal numbers. Many of these calculations also rely
 on rounding behavior in which the result after rounding is a decimal
 number (for example, multiplying a price by a premium rate, then
 rounding, should result in a decimal amount of money).</p> <p>On the
 other hand, most implementations of <code>float</code> and <code>double</code>,
 including in C# and Java, store numbers in a binary (base-2)
 floating-point format and use binary floating-point arithmetic. Many
 decimal numbers can't be represented exactly in binary floating-point
 format (regardless of its length). Applying binary arithmetic to
 numbers intended to be decimals can sometimes lead to unintuitive
 results, as is shown in the description for the FromDouble() method
 of this class.</p> <p><b>About ExtendedDecimal instances</b></p>
 <p>Each instance of this class consists of an integer mantissa and an
 integer exponent, both arbitrary-precision. The value of the number
 equals mantissa * 10^exponent.</p> <p>The mantissa is the value of
 the digits that make up a number, ignoring the decimal point and
 exponent. For example, in the number 2356.78, the mantissa is 235678.
 The exponent is where the "floating" decimal point of the number is
 located. A positive exponent means "move it to the right", and a
 negative exponent means "move it to the left." In the example 2,
 356.78, the exponent is -2, since it has 2 decimal places and the
 decimal point is "moved to the left by 2." Therefore, in the
 arbitrary-precision decimal representation, this number would be
 stored as 235678 * 10^-2.</p> <p>The mantissa and exponent format
 preserves trailing zeros in the number's value. This may give rise to
 multiple ways to store the same value. For example, 1.00 and 1 would
 be stored differently, even though they have the same value. In the
 first case, 100 * 10^-2 (100 with decimal point moved left by 2), and
 in the second case, 1 * 10^0 (1 with decimal point moved 0).</p>
 <p>This class also supports values for negative zero, not-a-number
 (NaN) values, and infinity. <b>Negative zero</b> is generally used
 when a negative number is rounded to 0; it has the same mathematical
 value as positive zero. <b>Infinity</b> is generally used when a
 non-zero number is divided by zero, or when a very high number can't
 be represented in a given exponent range. <b>Not-a-number</b> is
 generally used to signal errors.</p> <p>This class implements the
 General Decimal Arithmetic Specification version 1.70 (except part of
 chapter 6): <code>http://speleotrove.com/decimal/decarith.html</code></p>
 <p><b>Errors and Exceptions</b></p> <p>Passing a signaling NaN to any
 arithmetic operation shown here will signal the flag FlagInvalid and
 return a quiet NaN, even if another operand to that operation is a
 quiet NaN, unless noted otherwise.</p> <p>Passing a quiet NaN to any
 arithmetic operation shown here will return a quiet NaN, unless noted
 otherwise. Invalid operations will also return a quiet NaN, as stated
 in the individual methods.</p> <p>Unless noted otherwise, passing a
 null arbitrary-precision decimal argument to any method here will
 throw an exception.</p> <p>When an arithmetic operation signals the
 flag FlagInvalid, FlagOverflow, or FlagDivideByZero, it will not
 throw an exception too, unless the flag's trap is enabled in the
 precision context (see EContext's Traps property).</p> <p>If an
 operation requires creating an intermediate value that might be too
 big to fit in memory (or might require more than 2 gigabytes of
 memory to store -- due to the current use of a 32-bit integer
 internally as a length), the operation may signal an
 invalid-operation flag and return not-a-number (NaN). In certain rare
 cases, the compareTo method may throw OutOfMemoryError (called
 OutOfMemoryError in Java) in the same circumstances.</p>
 <p><b>Serialization</b></p> <p>An arbitrary-precision decimal value
 can be serialized (converted to a stable format) in one of the
 following ways:</p> <ul> <li>By calling the toString() method, which
 will always return distinct strings for distinct arbitrary-precision
 decimal values.</li> <li>By calling the UnsignedMantissa, Exponent,
 and IsNegative properties, and calling the IsInfinity, IsQuietNaN,
 and IsSignalingNaN methods. The return values combined will uniquely
 identify a particular arbitrary-precision decimal value.</li></ul>
 <p><b>Thread safety</b></p> <p>Instances of this class are immutable,
 so they are inherently safe for use by multiple threads. Multiple
 instances of this object with the same properties are
 interchangeable, so they should not be compared using the "=="
 operator (which only checks if each side of the operator is the same
 instance).</p> <p><b>Comparison considerations</b></p> <p>This
 class's natural ordering (under the compareTo method) is not
 consistent with the Equals method. This means that two values that
 compare as equal under the compareTo method might not be equal under
 the Equals method. The compareTo method compares the mathematical
 values of the two instances passed to it (and considers two different
 NaN values as equal), while two instances with the same mathematical
 value, but different exponents, will be considered unequal under the
 Equals method.</p>

## Fields

* `static ExtendedDecimal NaN`<br>
 A not-a-number value.
* `static ExtendedDecimal NegativeInfinity`<br>
 Negative infinity, less than any other number.
* `static ExtendedDecimal NegativeZero`<br>
 Represents the number negative zero.
* `static ExtendedDecimal One`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal PositiveInfinity`<br>
 Positive infinity, greater than any other number.
* `static ExtendedDecimal SignalingNaN`<br>
 A not-a-number value that signals an invalid operation flag when it's
 passed as an argument to any arithmetic operation in
 arbitrary-precision decimal.
* `static ExtendedDecimal Ten`<br>
 Represents the number 10.
* `static ExtendedDecimal Zero`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

## Methods

* `int compareTo(ExtendedDecimal other)`<br>
* `static ExtendedDecimal Create(BigInteger mantissa,
      BigInteger exponent)`<br>
 Creates a number with the value exponent*10^mantissa.
* `boolean equals(ExtendedDecimal other)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean equals(Object obj)`<br>
 Determines whether this object's mantissa and exponent are equal to
 those of another object and that other object is an
 arbitrary-precision decimal number.
* `static ExtendedDecimal FromString(String str)`<br>
 Creates a decimal number from a text string that represents a number.
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
 Gets a value indicating whether this object is not a number (NaN).
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
 Converts this value to a 64-bit floating-point number.
* `float ToSingle()`<br>
 Converts this value to a 32-bit floating-point number.
* `String toString()`<br>
 Converts this value to a string.

## Field Details

### One
    @Deprecated public static final ExtendedDecimal One
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.</span>
### Zero
    @Deprecated public static final ExtendedDecimal Zero
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.</span>
### NegativeZero
    public static final ExtendedDecimal NegativeZero
Represents the number negative zero.
### Ten
    public static final ExtendedDecimal Ten
Represents the number 10.
### NaN
    public static final ExtendedDecimal NaN
A not-a-number value.
### SignalingNaN
    public static final ExtendedDecimal SignalingNaN
A not-a-number value that signals an invalid operation flag when it&#x27;s
 passed as an argument to any arithmetic operation in
 arbitrary-precision decimal.
### PositiveInfinity
    public static final ExtendedDecimal PositiveInfinity
Positive infinity, greater than any other number.
### NegativeInfinity
    public static final ExtendedDecimal NegativeInfinity
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

### equals
    @Deprecated public boolean equals(ExtendedDecimal other)
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.</span>

**Parameters:**

* <code>other</code> - An arbitrary-precision decimal number.

**Returns:**

* true if this object's mantissa and exponent are equal to those of
 another object; otherwise, false.

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
    public static ExtendedDecimal Create(BigInteger mantissa, BigInteger exponent)
Creates a number with the value exponent*10^mantissa.

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
Creates a decimal number from a text string that represents a number. See
 <code>FromString(String, int, int, EContext)</code> for more information.

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
Converts this value to a 32-bit floating-point number. The half-even
 rounding mode is used. <p>If this value is a NaN, sets the high bit
 of the 32-bit floating point number's mantissa for a quiet NaN, and
 clears it for a signaling NaN. Then the next highest bit of the
 mantissa is cleared for a quiet NaN, and set for a signaling NaN.
 Then the other bits of the mantissa are set to the lowest bits of
 this object's unsigned mantissa.</p>

**Returns:**

* The closest 32-bit floating-point number to this value. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 32-bit floating point number.

### ToDouble
    public double ToDouble()
Converts this value to a 64-bit floating-point number. The half-even
 rounding mode is used. <p>If this value is a NaN, sets the high bit
 of the 64-bit floating point number's mantissa for a quiet NaN, and
 clears it for a signaling NaN. Then the next highest bit of the
 mantissa is cleared for a quiet NaN, and set for a signaling NaN.
 Then the other bits of the mantissa are set to the lowest bits of
 this object's unsigned mantissa.</p>

**Returns:**

* The closest 64-bit floating-point number to this value. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 64-bit floating point number.

### toString
    public String toString()
Converts this value to a string. Returns a value compatible with this
 class's FromString method.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object.

### IsNaN
    public boolean IsNaN()
Gets a value indicating whether this object is not a number (NaN).

**Returns:**

* true if this object is not a number (NaN); otherwise, false .

### IsInfinity
    public boolean IsInfinity()
Gets a value indicating whether this object is positive or negative
 infinity.

**Returns:**

* true if this object is positive or negative infinity; otherwise,
 false.

### isNegative
    @Deprecated public final boolean isNegative()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object is negative, including negative zero; otherwise,
 false.

### IsQuietNaN
    @Deprecated public boolean IsQuietNaN()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* true if this object is a quiet not-a-number value; otherwise, false.

### compareTo
    public int compareTo(ExtendedDecimal other)

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;ExtendedDecimal&gt;</code>

**Parameters:**

* <code>other</code> -

### signum
    @Deprecated public final int signum()
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.</span>

**Returns:**

* This value's sign: -1 if negative; 1 if positive; 0 if zero.
