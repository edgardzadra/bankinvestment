package br.com.casacambio.casa_cambio_domain.service;

import br.com.casacambio.casa_cambio_domain.BaseTest;
import br.com.casacambio.casa_cambio_domain.model.dto.CustomerRequest;
import br.com.casacambio.casa_cambio_domain.model.entity.Customer;
import br.com.casacambio.casa_cambio_domain.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest extends BaseTest {

    @MockBean
    private CustomerRepository repository;

    @Autowired
    private CustomerService service;

    @Test
    public void givenAllDataOkThenCustomerMustBeRegistered() {
        CustomerRequest request = new CustomerRequest();
        request.setDocumentNumber("12345678910");
        request.setName("Bruce Dickson");

        Customer customer = new Customer();
        customer.setName("Bruce Dickson");
        customer.setId(1L);
        customer.setDocumentNumber("12345678910");

        when(repository.save(any())).thenReturn(customer);

        service.createCustomer(request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(repository, times(1)).save(captor.capture());

        Customer captured = captor.getValue();

        Assert.assertEquals(customer.getDocumentNumber(), captured.getDocumentNumber());
        Assert.assertEquals(customer.getName(), captured.getName());

    }

    @Test
    public void givenAllDataOkThenCustomerMustBeVerified() {
        CustomerRequest request = new CustomerRequest();
        request.setDocumentNumber("12345678910");
        request.setName("Bruce Dickson");

        Customer customer = new Customer();
        customer.setName("Bruce Dickson");
        customer.setId(1L);
        customer.setDocumentNumber("12345678910");

        when(repository.findByDocumentNumber(any())).thenReturn(Optional.of(customer));

        service.customerExists("12345678910");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findByDocumentNumber(captor.capture());

        String captured = captor.getValue();

        Assert.assertEquals(customer.getDocumentNumber(), captured);

    }

}
