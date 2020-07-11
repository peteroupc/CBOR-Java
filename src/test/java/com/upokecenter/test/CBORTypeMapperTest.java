package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.cbor.*;

  public class CBORTypeMapperTest {
    @Test
    public void TestAddTypeName() {
      CBORTypeMapper tm = new CBORTypeMapper();
      try {
        tm.AddTypeName(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        tm.AddTypeName("");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        tm.AddTypeName("java.net.URI");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestAddTypePrefix() {
      CBORTypeMapper tm = new CBORTypeMapper();
      try {
        tm.AddTypePrefix(null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        tm.AddTypePrefix("");
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        tm.AddTypePrefix("java.net.URI");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
  }
