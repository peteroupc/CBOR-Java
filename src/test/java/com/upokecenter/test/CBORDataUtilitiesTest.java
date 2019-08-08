package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;
import com.upokecenter.cbor.*;

  public class CBORDataUtilitiesTest {
    private void AssertNegative(CBORObject obj) {
      if (!(obj.isNegative())) {
 Assert.fail();
 }
      CBORTestCommon.AssertRoundTrip(obj);
    }
    @Test
    public void TestPreserveNegativeZero() {
      CBORObject cbor;
      cbor = CBORDataUtilities.ParseJSONNumber("-0", false, false, true);
      {
        String stringTemp = cbor.toString();
        Assert.assertEquals(
          "-0",
          stringTemp);
      }
      cbor = CBORDataUtilities.ParseJSONNumber("-0e-1", false, false, true);
      {
        String stringTemp = cbor.toString();
        Assert.assertEquals(
          "-0.0",
          stringTemp);
      }
      cbor = CBORDataUtilities.ParseJSONNumber("-0e1", false, false, true);
      {
        String stringTemp = cbor.toString();
        Assert.assertEquals(
          "-0E+1",
          stringTemp);
      }
      cbor = CBORDataUtilities.ParseJSONNumber("-0.0e1", false, false, true);
      {
        String stringTemp = cbor.toString();
        Assert.assertEquals(
          "-0",
          stringTemp);
      }
      cbor = CBORDataUtilities.ParseJSONNumber("-0.0", false, false, true);
      {
        String stringTemp = cbor.toString();
        Assert.assertEquals(
          "-0.0",
          stringTemp);
      }

      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0",
    false,
    false,
    true));
      this.AssertNegative(
    CBORDataUtilities.ParseJSONNumber(
      "-0.0",
      false,
      false,
      true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.0000",
    false,
    false,
    true));
      this.AssertNegative(
    CBORDataUtilities.ParseJSONNumber(
      "-0e0",
      false,
      false,
      true));
      this.AssertNegative(
    CBORDataUtilities.ParseJSONNumber(
      "-0e+1",
      false,
      false,
      true));
      this.AssertNegative(
    CBORDataUtilities.ParseJSONNumber(
      "-0e-1",
      false,
      false,
      true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0e+999999999999",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0e-999999999999",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.0e0",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.0e+1",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.0e-1",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.0e+999999999999",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.0e-999999999999",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.000e0",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.000e+0",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.000e-0",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.000e1",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.000e+1",
    false,
    false,
    true));
      this.AssertNegative(
  CBORDataUtilities.ParseJSONNumber(
    "-0.000e-1",
    false,
    false,
    true));
      this.AssertNegative(
    CBORDataUtilities.ParseJSONNumber(
      "-0.000e+999999999999",
      false,
      false,
      true));
      this.AssertNegative(
    CBORDataUtilities.ParseJSONNumber(
      "-0.000e-999999999999",
      false,
      false,
      true));
    }
    @Test
    public void TestParseJSONNumberNegativeZero() {
      {
        String stringTemp = CBORDataUtilities.ParseJSONNumber("-0").toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
CBORDataUtilities.ParseJSONNumber("-0E+0").toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
CBORDataUtilities.ParseJSONNumber("-0E-0").toString();
        Assert.assertEquals(
          "0",
          stringTemp);
      }
      {
        String stringTemp =
CBORDataUtilities.ParseJSONNumber("-0E-1").toString();
        Assert.assertEquals(
          "0.0",
          stringTemp);
      }
      {
        String stringTemp =
CBORDataUtilities.ParseJSONNumber("-0.00").toString();
        Assert.assertEquals(
          "0.00",
          stringTemp);
      }
      {
        String stringTemp =
CBORDataUtilities.ParseJSONNumber("-0.00E+0").toString();
        Assert.assertEquals(
          "0.00",
          stringTemp);
      }
      {
        String stringTemp =
CBORDataUtilities.ParseJSONNumber("-0.00E-0").toString();
        Assert.assertEquals(
          "0.00",
          stringTemp);
      }
      {
        String stringTemp =
CBORDataUtilities.ParseJSONNumber("-0.00E-1").toString();
        Assert.assertEquals(
          "0.000",
          stringTemp);
      }
    }

    @Test
    public void TestParseJSONNumber() {
      if (CBORDataUtilities.ParseJSONNumber("100.", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-100.", false, false) != null) {
        Assert.fail();
      }
      if (
  CBORDataUtilities.ParseJSONNumber(
    "100.e+20",
    false,
    false) != null) {
        Assert.fail();
      }
      if (
  CBORDataUtilities.ParseJSONNumber(
    "-100.e20",
    false,
    false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("100.e20", false, false) != null) {
        Assert.fail();
      }

      if (CBORDataUtilities.ParseJSONNumber("+0.1", false, false) != null) {
        Assert.fail();
      }

      if (CBORDataUtilities.ParseJSONNumber("0.", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-0.", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0g.1", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0.e+20", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-0.e20", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0.e20", false, false) != null) {
        Assert.fail();
      }

      if (CBORDataUtilities.ParseJSONNumber(null, false, false) != null) {
        Assert.fail();
      }
      if (
  CBORDataUtilities.ParseJSONNumber(
    "",
    false,
    false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("xyz", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("true", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber(".1", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0..1", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0xyz", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0.1xyz", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0.xyz", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0.5exyz", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0.5q+88", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0.5ee88", false, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-5e") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-5e-2x") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-5e+2x") != null) {
        Assert.fail();
      }
      CBORObject cbor = CBORDataUtilities.ParseJSONNumber("2e-2147483648");
      CBORTestCommon.AssertJSONSer(cbor, "2E-2147483648");
      if (CBORDataUtilities.ParseJSONNumber(
        "0.5e+xyz",
        false,
        false) != null) {
        Assert.fail();
      }
      if (
  CBORDataUtilities.ParseJSONNumber(
    "0.5e+88xyz",
    false,
    false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0000") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0x1") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0xf") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0x20") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0x01") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber(".2") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber(".05") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-.2") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-.05") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("23.") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("23.e0") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("23.e1") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("0.") != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("5.2", true, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("5e+1", true, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-5.2", true, false) != null) {
        Assert.fail();
      }
      if (CBORDataUtilities.ParseJSONNumber("-5e+1", true, false) != null) {
        Assert.fail();
      }

      TestCommon.CompareTestEqual(
  CBORObject.FromObject(230),
  CBORDataUtilities.ParseJSONNumber("23.0e01"));
      TestCommon.CompareTestEqual(
  CBORObject.FromObject(23),
  CBORDataUtilities.ParseJSONNumber("23.0e00"));
      cbor = CBORDataUtilities.ParseJSONNumber(
        "1e+99999999999999999999999999",
        false,
        false);
      if (!(cbor != null)) {
 Assert.fail();
 }
      if (cbor.CanFitInDouble()) {
 Assert.fail();
 }
      CBORTestCommon.AssertRoundTrip(cbor);
    }
  }
