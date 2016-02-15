# com.upokecenter.util.BigInteger

    public final class BigInteger extends Object implements Comparable<BigInteger>

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.EInteger</code> in the <code>PeterO.Numbers</code>
 library (in .NET), or <code>com.upokecenter.numbers.EInteger</code> in the
 <code>com.github.peteroupc/numbers</code>
 artifact (in Java). This new class can be used in the
 <code>CBORObject.FromObject(Object)</code> method (by including the new
 library in your code, among other things), but this version of the
 CBOR library doesn't include any methods that explicitly take an
 <code>EInteger</code> as a parameter or return value.</b></p> An
 arbitrary-precision integer. <p><b>Thread safety:</b>Instances of
 this class are immutable, so they are inherently safe for use by
 multiple threads. Multiple instances of this object with the same
 value are interchangeable, but they should be compared using the
 "Equals" method rather than the "==" operator.</p>

## Fields

* `static BigInteger ONE`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `static BigInteger TEN`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `static BigInteger ZERO`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.

## Methods

* `BigInteger abs()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger add(BigInteger bigintAugend)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int bitLength()`<br>
 Finds the minimum number of bits needed to represent this object's
 value, except for its sign.
* `boolean canFitInInt()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int compareTo(BigInteger other)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger divide(BigInteger bigintDivisor)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger[] divideAndRemainder(BigInteger divisor)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `boolean equals(Object obj)`<br>
 Determines whether this object and another object are equal.
* `static BigInteger fromByteArray(byte[] bytes,
             boolean littleEndian)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `static BigInteger fromBytes(byte[] bytes,
         boolean littleEndian)`<br>
 Initializes an arbitrary-precision integer from an array of bytes.
* `static BigInteger fromRadixString(String str,
               int radix)`<br>
 Converts a string to an arbitrary-precision integer.
* `static BigInteger fromRadixSubstring(String str,
                  int radix,
                  int index,
                  int endIndex)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `static BigInteger fromString(String str)`<br>
 Converts a string to an arbitrary-precision integer.
* `static BigInteger fromSubstring(String str,
             int index,
             int endIndex)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger gcd(BigInteger bigintSecond)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int getDigitCount()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int getLowBit()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int getLowestSetBit()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int getUnsignedBitLength()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int hashCode()`<br>
 Returns the hash code for this instance.
* `int intValue()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int intValueChecked()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int intValueUnchecked()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isEven()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isZero()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `long longValue()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `long longValueChecked()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `long longValueUnchecked()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger mod(BigInteger divisor)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger ModPow(BigInteger pow,
      BigInteger mod)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger multiply(BigInteger bigintMult)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger negate()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger pow(int powerSmall)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger PowBigIntVar(BigInteger power)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger remainder(BigInteger divisor)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger shiftLeft(int numberBits)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger shiftRight(int numberBits)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `int signum()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger sqrt()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger[] sqrtWithRemainder()`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger subtract(BigInteger subtrahend)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `boolean testBit(int index)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
* `byte[] toByteArray(boolean littleEndian)`<br>
 Deprecated.
Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
 Use EInteger from PeterO.Numbers/com.upokecenter.numbers.
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
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>
### TEN
    @Deprecated public static final BigInteger TEN
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>
### ZERO
    @Deprecated public static final BigInteger ZERO
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>
## Method Details

### isEven
    @Deprecated public final boolean isEven()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this value is even; otherwise, <code>false</code>.

### isZero
    @Deprecated public final boolean isZero()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this value is 0; otherwise, <code>false</code>.

### signum
    @Deprecated public final int signum()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* 0 if this value is zero; -1 if this value is negative, or 1 if this
 value is positive.

### fromByteArray
    @Deprecated public static BigInteger fromByteArray(byte[] bytes, boolean littleEndian)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bytes</code> - A byte array.

* <code>littleEndian</code> - A Boolean object.

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bytes</code> is null.

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

### fromRadixSubstring
    @Deprecated public static BigInteger fromRadixSubstring(String str, int radix, int index, int endIndex)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>str</code> - A text string. The desired portion of the string must contain
 only characters allowed by the given radix, except that it may start
 with a minus sign ("-", U+002D) to indicate a negative number. The
 desired portion is not allowed to contain white space characters,
 including spaces.

* <code>radix</code> - A base from 2 to 36. Depending on the radix, the string can use
 the basic digits 0 to 9 (U + 0030 to U + 0039) and then the basic letters
 A to Z (U + 0041 to U + 005A). For example, 0-9 in radix 10, and 0-9,
 then A-F in radix 16.

* <code>index</code> - The index of the string that starts the string portion.

