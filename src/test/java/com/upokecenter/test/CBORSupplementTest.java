package com.upokecenter.test;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import java.io.*;
import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.cbor.*;
import com.upokecenter.numbers.*;

  public class CBORSupplementTest {
    @Test
    public void IncorrectDecimalFrac() {
      byte[] bytes;
      CBORObject cbor;
      // String instead of array
      bytes = new byte[] { (byte)0xc4, 0x61, 0x41 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EDecimal.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // number instead of array
      bytes = new byte[] { (byte)0xc4, 0x00 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EDecimal.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xc4, (byte)0x81, 0, };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EDecimal.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xc4, (byte)0x82, 0, 0x61, 0x41 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EDecimal.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xc4, (byte)0x82, 0x61, 0x41, 0 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EDecimal.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xc4, (byte)0x83, 0, 0, 0 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EDecimal.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void IncorrectBigFloat() {
      byte[] bytes;
      CBORObject cbor;
      // String instead of array
      bytes = new byte[] { (byte)0xc5, 0x61, 0x41 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EFloat.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // number instead of array
      bytes = new byte[] { (byte)0xc5, 0x00 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EFloat.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xc5, (byte)0x81, 0, };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EFloat.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xc5, (byte)0x82, 0, 0x61, 0x41 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EFloat.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xc5, (byte)0x82, 0x61, 0x41, 0 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EFloat.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xc5, (byte)0x83, 0, 0, 0 };
      cbor = CBORObject.DecodeFromBytes(bytes);
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      try {
        System.out.println("" + cbor.ToObject(EFloat.class));
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCBORObjectArgumentValidation() {
      Assert.assertEquals(
        CBORObject.Null,
        ToObjectTest.TestToFromObjectRoundTrip(null));
      Assert.assertEquals(
        CBORObject.Null,
        ToObjectTest.TestToFromObjectRoundTrip(null));
      Assert.assertEquals(
        CBORObject.True,
        ToObjectTest.TestToFromObjectRoundTrip(true));
      Assert.assertEquals(
        CBORObject.False,
        ToObjectTest.TestToFromObjectRoundTrip(false));
      Assert.assertEquals(
        ToObjectTest.TestToFromObjectRoundTrip(8),
        ToObjectTest.TestToFromObjectRoundTrip((byte)8));

      try {
        CBORObject.True.ToObject(ERational.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.False.ToObject(ERational.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewArray().ToObject(ERational.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        CBORObject.NewMap().ToObject(ERational.class);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestIncompleteCBORString() {
      byte[] bytes = { 0x65, 0x41, 0x41, 0x41, 0x41 };
      try {
        CBORObject.DecodeFromBytes(bytes);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestIncompleteIndefLengthArray() {
      byte[] bytes;
      bytes = new byte[] { (byte)0x9f, 0, 0, 0, 0, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0x9f, 0, 0, 0, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes(bytes);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestIncompleteIndefLengthMap() {
      // Premature end after value
      byte[] bytes = { (byte)0xbf, 0x61, 0x41, 0, 0x61, 0x42, 0 };
      try {
        CBORObject.DecodeFromBytes(bytes);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      // Premature end after key
      bytes = new byte[] { (byte)0xbf, 0x61, 0x41, 0, 0x61, 0x42 };
      try {
        CBORObject.DecodeFromBytes(bytes);
        Assert.fail("Should have failed");
      } catch (CBORException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { (byte)0xbf, 0x61, 0x41, 0, 0x61, 0x42, 0, (byte)0xff };
      try {
        CBORObject.DecodeFromBytes(bytes);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCyclicRefs() {
      CBORObject cbor = CBORObject.NewArray();
      cbor.Add(CBORObject.NewArray());
      cbor.Add(cbor);
      cbor.get(0).Add(cbor);
      try {
        {
          java.io.ByteArrayOutputStream memoryStream = null;
try {
memoryStream = new java.io.ByteArrayOutputStream();

          cbor.WriteTo(memoryStream);
}
finally {
try { if (memoryStream != null) { memoryStream.close(); } } catch (java.io.IOException ex) {}
}
}
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestNestingDepth() {
      try {
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          for (int i = 0; i < 2000; ++i) {
            // Write beginning of indefinite-length array
            ms.write(0x9f);
          }
          for (int i = 0; i < 2000; ++i) {
            // Write end of indefinite-length array
            ms.write(0xff);
          }
          // Assert throwing CBOR exception for reaching maximum
          // nesting depth
          try {
            CBORObject.DecodeFromBytes(ms.toByteArray());
            Assert.fail("Should have failed");
          } catch (CBORException ex) {
            // NOTE: Intentionally empty
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        {
          java.io.ByteArrayOutputStream ms = null;
try {
ms = new java.io.ByteArrayOutputStream();

          for (int i = 0; i < 495; ++i) {
            // Write beginning of indefinite-length array
            ms.write(0x9f);
          }
          for (int i = 0; i < 495; ++i) {
            // Write end of indefinite-length array
            ms.write(0xff);
          }
          // Maximum nesting depth not reached, so shouldn't throw
          try {
            CBORObject.DecodeFromBytes(ms.toByteArray());
          } catch (Exception ex) {
            Assert.fail(ex.toString());
            throw new IllegalStateException("", ex);
          }
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (Exception ex) {
        throw new IllegalStateException(ex.getMessage(), ex);
      }
    }

    @Test
    public void TestCBOREInteger() {
      EInteger bi = EInteger.FromString("9223372036854775808");
      try {
        ToObjectTest.TestToFromObjectRoundTrip(bi).ToObject(long.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(bi).ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bi = EInteger.FromString("-9223372036854775809");
      try {
        ToObjectTest.TestToFromObjectRoundTrip(bi).ToObject(long.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        ToObjectTest.TestToFromObjectRoundTrip(bi).ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bi = EInteger.FromString("-9223372036854775808");
      try {
        ToObjectTest.TestToFromObjectRoundTrip(bi).ToObject(int.class);
        Assert.fail("Should have failed");
      } catch (ArithmeticException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestEquivalentInfinities() {
      CBORObject co, co2;
      co = ToObjectTest.TestToFromObjectRoundTrip(CBORTestCommon.DecPosInf);
      co2 = ToObjectTest.TestToFromObjectRoundTrip(Double.POSITIVE_INFINITY);
      {
        boolean boolTemp = co.AsNumber().IsNegative() &&
          co.AsNumber().IsInfinity();
        boolean boolTemp2 = co2.AsNumber().IsNegative() &&
          co2.AsNumber().IsInfinity();
        Assert.assertEquals(boolTemp, boolTemp2);
      }
    }

    @Test
    public void TestSharedRefs() {
      CBOREncodeOptions encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      byte[] bytes;
      CBORObject cbor;
      String expected;
      bytes = new byte[] {
        (byte)0x9f, (byte)0xd8, 28, 1, (byte)0xd8, 29, 0, 3, 3, (byte)0xd8, 29,
        0, (byte)0xff,
       };
      cbor = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      expected = "[1,1,3,3,1]";
      Assert.assertEquals(expected, cbor.ToJSONString());
      bytes = new byte[] {
        (byte)0x9f, (byte)0xd8, 28, (byte)0x81, 1, (byte)0xd8, 29, 0, 3, 3, (byte)0xd8,
        29, 0, (byte)0xff,
       };
      cbor = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      expected = "[[1],[1],3,3,[1]]";
      Assert.assertEquals(expected, cbor.ToJSONString());
      // Checks if both objects are the same reference, not just equal
      if (!(cbor.get(0) == cbor.get(1))) {
 Assert.fail("cbor.get(0) not same as cbor.get(1)");
 }
      if (!(cbor.get(0) == cbor.get(4))) {
 Assert.fail("cbor.get(0) not same as cbor.get(4)");
 }
      bytes = new byte[] { (byte)0xd8, 28, (byte)0x82, 1, (byte)0xd8, 29, 0 };
      cbor = CBORObject.DecodeFromBytes(bytes, encodeOptions);
      Assert.assertEquals(2, cbor.size());
      // Checks if both objects are the same reference, not just equal
      if (!(cbor == cbor.get(1))) {
 Assert.fail("objects not the same");
 }
    }

    @Test
    public void TestBuiltInTags() {
      // As of 4.0, nearly all tags are no longer converted to native objects; thus,
      // DecodeFromBytes no longer fails when such tags are encountered but
      // have the wrong format (though it can fail for other reasons).
      // Tag 2, bignums
      byte[] bytes;
      byte[] secondbytes = new byte[] { 0, 0x20, 0x60, (byte)0x80, (byte)0xa0, (byte)0xe0 };
      byte[] firstbytes = new byte[] { (byte)0xc2, (byte)0xc3, (byte)0xc4, (byte)0xc5 };
      CBORObject cbor;
      for (byte firstbyte : firstbytes) {
        for (byte secondbyte : secondbytes) {
          bytes = new byte[] { firstbyte, secondbyte };
          cbor = CBORObject.DecodeFromBytes(bytes);
          if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
        }
      }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd8, 0x1e, (byte)0x9f, 0x01,
        0x01, (byte)0xff,
       });
      if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }

      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd8, 0x1e, (byte)0x9f, 0x01,
        0x01, 0x01, (byte)0xff,
       });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd8, 0x1e, (byte)0x9f, 0x01,
        (byte)0xff,
       });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }

      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd9, 0x01, 0x0e, (byte)0x9f,
        0x01,
        0x01, (byte)0xff,
       });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }

      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd9, 0x01, 0x0e, (byte)0x9f,
        0x01,
        0x01, 0x01, (byte)0xff,
       });
      if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd9, 0x01, 0x0e, (byte)0x9f,
        0x01,
        (byte)0xff,
       });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }

      cbor = CBORObject.DecodeFromBytes(
          new byte[] { (byte)0xd8, 0x1e, (byte)0x9f, (byte)0xff, });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }

      cbor = CBORObject.DecodeFromBytes(
          new byte[] { (byte)0xd9, 0x01, 0x0e, (byte)0x9f, (byte)0xff, });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4, (byte)0x9f, 0x00, 0x00,
        (byte)0xff,
       });
      if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5, (byte)0x9f, 0x00, 0x00,
        (byte)0xff,
       });
      if (!(cbor.isNumber())) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4, (byte)0x9f, 0x00, 0x00,
        0x00,
        (byte)0xff,
       });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5, (byte)0x9f, 0x00,
        0x00, 0x00,
        (byte)0xff,
       });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc4, (byte)0x9f, 0x00,
        (byte)0xff,
       });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xc5, (byte)0x9f, 0x00,
        (byte)0xff,
       });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc4, (byte)0x9f, (byte)0xff, });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc5, (byte)0x9f, (byte)0xff, });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc4, (byte)0x81, 0x00, });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      cbor = CBORObject.DecodeFromBytes(new byte[] { (byte)0xc5, (byte)0x81, 0x00, });
      if (cbor.isNumber()) {
 Assert.fail(cbor.toString());
 }
      {
        Object objectTemp = EInteger.FromInt32(0);
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc2,
          0x40,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-1");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3,
          0x41, 0x00,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-1");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3,
          0x40,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestUUID() {
      CBORObject obj =
        ToObjectTest.TestToFromObjectRoundTrip(java.util.UUID.fromString(
            "00112233-4455-6677-8899-AABBCCDDEEFF"));
      Assert.assertEquals(CBORType.ByteString, obj.getType());
      Assert.assertEquals(EInteger.FromString("37"), obj.getMostInnerTag());
      byte[] bytes = obj.GetByteString();
      Assert.assertEquals(16, bytes.length);
      Assert.assertEquals(0x00, bytes[0]);
      Assert.assertEquals(0x11, bytes[1]);
      Assert.assertEquals(0x22, bytes[2]);
      Assert.assertEquals(0x33, bytes[3]);
      Assert.assertEquals(0x44, bytes[4]);
      Assert.assertEquals(0x55, bytes[5]);
      Assert.assertEquals(0x66, bytes[6]);
      Assert.assertEquals(0x77, bytes[7]);
      Assert.assertEquals((byte)0x88, bytes[8]);
      Assert.assertEquals((byte)0x99, bytes[9]);
      Assert.assertEquals((byte)0xaa, bytes[10]);
      Assert.assertEquals((byte)0xbb, bytes[11]);
      Assert.assertEquals((byte)0xcc, bytes[12]);
      Assert.assertEquals((byte)0xdd, bytes[13]);
      Assert.assertEquals((byte)0xee, bytes[14]);
      Assert.assertEquals((byte)0xff, bytes[15]);
    }

    // @Test
    public static void TestMiniCBOR() {
      byte[] bytes;
      bytes = new byte[] { 0x19, 2 };
      try {
        {
          java.io.ByteArrayInputStream memoryStream = null;
try {
memoryStream = new java.io.ByteArrayInputStream(bytes);

          MiniCBOR.ReadInt32(memoryStream);
}
finally {
try { if (memoryStream != null) { memoryStream.close(); } } catch (java.io.IOException ex) {}
}
}
        Assert.fail("Should have failed");
      } catch (IOException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x1a, 2 };
      try {
        {
          java.io.ByteArrayInputStream memoryStream = null;
try {
memoryStream = new java.io.ByteArrayInputStream(bytes);

          MiniCBOR.ReadInt32(memoryStream);
}
finally {
try { if (memoryStream != null) { memoryStream.close(); } } catch (java.io.IOException ex) {}
}
}
        Assert.fail("Should have failed");
      } catch (IOException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x1b, 2 };
      try {
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

          MiniCBOR.ReadInt32(ms);
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        Assert.fail("Should have failed");
      } catch (IOException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x1b, 2, 2, 2, 2, 2, 2, 2, 2 };
      try {
        {
          java.io.ByteArrayInputStream memoryStream = null;
try {
memoryStream = new java.io.ByteArrayInputStream(bytes);

          MiniCBOR.ReadInt32(memoryStream);
}
finally {
try { if (memoryStream != null) { memoryStream.close(); } } catch (java.io.IOException ex) {}
}
}
        Assert.fail("Should have failed");
      } catch (IOException ex) {
        // NOTE: Intentionally empty
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      bytes = new byte[] { 0x1c, 2 };
      try {
        {
          java.io.ByteArrayInputStream memoryStream = null;
try {
memoryStream = new java.io.ByteArrayInputStream(bytes);

          MiniCBOR.ReadInt32(memoryStream);
}
finally {
try { if (memoryStream != null) { memoryStream.close(); } } catch (java.io.IOException ex) {}
}
}
        Assert.fail("Should have failed");
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        bytes = new byte[] { 0 };
        {
          java.io.ByteArrayInputStream ms = null;
try {
ms = new java.io.ByteArrayInputStream(bytes);

          Assert.assertEquals(0, MiniCBOR.ReadInt32(ms));
}
finally {
try { if (ms != null) { ms.close(); } } catch (java.io.IOException ex) {}
}
}
        bytes = new byte[] { 0x17 };
        {
          java.io.ByteArrayInputStream ms2 = null;
try {
ms2 = new java.io.ByteArrayInputStream(bytes);

          Assert.assertEquals(0x17, MiniCBOR.ReadInt32(ms2));
}
finally {
try { if (ms2 != null) { ms2.close(); } } catch (java.io.IOException ex) {}
}
}
        bytes = new byte[] { 0x18, 2 };
        {
          java.io.ByteArrayInputStream ms3 = null;
try {
ms3 = new java.io.ByteArrayInputStream(bytes);

          Assert.assertEquals(2, MiniCBOR.ReadInt32(ms3));
}
finally {
try { if (ms3 != null) { ms3.close(); } } catch (java.io.IOException ex) {}
}
}
        bytes = new byte[] { 0x19, 0, 2 };
        {
          java.io.ByteArrayInputStream ms4 = null;
try {
ms4 = new java.io.ByteArrayInputStream(bytes);

          Assert.assertEquals(2, MiniCBOR.ReadInt32(ms4));
}
finally {
try { if (ms4 != null) { ms4.close(); } } catch (java.io.IOException ex) {}
}
}
        bytes = new byte[] { 0x27 };
        {
          java.io.ByteArrayInputStream ms5 = null;
try {
ms5 = new java.io.ByteArrayInputStream(bytes);

          Assert.assertEquals(-1 - 7, MiniCBOR.ReadInt32(ms5));
}
finally {
try { if (ms5 != null) { ms5.close(); } } catch (java.io.IOException ex) {}
}
}
        bytes = new byte[] { 0x37 };
        {
          java.io.ByteArrayInputStream ms6 = null;
try {
ms6 = new java.io.ByteArrayInputStream(bytes);

          Assert.assertEquals(-1 - 0x17, MiniCBOR.ReadInt32(ms6));
}
finally {
try { if (ms6 != null) { ms6.close(); } } catch (java.io.IOException ex) {}
}
}
      } catch (IOException ioex) {
        Assert.fail(ioex.getMessage());
      }
    }

    @Test
    public void TestNegativeBigInts() {
      {
        Object objectTemp = EInteger.FromString("-257");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3,
          0x42, 1,
          0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-65537");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3,
          0x43, 1,
          0, 0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-16777217");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3, 0x44,
          1,
          0, 0, 0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-4294967297");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3, 0x45,
          1,
          0, 0, 0, 0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-1099511627777");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3, 0x46,
          1,
          0, 0, 0, 0, 0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-281474976710657");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3, 0x47,
          1,
          0, 0, 0, 0,
          0, 0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-72057594037927937");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3, 0x48,
          1,
          0, 0, 0, 0,
          0, 0, 0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-18446744073709551617");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3, 0x49,
          1,
          0, 0, 0, 0, 0, 0, 0, 0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
      {
        Object objectTemp = EInteger.FromString("-4722366482869645213697");
        Object objectTemp2 = CBORObject.DecodeFromBytes(new byte[] {
          (byte)0xc3, 0x4a,
          1,
          0, 0, 0, 0, 0, 0, 0, 0, 0,
         }).ToObject(EInteger.class);
        Assert.assertEquals(objectTemp, objectTemp2);
      }
    }

    @Test
    public void TestStringRefs() {
      CBOREncodeOptions encodeOptions = new CBOREncodeOptions("resolvereferences=true");
      var cbor = CBORObject.DecodeFromBytes(
      new byte[] {
        (byte)0xd9, 1, 0, (byte)0x9f, 0x64, 0x61, 0x62, 0x63, 0x64, (byte)0xd8,
        0x19, 0x00, (byte)0xd8, 0x19, 0x00, 0x64, 0x62, 0x62, 0x63, 0x64, (byte)0xd8, 0x19,
        0x01, (byte)0xd8, 0x19, 0x00, (byte)0xd8, 0x19, 0x01, (byte)0xff,
       },
      encodeOptions);
      String expected =
        "[\"abcd\",\"abcd\",\"abcd\",\"bbcd\",\"bbcd\",\"abcd\",\"bbcd\"]";
      Assert.assertEquals(expected, cbor.ToJSONString());
      cbor = CBORObject.DecodeFromBytes(new byte[] {
        (byte)0xd9,
        1, 0, (byte)0x9f, 0x64, 0x61, 0x62, 0x63, 0x64, 0x62, 0x61,
        0x61, (byte)0xd8, 0x19, 0x00, (byte)0xd8, 0x19, 0x00, 0x64, 0x62,
        0x62, 0x63, 0x64, (byte)0xd8, 0x19, 0x01, (byte)0xd8, 0x19, 0x00,
        (byte)0xd8, 0x19, 0x01, (byte)0xff,
       },
      encodeOptions);
      expected =
  "[\"abcd\",\"aa\",\"abcd\",\"abcd\",\"bbcd\",\"bbcd\",\"abcd\",\"bbcd\"]";
      Assert.assertEquals(expected, cbor.ToJSONString());
    }

    @Test
    public void TestPodCompareTo() {
      CPOD3 cpod = new CPOD3();
      CBORObject cbor, cbor2;
      cpod.setAa("Gg");
      cpod.setBb("Jj");
      cpod.setCc("Hh");
      cbor = CBORObject.FromObject(cpod);
      cbor2 = CBORObject.NewMap().Add("aa", "Gg").Add("bb", "Jj").Add("cc",
  "Hh");
      TestCommon.CompareTestEqual(cbor, cbor2);
      cbor2 = CBORObject.FromObject(100);
      TestCommon.CompareTestGreater(cbor, cbor2);
      cbor2 = CBORObject.FromSimpleValue(10);
      TestCommon.CompareTestLess(cbor, cbor2);
    }

    @Test
    public void TestCPOD() {
      CPOD m = new CPOD();
m.setAa("Test");

      CBORObject cbor = CBORObject.FromObject(m);
      if (cbor.ContainsKey("bb")) {
 Assert.fail(cbor.toString());
 }
      Assert.assertEquals(cbor.toString(),"Test",cbor.get("aa").AsString());
    }
  }
