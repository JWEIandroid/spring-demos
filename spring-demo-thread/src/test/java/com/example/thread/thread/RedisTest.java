package com.example.thread.thread;

import com.example.thread.SpringDemoThreadApplication;
import com.example.thread.entity.Product;
import com.example.thread.util.JsonMapperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDemoThreadApplication.class)
@Profile("dev")
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void write() {
        for (int i = 0; i < 5000000; i++) {
            redisTemplate.opsForValue().set(i, i);
        }
    }


    /**
     * 有序集合测试
     */
    @Test
    public void sortedSetTest() {


        Product product = new Product();
        product.setId(1);
        product.setClickCount(100);

        Double score = redisTemplate.opsForZSet().score("bandang",product);
        redisTemplate.opsForZSet().add("bandang", product, score+1);
        redisTemplate.opsForZSet().reverseRange("bandang",0,-1);


    }

}
