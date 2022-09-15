package com.hello.queue.general.producer;

import com.hello.queue.NamesrvAddr;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.UUID;

/**
 * @ClassName SyncProducer
 * @Description
 * @Author laoshiren
 * @Date 13:38 2022/9/15
 */
@Slf4j
public class SyncProducer implements NamesrvAddr {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 创建一个producer，参数为Producer Group名称
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        // 指定nameServer地址
        producer.setNamesrvAddr(NamesrvAddr.namesrvaddr);
        // 设置当发送失败时重试发送的次数，默认为2次
        producer.setRetryTimesWhenSendFailed(3);
        // 设置发送超时时限为5s，默认3s
        producer.setSendMsgTimeout(5000);
        // 开启生产者
        producer.start();
        byte[] body = ("Hi, Sync Message ！").getBytes();
        Message msg = new Message("SyncTopic", "syncMsg", body);
        // 为消息指定key
        msg.setKeys("key-"+ UUID.randomUUID());
        log.info( "message  {}", msg);
        // 发送消息
        SendResult sendResult = producer.send(msg);
        log.info( "sendResult  {}", sendResult);
        // 关闭producer
        producer.shutdown();
    }
}
