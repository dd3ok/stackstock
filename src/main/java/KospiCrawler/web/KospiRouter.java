package KospiCrawler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import KospiCrawler.handler.KospiHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class KospiRouter implements WebFluxConfigurer {

    @Autowired
    KospiHandler kospiHandler;

    @Bean
    public RouterFunction<?> kospiRouterFunction() {
        return route(GET("/"),
                request -> ok().render("home")).
                andRoute(GET("/hello"),
                        request -> ok().bodyValue("hello")).
                andRoute(GET("/code/{code}"), kospiHandler::getKospi).
                andRoute(POST("/code/{code}"), kospiHandler::postKospi).
                andRoute(PUT("/code/{code}"), kospiHandler::putKospi).
                andRoute(DELETE("/code/{code}"), kospiHandler::deleteKospi);

    }

}
