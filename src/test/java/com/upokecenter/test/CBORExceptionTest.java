package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.cbor.*;

  public class CBORExceptionTest {
    @Test
    public void TestConstructor() {
      Assert.Throws<CBORException>(()=>throw new CBORException("Test exception"));
    }
  }
