package br.com.casacambio.bitcoinservice.service;

import br.com.casacambio.bitcoinservice.BaseTest;
import br.com.casacambio.bitcoinservice.model.BitCoinRequest;
import br.com.casacambio.bitcoinservice.service.exception.BadRequestException;
import br.com.casacambio.bitcoinservice.service.http.BankDomain;
import br.com.casacambio.bitcoinservice.service.http.BitCoinDomain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BitCoinServiceTest extends BaseTest {

    @MockBean
    private BankDomain bankDomain;

    @MockBean
    private BitCoinDomain bitCoinDomain;

    @Autowired
    private BitCoinService service;

    @Test
    public void givenAllDataSentThenReturnSuccess() throws Exception {
        when(bankDomain.getCustomerBalance(any())).thenReturn(BigDecimal.valueOf(20000));
        when(bitCoinDomain.getQuoteBitCoin()).thenReturn(BigDecimal.valueOf(10000));

        BigDecimal amount = BigDecimal.valueOf(1);
        String documentNumber = "12345678910";
        service.purchaseBitCoin(amount,documentNumber);
    }

    @Test(expected = BadRequestException.class)
    public void givenAllDataSentThenReturnBadRequest() throws Exception {
        when(bankDomain.getCustomerBalance(any())).thenReturn(BigDecimal.valueOf(9000));
        when(bitCoinDomain.getQuoteBitCoin()).thenReturn(BigDecimal.valueOf(10000));

        BigDecimal amount = BigDecimal.valueOf(1);
        String documentNumber = "12345678910";
        service.purchaseBitCoin(amount,documentNumber);
    }

    @Test(expected = BadRequestException.class)
    public void givenApiTimeoutThenReturnBadRequest() throws Exception {
        when(bankDomain.getCustomerBalance(any())).thenReturn(BigDecimal.valueOf(9000));
        when(bitCoinDomain.getQuoteBitCoin()).thenThrow(new BadRequestException("timeout api"));

        BigDecimal amount = BigDecimal.valueOf(1);
        String documentNumber = "12345678910";
        service.purchaseBitCoin(amount,documentNumber);
    }
}
