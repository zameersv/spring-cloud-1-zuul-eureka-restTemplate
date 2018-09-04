package com.javabuilders.servicea.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Bean(name = "restTemplateServiceB")
    public RestTemplate getServiceBRestTemplate(){
        return restTemplateBuilder.setConnectTimeout(2000)
                .setReadTimeout(3000)
                .build();
    }

    @Bean(name = "restTemplateServiceC")
    public RestTemplate getServiceCRestTemplate(){
        return restTemplateBuilder.setConnectTimeout(5000)
                .setReadTimeout(6000)
                .build();
    }


}
