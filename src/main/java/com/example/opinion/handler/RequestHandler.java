package com.example.opinion.handler;

import com.example.opinion.common.Context;
import com.example.opinion.common.constant.*;
import com.example.opinion.common.enums.RequestStatus;
import com.example.opinion.common.enums.RequestType;
import com.example.opinion.model.Request;
import com.example.opinion.model.Space;
import com.example.opinion.model.UserProfile;
import com.example.opinion.service.GeneralService;
import com.example.opinion.service.RequestService;
import com.example.opinion.service.SpaceService;
import com.example.opinion.service.UserService;
import com.example.opinion.wrapper.UserWrapper;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RequestHandler {

  @Autowired private UserService userService;

  @Autowired private SpaceService spaceService;

  @Autowired private RequestService requestService;

  @Autowired private GeneralService generalService;
  Map<RequestType, Function<Context, Context>> mapping = new HashMap<>();

  @PostConstruct
  public void init() {
    mapping.put(RequestType.SPACE_ENTRY, this::processSpaceEntryRequest);
  }

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

  public Mono<Context> processRequest(String requestId, String userId, String spaceId) {
    return generalService
        .fetchDetailsSpaceAndRequest(spaceId, requestId)
        .map(
            context -> {
              context.put(UserConstant.USER_ID, userId);
              var function = mapping.get(context.getOrThrow(RequestConstant.REQUEST).getType());
              return function.apply(context);
            })
        .flatMap(context -> generalService.updateDetails(context));
  }

  private Context processSpaceEntryRequest(Context context) {
    String userId = context.getOrThrow(UserConstant.USER_ID);
    Space space = context.getOrThrow(SpaceConstant.SPACE);
    Request request = context.getOrThrow(RequestConstant.REQUEST);

    if (RequestStatus.RESOLVED == request.getStatus()) {
      context.put(Constant.MESSAGE, "Duplicate Request");
      return context;
    }

    if (!request.getRequestTo().contains(userId)) {
      context.put(Constant.MESSAGE, "Invalid User");
      return context;
    }

    if (!space.getOwners().contains(userId)) {
      context.put(Constant.MESSAGE, "User doesn't have access");
      return context;
    }

    space.getMembers().addAll(request.getRequestFrom());
    context.put(SpaceConstant.UPDATE, true);

    request.setStatus(RequestStatus.RESOLVED);
    context.put(RequestConstant.UPDATE, true);

    UserProfile userProfile = new UserProfile();
    userProfile.setUserId(request.getRequestFrom().get(0));
    userProfile.setFollowedSpaces(List.of(space.getSpaceId()));

    UserWrapper userWrapper = new UserWrapper(userProfile);
    userWrapper.setUpdateUserPartially(true);
    context.put(UserConstant.USER_WRAPPER, userWrapper);

    context.put(Constant.MESSAGE, "Successful");
    return context;
  }
}
