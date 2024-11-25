package com.be.code.ping


import com.be.code.pong.controller.PongController
import com.be.code.pong.service.PongLogService
import com.google.common.util.concurrent.RateLimiter
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

/**
 * 测试pong服务
 */
class PongControllerTest extends Specification {

    def rateLimiter = Mock(RateLimiter)
    def pongLogService = Mock(PongLogService)
    def controller = new PongController()

    def setup() {
        controller.rateLimiter = rateLimiter
        controller.pongLogService = pongLogService
    }

    def "return 'World' when request is allowed"() {
        given:
        rateLimiter.tryAcquire() >> true

        when:
        def result = controller.pong("test").block()

        then:
        result == "World"
    }

    def "return 429 status when request is not allowed"() {
        given:
        rateLimiter.tryAcquire() >> false

        when:
        controller.pong("test").block()

        then:
        def exception = thrown(ResponseStatusException)
        exception.status == HttpStatus.TOO_MANY_REQUESTS
    }
}