package com.donald.txmsgstock.model;

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
public class Stock implements Serializable {

    private static final long serialVersionUID = 2127099109599870497L;

    private Long id;

    /**
     * 商品id.
     */
    private Long productId;

    /**
     * 总库存
     */
    private Integer totalCount;
}