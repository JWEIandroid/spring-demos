package com.example.thread.thread;

import com.example.thread.SpringDemoThreadApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDemoThreadApplication.class)
@Profile("dev")
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void write(){
        for (int i = 0; i < 5000000; i++) {
             redisTemplate.opsForValue().set(i,i);
        }
    }


    public void leaderboard(){

    }

    @Autowired
    public void zSetTest(){
    }


}
