package com.example.opinion.service;

import com.example.opinion.model.Nay;
import com.example.opinion.repository.NayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NayService {

  private final NayRepository nayRepository;

  @Autowired
  public NayService(NayRepository nayRepository) {
    this.nayRepository = nayRepository;
  }

  public Mono<Nay> createNay(Nay nay) {
    return nayRepository.save(nay);
  }

  // Other methods like getting, updating, deleting nay can be implemented similarly
}
