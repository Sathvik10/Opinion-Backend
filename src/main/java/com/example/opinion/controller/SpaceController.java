package com.example.opinion.controller;

import com.example.opinion.common.constant.Constant;
import com.example.opinion.handler.SpaceHandler;
import com.example.opinion.model.Space;
import com.example.opinion.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/space")
public class SpaceController {

  private final SpaceService spaceService;

  @Autowired private SpaceHandler spaceHandler;

  @Autowired
  public SpaceController(SpaceService spaceService) {
    this.spaceService = spaceService;
  }

  @PostMapping
  public Mono<ResponseEntity<Space>> createSpace(@RequestBody Space space) {
    return spaceHandler
        .createSpace(space)
        .map(savedSpace -> ResponseEntity.status(HttpStatus.CREATED).body(savedSpace));
  }

  @GetMapping
  public Flux<Space> getAllSpaces() {
    return spaceService.getAllSpaces();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Space>> getSpaceById(@PathVariable String id) {
    return spaceService
        .getSpaceById(id)
        .map(Space -> ResponseEntity.ok(Space))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Space>> updateSpace(@RequestBody Space Space) {
    return spaceService
        .updateSpace(Space)
        .map(updatedSpace -> ResponseEntity.ok(updatedSpace))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteSpace(@PathVariable String id) {
    return spaceService
        .deleteSpace(id)
        .then(Mono.just(ResponseEntity.noContent().<Void>build()))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/entryRequest")
  public Mono<ResponseEntity<String>> createEntryRequest(
      @RequestParam String spaceId, @RequestParam String userId) {
    return spaceHandler
        .createEntryRequest(spaceId, userId)
        .map(context -> ResponseEntity.ok(context.remove(Constant.MESSAGE)));
  }
}
