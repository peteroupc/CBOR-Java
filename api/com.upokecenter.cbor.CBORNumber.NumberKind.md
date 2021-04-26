# com.upokecenter.cbor.CBORNumber.NumberKind

    public static enum CBORNumber.NumberKind extends java.lang.Enum<CBORNumber.NumberKind>

Specifies the underlying form of this CBOR number object.

## Enum Constants

* `Double`<br>
 A 64-bit binary floating-point number.
* `EDecimal`<br>
 An arbitrary-precision decimal number.
* `EFloat`<br>
 An arbitrary-precision binary number.
* `EInteger`<br>
 An arbitrary-precision integer.
* `ERational`<br>
 An arbitrary-precision rational number.
* `Integer`<br>
 A 64-bit signed integer.

## Methods

* `static CBORNumber.NumberKind valueOf​(java.lang.String name)`<br>
 Returns the enum constant of this type with the specified name.
* `static CBORNumber.NumberKind[] values()`<br>
 Returns an array containing the constants of this enum type, in
the order they are declared.

## Method Details

### Integer
    public static final CBORNumber.NumberKind Integer
### Double
    public static final CBORNumber.NumberKind Double
### EInteger
    public static final CBORNumber.NumberKind EInteger
### EDecimal
    public static final CBORNumber.NumberKind EDecimal
### EFloat
    public static final CBORNumber.NumberKind EFloat
### ERational
    public static final CBORNumber.NumberKind ERational
### values
    public static CBORNumber.NumberKind[] values()
### valueOf
    public static CBORNumber.NumberKind valueOf​(java.lang.String name)
## Enum Constant Details

### Integer
    public static final CBORNumber.NumberKind Integer
A 64-bit signed integer.
### Double
    public static final CBORNumber.NumberKind Double
A 64-bit binary floating-point number.
### EInteger
    public static final CBORNumber.NumberKind EInteger
An arbitrary-precision integer.
### EDecimal
    public static final CBORNumber.NumberKind EDecimal
An arbitrary-precision decimal number.
### EFloat
    public static final CBORNumber.NumberKind EFloat
An arbitrary-precision binary number.
### ERational
    public static final CBORNumber.NumberKind ERational
An arbitrary-precision rational number.
