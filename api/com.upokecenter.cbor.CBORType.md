# com.upokecenter.cbor.CBORType

    public enum CBORType extends Enum<CBORType>

## Nested Classes

## Enum Constants

* `Array `<br>
  
* `Boolean `<br>
  
* `ByteString `<br>
  
* `FloatingPoint `<br>
  
* `Integer `<br>
  
* `Map `<br>
  
* `Number `<br>
 Deprecated.
Since version 4.0, CBORObject.Type no longer returns this value for any
 CBOR Object - this is a breaking change from earlier
 versions.

* `SimpleValue `<br>
  
* `TextString `<br>
  

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
