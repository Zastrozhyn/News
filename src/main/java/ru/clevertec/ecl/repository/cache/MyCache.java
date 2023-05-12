package ru.clevertec.ecl.repository.cache;

public interface MyCache<T> {

    void put(Long id, T val);
    T get(Long id);
}
