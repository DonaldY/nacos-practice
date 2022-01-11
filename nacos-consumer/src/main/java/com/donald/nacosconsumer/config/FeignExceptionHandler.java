package com.donald.nacosconsumer.config;

import com.donald.nacosconsumer.utils.JsonUtils;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author donald
 * @date 2022/01/10
 */
@RestControllerAdvice
public class FeignExceptionHandler {
    @ExceptionHandler(FeignException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handlePctException(FeignException exception) {
        logger.debug("param error.", exception);

        ExceptionResponse response  = JsonUtils.strToObject(exception.contentUTF8(), ExceptionResponse.class);

        return ResponseEntity.status(exception.status())
                .body(response);
    }

    private static final Logger logger = LoggerFactory.getLogger(FeignExceptionHandler.class);
}