# com.upokecenter.cbor.JSONOptions.ConversionMode

    public static enum JSONOptions.ConversionMode extends Enum<JSONOptions.ConversionMode>

Specifies how JSON numbers are converted to CBOR objects when decoding JSON
 (such as via <code>FromJSONString</code> or <code>ReadJSON</code>). None of these
 conversion modes affects how CBOR objects are later encoded (such as via
 <code>EncodeToBytes</code>).

## Nested Classes

## Enum Constants

* `Decimal128 `<br>
 JSON numbers are decoded to CBOR as their closest-rounded approximation to
 an IEEE 854 decimal128 value, using the round-to-nearest/ties-to-even
 rounding mode and the rules for the EDecimal form of that approximation as
 given in the CBORObject.FromObject(EDecimal) method.

* `Double `<br>
 JSON numbers are decoded to CBOR as their closest-rounded approximation
 as 64-bit binary floating-point numbers (using the
 round-to-nearest/ties-to-even rounding mode).

* `Full `<br>
 JSON numbers are decoded to CBOR using the full precision given in the JSON
 text.

* `IntOrFloat `<br>
 A JSON number is decoded to CBOR objects either as a CBOR integer (major
 type 0 or 1) if the JSON number represents an integer at least -(2^53)+1 and
 less than 2^53, or as their closest-rounded approximation as 64-bit binary
 floating-point numbers (using the round-to-nearest/ties-to-even rounding
 mode) otherwise.

* `IntOrFloatFromDouble `<br>
 A JSON number is decoded to CBOR objects either as a CBOR integer (major
 type 0 or 1) if the number's closest-rounded approximation as a 64-bit
 binary floating-point number (using the round-to-nearest/ties-to-even
 rounding mode) represents an integer at least -(2^53)+1 and less than 2^53,
 or as that approximation otherwise.

## Methods

* `static JSONOptions.ConversionMode valueOf(String name)`<br>
 Returns the enum constant of this class with the specified name.

* `static JSONOptions.ConversionMode[] values()`<br>
 Returns an array containing the constants of this enum class, in
the order they are declared.

## Method Details

### values

    public static JSONOptions.ConversionMode[] values()

### valueOf

    public static JSONOptions.ConversionMode valueOf(String name)
