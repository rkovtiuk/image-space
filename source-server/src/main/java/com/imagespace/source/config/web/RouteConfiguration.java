package com.imagespace.source.config.web;

import com.imagespace.source.domain.handler.ImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouterFunction<ServerResponse> imageRoutes(ImageHandler handler) {
        return RouterFunctions
            .route(GET("/images/{id}").and(accept(APPLICATION_JSON)), handler::one)
            .andRoute(GET("/images").and(accept(APPLICATION_JSON)), handler::all);
    }

}
