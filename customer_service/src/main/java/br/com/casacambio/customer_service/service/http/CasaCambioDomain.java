package br.com.casacambio.customer_service.service.http;

import br.com.casacambio.customer_service.model.CustomerRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CasaCambioDomain {

    final private RestTemplate restTemplate;

    public CasaCambioDomain(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean customerExists(String documentNumber) {
        String response = restTemplate
                .getForObject("http://casacambiodomain:8081/v1/api/domain/customer/validate?documentNumber="+
                        documentNumber, String.class);

        return Boolean.parseBoolean(response);
    }

    public void saveCustomer(CustomerRequest request) {
        restTemplate.exchange("http://casacambiodomain:8081/v1/api/domain/customer/save", HttpMethod.POST,
                new HttpEntity<>(request), Object.class);
    }
}
