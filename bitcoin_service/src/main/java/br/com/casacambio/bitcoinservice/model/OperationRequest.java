package br.com.casacambio.bitcoinservice.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OperationRequest {
    @NotNull
    private BigDecimal amount;

    @NotNull
    private OperationEnum operation;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OperationEnum getOperation() {
        return operation;
    }

    public void setOperation(OperationEnum operation) {
        this.operation = operation;
    }
}
