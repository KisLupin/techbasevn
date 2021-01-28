package com.techbasevn.backend.cache.model;

import com.techbasevn.backend.cache.repositories.ICacheData;
import com.techbasevn.backend.common.Constant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Optional;

@Data
@NoArgsConstructor
public class UserCache implements ICacheData {
    private String token;
    private Integer id;
    private Duration ttl;

    @Override
    public String cacheKey() {
        return Constant.TOKEN_PREFIX + token;
    }

    @Override
    public Optional<Duration> ttl() {
        return Optional.ofNullable(this.ttl);
    }

    public UserCache(String token, Integer id) {
        this.token = token;
        this.id = id;
    }
}
