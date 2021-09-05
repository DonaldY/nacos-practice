package com.donald.hystrixpriactice;

import com.donald.hystrixpriactice.command.HelloWorldCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * @author donald
 * @date 2021/09/03
 */
@Profile("raw")
@SpringBootApplication
public class HystrixHelloWorldDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixHelloWorldDemo.class)
                .properties("spring.profiles.active=raw").web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean("HelloWorld")
    CommandLineRunner commandLineRunner() {
        return args -> {
            HelloWorldCommand helloWorld1 = new HelloWorldCommand("200");
            HelloWorldCommand helloWorld2 = new HelloWorldCommand("500");
            System.err.println(
                    helloWorld1.execute() + " and Circuit Breaker is " + (helloWorld1.isCircuitBreakerOpen() ? "open"
                            : "closed"));
            System.err.println(
                    helloWorld2.execute() + " and Circuit Breaker is " + (helloWorld2.isCircuitBreakerOpen() ? "open"
                            : "closed"));
        };
    }

}
