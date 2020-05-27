package com.example.rocketmq;


import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component//必须加 会在service被容器扫描到才能消费，不然会消费者没反应
@RocketMQMessageListener(topic = DemoMessage.TOPIC,consumerGroup="test-producer-group")
public class Consumer implements RocketMQListener<DemoMessage> {
    @Override
    public void onMessage(DemoMessage demoMessage) {
        System.out.println("这是一条消费消息："+ demoMessage);
    }
}
