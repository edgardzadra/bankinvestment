package br.com.casacambio.bank.service.http;

import br.com.casacambio.bank.model.CreditDebitAccount;
import br.com.casacambio.bank.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class BankDomain {
    final private RestTemplate restTemplate;

    @Autowired
    public BankDomain(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createOperation(String documentNumber, CreditDebitAccount creditDebitAccount) {
        try{
            restTemplate.exchange("http://casacambiodomain:8081/v1/api/domain/bank/"+
                            documentNumber, HttpMethod.POST,
                    new HttpEntity<>(creditDebitAccount), Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Customer Not Found");
        }

    }
}
