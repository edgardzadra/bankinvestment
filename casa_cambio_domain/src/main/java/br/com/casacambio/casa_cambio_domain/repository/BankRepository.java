package br.com.casacambio.casa_cambio_domain.repository;

import br.com.casacambio.casa_cambio_domain.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {

    List<Bank> findByCustomer_DocumentNumber(String documentNumber);
}
