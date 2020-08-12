package br.com.casacambio.casa_cambio_domain.service;

import br.com.casacambio.casa_cambio_domain.model.dto.CustomerRequest;
import br.com.casacambio.casa_cambio_domain.model.entity.Customer;
import br.com.casacambio.casa_cambio_domain.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    final private CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void createCustomer(CustomerRequest customer) {
        Customer entity = new Customer();
        BeanUtils.copyProperties(customer, entity);

        repository.save(entity);
    }

    public boolean customerExists(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber).isPresent();
    }
}
