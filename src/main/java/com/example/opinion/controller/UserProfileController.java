package com.example.opinion.controller;

import com.example.opinion.model.UserProfile;
import com.example.opinion.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user-profiles")
public class UserProfileController {

  private final UserProfileService userProfileService;

  @Autowired
  public UserProfileController(UserProfileService userProfileService) {
    this.userProfileService = userProfileService;
  }

  @PostMapping
  public Mono<ResponseEntity<UserProfile>> createUserProfile(@RequestBody UserProfile userProfile) {
    return userProfileService
        .createUserProfile(userProfile)
        .map(savedProfile -> ResponseEntity.status(HttpStatus.CREATED).body(savedProfile));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<UserProfile>> getUserProfileById(@PathVariable String id) {
    return userProfileService
        .getUserProfileByUserId(id)
        .map(profile -> ResponseEntity.ok(profile))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<UserProfile>> updateUserProfile(@RequestBody UserProfile userProfile) {
    return userProfileService
        .updateUserProfile(userProfile)
        .map(updatedProfile -> ResponseEntity.ok(updatedProfile))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteUserProfile(@PathVariable String id) {
    return userProfileService
        .deleteUserProfile(id)
        .then(Mono.just(ResponseEntity.noContent().<Void>build()))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
