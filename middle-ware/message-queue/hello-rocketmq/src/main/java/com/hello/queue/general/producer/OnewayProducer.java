package com.hello.queue.general.producer;

import com.hello.queue.NamesrvAddr;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @ClassName OnewayProducer
 * @Description
 * @Author laoshiren
 * @Date 18:02 2022/9/15
 */
public class OnewayProducer implements NamesrvAddr {


    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr(NamesrvAddr.namesrvaddr );
        producer.start();
        for (int i = 0; i < 10; i++) {
            byte[] body = ("Hi," + i).getBytes();
            Message msg = new Message("SyncTopic", "someTag", body);
            // 单向发送
            producer.sendOneway(msg);
        }
        producer.shutdown();
        System.out.println("producer shutdown");
    }


}
