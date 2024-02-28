package com.example.opinion.repository;

import com.example.opinion.model.Convo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvoRepository extends ReactiveMongoRepository<Convo, String> {
  // Custom methods or queries can be added here if needed
}
