package com.hello.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName AmqpConsumer
 * @Description
 * @Author laoshiren
 * @Date 16:44 2022/10/21
 */
@Slf4j
@Component
public class AmqpConsumer {

    @RabbitListener(queues = "yyds")
    public void yyds(Message message) {
        log.info("{}",message);
    }

}
