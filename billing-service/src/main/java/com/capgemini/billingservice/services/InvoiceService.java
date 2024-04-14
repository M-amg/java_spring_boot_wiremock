package com.capgemini.billingservice.services;

import com.capgemini.billingservice.dto.InvoiceDto;
import com.capgemini.billingservice.dto.InvoiceItemDto;
import com.capgemini.billingservice.entities.Invoice;
import com.capgemini.billingservice.entities.InvoiceItem;
import com.capgemini.billingservice.exception.ResourceNotFoundException;
import com.capgemini.billingservice.mappers.InvoiceItemMapper;
import com.capgemini.billingservice.mappers.InvoiceMapper;
import com.capgemini.billingservice.models.CustomerResponse;
import com.capgemini.billingservice.repositories.InvoiceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class InvoiceService {

  private final InvoiceRepository invoiceRepository;
  private final InvoiceMapper invoiceMapper;
  private final InvoiceItemMapper invoiceItemMapper;
  private final CustomerApiClient customerApiClient;

  public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
    return invoiceMapper.toDto(invoiceRepository.save(invoiceMapper.toEntity(invoiceDto)));
  }

  public InvoiceDto getInvoiceById(Long id) {
    Invoice invoice = invoiceRepository.findById(id).orElseThrow(RuntimeException::new);
    CustomerResponse customer = customerApiClient.getCustomer(invoice.getCustomerId());
    invoice.setCustomer(customer);
    return invoiceMapper.toDto(invoice);
  }

  public InvoiceDto addItemToService(List<InvoiceItemDto> itemsDto, Long invoiceId) {

    Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);

    if (invoiceOptional.isEmpty()) {
      throw new ResourceNotFoundException("");
    }
    Invoice invoice = invoiceOptional.get();
    if (CollectionUtils.isEmpty(invoice.getInvoiceItems())) {
      invoice.setInvoiceItems(new ArrayList<>());
    }

    List<InvoiceItem> itemsList = itemsDto.stream().map(invoiceItemMapper::toEntity).toList();

    invoice.getInvoiceItems().addAll(itemsList);

    return invoiceMapper.toDto(invoiceRepository.save(invoice));
  }
}
