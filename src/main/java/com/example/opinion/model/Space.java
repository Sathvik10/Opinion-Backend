package com.example.opinion.model;

import com.example.opinion.common.enums.SpaceType;
import com.example.opinion.common.enums.Visibility;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "space")
@TypeAlias("Space")
public class Space {
  @Id private String spaceId;
  private String creatorId;
  private String name;
  private SpaceType type;
  private Visibility visibility;
  private Long timestamp;
  private String description;
  private List<String> owners;
  private List<String> members;

  public static Space createInitSpace(User user) {
    Space space = new Space();
    space.setCreatorId(user.getUserId());
    space.setName(user.getName());
    space.setType(SpaceType.SELF);
    space.setTimestamp(System.currentTimeMillis());
    space.setDescription(user.getName() + " personal space");
    space.setOwners(Arrays.asList(user.getUserId()));
    space.setMembers(Arrays.asList(user.getUserId()));
    return space;
  }
}
