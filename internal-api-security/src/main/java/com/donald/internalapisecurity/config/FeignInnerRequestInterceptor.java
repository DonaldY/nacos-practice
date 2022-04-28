package com.donald.internalapisecurity.config;

import com.donald.internalapisecurity.common.Constant;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * feign 内部请求拦截器
 *
 * @author donald
 * @date 2022/01/12
 */
@Component
@ConditionalOnClass(Feign.class)
public class FeignInnerRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        template.header(Constant.INNER_REQUEST_HEADER, "true");
    }
}
