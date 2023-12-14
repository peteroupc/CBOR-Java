package com.upokecenter.cbor;

import java.util.*;

import com.upokecenter.util.*;

  final class CBORJsonWriter {
private CBORJsonWriter() {
}
    private static final String Hex16 = "0123456789ABCDEF";

    static void WriteJSONStringUnquoted(
      String str,
      StringOutput sb,
      JSONOptions options) throws java.io.IOException {
      int i = 0;
      for (; i < str.length(); ++i) {
        char c = str.charAt(i);
        if (c < 0x20 || c >= 0x7f || c == '\\' || c == '"') {
          sb.WriteString(str, 0, i);
          break;
        }
      }
      if (i == str.length()) {
        sb.WriteString(str, 0, i);
        return;
      }
      for (; i < str.length(); ++i) {
        char c = str.charAt(i);
        if (c == '\\' || c == '"') {
          sb.WriteCodePoint((int)'\\');
          sb.WriteCodePoint((int)c);
        } else if (c < 0x20 || (c >= 0x7f && (c == 0x2028 || c == 0x2029 ||
              (c >= 0x7f && c <= 0xa0) || c == 0xfeff || c == 0xfffe ||
              c == 0xffff))) {
          // Control characters, and also the line and paragraph separators
          // which apparently can't appear in JavaScript (as opposed to
          // JSON) strings
          if (c == 0x0d) {
            sb.WriteString("\\r");
          } else if (c == 0x0a) {
            sb.WriteString("\\n");
          } else if (c == 0x08) {
            sb.WriteString("\\b");
          } else if (c == 0x0c) {
            sb.WriteString("\\f");
          } else if (c == 0x09) {
            sb.WriteString("\\t");
          } else if (c == 0x85) {
            sb.WriteString("\\u0085");
          } else if (c >= 0x100) {
            sb.WriteString("\\u");
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 12) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 8) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 4) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)(c & 15)));
          } else {
            sb.WriteString("\\u00");
            sb.WriteCodePoint((int)Hex16.charAt((int)(c >> 4)));
            sb.WriteCodePoint((int)Hex16.charAt((int)(c & 15)));
          }
        } else if ((c & 0xfc00) == 0xd800) {
          if (i >= str.length() - 1 || (str.charAt(i + 1) & 0xfc00) != 0xdc00) {
            // NOTE: RFC 8259 doesn't prohibit any particular
            // error-handling behavior when a writer of JSON
            // receives a String with an unpaired surrogate.
            if (options.getReplaceSurrogates()) {
              // Replace unpaired surrogate with U+FFFD
              sb.WriteCodePoint(0xfffd);
            } else {
              throw new CBORException("Unpaired surrogate in String");
            }
          } else if (c >= 0x80 && options.getWriteBasic()) {
            c = str.charAt(i);
            sb.WriteString("\\u");
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 12) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 8) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 4) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)(c & 15)));
            c = str.charAt(i + 1);
            sb.WriteString("\\u");
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 12) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 8) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 4) & 15)));
            sb.WriteCodePoint((int)Hex16.charAt((int)(c & 15)));
          } else {
            sb.WriteString(str, i, 2);
          }
          ++i;
        } else if (c >= 0x80 && options.getWriteBasic()) {
          sb.WriteString("\\u");
          sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 12) & 15)));
          sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 8) & 15)));
          sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 4) & 15)));
          sb.WriteCodePoint((int)Hex16.charAt((int)(c & 15)));
        } else {
          sb.WriteCodePoint((int)c);
        }
      }
    }

    static void WriteJSONToInternal(
      CBORObject obj,
      StringOutput writer,
      JSONOptions options) throws java.io.IOException {
      if (obj.getType() == CBORType.Array || obj.getType() == CBORType.Map) {
        ArrayList<CBORObject> stack = new ArrayList<CBORObject>();
        WriteJSONToInternal(obj, writer, options, stack);
      } else {
        WriteJSONToInternal(obj, writer, options, null);
      }
    }

    private static void PopRefIfNeeded(
      List<CBORObject> stack,
      boolean pop) {
      if (pop && stack != null) {
        stack.remove(stack.size() - 1);
      }
    }

    private static boolean CheckCircularRef(
      List<CBORObject> stack,
      CBORObject parent,
      CBORObject child) {
      if (child.getType() != CBORType.Array && child.getType() != CBORType.Map) {
        return false;
      }
      CBORObject childUntag = child.Untag();
      if (parent.Untag() == childUntag) {
        throw new CBORException("Circular reference in CBOR Object");
      }
      if (stack != null) {
        for (CBORObject o : stack) {
          if (o.Untag() == childUntag) {
            throw new CBORException("Circular reference in CBOR Object");
          }
        }
      }
      stack.add(child);
      return true;
    }

    static void WriteJSONToInternal(
      CBORObject obj,
      StringOutput writer,
      JSONOptions options,
      List<CBORObject> stack) throws java.io.IOException {
      if (obj.isNumber()) {
        writer.WriteString(CBORNumber.FromCBORObject(obj).ToJSONString());
        return;
      }
      switch (obj.getType()) {
        case Integer:
        case FloatingPoint: {
          CBORObject untaggedObj = obj.Untag();
          writer.WriteString(
            CBORNumber.FromCBORObject(untaggedObj).ToJSONString());
          break;
        }
        case Boolean: {
          if (obj.isTrue()) {
            writer.WriteString("true");
            return;
          }
          if (obj.isFalse()) {
            writer.WriteString("false");
            return;
          }
          return;
        }
        case SimpleValue: {
          writer.WriteString("null");
          return;
        }
        case ByteString: {
          byte[] byteArray = obj.GetByteString();
          if (byteArray.length == 0) {
            writer.WriteString("\"\"");
            return;
          }
          writer.WriteCodePoint((int)'\"');
          if (obj.HasTag(22)) {
            // Base64 with padding
            Base64.WriteBase64(
              writer,
              byteArray,
              0,
              byteArray.length,
              true);
          } else if (obj.HasTag(23)) {
            // Write as base16
            for (int i = 0; i < byteArray.length; ++i) {
              writer.WriteCodePoint((int)Hex16.charAt((byteArray[i] >> 4) & 15));
              writer.WriteCodePoint((int)Hex16.charAt(byteArray[i] & 15));
            }
          } else {
            // Base64url no padding
            Base64.WriteBase64URL(
              writer,
              byteArray,
              0,
              byteArray.length,
              false);
          }
          writer.WriteCodePoint((int)'\"');
          break;
        }
        case TextString: {
          String thisString = obj.AsString();
          if (thisString.length() == 0) {
            writer.WriteString("\"\"");
            return;
          }
          writer.WriteCodePoint((int)'\"');
          WriteJSONStringUnquoted(thisString, writer, options);
          writer.WriteCodePoint((int)'\"');
          break;
        }
        case Array: {
          writer.WriteCodePoint((int)'[');
          for (int i = 0; i < obj.size(); ++i) {
            if (i > 0) {
              writer.WriteCodePoint((int)',');
            }
            boolean pop = CheckCircularRef(stack, obj, obj.get(i));
            WriteJSONToInternal(obj.get(i), writer, options, stack);
            PopRefIfNeeded(stack, pop);
          }
          writer.WriteCodePoint((int)']');
          break;
        }
        case Map: {
          boolean first = true;
          boolean hasNonStringKeys = false;
          Collection<Map.Entry<CBORObject, CBORObject>> entries =
            obj.getEntries();
          for (Map.Entry<CBORObject, CBORObject> entry : entries) {
            CBORObject key = entry.getKey();
            if (key.getType() != CBORType.TextString ||
              key.isTagged()) {
              // treat a non-text-String item or a tagged item
              // as having non-String keys
              hasNonStringKeys = true;
              break;
            }
          }
          if (!hasNonStringKeys) {
            writer.WriteCodePoint((int)'{');
            for (Map.Entry<CBORObject, CBORObject> entry : entries) {
              CBORObject key = entry.getKey();
              CBORObject value = entry.getValue();
              if (!first) {
                writer.WriteCodePoint((int)',');
              }
              writer.WriteCodePoint((int)'\"');
              WriteJSONStringUnquoted(key.AsString(), writer, options);
              writer.WriteCodePoint((int)'\"');
              writer.WriteCodePoint((int)':');
              boolean pop = CheckCircularRef(stack, obj, value);
              WriteJSONToInternal(value, writer, options, stack);
              PopRefIfNeeded(stack, pop);
              first = false;
            }
            writer.WriteCodePoint((int)'}');
          } else {
            // This map has non-String keys
            Map<String, CBORObject> stringMap = new
            HashMap<String, CBORObject>();
            // Copy to a map with String keys, since
            // some keys could be duplicates
            // when serialized to strings
            for (Map.Entry<CBORObject, CBORObject> entry : entries) {
              CBORObject key = entry.getKey();
              CBORObject value = entry.getValue();
              String str = null;
              switch (key.getType()) {
                case TextString:
                  str = key.AsString();
                  break;
                case Array:
                case Map: {
                  StringBuilder sb = new StringBuilder();
                  StringOutput sw = new StringOutput(sb);
                  boolean pop = CheckCircularRef(stack, obj, key);
                  WriteJSONToInternal(key, sw, options, stack);
                  PopRefIfNeeded(stack, pop);
                  str = sb.toString();
                  break;
                }
                default: str = key.ToJSONString(options);
                  break;
              }
              if (stringMap.containsKey(str)) {
                throw new CBORException(
                  "Duplicate JSON String equivalents of map" +
                  "\u0020keys");
              }
              stringMap.put(str, value);
            }
            first = true;
            writer.WriteCodePoint((int)'{');
            for (Map.Entry<String, CBORObject> entry : stringMap.entrySet()) {
              String key = entry.getKey();
              CBORObject value = entry.getValue();
              if (!first) {
                writer.WriteCodePoint((int)',');
              }
              writer.WriteCodePoint((int)'\"');
              WriteJSONStringUnquoted((String)key, writer, options);
              writer.WriteCodePoint((int)'\"');
              writer.WriteCodePoint((int)':');
              boolean pop = CheckCircularRef(stack, obj, value);
              WriteJSONToInternal(value, writer, options, stack);
              PopRefIfNeeded(stack, pop);
              first = false;
            }
            writer.WriteCodePoint((int)'}');
          }
          break;
        }
        default:
          throw new IllegalStateException("Unexpected item" +
            "\u0020type");
      }
    }
  }
