package br.com.casacambio.casa_cambio_domain.resource;

import br.com.casacambio.casa_cambio_domain.BaseTest;
import br.com.casacambio.casa_cambio_domain.model.dto.BitCoinRequest;
import br.com.casacambio.casa_cambio_domain.service.BitCoinService;
import br.com.casacambio.casa_cambio_domain.service.exception.BadRequestException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BitCoinResourceTest extends BaseTest {

    @MockBean
    private BitCoinService service;

    @Test
    public void givenCallGetQuoteThenRespondOk() throws Exception {
        when(service.getQuote()).thenReturn(BigDecimal.valueOf(1000));

        MvcResult result = mockMvc.perform(get("/v1/api/domain/bitcoin/quote"))
                .andExpect(status().isOk())
                .andReturn();

        String amount = result.getResponse().getContentAsString();

        Assert.assertEquals("1000", amount);
    }

    @Test
    public void givenCallGetQuoteThenRespondBadRequest() throws Exception {
        when(service.getQuote()).thenThrow(new BadRequestException("Api timeout"));

        mockMvc.perform(get("/v1/api/domain/bitcoin/quote"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenCallCreateOperationThenCreated() throws Exception {
        String documentNumber = "12345678910";
        BitCoinRequest request = new BitCoinRequest();
        request.setBitCoinPurchased(BigDecimal.valueOf(1));
        request.setQuote(BigDecimal.valueOf(10000));

        doNothing().when(service).registerOperation(any(), any());

        mockMvc.perform(post("/v1/api/domain/bitcoin/" + documentNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenCallCreateOperationThenNotFound() throws Exception {
        String documentNumber = "12345678910";
        BitCoinRequest request = new BitCoinRequest();
        request.setBitCoinPurchased(BigDecimal.valueOf(1));
        request.setQuote(BigDecimal.valueOf(10000));

        doThrow(new NotFoundException("user not founde"))
                .when(service).registerOperation(any(), any());

        mockMvc.perform(post("/v1/api/domain/bitcoin/" + documentNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(status().isNotFound());
    }

}
