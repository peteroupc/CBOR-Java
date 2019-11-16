# com.upokecenter.cbor.CBORNumber

    public final class CBORNumber extends java.lang.Object implements java.lang.Comparable<CBORNumber>

An instance of a number that CBOR or certain CBOR tags can represent. For
 this purpose, infinities and not-a-number or NaN values are considered
 numbers. Currently, this class can store one of the following kinds of
 numbers: 64-bit signed integers or binary floating-point numbers; or
 arbitrary-precision integers, decimal numbers, binary numbers, or
 rational numbers.

## Methods

* `CBORNumber Abs()`<br>
 Returns the absolute value of this CBOR number.
* `CBORNumber Add​(CBORNumber b)`<br>
 Returns the sum of this number and another number.
* `boolean CanFitInDouble()`<br>
 Not documented yet.
* `boolean CanFitInInt32()`<br>
 Returns whether this object's numerical value is an integer, is -(2^31) or
 greater, and is less than 2^31.
* `boolean CanFitInInt64()`<br>
 Returns whether this object's numerical value is an integer, is -(2^63) or
 greater, and is less than 2^63.
* `boolean CanFitInSingle()`<br>
 Not documented yet.
* `boolean CanTruncatedIntFitInInt32()`<br>
 Not documented yet.
* `boolean CanTruncatedIntFitInInt64()`<br>
 Not documented yet.
* `int compareTo​(CBORNumber other)`<br>
 Compares two CBOR numbers.
* `CBORNumber Divide​(CBORNumber b)`<br>
 Returns the quotient of this number and another number.
* `static CBORNumber FromByte​(byte inputByte)`<br>
 Converts a byte (from 0 to 255) to an arbitrary-precision decimal number.
* `static CBORNumber FromCBORObject​(CBORObject o)`<br>
 Creates a CBOR number object from a CBOR object representing a number (that
 is, one for which the IsNumber property in.NET or the isNumber()
 method in Java returns true).
* `static CBORNumber FromInt16​(short inputInt16)`<br>
 Converts a 16-bit signed integer to an arbitrary-precision decimal number.
* `boolean IsFinite()`<br>
 Not documented yet.
* `boolean IsInfinity()`<br>
 Gets a value indicating whether this object represents infinity.
* `boolean IsInteger()`<br>
 Not documented yet.
* `boolean IsNaN()`<br>
 Gets a value indicating whether this object represents a not-a-number value.
* `boolean IsNegative()`<br>
 Not documented yet.
* `boolean IsNegativeInfinity()`<br>
 Gets a value indicating whether this object represents negative infinity.
* `boolean IsPositiveInfinity()`<br>
 Gets a value indicating whether this object represents positive infinity.
* `boolean IsZero()`<br>
 Not documented yet.
* `CBORNumber Multiply​(CBORNumber b)`<br>
 Returns a CBOR number expressing the product of this number and the given
 number.
* `CBORNumber Negate()`<br>
 Returns a CBOR number with the same value as this one but with the sign
 reversed.
* `CBORNumber Remainder​(CBORNumber b)`<br>
 Returns the remainder when this number is divided by another number.
* `CBORNumber Subtract​(CBORNumber b)`<br>
 Returns a number that expresses this number minus another.
* `byte ToByteChecked()`<br>
 Converts this number's value to a byte (from 0 to 255) if it can fit in a
 byte (from 0 to 255) after converting it to an integer by discarding
 its fractional part.
* `byte ToByteIfExact()`<br>
 Converts this number's value to a byte (from 0 to 255) if it can fit in a
 byte (from 0 to 255) without rounding to a different numerical
 value.
* `byte ToByteUnchecked()`<br>
 Converts this number's value to an integer by discarding its fractional
 part, and returns the least-significant bits of its two's-complement
 form as a byte (from 0 to 255).
* `CBORObject ToCBORObject()`<br>
 Converts this object's value to a CBOR object.
* `com.upokecenter.numbers.EDecimal ToEDecimal()`<br>
 Not documented yet.
* `com.upokecenter.numbers.EFloat ToEFloat()`<br>
 Not documented yet.
* `com.upokecenter.numbers.EInteger ToEInteger()`<br>
 Not documented yet.
