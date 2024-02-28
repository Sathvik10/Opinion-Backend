package com.example.opinion.controller;

import com.example.opinion.model.Yay;
import com.example.opinion.service.YayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/yays")
public class YayController {

  private final YayService yayService;

  @Autowired
  public YayController(YayService yayService) {
    this.yayService = yayService;
  }

  @PostMapping
  public Mono<ResponseEntity<Yay>> createYay(@RequestBody Yay yay) {
    return yayService
        .createYay(yay)
        .map(savedYay -> ResponseEntity.status(HttpStatus.CREATED).body(savedYay));
  }
}
