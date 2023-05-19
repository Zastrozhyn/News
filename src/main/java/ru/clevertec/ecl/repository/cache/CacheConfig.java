package ru.clevertec.ecl.repository.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import ru.clevertec.ecl.repository.cache.impl.LFUCacheImpl;
import ru.clevertec.ecl.repository.cache.impl.LRUCacheImpl;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.repository.entity.User;

/**
 * Configuration class for MyCache implementation
 */
@Configuration
public class CacheConfig {
    @Value("${cache.type}")
    private String cacheType;
    @Value("${cache.capacity}")
    private int cacheCapacity;

    @Bean
    public MyCache<News> getNewsCache() {
        MyCache<News> cache;
        if (cacheType.equals("LFU")) {
            cache =  new LFUCacheImpl<>(cacheCapacity);
        } else {
            cache = new LRUCacheImpl<>(cacheCapacity);
        }
        return cache;
    }

    @Bean
    public MyCache<User> getUserCache() {
        MyCache<User> cache;
        if (cacheType.equals("LFU")) {
            cache =  new LFUCacheImpl<>(cacheCapacity);
        } else {
            cache = new LRUCacheImpl<>(cacheCapacity);
        }
        return cache;
    }

    @Bean
    public MyCache<Comment> getCommentCache() {
        MyCache<Comment> cache;
        if (cacheType.equals("LFU")) {
            cache =  new LFUCacheImpl<>(cacheCapacity);
        } else {
            cache = new LRUCacheImpl<>(cacheCapacity);
        }
        return cache;
    }

}
