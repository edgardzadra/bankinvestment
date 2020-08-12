package br.com.casacambio.consolidated.service;

import br.com.casacambio.consolidated.model.ConsolidatedInfoCustomer;
import br.com.casacambio.consolidated.service.http.CambioDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConsolidatedService {

    final private CambioDomain cambioDomain;

    @Autowired
    public ConsolidatedService(CambioDomain domain){
        this.cambioDomain = domain;
    }

    public ConsolidatedInfoCustomer getInformation(String documentNumber) {
        BigDecimal quoteOfBitCoin = cambioDomain.getQuoteOfBitCoin();
        BigDecimal totalInvestment = cambioDomain.getCustomerTotalInvestiment(documentNumber);
        BigDecimal accountBalance = cambioDomain.getCustomerBalance(documentNumber);
        BigDecimal bitCoinBalance = cambioDomain.getBalanceOfCustomerBitCoin(documentNumber);

        ConsolidatedInfoCustomer consolidatedInfoCustomer = new ConsolidatedInfoCustomer();
        consolidatedInfoCustomer.setQuoteBitCointOfDay(quoteOfBitCoin);
        consolidatedInfoCustomer.setBalanceAccountInvestiment(accountBalance);
        consolidatedInfoCustomer.setTotalInvestmentAmount(totalInvestment);
        consolidatedInfoCustomer.setBalanceBitCoin(bitCoinBalance);


        return consolidatedInfoCustomer;
    }
}
