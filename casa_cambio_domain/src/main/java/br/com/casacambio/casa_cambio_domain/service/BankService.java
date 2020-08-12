package br.com.casacambio.casa_cambio_domain.service;

import br.com.casacambio.casa_cambio_domain.model.dto.OperationRequest;
import br.com.casacambio.casa_cambio_domain.model.entity.Bank;
import br.com.casacambio.casa_cambio_domain.model.entity.Customer;
import br.com.casacambio.casa_cambio_domain.model.enums.OperationEnum;
import br.com.casacambio.casa_cambio_domain.repository.BankRepository;
import br.com.casacambio.casa_cambio_domain.repository.CustomerRepository;
import br.com.casacambio.casa_cambio_domain.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    final private BankRepository repository;
    final private CustomerRepository customerRepository;
    final Logger LOGGER = LoggerFactory.getLogger(BankService.class);


    @Autowired
    public BankService(BankRepository repository, CustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    public void saveOperation(String documentNumber, OperationRequest request) {
        Bank bank;
        Optional<Customer> customer =
                customerRepository.findByDocumentNumber(documentNumber);

        if(customer.isPresent()) {
            bank = new Bank(customer.get(), request);
        } else {
            LOGGER.error("user document {} not found", documentNumber);
            throw new NotFoundException("User Not found");
        }

        repository.save(bank);
    }

    public BigDecimal getBalance(String documentNumber) {

        List<Bank> transactions = repository
                .findByCustomer_DocumentNumber(documentNumber);

        if(transactions.size() == 0) {
            throw new NotFoundException("Transactions not found for user");
        }

        BigDecimal sumCredit = getAmountByOperation(transactions, OperationEnum.CREDITO);
        BigDecimal sumDebit = getAmountByOperation(transactions, OperationEnum.DEBITO);

        return sumCredit.subtract(sumDebit);
    }

    public BigDecimal getTotalInvestment(String documentNumber) {
        List<Bank> transactions = repository
                .findByCustomer_DocumentNumber(documentNumber);

        if(transactions.size() == 0) {
            throw new NotFoundException("Transactions not found for user");
        }

        return getAmountByOperation(transactions, OperationEnum.DEBITO);
    }

    private BigDecimal getAmountByOperation(List<Bank> transactions, OperationEnum operation) {
        return transactions.stream()
                .filter(t -> t.getOperation() == operation)
                .map(Bank::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
