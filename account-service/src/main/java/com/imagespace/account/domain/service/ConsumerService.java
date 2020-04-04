package com.imagespace.account.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagespace.account.common.event.SubscriptionEvent;
import com.imagespace.account.config.kafka.KafkaEventConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.StringUtils.EMPTY;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerService {

    ObjectMapper objectMapper;
    KafkaEventConfig kafkaEventConfig;
    SubscriptionService subscriptionService;

    @KafkaListener(topics = "${kafka-properties.topics.post}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvents(ConsumerRecord<String, String> cr) throws JsonProcessingException {
        log.info("Kafka consumer received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(), typeIdHeader(cr.headers()), cr.value(), cr.toString());
        var payload = objectMapper.readValue(cr.value(), SubscriptionEvent.class);
        var eventName = ofNullable(payload).map(SubscriptionEvent::getEventName).orElse(EMPTY);

        if (kafkaEventConfig.getUpdateSubscriptionPriority().equals(eventName)) {
            processCreatingPost(payload, cr.offset(), cr.key());
            return;
        }

        log.warn("Msg in {} offset with key {} in source topic will be filtered out because of unexpected event name {}", cr.offset(), cr.key(), eventName);
    }

    private void processCreatingPost(SubscriptionEvent payload, long offset, String key) {
        log.info("Start creating post from kafka msg in {} offset with key {}", offset, key);
        ofNullable(payload).map(SubscriptionEvent::getPayload).ifPresentOrElse(
            subscription -> subscriptionService.updateSubscriptionPriorityByLike(subscription.getFollower(), subscription.getFollowing()),
            () -> log.error("Empty payload in {} offset with key {}", offset, key));
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}
