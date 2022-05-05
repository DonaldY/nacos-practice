package com.donald.nacosconsumer.controller;

//import org.springframework.cloud.openfeign.FeignClient

/**
 * @author donald
 * @date 2022/05/05
 */
@org.springframework.cloud.openfeign.FeignClient(value = "cms", fallback = ICmsClientFallBackImpl.class)
public interface FeignClient {
}
