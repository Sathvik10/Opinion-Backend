package com.example.opinion.repository;

import com.example.opinion.model.Space;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SpaceRepository extends ReactiveMongoRepository<Space, String> {
  // Custom methods or queries can be added here if needed
  Mono<Space> findByCreatorId(String creatorId);
}
