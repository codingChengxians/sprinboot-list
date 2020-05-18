package com.example.oauth2;

import com.example.oauth2.entity.SysAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class Oauth2ApplicationTests {

    @Autowired
    private StringRedisTemplate template;
    @Test
    void contextLoads() {

    }

}
