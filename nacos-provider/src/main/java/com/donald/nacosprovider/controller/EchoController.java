package com.donald.nacosprovider.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author donald
 * @date 2021/01/24
 */
@RestController
class EchoController {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/echo")
    public String echo(HttpServletRequest request) {
        String serviceName = "192.168.199.174:8081";

        HttpHeaders headers = new HttpHeaders();

        if (StringUtils.isNotEmpty(request.getHeader("Gray"))) {

            headers.add("Gray", request.getHeader("Gray").equals("true") ? "true" : "false");
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("http://" + serviceName + "/", HttpMethod.GET, entity, String.class).getBody();
    }

    @GetMapping("/echo2")
    public String echo2(HttpServletRequest request) {
        return "echo: " + request.getParameter("name");
    }

    @GetMapping("/config")
    public String config() {

        return "env.get('book.category')=" +
                applicationContext.getEnvironment().getProperty("book.category", "unknown");
    }

    @GetMapping("/event")
    public String event() {

        applicationContext.publishEvent(new RefreshEvent(this, null, "just for test"));

        return "send RefreshEvent";
    }
}
