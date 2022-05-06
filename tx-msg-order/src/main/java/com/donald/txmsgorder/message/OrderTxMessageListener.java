package com.donald.txmsgorder.message;

import com.alibaba.fastjson.JSONObject;
import com.donald.txmsgorder.mapper.OrderMapper;
import com.donald.txmsgorder.service.OrderService;
import com.donald.txmsgorder.tx.TxMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author donald
 * @date 2022/05/06
 */
@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "tx_order_group")
public class OrderTxMessageListener implements RocketMQLocalTransactionListener {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object obj) {
        try{
            log.info("订单微服务执行本地事务");
            TxMessage txMessage = this.getTxMessage(msg);
            //执行本地事务
            orderService.submitOrderAndSaveTxNo(txMessage);
            //提交事务
            log.info("订单微服务提交事务");
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            e.printStackTrace();
            //异常回滚事务
            log.info("订单微服务回滚事务");
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("订单微服务查询本地事务");
        TxMessage txMessage = this.getTxMessage(msg);
        Integer exists = orderMapper.isExistsTx(txMessage.getTxNo());
        if(exists != null){
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    private TxMessage getTxMessage(Message msg){
        String messageString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(messageString);
        String txStr = jsonObject.getString("txMessage");
        return JSONObject.parseObject(txStr, TxMessage.class);
    }
}
