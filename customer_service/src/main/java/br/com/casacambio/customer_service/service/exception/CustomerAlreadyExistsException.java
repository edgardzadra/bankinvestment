package br.com.casacambio.customer_service.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException  extends RuntimeException {

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
