package com.imagespace.core.config.dao;

import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class DaoConfig {

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new ImageSpacePhysycalNameStrategy();
    }

}
