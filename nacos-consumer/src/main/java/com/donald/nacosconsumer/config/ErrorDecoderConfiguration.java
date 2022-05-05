package com.donald.nacosconsumer.config;

import com.donald.nacosconsumer.common.Response;
import com.donald.nacosconsumer.utils.JsonUtils;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * @author donald
 * @date 2022/05/05
 */
public class ErrorDecoderConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignError();
    }
    /**
     * 自定义错误解码器
     */
    public class FeignError implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, feign.Response response) {

            String json = "";
            try {
                // 1. 获取原始的返回内容
                json = Util.toString(response.body().asReader());

            } catch (IOException e) {

            }

            // 2. 解析错误
            Response responseObject  = JsonUtils.strToObject(json, Response.class);

            // 3. 抛出业务异常
            return new Exception();
        }
    }
}

