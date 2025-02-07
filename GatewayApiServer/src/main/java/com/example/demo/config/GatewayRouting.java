package com.example.demo.config;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class GatewayRouting {
@Bean
public RouteLocator configRoute(RouteLocatorBuilder builder) {
    return builder.routes().route("hospital-route", r->r.path("/hospitals-feign/**").uri("http://localhost:9091/hospitals-feign")).
            route("doctor-route", r->r.path("/doctors/**").uri("lb://find-doctor-all")).
            
            build();
}

}