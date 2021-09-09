package com.donald.hystrixpractice.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * @author donald
 * @date 2021/09/03
 */
public class HelloWorldCommand extends HystrixCommand<String> {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String code;

    public HelloWorldCommand(String code) {
        super(HystrixCommandGroupKey.Factory.asKey("HelloWorldExample"));
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