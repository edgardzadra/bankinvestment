package br.com.casacambio.casa_cambio_domain.repository;

import br.com.casacambio.casa_cambio_domain.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByDocumentNumber(String documentNumber);
}
