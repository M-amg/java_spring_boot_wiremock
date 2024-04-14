package com.capgemini.billingservice.dto;

import com.capgemini.billingservice.entities.Invoice;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.*;

@Builder
public record InvoiceItemDto(
    Long id,
    BigDecimal unitPrice,
    Long quantity,
    String designation,
    OffsetDateTime createdAt,
    InvoiceDto invoice) {}
