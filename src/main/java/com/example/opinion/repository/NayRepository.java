package com.example.opinion.repository;

import com.example.opinion.model.Nay;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NayRepository extends ReactiveMongoRepository<Nay, String> {
  // Custom methods or queries can be added here if needed
}
