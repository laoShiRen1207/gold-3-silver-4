package com.hello.queue.consumer;

import com.hello.queue.NamesrvAddr;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @ClassName RocketClientConsumer
 * @Description
 * @Author laoshiren
 * @Date 13:23 2022/9/15
 */
@Slf4j
public class RocketClientConsumer {

    static class LBConumser {

            public static void main(String[] args) throws Exception {
                // 实例化消息生产者,指定组名
                DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cg");
                // 指定Namesrv地址信息.
                consumer.setNamesrvAddr(NamesrvAddr.namesrvaddr);
                // 订阅Topic
                consumer.subscribe("SyncTopic", "*");
//                consumer.setMessageModel(MessageModel.BROADCASTING);
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
