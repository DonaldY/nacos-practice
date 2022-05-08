package com.donald.txnotifymsgpay.service;


import com.donald.txnotifymsgpay.model.PayInfo;

/**
 * @author donald
 * @date 2022/05/08
 */
public interface PayInfoService {

    /**
     * 保存充值信息
     */
    PayInfo savePayInfo(PayInfo payInfo);

    /**
     * 查询指定的充值信息
     */
    PayInfo getPayInfoByTxNo(String txNo);
}
