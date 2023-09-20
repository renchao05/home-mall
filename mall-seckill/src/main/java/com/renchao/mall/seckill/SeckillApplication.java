package com.renchao.mall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 秒杀业务没有做，课程里面有很多不合理的地方，暂时略过，以后补充
 */
@EnableAsync    // 开启异步任务
@EnableScheduling   // 开启定时任务
@EnableFeignClients
@SpringBootApplication
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }
}
