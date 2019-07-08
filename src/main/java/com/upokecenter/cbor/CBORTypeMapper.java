package com.upokecenter.cbor;

import java.util.*;

    /**
     * Holds converters to customize the serialization and deserialization behavior
     * of <code>CBORObject.FromObject</code> and <code>CBORObject#ToObject</code> , as
     * well as type filters for <code>ToObject</code>.
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
     * Registers an object that converts objects of a given type to CBOR objects
     * (called a CBOR converter).
     * @param type A Type object specifying the type that the converter converts to
     * CBOR objects.
     * @param converter The parameter {@code converter} is an ICBORConverter
     * object.
     * @param <T> Must be the same as the "type" parameter.
     * @return This object.
     * @throws NullPointerException The parameter {@code type} or {@code
     * converter} is null.
     * @throws IllegalArgumentException Converter doesn't contain a proper
     *  ToCBORObject method".
     */
    public <T> CBORTypeMapper AddConverter(java.lang.reflect.Type type,
      ICBORConverter<T> converter) {
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
      ci.setFromObject(PropertyMap.FindOneArgumentMethod(
        converter,
        "FromCBORObject",
        CBORObject.class));
      this.converters.put(type, ci);
      return this;
    }

    @SuppressWarnings("unchecked")
 <T> T ConvertBackWithConverter(
      CBORObject cbor,
      java.lang.reflect.Type type) {
      ConverterInfo convinfo = null;
      if (this.converters.containsKey(type)) {
        convinfo = this.converters.get(type);
      } else {
        return null;
      }
      if (convinfo == null) {
        return null;
      }
      if (convinfo.getFromObject() == null) {
        return null;
      }
      return (T)(PropertyMap.InvokeOneArgumentMethod(
        convinfo.getFromObject(),
        convinfo.getConverter(),
        cbor));
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
     * Returns whether the given Java or.NET type name fits the filters given in
     * this mapper.
     * @param typeName The fully qualified name of a Java or.NET class (e.g.,
     * {@code java.math.BigInteger} or {@code
     * System.Globalization.CultureInfo}).
     * @return Either {@code true} if the given Java or.NET type name fits the
     * filters given in this mapper, or {@code false} otherwise.
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
     * Adds a prefix of a Java or.NET type for use in type matching. A type
     * matches a prefix if its fully qualified name is or begins with that
     * prefix, using codepoint-by-codepoint (case-sensitive) matching.
     * @param prefix The prefix of a Java or.NET type (e.g., `java.math.` or
     * `System.Globalization`).
     * @return This object.
     * @throws NullPointerException The parameter {@code prefix} is null.
     * @throws IllegalArgumentException The parameter {@code prefix} is empty.
     */
    public CBORTypeMapper AddTypePrefix(String prefix) {
      if (prefix == null) {
        throw new NullPointerException("prefix");
      }
      if (prefix.length() == 0) {
        throw new IllegalArgumentException("prefix" + " is empty.");
      }
      this.typePrefixes.add(prefix);
      return this;
    }

    /**
     * Adds the fully qualified name of a Java or.NET type for use in type
     * matching.
     * @param name The fully qualified name of a Java or.NET class (e.g., {@code
     * java.math.BigInteger} or {@code System.Globalization.CultureInfo}).
     * @return This object.
     * @throws NullPointerException The parameter {@code name} is null.
     * @throws IllegalArgumentException The parameter {@code name} is empty.
     */
    public CBORTypeMapper AddTypeName(String name) {
      if (name == null) {
        throw new NullPointerException("name");
      }
      if (name.length() == 0) {
        throw new IllegalArgumentException("name" + " is empty.");
      }
      this.typeNames.add(name);
      return this;
    }

    static final class ConverterInfo {
      public final Object getToObject() { return propVartoobject; }
public final void setToObject(Object value) { propVartoobject = value; }
private Object propVartoobject;

      public final Object getFromObject() { return propVarfromobject; }
public final void setFromObject(Object value) { propVarfromobject = value; }
private Object propVarfromobject;

      public final Object getConverter() { return propVarconverter; }
public final void setConverter(Object value) { propVarconverter = value; }
private Object propVarconverter;
    }
  }
