package com.donald.txmsgorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(value = { "com.donald.txmsgorder.mapper" })
@EnableTransactionManagement(proxyTargetClass = true)
public class TxMsgOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxMsgOrderApplication.class, args);
    }

}
