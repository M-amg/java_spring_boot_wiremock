package com.capgemini.billingservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InvoiceStatus {
    IN_PROGRESSING("in progressing"),
    VALIDATE("validate"),
    REJECTED("rejected");

    private final String value;
}
