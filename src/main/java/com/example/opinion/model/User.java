package com.example.opinion.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.ObjectUtils;

@Data
@Document(collection = "user")
@TypeAlias("User")
public class User {
  @Id private String userId;
  private String name;
  private String emailId;
  private String password;
  private String oauth;
  private String imageUrl;
  private Long lastTimeStamp;

  public User copyPartially(User user) {
    if (!user.getUserId().equalsIgnoreCase(this.userId))
      throw new RuntimeException("Data Mismatch");
    if (!ObjectUtils.isEmpty(user.name)) this.setName(user.getName());
    if (!ObjectUtils.isEmpty(user.emailId)) this.setEmailId(user.getEmailId());
    if (!ObjectUtils.isEmpty(user.password)) this.setPassword(user.getPassword());
    if (!ObjectUtils.isEmpty(user.oauth)) this.setOauth(user.getOauth());
    if (!ObjectUtils.isEmpty(user.imageUrl)) this.setImageUrl(user.getImageUrl());

    return this;
  }
}
