package com.imagespace.source.domain.service;

import com.imagespace.source.config.kafka.KafkaConsumerConfiguration;
import com.imagespace.source.domain.handler.SourceHandler;
import com.imagespace.source.dto.EventDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerService {

    String EMPTY_EVENT_NAME = "";

    SourceHandler imageHandler;
    KafkaConsumerConfiguration consumerConfig;

    @KafkaListener(topics = "${kafka-properties.source-topic-name}")
    public void listenAsObject(ConsumerRecord<String, EventDto> cr, @Payload EventDto payload) {
        log.info("Start consuming msg, received key {}: Type [{}] | Payload: {} | Record: {}",
                cr.key(), typeIdHeader(cr.headers()), payload, cr.toString());

        var eventName = Optional.ofNullable(payload).map(EventDto::getEventName).orElse(EMPTY_EVENT_NAME);
        if (eventName.equals(consumerConfig.getCreateSourceEventName())) {
            processCreatingSource(payload, cr.offset(), cr.key());
        } else if (eventName.equals(consumerConfig.getDeleteSourceEventName())) {
            processDeletingSource(payload, cr.offset(), cr.key());
        } else if (eventName.equals(EMPTY_EVENT_NAME)) {
            log.warn("Msg in {} offset with key {} in source topic will be filtered out because of empty event name", cr.offset(), cr.key());
        } else {
            log.warn("Msg in {} offset with key {} in source topic will be filtered out because of unexpected event name {}", cr.offset(), cr.key(), eventName);
        }
    }

    private void processCreatingSource(EventDto payload, long offset, String key) {
        log.info("Start creating source from kafka msg in {} offset with key {}", offset, key);
        Optional.ofNullable(payload).map(EventDto::getBody).ifPresentOrElse(
            sourceDto -> imageHandler.save(sourceDto.getId(), sourceDto.getSource()),
            () -> log.error("Empty Source payload in {} offset with key {}", offset, key));
    }

    private void processDeletingSource(EventDto payload, long offset, String key) {
        log.info("Start removing source from kafka msg in {} offset with key {}", offset, key);
        Optional.ofNullable(payload).map(EventDto::getBody).ifPresentOrElse(
            sourceDto -> imageHandler.delete(sourceDto.getId()),
            () -> log.error("Empty Source payload in {} offset with key {}", offset, key));

    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
            .filter(header -> header.key().equals("__TypeId__"))
            .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}
