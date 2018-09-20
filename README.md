# Spring cloud - Api Gateway, Service Discovery, REST client.
### Spring cloud Netflix components
~~~
  1. Service Discovery - Eureka server 
  2. Api Gateway - Zuul
  3. REST client - RestTemplate
~~~  
## Project Build
### Pre-requisites
1.  Java8
### Build Steps:
1.  clone the project from the gitHub: [ https://github.com/zameersv/spring-cloud-1-zuul-eureka-restTemplate.git](https://github.com/zameersv/spring-cloud-1-zuul-eureka-restTemplate.git " https://github.com/zameersv/spring-cloud-1-zuul-eureka-restTemplate.git")
2. From Project directory, run - `./gradlew clean build`
3. Start the spring boot app, run - `./gradlew :<moduleName>:bootRun`. For example, `./gradlew :registry:bootRun`

## 1. Service Discovery - Eureka Server:

A service registry is a phone book for your microservices. Each service registers itself with the service registry and tells the registry where it lives (host, port, node name).

This service registry is used by different other components of micro service based architecture like Api-gateway(Zuul), Turbine, Config server etc., to connect to the services without worrying about configuring the IP addresses of each service which may change frequently. As you will see in this project, service registry is used by Api-gateway(Zuul) to connect to the down stream services. 

When a service registers with Eureka server, it provides meta-data about itself — such as host, port, health indicator URL and other details. Eureka server receives heartbeat messages from each instance belonging to a service. If the heartbeat fails over a configurable timetable, the instance is normally removed from the registry.

    eureka:
      client:
        registerWithEureka: true
        fetchRegistry: true
        service-url:
          defaultZone: 'http://127.0.0.1:8761/eureka/'

In this case I am using Eureka server as a service registry. The other most popular service registry application is [[1]][consul]. 

### Steps to configure Eureka Server

1. Add the Netflix Eureka server dependency to build file and annotate the main class with @EnableEurekaServer.

`compile('org.springframework.cloud:spring-cloud-starter-netflix-eureka-server')`

## 2. Zuul
Spring Cloud has created an embedded Zuul proxy to ease the development of a common use case where a UI application wants to make proxy calls to one or more back end services. 
### Steps to configure Zuul
1. To enable it, annotate a Spring Boot main class with @EnableZuulProxy, ofcourse you need to add the dependency in the build file.

`compile('org.springframework.cloud:spring-cloud-starter-netflix-zuul')`

2. Add Eureka client dependency to the project.

3. Configure the routes using the below properties. In the below example all the routes with path /customer/** be redirected to service with service ID customer. Zuul will resolve this by checking with Eureka server.

        zuul:
          ignoredServices: '*'
          routes:
            customer:
              path: /customer/** 
              serviceId: customer
              stripPrefix: false

#### How Zuul will resolve the routes ?

The order and product services can be accessed directly using the below URLs.
1. Order Service - http://localhost:8081/customer/order/123
2. Product Service - http://localhost:8082/product/recommendation/234

With Zuul configured with routes, we can access the above REST Endpoints using hostname of the Zuul server assuming Zuul running in localhost at port 8080. 

1. Order Service - http://localhost:8080/customer/order/123
2. Product Service - http://localhost:8080/product/recommendation/234 

Zuul server will match the path of the REST endpoint(/customer/**) with the routes defined in its configuration. Once a matching path found, Zuul will resolve the service URL with Eureka server using the serivceID defined for that particular route. 

## 3. RestClient - RestTemplate
Spring boot provides RestTemplate to make service-to-service calls. 

In this project, there are 3 services created - Customer, Order & Product services. Customer service communicates with Order service and Product services through RestTemplate.

Two restTemplate beans are defined **orderApiRestTemplate** and **productApiRestTemplate** to communicate with order service and product service respectively. By defining separate restTemplate beans, gives us ability to maintain different settings for each of the RestTemplate Bean. 

For example, in this project I have defined separate connectionTimeout and readTimeout for order & product services. Also, by externalizing these settings  we can maintain different timeouts for different environments(test, UAT & Prod)

    rest:
      api:
        order:
          pool_size: 20
          connection_timeout: 2000
          read_timeout: 3000
        product:
          pool_size: 10
          connection_timeout: 5000
          read_timeout: 6000


#### Creating Pooled RestTemplate

If you are using RestTemplate to for service-to-service calls, it is important to realize that RestTemplate does not use any type of Http connection pool and will establish and close a connection every time a REST call is made.

In this project, each RestTemplate bean is created with it own connection pool. Attaching the screen shot of the code. Please refer to my complete project in  gitHub for more details regarding configuration.

If you access the order service using the below URL it will timeout as the order service is made to sleep for 3000ms.

http://localhost:8080/customer/order/1

[consul]: https://www.consul.io "Consul"
