package com.example.opinion.common.constant;

import com.example.opinion.common.ContextKey;
import com.example.opinion.model.Request;

public class RequestConstant {
  public static final ContextKey<Request> REQUEST = new ContextKey<>("REQUEST");
  public static final ContextKey<Boolean> UPDATE = new ContextKey<>("REQUEST_UPDATE");
}
