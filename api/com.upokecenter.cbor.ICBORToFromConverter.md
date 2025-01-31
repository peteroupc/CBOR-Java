# com.upokecenter.cbor.ICBORToFromConverter

    public interface ICBORToFromConverter<T> extends ICBORConverter<T>

Classes that implement this interface can support conversions from CBOR
 objects to a custom type and back.

## Methods

* `T FromCBORObject(CBORObject obj)`<br>
 Converts a CBOR object to a custom type.

## Method Details

### FromCBORObject

    T FromCBORObject(CBORObject obj)

Converts a CBOR object to a custom type.

**Parameters:**

* <code>obj</code> - A CBOR object to convert to the custom type.

**Returns:**

* An object of the custom type after conversion.
