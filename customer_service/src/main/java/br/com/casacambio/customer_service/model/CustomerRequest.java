package br.com.casacambio.customer_service.model;

import javax.validation.constraints.NotBlank;

public class CustomerRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String documentNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
