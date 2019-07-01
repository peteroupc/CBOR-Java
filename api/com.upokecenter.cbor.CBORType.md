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
* `Map`<br>
 A map of CBOR objects.
* `Number`<br>
 A number of any kind, including integers, big integers, floating point
 numbers, and decimal numbers.
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
    public static final CBORType Number
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
### values
    public static CBORType[] values()
### valueOf
    public static CBORType valueOf​(java.lang.String name)
## Enum Constant Details

### Number
    public static final CBORType Number
A number of any kind, including integers, big integers, floating point
 numbers, and decimal numbers. The floating-point value Not-a-Number
 is also included in the Number type.
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
