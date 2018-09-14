package com.javabuilders.servicea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@RestController
public class ServiceAController {

    @Autowired
    @Qualifier("restTemplateServiceC")
    private RestTemplate restTemplateC;

    @Autowired
    @Qualifier("restTemplateServiceB")
    private RestTemplate restTemplateB;

    /**
     * Rest end point URL defined is just for demo purpose.
     * In real time follow REST URL standards
     * @param id
     * @return
     */
    @GetMapping("/service-a/call-service-b/{id}")
    public String getServiceBDetails(@PathVariable String id){
        //This service call will fail as Service-b will take 3 seconds.
        return restTemplateB.getForObject(format("http://localhost:8080/service-b/%s",id+"-b"),String.class);
    }

    /**
     * Rest end point URL defined is just for demo purpose.
     * In real time follow REST URL standards.
     * @param id
     * @return
     */
    @GetMapping("/service-a/call-service-c/{id}")
    public String getServiceCDetails(@PathVariable String id){
        //Calling Service-b instead of writing another spring boot application for service-c.
        //This service call wil get response back, as the resttemplateC has readTimeout of 5000ms which is more than 3000ms
        return restTemplateC.getForObject(format("http://localhost:8080/service-b/%s",id+"-b"),String.class);
    }

}
