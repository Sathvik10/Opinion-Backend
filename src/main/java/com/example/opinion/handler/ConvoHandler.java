package com.example.opinion.handler;

import com.example.opinion.model.Convo;
import com.example.opinion.service.ConvoService;
import com.example.opinion.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ConvoHandler {

    @Autowired
    SpaceService spaceService;


    @Autowired
    ConvoService convoService;

    public Mono<Convo> createConvo(Convo convo) {
        return spaceService
                .getSpaceById(convo.getSpaceId())
                .flatMap(
                        space -> {
                            if (space.getOwners().contains(convo.getUserId())) {
                                convo.setNayCount(0);
                                convo.setYayCount(0);
                                return convoService.createConvo(convo);
                            } else {
                                return Mono.error(new IllegalArgumentException("User doesn't have access"));
                            }
                        })
                .switchIfEmpty(Mono.error(new RuntimeException("Space Doesn't Exist")));
    }
}
