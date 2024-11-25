package com.be.code.ping

import com.be.code.ping.service.PingService
import com.be.code.ping.util.FileLockUtils
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.DefaultWebClient
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.nio.channels.FileLock

class PingServiceSpecTest extends Specification {

    def pongUri = "/pong/{word}";

    def webClient = Mock(DefaultWebClient);

    def pingService;

    def fileLockUtils = Mock(FileLockUtils)

    def setup() {
        pingService = new PingService("pongUri": pongUri, "webClient": webClient, "fileLockUtils": fileLockUtils)
    }

    def "ping - Request Allowed, Pong Responds OK"() {
        def lock = Mock(FileLock)
        webClient = WebClient.builder().baseUrl("123").exchangeFunction(new ExchangeFunction() {
            @Override
            public Mono<ClientResponse> exchange(ClientRequest request) {
                ClientResponse clientResponse = ClientResponse.create(HttpStatus.OK).body("World").build();
                return Mono.just(clientResponse);
            }
        }) .build();
        given:
        pingService.webClient = webClient
        pingService.fileLockUtils.getFileLock() >> lock


        when:
        def result = pingService.ping()

        then:
        result == "World"
    }

    def "ping - Rate Limited"() {
        given:
        pingService.fileLockUtils.getFileLock() >> null

        when:
        pingService.ping()

        then:
        0 * webClient.post()
    }

    def "ping  - Request Allowed, Pong Responds 429"() {
        def lock = Mock(FileLock)
        webClient = WebClient.builder().baseUrl("123").exchangeFunction(new ExchangeFunction() {
            @Override
            public Mono<ClientResponse> exchange(ClientRequest request) {
                ClientResponse clientResponse = ClientResponse.create(HttpStatus.TOO_MANY_REQUESTS).build();
                return Mono.just(clientResponse);
            }
        }) .build();
        given:
        pingService.webClient = webClient
        pingService.fileLockUtils.getFileLock() >> lock


        when:
        def result = pingService.ping()

        then:
        result == pingService.PONG_THROTTLED_MSG
    }

    def "ping  - Request Allowed, Pong throw error"() {
        def lock = Mock(FileLock)
        webClient = WebClient.builder().baseUrl("123").exchangeFunction(new ExchangeFunction() {
            @Override
            public Mono<ClientResponse> exchange(ClientRequest request) {
                ClientResponse clientResponse = ClientResponse.create(HttpStatus.BAD_GATEWAY).build();
                return Mono.just(clientResponse);
            }
        }) .build();
        given:
        pingService.webClient = webClient
        pingService.fileLockUtils.getFileLock() >> lock


        when:
        def result = pingService.ping()

        then:
        result == pingService.SERVICE_ERROR_MSG
    }

}
