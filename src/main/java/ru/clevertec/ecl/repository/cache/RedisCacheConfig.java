package ru.clevertec.ecl.repository.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

/**
 * Configuration of redis cache
 */
@Profile("prod")
@Configuration
@EnableCaching
public class RedisCacheConfig {
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig();
    }
}
