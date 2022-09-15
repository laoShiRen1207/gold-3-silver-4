package com.hello.queue.order;

import com.hello.queue.NamesrvAddr;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @ClassName OrderProducer
 * @Description
 * @Author laoshiren
 * @Date 20:30 2022/9/15
 */
@Slf4j
public class OrderProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr(NamesrvAddr.namesrvaddr);
        producer.start();

        MessageQueueSelector messageQueueSelector = (mqs, msg, arg) -> {
            Integer id = (Integer) arg;
//            System.out.printf("id %d ", id);
//            System.out.println();
            int index = id % mqs.size();
            return mqs.get(index);
        };
        for (int i = 0; i < 10; i++) {
            Integer orderId = i;
            byte[] body = ("Hi," + i).getBytes();
            Message msg = new Message("SyncTopic", "TagA", body);
            SendResult send = producer.send(msg, messageQueueSelector, orderId);
//            SendResult send = producer.send(msg);
            log.info("sendResult  {}", send);
        }
        producer.shutdown();
    }

}
