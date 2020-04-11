package com.imagespace.post.config.queue;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Data
@EnableKafka
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "kafka-properties.events")
public class KafkaEventConfig {

    String createPost;
    String createSource;
    String deleteSource;
    String updateSubscriptionPriority;

}
