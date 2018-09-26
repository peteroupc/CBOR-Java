package com.upokecenter.test;

  public final class PODClass {
    public PODClass() {
      this.setPropA(0);
      this.setPropB(1);
      this.setPropC(false);
      this.propVarprivatepropa = 2;
      this.setFloatProp(0);
      this.setDoubleProp(0);
      this.setStringProp("");
      this.setStringArray(null);
    }

    private final int getPrivatePropA() { return propVarprivatepropa; }
private final int propVarprivatepropa;

    public static int getStaticPropA() { return propVarstaticpropa; }
public static void setStaticPropA(int value) { propVarstaticpropa = value; }
private static int propVarstaticpropa;

    public final int getPropA() { return propVarpropa; }
public final void setPropA(int value) { propVarpropa = value; }
private int propVarpropa;

    public final int getPropB() { return propVarpropb; }
public final void setPropB(int value) { propVarpropb = value; }
private int propVarpropb;

    public final boolean isPropC() { return propVarispropc; }
public final void setPropC(boolean value) { propVarispropc = value; }
private boolean propVarispropc;

    public final float getFloatProp() { return propVarfloatprop; }
public final void setFloatProp(float value) { propVarfloatprop = value; }
private float propVarfloatprop;

    public final double getDoubleProp() { return propVardoubleprop; }
public final void setDoubleProp(double value) { propVardoubleprop = value; }
private double propVardoubleprop;

    public final String getStringProp() { return propVarstringprop; }
public final void setStringProp(String value) { propVarstringprop = value; }
private String propVarstringprop;

    public final String[] getStringArray() { return propVarstringarray; }
public final void setStringArray(String[] value) { propVarstringarray = value; }
private String[] propVarstringarray;
  }
