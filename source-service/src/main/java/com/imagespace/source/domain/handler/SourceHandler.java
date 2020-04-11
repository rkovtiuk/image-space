package com.imagespace.source.domain.handler;

import com.imagespace.source.common.enums.SourceType;
import com.imagespace.source.common.factory.ServerResponseFactory;
import com.imagespace.source.domain.entity.SourceDocument;
import com.imagespace.source.domain.repository.SourceRepository;
import com.imagespace.source.domain.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SourceHandler {

    ImageService imageService;
    SourceRepository sourceRepository;

    @Transactional
    public Mono<SourceDocument> save(String sourceId, byte[] sourceData) {
        return this.sourceRepository
            .save(imageService.resizeSourceData(sourceId, sourceData))
            .doOnSuccess(doc -> log.info("Image {} has been saved.", sourceId));
    }

    @Transactional
    public Mono<SourceDocument> delete(String sourceId) {
        return this.sourceRepository
            .findFirstBySourceId(sourceId)
            .map(source -> {
                source.setDeleted(true);
                sourceRepository.save(source);
                return source;
            }).doOnSuccess(doc -> log.info("Source {} has been deleted.", sourceId));
    }

    public Mono<ServerResponse> one(ServerRequest request, SourceType sourceType) {
        return Optional.ofNullable(sourceType)
            .map(type -> getSourceData(request.pathVariable("id"), type))
            .orElseGet(ServerResponseFactory::getServerResponse);
    }

    private Mono<ServerResponse> getSourceData(String id, SourceType sourceType) {
        return switch (sourceType) {
            case PREVIEW -> this.sourceRepository
                    .findPreviewSourceById(id).flatMap(ServerResponseFactory::getServerResponse)
                    .switchIfEmpty(ServerResponseFactory.getServerResponse());
            case SMALL -> this.sourceRepository.findSmallSourceById(id)
                    .flatMap(ServerResponseFactory::getServerResponse)
                    .switchIfEmpty(ServerResponseFactory.getServerResponse());
            default -> this.sourceRepository.findPostSourceById(id)
                    .flatMap(ServerResponseFactory::getServerResponse)
                    .switchIfEmpty(ServerResponseFactory.getServerResponse());
        };
    }

}
