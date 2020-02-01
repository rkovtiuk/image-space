package com.imagespace.post.domain.service;

import com.imagespace.post.common.dto.EventDto;
import com.imagespace.post.config.kafka.KafkaEventProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerService {

    String EMPTY_EVENT_NAME = "";

    PostService postService;
    KafkaEventProperties eventProperties;

    @KafkaListener(
        topics = "${kafka-properties.post-topic-name}",
        groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvents(ConsumerRecord<String, EventDto> cr, @Payload EventDto payload) {
        log.info("Kafka consumer received key {}: Type [{}] | Payload: {} | Record: {}",
                cr.key(), typeIdHeader(cr.headers()), payload, cr.toString());

        var eventName = Optional.ofNullable(payload).map(EventDto::getEventName).orElse(EMPTY_EVENT_NAME);
        if (eventName.equals(eventProperties.getCreateSourceEventName())) {
            processCreatingPost(payload, cr.offset(), cr.key());
        } else if (eventName.equals(eventProperties.getDeleteSourceEventName())) {
            processDeletingPost(payload, cr.offset(), cr.key());
        } else if (eventName.equals(EMPTY_EVENT_NAME)) {
            log.warn("Msg in {} offset with key {} in source topic will be filtered out because of empty event name", cr.offset(), cr.key());
        } else {
            log.warn("Msg in {} offset with key {} in source topic will be filtered out because of unexpected event name {}", cr.offset(), cr.key(), eventName);
        }
    }

    private void processCreatingPost(EventDto payload, long offset, String key) {
        log.info("Start creating post from kafka msg in {} offset with key {}", offset, key);
        Optional.ofNullable(payload).map(EventDto::getBody).ifPresentOrElse(
            post -> postService.createPost(post.getSourceId(), post.getAccountId()),
            () -> log.error("Empty Post payload in {} offset with key {}", offset, key));
    }

    private void processDeletingPost(EventDto payload, long offset, String key) {
        log.info("Start removing source from kafka msg in {} offset with key {}", offset, key);
        Optional.ofNullable(payload).map(EventDto::getBody).ifPresentOrElse(
            post -> postService.deletePost(post.getId(), post.getAccountId()),
            () -> log.error("Empty Post payload in {} offset with key {}", offset, key));

    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}