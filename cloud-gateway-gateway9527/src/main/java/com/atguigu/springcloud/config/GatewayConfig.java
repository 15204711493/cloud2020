package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator ok(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("bd",r->r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        routes.route("bd",r->r.path("/guoji").uri("http://news.baidu.com/guonji")).build();
        return routes.build();

    }
}
