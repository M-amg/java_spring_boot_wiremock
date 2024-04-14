package com.capgemini.billingservice.entities;

import com.capgemini.billingservice.enums.InvoiceStatus;
import com.capgemini.billingservice.models.CustomerResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue
    private Long id;
    private Long customerId;
    private InvoiceStatus status;
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> invoiceItems;

    @Transient
    private CustomerResponse customer;
}
