package br.com.casacambio.casa_cambio_domain.model.dto;

import java.math.BigDecimal;

public class BitCoinResponse {

    private String base;
    private String currency;
    private BigDecimal amount;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
