package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.cbor.*;

  public class URIUtilityTest {
    public static String CborNamespace() {
      return CBORObject.class.getPackage().getName();
    }

    private static final String URIUtilityName =
      CborNamespace() + ".URIUtility";

    private static void assertIdempotency(String s) {
      boolean cond = (boolean)(Boolean)Reflect.InvokeStatic(
    URIUtilityName,
    "isValidIRI",
    s);
      if (!(cond)) {
 Assert.fail();
 }
      {
        String stringTemp = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          s,
          0);
        String stringTemp2 = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          (String)Reflect.InvokeStatic(URIUtilityName, "escapeURI", s, 0),
          0);
        Assert.assertEquals(stringTemp, stringTemp2);
      }
      {
        String stringTemp = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          s,
          1);
        String stringTemp2 = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          (String)Reflect.InvokeStatic(URIUtilityName, "escapeURI", s, 1),
          1);
        Assert.assertEquals(stringTemp, stringTemp2);
      }
      {
        String stringTemp = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          s,
          2);
        String stringTemp2 = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          (String)Reflect.InvokeStatic(URIUtilityName, "escapeURI", s, 2),
          2);
        Assert.assertEquals(stringTemp, stringTemp2);
      }
      {
        String stringTemp = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          s,
          3);
        String stringTemp2 = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          (String)Reflect.InvokeStatic(URIUtilityName, "escapeURI", s, 3),
          3);
        Assert.assertEquals(stringTemp, stringTemp2);
      }
    }

    private static void assertIdempotencyNeg(String s) {
      if (!(!((boolean)(Boolean)Reflect.InvokeStatic(
    URIUtilityName,
    "isValidIRI",
    s))))Assert.fail();
      {
        String stringTemp = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          s,
          0);
        String stringTemp2 = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          (String)Reflect.InvokeStatic(URIUtilityName, "escapeURI", s, 0),
          0);
        Assert.assertEquals(stringTemp, stringTemp2);
      }
      {
        String stringTemp = (String)Reflect.InvokeStatic(
             URIUtilityName,
             "escapeURI",
             s,
             1);
        String stringTemp2 = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          (String)Reflect.InvokeStatic(URIUtilityName, "escapeURI", s, 1),
          1);
        Assert.assertEquals(stringTemp, stringTemp2);
      }
      {
        String stringTemp = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          s,
          2);
        String stringTemp2 = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          (String)Reflect.InvokeStatic(URIUtilityName, "escapeURI", s, 2),
          2);
        Assert.assertEquals(stringTemp, stringTemp2);
      }
      {
        String stringTemp = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          s,
          3);
        String stringTemp2 = (String)Reflect.InvokeStatic(
          URIUtilityName,
          "escapeURI",
          (String)Reflect.InvokeStatic(URIUtilityName, "escapeURI", s, 3),
          3);
        Assert.assertEquals(stringTemp, stringTemp2);
      }
    }

    private static void assertResolve(String src, String baseuri, String dest) {
      assertIdempotency(src);
      assertIdempotency(baseuri);
      assertIdempotency(dest);
      String res = (String)Reflect.InvokeStatic(
    URIUtilityName,
    "relativeResolve",
    src,
    baseuri);
      assertIdempotency(res);
      Assert.assertEquals(dest, res);
    }

    @Test(timeout = 5000)
    public void testRelativeResolve() {
      assertResolve(
    "index.html",
    "http://example.com",
    "http://example.com/index.html");
      assertResolve(
          "./.x",
          "http://example.com/a/b/c/d/e.f",
          "http://example.com/a/b/c/d/.x");
      assertResolve(
          ".x",
          "http://example.com/a/b/c/d/e.f",
          "http://example.com/a/b/c/d/.x");
      assertResolve(
          "../.x",
          "http://example.com/a/b/c/d/e.f",
          "http://example.com/a/b/c/.x");
      assertResolve(
          "../..../../../.../.x",
          "http://example.com/a/b/c/d/e.f",
          "http://example.com/a/b/.../.x");
    }

    @Test(timeout = 5000)
    public void testUri() {
      assertIdempotency("");
      assertIdempotency("e");
      assertIdempotency("e:x");
      assertIdempotency("e://x:@y");
      assertIdempotency("e://x/y");
      assertIdempotency("e://x//y");
      assertIdempotency("a://x:@y/z");
      assertIdempotency("a://x:/y");
      assertIdempotency("aa:/w/x");
      assertIdempotency("01/w/x");
      assertIdempotency("e://x");
      assertIdempotency("e://x/:/");
      assertIdempotency("x/:/");
      assertIdempotency("/:/");
      assertIdempotency("///");
      assertIdempotency("e://x:");
      assertIdempotency("e://x:%30");
      assertIdempotency("a://x:?x");
      assertIdempotency("a://x#x");
      assertIdempotency("a://x?x");
      assertIdempotency("a://x:#x");
      assertIdempotency("e://x@x");
      assertIdempotency("e://x@:");
      assertIdempotency("e://x@:?");
      assertIdempotency("e://x@:#");
      assertIdempotency("//x@x");
      assertIdempotency("//x@:");
      assertIdempotency("//x@:?");
      assertIdempotency("//x@:#");
      assertIdempotency("//x@:?x");
      assertIdempotency("e://x@:#x");
      assertIdempotency("a://x:?x");
      assertIdempotency("a://x#x");
      assertIdempotency("a://x?x");
      assertIdempotency("a://x:#x");
      assertIdempotencyNeg("e://^//y");
      assertIdempotencyNeg("e^");
      assertIdempotencyNeg("e://x:a");
      assertIdempotencyNeg("a://x::/y");
      assertIdempotency("x@yz");
      assertIdempotencyNeg("x@y:z");
      assertIdempotencyNeg("01:/w/x");
      assertIdempotencyNeg("e://x:%30/");
      assertIdempotencyNeg("a://xxx@[");
      assertIdempotencyNeg("a://[");
      assertIdempotency("a://[va.a]");
      assertIdempotency("a://[v0.0]");
      assertIdempotency("a://x:/");
      assertIdempotency("a://[va.a]:/");
      assertIdempotencyNeg("a://x%/");
      assertIdempotencyNeg("a://x%xy/");
      assertIdempotencyNeg("a://x%x%/");
      assertIdempotencyNeg("a://x%%x/");
      assertIdempotency("a://x%20/");
      assertIdempotency("a://[v0.0]/");
      assertIdempotencyNeg("a://[wa.a]");
      assertIdempotencyNeg("a://[w0.0]");
      assertIdempotencyNeg("a://[va.a/");
      assertIdempotencyNeg("a://[v.a]");
      assertIdempotencyNeg("a://[va.]");
      assertIPv6("a:a:a:a:a:a:100.100.100.100");
      assertIPv6("::a:a:a:a:a:100.100.100.100");
      assertIPv6("::a:a:a:a:a:99.255.240.10");
      assertIPv6("::a:a:a:a:99.255.240.10");
      assertIPv6("::99.255.240.10");
      assertIPv6Neg("99.255.240.10");
      assertIPv6("a:a:a:a:a::99.255.240.10");
      assertIPv6("a:a:a:a:a:a:a:a");
      assertIPv6("aaa:a:a:a:a:a:a:a");
      assertIPv6("aaaa:a:a:a:a:a:a:a");
      assertIPv6("::a:a:a:a:a:a:a");
      assertIPv6("a::a:a:a:a:a:a");
      assertIPv6("a:a::a:a:a:a:a");
      assertIPv6("a:a:a::a:a:a:a");
      assertIPv6("a:a:a:a::a:a:a");
      assertIPv6("a:a:a:a:a::a:a");
      assertIPv6("a:a:a:a:a:a::a");
      assertIPv6("a::a");
      assertIPv6("::a");
      assertIPv6("::a:a");
      assertIPv6("::");
      assertIPv6("a:a:a:a:a:a:a::");
      assertIPv6("a:a:a:a:a:a::");
      assertIPv6("a:a:a:a:a::");
      assertIPv6("a:a:a:a::");
      assertIPv6("a:a::");
      assertIPv6Neg("a:a::a:a:a:a:a:a");
      assertIPv6Neg("aaaaa:a:a:a:a:a:a:a");
      assertIPv6Neg("a:a:a:a:a:a:a:100.100.100.100");
      assertIPv6Neg("a:a:a:a:a:a::99.255.240.10");
      assertIPv6Neg(":::a:a:a:a:a:a:a");
      assertIPv6Neg("a:a:a:a:a:a:::a");
      assertIPv6Neg("a:a:a:a:a:a:a:::");
      assertIPv6Neg("::a:a:a:a:a:a:a:");
      assertIPv6Neg("::a:a:a:a:a:a:a:a");
      assertIPv6Neg("a:a");
      assertIdempotency("e://[va.a]");
      assertIdempotency("e://[v0.0]");
      assertIdempotencyNeg("e://[wa.a]");
      assertIdempotencyNeg("e://[va.^]");
      assertIdempotencyNeg("e://[va.]");
      assertIdempotencyNeg("e://[v.a]");
    }

    private static void assertIPv6Neg(String str) {
      assertIdempotencyNeg("e://[" + str + "]");
      assertIdempotencyNeg("e://[" + str + "NANA]");
      assertIdempotencyNeg("e://[" + str + "%25]");
      assertIdempotencyNeg("e://[" + str + "%NANA]");
      assertIdempotencyNeg("e://[" + str + "%25NANA]");
      assertIdempotencyNeg("e://[" + str + "%52NANA]");
      assertIdempotencyNeg("e://[" + str + "%25NA<>NA]");
      assertIdempotencyNeg("e://[" + str + "%25NA%E2NA]");
      assertIdempotencyNeg("e://[" + str + "%25NA%2ENA]");
    }

    private static void assertIPv6(String str) {
      assertIdempotency("e://[" + str + "]");
      assertIdempotencyNeg("e://[" + str + "NANA]");
      assertIdempotencyNeg("e://[" + str + "%25]");
      assertIdempotencyNeg("e://[" + str + "%NANA]");
      assertIdempotency("e://[" + str + "%25NANA]");
      assertIdempotencyNeg("e://[" + str + "%52NANA]");
      assertIdempotencyNeg("e://[" + str + "%25NA<>NA]");
      assertIdempotency("e://[" + str + "%25NA%E2NA]");
      assertIdempotency("e://[" + str + "%25NA%2ENA]");
    }
  }
