package com.donald.nacosconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author donald
 * @date 2021/01/28
 */
@RestController
class HelloController {

    @Autowired
    private DiscoveryClient discoveryClient; // 服务发现

    @Autowired
    private RestTemplate restTemplate;

    private String serviceName = "my-provider";

    @GetMapping("/info")
    public String info() {
        // 使用 DiscoveryClient 获取 my-provider 服务对应的所有实例
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
        StringBuilder sb = new StringBuilder();
        sb.append("All services: " + discoveryClient.getServices() + "<br/>");
        sb.append("my-provider instance list: <br/>");
        serviceInstances.forEach(instance -> {
            sb.append("[ serviceId: " + instance.getServiceId() +
                    ", host: " + instance.getHost() +
                    ", port: " + instance.getPort() + " ]");
            sb.append("<br/>");
        });
        return sb.toString();
    }

    @GetMapping("/hello")
    public String hello() {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
        ServiceInstance serviceInstance = serviceInstances.stream()
                .findAny().orElseThrow(() ->
                        new IllegalStateException("no " + serviceName + " instance available"));
        return restTemplate.getForObject(
                "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() +
                        "/echo?name=nacos", String.class);
    }

    @GetMapping("/hello/1")
    public String hello1(HttpServletResponse response) {

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

        return "123";
    }

    @PostMapping("/hello/2")
    public String hello2(@RequestBody Object o, HttpServletResponse response) {

        System.out.println(o.toString());

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

        return "456";
    }

    @GetMapping("/test/1")
    public String test1() {

        return "123";
    }

    @PostMapping("/test/2")
    public String test2(@RequestBody Object o) {

        System.out.println(o.toString());

        return "456";
    }
}