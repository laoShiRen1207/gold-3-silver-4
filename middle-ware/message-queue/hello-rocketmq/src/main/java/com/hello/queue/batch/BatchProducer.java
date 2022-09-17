package com.hello.queue.batch;

import com.hello.queue.NamesrvAddr;
import com.hello.queue.batch.message.MessageListSplitter;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BatchProducer
 * @Description
 * @Author laoshiren
 * @Date 13:32 2022/9/17
 */
public class BatchProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr(NamesrvAddr.namesrvaddr);
        producer.start();

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            byte[] body = ("Hi," + i).getBytes();
            Message msg = new Message("batchTopic", "someTag", body);
            messages.add(msg);
        }
        MessageListSplitter splitter = new MessageListSplitter(messages);

        while (splitter.hasNext()) {
            List<Message> next = splitter.next();
            producer.send(next);
        }
        producer.shutdown();
    }

}
