package com.imagespace.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

import static org.springframework.boot.SpringApplication.run;

@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
@EnableKafka
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        run(ApiApplication.class);
    }

}
