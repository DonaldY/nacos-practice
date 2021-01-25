package com.donald.nacosprovider.controller;

import com.donald.nacosprovider.config.BookProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author donald
 * @date 2021/01/24
 */
@RefreshScope
@RestController
class ConfigurationController {

    @Value("${book.author:unknown}")
    String bookAuthor;

    @Value("${book.name:unknown}")
    String bookName;

    @Value("${book.category:unknown}")
    String bookCategory;

    @Autowired
    BookProperties bookProperties;

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

        return  "env.get('book.author')="
                + applicationContext.getEnvironment().getProperty("book.author", "unknown")
                + "<br/>env.get('book.name')="
                + applicationContext.getEnvironment().getProperty("book.name", "unknown")
                + "<br/>env.get('book.category')="
                + applicationContext.getEnvironment().getProperty("book.category", "unknown");
    }

    @GetMapping("/config3")
    public String config3() {

        return  "env.get('book.author')="
                + applicationContext.getEnvironment().getProperty("book.author", "unknown")
                + "<br/>bookAuthor=" + bookAuthor
                + "<br/>bookProperties="
                + bookProperties;

    }
}