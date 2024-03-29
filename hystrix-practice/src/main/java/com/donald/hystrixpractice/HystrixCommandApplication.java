package com.donald.hystrixpractice;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author donald
 * @date 2021/09/03
 */
@Profile("annotation")
@SpringBootApplication
@EnableCircuitBreaker
public class HystrixCommandApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixCommandApplication.class)
                .properties("spring.profiles.active=annotation").web(WebApplicationType.SERVLET)
                .run(args);
    }

    @RestController
    class HystrixController {

        @HystrixCommand(groupKey = "ControllerGroup", fallbackMethod = "fallback")
        @GetMapping("/exp")
        public String exp() {
            return new RestTemplate().getForObject("https://httpbin.org/status/500", String.class);
        }

        @HystrixCommand(commandKey = "hello", fallbackMethod = "fallback",
                commandProperties = {
                        @HystrixProperty(name = "fallback.enabled", value = "false")
                })
        @GetMapping("/hello1")
        public String hello1() {
            return new RestTemplate().getForObject("https://httpbin.org/status/500", String.class);
        }

        public String fallback() {
            return "Hystrix fallback";
        }
    }
}

