package com.upokecenter.cbor;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

    /**
     * Implements arithmetic operations with CBOR objects.
     */
  final class CBORObjectMath {
private CBORObjectMath() {
}
    public static CBORObject Addition(CBORObject a, CBORObject b) {
      if (a == null) {
        throw new NullPointerException("a");
      }
      if (b == null) {
        throw new NullPointerException("b");
      }
      if (!a.isLegacyNumber()) {
        throw new IllegalArgumentException("a.Type (" + a.getType() +
          ") does not indicate a number");
      }
      if (!b.isLegacyNumber()) {
        throw new IllegalArgumentException("b.Type (" + b.getType() +
          ") does not indicate a number");
      }
      Object objA = a.getThisItem();
      Object objB = b.getThisItem();
      int typeA = a.getItemType();
      int typeB = b.getItemType();
      if (typeA == CBORObject.CBORObjectTypeInteger && typeB ==
      CBORObject.CBORObjectTypeInteger) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if ((valueA < 0 && valueB < Long.MIN_VALUE - valueA) ||
                (valueA > 0 && valueB > Long.MAX_VALUE - valueA)) {
          // would overflow, convert to EInteger
          return CBORObject.FromObject((EInteger.FromInt64(valueA)).Add(EInteger.FromInt64(valueB)));
        }
        return CBORObject.FromObject(valueA + valueB);
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedRational ||
             typeB == CBORObject.CBORObjectTypeExtendedRational) {
        ERational e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedRational(objA);
        ERational e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedRational(objB);
        return CBORObject.FromObject(e1.Add(e2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedDecimal ||
             typeB == CBORObject.CBORObjectTypeExtendedDecimal) {
        EDecimal e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedDecimal(objA);
        EDecimal e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedDecimal(objB);
        return CBORObject.FromObject(e1.Add(e2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedFloat || typeB ==
      CBORObject.CBORObjectTypeExtendedFloat ||
               typeA == CBORObject.CBORObjectTypeDouble || typeB ==
               CBORObject.CBORObjectTypeDouble ||
               typeA == CBORObject.CBORObjectTypeSingle || typeB ==
               CBORObject.CBORObjectTypeSingle) {
        EFloat e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedFloat(objA);
        EFloat e2 = CBORObject.GetNumberInterface(typeB).AsExtendedFloat(objB);
        return CBORObject.FromObject(e1.Add(e2));
      } else {
        EInteger b1 = CBORObject.GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = CBORObject.GetNumberInterface(typeB).AsEInteger(objB);
        return CBORObject.FromObject(b1.Add(b2));
      }
    }

    public static CBORObject Subtract(CBORObject a, CBORObject b) {
      if (a == null) {
        throw new NullPointerException("a");
      }
      if (b == null) {
        throw new NullPointerException("b");
      }
      if (!a.isLegacyNumber()) {
        throw new IllegalArgumentException("a.Type (" + a.getType() +
          ") does not indicate a number");
      }
      if (!b.isLegacyNumber()) {
        throw new IllegalArgumentException("b.Type (" + b.getType() +
          ") does not indicate a number");
      }
      Object objA = a.getThisItem();
      Object objB = b.getThisItem();
      int typeA = a.getItemType();
      int typeB = b.getItemType();
      if (typeA == CBORObject.CBORObjectTypeInteger && typeB ==
      CBORObject.CBORObjectTypeInteger) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if ((valueB < 0 && Long.MAX_VALUE + valueB < valueA) ||
                (valueB > 0 && Long.MIN_VALUE + valueB > valueA)) {
          // would overflow, convert to EInteger
          return CBORObject.FromObject((EInteger.FromInt64(valueA)).Subtract(EInteger.FromInt64(valueB)));
        }
        return CBORObject.FromObject(valueA - valueB);
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedRational ||
             typeB == CBORObject.CBORObjectTypeExtendedRational) {
        ERational e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedRational(objA);
        ERational e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedRational(objB);
        return CBORObject.FromObject(e1.Subtract(e2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedDecimal ||
             typeB == CBORObject.CBORObjectTypeExtendedDecimal) {
        EDecimal e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedDecimal(objA);
        EDecimal e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedDecimal(objB);
        return CBORObject.FromObject(e1.Subtract(e2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedFloat || typeB ==
      CBORObject.CBORObjectTypeExtendedFloat ||
               typeA == CBORObject.CBORObjectTypeDouble || typeB ==
               CBORObject.CBORObjectTypeDouble ||
               typeA == CBORObject.CBORObjectTypeSingle || typeB ==
               CBORObject.CBORObjectTypeSingle) {
        EFloat e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedFloat(objA);
        EFloat e2 = CBORObject.GetNumberInterface(typeB).AsExtendedFloat(objB);
        return CBORObject.FromObject(e1.Subtract(e2));
      } else {
        EInteger b1 = CBORObject.GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = CBORObject.GetNumberInterface(typeB).AsEInteger(objB);
        return CBORObject.FromObject(b1.Subtract(b2));
      }
    }

    public static CBORObject Multiply(CBORObject a, CBORObject b) {
      if (a == null) {
        throw new NullPointerException("a");
      }
      if (b == null) {
        throw new NullPointerException("b");
      }
      if (!a.isLegacyNumber()) {
        throw new IllegalArgumentException("a.Type (" + a.getType() +
          ") does not indicate a number");
      }
      if (!b.isLegacyNumber()) {
        throw new IllegalArgumentException("b.Type (" + b.getType() +
          ") does not indicate a number");
      }
      Object objA = a.getThisItem();
      Object objB = b.getThisItem();
      int typeA = a.getItemType();
      int typeB = b.getItemType();
      if (typeA == CBORObject.CBORObjectTypeInteger && typeB ==
      CBORObject.CBORObjectTypeInteger) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        boolean apos = valueA > 0L;
        boolean bpos = valueB > 0L;
        if (
          (apos && ((!bpos && (Long.MIN_VALUE / valueA) > valueB) ||
          (bpos && valueA > (Long.MAX_VALUE / valueB)))) ||
          (!apos && ((!bpos && valueA != 0L &&
          (Long.MAX_VALUE / valueA) > valueB) ||
          (bpos && valueA < (Long.MIN_VALUE / valueB))))) {
          // would overflow, convert to EInteger
          EInteger bvalueA = EInteger.FromInt64(valueA);
          EInteger bvalueB = EInteger.FromInt64(valueB);
          return CBORObject.FromObject(bvalueA.Multiply(bvalueB));
        }
        return CBORObject.FromObject(valueA * valueB);
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedRational ||
             typeB == CBORObject.CBORObjectTypeExtendedRational) {
        ERational e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedRational(objA);
        ERational e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedRational(objB);
        return CBORObject.FromObject(e1.Multiply(e2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedDecimal ||
             typeB == CBORObject.CBORObjectTypeExtendedDecimal) {
        EDecimal e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedDecimal(objA);
        EDecimal e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedDecimal(objB);
        return CBORObject.FromObject(e1.Multiply(e2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedFloat || typeB ==
      CBORObject.CBORObjectTypeExtendedFloat ||
               typeA == CBORObject.CBORObjectTypeDouble || typeB ==
               CBORObject.CBORObjectTypeDouble ||
               typeA == CBORObject.CBORObjectTypeSingle || typeB ==
               CBORObject.CBORObjectTypeSingle) {
        EFloat e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedFloat(objA);
        EFloat e2 = CBORObject.GetNumberInterface(typeB).AsExtendedFloat(objB);
        return CBORObject.FromObject(e1.Multiply(e2));
      } else {
        EInteger b1 = CBORObject.GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = CBORObject.GetNumberInterface(typeB).AsEInteger(objB);
        return CBORObject.FromObject(b1.Multiply(b2));
      }
    }

    public static CBORObject Divide(CBORObject a, CBORObject b) {
      if (a == null) {
        throw new NullPointerException("a");
      }
      if (b == null) {
        throw new NullPointerException("b");
      }
      if (!a.isLegacyNumber()) {
        throw new IllegalArgumentException("a.Type (" + a.getType() +
          ") does not indicate a number");
      }
      if (!b.isLegacyNumber()) {
        throw new IllegalArgumentException("b.Type (" + b.getType() +
          ") does not indicate a number");
      }
      Object objA = a.getThisItem();
      Object objB = b.getThisItem();
      int typeA = a.getItemType();
      int typeB = b.getItemType();
      if (typeA == CBORObject.CBORObjectTypeInteger && typeB ==
      CBORObject.CBORObjectTypeInteger) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        if (valueB == 0) {
          return (valueA == 0) ? CBORObject.NaN : ((valueA < 0) ?
            CBORObject.NegativeInfinity : CBORObject.PositiveInfinity);
        }
        if (valueA == Long.MIN_VALUE && valueB == -1) {
          return CBORObject.FromObject(valueA).Negate();
        }
        long quo = valueA / valueB;
        long rem = valueA - (quo * valueB);
        return (rem == 0) ? CBORObject.FromObject(quo) :
        CBORObject.FromObject(
  ERational.Create(
  EInteger.FromInt64(valueA),
  EInteger.FromInt64(valueB)));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedRational ||
             typeB == CBORObject.CBORObjectTypeExtendedRational) {
        ERational e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedRational(objA);
        ERational e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedRational(objB);
        return CBORObject.FromObject(e1.Divide(e2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedDecimal ||
             typeB == CBORObject.CBORObjectTypeExtendedDecimal) {
        EDecimal e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedDecimal(objA);
        EDecimal e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedDecimal(objB);
        if (e1.isZero() && e2.isZero()) {
          return CBORObject.NaN;
        }
        EDecimal eret = e1.Divide(e2, null);
        // If either operand is infinity or NaN, the result
        // is already exact. Likewise if the result is a finite number.
        if (!e1.isFinite() || !e2.isFinite() || eret.isFinite()) {
          return CBORObject.FromObject(eret);
        }
        ERational er1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedRational(objA);
        ERational er2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedRational(objB);
        return CBORObject.FromObject(er1.Divide(er2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedFloat || typeB ==
      CBORObject.CBORObjectTypeExtendedFloat ||
               typeA == CBORObject.CBORObjectTypeDouble || typeB ==
               CBORObject.CBORObjectTypeDouble ||
               typeA == CBORObject.CBORObjectTypeSingle || typeB ==
               CBORObject.CBORObjectTypeSingle) {
        EFloat e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedFloat(objA);
        EFloat e2 = CBORObject.GetNumberInterface(typeB).AsExtendedFloat(objB);
        if (e1.isZero() && e2.isZero()) {
          return CBORObject.NaN;
        }
        EFloat eret = e1.Divide(e2, null);
        // If either operand is infinity or NaN, the result
        // is already exact. Likewise if the result is a finite number.
        if (!e1.isFinite() || !e2.isFinite() || eret.isFinite()) {
          return CBORObject.FromObject(eret);
        }
        ERational er1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedRational(objA);
        ERational er2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedRational(objB);
        return CBORObject.FromObject(er1.Divide(er2));
      } else {
        EInteger b1 = CBORObject.GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = CBORObject.GetNumberInterface(typeB).AsEInteger(objB);
        if (b2.isZero()) {
          return b1.isZero() ? CBORObject.NaN : ((b1.signum() < 0) ?
            CBORObject.NegativeInfinity : CBORObject.PositiveInfinity);
        }
        EInteger bigrem;
        EInteger bigquo;
        {
          EInteger[] divrem = b1.DivRem(b2);
          bigquo = divrem[0];
          bigrem = divrem[1]; }
        return bigrem.isZero() ? CBORObject.FromObject(bigquo) :
        CBORObject.FromObject(ERational.Create(b1, b2));
      }
    }

    public static CBORObject Remainder(CBORObject a, CBORObject b) {
      if (a == null) {
        throw new NullPointerException("a");
      }
      if (b == null) {
        throw new NullPointerException("b");
      }
      if (!a.isLegacyNumber()) {
        throw new IllegalArgumentException("a.Type (" + a.getType() +
          ") does not indicate a number");
      }
      if (!b.isLegacyNumber()) {
        throw new IllegalArgumentException("b.Type (" + b.getType() +
          ") does not indicate a number");
      }
      Object objA = a.getThisItem();
      Object objB = b.getThisItem();
      int typeA = a.getItemType();
      int typeB = b.getItemType();
      if (typeA == CBORObject.CBORObjectTypeInteger && typeB ==
      CBORObject.CBORObjectTypeInteger) {
        long valueA = (((Long)objA).longValue());
        long valueB = (((Long)objB).longValue());
        return (valueA == Long.MIN_VALUE && valueB == -1) ?
        CBORObject.FromObject(0) : CBORObject.FromObject(valueA % valueB);
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedRational ||
             typeB == CBORObject.CBORObjectTypeExtendedRational) {
        ERational e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedRational(objA);
        ERational e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedRational(objB);
        return CBORObject.FromObject(e1.Remainder(e2));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedDecimal ||
             typeB == CBORObject.CBORObjectTypeExtendedDecimal) {
        EDecimal e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedDecimal(objA);
        EDecimal e2 =
        CBORObject.GetNumberInterface(typeB).AsExtendedDecimal(objB);
        return CBORObject.FromObject(e1.Remainder(e2, null));
      }
      if (typeA == CBORObject.CBORObjectTypeExtendedFloat || typeB ==
      CBORObject.CBORObjectTypeExtendedFloat ||
               typeA == CBORObject.CBORObjectTypeDouble || typeB ==
               CBORObject.CBORObjectTypeDouble ||
               typeA == CBORObject.CBORObjectTypeSingle || typeB ==
               CBORObject.CBORObjectTypeSingle) {
        EFloat e1 =
        CBORObject.GetNumberInterface(typeA).AsExtendedFloat(objA);
        EFloat e2 = CBORObject.GetNumberInterface(typeB).AsExtendedFloat(objB);
        return CBORObject.FromObject(e1.Remainder(e2, null));
      } else {
        EInteger b1 = CBORObject.GetNumberInterface(typeA).AsEInteger(objA);
        EInteger b2 = CBORObject.GetNumberInterface(typeB).AsEInteger(objB);
        return CBORObject.FromObject(b1.Remainder(b2));
      }
    }
  }
