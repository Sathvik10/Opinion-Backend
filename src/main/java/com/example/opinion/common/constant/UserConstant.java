package com.example.opinion.common.constant;

import com.example.opinion.common.ContextKey;
import com.example.opinion.model.User;
import com.example.opinion.wrapper.UserWrapper;

public class UserConstant {
  public static final ContextKey<User> USER = new ContextKey<>("USER");
  public static final ContextKey<String> USER_ID = new ContextKey<>("USER_ID");
  public static final ContextKey<UserWrapper> USER_WRAPPER = new ContextKey<>("USER_WRAPPER");
}
