# com.upokecenter.util.ExtendedDecimal

    public final class ExtendedDecimal extends Object implements Comparable<ExtendedDecimal>

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.EDecimal</code> in the <code>PeterO.Numbers</code>
 library (in .NET), or <code>com.upokecenter.numbers.EDecimal</code> in the
 <code>com.github.peteroupc/numbers</code>
 artifact (in Java). This new class can be used in the
 <code>CBORObject.FromObject(Object)</code> method (by including the new
 library in your code, among other things), but this version of the
 CBOR library doesn't include any methods that explicitly take an
 <code>EDecimal</code> as a parameter or return value.</b></p> Represents an
 arbitrary-precision decimal floating-point number. <p><b>About
 decimal arithmetic</b></p> <p> Decimal (base-10) arithmetic, such as
 that provided by this class, is appropriate for calculations
 involving such real-world data as prices and other sums of money, tax
 rates, and measurements. These calculations often involve multiplying
 or dividing one decimal with another decimal, or performing other
 operations on decimal numbers. Many of these calculations also rely
 on rounding behavior in which the result after rounding is a decimal
 number (for example, multiplying a price by a premium rate, then
 rounding, should result in a decimal amount of money). </p> <p>On the
 other hand, most implementations of <code>float</code> and <code>double</code>,
 including in C# and Java, store numbers in a binary (base-2)
 floating-point format and use binary floating-point arithmetic. Many
 decimal numbers can't be represented exactly in binary floating-point
 format (regardless of its length). Applying binary arithmetic to
 numbers intended to be decimals can sometimes lead to unintuitive
 results, as is shown in the description for the FromDouble() method
 of this class.</p> <p><b>About ExtendedDecimal instances</b></p> <p>
 Each instance of this class consists of an integer mantissa and an
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

* `ExtendedDecimal Abs()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Abs(PrecisionContext context)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Add(ExtendedDecimal otherValue)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Add(ExtendedDecimal otherValue,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `int compareTo(ExtendedDecimal other)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `int CompareToBinary(ExtendedFloat other)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal CompareToSignal(ExtendedDecimal other,
               PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal CompareToWithContext(ExtendedDecimal other,
                    PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal Create(BigInteger mantissa,
      BigInteger exponent)`<br>
 Creates a number with the value exponent*10^mantissa.
* `static ExtendedDecimal Create(int mantissaSmall,
      int exponentSmall)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal CreateNaN(BigInteger diag)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal CreateNaN(BigInteger diag,
         boolean signaling,
         boolean negative,
         PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Divide(ExtendedDecimal divisor)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Divide(ExtendedDecimal divisor,
      PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal[] DivideAndRemainderNaturalScale(ExtendedDecimal divisor)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal[] DivideAndRemainderNaturalScale(ExtendedDecimal divisor,
                              PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal DivideToExponent(ExtendedDecimal divisor,
                BigInteger exponent,
                PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal DivideToExponent(ExtendedDecimal divisor,
                BigInteger desiredExponent,
                Rounding rounding)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal DivideToExponent(ExtendedDecimal divisor,
                long desiredExponentSmall,
                PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal DivideToExponent(ExtendedDecimal divisor,
                long desiredExponentSmall,
                Rounding rounding)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal DivideToIntegerNaturalScale(ExtendedDecimal divisor)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal DivideToIntegerNaturalScale(ExtendedDecimal divisor,
                           PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal DivideToIntegerZeroScale(ExtendedDecimal divisor,
                        PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal DivideToSameExponent(ExtendedDecimal divisor,
                    Rounding rounding)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean equals(ExtendedDecimal other)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean equals(Object obj)`<br>
 Determines whether this object's mantissa and exponent are equal to
 those of another object and that other object is an
 arbitrary-precision decimal number.
* `ExtendedDecimal Exp(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromBigInteger(BigInteger bigint)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromDouble(double dbl)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromExtendedFloat(ExtendedFloat bigfloat)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromInt32(int valueSmaller)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromInt64(long valueSmall)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromSingle(float flt)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromString(String str)`<br>
 Creates a decimal number from a text string that represents a number.
* `static ExtendedDecimal FromString(String str,
          int offset,
          int length)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromString(String str,
          int offset,
          int length,
          PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal FromString(String str,
          PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger getExponent()`<br>
 Gets this object's exponent.
* `BigInteger getMantissa()`<br>
 Gets this object's un-scaled value.
* `BigInteger getUnsignedMantissa()`<br>
 Gets the absolute value of this object's un-scaled value.
* `int hashCode()`<br>
 Calculates this object's hash code.
* `boolean isFinite()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsInfinity()`<br>
 Gets a value indicating whether this object is positive or negative
 infinity.
* `boolean IsNaN()`<br>
 Gets a value indicating whether this object is not a number (NaN).
