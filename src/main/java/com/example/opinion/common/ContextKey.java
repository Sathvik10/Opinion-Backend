package com.example.opinion.common;

import lombok.Data;

@Data
public class ContextKey<T> {
  private String name;

  public ContextKey(String name) {
    this.name = name;
  }
}
