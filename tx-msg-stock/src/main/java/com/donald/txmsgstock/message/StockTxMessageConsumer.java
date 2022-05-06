package com.donald.txmsgstock.message;

import com.alibaba.fastjson.JSONObject;
import com.donald.txmsgstock.service.StockService;
import com.donald.txmsgstock.tx.TxMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author donald
 * @date 2022/05/06
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "tx_stock_group", topic = "topic_txmsg")
public class StockTxMessageConsumer implements RocketMQListener<String> {
    @Autowired
    private StockService stockService;

    @Override
    public void onMessage(String message) {
        log.info("库存微服务开始消费事务消息:{}", message);
        TxMessage txMessage = this.getTxMessage(message);
        stockService.decreaseStock(txMessage);
    }

    private TxMessage getTxMessage(String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String txStr = jsonObject.getString("txMessage");
        return JSONObject.parseObject(txStr, TxMessage.class);
    }
}
