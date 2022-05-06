package com.donald.txmsgorder.tx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author donald
 * @date 2022/05/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxMessage implements Serializable {

    private static final long serialVersionUID = -4704980150056885074L;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品购买数量
     */
    private Integer payCount;

    /**
     * 全局事务编号
     */
    private String txNo;
}