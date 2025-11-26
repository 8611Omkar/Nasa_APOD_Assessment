package com.example.Nasa_APOD_Assessment.Configuration;



import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${cache.expire-seconds:3600}")
    private int expireSeconds;

    @Value("${cache.max-size:500}")
    private int maxSize;

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .maximumSize(maxSize);
    }

    @Bean
    public CaffeineCacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager manager = new CaffeineCacheManager("apodByDate", "apodRange");
        manager.setCaffeine(caffeine);
        return manager;
    }
}

