package com.imagespace.source.config.kafka;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "kafka-properties")
public class KafkaEventProperties {

    String createSourceEventName;
    String deleteSourceEventName;

}
