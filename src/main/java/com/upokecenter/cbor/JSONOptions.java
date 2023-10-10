package com.upokecenter.cbor;

  public final class JSONOptions {
    public enum ConversionMode
    {
      Full,

      Double,

      IntOrFloat,

      IntOrFloatFromDouble,

      Decimal128,
    }

    public JSONOptions() {
 this("");
    }

/**
 * @deprecated Use the more readable String constructor instead.
 */
@Deprecated
    public JSONOptions(boolean base64Padding) {
 this("base64Padding=" + (base64Padding ? "1" : "0"));
    }

@SuppressWarnings("deprecation")

/**
 * @deprecated Use the more readable String constructor instead.
 */
@Deprecated
    public JSONOptions(boolean base64Padding, boolean replaceSurrogates) {
 this("base64Padding=" + (base64Padding ? "1" : "0") +
           ";replacesurrogates=" + (replaceSurrogates ? "1" : "0"));
    }

    public JSONOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarpreservenegativezero = parser.GetBoolean(
        "preservenegativezero",
        true);
      this.propVarallowduplicatekeys = parser.GetBoolean(
        "allowduplicatekeys",
        false);
      this.propVarkeepkeyorder = parser.GetBoolean(
        "keepkeyorder",
        false);
      this.propVarbase64padding = parser.GetBoolean("base64padding", true);
      this.propVarreplacesurrogates = parser.GetBoolean(
        "replacesurrogates",
        false);
      this.propVarnumberconversion = ToNumberConversion(parser.GetLCString(
        "numberconversion",
        null));
      this.propVarwritebasic = parser.GetBoolean(
        "writebasic",
        false);
    }

    @Override public String toString() {
      return new StringBuilder()
        .append("base64padding=").append(this.getBase64Padding() ? "true" : "false")
        .append(";replacesurrogates=")
        .append(this.getReplaceSurrogates() ? "true" : "false")
        .append(";preservenegativezero=")
        .append(this.getPreserveNegativeZero() ? "true" : "false")
        .append(";keepkeyorder=").append(this.getKeepKeyOrder() ? "true" : "false")
        .append(";numberconversion=").append(this.FromNumberConversion())
        .append(";allowduplicatekeys=")
        .append(this.getAllowDuplicateKeys() ? "true" : "false")
        .append(";writebasic=").append(this.getWriteBasic() ? "true" : "false")
        .toString();
    }

    public static final JSONOptions Default = new JSONOptions();

/**
 * @deprecated This property now has no effect. This library now includes \u0020necessary
 * padding when writing traditional base64 to JSON and\u0020includes no
 * padding when writing base64url to JSON, in \u0020accordance with the
 * revision of the CBOR specification.
 */
@Deprecated
    public final boolean getBase64Padding() { return propVarbase64padding; }
private final boolean propVarbase64padding;

    private String FromNumberConversion() {
      ConversionMode kind = this.getNumberConversion();
      return kind == ConversionMode.Full ? "full" :
        kind == ConversionMode.Double ? "double" :
        kind == ConversionMode.Decimal128 ? "decimal128" :
        kind == ConversionMode.IntOrFloat ? "intorfloat" :
        (kind == ConversionMode.IntOrFloatFromDouble) ?
"intorfloatfromdouble" : "full";
    }

    private static ConversionMode ToNumberConversion(String str) {
      if (str != null) {
        if (str.equals("full")) {
          return ConversionMode.Full;
        }
        if (str.equals("double")) {
          return ConversionMode.Double;
        }
        if (str.equals("decimal128")) {
          return ConversionMode.Decimal128;
        }
        if (str.equals("intorfloat")) {
          return ConversionMode.IntOrFloat;
        }
        if (str.equals("intorfloatfromdouble")) {
          return ConversionMode.IntOrFloatFromDouble;
        }
      } else {
        return ConversionMode.IntOrFloat;
      }
      throw new IllegalArgumentException("Unrecognized conversion mode");
    }

    public final boolean getPreserveNegativeZero() { return propVarpreservenegativezero; }
private final boolean propVarpreservenegativezero;

    public final ConversionMode getNumberConversion() { return propVarnumberconversion; }
private final ConversionMode propVarnumberconversion;

    public final boolean getWriteBasic() { return propVarwritebasic; }
private final boolean propVarwritebasic;

    public final boolean getKeepKeyOrder() { return propVarkeepkeyorder; }
private final boolean propVarkeepkeyorder;

    public final boolean getAllowDuplicateKeys() { return propVarallowduplicatekeys; }
private final boolean propVarallowduplicatekeys;

    public final boolean getReplaceSurrogates() { return propVarreplacesurrogates; }
private final boolean propVarreplacesurrogates;
  }
