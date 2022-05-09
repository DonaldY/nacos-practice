package com.donald.txnotifymsgaccount;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan(value = { "com.donald.txnotifymsgaccount.mapper" })
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
public class TxNotifymsgAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxNotifymsgAccountApplication.class, args);
    }

}
