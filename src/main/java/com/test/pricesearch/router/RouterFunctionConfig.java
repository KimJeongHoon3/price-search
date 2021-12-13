package com.test.pricesearch.router;

import com.test.pricesearch.handler.PriceSearchHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {
    @Bean
    RouterFunction priceSearchRouterFunction(PriceSearchHandler priceSearchHandler){
        return route().GET("/",priceSearchHandler::init)
                .GET("market/{type}",priceSearchHandler::findName)
                .GET("market/{type}/{name}",priceSearchHandler::findPrice)
                .build();

    }
}
