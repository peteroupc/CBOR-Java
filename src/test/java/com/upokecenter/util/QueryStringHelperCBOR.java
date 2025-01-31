package com.upokecenter.util;
// Written by Peter O.
// Any copyright to this work is released to the Public Domain.
// In case this is not possible, this work is also
// licensed under the Unlicense: https://unlicense.org/

import java.util.*;

import com.upokecenter.cbor.*;

  public final class QueryStringHelperCBOR {
    private QueryStringHelperCBOR() {
    }
    @SuppressWarnings("unchecked")
private static CBORObject ConvertListsToCBOR(List<Object> dict) {
      CBORObject cbor = CBORObject.NewArray();
      for (int i = 0; i < dict.size(); ++i) {
        Object di = dict.get(i);
        Map<String, Object> value = ((di instanceof Map<?, ?>) ? (Map<String, Object>)di : null);
        // A list contains only integer indices,
        // with no gaps.
        if (QueryStringHelper.IsList(value)) {
          List<Object> newList = QueryStringHelper.ConvertToList(value);
          cbor.Add(ConvertListsToCBOR(newList));
        } else if (value != null) {
          // Convert the list's descendents
          // if they are lists
          cbor.Add(ConvertListsToCBOR(value));
        } else {
          cbor.Add(dict.get(i));
        }
      }
      return cbor;
    }

    @SuppressWarnings("unchecked")
private static CBORObject ConvertListsToCBOR(Map<String, Object>
      dict) {
      CBORObject cbor = CBORObject.NewMap();
      for (String key : new ArrayList<String>(dict.keySet())) {
        Object di = dict.get(key);
        Map<String, Object> value = ((di instanceof Map<?, ?>) ? (Map<String, Object>)di : null);
        // A list contains only integer indices,
        // with no gaps.
        if (QueryStringHelper.IsList(value)) {
          List<Object> newList = QueryStringHelper.ConvertToList(value);
          cbor.Add(key, ConvertListsToCBOR(newList));
        } else if (value != null) {
          // Convert the dictionary's descendents
          // if they are lists
          cbor.Add(key, ConvertListsToCBOR(value));
        } else {
          cbor.Add(key, dict.get(key));
        }
      }
      return cbor;
    }

    public static CBORObject QueryStringToCBOR(String query) {
      return QueryStringToCBOR(query, "&");
    }
    public static CBORObject QueryStringToCBOR(String query,
      String delimiter) {
      // Convert array-like dictionaries to ILists
      return
        ConvertListsToCBOR(QueryStringHelper.QueryStringToDictInternal(query,
        delimiter));
    }
  }
