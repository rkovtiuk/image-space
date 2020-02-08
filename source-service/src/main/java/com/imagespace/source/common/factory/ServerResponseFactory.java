package com.imagespace.source.common.factory;

import com.imagespace.source.common.projection.PostSource;
import com.imagespace.source.common.projection.PreviewSource;
import com.imagespace.source.common.projection.SmallSource;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@UtilityClass
public class ServerResponseFactory {

    public Mono<ServerResponse> getServerResponse() {
        return ServerResponse.notFound().build();
    }

    public Mono<ServerResponse> getServerResponse(PostSource source) {
        return ServerResponse.ok().contentType(MediaType.IMAGE_JPEG).body(fromValue(source.getPostData()));
    }

    public Mono<ServerResponse> getServerResponse(PreviewSource source) {
        return ServerResponse.ok().contentType(MediaType.IMAGE_JPEG).body(fromValue(source.getPreviewData()));
    }

    public Mono<ServerResponse> getServerResponse(SmallSource source) {
        return ServerResponse.ok().contentType(MediaType.IMAGE_JPEG).body(fromValue(source.getSmallData()));
    }

}
