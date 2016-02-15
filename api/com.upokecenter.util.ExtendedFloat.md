# com.upokecenter.util.ExtendedFloat

    public final class ExtendedFloat extends Object implements Comparable<ExtendedFloat>

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.EFloat</code> in the <code>PeterO.Numbers</code>
 library (in .NET), or <code>com.upokecenter.numbers.EFloat</code> in the <code>com.github.peteroupc/numbers</code>
 artifact (in Java). This new class can be used in the
 <code>CBORObject.FromObject(Object)</code> method (by including the new
 library in your code, among other things), but this version of the
 CBOR library doesn't include any methods that explicitly take an
 <code>EFloat</code> as a parameter or return value.</b></p> <p> Represents
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
 Java) in the same circumstances.</p> <p><b>Thread
 safety:</b>Instances of this class are immutable, so they are
 inherently safe for use by multiple threads. Multiple instances of
 this object with the same properties are interchangeable, so they
 should not be compared using the "==" operator (which only checks if
 each side of the operator is the same instance).</p>

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

* `ExtendedFloat Abs()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Abs(PrecisionContext context)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Add(ExtendedFloat otherValue)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Add(ExtendedFloat otherValue,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `int compareTo(ExtendedFloat other)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat CompareToSignal(ExtendedFloat other,
               PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat CompareToWithContext(ExtendedFloat other,
                    PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat Create(BigInteger mantissa,
      BigInteger exponent)`<br>
 Creates a number with the value exponent*2^mantissa.
* `static ExtendedFloat Create(int mantissaSmall,
      int exponentSmall)`<br>
 Creates a number with the value exponent*2^mantissa.
* `static ExtendedFloat CreateNaN(BigInteger diag)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat CreateNaN(BigInteger diag,
         boolean signaling,
         boolean negative,
         PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Divide(ExtendedFloat divisor)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Divide(ExtendedFloat divisor,
      PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat[] DivideAndRemainderNaturalScale(ExtendedFloat divisor)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat[] DivideAndRemainderNaturalScale(ExtendedFloat divisor,
                              PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat DivideToExponent(ExtendedFloat divisor,
                BigInteger exponent,
                PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat DivideToExponent(ExtendedFloat divisor,
                BigInteger desiredExponent,
                Rounding rounding)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat DivideToExponent(ExtendedFloat divisor,
                long desiredExponentSmall,
                PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat DivideToExponent(ExtendedFloat divisor,
                long desiredExponentSmall,
                Rounding rounding)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat DivideToIntegerNaturalScale(ExtendedFloat divisor)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat DivideToIntegerNaturalScale(ExtendedFloat divisor,
                           PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat DivideToIntegerZeroScale(ExtendedFloat divisor,
                        PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat DivideToSameExponent(ExtendedFloat divisor,
                    Rounding rounding)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
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
* `ExtendedFloat Exp(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromBigInteger(BigInteger bigint)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromDouble(double dbl)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromInt32(int valueSmaller)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromInt64(long valueSmall)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromSingle(float flt)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromString(String str)`<br>
 Creates a binary float from a text string that represents a number.
* `static ExtendedFloat FromString(String str,
          int offset,
          int length)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromString(String str,
          int offset,
          int length,
          PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat FromString(String str,
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
* `boolean isFinite()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
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
* `boolean isZero()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Log(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Log10(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat Max(ExtendedFloat first,
   ExtendedFloat second)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat Max(ExtendedFloat first,
   ExtendedFloat second,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat MaxMagnitude(ExtendedFloat first,
            ExtendedFloat second)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat MaxMagnitude(ExtendedFloat first,
            ExtendedFloat second,
            PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat Min(ExtendedFloat first,
   ExtendedFloat second)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat Min(ExtendedFloat first,
   ExtendedFloat second,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat MinMagnitude(ExtendedFloat first,
            ExtendedFloat second)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat MinMagnitude(ExtendedFloat first,
            ExtendedFloat second,
            PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MovePointLeft(BigInteger bigPlaces)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MovePointLeft(BigInteger bigPlaces,
             PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MovePointLeft(int places)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MovePointLeft(int places,
             PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MovePointRight(BigInteger bigPlaces)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MovePointRight(BigInteger bigPlaces,
              PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MovePointRight(int places)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MovePointRight(int places,
              PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Multiply(ExtendedFloat otherValue)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Multiply(ExtendedFloat op,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MultiplyAndAdd(ExtendedFloat multiplicand,
              ExtendedFloat augend)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MultiplyAndAdd(ExtendedFloat op,
              ExtendedFloat augend,
              PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat MultiplyAndSubtract(ExtendedFloat op,
                   ExtendedFloat subtrahend,
                   PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Negate()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Negate(PrecisionContext context)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat NextMinus(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat NextPlus(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat NextToward(ExtendedFloat otherValue,
          PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `static ExtendedFloat PI(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Plus(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Pow(ExtendedFloat exponent,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Pow(int exponentSmall)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Pow(int exponentSmall,
   PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger Precision()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Quantize(BigInteger desiredExponent,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Quantize(ExtendedFloat otherValue,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Quantize(int desiredExponentSmall,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Reduce(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Remainder(ExtendedFloat divisor,
         PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RemainderNaturalScale(ExtendedFloat divisor)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RemainderNaturalScale(ExtendedFloat divisor,
                     PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RemainderNear(ExtendedFloat divisor,
             PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RoundToBinaryPrecision(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RoundToExponent(BigInteger exponent,
               PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RoundToExponent(int exponentSmall,
               PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RoundToExponentExact(BigInteger exponent,
                    PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RoundToExponentExact(int exponentSmall,
                    PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RoundToIntegralExact(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RoundToIntegralNoRoundedFlag(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat RoundToPrecision(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat ScaleByPowerOfTwo(BigInteger bigPlaces)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat ScaleByPowerOfTwo(BigInteger bigPlaces,
                 PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat ScaleByPowerOfTwo(int places)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat ScaleByPowerOfTwo(int places,
                 PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `int signum()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat SquareRoot(PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Subtract(ExtendedFloat otherValue)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedFloat Subtract(ExtendedFloat otherValue,
        PrecisionContext ctx)`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger ToBigInteger()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger ToBigIntegerExact()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `double ToDouble()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `String ToEngineeringString()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `ExtendedDecimal ToExtendedDecimal()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `String ToPlainString()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `float ToSingle()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
* `String toString()`<br>
 Converts this value to a string.
* `ExtendedFloat Ulp()`<br>
 Deprecated.
Use EFloat from PeterO.Numbers/com.upokecenter.numbers.
 Use EFloat from PeterO.Numbers/com.upokecenter.numbers.

## Field Details

### One
    @Deprecated public static final ExtendedFloat One
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>
### Zero
    @Deprecated public static final ExtendedFloat Zero
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>
### NegativeZero
    @Deprecated public static final ExtendedFloat NegativeZero
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>
### Ten
    @Deprecated public static final ExtendedFloat Ten
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>
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
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision binary float.

**Returns:**

* <code>true</code> if this object's mantissa and exponent are equal to
 those of another object; otherwise, <code>false</code>.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### equals
    @Deprecated public boolean equals(ExtendedFloat other)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>other</code> - An arbitrary-precision binary float.

**Returns:**

* <code>true</code> if this object's mantissa and exponent are equal to
 those of another object; otherwise, <code>false</code>.

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

* <code>true</code> if the objects are equal; otherwise, <code>false</code>.

### hashCode
    public int hashCode()
Calculates this object&#x27;s hash code.

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* This object's hash code.

### CreateNaN
    @Deprecated public static ExtendedFloat CreateNaN(BigInteger diag)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>diag</code> - A number to use as diagnostic information associated with this
 object. If none is needed, should be zero.

**Returns:**

* A quiet not-a-number.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>diag</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>diag</code> is less than 0.

### CreateNaN
    @Deprecated public static ExtendedFloat CreateNaN(BigInteger diag, boolean signaling, boolean negative, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>diag</code> - A number to use as diagnostic information associated with this
 object. If none is needed, should be zero.

* <code>signaling</code> - Whether the return value will be signaling (true) or quiet
 (false).

* <code>negative</code> - Whether the return value is negative.

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>diag</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>diag</code> is less than 0.

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
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
Creates a binary float from a text string that represents a number. See the
 four-parameter FromString method.

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is not documented yet.

**Returns:**

* The parsed number, converted to arbitrary-precision binary float.

### FromString
    @Deprecated public static ExtendedFloat FromString(String str, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>str</code> - A text string.

* <code>ctx</code> - A PrecisionContext object specifying the precision, rounding, and
 exponent range to apply to the parsed number. Can be null.

**Returns:**

* The parsed number, converted to arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

### FromString
    @Deprecated public static ExtendedFloat FromString(String str, int offset, int length)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>str</code> - A text string.

* <code>offset</code> - A zero-based index showing where the desired portion of <code>str</code> begins.

* <code>length</code> - The length, in code units, of the desired portion of <code>str</code> (but not more than <code>str</code> 's length).

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>IllegalArgumentException</code> - Either <code>offset</code> or <code>length</code> is
 less than 0 or greater than <code>str</code> 's length, or <code>str</code> ' s
 length minus <code>offset</code> is less than <code>length</code>.

### ToBigInteger
    @Deprecated public BigInteger ToBigInteger()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is infinity or NaN.

### ToBigIntegerExact
    @Deprecated public BigInteger ToBigIntegerExact()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is infinity or NaN.

* <code>ArithmeticException</code> - This object's value is not an exact integer.

### ToSingle
    @Deprecated public float ToSingle()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* The closest 32-bit floating-point number to this value. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 32-bit floating point number.

### ToDouble
    @Deprecated public double ToDouble()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* The closest 64-bit floating-point number to this value. The return
 value can be positive infinity or negative infinity if this value
 exceeds the range of a 64-bit floating point number.

### FromSingle
    @Deprecated public static ExtendedFloat FromSingle(float flt)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>flt</code> - A 32-bit floating-point number.

**Returns:**

* A binary float with the same value as <code>flt</code>.

### FromBigInteger
    @Deprecated public static ExtendedFloat FromBigInteger(BigInteger bigint)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigint</code> - An arbitrary-precision integer.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigint</code> is null.

### FromInt64
    @Deprecated public static ExtendedFloat FromInt64(long valueSmall)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>valueSmall</code> - A 64-bit signed integer.

**Returns:**

* An arbitrary-precision binary float with the exponent set to 0.

### FromInt32
    @Deprecated public static ExtendedFloat FromInt32(int valueSmaller)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>valueSmaller</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision binary float with the exponent set to 0.

### FromDouble
    @Deprecated public static ExtendedFloat FromDouble(double dbl)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>dbl</code> - A 64-bit floating-point number.

**Returns:**

* A binary float with the same value as <code>dbl</code>.

### ToExtendedDecimal
    @Deprecated public ExtendedDecimal ToExtendedDecimal()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* An extended decimal with the same value as this extended float.

### toString
    public String toString()
Converts this value to a string.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object. The value is converted to
 decimal and the decimal form of this number's value is returned.

### ToEngineeringString
    @Deprecated public String ToEngineeringString()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* A text string.

### ToPlainString
    @Deprecated public String ToPlainString()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* A text string.

### IsNegativeInfinity
    @Deprecated public boolean IsNegativeInfinity()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this object is negative infinity; otherwise, <code>false</code>.

### IsPositiveInfinity
    @Deprecated public boolean IsPositiveInfinity()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this object is positive infinity; otherwise, <code>false</code>.

### IsNaN
    public boolean IsNaN()
Returns whether this object is a not-a-number value.

**Returns:**

* <code>true</code> if this object is a not-a-number value; otherwise,
 <code>false</code>.

### IsInfinity
    public boolean IsInfinity()
Gets a value indicating whether this object is positive or negative
 infinity.

**Returns:**

* <code>true</code> if this object is positive or negative infinity;
 otherwise, <code>false</code>.

### isFinite
    @Deprecated public final boolean isFinite()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this object is finite (not infinity or NaN);
 otherwise, <code>false</code>.

### isNegative
    @Deprecated public final boolean isNegative()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this object is negative, including negative zero;
 otherwise, <code>false</code>.

### IsQuietNaN
    @Deprecated public boolean IsQuietNaN()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this object is a quiet not-a-number value;
 otherwise, <code>false</code>.

### IsSignalingNaN
    @Deprecated public boolean IsSignalingNaN()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this object is a signaling not-a-number value;
 otherwise, <code>false</code>.

### signum
    @Deprecated public final int signum()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* This value's sign: -1 if negative; 1 if positive; 0 if zero.

### isZero
    @Deprecated public final boolean isZero()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this object's value equals 0; otherwise, <code>false</code>.

### Abs
    @Deprecated public ExtendedFloat Abs()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* An arbitrary-precision binary float.

### Negate
    @Deprecated public ExtendedFloat Negate()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* An arbitrary-precision binary float. If this value is positive zero,
 returns positive zero.

### Divide
    @Deprecated public ExtendedFloat Divide(ExtendedFloat divisor)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - The number to divide by.

**Returns:**

* The quotient of the two numbers. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
 the dividend are 0.

**Throws:**

* <code>ArithmeticException</code> - The result can't be exact because it would have
 a nonterminating binary expansion.

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToSameExponent
    @Deprecated public ExtendedFloat DivideToSameExponent(ExtendedFloat divisor, Rounding rounding)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>rounding</code> - The rounding mode to use if the result must be scaled down
 to have the same exponent as this value.

**Returns:**

* The quotient of the two numbers. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
 the dividend are 0.

**Throws:**

* <code>ArithmeticException</code> - The rounding mode is Rounding.Unnecessary and
 the result is not exact.

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToIntegerNaturalScale
    @Deprecated public ExtendedFloat DivideToIntegerNaturalScale(ExtendedFloat divisor)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat Reduce(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat RemainderNaturalScale(ExtendedFloat divisor)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - The number to divide by.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### RemainderNaturalScale
    @Deprecated public ExtendedFloat RemainderNaturalScale(ExtendedFloat divisor, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>ctx</code> - A precision context object to control the precision, rounding,
 and exponent range of the integer part of the result. This context
 will be used only in the division portion of the remainder
 calculation. Flags will be set on the given context only if the
 context's HasFlags is true and the integer part of the division
 result doesn't fit the precision and exponent range without rounding.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToExponent
    @Deprecated public ExtendedFloat DivideToExponent(ExtendedFloat divisor, long desiredExponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - An arbitrary-precision binary float.

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
 exponent is outside that range.

**Throws:**

* <code>ArithmeticException</code> - The rounding mode is Rounding.Unnecessary and
 the result is not exact.

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### Divide
    @Deprecated public ExtendedFloat Divide(ExtendedFloat divisor, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
 the dividend are 0.

**Throws:**

* <code>ArithmeticException</code> - Either <code>ctx</code> is null or <code>ctx</code> 's
 precision is 0, and the result would have a nonterminating binary
 expansion; or, the rounding mode is Rounding.Unnecessary and the
 result is not exact.

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToExponent
    @Deprecated public ExtendedFloat DivideToExponent(ExtendedFloat divisor, long desiredExponentSmall, Rounding rounding)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - An arbitrary-precision binary float.

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
 the dividend are 0.

**Throws:**

* <code>ArithmeticException</code> - The rounding mode is Rounding.Unnecessary and
 the result is not exact.

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToExponent
    @Deprecated public ExtendedFloat DivideToExponent(ExtendedFloat divisor, BigInteger exponent, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - An arbitrary-precision binary float.

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
 exponent is outside that range.

**Throws:**

* <code>ArithmeticException</code> - The rounding mode is Rounding.Unnecessary and
 the result is not exact.

* <code>NullPointerException</code> - The parameter <code>divisor</code> or <code>exponent</code> is null.

### DivideToExponent
    @Deprecated public ExtendedFloat DivideToExponent(ExtendedFloat divisor, BigInteger desiredExponent, Rounding rounding)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - An arbitrary-precision binary float.

* <code>desiredExponent</code> - The desired exponent. A negative number places the
 cutoff point to the right of the usual decimal point. A positive
 number places the cutoff point to the left of the usual decimal
 point.

* <code>rounding</code> - The rounding mode to use if the result must be scaled down
 to have the same exponent as this value.

**Returns:**

* The quotient of the two objects. Signals FlagDivideByZero and
 returns infinity if the divisor is 0 and the dividend is nonzero.
 Signals FlagInvalid and returns not-a-number (NaN) if the divisor and
 the dividend are 0.

**Throws:**

* <code>ArithmeticException</code> - The rounding mode is Rounding.Unnecessary and
 the result is not exact.

* <code>NullPointerException</code> - The parameter <code>divisor</code> or <code>desiredExponent</code> is null.

### Abs
    @Deprecated public ExtendedFloat Abs(PrecisionContext context)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>context</code> - A precision context to control precision, rounding, and
 exponent range of the result. If HasFlags of the context is true,
 will also store the flags resulting from the operation (the flags are
 in addition to the pre-existing flags). Can be null.

**Returns:**

* The absolute value of this object.

### Negate
    @Deprecated public ExtendedFloat Negate(PrecisionContext context)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>context</code> - A precision context to control precision, rounding, and
 exponent range of the result. If HasFlags of the context is true,
 will also store the flags resulting from the operation (the flags are
 in addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision binary float. If this value is positive zero,
 returns positive zero.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>context</code> is null.

### Add
    @Deprecated public ExtendedFloat Add(ExtendedFloat otherValue)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision binary float.

**Returns:**

* The sum of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Subtract
    @Deprecated public ExtendedFloat Subtract(ExtendedFloat otherValue)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision binary float.

**Returns:**

* The difference of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Subtract
    @Deprecated public ExtendedFloat Subtract(ExtendedFloat otherValue, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision binary float.

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
    @Deprecated public ExtendedFloat Multiply(ExtendedFloat otherValue)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>otherValue</code> - Another binary float.

**Returns:**

* The product of the two binary floats.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### MultiplyAndAdd
    @Deprecated public ExtendedFloat MultiplyAndAdd(ExtendedFloat multiplicand, ExtendedFloat augend)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>multiplicand</code> - The value to multiply.

* <code>augend</code> - The value to add.

**Returns:**

* The result this * multiplicand + augend.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>multiplicand</code> or
 <code>augend</code> is null.

### DivideToIntegerNaturalScale
    @Deprecated public ExtendedFloat DivideToIntegerNaturalScale(ExtendedFloat divisor, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - The number to divide by.

* <code>ctx</code> - A precision context object to control the precision, rounding,
 and exponent range of the integer part of the result. Flags will be
 set on the given context only if the context's HasFlags is true and
 the integer part of the result doesn't fit the precision and exponent
 range without rounding.

**Returns:**

* The integer part of the quotient of the two objects. Returns null if
 the return value would overflow the exponent range. A caller can
 handle a null return value by treating it as positive infinity if
 both operands have the same sign or as negative infinity if both
 operands have different signs. Signals FlagDivideByZero and returns
 infinity if the divisor is 0 and the dividend is nonzero. Signals
 FlagInvalid and returns not-a-number (NaN) if the divisor and the
 dividend are 0.

**Throws:**

* <code>ArithmeticException</code> - The rounding mode is Rounding.Unnecessary and
 the integer part of the result is not exact.

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### DivideToIntegerZeroScale
    @Deprecated public ExtendedFloat DivideToIntegerZeroScale(ExtendedFloat divisor, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat Remainder(ExtendedFloat divisor, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - An arbitrary-precision binary float.

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* The remainder of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### RemainderNear
    @Deprecated public ExtendedFloat RemainderNear(ExtendedFloat divisor, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat NextMinus(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A precision context object to control the precision and exponent
 range of the result. The rounding mode from this context is ignored.
 If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags).

**Returns:**

* Returns the largest value that's less than the given value. Returns
 negative infinity if the result is negative infinity.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>ctx</code> is null, the
 precision is 0, or <code>ctx</code> has an unlimited exponent range.

### NextPlus
    @Deprecated public ExtendedFloat NextPlus(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A precision context object to control the precision and exponent
 range of the result. The rounding mode from this context is ignored.
 If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags).

**Returns:**

* Returns the smallest value that's greater than the given value.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>ctx</code> is null, the
 precision is 0, or <code>ctx</code> has an unlimited exponent range.

### NextToward
    @Deprecated public ExtendedFloat NextToward(ExtendedFloat otherValue, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>otherValue</code> - An arbitrary-precision binary float.

* <code>ctx</code> - A precision context object to control the precision and exponent
 range of the result. The rounding mode from this context is ignored.
 If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags).

**Returns:**

* Returns the next value that is closer to the other object' s value
 than this object's value.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>ctx</code> is null, the
 precision is 0, or <code>ctx</code> has an unlimited exponent range.

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### Max
    @Deprecated public static ExtendedFloat Max(ExtendedFloat first, ExtendedFloat second, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>first</code> - An arbitrary-precision binary float.

* <code>second</code> - Another arbitrary-precision binary float.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The larger value of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### Min
    @Deprecated public static ExtendedFloat Min(ExtendedFloat first, ExtendedFloat second, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>first</code> - An arbitrary-precision binary float.

* <code>second</code> - Another arbitrary-precision binary float.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The smaller value of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### MaxMagnitude
    @Deprecated public static ExtendedFloat MaxMagnitude(ExtendedFloat first, ExtendedFloat second, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>first</code> - Another arbitrary-precision binary float.

* <code>second</code> - An arbitrary-precision binary float. (3).

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### MinMagnitude
    @Deprecated public static ExtendedFloat MinMagnitude(ExtendedFloat first, ExtendedFloat second, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>first</code> - Another arbitrary-precision binary float.

* <code>second</code> - An arbitrary-precision binary float. (3).

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### Max
    @Deprecated public static ExtendedFloat Max(ExtendedFloat first, ExtendedFloat second)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>first</code> - An arbitrary-precision binary float.

* <code>second</code> - Another arbitrary-precision binary float.

**Returns:**

* The larger value of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### Min
    @Deprecated public static ExtendedFloat Min(ExtendedFloat first, ExtendedFloat second)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>first</code> - An arbitrary-precision binary float.

* <code>second</code> - Another arbitrary-precision binary float.

**Returns:**

* The smaller value of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### MaxMagnitude
    @Deprecated public static ExtendedFloat MaxMagnitude(ExtendedFloat first, ExtendedFloat second)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>first</code> - Another arbitrary-precision binary float.

* <code>second</code> - An arbitrary-precision binary float. (3).

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### MinMagnitude
    @Deprecated public static ExtendedFloat MinMagnitude(ExtendedFloat first, ExtendedFloat second)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>first</code> - Another arbitrary-precision binary float.

* <code>second</code> - An arbitrary-precision binary float. (3).

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>first</code> or <code>second</code> is null.

### compareTo
    @Deprecated public int compareTo(ExtendedFloat other)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;ExtendedFloat&gt;</code>

**Parameters:**

* <code>other</code> - An arbitrary-precision binary float.

**Returns:**

* Less than 0 if this object's value is less than the other value, or
 greater than 0 if this object's value is greater than the other value
 or if <code>other</code> is null, or 0 if both values are equal.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>other</code> is null.

### CompareToWithContext
    @Deprecated public ExtendedFloat CompareToWithContext(ExtendedFloat other, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>other</code> - An arbitrary-precision binary float.

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
    @Deprecated public ExtendedFloat CompareToSignal(ExtendedFloat other, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>other</code> - An arbitrary-precision binary float.

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
    @Deprecated public ExtendedFloat Add(ExtendedFloat otherValue, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat Quantize(BigInteger desiredExponent, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>desiredExponent</code> - An arbitrary-precision integer.

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* A binary float with the same value as this object but with the
 exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
 if an overflow error occurred, or the rounded result can't fit the
 given precision, or if the context defines an exponent range and the
 given exponent is outside that range.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>desiredExponent</code>
 is null.

### Quantize
    @Deprecated public ExtendedFloat Quantize(int desiredExponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>desiredExponentSmall</code> - A 32-bit signed integer.

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* A binary float with the same value as this object but with the
 exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
 if an overflow error occurred, or the rounded result can't fit the
 given precision, or if the context defines an exponent range and the
 given exponent is outside that range.

### Quantize
    @Deprecated public ExtendedFloat Quantize(ExtendedFloat otherValue, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>otherValue</code> - A binary float containing the desired exponent of the
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

* A binary float with the same value as this object but with the
 exponent changed. Signals FlagInvalid and returns not-a-number (NaN)
 if an overflow error occurred, or the result can't fit the given
 precision without rounding. Signals FlagInvalid and returns
 not-a-number (NaN) if the new exponent is outside of the valid range
 of the precision context, if it defines an exponent range.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>otherValue</code> is
 null.

### RoundToIntegralExact
    @Deprecated public ExtendedFloat RoundToIntegralExact(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A precision context to control precision and rounding of the
 result. If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags). Can be null, in which case the default rounding
 mode is HalfEven.

**Returns:**

* A binary number rounded to the closest integer representable in the
 given precision. Signals FlagInvalid and returns not-a-number (NaN)
 if the result can't fit the given precision without rounding. Signals
 FlagInvalid and returns not-a-number (NaN) if the precision context
 defines an exponent range, the new exponent must be changed to 0 when
 rounding, and 0 is outside of the valid range of the precision
 context.

### RoundToIntegralNoRoundedFlag
    @Deprecated public ExtendedFloat RoundToIntegralNoRoundedFlag(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A precision context to control precision and rounding of the
 result. If HasFlags of the context is true, will also store the flags
 resulting from the operation (the flags are in addition to the
 pre-existing flags), except that this function will never add the
 FlagRounded and FlagInexact flags (the only difference between this
 and RoundToExponentExact). Can be null, in which case the default
 rounding mode is HalfEven.

**Returns:**

* A binary number rounded to the closest integer representable in the
 given precision, meaning if the result can't fit the precision,
 additional digits are discarded to make it fit. Signals FlagInvalid
 and returns not-a-number (NaN) if the precision context defines an
 exponent range, the new exponent must be changed to 0 when rounding,
 and 0 is outside of the valid range of the precision context.

### RoundToExponentExact
    @Deprecated public ExtendedFloat RoundToExponentExact(BigInteger exponent, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>exponent</code> - The minimum exponent the result can have. This is the
 maximum number of fractional digits in the result, expressed as a
 negative number. Can also be positive, which eliminates lower-order
 places from the number. For example, -3 means round to the sixteenth
 (10b^-3, 0.0001b), and 3 means round to the sixteen-place (10b^3,
 1000b). A value of 0 rounds the number to an integer.

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* A binary number rounded to the closest value representable in the
 given precision. Signals FlagInvalid and returns not-a-number (NaN)
 if the result can't fit the given precision without rounding. Signals
 FlagInvalid and returns not-a-number (NaN) if the precision context
 defines an exponent range, the new exponent must be changed to the
 given exponent when rounding, and the given exponent is outside of
 the valid range of the precision context.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>exponent</code> is null.

### RoundToExponent
    @Deprecated public ExtendedFloat RoundToExponent(BigInteger exponent, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>exponent</code> - The minimum exponent the result can have. This is the
 maximum number of fractional digits in the result, expressed as a
 negative number. Can also be positive, which eliminates lower-order
 places from the number. For example, -3 means round to the sixteenth
 (10b^-3, 0.0001b), and 3 means round to the sixteen-place (10b^3,
 1000b). A value of 0 rounds the number to an integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null, in which case the
 default rounding mode is HalfEven.

**Returns:**

* A binary number rounded to the closest value representable in the
 given precision, meaning if the result can't fit the precision,
 additional digits are discarded to make it fit. Signals FlagInvalid
 and returns not-a-number (NaN) if the precision context defines an
 exponent range, the new exponent must be changed to the given
 exponent when rounding, and the given exponent is outside of the
 valid range of the precision context.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>exponent</code> is null.

### RoundToExponentExact
    @Deprecated public ExtendedFloat RoundToExponentExact(int exponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>exponentSmall</code> - The minimum exponent the result can have. This is the
 maximum number of fractional digits in the result, expressed as a
 negative number. Can also be positive, which eliminates lower-order
 places in the number. For example, -3 means round to the sixteenth
 (10b^-3, 0.0001b), and 3 means round to the sixteen-place (10b^3,
 1000b). A value of 0 rounds the number to an integer.

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* A binary number rounded to the closest value representable in the
 given precision. Signals FlagInvalid and returns not-a-number (NaN)
 if the result can't fit the given precision without rounding. Signals
 FlagInvalid and returns not-a-number (NaN) if the precision context
 defines an exponent range, the new exponent must be changed to the
 given exponent when rounding, and the given exponent is outside of
 the valid range of the precision context.

### RoundToExponent
    @Deprecated public ExtendedFloat RoundToExponent(int exponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>exponentSmall</code> - The minimum exponent the result can have. This is the
 maximum number of fractional digits in the result, expressed as a
 negative number. Can also be positive, which eliminates lower-order
 places in the number. For example, -3 means round to the sixteenth
 (10b^-3, 0.0001b), and 3 means round to the sixteen-place (10b^3,
 1000b). A value of 0 rounds the number to an integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null, in which case the
 default rounding mode is HalfEven.

**Returns:**

* A binary number rounded to the closest value representable in the
 given precision, meaning if the result can't fit the precision,
 additional digits are discarded to make it fit. Signals FlagInvalid
 and returns not-a-number (NaN) if the precision context defines an
 exponent range, the new exponent must be changed to the given
 exponent when rounding, and the given exponent is outside of the
 valid range of the precision context.

### Multiply
    @Deprecated public ExtendedFloat Multiply(ExtendedFloat op, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>op</code> - Another binary float.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* The product of the two binary floats.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>op</code> is null.

### MultiplyAndAdd
    @Deprecated public ExtendedFloat MultiplyAndAdd(ExtendedFloat op, ExtendedFloat augend, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat MultiplyAndSubtract(ExtendedFloat op, ExtendedFloat subtrahend, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat RoundToPrecision(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A context for controlling the precision, rounding mode, and
 exponent range. Can be null.

**Returns:**

* The closest value to this object's value, rounded to the specified
 precision. Returns the same value as this object if <code>ctx</code> is
 null or the precision and exponent range are unlimited.

### Plus
    @Deprecated public ExtendedFloat Plus(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A context for controlling the precision, rounding mode, and
 exponent range. Can be null.

**Returns:**

* The closest value to this object's value, rounded to the specified
 precision. Returns the same value as this object if <code>ctx</code> is
 null or the precision and exponent range are unlimited.

### RoundToBinaryPrecision
    @Deprecated public ExtendedFloat RoundToBinaryPrecision(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A context for controlling the precision, rounding mode, and
 exponent range. The precision is interpreted as the maximum bit
 length of the mantissa. Can be null.

**Returns:**

* The closest value to this object's value, rounded to the specified
 precision. Returns the same value as this object if <code>ctx</code> is
 null or the precision and exponent range are unlimited.

### SquareRoot
    @Deprecated public ExtendedFloat SquareRoot(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
 number, but the return value is still NaN).

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>ctx</code> is null or the
 precision is unlimited (the context's Precision property is 0).

### Exp
    @Deprecated public ExtendedFloat Exp(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). <i>This parameter cannot be
 null, as the exponential function's results are generally not
 exact.</i>

**Returns:**

* Exponential of this object. If this object's value is 1, returns an
 approximation to " e" within the given precision.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>ctx</code> is null or the
 precision is unlimited (the context's Precision property is 0).

### Log
    @Deprecated public ExtendedFloat Log(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>ctx</code> is null or the
 precision is unlimited (the context's Precision property is 0).

### Log10
    @Deprecated public ExtendedFloat Log10(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat Pow(ExtendedFloat exponent, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>exponent</code> - An arbitrary-precision binary float.

* <code>ctx</code> - A PrecisionContext object.

**Returns:**

* This^exponent. Signals the flag FlagInvalid and returns NaN if this
 object and exponent are both 0; or if this value is less than 0 and
 the exponent either has a fractional part or is infinity.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>ctx</code> is null or the
 precision is unlimited (the context's Precision property is 0), and
 the exponent has a fractional part.

* <code>NullPointerException</code> - The parameter <code>exponent</code> is null.

### Pow
    @Deprecated public ExtendedFloat Pow(int exponentSmall, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
    @Deprecated public ExtendedFloat Pow(int exponentSmall)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>exponentSmall</code> - A 32-bit signed integer.

**Returns:**

* This^exponent. Returns not-a-number (NaN) if this object and
 exponent are both 0.

### PI
    @Deprecated public static ExtendedFloat PI(PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). <i>This parameter cannot be
 null, as &#x3c0; can never be represented exactly.</i>

**Returns:**

* The constant π rounded to the given precision.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>ctx</code> is null or the
 precision is unlimited (the context's Precision property is 0).

### MovePointLeft
    @Deprecated public ExtendedFloat MovePointLeft(int places)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision binary float.

### MovePointLeft
    @Deprecated public ExtendedFloat MovePointLeft(int places, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision binary float.

### MovePointLeft
    @Deprecated public ExtendedFloat MovePointLeft(BigInteger bigPlaces)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### MovePointLeft
    @Deprecated public ExtendedFloat MovePointLeft(BigInteger bigPlaces, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### MovePointRight
    @Deprecated public ExtendedFloat MovePointRight(int places)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision binary float.

### MovePointRight
    @Deprecated public ExtendedFloat MovePointRight(int places, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision binary float.

### MovePointRight
    @Deprecated public ExtendedFloat MovePointRight(BigInteger bigPlaces)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### MovePointRight
    @Deprecated public ExtendedFloat MovePointRight(BigInteger bigPlaces, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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

### ScaleByPowerOfTwo
    @Deprecated public ExtendedFloat ScaleByPowerOfTwo(int places)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

**Returns:**

* An arbitrary-precision binary float.

### ScaleByPowerOfTwo
    @Deprecated public ExtendedFloat ScaleByPowerOfTwo(int places, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>places</code> - A 32-bit signed integer.

* <code>ctx</code> - A precision context to control precision, rounding, and exponent
 range of the result. If HasFlags of the context is true, will also
 store the flags resulting from the operation (the flags are in
 addition to the pre-existing flags). Can be null.

**Returns:**

* An arbitrary-precision binary float.

### ScaleByPowerOfTwo
    @Deprecated public ExtendedFloat ScaleByPowerOfTwo(BigInteger bigPlaces)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigPlaces</code> - An arbitrary-precision integer.

**Returns:**

* An arbitrary-precision binary float.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigPlaces</code> is
 null.

### ScaleByPowerOfTwo
    @Deprecated public ExtendedFloat ScaleByPowerOfTwo(BigInteger bigPlaces, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* An arbitrary-precision integer.

### Ulp
    @Deprecated public ExtendedFloat Ulp()
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* An arbitrary-precision binary float.

### DivideAndRemainderNaturalScale
    @Deprecated public ExtendedFloat[] DivideAndRemainderNaturalScale(ExtendedFloat divisor)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - The number to divide by.

**Returns:**

* A 2 element array consisting of the quotient and remainder in that
 order.

### DivideAndRemainderNaturalScale
    @Deprecated public ExtendedFloat[] DivideAndRemainderNaturalScale(ExtendedFloat divisor, PrecisionContext ctx)
Deprecated.&nbsp;<i>Use EFloat from PeterO.Numbers/com.upokecenter.numbers.</i>

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
