package com.be.code.ping.task;

import com.be.code.ping.service.PingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PingTask {

    @Resource
    private PingService pingService;

    @Scheduled(cron = "*/1 * * * * ?")
    public void ping() {
        pingService.ping();
    }
}
