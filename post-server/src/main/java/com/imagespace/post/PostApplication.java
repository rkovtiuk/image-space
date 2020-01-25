package com.imagespace.post;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import static org.springframework.boot.SpringApplication.run;

@EnableDiscoveryClient
@SpringCloudApplication
public class PostApplication {

    public static void main(String[] args) {
        run(PostApplication.class);
    }

}
