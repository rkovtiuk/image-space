package com.imagespace.post.domain.service;

import com.imagespace.post.common.dto.EventDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerService {

    @KafkaListener(
        topics = "${kafka-properties.post-topic-name}",
        groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvents(ConsumerRecord<String, EventDto> cr, @Payload EventDto payload) {
        log.info("Kafka consumer received key {}: Type [{}] | Payload: {} | Record: {}",
                cr.key(), typeIdHeader(cr.headers()), payload, cr.toString());
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}
