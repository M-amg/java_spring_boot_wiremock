package com.capgemini.billingservice.dto;

import com.capgemini.billingservice.enums.InvoiceStatus;
import com.capgemini.billingservice.models.CustomerResponse;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record InvoiceDto(
    Long id,
    Long customerId,
    InvoiceStatus status,
    Date createdAt,
    List<InvoiceItemDto> invoiceItems,
    CustomerResponse customer
    ) {}
