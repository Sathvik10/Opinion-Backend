package com.example.opinion.service;

import com.example.opinion.common.Context;
import com.example.opinion.common.constant.RequestConstant;
import com.example.opinion.common.constant.SpaceConstant;
import com.example.opinion.common.constant.UserConstant;
import com.example.opinion.common.enums.RequestStatus;
import com.example.opinion.common.enums.RequestType;
import com.example.opinion.common.enums.Visibility;
import com.example.opinion.model.Request;
import com.example.opinion.model.Space;
import com.example.opinion.model.User;
import com.example.opinion.model.UserProfile;
import com.example.opinion.repository.SpaceRepository;

import java.util.Arrays;
import java.util.List;

import com.example.opinion.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SpaceService {

  private final SpaceRepository spaceRepository;

  @Autowired
  public SpaceService(SpaceRepository spaceRepository) {
    this.spaceRepository = spaceRepository;
  }

  public Mono<Space> createSpace(Space space) {
    space.setTimestamp(System.currentTimeMillis());
    return spaceRepository.save(space);
  }

  public Flux<Space> getAllSpaces() {
    return spaceRepository.findAll();
  }

  public Mono<Space> getSpaceById(String id) {
    return spaceRepository.findById(id);
  }

  public Mono<Space> updateSpace(Space space) {
    return spaceRepository.save(space);
  }

  public Mono<Void> deleteSpace(String id) {
    return spaceRepository.deleteById(id);
  }

  public Mono<Space> getSpaceByUserId(String userId) {
    return spaceRepository.findByCreatorId(userId);
  }

  public boolean isPublic(Space space) {
    return Visibility.PUBLIC == space.getVisibility();
  }

  public Context handleEntryRequest(Space space, String userId) {
    Context context = new Context();
    if (isPublic(space)) {
      space.getMembers().add(userId);
      context.put(SpaceConstant.UPDATE, true);
      context.put(SpaceConstant.SPACE, space);

      UserProfile userProfile = new UserProfile();
      userProfile.setUserId(userId);
      userProfile.setFollowedSpaces(Arrays.asList(space.getSpaceId()));

      UserWrapper userWrapper = new UserWrapper(userProfile);
      userWrapper.setUpdateUserProfilePartially(true);

      context.put(UserConstant.USER_WRAPPER, userWrapper);
      return context;
    }
    Request request =
        new Request(
            RequestType.SPACE_ENTRY,
            List.of(userId),
            space.getOwners(),
            space.getSpaceId(),
            System.currentTimeMillis(),
            RequestStatus.OPEN);
    context.put(RequestConstant.REQUEST, request);
    context.put(RequestConstant.UPDATE, true);
    return context;
  }
}
