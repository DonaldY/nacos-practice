package com.donald.txjtaatomikos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TxJtaAtomikosApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        this.orderService.createOrder();
    }
}