* <code>endIndex</code> - The index of the string that ends the string portion. The
 length will be index + endIndex - 1.

**Returns:**

* An arbitrary-precision integer with the same value as given in the
 string portion.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>index</code> is less than 0,
 <code>endIndex</code> is less than 0, or either is greater than the
 string's length, or <code>endIndex</code> is less than <code>index</code>.

* <code>NumberFormatException</code> - The string portion is empty or in an invalid
 format.

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

### fromSubstring
    @Deprecated public static BigInteger fromSubstring(String str, int index, int endIndex)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>str</code> - A text string. The desired portion of the string must contain
 only basic digits 0 to 9 (U + 0030 to U + 0039), except that it may start
 with a minus sign ("-", U+002D) to indicate a negative number. The
 desired portion is not allowed to contain white space characters,
 including spaces.

* <code>index</code> - The index of the string that starts the string portion.

* <code>endIndex</code> - The index of the string that ends the string portion. The
 length will be index + endIndex - 1.

**Returns:**

* An arbitrary-precision integer with the same value as given in the
 string portion.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>str</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>index</code> is less than 0,
 <code>endIndex</code> is less than 0, or either is greater than the
 string's length, or <code>endIndex</code> is less than <code>index</code>.

* <code>NumberFormatException</code> - The string portion is empty or in an invalid
 format.

### valueOf
    public static BigInteger valueOf(long longerValue)
Converts a 64-bit signed integer to a big integer.

**Parameters:**

* <code>longerValue</code> - A 64-bit signed integer.

**Returns:**

* An arbitrary-precision integer with the same value as the 64-bit
 number.

### abs
    @Deprecated public BigInteger abs()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* This object's value with the sign removed.

### add
    @Deprecated public BigInteger add(BigInteger bigintAugend)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigintAugend</code> - Another arbitrary-precision integer.

**Returns:**

* The sum of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigintAugend</code> is
 null.

### bitLength
    public int bitLength()
Finds the minimum number of bits needed to represent this object&#x27;s
 value, except for its sign. If the value is negative, finds the
 number of bits in a value equal to this object's absolute value minus
 1.

**Returns:**

* The number of bits in this object's value. Returns 0 if this
 object's value is 0 or negative 1.

### canFitInInt
    @Deprecated public boolean canFitInInt()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* <code>true</code> if this object's value is MinValue or greater, and
 MaxValue or less; otherwise, <code>false</code>.

### compareTo
    @Deprecated public int compareTo(BigInteger other)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Specified by:**

* <code>compareTo</code>&nbsp;in interface&nbsp;<code>Comparable&lt;BigInteger&gt;</code>

**Parameters:**

* <code>other</code> - The parameter <code>other</code> is not documented yet.

**Returns:**

* Zero if the values are equal; a negative number if this instance is
 less, or a positive number if this instance is greater.

### divide
    @Deprecated public BigInteger divide(BigInteger bigintDivisor)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigintDivisor</code> - Another arbitrary-precision integer.

**Returns:**

* The quotient of the two objects.

**Throws:**

* <code>ArithmeticException</code> - The divisor is zero.

* <code>NullPointerException</code> - The parameter <code>bigintDivisor</code> is
 null.

* <code>ArithmeticException</code> - Attempted to divide by zero.

### divideAndRemainder
    @Deprecated public BigInteger[] divideAndRemainder(BigInteger divisor)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - An arbitrary-precision integer.

**Returns:**

* An array with two big integers: the first is the quotient, and the
 second is the remainder.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

* <code>ArithmeticException</code> - The parameter <code>divisor</code> is 0.

* <code>ArithmeticException</code> - Attempted to divide by zero.

### equals
    public boolean equals(Object obj)
Determines whether this object and another object are equal.

**Overrides:**

* <code>equals</code>&nbsp;in class&nbsp;<code>Object</code>

**Parameters:**

* <code>obj</code> - An arbitrary object.

**Returns:**

* <code>true</code> if this object and another object are equal; otherwise,
 <code>false</code>.

### gcd
    @Deprecated public BigInteger gcd(BigInteger bigintSecond)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigintSecond</code> - Another arbitrary-precision integer.

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigintSecond</code> is
 null.

### getDigitCount
    @Deprecated public int getDigitCount()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* The number of decimal digits. Returns 1 if this object' s value is
 0.

### hashCode
    public int hashCode()
Returns the hash code for this instance.

**Overrides:**

* <code>hashCode</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A 32-bit signed integer.

### getLowBit
    @Deprecated public int getLowBit()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* The lowest bit set in the number, starting at 0. Returns 0 if this
 value is 0 or odd. (NOTE: In future versions, may return -1 instead
 if this value is 0.).

