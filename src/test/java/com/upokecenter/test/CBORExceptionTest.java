package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.cbor.*;

  public class CBORExceptionTest {
    @Test
    public void TestConstructor() {
     try {
        throw new CBORException("Test exception");
      } catch (CBORException ex) {
         // expected exception
      }
    }
  }
