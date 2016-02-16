# com.upokecenter.util.TrapException

    @Deprecated public class TrapException extends ArithmeticException

<strong>Deprecated.</strong>&nbsp;
<div class='block'><i>Use ETrapException from PeterO.Numbers/com.upokecenter.numbers.</i></div>

## Methods

* `TrapException(int flag,
             PrecisionContext ctx,
             Object result) com.upokecenter.TrapException`<br>
 Deprecated.  Initializes a new instance of the com.upokecenter.TrapException
 class.
* `PrecisionContext getContext()`<br>
 Deprecated.  Gets the precision context used during the operation that triggered the
 trap.
* `int getError()`<br>
 Deprecated.  Gets the flag that specifies the kind of error (PrecisionContext.FlagXXX).
* `Object getResult()`<br>
 Deprecated.  Gets the defined result of the operation that caused the trap.

## Constructors

* `TrapException(int flag,
             PrecisionContext ctx,
             Object result) com.upokecenter.TrapException`<br>
 Deprecated.  Initializes a new instance of the com.upokecenter.TrapException
 class.

## Method Details

### TrapException
    public TrapException(int flag, PrecisionContext ctx, Object result)
Deprecated.&nbsp;

**Parameters:**

* <code>flag</code> - A flag that specifies the kind of error
 (PrecisionContext.FlagXXX). This will only be one flag, such as
 FlagInexact or FlagSubnormal.

* <code>ctx</code> - A context object for arbitrary-precision arithmetic settings.

* <code>result</code> - The desired result of the operation that caused the trap, such
 as an <code>ExtendedDecimal</code> or <code>ExtendedFloat</code>.

### TrapException
    public TrapException(int flag, PrecisionContext ctx, Object result)
Deprecated.&nbsp;

**Parameters:**

* <code>flag</code> - A flag that specifies the kind of error
 (PrecisionContext.FlagXXX). This will only be one flag, such as
 FlagInexact or FlagSubnormal.

* <code>ctx</code> - A context object for arbitrary-precision arithmetic settings.

* <code>result</code> - The desired result of the operation that caused the trap, such
 as an <code>ExtendedDecimal</code> or <code>ExtendedFloat</code>.

### getContext
    public final PrecisionContext getContext()
Deprecated.&nbsp;

**Returns:**

* The precision context used during the operation that triggered the
 trap. May be null.

### getResult
    public final Object getResult()
Deprecated.&nbsp;

**Returns:**

* The defined result of the operation that caused the trap.

### getError
    public final int getError()
Deprecated.&nbsp;

**Returns:**

* The flag that specifies the kind of error
 (PrecisionContext.FlagXXX). This will only be one flag, such as
 FlagInexact or FlagSubnormal.
