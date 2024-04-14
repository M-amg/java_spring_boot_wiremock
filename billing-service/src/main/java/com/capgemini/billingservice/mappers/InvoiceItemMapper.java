package com.capgemini.billingservice.mappers;

import com.capgemini.billingservice.dto.InvoiceItemDto;
import com.capgemini.billingservice.entities.InvoiceItem;
import com.capgemini.billingservice.mappers.resolvers.InvoiceItemResolver;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,uses = {
        DateMapper.class,
        InvoiceItemResolver.class,
        InvoiceMapper.class
})
public interface InvoiceItemMapper {
   // @BeanMapping(builder = @Builder(disableBuilder = true))
    InvoiceItem toEntity(InvoiceItemDto invoiceItemDto);

    InvoiceItemDto toDto(InvoiceItem invoiceItem);

}
