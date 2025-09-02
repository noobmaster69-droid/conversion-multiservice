package impetus.exchange.service.controller;

import impetus.exchange.service.model.CurrencyExchange;
import impetus.exchange.service.repo.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/")
public class ExchangeServiceController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repo;

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
        CurrencyExchange obj = repo.findByFromAndTo(from, to);
        if(obj == null){
            throw new RuntimeException("Errrrr....");
        }
        obj.setEnvironment(environment.getProperty("server.port"));
        return obj;
    }
}
