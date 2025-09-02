package impetus.cloudgateway.service.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
import java.util.function.Predicate;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        Function<PredicateSpec, Buildable<Route>> routeFunctions1  = p->
                p.path("/get")
                .uri("http://httpbin.org:80");
        RouteLocator builds = builder.routes()
                .route(p->p.path("/currency-conversion/**").uri("lb://conversion.service"))
                .route(p->p.path("/currency-conversion-feign/**").uri("lb://conversion.service"))
                .route(p->p.path("/currency-conversion-new/**")
                        .filters(f->
                                f.rewritePath("/currency-conversion-new/", "/currency-conversion-feign/"))
                        .uri("lb://conversion.service"))
                .route(p -> p.path("/currency-exchange/**").uri("lb://exchange.service"))

                .build();
        return builds;

//        RouteLocator build = builder.routes()
//                .route(routeFunction->routeFunction.path("currency-exchange")
//                        .uri("lb://exchange.service"))
//                .route(routeFunction-> routeFunction
//                        .path("/currency-conversion/**")
//                        .filters(f->
//                                f.rewritePath("/currency-conversion/(?<Segment>.*)",
//                                        "currency-conversion-feign/${segment}"))
//                        .uri("lb://conversion.service"))
//                .build();
//        return build;
    }
}
