package com.capgemini.customerservice.customer;

import lombok.Builder;

@Builder
public record ErrorDto(Integer code,String message) {}
