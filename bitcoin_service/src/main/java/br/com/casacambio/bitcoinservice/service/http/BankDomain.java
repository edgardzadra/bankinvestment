package br.com.casacambio.bitcoinservice.service.http;

import br.com.casacambio.bitcoinservice.model.OperationRequest;
import br.com.casacambio.bitcoinservice.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class BankDomain {
    final private RestTemplate restTemplate;

    Logger LOGGER = LoggerFactory.getLogger(BankDomain.class);

    @Autowired
    public BankDomain(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getCustomerBalance(String documentNumber) {
        try{
            String balance =  restTemplate.getForObject("http://casacambiodomain:8081/v1/api/domain/bank/"+
                            documentNumber+"/balance", String.class);

            return new BigDecimal(balance.replaceAll("[^\\.0123456789]",""));
        } catch (HttpClientErrorException.NotFound e) {
            LOGGER.error("getCustomerBalance: customer {} not have balance to show", documentNumber);
            throw new NotFoundException("Customer does not have credit/debit for balance");
        }

    }

    public void updateBalanceOfCustomer(String documentNumber, OperationRequest request) {
        try{
            restTemplate.exchange("http://casacambiodomain:8081/v1/api/domain/bank/"+
                    documentNumber, HttpMethod.POST, new HttpEntity<>(request),Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            LOGGER.error("updateBalanceOfCustomer: customer {} not registered", documentNumber);
            throw new NotFoundException("Customer not registered");
        }
    }
}
