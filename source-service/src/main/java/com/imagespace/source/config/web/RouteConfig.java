package com.imagespace.source.config.web;

import com.imagespace.source.common.enums.SourceType;
import com.imagespace.source.domain.handler.SourceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouteConfig {

    @Bean
    public RouterFunction<ServerResponse> sourceRoutes(SourceHandler handler) {
        return RouterFunctions
            .route(GET("/sources/{id}").and(accept(IMAGE_JPEG)), request -> handler.one(request, SourceType.POST))
            .andRoute(GET("/sources/{id}/preview").and(accept(IMAGE_JPEG)), request -> handler.one(request, SourceType.PREVIEW))
            .andRoute(GET("/sources/{id}/small").and(accept(IMAGE_JPEG)), request -> handler.one(request, SourceType.SMALL));
    }

}
