package com.donald.txmsgstock.mapper;

import com.donald.txmsgstock.model.Stock;
import org.apache.ibatis.annotations.Param;

/**
 * @author donald
 * @date 2022/05/06
 */
public interface StockMapper {

    Stock getStockById(@Param("id") Long id);

    /**
     * 根据商品id获取库存信息
     */
    Stock getStockByProductId(@Param("productId") Long productId);

    /**
     * 修改商品库存
     */
    int updateTotalCountById(@Param("count") Integer count, @Param("id") Long id);


    /**
     * 检查是否存在指定事务编号的事务记录，如果存在则说明已经执行过
     * 用于幂等操作
     */
    Integer isExistsTx(@Param("txNo") String txNo);

    /**
     * 保存事务记录
     */
    void saveTxLog(@Param("txNo") String txNo);
}
