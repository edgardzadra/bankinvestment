package br.com.casacambio.bank.service;

import br.com.casacambio.bank.BaseTest;
import br.com.casacambio.bank.model.CreditDebitAccount;
import br.com.casacambio.bank.model.OperationEnum;
import br.com.casacambio.bank.service.http.BankDomain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OperateAccountServiceTest extends BaseTest {

    @MockBean
    private BankDomain bankDomain;

    @Autowired
    private OperateAccountService service;

    @Test
    public void givenAllDataSentThenReturnSuccess() throws Exception {
        String documentNumber = "12345678910";
        CreditDebitAccount creditDebitAccount = new CreditDebitAccount();
        creditDebitAccount.setAmount(BigDecimal.valueOf(100));
        creditDebitAccount.setOperation(OperationEnum.CREDITO);

        doNothing().when(bankDomain).createOperation(any(),any());

        service.operateAccount(documentNumber, creditDebitAccount);

        verify(bankDomain, times(1))
                .createOperation(documentNumber, creditDebitAccount);
    }
}
