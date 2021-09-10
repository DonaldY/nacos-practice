package com.donlad.hystrixpractice;

import com.donald.hystrixpractice.command.CircuitBreakerCommand;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author donald
 * @date 2021/09/09
 */
public class HystrixTest {

    @Test
    public void testCircuitBreaker() throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            CircuitBreakerCommand circuitBreakerCommand = new CircuitBreakerCommand(i % 2);
            TimeUnit.MILLISECONDS.toMillis(500);
            System.out.println(i + " - " + circuitBreakerCommand.execute());
        }
        TimeUnit.SECONDS.sleep(3);
        System.out.println("3 秒之后，断路器变成半开状态，一个请求通过 ==============");
        CircuitBreakerCommand commandCircuit = new CircuitBreakerCommand(0);
        System.out.println(commandCircuit.execute());
        System.out.println("断路器关闭，尝试访问");
        for (int i = 0; i < 3; i++) {
            CircuitBreakerCommand c = new CircuitBreakerCommand(0);
            System.out.println(c.execute());
        }
    }
}
