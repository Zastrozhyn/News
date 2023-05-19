package ru.clevertec.ecl.repository.cache;


/**
 * MyCache interface
 * @param <T>
 */
public interface MyCache<T> {

    void put(Long id, T val);

    T get(Long id);
}
