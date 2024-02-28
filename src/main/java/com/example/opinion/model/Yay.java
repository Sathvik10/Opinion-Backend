package com.example.opinion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "yay")
@TypeAlias("Yay")
public class Yay {
  @Id private String yayId;
  private String convoId;
  private String userId;
}
