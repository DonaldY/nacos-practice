package com.donald.hystrixpractice.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.web.client.RestTemplate;

/**
 * @author donald
 * @date 2021/09/03
 */
public class TimeoutRestCommand extends HystrixCommand<String> {

    private final RestTemplate restTemplate = new RestTemplate();

    private final int seconds;

    public TimeoutRestCommand(int seconds) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TimeoutRestExample"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(1500)
                        //.withExecutionTimeoutInMilliseconds(100)
                )
        );
        this.seconds = seconds;
    }

    @Override
    protected String run() {
        String url = "http://httpbin.org/delay/" + seconds;
        System.out.println("start to curl: " + url);
        restTemplate.getForObject(url, String.class);
        return "Request success";
    }

    @Override
    protected String getFallback() {
        return "Request failed";
    }
}