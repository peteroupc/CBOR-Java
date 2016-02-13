# com.upokecenter.util.BigInteger

    public final class BigInteger extends Object implements Comparable<BigInteger>

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.EInteger</code> in the <code>PeterO.Numbers</code>
 library (in .NET), or <code>com.upokecenter.numbers.EInteger</code> in the
 <code>com.github.peteroupc/numbers</code>
 artifact (in Java). This new class can be used in the
 <code>CBORObject.FromObject(object)</code> method (by including the new
 library in your code, among other things), but this version of the
 CBOR library doesn't include any methods that explicitly take an
 <code>EInteger</code> as a parameter or return value.</b></p> An
 arbitrary-precision integer. <p><b>Thread safety:</b> Instances of
 this class are immutable, so they are inherently safe for use by
 multiple threads. Multiple instances of this object with the same
 value are interchangeable, but they should be compared using the
 "Equals" method rather than the "==" operator.</p>

## Fields

* `static BigInteger ONE`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.

## Methods

* `int bitLength()`<br>
 Finds the minimum number of bits needed to represent this object's
 value, except for its sign.
* `int compareTo(BigInteger other)`<br>
 Not documented yet.
* `boolean equals(Object obj)`<br>
 Determines whether this object and another object are equal.
* `static BigInteger fromBytes(byte[] bytes,
         boolean littleEndian)`<br>
 Initializes an arbitrary-precision integer from an array of bytes.
* `static BigInteger fromRadixString(String str,
               int radix)`<br>
 Converts a string to an arbitrary-precision integer.
* `static BigInteger fromString(String str)`<br>
 Converts a string to an arbitrary-precision integer.
* `int hashCode()`<br>
 Returns the hash code for this instance.
* `byte[] toBytes(boolean littleEndian)`<br>
 Returns a byte array of this object's value.
* `String toRadixString(int radix)`<br>
 Generates a string representing the value of this object, in the given
 radix.
* `String toString()`<br>
 Converts this object to a text string in base 10.
* `static BigInteger valueOf(long longerValue)`<br>
 Converts a 64-bit signed integer to a big integer.

## Field Details

### ONE
    @Deprecated public static final BigInteger ONE
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;<span class='deprecationComment'>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</span>
## Method Details

### fromBytes
    public static BigInteger fromBytes(byte[] bytes, boolean littleEndian)
Initializes an arbitrary-precision integer from an array of bytes.

**Parameters:**

* <code>bytes</code> - A byte array consisting of the two's-complement integer
 representation of the arbitrary-precision integer to create. The last
 byte contains the lowest 8-bits, the next-to-last contains the next
 lowest 8 bits, and so on. To encode negative numbers, take the
 absolute value of the number, subtract by 1, encode the number into
 bytes, XOR each byte, and if the most-significant bit of the first
 byte isn't set, add an additional byte at the start with the value
 255. For little-endian, the byte order is reversed from the byte
 order just discussed.

* <code>littleEndian</code> - If true, the byte order is little-endian, or
 least-significant-byte first. If false, the byte order is big-endian,
 or most-significant-byte first.

**Returns:**

* An arbitrary-precision integer. Returns 0 if the byte array's length
 is 0.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bytes</code> is null.

### fromRadixString
    public static BigInteger fromRadixString(String str, int radix)
Converts a string to an arbitrary-precision integer.

**Parameters:**

* <code>str</code> - A text string. The string must contain only characters allowed by
 the given radix, except that it may start with a minus sign ("-",
 U + 002D) to indicate a negative number. The string is not allowed to
 contain white space characters, including spaces.

* <code>radix</code> - A base from 2 to 36. Depending on the radix, the string can use
 the basic digits 0 to 9 (U + 0030 to U + 0039) and then the basic letters
 A to Z (U + 0041 to U + 005A). For example, 0-9 in radix 10, and 0-9,
 then A-F in radix 16.

**Returns:**

* An arbitrary-precision integer with the same value as given in the
 string.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>radix</code> is less than 2
 or greater than 36.

* <code>NumberFormatException</code> - The string is empty or in an invalid format.

### fromString
    public static BigInteger fromString(String str)
Converts a string to an arbitrary-precision integer.

**Parameters:**

* <code>str</code> - A text string. The string must contain only basic digits 0 to 9
 (U+0030 to U+0039), except that it may start with a minus sign ("-",
 U + 002D) to indicate a negative number. The string is not allowed to
 contain white space characters, including spaces.

**Returns:**

* An arbitrary-precision integer with the same value as given in the
 string.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>NumberFormatException</code> - The parameter <code>str</code> is in an invalid
 format.

### valueOf
    public static BigInteger valueOf(long longerValue)
Converts a 64-bit signed integer to a big integer.

**Parameters:**

* <code>longerValue</code> - A 64-bit signed integer.

**Returns:**

* An arbitrary-precision integer with the same value as the 64-bit
 number.

### bitLength
    public int bitLength()
Finds the minimum number of bits needed to represent this object&#x27;s
 value, except for its sign. If the value is negative, finds the
 number of bits in a value equal to this object's absolute value minus
 1.

**Returns:**

* The number of bits in this object's value. Returns 0 if this
 object's value is 0 or negative 1.

### equals
    public boolean equals(Object obj)
Determines whether this object and another object are equal.

**Overrides:**

* <code>equals</code>&nbsp;in class&nbsp;<code>Object</code>

**Parameters:**

* <code>obj</code> - An arbitrary object.

**Returns:**

* true if this object and another object are equal; otherwise, false.

### hashCode
    public int hashCode()
Returns the hash code for this instance.

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A 32-bit signed integer.

### toBytes
    public byte[] toBytes(boolean littleEndian)
Returns a byte array of this object&#x27;s value. The byte array will take
 the form of the number's two' s-complement representation, using the
 fewest bytes necessary to represent its value unambiguously. If this
 value is negative, the bits that appear "before" the most significant
 bit of the number will be all ones.

**Parameters:**

* <code>littleEndian</code> - If true, the least significant bits will appear first.

**Returns:**

* A byte array. If this value is 0, returns a byte array with the
 single element 0.

### toRadixString
    public String toRadixString(int radix)
Generates a string representing the value of this object, in the given
 radix.

**Parameters:**

* <code>radix</code> - A radix from 2 through 36. For example, to generate a
 hexadecimal (base-16) string, specify 16. To generate a decimal
 (base-10) string, specify 10.

**Returns:**

* A string representing the value of this object. If this value is 0,
 returns "0". If negative, the string will begin with a hyphen/minus
 ("-"). Depending on the radix, the string will use the basic digits 0
 to 9 (U + 0030 to U + 0039) and then the basic letters A to Z (U + 0041 to
 U + 005A). For example, 0-9 in radix 10, and 0-9, then A-F in radix 16.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter "index" is less than 0,
 "endIndex" is less than 0, or either is greater than the string's
 length, or "endIndex" is less than "index" ; or radix is less than 2
 or greater than 36.

### toString
    public String toString()
Converts this object to a text string in base 10.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object. If negative, the string will
 begin with a minus sign ("-", U+002D). The string will use the basic
 digits 0 to 9 (U + 0030 to U + 0039).

### compareTo
    public int compareTo(BigInteger other)
Not documented yet.

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;BigInteger&gt;</code>

**Parameters:**

* <code>other</code> - A BigInteger object.

**Returns:**

* A 32-bit signed integer.
