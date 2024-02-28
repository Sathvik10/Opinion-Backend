package com.example.opinion.repository;

import com.example.opinion.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
  // You can define custom query methods here if needed
}
