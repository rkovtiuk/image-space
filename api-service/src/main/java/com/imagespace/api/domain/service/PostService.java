package com.imagespace.api.domain.service;

import com.imagespace.api.common.dto.PostDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    ProducerService kafkaProducer;
    SourceService imageService;

    public void createPost(UUID accountId, byte[] sourceData) {
        var postId = UUID.randomUUID();
        var sourceId = UUID.randomUUID();

        var post = new PostDto(postId, accountId, sourceId);
        imageService.savePostSource(sourceId, sourceData, () -> kafkaProducer.sendCreatePostEvent(post));
    }

}
