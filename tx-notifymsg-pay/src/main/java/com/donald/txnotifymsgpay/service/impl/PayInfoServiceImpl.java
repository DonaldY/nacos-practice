package com.donald.txnotifymsgpay.service.impl;

import com.donald.txnotifymsgpay.mapper.PayInfoMapper;
import com.donald.txnotifymsgpay.model.PayInfo;
import com.donald.txnotifymsgpay.service.PayInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author donald
 * @date 2022/05/08
 */
@Slf4j
@Service
public class PayInfoServiceImpl implements PayInfoService {
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public PayInfo savePayInfo(PayInfo payInfo) {
        payInfo.setTxNo(UUID.randomUUID().toString());
        payInfo.setPayResult("success");
        payInfo.setPayTime(LocalDateTime.now());
        int count = payInfoMapper.savePayInfo(payInfo);
        //充值信息保存成功
        if(count > 0){
            log.info("充值微服务向账户微服务发送结果消息");
            //发送消息通知账户微服务
            rocketMQTemplate.convertAndSend("topic_nofitymsg", payInfo);
            return payInfo;
        }
        return null;
    }

    @Override
    public PayInfo getPayInfoByTxNo(String txNo) {
        return payInfoMapper.getPayInfoByTxNo(txNo);
    }
}
