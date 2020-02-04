package com.imagespace.source.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagespace.source.common.dto.EventDto;
import com.imagespace.source.config.kafka.KafkaConfig;
import com.imagespace.source.domain.handler.SourceHandler;
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

    ObjectMapper objectMapper;
    SourceHandler sourceHandler;
    KafkaConfig eventProperties;

    @KafkaListener(topics = "${kafka-properties.source-topic-name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenAsObject(ConsumerRecord<String, String> cr) throws JsonProcessingException {
        log.info("Start consuming msg, received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(), typeIdHeader(cr.headers()), cr.value(), cr.toString());
        var payload = objectMapper.readValue(cr.value(), EventDto.class);
        var eventName = ofNullable(payload).map(EventDto::getEventName).orElse(EMPTY);

        if (eventName.equals(eventProperties.getCreateSourceEventName())) {
            processCreatingSource(payload, cr.offset(), cr.key());
        } else if (eventName.equals(eventProperties.getDeleteSourceEventName())) {
            processDeletingSource(payload, cr.offset(), cr.key());
        } else {
            log.warn("Msg in {} offset with key {} in source topic will be filtered out because of unexpected event name {}", cr.offset(), cr.key(), eventName);
        }
    }

    private void processCreatingSource(EventDto payload, long offset, String key) {
        log.info("Start creating source from kafka msg in {} offset with key {}", offset, key);
        ofNullable(payload).map(EventDto::getBody).ifPresentOrElse(
            sourceDto -> sourceHandler
                .save(sourceDto.getId(), sourceDto.getSource())
                .subscribe(sourceDocument -> log.info("Kafka msg in {} offset with key {} had been successfully processed.", offset, key)),
            () -> log.error("Empty Source payload in {} offset with key {}", offset, key));
    }

    private void processDeletingSource(EventDto payload, long offset, String key) {
        log.info("Start removing source from kafka msg in {} offset with key {}", offset, key);
        ofNullable(payload).map(EventDto::getBody).ifPresentOrElse(
            sourceDto -> sourceHandler
                .delete(sourceDto.getId())
                .subscribe(sourceDocument -> log.info("Kafka msg in {} offset with key {} had been successfully processed.", offset, key)),
            () -> log.error("Empty Source payload in {} offset with key {}", offset, key));
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
            .filter(header -> header.key().equals("__TypeId__"))
            .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}
