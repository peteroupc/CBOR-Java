# com.upokecenter.util.Rounding

    @Deprecated public enum Rounding extends Enum<Rounding>

<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
<div class='block'><span class='deprecationComment'>Use ERounding from PeterO.Numbers/com.upokecenter.numbers.</span></div>

## Enum Constants

* `Ceiling`<br>
 Deprecated.  If there is a fractional part, the number is rounded to the highest
 representable number that's closest to it.
* `Down`<br>
 Deprecated.  The fractional part is discarded (the number is truncated).
* `Floor`<br>
 Deprecated.  If there is a fractional part, the number is rounded to the lowest
 representable number that's closest to it.
* `HalfDown`<br>
 Deprecated.  Rounded to the nearest number; if the fractional part is exactly half, it is
 discarded.
* `HalfEven`<br>
 Deprecated.  Rounded to the nearest number; if the fractional part is exactly half, the
 number is rounded to the closest representable number that is even.
* `HalfUp`<br>
 Deprecated.  Rounded to the nearest number; if the fractional part is exactly half, the
 number is rounded to the closest representable number away from zero.
* `Odd`<br>
 Deprecated.  If there is a fractional part and the whole number part is even, the number
 is rounded to the closest representable odd number away from zero.
* `OddOrZeroFiveUp`<br>
 Deprecated.  For binary floating point numbers, this is the same as Odd.
* `Unnecessary`<br>
 Deprecated.  Indicates that rounding will not be used.
* `Up`<br>
 Deprecated.  If there is a fractional part, the number is rounded to the closest
 representable number away from zero.
* `ZeroFiveUp`<br>
 Deprecated.  If there is a fractional part and if the last digit before rounding is 0 or
 half the radix, the number is rounded to the closest representable
 number away from zero; otherwise the fractional part is discarded.

## Methods

* `static Rounding valueOf(String name)`<br>
 Deprecated.  Returns the enum constant of this type with the specified name.
* `static Rounding[] values()`<br>
 Deprecated.  Returns an array containing the constants of this enum type, in
the order they are declared.

## Method Details

### Up
    public static final Rounding Up
### Down
    public static final Rounding Down
### Ceiling
    public static final Rounding Ceiling
### Floor
    public static final Rounding Floor
### HalfUp
    public static final Rounding HalfUp
### HalfDown
    public static final Rounding HalfDown
### HalfEven
    public static final Rounding HalfEven
### Unnecessary
    public static final Rounding Unnecessary
### ZeroFiveUp
    public static final Rounding ZeroFiveUp
### Odd
    public static final Rounding Odd
### OddOrZeroFiveUp
    public static final Rounding OddOrZeroFiveUp
### values
    public static Rounding[] values()
### valueOf
    public static Rounding valueOf(String name)
## Enum Constant Details

### Up
    public static final Rounding Up
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### Down
    public static final Rounding Down
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### Ceiling
    public static final Rounding Ceiling
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### Floor
    public static final Rounding Floor
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### HalfUp
    public static final Rounding HalfUp
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### HalfDown
    public static final Rounding HalfDown
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### HalfEven
    public static final Rounding HalfEven
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### Unnecessary
    public static final Rounding Unnecessary
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### ZeroFiveUp
    public static final Rounding ZeroFiveUp
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### Odd
    public static final Rounding Odd
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
### OddOrZeroFiveUp
    public static final Rounding OddOrZeroFiveUp
<span class='deprecatedLabel'>Deprecated.</span>&nbsp;
