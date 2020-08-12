package br.com.casacambio.consolidated.service.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class CambioDomain {

    final private RestTemplate restTemplate;

    public CambioDomain(RestTemplate template) {
        this.restTemplate = template;
    }

    public BigDecimal getQuoteOfBitCoin() {
        String quote = restTemplate.getForObject("http://casacambiodomain:8081/v1/api/domain/bitcoin/quote", String.class);
        return convertStringToBigDecimal(quote);
    }

    public BigDecimal getCustomerTotalInvestiment(String documentNumber) {
        String quote = restTemplate.getForObject("http://casacambiodomain:8081/v1/api/domain/bank/"+documentNumber+"/investment", String.class);
        return convertStringToBigDecimal(quote);
    }

    public BigDecimal getCustomerBalance(String documentNumber) {
        String quote = restTemplate.getForObject("http://casacambiodomain:8081/v1/api/domain/bank/"+documentNumber+"/balance", String.class);
        return convertStringToBigDecimal(quote);
    }

    public BigDecimal getBalanceOfCustomerBitCoin(String documentNumber) {
        String quote = restTemplate.getForObject("http://casacambiodomain:8081/v1/api/domain/bitcoin/"+documentNumber+"/balance", String.class);
        return convertStringToBigDecimal(quote);
    }


    private BigDecimal convertStringToBigDecimal(String value) {
        return new BigDecimal(value.replaceAll("[^\\.0123456789]",""));
    }
}
