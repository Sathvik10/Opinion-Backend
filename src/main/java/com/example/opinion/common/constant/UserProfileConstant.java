package com.example.opinion.common.constant;

import com.example.opinion.common.ContextKey;
import com.example.opinion.model.UserProfile;

public class UserProfileConstant {
  public static final ContextKey<UserProfile> USER_PROFILE = new ContextKey<>("USER_PROFILE");

  public static final ContextKey<Boolean> UPDATE = new ContextKey<>("USER_PROFILE_UPDATE");
}
