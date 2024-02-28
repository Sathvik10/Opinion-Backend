package com.example.opinion.common;

import java.util.HashMap;

public class Context {
  private HashMap<Object, Object> map;

  public Context() {
    map = new HashMap<>();
  }

  public <T> T getOrDefault(ContextKey<T> key, T defaultValue) {
    return (T) map.getOrDefault(key.getName(), defaultValue);
  }

  public <T> T getOrThrow(ContextKey<T> key) {
    T value = (T) map.get(key.getName());
    if (value == null) throw new RuntimeException("Data Not Found. Key:" + key.getName());
    return (T) value;
  }

  public <T> void put(ContextKey<T> key, T value) {
    map.put(key.getName(), value);
  }

  public <T> T remove(ContextKey<T> key) {
    return (T) map.remove(key.getName());
  }

  public <T> T removeOrDefault(ContextKey<T> key, T defaultValue) {
    T value = (T) map.remove(key.getName());
    return value == null ? defaultValue : value;
  }

  public <T> T removeOrThrow(ContextKey<T> key) {
    T value = (T) map.remove(key.getName());
    if (value == null) throw new RuntimeException("Data Not Found. Key:" + key.getName());
    return value;
  }
}
