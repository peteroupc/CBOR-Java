# com.upokecenter.cbor.CBORNumber

    public final class CBORNumber extends Object implements Comparable<CBORNumber>

## Nested Classes

* `static enum  CBORNumber.NumberKind`<br>
  

## Methods

* `CBORNumber Abs()`<br>
  
* `CBORNumber Add(CBORNumber b)`<br>
  
* `boolean CanFitInDouble()`<br>
  
* `boolean CanFitInInt32()`<br>
  
* `boolean CanFitInInt64()`<br>
  
* `boolean CanFitInSingle()`<br>
  
* `boolean CanFitInUInt64()`<br>
  
* `boolean CanTruncatedIntFitInInt32()`<br>
  
* `boolean CanTruncatedIntFitInInt64()`<br>
  
* `boolean CanTruncatedIntFitInUInt64()`<br>
  
* `int compareTo(int other)`<br>
  
* `int compareTo(long other)`<br>
  
* `int compareTo(CBORNumber other)`<br>
  
* `CBORNumber Divide(CBORNumber b)`<br>
  
* `static CBORNumber FromByte(byte inputByte)`<br>
  
* `static CBORNumber FromCBORObject(CBORObject o)`<br>
  
* `static CBORNumber FromInt16(short inputInt16)`<br>
  
* `final CBORNumber.NumberKind getKind()`<br>
  
* `boolean IsFinite()`<br>
  
* `boolean IsInfinity()`<br>
  
* `boolean IsInteger()`<br>
  
* `boolean IsNaN()`<br>
  
* `boolean IsNegative()`<br>
  
* `boolean IsNegativeInfinity()`<br>
  
* `boolean IsPositiveInfinity()`<br>
  
* `boolean IsZero()`<br>
  
* `CBORNumber Multiply(CBORNumber b)`<br>
  
* `CBORNumber Negate()`<br>
  
* `CBORNumber Remainder(CBORNumber b)`<br>
  
* `final int signum()`<br>
  
* `CBORNumber Subtract(CBORNumber b)`<br>
  
* `byte ToByteChecked()`<br>
  
* `byte ToByteIfExact()`<br>
  
* `byte ToByteUnchecked()`<br>
  
* `CBORObject ToCBORObject()`<br>
  
* `com.upokecenter.numbers.EDecimal ToEDecimal()`<br>
  
* `com.upokecenter.numbers.EFloat ToEFloat()`<br>
  
* `com.upokecenter.numbers.EInteger ToEInteger()`<br>
  
* `com.upokecenter.numbers.EInteger ToEIntegerIfExact()`<br>
  
* `com.upokecenter.numbers.ERational ToERational()`<br>
  
* `short ToInt16Checked()`<br>
  
* `short ToInt16IfExact()`<br>
  
* `short ToInt16Unchecked()`<br>
  
* `int ToInt32Checked()`<br>
  
* `int ToInt32IfExact()`<br>
  
* `int ToInt32Unchecked()`<br>
  
* `long ToInt64Checked()`<br>
  
* `long ToInt64IfExact()`<br>
  
* `long ToInt64Unchecked()`<br>
  
* `String toString()`<br>
  

## Method Details

### ToCBORObject
    public CBORObject ToCBORObject()
### signum
    public final int signum()
### FromCBORObject
    public static CBORNumber FromCBORObject(CBORObject o)
### getKind
    public final CBORNumber.NumberKind getKind()
### CanTruncatedIntFitInInt32
    public boolean CanTruncatedIntFitInInt32()
### CanTruncatedIntFitInInt64
    public boolean CanTruncatedIntFitInInt64()
### CanTruncatedIntFitInUInt64
    public boolean CanTruncatedIntFitInUInt64()
### CanFitInSingle
    public boolean CanFitInSingle()
### CanFitInDouble
    public boolean CanFitInDouble()
### IsFinite
    public boolean IsFinite()
### IsInteger
    public boolean IsInteger()
### IsNegative
    public boolean IsNegative()
### IsZero
    public boolean IsZero()
### ToEInteger
    public com.upokecenter.numbers.EInteger ToEInteger()
### ToEIntegerIfExact
    public com.upokecenter.numbers.EInteger ToEIntegerIfExact()
### ToByteChecked
    public byte ToByteChecked()
### ToByteUnchecked
    public byte ToByteUnchecked()
### ToByteIfExact
    public byte ToByteIfExact()
### FromByte
    public static CBORNumber FromByte(byte inputByte)
### ToInt16Checked
    public short ToInt16Checked()
### ToInt16Unchecked
    public short ToInt16Unchecked()
### ToInt16IfExact
    public short ToInt16IfExact()
### FromInt16
    public static CBORNumber FromInt16(short inputInt16)
### ToInt32Checked
    public int ToInt32Checked()
### ToInt32Unchecked
    public int ToInt32Unchecked()
### ToInt32IfExact
    public int ToInt32IfExact()
### ToInt64Checked
    public long ToInt64Checked()
### ToInt64Unchecked
    public long ToInt64Unchecked()
### ToInt64IfExact
    public long ToInt64IfExact()
### toString
    public String toString()

**Overrides:**

* <code>toString</code> in class <code>Object</code>

### CanFitInInt32
    public boolean CanFitInInt32()
### CanFitInInt64
    public boolean CanFitInInt64()
### CanFitInUInt64
    public boolean CanFitInUInt64()
### IsInfinity
    public boolean IsInfinity()
### IsPositiveInfinity
    public boolean IsPositiveInfinity()
### IsNegativeInfinity
    public boolean IsNegativeInfinity()
### IsNaN
    public boolean IsNaN()
### ToEDecimal
    public com.upokecenter.numbers.EDecimal ToEDecimal()
### ToEFloat
    public com.upokecenter.numbers.EFloat ToEFloat()
### ToERational
    public com.upokecenter.numbers.ERational ToERational()
### Abs
    public CBORNumber Abs()
### Negate
    public CBORNumber Negate()
### Add
    public CBORNumber Add(CBORNumber b)
### Subtract
    public CBORNumber Subtract(CBORNumber b)
### Multiply
    public CBORNumber Multiply(CBORNumber b)
### Divide
    public CBORNumber Divide(CBORNumber b)
### Remainder
    public CBORNumber Remainder(CBORNumber b)
### compareTo
    public int compareTo(int other)
### compareTo
    public int compareTo(long other)
### compareTo
    public int compareTo(CBORNumber other)

**Specified by:**

* <code>compareTo</code> in interface <code>Comparable&lt;CBORNumber&gt;</code>
