package com.capgemini.customerservice.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
