package com.donald.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


/**
 * @author donald
 * @date 2021/02/24
 */
@Component
public class CustomResponseFilterFactory extends ModifyResponseBodyGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Config config) {

        return new ModifyResponseGatewayFilter(this.getConfig());
    }

    private Config getConfig() {
        Config cf = new Config();

        cf.setRewriteFunction(byte[].class, byte[].class, getRewriteFunction());

        return cf;
    }

    /**
     * 重写 Response 返回体
     *
     * 如果 HTTP status 为 429, 则修改
     *
     * @return 重写方法
     */
    private RewriteFunction<byte[], byte[]> getRewriteFunction() {

        return (exchange, resp) -> {

            if (exchange.getResponse().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {

                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                exchange.getResponse().getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                String data = "{\"code\":" + 11429 + ",\"msg\": \"" + "request limit" + "\"}";

                return Mono.just(data.getBytes());
            }

            return Mono.just(resp);
        };
    }
}