package com.imagespace.source;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import static org.springframework.boot.SpringApplication.run;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class SourceApplication {

    public static void main(String[] args) {
        run(SourceApplication.class, args);
    }

}
