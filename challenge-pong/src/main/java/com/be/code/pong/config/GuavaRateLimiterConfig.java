package com.be.code.pong.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**

 */
@Configuration
public class GuavaRateLimiterConfig {

    @Value(value = "${api.config.maxRequest}")
    private Double maxRequest;

    @Bean
    public RateLimiter rateLimiter() {
        RateLimiter rateLimiter = RateLimiter.create(maxRequest); // 每秒请求
        return rateLimiter;
    }
}
