package com.imagespace.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.boot.SpringApplication.run;

@EnableDiscoveryClient
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class CoreServerApplication {

    public static void main(String[] args) {
        run(CoreServerApplication.class);
    }

}
