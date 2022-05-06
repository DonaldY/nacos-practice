package com.donald.txmsgorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.donald.txmsgorder.mapper.OrderMapper;
import com.donald.txmsgorder.model.Order;
import com.donald.txmsgorder.service.OrderService;
import com.donald.txmsgorder.tx.TxMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author donald
 * @date 2022/05/06
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitOrderAndSaveTxNo(TxMessage txMessage) {
        Integer existsTx = orderMapper.isExistsTx(txMessage.getTxNo());
        if(existsTx != null){
            log.info("订单微服务已经执行过事务,商品id为:{}，事务编号为:{}",txMessage.getProductId(), txMessage.getTxNo());
            return;
        }
        //生成订单
        Order order = new Order();
        order.setId(System.currentTimeMillis());
        order.setCreateTime(LocalDateTime.now());
        order.setOrderNo(String.valueOf(System.currentTimeMillis()));
        order.setPayCount(txMessage.getPayCount());
        order.setProductId(txMessage.getProductId());
        orderMapper.saveOrder(order);

        //添加事务日志
        orderMapper.saveTxLog(txMessage.getTxNo());
    }

    @Override
    public void submitOrder(Long productId, Integer payCount) {
        //生成全局分布式序列号
        String txNo = UUID.randomUUID().toString();
        TxMessage txMessage = new TxMessage(productId, payCount, txNo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txMessage", txMessage);
        Message<String> message = MessageBuilder.withPayload(jsonObject.toJSONString()).build();
        //发送一条事务消息
        rocketMQTemplate.sendMessageInTransaction("tx_order_group", "topic_txmsg",
                message, null);
    }
}
