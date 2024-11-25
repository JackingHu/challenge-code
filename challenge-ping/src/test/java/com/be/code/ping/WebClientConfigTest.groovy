package com.be.code.ping

import com.be.code.ping.config.WebClientConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.reactive.function.client.DefaultWebClient
import spock.lang.Specification

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
class WebClientConfigTest extends Specification {

    def webClientConfig;

    def setup() {
        webClientConfig = new WebClientConfig("pongBaseUrl":"http://mockapi.com")
    }

    def "should create a WebClient with the correct base URL and default header"() {

        when:
        def webClient = webClientConfig.webClient();

        then:
        webClient.getMetaPropertyValues()[0].bean.builder.baseUrl == "http://mockapi.com"

    }
}
