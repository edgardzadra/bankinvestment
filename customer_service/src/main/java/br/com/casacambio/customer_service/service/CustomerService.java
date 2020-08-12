package br.com.casacambio.customer_service.service;

import br.com.casacambio.customer_service.model.CustomerRequest;
import br.com.casacambio.customer_service.resource.CustomerResource;
import br.com.casacambio.customer_service.service.exception.CustomerAlreadyExistsException;
import br.com.casacambio.customer_service.service.http.CasaCambioDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    final private Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);

    final private CasaCambioDomain casaCambioDomain;

    public CustomerService(CasaCambioDomain casaCambioDomain) {
        this.casaCambioDomain = casaCambioDomain;
    }

    public void createCustomer(CustomerRequest request) {
        boolean isCustomerRegistered = casaCambioDomain.customerExists(request.getDocumentNumber());

        if (!isCustomerRegistered) {
            casaCambioDomain.saveCustomer(request);
        } else {
            LOGGER.error("createCustomer: customer already exists documentNumber = {}", request.getDocumentNumber());
            throw new CustomerAlreadyExistsException("Customer Already Registered");
        }
    }
}
