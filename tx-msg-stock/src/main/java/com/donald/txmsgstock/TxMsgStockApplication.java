package com.donald.txmsgstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class TxMsgStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxMsgStockApplication.class, args);
    }

}
