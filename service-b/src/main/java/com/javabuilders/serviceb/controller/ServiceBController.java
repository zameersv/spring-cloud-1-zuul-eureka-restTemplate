package com.javabuilders.serviceb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServiceBController {

    @GetMapping("/service-b/{id}")
    public String getServiceB(@PathVariable String id) throws InterruptedException {
        Thread.sleep(3000l);
        return id;
    }
}

