# com.upokecenter.util.BigInteger

    @Deprecated public final class BigInteger extends java.lang.Object implements java.lang.Comparable<BigInteger>

Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers and the
 output of this class's toString method.

## Fields

* `static BigInteger ONE`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.

## Methods

* `int bitLength()`<br>
 Deprecated. Finds the minimum number of bits needed to represent this object's value,
 except for its sign.
* `int compareTo​(BigInteger other)`<br>
 Deprecated. Compares this value to another.
* `boolean equals​(java.lang.Object obj)`<br>
 Deprecated. Determines whether this object and another object are equal.
* `static BigInteger fromBytes​(byte[] bytes,
         boolean littleEndian)`<br>
 Deprecated. Initializes an arbitrary-precision integer from an array of bytes.
* `static BigInteger fromRadixString​(java.lang.String str,
               int radix)`<br>
 Deprecated. Converts a string to an arbitrary-precision integer.
* `static BigInteger fromString​(java.lang.String str)`<br>
 Deprecated. Converts a string to an arbitrary-precision integer.
* `int hashCode()`<br>
 Deprecated. Returns the hash code for this instance.
* `byte[] toBytes​(boolean littleEndian)`<br>
 Deprecated. Returns a byte array of this object's value.
* `java.lang.String toRadixString​(int radix)`<br>
 Deprecated. Generates a string representing the value of this object, in the given
 radix.
* `java.lang.String toString()`<br>
 Deprecated. Converts this object to a text string in base 10.
* `static BigInteger valueOf​(long longerValue)`<br>
 Deprecated. Converts a 64-bit signed integer to a big integer.

## Field Details

### ONE
    @Deprecated public static final BigInteger ONE
Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.

## Method Details

### fromBytes
    public static BigInteger fromBytes​(byte[] bytes, boolean littleEndian)
Deprecated.

**Parameters:**

* <code>bytes</code> - A byte array consisting of the two's-complement form of the
 arbitrary-precision integer to create. The last byte contains the
 lowest 8-bits, the next-to-last contains the next lowest 8 bits, and
 so on. To encode negative numbers, take the absolute value of the
 number, subtract by 1, encode the number into bytes, XOR each byte,
 and if the most-significant bit of the first byte isn't set, add an
 additional byte at the start with the value 255. For little-endian,
 the byte order is reversed from the byte order just discussed.

* <code>littleEndian</code> - If true, the byte order is little-endian, or
 least-significant-byte first. If false, the byte order is
 big-endian, or most-significant-byte first.

**Returns:**

* An arbitrary-precision integer. Returns 0 if the byte array's length
 is 0.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>bytes</code> is null.

### fromRadixString
    public static BigInteger fromRadixString​(java.lang.String str, int radix)
Deprecated.

**Parameters:**

* <code>str</code> - A text string. The string must contain only characters allowed by
  the given radix, except that it may start with a minus sign ("-",
 U + 002D) to indicate a negative number. The string is not allowed to
 contain white space characters, including spaces.

* <code>radix</code> - A base from 2 to 36. Depending on the radix, the string can use
 the basic digits 0 to 9 (U + 0030 to U + 0039) and then the basic
 letters A to Z (U + 0041 to U + 005A). For example, 0-9 in radix 10, and
 0-9, then A-F in radix 16.

**Returns:**

* An arbitrary-precision integer with the same value as given in the
 string.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The parameter <code>radix</code> is less than 2
 or greater than 36.

* <code>java.lang.NumberFormatException</code> - The string is empty or in an invalid format.

### fromString
    public static BigInteger fromString​(java.lang.String str)
Deprecated.

**Parameters:**

* <code>str</code> - A text string. The string must contain only basic digits 0 to 9
  (U+0030 to U+0039), except that it may start with a minus sign ("-",
 U + 002D) to indicate a negative number. The string is not allowed to
 contain white space characters, including spaces.

**Returns:**

* An arbitrary-precision integer with the same value as given in the
 string.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

* <code>java.lang.NumberFormatException</code> - The parameter <code>str</code> is in an invalid
 format.

### valueOf
    public static BigInteger valueOf​(long longerValue)
Deprecated.

**Parameters:**

* <code>longerValue</code> - The parameter <code>longerValue</code> is a 64-bit signed
 integer.

**Returns:**

* An arbitrary-precision integer with the same value as the 64-bit
 number.

### bitLength
    public int bitLength()
Deprecated.

**Returns:**

* The number of bits in this object's value. Returns 0 if this
 object's value is 0 or negative 1.

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

* <code>true</code> if this object and another object are equal; otherwise,
 <code>false</code>.

### hashCode
    public int hashCode()
Deprecated.

**Overrides:**

* <code>hashCode</code> in class <code>java.lang.Object</code>

**Returns:**

* A 32-bit signed integer.

### toBytes
    public byte[] toBytes​(boolean littleEndian)
Deprecated.

**Parameters:**

* <code>littleEndian</code> - If true, the least significant bits will appear first.

**Returns:**

* A byte array. If this value is 0, returns a byte array with the
 single element 0.

### toRadixString
    public java.lang.String toRadixString​(int radix)
Deprecated.

**Parameters:**

* <code>radix</code> - A radix from 2 through 36. For example, to generate a
 hexadecimal (base-16) string, specify 16. To generate a decimal
 (base-10) string, specify 10.

**Returns:**

* A string representing the value of this object. If this value is 0,
  returns "0". If negative, the string will begin with a hyphen/minus
  ("-"). Depending on the radix, the string will use the basic digits
 0 to 9 (U + 0030 to U + 0039) and then the basic letters A to Z (U + 0041
 to U + 005A). For example, 0-9 in radix 10, and 0-9, then A-F in radix
 16.

**Throws:**

* <code>java.lang.IllegalArgumentException</code> - The parameter "index" is less than 0,
  "endIndex" is less than 0, or either is greater than the string's
  length, or "endIndex" is less than "index" ; or radix is less than 2
 or greater than 36.

### toString
    public java.lang.String toString()
Deprecated.

**Overrides:**

* <code>toString</code> in class <code>java.lang.Object</code>

**Returns:**

* A string representation of this object. If negative, the string will
  begin with a minus sign ("-", U+002D). The string will use the basic
 digits 0 to 9 (U + 0030 to U + 0039).

### compareTo
    public int compareTo​(BigInteger other)
Deprecated.

**Specified by:**

* <code>compareTo</code> in interface <code>java.lang.Comparable&lt;BigInteger&gt;</code>

**Parameters:**

* <code>other</code> - The parameter <code>other</code> is an arbitrary-precision integer.

**Returns:**

* Less than 0 if this value is less than, 0 if equal to, or greater
 than 0 if greater than the other value.
