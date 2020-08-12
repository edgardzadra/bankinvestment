package br.com.casacambio.casa_cambio_domain.resource;

import br.com.casacambio.casa_cambio_domain.BaseTest;
import br.com.casacambio.casa_cambio_domain.model.dto.OperationRequest;
import br.com.casacambio.casa_cambio_domain.model.enums.OperationEnum;
import br.com.casacambio.casa_cambio_domain.service.BankService;
import br.com.casacambio.casa_cambio_domain.service.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BankResourceTest extends BaseTest {

    @MockBean
    private BankService service;

    @Test
    public void givenAllDataSentThenReturnCreated() throws Exception {
        doNothing().when(service).saveOperation(any(), any());
        String document = "12345678910";

        OperationRequest request = new OperationRequest();
        request.setOperation(OperationEnum.CREDITO);
        request.setAmount(BigDecimal.valueOf(100));

        mockMvc.perform(post("/v1/api/domain/bank/" + document)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenUserNotFoundThenReturnNotFound() throws Exception {
        doThrow(new NotFoundException("user not found"))
                .when(service).saveOperation(any(), any());
        String document = "12345678910";

        OperationRequest request = new OperationRequest();
        request.setOperation(OperationEnum.CREDITO);
        request.setAmount(BigDecimal.valueOf(100));

        mockMvc.perform(post("/v1/api/domain/bank/" + document)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenAllDataSentBalanceThenReturnOk() throws Exception {
        when(service.getBalance(any())).thenReturn(BigDecimal.valueOf(100));
        String document = "12345678910";

        MvcResult result = mockMvc.perform(get("/v1/api/domain/bank/" + document + "/balance"))
                .andExpect(status().isOk())
                .andReturn();

        String value = result.getResponse().getContentAsString();

        Assert.assertEquals("100", value);
    }

    @Test
    public void givenNotFoundOperationsThenReturnNotFound() throws Exception {
        when(service.getBalance(any())).thenThrow(new NotFoundException("user not found"));
        String document = "12345678910";

        mockMvc.perform(get("/v1/api/domain/bank/" + document + "/balance"))
                .andExpect(status().isNotFound());
    }
}
