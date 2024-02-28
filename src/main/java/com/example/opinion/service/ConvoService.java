package com.example.opinion.service;

import com.example.opinion.model.Convo;
import com.example.opinion.repository.ConvoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ConvoService {

  private final ConvoRepository convoRepository;

  @Autowired private SpaceService spaceService;

  @Autowired
  public ConvoService(ConvoRepository convoRepository) {
    this.convoRepository = convoRepository;
  }

  public Mono<Convo> createConvo(Convo convo) {
    return convoRepository.save(convo);
  }

  public Flux<Convo> getAllConvos() {
    return convoRepository.findAll();
  }

  public Mono<Convo> getConvoById(String id) {
    return convoRepository.findById(id);
  }

  public Mono<Convo> updateConvo(String id, Convo convo) {
    return convoRepository
        .findById(id)
        .flatMap(
            existingConvo -> {
              existingConvo.setUserId(convo.getUserId());
              existingConvo.setDescription(convo.getDescription());
              existingConvo.setTags(convo.getTags());
              existingConvo.setType(convo.getType());
              existingConvo.setContent(convo.getContent());
              existingConvo.setContentLink(convo.getContentLink());
              existingConvo.setSpaceId(convo.getSpaceId());
              existingConvo.setYayCount(convo.getYayCount());
              existingConvo.setNayCount(convo.getNayCount());
              // Update other fields as needed
              return convoRepository.save(existingConvo);
            });
  }

  public Mono<Void> deleteConvo(String id) {
    return convoRepository.deleteById(id);
  }
}
