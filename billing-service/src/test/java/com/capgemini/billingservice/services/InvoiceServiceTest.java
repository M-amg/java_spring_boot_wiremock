package com.capgemini.billingservice.services;

import static com.capgemini.billingservice.enums.InvoiceStatus.IN_PROGRESSING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.capgemini.billingservice.dto.InvoiceDto;
import com.capgemini.billingservice.dto.InvoiceItemDto;
import com.capgemini.billingservice.entities.Invoice;
import com.capgemini.billingservice.entities.InvoiceItem;
import com.capgemini.billingservice.mappers.InvoiceItemMapper;
import com.capgemini.billingservice.mappers.InvoiceMapper;
import com.capgemini.billingservice.repositories.InvoiceRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class InvoiceServiceTest {

  @InjectMocks private InvoiceService invoiceService;

  @Mock private InvoiceMapper invoiceMapper;

  @Mock private InvoiceItemMapper invoiceItemMapper;

  @Mock private InvoiceRepository invoiceRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateInvoice() {
    // Given
    InvoiceDto invoiceDto = InvoiceDto.builder().customerId(1L).status(IN_PROGRESSING).build();
    Invoice invoice = Invoice.builder().customerId(1L).status(IN_PROGRESSING).build();

    when(invoiceRepository.save(any())).thenReturn(invoice);
    when(invoiceMapper.toDto(invoice)).thenReturn(invoiceDto);

    // When
    InvoiceDto savedInvoice = invoiceService.createInvoice(invoiceDto);

    // Then
    assertThat(savedInvoice.customerId()).isSameAs(invoice.getCustomerId());
  }

  @Test
  void shouldAddItemsToInvoice() {
    // Given
    Long invoiceId = 1L;
    Invoice invoice = Invoice.builder().id(1L).customerId(1L).status(IN_PROGRESSING).build();

    List<InvoiceItemDto> invoiceItemDtoList =
        List.of(
            InvoiceItemDto.builder()
                .quantity(1L)
                .unitPrice(BigDecimal.TEN)
                .designation("Product1")
                .build());

    List<InvoiceItem> invoiceItemList =
        List.of(
            InvoiceItem.builder()
                .quantity(1L)
                .unitPrice(BigDecimal.TEN)
                .designation("Product1")
                .build());

    InvoiceDto invoiceDto =
        InvoiceDto.builder()
            .invoiceItems(invoiceItemDtoList)
            .customerId(1L)
            .status(IN_PROGRESSING)
            .build();

    when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.ofNullable(invoice));
    when(invoiceItemMapper.toEntity(any())).thenReturn(invoiceItemList.get(0));
    when(invoiceRepository.save(any())).thenReturn(invoice);
    when(invoiceMapper.toDto(invoice)).thenReturn(invoiceDto);

    // When
    InvoiceDto savedInvoice = invoiceService.addItemToService(invoiceItemDtoList, invoiceId);

    // Then
    assertThat(savedInvoice.invoiceItems()).isNotNull();
  }
}
