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

    // 注意：需要有 setter 方法，参数注入才有效
    public void setCategory(String category) {
        this.category = category;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookProperties{" +
                "category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
