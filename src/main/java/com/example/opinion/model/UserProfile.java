package com.example.opinion.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.ObjectUtils;

@Data
@Document(collection = "user_profile")
@TypeAlias("UserProfile")
public class UserProfile {
  @Id private String userProfileId;
  private String userId;
  private String phone;
  private List<String> followedSpaces;
  private List<String> ownedSpaces;
  private String description;

  public UserProfile copyPartially(UserProfile userProfile)
  {
      if(!this.getUserId().equalsIgnoreCase(userProfile.getUserId()))
        throw new RuntimeException("Data Mismatch");
      if(!ObjectUtils.isEmpty(userProfile.getPhone())) this.setPhone(userProfile.getPhone());
    if(!ObjectUtils.isEmpty(userProfile.getDescription())) this.setDescription(userProfile.getDescription());
    if(!ObjectUtils.isEmpty(userProfile.getFollowedSpaces())) this.getFollowedSpaces().addAll(userProfile.getFollowedSpaces());
    if(!ObjectUtils.isEmpty(userProfile.getOwnedSpaces())) this.getOwnedSpaces().addAll(userProfile.getOwnedSpaces());

    return this;
  }
}
