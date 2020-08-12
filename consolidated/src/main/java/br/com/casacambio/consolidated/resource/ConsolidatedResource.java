package br.com.casacambio.consolidated.resource;

import br.com.casacambio.consolidated.model.ConsolidatedInfoCustomer;
import br.com.casacambio.consolidated.service.ConsolidatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/consolidated")
public class ConsolidatedResource {


    final ConsolidatedService service;

    @Autowired
    public ConsolidatedResource(ConsolidatedService service) {
        this.service = service;
    }

    @GetMapping("/{documentNumber}")
    public ConsolidatedInfoCustomer getInformations(@PathVariable String documentNumber) {
        return service.getInformation(documentNumber);
    }
}
