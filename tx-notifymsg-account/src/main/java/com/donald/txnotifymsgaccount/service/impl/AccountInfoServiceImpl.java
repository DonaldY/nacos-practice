package com.donald.txnotifymsgaccount.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.donald.txnotifymsgaccount.mapper.AccountInfoMapper;
import com.donald.txnotifymsgaccount.mapper.PayInfoMapper;
import com.donald.txnotifymsgaccount.model.PayInfo;
import com.donald.txnotifymsgaccount.service.AccountInfoService;
import com.donald.txnotifymsgaccount.utils.HttpConnectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author donald
 * @date 2022/05/08
 */
@Slf4j
@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    @Autowired
    private AccountInfoMapper accountInfoMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;

    private String url = "http://localhost:8083/pay/query/payresult/";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountBalance(PayInfo payInfo) {
        if(payInfoMapper.isExistsPayInfo(payInfo.getTxNo()) != null){
            log.info("账户微服务已经处理过当前事务...");
            return;
        }
        //更新账户余额
        accountInfoMapper.updateAccoutBalanceByAccountNo(payInfo.getPayAmount(), payInfo.getAccountNo());
        //保存充值记录
        payInfoMapper.savePayInfo(payInfo);
    }

    @Override
    public PayInfo queryPayResult(String txNo) {
        String getUrl = url.concat(txNo);
        try{
            String payData = HttpConnectionUtils.getPayData(getUrl, null, null, HttpConnectionUtils.TYPE_STREAM);
            if(!StringUtils.isBlank(payData)){
                JSONObject jsonObject = JSONObject.parseObject(payData);
                PayInfo payInfo = jsonObject.toJavaObject(PayInfo.class);
                if(payInfo != null && "success".equals(payInfo.getPayResult())){
                    this.updateAccountBalance(payInfo);
                }
                return payInfo;
            }
        }catch (Exception e){
            log.error("查询充值结果异常:", e);
        }
        return null;
    }
}