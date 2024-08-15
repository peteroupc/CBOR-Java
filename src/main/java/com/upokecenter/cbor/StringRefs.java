package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under the Unlicense: https://unlicense.org/

 */

import java.util.*;
import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  /**
   * Implements CBOR string references, described at {@code
   * http://cbor.schmorp.de/stringref}.
   */
  class StringRefs {
    private final ArrayList<ArrayList<CBORObject>> stack;

    public StringRefs() {
      this.stack = new ArrayList<ArrayList<CBORObject>>();
      ArrayList<CBORObject> firstItem = new ArrayList<CBORObject>();
      this.stack.add(firstItem);
    }

    public void Push() {
      ArrayList<CBORObject> firstItem = new ArrayList<CBORObject>();
      this.stack.add(firstItem);
    }

    public void Pop() {
      this.stack.remove(this.stack.size() - 1);
    }

    public void AddStringIfNeeded(CBORObject str, int lengthHint) {
      boolean addStr = false;
      ArrayList<CBORObject> lastList = this.stack.get(this.stack.size() - 1);
      if (lastList.size() < 24) {
        addStr |= lengthHint >= 3;
      } else if (lastList.size() < 256) {
        addStr |= lengthHint >= 4;
      } else if (lastList.size() < 65536) {
        addStr |= lengthHint >= 5;
      } else {
        // NOTE: lastList's size can't be higher than (2^64)-1
        addStr |= lengthHint >= 7;
      }
      // NOTE: An additional branch, with lengthHint >= 11, would
      // be needed if the size could be higher than (2^64)-1
      if (addStr) {
        lastList.add(str);
      }
    }

    public CBORObject GetString(long smallIndex) {
      if (smallIndex < 0) {
        throw new CBORException("Unexpected index");
      }
      if (smallIndex > Integer.MAX_VALUE) {
        throw new CBORException("Index " + smallIndex +
          " is bigger than supported ");
      }
      int index = (int)smallIndex;
      ArrayList<CBORObject> lastList = this.stack.get(this.stack.size() - 1);
      if (index >= lastList.size()) {
        throw new CBORException("Index " + index + " is not valid");
      }
      CBORObject ret = lastList.get(index);
      // Byte strings are mutable, so make a copy
      return (ret.getType() == CBORType.ByteString) ?
        CBORObject.FromObject(ret.GetByteString()) : ret;
    }

    public CBORObject GetString(EInteger bigIndex) {
      if (bigIndex.signum() < 0) {
        throw new CBORException("Unexpected index");
      }
      if (!bigIndex.CanFitInInt32()) {
        throw new CBORException("Index " + bigIndex +
          " is bigger than supported ");
      }
      int index = bigIndex.ToInt32Checked();
      ArrayList<CBORObject> lastList = this.stack.get(this.stack.size() - 1);
      if (index >= lastList.size()) {
        throw new CBORException("Index " + index + " is not valid");
      }
      CBORObject ret = lastList.get(index);
      // Byte strings are mutable, so make a copy
      return (ret.getType() == CBORType.ByteString) ?
        CBORObject.FromObject(ret.GetByteString()) : ret;
    }
  }
