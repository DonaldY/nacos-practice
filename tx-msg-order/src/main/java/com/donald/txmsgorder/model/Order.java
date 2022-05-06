package com.donald.txmsgorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author donald
 * @date 2022/05/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = 2874316208843394191L;

    private Long id;

    /**
     * 创建时间.
     */
    private LocalDateTime createTime;

    /**
     * 订单编号.
     */
    private String orderNo;

    /**
     * 商品id.
     */
    private Long productId;

    /**
     * 购买数量.
     */
    private Integer payCount;

}
