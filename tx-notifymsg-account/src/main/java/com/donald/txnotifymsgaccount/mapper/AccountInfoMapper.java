package com.donald.txnotifymsgaccount.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author donald
 * @date 2022/05/08
 */
public interface AccountInfoMapper {
    /**
     * 更新指定账户下的余额
     */
    int updateAccoutBalanceByAccountNo(@Param("payBalance") BigDecimal payBalance,
                                       @Param("accountNo") String accountNo);
}