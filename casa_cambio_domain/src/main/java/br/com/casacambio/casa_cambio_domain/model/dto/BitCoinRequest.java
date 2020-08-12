package br.com.casacambio.casa_cambio_domain.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BitCoinRequest {

    @NotNull
    private BigDecimal bitCoinPurchased;

    @NotNull
    private BigDecimal quote;

    public BigDecimal getBitCoinPurchased() {
        return bitCoinPurchased;
    }

    public void setBitCoinPurchased(BigDecimal bitCoinPurchased) {
        this.bitCoinPurchased = bitCoinPurchased;
    }

    public BigDecimal getQuote() {
        return quote;
    }

    public void setQuote(BigDecimal quote) {
        this.quote = quote;
    }
}
