package com.upokecenter.cbor;
/*
Written by Peter O. in 2014.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

  class CBORTag0 implements ICBORTag, ICBORObjectConverter<java.util.Date> {
    private static String DateTimeToString(java.util.Date bi) {
      int[] dt = PropertyMap.BreakDownDateTime(bi);
      // TODO: Change to true in next major version
      return CBORUtilities.ToAtomDateTimeString(dt, false);
    }

    static void AddConverter() {
      // TODO: FromObject with Dates has different behavior
      // in Java version, which has to be retained until
      // the next major version for backward compatibility.
      // However, since ToObject is new, we can convert
      // to Date in the .NET and Java versions
      CBORObject.AddConverter(java.util.Date.class, new CBORTag0());
    }

    public CBORTypeFilter GetTypeFilter() {
      return CBORTypeFilter.TextString;
    }

    public CBORObject ValidateObject(CBORObject obj) {
      if (obj.getType() != CBORType.TextString) {
        throw new CBORException("Not a text String");
      }
      return obj;
    }

    public java.util.Date FromCBORObject(CBORObject obj) {
      if (!obj.HasTag(0)) {
        throw new CBORException("Not tag 0");
      }
      return StringToDateTime(obj.AsString());
    }

    public static java.util.Date StringToDateTime(String str) {
      int[] dt = CBORUtilities.ParseAtomDateTimeString(str);
      return PropertyMap.BuildUpDateTime(dt);
    }

    public CBORObject ToCBORObject(java.util.Date obj) {
      return CBORObject.FromObjectAndTag(DateTimeToString(obj), 0);
    }
  }
