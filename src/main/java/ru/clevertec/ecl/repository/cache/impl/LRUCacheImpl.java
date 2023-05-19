package ru.clevertec.ecl.repository.cache.impl;

import ru.clevertec.ecl.repository.cache.MyCache;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * LRU cache implementation
 * @param <T> entity
 */
public class LRUCacheImpl<T> implements MyCache<T> {
    private final HashMap<Long, T> data = new HashMap<>();
    private final LinkedList<Long> order = new LinkedList<>();
    private final int capacity;

    public LRUCacheImpl(int capacity) {
        this.capacity = capacity;
    }


    /**
     * Put entity to cache
     * @param key
     * @param value entity
     */
    @Override
    public void put(Long key, T value) {
        if (order.size() >= capacity) {
            long keyRemoved = order.removeLast();
            data.remove(keyRemoved);
        }
        order.addFirst(key);
        data.put(key, value);
    }

    /**
     * Get entity from cache
     * @param key
     * @return entity
     */
    @Override
    public T get(Long key) {
        T result = data.get(key);
        if (result != null) {
            order.remove(key);
            order.addFirst(key);
        }
        return result;
    }

}
