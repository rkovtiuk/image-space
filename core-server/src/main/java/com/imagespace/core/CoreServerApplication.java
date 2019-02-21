package com.imagespace.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import static org.springframework.boot.SpringApplication.run;

@EnableDiscoveryClient
@SpringBootApplication
public class CoreServerApplication {

    public static void main(String[] args) {
        run(CoreServerApplication.class);
    }

}