* `com.upokecenter.numbers.EInteger ToEIntegerIfExact()`<br>
 Not documented yet.
* `com.upokecenter.numbers.ERational ToERational()`<br>
 Not documented yet.
* `short ToInt16Checked()`<br>
 Converts this number's value to a 16-bit signed integer if it can fit in a
 16-bit signed integer after converting it to an integer by
 discarding its fractional part.
* `short ToInt16IfExact()`<br>
 Converts this number's value to a 16-bit signed integer if it can fit in a
 16-bit signed integer without rounding to a different numerical
 value.
* `short ToInt16Unchecked()`<br>
 Converts this number's value to an integer by discarding its fractional
 part, and returns the least-significant bits of its two's-complement
 form as a 16-bit signed integer.
* `int ToInt32Checked()`<br>
 Converts this number's value to a 32-bit signed integer if it can fit in a
 32-bit signed integer after converting it to an integer by
 discarding its fractional part.
* `int ToInt32IfExact()`<br>
 Converts this number's value to a 32-bit signed integer if it can fit in a
 32-bit signed integer without rounding to a different numerical
 value.
* `int ToInt32Unchecked()`<br>
 Converts this number's value to an integer by discarding its fractional
 part, and returns the least-significant bits of its two's-complement
 form as a 32-bit signed integer.
* `long ToInt64Checked()`<br>
 Converts this number's value to a 64-bit signed integer if it can fit in a
 64-bit signed integer after converting it to an integer by
 discarding its fractional part.
* `long ToInt64IfExact()`<br>
 Converts this number's value to a 64-bit signed integer if it can fit in a
 64-bit signed integer without rounding to a different numerical
 value.
* `long ToInt64Unchecked()`<br>
 Converts this number's value to an integer by discarding its fractional
 part, and returns the least-significant bits of its two's-complement
 form as a 64-bit signed integer.
* `java.lang.String toString()`<br>
 Returns the value of this object in text form.

## Method Details

### ToCBORObject
    public CBORObject ToCBORObject()
Converts this object's value to a CBOR object.

**Returns:**

* A CBOR object that stores this object's value.

### FromCBORObject
    public static CBORNumber FromCBORObject​(CBORObject o)
Creates a CBOR number object from a CBOR object representing a number (that
 is, one for which the IsNumber property in.NET or the isNumber()
 method in Java returns true).

**Parameters:**

* <code>o</code> - The parameter is a CBOR object representing a number.

**Returns:**

* A CBOR number object, or null if the given CBOR object is null or
 does not represent a number.

### CanTruncatedIntFitInInt32
    public boolean CanTruncatedIntFitInInt32()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### CanTruncatedIntFitInInt64
    public boolean CanTruncatedIntFitInInt64()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### CanFitInSingle
    public boolean CanFitInSingle()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### CanFitInDouble
    public boolean CanFitInDouble()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### IsFinite
    public boolean IsFinite()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### IsInteger
    public boolean IsInteger()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### IsNegative
    public boolean IsNegative()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### IsZero
    public boolean IsZero()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### ToEInteger
    public com.upokecenter.numbers.EInteger ToEInteger()
Not documented yet.

**Returns:**

* The return value is not documented yet.

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number.

### ToEIntegerIfExact
    public com.upokecenter.numbers.EInteger ToEIntegerIfExact()
Not documented yet.

**Returns:**

* The return value is not documented yet.

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number or is not
 an exact integer.

### ToByteChecked
    public byte ToByteChecked()
Converts this number's value to a byte (from 0 to 255) if it can fit in a
 byte (from 0 to 255) after converting it to an integer by discarding
 its fractional part.

**Returns:**

* This number's value, truncated to a byte (from 0 to 255).

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number, or the
 number, once converted to an integer by discarding its fractional
 part, is less than 0 or greater than 255.

### ToByteUnchecked
    public byte ToByteUnchecked()
Converts this number's value to an integer by discarding its fractional
 part, and returns the least-significant bits of its two's-complement
 form as a byte (from 0 to 255).

**Returns:**

* This number, converted to a byte (from 0 to 255). Returns 0 if this
 value is infinity or not-a-number.

### ToByteIfExact
    public byte ToByteIfExact()
