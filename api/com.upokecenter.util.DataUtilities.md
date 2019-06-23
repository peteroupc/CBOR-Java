# com.upokecenter.util.DataUtilities

    public final class DataUtilities extends java.lang.Object

Contains methods useful for reading and writing strings. It is designed to
 have no dependencies other than the basic runtime class library.
 <p>Many of these methods work with text encoded in UTF-8, an encoding
 form of the Unicode Standard which uses one byte to encode the most
 basic characters and two to four bytes to encode other characters. For
 example, the <code>GetUtf8</code> method converts a text string to an array
 of bytes in UTF-8. </p> <p>In C# and Java, text strings are represented
 as sequences of 16-bit values called <code>char</code> s. These sequences are
 well-formed under UTF-16, a 16-bit encoding form of Unicode, except if
 they contain unpaired surrogate code points. (A surrogate code point is
 used to encode supplementary characters, those with code points U + 10000
 or higher, in UTF-16. A surrogate pair is a high surrogate [U + D800 to
 U + DBFF] followed by a low surrogate [U + DC00 to U + DFFF]. An unpaired
 surrogate code point is a surrogate not appearing in a surrogate pair.)
 Many of the methods in this class allow setting the behavior to follow
 when unpaired surrogate code points are found in text strings, such as
 throwing an error or treating the unpaired surrogate as a replacement
 character (U + FFFD). </p>

## Methods

* `static int CodePointAt​(java.lang.String str,
           int index)`<br>
 Gets the Unicode code point at the given index of the string.
* `static int CodePointAt​(java.lang.String str,
           int index,
           int surrogateBehavior)`<br>
 Gets the Unicode code point at the given index of the string.
* `static int CodePointBefore​(java.lang.String str,
               int index)`<br>
 Gets the Unicode code point just before the given index of the string.
* `static int CodePointBefore​(java.lang.String str,
               int index,
               int surrogateBehavior)`<br>
 Gets the Unicode code point just before the given index of the string.
* `static int CodePointCompare​(java.lang.String strA,
                java.lang.String strB)`<br>
 Compares two strings in Unicode code point order.
* `static int CodePointLength​(java.lang.String str)`<br>
 Finds the number of Unicode code points in the given text string.
* `static byte[] GetUtf8Bytes​(java.lang.String str,
            boolean replace)`<br>
 Encodes a string in UTF-8 as a byte array.
* `static byte[] GetUtf8Bytes​(java.lang.String str,
            boolean replace,
            boolean lenientLineBreaks)`<br>
 Encodes a string in UTF-8 as a byte array.
* `static long GetUtf8Length​(java.lang.String str,
             boolean replace)`<br>
 Calculates the number of bytes needed to encode a string in UTF-8.
* `static java.lang.String GetUtf8String​(byte[] bytes,
             boolean replace)`<br>
 Generates a text string from a UTF-8 byte array.
* `static java.lang.String GetUtf8String​(byte[] bytes,
             int offset,
             int bytesCount,
             boolean replace)`<br>
 Generates a text string from a portion of a UTF-8 byte array.
* `static int ReadUtf8​(java.io.InputStream stream,
        int bytesCount,
        java.lang.StringBuilder builder,
        boolean replace)`<br>
 Reads a string in UTF-8 encoding from a data stream.
* `static int ReadUtf8FromBytes​(byte[] data,
                 int offset,
                 int bytesCount,
                 java.lang.StringBuilder builder,
                 boolean replace)`<br>
 Reads a string in UTF-8 encoding from a byte array.
* `static java.lang.String ReadUtf8ToString​(java.io.InputStream stream)`<br>
 Reads a string in UTF-8 encoding from a data stream in full and returns that
 string.
* `static java.lang.String ReadUtf8ToString​(java.io.InputStream stream,
                int bytesCount,
                boolean replace)`<br>
 Reads a string in UTF-8 encoding from a data stream and returns that string.
* `static java.lang.String ToLowerCaseAscii​(java.lang.String str)`<br>
 Returns a string with the basic upper-case letters A to Z (U + 0041 to U + 005A)
 converted to lower-case.
