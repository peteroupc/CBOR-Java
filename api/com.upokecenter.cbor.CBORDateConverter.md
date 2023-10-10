# com.upokecenter.cbor.CBORDateConverter

    public final class CBORDateConverter extends Object implements ICBORToFromConverter<Date>

## Nested Classes

* `static enum  CBORDateConverter.ConversionType`<br>
  

## Fields

* `static final CBORDateConverter TaggedNumber`<br>
  
* `static final CBORDateConverter TaggedString`<br>
  
* `static final CBORDateConverter UntaggedNumber`<br>
  

## Constructors

## Methods

* `CBORObject DateTimeFieldsToCBORObject(int year,
 int[] lesserFields)`<br>
  
* `CBORObject DateTimeFieldsToCBORObject(int smallYear,
 int month,
 int day)`<br>
  
* `CBORObject DateTimeFieldsToCBORObject(int smallYear,
 int month,
 int day,
 int hour,
 int minute,
 int second)`<br>
  
* `CBORObject DateTimeFieldsToCBORObject(com.upokecenter.numbers.EInteger bigYear,
 int[] lesserFields)`<br>
  
* `Date FromCBORObject(CBORObject obj)`<br>
  
* `final CBORDateConverter.ConversionType getType()`<br>
  
* `CBORObject ToCBORObject(Date obj)`<br>
  
* `boolean TryGetDateTimeFields(CBORObject obj,
 com.upokecenter.numbers.EInteger[] year,
 int[] lesserFields)`<br>
  

## Field Details

### TaggedString
    public static final CBORDateConverter TaggedString
### TaggedNumber
    public static final CBORDateConverter TaggedNumber
### UntaggedNumber
    public static final CBORDateConverter UntaggedNumber
## Method Details

### getType
    public final CBORDateConverter.ConversionType getType()
### FromCBORObject
    public Date FromCBORObject(CBORObject obj)

**Specified by:**

* <code>FromCBORObject</code> in interface <code>ICBORToFromConverter&lt;Date&gt;</code>

### TryGetDateTimeFields
    public boolean TryGetDateTimeFields(CBORObject obj, com.upokecenter.numbers.EInteger[] year, int[] lesserFields)
### DateTimeFieldsToCBORObject
    public CBORObject DateTimeFieldsToCBORObject(int smallYear, int month, int day)
### DateTimeFieldsToCBORObject
    public CBORObject DateTimeFieldsToCBORObject(int smallYear, int month, int day, int hour, int minute, int second)
### DateTimeFieldsToCBORObject
    public CBORObject DateTimeFieldsToCBORObject(int year, int[] lesserFields)
### DateTimeFieldsToCBORObject
    public CBORObject DateTimeFieldsToCBORObject(com.upokecenter.numbers.EInteger bigYear, int[] lesserFields)
### ToCBORObject
    public CBORObject ToCBORObject(Date obj)

**Specified by:**

* <code>ToCBORObject</code> in interface <code>ICBORConverter&lt;Date&gt;</code>
