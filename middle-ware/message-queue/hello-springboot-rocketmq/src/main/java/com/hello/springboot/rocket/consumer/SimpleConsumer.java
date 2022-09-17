package com.hello.springboot.rocket.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName SimpleConsumer
 * @Description
 * @Author laoshiren
 * @Date 16:41 2022/9/17
 */
@Slf4j
@Component
@RocketMQMessageListener(
        //主题
        topic = "simple",
        //消费组
        consumerGroup = "simple-consumer-group",
        messageModel = MessageModel.CLUSTERING
)
public class SimpleConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("{}", message);
    }

}
