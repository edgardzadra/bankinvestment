package br.com.casacambio.casa_cambio_domain.model.entity;

import br.com.casacambio.casa_cambio_domain.model.dto.OperationRequest;
import br.com.casacambio.casa_cambio_domain.model.enums.OperationEnum;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "OPERATION")
    private OperationEnum operation;

    @Column(name = "DATE_TRANSACTION")
    private Date dateTransaction;

    @ManyToOne
    private Customer customer;

    @PrePersist
    protected void onCreate() {
        dateTransaction = Calendar.getInstance().getTime();
    }

    public Bank() {}

    public Bank(Customer customer, OperationRequest request) {
        this.customer = customer;
        this.amount = request.getAmount();
        this.operation = request.getOperation();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return id.equals(bank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