### getLowestSetBit
    @Deprecated public int getLowestSetBit()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* See getLowBit().

### getUnsignedBitLength
    @Deprecated public int getUnsignedBitLength()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* The number of bits in this object's value. Returns 0 if this
 object's value is 0, and returns 1 if the value is negative 1.

### intValue
    @Deprecated public int intValue()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* A 32-bit signed integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is too big to fit a
 32-bit signed integer.

### intValueChecked
    @Deprecated public int intValueChecked()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* A 32-bit signed integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is too big to fit a
 32-bit signed integer.

### intValueUnchecked
    @Deprecated public int intValueUnchecked()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* A 32-bit signed integer.

### longValue
    @Deprecated public long longValue()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* A 64-bit signed integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is too big to fit a
 64-bit signed integer.

### longValueChecked
    @Deprecated public long longValueChecked()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* A 64-bit signed integer.

**Throws:**

* <code>ArithmeticException</code> - This object's value is too big to fit a
 64-bit signed integer.

### longValueUnchecked
    @Deprecated public long longValueUnchecked()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* A 64-bit signed integer.

### mod
    @Deprecated public BigInteger mod(BigInteger divisor)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - A divisor greater than 0 (the modulus).

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>ArithmeticException</code> - The parameter <code>divisor</code> is negative.

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

### ModPow
    @Deprecated public BigInteger ModPow(BigInteger pow, BigInteger mod)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>pow</code> - Another arbitrary-precision integer.

* <code>mod</code> - An arbitrary-precision integer. (3).

**Returns:**

* An arbitrary-precision integer.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>pow</code> or <code>mod</code> is null.

### multiply
    @Deprecated public BigInteger multiply(BigInteger bigintMult)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>bigintMult</code> - Another arbitrary-precision integer.

**Returns:**

* The product of the two numbers.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigintMult</code> is
 null.

### negate
    @Deprecated public BigInteger negate()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* This object's value with the sign reversed.

### pow
    @Deprecated public BigInteger pow(int powerSmall)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>powerSmall</code> - The exponent to raise to.

**Returns:**

* The result. Returns 1 if <code>powerSmall</code> is 0.

**Throws:**

* <code>IllegalArgumentException</code> - The parameter <code>powerSmall</code> is less
 than 0.

### PowBigIntVar
    @Deprecated public BigInteger PowBigIntVar(BigInteger power)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>power</code> - The exponent to raise to.

**Returns:**

* The result. Returns 1 if <code>power</code> is 0.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>power</code> is null.

* <code>IllegalArgumentException</code> - The parameter <code>power</code> is less than 0.

### remainder
    @Deprecated public BigInteger remainder(BigInteger divisor)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>divisor</code> - Another arbitrary-precision integer.

**Returns:**

* The remainder of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>divisor</code> is null.

* <code>ArithmeticException</code> - Attempted to divide by zero.

### shiftLeft
    @Deprecated public BigInteger shiftLeft(int numberBits)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>numberBits</code> - The number of bits to shift. Can be negative, in which
 case this is the same as shiftRight with the absolute value of
 numberBits.

**Returns:**

* An arbitrary-precision integer.

### shiftRight
    @Deprecated public BigInteger shiftRight(int numberBits)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>numberBits</code> - Number of bits to shift right.

**Returns:**

* An arbitrary-precision integer.

### sqrt
    @Deprecated public BigInteger sqrt()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* The square root of this object's value. Returns 0 if this value is 0
 or less.

### sqrtWithRemainder
    @Deprecated public BigInteger[] sqrtWithRemainder()
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Returns:**

* An array of two big integers: the first integer is the square root,
 and the second is the difference between this value and the square of
 the first integer. Returns two zeros if this value is 0 or less, or
 one and zero if this value equals 1.

### subtract
    @Deprecated public BigInteger subtract(BigInteger subtrahend)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>subtrahend</code> - Another arbitrary-precision integer.

**Returns:**

* The difference of the two objects.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>subtrahend</code> is
 null.

### testBit
    @Deprecated public boolean testBit(int index)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>index</code> - Zero based index of the bit to test. 0 means the least
 significant bit.

**Returns:**

* <code>true</code> if a bit is set in the two's-complement representation
 of this object's value; otherwise, <code>false</code>.

### toByteArray
    @Deprecated public byte[] toByteArray(boolean littleEndian)
Deprecated.&nbsp;<i>Use EInteger from PeterO.Numbers/com.upokecenter.numbers.</i>

**Parameters:**

* <code>littleEndian</code> - A Boolean object.

**Returns:**

* A byte array.

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
