# com.upokecenter.cbor.ICBORTag

    @Deprecated public interface ICBORTag

Deprecated.
<div class='deprecationComment'>May be removed in the future without replacement. Not as useful as
 ICBORConverters and ICBORObjectConverters for FromObject and ToObject.</div>

## Methods

* `CBORTypeFilter GetTypeFilter()`<br>
 Deprecated. Gets a type filter specifying what kinds of CBOR objects are supported by
 this tag.
* `CBORObject ValidateObject​(CBORObject obj)`<br>
 Deprecated. Generates a CBOR object based on the data of another object.

## Method Details

### GetTypeFilter
    CBORTypeFilter GetTypeFilter()
Deprecated.

**Returns:**

* A CBOR type filter.

### ValidateObject
    CBORObject ValidateObject​(CBORObject obj)
Deprecated.

**Parameters:**

* <code>obj</code> - A CBOR object with the corresponding tag handled by the ICBORTag
 object.

**Returns:**

* A CBORObject object. Note that this method may choose to return the
 same object as the parameter.
