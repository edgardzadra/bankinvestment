package br.com.casacambio.consolidated.model;

import java.math.BigDecimal;

public class ConsolidatedInfoCustomer {

    private BigDecimal balanceAccountInvestiment;
    private BigDecimal balanceBitCoin;
    private BigDecimal totalInvestmentAmount;
    private BigDecimal quoteBitCointOfDay;

    public BigDecimal getBalanceAccountInvestiment() {
        return balanceAccountInvestiment;
    }

    public void setBalanceAccountInvestiment(BigDecimal balanceAccountInvestiment) {
        this.balanceAccountInvestiment = balanceAccountInvestiment;
    }

    public BigDecimal getBalanceBitCoin() {
        return balanceBitCoin;
    }

    public void setBalanceBitCoin(BigDecimal balanceBitCoin) {
        this.balanceBitCoin = balanceBitCoin;
    }

    public BigDecimal getTotalInvestmentAmount() {
        return totalInvestmentAmount;
    }

    public void setTotalInvestmentAmount(BigDecimal totalInvestmentAmount) {
        this.totalInvestmentAmount = totalInvestmentAmount;
    }

    public BigDecimal getQuoteBitCointOfDay() {
        return quoteBitCointOfDay;
    }

    public void setQuoteBitCointOfDay(BigDecimal quoteBitCointOfDay) {
        this.quoteBitCointOfDay = quoteBitCointOfDay;
    }
}
