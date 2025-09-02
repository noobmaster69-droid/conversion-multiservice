package impetus.conversion.service.controller;

import impetus.conversion.service.model.CurrencyConversion;
import impetus.conversion.service.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/")
public class ConversionServiceController {

    @Autowired
    CurrencyExchangeProxy proxy;

    @GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversionRate(@PathVariable String from, @PathVariable String to,
                                                      @PathVariable BigDecimal quantity){

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);
        CurrencyConversion responseObj = responseEntity.getBody();
        responseObj.setQuantity(quantity);

        responseObj.setTotalCalculatedAmount(quantity.multiply(responseObj.getConversionMultiple()));
        return new CurrencyConversion(responseObj.getId(),from,to,responseObj.getConversionMultiple()
                ,quantity,responseObj.getTotalCalculatedAmount(), responseObj.getEnvironment());
        
    }

    @GetMapping("currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversionRateFeign(@PathVariable String from, @PathVariable String to,
                                                      @PathVariable BigDecimal quantity){

        CurrencyConversion responseObj = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(responseObj.getId(),from,to,responseObj.getConversionMultiple()
                ,quantity,responseObj.getConversionMultiple().multiply(quantity), responseObj.getEnvironment());

    }


}
