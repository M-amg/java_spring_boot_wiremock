package com.capgemini.billingservice.repositories;

import com.capgemini.billingservice.entities.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {}
