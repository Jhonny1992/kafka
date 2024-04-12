package pe.com.susaya.gateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder locatorBuilder){
        return locatorBuilder
                .routes()
                .route( route ->route
                        .path("/microservice04/v1/service04/**")
                        .uri("http://microservice04:8085")
                )
                .build();
    }


}
