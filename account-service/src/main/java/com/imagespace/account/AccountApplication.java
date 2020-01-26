package com.imagespace.account;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.boot.SpringApplication.run;

@EnableDiscoveryClient
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        run(AccountApplication.class);
    }

}
