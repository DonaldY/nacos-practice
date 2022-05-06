package com.donald.txmsgorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class TxMsgOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxMsgOrderApplication.class, args);
    }

}
