package com.imagespace.discover;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableEurekaServer
public class DiscoverServerApplication {

    public static void main(String[] args) {
        run(DiscoverServerApplication.class);
    }

}
