package br.com.casacambio.casa_cambio_domain.model.entity;

import br.com.casacambio.casa_cambio_domain.model.dto.BitCoinRequest;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "bitcoinbank")
public class BitCoinBank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BIT_COIN_PURCHASED")
    private BigDecimal bitCoinPurchased;

    @Column(name = "QUOTE")
    private BigDecimal quote;

    @Column(name = "DATE_TRANSACTION")
    private Date dateTransaction;

    @ManyToOne
    private Customer customer;

    public BitCoinBank(){}

    public BitCoinBank(Customer customer, BitCoinRequest request) {
        this.bitCoinPurchased = request.getBitCoinPurchased();
        this.quote = request.getQuote();
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PrePersist
    protected void onCreate() {
        dateTransaction = Calendar.getInstance().getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitCoinBank customer = (BitCoinBank) o;
        return id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
