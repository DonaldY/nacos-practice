package com.donald.nacosprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author donald
 * @date 2021/01/24
 */
@RestController
class EchoController {

    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/echo")
    public String echo(HttpServletRequest request) {
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
