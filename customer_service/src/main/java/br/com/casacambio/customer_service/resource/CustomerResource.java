package br.com.casacambio.customer_service.resource;

import br.com.casacambio.customer_service.model.CustomerRequest;
import br.com.casacambio.customer_service.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api/customer")
@Validated
public class CustomerResource {

    final private Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);

    final private CustomerService service;

    @Autowired
    public CustomerResource(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequest request) {
        LOGGER.info("createCustomer: documentNumer= {}", request.getDocumentNumber());
        service.createCustomer(request);
        LOGGER.info("createCustomer: success created documentNumer= {}", request.getDocumentNumber());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
