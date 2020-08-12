package br.com.casacambio.casa_cambio_domain.resource;

import br.com.casacambio.casa_cambio_domain.model.dto.BitCoinRequest;
import br.com.casacambio.casa_cambio_domain.service.BitCoinService;
import br.com.casacambio.casa_cambio_domain.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/v1/api/domain/bitcoin")
public class BitCoinResource {

    Logger LOGGER = LoggerFactory.getLogger(BitCoinResource.class);
    final BitCoinService service;

    @Autowired
    public BitCoinResource(BitCoinService service) {
        this.service = service;
    }

    @GetMapping("/quote")
    public BigDecimal quoteOfDay() {
        LOGGER.info("quoteOfDay: getting quote of day");
        BigDecimal quote =  service.getQuote();
        LOGGER.info("quoteOfDay: successfully got quote of day");

        return quote;
    }

    @PostMapping("/{documentNumber}")
    public ResponseEntity<?> createOperation(@PathVariable @NotBlank String documentNumber,
                                          @Valid @RequestBody BitCoinRequest request) {
        LOGGER.info("createOperation: documentNumber: {}", documentNumber);
        try{
            service.registerOperation(documentNumber, request);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        LOGGER.info("createOperation: created operation documentNumber: {}", documentNumber);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{documentNumber}/balance")
    public BigDecimal getBalance(@PathVariable String documentNumber) {

        LOGGER.info("getBalance: customer {}", documentNumber);
        BigDecimal value = service.getBalanceBitCoin(documentNumber);
        LOGGER.info("getBalance: successfuly get balance {}", documentNumber);

        return value;
    }
}
