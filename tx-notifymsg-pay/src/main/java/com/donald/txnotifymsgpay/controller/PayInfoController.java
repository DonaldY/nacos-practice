package com.donald.txnotifymsgpay.controller;

import com.donald.txnotifymsgpay.model.PayInfo;
import com.donald.txnotifymsgpay.service.PayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author donald
 * @date 2022/05/08
 */
@RestController
public class PayInfoController {

    @Autowired
    private PayInfoService payInfoService;

    //充值
    @GetMapping(value = "/pay_account")
    public PayInfo pay(PayInfo payInfo){
        //生成事务编号
        return payInfoService.savePayInfo(payInfo);
    }

    //查询充值结果
    @GetMapping(value = "/query/payresult/{txNo}")
    public PayInfo payResult(@PathVariable("txNo") String txNo){
        return payInfoService.getPayInfoByTxNo(txNo);
    }
}
