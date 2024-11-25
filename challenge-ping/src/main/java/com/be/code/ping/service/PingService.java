package com.be.code.ping.service;

import com.be.code.ping.util.FileLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.channels.FileLock;

@Service
@Slf4j
public class PingService {
    @Value(value = "${api.config.pong.uri}")
    private String pongUri;

    @Resource
    private WebClient webClient;

    @Resource
    private FileLockUtils fileLockUtils;

    private static final String RATE_LIMITED_MSG = "[ping][Request not send as being “rate limited”]";
    private static final String PONG_THROTTLED_MSG = "[ping][callback][Request send & Pong throttled it]";
    private static final String SERVICE_ERROR_MSG = "[ping][error]{code: -1, msg: '服务异常'}";

    public String ping() {
        //限流-多个jvm进程只能有2个能请求pong 服务
        FileLock fileLock = fileLockUtils.getFileLock();
        if (fileLock == null) {
            log.info(RATE_LIMITED_MSG);
            return RATE_LIMITED_MSG;
        }
        // 使用webflux的 webclient访问
        String word = "hello";
        String result = webClient.post()
                .uri(pongUri, word)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        log.info("[ping][callback]Request sent & Pong Respond");
                        return response.bodyToMono(String.class);
                    } else if (response.statusCode().value() == 429) {
                        log.info(PONG_THROTTLED_MSG);
                        return Mono.just(PONG_THROTTLED_MSG);
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                }).doOnError(e -> log.error("[ping][error][Request send error: {}]", e.getMessage(), e))
                .onErrorReturn(SERVICE_ERROR_MSG)
                .block(); // 阻塞 为了看到结果
        //log.info("result:{}", result);
        // 释放锁
        try {
            fileLock.release();
        } catch (IOException e) {
            log.error("[ping][fileLock][release error: {}]", e.getMessage(), e);
        }
        return result;
    }


}
