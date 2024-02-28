package com.example.opinion.model;

import com.example.opinion.common.enums.ConvoType;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "convo")
@TypeAlias("Convo")
public class Convo {
  @Id private String convoId;
  private String userId;
  private String description;
  private List<String> tags;
  private ConvoType type;
  private String content;
  private String contentLink;
  private String spaceId;
  private int yayCount;
  private int nayCount;

  // Constructors, getters, setters, etc.
}
