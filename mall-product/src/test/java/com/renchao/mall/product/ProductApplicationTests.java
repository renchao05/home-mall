package com.renchao.mall.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test01() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("hello", "你好！- " + UUID.randomUUID());
        System.out.println("刚刚存储的数据：" + ops.get("hello"));
    }
}
