package com.donald.ribbonconsumer;

import javax.servlet.http.HttpServletRequest;

import com.donald.ribbonconsumer.gray.GrayInterceptor;
import com.donald.ribbonconsumer.gray.GrayRequestInterceptor;
import com.donald.ribbonconsumer.gray.GrayRule;
import com.netflix.loadbalancer.IRule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@EnableFeignClients
@SpringBootApplication
public class RibbonConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonConsumerApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new GrayInterceptor());
        return restTemplate;
    }

    @Bean
    public GrayRequestInterceptor requestInterceptor() {
        return new GrayRequestInterceptor();
    }

    @Bean
    public IRule myRule() {
        return new GrayRule();
    }

    @RestController
    class HelloController {

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private EchoService echoService;

        private String serviceName = "nacos-traffic-service";

        @GetMapping("/echo")
        public String echo(HttpServletRequest request) {
            HttpHeaders headers = new HttpHeaders();
            if (StringUtils.isNotEmpty(request.getHeader("Gray"))) {
                headers.add("Gray", request.getHeader("Gray").equals("true") ? "true" : "false");
            }
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange("http://" + serviceName + "/", HttpMethod.GET, entity, String.class).getBody();
        }

        @GetMapping("/echoFeign")
        public String echoFeign(HttpServletRequest request) {
            if (StringUtils.isNotEmpty(request.getHeader("Gray"))) {
                if (request.getHeader("Gray").equals("true")) {
                    return echoService.echo("true");
                }
            }
            return echoService.echo("false");
        }

    }

    @FeignClient(name = "nacos-traffic-service")
    interface EchoService {

        @GetMapping("/")
        String echo(@RequestHeader("Gray") String gray);

    }
}
