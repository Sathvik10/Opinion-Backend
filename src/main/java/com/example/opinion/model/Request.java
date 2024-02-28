package com.example.opinion.model;

import com.example.opinion.common.enums.RequestStatus;
import com.example.opinion.common.enums.RequestType;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "request")
@TypeAlias("Request")
public class Request {
  @Id private String requestId;
  private RequestType type;
  private List<String> requestFrom;
  private List<String> requestTo;
  private String destinationId;
  private long timestamp;
  private RequestStatus status;

  public Request(
      RequestType type,
      List<String> requestFrom,
      List<String> requestTo,
      String destinationId,
      long timestamp,
      RequestStatus status) {
    this.type = type;
    this.requestFrom = requestFrom;
    this.requestTo = requestTo;
    this.destinationId = destinationId;
    this.timestamp = timestamp;
    this.status = status;
  }
  // Constructors, getters, setters, etc.
}
