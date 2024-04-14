package com.capgemini.customerservice.customer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String fullName;
    private String email;
}
