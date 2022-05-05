package com.donald.nacosconsumer.controller;

import com.donald.nacosconsumer.common.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author donald
 * @date 2022/05/05
 */
@FeignClient(value = "my-provider")
public interface ProviderClient {

    @GetMapping(value = "/test/ok")
    Response ok();

    @GetMapping(value = "/test/error")
    Response error();
}
