package com.donald.hystrixpriactice;

import com.donald.hystrixpriactice.command.CircuitBreakerRestCommand;
import com.donald.hystrixpriactice.command.HelloWorldCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * @author donald
 * @date 2021/09/03
 */
@EnableHystrix
@SpringBootApplication
public class HystrixCircuitBreakerDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixCircuitBreakerDemo.class)
               .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean("CircuitBreaker")
    CommandLineRunner commandLineRunner() {
        return args -> {
            int num = 1;
            while (num <= 15) {
                CircuitBreakerRestCommand command = new CircuitBreakerRestCommand("500");
                System.err.println("Execute " + num + ": " + command.execute() + " and Circuit Breaker is " + (
                        command.isCircuitBreakerOpen() ? "open" : "closed"));
                num++;
            }
            Thread.sleep(3000L);
            CircuitBreakerRestCommand command = new CircuitBreakerRestCommand("200");
            System.err.println("Execute " + num + ": " + command.execute() + " and Circuit Breaker is " + (
                    command.isCircuitBreakerOpen() ? "open" : "closed"));
        };
    }
}
