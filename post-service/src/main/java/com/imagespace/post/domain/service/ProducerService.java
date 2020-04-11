package com.imagespace.post.domain.service;

import com.imagespace.post.common.dto.EventDto;
import com.imagespace.post.common.dto.SourceDto;
import com.imagespace.post.common.dto.SubscriptionDto;
import com.imagespace.post.config.queue.KafkaEventConfig;
import com.imagespace.post.config.queue.KafkaTopicConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProducerService {

    KafkaTopicConfig kafkaTopicConfig;
    KafkaEventConfig kafkaEventConfig;
    KafkaTemplate<String, EventDto> template;

    public Optional<SendResult<String, EventDto>> sendUpdateSubscriptionPriority(UUID followerId, UUID followingId) {
        var eventName = kafkaEventConfig.getUpdateSubscriptionPriority();
        var event = new EventDto<>(eventName, new SubscriptionDto(null, followerId, followingId));
        return sendEvent(event, kafkaTopicConfig.getSubscription(), followerId.toString());
    }

    public Optional<SendResult<String, EventDto>> sendCreateSourceEvent(SourceDto source) {
        var eventName = kafkaEventConfig.getCreateSource();
        var event = new EventDto<>(eventName, source);
        return sendEvent(event, kafkaTopicConfig.getSource(), source.getId().toString());
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
