package com.example.opinion.handler;

import com.example.opinion.common.Context;
import com.example.opinion.common.constant.Constant;
import com.example.opinion.common.constant.RequestConstant;
import com.example.opinion.common.constant.SpaceConstant;
import com.example.opinion.common.enums.RequestType;
import com.example.opinion.model.Request;
import com.example.opinion.model.Space;
import com.example.opinion.service.GeneralService;
import com.example.opinion.service.RequestService;
import com.example.opinion.service.SpaceService;
import com.example.opinion.service.UserProfileService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SpaceHandler {

  @Autowired RequestService requestService;

  @Autowired SpaceService spaceService;

  @Autowired UserProfileService userProfileService;

  @Autowired
  GeneralService generalService;

  public Mono<Context> createEntryRequest(String spaceId, String userId) {
    return requestService
        .requestExists(RequestType.SPACE_ENTRY, spaceId, userId)
        .flatMap(
            exists -> {
              if (Boolean.TRUE.equals(exists)) {
                Context context = new Context();
                context.put(Constant.MESSAGE, "Existing Request");
                return Mono.just(context);
              } else {
                return spaceService
                    .getSpaceById(spaceId)
                    .map(space -> spaceService.handleEntryRequest(space, userId));
              }
            })
        .flatMap(context -> generalService.updateDetails(context));
  }

  // Minimum Payload
  //    {
  //    "creatorId": "659b332d7c0ba344885c52e2",
  //    "name": "Chumma",
  //    "type": "MEMBERS",
  //    "visibility" : "PUBLIC",
  //    "description": "Chumma"
  //    }
  public Mono<Space> createSpace(Space space) {
    space.setOwners(Arrays.asList(space.getCreatorId()));
    space.setMembers(Arrays.asList(space.getCreatorId()));
    return spaceService
        .createSpace(space)
        .flatMap(
            savedSpace ->
                userProfileService
                    .addSpaceToOwnedSpaces(savedSpace.getCreatorId(), savedSpace.getSpaceId())
                    .thenReturn(savedSpace));
  }
}
