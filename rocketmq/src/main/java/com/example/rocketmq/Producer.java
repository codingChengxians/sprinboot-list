package com.example.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate ;

    public SendResult syncSend(Long id) {
        DemoMessage demoMessage = new DemoMessage();
        demoMessage.setId(id);
        demoMessage.setMessage("message!");
        return rocketMQTemplate.syncSend(DemoMessage.TOPIC, demoMessage);
    }

    public void asyncSend(SendCallback sendCallback) {
        DemoMessage demoMessage = new DemoMessage();
        demoMessage.setId(1L);
        demoMessage.setMessage("message!");
        rocketMQTemplate.asyncSend(DemoMessage.TOPIC, demoMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    public void sendOneWay() {
        DemoMessage demoMessage = new DemoMessage();
        demoMessage.setId(1L);
        demoMessage.setMessage("message!");
        rocketMQTemplate.sendOneWay(DemoMessage.TOPIC, demoMessage);
    }

}

