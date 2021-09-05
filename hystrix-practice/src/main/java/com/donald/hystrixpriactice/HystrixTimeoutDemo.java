package com.donald.hystrixpriactice;

import com.donald.hystrixpriactice.command.TimeoutRestCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author donald
 * @date 2021/09/03
 */
@SpringBootApplication
public class HystrixTimeoutDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixTimeoutDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            int num = 1;
            while (num <= 10) {
                TimeoutRestCommand command = new TimeoutRestCommand(num);
                System.err.println("Execute " + num + ": " + command.execute() + " and Circuit Breaker is " + (
                        command.isCircuitBreakerOpen() ? "open" : "closed"));
                num++;
            }
        };
    }
}
