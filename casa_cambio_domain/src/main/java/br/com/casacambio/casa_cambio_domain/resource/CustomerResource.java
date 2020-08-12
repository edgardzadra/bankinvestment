package br.com.casacambio.casa_cambio_domain.resource;

import br.com.casacambio.casa_cambio_domain.model.dto.CustomerRequest;
import br.com.casacambio.casa_cambio_domain.service.CustomerService;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("/v1/api/domain/customer")
@Validated
public class CustomerResource {

     final Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);

     final private CustomerService customerService;

     @Autowired
     public CustomerResource(CustomerService service) {
         this.customerService = service;
     }

    @PostMapping("/save")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequest customer) {
        LOGGER.info("createCustomer: documentNumber: {}", customer.getDocumentNumber());
        customerService.createCustomer(customer);
        LOGGER.info("createCustomer: successfully added customer documentNumber: {}",
                customer.getDocumentNumber());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/validate")
    public String customerExists(@RequestParam @NotBlank String documentNumber) {
        LOGGER.info("customerExists: documentNumber: {}", documentNumber);
        String customerExists = String.valueOf(customerService.customerExists(documentNumber));
        LOGGER.info("customerExists: {}", customerExists);

        return customerExists;
    }
}
