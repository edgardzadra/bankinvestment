package br.com.casacambio.customer_service.service;

import br.com.casacambio.customer_service.BaseTest;
import br.com.casacambio.customer_service.model.CustomerRequest;
import br.com.casacambio.customer_service.service.exception.CustomerAlreadyExistsException;
import br.com.casacambio.customer_service.service.http.CasaCambioDomain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest extends BaseTest {

    @MockBean
    private CasaCambioDomain casaCambioDomain;

    @Autowired
    private CustomerService service;

    @Test
    public void givenAllDataOkThenCustomerMustBeRegistered() {
        CustomerRequest request = new CustomerRequest();
        request.setDocumentNumber("12345678910");
        request.setName("Bruce Dickson");

        service.createCustomer(request);

        when(casaCambioDomain.customerExists(any())).thenReturn(false);

        service.createCustomer(request);

        verify(casaCambioDomain, times(2)).saveCustomer(request);
        verify(casaCambioDomain, times(2)).customerExists(request.getDocumentNumber());

    }

    @Test(expected = CustomerAlreadyExistsException.class)
    public void givenSendSameDocumentNumberThenThrowException() {
        CustomerRequest request = new CustomerRequest();
        request.setDocumentNumber("12345678910");
        request.setName("Bruce Dickson");

        when(casaCambioDomain.customerExists(any())).thenReturn(true);
        service.createCustomer(request);
    }

}
