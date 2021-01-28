package com.techbasevn.backend.cache.repositories;

import java.time.Duration;
import java.util.Optional;

public interface ICacheData {
    String cacheKey();

    Optional<Duration> ttl();
}
