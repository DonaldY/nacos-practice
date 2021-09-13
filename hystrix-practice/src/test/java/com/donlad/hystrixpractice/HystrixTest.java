package com.donlad.hystrixpractice;

import com.donald.hystrixpractice.command.CircuitBreakerCommand;
import com.donald.hystrixpractice.command.CommandCollapserGetValueForKey;
import com.donald.hystrixpractice.command.LimitCommand;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author donald
 * @date 2021/09/09
 */
public class HystrixTest {

    @Test
    public void testCircuitBreaker() throws InterruptedException {

        for (int i = 0; i < 30; i++) {
            CircuitBreakerCommand circuitBreakerCommand = new CircuitBreakerCommand(i % 2);
            TimeUnit.MILLISECONDS.sleep(200);
            System.out.println(i + " - " + circuitBreakerCommand.execute());
        }
        System.out.println("流量 10 个，异常 50 % 达标：" + new Date());
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

    @Test
    public void test() throws InterruptedException {
        int count = 13;
        CountDownLatch downLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread(() -> {
                LimitCommand commandLimit = new LimitCommand();
                String execute = commandLimit.execute();
                System.out.println(Thread.currentThread().getName() + " "
                        + finalI + " : " + execute + "  :  " + new Date());
                downLatch.countDown();
            }).start();
        }
        downLatch.await();
    }

    @Test
    public void testCollapser() throws Exception {
        // 创建一个上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try {

            // 这里在一个线程中请求多次
            Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
            Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
            Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();
            Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();

            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
            System.out.println(f4.get());

            HystrixRequestLog currentRequest = HystrixRequestLog.getCurrentRequest();
            Collection<HystrixInvokableInfo<?>> allExecutedCommands = currentRequest.getAllExecutedCommands();
            System.out.println("当前线程中请求实际发起了几次：" + allExecutedCommands.size());
            HystrixCommand<?>[] hystrixCommands =
                    allExecutedCommands.toArray(new HystrixCommand<?>[allExecutedCommands.size()]);
            HystrixCommand<?> hystrixCommand = hystrixCommands[0];
            System.out.println("其中第一个 command 的名称：" + hystrixCommand.getCommandKey());
            System.out.println("command 执行事件" + hystrixCommand.getExecutionEvents());

        } finally {
            context.shutdown();
        }
    }
}
