# com.upokecenter.util.PrecisionContext

    public class PrecisionContext extends Object

<p><b>This class is largely obsolete. It will be replaced by a new version
 of this class in a different namespace/package and library, called
 <code>PeterO.Numbers.EContext</code> in the <code>PeterO.EContext</code> library
 (in .NET), or <code>com.upokecenter.numbers.EFloat</code> in the <code>com.github.peteroupc/numbers</code>
 artifact (in Java).</b></p>Contains parameters for controlling the
 precision, rounding, and exponent range of arbitrary-precision
 numbers.

## Fields

* `static PrecisionContext Basic`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext BigDecimalJava`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext Binary128`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext Binary16`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext Binary32`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext Binary64`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext CliDecimal`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext Decimal128`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext Decimal32`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext Decimal64`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagClamped`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagDivideByZero`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagInexact`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagInvalid`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagLostDigits`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagOverflow`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagRounded`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagSubnormal`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static int FlagUnderflow`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext JavaBigDecimal`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext Unlimited`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.

## Constructors

* `PrecisionContext(int precision,
                Rounding rounding,
                int exponentMinSmall,
                int exponentMaxSmall,
                boolean clampNormalExponents)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.

## Methods

* `PrecisionContext Copy()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `boolean ExponentWithinRange(BigInteger exponent)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext ForPrecision(int precision)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext ForPrecisionAndRounding(int precision,
                       Rounding rounding)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `static PrecisionContext ForRounding(Rounding rounding)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `boolean getAdjustExponent()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `boolean getClampNormalExponents()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger getEMax()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger getEMin()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `int getFlags()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `boolean getHasExponentRange()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `boolean getHasFlags()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `boolean getHasMaxPrecision()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `BigInteger getPrecision()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `Rounding getRounding()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `int getTraps()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isPrecisionInBits()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `boolean isSimplified()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `void setFlags(int value)`<br>
* `String toString()`<br>
 Gets a string representation of this object.
* `PrecisionContext WithAdjustExponent(boolean adjustExponent)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithBigExponentRange(BigInteger exponentMin,
                    BigInteger exponentMax)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithBigPrecision(BigInteger bigintPrecision)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithBlankFlags()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithExponentClamp(boolean clamp)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithExponentRange(int exponentMinSmall,
                 int exponentMaxSmall)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithNoFlags()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithPrecision(int precision)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithPrecisionInBits(boolean isPrecisionBits)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithRounding(Rounding rounding)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithSimplified(boolean simplified)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithTraps(int traps)`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.
* `PrecisionContext WithUnlimitedExponents()`<br>
 Deprecated.
Use EContext from PeterO.Numbers/com.upokecenter.numbers.
 Use EContext from PeterO.Numbers/com.upokecenter.numbers.

## Field Details

### FlagClamped
    @Deprecated public static final int FlagClamped
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### FlagDivideByZero
    @Deprecated public static final int FlagDivideByZero
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### FlagInexact
    @Deprecated public static final int FlagInexact
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### FlagInvalid
    @Deprecated public static final int FlagInvalid
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### FlagLostDigits
    @Deprecated public static final int FlagLostDigits
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### FlagOverflow
    @Deprecated public static final int FlagOverflow
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### FlagRounded
    @Deprecated public static final int FlagRounded
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### FlagSubnormal
    @Deprecated public static final int FlagSubnormal
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### FlagUnderflow
    @Deprecated public static final int FlagUnderflow
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Basic
    @Deprecated public static final PrecisionContext Basic
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### BigDecimalJava
    @Deprecated public static final PrecisionContext BigDecimalJava
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Binary128
    @Deprecated public static final PrecisionContext Binary128
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Binary16
    @Deprecated public static final PrecisionContext Binary16
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Binary32
    @Deprecated public static final PrecisionContext Binary32
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Binary64
    @Deprecated public static final PrecisionContext Binary64
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### CliDecimal
    @Deprecated public static final PrecisionContext CliDecimal
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Decimal128
    @Deprecated public static final PrecisionContext Decimal128
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Decimal32
    @Deprecated public static final PrecisionContext Decimal32
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Decimal64
    @Deprecated public static final PrecisionContext Decimal64
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### JavaBigDecimal
    @Deprecated public static final PrecisionContext JavaBigDecimal
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
### Unlimited
    @Deprecated public static final PrecisionContext Unlimited
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.
## Method Details

### getAdjustExponent
    @Deprecated public final boolean getAdjustExponent()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if the EMax and EMin properties refer to the number's
 Exponent property adjusted to the number's precision, or false if
 they refer to just the number's Exponent property.

### getClampNormalExponents
    @Deprecated public final boolean getClampNormalExponents()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* If true, a converted number's Exponent property will not be higher
 than EMax + 1 - Precision.

### getEMax
    @Deprecated public final BigInteger getEMax()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The highest exponent possible when a converted number is expressed
 in scientific notation with one digit before the decimal point. For
 example, with a precision of 3 and an EMax of 100, the maximum value
 possible is 9.99E + 100. (This is not the same as the highest
 possible Exponent property.) If HasExponentRange is false, this value
 will be 0.

### getEMin
    @Deprecated public final BigInteger getEMin()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The lowest exponent possible when a converted number is expressed in
 scientific notation with one digit before the decimal point.

