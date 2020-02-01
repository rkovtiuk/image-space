package com.imagespace.source.domain.handler;

import com.imagespace.source.domain.entity.SourceDocument;
import com.imagespace.source.domain.repository.SourceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SourceHandler {

    SourceRepository sourceRepository;

    @Transactional
    public Mono<SourceDocument> save(String sourceId, byte[] sourceData) {
        return this.sourceRepository
            .save(new SourceDocument(sourceId, sourceData))
            .doOnSuccess(doc -> log.info("Image {} has been saved.", sourceId));
    }

    @Transactional
    public Mono<SourceDocument> delete(String sourceId) {
        return this.sourceRepository
            .findById(sourceId)
            .map(source -> {
                source.setDeleted(true);
                sourceRepository.save(source);
                return source;
            }).doOnSuccess(doc -> log.info("Source {} has been deleted.", sourceId));
    }

    public Mono<ServerResponse> one(ServerRequest request) {
        return this.sourceRepository.findById(request.pathVariable("id"))
            .flatMap(image -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(image)))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return request.queryParam("ids")
            .map(ids -> Arrays.asList(ids.split(",")))
            .map(this.sourceRepository::findAllBySourceIdIn)
            .map(images -> ServerResponse.ok().contentType(APPLICATION_JSON).body(images, SourceDocument.class))
            .orElse(ServerResponse.ok().contentType(APPLICATION_JSON).body(Flux.empty(), SourceDocument.class));
    }

}
