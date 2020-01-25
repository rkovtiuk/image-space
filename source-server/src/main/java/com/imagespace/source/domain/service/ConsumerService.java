package com.imagespace.source.domain.service;

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

    SourceHandler imageHandler;

    @KafkaListener(
            topics = "${kafka-properties.source-topic-name}",
            clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenAsObject(ConsumerRecord<String, EventDto> cr, @Payload EventDto payload) {
        log.info("Start consuming msg, received key {}: Type [{}] | Payload: {} | Record: {}",
                cr.key(), typeIdHeader(cr.headers()), payload, cr.toString());

        Optional
            .ofNullable(payload)
            .ifPresentOrElse(
                eventDto -> imageHandler.save(eventDto.getBody()),
                () -> log.error("Empty IMAGE payload in {} offset with key {}", cr.offset(), cr.key()));
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}
