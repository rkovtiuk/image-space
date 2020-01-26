package com.imagespace.api.domain.service;

import com.imagespace.api.config.producer.KafkaProducerConfiguration;
import com.imagespace.api.dto.EventDto;
import com.imagespace.api.dto.PostDto;
import com.imagespace.api.dto.SourceDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProducerService {

    KafkaProducerConfiguration kafkaConfig;
    KafkaTemplate<String, EventDto> template;

    public void sendCreateSourceEvent(SourceDto source, Runnable onSuccessFunc) {
        var event = new EventDto<>(kafkaConfig.getCreateSourceEventName(), source);
        template
            .send(kafkaConfig.getSourceTopicName(), source.getId().toString(), event)
            .addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("Can't send source {} for save. Details ", source.getId(), throwable);
                }

                @Override
                public void onSuccess(SendResult<String, EventDto> stringEventDtoSendResult) {
                    log.info("Successfully sent source {} for create", source.getId());
                    onSuccessFunc.run();
                }
            });
    }

    public void sendCreatePostEvent(PostDto post) {
        var event = new EventDto<>(kafkaConfig.getCreateSourceEventName(), post);
        template
            .send(kafkaConfig.getSourceTopicName(), post.getId().toString(), event)
            .addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("Can't send post {} for save. Details ", post.getId(), throwable);
                }

                @Override
                public void onSuccess(SendResult<String, EventDto> stringEventDtoSendResult) {
                    log.info("Successfully sent post {} for create", post.getId());
                }
            });
    }

}
