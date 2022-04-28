package com.donald.internalapisecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author donald
 * @date 2022/01/12
 */
@Configuration
public class InternalApiConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(internalApiInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");
    }

    @Bean
    public InternalApiInterceptor internalApiInterceptor() {
        return new InternalApiInterceptor();
    }
}