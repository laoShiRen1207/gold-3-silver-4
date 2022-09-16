package com.hello.queue.delay;

import com.hello.queue.NamesrvAddr;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @ClassName DelayProducer
 * @Description
 * @Author laoshiren
 * @Date 23:30 2022/9/15
 */
@Slf4j
public class DelayProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("delay-pg");
        producer.setNamesrvAddr(NamesrvAddr.namesrvaddr);
        producer.start();
        for (int i = 0; i < 10; i++) {
            byte[] body = ("Hi," + i).getBytes();
            Message msg = new Message("TopicB", "someTag", body);
            // 指定消息延迟等级为3级，即延迟10s
             msg.setDelayTimeLevel(3);
            SendResult sendResult = producer.send(msg);
            // 输出消息被发送的时间
            log.info("sr {}", sendResult);
        }
        producer.shutdown();
    }

}
