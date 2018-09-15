package com.javabuilders.servicea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@RestController
public class CustomerController {

    @Autowired
    @Qualifier("orderApiRestTemplate")
    private RestTemplate orderApiRestTemplate;

    @Autowired
    @Qualifier("productApiRestTemplate")
    private RestTemplate productApiRestTemplateConfig;

    @GetMapping("/customer/order/{id}")
    public String getCustomerOrderDetails(@PathVariable String id){
        //This service call will fail to Order Service will take 3 seconds.
        return orderApiRestTemplate.getForObject(format("http://localhost:8080/order/%s",id+"-b"),String.class);
    }

    @GetMapping("/customer/product/recommendation/{id}")
    public String getCustomerProductRecommendation(@PathVariable String id){
        //This service call wil get response back, as the productApiRestTemplateConfig has readTimeout of 5000ms which is more than 3000ms
        return productApiRestTemplateConfig.getForObject(format("http://localhost:8080/product/recommendation/%s",id),String.class);
    }

}
