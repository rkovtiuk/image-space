package com.imagespace.source.domain.handler;

import com.imagespace.source.domain.entity.ImageDocument;
import com.imagespace.source.domain.repository.ImageRepository;
import com.imagespace.source.dto.ImageDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageHandler {

    ImageRepository imageRepository;
    TransactionalOperator transactionalOperator;

    @Transactional
    public Mono<ImageDocument> save(ImageDto dto) {
        return this.imageRepository
            .save(new ImageDocument(dto.getId(), dto.getSource()))
            .doOnSuccess(doc -> log.info("Image {} has been save.", doc.getId()));
    }

    public Mono<ServerResponse> one(ServerRequest request) {
        return this.imageRepository.findById(request.pathVariable("id"))
            .flatMap(image -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(image)))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return request.queryParam("ids")
                .map(ids -> Arrays.asList(ids.split(",")))
                .map(this.imageRepository::findAllByIdIn)
                .map(images -> ServerResponse.ok().contentType(APPLICATION_JSON).body(images, ImageDocument.class))
                .orElse(ServerResponse.ok().contentType(APPLICATION_JSON).body(Flux.empty(), ImageDocument.class));
    }

}
