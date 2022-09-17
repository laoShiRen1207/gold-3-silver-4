package com.hello.queue.transaction;

import com.hello.queue.NamesrvAddr;
import com.hello.queue.transaction.listener.LaoShiRenTransactionListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.*;

/**
 * @ClassName TransactionProducer
 * @Description
 * @Author laoshiren
 * @Date 12:15 2022/9/17
 */
@Slf4j
public class TransactionProducer {

    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("tpg");
        producer.setNamesrvAddr(NamesrvAddr.namesrvaddr);

        ExecutorService executorService = new ThreadPoolExecutor(2, 5,
                100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000), r -> {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        });

        producer.setExecutorService(executorService);
        // 为生产者添加事务监听器
        producer.setTransactionListener(new LaoShiRenTransactionListener());
        producer.start();


        String[] tags = {"TAGA","TAGB","TAGC"};
        byte[] body = ("Hi," + tags[1]).getBytes();
        Message msg = new Message("TTopic", tags[2], body);
        SendResult sendResult = producer.sendMessageInTransaction(msg,null);
        System.out.println("发送结果为：" + sendResult.getSendStatus());

    }

}
