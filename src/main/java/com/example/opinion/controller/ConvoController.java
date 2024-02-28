package com.example.opinion.controller;

import com.example.opinion.handler.ConvoHandler;
import com.example.opinion.model.Convo;
import com.example.opinion.service.ConvoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/convo")
public class ConvoController {

  private final ConvoHandler convoHandler;

  @Autowired ConvoService convoService;

  @Autowired
  public ConvoController(ConvoHandler convoHandler) {
    this.convoHandler = convoHandler;
  }

  @PostMapping
  public Mono<ResponseEntity<Convo>> createConvo(@RequestBody Convo convo) {
    return convoHandler
        .createConvo(convo)
        .map(savedConvo -> ResponseEntity.status(HttpStatus.CREATED).body(savedConvo))
            .doOnError(throwable -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
  }

  @GetMapping
  public Flux<Convo> getAllConvos() {
    return convoService.getAllConvos();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Convo>> getConvoById(@PathVariable String id) {
    return convoService
        .getConvoById(id)
        .map(convo -> ResponseEntity.ok(convo))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Convo>> updateConvo(
      @PathVariable String id, @RequestBody Convo convo) {
    return convoService
        .updateConvo(id, convo)
        .map(updatedConvo -> ResponseEntity.ok(updatedConvo))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteConvo(@PathVariable String id) {
    return convoService
        .deleteConvo(id)
        .then(Mono.just(ResponseEntity.noContent().<Void>build()))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
