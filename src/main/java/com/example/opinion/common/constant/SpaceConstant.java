package com.example.opinion.common.constant;

import com.example.opinion.common.ContextKey;
import com.example.opinion.model.Space;

public class SpaceConstant {
  public static final ContextKey<Boolean> UPDATE = new ContextKey<>("SPACE_UPDATE");
  public static final ContextKey<Space> SPACE = new ContextKey<>("SPACE");
}
