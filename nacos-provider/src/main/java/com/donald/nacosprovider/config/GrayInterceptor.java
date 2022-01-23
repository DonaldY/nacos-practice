package com.donald.nacosprovider.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author donald
 * @date 2022/01/23
 */
public class GrayInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution execution) throws IOException {

        if (httpRequest.getHeaders().containsKey("Gray")) {

            String value = httpRequest.getHeaders().getFirst("Gray");
            if (value.equals("true")) {
                RibbonRequestContextHolder.getCurrentContext().put("Gray", Boolean.TRUE.toString());
            }
        }

        return execution.execute(httpRequest, bytes);
    }
}
