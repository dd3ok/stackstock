package stackstock.web;

import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class KospiRouter implements WebFluxConfigurer {

    @Bean
    public RouterFunction<?> kospiRouterFunction() {
        return route(GET("/"),
                request -> ok().render("home")).
                andRoute(GET("/hello"),
                        request -> ok().bodyValue("hello"));
    }

}
