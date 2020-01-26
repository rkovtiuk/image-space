package com.imagespace.source;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.boot.SpringApplication.run;

@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableReactiveMongoRepositories
@SpringBootApplication
public class SourceApplication {

    public static void main(String[] args) {
        run(SourceApplication.class, args);
    }

}
