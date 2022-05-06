package com.donald.txmsgorder.mapper;

import com.donald.txmsgorder.model.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @author donald
 * @date 2022/05/06
 */
public interface OrderMapper {

    /**
     * 保存订单
     */
    void saveOrder(@Param("order") Order order);

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