* `static java.lang.String ToUpperCaseAscii​(java.lang.String str)`<br>
 Returns a string with the basic lower-case letters A to Z (U + 0061 to U + 007A)
 converted to upper-case.
* `static int WriteUtf8​(java.lang.String str,
         int offset,
         int length,
         java.io.OutputStream stream,
         boolean replace)`<br>
 Writes a portion of a string in UTF-8 encoding to a data stream.
* `static int WriteUtf8​(java.lang.String str,
         int offset,
         int length,
         java.io.OutputStream stream,
         boolean replace,
         boolean lenientLineBreaks)`<br>
 Writes a portion of a string in UTF-8 encoding to a data stream.
* `static int WriteUtf8​(java.lang.String str,
         java.io.OutputStream stream,
         boolean replace)`<br>
 Writes a string in UTF-8 encoding to a data stream.

## Method Details

### GetUtf8String
    public static java.lang.String GetUtf8String​(byte[] bytes, boolean replace)
Generates a text string from a UTF-8 byte array.

**Parameters:**

* <code>bytes</code> - A byte array containing text encoded in UTF-8.

* <code>replace</code> - If true, replaces invalid encoding with the replacement
 character (U + FFFD). If false, stops processing when invalid UTF-8 is
 seen.

**Returns:**

* A string represented by the UTF-8 byte array.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>bytes</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The string is not valid UTF-8 and <code>
 replace</code> is false.

### CodePointLength
    public static int CodePointLength​(java.lang.String str)
Finds the number of Unicode code points in the given text string. Unpaired
 surrogate code points increase this number by 1. This is not
 necessarily the length of the string in "char" s.

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

**Returns:**

* The number of Unicode code points in the given string.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

### GetUtf8String
    public static java.lang.String GetUtf8String​(byte[] bytes, int offset, int bytesCount, boolean replace)
Generates a text string from a portion of a UTF-8 byte array.

**Parameters:**

* <code>bytes</code> - A byte array containing text encoded in UTF-8.

* <code>offset</code> - Offset into the byte array to start reading.

* <code>bytesCount</code> - Length, in bytes, of the UTF-8 text string.

* <code>replace</code> - If true, replaces invalid encoding with the replacement
 character (U + FFFD). If false, stops processing when invalid UTF-8 is
 seen.

**Returns:**

* A string represented by the UTF-8 byte array.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>bytes</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The portion of the byte array is not valid
 UTF-8 and <code>replace</code> is false.

* <code>java.lang.IllegalArgumentException</code> - The parameter <code>offset</code> is less than
 0, <code>bytesCount</code> is less than 0, or offset plus bytesCount is
 greater than the length of "data" .

### GetUtf8Bytes
    public static byte[] GetUtf8Bytes​(java.lang.String str, boolean replace)
<p>Encodes a string in UTF-8 as a byte array. This method does not insert a
 byte-order mark (U + FEFF) at the beginning of the encoded byte array.
 </p> <p>REMARK: It is not recommended to use
 <code>Encoding.UTF8.GetBytes</code> in .NET, or the <code>getBytes()</code>
 method in Java to do this. For instance, <code>getBytes()</code> encodes
 text strings in a default (so not fixed) character encoding, which
 can be undesirable. </p>

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

* <code>replace</code> - If true, replaces unpaired surrogate code points with the
 replacement character (U + FFFD). If false, stops processing when an
 unpaired surrogate code point is seen.

**Returns:**

* The string encoded in UTF-8.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The string contains an unpaired surrogate
 code point and <code>replace</code> is false, or an internal error
 occurred.

### GetUtf8Bytes
    public static byte[] GetUtf8Bytes​(java.lang.String str, boolean replace, boolean lenientLineBreaks)
<p>Encodes a string in UTF-8 as a byte array. This method does not insert a
 byte-order mark (U + FEFF) at the beginning of the encoded byte array.
 </p> <p>REMARK: It is not recommended to use
 <code>Encoding.UTF8.GetBytes</code> in .NET, or the <code>getBytes()</code>
 method in Java to do this. For instance, <code>getBytes()</code> encodes
 text strings in a default (so not fixed) character encoding, which
 can be undesirable. </p>

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

