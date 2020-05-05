# com.upokecenter.cbor.CBORDataUtilities

    public final class CBORDataUtilities extends java.lang.Object

## Methods

* `static CBORObject ParseJSONNumber​(java.lang.String str)`<br>
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               boolean integersOnly,
               boolean positiveOnly)`<br>
 Deprecated.
Call the one-argument version of this method instead.
 Call the one-argument version of this method instead.
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               boolean integersOnly,
               boolean positiveOnly,
               boolean preserveNegativeZero)`<br>
 Deprecated.
Instead, call ParseJSONNumber(str, jsonoptions) with a JSONOptions that
 sets preserveNegativeZero to the desired value, either true or
 false.
 Instead, call ParseJSONNumber(str, jsonoptions) with a JSONOptions that
 sets preserveNegativeZero to the desired value, either true or
 false.
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               int offset,
               int count)`<br>
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               int offset,
               int count,
               JSONOptions options)`<br>
* `static CBORObject ParseJSONNumber​(java.lang.String str,
               JSONOptions options)`<br>

## Method Details

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str)
### ParseJSONNumber
    @Deprecated public static CBORObject ParseJSONNumber​(java.lang.String str, boolean integersOnly, boolean positiveOnly)
Deprecated.
Call the one-argument version of this method instead. If this method
 call used positiveOnly = true, check that the String does
 not begin with '-' before calling that version. If this method
 call used integersOnly  = true, check that the String does not
 contain '.', 'E', or 'e' before calling that version.

### ParseJSONNumber
    @Deprecated public static CBORObject ParseJSONNumber​(java.lang.String str, boolean integersOnly, boolean positiveOnly, boolean preserveNegativeZero)
Deprecated.
Instead, call ParseJSONNumber(str, jsonoptions) with a JSONOptions that
 sets preserveNegativeZero to the desired value, either true or
 false. If this method call used positiveOnly = true, check that the
 String does not begin with '-' before calling that
 version. If this method call used integersOnly  = true, check
 that the String does not contain '.', 'E',
 or 'e' before calling that version.

### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str, JSONOptions options)
### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str, int offset, int count)
### ParseJSONNumber
    public static CBORObject ParseJSONNumber​(java.lang.String str, int offset, int count, JSONOptions options)
