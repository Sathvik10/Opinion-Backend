package com.example.opinion.service;

import com.example.opinion.model.Yay;
import com.example.opinion.repository.YayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class YayService {

  private final YayRepository yayRepository;

  @Autowired
  public YayService(YayRepository yayRepository) {
    this.yayRepository = yayRepository;
  }

  public Mono<Yay> createYay(Yay yay) {
    return yayRepository.save(yay);
  }

  // Other methods like getting, updating, deleting yay can be implemented similarly
}
