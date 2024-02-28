package com.example.opinion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nays")
@TypeAlias("Nay")
public class Nay {
  @Id private String nayId;
  private String convoId;
  private String userId;

  // Constructors, getters, setters, etc.
}
