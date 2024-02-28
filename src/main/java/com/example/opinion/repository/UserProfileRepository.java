package com.example.opinion.repository;

import com.example.opinion.model.UserProfile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserProfileRepository extends ReactiveMongoRepository<UserProfile, String> {
  // Custom methods or queries can be added here if needed
  Mono<UserProfile> findByUserId(String userId);
}
