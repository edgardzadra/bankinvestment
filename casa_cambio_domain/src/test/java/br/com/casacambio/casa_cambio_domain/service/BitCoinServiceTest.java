package br.com.casacambio.casa_cambio_domain.service;

import br.com.casacambio.casa_cambio_domain.BaseTest;
import br.com.casacambio.casa_cambio_domain.model.dto.BitCoinRequest;
import br.com.casacambio.casa_cambio_domain.model.entity.BitCoinBank;
import br.com.casacambio.casa_cambio_domain.model.entity.Customer;
import br.com.casacambio.casa_cambio_domain.repository.BitCoinBankRepository;
import br.com.casacambio.casa_cambio_domain.repository.CustomerRepository;
import br.com.casacambio.casa_cambio_domain.service.exception.BadRequestException;
import br.com.casacambio.casa_cambio_domain.service.exception.NotFoundException;
import br.com.casacambio.casa_cambio_domain.service.http.BitCoinApi;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BitCoinServiceTest extends BaseTest {

    @MockBean
    private BitCoinApi api;

    @Autowired
    private BitCoinService service;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    BitCoinBankRepository bitCoinBankRepository;

    @Test
    public void givenGetQuoteOfDayWhenServiceRespondOkThenGetQuote() {
        when(api.getAmount()).thenReturn(BigDecimal.valueOf(1000));

        BigDecimal quote = service.getQuote();

        Assert.assertEquals(BigDecimal.valueOf(1000), quote);
    }

    @Test(expected = BadRequestException.class)
    public void givenGetQuoteOfDayWhenServiceNotRespondThenThrowBadRequest() {
        when(api.getAmount()).thenThrow(new BadRequestException("Api timeout"));

        service.getQuote();
    }

    @Test
    public void givenSendAllDataThenSaveOperation() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Jose");
        customer.setDocumentNumber("12345678910");

        when(customerRepository.findByDocumentNumber(any())).thenReturn(Optional.of(customer));
        when(bitCoinBankRepository.save(any())).thenReturn(new BitCoinBank());

        BitCoinRequest request = new BitCoinRequest();
        request.setQuote(BigDecimal.valueOf(10000));
        request.setBitCoinPurchased(BigDecimal.valueOf(1));

        service.registerOperation(customer.getDocumentNumber(), request);
    }

    @Test(expected = NotFoundException.class)
    public void givenNotFoundCustomerThenThrowNotFoundException() {

        when(bitCoinBankRepository.save(any())).thenReturn(new BitCoinBank());

        BitCoinRequest request = new BitCoinRequest();
        request.setQuote(BigDecimal.valueOf(10000));
        request.setBitCoinPurchased(BigDecimal.valueOf(1));

        service.registerOperation("12345678910", request);
    }
}
