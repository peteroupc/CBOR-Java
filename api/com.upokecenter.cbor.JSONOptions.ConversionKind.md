# com.upokecenter.cbor.JSONOptions.ConversionKind

    public static enum JSONOptions.ConversionKind extends java.lang.Enum<JSONOptions.ConversionKind>

Specifies how JSON numbers are converted to CBOR when decoding JSON.

## Enum Constants

* `Double`<br>
 JSON numbers are decoded to CBOR as their closest-rounded approximation as
 64-bit binary floating-point numbers.
* `Full`<br>
 JSON numbers are decoded to CBOR using the full precision given in the JSON
 text.
* `IntOrFloat`<br>
 A JSON number is decoded to CBOR either as a CBOR integer (major type 0 or
 1) if the JSON number represents an integer at least -(2^53) and
 less than or equal to 2^53, or as their closest-rounded
 approximation as 64-bit binary floating-point numbers otherwise,
 where a JSON number is treated as an integer or non-integer based
 on the full precision given in the JSON text.
* `IntOrFloatFromDouble`<br>
 A JSON number is decoded to CBOR either as a CBOR integer (major type 0 or
 1) if the number's closest-rounded approximation as a 64-bit
 binary floating-point number represents an integer at least
 -(2^53) and less than or equal to 2^53, or as that approximation
 otherwise, where a JSON number is treated as an integer or
 non-integer based on its approximated floating-point number.

## Methods

* `static JSONOptions.ConversionKind valueOf​(java.lang.String name)`<br>
 Returns the enum constant of this type with the specified name.
* `static JSONOptions.ConversionKind[] values()`<br>
 Returns an array containing the constants of this enum type, in
the order they are declared.

## Method Details

### Full
    public static final JSONOptions.ConversionKind Full
### Double
    public static final JSONOptions.ConversionKind Double
### IntOrFloat
    public static final JSONOptions.ConversionKind IntOrFloat
### IntOrFloatFromDouble
    public static final JSONOptions.ConversionKind IntOrFloatFromDouble
### values
    public static JSONOptions.ConversionKind[] values()
### valueOf
    public static JSONOptions.ConversionKind valueOf​(java.lang.String name)
## Enum Constant Details

### Full
    public static final JSONOptions.ConversionKind Full
JSON numbers are decoded to CBOR using the full precision given in the JSON
 text. This may involve numbers being converted to
 arbitrary-precision integers or decimal numbers, where
 appropriate. The distinction between positive and negative zero
 is preserved.
### Double
    public static final JSONOptions.ConversionKind Double
JSON numbers are decoded to CBOR as their closest-rounded approximation as
 64-bit binary floating-point numbers. The distinction between
 positive and negative zero is preserved.
### IntOrFloat
    public static final JSONOptions.ConversionKind IntOrFloat
A JSON number is decoded to CBOR either as a CBOR integer (major type 0 or
 1) if the JSON number represents an integer at least -(2^53) and
 less than or equal to 2^53, or as their closest-rounded
 approximation as 64-bit binary floating-point numbers otherwise,
 where a JSON number is treated as an integer or non-integer based
 on the full precision given in the JSON text. For example, the
 JSON number 0.99999999999999999999999999999999999 is not an
 integer, so it's converted to its closest floating-point
 approximation, namely 1.0.
### IntOrFloatFromDouble
    public static final JSONOptions.ConversionKind IntOrFloatFromDouble
A JSON number is decoded to CBOR either as a CBOR integer (major type 0 or
 1) if the number's closest-rounded approximation as a 64-bit
 binary floating-point number represents an integer at least
 -(2^53) and less than or equal to 2^53, or as that approximation
 otherwise, where a JSON number is treated as an integer or
 non-integer based on its approximated floating-point number. For
 example, the JSON number 0.99999999999999999999999999999999999 is
 the integer 1 when rounded to its closest floating-point
 approximation (1.0), so it's converted to the CBOR integer 1
 (major type 0).
