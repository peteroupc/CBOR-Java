package com.upokecenter.cbor;

  public final class CBOREncodeOptions {
    public static final CBOREncodeOptions Default =
      new CBOREncodeOptions();

    public static final CBOREncodeOptions DefaultCtap2Canonical =
      new CBOREncodeOptions("ctap2canonical=true");

    public CBOREncodeOptions() {
 this("");
    }

/**
 * @deprecated Use the more readable String constructor instead.
 */
@Deprecated
    public CBOREncodeOptions(
      boolean useIndefLengthStrings,
      boolean allowDuplicateKeys) {
 this(useIndefLengthStrings, allowDuplicateKeys, false);
    }

/**
 * @deprecated Use the more readable String constructor instead.
 */
@Deprecated
    public CBOREncodeOptions(
      boolean useIndefLengthStrings,
      boolean allowDuplicateKeys,
      boolean ctap2Canonical) {
      this.propVarresolvereferences = false;
      this.propVarallowempty = false;
      this.propVarfloat64 = false;
      this.propVarkeepkeyorder = false;
      this.propVaruseindeflengthstrings = useIndefLengthStrings;
      this.propVarallowduplicatekeys = allowDuplicateKeys;
      this.propVarctap2canonical = ctap2Canonical;
    }

    public CBOREncodeOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarresolvereferences = parser.GetBoolean("resolvereferences",
          false);
      this.propVaruseindeflengthstrings = parser.GetBoolean(
        "useindeflengthstrings",
        false);
      this.propVarfloat64 = parser.GetBoolean(
        "float64",
        false);
      this.propVarallowduplicatekeys = parser.GetBoolean("allowduplicatekeys",
          false);
      this.propVarkeepkeyorder = parser.GetBoolean("keepkeyorder",
          false);
      this.propVarallowempty = parser.GetBoolean("allowempty", false);
      this.propVarctap2canonical = parser.GetBoolean("ctap2canonical", false);
    }

    @Override public String toString() {
      return new StringBuilder()
        .append("allowduplicatekeys=")
        .append(this.getAllowDuplicateKeys() ? "true" : "false")
        .append(";useindeflengthstrings=")
        .append(this.getUseIndefLengthStrings() ? "true" : "false")
        .append(";float64=").append(this.getFloat64() ? "true" : "false")
        .append(";ctap2canonical=")
        .append(this.getCtap2Canonical() ? "true" : "false")
        .append(";keepkeyorder=").append(this.getKeepKeyOrder() ? "true" : "false")
        .append(";resolvereferences=")
        .append(this.getResolveReferences() ? "true" : "false")
        .append(";allowempty=").append(this.getAllowEmpty() ? "true" : "false")
        .toString();
    }

    public final boolean getResolveReferences() { return propVarresolvereferences; }
private final boolean propVarresolvereferences;

    public final boolean getUseIndefLengthStrings() { return propVaruseindeflengthstrings; }
private final boolean propVaruseindeflengthstrings;

    public final boolean getKeepKeyOrder() { return propVarkeepkeyorder; }
private final boolean propVarkeepkeyorder;

    public final boolean getAllowEmpty() { return propVarallowempty; }
private final boolean propVarallowempty;

    public final boolean getAllowDuplicateKeys() { return propVarallowduplicatekeys; }
private final boolean propVarallowduplicatekeys;

    public final boolean getFloat64() { return propVarfloat64; }
private final boolean propVarfloat64;

    public final boolean getCtap2Canonical() { return propVarctap2canonical; }
private final boolean propVarctap2canonical;
  }
