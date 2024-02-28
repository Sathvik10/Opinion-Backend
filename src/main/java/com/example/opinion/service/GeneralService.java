package com.example.opinion.service;

import com.example.opinion.common.Context;
import com.example.opinion.common.constant.RequestConstant;
import com.example.opinion.common.constant.SpaceConstant;
import com.example.opinion.common.constant.UserConstant;
import com.example.opinion.model.Request;
import com.example.opinion.model.Space;
import com.example.opinion.model.User;
import com.example.opinion.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GeneralService {

  @Autowired private UserService userService;

  @Autowired private SpaceService spaceService;

  @Autowired private RequestService requestService;

  public Mono<Context> fetchDetails(String userId, String spaceId, String requestId) {
    Mono<User> userMono = userService.getUserById(userId);
    Mono<Space> spaceMono = spaceService.getSpaceById(spaceId);
    Mono<Request> requestMono = requestService.getRequestById(requestId);

    return Mono.zip(userMono, spaceMono, requestMono)
        .map(
            tuple -> {
              User user = tuple.getT1();
              Space space = tuple.getT2();
              Request request = tuple.getT3();

              Context context = new Context();
              context.put(UserConstant.USER, user);
              context.put(SpaceConstant.SPACE, space);
              context.put(RequestConstant.REQUEST, request);
              return context;
            });
  }

  public Mono<Context> fetchDetailsUserAndSpace(String userId, String spaceId) {
    Mono<User> userMono = userService.getUserById(userId);
    Mono<Space> spaceMono = spaceService.getSpaceById(spaceId);

    return Mono.zip(userMono, spaceMono)
        .map(
            tuple -> {
              User user = tuple.getT1();
              Space space = tuple.getT2();

              Context context = new Context();
              context.put(UserConstant.USER, user);
              context.put(SpaceConstant.SPACE, space);
              return context;
            });
  }

  public Mono<Context> fetchDetailsSpaceAndRequest(String spaceId, String requestId) {
    Mono<Space> spaceMono = spaceService.getSpaceById(spaceId);
    Mono<Request> requestMono = requestService.getRequestById(requestId);

    return Mono.zip(spaceMono, requestMono)
        .map(
            tuple -> {
              Space space = tuple.getT1();
              Request request = tuple.getT2();

              Context context = new Context();
              context.put(SpaceConstant.SPACE, space);
              context.put(RequestConstant.REQUEST, request);
              return context;
            });
  }

  public Mono<Context> updateDetails(Context context) {
    Mono<Space> spaceMono =
        context.removeOrDefault(SpaceConstant.UPDATE, false)
            ? spaceService.updateSpace(context.removeOrThrow(SpaceConstant.SPACE))
            : Mono.just(new Space());

    Mono<Request> requestMono =
        context.removeOrDefault(RequestConstant.UPDATE, false)
            ? requestService.updateRequest(context.removeOrThrow(RequestConstant.REQUEST))
            : Mono.just(new Request());

    Mono<UserWrapper> userWrapperMono =
        userService.updateUser(context.remove(UserConstant.USER_WRAPPER));

    return Mono.zip(spaceMono, requestMono, userWrapperMono)
        .flatMap(tuple -> Mono.just(context))
        .switchIfEmpty(Mono.just(context));
  }
}
