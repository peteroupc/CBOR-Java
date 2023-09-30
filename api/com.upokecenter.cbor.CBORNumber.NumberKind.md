# com.upokecenter.cbor.CBORNumber.NumberKind

    public static enum CBORNumber.NumberKind extends Enum<CBORNumber.NumberKind>

Specifies the underlying form of this CBOR number object.

## Nested Classes

## Enum Constants

* `Double `<br>
 A 64-bit binary floating-point number.

* `EDecimal `<br>
 An arbitrary-precision decimal number.

* `EFloat `<br>
 An arbitrary-precision binary number.

* `EInteger `<br>
 An arbitrary-precision integer.

* `ERational `<br>
 An arbitrary-precision rational number.

* `Integer `<br>
 A 64-bit signed integer.

## Methods

* `static CBORNumber.NumberKind valueOf(String name)`<br>
 Returns the enum constant of this class with the specified name.

* `static CBORNumber.NumberKind[] values()`<br>
 Returns an array containing the constants of this enum class, in
the order they are declared.

## Method Details

### values
    public static CBORNumber.NumberKind[] values()
### valueOf
    public static CBORNumber.NumberKind valueOf(String name)
