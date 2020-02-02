package com.imagespace.api.domain.service;

import com.imagespace.api.common.dto.EventDto;
import com.imagespace.api.common.dto.PostDto;
import com.imagespace.api.common.dto.SourceDto;
import com.imagespace.api.config.kafka.KafkaConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProducerService {

    KafkaConfig kafkaConfig;
    KafkaTemplate<String, EventDto> template;

    public Optional<SendResult<String, EventDto>> sendCreateSourceEvent(SourceDto source) {
        var event = new EventDto<>(kafkaConfig.getCreateSourceEventName(), source);
        return sendEvent(event, kafkaConfig.getSourceTopicName(), source.getId().toString());
    }

    public Optional<SendResult<String, EventDto>> sendCreatePostEvent(PostDto post) {
        var event = new EventDto<>(kafkaConfig.getCreateSourceEventName(), post);
        return sendEvent(event, kafkaConfig.getPostTopicName(), post.getId().toString());
    }

    private Optional<SendResult<String, EventDto>> sendEvent(EventDto event, String topic, String key) {
        try {
            SendResult<String, EventDto> result = template.send(topic, key, event).get();
            log.info("Successfully sent event to topic '{}' with key '{}'. Event data: '{}'.", topic, key, event);
            return Optional.of(result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Can't send event to the topic '{}' with key '{}'. Event: {}. Details:\n ", topic, key, event, e);
        }

        return Optional.empty();
    }

}
