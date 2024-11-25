package com.be.code.pong.controller;

import com.be.code.pong.domain.PongLog;
import com.be.code.pong.service.PongLogService;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@Slf4j
public class PongController {

    @Resource
    private RateLimiter rateLimiter;

    @Resource
    private PongLogService pongLogService;

    @PostMapping("/pong/{words}")
    public Mono<String> pong(@PathVariable("words") String words) {
        log.info("[pong][消息内容({})]", words);
        if (rateLimiter.tryAcquire()) {
            PongLog log = PongLog.builder().createTime(new Date()).receiveMsg(words).result("World").resultCode(200).build();
            pongLogService.save(log);
            return Mono.just("World");
        } else {
            PongLog pongLog = PongLog.builder().createTime(new Date()).receiveMsg(words).result("请求被限流啦").resultCode(429).build();
            pongLogService.save(pongLog);
            log.info("[pong][请求被限流啦]");
            return Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS));
        }
    }
}
