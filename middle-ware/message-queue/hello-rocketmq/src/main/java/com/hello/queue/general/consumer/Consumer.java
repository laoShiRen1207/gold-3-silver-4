package com.hello.queue.general.consumer;

import com.hello.queue.NamesrvAddr;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassName Consumer
 * @Description
 * @Author laoshiren
 * @Date 17:39 2022/9/15
 */
public class Consumer implements NamesrvAddr {

    public static void main(String[] args) throws Exception {
        // 定义一个pull消费者
        // DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("cg");
        // 定义一个push消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cg");
        // 指定nameServer
        consumer.setNamesrvAddr(NamesrvAddr.namesrvaddr);
        // 指定从最后一次消费消息开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 指定消费topic与tag
        consumer.subscribe("SyncTopic", "*");

        // 一旦broker中有了其订阅的消息就会触发该方法的执行，其返回值为当前consumer消费的状态
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // 逐条消费消息
            for (MessageExt msg : msgs) {
                System.out.println(msg);
                String string = new String(msg.getBody());
                System.out.println(string);
            }
            // 返回消费状态：消费成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 开启消费者消费
        consumer.start();
        System.out.println("Consumer Started");
    }

}
