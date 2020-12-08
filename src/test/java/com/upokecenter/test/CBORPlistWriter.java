package com.upokecenter.test;

import java.util.*;
import java.io.*;

import com.upokecenter.util.*;
import com.upokecenter.cbor.*;

  final class CBORPlistWriter {
private CBORPlistWriter() {
}
    private static final String Hex16 = "0123456789ABCDEF";

    static void WritePlistStringUnquoted(
      String str,
      StringOutput sb,
      JSONOptions options) throws java.io.IOException {
      int i = 0;
      for (; i < str.length(); ++i) {
        char c = str.charAt(i);
        if (c < 0x20 || c >= 0x7f || c == '\\' || c == '"' || c == '&' ||
            c == '<' || c == '>') {
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
        // TODO: XML doesn't support all Unicode code points, even if escaped.
        // Therefore, replace all unsupported code points with replacement
        // characters.
        if (c == '\\' || c == '"') {
          sb.WriteCodePoint((int)'\\');
          sb.WriteCodePoint((int)c);
        } else if (c < 0x20 || c == '&' || c == '<' || c == '>' || (c >= 0x7f &&
            (c == 0x2028 || c == 0x2029 ||
              (c >= 0x7f && c <= 0xa0) || c == 0xfeff || c == 0xfffe ||
              c == 0xffff))) {
          sb.WriteString("&#x");
          sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 12) & 15)));
          sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 8) & 15)));
          sb.WriteCodePoint((int)Hex16.charAt((int)((c >> 4) & 15)));
          sb.WriteCodePoint((int)Hex16.charAt((int)(c & 15)));
          sb.WriteString(";");
        } else if ((c & 0xfc00) == 0xd800) {
          if (i >= str.length() - 1 || (str.charAt(i + 1) & 0xfc00) != 0xdc00) {
            if (options.getReplaceSurrogates()) {
              // Replace unpaired surrogate with U+FFFD
              sb.WriteCodePoint(0xfffd);
            } else {
              throw new CBORException("Unpaired surrogate in String");
            }
          } else {
            sb.WriteString(str, i, 2);
            ++i;
          }
        } else {
          sb.WriteCodePoint((int)c);
        }
      }
    }

    static String ToPlistString(CBORObject obj) {
      StringBuilder builder = new StringBuilder();
      try {
         WritePlistToInternal(
            obj,
            new StringOutput(builder),
            JSONOptions.Default);
         return builder.toString();
      } catch (IOException ex) {
         throw new CBORException(ex.getMessage(), ex);
      }
    }

    static void WritePlistToInternal(
      CBORObject obj,
      StringOutput writer,
      JSONOptions options) throws java.io.IOException {
      writer.WriteString("<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST");
      writer.WriteString(" 1.0//EN\" \"http://www.apple.com/DTDs/");
      writer.WriteString("PropertyList-1.0.dtd\"><plist version='1.0'>");
      WritePlistToInternalCore(obj, writer, options);
      writer.WriteString("</plist>");
    }

    static void WritePlistToInternalCore(
      CBORObject obj,
      StringOutput writer,
      JSONOptions options) throws java.io.IOException {
      if (obj.getType() == CBORType.Array || obj.getType() == CBORType.Map) {
        ArrayList<CBORObject> stack = new ArrayList<CBORObject>();
        WritePlistToInternalCore(obj, writer, options, stack);
      } else {
        WritePlistToInternalCore(obj, writer, options, null);
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

    // TODO: Support dates
    static void WritePlistToInternalCore(
      CBORObject obj,
      StringOutput writer,
      JSONOptions options,
      List<CBORObject> stack) throws java.io.IOException {
      if (obj.isNumber()) {
        if (obj.AsNumber().IsInteger()) {
          writer.WriteString("<integer>");
          writer.WriteString(obj.ToJSONString());
          writer.WriteString("</integer>");
        } else {
          writer.WriteString("<real>");
          writer.WriteString(obj.ToJSONString());
          writer.WriteString("</real>");
        }
        return;
      }
      switch (obj.getType()) {
        case Integer: {
          CBORObject untaggedObj = obj.Untag();
          writer.WriteString("<integer>");
          writer.WriteString(untaggedObj.ToJSONString());
          writer.WriteString("</integer>");
          break;
        }
        case FloatingPoint: {
          CBORObject untaggedObj = obj.Untag();
          writer.WriteString("<real>");
          writer.WriteString(untaggedObj.ToJSONString());
          writer.WriteString("</real>");
          break;
        }
        case Boolean: {
          if (obj.isTrue()) {
            writer.WriteString("<true/>");
            return;
          }
          if (obj.isFalse()) {
            writer.WriteString("<false/>");
            return;
          }
          return;
        }
        case SimpleValue: {
          writer.WriteString("<String>null</String>");
          return;
        }
        case ByteString: {
          byte[] byteArray = obj.GetByteString();
          if (byteArray.length == 0) {
            writer.WriteString("<data></data>");
            return;
          }
          if (obj.HasTag(22)) {
            writer.WriteString("<data>");
            // Base64 with padding
            Base64.WriteBase64(
              writer,
              byteArray,
              0,
              byteArray.length,
              true);
            writer.WriteString("</data>");
          } else if (obj.HasTag(23)) {
            writer.WriteString("<String>");
            // Write as base16
            for (int i = 0; i < byteArray.length; ++i) {
              writer.WriteCodePoint((int)Hex16.charAt((byteArray[i] >> 4) & 15));
              writer.WriteCodePoint((int)Hex16.charAt(byteArray[i] & 15));
            }
            writer.WriteString("</String>");
          } else {
            writer.WriteString("<data>");
            // Base64 with padding
            Base64.WriteBase64(
              writer,
              byteArray,
              0,
              byteArray.length,
              true);
            writer.WriteString("</data>");
          }
          break;
        }
        case TextString: {
          String thisString = obj.AsString();
          if (thisString.length() == 0) {
            writer.WriteString("<String></String>");
            return;
          }
          writer.WriteString("<String>");
          WritePlistStringUnquoted(thisString, writer, options);
          writer.WriteString("</String>");
          break;
        }
        case Array: {
          writer.WriteString("<array>");
          for (int i = 0; i < obj.size(); ++i) {
            boolean pop = CheckCircularRef(stack, obj, obj.get(i));
            WritePlistToInternalCore(obj.get(i), writer, options, stack);
            PopRefIfNeeded(stack, pop);
          }
          writer.WriteString("</array>");
          break;
        }
        case Map: {
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
            writer.WriteString("<dict>");
            for (Map.Entry<CBORObject, CBORObject> entry : entries) {
              CBORObject key = entry.getKey();
              CBORObject value = entry.getValue();
              writer.WriteString("<key>");
              WritePlistStringUnquoted(key.AsString(), writer, options);
              writer.WriteString("</key>");
              boolean pop = CheckCircularRef(stack, obj, value);
              WritePlistToInternalCore(value, writer, options, stack);
              PopRefIfNeeded(stack, pop);
            }
            writer.WriteString("</dict>");
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
                  WritePlistToInternalCore(key, sw, options, stack);
                  PopRefIfNeeded(stack, pop);
                  str = sb.toString();
                  break;
                }
                default: str = key.ToJSONString(options);
                  break;
              }
              if (stringMap.containsKey(str)) {
                throw new CBORException(
                  "Duplicate Plist String equivalents of map" +
                  "\u0020keys");
              }
              stringMap.put(str, value);
            }
            writer.WriteString("<dict>");
            for (Map.Entry<String, CBORObject> entry : stringMap.entrySet()) {
              String key = entry.getKey();
              CBORObject value = entry.getValue();
              writer.WriteString("<key>");
              WritePlistStringUnquoted((String)key, writer, options);
              writer.WriteString("</key>");
              boolean pop = CheckCircularRef(stack, obj, value);
              WritePlistToInternalCore(value, writer, options, stack);
              PopRefIfNeeded(stack, pop);
            }
            writer.WriteString("</dict>");
          }
          break;
        }
        default: throw new IllegalStateException("Unexpected item" +
            "\u0020type");
      }
    }
  }
