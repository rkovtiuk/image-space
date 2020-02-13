package com.imagespace.post;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.boot.SpringApplication.run;

@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableJpaRepositories
@EnableTransactionManagement
@SpringCloudApplication
public class PostApplication {

    public static void main(String[] args) {
        run(PostApplication.class);
    }

}
