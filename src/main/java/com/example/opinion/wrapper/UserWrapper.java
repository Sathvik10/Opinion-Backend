package com.example.opinion.wrapper;

import com.example.opinion.model.User;
import com.example.opinion.model.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserWrapper {
  private User user;

  private UserProfile userProfile;

  private boolean updateUser = false;

  private boolean updateUserPartially = false;

  private boolean updateUserProfile = false;

  public void setUpdateUser(boolean updateUser) {
    this.updateUser = updateUser;
  }

  public void setUpdateUserPartially(boolean updateUserPartially) {
    this.updateUserPartially = updateUserPartially;
  }

  public void setUpdateUserProfile(boolean updateUserProfile) {
    this.updateUserProfile = updateUserProfile;
  }

  public void setUpdateUserProfilePartially(boolean updateUserProfilePartially) {
    this.updateUserProfilePartially = updateUserProfilePartially;
  }

  private boolean updateUserProfilePartially = false;

  public UserWrapper(User user, UserProfile userProfile) {
    this.user = user;
    this.userProfile = userProfile;
  }

  public UserWrapper(User user) {
    this.user = user;
  }

  public UserWrapper(UserProfile userProfile) {
    this.userProfile = userProfile;
  }
}
