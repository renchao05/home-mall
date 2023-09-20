package com.renchao.mall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
//@Component
public class HelloScheduled {

    @Async
    @Scheduled(cron = "*/5 * * * * ?")
    public void hello() throws InterruptedException {
        log.info("hello!!...............");
        Thread.sleep(30000);
    }
}