Converts this number's value to a byte (from 0 to 255) if it can fit in a
 byte (from 0 to 255) without rounding to a different numerical
 value.

**Returns:**

* This number's value as a byte (from 0 to 255).

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number, is not
 an exact integer, or is less than 0 or greater than 255.

### FromByte
    public static CBORNumber FromByte​(byte inputByte)
Converts a byte (from 0 to 255) to an arbitrary-precision decimal number.

**Parameters:**

* <code>inputByte</code> - The number to convert as a byte (from 0 to 255).

**Returns:**

* This number's value as an arbitrary-precision decimal number.

### ToInt16Checked
    public short ToInt16Checked()
Converts this number's value to a 16-bit signed integer if it can fit in a
 16-bit signed integer after converting it to an integer by
 discarding its fractional part.

**Returns:**

* This number's value, truncated to a 16-bit signed integer.

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number, or the
 number, once converted to an integer by discarding its fractional
 part, is less than -32768 or greater than 32767.

### ToInt16Unchecked
    public short ToInt16Unchecked()
Converts this number's value to an integer by discarding its fractional
 part, and returns the least-significant bits of its two's-complement
 form as a 16-bit signed integer.

**Returns:**

* This number, converted to a 16-bit signed integer. Returns 0 if this
 value is infinity or not-a-number.

### ToInt16IfExact
    public short ToInt16IfExact()
Converts this number's value to a 16-bit signed integer if it can fit in a
 16-bit signed integer without rounding to a different numerical
 value.

**Returns:**

* This number's value as a 16-bit signed integer.

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number, is not
 an exact integer, or is less than -32768 or greater than 32767.

### FromInt16
    public static CBORNumber FromInt16​(short inputInt16)
Converts a 16-bit signed integer to an arbitrary-precision decimal number.

**Parameters:**

* <code>inputInt16</code> - The number to convert as a 16-bit signed integer.

**Returns:**

* This number's value as an arbitrary-precision decimal number.

### ToInt32Checked
    public int ToInt32Checked()
Converts this number's value to a 32-bit signed integer if it can fit in a
 32-bit signed integer after converting it to an integer by
 discarding its fractional part.

**Returns:**

* This number's value, truncated to a 32-bit signed integer.

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number, or the
 number, once converted to an integer by discarding its fractional
 part, is less than -2147483648 or greater than 2147483647.

### ToInt32Unchecked
    public int ToInt32Unchecked()
Converts this number's value to an integer by discarding its fractional
 part, and returns the least-significant bits of its two's-complement
 form as a 32-bit signed integer.

**Returns:**

* This number, converted to a 32-bit signed integer. Returns 0 if this
 value is infinity or not-a-number.

### ToInt32IfExact
    public int ToInt32IfExact()
Converts this number's value to a 32-bit signed integer if it can fit in a
 32-bit signed integer without rounding to a different numerical
 value.

**Returns:**

* This number's value as a 32-bit signed integer.

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number, is not
 an exact integer, or is less than -2147483648 or greater than
 2147483647.

### ToInt64Checked
    public long ToInt64Checked()
Converts this number's value to a 64-bit signed integer if it can fit in a
 64-bit signed integer after converting it to an integer by
 discarding its fractional part.

**Returns:**

* This number's value, truncated to a 64-bit signed integer.

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number, or the
 number, once converted to an integer by discarding its fractional
 part, is less than -9223372036854775808 or greater than
 9223372036854775807.

### ToInt64Unchecked
    public long ToInt64Unchecked()
Converts this number's value to an integer by discarding its fractional
 part, and returns the least-significant bits of its two's-complement
 form as a 64-bit signed integer.

**Returns:**

* This number, converted to a 64-bit signed integer. Returns 0 if this
 value is infinity or not-a-number.

### ToInt64IfExact
    public long ToInt64IfExact()
Converts this number's value to a 64-bit signed integer if it can fit in a
 64-bit signed integer without rounding to a different numerical
 value.

**Returns:**

* This number's value as a 64-bit signed integer.

**Throws:**

* <code>java.lang.ArithmeticException</code> - This value is infinity or not-a-number, is not
 an exact integer, or is less than -9223372036854775808 or greater
 than 9223372036854775807.

### toString
    public java.lang.String toString()
