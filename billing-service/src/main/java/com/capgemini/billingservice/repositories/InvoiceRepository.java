package com.capgemini.billingservice.repositories;

import com.capgemini.billingservice.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {}
