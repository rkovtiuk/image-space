package com.imagespace.api.domain.service;

import com.imagespace.api.common.dto.SourceDto;
import com.imagespace.api.common.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SourceService {

    ProducerService kafkaProducer;

    public void savePostSource(UUID sourceId, byte[] sourceData) {
        var source = new SourceDto(sourceId, sourceData);
        log.info("Start sending source to kafka for save");
        kafkaProducer.sendCreateSourceEvent(source)
            .orElseThrow(() -> new BadRequestException("Can't save a source."));
    }

}
