package com.donald.hystrixpractice.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.web.client.RestTemplate;

/**
 * @author donald
 * @date 2021/09/03
 */
public class CircuitBreakerCommand extends HystrixCommand<String> {

    private int code;

    public CircuitBreakerCommand(int code) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CBExample"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                // 当异常占比超过 50%
                                .withCircuitBreakerErrorThresholdPercentage(50)
                                // 10 秒时间窗口流量达到 10个
                                .withCircuitBreakerRequestVolumeThreshold(10)
                                // 断路器打开之后，后续请求都会被拒绝并走降级机制，打开 3 秒后，变成半开状态
                                .withCircuitBreakerSleepWindowInMilliseconds(3000)
                        //.withMetricsRollingStatisticalWindowInMilliseconds(1000)
                )
        );
        this.code = code;
    }

    @Override
    protected String run() {
        if (code == 1) {
            throw new RuntimeException("failure from CommandThatFailsFast");
        }
        return "Request success";
    }

    @Override
    protected String getFallback() {
        return "Request failed by fallback";
    }
}
