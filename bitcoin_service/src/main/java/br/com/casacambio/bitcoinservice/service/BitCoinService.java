package br.com.casacambio.bitcoinservice.service;

import br.com.casacambio.bitcoinservice.model.BitCoinRequest;
import br.com.casacambio.bitcoinservice.model.OperationEnum;
import br.com.casacambio.bitcoinservice.model.OperationRequest;
import br.com.casacambio.bitcoinservice.service.exception.BadRequestException;
import br.com.casacambio.bitcoinservice.service.http.BankDomain;
import br.com.casacambio.bitcoinservice.service.http.BitCoinDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BitCoinService {

    final BankDomain bankDomain;
    final BitCoinDomain domain;
    Logger LOGGER = LoggerFactory.getLogger(BitCoinService.class);

    @Autowired
    public BitCoinService(BankDomain bankDomain, BitCoinDomain bitCoinDomain) {
        this.bankDomain = bankDomain;
        this.domain = bitCoinDomain;
    }

    public void purchaseBitCoin(BigDecimal amount, String documentNumber) {
        BigDecimal balance = bankDomain.getCustomerBalance(documentNumber);
        BigDecimal quote = domain.getQuoteBitCoin();

        BigDecimal totalValueOfPurchase = quote.multiply(amount);

        if(totalValueOfPurchase.compareTo(balance) > 0) {
            LOGGER.error("purchaseBitCoin: user {} does not have balance to operate", documentNumber);
            throw new BadRequestException("Insuficient balance");
        }

        updateCustomerBalance(documentNumber, totalValueOfPurchase);
        createBitCoinTransaction(amount, documentNumber, quote);
    }

    private void createBitCoinTransaction(BigDecimal amount, String documentNumber, BigDecimal quote) {
        BitCoinRequest request = new BitCoinRequest();
        request.setBitCoinPurchased(amount);
        request.setQuote(quote);
        domain.createTransaction(documentNumber, request);
        LOGGER.info("purchaseBitCoin: created transaction for customer {} amount {}",
                documentNumber, amount);
    }

    private void updateCustomerBalance(String documentNumber, BigDecimal totalValueOfPurchase) {
        OperationRequest operationRequest = new OperationRequest();
        operationRequest.setAmount(totalValueOfPurchase);
        operationRequest.setOperation(OperationEnum.DEBITO);
        bankDomain.updateBalanceOfCustomer(documentNumber, operationRequest);
        LOGGER.info("purchaseBitCoin: updated balance to {} for customer {}",
                totalValueOfPurchase, documentNumber);
    }
}
