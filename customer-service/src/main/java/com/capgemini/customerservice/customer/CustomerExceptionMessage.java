package com.capgemini.customerservice.customer;

import lombok.Getter;

@Getter
public enum CustomerExceptionMessage {
    CUSTOMER_NOT_FOUND_EXCEPTION("No customer found"),
    CUSTOMER_ALREADY_EXIST_EXCEPTION("Customer already exist with given email: "),
    CUSTOMER_EMAIL_NOT_VALID_EXCEPTION("Email not valid: ");
    private final String value;

    CustomerExceptionMessage(String value) {
        this.value = value;
    }
}