* <code>replace</code> - If true, replaces unpaired surrogate code points with the
 replacement character (U + FFFD). If false, stops processing when an
 unpaired surrogate code point is seen.

* <code>lenientLineBreaks</code> - If true, replaces carriage return (CR) not followed
 by line feed (LF) and LF not preceded by CR with CR-LF pairs.

**Returns:**

* The string encoded in UTF-8.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The string contains an unpaired surrogate
 code point and <code>replace</code> is false, or an internal error
 occurred.

### GetUtf8Length
    public static long GetUtf8Length​(java.lang.String str, boolean replace)
Calculates the number of bytes needed to encode a string in UTF-8.

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

* <code>replace</code> - If true, treats unpaired surrogate code points as having 3
 UTF-8 bytes (the UTF-8 length of the replacement character U + FFFD).

**Returns:**

* The number of bytes needed to encode the given string in UTF-8, or
 -1 if the string contains an unpaired surrogate code point and <code>
 replace</code> is false.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

### CodePointBefore
    public static int CodePointBefore​(java.lang.String str, int index)
Gets the Unicode code point just before the given index of the string.

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

* <code>index</code> - Index of the current position into the string.

**Returns:**

* The Unicode code point at the previous position. Returns -1 if
 <code>index</code> is 0 or less, or is greater than the string's length.
 Returns the replacement character (U + FFFD) if the previous character
 is an unpaired surrogate code point. If the return value is 65536
 (0x10000) or greater, the code point takes up two UTF-16 code units.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

### CodePointBefore
    public static int CodePointBefore​(java.lang.String str, int index, int surrogateBehavior)
Gets the Unicode code point just before the given index of the string.

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

* <code>index</code> - Index of the current position into the string.

* <code>surrogateBehavior</code> - Specifies what kind of value to return if the
 previous character is an unpaired surrogate code point: if 0, return
 the replacement character (U + FFFD); if 1, return the value of the
 surrogate code point; if neither 0 nor 1, return -1.

**Returns:**

* The Unicode code point at the previous position. Returns -1 if
 <code>index</code> is 0 or less, or is greater than the string's length.
 Returns a value as specified under <code>surrogateBehavior</code> if the
 previous character is an unpaired surrogate code point. If the return
 value is 65536 (0x10000) or greater, the code point takes up two
 UTF-16 code units.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

### CodePointAt
    public static int CodePointAt​(java.lang.String str, int index)
Gets the Unicode code point at the given index of the string.<p/><p>The
 following example shows how to iterate a text string code point by
 code point. </p> <pre>for (var i = 0;i&lt;str.length(); ++i) { int
 codePoint = DataUtilities.CodePointAt(str, i);
 Console.WriteLine("codePoint:"+codePoint); if (codePoint &gt;=
 0x10000) { i++; /* Supplementary code point */ } }</pre>

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

* <code>index</code> - Index of the current position into the string.

**Returns:**

* The Unicode code point at the given position. Returns -1 if <code>
 index</code> is less than 0, or is the string's length or greater. Returns
 the replacement character (U + FFFD) if the current character is an
 unpaired surrogate code point. If the return value is 65536 (0x10000)
 or greater, the code point takes up two UTF-16 code units.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

### CodePointAt
    public static int CodePointAt​(java.lang.String str, int index, int surrogateBehavior)
Gets the Unicode code point at the given index of the string.<p/><p>The
 following example shows how to iterate a text string code point by
 code point, terminating the loop when an unpaired surrogate is found.
 </p> <pre>for (var i = 0;i&lt;str.length(); ++i) { int codePoint =
 DataUtilities.CodePointAt(str, i, 2); if (codePoint &lt; 0) { break;
 /* Unpaired surrogate */ } Console.WriteLine("codePoint:"+codePoint);
 if (codePoint &gt;= 0x10000) { i++; /* Supplementary code point */ }
 }</pre>

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

* <code>index</code> - Index of the current position into the string.

* <code>surrogateBehavior</code> - Specifies what kind of value to return if the
 previous character is an unpaired surrogate code point: if 0, return
 the replacement character (U + FFFD); if 1, return the value of the
 surrogate code point; if neither 0 nor 1, return -1.

