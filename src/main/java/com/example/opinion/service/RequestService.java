package com.example.opinion.service;

import com.example.opinion.common.enums.RequestType;
import com.example.opinion.model.Request;
import com.example.opinion.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestService {

  private final RequestRepository requestsRepository;

  @Autowired
  public RequestService(RequestRepository requestsRepository) {
    this.requestsRepository = requestsRepository;
  }

  public Mono<Request> createRequest(Request request) {
    return requestsRepository.save(request);
  }

  public Flux<Request> getAllRequests() {
    return requestsRepository.findAll();
  }

  public Mono<Request> getRequestById(String id) {
    return requestsRepository.findById(id);
  }

  public Mono<Request> updateRequest(Request request) {
    return requestsRepository.save(request);
  }

  public Mono<Boolean> requestExists(RequestType type, String spaceId, String userId) {
    return requestsRepository
        .findByTypeAndDestinationId(type.name(), spaceId)
        .any(request -> request.getRequestFrom().contains(userId))
        .switchIfEmpty(Mono.just(false));
  }

  // Other service methods such as update and delete can be added similarly
}
