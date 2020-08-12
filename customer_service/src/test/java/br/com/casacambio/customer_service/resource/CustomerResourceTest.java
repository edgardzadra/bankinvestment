package br.com.casacambio.customer_service.resource;

import br.com.casacambio.customer_service.BaseTest;
import br.com.casacambio.customer_service.model.CustomerRequest;
import br.com.casacambio.customer_service.service.CustomerService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

        mockMvc.perform(post("/v1/api/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(customerRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenSendDataMissingNameThenResponseBadRequest() throws Exception {
        doNothing().when(service).createCustomer(any());

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setDocumentNumber("12345678910");
        customerRequest.setName("");

        mockMvc.perform(post("/v1/api/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(customerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenSendDataMissingDocumentThenResponseBadRequest() throws Exception {
        doNothing().when(service).createCustomer(any());

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setDocumentNumber("");
        customerRequest.setName("Teste");

        mockMvc.perform(post("/v1/api/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(customerRequest)))
                .andExpect(status().isBadRequest());
    }


}
