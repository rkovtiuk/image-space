package com.imagespace.api.domain.service;

import com.imagespace.api.domain.producer.KafkaProducer;
import com.imagespace.api.dto.ImageEventDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    KafkaProducer kafkaProducer;

    // TODO: 07.09.19 remove this test method
    @PostConstruct
    public void init() {
        ImageEventDto image = new ImageEventDto();
        image.setId(UUID.randomUUID().toString());
        image.setSource(UUID.randomUUID().toString().getBytes());
        kafkaProducer.sendImageEvent(image);
    }

}
