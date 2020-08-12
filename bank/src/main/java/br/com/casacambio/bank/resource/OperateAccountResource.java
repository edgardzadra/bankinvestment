package br.com.casacambio.bank.resource;

import br.com.casacambio.bank.model.CreditDebitAccount;
import br.com.casacambio.bank.service.OperateAccountService;
import br.com.casacambio.bank.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/api/operate")
@Validated
public class OperateAccountResource {

    Logger LOGGER = LoggerFactory.getLogger(OperateAccountResource.class);

    final private OperateAccountService operateAccountService;

    @Autowired
    public OperateAccountResource(OperateAccountService operateAccountService) {
        this.operateAccountService = operateAccountService;
    }

    @PostMapping("/{documentNumber}")
    public ResponseEntity<?> operationCustomerAccount(@RequestBody CreditDebitAccount creditDebitAccount,
                                                   @NotBlank @PathVariable String documentNumber) {
        LOGGER.info("operationCustomerAccount: {} in {}",creditDebitAccount.getOperation(), documentNumber);
        try{
            operateAccountService.operateAccount(documentNumber, creditDebitAccount);
        } catch (NotFoundException e) {
            LOGGER.error("operationCustomerAccount: user not exists {}", documentNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            LOGGER.error("operationCustomerAccount: error - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("operationCustomerAccount: success inserted operation {} in {}",creditDebitAccount.getOperation(), documentNumber);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
