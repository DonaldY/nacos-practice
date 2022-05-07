package com.donald.txmsgstock.service.impl;

import com.donald.txmsgstock.mapper.StockMapper;
import com.donald.txmsgstock.model.Stock;
import com.donald.txmsgstock.service.StockService;
import com.donald.txmsgstock.tx.TxMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author donald
 * @date 2022/05/07
 */
@Slf4j
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;

    @Override
    public Stock getStockById(Long id) {
        return stockMapper.getStockById(id);
    }

    @Override
    public void decreaseStock(TxMessage txMessage) {
        log.info("库存微服务执行本地事务,商品id:{}, 购买数量:{}", txMessage.getProductId(), txMessage.getPayCount());
        //检查是否执行过事务
        Integer exists = stockMapper.isExistsTx(txMessage.getTxNo());
        if(exists != null){
            log.info("库存微服务已经执行过事务,事务编号为:{}", txMessage.getTxNo());
        }
        Stock stock = stockMapper.getStockByProductId(txMessage.getProductId());
        if(stock.getTotalCount() < txMessage.getPayCount()){
            throw  new RuntimeException("库存不足");
        }
        stockMapper.updateTotalCountById(txMessage.getPayCount(), stock.getId());
        //记录事务日志
        stockMapper.saveTxLog(txMessage.getTxNo());
    }
}