# com.upokecenter.cbor.JSONOptions.ConversionMode

    public static enum JSONOptions.ConversionMode extends java.lang.Enum<JSONOptions.ConversionMode>

Specifies how JSON numbers are converted to CBOR objects when decoding JSON
(such as via <code>FromJSONString</code> or <code>ReadJSON</code>). None of
these conversion modes affects how CBOR objects are later encoded
(such as via <code>EncodeToBytes</code>).

## Enum Constants

- `Decimal128 CBORObject.FromObject(EDecimal)`<br>
  JSON numbers are decoded to CBOR as their closest-rounded approximation to
  an IEEE 854 decimal128 value, using the
  round-to-nearest/ties-to-even rounding mode and the rules for the
  EDecimal form of that approximation as given in the
  CBORObject.FromObject(EDecimal) method.
- `Double`<br>
  JSON numbers are decoded to CBOR as their closest-rounded approximation as
  64-bit binary floating-point numbers (using the
  round-to-nearest/ties-to-even rounding mode).
- `Full`<br>
  JSON numbers are decoded to CBOR using the full precision given in the JSON
  text.
- `IntOrFloat`<br>
  A JSON number is decoded to CBOR objects either as a CBOR integer (major
  type 0 or 1) if the JSON number represents an integer at least
  -(2^53)+1 and less than 2^53, or as their closest-rounded
  approximation as 64-bit binary floating-point numbers (using the
  round-to-nearest/ties-to-even rounding mode) otherwise.
- `IntOrFloatFromDouble`<br>
  A JSON number is decoded to CBOR objects either as a CBOR integer (major
  type 0 or 1) if the number's closest-rounded approximation as a
  64-bit binary floating-point number (using the
  round-to-nearest/ties-to-even rounding mode) represents an
  integer at least -(2^53)+1 and less than 2^53, or as that
  approximation otherwise.

## Methods

- `static JSONOptions.ConversionMode valueOf​(java.lang.String name)`<br>
  Returns the enum constant of this type with the specified name.
- `static JSONOptions.ConversionMode[] values()`<br>
  Returns an array containing the constants of this enum type, in
  the order they are declared.

## Method Details

### Full

    public static final JSONOptions.ConversionMode Full

### Double

    public static final JSONOptions.ConversionMode Double

### IntOrFloat

    public static final JSONOptions.ConversionMode IntOrFloat

### IntOrFloatFromDouble

    public static final JSONOptions.ConversionMode IntOrFloatFromDouble

### Decimal128

    public static final JSONOptions.ConversionMode Decimal128

### values

    public static JSONOptions.ConversionMode[] values()

### valueOf

    public static JSONOptions.ConversionMode valueOf​(java.lang.String name)

## Enum Constant Details

### Full

    public static final JSONOptions.ConversionMode Full

JSON numbers are decoded to CBOR using the full precision given in the JSON
text. The number will be converted to a CBOR object as follows:
If the number's exponent is 0 (after shifting the decimal point
to the end of the number without changing its value), use the
rules given in the <code>CBORObject.FromObject(EInteger)</code> method;
otherwise, use the rules given in the
<code>CBORObject.FromObject(EDecimal)</code> method. An exception in
version 4.x involves negative zeros; if the negative zero's
exponent is 0, it's written as a CBOR floating-point number;
otherwise the negative zero is written as an EDecimal.

### Double

    public static final JSONOptions.ConversionMode Double

JSON numbers are decoded to CBOR as their closest-rounded approximation as
64-bit binary floating-point numbers (using the
round-to-nearest/ties-to-even rounding mode). (In some cases,
numbers extremely close to zero may underflow to positive or
negative zero, and numbers of extremely large absolute value may
overflow to infinity.). It's important to note that this mode
affects only how JSON numbers are <i>decoded</i> to a CBOR
object; it doesn't affect how <code>EncodeToBytes</code> and other
methods encode CBOR objects. Notably, by default,
<code>EncodeToBytes</code> encodes CBOR floating-point values to the
CBOR format in their 16-bit ("half-float"), 32-bit
("single-precision"), or 64-bit ("double-precision") encoding
form depending on the value.

### IntOrFloat

    public static final JSONOptions.ConversionMode IntOrFloat

A JSON number is decoded to CBOR objects either as a CBOR integer (major
type 0 or 1) if the JSON number represents an integer at least
-(2^53)+1 and less than 2^53, or as their closest-rounded
approximation as 64-bit binary floating-point numbers (using the
round-to-nearest/ties-to-even rounding mode) otherwise. For
example, the JSON number 0.99999999999999999999999999999999999 is
not an integer, so it's converted to its closest 64-bit binary
floating-point approximation, namely 1.0. (In some cases, numbers
extremely close to zero may underflow to positive or negative
zero, and numbers of extremely large absolute value may overflow
to infinity.). It's important to note that this mode affects only
how JSON numbers are <i>decoded</i> to a CBOR object; it doesn't
affect how <code>EncodeToBytes</code> and other methods encode CBOR
objects. Notably, by default, <code>EncodeToBytes</code> encodes CBOR
floating-point values to the CBOR format in their 16-bit
("half-float"), 32-bit ("single-precision"), or 64-bit
("double-precision") encoding form depending on the value.

### IntOrFloatFromDouble

    public static final JSONOptions.ConversionMode IntOrFloatFromDouble

A JSON number is decoded to CBOR objects either as a CBOR integer (major
type 0 or 1) if the number's closest-rounded approximation as a
64-bit binary floating-point number (using the
round-to-nearest/ties-to-even rounding mode) represents an
integer at least -(2^53)+1 and less than 2^53, or as that
approximation otherwise. For example, the JSON number
0.99999999999999999999999999999999999 is the integer 1 when
rounded to its closest 64-bit binary floating-point approximation
(1.0), so it's converted to the CBOR integer 1 (major type 0).
(In some cases, numbers extremely close to zero may underflow to
zero, and numbers of extremely large absolute value may overflow
to infinity.). It's important to note that this mode affects only
how JSON numbers are <i>decoded</i> to a CBOR object; it doesn't
affect how <code>EncodeToBytes</code> and other methods encode CBOR
objects. Notably, by default, <code>EncodeToBytes</code> encodes CBOR
floating-point values to the CBOR format in their 16-bit
("half-float"), 32-bit ("single-precision"), or 64-bit
("double-precision") encoding form depending on the value.

### Decimal128

    public static final JSONOptions.ConversionMode Decimal128

JSON numbers are decoded to CBOR as their closest-rounded approximation to
an IEEE 854 decimal128 value, using the
round-to-nearest/ties-to-even rounding mode and the rules for the
EDecimal form of that approximation as given in the
<code>CBORObject.FromObject(EDecimal)</code> method. (In some cases,
numbers extremely close to zero may underflow to zero, and
numbers of extremely large absolute value may overflow to
infinity.).
