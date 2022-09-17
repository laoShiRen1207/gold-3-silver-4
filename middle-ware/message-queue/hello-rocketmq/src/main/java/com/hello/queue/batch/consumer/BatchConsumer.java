package com.hello.queue.batch.consumer;

import com.hello.queue.NamesrvAddr;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassName BatchConsumer
 * @Description
 * @Author laoshiren
 * @Date 15:21 2022/9/17
 */
@Slf4j
public class BatchConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cg");
        consumer.setNamesrvAddr(NamesrvAddr.namesrvaddr);

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("batchTopic", "*");

        // 指定每次可以消费10条消息，默认为1
        consumer.setConsumeMessageBatchMaxSize(10);

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            log.info(" batch started  {}", msgs.size());
            for (MessageExt msg : msgs) {
                log.info("{}",msg);
            }
            // 消费成功的返回结果
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            // 消费异常时的返回结果
            // return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
        consumer.start();

    }

}
