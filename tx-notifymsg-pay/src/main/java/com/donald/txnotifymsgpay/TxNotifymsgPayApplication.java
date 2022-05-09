package com.donald.txnotifymsgpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan(value = { "com.donald.txnotifymsgpay.mapper" })
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
public class TxNotifymsgPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxNotifymsgPayApplication.class, args);
    }

}
