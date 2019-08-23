# com.upokecenter.cbor.CBORNumber

    public final class CBORNumber extends java.lang.Object implements java.lang.Comparable<CBORNumber>

An instance of a number that CBOR or certain CBOR tags can represent. For
 this purpose, infinities and not-a-number or NaN values are
 considered numbers. Currently, this class can store one of the
 following kinds of numbers: 64-bit signed integers or binary
 floating-point numbers; or arbitrary-precision integers, decimal
 numbers, binary numbers, or rational numbers.

## Methods

* `CBORNumber Add​(CBORNumber b)`<br>
 Returns the sum of this number and another number.
* `int compareTo​(CBORNumber other)`<br>
 Compares two CBOR numbers.
* `CBORNumber Divide​(CBORNumber b)`<br>
 Returns the quotient of this number and another number.
* `static CBORNumber FromCBORObject​(CBORObject o)`<br>
 Creates a CBOR number object from a CBOR object representing a number (that
 is, one for which the IsNumber property in.NET or the isNumber()
 method in Java returns true).
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
* `CBORObject ToCBORObject()`<br>
 Converts this object's value to a CBOR object.
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

### toString
    public java.lang.String toString()
Returns the value of this object in text form.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A text string representing the value of this object.

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
