package com.imagespace.source;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static org.springframework.boot.SpringApplication.run;

@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableReactiveMongoRepositories
@SpringBootApplication
public class SourceServerApplication {

    public static void main(String[] args) {
        run(SourceServerApplication.class, args);
    }

}
