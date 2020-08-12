package br.com.casacambio.bank.service;

import br.com.casacambio.bank.model.CreditDebitAccount;
import br.com.casacambio.bank.service.exception.NotFoundException;
import br.com.casacambio.bank.service.http.BankDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class OperateAccountService {

    final BankDomain bankDomain;
    Logger LOGGER = LoggerFactory.getLogger(OperateAccountService.class);

    @Autowired
    public OperateAccountService(BankDomain bankDomain) {
        this.bankDomain = bankDomain;
    }

    public void operateAccount(String documentNumber, CreditDebitAccount creditDebitAccount) throws Exception {
        try{
            bankDomain.createOperation(documentNumber, creditDebitAccount);
        } catch (NotFoundException e) {
            LOGGER.error("operateAccount: user {} not found", documentNumber);
            throw new NotFoundException("User not found");
        }catch (Exception e) {
            LOGGER.error("operateAccount: error - {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
