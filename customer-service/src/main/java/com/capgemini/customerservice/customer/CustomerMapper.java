package com.capgemini.customerservice.customer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    uses = {})
interface CustomerMapper {
  Customer toEntity(CustomerDto customerDto);

  CustomerDto toDto(Customer customer);
}
