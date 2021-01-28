package com.techbasevn.backend.cache.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CacheRepository<T extends ICacheData> implements DataCacheRepository<T> {
    private static final Logger logger = LoggerFactory.getLogger(CacheRepository.class);
    private final RedisTemplate<String, Object> redisTemplate; // and we're in business
    private final ObjectMapper objectMapper;

    @Override
    public Boolean add(T object) {
        try {
            String jsonObject = objectMapper.writeValueAsString(object);
            Optional<Duration> ttlOptional = object.ttl();
            if (ttlOptional.isPresent()) {
                redisTemplate.opsForValue().set(object.cacheKey(), jsonObject, ttlOptional.get());
            } else {
                redisTemplate.opsForValue().set(object.cacheKey(), jsonObject);
            }
            return true;
        } catch (Exception e) {
            logger.error("Unable to add object of key {} {}", object.cacheKey(), e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean delete(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            logger.error("Unable to delete entry {} {}", key, e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<T> find(String key, Class<T> tClass) {
        try {
            String jsonObj = String.valueOf(redisTemplate.opsForValue().get(key));
            return Optional.ofNullable(objectMapper.readValue(jsonObj, tClass));
        } catch (Exception e) {
            logger.info("Unable to find entry {} {}", key, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<String> allKeys(String prefix) {
        Set<byte[]> keys = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().keys("*".getBytes());
        if (keys != null) {
            return new ArrayList<>(keys).parallelStream().map(String::new)
                    .map(t -> t.substring(7)).filter(t -> t.startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Optional<T> getOrElseUpdate(String key, Class<T> tClass, Supplier<? extends T> supplier) {
        try {
            String jsonObj = String.valueOf(redisTemplate.opsForValue().get(key));

            T ret = objectMapper.readValue(jsonObj, tClass);
            if (ret == null) {
                ret = supplier.get();
                if (ret != null) {
                    add(ret);
                }
            }
            return Optional.ofNullable(ret);
        } catch (Exception e) {
            logger.info("Unable to find entry {} {}", key, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Boolean isAvailable() {
        try {
            String status =
                    Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().ping();
            if (status != null) {
                return true;
            }
        } catch (Exception e) {
            logger.warn("Redis server is not available at the moment.");
        }

        return false;
    }

    @Override
    public Boolean expireKey(T object) {
        return redisTemplate.expire(object.cacheKey(), object.ttl().orElse(Duration.ofSeconds(30)));
    }
}
