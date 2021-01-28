package com.techbasevn.backend.cache.repositories;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface DataCacheRepository<T> {
    Boolean add(T object);

    Boolean delete(String key);

    Optional<T> find(String key, Class<T> tClass);

    List<String> allKeys(String prefix);

    Optional<T> getOrElseUpdate(String key, Class<T> tClass, Supplier<? extends T> supplier);

    Boolean isAvailable();

    Boolean expireKey(T object);
}
