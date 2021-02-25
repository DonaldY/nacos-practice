package com.donald.gateway.config;

import com.donald.gateway.dto.AppIdDTO;
import com.donald.gateway.utils.RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author donald
 * @date 2021/02/25
 */
@Configuration
public class KeyResolverConfig {

    private ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Bean(name = "ideKeyResolver")
    public IdeKeyResolver ideKeyResolver() {

        return new IdeKeyResolver();
    }

    class IdeKeyResolver implements KeyResolver {

        @Override
        public Mono<String> resolve(ServerWebExchange exchange) {

            ServerHttpRequest request = exchange.getRequest();

            String contentType = request.getHeaders().getFirst("Content-Type");

            if (request.getMethod() != HttpMethod.POST || "multipart/form-data".equals(contentType)) {

                return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
            }

            String bodyContent = RequestUtil.resolveBodyFromRequest(exchange.getRequest());

            try {
                AppIdDTO appIdDTO = JSON_MAPPER.readValue(bodyContent, AppIdDTO.class);

                System.out.println("get appId from body : " + appIdDTO.getAppId());

                return Mono.just(appIdDTO.getAppId());

            } catch (JsonProcessingException ignored) {

            }

            return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        }
    }
}


