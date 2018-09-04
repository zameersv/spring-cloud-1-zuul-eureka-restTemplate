package com.javabuilders.serviceb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;

@RestController
public class ServiceControllerB {

    @GetMapping("/service-b/{id}")
    public String getServiceB(@PathVariable String id) throws InterruptedException {
        Thread.sleep(3000l);
        return id;
    }
}

