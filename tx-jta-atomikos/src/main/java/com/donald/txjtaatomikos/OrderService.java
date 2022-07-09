package com.donald.txjtaatomikos;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author donald
 * @date 2022/07/10
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final JdbcTemplate orderJdbcTemplate;
    private final JdbcTemplate stockJdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void createOrder() {

        orderJdbcTemplate.update("INSERT INTO `order` (id, create_time, order_no, product_id,  pay_count) " +
                " VALUES (2, NOW(), 2, 1, 1)");

        stockJdbcTemplate.update("UPDATE stock SET total_count = total_count - 1 where id = ?", 1);
    }
}
