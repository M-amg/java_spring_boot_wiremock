package com.capgemini.billingservice.controller;

import com.capgemini.billingservice.dto.InvoiceDto;
import com.capgemini.billingservice.dto.InvoiceItemDto;
import com.capgemini.billingservice.services.InvoiceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/billing")
@RequiredArgsConstructor
public class BillingController {

  private final InvoiceService invoiceService;

  @GetMapping("/{id}")
  public ResponseEntity<InvoiceDto> getInvoice(@PathVariable("id") Long id) {
    return new ResponseEntity<>(invoiceService.getInvoiceById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceDto invoiceDto) {
    return new ResponseEntity<>(invoiceService.createInvoice(invoiceDto), HttpStatus.CREATED);
  }

  @PostMapping("/{invoiceId}")
  public ResponseEntity<InvoiceDto> addItemsToInvoice(
      @PathVariable("invoiceId") Long id, @RequestBody List<InvoiceItemDto> invoiceItemDtoList) {
    return new ResponseEntity<>(
        invoiceService.addItemToService(invoiceItemDtoList, id), HttpStatus.CREATED);
  }
}
