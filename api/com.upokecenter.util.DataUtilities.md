# com.upokecenter.util.DataUtilities

    public final class DataUtilities extends Object

Contains methods useful for reading and writing strings. It is designed to
 have no dependencies other than the basic runtime class library.
 <p>Many of these methods work with text encoded in UTF-8, an encoding
 form of the Unicode Standard which uses one byte to encode the most
 basic characters and two to four bytes to encode other characters.
 For example, the <code>GetUtf8</code> method converts a text string to an
 array of bytes in UTF-8.</p> <p>In C# and Java, text strings are
 represented as sequences of 16-bit values called <code>char</code> s. These
 sequences are well-formed under UTF-16, a 16-bit encoding form of
 Unicode, except if they contain unpaired surrogate code points. (A
 surrogate code point is used to encode supplementary characters,
 those with code points U + 10000 or higher, in UTF-16. A surrogate pair
 is a high surrogate [U + D800 to U + DBFF] followed by a low surrogate
 [U + DC00 to U + DFFF]. An unpaired surrogate code point is a surrogate
 not appearing in a surrogate pair.) Many of the methods in this class
 allow setting the behavior to follow when unpaired surrogate code
 points are found in text strings, such as throwing an error or
 treating the unpaired surrogate as a replacement character
 (U + FFFD).</p>
