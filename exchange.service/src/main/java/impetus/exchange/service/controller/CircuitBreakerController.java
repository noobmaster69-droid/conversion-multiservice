package impetus.exchange.service.controller;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerController {

    

    @GetMapping("/circuitbreaker")
    @Retry(name = "default", fallbackMethod = "fallBackMethod")
    public String callCircuitBreak(){
        throw new RuntimeException("Exception");
    }

    public String fallBackMethod(Exception ex){
        return "fallback";
    }

}
