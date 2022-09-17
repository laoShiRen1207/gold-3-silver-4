package com.hello.springboot.rocket.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName SimpleProducer
 * @Description
 * @Author laoshiren
 * @Date 16:54 2022/9/17
 */
@Slf4j
@Component
public class SimpleProducer {

    String message = "遇见一次就花光所有运气的人，这辈子都不会开心。而你所拒绝的任何一个真心为你、用情过度的人，极有可能你已经改变了别人一生的轨迹。";

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSend(){
        return rocketMQTemplate.syncSend("simple", message);
    }


    public void asyncSend(){
        Message<String> asyncSendMessage = MessageBuilder.withPayload(message).build();

        rocketMQTemplate.asyncSend("simple", asyncSendMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 成功回调
                log.info("发送成功: {}", sendResult.getSendStatus().toString());
            }

            @Override
            public void onException(Throwable throwable) {
                // 失败回调
                log.error("发送失败: {}", throwable.getMessage());
            }
        });
    }

    public void onewaySend() {
        Message<String> sendMessage = MessageBuilder.withPayload(message).build();
        rocketMQTemplate.sendOneWay("simple", sendMessage);
    }

    public TransactionSendResult transactionSend(){
        Message<String> rocketMsg = MessageBuilder.withPayload(message).build();
        return rocketMQTemplate.sendMessageInTransaction("transaction", rocketMsg, null);
    }



}
