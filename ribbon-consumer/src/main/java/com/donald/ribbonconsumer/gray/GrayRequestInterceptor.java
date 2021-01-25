package com.donald.ribbonconsumer.gray;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author donald
 * @date 2021/01/25
 */
public class GrayRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        if (template.headers().containsKey("Gray")) {
            String value = template.headers().get("Gray").iterator().next();
            if (value.equals("true")) {
                RibbonRequestContextHolder.getCurrentContext().put("Gray", Boolean.TRUE.toString());
            }
        }
    }
}
