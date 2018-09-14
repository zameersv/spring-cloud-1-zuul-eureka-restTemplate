package com.javabuilders.servicea.config;

import com.javabuilders.servicea.util.rest.PooledRestTemplateBuilder;
import com.javabuilders.servicea.util.rest.RestTemplateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Autowired
    private PooledRestTemplateBuilder pooledRestTemplateBuilder;

    @Bean
    @ConfigurationProperties(prefix = "rest.service-b")
    public RestTemplateProperties serviceBRestTemplateConfig() {
        return new RestTemplateProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "rest.service-c")
    public RestTemplateProperties serviceCRestTemplateConfig() {
        return new RestTemplateProperties();
    }


    @Bean(name = "restTemplateServiceB")
    public RestTemplate getServiceBRestTemplate(@Qualifier("serviceBRestTemplateConfig")
                                                        RestTemplateProperties restTemplateProperties) {
        return pooledRestTemplateBuilder.with(restTemplateProperties)
                .build();

    }

    @Bean(name = "restTemplateServiceC")
    public RestTemplate getServiceCRestTemplate(@Qualifier("serviceCRestTemplateConfig")
                                                        RestTemplateProperties restTemplateProperties) {
        return pooledRestTemplateBuilder.with(restTemplateProperties)
                .build();
    }


}
