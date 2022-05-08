package com.donald.txnotifymsgaccount.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author donald
 * @date 2022/05/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo implements Serializable {
    private static final long serialVersionUID = 3159662335364762944L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 账户
     */
    private String accountNo;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 账户余额
     */
    private BigDecimal accountBalance;

}
