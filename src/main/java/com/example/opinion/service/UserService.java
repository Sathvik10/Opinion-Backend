package com.example.opinion.service;

import com.example.opinion.model.Space;
import com.example.opinion.model.User;
import com.example.opinion.model.UserProfile;
import com.example.opinion.repository.UserRepository;
import com.example.opinion.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired private SpaceService spaceService;

  @Autowired private UserProfileService userProfileService;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Mono<User> createUser(User user) {
    user.setLastTimeStamp(System.currentTimeMillis());
    return userRepository
        .save(user)
        .flatMap(
            savedUser ->
                spaceService.createSpace(Space.createInitSpace(savedUser)).thenReturn(savedUser));
  }

  public Mono<User> getUserById(String userId) {
    return userRepository.findById(userId);
  }

  public Flux<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Mono<User> updateUser(User user) {
    // Assuming the user exists in the database
    user.setLastTimeStamp(System.currentTimeMillis());
    return userRepository.save(user);
  }

  public Mono<UserWrapper> updateUser(UserWrapper userWrapper) {
    if (ObjectUtils.isEmpty(userWrapper)) return Mono.just(new UserWrapper());
    Mono<User> userMono = Mono.just(new User());
    if (userWrapper.isUpdateUser()) {
      userMono = updateUser(userWrapper.getUser());
    }

    if (userWrapper.isUpdateUserPartially()) {
      userMono =
          getUserById(userWrapper.getUser().getUserId())
              .flatMap(user -> updateUser(user.copyPartially(userWrapper.getUser())));
    }

    Mono<UserProfile> userProfileMono = Mono.just(new UserProfile());
    if (userWrapper.isUpdateUserProfile()) {
      userProfileMono = userProfileService.updateUserProfile(userWrapper.getUserProfile());
    }

    if (userWrapper.isUpdateUserProfilePartially()) {
      userProfileMono =
          userProfileService
              .getUserProfileByUserId(userWrapper.getUserProfile().getUserId())
              .flatMap(
                  userProfile ->
                      userProfileService.updateUserProfile(
                          userProfile.copyPartially(userWrapper.getUserProfile())));
    }

    return Mono.zip(userMono, userProfileMono)
            .map(tuple -> new UserWrapper(tuple.getT1(), tuple.getT2()))
            .switchIfEmpty(Mono.just(new UserWrapper()));
  }

  public Mono<Void> deleteUserById(String userId) {
    return userRepository.deleteById(userId);
  }

  public Mono<Long> countUsers() {
    return userRepository.count();
  }

  // Other methods based on your business logic or requirements
}
