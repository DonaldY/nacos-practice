package com.donald.txmsgstock.service;

import com.donald.txmsgstock.model.Stock;
import com.donald.txmsgstock.tx.TxMessage;

/**
 * @author donald
 * @date 2022/05/06
 */
public interface StockService {

    Stock getStockById(Long id);

    /**
     * 扣减库存
     */
    void decreaseStock(TxMessage txMessage);
}
