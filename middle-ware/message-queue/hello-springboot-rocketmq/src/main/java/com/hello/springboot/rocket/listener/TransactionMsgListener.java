package com.hello.springboot.rocket.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @ClassName TransactionMsgListener
 * @Description
 * @Author laoshiren
 * @Date 17:17 2022/9/17
 */
@Slf4j
@RocketMQTransactionListener(corePoolSize = 6, maximumPoolSize = 2*6)
public class TransactionMsgListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 本地事务提交
        int i = RandomUtils.nextInt(0, 3);
        log.info("state {} ",i);
        switch (i) {
            case 1: return RocketMQLocalTransactionState.ROLLBACK;
            case 2: return RocketMQLocalTransactionState.UNKNOWN;
            case 0:
            default:
                return RocketMQLocalTransactionState.COMMIT;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // 数据库回查事务提交
        log.info("state 回查");
        return RocketMQLocalTransactionState.COMMIT;
    }
}
