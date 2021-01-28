package com.techbasevn.backend.configure.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "cache")
public class RedisConfigurationProperties {
    @Value("${spring.redis.timeout}")
    private long timeoutOfSeconds;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.topic}")
    private String topic;
    // Mapping of cacheNames to expire-after-write timeout in seconds
    private Map<String, Long> cacheExpiration = new HashMap<>();
}
