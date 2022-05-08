package com.donald.txnotifymsgaccount.controller;

import com.donald.txnotifymsgaccount.model.PayInfo;
import com.donald.txnotifymsgaccount.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author donald
 * @date 2022/05/08
 */
@RestController
public class AccountInfoController {

    @Autowired
    private AccountInfoService accountInfoService;

    //主动查询充值结果
    @GetMapping(value = "/query/payresult/{txNo}")
    public PayInfo result(@PathVariable("txNo") String txNo){
        return accountInfoService.queryPayResult(txNo);
    }
}
