package com.capgemini.billingservice.mappers.resolvers;

import com.capgemini.billingservice.dto.InvoiceDto;
import com.capgemini.billingservice.entities.Invoice;
import com.capgemini.billingservice.models.CustomerResponse;
import com.capgemini.billingservice.repositories.InvoiceRepository;
import com.capgemini.billingservice.services.CustomerApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
@Slf4j
public class InvoiceResolver {

    private final InvoiceRepository invoiceRepository;
    private final CustomerApiClient customerApiClient;
    @ObjectFactory
    public Invoice resolve(InvoiceDto dto, @TargetType Class<Invoice> type) {
        log.info("I'm in resolver {}", dto);
        if (dto != null && dto.id() != null) {
            Optional<Invoice> optionalInvoice = invoiceRepository.findById(dto.id());
            if (optionalInvoice.isPresent()) {
                Invoice invoice = optionalInvoice.get();
                CustomerResponse customer = customerApiClient.getCustomer(invoice.getCustomerId());
                invoice.setCustomer(customer);
                return invoice;
            }
        }
        return new Invoice();
    }


}
