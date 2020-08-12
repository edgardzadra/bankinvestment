package br.com.casacambio.casa_cambio_domain.resource;

import br.com.casacambio.casa_cambio_domain.BaseTest;
import br.com.casacambio.casa_cambio_domain.model.dto.CustomerRequest;
import br.com.casacambio.casa_cambio_domain.service.CustomerService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerResourceTest extends BaseTest {

    @MockBean
    private CustomerService service;

    @Test
    public void givenSendDataThenResponseCreated() throws Exception {
        doNothing().when(service).createCustomer(any());

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setDocumentNumber("12345678910");
        customerRequest.setName("Teste");

        mockMvc.perform(post("/v1/api/domain/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(customerRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenSendDataMissingNameThenResponseUnprocessableEntity() throws Exception {
        doNothing().when(service).createCustomer(any());

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setDocumentNumber("12345678910");
        customerRequest.setName("");

        mockMvc.perform(post("/v1/api/domain/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(customerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenSendDataMissingDocumentThenResponseUnprocessableEntity() throws Exception {
        doNothing().when(service).createCustomer(any());

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setDocumentNumber("");
        customerRequest.setName("Teste");

        mockMvc.perform(post("/v1/api/domain/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(customerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidatedCustomerExistsThenOk() throws Exception {
        when(service.customerExists(any())).thenReturn(true);

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setDocumentNumber("12345678910");
        customerRequest.setName("Teste");

        mockMvc.perform(get("/v1/api/domain/customer/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .param("documentNumber","12345678910"))
                .andExpect(status().isOk());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(service, times(1)).customerExists(captor.capture());

        Assert.assertEquals("12345678910",captor.getValue());
    }


    @Test(expected = NestedServletException.class)
    public void givenDocumentNumberNotPassedExistsThenBadRequest() throws Exception {
        when(service.customerExists(any())).thenReturn(true);

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setDocumentNumber("12345678910");
        customerRequest.setName("Teste");

        mockMvc.perform(get("/v1/api/domain/customer/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .param("documentNumber",""))
                .andExpect(status().isBadRequest());

    }

}
