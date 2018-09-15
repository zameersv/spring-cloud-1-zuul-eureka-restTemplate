package com.javabuilders.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderController {

    @GetMapping("/order/{id}")
    public String getOrderById(@PathVariable String id) throws InterruptedException {
        Thread.sleep(3000l);
        return id;
    }
}

