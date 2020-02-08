package com.imagespace.post.config.dao;

import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class DaoConfig {

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new ImageSpacePhysycalNameStrategy();
    }

}
