package com.example.app.type;

import com.apollographql.apollo.api.ScalarType;

import java.util.Date;

import javax.annotation.Generated;

@Generated("Apollo GraphQL")
public enum CustomType implements ScalarType {
  DATE {
    @Override
    public String typeName() {
      return "Date";
    }

    @Override
    public Class javaType() {
      return Date.class;
    }
  },

  ID {
    @Override
    public String typeName() {
      return "ID";
    }

    @Override
    public Class javaType() {
      return String.class;
    }
  }
}
