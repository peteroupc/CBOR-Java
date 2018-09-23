package com.upokecenter.cbor;

import java.util.*;

    /**
     * Not documented yet.
     */
  public final class CBORTypeMapper {
    private final List<String> typePrefixes;
    private final List<String> typeNames;
    private final Map<Object, ConverterInfo>
      converters;

    /**
     * Initializes a new instance of the {@link CBORTypeMapper} class.
     */
    public CBORTypeMapper() {
this.typePrefixes = new ArrayList<String>();
this.typeNames = new ArrayList<String>();
this.converters = new HashMap<Object, ConverterInfo>();
    }

    /**
     * Not documented yet.
     * @param type The parameter {@code type} is not documented yet.
     * @param converter The parameter {@code converter} is not documented yet.
     * @param <T> Type parameter not documented yet.
     * @throws java.lang.NullPointerException The parameter {@code type} or {@code
     * converter} is null.
     * @throws IllegalArgumentException Converter doesn't contain a proper
     * ToCBORObject method.
     */
    public <T> void AddConverter(java.lang.reflect.Type type, ICBORConverter<T> converter) {
      if (type == null) {
        throw new NullPointerException("type");
      }
      if (converter == null) {
        throw new NullPointerException("converter");
      }
      ConverterInfo ci = new ConverterInfo();
      ci.setConverter(converter);
      ci.setToObject(PropertyMap.FindOneArgumentMethod(
        converter,
        "ToCBORObject",
        type));
      if (ci.getToObject() == null) {
        throw new IllegalArgumentException(
          "Converter doesn't contain a proper ToCBORObject method");
      }
      this.converters.put(type, ci);
    }

    CBORObject ConvertWithConverter(Object obj) {
      Object type = obj.getClass();
      ConverterInfo convinfo = null;
        if (this.converters.containsKey(type)) {
          convinfo = this.converters.get(type);
        } else {
          return null;
        }
      if (convinfo == null) {
        return null;
      }
      return (CBORObject)PropertyMap.InvokeOneArgumentMethod(
        convinfo.getToObject(),
        convinfo.getConverter(),
        obj);
    }

    /**
     * Not documented yet.
     * @param typeName The parameter {@code typeName} is not documented yet.
     * @return Either {@code true} or {@code false}.
     */
    public boolean FilterTypeName(String typeName) {
      if (((typeName) == null || (typeName).length() == 0)) {
 return false;
}
      for (String prefix : this.typePrefixes) {
   if (typeName.length() >= prefix.length() &&
     typeName.substring(0, prefix.length()).equals(prefix)) {
     return true;
   }
      }
      for (String name : this.typeNames) {
   if (typeName.equals(name)) {
     return true;
   }
      }
      return false;
    }

    /**
     * Adds a prefix of a Java or .NET type for use in type matching. A type
     * matches a prefix if its fully qualified name is or begins with that
     * prefix, using codepoint-by-codepoint (case-sensitive) matching.
     * @param prefix The parameter {@code prefix} is not documented yet.
     * @return A CBORTypeMapper object.
     * @throws java.lang.NullPointerException The parameter {@code prefix} is null.
     * @throws IllegalArgumentException The parameter {@code prefix} is empty.
     */
    public CBORTypeMapper AddTypePrefix(String prefix) {
      if (prefix == null) {
  throw new NullPointerException("prefix");
}
if (prefix.length() == 0) {
  throw new IllegalArgumentException("prefix" + " is empty.");
}
typePrefixes.add(prefix);
      return this;
    }

    /**
     * Adds the fully qualified name of a Java or .NET type for use in type
     * matching.
     * @param name The parameter {@code name} is not documented yet.
     * @return A CBORTypeMapper object.
     * @throws java.lang.NullPointerException The parameter {@code name} is null.
     * @throws IllegalArgumentException The parameter {@code name} is empty.
     */
    public CBORTypeMapper AddTypeName(String name) {
      if (name == null) {
  throw new NullPointerException("name");
}
if (name.length() == 0) {
  throw new IllegalArgumentException("name" + " is empty.");
}
typeNames.add(name);
      return this;
    }

    static final class ConverterInfo {
      private Object toObject;

    /**
     * Gets the converter's ToCBORObject method.
     * @return The converter's ToCBORObject method.
     */
      public final Object getToObject() {
          return this.toObject;
        }
public final void setToObject(Object value) {
          this.toObject = value;
        }

      private Object converter;

    /**
     * Gets the ICBORConverter object.
     * @return The ICBORConverter object.
     */
      public final Object getConverter() {
          return this.converter;
        }
public final void setConverter(Object value) {
          this.converter = value;
        }
    }
  }
