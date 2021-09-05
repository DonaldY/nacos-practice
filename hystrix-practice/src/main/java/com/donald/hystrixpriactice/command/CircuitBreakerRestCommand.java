package com.donald.hystrixpriactice.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.web.client.RestTemplate;

/**
 * @author donald
 * @date 2021/09/03
 */
public class CircuitBreakerRestCommand extends HystrixCommand<String> {

    private final RestTemplate restTemplate = new RestTemplate();

    private String code;

    public CircuitBreakerRestCommand(String code) {
        // （withMetricsRollingStatisticalWindowInMilliseconds 方法，默认10s）
        // 10s 内如果至少有 10 个请求，且其中有 10% 的异常比例，就会触发熔断器进入 open 状态。
        // 3000ms，表示从 open 状态进入 Half-Open 状态的时间
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CBRestExample"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withCircuitBreakerErrorThresholdPercentage(10)
                                .withCircuitBreakerRequestVolumeThreshold(10)
                                .withCircuitBreakerSleepWindowInMilliseconds(3000)
                        //.withMetricsRollingStatisticalWindowInMilliseconds(1000)
                )
        );
        this.code = code;
    }

    @Override
    protected String run() {
        String url = "http://httpbin.org/status/" + code;
        System.out.println("start to curl: " + url);
        restTemplate.getForObject(url, String.class);
        return "Request success";
    }

    @Override
    protected String getFallback() {
        return "Request failed";
    }
}
