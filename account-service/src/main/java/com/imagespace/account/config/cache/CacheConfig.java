package com.imagespace.account.config.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "cache")
public class CacheConfig {

    private int duration;

    @Bean("coreCacheManager")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                    name,
                    CacheBuilder.newBuilder().expireAfterWrite(duration, TimeUnit.MINUTES).build().asMap(), false);
            }
        };
    }

}
