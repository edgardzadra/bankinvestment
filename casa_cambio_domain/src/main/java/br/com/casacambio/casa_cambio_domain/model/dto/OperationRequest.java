package br.com.casacambio.casa_cambio_domain.model.dto;

import br.com.casacambio.casa_cambio_domain.model.enums.OperationEnum;

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
