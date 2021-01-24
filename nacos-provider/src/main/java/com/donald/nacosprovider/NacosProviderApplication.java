package com.donald.nacosprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class NacosProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosProviderApplication.class, args);
	}

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
    }

    @RestController
    class ConfigurationController {

        @Value("${book.author:unknown}")
        String bookAuthor;

        @Value("${book.name:unknown}")
        String bookName;

        @Value("${book.category:unknown}")
        String bookCategory;

        @Autowired
        ApplicationContext applicationContext;

        @GetMapping("/config1")
        public String config() {
            return "bookAuthor=" + bookAuthor +
                    "<br/>bookName=" + bookName +
                    "<br/>bookCategory=" + bookCategory;
        }

        @GetMapping("/config2")
        public String config2() {

            return "env.get('book.category')="
                    + applicationContext.getEnvironment().getProperty("book.category", "unknown") +
                    "<br/>env.get('book.author')="
                    + applicationContext.getEnvironment().getProperty("book.author", "unknown");
        }

        @GetMapping("/event")
        public String event() {

            applicationContext.publishEvent(new RefreshEvent(this, null, "just for test"));

            return "send RefreshEvent";
        }
    }
}