**Returns:**

* The Unicode code point at the current position. Returns -1 if <code>
 index</code> is less than 0, or is the string's length or greater. Returns
 a value as specified under <code>surrogateBehavior</code> if the previous
 character is an unpaired surrogate code point. If the return value is
 65536 (0x10000) or greater, the code point takes up two UTF-16 code
 units.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null.

### ToLowerCaseAscii
    public static java.lang.String ToLowerCaseAscii​(java.lang.String str)
Returns a string with the basic upper-case letters A to Z (U + 0041 to U + 005A)
 converted to lower-case. Other characters remain unchanged.

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

**Returns:**

* The converted string, or null if <code>str</code> is null.

### ToUpperCaseAscii
    public static java.lang.String ToUpperCaseAscii​(java.lang.String str)
Returns a string with the basic lower-case letters A to Z (U + 0061 to U + 007A)
 converted to upper-case. Other characters remain unchanged.

**Parameters:**

* <code>str</code> - The parameter <code>str</code> is a text string.

**Returns:**

* The converted string, or null if <code>str</code> is null.

### CodePointCompare
    public static int CodePointCompare​(java.lang.String strA, java.lang.String strB)
Compares two strings in Unicode code point order. Unpaired surrogate code
 points are treated as individual code points.

**Parameters:**

* <code>strA</code> - The first string. Can be null.

* <code>strB</code> - The second string. Can be null.

**Returns:**

* A value indicating which string is " less" or " greater" . 0: Both
 strings are equal or null. Less than 0: a is null and b isn't; or the
 first code point that's different is less in A than in B; or b starts
 with a and is longer than a. Greater than 0: b is null and a isn't;
 or the first code point that's different is greater in A than in B;
 or a starts with b and is longer than b.

### WriteUtf8
    public static int WriteUtf8​(java.lang.String str, int offset, int length, java.io.OutputStream stream, boolean replace) throws java.io.IOException
Writes a portion of a string in UTF-8 encoding to a data stream.

**Parameters:**

* <code>str</code> - A string to write.

* <code>offset</code> - The zero-based index where the string portion to write begins.

* <code>length</code> - The length of the string portion to write.

* <code>stream</code> - A writable data stream.

* <code>replace</code> - If true, replaces unpaired surrogate code points with the
 replacement character (U + FFFD). If false, stops processing when an
 unpaired surrogate code point is seen.

**Returns:**

* 0 if the entire string portion was written; or -1 if the string
 portion contains an unpaired surrogate code point and <code>replace</code>
 is false.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null or
 <code>stream</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The parameter <code>offset</code> is less than
 0, <code>length</code> is less than 0, or <code>offset</code> plus <code>
 length</code> is greater than the string's length.

* <code>java.io.IOException</code> - An I/O error occurred.

### WriteUtf8
    public static int WriteUtf8​(java.lang.String str, int offset, int length, java.io.OutputStream stream, boolean replace, boolean lenientLineBreaks) throws java.io.IOException
Writes a portion of a string in UTF-8 encoding to a data stream.

**Parameters:**

* <code>str</code> - A string to write.

* <code>offset</code> - The zero-based index where the string portion to write begins.

* <code>length</code> - The length of the string portion to write.

* <code>stream</code> - A writable data stream.

* <code>replace</code> - If true, replaces unpaired surrogate code points with the
 replacement character (U + FFFD). If false, stops processing when an
 unpaired surrogate code point is seen.

* <code>lenientLineBreaks</code> - If true, replaces carriage return (CR) not followed
 by line feed (LF) and LF not preceded by CR with CR-LF pairs.

**Returns:**

* 0 if the entire string portion was written; or -1 if the string
 portion contains an unpaired surrogate code point and <code>replace</code>
 is false.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null or
 <code>stream</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The parameter <code>offset</code> is less than
 0, <code>length</code> is less than 0, or <code>offset</code> plus <code>
 length</code> is greater than the string's length.

* <code>java.io.IOException</code> - An I/O error occurred.

### WriteUtf8
    public static int WriteUtf8​(java.lang.String str, java.io.OutputStream stream, boolean replace) throws java.io.IOException
