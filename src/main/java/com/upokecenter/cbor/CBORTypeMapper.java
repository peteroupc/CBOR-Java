package com.upokecenter.cbor;

import java.util.*;

  public final class CBORTypeMapper {
    private final List<String> typePrefixes;
    private final List<String> typeNames;
    private final Map<Object, ConverterInfo>
    converters;

    public CBORTypeMapper() {
      this.typePrefixes = new ArrayList<String>();
      this.typeNames = new ArrayList<String>();
      this.converters = new HashMap<Object, ConverterInfo>();
    }

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
          type));;
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
      ConverterInfo convinfo = PropertyMap.GetOrDefault(
        this.converters,
        type,
        null);
      return (T)(convinfo == null ? null : (convinfo.getFromObject() == null) ? null :
        PropertyMap.CallFromObject(convinfo, cbor));
    }

    CBORObject ConvertWithConverter(Object obj) {
      Object type = obj.getClass();
      ConverterInfo convinfo = PropertyMap.GetOrDefault(
        this.converters,
        type,
        null);
      return (convinfo == null) ? null :
        PropertyMap.CallToObject(convinfo, obj);
    }

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
