package br.com.casacambio.bitcoinservice.resource;

import br.com.casacambio.bitcoinservice.BaseTest;
import br.com.casacambio.bitcoinservice.model.AmountRequest;
import br.com.casacambio.bitcoinservice.service.BitCoinService;
import br.com.casacambio.bitcoinservice.service.exception.BadRequestException;
import br.com.casacambio.bitcoinservice.service.exception.NotFoundException;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BitCoinResourceTest extends BaseTest {

    @MockBean
    private BitCoinService service;

    @Test
    public void givenAllDataSentThenReturnCreated() throws Exception {
        String documentNumber = "12345678910";
        AmountRequest amount = new AmountRequest();
        amount.setAmount("1");

        doNothing().when(service).purchaseBitCoin(any(), any());

        mockMvc.perform(post("/v1/api/bitcoin/"+documentNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(amount)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenUserNotFoundThenReturnNotFound() throws Exception {
        String documentNumber = "12345678910";
        AmountRequest amount = new AmountRequest();
        amount.setAmount("1");

        doThrow(new NotFoundException("user not found")).when(service).purchaseBitCoin(any(), any());

        mockMvc.perform(post("/v1/api/bitcoin/"+documentNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(amount)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenUserNotHaveCreditThenReturnBadRequest() throws Exception {
        String documentNumber = "12345678910";
        AmountRequest amount = new AmountRequest();
        amount.setAmount("1");

        doThrow(new BadRequestException("user does not have found")).when(service).purchaseBitCoin(any(), any());

        mockMvc.perform(post("/v1/api/bitcoin/"+documentNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(amount)))
                .andExpect(status().isBadRequest());
    }

}
