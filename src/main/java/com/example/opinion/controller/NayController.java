package com.example.opinion.controller;

import com.example.opinion.model.Nay;
import com.example.opinion.service.NayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/nays")
public class NayController {

  private final NayService nayService;

  @Autowired
  public NayController(NayService nayService) {
    this.nayService = nayService;
  }

  @PostMapping
  public Mono<ResponseEntity<Nay>> createNay(@RequestBody Nay nay) {
    return nayService
        .createNay(nay)
        .map(savedNay -> ResponseEntity.status(HttpStatus.CREATED).body(savedNay));
  }
}
