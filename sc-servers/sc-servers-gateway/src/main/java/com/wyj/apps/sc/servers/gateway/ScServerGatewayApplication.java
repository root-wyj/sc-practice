package com.wyj.apps.sc.servers.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2021-04-27
 */
@SpringBootApplication
@EnableEurekaClient
public class ScServerGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScServerGatewayApplication.class);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("orderRouter", r-> r.path("/order/**")
                        .filters(f -> f.addResponseHeader("X-CustomerHeader", "myHeader"))
                        .uri("lb://e-commerce-order")
                )
                .route("goodRouter", r -> r.path("/good/**")
                        .uri("lb://e-commerce-good")
                )
                .build();
    }

}
