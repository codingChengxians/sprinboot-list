package com.example.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RocketmqApplication {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private  Producer producer;

    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplication.class, args);

    }
    @RequestMapping(value = "/api/rocketmq")
    public void  send(){
        DemoMessage demoMessage = new DemoMessage();
        demoMessage.setId(1L);
        demoMessage.setMessage("hhhhh");
        rocketMQTemplate.syncSend(DemoMessage.TOPIC,demoMessage);
    }
    @RequestMapping(value = "/api/rocketmq2")
    public void  send2(){
        for (int i = 0;i<=10;i++){
            producer.syncSend(Long.valueOf(i));
        }
    }
}
