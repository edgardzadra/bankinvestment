package br.com.casacambio.casa_cambio_domain.service;

import br.com.casacambio.casa_cambio_domain.model.dto.BitCoinRequest;
import br.com.casacambio.casa_cambio_domain.model.entity.Bank;
import br.com.casacambio.casa_cambio_domain.model.entity.BitCoinBank;
import br.com.casacambio.casa_cambio_domain.model.entity.Customer;
import br.com.casacambio.casa_cambio_domain.repository.BitCoinBankRepository;
import br.com.casacambio.casa_cambio_domain.repository.CustomerRepository;
import br.com.casacambio.casa_cambio_domain.service.exception.NotFoundException;
import br.com.casacambio.casa_cambio_domain.service.http.BitCoinApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BitCoinService {

    Logger LOGGER = LoggerFactory.getLogger(BitCoinService.class);

    final private BitCoinApi bitCoinApi;

    final private CustomerRepository customerRepository;

    final private BitCoinBankRepository repository;

    @Autowired
    public BitCoinService(BitCoinApi api, CustomerRepository customerRepository,
                          BitCoinBankRepository repository) {
        this.bitCoinApi = api;
        this.customerRepository = customerRepository;
        this.repository = repository;
    }

    public BigDecimal getQuote() {
        return bitCoinApi.getAmount();
    }

    public void registerOperation(String documentNumber, BitCoinRequest request) {
        Optional<Customer> customer = customerRepository.findByDocumentNumber(documentNumber);

        if(customer.isPresent()) {
            BitCoinBank bitCoinBank = new BitCoinBank(customer.get(), request);
            repository.save(bitCoinBank);
        } else {
            LOGGER.error("registerOperation: customer not found {}", documentNumber);
            throw new NotFoundException("customer not found");
        }
    }

    public BigDecimal getBalanceBitCoin(String documentNumber) {
        List<BitCoinBank> transactions = repository.findByCustomer_DocumentNumber(documentNumber);

        if(transactions.size() == 0) {
            throw new NotFoundException("Transactions not found for user");
        }

        return transactions.stream()
                .map(BitCoinBank::getBitCoinPurchased)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
