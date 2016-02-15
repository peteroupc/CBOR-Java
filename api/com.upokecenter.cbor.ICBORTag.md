# com.upokecenter.cbor.ICBORTag

    public interface ICBORTag

Implemented by classes that validate CBOR objects belonging to a specific
 tag.

## Methods

* `CBORTypeFilter GetTypeFilter()`<br>
 Gets a type filter specifying what kinds of CBOR objects are supported by
 this tag.
* `CBORObject ValidateObject(CBORObject obj)`<br>
 Generates a CBOR object based on the data of another object.

## Method Details

### GetTypeFilter
    CBORTypeFilter GetTypeFilter()
Gets a type filter specifying what kinds of CBOR objects are supported by
 this tag.

**Returns:**

* A CBOR type filter.

### ValidateObject
    CBORObject ValidateObject(CBORObject obj)
Generates a CBOR object based on the data of another object. If the data is
 not valid, should throw a CBORException.

**Parameters:**

* <code>obj</code> - A CBOR object with the corresponding tag handled by the ICBORTag
 object.

**Returns:**

* A CBORObject object. Note that this method may choose to return the
 same object as the parameter.
