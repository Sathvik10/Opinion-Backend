package com.example.opinion.repository;

import com.example.opinion.model.Request;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RequestRepository extends ReactiveMongoRepository<Request, String> {
  // Define custom queries if needed
  Flux<Request> findByTypeAndDestinationIdAndRequestFromContaining(
      String type, String destinationId, String userId);

  @Query("{'type': ?0, 'destinationId': ?1, 'requestFrom': { $in: [ ?2 ] }}")
  Mono<Boolean> existsByTypeAndDestinationIdAndRequestFrom(
      String type, String destinationId, String userId);

  @Query("{'type': ?0, 'destinationId': ?1, 'requestFrom': { $in: [ ?2 ] }}")
  Mono<Long> countByTypeAndDestinationIdAndRequestFrom(
      String type, String destinationId, String userId);

  Flux<Request> findByTypeAndDestinationId(String type, String destinationId);
}
