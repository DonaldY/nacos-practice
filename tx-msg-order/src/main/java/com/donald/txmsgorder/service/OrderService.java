package com.donald.txmsgorder.service;

import com.donald.txmsgorder.tx.TxMessage;

/**
 * @author donald
 * @date 2022/05/06
 */
public interface OrderService {
    /**
     * 提交订单同时保存事务信息
     */
    void submitOrderAndSaveTxNo(TxMessage txMessage);

    /**
     * 提交订单
     * @param productId 商品id
     * @param payCount 购买数量
     */
    void submitOrder(Long productId, Integer payCount);
}
