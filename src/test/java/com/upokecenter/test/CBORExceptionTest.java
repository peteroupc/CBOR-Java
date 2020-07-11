package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.cbor.*;

  public class CBORExceptionTest {
    @Test[/*TEMP*/Timeout(20000000)]
    public void TestConstructor() {
      try {
        throw new CBORException("Test exception");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
  }