### getFlags
    @Deprecated public final int getFlags()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The flags that are set from converting numbers according to this
 precision context. If HasFlags is false, this value will be 0.

### setFlags
    public final void setFlags(int value)
### getHasExponentRange
    @Deprecated public final boolean getHasExponentRange()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this context defines a minimum and maximum exponent;
 otherwise, <code>false</code>.

### getHasFlags
    @Deprecated public final boolean getHasFlags()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this context has a mutable Flags field; otherwise,
 <code>false</code>.

### getHasMaxPrecision
    @Deprecated public final boolean getHasMaxPrecision()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this context defines a maximum precision; otherwise,
 <code>false</code>.

### isPrecisionInBits
    @Deprecated public final boolean isPrecisionInBits()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if this context's Precision property is in bits, rather
 than digits; otherwise, <code>false</code>. The default is false.

### isSimplified
    @Deprecated public final boolean isSimplified()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* <code>true</code> if a "simplified" arithmetic will be used; otherwise,
 <code>false</code>.

### getPrecision
    @Deprecated public final BigInteger getPrecision()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The maximum length of a converted number in digits, ignoring the
 decimal point and exponent.

### getRounding
    @Deprecated public final Rounding getRounding()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The desired rounding mode when converting numbers that can't be
 represented in the given precision and exponent range.

### getTraps
    @Deprecated public final int getTraps()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* The traps that are set for each flag in the context.

### ForPrecision
    @Deprecated public static PrecisionContext ForPrecision(int precision)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>precision</code> - Maximum number of digits (precision).

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### ForPrecisionAndRounding
    @Deprecated public static PrecisionContext ForPrecisionAndRounding(int precision, Rounding rounding)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>precision</code> - Maximum number of digits (precision).

* <code>rounding</code> - An ERounding object.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### ForRounding
    @Deprecated public static PrecisionContext ForRounding(Rounding rounding)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>rounding</code> - The rounding mode for the new precision context.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### Copy
    @Deprecated public PrecisionContext Copy()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### ExponentWithinRange
    @Deprecated public boolean ExponentWithinRange(BigInteger exponent)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponent</code> - An arbitrary-precision integer indicating the desired
 exponent.

**Returns:**

* <code>true</code> if a number can have the given Exponent property under
 this precision context; otherwise, <code>false</code>. If this context
 allows unlimited precision, returns true for the exponent EMax and
 any exponent less than EMax.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>exponent</code> is null.

### toString
    public String toString()
Gets a string representation of this object. Note that the format is not
 intended to be parsed and may change at any time.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object.

### WithAdjustExponent
    @Deprecated public PrecisionContext WithAdjustExponent(boolean adjustExponent)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>adjustExponent</code> - The new value of the "AdjustExponent" property for the
 copy.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithBigExponentRange
    @Deprecated public PrecisionContext WithBigExponentRange(BigInteger exponentMin, BigInteger exponentMax)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponentMin</code> - Desired minimum exponent (EMin).

* <code>exponentMax</code> - Desired maximum exponent (EMax).

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>exponentMin</code> is
 null.

* <code>NullPointerException</code> - The parameter <code>exponentMax</code> is
 null.

### WithBigPrecision
    @Deprecated public PrecisionContext WithBigPrecision(BigInteger bigintPrecision)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>bigintPrecision</code> - Desired maximum number of digits a number can have.
 If 0, the number of digits is unlimited.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

**Throws:**

* <code>NullPointerException</code> - The parameter <code>bigintPrecision</code>
 is null.

### WithBlankFlags
    @Deprecated public PrecisionContext WithBlankFlags()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithExponentClamp
    @Deprecated public PrecisionContext WithExponentClamp(boolean clamp)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>clamp</code> - Desired value for ClampNormalExponents.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithExponentRange
    @Deprecated public PrecisionContext WithExponentRange(int exponentMinSmall, int exponentMaxSmall)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>exponentMinSmall</code> - Desired minimum exponent (EMin).

* <code>exponentMaxSmall</code> - Desired maximum exponent (EMax).

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithNoFlags
    @Deprecated public PrecisionContext WithNoFlags()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithPrecision
    @Deprecated public PrecisionContext WithPrecision(int precision)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>precision</code> - Desired maximum number of digits a number can have. If 0,
 the number of digits is unlimited.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithPrecisionInBits
    @Deprecated public PrecisionContext WithPrecisionInBits(boolean isPrecisionBits)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>isPrecisionBits</code> - The new value of the "IsPrecisionInBits" property for
 the copy.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithRounding
    @Deprecated public PrecisionContext WithRounding(Rounding rounding)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>rounding</code> - Desired value of the Rounding property.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithSimplified
    @Deprecated public PrecisionContext WithSimplified(boolean simplified)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>simplified</code> - The new value of the "IsSimplified" property for the copy.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithTraps
    @Deprecated public PrecisionContext WithTraps(int traps)
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Parameters:**

* <code>traps</code> - Flags representing the traps to enable. See the property
 "Traps".

**Returns:**

* A context object for arbitrary-precision arithmetic settings.

### WithUnlimitedExponents
    @Deprecated public PrecisionContext WithUnlimitedExponents()
Deprecated.&nbsp;Use EContext from PeterO.Numbers/com.upokecenter.numbers.

**Returns:**

* A context object for arbitrary-precision arithmetic settings.
