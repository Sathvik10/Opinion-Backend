package com.example.opinion.service;

import com.example.opinion.model.UserProfile;
import com.example.opinion.repository.UserProfileRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserProfileService {

  private final UserProfileRepository userProfileRepository;
  @Autowired SpaceService spaceService;

  @Autowired
  public UserProfileService(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  public Mono<UserProfile> createUserProfile(UserProfile userProfile) {

    return spaceService.getSpaceByUserId(userProfile.getUserId())
        .flatMap(
            space -> {
              userProfile.setOwnedSpaces(Arrays.asList(space.getSpaceId()));
              userProfile.setFollowedSpaces(Arrays.asList(space.getSpaceId()));
              return userProfileRepository.save(userProfile);
            })
        .switchIfEmpty(Mono.error(new RuntimeException("User ID doesn't exist")));
  }

  public Flux<UserProfile> getAllUserProfiles() {
    return userProfileRepository.findAll();
  }

  public Mono<UserProfile> getUserProfileById(String id) {
    return userProfileRepository.findById(id);
  }

  public Mono<UserProfile> updateUserProfile(UserProfile userProfile) {
    return userProfileRepository.save(userProfile);
  }

  public Mono<Void> deleteUserProfile(String id) {
    return userProfileRepository.deleteById(id);
  }

  public Mono<UserProfile> getUserProfileByUserId(String id) {
    return userProfileRepository.findByUserId(id);
  }

  public Mono<UserProfile> addSpaceToOwnedSpaces(String userId, String spaceId) {
    return getUserProfileByUserId(userId)
        .flatMap(
            userProfile -> {
              userProfile.getOwnedSpaces().add(spaceId);
              return updateUserProfile(userProfile);
            });
  }
}
