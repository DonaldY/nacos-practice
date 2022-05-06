package com.donald.txmsgorder.controller;

import com.donald.txmsgorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author donald
 * @date 2022/05/06
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/submit_order")
    public String transfer(@RequestParam("productId")Long productId,
                           @RequestParam("payCount") Integer payCount){

        orderService.submitOrder(productId, payCount);
        return "下单成功";
    }
}
