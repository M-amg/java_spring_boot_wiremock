package com.capgemini.billingservice.mappers;

import com.capgemini.billingservice.dto.InvoiceDto;
import com.capgemini.billingservice.entities.Invoice;
import com.capgemini.billingservice.mappers.resolvers.InvoiceResolver;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
        DateMapper.class,
        InvoiceResolver.class,
      //  InvoiceItemMapper.class
})
public interface InvoiceMapper {
    @BeanMapping(builder = @Builder(disableBuilder = true))
    Invoice toEntity(InvoiceDto invoiceDto);
    InvoiceDto toDto(Invoice invoice);

}
