# com.upokecenter.cbor.CBORType

    public enum CBORType extends java.lang.Enum<CBORType>

Represents a type that a CBOR object can have.

## Enum Constants

* `Array`<br>
 An array of CBOR objects.
* `Boolean`<br>
 The simple values true and false.
* `ByteString`<br>
 An array of bytes.
* `FloatingPoint`<br>
 A 16-, 32-, or 64-bit binary floating-point number.
* `Integer`<br>
 An integer in the interval [-(2^64), 2^64 - 1], or an integer of major type
 0 and 1.
* `Map`<br>
 A map of CBOR objects.
* `Number`<br>
 Deprecated.
Use the IsNumber property of CBORObject to determine whether a CBOR Object
 represents a number, or use the two new CBORType values instead.
 Use the IsNumber property of CBORObject to determine whether a CBOR Object
 represents a number, or use the two new CBORType values instead.
* `SimpleValue`<br>
 A "simple value" other than floating point values, true, and false.
* `TextString`<br>
 A text string.

## Methods

* `static CBORType valueOf​(java.lang.String name)`<br>
 Returns the enum constant of this type with the specified name.
* `static CBORType[] values()`<br>
 Returns an array containing the constants of this enum type, in
the order they are declared.

## Method Details

### Number
    @Deprecated public static final CBORType Number
### Boolean
    public static final CBORType Boolean
### SimpleValue
    public static final CBORType SimpleValue
### ByteString
    public static final CBORType ByteString
### TextString
    public static final CBORType TextString
### Array
    public static final CBORType Array
### Map
    public static final CBORType Map
### Integer
    public static final CBORType Integer
### FloatingPoint
    public static final CBORType FloatingPoint
### values
    public static CBORType[] values()
### valueOf
    public static CBORType valueOf​(java.lang.String name)
## Enum Constant Details

### Number
    @Deprecated public static final CBORType Number
Deprecated.
Use the IsNumber property of CBORObject to determine whether a CBOR Object
 represents a number, or use the two new CBORType values instead.
 CBORType.Integer covers CBOR objects representing integers of major type
 0 and 1. CBORType.FloatingPoint covers CBOR objects representing 16-,
 32-, and 64-bit floating-point numbers.

### Boolean
    public static final CBORType Boolean
The simple values true and false.
### SimpleValue
    public static final CBORType SimpleValue
A "simple value" other than floating point values, true, and false.
### ByteString
    public static final CBORType ByteString
An array of bytes.
### TextString
    public static final CBORType TextString
A text string.
### Array
    public static final CBORType Array
An array of CBOR objects.
### Map
    public static final CBORType Map
A map of CBOR objects.
### Integer
    public static final CBORType Integer
An integer in the interval [-(2^64), 2^64 - 1], or an integer of major type
 0 and 1.
### FloatingPoint
    public static final CBORType FloatingPoint
A 16-, 32-, or 64-bit binary floating-point number.
