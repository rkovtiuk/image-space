package com.imagespace.post.domain.service;

import com.imagespace.post.common.dto.SourceDto;
import com.imagespace.post.common.event.BaseEvent;
import com.imagespace.post.common.event.SourceEvent;
import com.imagespace.post.config.kafka.KafkaConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProducerService {

    KafkaConfig kafkaConfig;
    KafkaTemplate<String, BaseEvent> template;

    public Optional<SendResult<String, BaseEvent>> sendCreateSourceEvent(SourceDto source) {
        var event = new SourceEvent(kafkaConfig.getCreateSourceEventName(), source);
        return sendEvent(event, kafkaConfig.getSourceTopicName(), source.getId().toString());
    }

    private Optional<SendResult<String, BaseEvent>> sendEvent(BaseEvent event, String topic, String key) {
        try {
            SendResult<String, BaseEvent> result = template.send(topic, key, event).get();
            log.info("Successfully sent event to topic '{}' with key '{}'. Event data: '{}'.", topic, key, event);
            return Optional.of(result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Can't send event to the topic '{}' with key '{}'. Event: {}. Details:\n ", topic, key, event, e);
        }

        return Optional.empty();
    }

}
