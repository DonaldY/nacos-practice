package com.donald.txnotifymsgaccount.service;

import com.donald.txnotifymsgaccount.model.PayInfo;

/**
 * @author donald
 * @date 2022/05/08
 */
public interface AccountInfoService {

    /**
     * 更新账户余额
     */
    void updateAccountBalance(PayInfo payInfo);

    /**
     * 查询充值结果
     */
    PayInfo queryPayResult(String txNo);
}