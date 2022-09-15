package com.hello.queue.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @ClassName RocketClientConsumer
 * @Description
 * @Author laoshiren
 * @Date 13:23 2022/9/15
 */
public class RocketClientConsumer {

    static class LBConumser {

            public static void main(String[] args) throws Exception {
                // 实例化消息生产者,指定组名
                DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
                // 指定Namesrv地址信息.
                consumer.setNamesrvAddr("172.31.2.27:9876");
                // 订阅Topic
                consumer.subscribe("dehua", "*");
                //负载均衡模式消费
                consumer.setMessageModel(MessageModel.CLUSTERING);
                // 注册回调函数，处理消息
                consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                });
                //启动消息者
                consumer.start();
                System.out.printf("Consumer Started.%n");
            }


    }

}
