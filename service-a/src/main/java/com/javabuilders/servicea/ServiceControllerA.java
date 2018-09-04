package com.javabuilders.servicea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@RestController
public class ServiceControllerA {

    @Autowired
    @Qualifier("restTemplateServiceC")
    private RestTemplate restTemplateC;

    @Autowired
    @Qualifier("restTemplateServiceB")
    private RestTemplate restTemplateB;

    @GetMapping("/service-a/{id}")
    public String getServiceADetails(@PathVariable String id){
        return id;
    }

    @GetMapping("/service-a/b/{id}")
    public String getServiceBDetails(@PathVariable String id){
        //This service call will fail as Service-b will take 30 seconds.
        String serviceB = restTemplateB.getForObject(format("http://localhost:8080/service-b/%s",id+"-b"),String.class);
        return serviceB;
    }

    @GetMapping("/service-a/c/{id}")
    public String getServiceCDetails(@PathVariable String id){
        //This service cal wil pass as the resttemplateC has readTimeout more than 3000ms
        String serviceB = restTemplateC.getForObject(format("http://localhost:8080/service-b/%s",id+"-b"),String.class);
        return serviceB;
    }

}