Returns the value of this object in text form.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A text string representing the value of this object.

### CanFitInInt32
    public boolean CanFitInInt32()
Returns whether this object's numerical value is an integer, is -(2^31) or
 greater, and is less than 2^31.

**Returns:**

* <code>true</code> if this object's numerical value is an integer, is
 -(2^31) or greater, and is less than 2^31; otherwise, <code>false</code>.

### CanFitInInt64
    public boolean CanFitInInt64()
Returns whether this object's numerical value is an integer, is -(2^63) or
 greater, and is less than 2^63.

**Returns:**

* <code>true</code> if this object's numerical value is an integer, is
 -(2^63) or greater, and is less than 2^63; otherwise, <code>false</code>.

### IsInfinity
    public boolean IsInfinity()
Gets a value indicating whether this object represents infinity.

**Returns:**

* <code>true</code> if this object represents infinity; otherwise, <code>
 false</code>.

### IsPositiveInfinity
    public boolean IsPositiveInfinity()
Gets a value indicating whether this object represents positive infinity.

**Returns:**

* <code>true</code> if this object represents positive infinity; otherwise,
 <code>false</code>.

### IsNegativeInfinity
    public boolean IsNegativeInfinity()
Gets a value indicating whether this object represents negative infinity.

**Returns:**

* <code>true</code> if this object represents negative infinity; otherwise,
 <code>false</code>.

### IsNaN
    public boolean IsNaN()
Gets a value indicating whether this object represents a not-a-number value.

**Returns:**

* <code>true</code> if this object represents a not-a-number value;
 otherwise, <code>false</code>.

### ToEDecimal
    public com.upokecenter.numbers.EDecimal ToEDecimal()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### ToEFloat
    public com.upokecenter.numbers.EFloat ToEFloat()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### ToERational
    public com.upokecenter.numbers.ERational ToERational()
Not documented yet.

**Returns:**

* The return value is not documented yet.

### Abs
    public CBORNumber Abs()
Returns the absolute value of this CBOR number.

**Returns:**

* This object's absolute value without its negative sign.

### Negate
    public CBORNumber Negate()
Returns a CBOR number with the same value as this one but with the sign
 reversed.

**Returns:**

* A CBOR number with the same value as this one but with the sign
 reversed.

### Add
    public CBORNumber Add​(CBORNumber b)
Returns the sum of this number and another number.

**Parameters:**

* <code>b</code> - The number to add with this one.

**Returns:**

* The sum of this number and another number.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>b</code> is null.

### Subtract
    public CBORNumber Subtract​(CBORNumber b)
Returns a number that expresses this number minus another.

**Parameters:**

* <code>b</code> - The second operand to the subtraction.

**Returns:**

* A CBOR number that expresses this number minus the given number.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>b</code> is null.

### Multiply
    public CBORNumber Multiply​(CBORNumber b)
Returns a CBOR number expressing the product of this number and the given
 number.

**Parameters:**

* <code>b</code> - The second operand to the multiplication operation.

**Returns:**

* A number expressing the product of this number and the given number.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>b</code> is null.

### Divide
    public CBORNumber Divide​(CBORNumber b)
Returns the quotient of this number and another number.

**Parameters:**

* <code>b</code> - The right-hand side (divisor) to the division operation.

**Returns:**

* The quotient of this number and another one.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>b</code> is null.

### Remainder
    public CBORNumber Remainder​(CBORNumber b)
Returns the remainder when this number is divided by another number.

**Parameters:**

* <code>b</code> - The right-hand side (dividend) of the remainder operation.

**Returns:**

* The remainder when this number is divided by the other number.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>b</code> is null.

### compareTo
    public int compareTo​(CBORNumber other)
Compares two CBOR numbers. In this implementation, the two numbers'
 mathematical values are compared. Here, NaN (not-a-number) is
 considered greater than any number.

**Specified by:**

* <code>compareTo</code> in interface <code>java.lang.Comparable&lt;CBORNumber&gt;</code>

**Parameters:**

* <code>other</code> - A value to compare with. Can be null.

**Returns:**

* A negative number, if this value is less than the other object; or
 0, if both values are equal; or a positive number, if this value is
 less than the other object or if the other object is null. This
 implementation returns a positive number if.
