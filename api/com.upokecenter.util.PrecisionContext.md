# com.upokecenter.util.PrecisionContext

    @Deprecated public class PrecisionContext extends Object

Deprecated.
<div class='deprecationComment'>Use EContext from PeterO.Numbers/com.upokecenter.numbers.</div>

## Methods

* `PrecisionContext​(int precision,
                Rounding rounding,
                int exponentMinSmall,
                int exponentMaxSmall,
                boolean clampNormalExponents) PrecisionContext`<br>
 Deprecated. Initializes a new instance of the PrecisionContext class.
* `String toString()`<br>
 Deprecated. Gets a string representation of this object.

## Constructors

* `PrecisionContext​(int precision,
                Rounding rounding,
                int exponentMinSmall,
                int exponentMaxSmall,
                boolean clampNormalExponents) PrecisionContext`<br>
 Deprecated. Initializes a new instance of the PrecisionContext class.

## Method Details

### PrecisionContext
    public PrecisionContext​(int precision, Rounding rounding, int exponentMinSmall, int exponentMaxSmall, boolean clampNormalExponents)
Deprecated.

**Parameters:**

* <code>precision</code> - The maximum number of digits a number can have, or 0 for an
 unlimited number of digits.

* <code>rounding</code> - The rounding mode to use when a number can't fit the given
 precision.

* <code>exponentMinSmall</code> - The minimum exponent.

* <code>exponentMaxSmall</code> - The maximum exponent.

* <code>clampNormalExponents</code> - Whether to clamp a number's significand to the
 given maximum precision (if it isn't zero) while remaining within the
 exponent range.

### PrecisionContext
    public PrecisionContext​(int precision, Rounding rounding, int exponentMinSmall, int exponentMaxSmall, boolean clampNormalExponents)
Deprecated.

**Parameters:**

* <code>precision</code> - The maximum number of digits a number can have, or 0 for an
 unlimited number of digits.

* <code>rounding</code> - The rounding mode to use when a number can't fit the given
 precision.

* <code>exponentMinSmall</code> - The minimum exponent.

* <code>exponentMaxSmall</code> - The maximum exponent.

* <code>clampNormalExponents</code> - Whether to clamp a number's significand to the
 given maximum precision (if it isn't zero) while remaining within the
 exponent range.

### toString
    public String toString()
Deprecated.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object.

### toString
    public String toString()
Deprecated.

**Overrides:**

* <code>toString</code>&nbsp;in class&nbsp;<code>Object</code>

**Returns:**

* A string representation of this object.
