package com.donald.txnotifymsgaccount.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author donald
 * @date 2022/05/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayInfo implements Serializable {
    private static final long serialVersionUID = -1971185546761595695L;
    /**
     * 充值记录主键
     */
    private String txNo;

    /**
     * 账户
     */
    private String accountNo;

    /**
     * 充值金额
     */
    private BigDecimal payAmount;

    /**
     * 充值时间
     */
    private LocalDateTime payTime;

    /**
     * 充值结果
     */
    private String payResult;
}
