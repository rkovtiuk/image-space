package com.imagespace.account.config.dao;

import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoConfig {

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new ImageSpacePhysycalNameStrategy();
    }

}
