package br.com.casacambio.casa_cambio_domain.resource;

import br.com.casacambio.casa_cambio_domain.model.dto.OperationRequest;
import br.com.casacambio.casa_cambio_domain.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/api/domain/bank")
@Validated
public class BankResource {

    final Logger LOGGER = LoggerFactory.getLogger(BankResource.class);
    final private BankService bankService;

    public BankResource(BankService service) {
        this.bankService = service;
    }

    @PostMapping("/{documentNumber}")
    public ResponseEntity<?> createOperation(@Valid @RequestBody OperationRequest request,
                                          @PathVariable String documentNumber) {
        LOGGER.info("createOperation: {} in {}", request.getOperation(), documentNumber);
        bankService.saveOperation(documentNumber, request);
        LOGGER.info("createOperation: success {} in {}", request.getOperation(), documentNumber);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{documentNumber}/balance")
    public BigDecimal getCustomerBalance(@PathVariable @NotBlank String documentNumber) {
        BigDecimal amount = null;

        LOGGER.info("getCustomerBalance: documentNumber {}", documentNumber);
        amount = bankService.getBalance(documentNumber);
        LOGGER.info("getCustomerBalance: success documentNumber {}", documentNumber);

        return amount;
    }

    @GetMapping("/{documentNumber}/investment")
    public BigDecimal getTotalInvestiment(@PathVariable String documentNumber) {
        LOGGER.info("getTotalInvestiment: customer {}", documentNumber);
        BigDecimal value = bankService.getTotalInvestment(documentNumber);
        LOGGER.info("getTotalInvestiment: successfully customer {}", documentNumber);
        return value;
    }
}
