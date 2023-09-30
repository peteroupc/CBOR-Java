# com.upokecenter.cbor.CBORType

    public enum CBORType extends Enum<CBORType>

Represents a type that a CBOR object can have.

## Nested Classes

## Enum Constants

* `Array `<br>
 An array of CBOR objects.

* `Boolean `<br>
 The simple values true and false.

* `ByteString `<br>
 An array of bytes.

* `FloatingPoint `<br>
 A 16-, 32-, or 64-bit binary floating-point number.

* `Integer `<br>
 An integer in the interval [-(2^64), 2^64 - 1], or an integer of major type
 0 and 1.

* `Map `<br>
 A map of CBOR objects.

* `Number `<br>
 Deprecated.
Since version 4.0, CBORObject.Type no longer returns this value for any
 CBOR Object - this is a breaking change from earlier
 versions.

* `SimpleValue `<br>
 A "simple value" other than floating point values, true, and false.

* `TextString `<br>
 A text string.

## Methods

* `static CBORType valueOf(String name)`<br>
 Returns the enum constant of this class with the specified name.

* `static CBORType[] values()`<br>
 Returns an array containing the constants of this enum class, in
the order they are declared.

## Method Details

### values
    public static CBORType[] values()
### valueOf
    public static CBORType valueOf(String name)