Writes a string in UTF-8 encoding to a data stream.

**Parameters:**

* <code>str</code> - A string to write.

* <code>stream</code> - A writable data stream.

* <code>replace</code> - If true, replaces unpaired surrogate code points with the
 replacement character (U + FFFD). If false, stops processing when an
 unpaired surrogate code point is seen.

**Returns:**

* 0 if the entire string was written; or -1 if the string contains an
 unpaired surrogate code point and <code>replace</code> is false.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>str</code> is null or
 <code>stream</code> is null.

* <code>java.io.IOException</code> - An I/O error occurred.

### ReadUtf8FromBytes
    public static int ReadUtf8FromBytes​(byte[] data, int offset, int bytesCount, java.lang.StringBuilder builder, boolean replace)
Reads a string in UTF-8 encoding from a byte array.

**Parameters:**

* <code>data</code> - A byte array containing a UTF-8 text string.

* <code>offset</code> - Offset into the byte array to start reading.

* <code>bytesCount</code> - Length, in bytes, of the UTF-8 text string.

* <code>builder</code> - A string builder object where the resulting string will be
 stored.

* <code>replace</code> - If true, replaces invalid encoding with the replacement
 character (U + FFFD). If false, stops processing when invalid UTF-8 is
 seen.

**Returns:**

* 0 if the entire string was read without errors, or -1 if the string
 is not valid UTF-8 and <code>replace</code> is false.

**Throws:**

* <code>java.lang.NullPointerException</code> - The parameter <code>data</code> is null or
 <code>builder</code> is null.

* <code>java.lang.IllegalArgumentException</code> - The parameter <code>offset</code> is less than
 0, <code>bytesCount</code> is less than 0, or offset plus bytesCount is
 greater than the length of <code>data</code> .

### ReadUtf8ToString
    public static java.lang.String ReadUtf8ToString​(java.io.InputStream stream) throws java.io.IOException
Reads a string in UTF-8 encoding from a data stream in full and returns that
 string. Replaces invalid encoding with the replacement character
 (U + FFFD).

**Parameters:**

* <code>stream</code> - A readable data stream.

**Returns:**

* The string read.

**Throws:**

* <code>java.io.IOException</code> - An I/O error occurred.

* <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

### ReadUtf8ToString
    public static java.lang.String ReadUtf8ToString​(java.io.InputStream stream, int bytesCount, boolean replace) throws java.io.IOException
Reads a string in UTF-8 encoding from a data stream and returns that string.

**Parameters:**

* <code>stream</code> - A readable data stream.

* <code>bytesCount</code> - The length, in bytes, of the string. If this is less than
 0, this function will read until the end of the stream.

* <code>replace</code> - If true, replaces invalid encoding with the replacement
 character (U + FFFD). If false, throws an error if an unpaired
 surrogate code point is seen.

**Returns:**

* The string read.

**Throws:**

* <code>java.io.IOException</code> - An I/O error occurred; or, the string is not
 valid UTF-8 and <code>replace</code> is false.

* <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null.

### ReadUtf8
    public static int ReadUtf8​(java.io.InputStream stream, int bytesCount, java.lang.StringBuilder builder, boolean replace) throws java.io.IOException
Reads a string in UTF-8 encoding from a data stream.

**Parameters:**

* <code>stream</code> - A readable data stream.

* <code>bytesCount</code> - The length, in bytes, of the string. If this is less than
 0, this function will read until the end of the stream.

* <code>builder</code> - A string builder object where the resulting string will be
 stored.

* <code>replace</code> - If true, replaces invalid encoding with the replacement
 character (U + FFFD). If false, stops processing when an unpaired
 surrogate code point is seen.

**Returns:**

* 0 if the entire string was read without errors, -1 if the string is
 not valid UTF-8 and <code>replace</code> is false, or -2 if the end of the
 stream was reached before the last character was read completely
 (which is only the case if <code>bytesCount</code> is 0 or greater).

**Throws:**

* <code>java.io.IOException</code> - An I/O error occurred.

* <code>java.lang.NullPointerException</code> - The parameter <code>stream</code> is null or
 <code>builder</code> is null.
