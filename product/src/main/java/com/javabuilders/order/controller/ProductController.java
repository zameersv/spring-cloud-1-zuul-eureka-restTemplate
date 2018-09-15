package com.javabuilders.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {

    @GetMapping("/product/recommendation/{id}")
    public String getProductRecommendations(@PathVariable String id) throws InterruptedException {
        Thread.sleep(3000l);
        return id;
    }
}

