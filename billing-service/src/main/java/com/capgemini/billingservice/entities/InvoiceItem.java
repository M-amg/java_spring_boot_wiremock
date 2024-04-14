package com.capgemini.billingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal unitPrice;
    private Long quantity;
    private String designation;
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    private Invoice invoice;

}
