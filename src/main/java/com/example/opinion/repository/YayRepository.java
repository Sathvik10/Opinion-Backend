package com.example.opinion.repository;

import com.example.opinion.model.Yay;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YayRepository extends ReactiveMongoRepository<Yay, String> {
  // Custom methods or queries can be added here if needed
}
