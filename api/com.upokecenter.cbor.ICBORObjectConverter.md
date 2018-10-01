# com.upokecenter.cbor.ICBORObjectConverter

    public interface ICBORObjectConverter<T> extends ICBORConverter<T>

Interface implemented by classes that convert objects of arbitrary types to
 and from CBOR objects.

## Methods

* `T FromCBORObject​(CBORObject cbor)`<br>
 Converts a CBOR object to an object of a type supported by the implementing
 class.

## Method Details

### FromCBORObject
    T FromCBORObject​(CBORObject cbor)
Converts a CBOR object to an object of a type supported by the implementing
 class.

**Parameters:**

* <code>cbor</code> - A CBOR object to convert.

**Returns:**

* The converted object.

**Throws:**

* <code>CBORException</code> - An error occurred in the
 conversion; for example, the conversion doesn't support the given
 CBOR object.

### FromCBORObject
    T FromCBORObject​(CBORObject cbor)
Converts a CBOR object to an object of a type supported by the implementing
 class.

**Parameters:**

* <code>cbor</code> - A CBOR object to convert.

**Returns:**

* The converted object.

**Throws:**

* <code>CBORException</code> - An error occurred in the
 conversion; for example, the conversion doesn't support the given
 CBOR object.
