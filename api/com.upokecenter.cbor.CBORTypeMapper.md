# com.upokecenter.cbor.CBORTypeMapper

    public final class CBORTypeMapper extends Object

## Constructors

## Methods

* `<T> CBORTypeMapper AddConverter(Type type,
 ICBORConverter<T> converter)`<br>
  
* `CBORTypeMapper AddTypeName(String name)`<br>
  
* `CBORTypeMapper AddTypePrefix(String prefix)`<br>
  
* `boolean FilterTypeName(String typeName)`<br>
  

## Method Details

### AddConverter
    public <T> CBORTypeMapper AddConverter(Type type, ICBORConverter<T> converter)
### FilterTypeName
    public boolean FilterTypeName(String typeName)
### AddTypePrefix
    public CBORTypeMapper AddTypePrefix(String prefix)
### AddTypeName
    public CBORTypeMapper AddTypeName(String name)
