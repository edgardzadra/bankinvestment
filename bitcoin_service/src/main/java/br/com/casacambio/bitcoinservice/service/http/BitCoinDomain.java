package br.com.casacambio.bitcoinservice.service.http;

import br.com.casacambio.bitcoinservice.model.BitCoinRequest;
import br.com.casacambio.bitcoinservice.model.OperationRequest;
import br.com.casacambio.bitcoinservice.service.BitCoinService;
import br.com.casacambio.bitcoinservice.service.exception.BadRequestException;
import br.com.casacambio.bitcoinservice.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class BitCoinDomain {

    final private RestTemplate restTemplate;
    Logger LOGGER = LoggerFactory.getLogger(BitCoinService.class);

    @Autowired
    public BitCoinDomain(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public BigDecimal getQuoteBitCoin() {
        String quote;

        try {
             quote = restTemplate.getForObject("http://casacambiodomain:8081/v1/api/domain/bitcoin/quote", String.class);
        } catch (HttpClientErrorException.BadRequest e) {
            LOGGER.error("getQuoteBitCoin: error api timeout");
            throw new BadRequestException("Api timeout");
        }

        return new BigDecimal(quote.replaceAll("[^\\.0123456789]", ""));
    }

    public void createTransaction(String documentNumber, BitCoinRequest request) {
        try{
            restTemplate.exchange("http://casacambiodomain:8081/v1/api/domain/bitcoin/"+
                    documentNumber, HttpMethod.POST, new HttpEntity<>(request),Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            LOGGER.error("updateBalanceOfCustomer: customer {} not registered", documentNumber);
            throw new NotFoundException("Customer not registered");
        }
    }
}
