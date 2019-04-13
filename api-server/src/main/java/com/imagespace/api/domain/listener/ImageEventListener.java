package com.imagespace.api.domain.listener;

import com.imagespace.api.domain.dto.ImageEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
public class ImageEventListener implements ListenableFutureCallback<SendResult<String, ImageEventDto>> {

    @Override
    public void onSuccess(SendResult<String, ImageEventDto> result) {
        log.info("Image event message {} successfully sent",
                result.getProducerRecord().value().getId());
    }

    @Override public void onFailure(Throwable throwable) {
        log.error("Can't send IMG to Kafka Topic. Details ", throwable);
    }

}
