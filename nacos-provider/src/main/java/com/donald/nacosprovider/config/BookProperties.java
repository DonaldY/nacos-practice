package com.donald.nacosprovider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author donald
 * @date 2021/01/24
 */
@Configuration
@ConfigurationProperties(prefix = "book")
public class BookProperties {

    private String category;

    private String author;

    private String name;

    @Override
    public String toString() {
        return "BookProperties{" +
                "category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
