package com.upokecenter.cbor;
import com.upokecenter.util.*;

  class CharacterInputWithCount implements ICharacterInput {
   int offset;
   ICharacterInput ci;
   public CharacterInputWithCount (ICharacterInput ci) {
    this.ci = ci;
   }

   public int GetOffset() {
     return offset;
   }

   private String NewErrorString(String str) {
     return str + " (offset " + this.GetOffset() + ")";
   }

   public void RaiseError(String str) {
     throw new CBORException(NewErrorString(str));
   }

   public int ReadChar() {
     int c = -1;
     try {
       c = ci.ReadChar();
     } catch (IllegalStateException ex) {
       if (ex.getCause() == null) {
         throw new CBORException(NewErrorString(ex.getMessage()),
           ex);
       } else {
         throw new CBORException(NewErrorString(ex.getMessage()),
           ex.getCause());
       }
     }
     if (c >= 0) {
       ++offset;
     }
     return c;
   }

   public int Read(int[] chars, int index, int length) {
     if ((chars) == null) {
  throw new NullPointerException("chars");
}
if (index < 0) {
  throw new IllegalArgumentException("index (" + index +
    ") is less than " + 0);
}
if (index > chars.length) {
  throw new IllegalArgumentException("index (" + index +
    ") is more than " + chars.length);
}
if (length < 0) {
  throw new IllegalArgumentException("length (" + length +
    ") is less than " + 0);
}
if (length > chars.length) {
  throw new IllegalArgumentException("length (" + length +
    ") is more than " + chars.length);
}
if (chars.length-index < length) {
  throw new IllegalArgumentException("chars's length minus " + index + " (" +
    (chars.length-index) + ") is less than " + length);
}
     int ret = ci.Read(chars, index, length);
     if (ret > 0) {
 offset += ret;
}
     return ret;
   }
  }
