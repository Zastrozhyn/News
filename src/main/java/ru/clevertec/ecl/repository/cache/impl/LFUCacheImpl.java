package ru.clevertec.ecl.repository.cache.impl;

import ru.clevertec.ecl.repository.cache.MyCache;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * LFU cache implementation
 * @param <T> entity
 */
public class LFUCacheImpl<T> implements MyCache<T> {
    private static final Long ONE = 1L;
    private final HashMap<Long, T> values = new HashMap<>();
    private final HashMap<Long, Long> counts = new HashMap<>();
    private final HashMap<Long, LinkedHashSet<Long>> lists = new HashMap<>();
    private final int capacity;
    private long minFrequent = -1;

    public LFUCacheImpl(int capacity) {
        this.capacity = capacity;
        lists.put(ONE, new LinkedHashSet<>());
    }

    /**
     * Get entity from cache
     * @param key
     * @return entity
     */
    @Override
    public T get(Long key) {
        if (!values.containsKey(key))
            return null;
        long count = counts.get(key);
        counts.put(key, count + 1);
        lists.get(count).remove(key);

        if (count == minFrequent && lists.get(count).size() == 0)
            minFrequent++;
        if (!lists.containsKey(count + 1))
            lists.put(count + 1, new LinkedHashSet<>());
        lists.get(count + 1).add(key);
        return values.get(key);
    }

    /**
     * Put entity to cache
     * @param key
     * @param value entity
     */
    @Override
    public void put(Long key, T value) {
        if (values.containsKey(key)) {
            values.put(key, value);
            get(key);
            return;
        }
        if (values.size() >= capacity) {
            long idToRemove = lists.get(minFrequent).iterator().next();
            System.out.println(lists.get(minFrequent));
            lists.get(minFrequent).remove(idToRemove);
            values.remove(idToRemove);
            counts.remove(idToRemove);
        }
        values.put(key, value);
        counts.put(key, 1L);
        minFrequent = 1L;
        lists.get(ONE).add(key);
    }
}
