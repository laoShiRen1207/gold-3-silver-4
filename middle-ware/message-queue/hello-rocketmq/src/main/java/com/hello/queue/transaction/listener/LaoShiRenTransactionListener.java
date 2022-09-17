package com.hello.queue.transaction.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @ClassName LaoShiRenTransactionListener
 * @Description
 * @Author laoshiren
 * @Date 12:00 2022/9/17
 */
@Slf4j
public class LaoShiRenTransactionListener implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 这里其实是一顿业务操作 返回 COMMIT_MESSAGE 或者 ROLLBACK 或者 UNKNOW

        String tags = msg.getTags();
        log.info("{} 业务操作" ,tags);
        switch (tags) {
            case "TAGA": return LocalTransactionState.COMMIT_MESSAGE;
            case "TAGB": return LocalTransactionState.ROLLBACK_MESSAGE;
            case "TAGC":
            default: return LocalTransactionState.UNKNOW;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info("{} 消息回查", msg.getTags());
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
