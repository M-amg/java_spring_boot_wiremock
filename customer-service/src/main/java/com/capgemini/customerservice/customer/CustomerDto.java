package com.capgemini.customerservice.customer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("Customer")
public class CustomerDto {
    private Long id;
    private String fullName;
    private String email;
}
