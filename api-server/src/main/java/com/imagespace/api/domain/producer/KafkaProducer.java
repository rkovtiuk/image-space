package com.imagespace.api.domain.producer;

import com.imagespace.api.config.producer.KafkaProducerConfiguration;
import com.imagespace.api.dto.ImageEventDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaProducer {

    KafkaProducerConfiguration kafkaProducerConfiguration;
    KafkaTemplate<String, ImageEventDto> template;

    public void sendImageEvent(ImageEventDto imgData) {
        template
            .send(kafkaProducerConfiguration.getImgTopicName(), imgData.getId(), imgData)
            .addCallback(new KafkaImageEventListener());
    }

}
