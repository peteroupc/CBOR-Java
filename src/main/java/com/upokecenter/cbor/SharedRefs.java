package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */
import java.util.*;
import com.upokecenter.numbers.*;

  class SharedRefs {
    private final List<CBORObject> sharedObjects;

    public SharedRefs() {
      this.sharedObjects = new ArrayList<CBORObject>();
    }

    public void AddObject(CBORObject obj) {
      this.sharedObjects.add(obj);
    }

    public CBORObject GetObject(long smallIndex) {
      if (smallIndex < 0) {
        throw new CBORException("Unexpected index");
      }
      if (smallIndex > Integer.MAX_VALUE) {
        throw new CBORException("Index " + smallIndex +
          " is bigger than supported ");
      }
      int index = (int)smallIndex;
      if (index >= this.sharedObjects.size()) {
 throw new CBORException("Index " + index + " is not valid");
}
 return this.sharedObjects.get(index);
    }

    public CBORObject GetObject(EInteger bigIndex) {
      if (bigIndex.signum() < 0) {
        throw new CBORException("Unexpected index");
      }
      if (!bigIndex.CanFitInInt32()) {
        throw new CBORException("Index " + bigIndex +
          " is bigger than supported ");
      }
      int index = bigIndex.ToInt32Checked();
      if (index >= this.sharedObjects.size()) {
 throw new CBORException("Index " + index + " is not valid");
}
 return this.sharedObjects.get(index);
    }
  }
