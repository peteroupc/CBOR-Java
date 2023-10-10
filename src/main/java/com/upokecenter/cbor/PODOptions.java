package com.upokecenter.cbor;

  public class PODOptions {
    public PODOptions() {
 this("");
    }

/**
 * @deprecated Use the more readable String constructor instead.
 */
@Deprecated
    public PODOptions(boolean removeIsPrefix, boolean useCamelCase) {
      this.propVarusecamelcase = useCamelCase;
    }

    public PODOptions(String paramString) {
      if (paramString == null) {
        throw new NullPointerException("paramString");
      }
      OptionsParser parser = new OptionsParser(paramString);
      this.propVarusecamelcase = parser.GetBoolean("usecamelcase", true);
    }

    @Override public String toString() {
      return new StringBuilder()
        .append("usecamelcase=").append(this.getUseCamelCase() ? "true" :
"false")
        .toString();
    }

    public static final PODOptions Default = new PODOptions();

    public final boolean getUseCamelCase() { return propVarusecamelcase; }
private final boolean propVarusecamelcase;
  }
