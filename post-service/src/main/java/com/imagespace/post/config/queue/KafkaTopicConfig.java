package com.imagespace.post.config.queue;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Data
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "kafka-properties.topics")
public class KafkaTopicConfig {

    String post;
    String source;
    String subscription;

    @Bean
    public AdminClient init(KafkaAdmin kafkaAdmin) {
        List<String> topics = List.of(post, source, subscription);
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfig());
        adminClient.listTopics().names().whenComplete((strings, throwable) -> adminClient.createTopics(
                topics.stream()
                        .filter(not(strings::contains))
                        .map(topic -> new NewTopic(topic, 1, (short) 1))
                        .collect(Collectors.toList())));

        return adminClient;
    }

}
