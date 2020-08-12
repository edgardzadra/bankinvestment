package br.com.casacambio.casa_cambio_domain.repository;

import br.com.casacambio.casa_cambio_domain.model.entity.BitCoinBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BitCoinBankRepository extends JpaRepository<BitCoinBank, Long> {

    List<BitCoinBank> findByCustomer_DocumentNumber(String documentNumber);
}
