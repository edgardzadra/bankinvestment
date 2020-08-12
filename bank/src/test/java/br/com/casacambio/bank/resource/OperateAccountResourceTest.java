package br.com.casacambio.bank.resource;

import br.com.casacambio.bank.BaseTest;
import br.com.casacambio.bank.model.CreditDebitAccount;
import br.com.casacambio.bank.model.OperationEnum;
import br.com.casacambio.bank.service.OperateAccountService;
import br.com.casacambio.bank.service.exception.NotFoundException;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OperateAccountResourceTest extends BaseTest {

    @MockBean
    private OperateAccountService service;

    @Test
    public void givenAllDataSentthenReturnCreated() throws Exception {
        String documentNumber = "12345678910";
        CreditDebitAccount creditDebitAccount = new CreditDebitAccount();
        creditDebitAccount.setAmount(BigDecimal.valueOf(100));
        creditDebitAccount.setOperation(OperationEnum.CREDITO);

        doNothing().when(service).operateAccount(any(), any());

        mockMvc.perform(post("/v1/api/operate/"+documentNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(creditDebitAccount)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenUserNotExistsThenReturnNotFound() throws Exception {
        String documentNumber = "12345678910";
        CreditDebitAccount creditDebitAccount = new CreditDebitAccount();
        creditDebitAccount.setAmount(BigDecimal.valueOf(100));
        creditDebitAccount.setOperation(OperationEnum.CREDITO);

        doThrow(new NotFoundException("User not found")).when(service).operateAccount(any(), any());

        mockMvc.perform(post("/v1/api/operate/"+documentNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(creditDebitAccount)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenErrorThenReturnBadRequest() throws Exception {
        String documentNumber = "12345678910";
        CreditDebitAccount creditDebitAccount = new CreditDebitAccount();
        creditDebitAccount.setAmount(BigDecimal.valueOf(100));
        creditDebitAccount.setOperation(OperationEnum.CREDITO);

        doThrow(new Exception()).when(service).operateAccount(any(), any());

        mockMvc.perform(post("/v1/api/operate/"+documentNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(creditDebitAccount)))
                .andExpect(status().isBadRequest());
    }
}
