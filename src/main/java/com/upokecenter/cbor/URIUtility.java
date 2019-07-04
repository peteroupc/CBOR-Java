package com.upokecenter.cbor;

  final class URIUtility {
private URIUtility() {
}
    enum ParseMode {
    /**
     * The rules follow the syntax for parsing IRIs. In particular, many code
     * points outside the Basic Latin range (U + 0000 to U + 007F) are allowed.
     * Strings with unpaired surrogate code points are considered invalid.
     */
      IRIStrict,

    /**
     * The rules follow the syntax for parsing IRIs, except that code points
     * outside the Basic Latin range (U + 0000 to U + 007F) are not allowed.
     */
      URIStrict,

    /**
     * The rules only check for the appropriate delimiters when splitting the path,
     * without checking if all the characters in each component are valid.
     * Even with this mode, strings with unpaired surrogate code points are
     * considered invalid.
     */
      IRILenient,

    /**
     * The rules only check for the appropriate delimiters when splitting the path,
     * without checking if all the characters in each component are valid.
     * Code points outside the Basic Latin range (U + 0000 to U + 007F) are not
     * allowed.
     */
      URILenient,

    /**
     * The rules only check for the appropriate delimiters when splitting the path,
     * without checking if all the characters in each component are valid.
     * Unpaired surrogate code points are treated as though they were
     * replacement characters instead for the purposes of these rules, so
     * that strings with those code points are not considered invalid
     * strings.
     */
      IRISurrogateLenient,
    }

    private static final String HexChars = "0123456789ABCDEF";

    private static void AppendAuthority(
      StringBuilder builder,
      String refValue,
      int[] segments) {
      if (segments[2] >= 0) {
        builder.append("//");
        builder.append(
  refValue.substring(
    segments[2], (
    segments[2])+(segments[3] - segments[2])));
      }
    }

    private static void AppendFragment(
      StringBuilder builder,
      String refValue,
      int[] segments) {
      if (segments[8] >= 0) {
        builder.append('#');
        builder.append(
  refValue.substring(
    segments[8], (
    segments[8])+(segments[9] - segments[8])));
      }
    }

    private static void AppendNormalizedPath(
      StringBuilder builder,
      String refValue,
      int[] segments) {
      builder.append(
        NormalizePath(
  refValue.substring(
    segments[4], (
    segments[4])+(segments[5] - segments[4]))));
    }

    private static void AppendPath(
      StringBuilder builder,
      String refValue,
      int[] segments) {
      builder.append(
  refValue.substring(
    segments[4], (
    segments[4])+(segments[5] - segments[4])));
    }

    private static void AppendQuery(
      StringBuilder builder,
      String refValue,
      int[] segments) {
      if (segments[6] >= 0) {
        builder.append('?');
        builder.append(
  refValue.substring(
    segments[6], (
    segments[6])+(segments[7] - segments[6])));
      }
    }

    private static void AppendScheme(
      StringBuilder builder,
      String refValue,
      int[] segments) {
      if (segments[0] >= 0) {
        builder.append(
          refValue.substring(
            segments[0], (
            segments[0])+(segments[1] - segments[0])));
        builder.append(':');
      }
    }

    public static String EscapeURI(String s, int mode) {
      if (s == null) {
        return null;
      }
      int[] components = null;
      if (mode == 1) {
        components = (
          s == null) ? null : SplitIRI(
  s,
  0,
  s.length(),
  ParseMode.IRIStrict);
        if (components == null) {
          return null;
        }
      } else {
        components = (s == null) ? null : SplitIRI(
          s,
          0,
          s.length(),
          ParseMode.IRISurrogateLenient);
      }
      int index = 0;
      int valueSLength = s.length();
      StringBuilder builder = new StringBuilder();
      while (index < valueSLength) {
        int c = s.charAt(index);
        if ((c & 0xfc00) == 0xd800 && index + 1 < valueSLength &&
            (s.charAt(index + 1) & 0xfc00) == 0xdc00) {
         // Get the Unicode code point for the surrogate pair
          c = 0x10000 + ((c - 0xd800) << 10) + (s.charAt(index + 1) - 0xdc00);
          ++index;
        } else if ((c & 0xf800) == 0xd800) {
          c = 0xfffd;
        }
        if (mode == 0 || mode == 3) {
          if (c == '%' && mode == 3) {
           // Check for illegal percent encoding
            if (index + 2 >= valueSLength || !IsHexChar(s.charAt(index + 1)) ||
                !IsHexChar(s.charAt(index + 2))) {
              PercentEncodeUtf8(builder, c);
            } else {
              if (c <= 0xffff) {
                builder.append((char)c);
              } else if (c <= 0x10ffff) {
                builder.append((char)((((c - 0x10000) >> 10) & 0x3ff) +
                    0xd800));
                builder.append((char)(((c - 0x10000) & 0x3ff) + 0xdc00));
              }
            }
            ++index;
            continue;
          }
          if (c >= 0x7f || c <= 0x20 ||
              ((c & 0x7f) == c && "{}|^\\`<>\"".indexOf((char)c) >= 0)) {
            PercentEncodeUtf8(builder, c);
          } else if (c == '[' || c == ']') {
            if (components != null && index >= components[2] && index <
                components[3]) {
             // within the authority component, so don't percent-encode
              if (c <= 0xffff) {
                builder.append((char)c);
              } else if (c <= 0x10ffff) {
                builder.append((char)((((c - 0x10000) >> 10) & 0x3ff) +
                    0xd800));
                builder.append((char)(((c - 0x10000) & 0x3ff) + 0xdc00));
              }
            } else {
             // percent encode
              PercentEncodeUtf8(builder, c);
            }
          } else {
            if (c <= 0xffff) {
              builder.append((char)c);
            } else if (c <= 0x10ffff) {
              builder.append((char)((((c - 0x10000) >> 10) & 0x3ff) + 0xd800));
              builder.append((char)(((c - 0x10000) & 0x3ff) + 0xdc00));
            }
          }
        } else if (mode == 1 || mode == 2) {
          if (c >= 0x80) {
            PercentEncodeUtf8(builder, c);
          } else if (c == '[' || c == ']') {
            if (components != null && index >= components[2] && index <
                components[3]) {
             // within the authority component, so don't percent-encode
              if (c <= 0xffff) {
                builder.append((char)c);
              } else if (c <= 0x10ffff) {
                builder.append((char)((((c - 0x10000) >> 10) & 0x3ff) +
                    0xd800));
                builder.append((char)(((c - 0x10000) & 0x3ff) + 0xdc00));
              }
            } else {
             // percent encode
              PercentEncodeUtf8(builder, c);
            }
          } else {
            if (c <= 0xffff) {
              builder.append((char)c);
            } else if (c <= 0x10ffff) {
              builder.append((char)((((c - 0x10000) >> 10) & 0x3ff) + 0xd800));
              builder.append((char)(((c - 0x10000) & 0x3ff) + 0xdc00));
            }
          }
        }
        ++index;
      }
      return builder.toString();
    }

    public static boolean HasScheme(String refValue) {
      int[] segments = (refValue == null) ? null : SplitIRI(
        refValue,
        0,
        refValue.length(),
        ParseMode.IRIStrict);
      return segments != null && segments[0] >= 0;
    }

    public static boolean HasSchemeForURI(String refValue) {
      int[] segments = (refValue == null) ? null : SplitIRI(
        refValue,
        0,
        refValue.length(),
        ParseMode.URIStrict);
      return segments != null && segments[0] >= 0;
    }

    private static boolean IsHexChar(char c) {
      return (c >= 'a' && c <= 'f') ||
        (c >= 'A' && c <= 'F') || (c >= '0' && c <= '9');
    }

    private static int ToHex(char b1) {
      if (b1 >= '0' && b1 <= '9') {
        return b1 - '0';
      } else if (b1 >= 'A' && b1 <= 'F') {
        return b1 + 10 - 'A';
      } else {
        return (b1 >= 'a' && b1 <= 'f') ? (b1 + 10 - 'a') : 1;
      }
    }

    public static String PercentDecode(String str) {
      return (str == null) ? null : PercentDecode(str, 0, str.length());
    }

    public static String PercentDecode(String str, int index, int endIndex) {
      if (str == null) {
        return null;
      }
     // Quick check
      boolean quickCheck = true;
      int lastIndex = 0;
      int i = index;
      for (; i < endIndex; ++i) {
        if (str.charAt(i) >= 0xd800 || str.charAt(i) == '%') {
          quickCheck = false;
          lastIndex = i;
          break;
        }
      }
      if (quickCheck) {
        return str.substring(index, (index)+(endIndex - index));
      }
      StringBuilder retString = new StringBuilder();
      retString.append(str, index, (index)+(lastIndex));
      int cp = 0;
      int bytesSeen = 0;
      int bytesNeeded = 0;
      int lower = 0x80;
      int upper = 0xbf;
      int markedPos = -1;
      for (i = lastIndex; i < endIndex; ++i) {
        int c = str.charAt(i);
        if ((c & 0xfc00) == 0xd800 && i + 1 < endIndex &&
            (str.charAt(i + 1) & 0xfc00) == 0xdc00) {
         // Get the Unicode code point for the surrogate pair
          c = 0x10000 + ((c - 0xd800) << 10) + (str.charAt(i + 1) - 0xdc00);
          ++i;
        } else if ((c & 0xf800) == 0xd800) {
          c = 0xfffd;
        }
        if (c == '%') {
          if (i + 2 < endIndex) {
            int a = ToHex(str.charAt(i + 1));
            int b = ToHex(str.charAt(i + 2));
            if (a >= 0 && b >= 0) {
              b = (a * 16) + b;
              i += 2;
             // b now contains the byte read
              if (bytesNeeded == 0) {
               // this is the lead byte
                if (b < 0x80) {
                  retString.append((char)b);
                  continue;
                } else if (b >= 0xc2 && b <= 0xdf) {
                  markedPos = i;
                  bytesNeeded = 1;
                  cp = b - 0xc0;
                } else if (b >= 0xe0 && b <= 0xef) {
                  markedPos = i;
                  lower = (b == 0xe0) ? 0xa0 : 0x80;
                  upper = (b == 0xed) ? 0x9f : 0xbf;
                  bytesNeeded = 2;
                  cp = b - 0xe0;
                } else if (b >= 0xf0 && b <= 0xf4) {
                  markedPos = i;
                  lower = (b == 0xf0) ? 0x90 : 0x80;
                  upper = (b == 0xf4) ? 0x8f : 0xbf;
                  bytesNeeded = 3;
                  cp = b - 0xf0;
                } else {
                 // illegal byte in UTF-8
                  retString.append('\uFFFD');
                  continue;
                }
                cp <<= 6 * bytesNeeded;
                continue;
              } else {
               // this is a second or further byte
                if (b < lower || b > upper) {
                 // illegal trailing byte
                  cp = bytesNeeded = bytesSeen = 0;
                  lower = 0x80;
                  upper = 0xbf;
                  i = markedPos; // reset to the last marked position
                  retString.append('\uFFFD');
                  continue;
                }
               // reset lower and upper for the third
               // and further bytes
                lower = 0x80;
                upper = 0xbf;
                ++bytesSeen;
                cp += (b - 0x80) << (6 * (bytesNeeded - bytesSeen));
                markedPos = i;
                if (bytesSeen != bytesNeeded) {
                 // continue if not all bytes needed
                 // were read yet
                  continue;
                }
                int ret = cp;
                cp = 0;
                bytesSeen = 0;
                bytesNeeded = 0;
               // append the Unicode character
                if (ret <= 0xffff) {
                  retString.append((char)ret);
                } else {
                  retString.append((char)((((ret - 0x10000) >> 10) &
                        0x3ff) + 0xd800));
                  retString.append((char)(((ret - 0x10000) & 0x3ff) + 0xdc00));
                }
                continue;
              }
            }
          }
        }
        if (bytesNeeded > 0) {
         // we expected further bytes here,
         // so emit a replacement character instead
          bytesNeeded = 0;
          retString.append('\uFFFD');
        }
       // append the code point as is
        if (c <= 0xffff) {
          {
            retString.append((char)c);
          }
        } else if (c <= 0x10ffff) {
          retString.append((char)((((c - 0x10000) >> 10) & 0x3ff) + 0xd800));
          retString.append((char)(((c - 0x10000) & 0x3ff) + 0xdc00));
        }
      }
      if (bytesNeeded > 0) {
       // we expected further bytes here,
       // so emit a replacement character instead
        bytesNeeded = 0;
        retString.append('\uFFFD');
      }
      return retString.toString();
    }

    public static String EncodeStringForURI(String s) {
      if (s == null) {
        throw new NullPointerException("s");
      }
      int index = 0;
      StringBuilder builder = new StringBuilder();
      while (index < s.length()) {
        int c = s.charAt(index);
        if ((c & 0xfc00) == 0xd800 && index + 1 < s.length() &&
            (s.charAt(index + 1) & 0xfc00) == 0xdc00) {
         // Get the Unicode code point for the surrogate pair
          c = 0x10000 + ((c - 0xd800) << 10) + (s.charAt(index + 1) - 0xdc00);
        } else if ((c & 0xf800) == 0xd800) {
          c = 0xfffd;
        }
        if (c >= 0x10000) {
          ++index;
        }
        if ((c & 0x7F) == c && ((c >= 'A' && c <= 'Z') ||
                (c >= 'a' && c <= 'z') ||
                    (c >= '0' && c <= '9') || "-_.~".indexOf((char)c) >= 0)) {
          builder.append((char)c);
          ++index;
        } else {
          PercentEncodeUtf8(builder, c);
          ++index;
        }
      }
      return builder.toString();
    }

    private static boolean IsIfragmentChar(int c) {
     // '%' omitted
      return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
        (c >= '0' && c <= '9') ||
        ((c & 0x7F) == c && "/?-._~:@!$&'()*+,;=".indexOf((char)c) >= 0) ||
        (c >= 0xa0 && c <= 0xd7ff) || (c >= 0xf900 && c <= 0xfdcf) ||
        (c >= 0xfdf0 && c <= 0xffef) ||
        (c >= 0xe1000 && c <= 0xefffd) || (c >= 0x10000 && c <= 0xdfffd && (c &
          0xfffe) != 0xfffe);
    }

    private static boolean IsIpchar(int c) {
     // '%' omitted
      return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
        (c >= '0' && c <= '9') ||
        ((c & 0x7F) == c && "/-._~:@!$&'()*+,;=".indexOf((char)c) >= 0) ||
        (c >= 0xa0 && c <= 0xd7ff) || (c >= 0xf900 && c <= 0xfdcf) ||
        (c >= 0xfdf0 && c <= 0xffef) ||
        (c >= 0xe1000 && c <= 0xefffd) || (c >= 0x10000 && c <= 0xdfffd && (c &
          0xfffe) != 0xfffe);
    }

    private static boolean IsIqueryChar(int c) {
     // '%' omitted
      return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
        (c >= '0' && c <= '9') ||
        ((c & 0x7F) == c && "/?-._~:@!$&'()*+,;=".indexOf((char)c) >= 0) ||
        (c >= 0xa0 && c <= 0xd7ff) || (c >= 0xe000 && c <= 0xfdcf) ||
        (c >= 0xfdf0 && c <= 0xffef) ||
        (c >= 0x10000 && c <= 0x10fffd && (c & 0xfffe) != 0xfffe &&
           !(c >= 0xe0000 && c <= 0xe0fff));
    }

    private static boolean IsIRegNameChar(int c) {
     // '%' omitted
      return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
        (c >= '0' && c <= '9') ||
        ((c & 0x7F) == c && "-._~!$&'()*+,;=".indexOf((char)c) >= 0) ||
        (c >= 0xa0 && c <= 0xd7ff) || (c >= 0xf900 && c <= 0xfdcf) ||
        (c >= 0xfdf0 && c <= 0xffef) ||
        (c >= 0xe1000 && c <= 0xefffd) || (c >= 0x10000 && c <= 0xdfffd && (c &
          0xfffe) != 0xfffe);
    }

    private static boolean IsIUserInfoChar(int c) {
     // '%' omitted
      return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
        (c >= '0' && c <= '9') ||
        ((c & 0x7F) == c && "-._~:!$&'()*+,;=".indexOf((char)c) >= 0) ||
        (c >= 0xa0 && c <= 0xd7ff) || (c >= 0xf900 && c <= 0xfdcf) ||
        (c >= 0xfdf0 && c <= 0xffef) ||
        (c >= 0xe1000 && c <= 0xefffd) || (c >= 0x10000 && c <= 0xdfffd && (c &
          0xfffe) != 0xfffe);
    }

    public static boolean IsValidCurieReference(String s, int offset, int length) {
      if (s == null) {
        return false;
      }
      if (offset < 0) {
        throw new ArgumentException("offset (" + offset + ") is less than " +
               "0 ");
      }
      if (offset > s.length()) {
        throw new ArgumentException("offset (" + offset + ") is more than " +
          s.length());
      }
      if (length < 0) {
        throw new ArgumentException(
          "length (" + length + ") is less than " + "0 ");
      }
      if (length > s.length()) {
        throw new ArgumentException(
          "length (" + length + ") is more than " + s.length());
      }
      if (s.length() - offset < length) {
        throw new ArgumentException(
          "s's length minus " + offset + " (" + (s.length() - offset) +
          ") is less than " + length);
      }
      if (length == 0) {
        return true;
      }
      int index = offset;
      int valueSLength = offset + length;
      int state = 0;
      if (index + 2 <= valueSLength && s.charAt(index) == '/' && s.charAt(index + 1) == '/') {
       // has an authority, which is not allowed
        return false;
      }
      state = 0; // IRI Path
      while (index < valueSLength) {
       // Get the next Unicode character
        int c = s.charAt(index);
        if ((c & 0xfc00) == 0xd800 && index + 1 < valueSLength &&
            (s.charAt(index + 1) & 0xfc00) == 0xdc00) {
         // Get the Unicode code point for the surrogate pair
          c = 0x10000 + ((c - 0xd800) << 10) + (s.charAt(index + 1) - 0xdc00);
          ++index;
        } else if ((c & 0xf800) == 0xd800) {
         // error
          return false;
        }
        if (c == '%') {
         // Percent encoded character
          if (index + 2 < valueSLength && IsHexChar(s.charAt(index + 1)) &&
              IsHexChar(s.charAt(index + 2))) {
            index += 3;
            continue;
          }
          return false;
        }
        if (state == 0) { // Path
          if (c == '?') {
            state = 1; // move to query state
          } else if (c == '#') {
            state = 2; // move to fragment state
          } else if (!IsIpchar(c)) {
            return false;
          }
          ++index;
        } else if (state == 1) { // Query
          if (c == '#') {
            state = 2; // move to fragment state
          } else if (!IsIqueryChar(c)) {
            return false;
          }
          ++index;
        } else if (state == 2) { // Fragment
          if (!IsIfragmentChar(c)) {
            return false;
          }
          ++index;
        }
      }
      return true;
    }

    public static String BuildIRI(
      String schemeAndAuthority,
      String path,
      String query,
      String fragment) {
      StringBuilder builder = new StringBuilder();
      if (!((schemeAndAuthority) == null || (schemeAndAuthority).length() == 0)) {
        int[] irisplit = SplitIRI(schemeAndAuthority);
       // NOTE: Path component is always present in URIs;
       // we check here whether path component is empty
        if (irisplit == null || (irisplit[0] < 0 && irisplit[2] < 0) ||
          irisplit[4] != irisplit[5] || irisplit[6] >= 0 || irisplit[8] >= 0) {
          throw new ArgumentException("invalid schemeAndAuthority");
        }
      }
      if (((path) == null || (path).length() == 0)) {
        path = "";
      }
      for (int phase = 0; phase < 3; ++phase) {
        String s = path;
        if (phase == 1) {
          s = query;
          if (query == null) {
            continue;
          }
          builder.append('?');
        } else if (phase == 2) {
          s = fragment;
          if (fragment == null) {
            continue;
          }
          builder.append('#');
        }
        int index = 0;
        while (index < s.length()) {
          int c = s.charAt(index);
          if ((c & 0xfc00) == 0xd800 && index + 1 < s.length() &&
              (s.charAt(index + 1) & 0xfc00) == 0xdc00) {
           // Get the Unicode code point for the surrogate pair
            c = 0x10000 + ((c - 0xd800) << 10) + (s.charAt(index + 1) - 0xdc00);
          } else if ((c & 0xf800) == 0xd800) {
            c = 0xfffd;
          }
          if (c >= 0x10000) {
            ++index;
          }
          if (c == '%') {
            if (index + 2 < s.length() && IsHexChar(s.charAt(index + 1)) &&
              IsHexChar(s.charAt(index + 2))) {
              builder.append('%');
              builder.append(s.charAt(index + 1));
              builder.append(s.charAt(index + 2));
              index += 3;
            } else {
              builder.append("%25");
              ++index;
            }
          } else if ((c & 0x7f) == c &&
     ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') ||
              (c >= '0' && c <= '9') ||
              "-_.~/(=):!$&'*+,;@".indexOf((char)c) >= 0)) {
           // NOTE: Question mark will be percent encoded even though
           // it can appear in query and fragment strings
            builder.append((char)c);
            ++index;
          } else {
            PercentEncodeUtf8(builder, c);
            ++index;
          }
        }
      }
      String ret = builder.toString();
      if (SplitIRI(ret) == null) {
        throw new ArgumentException();
      }
      return ret;
    }

    public static boolean IsValidIRI(String s) {
      return ((s == null) ?
  null : SplitIRI(
    s,
    0,
    s.length(),
    ParseMode.IRIStrict)) != null;
    }

    public static boolean IsValidIRI(String s, ParseMode mode) {
      return ((s == null) ?
  null : SplitIRI(
    s,
    0,
    s.length(),
    mode)) != null;
    }

    private static final String ValueDotSlash = "." + "/";
    private static final String ValueSlashDot = "/" + ".";

    private static String NormalizePath(String path) {
      int len = path.length();
      if (len == 0 || path.equals("..") || path.equals(".")) {
        return "";
      }
      if (path.indexOf(ValueSlashDot) < 0 &&
          path.indexOf(
            ValueDotSlash) < 0) {
        return path;
      }
      StringBuilder builder = new StringBuilder();
      int index = 0;
      while (index < len) {
        char c = path.charAt(index);
        if ((index + 3 <= len && c == '/' && path.charAt(index + 1) == '.' &&
             path.charAt(index + 2) == '/') || (index + 2 == len && c == '.' &&
             path.charAt(index + 1) == '.')) {
         // begins with "/./" or is "..";
         // move index by 2
          index += 2;
          continue;
        }
        if (index + 3 <= len && c == '.' &&
            path.charAt(index + 1) == '.' && path.charAt(index + 2) == '/') {
         // begins with "../";
         // move index by 3
          index += 3;
          continue;
        }
        if ((index + 2 <= len && c == '.' &&
             path.charAt(index + 1) == '/') || (index + 1 == len && c == '.')) {
         // begins with "./" or is ".";
         // move index by 1
          ++index;
          continue;
        }
        if (index + 2 == len && c == '/' &&
            path.charAt(index + 1) == '.') {
         // is "/."; append '/' and break
          builder.append('/');
          break;
        }
        if (index + 3 == len && c == '/' &&
            path.charAt(index + 1) == '.' && path.charAt(index + 2) == '.') {
         // is "/.."; remove last segment,
         // append "/" and return
          int index2 = builder.length() - 1;
          String builderString = builder.toString();
          while (index2 >= 0) {
            if (builderString.charAt(index2) == '/') {
              break;
            }
            --index2;
          }
          if (index2 < 0) {
            index2 = 0;
          }
          builder.setLength(index2);
          builder.append('/');
          break;
        }
        if (index + 4 <= len && c == '/' && path.charAt(index + 1) == '.' &&
            path.charAt(index + 2) == '.' && path.charAt(index + 3) == '/') {
         // begins with "/../"; remove last segment
          int index2 = builder.length() - 1;
          String builderString = builder.toString();
          while (index2 >= 0) {
            if (builderString.charAt(index2) == '/') {
              break;
            }
            --index2;
          }
          if (index2 < 0) {
            index2 = 0;
          }
          builder.setLength(index2);
          index += 3;
          continue;
        }
        builder.append(c);
        ++index;
        while (index < len) {
         // Move the rest of the
         // path segment until the next '/'
          c = path.charAt(index);
          if (c == '/') {
            break;
          }
          builder.append(c);
          ++index;
        }
      }
      return builder.toString();
    }

    private static int ParseIPLiteral(String s, int offset, int endOffset) {
      int index = offset;
      if (offset == endOffset) {
        return -1;
      }
     // Assumes that the character before offset
     // is a '['
      if (s.charAt(index) == 'v') {
       // IPvFuture
        ++index;
        boolean hex = false;
        while (index < endOffset) {
          char c = s.charAt(index);
          if (IsHexChar(c)) {
            hex = true;
          } else {
            break;
          }
          ++index;
        }
        if (!hex) {
          return -1;
        }
        if (index >= endOffset || s.charAt(index) != '.') {
          return -1;
        }
        ++index;
        hex = false;
        while (index < endOffset) {
          char c = s.charAt(index);
          if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
              (c >= '0' && c <= '9') || ((c & 0x7F) == c &&
               ":-._~!$&'()*+,;=".indexOf(c) >= 0)) {
            hex = true;
          } else {
            break;
          }
          ++index;
        }
        if (!hex) {
          return -1;
        }
        if (index >= endOffset || s.charAt(index) != ']') {
          return -1;
        }
        ++index;
        return index;
      }
      if (s.charAt(index) == ':' ||
          IsHexChar(s.charAt(index))) {
        int startIndex = index;
        while (index < endOffset && ((s.charAt(index) >= 65 && s.charAt(index) <= 70) ||
     (s.charAt(index) >= 97 && s.charAt(index) <= 102) || (s.charAt(index) >= 48 && s.charAt(index)
     <= 58) || (s.charAt(index) == 46))) {
          ++index;
        }
        if (index >= endOffset || (s.charAt(index) != ']' && s.charAt(index) != '%')) {
          return -1;
        }
       // NOTE: Array is initialized to zeros
        int[] addressParts = new int[8];
        int ipEndIndex = index;
        boolean doubleColon = false;
        int doubleColonPos = 0;
        int totalParts = 0;
        boolean ipv4part = false;
        index = startIndex;
       // DebugUtility.Log(s.substring(startIndex, (startIndex)+(ipEndIndex-startIndex)));
        for (int part = 0; part < 8; ++part) {
          if (!doubleColon &&
            ipEndIndex - index > 1 && s.charAt(index) == ':' &&
            s.charAt(index + 1) == ':') {
            doubleColon = true;
            doubleColonPos = part;
            index += 2;
            if (index == ipEndIndex) {
              break;
            }
          }
          int hex = 0;
          boolean haveHex = false;
          int curindex = index;
          for (int i = 0; i < 4; ++i) {
            if (IsHexChar(s.charAt(index))) {
              hex = (hex << 4) | ToHex(s.charAt(index));
              haveHex = true;
              ++index;
            } else {
              break;
            }
          }
          if (!haveHex) {
            return -1;
          }
          if (index < ipEndIndex && s.charAt(index) == '.' && part < 7) {
            ipv4part = true;
            index = curindex;
            break;
          }
          addressParts[part] = hex;
          ++totalParts;
          if (index < ipEndIndex && s.charAt(index) != ':') {
            return -1;
          }
          if (index == ipEndIndex && doubleColon) {
            break;
          }
          // Skip single colon, but not double colon
          if (index < ipEndIndex && (index + 1 >= ipEndIndex ||
            s.charAt(index + 1) != ':')) {
            ++index;
          }
        }
        if (index != ipEndIndex && !ipv4part) {
          return -1;
        }
        if (doubleColon || ipv4part) {
          if (ipv4part) {
            int[] ipparts = new int[4];
            for (int part = 0; part < 4; ++part) {
              if (part > 0) {
                if (index < ipEndIndex && s.charAt(index) == '.') {
                  ++index;
                } else {
                  return -1;
                }
              }
              if (index + 1 < ipEndIndex && s.charAt(index) == '0' &&
           (s.charAt(index + 1) >= '0' && s.charAt(index + 1) <= '9')) {
                return -1;
              }
              int dec = 0;
              boolean haveDec = false;
              int curindex = index;
              for (int i = 0; i < 4; ++i) {
                if (s.charAt(index) >= '0' && s.charAt(index) <= '9') {
                  dec = (dec * 10) + ((int)s.charAt(index) - '0');
                  haveDec = true;
                  ++index;
                } else {
                  break;
                }
              }
              if (!haveDec || dec > 255) {
                return -1;
              }
              ipparts[part] = dec;
            }
            if (index != ipEndIndex) {
              return -1;
            }
            addressParts[totalParts] = (ipparts[0] << 8) | ipparts[1];
            addressParts[totalParts + 1] = (ipparts[2] << 8) | ipparts[3];
            totalParts += 2;
            if (!doubleColon && totalParts != 8) {
              return -1;
            }
          }
          if (doubleColon) {
            int resid = 8 - totalParts;
            if (resid == 0) {
             // Purported IPv6 address contains
             // 8 parts and a double colon
              return -1;
            }
            int[] newAddressParts = new int[8];
            System.arraycopy(addressParts, 0, newAddressParts, 0, doubleColonPos);
            System.arraycopy(
              addressParts,
              doubleColonPos,
              newAddressParts,
              doubleColonPos + resid,
              totalParts - doubleColonPos);
            System.arraycopy(newAddressParts, 0, addressParts, 0, 8);
          }
        } else if (totalParts != 8) {
          return -1;
        }

  // DebugUtility.Log("{0:X4}:{0:X4}:{0:X4}:{0:X4}:{0:X4}:{0:X4}:{0:X4}:{0:X4}"
       // ,
       // addressParts[0], addressParts[1], addressParts[2],
       // addressParts[3], addressParts[4], addressParts[5],
       // addressParts[6], addressParts[7]);
        if (s.charAt(index) == '%') {
          if (index + 2 < endOffset && s.charAt(index + 1) == '2' &&
              s.charAt(index + 2) == '5' && (addressParts[0] & 0xFFC0) == 0xFE80) {
           // Zone identifier in an IPv6 address
           // (see RFC6874)
           // NOTE: Allowed only if address has prefix fe80::/10
            index += 3;
            boolean haveChar = false;
            while (index < endOffset) {
              char c = s.charAt(index);
              if (c == ']') {
                return haveChar ? index + 1 : -1;
              }
              if (c == '%') {
                if (index + 2 < endOffset && IsHexChar(s.charAt(index + 1)) &&
                    IsHexChar(s.charAt(index + 2))) {
                  index += 3;
                  haveChar = true;
                  continue;
                }
                return -1;
              }
              if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
             (c >= '0' && c <= '9') || c == '.' || c == '_' || c == '-' ||
                c == '~') {
               // unreserved character under RFC3986
                ++index;
                haveChar = true;
                continue;
              }
              return -1;
            }
            return -1;
          }
          return -1;
        }
        ++index;
        return index;
      }
      return -1;
    }

    private static String PathParent(
      String refValue,
      int startIndex,
      int endIndex) {
      if (startIndex > endIndex) {
        return "";
      }
      --endIndex;
      while (endIndex >= startIndex) {
        if (refValue.charAt(endIndex) == '/') {
          return refValue.substring(startIndex, (startIndex)+((endIndex + 1) - startIndex));
        }
        --endIndex;
      }
      return "";
    }

    private static void PercentEncode(StringBuilder buffer, int b) {
      buffer.append('%');
      buffer.append(HexChars.charAt((b >> 4) & 0x0f));
      buffer.append(HexChars.charAt(b & 0x0f));
    }

    private static void PercentEncodeUtf8(StringBuilder buffer, int cp) {
      if (cp <= 0x7f) {
        buffer.append('%');
        buffer.append(HexChars.charAt((cp >> 4) & 0x0f));
        buffer.append(HexChars.charAt(cp & 0x0f));
      } else if (cp <= 0x7ff) {
        PercentEncode(buffer, 0xc0 | ((cp >> 6) & 0x1f));
        PercentEncode(buffer, 0x80 | (cp & 0x3f));
      } else if (cp <= 0xffff) {
        PercentEncode(buffer, 0xe0 | ((cp >> 12) & 0x0f));
        PercentEncode(buffer, 0x80 | ((cp >> 6) & 0x3f));
        PercentEncode(buffer, 0x80 | (cp & 0x3f));
      } else {
        PercentEncode(buffer, 0xf0 | ((cp >> 18) & 0x07));
        PercentEncode(buffer, 0x80 | ((cp >> 12) & 0x3f));
        PercentEncode(buffer, 0x80 | ((cp >> 6) & 0x3f));
        PercentEncode(buffer, 0x80 | (cp & 0x3f));
      }
    }

    public static String RelativeResolve(String refValue, String baseURI) {
      return RelativeResolve(refValue, baseURI, ParseMode.IRIStrict);
    }

    public static String RelativeResolve(
      String refValue,
      String baseURI,
      ParseMode parseMode) {
      int[] segments = (refValue == null) ? null : SplitIRI(
        refValue,
        0,
        refValue.length(),
        parseMode);
      if (segments == null) {
        return null;
      }
      int[] segmentsBase = (
        baseURI == null) ? null : SplitIRI(
  baseURI,
  0,
  baseURI.length(),
  parseMode);
      if (segmentsBase == null) {
        return refValue;
      }
      StringBuilder builder = new StringBuilder();
      if (segments[0] >= 0) { // scheme present
        AppendScheme(builder, refValue, segments);
        AppendAuthority(builder, refValue, segments);
        AppendNormalizedPath(builder, refValue, segments);
        AppendQuery(builder, refValue, segments);
        AppendFragment(builder, refValue, segments);
      } else if (segments[2] >= 0) { // authority present
        AppendScheme(builder, baseURI, segmentsBase);
        AppendAuthority(builder, refValue, segments);
        AppendNormalizedPath(builder, refValue, segments);
        AppendQuery(builder, refValue, segments);
        AppendFragment(builder, refValue, segments);
      } else if (segments[4] == segments[5]) {
        AppendScheme(builder, baseURI, segmentsBase);
        AppendAuthority(builder, baseURI, segmentsBase);
        AppendPath(builder, baseURI, segmentsBase);
        if (segments[6] >= 0) {
          AppendQuery(builder, refValue, segments);
        } else {
          AppendQuery(builder, baseURI, segmentsBase);
        }
        AppendFragment(builder, refValue, segments);
      } else {
        AppendScheme(builder, baseURI, segmentsBase);
        AppendAuthority(builder, baseURI, segmentsBase);
        if (segments[4] < segments[5] && refValue.charAt(segments[4]) == '/') {
          AppendNormalizedPath(builder, refValue, segments);
        } else {
          StringBuilder merged = new StringBuilder();
          if (segmentsBase[2] >= 0 && segmentsBase[4] == segmentsBase[5]) {
            merged.append('/');
            AppendPath(merged, refValue, segments);
            builder.append(NormalizePath(merged.toString()));
          } else {
            merged.append(
              PathParent(
                baseURI,
                segmentsBase[4],
                segmentsBase[5]));
            AppendPath(merged, refValue, segments);
            builder.append(NormalizePath(merged.toString()));
          }
        }
        AppendQuery(builder, refValue, segments);
        AppendFragment(builder, refValue, segments);
      }
      return builder.toString();
    }

    private static String ToLowerCaseAscii(String str) {
      if (str == null) {
        return null;
      }
      int len = str.length();
      char c = (char)0;
      boolean hasUpperCase = false;
      for (int i = 0; i < len; ++i) {
        c = str.charAt(i);
        if (c >= 'A' && c <= 'Z') {
          hasUpperCase = true;
          break;
        }
      }
      if (!hasUpperCase) {
        return str;
      }
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < len; ++i) {
        c = str.charAt(i);
        if (c >= 'A' && c <= 'Z') {
          builder.append((char)(c + 0x20));
        } else {
          builder.append(c);
        }
      }
      return builder.toString();
    }

    public static String[] SplitIRIToStrings(String s) {
      int[] indexes = SplitIRI(s);
      if (indexes == null) {
        return null;
      }
      return new String[] {
 indexes[0] < 0 ? null : ToLowerCaseAscii(
  s.substring(
    indexes[0], (
    indexes[0])+(indexes[1] - indexes[0]))),
 indexes[2] < 0 ? null : s.substring(indexes[2], (indexes[2])+(indexes[3] - indexes[2])),
 indexes[4] < 0 ? null : s.substring(indexes[4], (indexes[4])+(indexes[5] - indexes[4])),
 indexes[6] < 0 ? null : s.substring(indexes[6], (indexes[6])+(indexes[7] - indexes[6])),
 indexes[8] < 0 ? null : s.substring(indexes[8], (indexes[8])+(indexes[9] - indexes[8])),
  };
    }

    public static int[] SplitIRI(String s) {
      return (s == null) ? null : SplitIRI(s, 0, s.length(), ParseMode.IRIStrict);
    }

    public static int[] SplitIRI(
      String s,
      int offset,
      int length,
      ParseMode parseMode) {
      if (s == null) {
        return null;
      }
      if (s == null) {
        throw new NullPointerException("s");
      }
      if (offset < 0) {
        throw new ArgumentException("offset (" + offset +
          ") is less than 0");
      }
      if (offset > s.length()) {
        throw new ArgumentException("offset (" + offset +
          ") is more than " + s.length());
      }
      if (length < 0) {
        throw new ArgumentException("length (" + length +
          ") is less than 0");
      }
      if (length > s.length()) {
        throw new ArgumentException("length (" + length +
          ") is more than " + s.length());
      }
      if (s.length() - offset < length) {
        throw new ArgumentException("s's length minus " + offset + " (" +
          (s.length() - offset) + ") is less than " + length);
      }
      int[] retval = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
      if (length == 0) {
        retval[4] = 0;
        retval[5] = 0;
        return retval;
      }
      boolean asciiOnly = parseMode == ParseMode.URILenient || parseMode ==
        ParseMode.URIStrict;
      boolean strict = parseMode == ParseMode.URIStrict || parseMode ==
        ParseMode.IRIStrict;
      int index = offset;
      int valueSLength = offset + length;
      boolean scheme = false;
     // scheme
      while (index < valueSLength) {
        int c = s.charAt(index);
        if (index > offset && c == ':') {
          scheme = true;
          retval[0] = offset;
          retval[1] = index;
          ++index;
          break;
        }
        if (strict && index == offset && !((c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z'))) {
          break;
        }
        if (strict && index > offset &&
        !((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' &&
              c <= '9') || c == '+' || c == '-' || c == '.')) {
          break;
        }
        if (!strict && (c == '#' || c == ':' || c == '?' || c == '/')) {
          break;
        }
        ++index;
      }
      if (!scheme) {
        index = offset;
      }
      int state = 0;
      if (index + 2 <= valueSLength && s.charAt(index) == '/' && s.charAt(index + 1) == '/') {
       // authority
       // (index + 2, valueSLength)
        index += 2;
        int authorityStart = index;
        retval[2] = authorityStart;
        retval[3] = valueSLength;
        state = 0; // userinfo
       // Check for userinfo
        while (index < valueSLength) {
          int c = s.charAt(index);
          if (asciiOnly && c >= 0x80) {
            return null;
          }
          if ((c & 0xfc00) == 0xd800 && index + 1 < valueSLength &&
              (s.charAt(index + 1) & 0xfc00) == 0xdc00) {
           // Get the Unicode code point for the surrogate pair
            c = 0x10000 + ((c - 0xd800) << 10) + (s.charAt(index + 1) - 0xdc00);
            ++index;
          } else if ((c & 0xf800) == 0xd800) {
            if (parseMode == ParseMode.IRISurrogateLenient) {
              c = 0xfffd;
            } else {
              return null;
            }
          }
          if (c == '%' && (state == 0 || state == 1) && strict) {
           // Percent encoded character (except in port)
            if (index + 2 < valueSLength && IsHexChar(s.charAt(index + 1)) &&
                IsHexChar(s.charAt(index + 2))) {
              index += 3;
              continue;
            }
            return null;
          }
          if (state == 0) { // User info
            if (c == '/' || c == '?' || c == '#') {
             // not user info
              state = 1;
              index = authorityStart;
              continue;
            }
            if (strict && c == '@') {
             // is user info
              ++index;
              state = 1;
              continue;
            }
            if (strict && IsIUserInfoChar(c)) {
              ++index;
              if (index == valueSLength) {
               // not user info
                state = 1;
                index = authorityStart;
                continue;
              }
            } else {
             // not user info
              state = 1;
              index = authorityStart;
              continue;
            }
          } else if (state == 1) { // host
            if (c == '/' || c == '?' || c == '#') {
             // end of authority
              retval[3] = index;
              break;
            }
            if (!strict) {
              ++index;
            } else if (c == '[') {
              ++index;
              index = ParseIPLiteral(s, index, valueSLength);
              if (index < 0) {
                return null;
              }
              continue;
            } else if (c == ':') {
             // port
              state = 2;
              ++index;
            } else if (IsIRegNameChar(c)) {
             // is valid host name char
             // (note: IPv4 addresses included
             // in ireg-name)
              ++index;
            } else {
              return null;
            }
          } else if (state == 2) { // Port
            if (c == '/' || c == '?' || c == '#') {
             // end of authority
              retval[3] = index;
              break;
            }
            if (c >= '0' && c <= '9') {
              ++index;
            } else {
              return null;
            }
          }
        }
      }
      boolean colon = false;
      boolean segment = false;
      boolean fullyRelative = index == offset;
      retval[4] = index; // path offsets
      retval[5] = valueSLength;
      state = 0; // IRI Path
      while (index < valueSLength) {
       // Get the next Unicode character
        int c = s.charAt(index);
        if (asciiOnly && c >= 0x80) {
          return null;
        }
        if ((c & 0xfc00) == 0xd800 && index + 1 < valueSLength &&
            (s.charAt(index + 1) & 0xfc00) == 0xdc00) {
         // Get the Unicode code point for the surrogate pair
          c = 0x10000 + ((c - 0xd800) << 10) + (s.charAt(index + 1) - 0xdc00);
          ++index;
        } else if ((c & 0xf800) == 0xd800) {
         // error
          return null;
        }
        if (c == '%' && strict) {
         // Percent encoded character
          if (index + 2 < valueSLength && IsHexChar(s.charAt(index + 1)) &&
              IsHexChar(s.charAt(index + 2))) {
            index += 3;
            continue;
          }
          return null;
        }
        if (state == 0) { // Path
          if (c == ':' && fullyRelative) {
            colon = true;
          } else if (c == '/' && fullyRelative && !segment) {
           // noscheme path can't have colon before slash
            if (strict && colon) {
              return null;
            }
            segment = true;
          }
          if (c == '?') {
            retval[5] = index;
            retval[6] = index + 1;
            retval[7] = valueSLength;
            state = 1; // move to query state
          } else if (c == '#') {
            retval[5] = index;
            retval[8] = index + 1;
            retval[9] = valueSLength;
            state = 2; // move to fragment state
          } else if (strict && !IsIpchar(c)) {
            return null;
          }
          ++index;
        } else if (state == 1) { // Query
          if (c == '#') {
            retval[7] = index;
            retval[8] = index + 1;
            retval[9] = valueSLength;
            state = 2; // move to fragment state
          } else if (strict && !IsIqueryChar(c)) {
            return null;
          }
          ++index;
        } else if (state == 2) { // Fragment
          if (strict && !IsIfragmentChar(c)) {
            return null;
          }
          ++index;
        }
      }
      if (strict && fullyRelative && colon && !segment) {
        return null; // ex. "x@y:z"
      }
      return retval;
    }

    public static int[] SplitIRI(String s, ParseMode parseMode) {
      return (s == null) ? null : SplitIRI(s, 0, s.length(), parseMode);
    }

    private static boolean PathHasDotComponent(String path) {
      if (path == null || path.length() == 0) {
        return false;
      }
      path = PercentDecode(path);
      if (path.equals("..")) {
        return true;
      }
      if (path.equals(".")) {
        return true;
      }
      if (path.indexOf(ValueSlashDot) < 0 &&
              path.indexOf(
                ValueDotSlash) < 0) {
        return false;
      }
      int index = 0;
      int len = path.length();
      while (index < len) {
        char c = path.charAt(index);
        if ((index + 3 <= len && c == '/' && path.charAt(index + 1) == '.' &&
             path.charAt(index + 2) == '/') || (index + 2 == len && c == '.' &&
             path.charAt(index + 1) == '.')) {
         // begins with "/./" or is "..";
          return true;
        }
        if (index + 3 <= len && c == '.' &&
            path.charAt(index + 1) == '.' && path.charAt(index + 2) == '/') {
         // begins with "../";
          return true;
        }
        if ((index + 2 <= len && c == '.' &&
             path.charAt(index + 1) == '/') || (index + 1 == len && c == '.')) {
         // begins with "./" or is ".";
          return true;
        }
        if (index + 2 == len && c == '/' && path.charAt(index + 1) == '.') {
         // is "/."
          return true;
        }
        if (index + 3 == len && c == '/' &&
            path.charAt(index + 1) == '.' && path.charAt(index + 2) == '.') {
         // is "/.."
          return true;
        }
        if (index + 4 <= len && c == '/' && path.charAt(index + 1) == '.' &&
            path.charAt(index + 2) == '.' && path.charAt(index + 3) == '/') {
         // begins with "/../"
          return true;
        }
        ++index;
        while (index < len) {
         // Move the rest of the
         // path segment until the next '/'
          c = path.charAt(index);
          if (c == '/') {
            break;
          }
          ++index;
        }
      }
      return false;
    }

    private static String UriPath(String uri, ParseMode parseMode) {
      int[] indexes = SplitIRI(uri, parseMode);
      return (
       indexes == null) ? null : uri.substring(
       indexes[4], (
       indexes[4])+(indexes[5] - indexes[4]));
    }

    public static String DirectoryPath(String uri) {
      return DirectoryPath(uri, ParseMode.IRIStrict);
    }

    public static String DirectoryPath(String uri, ParseMode parseMode) {
      int[] indexes = SplitIRI(uri, parseMode);
      if (indexes == null) {
        return null;
      }
      String schemeAndAuthority = uri.substring(0, indexes[4]);
      String path = uri.substring(indexes[4], (indexes[4])+(indexes[5] - indexes[4]));
      if (path.length() > 0) {
        for (int i = path.length() - 1; i >= 0; --i) {
          if (path.charAt(i) == '/') {
            return schemeAndAuthority + path.substring(0, i + 1);
          }
        }
        return schemeAndAuthority + path;
      } else {
        return schemeAndAuthority;
      }
    }

    public static String RelativeResolveWithinBaseURI(
      String refValue,
      String absoluteBaseURI) {
      String rel = RelativeResolve(refValue, absoluteBaseURI);
      if (rel == null) {
        return null;
      }
      String relpath = UriPath(refValue, ParseMode.IRIStrict);
      if (PathHasDotComponent(relpath)) {
       // Resolved path has a dot component in it (usually
       // because that component is percent-encoded)
        return null;
      }
      String absuri = DirectoryPath(absoluteBaseURI);
      String reluri = DirectoryPath(rel);
      return (absuri == null || reluri == null ||
         !absuri.equals(reluri)) ? null : rel;
    }
  }
