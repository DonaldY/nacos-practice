package com.donald.nacosconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

}