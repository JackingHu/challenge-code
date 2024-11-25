package com.be.code.ping

import com.be.code.pong.config.GuavaRateLimiterConfig
import spock.lang.Specification

class GuavaRateLimiterConfigTest extends Specification {

    def guavaRateLimiterConfig = new GuavaRateLimiterConfig()


    def "test rateLimiter bean creation"() {
        given:
        guavaRateLimiterConfig.maxRequest = 5.0

        when:
        def createdRateLimiter = guavaRateLimiterConfig.rateLimiter()

        then:
        createdRateLimiter != null
        createdRateLimiter.getRate() == 5.0
    }
}
