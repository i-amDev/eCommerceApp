package com.project.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("product-service", r -> r.path("/product/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .route("user-service", r -> r.path("/user/**")
                        .uri("lb://USER-SERVICE"))
                .build();
    }


}