* `boolean isNegative()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsNegativeInfinity()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsPositiveInfinity()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsQuietNaN()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean IsSignalingNaN()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isZero()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Log(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Log10(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal Max(ExtendedDecimal first,
   ExtendedDecimal second)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal Max(ExtendedDecimal first,
   ExtendedDecimal second,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal MaxMagnitude(ExtendedDecimal first,
            ExtendedDecimal second)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal MaxMagnitude(ExtendedDecimal first,
            ExtendedDecimal second,
            PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal Min(ExtendedDecimal first,
   ExtendedDecimal second)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal Min(ExtendedDecimal first,
   ExtendedDecimal second,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal MinMagnitude(ExtendedDecimal first,
            ExtendedDecimal second)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal MinMagnitude(ExtendedDecimal first,
            ExtendedDecimal second,
            PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MovePointLeft(BigInteger bigPlaces)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MovePointLeft(BigInteger bigPlaces,
             PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MovePointLeft(int places)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MovePointLeft(int places,
             PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MovePointRight(BigInteger bigPlaces)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MovePointRight(BigInteger bigPlaces,
              PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MovePointRight(int places)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MovePointRight(int places,
              PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Multiply(ExtendedDecimal otherValue)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Multiply(ExtendedDecimal op,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MultiplyAndAdd(ExtendedDecimal multiplicand,
              ExtendedDecimal augend)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MultiplyAndAdd(ExtendedDecimal op,
              ExtendedDecimal augend,
              PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal MultiplyAndSubtract(ExtendedDecimal op,
                   ExtendedDecimal subtrahend,
                   PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Negate()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Negate(PrecisionContext context)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal NextMinus(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal NextPlus(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal NextToward(ExtendedDecimal otherValue,
          PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedDecimal PI(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Plus(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Pow(ExtendedDecimal exponent,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Pow(int exponentSmall)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Pow(int exponentSmall,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger Precision()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Quantize(BigInteger desiredExponent,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Quantize(ExtendedDecimal otherValue,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Quantize(int desiredExponentSmall,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Quantize(int desiredExponentSmall,
        Rounding rounding)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Reduce(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Remainder(ExtendedDecimal divisor,
         PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RemainderNaturalScale(ExtendedDecimal divisor)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RemainderNaturalScale(ExtendedDecimal divisor,
                     PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RemainderNear(ExtendedDecimal divisor,
             PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RoundToBinaryPrecision(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RoundToExponent(BigInteger exponent,
               PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RoundToExponent(int exponentSmall,
               PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RoundToExponentExact(BigInteger exponent,
                    PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RoundToExponentExact(int exponentSmall,
                    PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RoundToIntegralExact(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RoundToIntegralNoRoundedFlag(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal RoundToPrecision(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal ScaleByPowerOfTen(BigInteger bigPlaces)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal ScaleByPowerOfTen(BigInteger bigPlaces,
                 PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal ScaleByPowerOfTen(int places)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal ScaleByPowerOfTen(int places,
                 PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `int signum()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal SquareRoot(PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Subtract(ExtendedDecimal otherValue)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal Subtract(ExtendedDecimal otherValue,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger ToBigInteger()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger ToBigIntegerExact()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `double ToDouble()`<br>
 Converts this value to a 64-bit floating-point number.
* `String ToEngineeringString()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat ToExtendedFloat()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `String ToPlainString()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
* `float ToSingle()`<br>
 Converts this value to a 32-bit floating-point number.
* `String toString()`<br>
 Converts this value to a string.
* `ExtendedDecimal Ulp()`<br>
 Deprecated.
Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
 Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

## Field Details

### One
    @Deprecated public static final ExtendedDecimal One
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
### Zero
    @Deprecated public static final ExtendedDecimal Zero
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.
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
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>other</code> - An arbitrary-precision decimal number.

**Returns:**

* <code>true</code> if this object's mantissa and exponent are equal to
 those of another object; otherwise, <code>false</code>.

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

* <code>true</code> if the objects are equal; otherwise, <code>false</code>.

### hashCode
    public int hashCode()
Calculates this object&#x27;s hash code.

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* This object's hash code.

### Create
    @Deprecated public static ExtendedDecimal Create(int mantissaSmall, int exponentSmall)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>mantissaSmall</code> - The un-scaled value.

* <code>exponentSmall</code> - The decimal exponent.

**Returns:**

* An arbitrary-precision decimal number.

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

### CreateNaN
    @Deprecated public static ExtendedDecimal CreateNaN(BigInteger diag)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>diag</code> - A number to use as diagnostic information associated with this
 object. If none is needed, should be zero.

**Returns:**

* A quiet not-a-number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>diag</code> is null or
 is less than 0.

### CreateNaN
    @Deprecated public static ExtendedDecimal CreateNaN(BigInteger diag, boolean signaling, boolean negative, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>diag</code> - A number to use as diagnostic information associated with this
 object. If none is needed, should be zero.

* <code>signaling</code> - Whether the return value will be signaling (true) or quiet
 (false).

* <code>negative</code> - Whether the return value is negative.

* <code>ctx</code> - A context object for arbitrary-precision arithmetic settings.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>diag</code> is null or
 is less than 0.

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

### FromString
    @Deprecated public static ExtendedDecimal FromString(String str, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>str</code> - A string that represents a number.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number with the same value as the
 given string.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>NumberFormatException</code> - The parameter <code>str</code> is not a correctly
 formatted number string.

### FromString
    @Deprecated public static ExtendedDecimal FromString(String str, int offset, int length)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>str</code> - A string that represents a number.

* <code>offset</code> - A zero-based index showing where the desired portion of <code>str</code> begins.

* <code>length</code> - The length, in code units, of the desired portion of <code>str</code> (but not more than <code>str</code> 's length).

**Returns:**

* An arbitrary-precision decimal number with the same value as the
 given string.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>NumberFormatException</code> - The parameter <code>str</code> is not a correctly
 formatted number string.

### FromString
    @Deprecated public static ExtendedDecimal FromString(String str, int offset, int length, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>str</code> - A text string, a portion of which represents a number.

* <code>offset</code> - A zero-based index that identifies the start of the number.

* <code>length</code> - The length of the number within the string.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number with the same value as the
 given string.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>NumberFormatException</code> - The parameter <code>str</code> is not a correctly
 formatted number string.

### CompareToBinary
    @Deprecated public int CompareToBinary(ExtendedFloat other)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>other</code> - The other object to compare. Can be null.

**Returns:**

* Zero if the values are equal; a negative number if this instance is
 less, or a positive number if this instance is greater. Returns 0 if
 both values are NaN (even signaling NaN) and 1 if this value is NaN
 (even signaling NaN) and the other isn't, or if the other value is
 null.

### ToBigInteger
    @Deprecated public BigInteger ToBigInteger()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is infinity or NaN.

### ToBigIntegerExact
    @Deprecated public BigInteger ToBigIntegerExact()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is infinity or NaN.

* <code>ArithmeticException</code> - This object's value is not an exact integer.

### ToExtendedFloat
    @Deprecated public ExtendedFloat ToExtendedFloat()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision binary float.

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

### FromSingle
    @Deprecated public static ExtendedDecimal FromSingle(float flt)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>flt</code> - A 32-bit floating-point number.

**Returns:**

* A decimal number with the same value as <code>flt</code>.

### FromBigInteger
    @Deprecated public static ExtendedDecimal FromBigInteger(BigInteger bigint)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigint</code> - An arbitrary-precision integer.

**Returns:**

* An arbitrary-precision decimal number with the exponent set to 0.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigint</code> is null.

### FromInt64
    @Deprecated public static ExtendedDecimal FromInt64(long valueSmall)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>valueSmall</code> - A 64-bit signed integer.

**Returns:**

* An arbitrary-precision decimal number with the exponent set to 0.

### FromInt32
    @Deprecated public static ExtendedDecimal FromInt32(int valueSmaller)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>valueSmaller</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision decimal number with the exponent set to 0.

### FromDouble
    @Deprecated public static ExtendedDecimal FromDouble(double dbl)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>dbl</code> - A 64-bit floating-point number.

**Returns:**

* A decimal number with the same value as <code>dbl</code>.

### FromExtendedFloat
    @Deprecated public static ExtendedDecimal FromExtendedFloat(ExtendedFloat bigfloat)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigfloat</code> - An arbitrary-precision binary floating-point number.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigfloat</code> is null.

### toString
    public String toString()
Converts this value to a string. Returns a value compatible with this
 class's FromString method.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object.

### ToEngineeringString
    @Deprecated public String ToEngineeringString()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* A text string.

### ToPlainString
    @Deprecated public String ToPlainString()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* A text string.

### IsNegativeInfinity
    @Deprecated public boolean IsNegativeInfinity()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is negative infinity; otherwise, <code>false</code>.

### IsPositiveInfinity
    @Deprecated public boolean IsPositiveInfinity()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is positive infinity; otherwise, <code>false</code>.

### IsNaN
    public boolean IsNaN()
Gets a value indicating whether this object is not a number (NaN).

**Returns:**

* <code>true</code> if this object is not a number (NaN); otherwise, <code>false</code>.

### IsInfinity
    public boolean IsInfinity()
Gets a value indicating whether this object is positive or negative
 infinity.

**Returns:**

* <code>true</code> if this object is positive or negative infinity;
 otherwise, <code>false</code>.

### isFinite
    @Deprecated public final boolean isFinite()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is finite (not infinity or NaN);
 otherwise, <code>false</code>.

### isNegative
    @Deprecated public final boolean isNegative()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is negative, including negative zero;
 otherwise, <code>false</code>.

### IsQuietNaN
    @Deprecated public boolean IsQuietNaN()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is a quiet not-a-number value;
 otherwise, <code>false</code>.

### IsSignalingNaN
    @Deprecated public boolean IsSignalingNaN()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object is a signaling not-a-number value;
 otherwise, <code>false</code>.

### signum
    @Deprecated public final int signum()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* This value's sign: -1 if negative; 1 if positive; 0 if zero.

### isZero
    @Deprecated public final boolean isZero()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this object's value equals 0; otherwise, <code>false</code>.

### Abs
    @Deprecated public ExtendedDecimal Abs()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision decimal number.

### Negate
    @Deprecated public ExtendedDecimal Negate()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision decimal number. If this value is positive
 zero, returns positive zero.

### Divide
    @Deprecated public ExtendedDecimal Divide(ExtendedDecimal divisor)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

**Returns:**

* The quotient of the two numbers. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Returns not-a-number (NaN) if the divisor and the dividend are 0.
 Returns NaN if the result can't be exact because it would have a
 nonterminating decimal expansion.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToSameExponent
    @Deprecated public ExtendedDecimal DivideToSameExponent(ExtendedDecimal divisor, Rounding rounding)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>rounding</code> - The rounding mode to use if the result must be scaled down
 to have the same exponent as this value.

**Returns:**

* The quotient of the two numbers. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
 the dividend are 0. Signals FlagInvalid and returns not-a-number
 (NaN) if the rounding mode is Rounding.Unnecessary and the result is
 not exact.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToIntegerNaturalScale
    @Deprecated public ExtendedDecimal DivideToIntegerNaturalScale(ExtendedDecimal divisor)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

**Returns:**

* The integer part of the quotient of the two objects. Signals
 FlagDivideByZero and returns infinity if the divisor is 0 and the
 dividend is nonzero. Signals FlagInvalid and returns not-a-number
 (NaN) if the divisor and the dividend are 0.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### Reduce
    @Deprecated public ExtendedDecimal Reduce(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* This value with trailing zeros removed. Note that if the result has
 a very high exponent and the context says to clamp high exponents,
 there may still be some trailing zeros in the mantissa.

### RemainderNaturalScale
    @Deprecated public ExtendedDecimal RemainderNaturalScale(ExtendedDecimal divisor)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### RemainderNaturalScale
    @Deprecated public ExtendedDecimal RemainderNaturalScale(ExtendedDecimal divisor, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>ctx</code> - A precision context object to control the precision, rounding,
 and exponent range of the result. This context will be used only in
 the division portion of the remainder calculation; as a result, it's
 possible for the return value to have a higher precision than given
 in this context. Flags will be set on the given context only if the
 context's HasFlags is true and the integer part of the division
 result doesn't fit the precision and exponent range without rounding.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToExponent
    @Deprecated public ExtendedDecimal DivideToExponent(ExtendedDecimal divisor, long desiredExponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - An arbitrary-precision decimal number.

* <code>desiredExponentSmall</code> - The desired exponent. A negative number places
 the cutoff point to the right of the usual decimal point. A positive
 number places the cutoff point to the left of the usual decimal
 point.

* <code>ctx</code> - A precision context object to control the rounding mode to use if
 the result must be scaled down to have the same exponent as this
 value. If the precision given in the context is other than 0, calls
 the Quantize method with both arguments equal to the result of the
 operation (and can signal FlagInvalid and return NaN if the result
 doesn't fit the given precision). If HasFlags of the context is true,
 will also store the flags resulting from the operation (the flags are
 in addition to the pre-existing flags). Can be null, in which case
 the default rounding mode is HalfEven.

**Returns:**

* The quotient of the two objects. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
 the dividend are 0. Signals FlagInvalid and returns not-a-number
 (NaN) if the context defines an exponent range and the desired
 exponent is outside that range. Signals FlagInvalid and returns
 not-a-number (NaN) if the rounding mode is Rounding.Unnecessary and
 the result is not exact.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### Divide
    @Deprecated public ExtendedDecimal Divide(ExtendedDecimal divisor, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The quotient of the two objects. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
 the dividend are 0; or, either <code>ctx</code> is null or <code>ctx</code> 's
 precision is 0, and the result would have a nonterminating decimal
 expansion; or, the rounding mode is Rounding.Unnecessary and the
 result is not exact.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToExponent
    @Deprecated public ExtendedDecimal DivideToExponent(ExtendedDecimal divisor, long desiredExponentSmall, Rounding rounding)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - An arbitrary-precision decimal number.

* <code>desiredExponentSmall</code> - The desired exponent. A negative number places
 the cutoff point to the right of the usual decimal point. A positive
 number places the cutoff point to the left of the usual decimal
 point.

* <code>rounding</code> - The rounding mode to use if the result must be scaled down
 to have the same exponent as this value.

**Returns:**

* The quotient of the two objects. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
 the dividend are 0. Signals FlagInvalid and returns not-a-number
 (NaN) if the rounding mode is Rounding.Unnecessary and the result is
 not exact.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToExponent
    @Deprecated public ExtendedDecimal DivideToExponent(ExtendedDecimal divisor, BigInteger exponent, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - An arbitrary-precision decimal number.

* <code>exponent</code> - The desired exponent. A negative number places the cutoff
 point to the right of the usual decimal point. A positive number
 places the cutoff point to the left of the usual decimal point.

* <code>ctx</code> - A precision context object to control the rounding mode to use if
 the result must be scaled down to have the same exponent as this
 value. If the precision given in the context is other than 0, calls
 the Quantize method with both arguments equal to the result of the
 operation (and can signal FlagInvalid and return NaN if the result
 doesn't fit the given precision). If HasFlags of the context is true,
 will also store the flags resulting from the operation (the flags are
 in addition to the pre-existing flags). Can be null, in which case
 the default rounding mode is HalfEven.

**Returns:**

* The quotient of the two objects. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
 the dividend are 0. Signals FlagInvalid and returns not-a-number
 (NaN) if the context defines an exponent range and the desired
 exponent is outside that range. Signals FlagInvalid and returns
 not-a-number (NaN) if the rounding mode is Rounding.Unnecessary and
 the result is not exact.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> or <code>exponent</code> is null.

### DivideToExponent
    @Deprecated public ExtendedDecimal DivideToExponent(ExtendedDecimal divisor, BigInteger desiredExponent, Rounding rounding)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - An arbitrary-precision decimal number.

* <code>desiredExponent</code> - The desired exponent. A negative number places the
 cutoff point to the right of the usual decimal point. A positive
 number places the cutoff point to the left of the usual decimal
 point.

* <code>rounding</code> - The rounding mode to use if the result must be scaled down
 to have the same exponent as this value.

**Returns:**

* The quotient of the two objects. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Returns not-a-number (NaN) if the divisor and the dividend are 0.
 Returns NaN if the rounding mode is Rounding.Unnecessary and the
 result is not exact.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> or <code>desiredExponent</code> is null.

### Abs
    @Deprecated public ExtendedDecimal Abs(PrecisionContext context)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>context</code> - A precision context to control precision, rounding, and
 exponent range of the result. If HasFlags of the context is true,
 will also store the flags resulting from the operation (the flags are
 in addition to the pre-existing flags). Can be null.

**Returns:**

* The absolute value of this object.

### Negate
    @Deprecated public ExtendedDecimal Negate(PrecisionContext context)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>context</code> - A precision context to control precision, rounding, and
 exponent range of the result. If HasFlags of the context is true,
 will also store the flags resulting from the operation (the flags are
 in addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number. If this value is positive
 zero, returns positive zero.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>context</code> is null.

### Add
    @Deprecated public ExtendedDecimal Add(ExtendedDecimal otherValue)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision decimal number.

**Returns:**

* The sum of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Subtract
    @Deprecated public ExtendedDecimal Subtract(ExtendedDecimal otherValue)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision decimal number.

**Returns:**

* The difference of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Subtract
    @Deprecated public ExtendedDecimal Subtract(ExtendedDecimal otherValue, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision decimal number.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The difference of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Multiply
    @Deprecated public ExtendedDecimal Multiply(ExtendedDecimal otherValue)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - Another decimal number.

**Returns:**

* The product of the two decimal numbers.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### MultiplyAndAdd
    @Deprecated public ExtendedDecimal MultiplyAndAdd(ExtendedDecimal multiplicand, ExtendedDecimal augend)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>multiplicand</code> - The value to multiply.

* <code>augend</code> - The value to add.

**Returns:**

* The result this * <code>multiplicand</code> + <code>augend</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>multiplicand</code> or
 <code>augend</code> is null.

### DivideToIntegerNaturalScale
    @Deprecated public ExtendedDecimal DivideToIntegerNaturalScale(ExtendedDecimal divisor, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>ctx</code> - A precision context object to control the precision, rounding,
 and exponent range of the integer part of the result. Flags will be
 set on the given context only if the context's HasFlags is true and
 the integer part of the result doesn't fit the precision and exponent
 range without rounding.

**Returns:**

* The integer part of the quotient of the two objects. Signals
 FlagInvalid and returns not-a-number (NaN) if the return value would
 overflow the exponent range. Signals FlagDivideByZero and returns
 infinity if the divisor is 0 and the dividend is nonzero. Signals
 FlagInvalid and returns not-a-number (NaN) if the divisor and the
 dividend are 0. Signals FlagInvalid and returns not-a-number (NaN) if
 the rounding mode is Rounding.Unnecessary and the result is not
 exact.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToIntegerZeroScale
    @Deprecated public ExtendedDecimal DivideToIntegerZeroScale(ExtendedDecimal divisor, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>ctx</code> - A precision context object to control the precision. The rounding
 and exponent range settings of this context are ignored. If HasFlags
 of the context is true, will also store the flags resulting from the
 operation (the flags are in addition to the pre-existing flags). Can
 be null.

**Returns:**

* The integer part of the quotient of the two objects. The exponent
 will be set to 0. Signals FlagDivideByZero and returns infinity if
 the divisor is 0 and the dividend is nonzero. Signals FlagInvalid and
 returns not-a-number (NaN) if the divisor and the dividend are 0, or
 if the result doesn't fit the given precision.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### Remainder
    @Deprecated public ExtendedDecimal Remainder(ExtendedDecimal divisor, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - An arbitrary-precision decimal number.

* <code>ctx</code> - The parameter <code>ctx</code> is not documented yet.

**Returns:**

* The remainder of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### RemainderNear
    @Deprecated public ExtendedDecimal RemainderNear(ExtendedDecimal divisor, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>ctx</code> - A precision context object to control the precision. The rounding
 and exponent range settings of this context are ignored (the rounding
 mode is always treated as HalfEven). If HasFlags of the context is
 true, will also store the flags resulting from the operation (the
 flags are in addition to the pre-existing flags). Can be null.

**Returns:**

* The distance of the closest multiple. Signals FlagInvalid and
 returns not-a-number (NaN) if the divisor is 0, or either the result
 of integer division (the quotient) or the remainder wouldn't fit the
 given precision.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### NextMinus
    @Deprecated public ExtendedDecimal NextMinus(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context object to control the precision and exponent
 range of the result. The rounding mode from this context is ignored.
 If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags).

**Returns:**

* Returns the largest value that's less than the given value. Returns
 negative infinity if the result is negative infinity. Signals
 FlagInvalid and returns not-a-number (NaN) if the parameter <code>ctx</code> is null, the precision is 0, or <code>ctx</code> has an unlimited
 exponent range.

### NextPlus
    @Deprecated public ExtendedDecimal NextPlus(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context object to control the precision and exponent
 range of the result. The rounding mode from this context is ignored.
 If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags).

**Returns:**

* Returns the smallest value that's greater than the given
 value.Signals FlagInvalid and returns not-a-number (NaN) if the
 parameter <code>ctx</code> is null, the precision is 0, or <code>ctx</code> has
 an unlimited exponent range.

### NextToward
    @Deprecated public ExtendedDecimal NextToward(ExtendedDecimal otherValue, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision decimal number.

* <code>ctx</code> - A precision context object to control the precision and exponent
 range of the result. The rounding mode from this context is ignored.
 If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags).

**Returns:**

* Returns the next value that is closer to the other object' s value
 than this object's value. Signals FlagInvalid and returns NaN if the
 parameter <code>ctx</code> is null, the precision is 0, or <code>ctx</code> has
 an unlimited exponent range.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Max
    @Deprecated public static ExtendedDecimal Max(ExtendedDecimal first, ExtendedDecimal second, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>first</code> - The first value to compare.

* <code>second</code> - The second value to compare.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The larger value of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### Min
    @Deprecated public static ExtendedDecimal Min(ExtendedDecimal first, ExtendedDecimal second, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>first</code> - The first value to compare.

* <code>second</code> - The second value to compare.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The smaller value of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### MaxMagnitude
    @Deprecated public static ExtendedDecimal MaxMagnitude(ExtendedDecimal first, ExtendedDecimal second, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>first</code> - The first value to compare.

* <code>second</code> - The second value to compare.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### MinMagnitude
    @Deprecated public static ExtendedDecimal MinMagnitude(ExtendedDecimal first, ExtendedDecimal second, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>first</code> - The first value to compare.

* <code>second</code> - The second value to compare.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### Max
    @Deprecated public static ExtendedDecimal Max(ExtendedDecimal first, ExtendedDecimal second)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>first</code> - An arbitrary-precision decimal number.

* <code>second</code> - Another arbitrary-precision decimal number.

**Returns:**

* The larger value of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### Min
    @Deprecated public static ExtendedDecimal Min(ExtendedDecimal first, ExtendedDecimal second)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>first</code> - The first value to compare.

* <code>second</code> - The second value to compare.

**Returns:**

* The smaller value of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### MaxMagnitude
    @Deprecated public static ExtendedDecimal MaxMagnitude(ExtendedDecimal first, ExtendedDecimal second)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>first</code> - The first value to compare.

* <code>second</code> - The second value to compare.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### MinMagnitude
    @Deprecated public static ExtendedDecimal MinMagnitude(ExtendedDecimal first, ExtendedDecimal second)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>first</code> - The first value to compare.

* <code>second</code> - The second value to compare.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### compareTo
    @Deprecated public int compareTo(ExtendedDecimal other)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;ExtendedDecimal&gt;</code>

**Parameters:**

* <code>other</code> - An arbitrary-precision decimal number.

**Returns:**

* Less than 0 if this object's value is less than the other value, or
 greater than 0 if this object's value is greater than the other value
 or if <code>other</code> is null, or 0 if both values are equal.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### CompareToWithContext
    @Deprecated public ExtendedDecimal CompareToWithContext(ExtendedDecimal other, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>other</code> - An arbitrary-precision decimal number.

* <code>ctx</code> - A precision context. The precision, rounding, and exponent range
 are ignored. If HasFlags of the context is true, will store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags). Can be null.

**Returns:**

* Quiet NaN if this object or the other object is NaN, or 0 if both
 objects have the same value, or -1 if this object is less than the
 other value, or 1 if this object is greater.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### CompareToSignal
    @Deprecated public ExtendedDecimal CompareToSignal(ExtendedDecimal other, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>other</code> - An arbitrary-precision decimal number.

* <code>ctx</code> - A precision context. The precision, rounding, and exponent range
 are ignored. If HasFlags of the context is true, will store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags). Can be null.

**Returns:**

* Quiet NaN if this object or the other object is NaN, or 0 if both
 objects have the same value, or -1 if this object is less than the
 other value, or 1 if this object is greater.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### Add
    @Deprecated public ExtendedDecimal Add(ExtendedDecimal otherValue, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - The number to add to.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The sum of thisValue and the other object.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Quantize
    @Deprecated public ExtendedDecimal Quantize(BigInteger desiredExponent, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>desiredExponent</code> - An arbitrary-precision integer.

* <code>ctx</code> - A precision context to control precision and rounding of the
 result. If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags). Can be null, in which case the default rounding
 mode is HalfEven.

**Returns:**

* A decimal number with the same value as this object but with the
 exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
 if the rounded result can't fit the given precision, or if the
 context defines an exponent range and the given exponent is outside
 that range.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>desiredExponent</code>
 is null.

### Quantize
    @Deprecated public ExtendedDecimal Quantize(int desiredExponentSmall, Rounding rounding)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>desiredExponentSmall</code> - The desired exponent for the result. The
 exponent is the number of fractional digits in the result, expressed
 as a negative number. Can also be positive, which eliminates
 lower-order places from the number. For example, -3 means round to
 the thousandth (10^-3, 0.0001), and 3 means round to the thousand
 (10^3, 1000). A value of 0 rounds the number to an integer.

* <code>rounding</code> - The mode to use when the result needs to be rounded in order
 to have the given exponent.

**Returns:**

* A decimal number with the same value as this object but with the
 exponent changed. Returns not-a-number (NaN) if the rounding mode is
 Rounding.Unnecessary and the result is not exact.

### Quantize
    @Deprecated public ExtendedDecimal Quantize(int desiredExponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>desiredExponentSmall</code> - The desired exponent for the result. The
 exponent is the number of fractional digits in the result, expressed
 as a negative number. Can also be positive, which eliminates
 lower-order places from the number. For example, -3 means round to
 the thousandth (10^-3, 0.0001), and 3 means round to the thousand
 (10^3, 1000). A value of 0 rounds the number to an integer.

* <code>ctx</code> - A precision context to control precision and rounding of the
 result. If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags). Can be null, in which case the default rounding
 mode is HalfEven.

**Returns:**

* A decimal number with the same value as this object but with the
 exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
 if the rounded result can't fit the given precision, or if the
 context defines an exponent range and the given exponent is outside
 that range.

### Quantize
    @Deprecated public ExtendedDecimal Quantize(ExtendedDecimal otherValue, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>otherValue</code> - A decimal number containing the desired exponent of the
 result. The mantissa is ignored. The exponent is the number of
 fractional digits in the result, expressed as a negative number. Can
 also be positive, which eliminates lower-order places from the
 number. For example, -3 means round to the thousandth (10^-3,
 0.0001), and 3 means round to the thousand (10^3, 1000). A value of 0
 rounds the number to an integer.

* <code>ctx</code> - A precision context to control precision and rounding of the
 result. If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags). Can be null, in which case the default rounding
 mode is HalfEven.

**Returns:**

* A decimal number with the same value as this object but with the
 exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
 if the result can't fit the given precision without rounding, or if
 the precision context defines an exponent range and the given
 exponent is outside that range.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### RoundToIntegralExact
    @Deprecated public ExtendedDecimal RoundToIntegralExact(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context to control precision and rounding of the
 result. If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags). Can be null, in which case the default rounding
 mode is HalfEven.

**Returns:**

* A decimal number rounded to the closest integer representable in the
 given precision. Signals FlagInvalid and returns not-a-number (NaN)
 if the result can't fit the given precision without rounding. Signals
 FlagInvalid and returns not-a-number (NaN) if the precision context
 defines an exponent range, the new exponent must be changed to 0 when
 rounding, and 0 is outside of the valid range of the precision
 context.

### RoundToIntegralNoRoundedFlag
    @Deprecated public ExtendedDecimal RoundToIntegralNoRoundedFlag(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context to control precision and rounding of the
 result. If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags), except that this function will never add the
 FlagRounded and FlagInexact flags (the only difference between this
 and RoundToExponentExact). Can be null, in which case the default
 rounding mode is HalfEven.

**Returns:**

* A decimal number rounded to the closest integer representable in the
 given precision, meaning if the result can't fit the precision,
 additional digits are discarded to make it fit. Signals FlagInvalid
 and returns not-a-number (NaN) if the precision context defines an
 exponent range, the new exponent must be changed to 0 when rounding,
 and 0 is outside of the valid range of the precision context.

### RoundToExponentExact
    @Deprecated public ExtendedDecimal RoundToExponentExact(BigInteger exponent, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponent</code> - The minimum exponent the result can have. This is the
 maximum number of fractional digits in the result, expressed as a
 negative number. Can also be positive, which eliminates lower-order
 places from the number. For example, -3 means round to the thousandth
 (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
 value of 0 rounds the number to an integer.

* <code>ctx</code> - A context object for arbitrary-precision arithmetic settings.

**Returns:**

* A decimal number rounded to the closest value representable in the
 given precision. Signals FlagInvalid and returns not-a-number (NaN)
 if the result can't fit the given precision without rounding. Signals
 FlagInvalid and returns not-a-number (NaN) if the precision context
 defines an exponent range, the new exponent must be changed to the
 given exponent when rounding, and the given exponent is outside of
 the valid range of the precision context.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>exponent</code> is null.

### RoundToExponent
    @Deprecated public ExtendedDecimal RoundToExponent(BigInteger exponent, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponent</code> - The minimum exponent the result can have. This is the
 maximum number of fractional digits in the result, expressed as a
 negative number. Can also be positive, which eliminates lower-order
 places from the number. For example, -3 means round to the thousandth
 (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
 value of 0 rounds the number to an integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null, in which case the
 default rounding mode is HalfEven.

**Returns:**

* A decimal number rounded to the closest value representable in the
 given precision, meaning if the result can't fit the precision,
 additional digits are discarded to make it fit. Signals FlagInvalid
 and returns not-a-number (NaN) if the precision context defines an
 exponent range, the new exponent must be changed to the given
 exponent when rounding, and the given exponent is outside of the
 valid range of the precision context.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>exponent</code> is null.

### RoundToExponentExact
    @Deprecated public ExtendedDecimal RoundToExponentExact(int exponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponentSmall</code> - The minimum exponent the result can have. This is the
 maximum number of fractional digits in the result, expressed as a
 negative number. Can also be positive, which eliminates lower-order
 places from the number. For example, -3 means round to the thousandth
 (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
 value of 0 rounds the number to an integer.

* <code>ctx</code> - A context object for arbitrary-precision arithmetic settings.

**Returns:**

* A decimal number rounded to the closest value representable in the
 given precision. Signals FlagInvalid and returns not-a-number (NaN)
 if the result can't fit the given precision without rounding. Signals
 FlagInvalid and returns not-a-number (NaN) if the precision context
 defines an exponent range, the new exponent must be changed to the
 given exponent when rounding, and the given exponent is outside of
 the valid range of the precision context.

### RoundToExponent
    @Deprecated public ExtendedDecimal RoundToExponent(int exponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponentSmall</code> - The minimum exponent the result can have. This is the
 maximum number of fractional digits in the result, expressed as a
 negative number. Can also be positive, which eliminates lower-order
 places from the number. For example, -3 means round to the thousandth
 (10^-3, 0.0001), and 3 means round to the thousand (10^3, 1000). A
 value of 0 rounds the number to an integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null, in which case the
 default rounding mode is HalfEven.

**Returns:**

* A decimal number rounded to the closest value representable in the
 given precision, meaning if the result can't fit the precision,
 additional digits are discarded to make it fit. Signals FlagInvalid
 and returns not-a-number (NaN) if the precision context defines an
 exponent range, the new exponent must be changed to the given
 exponent when rounding, and the given exponent is outside of the
 valid range of the precision context.

### Multiply
    @Deprecated public ExtendedDecimal Multiply(ExtendedDecimal op, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>op</code> - Another decimal number.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The product of the two decimal numbers.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>op</code> is null.

### MultiplyAndAdd
    @Deprecated public ExtendedDecimal MultiplyAndAdd(ExtendedDecimal op, ExtendedDecimal augend, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>op</code> - The value to multiply.

* <code>augend</code> - The value to add.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The result thisValue * multiplicand + augend.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>op</code> or <code>augend</code> is null.

### MultiplyAndSubtract
    @Deprecated public ExtendedDecimal MultiplyAndSubtract(ExtendedDecimal op, ExtendedDecimal subtrahend, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>op</code> - The value to multiply.

* <code>subtrahend</code> - The value to subtract.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The result thisValue * multiplicand - subtrahend.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>op</code> or <code>subtrahend</code> is null.

### RoundToPrecision
    @Deprecated public ExtendedDecimal RoundToPrecision(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A context for controlling the precision, rounding mode, and
 exponent range. Can be null.

**Returns:**

* The closest value to this object's value, rounded to the specified
 precision. Returns the same value as this object if <code>ctx</code> is
 null or the precision and exponent range are unlimited.

### Plus
    @Deprecated public ExtendedDecimal Plus(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A context for controlling the precision, rounding mode, and
 exponent range. Can be null.

**Returns:**

* The closest value to this object's value, rounded to the specified
 precision. Returns the same value as this object if <code>ctx</code> is
 null or the precision and exponent range are unlimited.

### RoundToBinaryPrecision
    @Deprecated public ExtendedDecimal RoundToBinaryPrecision(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A context for controlling the precision, rounding mode, and
 exponent range. The precision is interpreted as the maximum bit
 length of the mantissa. Can be null.

**Returns:**

* The closest value to this object's value, rounded to the specified
 precision. Returns the same value as this object if <code>ctx</code> is
 null or the precision and exponent range are unlimited.

### SquareRoot
    @Deprecated public ExtendedDecimal SquareRoot(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). <i>This parameter cannot be
 null, as the square root function's results are generally not exact
 for many inputs.</i>

**Returns:**

* The square root. Signals the flag FlagInvalid and returns NaN if
 this object is less than 0 (the square root would be a complex
 number, but the return value is still NaN). Signals FlagInvalid and
 returns not-a-number (NaN) if the parameter <code>ctx</code> is null or
 the precision is unlimited (the context's Precision property is 0).

### Exp
    @Deprecated public ExtendedDecimal Exp(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). <i>This parameter cannot be
 null, as the exponential function's results are generally not
 exact.</i>

**Returns:**

* Exponential of this object. If this object's value is 1, returns an
 approximation to " e" within the given precision. Signals FlagInvalid
 and returns not-a-number (NaN) if the parameter <code>ctx</code> is null
 or the precision is unlimited (the context's Precision property is
 0).

### Log
    @Deprecated public ExtendedDecimal Log(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). <i>This parameter cannot be
 null, as the ln function's results are generally not exact.</i>

**Returns:**

* Ln(this object). Signals the flag FlagInvalid and returns NaN if
 this object is less than 0 (the result would be a complex number with
 a real part equal to Ln of this object's absolute value and an
 imaginary part equal to pi, but the return value is still NaN.).
 Signals FlagInvalid and returns not-a-number (NaN) if the parameter
 <code>ctx</code> is null or the precision is unlimited (the context's
 Precision property is 0). Signals no flags and returns negative
 infinity if this object's value is 0.

### Log10
    @Deprecated public ExtendedDecimal Log10(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). <i>This parameter cannot be
 null, as the ln function's results are generally not exact.</i>

**Returns:**

* Ln(this object)/Ln(10). Signals the flag FlagInvalid and returns
 not-a-number (NaN) if this object is less than 0. Signals FlagInvalid
 and returns not-a-number (NaN) if the parameter <code>ctx</code> is null
 or the precision is unlimited (the context's Precision property is
 0).

### Pow
    @Deprecated public ExtendedDecimal Pow(ExtendedDecimal exponent, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponent</code> - An arbitrary-precision decimal number.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags).

**Returns:**

* This^exponent. Signals the flag FlagInvalid and returns NaN if this
 object and exponent are both 0; or if this value is less than 0 and
 the exponent either has a fractional part or is infinity. Signals
 FlagInvalid and returns not-a-number (NaN) if the parameter <code>ctx</code> is null or the precision is unlimited (the context's Precision
 property is 0), and the exponent has a fractional part.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>exponent</code> is null.

### Pow
    @Deprecated public ExtendedDecimal Pow(int exponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponentSmall</code> - A 32-bit signed integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags).

**Returns:**

* This^exponent. Signals the flag FlagInvalid and returns NaN if this
 object and exponent are both 0.

### Pow
    @Deprecated public ExtendedDecimal Pow(int exponentSmall)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponentSmall</code> - A 32-bit signed integer.

**Returns:**

* This^exponent. Returns not-a-number (NaN) if this object and
 exponent are both 0.

### PI
    @Deprecated public static ExtendedDecimal PI(PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). <i>This parameter cannot be
 null, as &#x3c0; can never be represented exactly.</i>

**Returns:**

* The constant π rounded to the given precision. Signals FlagInvalid
 and returns not-a-number (NaN) if the parameter <code>ctx</code> is null
 or the precision is unlimited (the context's Precision property is
 0).

### MovePointLeft
    @Deprecated public ExtendedDecimal MovePointLeft(int places)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision decimal number.

### MovePointLeft
    @Deprecated public ExtendedDecimal MovePointLeft(int places, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number.

### MovePointLeft
    @Deprecated public ExtendedDecimal MovePointLeft(BigInteger bigPlaces)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### MovePointLeft
    @Deprecated public ExtendedDecimal MovePointLeft(BigInteger bigPlaces, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### MovePointRight
    @Deprecated public ExtendedDecimal MovePointRight(int places)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision decimal number.

### MovePointRight
    @Deprecated public ExtendedDecimal MovePointRight(int places, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number.

### MovePointRight
    @Deprecated public ExtendedDecimal MovePointRight(BigInteger bigPlaces)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### MovePointRight
    @Deprecated public ExtendedDecimal MovePointRight(BigInteger bigPlaces, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* A number whose scale is increased by <code>bigPlaces</code>, but not to
 more than 0.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### ScaleByPowerOfTen
    @Deprecated public ExtendedDecimal ScaleByPowerOfTen(int places)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision decimal number.

### ScaleByPowerOfTen
    @Deprecated public ExtendedDecimal ScaleByPowerOfTen(int places, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision decimal number.

### ScaleByPowerOfTen
    @Deprecated public ExtendedDecimal ScaleByPowerOfTen(BigInteger bigPlaces)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

**Returns:**

* An arbitrary-precision decimal number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### ScaleByPowerOfTen
    @Deprecated public ExtendedDecimal ScaleByPowerOfTen(BigInteger bigPlaces, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* A number whose scale is increased by <code>bigPlaces</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### Precision
    @Deprecated public BigInteger Precision()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision integer.

### Ulp
    @Deprecated public ExtendedDecimal Ulp()
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* An arbitrary-precision decimal number.

### DivideAndRemainderNaturalScale
    @Deprecated public ExtendedDecimal[] DivideAndRemainderNaturalScale(ExtendedDecimal divisor)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

**Returns:**

* A 2 element array consisting of the quotient and remainder in that
 order.

### DivideAndRemainderNaturalScale
    @Deprecated public ExtendedDecimal[] DivideAndRemainderNaturalScale(ExtendedDecimal divisor, PrecisionContext ctx)
Deprecated.&nbsp;Use EDecimal from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>ctx</code> - A precision context object to control the precision, rounding,
 and exponent range of the result. This context will be used only in
 the division portion of the remainder calculation; as a result, it's
 possible for the remainder to have a higher precision than given in
 this context. Flags will be set on the given context only if the
 context's HasFlags is true and the integer part of the division
 result doesn't fit the precision and exponent range without rounding.

**Returns:**

* A 2 element array consisting of the quotient and remainder in that
 order.
