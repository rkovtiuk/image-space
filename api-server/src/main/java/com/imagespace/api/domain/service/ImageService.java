package com.imagespace.api.domain.service;

import com.imagespace.api.config.producer.ProducerConfiguration;
import com.imagespace.api.domain.dto.ImageEventDto;
import com.imagespace.api.domain.listener.ImageEventListener;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Data
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    ProducerConfiguration producerConfiguration;

    KafkaTemplate<String, ImageEventDto> template;

    public void sendImageEventToQueue(ImageEventDto imgData) {
        template
            .send(
                producerConfiguration.getTopicName(),
                producerConfiguration.getMsgPerRequest(),
                imgData)
            .addCallback(new ImageEventListener());
    }

    @PostConstruct
    public void init() {
        sendImageEventToQueue(new ImageEventDto().setId("123").setSource("Arrau".getBytes()));
        sendImageEventToQueue(new ImageEventDto().setId("435").setSource("Arrau".getBytes()));
    }

}
