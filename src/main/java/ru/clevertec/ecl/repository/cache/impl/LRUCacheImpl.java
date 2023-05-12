package ru.clevertec.ecl.repository.cache.impl;

import ru.clevertec.ecl.repository.cache.MyCache;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCacheImpl<T> implements MyCache<T> {
    private final HashMap<Long, T> data = new HashMap<>();
    private final LinkedList<Long> order = new LinkedList<>();
    private final int capacity;

    public LRUCacheImpl(int capacity){
        this.capacity = capacity;
    }

    @Override
    public void put(Long id, T val){
        if (order.size() >= capacity) {
            long keyRemoved = order.removeLast();
            data.remove(keyRemoved);
        }
        order.addFirst(id);
        data.put(id, val);
    }

    @Override
    public T get(Long id){
        T result = data.get(id);
        if (result != null) {
            order.remove(id);
            order.addFirst(id);
        }
        return result;
    }
}
