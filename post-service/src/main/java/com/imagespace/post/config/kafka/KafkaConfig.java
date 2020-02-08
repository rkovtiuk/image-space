package com.imagespace.post.config.kafka;

import com.imagespace.post.common.event.BaseEvent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.function.Predicate.not;

@Data
@EnableKafka
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "kafka-properties")
public class KafkaConfig {

    String postTopicName;
    String sourceTopicName;

    String createSourceEventName;
    String deleteSourceEventName;

    String msgPerRequest;

    final KafkaProperties kafkaProperties;

    @Bean
    public Map<String, Object> producerConfigs() {
        var props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, BaseEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, BaseEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public AdminClient init(KafkaAdmin kafkaAdmin) {
        List<String> topics = newArrayList(postTopicName, sourceTopicName);
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfig());
        adminClient.listTopics().names().whenComplete((strings, throwable) -> adminClient.createTopics(
            topics.stream()
                .filter(not(strings::contains))
                .map(topic -> new NewTopic(topic, 1, (short) 1))
                .collect(Collectors.toList())));

        return adminClient;
    }

}
