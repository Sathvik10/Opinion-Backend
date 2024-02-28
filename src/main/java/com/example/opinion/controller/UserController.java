package com.example.opinion.controller;

import com.example.opinion.common.constant.Constant;
import com.example.opinion.handler.RequestHandler;
import com.example.opinion.model.User;
import com.example.opinion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired private RequestHandler requestHandler;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userId}")
  public Mono<ResponseEntity<User>> getUserById(@PathVariable String userId) {
    return userService
        .getUserById(userId)
        .map(user -> ResponseEntity.ok(user))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/count")
  public Mono<ResponseEntity<Long>> getUserCount() {
    return userService
        .countUsers()
        .map(count -> ResponseEntity.ok(count))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
    return userService
        .createUser(user)
        .map(savedUser -> ResponseEntity.ok(savedUser))
        .doOnError(error -> ResponseEntity.internalServerError().build());
  }

  @PostMapping("/request")
  public Mono<ResponseEntity<String>> processRequest(
      @RequestParam String requestId,
      @RequestParam String userId,
      @RequestParam(required = false) String spaceId) {
    return requestHandler
        .processRequest(requestId, userId, spaceId)
        .map(context -> ResponseEntity.ok(context.remove(Constant.MESSAGE)));
  }
}
