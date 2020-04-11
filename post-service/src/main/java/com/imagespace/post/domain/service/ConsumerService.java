package com.imagespace.post.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagespace.post.common.dto.EventDto;
import com.imagespace.post.common.dto.PostDto;
import com.imagespace.post.config.queue.KafkaEventConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.StringUtils.EMPTY;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerService {

    PostService postService;
    ObjectMapper objectMapper;
    KafkaEventConfig kafkaEventConfig;

    @KafkaListener(topics = "${kafka-properties.topics.post}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvents(ConsumerRecord<String, String> cr) throws JsonProcessingException {
        log.info("Kafka consumer received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(), typeIdHeader(cr.headers()), cr.value(), cr.toString());
        EventDto<PostDto> payload = objectMapper.readValue(cr.value(), new TypeReference<EventDto<PostDto>>() {});
        var eventName = ofNullable(payload).map(EventDto::getEventName).orElse(EMPTY);

        if (kafkaEventConfig.getCreatePost().equals(eventName)) {
            processCreatingPost(payload, cr.offset(), cr.key());
        } else if (kafkaEventConfig.getDeleteSource().equals(eventName)) {
            processDeletingPost(payload, cr.offset(), cr.key());
        } else {
            log.warn("Msg in {} offset with key {} in source topic will be filtered out because of unexpected event name {}", cr.offset(), cr.key(), eventName);
        }
    }

    private void processCreatingPost(EventDto<PostDto> payload, long offset, String key) {
        log.info("Start creating post from kafka msg in {} offset with key {}", offset, key);
        ofNullable(payload).map(EventDto::getPayload).ifPresentOrElse(
            post -> postService.createPost(post.getSourceId(), post.getAccountId()),
            () -> log.error("Empty Post payload in {} offset with key {}", offset, key));
    }

    private void processDeletingPost(EventDto<PostDto> payload, long offset, String key) {
        log.info("Start removing source from kafka msg in {} offset with key {}", offset, key);
        ofNullable(payload).map(EventDto::getPayload).ifPresentOrElse(
            post -> postService.deletePost(post.getId(), post.getAccountId()),
            () -> log.error("Empty Post payload in {} offset with key {}", offset, key));
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
            .filter(header -> header.key().equals("__TypeId__"))
            .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}
