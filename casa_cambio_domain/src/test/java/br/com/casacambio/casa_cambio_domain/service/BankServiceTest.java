package br.com.casacambio.casa_cambio_domain.service;

import br.com.casacambio.casa_cambio_domain.BaseTest;
import br.com.casacambio.casa_cambio_domain.model.dto.OperationRequest;
import br.com.casacambio.casa_cambio_domain.model.entity.Bank;
import br.com.casacambio.casa_cambio_domain.model.entity.Customer;
import br.com.casacambio.casa_cambio_domain.model.enums.OperationEnum;
import br.com.casacambio.casa_cambio_domain.repository.BankRepository;
import br.com.casacambio.casa_cambio_domain.repository.CustomerRepository;
import br.com.casacambio.casa_cambio_domain.service.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BankServiceTest extends BaseTest {

    @Autowired
    private BankService service;

    @MockBean
    private BankRepository repository;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void givenAllDataOkThenTransactionSave() {
        Customer customer = new Customer();
        customer.setName("Jose");
        customer.setDocumentNumber("12345678910");
        customer.setId(1L);

        OperationRequest request = new OperationRequest();
        request.setAmount(BigDecimal.valueOf(100));
        request.setOperation(OperationEnum.CREDITO);

        when(customerRepository.findByDocumentNumber(any()))
                .thenReturn(Optional.of(customer));

        service.saveOperation("12345678910", request);

        ArgumentCaptor<Bank> captor = ArgumentCaptor.forClass(Bank.class);


        verify(repository, times(1)).save(captor.capture());

        Bank value = captor.getValue();

        Assert.assertEquals(request.getAmount(), value.getAmount());
        Assert.assertEquals(request.getOperation(), value.getOperation());
        Assert.assertNotNull(value.getCustomer());
    }

    @Test(expected = NotFoundException.class)
    public void givenUserNotExistsThenthrowNotFoundException() {
        OperationRequest request = new OperationRequest();
        request.setAmount(BigDecimal.valueOf(100));
        request.setOperation(OperationEnum.CREDITO);

        when(customerRepository.findByDocumentNumber(any()))
                .thenThrow(new NotFoundException("user not found"));

        service.saveOperation("12345678910", request);
    }

    @Test
    public void GivenCustomerHaveDebitCreditThenReturnCalc() {
        Customer c1 = new Customer();
        c1.setId(1L);
        c1.setDocumentNumber("12345678910");
        c1.setName("JOSE");

        Bank b1 = new Bank();
        b1.setId(1L);
        b1.setAmount(BigDecimal.valueOf(100));
        b1.setOperation(OperationEnum.CREDITO);
        b1.setCustomer(c1);

        Bank b2 = new Bank();
        b2.setId(2L);
        b2.setAmount(BigDecimal.valueOf(60));
        b2.setOperation(OperationEnum.DEBITO);
        b2.setCustomer(c1);

        List<Bank> bank = new ArrayList<>();
        bank.add(b1);
        bank.add(b2);

        when(repository.findByCustomer_DocumentNumber(any())).thenReturn(bank);
        when(customerRepository.findByDocumentNumber(any())).thenReturn(Optional.of(c1));


        BigDecimal amount = service.getBalance(c1.getDocumentNumber());

        Assert.assertEquals(BigDecimal.valueOf(40), amount);

    }

    @Test(expected = NotFoundException.class)
    public void GivenCustomerDontHaveOperationsReturnNotFoundException() {
        Customer c1 = new Customer();
        c1.setId(1L);
        c1.setDocumentNumber("12345678910");
        c1.setName("JOSE");

        when(customerRepository.findByDocumentNumber(any())).thenReturn(Optional.of(c1));

        service.getBalance(c1.getDocumentNumber());
    }
}
