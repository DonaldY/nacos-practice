package com.donald.txnotifymsgaccount.message;

import com.alibaba.fastjson.JSONObject;
import com.donald.txnotifymsgaccount.model.PayInfo;
import com.donald.txnotifymsgaccount.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author donald
 * @date 2022/05/09
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "consumer_group_account", topic = "topic_nofitymsg")
public class NotifyMsgAccountListener implements RocketMQListener<PayInfo> {
    @Autowired
    private AccountInfoService accountInfoService;
    @Override
    public void onMessage(PayInfo payInfo) {
        log.info("账户微服务收到RocketMQ的消息:{}", JSONObject.toJSONString(payInfo));
        //如果是充值成功，则修改账户余额
        if("success".equals(payInfo.getPayResult())){
            accountInfoService.updateAccountBalance(payInfo);
        }
        log.info("更新账户余额完毕:{}", JSONObject.toJSONString(payInfo));
    }
}