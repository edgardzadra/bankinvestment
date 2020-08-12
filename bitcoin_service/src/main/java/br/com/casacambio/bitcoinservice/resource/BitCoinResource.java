package br.com.casacambio.bitcoinservice.resource;

import br.com.casacambio.bitcoinservice.model.AmountRequest;
import br.com.casacambio.bitcoinservice.service.BitCoinService;
import br.com.casacambio.bitcoinservice.service.exception.BadRequestException;
import br.com.casacambio.bitcoinservice.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/api/bitcoin")
@Validated
public class BitCoinResource {

    Logger LOGGER = LoggerFactory.getLogger(BitCoinResource.class);

    final private BitCoinService service;

    @Autowired
    public BitCoinResource(BitCoinService bitCoinService) {
        this.service = bitCoinService;
    }

    @PostMapping("/{documentNumber}")
    public ResponseEntity<?> bitCoinPurchase(@RequestBody AmountRequest request,
                                             @NotBlank @PathVariable String documentNumber) {

        LOGGER.info("bitCoinPurchase: customer {}", documentNumber);
        try {
            BigDecimal value = new BigDecimal(request.getAmount());

            this.service.purchaseBitCoin(value, documentNumber);
        }catch (BadRequestException e) {
            LOGGER.error("bitCoinPurchase: service error customer {}", documentNumber);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (NotFoundException e) {
            LOGGER.info("bitCoinPurchase: customer {} not found", documentNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        LOGGER.info("bitCoinPurchase: successfully purchased customer {}", documentNumber);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
